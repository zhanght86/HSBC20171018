package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCAppntDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCInsureAccClassDB;
import com.sinosoft.lis.db.LCInsureAccClassFeeDB;
import com.sinosoft.lis.db.LCInsureAccDB;
import com.sinosoft.lis.db.LCInsureAccFeeDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LPAppntDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPInsureAccClassDB;
import com.sinosoft.lis.db.LPInsureAccClassFeeDB;
import com.sinosoft.lis.db.LPInsureAccDB;
import com.sinosoft.lis.db.LPInsureAccFeeDB;
import com.sinosoft.lis.db.LPInsuredDB;
import com.sinosoft.lis.db.LPMoveDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.reagent.ReagentBqMove;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccClassSchema;
import com.sinosoft.lis.schema.LCInsureAccFeeSchema;
import com.sinosoft.lis.schema.LCInsureAccSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.schema.LPAppntSchema;
import com.sinosoft.lis.schema.LPContSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPInsureAccClassFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccClassSchema;
import com.sinosoft.lis.schema.LPInsureAccFeeSchema;
import com.sinosoft.lis.schema.LPInsureAccSchema;
import com.sinosoft.lis.schema.LPInsuredSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.schema.LPPremSchema;
import com.sinosoft.lis.vschema.LCAddressSet;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccClassSet;
import com.sinosoft.lis.vschema.LCInsureAccFeeSet;
import com.sinosoft.lis.vschema.LCInsureAccSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPAddressSet;
import com.sinosoft.lis.vschema.LPAppntSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPInsureAccClassFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccClassSet;
import com.sinosoft.lis.vschema.LPInsureAccFeeSet;
import com.sinosoft.lis.vschema.LPInsureAccSet;
import com.sinosoft.lis.vschema.LPInsuredSet;
import com.sinosoft.lis.vschema.LPMoveSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 被保人重要资料变更保全确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author lanjun
 * @version 1.0
 */
public class PEdorPRConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorPRConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	public PEdorPRConfirmBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据操作
		if (!dealData()) {
			return false;
		}

		// 输出数据准备
		if (!prepareOutputData()) {
			return false;
		}

		this.setOperate("CONFIRM||PR");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据查询业务处理(queryData())
	 * 
	 */

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));
		if (mLPEdorItemSchema == null || mGlobalInput == null) {
			mErrors.addOneError(new CError("传入数据不完全！"));
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setInsuredNo(mLPEdorItemSchema.getInsuredNo());
		tLPEdorItemDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() != 1) {
			mErrors.addOneError(new CError("查询批改项目信息失败！"));
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}


	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		LCContSet aLCContSet = new LCContSet();
		LCPolSet aLCPolSet = new LCPolSet();
		LCPremSet aLCPremSet = new LCPremSet();
		LCGetSet aLCGetSet = new LCGetSet();
		LCInsuredSet aLCInsuredSet = new LCInsuredSet();
		LCAppntSet aLCAppntSet = new LCAppntSet();
		LCInsureAccFeeSet aLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LCInsureAccClassFeeSet aLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LCInsureAccSet aLCInsureAccSet = new LCInsureAccSet();
		LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
		LCAddressSet aLCAddressSet = new LCAddressSet();

		LPContSet aLPContSet = new LPContSet();
		LPPolSet aLPPolSet = new LPPolSet();
		LPPremSet aLPPremSet = new LPPremSet();
		LPGetSet aLPGetSet = new LPGetSet();
		LPInsuredSet aLPInsuredSet = new LPInsuredSet();
		LPAppntSet aLPAppntSet = new LPAppntSet();
		LPInsureAccFeeSet aLPInsureAccFeeSet = new LPInsureAccFeeSet();
		LPInsureAccClassFeeSet aLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LPInsureAccSet aLPInsureAccSet = new LPInsureAccSet();
		LPInsureAccClassSet aLPInsureAccClassSet = new LPInsureAccClassSet();
		LPAddressSet aLPAddressSet = new LPAddressSet();

		Reflections tRef = new Reflections();

		// 更换保单表
		LPContSchema tLPContSchema = new LPContSchema();
		LCContSchema tLCContSchema = new LCContSchema();
		LPContSchema aLPContSchema = new LPContSchema();
		LCContSchema aLCContSchema = new LCContSchema();
		LCContSet tLCContSet = new LCContSet();
		LPContSet tLPContSet = new LPContSet();

		tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPContSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPContDB tLPContDB = new LPContDB();
		tLPContDB.setSchema(tLPContSchema);
		tLPContSet = tLPContDB.query();
		for (int j = 1; j <= tLPContSet.size(); j++) {
			aLPContSchema = tLPContSet.get(j);
			tLCContSchema = new LCContSchema();
			tLPContSchema = new LPContSchema();

			LCContDB aLCContDB = new LCContDB();
			aLCContDB.setContNo(aLPContSchema.getContNo());
			aLCContDB.setInsuredNo(aLPContSchema.getInsuredNo());
			LCContSet nLCContSet = aLCContDB.query();
			if (aLCContDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(aLCContDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			if (nLCContSet == null || nLCContSet.size() < 1) {
				mErrors.copyAllErrors(aLCContDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCContSchema = nLCContSet.get(1);
			tRef.transFields(tLPContSchema, aLCContSchema);
			tLPContSchema.setEdorNo(aLPContSchema.getEdorNo());
			tLPContSchema.setEdorType(aLPContSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCContSchema, aLPContSchema);
			tLCContSchema.setModifyDate(PubFun.getCurrentDate());
			tLCContSchema.setModifyTime(PubFun.getCurrentTime());

			aLPContSet.add(tLPContSchema);
			tLCContSet.add(tLCContSchema);
			aLCContSet.add(tLCContSchema);
		}

		// 更换险种管理机构
		LPPolSchema tLPPolSchema = new LPPolSchema();
		LCPolSchema tLCPolSchema = new LCPolSchema();
		LPPolSchema aLPPolSchema = new LPPolSchema();
		LCPolSchema aLCPolSchema = new LCPolSchema();
		LCPolSet tLCPolSet = new LCPolSet();
		LPPolSet tLPPolSet = new LPPolSet();

		tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		// tLPPolSchema.setPolNo(mLPEdorItemSchema.getPolNo());

		LPPolDB tLPPolDB = new LPPolDB();
		tLPPolDB.setSchema(tLPPolSchema);
		tLPPolSet = tLPPolDB.query();
		//主附险标志
		boolean tMainRiskFlag = false;
		for (int j = 1; j <= tLPPolSet.size(); j++) {
			aLPPolSchema = tLPPolSet.get(j);
			tLCPolSchema = new LCPolSchema();
			tLPPolSchema = new LPPolSchema();

			LCPolDB aLCPolDB = new LCPolDB();
			aLCPolDB.setPolNo(aLPPolSchema.getPolNo());
			if (!aLCPolDB.getInfo()) {
				mErrors.copyAllErrors(aLCPolDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCPolSchema = aLCPolDB.getSchema();
			if (aLCPolSchema.getPolNo().equals(aLCPolSchema.getMainPolNo())) {
				//只有是主险时才做孤儿单归属
				tMainRiskFlag = true;
			}
			tRef.transFields(tLPPolSchema, aLCPolSchema);
			tLPPolSchema.setEdorNo(aLPPolSchema.getEdorNo());
			tLPPolSchema.setEdorType(aLPPolSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCPolSchema, aLPPolSchema);
			tLCPolSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPolSchema.setModifyTime(PubFun.getCurrentTime());

			aLPPolSet.add(tLPPolSchema);
			tLCPolSet.add(tLCPolSchema);
			aLCPolSet.add(tLCPolSchema);
		}

		// 更新相关通知书的打印管理表机构
		String tManageCom = tLPPolSet.get(1).getManageCom();
		logger.debug("The managecom to migrate is " + tManageCom);
		String uptPrintSql = "update loprtmanager set managecom = '?tManageCom?' where otherno in (?otherno?";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		ArrayList<String> strArr=new ArrayList<String>();
		strArr.add(mLPEdorItemSchema.getContNo());
		for (int j = 1; j <= tLPPolSet.size(); j++) {
//		bangdingqian	
//			uptPrintSql += ",'" + tLPPolSet.get(j).getPolNo() + "'";
			strArr.add(tLPPolSet.get(j).getPolNo());
		}
		uptPrintSql += ")";
		sqlbv.sql(uptPrintSql);
		sqlbv.put("tManageCom", tManageCom);
		sqlbv.put("otherno", strArr);
		logger.debug("uptPrintSql: " + uptPrintSql);

		// 更新应收和应付的管理机构
		// 更新应收总表
		String upsPaySql = "update ljspay set managecom = '?tManageCom?' where otherno in (?otherno?";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		ArrayList<String> strArr1=new ArrayList<String>();
		strArr1.add(mLPEdorItemSchema.getContNo());
		for (int j = 1; j <= tLPPolSet.size(); j++) {
			strArr1.add(tLPPolSet.get(j).getPolNo());
//	bangdingqian		upsPaySql += ",'" + tLPPolSet.get(j).getPolNo() + "'";
		}
		upsPaySql += ")";
		sqlbv1.sql(upsPaySql);
		sqlbv1.put("tManageCom", tManageCom);
		sqlbv1.put("otherno", strArr1);
		logger.debug("upsPaySql: " + upsPaySql);
		// 更新应收子表
		String upsPayPersonSql = "update ljspayperson set managecom = '?tManageCom?' where contno = '?contno?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(upsPayPersonSql);
		sbv1.put("tManageCom", tManageCom);
		sbv1.put("contno", mLPEdorItemSchema.getContNo());
		logger.debug("upsPayPersonSql: " + upsPayPersonSql);
		// 更新渠道应收表
		String upasPayPersonSql = "update laspayperson set managecom = '?tManageCom?' where contno = '?contno?' and actupayflag = '0'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(upasPayPersonSql);
		sbv2.put("tManageCom", tManageCom);
		sbv2.put("contno", mLPEdorItemSchema.getContNo());
		logger.debug("upasPayPersonSql: " + upasPayPersonSql);
		// 更新应付总表
		String upsGetSql = "update ljsget set managecom = '?tManageCom?' where otherno in (?otherno?";
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
        ArrayList<String> strArr2=new ArrayList<String>();
        strArr2.add(mLPEdorItemSchema.getContNo());
		for (int j = 1; j <= tLPPolSet.size(); j++) {
			 strArr2.add(tLPPolSet.get(j).getPolNo());
//	bangdingqian		upsGetSql += ",'" + tLPPolSet.get(j).getPolNo() + "'";
		}
		upsGetSql += ")";
		sbv3.sql(upsGetSql);
		sbv3.put("tManageCom", tManageCom);
		sbv3.put("otherno", strArr2);
		logger.debug("upsGetSql: " + upsGetSql);
		// 更新应付子表
		String upsGetDrewSql = "update ljsgetdraw set managecom = '?tManageCom?' where contno = '?contno?'";
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(upsGetDrewSql);
		sbv4.put("tManageCom", tManageCom);
		sbv4.put("contno", mLPEdorItemSchema.getContNo());
		logger.debug("upsGetDrewSql: " + upsGetDrewSql);
		mMap.put(sqlbv1, "UPDATE");
		mMap.put(sbv1, "UPDATE");
		mMap.put(sbv2, "UPDATE");
		mMap.put(sbv3, "UPDATE");
		mMap.put(sbv4, "UPDATE");

		// 得到当前保全需要更新的被保人保全数据。(取得主附险变动信息)
		LCInsuredSchema aLCInsuredSchema = new LCInsuredSchema();
		LPInsuredSchema aLPInsuredSchema = new LPInsuredSchema();
		LCInsuredSet tLCInsuredSet = new LCInsuredSet();
		LPInsuredSet tLPInsuredSet = new LPInsuredSet();

		LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
		tLPInsuredSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsuredSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsuredDB tLPInsuredDB = new LPInsuredDB();
		tLPInsuredDB.setSchema(tLPInsuredSchema);
		tLPInsuredSet = tLPInsuredDB.query();
		for (int j = 1; j <= tLPInsuredSet.size(); j++) {
			aLPInsuredSchema = tLPInsuredSet.get(j);
			LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
			tLPInsuredSchema = new LPInsuredSchema();

			LCInsuredDB aLCInsuredDB = new LCInsuredDB();
			aLCInsuredDB.setContNo(aLPInsuredSchema.getContNo());
			aLCInsuredDB.setInsuredNo(aLPInsuredSchema.getInsuredNo());
			if (!aLCInsuredDB.getInfo()) {
				mErrors.copyAllErrors(aLCInsuredDB.mErrors);
				mErrors.addOneError(new CError("查询被保人信息失败！"));
				return false;
			}
			aLCInsuredSchema = aLCInsuredDB.getSchema();
			tRef.transFields(tLPInsuredSchema, aLCInsuredSchema);
			tLPInsuredSchema.setEdorNo(aLPInsuredSchema.getEdorNo());
			tLPInsuredSchema.setEdorType(aLPInsuredSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCInsuredSchema, aLPInsuredSchema);
			tLCInsuredSchema.setModifyDate(PubFun.getCurrentDate());
			tLCInsuredSchema.setModifyTime(PubFun.getCurrentTime());

			aLPInsuredSet.add(tLPInsuredSchema);
			tLCInsuredSet.add(tLCInsuredSchema);
			aLCInsuredSet.add(tLCInsuredSchema);
		}

		// 互换投保人表LCAppnt和LPAppnt，Add By QianLy------------------
		LCAppntSchema aLCAppntSchema = new LCAppntSchema();
		LPAppntSchema aLPAppntSchema = new LPAppntSchema();
		LCAppntSet tLCAppntSet = new LCAppntSet();
		LPAppntSet tLPAppntSet = new LPAppntSet();

		LPAppntSchema tLPAppntSchema = new LPAppntSchema();
		tLPAppntSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPAppntSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPAppntSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPAppntDB tLPAppntDB = new LPAppntDB();
		tLPAppntDB.setSchema(tLPAppntSchema);
		tLPAppntSet = tLPAppntDB.query();
		for (int j = 1; j <= tLPAppntSet.size(); j++) {
			aLPAppntSchema = tLPAppntSet.get(j);
			LCAppntSchema tLCAppntSchema = new LCAppntSchema();
			tLPAppntSchema = new LPAppntSchema();

			LCAppntDB aLCAppntDB = new LCAppntDB();
			aLCAppntDB.setContNo(aLPAppntSchema.getContNo());
			aLCAppntDB.setAppntNo(aLPAppntSchema.getAppntNo());
			if (!aLCAppntDB.getInfo()) {
				mErrors.copyAllErrors(aLCAppntDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCAppntSchema = aLCAppntDB.getSchema();
			tRef.transFields(tLPAppntSchema, aLCAppntSchema);
			tLPAppntSchema.setEdorNo(aLPAppntSchema.getEdorNo());
			tLPAppntSchema.setEdorType(aLPAppntSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCAppntSchema, aLPAppntSchema);
			tLCAppntSchema.setModifyDate(PubFun.getCurrentDate());
			tLCAppntSchema.setModifyTime(PubFun.getCurrentTime());

			aLPAppntSet.add(tLPAppntSchema);
			tLCAppntSet.add(tLCAppntSchema);
			aLCAppntSet.add(tLCAppntSchema);
		}
		// -----------------------

		LCGetSchema aLCGetSchema = new LCGetSchema();
		LPGetSchema aLPGetSchema = new LPGetSchema();
		LCGetSet tLCGetSet = new LCGetSet();
		LPGetSet tLPGetSet = new LPGetSet();

		LPGetSchema tLPGetSchema = new LPGetSchema();
		tLPGetSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPGetSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPGetSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPGetDB tLPGetDB = new LPGetDB();
		tLPGetDB.setSchema(tLPGetSchema);
		tLPGetSet = tLPGetDB.query();
		for (int j = 1; j <= tLPGetSet.size(); j++) {
			aLPGetSchema = tLPGetSet.get(j);
			LCGetSchema tLCGetSchema = new LCGetSchema();
			tLPGetSchema = new LPGetSchema();

			LCGetDB aLCGetDB = new LCGetDB();
			aLCGetDB.setPolNo(aLPGetSchema.getPolNo());
			aLCGetDB.setDutyCode(aLPGetSchema.getDutyCode());
			aLCGetDB.setGetDutyCode(aLPGetSchema.getGetDutyCode());
			if (!aLCGetDB.getInfo()) {
				mErrors.copyAllErrors(aLCGetDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCGetSchema = aLCGetDB.getSchema();
			tRef.transFields(tLPGetSchema, aLCGetSchema);
			tLPGetSchema.setEdorNo(aLPGetSchema.getEdorNo());
			tLPGetSchema.setEdorType(aLPGetSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCGetSchema, aLPGetSchema);
			tLCGetSchema.setModifyDate(PubFun.getCurrentDate());
			tLCGetSchema.setModifyTime(PubFun.getCurrentTime());

			aLPGetSet.add(tLPGetSchema);
			tLCGetSet.add(tLCGetSchema);
			aLCGetSet.add(tLCGetSchema);
		}

		// 得到当前保单的投保人资料
		LCPremSchema aLCPremSchema = new LCPremSchema();
		LPPremSchema aLPPremSchema = new LPPremSchema();
		LCPremSet tLCPremSet = new LCPremSet();
		LPPremSet tLPPremSet = new LPPremSet();

		LPPremSchema tLPPremSchema = new LPPremSchema();
		tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPPremSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPPremDB tLPPremDB = new LPPremDB();
		tLPPremDB.setSchema(tLPPremSchema);
		tLPPremSet = tLPPremDB.query();
		for (int j = 1; j <= tLPPremSet.size(); j++) {
			aLPPremSchema = tLPPremSet.get(j);
			LCPremSchema tLCPremSchema = new LCPremSchema();
			tLPPremSchema = new LPPremSchema();

			LCPremDB aLCPremDB = new LCPremDB();
			aLCPremDB.setPolNo(aLPPremSchema.getPolNo());
			aLCPremDB.setDutyCode(aLPPremSchema.getDutyCode());
			aLCPremDB.setPayPlanCode(aLPPremSchema.getPayPlanCode());
			if (!aLCPremDB.getInfo()) {
				mErrors.copyAllErrors(aLCPremDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCPremSchema = aLCPremDB.getSchema();
			tRef.transFields(tLPPremSchema, aLCPremSchema);
			tLPPremSchema.setEdorNo(aLPPremSchema.getEdorNo());
			tLPPremSchema.setEdorType(aLPPremSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCPremSchema, aLPPremSchema);
			tLCPremSchema.setModifyDate(PubFun.getCurrentDate());
			tLCPremSchema.setModifyTime(PubFun.getCurrentTime());

			aLPPremSet.add(tLPPremSchema);
			tLCPremSet.add(tLCPremSchema);
			aLCPremSet.add(tLCPremSchema);
		}

		// 得到当前保单的投保人资料
		LCInsureAccFeeSchema aLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
		LPInsureAccFeeSchema aLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();

		LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
		tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccFeeSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
		tLPInsureAccFeeDB.setSchema(tLPInsureAccFeeSchema);
		tLPInsureAccFeeSet = tLPInsureAccFeeDB.query();
		for (int j = 1; j <= tLPInsureAccFeeSet.size(); j++) {
			aLPInsureAccFeeSchema = tLPInsureAccFeeSet.get(j);
			LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
			tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();

			LCInsureAccFeeDB aLCInsureAccFeeDB = new LCInsureAccFeeDB();
			aLCInsureAccFeeDB.setContNo(aLPInsureAccFeeSchema.getContNo());
			aLCInsureAccFeeDB.setAppntNo(aLPInsureAccFeeSchema.getAppntNo());
			LCInsureAccFeeSet nLCInsureAccFeeSet = aLCInsureAccFeeDB.query();
			if (aLCInsureAccFeeDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(aLCInsureAccFeeDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			if (nLCInsureAccFeeSet == null || nLCInsureAccFeeSet.size() < 1) {
				mErrors.copyAllErrors(aLCInsureAccFeeDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCInsureAccFeeSchema = nLCInsureAccFeeSet.get(1);
			tRef.transFields(tLPInsureAccFeeSchema, aLCInsureAccFeeSchema);
			tLPInsureAccFeeSchema.setEdorNo(aLPInsureAccFeeSchema.getEdorNo());
			tLPInsureAccFeeSchema.setEdorType(aLPInsureAccFeeSchema
					.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCInsureAccFeeSchema, aLPInsureAccFeeSchema);
			tLCInsureAccFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLCInsureAccFeeSchema.setModifyTime(PubFun.getCurrentTime());

			aLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
			tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
			aLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
		}

		// 得到当前保单的投保人资料
		LCInsureAccSchema aLCInsureAccSchema = new LCInsureAccSchema();
		LPInsureAccSchema aLPInsureAccSchema = new LPInsureAccSchema();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();

		LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
		tLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		tLPInsureAccDB.setSchema(tLPInsureAccSchema);
		tLPInsureAccSet = tLPInsureAccDB.query();
		for (int j = 1; j <= tLPInsureAccSet.size(); j++) {
			aLPInsureAccSchema = tLPInsureAccSet.get(j);
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
			tLPInsureAccSchema = new LPInsureAccSchema();

			LCInsureAccDB aLCInsureAccDB = new LCInsureAccDB();
			aLCInsureAccDB.setContNo(aLPInsureAccSchema.getContNo());
			aLCInsureAccDB.setAppntNo(aLPInsureAccSchema.getAppntNo());
			LCInsureAccSet ttLCInsureAccSet = aLCInsureAccDB.query();
			if (aLCInsureAccDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(aLCInsureAccDB.mErrors);
				mErrors.addOneError(new CError("查询保单帐户信息失败！"));
				return false;
			}
			if (ttLCInsureAccSet == null || ttLCInsureAccSet.size() < 1) {
				mErrors.copyAllErrors(aLCInsureAccDB.mErrors);
				mErrors.addOneError(new CError("未查到保单帐户信息！"));
				return false;
			}
			aLCInsureAccSchema = ttLCInsureAccSet.get(1);
			tRef.transFields(tLPInsureAccSchema, aLCInsureAccSchema);
			tLPInsureAccSchema.setEdorNo(aLPInsureAccSchema.getEdorNo());
			tLPInsureAccSchema.setEdorType(aLPInsureAccSchema.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCInsureAccSchema, aLPInsureAccSchema);
			tLCInsureAccSchema.setModifyDate(PubFun.getCurrentDate());
			tLCInsureAccSchema.setModifyTime(PubFun.getCurrentTime());

			aLPInsureAccSet.add(tLPInsureAccSchema);
			tLCInsureAccSet.add(tLCInsureAccSchema);
			aLCInsureAccSet.add(tLCInsureAccSchema);
		}

		// 得到当前保单的投保人资料
		LCInsureAccClassFeeSchema aLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
		LPInsureAccClassFeeSchema aLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
		LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();

		LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
		tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccClassFeeSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
		tLPInsureAccClassFeeDB.setSchema(tLPInsureAccClassFeeSchema);
		tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB.query();
		for (int j = 1; j <= tLPInsureAccClassFeeSet.size(); j++) {
			aLPInsureAccClassFeeSchema = tLPInsureAccClassFeeSet.get(j);
			LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
			tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();

			LCInsureAccClassFeeDB aLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
			aLCInsureAccClassFeeDB.setContNo(aLPInsureAccClassFeeSchema
					.getContNo());
			aLCInsureAccClassFeeDB.setAppntNo(aLPInsureAccClassFeeSchema
					.getAppntNo());
			LCInsureAccClassFeeSet nLCInsureAccClassFeeSet = aLCInsureAccClassFeeDB
					.query();
			if (aLCInsureAccClassFeeDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(aLCInsureAccClassFeeDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			if (nLCInsureAccClassFeeSet == null
					|| nLCInsureAccClassFeeSet.size() < 1) {
				mErrors.copyAllErrors(aLCInsureAccClassFeeDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCInsureAccClassFeeSchema = nLCInsureAccClassFeeSet.get(1);
			tRef.transFields(tLPInsureAccClassFeeSchema,
					aLCInsureAccClassFeeSchema);
			tLPInsureAccClassFeeSchema.setEdorNo(aLPInsureAccClassFeeSchema
					.getEdorNo());
			tLPInsureAccClassFeeSchema.setEdorType(aLPInsureAccClassFeeSchema
					.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCInsureAccClassFeeSchema,
					aLPInsureAccClassFeeSchema);
			tLCInsureAccClassFeeSchema.setModifyDate(PubFun.getCurrentDate());
			tLCInsureAccClassFeeSchema.setModifyTime(PubFun.getCurrentTime());

			aLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
			tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			aLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
		}

		// 得到当前保单的投保人资料
		LCInsureAccClassSchema aLCInsureAccClassSchema = new LCInsureAccClassSchema();
		LPInsureAccClassSchema aLPInsureAccClassSchema = new LPInsureAccClassSchema();
		LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
		LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();

		LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
		tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPInsureAccClassSchema.setContNo(mLPEdorItemSchema.getContNo());

		LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
		tLPInsureAccClassDB.setSchema(tLPInsureAccClassSchema);
		tLPInsureAccClassSet = tLPInsureAccClassDB.query();
		for (int j = 1; j <= tLPInsureAccClassSet.size(); j++) {
			aLPInsureAccClassSchema = tLPInsureAccClassSet.get(j);
			LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
			tLPInsureAccClassSchema = new LPInsureAccClassSchema();

			LCInsureAccClassDB aLCInsureAccClassDB = new LCInsureAccClassDB();
			aLCInsureAccClassDB.setContNo(aLPInsureAccClassSchema.getContNo());
			aLCInsureAccClassDB
					.setAppntNo(aLPInsureAccClassSchema.getAppntNo());
			LCInsureAccClassSet ttLCInsureAccClassSet = aLCInsureAccClassDB
					.query();
			if (aLCInsureAccClassDB.mErrors.needDealError()) {
				mErrors.copyAllErrors(aLCInsureAccClassDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			if (ttLCInsureAccClassSet == null
					|| ttLCInsureAccClassSet.size() < 1) {
				mErrors.copyAllErrors(aLCInsureAccClassDB.mErrors);
				mErrors.addOneError(new CError("查询投保人信息失败！"));
				return false;
			}
			aLCInsureAccClassSchema = ttLCInsureAccClassSet.get(1);
			tRef.transFields(tLPInsureAccClassSchema, aLCInsureAccClassSchema);
			tLPInsureAccClassSchema.setEdorNo(aLPInsureAccClassSchema
					.getEdorNo());
			tLPInsureAccClassSchema.setEdorType(aLPInsureAccClassSchema
					.getEdorType());

			// 转换成保单个人信息。
			tRef.transFields(tLCInsureAccClassSchema, aLPInsureAccClassSchema);
			tLCInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
			tLCInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());

			aLPInsureAccClassSet.add(tLPInsureAccClassSchema);
			tLCInsureAccClassSet.add(tLCInsureAccClassSchema);
			aLCInsureAccClassSet.add(tLCInsureAccClassSchema);
		}

		// 调用孤儿单程序 add by zhangtao 2005-08-24
		//comment by jiaqiangli 2008-10-07 MS本地化 孤儿单归属程序
//		LAOrphanSlipCollection tLAOrphanSlipCollection = new LAOrphanSlipCollection();
//		tLAOrphanSlipCollection.setContNo(mLPEdorItemSchema.getContNo());
//		tLAOrphanSlipCollection.setManageCom(tLPContSet.get(1).getManageCom()); // 新的管理机构
//		tLAOrphanSlipCollection.setFlag(1);
//		tLAOrphanSlipCollection.setBranchType("1");
//		tLAOrphanSlipCollection.setBranchType2("01");
//		tLAOrphanSlipCollection.setOperator(mLPEdorItemSchema.getOperator());
//
//		if (!tLAOrphanSlipCollection.prepareOrphanPolicy()) {
//			mErrors.copyAllErrors(tLAOrphanSlipCollection.mErrors);
//			return false;
//		} else {
//			LAOrphanPolicySet rLAOrphanPolicySet = tLAOrphanSlipCollection
//					.getResult();
//			if (rLAOrphanPolicySet != null) {
//				mMap.put(rLAOrphanPolicySet, "DELETE&INSERT");
//			}
//		}
		
		
		//ReagentBqMove 先迁至lis65
		logger.debug("tMainRiskFlag"+tMainRiskFlag);
		if (tMainRiskFlag == true) {
			//这个程序的父接口还有待修改
			ReagentBqMove tReagentBqMove = new ReagentBqMove(null,
					mLPEdorItemSchema.getContNo(),
                    aLCContSet.get(1).getManageCom(),
                    aLPContSet.get(1).getManageCom());

            if (!tReagentBqMove.submitData())
            {
                CError.buildErr(this, "归属失败!");
                return false;
            }
            /*
			 * //LRAscriptionSet delete&insert public LRAscriptionSet
			 * getLRAscriptionSet() { return this.mLRAscriptionSet; }
			 * //LRAscriptionBSet insert public LRAscriptionBSet
			 * getLRAscriptionBSet() { return this.mLRAscriptionBSet; }
			 * 
			 * //LRAdimAscriptionSet delete&insert public LRAdimAscriptionSet
			 * getLRAdimAscriptionSet() { return this.mLRAdimAscriptionSet; }
			 * //LRAdimAscriptionBSet insert public LRAdimAscriptionBSet
			 * getLRAdimAscriptionBSet() { return this.mLRAdimAscriptionBSet; }
			 */
            mMap.put(tReagentBqMove.getLRAscriptionSet(),"DELETE&INSERT");
            mMap.put(tReagentBqMove.getLRAscriptionBSet(),"INSERT");
            mMap.put(tReagentBqMove.getLRAdimAscriptionSet(),"DELETE&INSERT");
            mMap.put(tReagentBqMove.getLRAdimAscriptionBSet(),"INSERT");
            //modiyfy lccont lcpol
			LAAgentSchema tLAAgentSchema = tReagentBqMove.getLAAgentSchema();
			for (int i=1;i<=aLCContSet.size();i++) {
				aLCContSet.get(i).setAgentCode(tLAAgentSchema.getAgentCode());
				aLCContSet.get(i).setAgentGroup(tLAAgentSchema.getAgentGroup());
			}
			for (int i=1;i<=aLCPolSet.size();i++) {
				aLCPolSet.get(i).setAgentCode(tLAAgentSchema.getAgentCode());
				aLCPolSet.get(i).setAgentGroup(tLAAgentSchema.getAgentGroup());
			}
			//更新扫描件信息
            String es_doc_mainsql = "update es_doc_main set managecom = '?managecom?' " + "where doccode = '?doccode?' ";
            SQLwithBindVariables sbv5=new SQLwithBindVariables();
            sbv5.sql(es_doc_mainsql);
            sbv5.put("managecom", aLCContSet.get(1).getManageCom());
            sbv5.put("doccode", aLCContSet.get(1).getPrtNo().trim());
            mMap.put(sbv5, "UPDATE");
		}
		
		
		// add by jiaqiangli 2008-10-08 update lpmove.OutDate InDate
		LPMoveDB tLPMoveDB = new LPMoveDB();
		LPMoveSet tUpdateLPMoveSet = new LPMoveSet();
	    tLPMoveDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
	    tLPMoveDB.setEdorType(mLPEdorItemSchema.getEdorType());
	    tUpdateLPMoveSet = tLPMoveDB.query();
	    if (tUpdateLPMoveSet.size() == 0) {
	    	CError.buildErr(this, "LPMove查询失败!");
            return false;
	    }
	    // add by jiaqiangli 2008-10-08 这些字段不知有啥用 最小完毕集合只需记录新旧机构及contno polno and so on
	    for (int i = 0; i < tUpdateLPMoveSet.size(); i++) {
			tUpdateLPMoveSet.get(i + 1).setOutDate(PubFun.getCurrentDate());
			tUpdateLPMoveSet.get(i + 1).setInDate(PubFun.getCurrentDate());
		}
		mMap.put(tUpdateLPMoveSet, "UPDATE");
		// add by jiaqiangli 2008-10-08 update lpmove.OutDate InDate
	    
		
		// 得到当前LPEdorItem保单信息主表的状态，并更新状态为申请确认。
		// mLPEdorItemSchema.setEdorState("0");
		mMap.put(sqlbv, "UPDATE");
		mMap.put(mLPEdorItemSchema, "UPDATE");
		mMap.put(aLCContSet, "UPDATE");
		mMap.put(aLCPolSet, "UPDATE");
		mMap.put(aLCInsuredSet, "UPDATE");
		mMap.put(aLCAppntSet, "UPDATE");
		mMap.put(aLCGetSet, "UPDATE");
		mMap.put(aLCPremSet, "UPDATE");
		mMap.put(aLCInsureAccFeeSet, "UPDATE");
		mMap.put(aLCInsureAccClassFeeSet, "UPDATE");
		mMap.put(aLCInsureAccSet, "UPDATE");
		mMap.put(aLCInsureAccClassSet, "UPDATE");
		mMap.put(aLCAddressSet, "UPDATE");

		mMap.put(aLPContSet, "UPDATE");
		mMap.put(aLPPolSet, "UPDATE");
		mMap.put(aLPInsuredSet, "UPDATE");
		mMap.put(aLPAppntSet, "UPDATE");
		mMap.put(aLPGetSet, "UPDATE");
		mMap.put(aLPPremSet, "UPDATE");
		mMap.put(aLPInsureAccFeeSet, "UPDATE");
		mMap.put(aLPInsureAccClassFeeSet, "UPDATE");
		mMap.put(aLPInsureAccSet, "UPDATE");
		mMap.put(aLPInsureAccClassSet, "UPDATE");
		mMap.put(aLPAddressSet, "UPDATE");

		// mMap.put(aLPPersonSet, "UPDATE");
		return true;
	}

	private boolean prepareOutputData() {
		mResult.clear();
		mResult.add(mMap);
		return true;
	}

}
