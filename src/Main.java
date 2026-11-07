import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String[] args) {
        //Overall Timer
        Scanner scanner = new Scanner(System.in);

        System.out.print("How long are you studying in (minutes): ");
        int min = scanner.nextInt();
        int final = min * 60
        System.out.println("To start timer, type start");
        String start = scanner.nextLine();

        // to start the overall study timer: orgingal study duration with no pauses
        if (start.equalsIgnoreCase("start")) {
            // create the overall study timer
            Timer timer = new Timer();
            TimerTask = task = new TimerTask() {
                private int sec = 0;

                public void run() {
                    sec++;
                    if(sec == final) {
                        timer.cancel();
                    }
                }
            };
            // actually starts the overall study timer
            timer.scheduleAtFixedRate(task, 1000, 1000);

            // create the study timer
        }
        System.out.println("Press S to resume studying or B to take scroll break");
        //when user presses study/break, display timer of study time and break timer



        scanner.close();
        //producity report
        if(mins == 0) {
            System.out.println("Desired timer: ");
            System.out.println("Overall time: ");
        }
    }
}