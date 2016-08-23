package enzymeKinetcs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProcessPicture {
	
	BufferedImage image = null;	
	int[][] pixels;
	int[][] reds;
	int[][] greens;
	int[][] blues;
	int width = 0;
	int height = 0;
	String filename;
	
	public ProcessPicture(String filename){
		
		this.filename = filename;
	}
	
	public void readImage(){
		
		System.out.println("Reading image: " + filename);
		
		try {
			image = ImageIO.read(new File(filename));
		} catch (IOException e){
			System.err.println("ERROR: Cannot read file.");
		}
		
		width = image.getWidth();
		System.out.println("width: " + width);
		height = image.getHeight();
		System.out.println("height: " + height);
		pixels = new int[height][width];
		reds = new int[height][width];
		greens = new int[height][width];
		blues = new int[height][width];
		
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
		
		
	}
	
	public void writeImage(String filename){		
		
		BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				temp.setRGB(j, i, pixels[i][j]);
			}
		}
		
		try {
			ImageIO.write(temp, "jpg", new File(filename));
		} catch (IOException e) {
			System.err.println("ERROR: Cannot write image.");
		}
	}
	
	public void printImage(){
		 
		for(int i = 0; i < height; i++){
			for(int j = 0; j < width; j++){
				System.out.println("Pixel " + j + "," + i + ": [" + reds[i][j] + "," + greens[i][j] + "," + blues[i][j] + "]");
			}
		}
	}

}

