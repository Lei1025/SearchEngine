package webEngine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import util.FileHelper;

public class HTMLToTxt {

	//1. Inverted index
	//Write a program that using the 100 given Web pages constructs an inverted index.
	//The words (keys) are stored in a trie.  
	//The value of each word is the index to a list of occurrences of that word in each Web page. 
	//You can keep the lists of occurrences in a two-dimensional array as explained in class. 
	 

	public static void main(String[] args) throws IOException {
		//exception of IOException+FileNotFoundException
		
		transformHTMLtoTXT();
	}

	 
	private static void transformHTMLtoTXT() 
			throws IOException, FileNotFoundException {
		List<File> fileList = FileHelper.getFileList("./data/W3C Web Pages");
		
		StringBuilder sb = new StringBuilder();
		for (File file : fileList) {
			org.jsoup.nodes.Document doc = Jsoup.parse(FileHelper.readToBuffer(sb, file));
			FileHelper.writeFile(doc.text().replaceAll("<", "").replaceAll(">", ""), "./data/Text/"
			+ file.getName().replace(".htm", ".txt"));
		}
		
	}


}
