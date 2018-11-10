//import java.io.IOException;
//import java.util.Scanner;
@SuppressWarnings("unchecked")

public class InvertedPageIndex {

	MyHashTable invertedIndex;
	private int totalIntPages;

	public InvertedPageIndex() {
		invertedIndex=new MyHashTable(800);
		this.totalIntPages = 0;
	}

	public void addPage(PageEntry p) throws LinkedListOutofBoundsException
	{
		Node<WordEntry> it=p.pgIndex.WordEntries.head;

		while(it.data()!=null)
		{
			invertedIndex.addPositionsForWord(it.data());
			it=it.next;
		}
		totalIntPages += 1;
	}

	public MySet<PageEntry> getPagesWhichContainWord(String str)
	{
		WordEntry we=invertedIndex.get(str);
		if(we==null)
		{
			System.out.println("No webpage contains word " + str);
			return null;
		}

		MySet<PageEntry> ret=new MySet<PageEntry>();
		MyLinkedList<Position> pos=we.getAllPositionsForThisWord();
		Node<Position> it=pos.head;
		while(it.data()!=null)
		{
			ret.Insert(it.data().getPageEntry());
			it=it.next;
		}

		return ret;
	}
	// public int numberOfPagesContainingWord(String str) {
	// 	MySet<PageEntry> peet = this.getPagesWhichContainWord(str);
	// 	return peet.getSize();
	// }
	///////////////////////////////////////////////////////////////////////////
	// public MySet<PageEntry> getPagesWhichContainPhrase(String str[]) {
	
	// 	String w1 = str[0];
	// 	String w2 = str[1];
	// 	// MySet<PageEntry> p1 = getPagesWhichContainWord(w1);
	// 	// MySet<PageEntry> p2 = getPagesWhichContainWord(w2);

	// 	WordEntry ww1 = this.invertedIndex.get(w1);
	// 	WordEntry ww2 = this.invertedIndex.get(w2);		

	// 	MyLinkedList<Position> www1 = ww1.getAllPositionsForThisWord();
	// 	MyLinkedList<Position> www2 = ww2.getAllPositionsForThisWord();

	// 	MySet<PageEntry> ret = new MySet<PageEntry>();

	// 	for(int i=0;i<www1.getSize();i++){
	// 		for(int j=0;j<www2.getSize();j++){
	// 			if(www1.get(i).getPageEntry().pageName.equals(www2.get(j).getPageEntry().pageName)){
	// 				if(  abs(  www1.get(i).getWordIndex() - www2.get(j).getWordIndex()  ) == 1  ){
	// 					ret = ret.Union(www1.get(i).getPageEntry());
	// 				}
	// 			}
	// 		}
	// 	}
	// 	return ret;
	// }
	//////////////////////////////////////////////////////////////////////////
	public static MySet<PageEntry> ret = new MySet<PageEntry>();
	// public void rec(PageEntry p1, String ww2){
		
	// 	//PageEntry p1 = tree1.key.getPageEntry();
	// 	String pageName = p1.pageName;
	// 	FileInputStream file=new FileInputStream("webpages/"+pageName);
	// 	Scanner s=new Scanner(file);
	// 	String connectors[]={"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
	// 	int i=1;
	// 	while(s.hasNext()) {
	// 		String toPut=s.next();
	// 		for(int ii=0;ii<connectors.length;ii++){
	// 			if(!toPut.equals(connectors[ii])){
	// 				break;
	// 			}else {continue;}
	// 		}
	// 	}
	// 	if(ww2.equals(s.next())){
	// 		ret.Insert(p1);
	// 	}
			
	// }

	public MySet<PageEntry> getPagesWhichContainAllWord(String str[]) throws LinkedListOutofBoundsException {
		if(str.length>0){
			MySet<PageEntry> possiblePages = getPagesWhichContainWord(str[0]);
			for (int i=1;i<str.length;i++)
            {
                possiblePages = possiblePages.Intersection(getPagesWhichContainWord(str[i]));
                if(possiblePages.GetSize() == 0)
                {
                    break;
                }
            }
            if(possiblePages.GetSize() > 0)
            {
                return possiblePages;
            }
		}
		return null;
	}

	public MySet<PageEntry> getPagesWhichContainPhrase(String str[]) throws LinkedListOutofBoundsException{
		// WordEntry ww1 = this.invertedIndex.get(str[0]);
		// WordEntry ww2 = this.invertedIndex.get(str[1]);
		// AVLTree tree1 = ww1.avl;
		// NodeAVL it = tree1.root;
		// MyLinkedList<NodeAVL> ava = tree1.giveSequence();
		// for(int k=0;k<ava.getSize();k++){
		// 	rec(ava.get(k), ww2);
		// }
		// return ret;
		MySet<PageEntry> possiblePages = getPagesWhichContainAllWord(str);
		if(possiblePages != null) {
			MyLinkedList<PageEntry> pages = possiblePages.list();
			Node<PageEntry> it = pages.head;
			MySet<PageEntry> validPages = new MySet<PageEntry>();
			while(it!=null){
				if(it.data().isPhrasePresent(str)!=null){
					validPages.Insert(it.data());
				}
				it = it.next;
			}
			if(validPages.GetSize() > 0)
            {
                return validPages;
            }
		}
		return null;
	}

	public MySet<PageEntry> getPagesWhichContainAnyWord(String str[]) throws LinkedListOutofBoundsException, EmptySetException {
		if(str.length > 0)
        {
            MySet<PageEntry> possiblePages = getPagesWhichContainWord(str[0]);
            for (int i=1;i<str.length;i++)
            {
                possiblePages = possiblePages.Union(getPagesWhichContainWord(str[i]));
                if(possiblePages.GetSize() == 0)
                {
                    break;
                }
            }
            if(possiblePages.GetSize() > 0)
            {
                return possiblePages;
            }
        }
        return null;
	}

	// public PageEntry getPageEntry(int i){
	// 	return (PageEntry)this.pages.returnList().get(i);
	// }
		

}
