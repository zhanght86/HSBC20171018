package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.db.LJAPayDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LCGrpPolSchema;
import com.sinosoft.lis.schema.LJAPayGrpSchema;
import com.sinosoft.lis.schema.LJAPaySchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.tb.LCContSignBL;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.lis.vschema.LJAPayGrpSet;
import com.sinosoft.lis.vschema.LJAPaySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全申请确认处理类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author Minim
 * @ReWrite: ZhangRong
 * @version 1.0
 */

public class GrpEdorNIConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(GrpEdorNIConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局基础数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 签单调用传入的集体保单号 */

	/** 传入的业务数据 */
	private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
	private LJAPayGrpSet mLJAPayGrpSet = new LJAPayGrpSet();

	public GrpEdorNIConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括"INSERT"
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		this.mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 数据准备操作
		if (!prepareOutputData()) {
			return false;
		}

		return true;
	}

	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
					.getObjectByObjectName("LPGrpEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			e.printStackTrace();
			CError tError = new CError();
			tError.moduleName = "GEdorNIDetailBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败!!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	private boolean dealData() {
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorNo(mLPGrpEdorItemSchema.getEdorNo());
		LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemSet == null || tLPEdorItemSet.size() < 1) {
			CError.buildErr(this, "查询新增人的保全批改信息失败!");
			return false;
		}
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			LCContDB tLCContDB = new LCContDB();
			tLCContDB.setContNo(tLPEdorItemSet.get(i).getContNo());
			if (!tLCContDB.getInfo()) {
				CError.buildErr(this, "查询新增人个单信息失败!");
				return false;
			}
			if (tLCContDB.getAppFlag().equals("2")) {
				LCContSet tLCContSet = new LCContSet();
				tLCContSet.add(tLCContDB.getSchema());
				TransferData tTransferData = new TransferData();
				tTransferData.setNameAndValue("EdorNo", mLPGrpEdorItemSchema
						.getEdorNo());
				tTransferData.setNameAndValue("EdorAcceptNo",
						mLPGrpEdorItemSchema.getEdorAcceptNo());
				tTransferData.setNameAndValue("EdorType", mLPGrpEdorItemSchema
						.getEdorType());

				VData vContPub = new VData();
				mGlobalInput.ComCode = mGlobalInput.ManageCom;
				vContPub.add(mGlobalInput);
				vContPub.add(tLCContSet);
				vContPub.add(tTransferData);

				// 准备签单数据
				logger.debug("Start 准备签单数据...");

				LCContSignBL tLCContSignBL = new LCContSignBL(); // 调用个单合同签单程序
				if (!tLCContSignBL.submitData(vContPub, "INSERT||ENDORSE")) {
					mErrors.copyAllErrors(tLCContSignBL.mErrors);
					mErrors.addOneError(new CError("新被保人签单失败！"));
					return false;
				}

				VData tResultData = tLCContSignBL.getResult();
				MMap tMap = (MMap) tResultData.getObjectByObjectName("MMap", 0);
				if (tMap == null) {
					mErrors.copyAllErrors(tLCContSignBL.mErrors);
					mErrors.addOneError(new CError("获得签单程序返回数据失败!"));
				}
				mMap.add(tMap);
			}
		}

		String tGrpContNo = mLPGrpEdorItemSchema.getGrpContNo();

		LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		tLCGrpContDB.setGrpContNo(tGrpContNo);
		if (!tLCGrpContDB.getInfo()) {
			mErrors.copyAllErrors(tLCGrpContDB.mErrors);
			mErrors.addOneError(new CError("查询集体保单数据失败！"));
			return false;
		}

		String tSql = "";
		LJAPayDB tLJAPayDB = new LJAPayDB();
		tLJAPayDB.setOtherNo(mLPGrpEdorItemSchema.getEdorAcceptNo());
		LJAPaySet tLJAPaySet = tLJAPayDB.query();
		if (tLJAPaySet == null || tLJAPaySet.size() <= 0) {
			mErrors.copyAllErrors(tLJAPayDB.mErrors);
			mErrors.addOneError(new CError("查询实收数据失败！"));
			return false;
		}
		LJAPaySchema tLJAPaySchema = tLJAPaySet.get(1);

		LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
		tLCGrpPolDB.setGrpContNo(tGrpContNo);
		LCGrpPolSet tLCGrpPolSet = tLCGrpPolDB.query();
		if (tLCGrpPolSet == null || tLCGrpPolSet.size() <= 0) {
			mErrors.copyAllErrors(tLCGrpPolDB.mErrors);
			mErrors.addOneError(new CError("没有找到集体保单险种表数据！"));
			return false;
		}

		int n = tLCGrpPolSet.size();
		LCGrpPolSchema tLCGrpPolSchema = null;
		for (int i = 1; i <= n; i++) {
			tLCGrpPolSchema = tLCGrpPolSet.get(i);

			// 计算总保费
			String dBF = "";
			ExeSQL tExeSQL = new ExeSQL();
			tSql = "select SUM(Prem) from LCPol where ContNo in (select ContNo from LPEdorItem where EdorNo = '"
					+ mLPGrpEdorItemSchema.getEdorNo()
					+ "') and RiskCode = '"
					+ tLCGrpPolSchema.getRiskCode() + "'";
			dBF = tExeSQL.getOneValue(tSql);
			if (dBF == null || dBF.equals("")) {
				continue;
			}
			logger.debug("险种总保费为：" + dBF);

			LJAPayGrpSchema tLJAPayGrpSchema = new LJAPayGrpSchema();
			tLJAPayGrpSchema.setGrpContNo(tGrpContNo);
			tLJAPayGrpSchema.setGrpPolNo(tLCGrpPolSchema.getGrpPolNo());
			tLJAPayGrpSchema.setPayCount(1);
			tLJAPayGrpSchema.setGrpContNo(tLCGrpPolSchema.getGrpContNo());
			tLJAPayGrpSchema.setAppntNo(tLCGrpContDB.getAppntNo());
			tLJAPayGrpSchema.setPayNo(tLJAPaySchema.getPayNo());
			tLJAPayGrpSchema.setEndorsementNo(mLPGrpEdorItemSchema.getEdorNo());
			tLJAPayGrpSchema.setSumDuePayMoney(dBF);
			tLJAPayGrpSchema.setSumActuPayMoney(dBF);
			tLJAPayGrpSchema.setPayIntv(tLCGrpPolSchema.getPayIntv());
			tLJAPayGrpSchema.setPayDate(tLJAPaySchema.getPayDate());
			tLJAPayGrpSchema.setPayType(mLPGrpEdorItemSchema.getEdorType());
			tLJAPayGrpSchema.setEnterAccDate(tLJAPaySchema.getEnterAccDate());
			tLJAPayGrpSchema.setConfDate(PubFun.getCurrentDate());
			tLJAPayGrpSchema.setLastPayToDate("1899-12-31");
			tLJAPayGrpSchema.setCurPayToDate(tLCGrpPolSchema.getPaytoDate());
			tLJAPayGrpSchema.setSerialNo(tLJAPaySchema.getSerialNo());
			tLJAPayGrpSchema.setOperator(mGlobalInput.Operator);
			tLJAPayGrpSchema.setMakeDate(PubFun.getCurrentDate());
			tLJAPayGrpSchema.setMakeTime(PubFun.getCurrentTime());
			tLJAPayGrpSchema.setModifyDate(PubFun.getCurrentDate());
			tLJAPayGrpSchema.setModifyTime(PubFun.getCurrentTime());
			tLJAPayGrpSchema.setManageCom(tLCGrpPolSchema.getManageCom());
			tLJAPayGrpSchema.setAgentCom(tLCGrpPolSchema.getAgentCom());
			tLJAPayGrpSchema.setAgentType(tLCGrpPolSchema.getAgentType());
			tLJAPayGrpSchema.setRiskCode(tLCGrpPolSchema.getRiskCode());
			tLJAPayGrpSchema.setAgentCode(tLCGrpPolSchema.getAgentCode());
			tLJAPayGrpSchema.setAgentGroup(tLCGrpPolSchema.getAgentGroup());

			mLJAPayGrpSet.add(tLJAPayGrpSchema);
		}

		// 更新PolNo，ContNo
		// String updateno = "update ljapayperson a set (polno,contno) ="
		// + " (select polno,contno from lcpol where a.polno = proposalno and
		// grpcontno = a.grpcontno and appflag = '1') "
		// + " where payno = '" + tLJAPaySchema.getPayNo() + "'"
		// + " and grpcontno = '" + mLPGrpEdorItemSchema.getGrpContNo() + "'";
		// mMap.put(updateno,"UPDATE");

		// mLPGrpEdorItemSchema.setEdorState("0");
		// mLPGrpEdorItemSchema.setEdorValiDate(PubFun.getCurrentDate());
		// mLPGrpEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
		// mLPGrpEdorItemSchema.setModifyTime(PubFun.getCurrentTime());

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据
	 * 
	 * @return 如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		mMap.put(mLJAPayGrpSet, "INSERT");
		// mMap.put(mLPGrpEdorItemSchema, "UPDATE");
		mMap
				.put(
						"update LPEdorMain a set edorstate = '0',ContNo = (select ContNo from LCCont b where a.ContNo = b.ProposalContNo) "
								+ "where a.EdorNo = '"
								+ mLPGrpEdorItemSchema.getEdorNo() + "'",
						"UPDATE"); // 个人批单主表中合同保单换号
		mMap
				.put(
						"update LPEdorItem a set edorstate = '0',ContNo = (select ContNo from LCCont b where a.ContNo = b.ProposalContNo) "
								+ "where a.EdorNo = '"
								+ mLPGrpEdorItemSchema.getEdorNo() + "'",
						"UPDATE"); // 个人批单主表中合同保单换号
		// 修改LCGrpCont投保人数Peoples2,amnt,prem,mult
		mMap
				.put(
						"update lcgrpcont a set (peoples2,amnt,prem,mult) = (select nvl(sum(1),0),nvl(sum(amnt),0),nvl(sum(prem),0),nvl(sum(mult),0) from lccont where grpcontno = a.grpcontno and appflag = '1') where a.grpcontno = '"
								+ mLPGrpEdorItemSchema.getGrpContNo() + "'",
						"UPDATE");
		// 修改LCGrpPol投保人数Peoples2,amnt,prem,mult
		mMap
				.put(
						"update lcgrppol a set (peoples2,amnt,prem,mult) = (select nvl(sum(1),0),nvl(sum(amnt),0),nvl(sum(prem),0),nvl(sum(mult),0) from lcpol where grppolno = a.grppolno and appflag = '1') where a.grpcontno = '"
								+ mLPGrpEdorItemSchema.getGrpContNo() + "'",
						"UPDATE");
		String tCurrentDate = PubFun.getCurrentDate();
		String tCurrentTime = PubFun.getCurrentTime();

		// 待具体情况进行修改
		// mMap.put("update LCCont set SignDate = '" + tCurrentDate + "',
		// SignTime = '" + tCurrentTime + "' where GrpContNo = '"
		// + mLPGrpEdorItemSchema.getGrpContNo() + "' and AppFlag = '1' and
		// (SignDate is null or SignTime is null)", "UPDATE");
		// mMap.put("update LCPol set SignDate = '" + tCurrentDate + "',
		// SignTime = '" + tCurrentTime + "' where GrpContNo = '"
		// + mLPGrpEdorItemSchema.getGrpContNo() + "' and AppFlag = '1' and
		// (SignDate is null or SignTime is null)", "UPDATE"); //设置签单时间
		// mMap.put("update LCPol a set PayEndDate = (select PayEndDate from
		// LCGrpPol where GrpContNo = '" + mLPGrpEdorItemSchema.getGrpContNo() +
		// "' and LCGrpPol.RiskCode = a.RiskCode), "
		// + "PayToDate = (select PayToDate from LCGrpPol where GrpContNo = '" +
		// mLPGrpEdorItemSchema.getGrpContNo() + "' and LCGrpPol.RiskCode =
		// a.RiskCode), "
		// + "EndDate = (select PayEndDate from LCGrpPol where GrpContNo = '" +
		// mLPGrpEdorItemSchema.getGrpContNo() + "' and LCGrpPol.RiskCode =
		// a.RiskCode) "
		// + "where GrpContNo = '" + mLPGrpEdorItemSchema.getGrpContNo() + "'
		// and AppFlag = '1'", "UPDATE"); //设置交止日期，责任终止日期等同原团单相同

		mResult.clear();
		mResult.add(mMap);
		return true;
	}

}
