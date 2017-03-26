package enzymeKinetics;

import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class FindSlopes {

	protected ArrayList<Double> Min1Concs1;
	protected ArrayList<Double> Min1Concs2;
	protected ArrayList<Double> Min1Concs3;
	protected ArrayList<Double> Min2Concs1;
	protected ArrayList<Double> Min2Concs2;
	protected ArrayList<Double> Min2Concs3;
	protected ArrayList<Double> Min3Concs1;
	protected ArrayList<Double> Min3Concs2;
	protected ArrayList<Double> Min3Concs3;
	protected ArrayList<Double> Rates1;
	protected ArrayList<Double> Rates2;
	protected ArrayList<Double> Rates3;
	protected ArrayList<Double> AvgRates;
	protected ArrayList<Double> RatesStdDev;

	public FindSlopes(ArrayList<Double> Min1Concs1, ArrayList<Double> Min1Concs2, ArrayList<Double> Min1Concs3, ArrayList<Double> Min2Concs1, ArrayList<Double> Min2Concs2, ArrayList<Double> Min2Concs3, ArrayList<Double> Min3Concs1, ArrayList<Double> Min3Concs2, ArrayList<Double> Min3Concs3){

		this.Min1Concs1 = Min1Concs1;
		this.Min1Concs2 = Min1Concs2;
		this.Min1Concs3 = Min1Concs3;
		this.Min2Concs1 = Min2Concs1;
		this.Min2Concs2 = Min2Concs2;
		this.Min2Concs3 = Min2Concs3;
		this.Min3Concs1 = Min3Concs1;
		this.Min3Concs2 = Min3Concs2;
		this.Min3Concs3 = Min3Concs3;
	}

	protected void FindRates() {		

		// initialize arrays
		Rates1 = new ArrayList<Double>();
		Rates2 = new ArrayList<Double>();
		Rates3 = new ArrayList<Double>();

		// decimal format
		DecimalFormat df = new DecimalFormat("0.0000");

		// get rates for conc1
		for(int row = 0; row < 8; row++){
			SimpleRegression sr = new SimpleRegression();
			sr.addData(1.0, Min1Concs1.get(row));
			sr.addData(2.0, Min2Concs1.get(row));
			sr.addData(3.0, Min3Concs1.get(row));
			double slope = sr.getSlope();
			String strTemp = df.format(slope);
			slope = Double.parseDouble(strTemp);
			Rates1.add(slope);
		}

		// get rates for conc2
		for(int row = 0; row < 8; row++){
			SimpleRegression sr = new SimpleRegression();
			sr.addData(1.0, Min1Concs2.get(row));
			sr.addData(2.0, Min2Concs2.get(row));
			sr.addData(3.0, Min3Concs2.get(row));
			double slope = sr.getSlope();
			String strTemp = df.format(slope);
			slope = Double.parseDouble(strTemp);
			Rates2.add(slope);
		}

		// get rates for conc3
		for(int row = 0; row < 8; row++){
			SimpleRegression sr = new SimpleRegression();
			sr.addData(1.0, Min1Concs3.get(row));
			sr.addData(2.0, Min2Concs3.get(row));
			sr.addData(3.0, Min3Concs3.get(row));
			double slope = sr.getSlope();
			String strTemp = df.format(slope);
			slope = Double.parseDouble(strTemp);
			Rates3.add(slope);
		}
	}

	protected void FindRatesAvg (){

		// initialize array
		AvgRates = new ArrayList<Double>();

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.000000");

		for(int row = 0; row < 8; row++){
			Double numerator = Rates1.get(row) + Rates2.get(row) + Rates3.get(row);
			Double avg = numerator / 3.0;
			String strTemp = df.format(avg);
			avg = Double.parseDouble(strTemp);
			AvgRates.add(avg);
		}
	}

	protected void FindRatesStdDev (){

		// initialize array 
		RatesStdDev = new ArrayList<Double>();

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.000000");

		for(int row = 0; row < 8; row++){
			double[] temp = new double[3];
			temp[0] = Rates1.get(row);
			temp[1] = Rates2.get(row);
			temp[2] = Rates3.get(row);
			StandardDeviation StdDev = new StandardDeviation();
			double temp1 = StdDev.evaluate(temp);
			String strTemp = df.format(temp1);
			temp1 = Double.parseDouble(strTemp);
			RatesStdDev.add(temp1);
		}
	}

	protected void PrintRates(PrintWriter pw){

		pw.println("Rate Data");
		for(int row = 0; row < 8; row++){
			pw.println(Rates1.get(row));
		}
		pw.println();
		
		pw.println("Rate Data");
		for(int row = 0; row < 8; row++){
			pw.println(Rates2.get(row));
		}
		pw.println();
		
		pw.println("Rate Data");
		for(int row = 0; row < 8; row++){
			pw.println(Rates3.get(row));
		}
		pw.println();
	}

	protected void PrintAverageRates(PrintWriter pw) {

		pw.println("Average Rate Data");
		for (int row = 0; row < 8; row++){
			pw.println(AvgRates.get(row));
		}
		pw.println();
	}

	protected void PrintStdDevRates(PrintWriter pw) {

		pw.println("Standard Deviation Rate Data");
		for (int row = 0; row < 8; row++){
			pw.println(RatesStdDev.get(row));
		}
		pw.println();
	}
}
