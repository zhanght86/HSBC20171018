package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.*;
import com.sinosoft.lis.schema.FIOperationLogSchema;
import com.sinosoft.lis.schema.FIOperationParameterSchema;
import com.sinosoft.lis.schema.FIDataDealErrSchema;
import com.sinosoft.lis.vschema.FIDataDealErrSet;
import com.sinosoft.lis.vschema.FIOperationParameterSet;
import com.sinosoft.lis.db.FIOperationLogDB;
import com.sinosoft.lis.db.FIOperationParameterDB;
import com.sinosoft.lis.db.FIDataDealErrDB;

import java.io.File;

import com.sinosoft.lis.fininterface.GenTxtFile;
/*
 * fanxin
 * 2008-09-05
*/
public class LogInfoSave
{
private static Logger logger = Logger.getLogger(LogInfoSave.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	
	public CErrors mmErrors = new CErrors();


	/** 往后面传输数据的容器 */
	private VData mInputData;
	
	private VData mInputDealErrData;
	
	private String mContent = "";
	
	private MMap map = new MMap();
	
	private MMap mmap = new MMap();
	
	private FIOperationLogSchema mFIOperationLogSchema  = new FIOperationLogSchema();
	private FIOperationParameterSet mFIOperationParameterSet = new FIOperationParameterSet();
	private FIDataDealErrSet mFIDataDealErrSet = new FIDataDealErrSet();
	
	public LogInfoSave()
	{}
	
	/**
	 * 传输数据的公共方法
	 */
	public boolean submitLogData(VData cInputData,String cContent)
	{
		int tCount = 0;
		// 将操作数据拷贝到本类中
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData,cContent))
		{
			tCount = tCount+1;
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			tCount = tCount+1;
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogInfoSave";
			tError.functionName = "submitData";
			tError.errorMessage = "Error at LogInfoSave-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		if (!prepareOutputData())
		{
			tCount = tCount+1;
			return false;
		}
		else
		{
			logger.debug("Start LogInfoSave Submit...");
			String cOperate = "INSERT||MAIN";
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
    			logger.debug(5);
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "LogInfoSave";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mErrors.addOneError(tError);
                return false;
            }
    		logger.debug("End LogInfoSave Submit...");		
		}
		if(tCount == 0)
		{
			PrintLog();
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
	private boolean dealData()
	{
		boolean tReturn = true;
		if(!InsertFIOperationLogSchema())
		{
			tReturn = false;
		}
		if(!InsertFIOperationParameterSet())
		{
			tReturn = false;
		}

		logger.debug(3);
		return tReturn;
	}
	private boolean PrintLog()
	{
		boolean tReturn = true;
		String AllTxt = this.mContent+"    ||本次日志结束||    "+"\r\n";
		String LogFileName = this.mFIOperationLogSchema.getLogFileName() + ".txt";
		String LogFilePath = this.mFIOperationLogSchema.getLogFilePath() + LogFileName;
		logger.debug("路径和文件名是"+LogFilePath+"内容是："+AllTxt);
		GenTxtFile gTxtFile = new GenTxtFile(LogFilePath, AllTxt);
		gTxtFile.writeTxt();
		return tReturn;
	}
	private boolean InsertFIOperationLogSchema()
	{
		boolean tReturn = true;
		map.put(mFIOperationLogSchema, "INSERT");
		return tReturn;
	}
	private boolean InsertFIOperationParameterSet()
	{
		boolean tReturn = true;
		int tJudge = 0;
		
		for(int i = 0;i<mFIOperationParameterSet.size();i++)
		{
			FIOperationParameterSchema tFIOperationParameterSchema = mFIOperationParameterSet.get(i+1);
			ExeSQL tExeSQL = new ExeSQL();
			int tCount;
			String sql ="select COUNT(*) from FIOperationLog where EventNo = '?EventNo?'  ";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(sql);
			sqlbv.put("EventNo", tFIOperationParameterSchema.getEventNo());
			tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
			ExeSQL tExeSQL1 = new ExeSQL();
			String tString;
			String sql1 = "select EventType from FIOperationLog where EventNo ='?EventNo?'";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql1);
			sqlbv1.put("EventNo", tFIOperationParameterSchema.getEventNo());
			tString = tExeSQL1.getOneValue(sqlbv1);
			if(!((tCount > 0)||(tFIOperationParameterSchema.getEventNo() == mFIOperationLogSchema.getEventNo())))
			{
				tJudge = tJudge+1;
			}
			if(!((tFIOperationParameterSchema.getEventType() == tString)||(tFIOperationParameterSchema.getEventType() == mFIOperationLogSchema.getEventType())))
			{
				tJudge = tJudge+1;
			}
		}
		if(tJudge != 0)
		{
			tReturn = false;
		}
		if(tReturn)
		{
			map.put(mFIOperationParameterSet, "INSERT");	
		}
		else
		{
			logger.debug("插入数据为非法!");
		}
		logger.debug("tJudge======="+tJudge);
		logger.debug("tReturn======"+tReturn);
		return tReturn;
	}
	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData,String cContent)
	{
		this.mFIOperationLogSchema = (FIOperationLogSchema) cInputData.getObjectByObjectName("FIOperationLogSchema", 0);
		this.mFIOperationParameterSet = (FIOperationParameterSet) cInputData.getObjectByObjectName("FIOperationParameterSet", 0);
		this.mContent = cContent;
		logger.debug(2);
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
			tError.moduleName = "LogInfoSave";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug(4);
		return true;
	}
	
	public boolean submitLogDealErrData(VData cInputDealErrData)
	{
		// 将操作数据拷贝到本类中
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputDealErrData(cInputDealErrData))
		{
			return false;
		}
		// 进行业务处理
		if (!dealDealErrData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogInfoSave";
			tError.functionName = "submitData";
			tError.errorMessage = "Error at LogInfoSave-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		if (!prepareOutputDealErrData())
		{
			return false;
		}
		else
		{
			logger.debug("Start LogInfoSave Submit...");
			String cOperate = "INSERT||MAIN";
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputDealErrData, cOperate))
            {
    			logger.debug(5);
                // @@错误处理
                this.mmErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "LogInfoSave";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";
                this.mmErrors.addOneError(tError);
                return false;
            }
    		logger.debug("End LogInfoSave Submit...");		
		}
		mInputDealErrData = null;
		return true;
	}
	
	public boolean prepareOutputDealErrData()
	{

		try
		{
			this.mInputDealErrData = new VData();
			this.mInputDealErrData.add(mmap);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LogInfoSubmit";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	public boolean dealDealErrData()
	{
		boolean tReturn = true;
		mmap.put(mFIDataDealErrSet, "INSERT");
		return  tReturn;
	}
	
	public boolean getInputDealErrData(VData cInputDealErrData)
	{
		this.mFIDataDealErrSet = (FIDataDealErrSet) cInputDealErrData.getObjectByObjectName("FIDataDealErrSet", 0);
		return true;
	}
	
}
