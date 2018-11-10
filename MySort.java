import java.util.ArrayList;
import java.util.Collections;

public class MySort {
	public ArrayList<SearchResult> sortThisList(MySet<SearchResult> listOfSearchResultEntries) throws LinkedListOutofBoundsException {
		ArrayList<SearchResult> trek = new ArrayList<SearchResult>();
		for(int i=0;i<listOfSearchResultEntries.GetSize();i++){
			if(trek.contains(listOfSearchResultEntries.getIthElement(i))){
				continue;
			}
			trek.add(listOfSearchResultEntries.getIthElement(i));
		}
		Collections.sort(trek);
		return trek;
	}
}