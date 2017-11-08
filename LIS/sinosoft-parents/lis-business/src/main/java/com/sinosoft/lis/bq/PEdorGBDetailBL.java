package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bl.LPDutyBL;
import com.sinosoft.lis.bl.LPGetBL;
import com.sinosoft.lis.bl.LPPolBL;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 领取年龄变更（保全项目代码：GB）项目明细
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author WuHao
 * @version 1.0
 */
public class PEdorGBDetailBL {
private static Logger logger = Logger.getLogger(PEdorGBDetailBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private MMap mMap = new MMap();
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	// 准备校验规则所需要的计算要素
	private BqCalBase mBqCalBase = new BqCalBase();

	Reflections mReflections = new Reflections();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	private LPGetSchema mLPGetSchema = new LPGetSchema();
	private LPPolSchema mLPPolSchema = new LPPolSchema();
	private LPDutySchema mLPDutySchema = new LPDutySchema();

	private LPPolSchema tLPPolSchema = new LPPolSchema();
	private LPDutySchema tLPDutySchema = new LPDutySchema();
	private LPGetSchema tLPGetSchema = new LPGetSchema();

	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private LPGetSet mLPGetSet = new LPGetSet();
	private LPDutySet mLPDutySet = new LPDutySet();

	/** 控制信息传输类 */
	private TransferData mTransferData = new TransferData();

	/** 重算后的领取标准 */
	private String mStandMoney;

	public PEdorGBDetailBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public void setOperate(String cOperate) {
		this.mOperate = cOperate;
	}

	public String getOperate() {
		return this.mOperate;
	}

	// 获得重算后的领取标准金额，可在保全操作完毕后由BLF层加入mResult
	public String getStandMoney() {
		return this.mStandMoney;
	}

	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.setOperate(cOperate);

		if (!cOperate.equals("UPDATE||MAIN")) {
			return false;
		}
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 数据校验操作（checkdata)
		if (!checkData()) {
			return false;
		}

		// 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
		if (!dealData()) {
			return false;
		}
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		mInputData = (VData) cInputData.clone();

		mLPEdorItemSchema = (LPEdorItemSchema) cInputData
				.getObjectByObjectName("LPEdorItemSchema", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mLPGetSchema = (LPGetSchema) mInputData.getObjectByObjectName(
				"LPGetSchema", 0);
		mLPPolSchema = (LPPolSchema) mInputData.getObjectByObjectName(
				"LPPolSchema", 0);
		mLPDutySchema = (LPDutySchema) mInputData.getObjectByObjectName(
				"LPDutySchema", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null || mLPEdorItemSchema == null
				|| mTransferData == null || mLPGetSchema == null
				|| mLPPolSchema == null || mLPDutySchema == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		return true;
	}

	private boolean checkData() {
		boolean flag = true;
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		tLPEdorItemSchema.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
		tLPEdorItemSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
		tLPEdorItemSchema.setEdorType(mLPEdorItemSchema.getEdorType());
		tLPEdorItemSchema.setContNo(mLPEdorItemSchema.getContNo());
		tLPEdorItemSchema.setPolNo(mLPEdorItemSchema.getPolNo());
		tLPEdorItemSchema.setInsuredNo(mLPEdorItemSchema.getInsuredNo());

		tLPEdorItemDB.setSchema(tLPEdorItemSchema);
		if (this.getOperate().equals("UPDATE||MAIN")) {
			if (!tLPEdorItemDB.getInfo()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorGBDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "无保全申请数据!";
				this.mErrors.addOneError(tError);
				return false;
			}
			// 获取保全主表数据，节省查询次数
			mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());
			if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PEdorGBDetailBL";
				tError.functionName = "checkData";
				tError.errorMessage = "该保全已经申请确认不能修改!";
				this.mErrors.addOneError(tError);
				return false;
			}
		}
		return flag;
	}

	/**
	 * 准备需要保存的数据
	 */
	private boolean dealData() {
		// 获得此时的日期和时间
		String strCurrentDate = PubFun.getCurrentDate();
		String strCurrentTime = PubFun.getCurrentTime();
		// 变更后的领取年龄
		logger.debug("Getyear : " + mLPDutySchema.getGetYear());

		// 险种表
		LPPolBL tLPPolBL = new LPPolBL();
		if (!tLPPolBL.queryLastLPPol(this.mLPEdorItemSchema, this.mLPPolSchema)) {
			CError.buildErr(this, "没有查到险种表中信息！");
			return false;
		}
		this.tLPPolSchema.setSchema(tLPPolBL.getSchema());
		if (this.tLPPolSchema.getInsuYear() == this.tLPPolSchema.getGetYear()) {
			this.tLPPolSchema.setInsuYear(mLPPolSchema.getGetYear());
		}
		this.tLPPolSchema.setGetYear(mLPPolSchema.getGetYear());

		// 责任表
		LPDutyBL tLPDutyBL = new LPDutyBL();
		if (!tLPDutyBL.queryLastLPDuty(this.mLPEdorItemSchema,
				this.mLPDutySchema)) {
			CError.buildErr(this, "没有查到责任表中信息！");
			return false;
		}
		this.tLPDutySchema.setSchema(tLPDutyBL.getSchema());

		// 领取表
		LPGetBL tLPGetBL = new LPGetBL();
		if (!tLPGetBL.queryLastLPGet(this.mLPEdorItemSchema, this.mLPGetSchema)) {
			CError.buildErr(this, "没有查到领取表中信息！");
			return false;
		}
		this.tLPGetSchema.setSchema(tLPGetBL.getSchema());

		// 添加计算要素
		mBqCalBase.setPolNo(tLPPolSchema.getPolNo());
		mBqCalBase.setRiskCode(tLPPolSchema.getRiskCode());

		// 领取年龄变更后是否需要重算领取标准的标识
		boolean tReCal = false;
		tReCal = this.ReCalculate(tLPPolSchema, mLPEdorItemSchema);

		if (!tReCal) {
			mErrors.addOneError(new CError("重算失败！"));
			return false;
		}
		LPGetSet tLPGetSet = new LPGetSet();
		LPDutySet tLPDutySet = new LPDutySet();
		// 领取表
		for (int i = 1; i <= mLPGetSet.size(); i++) {
			tLPGetSchema = mLPGetSet.get(i).getSchema();
			if (tLPGetSchema.getPolNo().equals(mLPGetSchema.getPolNo())
					&& tLPGetSchema.getGetDutyCode().equals(
							mLPGetSchema.getGetDutyCode())
					&& tLPGetSchema.getDutyCode().equals(
							mLPGetSchema.getDutyCode())) {
				tLPGetSchema.setModifyDate(strCurrentDate);
				tLPGetSchema.setModifyTime(strCurrentTime);
				tLPGetSchema.setOperator(this.mGlobalInput.Operator);
				tLPGetSet.add(tLPGetSchema);
			}
		}

		// 责任表
		for (int i = 1; i <= mLPDutySet.size(); i++) {
			tLPDutySchema = mLPDutySet.get(i).getSchema();
			if (tLPDutySchema.getPolNo().equals(mLPDutySchema.getPolNo())
					&& tLPDutySchema.getDutyCode().equals(
							mLPDutySchema.getDutyCode())) {
				tLPDutySchema.setModifyDate(strCurrentDate);
				tLPDutySchema.setModifyTime(strCurrentTime);
				tLPDutySchema.setOperator(this.mGlobalInput.Operator);
				tLPDutySet.add(tLPDutySchema);
			}
		}

		// 保单险种表
		tLPPolSchema.setModifyDate(strCurrentDate);
		tLPPolSchema.setModifyTime(strCurrentTime);
		tLPPolSchema.setOperator(this.mGlobalInput.Operator);

		// 修改“个险保全项目表”相应信息
		mLPEdorItemSchema.setEdorState("3");
		mLPEdorItemSchema.setOperator(this.mGlobalInput.Operator);
		mLPEdorItemSchema.setModifyDate(strCurrentDate);
		mLPEdorItemSchema.setModifyTime(strCurrentTime);

		mMap.put(tLPPolSchema, "DELETE&INSERT");
		mMap.put(tLPDutySet, "DELETE&INSERT");
		mMap.put(tLPGetSet, "DELETE&INSERT");
		mMap.put(mLPEdorItemSchema, "UPDATE");

		mResult.clear();
		mResult.add(mMap);
		mResult.add(mBqCalBase);
		return true;
	}

	/**
	 * 根据传入的险种信息和个人保全项目信息重算，得到重算后的领取、责任、险种等信息
	 * 
	 * @param aLPPolSchema
	 *            LPPolSchema
	 * @param aLPEdorItemSchema
	 *            LPEdorItemSchema
	 * @return boolean
	 */
	private boolean ReCalculate(LPPolSchema aLPPolSchema,
			LPEdorItemSchema aLPEdorItemSchema) {

		ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, aLPEdorItemSchema);
		// 准备数据进行重算
		LPEdorItemSchema tLPEdorItemSchema = aLPEdorItemSchema.getSchema();
		// 准备重算需要的保单表数据
		LCPolBL tLCPolBL = tReCalBL.getRecalPol(aLPPolSchema);
		// 变更后的年龄
		tLCPolBL.setGetYear(tLPPolSchema.getGetYear());
		// 准备重算需要的保费项表数据
		LCPremSet tLCPremSet = tReCalBL.getRecalPrem(tLPEdorItemSchema);
		// 准备重算需要的责任表数据
		LCDutyBLSet tLCDutyBLSet = tReCalBL.getRecalDuty(tLPEdorItemSchema);
		// 变更后的年龄
		for (int i = 1; i <= tLCDutyBLSet.size(); i++) {
			if (tLCDutyBLSet.get(i).getInsuYear() == tLCDutyBLSet.get(i)
					.getGetYear()) {
				tLCDutyBLSet.get(i).setInsuYear(mLPDutySchema.getGetYear());
			}
			tLCDutyBLSet.get(i).setGetYear(mLPDutySchema.getGetYear());
		}

		// 准备重算需要的领取项表数据
		LCGetBLSet tLCGetBLSet = tReCalBL.getRecalGet(tLPEdorItemSchema);

		if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPremSet,
				tLCGetBLSet, tLPEdorItemSchema)) {
			this.mErrors.copyAllErrors(tReCalBL.mErrors);
			CError tCError = new CError();
			tCError.buildErr(this, "重算失败");
			mErrors.addOneError(tCError);
			return false;
		}
		aLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1)); // 将计算结果返回
		mLPDutySet.add(tReCalBL.aftLPDutySet);
		mLPGetSet.add(tReCalBL.aftLPGetSet);

		return true;
	}

	/**
	 * 判断是否需要领取时重算领取标准：
	 * 
	 * @param aLPGetSchema
	 *            LPGetSchema
	 * @return boolean true 领取时需要重算，则变更领取年龄后不需要重算领取标准； false
	 *         领取时不需要重算，则变更领取年龄后需要重算领取标准；
	 */
	private boolean needCalculate(LPGetSchema aLPGetSchema) {
		boolean flag = false;
		// BqCalBase tBqCalBase = new BqCalBase();

		String strSQL = "Select a.NeedReCompute from LMDutyGetAlive a"
				+ " where a.GetDutyCode ='?GetDutyCode?'" + " and a.GetDutyKind ='?GetDutyKind?'";
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("GetDutyCode", aLPGetSchema.getGetDutyCode());
			sqlbv.put("GetDutyKind", aLPGetSchema.getGetDutyKind());
			ExeSQL exeSQL = new ExeSQL();
			if (exeSQL.getOneValue(sqlbv).equals("1")) { // 领取时需要计算
				flag = true;
			} else if (exeSQL.getOneValue(sqlbv).equals("0")) { // 领取时不需要计算
				flag = false;
			}
		} catch (Exception e) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorGBDetailBL";
			tError.functionName = "needCalculate";
			tError.errorMessage = "判断领取时是否需要计算领取标准时产生错误!";
			this.mErrors.addOneError(tError);
		}
		return flag;
	}

	private String queryBirthday(LPEdorItemSchema aLPEdorItemSchema) {
		String strSQL = "Select a.Birthday from LcInsured a "
				+ " where a.InsuredNo = '?InsuredNo?'" + " and a.ContNo='?ContNo?'";
		String tBirthday = "";
		logger.debug("queryBirthday strSQL:" + strSQL);
		try {
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(strSQL);
			sqlbv.put("InsuredNo", aLPEdorItemSchema.getInsuredNo());
			sqlbv.put("ContNo", aLPEdorItemSchema.getContNo().toString());
			ExeSQL exeSQL = new ExeSQL();
			tBirthday = exeSQL.getOneValue(sqlbv);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "PEdorGBDetailBL";
			tError.functionName = "queryBirthday";
			tError.errorMessage = "未查询到被保人出生日期!";
			this.mErrors.addOneError(tError);
		}
		return tBirthday;
	}

	public static void main(String[] args) {
		PEdorGBDetailBL pedorgbdetailbl = new PEdorGBDetailBL();

		// GlobalInput tG = new GlobalInput();
		// tG.Operator = "asdf";
		// tG.ManageCom = "86110000";
		//
		// LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
		// tLPEdorItemSchema.setEdorAcceptNo("86000000001213");
		// tLPEdorItemSchema.setEdorNo("410000000001044");
		// tLPEdorItemSchema.setEdorType("GM");
		// tLPEdorItemSchema.setContNo("230110000000534");
		// tLPEdorItemSchema.setInsuredNo("0000498120");
		// tLPEdorItemSchema.setPolNo("210110000001066");
		//
		// LPGetSchema tLPGetSchema = new LPGetSchema();
		//
		// VData tVData = new VData();
		// tVData.add(tG);
		// tVData.add(tLPEdorItemSchema);
		// tVData.add(tLPGetSchema);
		//
		// if (!pedorgbdetailbl.submitData(tVData, "UPDATE|MAIN")) {
		// logger.debug(pedorgbdetailbl.mErrors.getError(0).errorMessage);
		// }

	}

}
