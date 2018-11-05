package tsp.heuristic;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import tsp.Instance;

public class Fourmi extends AHeuristic{
	long[][] t = this.m_instance.getDistances();
	public Fourmi(Instance instance, String name) throws Exception {
		super(instance, name);
		// TODO Auto-generated constructor stub
	}



	// Fonction retournant la répartition initiale aléatoire des m fourmis sur les n villes //

	public static void RepartitionAleat (Chemin[] memoire, int nbrVilles, int nbrFourmis, long[][] distances) throws Exception {
	for (int i=0 ; i<nbrFourmis ; i++) {
		memoire[i].visiter(i, distances);
	}
}

		
	// Fonction réalisant un tour pour la fourmi k //
	
public static void TourIndiv(Chemin memoireK, int nbrVilles, double[][] pheromones, double[][] visibilite, double alfa, double beta, long[][] distances) throws Exception {
while (!memoireK.getTovisit().isEmpty()) {
		double sum = 0.0;
		int last = (int) memoireK.getLast(); //indice de la ville sur laquelle la fourmi k se trouve
		Double[] probas=new Double[nbrVilles]; //Tableau représentant la proabilité que la fourmi k aille à la ville i (vaut 0 si déja visitée)
		for (int i=0;i<nbrVilles;i++) {
			probas[i]=0.0;
		}
		//Calcul des proba 
		for (Long next:memoireK.getTovisit()) {
			sum+=Math.pow(pheromones[last][(int)Math.floor(next)], alfa)*Math.pow(visibilite[last][(int)Math.floor(next)],beta);;
			probas[(int)Math.floor(next)] = Math.pow(pheromones[last][(int)Math.floor(next)],alfa)*Math.pow(visibilite[last][(int)Math.floor(next)],beta);

		}
	double aleat = Math.random();
	int index =0;
	while(index<nbrVilles && aleat>(probas[index]/sum)) {
		index++;
	}
	if (index<nbrVilles) {
		memoireK.visiter(index, distances);
	} else {
		index=0;
		while(probas[index]==0.0) {
		index++;
	}
		memoireK.visiter(index, distances);
	}
	}
	}



	//Realise un tour pour toutes les fourmis //
	
	public static void TourGeneral(Chemin[] memoire, int nbrVilles, double[][] pheromones, double[][] visibilite, double alfa, double beta, long[][] distances) throws Exception {
		for (int k=0; k<nbrVilles;k++) {
			TourIndiv(memoire[k],nbrVilles,pheromones,visibilite,alfa,beta, distances);
		}
	}
	
	
	//Met à jour le taux de phéromones après un tour//
	
	public static void MajPheromones (double[][] pheromones, Chemin[] memoire, int nbrFourmis, int nbrVilles, double ro, double Q, long[][] t) throws Exception {
		for (int k=0 ; k<nbrVilles;k++) {
			while(memoire[k].getVisited().size()>1) {
				int prec=(int) Math.floor(memoire[k].getVisited().remove(memoire[k].getVisited().size()-1));
				int suiv =(int) Math.floor(memoire[k].getLast());
				pheromones[prec][(int) Math.floor(suiv)]= ro*pheromones[prec][(int) Math.floor(suiv)]+Q/memoire[k].getCout();
				memoire[k].getTovisit().add((long) prec);
			}
		}
	}


	
	
	//Fonction calculant la longueur du chemin pour chaque fourmi//
	
	 /* public static double[] LongueurChemin (Chemin[] memoire, long[][] t, int nbrFourmis) {
		double[] res = new double[nbrFourmis];
		for (int i=0 ; i<nbrFourmis ; i++) {
			res[i]=memoire[i].getCout();
		}
		return res;
	} */
	
	
	//Fonction retournant le chemin le plus court et sa valeur //
	
	public static Tuple CheminLePlusCourt (Chemin[] memoire, long[][] t, int nbrFourmis) {
		double cout = 100000.0;
		int ind = 0;
		for (int c=0;c<nbrFourmis;c++) {
			double coutChemin = memoire[c].getCout();
			if (coutChemin<cout) {
				ind=c;
				cout=coutChemin;
			}
		}
		return new Tuple(ind,cout);
	}

	
	//Effaçage de la mémoire //
	
	public static void effacerMemoire (Chemin[] memoire, int nbrVilles) {
		for (int i=0;i<memoire.length;i++) {
			memoire[i]=new Chemin(nbrVilles);
		}
	}
	
	
@Override
public void solve() throws Exception {
	// TODO Auto-generated method stub

	long[][] t = this.m_instance.getDistances(); // Matrice des distances 
	int nbrVilles=this.m_instance.getNbCities(); // Nombre de villes 
	int nbrFourmis = nbrVilles; // Nombre de fourmis 
	double c = 0.01; //Constante pour les pheromones 
	double alfa=1; //Importance phéromones 
	double beta=1; //Importace visibilité 
	double ro=0.8; //Coeficient de vitesse d'évaporation 
	double min=100000.0; //Longueur minimum
	Chemin cheminFinal = new Chemin(nbrVilles) ;//Chemin le plus court
	int Cmax=500 ; //Constante nombre d'itération 
	double Q=100.0; //Taux de pheromones déposées

	
	// Tableau des mémoires de chaque fourmi initialisé à vide//
		
	Chemin[] memoire = new Chemin[nbrFourmis];
	for (int compt=0; compt<nbrFourmis ; compt ++) {
		memoire[compt]=new Chemin(nbrVilles);
	}
	
	
	//Tableau des phéromones déposées intitialsées à c//
	
	double[][] pheromones = new double[nbrVilles][nbrVilles];
	for (int i=0 ; i<nbrVilles ; i++) {
		for (int j=0 ; j<nbrVilles ; j++) {
			if (i==j) {
				pheromones[i][j]=0.0;
			} else {
				pheromones[i][j]=c;
			}
		}

	}
	
	
	//Tableau des visibilités et des distances en double //
	
	double[][] visib = new double[nbrVilles][nbrVilles];
	for (int i=0;i<nbrVilles ; i++) {
		for (int j=0;j<nbrVilles ; j++) {
			if (i==j) {
				visib[i][j]=Double.NaN;
			} else {
				visib[i][j]=1.0/t[i][j];
		}
	}
	}

	
	int[] resultat = new int[nbrVilles+1];	
	for (int compt=0;compt<Cmax;compt++){
		RepartitionAleat(memoire,nbrVilles, nbrFourmis, t);
		TourGeneral(memoire, nbrVilles, pheromones, visib, alfa, beta, t);
		double cout = CheminLePlusCourt(memoire,t, nbrFourmis).getX2();
		if (cout<min) {
			min=cout;
			cheminFinal=memoire[CheminLePlusCourt(memoire,t, nbrFourmis).getX1()];
			for (int k=0;k<nbrVilles;k++) {
				resultat[k]=(int)Math.floor(cheminFinal.getVisited().get(k));		
			}
		}
		MajPheromones(pheromones, memoire, nbrVilles, nbrFourmis, ro, Q, t);
		effacerMemoire(memoire, nbrVilles);		
	}
	for (int k=0;k<nbrVilles;k++) {
		this.m_solution.setCityPosition(resultat[k], k);
	}
	this.m_solution.setCityPosition(resultat[0], nbrVilles);
}


}


