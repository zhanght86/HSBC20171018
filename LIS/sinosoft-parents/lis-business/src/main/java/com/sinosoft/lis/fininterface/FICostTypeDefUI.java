package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICostTypeDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FICostTypeDefSchema;
import com.sinosoft.lis.vschema.FICostTypeDefSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: FICostTypeDefUI
 * </p>
 * <p>
 * Description: 财务接口-财务规则参数管理-凭证费用类型定义UI
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

public class FICostTypeDefUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FICostTypeDefUI.class);

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
	private FICostTypeDefSchema mFICostTypeDefSchema = new FICostTypeDefSchema();

	//private FICostTypeDefSet mFICostTypeDefSet = new FICostTypeDefSet();

	private FICostTypeDefBL mFICostTypeDefBL = new FICostTypeDefBL();

	private String mVersionNo = "";
	private String mCertificateID = "";
	private String mCostID = "";
	

	public FICostTypeDefUI()
	{}
	
	public String getVersionNo()
	{
		return mVersionNo;
	}	

	public String getCertificateID()
	{
		return mCertificateID;
	}
	
	public String getCostID()
	{
		return mCostID;
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
		logger.debug("Start FICostTypeDefUI Submit...");
		logger.debug("VersionNo:" + mFICostTypeDefSchema.getVersionNo());
		logger.debug("CertificateID:" + mFICostTypeDefSchema.getCertificateID());
		logger.debug("CostID:" + mFICostTypeDefSchema.getCostID());

		mFICostTypeDefBL.submitData(mInputData, mOperate);

		logger.debug("End FICostTypeDefUI Submit...");
		// 如果有需要处理的错误，则返回
		if (mFICostTypeDefBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(mFICostTypeDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICostTypeDefUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN"))
		{
			this.mResult.clear();
			this.mResult = mFICostTypeDefBL.getResult();
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
		this.mFICostTypeDefSchema.setSchema((FICostTypeDefSchema) cInputData.getObjectByObjectName("FICostTypeDefSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostTypeDefUI";
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
		
		//凭证费用类型定义页面点击“添加”按钮重复录入的校验
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			int Count;
			ExeSQL tExeSQL = new ExeSQL();			
			String sql = "select COUNT(*) from FICostTypeDef where VersionNo='?VersionNo?'  and CostID='?CostID?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("VersionNo", this.mFICostTypeDefSchema.getVersionNo());
			sqlbv.put("CostID", this.mFICostTypeDefSchema.getCostID());
			Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
			if(Count >= 1)
			{
				CError tError = new CError();
				tError.moduleName = "FICostTypeDefUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}	
		}
		
		if(this.mOperate.equals("DELETE||MAIN"))
		{
			int Count;
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "select COUNT(*) from FICostDataAcquisitionDef where VersionNo='?VersionNo?'  and CostOrDataID='?CostOrDataID?'  ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("VersionNo", this.mFICostTypeDefSchema.getVersionNo());
			sqlbv1.put("CostOrDataID", this.mFICostTypeDefSchema.getCostID());
			Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
			if(Count >= 1)
			{
				CError tError = new CError();
				tError.moduleName = "FICertificateTypeDefUI";
				tError.functionName = "dealData";
				tError.errorMessage = "该条记录已被凭证费用数据采集定义页面引用，请先在子页面中删除这些被引用数据后再进行此操作";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}
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
			mInputData.add(this.mFICostTypeDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostTypeDefUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		tTransferdata.setNameAndValue("String", mCostID);
    	mResult.add(tTransferdata);
		return this.mResult;

	}

	public static void main(String[] args)
	{
		VData tVData = new VData();
		String sqlstr = "select * from FICostTypeDef where VersionNo='ver02' and CertificateID='C00001' and CostID = 'D00001' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sqlstr);
		FICostTypeDefSchema tFICostTypeDefSchema = new FICostTypeDefSchema();
		FICostTypeDefSet tFICostTypeDefSet = new FICostTypeDefSet();
		FICostTypeDefDB tFICostTypeDefDB = tFICostTypeDefSchema.getDB();
		tFICostTypeDefSet = tFICostTypeDefDB.executeQuery(sqlbv2);
		if (tFICostTypeDefSet.size() > 0)
		{
			tFICostTypeDefSchema = tFICostTypeDefSet.get(1);
			tFICostTypeDefSchema.setVersionNo("ver1");
			tFICostTypeDefSchema.setCertificateID("C00001");
			tFICostTypeDefSchema.setCostID("D00001");
			tFICostTypeDefSchema.setCostName("D00001NAME");
			tFICostTypeDefSchema.setFinItemType("D");				
			tFICostTypeDefSchema.setFinItemID("000001");
			tFICostTypeDefSchema.setFinItemName("000001NAME");
			tFICostTypeDefSchema.setRemark("COSTREMARK");							
			tFICostTypeDefSchema.setState("01");
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
		tVData.addElement(tFICostTypeDefSchema);
		tVData.addElement("5");
		FICostTypeDefUI tFICostTypeDefUI = new FICostTypeDefUI();
		tFICostTypeDefUI.submitData(tVData, "UPDATE||MAIN");
		// logger.debug("Error:" +
		// tFICostTypeDefUI.mErrors.getFirstError());
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

}
