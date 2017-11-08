package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.operfee.AutoReNewDeal;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.TaxCalculator;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.vschema.LCDutySet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LJSPayPersonSet;
import com.sinosoft.lis.vschema.LJSPaySet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
/**
 * <p>Title: Web业务系统 </p>
 * <p>Description: 满期降低保额续保确认类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author Nicholas
 * @version 1.0
 */
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LJSPayPersonDB;

public class PEdorERConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorERConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	//public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;

	/** 业务数据 */
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private GlobalInput mGlobalInput = new GlobalInput();

	// private Reflections mReflections = new Reflections();

	public PEdorERConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串，主要包括""和""
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		// 数据校验
		if (!checkData()) {
			return false;
		}

		logger.debug("after checkData...");

		// 进行业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return 包含有数据查询结果字符串的VData对象
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorItemSchema = (LPEdorItemSchema) mInputData
					.getObjectByObjectName("LPEdorItemSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERConfirmBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "接收数据失败！";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 数据校验
	 * 
	 * @return: boolean
	 */
	private boolean checkData() {
		return true;
	}

	/**
	 * 根据前面的输入数据，进行逻辑处理
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		try {
			// 获得此时的日期和时间
			String strCurrentDate = PubFun.getCurrentDate();
			String strCurrentTime = PubFun.getCurrentTime();

			String tSqla = "UPDATE LPRNPolAmnt set State='0'," + " Operator='?Operator?',"
					+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD')," + " ModifyTime='?strCurrentTime?'" + " WHERE ContNo='?ContNo?' and EdorNo <> '?EdorNo?'";
			SQLwithBindVariables sbv1=new SQLwithBindVariables();
			sbv1.sql(tSqla);
			sbv1.put("Operator", this.mGlobalInput.Operator);
			sbv1.put("strCurrentDate", strCurrentDate);
			sbv1.put("strCurrentTime", strCurrentTime);
			sbv1.put("ContNo", mLPEdorItemSchema.getContNo());
			sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			mMap.put(sbv1, "UPDATE");
			String tSqlb = "UPDATE LPRNPolAmnt set State='1'," + " Operator='?Operator?',"
					+ " ModifyDate=to_date('?strCurrentDate?','YYYY-MM-DD')," + " ModifyTime='?strCurrentTime?'" + " WHERE EdorNo='?EdorNo?'" + " and EdorType='?EdorType?'";
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql(tSqlb);
			sbv2.put("Operator", this.mGlobalInput.Operator);
			sbv2.put("strCurrentDate", strCurrentDate);
			sbv2.put("strCurrentTime", strCurrentTime);
			sbv2.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv2.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			mMap.put(sbv2, "UPDATE");
			String tSqlc = "DELETE FROM LPPol" + " WHERE EdorNo='?EdorNo?'" + " and EdorType='?EdorType?'" + " and ContNo='?ContNo?'";
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(tSqlc);
			sbv3.put("EdorNo", mLPEdorItemSchema.getEdorNo());
			sbv3.put("EdorType", mLPEdorItemSchema.getEdorType());
			sbv3.put("ContNo", mLPEdorItemSchema.getContNo());
			mMap.put(sbv3, "DELETE");

			// 如果续期已经抽档，则更新抽档数据
			if (!getNewOperFeeData()) {
				return false;
			}

			mResult.clear();
			mResult.add(mMap);
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERConfirmBL";
			tError.functionName = "dealData";
			tError.errorMessage = "数据处理错误！ " + e.getMessage();
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 如果已经有续期抽档，需要按照减额后的保额进行重算保费，更新续期抽档数据
	 * 
	 * @return boolean
	 */
	private boolean getNewOperFeeData() {
		// 查询是否有续期抽档数据，如果有，则需要更新续期抽档数据
		String tSql = "SELECT * FROM LJSPay WHERE OtherNoType in ('1','2','3') and OtherNo='?OtherNo?'";
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(tSql);
		sbv1.put("OtherNo", mLPEdorItemSchema.getContNo());
		LJSPayDB tLJSPayDB = new LJSPayDB();
		LJSPaySet tLJSPaySet = new LJSPaySet();
		tLJSPaySet = tLJSPayDB.executeQuery(sbv1);
		if (tLJSPaySet == null || tLJSPaySet.size() <= 0) {
			// 没有续期抽档，不需要做处理
			return true;
		}
		LJSPaySchema tLJSPaySchema = tLJSPaySet.get(1);

		// 校验是否有暂交费记录，如果已经有暂交费，不能直接更新续期抽档数据，需要做暂交费退费和续期回退
		// （此校验功能已在保全申请时添加校验）
		tSql = "SELECT 'X' FROM LJTempFee WHERE TempFeeNo='?TempFeeNo?'";
		sbv1=new SQLwithBindVariables();
		sbv1.sql(tSql);
		sbv1.put("TempFeeNo", tLJSPaySchema.getGetNoticeNo());
		ExeSQL tExeSQL = new ExeSQL();
		SSRS tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sbv1);
		if (tSSRS != null && tSSRS.MaxRow > 0) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorERConfirmBL";
			tError.functionName = "getNewOperFeeData";
			tError.errorMessage = "保单已有暂交费，如仍要满期降额续保，请退费后重新操作！";
			this.mErrors.addOneError(tError);
			return false;
		}

		// 从将额续保轨迹表中查出本次保全选择降额续保的险种及其新保额
		tSql = "SELECT distinct a.PolNo,a.s_Amnt,"
				+ " (case when exists(select 'x' from LCDuty b where PolNo=a.PolNo and exists(select 'y' from LMDuty where DutyCode=b.DutyCode and AmntFlag='1')) then '1'"
				+ " else '2'" + " end)"
				+ " FROM (select PolNo,sum(Amnt) as s_Amnt"
				+ " from LPRNPolAmnt" + " where EdorNo='?EdorNo?'" + " and EdorType='?EdorType?'" + " group by PolNo) a";
		sbv1=new SQLwithBindVariables();
		sbv1.sql(tSql);
		sbv1.put("EdorNo", mLPEdorItemSchema.getEdorNo());
		sbv1.put("EdorType", mLPEdorItemSchema.getEdorType());
		tSSRS = new SSRS();
		tSSRS = tExeSQL.execSQL(sbv1);
		if (tSSRS == null || tSSRS.MaxRow <= 0) {
			return true;
		}

		LCPolSet tAllLCPolSet = new LCPolSet();
		LCDutySet tAllLCDutySet = new LCDutySet();
		LCPremSet tAllLCPremSet = new LCPremSet();
		LCGetSet tAllLCGetSet = new LCGetSet();
		LJSPayPersonSet tAllLJSPayPersonSet = new LJSPayPersonSet();

		LCPolDB tLCPolDB = new LCPolDB();
		LCPolSchema tLCPolSchema = null;
		// 循环所有降额续保的险种进行重算
		for (int i = 1; i <= tSSRS.MaxRow; i++) {
			// 查询原有险种信息（本期尚未终止的，appflag=1）
			tLCPolDB.setPolNo(tSSRS.GetText(i, 1));
			if (!tLCPolDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorERConfirmBL";
				tError.functionName = "getNewOperFeeData";
				tError.errorMessage = "查询保单险钟信息失败！";
				this.mErrors.addOneError(tError);
				return false;
			}
			tLCPolSchema = tLCPolDB.getSchema();
			if ("2".equals(tSSRS.GetText(i, 3))) {
				tLCPolSchema.setMult(tSSRS.GetText(i, 2));
			} else {
				tLCPolSchema.setAmnt(tSSRS.GetText(i, 2));
			}

			VData tVData = new VData();
			LCPolSchema tForReCalLCPolSchema = tLCPolSchema.getSchema();
			tVData.add(tForReCalLCPolSchema);
			tVData.add(this.mGlobalInput);
			AutoReNewDeal tAutoReNewDeal = new AutoReNewDeal(mLPEdorItemSchema
					.getEdorNo(), mLPEdorItemSchema.getEdorType());
			if (!tAutoReNewDeal.submitData(tVData, "Deal")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorERConfirmBL";
				tError.functionName = "getNewOperFeeData";
				tError.errorMessage = "保单号:" + tLCPolSchema.getContNo() + "，"
						+ tLCPolSchema.getRiskCode() + "附加险自动续保计算保费失败！";
				this.mErrors.addOneError(tError);
				return false;
			}

			// 获得重算后的数据
			LCPolSchema tNewLCPolSchema = (LCPolSchema) tAutoReNewDeal
					.getResult().getObjectByObjectName("LCPolSchema", 0);
			LCDutySet tNewLCDutySet = (LCDutySet) tAutoReNewDeal.getResult()
					.getObjectByObjectName("LCDutyBLSet", 0);
			LCPremSet tNewLCPremSet = (LCPremSet) tAutoReNewDeal.getResult()
					.getObjectByObjectName("LCPremBLSet", 0);
			LCGetSet tNewLCGetSet = (LCGetSet) tAutoReNewDeal.getResult()
					.getObjectByObjectName("LCGetBLSet", 0);

			// 查询原有续期抽档的险种信息（appflag=9）
			tSql = "SELECT * FROM LCPol WHERE ContNo='?ContNo?' and RiskCode = '?RiskCode?' and PolNo <> '?PolNo?' and AppFlag = '9'";
			sbv1=new SQLwithBindVariables();
			sbv1.sql(tSql);
			sbv1.put("ContNo", tLCPolDB.getContNo());
			sbv1.put("RiskCode", tLCPolDB.getRiskCode());
			sbv1.put("PolNo", tLCPolDB.getPolNo());
			LCPolSet tLCPolSet = new LCPolSet();
			tLCPolSet = tLCPolDB.executeQuery(sbv1);
			if (tLCPolSet == null || tLCPolSet.size() <= 0) {
				continue;
			}
			// 备份原有续期抽档的PolNo
			String sPolNo_old = tLCPolSet.get(1).getPolNo();

			// 将重算后的数据PolNo换回原有续期抽档的PolNo。因为调用续期的类又产生了新的PolNo(投保单号也换回，因为可能又新产生了投保单号)
			tNewLCPolSchema.setPolNo(sPolNo_old);
			tNewLCPolSchema.setProposalNo(sPolNo_old);
			for (int nds = 1; nds <= tNewLCDutySet.size(); nds++) {
				tNewLCDutySet.get(nds).setPolNo(sPolNo_old);
			}
			for (int nps = 1; nps <= tNewLCPremSet.size(); nps++) {
				tNewLCPremSet.get(nps).setPolNo(sPolNo_old);
			}
			for (int ngs = 1; ngs <= tNewLCGetSet.size(); ngs++) {
				tNewLCGetSet.get(ngs).setPolNo(sPolNo_old);
			}
			tAllLCPolSet.add(tNewLCPolSchema);
			tAllLCDutySet.add(tNewLCDutySet);
			tAllLCPremSet.add(tNewLCPremSet);
			tAllLCGetSet.add(tNewLCGetSet);

			// 删除原有续期抽档的数据
			SQLwithBindVariables sbv2=new SQLwithBindVariables();
			sbv2.sql("DELETE FROM LCDuty WHERE PolNo='?sPolNo_old?'");
			sbv2.put("sPolNo_old", sPolNo_old);
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql("DELETE FROM LCPrem WHERE PolNo='?sPolNo_old?'");
			sbv3.put("sPolNo_old", sPolNo_old);
			SQLwithBindVariables sbv4=new SQLwithBindVariables();
			sbv4.sql("DELETE FROM LCGet WHERE PolNo='?sPolNo_old?'");
			sbv4.put("sPolNo_old", sPolNo_old);
			mMap.put(sbv2,
					"DELETE");
			mMap.put(sbv3,
					"DELETE");
			mMap.put(sbv4,
					"DELETE");

			// 查询原有续期抽档应收数据
			LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
			tLJSPayPersonDB.setPolNo(sPolNo_old);
			LJSPayPersonSet tLJSPayPersonSet = tLJSPayPersonDB.query();
			if (tLJSPayPersonDB.mErrors.needDealError()) {
				CError.buildErr(this, "续期应收信息查询失败!");
				return false;
			}
			if (tLJSPayPersonSet == null || tLJSPayPersonSet.size() < 1) {
				CError.buildErr(this, "续期应收信息查询失败!");
				return false;
			}

			// 根据重算后的保费项更新续期应收数据
			String sDutyCode_Prem;
			String sPayPlanCode_Prem;
			String sDutyCode_Pay;
			String sPayPlanCode_Pay;
			for (int iPrem = 1; iPrem <= tNewLCPremSet.size(); iPrem++) {
				sDutyCode_Prem = tNewLCPremSet.get(iPrem).getDutyCode();
				sPayPlanCode_Prem = tNewLCPremSet.get(iPrem).getPayPlanCode();
				double dPrem = tNewLCPremSet.get(iPrem).getPrem();
				for (int k = 1; k <= tLJSPayPersonSet.size(); k++) {
					sDutyCode_Pay = tLJSPayPersonSet.get(k).getDutyCode();
					sPayPlanCode_Pay = tLJSPayPersonSet.get(k).getPayPlanCode();
					if (sDutyCode_Prem.equals(sDutyCode_Pay)
							&& sPayPlanCode_Prem.equals(sPayPlanCode_Pay)) {
						tLJSPayPersonSet.get(k).setSumDuePayMoney(dPrem);
						tLJSPayPersonSet.get(k).setSumActuPayMoney(dPrem);
						break;
					}
				}
			}

			// 按险种更新打印管理子表
			tSql = "UPDATE LOPRTManagerSub SET DuePayMoney = ?DuePayMoney? WHERE GetNoticeNo='?GetNoticeNo?' and OtherNoType='00' and OtherNo='?OtherNo?' and RiskCode='?RiskCode?'";
			SQLwithBindVariables sbv5=new SQLwithBindVariables();
			sbv5.sql(tSql);
			sbv5.put("DuePayMoney", tNewLCPolSchema.getPrem());
			sbv5.put("GetNoticeNo", tLJSPaySchema.getGetNoticeNo());
			sbv5.put("OtherNo", tLCPolSchema.getContNo());
			sbv5.put("RiskCode", tLCPolSchema.getRiskCode());
			mMap.put(sbv5, "UPDATE");
	         //营改增 add zhangyingfeng 2016-07-14
	          //价税分离 计算器
	          TaxCalculator.calBySchemaSet(tLJSPayPersonSet);
	          //end zhangyingfeng 2016-07-14
			tAllLJSPayPersonSet.add(tLJSPayPersonSet);
		}
		mMap.put(tAllLCPolSet, "UPDATE");
		mMap.put(tAllLCDutySet, "INSERT");
		mMap.put(tAllLCPremSet, "INSERT");
		mMap.put(tAllLCGetSet, "INSERT");
		mMap.put(tAllLJSPayPersonSet, "UPDATE");

		// 更新应收总表
		tSql = " update ljspay s set sumduepaymoney = "
				+ " (select sum(sumduepaymoney) from ljspayperson p where p.getnoticeno = s.getnoticeno) "
				+ " where s.getnoticeno = '?getnoticeno?' ";
		SQLwithBindVariables sbv6=new SQLwithBindVariables();
		sbv6.sql(tSql);
		sbv6.put("getnoticeno", tLJSPaySchema.getGetNoticeNo());
		mMap.put(sbv6, "UPDATE");

		return true;
	}
}
