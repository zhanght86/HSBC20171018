package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FICostDataBaseDefSchema;
import com.sinosoft.utility.*;

public class FICostDataBaseDefBL
{
private static Logger logger = Logger.getLogger(FICostDataBaseDefBL.class);

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
	private FICostDataBaseDefSchema mFICostDataBaseDefSchema = new FICostDataBaseDefSchema();
	
	private MMap map = new MMap();
    
    private String mmResult = new String();//备用返回条件，暂不启用。


	public FICostDataBaseDefBL()
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
			tError.moduleName = "FICostDataBaseDefBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败 FICostDataBaseDefBL-->dealData!";
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
			logger.debug("Start FICostDataBaseDefBL Submit...");
			
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "FICostDataBaseDefBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";

                this.mErrors.addOneError(tError);
                return false;
            }
            mmResult = "Succ";
			logger.debug("End FICostDataBaseDefBL Submit...");
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
		/*if (this.mOperate.equals("UPDATE||MAIN"))
		{
			FICostDataBaseDefDB tFICostDataBaseDefDB = new FICostDataBaseDefDB();
			tFICostDataBaseDefDB.setVersionNo(this.mFICostDataBaseDefSchema.getVersionNo());
			tFICostDataBaseDefDB.setAcquisitionID(this.mFICostDataBaseDefSchema.getAcquisitionID());
			tFICostDataBaseDefDB.setDataBaseID(this.mFICostDataBaseDefSchema.getDataBaseID());
			if (!tFICostDataBaseDefDB.getInfo())
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tFICostDataBaseDefDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "FICostDataBaseDefBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
			tFICostDataBaseDefDB.setVersionNo(this.mFICostDataBaseDefSchema.getVersionNo());
			tFICostDataBaseDefDB.setAcquisitionID(this.mFICostDataBaseDefSchema.getAcquisitionID());
			tFICostDataBaseDefDB.setDataBaseID(this.mFICostDataBaseDefSchema.getDataBaseID());
			tFICostDataBaseDefDB.setDataBaseName(this.mFICostDataBaseDefSchema.getDataBaseName());
			tFICostDataBaseDefDB.setDataBaseOrder(this.mFICostDataBaseDefSchema.getDataBaseOrder());
			tFICostDataBaseDefDB.setRemark(this.mFICostDataBaseDefSchema.getRemark());
			this.mFICostDataBaseDefSchema.setSchema(tFICostDataBaseDefDB);

		}*/
		if(mOperate.equals("INSERT||MAIN"))
		{
			map.put(mFICostDataBaseDefSchema,"INSERT");
			tReturn = true;
		}
		if(mOperate.equals("UPDATE||MAIN"))
		{
			map.put(mFICostDataBaseDefSchema,"UPDATE");
			tReturn = true;
		}
		if(mOperate.equals("DELETE||MAIN"))
		{
			map.put(mFICostDataBaseDefSchema,"DELETE");
			tReturn = true;
		}
		return tReturn;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		logger.debug("getInputData start");
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		this.mFICostDataBaseDefSchema.setSchema((FICostDataBaseDefSchema) cInputData.getObjectByObjectName("FICostDataBaseDefSchema", 0));
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
			this.mResult.clear();
			this.mResult.add(this.mFICostDataBaseDefSchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FICostDataBaseDefBL";
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
