package com.sinosoft.print.manage;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LPrtCodeSet;
import com.sinosoft.lis.vschema.LPrtXmlStyleSet;
import com.sinosoft.print.func.*;
import com.sinosoft.utility.*;

public class LPrtTemApproveBL
{
private static Logger logger = Logger.getLogger(LPrtTemApproveBL.class);

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
	
	private LPrtTempleteLogSchema mLPrtTempleteLogSchema = new LPrtTempleteLogSchema();
	
	private TransferData mTransferData = new TransferData();
	
	private ReadFile mReadFile = new ReadFile();
	
	private LPrtCodeSet mLPrtCodeSet = null;
	
	private LPrtXmlStyleSet mLPrtXmlStyleSet = null;
	
	private String mErrorTags ="";
	
	public LPrtTemApproveBL()
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
		// 进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LPrtTemApproveBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LPrtTemApproveBL-->dealData!";
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
			logger.debug("Start LPrtTemApproveBL Submit...");

			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mInputData, null))
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);

				CError tError = new CError();
				tError.moduleName = "LPrtTemApproveBL";
				tError.functionName = "LPrtTemApproveBL";
				tError.errorMessage = "数据提交失败!";

				this.mErrors.addOneError(tError);
				return false;
			}

			logger.debug("---LPrtTemApproveBL---");

		}
		mInputData = null;
		return true;
	}
    
	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		String tSql = "select printid from lprtdefinition where printname = '?printname?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSql);
		sqlbv.put("printname", mLPrtTempleteDB.getTempleteName());
		ExeSQL tExe = new ExeSQL();
		String tPrintID = tExe.getOneValue(sqlbv);
		String tTempleteID = mLPrtTempleteSchema.getTempleteID();
		String tState = mLPrtTempleteSchema.getState();//审批结论
		String tOldState = mLPrtTempleteDB.getState();//原状态
		String tFilePath = mLPrtTempleteDB.getFilePath();
		String tTempleteType = mLPrtTempleteDB.getTempleteType();
		String tPath = (String) mTransferData.getValueByName("Path");//得到应用根路径
		tFilePath = tPath + tFilePath;//得到文件全路径
		LPrtTempleteSchema tLPrtTempleteSchema = new LPrtTempleteSchema();
		tLPrtTempleteSchema.setSchema(mLPrtTempleteDB);
		if (this.mOperate.equals("UPDATE||MAIN"))
		{
			String tCurrentDate = PubFun.getCurrentDate();
			String tCurrentTime = PubFun.getCurrentTime();
			tLPrtTempleteSchema.setModifyTime(tCurrentTime);
			tLPrtTempleteSchema.setModifyDate(tCurrentDate);
			tLPrtTempleteSchema.setState(mLPrtTempleteSchema.getState());
			tLPrtTempleteSchema.setRemark(mLPrtTempleteSchema.getRemark());
			tLPrtTempleteSchema.setOperator(mGlobalInput.Operator);
			map.put(tLPrtTempleteSchema, "UPDATE");
			String tOperateType = "";
			
			if(tOldState.equals("1")&&tState.equals("2"))
			{
				CError tError = new CError();
				tError.moduleName = "LPrtTemApproveBL";
				tError.functionName = "dealData";
				tError.errorMessage = "模板发布后不能更改为测试！";
				mErrors.addOneError(tError);
				return false;
			}else if(tOldState.equals("0")&&tState.equals("1"))
			{
				CError tError = new CError();
				tError.moduleName = "LPrtTemApproveBL";
				tError.functionName = "dealData";
				tError.errorMessage = "模板必须测试后才能发布！";
				mErrors.addOneError(tError);
				return false;
			}
			
			//变更为测试状态时 读取数据 存入标签值和条码值
			if(tState.equals("2"))
			{
				try
				{
					//读取文件
					mLPrtXmlStyleSet = mReadFile.readData(tTempleteID, tFilePath, tTempleteType); 
					//读取文件的条形码
					mLPrtCodeSet = mReadFile.readBarCode(tTempleteID, tFilePath, tTempleteType);
				}
				catch (Exception ex)
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LPrtTemApproveBL";
					tError.functionName = "dealData";
					tError.errorMessage = mReadFile.getErrors().getFirstError();
					this.mErrors.addOneError(tError);
					return false;
				}
				//删除原来的数据
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql("delete from  LPrtXmlStyle where templeteid ='?tTempleteID?'");
				sqlbv1.put("tTempleteID", tTempleteID);
				map.put(sqlbv1, "DELETE");
				map.put(mLPrtXmlStyleSet, "INSERT");
				//删除原来的数据
				SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
				sqlbv2.sql("delete from  Lprtcode where templeteid ='?tTempleteID?'");
				sqlbv2.put("tTempleteID", tTempleteID);
				map.put(sqlbv2, "DELETE");
				map.put(mLPrtCodeSet, "INSERT");
			}
			//发布模板前校验模板标签是否有错
			if(tState.equals("1"))
			{
				//校验错误标签
				mErrorTags = PrintFunc.CheckTag(tTempleteID);
				if(!mErrorTags.equals(""))
				{
					CError tError = new CError();
					tError.moduleName = "LPrtTemApproveBL";
					tError.functionName = "dealData";
					tError.errorMessage = "错误标签为"+mErrorTags+"！";
					mErrors.addOneError(tError);
					return false;
				}
			}
			if (tState.equals("0"))
			{
				tOperateType = "DISABLE";
			}
			else if (tState.equals("1"))
			{
				tOperateType = "RELEASE";
			}
			else if (tState.equals("2"))
			{
				tOperateType = "TEST";
			}
			mLPrtTempleteLogSchema = PrintFunc.getTemLog(tOperateType, tPrintID, mLPrtTempleteSchema.getTempleteID(),"2",mGlobalInput.Operator);
			map.put(mLPrtTempleteLogSchema, "INSERT");
		}
		return true;
	}
	
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mLPrtTempleteSchema.setSchema((LPrtTempleteSchema) cInputData.getObjectByObjectName("LPrtTempleteSchema", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData", 0);
		mLPrtTempleteDB.setTempleteID(mLPrtTempleteSchema.getTempleteID());
		mLPrtTempleteDB.getInfo();
		return true;
	}
	
	/**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
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
			tError.moduleName = "LPrtTemApproveBL";
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
