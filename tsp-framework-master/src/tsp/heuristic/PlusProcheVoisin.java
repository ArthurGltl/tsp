package tsp.heuristic;

import tsp.Instance;
import tsp.TSPSolver;

public class PlusProcheVoisin extends AHeuristic {

	public PlusProcheVoisin(Instance instance, String name) throws Exception {
		super(instance, name);
	}

	@Override
	public void solve() throws Exception {
		long distance=0;
		this.m_solution.setCityPosition(1, 0);
		for(int i=1; i<this.m_instance.getNbCities()-1; i++) {
			this.m_solution.setCityPosition(this.m_solution.plusproche(this.m_solution.getCity(i-1)),i);
			distance+=this.m_solution.getInstance().getDistances(this.getSolution().getCity(i),this.getSolution().getCity(i-1));
		}
		this.m_solution.setCityPosition(1, this.m_instance.getNbCities());
		distance+=this.m_solution.getInstance().getDistances(1,this.m_instance.getNbCities()-1);
		System.out.println(distance);
	}

	

}
