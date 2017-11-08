package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIPeriodManagementDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.FIPeriodManagementSchema;
import com.sinosoft.utility.*;

public class FIPeriodManagementBL
{
private static Logger logger = Logger.getLogger(FIPeriodManagementBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;


	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private FIPeriodManagementSchema mFIPeriodManagementSchema = new FIPeriodManagementSchema();
	
    private MMap map = new MMap();
    
    private String mmResult = new String();//备用返回条件，暂不启用。

	public FIPeriodManagementBL()
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
			tError.moduleName = "FIPeriodManagementBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败FIPeriodManagementBL-->dealData!";
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
			logger.debug("Start FIPeriodManagementBL Submit...");
			
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "FIPeriodManagementBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";

                this.mErrors.addOneError(tError);
                return false;
            }
            mmResult = "Succ";
			logger.debug("End FIPeriodManagementBL Submit...");
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
		String currentDate = PubFun.getCurrentDate();
		String currentTime = PubFun.getCurrentTime();
		/*//校验本月度的会计起期必须大于上个月度的会计止期
		String lastYear = "";
		String lastMonth = "";
		if(this.mFIPeriodManagementSchema.getmonth().equals("01"))
		{
			 lastMonth = "12";
			 lastYear = String.valueOf(Integer.parseInt(this.mFIPeriodManagementSchema.getyear())-1);
		}
		else
		{
			String temp = String.valueOf(Integer.parseInt(this.mFIPeriodManagementSchema.getmonth())-1);
			lastYear = this.mFIPeriodManagementSchema.getyear();
			if(temp.trim().length()==1)
			{
				lastMonth = "0"+temp;
			}
			else
			lastMonth = temp;
		}
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select count(*) from FIPeriodManagement where year = '"+lastYear+"' and month = '"+lastMonth+"' ";
		if(!tExeSQL.getOneValue(sql).equals("0"))
		{
			String sql1 = "select to_char(enddate,'YYYY-mm-dd') from FIPeriodManagement where year = '"+lastYear+"' and month = '"+lastMonth+"' ";
			String sql2 = "select to_char(to_date('"+this.mFIPeriodManagementSchema.getstartdate()+"','YYYY-mm-dd'),'yyyy-mm-dd') from dual";
			logger.debug(tExeSQL.getOneValue(sql1));
			logger.debug(tExeSQL.getOneValue(sql2));
			if(tExeSQL.getOneValue(sql1).compareTo(tExeSQL.getOneValue(sql2)) > 0 )
		  {
			  CError tError = new CError();
			  tError.moduleName = "FIPeriodManagementBL";
			  tError.functionName = "submitData";
			  tError.errorMessage = "输入的会计月度起期不能小于上一月度的止期";
			  this.mErrors.addOneError(tError);
			  return false;
		  }
		}*/
		
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			this.mFIPeriodManagementSchema.setOperator(mGlobalInput.Operator);
			this.mFIPeriodManagementSchema.setManageCom(mGlobalInput.ManageCom);
			this.mFIPeriodManagementSchema.setMakeDate(currentDate);
			this.mFIPeriodManagementSchema.setMakeTime(currentTime);
			this.mFIPeriodManagementSchema.setModifyDate(currentDate);
			this.mFIPeriodManagementSchema.setModifyTime(currentTime);
		}
		if (this.mOperate.equals("UPDATE||MAIN"))
		{
			FIPeriodManagementDB tFIPeriodManagementDB = new FIPeriodManagementDB();
			tFIPeriodManagementDB.setyear(this.mFIPeriodManagementSchema.getyear());
			tFIPeriodManagementDB.setmonth(this.mFIPeriodManagementSchema.getmonth());
			if (!tFIPeriodManagementDB.getInfo())
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tFIPeriodManagementDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "FIPeriodManagementBL";
				tError.functionName = "submitDat";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			tFIPeriodManagementDB.setyear(this.mFIPeriodManagementSchema.getyear());
			tFIPeriodManagementDB.setmonth(this.mFIPeriodManagementSchema.getmonth());
			tFIPeriodManagementDB.setstartdate(this.mFIPeriodManagementSchema.getstartdate());
			tFIPeriodManagementDB.setenddate(this.mFIPeriodManagementSchema.getenddate());
			tFIPeriodManagementDB.setManageCom(this.mFIPeriodManagementSchema.getManageCom());
			tFIPeriodManagementDB.setstate(this.mFIPeriodManagementSchema.getstate());
			this.mFIPeriodManagementSchema.setSchema(tFIPeriodManagementDB);
			this.mFIPeriodManagementSchema.setModifyTime(currentTime);
			this.mFIPeriodManagementSchema.setModifyDate(currentDate);
			this.mFIPeriodManagementSchema.setOperator(mGlobalInput.Operator);
			this.mFIPeriodManagementSchema.setManageCom(mGlobalInput.ManageCom);
		}
		if(cOperate.equals("INSERT||MAIN"))
		{
			map.put(mFIPeriodManagementSchema,"INSERT");
			tReturn = true;
		}
		if(cOperate.equals("UPDATE||MAIN"))
		{
			map.put(mFIPeriodManagementSchema,"UPDATE");
			tReturn = true;
		}
		if(cOperate.equals("DELETE||MAIN"))
		{
			map.put(mFIPeriodManagementSchema,"DELETE");
			tReturn = true;
		}
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFIPeriodManagementSchema.setSchema((FIPeriodManagementSchema) cInputData.getObjectByObjectName("FIPeriodManagementSchema", 0));
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
			this.mResult.clear();
			this.mResult.add(this.mFIPeriodManagementSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIPeriodManagementBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public String getResult()
	{
		return this.mmResult;
	}
}
