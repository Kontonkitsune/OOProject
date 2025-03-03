import java.util.ArrayList;
import java.util.Stack;

/**
 * Takes an equation, and through a three-step process, calculates it.
 * 
 * @author Lucian Reiter (Azeria)
 */
public class Calculator {
	private final String equation;
	private final ArrayList<String> inFix;
	private final ArrayList<String> postFix;
	private final double result;

	/**
	 * Declares the calculator and calculates the equation through a three step process.
	 * 
	 * @param equation equation to be calculated
	 */
	public Calculator(String equation) {
		this.equation = equation;
		this.inFix = separate(equation);
		this.postFix = intopost(inFix);
		this.result = calcPostFix(postFix);
	}
	
	public String getEquation() {
		return this.equation;
	}
	
	/**
	 * Returns inFix as a formatted string.
	 * 
	 * @return inFix as formatted string
	 */
	public String getStringInFix() {
		String output = "";
		for (String x : this.inFix) {
			output += x + " ";
		}
		return output;
	}
	
	/**
	 * Returns postFix as a formatted string.
	 * 
	 * @return postFix as formatted string
	 */
	public String getStringPostFix() {
		String output = "";
		for (String x : this.postFix) {
			output += x + " ";
		}
		return output;
	}
	
	public ArrayList<String> getInFix() {
		return this.inFix;
	}
	
	public ArrayList<String> getPostFix() {
		return this.postFix;
	}
	
	public double getResult() {
		return this.result;
	}
	
	/**
	 * Interprets an equation as a string and separates it into a list of numbers and operator symbols.
	 * 
	 * @param input the equation
	 * @return list with every symbol and number separated
	 */
	private ArrayList<String> separate(String input) {
		int iterVar = 0;
		int iterVar2;
		String allowedChars = "()+-*/^";
		ArrayList<String> currentArray = new ArrayList<>();
		while (iterVar < input.length()) {
			
			
			if (Character.isDigit(input.charAt(iterVar))) {
				iterVar2 = iterVar;
				while (iterVar2 < input.length() && (Character.isDigit(input.charAt(iterVar2)) || input.charAt(iterVar2) == '.')) {
					iterVar2 += 1;
				}
				currentArray.add(input.substring(iterVar,iterVar2));
				iterVar = iterVar2;
			}
			else if (allowedChars.indexOf(input.charAt(iterVar)) != -1) {
				currentArray.add(input.substring(iterVar,iterVar+1));
				iterVar += 1;

			}
			else {
				iterVar += 1;
			}
			
		}
		return currentArray;
	}
	
	/**
	 * Returns the priority of a given operator symbol.
	 * 
	 * @param input operator symbol
	 * @return priority value for use in inToPost()
	 */
	private int getPriority(String input) {
		if ("^".contains(input)) {
			return 3;
		}
		else if ("*/".contains(input)) {
			return 2;
		}
		else if ("+-".contains(input)) {
			return 1;
		}
		else if ("(".contains(input)) {
			return 0;
		}
		else {
			return -1;
		}
	}

	/**
	 * Converts infix (human-readable equation) to postfix (machine-readable equation).
	 * 
	 * @param input list of symbols in infix order
	 * @return list of symbols in postfix order
	 */
	private ArrayList<String> intopost(ArrayList<String> input) {
		Stack<String> stack = new Stack<>();
		ArrayList<String> output = new ArrayList<>();
		int iterVar = 0;
		boolean flag;
		String currentSymbol;

		String allowedChars = "+-*/^";
		
		while (iterVar < input.size()) {
			currentSymbol = input.get(iterVar);
			if (currentSymbol.matches("-?\\d+(\\.\\d+)?")) {
				output.add(currentSymbol);
			}
			else if (allowedChars.contains(currentSymbol)) {
				while (!stack.isEmpty() && getPriority(stack.peek()) >= getPriority(currentSymbol)) {
					output.add(stack.pop());
				}
				stack.push(currentSymbol);
			}
			else if ("(".equals(currentSymbol)) {
				stack.push("(");
			}
			else if (")".equals(currentSymbol)) {
				flag = true;
				while (flag) {
					if (!stack.isEmpty()) {
						currentSymbol = stack.pop();
						if (allowedChars.contains(currentSymbol)) {
							output.add(currentSymbol);
						} 
						else {
							flag = false;
						}
					}
					else {
						flag = false;
					}
				}
			}
			iterVar += 1;
		}
		while (!stack.isEmpty()) {
			output.add(stack.pop());
		}
		return output;
	}

	/**
	 * Calculates the final value of an equation in postfix.
	 * 
	 * @param postFix list of numbers symbols in postfix order
	 * @return final value of equation
	 */
	private double calcPostFix(ArrayList<String> postFix) {
		Stack<Double> stack = new Stack<>();
		int iterVar = 0;
		double operator;
		double operand;
		while (iterVar < postFix.size()) {
			if (postFix.get(iterVar).matches("-?\\d+(\\.\\d+)?")) {
				stack.push(Double.valueOf(postFix.get(iterVar)));
			}
			else {
				operand = stack.pop();
				operator = stack.pop();
				switch (postFix.get(iterVar)) {
					case "+" -> stack.push(operator + operand);

					case "-" -> stack.push(operator - operand);

					case "*" -> stack.push(operator * operand);

					case "/" -> stack.push(operator / operand);

					case "^" -> stack.push(Math.pow(operator, operand));
				}
			}
			iterVar += 1;
		}
		return stack.pop();
	}
}
