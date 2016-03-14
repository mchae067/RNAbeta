//import java.io.File;
import java.util.*;
//import java.io.FileNotFoundException;

public class RNABasePair {

		public static void main(String args[]) {
			
		/* //Command line/file input (unused)
			if(args.length > 1){
	    		System.out.println("invalid command-line arguements");
	    		System.exit(1);
	    	}
	        Scanner in = null;
	    	if(args.length == 1){
	    		File inFile = new File(args[0]);
	    		if (!inFile.exists() || !inFile.canRead()) {
	    		    System.err.println("Problem with input file!");
	    		    System.exit(1);
	    		 }
	    		 try {
					in = new Scanner(inFile);
				} catch (FileNotFoundException e) {
					System.out.println("File not found!");
				}	*/
			
			
			//Test sequence: CAGAUCGGCGAUACGAGCAUACAAUGCUAAGCGAGCUUAGCUGCA
			//Manual input
			Scanner scnr = new Scanner(System.in);
			System.out.print("Input RNA String: ");
			String RNA = scnr.next();
			
			//Constructs the RNA chain of nodes
			String[] sequence = RNA.split("(?!^)");
			ArrayList<Node> strand = new ArrayList<Node>();
			Node head = new Node(sequence[0], 1);
			strand.add(head);
			for (int i=1; i<sequence.length; i++) {
				try {
					Node node = new Node(sequence[i], i+1);
					strand.add(node);
				}
				catch (ArrayIndexOutOfBoundsException e) {
					Node node = new Node(sequence[i], i+1);
					strand.add(node);
				}
			}
			
			//Compute and print results
			int basePairs = optimize(strand);
			System.out.println("Number of base pairs: "+basePairs);
			//BPSEQ format
			for (int i=0; i<strand.size(); i++) {
				Node node = strand.get(i);
				if (node.getBasePartner()==null) {
					System.out.println(node.getIndex()+" "+node.getNucleotide()+" 0");
					}
				else {
					System.out.println(node.getIndex()+" "+node.getNucleotide()+" "
							+node.getBasePartner().getIndex());
				}
			}
			//Bracket format
			for (int i=0; i<strand.size(); i++) {
				Node node = strand.get(i);
				if (node.hasBasePartner() 
						&& node.getIndex()<node.getBasePartner().getIndex()) {
					System.out.print("(");
				}
				else if (node.hasBasePartner() 
						&& node.getIndex()>node.getBasePartner().getIndex()) {
					System.out.print(")");
				}
				else {
					System.out.print(".");
				}
			}
		}
		
		//Method for determining the optimal pairings of Nodes
		private static int optimize(ArrayList<Node> RNA) {

			int[][] opt = new int[RNA.size()+1][RNA.size()+1];
			
			
			for (int k=5;k<=RNA.size()-1;k++ ) {
				for (int i=1;i<=RNA.size()-k;i++) {
					int j = i+k;
					if (i>=j-4) {
						opt[i][j] = 0;
					}
					else {
						opt[i][j] = Math.max(opt[i][j-1], 
								computeMax(i,j,RNA,opt));
					}
				}
			}
			int basePairs = opt[1][RNA.size()];
			align(RNA);
			return basePairs;
		}
			
		//Computes the maximum t between i and j, only for when t and j can pair
		private static int computeMax(int i, int j, ArrayList<Node> RNA, int[][] opt) {
				
			int maximum  = 0;
				
			for (int t=j-5;t>=i;t--) {
				if (RNA.get(i-1).canPair(j-1,RNA)) {
					int current = 1+opt[i][t-1] + 
								opt[t+1][j-1];
					if (current>maximum) {
						maximum = current;
					}
				}
			}
			return maximum;
		}
		
		//Makeshift align method.  Inserts blank node where match does not occur
		public static void align(ArrayList<Node> RNA) {
			for (int i=0;i<RNA.size()-5;i++) {
				if (RNA.get(i).canPair(RNA.size()-i-1, RNA)) {
					RNA.get(i).setBasePartner(RNA.get(RNA.size()-i-1));
					RNA.get(RNA.size()-i-1).setBasePartner(RNA.get(i));
				}
				else {
					Node blank = new Node("-", RNA.size()-i-1);
					RNA.add(RNA.size()-i-1, blank);
				}
			}
		}
			
		/*//Aligns the RNA sequence to form a secondary structure (unused)
		private static void align(ArrayList<Node> RNA) {
						
			//Initiate Strings to be aligned (RNA and RNA reversed)
			StringBuffer buffer1 = new StringBuffer(RNA.size());
			for (int i=0;i<RNA.size();i++) {
				buffer1.append(RNA.get(i).getNucleotide());
			}
			String string1 = buffer1.toString();
			StringBuffer buffer2 = new StringBuffer(RNA.size());
			for (int i=RNA.size()-1;i>=0;i--) {
				buffer2.append(RNA.get(i).getNucleotide());
			}
			String string2 = buffer2.toString();
			
			//Create variables to hold values
			int[][] opt = new int[RNA.size()][RNA.size()];
			int gapPenalty = computeGapPenalty(RNA, string1, string2);
			int mismatchCost = computeMismatch(RNA, string1, string2);
			for (int i=0;i<RNA.size();i++) {
				opt[0][i] = i*gapPenalty;
				opt[i][0] = i*gapPenalty;
			}
			
			//Recurrence to calculate minimum cost
			for (int j=1; j<RNA.size(); j++){
				for (int i=1; i<RNA.size(); i++) {
					int mismatch = mismatchCost+opt[i-1][j-1];
					int gapMin = Math.min(gapPenalty+opt[i-1][j], gapPenalty+opt[i][j-1]);
					opt[i][j] = Math.min(mismatch, gapMin);
				}
			}
			int minCost = opt[RNA.size()-1][RNA.size()-1];
		}
		
		private static int computeGapPenalty(ArrayList<Node> RNA, String string1, String string2) {
			
			return 1;
		}
		
		//Computes the mismatch cost between two nucleotides
		//Xi denoting the character at i in the forward String
		//Yj denoting the character at j in the reverse String
		private static int computeMismatch(ArrayList<Node> RNA, String string1, String string2) {
			
			return 1;
		}*/
}
