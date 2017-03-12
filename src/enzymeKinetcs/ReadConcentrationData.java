package src.enzymeKinetcs;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadConcentrationData {

	protected ArrayList<Double> SampleConcs;
	protected ArrayList<Double> CalibrationConcs;
	
	protected void ReadSampleConcs(String sampleConcs){

		SampleConcs = new ArrayList<Double>();
		double temp = 0;

		try {
			Scanner scan = new Scanner(new File(sampleConcs));

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

				SampleConcs.add(temp);

			}
			scan.close();
		} catch (Exception e) {
			System.err.println("ERROR: File cannot be found");
		}	
	}
	
	protected void ReadCalibrationConcs(String calibrationConcs){

		CalibrationConcs = new ArrayList<Double>();
		double temp = 0;

		try {
			Scanner scan = new Scanner(new File(calibrationConcs));

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

				CalibrationConcs.add(temp);

			}
			scan.close();
		} catch (Exception e) {
			System.err.println("ERROR: File cannot be found");
		}	
	}
	
	protected void PrintSampleConcs(){

		System.out.println("Sample Concentrations");

		for(int row = 0; row < 8; row ++){
			System.out.println(SampleConcs.get(row));
		}

		System.out.println();
	}
	
	protected void PrintCalibrationConcs(){

		System.out.println("Calibration Concentrations");

		for(int row = 0; row < 8; row ++){
			System.out.println(CalibrationConcs.get(row));
		}

		System.out.println();
	}

}
