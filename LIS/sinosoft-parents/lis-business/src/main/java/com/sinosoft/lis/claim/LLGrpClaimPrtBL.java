package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.claimgrp.Stringtools;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * Title: 团险理赔打印
 * 
 * Description:团险理赔打印
 * 
 * 1、立案结论打印：不予立案通知书
 * 
 * 2、给付通知打印
 * 
 * 3、理赔批单
 * 
 * 4、支付清单
 * 
 * 5、赔款清单
 * 
 * 6、理赔决定通知书
 * 
 * Copyright: Copyright (c) 2005
 * 
 * Company:sinosoft
 * 
 * @author mw,2009-04-14
 */

public class LLGrpClaimPrtBL implements PrintService {
private static Logger logger = Logger.getLogger(LLGrpClaimPrtBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 前台传入的数据 */
	private VData mInputData = new VData();

	private String mOperate = "";

	/** 往后面传输数据的容器 */
	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	private GlobalInput mG = new GlobalInput();

	private String RgtNo = ""; // 立案号

	private String CustomerNo = ""; // 出险人

	public LLGrpClaimPrtBL() {
	}

	/**
	 * 提交数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印开始---------LLPRBDealORFactPayBL---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 校验检查外部传入的数据
		if (!checkInputData()) {
			return false;
		}

		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		logger.debug("-----理赔清单打印结束----LLPRBDealORFactPayBL---");

		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		mOperate = cOperate;
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中

		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
		mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);

		if ("RgtConclusion".equals(mOperate)) {
			RgtNo = (String) mTransferData.getValueByName("RgtNo"); // 立案号
			CustomerNo = (String) mTransferData.getValueByName("CustomerNo"); // 出险人
		} else if ("PayNotice".equals(mOperate)) {
			RgtNo = (String) mTransferData.getValueByName("RgtNo"); // 立案号
			CustomerNo = (String) mTransferData.getValueByName("CustomerNo"); // 出险人
		} else if ("ClaimBatch".equals(mOperate)) {
			RgtNo = (String) mTransferData.getValueByName("RgtNo"); // 立案号
		} else if ("ClaimPay".equals(mOperate)) {
			RgtNo = (String) mTransferData.getValueByName("RgtNo"); // 立案号
		} else if ("GetBill".equals(mOperate)) {
			RgtNo = (String) mTransferData.getValueByName("RgtNo"); // 立案号
		} else if ("ClaimPayTR".equals(mOperate)) {
			RgtNo = (String) mTransferData.getValueByName("RgtNo"); // 立案号
		}

		return true;
	}

	/**
	 * 校验传入的报案信息是否合法 输出：如果发生错误则返回false,否则返回true
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		if ("RgtConclusion".equals(mOperate)) {// 立案结论打印：不予立案通知书
			if (dealRgtConclusion() == false) {
				return false;
			}
		} else if ("PayNotice".equals(mOperate)) {// 给付通知打印
			if (dealPayNotice() == false) {
				return false;
			}
		} else if ("ClaimBatch".equals(mOperate)) {// 理赔批单
			if (dealClaimBatch() == false) {
				return false;
			}
		} else if ("ClaimPay".equals(mOperate)) {// 支付清单
			if (dealClaimPay() == false) {
				return false;
			}
		} else if ("GetBill".equals(mOperate)) {// 赔款清单
			if (dealGetBill() == false) {
				return false;
			}
		} else if ("ClaimPayTR".equals(mOperate)) {// 理赔决定通知书
			if (dealClaimPayTR() == false) {
				return false;
			}
		}

		return true;
	}

	// 立案结论打印：不予立案通知书
	private boolean dealRgtConclusion() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		tXmlExport.createDocument("RgtConclusion.vts", "");

		logger.debug("----------以下查询，为变量赋值-----------");
		logger.debug("RgtNo=" + RgtNo);

		// 团体保单号
		String GrpContNo = tExeSQL.getOneValue("select grpcontno from llreport where rptno='" + RgtNo + "'");

		// 出险人姓名
		String tSql = "select a.name from ldperson a where " + "a.customerno = '" + CustomerNo + "'";
		String tName = tExeSQL.getOneValue(tSql);

		// 理赔类型
		String ClaimType = tLLPRTPubFunBL.getSLLReportReason(RgtNo, CustomerNo);

		// 结案日期
		String EndCaseDate = tExeSQL
				.getOneValue("select to_char(endcasedate,'yyyy-mm-dd') from llregister where rgtno='" + RgtNo
						+ "'");

		// 不予立案原因
		String NoRgtReason = tExeSQL
				.getOneValue("select codename from ldcode where codetype='llnorgtreason' "
						+ " and code=(select norgtreason from llregister where rgtno='" + RgtNo + "')");

		// 保单服务人员和电话-()------------------------------------------------------
		String tAgentCodeSql = "select AgentCode from lcgrpcont where 1=1 " + " and grpcontno='" + GrpContNo
				+ "'";
		String tAgentCode = tExeSQL.getOneValue(tAgentCodeSql);

		String SeveiceName = "";
		String tel = "";
		if (tAgentCode.equals("") || tAgentCode == null) {
			SeveiceName = "";
			tel = "";
		} else {
			LAAgentDB tLAAgentDB = new LAAgentDB();
			tLAAgentDB.setAgentCode(tAgentCode);
			tLAAgentDB.getInfo();
			SeveiceName = tLAAgentDB.getName();
			tel = tLAAgentDB.getPhone();
			if (tel == null) {
				tel = "    ";
			}
		}

		// 受理人
		String operator = "";
		LLSubReportSchema ttLLSubReportSchema = new LLSubReportSchema();
		ttLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(RgtNo, CustomerNo);
		operator = ttLLSubReportSchema.getOperator();
		String checkerSQL = "select UserName from llclaimuser " + "where usercode='" + operator + "'";
		String jingbanren = tExeSQL.getOneValue(checkerSQL);

		// 通知日期
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" + StrTool.getDay() + "日";

		// 给模板中的变量赋值
		tTextTag.add("DOFGrpContNo", GrpContNo);// 团体保单号
		tTextTag.add("DOFRgtNo", RgtNo);// 团体立案号
		tTextTag.add("DOFCustomerName", tName);// 出险人
		tTextTag.add("DOFMngCom", ClaimType);// 理赔类型
		tTextTag.add("DOFCustomerName1", tName);// 出险人

		tTextTag.add("DOFEndCaseDate", EndCaseDate);// 理赔决定日期
		tTextTag.add("DOFNoRgtReason", NoRgtReason);// 不予立案原因

		tTextTag.add("DOFSeveiceName", SeveiceName);// 保单服务人员
		tTextTag.add("DOFtel", "95596");// 联系电话
		tTextTag.add("DOFjingbanren", jingbanren);// 受理人
		tTextTag.add("DOFSysDate", SysDate);// 通知日期
		logger.debug("tTextTag.size()=" + tTextTag.size());

		tXmlExport.addTextTag(tTextTag);

		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);

		return true;
	}

	// 给付通知打印
	private boolean dealPayNotice() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();

		tXmlExport.createDocument("PayNotice.vts", "");
		logger.debug("RgtNo=" + RgtNo);

		// 取保单号
		String GrpcontNo = tExeSQL.getOneValue("select GrpContNo from LLRegister where RgtNo='" + RgtNo
				+ "'  and EndCaseFlag='1' and RgtNo not in (select RgtNo from LLClaimpolicy where RgtNo='"
				+ RgtNo + "'  and GiveType ='1')");
		logger.debug("GrpcontNo=" + GrpcontNo);

		// 出险日期
		String AccDate = tExeSQL
				.getOneValue("select to_char(a.accdate,'yyyy-mm-dd') from llcase a where a.rgtno='" + RgtNo
						+ "'");

		// 出险人姓名
		String tSql = "select a.name from ldperson a where " + "a.customerno = '" + CustomerNo + "'";
		String CustomerName = tExeSQL.getOneValue(tSql);

		// 给模板中的变量赋值
		tTextTag.add("CustomerName", CustomerName);// 
		tTextTag.add("CAccDate", AccDate);// 

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[6];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";

		String Sql = "select d.name, b.actugetno,"
				+ " (select c.codename from ldcode c where c.codetype = 'relation' and c.code=d.relationtoinsured),"
				+ " (select a.codename from ldcode a where a.codetype = 'paymode' and a.code=b.paymode),"
				+ " b.drawerid, b.sumgetmoney, b.bankcode, d.bankaccno, b.accname"
				+ " from ljaget b,llbnfGather d where b.othernotype = '5'"
				+ " and d.caseno = b.otherno and d.otherno = b.actugetno" + " and b.otherno = '" + RgtNo
				+ "'" + " and b.insuredno='" + CustomerNo + "'";
		logger.debug("--给付通知查询sql:" + Sql);

		SSRS tSSRS = tExeSQL.execSQL(Sql);

		double dRealPay = 0.00;
		String[] stra = null;
		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				stra = new String[6];
				stra[0] = "受益人";
				stra[1] = tSSRS.GetText(i, 1);
				stra[2] = "实付号";
				stra[3] = tSSRS.GetText(i, 2);
				stra[4] = "与被保险人关系";
				stra[5] = tSSRS.GetText(i, 3);
				tListTable.add(stra);

				stra = new String[6];
				stra[0] = "支付方式";
				stra[1] = tSSRS.GetText(i, 4);
				stra[2] = "证件号码";
				stra[3] = tSSRS.GetText(i, 5);
				stra[4] = "受益金额";
				stra[5] = tSSRS.GetText(i, 6);
				tListTable.add(stra);

				stra = new String[6];
				stra[0] = "支付银行";
				stra[1] = tSSRS.GetText(i, 7);
				stra[2] = "银行帐号";
				stra[3] = tSSRS.GetText(i, 8);
				stra[4] = "银行帐户名";
				stra[5] = tSSRS.GetText(i, 9);
				tListTable.add(stra);

				dRealPay = dRealPay + Stringtools.stringtodouble(tSSRS.GetText(i, 6));
			}
		} else {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}

		// -- 医疗账单费用 LLCaseReceipt Fee, 收据张数: LLFeeMain 收据数
		// -- 合理费用 LLCaseReceipt AdjSum
		// -- 费用类型 FeeItemType A:门诊费用,B:住院费用 分类sum金额;
		// -- 各种理赔类型下的理赔金额: select * from llclaimdetail
		// 根据customerno,getdutykind 汇总:
		String Content = "";
		// 医疗账单费用合计
		String Fee = tExeSQL.getOneValue("select nvl(sum(a.fee),0) from LLCaseReceipt a where a.clmno='"
				+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 收据合计张数
		String mainfeenoNum = tExeSQL
				.getOneValue("select nvl(count(a.mainfeeno),0) from LLFeeMain a where a.clmno='" + RgtNo
						+ "' and a.customerno='" + CustomerNo + "'");

		// 合理费用
		String adjsum = tExeSQL
				.getOneValue("select nvl(sum(a.adjsum-a.selfamnt),0) from LLCaseReceipt a where a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 门诊费用
		String adjsumA = tExeSQL
				.getOneValue("select nvl(sum(a.adjsum),0) from LLCaseReceipt a where a.FeeItemType='A' and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 住院费用
		String adjsumB = tExeSQL
				.getOneValue("select nvl(sum(a.adjsum),0) from LLCaseReceipt a where a.FeeItemType='B' and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");

		// 本次赔付医疗保险金
		String RealPay00 = tExeSQL
				.getOneValue("select nvl(sum(RealPay),0) from llclaimdetail a where a.getdutykind in ('100','200') and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 本次残疾保险金
		String RealPay01 = tExeSQL
				.getOneValue("select nvl(sum(RealPay),0) from llclaimdetail a where a.getdutykind in ('101','201') and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 本次身故保险金
		String RealPay02 = tExeSQL
				.getOneValue("select nvl(sum(RealPay),0) from llclaimdetail a where a.getdutykind in ('102','202') and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 本次高残保险金
		String RealPay03 = tExeSQL
				.getOneValue("select nvl(sum(RealPay),0) from llclaimdetail a where a.getdutykind in ('103','203') and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 本次重大疾病保险金
		String RealPay04 = tExeSQL
				.getOneValue("select nvl(sum(RealPay),0) from llclaimdetail a where a.getdutykind in ('104','204') and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");
		// 本次特种疾病保险金
		String RealPay05 = tExeSQL
				.getOneValue("select nvl(sum(RealPay),0) from llclaimdetail a where a.getdutykind in ('105','205') and a.clmno='"
						+ RgtNo + "' and a.customerno='" + CustomerNo + "'");

		if (Double.parseDouble(Fee) > 0 && Double.parseDouble(mainfeenoNum) > 0) {
			Content += "    您此次所提交的理赔申请材料中，医疗账单费用合计:" + Fee + "元。收据合计：" + mainfeenoNum
					+ "张。    依据保险合同的约定，经过审慎核定，合理费用为：" + adjsum + "元。其中:门诊费用：" + adjsumA + "元。住院费用：" + adjsumB
					+ "元。";
		}

		// 各理赔类型的金额
		String RealPay = "";
		if (Double.parseDouble(RealPay00) > 0) {
			RealPay += "医疗保险金" + RealPay00 + "元,";
		}
		if (Double.parseDouble(RealPay01) > 0) {
			RealPay += "残疾保险金" + RealPay01 + "元,";
		}
		if (Double.parseDouble(RealPay02) > 0) {
			RealPay += "身故保险金" + RealPay02 + "元,";
		}
		if (Double.parseDouble(RealPay03) > 0) {
			RealPay += "高残保险金" + RealPay03 + "元,";
		}
		if (Double.parseDouble(RealPay04) > 0) {
			RealPay += "重大疾病保险金" + RealPay04 + "元,";
		}
		if (Double.parseDouble(RealPay05) > 0) {
			RealPay += "特种疾病保险金" + RealPay05 + "元,";
		}
		// 本保险年度累计赔付
		String cvalidate = tExeSQL.getOneValue("select a.cvalidate from lcgrpcont a where a.grpcontno='"
				+ GrpcontNo + "'");
		int PolYear = PubFun.calPolYear(cvalidate, PubFun.getCurrentDate());
		String StartDate = PubFun.newCalDate(cvalidate, "Y", PolYear - 1);// 本保险年度开始日期
		String EndDate = PubFun.newCalDate(cvalidate, "Y", PolYear);// 本保险年度结束日期
		String RealPaySum = tExeSQL
				.getOneValue("select nvl(sum(a.sumgetmoney),0) from ljaget a where a.othernotype='5' and a.insuredno='"
						+ CustomerNo + "' and a.makedate>='" + StartDate + "' and a.makedate<='" + EndDate
						+ "'");
		Content += "    本次赔付" + RealPay + "您在本保险年度累计赔付达：" + RealPaySum + "元。";

		tTextTag.add("dRealPay", dRealPay);// 
		tTextTag.add("Content", Content);//
		tXmlExport.addTextTag(tTextTag);
		tXmlExport.addListTable(tListTable, Title);// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组

		logger.debug("---以下 将tXmlExport打包，返回前台--");
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);

		return true;
	}

	// 理赔批单
	private boolean dealClaimBatch() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();

		tXmlExport.createDocument("ClaimBatch.vts", "");

		// 团单号
		String GrpContNo = tExeSQL
				.getOneValue("select GrpContNo from llregister where rgtno='" + RgtNo + "'");

		// 人数
		String CountInsured = tExeSQL
				.getOneValue("select count(*) from (select unique customerno from llclaimdetail where  ClmNo='"
						+ RgtNo + "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))");
		// 取一个被保险人姓名
		String InsuredName = tExeSQL.getOneValue("select Name from lcinsured where GrpContNo='" + GrpContNo
				+ "' and InsuredNO in (select InsuredNO from LLClaimpolicy where  ClmNo='" + RgtNo
				+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))");
		if (Stringtools.stringtoint(CountInsured) > 1) {
			InsuredName = InsuredName.trim() + " 等 " + CountInsured + " 人";
		}

		// 批改日期
		String EndCaseDate = tExeSQL
				.getOneValue("select to_char(EndCaseDate,'yyyy-mm-dd') from LLRegister where RgtNo='" + RgtNo
						+ "' ");

		// 全部保险责任形成一个串
		String Duty = "";
		String SqlDuty = "select unique trim(b.CodeName)  GetDutyName   "
				+ " from LLClaimDetail a,ldcode b  " + "  where b.codetype='ngetdutykind' "
				+ "  and a.GetDutyKind=b.code    and a.RgtNo='" + RgtNo
				+ "'  and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2')";
		SSRS tSSRS = tExeSQL.execSQL(SqlDuty);
		if (tSSRS == null) {
			CError tError = new CError();
			tError.moduleName = "LLGrpClaimPrtBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String tmp = tSSRS.GetText(i, 1);
			if (tmp == null) {
				tmp = "";
			}
			if (i == 1) {
				Duty = Duty + "" + tmp.trim();
			} else {
				Duty = Duty + "、" + tmp.trim();
			}
		}

		// 判断是否有死亡
		String DeadStr = "";
		if (Duty.indexOf("死亡") >= 0 || Duty.indexOf("身故") >= 0) {
			DeadStr = "，以上被保险人责任终止。";
		} else {
			DeadStr = "。";
		}

		// 全部保单号形成一个串
		String StrGrpContNO = "";
		String SqlGrpContNO = "select unique grpcontno from lcpol  where PolNo in (select unique polno from llclaimpolicy  where  RgtNo='"
				+ RgtNo + "'  and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))";
		tSSRS = tExeSQL.execSQL(SqlGrpContNO);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			String tmp = tSSRS.GetText(i, 1); // .getString(1);
			if (tmp == null) {
				tmp = "";
			}
			if (i == 1) {
				StrGrpContNO = StrGrpContNO + "" + tmp.trim();
			} else {
				StrGrpContNO = StrGrpContNO + "、" + tmp.trim();
			}
		}

		// 计算总共的赔付金额
		String RealPay = tExeSQL.getOneValue("select sum(RealPay) from LLClaim where RgtNo='" + RgtNo
				+ "'   and (GiveType='0' or GiveType='4')");
		RealPay = Stringtools.stringtostring(RealPay, 2);

		tTextTag.add("RgtNo", RgtNo);// 立案号
		tTextTag.add("InsuredName", InsuredName);// 被保险人
		tTextTag.add("EndCaseDate", EndCaseDate);// 批改日期
		tTextTag.add("Duty", Duty);// 全部保险责任
		tTextTag.add("RealPay", RealPay);// 给付理赔保险金
		logger.debug("tTextTag.size()=" + tTextTag.size());

		tXmlExport.addTextTag(tTextTag);

		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		return true;
	}

	// 支付清单
	private boolean dealClaimPay() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();

		tXmlExport.createDocument("ClaimPay.vts", "");
		logger.debug("RgtNo=" + RgtNo);

		// 取投保单位
		String CustomerName = tExeSQL.getOneValue("select GrpName from LLRegister where RgtNo='" + RgtNo
				+ "' and rownum<=1");

		// 取保单号
		String GrpcontNo = tExeSQL.getOneValue("select GrpContNo from LLRegister where RgtNo='" + RgtNo
				+ "'  and rownum<=1");

		// 给模板中的变量赋值
		tTextTag.add("CustomerName", CustomerName);
		tTextTag.add("GrpcontNo", GrpcontNo);
		tTextTag.add("RgtNo", RgtNo);

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[8];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";

		// 个人客户号
		String InsuredNo = "";
		// 姓名
		String InsuredName = "";
		// 身份证号
		String IDNo = "";
		// 本次赔付
		String RealPay = "";
		// 合计金额
		double SumRealPay = 0.00;
		// 收款人
		String AccName = "";
		// 收款银行
		String BankName = "";
		// 银行帐户
		String BankAccNo = "";
		// 实付号
		String actugetno = "";

		// 数据准备SQL文
		String Sql = "select CustomerNo,sum(realpay) from LLClaimDetail where "
				+ " GiveType='0'" // 不是拒赔
				+ " and clmno in (select RgtNo from  LLRegister where EndCaseDate is not null "
				+ " and EndCaseFlag='1' and RgtNo='" + RgtNo + "') and ClmNo='" + RgtNo + "'"
				+ " group by CustomerNo";
		SSRS tSSRS = tExeSQL.execSQL(Sql);
		if (tSSRS == null) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			InsuredNo = tSSRS.GetText(i, 1);
			if (InsuredNo == null) {
				buildError("dealdata", "打印理赔支付清单时，被保险人客号为空值,赔案号:" + RgtNo);
				return false;
			}
			InsuredName = tExeSQL.getOneValue("select trim(Name) from LCInsured where GrpContNo='"
					+ GrpcontNo + "' and  Insuredno='" + InsuredNo + "'");
			IDNo = tExeSQL
					.getOneValue("select trim(IDNO) from LDPerson where CustomerNo='" + InsuredNo + "'");

			Sql = " select a.sumgetmoney, a.drawer, "
					+ " nvl((select codename from ldcode where codetype='bank' and code=a.bankcode),''), "
					+ " nvl(a.bankaccno,''), a.actugetno "
					+ " from ljaget a where a.othernotype = '5' and a.otherno = '" + RgtNo + "' "
					+ " and a.insuredno = '" + InsuredNo + "' ";
			SSRS mSSRS = tExeSQL.execSQL(Sql);
			if (mSSRS == null) {
				buildError("dealdata", "查询实付信息出错!");
				return false;
			}
			for (int j = 1; j <= mSSRS.getMaxRow(); j++) {
				RealPay = mSSRS.GetText(j, 1);
				AccName = mSSRS.GetText(j, 2);
				BankName = mSSRS.GetText(j, 3);
				BankAccNo = mSSRS.GetText(j, 4);
				actugetno = mSSRS.GetText(j, 5);

				String[] stra = new String[8];
				stra[0] = InsuredNo;
				stra[1] = InsuredName;
				stra[2] = IDNo;
				stra[3] = RealPay;
				stra[4] = AccName;
				stra[5] = BankName;
				stra[6] = BankAccNo;
				stra[7] = actugetno;
				tListTable.add(stra);
				SumRealPay = SumRealPay + Stringtools.stringtodouble(RealPay);
			}
		} // 循环结束

		tTextTag.add("SumRealPay", SumRealPay);
		tTextTag.add("CurrentDate", PubFun.getCurrentDate());
		tXmlExport.addTextTag(tTextTag);
		tXmlExport.addListTable(tListTable, Title);// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组

		logger.debug("---以下 将tXmlExport打包，返回前台--");
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);

		return true;
	}

	// 赔款清单
	private boolean dealGetBill() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();

		tXmlExport.createDocument("GetBill.vts", "");
		logger.debug("RgtNo=" + RgtNo);

		// 取投保单位
		String CustomerName = tExeSQL.getOneValue("select GrpName from LLRegister where RgtNo='" + RgtNo
				+ "' and rownum<=1");

		// 取保单号
		String GrpcontNo = tExeSQL.getOneValue("select GrpContNo from LLRegister where RgtNo='" + RgtNo
				+ "'  and rownum<=1");

		// 给模板中的变量赋值
		tTextTag.add("CustomerName", CustomerName);
		tTextTag.add("GrpcontNo", GrpcontNo);
		tTextTag.add("RgtNo", RgtNo);

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[6];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";

		// 个人客户号
		String InsuredNo = "";
		// 姓名
		String InsuredName = "";
		// 险种
		String RiskCode = "";
		// 保额
		String Amnt = "";
		// 本次赔付
		double SumRealPay = 0.00;
		String RealPay = "";
		// 累计赔付金额
		String ljPay = "";

		// 数据准备SQL文
		String Sql = "select CustomerNo,RiskCode,sum(amnt),sum(realpay) from LLClaimDetail "
				+ " where GiveType='0' " // 不是拒赔
				+ " and 1=1 and ClmNo='" + RgtNo + "' group by CustomerNo,RiskCode ";
		SSRS tSSRS = tExeSQL.execSQL(Sql);
		if (tSSRS == null) {
			buildError("dealdata", "没有要统计的数据!");
			return false;
		}

		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
			InsuredNo = tSSRS.GetText(i, 1);
			if (InsuredNo == null) {
				buildError("dealdata", "打印赔清单时，被保险人客号为空值!赔案号" + RgtNo);
				return false;
			}
			InsuredName = tExeSQL.getOneValue("select name from LCInsured where grpcontno='" + GrpcontNo
					+ "' and InsuredNO='" + InsuredNo + "'");

			RiskCode = tSSRS.GetText(i, 2);
			Amnt = tExeSQL.getOneValue("select amnt from lcpol where GrpcontNo='" + GrpcontNo
					+ "' and riskcode='" + RiskCode + "' and InsuredNo='" + InsuredNo + "'");

			RealPay = Stringtools.stringtostring(tSSRS.GetText(i, 4), 2);

			// 累计赔付金额
			String tmpsql = "select sum(realpay) from LLClaimDetail where GRpContno='" + GrpcontNo
					+ "' and CustomerNo='" + InsuredNo + "'  and RiskCode='" + RiskCode + "'"
					+ "   and RgtNo in (select RgtNo from  LLRegister "
					+ " where EndCaseFlag='1' and clmstate!='80' and EndCaseDate is not null  )";
			ljPay = tExeSQL.getOneValue(tmpsql);
			ljPay = Stringtools.stringtostring(ljPay, 2);

			String[] stra = new String[6];
			stra[0] = InsuredNo;
			stra[1] = InsuredName;
			stra[2] = RiskCode;
			stra[3] = Amnt;
			stra[4] = RealPay;
			stra[5] = ljPay;
			tListTable.add(stra);

			SumRealPay = SumRealPay + Stringtools.stringtodouble(RealPay);
		} // 循环结束

		tTextTag.add("SumRealPay", SumRealPay);
		tTextTag.add("CurrentDate", PubFun.getCurrentDate());
		tXmlExport.addTextTag(tTextTag);
		tXmlExport.addListTable(tListTable, Title);// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组

		logger.debug("---以下 将tXmlExport打包，返回前台--");
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);

		return true;
	}

	// 理赔决定通知书
	private boolean dealClaimPayTR() {
		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		ExeSQL tExeSQL = new ExeSQL();

		tXmlExport.createDocument("ClaimPayTR.vts", "");

		// 团单号
		String GrpContNo = tExeSQL
				.getOneValue("select GrpContNo from llregister where rgtno='" + RgtNo + "'");
		// 人数
		String CountInsured = tExeSQL
				.getOneValue("select count(*) from (select unique InsuredNO from LLClaimpolicy where  ClmNo='"
						+ RgtNo + "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))");
		// 取一个被保险人姓名
		String InsuredName = tExeSQL.getOneValue("select Name from lcinsured where GrpContNo='" + GrpContNo
				+ "' and InsuredNO in (select InsuredNO from LLClaimpolicy where  ClmNo='" + RgtNo
				+ "' and (GiveType='0' or GiveType='4' or GiveType='1' or GiveType='2'))");
		if (Stringtools.stringtoint(CountInsured) > 1) {
			InsuredName = "被保险人：  " + InsuredName.trim() + " 等 " + CountInsured + " 人";
		}

		// 出险日期
		String AccDate = tExeSQL
				.getOneValue("select to_char(a.accdate,'yyyy-mm-dd') from llcase a where a.rgtno='" + RgtNo
						+ "'");

		// 理赔申请决定
		String AuditConclusion = "";
		// 决定的理由
		String AuditNoPassReason = "";
		SSRS tSSRS = tExeSQL
				.execSQL("select a.AuditConclusion,AuditNoPassReason from llclaimuwmain a where a.rgtno='"
						+ RgtNo + "'");
		if ("1".equals(tSSRS.GetText(1, 1))) {// 1-拒付
			AuditConclusion = "拒付";
			AuditNoPassReason = tExeSQL
					.getOneValue("select codename from ldcode a where a.codetype='llprotestreason' and code='"
							+ tSSRS.GetText(1, 2) + "'");
		} else if (("0".equals(tSSRS.GetText(1, 1))) || ("4".equals(tSSRS.GetText(1, 1)))) {// 0-给付,4-审批管理(视同赔付):判断协议给付/通融给付
			SSRS tSSRS1 = tExeSQL
					.execSQL("select b.adjreason,b.AdjRemark from llclaimdetail b where b.clmno='" + RgtNo
							+ "'");
			if ("00".equals(tSSRS1.GetText(1, 1))) {
				AuditConclusion = "协议给付";
				AuditNoPassReason = tSSRS1.GetText(1, 2);
			} else if ("14".equals(tSSRS1.GetText(1, 1))) {
				AuditConclusion = "通融给付";
				AuditNoPassReason = tSSRS1.GetText(1, 2);
			} else {
				buildError("dealdata", "该理赔结论不是拒付/协议给付/通融给付，不能打印此通知书!");
				return false;
			}
		} else {
			buildError("dealdata", "该理赔结论不是拒付/协议给付/通融给付，不能打印此通知书!");
			return false;
		}

		tTextTag.add("GrpContNo", GrpContNo);
		tTextTag.add("InsuredName", InsuredName);
		tTextTag.add("AccDate", AccDate);
		tTextTag.add("AuditConclusion", AuditConclusion);
		tTextTag.add("AuditNoPassReason", AuditNoPassReason);
		tTextTag.add("CurrentDate", PubFun.getCurrentDate());
		logger.debug("tTextTag.size()=" + tTextTag.size());

		tXmlExport.addTextTag(tTextTag);

		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		return true;
	}

	// 错误处理函数
	public CErrors getErrors() {
		return mErrors;
	}

	// 打包数据---用于向前台返回
	public VData getResult() {
		return mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LLGrpClaimPrtBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StatiCode", "8632");// 统计机构
		tTransferData.setNameAndValue("DStartDate", "2005-01-06");// 应付起期

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLGrpClaimPrtBL tLLGrpClaimPrtBL = new LLGrpClaimPrtBL();
		if (tLLGrpClaimPrtBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔打印出错---------------");
		}
	}

}
