package com.sinosoft.lis.bq;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGetDB;
import com.sinosoft.lis.db.LPPolDB;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LCContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPPolSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;

/**
 * <p>
 * Title: 减额缴清变更(PU)批单数据生成
 * </p>
 * 
 * <p>
 * Description: 数据生成
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * </p>
 * 
 * @author wangyiyan
 * @version 1.0
 */

public class PEdorPUPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorPUPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	// public CErrors mErrors = new CErrors();

	// 全局变量
	private String mOperate;

	private VData mInputData = new VData();

	@SuppressWarnings("unused")
	private GlobalInput mGlobalInput = new GlobalInput();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable tPUListTable = new ListTable();

	public PEdorPUPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
		logger.debug("Start preparing the data to print ====>" + mOperate);

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("PU数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("PU数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("准备数据失败!");
			return false;
		}

		logger.debug("End preparing the data to print ====>" + mOperate);
		return true;
	}

	private boolean getInputData() {
		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		xmlexport = (XmlExportNew) mInputData
				.getObjectByObjectName("XmlExportNew", 0);
		if (mLPEdorItemSchema == null || xmlexport == null) {
			return false;
		}
		return true;
	}

	private boolean checkData() {
		if (!mOperate.equals("PRINTPU")) {
			return false;
		}
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
		tLPEdorItemDB.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemDB.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet.size() < 1) {
			return false;
		}
		mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1));
		return true;
	}

	private boolean dealData() {
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayPU");
		xmlexport.addDisplayControl("displayTail1");
		tPUListTable.setName("PU");
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号
		// 在LPPol表查询需要打印的险种记录，包括主险和长期附加险。
		// 增加减额缴清打印主险和长期附加险2005-12-12添加，开始
		LPPolDB tLPPolDB = new LPPolDB();
		LPPolSchema tLPPolSchema = new LPPolSchema();
		tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
		// 2005-12-12注tLPPolDB.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
		LPPolSet tLPPolSet = new LPPolSet();
		tLPPolSet = tLPPolDB.query();
		if (tLPPolSet == null || tLPPolSet.size() < 1) {
			mErrors.addOneError("打印生成数据时，没有该保全批单信息!");
			return false;
		}
		// 取每一条保单批改记录
		ExeSQL tExeSQL = new ExeSQL();
		int i = 1;
		for (i = 1; i <= tLPPolSet.size(); i++) {
			tLPPolSchema = tLPPolDB.getSchema();
			tLPPolSchema = tLPPolSet.get(i);
			// 判断是否是主险
			if (tLPPolSchema.getPolNo().equals(tLPPolSchema.getMainPolNo())) {
				// 批单数据
				// 原保险金额，在LC*表取数
				xmlexport.addDisplayControl("displayPU_mainpolno");
				LCPolDB tLCPolDB = new LCPolDB();
				LCPolSchema tLCPolSchema = new LCPolSchema();
				tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
				if (!tLCPolDB.getInfo()) {
					mErrors.addOneError("打印生成数据时，取原保单信息失败!");
					return false;
				}
				tLCPolSchema = tLCPolDB.getSchema();
				String lCPolAmnt = String.valueOf(tLCPolSchema.getAmnt());
				if (tLCPolSchema.getAmnt() < 0) {
					lCPolAmnt = lCPolAmnt.substring(1, lCPolAmnt.length());
				}
				mTextTag.add("LCPol.Amnt", BqNameFun.getRound(lCPolAmnt));
				// 减额缴清后的保单数据，在LP*表取数
				// LPPolDB tLPPolDB = new LPPolDB();
				// LPPolSchema tLPPolSchema = new LPPolSchema();
				// tLPPolDB.setEdorNo (mLPEdorItemSchema.getEdorNo ()) ;
				// tLPPolDB.setPolNo (mLPEdorItemSchema.getPolNo ()) ;
				// tLPPolDB.setEdorType (mLPEdorItemSchema.getEdorType ()) ;
				// if (!tLPPolDB.getInfo ())
				// {
				// mErrors.addOneError ("打印生成数据时，取新保单信息失败!") ;
				// return false ;
				// }
				// tLPPolSchema = tLPPolDB.getSchema () ;
				// 2005-12-12修改QC5067BUG 开始，解决618转145取保额错误的问题
				// 取险种的销售方式：保额='1'，份数<>'1'
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql("select vpu from lmduty where dutycode in (select dutycode from lmriskduty where riskcode='?riskcode?')");
				sqlbv.put("riskcode", tLPPolSchema.getRiskCode());
				String tVPU = tExeSQL
						.getOneValue(sqlbv);
				if ("1".equals(tVPU)) {
					mTextTag.add("LPPol.Amnt", getRound(tLPPolSchema.getMult())
							+ "份"); // 缴清后新的保险份数
				} else {
					mTextTag.add("LPPol.Amnt", BqNameFun.getRound(tLPPolSchema
							.getAmnt())); // 缴清后新的保险金额
				}
				// 2005-12-12修改QC5067BUG 结束，解决618转145取保额错误的问题
				// 判断是否是分红险,打印减额缴清后的新险种名称
				SSRS tSSRS = new SSRS();

				String tSql = "SELECT case"
						+ " when exists(select 'X' from LMRiskApp where RiskCode=a.RiskCode and BonusFlag in ('1','2')) then '1'"
						+ " else '0'" + " end" + " FROM LCPol a"
						+ " WHERE PolNo='?PolNo?'";
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(tSql);
				sbv1.put("PolNo", tLPPolSchema.getPolNo());
				tSSRS = tExeSQL.execSQL(sbv1);
				if (!tSSRS.GetText(1, 1).equals("0")) {
					// 是分红险
					LMRiskDB tLMRiskDB = new LMRiskDB();
					// LMRiskSchema tLMRiskSchema = new LMRiskSchema () ;
					tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
					if (!tLMRiskDB.getInfo()) {
						mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
						return false;
					}
					// mTextTag.add ("RiskName","缴清后险种：") ; //缴清后险种（分红险输出，否则不输出）
					xmlexport.addDisplayControl("displayPU_mainpolno1");
					mTextTag.add("LMRisk.RiskName", tLMRiskDB
							.getRiskShortName());
				}

				// 判断是否是607、614、615险种,打印保险期间
				if ((tLCPolSchema.getRiskCode().equals("00607000"))
						|| (tLCPolSchema.getRiskCode().equals("00614000"))
						|| (tLCPolSchema.getRiskCode().equals("00615000"))) {
					// mTextTag.add ("PolDate","保险期间：") ;
					xmlexport.addDisplayControl("displayPU_mainpolno2");
					SQLwithBindVariables sbv2=new SQLwithBindVariables();
					sbv2.sql("SELECT (EndDate-1) From LPPol where PolNo = '?PolNo?' And EdorNo = '?EdorNo?' And EdorType = '?EdorType?'");
					sbv2.put("PolNo", tLPPolSchema.getPolNo());
					sbv2.put("EdorNo", tLPPolSchema.getEdorNo());
					sbv2.put("EdorType", tLPPolSchema.getEdorType());
					String mEndDate = tExeSQL
							.getOneValue(sbv2);
					mTextTag.add("LPPolDate", tLPPolSchema.getCValiDate() + "至"
							+ BqNameFun.delTime(mEndDate));
				}

				// 判断是否是618险种,打印领取方式
				if (tLCPolSchema.getRiskCode().equals("00618000")) {
					LPGetDB tLPGetDB = new LPGetDB();
					tLPGetDB.setEdorNo(tLPPolSchema.getEdorNo());
					tLPGetDB.setEdorType(tLPPolSchema.getEdorType());
					tLPGetDB.setPolNo(tLPPolSchema.getPolNo());
					// 2005-12-12修改开始，解决618转145取领取方式错误的问题
					tLPGetDB.setGetDutyCode("145041");
					// 2005-12-12修改结束，解决618转145取领取方式错误的问题
					LPGetSet iLPGetSet = new LPGetSet();
					iLPGetSet = tLPGetDB.query();
					if (iLPGetSet == null || iLPGetSet.size() < 1) {
						mErrors.addOneError("打印生成数据时，查询领取项信息失败!");
						return false;
					}
					// 2005-12-12修改开始，解决618转145取领取方式错误的问题
					CodeNameQuery tCodeNameQuery = new CodeNameQuery();
					xmlexport.addDisplayControl("displayPU_mainpolno3");
					mTextTag.add("GetIntv", tCodeNameQuery.getName("getintv",
							String.valueOf(iLPGetSet.get(1).getGetIntv())));
					// 2005-12-12修改结束，解决618转145取领取方式错误的问题
				}
			} else { // 判断是否是长期附加险
				SQLwithBindVariables sbv3=new SQLwithBindVariables();
				sbv3.sql("select riskcode from lmriskapp where subriskflag='S' and riskprop<>'G' and riskcode "
						+ "in (select riskcode from lmriskduty where dutycode in (select dutycode from lmduty "
						+ "where insuyear>1 or insuyear is null)) And riskcode='?riskcode?'");
				sbv3.put("riskcode", tLPPolSchema.getRiskCode());
				String tSubriskcode = tExeSQL
						.getOneValue(sbv3);
				if (tSubriskcode != null && tSubriskcode != "") {
					String[] strPU = new String[2];
					// 原保险金额，在LC*表取数
					xmlexport.addDisplayControl("displayPU_subpolno");
					LCPolDB tLCPolDB = new LCPolDB();
					LCPolSchema tLCPolSchema = new LCPolSchema();
					tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
					if (!tLCPolDB.getInfo()) {
						mErrors.addOneError("打印生成数据时，取原保单信息失败!");
						return false;
					}
					tLCPolSchema = tLCPolDB.getSchema();
					String lCPolAmnt = String.valueOf(tLCPolSchema.getAmnt());
					if (tLCPolSchema.getAmnt() < 0) {
						lCPolAmnt = lCPolAmnt.substring(1, lCPolAmnt.length());
					}
					strPU[0] = "原保险金额：";
					strPU[1] = BqNameFun.getRound(lCPolAmnt);
					tPUListTable.add(strPU);
					// mTextTag.add ("LCPol.Amnt",BqNameFun.getRound
					// (lCPolAmnt)) ;
					// 取险种的销售方式：保额='1'，份数<>'1'
					SQLwithBindVariables sbv4=new SQLwithBindVariables();
					sbv4.sql("select vpu from lmduty where dutycode in (select dutycode from lmriskduty where riskcode='?riskcode?')");
					sbv4.put("riskcode", tLPPolSchema.getRiskCode());
					String tVPU = tExeSQL
							.getOneValue(sbv4);
					if ("1".equals(tVPU)) {
						// mTextTag.add ("LPPol.Amnt",BqNameFun.getRound
						// (tLPPolSchema.getMult ())) ; //缴清后新的保险份数
						strPU[0] = "缴清后保险金额：";
						strPU[1] = getRound(tLPPolSchema.getMult()) + "份";
						tPUListTable.add(strPU);
					} else {
						// mTextTag.add ("LPPol.Amnt",BqNameFun.getRound
						// (tLPPolSchema.getAmnt ())) ; //缴清后新的保险金额
						strPU[0] = "缴清后保险金额：";
						strPU[1] = BqNameFun.getRound(tLPPolSchema.getMult());
						tPUListTable.add(strPU);
					}
					LMRiskDB tLMRiskDB = new LMRiskDB();
					tLMRiskDB.setRiskCode(tLPPolSchema.getRiskCode());
					if (!tLMRiskDB.getInfo()) {
						mErrors.addOneError("打印生成数据时，取险种名称信息失败!");
						return false;
					}
					strPU[0] = "缴清后险种：";
					strPU[1] = tLMRiskDB.getRiskShortName();
					tPUListTable.add(strPU);
					strPU[0] = "";
					strPU[1] = "";
					tPUListTable.add(strPU);
					// mTextTag.add
					// ("LMRisk.RiskName",tLMRiskDB.getRiskShortName ()) ;
				}
			}
		}
		// displayTail1信息
		String[] b_strEN = new String[2];

		xmlexport.addListTable(tPUListTable, b_strEN);

		mTextTag.add("LPEdor.ValiDate", mLPEdorItemSchema.getEdorValiDate());
		mTextTag.add("Operator", CodeNameQuery.getOperator(mLPEdorItemSchema
				.getOperator()));
		mTextTag.add("ApproveOperator", CodeNameQuery
				.getOperator(mLPEdorItemSchema.getApproveOperator()));
		String tAgentCode = tLCContSchema.getAgentCode();

		String[] allArr = BqNameFun.getAllNames(tAgentCode);
		/*
		 * String tDistrict = allArr[BqNameFun.DISTRICT]; //区 String tDepart =
		 * allArr[BqNameFun.DEPART]; //部 String tTeam = allArr[BqNameFun.TEAM];
		 * //组 String tSaleService = allArr[BqNameFun.SALE_SERVICE]; //营销服务部
		 * String tCom = allArr[BqNameFun.COM]; //*分公司*中心支公司 String tAdress =
		 * allArr[BqNameFun.ADDRESS]; //公司地址 String tZipCode =
		 * allArr[BqNameFun.ZIPCODE]; //公司邮编 String tPhone =
		 * allArr[BqNameFun.PHONE]; //公司电话 String tAgentName =
		 * allArr[BqNameFun.AGENT_NAME]; //代理人姓名 //
		 * 上面tCom中是分公司和中支合在一起的，如果需要分别显示分公司和中支，则可以取下两个数，但不推荐使用： String tFilial =
		 * allArr[BqNameFun.FILIAL]; //*分公司 String tBranch =
		 * allArr[BqNameFun.CENTER_BRANCH]; //*中心支公司
		 */
		mTextTag.add("ManageCom", allArr[BqNameFun.SALE_SERVICE]);
		mTextTag.add("LABranchGroup.Name", allArr[BqNameFun.DEPART]);
		mTextTag.add("LAAgent.Name", allArr[BqNameFun.AGENT_NAME]);
		mTextTag.add("LAAgent.AgentCode", tLCContSchema.getAgentCode());
		return true;
	}

	private static String getRound(double tValue) {
		String t = "0.00000";
		DecimalFormat tDF = new DecimalFormat(t);
		return tDF.format(tValue);
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成打印数据失败!");
			return false;
		}
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
