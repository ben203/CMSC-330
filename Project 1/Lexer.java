package project1;

/**
 * 
 * File: Parser.java 
 * Author: Bedemariam Degef 
 * Date: April 10, 2020 
 * Purpose:This is the lexer class used to obtain tokens  
 * 
 */

import java.io.*;

public class Lexer {
	private static final int KEYWORDS = 11;
	private StreamTokenizer tokenizer;
	private String punctuation = ",:;.()";

	/**
	 * 
	 * Punctuation tokens from the grammar definition
	 * 
	 */
	private Token[] punctuationTokens = { Token.COMMA, Token.COLON, Token.SEMICOLON, Token.PERIOD, Token.LEFT_PAREN,
			Token.RIGHT_PAREN };

	/**
	 * 
	 * Constructor that creates a lexical analyzer object given the source file
	 * 
	 * @param fileName
	 * @throws FileNotFoundException
	 */

	public Lexer(String fileName) throws FileNotFoundException {
		tokenizer = new StreamTokenizer(new FileReader(fileName));
		tokenizer.ordinaryChar('.');
		tokenizer.quoteChar('"');
	}

	/**
	 * 
	 * Returns the next token in the input stream
	 * 
	 * @return
	 * @throws SyntaxErrorException
	 * @throws IOException
	 */

	public Token getNextToken() throws SyntaxErrorException, IOException {
		int token = tokenizer.nextToken();
		switch (token) {
		case StreamTokenizer.TT_NUMBER:
			return Token.NUMBER;
		case StreamTokenizer.TT_WORD:
			for (Token aToken : Token.values()) {
				if (aToken.ordinal() == KEYWORDS)
					break;
				if (aToken.name().equals(tokenizer.sval.toUpperCase()))
					return aToken;
			}
			throw new SyntaxErrorException(lineNo(), "Invalid token " + getLexeme());
		case StreamTokenizer.TT_EOF:
			return Token.EOF;
		case '"':
			return Token.STRING;
		default:
			for (int i = 0; i < punctuation.length(); i++)
				if (token == punctuation.charAt(i))
					return punctuationTokens[i];
		}
		return Token.EOF;
	}

	/**
	 * 
	 * Returns the lexeme associated with the current token
	 * 
	 * @return
	 */

	public String getLexeme() {

		return tokenizer.sval;
	}

	/**
	 * 
	 * Returns the numeric value of the current token for numeric tokens
	 * 
	 * @return
	 */

	public double getValue() {

		return tokenizer.nval;
	}

	/**
	 * 
	 * Returns the current line of the input file
	 * 
	 * @return
	 */

	public int lineNo() {
		return tokenizer.lineno();
	}
}