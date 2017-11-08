package com.sinosoft.print.func;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

import com.f1j.ss.GRObject;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * ClassName: ReadFile
 * </p>
 * <p>
 * Description: 模板文件读取类，提取xml标签信息到LPrtXmlStyle表中，打印测试功能调用
 * </p>
 * @author guoxiange
 * @version 1.0
 * @modify 2011-2-22
 */
public class ReadFile
{
private static Logger logger = Logger.getLogger(ReadFile.class);

	private LPrtXmlStyleSet mLPrtXmlStyleSet = null;

//	private LPrtXmlStyleSchema mLPrtXmlStyleSchema = null;

	private LPrtCodeSet mLPrtCodeSet = null;

//	private LPrtCodeSchema mLPrtCodeSchema = null;

	/** 错误处理类 */
	private CErrors mErrors = new CErrors();

	/** 最大行数 */
	private final int mMaxRow = 100;

	/** 最大列数 */
	private final int mMaxCol = 100;

	/** 处理结果 */
	private VData mResult = new VData();

	/**
	 * 读取文件公共方法
	 * @param cFilePath 文件物理路径
	 * @param cTempleteID 文件的ID
	 * @param cTempleteType 模板类型
	 * @return
	 */
	public LPrtXmlStyleSet readData(String cTempleteID, String cFilePath, String cTempleteType)
	{
		String tTempleteType = cTempleteType;
		mLPrtXmlStyleSet = new LPrtXmlStyleSet();
		mLPrtCodeSet = new LPrtCodeSet();
		// 如果模板类型为 0 即xls
		if (tTempleteType.equals("0"))
		{
			if (!readVTS(cFilePath, cTempleteID))
			{
				CError tError = new CError();
				tError.moduleName = "ReadFile";
				tError.functionName = "readVTS";
				tError.errorMessage = "VTS文件读取失败！";
				mErrors.addOneError(tError);
			}
		}
		// 如果模板类型为 1 即pdf
		else if (tTempleteType.equals("1"))
		{
			if (!readPDF(cFilePath, cTempleteID))
			{
				CError tError = new CError();
				tError.moduleName = "ReadFile";
				tError.functionName = "readPDF";
				tError.errorMessage = "PDF文件读取失败！";
				mErrors.addOneError(tError);
			}
		}
		// 如果模板类型为 3 即xsl
		else if (tTempleteType.equals("3"))
		{

		}
		return mLPrtXmlStyleSet;
	}

	/**
	 * 读取PDF文件方法
	 * @param cFilePath 文件物理路径
	 * @param cTempleteID 文件的ID
	 * @return
	 */
	private boolean readPDF(String cFilePath, String cTempleteID)
	{
		try
		{
			String tFilePath = cFilePath;
			String tTempleteID = cTempleteID;
			ByteArrayOutputStream ba = new ByteArrayOutputStream();
			PdfReader reader = new PdfReader(tFilePath);
			PdfStamper stamp = new PdfStamper(reader, ba);
			PdfContentByte under = stamp.getUnderContent(1);
			BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			Font FontChinese = new Font(bf, 14, Font.NORMAL);
			AcroFields form = stamp.getAcroFields();

			HashMap acroFieldMap = form.getFields();
			Iterator i = acroFieldMap.keySet().iterator();

			LPrtXmlStyleSchema tLPrtXmlStyleSchema;
			LPrtCodeSchema tLPrtCodeSchema; 
			// 可以得到全部的控件名，这个东西需要考虑一下实现。。。
			while (i.hasNext())
			{
				// 获得块名
				String tStrText = (String) i.next();
				// 通过块名获得块
				AcroFields.Item item = form.getFieldItem(tStrText);
				logger.debug("****tStrText***" + tStrText + "*******");
				// 通过块名获得里面的值
				// String value = form.getField(tStrText);
				// 读取块名并存到数据库中，如果块名有$符号，则进行如下处理
				if (tStrText.indexOf("$") >= 0)
				{
					// 如果块名中包含"$B"，则它是条形码标签，进行如下特殊处理
					if (tStrText.indexOf("$B") >= 0)
					{
						// 对条形码标签截取块名从$开始两字符后面的内容,在前面加上"#"，用以区分
						String[] tBarcode = tStrText.split("#");
//						tStrText = "#" + tBarcode[0];
						tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
						tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
						tLPrtXmlStyleSchema.setDescription("#" + tBarcode[0]);
						tLPrtXmlStyleSchema.setXmlLevel(4);
						mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);

						
						// 把块名的"$B"截掉，然后以"#"截成字符数组
						// 例"$Bguoxiang#1#100,100" 得到三个字符串值
						// "guoxiang","1"和100,100"
						// 第一个字符串值"guoxiang"是条形码名称，第二个字符串值"1"是条形码类型，第三个字符串值的"100,100"是条形码的坐标
						if (tStrText.indexOf("#") >= 0)
						{
							String[] tName = tStrText.substring(2).split("#");
							String[] tNameCode = tName[0].split(",");
							tLPrtCodeSchema = new LPrtCodeSchema();
							tLPrtCodeSchema.setCodeType("barcode");
							tLPrtCodeSchema.setCode(tName[0]);
							if (tNameCode[1].equals("1"))
							{
								tLPrtCodeSchema.setContent1("BarCode");
							}
							else
							{
								tLPrtCodeSchema.setContent1("DBarCode");
							}
							tLPrtCodeSchema.setContent2(tName[1]);
							tLPrtCodeSchema.setTempleteID(tTempleteID);
							mLPrtCodeSet.add(tLPrtCodeSchema);
						}
						else
						{
							CError tError = new CError();
							tError.moduleName = "ReadFile";
							tError.functionName = "readBarCode";
							tError.errorMessage = "条形码标签定义错误！";
							mErrors.addOneError(tError);
						}
					}
					else
					{
						// 第一次截取，截取块名从$开始两字符后面的内容
						// 例："$*/AppntInfo/Appnt/Name" 截取后为
						// "/AppntInfo/Appnt/Name" ；"$=/Company/ManageName$"
						// 截取后为 "/Company/ManageName$"
						String tStrText1 = tStrText.substring(tStrText.indexOf("$") + 2);
						// 如果第一次截取后的块名还有$符号，则进行如下处理
						// 例："$=/Company/ManageName$" 截取后为
						// "/Company/ManageName$"
						if (tStrText1.indexOf("$") >= 0)
						{
							// 对原块名截取单元格第一个$到最后一个$的内容
							// 例："$=/Company/ManageName$" 进行如下处理后为
							// "/Company/ManageName"
							tStrText = tStrText.substring(tStrText.indexOf("$") + 2, tStrText.lastIndexOf("$"));
							tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
							tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
							tLPrtXmlStyleSchema.setDescription(tStrText);
							// 把tStrText以"/"进行拆分
							String[] tTextEle = tStrText.substring(1).split("/");
							if (tTextEle.length == 3)
							{
								tLPrtXmlStyleSchema.setXmlLevel(3);
							}
							else if (tTextEle.length == 2)
							{
								tLPrtXmlStyleSchema.setXmlLevel(2);
							}
							else
							{
								tLPrtXmlStyleSchema.setXmlLevel(1);
							}
							mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);

						}
						else
						{
							// 如果第一次截取后的块名没有有$符号，则对截取后的块名进行如下处理
							// 例："$*/AppntInfo/Appnt/Name" 第一次截取后为
							// "/AppntInfo/Appnt/Name"
							tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
							tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
							tLPrtXmlStyleSchema.setDescription(tStrText1);
							// 把tStrText以"/"进行拆分
							String[] tTextEle = tStrText.substring(3).split("/");
							if (tTextEle.length == 3)
							{
								tLPrtXmlStyleSchema.setXmlLevel(3);
							}
							else if (tTextEle.length == 2)
							{
								tLPrtXmlStyleSchema.setXmlLevel(2);
							}
							else
							{
								tLPrtXmlStyleSchema.setXmlLevel(1);
							}
							mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);
						}
					}
				}
			}

			stamp.setFormFlattening(true);
			stamp.close();
		}
		catch (Exception e)
		{
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 读取vts文件方法
	 * @param cFilePath 文件物理路径
	 * @param cTempleteID 文件的ID
	 * @return
	 */
	private boolean readVTS(String cFilePath, String cTempleteID)
	{
		String tTempleteID = cTempleteID; // 将TempleteID拷贝到本类中

		String tFilePath = cFilePath; // 将cFile拷贝到本类中

		// 存放每个单元格的数据内容
		String tStrText;

		int nRow, nCol;

		int tObjNum = 0;// 对象个数

		com.f1j.ss.BookModelImpl bmTemplate = null;
		// 使用文件流读取数据
		InputStream tInputStream;
		try
		{
			tInputStream = new FileInputStream(tFilePath);
			bmTemplate = new com.f1j.ss.BookModelImpl();
			// 将流数据读入到BookModelImpl对象中
			bmTemplate.read(tInputStream, new com.f1j.ss.ReadParams());
			// 关闭不使用的对象
			tInputStream.close();

			// 获得文件里面的第一个对象
			GRObject tFirstObj = bmTemplate.getFirstObject();
			// 如果第一个对象存在，则对它进行处理。然后判断是否存在下一个对象，如果存在就进行处理，以此类推。

			LPrtXmlStyleSchema tLPrtXmlStyleSchema;
			LPrtCodeSchema tLPrtCodeSchema; 
			if (tFirstObj != null)
			{
				tObjNum = 0;
				do
				{
					tObjNum++;
					if (tObjNum > 1)
					{
						tFirstObj = tFirstObj.getNextObject();
					}
					tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
					String tObjName = tFirstObj.getName();// 获得对象名
//					tObjName = "#" + tObjName;
					tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
					// 对于条形码对象标签，在前面加上"#"，用以区分
					tLPrtXmlStyleSchema.setDescription("#" + tObjName);
					tLPrtXmlStyleSchema.setXmlLevel(4);
					mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);
					
					// 将对象名以","截取成字符数组
					// 以"CertifyCode,1"为例，截取后得到两个字符串"CertifyCode"和"1"，第一个是条形码名，第二个是条形码类型
					String[] tName = tObjName.split(",");
					tLPrtCodeSchema = new LPrtCodeSchema();
					tLPrtCodeSchema.setTempleteID(tTempleteID);
					tLPrtCodeSchema.setCodeType("barcode");
					tLPrtCodeSchema.setCode(tObjName);
					if (tName.length > 1)
					{
						if (tName[1].equals("1"))
						{
							tLPrtCodeSchema.setContent1("BarCode");
						}
						else
						{
							tLPrtCodeSchema.setContent1("DBarCode");
						}
					}
					else
					{
						tLPrtCodeSchema.setContent1("BarCode");
					}
					double tX1 = Arith.round(tFirstObj.getPos().getX1() * 30, 2);
					double tX2 = Arith.round(tFirstObj.getPos().getX2() * 30, 2);
					double tY1 = Arith.round(tFirstObj.getPos().getY1() * 30, 2);
					double tY2 = Arith.round(tFirstObj.getPos().getY2() * 30, 2);
					// 获取对象坐标
					String tPosition = "0," + tX1 + "," + tY1 + "," + Arith.round((tX2 - tX1), 2) + ","
							+ Arith.round((tY2 - tY1), 2);
					tLPrtCodeSchema.setContent2(tPosition);
					mLPrtCodeSet.add(tLPrtCodeSchema);
				}
				while (tFirstObj.getNextObject() != null);
			}
			logger.debug("*****" + tObjNum + "**tObjNum");

			// 循环模板中的行记录信息
			for (nRow = 0; nRow < mMaxRow; nRow++)
			{
				// 循环模板中的列记录信息
				for (nCol = 0; nCol < mMaxCol; nCol++)
				{
					// 获取单元格中的信息
					tStrText = bmTemplate.getText(nRow, nCol).trim();
					if (tStrText.equals("") || tStrText.length() == 0)
					{
						continue;
					}
					else
					{
						if (nCol == 0)
						{
							// 无论在那一行的首列发现了行结束符号，都认为是正确的。
							if (tStrText.equals("$/"))
							{
								return true;
							}
						}
						if (tStrText.equals("$/"))
						{
							// 如果到了列结束符，则停止。
							break;
						}
						else
						{
							if (tStrText.length() > 1)
							{
								// 读取单元格内容并存到数据库中，如果单元格内容有$符号，则进行如下处理
								// 如：$*/InsuredInfo/Insured/Name
								// ；尊敬的$=/LCCont.AppntName$你好$=/AppntSex$
								// ；$FWcrydcwj.vts - 问卷1
								if (tStrText.indexOf("$") >= 0)
								{
									// 如果单元格内容有$F符号，它是文件合并的标签，则需要进行如下处理
									// 例"$FWcrydcwj.vts - 问卷1"
									if (tStrText.indexOf("$F") >= 0)
									{
										// 先截取前两字符后在前面加上"@"符号，用以区分
										// 例"$FWcrydcwj.vts - 问卷1"
										// 处理后为"@Wcrydcwj.vts - 问卷1"
										tStrText = "@" + tStrText.substring(2);
										tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
										tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
										tLPrtXmlStyleSchema.setDescription(tStrText);
										tLPrtXmlStyleSchema.setXmlLevel(5);
										mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);
									}
									// 增加条形码读取设置，新增类型$T，2011-8-22
									else if (tStrText.indexOf("$T") >= 0)
									{
										// 对于条形码对象标签，在前面加上"#"，用以区分
										tStrText = "#" + tStrText.substring(2);
										tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
										tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
										tLPrtXmlStyleSchema.setDescription(tStrText);
										tLPrtXmlStyleSchema.setXmlLevel(4);
										mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);

										// 为了提高效率，直接在读取标签信息的时候，即准备好条形码标签数据
										if (mLPrtCodeSet == null)
										{
											mLPrtCodeSet = new LPrtCodeSet();
										}
										tLPrtCodeSchema = new LPrtCodeSchema();
										String[] tName = tStrText.substring(1).split(",");
										tLPrtCodeSchema.setTempleteID(tTempleteID);
										tLPrtCodeSchema.setCodeType("barcode");
										tLPrtCodeSchema.setCode(tStrText.substring(1));
										if (tName.length > 1)
										{
											// 条形码类型判断：1-一维、2-二维
											if ("1".equals(tName[1]))
											{
												tLPrtCodeSchema.setContent1("BarCode");
												tLPrtCodeSchema.setContent2("BarCode");
											}
											else
											{
												tLPrtCodeSchema.setContent1("DBarCode");
												tLPrtCodeSchema.setContent2("DBarCode");
											}
										}
										else
										{
											tLPrtCodeSchema.setContent1("BarCode");
											tLPrtCodeSchema.setContent2("BarCode");
										}
										mLPrtCodeSet.add(tLPrtCodeSchema);
									}
									// 增加$<和$>标签的处理，2011-8-23
									else if (tStrText.indexOf("$<") >= 0)
									{
										tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
										tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
										// 处理特殊信息
										tLPrtXmlStyleSchema.setDescription("$" + tStrText.substring(3));
										tLPrtXmlStyleSchema.setXmlLevel(6); // level为6
										mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);
									}
									// 增加表头信息读取设置，新增类型$t，2011-8-22
									else if (tStrText.indexOf("$t") >= 0)
									{
										// 表头信息不是变量信息，跳过
										continue;
									}
									else
									{
										// 第一次截取，截取单元格从$开始两字符后面的内容
										// 例 /InsuredInfo/Insured/Name
										// ；/LCCont.AppntName$你好$=/AppntSex$
										String tStrText1 = tStrText.substring(tStrText.indexOf("$") + 2);
										// 如果第一次截取后的单元格内容还有$符号，则进行如下处理
										// 例：/LCCont.AppntName$你好$=/AppntSex$
										if (tStrText1.indexOf("$") >= 0)
										{
											int k = 0; // $符号的个数
											// 截取原单元格内容，从第一个$开始到最后一个$的内容（包括$符号）
											// 例：$=/LCCont.AppntName$你好$=/AppntSex$
											tStrText = tStrText.substring(tStrText.indexOf("$"), tStrText.lastIndexOf("$") + 1);
											// 得到每个单元格值包含$的个数
											// 以"$=/LCCont.AppntName$你好$=/AppntSex$"为例，k为4
											for (int j = 0; j < tStrText.length(); j++)
											{
												if (tStrText.charAt(j) == '$')
												{
													k++;
												}
											}
											// 获得每个单元格每两个$中间的值（$的个数为偶数且大于等于2）
											// 以"$=/LCCont.AppntName$你好$=/AppntSex$"为例
											// 得到两个值 "=/LCCont.AppntName"和
											// "=/AppntSex"
											for (int i = 2; i <= k; i = i + 2)
											{
												tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
												tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
												String tStrText2 = StrTool.decodeStr(tStrText, "$", i);
												if (!tStrText2.equals(""))
												{
													// 对得到的值截取第一个字符后 ，再进行处理
													// 如对“=/LCCont.AppntName”截取后为
													// “/LCCont.AppntName”
													tStrText2 = tStrText2.substring(1);
													// 把tStrText2以"/"进行拆分
													String[] tTextEle = tStrText2.substring(1).split("/");
													if (tTextEle.length == 3)
													{
														tLPrtXmlStyleSchema.setXmlLevel(3);
													}
													else if (tTextEle.length == 2)
													{
														tLPrtXmlStyleSchema.setXmlLevel(2);
													}
													else
													{
														tLPrtXmlStyleSchema.setXmlLevel(1);
													}
													tLPrtXmlStyleSchema.setDescription(tStrText2);
													mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);
												}
											}
										}
										else
										{
											// 需要考虑$B和$K的情况
											if (!"".equals(tStrText1))
											{
												// 如果第一次截取后的单元格内容没有$符号,它是普通标签,进行如下处理
												// /InsuredInfo/Insured/Name
												tLPrtXmlStyleSchema = new LPrtXmlStyleSchema();
												tLPrtXmlStyleSchema.setTempleteID(tTempleteID);
												tLPrtXmlStyleSchema.setDescription(tStrText1);
												// 把tStrText1以"/"进行拆分
												String[] tTextEle = tStrText1.substring(1).split("/");
												if (tTextEle.length == 3)
												{
													tLPrtXmlStyleSchema.setXmlLevel(3);
												}
												else if (tTextEle.length == 2)
												{
													tLPrtXmlStyleSchema.setXmlLevel(2);
												}
												else
												{
													tLPrtXmlStyleSchema.setXmlLevel(1);
												}
												mLPrtXmlStyleSet.add(tLPrtXmlStyleSchema);
											}
										}
									}
								}
							}
						}
					}
				}
			}
			return true;
		}
		catch (Exception e)
		{
			// 自动生成 catch 块
			e.printStackTrace();
			return false;
		}
		finally
		{
			// 关闭不再使用的对象
			if (bmTemplate != null)
			{
				bmTemplate.destroy();
			}
		}
	}
	
	/**
	 * 返回条形码描述信息
	 * @return
	 */
	public LPrtCodeSet getLPrtCodeSet()
	{
		return mLPrtCodeSet;
	}

	/**
	 * 读取BarCode方法
	 * @param cFilePath 文件物理路径
	 * @param cTempleteID 文件的ID
	 * @param cTempleteType 文件的ID
	 * @return
	 */
	public LPrtCodeSet readBarCode(String cTempleteID, String cFilePath, String cTempleteType)
	{
		String tTempleteID = cTempleteID; // 将TempleteID拷贝到本类中

		String tFilePath = cFilePath; // 将cFile拷贝到本类中

		String tTempleteType = cTempleteType; // 将cFile拷贝到本类中

		mLPrtCodeSet = new LPrtCodeSet();

		// 对于模板类型是xls，读取条形码对象标签
		if (tTempleteType.equals("0"))
		{
			int tBarNum = 0;// 对象个数

			com.f1j.ss.BookModelImpl bmTemplate = null;
			// 使用文件流读取数据
			InputStream tInputStream;
			try
			{
				tInputStream = new FileInputStream(tFilePath);
				bmTemplate = new com.f1j.ss.BookModelImpl();
				// 将流数据读入到BookModelImpl对象中
				bmTemplate.read(tInputStream, new com.f1j.ss.ReadParams());
				// 关闭不使用的对象
				tInputStream.close();

				// 获得文件里面的第一个对象
				GRObject tFirstObj = bmTemplate.getFirstObject();
				// 如果第一个对象存在，则对它进行处理。然后判断是否存在下一个对象，如果存在就进行处理，以此类推。
				LPrtCodeSchema tLPrtCodeSchema;
				if (tFirstObj != null)
				{
					tBarNum = 0;
					do
					{
						tBarNum++;
						if (tBarNum > 1)
						{
							tFirstObj = tFirstObj.getNextObject();
						}
						tLPrtCodeSchema = new LPrtCodeSchema();
						String tObjName = tFirstObj.getName();// 获得对象名
						// 将对象名以","截取成字符数组
						// 以"CertifyCode,1"为例，截取后得到两个字符串"CertifyCode"和"1"，第一个是条形码名，第二个是条形码类型
						String[] tName = tObjName.split(",");
						tLPrtCodeSchema.setTempleteID(tTempleteID);
						tLPrtCodeSchema.setCodeType("barcode");
						tLPrtCodeSchema.setCode(tObjName);
						if (tName.length > 1)
						{
							if (tName[1].equals("1"))
							{
								tLPrtCodeSchema.setContent1("BarCode");
							}
							else
							{
								tLPrtCodeSchema.setContent1("DBarCode");
							}
						}
						else
						{
							tLPrtCodeSchema.setContent1("BarCode");
						}
						double tX1 = Arith.round(tFirstObj.getPos().getX1() * 30, 2);
						double tX2 = Arith.round(tFirstObj.getPos().getX2() * 30, 2);
						double tY1 = Arith.round(tFirstObj.getPos().getY1() * 30, 2);
						double tY2 = Arith.round(tFirstObj.getPos().getY2() * 30, 2);
						// 获取对象坐标
						String tPosition = "0," + tX1 + "," + tY1 + "," + Arith.round((tX2 - tX1), 2) + ","
								+ Arith.round((tY2 - tY1), 2);
						tLPrtCodeSchema.setContent2(tPosition);
						mLPrtCodeSet.add(tLPrtCodeSchema);
					}
					while (tFirstObj.getNextObject() != null);
				}
				// logger.debug("*****" + tBarNum + "**tBarNum");
			}
			catch (Exception e)
			{
				CError tError = new CError();
				tError.moduleName = "ReadFile";
				tError.functionName = "readBarCode";
				tError.errorMessage = "条形码对象标签读取错误！";
				mErrors.addOneError(tError);
			}
			finally
			{
				// 关闭不再使用的对象
				if (bmTemplate != null)
				{
					bmTemplate.destroy();
				}
			}
		}
		// 对于模板类型是pdf，读取条形码标签
		else if (tTempleteType.equals("1"))
		{
			try
			{
				ByteArrayOutputStream ba = new ByteArrayOutputStream();
				PdfReader reader = new PdfReader(tFilePath);
				PdfStamper stamp = new PdfStamper(reader, ba);
				PdfContentByte under = stamp.getUnderContent(1);
				BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

				Font FontChinese = new Font(bf, 14, Font.NORMAL);
				AcroFields form = stamp.getAcroFields();

				HashMap acroFieldMap = form.getFields();
				Iterator i = acroFieldMap.keySet().iterator();

				LPrtCodeSchema tLPrtCodeSchema;
				// 可以得到全部的控件名，这个东西需要考虑一下实现。。。
				while (i.hasNext())
				{
					// 获得块名
					String tStrText = (String) i.next();
					// tStrText = "$Bguoxiang#1#100,100";
					// 如果块名中包含"$B"，则它是条形码标签，进行如下特殊处理
					if (tStrText.indexOf("$B") >= 0)
					{
						// 把块名的"$B"截掉，然后以"#"截成字符数组
						// 例"$Bguoxiang#1#100,100" 得到三个字符串值
						// "guoxiang","1"和100,100"
						// 第一个字符串值"guoxiang"是条形码名称，第二个字符串值"1"是条形码类型，第三个字符串值的"100,100"是条形码的坐标
						if (tStrText.indexOf("#") >= 0)
						{
							String[] tName = tStrText.substring(2).split("#");
							String[] tNameCode = tName[0].split(",");
							tLPrtCodeSchema = new LPrtCodeSchema();
							tLPrtCodeSchema.setCodeType("barcode");
							tLPrtCodeSchema.setCode(tName[0]);
							if (tNameCode[1].equals("1"))
							{
								tLPrtCodeSchema.setContent1("BarCode");
							}
							else
							{
								tLPrtCodeSchema.setContent1("DBarCode");
							}
							tLPrtCodeSchema.setContent2(tName[1]);
							tLPrtCodeSchema.setTempleteID(tTempleteID);
							mLPrtCodeSet.add(tLPrtCodeSchema);
						}
						else
						{
							CError tError = new CError();
							tError.moduleName = "ReadFile";
							tError.functionName = "readBarCode";
							tError.errorMessage = "条形码标签定义错误！";
							mErrors.addOneError(tError);
						}
					}
				}
				stamp.setFormFlattening(true);
				stamp.close();
			}
			catch (Exception e)
			{
				CError tError = new CError();
				tError.moduleName = "ReadFile";
				tError.functionName = "readBarCode";
				tError.errorMessage = "条形码标签读取错误！";
				mErrors.addOneError(tError);
			}
		}
		return mLPrtCodeSet;
	}

	/**
	 * 获取处理结果
	 */
	public VData getResult()
	{
		return mResult;
	}

	/**
	 * 获取错误信息
	 */
	public CErrors getErrors()
	{
		return mErrors;
	}

	public static void main(String[] args)
	{
		ReadFile a = new ReadFile();
		a.readData("000001", "C:/2s.xls", "0");
	}
}
