/*
 * <p>ClassName: LDCurrencyBL </p>
 * <p>Description: LDCurrencyBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2003-06-21
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDCurrencyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCurrencySchema;
import com.sinosoft.lis.vschema.LDCurrencySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDCurrencyBL
{
private static Logger logger = Logger.getLogger(LDCurrencyBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LDCurrencySchema mLDCurrencySchema = new LDCurrencySchema();

	private LDCurrencySet mLDCurrencySet = new LDCurrencySet();

	public LDCurrencyBL()
	{}

	public static void main(String[] args)
	{}

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
		// 进行业务处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCurrencyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LDCurrencyBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			return false;
		}
		if (this.mOperate.equals("QUERY||MAIN"))
		{
			this.submitquery();
		}

		PubSubmit tPubSubmit = new PubSubmit();
		if (map != null)
		{
			mInputData.add(map);
		}
		if (!tPubSubmit.submitData(mInputData, mOperate))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDCurrencyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mInputData = null;
		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData()
	{
		// 查询录入主键是否重复
		LDCurrencyDB tLDCurrencyDB = new LDCurrencyDB();
		tLDCurrencyDB.setCurrCode(mLDCurrencySchema.getCurrCode());
		int iCount = tLDCurrencyDB.getCount();
		if (tLDCurrencyDB.mErrors.needDealError())
		{
			this.mErrors.copyAllErrors(tLDCurrencyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDCurrencyBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询所录代码是否存在出错！";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("count:" + iCount);
		if (this.mOperate.equals("INSERT||MAIN"))
		{
			if (iCount > 0)
			{
				CError tError = new CError();
				tError.moduleName = "LDCurrencyBL";
				tError.functionName = "dealData";
				tError.errorMessage = "编码" + mLDCurrencySchema.getCurrCode() + "已存在！";
				this.mErrors.addOneError(tError);
				return false;
			}
			map.put(mLDCurrencySchema, "INSERT");
		}
		else if (this.mOperate.equals("UPDATE||MAIN") || this.mOperate.equals("DELETE||MAIN"))
		{
			if (iCount != 1)
			{
				CError tError = new CError();
				tError.moduleName = "LDCurrencyBL";
				tError.functionName = "dealData";
				tError.errorMessage = "编码" + mLDCurrencySchema.getCurrCode() + "不存在，无法作修改或删除操作！";
				this.mErrors.addOneError(tError);
				return false;
			}
			if (this.mOperate.equals("UPDATE||MAIN"))
			{
				map.put(tLDCurrencyDB.getSchema(), "DELETE");
				map.put(mLDCurrencySchema, "INSERT");
			}
			else if (this.mOperate.equals("DELETE||MAIN"))
			{
				map.put(tLDCurrencyDB.getSchema(), "DELETE");
			}
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mLDCurrencySchema.setSchema((LDCurrencySchema) cInputData.getObjectByObjectName("LDCurrencySchema", 0));
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery()
	{
		this.mResult.clear();
		logger.debug("Start LDCurrencyBLQuery Submit...");
		LDCurrencyDB tLDCurrencyDB = new LDCurrencyDB();
		tLDCurrencyDB.setSchema(this.mLDCurrencySchema);
		this.mLDCurrencySet = tLDCurrencyDB.query();
		this.mResult.add(this.mLDCurrencySet);
		logger.debug("End LDCurrencyBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDCurrencyDB.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tLDCurrencyDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDCurrencyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		mInputData = null;
		return true;
	}

	private boolean prepareOutputData()
	{
		try
		{
			this.mInputData = new VData();
			this.mInputData.add(this.mGlobalInput);
			this.mInputData.add(this.mLDCurrencySchema);
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDCurrencyBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}
}
