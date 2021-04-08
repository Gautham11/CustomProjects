package display;

public class RGB {
	
	private int red ;
	private int green;
	private int blue;
	
	RGB(int r, int g, int b){
		this.red = r;
		this.green = g;
		this.blue = b;
			
	}

	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	
	public String toString() {
		return "R" + this.getRed() +"B"+ this.getBlue() +"G"+this.getGreen();
		
	}

}
