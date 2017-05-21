package app;
 
import java.io.IOException;
import java.util.List;
import java.util.Scanner;
 
import util.FragmentPicker;
import webEngine.EachPage;
import webEngine.PageRanking;

public class SEranking  {
	 

	//2. Web search engine
	//Write a program that given a keyword (a single word)
	//It shows a list of Web pages in a ranking from high to low. 
	//(sorted in decreasing order of occurrence of that word)
	

	private static Scanner keyboardFunc;



	private void showPages(String searchText) {
		 
		String[] words = searchText.split(" ");
		List<EachPage> searchResult = PageRanking.listTop10Pages(words);
		for (EachPage page : searchResult) {
			System.out.println("Search result in web page: "+page.getPageName()+" with searching score: "+page.getPageScore());
			 
			 
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
			 
			SEranking	app= new SEranking();
			app.showPages(getInputFunc);
			
		}
		
	 	
		 
	}

}
