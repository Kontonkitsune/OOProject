import java.util.Scanner;
/**
 * Main code for a test run of the Calculator class.
 * 
 * @author Lucian Reiter (Azeria)
 */
public class CalculatorMain {
    
	/**
     * Continuously takes user input and attempts to calculate user inputted equations until the user types "exit".
     * 
	 * @param args unused
	 */
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
            while (true) { 
                System.out.print("Please enter an equation to calculate, or type \"exit\" to quit!\n>>> ");
                String userInput = scanner.nextLine();
                if (userInput.contains("exit")) {
                    break;
                }
                try {
                    Calculator calc = new Calculator(userInput);
                    
                    System.out.println("The expression is: " + calc.getStringInFix());

                    System.out.println("This is converted to: " + calc.getStringPostFix());

                    System.out.println("The result is: " + calc.getResult());
                } catch (Exception exc) {
                    System.out.println("This is not a valid expression!");
                }
            }
        }
    }
}
