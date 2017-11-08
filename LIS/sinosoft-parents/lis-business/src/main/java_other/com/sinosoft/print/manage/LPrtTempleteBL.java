package com.sinosoft.print.manage;
import org.apache.log4j.Logger;

import java.io.File;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.print.func.*;

public class LPrtTempleteBL
{
private static Logger logger = Logger.getLogger(LPrtTempleteBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LPrtTempleteSchema mLPrtTempleteSchema = new LPrtTempleteSchema();

	private LPrtTempleteDB mLPrtTempleteDB = new LPrtTempleteDB();

	private LPrtRelatedSchema mLPrtRelatedSchema = new LPrtRelatedSchema();

	private LPrtTempleteLogSchema mLPrtTempleteLogSchema = new LPrtTempleteLogSchema();

	private TransferData mTransferData = new TransferData();

	@SuppressWarnings("unused")
	private String mOutputTypeName = "";

	private String mPath = "";

	private String mTempleteTypeName = "";

	private String mTempleteID = "";

	private String mFilePath = "";

	/** VTS最大行数 */
	private final int mMaxRow = 100;

	/** VTS最大列数 */
	private final int mMaxCol = 100;

	public LPrtTempleteBL()
	{}

	/**
	 * 传输数据的公共方法
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
		{
			return false;
		}
		// 校验是否为默认模板
		if (!checkData(cInputData, cOperate))
		{
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LPrtTempleteBL-->dealData!";
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
			logger.debug("Start LPrtTempleteBL Submit...");

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, null))
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "LPrtTempleteBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}

			logger.debug("---LPrtTempleteBL---");

		}
		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		String tPrintID = mLPrtRelatedSchema.getPrintID();
		String tLanguage = mLPrtTempleteSchema.getLanguage();
		String tTempleteName = mLPrtTempleteSchema.getTempleteName();
		String tTempleteType = mLPrtTempleteSchema.getTempleteType();
		String tFilePath = mLPrtTempleteSchema.getFilePath();
		mPath = (String) mTransferData.getValueByName("Path");
		mOutputTypeName = (String) mTransferData.getValueByName("OutputTypeName");
		mTempleteTypeName = (String) mTransferData.getValueByName("TempleteTypeName");
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			mLPrtTempleteSchema.setMakeDate(tCurrentDate);
			mLPrtTempleteSchema.setMakeTime(tCurrentTime);
			mLPrtTempleteSchema.setModifyTime(tCurrentTime);
			mLPrtTempleteSchema.setModifyDate(tCurrentDate);
			mLPrtTempleteSchema.setState("0");
			mLPrtTempleteSchema.setOperator(mGlobalInput.Operator);
			if (!mLPrtTempleteSchema.getTempleteID().equals(""))
			{
				CError tError = new CError();
				tError.moduleName = "LPrtTempleteBL";
				tError.functionName = "dealData()";
				tError.errorMessage = "系统中已存在该模板！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (tTempleteType.equals("0"))
			{
				//校验VTS文件的语法，如果有错误则提示错误信息并删除上传的文件
				if (!CheckVTS.CheckVTSFile(mPath + tFilePath, mMaxRow, mMaxCol))
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtTempleteBL";
					tError.functionName = "CheckData";
					tError.errorMessage = CheckVTS.mErrors.getFirstError();
					this.mErrors.addOneError(tError);
					File tFile1 = new File(mPath + tFilePath);
					if (tFile1.exists())
					{
						tFile1.delete();
					}
					return false;
				}
			}
			String tNo = PubFun1.CreateMaxNo("TempleteID", 6);
			logger.debug("(***************"+tNo);
			mLPrtTempleteSchema.setTempleteID(tNo);
			mLPrtRelatedSchema.setTempleteID(tNo);
			
			//取打印类型名称，从前台获取会有乱码 zhaoxf-add-20110414
			String ttSql = "SELECT printname FROM lprtdefinition WHERE printid='?tPrintID?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(ttSql);
			sqlbv.put("tPrintID", tPrintID);
			String tPrintName = new ExeSQL().getOneValue(sqlbv);
			mLPrtTempleteSchema.setTempleteName(tPrintName);
			
			ExeSQL tExe = new ExeSQL();
			String tSql = "select Templetename ,Language,TempleteType from LPrtTemplete where Templetename = '?tTempleteName?' and Language = '?tLanguage?' and TempleteType = '?tTempleteType?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(tSql);
			sqlbv1.put("tTempleteName", tTempleteName);
			sqlbv1.put("tLanguage", tLanguage);
			sqlbv1.put("tTempleteType", tTempleteType);
			SSRS tResult = tExe.execSQL(sqlbv1);
			if (tResult != null && tResult.getMaxRow() > 0)
			{
				if (!tResult.GetText(1, 1).equals(tTempleteName) || !tResult.GetText(1, 2).equals(tLanguage)
						|| !tResult.GetText(1, 3).equals(tTempleteType))
				{
					map.put(mLPrtTempleteSchema, "INSERT");
					map.put(mLPrtRelatedSchema, "INSERT");
				}
				else
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtTempleteBL";
					tError.functionName = "dealData()";
					tError.errorMessage = "系统中已存在该模板！";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			else
			{
				map.put(mLPrtTempleteSchema, "INSERT");
				map.put(mLPrtRelatedSchema, "INSERT");
			}
		}
		if (this.mOperate.equals("UPDATE||MAIN"))
		{
			String tFilePath1 = mLPrtTempleteDB.getFilePath();
			mLPrtTempleteSchema.setMakeDate(mLPrtTempleteDB.getMakeDate());
			mLPrtTempleteSchema.setMakeTime(mLPrtTempleteDB.getMakeTime());
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			mLPrtTempleteSchema.setModifyTime(tCurrentTime);
			mLPrtTempleteSchema.setModifyDate(tCurrentDate);
			mLPrtTempleteSchema.setState("0");
			mLPrtTempleteSchema.setOperator(mGlobalInput.Operator);
			// 文件名称修改
			String tOldFileName = mPath + tFilePath1;
			//将输出类型名修改为模板类型 zhaoxf-modify-20110414
//			String tNewFileName = "print/templete/" + mTempleteTypeName + "/" + tPrintID + tLanguage + mOutputTypeName
//					+ "." + mTempleteTypeName;
			String tNewFileName = "print/templete/" + mTempleteTypeName + "/" + tPrintID + tLanguage + mTempleteTypeName
					+ "." + mTempleteTypeName;
			String tNewFileName1 = mPath + tNewFileName;
			logger.debug("原文件名" + tOldFileName);
			logger.debug("新文件名" + tNewFileName1);
			if (!tOldFileName.equals(tNewFileName1))
			{
				File tFile2 = new File(tOldFileName);
				tFile2.renameTo(new File(tNewFileName1));
				if (tFile2.exists())
				{
					tFile2.delete();
				}
				if (tTempleteType.equals("0"))
				{
					//校验VTS文件的语法，如果有错误则提示错误信息并删除上传的文件
					if (!CheckVTS.CheckVTSFile(tNewFileName1, mMaxRow, mMaxCol))
					{
						// @@错误处理
						CError tError = new CError();
						tError.moduleName = "LPrtTempleteBL";
						tError.functionName = "CheckData";
						tError.errorMessage = CheckVTS.mErrors.getFirstError();
						this.mErrors.addOneError(tError);
						File tFile3 = new File(tNewFileName1);
						if (tFile3.exists())
						{
							tFile3.delete();
						}
						return false;
					}
				}
				logger.debug("文件名称修改！");
			}
			else
			{
				logger.debug("文件名称没有变化！");
			}
			mLPrtTempleteSchema.setFilePath(tNewFileName);
			
			//取打印类型名称，从前台获取会有乱码 zhaoxf-add-20110414
			String ttSql = "SELECT printname FROM lprtdefinition WHERE printid='?tPrintID?'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(ttSql);
			sqlbv2.put("tPrintID", tPrintID);
			String tPrintName = new ExeSQL().getOneValue(sqlbv2);
			mLPrtTempleteSchema.setTempleteName(tPrintName);
			
			map.put(mLPrtTempleteSchema, "UPDATE");
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql("delete  from lprtrelated where templeteid ='?templeteid?'");
			sqlbv3.put("templeteid", mLPrtTempleteSchema.getTempleteID());
			map.put(sqlbv3, "DELETE");
			map.put(mLPrtRelatedSchema, "INSERT");
		}
		if (this.mOperate.equals("DELETE||MAIN"))
		{
			String tFilePath1 = mLPrtTempleteDB.getFilePath();
			// 文件删除
			File tFile1 = new File(mPath + tFilePath1);
			logger.debug("删除文件" + mPath + tFilePath1);
			if (tFile1.exists())
			{
				tFile1.delete();
				logger.debug("文件删除！");
			}
			map.put(mLPrtRelatedSchema, "DELETE");
			map.put(mLPrtTempleteSchema, "DELETE");
		}
		mLPrtTempleteLogSchema = PrintFunc.getTemLog(mOperate.substring(0, 6), mLPrtRelatedSchema.getPrintID(), mLPrtTempleteSchema.getTempleteID(), "1", mGlobalInput.Operator);
		map.put(mLPrtTempleteLogSchema, "INSERT");
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mLPrtTempleteSchema.setSchema((LPrtTempleteSchema) cInputData.getObjectByObjectName("LPrtTempleteSchema", 0));
		mLPrtTempleteDB.setTempleteID(mLPrtTempleteSchema.getTempleteID());
		mLPrtTempleteDB.getInfo();
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mLPrtRelatedSchema.setSchema((LPrtRelatedSchema) cInputData.getObjectByObjectName("LPrtRelatedSchema", 0));
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		return true;
	}

	/**
	 *数据校验，如果操作为insert，校验模板是否为默认模板；如果操作为update，先校验修改后模板是否为默认模板，再校验修改后的模板是否已经存在。
	 */
	private boolean checkData(VData cInputData, String cOperate)
	{
		String tTempleteId = mLPrtTempleteSchema.getTempleteID();
		String tTempleteName = mLPrtTempleteSchema.getTempleteName();
		String tDefaultType = mLPrtTempleteSchema.getDefaultType();
		String tLanguage = mLPrtTempleteSchema.getLanguage();
		String tTempleteType = mLPrtTempleteSchema.getTempleteType();
		String tSql1 = "select count(*) from lprttemplete where defaulttype ='Y' and templetename ='?tTempleteName?'";
		SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
		sqlbv4.sql(tSql1);
		sqlbv4.put("tTempleteName", tTempleteName);
		ExeSQL tExe1 = new ExeSQL();
		// 添加模板时，校验一个打印项目只有一个默认模板
		if (tDefaultType.equals("Y") && cOperate.equals("INSERT||MAIN") && !tExe1.getOneValue(sqlbv4).equals("0"))
		{
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该打印项目已存在默认模板!";
			this.mErrors.addOneError(tError);
			return false;
		}
		String tSql2 = "select count(*) from lprttemplete where defaulttype ='Y' and templetename ='?tTempleteName?'" + " and templeteid not in ('?tTempleteId?')";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(tSql2);
		sqlbv5.put("tTempleteName", tTempleteName);
		sqlbv5.put("tTempleteId", tTempleteId);
		ExeSQL tExe2 = new ExeSQL();
		// 修改模板时，校验一个打印项目只有一个默认模板
		if (tDefaultType.equals("Y") && cOperate.equals("UPDATE||MAIN") && !tExe2.getOneValue(sqlbv5).equals("0"))
		{
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteBL";
			tError.functionName = "checkData";
			tError.errorMessage = "该打印项目已存在默认模板!";
			this.mErrors.addOneError(tError);
			return false;
		}

		ExeSQL tExe3 = new ExeSQL();
		String tSql3 = "select Templetename ,Language,TempleteType from LPrtTemplete where Templetename = '?tTempleteName?' and Language = '?tLanguage?' and TempleteType = '?tTempleteType?'"
				+ "and templeteid not in ('?tTempleteId?')";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(tSql3);
		sqlbv6.put("tTempleteName", tTempleteName);
		sqlbv6.put("tLanguage", tLanguage);
		sqlbv6.put("tTempleteType", tTempleteType);
		sqlbv6.put("tTempleteId", tTempleteId);
		SSRS tResult1 = tExe3.execSQL(sqlbv6);
		// 修改模板时，校验修改后的模板是否已经存在
		if (tResult1 != null && tResult1.getMaxRow() > 0)
		{
			if (cOperate.equals("UPDATE||MAIN")
					&& (tResult1.GetText(1, 1).equals(tTempleteName) && tResult1.GetText(1, 2).equals(tLanguage) && tResult1.GetText(1, 3).equals(tTempleteType)))
			{
				CError tError = new CError();
				tError.moduleName = "LPrtTempleteBL";
				tError.functionName = "checkData";
				tError.errorMessage = "打印模板已存在!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	
	/**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
	@SuppressWarnings("unchecked")
	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(map);
			mResult.clear();
			mTempleteID = mLPrtTempleteSchema.getTempleteID();
			mFilePath = mLPrtTempleteSchema.getFilePath();
			mResult.add(mTempleteID);
			mResult.add(mFilePath);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtTempleteBL";
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

