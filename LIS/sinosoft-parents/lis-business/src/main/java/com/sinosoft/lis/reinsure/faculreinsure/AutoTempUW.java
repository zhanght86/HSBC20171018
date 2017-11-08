/**
 * ModifyDate	Developer			ReleaseDate		MantisNo		Description
 * ==========================================================
 * 2015-07-14	Goeast Hui		2015-09-18		N/A					IBA02 New Refundable PA Plan & Demise IBA00_IBA01													
 */

package com.sinosoft.lis.reinsure.faculreinsure;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.RICalFactorValueDB;
import com.sinosoft.lis.db.RIExchangeRateDB;
import com.sinosoft.lis.db.RIItemCalDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.reinsure.faculreinsure.uw.FacuAudit;
import com.sinosoft.lis.reinsure.faculreinsure.uw.RIFaculInitData;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.RICalFactorValueSchema;
import com.sinosoft.lis.schema.RIGUWErrorSchema;
import com.sinosoft.lis.schema.RIItemCalSchema;
import com.sinosoft.lis.schema.RILMUWSchema;
import com.sinosoft.lis.schema.RIPolRecordSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.RIAccumulateDefSet;
import com.sinosoft.lis.vschema.RIAccumulateRDCodeSet;
import com.sinosoft.lis.vschema.RICalFactorValueSet;
import com.sinosoft.lis.vschema.RIExchangeRateSet;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.lis.vschema.RILMUWSet;
import com.sinosoft.lis.vschema.RIPreceptSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: AutoTempUW
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2007
 * </p>
 * <p>
 * Company: SINOSOFT
 * </p>
 * 
 * @author:Zhang Bin
 * @version 1.0
 */
public class AutoTempUW {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private RILMUWSet mGrpRILMUWSet;
	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();
	/** 数据操作字符串 */
	private String DETAILFLAG = "01"; // 01-代表险种级别 02-代表责任级别
	private String mOpeFlag = "";
	private String mOpeData = "";
	private String mUWResult = "0";
	private String mBatchNo = "";
	/** 累计风险类别 */
	private RIAccumulateDefSet mRIAccumulateDefSet;
	/** 累计险种/责任表 */
	private RIAccumulateRDCodeSet mRIAccumulateRDCodeSet;
	/** 再保方案 */
	private RIPreceptSet mRIPreceptSet;
	/** 核保核赔算法 */
	private RIItemCalSet mRIItemCalSet;
	/**  **/
	private RIExchangeRateSet mRIExchangeRateSet;

	private VData mResult;
	private String mContNo;
	private String[][] mInputInfo;

	private LCGrpContSchema mLCGrpContSchema;
	private RIPolRecodeChng mRIPolRecodeChng;
	private MMap mMap = new MMap();
	private VData mVData = new VData();
	private PubSubmit mPubSubmit = new PubSubmit();

	/**
	 * @param
	 * @param aOpeFlag
	 *            String 01:个单新单， 03：个单保全；04：理赔；05：团体新单；06团体保全
	 */
	public AutoTempUW(VData vInputData, String aOpeFlag) {
		if (!initData(vInputData, aOpeFlag)) {
		}
	}

	/**
	 * @param
	 */
	public AutoTempUW() {
	}

	/**
	 * aOpeFlag='04' aOpeData:CaseNo
	 * 
	 * @param aOpeData
	 *            String
	 * @param aOpeFlag
	 *            String
	 * @return boolean
	 */
	private boolean initData(VData vData, String aOpeFlag) {
		try {
			mBatchNo = PubFun1.CreateMaxNo("RIGUWERRBATCH", 10);
			this.mInputData = vData;
			this.mOpeFlag = aOpeFlag;
			if (!getInputData(mInputData)) {
				return false;
			}
			RIFaculInitData tRIFaculInitData = new RIFaculInitData();
			tRIFaculInitData.init(mInputData, mOpeFlag);
			// 获取累计风险定义
			this.mRIAccumulateDefSet = tRIFaculInitData.getRIAccumulateDefSet();
			// 获取险种、责任编码
			this.mRIAccumulateRDCodeSet = tRIFaculInitData
					.getRIAccumulateRDCodeSet();
			// 获取再保方案
			this.mRIPreceptSet = tRIFaculInitData.getRIPreceptSet();
			// 获取核保核赔算法
			this.mRIItemCalSet = tRIFaculInitData.getRIItemCalSet();
		} catch (Exception ex) {
			buildError("dealData", " 再保核赔时初始化数据出错 ");
			return false;
		}
		return true;
	}

	private boolean getInputData(VData vInputData) {
		if (mOpeFlag.equals("04")) {
			mInputInfo = (String[][]) vInputData.getObject(0);
		} else {
			TransferData tTransferData = (TransferData) vInputData
					.getObjectByObjectName("TransferData", 0);
			mContNo = (String) tTransferData.getValueByName("ContNo");
		}
		return true;
	}

	// 核赔,核保方法调用
	public boolean deal(VData vInputData, String aOpeFlag) {
		mOpeFlag = aOpeFlag;
		if (!getInputData(vInputData)) {
			return false;
		}
		if (!initRIExchangeRateSet()) {
			buildError("getUWInfo", "初始化汇率失败！" + this.mErrors.getFirstError());
			return false;
		}
		if (aOpeFlag.equals("01")) {
			if (!getUWInfo()) {
				buildError("getUWInfo",
						"调用再保核保核赔失败！" + this.mErrors.getFirstError());
				return false;
			}
		}
		if (aOpeFlag.equals("04")) {
			if (!getClaimInfo()) {
				buildError("getClaimInfo",
						"调用再保核保核赔失败！" + this.mErrors.getFirstError());
				return false;
			}
		}
		if (aOpeFlag.equals("07")) {
			if (!getRICompanyInfo()) {
				buildError("getRICompanyInfo",
						"调用再保核保核赔失败！" + this.mErrors.getFirstError());
				return false;
			}
		}
		if (aOpeFlag.equals("21")) {
			if (!uwFirstCont()) {
				buildError("uwFirstCont",
						"临分核保失败！" + this.mErrors.getFirstError());
				return false;
			}
		}
		if (aOpeFlag.equals("22")) {
			if (!uwTwoCont()) {
				buildError("uwFirstCont",
						"临分核保失败！" + this.mErrors.getFirstError());
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		TransferData tTransferData = new TransferData();
		AutoTempUW test = new AutoTempUW();
		String aOpeFlag;
		VData result;
		// // 理赔
		VData t = new VData();
		String[][] a = new String[2][8];
		a[0][0] = "9805445188"; // 保单号
		a[0][1] = "IBW30"; // RiskCode
		a[0][2] = "W30001"; // DutyCode
		a[0][3] = "110010000902913"; // 保单险种号
		a[0][4] = "Case00001"; // 案件号
		a[0][5] = "0000900657"; // 被保险人号
		a[0][6] = "0000900657"; // 被保险人姓名
		a[0][7] = "50000"; // 实赔金额

		a[1][0] = "9805445188"; // 保单号
		a[1][1] = "IRA01"; // RiskCode
		a[1][2] = "A01R01"; // DutyCode
		a[1][3] = "110010000902914"; // 保单险种号
		a[1][4] = "Case00002"; // 案件号
		a[1][5] = "0000901362"; // 被保险人号
		a[1][6] = "0000901362"; // 被保险人姓名
		a[1][7] = "80000"; // 实赔金额
		t.add(a);
		t.add(tTransferData);
		aOpeFlag = "04";
		if (!test.deal(t, aOpeFlag)) {
			System.out.println("aaaaaaaaaa" + test.mErrors.getFirstError());
		}
		result = test.getResult();
		System.out.println("result.size: " + result.size());
		TransferData aTransferData;
		// for (int i = 0; i < result.size(); i++) {
		// aTransferData = (TransferData) result.getObjectByObjectName(
		// "TransferData", i);
		// System.out.print("ContNo: "
		// + aTransferData.getValueByName("ContNo") + "   ");
		// System.out.print("RiskCode: "
		// + aTransferData.getValueByName("RiskCode") + "   ");
		// System.out.print("DutyCode: "
		// + aTransferData.getValueByName("DutyCode") + "   ");
		// System.out.print("PolNo: " + aTransferData.getValueByName("PolNo")
		// + "   ");
		// System.out.print("CaseNo: "
		// + aTransferData.getValueByName("CaseNo") + "   ");
		// System.out.print("InsuredNo: "
		// + aTransferData.getValueByName("InsuredNo") + "   ");
		// System.out.print("InsuredName: "
		// + aTransferData.getValueByName("InsuredName") + "   ");
		// System.out.print("RealPay: "
		// + aTransferData.getValueByName("RealPay") + "   ");
		// System.out.print("ReComCode: "
		// + aTransferData.getValueByName("ReComCode") + "   ");
		// System.out.print("ReComName: "
		// + aTransferData.getValueByName("ReComName") + "   ");
		// System.out.print("ReContCode: "
		// + aTransferData.getValueByName("ReContCode") + "   ");
		// System.out.print("NoticeLimit: "
		// + aTransferData.getValueByName("NoticeLimit") + "   ");
		// System.out.print("ParaLimit: "
		// + aTransferData.getValueByName("ParaLimit") + "   ");
		// System.out.println();
		// }
		// 核保
		// test = new AutoTempUW();
		// t = new VData();
		// tTransferData.setNameAndValue("ContNo", "86110020100210000042");
		// t.add(tTransferData);
		// aOpeFlag = "01";
		// if (!test.deal(t, aOpeFlag)) {
		// System.out.println("aaaaaaaaaa" + test.mErrors.getFirstError());
		// }
		// result = test.getResult();
		// System.out.println("result.size: " + result.size());
		// for (int i = 0; i < result.size(); i++) {
		// aTransferData = (TransferData) result.getObjectByObjectName(
		// "TransferData", i);
		// System.out.print("ContNo: "
		// + aTransferData.getValueByName("ContNo"));
		// System.out.print("RiskCode: "
		// + aTransferData.getValueByName("RiskCode"));
		// System.out.print("DutyCode: "
		// + aTransferData.getValueByName("DutyCode"));
		// System.out.print("PolNo: " + aTransferData.getValueByName("PolNo"));
		// System.out.print("InsuredNo: "
		// + aTransferData.getValueByName("InsuredNo"));
		// System.out.print("ReContCode: "
		// + aTransferData.getValueByName("ReContCode"));
		// System.out.print("ReContName: "
		// + aTransferData.getValueByName("ReContName"));
		// System.out.print("ReComCode: "
		// + aTransferData.getValueByName("ReComCode"));
		// System.out.print("ReComName: "
		// + aTransferData.getValueByName("ReComName"));
		// System.out.print("ItemCode: "
		// + aTransferData.getValueByName("ItemCode"));
		// System.out.print("ItemName: "
		// + aTransferData.getValueByName("ItemName"));
		// System.out.print("Currency: "
		// + aTransferData.getValueByName("Currency"));
		// System.out.print("Value: " + aTransferData.getValueByName("Value"));
		// System.out.print("Ramark: "
		// + aTransferData.getValueByName("Ramark"));
		// System.out.println();
		// }
		// 再保公司信息
		// test = new AutoTempUW();
		// t = new VData();
		// tTransferData.setNameAndValue("ContNo", "86110020100210000042");
		// t.add(tTransferData);
		// aOpeFlag = "07";
		// if (!test.deal(t, aOpeFlag)) {
		// System.out.println("aaaaaaaaaa" + test.mErrors.getFirstError());
		// }
		// result = test.getResult();
		// System.out.println("result.size: " + result.size());
		for (int i = 0; i < result.size(); i++) {
			aTransferData = (TransferData) result.getObjectByObjectName(
					"TransferData", i);
			System.out.println("ContNo: "
					+ aTransferData.getValueByName("ContNo"));
			System.out.println("RiskCode: "
					+ aTransferData.getValueByName("RiskCode"));
			System.out.println("DutyCode: "
					+ aTransferData.getValueByName("DutyCode"));
			System.out.println("ReContCode: "
					+ aTransferData.getValueByName("ReContCode"));
			System.out.println("ReContName: "
					+ aTransferData.getValueByName("ReComName"));
			System.out.println("ReComName: "
					+ aTransferData.getValueByName("NoticeLimit"));
			System.out.println("UpperLimit: "
					+ aTransferData.getValueByName("ParaLimit"));
			System.out.println("LowerLimit: "
					+ aTransferData.getValueByName("NoticeRemark"));
			System.out.println("AcceptQuota: "
					+ aTransferData.getValueByName("ParaRemark"));
			System.out.println();
		}
	}

	/**
	 * 中银再保核赔
	 * 
	 * @return
	 */
	private boolean getClaimInfo() {
		ExeSQL tExeSQL = new ExeSQL();
		SSRS claimSSRS;
		mResult = new VData();
		String[][] tReturnInfo;
		TransferData tTransferData;
		try {
			for (int i = 0; i < mInputInfo.length; i++) {
				String polno = mInputInfo[i][3];
				SSRS polInfo = getPolInfo(polno, "04");
				for (int j = 0; j < polInfo.getMaxRow(); j++) {
					String riPreceptNo = mapFactorValues(polInfo
							.getRowData(j + 1));
					String sql2 = "SELECT x.A1,X.A2,X.A3,"
							+ "nvl((SELECT a.factorvalue FROM ricalfactorvalue a WHERE a.serialno = X.A4),'No Limited'),"
							+ "nvl((SELECT a.factorvalue FROM ricalfactorvalue a WHERE a.serialno = X.A5),'No Limited'),"
							+ "nvl((SELECT a.remark FROM ricalfactorvalue a WHERE a.serialno = X.A4),'No Limited'),"
							+ "nvl((SELECT a.remark FROM ricalfactorvalue a WHERE a.serialno = X.A5),'No Limited') "
							+ "FROM (select distinct r.recomcode A1,(select c.companyname from RIComInfo c where c.companyno = r.recomcode) A2,'' A3,"
							+ "(select n.serialno from RICalFactorValue n where n.factorcode = '002' and n.RIPreceptNo = r.RIPreceptNo and n.recomcode = r.recomcode) A4, "
							+ "decode(r.factorname,null,(select p.serialno from RICalFactorValue p where p.factorcode = '003' and p.RIPreceptNo = r.RIPreceptNo and p.recomcode = r.recomcode and rownum = 1),r.serialno) A5 "
							+ " from RICalFactorValue r, RIPrecept b where r.recontcode = b.ricontno and r.RIPreceptNo = b.RIPreceptNo and r.FactorType = '02' and b.State = '01' and b.RIPreceptNo = '"
							+ riPreceptNo + "') X";

					// String sql2 = "select distinct r.recomcode A1,"
					// +
					// " (select a.companyname from RIComInfo a where a.companyno = r.recomcode) A2,'' A3,"
					// +
					// " (select decode(min(CAST(x.Factorvalue AS float)),null,'No Limited',min(CAST(x.Factorvalue AS float))) from RICalFactorValue x where x.factorcode='002' and x.RIPreceptNo = r.RIPreceptNo and x.recomcode =  r.recomcode) A4, "
					// +
					// " decode(r.factorname,null,(select decode(min(CAST(x.Factorvalue AS float)),null,'No Limited',min(CAST(x.Factorvalue AS float))) from RICalFactorValue x where x.factorcode = '003' and x.RIPreceptNo = r.RIPreceptNo and x.recomcode = r.recomcode),r.Factorvalue) A5 "
					// + " from RICalFactorValue r ,RIPrecept b "
					// + " where b.accumulatedefno='"
					// + accSSRS.getAllData()[j][0]
					// +
					// "' and r.recontcode=b.ricontno and r.RIPreceptNo=b.RIPreceptNo and r.FactorType='02' and b.State='01'"
					// +
					// " and b.ripreceptno in (SELECT c.ripreceptno FROM riprecept c WHERE c.arithmeticid in (SELECT h.arithmeticdefid FROM riarithmeticriskrela h WHERE h.riskcode = '"
					// + mInputInfo[i][1] + "'))";

					System.out.println("得到再保核赔信息：" + sql2);
					claimSSRS = tExeSQL.execSQL(sql2);
					tReturnInfo = new String[claimSSRS.getMaxRow()][13];

					for (int k = 0; k < claimSSRS.getAllData().length; k++) {
						tTransferData = new TransferData();

						tTransferData.setNameAndValue("ContNo",
								mInputInfo[i][0]);// 保单号
						tTransferData.setNameAndValue("RiskCode",
								mInputInfo[i][1]);// 险种号
						tTransferData.setNameAndValue("DutyCode",
								mInputInfo[i][2]);// 责任编码
						tTransferData
								.setNameAndValue("PolNo", mInputInfo[i][3]);// 保单险种号
						tTransferData.setNameAndValue("CaseNo",
								mInputInfo[i][4]);// 案件号码
						tTransferData.setNameAndValue("InsuredNo",
								mInputInfo[i][5]);// 被保人号
						tTransferData.setNameAndValue("InsuredName",
								mInputInfo[i][6]);// 被保人姓名
						tTransferData.setNameAndValue("RealPay",
								mInputInfo[i][7]);// 实赔金额
						tTransferData.setNameAndValue("ReComCode",
								claimSSRS.getAllData()[k][0]);// 再保公司编码
						tTransferData.setNameAndValue("ReComName",
								claimSSRS.getAllData()[k][1]);// 再保公司名称
						tTransferData.setNameAndValue("ReContCode",
								claimSSRS.getAllData()[k][2]);// 再保合同编码
						tTransferData.setNameAndValue("NoticeLimit",
								claimSSRS.getAllData()[k][3]);// 通知限额
						tTransferData.setNameAndValue("ParaLimit",
								claimSSRS.getAllData()[k][4]);// 参与限额
						tTransferData.setNameAndValue("NoticeRemark",
								claimSSRS.getAllData()[k][5]);// 通知限额文字说明
						tTransferData.setNameAndValue("ParaRemark",
								claimSSRS.getAllData()[k][6]);// 参与限额文字说明

						mResult.add(tTransferData);
						mResult = compareInfo();
					}
				}
			}
		} catch (Exception ex) {
			buildError("getInputData", "调用再保核保核赔失败:" + ex.getMessage());
			return false;
		}
		return true;
	}

	private VData compareInfo() {
		VData tVData = new VData();
		HashMap<String, TransferData> map = new HashMap<String, TransferData>();
		for (int i = 0; i < mResult.size(); i++) {
			TransferData tTransferData = (TransferData) mResult
					.getObjectByObjectName("TransferData", i);
			String t = (String) tTransferData.getValueByName("ContNo")
					+ (String) tTransferData.getValueByName("RiskCode")
					+ (String) tTransferData.getValueByName("DutyCode")
					+ (String) tTransferData.getValueByName("ReComCode")
					+ (String) tTransferData.getValueByName("NoticeLimit")
					+ (String) tTransferData.getValueByName("ParaLimit");
			map.put(t, tTransferData);
		}
		Set<String> set = map.keySet();
		for (Iterator<String> iterator = set.iterator(); iterator.hasNext();) {
			String s = (String) iterator.next();
			TransferData t = (TransferData) map.get(s);
			System.out.println(s + ":" + (String) t.getValueByName("ContNo"));
			tVData.add(t);
		}

		return tVData;
	}

	/**
	 * 中银再保核保信息
	 * 
	 * @return
	 */
	private boolean getUWInfo() {
		TransferData tTransferData;
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer sqlStrBuffer = new StringBuffer();
		try {
			sqlStrBuffer
					.append(" select distinct a.contno,a.riskcode,b.dutycode,a.polno,a.insuredno from lcpol a,lcduty b where a.polno=b.polno and a.contno = '"
							+ mContNo + "' ");
			SSRS paraSSRS = new SSRS();
			paraSSRS = tExeSQL.execSQL(sqlStrBuffer.toString());
			mResult = new VData();

			SSRS tSSRS;
			for (int i = 0; i < paraSSRS.getAllData().length; i++) {

				tSSRS = new SSRS();

				sqlStrBuffer = new StringBuffer();
				sqlStrBuffer
						.append(" select distinct v.Recontcode,(select b.Ricontname from Ribargaininfo b where b.ricontno = v.Recontcode),v.Recomcode,(select c.Companyname from Ricominfo c where c.Companyno=v.Recomcode),v.Factorcode,v.Factorname,v.Currency,v.Factorvalue,v.Remark ");
				sqlStrBuffer
						.append(" from RICalFactorValue v where v.Ripreceptno in (select r.Ripreceptno from Riprecept r where r.State ='01' and r.arithmeticid in (SELECT h.arithmeticdefid FROM riarithmeticriskrela h WHERE h.riskcode = '");
				sqlStrBuffer.append(paraSSRS.getAllData()[i][1]);
				sqlStrBuffer.append("')) and v.Factortype='01'");

				System.out.println(" sql: " + sqlStrBuffer);

				tSSRS = tExeSQL.execSQL(sqlStrBuffer.toString());

				for (int j = 0; j < tSSRS.getAllData().length; j++) {
					tTransferData = new TransferData();

					tTransferData.setNameAndValue("ContNo",
							paraSSRS.getAllData()[i][0]); // 保单号
					tTransferData.setNameAndValue("RiskCode",
							paraSSRS.getAllData()[i][1]); // 险种号
					tTransferData.setNameAndValue("DutyCode",
							paraSSRS.getAllData()[i][2]); // 责任号
					tTransferData.setNameAndValue("PolNo",
							paraSSRS.getAllData()[i][3]); // 保单险种号
					tTransferData.setNameAndValue("InsuredNo",
							paraSSRS.getAllData()[i][4]); // 被保人号
					tTransferData.setNameAndValue("ReContCode",
							tSSRS.getAllData()[j][0]); // 再保合同号
					tTransferData.setNameAndValue("ReContName",
							tSSRS.getAllData()[j][1]); // 再保合同名
					tTransferData.setNameAndValue("ReComCode",
							tSSRS.getAllData()[j][2]); // 再保公司编码
					tTransferData.setNameAndValue("ReComName",
							tSSRS.getAllData()[j][3]); // 再保公司名称
					tTransferData.setNameAndValue("ItemCode",
							tSSRS.getAllData()[j][4]); // 计算项编码
					tTransferData.setNameAndValue("ItemName",
							tSSRS.getAllData()[j][5]); // 计息项名称
					tTransferData.setNameAndValue("Currency",
							tSSRS.getAllData()[j][6]); // 币种代码
					tTransferData.setNameAndValue("Value",
							tSSRS.getAllData()[j][7]); // 值
					tTransferData.setNameAndValue("Ramark",
							tSSRS.getAllData()[j][8]); // 说明
					mResult.add(tTransferData);
				}

			}
		} catch (Exception ex) {
			buildError("getInputData", "调用再保核保核赔失败:" + ex.getMessage());
			return false;
		}
		return true;
	}

	// 初始化汇率
	private boolean initRIExchangeRateSet() {
		RIExchangeRateDB tRIExchangeRateDB = new RIExchangeRateDB();
		String sql = "select * from RIExchangeRate x,(select max(a.Serialno) Serialno,a.Currency Currency from RIExchangeRate a where a.state='01' group by a.Currency) y where x.Serialno = y.Serialno and x.Currency = y.Currency ";
		mRIExchangeRateSet = tRIExchangeRateDB.executeQuery(sql);
		if (tRIExchangeRateDB.mErrors.needDealError()) {
			buildError("InitInfo", "初始化累计风险为 的汇率出错");
			return false;
		}
		return true;
	}

	/**
	 * 中银再保核保信息
	 * 
	 * @return
	 */
	private boolean uwFirstCont() {
		RICalFactorValueSet resultRICalFactorValueSet = new RICalFactorValueSet();
		mResult = new VData();
		try {
			SSRS polInfo = getPolInfo(mContNo, "01");
			for (int i = 0; i < polInfo.getMaxRow(); i++) {
				SSRS factorInfo = getFactorInfo(polInfo.getRowData(i + 1));
				for (int j = 0; j < factorInfo.getMaxRow(); j++) {
					String itemCalSql = "select * from RIItemCal a where a.ArithmeticType='21' and a.Arithmeticid='"
							+ factorInfo.getAllData()[j][0] + "'";
					System.out.println(" itemCalSql: " + itemCalSql);

					RIItemCalDB tRIItemCalDB = new RIItemCalDB();
					RIItemCalSet tRIItemCalSet = tRIItemCalDB
							.executeQuery(itemCalSql);
					for (int k = 1; k <= tRIItemCalSet.size(); k++) {

						RIItemCalSchema tRIItemCalSchema = tRIItemCalSet.get(k);
						if (tRIItemCalSchema.getItemCalType().equals("1")) {
							// 固定值
						}
						if (tRIItemCalSchema.getItemCalType().equals("2")) {
							// 核保算法sql
						}
						if (tRIItemCalSchema.getItemCalType().equals("3")) {
							// 核保算法类
							TransferData tTransferData = new TransferData();
							tTransferData.setNameAndValue("ContNo", mContNo); // 保单号
							tTransferData.setNameAndValue("PolNo",
									polInfo.getAllData()[i][1]); // 保单险种号
							tTransferData.setNameAndValue("RiskCode",
									polInfo.getAllData()[i][2]); // 责任编码
							tTransferData.setNameAndValue("InsuredNo",
									polInfo.getAllData()[i][3]); // 客户号

							tTransferData.setNameAndValue("FactorID",
									factorInfo.getAllData()[j][0]); // 要素标识
							tTransferData.setNameAndValue("FactorValue",
									factorInfo.getAllData()[j][1]);// 要素值
							tTransferData.setNameAndValue("Currency",
									factorInfo.getAllData()[j][2]);// 币种
							tTransferData.setNameAndValue("AccumulateDefNO",
									factorInfo.getAllData()[j][3]);// 累计风险
							tTransferData.setNameAndValue("AccumulateMode",
									factorInfo.getAllData()[j][4]);// 累计方式

							VData tVData = new VData();
							tVData.add(tTransferData);
							tVData.add(mRIExchangeRateSet);

							Class tClass = Class.forName(tRIItemCalSchema
									.getCalClass());
							FacuAudit tFacuAudit = (FacuAudit) tClass
									.newInstance();
							tFacuAudit.submitData(tVData, "");

							if (!tFacuAudit.getResult()) {

								RICalFactorValueDB tRICalFactorValueDB = new RICalFactorValueDB();
								tRICalFactorValueDB.setSerialNo(factorInfo
										.getAllData()[j][5]);
								if (!tRICalFactorValueDB.getInfo()) {
									buildError("getInputData", "查询临分核保阀值失败！");
									return false;
								}
								RICalFactorValueSchema tRICalFactorValueSchema = tRICalFactorValueDB
										.getSchema();
								resultRICalFactorValueSet
										.add(tRICalFactorValueSchema);
							}
						}
					}
				}
			}

			if (resultRICalFactorValueSet.size() > 0) {
				mResult.add(resultRICalFactorValueSet);
			}
			for (int i = 1; i <= resultRICalFactorValueSet.size(); i++) {
				System.out.println("getFactorID: "
						+ resultRICalFactorValueSet.get(i).getFactorID()
						+ " getFactorValue "
						+ resultRICalFactorValueSet.get(i).getFactorValue());
			}
			return true;
		} catch (Exception e) {
			buildError("uwFirstCont", "调用再保核保接口信息错误");
			return false;
		}

	}

	private SSRS getFactorInfo(String[] rowData) {
		String preceptNo = mapFactorValues(rowData);

		String factorSQL = "select v.Factorid,v.FactorValue,v.Currency,p.Accumulatedefno,(select a.Accumulatemode from Riaccumulatedef a where a.Accumulatedefno=p.Accumulatedefno),v.SerialNo from RICalFactorValue v ,Riprecept p where v.Ripreceptno=p.Ripreceptno and p.State ='01' and v.Factortype='01' and v.ValueType='01' and p.ripreceptno='"
				+ preceptNo + "'";
		System.out.println(" factorSQL: " + factorSQL);

		SSRS ret = new ExeSQL().execSQL(factorSQL);
		return ret;
	}

	private String mapFactorValues(String[] rowData) {
		String tContNo = rowData[0];// 保单号
		String tPolNo = rowData[1];// 保单险种号
		String tRiskCode = rowData[2];// 险种编码
		String tInsuredNo = rowData[3];// 被保人号
		String tInsuredAppAge = rowData[4];// 被保人年龄
		String tCValiDate = rowData[5];// 保单生效日
		String tCurrency = rowData[6];// 币种
		String tSaleChnl = rowData[7];// 销售渠道
		String tPlanLevel = rowData[8];// 计划类别
		String mainPolNo = rowData[9];// 主险保单险种号
		String mainRiskCode = rowData[10];// 主险险种编码
		String mainInsuredNo = rowData[11];// 主险被保人号
		String mainInsuredAppAge = rowData[12];// 主险被保人年龄
		String mainCValiDate = rowData[13];// 主险保单生效日
		String mainCurrency = rowData[14];// 主险币种
		String mainSaleChnl = rowData[15];// 主险销售渠道
		String mainPlanLevel = rowData[16];// 主险计划类别

		String ret = "";
		if ("IBW11".equals(tRiskCode)) {
			ret = "C01010010010";
		}
		if ("IBN03".equals(tRiskCode)) {
			ret = "C01010210010";
		}
		if ("IBM00".equals(tRiskCode)) {
			ret = "C01010140130";
		}
		if ("IBW11".equals(tRiskCode)) {
			ret = "C01010010010";
		} else if ("IBE04".equals(tRiskCode) || "IBE05".equals(tRiskCode)
				|| "IBE12".equals(tRiskCode) || "IBE19".equals(tRiskCode)
				|| "IBE24".equals(tRiskCode) || "IBE58".equals(tRiskCode)
				|| "IBE59".equals(tRiskCode) || "IBT08".equals(tRiskCode)
				|| "IBT09".equals(tRiskCode) || "IBT12".equals(tRiskCode)
				|| "IBW02".equals(tRiskCode) || "IBW08".equals(tRiskCode)
				|| "IBW13".equals(tRiskCode) || "IBW14".equals(tRiskCode)) {
			ret = "C01010010020";
		} else if ("IBE40".equals(tRiskCode)) {
			ret = "C01010020010";
		} else if ("IBU00".equals(tRiskCode) || "IBU01".equals(tRiskCode)
				|| "IBU02".equals(tRiskCode)) {
			ret = "C01010060010";
		} else if ("IBU03".equals(tRiskCode)) {
			ret = "C01010220010";
		} else if ("IBW04".equals(tRiskCode) || "IBW05".equals(tRiskCode)
				|| "IBW06".equals(tRiskCode) || "IBW07".equals(tRiskCode)) {
			ret = "C01010090010";
		} else if ("IBW15".equals(tRiskCode)) {
			ret = "C01010090020";
		} else if ("IBT16".equals(tRiskCode)) {
			ret = "C01010110010";
		} else if ("IBE06".equals(tRiskCode) || "IBE07".equals(tRiskCode)
				|| "IBE13".equals(tRiskCode) || "IBT04".equals(tRiskCode)
				|| "IBW03".equals(tRiskCode) || "IBW09".equals(tRiskCode)
				|| "IBW10".equals(tRiskCode)) {
			ret = "C01010120040";
		} else if ("IBW12".equals(tRiskCode)) {
			ret = "C01010140010";
		} else if ("IBL00".equals(tRiskCode)) {
			ret = "C01010140020";
		} else if ("IBE21".equals(tRiskCode) || "IBE22".equals(tRiskCode)
				|| "IBE50".equals(tRiskCode) || "IBE51".equals(tRiskCode)) {
			ret = "C01010140090";
		} else if ("IBE23".equals(tRiskCode)) {
			ret = "C01010140100";
		} else if ("IBE29".equals(tRiskCode) || "IBE34".equals(tRiskCode)
				|| "IBW18".equals(tRiskCode)) {
			ret = "C01010140110";
		} else if ("IBW31".equals(tRiskCode)) {
			ret = "C01010140150";
		} else if ("IBL03".equals(tRiskCode)) {
			ret = "C01010140160";
		} else if ("IBW32".equals(tRiskCode)) {
			ret = "C01010140170";
		} else if ("IBN00".equals(tRiskCode) || "IBN01".equals(tRiskCode)) {
			ret = "C01010160010";
		} else if ("IBA00".equals(tRiskCode)) {
			ret = "C01010170010";
		// START [IBA02 New Refundable PA Plan & Demise IBA00_IBA01] (by Goeast on 20150714)
		} else if ("IBA01".equals(tRiskCode) || "IBA02".equals(tRiskCode)) {
		// END [IBA02 New Refundable PA Plan & Demise IBA00_IBA01] (by Goeast on 20150714)
			ret = "C01010180010";
		} else if ("IBC01".equals(tRiskCode) || "IBC02".equals(tRiskCode)) {
			ret = "C01010190010";
		}

		else if ("IBW00".equals(tRiskCode)) {
			if (PubFun.checkDate(tCValiDate, "2001-12-19")) {
				ret = "C01010010010";
			} else {
				ret = "C01010010020";
			}
		} else if ("IBM01".equals(tRiskCode)) {
			if (PubFun.checkDate("2007-10-18", tCValiDate)
					&& PubFun.checkDate(tCValiDate, "2008-09-10")) {
				ret = "C01010100010";
			} else if (PubFun.checkDate("2008-09-11", tCValiDate)
					&& PubFun.checkDate(tCValiDate, "2008-12-31")) {
				ret = "C01010140140";
			}
		} else if ("IBT00".equals(tRiskCode) || "IBT02".equals(tRiskCode)
				|| "IBT03".equals(tRiskCode)) {
			if (PubFun.checkDate(tCValiDate, "2004-04-01")) {
				ret = "C01010080010";
			} else {
				ret = "C01010010020";
			}
		} else if ("IBW30".equals(tRiskCode)) {
			if ("03".equals(tSaleChnl) && "01".equals(tCurrency)) {
				ret = "C01010010070";
			} else if ("08".equals(tSaleChnl) && "01".equals(tCurrency)) {
				ret = "C01010010080";
			} else if (!"01".equals(tCurrency)) {
				ret = "C01010140150";
			}
		} else if ("IBT06".equals(tRiskCode) || "IBT15".equals(tRiskCode)) {
			if ("5".equals(tPlanLevel)) {
				ret = "C01010150010";
			} else if ("1".equals(tPlanLevel)) {
				ret = "C01010010020";
			}
		} else if ("IBE53".equals(tRiskCode) && "01".equals(tCurrency)) {
			if (PubFun.checkDate("2012-09-17", tCValiDate)) {
				ret = "C01010040020";
			} else {
				ret = "C01010040010";
			}
		} else if ("IBE62".equals(tRiskCode) && "01".equals(tCurrency)) {
			ret = "C01010070010";
		} else if ("IBW19".equals(tRiskCode) || "IBW20".equals(tRiskCode)
				|| "IBW21".equals(tRiskCode) || "IBW23".equals(tRiskCode)
				|| "IBW24".equals(tRiskCode) || "IBW25".equals(tRiskCode)
				|| "IBW26".equals(tRiskCode) || "IBW27".equals(tRiskCode)
				|| "IBW28".equals(tRiskCode) || "IBW29".equals(tRiskCode)) {
			if (PubFun.checkDate("2006-07-17", tCValiDate)) {
				ret = "C01010140120";
			}
		}

		else if ("IRT00".equals(tRiskCode)) {
			if ("IBW00".equals(mainRiskCode) || "IBW11".equals(mainRiskCode)
					|| "IBE12".equals(mainRiskCode)
					|| "IBE19".equals(mainRiskCode)
					|| "IBW02".equals(mainRiskCode)
					|| "IBW08".equals(mainRiskCode)
					|| "IBW14".equals(mainRiskCode)
					|| "IBW13".equals(mainRiskCode)
					|| "IBW04".equals(mainRiskCode)
					|| "IBW05".equals(mainRiskCode)) {
				ret = "C01010010020";
			} else if ("IBW15".equals(mainRiskCode)) {
				ret = "C01010090020";
			} else if ("IBW04".equals(mainRiskCode)
					|| "IBW05".equals(mainRiskCode)
					|| "IBW06".equals(mainRiskCode)
					|| "IBW06".equals(mainRiskCode)) {
				ret = "C01010090010";
			} else if ("IBE07".equals(mainRiskCode)
					|| "IBE13".equals(mainRiskCode)
					|| "IBW10".equals(mainRiskCode)
					|| "IBW03".equals(mainRiskCode)
					|| "IBW09".equals(mainRiskCode)
					|| "IBW07".equals(mainRiskCode)
					|| "IBW06".equals(mainRiskCode)
					|| "IBW12".equals(mainRiskCode)) {
				ret = "C01010120040";
			} else if ("IBW31".equals(mainRiskCode)
					|| "IBW32".equals(mainRiskCode)
					|| "IBC01".equals(mainRiskCode)
					|| "IBC02".equals(mainRiskCode)
					|| "IBE22".equals(mainRiskCode)
					|| "IBE29".equals(mainRiskCode)
					|| "IBW18".equals(mainRiskCode)
					|| "IBW19".equals(mainRiskCode)) {
				ret = "C01010140070";
			} else if ("IBW30".equals(mainRiskCode)
					&& !"01".endsWith(mainCurrency)) {
				ret = "C01010140070";
			}
		} else if ("IRA00".equals(tRiskCode)) {
			if ("IBW00".equals(mainRiskCode) || "IBW11".equals(mainRiskCode)
					|| "IBE12".equals(mainRiskCode)
					|| "IBE19".equals(mainRiskCode)
					|| "IBW02".equals(mainRiskCode)
					|| "IBW08".equals(mainRiskCode)
					|| "IBW14".equals(mainRiskCode)
					|| "IBW13".equals(mainRiskCode)
					|| "IBW04".equals(mainRiskCode)
					|| "IBW05".equals(mainRiskCode)
					|| "IBT06".equals(mainRiskCode)
					|| "IBT15".equals(mainRiskCode)) {
				ret = "C01010010030";
			} else if ("IBW15".equals(mainRiskCode)) {
				ret = "C01010090050";
			} else if ("IBL00".equals(mainRiskCode)) {
				ret = "C01010140040";
			} else if ("IBE13".equals(mainRiskCode)
					|| "IBT04".equals(mainRiskCode)
					|| "IBW03".equals(mainRiskCode)
					|| "IBW09".equals(mainRiskCode)
					|| "IBW10".equals(mainRiskCode)
					|| "IBW06".equals(mainRiskCode)
					|| "IBW07".equals(mainRiskCode)
					|| "IBW12".equals(mainRiskCode)) {
				ret = "C01010120020";
			} else if ("IBE27".equals(mainRiskCode)
					|| "IBC01".equals(mainRiskCode)
					|| "IBC02".equals(mainRiskCode)
					|| "IBE21".equals(mainRiskCode)
					|| "IBE22".equals(mainRiskCode)
					|| "IBW18".equals(mainRiskCode)
					|| "IBE29".equals(mainRiskCode)
					|| "IBW19".equals(mainRiskCode)
					|| "IBW20".equals(mainRiskCode)
					|| "IBW21".equals(mainRiskCode)
					|| "IBW31".equals(mainRiskCode)
					|| "IBW32".equals(mainRiskCode)) {
				ret = "C01010140060";
			} else if ("IBW30".equals(mainRiskCode)
					&& !"01".endsWith(mainCurrency)) {
				ret = "C01010140060";
			}
		} else if ("IRA05".equals(tRiskCode)) {
			if ("IBW00".equals(mainRiskCode) || "IBW11".equals(mainRiskCode)
					|| "IBE12".equals(mainRiskCode)
					|| "IBE19".equals(mainRiskCode)
					|| "IBW02".equals(mainRiskCode)
					|| "IBW08".equals(mainRiskCode)
					|| "IBW14".equals(mainRiskCode)
					|| "IBW13".equals(mainRiskCode)
					|| "IBW04".equals(mainRiskCode)
					|| "IBW05".equals(mainRiskCode)
					|| "IBT06".equals(mainRiskCode)
					|| "IBT15".equals(mainRiskCode)) {
				ret = "C01010010030";
			} else if ("IBE27".equals(mainRiskCode)
					|| "IBC01".equals(mainRiskCode)
					|| "IBC02".equals(mainRiskCode)
					|| "IBE21".equals(mainRiskCode)
					|| "IBE22".equals(mainRiskCode)
					|| "IBW18".equals(mainRiskCode)
					|| "IBE29".equals(mainRiskCode)
					|| "IBW19".equals(mainRiskCode)
					|| "IBW20".equals(mainRiskCode)
					|| "IBW21".equals(mainRiskCode)
					|| "IBW31".equals(mainRiskCode)
					|| "IBW32".equals(mainRiskCode)) {
				ret = "C01010140060";
			} else if ("IBW30".equals(mainRiskCode)
					&& !"01".endsWith(mainCurrency)) {
				ret = "C01010140060";
			}
		} else if ("IRA03".equals(tRiskCode)) {
			if ("IBW00".equals(mainRiskCode) || "IBW11".equals(mainRiskCode)
					|| "IBE12".equals(mainRiskCode)
					|| "IBE19".equals(mainRiskCode)
					|| "IBW02".equals(mainRiskCode)
					|| "IBW08".equals(mainRiskCode)
					|| "IBW14".equals(mainRiskCode)
					|| "IBW13".equals(mainRiskCode)
					|| "IBW04".equals(mainRiskCode)
					|| "IBW05".equals(mainRiskCode)
					|| "IBT06".equals(mainRiskCode)
					|| "IBT15".equals(mainRiskCode)
					|| "IBT12".equals(mainRiskCode)) {
				ret = "C01010010040";
			}
		} else if ("IRA01".equals(tRiskCode)) {
			if ("IBW00".equals(mainRiskCode) || "IBW11".equals(mainRiskCode)
					|| "IBE12".equals(mainRiskCode)
					|| "IBE19".equals(mainRiskCode)
					|| "IBW02".equals(mainRiskCode)
					|| "IBW08".equals(mainRiskCode)
					|| "IBW14".equals(mainRiskCode)
					|| "IBW13".equals(mainRiskCode)
					|| "IBW04".equals(mainRiskCode)
					|| "IBW05".equals(mainRiskCode)
					|| "IBT06".equals(mainRiskCode)
					|| "IBT15".equals(mainRiskCode)
					|| "IBT12".equals(mainRiskCode)) {
				ret = "C01010010040";
			} else if ("IBW15".equals(mainRiskCode)) {
				ret = "C01010090060";
			} else if ("IBC01".equals(mainRiskCode)
					|| "IBC02".equals(mainRiskCode)
					|| "IBE21".equals(mainRiskCode)
					|| "IBE22".equals(mainRiskCode)
					|| "IBE29".equals(mainRiskCode)
					|| "IBW18".equals(mainRiskCode)
					|| "IBW19".equals(mainRiskCode)
					|| "IBW20".equals(mainRiskCode)
					|| "IBW21".equals(mainRiskCode)
					|| "IBW31".equals(mainRiskCode)
					|| "IBW32".equals(mainRiskCode)) {
				ret = "C01010140050";
			} else if ("IBW30".equals(mainRiskCode)
					&& !"01".endsWith(mainCurrency)) {
				ret = "C01010140050";
			} else if ("IBE07".equals(mainRiskCode)
					|| "IBE13".equals(mainRiskCode)
					|| "IBT04".equals(mainRiskCode)
					|| "IBW03".equals(mainRiskCode)
					|| "IBW09".equals(mainRiskCode)
					|| "IBW10".equals(mainRiskCode)
					|| "IBW12".equals(mainRiskCode)
					|| "IBW06".equals(mainRiskCode)
					|| "IBW07".equals(mainRiskCode)) {
				ret = "C01010120010";
			} else if ("IBL03".equals(mainRiskCode)
					|| "IBL00".equals(mainRiskCode)) {
				ret = "C01010140030";
			}
		}

		else if ("IRC02".equals(tRiskCode)) {
			if ("IBW00".equals(mainRiskCode) || "IBW11".equals(mainRiskCode)
					|| "IBE19".equals(mainRiskCode)
					|| "IBW14".equals(mainRiskCode)
					|| "IBW13".equals(mainRiskCode)) {
				ret = "C01010010050";
			} else if ("IBW03".equals(mainRiskCode)
					|| "IBW09".equals(mainRiskCode)
					|| "IBW12".equals(mainRiskCode)
					|| "IBW20".equals(mainRiskCode)
					|| "IBW21".equals(mainRiskCode)
					|| "IBW29".equals(mainRiskCode)) {
				ret = "C01010120030";
			} else if ("IBW12".equals(mainRiskCode)) {
				ret = "C01010140080";
			}			
		}else if ("IRC03".equals(tRiskCode)) {
			if ("IBW32".equals(mainRiskCode)) {
				ret = "C01010141010";
			}  else if ("IBW19".equals(mainRiskCode)) {
				ret = "C01010141011";
			}			
		}else if ("IRC04".equals(tRiskCode)) {
			if ("IBW00".equals(mainRiskCode) || "IBW11".equals(mainRiskCode)
					|| "IBE19".equals(mainRiskCode)
					|| "IBW14".equals(mainRiskCode)
					|| "IBW13".equals(mainRiskCode)) {
				ret = "C01010010050";
			} else if ("IBW03".equals(mainRiskCode)
					|| "IBW09".equals(mainRiskCode)
					|| "IBW12".equals(mainRiskCode)
					|| "IBW20".equals(mainRiskCode)
					|| "IBW21".equals(mainRiskCode)
					|| "IBW29".equals(mainRiskCode)) {
				ret = "C01010120030";
			} else if ("IBW12".equals(mainRiskCode)) {
				ret = "C01010140080";
			}else if ("IBW32".equals(mainRiskCode) 
					|| "IBU09".equals(mainRiskCode) 
					|| "IBT17".equals(mainRiskCode)) {
				ret = "C01010141013";
			}else if ("IBW30".equals(mainRiskCode) 
					|| "IBW31".equals(mainRiskCode)) {
				ret = "C01010141012";
			}			
		}
		
		else if ("IRD01".equals(tRiskCode) || "IRD07".equals(tRiskCode)
				|| "IRD08".equals(tRiskCode)) {
			if ("IBW01".equals(mainRiskCode)) {
				ret = "C01010010060";
			} else if ("IBT00".equals(mainRiskCode)
					|| "IBT02".equals(mainRiskCode)) {
				if (PubFun.checkDate("2004-04-02", mainCValiDate)) {
					ret = "C01010010060";
				} else {
					ret = "C01010080020";
				}
			} else if ("IBT16".equals(mainRiskCode)) {
				ret = "C01010110020";
			}
		} else if ("IRA02".equals(tRiskCode)) {
			ret = "C01010130010";
		} else if ("IRA04".equals(tRiskCode)) {
			ret = "C01010130020";
		} else if ("IBW35".equals(tRiskCode)) {
			ret = "C01010140200";
		} else if ("IBW40".equals(tRiskCode)) {
			ret = "C01010140190";
		} else if ("IBT19".equals(tRiskCode)) {
			ret = "C01010140201";
		} else if ("IBW37".equals(tRiskCode)|| "IBW38".equals(mainRiskCode)|| "IBW39".equals(mainRiskCode)) {
			ret = "C01010143010";			
		} else {
			ret = "";
		}
		System.out.println("mapRIPreceptNo：" + ret);
		return ret;
	}

	private SSRS getPolInfo(String idNO, String operateType) {
		SSRS ret = null;
		if ("01".equals(operateType)) {
			String polSQL = "select distinct a.contno,a.polno,a.riskcode,a.insuredno,a.insuredappage,a.cvalidate,a.currency,a.salechnl,b.planlevel,"
					+ "c.polno,c.riskcode,c.insuredno,c.insuredappage,c.cvalidate,c.currency,c.salechnl,e.planlevel"
					+ " from lcpol a,lcduty b,lcpol c,lcduty e where a.polno=b.polno and c.polno=e.polno and a.mainpolno=c.polno and a.contno='"
					+ idNO + "'";
			ret = new ExeSQL().execSQL(polSQL);
		} else if ("04".equals(operateType)) {
			String polSQL = "select distinct a.contno,a.polno,a.riskcode,a.insuredno,a.insuredappage,a.cvalidate,a.currency,a.salechnl,b.planlevel,"
					+ "c.polno,c.riskcode,c.insuredno,c.insuredappage,c.cvalidate,c.currency,c.salechnl,e.planlevel"
					+ " from lcpol a,lcduty b,lcpol c,lcduty e where a.polno=b.polno and c.polno=e.polno and a.mainpolno=c.polno and a.polno='"
					+ idNO + "'";
			ret = new ExeSQL().execSQL(polSQL);
		}
		return ret;
	}

	/**
	 * 中银再保核保信息
	 * 
	 * @return
	 */
	@Deprecated
	private boolean uwFirstCont2() {
		TransferData tTransferData;
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer contBuffer = new StringBuffer();
		RICalFactorValueSet resultRICalFactorValueSet = new RICalFactorValueSet();
		try {
			contBuffer
					.append(" select distinct a.contno,a.riskcode,b.dutycode,a.polno,a.insuredno from lcpol a,lcduty b where a.polno=b.polno and a.contno = '"
							+ mContNo + "' ");
			System.out.println(" sql: " + contBuffer);
			SSRS contSSRS = new SSRS();
			contSSRS = tExeSQL.execSQL(contBuffer.toString());
			mResult = new VData();

			RICalFactorValueDB tRICalFactorValueDB = new RICalFactorValueDB();
			RIItemCalDB tRIItemCalDB = new RIItemCalDB();

			StringBuffer factorBuffer;
			for (int i = 0; i < contSSRS.getAllData().length; i++) {
				factorBuffer = new StringBuffer();
				factorBuffer
						.append(" select v.Factorid,v.FactorValue,v.Currency,p.Accumulatedefno,(select a.Accumulatemode from Riaccumulatedef a where a.Accumulatedefno=p.Accumulatedefno),v.SerialNo from RICalFactorValue v ,Riprecept p where v.Ripreceptno=p.Ripreceptno and p.State ='01' ");
				factorBuffer
						.append(" and p.arithmeticid in (SELECT rela.arithmeticdefid FROM riarithmeticriskrela rela WHERE rela.riskcode='");
				factorBuffer.append(contSSRS.getAllData()[i][1]);
				factorBuffer
						.append("') and v.Factortype = '01' and v.ValueType = '01'");
				System.out.println(" sql: " + factorBuffer);

				SSRS factorSSRS = new SSRS();
				factorSSRS = tExeSQL.execSQL(factorBuffer.toString());

				for (int j = 0; j < factorSSRS.getAllData().length; j++) {
					factorBuffer = new StringBuffer();
					factorBuffer
							.append("select * from RIItemCal a where a.ArithmeticType='21' and a.Arithmeticid = '");
					factorBuffer.append(factorSSRS.getAllData()[j][0]);
					factorBuffer.append("' ");
					System.out.println(" sql: " + factorBuffer);
					RIItemCalSet tRIItemCalSet = tRIItemCalDB
							.executeQuery(factorBuffer.toString());
					for (int k = 1; k <= tRIItemCalSet.size(); k++) {

						RIItemCalSchema tRIItemCalSchema = tRIItemCalSet.get(k);
						if (tRIItemCalSchema.getItemCalType().equals("1")) {
							// 固定值
						}
						if (tRIItemCalSchema.getItemCalType().equals("2")) {
							// 核保算法sql
						}
						if (tRIItemCalSchema.getItemCalType().equals("3")) {
							// 核保算法类
							tTransferData = new TransferData();
							tTransferData.setNameAndValue("ContNo", mContNo); // 保单号
							tTransferData.setNameAndValue("PolNo",
									contSSRS.getAllData()[i][3]); // 保单险种号
							tTransferData.setNameAndValue("DutyCode",
									contSSRS.getAllData()[i][2]); // 责任编码
							tTransferData.setNameAndValue("InsuredNo",
									contSSRS.getAllData()[i][4]); // 客户号

							tTransferData.setNameAndValue("FactorID",
									factorSSRS.getAllData()[j][0]); // 要素标识
							tTransferData.setNameAndValue("FactorValue",
									factorSSRS.getAllData()[j][1]);// 要素值
							tTransferData.setNameAndValue("Currency",
									factorSSRS.getAllData()[j][2]);// 币种
							tTransferData.setNameAndValue("AccumulateDefNO",
									factorSSRS.getAllData()[j][3]);// 累计风险
							tTransferData.setNameAndValue("AccumulateMode",
									factorSSRS.getAllData()[j][4]);// 累计方式

							VData tVData = new VData();
							tVData.add(tTransferData);
							tVData.add(mRIExchangeRateSet);

							Class tClass = Class.forName(tRIItemCalSchema
									.getCalClass());
							FacuAudit tFacuAudit = (FacuAudit) tClass
									.newInstance();
							tFacuAudit.submitData(tVData, "");

							if (!tFacuAudit.getResult()) {

								tRICalFactorValueDB = new RICalFactorValueDB();
								tRICalFactorValueDB.setSerialNo(factorSSRS
										.getAllData()[j][5]);
								if (!tRICalFactorValueDB.getInfo()) {
									buildError("getInputData", "查询临分核保阀值失败！");
									return false;
								}
								RICalFactorValueSchema tRICalFactorValueSchema = tRICalFactorValueDB
										.getSchema();
								resultRICalFactorValueSet
										.add(tRICalFactorValueSchema);
							}
						}
					}
				}
			}
			if (resultRICalFactorValueSet.size() > 0) {
				mResult.add(resultRICalFactorValueSet);
			}
			for (int i = 1; i <= resultRICalFactorValueSet.size(); i++) {
				System.out.println("getFactorID: "
						+ resultRICalFactorValueSet.get(i).getFactorID()
						+ " getFactorValue "
						+ resultRICalFactorValueSet.get(i).getFactorValue());
			}
		} catch (Exception ex) {
			buildError("getInputData", "调用再保核保核赔失败:" + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 中银再保核保信息
	 * 
	 * @return
	 */
	private boolean uwTwoCont() {
		TransferData tTransferData;
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer contBuffer = new StringBuffer();
		RICalFactorValueSet resultRICalFactorValueSet = new RICalFactorValueSet();
		try {
			contBuffer
					.append(" select distinct a.contno,a.riskcode,b.dutycode,a.polno,a.insuredno from lcpol a,lcduty b where a.polno=b.polno and a.contno = '"
							+ mContNo + "' ");
			System.out.println(" sql: " + contBuffer);
			SSRS contSSRS = new SSRS();
			contSSRS = tExeSQL.execSQL(contBuffer.toString());
			mResult = new VData();

			RICalFactorValueDB tRICalFactorValueDB = new RICalFactorValueDB();
			RIItemCalDB tRIItemCalDB = new RIItemCalDB();

			StringBuffer factorBuffer;
			for (int i = 0; i < contSSRS.getAllData().length; i++) {
				factorBuffer = new StringBuffer();
				factorBuffer
						.append(" select v.Factorid,v.FactorValue,v.Currency,p.Accumulatedefno,(select a.Accumulatemode from Riaccumulatedef a where a.Accumulatedefno=p.Accumulatedefno),v.SerialNo from RICalFactorValue v ,Riprecept p where v.Ripreceptno=p.Ripreceptno and p.State ='01' ");
				factorBuffer
						.append(" and p.arithmeticid in (SELECT rela.arithmeticdefid FROM riarithmeticriskrela rela WHERE rela.riskcode='");
				factorBuffer.append(contSSRS.getAllData()[i][1]);
				factorBuffer
						.append("') and v.Factortype = '01' and v.ValueType = '02' ");
				System.out.println(" sql: " + factorBuffer);

				SSRS factorSSRS = new SSRS();
				factorSSRS = tExeSQL.execSQL(factorBuffer.toString());

				for (int j = 0; j < factorSSRS.getAllData().length; j++) {
					factorBuffer = new StringBuffer();
					factorBuffer
							.append("select * from RIItemCal a where a.ArithmeticType='22' and a.Arithmeticid = '");
					factorBuffer.append(factorSSRS.getAllData()[j][0]);
					factorBuffer.append("' ");
					System.out.println(" sql: " + factorBuffer);
					RIItemCalSet tRIItemCalSet = tRIItemCalDB
							.executeQuery(factorBuffer.toString());
					for (int k = 1; k <= tRIItemCalSet.size(); k++) {

						RIItemCalSchema tRIItemCalSchema = tRIItemCalSet.get(k);
						if (tRIItemCalSchema.getItemCalType().equals("1")) {
							// 固定值
						}
						if (tRIItemCalSchema.getItemCalType().equals("2")) {
							// 核保算法sql
						}
						if (tRIItemCalSchema.getItemCalType().equals("3")) {
							// 核保算法类
							tTransferData = new TransferData();
							tTransferData.setNameAndValue("ContNo", mContNo); // 保单号
							tTransferData.setNameAndValue("PolNo",
									contSSRS.getAllData()[i][3]); // 保单险种号
							tTransferData.setNameAndValue("DutyCode",
									contSSRS.getAllData()[i][2]); // 责任编码
							tTransferData.setNameAndValue("InsuredNo",
									contSSRS.getAllData()[i][4]); // 客户号

							tTransferData.setNameAndValue("FactorID",
									factorSSRS.getAllData()[j][0]); // 要素标识
							tTransferData.setNameAndValue("FactorValue",
									factorSSRS.getAllData()[j][1]);// 要素值
							tTransferData.setNameAndValue("Currency",
									factorSSRS.getAllData()[j][2]);// 币种
							tTransferData.setNameAndValue("AccumulateDefNO",
									factorSSRS.getAllData()[j][3]);// 累计风险
							tTransferData.setNameAndValue("AccumulateMode",
									factorSSRS.getAllData()[j][4]);// 累计方式

							VData tVData = new VData();
							tVData.add(tTransferData);
							tVData.add(mRIExchangeRateSet);

							Class tClass = Class.forName(tRIItemCalSchema
									.getCalClass());
							FacuAudit tFacuAudit = (FacuAudit) tClass
									.newInstance();
							tFacuAudit.submitData(tVData, "");

							if (!tFacuAudit.getResult()) {

								tRICalFactorValueDB = new RICalFactorValueDB();
								tRICalFactorValueDB.setSerialNo(factorSSRS
										.getAllData()[j][5]);
								if (!tRICalFactorValueDB.getInfo()) {
									buildError("getInputData", "查询临分核保阀值失败！");
									return false;
								}
								RICalFactorValueSchema tRICalFactorValueSchema = tRICalFactorValueDB
										.getSchema();
								resultRICalFactorValueSet
										.add(tRICalFactorValueSchema);
							}
						}
					}
				}
			}
			if (resultRICalFactorValueSet.size() > 0) {
				mResult.add(resultRICalFactorValueSet);
			}
			for (int i = 1; i <= resultRICalFactorValueSet.size(); i++) {
				System.out.println("getFactorID: "
						+ resultRICalFactorValueSet.get(i).getFactorID()
						+ " getFactorValue "
						+ resultRICalFactorValueSet.get(i).getFactorValue());
			}
		} catch (Exception ex) {
			buildError("getInputData", "调用再保核保核赔失败:" + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 调用再保合同信息
	 * 
	 * @return
	 */
	private boolean getRICompanyInfo() {

		TransferData tTransferData;
		ExeSQL tExeSQL = new ExeSQL();
		StringBuffer sqlStrBuffer = new StringBuffer();
		try {
			sqlStrBuffer
					.append(" select distinct a.contno,a.riskcode,b.dutycode from lcpol a,lcduty b where a.polno=b.polno and a.contno = '"
							+ mContNo + "' ");
			SSRS paraSSRS = new SSRS();
			paraSSRS = tExeSQL.execSQL(sqlStrBuffer.toString());
			mResult = new VData();

			SSRS tSSRS;
			for (int i = 0; i < paraSSRS.getAllData().length; i++) {
				tSSRS = new SSRS();

				sqlStrBuffer = new StringBuffer();
				sqlStrBuffer
						.append(" select distinct d.Ricontno,d.Incomecompanyno,(select m.Companyname from Ricominfo m where m.Companyno = d.Incomecompanyno),d.Lowerlimit,d.Upperlimit,d.Possessscale from RIRiskDivide d where d.Possessscale<>0 and not exists (select * from RIComInfo com where com.Companyno = d.Incomecompanyno and com.Remark = '01') ");
				sqlStrBuffer
						.append(" and exists (select * from Riprecept r where r.Accumulatedefno in ( select a.Accumulatedefno from RIAccumulateGetDuty a where a.Associatedcode='");
				sqlStrBuffer.append(paraSSRS.getAllData()[i][1]);
				sqlStrBuffer.append("' and a.Getdutycode='");
				sqlStrBuffer.append(paraSSRS.getAllData()[i][2]);
				sqlStrBuffer.append("' )) order by d.Ricontno  ");

				System.out.println(" sql: " + sqlStrBuffer);

				tSSRS = tExeSQL.execSQL(sqlStrBuffer.toString());
				for (int j = 0; j < tSSRS.getAllData().length; j++) {
					tTransferData = new TransferData();
					tTransferData.setNameAndValue("ContNo",
							paraSSRS.getAllData()[i][0]);// 保单号
					tTransferData.setNameAndValue("RiskCode",
							paraSSRS.getAllData()[i][1]);// 险种
					tTransferData.setNameAndValue("DutyCode",
							paraSSRS.getAllData()[i][2]);// 责任编码
					tTransferData.setNameAndValue("ReContCode",
							tSSRS.getAllData()[j][0]); // 再保合同名
					tTransferData.setNameAndValue("ReContName",
							tSSRS.getAllData()[j][1]); // 再保公司编码
					tTransferData.setNameAndValue("ReComName",
							tSSRS.getAllData()[j][2]); // 再保公司名称
					tTransferData.setNameAndValue("UpperLimit",
							tSSRS.getAllData()[j][3]); // 保额下线
					tTransferData.setNameAndValue("LowerLimit",
							tSSRS.getAllData()[j][4]); // 保额上线
					tTransferData.setNameAndValue("AcceptQuota",
							tSSRS.getAllData()[j][5]); // 接受份额
					mResult.add(tTransferData);
				}
			}
		} catch (Exception ex) {
			buildError("getInputData", "调用再保核保核赔失败:" + ex.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 新单个人核保
	 * 
	 * @return boolean
	 */
	public boolean uwInd(LCPolSchema aLCPolSchema) {
		LCDutyDB tLCDutyDB = new LCDutyDB();
		tLCDutyDB.setPolNo(aLCPolSchema.getPolNo());
		LCDutySet tLCDutySet = tLCDutyDB.query();
		mTransferData.setNameAndValue("OpeFlag", "01");
		RIItemCalSchema tRIItemCalSchema = new RIItemCalSchema();
		for (int i = 1; i <= tLCDutySet.size(); i++) {
			mInputData = new VData();
			mInputData.add(aLCPolSchema);
			mInputData.add(tLCDutySet.get(i));
			mInputData.add(this.mLCGrpContSchema);
			mInputData.add(mTransferData);

			mRIPolRecodeChng = new RIPolRecodeChng();
			if (!mRIPolRecodeChng.submitData(mInputData, "14")) {
				buildError("uwInd", "转化数据类型失败");
				return false;
			}
			RIPolRecordSchema tRIPolRecordSchema = new RIPolRecordSchema();
			tRIPolRecordSchema = mRIPolRecodeChng.getResult();
			for (int j = 1; j <= mRIItemCalSet.size(); j++) {
				String tUWFlag = "";
				tRIItemCalSchema = mRIItemCalSet.get(j);
				if (!isPrecept(tRIItemCalSchema, tRIPolRecordSchema)) {
					continue;
				}
				if (tRIItemCalSchema.getItemCalType().equals("1")) {
				}
				if (tRIItemCalSchema.getItemCalType().equals("2")) { // 核保算法sql
					String tCalSql;
					tCalSql = tRIItemCalSchema.getCalSQL();
					if (tCalSql == null && tCalSql.equals("")) {
						buildError("dealData", "没有定义CalSQL");
						return false;
					}
					PubCalculator tPubCalculator = new PubCalculator();
					int tt = tRIPolRecordSchema.getFieldCount();
					for (int k = 0; k < tt; k++) {
						String clumnname = tRIPolRecordSchema.getFieldName(k);
						String clumnvalue = tRIPolRecordSchema.getV(clumnname);
						tPubCalculator.addBasicFactor(clumnname, clumnvalue);
					}
					tPubCalculator.setCalSql(tCalSql);
					tCalSql = tPubCalculator.calculateEx();
					System.out.println(" sql: " + tCalSql);
					ExeSQL tExeSQL = new ExeSQL();
					String temp = tExeSQL.getOneValue(tCalSql);
					System.out.println(" 结果： " + temp);
					if (tExeSQL.mErrors.needDealError()) {
						buildError(
								"dealData",
								" 核保规则: "
										+ tRIItemCalSchema.getArithmeticName()
										+ ",计算项出错 ");
						return false;
					} else if (temp == null || temp.equals("")) {
						buildError(
								"dealData",
								" 核保规则: "
										+ tRIItemCalSchema.getArithmeticName()
										+ ",计算项出错 ");
						return false;
					} else if (temp.equals("1")) {
						// 得到核保结果,记录核保结果
						tUWFlag = temp;
						mUWResult = temp;
					}
				}
				if (tUWFlag.equals("1")) {
					if (!recordUWInfo(tRIPolRecordSchema, tRIItemCalSchema)) {
						buildError(
								"dealData",
								" 核保规则: "
										+ tRIItemCalSchema.getArithmeticName()
										+ " 出错 ");
						return false;
					}
				}
				tUWFlag = null;
			}
		}
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 团体核保 团单核保是将LCGRPCont作为接口表
	 * 
	 * @return boolean
	 */
	public boolean uwGrp() {
		try {
			RILMUWSchema tRILMUWSchema = new RILMUWSchema();
			for (int i = 1; i <= mGrpRILMUWSet.size(); i++) {
				String tUWFlag = "";
				mUWResult = "";
				for (int j = 1; j <= mRIPreceptSet.size(); j++) {
					if (!mGrpRILMUWSet.get(i).getRIPreceptNo()
							.equals(mRIPreceptSet.get(j).getRIPreceptNo())) {
						continue;
					} else {
						tRILMUWSchema = mGrpRILMUWSet.get(i);
						if (tRILMUWSchema.getItemCalType().equals("1")) {
						}
						if (tRILMUWSchema.getItemCalType().equals("2")) { // 核保算法sql
							String tCalSql;
							tCalSql = tRILMUWSchema.getCalSQL();
							if (tCalSql == null && tCalSql.equals("")) {
								buildError("dealData",
										tRILMUWSchema.getRuleName()
												+ "没有定义CalSQL");
								return false;
							}
							PubCalculator tPubCalculator = new PubCalculator();
							int tt = mLCGrpContSchema.getFieldCount();
							for (int k = 0; k < tt; k++) {
								String clumnname = mLCGrpContSchema
										.getFieldName(k);
								String clumnvalue = mLCGrpContSchema
										.getV(clumnname);
								tPubCalculator.addBasicFactor(clumnname,
										clumnvalue);
							}
							tPubCalculator.setCalSql(tCalSql);
							tCalSql = tPubCalculator.calculateEx();
							ExeSQL tExeSQL = new ExeSQL();
							String temp = tExeSQL.getOneValue(tCalSql);
							if (tExeSQL.mErrors.needDealError()) {
								buildError("dealData", " 核保规则: "
										+ tRILMUWSchema.getRuleName()
										+ ",计算项出错 ");
								return false;
							} else if (temp == null || temp.equals("")) {
								buildError("dealData", " 核保规则: "
										+ tRILMUWSchema.getRuleName()
										+ ",计算项出错 ");
								return false;
							} else if (temp.equals("1")) {
								// 核保结果
								tUWFlag = temp;
								mUWResult = temp;
							}
						}
						if (tUWFlag.equals("1")) {
							if (!recordUWInfo(this.mOpeData, tRILMUWSchema)) {
								buildError("dealData", " 核保规则: "
										+ tRILMUWSchema.getRuleName() + " 出错 ");
								return false;
							}
						}
						tUWFlag = null;
					}
				}
			}
			return true;
		} catch (Exception ex) {
			buildError("InitInfo", " RICalEngine_Batch->4 " + ex.getMessage());
			return false;
		}
	}

	/**
	 * 核赔
	 * 
	 * @return boolean
	 */
	public boolean uwClm() {
		if (!deleteUWError()) {
			return false;
		}
		String tSql = "";
		if (DETAILFLAG.equals("01")) { // 到险种
			tSql = "select a.riskcode,a.StandGetMoney,a.ClmGetMoney,a.PolNo,a.InsuredNo,a.InsuredName from RIClaimRiskDetailView a where a.caseno='"
					+ mOpeData + "'  ";
			ExeSQL exeSQL = new ExeSQL();
			SSRS ssrs = exeSQL.execSQL(tSql);
			if (ssrs.getMaxRow() <= 0) {
				return true;
			}
			if (exeSQL.mErrors.needDealError()) {
				buildError("dealData", "理赔查询失败, 理赔号：" + mOpeData);
				return false;
			}
			String[][] result = ssrs.getAllData();
			RIItemCalSchema tRIItemCalSchema = new RIItemCalSchema();
			for (int i = 0; i < result.length; i++) { // 对险种理/赔号循环
				mInputData = new VData();
				mTransferData = new TransferData();
				mTransferData.setNameAndValue("CaseNo", mOpeData);
				mTransferData.setNameAndValue("RiskCode", result[i][0]);
				mTransferData.setNameAndValue("StandPay", result[i][1]);
				mTransferData.setNameAndValue("RealPay", result[i][2]);
				mTransferData.setNameAndValue("PolNo", result[i][3]);
				mTransferData.setNameAndValue("InsuredNo", result[i][4]);
				mTransferData.setNameAndValue("InsuredName", result[i][5]);

				mInputData.add(mTransferData);
				mRIPolRecodeChng = new RIPolRecodeChng();
				if (!mRIPolRecodeChng.submitData(mInputData, "14")) {
					buildError("uwClm", "转化数据类型失败");
					return false;
				}
				RIPolRecordSchema tRIPolRecordSchema = new RIPolRecordSchema();
				tRIPolRecordSchema = mRIPolRecodeChng.getResult();
				for (int j = 1; j <= mRIItemCalSet.size(); j++) {
					String tUWFlag = "";
					tRIItemCalSchema = mRIItemCalSet.get(j);
					if (!isPrecept(tRIItemCalSchema, tRIPolRecordSchema)) {
						continue;
					}
					if (tRIItemCalSchema.getItemCalType().equals("1")) {

					} else if (tRIItemCalSchema.getItemCalType().equals("2")) { // 核赔算法sql
						String tCalSql;
						tCalSql = tRIItemCalSchema.getCalSQL();
						if (tCalSql == null && tCalSql.equals("")) {
							buildError("dealData", "没有定义CalSQL");
							return false;
						}
						PubCalculator tPubCalculator = new PubCalculator();
						int tt = tRIPolRecordSchema.getFieldCount();
						for (int k = 0; k < tt; k++) {
							String clumnname = tRIPolRecordSchema
									.getFieldName(k);
							String clumnvalue = tRIPolRecordSchema
									.getV(clumnname);
							tPubCalculator
									.addBasicFactor(clumnname, clumnvalue);
						}
						tPubCalculator.setCalSql(tCalSql);
						tCalSql = tPubCalculator.calculateEx();
						System.out.println(" sql: " + tCalSql);
						ExeSQL tExeSQL = new ExeSQL();
						String temp = tExeSQL.getOneValue(tCalSql);
						if (tExeSQL.mErrors.needDealError()) {
							buildError("dealData", " 核赔规则: ,计算项出错 ");
							return false;
						} else if (temp == null || temp.equals("")) {
							buildError("dealData", " 核赔规则: ,计算项出错 ");
							return false;
						} else if (temp.equals("1")) {// 得到核保结果,记录核保结果
							tUWFlag = temp;
							mUWResult = temp;
						}
					} else if (tRIItemCalSchema.getItemCalType().equals("3")) {// 核赔算法类
						try {
							Class tClass = Class.forName(tRIItemCalSchema
									.getCalClass());
							RIUWClm tRIUWClm_BF_Life = (RIUWClm) tClass
									.newInstance();
							if (!tRIUWClm_BF_Life.submitData(mInputData, "")) {
								mErrors.copyAllErrors(tRIUWClm_BF_Life
										.getCErrors());
								return false;
							}
							System.out.println(tRIItemCalSchema
									.getArithmeticDefID());
							mUWResult = tRIUWClm_BF_Life.getResult();
							tUWFlag = mUWResult;
						} catch (Exception ex) {
							buildError("dealData",
									"调用理赔自核算法类出错，" + ex.getMessage());
							System.out
									.println("调用理赔自核算法类出错，" + ex.getMessage());
							return false;
						}

					} else {
						buildError("dealData", "未知算法处理类型!");
						return false;
					}
					if (tUWFlag.equals("1")) {
						if (!recordUWInfo(tRIPolRecordSchema, tRIItemCalSchema)) {
							buildError(
									"dealData",
									" 核赔规则: "
											+ tRIItemCalSchema
													.getArithmeticName()
											+ " 出错 ");
							return false;
						}
					}
					tUWFlag = null;
				}
			}
		} else { // 到责任

		}
		return true;
	}

	/**
	 * 保存个人核保信息
	 * 
	 * @return boolean
	 */
	private boolean recordUWInfo(RIPolRecordSchema aRIPolRecordSchema,
			RIItemCalSchema aRIItemCalSchema) {
		RIGUWErrorSchema tRIGUWErrorSchema = new RIGUWErrorSchema();
		tRIGUWErrorSchema.setArithmeticID(aRIItemCalSchema.getArithmeticID());
		tRIGUWErrorSchema.setBatchNo(mBatchNo);
		tRIGUWErrorSchema.setSerialNo(PubFun1.CreateMaxNo("RIGUWERR", 20));
		if (mOpeFlag.equals("14")) { // 理赔
			tRIGUWErrorSchema.setAuditCode(this.mOpeData);
			tRIGUWErrorSchema.setAuditAffixCode("000000");
		} else if (mOpeFlag.equals("13")) { // 保全
			tRIGUWErrorSchema.setAuditCode(aRIPolRecordSchema.getEdorNo());
			tRIGUWErrorSchema.setAuditAffixCode(aRIPolRecordSchema
					.getFeeOperationType());
		} else if (mOpeFlag.equals("11")) { // 新单
			tRIGUWErrorSchema.setAuditCode("000000");
			tRIGUWErrorSchema.setAuditAffixCode("000000");
		}
		tRIGUWErrorSchema.setCalItemType(mOpeFlag);
		tRIGUWErrorSchema.setRuleType("01");
		tRIGUWErrorSchema.setGrpContNo(aRIPolRecordSchema.getGrpContNo());
		tRIGUWErrorSchema.setProposalGrpContNo(aRIPolRecordSchema
				.getProposalGrpContNo());
		tRIGUWErrorSchema.setGrpPolNo(aRIPolRecordSchema.getGrpContNo());
		tRIGUWErrorSchema.setGrpProposalNo(aRIPolRecordSchema.getGrpContNo());
		tRIGUWErrorSchema.setPolNo(aRIPolRecordSchema.getPolNo());
		tRIGUWErrorSchema.setProposalNo(aRIPolRecordSchema.getPolNo());
		tRIGUWErrorSchema.setContPlanCode("");
		tRIGUWErrorSchema.setRiskcode(aRIPolRecordSchema.getRiskCode());
		tRIGUWErrorSchema.setDutyCode(aRIPolRecordSchema.getDutyCode());
		tRIGUWErrorSchema.setInsuredNo(aRIPolRecordSchema.getInsuredNo());
		tRIGUWErrorSchema.setInsuredName(aRIPolRecordSchema.getInsuredName());
		tRIGUWErrorSchema.setUWError(aRIItemCalSchema.getReMark());
		tRIGUWErrorSchema.setMakeDate(PubFun.getCurrentDate());
		tRIGUWErrorSchema.setMakeTime(PubFun.getCurrentTime());
		tRIGUWErrorSchema.setManageCom(this.globalInput.ManageCom);
		tRIGUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tRIGUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		System.out.println("=============:" + mOpeData);
		mMap = new MMap();
		mPubSubmit = new PubSubmit();
		mMap.put(tRIGUWErrorSchema, "INSERT");
		if (!prepareOutputData()) {
			return false;
		}
		if (!mPubSubmit.submitData(this.mInputData, "")) {
			if (mPubSubmit.mErrors.needDealError()) {
				// @@错误处理
				buildError("update", "删除时出现错误!");
				return false;
			}
		}
		mMap = null;
		mPubSubmit = null;
		return true;
	}

	/**
	 * 保存团体核保信息
	 * 
	 * @return boolean
	 */
	private boolean recordUWInfo(String aGrpContNo, RILMUWSchema aRILMUWSchema) {
		RIGUWErrorSchema tRIGUWErrorSchema = new RIGUWErrorSchema();
		tRIGUWErrorSchema.setBatchNo(mBatchNo);
		tRIGUWErrorSchema.setSerialNo(PubFun1.CreateMaxNo("RIGUWERR", 20));
		tRIGUWErrorSchema.setArithmeticID(aRILMUWSchema.getRuleCode());
		tRIGUWErrorSchema.setCalItemType(mOpeFlag);
		tRIGUWErrorSchema.setRuleType(mOpeFlag);
		tRIGUWErrorSchema.setAuditCode("000000");
		tRIGUWErrorSchema.setAuditAffixCode("000000");
		tRIGUWErrorSchema.setGrpContNo(aGrpContNo);
		tRIGUWErrorSchema.setProposalGrpContNo(mLCGrpContSchema.getGrpContNo());
		tRIGUWErrorSchema.setGrpPolNo("000000");
		tRIGUWErrorSchema.setGrpProposalNo("000000");
		tRIGUWErrorSchema.setPolNo("000000");
		tRIGUWErrorSchema.setProposalNo("000000");
		tRIGUWErrorSchema.setContPlanCode(aRILMUWSchema.getContPlanCode());
		tRIGUWErrorSchema.setRiskcode("000000");
		tRIGUWErrorSchema.setDutyCode("000000");
		tRIGUWErrorSchema.setUWError(aRILMUWSchema.getUWDesc());
		tRIGUWErrorSchema.setMakeDate(PubFun.getCurrentDate());
		tRIGUWErrorSchema.setMakeTime(PubFun.getCurrentTime());
		tRIGUWErrorSchema.setManageCom(this.globalInput.ManageCom);
		tRIGUWErrorSchema.setModifyDate(PubFun.getCurrentDate());
		tRIGUWErrorSchema.setModifyTime(PubFun.getCurrentTime());
		mMap = new MMap();
		mPubSubmit = new PubSubmit();
		mMap.put(tRIGUWErrorSchema, "INSERT");
		if (!prepareOutputData()) {
			return false;
		}
		if (!mPubSubmit.submitData(this.mInputData, "")) {
			if (mPubSubmit.mErrors.needDealError()) {
				// @@错误处理
				buildError("update", "删除时出现错误!");
				return false;
			}
		}
		mMap = null;
		mPubSubmit = null;
		return true;
	}

	/**
	 * 判断再保方案
	 * 
	 * @param tIndRILMUWSchema
	 *            RILMUWSchema
	 * @param mRIPolRecordSchema
	 *            RIPolRecordSchema
	 * @return boolean
	 */
	private boolean isPrecept(RIItemCalSchema tRIItemCalSchema,
			RIPolRecordSchema tRIPolRecordSchema) {
		String accumulateDefNo = "";
		RIAccumulateRDCodeSet tRIAccumulateRDCodeSet = new RIAccumulateRDCodeSet();
		// 获取所有与保单或理赔相同的累计险种
		for (int i = 1; i <= mRIAccumulateRDCodeSet.size(); i++) {
			// 测试信息
			// System.out.println(" accumulate: "+mRIAccumulateRDCodeSet.get(i).getAssociatedCode()+" bb: "+mRIPolRecordSchema.getRiskCode());
			if (mRIAccumulateRDCodeSet.get(i).getAssociatedCode()
					.equals(tRIPolRecordSchema.getRiskCode())) {
				tRIAccumulateRDCodeSet.add(mRIAccumulateRDCodeSet.get(i));
			}
		}
		if (tRIAccumulateRDCodeSet.size() == 0) {
			return false;
		}
		// 获取所有与保单或理赔相同的再保方案
		RIPreceptSet tRIPreceptSet = new RIPreceptSet();
		for (int i = 1; i <= tRIAccumulateRDCodeSet.size(); i++) {
			accumulateDefNo = tRIAccumulateRDCodeSet.get(i)
					.getAccumulateDefNO();
			for (int j = 1; j <= this.mRIPreceptSet.size(); j++) {
				// 测试信息
				// System.out.println(" ripreceptNo: "+
				// accumulateDefNo+"  bb: "+mRIPreceptSet.get(j).getAccumulateDefNO());
				if (accumulateDefNo.equals(mRIPreceptSet.get(j)
						.getAccumulateDefNO())) {
					tRIPreceptSet.add(mRIPreceptSet.get(j));
				}
			}
		}
		if (tRIPreceptSet.size() == 0) {
			return false;
		}
		// 判断在再保方案中算法定义是否与此险种的匹配
		for (int i = 1; i <= tRIPreceptSet.size(); i++) {
			if (tRIPreceptSet.get(i).getArithmeticID()
					.equals(tRIItemCalSchema.getArithmeticDefID())) {
				// 测试信息
				// System.out.println(" Arithemetic : "+tRIPreceptSet.get(i).getArithmeticID()+"  bb: "+tRIItemCalSchema.getArithmeticDefID());
				return true;
			}
		}
		return false;
	}

	/**
	 * 在UWError表中只保存最后一次核赔信息 其中： 核赔保存该caseno的最后一次核赔信息
	 * 个单核保保存ProposalContNo的最后一次核保信息 个单保全核保保存EdorNo的ProposalContno的最后一次核保信息
	 * 团单核保保存ProposalGrpContno的最后一次核保信息
	 * 
	 * @return boolean
	 */
	private boolean deleteUWError() {
		String tSql1 = "";
		ExeSQL exeSQL = new ExeSQL();
		if (mOpeFlag == "15") { // 团单核保
			tSql1 = " insert into RIGUWErrorBake select * from RIGUWError a where a.CalItemType='15' and a.GrpContNo='"
					+ mOpeData + "' ";
		} else if (mOpeFlag == "14") { // 再保核赔
			tSql1 = " insert into RIGUWErrorBake select * from RIGUWError a where a.CalItemType='14' and a.AuditCode='"
					+ mOpeData + "' ";
		}
		mMap.put(tSql1, "UPDATE");
		String tSql2 = "";
		if (mOpeFlag == "15") {
			tSql2 = " delete from RIGUWError a where a.CalItemType='15' and a.GrpContNo='"
					+ mOpeData + "' ";
		} else if (mOpeFlag == "14") {
			tSql2 = " delete from RIGUWError a where a.CalItemType='14' and a.AuditCode='"
					+ mOpeData + "' ";
		}
		mMap.put(tSql2, "UPDATE");
		this.mVData.clear();
		this.mVData.add(mMap);
		if (!mPubSubmit.submitData(this.mVData, "")) {
			return false;
		}
		mMap = null;
		mVData = null;
		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AutoTempUW";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}
}


