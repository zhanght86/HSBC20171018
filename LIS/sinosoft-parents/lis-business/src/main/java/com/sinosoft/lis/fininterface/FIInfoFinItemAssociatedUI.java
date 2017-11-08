package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIInfoFinItemAssociatedDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIInfoFinItemAssociatedSchema;
import com.sinosoft.lis.vschema.FIInfoFinItemAssociatedSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: FIInfoFinItemAssociatedUI
 * </p>
 * <p>
 * Description: 财务接口-财务规则参数管理-科目关联专项定义UI
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * @Database: 财务接口
 * @author：ZhongYan
 * @version：1.0
 * @CreateDate：2008-08-11
 */

public class FIInfoFinItemAssociatedUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIInfoFinItemAssociatedUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData tTransferdata = new TransferData();

	/** 业务处理相关变量 */
	private FIInfoFinItemAssociatedSchema mFIInfoFinItemAssociatedSchema = new FIInfoFinItemAssociatedSchema();

	//private FIInfoFinItemAssociatedSet mFIInfoFinItemAssociatedSet = new FIInfoFinItemAssociatedSet();

	private FIInfoFinItemAssociatedBL mFIInfoFinItemAssociatedBL = new FIInfoFinItemAssociatedBL();

	private String mVersionNo = "";
	private String mFinItemID = "";
	private String mAssociatedID = "";
	

	public FIInfoFinItemAssociatedUI()
	{}
	
	public String getVersionNo()
	{
		return mVersionNo;
	}	

	public String getFinItemID()
	{
		return mFinItemID;
	}
	
	public String getAssociatedID()
	{
		return mAssociatedID;
	}
	

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
		logger.debug("getInputData");
		if (!getInputData(cInputData))
		{
			return false;
		}

		logger.debug("dealData");
		if (!dealData())
		{
			return false;
		}

		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		logger.debug("Start FIInfoFinItemAssociatedUI Submit...");
		logger.debug("VersionNo:" + mFIInfoFinItemAssociatedSchema.getVersionNo());
		logger.debug("FinItemID:" + mFIInfoFinItemAssociatedSchema.getFinItemID());
		logger.debug("AssociatedID:" + mFIInfoFinItemAssociatedSchema.getAssociatedID());

		mFIInfoFinItemAssociatedBL.submitData(mInputData, mOperate);

		logger.debug("End FIInfoFinItemAssociatedUI Submit...");
		// 如果有需要处理的错误，则返回
		if (mFIInfoFinItemAssociatedBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(mFIInfoFinItemAssociatedBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIInfoFinItemAssociatedUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN"))
		{
			this.mResult.clear();
			this.mResult = mFIInfoFinItemAssociatedBL.getResult();
		}
		mInputData = null;
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * @param cInputData VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0)); //(GlobalInput)强制类型转换
		this.mFIInfoFinItemAssociatedSchema.setSchema((FIInfoFinItemAssociatedSchema) cInputData.getObjectByObjectName("FIInfoFinItemAssociatedSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIInfoFinItemAssociatedUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * @return
	 */
	private boolean dealData()
	{		
		boolean tReturn = true;

		if (this.mOperate.equals("INSERT||MAIN"))
		{
			//科目关联专项定义页面点击“添加”按钮重复录入的校验
			if (!FIInfoFinItemAssociatedInsertFail())
			{
				CError tError = new CError();
				tError.moduleName = "FIInfoFinItemAssociatedUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return tReturn;
	}
	
	private boolean FIInfoFinItemAssociatedInsertFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FIInfoFinItemAssociated where VersionNo='?VersionNo?'  and FinItemID='?FinItemID?'  and AssociatedID='?AssociatedID?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("VersionNo", this.mFIInfoFinItemAssociatedSchema.getVersionNo());
		sqlbv.put("FinItemID", this.mFIInfoFinItemAssociatedSchema.getFinItemID());
		sqlbv.put("AssociatedID", this.mFIInfoFinItemAssociatedSchema.getAssociatedID());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
		if (Count >= 1)
		{
			tReturn = false;
		}
		return tReturn;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * @return
	 */
	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mFIInfoFinItemAssociatedSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIInfoFinItemAssociatedUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		tTransferdata.setNameAndValue("String", mAssociatedID);
    	mResult.add(tTransferdata);
		return this.mResult;

	}

	public static void main(String[] args)
	{
		VData tVData = new VData();
		String sqlstr = "select * from FIInfoFinItemAssociated where VersionNo='ver01' and FinItemID='000001' and AssociatedID = '100001' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sqlstr);
		FIInfoFinItemAssociatedSchema tFIInfoFinItemAssociatedSchema = new FIInfoFinItemAssociatedSchema();
		FIInfoFinItemAssociatedSet tFIInfoFinItemAssociatedSet = new FIInfoFinItemAssociatedSet();
		FIInfoFinItemAssociatedDB tFIInfoFinItemAssociatedDB = tFIInfoFinItemAssociatedSchema.getDB();
		tFIInfoFinItemAssociatedSet = tFIInfoFinItemAssociatedDB.executeQuery(sqlbv1);
		if (tFIInfoFinItemAssociatedSet.size() > 0)
		{
			tFIInfoFinItemAssociatedSchema = tFIInfoFinItemAssociatedSet.get(1);
			tFIInfoFinItemAssociatedSchema.setVersionNo("ver1");
			tFIInfoFinItemAssociatedSchema.setFinItemID("000001");
			tFIInfoFinItemAssociatedSchema.setAssociatedID("100001");
			tFIInfoFinItemAssociatedSchema.setAssociatedName("专项100001");
			tFIInfoFinItemAssociatedSchema.setReMark("专项100001remark");
		}
		else
		{
			logger.debug("error");
			return;
		}
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tVData.clear();
		// 提交
		tVData.addElement(tG);
		tVData.addElement("861100");
		tVData.addElement(tFIInfoFinItemAssociatedSchema);
		tVData.addElement("5");
		FIInfoFinItemAssociatedUI tFIInfoFinItemAssociatedUI = new FIInfoFinItemAssociatedUI();
		tFIInfoFinItemAssociatedUI.submitData(tVData, "UPDATE||MAIN");
		// logger.debug("Error:" +
		// tFIInfoFinItemAssociatedUI.mErrors.getFirstError());
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

}
