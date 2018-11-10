import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
@SuppressWarnings("unchecked")

public class SearchEngine{

	InvertedPageIndex inv;
	public SearchEngine() {
		inv=new InvertedPageIndex();
	}

	public void addPage(String filename) throws LinkedListOutofBoundsException, IOException, PositionNotFoundException
	{
		PageEntry p=new PageEntry(filename);

		if(p.pgIndex==null)
		{
			throw new IOException();
		}
		inv.addPage(p);
	}

	public PageEntry[] findPagesWhichContainWord(String word) throws LinkedListOutofBoundsException,WordNotFoundException
	{

		if(word==null)
		{
			throw new WordNotFoundException();
		}
		MySet<PageEntry> ret=inv.getPagesWhichContainWord(word); //String word -> WordEntry -> listofAllPostions -> list of respective PageEntry
		if(ret==null)
		{
			return null;
		}
		//Now all pageNames will be retrieved from ret
		PageEntry list[]=new PageEntry[ret.GetSize()];
		int i=0;
		int listC=0; // No Use till Assignment 3
		while(i<ret.GetSize())
		{
			PageEntry temp=ret.getIthElement(i);

			int count;
			for(count=0;count<listC;count++)
				if(list[count].pageName.equals(temp.pageName))
					break;
			if(count!=listC)
			{
				i++;
				continue;
			}
			list[listC]=temp;
			i++;
			listC++;
		}
////////////////////////////////////////////////////////////////////////////////////////////////
		return list;
	}

	public void positionsWhereWordIsInWebpage(String word,String pageName) throws LinkedListOutofBoundsException, IOException,WordNotFoundException, PositionNotFoundException
	{
		if((new PageEntry(pageName)).pgIndex==null)
		{
			throw new IOException();
		}

		String word2=filter(word);
		if(word2==null)
		{
			throw new WordNotFoundException();
		}
		WordEntry we=inv.invertedIndex.get(word2); //get wordEntry from it :/
		if(we==null)
		{
			//System.out.println("Word "+word+"/"+word2+" not found in "+pageName);
			System.out.println("Webpage " + pageName + " does not contain word " + word);
			return;
		}

		Node<Position> nodee=we.getAllPositionsForThisWord().head;
		System.out.print("Position(s) of "+word+"/"+word2+" in "+pageName+": ");
		int count=0;
		while(nodee.data()!=null)
		{
			if(nodee.data().getPageEntry().pageName.equals(pageName))
			{
				System.out.print(nodee.data().getWordIndex()+",");
				count++;
			}
			nodee=nodee.next;
		}
		if(count==0)
		{
			System.out.println("File is not added");
		}
		else
		System.out.println();
	}

	public String filter(String str)
	{
		String connectors[]={"a","an","the","they","these","this","for","is","are","was","of","or","and","does","will","whose"};
		boolean connectorFound=false;
		for(int c=0;c<connectors.length;c++)
		{
			if(str.equals(connectors[c]))
			{	
				connectorFound=true;
				break;
			}
		}
		if(connectorFound)
		{
			return null;
		}	
		str = str.replaceAll("[\\-\\:\\^\\,\\.\\;\\'\\?\\!\\#\\<\\>\\[\\]\\=\\(\\)\\{\\}]", "");
		str = str.replace('"', ' ');
		str = str.trim();
		str = str.toLowerCase();
		// if(str.endsWith("s")) 
		// 	str = str.substring(0, str.length() - 1);

		return str;
	}

	// public PageEntry[] getPagesWhichContainAllWords(String arr[]) throws LinkedListOutofBoundsException {
	// 	PageEntry[] a1 = getPagesWhichContainWord(arr[0]);
	// 	for(int l=1;l<arr.length-1;l++){
	// 		a1 = arrayIntersect(a1, getPagesWhichContainWord(arr[l]));
	// 	}
	// 	return a1;
	// }

	// public PageEntry[] getPagesWhichContainAnyWord(String arr[]) throws LinkedListOutofBoundsException {

	// }

	// public PageEntry[] arrayIntersect(PageEntry[] a, PageEntry[] b){
	// 	MyLinkedList<PageEntry> ask = new MyLinkedList<PageEntry>();
	// 	for(int i=0;i<a.length;a++){
	// 		for(int j=0;j<b.length;j++){
	// 			if(a[i] == b[j]){ask.addElement(a[i]);}
	// 		}
	// 	}
	// 	String[] dude = new String[ask.getSize()];
	// 	for(int u=0;u<ask.getSize();u++){
	// 		dude[u] = ask.get(i).data();
	// 	}
	// 	return dude;
	// }

	public void andOrHandling(String arr[], int flag) throws LinkedListOutofBoundsException, EmptySetException, WordNotFoundException{
		MySet<PageEntry> array[]=new MySet[arr.length-1];
		
		for(int i=1;i<arr.length;i++)
		{
			PageEntry a[]=findPagesWhichContainWord(arr[i]);
			if(a==null)
			{
				throw new WordNotFoundException();
			}
			MySet<PageEntry> temp=new MySet<PageEntry>();
			for(int j=0;j<a.length && a[j]!=null;j++)
			{
				temp.Insert(a[j]);
			}
			array[i-1]=temp;
		}

		MySet<PageEntry> set=new MySet<PageEntry>();
		for(int c=0;c<array[0].GetSize();c++)
		{
			set.Insert(array[0].getIthElement(c));
		}
		for(int i=1;i<array.length;i++)
		{
			if(flag==1)
				set.Intersection(array[i]);
			else if(flag==0)
				set.Union(array[i]);
		}

		MySet<SearchResult> ret=new MySet<SearchResult>();
		for(int i=0;i<set.GetSize();i++)
		{
			// double score=0;
			// PageIndex pg=set.getIthElement(i).getPageIndex();
			// for(int j=1;j<arr.length;j++)
			// {
			// 	score+=pg.getScore(arr[j]);
			// }
			// SearchResult temp=new SearchResult(set.getIthElement(i), score);
			// ret.Insert(temp);

			//double score = 0;
			//System.out.println((float)(set.list().get(i).data().getRelevanceOfPage(arr, true)));
			SearchResult temp = new SearchResult(set.getIthElement(i), set.list().get(i).data().getRelevanceOfPage(arr, false));
			ret.Insert(temp);
		}

		MySort s=new MySort();
		ArrayList<SearchResult> r=s.sortThisList(ret);

		for(int i=0;i<r.size();i++)
		{
			System.out.print(r.get(i).getPageEntry().pageName+",");
		}
		System.out.println();
	}
	

	public void pagesWhichContainPhrase(String arr[]) throws LinkedListOutofBoundsException, WordNotFoundException
	{
		MySet<PageEntry> array[]=new MySet[arr.length-1];
		//System.out.println(arr.length);
		for(int i=1;i<arr.length;i++) // CREATING set of arrays, each array having PageEntry corresponding to each element in arr
		{
			PageEntry a[]=findPagesWhichContainWord(arr[i]);
			MySet<PageEntry> temp=new MySet<PageEntry>();
			//System.out.println(a.length);
			if(a==null)
			{
				throw new WordNotFoundException();
			}
			for(int j=0;j<a.length && a[j]!=null;j++)
			{
				//System.out.println(a[j].pageName);
				temp.Insert(a[j]);
			}
			
			array[i-1]=temp;
			//System.out.println(array[i-1].GetSize());
		}

		MySet<PageEntry> set=new MySet<PageEntry>();
		for(int c=0;c<array[0].GetSize();c++)
		{
			set.Insert(array[0].getIthElement(c));
		}
		//System.out.println(set.GetSize());
		for(int i=1;i<array.length;i++)
		{
			set = set.Intersection(array[i]);
		}
		//System.out.println(set.GetSize());
		if(set.GetSize()==0)
		{
			System.out.println("No match found");
			return;
		}
		
		MySet<SearchResult> ret=new MySet<SearchResult>(); //final pages not in order
		Node<PageEntry> it=set.getHead();
		while(it.data()!=null)
		{
			/////////////////THIS IS CHECKED//////////////////////********
			float score=it.data().getRelevanceOfPage(arr, true);
			//System.out.println(score);
			if(score!=0)
			{
				SearchResult s=new SearchResult(it.data(), score);
				ret.Insert(s);
			}
			it=it.next;
		}
		//System.out.println(ret.GetSize() +" ** ");
		if(ret.GetSize()==0)
		{
			System.out.println("No match found");
			return;
		}
		
		MySort srt=new MySort();
		ArrayList<SearchResult> list=srt.sortThisList(ret); //final pages in order

		for(int i=0;i<list.size();i++)
		{
			System.out.print(list.get(i).getPageEntry().pageName +", ");
		}
		System.out.println();
	}



	public void performAction(String actionMessage){
		String actions[]=actionMessage.split(" ");
		try{
			if(actions[0].equals("addPage"))
			{
				addPage(actions[1]);
			}
			else if(actions[0].equals("queryFindPagesWhichContainWord"))
			{
				PageEntry list[]=findPagesWhichContainWord(filter(actions[1]));
				if(list==null)
				{
					return;
				}
				String ans = actions[0] + " " + actions[1]+" : ";
				//ans = ans +"Pages which contain '"+actions[1]+"' : ";
				//System.out.print("Pages which contain '"+actions[1]+"' : ");
				for(int i=0;i<list.length && list[i]!=null ;i++)
				{
					ans = ans + list[i].pageName + ", ";
					//System.out.print(list[i].pageName+",");
				}
				System.out.print(ans.substring(0,ans.length()-2));
				System.out.println();
			}
			else if(actions[0].equals("queryFindPositionsOfWordInAPage"))
			{
				// System.out.print(actions[0] + ": ");
				positionsWhereWordIsInWebpage(actions[1], actions[2]);
			}
			else if(actions[0].equals("queryFindPagesWhichContainAllWords"))
			{
				System.out.print("Pages which contain all of these '");
				for(int i=1;i<actions.length;i++)
				{
					System.out.print(actions[i]+" ");
				}
				System.out.print("': ");
				andOrHandling(actions,1);
			}
			else if(actions[0].equals("queryFindPagesWhichContainAnyOfTheseWords"))
			{
				System.out.print("Pages which contain any of these '");
				for(int i=1;i<actions.length;i++)
				{
					System.out.print(actions[i]+" ");
				}
				System.out.print("': ");
				andOrHandling(actions, 0);
			}
			else if(actions[0].equals("queryFindPagesWhichContainPhrase"))
			{
				System.out.print("Pages which contain phrase '");
				for(int i=1;i<actions.length;i++)
				{
					System.out.print(actions[i]+" ");
				}
				System.out.print("': ");
				String[] af = new String[actions.length-1];
				for(int l=0;l<af.length;l++){af[l] = actions[l+1];}
				this.pagesWhichContainPhrase(af);
			}	
			else
			{
				System.out.println("Invalid Input");
			}
		}
		catch(LinkedListOutofBoundsException e)
		{
			e.printMessage();
		}
		catch(IOException e)
		{
			System.out.println("IOException");
		}
		catch(WordNotFoundException e)
		{
			e.printMessage(actions[1]);
		}
		catch(EmptySetException e)
		{
			e.printMessage();
		}
		catch(PositionNotFoundException e)
		{
			e.printMessage();
		}
	}
	// public static void main(String[] args) {
	// 	SearchEngine s = new SearchEngine();
	// 	try{	
	// 		s.addPage("studytonight");
	// 		s.addPage("geeks_quiz");
	// 		s.addPage("auckland_stack");
	// 		s.addPage("cmu_stack");
	// 		s.addPage("stack_cprogramming");
	// 		s.addPage("stack_datastructure_wiki");
	// 		s.addPage("stacklighting");
	// 		s.addPage("stackmagazine");
	// 		s.addPage("stack_oracle");
	// 		s.addPage("stackoverflow");
	// 		s.addPage("stacks_and_queues");

	// 		String[] x = new String[2];
	// 		x[0]="data";
	// 		x[1] = "structure";
	// 		//s.andOrHandling(x, 0);
	// 		//s.pagesWhichContainPhrase(x);
	// 		s.performAction("queryFindPagesWhichContainPhrase data structure");
	// 	} catch(LinkedListOutofBoundsException e){}
	// 	catch(IOException e){}
	// 	catch(PositionNotFoundException e){}
	// 	//catch(EmptySetException e){}
	// 	//catch(WordNotFoundException e){}
	// }
}
