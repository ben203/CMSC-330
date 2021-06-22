package project1;

/**
 * 
 * File: Parser.java 
 * Author: Bedemariam Degef 
 * Date: April 10, 2020 
 * Purpose:This class contains the main method that will launch the program 
 * 
 */

import java.io.IOException;

public class Main {

	/**
	 * 
	 * Process the file and construct the GUI
	 * 
	 * @param args
	 * @throws IOException
	 * @throws SyntaxErrorException
	 */
	public static void main(String[] args) throws IOException, SyntaxErrorException {

		Lexer lexer = new Lexer(new FileChooser().getFilePath());
		Parser parser = new Parser(lexer);
		parser.parse();
	}

}