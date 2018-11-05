package tsp.heuristic;

import tsp.Instance;
import tsp.TSPSolver;

public class PlusProcheVoisin extends AHeuristic {

	public PlusProcheVoisin(Instance instance, String name) throws Exception {
		super(instance, name);
	}

	@Override
	public void solve() throws Exception {
		boolean[] visite = new boolean[this.m_instance.getNbCities()];
		this.m_solution.setCityPosition(1, 0);
		visite[0]=true;
		for(int i=1; i<this.m_instance.getNbCities()-1; i++) {
			this.m_solution.setCityPosition(this.plusproche(this.m_solution.getCity(i-1), visite),i);
			visite[this.plusproche(this.m_solution.getCity(i-1), visite)-1]=true;
		}
		this.m_solution.setCityPosition(1, this.m_instance.getNbCities());

	}
	
	public boolean estvisite(int v, boolean[] visite) throws Exception {
		return visite[v-1]==true;
	}

	public int plusproche(int v, boolean visite[]) throws Exception{
		long distance=100000;
		int k=1;
		for(int i=1; i<this.m_instance.getNbCities(); i++) {
			if (this.m_instance.getDistances(v, i)<distance && !this.estvisite(i, visite)) {
				distance=this.m_instance.getDistances(v,i);
				k=i;
			}
		}
		return k;
	}
	
	
	
	
	
	public void permut() throws Exception {
		for(int i=0; i<this.m_instance.getNbCities()-2; i++) {
			for(int j=i+2; j<this.m_instance.getNbCities(); j++) {
				long gain = this.m_solution.getInstance().getDistances(this.m_solution.getCity(i), this.m_solution.getCity(j))
						+ this.m_solution.getInstance().getDistances(this.m_solution.getCity(i+1), this.m_solution.getCity(j+1))
						- this.m_solution.getInstance().getDistances(this.m_solution.getCity(i), this.m_solution.getCity(i+1))
						- this.m_solution.getInstance().getDistances(this.m_solution.getCity(j), this.m_solution.getCity(j+1));
				if (gain<0) {
					permutation(i,j);
				}
			}
		}
	}
	
	public void permutation (int i, int j) throws Exception {
		int[] res = new int[j-i+1];
		for(int k=i+1; k<j+1; k++) {
			res[k-i]=this.m_solution.getCity(k);
		}
		for(int k=i+1; k<j+1; k++) {
			this.m_solution.setCityPosition(res[k-i], j-k+i+1);
		}
	}

	

}
