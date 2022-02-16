
public class MatrixFunctions {
	
	public int[] translate(int[] arr, int tX, int tY) {
		int x1 = arr[0] + tX;
		int y1 = arr[1] + tY;
		int x2 = arr[2] + tX;
		int y2 = arr[3] + tY;
		int[] arr2 = {x1, y1, x2, y2};
		return arr2;
	} // translate method
	
	public int[] basicSc(int[] arr, int sX, int sY) {
		int x1 = arr[0] * sX;
		int y1 = arr[1] * sY;
		int x2 = arr[2] * sX;
		int y2 = arr[3] * sY;
		int[] arr2 = {x1, y1, x2, y2};
		return arr2;
	} // basicSc method
	
	public int[] basicRot(int[] arr, double angle) {
		double rad = Math.toRadians(angle);
		double x1 = (double)arr[0] * Math.cos(rad) + (double)arr[1] * Math.sin(rad);
		double y1 = -(double)arr[0] * Math.sin(rad) + (double)arr[1] * Math.cos(rad);
		double x2 = (double)arr[2] * Math.cos(rad) + (double)arr[3] * Math.sin(rad);
		double y2 = -(double)arr[2] * Math.sin(rad) + (double)arr[3] * Math.cos(rad);;
		
		int[] arr2 = {(int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2)};
		return arr2;
	} // basicRot method
	
	public int[] scale(int[] arr, int sX, int sY, int cX, int cY) {
		int x1 = (cX - cX * sX) + arr[0] * sX;
		int y1 = (cY - cY * sY) + arr[1] * sY;
		int x2 = (cX - cX * sX) + arr[2] * sX;
		int y2 = (cY - cY * sY) + arr[3] * sY;
		int[] arr2 = {x1, y1, x2, y2};
		return arr2;
	} // scale method
	
	public int[] rotate(int[] arr, double angle, double cX, double cY) {
		double rad = Math.toRadians(angle);
		double x1 = arr[0] * Math.cos(rad)
					+ arr[1] * Math.sin(rad)
					+ cX - cX * Math.cos(rad)
					- cY * Math.sin(rad);
		double y1 = -arr[0] * Math.sin(rad)
				+ arr[1] * Math.cos(rad)
				+ cY + cX * Math.sin(rad)
				- cY * Math.cos(rad);
		double x2 = arr[2] * Math.cos(rad)
				+ arr[3] * Math.sin(rad)
				+ cX - cX * Math.cos(rad)
				- cY * Math.sin(rad);
		double y2 = -arr[2] * Math.sin(rad)
				+ arr[3] * Math.cos(rad)
				+ cY + cX * Math.sin(rad)
				- cY * Math.cos(rad);
		
		int[] arr2 = {(int) Math.round(x1), (int) Math.round(y1), (int) Math.round(x2), (int) Math.round(y2)};
		return arr2;
	} // rotate method
	
} // MatrixFunctions class
