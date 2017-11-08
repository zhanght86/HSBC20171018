/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.agentprint;
import org.apache.log4j.Logger;

import com.sinosoft.lis.f1print.PrintService;
import com.sinosoft.lis.pubfun.GlobalInput;
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
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liuyin
 * @version 1.0
 */
public class TollBussDisBL implements PrintService {
private static Logger logger = Logger.getLogger(TollBussDisBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 取得查询条件中管理机构的值 */
	private String mManageCom;
	private String mBranchAttr;
	private String mAgentCode;
	private String mStartDate;
	private String mEndDate;
	private String mTolledState;
	private String mBatchFlag = "0";
	private String mPrinter = "";
	private String mRiskChnl = "";// 险种渠道
	private String mRiskChnlName = "";// 险种渠道名字

	private int sum = 0;

	private XmlExport tXmlExport;
	private XmlExport mXmlExportAll = new XmlExport();
	public static final String VTS_NAME = "TollBussDis.vts"; // 模板名称

	public TollBussDisBL() {
	}

	public static void main(String[] args) {
		GlobalInput tGI = new GlobalInput();
		tGI.ManageCom = "8621";
		tGI.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ManageCom", "86210099");
		// tTransferData.setNameAndValue("BranchAttr", "");
		tTransferData.setNameAndValue("AgentCode", "");
		tTransferData.setNameAndValue("StartDate", "2006-03-02");
		tTransferData.setNameAndValue("EndDate", "2006-03-02");
		tTransferData.setNameAndValue("BatchFlag", "1");
		tTransferData.setNameAndValue("Printer", "Zan");

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tGI);

		TollBussDisBL tTollBussDisBL = new TollBussDisBL();
		if (!tTollBussDisBL.submitData(tVData, "Print")) {
			logger.debug(tTollBussDisBL.mErrors.getFirstError()
					.toString());
		}
		logger.debug("---------------end--------------");
	}

	public boolean submitData(VData cInputData, String cOperate) {
		/** 得到从外部传来的数据，并备份到本类中 */
		if (!getInputData(cInputData)) {
			return false;
		}

		mResult.clear();

		// 准备所有要打印的数据
		if (!queryData()) {
			return false;
		}
		return true;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}

	private boolean getInputData(VData cInputData) {
		/** 保存从外部传来的管理机构的信息，作为查询条件 */
		TransferData tTD = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mManageCom = (String) tTD.getValueByName("ManageCom"); // 管理机构
		if (mManageCom == null || mManageCom.equals("")) {
			mManageCom = mGlobalInput.ManageCom;
		}
		mBranchAttr = (String) tTD.getValueByName("BranchAttr");// 销售机构
		mAgentCode = (String) tTD.getValueByName("AgentCode"); // 新服务人员
		mStartDate = (String) tTD.getValueByName("StartDate"); // 开始日期
		mEndDate = (String) tTD.getValueByName("EndDate"); // 截至日期
		mTolledState = (String) tTD.getValueByName("TolledState");
		mPrinter = (String) tTD.getValueByName("Printer");
		mRiskChnl = (String) tTD.getValueByName("RiskChnl");// 产品渠道

		if (mAgentCode == null || mAgentCode.trim().equals(""))
			mBatchFlag = "1";
		else
			mBatchFlag = "0";

		if (mRiskChnl == null || mRiskChnl.equals("")) {
			mRiskChnlName = "";
		} else if (mRiskChnl.equals("1")) {
			mRiskChnl = "I";
			mRiskChnlName = "个险";
		} else if (mRiskChnl.equals("5")) {
			mRiskChnl = "T";
			mRiskChnlName = "电话直销";
		}

		return true;
	}

	/**
	 * 
	 * @return boolean
	 */
	private boolean queryData() {
		double RiskFee = 0; // 保费合计
		int Count = 0; // 件数合计
		int EFTCount = 0; // EFT件数
		int ServeCount = 0; // 上门件数
		int PersonCount = 0; // 自缴件数

		mXmlExportAll.createDocuments(mPrinter, mGlobalInput);
		mXmlExportAll.setTemplateName(VTS_NAME);

		// 查询机构所有在职收费员(包括业务员兼职收费员)
		String AgentSQL = "";

		AgentSQL = "select a.agentcode,b.branchattr from laagent a,labranchgroup b where a.managecom like concat('"
				+ "?mManageCom?"
				+ "','%') and a.agentstate <= '02' and a.branchtype='4' and a.branchcode=b.agentgroup";
		if (mAgentCode != null && !mAgentCode.equals("")) {
			AgentSQL += " and a.agentcode='" + "?agentcode?" + "'";
		}
		if (mBranchAttr != null && !mBranchAttr.equals("")) {
			AgentSQL += " and b.branchattr like concat('" + "?mBranchAttr?" + "','%')";
		}
		AgentSQL += " union select a.agentcode,b.branchattr from laagentollquaf a,labranchgroup b "
				+ "where a.managecom like concat('"
				+ "?mManageCom?"
				+ "','%') and a.tollstate <> '2'"
				+ " and a.tollflag = '2' and a.branchcode=b.agentgroup ";
		if (mAgentCode != null && !mAgentCode.equals("")) {
			AgentSQL += " and a.agentcode='" + "?agentcode?" + "'";
		}
		if (mBranchAttr != null && !mBranchAttr.equals("")) {
			AgentSQL += " and b.branchattr like concat('" + "?mBranchAttr?" + "','%')";
		}

		AgentSQL += " order by branchattr,agentcode";
       SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
       sqlbv1.sql(AgentSQL);
       sqlbv1.put("mManageCom", mManageCom);
       sqlbv1.put("agentcode", mAgentCode);
       sqlbv1.put("mBranchAttr", mBranchAttr);
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tAgentSSRS = new SSRS();
		tAgentSSRS = tExeSQL.execSQL(sqlbv1);
		if (tAgentSSRS == null || tAgentSSRS.getMaxRow() == 0) {
			CError tError = new CError();
			tError.moduleName = "TollBussDisBL";
			tError.functionName = "queryData";
			tError.errorMessage = "管理机构" + mManageCom + "不存在满足条件的在职收费员!";
			this.mErrors.addOneError(tError);
			return false;
		}
		for (int index = 1; index <= tAgentSSRS.getMaxRow(); index++) {
			mAgentCode = tAgentSSRS.GetText(index, 1);
			String strSql = "";
			// 查询收费员基本信息
			strSql = "select a.name,b.name,c.name from laagent a, labranchgroup b, ldcom c "
					+ "where a.branchcode = b.agentgroup and a.managecom = trim(c.comcode) "
					+ "and a.agentcode ='" + "?agentcode1?" + "'";
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		       sqlbv2.sql(strSql);
		       sqlbv2.put("agentcode1", mAgentCode);
			tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(sqlbv2);

			if (tSSRS == null || tSSRS.getMaxRow() == 0) {
				CError tError = new CError();
				tError.moduleName = "TollBussDisBL";
				tError.functionName = "queryData";
				tError.errorMessage = "未查到收费员" + mAgentCode + "的基本信息!";
				this.mErrors.addOneError(tError);
				return false;
			}

			String tAgentName = tSSRS.GetText(1, 1);
			String tAgentBranchName = tSSRS.GetText(1, 2);
			String tManageComName = tSSRS.GetText(1, 3);
			// logger.debug("mTolledState"+
			// mTolledState.length()+mTolledState);
			// 查询统计起期和止期内收费员维护的保单
			logger.debug("**********=====> " + mRiskChnl
					+ " <=====**********");
			/*"Oracle：select nvl(sum(prem),0) from ljtempfee_lmriskapp
			改造为：select (case when sum(prem) is not null then sum(prem)  else 0 end)  from ljtempfee_lmriskapp;"
            */
			strSql = "select a.grpcontno,a.StartSerDate,b.agentcode,(select name from laagent where agentcode=b.agentcode), "
					+ " (select (case when sum(StandPrem) is not null then sum(StandPrem) else 0 end) from lcprem where contno=a.grpcontno and PayPlanType='01' and substr(PayPlanCode,1,6)='000000')," // 健康加费
					+ " (select (case when sum(StandPrem) is not null then sum(StandPrem) else 0 end) from lcprem where contno=a.grpcontno and PayPlanType='02' and substr(PayPlanCode,1,6)='000000')," // 职业加费
					+ " c.InsuredBirthday,a.relationship,a.StartSerDate"
					+ " from lacommisiondetail a ,lacommisiondetailb b,lccont c where a.StartSerDate<='"
					+ "?StartSerDate?"
					+ "' and a.StartSerDate>='"
					+ "?StartSerDate2?"
					+ "' and a.poltype='1' "
					+ " and a.agentcode='"
					+ "?StartSerDate3?"
					+ "' and a.grpcontno=b.grpcontno"
					+ " and a.agentcode<>b.agentcode and a.maketime=b.maketime"
					+ " and a.startserdate=b.endserdate and a.grpcontno=c.contno ";
			if (mTolledState != null && mTolledState.trim().equals("Y")) {
				strSql += " and c.payintv='-1'";
			} else if (mTolledState != null && mTolledState.trim().equals("N")) {
				strSql += " and c.payintv <>'-1'";
			}
			if (mRiskChnl != null && !mRiskChnl.equals("")) {
				strSql += " and exists (select '1' from lcpol lc, lmriskapp lm where a.grpcontno = lc.contno and lc.polno = lc.mainpolno and lc.riskcode = lm.riskcode and lm.mngcom = '"
						+ "?date?" + "')";
				
			}
			
			// strSql += " union select
			// a.grpcontno,a.StartSerDate,b.agentcode,(select name from laagent
			// where agentcode=b.agentcode), "
			// +" (select nvl(sum(StandPrem),0) from lcprem where
			// contno=a.grpcontno and PayPlanType='01' and
			// substr(PayPlanCode,1,6)='000000')," //健康加费
			// +" (select nvl(sum(StandPrem),0) from lcprem where
			// contno=a.grpcontno and PayPlanType='02' and
			// substr(PayPlanCode,1,6)='000000')," //职业加费
			// +" c.InsuredBirthday,a.relationship,a.StartSerDate"
			// + " from lacommisiondetailb a, lacommisiondetailb b,lccont c
			// where a.StartSerDate<='" +
			// mEndDate
			// + "' and a.StartSerDate>='" + mStartDate +
			// "' and a.EndSerDate>='" + mEndDate
			// + "' and a.poltype='1' and a.agentcode='" + mAgentCode
			// + "' and a.grpcontno=b.grpcontno and a.startserdate=b.endserdate
			// and a.grpcontno=c.contno";
			// if (mTolledState != null && mTolledState.trim().equals("Y"))
			// {
			// strSql += " and c.payintv='-1'";
			// }
			// else if (mTolledState != null && mTolledState.trim().equals("N"))
			// {
			// strSql += " and c.payintv <>'-1'";
			// }
			strSql += " order by appflag,(select state from lccontstate s,lcpol pol"
					+ " where pol.polno=pol.mainpolno and pol.mainpolno=s.polno and pol.contno=c.contno and statetype='Available'"
					+ " and s.enddate is null and s.startdate<'"
					+ "?StartSerDate?"
					+ "'),c.appntno,grpcontno";
		
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		       sqlbv3.sql(strSql);
		       sqlbv3.put("StartSerDate", mEndDate);
		       sqlbv3.put("StartSerDate2", mStartDate);
		       sqlbv3.put("StartSerDate3", mAgentCode);
		       sqlbv3.put("date", mRiskChnl);
			ExeSQL tContExeSQL = new ExeSQL();
			SSRS tContSSRS = new SSRS();
			tContSSRS = tContExeSQL.execSQL(sqlbv3);

			int t = tContSSRS.getMaxRow();
			logger.debug("===========NOW ROWS:" + t);
			sum += t;
			logger.debug("===============NOW TOTAL ROWS:" + sum);

			if (tContSSRS == null || tContSSRS.getMaxRow() == 0) {
				logger.debug("未查到收费员" + mAgentCode + "分配的满足条件的保单信息");
				// continue;

				CError tError = new CError();
				tError.moduleName = "TollBussDisBL";
				tError.functionName = "queryData";
				tError.errorMessage = "未查到收费员" + mAgentCode + "分配的满足条件的保单信息";
				this.mErrors.addOneError(tError);
				return false;
			} else {
				logger.debug("总算有满足条件的人啦!!");
				ListTable tlistTable = new ListTable();
				String strArr[] = null;
				tlistTable.setName("CONT");
				logger.debug("&&&&&&&&&&&&&&&&&&&&&" + tContSSRS.MaxRow);

				if (mBatchFlag.equals("0")) {

					if (tContSSRS.MaxRow > 3000) {
						CError tError = new CError();
						tError.moduleName = "TollBussDisBL";
						tError.functionName = "queryData";
						tError.errorMessage = "此人的记录已超过3000条,可能会引起内存溢出,请重新设置条件缩小范围!";
						this.mErrors.addOneError(tError);
						return false;

					}
				}
				for (int i = 1; i <= tContSSRS.MaxRow; i++) {
					strArr = new String[21];
					strArr[0] = tContSSRS.GetText(i, 1); // 保单号
					strArr[11] = tContSSRS.GetText(i, 2); // 分配时间
					strArr[12] = tContSSRS.GetText(i, 3); // 原服务人员代码
					strArr[13] = tContSSRS.GetText(i, 4); // 原服务人员姓名
					strArr[14] = tContSSRS.GetText(i, 5);// 健康加费
					strArr[15] = tContSSRS.GetText(i, 6);// 职业加费
					strArr[16] = tContSSRS.GetText(i, 7);// 被保人生日
					strArr[17] = tContSSRS.GetText(i, 8);// 投业关系(此字段在表中尚未维护)
					strArr[18] = tContSSRS.GetText(i, 9);// 最后一次分配时间
					logger.debug("*****-------------这个人是----------------**********"
									+ strArr[12]);
					// if (strArr[0].equals("BJ019721041001328")){
					// try
					// {
					//
					// }
					// catch (Exception ex)
					// {
					//
					//
					// }

					/*strSql = "select nvl(sum(prem),0) from lcpol where appflag='1' and contno='"
							+ strArr[0] + "'";*/
					strSql = "select (case when sum(prem) is not null then sum(prem) else 0 end) from lcpol where appflag='1' and contno='"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
					sqlbv6.sql(strSql);
					sqlbv6.put("contno", strArr[0]);
					tExeSQL = new ExeSQL();
					tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sqlbv6);
					if (tSSRS == null || tSSRS.getMaxRow() == 0) {
						CError tError = new CError();
						tError.moduleName = "TollBussDisBL";
						tError.functionName = "queryData";
						tError.errorMessage = "未查到保单号为" + strArr[0] + "的保单信息!";
						this.mErrors.addOneError(tError);
						return false;
					}
					// }
					// if ( !tSSRS.GetText(1, 1).equals("")&& tSSRS.GetText(1,
					// 1)!=null){
					strArr[1] = tSSRS.GetText(1, 1); // 保费
					// }

					RiskFee += Double.parseDouble(strArr[1]);

					Count++;

					// 查询保单状态
					strSql = " select a.state from lccontstate a,lcpol b where a.polno=b.polno and b.mainpolno=b.polno"
							+ " and a.statetype='Terminate' and b.contno='"
							+ "?contno1?" + "'";
					SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
					sqlbv7.sql(strSql);
					sqlbv7.put("contno1", strArr[0]);
					tExeSQL = new ExeSQL();
					tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sqlbv7);
					// if (tSSRS == null || tSSRS.getMaxRow() == 0)
					// {
					// strArr[2] = "";
					// }
					// String tIsTerminate = tSSRS.GetText(1,1);
					String tIsTerminate = "";
					if (tSSRS != null && tSSRS.getMaxRow() != 0) {
						tIsTerminate = tSSRS.GetText(1, 1);
						if (tIsTerminate.equals("1")) {
							strArr[2] = "终止";
						}
					}
					if (tSSRS == null || tSSRS.getMaxRow() == 0
							|| tIsTerminate.equals("0")) {
						strSql = " select 'X' from lccontstate a,lcpol b where a.polno=b.polno and b.mainpolno=b.polno"
								+ " and a.statetype='Available' and a.state='0' and a.enddate is null and b.contno='"
								+ "?contno2?"
								+ "' and a.startdate <'"
								+ "?startdate?"
								+ "'";
						SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
						sqlbv8.sql(strSql);
						sqlbv8.put("contno2", strArr[0]);
						sqlbv8.put("startdate", mEndDate);
						tExeSQL = new ExeSQL();
						tSSRS = new SSRS();
						tSSRS = tExeSQL.execSQL(sqlbv8);
						if (tSSRS == null || tSSRS.getMaxRow() == 0) {
							strArr[2] = "失效";
						} else {
							strArr[2] = "有效";
						}
					}

					strSql = "select paylocation,signdate from lccont where contno='"
							+ "?contno3?" + "'";
					SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
					sqlbv9.sql(strSql);
					sqlbv9.put("contno3", strArr[0]);
					tExeSQL = new ExeSQL();
					tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sqlbv9);
					if (tSSRS == null || tSSRS.getMaxRow() == 0) {
						CError tError = new CError();
						tError.moduleName = "TollBussDisBL";
						tError.functionName = "queryData";
						tError.errorMessage = "未查到保单号为" + strArr[0] + "的保单信息!";
						this.mErrors.addOneError(tError);
						return false;
					}
					String tPayLoction = tSSRS.GetText(1, 1);
					if (tPayLoction.equals("0")) {
						strArr[3] = "EFT";
						EFTCount++;
					} else if (tPayLoction.equals("1")) {
						strArr[3] = "自缴";
						PersonCount++;
					} else if (tPayLoction.equals("2")) {
						strArr[3] = "上门";
						ServeCount++;
					}
					strArr[4] = tSSRS.GetText(1, 2);

					strSql = "select payintv,max(paytodate) from lcpol where contno = '"
							+ "?contno4?"
							+ "' And polno = mainpolno group by payintv";
					SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
					sqlbv10.sql(strSql);
					sqlbv10.put("contno4", strArr[0]);
					tExeSQL = new ExeSQL();
					tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sqlbv10);

					if (tSSRS.GetText(1, 1).trim().equals("0"))
						strArr[5] = "";
					else
						strArr[5] = tSSRS.GetText(1, 2); // 缴费对应日

					// 查询孤儿单起期:第一次分配时间
					strSql = "select EndSerDate from lacommisiondetailb where poltype='0' and grpcontno='"
							+ "?contno5?" + "' order by makedate desc";
					SQLwithBindVariables sqlbv11=new SQLwithBindVariables();
					sqlbv11.sql(strSql);
					sqlbv11.put("contno5", strArr[0]);
					tExeSQL = new ExeSQL();
					tSSRS = new SSRS();
					tSSRS = tExeSQL.execSQL(sqlbv11);
					if (tSSRS == null || tSSRS.getMaxRow() == 0) {
						// CError tError = new CError();
						// tError.moduleName = "TollBussDisBL";
						// tError.functionName = "queryData";
						// tError.errorMessage = "未查到保单号为" + strArr[0] +
						// "的保单分配信息!";
						// this.mErrors.addOneError(tError);
						// return false;
						strArr[6] = strArr[11];

					} else {
						strArr[6] = tSSRS.GetText(1, 1); // 孤儿单起期
					}

					
					tSSRS = new SSRS();
					String sql = " select appntname,GetAppntPhone(a.contno,''),zipcode,concat((select placename from ldaddress where placetype = '03' and trim(placecode) = b.county),postaladdress) from lcappnt a,lcaddress b"
						       + " where a.appntno = b.customerno And a.Addressno = b.Addressno and contno='"
						       + strArr[0] + "'";
//					String sql = " select concat(appntname,GetAppntPhone(a.contno,'')),zipcode),(select placename from " +
//							"ldaddress where placetype = '03' and trim(placecode) = b.county)),postaladdress) from lcappnt a,lcaddress b)"
//							+ " where a.appntno = b.customerno And a.Addressno = b.Addressno and contno='"
//							+ "?contno6?" + "'";
					SQLwithBindVariables sqlbv12=new SQLwithBindVariables();
					sqlbv12.sql(sql);
					sqlbv12.put("contno6", strArr[0]);
					tSSRS = tExeSQL.execSQL(sqlbv12);
					if (tSSRS == null || tSSRS.MaxRow == 0) {
						strArr[7] = "不详";
						strArr[8] = "不详";
						strArr[9] = "不详";
						strArr[10] = "不详";

					} else {
						strArr[7] = tSSRS.GetText(1, 1);
						strArr[8] = tSSRS.GetText(1, 2);
						strArr[9] = tSSRS.GetText(1, 3);
						strArr[10] = tSSRS.GetText(1, 4);
					}
					strSql = "select to_char(max(lastpaytodate),'yyyy-mm-dd') from ljapayperson where ContNo='"
							+ "?contno7?" + "'";
					// 此保单最后一次抽档时间
					SQLwithBindVariables sqlbv13=new SQLwithBindVariables();
					sqlbv13.sql(strSql);
					sqlbv13.put("contno7", strArr[0]);
					String DrawDate = tExeSQL.getOneValue(sqlbv13);

					strSql = "select agentcode from lacommisiondetail"
							+ " where (StartSerDate<='" + "?StartSerDate?"
							+ "' and (EndSerDate is null)) and grpcontno='"
							+ "?grpcontno?" + "'"
							+ " union select agentcode from lacommisiondetailb"
							+ " where (StartSerDate<='" + "?StartSerDate?"
							+ "' and EndSerDate>'" + "?StartSerDate?"
							+ "' and grpcontno='" + "?grpcontno?" + "')";
					SQLwithBindVariables sqlbv15=new SQLwithBindVariables();
					sqlbv15.sql(strSql);
					sqlbv15.put("StartSerDate", DrawDate);
					sqlbv15.put("grpcontno", strArr[0]);
					SSRS agentSSRS = new SSRS();
					agentSSRS = tExeSQL.execSQL(sqlbv15);
					int agentnum = agentSSRS.getMaxRow();
					if (agentnum > 0) {
						for (int j = 1; j <= agentnum; j++) {
							strArr[19] = agentSSRS.GetText(j, 1);
							SQLwithBindVariables sqlbv16=new SQLwithBindVariables();
							sqlbv16.sql("select name from laagent where agentcode='"
									+ "?agentcode?" + "'");
							sqlbv16.put("agentcode", strArr[19]);
							strArr[20] = tExeSQL
									.getOneValue(sqlbv16);
							tlistTable.add(strArr);
						}
					} else {
						strArr[19] = "-";
						strArr[20] = "-";
						tlistTable.add(strArr);
					}
				}
				TextTag texttag = new TextTag(); // 新建一个TextTag的实例
				tXmlExport = new XmlExport(); // 新建一个XmlExport的实例
				tXmlExport.createDocument("TollBussDis.vts", "PRINT"); // 最好紧接着就初始化xml文档

				String[] ContractTitle = new String[21];
				tXmlExport.addListTable(tlistTable, ContractTitle);

				texttag.add("ManageCom", tManageComName);
				texttag.add("RiskFee", RiskFee);
				texttag.add("Count", Count);
				texttag.add("PersonCount", PersonCount);
				texttag.add("EFTCount", EFTCount);
				texttag.add("ServeCount", ServeCount);
				texttag.add("AgentCode", mAgentCode);
				texttag.add("Name", tAgentName);
				texttag.add("StartDate", mStartDate);
				texttag.add("EndDate", mEndDate);
				texttag.add("BranchAttr", tAgentBranchName);
				texttag.add("RiskChnl", mRiskChnlName);
				// texttag.add("BranchAttr2", tAgentBranchName);

				if (texttag.size() > 0) {
					tXmlExport.addTextTag(texttag);
				}
				mXmlExportAll.addDataSet(tXmlExport.getDocument()
						.getRootElement());
			}

			// XmlExport xmlexport = new XmlExport();
			// mXmlExportAll.outputDocumentToFile("c:\\", "test"); //输出xml文档到文件
		}
		mResult.clear();
		mResult.addElement(tXmlExport);
		mResult.addElement(mXmlExportAll);

		return true;
	}

	public VData getResult() {
		return mResult;
	}

}
