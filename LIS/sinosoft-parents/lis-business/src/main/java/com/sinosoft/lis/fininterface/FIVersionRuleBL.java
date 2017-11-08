package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIRulesVersionDB;
import com.sinosoft.lis.db.FIRulesVersionTraceDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIRulesVersionSchema;
import com.sinosoft.lis.schema.FIRulesVersionTraceSchema;
import com.sinosoft.lis.vschema.FIRulesVersionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

public class FIVersionRuleBL
{
private static Logger logger = Logger.getLogger(FIVersionRuleBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */ 
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;
	
	private int mResult;
	
	String mMaintenanceno = "";

	/** 业务处理相关变量 */
	private FIRulesVersionSchema mFIRulesVersionSchema = new FIRulesVersionSchema();
	
	private FIRulesVersionTraceSchema mFIRulesVersionTraceSchema = new FIRulesVersionTraceSchema();
	
	private FIRulesVersionDB mFIRulesVersionDB = new FIRulesVersionDB();
	
	private FIRulesVersionSet mFIRulesVersionSet = new FIRulesVersionSet();
	
	private MMap map = new MMap();
    

	public FIVersionRuleBL()
	{}

	/**
	 * 传输数据的公共方法
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
		if (!dealData(cOperate))
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败 FIVersionRuleBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		//logger.debug("over dealData");
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		else
		{
			logger.debug("Start FIVersionRuleBL Submit...");
			
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "FIVersionRuleBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";

                this.mErrors.addOneError(tError);
                return false;
            }
			logger.debug("End FIVersionRuleBL Submit...");
		}
		mInputData = null;
		return true;
	}

	public static void main(String[] args)
	{
	}
	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData(String cOperate)
	{
		boolean tReturn = true;
		if (this.mOperate.equals("addVersion"))
		{
			int tCount;
			int tCount0;
			ExeSQL tExeSQL0 = new ExeSQL();
			String sql0 = "select COUNT(*) from FIRulesVersion where startdate >= '?startdate?' and (VersionState != '03' or VersionState is null)";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql0);
			sqlbv.put("startdate", this.mFIRulesVersionSchema.getStartDate());
			tCount0 = Integer.parseInt(tExeSQL0.getOneValue(sqlbv));
			if(tCount0 >= 1)
			{
				CError tError = new CError();
				tError.moduleName = "FIVersionRuleBL";
				tError.functionName = "dealData";
				tError.errorMessage = "非法时间段";
				this.mErrors.addOneError(tError);
				return false;
			}
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "select COUNT(*) from FIRulesVersion where startdate < '?startdate?' and (VersionState != '03' or VersionState is null)";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("startdate", this.mFIRulesVersionSchema.getStartDate());
			logger.debug(tExeSQL.getOneValue(sqlbv1));
			tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
			if(tCount >= 1)
			{	
				tReturn = addVersion(cOperate);
			}
			else if(tCount == 0)
			{
				tReturn = addVersion1(cOperate);
			}
		}
		if (this.mOperate.equals("deleteVersion"))
		{
			if(mFIRulesVersionSchema.getVersionState().equals("03"))
			{
				CError tError = new CError();
				tError.moduleName = "FIVersionRuleBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该版本已经被删除";
				this.mErrors.addOneError(tError);
				return false;
			}
			ExeSQL tExeSQL1 = new ExeSQL();
			ExeSQL tExeSQL2 = new ExeSQL();
			ExeSQL tExeSQL3 = new ExeSQL();
			ExeSQL tExeSQL4 = new ExeSQL();
			//ExeSQL tExeSQL5 = new ExeSQL();
			String sql1 = "select COUNT(*) from FIRULESVERSION where startdate > '?startdate?' ";
			String sql2 = "select COUNT(*) from FIRULESVERSION where startdate > '?startdate?' and versionstate != 03";
			
			//String sql3 = "select enddate from firulesversion where startdate =(select max(startdate) from firulesversion where startdate < '"+this.mFIRulesVersionSchema.getStartDate()+"')";
			String sql3 = "select COUNT(*) from firulesversion where startdate < '?startdate?' and (versionstate != 03 or versionstate is null)";//判断是否为首个版本
			String sql4 = "select COUNT(*) from FIRULESVERSION where startdate > '?startdate?' and versionstate is null";
			//String sql5 = "select MaintenanceState from FIRulesVersionTrace where VersionNo = '"+this.mFIRulesVersionSchema.getVersionNo()+"'  ";
			//String tstate = "";
			//tstate = tExeSQL5.getOneValue(sql5);
			/*if(tstate.equals("02"))
			{
				CError tError = new CError();
				tError.moduleName = "FIVersionRuleBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该版本正处于维护状态，请确认完成或撤销修改后再进行删除。";
				this.mErrors.addOneError(tError);
				return false;
			}*/
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql1);
			sqlbv2.put("startdate", this.mFIRulesVersionSchema.getStartDate());
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			sqlbv3.sql(sql2);
			sqlbv3.put("startdate", this.mFIRulesVersionSchema.getStartDate());
			SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
			sqlbv4.sql(sql3);
			sqlbv4.put("startdate", this.mFIRulesVersionSchema.getStartDate());
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sql4);
			sqlbv5.put("startdate", this.mFIRulesVersionSchema.getStartDate());
			int tCount1;
			int tCount2;
			int tCount3;
			int tCount4;
			tCount1 = Integer.parseInt(tExeSQL1.getOneValue(sqlbv2));
			tCount2 = Integer.parseInt(tExeSQL2.getOneValue(sqlbv3));
			tCount3 = Integer.parseInt(tExeSQL1.getOneValue(sqlbv4));
			tCount4 = Integer.parseInt(tExeSQL4.getOneValue(sqlbv5));
			if(tCount2 > 0||tCount4 >0)
			{
				mResult = 1;
			}
			if (mResult >= 1)
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "FIVersionRuleBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该版本不是最新的版本，不得进行删除操作。";
				this.mErrors.addOneError(tError);
				return false;
			}
			else if(tCount1 == 0||tCount2 == 0)
			{	
				if(tCount3 == 0)
				{
					logger.debug("deleteversion1");
					tReturn = deleteVersion1(cOperate);
				}
				else
				{
					logger.debug("deleteversion");
					tReturn = deleteVersion(cOperate);
				}
			}
			
		}
		if (this.mOperate.equals("applyAmend"))
		{
			tReturn = applyAmend(cOperate);
		}
		if (this.mOperate.equals("CompleteAmend"))
		{
			tReturn = CompleteAmend(cOperate);
		}
		if (this.mOperate.equals("cancelAmend"))
		{
			tReturn = cancelAmend(cOperate);
		}
		return tReturn;
	}
	
	private boolean addVersion1(String cOperate)
	{
		try
		{
			String currentDate = PubFun.getCurrentDate();
			String currentTime = PubFun.getCurrentTime();
			this.mFIRulesVersionSchema.setOperator(mGlobalInput.Operator);
			this.mFIRulesVersionSchema.setMakeDate(currentDate);
			this.mFIRulesVersionSchema.setMakeTime(currentTime);
			this.mFIRulesVersionSchema.setAppDate(currentDate);
			map.put(mFIRulesVersionSchema,"INSERT");
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	private boolean addVersion(String cOperate)
	{
		try
		{
		FIRulesVersionSchema tFIRulesVersionSchema = new FIRulesVersionSchema();
		String currentDate = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		String sql = "";
		sql = "select * from FIRulesVersion where (VersionState != 03 or VersionState is null) and StartDate < '?StartDate?' order by StartDate DESC  ";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sql);
		sqlbv6.put("StartDate", this.mFIRulesVersionSchema.getStartDate());
		mFIRulesVersionSet = mFIRulesVersionDB.executeQuery(sqlbv6);

		tFIRulesVersionSchema = mFIRulesVersionSet.get(1);
		logger.debug(tFIRulesVersionSchema.getVersionNo());
		this.mFIRulesVersionSchema.setOperator(mGlobalInput.Operator);
		this.mFIRulesVersionSchema.setMakeDate(currentDate);
		this.mFIRulesVersionSchema.setMakeTime(currentTime);
		this.mFIRulesVersionSchema.setAppDate(currentDate);
		tFIRulesVersionSchema.setEndDate(mFIRulesVersionSchema.getStartDate());
		map.put(mFIRulesVersionSchema,"INSERT");
		map.put(tFIRulesVersionSchema, "UPDATE");
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	private boolean deleteVersion(String cOperate)
	{
		try
		{
		FIRulesVersionSchema tFIRulesVersionSchema = new FIRulesVersionSchema();
		String sql = "";
		String sql1 = "";
		int tcount;
		boolean tReturn = true;
		ExeSQL tExeSQL1 = new ExeSQL();
		sql = "select * from FIRulesVersion where (VersionState != 03 or VersionState is null) and enddate is not null  order by StartDate DESC  ";
		//sql1= "select ";
		//tcount = Integer.parseInt(tExeSQL1.getOneValue(sql1));
		/*if(tcount == 0)
		{
			logger.debug(".....");
			tReturn = deleteVersion1(cOperate);
			if(tReturn == false)
			{
				return false;
			}
			else
			{
				return true;
			}
		}*/
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sql);
		mFIRulesVersionSet = mFIRulesVersionDB.executeQuery(sqlbv7);
		tFIRulesVersionSchema = mFIRulesVersionSet.get(1);
		logger.debug(tFIRulesVersionSchema.getVersionNo());
		FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
		tFIRulesVersionDB.setVersionNo(this.mFIRulesVersionSchema.getVersionNo());
		if (!tFIRulesVersionDB.getInfo())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIRulesVersionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tFIRulesVersionDB.setVersionState("03");
		tFIRulesVersionSchema.setEndDate("");
		logger.debug(tFIRulesVersionDB.getVersionState());
		logger.debug(tFIRulesVersionSchema.getEndDate());
		this.mFIRulesVersionSchema.setSchema(tFIRulesVersionDB);
		map.put(mFIRulesVersionSchema, "UPDATE");
		map.put(tFIRulesVersionSchema, "UPDATE");
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	private boolean deleteVersion1(String cOperate)
	{
		try
		{
		FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
		tFIRulesVersionDB.setVersionNo(this.mFIRulesVersionSchema.getVersionNo());
		if (!tFIRulesVersionDB.getInfo())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIRulesVersionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tFIRulesVersionDB.setVersionState("03");
		this.mFIRulesVersionSchema.setSchema(tFIRulesVersionDB);
		map.put(mFIRulesVersionSchema, "UPDATE");
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	private boolean applyAmend(String cOperate)
	{
		try
		{
			ExeSQL tExeSQL = new ExeSQL();
			int tCount;
			String sql = "select COUNT(*) from FIRulesVersionTrace where VersionNo = '?VersionNo?' and MaintenanceState = '02' ";
			SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
			sqlbv8.sql(sql);
			sqlbv8.put("VersionNo", this.mFIRulesVersionSchema.getVersionNo());
			tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv8));
			if(tCount > 0)
			{
					CError tError = new CError();
					tError.moduleName = "FIVersionRuleBL";
					tError.functionName = "submitData";
					tError.errorMessage = "在该版本之前仍有未完成修改的操作，请确认完成后再进行此操作！";
					this.mErrors.addOneError(tError);
					return false;
			}
			else
			{
				if((this.mFIRulesVersionSchema.getVersionState().equals("03"))||(this.mFIRulesVersionSchema.getVersionState().equals("02")))
				{
					CError tError = new CError();
					tError.moduleName = "FIVersionRuleBL";
					tError.functionName = "submitData";
					tError.errorMessage = "该版本已经失效或者正在维护！";
					this.mErrors.addOneError(tError);
					return false;
				}
				String currentDate = PubFun.getCurrentDate();
				String currentTime = PubFun.getCurrentTime();
				mMaintenanceno = PubFun1.CreateMaxNo("Maintenanceno",20);
				logger.debug("---------"+mMaintenanceno);
				logger.debug("流水号作业：维护编号" + mMaintenanceno);		
				logger.debug(this.mFIRulesVersionSchema.getVersionState());
				this.mFIRulesVersionTraceSchema.setMaintenanceno(mMaintenanceno);
				this.mFIRulesVersionTraceSchema.setMaintenanceState("02");
				this.mFIRulesVersionTraceSchema.setOperator(mGlobalInput.Operator);
				this.mFIRulesVersionTraceSchema.setMakeDate(currentDate);
				this.mFIRulesVersionTraceSchema.setMakeTime(currentTime);
				FIRulesVersionDB tFIRulesVersionDB = new FIRulesVersionDB();
				tFIRulesVersionDB.setVersionNo(this.mFIRulesVersionSchema.getVersionNo());
				if (!tFIRulesVersionDB.getInfo())
				{
					// @@错误处理
					this.mErrors.copyAllErrors(tFIRulesVersionDB.mErrors);
					CError tError = new CError();
					tError.moduleName = "FIVersionRuleBL";
					tError.functionName = "submitData";
					tError.errorMessage = "数据提交失败!";
					this.mErrors.addOneError(tError);
					return false;
				}
				tFIRulesVersionDB.setVersionState("02");
				this.mFIRulesVersionSchema.setSchema(tFIRulesVersionDB);
				map.put(mFIRulesVersionTraceSchema, "INSERT");
				map.put(mFIRulesVersionSchema, "UPDATE");
			}
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	private boolean CompleteAmend(String cOperate)
	{
		try
		{
		ExeSQL tExeSQL0 = new ExeSQL();
		String sql0 = "";
		String tState = "";
		sql0 = "select MaintenanceState from FIRulesVersionTrace where Maintenanceno = '?Maintenanceno?' ";
		SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
		sqlbv9.sql(sql0);
		sqlbv9.put("Maintenanceno", this.mFIRulesVersionTraceSchema.getMaintenanceno());
		logger.debug("===================11111"+tExeSQL0.getOneValue(sqlbv9));
		tState = tExeSQL0.getOneValue(sqlbv9);
		if(tState.equals("02"))
		{
			FIRulesVersionTraceDB tFIRulesVersionTraceDB = new FIRulesVersionTraceDB();		
			tFIRulesVersionTraceDB.setMaintenanceno(this.mFIRulesVersionTraceSchema.getMaintenanceno());
			if (!tFIRulesVersionTraceDB.getInfo())
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tFIRulesVersionTraceDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "FIVersionRuleBL";
				tError.functionName = "submitDat";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tFIRulesVersionTraceDB.setMaintenanceState("01");
			this.mFIRulesVersionTraceSchema.setSchema(tFIRulesVersionTraceDB);
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "";
			sql = "select VersionNo from FIRulesVersion where VersionNo = '?VersionNo?' ";
			SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
			sqlbv10.sql(sql);
			sqlbv10.put("VersionNo", this.mFIRulesVersionTraceSchema.getVersionNo());
			logger.debug(tExeSQL.getOneValue(sqlbv10));
			FIRulesVersionDB ttFIRulesVersionDB = new FIRulesVersionDB();
			FIRulesVersionSchema tFIRulesVersionSchema = new FIRulesVersionSchema();
			ttFIRulesVersionDB.setVersionNo(tExeSQL.getOneValue(sqlbv10));
			if (!ttFIRulesVersionDB.getInfo())
			{
				// @@错误处理
				this.mErrors.copyAllErrors(ttFIRulesVersionDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "FIVersionRuleBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			ttFIRulesVersionDB.setVersionState("01");
			tFIRulesVersionSchema.setSchema(ttFIRulesVersionDB);
			map.put(mFIRulesVersionTraceSchema,"UPDATE");
			map.put(tFIRulesVersionSchema, "UPDATE");
		}
		else
		{
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "版本维护状态不是申请修改状态，请先申请修改！";
			this.mErrors.addOneError(tError);
			return false;
		}
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	private boolean cancelAmend(String cOperate)
	{
		try
		{
		ExeSQL tExeSQL0 = new ExeSQL();
		String sql0 = "";
		String tState = "";
		sql0 = "select MaintenanceState from FIRulesVersionTrace where Maintenanceno = '?Maintenanceno?' ";
		SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
		sqlbv11.sql(sql0);
		sqlbv11.put("Maintenanceno", this.mFIRulesVersionTraceSchema.getMaintenanceno());
		logger.debug("===================11111"+tExeSQL0.getOneValue(sqlbv11));
		tState = tExeSQL0.getOneValue(sqlbv11);
		if(tState.equals("02"))
		{
		FIRulesVersionTraceDB tFIRulesVersionTraceDB = new FIRulesVersionTraceDB();		
		tFIRulesVersionTraceDB.setMaintenanceno(this.mFIRulesVersionTraceSchema.getMaintenanceno());
		if (!tFIRulesVersionTraceDB.getInfo())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIRulesVersionTraceDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIPeriodManagementBL";
			tError.functionName = "submitDat";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tFIRulesVersionTraceDB.setMaintenanceState("03");
		this.mFIRulesVersionTraceSchema.setSchema(tFIRulesVersionTraceDB);
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "";
		sql = "select VersionNo from FIRulesVersion where VersionNo = '?VersionNo?' ";
		SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
		sqlbv12.sql(sql);
		sqlbv12.put("VersionNo", this.mFIRulesVersionTraceSchema.getVersionNo());
		logger.debug(tExeSQL.getOneValue(sqlbv12));
		FIRulesVersionDB ttFIRulesVersionDB = new FIRulesVersionDB();
		FIRulesVersionSchema tFIRulesVersionSchema = new FIRulesVersionSchema();
		ttFIRulesVersionDB.setVersionNo(tExeSQL.getOneValue(sqlbv12));
		if (!ttFIRulesVersionDB.getInfo())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(ttFIRulesVersionDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		ttFIRulesVersionDB.setVersionState("01");
		tFIRulesVersionSchema.setSchema(ttFIRulesVersionDB);
		map.put(mFIRulesVersionTraceSchema,"UPDATE");
		map.put(tFIRulesVersionSchema, "UPDATE");
		}
		else
		{
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "版本维护状态不是申请修改状态，不能进行撤销申请的操作！";
			this.mErrors.addOneError(tError);
			return false;
		}
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		mOperate = (String)cInputData.getObjectByObjectName("String", 0);
		logger.debug(mOperate);
		if(mOperate.equals("addVersion")||mOperate.equals("deleteVersion"))
		{
			this.mFIRulesVersionSchema.setSchema((FIRulesVersionSchema) cInputData.getObjectByObjectName("FIRulesVersionSchema", 0));
		}
		else if((mOperate.equals("CompleteAmend"))||(mOperate.equals("cancelAmend")))
		{
			this.mFIRulesVersionTraceSchema.setSchema((FIRulesVersionTraceSchema) cInputData.getObjectByObjectName("FIRulesVersionTraceSchema", 0));
		}
		else if(mOperate.equals("applyAmend"))
		{
			this.mFIRulesVersionSchema.setSchema((FIRulesVersionSchema) cInputData.getObjectByObjectName("FIRulesVersionSchema", 0));
			this.mFIRulesVersionTraceSchema.setSchema((FIRulesVersionTraceSchema) cInputData.getObjectByObjectName("FIRulesVersionTraceSchema", 0));
		}
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */

	private boolean prepareOutputData()
	{
		try
		{
			this.mInputData = new VData();
			this.mInputData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIVersionRuleBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

}
