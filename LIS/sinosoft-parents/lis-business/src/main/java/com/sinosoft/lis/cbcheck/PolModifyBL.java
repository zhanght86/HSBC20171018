package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAddressDB;
import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCEdorReasonDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCAddressSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCBnfSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCEdorReasonSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 订正模块 - 保单修改 - 业务逻辑处理
 * </p>
 * <p>
 * Copyright: Copyright (c) 2008
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author tongmeng
 * @version 1.0
 */
public class PolModifyBL {
private static Logger logger = Logger.getLogger(PolModifyBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private Reflections mReflections = new Reflections();
	private LCContSchema mLCContSchema = new LCContSchema();
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private LCAppntSchema mLCAppntSchema = new LCAppntSchema();
	private LCAddressSchema mLCInsuredAddressSchema = new LCAddressSchema();
	private LCAddressSchema mLCAppntAddressSchema = new LCAddressSchema();
	// private LCBankAuthSchema mLCBankAuthSchema = new LCBankAuthSchema();
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCEdorReasonSchema mLCEdorReasonSchema = new LCEdorReasonSchema();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private VData mResult = new VData();

	/* 最终结果存储 */
	private LCContSchema tLCContSchema = new LCContSchema();
	private LPContSchema tLPContSchema = new LPContSchema();
	private LCAppntSchema tLCAppntSchema = new LCAppntSchema();
	private LPContSet tLPContSet = new LPContSet();

	private LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
	// private LCBankAuthSchema tLCBankAuthSchema = new LCBankAuthSchema();
	private LCBnfSet tLCBnfSet = new LCBnfSet();
	private LCPolSet tLCPolSet = new LCPolSet();
	private LCCustomerImpartSet tLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCEdorReasonSchema tLCEdorReasonSchema = new LCEdorReasonSchema();
	private LCAddressSchema tLCInsuredAddressSchema = new LCAddressSchema();
	private LCAddressSchema tLCAppntAddressSchema = new LCAddressSchema();
	private LCAddressSet tLCAddressSet = new LCAddressSet();

	public PolModifyBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务数据校验
		if (!checkData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		logger.debug("before prepareData");

		if (!prepareData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 从输入数据中得到所有对象
		this.mLCContSchema = (LCContSchema) cInputData.getObjectByObjectName(
				"LCContSchema", 0);
		this.mLCInsuredSchema = (LCInsuredSchema) cInputData
				.getObjectByObjectName("LCInsuredSchema", 0);
		this.mLCAppntSchema = (LCAppntSchema) cInputData.getObjectByObjectName(
				"LCAppntSchema", 0);
		this.mLCInsuredAddressSchema = (LCAddressSchema) cInputData
				.getObjectByObjectName("LCAddressSchema", 3);
		this.mLCAppntAddressSchema = (LCAddressSchema) cInputData
				.getObjectByObjectName("LCAddressSchema", 4);
		this.mLCBnfSet = (LCBnfSet) cInputData.getObjectByObjectName(
				"LCBnfSet", 0);
		this.mLCEdorReasonSchema = (LCEdorReasonSchema) cInputData
				.getObjectByObjectName("LCEdorReasonSchema", 0);
		this.mGlobalInput = (GlobalInput) cInputData
		.getObjectByObjectName("GlobalInput", 0);
		mInputData = cInputData;

		return true;
	}

	/**
	 * 校验业务数据
	 * 
	 * @return
	 */
	private boolean checkData() {
		if (mGlobalInput == null) {
			CError.buildErr(this, "请重新登录!");

			return false;
		}

		if ((mLCEdorReasonSchema.getReason() == null)
				|| mLCEdorReasonSchema.getReason().equals("")) {
			CError.buildErr(this, "请录入修改原因!");

			return false;
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		LCContDB mLCContDB = new LCContDB();
		mLCContDB.setSchema(this.mLCContSchema);

		if (!mLCContDB.getInfo()) {
			CError.buildErr(this, "未找到要修改的保单信息!");

			return false;
		}
		
		if (mLCContDB.getInsuredName() == null) {
			mLCContDB.setInsuredName("");
		}
		if (mLCContDB.getAppntName() == null) {
			mLCContDB.setAppntName("");
		}
		if (mLCContDB.getInsuredIDType() == null) {
			mLCContDB.setInsuredIDType("");
		}
		if (mLCContDB.getInsuredIDNo() == null) {
			mLCContDB.setInsuredIDNo("");
		}
		if (mLCContDB.getAppntIDType() == null) {
			mLCContDB.setAppntIDType("");
		}
		if (mLCContDB.getAppntIDNo() == null) {
			mLCContDB.setAppntIDNo("");
		}
		if (mLCContDB.getNewBankCode() == null) {
			mLCContDB.setNewBankCode("");
		}
		if (mLCContDB.getNewAccName() == null) {
			mLCContDB.setNewAccName("");
		}
		if (mLCContDB.getNewBankAccNo() == null) {
			mLCContDB.setNewBankAccNo("");
		}
		if (mLCContDB.getPayLocation() == null) {
			mLCContDB.setPayLocation("");
		}
		if (mLCContDB.getBankCode() == null) {
			mLCContDB.setBankCode("");
		}
		if (mLCContDB.getAccName() == null) {
			mLCContDB.setAccName("");
		}
		if (mLCContDB.getBankAccNo() == null) {
			mLCContDB.setBankAccNo("");
		}
		if (!mLCContDB.getInsuredName().equals(mLCContSchema.getInsuredName())
				|| !mLCContDB.getAppntName().equals(
						mLCContSchema.getAppntName())
				|| !mLCContDB.getInsuredIDType().equals(mLCContSchema.getInsuredIDType())
				|| !mLCContDB.getInsuredIDNo().equals(mLCContSchema.getInsuredIDNo())
				|| !mLCContDB.getAppntIDType().equals(mLCContSchema.getAppntIDType())
				|| !mLCContDB.getAppntIDNo().equals(mLCContSchema.getAppntIDNo())
				|| !mLCContDB.getPayLocation().equals(mLCContSchema.getPayLocation())
				|| !mLCContDB.getBankCode().equals(mLCContSchema.getBankCode())
				|| !mLCContDB.getAccName().equals(mLCContSchema.getAccName())
				|| !mLCContDB.getBankAccNo().equals(
						mLCContSchema.getBankAccNo())
				|| !mLCContDB.getNewBankCode().equals(mLCContSchema.getNewBankCode())
				|| !mLCContDB.getNewAccName().equals(mLCContSchema.getNewAccName())
				|| !mLCContDB.getNewBankAccNo().equals(
								mLCContSchema.getNewBankAccNo())) {
			logger.debug("************begin---------修改合同信息--------begin*************");
			mReflections.transFields(tLCContSchema, mLCContDB.getSchema());

			/*// 如果不是银行自动转账或者是银行转账，账户信息为空
			if (!mLCContSchema.getPayLocation().equals("0")
					&& !mLCContSchema.getPayLocation().equals("8")) {
				mLCContSchema.setAccName("");
				mLCContSchema.setBankCode("");
				mLCContSchema.setBankAccNo("");
			}*/

			tLCContSchema.setInsuredName(mLCContSchema.getInsuredName());
			//logger.debug("mLCContSchema.getInsuredName():"
			//		+ tLCContSchema.getInsuredName());
			tLCContSchema.setAppntName(mLCContSchema.getAppntName());
			tLCContSchema.setInsuredIDType(mLCContSchema.getInsuredIDType());
			tLCContSchema.setInsuredIDNo(mLCContSchema.getInsuredIDNo());
			tLCContSchema.setAppntIDType(mLCContSchema.getAppntIDType());
			tLCContSchema.setAppntIDNo(mLCContSchema.getAppntIDNo());
			tLCContSchema.setNewBankCode(mLCContSchema.getNewBankCode());
			tLCContSchema.setNewAccName(mLCContSchema.getNewAccName());
			tLCContSchema.setNewBankAccNo(mLCContSchema.getNewBankAccNo());
			tLCContSchema.setPayLocation(mLCContSchema.getPayLocation());
			tLCContSchema.setBankCode(mLCContSchema.getBankCode());
			tLCContSchema.setAccName(mLCContSchema.getAccName());
			tLCContSchema.setBankAccNo(mLCContSchema.getBankAccNo());
			tLCContSchema.setModifyDate(CurrentDate);
			tLCContSchema.setModifyTime(CurrentTime);
			tLCContSchema.setOperator(mGlobalInput.Operator);

			/*if ((mLCContDB.getGetPolDate() != null)
					&& !mLCContDB.getGetPolDate().equals(
							mLCContSchema.getGetPolDate())) {
				LZSysCertifyDB tLZSysCertifyDB = new LZSysCertifyDB();
				tLZSysCertifyDB.setCertifyCode("9995"); // 保单回执单证一定要先回收才能修改
				tLZSysCertifyDB.setCertifyNo(mLCContDB.getContNo());
				tLZSysCertifyDB.setStateFlag("1");

				if (tLZSysCertifyDB.query().size() == 0) {
					CError tCError = new CError();
					tCError.buildErr(this, "请先做个人保单回执单证回收,再做保单回执客户签收日期修改!");

					return false;
				}
				LPContDB tLPContDB = new LPContDB();

				String strsql = "select * from lpcont where contno ='"
						+ mLCContDB.getContNo()
						+ "' and exists (select 1 from lpedormain where edorno=lpcont.edorno and edorstate<>'0'))";
				tLPContSet = tLPContDB.executeQuery(strsql);

				if (tLPContSet.size() > 0) {
					for (int i = 1; i <= tLPContSet.size(); i++) {
						tLPContSet.get(i).setGetPolDate(
								mLCContSchema.getGetPolDate());
					}
				}

				mResult.add(tLPContSet);
			}

			tLCContSchema.setGetPolDate(mLCContSchema.getGetPolDate());*/
			mResult.add(tLCContSchema);
		}

		LCInsuredDB mLCInsuredDB = new LCInsuredDB();
		mLCInsuredDB.setSchema(mLCInsuredSchema);

		if (!mLCInsuredDB.getInfo()) {
			CError.buildErr(this, "未找到该保单的被保人信息!");

			return false;
		}

		if (mLCInsuredDB.getName() == null) {
			mLCInsuredDB.setName("");
		}
		
		if (mLCInsuredDB.getIDType() == null) {
			mLCInsuredDB.setIDType("");
		}

		if (mLCInsuredDB.getIDNo() == null) {
			mLCInsuredDB.setIDNo("");
		}

		if (!mLCInsuredDB.getName().equals(mLCInsuredSchema.getName())
				|| !mLCInsuredDB.getIDType().equals(mLCInsuredSchema.getIDType())
				|| !mLCInsuredDB.getIDNo().equals(mLCInsuredSchema.getIDNo())
				) {
			logger.debug("************begin---------修改被保人信息--------begin*************");
			mReflections
					.transFields(tLCInsuredSchema, mLCInsuredDB.getSchema());
			tLCInsuredSchema.setName(mLCInsuredSchema.getName());
			tLCInsuredSchema.setIDType(mLCInsuredSchema.getIDType());
			tLCInsuredSchema.setIDNo(mLCInsuredSchema.getIDNo());
			tLCInsuredSchema.setModifyDate(CurrentDate);
			tLCInsuredSchema.setModifyTime(CurrentTime);
			mResult.add(tLCInsuredSchema);
		}

		// 被保人地址信息修改--稍后处理
		LCAddressDB mLCInsuredAddressDB = new LCAddressDB();
		mLCInsuredAddressDB.setSchema(this.mLCInsuredAddressSchema);
		if (!mLCInsuredAddressDB.getInfo()) {
			CError.buildErr(this, "未找到该保单的被保人地址信息!");

			return false;
		}

		{
			logger.debug("开始修改被保人地址信息");
			mReflections.transFields(this.tLCInsuredAddressSchema,
					mLCInsuredAddressDB.getSchema());
			tLCInsuredAddressSchema
					.setPostalAddress(this.mLCInsuredAddressSchema
							.getPostalAddress());
			tLCInsuredAddressSchema.setZipCode(this.mLCInsuredAddressSchema
					.getZipCode());
			tLCInsuredAddressSchema.setMobile(this.mLCInsuredAddressSchema
					.getMobile());
			tLCInsuredAddressSchema
					.setPhone(this.mLCInsuredAddressSchema
							.getPhone());
			tLCInsuredAddressSchema.setHomeAddress(this.mLCInsuredAddressSchema
					.getHomeAddress());
			tLCInsuredAddressSchema.setHomeZipCode(this.mLCInsuredAddressSchema
					.getHomeZipCode());
			tLCInsuredAddressSchema.setGrpName(this.mLCInsuredAddressSchema
					.getGrpName());
			tLCInsuredAddressSchema.setEMail(this.mLCInsuredAddressSchema
					.getEMail());
			tLCInsuredAddressSchema.setModifyDate(CurrentDate);
			tLCInsuredAddressSchema.setModifyTime(CurrentTime);
			// mResult.add(tLCInsuredAddressSchema);
			this.tLCAddressSet.add(tLCInsuredAddressSchema);
		}

		LCAppntDB mLCAppntDB = new LCAppntDB();
		mLCAppntDB.setSchema(mLCAppntSchema);

		if (!mLCAppntDB.getInfo()) {
			CError.buildErr(this, "未找到该保单的投保人信息!");

			return false;
		}

		if (mLCAppntDB.getAppntName() == null) {
			mLCAppntDB.setAppntName("");
		}

		if (mLCAppntDB.getIDType() == null) {
			mLCAppntDB.setIDType("");
		}
		
		if (mLCAppntDB.getIDNo() == null) {
			mLCAppntDB.setIDNo("");
		}

		if ( !mLCAppntDB.getAppntName().equals(
						mLCAppntSchema.getAppntName())
				|| !mLCAppntDB.getIDType().equals(mLCAppntSchema.getIDType())
				|| !mLCAppntDB.getIDNo().equals(mLCAppntSchema.getIDNo())
				) {
			logger.debug("************begin---------修改投保人信息--------begin*************");
			mReflections.transFields(tLCAppntSchema, mLCAppntDB.getSchema());
			tLCAppntSchema.setAppntName(mLCAppntSchema.getAppntName());
			tLCAppntSchema.setIDType(mLCAppntSchema.getIDType());
			tLCAppntSchema.setIDNo(mLCAppntSchema.getIDNo());
			tLCAppntSchema.setModifyDate(CurrentDate);
			tLCAppntSchema.setModifyTime(CurrentTime);
			mResult.add(tLCAppntSchema);
		}
		// 投保人地址修改 -- 稍后处理
		LCAddressDB mLCAppntAddressDB = new LCAddressDB();
		mLCAppntAddressDB.setSchema(this.mLCAppntAddressSchema);
		if (!mLCAppntAddressDB.getInfo()) {
			CError.buildErr(this, "未找到该保单的被保人地址信息!");

			return false;
		}

		{
			logger.debug("开始修改投保人地址信息");
			mReflections.transFields(this.tLCAppntAddressSchema,
					mLCAppntAddressDB.getSchema());
			tLCAppntAddressSchema.setPostalAddress(this.mLCAppntAddressSchema
					.getPostalAddress());
			tLCAppntAddressSchema.setZipCode(this.mLCAppntAddressSchema
					.getZipCode());
			tLCAppntAddressSchema.setMobile(this.mLCAppntAddressSchema
					.getMobile());
			tLCAppntAddressSchema.setPhone(this.mLCAppntAddressSchema
					.getPhone());
			tLCAppntAddressSchema.setHomeAddress(this.mLCAppntAddressSchema
					.getHomeAddress());
			tLCAppntAddressSchema.setHomeZipCode(this.mLCAppntAddressSchema
					.getHomeZipCode());
			tLCAppntAddressSchema.setGrpName(this.mLCAppntAddressSchema
					.getGrpName());
			tLCAppntAddressSchema.setEMail(this.mLCAppntAddressSchema
					.getEMail());
			tLCAppntAddressSchema.setModifyDate(CurrentDate);
			tLCAppntAddressSchema.setModifyTime(CurrentTime);
			this.tLCAddressSet.add(tLCAppntAddressSchema);
			// mResult.add(tLCAppntAddressSchema);
		}

		mResult.add(tLCAddressSet);
		
		if (!mLCInsuredDB.getName().equals(mLCInsuredSchema.getName())
				|| !mLCAppntDB.getAppntName().equals(mLCAppntSchema.getAppntName())
				) {
			logger.debug("************begin---------修改险种保单信息--------begin*************");
			//修改险种表信息
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(mLCContSchema.getContNo());
			LCPolSet mLCPolSet = tLCPolDB.query();
			logger.debug("mLCPolSet:" + mLCPolSet.size());
			for (int i = 1; i <= mLCPolSet.size(); i++) {
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolSchema.setSchema(mLCPolSet.get(i));
				tLCPolSchema.setInsuredName(mLCInsuredSchema.getName());
				tLCPolSchema.setAppntName(mLCAppntSchema.getAppntName());
				tLCPolSchema.setModifyDate(CurrentDate);
				tLCPolSchema.setModifyTime(CurrentTime);
				tLCPolSet.add(tLCPolSchema);
			}
			mResult.add(tLCPolSet);
		}	
		
		logger.debug("************begin---------修改受益人信息--------begin*************");
		logger.debug("mLCBnfSet:" + mLCBnfSet.size());

		for (int i = 1; i <= mLCBnfSet.size(); i++) {
			LCBnfSchema tLCBnfSchema = new LCBnfSchema();
			double[] sumLiveBnf = { 0.00, 0.00, 0.00, 0.00, 0.00,
					0.00, 0.00 }; // 各级别生存受益比例之和
			double[] sumDeadBnf = { 0.00, 0.00, 0.00, 0.00, 0.00,
					0.00, 0.00 }; // 各级别身故受益比例之和

			/*
			 * LCBnfDB mLCBnfDB = new LCBnfDB();
			 * mLCBnfDB.setSchema(mLCBnfSet.get(i));
			 * 
			 * if (mLCBnfDB.getInfo()) { mReflections.transFields(tLCBnfSchema,
			 * mLCBnfDB.getSchema());
			 * tLCBnfSchema.setName(mLCBnfSet.get(i).getName());
			 * tLCBnfSchema.setIDType(mLCBnfSet.get(i).getIDType());
			 * tLCBnfSchema.setIDNo(mLCBnfSet.get(i).getIDNo());
			 * tLCBnfSchema.setRelationToInsured(mLCBnfSet.get(i)
			 * .getRelationToInsured()); //
			 * tLCBnfSchema.setBnfLot(mLCBnfSet.get(i).getBnfLot());
			 * tLCBnfSchema.setBnfGrade(mLCBnfSet.get(i).getBnfGrade());
			 * tLCBnfSchema.setAddress(mLCBnfSet.get(i).getAddress()); } else {
			 * tLCBnfSchema.setSchema(mLCBnfSet.get(i)); }
			 */
			tLCBnfSchema.setSchema(mLCBnfSet.get(i));
			// 受益人身份证校验
			if ("0".equals(tLCBnfSchema.getIDType())) {
				if (tLCBnfSchema.getIDNo() == null
						|| "".equals(tLCBnfSchema.getIDNo())) {
					CError.buildErr(this,
							"受益人的证件为身份证号，但证件号码为空！");
					return false;
				}

				if (tLCBnfSchema.getIDNo().length() != 15
						&& tLCBnfSchema.getIDNo().length() != 18) {
					CError.buildErr(this, "受益人的身份证号位数错误！");
					return false;
				}
			}

			if ("0".equals(tLCBnfSchema.getBnfType())) {
				sumLiveBnf[Integer.parseInt(tLCBnfSchema
						.getBnfGrade())] += tLCBnfSchema
						.getBnfLot();
			} else {
				sumDeadBnf[Integer.parseInt(tLCBnfSchema
						.getBnfGrade())] += tLCBnfSchema
						.getBnfLot();
			}
	
			for (int m = 0; m < sumLiveBnf.length; m++) {
				if (sumLiveBnf[m] > 1.00) {
					logger.debug("生存受益人受益顺序 " + m
							+ " 的受益比例和为：" + sumLiveBnf[m]);
					CError.buildErr(this, "生存受益人受益顺序 " + m
							+ " 的受益比例和为：" + sumLiveBnf[m]
							+ " 大于100%！");
					return false;
				}
			}
			for (int n = 0; n < sumDeadBnf.length; n++) {
				if (sumDeadBnf[n] > 1.00) {
					logger.debug("死亡受益人受益顺序 " + n
							+ " 的受益比例和为：" + sumDeadBnf[n]);
					CError.buildErr(this, "死亡受益人受益顺序 " + n
							+ " 的受益比例和为：" + sumDeadBnf[n]
							+ " 大于100%！");
					return false;
				}
			}
			
			tLCBnfSchema.setBnfNo(i);
			tLCBnfSchema.setModifyDate(CurrentDate);
			tLCBnfSchema.setModifyTime(CurrentTime);
			
			if(tLCBnfSchema.getOperator()==null || tLCBnfSchema.getOperator().equals(""))
				tLCBnfSchema.setOperator(mGlobalInput.Operator);
			if(tLCBnfSchema.getMakeDate()==null || tLCBnfSchema.getMakeDate().equals(""))
			{
				tLCBnfSchema.setMakeDate(CurrentDate);
				tLCBnfSchema.setMakeTime(CurrentTime);
			}
			
			tLCBnfSet.add(tLCBnfSchema);
		}
		mResult.add(tLCBnfSet);

		//mResult.add(tLCCustomerImpartSet);
		logger.debug("************begin---------记录保单修改原因--------begin*************");
		LCEdorReasonDB mLCEdorReasonDB = new LCEdorReasonDB();
		mLCEdorReasonDB.setEdorNo(mLCContDB.getPrtNo());
		mLCEdorReasonDB.setEdorType("10"); // 印刷号
		mLCEdorReasonDB.setContNo(mLCContDB.getContNo());
		mLCEdorReasonDB.setType("11"); // 保单修改原因

		if (mLCEdorReasonDB.getInfo()) {
			mReflections.transFields(tLCEdorReasonSchema, mLCEdorReasonDB
					.getSchema());
		} else {
			tLCEdorReasonSchema.setEdorNo(mLCContDB.getPrtNo());
			tLCEdorReasonSchema.setEdorType("10");
			tLCEdorReasonSchema.setContNo(mLCContDB.getContNo());
			tLCEdorReasonSchema.setType("11");
			tLCEdorReasonSchema.setMakeDate(CurrentDate);
			tLCEdorReasonSchema.setMakeTime(CurrentTime);
		}

		tLCEdorReasonSchema.setReason(mLCEdorReasonSchema.getReason());
		tLCEdorReasonSchema.setOperator(mGlobalInput.Operator);
		tLCEdorReasonSchema.setModifyDate(CurrentDate);
		tLCEdorReasonSchema.setModifyTime(CurrentTime);
		mResult.add(tLCEdorReasonSchema);

		return true;
	}

	/**
	 * 打印信息表
	 * 
	 * @return
	 */
	private boolean prepareData() {
		mResult.add(mLCContSchema.getContNo());

		PolModifyBLS tPolModifyBLS = new PolModifyBLS();

		if (!tPolModifyBLS.submitData(mResult, "")) {
			this.mErrors.copyAllErrors(tPolModifyBLS.mErrors);

			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}
}
