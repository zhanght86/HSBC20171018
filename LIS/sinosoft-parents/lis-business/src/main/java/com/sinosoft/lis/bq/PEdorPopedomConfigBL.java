package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LPEdorPopedomDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LOBEdorPopedomSchema;
import com.sinosoft.lis.schema.LPEdorPopedomSchema;
import com.sinosoft.lis.vschema.LOBEdorPopedomSet;
import com.sinosoft.lis.vschema.LPEdorPopedomSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全权限配置
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author liurx
 * @creatdate:2006-01-13
 * @version 1.0
 */
public class PEdorPopedomConfigBL {
private static Logger logger = Logger.getLogger(PEdorPopedomConfigBL.class);

	public CErrors mErrors = new CErrors();
	private String mOperate;
	// 往后面传输数据的容器
	private VData mInputData = new VData();
	// 往界面传输数据的容器
	private VData mResult = new VData();
	private MMap mMap = new MMap();

	private String mManageCom; // 预留字段支持按机构订制权限
	private String mEdorPopedomType; // 保全权限类型 1-个人（对应riskprop I-个人 Y-银代）
	// 2-团体（对应riskprop G-团体）
	private String mEdorPopedom; // 个人保全权限级别
	private String mGEdorPopedom; // 团体保全权限级别
	private String mLimitGetMoneyI; // 个人保全个人险付费限额
	private String mLimitGetMoneyY; // 个人保全银代付费限额
	private String mLimitGetMoneyL; // 团体保全长期险付费限额
	private String mLimitGetMoneyM; // 团体保全一年期短险付费限额
	private String mLimitGetMoneyS; // 团体保全极短期险付费限额
	private String mModifyAppDateNum; // 申请日期修改权限（天数）

	private String mOperator;
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();
	private LPEdorPopedomSet mLPEdorPopedomSet = new LPEdorPopedomSet();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorPopedomConfigBL() {
	}

	/**
	 * 统一提交接口
	 * 
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();

		if (!getInputData())
			return false;

		if (!checkData())
			return false;

		if (!dealData())
			return false;

		if (!prepareOutputData())
			return false;

		PubSubmit tPubSubmit = new PubSubmit();
		if (!tPubSubmit.submitData(mInputData, "")) {
			mErrors.copyAllErrors(tPubSubmit.mErrors);
			mErrors.addOneError(new CError("在更新数据时发生错误"));
			return false;
		}
		return true;
	}

	/**
	 * 获取传入参数
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mLPEdorPopedomSet = (LPEdorPopedomSet) mInputData
				.getObjectByObjectName("LPEdorPopedomSet", 0);
		if (mGlobalInput == null || mTransferData == null
				|| mLPEdorPopedomSet == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mOperator = mGlobalInput.Operator;
		if (mOperator == null || mOperator.trim().equals("")) {
			mErrors.addOneError(new CError("传入操作员代码为空！"));
			return false;
		}

		mManageCom = (String) mTransferData.getValueByName("ManageCom");
		mEdorPopedomType = (String) mTransferData
				.getValueByName("EdorPopedomType");
		mEdorPopedom = (String) mTransferData.getValueByName("EdorPopedom");
		mGEdorPopedom = (String) mTransferData.getValueByName("GEdorPopedom");
		// mLimitGetMoneyI = (String) mTransferData.getValueByName("LimitGetMoneyI");
		// mLimitGetMoneyY = (String) mTransferData.getValueByName("LimitGetMoneyY");
		// mLimitGetMoneyL = (String) mTransferData.getValueByName("LimitGetMoneyL");
		// mLimitGetMoneyM = (String) mTransferData.getValueByName("LimitGetMoneyM");
		// mLimitGetMoneyS = (String) mTransferData.getValueByName("LimitGetMoneyS");
		mModifyAppDateNum = (String) mTransferData
				.getValueByName("ModifyAppDateNum");

		if (mManageCom == null || mManageCom.trim().equals("")) {
			mErrors.addOneError(new CError("传入管理机构不能为空！"));
			return false;
		}
		return true;
	}

	/**
	 * 校验规则 只有拥有最高保全权限级别的用户才可以配置保全权限
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		String tOperEdorPopedom = null; // 操作员个人保全权限级别
		String tOperGEdorPopedom = null; // 操作员团体保全权限级别
		String tMaxEdorPopedom = null; // 个单保全最高权限
		String tMaxGEdorPopedom = null; // 团单保全最高权限
		ExeSQL tExeSQL = new ExeSQL();
		String strsql = new String("");

		// 当前用户保全权限查询
		strsql = "select trim(usertype), trim(edorpopedom) from ldedoruser where usercode = '?mOperator?'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strsql);
		sqlbv.put("mOperator", mOperator);
		SSRS tssrs = tExeSQL.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() < 1) {
			CError.buildErr(this, "用户权限查询失败！");
			return false;
		}
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			if (tssrs.GetText(i, 1).equals("1")) // usertype 1-个人保全
			{
				tOperEdorPopedom = tssrs.GetText(i, 2);
			}
			if (tssrs.GetText(i, 1).equals("2")) // usertype 2-团体保全
			{
				tOperGEdorPopedom = tssrs.GetText(i, 2);
			}
		}

		// 系统定义最高保全权限查询
		strsql = " select trim(codetype), max(trim(code)) from ldcode where codetype in ('gedorpopedom' , 'edorpopedom') group by codetype ";
		SQLwithBindVariables sbv=new SQLwithBindVariables();
		sbv.sql(strsql);
		tssrs = tExeSQL.execSQL(sbv);
		if (tssrs == null || tssrs.getMaxRow() < 1) {
			CError.buildErr(this, "用户权限查询失败！");
			return false;
		}
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			if (tssrs.GetText(i, 1).equals("edorpopedom")) // edorpopedom
			// 1-个人保全级别定义
			{
				tMaxEdorPopedom = tssrs.GetText(i, 2);
			}
			if (tssrs.GetText(i, 1).equals("gedorpopedom")) // gedorpopedom
			// 2-团体保全级别定义
			{
				tMaxGEdorPopedom = tssrs.GetText(i, 2);
			}
		}

		// 个人保全权限校验
		if(mEdorPopedom !=null && !mEdorPopedom.trim().equals("")){
			if (tOperEdorPopedom == null || tOperEdorPopedom.trim().equals("")) {
				CError.buildErr(this, "对不起，您没有个人保全权限！");
				return false;
			}
			if (tOperEdorPopedom.compareToIgnoreCase(tMaxEdorPopedom) < 0) {
				CError.buildErr(this, "只有最高级别的用户才可以配置保全权限！");
				return false;
			}
		}

		// 团体保全权限校验
		if(mGEdorPopedom !=null && !mGEdorPopedom.trim().equals("")){
			if (tOperGEdorPopedom == null || tOperGEdorPopedom.trim().equals("")) {
				CError.buildErr(this, "对不起，您没有团体保全权限！");
				return false;
			}
			if (tOperGEdorPopedom.compareToIgnoreCase(tMaxGEdorPopedom) != 0) {
				CError.buildErr(this, "只有最高级别的用户才可以配置保全权限！");
				return false;
			}
		}

		return true;
	}

	/**
	 * 业务处理
	 * 
	 * @return boolean
	 */
	public boolean dealData() {
		String sAndRiskProp = "";
		String sEdorPopeDom = "";
		if (mEdorPopedomType.equals("1")) // 个人保全
		{
			sAndRiskProp += " and RiskProp in ('I','Y') ";
			sEdorPopeDom = mEdorPopedom;
		} else if (mEdorPopedomType.equals("2")) // 团体保全
		{
			sAndRiskProp += " and RiskProp = 'G' ";
			sEdorPopeDom = mGEdorPopedom;
		}

		// 删除权限数据
		String delSql = " delete from LPEdorPopedom where "
				+ " EdorPopeDom = '?sEdorPopeDom?' and ManageCom = '?mManageCom?'" + sAndRiskProp;
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(delSql);
		sbv1.put("sEdorPopeDom", sEdorPopeDom);
		sbv1.put("mManageCom", mManageCom);
		mMap.put(sbv1, "DELETE");

		// 备份权限数据
		String strSql = " select * from LPEdorPopedom where "
				+ " EdorPopeDom = '?sEdorPopeDom?' and ManageCom = '?mManageCom?'" + sAndRiskProp;
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(strSql);
		sbv2.put("sEdorPopeDom", sEdorPopeDom);
		sbv2.put("mManageCom", mManageCom);

		LPEdorPopedomDB tLPEdorPopedomDB = new LPEdorPopedomDB();
		LPEdorPopedomSet delLPEdorPopedomSet = tLPEdorPopedomDB
				.executeQuery(sbv2);
		if (delLPEdorPopedomSet != null && delLPEdorPopedomSet.size() > 0) {
			// 产生批次号
			strSql = " select (case when max(SerialNo) is not null then max(SerialNo) else 0 end) from LOBEdorPopedom where "
					+ " EdorPopeDom = '?sEdorPopeDom?' and ManageCom = '?mManageCom?'" + sAndRiskProp;
			SQLwithBindVariables sbv3=new SQLwithBindVariables();
			sbv3.sql(strSql);
			sbv3.put("sEdorPopeDom", sEdorPopeDom);
			sbv3.put("mManageCom", mManageCom);

			ExeSQL tExesql = new ExeSQL();
			int iSerialNo = 1 + Integer.parseInt(tExesql.getOneValue(sbv3));

			// 备份到B表中
			LOBEdorPopedomSet tLOBEdorPopedomSet = new LOBEdorPopedomSet();
			for (int i = 1; i <= delLPEdorPopedomSet.size(); i++) {
				LOBEdorPopedomSchema tLOBEdorPopedomSchema = new LOBEdorPopedomSchema();
				PubFun.copySchema(tLOBEdorPopedomSchema, delLPEdorPopedomSet
						.get(i));
				tLOBEdorPopedomSchema.setEndDate(mCurrentDate);
				tLOBEdorPopedomSchema.setModifyDate(mCurrentDate);
				tLOBEdorPopedomSchema.setModifyTime(mCurrentTime);
				tLOBEdorPopedomSchema.setSerialNo(iSerialNo);
				tLOBEdorPopedomSet.add(tLOBEdorPopedomSchema);
				tLOBEdorPopedomSchema = null;
			}
			mMap.put(tLOBEdorPopedomSet, "INSERT");
		}

		// 重新组合新数据
		LPEdorPopedomSet instLPEdorPopedomSet = new LPEdorPopedomSet();
		LPEdorPopedomSchema tLPEdorPopedomSchema;
		LPEdorPopedomSchema tLPEdorPopedomSchema_other;
		if (mEdorPopedomType.equals("1")) // 个人保全
		{
			for (int i = 1; i <= mLPEdorPopedomSet.size(); i++) {
				tLPEdorPopedomSchema = new LPEdorPopedomSchema();
				tLPEdorPopedomSchema.setManageCom(mManageCom);
				tLPEdorPopedomSchema.setEdorPopedom(mEdorPopedom);
				tLPEdorPopedomSchema.setEdorCode(mLPEdorPopedomSet.get(i)
						.getEdorCode());
				tLPEdorPopedomSchema.setRiskProp("I"); // 个人险
				tLPEdorPopedomSchema.setRiskPeriod("0"); // 个人保全保全不分长短险
				tLPEdorPopedomSchema.setApplyFlag(mLPEdorPopedomSet.get(i)
						.getApplyFlag());
				tLPEdorPopedomSchema.setApproveFlag(mLPEdorPopedomSet.get(i)
						.getApproveFlag());
				tLPEdorPopedomSchema.setAppDateModifyFlag(mModifyAppDateNum);
				tLPEdorPopedomSchema.setLimitGetMoney(mLPEdorPopedomSet.get(i)
						.getLimitGetMoney());
				tLPEdorPopedomSchema.setLimitPayMoney(99999999999999.00);
				tLPEdorPopedomSchema.setStartDate(mCurrentDate);
				tLPEdorPopedomSchema.setOperator(mOperator);
				tLPEdorPopedomSchema.setMakeDate(mCurrentDate);
				tLPEdorPopedomSchema.setMakeTime(mCurrentTime);
				tLPEdorPopedomSchema.setModifyDate(mCurrentDate);
				tLPEdorPopedomSchema.setModifyTime(mCurrentTime);
				instLPEdorPopedomSet.add(tLPEdorPopedomSchema);

				tLPEdorPopedomSchema_other = new LPEdorPopedomSchema();
				PubFun.copySchema(tLPEdorPopedomSchema_other,
						tLPEdorPopedomSchema);
				tLPEdorPopedomSchema_other.setRiskProp("Y"); // 银代险
				tLPEdorPopedomSchema_other.setLimitGetMoney(mLPEdorPopedomSet
						.get(i).getLimitGetMoney());
				tLPEdorPopedomSchema_other.setLimitPayMoney(99999999999999.00);
				instLPEdorPopedomSet.add(tLPEdorPopedomSchema_other);
			}
		}

		if (mEdorPopedomType.equals("2")) // 团体保全
		{
			for (int i = 1; i <= mLPEdorPopedomSet.size(); i++) {
				tLPEdorPopedomSchema = new LPEdorPopedomSchema();
				tLPEdorPopedomSchema.setManageCom(mManageCom);
				tLPEdorPopedomSchema.setEdorPopedom(mGEdorPopedom);
				tLPEdorPopedomSchema.setEdorCode(mLPEdorPopedomSet.get(i)
						.getEdorCode());
				tLPEdorPopedomSchema.setRiskProp("G"); // 团体险
				tLPEdorPopedomSchema.setRiskPeriod("L"); // 长期险
				tLPEdorPopedomSchema.setApplyFlag(mLPEdorPopedomSet.get(i)
						.getApplyFlag());
				tLPEdorPopedomSchema.setApproveFlag(mLPEdorPopedomSet.get(i)
						.getApproveFlag());
				tLPEdorPopedomSchema.setAppDateModifyFlag(mModifyAppDateNum);
				tLPEdorPopedomSchema.setLimitGetMoney(mLPEdorPopedomSet.get(i)
						.getLimitGetMoney());
				tLPEdorPopedomSchema.setLimitPayMoney(99999999999999.00);
				tLPEdorPopedomSchema.setStartDate(mCurrentDate);
				tLPEdorPopedomSchema.setOperator(mOperator);
				tLPEdorPopedomSchema.setMakeDate(mCurrentDate);
				tLPEdorPopedomSchema.setMakeTime(mCurrentTime);
				tLPEdorPopedomSchema.setModifyDate(mCurrentDate);
				tLPEdorPopedomSchema.setModifyTime(mCurrentTime);
				instLPEdorPopedomSet.add(tLPEdorPopedomSchema);

				tLPEdorPopedomSchema_other = new LPEdorPopedomSchema();
				PubFun.copySchema(tLPEdorPopedomSchema_other,
						tLPEdorPopedomSchema);
				tLPEdorPopedomSchema_other.setRiskPeriod("M"); // 一年期短险
				tLPEdorPopedomSchema_other.setLimitGetMoney(mLPEdorPopedomSet
						.get(i).getLimitGetMoney());
				tLPEdorPopedomSchema_other.setLimitPayMoney(99999999999999.00);
				instLPEdorPopedomSet.add(tLPEdorPopedomSchema_other);

				tLPEdorPopedomSchema_other = new LPEdorPopedomSchema();
				PubFun.copySchema(tLPEdorPopedomSchema_other,
						tLPEdorPopedomSchema);
				tLPEdorPopedomSchema_other.setRiskPeriod("S"); // 极短期险
				tLPEdorPopedomSchema_other.setLimitGetMoney(mLPEdorPopedomSet
						.get(i).getLimitGetMoney());
				tLPEdorPopedomSchema_other.setLimitPayMoney(99999999999999.00);
				instLPEdorPopedomSet.add(tLPEdorPopedomSchema_other);
			}
		}

		mMap.put(instLPEdorPopedomSet, "INSERT");

		// 更新保全项目定义表中的保全级别为保全项目最低申请级别
		String updSql = " update lmedoritem m set m.edorpopedom = "
				+ " (select min(edorpopedom) " + "  from LPEdorPopedom "
				+ "  where applyflag = '1' "
				+ "  and edorcode = m.edorcode and RiskProp in ('I','Y')) "
				+ " where m.appobj in ('I', 'B')"; // 个人保全项目
		SQLwithBindVariables sbv4=new SQLwithBindVariables();
		sbv4.sql(updSql);
		mMap.put(sbv4, "UPDATE");

		updSql = " update lmedoritem m set m.edorpopedom = "
				+ " (select min(edorpopedom) " + "  from LPEdorPopedom "
				+ "  where applyflag = '1' "
				+ "  and edorcode = m.edorcode and RiskProp = 'G') "
				+ " where m.appobj = 'G'"; // 团体保全项目
		SQLwithBindVariables sbv5=new SQLwithBindVariables();
		sbv5.sql(updSql);
		mMap.put(sbv5, "UPDATE");

		return true;
	}

	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMap);

		mResult.clear();
		return true;
	}

	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate(String cOperate) {
		return this.mOperate;
	}

	public VData getResult() {
		return mResult;
	}

	public static void main(String[] args) {
		logger.debug("test start:");
		TransferData tTransferData = new TransferData();
		GlobalInput tGI = new GlobalInput();

		tGI.Operator = "001";
		tGI.ComCode = "86";
		tGI.ManageCom = "86";

		tTransferData.setNameAndValue("EdorPopedomType", "2");
		tTransferData.setNameAndValue("EdorPopedom", "");
		tTransferData.setNameAndValue("GEdorPopedom", "A");
		tTransferData.setNameAndValue("ManageCom", "86");
		tTransferData.setNameAndValue("LimitGetMoneyI", "");
		tTransferData.setNameAndValue("LimitGetMoneyY", "");
		tTransferData.setNameAndValue("LimitGetMoneyL", "200000");
		tTransferData.setNameAndValue("LimitGetMoneyM", "200000");
		tTransferData.setNameAndValue("LimitGetMoneyS", "200000");
		tTransferData.setNameAndValue("ModifyAppDateNum", "30");
		LPEdorPopedomSet tLPEdorPopedomSet = new LPEdorPopedomSet();
		tLPEdorPopedomSet
				.decode("||AD|||1|1|0|0|0|||||||^||AG|||1|1|0|0|0|||||||^||AN|||1|1|0|0|0|||||||^||BB|||1|1|0|0|0|||||||^||BC|||1|1|0|0|0|||||||^||BD|||1|1|0|0|0|||||||^||BL|||1|1|0|0|0|||||||^||CT|||0|0|0|0|0|||||||^||EA|||1|1|0|0|0|||||||^||FM|||1|1|0|0|0|||||||^||GB|||1|1|0|0|0|||||||^||GC|||1|1|0|0|0|||||||^||GM|||1|1|0|0|0|||||||^||GR|||1|1|0|0|0|||||||^||GS|||1|1|0|0|0|||||||^||IC|||1|1|0|0|0|||||||^||IO|||1|1|0|0|0|||||||^||IR|||1|1|0|0|0|||||||^||LN|||1|1|0|0|0|||||||^||LR|||1|1|0|0|0|||||||^||MC|||1|1|0|0|0|||||||^||NC|||1|1|0|0|0|||||||^||NI|||1|1|0|0|0|||||||^||OG|||1|1|0|0|0|||||||^||PT|||1|1|0|0|0|||||||^||RD|||1|1|0|0|0|||||||^||RE|||1|1|0|0|0|||||||^||RF|||1|1|0|0|0|||||||^||RT|||1|1|0|0|0|||||||^||SD|||1|1|0|0|0|||||||^||SE|||1|1|0|0|0|||||||^||WA|||1|1|0|0|0|||||||^||WM|||1|1|0|0|0|||||||^||WS|||1|1|0|0|0|||||||^||XS|||1|1|0|0|0|||||||^||XT|||1|1|0|0|0|||||||^||YC|||1|1|0|0|0|||||||^||ZT|||1|1|0|0|0|||||||");

		VData tVData = new VData();
		tVData.add(tTransferData);
		tVData.add(tLPEdorPopedomSet);

		tVData.add(tGI);
		PEdorPopedomConfigBL tPEdorPopedomConfigBL = new PEdorPopedomConfigBL();
		if (!tPEdorPopedomConfigBL.submitData(tVData, "")) {
			logger.debug("test failed:"
					+ tPEdorPopedomConfigBL.mErrors.getFirstError().toString());
		}
		logger.debug("test end");
	}
}
