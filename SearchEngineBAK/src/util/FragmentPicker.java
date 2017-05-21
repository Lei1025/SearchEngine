package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentPicker {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static String pickFragment(String pageName, String[] words) throws IOException {
		//
		StringBuilder sb = new StringBuilder();
		Map<String, List<Integer>> result = new HashMap<String, List<Integer>>();
		File file = FileHelper.getFileList(Paths.get("./data/Text").toString() + "/" + pageName).get(0);
		//get the file using the parameter which is the name of page
		String fileContent = FileHelper.readToBuffer(file);
		//convert the file to the string

		for (String word : words) {
			// central word
			MyKMP kmp = new MyKMP(word);
			//KMP concept is the key to solve the fragment picking mission.
			result.put(word, kmp.searchAll(fileContent, 5));
		}

		for (String key : result.keySet()) {
			for (Integer pos : result.get(key)) {
				int wordCount = 80;
				if (pos - wordCount < 0) {
					pos = 0;
				} else {
					pos = pos - wordCount;
				}
				wordCount = pos + 2 * wordCount;
				sb.append(fileContent.substring(pos, wordCount)).append("...\n");
				if(sb.length() > 300){
					break;
				}
			}
			if(sb.length() > 300){
				break;
			}
		}

		return sb.toString();
	}

}
