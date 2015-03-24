package neuralnet;

import java.io.Serializable;


public class Layer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Neuron[] neurons;
	private int inputs;
	private int size;
	private double[] layerOutput;
	private String name;
	
	public Layer(int inputs, int amountOfNeurons, String layerName){
		this.inputs = inputs;
		size = amountOfNeurons;
		neurons = new Neuron[amountOfNeurons];
		for(int i = 0; i < size; i++) neurons[i] = new Neuron(inputs);
		layerOutput = new double[size];
		name = layerName;
	}
	
	public void printWeights(){
		double[] weights;
		for(int i = 0; i < neurons.length; i++) {
			System.out.println(name + " -- neuron " + i);
			weights = neurons[i].getWeights();
			for(int j = 0; j < weights.length; j++){
				System.out.print(weights[j] + ",");
			}
			System.out.println();
		}		
	}
	
	
	public String getName() { return name; }
	
	
	public double[] applyInput(double[] input) throws Exception {
		if(input.length != inputs) throw new Exception("input lengths do not match!!");
		for(int i = 0; i < neurons.length; i++){
			layerOutput[i] = neurons[i].apply(input);
		}
		return layerOutput;
	}
	
	public double[] getOutput() { return layerOutput; }
	
	public Neuron getNeuron(int index) { return neurons[index]; }
	
	public int getInputLength() { return inputs; }
	
	public int getSize() { return size; }
	

}
