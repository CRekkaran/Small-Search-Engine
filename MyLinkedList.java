@SuppressWarnings("unchecked")
public class MyLinkedList<T> {
	
	Node<T> head;
	private int size;
	
	public MyLinkedList() {
		head=new Node<T>();
		size=0;
	}
	public int getSize(){
		return size;
	}
	public MyLinkedList(Node<T> top){	
		this.head=top;
		size=1;
	}
	// public Node<T> search(T){
	// 	Node<T> it = this.head
	// 	for(int i=0;i<size;i++){
	// 		if()
	// 	}
	// }
	public void addElement(T data)
	{
		Node<T> node=new Node<T>(data);
		node.setNext(head);
		head=node;
		size +=1;
	}
	public void remove(T node){
		
		if(node.equals(this.head.data()))
		{
			head=head.next;
			size -= 1;
			return;
		}		
		Node<T> curr=this.head;
		while(!curr.next.data().equals(node))
		{
			curr=curr.next;
		}
		
		
		curr.next=curr.next.next;
		size--;
	}
	public Boolean isEmpty()
	{
		if(size==0)
		{
			return true;
		}
		return false;
	}
	//Sort this contains -> DONE
	public boolean contains(T node){
		int i=0;
		Node<T> curr=head;
		while(i<size && !curr.data().equals(node))
		{
			curr=curr.next;
			i++;
		}
		
		if(i==size)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	// public boolean contains(Node<T> node){
	// 	int i=0;
	// 	Node<T> curr=head;
	// 	while(i<size && !curr.equals(node))
	// 	{
	// 		curr=curr.next;
	// 		i++;
	// 	}
		
	// 	if(i==size)
	// 	{
	// 		return false;
	// 	}
	// 	else
	// 	{
	// 		return true;
	// 	}
	// }
	
	public Node<T> get(int i) throws LinkedListOutofBoundsException {
		Node<T> curr=this.head;
		if(i>=size)
		{
			throw new LinkedListOutofBoundsException();
		}
		int count=size-i-1;
		while(count>0){
			curr=curr.next;
			count--;
		}
		return curr;
	}
}
