package project1;

/**
 * 
 * File: Parser.java 
 * Author: Bedemariam Degef 
 * Date: April 10, 2020 
 * Purpose:An exception class thrown for invalid syntax 
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class SyntaxErrorException extends Exception {

	private JFrame frame;

	/**
	 * 
	 * Display the error message
	 * 
	 * @param line
	 * @param description
	 */
	public SyntaxErrorException(int line, String description) {
		JOptionPane.showMessageDialog(frame, "Line: " + line + " description: " + description, "syntax error",
				JOptionPane.ERROR_MESSAGE);

	}

}
