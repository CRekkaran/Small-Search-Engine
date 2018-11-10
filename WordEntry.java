@SuppressWarnings("unchecked")

//Errors check : getPages -> CHECKED

//CONTAINS ALL POSITIONS FOR A PARTICULAR WORD

/////FUNCTIONS/////
//void addPosition(Position)
//void addPositions(MyLinkedList<Position>)
//MyLinkedList<Position> getAllPositionsForThisWord()
//String getWord()
///////////////////


public class WordEntry {
	
	private String word;
	private MyLinkedList<Position> positions;
	AVLTree avl;
	int inPages;
	
	public WordEntry(String word) {
		this.word=word;
		positions= new MyLinkedList<Position>();
		this.avl = new AVLTree();
		this.inPages = 0;
	}
	
	public WordEntry(String word,MyLinkedList<Position> l) throws LinkedListOutofBoundsException
	{
		this.word=word;
		this.positions=l;
		for(int i=0;i<l.getSize();i++){
			avl.insert(avl.root, l.get(i).data());
		}
	}
	
	public void addPosition(Position position)
	{
		positions.addElement(position);
		avl.insert(avl.root, position);
	}

	public void addPositions(MyLinkedList<Position> positions) throws LinkedListOutofBoundsException
	{
		//adding an entire list of new positions to be added
		Node<Position> tailOfToBeAdded=positions.get(positions.getSize()-1);
		tailOfToBeAdded.setNext(this.positions.head);
		this.positions.head=positions.head;
		for(int h=0;h<positions.getSize();h++){
			avl.insert(avl.root, positions.get(h).data());
		}
	}
////////////////////////////////////////////////////////////////////////////////	
	// SEE THE ERROR IN THIS :/ -> Not in the Mood

	// public MyLinkedList<PageEntry> getPages(){
	// 	MyLinkedList<PageEntry> few = new MyLinkedList<PageEntry>();
	// 	for(int i=0;i<positions.getSize();i++){
	// 		if(!few.contains(this.positions.get(i).getPageEntry())){
	// 			few.addElement(this.positions.get(i).data().getPageEntry());
	// 		}
	// 	}
	// 	return few;
	// }
////////////////////////////////////////////////////////////////////////////////
	public MyLinkedList<Position> getAllPositionsForThisWord()
	{
		return this.positions;
	}

	public String getWord()
	{
		return this.word;
	}
	public String toString(){
		return this.word;
	}
	
	

}
