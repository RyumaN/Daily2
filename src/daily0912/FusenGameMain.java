package daily0912;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

import daily0912.Karasu;


public class FusenGameMain {
	JFrame frame1;
	BufferStrategy bstrategy;
	int cy = 200;
	boolean spkey = false;
	BufferedImage backimage, pimage, eimage;
	double speed;
	boolean isgameover = false;
	Karasu[] karasus = new Karasu[6];
	Clip clip1, clip2;
	Sequencer midiseq = null;

		FusenGameMain(){
			frame1 = new JFrame("風船ゲーム");
			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame1.setBackground(Color.WHITE);
			frame1.setResizable(false);


			frame1.setVisible(true);
			Insets insets = frame1.getInsets();
			frame1.setSize(600 + insets.left + insets.right, 400 + insets.top + insets.bottom);
			frame1.setLocationRelativeTo(null);

			frame1.createBufferStrategy(2);
			bstrategy = frame1.getBufferStrategy();
			frame1.setIgnoreRepaint(true);

			frame1.addKeyListener(new MyKeyAdapter());

			try{
				pimage = ImageIO.read(getClass().getResource("fusenman.png"));
				eimage = ImageIO.read(getClass().getResource("karasu.png"));
				backimage = ImageIO.read(getClass().getResource("back.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}

			clip1 = otoYomikomi("fall01.wav");
			clip2 = otoYomikomi("power05.wav");
			midiYomikomi("toccata.mid");

			for(int i = 0; i < karasus.length; i = i + 1){
				karasus[i] = new Karasu(eimage, i);
			}

			midiseq.start();

			Timer t = new Timer();
			t.schedule(new MyTimerTask(), 10, 500);
	}

	public static void main(String[] args) {
		FusenGameMain gtm = new FusenGameMain();

	}

	void drawStringCenter(String str, int y, Graphics g){
		int fw = frame1.getWidth() / 2;  //ウィンドウ幅÷2
		FontMetrics fm = g.getFontMetrics();  //文字列幅÷2
		int strw = fm.stringWidth(str) / 2;  //文字表示
		g.drawString(str, fw - strw, y);
	}

	Clip otoYomikomi(String fname){
		Clip clip = null;

		try{
		AudioInputStream aistream = AudioSystem.
				getAudioInputStream(getClass().getResource(fname));
		DataLine.Info info = new DataLine.Info(Clip.class, aistream.getFormat());
		clip = (Clip)AudioSystem.getLine(info);
		clip.open(aistream);

		} catch (UnsupportedAudioFileException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		} catch (LineUnavailableException e){
			e.printStackTrace();
		}
		return clip;
	}

	void midiYomikomi(String fname){
		if(midiseq == null){
			try {
				midiseq = MidiSystem.getSequencer();
				midiseq.open();
			} catch (MidiUnavailableException e) {
				e.printStackTrace();
			}
		}

		try{
				Sequence seq = MidiSystem.getSequence(getClass().getResource(fname));
				midiseq.setSequence(seq);
		} catch (InvalidMidiDataException e){
				e.printStackTrace();
		} catch(IOException e){
				e.printStackTrace();
		}
	}


	class MyTimerTask extends TimerTask{

		@Override
		public void run() {
			Graphics g = bstrategy.getDrawGraphics();
			if(bstrategy.contentsLost() == false){
				Insets insets = frame1.getInsets();
				g.translate(insets.left, insets.top);

				if(isgameover == true){
					midiseq.stop();
					clip1.start();
					g.setColor(Color.RED);
					g.setFont(new Font("Sanserif", Font.BOLD, 80));
					drawStringCenter("GAMEOVER", 200, g);
					bstrategy.show();
					g.dispose();
					return;
				}

				if(spkey == true){
					speed = speed - 0.25;
					if(clip2.isRunning() == false){
						clip2.setFramePosition(0);
						clip2.start();
					}
				}else{
					speed = speed + 0.25;
				}
				if(speed < -6) speed = -6;
				if(speed > 6) speed = 6;
				cy = cy + (int)speed;
				if(cy < 0) cy = 0;
				if(cy > 448) isgameover = true;

				g.drawImage(backimage, 0, 0, frame1);
				g.drawImage(pimage, 270, cy, frame1);

				for(int i = 0; i<karasus.length; i = i + 1){
					karasus[i].draw(g, frame1);
				}

				for(int i = 0; i < karasus.length; i = i +1){
					if(karasus[i].isAtari(270, cy) == true)
						isgameover = true;
				}

				bstrategy.show();
				g.dispose();

			}

		}

	}

	class MyKeyAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {

			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				spkey = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {

			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				spkey = false;
		}

	}
}
}