package neuralnet;

import java.io.Serializable;
import java.util.Random;

public class Neuron implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numberOfInputs;
	private double[] weights;
	private double bias, output;
	static Random random = new Random();
	
	public Neuron(int numberOfInputs){
		this.numberOfInputs = numberOfInputs;
		weights = new double[numberOfInputs];
		for(int i = 0; i < weights.length; i++) weights[i] = random.nextGaussian();
		bias = random.nextGaussian();
	}
	
	public double apply(double[] input) throws Exception{
		if(input.length != numberOfInputs) throw new Exception("Incorrect input length");
		double sum = 0;
		for(int i = 0; i < weights.length; i++){
			sum += input[i] * weights[i];
		}
		output = 1 / (1 + Math.exp(-sum + bias));
		return output;
	}
	
	public double[] getWeights(){ return weights; }
	
	public void setWeights(double[] weights) { this.weights = weights; }
	
	public double getWeightSum(){
		double sum = 0;
		for(int i = 0; i < weights.length; i++){
			sum += weights[i];
		}
		return sum;
	}
	
	public double getBias() { return bias; }
	
	public double setBias(double bias) { return this.bias = bias; }
	
	public double getOutput() { return output; }

}
