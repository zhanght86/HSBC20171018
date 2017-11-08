package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.FIDetailFinItemCodeSchema;

/**
 * <p>
 * ClassName: FIDetailFinItemCodeBL
 * </p>
 * <p>
 * Description: 财务接口-财务规则参数管理-明细科目分支影射定义BL
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

public class FIDetailFinItemCodeBL
{
private static Logger logger = Logger.getLogger(FIDetailFinItemCodeBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 往界面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	private MMap mMMap = new MMap();

	// private String CurrentDate = PubFun.getCurrentDate();

	// private String CurrentTime = PubFun.getCurrentTime();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	// private TransferData mTransferData = new TransferData();

	// private Reflections mReflections = new Reflections();

	// private JdbcUrl mJdbcUrl = new JdbcUrl();

	// private ExeSQL mExeSQL = new ExeSQL();

	/** 业务处理相关变量 */
	private FIDetailFinItemCodeSchema mFIDetailFinItemCodeSchema = new FIDetailFinItemCodeSchema();

	// private FIDetailFinItemCodeSet mFIDetailFinItemCodeSet = new
	// FIDetailFinItemCodeSet();

	private String mVersionNo = "";
	private String mFinItemID = "";
	private String mJudgementNo = "";
	private String mLevelConditionValue = "";	
	
	public FIDetailFinItemCodeBL()
	{
	}
	
	public String getVersionNo()
	{
		return mVersionNo;
	}	

	public String getFinItemID()
	{
		return mFinItemID;
	}
	
	public String getJudgementNo()
	{
		return mJudgementNo;
	}

	public String getLevelConditionValue()
	{
		return mLevelConditionValue;
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
		if (!getInputData(cInputData))
		{
			return false;
		}
		if (!checkdata())
		{
			return false;
		}
		// 进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败FIDetailFinItemCodeBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		// if (this.mOperate.equals("QUERY||MAIN"))
		// {
		// this.submitquery();
		// }
		if (!pubSubmit())
		{
			return false;
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
		this.mFIDetailFinItemCodeSchema.setSchema((FIDetailFinItemCodeSchema) cInputData.getObjectByObjectName("FIDetailFinItemCodeSchema", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (this.mGlobalInput == null)
		{
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "您输入的管理机构或者操作员代码为空！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (this.mFIDetailFinItemCodeSchema == null)
		{
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "mFIDetailFinItemCodeSchema为空！";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean checkdata()
	{

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * @return
	 */
	private boolean dealData()
	{
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			logger.debug("进入新增模块！");
			mMMap.put(mFIDetailFinItemCodeSchema, "INSERT");
		}

		else if (this.mOperate.equals("UPDATE||MAIN"))
		{

			logger.debug("进入修改模块！");
			mMMap.put(mFIDetailFinItemCodeSchema, "UPDATE");

		}

		else if (this.mOperate.equals("DELETE||MAIN"))
		{

			logger.debug("进入删除模块！");
			mMMap.put(mFIDetailFinItemCodeSchema, "DELETE");

		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * @return
	 */
	// private boolean submitquery()
	// {
	// this.mResult.clear();
	// logger.debug("Start FIDetailFinItemCodeBLQuery Submit...");
	// FIDetailFinItemCodeDB tFIDetailFinItemCodeDB = new
	// FIDetailFinItemCodeDB();
	// tFIDetailFinItemCodeDB.setSchema(this.mFIDetailFinItemCodeSchema);
	// this.mFIDetailFinItemCodeSet = tFIDetailFinItemCodeDB.query();
	// this.mResult.add(this.mFIDetailFinItemCodeSet);
	// logger.debug("End FIDetailFinItemCodeBLQuery Submit...");
	// // 如果有需要处理的错误，则返回
	// if (tFIDetailFinItemCodeDB.mErrors.needDealError())
	// {
	// // @@错误处理
	// this.mErrors.copyAllErrors(tFIDetailFinItemCodeDB.mErrors);
	// CError tError = new CError();
	// tError.moduleName = "FIDetailFinItemCodeBL";
	// tError.functionName = "submitData";
	// tError.errorMessage = "数据提交失败!";
	// this.mErrors.addOneError(tError);
	// return false;
	// }
	// mInputData = null;
	// return true;
	// }

	/**
	 * 准备需要保存的数据
	 * @return boolean
	 */
	private boolean prepareOutputData()
	{
		try
		{
			// this.mInputData = new VData();
			// this.mInputData.add(this.mGlobalInput);
			// this.mInputData.add(this.mFIDetailFinItemCodeSchema);
			mInputData.add(mMMap);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 提交数据
	 * @return
	 */
	private boolean pubSubmit()
	{
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, ""))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIDetailFinItemCodeBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败FIDetailFinItemCodeBL-->pubSubmit!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;

	}

	public VData getResult()
	{
		return this.mResult;
	}

	public static void main(String[] args)
	{

	}

}
