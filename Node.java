
public class Node<T> {
	
	private T data;
	Node<T> next;
	public Node() {
		next=null;
	}
	public Node(T data){
		this.data=data;
		next=null;
	}
	public Node<T> getNext(){
		return this.next;
	}
	public T data(){
		return this.data;
	}
	public void setNext(Node<T> next){
		this.next=next;
	}

}
