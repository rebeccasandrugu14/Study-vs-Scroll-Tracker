import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    // varibles b/w study & scroll timer
    private static boolean paused = true;
    private static int studysec = 0;
    private static int scrollsec = 0;
    private static Object lock = new Object();
    private static boolean done = false;

    //varibles for the desired studyed timer
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

    public static boolean isDoone() {
    synchronized (lock) {
        return done;
    }
    }
}

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //Get user to enter their study infomation goals
        System.out.print("How long are you studying in (minutes): ");
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
                            System.out.println("Study session completed!");

                            //Display productivity reports
                            productivityReport(finalsec);
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
                System.out.println("Press S to resume studying or B to take scroll break");
                String choice = scanner.nextLine();

                if(isDone()) {
                    break;
                }
                if(choice.equalsIgnoreCase("S")) {
                    //change the boolean pause value
                    study();
                    System.out.print.println("Studying resumed.");
                }
                else if(choice.equalsIgnoreCase("B")) {
                    scroll();
                    System.out.println("Scrolling break.");
                }
                else {
                    System.out.println("Pick a vaild choice");
                }
            }
        }
        scanner.close();
    }

    //The data from
    public static void productivityReport(int finalsec) {

        //calculates the percentages of studying and scrolling
        double studyper = (oristudysec / (double) finalsec) * 100;
        double scrollper = (oriscrollsec / (double) finalsec) * 100;

        //Oringial Duration Report
        System.out.println("Desired Duratoin Report");
        System.out.println("Desired duration: " + finalsec/60.0 + " minutes");
        System.out.println("Time spent studying: " + oristudysec/60.0 + " minutes");
        System.out.println("Time spent scrolling: " + oriscrollsec/60.0 + " minutes");
        System.out.println("Study percentage: " + String.format("%.2f", studyper) + "%");
        System.out.println("Scroll percentage: " + String.format("%.2f", scrollper) + "%");

        System.out.println();

        // Calculate overall percentages
        double totstudy = (finalsec / (double) (finalsec + scrollsec)) * 100;
        double totscroll = (scrollsec / (double) (finalsec + scrollsec)) * 100;

        //Overall Duration Report
        System.out.println("\nOverall Report");
        System.out.println("Total Duration: " + (finalsec + scrollsec)/60.0 + " minutes");
        System.out.println("Time spent studying " + finalsec/60.0 + " minutes");
        System.out.println("Time spent scrolling " + scrollsec/60.0 + " minutes");
        System.out.println("Study percentage: " + String.format("%.2f", totstudy) + "%");
        System.out.println("Scroll percentage: " + String.format("%.2f", totscroll) + "%");
    }