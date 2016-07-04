package enzymeKinetcs;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ProcessData {

	// class variables
	protected String ConcentrationsFilename;
	protected ArrayList<Double> Concentrations;

	public ProcessData(String filename){

		ConcentrationsFilename = filename;
	}

	protected void ReadConcentrations(){

		Concentrations = new ArrayList<Double>();
		double temp = 0;

		try {
			Scanner scan = new Scanner(new File(ConcentrationsFilename));

			for(int row = 0; row < 8; row++){

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

				Concentrations.add(temp);

			}
			scan.close();
		} catch (Exception e) {
			System.err.println("ERROR: File cannot be found");
		}	
	}

	protected void PrintConcentrations(){

		System.out.println("Concentrations");

		for(int row = 0; row < 8; row ++){
			System.out.println(Concentrations.get(row));
		}

		System.out.println();
	}

	public static void main(String[] args) {

		// variables to add times and memory
		long totalTime = 0;
		long totalMemory = 0;

		// program timing
		for(int count = 0; count < 100; count++){

			long startTime = System.currentTimeMillis();

			if(args.length != 4){
				System.err.println("ERROR: Incorrect number of arguments.");
				System.exit(-1);
			}

			ProcessData pd = new ProcessData(args[3]);
			pd.ReadConcentrations();

			//System.out.println("********ANALYZING DATA********");
			//System.out.println();

			//System.out.println("MINUTE 1");		
			AnalyzeData Min1 = new AnalyzeData();
			Min1.ReadData(args[0]);
			Min1.AverageData();
			Min1.NormalizeData();
			Min1.FindCalibrationEquation(pd.Concentrations);
			Min1.FindConcentrations();
			//Min1.PrintRawData();
			//Min1.PrintAveragedData();
			//Min1.PrintNormalizedData();
			//System.out.println("Calibration Slope: " + Min1.CalibrationSlope);
			//System.out.println("Calibration Intercept: " + Min1.CalibrationIntercept);
			//System.out.println();
			//Min1.PrintConcentrationData();

			//System.out.println("MINUTE 2");
			AnalyzeData Min2 = new AnalyzeData();
			Min2.ReadData(args[1]);
			Min2.AverageData();
			Min2.NormalizeData();
			Min2.FindCalibrationEquation(pd.Concentrations);
			Min2.FindConcentrations();
//			Min2.PrintRawData();
//			Min2.PrintAveragedData();
//			Min2.PrintNormalizedData();
//			System.out.println("Calibration Slope: " + Min2.CalibrationSlope);
//			System.out.println("Calibration Intercept: " + Min2.CalibrationIntercept);
//			System.out.println();
//			Min2.PrintConcentrationData();

//			System.out.println("MINUTE 3");
			AnalyzeData Min3 = new AnalyzeData();
			Min3.ReadData(args[2]);
			Min3.AverageData();
			Min3.NormalizeData();
			Min3.FindCalibrationEquation(pd.Concentrations);
			Min3.FindConcentrations();
//			Min3.PrintRawData();
//			Min3.PrintAveragedData();
//			Min3.PrintNormalizedData();
//			System.out.println("Calibration Slope: " + Min3.CalibrationSlope);
//			System.out.println("Calibration Intercept: " + Min3.CalibrationIntercept);
//			System.out.println();
//			Min3.PrintConcentrationData();

//			System.out.println("********COMBINING DATA********");
//			System.out.println();
			CombineData Combine = new CombineData();
			ArrayList<Double> Rates1 = Combine.FindRates(Min1.ColConcs1, Min2.ColConcs1, Min3.ColConcs1);
			ArrayList<Double> Rates2 = Combine.FindRates(Min1.ColConcs2, Min2.ColConcs2, Min3.ColConcs2);
			ArrayList<Double> Rates3 = Combine.FindRates(Min1.ColConcs3, Min2.ColConcs3, Min3.ColConcs3);
//			Combine.PrintRates(Rates1);
//			Combine.PrintRates(Rates2);
//			Combine.PrintRates(Rates3);
			ArrayList<Double> RatesAvg = Combine.FindRatesAvg(Rates1, Rates2, Rates3);
//			Combine.PrintAverageRates(RatesAvg);
			ArrayList<Double> RatesStdDev = Combine.FindRatesStdDev(Rates1, Rates2, Rates3);
//			Combine.PrintStdDevRates(RatesStdDev);
			ArrayList<Double> VmaxKm = Combine.FindVmaxKm(RatesAvg, pd.Concentrations);
//			Combine.PrintVmaxKm(VmaxKm);

			long endTime = System.currentTimeMillis();
			Runtime runtime = Runtime.getRuntime();
			runtime.gc();

			totalTime += endTime - startTime;
			totalMemory += runtime.totalMemory() - runtime.freeMemory();
			System.out.println((count+1) + ": " + (endTime - startTime) + " milliseconds");
		
		}
		
		System.out.println("Avg time: " + (totalTime/100) + " ms");
		System.out.println("Avg memory: " + (totalMemory/100) + " bytes");
	}
}
