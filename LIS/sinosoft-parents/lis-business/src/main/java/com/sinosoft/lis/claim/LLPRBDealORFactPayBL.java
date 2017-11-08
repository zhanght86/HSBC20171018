package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDCodeSchema;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title: 理赔清单打印：应付和实付清单-----LLPRBDealORFactPay.vts
 * </p>
 * <p>
 * Description: 统计界面：统计机构（总公司、某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、应付起止日期、实付起止日期、
 * 清单类型（实付清单、应付未付清单）、付费方式（EFT、网上支付、现金等方式、全部方式） 表头：报表名称、统计条件、打印日期
 * 数据项：实付号、赔案号、领款人姓名和身份证、银行、账号、金额、支付方式、审批通过日期、实付日期、审核人代码和姓名、
 * 审核结论、案件标识（如出险人死亡需要标识）、受托人、受托人联系电话、受托人区部组（受托人为业务员时）
 * 算法：选定条件统计（应付未付统计为应付期间应付且到实付止期未付的数据） 排序：赔案号、实付号 表尾：统计数量和汇总金额 能输出到Excel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2005-11-15
 * @version 1.0
 */

public class LLPRBDealORFactPayBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBDealORFactPayBL.class);

	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private MMap mMMap = new MMap();

	private TransferData mTransferData = new TransferData();
	private GlobalInput mG = new GlobalInput();
	private String mStatiCode = ""; // 统计机构
	private String mDStartDate = ""; // 应付起期
	private String mDEndDate = ""; // 应付止期
	private String mFStartDate = ""; // 实付起期
	private String mFEndDate = ""; // 实付止期
	private String mPRBType = ""; // 清单类型
	private String mPayMode = ""; // 支付方式
	private String mNCLType = "";// 申请类型

	public LLPRBDealORFactPayBL() {

	}

	/**
	 * 主函数------主要用于设置数据，调试程序入口
	 * 
	 * @param:
	 * @return: 数据处理后信息
	 */

	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ComCode = "86";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("StatiCode", "8632");// 统计机构
		tTransferData.setNameAndValue("DStartDate", "2005-01-06");// 应付起期
		tTransferData.setNameAndValue("DEndDate", "2005-11-09"); // 应付止期
		tTransferData.setNameAndValue("FStartDate", "2005-01-06");// 实付起期
		tTransferData.setNameAndValue("FEndDate", "2005-11-15"); // 实付止期
		tTransferData.setNameAndValue("PRBType", "1");// 清单类型
		tTransferData.setNameAndValue("PayMode", "");// 支付方式

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBDealORFactPayBL tLLPRBDealORFactPayBL = new LLPRBDealORFactPayBL();
		if (tLLPRBDealORFactPayBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：应付和实付清单出错---------------");
		}
	}

	/**
	 * 提交数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印：应付和实付清单开始---------LLPRBDealORFactPayBL---");
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
		logger.debug("-----理赔清单打印：应付和实付清单结束----LLPRBDealORFactPayBL---");

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
		mInputData = cInputData;// 得到外部传入的数据,将数据备份到本类中
		mG = ((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mStatiCode = (String) mTransferData.getValueByName("StatiCode"); // 统计机构
		mDStartDate = (String) mTransferData.getValueByName("DStartDate"); // 应付起期
		mDEndDate = (String) mTransferData.getValueByName("DEndDate"); // 应付止期
		mFStartDate = (String) mTransferData.getValueByName("FStartDate"); // 实付起期
		mFEndDate = (String) mTransferData.getValueByName("FEndDate"); // 实付止期
		mPRBType = (String) mTransferData.getValueByName("PRBType"); // 清单类型
		mPayMode = (String) mTransferData.getValueByName("PayMode"); // 支付方式
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

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

		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();

		XmlExport tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
		TextTag tTextTag = new TextTag(); // 新建一个TextTag的实例
		tXmlExport.createDocument("LLPRBDealORFactPay.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DOF");
		// 定义列表标题
		String[] Title = new String[17];
		Title[0] = "";
		Title[1] = "";
		Title[2] = "";
		Title[3] = "";
		Title[4] = "";
		Title[5] = "";
		Title[6] = "";
		Title[7] = "";
		Title[8] = "";
		Title[9] = "";
		Title[10] = "";
		Title[11] = "";
		Title[12] = "";
		Title[13] = "";
		Title[14] = "";
		Title[15] = "";
		Title[16] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		double tStatMoney = 0;// 总金额
		int tStatCount = 0; // 用来统计符合条件的记录的数量
		/*
		 * select a.actugetno,a.otherno,a.confdate,sum(a.pay),
		 * b.payeename,b.payeeidno,b.bankcode,b.bankaccno,b.casepaymode, (select
		 * g.bankname from ldbank g where g.bankcode=b.bankcode), (select
		 * k.codename from ldcode k where b.casepaymode = k.code and k.codetype =
		 * 'llpaymode'), (select max(t.examdate) from llclaimuwmain t where
		 * t.clmno=a.otherno), (select auditper from llclaimuwmain t where
		 * t.clmno=a.otherno), (select username from llclaimuser d,llclaimuwmain
		 * t where t.clmno=a.otherno and t.auditper=d.usercode), (select
		 * t.auditconclusion from llclaimuwmain t where t.clmno=a.otherno),
		 * (select k.codename from llclaimuwmain t,ldcode k where
		 * t.clmno=a.otherno and t.auditconclusion=k.code and
		 * k.codetype='llclaimconclusion'), (select (case count(1) when 0 then
		 * '未亡' else '死亡' end) from llappclaimreason l where l.caseno =a.otherno
		 * and l.reasoncode in ('102','202')), (select f.assigneename from
		 * llregister f where f.rgtno=a.otherno), (select f.assigneephone from
		 * llregister f where f.rgtno=a.otherno) from ljagetclaim a,llbnf b
		 * where a.otherno=b.clmno and a.polno=b.polno and a.managecom like
		 * '8632%' group by
		 * a.actugetno,a.otherno,a.confdate,b.payeename,b.payeeidno,b.bankcode,b.bankaccno,b.casepaymode
		 * order by a.otherno,a.actugetno
		 */
		// 业务类型判断llregister.rgtobj：1-个险 2-团险（共享池选项不是待支付SQL中判断条件）
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";
		// 实付号,赔案号,领款人,领款人身份证号,银行代码,银行帐号,
		String strSQL = " select a.actugetno,a.otherno,a.drawer,a.drawerid,a.bankcode,a.bankaccno, "
				// 金额,支付方式代码,实付日期,
				+ " sum(a.sumgetmoney),a.paymode,a.confdate, "
				// 银行名称
				+ " (select max(b.bankname) from ldbank b where b.bankcode=a.bankcode), "// Modify
																							// by
																							// zhaorx
																							// 2006-10-09
				// 支付方式名称
				+ " (select c.codename from ldcode c where trim(c.code)=a.paymode and c.codetype = 'llpaymode'), "
				// 审批通过日期
				+ " (select max(d.examdate) from llclaimuwmain d where d.clmno=a.otherno), "
				// 审核人代码
				+ " (select d.auditper from llclaimuwmain d where d.clmno=a.otherno), "
				// 审核人姓名
				+ " (select f.username from llclaimuser f,llclaimuwmain d where d.clmno=a.otherno and d.auditper=f.usercode), "
				// 审核结论代码
				+ " (select d.auditconclusion from llclaimuwmain d where d.clmno=a.otherno), "
				// 审核结论名称
				+ " (select c.codename from llclaimuwmain d,ldcode c  where d.clmno=a.otherno and d.auditconclusion=c.code and c.codetype='llclaimconclusion'), "
				// 案件标识
				+ " (select (case count(1) when 0 then '未亡' else '死亡' end) from llappclaimreason l where l.caseno =a.otherno and l.reasoncode in ('102','202')), "
				// 受托人名称
				+ " (select g.assigneename from llregister g where g.rgtno=a.otherno), "
				// 受托人联系电话
				+ " (select g.assigneephone from llregister g where g.rgtno=a.otherno), "
				// 受托人区部组(受托人为业务员时)
				+ " (select j.name from llregister g,laagent h,labranchgroup j where g.assigneetype='1' and g.assigneecode=h.agentcode and h.agentgroup=j.agentgroup) "
				+ " from ljaget a " + " where 1=1 and a.othernotype='5' " // 类型为5,表示是赔案号
				+ " and a.managecom like concat('" + "?staticode?" + "','%') " + tNCLType;

		// 拼入清单类型条件
		if (mPRBType.equals("1")) // 实付清单
		{
			strSQL = strSQL + " and a.confdate is not null "
					+ " and a.confdate between '" + "?fstartdate?" + "' and '"
					+ "?fenddate?" + "' ";
		} else if (mPRBType.equals("2")) // 应付未付清单
		{
			strSQL = strSQL + " and a.confdate is null "
					+ " and a.shoulddate between '" + "?dstartdate?" + "' and '"
					+ "?denddate?" + "' ";
		} else // 合并两张清单的结果，by niuzj,2005-12-12
		{
			// strSQL = strSQL +" and ( a.confdate between '"+mFStartDate+"' and
			// '"+mFEndDate+"' "
			// +" or a.shoulddate between '"+mDStartDate+"' and '"+mDEndDate+"'
			// ) ";
			// Modifyed by niuzj,2005-12-13,不是简单的合并，而是只统计当天应付的数据（不管是否实付）
			strSQL = strSQL + " and a.shoulddate between '" + "?dstartdate?"
					+ "' and '" + "?denddate?" + "' ";
		}

		// 拼入支付方式
		if (mPayMode == null || mPayMode.equals("")) {
			strSQL = strSQL;
		} else {
			strSQL = strSQL + " and a.paymode='" + "?paymode?" + "' ";
		}
		// 拼入分组条件
		strSQL = strSQL
				+ " group by a.actugetno,a.otherno,a.drawer,a.drawerid,a.bankcode,a.bankaccno,a.paymode,a.confdate  ";
		// 拼入排序条件
		strSQL = strSQL + " order by a.otherno,a.actugetno ";

		logger.debug("最后的查询语句:strSQL==" + strSQL);
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("fstartdate", mFStartDate);
		sqlbv.put("dstartdate", mDStartDate);
		sqlbv.put("fenddate", mFEndDate);
		sqlbv.put("denddate", mDEndDate);
		sqlbv.put("paymode", mPayMode);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String[] stra = new String[17];
				stra[0] = tSSRS.GetText(i, 1); // 实付号
				stra[1] = tSSRS.GetText(i, 2); // 赔案号
				stra[2] = tSSRS.GetText(i, 3); // 领款人姓名
				stra[3] = tSSRS.GetText(i, 4); // 身份证号
				stra[4] = tSSRS.GetText(i, 10); // 银行
				stra[5] = tSSRS.GetText(i, 6); // 帐号
				stra[6] = tSSRS.GetText(i, 7); // 金额
				stra[7] = tSSRS.GetText(i, 11); // 支付方式
				stra[8] = tSSRS.GetText(i, 12); // 审批通过日期
				stra[9] = tSSRS.GetText(i, 9); // 实付日期
				stra[10] = tSSRS.GetText(i, 13);// 审核人代码
				stra[11] = tSSRS.GetText(i, 14);// 审核人姓名
				stra[12] = tSSRS.GetText(i, 16);// 审核结论
				stra[13] = tSSRS.GetText(i, 17);// 案件标识
				stra[14] = tSSRS.GetText(i, 18);// 受托人
				stra[15] = tSSRS.GetText(i, 19);// 受托人联系电话
				stra[16] = tSSRS.GetText(i, 20);// 受托人区部组

				tListTable.add(stra);
				String tMoney = tSSRS.GetText(i, 7);
				tStatCount = tStatCount + 1; // 记录数量加1
				tStatMoney = tStatMoney + Double.parseDouble(tMoney); // 统计总金额

			}

		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBDealORFactPayBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");
		// 统计日期$=/DOFDate$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 应付起期$=/DOFDStaTime$
		String tDStartDate = mDStartDate;
		// 应付止期$=/DOFDEndTime$
		String tDEndDate = mDEndDate;
		// 实付起期$=/DOFFStaTime$
		String tFStartDate = mFStartDate;
		// 实付止期$=/DOFFEndTime$
		String tFEndDate = mFEndDate;

		// 统计机构$=/DOFStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();

		// 清单类型$=/DOFPRBType$
		String tPRBType = "";
		if (mPRBType.equals("1")) {
			tPRBType = "实付清单";
		} else if (mPRBType.equals("2")) {
			tPRBType = "应付未付清单";
		} else {
			tPRBType = "应付清单";
		}

		// 支付方式$=/DOFPayMode$
		String tPayMode = "";
		if (mPayMode == null || mPayMode.equals("")) {
			tPayMode = "全部支付方式";
		} else {
			LDCodeSchema tLDCodeSchema = new LDCodeSchema();
			LDCodeDB tLDCodeDB = new LDCodeDB();
			tLDCodeDB.setCodeType("llpaymode");
			tLDCodeDB.setCode(mPayMode);
			tLDCodeDB.getInfo();
			tLDCodeSchema = tLDCodeDB.getSchema();
			tPayMode = tLDCodeSchema.getCodeName();
		}
		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";

		// 给模板中的变量赋值
		tTextTag.add("DOFStatMngcom", tMngCom); // 统计机构
		tTextTag.add("DOFDate", SysDate); // 统计日期
		tTextTag.add("DOFDStaTime", tDStartDate); // 应付起期
		tTextTag.add("DOFDEndTime", tDEndDate); // 应付止期
		tTextTag.add("DOFFStaTime", tFStartDate); // 实付起期
		tTextTag.add("DOFFEndTime", tFEndDate); // 实付止期
		tTextTag.add("DOFPRBType", tPRBType); // 清单类型
		tTextTag.add("DOFPayMode", tPayMode); // 支付方式
		tTextTag.add("DOFApplType", tApplTypeName); // 申请类型$=/DOApplType$
		tTextTag.add("DOFStatSum", tStatCount); // 统计总数量
		tTextTag.add("DOFStatMoneySum", new DecimalFormat("0.00")
				.format(tStatMoney)); // 统计总金额

		logger.debug("********************************************");
		logger.debug("---以下 tXmlExport实例添加一个列表和动态文本标签--");
		// tXmlExport为添加一个列表，参数为ListTag和动态列表的表头数组
		tXmlExport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			tXmlExport.addTextTag(tTextTag);
		}

		// /*##################### 后台调试部分，生成临时文件############################*/
		// String strTemplatePath="E:/lis/ui/f1print/NCLtemplate/";
		// String strVFPathName=strTemplatePath+"test.vts"; //生成临时文件名
		// CombineVts tcombineVts = null;
		// tcombineVts = new CombineVts(tXmlExport.getInputStream(),
		// strTemplatePath);
		// ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		// tcombineVts.output(dataStream);
		// AccessVtsFile.saveToFile(dataStream, strVFPathName);

		logger.debug("********************************************");
		logger.debug("---以下 将tXmlExport打包，返回前台--");
		// tXmlExport.outputDocumentToFile("c:\\", "testHZM"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.add(mTransferData);
		logger.debug("xmlexport=" + tXmlExport);

		return true;
	}

	/**
	 * 准备需要保存的数据
	 * 
	 * @return boolean
	 */
	private boolean prepareOutputData() {
		return true;
	}

	/**
	 * 提交数据
	 * 
	 * @return
	 */
	private boolean pubSubmit() {
		// 进行数据提交
		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			return false;
		}
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

}
