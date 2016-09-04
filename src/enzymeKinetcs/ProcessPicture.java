package enzymeKinetcs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ProcessPicture {
	
	BufferedImage image = null;	
	double[][] pixels;
	double[][] reds;
	double[][] greens;
	double[][] blues;
	int width = 0;
	int height = 0;
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
	
	public void ReadImage(){
		
		System.out.println("Reading image: " + filename);
		
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e){
			System.err.println("ERROR: Cannot read file.");
		}
		
		width = image.getWidth();
		height = image.getHeight();
		System.out.println("width: " + width);
		System.out.println("height: " + height);
		pixels = new double[height][width];
		reds = new double[height][width];
		greens = new double[height][width];
		blues = new double[height][width];
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				int pixel = image.getRGB(j, i);
				pixels[i][j] = pixel;
								
				int red = (pixel >> 16) & 255;
				reds[i][j] = red;
				
				int green = (pixel >> 8) & 255;
				greens[i][j] = green;
				
				int blue = pixel & 255;
				blues[i][j] = blue;	
			}
		}		
	}
	
	public void ExtractPixels(){
		
		BG1 = new ArrayList<Double>();
		BG2 = new ArrayList<Double>();
		BG3 = new ArrayList<Double>();
		BG4 = new ArrayList<Double>();
		BG5 = new ArrayList<Double>();
		Sample1 = new ArrayList<Double>();
		Sample2 = new ArrayList<Double>();
		Sample3 = new ArrayList<Double>();
		Calibration = new ArrayList<Double>();
		int startX = 220;
		int startY = 424;
		
		for(int row = 0; row < 8; row++){
			
			startX = 220; //reset X
			
			for(int col = 0; col < 9; col++){
				
				double value = greens[startY][startX];
				
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
				startX += 180; //increment X
			}
			startY += 155; //increment Y
		}
	}
	
	protected void PrintRawData() {

		System.out.println("Raw Data");

		for(int row = 0; row < 8; row++){
				System.out.print(BG1.get(row) + "  ");
				System.out.print(Sample1.get(row) + "  ");
				System.out.print(BG2.get(row) + "  "); 
				System.out.print(Sample2.get(row) + "  "); 
				System.out.print(BG3.get(row) + "  "); 
				System.out.print(Sample3.get(row) + "  "); 
				System.out.print(BG4.get(row) + "  "); 
				System.out.print(Calibration.get(row) + "  "); 
				System.out.println(BG5.get(row));
		}

		System.out.println();
	}
}

