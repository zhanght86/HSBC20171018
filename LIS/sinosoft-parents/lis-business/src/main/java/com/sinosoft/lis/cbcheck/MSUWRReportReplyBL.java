package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCCUWMasterDB;
import com.sinosoft.lis.db.LCRReportDB;
import com.sinosoft.lis.pubfun.CalBase;
import com.sinosoft.lis.pubfun.FDate;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCCUWMasterSchema;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LCPENoticeSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.schema.LCUWSubSchema;
import com.sinosoft.lis.schema.LMUWSchema;
import com.sinosoft.lis.vschema.LCAppntIndSet;
import com.sinosoft.lis.vschema.LCBnfSet;
import com.sinosoft.lis.vschema.LCCUWMasterSet;
import com.sinosoft.lis.vschema.LCCUWSubSet;
import com.sinosoft.lis.vschema.LCCustomerImpartSet;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LCInsuredSet;
import com.sinosoft.lis.vschema.LCIssuePolSet;
import com.sinosoft.lis.vschema.LCPENoticeItemSet;
import com.sinosoft.lis.vschema.LCPENoticeSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.lis.vschema.LCSpecNoteSet;
import com.sinosoft.lis.vschema.LCSpecSet;
import com.sinosoft.lis.vschema.LCUWErrorSet;
import com.sinosoft.lis.vschema.LMUWSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统个人单人工核保生调回复部分
 * </p>
 * <p>
 * Description: 逻辑处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WHN
 * @version 1.0
 */
public class MSUWRReportReplyBL {
private static Logger logger = Logger.getLogger(MSUWRReportReplyBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private String mIsueManageCom;
	private String mManageCom;
	private String mpassflag; // 通过标记
	private int merrcount; // 错误条数
	private String mCalCode; // 计算编码
	private String mUser;
	private FDate fDate = new FDate();
	private double mValue;
	private String mInsuredNo = "";
	private String mBackObj = "";
	private String mflag = ""; // 分，个单标记

	/** 业务处理相关变量 */
	private LCPolSet mLCPolSet = new LCPolSet();
	private LCPolSet mmLCPolSet = new LCPolSet();
	private LCPolSet m2LCPolSet = new LCPolSet();
	private LCPolSet mAllLCPolSet = new LCPolSet();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private String mPolNo = "";
	private String mOldPolNo = "";
	/** 集体单表 */
	private LCGrpPolSet mAllLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSet mLCGrpPolSet = new LCGrpPolSet();
	private LCGrpPolSchema mLCGrpPolSchema = new LCGrpPolSchema();
	/** 保费项表 */
	private LCPremSet mLCPremSet = new LCPremSet();
	private LCPremSet mAllLCPremSet = new LCPremSet();
	/** 领取项表 */
	private LCGetSet mLCGetSet = new LCGetSet();
	private LCGetSet mAllLCGetSet = new LCGetSet();
	/** 责任表 */
	private LCDutySet mLCDutySet = new LCDutySet();
	private LCDutySet mAllLCDutySet = new LCDutySet();
	/** 特别约定表 */
	private LCSpecSet mLCSpecSet = new LCSpecSet();
	private LCSpecSet mAllLCSpecSet = new LCSpecSet();
	/** 特别约定注释表 */
	private LCSpecNoteSet mLCSpecNoteSet = new LCSpecNoteSet();
	private LCSpecNoteSet mAllLCSpecNoteSet = new LCSpecNoteSet();
	/** 核保主表 */
	private LCCUWMasterSet mLCCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSet mAllLCUWMasterSet = new LCCUWMasterSet();
	private LCCUWMasterSchema mLCCUWMasterSchema = new LCCUWMasterSchema();
	/** 核保子表 */
	private LCCUWSubSet mLCCUWSubSet = new LCCUWSubSet();
	private LCCUWSubSet mAllLCCUWSubSet = new LCCUWSubSet();
	private LCUWSubSchema mLCUWSubSchema = new LCUWSubSchema();
	/** 核保错误信息表 */
	private LCUWErrorSet mLCUWErrorSet = new LCUWErrorSet();
	private LCUWErrorSet mAllLCErrSet = new LCUWErrorSet();
	/** 告知表 */
	private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
	private LCCustomerImpartSet mAllLCCustomerImpartSet = new LCCustomerImpartSet();
	/** 投保人表 */
	private LCAppntIndSet mLCAppntIndSet = new LCAppntIndSet();
	private LCAppntIndSet mAllLCAppntIndSet = new LCAppntIndSet();
	/** 受益人表 */
	private LCBnfSet mLCBnfSet = new LCBnfSet();
	private LCBnfSet mAllLCBnfSet = new LCBnfSet();
	/** 被保险人表 */
	private LCInsuredSet mLCInsuredSet = new LCInsuredSet();
	private LCInsuredSet mAllLCInsuredSet = new LCInsuredSet();
	/** 体检资料主表 */
	private LCPENoticeSet mLCPENoticeSet = new LCPENoticeSet();
	private LCPENoticeSet mAllLCPENoticeSet = new LCPENoticeSet();
	private LCPENoticeSchema mLCPENoticeSchema = new LCPENoticeSchema();
	/** 体检资料项目表 */
	private LCPENoticeItemSet mLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mmLCPENoticeItemSet = new LCPENoticeItemSet();
	private LCPENoticeItemSet mAllLCPENoticeItemSet = new LCPENoticeItemSet();
	/** 问题件表 */
	private LCIssuePolSet mLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mmLCIssuePolSet = new LCIssuePolSet();
	private LCIssuePolSet mAllLCIssuePolSet = new LCIssuePolSet();
	/** 生存调查表 */
	private LCRReportSchema mLCRReportSchema = new LCRReportSchema();
	private LCRReportSet mLCRReportSet = new LCRReportSet();
	private LCRReportSet mmLCRReportSet = new LCRReportSet();
	private LCRReportSet mAllLCRReportSet = new LCRReportSet();
	/** 计算公式表* */
	private LMUWSchema mLMUWSchema = new LMUWSchema();
	// private LMUWDBSet mLMUWDBSet = new LMUWDBSet();
	private LMUWSet mLMUWSet = new LMUWSet();

	private CalBase mCalBase = new CalBase();

	public MSUWRReportReplyBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		int flag = 0; // 判断是不是所有数据都不成功
		int j = 0; // 符合条件数据个数

		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		// GlobalInput tGlobalInput = new GlobalInput();
		// this.mOperate = tGlobalInput.;

		logger.debug("---1---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData))
			return false;
		logger.debug("---UWRReportChkBL getInputData---");

		mOldPolNo = mLCRReportSchema.getContNo();
		mPolNo = mLCRReportSchema.getContNo();

		// 校验数据
		// if(!CheckReply())
		// return false;

		logger.debug("---UWRReportReplyBL checkData---");
		// 数据操作业务处理
		if (!dealData())
			return false;

		logger.debug("---UWRReportReplyBL dealData---");
		// 准备给后台的数据
		prepareOutputData();

		logger.debug("---UWRReportReplyBL prepareOutputData---");
		// 数据提交
		MSUWRReportReplyBLS tUWRReportReplyBLS = new MSUWRReportReplyBLS();
		logger.debug("Start UWRReportReplyBL Submit...");
		if (!tUWRReportReplyBLS.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tUWRReportReplyBLS.mErrors);
			CError tError = new CError();
			tError.moduleName = "UWRReportReplyBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		logger.debug("---UWRReportReplyBL commitData---");
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {

		if (dealOnePol() == false)
			return false;

		return true;
	}

	/**
	 * 操作一张保单的业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealOnePol() {
		// 健康信息
		if (prepareReport() == false)
			return false;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperate = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;

		mLCRReportSchema.setSchema((LCRReportSchema) cInputData
				.getObjectByObjectName("LCRReportSchema", 0));

		if (mLCRReportSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportReplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有传入保单信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		if (mLCRReportSchema.getReplyContente() == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportReplyBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "没有录入回复信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 校验是不是已经全部回复
	 * 
	 * @return
	 */
	/*
	 * private boolean CheckReply() { String tsql = "select * from LCRReport
	 * where polno = '"+mLCRReportSchema.getPolNo()+"' and serialno = (select
	 * max(serialno) from lcrreport where polno =
	 * '"+mLCRReportSchema.getPolNo()+"') and replyflag = '0'";
	 * 
	 * logger.debug("sql:"+tsql); LCRReportDB tLCRReportDB = new
	 * LCRReportDB(); LCRReportSet tLCRReportSet = new LCRReportSet();
	 * 
	 * tLCRReportSet = tLCRReportDB.executeQuery(tsql);
	 * 
	 * if(tLCRReportSet.size() == 0) { } else { // @@错误处理 CError tError = new
	 * CError(); tError.moduleName = "UWRReportChkBL"; tError.functionName =
	 * "CheckReply"; tError.errorMessage = "上次生存调查报告尚未回复,不能录入!"; this.mErrors
	 * .addOneError(tError) ; return false; }
	 * 
	 * return true; }
	 */
	/**
	 * 准备体检资料信息 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean prepareReport() {
		// 取回复内容
		String tReplyContent = mLCRReportSchema.getReplyContente();
		// 取生调记录
		String tsql = "select * from LCRReport where contno = '"
				+ "?contno?" + "' and replyflag = '0'";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tsql);
		sqlbv.put("contno", mLCRReportSchema.getContNo());
		logger.debug("sql:" + tsql);
		LCRReportDB tLCRReportDB = new LCRReportDB();
		LCRReportSet tLCRReportSet = new LCRReportSet();

		tLCRReportSet = tLCRReportDB.executeQuery(sqlbv);

		if (tLCRReportSet.size() == 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportReplyBL";
			tError.functionName = "prepareReport";
			tError.errorMessage = "此单已经回复!";
			this.mErrors.addOneError(tError);
			return false;
		} else {
			mLCRReportSchema = tLCRReportSet.get(1);
		}

		mLCRReportSchema.setReplyContente(tReplyContent);
		mLCRReportSchema.setReplyFlag("1");
		mLCRReportSchema.setReplyOperator(mOperate);
		mLCRReportSchema.setReplyDate(PubFun.getCurrentDate());
		mLCRReportSchema.setReplyTime(PubFun.getCurrentTime());
		mLCRReportSchema.setModifyDate(PubFun.getCurrentDate());
		mLCRReportSchema.setModifyTime(PubFun.getCurrentTime());

		// 核保主表信息
		LCCUWMasterDB tLCCUWMasterDB = new LCCUWMasterDB();
		tLCCUWMasterDB.setContNo(mPolNo);

		if (tLCCUWMasterDB.getInfo() == false) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "UWRReportBL";
			tError.functionName = "prepareReport";
			tError.errorMessage = "无核保主表信息!";
			this.mErrors.addOneError(tError);
			return false;
		}

		mLCCUWMasterSchema = tLCCUWMasterDB.getSchema();
		// if (mLCUWMasterSchema.getPrintFlag().equals("0"))
		// {
		// CError tError = new CError();
		// tError.moduleName = "UWRReportBL";
		// tError.functionName = "prepareReport";
		// tError.errorMessage = "未发核保通知，不可回复!";
		// this.mErrors .addOneError(tError) ;
		// return false;
		// }

		mLCCUWMasterSchema.setReportFlag("2");

		return true;
	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(mLCRReportSchema);
		mInputData.add(mLCCUWMasterSchema);
	}
}
