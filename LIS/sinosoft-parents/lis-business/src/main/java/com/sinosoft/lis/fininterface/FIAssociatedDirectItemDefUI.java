package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIAssociatedDirectItemDefDB;
import com.sinosoft.lis.db.FITableColumnDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FIAssociatedDirectItemDefSchema;
import com.sinosoft.lis.vschema.FIAssociatedDirectItemDefSet;
import com.sinosoft.lis.vschema.FITableColumnDefSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>
 * ClassName: FIAssociatedItemDefUI
 * </p>
 * <p>
 * Description: 财务接口-财务规则参数管理-科目专项定义UI
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

public class FIAssociatedDirectItemDefUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FIAssociatedDirectItemDefUI.class);

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
	private FIAssociatedDirectItemDefSchema mFIAssociatedDirectItemDefSchema = new FIAssociatedDirectItemDefSchema();

	// private FIAssociatedDirectItemDefSet mFIAssociatedDirectItemDefSet = new
	// FIAssociatedDirectItemDefSet();

	private FIAssociatedDirectItemDefBL mFIAssociatedDirectItemDefBL = new FIAssociatedDirectItemDefBL();

	private String mVersionNo = "";

	private String mColumnID = "";

	public FIAssociatedDirectItemDefUI()
	{}

	public String getVersionNo()
	{
		return mVersionNo;
	}

	public String getColumnID()
	{
		return mColumnID;
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
		logger.debug("Start FIAssociatedDirectItemDefUI Submit...");
		logger.debug("VersionNo:" + mFIAssociatedDirectItemDefSchema.getVersionNo());
		logger.debug("ColumnID:" + mFIAssociatedDirectItemDefSchema.getColumnID());

		mFIAssociatedDirectItemDefBL.submitData(mInputData, mOperate);

		logger.debug("End FIAssociatedDirectItemDefUI Submit...");
		// 如果有需要处理的错误，则返回
		if (mFIAssociatedDirectItemDefBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(mFIAssociatedDirectItemDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FIAssociatedDirectItemDefUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("QUERY||MAIN"))
		{
			this.mResult.clear();
			this.mResult = mFIAssociatedDirectItemDefBL.getResult();
		}
		mColumnID = mFIAssociatedDirectItemDefBL.getColumnID();
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
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0)); // (GlobalInput)强制类型转换
		this.mFIAssociatedDirectItemDefSchema.setSchema((FIAssociatedDirectItemDefSchema) cInputData.getObjectByObjectName("FIAssociatedDirectItemDefSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAssociatedDirectItemDefUI";
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
		
		if (this.mOperate.equals("INSERT||MAIN")||this.mOperate.equals("UPDATE||MAIN"))
		{
			tReturn = TransForInsertOrUpdate();
		}
		if (this.mOperate.equals("DELETE||MAIN"))
		{
			tReturn = TransForDelete();
		}

		if (this.mOperate.equals("INSERT||MAIN"))
		{			
			// 科目专项定义页面点击“添加”按钮重复录入的校验
			if (!FIAssociatedDirectItemDefInsertFail())
			{
				CError tError = new CError();
				tError.moduleName = "FIAssociatedDirectItemDefUI";
				tError.functionName = "dealData";
				tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return tReturn;
		

	}
	
	private boolean TransForDelete()
	{
		boolean tReturn = true;
		FITableColumnDefSet tFITableColumnDefSet1 = new FITableColumnDefSet();
		FITableColumnDefDB tFITableColumnDefDB1 = new FITableColumnDefDB();
		tFITableColumnDefDB1.setTableID("FIDataTransResult");
		tFITableColumnDefDB1.setColumnMark(mFIAssociatedDirectItemDefSchema.getColumnID());
		tFITableColumnDefSet1 = tFITableColumnDefDB1.query();
		if (tFITableColumnDefSet1 == null || tFITableColumnDefSet1.size() > 1)
		{
			logger.debug("专项表字段标识定义错误，请重新定义");
			tReturn = false;
		}
		mFIAssociatedDirectItemDefSchema.setColumnID(tFITableColumnDefSet1.get(1).getColumnID());
		return tReturn;
	}
	private boolean TransForInsertOrUpdate()
	{
		boolean tReturn = true;
		FITableColumnDefSet tFITableColumnDefSet = new FITableColumnDefSet();
		FITableColumnDefDB tFITableColumnDefDB = new FITableColumnDefDB();
		tFITableColumnDefDB.setTableID("FIAboriginalData");
		tFITableColumnDefDB.setColumnMark(mFIAssociatedDirectItemDefSchema.getSourceColumnID());
		tFITableColumnDefSet = tFITableColumnDefDB.query();
		if (tFITableColumnDefSet == null || tFITableColumnDefSet.size() > 1)
		{
			logger.debug("上游数据来源字段定义错误，请重新定义");
			tReturn = false;
		}
		try{
			
			mFIAssociatedDirectItemDefSchema.setSourceColumnID(tFITableColumnDefSet.get(1).getColumnID());	
		}catch(Exception e){
			CError tError = new CError();
			tError.moduleName = "FIAssociatedDirectItemDefUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "财务接口无此版本信息。";
			this.mErrors.addOneError(tError);
			return false;
		}
		
		FITableColumnDefSet tFITableColumnDefSet1 = new FITableColumnDefSet();
		FITableColumnDefDB tFITableColumnDefDB1 = new FITableColumnDefDB();
		tFITableColumnDefDB1.setTableID("FIDataTransResult");
		tFITableColumnDefDB1.setColumnMark(mFIAssociatedDirectItemDefSchema.getColumnID());
		tFITableColumnDefSet1 = tFITableColumnDefDB1.query();
		if (tFITableColumnDefSet1 == null || tFITableColumnDefSet1.size() > 1)
		{
			logger.debug("专项表字段标识定义错误，请重新定义");
			tReturn = false;
		}
		mFIAssociatedDirectItemDefSchema.setColumnID(tFITableColumnDefSet1.get(1).getColumnID());
		return tReturn;
	}
	
	private boolean FIAssociatedDirectItemDefInsertFail()
	{
		boolean tReturn = true;
		int Count;
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "select COUNT(*) from FIAssociatedDirectItemDef where VersionNo='?VersionNo?'  and ColumnID='?ColumnID?'  ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("VersionNo", this.mFIAssociatedDirectItemDefSchema.getVersionNo());
		sqlbv.put("ColumnID", this.mFIAssociatedDirectItemDefSchema.getColumnID());
		Count = Integer.parseInt(tExeSQL.getOneValue(sqlbv));
		if (Count >= 1)
		{
			tReturn = false;
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
			mInputData.add(this.mFIAssociatedDirectItemDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIAssociatedDirectItemDefUI";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		tTransferdata.setNameAndValue("String", mColumnID);
    	mResult.add(tTransferdata);
		return this.mResult;

	}

	public static void main(String[] args)
	{
		/*VData tVData = new VData();
		String sqlstr = "select * from FIAssociatedDirectItemDef where VersionNo='ver02' and AssociatedID = '100003' ";
		FIAssociatedDirectItemDefSchema tFIAssociatedDirectItemDefSchema = new FIAssociatedDirectItemDefSchema();
		FIAssociatedDirectItemDefSet tFIAssociatedDirectItemDefSet = new FIAssociatedDirectItemDefSet();
		FIAssociatedDirectItemDefDB tFIAssociatedDirectItemDefDB = tFIAssociatedDirectItemDefSchema.getDB();
		tFIAssociatedDirectItemDefSet = tFIAssociatedDirectItemDefDB.executeQuery(sqlstr);
		if (tFIAssociatedDirectItemDefSet.size() > 0)
		{
			tFIAssociatedDirectItemDefSchema = tFIAssociatedDirectItemDefSet.get(1);
			tFIAssociatedDirectItemDefSchema.setVersionNo("ver02");
			tFIAssociatedDirectItemDefSchema.setAssociatedID("100003");
			tFIAssociatedDirectItemDefSchema.setAssociatedName("100003name");
			tFIAssociatedDirectItemDefSchema.setColumnID("Payintv");
			tFIAssociatedDirectItemDefSchema.setTransFlag("S");
			tFIAssociatedDirectItemDefSchema.setTransSQL("select 1 from dual");
			tFIAssociatedDirectItemDefSchema.setTransClass("1");
			tFIAssociatedDirectItemDefSchema.setReMark("remark");
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
		tVData.addElement(tFIAssociatedDirectItemDefSchema);
		tVData.addElement("5");
		FIAssociatedDirectItemDefUI tFIAssociatedDirectItemDefUI = new FIAssociatedDirectItemDefUI();
		tFIAssociatedDirectItemDefUI.submitData(tVData, "UPDATE||MAIN");
		// logger.debug("Error:" +
		// tFIAssociatedDirectItemDefUI.mErrors.getFirstError());*/
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

}
