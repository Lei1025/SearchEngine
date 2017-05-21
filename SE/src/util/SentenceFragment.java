package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SentenceFragment {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}


	//3. String matching
	//Write another program that implements the search index from another perspective. 
	//Given a keyword and a web page
	//The program should use string matching to find how many occurrences of that keyword are in a particular web page. 
	//The program should do this for all Web pages, 
	//and rank the pages based on then number of occurrences. 
	//Note that the programs for string matching we’ve seen in class find the first occurrence only.
	//You will have to modify the matching algorithm to find all occurrences (or at least to count them). 
	
	
	public static String pickFragment(String pageName, String[] words) throws IOException 
	{
		
		StringBuilder sb = new StringBuilder();
		Map<String, List<Integer>> result = new HashMap<String, List<Integer>>();
		//store the word and the position of the word in the file
		File file = FileHelper.getFileList(Paths.get("./data/Text").toString() + "/" + pageName).get(0);
		//get the file using the parameter which is the name of page
		String fileContent = FileHelper.readToBuffer(file);
		//convert the file to the string

		for (String word : words) 
		{
			// for loop central word as pattern
			SEKMP kmp = new SEKMP(word);
			//pass the word as pattern to be 
			//here is what are required as the string matching
			//KMP concept is the key to solve the fragment picking mission.
			result.put(word, kmp.searchAll(fileContent, 5));
			//show at most 5 results 
			//store them in the hash map， key is the word string and the value is the array list which stores the  position integers in the txt
		}

		for (String key : result.keySet()) 
			//for loops all the words of the user input
		{
			for (Integer pos : result.get(key)) 
			{//for loop all the positions of each word
				int wordCount = 50;
				//start form 100 words ahead the word
				if (pos - wordCount < 0) {
					pos = 0;
				} else {
					pos = pos - wordCount;
				}
				wordCount = pos + 2 * wordCount;
				sb.append(fileContent.substring(pos, wordCount)).append("...\n");
				//substring method 
				if(sb.length() > 10000){
					break;
				}
			}
			//this is the variable you can change and customize 
			if(sb.length() > 10000){
				break;
			}
		}

		return sb.toString();
	}

}
