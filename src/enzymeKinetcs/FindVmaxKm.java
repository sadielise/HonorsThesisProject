package enzymeKinetcs;

import java.util.ArrayList;

import org.apache.commons.math3.stat.regression.SimpleRegression;

public class FindVmaxKm {

	ArrayList<Double> ReactionRate;
	ArrayList<Double> SubstrateConc;
	SimpleRegression LB;
	SimpleRegression MM;
	double Vmax;
	double Km;

	public FindVmaxKm(ArrayList<Double> ReactionRate, ArrayList<Double> SubstrateConc){

		this.ReactionRate = ReactionRate;
		this.SubstrateConc = SubstrateConc;

	}

	protected void LineweaverBurk(){

		
	}

	protected void FindValues(){

		boolean done = false;

		while(done == false){
			
			LB = new SimpleRegression();
			for(int i = 0; i < SubstrateConc.size(); i++){
				double x = 1.0/SubstrateConc.get(i);
				double y = 1.0/ReactionRate.get(i);
				LB.addData(x, y);
			}
			Vmax = 1.0/LB.getIntercept();
			Km = LB.getSlope()/LB.getIntercept();

			MM = new SimpleRegression();

			for(int i = 0; i < 8; i++){
				Double temp = (Vmax * SubstrateConc.get(i))/(Km + SubstrateConc.get(i));
				MM.addData(SubstrateConc.get(0), temp);
			}

			double diff = Math.abs(MM.getSlope() - LB.getSlope());

		}
	}

	protected void PrintVmaxKm(){

		System.out.println("Vmax: " + Vmax);
		System.out.println("Km: " + Km);
		System.out.println();
	}

}
