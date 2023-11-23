package lab10ctdl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class MyLIFO_App {
// This method reserves the given array
	public static <E> void reserve(E[] array) {
		Stack<E> re = new Stack<>();
		for (int i = 0; i < array.length; i++) {
			re.push(array[i]);
		}
		for (int i = 0; i < array.length; i++) {
			array[i] = re.pop();
		}
	}

// This method checks the correctness of the given input

// i.e. ()(())[]{(())} ==> true, ){[]}() ==> false

	public static boolean isCorrect(String input) {
		Stack<Character> re = new Stack<Character>();
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '{' || input.charAt(i) == '(' || input.charAt(i) == '[') {
				re.push(input.charAt(i));
			} else if (!re.isEmpty() && ((input.charAt(i) == ']' && re.peek() == '[')
					|| (input.charAt(i) == '}' && re.peek() == '{') || (input.charAt(i) == ')' && re.peek() == '('))) {
				re.pop();
			} else {
				re.push(input.charAt(i));
			}
		}
		if (re.isEmpty())
			return true;
		return false;
	}
// This method evaluates the value of an expression

// i.e. 51 + (54 *(3+2)) = 321

	public int evaluate(String exp) {
		Stack<Integer> operands = new Stack<>(); // Operand stack
		Stack<Character> operations = new Stack<>(); // Operator stack
		for (int i = 0; i < exp.length(); i++) {
			char c = exp.charAt(i);
			if (Character.isDigit(c)) // check if it is number
			{
				// Entry is Digit, and it could be greater than a one-digit number
				int num = 0;
				while (Character.isDigit(c)) {
					num = num * 10 + (c - '0');
					i++;
					if (i < exp.length()) {
						c = exp.charAt(i);
					} else {
						break;
					}
				}
				i--;
				operands.push(num);
			} else if (c == '(') {
				operations.push(c); // push character to operators stack
			}
			// Closed brace, evaluate the entire brace
			else if (c == ')') {
				while (operations.peek() != '(') {
					int output = performOperation(operands, operations);
					operands.push(output); // push result back to stack
				}
				operations.pop();
			}

			// current character is operator
			else if (isOperator(c)) {
				while (!operations.isEmpty() && precedence(c) <= precedence(operations.peek())) {
					int output = performOperation(operands, operations);
					operands.push(output); // push result back to stack
				}
				operations.push(c); // push the current operator to stack
			}
		}

		while (!operations.isEmpty()) {
			int output = performOperation(operands, operations);
			operands.push(output); // push final result back to stack
		}
		return operands.pop();
	}

	static int precedence(char c) {
		switch (c) {
		case '+':
		case '-':
			return 1;
		case '*':
		case '/':
			return 2;
		case '^':
			return 3;
		}
		return -1;
	}

	public int performOperation(Stack<Integer> operands, Stack<Character> operations) {
		int a = operands.pop();
		int b = operands.pop();
		char operation = operations.pop();
		switch (operation) {
		case '+':
			return a + b;
		case '-':
			return b - a;
		case '*':
			return a * b;
		case '/':
			if (a == 0) {
				System.out.println("Cannot divide by zero");
				return 0;
			}
			return b / a;
		}
		return 0;
	}

	public boolean isOperator(char c) {
		return (c == '+' || c == '-' || c == '/' || c == '*' || c == '^');
	}

	// method stutter that accepts a queue of integers as
	// a parameter and replaces
	// every element of the queue with two copies of that
	// element
	// front [1, 2, 3] back
	// becomes
	// front [1, 1, 2, 2, 3, 3] back
	public static void stutter (Queue<Integer> q) {
		int size = q.size();
		for (int i = 0; i < size; i++) {
			int line = q.remove();
			q.add(line);
			q.add(line);
		}
	}
	

	// Method mirror that accepts a queue of strings as a
	// parameter and appends the
	// queue's contents to itself in reverse order
	// front [a, b, c] back
	// becomes
	// front [a, b, c, c, b, a] back
	public static Queue<Integer> mirrorQueue(Queue<Integer> queue) {
        Queue<Integer> mirroredQueue = new LinkedList<>();
 
        // Append the elements of the original queue in reverse order to the mirrored queue
        while (!queue.isEmpty()) {
            int element = queue.poll();
            mirroredQueue.add(element);
            mirroredQueue.add(element);
        }
 
        return mirroredQueue;
    }

}

