package com.sinosoft.print.func;
import org.apache.log4j.Logger;

import java.util.*;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.service.*;
import com.sinosoft.utility.*;

public class CreateXMLFile implements BusinessService
{
private static Logger logger = Logger.getLogger(CreateXMLFile.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private MMap map = new MMap();

	/** 业务处理相关变量 */

	private LPrtTempleteDB mLPrtTempleteDB = new LPrtTempleteDB();

	private TransferData mTransferData = new TransferData();

	private ArrayList mArray = new ArrayList();

	private XmlExportNew mXmlExport = new XmlExportNew();

	private String mTempleteID = "";

	public CreateXMLFile()
	{}

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperater)
	{
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CreateXMLFile";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败CreateXMLFile-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		else
		{
			logger.debug("Start CreateXMLFile Submit...");

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, null))
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "CreateXMLFile";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}

			logger.debug("---CreateXMLFile---");

		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		String tOutputType = mLPrtTempleteDB.getOutputType();
		String tTempleteName = mLPrtTempleteDB.getTempleteName();
		mArray = (ArrayList) mTransferData.getValueByName("ArrayList");
		Collections.sort(mArray);
		mXmlExport.createDocument(tTempleteName, "", "", tOutputType);
		mXmlExport.setUserLanguage(mLPrtTempleteDB.getLanguage());
		String tParentCode = "";// 父节点
		String tParent = "";// 同名标签的父节点
		int tCodeLen = 0;// 标签层级数
		String tName = "";// 标签名
		String tValue = "";// 标签值
		String tIndex = "";// 标签索引
		TextTag tTextTag;
		ArrayList tArr = new ArrayList();// 用来存带有"/"的多层标签
		ArrayList tOthe = new ArrayList();// 用来存放其他的标签
		ArrayList tArry = new ArrayList();// 用来存同名标签
		for (int i = 0; i < mArray.size(); i++)
		{
			// 得到集合每个元素，由三要素构成 ，“标签名”，“索引”，“标签值”
			String tElement = (String) mArray.get(i);
			tElement = XMLPrintTagName.changeRowColToRC(tElement);
			if (!tElement.equals(""))
			{
				// 将每个元素以","拆分成标签名和标签值
				String[] tEleStr = tElement.split("&");
				tIndex = tEleStr[1];
				if (tEleStr.length == 3)
				{
					tValue = tEleStr[2];
				}
				else
				{
					tValue = "";
				}
				// 对索引不为0，既有同名标签的处理
				if (!tIndex.equals("0"))
				{
					tName = tEleStr[0].substring(1);
					// 获得以"/"截取的字符串数值
					String[] tCode = tName.split("/");
					// 父节点为空
					if (tParent.equals(""))
					{
						// 不做xml的输出
						tParent = tCode[0];
						tArry = new ArrayList();
						tArry.add(tElement);
					}
					// 父节点不同
					else if (!tParent.equals(tCode[0]))
					{
						// 父亲换掉了
						tParent = tCode[0];
						// 特殊的处理，将缓存的父亲和孩子输出到xml里面
						if (tArry.size() > 0)
						{
							if (!createMulTags(tArry, mXmlExport))
							{
								return false;
							}
						}
						// 初始化集合，清空缓存数据
						tArry = new ArrayList();
						// 添加新的父亲和孩子信息
						tArry.add(tElement);
					}
					// 父节点相同
					else
					{
						// 添加新的父亲和孩子信息
						tArry.add(tElement);
					}
				}
				else
				{
					tName = tEleStr[0];
					// 对带有"/"的标签的处理
					if (tName.indexOf("/") >= 0)
					{
						tName = tName.substring(1);
						// 获得以"/"截取的字符串数值
						String[] tCode = tName.split("/");
						tCodeLen = tCode.length;
						// tCodeLen = 1不会有孩子
						if (tCodeLen == 1)
						{
							// 一层标签的生成
							tParentCode = tCode[0];
							tTextTag = new TextTag();
							tTextTag.add(tParentCode, tValue);
							mXmlExport.addTextTag(tTextTag);
							// 一层标签生成后，判断集合里是否有缓存的多层标签，如果有就进行处理
							if (tArr.size() > 0)
							{
								// 多层标签的生成
								if (!createMulTag(tArr, mXmlExport))
								{
									return false;
								}
							}
							// 初始化集合，清空缓存数据
							tArr = new ArrayList();
						}
						else
						{
							if (tParentCode.equals(""))
							{
								// 不做xml的输出
								tParentCode = tCode[0];
								tArr = new ArrayList();
								tArr.add(tElement);
							}
							else if (!tParentCode.equals(tCode[0]))
							{
								// 父亲换掉了
								tParentCode = tCode[0];
								// 特殊的处理，将缓存的父亲和孩子输出到xml里面
								if (tArr.size() > 0)
								{
									if (!createMulTag(tArr, mXmlExport))
									{
										return false;
									}
								}
								// 添加新的父亲和孩子信息
								tArr = new ArrayList();
								tArr.add(tElement);
							}
							else
							{
								// 增加孩子信息
								tArr.add(tElement);
							}
						}
					}
					else
					{
						// 把不含"/"的其他标签存到集合中，有可能是条形码对象标签、文件合并的标签等
						tOthe.add(tElement);
					}
				}
			}
		}
		// 处理同名标签
		if (tArry.size() > 0)
		{
			if (!createMulTags(tArry, mXmlExport))
			{
				return false;
			}
			tArry = new ArrayList();
		}
		// 如果最后集合还有缓存数据，则进行处理
		if (tArr.size() > 0)
		{
			if (!createMulTag(tArr, mXmlExport))
			{
				return false;
			}
			tArr = new ArrayList();
		}
		// 其他标签的生成
		if (tOthe.size() > 0)
		{
			if (!createMulTag(tOthe, mXmlExport))
			{
				return false;
			}
			tOthe = new ArrayList();
		}
//		mXmlExport.outputDocumentToFile("c:/", "test");
		mResult.add(mXmlExport);
		return true;
	}

	/**
	 * 生成XML多层标签和一些特殊标签（合并文件，条形码等）
	 * @return
	 */
	private boolean createMulTag(ArrayList cArry, XmlExportNew cXmlExportNew)
	{
		ArrayList tArry = cArry;
		XmlExportNew mXmlExport = cXmlExportNew;
		// 存放条形码对象标签
		ArrayList tBarCode = new ArrayList();
		// 存放文件合并的标签
		ArrayList tFileCom = new ArrayList();
		// 存放展现控制的标签
		ArrayList tDisplay = new ArrayList();
		// 得到集合第一个元素
		String tPCode = (String) tArry.get(0);
		// 先把第一个元素以","拆分成标签名和标签值
		String[] tElePra = tPCode.split("&");
		// 把第一个元素截掉第一个字符，再以"/"拆分成字符串数组
		String[] tEleSon = tElePra[0].substring(1).split("/");
		for (int i = 0; i < tArry.size(); i++)
		{
			// 得到集合里面每个元素
			String tPelement = (String) tArry.get(i);
			// 如果集合中的元素有条形码对象标签（以"#"开始的元素），则把它放到集合tBarCode中
			if (tPelement.indexOf("#") == 0)
			{
				tBarCode.add(tPelement);
			}
			// 如果集合中的元素有文件合并的标签（以"@"开始的元素），则把它放到集合tFileCom中
			else if (tPelement.indexOf("@") == 0)
			{
				tFileCom.add(tPelement);
			}
			// 如果集合中的元素有展现控制的标签（以"$"开始的元素），则把它放到集合tDisplay中
			else if (tPelement.indexOf("$") == 0)
			{
				tDisplay.add(tPelement);
			}
		}
		try
		{
			if (tEleSon.length == 2)
			{
				// 两层标签的生成
				mXmlExport.createTitle(tEleSon[0]);
				String tTwoSonValue = "";
				for (int k = 0; k < tArry.size(); k++)
				{
					String tTwo = (String) tArry.get(k);
					// 先把每个元素以","拆分成标签名和标签值
					String[] tTwoPra = tTwo.split("&");
					if (tTwoPra.length == 3)
					{
						tTwoSonValue = tTwoPra[2];
					}
					else
					{
						tTwoSonValue = "";
					}
					// 把元素截掉第一个值，再以"/"拆分成字符串数组
					String[] tTwoSon = tTwoPra[0].substring(1).split("/");
					mXmlExport.addElement(tTwoSon[0], tTwoSon[1], tTwoSonValue);
				}
			}
			else if (tEleSon.length == 3)
			{
				// 三层标签的生成
				ListTable tListTable = new ListTable();
				tListTable.setName(tEleSon[0]);
				String[] tTitle = new String[tArry.size()];
				String[] StrCont = new String[tArry.size()];
				String[] tThrSon = new String[3];
				for (int j = 0; j < tArry.size(); j++)
				{
					String tThr = (String) tArry.get(j);
					// 先把每个元素以","拆分成标签名和标签值
					String[] tThrPra = tThr.split("&");
					// 把元素截掉第一个值，再以"/"拆分成字符串数组
					tThrSon = tThrPra[0].substring(1).split("/");
					tTitle[j] = tThrSon[2];
					if (tThrPra.length == 3)
					{
						StrCont[j] = tThrPra[2];
					}
					else
					{
						StrCont[j] = "";
					}
				}
				tListTable.add(StrCont);
				mXmlExport.addListTable(tListTable, tTitle, tThrSon[1]);
			}
			else if (tEleSon.length == 1)
			{
				// 文件合并的标签的生成
				if (tFileCom.size() > 0)
				{
					ListTable tListTable = new ListTable();
					tListTable.setName("CombineInfo");
					String[] tTitle = new String[2];
					tTitle[0] = "FileName";
					tTitle[1] = "FileDepict";
					for (int l = 0; l < tFileCom.size(); l++)
					{
						String[] StrCont = new String[2];
						// 得到每个文件合并的标签 并把"@"截掉
						String tFile = ((String) tFileCom.get(l)).substring(1);
						// 先把每个元素以","拆分成标签名和标签值
						String[] tFilPra = tFile.split("&");
						// 将文件名以-拆分成字符串
						String[] tFileName = tFilPra[0].split("-");
						if (tFilPra.length == 3)
						{
							ExeSQL tExe = new ExeSQL();
							String tSql = "select substr(Description,2)  from Lprtxmlstyle a where  a.templeteid ='?mTempleteID?'  and a.Description like concat(concat('%','?Description?'),'%')";
							SQLwithBindVariables sqlbv=new SQLwithBindVariables();
							sqlbv.sql(tSql);
							sqlbv.put("mTempleteID", mTempleteID);
							sqlbv.put("Description", tFilPra[2]);
							SSRS tResult = tExe.execSQL(sqlbv);
							// 校验表中里有没有该合并文件
							if (tResult != null && tResult.getMaxRow() > 0)
							{
								for (int i = 1; i <= tResult.getMaxRow(); i++)
								{
									// 得到每个文件合并的标签
									String[] tDes = tResult.GetText(1, i).split("-");
									if (tDes[0].equals(tFilPra[2]))
									{
										StrCont[0] = tFilPra[2];
									}
								}
								if (StrCont[0].equals(""))
								{
									CError tError = new CError();
									tError.moduleName = "CreateXMLFile";
									tError.functionName = "createMulTag";
									tError.errorMessage = "要合并的文件" + tFilPra[2] + "不存在！";
									mErrors.addOneError(tError);
									return false;
								}
							}
							else
							{
								CError tError = new CError();
								tError.moduleName = "CreateXMLFile";
								tError.functionName = "createMulTag";
								tError.errorMessage = "要合并的文件" + tFilPra[2] + "不存在！";
								mErrors.addOneError(tError);
								return false;
							}
						}
						else
						{
							StrCont[0] = "";
						}
						StrCont[1] = tFileName[1];
						tListTable.add(StrCont);
					}
					mXmlExport.addListTable(tListTable, tTitle, "CombineFile");

				}
				// 展现控制标签的生成 2011-8-23
				if (tDisplay.size() > 0)
				{
					String tStrDisplay = "";
					String[] tArrDisplay = null;
					for (int m = 0; m < tDisplay.size(); m++)
					{
						// 去除标识位$
						tStrDisplay = ((String) tDisplay.get(m)).substring(1);
						tArrDisplay = tStrDisplay.split("&");
						if (tArrDisplay.length >= 3)
						{
							if ("1".equals(tArrDisplay[2]))
							{
								mXmlExport.addDisplayControl(tArrDisplay[0]);
							}
						}
					}
				}
				// 条形码对象标签的生成
				if (tBarCode.size() > 0)
				{
					ListTable tListTable = new ListTable();
					tListTable.setName("BarCodeInfo");
					String[] tTitle = new String[5];
					tTitle[0] = "Name";
					tTitle[1] = "Code";
					tTitle[2] = "Param";
					tTitle[3] = "Type";
					tTitle[4] = "Position";
					for (int m = 0; m < tBarCode.size(); m++)
					{
						String[] StrCont = new String[5];
						// 得到每个条形码标签 并把首个"#"截掉
						String tObj = ((String) tBarCode.get(m)).substring(1);
						// 先把每个元素以"="拆分成标签名和标签值
						String[] tBarPra = tObj.split("&");
						StrCont[0] = tBarPra[0];
						if (tBarPra.length == 3)
						{
							StrCont[1] = tBarPra[2];
						}
						else
						{
							StrCont[1] = "";
						}
						StrCont[2] = "BarHeight=20&amp;BarRation=3&amp;BarWidth=1&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10";
						// 如果条形码标签长度大于一，则Type值为字符串数组第二个值 例 a#1，Name为a，Type为1。
						// 如果Type 为 1 则 值为 "BarCode" ；Type 为 2 值为"DBarCode"
						ExeSQL tExe = new ExeSQL();
						String tSql = "select * from lprtcode where  codetype='barcode' and code ='?code?'";
						SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
						sqlbv1.sql(tSql);
						sqlbv1.put("code", tBarPra[0]);
						SSRS tResult = tExe.execSQL(sqlbv1);
						// 得到条形码的类型和坐标
						if (tResult != null && tResult.getMaxRow() > 0)
						{
							StrCont[3] = tResult.GetText(1, 4);
						}
						StrCont[4] = "";
						tListTable.add(StrCont);
					}
					mXmlExport.addListTable(tListTable, tTitle, "BarCode");
				}
			}
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "CreateXMLFile";
			tError.functionName = "createMulTag";
			tError.errorMessage = "生成XML失败!";
			mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 生成同名标签
	 * @return
	 */
	private boolean createMulTags(ArrayList cArry, XmlExportNew cXmlExportNew)
	{
		ArrayList tArry = cArry;
		Collections.sort(tArry);
		XmlExportNew mXmlExport = cXmlExportNew;
		// 同名标签父集合，存放子集合
		ArrayList tA = new ArrayList();
		// 同名标签子集合，存放每组标签
		ArrayList tB = new ArrayList();
		// 第一个父集合中第一个子集合的第二个元素
		String tFiAone = "";
		for (int a = 0; a < tArry.size(); a++)
		{
			tB = new ArrayList();
			// 得到集合元素
			String tFiA = (String) tArry.get(a);
			// 先把元素以"，"拆分成标签名和标签值
			String[] tElA = tFiA.split("&");
			//
			if (tA.size() > 0 && ((ArrayList) tA.get(0)).size() > 1)
			{
				tFiAone = (String) ((ArrayList) tA.get(0)).get(1);
			}
			if (tFiA.equals(tFiAone))
			{
				break;
			}
			// 把元素放入集合
			tB.add(tFiA);
			for (int b = a + 1; b < tArry.size(); b++)
			{
				// 得到集合元素
				String tFiB = (String) tArry.get(b);
				// 先把元素以"，"拆分成标签名和标签值
				String[] tElB = tFiB.split("&");
				// 如果索引相同就放入集合
				if (tElA[1].equals(tElB[1]))
				{
					tB.add(tFiB);
				}
			}
			tA.add(tB);
		}
		if (tA.size() > 0)
		{
			// 得到集合第一个子集合
			ArrayList tArFirst = (ArrayList) tA.get(0);
			// 得到集合第一个子集合的第一个元素
			String tFirst = (String) tArFirst.get(0);
			// 先把第一个元素以"，"拆分成标签名和标签值
			String[] tElePra = tFirst.split("&");
			// 把第一个元素截掉第一个字符，再以"/"拆分成字符串数组
			String[] tEleSon = tElePra[0].substring(1).split("/");
			// 三层标签的生成
			ListTable tListTable = new ListTable();
			tListTable.setName(tEleSon[0]);
			String[] tTitle = new String[tArFirst.size()];
			String[] tThrSa = new String[3];
			for (int s = 0; s < tArFirst.size(); s++)
			{
				// 得到集合第一个子集合的每个元素
				String tFirEa = (String) tArFirst.get(s);
				// 先把每个元素以"，"拆分成标签名和标签值
				String[] tFirPa = tFirEa.split("&");
				// 把标签名截掉第一个字符，再以"/"拆分成字符串数组
				String[] tFirSon = tFirPa[0].substring(1).split("/");
				// 给Title赋值
				tTitle[s] = tFirSon[2];
			}
			for (int r = 0; r < tA.size(); r++)
			{
				// 得到每个子集合
				ArrayList tThr = (ArrayList) tA.get(r);
				String[] StrCont = new String[tArFirst.size()];
				for (int t = 0; t < tThr.size(); t++)
				{
					// 得到集合每个子集合的每个元素
					String tEvery = (String) tThr.get(t);
					// 先把每个元素以","拆分成标签名和标签值
					String[] tThrPra = tEvery.split("&");
					// 把标签名截掉第一个字符，再以"/"拆分成字符串数组
					tThrSa = tThrPra[0].substring(1).split("/");

					if (tThrPra.length == 3)
					{
						StrCont[t] = tThrPra[2];
					}
					else
					{
						StrCont[t] = "";
					}
				}
				tListTable.add(StrCont);
			}
			mXmlExport.addListTable(tListTable, tTitle, tThrSa[1]);
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mTempleteID = (String) mTransferData.getValueByName("TempleteID");
		mLPrtTempleteDB.setTempleteID(mTempleteID);
		mLPrtTempleteDB.getInfo();
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "CreateXMLFile";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
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
}
