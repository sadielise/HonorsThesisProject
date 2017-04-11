package enzymeKinetics;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadConfigurationData {

	protected String InputFile;
	protected ArrayList<Double> SubstrateConcs;
	protected ArrayList<Double> CalibrationConcs;
	protected String ColorChannel;
	
	public ReadConfigurationData(String fileName){
		this.InputFile = fileName;
	}
	
	protected void ReadData(){

		SubstrateConcs = new ArrayList<Double>();
		CalibrationConcs = new ArrayList<Double>();
		double temp = 0;

		try {
			Scanner scan = new Scanner(new File(this.InputFile));

			for(int i = 0; i < 8; i++){

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

				SubstrateConcs.add(temp);
			}
			
			for(int j = 0; j < 8; j++){

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
			
			this.ColorChannel = scan.next().toLowerCase();
			
			scan.close();
		} catch (Exception e) {
			System.err.println("ERROR: File cannot be found");
		}	
	}
	
	protected void PrintConfigurationData(PrintWriter pw){
		
		pw.println("Substrate Concentrations");
		for(int row = 0; row < SubstrateConcs.size(); row ++){
			pw.println(SubstrateConcs.get(row));
		}
		pw.println();
		
		pw.println("Calibration Concentrations");
		for(int row = 0; row < CalibrationConcs.size(); row ++){
			pw.println(CalibrationConcs.get(row));
		}
		pw.println();
		
		pw.println("Color Channel: " + this.ColorChannel);
		pw.println();
	}
}
