package enzymeKinetics;

import java.util.ArrayList;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class FindVmaxKm {

	private ArrayList<Double> ReactionRate;
	private ArrayList<Double> SubstrateConc;
	private SimpleRegression LB;
	private SimpleRegression MM;
	private double Vmax;
	private double Km;
	
	protected double getVmax(){
		return Vmax;
	}
	
	protected double getKm(){
		return Km;
	}

	protected FindVmaxKm(ArrayList<Double> ReactionRate, ArrayList<Double> SubstrateConc){

		this.ReactionRate = ReactionRate;
		this.SubstrateConc = SubstrateConc;
	}
	
	protected void LineweaverBurk(int index){
		LB = new SimpleRegression();
		for(int i = index; i < SubstrateConc.size()-index; i++){
			double x = 1.0/SubstrateConc.get(i);
			double y = 1.0/ReactionRate.get(i);
			LB.addData(x, y);
		}
	}
	
	protected void MichaelisMenten(int index, double tempVmax, double tempKm){
		
		MM = new SimpleRegression();
		for(int i = index; i < SubstrateConc.size()-index; i++){
			Double temp = (tempVmax * SubstrateConc.get(i))/(tempKm + SubstrateConc.get(i));
			MM.addData(SubstrateConc.get(i), temp);
		}
	}

	protected void FindValues(){

		boolean done = false;
		double deviation = 0;
		int index = 0;
		double tempVmax = 0;
		double tempKm = 0;

		while(done == false){
			
			LineweaverBurk(index);
			Vmax = tempVmax;
			Km = tempKm;
			tempVmax = 1.0/LB.getIntercept();
			tempKm = LB.getSlope()/LB.getIntercept();
			
			MichaelisMenten(index, tempVmax, tempKm);

			// find difference between LB and MM slopes
			double diff = Math.abs(MM.getSlope() - LB.getSlope());

			// clear regressions
			LB.clear();
			MM.clear();
			
			if(index == 0){
				deviation = diff;
				System.out.println("Removing concentration: " + SubstrateConc.get(index));
				System.out.println("Removing rate: " + ReactionRate.get(index));
				System.out.println();
				index++;
			}
			
			else {
				if(diff < deviation) {
					deviation = diff;
					System.out.println("Removing concentration: " + SubstrateConc.get(index));
					System.out.println("Removing rate: " + ReactionRate.get(index));
					System.out.println();
					index++;
				}
				else {
					done = true;
				}
			}
		}
	}

	protected void PrintVmaxKm(){

		System.out.println("Vmax: " + Vmax);
		System.out.println("Km: " + Km);
		System.out.println();
	}

}
