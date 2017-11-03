package util.math;

import java.util.Random;

public class TerrainGenerator {
	
	private static Random rand = new Random();
	
	/**
	 * 
	 * @param width
	 * @param height
	 * @param patchSize
	 * @param patchFrequency
	 * @param centerWeighting
	 * @return
	 */
	public static boolean[][] generateTerrain(int width, int height, int patchSize, double patchFrequency, double centerWeighting) {
		
		int centerX = width/2;
		int centerY = height/2;
		boolean[][] vals = new boolean[width][height];
		double maxDist = distance2(centerX, centerY, width, centerX);
		
		for (int i = 0; i < width/patchSize; i++) {
			for (int j = 0; j < height/patchSize; j++) {
				int x = -1;
				int y = -1;
				while (x < 0 || x >= width || y < 0 || y >= height) {
					x = i*patchSize + rand.nextInt(patchSize);
					y = j*patchSize + rand.nextInt(patchSize);
				} 
				
				double distFactor = distance2(centerX, centerY, x, y) / maxDist;
				double randNum = rand.nextDouble() * distFactor;
				if (distFactor > centerWeighting) {
					break;
				}
				
				int widthVary = rand.nextInt(patchSize/4 * 2) - patchSize/4;
				int heightVary = rand.nextInt(patchSize/4 * 2) - patchSize/4;
				for (int m = -widthVary + x; m < x + patchSize + widthVary; m++) {
					for (int n = -heightVary + y; n < y + patchSize + heightVary; n++) {
						double chance = rand.nextDouble();
						if (chance < patchFrequency && m >= 0 && m < width && n >= 0 && n < height) {
							vals[m][n] = true;
						}
					}
				}
			}
		}
		
		return vals;
		
	}
	
	private static double distance2(int centerX, int centerY, int x, int y) {
		int dX = x - centerX;
		int dY = y - centerY;
		return dX*dX + dY*dY;	
	}

}
