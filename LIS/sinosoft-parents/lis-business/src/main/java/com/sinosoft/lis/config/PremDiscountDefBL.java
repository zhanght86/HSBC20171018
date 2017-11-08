package com.sinosoft.lis.config;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LMDiscountDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LMDiscountSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author ccvip
 * @version 6.0
 */
public class PremDiscountDefBL {
private static Logger logger = Logger.getLogger(PremDiscountDefBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();

	/** 需要传到关系的数据* */

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();
	private String theCurrentTime = PubFun.getCurrentTime();

	/** 业务处理相关变量 */
	LMDiscountSchema mLMDiscountSchema = new LMDiscountSchema();

	public PremDiscountDefBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in UWQualityManageBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false)
			return false;
		logger.debug("---getInputData---");
		if (this.checkData() == false)
			return false;
		logger.debug("---checkData---");

		// 根据业务逻辑对数据进行处理
		if (this.dealData() == false)
			return false;

		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		logger.debug("Start UWQualityManageBL Submit...");
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "UWQualityManageBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");

		return true;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			
			mLMDiscountSchema.setSchema((LMDiscountSchema) mInputData
					.getObjectByObjectName("LMDiscountSchema", 0));
		
			return true;
		} catch (Exception ex) {
			CError.buildErr(this, ex.toString());
			return false;
		}

	}

	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		String tNowDate = PubFun.getCurrentDate();
		String tNowTime = PubFun.getCurrentTime();
		
		String tMakeDate = "";
		String tMakeTime = "";
		//判断是否有折扣号码,如果有折扣号码,需要先把以前的数据查出来
		
		String tDiscountCode = mLMDiscountSchema.getDiscountCode();
		if(tDiscountCode==null||tDiscountCode.equals(""))
		{
			tDiscountCode = PubFun1.CreateMaxNo("DiscountCode", 20);
		}
		
		LMDiscountDB tOldLMDiscountDB = new LMDiscountDB();
		tOldLMDiscountDB.setDiscountCode(tDiscountCode);
		if(tOldLMDiscountDB.getInfo())
		{
			tMakeDate = tOldLMDiscountDB.getMakeDate();
			tMakeTime = tOldLMDiscountDB.getMakeTime();
		}
		else
		{
			tMakeDate = tNowDate;
			tMakeTime = tNowTime;
		}
		
		
		this.mResult.add(0,tDiscountCode);
		mLMDiscountSchema.setDiscountCode(tDiscountCode);
		mLMDiscountSchema.setOperator(this.mGlobalInput.Operator);
		mLMDiscountSchema.setMakeDate(tMakeDate);
		mLMDiscountSchema.setMakeTime(tMakeTime);
		mLMDiscountSchema.setModifyDate(tNowDate);
		mLMDiscountSchema.setModifyTime(tNowTime);
		//特殊处理
		if(mLMDiscountSchema.getRiskCode()==null||mLMDiscountSchema.getRiskCode().equals(""))
		{
			mLMDiscountSchema.setRiskCode("000000");
		}
		
		if(mLMDiscountSchema.getDutyCode()==null||mLMDiscountSchema.getDutyCode().equals(""))
		{
			mLMDiscountSchema.setDutyCode("000000");
		}
		
		if(mLMDiscountSchema.getAddFeeDiscFlag()==null||mLMDiscountSchema.getAddFeeDiscFlag().equals(""))
		{
			mLMDiscountSchema.setAddFeeDiscFlag("N");
		}
		
		//mLMDiscountSchema.set
		map.put(mLMDiscountSchema, "DELETE&INSERT");
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
	}

	/**
	 * 得到处理后的结果集
	 * 
	 * @return 结果集
	 */

	public VData getResult() {
		return mResult;
	}

}
