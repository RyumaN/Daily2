package Daily0904;

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
import javax.swing.JFrame;

public class FusenGameMain {
	JFrame frame1;
	BufferStrategy bstrategy;
	int cy = 200;
	boolean spkey = false;
	BufferedImage backimage, pimage, eimage;
	int count = 0;
	double speed;

		FusenGameMain(){
			frame1 = new JFrame("風船ゲーム");
			frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame1.setBackground(Color.WHITE);
			frame1.setResizable(false);


			frame1.setVisible(true);
			Insets insets = frame1.getInsets();
			frame1.setSize(600 + insets.left + insets.right, 400 + insets.top + insets.bottom);
			frame1.setLocationRelativeTo(null);
			frame1.setIgnoreRepaint(true);
			frame1.createBufferStrategy(2);
			bstrategy = frame1.getBufferStrategy();

			frame1.addKeyListener(new MyKeyAdapter());

			try{
				pimage = ImageIO.read(getClass().getResource("fusenman.png"));
				eimage = ImageIO.read(getClass().getResource("karasu.png"));
				backimage = ImageIO.read(getClass().getResource("back.jpg"));
			} catch (IOException e){
				e.printStackTrace();
			}

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


	class MyTimerTask extends TimerTask{

		@Override
		public void run() {
			Graphics g = bstrategy.getDrawGraphics();
			if(bstrategy.contentsLost() == false){
				Insets insets = frame1.getInsets();
				g.translate(insets.left, insets.top);

				if(spkey == true){
					speed = speed - 0.25;
				}else{
					speed = speed + 0.25;
				}
				if(speed < -6) speed = -6;
				if(speed > 6) speed = 6;
				cy = cy + (int)speed;
				if(cy < 0) cy = 0;

				g.clearRect(0, 0, 600, 400);
				g.drawImage(pimage, 270, cy, frame1);

				/*g.setColor(Color.BLUE);
				g.fillOval(250, cy, 100, 100);
				g.fillRect(0, 0, 100, 100);*/

				/*g.setFont(new Font("Serif", Font.PLAIN, 40));
				g.drawString("こんちわ Hello!", 100, 100);
				drawStringCenter("こんちわ Hello!", 100, g);
				g.setFont(new Font("SansSerif", Font.PLAIN, 60));
				g.drawString("こんちわ Hello!", 100, 140);
				drawStringCenter("こんちわ Hello!", 160, g);
				g.setFont(new Font("Monospaced", Font.PLAIN, 40));
				g.drawString("こんちわ Hello!", 100, 180);
				g.setFont(new Font("Dialog", Font.PLAIN, 40));
				g.drawString("こんちわ Hello!", 100, 220);
				g.setFont(new Font("DialogInput", Font.PLAIN, 40));
				g.drawString("こんちわ Hello!", 100, 260);
				g.setFont(new Font("DHP行書体", Font.PLAIN, 40));
				g.drawString("こんちわ Hello!", 100, 300);*/
				bstrategy.show();
				g.dispose();
				System.out.println(cy + "." + speed);
			}

		}

	}

	class MyKeyAdapter extends KeyAdapter{

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				spkey = true;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO 自動生成されたメソッド・スタブ
			if(e.getKeyCode() == KeyEvent.VK_SPACE){
				spkey = false;
		}

	}
}
}