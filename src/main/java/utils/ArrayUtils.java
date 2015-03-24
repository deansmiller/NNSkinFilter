package utils;

import Jama.Matrix;

public class ArrayUtils {
	
	public static double mean(double[] array){
		double mean = 0;
		for(int i = 0; i < array.length; i++){
			mean += array[i];
		}
		return mean / array.length;
	}
	
	public static void print(double[] array){
		for(int i = 0; i < array.length; i++)
			System.out.print(array[i] + ",");
		System.out.println();
	}
	
	public static void print(Matrix m){
		for(int i = 0; i < m.getRowDimension(); i++){
			for(int j = 0; j < m.getColumnDimension(); j++){
				System.out.print(m.get(i, j) + ",");
			}
			System.out.println();
		}
		
	}
	
	public static double[] reverse(double[] arr){
		double[] tmp = new double[arr.length];
		int x = 0;
		for(int i = arr.length-1; i >= arr.length-1; i--){
			tmp[x] = arr[i];
			x++;
		}
		return tmp;
	}
	
	public static double[] subtract(double[] a, double[] b) throws Exception {
		if(a.length != b.length) throw new Exception("Arrays have to be of the same length");
		double[] tmp = new double[a.length];
		for(int i = 0; i < a.length; i++){
			tmp[i] = a[i] - b[i];
			//if(tmp[i] < 0) tmp[i] = 0;
		}
		
		return tmp;
	}
	
	public static double[] subtract(double[] a, double scalar) throws Exception {
		double[] tmp = new double[a.length];
		for(int i = 0; i < a.length; i++){
			tmp[i] = a[i] - scalar;
		}
		
		return tmp;
	}
	
	public static double[] plus(double[] a, double[] b) throws Exception {
		if(a.length != b.length) throw new Exception("Arrays have to be of the same length");
		double[] tmp = new double[a.length];
		for(int i = 0; i < a.length; i++){
			tmp[i] = a[i] + b[i];
		}
			
		
		return tmp;
	}
	
	public static double[] getColumn(Matrix m, int col) throws Exception {
		if(col > m.getColumnDimension()) throw new Exception("Column index out of bounds!");
		double[] column = new double[m.getRowDimension()];
		
		for(int i = 0; i < m.getRowDimension(); i++){
			column[i] = m.get(i, col);
		}

		return column;
	}
	
	public static void dimensions(Matrix m){
		System.out.println(m.getRowDimension() + "," + m.getColumnDimension());
	}
	
	public static void dimension(double[] arr){
		System.out.println(arr.length);
	}
	
	public static Matrix toMatrix(double[] vector, int rows, int cols) throws Exception {
		Matrix m = null;
		double[][] tmp = new double[rows][cols];
		int x = 0;
		for(int i = 0; i < rows; i++){
			x = 0;
			for(int j = 0; j < cols; j++){
				tmp[i][j] = vector[i];
			}
			
		}
		m = new Matrix(tmp);
		return m;
	}
	
	public static double magnitude(double[] vector){
		double sum = 0;
		for(int i = 0; i < vector.length; i++){
			sum += Math.pow(vector[i], 2);
		}
		return Math.sqrt(sum);
	}
	
	public static double sum(double[] a, double[] b) throws Exception{
		if(a.length != b.length) throw new Exception("Arrays have to be of the same length");
		double sum = 0;
		for(int i = 0; i < a.length; i++){
			sum += a[i] + b[i];
		}		
		return sum;
	}


}
