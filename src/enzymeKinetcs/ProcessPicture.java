package src.enzymeKinetcs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ProcessPicture {
	
	BufferedImage image = null;	
	String filename;
	protected ArrayList<Double> BG1;
	protected ArrayList<Double> BG2;
	protected ArrayList<Double> BG3;
	protected ArrayList<Double> BG4;
	protected ArrayList<Double> BG5;
	protected ArrayList<Double> Sample1;
	protected ArrayList<Double> Sample2;
	protected ArrayList<Double> Sample3;
	protected ArrayList<Double> Calibration;
	
	public ProcessPicture(String filename){
		
		this.filename = filename;
	}
	
	public void ExtractPixels(ArrayList<Integer[]> pixels){
		
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e){
			System.err.println("ERROR: Cannot read file.");
		}
		
		BG1 = new ArrayList<Double>();
		BG2 = new ArrayList<Double>();
		BG3 = new ArrayList<Double>();
		BG4 = new ArrayList<Double>();
		BG5 = new ArrayList<Double>();
		Sample1 = new ArrayList<Double>();
		Sample2 = new ArrayList<Double>();
		Sample3 = new ArrayList<Double>();
		Calibration = new ArrayList<Double>();
		int pixelCounter = 0;
		
		for(int row = 0; row < 8; row++){
						
			for(int col = 0; col < 9; col++){
				
				Integer[] xy = pixels.get(pixelCounter);
				int x = xy[0];
				int y = xy[1];
				
				System.out.print("(" + x + "," + y + ") ");
				int pixel = image.getRGB(x, y);
				int green = (pixel >> 8) & 255;
				double value = Math.abs(green - 255);
				
				switch(col){
					case 0: BG1.add(value); break;
					case 1: Sample1.add(value); break;
					case 2: BG2.add(value); break;
					case 3: Sample2.add(value); break;
					case 4: BG3.add(value); break;
					case 5: Sample3.add(value); break;
					case 6: BG4.add(value); break;
					case 7: Calibration.add(value); break;
					case 8: BG5.add(value); break;
					default: System.err.println("ERROR: Reached the default case. That's weird..."); break;
				}

				pixelCounter++;
			}
			System.out.println();
		}
	}
	
	protected void PrintRawData() {

		System.out.println("Raw Data");

		for(int row = 0; row < 8; row++){
				System.out.print(BG1.get(row) + "\t");
				System.out.print(Sample1.get(row) + "\t");
				System.out.print(BG2.get(row) + "\t"); 
				System.out.print(Sample2.get(row) + "\t"); 
				System.out.print(BG3.get(row) + "\t"); 
				System.out.print(Sample3.get(row) + "\t"); 
				System.out.print(BG4.get(row) + "\t"); 
				System.out.print(Calibration.get(row) + "\t"); 
				System.out.println(BG5.get(row));
		}

		System.out.println();
	}
}

