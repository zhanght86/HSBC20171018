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

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDScanMainDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LDScanMainSchema;
import com.sinosoft.lis.schema.LDScanPagesSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class LDScanBL
{
private static Logger logger = Logger.getLogger(LDScanBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private MMap map = new MMap();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private LDScanMainSchema mLDScanMainSchema = new LDScanMainSchema();

	private LDScanPagesSchema mLDScanPagesSchema = new LDScanPagesSchema();

	public LDScanBL()
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
			return false;
		}


		mInputData.add(map);
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, mOperate))
		{
			CError.buildErr(this, "数据提交失败");
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

		if (this.mOperate.equals("am")) {
			if(mLDScanMainSchema!=null){
				LDScanMainDB  tLDScanMainDB = new LDScanMainDB();
				tLDScanMainDB.setSubType(mLDScanMainSchema.getSubType());
				if(tLDScanMainDB.getInfo()){
					CError.buildErr(this, "影像件类型已经存在");
					return false;
				}
				map.put(mLDScanMainSchema, "INSERT");
			}
			
		} else if (this.mOperate.equals("ap")){
			
		} else if (this.mOperate.equals("up")){
			
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData)
	{
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0);
		if (mOperate.equals("am")){
			mLDScanMainSchema.setSchema((LDScanMainSchema) cInputData.getObjectByObjectName("LDScanMainSchema", 0));
		}
		else if (mOperate.equals("ap")) {
			mLDScanPagesSchema.setSchema((LDScanPagesSchema) cInputData.getObjectByObjectName("LDScanPagesSchema", 0));
		}
		return true;
	}

	public VData getResult()
	{
		return this.mResult;
	}
}
