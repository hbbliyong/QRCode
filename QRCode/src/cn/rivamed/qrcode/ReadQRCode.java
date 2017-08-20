package cn.rivamed.qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import jp.sourceforge.qrcode.QRCodeDecoder;
import jp.sourceforge.qrcode.data.QRCodeImage;

public class ReadQRCode {

	public static void main(String[] args) throws IOException {
		File file=new File("D:/qrcode.png");
		BufferedImage bufferedImage=ImageIO.read(file);
		QRCodeDecoder codeDecoder=new QRCodeDecoder();
		String result=new String(codeDecoder.decode(new QRCodeImage() {
			
			@Override
			public int getWidth() {
				// TODO Auto-generated method stub
				return bufferedImage.getWidth();
			}
			
			@Override
			public int getPixel(int arg0, int arg1) {
				// TODO Auto-generated method stub
				return bufferedImage.getRGB(arg0, arg1);
			}
			
			@Override
			public int getHeight() {
				// TODO Auto-generated method stub
				return bufferedImage.getHeight();
			}
		}),"gb2312");
		System.out.println(result);
	}

}
