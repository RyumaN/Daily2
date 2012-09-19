package daily0908; //敵キャラ設定

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


public class Karasu{
	int char_x, char_y;
	BufferedImage bimage1;
	int waittime;

	public Karasu(BufferedImage b1, int index){
		bimage1 = b1;
		syutsugen();
		waittime = index * 20;
	}

	void syutsugen() {
		char_x = 600;
		char_y = (int) (Math.random() * 400);
		waittime = waittime + (int) (Math.random() * 30);

	}

	public void draw(Graphics g, ImageObserver io){

		if(waittime > 0){
			waittime = waittime - 1;
			return;
		}

		g.drawImage(bimage1, char_x, char_y, io);
		char_x = char_x - 4;
		if(char_x < -48)
			syutsugen();
	}


}