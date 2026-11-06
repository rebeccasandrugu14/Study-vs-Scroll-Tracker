import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How long are you studying in (minutes): ");
        int min = scanner.nextInt();
        System.out.println("To start timer, type start");
        String start = scanner.nextLine();
        if (start.equalsIgnoreCase("start")) {
            // start the overall timer & study timer
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