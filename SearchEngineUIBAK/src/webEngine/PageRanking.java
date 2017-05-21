package webEngine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

//1. Inverted index
//Write a program that using the 100 given Web pages constructs an inverted index.
//The words (keys) are stored in a trie.
//The value of each word is the index to a list of occurrences of that word in each Web page. 
//You can keep the lists of occurrences in a two-dimensional array as explained in class. 
//
//
//2. Web search engine
//Write a program that given a keyword (a single word)
//It shows a list of Web pages in a ranking from high to low. 
//(sorted in decreasing order of occurrence of that word)
//
//3. String matching
//Write another program that implements the search index from another perspective. 
//Given a keyword and a web page
//The program should use string matching to find how many occurrences of that keyword are in a particular web page. 
//The program should do this for all Web pages, 
//and rank the pages based on then number of occurrences. 
//Note that the programs for string matching we’ve seen in class find the first occurrence only.
//You will have to modify the matching algorithm to find all occurrences (or at least to count them). 

public class PageRanking 
{
	private final static CreateWordDic wordDic = CreateWordDic.getInstatnce();
	//ranking step1: instance of word dictionary
	private final static Map<String, List<PageInfo>> wordDictionary = wordDic.getWordDictionary();
	
	private final static String[] dataArray = { "web", "link" };

	public static void main(String[] args) throws IOException {

		List<PageInfo> list = joinList(dataArray);
		for (int i = 0; i < list.size(); i++) 
		{
			//below has been Commented
			//below just all web pages result either contains web or link without being sorted
			// System.out.println(list.get(i).getPageName() + "--" +
			 //list.get(i).getFrequency());
		}
		Map<String, Integer> map = convertListToTable(list);
		List<EachPage> pageList = listTop10Pages(map);
		 System.out.println("shit");
		printTop10Pages(pageList);
	}

	/**
	 * Add the list of searching each string in word dictionary.
	 * 
	 * @param inputData
	 * @return Give a list that contains all the
	 */
	private static List<PageInfo> joinList(String[] inputData) {
		List<PageInfo> newList = new ArrayList<PageInfo>();
		for (String str : inputData) {
			if (wordDictionary.containsKey(str)) 
			{
				newList.addAll(wordDictionary.get(str));
				//addAll is a function belongs to the List.java  
				//remove the repeated elements.
				//repeated pages means that those pages have both words of input
				//http://blog.csdn.net/d3623301984/article/details/4234445
				
			}
		}
		return newList;
		//which contains all pageInfo (has the word searched), as a array list 
	}

	/**
	 * Print top 10 pages.
	 * 
	 * @param pageList
	 */
	private static void printTop10Pages(List<EachPage> pageList) 
	{
		for (int i = 0; i < pageList.size(); i++) 
		{
			System.out.println(pageList.get(i).getPageName() + "--" + pageList.get(i).getPageScore());
		}
	}

	/**
	 * Print pages with page name and page score.
	 * 
	 * @param map
	 */
	private static void printPageMap(Map<String, Integer> map) 
	{
		System.out.println("The elements in hash map");
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + "---" + map.get(key));
		}
	}

	/**
	 * Print the 10 pages which have the top highest score in descending order.
	 * 
	 * @param map
	 * @return Give a list of pages in descending order according to page score.
	 */
	private static List<EachPage> listTop10Pages(Map<String, Integer> map) {
		int counter = 0;
		PriorityQueue<EachPage> pq = pageSorting(map);
		List<EachPage> list = new ArrayList<EachPage>();
		while (!pq.isEmpty() && counter <= 10) {
			counter += 1;
			list.add(pq.poll());
		}
		return list;
	}

	public static List<EachPage> listTop10Pages(String[] args) {

		List<PageInfo> pagelist = joinList(args);
		//first join all the web pages contain either words of parameters args
		Map<String, Integer> map = convertListToTable(pagelist);

		int counter = 0;
		PriorityQueue<EachPage> pq = pageSorting(map);
		List<EachPage> list = new ArrayList<EachPage>();
		while (!pq.isEmpty() && counter <= 10) {
			counter += 1;
			list.add(pq.poll());
		}
		return list;
	}

	/**
	 * Count score of each page
	 * 
	 * @param list
	 * @return Give hash map which key is page name and value is page score
	 */
	private static Map<String, Integer> convertListToTable(List<PageInfo> list) {

		Map<String, Integer> table = new HashMap<String, Integer>();
		for (int i = 0; i < list.size(); i++) {

			if (!table.containsKey(list.get(i).getPageName())) {
				table.put(list.get(i).getPageName(), list.get(i).getFrequency());
			} else {
				Integer score = table.get(list.get(i).getPageName()) + list.get(i).getFrequency();
				table.put(list.get(i).getPageName(), score);
			}
		}

		return table;
	}

	/**
	 * Sorting page according to priority queue
	 * 
	 * @param table
	 * @return Give a queue with descending order according to page score
	 */
	private static PriorityQueue<EachPage> pageSorting(Map<String, Integer> table) {
		PriorityQueue<EachPage> pq = new PriorityQueue<EachPage>();
		Set<String> keys = table.keySet();
		for (String key : keys) {
			EachPage eachPage = new EachPage(key, table.get(key));
			pq.add(eachPage);
		}
		return pq;
	}

}
