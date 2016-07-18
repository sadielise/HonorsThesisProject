package enzymeKinetcs;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadData {

	protected ArrayList<Double> BG1;
	protected ArrayList<Double> BG2;
	protected ArrayList<Double> BG3;
	protected ArrayList<Double> BG4;
	protected ArrayList<Double> BG5;
	protected ArrayList<Double> Sample1;
	protected ArrayList<Double> Sample2;
	protected ArrayList<Double> Sample3;
	protected ArrayList<Double> Calibration;
	protected ArrayList<Double> SampleConcs;
	protected ArrayList<Double> CalibrationConcs;

	protected void ReadFile(String filename){

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
				for(int col = 0; col < 9; col++){

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
					case 0: BG1.add(temp); break;
					case 1: Sample1.add(temp); break;
					case 2: BG2.add(temp); break;
					case 3: Sample2.add(temp); break;
					case 4: BG3.add(temp); break;
					case 5: Sample3.add(temp); break;
					case 6: BG4.add(temp); break;
					case 7: Calibration.add(temp); break;
					case 8: BG5.add(temp); break;
					default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
					}
				}
			}
			scan.close();

		} catch (Exception e) {
			System.err.println("ERROR: File cannot be found");
		}	
	}
	
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

				SampleConcs.add(temp);

			}
			scan.close();
		} catch (Exception e) {
			System.err.println("ERROR: File cannot be found");
		}	
	}

	protected void PrintRawData() {

		System.out.println("Raw Data");

		for(int row = 0; row < 8; row++){
			for(int col = 0; col < 9; col++){
				switch(col){
				case 0: System.out.print(BG1.get(row) + "  "); break;
				case 1: System.out.print(Sample1.get(row) + "  "); break;
				case 2: System.out.print(BG2.get(row) + "  "); break;
				case 3: System.out.print(Sample2.get(row) + "  "); break;
				case 4: System.out.print(BG3.get(row) + "  "); break;
				case 5: System.out.print(Sample3.get(row) + "  "); break;
				case 6: System.out.print(BG4.get(row) + "  "); break;
				case 7: System.out.print(Calibration.get(row) + "  "); break;
				case 8: System.out.println(BG5.get(row));
				default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
				}
			}
		}

		System.out.println();
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
