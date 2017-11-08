package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDHospitalDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDHospitalBSchema;
import com.sinosoft.lis.schema.LDHospitalSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
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
public class UWHospitalManageBL {
private static Logger logger = Logger.getLogger(UWHospitalManageBL.class);
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
	private LDHospitalSchema mLDHospitalSchema = new LDHospitalSchema();
	private LDHospitalSchema mOutLDHospitalSchema = new LDHospitalSchema();
	private LDHospitalBSchema mOutLDHospitalBSchema = new LDHospitalBSchema();
	public UWHospitalManageBL() {
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
			this.mLDHospitalSchema.setSchema((LDHospitalSchema) mInputData
					.getObjectByObjectName("LDHospitalSchema", 0));
			// 客户表
			return true;
		} catch (Exception ex) {
			CError.buildErr(this,"获取数据出错!");
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
			if (this.mLDHospitalSchema.getHospitCode() == null||this.mLDHospitalSchema.getHospitCode().equals("")) {
				CError.buildErr(this,"请输入体检医院编码!");
				return false;
			}
		return true;
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		try {
			String tNowDate = PubFun.getCurrentDate();
			String tNowTime = PubFun.getCurrentTime();
			LDHospitalDB tLDHospitalDB = new LDHospitalDB();
			//获取原始医院信息
			tLDHospitalDB.setHospitCode(this.mLDHospitalSchema.getHospitCode());
			if(!tLDHospitalDB.getInfo())
			{
				CError.buildErr(this,"不存在体检医院:"+this.mLDHospitalSchema.getHospitCode()+"的信息,请先维护体检医院数据!");
				return false;
			}
			//备份数据
			//生成转储号
			String tSerialNo = PubFun1.CreateMaxNo("SerialNo", 20);
			Reflections tReflections = new Reflections();
			LDHospitalBSchema tLDHospitalBSchema = new LDHospitalBSchema();
			tReflections.transFields(tLDHospitalBSchema, tLDHospitalDB.getSchema());
			//设置其他需要保存的信息
			tLDHospitalBSchema.setSerialNo(tSerialNo);
			tLDHospitalBSchema.setModifyDate2(tNowDate);
			tLDHospitalBSchema.setModifyTime2(tNowTime);
			tLDHospitalBSchema.setOperator2(this.mGlobalInput.Operator);
			
			this.mOutLDHospitalBSchema.setSchema(tLDHospitalBSchema);
			
			//修改
			tLDHospitalDB.setBlackListFlag(this.mLDHospitalSchema.getBlackListFlag());
			tLDHospitalDB.setReasonType(this.mLDHospitalSchema.getReasonType());
			tLDHospitalDB.setRemark(this.mLDHospitalSchema.getRemark());
			tLDHospitalDB.setModifyDate(tNowDate);
			tLDHospitalDB.setModifyTime(tNowTime);
			tLDHospitalDB.setOperator(this.mGlobalInput.Operator);
			this.mOutLDHospitalSchema.setSchema(tLDHospitalDB.getSchema());

			return true;
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this,e.toString());
			return false;
		}
	}

	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		map.put(this.mOutLDHospitalBSchema, "INSERT");
		map.put(this.mOutLDHospitalSchema,"UPDATE");
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
