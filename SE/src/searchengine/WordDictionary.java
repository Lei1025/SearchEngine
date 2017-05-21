package searchengine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class WordDictionary {

	private final static String spliter = " ";
	private final Integer COUNTER = 1;
	private final static Path DIR = Paths.get("./data/Text");
	private final static Map<String, List<PageNameFrequency>> wordDictionary = new HashMap<>();
	//store the word dictionary in the hash map, key is the string(), value is the list
	private final static WordDictionary createWordDic = new WordDictionary();
	//static means that this is for this class, not belongs to each instance
	

	public static WordDictionary getInstatnce() {
		return createWordDic;
	}

	private WordDictionary() {
		try {
			initializeWordDictionary(createFileList(DIR));
			//firstly you have to tell the program your 100 files of web page texts
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<File> createFileList(Path dir) 
	{
		List<File> fileList = new ArrayList<File>();
		//array list for store all the files
		try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.txt")) 
		//get all files which are .txt 
		{
			for (Path file : stream) {
				fileList.add(file.toFile());
				// for loop can never throw IOExcetiopn
			}

		} 
		catch (IOException | DirectoryIteratorException x) 
		{
			
			System.err.println(x);
		}
		return fileList;
	}

	/**
	 * Store each string in string array into hashtable
	 * 
	 * @param str1
	 * @return
	 */
	private Map<String, Integer> storeWordInHashtable(String[] str1) {
		Map<String, Integer> table1 = new HashMap<String, Integer>();
		for (String str : str1) 
		{
			//for loop all the string array members, 
			if (!table1.containsKey(str)) 
			{
				table1.put(str, COUNTER);
			} else 
			{
				Integer newValue = table1.get(str) + 1;
				//get how many times this word has appered in this file
				table1.put(str, newValue);
			}
		}
		return table1;
	}

	/**
	 * Save the word which meet the requirement to a string array. only the
	 * letters and numbers are allowed
	 * 
	 * @param br
	 * @param sb
	 * @return
	 * @throws IOException
	 */
	private String[] fetchWord(File filename) throws IOException 
	{
		//digest the file into string array, which contains all words of the file
		InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
		BufferedReader br = new BufferedReader(reader);
		StringBuilder sb = new StringBuilder();
		int tmpchar;
		while ((tmpchar = br.read()) != -1) 
		{
			if ((char) tmpchar != '\n' && (char) tmpchar != ' ') 
			{
				if (isDigit((char) tmpchar) || isAlphabit((char) tmpchar)) 
				{
					sb.append((char) tmpchar);
				}
			} 
			else 
			{
				sb.append(spliter);
			}
		}
		String[] str1 = sb.toString().split("\\s+");
		br.close();
		return str1;
	}

	public static boolean isDigit(char tmpchar) 
	{//check for Digit
		return tmpchar >= '0' && tmpchar <= '9';
	}

	public static boolean isAlphabit(char tmpchar) 
	{//check for Alphabit
		return (tmpchar >= 'A' && tmpchar <= 'Z') || (tmpchar >= 'a' && tmpchar <= 'z');
	}

	public static void main(String[] args) throws IOException {
		//entrance of the class
		final WordDictionary wordDic = WordDictionary.getInstatnce();
		//step1 :

		Map<String, List<PageNameFrequency>> aaa = wordDic.getWordDictionary();

		Set<String> keys = aaa.keySet();
		for (String key : keys) {
			System.out.println(key + "---" + aaa.get(key).size());
			for (int i = 0; i < aaa.get(key).size(); i++) 
			{
				System.out.println(aaa.get(key).get(i).getPageName() + "--"
						+ aaa.get(key).get(i).getFrequency());
			}

		}

	}

	public Map<String, List<PageNameFrequency>> getWordDictionary() 
	{
		Map<String, List<PageNameFrequency>> map = new HashMap<>();
		map.putAll(wordDictionary);
		return map;
	}

	private void initializeWordDictionary(List<File> fileList) throws IOException 
	//parameter filelist is array list and the members are type of file
	{
		// digest all the texts which are from the step1(converting html to txt)
		for (File file : fileList) {
			String[] str1 = fetchWord(file);
			Map<String, Integer> table1 = storeWordInHashtable(str1);
			//table1 means that:
			//this step is for get the hashtable storing(string,times)
			Set<String> keys = table1.keySet();
			//System.out.println(file);
			for (String key : keys) {
				//System.out.println(key + "----" + table1.get(key));

				PageNameFrequency pi = new PageNameFrequency(table1.get(key), file.getName());
				//new PageInfo(3,The WebSocket API.txt)
				//which means that pi stores :key which is word string "web" appears 3 times in The WebSocket API.txt
				if (!wordDictionary.containsKey(key)) 
				{//  if the wordDictionary which is type of hashmap ,has not the key of the word.
					//then store the new word as the key to the hashtable which is the word dictionary
					List<PageNameFrequency> pageList = new ArrayList<PageNameFrequency>();
					//because the word can appear in different files(web pages), so we need to initialize a array list
					// for storing all files contain this word. and also the occurrences in each file.at least count them
					pageList.add(pi);
					//add the element to the whole wordDictionary which is a hashtable value 
					wordDictionary.put(key, pageList);
					//first time put the new word as the key to the hashtable wordDicgtionary
				} else {
					List<PageNameFrequency> pageList = wordDictionary.get(key);
					if (!pageList.contains(pi)) {
						pageList.add(pi);
					}
				}
			}
		}
	}

}
