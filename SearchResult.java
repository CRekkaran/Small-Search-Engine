
public class SearchResult implements Comparable<SearchResult>{
	
	PageEntry p;
	double r;
	
	public SearchResult(PageEntry p,double r)
	{
		this.p=p;
		this.r=r;
	}
	
	public PageEntry getPageEntry()
	{
		return p;
	}
	
	public double getRelevance()
	{
		return r;
	}
	
	public int compareTo(SearchResult otherObject) {
		if(otherObject.r>this.r){return 1;}
		else if(otherObject.r<this.r){return -1;}
		else return 0;
	}



}
