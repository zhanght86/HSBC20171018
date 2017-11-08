
package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.fininterface.FIDataBaseLinkBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class FIDataBaseLinkUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIDataBaseLinkUI.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	// 业务处理相关变量
	/** 全局数据 */
	private FIDataBaseLinkSchema  mFIDataBaseLinkSchema = new FIDataBaseLinkSchema(); 
	private GlobalInput mGlobalInput = new GlobalInput();
	
		public FIDataBaseLinkUI()
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
		if (!dealData())
		{
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		FIDataBaseLinkBL tFIDataBaseLinkBL = new FIDataBaseLinkBL();
		logger.debug("Start FIDataBaseLink UI Submit...");
		tFIDataBaseLinkBL.submitData(mInputData, mOperate);
		logger.debug("End FIDataBaseLink UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tFIDataBaseLinkBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFIDataBaseLinkBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDataBaseLinkUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
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
			mInputData.add(this.mFIDataBaseLinkSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataBaseLinkUI";
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
		if ((this.mOperate.equals("INSERT||MAIN1"))||(this.mOperate.equals("INSERT||MAIN2"))||(this.mOperate.equals("INSERT||MAIN3"))||(this.mOperate.equals("INSERT||MAIN4")))
		{
			if (!FIDataBaseLinkInsertFail())
			{
				CError tError = new CError();
				tError.moduleName = "FIDataBaseLinkUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return tReturn;
	}
	private boolean FIDataBaseLinkInsertFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FIDataBaseLink where InterfaceCode='?InterfaceCode?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("InterfaceCode", this.mFIDataBaseLinkSchema.getInterfaceCode());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
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
		this.mFIDataBaseLinkSchema.setSchema((FIDataBaseLinkSchema) cInputData.getObjectByObjectName("FIDataBaseLinkSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataBaseLinkUI";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有得到足够的信息！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	public VData getResult() {
		return null;
	}

}
