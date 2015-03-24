package neuralnet;

import java.awt.Toolkit;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import Jama.Matrix;

public class Network implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Layer hiddenLayer, outputLayer;
	private double[] networkOutput;
	private double learningRate = 0.05;
	private double error = 0.05;
	private List<Pattern> patterns;
	private int inputs;
	
	
	public Network(int inputSize, int hiddenSize, int outputSize){
		inputs = inputSize;
		hiddenLayer = new Layer(inputSize, hiddenSize, "Hidden Layer");
		outputLayer = new Layer(hiddenSize, outputSize, "Output Layer");
	}
	
	public void persistNetwork(String path) throws FileNotFoundException, IOException{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
		oos.writeObject(this);
		oos.close();
	}
	
	public static Network createNetworkFromFile(String path) throws ClassNotFoundException, IOException{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(path)));
		Network n = (Network) ois.readObject();
		ois.close();
		return n;
	}
	
	public int getInputs() { return inputs; }
	
	public void printWeights(){
		hiddenLayer.printWeights();
		outputLayer.printWeights();
	}
	
	public void setParameters(double learningRate, double error){
		this.learningRate = learningRate;
		this.error = error;
	}
	
	public void applyInput(double[] input) throws Exception {
		if(input.length != inputs) throw new Exception("Input length incorrect! Expecting length: " + inputs + " but got: " + input.length);
		networkOutput = hiddenLayer.applyInput(input);
		networkOutput = outputLayer.applyInput(networkOutput);
	}
	
	public void applyInput(Matrix input) throws Exception {
		double[] i = input.getColumnPackedCopy();
		if(i.length != inputs) throw new Exception("Input length incorrect! Expecting length: " + inputs + " but got: " + i.length);
		networkOutput = hiddenLayer.applyInput(i);
		networkOutput = outputLayer.applyInput(networkOutput);
	}
	
	private double calculateMSE(double[] targetOutput, double[] networkOutput){
		double sum = 0;
		for(int i = 0; i < targetOutput.length; i++){
			sum += Math.pow((targetOutput[i] - networkOutput[i]), 2);
		}
		return Math.sqrt(sum);
	}
	
	private double[] calculateErrors(double[] targetOutput){
		double[] errors = new double[targetOutput.length];
		for(int i = 0; i < targetOutput.length; i++){
			errors[i] = (targetOutput[i] - networkOutput[i]) * networkOutput[i] * (1 - networkOutput[i]);
		}
		return errors;
	}
	
	private double[] calculateHiddenErrors(double[] outputErrors){
		double sum = 0;
		double[] errors = new double[hiddenLayer.getSize()];
		Neuron neuron;
		for(int i = 0; i < hiddenLayer.getSize(); i++){
			neuron = hiddenLayer.getNeuron(i);
			for(int j = 0; j < outputErrors.length; j++){
				sum += neuron.getWeightSum() * outputErrors[j];
			}
			errors[i] = neuron.getOutput() * (1 - neuron.getOutput()) * sum;
			sum = 0;
		}	
		return errors;
	}
	
	
	private void makeWeightChanges(double[] input, double[] output){
		double[] outputErrors = calculateErrors(output);
		double[] hiddenErrors = calculateHiddenErrors(outputErrors);
		double[] weights;
		double bias = 0;
		Neuron neuron;
			
		for(int i = 0; i < hiddenLayer.getSize(); i++){
			neuron = hiddenLayer.getNeuron(i);
			weights = neuron.getWeights();
			bias = neuron.getBias();
			for(int j = 0; j < input.length; j++){
				weights[j] += learningRate * hiddenErrors[i] * input[j];
			}
			neuron.setWeights(weights);
			bias += learningRate * hiddenErrors[i];
			neuron.setBias(bias);
		}
		
		for(int i = 0; i < output.length; i++){
			neuron = outputLayer.getNeuron(i);
			weights = neuron.getWeights();
			bias = neuron.getBias();
			for(int j = 0; j < hiddenLayer.getSize(); j++){
				weights[j] += learningRate * outputErrors[i] * hiddenLayer.getNeuron(j).getOutput();
			}
			neuron.setWeights(weights);
			bias += learningRate * outputErrors[i];
			neuron.setBias(bias);
		}

	}
	
	
	public void setPatterns(List<Pattern> patterns) { this.patterns = patterns; }
	
	
	public void train() throws Exception{
		double currentError = 0;
		int iteration = 0;
		System.out.println("Training...");
		do {
			for(Pattern pattern : patterns){
				applyInput(pattern.getInput());
				makeWeightChanges(pattern.getInput(), pattern.getOutput());
				currentError = calculateMSE(pattern.getOutput(), networkOutput);
			}
			iteration++;
			System.out.println(iteration + ": " + currentError);
		} while(currentError > error);
		Toolkit.getDefaultToolkit().beep();
		System.out.println("Training completed");
	}
	
	
	public void testRun(List<Pattern> testRuns) throws Exception{
		int correct = 0;
		for(Pattern pattern : testRuns){
			applyInput(pattern.getInput());
			System.out.println(stringNetworkOutput() + " " + pattern);
			if(stringNetworkOutput().equals(pattern.toString())) correct++;
		}
		
		System.out.println("Correct classifictions: " + correct);
		System.out.println("Test run sample count: " + testRuns.size());
	}
	
	
	private String stringNetworkOutput(){
		String tmp = "";
		for(int i = 0; i < networkOutput.length; i++){
			if(networkOutput[i] > 0.5) tmp += "1.0,";
			else tmp += "0.0,";
		}
		return tmp;
	}
	
	public double[] getOutput() { return networkOutput; }
	

}
