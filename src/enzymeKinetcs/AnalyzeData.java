package enzymeKinetcs;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import org.apache.commons.math3.stat.regression.SimpleRegression;

public class AnalyzeData {

	protected ArrayList<Double> BG1;
	protected ArrayList<Double> BG2;
	protected ArrayList<Double> BG3;
	protected ArrayList<Double> BG4;
	protected ArrayList<Double> BGAvg1;
	protected ArrayList<Double> BGAvg2;
	protected ArrayList<Double> BGAvg3;
	protected ArrayList<Double> Sample1;
	protected ArrayList<Double> Sample2;
	protected ArrayList<Double> Sample3;
	protected ArrayList<Double> Norm1;
	protected ArrayList<Double> Norm2;
	protected ArrayList<Double> Norm3;
	protected ArrayList<Double> ColConcs1;
	protected ArrayList<Double> ColConcs2;
	protected ArrayList<Double> ColConcs3;
	protected double CalibrationSlope;
	protected double CalibrationIntercept;
	
	protected void ReadData(String filename){
		
		// initialize arrays and variables
		BG1 = new ArrayList<Double>();
		BG2 = new ArrayList<Double>();
		BG3 = new ArrayList<Double>();
		BG4 = new ArrayList<Double>();
		Sample1 = new ArrayList<Double>();
		Sample2 = new ArrayList<Double>();
		Sample3 = new ArrayList<Double>();
		double temp = 0;
		
		// decimal formatter
		DecimalFormat df = new DecimalFormat("0.000");
		
		try {
			Scanner scan = new Scanner(new File(filename));
			
			for(int row = 0; row < 8; row++){
				for(int col = 0; col < 7; col++){
					
					// get all doubles
					if(scan.hasNextDouble()){
						temp = scan.nextDouble();
					}
					
					// catch any integers
					else if(scan.hasNextInt()){
						temp = scan.nextInt();
					}
					
					// handle too few values
					else {
						System.err.println("ERROR: Not enough values in the data");
						System.exit(-1);
					}
					
					String tempStr = df.format(temp);
					temp = Double.parseDouble(tempStr);
					
					// add values to their corresponding arrays
					switch(col){
					case 0: Sample1.add(temp); break;
					case 1: Sample2.add(temp); break;
					case 2: Sample3.add(temp); break;
					case 3: BG1.add(temp); break;
					case 4: BG2.add(temp); break;
					case 5: BG3.add(temp); break;
					case 6: BG4.add(temp); break;
					default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
					}
				}
			}
			scan.close();
			
		} catch (Exception e) {
			System.err.println("ERROR: File cannot be found");
		}	
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
	
	protected void FindCalibrationEquation(ArrayList<Double> Concentrations){
		
		SimpleRegression sr = new SimpleRegression();
		for(int row = 0; row < 8; row++){
			sr.addData(Concentrations.get(row), Norm1.get(row));
			sr.addData(Concentrations.get(row), Norm2.get(row));
			sr.addData(Concentrations.get(row), Norm3.get(row));
		}
		
		// NEED TO CHANGE THIS, TESTING W/ STANDARD EQUATION
		CalibrationSlope = 1098.1;
		CalibrationIntercept = 1.2766;
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
			temp = (Norm1.get(row) - CalibrationIntercept) / CalibrationSlope;
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			ColConcs1.add(temp);
		}
		
		// find concentration of Sample2
		for(int row = 0; row < 8; row++){
			temp = (Norm2.get(row) - CalibrationIntercept) / CalibrationSlope;
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			ColConcs2.add(temp);
		}
		
		// find concentration of Sample3
		for(int row = 0; row < 8; row++){
			temp = (Norm3.get(row) - CalibrationIntercept) / CalibrationSlope;
			String strTemp = df.format(temp);
			temp = Double.parseDouble(strTemp);
			ColConcs3.add(temp);
		}	
	}

	protected void PrintRawData() {
		
		System.out.println("Raw Data");
		
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 7; col++){
				switch(col){
					case 0: System.out.print(Sample1.get(row) + "  "); break;
					case 1: System.out.print(Sample2.get(row) + "  "); break;
					case 2:	System.out.print(Sample3.get(row) + "  "); break;
					case 3: System.out.print(BG1.get(row) + "  "); break;
					case 4: System.out.print(BG2.get(row) + "  "); break;
					case 5: System.out.print(BG3.get(row) + "  "); break;
					case 6: System.out.println(BG4.get(row)); break;
					default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
				}
			}
		}
		
		System.out.println();
	}
	
	protected void PrintAveragedData() {
		
		System.out.println("Averaged Data");
		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 3; col++){
				switch(col){
					case 0: System.out.print(BGAvg1.get(row) + "  "); break;
					case 1:	System.out.print(BGAvg2.get(row) + "  "); break;
					case 2: System.out.println(BGAvg3.get(row) + "  "); break;
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
					case 0: System.out.print(Norm1.get(row) + "  "); break;
					case 1:	System.out.print(Norm2.get(row) + "  "); break;
					case 2: System.out.println(Norm3.get(row) + "  "); break;
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
				case 0: System.out.print(ColConcs1.get(row) + "  "); break;
				case 1: System.out.print(ColConcs2.get(row) + "  "); break;
				case 2: System.out.println(ColConcs3.get(row) + "  "); break;
				default: System.err.print("Error: Reached the default case. That's weird...");
				}
			}
		}
		
		System.out.println();
	}
	
	public static void main(String[] args){
		
	}
}
