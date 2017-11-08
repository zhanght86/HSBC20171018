
package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FITableColumnDefDB;
import com.sinosoft.lis.fininterface.FICostDataKeyDefBL;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.FICostDataKeyDefSchema;
import com.sinosoft.lis.vschema.FITableColumnDefSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

public class FICostDataKeyDefUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(FICostDataKeyDefUI.class);

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
	private FICostDataKeyDefSchema  mFICostDataKeyDefSchema = new FICostDataKeyDefSchema(); 
	private GlobalInput mGlobalInput = new GlobalInput();
	
	public FICostDataKeyDefUI()
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
		FICostDataKeyDefBL tFICostDataKeyDefBL = new FICostDataKeyDefBL();
		logger.debug("Start FICostDataKeyDef UI Submit...");
		tFICostDataKeyDefBL.submitData(mInputData, mOperate);
		logger.debug("End FICostDataKeyDef UI Submit...");
		// 如果有需要处理的错误，则返回
		if (tFICostDataKeyDefBL.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFICostDataKeyDefBL.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICostDataKeyDefUI";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mOperate.equals("INSERT||MAIN"))
		{
			this.mmResult = tFICostDataKeyDefBL.getResult();
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
			mInputData.add(this.mFICostDataKeyDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataKeyDefUI";
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
		if (this.mOperate.equals("INSERT||MAIN")||this.mOperate.equals("DELETE||MAIN"))
		{
			FITableColumnDefSet tFITableColumnDefSet = new FITableColumnDefSet();
			FITableColumnDefDB tFITableColumnDefDB = new FITableColumnDefDB();
			tFITableColumnDefDB.setTableID("FIAboriginalData");
			tFITableColumnDefDB.setColumnMark(mFICostDataKeyDefSchema.getKeyID());
			tFITableColumnDefSet = tFITableColumnDefDB.query();
			if (tFITableColumnDefSet == null || tFITableColumnDefSet.size() > 1)
			{
				logger.debug("主键标识字段定义错误，请重新定义");
			}
			mFICostDataKeyDefSchema.setKeyID(tFITableColumnDefSet.get(1).getColumnID());
			if(this.mOperate.equals("INSERT||MAIN"))
			{
				int Count;
				ExeSQL tExeSQL1 = new ExeSQL();
				String sql1 = "select COUNT(*) from FICostDataKeyDef where VersionNo='?VersionNo?'  and AcquisitionID='?AcquisitionID?'  and KeyID='?KeyID?' ";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(sql1);
				sqlbv.put("VersionNo", this.mFICostDataKeyDefSchema.getVersionNo());
				sqlbv.put("AcquisitionID", this.mFICostDataKeyDefSchema.getAcquisitionID());
				sqlbv.put("KeyID", this.mFICostDataKeyDefSchema.getKeyID());
				Count = Integer.parseInt(tExeSQL1.getOneValue(sqlbv));
				if (Count >= 1)
				{
					CError tError = new CError();
					tError.moduleName = "FICostDataKeyDefUI";
					tError.functionName = "dealData";
					tError.errorMessage = "您输入的记录在数据库中已经存在，请重新录入";
					this.mErrors.addOneError(tError);
					return false;
				}
			}
			
		}
		if(this.mOperate.equals("INSERT||MAIN"))
		{
			
			ExeSQL tExeSQL = new ExeSQL();
			String sql = "";
			int tCount;
			sql = "select COUNT(KeyOrder) from FICostDataKeyDef where VersionNo = '?VersionNo?' and acquisitionid = '?acquisitionid?' ";
			SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
			sqlbv1.sql(sql);
			sqlbv1.put("VersionNo", this.mFICostDataKeyDefSchema.getVersionNo());
			sqlbv1.put("acquisitionid", this.mFICostDataKeyDefSchema.getAcquisitionID());
			tCount = Integer.parseInt(tExeSQL.getOneValue(sqlbv1));
			logger.debug(tExeSQL.getOneValue(sqlbv1));
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
			sql1 = "select MAX(KeyOrder) from FICostDataKeyDef where VersionNo = '?VersionNo?' and acquisitionid = '?acquisitionid?' ";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			sqlbv2.sql(sql1);
			sqlbv2.put("VersionNo", this.mFICostDataKeyDefSchema.getVersionNo());
			sqlbv2.put("acquisitionid", this.mFICostDataKeyDefSchema.getAcquisitionID());
			tCount1 = Integer.parseInt(tExeSQL1.getOneValue(sqlbv2));
			if(tCount1 > this.mFICostDataKeyDefSchema.getKeyOrder())
			{
				CError tError = new CError();
				tError.moduleName = "FICostDataKeyDefUI";
				tError.functionName = "prepareData";
				tError.errorMessage = "删除错误，请将该序号之后的所有数据删除后重新录入";
				this.mErrors.addOneError(tError);
				tReturn = false;
			}
		}
		
		return tReturn;
	}
	
		
	
	private boolean InsertOtherValue()
	{
		ExeSQL tExeSQL = new ExeSQL();
		String sql = "";
		int tValue;
		sql="select MAX(KeyOrder) from FICostDataKeyDef where VersionNo = '?VersionNo?' and acquisitionid = '?acquisitionid?' ";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("VersionNo", this.mFICostDataKeyDefSchema.getVersionNo());
		sqlbv3.put("acquisitionid", this.mFICostDataKeyDefSchema.getAcquisitionID());
		tValue = Integer.parseInt(tExeSQL.getOneValue(sqlbv3))+1;
		logger.debug(tExeSQL.getOneValue(sqlbv3));
		mFICostDataKeyDefSchema.setKeyOrder(tValue);
		return true;
	}
	private boolean InsertFirstValue()
	{
		mFICostDataKeyDefSchema.setKeyOrder(1);
		logger.debug("firstorder====="+mFICostDataKeyDefSchema.getKeyOrder());
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFICostDataKeyDefSchema.setSchema((FICostDataKeyDefSchema) cInputData.getObjectByObjectName("FICostDataKeyDefSchema", 0));
		if (mGlobalInput == null)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataKeyDefUI";
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
