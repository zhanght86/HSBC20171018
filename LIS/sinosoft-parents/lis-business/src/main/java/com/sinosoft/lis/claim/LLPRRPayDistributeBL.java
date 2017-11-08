package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LDComDB;
import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LDComSchema;
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
 * Title:
 * </p>
 * <p>
 * Description: 给付金额分布统计
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author
 * @version 1.0
 */

public class LLPRRPayDistributeBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRPayDistributeBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */

	private GlobalInput mG = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	private String CurrentDate = PubFun.getCurrentDate(); // 系统日期
	private String CurrentTime = PubFun.getCurrentTime(); // 系统时间
	private String mStartDate = ""; // 开始日期
	private String mEndDate = ""; // 结束日期
	private String mLevel = ""; // 机构层面选项
	private String mManageCom = ""; // 机构统计范围选项
	private String mClaimType = ""; // 理赔类型
	private String mPDStAroun = ""; // 统计口径
	private String mPDStArounName = "";
	private String mPDRiskCho = ""; // 险种选项
	private String mPDRiskChoName = "";
	private String mNCLType = ""; // 申请类型

	public LLPRRPayDistributeBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		logger.debug("-统计报表:给付金额分布统计-LLPRRPayDistributeBL测试-开始-");
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

		logger.debug("-统计报表:给付金额分布统计--LLPRRPayDistributeBL测试--结束--");

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

		mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		mStartDate = (String) mTransferData.getValueByName("StartDate"); // 统计起期
		mEndDate = (String) mTransferData.getValueByName("EndDate"); // 统计止期
		mLevel = (String) mTransferData.getValueByName("Level"); // 统计层面
		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 统计机构
		mClaimType = (String) mTransferData.getValueByName("ClaimType"); // 理赔类型
		mPDStAroun = (String) mTransferData.getValueByName("PDStAroun"); // 统计口径
		mPDStArounName = (String) mTransferData.getValueByName("PDStArounName");
		mPDRiskCho = (String) mTransferData.getValueByName("PDRiskCho"); // 险种选项
		mPDRiskChoName = (String) mTransferData.getValueByName("PDRiskChoName");
		mNCLType = (String) mTransferData.getValueByName("NCLType"); // 申请类型

		return true;
	}

	/*
	 * 校验检查外部传入的数据 @param cInputData VData @return boolean
	 */
	private boolean checkInputData() {
		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		LLPRTPubFunBL tLLPRTPubFunBL = new LLPRTPubFunBL();
		// 获取当前系统时间
		String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月"
				+ StrTool.getDay() + "日";
		// 新建一个XmlExport的实例
		XmlExport xmlexport = new XmlExport();
		// 所用模板名称
		xmlexport.createDocument("LLPRRPayDistribute.vts", "");

		logger.debug("*************************************************");
		logger.debug("*********以下为“ListTable实例”准备数据*************");
		// 新建一个ListTable实例
		ListTable tListTable = new ListTable();
		tListTable.setName("PD");
		// 定义列表标题，共17列,由于列数量太多，采用循环的方式
		String[] Title = new String[19];
		for (int i = 1; i <= 19; i++) {
			Title[i - 1] = "";
		}

		// 定义 数组用于存储数据的合计《每个区间的 给付数--给付金额》
		String[] PDAdd = new String[17];// 其中PDAdd[0]不用
		for (int i = 1; i <= 17; i++) {
			PDAdd[i - 1] = "0";
		}

		// 根据传入的 理赔类型拼写 ，判断是否查询要用到该条件，若为空则不用，否则用该条件
		String tIsClaimType = mClaimType.equals("") ? ""
				: " and a.rgtno in( select b.clmno from llclaimdutykind b where b.getdutykind='"
						+ "?claimtype?" + "')";

		// 首先根据 外部传入的参数“机构统计范围---strManageCom”
		// 和“机构层面-----strLevel(01-总公司、02-分公司、03-中支公司、04-支公司、05-用户”
		logger.debug("**************以下准备根据外部条件，拼写外层查询语句***********");
		String strLevelSql = "";
		if (mLevel.equals("05")) {
			// 查询所选机构下的 所有理赔用户
			strLevelSql = " select usercode,username from llclaimuser where 1=1 "
					+ " and comcode like concat('"
					+ "?mngcom?"
					+ "','%') order by usercode ";
			logger.debug("***********外层查询语句<用户>===" + strLevelSql);
		} else {
			// 查选出所选机构下的处于所选机构级别<机构层面>的所有 机构，用于外层循环
			strLevelSql = " select comcode,name from ldcom where 1=1 "
					+ " and comcode like concat('" + "?mngcom?" + "','%') "
					+ " and trim(comgrade)='" + "?level?" + "' "
					+ " order by comcode ";
			logger.debug("***********外层查询语句<机构>===" + strLevelSql);
		}
		// 用数组来存入，8个金额分布区间,16个起止数《 t 表为 llclaim<赔案表> 》
		// 应用时直接将 数组嵌入查询语句中
		String[] MRange = new String[8];
		MRange[0] = " and t.standpay>=0 and t.standpay<500"; // *0-500*/
		MRange[1] = " and t.standpay>=500 and t.standpay<1000"; // *500-1000*/
		MRange[2] = " and t.standpay>=1000 and t.standpay<5000"; // *1000-5000*/
		MRange[3] = " and t.standpay>=5000 and t.standpay<10000"; // *5000-1万*/
		MRange[4] = " and t.standpay>=10000 and t.standpay<30000"; // *1万-3万*/
		MRange[5] = " and t.standpay>=30000 and t.standpay<50000"; // *3万-5万*/
		MRange[6] = " and t.standpay>=50000 and t.standpay<100000"; // *5万-10万*/
		MRange[7] = " and t.standpay>=100000 "; // *10万以上*/

		String tRiskChoTa = mPDRiskCho.equals("01") ? " and exists (select 'X' from llclaimpolicy x where x.riskcode = z.riskcode and a.rgtno = x.clmno )"
				: "";// 险种选项_连llclaimpolicy x 表
		// String tRiskChoCo = mPDRiskCho.equals("01") ? " and a.rgtno = x.clmno
		// and x.riskcode = z.riskcode " : "";//险种选项_连接条件
		String tRiskChoLa = mPDRiskCho.equals("01") ? " , substr(z.riskcode,3,3) from llclaimpolicy z group by z.riskcode order by z.riskcode "
				: ",'不分险种' from dual ";// 险种选项_查险种及表尾分组
		String tStArounTa = mPDStAroun.equals("01") ? ",llclaimuwmain r " : "";// 统计口径_连llclaimuwmain
																				// r 表
		String tStArounCo = mPDStAroun.equals("01") ? " and a.rgtno = r.clmno "
				: "";// 统计口径_连接条件
		String tStArounTi = mPDStAroun.equals("01") ? " and r.examconclusion = '0' and r.examdate "
				: " and a.clmstate='60' and a.endcaseflag = '1' and a.endcasedate ";// 统计口径_时间字段（审批或结案）
		// 申请类型判断llregister.rgtobj：1-个险 2-团险
		String tApplType = mNCLType.trim().equals("1") ? " and a.rgtobj = '1' "
				: " and a.rgtobj = '2'  ";
		// 内层查询语句<由于查询字段太多<17个项目>，且语句结构相似，故利用循环来拼写而成>
		// 查询“给付件数”的SQL语句
		String tNumSQL = " select count(1) from llregister a,llclaim t "
				+ tStArounTa + " where 1=1 " + tRiskChoTa + tStArounCo
				+ " and t.clmno=a.rgtno " + tIsClaimType + tApplType
				+ " and t.givetype='0' " + tStArounTi + " between '"
				+ "?startdate?" + "' and '" + "?enddate?" + "'";
		String tNumHE = " select count(1) from llregister a,llclaim t "
				+ tStArounTa// 分险种统计时各项数据求和的SQL
				+ " where 1=1 " + tStArounCo + " and t.clmno=a.rgtno "
				+ tIsClaimType + tApplType + " and t.givetype='0' "
				+ tStArounTi + " between '" + "?startdate?" + "' and '" + "?enddate?"
				+ "'";
		logger.debug("**************以下执行外层查询语句********************");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		sqlbv.sql(strLevelSql);
		sqlbv.put("mngcom", mManageCom);
		sqlbv.put("level", mLevel);
		ExeSQL tLevelExeSQL = new ExeSQL();
		SSRS tLevelSSRS = new SSRS();
		tLevelSSRS = tLevelExeSQL.execSQL(sqlbv);
		int tMaxRow = tLevelSSRS.getMaxRow();
		for (int index = 1; index <= tMaxRow; index++) {
			logger.debug("****第 " + index + " 次外层循环*********开始**********");
			// 为列表的每行（19列）新声明一个数组，并为其赋值，然后添加到列表中去
			String[] Stra = new String[19];
			for (int i = 1; i <= 19; i++) {
				Stra[i - 1] = "";
			}
			String tCode = tLevelSSRS.GetText(index, 1); // 机构或用户代码
			String tName = tLevelSSRS.GetText(index, 2); // 机构或用户名称
			String tomSQL = mLevel.equals("05") ? " and a.operator='" + "?code?"
					+ "' " // 用户区分
			: " and a.mngcom like concat('" + "?code?" + "','%') "; // 机构区分
			// 查询“给付总金额”的SQL语句
			String tSumSQL = " select (case when sum(t.standpay) is null then 0 else sum(t.standpay) end) from llregister a,llclaim t "
					+ tStArounTa
					+ " where 1=1 "
					+ tRiskChoTa
					+ tStArounCo
					+ " and t.clmno=a.rgtno "
					+ tIsClaimType
					+ tApplType
					+ " and t.givetype='0' "
					+ tStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " and a.rgtno not in (select llappeal.caseno from llappeal "// 原赔案对应申诉案件结案且符合统计条件，原赔案不统计
					+ " where llappeal.appealno in (select a.rgtno from llregister a,llclaim t "
					+ tStArounTa
					+ " where 1=1 "
					+ tStArounCo
					+ " and t.clmno=a.rgtno "
					+ tIsClaimType
					+ tApplType
					+ " and t.givetype = '0' "
					+ " and a.clmstate = '60' "// 申诉案件在统计条件内，且已结案
					+ " and a.rgtstate = '13' "// 申诉案件，不考虑申诉案件金额范围
					+ tStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?" + "'" + tomSQL + "))";
			String tSumHE = " select (case when sum(t.standpay) is null then 0 else sum(t.standpay) end) from llregister a,llclaim t "
					+ tStArounTa
					+ " where 1=1 "
					+ tStArounCo
					+ " and t.clmno=a.rgtno "// 分险种统计时各项数据求和的SQL,条件要匹配
					+ tIsClaimType
					+ tApplType
					+ " and t.givetype='0' "
					+ tStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?"
					+ "'"
					+ " and a.rgtno not in (select llappeal.caseno from llappeal "
					+ " where llappeal.appealno in (select a.rgtno from llregister a,llclaim t "
					+ tStArounTa
					+ " where 1=1 "
					+ tStArounCo
					+ " and t.clmno=a.rgtno "
					+ tIsClaimType
					+ tApplType
					+ " and t.givetype = '0' "
					+ " and a.clmstate = '60' "// 申诉案件在统计条件内，且已结案
					+ " and a.rgtstate = '13' "// 申诉案件
					+ tStArounTi
					+ " between '"
					+ "?startdate?"
					+ "' and '"
					+ "?enddate?" + "'" + tomSQL + "))";
			String tQueryInSQL = "";
			String tQueryHESQL = "";// 分险种统计时各项数据求和的SQL
			if (mLevel.equals("05")) {// 选择了根据“用户”来统计
				for (int j = 1; j <= 8; j++) {
					String tpaySQL = MRange[j - 1]; // 金额分布区间控制
					// String tomSQL = " and a.operator='"+ tCode +"'"; //用户区分
					String ttNumSQL = tNumSQL + tpaySQL + tomSQL;
					String ttSumSQL = tSumSQL + tpaySQL + tomSQL;
					String ttNumHE = tNumHE + tpaySQL + tomSQL;// 分险种统计时各项数据求和的SQL
					String ttSumHE = tSumHE + tpaySQL + tomSQL;
					tQueryInSQL = tQueryInSQL + ",( " + ttNumSQL + "),( "
							+ ttSumSQL + ")";
					tQueryHESQL = tQueryHESQL + ",( " + ttNumHE + "),( "
							+ ttSumHE + ")";// 分险种统计时各项数据求和的SQL
				}
				tQueryInSQL = "select '" + "?name?" + "'" + tQueryInSQL + ",'"
						+ "?code?" + "'" + tRiskChoLa;
				tQueryHESQL = "select '" + "?name?" + "'" + tQueryHESQL + ",'"
						+ "?code?" + "'" + ",'不分险种' from dual ";// 分险种统计时各项数据求和的SQL
				logger.debug("***针对用户**" + tCode + "---" + tName
						+ "的查询语句");
			} else {// 选择了根据“非用户”来统计
				for (int j = 1; j <= 8; j++) {
					String tpaySQL = MRange[j - 1]; // 金额分布区间控制
					// String tomSQL = " and a.mngcom like '"+ tCode +"%'";
					// //机构区分
					String ttNumSQL = tNumSQL + tpaySQL + tomSQL;
					String ttSumSQL = tSumSQL + tpaySQL + tomSQL;
					String ttNumHE = tNumHE + tpaySQL + tomSQL;
					String ttSumHE = tSumHE + tpaySQL + tomSQL;
					tQueryInSQL = tQueryInSQL + ",( " + ttNumSQL + "),( "
							+ ttSumSQL + ")";
					tQueryHESQL = tQueryHESQL + ",( " + ttNumHE + "),( "
							+ ttSumHE + ")";
				}
				tQueryInSQL = "select '" + "?name?" + "'" + tQueryInSQL + ",'"
						+ "?code?" + "'" + tRiskChoLa;
				tQueryHESQL = "select '" + "?name?" + "'" + tQueryHESQL + ",'"
						+ "?code?" + "'" + ",'不分险种' from dual ";
				logger.debug("***针对机构**" + tCode + "---" + tName
						+ "的查询语句");
			}
			logger.debug("========" + tQueryInSQL);
			logger.debug("**********************************************");
			ExeSQL tInExeSQL = new ExeSQL();
			SSRS tInSSRS = new SSRS();
			SSRS tHESSRS = new SSRS();// 分险种统计时各项数据求和的SQL
			if (mPDRiskCho.equals("01")) {
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tQueryHESQL);
				sqlbv1.put("code", tCode);
				sqlbv1.put("name", tName);
				sqlbv1.put("startdate", mStartDate);
				sqlbv1.put("enddate", mEndDate);
				sqlbv1.put("claimtype", mClaimType);
				tHESSRS = tInExeSQL.execSQL(sqlbv1);
			}
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tQueryInSQL);
			sqlbv2.put("code", tCode);
			sqlbv2.put("name", tName);
			sqlbv2.put("startdate", mStartDate);
			sqlbv2.put("enddate", mEndDate);
			sqlbv2.put("claimtype", mClaimType);
			tInSSRS = tInExeSQL.execSQL(sqlbv2);
			int ttMaxRow = tInSSRS.getMaxRow();
			// 由于列表的列数量太多，采用循环赋值
			int v = 0;
			for (v = 1; v <= ttMaxRow; v++) {
				logger.debug("****第 " + v + " 次内层循环*********开始**********");
				Stra = new String[19];
				for (int k = 1; k <= 19; k++) {
					Stra[k - 1] = tInSSRS.GetText(v, k);
				}
				tListTable.add(Stra);
				if (mPDRiskCho.equals("01"))// 分险种统计时各项数据求和
				{
					if (v == 1) {
						for (int n = 2; n <= 16; n = n + 2) {
							// 计算给付数合计Modify by zhaorx 2006-10-13
							int aN = Integer.parseInt(tHESSRS.GetText(1, n));
							PDAdd[n - 1] = String.valueOf(aN
									+ Integer.parseInt(PDAdd[n - 1]));
							// 计算金额合计
							double xM = Double.parseDouble(tHESSRS.GetText(1,
									(n + 1)));
							PDAdd[n] = String.valueOf(xM
									+ Double.parseDouble(PDAdd[n]));
						}
					}
				} else {// 计算合计每个区间的 给付数--给付金额-----8个区间16项，对应数据行的1-16列》
					for (int m = 1; m <= 15; m = m + 2) {
						// 计算给付数合计<奇数列>
						int a = Integer.parseInt(PDAdd[m]);
						int b = Integer.parseInt(Stra[m].trim());
						int c = a + b;
						PDAdd[m] = String.valueOf(c);
						// 计算金额合计<偶数列>
						double x = Double.parseDouble(PDAdd[m + 1]);
						double y = Double.parseDouble(Stra[m + 1]);
						double z = x + y;
						PDAdd[m + 1] = String.valueOf(z);
					}
				}
				logger.debug("****第 " + v + " 次内层循环*********结束**********");
			}
			logger.debug("****第 " + index + " 次外层循环*********结束**********");
		}
		logger.debug("*****以上为“ListTable实例”准备数据完成，成功！！！***");
		logger.debug("***********************************************");

		logger.debug("*********以下为“TextTag实例”准备数据*************");
		// 新建一个TextTag的实例
		TextTag tTextTag = new TextTag();
		// 为表头准备数据
		String PDDate = SysDate;// 统计日期: PDDate
		String PDStatiRange = mStartDate + "至" + mEndDate;// 统计时间段：PDStatiRange
		// 机构统计范围：PDMngCom
		LDComSchema tLDComSchema = new LDComSchema();
		LDComDB tLDComDB = new LDComDB();
		tLDComDB.setComCode(mManageCom);
		tLDComDB.getInfo();
		tLDComSchema.setSchema(tLDComDB.getSchema());
		String PDMngCom = tLDComSchema.getName();// 机构统计范围：PDMngCom
		// 机构层面：PDLevel
		String PDLevel = "";
		switch (Integer.parseInt(mLevel)) {
		case 1:
			PDLevel = "总公司";
			break;
		case 2:
			PDLevel = "分公司";
			break;
		case 3:
			PDLevel = "中支公司";
			break;
		case 4:
			PDLevel = "支公司";
			break;
		case 5:
			PDLevel = "用户";
			break;
		default:
			PDLevel = "";
			break;
		}
		// 理赔类型：PDClaimType
		String PDClaimType = mClaimType.equals("") ? " 所有类型 " : " "
				+ tLLPRTPubFunBL.getLDCode("llclaimtype", mClaimType) + " ";
		// 统计层面：PDStati
		String PDStati = "赔案层面";
		// 申请类型: $=/PDApplType$
		String mNCLTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		tTextTag.add("PDApplType", mNCLTypeName);
		// 为表头赋值
		tTextTag.add("PDDate", PDDate); // 统计日期: $=/PDDate$
		tTextTag.add("PDRange", PDStatiRange);// 统计时间段：$=/PDStatiRange$
		tTextTag.add("PDMngCom", PDMngCom); // 机构统计范围：$=/PDMngCom$
		tTextTag.add("PDLevel", PDLevel);// 机构层面：$=/PDLevel$
		tTextTag.add("PDClaimType", PDClaimType); // 理赔类型：$=/PDClaimType$
		tTextTag.add("PDStati", PDStati);// 统计层面： $=/PDStat$
		tTextTag.add("PDStAround", mPDStArounName);// 统计口径：$=/PDStAround$
		tTextTag.add("PDRiskChoice", mPDRiskChoName);// 险种选项:$=/PDRiskChoice$
		// 循环为合计项目赋值
		for (int m = 1; m <= 16; m++) {
			String str = "PDAdd" + String.valueOf(m);
			tTextTag.add(str, PDAdd[m]);
		}

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

		tTransferData.setNameAndValue("StartDate", "2005-10-01"); // 统计起期
		tTransferData.setNameAndValue("EndDate", "2005-11-01"); // 统计止期
		tTransferData.setNameAndValue("Level", "05"); // 统计层面 01 02 03 04 05
		tTransferData.setNameAndValue("ManageCom", "86"); // 统计机构
		tTransferData.setNameAndValue("ClaimType", ""); // 理赔类型
		tTransferData.setNameAndValue("PDStAroun", "01"); // 统计口径 01 02
		tTransferData.setNameAndValue("PDRiskCho", "02"); // 险种选项 01 02

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRPayDistributeBL tLLPRRPayDistributeBL = new LLPRRPayDistributeBL();
		if (tLLPRRPayDistributeBL.submitData(tVData, "") == false) {
			logger.debug("-------统计报表打印：给付金额分布统计---失败----");
		}
		int n = tLLPRRPayDistributeBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRPayDistributeBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}
}
