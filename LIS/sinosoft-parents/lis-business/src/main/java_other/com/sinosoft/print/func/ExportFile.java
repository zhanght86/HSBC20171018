package com.sinosoft.print.func;
import org.apache.log4j.Logger;

import java.util.ArrayList;

import com.sinosoft.lis.db.LPrtTempleteDB;
import com.sinosoft.utility.*;

public class ExportFile
{
private static Logger logger = Logger.getLogger(ExportFile.class);


	private LPrtTempleteDB mLPrtTempleteDB = new LPrtTempleteDB();

	/** 错误处理类 */
	private CErrors mErrors = new CErrors();

	/** 处理结果 */
	private VData mResult = new VData();

	public ExportFile()
	{}

	/**
	 * 导出文件数据
	 * @param cTempleteID 文件的ID
	 * @return
	 */
	public boolean exportData(String cTempleteID)
	{
		String tTempleteID = cTempleteID;
		mLPrtTempleteDB.setTempleteID(tTempleteID);
		mLPrtTempleteDB.getInfo();
		String tTempleteName = mLPrtTempleteDB.getTempleteName();
		String tOutputType = mLPrtTempleteDB.getOutputType();
		XmlExportNew tXmlExportNew = new XmlExportNew();
		tXmlExportNew.createDocument(tTempleteName, "", "", tOutputType);
		tXmlExportNew.setUserLanguage(mLPrtTempleteDB.getLanguage());
		TextTag tTextTag;
		ExeSQL tExe = new ExeSQL();
		String tSql = "select distinct a.Description from Lprtxmlstyle a where a.Templeteid ='?tTempleteID?' order by a.Description";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("tTempleteID", tTempleteID);
		SSRS tResult = tExe.execSQL(sqlbv);
		// logger.debug(tResult.MaxRow);
		String tParentCode = "";// 父节点
		int tCodeLen = 0;// 标签层级数
		// int tCoLen = 0;// 标签层级数，用于判断标签层级是否正确
		ArrayList tArr = new ArrayList();// 用来存带有"/"的多层标签
		ArrayList tOthe = new ArrayList();// 用来存放其他的标签
		for (int i = 1; i <= tResult.MaxRow; i++)
		{
			String tResult1 = tResult.GetText(i, 1);
			// 对带有"/"的标签的处理
			if (tResult1.indexOf("/") >= 0)
			{
				tResult1 = tResult1.substring(1);
				// 获得以"/"截取的字符串数值
				String[] tCode = tResult1.split("/");
				tCodeLen = tCode.length;
				// tCodeLen = 1不会有孩子
				if (tCodeLen == 1)
				{
					// 一层标签的生成
					tParentCode = tCode[0];
					tTextTag = new TextTag();
					tTextTag.add(tParentCode, "");
					tXmlExportNew.addTextTag(tTextTag);
					// 一层标签生成后，判断集合里是否有缓存的多层标签，如果有就进行处理
					if (tArr.size() > 0)
					{
						// 多层标签的生成
						if (!createMulTag(tArr, tXmlExportNew))
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
						tArr.add(tCode);
					}
					else if (!tParentCode.equals(tCode[0]))
					{
						// 父亲换掉了
						tParentCode = tCode[0];
						// 特殊的处理，将缓存的父亲和孩子输出到xml里面
						if (tArr.size() > 0)
						{
							if (!createMulTag(tArr, tXmlExportNew))
							{
								return false;
							}
						}
						// 添加新的父亲和孩子信息
						tArr = new ArrayList();
						tArr.add(tCode);
					}
					else
					{
						// 增加孩子信息
						tArr.add(tCode);
					}
				}
			}
			else
			{
				// 把不含"/"的其他标签存到集合中，有可能是条形码对象标签、文件合并的标签等
				String[] tOtheCode = new String[1];
				tOtheCode[0] = tResult1;
				tOthe.add(tOtheCode);
			}
		}
		// 如果最后集合还有缓存数据，则进行处理
		if (tArr.size() > 0)
		{
			if (!createMulTag(tArr, tXmlExportNew))
			{
				return false;
			}
		}
		// 其他标签的生成
		if (tOthe.size() > 0)
		{
			if (!createMulTag(tOthe, tXmlExportNew))
			{
				return false;
			}
		}
		mResult.add(tXmlExportNew);
		return true;
	}

	/**
	 * 生成XML多层标签和一些特殊标签（合并文件，条形码等）
	 * @return
	 */
	private boolean createMulTag(ArrayList cArry, XmlExportNew cXmlExportNew)
	{
		ArrayList tArry = cArry;
		ArrayList tBarCode = new ArrayList();// 存放条形码对象标签
		ArrayList tFileCom = new ArrayList();// 存放文件合并的标签
		ArrayList tDisplay = new ArrayList();// 存放显示控制的标签
		XmlExportNew tXmlExportNew = cXmlExportNew;
		String[] tPCode = (String[]) tArry.get(0);
		for (int i = 0; i < tArry.size(); i++)
		{
			// 得到集合里面每个数组
			String[] tEachPCode = (String[]) tArry.get(i);
			// 判断标签层次是否正确
			if (tEachPCode.length != tPCode.length)
			{
				CError tError = new CError();
				tError.moduleName = "ExportFile";
				tError.functionName = "createMulTag";
				tError.errorMessage = "文件标签定义错误！";
				mErrors.addOneError(tError);
				return false;
			}
			// 如果集合中的元素有条形码对象标签（以"#"开始的元素），则把它放到集合tBarCode中
			if (tEachPCode[0].indexOf("#") == 0)
			{
				tBarCode.add(tEachPCode[0]);
			}
			// 如果集合中的元素有文件合并的标签（以"@"开始的元素），则把它放到集合tFileCom中
			else if (tEachPCode[0].indexOf("@") == 0)
			{
				tFileCom.add(tEachPCode[0]);
			}
			// 显示控制标签处理
			else if (tEachPCode[0].indexOf("$") == 0)
			{
				tDisplay.add(tEachPCode[0]);
			}
		}
		try
		{
			if (tPCode.length == 2)
			{
				// 两层标签的生成
				tXmlExportNew.createTitle(tPCode[0]);
				for (int k = 0; k < tArry.size(); k++)
				{
					tPCode = (String[]) tArry.get(k);
					tXmlExportNew.addElement(tPCode[0], tPCode[1], "");
				}
			}
			else if (tPCode.length == 3)
			{
				// 三层标签的生成
				ListTable tListTable = new ListTable();
				tListTable.setName(tPCode[0]);
				String[] tTitle = new String[tArry.size()];
				String[] StrCont = new String[tArry.size()];
				for (int j = 0; j < tArry.size(); j++)
				{
					tPCode = (String[]) tArry.get(j);
					if (tPCode[2].indexOf("|") >= 0)
					{
						tPCode[2] = tPCode[2].substring(0, tPCode[2].indexOf("|"));
					}
					tTitle[j] = tPCode[2];
					StrCont[j] = "";
				}
				tListTable.add(StrCont);
				tXmlExportNew.addListTable(tListTable, tTitle, tPCode[1]);
			}
			else if (tPCode.length == 1)
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
						// 将文件名以-拆分成字符串 如"Wcrydcwj.vts - 问卷1"
						// 截取后得到两个值"Wcrydcwj.vts" 和"问卷1"
						String[] tFileName = tFile.split("-");
						StrCont[0] = tFileName[0];
						StrCont[1] = tFileName[1];
						tListTable.add(StrCont);
					}
					tXmlExportNew.addListTable(tListTable, tTitle, "CombineFile");

				}
				// 显示控制标签处理 2011-8-29
				if (tDisplay.size() > 0)
				{
					String tStrDisplay = "";
					for (int m = 0; m < tDisplay.size(); m++)
					{
						// 去除标识位$
						tStrDisplay = ((String) tDisplay.get(m)).substring(1);
						tXmlExportNew.addDisplayControl(tStrDisplay);
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
						// 然后把得到的条形码标签以#拆分成字符串，得到一个字符串数组 例："a#1" 截取后得到两个值"a"
						// 和"1"
						String[] tObjName = tObj.split(",");
						StrCont[0] = tObj;
						StrCont[1] = "";
						StrCont[2] = "BarHeight=20&amp;BarRation=3&amp;BarWidth=1&amp;BgColor=FFFFFF&amp;ForeColor=000000&amp;XMargin=10&amp;YMargin=10";
						// 如果条形码标签长度大于一，则Type值为字符串数组第二个值 例 a#1，Name为a，Type为1。
						// 如果Type 为 1 则 值为 "BarCode" ；Type 为 2 值为"DBarCode"
						if (tObjName.length > 1)
						{
							String tType = tObjName[1];
							if (tType.equals("1"))
							{
								StrCont[3] = "BarCode";
							}
							else
							{
								StrCont[3] = "DBarCode";
							}
						}
						else
						{
							// 另外，则Type值为1， 例 a，Name为a，Type为1。
							StrCont[3] = "BarCode";
						}
						// String tXY = (String)
						// mTransferData.getValueByName(tObj);// 得到坐标
						StrCont[4] = "";
						tListTable.add(StrCont);
					}
					tXmlExportNew.addListTable(tListTable, tTitle, "BarCode");
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "ExportFile";
			tError.functionName = "createMulTag";
			tError.errorMessage = "文件导出失败!";
			mErrors.addOneError(tError);
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

	public static void main(String[] args)
	{
		ExportFile tExportFile = new ExportFile();
		tExportFile.exportData("000001");
	}
}
