
public class Position {
	
	//Tuple of PageEntry p and WordIndex in page p// :/
	private PageEntry p;
	private int WordIndex;
	//create constructore with arguement
	public Position(PageEntry p,int wordIndex) {
		
		this.p=p;
		this.WordIndex=wordIndex;
	}
	public PageEntry getPageEntry(){
		return p;
	}
	public int getWordIndex(){
		return WordIndex;
	}
	public void setWordIndex(int a){
		this.WordIndex=a;
	}
	public void setPageEntry(PageEntry pp){
		this.p = pp;
	}
}
