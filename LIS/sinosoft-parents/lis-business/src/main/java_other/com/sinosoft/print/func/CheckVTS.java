/**
 * Copyright (c) 2006 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.print.func;
import org.apache.log4j.Logger;

import java.io.*;

import com.sinosoft.utility.*;

/**
 * <p>
 * Title: VTS校验类
 * </p>
 * <p>
 * Description: 校验VTS描述文件的语法错误
 * </p>
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * <p>
 * Company: SinoSoft
 * </p>
 * @author zhuxf
 * @version 1.0
 */
public class CheckVTS
{
private static Logger logger = Logger.getLogger(CheckVTS.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public static CErrors mErrors = new CErrors();
	
	public CheckVTS()
	{}

	/**
	 * 校验VTS文件的语法
	 * @param cFile 校验文件
	 * @param cMaxRow 校验的行数
	 * @param cMaxCol 校验的列数
	 * @return
	 */
	public static boolean CheckVTSFile(String cFile, int cMaxRow, int cMaxCol)
	{
		return true;
//		com.f1j.ss.BookModelImpl bmTemplate = null;
//		// 使用文件流读取数据
//		InputStream tInputStream;
//		try
//		{
//			tInputStream = new FileInputStream(cFile);
//			bmTemplate = new com.f1j.ss.BookModelImpl();
//			// 将流数据读入到BookModelImpl对象中
//			bmTemplate.read(tInputStream, new com.f1j.ss.ReadParams());
//			// 关闭不使用的对象
//			tInputStream.close();
//
//			int nMaxRow = cMaxRow; // 设置校验的行数
//			int nMaxCol = cMaxCol; // 设置校验的列数
//
//			// 存放每个单元格的数据
//			String tStrText;
//			// 存放每个单元格的类型
//			String tStrType;
//			// 是否存在列结束符，默认存在
//			boolean tColFlag = true;
//
//			// 增加列宽的校验,对没有用到的列统一设置的列宽杀.
//			// yang add
//			if (bmTemplate.getColWidth(5 * 26) < 2000)
//			{
//				CError tError = new CError();
//				tError.errorMessage = "文件" + cFile + "对没有用到的列统一设置了列宽,需要修改！";
//				mErrors.addOneError(tError);
//				logger.debug("****** 文件" + cFile + "对没有用到的列统一设置了列宽,需要修改****** ");
//			}
//
//			int nRow, nCol;
//			// 循环模板中的行记录信息
//			for (nRow = 0; nRow < nMaxRow; nRow++)
//			{
//				// 默认设置每一行的列结束符描述都不存在
//				tColFlag = false;
//
//				// 循环模板中的列记录信息
//				for (nCol = 0; nCol < nMaxCol; nCol++)
//				{
//					// 获取单元格中的信息
//					tStrText = bmTemplate.getText(nRow, nCol).trim();
//
//					if (tStrText.equals("") || tStrText.length() == 0)
//					{
//						continue;
//					}
//					else
//					{
//						if (nCol == 0)
//						{
//							// 无论在那一行的首列发现了行结束符号，都认为该vts文件的描述是正确的。
//							if (tStrText.equals("$/"))
//							{
//								return true;
//							}
//						}
//						if (tStrText.equals("$/"))
//						{
//							// 设置列结束符校验位为true
//							tColFlag = true;
//							// 如果校验到了列结束符，则无需校验其后的列信息。
//							break;
//						}
//						else
//						{
//							// 设置列结束符校验位为false
//							tColFlag = false;
//
//							if (tStrText.length() > 1)
//							{
//								// 获取单元格的类型
//								tStrType = tStrText.substring(0, 2);
//								// 可扩充这里的描述校验规则
//								if (tStrType.equals("$="))
//								{}
//							}
//						}
//					}
//				}
//				if (!tColFlag)
//				{
//					CError tError = new CError();
//					tError.errorMessage = "文件" + cFile + "的第" + (nRow + 1) + "行没有发现列结束符！";
//					mErrors.addOneError(tError);
//					logger.debug("****** 文件" + cFile + "的第" + (nRow + 1) + "行没有发现列结束符 ******");
//					return false;
//				}
//			}
//			CError tError = new CError();
//			tError.errorMessage = "文件" + cFile + "没有发现行结束符 ！";
//			mErrors.addOneError(tError);
//			logger.debug("****** 文件" + cFile + "没有发现行结束符 ******");
//			return false;
//		}
//		catch (Exception e)
//		{
//			// 自动生成 catch 块
//			e.printStackTrace();
//			return false;
//		}
//		finally
//		{
//			// 关闭不再使用的对象
//			bmTemplate.destroy();
//		}
	}

	/**
	 * 循环目录下的文件信息
	 * @param cPath 校验路径
	 * @param cMaxRow 校验的行数
	 * @param cMaxCol 校验的列数
	 * @throws Exception
	 */
	public void FileList(String cPath, int cMaxRow, int cMaxCol) throws Exception
	{
		File MyDir = new File(cPath);
		// 获得目录下的文件列表
		String[] FileNames = MyDir.list();
		// 循环处理文件
		for (int i = 0; i < FileNames.length; i++)
		{
			File m = new File(cPath + FileNames[i]);
			// 如果对象是目录，则递归循环其下的文件信息
			if (m.isDirectory())
			{
				FileList(cPath + FileNames[i] + "/", cMaxRow, cMaxCol);
			}
			else
			{
				// 如果是vts文件才处理
				if (FileNames[i].indexOf(".vts") != -1)
				{
					if (!CheckVTSFile(cPath + FileNames[i], cMaxRow, cMaxCol))
					{
						// logger.debug("文件" + cPath + FileNames[i] +
						// "描述错误，请查询！");
						logger.debug("");
					}
				}
			}
		}
	}

	/**
	 * 测试函数
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		CheckVTS fc = new CheckVTS();
		// String oPath = "d:/lis/ui/f1print/ncltemplate/";
		String oPath = args[0];

		/**
		 * 校验vts描述文件<br>
		 * 传入参数分别为：校验路径、校验行数、校验列数
		 */
		fc.FileList(oPath, 1000, 200);
	}
}
