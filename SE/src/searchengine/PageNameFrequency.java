package searchengine;

public class PageNameFrequency {
	private final String pageName;
	private int wordFrequency;

	public PageNameFrequency(final int frequency, final String pageName) 
	// first parameter as the times/occurances/appearance times of the word
	//second parameter as the name of the text(page)
	{

		this.pageName = pageName;
		this.wordFrequency = frequency;
	}

	public void setFrequency(int frequency) {
		this.wordFrequency = frequency;
	}
	
	public String getPageName() {
		return pageName;
	}

	public int getFrequency() {
		return wordFrequency;
	}
}
