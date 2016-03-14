import java.util.*;

public class Node {
	private String nucleotide;
	private Node basePartner;
	private int index;
	
	public Node (String nucleotide, int index) {
		this.nucleotide = nucleotide;
		this.index = index;
		basePartner = null;
	}
	
	public String getNucleotide() {
		return nucleotide;
	}
	
	public boolean canPair(int index, ArrayList<Node> RNA) {
		if(this.nucleotide.equals("A") && 
				RNA.get(index).getNucleotide().equals("U")) {
			return true;
		}
		else if(this.nucleotide.equals("U") && 
				RNA.get(index).getNucleotide().equals("A")) {
			return true;
		}
		else if(this.nucleotide.equals("G") && 
				RNA.get(index).getNucleotide().equals("C")) {
			return true;
		}
		else if(this.nucleotide.equals("C") && 
				RNA.get(index).getNucleotide().equals("G")) {
			return true;
		}
		//Part 5, G-U base pairing
		/*else if(this.nucleotide.equals("G") && 
				RNA.get(index).getNucleotide().equals("U")) {
			return true;
		}
		else if(this.nucleotide.equals("U") && 
				RNA.get(index).getNucleotide().equals("G")) {
			return true;*/
		else {
			return false;
		}
	}
	
	public void setBasePartner(Node basePartner) {
		this.basePartner = basePartner;
	}
	
	public Node getBasePartner() {
		return basePartner;
	}
	
	public boolean hasBasePartner() {
		if (this.basePartner==null) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public int getIndex() {
		return index;
	}
}
