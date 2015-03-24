package neuralnet;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;

import readers.PatternReader;


public class Main {
	

	public static void main(String[] args) {
		
		

		try {		
			Properties properties = new Properties();
			InputStream inputStream = new FileInputStream(args[0]);
			properties.load(inputStream);
			String trainingData = (String) properties.get("trainingDataPath");
			String trainedNetworkLocation = (String) properties.get("trainedNetworkPath");
			String learningRateStr= (String) properties.get("learningRate");
			String errorRateStr = (String) properties.get("errorRate");
			
			
			PatternReader train = new PatternReader(trainingData); 
			ArrayList<Pattern> trainingSet = new ArrayList<Pattern>();
			trainingSet.addAll(train.read());
			Collections.shuffle(trainingSet);
			Network n = new Network(3, 12, 1);
			n.setParameters(Double.parseDouble(learningRateStr), Double.parseDouble(errorRateStr));
			n.setPatterns(trainingSet);
			n.train();
			Collections.shuffle(trainingSet);
			n.testRun(trainingSet);
			n.persistNetwork(trainedNetworkLocation);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
