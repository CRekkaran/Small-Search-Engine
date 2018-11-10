public class WordAndPosition {
	WordEntry word;
	Position position;
	WordAndPosition(PageEntry p, WordEntry w, int pos){
		this.word = w;
		this.position = new Position(p, pos);
	}
	public Position givePosition(){
		return this.position;
	}
	public WordEntry giveWordEntry(){
		return this.word;
	}
	public void setThePosition(PageEntry p, int pos){
		this.position = new Position(p, pos);
	}
}