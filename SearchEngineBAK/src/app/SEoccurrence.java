package app;
 
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
 
import util.FragmentPicker;
import webEngine.EachPage;
import webEngine.PageRanking;

public class SEoccurrence  {
	 
	//3. String matching
	//Write another program that implements the search index from another perspective. 
	//Given a keyword and a web page
	//The program should use string matching to find how many occurrences of that keyword are in a particular web page. 
	//The program should do this for all Web pages, 
	//and rank the pages based on then number of occurrences. 
	//Note that the programs for string matching we’ve seen in class find the first occurrence only.
	//You will have to modify the matching algorithm to find all occurrences (or at least to count them). 

	private static Scanner keyboardFunc;


	//not compile solve:
	//http://blog.csdn.net/huahuagongzi99999/article/details/7719882

	private void getSearchResult(String searchText) {
		 
		String[] words = searchText.split(" ");
		List<EachPage> searchResult = PageRanking.listTop10Pages(words);
		for (EachPage page : searchResult) {
			System.out.println("Search result in web page: "+page.getPageName()+" with searching score: "+page.getPageScore());
			
		 
			try {
				//show some of the fragment sentences containing those words
				//here the 
				System.out.println("Showing fragment sentences : "+FragmentPicker.pickFragment(page.getPageName(), words));
				
			 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		}

		 
	}
   

	public static void main(String[] args) {
		 
		
		boolean continueOrQuit=true;
		
		//conitue while loop or not ; sometimes the system exit when bad input happens
		while(continueOrQuit)
		{
			//the guide
			System.out.print("** My search engine application. Type the word or words with blank spliter. **\n   ");
			keyboardFunc = new Scanner(System.in);
			String getInputFunc = keyboardFunc.nextLine();
			 
			SEoccurrence	app= new SEoccurrence();
			app.getSearchResult(getInputFunc);
			
		}
		
	 	
		 
	}

}
