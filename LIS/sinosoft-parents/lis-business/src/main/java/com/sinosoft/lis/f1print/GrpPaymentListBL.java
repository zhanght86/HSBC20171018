//GrpPaymentListBL.java

package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

import com.f1j.ss.BookModelImpl;
import com.f1j.util.F1Exception;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GrpPaymentListBL {
private static Logger logger = Logger.getLogger(GrpPaymentListBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	// 业务处理相关变量
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String mManageCom = "";
	private String mGrpContNo = "";
	private String mAgentCode = "";
	private String mCvaliDate = "";
	private String mPayEndDate = "";

	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private String operator = "";

	/**
	 * 获取返回信息
	 * 
	 * @return VData
	 */
	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RNContAccGetPrintBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	private boolean getInputData(VData cInputData) {
		// 全局变量

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput == null) {
			buildError("getInputData", "没有得到足够的信息！");
			return false;
		}
		operator = mGlobalInput.Operator;
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mManageCom = (String) mTransferData.getValueByName("ManageCom"); // 获得管理机构
		mGrpContNo = (String) mTransferData.getValueByName("GrpContNo"); // 获得团单号
		mAgentCode = (String) mTransferData.getValueByName("AgentCode");// 获得代理人号码
		mCvaliDate = (String) mTransferData.getValueByName("CvaliDate");// 获得交费始期
		mPayEndDate = (String) mTransferData.getValueByName("PayEndDate");// 获得交费止期

		return true;
	}

	// 查询符合条件的业务员及其信息,将结果放在一个SSRS中
	private SSRS getAgentInf() {
		logger.debug("getAgentInf() beginning");
		SSRS nSSRS = new SSRS();
		ExeSQL nExeSQL = new ExeSQL();

		// String strSql = " Select lp.ManageCom, lp.AgentCom, lp.AgentType,
		// lp.AgentGroup, lp.AgentCode, la.name "
		// + " from Lcgrppol lp, Laagent la "
		// + " where lp.ManageCom = '"+ mManageCom +"' and lp.CvaliDate =
		// '"+mCvaliDate+"' and "
		// + " lp.PayEndDate = '"+mPayEndDate+"' and lp.AgentCode =
		// la.AgentCode";
		String strSql = " Select distinct lp.ManageCom, lp.AgentCom, lp.AgentType, lp.AgentGroup, lp.AgentCode "
				+ " from Lcgrppol lp "
				+ " where lp.ManageCom like '"
				+ mManageCom
				+ "%' and lp.PaytoDate between '"
				+ mCvaliDate
				+ "' and " + "'" + mPayEndDate + "'";

		if ((mGrpContNo == null || (mGrpContNo.equals("")))
				&& (mAgentCode != null && (!mAgentCode.equals("")))) {
			strSql = strSql + " and lp.AgentCode = '" + mAgentCode + "'";
		} else if ((mGrpContNo != null && (!mGrpContNo.equals("")))
				&& (mAgentCode == null || (mAgentCode.equals("")))) {
			strSql = strSql + " and lp.GrpContNo = '" + mGrpContNo + "'";
		} else if ((mGrpContNo != null && (!mGrpContNo.equals("")))
				&& (mAgentCode != null && (!mAgentCode.equals("")))) {
			strSql = strSql + " and lp.GrpContNo = '" + mGrpContNo
					+ "' and lp.AgentCode = '" + mAgentCode + "'";
		}

		strSql = strSql + " ORDER BY lp.AgentCode ";

		nSSRS = nExeSQL.execSQL(strSql);

		return nSSRS;
	}

	// 查询业务员下的符合条件的保单
	private Vector getContInf(SSRS vSSRS) {
		logger.debug("getContInf() beginning");
		Vector va = new Vector();// 保存所有的代理人

		String contSQL = "";
		SSRS contSSRS = new SSRS();
		ExeSQL contExeSQL = new ExeSQL();

		for (int i = 0; i < vSSRS.MaxRow; i++) {
			String mAgentCode = vSSRS.GetText(i + 1, 5);// 提取一个代理人号码；
			Vector vc = new Vector();// 保存一个代理人信息

			contSQL = " Select distinct lp.AgentCode, lp.GrpContNo, lj.CurPayToDate, lp.GrpName, lp.peoples2, lca.postalAddress,"
					+ " lca.phone, lca.Zipcode, lj.payCount"
					+ " from Lcgrppol lp, ljapaygrp lj ,Lcgrpappnt lca"
					+ " where lp.GrpContNo = lj.GrpContNo and lp.GrpContNo = lca.GrpContNo "
					+ " and lp.AgentCode = '"
					+ mAgentCode
					+ "' and lp.paytodate between '"
					+ mCvaliDate
					+ "' and '"
					+ mPayEndDate + "'";

			if (mGrpContNo != null && (!mGrpContNo.equals(""))) {
				contSQL = contSQL + " and lp.GrpContNo = '" + mGrpContNo + "'";
			}

			contSQL = contSQL + "  ORDER BY lj.CurPayToDate ,lp.GrpContNo";

			contSSRS = contExeSQL.execSQL(contSQL);
			vc.add(0, mAgentCode);
			vc.add(1, contSSRS);

			va.add(vc);
		}
		return va;
	}

	// 获得每份保单的险种具体信息
	private Vector getRiskInf(Vector vVector) {
		logger.debug("getRiskInf() beginning");
		Vector vm = new Vector();// 存放所有的代理人。

		for (int i = 0; i < vVector.size(); i++) {
			Vector vt = (Vector) vVector.get(i);// 提取一个代理人
			String tAgentCode = (String) vt.get(0);// 提取代理人号码
			SSRS tSSRS = (SSRS) vt.get(1);// 提取代理人下的保单信息

			Vector va = new Vector();// 保存一个代理人信息
			Vector vc = new Vector();// 存放一个代理人下所有的合同。

			for (int j = 0; j < tSSRS.MaxRow; j++) {
				Vector vr = new Vector();// 保存一个合同，第一位存放保单号，第二位存放保单信息的SRSS
				String vGrpContNo = tSSRS.GetText(j + 1, 2);// 存放合同号
				String vLastPayToDate = tSSRS.GetText(j + 1, 3);
				SSRS rSSRS = new SSRS();// 存放合同的具体信息

				String riskSQL = "";
				ExeSQL riskExeSQL = new ExeSQL();

				riskSQL = " Select distinct lm.riskcode, lp.prem ,lpc.bankAccNo,(select Trim(codename) from ldcode where  codetype ='paymode' And code=lpc.getflag) "
						+ " from Lmriskapp lm, LcGrppol lp, lcgrpcont lpc"
						+ " where lp.riskcode = lm.riskcode and lp.GrpContNo = lpc.GrpContNo and lp.grpContNo = '"
						+ vGrpContNo + "' ";
				rSSRS = riskExeSQL.execSQL(riskSQL);

				vr.add(0, vGrpContNo);
				vr.add(1, rSSRS);
				vr.add(2, vLastPayToDate);
				vc.add(vr);
			}
			va.add(0, tAgentCode);
			va.add(1, vc);
			vm.add(va);
		}

		return vm;
	}

	// 保单分析；
	private Vector parseRiskInf(Vector vVector) {
		logger.debug("parseRiskInf() beginning");
		Vector vrs = new Vector();// 提取从外部获得的Vector
		Vector allAgentVector = new Vector();// 保存所有的代理人

		for (int i = 0; i < vVector.size(); i++) {
			vrs = (Vector) vVector.get(i);// 得到一个代理人
			String agentcode = (String) vrs.get(0);// 提取一个代理人编号；
			Vector va = (Vector) vrs.get(1);// 提取vrs中，代理人的所有保单

			Vector agentVector = new Vector();// 保存一个代理人信息
			Vector allContVector = new Vector();// 保存每个代理人下的所有保单

			for (int j = 0; j < va.size(); j++) {
				Vector vc = (Vector) va.get(j);// 提取代理人的一个具体的保单
				String contNo = (String) vc.get(0);// 提取每个保单的保单号
				SSRS vSSRS = (SSRS) vc.get(1);// 提取每个保单的险种信息
				String lastPayToDate = (String) vc.get(2);

				Vector contVector = new Vector();// 保存每个险种的保单
				Vector mRiskVector = new Vector();// 保存每一个保单下的主险信息

				double subprem = 0;// 总保费合计；
				String ssubprem = "";
				String tprem = "";// 记录保费
				String tRiskCode = "";// 记录险种
				double mprem = 0;// 主险保费合计
				String smprem = "";// 保存主险保费合计
				double sprem = 0;// 附险保费合计
				String ssprem = "";// 保存附险保费合计

				for (int k = 0; k < vSSRS.MaxRow; k++) {

					String parFlag = "";
					String strParseSQL = "";
					ExeSQL parExeSQL = new ExeSQL();
					SSRS pSSRS = new SSRS();

					tRiskCode = vSSRS.GetText(k + 1, 1);
					tprem = vSSRS.GetText(k + 1, 2);
					strParseSQL = " Select distinct lm.subRiskFlag, lm.riskname"
							+ " From Lmriskapp lm"
							+ " Where lm.riskcode = '"
							+ tRiskCode + "' ";
					pSSRS = parExeSQL.execSQL(strParseSQL);
					parFlag = pSSRS.GetText(1, 1);

					if (parFlag.equals("M")) {
						String[] mRisk = new String[3];
						mRisk[0] = tRiskCode;// 主险编号；
						mRisk[1] = pSSRS.GetText(1, 2);// 主险名称
						mRisk[2] = tprem;// 主险保费
						mRiskVector.add(mRisk);
						// 计算主险保费合计
						mprem += Double.parseDouble(tprem);

					} else if (parFlag.equals("S")) {
						// 计算附险保费合计
						sprem += Double.parseDouble(tprem);
					}
				}
				smprem = Double.toString(mprem);
				ssprem = Double.toString(sprem);
				// 计算总保费合计
				subprem = mprem + sprem;
				ssubprem = Double.toString(subprem);

				mRiskVector.add(smprem);
				contVector.add(0, contNo);
				contVector.add(1, vSSRS);
				contVector.add(2, mRiskVector);
				contVector.add(3, ssprem);
				contVector.add(4, ssubprem);
				contVector.add(5, lastPayToDate);

				allContVector.add(contVector);
			}

			agentVector.add(0, agentcode);
			agentVector.add(1, allContVector);
			allAgentVector.add(agentVector);
		}

		return allAgentVector;

	}

	private boolean getPrintData() {

		// 根据条件查询业务员；
		SSRS tSSRS = getAgentInf();
		if (tSSRS.MaxRow <= 0) {
			System.err.println("在getPrintData()!");
			buildError("getPrintData", "打印失败，没得到有效数据！");
			return false;
		}
		// 业务员下符合条件的的保单；
		Vector allCont = getContInf(tSSRS);
		if (allCont.size() == 0) {
			System.err.println("getContInf()!");
			buildError("getPrintData", "打印失败，没得到有效数据！");
			return false;
		}
		// 获得保单具体信息
		Vector riskInf = getRiskInf(allCont);
		if (riskInf.size() == 0) {
			System.err.println("getContInf()!");
			buildError("getPrintData", "打印失败，没得到有效数据！");
			return false;
		}

		// 分析所有保单
		Vector parVect = parseRiskInf(riskInf);

		// 打印数据按业务员进行分组
		for (int i = 0; i < parVect.size(); i++) {

			double sum = 0;
			int num = 0;

			// 新建一个TextTag的实例
			TextTag texttag = new TextTag();
			// 新建一个XmlExport的实例
			XmlExport xmlexport = new XmlExport();

			// 业务员所有业务信息；
			int count = 14;
			String[] cols = null;
			ListTable aListTable = new ListTable();

			String[] strTitle = new String[14];

			// 每个业务员信息（单值）
			// 得到一个具体的业务员
			Vector aAgent = (Vector) parVect.get(i);

			// 得到业务员号
			String agentCode = (String) aAgent.get(0);
			// 其他信息
			int infLine = 0;
			for (int j = 1; j <= tSSRS.MaxRow; j++) {
				if (tSSRS.GetText(j, 5).equals(agentCode)) {
					infLine = j;
					break;
				}
			}
			String listDate = CurrentDate;
			String manageCom = mManageCom;

			// String agentName = tSSRS.GetText(infLine, 6);
			String agentGroup = tSSRS.GetText(infLine, 4);
			String agentType = tSSRS.GetText(infLine, 3);
			String agentCom = tSSRS.GetText(infLine, 2);

			texttag.add("listDate", listDate);
			// texttag.add("manageCom", manageCom);
			// ///////////add by
			// yinxiaoyi20070330业务需要显示中文名字//////////////////////////
			SSRS fSSRS = new SSRS();
			ExeSQL exeSql = new ExeSQL();
			String tSql = "select name from ldcom where comcode ='" + manageCom
					+ "'";
			fSSRS = exeSql.execSQL(tSql);
			if (fSSRS.MaxRow > 0) {
				texttag.add("manageCom", fSSRS.GetText(1, 1));
			} else {
				texttag.add("manageCom", "");
			}

			tSql = "select name From LABranchGroup where agentgroup='"
					+ agentCom + "'";
			fSSRS = exeSql.execSQL(tSql);
			if (fSSRS.MaxRow > 0) {

				texttag.add("agentCom", fSSRS.GetText(1, 1));
			} else {
				texttag.add("agentCom", "");
			}

			tSql = "select name From LABranchGroup where agentgroup='"
					+ agentType + "'";
			fSSRS = exeSql.execSQL(tSql);
			if (fSSRS.MaxRow > 0) {

				texttag.add("agentType", fSSRS.GetText(1, 1));
			} else {
				texttag.add("agentType", "");
			}

			tSql = "select name From LABranchGroup where agentgroup='"
					+ agentGroup + "'";
			fSSRS = exeSql.execSQL(tSql);
			if (fSSRS.MaxRow > 0) {

				texttag.add("agentGroup", fSSRS.GetText(1, 1));
			} else {
				texttag.add("agentGroup", "");
			}

			tSql = "Select Name From Laagent Where agentcode='" + agentCode
					+ "'";
			fSSRS = exeSql.execSQL(tSql);
			if (fSSRS.MaxRow > 0) {

				texttag.add("agentName", fSSRS.GetText(1, 1));
			} else {
				texttag.add("agentName", "");
			}
			// /////////////////////end
			// 20070330/////////////////////////////////////////////////////////
			// texttag.add("agentType", agentType);
			// texttag.add("agentGroup", agentGroup);
			texttag.add("agentCode", agentCode);
			// texttag.add("agentCom", agentCom);
			// texttag.add("agentName", agentName);

			// 每个业务员负责的保单信息(多值)，每个主险险种确定一组值
			// 得到该业务员所有的保单
			Vector aAllCont = (Vector) aAgent.get(1);
			// 具体到每一份保单
			for (int k = 0; k < aAllCont.size(); k++) {
				// 得到一份保单
				Vector cCont = (Vector) aAllCont.get(k);
				// 提取保单号
				String cContNo = (String) cCont.get(0);
				String lLastPayToDate = (String) cCont.get(5);
				// 提取保单信息
				// 从allCont中得到所有的代理人

				for (int x = 0; x < allCont.size(); x++) {
					// 得到一个代理人
					Vector vVa = (Vector) allCont.get(x);
					// 得到该代理人下的保单
					SSRS contSSRS = (SSRS) vVa.get(1);
					// 查找保单，并提取信息

					for (int y = 1; y <= contSSRS.MaxRow; y++) {

						if (contSSRS.GetText(y, 2).equals(cContNo)
								&& contSSRS.GetText(y, 3)
										.equals(lLastPayToDate)) {
							// Select lp.AgentCode, lp.GrpContNo,
							// lj.LastPayToDate, lp.GrpName, lp.peoples2,
							// lca.postalAddress,"
							// + " lca.phone, lca.Zipcode, lj.payCount"
							cols = new String[count];

							cols[1] = lLastPayToDate;

							String gGrpName = contSSRS.GetText(y, 4);
							cols[2] = gGrpName;

							String pPeopes = contSSRS.GetText(y, 5);
							cols[3] = pPeopes;

							String pPostalAddress = contSSRS.GetText(y, 6);
							cols[4] = pPostalAddress;

							String pPhone = contSSRS.GetText(y, 7);
							cols[5] = pPhone;

							String zZipCode = contSSRS.GetText(y, 8);
							cols[6] = zZipCode;

							String pPayCount = contSSRS.GetText(y, 9);
							cols[11] = pPayCount;

							break;
						}
					}
				}
				// 提取险种信息
				SSRS rSSRS = (SSRS) cCont.get(1);
				// 提取附加险信息
				// 提取附加险保费合计
				String sSprem = (String) cCont.get(3);
				// 提取总保费合计
				String sSubPrem = (String) cCont.get(4);

				// 分析险种
				for (int m = 1; m <= rSSRS.getMaxRow(); m++) {

					// 提取一个险种号，以后判断
					String rRiskCode = rSSRS.GetText(m, 1);
					// 提取银行账号
					String bBankAccNo = rSSRS.GetText(m, 3);
					// 提取付款方式
					String pPayMode = rSSRS.GetText(m, 4);

					// 判断是否主险，如果是提取主险信息
					Vector mMainRiskVector = (Vector) cCont.get(2);
					for (int n = 0; n < mMainRiskVector.size() - 1; n++) {
						String[] mMainRisk = (String[]) mMainRiskVector.get(n);
						if (rRiskCode.equals(mMainRisk[0])) {
							// 该号是主险，提取信息
							// 提取主险号
							String mMainRiskCode = rRiskCode;
							// 提取主险名称
							String mMainRiskName = mMainRisk[1];
							// 提取主险保费
							String mMainRiskPrem = mMainRisk[2];
							// 提取主险保费合计
							String sSubMainRiskPrem = (String) mMainRiskVector
									.lastElement();

							// 添加信息到数组

							cols[0] = cContNo;
							cols[8] = new DecimalFormat("0.00").format(Double
									.parseDouble(mMainRiskPrem));
							cols[9] = new DecimalFormat("0.00").format(Double
									.parseDouble(sSprem));
							cols[10] = new DecimalFormat("0.00").format(Double
									.parseDouble(sSubPrem));
							cols[7] = rRiskCode;
							cols[12] = pPayMode;
							cols[13] = bBankAccNo;

							sum = sum + Double.parseDouble(cols[10]);// 计算总保费
							num++;// 计算件数

							aListTable.setName("RISK");
							aListTable.add(cols);
							break;
						}

					}

				}

			}

			xmlexport = new XmlExport();
			// 最好紧接着就初始化xml文档
			xmlexport.createDocument("GrpPaymentList.vts", "PRINT");
			xmlexport.addListTable(aListTable, strTitle);
			String strSum;
			strSum = new DecimalFormat("0.00").format(sum);
			texttag.add("sum", strSum);
			texttag.add("num", num);
			texttag.add("operator", operator);
			xmlexport.addTextTag(texttag);

			mResult.addElement(xmlexport);
			logger.debug("!!!");

		}

		return true;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		if (!cOperate.equals("PRINT")) {
			buildError("submitData", "不支持的操作字符串");
			return false;
		}

		if (!getInputData(cInputData)) {
			return false;
		}
		mResult.clear();

		if (!getPrintData()) {
			return false;
		}
		return true;
	}

	public static void main(String[] arg) {
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "86";
		tGI.ManageCom = "86";
		tGI.Operator = "001";

		// 传递非SCHEMA信息
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("CvaliDate", "2006-09-08");
		tTransferData.setNameAndValue("PayEndDate", "2007-02-08");
		tTransferData.setNameAndValue("ManageCom", "86210000");
		tTransferData.setNameAndValue("AgentCode", "00000057");
		// tTransferData.setNameAndValue("GrpContNo", "880000005458");

		VData tVData = new VData();
		tVData.addElement(tGI);
		tVData.addElement(tTransferData);

		GrpPaymentListBL tGrpPaymentListBL = new GrpPaymentListBL();
		if (tGrpPaymentListBL.submitData(tVData, "PRINT")) {
			VData RResult = new VData();
			RResult = tGrpPaymentListBL.getResult();
			String[] strVFPathName = new String[RResult.size()]; // 临时文件个数。
			for (int k = 0; k < RResult.size(); k++) {
				XmlExport txmlExport = new XmlExport();
				txmlExport = (XmlExport) RResult.getObjectByObjectName(
						"XmlExport", k);
				CombineVts tcombineVts = null;
				String strTemplatePath = "D:/lis/ui/f1print/NCLtemplate/";
				tcombineVts = new CombineVts(txmlExport.getInputStream(),
						strTemplatePath);
				ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
				tcombineVts.output(dataStream);
				strVFPathName[k] = "D:/lis/ui/vtsfile/001_" + k + "_"
						+ "GrpPaymentList.vts";
				AccessVtsFile.saveToFile(dataStream, strVFPathName[k]);
			}
			VtsFileCombine vtsfilecombine = new VtsFileCombine();
			try {
				BookModelImpl tb = vtsfilecombine.dataCombine(strVFPathName);
				vtsfilecombine.write(tb,
						"D:/lis/ui/vtsfile/001_newGrpPaymentList.vts");
			} catch (IOException ex) {
			} catch (F1Exception ex) {
			} catch (Exception ex) {
			}

		}

	}
}
