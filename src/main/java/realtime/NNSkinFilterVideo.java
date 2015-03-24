package realtime;



import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.JFrame;

import neuralnet.Network;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;

public class NNSkinFilterVideo extends JFrame implements Runnable {

	private static final long serialVersionUID = -585739158170333370L;
	private static final int INTERVAL = 100;
	private Webcam webcam = Webcam.getDefault();
	private static final int VIDEO_WIDTH = 320;
	private static final int VIDEO_HEIGHT = 240;
	
	private class ImageFilterer implements WebcamImageTransformer {
		private NNSkinFilter filter;
		private SkinLocalizer skinner;
		
		public ImageFilterer(NNSkinFilter filter) {
			this.filter = filter;
			skinner = new SkinLocalizer(320, 240);
			
		}

		@Override
		public BufferedImage transform(BufferedImage image) {
			try {
				image = filter.filterSkinPixels(image);
				image = skinner.showoff(image);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return image;
		}
	}


	public NNSkinFilterVideo(String networkPath, double threshold) throws Exception {
		Thread updater = new Thread(this, "updater-thread");
		updater.setDaemon(true);
		updater.start();
		setTitle("Javris");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		webcam.setViewSize(new Dimension(VIDEO_WIDTH, VIDEO_HEIGHT));
		Network skinNN = Network.createNetworkFromFile(networkPath);
		webcam.setImageTransformer(new ImageFilterer(new NNSkinFilter(skinNN, threshold, VIDEO_WIDTH, VIDEO_HEIGHT)));
		webcam.open();
		VideoPanel panel = new VideoPanel(webcam);
		add(panel);
		pack();
		setVisible(true);
	}

	public static void main(String[] args) throws Exception {
		Properties properties = new Properties();
		InputStream inputStream = new FileInputStream(args[0]);
		properties.load(inputStream);
		String networkPath = (String) properties.get("networkPath");
		double threshold = Double.parseDouble((String) properties.get("threshold"));
		new NNSkinFilterVideo(networkPath, threshold);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(INTERVAL * 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
