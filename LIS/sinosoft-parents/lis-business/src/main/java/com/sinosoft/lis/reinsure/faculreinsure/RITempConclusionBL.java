

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.db.RIAccumulateRDCodeDB;
import com.sinosoft.lis.db.RIDutyStateDB;
import com.sinosoft.lis.db.RITempContLinkDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIDutyStateSchema;
import com.sinosoft.lis.schema.RITempContLinkSchema;
import com.sinosoft.lis.vschema.RIAccumulateRDCodeSet;
import com.sinosoft.lis.vschema.RIDutyStateSet;
import com.sinosoft.lis.vschema.RITempContLinkSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class RITempConclusionBL implements BusinessService {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	public VData mResult = new VData();
	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	private TransferData mTransferData = new TransferData();
	private RITempContLinkSet mRITempContLinkSet;
	private RIDutyStateSet mRIDutyStateSet;
	private RIDutyStateSet delRIDutyStateSet;
	private RIAccumulateRDCodeSet mRIAccumulateRDCodeSet = new RIAccumulateRDCodeSet();
	private String mRIPreceptNo = "";
	private String mCalFeeType = "";
	private String mCalFeeTerm = "";
	private String mSerialNo = "";

	/** 数据操作字符串 */
	private String strOperate;
	private String mTempUWConclusion;
	private MMap mMap = new MMap();

	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */

	public RITempConclusionBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		strOperate = cOperate;
		if (!getInputData(cInputData)) {
			return false;
		}
		if (!getAccumulated()) {
			return false;
		}
		if (!dealData()) {
			return false;
		}
		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		this.mInputData = cInputData;
		this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mRIPreceptNo = (String) mTransferData.getValueByName("RIPreceptNo");
		mCalFeeType = (String) mTransferData.getValueByName("CalFeeType");
		mCalFeeTerm = (String) mTransferData.getValueByName("CalFeeTerm");
		mSerialNo = (String) mTransferData.getValueByName("SerialNo");

		System.out.println(" CalFeeTerm: " + mCalFeeTerm);
		System.out.println(" CalFeeType: " + mCalFeeType);
		System.out.println(" RIPreceptNo: " + mRIPreceptNo);
		System.out.println(" SerialNo: " + mSerialNo);
		if (strOperate.equals("01")) {
		}
		if (strOperate.equals("02")) {
			this.mRITempContLinkSet = (RITempContLinkSet) cInputData
					.getObjectByObjectName("RITempContLinkSet", 0);
		}
		if (strOperate.equals("03") || strOperate.equals("04")) {
			this.mRITempContLinkSet = (RITempContLinkSet) cInputData
					.getObjectByObjectName("RITempContLinkSet", 0);
		}
		return true;
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.Operator = "001";
		TransferData tTransferData = new TransferData();
		// 准备传输数据 VData
		VData tVData = new VData();
		tTransferData.setNameAndValue("RIContNo", "C000000001");
		tTransferData.setNameAndValue("RIPreceptNo", "S001000002");
		tTransferData.setNameAndValue("ContNo", "06290000");

		tTransferData.setNameAndValue("ContType", "1");
		tTransferData.setNameAndValue("CalFeeType", "02");
		tTransferData.setNameAndValue("CalFeeTerm", "02");

		RITempContLinkSchema tRITempContLinkSchema = new RITempContLinkSchema();

		tRITempContLinkSchema.setRIContNo("C000000001");
		tRITempContLinkSchema.setRIPreceptNo("S001000002");
		tRITempContLinkSchema.setRelaType("01");
		tRITempContLinkSchema.setProposalGrpContNo("000000");
		tRITempContLinkSchema.setGrpProposalNo("000000");
		tRITempContLinkSchema.setProposalContNo("06290000");
		tRITempContLinkSchema.setProposalNo("000000000465");
		tRITempContLinkSchema.setContPlanCode("000000");
		tRITempContLinkSchema.setRiskCode("1150041");
		tRITempContLinkSchema.setDutyCode("004100");
		tRITempContLinkSchema.setInsuredNo("00000162");

		RITempContLinkSet tRITempContLinkSet = new RITempContLinkSet();
		tRITempContLinkSet.add(tRITempContLinkSchema);
		tVData.add(tRITempContLinkSet);
		tVData.add(tTransferData);
		tVData.add(globalInput);

		// 数据传输
		RITempConclusionBL tRITempConclusionBL = new RITempConclusionBL();
		tRITempConclusionBL.submitData(tVData, "03");

		try {
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean dealData() {
		String tContType = (String) mTransferData.getValueByName("ContType");
		if (tContType.equals("1")) { // 个险
			if (!dealInd())
				return false;
		} else { // 团险
			if (!dealGrp())
				return false;
		}
		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("update", "保存临分核保结论时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;

		return true;
	}

	private boolean dealInd() {
		RITempContLinkSchema tRITempContLinkSchema;
		RIDutyStateSchema tRIDutyStateSchema;
		mRIDutyStateSet = new RIDutyStateSet();
		System.out.println(" mRITempContLinkSet.size(): "
				+ mRITempContLinkSet.size());
		if (strOperate.equals("02")) {
			for (int i = 1; i <= mRITempContLinkSet.size(); i++) {
				tRITempContLinkSchema = mRITempContLinkSet.get(i);
				tRITempContLinkSchema.setOperator(globalInput.Operator);
				tRITempContLinkSchema.setMakeDate(PubFun.getCurrentDate());
				tRITempContLinkSchema.setMakeTime(PubFun.getCurrentTime());
				String sqlRIDutyStat = "update RIDutyState set State='03' where ProposalNo='"
						+ tRITempContLinkSchema.getProposalNo()
						+ "'and DutyCode='"
						+ tRITempContLinkSchema.getDutyCode() + "'";
				mMap.put(sqlRIDutyStat, "UPDATE");
				// mRIDutyStateSet.add(tRIDutyStateSchema);
			}
			mMap.put(mRITempContLinkSet, "INSERT");
			// mMap.put(mRIDutyStateSet, "UPDATE");

		} else if (strOperate.equals("03") || strOperate.equals("04")) {
			// mMap.put(mRITempContLinkSet, "DELETE");

			for (int i = 1; i <= mRITempContLinkSet.size(); i++) {
				tRITempContLinkSchema = mRITempContLinkSet.get(i);
				if (tRITempContLinkSchema.getRelaType().equals("01")) {
					String sqlRIDutyStat = "update RIDutyState set State='02' where ProposalNo='"
							+ tRITempContLinkSchema.getProposalNo()
							+ "'and DutyCode='"
							+ tRITempContLinkSchema.getDutyCode() + "'";
					String sqlRITempContLink = "delete from RITempContLink where ProposalNo='"
							+ tRITempContLinkSchema.getProposalNo()
							+ "'and DutyCode='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "'and RelaType='01'";
					mMap.put(sqlRIDutyStat, "UPDATE");
					mMap.put(sqlRITempContLink, "DELETE");
				}
			}
		}
		return true;
	}

	private boolean dealGrp() {
		RITempContLinkSchema tRITempContLinkSchema;
		RIDutyStateSchema tRIDutyStateSchema;

		if (strOperate.equals("01")) { // 关联全部查询结果
			String tRIContNo = (String) mTransferData
					.getValueByName("RIContNo");
			String tContPlanCode = (String) mTransferData
					.getValueByName("ContPlanCode");
			String tRiskCode = (String) mTransferData
					.getValueByName("RiskCode");
			String tDutyCode = (String) mTransferData
					.getValueByName("DutyCode");
			String tInsuredNo = (String) mTransferData
					.getValueByName("InsuredNo");
			String tInsuredName = (String) mTransferData
					.getValueByName("InsuredName");
			String tRelaType = (String) mTransferData
					.getValueByName("RelaType");
			String tGrpContNo = (String) mTransferData
					.getValueByName("GrpContNo");

			String tSQL = " select '"
					+ tRIContNo
					+ "' RIContNo,'"
					+ mRIPreceptNo
					+ "' RIPreceptNo,'"
					+ tRelaType
					+ "' RelaType,'"
					+ tGrpContNo
					+ "' ProposalGrpContNo,'000000' GrpProposalNo,a.contno ProposalContNo,a.proposalno ProposalNo,(select distinct contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno)) ContPlanCode,a.RiskCode RiskCode,b.dutycode DutyCode,a.Insuredno InsuredNo,'"
					+ mCalFeeTerm
					+ "' CalFeeTerm,'"
					+ mCalFeeType
					+ "' CalFeeType,null StandbyString1,null StandbyString2,null StandbyString3,null StandbyDouble1,null StandbyDouble2,null StandbyDouble3,null StandbyDate1,null StandbyDate2,'"
					+ globalInput.Operator
					+ "' Operator,to_date('"
					+ PubFun.getCurrentDate()
					+ "') MakeDate,'"
					+ PubFun.getCurrentTime()
					+ "' MakeTime from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) "
					+ " and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"
					+ tGrpContNo + "') ";
			if (tRiskCode != null && !tRiskCode.equals("")) {
				tSQL = tSQL + " and trim(a.RiskCode)='" + tRiskCode + "'";
			}
			if (tDutyCode != null && !tDutyCode.equals("")) {
				tSQL = tSQL + " and trim(b.DutyCode)='" + tDutyCode + "'";
			}
			if (tInsuredNo != null && !tInsuredNo.equals("")) {
				tSQL = tSQL + " and trim(a.InsuredNo) = '" + tInsuredNo + "'";
			}
			if (tInsuredName != null && !tInsuredName.equals("")) {
				tSQL = tSQL + " and trim(a.InsuredName) like '%" + tInsuredName
						+ "%'";
			}
			if (tContPlanCode != null && !tContPlanCode.equals("")) {
				tSQL = tSQL
						+ " and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno) and trim(contplancode)='"
						+ tContPlanCode + "')";
			}
			tSQL = tSQL
					+ " and exists (select * from RIDutyState where trim(ProposalNo)=trim(a.ProposalNo) and trim(DutyCode)=trim(b.DutyCode) and trim(State) = '02' ) ";
			RITempContLinkDB tRITempContLinkDB = new RITempContLinkDB();
			System.out.println(" sql: " + tSQL);
			mRITempContLinkSet = tRITempContLinkDB.executeQuery(tSQL);
			if (!isAccumulated(mRITempContLinkSet)) {
				return false;
			}
			mMap.put(mRITempContLinkSet, "INSERT");

			// 责任保单修改为临分状态
			tSQL = " select '"
					+ tGrpContNo
					+ "' ProposalGrpContNo,a.proposalno ProposalNo,b.dutycode DutyCode,'03' State,(select UWConclusion from RIDutyState where trim(ProposalNo)=trim(a.proposalno) and trim(DutyCode)=trim(b.DutyCode)),'"
					+ mCalFeeTerm
					+ "' CalFeeTerm,'"
					+ mCalFeeType
					+ "' CalFeeType,null StandbyString1,null StandbyString2 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) "
					+ " and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"
					+ tGrpContNo + "') ";
			if (tRiskCode != null && !tRiskCode.equals("")) {
				tSQL = tSQL + " and trim(a.RiskCode)='" + tRiskCode + "'";
			}
			if (tDutyCode != null && !tDutyCode.equals("")) {
				tSQL = tSQL + " and trim(b.DutyCode)='" + tDutyCode + "'";
			}
			if (tInsuredNo != null && !tInsuredNo.equals("")) {
				tSQL = tSQL + " and trim(a.InsuredNo) = '" + tInsuredNo + "'";
			}
			if (tInsuredName != null && !tInsuredName.equals("")) {
				tSQL = tSQL + " and trim(a.InsuredName) like '%" + tInsuredName
						+ "%'";
			}
			if (tContPlanCode != null && !tContPlanCode.equals("")) {
				tSQL = tSQL
						+ " and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno) and trim(contplancode)='"
						+ tContPlanCode + "')";
			}
			tSQL = tSQL
					+ " and exists (select * from RIDutyState where trim(ProposalNo)=trim(a.ProposalNo) and trim(DutyCode)=trim(b.DutyCode) and trim(State) = '02' ) ";
			RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
			mRIDutyStateSet = tRIDutyStateDB.executeQuery(tSQL);
			// 置责任保单状态
			if (mRIDutyStateSet.size() > 0) {
				mMap.put(mRIDutyStateSet, "DELETE&INSERT");
			}
		} else if (strOperate.equals("02")) { // 关联选中
			mRIDutyStateSet = new RIDutyStateSet();
			for (int i = 1; i <= mRITempContLinkSet.size(); i++) {
				tRIDutyStateSchema = new RIDutyStateSchema();
				tRITempContLinkSchema = mRITempContLinkSet.get(i);
				if (tRITempContLinkSchema.getRelaType().equals("01")) { // 个人保单关联
					RITempContLinkDB tRITempContLinkDB = new RITempContLinkDB();
					RITempContLinkSet tRITempContLinkSet;
					tRITempContLinkDB.setRelaType("03");
					tRITempContLinkDB.setRIContNo(tRITempContLinkSchema
							.getRIContNo());
					tRITempContLinkDB.setRIPreceptNo(tRITempContLinkSchema
							.getRIPreceptNo());
					tRITempContLinkDB
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRITempContLinkDB.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRITempContLinkSet = tRITempContLinkDB.query();
					if (tRITempContLinkSet.size() != 0) {
						buildError("", "该责任保单已被关联到临分方案,不能重复关联!");
						return false;
					}
					tRITempContLinkDB = new RITempContLinkDB();
					tRITempContLinkDB.setRelaType("02");
					tRITempContLinkDB.setRIContNo(tRITempContLinkSchema
							.getRIContNo());
					tRITempContLinkDB.setRIPreceptNo(tRITempContLinkSchema
							.getRIPreceptNo());
					tRITempContLinkDB
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRITempContLinkDB.setContPlanCode(tRITempContLinkSchema
							.getContPlanCode());
					tRITempContLinkDB.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRITempContLinkSet = tRITempContLinkDB.query();
					if (tRITempContLinkSet.size() != 0) {
						buildError("", "该责任保单已被关联到临分方案,不能重复关联!");
						return false;
					}
					tRITempContLinkDB = new RITempContLinkDB();
					tRITempContLinkDB.setRelaType("01");
					tRITempContLinkDB.setRIContNo(tRITempContLinkSchema
							.getRIContNo());
					tRITempContLinkDB.setRIPreceptNo(tRITempContLinkSchema
							.getRIPreceptNo());
					tRITempContLinkDB
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRITempContLinkDB.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRITempContLinkDB.setProposalNo(tRITempContLinkSchema
							.getProposalNo());
					tRITempContLinkDB.setContPlanCode(tRITempContLinkSchema
							.getContPlanCode());
					if (tRITempContLinkDB.getInfo()) {
						buildError("", "该责任保单已被关联到临分方案,不能重复关联!");
						return false;
					}
					tRIDutyStateSchema
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRIDutyStateSchema.setProposalNo(tRITempContLinkSchema
							.getProposalNo());
					tRIDutyStateSchema.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRIDutyStateSchema.setState("03");
					tRIDutyStateSchema.setUWConclusion("02");
					tRIDutyStateSchema.setCalFeeTerm(mCalFeeTerm);
					tRIDutyStateSchema.setCalFeeType(mCalFeeType);
					tRIDutyStateSchema.setStandbyString1(mSerialNo);
					mRIDutyStateSet.add(tRIDutyStateSchema);
					// 置责任保单状态
					if (mRIDutyStateSet.size() > 0) {
						mMap.put(mRIDutyStateSet, "DELETE&INSERT");
					}
				} else if (tRITempContLinkSchema.getRelaType().equals("02")) { // 保障计划关联
					RITempContLinkDB tRITempContLinkDB = new RITempContLinkDB();
					RITempContLinkSet tRITempContLinkSet;

					tRITempContLinkDB.setRelaType("03");
					tRITempContLinkDB.setRIContNo(tRITempContLinkSchema
							.getRIContNo());
					tRITempContLinkDB.setRIPreceptNo(tRITempContLinkSchema
							.getRIPreceptNo());
					tRITempContLinkDB
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRITempContLinkDB.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRITempContLinkSet = tRITempContLinkDB.query();
					if (tRITempContLinkSet.size() != 0) {
						buildError("", "不能重复关联!");
						return false;
					}
					tRITempContLinkDB = new RITempContLinkDB();
					tRITempContLinkDB.setRelaType("02");
					tRITempContLinkDB.setRIContNo(tRITempContLinkSchema
							.getRIContNo());
					tRITempContLinkDB.setRIPreceptNo(tRITempContLinkSchema
							.getRIPreceptNo());
					tRITempContLinkDB
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRITempContLinkDB.setContPlanCode(tRITempContLinkSchema
							.getContPlanCode());
					tRITempContLinkDB.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRITempContLinkSet = tRITempContLinkDB.query();
					if (tRITempContLinkSet.size() != 0) {
						buildError("", "不能重复关联!");
						return false;
					}
					tRITempContLinkDB = new RITempContLinkDB();
					tRITempContLinkDB.setRelaType("01");
					tRITempContLinkDB.setRIContNo(tRITempContLinkSchema
							.getRIContNo());
					tRITempContLinkDB.setRIPreceptNo(tRITempContLinkSchema
							.getRIPreceptNo());
					tRITempContLinkDB
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRITempContLinkDB.setContPlanCode(tRITempContLinkSchema
							.getContPlanCode());
					tRITempContLinkDB.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRITempContLinkSet = tRITempContLinkDB.query();
					if (tRITempContLinkSet.size() != 0) {
						buildError("", "不能重复关联!");
						return false;
					}
					// 校验是否已经对该保障计划下的责任保单下其他临分结论
					String tSQL = " select a.* from RIDutyState a,(select a.proposalno B1,b.dutycode B2,(select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno)) B3 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno) and trim(contplancode)='"
							+ tRITempContLinkSchema.getContPlanCode()
							+ "')) b where trim(a.ProposalNo)=trim(b.B1) and trim(a.DutyCode)=trim(b.B2) and trim(a.ProposalGrpContNo)='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' and trim(a.Dutycode)='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "' and trim(a.state)<>'02' ";
					RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
					RIDutyStateSet tRIDutyStateSet = tRIDutyStateDB
							.executeQuery(tSQL);
					if (tRIDutyStateSet.size() != 0) {
						buildError("",
								"该保障计划下的部分或全部责任保单已下临分结论,请先删除相关临分结论后再进行保障计划的关联!");
						return false;
					}
					// 责任保单修改为临分状态
					tSQL = " select '"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' ProposalGrpContNo,a.proposalno ProposalNo,b.dutycode DutyCode,'03' State,(select UWConclusion from RIDutyState where trim(ProposalNo)=trim(a.proposalno) and trim(DutyCode)=trim(b.DutyCode)) UWConclusion,'"
							+ mCalFeeTerm
							+ "' CalFeeTerm,'"
							+ mCalFeeType
							+ "' CalFeeType,null StandbyString1,null StandbyString2 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) "
							+ " and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "') "
							+ " and trim(b.DutyCode)='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "' and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno) and trim(contplancode)='"
							+ tRITempContLinkSchema.getContPlanCode() + "')";
					tRIDutyStateDB = new RIDutyStateDB();
					mRIDutyStateSet = tRIDutyStateDB.executeQuery(tSQL);
					if (mRIDutyStateSet.size() > 0) {
						mMap.put(mRIDutyStateSet, "DELETE&INSERT");
					}
				} else { // 团体关联
					RITempContLinkDB tRITempContLinkDB = new RITempContLinkDB();
					RITempContLinkSet tRITempContLinkSet;
					tRITempContLinkDB.setRIContNo(tRITempContLinkSchema
							.getRIContNo());
					tRITempContLinkDB.setRIPreceptNo(tRITempContLinkSchema
							.getRIPreceptNo());
					tRITempContLinkDB
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRITempContLinkDB.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRITempContLinkSet = tRITempContLinkDB.query();
					if (tRITempContLinkSet.size() != 0) {
						buildError("", "团单下部分或全部保单已经关联到临分合同，不能重复关联!");
						return false;
					}
					// 校验是否已经对该团单下的责任保单下临分结论
					String tSQL = " select a.* from RIDutyState a,(select a.proposalno B1,b.dutycode B2,(select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=a.contno) B3 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) ) b where trim(a.ProposalNo)=trim(b.B1) and trim(a.DutyCode)=trim(b.B2) and trim(a.ProposalGrpContNo)='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' and trim(a.Dutycode)='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "' and trim(a.state)<>'02' ";
					RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
					RIDutyStateSet tRIDutyStateSet = tRIDutyStateDB
							.executeQuery(tSQL);
					if (tRIDutyStateSet.size() != 0) {
						buildError("",
								"该团单下的部分或全部责任保单已下临分结论,请先删除相关临分结论后再进行保障计划的关联!");
						return false;
					}
					// 责任保单修改为临分状态
					tSQL = " select '"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' ProposalGrpContNo,a.proposalno ProposalNo,b.dutycode DutyCode,'03' State,(select UWConclusion from RIDutyState where trim(ProposalNo)=trim(a.proposalno) and trim(DutyCode)=trim(b.DutyCode)) UWConclusion,'"
							+ mCalFeeTerm
							+ "' CalFeeTerm,'"
							+ mCalFeeType
							+ "' CalFeeType,null StandbyString1,null StandbyString2 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) "
							+ " and exists (select * from lcgrpcont where trim(grpcontno)=trim(a.grpcontno) and trim(proposalgrpcontno)='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "') " + " and trim(b.DutyCode)='"
							+ tRITempContLinkSchema.getDutyCode() + "' ";
					tRIDutyStateDB = new RIDutyStateDB();
					mRIDutyStateSet = tRIDutyStateDB.executeQuery(tSQL);

					if (mRIDutyStateSet.size() > 0) {
						mMap.put(mRIDutyStateSet, "DELETE&INSERT");
					}
				}
				tRITempContLinkSchema.setOperator(globalInput.Operator);
				tRITempContLinkSchema.setMakeDate(PubFun.getCurrentDate());
				tRITempContLinkSchema.setMakeTime(PubFun.getCurrentTime());
			}
			if (!isAccumulated(mRITempContLinkSet)) {
				return false;
			}
			mMap.put(mRITempContLinkSet, "INSERT");
		} else if (strOperate.equals("03")) { // 删除保单关联

			mMap.put(mRITempContLinkSet, "DELETE");

			mRIDutyStateSet = new RIDutyStateSet();
			for (int i = 1; i <= mRITempContLinkSet.size(); i++) {
				tRITempContLinkSchema = mRITempContLinkSet.get(i);
				if (tRITempContLinkSchema.getRelaType().equals("01")) {
					tRIDutyStateSchema = new RIDutyStateSchema();
					tRIDutyStateSchema
							.setProposalGrpContNo(tRITempContLinkSchema
									.getProposalGrpContNo());
					tRIDutyStateSchema.setProposalNo(tRITempContLinkSchema
							.getProposalNo());
					tRIDutyStateSchema.setDutyCode(tRITempContLinkSchema
							.getDutyCode());
					tRIDutyStateSchema.setState("02");
					tRIDutyStateSchema.setUWConclusion("02");
					tRIDutyStateSchema.setCalFeeTerm(null);
					tRIDutyStateSchema.setCalFeeType(null);

					mRIDutyStateSet.add(tRIDutyStateSchema);
					if (mRIDutyStateSet.size() > 0) {
						mMap.put(mRIDutyStateSet, "UPDATE");
					}
				}
				if (tRITempContLinkSchema.getRelaType().equals("02")) {
					// 还原责任保单临分状态
					String tSQL = "select a.* from RIDutyState a, (select a.proposalno B1,b.dutycode B2 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) and exists (select contplancode from lcinsured where trim(insuredno)=trim(a.insuredno) and contno=trim(a.contno) and contplancode='"
							+ tRITempContLinkSchema.getContPlanCode()
							+ "')) b where a.ProposalNo=b.B1 and a.DutyCode=b.B2 and a.ProposalGrpContNo='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' and b.B2='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "' and (UWConclusion ='' or UWConclusion is null) ";
					RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
					delRIDutyStateSet = tRIDutyStateDB.executeQuery(tSQL);
					if (delRIDutyStateSet.size() > 0) {
						mMap.put(delRIDutyStateSet, "DELETE");
					}
					tSQL = "select a.* from RIDutyState a, (select a.proposalno B1,b.dutycode B2 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) and exists (select contplancode from lcinsured where insuredno=trim(a.insuredno) and contno=trim(a.contno) and contplancode='"
							+ tRITempContLinkSchema.getContPlanCode()
							+ "')) b where trim(a.ProposalNo)=trim(b.B1) and trim(a.DutyCode)=b.B2 and trim(a.ProposalGrpContNo)='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' and b.B2='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "' and (UWConclusion <> '' or UWConclusion is not null) ";
					tRIDutyStateDB = new RIDutyStateDB();
					mRIDutyStateSet = tRIDutyStateDB.executeQuery(tSQL);
					for (int j = 1; j <= mRIDutyStateSet.size(); j++) {

						tRIDutyStateSchema = mRIDutyStateSet.get(j);
						tRIDutyStateSchema.setState(tRIDutyStateSchema
								.getUWConclusion());
						tRIDutyStateSchema.setCalFeeTerm(null);
						tRIDutyStateSchema.setCalFeeType(null);
					}
					if (mRIDutyStateSet.size() > 0) {
						mMap.put(mRIDutyStateSet, "UPDATE");
					}
				}
				if (tRITempContLinkSchema.getRelaType().equals("03")) {
					// 还原责任保单临分状态
					String tSQL = "select a.* from RIDutyState a, (select a.proposalno B1,b.dutycode B2 from lcpol a,lcduty b where trim(a.polno)=trim(b.polno) ) b where trim(a.ProposalNo)=b.B1 and trim(a.DutyCode)=b.B2 and trim(a.ProposalGrpContNo)='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' and b.B2='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "' and (UWConclusion ='' or UWConclusion is null) ";
					RIDutyStateDB tRIDutyStateDB = new RIDutyStateDB();
					delRIDutyStateSet = tRIDutyStateDB.executeQuery(tSQL);
					if (delRIDutyStateSet.size() > 0) {
						mMap.put(delRIDutyStateSet, "DELETE");
					}
					tSQL = "select a.* from RIDutyState a, (select a.proposalno B1,b.dutycode B2 from lcpol a,lcduty b where a.polno=b.polno ) b where a.ProposalNo=b.B1 and a.DutyCode=b.B2 and a.ProposalGrpContNo='"
							+ tRITempContLinkSchema.getProposalGrpContNo()
							+ "' and b.B2='"
							+ tRITempContLinkSchema.getDutyCode()
							+ "' and (UWConclusion <> '' or UWConclusion is not null) ";
					tRIDutyStateDB = new RIDutyStateDB();
					mRIDutyStateSet = tRIDutyStateDB.executeQuery(tSQL);
					for (int j = 1; j <= mRIDutyStateSet.size(); j++) {

						tRIDutyStateSchema = mRIDutyStateSet.get(j);
						tRIDutyStateSchema.setState(tRIDutyStateSchema
								.getUWConclusion());
						tRIDutyStateSchema.setCalFeeType(null);
						tRIDutyStateSchema.setStandbyString2(null);
					}
					if (mRIDutyStateSet.size() > 0) {
						mMap.put(mRIDutyStateSet, "UPDATE");
					}
				}
			}
		}

		return false;
	}

	private boolean getAccumulated() {
		String tSQL = " select distinct * from RIAccumulateRDCode where AccumulateDefNO = (select AccumulateDefNO from riprecept where ripreceptno='"
				+ mRIPreceptNo + "')";
		System.out.println(" tSQL : " + tSQL);
		RIAccumulateRDCodeDB tRIAccumulateRDCodeDB = new RIAccumulateRDCodeDB();
		mRIAccumulateRDCodeSet = tRIAccumulateRDCodeDB.executeQuery(tSQL);
		if (tRIAccumulateRDCodeDB.mErrors.needDealError()) {
			buildError("DealData", "查询累计方案出错");
			return false;
		}
		if (mRIAccumulateRDCodeSet.size() == 0) {
			buildError("DealData", "查询累计方案出错");
			return false;
		}
		return true;
	}

	private boolean isAccumulated(RITempContLinkSet aRITempContLinkSet) {
		int flag;
		for (int i = 1; i <= aRITempContLinkSet.size(); i++) {
			flag = 0;
			for (int j = 1; j <= mRIAccumulateRDCodeSet.size(); j++) {
				if (aRITempContLinkSet
						.get(i)
						.getDutyCode()
						.equals(mRIAccumulateRDCodeSet.get(j)
								.getAssociatedCode())) {
					flag = 1;
					continue;
				}
			}
			if (flag == 0) {
				buildError("DealData", "被关联保单的责任不是临分方案的累计风险！");
				return false;
			}
		}
		return true;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "RITempConclusionBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public VData getResult() {
		return this.mResult;
	}

	public CErrors getErrors() {
		return this.mErrors;
	}
}
