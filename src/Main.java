import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    //varibles b/w study & scroll timer
    private static boolean paused = true;
    private static int studysec = 0;
    private static int scrollsec = 0;
    private static Object lock = new Object();
    private static boolean done = false;

    //varibles for the desired stuyd timer
    private static int oristudysec = 0;
    private static int oriscrollsec = 0;

    public static void study() {
        //make sure only one timer can access at a time
        synchronized (lock) {
            paused = false;
        }
    }

    public static void scroll() {
        synchronized (lock) {
            paused = true;
        }
    }

    public static boolean isDone() {
        synchronized (lock) {
            return done;
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Get user to enter their study infomation goals
        System.out.println("How long are you studying in (minutes): ");
        int totmin = scanner.nextInt();
        scanner.nextLine();

        int finalsec = totmin * 60;
        studysec = totmin * 60;
        scrollsec = 0;

        //Checks to see when it can start the timer
        System.out.println("To start timer, type start");
        String start = scanner.nextLine();

        // to start the overall study timer: orgingal study duration with no pauses
        if (start.equalsIgnoreCase("start")) {
            //starts the timers
            study();

            //Creates all the timers
            Timer timer = new Timer();
            Timer study = new Timer();
            Timer scroll = new Timer();

            // create the desired study timer
            TimerTask task = new TimerTask() {
                private int sec = 0;

                @Override
                public void run() {
                    synchronized (lock) {
                        sec++;
                        if(!paused) {
                            oristudysec++;
                        }
                        else {
                            oriscrollsec++;
                        }
                        if(sec == finalsec) {
                            timer.cancel();
                        }
                    }
                }
            };
            // actually starts the overall study timer
            //timer.scheduleAtFixedRate(task, 1000, 1000);


            // Study Timer
            // Counts down the secounds when user is studying
            //Timer study = new Timer();
            TimerTask studytask = new TimerTask() {

                @Override
                public void run () {
                    //make sure that no other thread is accessing the if statement
                    synchronized (lock) {
                        //when the user to resume studying, time is updating & drecreasing
                        if(!paused && studysec > 0) {
                            studysec--;
                        }
                        if(studysec == 0) {
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
            //study.scheduleAtFixedRate(studytask, 1000, 1000);

            // Scroll Timer
            //Count seconds up
            //Timer scroll = new Timer();
            TimerTask scrolltask = new TimerTask() {

                @Override
                public void run () {
                    synchronized (lock) {
                        //when the user decided to take break, time is update & increaseing
                        if(paused && !done)
                            scrollsec++;
                    }
                }
            };
            timer.scheduleAtFixedRate(task, 1000, 1000);
            study.scheduleAtFixedRate(studytask, 1000, 1000);
            scroll.scheduleAtFixedRate(scrolltask, 1000, 1000);

            while(!isDone()) {
                System.out.println("Type S to resume studying or B to take scroll break");
                String choice = scanner.nextLine();

                if(isDone()) {
                    break;
                }
                if(choice.equalsIgnoreCase("S")) {
                    //change the boolean pause value
                    study();
                    System.out.println("Studying resumed.");
                }
                else if(choice.equalsIgnoreCase("B")) {
                    scroll();
                    System.out.println("Scrolling break.");
                }
                else {
                    System.out.println("Pick a vaild choice");
                }
            }
            reflect(scanner);
        }
        scanner.close();
    }

    //The data from
    public static void productivityReport(int finalsec) {

        //calculates the percentages of studying and scrolling
        double studyper = (oristudysec / (double) finalsec) * 100;
        double scrollper = (oriscrollsec / (double) finalsec) * 100;

        //Oringial Duration Report
        System.out.println("===Planned Duration Report---");
        System.out.println("Desired duration: " + String.format("%.2f", (finalsec/60.0)) + " minutes");
        System.out.println("Time spent studying: " + String.format("%.2f", (oristudysec/60.0)) + " minutes");
        System.out.println("Time spent scrolling: " + String.format("%.2f", (oriscrollsec/60.0)) + " minutes");
        System.out.println("Study percentage: " + String.format("%.2f", studyper) + "%");
        System.out.println("Scroll percentage: " + String.format("%.2f", scrollper) + "%");

        // Calculate overall percentages
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

    public static void reflect(Scanner scanner) {
        System.out.println();
        System.out.println("Are you happy with your producity score? (Yes or No)");

        String answer = scanner.nextLine();
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