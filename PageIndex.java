@SuppressWarnings("unchecked")
//Every Page have its own PageIndex; means a page has list of all words

public class PageIndex {
	
	MyLinkedList<WordEntry> WordEntries;
	public PageIndex() {
		this.WordEntries=new MyLinkedList<WordEntry>();
	}
	
	
	public void addPositionForWord(String str,Position p) throws LinkedListOutofBoundsException
	{
		int i=0;
		
		while(i<WordEntries.getSize())
		{
			if(WordEntries.get(i).data().getWord().equals(str))
			{
				WordEntries.get(i).data().addPosition(p);
				return;
			}
			
			i++;
		}
		
		WordEntry toAdd=new WordEntry(str);
		toAdd.addPosition(p);
		this.WordEntries.addElement(toAdd);
		
	}

	public MyLinkedList<WordEntry> getWordEntries()
	{
		return this.WordEntries;
	}
}
