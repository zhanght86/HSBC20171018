 package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICertificateTypeDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FICertificateTypeDefSchema;
import com.sinosoft.lis.vschema.FICertificateTypeDefSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: FICertificateTypeDefUI
 * </p>
 * <p>
 * Description: 财务接口-财务规则参数管理-凭证类型定义UI
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

public class FICertificateTypeDefUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FICertificateTypeDefUI.class);

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
	private FICertificateTypeDefSchema mFICertificateTypeDefSchema = new FICertificateTypeDefSchema();

	//private FICertificateTypeDefSet mFICertificateTypeDefSet = new FICertificateTypeDefSet();

	private FICertificateTypeDefBL mFICertificateTypeDefBL = new FICertificateTypeDefBL();

	private String mVersionNo = "";

	private String mCertificateID = "";

	public FICertificateTypeDefUI()
	{}

	public String getVersionNo()
	{
		return mVersionNo;
	}

	public String getCertificateID()
	{
		return mCertificateID;
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
		logger.debug("Start FICertificateTypeDefUI Submit...");
		logger.debug("VersionNo:" + mFICertificateTypeDefSchema.getVersionNo());
		logger.debug("FinItemID:" + mFICertificateTypeDefSchema.getCertificateID());

		mFICertificateTypeDefBL.submitData(mInputData, mOperate);

		logger.debug("End FICertificateTypeDefUI Submit...");
		// 如果有需要处理的错误，则返回
		if (mFICertificateTypeDefBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(mFICertificateTypeDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN"))
		{
			this.mResult.clear();
			this.mResult = mFICertificateTypeDefBL.getResult();
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
		this.mFICertificateTypeDefSchema.setSchema((FICertificateTypeDefSchema) cInputData.getObjectByObjectName("FICertificateTypeDefSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefUI";
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
		
		//凭证类型定义页面点击“添加”按钮重复录入的校验
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			int Count;
			ExeSQL tExeSQL = new ExeSQL();			
			String sql = "select COUNT(*) from FICertificateTypeDef where VersionNo='?VersionNo?'  and CertificateID='?CertificateID?'  ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("VersionNo", this.mFICertificateTypeDefSchema.getVersionNo());
			sqlbv.put("CertificateID", this.mFICertificateTypeDefSchema.getCertificateID());
			Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
			if(Count >= 1)
			{
				CError tError = new CError();
				tError.moduleName = "FICertificateTypeDefUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}			
		}		
		
		//凭证类型定义页面点击“删除”按钮的校验
		if(this.mOperate.equals("DELETE||MAIN"))
		{
			int Count;
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "select COUNT(*) from FICostTypeDef where VersionNo='?VersionNo?'  and CertificateID='?CertificateID?'  ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("VersionNo", this.mFICertificateTypeDefSchema.getVersionNo());
			sqlbv1.put("CertificateID", this.mFICertificateTypeDefSchema.getCertificateID());
			Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
			if(Count >= 1)
			{
				CError tError = new CError();
				tError.moduleName = "FICertificateTypeDefUI";
				tError.functionName = "dealData";
				tError.errorMessage = "该条记录已被凭证费用类型定义页面引用，请先在子页面中删除这些被引用数据后再进行此操作";
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
			mInputData.add(this.mFICertificateTypeDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICertificateTypeDefUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		tTransferdata.setNameAndValue("String", mCertificateID);
    	mResult.add(tTransferdata);
		return this.mResult;

	}

	public static void main(String[] args)
	{
		VData tVData = new VData();
		String sqlstr = "select * from FICertificateTypeDef where VersionNo='ver01' and CertificateID='C00001' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sqlstr);
		FICertificateTypeDefSchema tFICertificateTypeDefSchema = new FICertificateTypeDefSchema();
		FICertificateTypeDefSet tFICertificateTypeDefSet = new FICertificateTypeDefSet();
		FICertificateTypeDefDB tFICertificateTypeDefDB = tFICertificateTypeDefSchema.getDB();
		tFICertificateTypeDefSet = tFICertificateTypeDefDB.executeQuery(sqlbv2);
		if (tFICertificateTypeDefSet.size() > 0)
		{
			tFICertificateTypeDefSchema = tFICertificateTypeDefSet.get(1);
			tFICertificateTypeDefSchema.setVersionNo("ver1");
			tFICertificateTypeDefSchema.setCertificateID("C00001");
			tFICertificateTypeDefSchema.setCertificateName("凭证名称C00001");
			tFICertificateTypeDefSchema.setDetailIndexID("Payintv");
			tFICertificateTypeDefSchema.setDetailIndexName("缴发别");
			tFICertificateTypeDefSchema.setAcquisitionType("01");
			tFICertificateTypeDefSchema.setRemark("描述");
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
		tVData.addElement(tFICertificateTypeDefSchema);
		tVData.addElement("5");
		FICertificateTypeDefUI tFICertificateTypeDefUI = new FICertificateTypeDefUI();
		tFICertificateTypeDefUI.submitData(tVData, "UPDATE||MAIN");
		// logger.debug("Error:" +
		// tFICertificateTypeDefUI.mErrors.getFirstError());
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

}
