package Daily0906; //敵キャラ設定

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;


public class Karasu{
	int char_x, char_y;
	BufferedImage bimage1;

	Karasu(BufferedImage b1){
		bimage1 = b1;
		syutsugen();
	}

	void syutsugen() {
		char_x = 600;
		char_y = (int)(Math.random() * 400);

	}

	void draw(Graphics g, ImageObserver io){
		g.drawImage(bimage1, char_x, char_y, io);
		char_x = char_x - 4;
		if(char_x < -48)
			syutsugen();
	}


}