package readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import neuralnet.Pattern;

public class PatternReader implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Pattern> patterns;
	private BufferedReader bReader;
	private final int INPUT_COLUMN = 0;
	private final int OUTPUT_COLUMN = 1;
	private String delimiter = ":";

	public PatternReader(String filename) {
		patterns = new ArrayList<Pattern>();
		setDataFile(filename);
	}
	
	public void setDelimiter(String del){
		delimiter = del;
	}

	public List<Pattern> read() throws IOException{
		String currentLine;
		String[] tokens;
		while((currentLine = bReader.readLine()) != null){
			tokens = currentLine.split(delimiter);
			patterns.add(new Pattern(tokens[INPUT_COLUMN].split(","), tokens[OUTPUT_COLUMN].split(",")));
		}
		bReader.close();
		return patterns;

	}
	
	public void setDataFile(String filename){
		try {
			bReader = new BufferedReader(new FileReader(filename));
			patterns.clear();
		} catch(FileNotFoundException e){
			throw new Error(e);
		}
	}
		
}
