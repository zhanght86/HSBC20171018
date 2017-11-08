package com.sinosoft.lis.bq;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.*;

public class PEdorPAPrintBL implements EdorPrint {
	private static Logger logger = Logger.getLogger(PEdorPAPrintBL.class);

	// 公共数据
	private VData mResult = new VData();

	public CErrors mErrors = new CErrors();

	String CurrentDate = PubFun.getCurrentDate();

	// 全局变量
	private VData mInputData = new VData();

	private XmlExportNew xmlexport = new XmlExportNew();

	private TextTag mTextTag = new TextTag();

	private ListTable mListTableBef = new ListTable();

	private ListTable mListTableAft = new ListTable();

	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();

	private String[] strNSSBef;

	private String[] strNSSAft;

	public PEdorPAPrintBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

		// 数据传输
		if (!getInputData()) {
			mErrors.addOneError("PA数据传输失败!");
			return false;
		}

		// 数据校验
		if (!checkData()) {
			mErrors.addOneError("PA传入数据无效!");
			return false;
		}

		// 打印数据处理
		if (!dealData()) {
			mErrors.addOneError("PA数据打印失败!");
			return false;
		}

		// 准备数据
		if (!prepareData()) {
			mErrors.addOneError("PA准备数据失败!");
			return false;
		}

		return true;
	}

	private boolean getInputData() {

		mLPEdorItemSchema = (LPEdorItemSchema) mInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mLPEdorAppSchema = (LPEdorAppSchema) mInputData.getObjectByObjectName(
				"LPEdorAppSchema", 0);
		xmlexport = (XmlExportNew) mInputData.getObjectByObjectName(
				"XmlExportNew", 0);
		if (mLPEdorItemSchema == null) {
			mErrors.addOneError("PA得到数据失败!");
			return false;
		}

		return true;
	}

	private boolean checkData() {
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

		// xmlexport.createDocument("PrtEndorsement.vts", "IGPrinter");

		/** 要显示的部分 */
		xmlexport.addDisplayControl("displayHead1");
		xmlexport.addDisplayControl("displayTail1");
		BqNameFun.AddEdorHead(mLPEdorItemSchema, mLPEdorAppSchema, xmlexport);
		LCContDB tLCContDB = new LCContDB();
		LCContSchema tLCContSchema = new LCContSchema();
		tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		if (!tLCContDB.getInfo()) {
			mErrors.addOneError("打印生成数据时，取保单信息失败!");
			return false;
		}
		tLCContSchema = tLCContDB.getSchema();

		/** 批单头部 */
		mTextTag.add("AppntName", tLCContSchema.getAppntName()); // 原投保人姓名
		mTextTag.add("InsuredName", tLCContSchema.getInsuredName()); // 被保人姓名
		mTextTag.add("EdorAcceptNo", mLPEdorItemSchema.getEdorAcceptNo()); // 批单号
		mTextTag.add("EdorNo", mLPEdorItemSchema.getEdorNo()); // 批单号
		mTextTag.add("ContNo", mLPEdorItemSchema.getContNo()); // 保单号

		String strSQLAft = "select ContNo, PayPlanCode,(select distinct c.payplanname from lmriskaccpay c where c.payplancode=a.PayPlanCode),InsuAccNo,(select b.insuaccname from lmriskinsuacc b where trim(b.insuaccno)=trim(a.InsuAccNo)),InputMode,InvestRate,InvestMoney from LPPerInvestPlan a where  a.EdorNo='?EdorNo?' order by PayPlanCode,InsuAccNo asc";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strSQLAft);
		sqlbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		SSRS ttSSRS = new SSRS();
		ExeSQL ttExeSQL = new ExeSQL();
		ttSSRS = ttExeSQL.execSQL(sqlbv);
		logger.debug(strSQLAft);
		ttSSRS = ttExeSQL.execSQL(sqlbv);
		if (ttSSRS != null || ttSSRS.MaxRow > 0) {
			for (int j = 1; j <= ttSSRS.MaxRow; j++) {
				mListTableAft.setName("PAAft");
				strNSSAft = new String[9];
				strNSSAft[0] = ttSSRS.GetText(j, 1);
				strNSSAft[1] = ttSSRS.GetText(j, 2);
				strNSSAft[2] = ttSSRS.GetText(j, 3);
				strNSSAft[3] = ttSSRS.GetText(j, 4);
				strNSSAft[4] = ttSSRS.GetText(j, 5);
				if (ttSSRS.GetText(j, 6).equals("1")) {
					strNSSAft[5] = "按比例";
					strNSSAft[6] = ttSSRS.GetText(j, 7);
					strNSSAft[7] = "";
				} else {
					strNSSAft[5] = "按金额";
					strNSSAft[6] = "";
					strNSSAft[7] = ttSSRS.GetText(j, 8);
				}

				// strNSSAft[8] = ttSSRS.GetText(j, 9);

				if (!"".equals(strNSSAft[1])) {
					mListTableAft.add(strNSSAft);
					xmlexport.addDisplayControl("displayPA");
				}
			}
		}
		String sql = "select distinct PolNo, PayPlanCode from LPPerInvestPlan a where  a.EdorNo='?EdorNo?' ";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(sql);
		sbv.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		ttSSRS = ttExeSQL.execSQL(sbv);
		logger.debug(sql);
		ttSSRS = ttExeSQL.execSQL(sbv);

		if (ttSSRS != null || ttSSRS.MaxRow > 0) {
			for (int i = 1; i <= ttSSRS.MaxRow; i++) {
				String strSQLBef = "";
				strSQLBef = "select ContNo, PayPlanCode,(select distinct c.payplanname from lmriskaccpay c where c.payplancode=a.PayPlanCode),InsuAccNo,(select b.insuaccname from lmriskinsuacc b where trim(b.insuaccno)=trim(a.InsuAccNo)),InputMode,InvestRate,InvestMoney from LCPerInvestPlan a where a.PolNo= '?PolNo?' and PayPlanCode='?PayPlanCode?' order by PayPlanCode,InsuAccNo asc";
				SQLwithBindVariables sbv1=new SQLwithBindVariables();
				sbv1.sql(strSQLBef);
				sbv1.put("PolNo", ttSSRS.GetText(i, 1));
				sbv1.put("PayPlanCode", ttSSRS.GetText(i, 2));

				SSRS tSSRS = new SSRS();
				ExeSQL tExeSQL = new ExeSQL();
				tSSRS = tExeSQL.execSQL(sbv1);
				logger.debug(strSQLBef);
				if (tSSRS != null || tSSRS.MaxRow > 0) {
					for (int j = 1; j <= tSSRS.MaxRow; j++) {
						mListTableBef.setName("PABef");
						strNSSBef = new String[9];
						strNSSBef[0] = tSSRS.GetText(j, 1);
						strNSSBef[1] = tSSRS.GetText(j, 2);
						strNSSBef[2] = tSSRS.GetText(j, 3);
						strNSSBef[3] = tSSRS.GetText(j, 4);
						strNSSBef[4] = tSSRS.GetText(j, 5);
						if (tSSRS.GetText(j, 6).equals("1")) {
							strNSSBef[5] = "按比例";
							strNSSBef[6] = tSSRS.GetText(j, 7);
							strNSSBef[7] = "";
						} else {
							strNSSBef[5] = "按金额";
							strNSSBef[6] = "";
							strNSSBef[7] = tSSRS.GetText(j, 8);

						}

						// strNSSBef[8] = tSSRS.GetText(j, 9);

						if (!"".equals(strNSSBef[1])) {
							mListTableBef.add(strNSSBef);
							xmlexport.addDisplayControl("displayPA");
						}
					}
				}
			}
		}
		/*
		 * 设置displayTail1中的信息
		 */
		BqNameFun.AddEdorValiDatePart(mLPEdorItemSchema, mLPEdorAppSchema,
				xmlexport);
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareData() {
		if (mTextTag.size() < 1) {
			mErrors.addOneError("生成数据失败!");
			return false;
		}
		String[] c_strNSBef = new String[9];
		String[] c_strNSAft = new String[9];
		xmlexport.addListTable(mListTableBef, c_strNSBef);
		xmlexport.addListTable(mListTableAft, c_strNSAft);
		xmlexport.addTextTag(mTextTag);
		mResult.add(xmlexport);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
