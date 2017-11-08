package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LABranchGroupDB;
import com.sinosoft.lis.db.LCAppntIndDB;
import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LDCode1DB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.db.LJSPayPersonDB;
import com.sinosoft.lis.db.LYBankLogDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LCAppntIndSchema;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LDCode1Schema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.vschema.LABranchGroupSet;
import com.sinosoft.lis.vschema.LCAppntIndSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.SQLwithBindVariables;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author 刘岩松
 * function :续期保费银行转帐清单的公用方法
 * @version 1.0
 * @date 2004-4-27
 */

public class PremBankPubFun {
private static Logger logger = Logger.getLogger(PremBankPubFun.class);
	public CErrors mErrors = new CErrors();

	public PremBankPubFun() {
	}

	// 从前台接受数据
	/***************************************************************************
	 * Function:在LYBankLog中查询出要打印的批次号码的函数 Author：lys Date：2004-4-27
	 * Notice:管理机构要以登陆的管理机构为准。与界面上录入的管理机构是无关的。
	 */

	public LYBankLogSet queryData(String tStartDate, String tEndDate,
			String tFlag, String tComCode) {
		logger.debug("查询银行日志的信息！");
		LYBankLogDB tLYBankLogDB = new LYBankLogDB();
		LYBankLogSet tLYBankLogSet = new LYBankLogSet();
		// 要将bankcode去掉
		String getNotice_Sql = "select * from LYBankLog where StartDate >='"
				+ "?tStartDate?" + "' and StartDate <='" + "?tEndDate?"
				+ "' and LogType = '" + "?tFlag?" + "' and ComCode = '" + "?tComCode?"
				+ "' and ReturnDate is not null";
		SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		sqlbv1.sql(getNotice_Sql);
		sqlbv1.put("tStartDate", tStartDate);
		sqlbv1.put("tEndDate", tEndDate);
		sqlbv1.put("tFlag", tFlag);
		sqlbv1.put("tComCode", tComCode);
		logger.debug("查询批次号码的SQL:" + getNotice_Sql);
		tLYBankLogSet = tLYBankLogDB.executeQuery(sqlbv1);
		if (tLYBankLogDB.mErrors.needDealError()) {
			buildError("submitData", "银行日志表查询失败!");
		}
		int i_count = tLYBankLogSet.size();
		if (i_count == 0) {
			buildError("submitData", "在该起始日期和结束日期之间没有交易批次，请确认起止日期是否正确");
		}
		return tLYBankLogSet;
	}

	// 得到银行信息
	public LDBankSchema getBankInfo(String tBankCode) {
		logger.debug("开始执行查询银行信息");
		LDBankDB tLDBankDB = new LDBankDB();
		tLDBankDB.setBankCode(tBankCode);
		if (!tLDBankDB.getInfo()) {
			buildError("submitData", "银行信息查询错误！");
		}
		return tLDBankDB.getSchema();
	}

	public void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PayNoticeF1PBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public String getErrInfo(String tCodeType, String tCode, String tCode1) {
		String tErrReason = "";
		logger.debug("查询LDCode1中的错误信息！");
		LDCode1DB tLDCode1DB = new LDCode1DB();
		tLDCode1DB.setCodeType(tCodeType);
		tLDCode1DB.setCode(tCode);
		tLDCode1DB.setCode1(tCode1);
		if (!tLDCode1DB.getInfo()) {
			if("9999".equals(tCode1))
				return "记录不匹配";
			return "没有指明错误原因"+tCode1;
		}
		LDCode1Schema tLDCode1Schema = new LDCode1Schema();
		tLDCode1Schema.setSchema(tLDCode1DB.getSchema());
		tErrReason = tLDCode1Schema.getCodeName();
		return tErrReason;
	}

	// 更加LYReturnFromBank和LYReturnFromBankB的返回数据对LJSPayPerson和LJSPayGrp进行遍历，查询出续期的情况
	// 参数：1---号码；2---号码类型
	// 对应应收表中的其他号码类型。
	// 1 ---表示集体保单号
	// 2 ---表示个人保单号
	// 3 ---表示批改号
	// 4 ---表示合同投保单号
	// 5 ---表示集体投保单号
	// 6 ---表示个人投保单号
	// 7 ---表示合同印刷号（进行首期交费）
	// 8 ---表示集体印刷号
	// 9 ---表示个人印刷号

	public SQLwithBindVariables getPolInfo(String tPolNo, String tNoType, String tAgentState) {
		String pol_sql = "";
		String agent_sql = "";
		if (tAgentState.equals("1")) {
			agent_sql = " and AgentCode in (select AgentCode from LAAgent where AgentState in ('01','02')) ";
		} else {
			agent_sql = " and AgentCode in (select AgentCode from LAAgent where AgentState not in ('01','02')) ";

		}
		logger.debug("agent_sql" + agent_sql);
		if (!(tNoType == null || tNoType.equals(""))) {
			if (tNoType.trim().equals("2")) {
				pol_sql = "select polno,mainpolno,agentcode,agentgroup  from LCPol where PolNo = '"
						+ "?tPolNo?"
						+ "'"
						+ agent_sql
						+ "   union all select PolNo,MainPolNo,agentcode,agentgroup from LBPol where PolNo = '"
						+ "?tPolNo?" + "' " + agent_sql;
			}
			if (tNoType.trim().equals("6")) {
				pol_sql = "select PolNo,MainPolNo,agentcode,agentgroup from LCPol where ProposalNo= '"
						+ "?tPolNo?"
						+ "'"
						+ agent_sql
						+ " union all select PolNo,MainPolNo ,agentcode,agentgroup from LBPol where ProposalNo = '"
						+ "?tPolNo?" + "' " + agent_sql;
			}
			if (tNoType.trim().equals("9")) {
				pol_sql = "select PolNo,MainPolNo,agentcode,agentgroup from LCPol where PrtNo = '"
						+ "?tPolNo?"
						+ "'"
						+ agent_sql
						+ " union all select PolNo,MainPolNo ,agentcode,agentgroup from LBPol where PrtNo = '"
						+ "?tPolNo?" + "' " + agent_sql;
			}
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(pol_sql);
		sqlbv.put("tPolNo", tPolNo);
		logger.debug("查询的语句是" + pol_sql);
		return sqlbv;
	}

	// 获得LJAPayPerson 和LJAPayGrp 获得续期交费信息
	public LJSPayPersonSet getLJSPayPerson(String tPayCode) {
		LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
		String Pxq_sql = "select * from LJSPayPerson where polno = '"
				+ "?polno?" + "' and PayCount> '1' ";
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(Pxq_sql);
		sqlbv.put("polno", tPayCode.trim());
		logger.debug("查询的语句是" + Pxq_sql);
		LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
		tLJSPayPersonSet.set(tLJSPayPersonDB.executeQuery(sqlbv));
		buildError("submitData", "没有要打印的数据");
		return tLJSPayPersonSet;
	}

	// 获得划帐次数的函数

	public String getLJSPay(String tGetNoticeNo) {
		String HZ_count = "";
		LJSPaySet tLJSPaySet = new LJSPaySet();
		LJSPayDB tLJSPayDB = new LJSPayDB();
		tLJSPayDB.setGetNoticeNo(tGetNoticeNo);

		tLJSPaySet.set(tLJSPayDB.query());
		if (tLJSPaySet.size() == 0) {
			HZ_count = "过期";
		} else {
			HZ_count = String.valueOf(tLJSPaySet.get(1).getSendBankCount());
		}
		return HZ_count;
	}

	public String getBankAccNo(String tPayNo) {
		String BankAccNo = "";
		LJAPaySet tLJAPaySet = new LJAPaySet();
		LJAPayDB tLJAPayDB = new LJAPayDB();
		tLJAPayDB.setPayNo(tPayNo);
		tLJAPayDB.getInfo();
		BankAccNo = tLJAPayDB.getSchema().getBankAccNo().trim();
		return BankAccNo;

	}

	/***************************************************************************
	 * Function :代理人信息的查询 Parameter:代理人代码 Values :代理人姓名、电话
	 */
	// 获得代理人姓名的过程
	public LAAgentSchema getAgentInfo(String tAgentCode) {
		LAAgentDB tLAAgentDB = new LAAgentDB();
		tLAAgentDB.setAgentCode(tAgentCode.trim());
		tLAAgentDB.getInfo();
		LAAgentSchema tLAAgentSchema = new LAAgentSchema();
		tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
		return tLAAgentSchema;
	}

	public String getAgentGroup(String AgentGroup) {
		String tAgentGroup = "";
		LABranchGroupDB tLABranchGroupDB = new LABranchGroupDB();
		tLABranchGroupDB.setAgentGroup(AgentGroup);
		LABranchGroupSet tLABranchGroupSet = new LABranchGroupSet();
		tLABranchGroupSet.set(tLABranchGroupDB.query());
		if (tLABranchGroupSet.size() == 0) {
			tAgentGroup = "无机构";
		} else {
			tAgentGroup = tLABranchGroupSet.get(1).getName();
		}

		return tAgentGroup;
	}

	// 获得投保人信息的函数
	public LCAppntIndSchema getAppntInfo(String tAppntNo) {
		LCAppntIndSet tLCAppntIndSet = new LCAppntIndSet();
		LCAppntIndSchema tLCAppntIndSchema = new LCAppntIndSchema();
		LCAppntIndDB tLCAppntIndDB = new LCAppntIndDB();
		tLCAppntIndDB.setCustomerNo(tAppntNo);
		tLCAppntIndSet.set(tLCAppntIndDB.query());
		if (tLCAppntIndSet.size() > 0) {
			tLCAppntIndSchema.setSchema(tLCAppntIndSet.get(1));
		} else {
			tLCAppntIndSchema.setName("无记录");
		}
		return tLCAppntIndSchema;
	}

	// 获取银行的成功标志
	public String getBankSuccFlag(String tBankCode) {
		logger.debug("银行代码是" + tBankCode);
		try {
			LDBankSchema tLDBankSchema = new LDBankSchema();
			tLDBankSchema.setBankCode(tBankCode);
			tLDBankSchema.setSchema(tLDBankSchema.getDB().query().get(1));
			return tLDBankSchema.getAgentPaySuccFlag();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException("获取银行扣款成功标志信息失败！(getBankSuccFlag) "
					+ e.getMessage());
		}
	}

	public String getDFBankSuccFlag(String tBankCode) {
		logger.debug("银行代码是" + tBankCode);
		try {
			LDBankSchema tLDBankSchema = new LDBankSchema();
			tLDBankSchema.setBankCode(tBankCode);
			tLDBankSchema.setSchema(tLDBankSchema.getDB().query().get(1));
			return tLDBankSchema.getAgentGetSuccFlag();
		} catch (Exception e) {
			e.printStackTrace();
			throw new NullPointerException("获取银行扣款成功标志信息失败！(getBankSuccFlag) "
					+ e.getMessage());
		}
	}

	// 校验成功标志的方法
	public boolean verifyBankSuccFlag(String bankSuccFlag, String bBankSuccFlag) {
		int i;
		boolean passFlag = false;

		String[] arrSucc = PubFun.split(bankSuccFlag, ";");
		String tSucc = bBankSuccFlag;

		for (i = 0; i < arrSucc.length; i++) {
			if (arrSucc[i].equals(tSucc)) {
				passFlag = true;
				break;
			}
		}

		return passFlag;
	}

	// 获得LJAPay的信息
	public LJAPaySchema getLJAPayInfo(String tPayNo) {
		LJAPaySchema tLJAPaySchema = new LJAPaySchema();
		LJAPayDB tLJAPayDB = new LJAPayDB();
		tLJAPayDB.setPayNo(tPayNo);
		tLJAPayDB.getInfo();
		tLJAPaySchema.setSchema(tLJAPayDB.getSchema());
		return tLJAPaySchema;
	}

	public SQLwithBindVariables getPolType(String tPolNo, String tAgentState) {

		String agent = "";
		if (tAgentState.trim().equals("1")) {
			agent = " and AgentCode in (select AgentCode from LAAgent where AgentState  in ('01','02')) ";
		} else {
			agent = " and AgentCode in (select AgentCode from LAAgent where AgentState  not in ('01','02')) ";
		}
		String sql = " select PolNo ,MainPolNo,AgentCode,AgentGroup ,AppntNo from lcpol where PolNo = '"
				+ "?tPolNo?"
				+ "'"
				+ agent
				+ " union all select PolNo ,MainPolNo,AgentCode,AgentGroup,AppntNo from lbpol where PolNo = '"
				+ "?tPolNo?" + "' " + agent;
		logger.debug("查询的语句是" + sql);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("tPolNo", tPolNo);
		return sqlbv;
	}

	public static void main(String[] args) {
		// XQPremBankErrBL tXQPremBankErrBL = new XQPremBankErrBL();
		PremBankPubFun tPremBankPubFun = new PremBankPubFun();
		tPremBankPubFun.getPolInfo("111111111111111", "6", "1");

	}
}
