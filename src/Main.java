import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    //shared varibles b/w study & scroll timer
    private static boolean paused = true;
    private studysec = 0;
    private scrollsec = 0;
    private static Object lock = new Object();

    public void resume() {
        //make sure only one timer can access at a time
        synchronized (lock) {
            paused = false;
            studysec--;
            //do stuff with the secs
        }
    }

    public void paused() {
        synchronized (lock) {
            paused = true;
            scrollsec++;
        }
    }

    public static void main(String[] args) {

        //Desired Timer
        Scanner scanner = new Scanner(System.in);
        private int sec = 0;

        System.out.print("How long are you studying in (minutes): ");
        int totmin = scanner.nextInt();
        allmin = totmin;
        int finalsec = totmin * 60
        scrollsec = finalsec;
        System.out.println("To start timer, type start");
        String start = scanner.nextLine();

        // to start the overall study timer: orgingal study duration with no pauses
        if (start.equalsIgnoreCase("start")) {
            // create the desired study timer
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                private int sec = 0;
                public void run() {
                    sec++;
                    if(sec == finalsec) {
                        timer.cancel();
                    }
                }
            };
            // actually starts the overall study timer
            timer.scheduleAtFixedRate(task, 1000, 1000);


            // create the study timer & start the timer
            Timer study = new Timer();
            TimerTask studytask = new TimerTask() {
                @Override
                public void run () {
                    //make sure that no other thread is accessing the if statement
                    synchronized (lock) {
                        //when the user to resume studying, time is updating & drecreasing
                        if(!paused && studysec > 0)
                        studysec--;
                    }
                }
            };
            study.scheduleAtFixedRate(task, 1000, 1000);

            // create the scroll timer & start the timer
            Timer scroll = new Timer();
            TimerTask scrolltask = new TimerTask() {
                @Override
                public void run () {
                    synchronized (lock) {
                        //when the user decided to take break, time is update & increaseing
                        if(paused)
                            scrollsec++;
                    }
                }
            };
            scroll.scheduleAtFixedRate(task, 1000, 1000);
        }

        while(true) {
            System.out.println("Press S to resume studying or B to take scroll break");
            String choice = scanner.nextInt();
            if(choice.equalsIgnoreCase("S")) {
                //change the boolean pause value
            }
            if(choice.equalsIgnoreCase("B")) {
                //change the boolean pause value
            }
        }


        scanner.close();
        //producity report
        if(mins == 0) {
            System.out.println("Desired timer: ");
            System.out.println("Overall time: ");
        }
    }
}