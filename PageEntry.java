// ToDo Task :
// Get total number of pages to find relevance
// Get total no. of phrases in this page

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
@SuppressWarnings("unchecked")


public class PageEntry {

	BufferedReader br=null;
	//String pageData;
	PageIndex pgIndex;
	String pageName;
	int totalWords=0;
	int totalWordsWithoutConnectors;
	MyLinkedList<WordAndPosition> tuple = new MyLinkedList<WordAndPosition>();

	public PageEntry(String pageName) throws LinkedListOutofBoundsException, IOException,FileNotFoundException, PositionNotFoundException {
		// TODO Auto-generated constructor stub
		try {
			//File fileSource=new File("webpages/"+pageName);
			FileInputStream file=new FileInputStream("webpages/"+pageName);
			this.pageName=pageName;
			//pageData=" ";
			Scanner s=new Scanner(file);
			String connectors[]={"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
			int i=1;
			totalWordsWithoutConnectors = 0;
			pgIndex=new PageIndex();
			
			while(s.hasNext())
			{
				String toPut=s.next();

				totalWords += 1; //All words even if they are connectors are being accounted for total words
				boolean connectorFound=false;
				for(int c=0;c<connectors.length;c++)
				{

					if(toPut.equals(connectors[c]))
					{	
						connectorFound=true;
						break;
					}
				}
				if(connectorFound)
				{
					i++;
					//toPut = " ";
					continue;
				}
				else {
					totalWordsWithoutConnectors += 1;
					WordAndPosition temp = new WordAndPosition(this, new WordEntry(filter(toPut)), totalWordsWithoutConnectors-1);
					tuple.addElement(temp);
				}
				toPut=filter(toPut);
				String split[]=toPut.split(" ");
				int j=0;
				while(j<split.length && !connectorFound)
				{
					Position p=new Position(this, i);
					pgIndex.addPositionForWord(split[j], p);
					j++;
					i++;
				}
			}
			s.close();
			file.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.print("FileNotFound");
			//pageData=null;
			return;
		} catch(LinkedListOutofBoundsException e) {
			System.out.println(e);
		}

	}

	public PageIndex getPageIndex(){
		return this.pgIndex;
	}

	public String filter(String str){
		str = str.replaceAll("[\\-\\:\\^\\,\\.\\;\\'\\?\\!\\#\\<\\>\\[\\]\\=\\(\\)\\{\\}]", " ");
		str = str.replace('"', ' ');
		str = str.trim();
		str = str.toLowerCase();
		//ADDED "OPERATIONS"
		if(str.equals("stacks") || str.equals("structures") || str.equals("applications") || str.equals("operations")) 
			str = str.substring(0, str.length() - 1);

		return str;
	}

	public WordEntry getithWordEntry(int i) throws LinkedListOutofBoundsException {
		return this.pgIndex.WordEntries.get(i).data();
		
	}

	public WordEntry searchWordEntry(String sttr) throws LinkedListOutofBoundsException{
		for(int i=0;i<this.pgIndex.WordEntries.getSize();i++){
			if(this.pgIndex.WordEntries.get(i).data().getWord().equals(sttr))
				//System.out.println(this.pgIndex.WordEntries.get(i) + "JAHA");
				return this.pgIndex.WordEntries.get(i).data();
			// if(getithWordEntry(i).getWord().equals(sttr))
		}
		return null;
	}

	public int numberOfPagesContaingWord(String str)throws LinkedListOutofBoundsException{
		WordEntry temp = searchWordEntry(str);
		MyLinkedList<Position> tempy = temp.getAllPositionsForThisWord();
		MyLinkedList<PageEntry> finalPages = new MyLinkedList<PageEntry>();
		for(int i=0;i<tempy.getSize();i++){
			if(  !finalPages.contains(tempy.get(i).data().getPageEntry())  ){
				finalPages.addElement(tempy.get(i).data().getPageEntry());
			}
		}
		return finalPages.getSize();
	}

	public MyLinkedList<PageEntry> pagesContainingWord(String str) throws LinkedListOutofBoundsException {
		//System.out.println("HAHA");
		WordEntry temp = new WordEntry("a");
		//int tree = 0;
		temp = searchWordEntry(str);
		//if(temp==null){return null;}
		MyLinkedList<Position> tempy = temp.getAllPositionsForThisWord();
		MyLinkedList<PageEntry> finalPages = new MyLinkedList<PageEntry>();
		if(tempy!=null){	
			for(int i=0;i<tempy.getSize();i++){
				//System.out.println(finalPages.getSize());
				// if(finalPages.getSize() == 0){
				// 	finalPages.addElement(tempy.get(i).data().getPageEntry());
				// 	tree=1;
				// }


				if(  !finalPages.contains(tempy.get(i).data().getPageEntry()) ){
					//System.out.println("gfea");
					finalPages.addElement(tempy.get(i).data().getPageEntry());
					//tree = 1;
				}
			}
		}
		//System.out.println("HAFC");
		//if(tree == 0){System.out.println("No page contains " + str); return null;}
		return finalPages;
	}
	//*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/**/*/*/*/*/
	public int numberOfPagesContaingPhrase(String str[]) throws LinkedListOutofBoundsException {
		int crap = 0;
		MySet<PageEntry> gg = new MySet<PageEntry>();
		for(int ye=0;ye<pagesContainingWord(str[0]).getSize();ye++){
			gg.Insert(pagesContainingWord(str[0]).get(ye).data());
		}
		for(int i=1;i<str.length-1;i++){
			gg = gg.Intersection(new MySet(pagesContainingWord(str[i])));
		}
		//System.out.println("JAFSF" + gg.GetSize());
		for(int k=0;k<gg.GetSize();k++){
			if(gg.list().get(k).data().isPhrasePresent(str) == true){
				crap += 1;
			}
		}
		return crap;
	}
	//*/*/*/**/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/***/*/*/*/
	public String getPageName() {
		return this.pageName;
	}

	// CHECKED
	public float getFrequencyOfWordInThisPage(String str) throws LinkedListOutofBoundsException{ //get frequency of this word in this page
		return (float)(this.getTotalOccurenceOfWordInThisPage(str))/this.totalWords;
	}

	public int totalWordsIncludingConnectors(){
		return this.totalWords;
	}

	//CHECKED
	public int getTotalOccurenceOfWordInThisPage(String str) throws LinkedListOutofBoundsException {
		int count = 0;
		//Deisplaying all words from WordEntries
		MyLinkedList<WordEntry> wordsAll = this.pgIndex.WordEntries;
		for(int y=0;y<wordsAll.getSize();y++){
			for(int w=0;w<wordsAll.get(y).data().getAllPositionsForThisWord().getSize();w++) {
				//System.out.println(wordsAll.get(y).data().getWord());
				if(wordsAll.get(y).data().getWord().equals(str)){
					if(wordsAll.get(y).data().getAllPositionsForThisWord().get(w).data().getPageEntry().pageName.equals(this.pageName)){
						count++;
						//System.out.println(wordsAll.get(y).data().getAllPositionsForThisWord().get(w).data().getPageEntry().pageName);
					}
				}
			}
		}

		return count;
	}

	float getRelevanceOfPage(String str[], boolean doTheseWordsRepresentAPhrase) throws LinkedListOutofBoundsException{
		float relevance = 0;
		//System.out.println(doTheseWordsRepresentAPhrase);
		if(doTheseWordsRepresentAPhrase){
			//Phrase it is!
			//System.out.println((this.numberOfPagesContaingPhrase(str)));
			
			if(this.numberOfPagesContaingPhrase(str)!=0 && this.totalWords!=0 && (this.totalWords-(str.length-1)*noOfSuchPhrases(str))!=0)	
				relevance = (float)(Math.log(10/this.numberOfPagesContaingPhrase(str)))*noOfSuchPhrases(str)/(this.totalWords-(str.length-1)*noOfSuchPhrases(str));
			//System.out.println(this.numberOfPagesContaingPhrase(str));

		} else {
			//Its AND/OR
			MyLinkedList<WordEntry> words = pgIndex.getWordEntries();
            for(int i=0;i<str.length;i++) {
            	for(int ou=0;ou<words.getSize();ou++){
            		//System.out.println(words.get(ou).data().toString() + "HAHAsdv");
            		if(words.get(ou).data().toString().equals(str[i])){
            			//str[i] is in the word list
            			relevance += getFrequencyOfWordInThisPage(str[i])*Math.log(10/numberOfPagesContaingWord(str[i])); //COME BACK LATER : here number of pages is taken to be constant just bcoz idk what else to de
            		}
            	} 
            }

		}
		//System.out.println(relevance);
		return relevance;
	}

	//CHECKED
	public Boolean isPhrasePresent(String str[]) throws LinkedListOutofBoundsException{
		int flag = 1;
		for(int yt=0;yt<str.length;yt++){
			str[yt] = this.filter(str[yt]);
		}

		WordEntry firstWord = searchWordEntry(str[0]);
		
		AVLTree avl = firstWord.avl;
		MyLinkedList<NodeAVL> a = avl.giveSequence(avl);

		String[] newArr = new String[str.length];
		newArr[0] = str[0];
		//System.out.println(str.length);
		if(str.length>2){
			
			for(int t=0;t<str.length;t++){
				for(int q=0;q<getNextWord(str[t]).length;q++){
					newArr[t] = getNextWord(str[t])[q];
					if(newArr[t].equals(str[t])){
						flag = 1;
						//break;
					}
				}
			}
			if(flag==0){
				return false;
			}

		} else if(str.length==1) { // if only one word
			
			if(firstWord!=null){
				return true;
			} else {return false;}

		} else {				  // if two words in String
			//System.out.println("HAHA");
			for(int er=0;er<getNextWord(newArr[0]).length;er++){	
				//System.out.println(getNextWord(newArr[0])[er]);
				newArr[1] = getNextWord(newArr[0])[er];
				if(newArr[1].equals(str[1])){
					return true;
				}
			}
			for(int r=0;r<str.length;r++){
			if(!newArr[r].equals(str[r])){flag = 0;}
			}
			if(flag==0){
				return false;
			}

		}

		
		
		return true;

	}
    // CHECKED -> SEE THIS BELOW
	public String[] getNextWord(String str)throws NullPointerException, LinkedListOutofBoundsException{
		
		WordEntry word = searchWordEntry(str);
		if(word == null){
			throw new NullPointerException();
		}
		MyLinkedList<WordEntry> allWordsInThisPage =  pgIndex.WordEntries;

		//calculate no.of times str came in the page
		int nWords = getTotalOccurenceOfWordInThisPage(str);
		
		MyLinkedList<String> allNextWordsList = new MyLinkedList<String>();
		
		// PHASE - 1
		// int temp = 0;
		// for(int qwe=0;qwe<nWords;qwe++){
		// 	int u=0;
		// 	for(u=0;u<allWordsInThisPage.getSize();u++){
		// 		if(allWordsInThisPage.get(u).data() == word){
		// 			//break;//showing only the first occurence of the word ; sad :/
		// 			temp = 1;
		// 			//System.out.println("agdsgf");
		// 		}
		// 	}
		// 	if(temp==1){allNextWords[qwe] = allWordsInThisPage.get(u+1).data().getWord();}
		// }
		//System.out.println(word.getAllPositionsForThisWord().get(0).data().getPageEntry().pageName.equals(this.pageName));
		
		// for(int op=0;op<allWordsInThisPage.getSize();op++){
		// 	System.out.println(allWordsInThisPage.get(op).data().getWord());
		// }
		


		// PHASE - 2
		// for(int qwe=0;qwe<nWords;qwe++){
		// 	for(int i=0;i<word.getAllPositionsForThisWord().getSize();i++){
		// 		if(word.getAllPositionsForThisWord().get(i).data().getPageEntry().pageName.equals(this.pageName)){
		// 			//System.out.println("HAHA");
		// 			//allNextWords[qwe] = allWordsInThisPage.get(i+1).data().getWord();
		// 			for(int h=0;h<allWordsInThisPage.getSize();h++){
		// 				for(int hh=0;hh<allWordsInThisPage.get(h).data().getAllPositionsForThisWord().getSize();hh++){
		// 					if(allWordsInThisPage.get(h).data().getAllPositionsForThisWord().get(hh).data().getPageEntry().pageName.equals(this.pageName) && allWordsInThisPage.get(h).data().getAllPositionsForThisWord().get(hh).data().getWordIndex()== i+1){
		// 						allNextWords[qwe] = allWordsInThisPage.get(i+1).data().getWord();
		// 					}
		// 				}
		// 			}
		// 		}
		// 	}
		// }

		// PHASE - 3 -> FINALLY DONE :/
		
		int id=0;
		while(id<tuple.getSize()){
			//System.out.println(tuple.get(id).data().giveWordEntry().getWord().equals(word.getWord()));
			if(tuple.get(id).data().giveWordEntry().getWord().equals(word.getWord())){
				
				if(tuple.getSize()!=id+1)
					allNextWordsList.addElement(tuple.get(id+1).data().giveWordEntry().getWord());
			}
			id++;
		}
		
		String[] allNextWords = new String[nWords];

		for(int ty=0;ty<allNextWordsList.getSize();ty++){
			allNextWords[ty] = allNextWordsList.get(ty).data();
		}
		
		
		

		return allNextWords;
	}

	public int noOfSuchPhrases(String str[]) throws LinkedListOutofBoundsException{
		// String[] a = new String[getTotalOccurenceOfWordInThisPage(str[0])];
		// for(int e=0;e<a.length;e++){
		// 	for(int u=0;u<tuple.getSize();u++){
		// 		if(tuple.get(u).data().giveWordEntry().getWord().equals(str[0])){
		// 			if()
		// 		}
		// 	}
		// }
		if(!isPhrasePresent(str)){return 0;}
		int flash = 0;
		if(str.length>1){
			for(int e=0;e<tuple.getSize();e++){
				if(tuple.get(e).data().giveWordEntry().getWord().equals(str[0])){
					if(tuple.getSize()!=e+1){
						if(tuple.get(e+1).data().giveWordEntry().getWord().equals(str[1])){
							//System.out.println("HAHA");
							flash += 1;
						}
					}
				}
			}
		} else if(str.length==1){
			flash = getTotalOccurenceOfWordInThisPage(str[0]);
		} else {return 0;}
		return flash;
	}

	// public static void main(String[] args) {
	// 	try{
	// 		PageEntry p = new PageEntry("stack_datastructure_wiki");
	// 		//PageEntry w = new PageEntry("studytonight");
	// 		if(p==null ){
	// 			throw new LinkedListOutofBoundsException();
				
	// 		}
	// 		else{
	// 			//System.out.println(p.pgIndex.getWordEntries().getSize());
	// 			String[] ass = new String[2];
	// 			ass[0] = "data";
	// 			ass[1] = "structure"; // CHECKED -> Somehow operations word is not seen by the code "PLS CHECK IT NOW"

	// 			//System.out.println(p.searchWordEntry("karan"));
				

	// 			System.out.println(p.numberOfPagesContaingPhrase(ass));
	// 			// int y = p.noOfSuchPhrases(ass);
	// 			//System.out.println(w.pagesContainingWord("stafack").get(0).data().pageName);
	// 			//System.out.println(w.numberOfPagesContaingWord("jgk"));

	// 			//System.out.println(y);

	// 		}
	// 	} catch(LinkedListOutofBoundsException e){}
	// 	catch(IOException e){}
	// 	catch(PositionNotFoundException e){}
	// 	catch(NullPointerException e){}
		
	// }
}
