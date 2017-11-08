package com.sinosoft.lis.bq;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sinosoft.lis.customer.FICustomerMain;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LJABonusGetDB;
import com.sinosoft.lis.db.LJSGetClaimDB;
import com.sinosoft.lis.db.LJSGetDB;
import com.sinosoft.lis.db.LJSGetDrawDB;
import com.sinosoft.lis.db.LJSGetEndorseDB;
import com.sinosoft.lis.db.LJSGetOtherDB;
import com.sinosoft.lis.db.LJSGetTempFeeDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LJTempFeeClassDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LPBnfDB;
import com.sinosoft.lis.db.LPContDB;
import com.sinosoft.lis.db.LPDutyDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpContDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpPolDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.db.LPPremDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJABonusGetSchema;
import com.sinosoft.lis.schema.LJAGetClaimSchema;
import com.sinosoft.lis.schema.LJAGetDrawSchema;
import com.sinosoft.lis.schema.LJAGetEndorseSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.schema.LJAGetTempFeeSchema;
import com.sinosoft.lis.schema.LJAPayPersonSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LJSGetClaimSchema;
import com.sinosoft.lis.schema.LJSGetDrawSchema;
import com.sinosoft.lis.schema.LJSGetEndorseSchema;
import com.sinosoft.lis.schema.LJSGetOtherSchema;
import com.sinosoft.lis.schema.LJSGetSchema;
import com.sinosoft.lis.schema.LJSGetTempFeeSchema;
import com.sinosoft.lis.schema.LJSPayPersonSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LMRiskAppSchema;
import com.sinosoft.lis.schema.LPBnfSchema;
import com.sinosoft.lis.vdb.LJAGetClaimDBSet;
import com.sinosoft.lis.vdb.LJAGetDBSet;
import com.sinosoft.lis.vdb.LJAGetDrawDBSet;
import com.sinosoft.lis.vdb.LJAGetEndorseDBSet;
import com.sinosoft.lis.vdb.LJAGetOtherDBSet;
import com.sinosoft.lis.vdb.LJAGetTempFeeDBSet;
import com.sinosoft.lis.vdb.LJAPayDBSet;
import com.sinosoft.lis.vdb.LJTempFeeClassDBSet;
import com.sinosoft.lis.vdb.LJTempFeeDBSet;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJABonusGetSet;
import com.sinosoft.lis.vschema.LJAGetClaimSet;
import com.sinosoft.lis.vschema.LJAGetDrawSet;
import com.sinosoft.lis.vschema.LJAGetEndorseSet;
import com.sinosoft.lis.vschema.LJAGetOtherSet;
import com.sinosoft.lis.vschema.LJAGetSet;
import com.sinosoft.lis.vschema.LJAGetTempFeeSet;
import com.sinosoft.lis.vschema.LJAPayPersonSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSGetClaimSet;
import com.sinosoft.lis.vschema.LJSGetDrawSet;
import com.sinosoft.lis.vschema.LJSGetEndorseSet;
import com.sinosoft.lis.vschema.LJSGetOtherSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.lis.vschema.LJSGetTempFeeSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LJTempFeeClassSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.vschema.LPBnfSet;
import com.sinosoft.lis.vschema.LPContSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpContSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.lis.vschema.LPGrpPolSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.lis.vschema.LPPremSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * ClassName: LJFinaConfirm
 * </p>
 * <p>
 * Description: 财务业务核销转储
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @author: TJJ
 * @ReWrite: ZhangRong
 * @CreateDate：2002-10-15
 */
public class LJFinaConfirm {
private static Logger logger = Logger.getLogger(LJFinaConfirm.class);
	private VData mResult = new VData();
	private MMap mMap = new MMap();

	// 错误处理类，每个需要错误处理的类中都放置该类
	public CErrors mErrors = new CErrors();
	/* 通知书号 */
	String mGetNoticeNo;
	/* 给付类型标志 "I": 交费核销；"O":退费核销 */
	String mFlag;
	/* 给付类型 */
	String mType;
	/* 实付号码 */
	String mActuGetNo;
	/* 交费收据号码 */
	String mPayNo;
	/* 号码生成参数(set,get方法提供外部接口) */
	String mLimit;
	/* 操作员 */
	String mOperator;
	String mDrawer;
	String mDrawerID;
	String mShouldDate;
	//add by wk
	String mEdorAcceptNo;
	/* 退费系列表 */
	LJSGetSet mLJSGetSet = new LJSGetSet();
	LJSGetClaimSet mLJSGetClaimSet = new LJSGetClaimSet();
	LJSGetOtherSet mLJSGetOtherSet = new LJSGetOtherSet();
	LJSGetTempFeeSet mLJSGetTempFeeSet = new LJSGetTempFeeSet();
	LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
	LJAGetSet mLJAGetSet = new LJAGetSet();
	LJAGetSet mDelLJAGetSet = new LJAGetSet();
	LJAGetClaimSet mLJAGetClaimSet = new LJAGetClaimSet();
	LJAGetOtherSet mLJAGetOtherSet = new LJAGetOtherSet();
	LJAGetDrawSet mLJAGetDrawSet = new LJAGetDrawSet();
	LJABonusGetSet mDelLJABonusGetSet = new LJABonusGetSet();
	LJABonusGetSet mLJABonusGetSet = new LJABonusGetSet();
	LJAGetTempFeeSet mLJAGetTempFeeSet = new LJAGetTempFeeSet();
	/* 交费系列表 */
	LJAPaySet mLJAPaySet = new LJAPaySet();
	LJSPaySet mLJSPaySet = new LJSPaySet();
	/* 保全批改补退费表 */
	LJAGetEndorseSet mLJAGetEndorseSet = new LJAGetEndorseSet();
	LJSGetEndorseSet mLJSGetEndorseSet = new LJSGetEndorseSet();
	/* 应收个人交费表 */
	LJAPayPersonSet mLJAPayPersonSet = new LJAPayPersonSet();

	LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();
	/* 保费相关系列表 */
	LPPremSet mLPPremSet = new LPPremSet();
	LPDutySet mLPDutySet = new LPDutySet();
	LPPolSet mLPPolSet = new LPPolSet();
	LPContSet mLPContSet = new LPContSet();
	LPGrpPolSet mLPGrpPolSet = new LPGrpPolSet();
	LPGrpContSet mLPGrpContSet = new LPGrpContSet();
	/* 暂交费表 */
	LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
	LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
	// 财务交费到帐日期，add by Minim
	String mEnterAccDate = "";
	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();
	
	/**保全红利领取付费方式*/
	String tGetForm="";

	// @Constructor
	public LJFinaConfirm(String aGetNoticeNo) {
		mGetNoticeNo = aGetNoticeNo;
	}

	public LJFinaConfirm(String aGetNoticeNo, String aFlag) {
		mGetNoticeNo = aGetNoticeNo;
		mFlag = aFlag;
	}

	public LJFinaConfirm(String aGetNoticeNo, String aFlag, String aType) {
		mGetNoticeNo = aGetNoticeNo;
		mFlag = aFlag;
		mType = aType;
	}

	// @Method

	/**
	 * 提交整体给付核销
	 * 
	 * @return
	 */

	public boolean submitData() {
		logger.debug("\nStart LJFinaConfirm\n\n");
		// 核销交退费
		if (!this.chkFinaConfirm()) {
			return false;
		}

		if (mFlag.equals("O")) {
			if (!this.conLJAGetSerials()) {
				return false;
			}
			// if (!this.saveLJAGetSerials())
			// {
			// return false;
			// } //commented by zhangrong
			// return true;
		} else if (mFlag.equals("I")) {
			// 准备转储交费数据
			if (!this.conLJAPaySerials()) {
				return false;
			}
			// if (!this.saveLJAPaySerials())
			// {
			// return false;
			// } //commented by zhangrong
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "saveData";
			tError.errorMessage = "传入标志有误（mFlag）!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}

		// PubSubmit tSubmit = new PubSubmit();
		//
		// if (!tSubmit.submitData(mResult, "")) //数据提交
		// {
		// // @@错误处理
		// this.mErrors.copyAllErrors(tSubmit.mErrors);
		// CError tError = new CError();
		// tError.moduleName = "LJFinaConfirm";
		// tError.functionName = "submitData";
		// tError.errorMessage = "数据提交失败!";
		// this.mErrors.addOneError(tError);
		// return false;
		// }

		return true;
	}

	public VData getResult() {
		return mResult;
	}
	public LJAGetSet getLJAGetSet()
	{
		return this.mLJAGetSet;
	}
	
	public LJAPaySet getLJAPaySet()
	{
		return this.mLJAPaySet;
	}

	/**
	 * 按照给付类型提交给付核销
	 * 
	 * @return
	 */
	public boolean submitDataByType() {
		String aType;
		aType = mType;

		if (!this.chkFinaConfirm()) {
			return false;
		}
		if (mFlag.equals("O")) {
			if (!this.conLJAGetSerials(aType)) {
				return false;
			}
			if (!this.saveLJAGetSerials()) {
				return false;
			}
			return true;
		} else if (mFlag.equals("I")) {
			if (!this.conLJAPaySerials(aType)) {
				return false;
			}
			if (!this.saveLJAPaySerials()) {
				return false;
			}
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "saveData";
			tError.errorMessage = "传入标志有误（mFlag）!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备转储数据
	 * 
	 * @return true or false
	 */
	private boolean conLJAGetSerials() {
		String tActuGetNo;

		/* 应付总表 */
		LJSGetSchema tLJSGetSchema = new LJSGetSchema();

		/* 赔付应付表 */
		LJSGetClaimSchema tLJSGetClaimSchema = new LJSGetClaimSchema();

		/* 其他退费应付表 */
		LJSGetOtherSchema tLJSGetOtherSchema = new LJSGetOtherSchema();

		/* 暂交费退费应付表 */
		LJSGetTempFeeSchema tLJSGetTempFeeSchema = new LJSGetTempFeeSchema();

		/* 给付表(生存领取_应付) */
		LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();

		/* 批改补退费表（应收/应付） */
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		/* 实付总表 */
		mLJAGetSet = new LJAGetSet();
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();

		/* 赔付实付表 */
		LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
		mLJAGetClaimSet = new LJAGetClaimSet();

		/* 其他退费实付表 */
		LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
		mLJAGetOtherSet = new LJAGetOtherSet();

		/* 暂交费退费实付表 */
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		mLJAGetTempFeeSet = new LJAGetTempFeeSet();

		/* 给付表(生存领取_实付) */
		LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
		mLJAGetDrawSet = new LJAGetDrawSet();

		/* 批改补退费表（实收/实付） */
		LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
		mLJAGetEndorseSet = new LJAGetEndorseSet();

		/* 得到实付号码 */
		tActuGetNo = this.getActuGetNo();

		/* 赔付应付核销转储 */
		LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
		tLJSGetClaimDB.setGetNoticeNo(mGetNoticeNo);
		mLJSGetClaimSet = tLJSGetClaimDB.query();
		if (mLJSGetClaimSet != null) {
			mLJAGetClaimSet.clear();
			for (int i = 1; i <= mLJSGetClaimSet.size(); i++) {
				tLJSGetClaimSchema = new LJSGetClaimSchema();
				tLJAGetClaimSchema = new LJAGetClaimSchema();
				tLJSGetClaimSchema = mLJSGetClaimSet.get(i);
				Reflections tReflections = new Reflections();
				tReflections
						.transFields(tLJAGetClaimSchema, tLJSGetClaimSchema);

				tLJAGetClaimSchema.setActuGetNo(tActuGetNo);
				// tLJAGetClaimSchema.setConfDate(mCurrentDate);
				tLJAGetClaimSchema.setOperator(this.getOperator());
				tLJAGetClaimSchema.setMakeDate(mCurrentDate);
				tLJAGetClaimSchema.setMakeTime(mCurrentTime);
				tLJAGetClaimSchema.setModifyDate(mCurrentDate);
				tLJAGetClaimSchema.setModifyTime(mCurrentTime);

				mLJAGetClaimSet.add(tLJAGetClaimSchema);
			}
		}

		/* 其他退费核销转储 */
		LJSGetOtherDB tLJSGetOtherDB = new LJSGetOtherDB();
		tLJSGetOtherDB.setGetNoticeNo(mGetNoticeNo);
		mLJSGetOtherSet = tLJSGetOtherDB.query();
		if (mLJSGetOtherSet != null) {
			mLJAGetOtherSet.clear();
			for (int i = 1; i <= mLJSGetOtherSet.size(); i++) {
				tLJSGetOtherSchema = new LJSGetOtherSchema();
				tLJAGetOtherSchema = new LJAGetOtherSchema();
				tLJSGetOtherSchema = mLJSGetOtherSet.get(i);
				Reflections tReflections = new Reflections();
				tReflections
						.transFields(tLJAGetOtherSchema, tLJSGetOtherSchema);

				tLJAGetClaimSchema.setActuGetNo(tActuGetNo);
				// tLJAGetClaimSchema.setConfDate(mCurrentDate);
				tLJAGetClaimSchema.setOperator(this.getOperator());
				tLJAGetClaimSchema.setMakeDate(mCurrentDate);
				tLJAGetClaimSchema.setMakeTime(mCurrentTime);

				tLJAGetClaimSchema.setModifyDate(mCurrentDate);
				tLJAGetClaimSchema.setModifyTime(mCurrentTime);

				mLJAGetOtherSet.add(tLJAGetOtherSchema);
			}
		}

		/* 暂交费退费核销转储 */
		LJSGetTempFeeDB tLJSGetTempFeeDB = new LJSGetTempFeeDB();
		tLJSGetTempFeeDB.setGetNoticeNo(mGetNoticeNo);
		mLJSGetTempFeeSet = tLJSGetTempFeeDB.query();
		if (mLJSGetTempFeeSet != null) {
			mLJAGetTempFeeSet.clear();
			for (int i = 1; i <= mLJSGetTempFeeSet.size(); i++) {
				tLJSGetTempFeeSchema = new LJSGetTempFeeSchema();
				tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
				tLJSGetTempFeeSchema = mLJSGetTempFeeSet.get(i);
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLJAGetTempFeeSchema,
						tLJSGetTempFeeSchema);

				tLJAGetTempFeeSchema.setActuGetNo(tActuGetNo);
				// tLJAGetTempFeeSchema.setConfDate(mCurrentDate);
				tLJAGetTempFeeSchema.setOperator(this.getOperator());
				tLJAGetTempFeeSchema.setMakeDate(mCurrentDate);
				tLJAGetTempFeeSchema.setMakeTime(mCurrentTime);

				tLJAGetTempFeeSchema.setModifyDate(mCurrentDate);
				tLJAGetTempFeeSchema.setModifyTime(mCurrentTime);

				mLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
			}
		}

		/* 给付表(生存领取_应付)核销转储 */
		LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
		tLJSGetDrawDB.setGetNoticeNo(mGetNoticeNo);
		mLJSGetDrawSet = tLJSGetDrawDB.query();
		if (mLJSGetDrawSet != null) {
			mLJAGetDrawSet.clear();
			for (int i = 1; i <= mLJSGetDrawSet.size(); i++) {
				tLJSGetDrawSchema = new LJSGetDrawSchema();
				tLJAGetDrawSchema = new LJAGetDrawSchema();
				tLJSGetDrawSchema = mLJSGetDrawSet.get(i);

				Reflections tReflections = new Reflections();
				tReflections.transFields(tLJAGetDrawSchema, tLJSGetDrawSchema);

				tLJAGetDrawSchema.setActuGetNo(tActuGetNo);
				// tLJAGetDrawSchema.setConfDate(mCurrentDate);
				tLJAGetDrawSchema.setOperator(this.getOperator());
				tLJAGetDrawSchema.setMakeDate(mCurrentDate);
				tLJAGetDrawSchema.setMakeTime(mCurrentTime);

				tLJAGetDrawSchema.setModifyDate(mCurrentDate);
				tLJAGetDrawSchema.setModifyTime(mCurrentTime);

				mLJAGetDrawSet.add(tLJAGetDrawSchema);
				//营改增 add zhangyingfeng 2016-07-07
				//价税分离  计算器
				TaxCalculator.calBySchemaSet(mLJAGetDrawSet);
				//end zhangyingfeng
			}
		}

		/* 应付总表核销转储 */
		// 由于“批改补退费核销转储”中还会产生“应付总表”记录删除，所以此操作要在“批改补退费核销转储”前做！
		//String sql="select  edortype,decode((select othernotype from lpedorapp a where a.edoracceptno=b.edoracceptno),'4','G','I') from lpedoritem b where edoracceptno='"+mEdorAcceptNo+"' and rownum=1";
		String sql="select (case (select othernotype from lpedorapp where edoracceptno= '"+"?mEdorAcceptNo?"+"') when '4' then 'G' else 'I' end) from dual";
		ExeSQL tExeSQL = new ExeSQL();
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sql);
		sbv1.put("mEdorAcceptNo", mEdorAcceptNo);
		SSRS tssrs_edortype = tExeSQL.execSQL(sbv1);
		if(tssrs_edortype.getMaxRow()<=0)
		{
		    mErrors.addOneError(new CError("查询保全类型失败！"));
			return false;
		}
		String tRiskType = tssrs_edortype.GetText(1,1);
		String tEdorType = "";
		if(tRiskType.equals("G"))
		{
			//团险
			LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
			tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
			LPGrpEdorItemSet tLPGrpEdorItemSet = tLPGrpEdorItemDB.query();
			tEdorType = tLPGrpEdorItemSet.get(1).getEdorType();
		}else{
			//个险
			LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
			tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
			LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
			tEdorType = tLPEdorItemSet.get(1).getEdorType();
		}
		
		if(tEdorType == null || tEdorType.equals(""))
		{
			mErrors.addOneError(new CError("查询保全类型失败！"));
			return false;
		}
		//String tEdorType = tssrs_edortype.GetText(1, 1);
		if(tEdorType.equals("LG")&&tRiskType.equals("I"))
		{
			String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", this.getLimit());
			LPBnfDB tLPBnfDB = new LPBnfDB();
		    String sql_lpbnf = "select * from lpbnf where edorno in (select edorno from lpedoritem where edoracceptno='"+"?mEdorAcceptNo?"+"')";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(sql_lpbnf);
			sbv2.put("mEdorAcceptNo", mEdorAcceptNo);
		    LPBnfSet tLPBnfSet = tLPBnfDB.executeQuery(sbv2);
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
			LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
			tLJSGetEndorseSet = tLJSGetEndorseDB.query();
			LJAGetSet tLJAGetSet = new LJAGetSet();
			for(int i=1;i<=tLPBnfSet.size();i++)
			{
				String strLimit = this.getLimit();
				String temp_ActuGetNo = PubFun1.CreateMaxNo("GETNO", strLimit);
				
				LPBnfSchema tLPBnfSchema = tLPBnfSet.get(i);
				LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
				double actmoney=0;
				//营改增 add zhangyingfeng 2016-07-12
				//增加 税额 净额 汇总
				double actNetAm=0;   //净额
				double actTaxAm=0;   //税额
				double actTax=0;   //税率
				//end  zhangyingfeng 2016-07-12
				for(int j=1;j<=tLJSGetEndorseSet.size();j++)
				{
					LJSGetEndorseSchema temp_LJSGetEndorseSchema = tLJSGetEndorseSet.get(j);

					if(temp_LJSGetEndorseSchema.getGetFlag().equals("1"))
					{
						LJAGetDrawSchema temp_LJAGetDrawSchema = new LJAGetDrawSchema();
						temp_LJAGetDrawSchema.setActuGetNo(temp_ActuGetNo);
						PubFun.copySchema(temp_LJAGetDrawSchema, temp_LJSGetEndorseSchema);
						temp_LJAGetDrawSchema.setGetDutyCode("000000");//对于富贵年年这样的险种无法区分所以....
						temp_LJAGetDrawSchema.setGetDutyKind("000000");
	//					temp_LJAGetDrawSchema.setGetMoney(aGetMoney)
	//					temp_LJAGetDrawSchema.setEnterAccDate(aEnterAccDate)//财务实际领取设置
	//					temp_LJAGetDrawSchema.setConfDate(aConfDate)//财务实际领取设置
	//					temp_LJAGetDrawSchema.setLastGettoDate(aLastGettoDate) 无用
	//					temp_LJAGetDrawSchema.setCurGetToDate(aCurGetToDate) 无用
	//					temp_LJAGetDrawSchema.setGetmFirstFlag 无用
	//					temp_LJAGetDrawSchema.setGetChannelFlag无用
	//					temp_LJAGetDrawSchema.setDestrayFlag无用
						temp_LJAGetDrawSchema.setSerialNo(tSerialNo);//统一用一个流水号
						temp_LJAGetDrawSchema.setMakeDate(this.mCurrentDate);
						temp_LJAGetDrawSchema.setMakeTime(this.mCurrentTime);
						temp_LJAGetDrawSchema.setModifyDate(this.mCurrentDate);
						temp_LJAGetDrawSchema.setModifyTime(this.mCurrentTime);
						temp_LJAGetDrawSchema.setRReportFlag("0");//
						temp_LJAGetDrawSchema.setComeFlag("1");
						if(tLPBnfSet.size()==1)
						{
							temp_LJAGetDrawSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney());
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							temp_LJAGetDrawSchema.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount());   //净额
							temp_LJAGetDrawSchema.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount());  //税额
							temp_LJAGetDrawSchema.setTax(temp_LJSGetEndorseSchema.getTax());  //税率
							//end  zhangyingfeng 2016-07-12
						}
						else if(i==tLPBnfSet.size())
						{
							double money = 0; 
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							double netAm=0;   //净额
							double taxAm=0;   //税额
							//end  zhangyingfeng 2016-07-12
							for(int k=1;k<=mLJAGetDrawSet.size();k++)
							{
								if(mLJAGetDrawSet.get(k).getFeeFinaType().equals(temp_LJSGetEndorseSchema.getFeeFinaType()))
								{
									money = money + mLJAGetDrawSet.get(k).getGetMoney();
									//营改增 add zhangyingfeng 2016-07-12
									//增加 税额 净额 汇总
									netAm=netAm + mLJAGetDrawSet.get(k).getNetAmount();   //净额
									taxAm=taxAm + mLJAGetDrawSet.get(k).getTaxAmount();   //税额
									//end  zhangyingfeng 2016-07-12
								}
							}
							temp_LJAGetDrawSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney()-money);
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							temp_LJAGetDrawSchema.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount()-netAm);  //净额
							temp_LJAGetDrawSchema.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount()-taxAm);   //税额
							temp_LJAGetDrawSchema.setTax(temp_LJSGetEndorseSchema.getTax());  //税率  因业务不同不能统一  暂取任意一条
							//end  zhangyingfeng 2016-07-12
						}
						else
						{
							temp_LJAGetDrawSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney()*tLPBnfSchema.getBnfLot());
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							temp_LJAGetDrawSchema.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount()*tLPBnfSchema.getBnfLot());  //净额
							temp_LJAGetDrawSchema.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount()*tLPBnfSchema.getBnfLot());   //税额
							temp_LJAGetDrawSchema.setTax(temp_LJSGetEndorseSchema.getTax());  //税率  因业务不同不能统一  暂取任意一条
							//end  zhangyingfeng 2016-07-12
						}
						actmoney =actmoney + temp_LJAGetDrawSchema.getGetMoney();
//						tLJAGetDrawSet.add(temp_LJAGetDrawSchema);
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总
						actNetAm=actNetAm+temp_LJAGetDrawSchema.getNetAmount();   //净额
						actTaxAm=actTaxAm+temp_LJAGetDrawSchema.getTaxAmount();   //税额
						actTax=temp_LJAGetDrawSchema.getTax();   //税率
						//end  zhangyingfeng 2016-07-12
						this.mLJAGetDrawSet.add(temp_LJAGetDrawSchema);
					}
					else
					{
						LJAGetEndorseSchema temp_LJAGetEndorseSchema = new LJAGetEndorseSchema();
						PubFun.copySchema(temp_LJAGetEndorseSchema, temp_LJSGetEndorseSchema);
						temp_LJAGetEndorseSchema.setActuGetNo(temp_ActuGetNo);
						temp_LJAGetEndorseSchema.setOperator(this.getOperator());
						temp_LJAGetEndorseSchema.setMakeDate(mCurrentDate);
						temp_LJAGetEndorseSchema.setMakeTime(mCurrentTime);
						temp_LJAGetEndorseSchema.setModifyDate(mCurrentDate);
						temp_LJAGetEndorseSchema.setModifyTime(mCurrentTime);
						
						if(tLPBnfSet.size()==1)
						{
							temp_LJAGetEndorseSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney());
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							temp_LJAGetEndorseSchema.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount());  //净额
							temp_LJAGetEndorseSchema.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount());   //税额
							temp_LJAGetEndorseSchema.setTax(temp_LJSGetEndorseSchema.getTax());  //税率  因业务不同不能统一  暂取任意一条
							//end  zhangyingfeng 2016-07-12
						}
						else if(i==tLPBnfSet.size())
						{
							double money = 0; 
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							double netAm=0;   //净额
							double taxAm=0;   //税额
							//end  zhangyingfeng 2016-07-12
							for(int k=1;k<=this.mLJAGetEndorseSet.size();k++)
							{
								if(mLJAGetEndorseSet.get(k).getFeeFinaType().equals(temp_LJSGetEndorseSchema.getFeeFinaType()))
								{
									money = money + mLJAGetEndorseSet.get(k).getGetMoney();
									//营改增 add zhangyingfeng 2016-07-12
									//增加 税额 净额 汇总
									netAm=netAm + mLJAGetEndorseSet.get(k).getNetAmount();   //净额
									taxAm=taxAm + mLJAGetEndorseSet.get(k).getTaxAmount();   //税额
									//end  zhangyingfeng 2016-07-12
								}
							}
							temp_LJAGetEndorseSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney()-money);
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							temp_LJAGetEndorseSchema.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount()-netAm);  //净额
							temp_LJAGetEndorseSchema.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount()-taxAm);   //税额
							temp_LJAGetEndorseSchema.setTax(temp_LJSGetEndorseSchema.getTax());  //税率  因业务不同不能统一  暂取任意一条
							//end  zhangyingfeng 2016-07-12
						}
						else
						{
							temp_LJAGetEndorseSchema.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney()*tLPBnfSchema.getBnfLot());
							//营改增 add zhangyingfeng 2016-07-12
							//增加 税额 净额 汇总
							temp_LJAGetEndorseSchema.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount()*tLPBnfSchema.getBnfLot());  //净额
							temp_LJAGetEndorseSchema.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount()*tLPBnfSchema.getBnfLot());   //税额
							temp_LJAGetEndorseSchema.setTax(temp_LJSGetEndorseSchema.getTax());  //税率  因业务不同不能统一  暂取任意一条
							//end  zhangyingfeng 2016-07-12
						}
						actmoney =actmoney - temp_LJAGetEndorseSchema.getGetMoney();
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总
						actNetAm=actNetAm+temp_LJAGetEndorseSchema.getNetAmount();   //净额
						actTaxAm=actTaxAm+temp_LJAGetEndorseSchema.getTaxAmount();   //税额
						actTax=temp_LJAGetEndorseSchema.getTax();   //税率
						//end  zhangyingfeng 2016-07-12
						mLJAGetEndorseSet.add(temp_LJAGetEndorseSchema);
					}
				}
				
				LJAGetSchema temp_LJAGetSchema = new LJAGetSchema();
				PubFun.copySchema(temp_LJAGetSchema, mLJAGetDrawSet.get(1));
				temp_LJAGetSchema.setActuGetNo(temp_ActuGetNo);
				temp_LJAGetSchema.setOtherNo(mEdorAcceptNo);
				temp_LJAGetSchema.setOtherNoType("10");
				temp_LJAGetSchema.setPayMode(tLPBnfSchema.getRemark());//盗用remark记录付费方式
				temp_LJAGetSchema.setBankOnTheWayFlag("0");
				temp_LJAGetSchema.setBankSuccFlag("0");
				temp_LJAGetSchema.setSendBankCount(0);
				temp_LJAGetSchema.setAccName(tLPBnfSchema.getAccName());
//				temp_LJAGetSchema.setStartGetDate("")
				temp_LJAGetSchema.setSumGetMoney(actmoney);
				//营改增 add zhangyingfeng 2016-07-12
				//增加 税额 净额 税率 汇总
				temp_LJAGetSchema.setNetAmount(actNetAm);  //净额
				temp_LJAGetSchema.setTaxAmount(actTaxAm);   //税额
				temp_LJAGetSchema.setTax(actTax);  //税率
				//end  zhangyingfeng 2016-07-12
//				temp_LJAGetSchema.setShouldDate(aShouldDate)
//				temp_LJAGetSchema.setEnterAccDate(aEnterAccDate)
//				temp_LJAGetSchema.setConfDate(aConfDate)
//				ApproveCode
//				ApproveDate
//				GetNoticeNo
				temp_LJAGetSchema.setBankCode(tLPBnfSchema.getBankCode());
				temp_LJAGetSchema.setBankAccNo(tLPBnfSchema.getBankAccNo());
				temp_LJAGetSchema.setDrawer(tLPBnfSchema.getName());
				temp_LJAGetSchema.setDrawerID(tLPBnfSchema.getIDNo());
				temp_LJAGetSchema.setSerialNo(tSerialNo);
//				Operator
//				MakeDate
//				MakeTime
//				ModifyDate
//				ModifyTime
//				temp_LJAGetSchema.setActualDrawer(tLPBnfSchema.getName());
//				temp_LJAGetSchema.setActualDrawerID(tLPBnfSchema.getIDNo());
				temp_LJAGetSchema.setPolicyCom(temp_LJAGetSchema.getManageCom());
//				OperState
//				BILLPAYREASON
				tLJAGetSet.add(temp_LJAGetSchema);
			}
			this.mLJSGetEndorseSet.add(tLJSGetEndorseSet);
			this.mLJAGetSet.add(tLJAGetSet);
		}
		else if((tEdorType.equals("CT")||tEdorType.equals("XT"))&&tRiskType.equals("I"))
		{
			String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", this.getLimit());
			LPBnfDB tLPBnfDB = new LPBnfDB();
		    String sql_lpbnf = "select * from lpbnf where edorno in (select edorno from lpedoritem where edoracceptno='"+"?mEdorAcceptNo?"+"')";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(sql_lpbnf);
			sbv3.put("mEdorAcceptNo", mEdorAcceptNo);
		    LPBnfSet tLPBnfSet = tLPBnfDB.executeQuery(sbv3);
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
			LJSGetEndorseSet tLJSGetEndorseSet = new LJSGetEndorseSet();
			tLJSGetEndorseSet = tLJSGetEndorseDB.query();
			LJAGetSet tLJAGetSet = new LJAGetSet();
			for(int i=1;i<=tLPBnfSet.size();i++)
			{
				String strLimit = this.getLimit();
				String temp_ActuGetNo = PubFun1.CreateMaxNo("GETNO", strLimit);
				
				LPBnfSchema tLPBnfSchema = tLPBnfSet.get(i);
				LJAGetEndorseSet temp_LJAGetEndorseSet = new LJAGetEndorseSet();
				//double actmoney=0;
				Map curMap = new HashMap();
				for(int j=1;j<=tLJSGetEndorseSet.size();j++)
				{
					LJSGetEndorseSchema temp_LJSGetEndorseSchema = tLJSGetEndorseSet.get(j);
//					LJAGetDrawSchema temp_LJAGetDrawSchema = new LJAGetDrawSchema();
					String tCurrency = temp_LJSGetEndorseSchema.getCurrency();
					if(tCurrency == null || "".equals(tCurrency)){
						CError.buildErr(this, "批改补退费的币种信息不完整！");
						return false;
					}
					double curMoney = 0.0;
					//营改增 add zhangyingfeng 2016-07-12
					//增加 税额 净额 汇总 ，一起放入临时 map中 
					Hashtable<String, Double> tMap=new Hashtable<String, Double>();
					double tNetAm=0;   //净额
					double tTaxAm=0;   //税额
					double tTax=0;   //税率
					//end  zhangyingfeng 2016-07-12
					if(curMap.containsKey(tCurrency)){
						curMoney = (Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"curMoney");
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总 ，一起放入临时 map中
						tNetAm=(Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"netAm");   //净额
						tTaxAm=(Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"taxAm");  //税额
						tTax= (Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"tax");   //税率
						tMap.put(tCurrency+"curMoney", curMoney);
						tMap.put(tCurrency+"netAm", tNetAm);
						tMap.put(tCurrency+"taxAm", tTaxAm);
						tMap.put(tCurrency+"tax", tTax);
						//end  zhangyingfeng 2016-07-12
					}
					LJAGetEndorseSchema temp_LJAGetEndorseSchema_new = new LJAGetEndorseSchema();
					temp_LJAGetEndorseSchema_new.setActuGetNo(temp_ActuGetNo);
					PubFun.copySchema(temp_LJAGetEndorseSchema_new, temp_LJSGetEndorseSchema);
					temp_LJAGetEndorseSchema_new.setSerialNo(tSerialNo);//统一用一个流水号
					temp_LJAGetEndorseSchema_new.setMakeDate(this.mCurrentDate);
					temp_LJAGetEndorseSchema_new.setMakeTime(this.mCurrentTime);
					temp_LJAGetEndorseSchema_new.setModifyDate(this.mCurrentDate);
					temp_LJAGetEndorseSchema_new.setModifyTime(this.mCurrentTime);
					if(tLPBnfSet.size()==1)
					{
						temp_LJAGetEndorseSchema_new.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney());
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总  
						temp_LJAGetEndorseSchema_new.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount());  //净额
						temp_LJAGetEndorseSchema_new.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount());   //税额
						temp_LJAGetEndorseSchema_new.setTax(temp_LJSGetEndorseSchema.getTax());   //税率
						//end  zhangyingfeng 2016-07-12
						if(tEdorType.equals("XT"))
						{
							temp_LJAGetEndorseSchema_new.setSerialNo(
									String.valueOf(PubFun.round(Double.parseDouble(temp_LJSGetEndorseSchema.getSerialNo()),2)));
						}
					}
					else if(i==tLPBnfSet.size())
					{
						double money = 0;
						double act_money=0;//用于存储SerialNo中保存的退保原始数据，只有协议退保有用处
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总
						double netAm=0;   //净额
						double taxAm=0;   //税额
						//end  zhangyingfeng 2016-07-12
						for(int k=1;k<=mLJAGetEndorseSet.size();k++)
						{
							if(mLJAGetEndorseSet.get(k).getFeeFinaType().equals(temp_LJSGetEndorseSchema.getFeeFinaType())
									&&mLJAGetEndorseSet.get(k).getDutyCode().equals(temp_LJSGetEndorseSchema.getDutyCode())
									&&mLJAGetEndorseSet.get(k).getPayPlanCode().equals(temp_LJSGetEndorseSchema.getPayPlanCode())
									&&mLJAGetEndorseSet.get(k).getSubFeeOperationType().equals(temp_LJSGetEndorseSchema.getSubFeeOperationType()))
							{
								money = money + mLJAGetEndorseSet.get(k).getGetMoney();
								//营改增 add zhangyingfeng 2016-07-12
								//增加 税额 净额 汇总  
								netAm = netAm + mLJAGetEndorseSet.get(k).getNetAmount();  //净额
								taxAm = taxAm + mLJAGetEndorseSet.get(k).getTaxAmount();  //税额
								//end  zhangyingfeng 2016-07-12
								if(tEdorType.equals("XT"))
								{
									act_money = act_money +PubFun.round(Double.parseDouble(mLJAGetEndorseSet.get(k).getSerialNo()),2);
								}
							}
						}
						temp_LJAGetEndorseSchema_new.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney()-money);
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总
						temp_LJAGetEndorseSchema_new.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount()-netAm);  //净额
						temp_LJAGetEndorseSchema_new.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount()-taxAm);   //税额
						temp_LJAGetEndorseSchema_new.setTax(temp_LJSGetEndorseSchema.getTax());  //税率  因业务不同不能统一  暂取任意一条
						//end  zhangyingfeng 2016-07-12						
						if(tEdorType.equals("XT"))
						{
							temp_LJAGetEndorseSchema_new.setSerialNo(
									String.valueOf(PubFun.round(Double.parseDouble(temp_LJSGetEndorseSchema.getSerialNo())-act_money,2)));
						}
					}
					else
					{
						temp_LJAGetEndorseSchema_new.setGetMoney(temp_LJSGetEndorseSchema.getGetMoney()*tLPBnfSchema.getBnfLot());
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总
						temp_LJAGetEndorseSchema_new.setNetAmount(temp_LJSGetEndorseSchema.getNetAmount()*tLPBnfSchema.getBnfLot());  //净额
						temp_LJAGetEndorseSchema_new.setTaxAmount(temp_LJSGetEndorseSchema.getTaxAmount()*tLPBnfSchema.getBnfLot());   //税额
						temp_LJAGetEndorseSchema_new.setTax(temp_LJSGetEndorseSchema.getTax());  //税率  因业务不同不能统一  暂取任意一条
						//end  zhangyingfeng 2016-07-12
						if(tEdorType.equals("XT"))
						{
							temp_LJAGetEndorseSchema_new.setSerialNo(
									String.valueOf(PubFun.round(Double.parseDouble(temp_LJSGetEndorseSchema.getSerialNo())*tLPBnfSchema.getBnfLot(),2)));
						}
					}
					if(temp_LJAGetEndorseSchema_new.getGetFlag().equals("0"))
					{
						//actmoney =actmoney - temp_LJAGetEndorseSchema_new.getGetMoney();
						curMoney = curMoney - temp_LJAGetEndorseSchema_new.getGetMoney();
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总 ，一起放入临时 map中
						tNetAm=tNetAm - temp_LJAGetEndorseSchema_new.getNetAmount();   //净额
						tTaxAm=tTaxAm - temp_LJAGetEndorseSchema_new.getTaxAmount();  //税额
						tTax= temp_LJAGetEndorseSchema_new.getTax();   //税率
						tMap.put(tCurrency+"curMoney", curMoney);
						tMap.put(tCurrency+"netAm", tNetAm);
						tMap.put(tCurrency+"taxAm", tTaxAm);
						tMap.put(tCurrency+"tax", tTax);
						//end  zhangyingfeng 2016-07-12
					}
					else
					{
						//actmoney =actmoney + temp_LJAGetEndorseSchema_new.getGetMoney();
						curMoney = curMoney + temp_LJAGetEndorseSchema_new.getGetMoney();
						//营改增 add zhangyingfeng 2016-07-12
						//增加 税额 净额 汇总 ，一起放入临时 map中
						tNetAm=tNetAm + temp_LJAGetEndorseSchema_new.getNetAmount();   //净额
						tTaxAm=tTaxAm + temp_LJAGetEndorseSchema_new.getTaxAmount();  //税额
						tTax=temp_LJAGetEndorseSchema_new.getTax();   //税率
						tMap.put(tCurrency+"curMoney", curMoney);
						tMap.put(tCurrency+"netAm", tNetAm);
						tMap.put(tCurrency+"taxAm", tTaxAm);
						tMap.put(tCurrency+"tax", tTax);
						//end  zhangyingfeng 2016-07-12
					}
//					curMap.put(tCurrency, curMoney);
					//营改增 add zhangyingfeng 2016-07-12
					//增加 税额 净额 汇总 ，一起放入curMap中
					curMap.put(tCurrency, tMap);
					//end  zhangyingfeng 2016-07-12
					temp_LJAGetEndorseSet.add(temp_LJAGetEndorseSchema_new);
				}
//				this.mLJAGetDrawSet.add(tLJAGetDrawSet);
	            this.mLJAGetEndorseSet.add(temp_LJAGetEndorseSet);
				
	            Iterator curGetIT = curMap.keySet().iterator();
	            while(curGetIT.hasNext()){
	            	String tCurrency = (String)curGetIT.next();
	            	double tCurGetMoney = (Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"curMoney");
					//营改增 add zhangyingfeng 2016-07-12
					//增加 税额 净额 汇总 ，从 curMap 中  取出
	            	double tCurGetNetAm = (Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"netAm");  //净额
	            	double tCurGetTaxAm = (Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"taxAm"); //税额
	            	double tCurGetTax = (Double)((Hashtable)curMap.get(tCurrency)).get(tCurrency+"tax");  //税率
					//end  zhangyingfeng 2016-07-12
	            	LJAGetSchema temp_LJAGetSchema = new LJAGetSchema();
	            	PubFun.copySchema(temp_LJAGetSchema, temp_LJAGetEndorseSet.get(1));
	            	temp_LJAGetSchema.setActuGetNo(temp_ActuGetNo);
	            	temp_LJAGetSchema.setOtherNo(mEdorAcceptNo);
	            	temp_LJAGetSchema.setOtherNoType("10");
	            	temp_LJAGetSchema.setPayMode(tLPBnfSchema.getRemark());//盗用remark记录付费方式
	            	temp_LJAGetSchema.setBankOnTheWayFlag("0");
	            	temp_LJAGetSchema.setBankSuccFlag("0");
	            	temp_LJAGetSchema.setSendBankCount(0);
	            	temp_LJAGetSchema.setAccName(tLPBnfSchema.getAccName());
	            	temp_LJAGetSchema.setSumGetMoney(tCurGetMoney);
					//营改增 add zhangyingfeng 2016-07-12
					//增加 税额 净额 汇总 
	            	temp_LJAGetSchema.setNetAmount(tCurGetNetAm); //净额
	            	temp_LJAGetSchema.setTaxAmount(tCurGetTaxAm); //税额
	            	temp_LJAGetSchema.setTax(tCurGetTax);  //税率
					//end  zhangyingfeng 2016-07-12
	            	temp_LJAGetSchema.setBankCode(tLPBnfSchema.getBankCode());
	            	temp_LJAGetSchema.setBankAccNo(tLPBnfSchema.getBankAccNo());
	            	temp_LJAGetSchema.setDrawer(tLPBnfSchema.getName());
	            	temp_LJAGetSchema.setDrawerID(tLPBnfSchema.getIDNo());
	            	temp_LJAGetSchema.setSerialNo(tSerialNo);
	            	temp_LJAGetSchema.setPolicyCom(temp_LJAGetSchema.getManageCom());
	            	temp_LJAGetSchema.setCurrency(tCurrency);
	            	tLJAGetSet.add(temp_LJAGetSchema);
	            }
			}
			this.mLJSGetEndorseSet.add(tLJSGetEndorseSet);
			this.mLJAGetSet.add(tLJAGetSet);
		}else if(tRiskType.equals("G")&&(tEdorType.equals("AZ")||tEdorType.equals("AT")||tEdorType.equals("AX"))){
			//团险年金险减人,退保分帐户,年金险协议减人给付处理
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
			LJSGetEndorseSet tLJSGetEndorseSet = tLJSGetEndorseDB.query();

			if (tLJSGetEndorseSet != null && tLJSGetEndorseSet.size()>0){
				/*先分帐户给付*/
				String tSerialNo = PubFun1.CreateMaxNo("SERIALNO", this.getLimit());
				String tSql = "SELECT distinct ContNo,InsuredNo FROM LJSGetEndorse WHERE EndorsementNo IN (SELECT EdorNo FROM LPGrpEdorItem LPGrpEdorItem WHERE  EdorAcceptNo = '"+"?mEdorAcceptNo?"+"')";
				SSRS rSSRS = new SSRS();
				ExeSQL rExeSQL = new ExeSQL();
				SQLwithBindVariables sbv4=new SQLwithBindVariables();
				sbv4.sql(tSql);
				sbv4.put("mEdorAcceptNo", mEdorAcceptNo);
				rSSRS = rExeSQL.execSQL(sbv4);
				if (rSSRS == null || rSSRS.MaxRow < 1) {
					CError.buildErr(this, "查询本次保全补退费记录失败");
					return false;
				}
				int tArrLen = rSSRS.MaxRow;
				
				ExeSQL bnfExeSQL = new ExeSQL();
				String nBnfSql = "SELECT COUNT(*) FROM LPBNF WHERE EdorNo IN (SELECT EdorNo FROM LPGrpEdorItem LPGrpEdorItem WHERE  EdorAcceptNo ='"+"?mEdorAcceptNo?"+"')";
				SQLwithBindVariables sbv5=new SQLwithBindVariables();
				sbv5.sql(nBnfSql);
				sbv5.put("mEdorAcceptNo", mEdorAcceptNo);
				String tBnfNum = bnfExeSQL.getOneValue(sbv5);
				int dBnfNum = Integer.parseInt(tBnfNum);
				if(dBnfNum == 0)
				{
					CError.buildErr(this, "请导入分帐户给付的信息!");
					return false;
				}
				if(dBnfNum != tArrLen)
				{
					CError.buildErr(this, "导入的分帐户给付的帐户数与实际要给付的帐户数不等!");
					return false;
				}
				LJSGetDB tLJSGetDB = new LJSGetDB();
				tLJSGetDB.setGetNoticeNo(mGetNoticeNo);
				mLJSGetSet = tLJSGetDB.query();
				if (mLJSGetSet == null || mLJSGetSet.size()<1) {
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "conData";
					tError.errorMessage = "主表数据有误!";
					this.mErrors.addOneError(tError);
					return false;
				}
				mLJAGetSet.clear();
				tLJSGetSchema = mLJSGetSet.get(1);//用一个就够
					
				for (int k = 1; k <= tArrLen; k++) {
					String tContNo = rSSRS.GetText(k, 1);
					String tInsuredNo = rSSRS.GetText(k, 2);
					
//					String tCurSql = "select Currency, sum(GetMoney) from LJSGetEndorse where ContNo = '"+"?tContNo?"+"'  and InsuredNo = '"+"?tInsuredNo?"+"' and EndorsementNo IN (SELECT EdorNo FROM LPGrpEdorItem LPGrpEdorItem WHERE EdorAcceptNo = '?mEdorAcceptNo?') group by Currency";
					//营改增 add zhangyingfeng 2016-07-08
					//汇总 总额 总净额 总税额 税率(取一条即可，后面业务需要不能统一，暂取一条)
					String tCurSql = "select Currency, sum(GetMoney),sum(NetAmount),sum(TaxAmount),max(Tax) from LJSGetEndorse where ContNo = '"+"?tContNo?"+"'  and InsuredNo = '"+"?tInsuredNo?"+"' and EndorsementNo IN (SELECT EdorNo FROM LPGrpEdorItem LPGrpEdorItem WHERE EdorAcceptNo = '?mEdorAcceptNo?') group by Currency";
					//end zhangyingfeng 2016-07-08
					SQLwithBindVariables sbv6=new SQLwithBindVariables();
					sbv6.sql(tCurSql);
					sbv6.put("tContNo", tContNo);
					sbv6.put("tInsuredNo", tInsuredNo);
					sbv6.put("mEdorAcceptNo", mEdorAcceptNo);
					SSRS curSSRS = rExeSQL.execSQL(sbv6);
					if (curSSRS == null || curSSRS.MaxRow < 1) {
						CError.buildErr(this, "查询本次保全补退费记录失败");
						return false;
					}
					
					LPBnfDB tLPBnfDB = new LPBnfDB();
					String tBnfSql = "SELECT * FROM LPBNF WHERE EdorNo IN (SELECT EdorNo FROM LPGrpEdorItem LPGrpEdorItem WHERE  EdorAcceptNo ='"+"?mEdorAcceptNo?"+"') AND ContNo = '"+"?tContNo?"+"' AND InsuredNo = '"+"?tInsuredNo?"+"'";
					SQLwithBindVariables sbv7=new SQLwithBindVariables();
					sbv7.sql(tBnfSql);
					sbv7.put("mEdorAcceptNo", mEdorAcceptNo);
					sbv7.put("tContNo", tContNo);
					sbv7.put("tInsuredNo", tInsuredNo);
					LPBnfSet tLPBnfSet = tLPBnfDB.executeQuery(sbv7);
					if(tLPBnfSet == null || tLPBnfSet.size()<1)
					{
						CError.buildErr(this, "查询合同号"+tContNo+"客户号"+tInsuredNo+"补退费记录失败");
						return false;
					}
					String strLimit = this.getLimit();
					String temp_ActuGetNo = PubFun1.CreateMaxNo("GETNO", strLimit);
					LPBnfSchema tLPBnfSchema = tLPBnfSet.get(1);
					for(int c=1;c<=curSSRS.MaxRow;c++){
						String tCurrency =  curSSRS.GetText(c, 1);
						if(tCurrency == null || "".equals(tCurrency)){
							CError.buildErr(this, "批改补退费的币种信息不完整！");
							return false;
						}
						String tGetMoney = curSSRS.GetText(c, 2);
						double dGetMoney = Double.parseDouble(tGetMoney);
						LJAGetSchema temp_LJAGetSchema = new LJAGetSchema();
						Reflections tReflections = new Reflections();
						tReflections.transFields(temp_LJAGetSchema, tLJSGetSchema);
						temp_LJAGetSchema.setActuGetNo(temp_ActuGetNo);
						temp_LJAGetSchema.setCurrency(tCurrency);
						temp_LJAGetSchema.setOtherNo(mEdorAcceptNo);
						temp_LJAGetSchema.setOtherNoType("10");
						temp_LJAGetSchema.setPayMode(tLPBnfSchema.getRemark());//盗用remark记录付费方式
						temp_LJAGetSchema.setBankOnTheWayFlag("0");
						temp_LJAGetSchema.setBankSuccFlag("0");
						temp_LJAGetSchema.setSendBankCount(0);
						temp_LJAGetSchema.setAccName(tLPBnfSchema.getAccName());
						temp_LJAGetSchema.setShouldDate(mShouldDate);
						//营改增 add zhangyingfeng 2016-07-08
						//汇总 总额 总净额 总税额 税率(取一条即可，后面业务需要不能统一，暂取一条)
						temp_LJAGetSchema.setNetAmount(Double.parseDouble(curSSRS.GetText(c, 3)));
						temp_LJAGetSchema.setTaxAmount(Double.parseDouble(curSSRS.GetText(c, 4)));
						temp_LJAGetSchema.setTax(Double.parseDouble(curSSRS.GetText(c, 5)));
						//end zhangyingfeng 2016-07-08
						temp_LJAGetSchema.setSumGetMoney(dGetMoney);
						if(dGetMoney <= 0)
						{
							temp_LJAGetSchema.setEnterAccDate(mCurrentDate);
						}
						temp_LJAGetSchema.setBankCode(tLPBnfSchema.getBankCode());
						temp_LJAGetSchema.setBankAccNo(tLPBnfSchema.getBankAccNo());
						temp_LJAGetSchema.setDrawer(tLPBnfSchema.getName());
						temp_LJAGetSchema.setDrawerID(tLPBnfSchema.getIDNo());
						temp_LJAGetSchema.setSerialNo(tSerialNo);
						temp_LJAGetSchema.setPolicyCom(temp_LJAGetSchema.getManageCom());
						temp_LJAGetSchema.setOperator(this.getOperator());
						temp_LJAGetSchema.setMakeDate(mCurrentDate);
						temp_LJAGetSchema.setMakeTime(mCurrentTime);
						temp_LJAGetSchema.setModifyDate(mCurrentDate);
						temp_LJAGetSchema.setModifyTime(mCurrentTime);
						mLJAGetSet.add(temp_LJAGetSchema);
						
						/* 批改补退费核销转储 */
						for(int i = 1;i<=tLJSGetEndorseSet.size();i++)
						{
							tLJSGetEndorseSchema = tLJSGetEndorseSet.get(i);
							if(tLJSGetEndorseSchema.getContNo().equals(tContNo)&&tLJSGetEndorseSchema.getInsuredNo().equals(tInsuredNo)&&tLJSGetEndorseSchema.getCurrency().equals(tCurrency))
							{
								tLJAGetEndorseSchema = new LJAGetEndorseSchema();
								tReflections.transFields(tLJAGetEndorseSchema, tLJSGetEndorseSchema);
								String tRiskCode = tLJSGetEndorseSchema.getRiskCode();
								if("TB".equals(tLJSGetEndorseSchema.getFeeFinaType())&&tRiskCode!=null&&!tRiskCode.equals("000000"))
								{
									LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
									tLMRiskAppDB.setRiskCode(tRiskCode);
									if(tLMRiskAppDB.getInfo())
									{
										LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();
										if("H".equals(tLMRiskAppSchema.getRiskType())&&"1".equals(tLMRiskAppSchema.getHealthType()))
										{
											tLJAGetEndorseSchema.setFeeFinaType("CM");
										}
									}
								}
								tLJAGetEndorseSchema.setActuGetNo(temp_ActuGetNo);
								tLJAGetEndorseSchema.setCurrency(tCurrency);
								tLJAGetEndorseSchema.setOperator(this.getOperator());
								tLJAGetEndorseSchema.setMakeDate(mCurrentDate);
								tLJAGetEndorseSchema.setMakeTime(mCurrentTime);
								tLJAGetEndorseSchema.setModifyDate(mCurrentDate);
								tLJAGetEndorseSchema.setModifyTime(mCurrentTime);
								mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
							}
						}
					}
				}
				
				
			}
		}else {
			LJSGetDB tLJSGetDB = new LJSGetDB();
			tLJSGetDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetSet = tLJSGetDB.query();
			if (mLJSGetSet != null) {
				mLJAGetSet.clear();
				for (int i = 1; i <= mLJSGetSet.size(); i++) {
					tLJSGetSchema = new LJSGetSchema();
					tLJAGetSchema = new LJAGetSchema();
					tLJSGetSchema = mLJSGetSet.get(i);
					tGetForm=tLJSGetSchema.getPayMode();
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetSchema, tLJSGetSchema);
	
					tLJAGetSchema.setActuGetNo(tActuGetNo);
					tLJAGetSchema.setDrawer(mDrawer); // 领取人
					tLJAGetSchema.setDrawerID(mDrawerID); // 身份证号
					tLJAGetSchema.setShouldDate(mShouldDate);
					// tLJAGetSchema.setConfDate(mCurrentDate);
					tLJAGetSchema.setOperator(this.getOperator());
					tLJAGetSchema.setMakeDate(mCurrentDate);
					tLJAGetSchema.setMakeTime(mCurrentTime);
					tLJAGetSchema.setModifyDate(mCurrentDate);
					tLJAGetSchema.setModifyTime(mCurrentTime);
					if (tLJAGetSchema.getSumGetMoney() == 0) // 付费金额为0
					{
						tLJAGetSchema.setEnterAccDate(mCurrentDate);
					}
					mLJAGetSet.add(tLJAGetSchema);
				}
			} else {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "LJFinaConfirm";
				tError.functionName = "conData";
				tError.errorMessage = "主表数据有误!";
				this.mErrors.addOneError(tError);
				return false;
			}
	
			/* 批改补退费核销转储 */
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetEndorseSet = tLJSGetEndorseDB.query();
			if (mLJSGetEndorseSet != null) {
				// /***************************保全：年金，满期金给付相关核销*******Nicholas************************\
				LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet(); // 需要核销的年金，满期金给付记录
				LJSGetDrawSet oLJSGetDrawSet = null; // 每次查询中间变量
				LJSGetDrawDB oLJSGetDrawDB = null;
				LJABonusGetDB oLJABonusGetDB = null;
				String sGetNoticeNos = "''";
				// \***************************保全：年金，满期金给付相关核销*******Nicholas************************/
				mLJAGetEndorseSet.clear();
	
				for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
					tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					tLJAGetEndorseSchema = new LJAGetEndorseSchema();
					tLJSGetEndorseSchema = mLJSGetEndorseSet.get(i);
					if (((tLJSGetEndorseSchema.getFeeFinaType().equals("EF") || // 满期给付
							tLJSGetEndorseSchema.getFeeFinaType().equals("YF"))
							&& // 年金给付
							tLJSGetEndorseSchema.getOtherNoType().equals("5") )){
						oLJSGetDrawDB = new LJSGetDrawDB();
						oLJSGetDrawDB.setGetNoticeNo(tLJSGetEndorseSchema
								.getOtherNo());
						oLJSGetDrawDB.setPolNo(tLJSGetEndorseSchema.getPolNo());
						oLJSGetDrawDB.setDutyCode(tLJSGetEndorseSchema
								.getDutyCode());
						oLJSGetDrawSet = new LJSGetDrawSet();
						oLJSGetDrawSet = oLJSGetDrawDB.query();
						if (oLJSGetDrawSet == null || oLJSGetDrawSet.size() < 1) {
							// oLJABonusGetDB = new LJABonusGetDB();
							// oLJABonusGetDB.setGetNoticeNo(tLJSGetEndorseSchema.getOtherNo());
							// oLJABonusGetSet = oLJABonusGetDB.query();
							// if (oLJABonusGetSet != null)
							// {
							// tLJABonusGetSet.add(oLJABonusGetSet);
							// }
							sGetNoticeNos += ",'"
									+ tLJSGetEndorseSchema.getOtherNo() + "'";
						} else {
							tLJSGetDrawSet.add(oLJSGetDrawSet);
						}
						continue; // 该笔批改补退费不用核销
					}
					// \***************************保全：年金，满期金给付相关核销*******Nicholas************************/
					// ======ADD=====zhangtao=====保全回退特殊处理=====2005-10-08=========BGN==========
					if (tLJSGetEndorseSchema.getFeeOperationType().equals("RB")
							&& tLJSGetEndorseSchema.getFeeFinaType().equals("BF")) {
						/* 个人交费核销转储 */
						LJAPayPersonSchema tLJAPayPersonSchema;
						LJSPayPersonSchema tLJSPayPersonSchema;
						LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
						tLJSPayPersonDB.setPayType(tLJSGetEndorseSchema
								.getFeeOperationType());
						tLJSPayPersonDB.setContNo(tLJSGetEndorseSchema.getContNo());
						LJSPayPersonSet mLJSPayPersonSetemp = tLJSPayPersonDB.query();
						if (mLJSPayPersonSetemp != null && mLJSPayPersonSetemp.size() > 0) {
							mLJAPayPersonSet.clear();
							for (int j = 1; j <= mLJSPayPersonSetemp.size(); j++) {
								tLJAPayPersonSchema = new LJAPayPersonSchema();
								tLJSPayPersonSchema = new LJSPayPersonSchema();
								tLJSPayPersonSchema = mLJSPayPersonSetemp.get(j);
								Reflections tReflections = new Reflections();
								tReflections.transFields(tLJAPayPersonSchema,
										tLJSPayPersonSchema);
								tLJAPayPersonSchema
										.setSumActuPayMoney(tLJSPayPersonSchema
												.getSumDuePayMoney());
								tLJAPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
								tLJAPayPersonSchema.setPayNo(mActuGetNo); // 存实付号
								tLJAPayPersonSchema.setEnterAccDate(mCurrentDate);
								tLJAPayPersonSchema.setConfDate(mCurrentDate);
								tLJAPayPersonSchema.setOperator(this.getOperator());
								tLJAPayPersonSchema.setMakeDate(mCurrentDate);
								tLJAPayPersonSchema.setMakeTime(mCurrentTime);
								tLJAPayPersonSchema.setModifyDate(mCurrentDate);
								tLJAPayPersonSchema.setModifyTime(mCurrentTime);
								mLJAPayPersonSet.add(tLJAPayPersonSchema);
							}
							//add by jiaqiangli 2009-07-09 增加到删除集合里
							mLJSPayPersonSet.add(mLJSPayPersonSetemp);
							continue; // 该笔批改补退费不用核销
						}
					}
					// ======ADD=====zhangtao=====保全回退特殊处理=====2005-10-08=========END==========
					//对于红利领取,不转LJAGetEndorSe,转到LJABonusget pst on 2008-12-27
					if("DB".equals(tLJSGetEndorseSchema.getFeeOperationType()))
					{
						LJABonusGetSchema tLJABonusGetSchema = new LJABonusGetSchema();
						
						LCPolDB tLCPolDB = new LCPolDB();
						tLCPolDB.setPolNo(tLJSGetEndorseSchema.getPolNo());
						if (tLCPolDB.getInfo() == false) {
							 mErrors.addOneError(new CError("查询险种保单表失败！"));
							 return false;
						}
						LCPolSchema tLCPolSchema = tLCPolDB.getSchema();
						String tLimit = PubFun.getNoLimit(tLCPolSchema.getManageCom());
						String tSNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);//产生流水号码
						//添加实付子表-红利给付实付表
						PubFun.copySchema(tLJABonusGetSchema, tLJSGetEndorseSchema);	
						tLJABonusGetSchema.setActuGetNo(tActuGetNo);
						//
						tLJABonusGetSchema.setOtherNo(tLJSGetEndorseSchema.getPolNo()); //保存PolNo
						tLJABonusGetSchema.setOtherNoType("7");
						tLJABonusGetSchema.setPayMode(tGetForm);//内部转账??
						tLJABonusGetSchema.setBonusYear(String
								.valueOf(tLJSGetEndorseSchema.getGetDate().subSequence(0, 4)));
						tLJABonusGetSchema.setGetDate(mCurrentDate);
						//tLJABonusGetSchema.setEnterAccDate(mCurrentDate); 
						//tLJABonusGetSchema.setConfDate(mCurrentDate);
						tLJABonusGetSchema.setManageCom(tLCPolSchema.getManageCom());
						tLJABonusGetSchema.setAgentCom(tLCPolSchema.getAgentCom());
						tLJABonusGetSchema.setAgentType(tLCPolSchema.getAgentType());
						tLJABonusGetSchema.setAPPntName(tLCPolSchema.getAppntName());
						tLJABonusGetSchema.setAgentGroup(tLCPolSchema.getAgentGroup());
						tLJABonusGetSchema.setAgentCode(tLCPolSchema.getAgentCode());             				
						tLJABonusGetSchema.setSerialNo(tSNo);
						tLJABonusGetSchema.setOperator(this.getOperator());
						tLJABonusGetSchema.setState("0");
						tLJABonusGetSchema.setMakeDate(mCurrentDate);
						tLJABonusGetSchema.setMakeTime(mCurrentTime);
						tLJABonusGetSchema.setModifyDate(mCurrentDate);
						tLJABonusGetSchema.setModifyTime(mCurrentTime);
						tLJABonusGetSchema.setGrpContNo(tLCPolSchema.getGrpContNo());
						tLJABonusGetSchema.setContNo(tLCPolSchema.getContNo());
						tLJABonusGetSchema.setRiskCode(tLCPolSchema.getRiskCode());
						tLJABonusGetSchema.setPolNo(tLCPolSchema.getPolNo());
						tLJABonusGetSchema.setGrpPolNo(tLCPolSchema.getGrpPolNo());
						
						mLJABonusGetSet.add(tLJABonusGetSchema);
					}else
					{

						Reflections tReflections = new Reflections();
						tReflections.transFields(tLJAGetEndorseSchema,tLJSGetEndorseSchema);
						String tRiskCode = tLJSGetEndorseSchema.getRiskCode();
						if("TB".equals(tLJSGetEndorseSchema.getFeeFinaType())&&tRiskCode!=null&&!tRiskCode.equals("000000"))
						{
							LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
							tLMRiskAppDB.setRiskCode(tRiskCode);
							if(tLMRiskAppDB.getInfo())
							{
								LMRiskAppSchema tLMRiskAppSchema = tLMRiskAppDB.getSchema();
								if("H".equals(tLMRiskAppSchema.getRiskType())&&"1".equals(tLMRiskAppSchema.getHealthType()))
								{
									tLJAGetEndorseSchema.setFeeFinaType("CM");
								}
							}
						}
						tLJAGetEndorseSchema.setActuGetNo(tActuGetNo);
						// tLJAGetEndorseSchema.setGetConfirmDate(mCurrentDate);
						tLJAGetEndorseSchema.setOperator(this.getOperator());
						tLJAGetEndorseSchema.setMakeDate(mCurrentDate);
						tLJAGetEndorseSchema.setMakeTime(mCurrentTime);

						tLJAGetEndorseSchema.setModifyDate(mCurrentDate);
						tLJAGetEndorseSchema.setModifyTime(mCurrentTime);

						mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
					}
	
				}
	
				// /***************************保全：年金，满期金给付相关核销*******Nicholas************************\
				if (tLJSGetDrawSet != null && tLJSGetDrawSet.size() > 0) {
					mLJSGetDrawSet.add(tLJSGetDrawSet); // 删除应付
	
					Reflections tReflections;
					for (int j = 1; j <= tLJSGetDrawSet.size(); j++) {
						// “应付总表”中要删除的记录
						LJSGetSchema oLJSGetSchema = new LJSGetSchema();
						oLJSGetSchema.setGetNoticeNo(tLJSGetDrawSet.get(j)
								.getGetNoticeNo());
						mLJSGetSet.add(oLJSGetSchema);
	
						tLJSGetDrawSchema = new LJSGetDrawSchema();
						tLJAGetDrawSchema = new LJAGetDrawSchema();
						tLJSGetDrawSchema = tLJSGetDrawSet.get(j);
						tReflections = new Reflections();
						tReflections.transFields(tLJAGetDrawSchema,
								tLJSGetDrawSchema);
	
						tLJAGetDrawSchema.setGetNoticeNo(mGetNoticeNo);
						tLJAGetDrawSchema.setActuGetNo(tActuGetNo);
						// tLJAGetDrawSchema.setConfDate(mCurrentDate);
						tLJAGetDrawSchema.setOperator(this.getOperator());
						tLJAGetDrawSchema.setMakeDate(mCurrentDate);
						tLJAGetDrawSchema.setMakeTime(mCurrentTime);
	
						tLJAGetDrawSchema.setModifyDate(mCurrentDate);
						tLJAGetDrawSchema.setModifyTime(mCurrentTime);
	
						mLJAGetDrawSet.add(tLJAGetDrawSchema); // 转入实付
					}
				}
	//			String sql = "select * from ljabonusget where getnoticeno in ("
	//					+ sGetNoticeNos + ")";
	//			oLJABonusGetDB = new LJABonusGetDB();
	//			mDelLJABonusGetSet = oLJABonusGetDB.executeQuery(sql);
	//			if (mDelLJABonusGetSet != null && mDelLJABonusGetSet.size() > 0) {
	//				LJABonusGetSchema tLJABonusGetSchema;
	//				for (int j = 1; j <= mDelLJABonusGetSet.size(); j++) {
	//					tLJABonusGetSchema = mDelLJABonusGetSet.get(j).getSchema();
	//
	//					// “实付总表”中要删除的记录
	//					LJAGetSchema oLJAGetSchema = new LJAGetSchema();
	//					oLJAGetSchema.setActuGetNo(mDelLJABonusGetSet.get(j)
	//							.getActuGetNo());
	//					mDelLJAGetSet.add(oLJAGetSchema);
	//
	//					// 更新红利实付数据
	//					tLJABonusGetSchema.setActuGetNo(tActuGetNo);
	//					tLJABonusGetSchema.setGetNoticeNo(mGetNoticeNo);
	//					tLJABonusGetSchema.setState("1");
	//					tLJABonusGetSchema.setMakeDate(mCurrentDate);
	//					tLJABonusGetSchema.setMakeTime(mCurrentTime);
	//					tLJABonusGetSchema.setModifyDate(mCurrentDate);
	//					tLJABonusGetSchema.setModifyTime(mCurrentTime);
	//					mLJABonusGetSet.add(tLJABonusGetSchema);
	//
	//				}
	//			}
				// \***************************保全：年金，满期金给付相关核销*******Nicholas************************/
	
				// 修改累计保费，add by Minim at 2003-10-17
	
				// if (!modifySumPrem(mLJAGetEndorseSet))
				// {
				// return false;
				// }
			}
		}

		return true;
	}

	/**
	 * 修改累计保费
	 * 
	 * @param tLJAGetEndorseSet
	 * @return
	 */
	private boolean modifySumPrem(LJAGetEndorseSet tLJAGetEndorseSet) {
		if (tLJAGetEndorseSet == null || tLJAGetEndorseSet.size() < 1) {
			// 没有批改补退费记录
			return true;
		}
		try {
			logger.debug("\nStart modify SumPrem");

			// modify lpprem
			LPPremDB tLPPremDB = new LPPremDB();
			tLPPremDB.setEdorNo(tLJAGetEndorseSet.get(1).getEndorsementNo());
			tLPPremDB.setEdorType(tLJAGetEndorseSet.get(1)
					.getFeeOperationType());
			tLPPremDB.setContNo(tLJAGetEndorseSet.get(1).getContNo());
			tLPPremDB.setPolNo(tLJAGetEndorseSet.get(1).getPolNo());
			LPPremSet tLPPremSet = tLPPremDB.query();

			if (tLPPremSet != null && tLPPremSet.size() > 1) {
				LCPremDB tLCPremDB = new LCPremDB();
				tLCPremDB.setPolNo(tLJAGetEndorseSet.get(1).getPolNo());
				LCPremSet tLCPremSet = tLCPremDB.query();

				for (int j = 0; j < tLPPremSet.size(); j++) {
					for (int i = 0; i < tLCPremSet.size(); i++) {
						if (tLPPremSet.get(j + 1).getContNo().equals(
								tLCPremSet.get(i + 1).getContNo())
								&& tLPPremSet.get(j + 1).getPolNo().equals(
										tLCPremSet.get(i + 1).getPolNo())
								&& tLPPremSet.get(j + 1).getDutyCode().equals(
										tLCPremSet.get(i + 1).getDutyCode())
								&& tLPPremSet.get(j + 1).getPayPlanCode()
										.equals(
												tLCPremSet.get(i + 1)
														.getPayPlanCode())) {
							tLPPremSet
									.get(j + 1)
									.setSumPrem(
											PubFun
													.setPrecision(
															tLCPremSet
																	.get(j + 1)
																	.getSumPrem()
																	+ (tLPPremSet
																			.get(
																					j + 1)
																			.getPrem() - tLCPremSet
																			.get(
																					j + 1)
																			.getPrem()),
															"0.00"));
						}
					}
				}
				mLPPremSet.add(tLPPremSet);
			}
			// modify lpduty
			LPDutyDB tLPDutyDB = new LPDutyDB();
			tLPDutyDB.setEdorNo(tLJAGetEndorseSet.get(1).getEndorsementNo());
			tLPDutyDB.setEdorType(tLJAGetEndorseSet.get(1)
					.getFeeOperationType());
			tLPDutyDB.setContNo(tLJAGetEndorseSet.get(1).getContNo());
			tLPDutyDB.setPolNo(tLJAGetEndorseSet.get(1).getPolNo());
			LPDutySet tLPDutySet = tLPDutyDB.query();

			if (tLPDutySet != null && tLPDutySet.size() > 1) {
				LCDutyDB tLCDutyDB = new LCDutyDB();
				tLCDutyDB.setPolNo(tLJAGetEndorseSet.get(1).getPolNo());
				LCDutySet tLCDutySet = tLCDutyDB.query();

				for (int j = 0; j < tLPDutySet.size(); j++) {
					for (int i = 0; i < tLCDutySet.size(); i++) {
						if (tLPDutySet.get(j + 1).getContNo().equals(
								tLCDutySet.get(i + 1).getContNo())
								&& tLPDutySet.get(j + 1).getPolNo().equals(
										tLCDutySet.get(i + 1).getPolNo())
								&& tLPDutySet.get(j + 1).getDutyCode().equals(
										tLCDutySet.get(i + 1).getDutyCode())) {
							tLPDutySet
									.get(j + 1)
									.setSumPrem(
											PubFun
													.setPrecision(
															tLCDutySet
																	.get(i + 1)
																	.getSumPrem()
																	+ (tLPDutySet
																			.get(
																					j + 1)
																			.getPrem() - tLCDutySet
																			.get(
																					j + 1)
																			.getPrem()),
															"0.00"));
						}
					}
				}
				mLPDutySet.add(tLPDutySet);
			}
			// modify lppol
			LPPolDB tLPPolDB = new LPPolDB();
			tLPPolDB.setEdorNo(tLJAGetEndorseSet.get(1).getEndorsementNo());
			tLPPolDB
					.setEdorType(tLJAGetEndorseSet.get(1).getFeeOperationType());
			tLPPolDB.setContNo(tLJAGetEndorseSet.get(1).getContNo());
			tLPPolDB.setPolNo(tLJAGetEndorseSet.get(1).getPolNo());
			LPPolSet tLPPolSet = tLPPolDB.query();

			if (tLPPolSet != null && tLPPolSet.size() > 1) {
				LCPolDB tLCPolDB = new LCPolDB();
				tLCPolDB.setPolNo(tLJAGetEndorseSet.get(1).getPolNo());
				LCPolSet tLCPolSet = tLCPolDB.query();

				for (int j = 0; j < tLPPolSet.size(); j++) {
					for (int i = 0; i < tLCPolSet.size(); i++) {
						if (tLPPolSet.get(j + 1).getContNo().equals(
								tLCPolSet.get(i + 1).getContNo())
								&& tLPPolSet.get(j + 1).getPolNo().equals(
										tLCPolSet.get(i + 1).getPolNo())) {
							tLPPolSet
									.get(j + 1)
									.setSumPrem(
											PubFun
													.setPrecision(
															tLCPolSet
																	.get(i + 1)
																	.getSumPrem()
																	+ (tLPPolSet
																			.get(
																					j + 1)
																			.getPrem() - tLCPolSet
																			.get(
																					j + 1)
																			.getPrem()),
															"0.00"));
						}
					}
				}
				mLPPolSet.add(tLPPolSet);
			}

			// modify lpCont
			LPContDB tLPContDB = new LPContDB();
			tLPContDB.setEdorNo(tLJAGetEndorseSet.get(1).getEndorsementNo());
			tLPContDB.setEdorType(tLJAGetEndorseSet.get(1)
					.getFeeOperationType());
			tLPContDB.setContNo(tLJAGetEndorseSet.get(1).getContNo());
			LPContSet tLPContSet = tLPContDB.query();

			if (tLPContSet != null && tLPContSet.size() > 1) {
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(tLJAGetEndorseSet.get(1).getContNo());
				LCContSet tLCContSet = tLCContDB.query();

				for (int j = 0; j < tLPContSet.size(); j++) {
					for (int i = 0; i < tLCContSet.size(); i++) {
						if (tLPContSet.get(j + 1).getContNo().equals(
								tLCContSet.get(i + 1).getContNo())) {
							tLPContSet
									.get(j + 1)
									.setSumPrem(
											PubFun
													.setPrecision(
															tLCContSet
																	.get(i + 1)
																	.getSumPrem()
																	+ (tLPContSet
																			.get(
																					j + 1)
																			.getPrem() - tLCContSet
																			.get(
																					j + 1)
																			.getPrem()),
															"0.00"));
						}
					}
				}
				mLPContSet.add(tLPContSet);
			}

			// modify lpGrppol
			if (tLJAGetEndorseSet.get(1).getRiskCode() != null
					&& !tLJAGetEndorseSet.get(1).getRiskCode().equals("")) {
				LPGrpPolDB tLPGrpPolDB = new LPGrpPolDB();
				tLPGrpPolDB.setEdorNo(tLJAGetEndorseSet.get(1)
						.getEndorsementNo());
				tLPGrpPolDB.setEdorType(tLJAGetEndorseSet.get(1)
						.getFeeOperationType());
				tLPGrpPolDB.setGrpPolNo(tLJAGetEndorseSet.get(1).getGrpPolNo());
				tLPGrpPolDB.setRiskCode(tLJAGetEndorseSet.get(1).getRiskCode());
				LPGrpPolSet tLPGrpPolSet = tLPGrpPolDB.query();

				if (tLPGrpPolSet != null && tLPGrpPolSet.size() > 1) {
					LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
					tLCGrpPolDB.setGrpPolNo(tLJAGetEndorseSet.get(1)
							.getGrpPolNo());
					tLCGrpPolDB.setRiskCode(tLJAGetEndorseSet.get(1)
							.getRiskCode());
					LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();

					for (int j = 0; j < tLPGrpPolSet.size(); j++) {
						for (int i = 0; i < tLCGrpPolSet.size(); i++) {
							if (tLPGrpPolSet.get(j + 1).getGrpContNo().equals(
									tLCGrpPolSet.get(i + 1).getGrpContNo())
									&& tLPGrpPolSet.get(j + 1).getGrpPolNo()
											.equals(
													tLCGrpPolSet.get(i + 1)
															.getGrpPolNo())) {
								tLPGrpPolSet
										.get(j + 1)
										.setSumPrem(
												PubFun
														.setPrecision(
																tLCGrpPolSet
																		.get(
																				i + 1)
																		.getSumPrem()
																		+ (tLPGrpPolSet
																				.get(
																						j + 1)
																				.getPrem() - tLCGrpPolSet
																				.get(
																						j + 1)
																				.getPrem()),
																"0.00"));
							}
						}
					}
					mLPGrpPolSet.add(tLPGrpPolSet);
				}
			}

			// modify lpGrpCont
			LPGrpContDB tLPGrpContDB = new LPGrpContDB();
			tLPGrpContDB.setEdorNo(tLJAGetEndorseSet.get(1).getEndorsementNo());
			tLPGrpContDB.setEdorType(tLJAGetEndorseSet.get(1)
					.getFeeOperationType());
			tLPGrpContDB.setGrpContNo(tLJAGetEndorseSet.get(1).getGrpContNo());
			LPGrpContSet tLPGrpContSet = tLPGrpContDB.query();

			if (tLPGrpContSet != null && tLPGrpContSet.size() > 1) {
				LCGrpContDB tLCGrpContDB = new LCGrpContDB();
				tLCGrpContDB.setGrpContNo(tLJAGetEndorseSet.get(1)
						.getGrpContNo());
				LCGrpContSet tLCGrpContSet = tLCGrpContDB.query();

				for (int j = 0; j < tLPGrpContSet.size(); j++) {
					for (int i = 0; i < tLCGrpContSet.size(); i++) {
						if (tLPGrpContSet.get(j + 1).getGrpContNo().equals(
								tLCGrpContSet.get(i + 1).getGrpContNo())) {
							tLPGrpContSet.get(j + 1).setSumPrem(
									PubFun.setPrecision(tLCGrpContSet
											.get(i + 1).getSumPrem()
											+ (tLPGrpContSet.get(j + 1)
													.getPrem() - tLCGrpContSet
													.get(j + 1).getPrem()),
											"0.00"));
						}
					}
				}
				mLPGrpContSet.add(tLPGrpContSet);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "modifySumPrem";
			tError.errorMessage = "修改累计保费有误!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 提交累计保费修改，共用一个数据库连接
	 * 
	 * @param conn
	 * @throws Exception
	 */
	private void commitModifySumPrem(Connection conn) throws Exception {
		HashMap map = new HashMap();
		map.put(mLPPremSet, "UPDATE");
		map.put(mLPDutySet, "UPDATE");
		map.put(mLPPolSet, "UPDATE");
		VData tVData = new VData();
		tVData.add(map);

		LJFinaConfirmBLS tLJFinaConfirmBLS = new LJFinaConfirmBLS();
		// 不在LJFinaConfirmBLS中提交数据
		tLJFinaConfirmBLS.setCommitStatus(false);
		// 共用一个数据库连接
		tLJFinaConfirmBLS.setConnection(conn);
		if (tLJFinaConfirmBLS.submitData(tVData, "") == false) {
			// @@错误处理
			this.mErrors.copyAllErrors(tLJFinaConfirmBLS.mErrors);
			throw new Exception("commitModifySumPrem Faile");
		}
	}

	/**
	 * 按照给付类型准备转储数据
	 * 
	 * @return true or false
	 */
	private boolean conLJAGetSerials(String aType) {
		String tActuGetNo;

		/* 应付总表 */
		LJSGetSchema tLJSGetSchema = new LJSGetSchema();

		/* 赔付应付表 */
		LJSGetClaimSchema tLJSGetClaimSchema = new LJSGetClaimSchema();

		/* 其他退费应付表 */
		LJSGetOtherSchema tLJSGetOtherSchema = new LJSGetOtherSchema();

		/* 暂交费退费应付表 */
		LJSGetTempFeeSchema tLJSGetTempFeeSchema = new LJSGetTempFeeSchema();

		/* 给付表(生存领取_应付) */
		LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();

		/* 批改补退费表（应收/应付） */
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		/* 实付总表 */
		mLJAGetSet = new LJAGetSet();
		LJAGetSchema tLJAGetSchema = new LJAGetSchema();

		/* 赔付实付表 */
		LJAGetClaimSchema tLJAGetClaimSchema = new LJAGetClaimSchema();
		mLJAGetClaimSet = new LJAGetClaimSet();

		/* 其他退费实付表 */
		LJAGetOtherSchema tLJAGetOtherSchema = new LJAGetOtherSchema();
		mLJAGetOtherSet = new LJAGetOtherSet();

		/* 暂交费退费实付表 */
		LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
		mLJAGetTempFeeSet = new LJAGetTempFeeSet();

		/* 给付表(生存领取_实付) */
		LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
		mLJAGetDrawSet = new LJAGetDrawSet();

		/* 批改补退费表（实收/实付） */
		LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
		mLJAGetEndorseSet = new LJAGetEndorseSet();

		/* 得到实付号码 */
		tActuGetNo = this.getActuGetNo();

		/* 赔付应付核销转储 */
		if (aType.equals("LP")) {
			LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
			tLJSGetClaimDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetClaimSet = tLJSGetClaimDB.query();
			if (mLJSGetClaimSet != null) {

				mLJAGetClaimSet.clear();
				for (int i = 1; i <= mLJSGetClaimSet.size(); i++) {
					tLJSGetClaimSchema = new LJSGetClaimSchema();
					tLJAGetClaimSchema = new LJAGetClaimSchema();
					tLJSGetClaimSchema = mLJSGetClaimSet.get(i);
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetClaimSchema,
							tLJSGetClaimSchema);

					tLJAGetClaimSchema.setActuGetNo(tActuGetNo);
					tLJAGetClaimSchema.setOperator(this.getOperator());
					tLJAGetClaimSchema.setMakeDate(mCurrentDate);
					tLJAGetClaimSchema.setMakeTime(mCurrentTime);
					tLJAGetClaimSchema.setModifyDate(mCurrentDate);
					tLJAGetClaimSchema.setModifyTime(mCurrentTime);

					mLJAGetClaimSet.add(tLJAGetClaimSchema);
				}
			}
		}
		/* 其他退费核销转储 */
		if (aType.equals("OT")) {
			LJSGetOtherDB tLJSGetOtherDB = new LJSGetOtherDB();
			tLJSGetOtherDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetOtherSet = tLJSGetOtherDB.query();
			if (mLJSGetOtherSet != null) {

				mLJAGetOtherSet.clear();
				for (int i = 1; i <= mLJSGetOtherSet.size(); i++) {
					tLJSGetOtherSchema = new LJSGetOtherSchema();
					tLJAGetOtherSchema = new LJAGetOtherSchema();
					tLJSGetOtherSchema = mLJSGetOtherSet.get(i);
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetOtherSchema,
							tLJSGetOtherSchema);

					tLJAGetOtherSchema.setActuGetNo(tActuGetNo);
					tLJAGetOtherSchema.setOperator(this.getOperator());
					tLJAGetOtherSchema.setMakeDate(mCurrentDate);
					tLJAGetOtherSchema.setMakeTime(mCurrentTime);
					tLJAGetOtherSchema.setModifyDate(mCurrentDate);
					tLJAGetOtherSchema.setModifyTime(mCurrentTime);

					mLJAGetOtherSet.add(tLJAGetOtherSchema);
				}
			}
		}
		/* 暂交费退费核销转储 */
		if (aType.equals("TP")) {
			LJSGetTempFeeDB tLJSGetTempFeeDB = new LJSGetTempFeeDB();
			tLJSGetTempFeeDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetTempFeeSet = tLJSGetTempFeeDB.query();
			if (mLJSGetTempFeeSet != null) {

				mLJAGetTempFeeSet.clear();
				for (int i = 1; i <= mLJSGetTempFeeSet.size(); i++) {
					tLJSGetTempFeeSchema = new LJSGetTempFeeSchema();
					tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
					tLJSGetTempFeeSchema = mLJSGetTempFeeSet.get(i);
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetTempFeeSchema,
							tLJSGetTempFeeSchema);

					tLJAGetTempFeeSchema.setActuGetNo(tActuGetNo);
					tLJAGetTempFeeSchema.setOperator(this.getOperator());
					tLJAGetTempFeeSchema.setMakeDate(mCurrentDate);
					tLJAGetTempFeeSchema.setMakeTime(mCurrentTime);
					tLJAGetTempFeeSchema.setModifyDate(mCurrentDate);
					tLJAGetTempFeeSchema.setModifyTime(mCurrentTime);

					mLJAGetTempFeeSet.add(tLJAGetTempFeeSchema);
				}
			}
		}

		/* 给付表(生存领取_应付)核销转储 */
		if (aType.equals("DR")) {
			LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
			tLJSGetDrawDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetDrawSet = tLJSGetDrawDB.query();
			if (mLJSGetDrawSet != null) {

				mLJAGetDrawSet.clear();
				for (int i = 1; i <= mLJSGetDrawSet.size(); i++) {
					tLJSGetDrawSchema = new LJSGetDrawSchema();
					tLJAGetDrawSchema = new LJAGetDrawSchema();
					tLJSGetDrawSchema = mLJSGetDrawSet.get(i);
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetDrawSchema,
							tLJSGetDrawSchema);

					tLJAGetDrawSchema.setActuGetNo(tActuGetNo);
					tLJAGetDrawSchema.setOperator(this.getOperator());
					tLJAGetDrawSchema.setMakeDate(mCurrentDate);
					tLJAGetDrawSchema.setMakeTime(mCurrentTime);
					tLJAGetDrawSchema.setModifyDate(mCurrentDate);
					tLJAGetDrawSchema.setModifyTime(mCurrentTime);

					mLJAGetDrawSet.add(tLJAGetDrawSchema);
				}
			}
		}
		/* 批改补退费核销转储 */
		if (aType.equals("PG")) {
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetEndorseSet = tLJSGetEndorseDB.query();
			if (mLJSGetEndorseSet != null) {

				mLJAGetEndorseSet.clear();
				for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
					tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					tLJAGetEndorseSchema = new LJAGetEndorseSchema();
					tLJSGetEndorseSchema = mLJSGetEndorseSet.get(i);
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetEndorseSchema,
							tLJSGetEndorseSchema);
					tLJAGetEndorseSchema.setActuGetNo(tActuGetNo);
					tLJAGetEndorseSchema.setOperator(this.getOperator());
					tLJAGetEndorseSchema.setMakeDate(mCurrentDate);
					tLJAGetEndorseSchema.setMakeTime(mCurrentTime);
					tLJAGetEndorseSchema.setModifyDate(mCurrentDate);
					tLJAGetEndorseSchema.setModifyTime(mCurrentTime);

					mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
				}

				// 修改累计保费，add by Minim at 2003-10-17
				// if (!modifySumPrem(mLJAGetEndorseSet))
				// {
				// return false;
				// }
			}
		}
		/* 应付总表核销转储 */
		LJSGetDB tLJSGetDB = new LJSGetDB();
		tLJSGetDB.setGetNoticeNo(mGetNoticeNo);
		mLJSGetSet = tLJSGetDB.query();
		if (mLJSGetSet != null) {

			mLJAGetSet.clear();
			for (int i = 1; i <= mLJSGetSet.size(); i++) {
				tLJSGetSchema = new LJSGetSchema();
				tLJAGetSchema = new LJAGetSchema();
				tLJSGetSchema = mLJSGetSet.get(i);
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLJAGetSchema, tLJSGetSchema);

				tLJAGetSchema.setActuGetNo(tActuGetNo);
				tLJAGetSchema.setOperator(this.getOperator());
				tLJAGetSchema.setMakeDate(mCurrentDate);
				tLJAGetSchema.setMakeTime(mCurrentTime);
				tLJAGetSchema.setModifyDate(mCurrentDate);
				tLJAGetSchema.setModifyTime(mCurrentTime);

				mLJAGetSet.add(tLJAGetSchema);
			}
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "conData";
			tError.errorMessage = "主表数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 保存实付数据
	 * 
	 * @return
	 */
	public boolean saveLJAGetSerials() {
		String tActuGetNo;
		/* 实付总表 */
		LJAGetSet tLJAGetSet = new LJAGetSet();
		LJAGetDBSet tLJAGetDBSet;

		/* 赔付实付表 */
		LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
		LJAGetClaimDBSet tLJAGetClaimDBSet;

		/* 其他退费实付表 */
		LJAGetOtherSet tLJAGetOtherSet = new LJAGetOtherSet();
		LJAGetOtherDBSet tLJAGetOtherDBSet;

		/* 暂交费退费实付表 */
		LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
		LJAGetTempFeeDBSet tLJAGetTempFeeDBSet;

		/* 给付表(生存领取_实付) */
		LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
		LJAGetDrawDBSet tLJAGetDrawDBSet;

		/* 批改补退费表（实收/实付） */
		LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
		LJAGetEndorseDBSet tLJAGetEndorseDBSet;

		/* save data */
		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tActuGetNo = this.getActuGetNo();
		try {
			conn.setAutoCommit(false);

			tLJAGetClaimDBSet = new LJAGetClaimDBSet(conn);
			tLJAGetClaimDBSet.set(mLJAGetClaimSet);

			if (tLJAGetClaimDBSet != null && tLJAGetClaimDBSet.size() > 0) {
				if (!tLJAGetClaimDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "赔付纪录保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB(conn);
				tLJSGetClaimDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSGetClaimDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "赔付纪录删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			tLJAGetOtherDBSet = new LJAGetOtherDBSet(conn);
			tLJAGetOtherDBSet.set(mLJAGetOtherSet);

			if (tLJAGetOtherDBSet != null && tLJAGetOtherDBSet.size() > 0) {
				if (!tLJAGetOtherDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "其他退费纪录保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSGetOtherDB tLJSGetOtherDB = new LJSGetOtherDB(conn);
				tLJSGetOtherDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSGetOtherDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "其他退费纪录删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			tLJAGetTempFeeDBSet = new LJAGetTempFeeDBSet(conn);
			tLJAGetTempFeeDBSet.set(mLJAGetTempFeeSet);

			if (tLJAGetTempFeeDBSet != null && tLJAGetTempFeeDBSet.size() > 0) {
				if (!tLJAGetTempFeeDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "暂交费退费纪录保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSGetTempFeeDB tLJSGetTempFeeDB = new LJSGetTempFeeDB(conn);
				tLJSGetTempFeeDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSGetTempFeeDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "暂交费退费纪录删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			tLJAGetDrawDBSet = new LJAGetDrawDBSet(conn);
			tLJAGetDrawDBSet.set(mLJAGetDrawSet);

			if (tLJAGetDrawDBSet != null && tLJAGetDrawDBSet.size() > 0) {
				if (!tLJAGetDrawDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "生存给付纪录保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB(conn);
				tLJSGetDrawDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSGetDrawDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "生存给付纪录删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			tLJAGetEndorseDBSet = new LJAGetEndorseDBSet(conn);
			tLJAGetEndorseDBSet.set(mLJAGetEndorseSet);

			if (tLJAGetEndorseDBSet != null && tLJAGetEndorseDBSet.size() > 0) {
				if (!tLJAGetEndorseDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "保全交退费纪录保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB(conn);
				tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSGetEndorseDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "保全交退费纪录删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			tLJAGetDBSet = new LJAGetDBSet(conn);
			tLJAGetDBSet.set(mLJAGetSet);

			if (tLJAGetDBSet != null && tLJAGetDBSet.size() > 0) {
				if (!tLJAGetDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "实付保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSGetDB tLJSGetDB = new LJSGetDB(conn);
				tLJSGetDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSGetDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "实付纪录保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			commitModifySumPrem(conn);

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 准备转储交费数据
	 * 
	 * @return true or false
	 */
	private boolean conLJAPaySerials() {
		String tActuGetNo;

		/* 应收总表 */
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();

		/* 批改补退费表（应收/应付） */
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		/* 实收总表 */
		mLJAPaySet = new LJAPaySet();
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();

		/* 批改补退费表（实收/实付） */
		LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
		mLJAGetEndorseSet = new LJAGetEndorseSet();

		/* 得到实付号码 */
		String tPayNo = mPayNo;

		/* 批改补退费核销转储 */
		boolean blCancelFlag = false;
		LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
		tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
		mLJSGetEndorseSet = tLJSGetEndorseDB.query();
		if (mLJSGetEndorseSet != null) {
			mLJAGetEndorseSet.clear();
			for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
				tLJSGetEndorseSchema = new LJSGetEndorseSchema();
				tLJAGetEndorseSchema = new LJAGetEndorseSchema();
				tLJSGetEndorseSchema = mLJSGetEndorseSet.get(i);
				// ======ADD=====zhangtao=====保全回退特殊处理=====2005-10-08=========BGN==========
				if (tLJSGetEndorseSchema.getFeeOperationType().equals("RB")) {
					tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					tLJAGetEndorseSchema = new LJAGetEndorseSchema();
					tLJSGetEndorseSchema = mLJSGetEndorseSet.get(i);
					if ((tLJSGetEndorseSchema.getFeeFinaType().equals("EF") || // 满期给付
							tLJSGetEndorseSchema.getFeeFinaType().equals("YF"))
							&& // 年金给付
							tLJSGetEndorseSchema.getOtherNoType().equals("5")) {
						LJSGetDrawDB oLJSGetDrawDB = new LJSGetDrawDB();
						oLJSGetDrawDB.setGetNoticeNo(tLJSGetEndorseSchema
								.getOtherNo());
						oLJSGetDrawDB.setPolNo(tLJSGetEndorseSchema.getPolNo());
						oLJSGetDrawDB.setDutyCode(tLJSGetEndorseSchema
								.getDutyCode());
						LJSGetDrawSet tLJSGetDrawSet = oLJSGetDrawDB.query();
						if (tLJSGetDrawSet != null && tLJSGetDrawSet.size() > 0) {
							mLJSGetDrawSet.add(tLJSGetDrawSet); // 删除应付

							Reflections tReflections;
							for (int j = 1; j <= tLJSGetDrawSet.size(); j++) {
								LJSGetDrawSchema tLJSGetDrawSchema = new LJSGetDrawSchema();
								LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
								tLJSGetDrawSchema = tLJSGetDrawSet.get(j);
								tReflections = new Reflections();
								tReflections.transFields(tLJAGetDrawSchema,
										tLJSGetDrawSchema);
								tLJAGetDrawSchema.setGetNoticeNo(mGetNoticeNo);
								tLJAGetDrawSchema.setActuGetNo(mPayNo);
								// tLJAGetDrawSchema.setConfDate(mCurrentDate);
								tLJAGetDrawSchema.setOperator(this
										.getOperator());
								tLJAGetDrawSchema.setMakeDate(mCurrentDate);
								tLJAGetDrawSchema.setMakeTime(mCurrentTime);
								tLJAGetDrawSchema.setModifyDate(mCurrentDate);
								tLJAGetDrawSchema.setModifyTime(mCurrentTime);

								// XinYQ modified on 2007-04-13 : 如果存在两条 AG
								// 的领取记录
								// PolNo, DutyCode, GetDutyKind 一样, 只是
								// GetDutyCode 不一样, 就会主键冲突
								// LJSGetEndorse 没有 GetDutyCode, 只能先判断再添加更新
								if (mLJAGetDrawSet == null
										|| mLJAGetDrawSet.size() <= 0) {
									mLJAGetDrawSet.add(tLJAGetDrawSchema); // 如果是第一笔记录,
																			// 直接转入实付
								} else {
									boolean isAlreadyAdded = false;
									for (int k = 1; k <= mLJAGetDrawSet.size(); k++) {
										LJAGetDrawSchema tTmpLJAGetDrawSchema = new LJAGetDrawSchema();
										tTmpLJAGetDrawSchema = mLJAGetDrawSet
												.get(k);
										if (tTmpLJAGetDrawSchema
												.getActuGetNo()
												.equals(
														tLJAGetDrawSchema
																.getActuGetNo())
												&& tTmpLJAGetDrawSchema
														.getPolNo()
														.equals(
																tLJAGetDrawSchema
																		.getPolNo())
												&& tTmpLJAGetDrawSchema
														.getDutyCode()
														.equals(
																tLJAGetDrawSchema
																		.getDutyCode())
												&& tTmpLJAGetDrawSchema
														.getGetDutyKind()
														.equals(
																tLJAGetDrawSchema
																		.getGetDutyKind())
												&& tTmpLJAGetDrawSchema
														.getGetDutyCode()
														.equals(
																tLJAGetDrawSchema
																		.getGetDutyCode())) {
											isAlreadyAdded = true;
											break;
										}
										tTmpLJAGetDrawSchema = null;
									}
									if (!isAlreadyAdded) {
										mLJAGetDrawSet.add(tLJAGetDrawSchema); // 根据主键判断不重复之后再转入实付
									}
								}
							}
						}
						continue; // 该笔批改补退费不用核销
					}
				}
				// ======ADD=====zhangtao=====保全回退特殊处理=====2005-10-08=========END==========

				// =====ADD=====zhangtao=======2005-06-29========================BGN=============

				// **************************************************************
				// 以下各个保全项目的批改补退费核销时有一些特殊要求：
				// 某些批改补退费条目要转到LJAPayPerson，而不转到LJAGetEndorse
				// 注意：需要转到LJAPayPerson的数据当初在生成时会在LJSPayPerson和LJSGetEndorse
				// 同时插入记录，所以核销时LJSGetEndorse的数据不用转储
				// **************************************************************

				String sEdorType = tLJSGetEndorseSchema.getFeeOperationType();
				if (sEdorType.equals("RE")
						|| sEdorType.equals("NS")
						|| sEdorType.equals("NI") // 保单复效,新增附加险,新增被保险人
						|| sEdorType.equals("NR") // 团险被保险人添加新险种
						|| sEdorType.equals("AA") || sEdorType.equals("WA")
						|| sEdorType.equals("WS") ||  sEdorType.equals("IP") || sEdorType.equals("PM")||sEdorType.equals("HJ")||sEdorType.equals("WP") /*|| sEdorType.equals("FM")*/) // 附加险加保,整单加保,整单新增附加险,提前交清,交费间隔变更,交费年期变更by jiaqiangli 2008-09-23
					//comment by jiaqiangli 2008-10-30 PM交费间隔变更若有补费，则在同一个保单年度内，插入ljapayperson 销售系统需要提取数据
					//PM现程序只能支持短变长
				{
					if (tLJSGetEndorseSchema.getFeeFinaType().equals("BF")||tLJSGetEndorseSchema.getFeeFinaType().equals("ZK")) {
						blCancelFlag = true;
					} else {
						blCancelFlag = false;
					}
				} else {
					blCancelFlag = false;
				}
				if (blCancelFlag) {
					/* 个人交费核销转储 */
					String Sql;
					SQLwithBindVariables sqlbv=new SQLwithBindVariables();
					LJAPayPersonSchema tLJAPayPersonSchema;
					LJSPayPersonSchema tLJSPayPersonSchema;
					LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
					Sql = "select * from ljspayperson where 1=1"							
							+ " and contno = '"
							+ "?contno?" + "'"
							+ " and polno = '"
							+ "?polno?" + "'"
							+ " and dutycode = '"
							+ "?dutycode?" + "'"
							+ " and payplancode = '"
							+ "?payplancode?" + "'";
				
					sqlbv.put("contno", tLJSGetEndorseSchema.getContNo());
					sqlbv.put("polno", tLJSGetEndorseSchema.getPolNo());
					sqlbv.put("dutycode", tLJSGetEndorseSchema.getDutyCode());
					sqlbv.put("payplancode", tLJSGetEndorseSchema.getPayPlanCode());
					if (sEdorType.equals("RE")||sEdorType.equals("HJ")) {
						Sql += " and paycount = ?paycount?";
					
						sqlbv.put("paycount", tLJSGetEndorseSchema.getSubFeeOperationType().trim().substring(4));
					}
					if (tLJSGetEndorseSchema.getFeeFinaType().equals("ZK")){
						Sql += " and paytype = '?paytype?'";
					
					sqlbv.put("paytype",tLJSGetEndorseSchema.getSubFeeOperationType().trim().substring(0,3));
					}else{
						Sql += " and paytype = '?paytype?'";
					
					sqlbv.put("paytype",tLJSGetEndorseSchema.getFeeOperationType());
					}
					sqlbv.sql(Sql);
					LJSPayPersonSet mLJSPayPersonSetemp = tLJSPayPersonDB.executeQuery(sqlbv);
					if (mLJSPayPersonSetemp != null && mLJSPayPersonSetemp.size() > 0) {
						LMRiskAppDB tLMRiskAppDB = null;
						LMRiskAppSchema tLMRiskAppSchema = null;
						for (int j = 1; j <= mLJSPayPersonSetemp.size(); j++) {
							tLJAPayPersonSchema = new LJAPayPersonSchema();
							tLJSPayPersonSchema = new LJSPayPersonSchema();
							tLJSPayPersonSchema = mLJSPayPersonSetemp.get(j);
							tLMRiskAppDB = new LMRiskAppDB();
							tLMRiskAppDB.setRiskCode(tLJSPayPersonSchema.getRiskCode());
							if(!tLMRiskAppDB.getInfo())
							{
								CError.buildErr(this, "查询险种承保定义失败");
								return false;
							}
							tLMRiskAppSchema = tLMRiskAppDB.getSchema();
							
							Reflections tReflections = new Reflections();
							tReflections.transFields(tLJAPayPersonSchema,
									tLJSPayPersonSchema);

							tLJAPayPersonSchema
									.setSumActuPayMoney(tLJSPayPersonSchema
											.getSumDuePayMoney());
							tLJAPayPersonSchema.setGetNoticeNo(mGetNoticeNo);
							tLJAPayPersonSchema.setPayNo(tPayNo);
							//MS团险康福类的交费类别为TM,其它的为ZC
							if(!tLJSPayPersonSchema.getPayType().equals(tLJSGetEndorseSchema.getFeeOperationType()))
							{
								tLJAPayPersonSchema.setPayType(tLJSPayPersonSchema.getPayType());
							}
							else if("H".equals(tLMRiskAppSchema.getRiskType())&&"1".equals(tLMRiskAppSchema.getHealthType()))
							{
								tLJAPayPersonSchema.setPayType("TM");
							}else {
								tLJAPayPersonSchema.setPayType("ZC");
							}
							tLJAPayPersonSchema.setEnterAccDate(mEnterAccDate);
							tLJAPayPersonSchema.setConfDate(mCurrentDate);
							tLJAPayPersonSchema.setOperator(this.getOperator());
							tLJAPayPersonSchema.setMakeDate(mCurrentDate);
							tLJAPayPersonSchema.setMakeTime(mCurrentTime);
							tLJAPayPersonSchema.setModifyDate(mCurrentDate);
							tLJAPayPersonSchema.setModifyTime(mCurrentTime);
							//营改增 add zhangyingfeng 2016-07-11
							//价税分离 计算器
							TaxCalculator.calBySchema(tLJAPayPersonSchema);
							//end zhangyingfeng 2016-07-11
							mLJAPayPersonSet.add(tLJAPayPersonSchema);
						}
						//add by jiaqiangli 2009-07-09 添加到删除集合
						mLJSPayPersonSet.add(mLJSPayPersonSetemp);
					} else {
						// 复效核保加费时处理
						Reflections tReflections = new Reflections();
						tReflections.transFields(tLJAGetEndorseSchema,
								tLJSGetEndorseSchema);

						tLJAGetEndorseSchema.setActuGetNo(tPayNo);
						tLJAGetEndorseSchema.setEnterAccDate(mEnterAccDate); // add
																				// by
																				// Minim
						tLJAGetEndorseSchema.setGetConfirmDate(mCurrentDate); // add
																				// by
																				// Minim
						tLJAGetEndorseSchema.setOperator(this.getOperator());
						tLJAGetEndorseSchema.setMakeDate(mCurrentDate);
						tLJAGetEndorseSchema.setMakeTime(mCurrentTime);
						tLJAGetEndorseSchema.setModifyDate(mCurrentDate);
						tLJAGetEndorseSchema.setModifyTime(mCurrentTime);

						mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
					}
				} else {
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetEndorseSchema,
							tLJSGetEndorseSchema);

					tLJAGetEndorseSchema.setActuGetNo(tPayNo);
					tLJAGetEndorseSchema.setEnterAccDate(mEnterAccDate); // add
																			// by
																			// Minim
					tLJAGetEndorseSchema.setGetConfirmDate(mCurrentDate); // add
																			// by
																			// Minim
					tLJAGetEndorseSchema.setOperator(this.getOperator());
					tLJAGetEndorseSchema.setMakeDate(mCurrentDate);
					tLJAGetEndorseSchema.setMakeTime(mCurrentTime);
					tLJAGetEndorseSchema.setModifyDate(mCurrentDate);
					tLJAGetEndorseSchema.setModifyTime(mCurrentTime);

					mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
				}
			}

			// 修改累计保费
			// if (!modifySumPrem(mLJAGetEndorseSet))
			// {
			// return false;
			// }
		}

		/* 应付总表核销转储 */
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(mGetNoticeNo);
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet != null) {
			mLJAPaySet.clear();
			for (int i = 1; i <= tLJSPaySet.size(); i++) {
				tLJSPaySchema = new LJSPaySchema();
				tLJAPaySchema = new LJAPaySchema();
				tLJSPaySchema = tLJSPaySet.get(i);
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLJAPaySchema, tLJSPaySchema);
				tLJAPaySchema.setIncomeNo(tLJSPaySchema.getOtherNo());
				tLJAPaySchema.setIncomeType(tLJSPaySchema.getOtherNoType());
				tLJAPaySchema.setSumActuPayMoney(tLJSPaySchema
						.getSumDuePayMoney());
				tLJAPaySchema.setPayNo(tPayNo);
				tLJAPaySchema.setEnterAccDate(mEnterAccDate); // add by Minim
				tLJAPaySchema.setConfDate(mCurrentDate);
				tLJAPaySchema.setOperator(this.getOperator());
				tLJAPaySchema.setMakeDate(mCurrentDate);
				tLJAPaySchema.setMakeTime(mCurrentTime);
				tLJAPaySchema.setModifyDate(mCurrentDate);
				tLJAPaySchema.setModifyTime(mCurrentTime);

				mLJAPaySet.add(tLJAPaySchema);
			}
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "conData";
			tError.errorMessage = "主表数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 准备转储交费数据
	 * 
	 * @return true or false
	 */
	private boolean conLJAPaySerials(String aType) {

		/* 应付总表 */
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPaySchema tLJSPaySchema = new LJSPaySchema();

		/* 批改补退费表（应收/应付） */
		LJSGetEndorseSchema tLJSGetEndorseSchema = new LJSGetEndorseSchema();

		/* 实付总表 */
		mLJAPaySet = new LJAPaySet();
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();

		/* 批改补退费表（实收/实付） */
		LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
		mLJAGetEndorseSet = new LJAGetEndorseSet();

		/* 得到实付号码 */
		String tPayNo = mPayNo;

		if (aType.equals("PG")) {
			/* 批改补退费核销转储 */
			LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB();
			tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
			mLJSGetEndorseSet = tLJSGetEndorseDB.query();
			if (mLJSGetEndorseSet != null) {
				mLJAGetEndorseSet.clear();
				for (int i = 1; i <= mLJSGetEndorseSet.size(); i++) {
					tLJSGetEndorseSchema = new LJSGetEndorseSchema();
					tLJAGetEndorseSchema = new LJAGetEndorseSchema();
					tLJSGetEndorseSchema = mLJSGetEndorseSet.get(i);
					Reflections tReflections = new Reflections();
					tReflections.transFields(tLJAGetEndorseSchema,
							tLJSGetEndorseSchema);

					tLJAGetEndorseSchema.setActuGetNo(tPayNo);
					tLJAGetEndorseSchema.setOperator(this.getOperator());
					tLJAGetEndorseSchema.setMakeDate(mCurrentDate);
					tLJAGetEndorseSchema.setMakeTime(mCurrentTime);
					tLJAGetEndorseSchema.setModifyDate(mCurrentDate);
					tLJAGetEndorseSchema.setModifyTime(mCurrentTime);

					mLJAGetEndorseSet.add(tLJAGetEndorseSchema);
				}

				// 修改累计保费，add by Minim at 2003-10-17
				// if (!modifySumPrem(mLJAGetEndorseSet))
				// {
				// return false;
				// }
			}
		}
		/* 应付总表核销转储 */
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(mGetNoticeNo);
		tLJSPaySet = tLJSPayDB.query();
		if (tLJSPaySet != null) {
			mLJAPaySet.clear();
			for (int i = 1; i <= tLJSPaySet.size(); i++) {
				tLJSPaySchema = new LJSPaySchema();
				tLJAPaySchema = new LJAPaySchema();
				tLJSPaySchema = tLJSPaySet.get(i);
				Reflections tReflections = new Reflections();
				tReflections.transFields(tLJAPaySchema, tLJSPaySchema);

				tLJAPaySchema.setPayNo(tPayNo);
				tLJAPaySchema.setOperator(this.getOperator());

				tLJAPaySchema.setMakeDate(mCurrentDate);
				tLJAPaySchema.setMakeTime(mCurrentTime);

				tLJAPaySchema.setModifyDate(mCurrentDate);
				tLJAPaySchema.setModifyTime(mCurrentTime);

				mLJAPaySet.add(tLJAPaySchema);
			}
		} else {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "conData";
			tError.errorMessage = "主表数据有误!";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 保存实付数据
	 * 
	 * @return
	 */
	public boolean saveLJAPaySerials() {
		String tActuPayNo;
		/* 实付总表 */
		LJAPaySet tLJAPaySet = new LJAPaySet();
		LJAPayDBSet tLJAPayDBSet;

		/* 批改补退费表（实收/实付） */
		LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
		LJAGetEndorseDBSet tLJAGetEndorseDBSet;

		/* save data */
		Connection conn = null;
		conn = DBConnPool.getConnection();

		if (conn == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "saveData";
			tError.errorMessage = "数据库连接失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		String tPayNo = mPayNo;

		try {
			conn.setAutoCommit(false);

			tLJAGetEndorseDBSet = new LJAGetEndorseDBSet(conn);
			tLJAGetEndorseDBSet.set(mLJAGetEndorseSet);

			if (tLJAGetEndorseDBSet != null && tLJAGetEndorseDBSet.size() > 0) {
				if (!tLJAGetEndorseDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "保全交退费纪录保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSGetEndorseDB tLJSGetEndorseDB = new LJSGetEndorseDB(conn);
				tLJSGetEndorseDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSGetEndorseDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "保全交退费纪录删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			tLJAPayDBSet = new LJAPayDBSet(conn);
			tLJAPayDBSet.set(mLJAPaySet);

			if (tLJAPayDBSet != null && tLJAPayDBSet.size() > 0) {
				if (!tLJAPayDBSet.insert()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "实收保存失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}

				LJSPayDB tLJSPayDB = new LJSPayDB(conn);
				tLJSPayDB.setGetNoticeNo(mGetNoticeNo);
				if (!tLJSPayDB.deleteSQL()) {
					CError tError = new CError();
					tError.moduleName = "LJFinaConfirm";
					tError.functionName = "saveLJAGetserials";
					tError.errorMessage = "交费纪录删除失败!";
					this.mErrors.addOneError(tError);
					conn.rollback();
					conn.close();
					return false;
				}
			}

			logger.debug("Start Modify ljtempfee");
			// 更新暂交费表，add by Minim
			LJTempFeeDBSet tLJTempFeeDBSet = new LJTempFeeDBSet(conn);
			tLJTempFeeDBSet.set(mLJTempFeeSet);
			if (!tLJTempFeeDBSet.update()) {
				CError tError = new CError();
				tError.moduleName = "LJFinaConfirm";
				tError.functionName = "saveLJAGetserials";
				tError.errorMessage = "暂交费表更新失败!";
				this.mErrors.addOneError(tError);
				conn.rollback();
				conn.close();
				return false;
			}

			// 更新暂交费子表,add by JL at 2004-11-19
			LJTempFeeClassDBSet tLJTempFeeClassDBSet = new LJTempFeeClassDBSet(
					conn);
			tLJTempFeeClassDBSet.set(mLJTempFeeClassSet);
			if (!tLJTempFeeClassDBSet.update()) {
				CError.buildErr(this, "暂交费子表更新失败!");
				conn.rollback();
				conn.close();
				return true;
			}
			logger.debug("End Modify ljtempfee");

			commitModifySumPrem(conn);

			conn.commit();
			conn.close();
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LJFinaConfirm";
			tError.functionName = "saveData";
			tError.errorMessage = ex.toString();
			this.mErrors.addOneError(tError);
			try {
				conn.rollback();
				conn.close();
			} catch (Exception e) {
			}
			return false;
		}
		return true;
	}

	/**
	 * 核销交退费处理
	 * 
	 * @return
	 */
	private boolean chkFinaConfirm() {
		/* 财务给付表 */
		LJTempFeeSet tLJTempFeeSet = new LJTempFeeSet();
		LJTempFeeClassSet tLJTempFeeClassSet = new LJTempFeeClassSet();

		if (mFlag.equals("I")) {
			// 获取应收总表数据
			LJSPayDB tLJSPayDB = new LJSPayDB();
			tLJSPayDB.setGetNoticeNo(mGetNoticeNo);
			
			LJSPaySet tLJSPaySet = tLJSPayDB.query();
			if(tLJSPaySet == null || tLJSPaySet.size()<1){
				CError tError = new CError();
				tError.moduleName = "LJFinaConfirm";
				tError.functionName = "GetNoticeNO";
				tError.errorMessage = "没有财务应收交费纪录!";
				this.mErrors.addOneError(tError);
				return false;
			}

			

			// 生成实付号码
			String StrLimit = this.getLimit();
			mPayNo = PubFun1.CreateMaxNo("PAYNO", StrLimit);
			
			for(int l=1;l<=tLJSPaySet.size();l++){
				LJSPaySchema tLJSPaySchema = tLJSPaySet.get(l);
				// 如果需要财务收费
				if (tLJSPaySchema.getSumDuePayMoney() > 0) {
					mLJSPaySet.add(tLJSPaySchema);
					String tCurrency = tLJSPaySchema.getCurrency();
					// 获取暂交费数据
					LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
					// Modify by Minim for check EnterAccDate
					String strSql = " select * from ljtempfee where tempfeeno = '"
						+ "?mGetNoticeNo?"
						+ "' and Currency = '"+"?tCurrency?"+"' and EnterAccDate is not null and EnterAccDate <> '3000-01-01' order by EnterAccDate";
				    SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				    sqlbv.sql(strSql);
				    sqlbv.put("mGetNoticeNo", mGetNoticeNo);
				    sqlbv.put("tCurrency", tCurrency);
					tLJTempFeeSet = tLJTempFeeDB.executeQuery(sqlbv);
					if (tLJTempFeeSet == null || tLJTempFeeSet.size() == 0) {
						CError.buildErr(this, "没有财务交费纪录!");
						return false;
					}
					
					double tPayMoney = 0;
					double tPayMoneyClass;
					for (int i = 1; i <= tLJTempFeeSet.size(); i++) {
						tPayMoney = tPayMoney + tLJTempFeeSet.get(i).getPayMoney();
						// 核销暂交费数据,add by Minim
						tLJTempFeeSet.get(i).setConfFlag("1");
						tLJTempFeeSet.get(i).setConfDate(mCurrentDate);
						
						//add by jiaqiangli 外围渠道进行的增量提数
						tLJTempFeeSet.get(i).setModifyDate(this.mCurrentDate);
						tLJTempFeeSet.get(i).setModifyTime(this.mCurrentTime);
						//add by jiaqiangli 外围渠道进行的增量提数
						
						// 核销暂交费子表数据,add by JL at 2004-11-18
						LJTempFeeClassDB tLJTempFeeClassDB = new LJTempFeeClassDB();
						strSql = "select * from ljtempfeeclass where tempfeeno='"
							+ "?tempfeeno?"
							+ "' and Currency = '"+"?Currency?"+"' and EnterAccDate is not null";
						SQLwithBindVariables sbv=new SQLwithBindVariables();
						sbv.sql(strSql);
						sbv.put("tempfeeno", tLJTempFeeSet.get(i).getTempFeeNo());
						sbv.put("Currency", tLJTempFeeSet.get(i).getCurrency());
						tLJTempFeeClassSet = tLJTempFeeClassDB.executeQuery(sbv);
						if (tLJTempFeeClassSet == null
								|| tLJTempFeeClassSet.size() == 0) {
							CError.buildErr(this, "查找暂交费分类表记录失败!");
							return false;
						}
						tPayMoneyClass = 0;
						for (int j = 1; j <= tLJTempFeeClassSet.size(); j++) {
							tPayMoneyClass += tLJTempFeeClassSet.get(j).getPayMoney();
							// 核销暂交费子表
							tLJTempFeeClassSet.get(j).setConfFlag("1");
							tLJTempFeeClassSet.get(j).setConfDate(mCurrentDate);
							
							//add by jiaqiangli 外围渠道进行的增量提数
							tLJTempFeeClassSet.get(j).setModifyDate(this.mCurrentDate);
							tLJTempFeeClassSet.get(j).setModifyTime(this.mCurrentTime);
							//add by jiaqiangli 外围渠道进行的增量提数
						}
						// 校验暂交费子表金额是否与暂交费表相同
						double dIntv = Math.abs(tPayMoneyClass
								- tLJTempFeeSet.get(i).getPayMoney());
						if (dIntv < 0.01) // 除掉double类型计算误差
						{
							mLJTempFeeClassSet.add(tLJTempFeeClassSet);
						} else {
							CError.buildErr(this, "暂交费主表记录和暂交费子表记录金额不符!");
							return false;
						}
					}
					
					// 判断应收与暂收是否相等
					if (tPayMoney == tLJSPaySchema.getSumDuePayMoney()) {
						// 获取最晚的到帐日期,add by Minim
						mEnterAccDate = tLJTempFeeSet.get(1).getEnterAccDate();
						mLJTempFeeSet = tLJTempFeeSet;
					} else {
						CError tError = new CError();
						tError.moduleName = "LJFinaConfirm";
						tError.functionName = "GetNoticeNO";
						tError.errorMessage = "应交与暂交不符!";
						this.mErrors.addOneError(tError);
						return false;
					}
					
//					账户管理  添加代码  开始
					try
					{
						// 添加客户账户处理
						VData nInputData = new VData();
						TransferData nTransferData = new TransferData();
						// 指定用途
						nTransferData.setNameAndValue("OperationType", "3");
						// 传入本次核销金额
						nTransferData.setNameAndValue("SumDuePayMoney", tLJSPaySchema.getSumDuePayMoney());
						nTransferData.setNameAndValue("Currency", tLJSPaySchema.getCurrency());
						nInputData.add(mLJTempFeeSet);
						nInputData.add(mLJTempFeeClassSet);
						nInputData.add(nTransferData);
						FICustomerMain tFICustomerMain = new FICustomerMain();
						// 调用客户账户收费接口，传入财务标志RN
						if (tFICustomerMain.submitData(nInputData, "RN"))
						{
							// 获取接口计算结果，传入MMap，方便打包直接用PubSubmit提交
							mMap.add(tFICustomerMain.getMMap());
						}
						else
						{
							mErrors.copyAllErrors(tFICustomerMain.mErrors);
							return false;
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
					
				}
			}
			
			// add by JL at 2004-11-24,修改tLJSPayDB.getSumDuePayMoney()==0
			// mEnterAccDate为空的情况。
			if (mEnterAccDate == null || mEnterAccDate == "") {
				mEnterAccDate = mCurrentDate;
			}
		} else {
			if (mFlag.equals("O")) {
				String strLimit = this.getLimit();
				mActuGetNo = PubFun1.CreateMaxNo("GETNO", strLimit);
				this.setActuGetNo(mActuGetNo);
			} else {
				CError tError = new CError();
				tError.moduleName = "LJFinaConfirm";
				tError.functionName = "GetNoticeNO";
				tError.errorMessage = "标志传入有误!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return true;
	}

	private boolean prepareOutputData() {
		mMap.put(mLJSGetEndorseSet, "DELETE");
		mMap.put(mLJAGetEndorseSet, "INSERT");

		if (mFlag.equals("I")) {
			mMap.put(mLJTempFeeSet, "UPDATE");
			mMap.put(mLJTempFeeClassSet, "UPDATE");
			mMap.put(mLJSPaySet, "DELETE");
			mMap.put(mLJAPaySet, "INSERT");
			// =====ADD=====zhangtao=======2005-05-30========================BGN=============
			mMap.put(mLJSPayPersonSet, "DELETE");
			mMap.put(mLJAPayPersonSet, "INSERT");
			// =====ADD=====zhangtao=======2005-05-30========================END=============
			// =====ADD=====zhangtao=======2005-10-08======保全回退===========BGN=============
			mMap.put(mLJSGetDrawSet, "DELETE");
			mMap.put(mLJAGetDrawSet, "INSERT");
			// =====ADD=====zhangtao=======2005-10-08======保全回退===========END=============
		} else if (mFlag.equals("O")) {
			mMap.put(mLJSGetClaimSet, "DELETE");
			mMap.put(mLJAGetClaimSet, "INSERT");
			mMap.put(mLJSGetOtherSet, "DELETE");
			mMap.put(mLJAGetOtherSet, "INSERT");
			mMap.put(mLJSGetTempFeeSet, "DELETE");
			mMap.put(mLJAGetTempFeeSet, "INSERT");
			mMap.put(mLJSGetDrawSet, "DELETE");
			mMap.put(mLJAGetDrawSet, "INSERT");
			mMap.put(mLJSGetSet, "DELETE");
			mMap.put(mLJAGetSet, "INSERT");
			mMap.put(mDelLJAGetSet, "DELETE");// 红利领取对应的原实付总表记录需要删除
			//mMap.put(mDelLJABonusGetSet, "DELETE");
			mMap.put(mLJABonusGetSet, "DELETE&INSERT");// 更新红利领取记录的给付通知书号和实付号
			// =====ADD=====zhangtao=======2005-10-08======保全回退===========BGN=============
			mMap.put(mLJSPayPersonSet, "DELETE");
			mMap.put(mLJAPayPersonSet, "INSERT");
			// =====ADD=====zhangtao=======2005-10-08======保全回退===========END=============
		}

		// 更新保费变更数据
		mMap.put(mLPPremSet, "UPDATE");
		mMap.put(mLPDutySet, "UPDATE");
		mMap.put(mLPPolSet, "UPDATE");
		mMap.put(mLPContSet, "UPDATE");
		mMap.put(mLPGrpPolSet, "UPDATE");
		mMap.put(mLPGrpContSet, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		return true;
	}

	public void setLimit(String aLimit) {
		mLimit = aLimit;
	}

	public String getLimit() {
		return mLimit;
	}

	public void setOperator(String aOperator) {
		mOperator = aOperator;
	}

	public String getOperator() {
		return mOperator;
	}

	public void setActuGetNo(String aActuGetNo) {
		mActuGetNo = aActuGetNo;
	}

	public String getActuGetNo() {
		return mActuGetNo;
	}

	public void setDrawer(String aDrawer) {
		mDrawer = aDrawer;
	}

	public void setDrawerID(String aDrawerID) {
		mDrawerID = aDrawerID;
	}

	public void setShouldDate(String aShouldDate) {
		mShouldDate = aShouldDate;
	}
	
	public void setEdorAcceptNo(String aEdorAcceptNo)
	{
		mEdorAcceptNo = aEdorAcceptNo;
	}
	public String getEdorAcceptNo()
	{
		return mEdorAcceptNo;
	}

	public static void main(String[] args) {
		int f = PubFun.calInterval("2003-12-23", "2004-04-02", "D");
		String aGetNoticeNo = "86110020030310000276";
		LJFinaConfirm tLJFinaConfirm = new LJFinaConfirm(aGetNoticeNo, "I");
		tLJFinaConfirm.setOperator("001");
		tLJFinaConfirm.setLimit(PubFun.getNoLimit("86"));
		logger.debug("start LJFinaConfirm...");
		if (!tLJFinaConfirm.submitData()) {

		}
	}
}
