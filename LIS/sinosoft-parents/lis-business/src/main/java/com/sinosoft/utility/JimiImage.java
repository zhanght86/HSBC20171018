package com.sinosoft.utility;
import org.apache.log4j.Logger;

/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2006-11-30, 2006-12-11
 * @direction: 图像格式转换类(转换时不需要关心源图的格式)
 * @support  : GIF(no compressed encoding), JPEG, TIFF, PNG, PICT, Photoshop, BMP, Targa, ICO, CUR, Sunraster, XBM, XPM, PCX, DCX
 ******************************************************************************/

import java.awt.image.ImageProducer;
import java.io.File;

import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiException;
import com.sun.jimi.core.JimiWriter;
import com.sun.jimi.core.options.JPGOptions;
import com.sun.jimi.core.options.PNGOptions;

// ******************************************************************************

public class JimiImage {
private static Logger logger = Logger.getLogger(JimiImage.class);
	// public JimiImage() {}

	// ==========================================================================

	/**
	 * 转换图像格式为 GIF
	 * 
	 * @param String :
	 *            sSourceImage, 其它格式的源图像文件路径
	 * @param String :
	 *            sDestImage, 目标 GIF 图像文件存放路径
	 * @param int :
	 *            nTransparentIndex, 使某种颜色透明的调色板颜色值, 或 -1 不透明
	 * @param boolean:
	 *            isInterlaced, 是否采用隔行扫描处理方式
	 * @param int :
	 *            nFrameDelay, 每一桢的显示延迟时间, 以毫秒为单位
	 * @param boolean:
	 *            isUsingLocalPalette, 每一桢是否使用本地系统调色板颜色
	 * @param int :
	 *            nLoopNumbers, 动画循环显示次数, 或 -1 永远循环
	 * @return boolean
	 */
	// public boolean convertToGIF(String sSourceImage, String sDestImage, int
	// nTransparentIndex, boolean isInterlaced, int nFrameDelay, boolean
	// isUsingLocalPalette, int nLoopNumbers)
	// {
	// if (sSourceImage == null || sSourceImage.trim().equals(""))
	// {
	// logger.debug("\t@> JimiImage.convertToGIF() : 要转换的源图像文件路径不能为空！");
	// return false;
	// }
	//
	// if (sDestImage == null || sDestImage.trim().equals(""))
	// {
	// sDestImage = sSourceImage.substring(0, sSourceImage.lastIndexOf(".")) +
	// ".gif";
	// }
	// else if (!sDestImage.endsWith(".gif"))
	// {
	// sDestImage += ".gif";
	// }
	//
	// //----------------------------------------------------------------------
	//
	// //检查源图像文件
	// File tSourceImageFile = new File(sSourceImage);
	// if (!tSourceImageFile.exists())
	// {
	// logger.debug("\t@> JimiImage.convertToGIF() : 要转换的源图像文件路径不存在！");
	// return false;
	// }
	// else if (!tSourceImageFile.canRead())
	// {
	// logger.debug("\t@> JimiImage.convertToGIF() : 要转换的源图像文件路径不可读！");
	// return false;
	// }
	// else if (!tSourceImageFile.isFile())
	// {
	// logger.debug("\t@> JimiImage.convertToGIF() :
	// 要转换的源图像路径不是一个有效的文件名！");
	// return false;
	// }
	// tSourceImageFile = null;
	//
	// //----------------------------------------------------------------------
	//
	// try
	// {
	// GIFOptions tGIFOptions = new GIFOptions();
	// tGIFOptions.setTransparentIndex(nTransparentIndex);
	// tGIFOptions.setInterlaced(isInterlaced);
	// tGIFOptions.setFrameDelay(nFrameDelay);
	// tGIFOptions.setUseLocalPalettes(isUsingLocalPalette);
	// tGIFOptions.setNumberOfLoops(nLoopNumbers);
	// ImageProducer tImageProducer = Jimi.getImageProducer(sSourceImage);
	// JimiWriter tJimiWriter = Jimi.createJimiWriter(sDestImage);
	// tJimiWriter.setSource(tImageProducer);
	// tJimiWriter.setOptions(tGIFOptions);
	// tJimiWriter.putImage(sDestImage);
	// tImageProducer = null;
	// tJimiWriter = null;
	// tGIFOptions = null;
	// }
	// catch (JimiException je)
	// {
	// logger.debug("\t@> JimiImage.convertToGIF() : 转换图像格式发生异常！");
	// logger.debug(je);
	// return false;
	// }
	// catch (Exception ex)
	// {
	// ex.printStackTrace();
	// return false;
	// }
	//
	// return true;
	// } //function convertToGIF end
	// ==========================================================================
	/**
	 * 转换图像格式为 JPG
	 * 
	 * @param String :
	 *            sSourceImage, 其它格式的源图像文件路径
	 * @param String :
	 *            sDestImage, 目标 JPG 图像文件存放路径
	 * @param int :
	 *            nQuality, 品质, 0-100, 数值越高品质越好
	 * @return boolean
	 */
	public boolean convertToJPG(String sSourceImage, String sDestImage,
			int nQuality) {
		if (sSourceImage == null || sSourceImage.trim().equals("")) {
			logger.debug("\t@> JimiImage.convertToJPG() : 要转换的源图像文件路径不能为空！");
			return false;
		}

		if (sDestImage == null || sDestImage.trim().equals("")) {
			sDestImage = sSourceImage.substring(0, sSourceImage
					.lastIndexOf("."))
					+ ".jpg";
		} else if (!sDestImage.endsWith(".jpg")) {
			sDestImage += ".jpg";
		}

		// ----------------------------------------------------------------------

		// 检查源图像文件
		File tSourceImageFile = new File(sSourceImage);
		if (!tSourceImageFile.exists()) {
			logger.debug("\t@> JimiImage.convertToJPG() : 要转换的源图像文件路径不存在！");
			return false;
		} else if (!tSourceImageFile.canRead()) {
			logger.debug("\t@> JimiImage.convertToJPG() : 要转换的源图像文件路径不可读！");
			return false;
		} else if (!tSourceImageFile.isFile()) {
			logger.debug("\t@> JimiImage.convertToJPG() : 要转换的源图像路径不是一个有效的文件名！");
			return false;
		}
		tSourceImageFile = null;

		// ----------------------------------------------------------------------

		try {
			JPGOptions tJPGOptions = new JPGOptions();
			if (nQuality < 0 || nQuality > 100) {
				tJPGOptions.setQuality(75);
			} else {
				tJPGOptions.setQuality(nQuality);
			}
			ImageProducer tImageProducer = Jimi.getImageProducer(sSourceImage);
			JimiWriter tJimiWriter = Jimi.createJimiWriter(sDestImage);
			tJimiWriter.setSource(tImageProducer);
			tJimiWriter.setOptions(tJPGOptions);
			tJimiWriter.putImage(sDestImage);
			tImageProducer = null;
			tJimiWriter = null;
			tJPGOptions = null;
		} catch (JimiException je) {
			logger.debug("\t@> JimiImage.convertToJPG() : 转换图像格式发生异常！");
			je.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	} // function convertToJPG end

	// ==========================================================================

	/**
	 * 转换图像格式为 PNG
	 * 
	 * @param String :
	 *            sSourceImage, 其它格式的源图像文件路径
	 * @param String :
	 *            sDestImage, 目标 PNG 图像文件存放路径
	 * @param String :
	 *            sCompression, 压缩比, none, default, fast, max
	 * @return boolean
	 */
	public boolean convertToPNG(String sSourceImage, String sDestImage,
			String sCompression) {
		if (sSourceImage == null || sSourceImage.trim().equals("")) {
			logger.debug("\t@> JimiImage.convertToPNG() : 要转换的源图像文件路径不能为空！");
			return false;
		}

		if (sDestImage == null || sDestImage.trim().equals("")) {
			sDestImage = sSourceImage.substring(0, sSourceImage
					.lastIndexOf("."))
					+ ".png";
		} else if (!sDestImage.endsWith(".png")) {
			sDestImage += ".png";
		}

		// ----------------------------------------------------------------------

		// 检查源图像文件
		File tSourceImageFile = new File(sSourceImage);
		if (!tSourceImageFile.exists()) {
			logger.debug("\t@> JimiImage.convertToPNG() : 要转换的源图像文件路径不存在！");
			return false;
		} else if (!tSourceImageFile.canRead()) {
			logger.debug("\t@> JimiImage.convertToPNG() : 要转换的源图像文件路径不可读！");
			return false;
		} else if (!tSourceImageFile.isFile()) {
			logger.debug("\t@> JimiImage.convertToPNG() : 要转换的源图像路径不是一个有效的文件名！");
			return false;
		}
		tSourceImageFile = null;

		// ----------------------------------------------------------------------

		try {
			PNGOptions tPNGOptions = new PNGOptions();
			if (sCompression == null || sCompression.trim().equals("")) {
				tPNGOptions.setCompressionType(PNGOptions.COMPRESSION_DEFAULT);
			} else if (sCompression.equalsIgnoreCase("none")) {
				tPNGOptions.setCompressionType(PNGOptions.COMPRESSION_NONE);
			} else if (sCompression.equalsIgnoreCase("fast")) {
				tPNGOptions.setCompressionType(PNGOptions.COMPRESSION_FAST);
			} else if (sCompression.equalsIgnoreCase("max")) {
				tPNGOptions.setCompressionType(PNGOptions.COMPRESSION_MAX);
			} else {
				tPNGOptions.setCompressionType(PNGOptions.COMPRESSION_DEFAULT);
			}
			ImageProducer tImageProducer = Jimi.getImageProducer(sSourceImage);
			JimiWriter tJimiWriter = Jimi.createJimiWriter(sDestImage);
			tJimiWriter.setSource(tImageProducer);
			tJimiWriter.setOptions(tPNGOptions);
			tJimiWriter.putImage(sDestImage);
			tImageProducer = null;
			tJimiWriter = null;
			tPNGOptions = null;
		} catch (JimiException je) {
			logger.debug("\t@> JimiImage.convertToPNG() : 转换图像格式发生异常！");
			je.printStackTrace();
			return false;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

		return true;
	} // function convertToPNG end

	// ==========================================================================


} // class JimiImage end
