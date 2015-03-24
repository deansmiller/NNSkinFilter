package realtime;

import java.awt.Dimension;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamPanel;

public class VideoPanel extends WebcamPanel {

	private static final long serialVersionUID = 1L;

	
	public VideoPanel(Webcam webcam, boolean start) {
		super(webcam, start);
	}

	public VideoPanel(Webcam arg0, Dimension arg1, boolean arg2) {
		super(arg0, arg1, arg2);
	}

	public VideoPanel(Webcam webcam) {
		super(webcam);
	}
	
	public void webcamImageObtained(WebcamEvent we) {
	
	}
	
}
