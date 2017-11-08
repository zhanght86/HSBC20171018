package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description: 二次核保加费日结清单
 * </p>
 * 统计界面：统计机构，实收起期，实收止期，（财务科目），排序选项 排序：1.审批通过日期＋赔案号 2.险种号＋赔案号
 * 3.科目＋险种＋赔案号（由于财务科目选项为空时，不统计二次核保加费日结日结，所以按2与3排序相同） 表尾：各项数据的合计
 * <p>
 * Copyright : Copyright (c) 2002
 * </p>
 * <p>
 * Company :
 * </p>
 * 
 * @author : zhaorx,2006-02-24
 * @version : 1.0
 */

public class LLPRBDaiBalUWAddFeeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRBDaiBalUWAddFeeBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String mCurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String mCurrentTime = PubFun.getCurrentTime(); // 系统时间
	private String mManageCom = ""; // 统计机构
	private String mStartDate = ""; // 实收起期
	private String mEndDate = ""; // 实收止期
	private String mFinDayType = ""; // 财务科目(仅校验页面上是否所选为：14--二次核保加费日结，数据处理中并未用到)
	private String mOrderBy = ""; // 排序选项
	private String mNCLType = ""; // 申请类型

	public LLPRBDaiBalUWAddFeeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("---统计清单：二次核保加费日结清单---LLPRBDaiBalUWAddFeeBL测试---开始---");
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 处理业务数据
		if (!dealData()) {
			return false;
		}
		logger.debug("---统计清单：二次核保加费日结清单---LLPRBDaiBalUWAddFeeBL测试---结束---");

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
		mInputData = cInputData;
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 实收起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 实收止期
		mFinDayType = (String) mTransferData.getValueByName("FinDayType");// 财务科目
		mOrderBy = (String) mTransferData.getValueByName("StatiOpti"); // 排序选项
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称
		xmlexport.createDocument("LLPRBDaiBalUWAddFee.vts", "");

		logger.debug("*************************************************");
		logger.debug("*********以下为“ListTable实例”准备数据*************");
		// 新建一个ListTable实例
		ListTable tListTable = new ListTable();
		tListTable.setName("DBUWAF");
		// 定义列表标题，共18列,采用循环的方式
		String[] Title = new String[18];
		for (int i = 1; i <= 18; i++) {
			Title[i - 1] = "";
		}
		// First 查询SQL
		// String tSQLF = " select a.getnoticeno,a.polno,"
		// + " (select distinct otherno from ljapay where getnoticeno =
		// a.getnoticeno),"
		// + " nvl(a.sumduepaymoney,0),a.appntno,a.riskcode,(select
		// riskshortname from lmrisk where riskcode =
		// a.riskcode),a.confdate,'二次核保加费日结',"
		// + " (select subbaltypedesc from llbalancerela where baltype='C30' and
		// subbaltype ='C3001' and finatype='BF'),"
		// + " (select examdate from llclaimuwmain where examconclusion='0'
		// "//审批通过日期examconclusion='0'
		// + " and clmno in ((select distinct otherno from ljapay where
		// getnoticeno = a.getnoticeno))) "
		// + " from ljapayperson a where 1=1 and a.getnoticeno like 'CLMUW%'"
		// + " and a.confdate between '" +mStartDate+ "' and '" +mEndDate+ "'
		// and a.managecom like '" +mManageCom+ "%'"
		// + " union "//ljapayperson存加费金额，ljapayclaim存利息
		// + " select b.getnoticeno,b.polno,b.otherno,nvl(b.pay,0),"
		// + " (select distinct appntno from ljapay where getnoticeno =
		// b.getnoticeno),"
		// + " b.riskcode,(select riskshortname from lmrisk where riskcode =
		// b.riskcode),b.confdate,'二次核保加费日结',"
		// + " (select subbaltypedesc from llbalancerela where baltype='C30' and
		// subbaltype ='C3002' and finatype='LX'),"
		// + " (select examdate from llclaimuwmain where examconclusion='0' and
		// clmno = b.otherno) "
		// + " from ljapayclaim b where b.confdate between '" +mStartDate+ "'
		// and '" +mEndDate+ "' "
		// + " and exists (select 'X' from llregister where rgtno = b.otherno
		// and mngcom like '" +mManageCom+ "%') "
		// + " and b.feeoperationtype= 'C30' and b.subfeeoperationtype= 'C3002'
		// and b.feefinatype= 'LX' and b.othernotype= '5'";
		// 业务类型判断llregister.rgtobj：1-个险 2-团险
		String tNCLType = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where b.otherno = llregister.rgtno and llregister.rgtobj = '1' ) "
				: " and exists (select 'X' from llregister where b.otherno = llregister.rgtno and llregister.rgtobj = '2' ) ";

		String tSQLF = " select b.polno,b.otherno,((case when a.sumactupaymoney is null then 0 else a.sumactupaymoney end)-(case when b.pay is null then 0 else b.pay end)),(case when b.Pay is null then 0 else b.Pay end),a.appntno,b.riskcode,"
				+ " (select riskshortname from lmrisk where riskcode= b.riskcode), a.confdate,"
				+ " (select examdate from llclaimuwmain where examconclusion='0' and clmno= a.otherno) "
				+ " from ljapay a,ljapayclaim b where 1=1 and a.getnoticeno= b.getnoticeno and a.otherno= b.otherno "
				+ " and b.feeoperationtype= 'C30' and b.subfeeoperationtype= 'C3002' and b.feefinatype= 'LX' and b.othernotype= '5'"
				+ " and exists (select 'X' from llregister where rgtno= a.otherno and mngcom like concat('"
				+ "?mngcom?"
				+ "','%'))"
				+ tNCLType
				+ ""
				+ " and a.confdate between '"
				+ "?startdate?"
				+ "' and '"
				+ "?enddate?" + "' ";
		if (mOrderBy.equals("1")) {// 1-按审批通过日期＋赔案号
			tSQLF = tSQLF + " order by 9,2";
		} else {// 2-险种号＋赔案号 3-科目＋险种＋赔案号(财务科目选项为空时，二次核保加费日结清单不在统计内)
			tSQLF = tSQLF + " order by 6,2 ";
		}
		logger.debug("**************以下执行外层查询语句********************");
		double moneySum = 0.00;// 金额总计
		int statCount = 0; // 统计数量
		String auditConclu = "";// 审核结论
		String auditPer = "";// 审核人代码
		String auditPerName = "";// 审核人
		String accPersonName = "";// 出险人
		String accidentDate = "";// 事故日期
		String accDate = "";// 出险日期
		String deathFlag = "";// 案件标识
		String appntName = "";// 投保人姓名
		String appntIDNo = "";// 投保人证件类型及号码
		String moneyOrPay = "";// 二核加费或利息
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(tSQLF);
		sqlbv.put("mngcom", mManageCom);
		sqlbv.put("startdate", mStartDate);
		sqlbv.put("enddate", mEndDate);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRSF = new SSRS();
		tSSRSF = tExeSQL.execSQL(sqlbv);
		int tMiddle = 1;
		String subBalTypeDescM = "";
		if (tSSRSF.getMaxRow() != 0) {
			for (int index = 1; index <= tSSRSF.getMaxRow(); index++) {
				for (tMiddle = 1; tMiddle <= 2; tMiddle++) {
					moneyOrPay = tSSRSF.GetText(index, (2 + tMiddle));// 二核加费或利息
					subBalTypeDescM = tMiddle == 1 ? "二核加费补费" : "二核加费利息";
					String polNo = tSSRSF.GetText(index, 1);// 保单号
					String clmNo = tSSRSF.GetText(index, 2);// 赔案号
					String riskCode = tSSRSF.GetText(index, 6);// 给付险种代码
					String appntNo = tSSRSF.GetText(index, 5);// 投保人客户号码
					String riskShortName = tSSRSF.GetText(index, 7);// 给付险种简称
					String confDate = tSSRSF.GetText(index, 8);// 实付日期
					String finDayType = "二次核保加费日结";// 财务科目
					String subBalTypeDesc = subBalTypeDescM;// 项目名称
					String examDate = tSSRSF.GetText(index, 9);// 审批通过日期

					if (tMiddle == 1) {// 由于每行数据要两次，第二次不执行SQL，用上一次已查信息
						// Second 查询SQL
						String tSQLS = " select (select codename from ldcode where codetype= 'llclaimconclusion' and c.auditconclusion = code),"
								+ " c.auditper,(select username from lduser where usercode = c.auditper)"
								+ " from llclaimuwmain c where clmno = '"
								+ "?clmno?" + "'";
						SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
						sqlbv1.sql(tSQLS);
						sqlbv1.put("clmno", clmNo);
						SSRS tSSRSS = new SSRS();
						tSSRSS = tExeSQL.execSQL(sqlbv1);
						if (tSSRSS.MaxRow != 0) {
							auditConclu = tSSRSS.GetText(1, 1);// 审核结论
							auditPer = tSSRSS.GetText(1, 2);// 审核人代码
							auditPerName = tSSRSS.GetText(1, 3);// 审核人
						}

						// Third 查询SQL
						String tSQLT = " select f.name,concat((select codename from ldcode where codetype = 'llidtype' and code= f.idtype ),IDno) "
								+ " from ldperson f where customerno = '"
								+ "?appntno?" + "'";
						SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
						sqlbv2.sql(tSQLT);
						sqlbv2.put("appntno", appntNo);
						SSRS tSSRST = new SSRS();
						tSSRST = tExeSQL.execSQL(sqlbv2);
						if (tSSRST.MaxRow != 0) {
							appntName = tSSRST.GetText(1, 1); // 投保人姓名
							appntIDNo = tSSRST.GetText(1, 2); // 投保人证件类型及号码
						}

						// Fouth 查询SQL
						String tSQLFO = " select g.accidentdate,"
								+ " (select max(e.name) from llcase d,ldperson e where d.caseno = g.rgtno and e.customerno = d.customerno),"
								+ " (select max(accdate) from llcase where caseno = g.rgtno ),"
								+ " (select (case count(1) when 0 then '未亡' else '死亡' end) from llappclaimreason  "
								+ " where caseno = g.rgtno and reasoncode in ('102','202'))"
								+ " from llregister g where g.rgtno = '"
								+ "?rgtno?" + "'";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSQLFO);
						sqlbv3.put("rgtno", clmNo);
						SSRS tSSRSFO = new SSRS();
						tSSRSFO = tExeSQL.execSQL(sqlbv3);
						if (tSSRSFO.MaxRow != 0) {
							accidentDate = tSSRSFO.GetText(1, 1); // 出险人
							accPersonName = tSSRSFO.GetText(1, 2); // 事故日期
							accDate = tSSRSFO.GetText(1, 3); // 出险日期
							deathFlag = tSSRSFO.GetText(1, 4); // 案件标识
						}
					}
					String[] Stra = new String[18];
					Stra[0] = clmNo; // 赔案号
					Stra[1] = polNo; // 保单号
					Stra[2] = accPersonName; // 出险人
					Stra[3] = riskCode; // 给付险种代码
					Stra[4] = riskShortName; // 给付险种简称
					Stra[5] = auditConclu; // 审核结论
					Stra[6] = accidentDate; // 事故日期
					Stra[7] = accDate; // 出险日期
					Stra[8] = examDate; // 审批通过日期
					Stra[9] = finDayType; // 财务科目
					Stra[10] = subBalTypeDesc; // 项目名称
					Stra[11] = moneyOrPay; // 金额
					Stra[12] = appntName; // 投保人
					Stra[13] = appntIDNo; // 投保人证件及号码
					Stra[14] = deathFlag; // 案件标识
					Stra[15] = auditPer; // 审核人代码
					Stra[16] = auditPerName; // 审核人
					Stra[17] = confDate; // 实付日期
					tListTable.add(Stra);
					moneySum = moneySum + Double.parseDouble(moneyOrPay);// 金额总计
					statCount = statCount + 1;// 统计数量
				}
			}
		} else // 查询无结果
		{
			CError tError = new CError();
			tError.moduleName = "LLPRBDaiBalUWAddFeeBL";
			tError.functionName = "dealdata";
			tError.errorMessage = "没有要统计的数据!";
			mErrors.addOneError(tError);
			return false;
		}
		logger.debug("*****以上为“ListTable实例”准备数据完成，成功！！！*****");
		logger.debug("***********************************************");

		logger.debug("*********以下为“TextTag实例”准备数据*************");
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		// 为表头赋值
		// 申请类型判断
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人 " : "团体 ";
		tTextTag.add("DBUWAFDate", mCurrentDate);// 统计日期: $=/DBUWAFDate$
		tTextTag.add("DBUWAFStaTime", mStartDate);// 实收起期: $=/DBUWAFStaTime$
		tTextTag.add("DBUWAFEndTime", mEndDate);// 实收止期: $=/DBUWAFEndTime$
		tTextTag.add("PYNApplType", tApplTypeName); // 申请类型$=/PYNApplType$
		tTextTag.add("DBUWAFStatFinSeri", "二次核保加费日结");// 财务科目:
														// $=/DBUWAFStatFinSeri$
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema = tLDComDB.getSchema();
		String tMngCom = tLDComSchema.getName();
		tTextTag.add("DBUWAFStatMngcom", tMngCom);// 统计机构:
													// $=/DBUWAFStatMngcom$

		String orderBy = "";// 排序选项
		switch (Integer.parseInt(mOrderBy)) {
		case 1:
			orderBy = "审批通过日期＋赔案号";
			break;
		case 2:
			orderBy = "险种号＋赔案号";
			break;
		case 3:
			orderBy = "科目＋险种＋赔案号";
			break;
		default:
			orderBy = "";
			break;
		}
		tTextTag.add("DBUWAFOrder", orderBy);// 排序选项: $=/DBUWAFOrder$
		// 为表尾合计项目赋值
		tTextTag.add("DBUWAFStatMoneySum", new DecimalFormat("0.00")
				.format(moneySum)); // 总金额:$=/DBUWAFStatMoneySum$
		tTextTag.add("DBUWAFStatSum", statCount);// 统计数量:$=/DBUWAFStatSum$

		logger.debug("**以上为“TextTag实例”准备数据完成，成功！！！******");
		logger.debug("**********************************************");
		// 添加动态列表数组，参数为一个TextTag
		xmlexport.addListTable(tListTable, Title);
		// 添加动态文本标签的数组，参数为一个TextTag
		if (tTextTag.size() > 0) {
			xmlexport.addTextTag(tTextTag);
		}
		logger.debug("---以下 将XmlExport打包，返回前台--");
		mResult.clear();
		mResult.add(xmlexport);
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	// 得到结果集
	public VData getResult() {
		return mResult;
	}

	// 主函数
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StartDate", "2005-01-01"); // 实收起期
		tTransferData.setNameAndValue("EndDate", "2005-11-01"); // 实收止期
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("FinDayType", "14"); // 财务科目
		tTransferData.setNameAndValue("StatiOpti", "1"); // 排序选项

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRBDaiBalUWAddFeeBL tLLPRBDaiBalUWAddFeeBL = new LLPRBDaiBalUWAddFeeBL();
		if (tLLPRBDaiBalUWAddFeeBL.submitData(tVData, "") == false) {
			logger.debug("---统计清单：二次核保加费日结清单---失败----");
		}
		int n = tLLPRBDaiBalUWAddFeeBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRBDaiBalUWAddFeeBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
