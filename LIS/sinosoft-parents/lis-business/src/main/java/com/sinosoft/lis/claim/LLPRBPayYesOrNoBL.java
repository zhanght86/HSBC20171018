package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDBankDB;
import com.sinosoft.lis.db.LDCodeDB;
import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDBankSchema;
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
 * Title: 理赔清单打印：付费成功与不成功清单-----LLPRBPayYesOrNo.vts
 * </p>
 * <p>
 * Description:
 * 统计界面：统计机构（总公司、某分公司、某中支、某支公司，默认为操作用户所在的权限机构层面）、付款日期、付款银行选项（单个银行或全部银行）、
 * 清单类型（成功清单、不成功清单－目前仅EFT和网上银行存在不成功清单）、付费方式（EFT、网上支付、现金等方式、全部方式）
 * 表头：报表名称、统计条件、打印日期
 * 数据项：银行、实付号、赔案号、领款人姓名和身份证、账号、金额、支付方式、划款日期、实付标识、不成功原因、审核人代码和姓名、审核结论、
 * 审批通过日期、案件标识（如出险人死亡需要标识）、受托人、受托人联系电话、受托人区部组（受托人为业务员时） 算法：选定条件统计 排序：银行、赔案号
 * 表尾：统计数量和汇总金额 能输出到Excel
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author niuzj,2005-11-18
 * @version 1.0 Modify by niuzj，2006-04-13，兼容团险
 */

public class LLPRBPayYesOrNoBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBPayYesOrNoBL.class);

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
	private String mPayDate = ""; // 付款日期
	private String mBankCode = ""; // 付款银行
	private String mListType = ""; // 清单类型
	private String mPayMode = ""; // 支付方式
	private String mNCLType = ""; // 申请类型

	public LLPRBPayYesOrNoBL() {

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
		tTransferData.setNameAndValue("StatiCode", "86");// 统计机构
		tTransferData.setNameAndValue("PayDate", "2005-10-25");// 付款日期
		tTransferData.setNameAndValue("BankCode", ""); // 付款银行
		tTransferData.setNameAndValue("ListType", "1");// 清单类型
		tTransferData.setNameAndValue("PayMode", "");// 支付方式

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);

		LLPRBPayYesOrNoBL tLLPRBPayYesOrNoBL = new LLPRBPayYesOrNoBL();
		if (tLLPRBPayYesOrNoBL.submitData(tVData, "") == false) {
			logger.debug("----------理赔清单打印：付费成功与不成功清单出错---------------");
		}
	}

	/**
	 * 提交数据的公共方法
	 * 
	 * @param: cInputData--- 输入的数据 cOperate--- 数据操作符
	 * @return: 布尔值，成功返回“true”，失败返回“false”
	 */
	public boolean submitData(VData cInputData, String cOperate) {

		logger.debug("-----理赔清单打印：付费成功与不成功清单开始---------LLPRBPayYesOrNoBL---");
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
		logger.debug("-----理赔清单打印：付费成功与不成功清单结束----LLPRBPayYesOrNoBL---");

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
		mPayDate = (String) mTransferData.getValueByName("PayDate"); // 付款日期
		mBankCode = (String) mTransferData.getValueByName("BankCode"); // 付款银行
		mListType = (String) mTransferData.getValueByName("ListType"); // 清单类型
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
		tXmlExport.createDocument("LLPRBPayYesOrNo.vts", "");

		// 新建一个ListTable的实例
		ListTable tListTable = new ListTable();
		tListTable.setName("PYN");
		// 定义列表标题
		String[] Title = new String[21];
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
		Title[17] = "";
		Title[18] = "";

		logger.debug("---以下 查询列表$*/DOF/ROW/COL内容，并为列表赋值--");
		double tStatMoney = 0;// 总金额
		int tStatCount = 0; // 用来统计符合条件的记录的数量

		/***********************************************************************
		 * select * from lyreturnfrombankb where dealtype='F' and notype='5'
		 * order by polno
		 * 
		 * select * from ljaget where othernotype='5' and otherno like '9%' and
		 * paymode='4'
		 * 
		 * select distinct
		 * a.actugetno,a.otherno,a.bankcode,a.bankaccno,b.bankcode,b.bankname,b.accno,b.banksuccflag
		 * from ljaget a,lyreturnfrombankb b where a.otherno=b.polno and
		 * a.othernotype='5' //赔案号 and b.dealtype='F' //付费 and b.notype='5'
		 * //赔案号 and a.paymode='4' //支付方式 and trim(b.banksuccflag) <>'0000'
		 * 
		 * select c.code,c.code1,c.codename from lyreturnfrombankb b,ldcode1 c
		 * where trim(b.banksuccflag) <>'0000' and b.banksuccflag=trim(c.code1)
		 * and c.codetype='bankerror' //付费不成功原因
		 * 
		 */

		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where a.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";
		// String applType = mNCLType.trim().equals("1") ? " and a.rgtobj='1' "
		// : " and a.rgtobj='2' ";

		String strSQL = "";
		// strSQL=" select distinct
		// a.actugetno,a.otherno,a.drawer,a.drawerid,a.bankcode,a.bankaccno, "
		// +" sum(a.sumgetmoney),a.paymode,a.confdate, "
		// +" (case when a.confdate is null then '否' else '是' end), "
		// +" (select k.bankname from ldbank k where k.bankcode=a.bankcode), "
		// +" (select g.codename from ldcode g where trim(g.code)=a.paymode and
		// g.codetype = 'llpaymode') ";

		// 拼入清单类型条件,只从ljaget表中统计
		if (mListType.equals("1")) // 付费成功清单
		{
			strSQL = " select distinct a.actugetno,a.otherno,a.drawer,a.drawerid,a.bankcode,a.bankaccno, "
					+ " sum(a.sumgetmoney),a.paymode,a.confdate, "
					+ " (case when a.confdate is null then '否' else '是' end), "
					+ " (select max(k.bankname) from ldbank k where k.bankcode=a.bankcode), "
					+ " (select g.codename from ldcode g where trim(g.code)=a.paymode and g.codetype = 'llpaymode') "
					+ " from ljaget a where 1=1 "
					+ " and a.othernotype='5' " // 类型为5,表示是赔案号
					+ " and a.managecom like concat('"
					+ "?staticode?"
					+ "','%') "
					+ " and a.confdate is not null "
					+ " and a.confdate='"
					+ "?paydate?" + "' "; // 付款日期
			
			// 拼入支付方式
			if (mPayMode == null || mPayMode.equals("")) {
				strSQL = strSQL;
			} else {
				strSQL = strSQL + " and a.paymode='" + "?paymode?" + "' ";
			}
			// 拼入付款银行条件
			if (mBankCode == null || mBankCode.equals("")) {
				strSQL = strSQL;
			} else {
				strSQL = strSQL + " and a.bankcode='" + "?bankcode?" + "' ";
			}
			// 拼入申请类型
			strSQL = strSQL + tNCLType;
			// 拼入分组条件
			strSQL = strSQL
					+ " group by a.actugetno,a.otherno,a.drawer,a.drawerid,a.bankcode,a.bankaccno,a.paymode,a.confdate";
			// 拼入排序条件
			strSQL = strSQL + " order by a.bankcode,a.otherno ";
		}
		// 付费不成功清单，只统计支付方式是4和7的数据，从ljaget和lyreturnfrombankb表中统计
		else {
			strSQL = " select distinct a.actugetno,a.otherno,a.drawer,a.drawerid,a.bankcode,a.bankaccno, "
					// +" sum(a.sumgetmoney), "
					+ " a.sumgetmoney,a.paymode,b.senddate, "
					+ " (case when a.confdate is null then '否' else '是' end), "
					+ " (select max(k.bankname) from ldbank k where k.bankcode=a.bankcode), "
					+ " (select g.codename from ldcode g where trim(g.code)=a.paymode and g.codetype = 'llpaymode'), "
					+ " b.banksuccflag "
					+ " from ljaget a,lyreturnfrombankb b where 1=1 "
					+ " and a.otherno=b.polno "
					+ " and a.actugetno = b.paycode "
					+ " and a.othernotype='5' " // 类型为5,表示是赔案号
					+ " and b.dealtype='F' " // 付费
					+ " and a.managecom like concat('"
					+ "?staticode?"
					+ "','%') "
					+ " and b.senddate='" + "?paydate?" + "' " // 付款日期
					+ " and a.confdate is null " // 付款不成功时，实付日期为空
					+ " and trim(b.banksuccflag) <>'0000'"; // 不成功标记

			// 拼入支付方式
			if (mPayMode == null || mPayMode.equals("")) {
				strSQL = strSQL + " and (a.paymode='4' or a.paymode='7') ";
				;
			} else if (mPayMode.equals("4") || mPayMode.equals("7")) { // 银行代付EFT,网上银行
				strSQL = strSQL + " and a.paymode='" + "?paymode?" + "' ";
				;
			} else {
				CError tError = new CError();
				tError.moduleName = "LLPRBPayYesOrNoBL";
				tError.functionName = "dealdata";
				tError.errorMessage = "该支付方式下没有付费不成功清单!";
				mErrors.addOneError(tError);
				return false;
			}
			// 拼入付款银行条件
			if (mBankCode == null || mBankCode.equals("")) {
				strSQL = strSQL;
			} else {
				strSQL = strSQL + " and a.bankcode='" + "?bankcode?" + "' ";
			}
			// 拼入申请类型
			strSQL = strSQL + tNCLType;
			// 拼入分组条件
			// strSQL = strSQL +" group by
			// a.actugetno,a.otherno,a.drawer,a.drawerid,a.bankcode,a.bankaccno,a.paymode,b.senddate,b.banksuccflag,a.confdate
			// ";
			// 拼入排序条件
			strSQL = strSQL + " order by a.bankcode,a.otherno ";
		}
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strSQL);
		sqlbv.put("staticode", mStatiCode);
		sqlbv.put("paydate", mPayDate);
		sqlbv.put("paymode", mPayMode);
		sqlbv.put("bankcode", mBankCode);
		logger.debug("最后的查询语句:strSQL==" + strSQL);
		ExeSQL cusExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = cusExeSQL.execSQL(sqlbv);
		if (tSSRS.getMaxRow() != 0) {
			for (int i = 1; i <= tSSRS.MaxRow; i++) {
				String tActugetNo = tSSRS.GetText(i, 1); // 实付号
				String tClmNo = tSSRS.GetText(i, 2); // 赔案号
				String tDrawerName = tSSRS.GetText(i, 3); // 领款人姓名
				String tDrawerId = tSSRS.GetText(i, 4); // 领款人身份证号
				String tBankCode = tSSRS.GetText(i, 5); // 银行代码
				String tBankName = tSSRS.GetText(i, 11); // 银行名称
				String tBankAccNo = tSSRS.GetText(i, 6); // 银行帐号
				String tPay = tSSRS.GetText(i, 7); // 金额
				String tPaymode = tSSRS.GetText(i, 8); // 支付方式代码
				String tHKDate = tSSRS.GetText(i, 9); // 划款日期
				String tPayName = tSSRS.GetText(i, 12); // 支付方式名称
				String tConfFlag = tSSRS.GetText(i, 10); // 实付标识

				// String tSql1="";
				// if(mListType.equals("1"))
				// {
				// tSql1=" select a.confdate from ljaget a where
				// a.otherno='"+tClmNo+"' ";
				// }
				// else
				// {
				// tSql1=" select max(b.senddate) from lyreturnfrombankb b where
				// b.polno='"+tClmNo+"' ";
				// }
				// ExeSQL tExeSQL1 = new ExeSQL();
				// String tHKDate = tExeSQL1.getOneValue(tSql1);

				// 划款不成功原因
				String tSql2 = "";
				String tNoSuccRea = "";
				if (mListType.equals("1")) {
					tNoSuccRea = "";
				} else {
					tSql2 = " select distinct c.codename from lyreturnfrombankb b,ldcode1 c where 1=1 "
							+ " and b.banksuccflag=trim(c.code1) "
							+ " and c.codetype='bankerror' "
							+ " and c.code='"
							+ "?code?"
							+ "' "
							+ " and b.polno='"
							+ "?polno?"
							+ "' ";
					SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
					sqlbv1.sql(tSql2);
					sqlbv1.put("code", tBankCode);
					sqlbv1.put("polno", tClmNo);
					ExeSQL tExeSQL2 = new ExeSQL();
					tNoSuccRea = tExeSQL2.getOneValue(sqlbv1);
				}

				// 审核人代码、姓名、审核结论代码、审核结论名称、审批通过日期
				String tAuditPerCode = "";
				String tAuditPerName = "";
				String tAuditConCode = "";
				String tAuditConName = "";
				String tExamDate = "";
				String tSql3 = " select d.clmno,d.auditper, "
						+ " (select username from llclaimuser f, llclaimuwmain d where d.clmno ='"
						+ "?clmno?"
						+ "' and d.auditper = f.usercode), "
						+ " d.auditconclusion, "
						+ " (select g.codename from llclaimuwmain d, ldcode g where d.clmno ='"
						+ "?clmno?"
						+ "' and d.auditconclusion = g.code and g.codetype = 'llclaimconclusion'), "
						+ " max(d.examdate) "
						+ " from llclaimuwmain d where 1 = 1 "
						+ " and d.clmno='" + "?clmno?" + "' "
						+ " group by d.clmno,d.auditper,d.auditconclusion ";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tSql3);
				sqlbv2.put("clmno", tClmNo);
				ExeSQL tExeSQL3 = new ExeSQL();
				SSRS tSSRS3 = new SSRS();
				tSSRS3 = tExeSQL3.execSQL(sqlbv2);
				for (int j = 1; j <= tSSRS3.MaxRow; j++) {
					tAuditPerCode = tSSRS3.GetText(1, 2);
					tAuditPerName = tSSRS3.GetText(1, 3);
					tAuditConCode = tSSRS3.GetText(1, 4);
					tAuditConName = tSSRS3.GetText(1, 5);
					tExamDate = tSSRS3.GetText(1, 6);
				}

				// 案件标识
				String tSql4 = " select (case count(1) when 0 then '未亡' else '死亡' end) from llappclaimreason l where l.caseno ='"
						+ "?clmno?" + "' and l.reasoncode in ('102','202') ";
				SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
				sqlbv3.sql(tSql4);
				sqlbv3.put("clmno", tClmNo);
				ExeSQL tExeSQL4 = new ExeSQL();
				String tCaseFlag = tExeSQL4.getOneValue(sqlbv3);

				// 受托人名称、受托人联系电话、
				String tAssName = "";
				String tAssPhone = "";

				String tSql5 = " select g.assigneecode, g.assigneename, g.assigneephone "
						+ " from llregister g where 1=1 "
						+ " and g.rgtno ='"
						+ "?clmno?" + "' ";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSql5);
				sqlbv4.put("clmno", tClmNo);
				ExeSQL tExeSQL5 = new ExeSQL();
				SSRS tSSRS5 = new SSRS();
				tSSRS5 = tExeSQL5.execSQL(sqlbv4);
				for (int k = 1; k <= tSSRS5.MaxRow; k++) {
					tAssName = tSSRS5.GetText(1, 2);
					tAssPhone = tSSRS5.GetText(1, 3);
					// tBranchCode=tSSRS5.GetText(1,4);
				}
				// 受托人区部组(受托人为业务员时)
				String tSql6 = " select j.name from llregister g, laagent h, labranchgroup j where 1=1 "
						+ " and g.rgtno ='"
						+ "?clmno?"
						+ "' "
						+ " and g.assigneetype = '1' "
						+ " and (trim(g.assigneecode) = h.agentcode or g.assigneename=trim(h.name)) "
						+ " and h.agentgroup = j.agentgroup ";
				SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
				sqlbv5.sql(tSql6);
				sqlbv5.put("clmno", tClmNo);
				ExeSQL tExeSQL6 = new ExeSQL();
				String tBranchCode = tExeSQL6.getOneValue(sqlbv5);
				// 用赔案号查出险人代码和名字 2006-01-09 小妖
				String tCustomerNo = "";
				String tCustomerName = "";
				String tSQLNo = " select customerno from llsubreport where subrptno='"
						+ "?clmno?" + "' ";
				SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
				sqlbv6.sql(tSQLNo);
				sqlbv6.put("clmno", tClmNo);
				SSRS tSSRSNo = new SSRS();
				tSSRSNo = cusExeSQL.execSQL(sqlbv6);
				for (int z = 1; z <= tSSRSNo.getMaxRow(); z++) {// 可能一个赔案下有多个出险人（现在系统个险最多有两个出险人，若以后允许为多个，就不用再改了）
					tCustomerNo = tSSRSNo.GetText(1, z) + " " + tCustomerNo;
					String tSQLName = " select name from ldperson where customerno='"
							+ "?customerno?" + "'";
					SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
					sqlbv7.sql(tSQLName);
					sqlbv7.put("customerno", tSSRSNo.GetText(1, z));
					SSRS tSSRSName = new SSRS();
					tSSRSName = cusExeSQL.execSQL(sqlbv7);
					tCustomerName = tSSRSName.GetText(1, 1) + " "
							+ tCustomerName;
				}

				// 给模板中的每一列赋值
				String[] stra = new String[21];
				stra[0] = tBankName; // 银行名称
				stra[1] = tActugetNo;
				stra[2] = tClmNo;
				stra[3] = tDrawerName;
				stra[4] = tDrawerId;
				stra[5] = tBankAccNo;
				stra[6] = tPay;
				stra[7] = tPayName;
				stra[8] = tHKDate;
				stra[9] = tConfFlag;
				stra[10] = tNoSuccRea;
				stra[11] = tAuditPerCode;
				stra[12] = tAuditPerName;
				stra[13] = tAuditConName;
				stra[14] = tExamDate;
				stra[15] = tCaseFlag;
				stra[16] = tAssName;
				stra[17] = tAssPhone;
				stra[18] = tBranchCode;
				stra[19] = tCustomerNo; // 出险人代码
				stra[20] = tCustomerName; // 出险人名称

				tListTable.add(stra);
				String tMoney = tSSRS.GetText(i, 7);
				tStatCount = tStatCount + 1; // 记录数量加1
				tStatMoney = tStatMoney + Double.parseDouble(tMoney); // 统计总金额
			}
		} else {
			CError tError = new CError();
			tError.moduleName = "LLPRBPayYesOrNoBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}

		logger.debug("----------以下查询，是为单个变量赋值-----------");
		// 统计日期$=/PYNDate$
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 付款日期$=/PYNPayTime$
		String tPayDate = mPayDate;
		// 付款银行$=/PYNPayBank$
		String tBankName = "";
		if (mBankCode == null || mBankCode.equals("")) {
			tBankName = "全部银行";
		} else {
			LDBankSchema tLDBankSchema = new LDBankSchema();
			LDBankDB tLDBankDB = new LDBankDB();
			tLDBankDB.setBankCode(mBankCode);
			tLDBankDB.getInfo();
			tLDBankSchema = tLDBankDB.getSchema();
			tBankName = tLDBankSchema.getBankName();
		}
		// 统计机构$=/PYNStatMngcom$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mStatiCode);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		// 清单类型$=/PYNListType$
		String tListTyp = "";
		if (mListType.equals("1")) {
			tListTyp = "付款成功清单";
		} else {
			tListTyp = "付款不成功清单";
		}
		// 支付方式$=/PYNPayMode$
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
		tTextTag.add("PYNStatMngcom", tMngCom); // 统计机构
		tTextTag.add("PYNDate", SysDate); // 统计日期
		tTextTag.add("PYNPayTime", tPayDate); // 付款日期
		tTextTag.add("PYNPayBank", tBankName); // 付款银行
		tTextTag.add("PYNListType", tListTyp); // 清单类型
		tTextTag.add("PYNPayMode", tPayMode); // 支付方式
		tTextTag.add("PYNStatSum", tStatCount); // 统计总数量
		tTextTag.add("PYNApplType", tApplTypeName); // 申请类型$=/PYNApplType$
		tTextTag.add("PYNStatMoneySum", new DecimalFormat("0.00")
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
