package project1;

/**
 * 
 * File: Parser.java 
 * Author: Bedemariam Degef 
 * Date: April 10, 2020 
 * Purpose: This class is used to help get the file path
 * 
 */

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FileChooser {

	private FileFilter filter;

	/**
	 * 
	 * A constructor to initialize the file filter
	 * 
	 */
	public FileChooser() {

		filter = new FileNameExtensionFilter("file", "txt");

	}

	/**
	 * 
	 * Obtain the file path when appropriate file is selected
	 * 
	 * @return
	 */

	public String getFilePath() {

		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileFilter(filter);

		if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(null)) {

			return fileChooser.getSelectedFile().getAbsolutePath();
		}

		else {

			System.exit(0);

		}

		return "";

	}

}