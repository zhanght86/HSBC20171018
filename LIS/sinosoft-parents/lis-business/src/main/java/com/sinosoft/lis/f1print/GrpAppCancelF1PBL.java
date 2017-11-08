package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author lh
 * @version 1.0
 */

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpAppCancelF1PBL {
private static Logger logger = Logger.getLogger(GrpAppCancelF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	private String mOperate = "";
	private String mRealPayMoney = "";

	public GrpAppCancelF1PBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		try {

			if (!cOperate.equals("CONFIRM") && !cOperate.equals("PRINT")) {
				buildError("submitData", "不支持的操作字符串");
				return false;
			}

			// 得到外部传入的数据，将数据备份到本类中
			if (!getInputData(cInputData)) {
				return false;
			}

			if (cOperate.equals("PRINT")) {

				mResult.clear();

				// 准备所有要打印的数据
				getPrintData();

			}

			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("submitData", ex.toString());
			return false;
		}
	}

	/**
	 * 根据前面的输入数据，进行BL逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		// 全局变量
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mLOPRTManagerSchema.setSchema((LOPRTManagerSchema) cInputData
				.getObjectByObjectName("LOPRTManagerSchema", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}

		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "AppCalcelF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private void getPrintData() throws Exception {
		XmlExport xmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag texttag = new TextTag();
		xmlExport.createDocument("GrpWithDraw.vts", "printer"); // 最好紧接着就初始化xml文档

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all message！

		tLCGrpContDB.setGrpContNo(mLOPRTManagerSchema.getOtherNo());
		LCGrpContSet tempLCGrpContSet = tLCGrpContDB.query();

		if (tempLCGrpContSet.size() != 1) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			throw new Exception("在查询保单信息时出错！");
		}

		tLCGrpContDB.setSchema(tempLCGrpContSet.get(1));

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCGrpContDB.getAgentCode());
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在查询代理人信息时出错");
		}
		mLAAgentSchema = tLAAgentDB.getSchema();

		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(mLAAgentSchema.getAgentGroup());
		if (!tLABranchGroupDB.getInfo()) {
			mErrors.copyAllErrors(tLABranchGroupDB.mErrors);
			throw new Exception("在查询代理机构时出错");
		}
		String strPolNo = tLCGrpContDB.getGrpContNo();
		// 查询暂缴费表,查询缴费金额
		String tStrSQL = "select sum(PayMoney) from LJTempFee where "

		+ " (TempFeeType in ('0','6','1') and otherno = '" + strPolNo + "')"

		+ "and EnterAccDate is not null";
		logger.debug("tStrSQL:" + tStrSQL);
		ExeSQL tExeSQL = new ExeSQL();

		Double tDouble = new Double(tExeSQL.getOneValue(tStrSQL));
		double Sumprem = tDouble.doubleValue();
		mRealPayMoney = "￥" + mDecimalFormat.format(Sumprem);

		logger.debug("实际交的金额:" + mRealPayMoney);
		if (mRealPayMoney.equals(null) || (mRealPayMoney).trim().equals("")
				|| mRealPayMoney.equals("0")) {
			mRealPayMoney = "0";
		}

		if (tLCGrpContDB.getSaleChnl().equals("3")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(tLCGrpContDB.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("LCGrpCont.BankCode", mLAComSchema.getName()); // 代理机构
			texttag.add("LCGrpCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		LCContDB tLCContDB = new LCContDB();
		String tSql = "select nvl((select codename from ldcode where codetype = 'paymode' and code = "
				+ "(select paymode from ljtempfeeclass b "
				+ " where b.tempfeeno = a.tempfeeno "
				+ " and rownum = '1')),'未缴费')"
				+ " from ljtempfee a "
				+ " where a.otherno = '" + tLCGrpContDB.getGrpContNo() + "'";
		SSRS paymode = new SSRS();
		paymode = tExeSQL.execSQL(tSql);

		texttag.add("BarCode1", "UN082");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", tLCGrpContDB.getGrpContNo());
		texttag.add("LAAgent.Name", getAgentName(tLCGrpContDB.getAgentCode()));
		texttag.add("LCCont.AppntName", tLCGrpContDB.getGrpName());
		texttag.add("LAAgent.AgentCode", tLCGrpContDB.getAgentCode());
		texttag.add("LABranchGroup.Name", tLABranchGroupDB.getName());
		texttag.add("ManageComName", getComName(tLCGrpContDB.getManageCom()));
		texttag.add("Prem", mRealPayMoney);
		if (paymode != null && paymode.getMaxRow() != 0) {
			texttag.add("Paymode", paymode.GetText(1, 1));
		} else {
			texttag.add("Paymode", "");
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日");
		texttag.add("SysDate", df.format(new Date()));

		if (texttag.size() > 0) {
			xmlExport.addTextTag(texttag);
		}

		// xmlExport.outputDocumentToFile("e:\\", "t2HZM");
		mResult.clear();
		mResult.addElement(xmlExport);

	}

	// 下面是一些辅助函数

	private String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

	/**
	 * 得到通过机构代码得到机构名称
	 * 
	 * @param strComCode
	 *            String
	 * @return String
	 */
	private String getComName(String strComCode) {
		LDCodeDB tLDCodeDB = new LDCodeDB();
		tLDCodeDB.setCode(strComCode);
		tLDCodeDB.setCodeType("station");
		if (!tLDCodeDB.getInfo()) {
			mErrors.copyAllErrors(tLDCodeDB.mErrors);
			return "";
		}
		return tLDCodeDB.getCodeName();
	}

}
