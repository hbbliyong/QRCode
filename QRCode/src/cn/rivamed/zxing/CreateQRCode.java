package cn.rivamed.zxing;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class CreateQRCode {
	public static void main(String[] args) {
		
		int width=300;
		int height=300;
		
		String format="png";
		//这里如果你想自动跳转的话，需要加上https://
		String content="https://github.com/hbbliyong/QRCode.git";
		
		HashMap hits=new HashMap();
		hits.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
		//纠错等级，纠错等级越高存储信息越少
		hits.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		//边距
		hits.put(EncodeHintType.MARGIN, 2);
		
		try {
			BitMatrix bitMatrix=new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height,hits);
			//如果做网页版输出可以用输出到流
			//MatrixToImageWriter.writeToStream(matrix, format, stream);
			Path path=new File("D:/zxingQRCode.png").toPath();
			MatrixToImageWriter.writeToPath(bitMatrix, format, path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("that is all");
	}
}
