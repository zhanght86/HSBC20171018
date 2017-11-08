package com.sinosoft.lis.f1print;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LAComDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LOPRTManagerDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LAComSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;
/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author lh
 * @version 1.0
 */

public class AppCancelF1PBL {
private static Logger logger = Logger.getLogger(AppCancelF1PBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();
	// 保费保额计算出来后的精确位数
	private String FORMATMODOL = "0.00";
	// 数字转换对象
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL);

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LOPRTManagerSchema mLOPRTManagerSchema = new LOPRTManagerSchema();
	private LAAgentSchema mLAAgentSchema = new LAAgentSchema();

	private String mOperate = "";
	private String mRealPayMoney = "";

	public AppCancelF1PBL() {
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
		xmlExport.createDocument("WithDraw.vts", "printer"); // 最好紧接着就初始化xml文档

		LCContDB tLCContDB = new LCContDB();
		LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();

		tLOPRTManagerDB.setSchema(mLOPRTManagerSchema); // 将prtseq传给DB，目的查找所有相关信息，然后还要返回给schema
		if (tLOPRTManagerDB.getInfo() == false) {
			mErrors.copyAllErrors(tLOPRTManagerDB.mErrors);
			throw new Exception("在取得打印队列中数据时发生错误");
		}

		mLOPRTManagerSchema = tLOPRTManagerDB.getSchema(); // get all message！

		tLCContDB.setProposalContNo(mLOPRTManagerSchema.getOtherNo());
		LCContSet tempLCContSet = tLCContDB.query();

		if (tempLCContSet.size() != 1) {
			mErrors.copyAllErrors(tLCContDB.mErrors);
			throw new Exception("在查询保单信息时出错！");
		}

		tLCContDB.setSchema(tempLCContSet.get(1));

		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tLCContDB.getAgentCode());
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
		String strPolNo = tLCContDB.getProposalContNo();
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		// 查询暂缴费表,查询缴费金额
		String tStrSQL =
		// "select sum(PayMoney) from LJTempFee where "
		//
		// + " (TempFeeType in ('0','6','1')and otherno in (select ContNo from
		// lccont where proposalcontno='" +
		// strPolNo + "'))"
		//
		// + "and EnterAccDate is not null"
		// + " and confflag = '0'";
		"select sum(sumgetmoney) From ljaget where othernotype = '4'"
				+ " and otherno = '" + "?strPolNo?" + "' and enteraccdate is null";
		sqlbv.sql(tStrSQL);
		sqlbv.put("strPolNo", strPolNo);
		logger.debug("tStrSQL:" + tStrSQL);
		ExeSQL tExeSQL = new ExeSQL();

		mRealPayMoney = tExeSQL.getOneValue(sqlbv);

		logger.debug("实际交的金额:" + mRealPayMoney);
		if (mRealPayMoney.equals(null) || (mRealPayMoney).trim().equals("")
				|| mRealPayMoney.equals("0")) {
			mRealPayMoney = "0.00";
		}

		LCPolSchema mLCPolSchema = new LCPolSchema();
		LMRiskAppDB mLMRiskAppDB = new LMRiskAppDB();
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		String tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno";
		sqlbv1.sql(tSql);
		sqlbv1.put("contno", tLCContDB.getContNo());
		SSRS PolSSRS = new SSRS();
		ExeSQL tempExeSQL = new ExeSQL();
		PolSSRS = tempExeSQL.execSQL(sqlbv1);
		if (PolSSRS != null) {
			mLMRiskAppDB.setRiskCode(PolSSRS.GetText(1, 1));
			if (!mLMRiskAppDB.getInfo()) {
				buildError("outputXML", "查询LMRiskApp表出错！");
				return;
			}
		} else {
			buildError("outputXML", "查询合同险种信息出错！");
			return;
		}

		if (mLMRiskAppDB.getRiskProp().equals("Y")) {
			LAComSchema mLAComSchema = new LAComSchema();
			LAComDB mLAComDB = new LAComDB();
			mLAComDB.setAgentCom(tLCContDB.getAgentCom());
			if (!mLAComDB.getInfo()) {
				mErrors.copyAllErrors(mLAComDB.mErrors);
				buildError("outputXML", "在取得LACom的数据时发生错误");
				return;
			}
			mLAComSchema = mLAComDB.getSchema(); // 保存银行代码信息

			texttag.add("LCCont.BankCode", mLAComSchema.getName()); // 代理机构
			texttag.add("LCCont.AgentType", tLABranchGroupDB.getName()); // 业务分布及业务组.
		}

		tSql = "select riskcode from lcpol where contno = '"
				+ "?contno?" + "' and polno = mainpolno ";
		sqlbv1.sql(tSql);
		sqlbv1.put("contno", tLCContDB.getContNo());
		SSRS mainpol = new SSRS();
		mainpol = tempExeSQL.execSQL(sqlbv1);
		if (mainpol == null || mainpol.getMaxRow() == 0) {
			buildError("outputXML", "在取得保单主险数据时发生错误");
			return;
		}

		if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
			tSql = "select (case when (select codename from ldcode where codetype = 'paymode' and code = "
					+ "(select paymode from ljtempfeeclass b "
					+ " where b.tempfeeno = a.tempfeeno "
					+ " and rownum = '1')) is not null then (select codename from ldcode where codetype = 'paymode' and code = "
					+ "(select paymode from ljtempfeeclass b "
					+ " where b.tempfeeno = a.tempfeeno "
					+ " and rownum = '1'))  else '未缴费' end)"
					+ " from ljtempfee a "
					+ " where a.otherno = '" + "?otherno?" + "'";
		}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
			tSql = "select (case when (select codename from ldcode where codetype = 'paymode' and code = "
					+ "(select paymode from ljtempfeeclass b "
					+ " where b.tempfeeno = a.tempfeeno "
					+ "  limit 0,1)) is not null then (select codename from ldcode where codetype = 'paymode' and code = "
					+ "(select paymode from ljtempfeeclass b "
					+ " where b.tempfeeno = a.tempfeeno "
					+ "  limit 0,1))  else '未缴费' end)"
					+ " from ljtempfee a "
					+ " where a.otherno = '" + "?otherno?" + "'";
		}
		sqlbv1.sql(tSql);
		sqlbv1.put("otherno", tLCContDB.getContNo());
		SSRS paymode = new SSRS();
		paymode = tempExeSQL.execSQL(sqlbv1);
		// if (paymode != null || paymode.getMaxRow() != 0) {
		// buildError("outputXML", "在取得缴费方式数据时发生错误");
		// return;
		// }

		/**
		 * ==========================================================================
		 * 修改人 ： 万泽辉 修改时间： 2005/12/07 ManageComName: 取6位编码的中支机构
		 * LABranchGroup.Name：取8位编码的营业销售部 Sumprem : 取用标准的格式：0.00
		 * ==========================================================================
		 */
		Double tDouble = new Double(mRealPayMoney);
		double Sumprem = tDouble.doubleValue();
		String mSumprem = "￥ " + mDecimalFormat.format(Sumprem);

		texttag.add("BarCode1", "UN008");
		texttag
				.add(
						"BarCodeParam1",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");
		texttag.add("BarCode2", mLOPRTManagerSchema.getPrtSeq());
		texttag
				.add(
						"BarCodeParam2",
						"BarHeight=20&BarRation=3&BarWidth=1&BgColor=FFFFFF&ForeColor=000000&XMargin=10&YMargin=10");

		texttag.add("LCCont.ProposalContNo", tLCContDB.getContNo());
		texttag.add("LAAgent.Name", getAgentName(tLCContDB.getAgentCode()));
		texttag.add("LCCont.AppntName", tLCContDB.getAppntName());
		texttag.add("LAAgent.AgentCode", tLCContDB.getAgentCode());
		texttag.add("LABranchGroup.Name", getComName(tLCContDB.getManageCom())
				+ " " + getUpComName(tLAAgentDB.getBranchCode()));
		texttag.add("Prem", mSumprem);
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
	private String getComName(String strComCode) {
		LDComDB tLDComDB = new LDComDB();

		tLDComDB.setComCode(strComCode);
		if (!tLDComDB.getInfo()) {
			mErrors.copyAllErrors(tLDComDB.mErrors);
			buildError("outputXML", "在取得Ldcom的数据时发生错误");
		}
		return tLDComDB.getName();
	}

	public String getUpComName(String comcode) {
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		LABranchGroupDB ttLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(comcode);
		if (!tLABranchGroupDB.getInfo()) {
			this.buildError("getUpComName", comcode + "机构不存在！");
			return null;
		} else {
			ttLABranchGroupDB.setAgentGroup(tLABranchGroupDB.getUpBranch());
			if (!ttLABranchGroupDB.getInfo()) {
				this.buildError("getUpComName", "在查找comcode所属的营业部时出错！");
				return null;
			} else {
				return ttLABranchGroupDB.getName();
			}
		}
	}

	private String getAgentName(String strAgentCode) throws Exception {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(strAgentCode);
		if (!tLAAgentDB.getInfo()) {
			mErrors.copyAllErrors(tLAAgentDB.mErrors);
			throw new Exception("在取得LAAgent的数据时发生错误");
		}
		return tLAAgentDB.getName();
	}

}
