package com.sinosoft.report.f1report;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

//import com.swetake.util.Qrcode;

public class DBarCode
{
private static Logger logger = Logger.getLogger(DBarCode.class);

	// 输出文件类型
	public static String FORMAT_JPEG = "jpg";

	public static String FORMAT_GIF = "gif";

	public static String FORMAT_PNG = "png";

	public static String FORMAT_BMP = "bmp";


	private BufferedImage mBufferedImage = null;

	private String mCodeStr = ""; // 要编码的原始文本


	public DBarCode(String cCode)
	{
		this.mCodeStr = cCode;
	}

	// 将条码图片转换成字节数组
	public byte[] getBytes() throws IOException
	{
//		generatedImage();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		GifEncoder gif = new GifEncoder(mBufferedImage, bos);
		gif.encode();
		return bos.toByteArray();
	}
	
	public BufferedImage getImage()
	{
//		generatedImage();
		return mBufferedImage;
	}

	// 生成条码图像
//	public void generatedImage()
//	{
//		Qrcode qrcode = new Qrcode();
//		qrcode.setQrcodeErrorCorrect('M');
//		qrcode.setQrcodeEncodeMode('B');
//		qrcode.setQrcodeVersion(7);
//
//		String testString = mCodeStr;
//
//		byte[] d = null;
//		try
//		{
//			d = testString.getBytes("GBK");
//		}
//		catch (UnsupportedEncodingException e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		mBufferedImage = new BufferedImage(139, 139, BufferedImage.TYPE_INT_RGB);
//
//		// createGraphics
//		Graphics2D g = mBufferedImage.createGraphics();
//
//		// set background
//		g.setBackground(Color.WHITE);
//		g.clearRect(0, 0, 139, 139);
//
//		g.setColor(Color.BLACK);
//
//		if (d.length > 0 && d.length < 123)
//		{
//			boolean[][] b = qrcode.calQrcode(d);
//
//			for (int i = 0; i < b.length; i++)
//			{
//
//				for (int j = 0; j < b.length; j++)
//				{
//					if (b[j][i])
//					{
//						g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3);
//					}
//				}
//
//			}
//		}
//
//		g.dispose();
//		mBufferedImage.flush();
//	}

}
