package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FICostDataAcquisitionDefDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FICostDataAcquisitionDefSchema;
import com.sinosoft.lis.schema.FICostTypeDefSchema;
import com.sinosoft.lis.db.FICostTypeDefDB;
import com.sinosoft.utility.*;

public class FICostDataAcquisitionDefBL
{
private static Logger logger = Logger.getLogger(FICostDataAcquisitionDefBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();


	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private FICostDataAcquisitionDefSchema mFICostDataAcquisitionDefSchema = new FICostDataAcquisitionDefSchema();
	
	private MMap map = new MMap();
    


	public FICostDataAcquisitionDefBL()
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
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败FICostDataAcquisitionDefBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		else
		{
			logger.debug("Start FICostDataAcquisitionDefBL Submit...");
			
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "FICostDataAcquisitionDefBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";

                this.mErrors.addOneError(tError);
                return false;
            }
			logger.debug("End FICostDataAcquisitionDefBL Submit...");
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
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			tReturn = insert(cOperate);
		}
		if (this.mOperate.equals("UPDATE||MAIN"))
		{
			tReturn = update(cOperate);
		}
		if (this.mOperate.equals("DELETE||MAIN"))
		{
			tReturn = delete(cOperate);
		}
		return tReturn;
	}
	
	private boolean insert(String cOperate)
	{
		boolean tReturn = true; 
		if(mFICostDataAcquisitionDefSchema.getAcquisitionType().equals("01"))
		{
			logger.debug(mFICostDataAcquisitionDefSchema.getAcquisitionType());
			tReturn = updateficosttypedef(cOperate);
		}
		map.put(mFICostDataAcquisitionDefSchema,"INSERT");
		return tReturn;
	}
	
	/*private boolean updateficosttypedef(String cOperate)
	{
		
	}*/
	private boolean updateficosttypedef(String cOperate)
	{
		try
		{
		logger.debug("Start update ficosttypedef");
		ExeSQL tExeSQL = new ExeSQL();
		String tState = "";
		String sql = "select State from FICostTypeDef where VersionNo= '?VersionNo?' and CostID= '?CostID?' ";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("VersionNo", this.mFICostDataAcquisitionDefSchema.getVersionNo());
		sqlbv.put("CostID", this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		tState = tExeSQL.getOneValue(sqlbv);
		logger.debug(tState);
		FICostTypeDefSchema tFICostTypeDefSchema = new FICostTypeDefSchema();
		FICostTypeDefDB tFICostTypeDefDB = new FICostTypeDefDB();
		tFICostTypeDefDB.setVersionNo(this.mFICostDataAcquisitionDefSchema.getVersionNo());
		tFICostTypeDefDB.setCostID(this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		tFICostTypeDefDB.setState(tState);		
		if(!tFICostTypeDefDB.getInfo())
		{
//			 @@错误处理
			this.mErrors.copyAllErrors(tFICostTypeDefDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "无与该采集编号对应的费用编码!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tFICostTypeDefDB.setState("01");
		tFICostTypeDefSchema.setSchema(tFICostTypeDefDB);
		map.put(tFICostTypeDefSchema, "UPDATE");
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	private boolean update(String cOperate)
	{
		boolean tReturn = true; 
		if(mFICostDataAcquisitionDefSchema.getAcquisitionType().equals("01"))
		{
			logger.debug(mFICostDataAcquisitionDefSchema.getAcquisitionType());
			tReturn = updateficosttypedefforupdate(cOperate);
		}
		FICostDataAcquisitionDefDB tFICostDataAcquisitionDefDB = new FICostDataAcquisitionDefDB();
		tFICostDataAcquisitionDefDB.setVersionNo(this.mFICostDataAcquisitionDefSchema.getVersionNo());
		tFICostDataAcquisitionDefDB.setAcquisitionID(this.mFICostDataAcquisitionDefSchema.getAcquisitionID());
		if (!tFICostDataAcquisitionDefDB.getInfo())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tFICostDataAcquisitionDefDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			tReturn = false;
		}
		tFICostDataAcquisitionDefDB.setVersionNo(this.mFICostDataAcquisitionDefSchema.getVersionNo());
		tFICostDataAcquisitionDefDB.setAcquisitionID(this.mFICostDataAcquisitionDefSchema.getAcquisitionID());
		tFICostDataAcquisitionDefDB.setAcquisitionType(this.mFICostDataAcquisitionDefSchema.getAcquisitionType());
		tFICostDataAcquisitionDefDB.setDataSourceType(this.mFICostDataAcquisitionDefSchema.getDataSourceType());
		tFICostDataAcquisitionDefDB.setCostOrDataID(this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		tFICostDataAcquisitionDefDB.setDistillMode(this.mFICostDataAcquisitionDefSchema.getDistillMode());
		tFICostDataAcquisitionDefDB.setDistillSQL(this.mFICostDataAcquisitionDefSchema.getDistillSQL());
		tFICostDataAcquisitionDefDB.setDistillSQLForOne(this.mFICostDataAcquisitionDefSchema.getDistillSQLForOne());
		tFICostDataAcquisitionDefDB.setDistillClass(this.mFICostDataAcquisitionDefSchema.getDistillClass());
		tFICostDataAcquisitionDefDB.setRemark(this.mFICostDataAcquisitionDefSchema.getRemark());
		this.mFICostDataAcquisitionDefSchema.setSchema(tFICostDataAcquisitionDefDB);
		map.put(mFICostDataAcquisitionDefSchema,"UPDATE");
		return tReturn;
	}
	private boolean updateficosttypedefforupdate(String cOperate)
	{
		try
		{
		logger.debug("Start update ficosttypedef");
		ExeSQL tExeSQL = new ExeSQL();
		String tState = "";
		String sql = "select State from FICostTypeDef where VersionNo= '?VersionNo?' and CostID= '?CostID?' ";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sql);
		sqlbv1.put("VersionNo", this.mFICostDataAcquisitionDefSchema.getVersionNo());
		sqlbv1.put("CostID", this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		tState = tExeSQL.getOneValue(sqlbv1);
		logger.debug(tState);
		FICostTypeDefSchema tFICostTypeDefSchema = new FICostTypeDefSchema();
		FICostTypeDefDB tFICostTypeDefDB = new FICostTypeDefDB();
		tFICostTypeDefDB.setVersionNo(this.mFICostDataAcquisitionDefSchema.getVersionNo());
		tFICostTypeDefDB.setCostID(this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		tFICostTypeDefDB.setState(tState);		
		if(!tFICostTypeDefDB.getInfo())
		{
//			 @@错误处理
			this.mErrors.copyAllErrors(tFICostTypeDefDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "无与该采集编号对应的费用编码!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tFICostTypeDefDB.setState("01");
		tFICostTypeDefSchema.setSchema(tFICostTypeDefDB);
		map.put(tFICostTypeDefSchema, "UPDATE");
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
	
	private boolean delete(String cOperate)
	{
		boolean tReturn = true; 	
		map.put(mFICostDataAcquisitionDefSchema,"DELETE");
		if(mFICostDataAcquisitionDefSchema.getAcquisitionType().equals("01"))
		{
			logger.debug(mFICostDataAcquisitionDefSchema.getAcquisitionType());
			tReturn = updateficosttypedeffordelete(cOperate);
		}
		return tReturn;
	}
	
	private boolean updateficosttypedeffordelete(String cOperate)
	{
		try
		{
		logger.debug("Start update ficosttypedef");
		ExeSQL tExeSQL = new ExeSQL();
		String tState = "";
		String sql = "select State from FICostTypeDef where VersionNo= '?VersionNo?' and CostID= '?CostID?' ";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql);
		sqlbv2.put("VersionNo", this.mFICostDataAcquisitionDefSchema.getVersionNo());
		sqlbv2.put("CostID", this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		tState = tExeSQL.getOneValue(sqlbv2);
		logger.debug(tState);
		FICostTypeDefSchema tFICostTypeDefSchema = new FICostTypeDefSchema();
		FICostTypeDefDB tFICostTypeDefDB = new FICostTypeDefDB();
		tFICostTypeDefDB.setVersionNo(this.mFICostDataAcquisitionDefSchema.getVersionNo());
		tFICostTypeDefDB.setCostID(this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		tFICostTypeDefDB.setState(tState);		
		if(!tFICostTypeDefDB.getInfo())
		{
//			 @@错误处理
			this.mErrors.copyAllErrors(tFICostTypeDefDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "无与该采集编号对应的费用编码!";
			this.mErrors.addOneError(tError);
			return false;
		}
		ExeSQL tExeSQL1 = new ExeSQL();
		int count;
		String sql1 ="select COUNT(*) from FICostDataAcquisitionDef where VersionNo='?VersionNo?' and CostOrDataID= '?CostOrDataID?'";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql1);
		sqlbv3.put("VersionNo", this.mFICostDataAcquisitionDefSchema.getVersionNo());
		sqlbv3.put("CostOrDataID", this.mFICostDataAcquisitionDefSchema.getCostOrDataID());
		count = Integer.parseInt(tExeSQL1.getOneValue(sqlbv3));
		if(count == 1)
		{
			tFICostTypeDefDB.setState("02");
		}
		else
		{
			tFICostTypeDefDB.setState("01");
		}
		tFICostTypeDefSchema.setSchema(tFICostTypeDefDB);
		map.put(tFICostTypeDefSchema, "UPDATE");
		}
		catch (Exception ex)
		{
			CError tError = new CError();
			tError.moduleName = "FICostDataAcquisitionDefBL";
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
		logger.debug("getInputData start");
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFICostDataAcquisitionDefSchema.setSchema((FICostDataAcquisitionDefSchema) cInputData.getObjectByObjectName("FICostDataAcquisitionDefSchema", 0));
		logger.debug("getInputData end");
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
			tError.moduleName = "FICostDataAcquisitionDefBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
