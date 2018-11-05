package tsp.heuristic;

public class Tuple {

	private int x1;
	private Double x2;

	public Tuple() {
		this.x1=0;
		this.x2=0.0;
	}

	public Tuple(int a, Double b) {
		this.x1=a;
		this.x2=b;
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public Double getX2() {
		return x2;
	}

	public void setX2(Double x2) {
		this.x2 = x2;
	}

	

	

}