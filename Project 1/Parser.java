package project1;

/**
 * 
 * File: Parser.java 
 * Author: Bedemariam Degef 
 * Date: April 10, 2020 
 * Purpose:This is the parser class used to evaluate tokens   
 * 
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Parser {
	private Lexer lexer;
	private Token expectedToken;
	private Token token;
	private JFrame frame;
	private String string;
	private ButtonGroup group;

	/**
	 * 
	 * Constructor to initialize the lexer
	 * 
	 * @param lexer
	 */
	public Parser(Lexer lexer) {
		this.lexer = lexer;
	}

	/**
	 * 
	 * Creates the GUI for a valid syntax
	 * 
	 * @throws IOException
	 * @throws SyntaxErrorException
	 */

	public void parse() throws IOException, SyntaxErrorException {
		token = lexer.getNextToken();
		if (parseGUI()) {
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		} else {

			throw new SyntaxErrorException(lexer.lineNo(), " Expected Token " + expectedToken + " not " + token);
		}
	}

	/**
	 * 
	 * Token evaluator
	 * 
	 * @param evalToken
	 * @return
	 * @throws IOException
	 * @throws SyntaxErrorException
	 */

	public boolean evalToken(Token evalToken) throws IOException, SyntaxErrorException {
		if (token == evalToken) {
			switch (token) {
			case BUTTON:
			case END:
			case FLOW:
			case GRID:
			case LABEL:
			case LAYOUT:
			case PANEL:
			case RADIO:
			case TEXTFIELD:
			case WINDOW:
			case COMMA:
			case COLON:
			case LEFT_PAREN:
			case RIGHT_PAREN:
				token = lexer.getNextToken();
				break;
			case GROUP:
				group = new ButtonGroup();
				token = lexer.getNextToken();
				break;
			case STRING:
				string = lexer.getLexeme();
				token = lexer.getNextToken();
				break;
			case SEMICOLON:
				token = lexer.getNextToken();
			case EOF:
				break;
			case NUMBER:
				break;
			case PERIOD:
				break;
			default:
				break;
			}
			return true;
		} else {
			expectedToken = evalToken;
			return false;
		}
	}

	/**
	 * 
	 * Adds the panels when valid tokens are used
	 * 
	 * @return
	 * @throws IOException
	 * @throws SyntaxErrorException
	 */
	public boolean parseGUI() throws IOException, SyntaxErrorException {
		int width;
		int height;
		if (evalToken(Token.WINDOW)) {
			if (evalToken(Token.STRING)) {
				frame = new JFrame(string);

				JPanel window = new JPanel();
				if (evalToken(Token.LEFT_PAREN)) {
					if (evalToken(Token.NUMBER)) {
						width = (int) lexer.getValue();
						token = lexer.getNextToken();
						if (evalToken(Token.COMMA)) {
							if (evalToken(Token.NUMBER)) {
								height = (int) lexer.getValue();
								token = lexer.getNextToken();
								if (evalToken(Token.RIGHT_PAREN)) {
									frame.setSize(width, height);
									window.setSize(width, height);
									frame.add(window);
									if (parseLayout(window)) {
										if (parseWidgets(window)) {
											if (evalToken(Token.END)) {
												return evalToken(Token.PERIOD);
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * Check if correct tokens are used for the layout nonterminal
	 * 
	 */
	public boolean parseLayout(Container container) throws IOException, SyntaxErrorException {
		if (evalToken(Token.LAYOUT)) {
			if (parseLayoutType(container)) {
				return evalToken(Token.COLON);
			}
		}
		return false;
	}

	/**
	 * 
	 * Check if correct tokens are used for the layout type nonterminal
	 * 
	 */
	public boolean parseLayoutType(Container container) throws IOException, SyntaxErrorException {
		int rows;
		int cols;
		int hgap;
		int vgap;
		if (evalToken(Token.FLOW)) {
			container.setLayout(new FlowLayout());
			return true;
		} else if (evalToken(Token.GRID)) {
			if (evalToken(Token.LEFT_PAREN)) {
				if (evalToken(Token.NUMBER)) {
					rows = (int) lexer.getValue();
					token = lexer.getNextToken();
					if (evalToken(Token.COMMA)) {
						if (evalToken(Token.NUMBER)) {
							cols = (int) lexer.getValue();
							token = lexer.getNextToken();
							if (evalToken(Token.RIGHT_PAREN)) {
								container.setLayout(new GridLayout(rows, cols));
								return true;
							} else if (evalToken(Token.COMMA)) {
								if (evalToken(Token.NUMBER)) {
									hgap = (int) lexer.getValue();
									token = lexer.getNextToken();
									if (evalToken(Token.COMMA)) {
										if (evalToken(Token.NUMBER)) {
											vgap = (int) lexer.getValue();
											token = lexer.getNextToken();
											if (evalToken(Token.RIGHT_PAREN)) {
												container.setLayout(new GridLayout(rows, cols, hgap, vgap));
												return true;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * Check if correct nonterminal are used for the widgets nonterminal
	 * 
	 */
	public boolean parseWidgets(Container container) throws IOException, SyntaxErrorException {
		if (parseWidget(container)) {
			if (parseWidgets(container)) {
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 * 
	 * Check if correct tokens are used for the widget nonterminal
	 * 
	 */
	public boolean parseWidget(Container container) throws IOException, SyntaxErrorException {
		int cols;
		if (evalToken(Token.BUTTON)) {
			if (evalToken(Token.STRING)) {
				if (evalToken(Token.SEMICOLON)) {
					container.add(new JButton(string));
					return true;
				}
			}
		} else if (evalToken(Token.GROUP)) {
			if (parseRadioButtons(container)) {
				return endWidget();
			}
		} else if (evalToken(Token.LABEL)) {
			if (evalToken(Token.STRING)) {
				if (evalToken(Token.SEMICOLON)) {
					container.add(new JLabel(string));
					return true;
				}
			}
		} else if (evalToken(Token.PANEL)) {
			container.add(container = new JPanel());
			if (parseLayout(container)) {
				if (parseWidgets(container)) {
					return endWidget();
				}
			}
		} else if (evalToken(Token.TEXTFIELD)) {
			if (evalToken(Token.NUMBER)) {
				cols = (int) lexer.getValue();
				token = lexer.getNextToken();
				if (evalToken(Token.SEMICOLON)) {
					container.add(new JTextField(cols));
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * Check if correct tokens are used for the end widget nonterminal
	 * 
	 */
	public boolean endWidget() throws IOException, SyntaxErrorException {
		if (evalToken(Token.END)) {
			return evalToken(Token.SEMICOLON);
		}
		return false;
	}

	/**
	 * 
	 * Check if correct nonterminal are used for the RadioButtons nonterminal
	 * 
	 */

	public boolean parseRadioButtons(Container container) throws IOException, SyntaxErrorException {
		if (parseRadioButton(container)) {
			if (parseRadioButtons(container)) {
				return true;
			}
			return true;
		}
		return false;
	}

	/**
	 *
	 * Check if correct tokens are used for the RadioButton nonterminal
	 * 
	 */
	public boolean parseRadioButton(Container container) throws IOException, SyntaxErrorException {
		if (evalToken(Token.RADIO)) {
			if (evalToken(Token.STRING)) {
				if (evalToken(Token.SEMICOLON)) {
					JRadioButton rButton = new JRadioButton(string);
					container.add(rButton);
					group.add(rButton);
					return true;
				}
			}
		}
		return false;
	}
}