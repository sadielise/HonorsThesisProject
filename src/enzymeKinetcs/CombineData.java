package enzymeKinetcs;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class CombineData {

	protected ArrayList<Double> FindRates(ArrayList<Double> min1, ArrayList<Double> min2, ArrayList<Double> min3) {		

		// initialize array
		ArrayList<Double> rates = new ArrayList<Double>();

		// decimal format
		DecimalFormat df = new DecimalFormat("0.0000");

		for(int row = 0; row < 8; row++){
			SimpleRegression sr = new SimpleRegression();
			sr.addData(1.0, min1.get(row));
			sr.addData(2.0, min2.get(row));
			sr.addData(3.0, min3.get(row));
			double slope = sr.getSlope();
			String strTemp = df.format(slope);
			slope = Double.parseDouble(strTemp);
			rates.add(slope);
		}

		return rates;
	}

	protected ArrayList<Double> FindRatesAvg (ArrayList<Double> conc1, ArrayList<Double> conc2, ArrayList<Double> conc3){

		// initialize array
		ArrayList<Double> ratesAvg = new ArrayList<Double>();

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.000000");

		for(int row = 0; row < 8; row++){
			Double numerator = conc1.get(row) + conc2.get(row) + conc3.get(row);
			Double avg = numerator / 3.0;
			String strTemp = df.format(avg);
			avg = Double.parseDouble(strTemp);
			ratesAvg.add(avg);
		}

		return ratesAvg;
	}

	protected ArrayList<Double> FindRatesStdDev (ArrayList<Double> conc1, ArrayList<Double> conc2, ArrayList<Double> conc3){

		// initialize array 
		ArrayList<Double> ratesStdDev = new ArrayList<Double>();

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.000000");

		for(int row = 0; row < 8; row++){
			double[] temp = new double[3];
			temp[0] = conc1.get(row);
			temp[1] = conc2.get(row);
			temp[2] = conc3.get(row);
			StandardDeviation StdDev = new StandardDeviation();
			double temp1 = StdDev.evaluate(temp);
			String strTemp = df.format(temp1);
			temp1 = Double.parseDouble(strTemp);
			ratesStdDev.add(temp1);
		}

		return ratesStdDev;
	}

	protected ArrayList<Double> FindVmaxKm(ArrayList<Double> avgrate, ArrayList<Double> concentrations){

		// initialize array
		ArrayList<Double> vmaxkm = new ArrayList<Double>();

		// decimal format
		DecimalFormat df = new DecimalFormat("0.000000");

		// THIS IGNORES THE FIRST 2 ROWS OF DATA
		SimpleRegression sr = new SimpleRegression();
		for(int row = 2; row < 8; row++){
			double tempX = (1/concentrations.get(row));
			double tempY = (1/avgrate.get(row));
			sr.addData(tempX, tempY);
		}
		double yIntercept = sr.getIntercept();
		double slope = sr.getSlope();
		double temp1 = (1/yIntercept);
		String strTemp1 = df.format(temp1);
		temp1 = Double.parseDouble(strTemp1);
		double temp2 = (slope/yIntercept);
		String strTemp2 = df.format(temp2);
		temp2 = Double.parseDouble(strTemp2);
		vmaxkm.add(temp1);
		vmaxkm.add(temp2);
		return vmaxkm;
	}

	protected void PrintSubstrateConcentrations(ArrayList<Double> concs){

		System.out.println("Substrate Concentrations");
		for(int row = 0; row < 8; row++){
			System.out.println(concs.get(row));
		}

		System.out.println();
	}

	protected void PrintRates(ArrayList<Double> rates){

		System.out.println("Rate Data");

		for(int row = 0; row < 8; row++){
			System.out.println(rates.get(row));
		}

		System.out.println();
	}

	protected void PrintAverageRates(ArrayList<Double> avgRates) {

		System.out.println("Average Rate Data");

		for (int row = 0; row < 8; row++){
			System.out.println(avgRates.get(row));
		}

		System.out.println();
	}

	protected void PrintStdDevRates(ArrayList<Double> stdDevRates) {

		System.out.println("Standard Deviation Rate Data");

		for (int row = 0; row < 8; row++){
			System.out.println(stdDevRates.get(row));
		}

		System.out.println();
	}

	protected void PrintVmaxKm(ArrayList<Double> VmaxKm){

		System.out.println("Vmax: " + VmaxKm.get(0));
		System.out.println("Km: " + VmaxKm.get(1));
		System.out.println();
	}


	public static void main(String[] args) {

	}


}
