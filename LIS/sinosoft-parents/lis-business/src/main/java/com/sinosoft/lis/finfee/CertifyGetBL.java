package com.sinosoft.lis.finfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LAStatSegmentDB;
import com.sinosoft.lis.db.LAWageDB;
import com.sinosoft.lis.db.LJCertifyGetDB;
import com.sinosoft.lis.db.LJCertifyPayDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LAStatSegmentSchema;
import com.sinosoft.lis.schema.LJCertifyGetAgentSchema;
import com.sinosoft.lis.schema.LJCertifyGetSchema;
import com.sinosoft.lis.schema.LJCertifyPaySchema;
import com.sinosoft.lis.vschema.LAAgentSet;
import com.sinosoft.lis.vschema.LAStatSegmentSet;
import com.sinosoft.lis.vschema.LJCertifyGetAgentSet;
import com.sinosoft.lis.vschema.LJCertifyGetSet;
import com.sinosoft.lis.vschema.LJCertifyPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company: SinoSoft
 * </p>
 * 
 * @author Gaoht
 * @version 1.0
 */
public class CertifyGetBL {
private static Logger logger = Logger.getLogger(CertifyGetBL.class);
	private String mAgentCode = "";
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	private String CurrentDate = PubFun.getCurrentDate();
	private String CurrentTime = PubFun.getCurrentTime();
	private double mKmoney = 0;
	private double mSmoney = 0;
	private double mGetMoney = 0;
	private String mNo = "";
	public CErrors mErrors = new CErrors();
	private String mDistict, mDepartment, mBranchCode;
	private LAAgentSet mLAAgentSet = new LAAgentSet();
	private VData mResult = new VData();
	private LJCertifyGetSchema mLJCertifyGetSchema = new LJCertifyGetSchema();
	private LJCertifyGetAgentSet mLJCertifyGetAgentSet = new LJCertifyGetAgentSet();

	public CertifyGetBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData, cOperate)) {
			return false;
		}
		// 检验该保单是否被挂起(没有被挂起),是否被核销(应被核销)
		if (!checkDate()) {
			return false;
		}
		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mResult, "")) {
			// @@错误处理
			CError.buildErr(this, "提交数据失败");
			return false;
		}
		mResult.clear();
		return true;
	}

	private boolean getInputData(VData cInputData, String cOperate) {
		TransferData tTransferData = (TransferData) cInputData
				.getObjectByObjectName("TransferData", 0);
		mAgentCode = (String) tTransferData.getValueByName("AgentCode");
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		return true;
	}

	public VData getResult() {
		return mResult;
	}

	private boolean checkDate() {
		LJCertifyGetSet tLJCertifyGetSet = new LJCertifyGetSet();
		LJCertifyGetDB tLJCertifyGetDB = new LJCertifyGetDB();
		tLJCertifyGetDB.setAgentCode(mAgentCode);
		tLJCertifyGetSet = tLJCertifyGetDB.query();
		if (tLJCertifyGetSet.size() > 0) {
			CError.buildErr(this, "该业务员已经做过保证金领取申请");
			return false;
		}

		String hSql = "select * from laagent where agentcode='?mAgentCode?' and managecom like concat('?ManageCom?','%')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(hSql);
		sqlbv.put("mAgentCode", mAgentCode);
		sqlbv.put("ManageCom", mGlobalInput.ManageCom);
		LAAgentDB tLAAgentDB = new LAAgentDB();
		mLAAgentSet = tLAAgentDB.executeQuery(sqlbv);
		if (mLAAgentSet.size() == 0) {
			CError.buildErr(this, "未找到业务员信息");
			return false;
		}
		String tSql = "select outworkdate from laagent where outworkdate is not null and agentcode='?mAgentCode?'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(tSql);
		sqlbv1.put("mAgentCode", mAgentCode);
		String tDate = "";
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sqlbv1);
		if (tSSRS.getMaxRow() == 0) {
			CError.buildErr(this, "业务员并未离职");
			return false;
		}
		tDate = tSSRS.GetText(1, 1);
		tSql = "select * from lastatsegment where stattype='5' and startdate<='?tDate?' and enddate>='?tDate?'";
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(tSql);
		sqlbv2.put("tDate", tDate);
		LAStatSegmentSchema tLAStatSegmentSchema = new LAStatSegmentSchema();
		LAStatSegmentDB tLAStatSegmentDB = new LAStatSegmentDB();
		LAStatSegmentSet tLAStatSegmentSet = new LAStatSegmentSet();
		tLAStatSegmentSet = tLAStatSegmentDB.executeQuery(sqlbv2);
		if (tLAStatSegmentSet.size() == 0) {
			CError.buildErr(this, "并未发生薪资计算");
			return false;
		}
		tLAStatSegmentSchema = tLAStatSegmentSet.get(1);
		LAWageDB tLAWageDB = new LAWageDB();
		logger.debug("-----------------------"
				+ String.valueOf(tLAStatSegmentSchema.getYearMonth()));
		tLAWageDB.setIndexCalNo(String.valueOf(tLAStatSegmentSchema
				.getYearMonth()));
		tLAWageDB.setAgentCode(mAgentCode);
		if (!tLAWageDB.getInfo()) {
			CError.buildErr(this, "并未发生薪资计算");
			return false;
		}
		mKmoney = tLAWageDB.getW02() + tLAWageDB.getW01();
		logger.debug("扣款金额=====================" + mKmoney);

		LJCertifyPaySet tLJCertifyPaySet = new LJCertifyPaySet();
		LJCertifyPayDB tLJCertifyPayDB = new LJCertifyPayDB();
		tLJCertifyPayDB.setAgentCode(mAgentCode);
		tLJCertifyPaySet = tLJCertifyPayDB.query();

		if (tLJCertifyPaySet.size() == 0) {
			CError.buildErr(this, "未找到已经缴纳的保证金");
			return false;
		}
		for (int i = 1; i <= tLJCertifyPaySet.size(); i++) {
			LJCertifyPaySchema tLJCertifyPaySchema = new LJCertifyPaySchema();
			tLJCertifyPaySchema = tLJCertifyPaySet.get(i);
			mSmoney = mSmoney + tLJCertifyPaySchema.getActuPayMoney();
		}
		if (mSmoney < -mKmoney) {
			CError.buildErr(this, "缴纳的保证金" + mSmoney + "不够支付扣款金额" + mKmoney
					+ "");
			return false;
		}
		mGetMoney = mSmoney + mKmoney;
		if (mGetMoney == 0) {
			CError.buildErr(this, "缴纳的保证金" + mSmoney + ",扣款金额" + mKmoney
					+ "相等,不能退费");
			return false;
		}
		return true;
	}

	private boolean dealData() {
		String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		mNo = PubFun1.CreateMaxNo("GETNO", tLimit);

		// 区部组
		String aSql = " select substr(branchseries,1,12), "
				+ " substr(branchseries,14,12),"
				+ " substr(branchseries,27,12) "
				+ " from labranchgroup "
				+ " where agentgroup = (select branchcode from laagent where agentcode = '?mAgentCode?')";
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(aSql);
		sqlbv3.put("mAgentCode", mAgentCode);

		SSRS nSSRS = new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		nSSRS = tExeSQL.execSQL(sqlbv3);
		mDistict = nSSRS.GetText(1, 1);
		mDepartment = nSSRS.GetText(1, 2);
		mBranchCode = nSSRS.GetText(1, 3);
		logger.debug("代理人----------区：" + mDistict + "  部：" + mDepartment
				+ " 组：" + mBranchCode + "");

		if (mSmoney > 0) {
			CreatLJCertifyGetAgent(mSmoney, "TF");
		}
		if (-mKmoney > 0) {
			CreatLJCertifyGetAgent(mKmoney, "KK");
		}
		CreatLJCertifyGet();
		return true;
	}

	private boolean CreatLJCertifyGetAgent(double cMoney, String cFinFeeType) {
		LJCertifyGetAgentSchema tLJCertifyGetAgentSchema = new LJCertifyGetAgentSchema();
		tLJCertifyGetAgentSchema.setActuGetNo(mNo);
		tLJCertifyGetAgentSchema.setAgentCode(mAgentCode);
		tLJCertifyGetAgentSchema.setGetMoney(cMoney);
		tLJCertifyGetAgentSchema.setFeeFinaType(cFinFeeType);
		tLJCertifyGetAgentSchema.setDepartment(mDepartment);
		tLJCertifyGetAgentSchema.setDistict(mDistict);
		tLJCertifyGetAgentSchema.setBranchCode(mBranchCode);
		tLJCertifyGetAgentSchema.setAgentName(mLAAgentSet.get(1).getName());
		tLJCertifyGetAgentSchema.setBranchType(mLAAgentSet.get(1)
				.getBranchType());
		tLJCertifyGetAgentSchema.setConfDate(CurrentDate);
		tLJCertifyGetAgentSchema.setMakeDate(CurrentDate);
		tLJCertifyGetAgentSchema.setModifyDate(CurrentDate);
		tLJCertifyGetAgentSchema.setMakeTime(CurrentTime);
		tLJCertifyGetAgentSchema.setModifyTime(CurrentTime);
		tLJCertifyGetAgentSchema
				.setManageCom(mLAAgentSet.get(1).getManageCom());
		tLJCertifyGetAgentSchema.setOperator(mGlobalInput.Operator);
		mLJCertifyGetAgentSet.add(tLJCertifyGetAgentSchema);
		return true;
	}

	private boolean CreatLJCertifyGet() {
		mLJCertifyGetSchema.setActuGetNo(mNo);
		mLJCertifyGetSchema.setAgentCode(mAgentCode);
		mLJCertifyGetSchema.setGetMode("1");
		mLJCertifyGetSchema.setOperator(mGlobalInput.Operator);
		mLJCertifyGetSchema.setLastOperator(mGlobalInput.Operator);
		mLJCertifyGetSchema.setManageCom(mGlobalInput.ManageCom);
		mLJCertifyGetSchema.setDrawer(mLAAgentSet.get(1).getName());
		mLJCertifyGetSchema.setDrawerID(mLAAgentSet.get(1).getIDNo());
		mLJCertifyGetSchema.setPolicyCom(mLAAgentSet.get(1).getManageCom());
		mLJCertifyGetSchema.setShouldDate(CurrentDate);
		mLJCertifyGetSchema.setConfDate(CurrentDate);
		mLJCertifyGetSchema.setSumGetMoney(mGetMoney);
		mLJCertifyGetSchema.setMakeDate(CurrentDate);
		mLJCertifyGetSchema.setModifyDate(CurrentDate);
		mLJCertifyGetSchema.setModifyTime(CurrentTime);
		mLJCertifyGetSchema.setMakeTime(CurrentTime);
		return true;
	}

	private boolean prepareOutputData() {
		map.put(mLJCertifyGetSchema, "INSERT");
		map.put(mLJCertifyGetAgentSet, "INSERT");
		mResult.add(map);
		return true;
	}

	public static void main(String[] args) {

		String tAgentCode = "09203438";
		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("AgentCode", tAgentCode);

		VData tVData = new VData(); // 准备往传输数据 VData
		GlobalInput tGI = new GlobalInput();
		tGI.ComCode = "862100";
		tGI.ManageCom = "862100";
		tGI.Operator = "Gaoht";
		tVData.add(tGI);
		tVData.add(tTransferData);

		CertifyGetBL tCertifyGetBL = new CertifyGetBL();
		if (!tCertifyGetBL.submitData(tVData, "")) {

		}

	}
}
