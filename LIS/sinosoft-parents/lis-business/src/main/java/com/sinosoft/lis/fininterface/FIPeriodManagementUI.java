package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIPeriodManagementSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FIPeriodManagementUI implements BusinessService
{
private static Logger logger = Logger.getLogger(FIPeriodManagementUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private String mmResult;
	private TransferData tTransferdata = new TransferData();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private FIPeriodManagementSchema  mFIPeriodManagementSchema = new FIPeriodManagementSchema(); 
	private GlobalInput mGlobalInput = new GlobalInput();
	
		public FIPeriodManagementUI()
	{}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate)
	{
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
		FIPeriodManagementBL tFIPeriodManagementBL = new FIPeriodManagementBL();
		tFIPeriodManagementBL.submitData(mInputData, mOperate);
		if (tFIPeriodManagementBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIPeriodManagementBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIPeriodManagementUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("INSERT||MAIN"))
		{
			this.mmResult = tFIPeriodManagementBL.getResult();
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
			mInputData.add(this.mFIPeriodManagementSchema);
			logger.debug(mFIPeriodManagementSchema.getstartdate());
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIPeriodManagementUI";
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
			if (!FIPeriodManagementInsertFail())
			{
				CError tError = new CError();
				tError.moduleName = "FIPeriodManagementUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				return false;
			}
			
		}
		if(this.mOperate.equals("INSERT||MAIN"))
		{
			/*if (!FIPeriodManagementDateError())
			{
				CError tError = new CError();
				tError.moduleName = "FIPeriodManagementUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您所输入的日期有误，请重新输入";
				this.mErrors.addOneError(tError);
				return false;
			}*/
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "";
			int tcount;
			sql = "select COUNT(*) from FIPeriodManagement where state = '1'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			tcount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
			logger.debug(this.mFIPeriodManagementSchema.getstate());
			if(this.mFIPeriodManagementSchema.getstate().equals("1"))
			{
				if(tcount >= 1)
				{
					CError tError = new CError();
					tError.moduleName = "FIPeriodManagementUI";
					tError.functionName = "dealData";
					tError.errorMessage = "开启的区间不得大于1个";
					this.mErrors.addOneError(tError);
					return false;
				}		
			}
			
		}
		if(this.mOperate.equals("UPDATE||MAIN"))
		{
			ExeSQL tExeSQL1 = new ExeSQL();
			String sql1 = "";
			String sql2 = "";
			String tstate = "";
			int tcount1;
			if(this.mFIPeriodManagementSchema.getstate().equals("1"))
			{
				sql1 = "select state from FIPeriodManagement where year = '?year?' and month = '?month?' ";
				SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
				sqlbv1.sql(sql1);
				sqlbv1.put("year", this.mFIPeriodManagementSchema.getyear());
				sqlbv1.put("month", this.mFIPeriodManagementSchema.getmonth());
				tstate = tExeSQL1.getOneValue(sqlbv1);
				if(tstate.equals("0"))
				{
					sql2 = "select COUNT(*) from FIPeriodManagement where state = '1'";
					SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
					sqlbv2.sql(sql2);
					tcount1 = Integer.parseInt(tExeSQL1.getOneValue(sqlbv2));
					if(tcount1 >= 1)
					{
						CError tError = new CError();
						tError.moduleName = "FIPeriodManagementUI";
						tError.functionName = "dealData";
						tError.errorMessage = "开启的区间不得大于1个";
						this.mErrors.addOneError(tError);
						return false;
					}
				}
			}
//			sql1 = "select COUNT(*) from FIPeriodManagement where state = '"+this.mFIPeriodManagementSchema.getstate()+"' and year = '"++"' and month = '""' ";
		}
		return tReturn;
	}
	/*private boolean FIPeriodManagementDateError()
	{
		boolean tReturn = true;
		String tErrorS1 = "";
		String tErrorS2 = "";
		tErrorS1 = this.mFIPeriodManagementSchema.getstartdate();
		tErrorS2 = this.mFIPeriodManagementSchema.getenddate();
		tErrorS1 = tErrorS1.substring(5,7);
		tErrorS2 = tErrorS2.substring(5,7);
		if((tErrorS1.equals(this.mFIPeriodManagementSchema.getmonth()))&&(tErrorS2.equals(this.mFIPeriodManagementSchema.getmonth())))
		{
			tReturn = true;
		}
		else
		{
			tReturn = false;
		}
		return tReturn;
	}*/
	private boolean FIPeriodManagementInsertFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FIPeriodManagement where year='?year?' and month ='?month?' ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("year", this.mFIPeriodManagementSchema.getyear());
		sqlbv3.put("month", this.mFIPeriodManagementSchema.getmonth());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv3));
		if (Count >= 1)
		{
			tReturn = false;
		}
		
		return tReturn;
	}
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFIPeriodManagementSchema.setSchema((FIPeriodManagementSchema) cInputData.getObjectByObjectName("FIPeriodManagementSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIPeriodManagementUI";
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
