package tsp.heuristic;

import java.util.ArrayList;

public class Chemin{


	private long cout;
	private ArrayList<Long> visited;
	private ArrayList<Long> tovisit;
	private Long last;

	public Chemin (int nbrVilles) {
		this.cout=0;
		this.visited=new ArrayList<Long>();
		this.tovisit=new ArrayList<Long>();
		for (int i=0;i<nbrVilles;i++) {
			this.tovisit.add((long) i);
		}
	}

	public Chemin(long cout, ArrayList<Long> visited, ArrayList<Long> tovisit) {
		super();
		this.cout = cout;
		this.visited = visited;
		this.tovisit = tovisit;
		this.last=this.visited.get(this.visited.size()-1);
	}

	public long getCout() {
		return this.cout;
	}

	public void setCout(long cout) {
		this.cout = cout;
	}

	public ArrayList<Long> getVisited() {
		return visited;
	}

	public void setVisited(ArrayList<Long> visited) {
		this.visited = visited;
	}

	public ArrayList<Long> getTovisit() {
		return tovisit;
	}

	public void setTovisit(ArrayList<Long> tovisit) {
		this.tovisit = tovisit;
	}

	public long getLast() throws Exception {
		int l=this.getVisited().size();
		if (l!=0) {
			return this.getVisited().get(l-1);
		} else {
			throw new Exception ("liste vide");
		}
	}

	public void setLast(long last) {
		this.last = last;
	}
	
	public void visiter (long ville, long[][] distances) throws Exception {
		if (this.getVisited().size()>=1) {
			int prec = (int) Math.floor(this.getLast());
			this.getVisited().add(ville);
			this.getTovisit().remove(ville);
			this.setCout(this.getCout()+distances[prec][(int) ville]);
		} else {
			this.getVisited().add(ville);
			this.getTovisit().remove(ville);
		}
	}
	
	public String toString() {
		String res ="[";
		res+=this.getVisited().get(0);
		for (int i=1;i<this.getVisited().size();i++) {
			res+=" ; "+this.getVisited().get(i);
		}
		res+="]";
		return res;
	}
	
	public static void main (String[] args) throws Exception {
		Chemin c =new Chemin(2);
		System.out.println(c.getCout());
		long[][] distances = new long[2][2];
		distances[0][1]=5;
		distances[1][0]=5;
		c.visiter(0, distances);
		c.visiter(1, distances);
		System.out.println(c.getCout());
	}



	
}
