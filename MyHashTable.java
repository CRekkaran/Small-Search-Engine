@SuppressWarnings("unchecked")

public class MyHashTable {

	int actualSize;
	private int maxArraySize;
	MyLinkedList<WordEntry> list[];


	public MyHashTable(int n){

		actualSize=0;
		list=new MyLinkedList[n];
		

		this.maxArraySize=n;

		for(int i=0;i<this.maxArraySize;i++)
		{
			list[i]=new MyLinkedList<WordEntry>();
		}
	}

	public long hashedValue(String key)
	{
		long hashCode=0;

		String str=key;
		int i=0;
		long factor=1;
		while(i<str.length())
		{
			hashCode+=((int)(str.charAt(i)-'a'))*factor;
			factor*=33;
			i++;
		}

		if(( (37*hashCode+41)%maxArraySize )<0)
		{
			return -(37*hashCode+41+maxArraySize)%maxArraySize;
		}

		return (37*hashCode+41)%maxArraySize;
	}

	public boolean isEmpty()
	{
		return actualSize==0;
	}

	public int findValue(String key)
	{
		int hash=(int)hashedValue(key);

		//returns the index in the table for the hashed value of the key

		if(list[hash].getSize()==0)
		{
			return -1;
		}

		return hash;
	}

	public WordEntry get(String key) //gives Position of Word in the array of bucket else -1 if size of bucket = 0
	{

		if(findValue(key)==-1)
		{
			return null;//the key was not found
		}

		int index=findValue(key);
		Node<WordEntry> it=list[index].head;
		//gets the element 

		while(it.data()!=null && !it.data().getWord().equals(key)  )
		{
			it=it.next;
		}

		if(it.data()==null)
		{
			return null;
		}

		else
		{
			return it.data();
		}

	}

	public void put(WordEntry value)
	{
		int indexToBeAddedAt=(int) hashedValue(value.getWord());
		list[indexToBeAddedAt].addElement(value);
		actualSize++;
		return;

	}

	public void addPositionsForWord(WordEntry value) throws LinkedListOutofBoundsException
	{
		String phrase[]=value.getWord().split(" ");
		int i=0;
		for (String element : phrase) {
			if(element.equals(" ") || element.equals("")) continue;
			WordEntry entry=get(element);
			if(entry==null)
			{	
				MyLinkedList<Position> l=value.getAllPositionsForThisWord();
				WordEntry toPut=new WordEntry(element);

				Node<Position> it=l.head;
				while(it.data()!=null)
				{
					toPut.addPosition(new Position(it.data().getPageEntry(),it.data().getWordIndex() + i ));
					it=it.next;
				}
				put(toPut);
				i++;

			}
			else
			{
				Node<Position> it=value.getAllPositionsForThisWord().head;
				while(it.data()!=null)
				{
					entry.addPosition(new Position(it.data().getPageEntry(), it.data().getWordIndex() +i));
					it=it.next;
				}
				i++;

			}
		}

		return ;
	}

	// public static void main(String[] args) {
	// 	MyHashTable h = new MyHashTable(10);
	// 	WordEntry word1 = new WordEntry("Karan");
	// 	try{h.addPositionsForWord(word1);}
	// 	catch(LinkedListOutofBoundsException e){}
	// 	System.out.println(h.get("Karan").getWord());
	// }

}
