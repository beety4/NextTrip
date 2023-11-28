package config;

import java.util.Random;

public class RandModule {
	private Random rand = new Random();
	private final int[] arrCode = {1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33,
								   34, 35, 36 ,37, 38, 39};
	
    public int getRndInt(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }
    
	public int getRndAreaCode() {
		int i = getRndInt(0, 16);
		return arrCode[i];
	}
	
	
}
