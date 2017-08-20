package cn.rivamed.qrcode;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class CreateQRCode {

	public static void main(String[] args) throws IOException {

		Qrcode x=new Qrcode();
		int version=7;
		x.setQrcodeErrorCorrect('M');//纠错等级
		x.setQrcodeEncodeMode('B');//N代表数字，A代表a-Z,B代表其它（中文等）
		
		x.setQrcodeVersion(version);//版本号
		String qrData="https://github.com/hbbliyong/QRCode.git";
		//int width=300;
		int width=67+12*(version-1);
		//int height=300;
		int height=67+12*(version-1);
		BufferedImage bufferedImage=new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		
		Graphics2D gs=bufferedImage.createGraphics();
		gs.setBackground(Color.WHITE);
		gs.setColor(Color.BLACK);
		gs.clearRect(0, 0, width, height);
		
		int pixoff=2;//偏移量，如果不加有可能会导致识别不准确
		//如果有汉字需要加上编码
		byte[] d=qrData.getBytes("gb2312");
		//byte[] d=qrData.getBytes();
		if(d.length>0&&d.length<120){
			boolean[][] s=x.calQrcode(d);
			
			for(int i=0;i<s.length;i++){
				for(int j=0;j<s.length;j++){
					if(s[j][i]){
						gs.fillRect(j*3+pixoff, i*3+pixoff, 3, 3);
					}
				}
			}
		}
		gs.dispose();
		bufferedImage.flush();
		
		ImageIO.write(bufferedImage, "png", new File("D:/qrcode.png"));
	}

}
