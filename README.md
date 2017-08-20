## 1.二维码分类
&emsp;&#8195;二维条码也有许多不同的码制，就码制的编码原理而言，通常分为三种类型。

1. **线性堆叠式二维码**

>编码原理：  
建立在一维条码基础之上，按需要堆积成两行或多行。

图示： 
![image](http://note.youdao.com/yws/api/personal/file/14040041F2954481A2F907C90A9301F0?method=download&shareKey=3adfa7e4aaab33fea8306a12ce499fff)
2. __矩阵式二维码__

>最常用编码，原理： 
在一个矩形空间通过黑白像素在矩阵中的不同分布进行编码。在矩阵相应的位置上，用点（方点、圆点或其它形状）的出现表示二进制“1”，点的不出现表示二进制的“0”

图示：
![image](http://note.youdao.com/yws/api/personal/file/8495B1577522425CAE066EFE51AF2235?method=download&shareKey=7b961692bd2173cabd327656744fb875)
3. __邮政码__
> 通过不同长度的条进行编码，主要用于邮政编码。

## 2.QR Code
&emsp;&emsp;现在最常用的就是这种，咱们现在主要介绍的也是这种。为啥这种使用二维码那么受反应呢？主要QR Code这种二维码有如下优点：
>1. 识读速度快  
>2. 数据密度大
>3. 占用空间小
### 2.1 QR Code介绍
![image](http://note.youdao.com/yws/api/personal/file/DB8FA217F7F0498C90F34BBB6C7B5D8A?method=download&shareKey=0702666948b17d1d7b43d2c255db4a74)
### 2.2 QR Code 结构
![image](http://note.youdao.com/yws/api/personal/file/3AA0B0E83F4C479E964259E9C4065E86?method=download&shareKey=3d341c0a8c144fab8f02c0c1cc9a5374)
大家可以了解下二维码的结构，知道大概就行了，如果想了解详细信息的话可以自行百度，国家有详细的二维码规范。

## 3.后台JAVA代码实现二维码(QR Code)生成
&emsp;&emsp;这里介绍如下两种实现方式：
1. Java 后台实现，主要使用zxing和qrcodejar等第三方jar包。
2. 前端javascript实现，主要使用jquery.qrcode.js

### 3.1 使用zxing生成二维码
#### 3.1.1 zxing相关网站
[zxing的GitHub](https://github.com/zxing/zxing)  
[zxing的Java文档](https://zxing.github.io/zxing/apidocs/)

#### 3.1.2 生成zxing jar包
由于github上没有相关的jar包，所以需要我们自己生成一下，上面有好多关于android相关的，我们只需要选取核心包和javase这两部分代码。既下图矩形框内容：
![image](http://note.youdao.com/yws/api/personal/file/263162462A304B37A4CC5AF94F77E405?method=download&shareKey=248fa658839a418e2a8f203072bb33ae)
生成方式我大致说下：首先在ecplise里新建一个java项目zxing,将刚才画框代码拷贝进去，然后导出jar包即可。如果你不想生成也可以在我的[github](https://github.com/hbbliyong/QRCode.git)上自行下载。
### 3.1.3 生成二维码代码
````
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

````
生成的结果如下：
![image](http://note.youdao.com/yws/api/personal/file/EB60F00E22734E8DA43D515760528D4F?method=download&shareKey=853f823d25ad810ab5dd965994ab76f6)

由于代码都有详细注释，我就不一一讲解了，有疑问可以留言，我们一块探讨。

### 3.1.4 解析二维码代码
````
package cn.rivamed.zxing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ReadQRCode {

	public static void main(String[] args) {
		try {
			MultiFormatReader formatReader=new MultiFormatReader();
			File file=new File("D:/zxingQRCode.png");
			BufferedImage image=ImageIO.read(file);
			BinaryBitmap binaryBitmap=new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
			
			HashMap hints=new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");//编码
			
			Result result=formatReader.decode(binaryBitmap, hints);
			System.out.println("解析结果："+result.toString());
			System.out.println("二维码格式类型："+result.getBarcodeFormat());
			System.out.println("二维码文本"+result.getText());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

````

### 3.2 使用qrcode生成解析二维码
#### 3.2.1 生成二维码

```
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

````

生成的结果如下：

![image](http://note.youdao.com/yws/api/personal/file/4604D3AB439E4E6B831C11C71A63EB24?method=download&shareKey=0f6a576afc21bcbd89f099592265432c)
>这里需要注意的是，二维码长宽不能想zxing之直接定义，需要跟进这个公式生成67+12*(version-1)。比如我直接定义二维码的长宽为300.就会变成如下样子。
![image](http://note.youdao.com/yws/api/personal/file/75964660BF1741D0BFFBF4F2E151CC0B?method=download&shareKey=3267e41af565d8758f76d1381e088994)这上面空白看的不是太清，你把图片下载下载下来看就比较明显了。

#### 3.2.2 解析二维码

```
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

```

## 4.前台代码jquery生成二维码
### 4.1 [jquery.qrcode.js 的 GitHub](https://github.com/jeromeetienne/jquery-qrcode)
### 4.2 相关代码

```
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>二维码生成</title>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/js/jquery.qrcode.min.js"></script>
</head>
<body>
生成的二维码如下：<br>
<dir id="qrcode"></dir>
<script type="text/javascript">
jQuery('#qrcode').qrcode('https://github.com/hbbliyong/QRCode.git');
</script>
</body>
</html>
```

## 5.结束语
> 所有的代码我都上传到了github上面，大家可以下载运行。这里面介绍的都比较基础的，但也包含了前端后台多种方式，对于简单的应用已经足够了。至于一些扩展，如果加上logo啊，电子名品啊，大家可以自行摸索。感谢您的观看，如果有什么疑问可以留言。


ps:
一个在线生成二维码的网站推荐：[在线工具](http://tool.oschina.net/qr)
这个工具也是使用的zxing





   

