package neuralnet;

import Jama.Matrix;

public class Data {
	
	private Matrix data;
	private int rows, columns;
	private String label;
	
	public Data(double[][] data, int rows, int columns){
		this.data = new Matrix(data);
		this.rows = rows;
		this.columns = columns;
	}
	
	public Data(Matrix m){
		this.data = m;
		this.rows = m.getRowDimension();
		this.columns = m.getColumnDimension();
	}
	
	public Data(String[][] data, int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		double[][] tmp = new double[rows][columns];
		for(int r = 0; r < rows; r++){
			for(int c = 0; c < columns; c++){
				tmp[r][c] = new Double(data[r][c]);
			}
		}
		this.data = new Matrix(tmp);
	}
	
	public int getColumns(){
		return columns;
	}
	
	public int getRows(){
		return rows;
	}
	
	public static Data dataFromArray(double[] arr, int rows, int columns){
		double[][] tmp = new double[rows][columns];
		int index = 0;
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				tmp[row][column] = arr[index];
				index++;
			}
		}
		
		return new Data(tmp, rows, columns);
	}
	
	
	public void setLabel(String label){
		this.label = label;
	}
	
	public String getLabel(){
		return label;
	}
	
	public void print(){
		double[][] d = data.getArray();
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				System.out.print(d[i][j] + ",");
			}
			System.out.println();
		}		
	}
	
	public Matrix getMatrix(){
		return data;
	}
}
