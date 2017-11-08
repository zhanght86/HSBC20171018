/*
 * Created on 2005-12-28
 *
 * TODO 该类主要用于向差错率表中增加数据，用于差错率统计
 *
 */
package com.sinosoft.lis.cbcheck;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCErrorReportSchema;
import com.sinosoft.lis.schema.LCIssuePolSchema;
import com.sinosoft.lis.vschema.LCErrorReportSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * @author HL
 * 
 * TODO
 * 
 * 
 */
public class ErrorRateReportBL {
private static Logger logger = Logger.getLogger(ErrorRateReportBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 往界面传输数据的容器 */
	MMap mMap = new MMap();

	private VData mResult = new VData();

	private TransferData mTransferData = new TransferData();

	/** 数据操作字符串 */
	private String mOperator;

	private String mManageCom;

	/** 传入的业务处理相关变量 */
	private String mMissionID = "";

	private String mActivityID = "";

	private String mSubMissionID = "";

	private String mContNo = "";

	private LCIssuePolSchema mLCIssuePolSchema = new LCIssuePolSchema();

	private LCErrorReportSchema mLCErrorReportSchema = new LCErrorReportSchema();

	private LCErrorReportSet mLCErrorReportSet = new LCErrorReportSet();

	/**
	 * 
	 */
	public ErrorRateReportBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中，此类做数据提交使用
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}

		// 准备给后台的数据
		prepareOutputData();

		// 数据提交
		mResult.add(mMap);

		if (cOperate != null && cOperate.equals("submit")) {
			PubSubmit tPubSubmit = new PubSubmit();
			if (!tPubSubmit.submitData(mResult, "")) {
				// @@错误处理
				this.mErrors.copyAllErrors(tPubSubmit.mErrors);
				CError tError = new CError();
				tError.moduleName = "ErrorRateReportBL";
				tError.functionName = "submitData";
				tError.errorMessage = "数据提交失败!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}

		return true;
	}

	/**
	 * 数据操作类业务处理 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 根据missionid和activityid和submissionid判断差错率的岗位和人员
		/**
		 * 差错率判断规则： 录单发出的问题件：计为初审岗差错。
		 * 首次进入复核模块，发出的问题件：内部问题件计为录单岗差错；外部问题件计为初审岗、录单岗差错。
		 * 再次进入复核模块，发出的问题件：内部问题件计为录单岗、复核岗（非本次复核岗操作人员）差错；外部问题件计为初审岗、录单岗、复核岗（非本次复核岗操作人员）差错。
		 * 人工核保退回复核模块：同再次进入复核模块的差错率计算。
		 * 保全内部转办：目前维持现有保全模块项目不变，均计为复核岗操作差错，同时计成品保单差错。
		 * 
		 * 差错岗位：1-初审；2-录单；3-复核；4-成品保单
		 * 
		 * 其他号码类型：00 - 个单合同号;01 - 集体合同号;02 - 合同号;03 - 批单号;04 - 实付收据号;05 - 保单印刷号
		 * 
		 */
		if (mActivityID != null) {
			String tSQL = "";
			ExeSQL tExeSQL = new ExeSQL();
			SSRS tSSRS = new SSRS();

			/**
			 * @todo 利用数组将需要的数据存入
			 */

			String[][] tErrorReportCont = new String[3][2];

			// 如果是录单发出问题件
			if (mActivityID.equals("0000001098")
					|| mActivityID.equals("0000001099")) {
				// 记为初审岗错误
				tErrorReportCont[0][0] = "1";
				// 初审岗不记录错误人员，用全0代替用户代码
				tErrorReportCont[0][1] = "00000000000000000000";
			}
			// 如果是复核发出的问题件
			else if (mActivityID.equals("0000001001")) {
				// 利用SubMissionID判断是第几次进入复核
				if (mSubMissionID != null) {
					if (mSubMissionID.equals("1")) {
						tSQL = "select defaultOperator from lbmission where missionid='"
								+ "?missionid?"
//								+ "' and activityid in ('0000001098','0000001099','0000001094')";
								+ "' and activityid in (select activityid from lwactivity  where functionid in('10010020','10010001','10010002'))";
						SQLwithBindVariables sqlbv = new SQLwithBindVariables();
						sqlbv.sql(tSQL);
						sqlbv.put("missionid", mMissionID);
						tSSRS = tExeSQL.execSQL(sqlbv);
						// 判断问题件类型1-内部问题件2-外部问题件
						if (mLCIssuePolSchema.getBackObjType() != null
								&& mLCIssuePolSchema.getBackObjType().equals(
										"1")) {
							tErrorReportCont = new String[1][2];
							// 记为录单岗错误
							tErrorReportCont[0][0] = "2";
							tErrorReportCont[0][1] = tSSRS.GetText(1, 1);

						} else {
							tErrorReportCont = new String[2][2];
							// 记为初审岗错误
							tErrorReportCont[0][0] = "1";
							// 初审岗不记录错误人员，用全0代替用户代码
							tErrorReportCont[0][1] = "00000000000000000000";
							// 记为录单岗错误
							tErrorReportCont[1][0] = "2";
							tErrorReportCont[1][1] = tSSRS.GetText(1, 1);

						}
					} else {
						tSQL = "select case a1"
								+ " when '0000001001' then"
								+ " '3'"
								+ " else"
								+ " '2'"
								+ " end,"
								+ " a3"
								+ " from (select a.activityid as a1,"
								+ " a.submissionid as a2,"
								+ " a.defaultoperator as a3,"
								+ " row_number() over(partition by a.activityid order by a.submissionid desc) as rn"
								+ " from lbmission a"
								+ " where missionid = '"
								+ "?missionid?"
								+ "'"
//								+ " and activityid in ('0000001098', '0000001099', '0000001001','0000001094'))"
								+ " and activityid in (select activityid from lwactivity  where functionid in('10010020','10010001','10010002','10010003'))) "
								+ " where rn = 1";
						SQLwithBindVariables sqlbv = new SQLwithBindVariables();
						sqlbv.sql(tSQL);
						sqlbv.put("missionid", mMissionID);
						tSSRS = tExeSQL.execSQL(sqlbv);

						// 判断问题件类型1-内部问题件2-外部问题件
						if (mLCIssuePolSchema.getBackObjType() != null
								&& mLCIssuePolSchema.getBackObjType().equals(
										"1")) {

							tErrorReportCont[0][0] = tSSRS.GetText(1, 1);
							tErrorReportCont[0][1] = tSSRS.GetText(1, 2);

							tErrorReportCont[1][0] = tSSRS.GetText(2, 1);
							tErrorReportCont[1][1] = tSSRS.GetText(2, 2);

						} else {

							tErrorReportCont[0][0] = tSSRS.GetText(1, 1);
							tErrorReportCont[0][1] = tSSRS.GetText(1, 2);

							tErrorReportCont[1][0] = tSSRS.GetText(2, 1);
							tErrorReportCont[1][1] = tSSRS.GetText(2, 2);

							tErrorReportCont[2][0] = "1";
							tErrorReportCont[2][1] = "00000000000000000000";

						}

					}
				}

			} else if (mActivityID.equals("0000002001")) {
				// 先查找新契约的 MissionID
				tSQL = "select missionprop2 from lwmission where activityid = '0000000009' and missionid = '"
						+ "?missionid?" + "'";
				SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				sqlbv1.sql(tSQL);
				sqlbv1.put("missionid", mMissionID);
				SSRS contnoSSRS = new SSRS();
				contnoSSRS = tExeSQL.execSQL(sqlbv1);
				if (contnoSSRS != null && contnoSSRS.getMaxRow() > 0) {
					tSQL = "select proposalcontno from lccont where contno = '"
							+ "?contno?" + "'";
					SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
					sqlbv2.sql(tSQL);
					sqlbv2.put("contno", contnoSSRS.GetText(1, 1));
					SSRS ProposalcontnoSSRS = new SSRS();
					ProposalcontnoSSRS = tExeSQL.execSQL(sqlbv2);
					if (ProposalcontnoSSRS != null
							&& ProposalcontnoSSRS.getMaxRow() > 0) {
						tSQL = "select missionid from lbmission where missionprop1 = '"
								+ "?missionprop1?" + "'";
						SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
						sqlbv3.sql(tSQL);
						sqlbv3.put("missionprop1", ProposalcontnoSSRS.GetText(1, 1));
						SSRS missionidSSRS = new SSRS();
						missionidSSRS = tExeSQL.execSQL(sqlbv3);
						if (missionidSSRS != null
								&& missionidSSRS.getMaxRow() > 0) {
							mMissionID = missionidSSRS.GetText(1, 1);
						}
					}

				}

				tSQL = "select defaultOperator from lbmission where missionid='"
						+ "?missionid?"
//						+ "' and activityid in ('0000001001') order by makedate desc";
						+ "' and activityid in (select activityid from lwactivity  where functionid ='10010003') order by makedate desc";
				SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
				sqlbv4.sql(tSQL);
				sqlbv4.put("missionid", mMissionID);
				tSSRS = tExeSQL.execSQL(sqlbv4);
				tErrorReportCont = new String[2][2];
				// 记为复核岗错误
				if (tSSRS != null && tSSRS.getMaxRow() > 0) {
					tErrorReportCont[0][0] = "3";
					tErrorReportCont[0][1] = tSSRS.GetText(1, 1);

					// 记为成品保单错误
					tErrorReportCont[1][0] = "4";
					// 成品保单不记录错误人员，用全0代替用户代码
					tErrorReportCont[1][1] = "00000000000000000000";
				}

			}

			/**
			 * 根据数组中的值给LCErrorReport表准备数据
			 */
			logger.debug("tErrorReportCont.length=="
					+ tErrorReportCont.length);
			for (int i = 0; i < tErrorReportCont.length; i++) {
				if (tErrorReportCont[i][0] == null
						|| tErrorReportCont[0][0].equals("")) {
					continue;
				}
				LCErrorReportSchema tLCErrorReportSchema = new LCErrorReportSchema();
				tLCErrorReportSchema.setothernotype("05");
				tLCErrorReportSchema.setotherno(mLCIssuePolSchema
						.getProposalContNo());
				tLCErrorReportSchema.setErrorPos(tErrorReportCont[i][0]);
				tLCErrorReportSchema.setErrorOperator(tErrorReportCont[i][1]);
				tLCErrorReportSchema.setSerialNo(mLCIssuePolSchema
						.getSerialNo());
				tLCErrorReportSchema.setOperatorPos(mActivityID);
				tLCErrorReportSchema.setErrorCode(mLCIssuePolSchema
						.getIssueType());
				tLCErrorReportSchema.setErrorContent(mLCIssuePolSchema
						.getIssueCont());
				tLCErrorReportSchema.setOperator(mOperator);
				tLCErrorReportSchema.setMakeDate(PubFun.getCurrentDate());
				tLCErrorReportSchema.setMakeTime(PubFun.getCurrentTime());
				mLCErrorReportSet.add(tLCErrorReportSchema);
			}

		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		mOperator = tGlobalInput.Operator;
		mManageCom = tGlobalInput.ManageCom;

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);

		mMissionID = (String) mTransferData.getValueByName("MissionID");

		mSubMissionID = (String) mTransferData.getValueByName("SubMissionID");

		mActivityID = (String) mTransferData.getValueByName("ActivityID");

		mLCIssuePolSchema = (LCIssuePolSchema) cInputData
				.getObjectByObjectName("LCIssuePolSchema", 0);

		return true;
	}

	private boolean checkData() {
		if (mMissionID == null || mMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ErrorRateReportBL";
			tError.functionName = "checkData";
			tError.errorMessage = "传入MissionID数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mSubMissionID == null || mSubMissionID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ErrorRateReportBL";
			tError.functionName = "checkData";
			tError.errorMessage = "传入SubMissionID数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mActivityID == null || mActivityID.equals("")) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ErrorRateReportBL";
			tError.functionName = "checkData";
			tError.errorMessage = "传入ActivityID数据失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		if (mLCIssuePolSchema == null) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "ErrorRateReportBL";
			tError.functionName = "checkData";
			tError.errorMessage = "传入LCIssuePolSchema数据失败!";
			this.mErrors.addOneError(tError);
			return false;

		}

		return true;

	}

	/**
	 * 准备需要保存的数据
	 */
	private void prepareOutputData() {
		mMap.put(mLCErrorReportSet, "INSERT");
	}

	/**
	 * 返回结果
	 * 
	 * @return VData mResult
	 */
	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		ErrorRateReportBL tErrorRateReportBL = new ErrorRateReportBL();
		VData tVData = new VData();
		TransferData tTransferData = new TransferData();
		LCIssuePolSchema tLCIssuePolSchema = new LCIssuePolSchema();
		GlobalInput tGlobalInput = new GlobalInput();

		tGlobalInput.Operator = "001";

		tTransferData.setNameAndValue("MissionID", "00000000000000138425");
		tTransferData.setNameAndValue("SubMissionID", "1");
		tTransferData.setNameAndValue("ActivityID", "0000001098");

		tLCIssuePolSchema.setProposalContNo("20051229000002");
		tLCIssuePolSchema.setIssueCont("aa");
		tLCIssuePolSchema.setIssueType("01");
		tLCIssuePolSchema.setSerialNo("1");

		tVData.add(tGlobalInput);
		tVData.add(tTransferData);
		tVData.add(tLCIssuePolSchema);

		tErrorRateReportBL.submitData(tVData, "submit");

	}
}
