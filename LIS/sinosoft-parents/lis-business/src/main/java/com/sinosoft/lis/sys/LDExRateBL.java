/*
 * <p>ClassName: LDExRateBL </p>
 * <p>Description: LDExRateBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LIS
 * @CreateDate：2003-06-21
 */
package com.sinosoft.lis.sys;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LBExRateDB;
import com.sinosoft.lis.db.LDExRateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBExRateSchema;
import com.sinosoft.lis.schema.LDExRateSchema;
import com.sinosoft.lis.vschema.LBExRateSet;
import com.sinosoft.lis.vschema.LDExRateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDExRateBL
{
private static Logger logger = Logger.getLogger(LDExRateBL.class);

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
	private LDExRateSchema mLDExRateSchema = new LDExRateSchema();

	private LDExRateSet mLDExRateSet = new LDExRateSet();

	private LBExRateSchema mLBExRateSchema = new LBExRateSchema();

	private LBExRateSet mLBExRateSet = new LBExRateSet();

	public LDExRateBL()
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
			tError.moduleName = "LDExRateBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败LDExRateBL-->dealData!";
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
			tError.moduleName = "LDExRateBL";
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

		if (this.mOperate.equals("INSERT||MAIN") || this.mOperate.equals("UPDATE||MAIN"))
		{
			// 查询录入主键是否重复
			LDExRateDB tLDExRateDB = new LDExRateDB();
			tLDExRateDB.setCurrCode(mLDExRateSchema.getCurrCode());
			tLDExRateDB.setPer(mLDExRateSchema.getPer() + "");
			tLDExRateDB.setDestCode(mLDExRateSchema.getDestCode());

			int iCount = tLDExRateDB.getCount();
			if (tLDExRateDB.mErrors.needDealError())
			{
				this.mErrors.copyAllErrors(tLDExRateDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDExRateBL";
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
					tError.moduleName = "LDExRateBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该外汇牌价已存在，请确认后重新提交！";
					this.mErrors.addOneError(tError);
					return false;
				}
				map.put(mLDExRateSchema, "INSERT");
			}
			else
			{
				if (iCount != 1)
				{
					CError tError = new CError();
					tError.moduleName = "LDExRateBL";
					tError.functionName = "dealData";
					tError.errorMessage = "该外汇牌价不存在，无法作修改或删除操作！";
					this.mErrors.addOneError(tError);
					return false;
				}
				map.put(tLDExRateDB.getSchema(), "DELETE");
				map.put(mLDExRateSchema, "INSERT");
			}
		}
		else if (this.mOperate.equals("DELETE||MAIN"))
		{
			// 查询录入主键是否重复
			LDExRateDB tLDExRateDB = new LDExRateDB();
			tLDExRateDB.setCurrCode(mLBExRateSchema.getCurrCode());
			tLDExRateDB.setPer(mLBExRateSchema.getPer() + "");
			tLDExRateDB.setDestCode(mLBExRateSchema.getDestCode());

			int iCount = tLDExRateDB.getCount();
			if (tLDExRateDB.mErrors.needDealError())
			{
				this.mErrors.copyAllErrors(tLDExRateDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LDExRateBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询所录代码是否存在出错！";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("LDExRate记录 count:" + iCount);

			if (iCount != 1)
			{
				CError tError = new CError();
				tError.moduleName = "LDExRateBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该外汇牌价不存在，无法作备份操作！";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 查询录入主键是否重复
			LBExRateDB tLBExRateDB = new LBExRateDB();
			tLBExRateDB.setCurrCode(mLBExRateSchema.getCurrCode());
			tLBExRateDB.setPer(mLBExRateSchema.getPer() + "");
			tLBExRateDB.setDestCode(mLBExRateSchema.getDestCode());
			tLBExRateDB.setStartDate(mLBExRateSchema.getStartDate());
			tLBExRateDB.setStartTime(mLBExRateSchema.getStartTime());

			iCount = tLBExRateDB.getCount();
			if (tLBExRateDB.mErrors.needDealError())
			{
				this.mErrors.copyAllErrors(tLBExRateDB.mErrors);
				CError tError = new CError();
				tError.moduleName = "LBExRateBL";
				tError.functionName = "dealData";
				tError.errorMessage = "查询所录代码是否存在出错！";
				this.mErrors.addOneError(tError);
				return false;
			}
			logger.debug("LBExRate记录 count:" + iCount);

			if (iCount > 0)
			{
				CError tError = new CError();
				tError.moduleName = "LBExRateBL";
				tError.functionName = "dealData";
				tError.errorMessage = "该记录在历史中已备份，请确认后重新提交！";
				this.mErrors.addOneError(tError);
				return false;
			}
			map.put(tLDExRateDB.getSchema(), "DELETE");
			map.put(mLBExRateSchema, "INSERT");
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		this.mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		if (mOperate.equals("UPDATE||MAIN") || mOperate.equals("INSERT||MAIN"))
		{
			this.mLDExRateSchema.setSchema((LDExRateSchema) cInputData.getObjectByObjectName("LDExRateSchema", 0));
		}
		else if (mOperate.equals("DELETE||MAIN"))
		{
			this.mLBExRateSchema.setSchema((LBExRateSchema) cInputData.getObjectByObjectName("LBExRateSchema", 0));
		}
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean submitquery()
	{
		this.mResult.clear();
		logger.debug("Start LDExRateBLQuery Submit...");
		LDExRateDB tLDExRateDB = new LDExRateDB();
		tLDExRateDB.setSchema(this.mLDExRateSchema);
		this.mLDExRateSet = tLDExRateDB.query();
		this.mResult.add(this.mLDExRateSet);
		logger.debug("End LDExRateBLQuery Submit...");
		// 如果有需要处理的错误，则返回
		if (tLDExRateDB.mErrors.needDealError())
		{
			// @@错误处理
			this.mErrors.copyAllErrors(tLDExRateDB.mErrors);
			CError tError = new CError();
			tError.moduleName = "LDExRateBL";
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
			if (mOperate.equals("UPDATE||MAIN") || mOperate.equals("INSERT||MAIN"))
			{
				mInputData.add(this.mLDExRateSchema);
			}
			else if (mOperate.equals("DELETE||MAIN"))
			{
				mInputData.add(this.mLBExRateSchema);
			}
		}
		catch (Exception ex)
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDExRateBL";
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
