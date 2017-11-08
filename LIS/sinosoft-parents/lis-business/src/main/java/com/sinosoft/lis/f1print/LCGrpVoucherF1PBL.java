package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author zhangxing
 * @version 1.0

 */

import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCGrpAppntDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCInsuredDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCInsuredSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class LCGrpVoucherF1PBL {
private static Logger logger = Logger.getLogger(LCGrpVoucherF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 取得的险种保单号码
	private String mPolNo = "";
	// 取得集体保单号码
	private String mGrpContNo = "";

	// 输入的查询sql语句
	private String msql = "";

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LCGrpContSchema mLCGrpContSchema = new LCGrpContSchema();
	private LCPolSchema mLCPolSchema = new LCPolSchema();
	private LCInsuredSchema mLCInsuredSchema = new LCInsuredSchema();
	private LCGetSchema mLCGetSchema = new LCGetSchema();

	public LCGrpVoucherF1PBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		if (tTransferData == null) {
			buildError("getInputData", "数据传入不足！");
			return false;
		}
		mGrpContNo = (String) tTransferData.getValueByName("GrpContNo");
		mPolNo = (String) tTransferData.getValueByName("PolNo");

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LCGrpVoucherF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getPrintData() {

		// 查询集体保单信息
		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			buildError("outputXML", "在LCGrpCont表中找不到相关信息");
			return false;
		}
		mLCGrpContSchema = tLCGrpContDB.getSchema(); // 集体保单信息

		// 查询险种信息
		LCPolDB tLCPolDB = new LCPolDB();
		tLCPolDB.setPolNo(mPolNo);
		if (tLCPolDB.getInfo() == false) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			buildError("outputXML", "在LCPol表中找不到相关信息");
			return false;
		}
		mLCPolSchema = tLCPolDB.getSchema(); // 险种单信息

		// 查询投保人信息
		LCGrpAppntDB tLCGrpAppntDB = new LCGrpAppntDB();
		tLCGrpAppntDB.setGrpContNo(mGrpContNo);
		if (!tLCGrpAppntDB.getInfo()) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			buildError("outputXML", "在LCGrpAppnt表中找不到相关信息");
			return false;
		}

		// 查询被保险人信息
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(mLCPolSchema.getContNo());
		tLCInsuredDB.setInsuredNo(mLCPolSchema.getInsuredNo());
		if (!tLCInsuredDB.getInfo()) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			buildError("outputXML", "在LCInsured表中找不到相关信息");
			return false;
		}
		mLCInsuredSchema = tLCInsuredDB.getSchema();

		// 查询险种名称
		LMRiskDB tLMRiskDB = new LMRiskDB();
		tLMRiskDB.setRiskCode(mLCPolSchema.getRiskCode());
		if (!tLMRiskDB.getInfo()) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			buildError("outputXML", "在LMRisk表中找不到相关信息");
			return false;
		}
		// 受益人
		ExeSQL exeSql = new ExeSQL();
		String bnfName = "";
		String tBnfSql = "select name from lcbnf where polno='"
				+ mLCPolSchema.getPolNo() + "'";
		SSRS rSSRS = exeSql.execSQL(tBnfSql);
		if (rSSRS.MaxRow == 0) {
			bnfName = "法定";
		} else {
			bnfName = rSSRS.GetText(1, 1);
		}
		// 获取领取项信息
		LCGetDB tLCGetDB = new LCGetDB();
		LCGetSet tLCGetSet = new LCGetSet();
		tLCGetDB.setPolNo(mLCPolSchema.getPolNo());
		tLCGetSet = tLCGetDB.query();
		if (tLCGetSet == null || tLCGetSet.size() == 0) {
			mErrors.copyAllErrors(tLCPolDB.mErrors);
			buildError("outputXML", "在LCGet表中找不到相关信息");
			return false;
		}
		mLCGetSchema = tLCGetSet.get(1).getSchema();

		TextTag texttag = new TextTag(); // 新建一个TextTag的实例
		XmlExport xmlexport = new XmlExport(); // 新建一个XmlExport的实例
		xmlexport.createDocument("GrpVoucher.vts", "printer"); // 最好紧接着就初始化xml文档

		texttag.add("LCGrpCont_GrpContNo", mLCGrpContSchema.getGrpContNo()); // 保单号
		texttag.add("LCGrpCont_GrpName", tLCGrpAppntDB.getName()); // 投保单位名称
		texttag.add("LCPol_InsuredName", mLCInsuredSchema.getName()); // 被保险人姓名
		texttag.add("LCInsured_IDNo", mLCInsuredSchema.getIDNo()); // 被保险人身份证号码

		texttag.add("LCPol_RiskName", tLMRiskDB.getRiskName()); // 险种名称
		texttag.add("LCPol_Cvalidate", mLCPolSchema.getCValiDate()); // 保单生效日期
		texttag.add("LCPol_Amnt", mLCPolSchema.getAmnt()); // 保险金额
		texttag.add("LCPol_Prem", mLCPolSchema.getPrem()); // 交费标准
		texttag.add("LCBnf_Name", bnfName); // 受益人

		texttag.add("LCPol_PayIntv", getPayIntvName(mLCPolSchema.getPayIntv())); // 交费方式
		texttag.add("LCPol_GetYear", mLCPolSchema.getGetYear()); // 领取年龄
		texttag.add("LCGet_GetIntv", getPayIntvName(mLCGetSchema.getGetIntv())); // 领取方式
		texttag.add("LCGet_GetStartData", mLCGetSchema.getGetStartDate()); // 领取时间
		texttag.add("LCGet_StandMoney", mLCGetSchema.getStandMoney()); // 领取标准

		texttag.add("SysData", PubFun.getCurrentDate()); // 出单日期
		xmlexport.addTextTag(texttag);
		xmlexport.outputDocumentToFile("d:\\", "uw");
		mResult.clear();
		mResult.addElement(xmlexport);

		return true;
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 * @return
	 * @throws Exception
	 */
	private String getPayIntvName(int PayIntv) {
		String tSql = "select CodeName from LDcode where Code = '" + PayIntv
				+ "' and CodeType = 'payintv'";
		ExeSQL tExeSQL = new ExeSQL();
		// 如果没有try的话，无法捕获到ExeSQL抛出的异常
		return tExeSQL.getOneValue(tSql);

	}

}
