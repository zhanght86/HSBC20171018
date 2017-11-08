
package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.fininterface.FICostDataBaseDefBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FICostDataBaseDefSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class FICostDataBaseDefUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FICostDataBaseDefUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private TransferData tTransferdata = new TransferData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private String mmResult;
	private FICostDataBaseDefSchema  mFICostDataBaseDefSchema = new FICostDataBaseDefSchema(); 
	private GlobalInput mGlobalInput = new GlobalInput();
	
	public FICostDataBaseDefUI()
	{}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
		// 将操作数据拷贝到本类中
		this.mOperate = cOperate;

		if (!getInputData(cInputData))
		{
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		FICostDataBaseDefBL tFICostDataBaseDefBL = new FICostDataBaseDefBL();
		logger.debug("Start FICostDataBaseDef UI Submit...");
		tFICostDataBaseDefBL.submitData(mInputData, mOperate);
		logger.debug("End FICostDataBaseDef UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tFICostDataBaseDefBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFICostDataBaseDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICostDataBaseDefUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("INSERT||MAIN"))
		{
			this.mmResult = tFICostDataBaseDefBL.getResult();
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args)
	{
	}

	private boolean prepareOutputData()
	{
		try
		{
			mInputData.clear();
			mInputData.add(this.mGlobalInput);
			mInputData.add(this.mFICostDataBaseDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataBaseDefUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		boolean tReturn = true;
		// 此处增加一些校验代码
		if(this.mOperate.equals("INSERT||MAIN"))
		{
			if (!FICostDataBaseDefInsertFail())
			{
				CError tError = new CError();
				tError.moduleName = "FICostDataBaseDefUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				return false;
			}
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "";
			int tCount;
			sql = "select COUNT(DataBaseOrder) from FICostDataBaseDef where VersionNo = '?VersionNo?' and acquisitionid = '?acquisitionid?' ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("VersionNo", this.mFICostDataBaseDefSchema.getVersionNo());
			sqlbv.put("acquisitionid", this.mFICostDataBaseDefSchema.getAcquisitionID());
			tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
			logger.debug(tExeSQL.getOneValue(sqlbv));
			if(tCount == 0)
			{
				tReturn = InsertFirstValue();
			}
			else if(tCount >=1)
			{
				tReturn = InsertOtherValue();
			}
		}
		if(this.mOperate.equals("DELETE||MAIN"))
		{
			ExeSQL tExeSQL1 = new ExeSQL();
			String sql1 = "";
			int tCount1;
			sql1 = "select MAX(DataBaseOrder) from FICostDataBaseDef where VersionNo = '?VersionNo?' and acquisitionid = '?acquisitionid?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("VersionNo", this.mFICostDataBaseDefSchema.getVersionNo());
			sqlbv1.put("acquisitionid", this.mFICostDataBaseDefSchema.getAcquisitionID());
			tCount1 = Integer.parseInt(tExeSQL1.getOneValue(sqlbv1));
			if(tCount1 > this.mFICostDataBaseDefSchema.getDataBaseOrder())
			{
				CError tError = new CError();
				tError.moduleName = "FICostDataBaseDefUI";
				tError.functionName = "prepareData";
				tError.errorMessage = "删除错误，请将该序号之后的所有数据删除后重新录入";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}
		}
		return tReturn;
	}
	
	private boolean FICostDataBaseDefInsertFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FICostDataBaseDef where VersionNo='?VersionNo?'  and AcquisitionID='?AcquisitionID?'  and DataBaseID='?DataBaseID?' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("VersionNo", this.mFICostDataBaseDefSchema.getVersionNo());
		sqlbv2.put("AcquisitionID", this.mFICostDataBaseDefSchema.getAcquisitionID());
		sqlbv2.put("DataBaseID", this.mFICostDataBaseDefSchema.getDataBaseID());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv2));
		if (Count >= 1)
		{
			tReturn = false;
		}
		return tReturn;
	}
	
	private boolean InsertOtherValue()
	{
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "";
		int tValue;
		sql="select MAX(DataBaseOrder) from FICostDataBaseDef where VersionNo = '?VersionNo?' and acquisitionid = '?acquisitionid?' ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("VersionNo", this.mFICostDataBaseDefSchema.getVersionNo());
		sqlbv3.put("acquisitionid", this.mFICostDataBaseDefSchema.getAcquisitionID());
		tValue = Integer.parseInt(tExeSQL.getOneValue(sqlbv3))+1;
		logger.debug(tExeSQL.getOneValue(sqlbv3));
		mFICostDataBaseDefSchema.setDataBaseOrder(tValue);
		return true;
	}
	private boolean InsertFirstValue()
	{
		mFICostDataBaseDefSchema.setDataBaseOrder(1);
		logger.debug("firstorder====="+mFICostDataBaseDefSchema.getDataBaseOrder());
		return true;
	}


	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFICostDataBaseDefSchema.setSchema((FICostDataBaseDefSchema) cInputData.getObjectByObjectName("FICostDataBaseDefSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataBaseDefUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		tTransferdata.setNameAndValue("String", mmResult);
    	mResult.add(tTransferdata);
    	return mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}
