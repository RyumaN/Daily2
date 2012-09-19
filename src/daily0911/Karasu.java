package daily0911; //敵キャラ設定

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


public class Karasu{
	int char_x, char_y;
	BufferedImage bimage1;
	int waittime;
	boolean spkey = true;
	double speed;

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

		if(char_x < -48){
			syutsugen();
		}

		if(spkey == true){
			speed = speed - 0.15;
		}else{
				speed = speed + 0.15;
		}

		if(speed > 4 || speed < -4){
			spkey = !spkey;
			char_y = char_y + (int)speed;
		}
	}

	boolean isAtari(int x, int y){

		int ax1 = x + 12;
		int ay1 = y + 12;
		int ax2 = x + 36;
		int ay2 = y + 36;
		int bx1 = char_x + 12;
		int by1 = char_y + 12;
		int bx2 = char_x + 36;
		int by2 = char_y + 36;

		if((ax1 < bx2) && (bx1 < ax2) && (ay1 < by2) && (by1 < ay2)){
			return true;
		}else{
			return false;
		}
	}


}