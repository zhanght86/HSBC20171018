package com.sinosoft.lis.f1print;

import org.apache.log4j.Logger;

import com.sinosoft.lis.claim.LLClaimPubFunBL;
import com.sinosoft.lis.db.LDSysVarDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.HashReport;
import com.sinosoft.lis.schema.LDBankSchema;
import com.sinosoft.lis.schema.LDCode1Schema;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LYBankLogSet;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

public class NewSuccXQPremBankBL {
	private static Logger logger = Logger.getLogger(NewSuccXQPremBankBL.class);

	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mG = new GlobalInput();
	public PremBankPubFun mPremBankPubFun = new PremBankPubFun();

	private LYBankLogSet mLYBankLogSet = new LYBankLogSet();
	private LDBankSchema mLDBankSchema = new LDBankSchema();
	private LDCode1Schema mLDCode1Schema = new LDCode1Schema();
	private LJSPayPersonSet mLJSPayPersonSet = new LJSPayPersonSet();

	// 初始化全局变量，从前台承接数据
	private String strStartDate = ""; // 开始日期
	private String strEndDate = ""; // 结束日期
	private String strAgentState = ""; // 业务员的状态(1为在职单，0为孤儿单)
	private String strPremType = ""; // 首续期的标志
	private String strFlag = ""; // S or F(S为银行代收，F为银行代付)
	// private String strComCode = ""; //系统登陆的机构(查询银行日志表)
	private String strStation = "";
	private String templatepath = "";// 模板路径
	private String strBillNo = ""; // 批次号码
	private String mBankName = ""; // 银行名称
	private String mErrorReason = ""; // 失败原因
	private String mChkSuccFlag = ""; // 银行校验成功标志；
	private String mChkFailFlag = ""; // 银行校验失败标志；
	private String mAgentGroup = "";
	private String mAgentState = "";
	private double mMoney = 0.00;
	private double mAppendMoney = 0.00;

	private int mCount = 0;
	private VData mInputData;
	private String[][] result; // 用来装最后的结果数据

	public NewSuccXQPremBankBL() {
	}

	/**
	 * 传输数据的公共方法
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		if (!cOperate.equals("PRINT")) {
			mPremBankPubFun.buildError("submitData", "不支持的操作字符串");
			return false;
		}
		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!LLClaimPubFunBL.checkDate(strStartDate, strEndDate)) {
			mPremBankPubFun.buildError("submitData", "开始日期比结束日期晚,请从新录入");
			return false;
		}
		mResult.clear();
		// 准备所有要打印的数据
		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean getPrintData() {
		ListTable tlistTable = new ListTable();
		tlistTable.setName("MODE");

		TextTag texttag = new TextTag();// 新建一个TextTag的实例
		XmlExportNew xmlexport = new XmlExportNew();// 新建一个XmlExport的实例
		// xmlexport.createDocument("NewPrintXQBankSucc.vts","printer");
		xmlexport.createDocument("续期保费转账成功清单", "", "", "");// 1代表输出pdf

		ListTable alistTable = new ListTable();
		alistTable.setName("INFO");
		String tAgentSql = ""; // 代理人条件SQL
		String tDateSql = ""; // 时间条件SQL
		// 扣款成功SQL
		String tSuccSql = " and b.banksuccflag='0000'";
		String tLJS1Sql = ""; // 对应lyreturnfrombank
		String tLJS2Sql = ""; // 对应lyreturnfrombankb
		String tLJASql = ""; // 对应lyreturnfrombankb
		String main_sql = ""; // 主SQL

		if (strAgentState.equals("1")) {
			tAgentSql = " and exists (select 1 from LAAgent where AgentCode=a.agentcode and AgentState in ('01','02')) ";
		}
		if (strAgentState.equals("0")) {
			tAgentSql = " and not exists (select 1 from LAAgent where AgentCode=a.agentcode and AgentState in ('01','02')) ";
		}
		logger.debug("孤儿单或在职单的查询语句是" + tAgentSql);
		tDateSql = " and b.BankDealDate >='" + "?strStartDate?"
				+ "' and b.BankDealDate <='" + "?strEndDate?"
				+ "' and b.DealType = 'S' and b.ComCode like '"
				+ "?strStation?" + "%' ";
		// 在LYReturnFromBank和LYReturnFromBankB中查询出所有的符合日期条件的记录
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			tLJS1Sql = " select"
					+ " (select name from LABranchGroup where AgentGroup=a.AgentGroup) agnttgroupname,"
					+ " (select Name from LAAgent where AgentCode=a.AgentCode) agentname, "
					+ " (select appntname from LCAppnt where contno=a.otherno and appntno=a.appntno) appntname,"
					+ " (select y.postaladdress from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) postaladdress,"
					+ " (select y.zipcode from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) zipcode,"
					+ " a.otherno mainpolno,"
					+ " (select max(paycount) from ljspayperson where getnoticeno=a.getnoticeno) paycount,"
					+ " (select insuredname from lcpol where polno=a.otherno) insuredname,"
					+ " (select Bankname from ldbank where bankcode=a.bankcode) bankname,"
					+ " BankAccNo,"
					+ " StartPayDate,"
					+ " (select nvl(sum(SumDuePayMoney),0) from ljspayperson  where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno=mainpolno and lcpol.polno=ljspayperson.polno )) mainprem,"
					+ " (select nvl(sum(SumDuePayMoney),0) from ljspayperson where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno<>mainpolno and lcpol.polno=ljspayperson.polno )) subprem,"
					+ " SumDuePayMoney sumprem,"
					+ " b.BankDealDate paymoneydate,"
					+ " (select name from lacom where agentcom in (select agentcom from ljspayperson  where getnoticeno=a.getnoticeno and rownum=1)) agentcomname, "
					+ " (select decode(xqremindflag,'0','N','1','Y','Y') from lccont where a.otherno=contno)"
					+ " from ljspay a,lyreturnfrombank b where"
					+ " a.getnoticeno=paycode and a.OtherNoType='2'" // 个单续期
					+ tAgentSql + tSuccSql + tDateSql;

			tLJS2Sql = " select"
					+ " (select name from LABranchGroup where AgentGroup=a.AgentGroup) agnttgroupname,"
					+ " (select Name from LAAgent where AgentCode=a.AgentCode) agentname, "
					+ " (select appntname from LCAppnt where contno=a.otherno and appntno=a.appntno) appntname,"
					+ " (select y.postaladdress from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) postaladdress,"
					+ " (select y.zipcode from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) zipcode,"
					+ " a.otherno mainpolno,"
					+ " (select max(paycount) from ljspayperson where getnoticeno=a.getnoticeno) paycount,"
					+ " (select insuredname from lcpol where polno=a.otherno) insuredname,"
					+ " (select Bankname from ldbank where bankcode=a.bankcode) bankname,"
					+ " BankAccNo,"
					+ " StartPayDate,"
					+ " (select nvl(sum(SumDuePayMoney),0) from ljspayperson  where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno=mainpolno and lcpol.polno=ljspayperson.polno )) mainprem,"
					+ " (select nvl(sum(SumDuePayMoney),0) from ljspayperson where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno<>mainpolno and lcpol.polno=ljspayperson.polno )) subprem,"
					+ " SumDuePayMoney sumprem,"
					+ " b.BankDealDate paymoneydate,"
					+ " (select name from lacom where agentcom in (select agentcom from ljspayperson  where getnoticeno=a.getnoticeno and rownum=1)) agentcomname, "
					+ " (select decode(xqremindflag,'0','N','1','Y','Y') from lccont where a.otherno=contno)"
					+ " from ljspay a,lyreturnfrombankb b where "
					+ " a.getnoticeno=paycode and a.OtherNoType='2'" // 个单续期
					+ tAgentSql + tSuccSql + tDateSql;

			tLJASql = " select"
					+ " (select name from LABranchGroup where AgentGroup=a.AgentGroup) agnttgroupname,"
					+ " (select Name from LAAgent where AgentCode=a.AgentCode) agentname, "
					+ " (select appntname from LCAppnt where contno=a.incomeno and appntno=a.appntno) appntname,"
					+ " (select y.postaladdress from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.incomeno and x.appntno=a.appntno) postaladdress,"
					+ " (select y.zipcode from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.incomeno and x.appntno=a.appntno) zipcode,"
					+ " (decode((select polno from lcrnewstatelog where proposalno=a.incomeno),null,a.incomeno,(select polno from lcrnewstatelog where proposalno=a.incomeno))) mainpolno,"
					+ " (select max(paycount) from ljapayperson where payno=a.payno) paycount,"
					+ " (select insuredname from lcpol where polno=a.incomeno) insuredname,"
					+ " (select Bankname from ldbank where bankcode=a.bankcode) bankname,"
					+ " BankAccNo,"
					+ " (select lastpaytodate from ljapayperson where payno=a.payno and rownum=1) StartPayDate,"
					+ " (select nvl(sum(SumDuePayMoney),0) from ljapayperson  where payno=a.payno and polno=a.incomeno) mainprem,"
					+ " (select nvl(sum(SumDuePayMoney),0) from ljapayperson where payno=a.payno and polno<>a.incomeno) subprem,"
					+ " SumActuPayMoney sumprem,"
					+ " b.BankDealDate paymoneydate,"
					+ " (select name from lacom where agentcom in (select agentcom from ljapayperson where payno=a.payno and rownum=1)) agentcomname, "
					+ " (select decode(xqremindflag,'0','N','1','Y','Y') from lccont where a.otherno=contno)"
					+ " from ljapay a,lyreturnfrombankb b where "
					+ " a.getnoticeno=paycode and a.IncomeType='2'" // 个单续期
					+ tAgentSql + tSuccSql + tDateSql;
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			tLJS1Sql = " select"
					+ " (select name from LABranchGroup where AgentGroup=a.AgentGroup) agnttgroupname,"
					+ " (select Name from LAAgent where AgentCode=a.AgentCode) agentname, "
					+ " (select appntname from LCAppnt where contno=a.otherno and appntno=a.appntno) appntname,"
					+ " (select y.postaladdress from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) postaladdress,"
					+ " (select y.zipcode from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) zipcode,"
					+ " a.otherno mainpolno,"
					+ " (select max(paycount) from ljspayperson where getnoticeno=a.getnoticeno) paycount,"
					+ " (select insuredname from lcpol where polno=a.otherno) insuredname,"
					+ " (select Bankname from ldbank where bankcode=a.bankcode) bankname,"
					+ " BankAccNo,"
					+ " StartPayDate,"
					+ " (select (case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from ljspayperson  where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno=mainpolno and lcpol.polno=ljspayperson.polno )) mainprem,"
					+ " (select ( case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from ljspayperson where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno<>mainpolno and lcpol.polno=ljspayperson.polno )) subprem,"
					+ " SumDuePayMoney sumprem,"
					+ " b.BankDealDate paymoneydate,"
					+ " (select name from lacom where agentcom in (select agentcom from ljspayperson  where getnoticeno=a.getnoticeno limit 1)) agentcomname, "
					+ " (select ( case xqremindflag when '0' then 'N' when '1' then 'Y' else 'Y' end) from lccont where a.otherno=contno)"
					+ " from ljspay a,lyreturnfrombank b where"
					+ " a.getnoticeno=paycode and a.OtherNoType='2'" // 个单续期
					+ tAgentSql + tSuccSql + tDateSql;

			tLJS2Sql = " select"
					+ " (select name from LABranchGroup where AgentGroup=a.AgentGroup) agnttgroupname,"
					+ " (select Name from LAAgent where AgentCode=a.AgentCode) agentname, "
					+ " (select appntname from LCAppnt where contno=a.otherno and appntno=a.appntno) appntname,"
					+ " (select y.postaladdress from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) postaladdress,"
					+ " (select y.zipcode from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.otherno and x.appntno=a.appntno) zipcode,"
					+ " a.otherno mainpolno,"
					+ " (select max(paycount) from ljspayperson where getnoticeno=a.getnoticeno) paycount,"
					+ " (select insuredname from lcpol where polno=a.otherno) insuredname,"
					+ " (select Bankname from ldbank where bankcode=a.bankcode) bankname,"
					+ " BankAccNo,"
					+ " StartPayDate,"
					+ " (select ( case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from ljspayperson  where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno=mainpolno and lcpol.polno=ljspayperson.polno )) mainprem,"
					+ " (select ( case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from ljspayperson where getnoticeno=a.getnoticeno and exists (select 1 from lcpol where polno<>mainpolno and lcpol.polno=ljspayperson.polno )) subprem,"
					+ " SumDuePayMoney sumprem,"
					+ " b.BankDealDate paymoneydate,"
					+ " (select name from lacom where agentcom in (select agentcom from ljspayperson  where getnoticeno=a.getnoticeno limit 1)) agentcomname, "
					+ " (select ( case xqremindflag when '0' then 'N' when '1' then 'Y' else 'Y' end) from lccont where a.otherno=contno)"
					+ " from ljspay a,lyreturnfrombankb b where "
					+ " a.getnoticeno=paycode and a.OtherNoType='2'" // 个单续期
					+ tAgentSql + tSuccSql + tDateSql;

			tLJASql = " select"
					+ " (select name from LABranchGroup where AgentGroup=a.AgentGroup) agnttgroupname,"
					+ " (select Name from LAAgent where AgentCode=a.AgentCode) agentname, "
					+ " (select appntname from LCAppnt where contno=a.incomeno and appntno=a.appntno) appntname,"
					+ " (select y.postaladdress from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.incomeno and x.appntno=a.appntno) postaladdress,"
					+ " (select y.zipcode from lcappnt x,lcaddress y where x.appntno=y.customerno and x.addressno=y.addressno and x.contno=a.incomeno and x.appntno=a.appntno) zipcode,"
					+ " (( case when (select polno from lcrnewstatelog where proposalno=a.incomeno) is null then a.incomeno else (select polno from lcrnewstatelog where proposalno=a.incomeno) end)) mainpolno,"
					+ " (select max(paycount) from ljapayperson where payno=a.payno) paycount,"
					+ " (select insuredname from lcpol where polno=a.incomeno) insuredname,"
					+ " (select Bankname from ldbank where bankcode=a.bankcode) bankname,"
					+ " BankAccNo,"
					+ " (select lastpaytodate from ljapayperson where payno=a.payno limit 1) StartPayDate,"
					+ " (select ( case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from ljapayperson  where payno=a.payno and polno=a.incomeno) mainprem,"
					+ " (select ( case when sum(SumDuePayMoney) is not null then sum(SumDuePayMoney) else 0 end) from ljapayperson where payno=a.payno and polno<>a.incomeno) subprem,"
					+ " SumActuPayMoney sumprem,"
					+ " b.BankDealDate paymoneydate,"
					+ " (select name from lacom where agentcom in (select agentcom from ljapayperson where payno=a.payno limit 1)) agentcomname, "
					+ " (select ( case xqremindflag when '0' then 'N' when '1' then 'Y' else 'Y' end) from lccont where a.otherno=contno)"
					+ " from ljapay a,lyreturnfrombankb b where "
					+ " a.getnoticeno=paycode and a.IncomeType='2'" // 个单续期
					+ tAgentSql + tSuccSql + tDateSql;
		}

		main_sql = tLJS1Sql + " union all " + tLJS2Sql + " union all "
				+ tLJASql;

		// 此语句是为了测试大数据批单处理

		//b* 有问题 ，多拿到一列  @rownum:=0
		if (SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)) {
			main_sql = "select b.*,11,12,13,14,15,16,17,18,19,20 from (select rownum rn,a.* from testldmaxno a) b where rn<130000";
		} else if (SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)) {
			main_sql = "select b.*,11,12,13,14,15,16,17,18,19,20 from (select @rownum:=@rownum+1 rn,a.* from (select testldmaxno.*,@rownum:=0 from testldmaxno) a) b where rn<130000";
		}
		logger.debug("main_sql的查询语句是" + main_sql);
		ExeSQL main_exesql = new ExeSQL();
		SSRS main_ssrs = new SSRS();
		SQLwithBindVariables sqlbvq = new SQLwithBindVariables();
		sqlbvq.sql(main_sql);
		sqlbvq.put("strStartDate", strStartDate);
		sqlbvq.put("strEndDate", strEndDate);
		sqlbvq.put("strStation", strStation);
		main_ssrs = main_exesql.execSQL(sqlbvq);
		logger.debug("main_ssrs查询的结果是" + main_ssrs.getMaxRow());
		logger.debug("main_ssrs查询的结果是" + main_ssrs.getMaxRow());
		if (main_ssrs.getMaxRow() > 0) {
			result = new String[main_ssrs.getMaxRow()][20]; // 用来装最后的结果数据
			for (int main_count = 1; main_count <= main_ssrs.getMaxRow(); main_count++) {
				String[] cols = new String[20];
				String[] cols2 = new String[20];
				cols[0] = main_ssrs.GetText(main_count, 1);// 业务员组别
				cols[1] = main_ssrs.GetText(main_count, 2);
				;// 业务员姓名
				cols[2] = main_ssrs.GetText(main_count, 3);
				;// 投保人姓名
				cols[3] = main_ssrs.GetText(main_count, 4);
				;// 投保人地址
				cols[4] = main_ssrs.GetText(main_count, 5);
				;// 投保人邮编
				cols[5] = main_ssrs.GetText(main_count, 6);
				;// 主险保单号码
				cols[6] = main_ssrs.GetText(main_count, 7);
				;// 应缴期数
				cols[7] = main_ssrs.GetText(main_count, 8);
				;// 被保人
				cols[8] = main_ssrs.GetText(main_count, 9);// 开户行
				cols[9] = main_ssrs.GetText(main_count, 10);// 账号
				cols[10] = main_ssrs.GetText(main_count, 11);// 应缴日期
				cols[11] = main_ssrs.GetText(main_count, 12);// 主险实交金额
				cols[12] = main_ssrs.GetText(main_count, 13);// 附件险实金额合计
				cols[13] = main_ssrs.GetText(main_count, 14);
				cols[14] = main_ssrs.GetText(main_count, 15);// 交费日期
				cols[15] = main_ssrs.GetText(main_count, 16);
				cols[16] = main_ssrs.GetText(main_count, 17);// 缴费提示
				cols[17] = main_ssrs.GetText(main_count, 18);// 测试数据1
				cols[18] = main_ssrs.GetText(main_count, 19);// 测试数据2
				cols[19] = main_ssrs.GetText(main_count, 20);// 测试数据3
				alistTable.add(cols);

				// 静态报表数据
				cols2[0] = main_ssrs.GetText(main_count, 1);// 业务员组别
				cols2[1] = main_ssrs.GetText(main_count, 16);
				cols2[2] = main_ssrs.GetText(main_count, 2);
				;// 业务员姓名
				cols2[3] = main_ssrs.GetText(main_count, 3);
				;// 投保人姓名
				cols2[4] = main_ssrs.GetText(main_count, 17);
				;// 缴费提示
				cols2[5] = main_ssrs.GetText(main_count, 4);
				;// 投保人地址
				cols2[6] = main_ssrs.GetText(main_count, 5);
				;// 投保人邮编
				cols2[7] = main_ssrs.GetText(main_count, 6);
				;// 主险保单号码
				cols2[8] = main_ssrs.GetText(main_count, 7);
				;// 应缴期数
				cols2[9] = main_ssrs.GetText(main_count, 8);
				;// 被保人
				cols2[10] = main_ssrs.GetText(main_count, 9);// 开户行
				cols2[11] = main_ssrs.GetText(main_count, 10);// 账号
				cols2[12] = main_ssrs.GetText(main_count, 11);// 应缴日期
				cols2[13] = main_ssrs.GetText(main_count, 12);// 主险实交金额
				cols2[14] = main_ssrs.GetText(main_count, 13);// 附件险实金额合计
				cols2[15] = main_ssrs.GetText(main_count, 14);
				cols2[16] = main_ssrs.GetText(main_count, 15);// 交费日期
				cols2[17] = main_ssrs.GetText(main_count, 18);// 测试数据4
				cols2[18] = main_ssrs.GetText(main_count, 19);// 测试数据5
				cols2[19] = main_ssrs.GetText(main_count, 20);// 测试数据6
				result[main_count - 1] = cols2;
			}
		}
		logger.debug("开始生成文件！");
		String[] b_col = new String[20];
		xmlexport.addDisplayControl("displayinfo");
		xmlexport.addListTable(alistTable, b_col);
		texttag.add("AgentState", mAgentState);
		texttag.add("StartDate", strStartDate);
		texttag.add("EndDate", strEndDate);
		texttag.add("ComCode", strStation);
		texttag.add("SumMoney", mMoney);
		texttag.add("SumCount", mCount);
		if (texttag.size() > 0)
			xmlexport.addTextTag(texttag);
		// xmlexport.outputDocumentToFile("e:\\","NewXQPremBankSuccBL");//输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);

		// 生成静态报表部分
		if (!trans(result, main_ssrs.getMaxRow(), 17)) {
			logger.debug("将数组中为null的项置为空时出错");
			mPremBankPubFun.buildError("trans", "将数组中为null的项置为空时出错");
			return false;
		}

		TransferData tempTransferData = new TransferData();
		// 输入表头等信息
		tempTransferData.setNameAndValue("&startdate", strStartDate);
		tempTransferData.setNameAndValue("&enddate", strEndDate);
		tempTransferData.setNameAndValue("&managecom", this.strStation);
		tempTransferData.setNameAndValue("&poltype", this.mAgentState);

		HashReport tHashReport = new HashReport();
		String tpath = "";
		LDSysVarDB tLDSysVarDB = new LDSysVarDB();
		tLDSysVarDB.setSysVar("XSCreatListPath");
		if (!tLDSysVarDB.getInfo()) {
			return false;
		}
		tpath = tLDSysVarDB.getSysVarValue();
		String tFileName = "NewXQBankSucc" + "_" + strStartDate + "_"
				+ strEndDate + "_" + strStation + "_" + strAgentState;
		// tpath = "D:\\TEMP\\";
		tHashReport.outputArrayToFile1(tpath + tFileName + ".xls", templatepath
				+ "NewXQBankSucc.xls", result, tempTransferData);
		return true;
	}

	public boolean getInputData(VData tInputData) {
		strStartDate = (String) mInputData.get(0);
		strEndDate = (String) mInputData.get(1);
		strAgentState = (String) mInputData.get(2);
		strPremType = (String) mInputData.get(3);// 首期还是续期
		strFlag = (String) mInputData.get(4);// F or S
		strStation = (String) mInputData.get(5);
		templatepath = (String) mInputData.get(6);
		logger.debug("strStartDate " + strStartDate);
		logger.debug("strEndDate " + strEndDate);
		logger.debug("strAgentState " + strAgentState);
		logger.debug("strPremType " + strPremType);
		logger.debug("strFlag " + strFlag);
		logger.debug("strStation " + strStation);
		if (strAgentState.equals("1")) {
			mAgentState = "在职单";
		} else {
			mAgentState = "孤儿单";
		}
		strStartDate = strStartDate.trim();
		strEndDate = strEndDate.trim();
		strAgentState = strAgentState.trim();
		strPremType = strPremType.trim();
		strFlag = strFlag.trim();
		return true;
	}

	private boolean trans(String[][] result, int count1, int count2) // 将数组中为null的置为""
																		// ,count1为数组的行数,count2为数组列数
	{
		int a = 0;
		int b = 0;
		a = count1;
		b = count2;
		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				if (result[i][j] == null) {
					result[i][j] = "";
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String strStartDate = "2003-7-6"; // 开始日期
		String strEndDate = "2004-11-10"; // 结束日期
		String strAgentState = "0"; // 业务员的状态(1为在职单，0为孤儿单)
		String strPremType = "X"; // 首续期的标志
		String strFlag = "S"; // S or F(S为银行代收，F为银行代付)
		String strComCode = "86110000"; // 系统登陆的机构(查询银行日志表)
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		VData tVData = new VData();
		tVData.addElement(strStartDate);
		tVData.addElement(strEndDate);
		tVData.addElement(strAgentState);
		tVData.addElement(strPremType);
		tVData.addElement(strFlag);
		tVData.addElement(strComCode);
		tVData.addElement(tG);
		NewSuccXQPremBankBL aNewSuccXQPremBankBL = new NewSuccXQPremBankBL();
		aNewSuccXQPremBankBL.submitData(tVData, "PRINT");
	}
}
