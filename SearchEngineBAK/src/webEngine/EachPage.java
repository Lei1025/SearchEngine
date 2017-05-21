package webEngine;

public class EachPage implements Comparable<EachPage> {
	//java.util.Comparator as the interface comes from
	 
    /**
     * public interface Comparable<T> {
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     **/
	
	private final String pageName;
	private final int pageScore;
	
	public EachPage(final String name, final int score) {
		
		this.pageName = name;
		this.pageScore = score;
	}
	
	public String getPageName() 
	//getter
	{
		return pageName;
	}
	

	public int getPageScore() 
	//getter
	{
		return pageScore;
	}

	
	public String toString() {
		return String.format("%s, The score is =%d", pageName, pageScore);
	}

	@Override
	//public int compareTo(T o); which is the interface's method
	public int compareTo(EachPage to) 
	{
		//very important because this dooms the key part of pq,
		//this decided the way the search result comes to a decreasing of scores of each page
		
		//return -1;
		return to.pageScore - this.pageScore;
		// if other page's score more than this page. return negative
	}
}