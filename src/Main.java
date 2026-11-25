import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    //Variables for the study & scroll timer
    private static boolean paused = true;
    private static int studysec = 0;
    private static int scrollsec = 0;
    private static Object lock = new Object();
    private static boolean done = false;

    //Variables for tracking time within the planned study timer
    private static int oristudysec = 0;
    private static int oriscrollsec = 0;

    //Starts or resumes the user's study timer
    public static void study() {
        //Make sure only one timer can be access at a time
        synchronized (lock) {
            paused = false;
        }
    }

    //Pauses timer when user is taking a scroll break
    public static void scroll() {
        synchronized (lock) {
            paused = true;
        }
    }

    //Returns whether the studying portions are done
    public static boolean isDone() {
        synchronized (lock) {
            return done;
        }
    }

    public static void main(String[] args) {

        //Create a scanner object
        Scanner scanner = new Scanner(System.in);

        //User enters in their study goals
        System.out.println("How long are you studying in (minutes): ");
        int totmin = scanner.nextInt();
        scanner.nextLine();

        //Converts into minutes and initializes the values of studysec & scrollsec
        int finalsec = totmin * 60;
        studysec = totmin * 60;
        scrollsec = 0;

        //Asks the user to start the timers
        System.out.println("To start timer, type start");
        String start = scanner.nextLine();

        //Starts the overall study timer: original study duration with no pauses
        if (start.equalsIgnoreCase("start")) {
            //Sets the user's initial state to studying
            study();

            //Creates all the timers: overall, study, scroll
            Timer timer = new Timer();
            Timer study = new Timer();
            Timer scroll = new Timer();

            //Tracks studying & scrolling in the planned study time
            TimerTask task = new TimerTask() {
                //tracks the seconds of the planned duration
                private int sec = 0;

                //Updates the time passed and records the user's activity
                @Override
                public void run() {
                    synchronized (lock) {
                        sec++;
                        //Increased the original study time when user studying
                        if(!paused) {
                            oristudysec++;
                        }
                        //Increased the original scroll time when user takes a break
                        else {
                            oriscrollsec++;
                        }
                        //when seconds equals the user study goal, overall timer stops
                        if(sec == finalsec) {
                            timer.cancel();
                        }
                    }
                }
            };

            //creates study timer that counts down
            TimerTask studytask = new TimerTask() {

                @Override
                public void run () {
                    //Make sure that no other thread is accessing the if statement
                    synchronized (lock) {
                        //Decreases the study time when timer isn't paused
                        if(!paused && studysec > 0) {
                            studysec--;
                        }
                        //when study sec = 0, all timers stop and prints productivity report
                        if(studysec == 0) {
                            //signals that the all timers and methods correlated to timers to stop
                            done = true;

                            //Stops all timers
                            timer.cancel();
                            study.cancel();
                            scroll.cancel();

                            //The ending message
                            System.out.println();
                            System.out.println("Study session completed!");
                            System.out.println();

                            //Display productivity reports
                            productivityReport(finalsec);
                            System.out.println("\nType 'next' to continue to your productivity reflection.");
                        }
                    }
                }
            };

            //Scroll timer tracks time spent on breaks
            TimerTask scrolltask = new TimerTask() {

                @Override
                public void run () {
                    synchronized (lock) {
                        //Increases scroll time when the user is on a break
                        // and when the study session is still going
                        if(paused && !done)
                            scrollsec++;
                    }
                }
            };

            //Schedules all timers to update every second
            timer.scheduleAtFixedRate(task, 1000, 1000);
            study.scheduleAtFixedRate(studytask, 1000, 1000);
            scroll.scheduleAtFixedRate(scrolltask, 1000, 1000);

            //Ask the user if they want to take a scroll break or keep studying
            //while the study session is continuing
            while(!isDone()) {
                System.out.println("Type S to resume studying or B to take scroll break");
                String choice = scanner.nextLine();

                //If the study session is done, exits the loop
                if(isDone()) {
                    break;
                }
                //Checks if the user wants to resume studying
                if(choice.equalsIgnoreCase("S")) {
                    //change the boolean pause value
                    study();
                    System.out.println("Studying resumed.");
                }
                //Checking if the user wants a scroll break
                else if(choice.equalsIgnoreCase("B")) {
                    scroll();
                    System.out.println("Scrolling break.");
                }
                //Displays error message if user puts in the wrong input
                else {
                    System.out.println("Pick a valid choice");
                }
            }
            //Calls the reflect method
            reflect(scanner);
        }
        //Close Scanner object
        scanner.close();
    }

    //Calculate and displays the user's productivity report
    public static void productivityReport(int finalsec) {

        //Calculates the percentages of studying and scrolling - during planned time
        double studyper = (oristudysec / (double) finalsec) * 100;
        double scrollper = (oriscrollsec / (double) finalsec) * 100;

        //Planned Duration Report
        System.out.println("===Planned Duration Report---");
        System.out.println("Desired duration: " + String.format("%.2f", (finalsec/60.0)) + " minutes");
        System.out.println("Time spent studying: " + String.format("%.2f", (oristudysec/60.0)) + " minutes");
        System.out.println("Time spent scrolling: " + String.format("%.2f", (oriscrollsec/60.0)) + " minutes");
        System.out.println("Study percentage: " + String.format("%.2f", studyper) + "%");
        System.out.println("Scroll percentage: " + String.format("%.2f", scrollper) + "%");

        //Calculate study and scroll percentages during the entire session
        double totstudy = (finalsec / (double) (finalsec + scrollsec)) * 100;
        double totscroll = (scrollsec / (double) (finalsec + scrollsec)) * 100;

        //Overall Duration Report
        System.out.println("\n---Overall Report===");
        System.out.println("Total Duration: " + String.format("%.2f", ((finalsec + scrollsec)/60.0)) + " minutes");
        System.out.println("Time spent studying " +  String.format("%.2f", (finalsec/60.0)) + " minutes");
        System.out.println("Time spent scrolling " +  String.format("%.2f", (scrollsec/60.0)) + " minutes");
        System.out.println("Study percentage: " + String.format("%.2f", totstudy) + "%");
        System.out.println("Scroll percentage: " + String.format("%.2f", totscroll) + "%");
    }

    //Users reflect on their productivity
    public static void reflect(Scanner scanner) {
        System.out.println();
        //Ask users their feelings on their productivity scores
        System.out.println("Are you happy with your productivity scores? (Yes or No)");

        String answer = scanner.nextLine();
        //Give supportive messages and a closing message for users
        if(answer.equalsIgnoreCase("Yes")){
            System.out.println("Amazing job! Keep up your positive study habits!");
            System.out.println("Thanks for using the Study vs. Scroll Tracker!");
        }
        else if(answer.equalsIgnoreCase("No")) {
            System.out.println("Keep working hard towards your study goals!");
            System.out.println("Thanks for using the Study vs. Scroll Tracker!");
        }
        else {
            System.out.println("Thanks for using the Study vs. Scroll Tracker!");
        }
    }
}