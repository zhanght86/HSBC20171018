package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.db.FITableColumnDefDB;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.FIAssociatedItemDefSchema;
import com.sinosoft.lis.vschema.FITableColumnDefSet;

/**
 * <p>
 * ClassName: FIAssociatedItemDefBL
 * </p>
 * <p>
 * Description: 财务接口-财务规则参数管理-科目专项定义BL
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

public class FIAssociatedItemDefBL
{
private static Logger logger = Logger.getLogger(FIAssociatedItemDefBL.class);

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
	private FIAssociatedItemDefSchema mFIAssociatedItemDefSchema = new FIAssociatedItemDefSchema();

	// private FIAssociatedItemDefSet mFIAssociatedItemDefSet = new
	// FIAssociatedItemDefSet();

	private String mVersionNo = "";

	private String mAssociatedID = "";

	public FIAssociatedItemDefBL()
	{}

	public String getVersionNo()
	{
		return mVersionNo;
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
			tError.moduleName = "FIAssociatedItemDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败FIAssociatedItemDefBL-->dealData!";
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
		this.mFIAssociatedItemDefSchema.setSchema((FIAssociatedItemDefSchema) cInputData.getObjectByObjectName("FIAssociatedItemDefSchema", 0));
		
//		FITableColumnDefSet tFITableColumnDefSet = new FITableColumnDefSet();
//		FITableColumnDefDB tFITableColumnDefDB = new FITableColumnDefDB();
//		tFITableColumnDefDB.setTableID("FIAboriginalData");
//		tFITableColumnDefDB.setColumnMark(mFIAssociatedItemDefSchema.getSourceColumnID());
//		tFITableColumnDefSet = tFITableColumnDefDB.query();
//		if (tFITableColumnDefSet == null || tFITableColumnDefSet.size() > 1)
//		{
//			logger.debug("上游数据来源字段定义错误，请重新定义");
//		}
//		mFIAssociatedItemDefSchema.setSourceTableID(tFITableColumnDefSet.get(1).getColumnID());
		
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (this.mGlobalInput == null)
		{
			CError tError = new CError();
			tError.moduleName = "FIAssociatedItemDefBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "您输入的管理机构或者操作员代码为空！";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (this.mFIAssociatedItemDefSchema == null)
		{
			CError tError = new CError();
			tError.moduleName = "FIAssociatedItemDefBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "mFIAssociatedItemDefSchema为空！";
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
			mMMap.put(mFIAssociatedItemDefSchema, "INSERT");
		}

		else if (this.mOperate.equals("UPDATE||MAIN"))
		{

			logger.debug("进入修改模块！");
			mMMap.put(mFIAssociatedItemDefSchema, "UPDATE");

		}

		else if (this.mOperate.equals("DELETE||MAIN"))
		{

			logger.debug("进入删除模块！");
			mMMap.put(mFIAssociatedItemDefSchema, "DELETE");

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
	// logger.debug("Start FIAssociatedItemDefBLQuery Submit...");
	// FIAssociatedItemDefDB tFIAssociatedItemDefDB = new
	// FIAssociatedItemDefDB();
	// tFIAssociatedItemDefDB.setSchema(this.mFIAssociatedItemDefSchema);
	// this.mFIAssociatedItemDefSet = tFIAssociatedItemDefDB.query();
	// this.mResult.add(this.mFIAssociatedItemDefSet);
	// logger.debug("End FIAssociatedItemDefBLQuery Submit...");
	// // 如果有需要处理的错误，则返回
	// if (tFIAssociatedItemDefDB.mErrors.needDealError())
	// {
	// // @@错误处理
	// this.mErrors.copyAllErrors(tFIAssociatedItemDefDB.mErrors);
	// CError tError = new CError();
	// tError.moduleName = "FIAssociatedItemDefBL";
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
			// this.mInputData.add(this.mFIAssociatedItemDefSchema);
			mInputData.add(mMMap);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAssociatedItemDefBL";
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
			tError.moduleName = "FIAssociatedItemDefBL";
			tError.functionName = "PubSubmit.submitData";
			tError.errorMessage = "数据提交失败FIAssociatedItemDefBL-->pubSubmit!";
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
