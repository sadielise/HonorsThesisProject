package src.enzymeKinetcs;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ProcessData {

	protected ArrayList<Double> BG1;
	protected ArrayList<Double> BG2;
	protected ArrayList<Double> BG3;
	protected ArrayList<Double> BG4;
	protected ArrayList<Double> Sample1;
	protected ArrayList<Double> Sample2;
	protected ArrayList<Double> Sample3;
	protected ArrayList<Double> SlopeIntercept;
	protected ArrayList<Double> BGAvg1;
	protected ArrayList<Double> BGAvg2;
	protected ArrayList<Double> BGAvg3;
	protected ArrayList<Double> Norm1;
	protected ArrayList<Double> Norm2;
	protected ArrayList<Double> Norm3;
	protected ArrayList<Double> ColConcs1;
	protected ArrayList<Double> ColConcs2;
	protected ArrayList<Double> ColConcs3;

	public ProcessData(ArrayList<Double> BG1, ArrayList<Double> Sample1, ArrayList<Double> BG2, ArrayList<Double> Sample2, ArrayList<Double> BG3, ArrayList<Double> Sample3, ArrayList<Double> BG4, ArrayList<Double> SlopeIntercept){

		this.BG1 = BG1;
		this.Sample1 = Sample1;
		this.BG2 = BG2;
		this.Sample2 = Sample2;
		this.BG3 = BG3;
		this.Sample3 = Sample3;
		this.BG4 = BG4;
		this.SlopeIntercept = SlopeIntercept;
	}

	protected void AverageData(){

		// initialize arrays and variable
		BGAvg1 = new ArrayList<Double>();
		BGAvg2 = new ArrayList<Double>();
		BGAvg3 = new ArrayList<Double>();

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.0000");

		// find BG1 and BG2 averages
		for(int row = 0; row < 8; row++){
			double temp = 0;
			temp = (BG1.get(row) + BG2.get(row))/2.0;
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			BGAvg1.add(temp);
		}

		// find BG2 and BG3 averages
		for(int row = 0; row < 8; row++){
			double temp = 0;
			temp = (BG2.get(row) + BG3.get(row))/2.0;
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			BGAvg2.add(temp);
		}

		// find BG3 and BG4 averages
		for(int row = 0; row < 8; row++){
			double temp = 0;
			temp = (BG3.get(row) + BG4.get(row))/2.0;
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			BGAvg3.add(temp);
		}
	}

	protected void NormalizeData() {

		// initialize arrays and variables
		Norm1 = new ArrayList<Double>();
		Norm2 = new ArrayList<Double>();
		Norm3 = new ArrayList<Double>();
		double temp = 0;

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.0000");

		// find normalized version of Sample1
		for(int row = 0; row < 8; row++){
			temp = (Sample1.get(row) - BGAvg1.get(row));
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			Norm1.add(temp);
		}

		// find normalized version of Sample2
		for(int row = 0; row < 8; row++){
			temp = (Sample2.get(row) - BGAvg2.get(row));
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			Norm2.add(temp);
		}

		// find normalized version of Sample3
		for(int row = 0; row < 8; row++){
			temp = (Sample3.get(row) - BGAvg3.get(row));
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			Norm3.add(temp);
		}
	}

	protected void FindConcentrations() {

		// initialize arrays and variables
		ColConcs1 = new ArrayList<Double>();
		ColConcs2 = new ArrayList<Double>();
		ColConcs3 = new ArrayList<Double>();
		double temp = 0;

		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.000000");

		// find concentration of Sample1
		for(int row = 0; row < 8; row++){
			temp = (Norm1.get(row) - SlopeIntercept.get(1)) / SlopeIntercept.get(0);
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			ColConcs1.add(temp);
		}

		// find concentration of Sample2
		for(int row = 0; row < 8; row++){
			temp = (Norm2.get(row) - SlopeIntercept.get(1)) / SlopeIntercept.get(0);
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			ColConcs2.add(temp);
		}

		// find concentration of Sample3
		for(int row = 0; row < 8; row++){
			temp = (Norm3.get(row) - SlopeIntercept.get(1)) / SlopeIntercept.get(0);
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			ColConcs3.add(temp);
		}	
	}

	protected void PrintAveragedData() {

		System.out.println("Averaged Data");
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 3; col++){
				switch(col){
				case 0: System.out.print(BGAvg1.get(row) + "\t"); break;
				case 1:	System.out.print(BGAvg2.get(row) + "\t"); break;
				case 2: System.out.println(BGAvg3.get(row) + "\t"); break;
				default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
				}
			}
		}

		System.out.println();
	}

	protected void PrintNormalizedData() {

		System.out.println("Normalized Data");
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 3; col++){
				switch(col){
				case 0: System.out.print(Norm1.get(row) + "\t"); break;
				case 1:	System.out.print(Norm2.get(row) + "\t"); break;
				case 2: System.out.println(Norm3.get(row) + "\t"); break;
				default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
				}
			}
		}

		System.out.println();
	}

	protected void PrintConcentrationData() {

		System.out.println("Concentration Data");
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 3; col++){
				switch(col){
				case 0: System.out.printf("%.6f", ColConcs1.get(row)); System.out.print("\t"); break;
				case 1: System.out.printf("%.6f", ColConcs2.get(row)); System.out.print("\t"); break;
				case 2: System.out.printf("%.6f", ColConcs3.get(row)); System.out.println(); break;
				default: System.err.print("Error: Reached the default case. That's weird...");
				}
			}
		}

		System.out.println();
	}

}
