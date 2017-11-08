package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCHospitalQualityRecordDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LACustomerQualityRecordSchema;
import com.sinosoft.lis.schema.LCHospitalQualityRecordSchema;
import com.sinosoft.lis.vschema.LCHospitalQualityRecordSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
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
 * Copyright: Copyright (c) 2009
 * </p>
 * 
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author tongmeng
 * @version 6.0
 */
public class UWHospitalQualityRecordBL {
private static Logger logger = Logger.getLogger(UWHospitalQualityRecordBL.class);
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
    private LCHospitalQualityRecordSchema mLCHospitalQualityRecordSchema =new LCHospitalQualityRecordSchema();
    private LCHospitalQualityRecordSet mLCHospitalQualityRecordSet=new LCHospitalQualityRecordSet();
	public UWHospitalQualityRecordBL() {
	}

	/**
	 * 数据提交的公共方法
	 * 
	 * @param: cInputData 传入的数据 cOperate 数据操作字符串
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		try {
			mInputData = (VData) cInputData.clone();
			this.mOperate = cOperate;
			logger.debug("now in UWCustomerQualityRecordBL submit");
			// 将外部传入的数据分解到本类的属性中，准备处理
			// 判断是初始化还是处理体检医院信息
			if (!this.mOperate.equals("INIT")) {
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
				logger.debug("Start UWCustomerQualityRecordBL Submit...");
				if (!tPubSubmit.submitData(mInputData, mOperate)) {
					// @@错误处理
					this.mErrors.copyAllErrors(tPubSubmit.mErrors);
					return false;
				}
			}
			else
			{
				//初始化.
				//1-查询体检医院错误信息
				/*
				ExeSQL tExeSQL = new ExeSQL();
				String tSQL_Score = "select code,codename,othersign from ldcode where codetype='hospitalscore' order by othersign/1,code";
				SSRS tSSRS_Score = new SSRS();
				tSSRS_Score = tExeSQL.execSQL(tSQL_Score);
				this.mResult.add(0, tSSRS_Score);
				//查询管理岗信息
				String tSQL_Manage = "select code,codename from ldcode where codetype='hospitalmanagescore' ";
				SSRS tSSRS_Manage = new SSRS();
				tSSRS_Manage = tExeSQL.execSQL(tSQL_Manage);
				this.mResult.add(1,tSSRS_Manage);
				//查询内勤
				String tSQL_Inner = "select code,codename from ldcode where codetype='hospitalinnerscore' ";
				SSRS tSSRS_Inner = new SSRS(); 
				tSSRS_Inner = tExeSQL.execSQL(tSQL_Inner);
				this.mResult.add(2,tSSRS_Inner);
				*/
				//新需求
				ExeSQL tExeSQL =new ExeSQL();
				String tSQL_Quality ="select code,codename,othersign from ldcode where codetype='hospitalquality' order by othersign";
				SQLwithBindVariables sqlbv = new SQLwithBindVariables();
				sqlbv.sql(tSQL_Quality);
				SSRS tSSRS_Quality =new SSRS();
				tSSRS_Quality =tExeSQL.execSQL(sqlbv);
				this.mResult.add(0,tSSRS_Quality);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, e.toString());
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
			// 客户表
			this.mLCHospitalQualityRecordSchema.setSchema((LCHospitalQualityRecordSchema) mInputData
					.getObjectByObjectName("LCHospitalQualityRecordSchema", 0));
			return true;
		} catch (Exception ex) {
			CError.buildErr(this,ex.toString());
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
		if (mOperate.equals("INSERT")) {
			if (this.mLCHospitalQualityRecordSchema.getContNo()==null
					||this.mLCHospitalQualityRecordSchema.getContNo().equals("")
					||this.mLCHospitalQualityRecordSchema.getHospitCode()==null
					||this.mLCHospitalQualityRecordSchema.getHospitCode().equals("")
					) {
				CError.buildErr(this,"传入数据缺失!");
				return false;
			}

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
		if (mOperate.equals("INSERT")) {
			String tNowDate = PubFun.getCurrentDate();
			String tNowTime = PubFun.getCurrentTime();
			//首先获得原始数据
			LCHospitalQualityRecordSchema tLCHospitalQualityRecordSchema = new LCHospitalQualityRecordSchema();
			tLCHospitalQualityRecordSchema.setSchema(this.mLCHospitalQualityRecordSchema);
			LCHospitalQualityRecordDB tLCHospitalQualityRecordDB = new LCHospitalQualityRecordDB();
			tLCHospitalQualityRecordDB.setSchema(this.mLCHospitalQualityRecordSchema);
			if(!tLCHospitalQualityRecordDB.getInfo())
			{
				//没有记录
				tLCHospitalQualityRecordSchema.setMakeDate(tNowDate);
				tLCHospitalQualityRecordSchema.setMakeTime(tNowTime);	
			}
			else
			{
				tLCHospitalQualityRecordSchema.setMakeDate(tLCHospitalQualityRecordDB.getMakeDate());
				tLCHospitalQualityRecordSchema.setMakeTime(tLCHospitalQualityRecordDB.getMakeTime());
			}
		
			tLCHospitalQualityRecordSchema.setOperator(this.mGlobalInput.Operator);
			tLCHospitalQualityRecordSchema.setModifyDate(tNowDate);
			tLCHospitalQualityRecordSchema.setModifyTime(tNowTime);
			
			this.mLCHospitalQualityRecordSet.add(tLCHospitalQualityRecordSchema);
			
		}

		if (mOperate.equals("DELETE")) {
		}

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
		LCHospitalQualityRecordSet tLCHospitalQualityRecordSet = new LCHospitalQualityRecordSet();
		tLCHospitalQualityRecordSet.set(mLCHospitalQualityRecordSet);
		map.put(tLCHospitalQualityRecordSet,"DELETE");
		map.put(this.mLCHospitalQualityRecordSet,"INSERT");
		mInputData.add(map);
		mResult.clear();
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
