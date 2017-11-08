package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.FIDataBaseLinkDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.FIDataBaseLinkSchema;
import com.sinosoft.utility.*;

public class FIDataBaseLinkBL
{
private static Logger logger = Logger.getLogger(FIDataBaseLinkBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();


	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private FIDataBaseLinkSchema mFIDataBaseLinkSchema = new FIDataBaseLinkSchema();
	
	private MMap map = new MMap();
    

	//private String tCount = "";

	public FIDataBaseLinkBL()
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
			tError.moduleName = "FIDataBaseLinkBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败FIDataBaseLinkBL-->dealData!";
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
			logger.debug("Start FIDataBaseLinkBL Submit...");
			
			PubSubmit tPubSubmit = new PubSubmit();
            if (!tPubSubmit.submitData(mInputData, cOperate))
            {
                // @@错误处理
                this.mErrors.copyAllErrors(tPubSubmit.mErrors);

                CError tError = new CError();
                tError.moduleName = "FIDataBaseLinkBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据提交失败!";

                this.mErrors.addOneError(tError);
                return false;
            }
			logger.debug("End FIDataBaseLinkBL Submit...");
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
		if ((this.mOperate.equals("INSERT||MAIN1"))||(this.mOperate.equals("INSERT||MAIN2"))||(this.mOperate.equals("INSERT||MAIN3"))||(this.mOperate.equals("INSERT||MAIN4")))
		{
			this.mFIDataBaseLinkSchema.setOperator(mGlobalInput.Operator);
			this.mFIDataBaseLinkSchema.setManageCom(mGlobalInput.ManageCom);
			this.mFIDataBaseLinkSchema.setMakeDate(currentDate);
			this.mFIDataBaseLinkSchema.setMakeTime(currentTime);
			this.mFIDataBaseLinkSchema.setModifyDate(currentDate);
			this.mFIDataBaseLinkSchema.setModifyTime(currentTime);
		}
		if ((this.mOperate.equals("UPDATE||MAIN1"))||(this.mOperate.equals("UPDATE||MAIN2"))||(this.mOperate.equals("UPDATE||MAIN3"))||(this.mOperate.equals("UPDATE||MAIN4")))
		{
			FIDataBaseLinkDB tFIDataBaseLinkDB = new FIDataBaseLinkDB();
			tFIDataBaseLinkDB.setInterfaceCode(this.mFIDataBaseLinkSchema.getInterfaceCode());
			if (!tFIDataBaseLinkDB.getInfo())
			{
				// @@错误处理
				this.mErrors.copyAllErrors(tFIDataBaseLinkDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "FIDataBaseLinkBL";
				tError.functionName = "submitDat";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}

			tFIDataBaseLinkDB.setInterfaceCode(this.mFIDataBaseLinkSchema.getInterfaceCode());
			tFIDataBaseLinkDB.setInterfaceName(this.mFIDataBaseLinkSchema.getInterfaceName());
			tFIDataBaseLinkDB.setDBType(this.mFIDataBaseLinkSchema.getDBType());
			tFIDataBaseLinkDB.setIP(this.mFIDataBaseLinkSchema.getIP());
			tFIDataBaseLinkDB.setPort(this.mFIDataBaseLinkSchema.getPort());
			tFIDataBaseLinkDB.setDBName(this.mFIDataBaseLinkSchema.getDBName());
			tFIDataBaseLinkDB.setServerName(this.mFIDataBaseLinkSchema.getServerName());
			tFIDataBaseLinkDB.setUserName(this.mFIDataBaseLinkSchema.getUserName());
			tFIDataBaseLinkDB.setPassWord(this.mFIDataBaseLinkSchema.getPassWord());
			this.mFIDataBaseLinkSchema.setSchema(tFIDataBaseLinkDB);
			this.mFIDataBaseLinkSchema.setModifyTime(currentTime);
			this.mFIDataBaseLinkSchema.setModifyDate(currentDate);
			this.mFIDataBaseLinkSchema.setOperator(mGlobalInput.Operator);
			this.mFIDataBaseLinkSchema.setManageCom(mGlobalInput.ManageCom);
		}
		if ((this.mOperate.equals("INSERT||MAIN1"))||(this.mOperate.equals("INSERT||MAIN2"))||(this.mOperate.equals("INSERT||MAIN3"))||(this.mOperate.equals("INSERT||MAIN4")))
		{
			map.put(mFIDataBaseLinkSchema,"INSERT");
			tReturn = true;
		}
		if ((this.mOperate.equals("UPDATE||MAIN1"))||(this.mOperate.equals("UPDATE||MAIN2"))||(this.mOperate.equals("UPDATE||MAIN3"))||(this.mOperate.equals("UPDATE||MAIN4")))
		{
			map.put(mFIDataBaseLinkSchema,"UPDATE");
			tReturn = true;
		}
		if ((this.mOperate.equals("DELETE||MAIN1"))||(this.mOperate.equals("DELETE||MAIN2"))||(this.mOperate.equals("DELETE||MAIN3"))||(this.mOperate.equals("DELETE||MAIN4")))
		{
			map.put(mFIDataBaseLinkSchema,"DELETE");
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
		this.mFIDataBaseLinkSchema.setSchema((FIDataBaseLinkSchema) cInputData.getObjectByObjectName("FIDataBaseLinkSchema", 0));
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
			this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mFIDataBaseLinkSchema);
			this.mInputData.add(map);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "FIDataBaseLinkBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}
}
