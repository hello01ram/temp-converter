import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        double temp = 0.0;
        double results = 0.0;
        boolean convertToCelsius = true;
        try {
            temp = getTemperature();
            convertToCelsius = convertToCelsius();
        } catch(InputMismatchException e) {
            System.out.println("Invalid input");
            scanner.nextLine();
        }
        results = convert(convertToCelsius, temp);
        System.out.println("Results is " + results);
    }
    private static double getTemperature() {
        System.out.print("Enter your temperature: ");
        return scanner.nextDouble();
    }

    private static boolean convertToCelsius() {
        System.out.println("Convert to: ");
        System.out.println("1-Celsius\n2-Fahrenheit");
        System.out.print("Your selection: ");
        int isCelsius = scanner.nextInt();
        return isCelsius != 2 ? true : false;
    }

    private static double convert(boolean toCelsius, double temp) {
        return toCelsius ? ((temp - 32) * 5/9) : ((temp * 9/5) + 32);
    }
}