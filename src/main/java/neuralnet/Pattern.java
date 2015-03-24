package neuralnet;

import java.io.Serializable;

public class Pattern implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double[] input;
	protected double[] output;

	public Pattern(double[] input, double[] output){
		this.input = input;
		this.output = output;
	}
	
	public Pattern(String[] input, String[] output){
		this.input = new double[input.length];
		this.output = new double[output.length];
		for(int i = 0; i < input.length; i++) { this.input[i] = new Double(input[i]); }
		for(int i = 0; i < output.length; i++) { this.output[i] = new Double(output[i]); }
	}
	
	public double[] getInput(){ return input; }
	
	public double[] getOutput(){ return output; }
	
	public String toString(){
		String tmp = "";
		for(int i = 0; i < output.length; i++) tmp += output[i] + ",";
		return tmp;
	}
}
