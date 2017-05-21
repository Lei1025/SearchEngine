package searchengine;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class RankingResult 
{
	private final static WordDictionary wordDic = WordDictionary.getInstatnce();
	//ranking step1: instance of word dictionary
	private final static Map<String, List<PageNameFrequency>> wordDictionary = wordDic.getWordDictionary();
	
	private final static String[] dataArray = { "web", "link" };

	public static void main(String[] args) throws IOException {

		List<PageNameFrequency> list = joinList(dataArray);
		for (int i = 0; i < list.size(); i++) 
		{
			//below has been Commented
			//below just all web pages result either contains web or link without being sorted
			// System.out.println(list.get(i).getPageName() + "--" +
			 //list.get(i).getFrequency());
		}
		Map<String, Integer> map = convertListToTable(list);
		List<PageNameScore> pageList = listTop10Pages(map);
		 System.out.println("shit");
		printTop10Pages(pageList);
	}

	/**
	 * Add the list of searching each string in word dictionary.
	 * 
	 * @param inputData
	 * @return Give a list that contains all the
	 */
	private static List<PageNameFrequency> joinList(String[] inputData) {
		List<PageNameFrequency> newList = new ArrayList<PageNameFrequency>();
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
	private static void printTop10Pages(List<PageNameScore> pageList) 
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
	private static List<PageNameScore> listTop10Pages(Map<String, Integer> map) {
		int counter = 0;
		PriorityQueue<PageNameScore> pq = pageSorting(map);
		List<PageNameScore> list = new ArrayList<PageNameScore>();
		while (!pq.isEmpty() && counter <= 10) {
			counter += 1;
			list.add(pq.poll());
		}
		return list;
	}

	public static List<PageNameScore> listTop10Pages(String[] args) {

		List<PageNameFrequency> pagelist = joinList(args);
		//first join all the web pages contain either words of parameters args
		Map<String, Integer> map = convertListToTable(pagelist);

		int counter = 0;
		PriorityQueue<PageNameScore> pq = pageSorting(map);
		List<PageNameScore> list = new ArrayList<PageNameScore>();
		while (!pq.isEmpty() && counter <= 10) {
			counter += 1;
			list.add(pq.poll());
		}
		return list;
	}

	 
	private static Map<String, Integer> convertListToTable(List<PageNameFrequency> list) {
		//calculate score
		//transform all of the PageInfo instances which are stored in the list to be 
		//hash map with key of the page name and value of the score 
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
	private static PriorityQueue<PageNameScore> pageSorting(Map<String, Integer> table) {
		PriorityQueue<PageNameScore> pq = new PriorityQueue<PageNameScore>();
		Set<String> keys = table.keySet();
		for (String key : keys) {
			PageNameScore eachPage = new PageNameScore(key, table.get(key));
			pq.add(eachPage);
		}
		return pq;
	}

}
