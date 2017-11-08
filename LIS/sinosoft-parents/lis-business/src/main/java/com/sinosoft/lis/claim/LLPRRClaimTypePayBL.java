package com.sinosoft.lis.claim;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
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
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 理赔报表打印：理赔类型给付统计--LLPRRClaimTypePayBL.vts
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author
 * @version 1.0
 */
public class LLPRRClaimTypePayBL implements PrintService {
private static Logger logger = Logger.getLogger(LLPRRClaimTypePayBL.class);
	public CErrors mErrors = new CErrors();
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	private VData mInputData = new VData();
	/** 往后面传输数据的容器 */
	private VData mResult = new VData();
	/** 往界面传输数据的容器 */
	private String mOperate;
	/** 数据操作字符串 */
	private MMap mMMap = new MMap();

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();

	private GlobalInput mGlobalInput = new GlobalInput();
	/** 全局数据 */
	private TransferData mTransferData = new TransferData();

	/** 前台传入内容 */
	private String mStartDate = "";// 统计开始日期
	private String mEndDate = "";// 统计截止日期
	private String mLevel = "";// 机构层面
	private String mLevelName = "";
	private String mManageCom = "";// 机构范围
	private String mManageComName = "";
	private String mSortType = "";// 统计层面选项（赔案、保项）
	private String mSortTypeName = "";
	private String mTime = "";
	private String mStatArou = "";// 统计口径
	private String mStatArouName = "";
	private String mRiskChance = "";// 险种选项
	private String mRiskChanceName = "";
	private String mMng_User = "";// 应用于函数中的字符串
	private String mRiskCode = "";// 应用于函数中的字符串
	private String mNCLType = "";// 申请类型

	public LLPRRClaimTypePayBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param cInputData
	 *            输入的数据 cOperate 数据操作
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 数据处理
		if (!dealData()) {
			return false;
		}
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
		this.mInputData = cInputData;

		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		this.mStartDate = (String) mTransferData.getValueByName("StartDate");// 统计开始日期
		this.mEndDate = (String) mTransferData.getValueByName("EndDate");// 统计截止日期
		this.mLevel = (String) mTransferData.getValueByName("Level");// 机构统计层面
		this.mLevelName = (String) mTransferData.getValueByName("LevelName");
		this.mManageCom = (String) mTransferData.getValueByName("ManageCom");// 机构统计范围
		this.mManageComName = (String) mTransferData
				.getValueByName("ManageComName");
		this.mSortType = (String) mTransferData.getValueByName("SortType");// 统计层面选项（赔案、保项）
		this.mSortTypeName = (String) mTransferData
				.getValueByName("SortTypeName");
		this.mStatArou = (String) mTransferData.getValueByName("StatArou");// 统计口径
		this.mStatArouName = (String) mTransferData
				.getValueByName("StatArouName");
		this.mRiskChance = (String) mTransferData.getValueByName("RiskChance");// 险种选项
		this.mRiskChanceName = (String) mTransferData
				.getValueByName("RiskChanceName");
		this.mNCLType = (String) mTransferData.getValueByName("NCLType");// 申请类型

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		TextTag texttag = new TextTag();
		XmlExport xmlexport = new XmlExport();
		xmlexport.createDocument("LLPRRClaimTypePayBL.vts", "");
		String tStmt;
		String tPANoRisk;// 赔案层面分险种统计时汇总求和的SQL
		double tDecimalFormat = 0;
		String[] SPRAdd = new String[32];
		for (int i = 0; i < 32; i++) {
			SPRAdd[i] = "0";
		}

		// 表中数据项显示
		ListTable tlistTable = new ListTable();
		tlistTable.setName("SPR");
		ListTable tlistTableSum = new ListTable();
		tlistTableSum.setName("SPRAdd");

		mTime = " between date'" + "?startdate?" + "' and date'" + "?enddate?" + "'";
		String strRiskCodeHead = mRiskChance.equals("1") ? " , substr(x.riskcode,3,3) "
				: " ,'不分险种' ";// 险种选项条件

		// if (!mLevel.equals("5"))
		// {
		// tStmt = " select x.managecom " + strRiskCodeHead;
		// tPANoRisk = " select x.managecom ,'不分险种' ";
		// }
		// else
		// {
		// tStmt = "select x.usercode " + strRiskCodeHead;
		// tPANoRisk = "select x.usercode ,'不分险种'" ;
		// }

		// tStmt += getCalSql("02") + getCalSql("03") + getCalSql("04")
		// + getCalSql("00") + getCalSql("01") + getCalSql_09()
		// + getCalSql("05") + getCalSql("06");
		// tPANoRisk += getPANoRisk("02") + getPANoRisk("03") +
		// getPANoRisk("04")
		// + getPANoRisk("00") + getPANoRisk("01") + getPANoRisk_09()
		// + getPANoRisk("05") + getPANoRisk("06");

		// 生成统一的查询条件

		String strRiskCodeLast = mRiskChance.equals("1") ? " , b.riskcode riskcode "
				: "  ";
		String tFirstStmt = "";
		String tFirstPANoRisk = "";
		if (!mLevel.equals("5")) {
			tFirstStmt += " select distinct substr(a.mngcom, 1, " + mLevel
					+ " * 2) managecom " + strRiskCodeLast;
			tFirstPANoRisk += " select distinct substr(a.mngcom, 1, " + mLevel
					+ " * 2) managecom " + strRiskCodeLast;
		} else {
			tFirstStmt += " select distinct a.operator usercode "
					+ strRiskCodeLast;
			tFirstPANoRisk += " select distinct a.operator usercode "
					+ strRiskCodeLast;
		}

		// String tMn_Op = mLevel.equals("5") ? " ":" and
		// length(trim(a.mngcom))>=2*" + mLevel + " ";
		String tStatArouTa = mStatArou.equals("1") ? " ,llclaimuwmain c where a.clmno = c.clmno and a.clmno = b.clmno and c.examconclusion = '0' and c.examdate "
				: " where a.clmno = b.clmno and a.clmstate = '60' and a.endcasedate  ";// 整个SQL结尾时间字段

		tFirstStmt += " from llclaim a,llclaimpolicy b  " + tStatArouTa + mTime// where
																				// a.clmno=b.clmno
																				// and
																				// a.endcasedate
				// + " and a.clmstate = '60' "
				+ " and a.mngcom like concat('" + "?mngcom?" + "','%')";
		// + tMn_Op + " ";

		tFirstPANoRisk += " from llclaim a,llclaimpolicy b  " + tStatArouTa
				+ mTime + " and a.mngcom like concat('" + "?mngcom?" + "','%')";
		// + tMn_Op + " ";

		ExeSQL exeSQL0 = new ExeSQL();
		SSRS tFirstSSRS = new SSRS();
		if (mRiskChance.equals("1") && mStatArou.equals("1"))// 赔案层面，分险种统计时才执行这个SQL语句求各项和
		{
			logger.debug("***tFirstPANoRisk***" + tFirstPANoRisk);
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql(tFirstPANoRisk);
			sqlbv.put("mngcom", mManageCom);
			sqlbv.put("startdate", mStartDate);
			sqlbv.put("enddate", mEndDate);
			tFirstSSRS = exeSQL0.execSQL(sqlbv);
		} else {
			logger.debug("***tFirstStmt***" + tFirstStmt);
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tFirstStmt);
			sqlbv1.put("mngcom", mManageCom);
			sqlbv1.put("startdate", mStartDate);
			sqlbv1.put("enddate", mEndDate);
			tFirstSSRS = exeSQL0.execSQL(sqlbv1);
		}
		if (tFirstSSRS.getMaxRow() != 0) {
			for (int index = 1; index <= tFirstSSRS.getMaxRow(); index++) // for1
			{

				// ExeSQL exeSQL1 = new ExeSQL();
				// logger.debug("***tStmt***"+tStmt);
				// SSRS tSSRS = new SSRS();
				// logger.debug("A"+tPANoRisk);
				// if(mRiskChance.equals("1")&&mStatArou.equals("1"))//赔案层面，分险种统计时才执行这个SQL语句求各项和
				// {
				// logger.debug(tPANoRisk);
				// tSSRS = exeSQL1.execSQL(tPANoRisk);
				// }
				// SSRS ssrs = exeSQL1.execSQL(tStmt);

				// if (ssrs.getMaxRow() != 0)
				// {
				// for (int index = 1; index <= ssrs.getMaxRow(); index++)
				// //for1
				// {

				String[] cols = new String[35]; // 表中主体内容
				ExeSQL exeSQL1 = new ExeSQL();
				mMng_User = tFirstSSRS.GetText(index, 1);
				if (mRiskChance.equals("1")) {
					mRiskCode = tFirstSSRS.GetText(index, 2);
				}

				// 机构或者服务人员
				String tMOOcode = tFirstSSRS.GetText(index, 1);
				String tMOOcodeName = mLevel.equals("5") ? " select username from lduser where usercode  ='"
						+ "?moocode?" + "' "
						: " select name from ldcom where comcode='" + "?moocode?"
								+ "' ";
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				sqlbv2.sql(tMOOcodeName);
				sqlbv2.put("moocode",tMOOcode);
				SSRS ssrs2 = exeSQL1.execSQL(sqlbv2);
				cols[0] = tMOOcode;
				if (mRiskChance.equals("1")) {
					cols[33] = tFirstSSRS.GetText(index, 2);
				} else {
					cols[33] = "";
				}
				// cols[33] = tFirstSSRS.GetText(index,2);
				cols[34] = ssrs2.GetText(1, 1);

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.1 ***************");
				SSRS ssrs_02_1 = exeSQL1.execSQL(getCalSql("02", "1"));
				SSRS ssrs_02_2 = exeSQL1.execSQL(getCalSql("02", "2"));
				SSRS ssrs_02_3 = exeSQL1.execSQL(getCalSql("02", "3"));
				SSRS ssrs_02_4 = exeSQL1.execSQL(getCalSql("02", "4"));

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.2 ***************");
				SSRS ssrs_03_1 = exeSQL1.execSQL(getCalSql("03", "1"));
				SSRS ssrs_03_2 = exeSQL1.execSQL(getCalSql("03", "2"));
				SSRS ssrs_03_3 = exeSQL1.execSQL(getCalSql("03", "3"));
				SSRS ssrs_03_4 = exeSQL1.execSQL(getCalSql("03", "4"));

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.3 ***************");
				SSRS ssrs_04_1 = exeSQL1.execSQL(getCalSql("04", "1"));
				SSRS ssrs_04_2 = exeSQL1.execSQL(getCalSql("04", "2"));
				SSRS ssrs_04_3 = exeSQL1.execSQL(getCalSql("04", "3"));
				SSRS ssrs_04_4 = exeSQL1.execSQL(getCalSql("04", "4"));

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.4 ***************");
				SSRS ssrs_00_1 = exeSQL1.execSQL(getCalSql("00", "1"));
				SSRS ssrs_00_2 = exeSQL1.execSQL(getCalSql("00", "2"));
				SSRS ssrs_00_3 = exeSQL1.execSQL(getCalSql("00", "3"));
				SSRS ssrs_00_4 = exeSQL1.execSQL(getCalSql("00", "4"));

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.5 ***************");
				SSRS ssrs_01_1 = exeSQL1.execSQL(getCalSql("01", "1"));
				SSRS ssrs_01_2 = exeSQL1.execSQL(getCalSql("01", "2"));
				SSRS ssrs_01_3 = exeSQL1.execSQL(getCalSql("01", "3"));
				SSRS ssrs_01_4 = exeSQL1.execSQL(getCalSql("01", "4"));

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.6 ***************");
				SSRS ssrs_09_1 = exeSQL1.execSQL(getCalSql_09("1"));
				SSRS ssrs_09_2 = exeSQL1.execSQL(getCalSql_09("2"));
				SSRS ssrs_09_3 = exeSQL1.execSQL(getCalSql_09("3"));
				SSRS ssrs_09_4 = exeSQL1.execSQL(getCalSql_09("4"));

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.7 ***************");
				SSRS ssrs_05_1 = exeSQL1.execSQL(getCalSql("05", "1"));
				SSRS ssrs_05_2 = exeSQL1.execSQL(getCalSql("05", "2"));
				SSRS ssrs_05_3 = exeSQL1.execSQL(getCalSql("05", "3"));
				SSRS ssrs_05_4 = exeSQL1.execSQL(getCalSql("05", "4"));

				logger.debug("************" + index + "/"
						+ tFirstSSRS.getMaxRow() + " No.8 ***************");
				SSRS ssrs_06_1 = exeSQL1.execSQL(getCalSql("06", "1"));
				SSRS ssrs_06_2 = exeSQL1.execSQL(getCalSql("06", "2"));
				SSRS ssrs_06_3 = exeSQL1.execSQL(getCalSql("06", "3"));
				SSRS ssrs_06_4 = exeSQL1.execSQL(getCalSql("06", "4"));

				if (mRiskChance.equals("1") && mStatArou.equals("1"))// 赔案层面，分险种统计时才执行这个SQL语句求各项和
				{

				}
				logger.debug("*** ssrs_02_1.GetText(1, 1) ***"
						+ ssrs_02_1.GetText(1, 1));
				cols[1] = ssrs_02_1.GetText(1, 1);
				cols[2] = ssrs_02_2.GetText(1, 1);
				cols[3] = ssrs_02_3.GetText(1, 1);
				cols[4] = ssrs_02_4.GetText(1, 1);

				logger.debug("*** ssrs_03_1.GetText(1, 1) ***"
						+ ssrs_03_1.GetText(1, 1));
				cols[5] = ssrs_03_1.GetText(1, 1);
				cols[6] = ssrs_03_2.GetText(1, 1);
				cols[7] = ssrs_03_3.GetText(1, 1);
				cols[8] = ssrs_03_4.GetText(1, 1);

				logger.debug("*** ssrs_04_1.GetText(1, 1) ***"
						+ ssrs_04_1.GetText(1, 1));
				cols[9] = ssrs_04_1.GetText(1, 1);
				cols[10] = ssrs_04_2.GetText(1, 1);
				cols[11] = ssrs_04_3.GetText(1, 1);
				cols[12] = ssrs_04_4.GetText(1, 1);

				logger.debug("*** ssrs_00_1.GetText(1, 1) ***"
						+ ssrs_00_1.GetText(1, 1));
				cols[13] = ssrs_00_1.GetText(1, 1);
				cols[14] = ssrs_00_2.GetText(1, 1);
				cols[15] = ssrs_00_3.GetText(1, 1);
				cols[16] = ssrs_00_4.GetText(1, 1);

				logger.debug("*** ssrs_01_1.GetText(1, 1) ***"
						+ ssrs_01_1.GetText(1, 1));
				cols[17] = ssrs_01_1.GetText(1, 1);
				cols[18] = ssrs_01_2.GetText(1, 1);
				cols[19] = ssrs_01_3.GetText(1, 1);
				cols[20] = ssrs_01_4.GetText(1, 1);

				logger.debug("*** ssrs_09_1.GetText(1, 1) ***"
						+ ssrs_09_1.GetText(1, 1));
				cols[21] = ssrs_09_1.GetText(1, 1);
				logger.debug("***ssrs_09_1.GetText(1, 1)***"
						+ ssrs_09_1.GetText(1, 1));
				cols[22] = ssrs_09_2.GetText(1, 1);
				logger.debug("***ssrs_09_2.GetText(1, 1)***"
						+ ssrs_09_2.GetText(1, 1));
				cols[23] = ssrs_09_3.GetText(1, 1);
				logger.debug("***ssrs_09_3.GetText(1, 1)***"
						+ ssrs_09_3.GetText(1, 1));
				cols[24] = ssrs_09_4.GetText(1, 1);
				logger.debug("***ssrs_09_4.GetText(1, 1)***"
						+ ssrs_09_4.GetText(1, 1));

				logger.debug("*** ssrs_05_1.GetText(1, 1) ***"
						+ ssrs_05_1.GetText(1, 1));
				cols[25] = ssrs_05_1.GetText(1, 1);
				cols[26] = ssrs_05_2.GetText(1, 1);
				cols[27] = ssrs_05_3.GetText(1, 1);
				cols[28] = ssrs_05_4.GetText(1, 1);

				logger.debug("*** ssrs_06_1.GetText(1, 1) ***"
						+ ssrs_06_1.GetText(1, 1));
				cols[29] = ssrs_06_1.GetText(1, 1);
				cols[30] = ssrs_06_2.GetText(1, 1);
				cols[31] = ssrs_06_3.GetText(1, 1);
				cols[32] = ssrs_06_4.GetText(1, 1);

				logger.debug("*** END ***");

				// if(mRiskChance.equals("1")&&mStatArou.equals("1"))//赔案层面，分险种统计时才执行这个SQL语句求各项和
				// {
				for (int k = 0; k <= 31; k++)// 重新赋值
				{
					SPRAdd[k] = String.valueOf(Double.parseDouble(SPRAdd[k])
							+ Double.parseDouble(cols[k + 1]));
				}
				// }

				// 其它的项目

				// for (int counts = 2 ; counts <= 33; counts++)
				// {
				// cols[counts-1]=ssrs.GetText(index, (counts+1));
				// tDecimalFormat = Double.parseDouble(SPRAdd[counts-2]) +
				// Double.parseDouble(ssrs.GetText(index, (counts+1))) ;
				// SPRAdd[counts-2] = String.valueOf(new
				// DecimalFormat("0.00").format(tDecimalFormat)) ;
				// //logger.debug(counts);
				// }

				// logger.debug(SPRAdd[0]);
				tlistTable.add(cols);
				// if(mRiskChance.equals("1")&&mStatArou.equals("1"))//赔案层面，分险种统计时才执行这个SQL语句求各项和
				// {
				// for(int r=0; r<=31; r++)//将上面的和重新置为零
				// {
				// SPRAdd[r] = "0";
				// }
				// for(int z=0; z<=31; z++)//重新赋值
				// {
				// for(int x=1; x<=tSSRS.getMaxRow();
				// x++)//机构统计层面为用户时，用的是赔案层面，不分险种统计时求和
				// {
				// SPRAdd[z] = String.valueOf(Double.parseDouble(SPRAdd[z])
				// + Double.parseDouble(tSSRS.GetText(x,z+3)));
				// }
				// }
				// }

			}

			// if(mRiskChance.equals("1")&&mStatArou.equals("1"))//赔案层面，分险种统计时才执行这个SQL语句求各项和
			// {
			// ExeSQL exeSQL2 = new ExeSQL();
			// SSRS ssrs_02_1_Sum = exeSQL2.execSQL(getPANoRisk("02","1"));
			// SSRS ssrs_02_2_Sum = exeSQL2.execSQL(getPANoRisk("02","2"));
			// SSRS ssrs_02_3_Sum = exeSQL2.execSQL(getPANoRisk("02","3"));
			// SSRS ssrs_02_4_Sum = exeSQL2.execSQL(getPANoRisk("02","4"));
			//
			// SSRS ssrs_03_1_Sum = exeSQL2.execSQL(getPANoRisk("03","1"));
			// SSRS ssrs_03_2_Sum = exeSQL2.execSQL(getPANoRisk("03","2"));
			// SSRS ssrs_03_3_Sum = exeSQL2.execSQL(getPANoRisk("03","3"));
			// SSRS ssrs_03_4_Sum= exeSQL2.execSQL(getPANoRisk("03","4"));
			//
			// SSRS ssrs_04_1_Sum = exeSQL2.execSQL(getPANoRisk("04","1"));
			// SSRS ssrs_04_2_Sum = exeSQL2.execSQL(getPANoRisk("04","2"));
			// SSRS ssrs_04_3_Sum = exeSQL2.execSQL(getPANoRisk("04","3"));
			// SSRS ssrs_04_4_Sum = exeSQL2.execSQL(getPANoRisk("04","4"));
			//
			// SSRS ssrs_00_1_Sum = exeSQL2.execSQL(getPANoRisk("00","1"));
			// SSRS ssrs_00_2_Sum = exeSQL2.execSQL(getPANoRisk("00","2"));
			// SSRS ssrs_00_3_Sum = exeSQL2.execSQL(getPANoRisk("00","3"));
			// SSRS ssrs_00_4_Sum = exeSQL2.execSQL(getPANoRisk("00","4"));
			//
			// SSRS ssrs_01_1_Sum = exeSQL2.execSQL(getPANoRisk("01","1"));
			// SSRS ssrs_01_2_Sum = exeSQL2.execSQL(getPANoRisk("01","2"));
			// SSRS ssrs_01_3_Sum = exeSQL2.execSQL(getPANoRisk("01","3"));
			// SSRS ssrs_01_4_Sum = exeSQL2.execSQL(getPANoRisk("01","4"));
			//
			// SSRS ssrs_09_1_Sum = exeSQL2.execSQL(getPANoRisk_09("1"));
			// SSRS ssrs_09_2_Sum = exeSQL2.execSQL(getPANoRisk_09("2"));
			// SSRS ssrs_09_3_Sum = exeSQL2.execSQL(getPANoRisk_09("3"));
			// SSRS ssrs_09_4_Sum = exeSQL2.execSQL(getPANoRisk_09("4"));
			//
			// SSRS ssrs_05_1_Sum = exeSQL2.execSQL(getPANoRisk("05","1"));
			// SSRS ssrs_05_2_Sum = exeSQL2.execSQL(getPANoRisk("05","2"));
			// SSRS ssrs_05_3_Sum = exeSQL2.execSQL(getPANoRisk("05","3"));
			// SSRS ssrs_05_4_Sum = exeSQL2.execSQL(getPANoRisk("05","4"));
			//
			// SSRS ssrs_06_1_Sum = exeSQL2.execSQL(getPANoRisk("06","1"));
			// SSRS ssrs_06_2_Sum = exeSQL2.execSQL(getPANoRisk("06","2"));
			// SSRS ssrs_06_3_Sum = exeSQL2.execSQL(getPANoRisk("06","3"));
			// SSRS ssrs_06_4_Sum = exeSQL2.execSQL(getPANoRisk("06","4"));
			//
			// SPRAdd[0] = ssrs_02_1_Sum.GetText(1, 1);
			// SPRAdd[1] = ssrs_02_2_Sum.GetText(1, 1);
			// SPRAdd[2] = ssrs_02_3_Sum.GetText(1, 1);
			// SPRAdd[3] = ssrs_02_4_Sum.GetText(1, 1);
			//
			// SPRAdd[4] = ssrs_03_1_Sum.GetText(1, 1);
			// SPRAdd[5] = ssrs_03_2_Sum.GetText(1, 1);
			// SPRAdd[6] = ssrs_03_3_Sum.GetText(1, 1);
			// SPRAdd[7] = ssrs_03_4_Sum.GetText(1, 1);
			//
			// SPRAdd[8] = ssrs_04_1_Sum.GetText(1, 1);
			// SPRAdd[9] = ssrs_04_2_Sum.GetText(1, 1);
			// SPRAdd[10] = ssrs_04_3_Sum.GetText(1, 1);
			// SPRAdd[11] = ssrs_04_4_Sum.GetText(1, 1);
			//
			// SPRAdd[12] = ssrs_00_1_Sum.GetText(1, 1);
			// SPRAdd[13] = ssrs_00_2_Sum.GetText(1, 1);
			// SPRAdd[14] = ssrs_00_3_Sum.GetText(1, 1);
			// SPRAdd[15] = ssrs_00_4_Sum.GetText(1, 1);
			//
			// SPRAdd[16] = ssrs_01_1_Sum.GetText(1, 1);
			// SPRAdd[17] = ssrs_01_2_Sum.GetText(1, 1);
			// SPRAdd[18] = ssrs_01_3_Sum.GetText(1, 1);
			// SPRAdd[19] = ssrs_01_4_Sum.GetText(1, 1);
			//
			// SPRAdd[20] = ssrs_09_1_Sum.GetText(1, 1);
			// SPRAdd[21] = ssrs_09_2_Sum.GetText(1, 1);
			// SPRAdd[22] = ssrs_09_3_Sum.GetText(1, 1);
			// SPRAdd[23] = ssrs_09_4_Sum.GetText(1, 1);
			//
			// SPRAdd[24] = ssrs_05_1_Sum.GetText(1, 1);
			// SPRAdd[25] = ssrs_05_2_Sum.GetText(1, 1);
			// SPRAdd[26] = ssrs_05_3_Sum.GetText(1, 1);
			// SPRAdd[27] = ssrs_05_4_Sum.GetText(1, 1);
			//
			// SPRAdd[28] = ssrs_06_1_Sum.GetText(1, 1);
			// SPRAdd[29] = ssrs_06_2_Sum.GetText(1, 1);
			// SPRAdd[30] = ssrs_06_3_Sum.GetText(1, 1);
			// SPRAdd[31] = ssrs_06_4_Sum.GetText(1, 1);
			//
			// }
			tlistTableSum.add(SPRAdd);
		}

		String[] col = new String[1]; // 空行
		col[0] = "";

		// 查询结果显示
		xmlexport.addListTable(tlistTable, col);
		// 合计数据项显示
		xmlexport.addListTable(tlistTableSum, col);
		// 报表条件显示
		texttag.add("SPRStartDate", mStartDate); // 统计开始日期
		texttag.add("SPREndDate", mEndDate); // 统计截止日期
		texttag.add("SPRManageCom", mManageComName); // 机构统计范围
		texttag.add("SPRLevel", mLevelName); // 机构统计层面
		texttag.add("SPRSortType", mSortTypeName); // 统计层面选项（1-赔案、2-保项）
		texttag.add("SPRStatAroun", mStatArouName); // 统计口径:$=/SPRStatAroun$
		texttag.add("SPRRiskChance", mRiskChanceName);// 险种选项:$=/SPRRiskChance$
		// 申请类型
		String tApplTypeName = mNCLType.trim().equals("1") ? "个人" : "团体";
		texttag.add("SPRApplType", tApplTypeName);// 申请类型:$=/SPRApplType$

		if (texttag.size() > 0) {
			xmlexport.addTextTag(texttag);
		}
		// xmlexport.outputDocumentToFile("D:\\", "test22"); //输出xml文档到文件
		mResult.clear();
		mResult.addElement(xmlexport);
		return true;
	}

	/**
	 * getCalSql_09
	 * 
	 * @return String
	 */
	private SQLwithBindVariables getCalSql_09(String returnNo) {
		String tAgreeCount;
		String tAgreePay;
		String tRefuseCount;
		String tRefuseAmount;
		String tGetDutyKind;
		String tMgn_Operator;
		SQLwithBindVariables tResult;
		// String tClmState;

		// 生成getdutykind的子字符串
		tGetDutyKind = " and substr(a.getdutykind, 2, 2) = '09' ";
		// 生成ClmState的子字符串
		// tClmState = " and llclaim.clmstate = '60' ";
		// 统计口径条件判断取用哪个字符串
		String tStatArouMngcom = mStatArou.equals("1") ? " llclaimuwmain.mngcom "
				: " llclaim.mngcom ";
		String tStatArouOperat = mStatArou.equals("1") ? " llclaimuwmain.operator "
				: " llclaim.operator ";
		// 生成机构或操作员的子字符串
		if (!mLevel.equals("5")) {
			tMgn_Operator = " and substr(" + tStatArouMngcom + ", 1," + "?level?"
					+ "*2) = '" + "?mnguser?" + "'";
		} else {
			tMgn_Operator = " and " + tStatArouOperat + " = '" + "?mnguser?"
					+ "'";
		}
		// 险种选项条件判断取用哪个字符串
		String strClaimOrDetail = mSortType.equals("1") ? " llclaim.clmno "
				: " llclaimdetail.clmno ";
		String strRiskChanceCount = mRiskChance.equals("1") ? " and exists(select 'X' from llclaimpolicy where clmno="
				+ strClaimOrDetail + " and riskcode= '" + "?riskcode?" + "') "
				: " ";
		String strRiskChanceMoney = mRiskChance.equals("1") ? " and exists(select 'X' from llclaimpolicy where clmno=llclaimdetail.clmno and riskcode= '"
				+ "?riskcode?" + "') "
				: " ";
		String strRiskChanceMoneya = mRiskChance.equals("1") ? " and exists(select 'X' from llclaimpolicy where clmno=a.clmno and riskcode= '"
				+ "?riskcode?" + "') "
				: " ";
		// 统计口径条件判断取用哪个字符串
		String tStatArouUw = mStatArou.equals("1") ? " llclaim,llclaimuwmain where llclaimuwmain.clmno=llclaim.clmno "
				: " llclaim where 1 = 1 ";
		String tStatArouTi = mStatArou.equals("1") ? " llclaimuwmain.examconclusion = '0' and llclaimuwmain.examdate "
				: " llclaim.clmstate = '60' and llclaim.endcasedate ";
		String tStatArouCo = mStatArou.equals("1") ? " llclaimuwmain.clmno "
				: " llclaim.clmno ";
		// 申请类型判断
		String tApplTypeClaim = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='2') ";// llclaim
		String tApplTypeDetail = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='2') ";// llclaimdetail
		String tApplTypeDetaila = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=a.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=a.clmno and llregister.rgtobj='2') ";// llclaimdetail
																														// a
		// 根据选择的统计范围的不同生成不同的给付数
		if (mSortType.equals("1"))// 统计层面选项（1-赔案、2-保项）
		{
			tAgreeCount = " select count(1) from " + tStatArouUw
					+ " and exists (select 'Y' from llclaimdetail "
					+ " where clmno = llclaim.clmno "
					+ " and substr(getdutykind, 2, 2) = '09' "
					+ " and givetype = '0') " + tApplTypeClaim
					// + tClmState
					+ " and " + tStatArouTi + mTime + tMgn_Operator
					+ strRiskChanceCount;
		} else// 统计层面选项（1-赔案、2-保项）
		{
			tAgreeCount = " select count(1) from llclaimdetail where 1 = 1 "
					+ " and exists (select 'Y' from " + tStatArouUw + " and "
					+ tStatArouTi
					+ mTime
					+ tMgn_Operator
					// + tClmState
					+ " and " + tStatArouCo + " = llclaimdetail.clmno) "
					+ tApplTypeDetail
					+ " and substr(getdutykind, 2, 2) = '09' "
					+ " and givetype = '0' " + strRiskChanceCount;
		}
		// 给付金额
		tAgreePay = " select (case when sum(b.standprem*b.exemptperiod) is null then 0 else sum(b.standprem*b.exemptperiod) end) from llclaimdetail a, llexempt b where 1 = 1 "
				+ " and a.clmno = b.clmno "
				+ " and exists (select 'X' from "
				+ tStatArouUw + " and " + tStatArouTi + mTime + tMgn_Operator
				// + tClmState
				+ " and " + tStatArouCo + " = a.clmno) " + tApplTypeDetaila
				// 需要剔除申诉案件对应的申诉赔付金额
				// + " and not exists(select 'X' from llappeal where
				// appealno=llclaimdetail.clmno)"
				+ " and givetype = '0' " + tGetDutyKind + strRiskChanceMoneya;
		// 根据选择的统计范围的不同生成不同的拒付数
		if (mSortType.equals("1"))// 统计层面选项（1-赔案、2-保项）
		{
			tRefuseCount = " select count(1) from llclaim " + tStatArouUw
					+ " and exists (select 'Y' from llclaimdetail "
					+ " where clmno = llclaim.clmno "
					+ " and substr(getdutykind, 2, 2) = '09' "
					+ " and givetype = '1') " + tApplTypeClaim
					// + tClmState
					+ " and " + tStatArouTi + mTime + tMgn_Operator
					+ strRiskChanceCount;
		} else// 统计层面选项（1-赔案、2-保项）
		{
			tRefuseCount = " select count(1) from llclaimdetail where 1 = 1 "
					+ " and exists (select 'Y' from " + tStatArouUw + " and "
					+ tStatArouTi
					+ mTime
					+ tMgn_Operator
					// + tClmState
					+ " and " + tStatArouCo + " = llclaimdetail.clmno) "
					+ tApplTypeDetail
					+ " and substr(getdutykind, 2, 2) = '09' "
					+ " and givetype = '1' " + strRiskChanceCount;
		}
		// 拒付金额
		tRefuseAmount = " select (case when sum(realpay) is null then  0 else sum(realpay) end) from llclaimdetail where 1 = 1 "
				+ " and exists (select 'Z' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				// + tClmState
				+ " and "
				+ tStatArouCo
				+ " = llclaimdetail.clmno) "
				+ tApplTypeDetail
				+ " and substr(getdutykind, 2, 2) = '09' "
				+ " and givetype = '1' " + strRiskChanceMoney;
		// 生成返回的字符串
		// tResult = " ,( "+ tAgreeCount + " ),nvl(( " + tAgreePay + " ),0),( "
		// + tRefuseCount + " ),( " + tRefuseAmount + " ) " ;
		switch (Integer.parseInt(returnNo)) {
		case 1:
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tAgreeCount);
			sqlbv1.put("level",mLevel);
			sqlbv1.put("mnguser",mMng_User);
			sqlbv1.put("riskcode",mRiskCode);
			sqlbv1.put("startdate",mStartDate);
			sqlbv1.put("enddate",mEndDate);
			tResult = sqlbv1;
			break;
		case 2:
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tAgreePay);
			sqlbv2.put("level",mLevel);
			sqlbv2.put("mnguser",mMng_User);
			sqlbv2.put("riskcode",mRiskCode);
			sqlbv2.put("startdate",mStartDate);
			sqlbv2.put("enddate",mEndDate);
			tResult = sqlbv2;
			break;
		case 3:
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tRefuseCount);
			sqlbv3.put("level",mLevel);
			sqlbv3.put("mnguser",mMng_User);
			sqlbv3.put("riskcode",mRiskCode);
			sqlbv3.put("startdate",mStartDate);
			sqlbv3.put("enddate",mEndDate);
			tResult = sqlbv3;
			break;
		case 4:
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tRefuseAmount);
			sqlbv4.put("level",mLevel);
			sqlbv4.put("mnguser",mMng_User);
			sqlbv4.put("riskcode",mRiskCode);
			sqlbv4.put("startdate",mStartDate);
			sqlbv4.put("enddate",mEndDate);
			tResult = sqlbv4;
			break;
		default:
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("");
			tResult = sqlbv;
			break;
		}
		return (tResult);
	}

	/**
	 * 功能: 得到查询用的字符串
	 * 
	 * @param 1
	 *            tOperator 死亡 02 高残 03 重疾 04 医疗 00 伤残 01 特种疾病 05 失业失能 06
	 * @return String
	 */
	public SQLwithBindVariables getCalSql(String tOperator, String returnNo) {
		String tAgreeCount;
		String tAgreePay;
		String tRefuseCount;
		String tRefuseAmount;
		String tGetDutyKind;
		String tMgn_Operator;
		SQLwithBindVariables tResult;
		// String tClmState;
		String tAgreeAppealPay;

		// 生成getdutykind的子字符串
		tGetDutyKind = " and substr(getdutykind, 2, 2) = '" + "?operator?" + "' ";
		// 生成ClmState的子字符串
		// tClmState = " and llclaim.clmstate = '60' ";
		// 生成机构或操作员的子字符串
		String tStatArouMngcom = mStatArou.equals("1") ? " llclaimuwmain.mngcom "
				: " llclaim.mngcom ";
		String tStatArouOperat = mStatArou.equals("1") ? " llclaimuwmain.operator "
				: " llclaim.operator ";
		if (!mLevel.equals("5")) {
			tMgn_Operator = " and substr(" + tStatArouMngcom + ", 1," + "?level?"
					+ "*2) = '" + "?mnguser?" + "'";
		} else {
			tMgn_Operator = " and " + tStatArouOperat + " = '" + "?mnguser?"
					+ "'";
		}
		// 险种选项条件判断取用哪个字符串
		String strClaimOrDetail = mSortType.equals("1") ? " llclaim.clmno "
				: " llclaimdetail.clmno ";
		// String strClaimOrDetail_clmno = mSortType.equals("1") ? "
		// llclaim.clmno " : " llclaimdetail.clmno " ;

		String strRiskChanceCount = mRiskChance.equals("1") ? " and exists(select 'X' from llclaimpolicy where clmno="
				+ strClaimOrDetail + " and riskcode = '" + "?riskcode?" + "') "
				: " ";
		String strRiskChanceMoney1 = mRiskChance.equals("1") ? " and exists(select 'X' from llclaimpolicy where clmno=llclaimdetail.clmno and polno=llclaimdetail.polno and riskcode = '"
				+ "?riskcode?" + "') "
				: " ";
		String strRiskChanceMoneyb = mRiskChance.equals("1") ? " and exists(select 'X' from llclaimpolicy where clmno=llbalance.clmno and polno=llbalance.polno and riskcode = '"
				+ "?riskcode?" + "') "
				: " ";
		// 统计口径条件判断取用哪个字符串
		String tStatArouUw = mStatArou.equals("1") ? " llclaim,llclaimuwmain where llclaimuwmain.clmno=llclaim.clmno "
				: " llclaim where 1 = 1 ";
		String tStatArouTi = mStatArou.equals("1") ? " llclaimuwmain.examconclusion = '0' and llclaimuwmain.examdate "
				: " llclaim.clmstate = '60' and llclaim.endcasedate ";
		String tStatArouCo = mStatArou.equals("1") ? " llclaimuwmain.clmno "
				: " llclaim.clmno ";
		// 申请类型判断
		String tApplTypeClaim = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='2') ";// llclaim
		String tApplTypeDetail = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='2') ";// llclaimdetail
		String tApplTypeBalance = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llbalance.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llbalance.clmno and llregister.rgtobj='2') ";// llbalance
		// 根据选择的统计层面的不同生成不同的给付数
		if (mSortType.equals("1"))// 统计层面选项（1-赔案、2-保项）
		{
			tAgreeCount = " select count(1) from " + tStatArouUw
					+ " and exists (select 'Y' from llclaimdetail "
					+ " where clmno = llclaim.clmno " + tGetDutyKind
					+ " and givetype = '0') " + tApplTypeClaim
					// + tClmState
					+ " and " + tStatArouTi + mTime + tMgn_Operator
					+ strRiskChanceCount;
		} else// 统计层面选项（1-赔案、2-保项）
		{
			tAgreeCount = " select count(1) from llclaimdetail where 1 = 1 "
					+ " and exists (select 'Y' from " + tStatArouUw + " and "
					+ tStatArouTi + mTime
					+ tMgn_Operator
					// + tClmState
					+ " and " + tStatArouCo + " = llclaimdetail.clmno) "
					+ tApplTypeDetail + tGetDutyKind + " and givetype = '0' "
					+ strRiskChanceMoney1;
		}
		// 给付金额
		tAgreePay = " select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaimdetail where 1 = 1 "
				+ " and exists (select 'X' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				// + tClmState
				+ " and "
				+ tStatArouCo
				+ " = llclaimdetail.clmno) "
				+ tApplTypeDetail
				+ " and givetype = '0' "
				+ tGetDutyKind
				// 需要剔除申诉案件对应的申诉赔付金额
				+ " and not exists(select 'X' from llappeal where appealno=llclaimdetail.clmno)"
				+ strRiskChanceMoney1;
		// 申诉案件对应的申诉赔付金额的差值
		tAgreeAppealPay = " select (case when sum(pay) is null then 0 else sum(pay) end) from llbalance where 1 = 1 "
				+ " and exists (select 'X' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				// + tClmState
				+ " and "
				+ tStatArouCo
				+ " = llbalance.clmno) "
				+ tApplTypeBalance
				// + " and trim(givetype) = '0' "
				+ " and substr(subfeeoperationtype, 2, 2) = '"
				+ tOperator
				+ "' "
				// + tGetDutyKind
				// 需要确认是申诉案件
				+ " and exists(select 'X' from llappeal where appealno=llbalance.clmno)"
				// 理赔的类型为赔款
				+ " and feefinatype='PK' "
				+ " and feeoperationtype='A'"
				+ strRiskChanceMoneyb;

		// 根据选择的统计范围的不同生成不同的拒付数
		if (mSortType.equals("1"))// 统计层面选项（1-赔案、2-保项）
		{
			tRefuseCount = " select count(1) from " + tStatArouUw
					+ " and exists (select 'Y' from llclaimdetail "
					+ " where clmno = llclaim.clmno " + tGetDutyKind
					+ " and givetype = '1') " + tApplTypeClaim
					// + tClmState
					+ " and " + tStatArouTi + mTime + tMgn_Operator
					+ strRiskChanceCount;
		} else// 统计层面选项（1-赔案、2-保项）
		{
			tRefuseCount = " select count(1) from llclaimdetail where 1 = 1 "
					+ " and exists (select 'Y' from " + tStatArouUw + " and "
					+ tStatArouTi + mTime
					+ tMgn_Operator
					// + tClmState
					+ " and " + tStatArouCo + " = llclaimdetail.clmno) "
					+ tApplTypeDetail + tGetDutyKind + " and givetype = '1' "
					+ strRiskChanceMoney1;
		}
		// 拒付金额
		tRefuseAmount = " select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaimdetail where 1 = 1 "
				+ " and exists (select 'Z' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				// + tClmState
				+ " and "
				+ tStatArouCo
				+ " = llclaimdetail.clmno) "
				+ tApplTypeDetail
				+ tGetDutyKind
				+ " and givetype = '1' "
				+ strRiskChanceMoney1;
		// 生成返回的字符串
		// tResult = " ,( "+ tAgreeCount + " ),( (" + tAgreePay + ") + (" +
		// tAgreeAppealPay + ") ),( " + tRefuseCount + " ),( " + tRefuseAmount +
		// " ) " ;
		switch (Integer.parseInt(returnNo)) {
		case 1:
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tAgreeCount);
			sqlbv1.put("operator",tOperator);
			sqlbv1.put("level",mLevel);
			sqlbv1.put("mnguser",mMng_User);
			sqlbv1.put("riskcode",mRiskCode);
			sqlbv1.put("startdate",mStartDate);
			sqlbv1.put("enddate",mEndDate);
			tResult = sqlbv1;
			break;
		case 2:
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select (" + tAgreePay + ") + (" + tAgreeAppealPay
					+ ") from LDSysvar where sysvar='onerow'");
			sqlbv2.sql(tAgreeCount);
			sqlbv2.put("operator",tOperator);
			sqlbv2.put("level",mLevel);
			sqlbv2.put("mnguser",mMng_User);
			sqlbv2.put("riskcode",mRiskCode);
			sqlbv2.put("startdate",mStartDate);
			sqlbv2.put("enddate",mEndDate);
			tResult = sqlbv2;
			break;
		case 3:
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tRefuseCount);
			sqlbv3.sql(tAgreeCount);
			sqlbv3.put("operator",tOperator);
			sqlbv3.put("level",mLevel);
			sqlbv3.put("mnguser",mMng_User);
			sqlbv3.put("riskcode",mRiskCode);
			sqlbv3.put("startdate",mStartDate);
			sqlbv3.put("enddate",mEndDate);
			tResult = sqlbv3;
			break;
		case 4:
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tRefuseAmount);
			sqlbv4.sql(tAgreeCount);
			sqlbv4.put("operator",tOperator);
			sqlbv4.put("level",mLevel);
			sqlbv4.put("mnguser",mMng_User);
			sqlbv4.put("riskcode",mRiskCode);
			sqlbv4.put("startdate",mStartDate);
			sqlbv4.put("enddate",mEndDate);
			tResult = sqlbv4;
			break;
		default:
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("");
			tResult = sqlbv;
			break;
		}

		return (tResult);
	}

	private SQLwithBindVariables getPANoRisk_09(String returnNo)// 赔案层面分险种统计时汇总求和的SQL
	{
		String tAgreeCount;
		String tAgreePay;
		String tRefuseCount;
		String tRefuseAmount;
		String tGetDutyKind;
		String tMgn_Operator;
		SQLwithBindVariables tResult;

		// 生成getdutykind的子字符串
		tGetDutyKind = " and substr(a.getdutykind, 2, 2) = '09' ";
		// 统计口径条件判断取用哪个字符串
		String tStatArouMngcom = mStatArou.equals("1") ? " llclaimuwmain.mngcom "
				: " llclaim.mngcom ";
		String tStatArouOperat = mStatArou.equals("1") ? " llclaimuwmain.operator "
				: " llclaim.operator ";
		// 生成机构或操作员的子字符串
		// if (!mLevel.equals("5"))
		// {
		// tMgn_Operator = " and substr(" +tStatArouMngcom+ ", 1,"+ mLevel +
		// "*2) = " + mMng_User ;
		// }
		// else
		// {
		// tMgn_Operator = " and " +tStatArouOperat+ "=" + mMng_User ;//
		// }
		tMgn_Operator = " and " + tStatArouMngcom + " like concat('" + "?mngcom?"
				+ "','%')";

		// 统计口径条件判断取用哪个字符串
		String tStatArouUw = mStatArou.equals("1") ? " llclaim,llclaimuwmain where llclaimuwmain.clmno=llclaim.clmno "
				: " llclaim where 1 = 1 ";
		String tStatArouTi = mStatArou.equals("1") ? " llclaimuwmain.examconclusion = '0' and llclaimuwmain.examdate "
				: " llclaim.clmstate = '60' and llclaim.endcasedate ";
		String tStatArouCo = mStatArou.equals("1") ? " llclaimuwmain.clmno "
				: " llclaim.clmno ";
		// 申请类型判断
		String tApplTypeClaim = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='2') ";// llclaim
		String tApplTypeDetail = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='2') ";// llclaimdetail
		String tApplTypeDetaila = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=a.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=a.clmno and llregister.rgtobj='2') ";// llclaimdetail
																														// a
		// 根据选择的统计范围的不同生成不同的给付数
		tAgreeCount = " select count(1) from " + tStatArouUw
				+ " and exists (select 'Y' from llclaimdetail "
				+ " where clmno = llclaim.clmno "
				+ " and substr(getdutykind, 2, 2) = '09' "
				+ " and givetype = '0') " + tApplTypeClaim + " and "
				+ tStatArouTi + mTime + tMgn_Operator;
		// 给付金额
		tAgreePay = " select (case when sum(b.standprem*b.exemptperiod) is null then 0 else sum(b.standprem*b.exemptperiod) end) from llclaimdetail a, llexempt b where 1 = 1 "
				+ " and a.clmno = b.clmno "
				+ " and exists (select 'X' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				+ " and " + tStatArouCo + " = a.clmno) " + tApplTypeDetaila
				// 需要剔除申诉案件对应的申诉赔付金额
				// + " and not exists(select 'X' from llappeal where
				// appealno=llclaimdetail.clmno)"
				+ " and givetype = '0' " + tGetDutyKind;
		// 根据选择的统计范围的不同生成不同的拒付数
		tRefuseCount = " select count(1) from llclaim " + tStatArouUw
				+ " and exists (select 'Y' from llclaimdetail "
				+ " where clmno = llclaim.clmno "
				+ " and substr(getdutykind, 2, 2) = '09' "
				+ " and givetype = '1') " + tApplTypeClaim + " and "
				+ tStatArouTi + mTime + tMgn_Operator;
		// 拒付金额
		tRefuseAmount = " select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaimdetail where 1 = 1 "
				+ " and exists (select 'Z' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				+ " and "
				+ tStatArouCo
				+ " = llclaimdetail.clmno) "
				+ tApplTypeDetail
				+ " and substr(getdutykind, 2, 2) = '09' "
				+ " and givetype = '1' ";
		// 生成返回的字符串
		// tResult = " ,( "+ tAgreeCount + " ),nvl(( " + tAgreePay + " ),0),( "
		// + tRefuseCount + " ),( " + tRefuseAmount + " ) " ;
		switch (Integer.parseInt(returnNo)) {
		case 1:
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tAgreeCount);
			sqlbv1.put("mngcom",mManageCom);
			sqlbv1.put("startdate",mStartDate);
			sqlbv1.put("enddate",mEndDate);
			tResult = sqlbv1;
			break;
		case 2:
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql(tAgreePay);
			sqlbv2.put("mngcom",mManageCom);
			sqlbv2.put("startdate",mStartDate);
			sqlbv2.put("enddate",mEndDate);
			tResult = sqlbv2;
			break;
		case 3:
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tRefuseAmount);
			sqlbv3.put("mngcom",mManageCom);
			sqlbv3.put("startdate",mStartDate);
			sqlbv3.put("enddate",mEndDate);
			tResult = sqlbv3;
			break;
		case 4:
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tRefuseAmount);
			sqlbv4.put("mngcom",mManageCom);
			sqlbv4.put("startdate",mStartDate);
			sqlbv4.put("enddate",mEndDate);
			tResult = sqlbv4;
			break;
		default:
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("");
			tResult = sqlbv;
			break;
		}
		return (tResult);
	}

	public SQLwithBindVariables getPANoRisk(String tOperator, String returnNo)// 赔案层面分险种统计时汇总求和的SQL
	{
		String tAgreeCount;
		String tAgreePay;
		String tRefuseCount;
		String tRefuseAmount;
		String tGetDutyKind;
		String tMgn_Operator;
		SQLwithBindVariables tResult;
		String tAgreeAppealPay;

		// 生成getdutykind的子字符串
		tGetDutyKind = " and substr(getdutykind, 2, 2) = '" + "?operator?" + "' ";
		// 生成机构或操作员的子字符串
		String tStatArouMngcom = mStatArou.equals("1") ? " llclaimuwmain.mngcom "
				: " llclaim.mngcom ";
		String tStatArouOperat = mStatArou.equals("1") ? " llclaimuwmain.operator "
				: " llclaim.operator ";
		// if (!mLevel.equals("5"))
		// {
		// tMgn_Operator = " and substr(" +tStatArouMngcom+ ", 1,"+ mLevel +
		// "*2) = " + mMng_User ;
		// }
		// else
		// {
		// tMgn_Operator = " and " +tStatArouOperat+ "=" + mMng_User ;//
		// }
		tMgn_Operator = " and " + tStatArouMngcom + " like concat('" + "?mngcom?"
				+ "','%') ";

		// 统计口径条件判断取用哪个字符串
		String tStatArouUw = mStatArou.equals("1") ? " llclaim,llclaimuwmain where llclaimuwmain.clmno=llclaim.clmno "
				: " llclaim where 1 = 1 ";
		String tStatArouTi = mStatArou.equals("1") ? " llclaimuwmain.examconclusion = '0' and llclaimuwmain.examdate "
				: " llclaim.clmstate = '60' and llclaim.endcasedate ";
		String tStatArouCo = mStatArou.equals("1") ? " llclaimuwmain.clmno "
				: " llclaim.clmno ";
		// 申请类型判断
		String tApplTypeClaim = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaim.clmno and llregister.rgtobj='2') ";// llclaim
		String tApplTypeDetail = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llclaimdetail.clmno and llregister.rgtobj='2') ";// llclaimdetail
		String tApplTypeBalance = mNCLType.trim().equals("1") ? " and exists (select 'X' from llregister where llregister.rgtno=llbalance.clmno and llregister.rgtobj='1') "
				: " and exists (select 'X' from llregister where llregister.rgtno=llbalance.clmno and llregister.rgtobj='2') ";// llbalance
		// 根据选择的统计层面的不同生成不同的给付数
		tAgreeCount = " select count(1) from " + tStatArouUw
				+ " and exists (select 'Y' from llclaimdetail "
				+ " where clmno = llclaim.clmno " + tGetDutyKind
				+ " and givetype = '0') " + tApplTypeClaim + " and "
				+ tStatArouTi + mTime + tMgn_Operator;
		// 给付金额
		tAgreePay = " select (case when sum(realpay) is null then 0 else sum(realpay) end) from llclaimdetail where 1 = 1 "
				+ " and exists (select 'X' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				+ " and "
				+ tStatArouCo
				+ " = llclaimdetail.clmno) "
				+ tApplTypeDetail
				+ " and givetype = '0' "
				+ tGetDutyKind
				// 需要剔除申诉案件对应的申诉赔付金额
				+ " and not exists(select 'X' from llappeal where appealno=llclaimdetail.clmno)";
		// 申诉案件对应的申诉赔付金额的差值
		tAgreeAppealPay = " select (case when sum(pay) is null then 0 else sum(pay) end) from llbalance where 1 = 1 "
				+ " and exists (select 'X' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				+ " and "
				+ tStatArouCo
				+ " = llbalance.clmno) "
				+ tApplTypeBalance
				// + " and trim(givetype) = '0' "
				+ " and substr(subfeeoperationtype, 2, 2) = '"
				+ "?operator?"
				+ "' "
				// + tGetDutyKind
				// 需要确认是申诉案件
				+ " and exists(select 'X' from llappeal where appealno=llbalance.clmno)"
				// 理赔的类型为赔款
				+ " and feefinatype='PK' " + " and feeoperationtype='A'";

		// 根据选择的统计范围的不同生成不同的拒付数
		tRefuseCount = " select count(1) from " + tStatArouUw
				+ " and exists (select 'Y' from llclaimdetail "
				+ " where clmno = llclaim.clmno " + tGetDutyKind
				+ " and givetype = '1') " + tApplTypeClaim + " and "
				+ tStatArouTi + mTime + tMgn_Operator;
		// 拒付金额
		tRefuseAmount = " select (case when sum(realpay) is nul then 0 else sum(realpay) end) from llclaimdetail where 1 = 1 "
				+ " and exists (select 'Z' from "
				+ tStatArouUw
				+ " and "
				+ tStatArouTi
				+ mTime
				+ tMgn_Operator
				+ " and "
				+ tStatArouCo
				+ " = llclaimdetail.clmno) "
				+ tApplTypeDetail
				+ tGetDutyKind
				+ " and givetype = '1' ";
		// 生成返回的字符串
		// tResult = " ,( "+ tAgreeCount + " ),( (" + tAgreePay + ") + (" +
		// tAgreeAppealPay + ") ),( " + tRefuseCount + " ),( " + tRefuseAmount +
		// " ) " ;
		switch (Integer.parseInt(returnNo)) {
		case 1:
			SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
			sqlbv1.sql(tAgreeCount);
			sqlbv1.put("operator", tOperator);
			sqlbv1.put("mngcom",mManageCom);
			sqlbv1.put("startdate",mStartDate);
			sqlbv1.put("enddate",mEndDate);
			tResult = sqlbv1;
			break;
		case 2:
			SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
			sqlbv2.sql("select (" + tAgreePay + ") + (" + tAgreeAppealPay
					+ ") from LDSysvar where sysvar='onerow'");
			sqlbv2.put("operator", tOperator);
			sqlbv2.put("mngcom",mManageCom);
			sqlbv2.put("startdate",mStartDate);
			sqlbv2.put("enddate",mEndDate);
			tResult = sqlbv2;
			break;
		case 3:
			SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
			sqlbv3.sql(tRefuseCount);
			sqlbv3.put("operator", tOperator);
			sqlbv3.put("mngcom",mManageCom);
			sqlbv3.put("startdate",mStartDate);
			sqlbv3.put("enddate",mEndDate);
			tResult = sqlbv3;
			break;
		case 4:
			SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
			sqlbv4.sql(tRefuseAmount);
			sqlbv4.put("operator", tOperator);
			sqlbv4.put("mngcom",mManageCom);
			sqlbv4.put("startdate",mStartDate);
			sqlbv4.put("enddate",mEndDate);
			tResult = sqlbv4;
			break;
		default:
			SQLwithBindVariables sqlbv = new SQLwithBindVariables();
			sqlbv.sql("");
			tResult = sqlbv;
			break;
		}
		return (tResult);
	}

	/**
	 * 返回错误结果集
	 * 
	 * @return CErrors
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 返回结果集
	 * 
	 * @return Vdate
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 主函数,测试用
	 */
	public static void main(String[] args) {
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";
		tG.ComCode = "86";
		TransferData tTransferData = new TransferData();

		tTransferData.setNameAndValue("StartDate", "2006-03-26");
		tTransferData.setNameAndValue("EndDate", "2006-04-07");
		tTransferData.setNameAndValue("Level", "5");
		tTransferData.setNameAndValue("ManageCom", "86210000");
		tTransferData.setNameAndValue("SortType", "1");// 统计层面
		tTransferData.setNameAndValue("StatArou", "1");// 统计口径
		tTransferData.setNameAndValue("RiskChance", "1");// 险种选项
		tTransferData.setNameAndValue("NCLType", "1");// 申请类型

		VData tVData = new VData();
		tVData.add(tG);
		tVData.add(tTransferData);
		LLPRRClaimTypePayBL tLLPRRClaimTypePayBL = new LLPRRClaimTypePayBL();
		if (tLLPRRClaimTypePayBL.submitData(tVData, "") == false) {
			logger.debug("-------false-------");
		}
		int n = tLLPRRClaimTypePayBL.mErrors.getErrorCount();
		for (int i = 0; i < n; i++) {
			String Content = "";
			Content = Content + "原因是: "
					+ tLLPRRClaimTypePayBL.mErrors.getError(i).errorMessage;
			logger.debug(Content);
		}
	}

	private void jbInit() throws Exception {
	}
}
