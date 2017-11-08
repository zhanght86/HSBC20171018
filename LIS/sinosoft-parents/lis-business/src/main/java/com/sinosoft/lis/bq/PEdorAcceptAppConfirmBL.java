package com.sinosoft.lis.bq;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LWMissionDB;
import com.sinosoft.lis.f1print.PrintTool;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPEdorPrintSchema;
import com.sinosoft.lis.schema.LWMissionSchema;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LWMissionSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExportNew;
import com.sinosoft.workflowengine.ActivityOperator;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 保全受理申请确认
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: Sinosoft
 * </p>
 * 
 * @author zhangtao
 * @version 1.0
 */
public class PEdorAcceptAppConfirmBL {
private static Logger logger = Logger.getLogger(PEdorAcceptAppConfirmBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	@SuppressWarnings("unused")
	private String mOperate;
	private ExeSQL mExeSql = new ExeSQL();
	private String mLocalCur = "";
	/** 全局数据 */
	MMap map = new MMap();
	private GlobalInput mGlobalInput;
	private LPEdorAppSchema mLPEdorAppSchema = new LPEdorAppSchema();
	private TransferData mTransferData = new TransferData();
	private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
	private List curList = new ArrayList();

	// 系统当前时间
	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public PEdorAcceptAppConfirmBL() {
	}

	/**
	 * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
	 * 
	 * @param cInputData
	 * @param cOperate
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 将操作数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;

		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData()) {
			return false;
		}
		logger.debug("---End getInputData---");

		if (!checkData()) {
			return false;
		}
		logger.debug("---End checkData---");

		// 数据操作业务处理
		if (!dealData()) {
			return false;
		}
		logger.debug("---End dealData---");

		if (!prepareOutputData()) {
			return false;
		}
		logger.debug("---End prepareOutputData---");

		return true;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 数据输出方法，供外界获取数据处理结果
	 * 
	 * @return
	 */
	public boolean dealData() {
		// 执行各个保全项目的申请确认方法
		if (!doPEdorXXAppConfirm(mLPEdorItemSet)) {
			return false;
		}
		


		// 统计变动保费等
		if (!sumChang()) {
			return false;
		}
 
		// 统一更新批改状态与核保状态
		if (!updState()) {
			return false;
		}

		if (!printData()) {
			return false;
		}

		// 备份并删除保全受理和无扫描受理节点任务
		if (!delMission()) {
			return false;
		}

		return true;
	}

	/**
	 * 从输入数据中得到所有对象
	 * 
	 * @return boolean
	 */
	private boolean getInputData() {
		try {
			mLPEdorAppSchema = (LPEdorAppSchema) mInputData
					.getObjectByObjectName("LPEdorAppSchema", 0);
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);

		} catch (Exception e) {
			CError.buildErr(this, "接收数据失败");
			return false;
		}

		return true;
	}

	/**
	 * 校验
	 * 
	 * @return boolean
	 */
	private boolean checkData() {
		// 校验收付费方式信息
		String sBankCode = mLPEdorAppSchema.getBankCode();
		String sBankAccNo = mLPEdorAppSchema.getBankAccNo();
		String sAccName = mLPEdorAppSchema.getAccName();
		String sPayGetName = mLPEdorAppSchema.getPayGetName();
		String sPersonID = mLPEdorAppSchema.getPersonID();
		String sPayForm = mLPEdorAppSchema.getPayForm();
		String sGetForm = mLPEdorAppSchema.getGetForm();
		if (sPayForm == null || sPayForm.trim().equals("") || sGetForm == null
				|| sGetForm.trim().equals("")) {
			CError.buildErr(this, "对不起，您还没有录入收付费方式!");
			return false;
		}
		//add by jiaqiangli 2009-05-31 网银代付
		if (sPayForm.trim().equals("4") || sPayForm.trim().equals("9")) {
			// 银行划款或网上支付
			if (sBankCode == null || sBankCode.trim().equals("")
					|| sBankAccNo == null || sBankAccNo.trim().equals("")
					|| sAccName == null || sAccName.trim().equals("")) {
				CError.buildErr(this, "银行收付费信息不完整!");
				return false;
			}
		} else {
			sBankCode = "";
			sBankAccNo = "";
			sAccName = "";
		}
		// 查询保全受理信息
		LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
		tLPEdorAppDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		if (!tLPEdorAppDB.getInfo()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询保全受理失败";
			mErrors.addOneError(tError);
			return false;
		}

		mLPEdorAppSchema.setSchema(tLPEdorAppDB.getSchema());

		// 校验该保全受理下是否还有未录入的保全项目
		LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		tLPEdorItemDB.setEdorState("3"); // 保全明细未录入
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询保全项目失败";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLPEdorItemSet != null && mLPEdorItemSet.size() > 0) {
			CError tError = new CError();
			tError.errorMessage = "该保全受理下还有保全项目明细没有录入";
			mErrors.addOneError(tError);
			return false;
		}

		// 查询出该保全受理下所有的保全项目信息
		mLPEdorItemSet.clear();
		tLPEdorItemDB = new LPEdorItemDB();
		tLPEdorItemDB.setEdorAcceptNo(mLPEdorAppSchema.getEdorAcceptNo());
		mLPEdorItemSet = tLPEdorItemDB.query();
		if (tLPEdorItemDB.mErrors.needDealError()) {
			mErrors.copyAllErrors(tLPEdorAppDB.mErrors);
			CError tError = new CError();
			tError.errorMessage = "查询保全项目失败!";
			mErrors.addOneError(tError);
			return false;
		}
		if (mLPEdorItemSet == null || mLPEdorItemSet.size() < 1) {
			CError tError = new CError();
			tError.errorMessage = "该保全受理下没有保全项目!";
			mErrors.addOneError(tError);
			return false;
		}
		mLPEdorAppSchema.setGetForm(sGetForm);
		mLPEdorAppSchema.setPayForm(sPayForm);
		mLPEdorAppSchema.setBankCode(sBankCode);
		mLPEdorAppSchema.setBankAccNo(sBankAccNo);
		mLPEdorAppSchema.setAccName(sAccName);
		mLPEdorAppSchema.setPayGetName(sPayGetName);
		mLPEdorAppSchema.setPersonID(sPersonID);
		// 校验补退费领取人信息
		if (!checkPayGetPersonInfo(mLPEdorAppSchema)) {
			return false;
		}

		// <!-- XinYQ added on 2006-03-14 : 通过收费方式确定付费领取方式 : BGN -->
		if (!checkAppEdorGetForm())
			return false;
		// <!-- XinYQ added on 2006-03-14 : 通过收费方式确定付费领取方式 : END -->

		// 如果领取人为空，给出默认值
		if (mLPEdorAppSchema.getPayGetName() == null
				|| mLPEdorAppSchema.getPayGetName().equals("")) {
			if (mLPEdorAppSchema.getOtherNoType().equals("3")) // 个人保单号
			{
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mLPEdorAppSchema.getOtherNo());
				LCContSet tLCContSet = tLCContDB.query();
				if (tLCContDB.mErrors.needDealError()) {
					CError.buildErr(this, "保单查询失败!");
					return false;
				}
				if (tLCContSet == null || tLCContSet.size() != 1) {
					CError.buildErr(this, "该保单不存在! 保单号："
							+ mLPEdorAppSchema.getOtherNo());
					return false;
				}

				String sHasEdorType = hasEdorType(mLPEdorAppSchema
						.getEdorAcceptNo(), "'AG'");
				if (sHasEdorType == null) {
					return false;
				} else if (sHasEdorType.equals("N")) // 没有年金满期金给付（AG）默认为投保人
				{
					mLPEdorAppSchema.setPayGetName(tLCContSet.get(1)
							.getAppntName());
					mLPEdorAppSchema.setPersonID(tLCContSet.get(1)
							.getAppntIDNo());
				} else // 如果有年金满期金给付（AG） 默认为被保人
				{
					mLPEdorAppSchema.setPayGetName(tLCContSet.get(1)
							.getInsuredName());
					mLPEdorAppSchema.setPersonID(tLCContSet.get(1)
							.getInsuredIDNo());
				}
			} else if (mLPEdorAppSchema.getOtherNoType().equals("1")) // 个人客户号
			{
				// 默认为申请人
				mLPEdorAppSchema.setPayGetName(mLPEdorAppSchema.getEdorAppName());

			}
		}
		//查询本币
		String tSQL = "select codename from ldcode1 where codetype = 'currencyprecision' and code1 = (select sysvarvalue from ldsysvar where sysvar = 'nativeplace')";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		mLocalCur = mExeSql.getOneValue(sqlbv);
		if(mLocalCur == null ||mLocalCur.equals("")){
			CError.buildErr(this, "查询本币定义失败");
			return false;
		}
		map.put(mLPEdorAppSchema, "UPDATE");
		return true;
	}

	@SuppressWarnings("unchecked")
	private boolean prepareOutputData() {

		mInputData.clear();
		mInputData.add(map);
		mResult.clear();
		mResult.add(map);
		return true;
	}

	// --------------------------------------------------------------------------

	// <!-- XinYQ added on 2006-03-14 : 通过收费方式确定付费领取方式 : BGN -->
	/**
	 * 通过收费方式确定付费领取方式
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean checkAppEdorGetForm() {
		String sExactGetForm = EdorVerifyBL.getRefundGetForm(mLPEdorAppSchema
				.getEdorAcceptNo());
		// 检查传入的领取方式是否和正确的付费方式一致
		if (sExactGetForm != null
				&& !sExactGetForm.trim().equals("")
				&& !mLPEdorAppSchema.getGetForm().trim().equalsIgnoreCase(
						sExactGetForm)) {
			String QuerySQL = new String("");
			QuerySQL = "select CodeName " + "from LDCode " + "where 1 = 1 "
					+ "and CodeType = 'edorgetpayform' " + "and Code = '?sExactGetForm?'";
			SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			sqlbv.sql(QuerySQL);
			sqlbv.put("sExactGetForm", sExactGetForm);
			ExeSQL tExeSQL = new ExeSQL();
			String sExactGetName = tExeSQL.getOneValue(sqlbv);
			tExeSQL = null;
			CError.buildErr(this, "犹豫期退费付费方式必须与新契约收费方式(" + sExactGetForm + "-"
					+ sExactGetName + ")一致！");
			return false;
		}
		return true;
	}

	// <!-- XinYQ added on 2006-03-14 : 通过收费方式确定付费领取方式 : END -->

	// --------------------------------------------------------------------------

	/**
	 * 调用各个保全项目的申请确认方法
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	private boolean doPEdorXXAppConfirm(LPEdorItemSet tLPEdorItemSet) {
		for (int i = 1; i <= tLPEdorItemSet.size(); i++) {
			LPEdorItemSchema tLPEdorItemSchema = tLPEdorItemSet.get(i);

			// //校验个单保全申请的明细信息是否完整 暂时注掉
			// if (!verifyEdorDetail(tLPEdorItemSchema))
			// {
			// return false;
			// }

			String sEdortype = tLPEdorItemSchema.getEdorType();
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
						+ sEdortype + "AppConfirmBL");
				EdorAppConfirm tPEdorAppConfirm = (EdorAppConfirm) tClass
						.newInstance();

				VData tVData = new VData();
				tVData.add(tLPEdorItemSchema);
				tVData.add(mGlobalInput);

				if (!tPEdorAppConfirm.submitData(tVData, "APPCONFIRM||"
						+ sEdortype)) {
					CError.buildErr(this, "处理保单号为："
							+ tLPEdorItemSchema.getPolNo() + "保全项目为:"
							+ sEdortype + "的保全申请确认时失败！");
					return false;
				} else {
					VData rVData = tPEdorAppConfirm.getResult();
					MMap tMap = (MMap) rVData.getObjectByObjectName("MMap", 0);
					if (tMap != null) {
						map.add(tMap);
					}
				}
			} catch (ClassNotFoundException ex) {
				// 没有不用处理
				logger.debug("====PEdor" + sEdortype
						+ "AppConfirmBL not found====");
			} catch (Exception ex) {
				ex.printStackTrace();
				CError.buildErr(this, "调用保全项目" + sEdortype + "的申请确认方法时失败！");
				return false;
			}
			//处理保全项目的Currency;
			if(!dealCurrency(tLPEdorItemSchema)){
				return false;
			}
			
		}
		return true;
	}

	/**
	 * 统计变动保费、变动保额、补/退费金额、补/退费利息
	 * 
	 * @return boolean
	 */
	private boolean sumChang() {
		String tEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
		String wherePartApp = "where EdorAcceptNo='" + "?tEdorAcceptNo?" + "'";
		String wherePartMain = "where EdorAcceptNo='" + "?tEdorAcceptNo?" + "'"
				+ " and m.edorno = i.edorno ";
		StringBuffer sbSQL = new StringBuffer();

		// 批单层
		sbSQL.append(" UPDATE LPEdorMain m set ").append(
				" ChgPrem = (select sum(ChgPrem) from LPEdorItem i ").append(
				wherePartMain).append("), ").append(
				" ChgAmnt = (select sum(ChgAmnt) from LPEdorItem i ").append(
				wherePartMain).append("), ").append(
				" GetMoney = (select sum(GetMoney) from LPEdorItem i ").append(
				wherePartMain).append("), ").append(
				" GetInterest = (select sum(GetInterest) from LPEdorItem i ")
				.append(wherePartMain).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv1, "UPDATE");

		// 保全申请层
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp a set ").append(
				" ChgPrem = (select sum(ChgPrem) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" ChgAmnt = (select sum(ChgAmnt) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" GetMoney = (select sum(GetMoney) from LPEdorItem ").append(
				wherePartApp).append("), ").append(
				" GetInterest = (select sum(GetInterest) from LPEdorItem ")
				.append(wherePartApp).append(") ").append(wherePartApp);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv2, "UPDATE");

		return true;
	}

	/**
	 * 统一更新保全受理、保全申请批单、保全项目的 批改状态为申请确认[2]，核保状态为未核保[0]
	 * 
	 * @return boolean
	 */
	private boolean updState() {
		String tEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
		String wherePart = "where EdorAcceptNo='" + "?tEdorAcceptNo?" + "'";
		String tCurrency = "";
		if(curList.size()>0){
			if(curList.size()>1){
				tCurrency = mLocalCur;
			}else{
				tCurrency = (String)curList.get(0);
			}
		}
		StringBuffer sbSQL = new StringBuffer();

		// 保全项目
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorItem set EdorState = '2' , UWFlag = '0', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(sbSQL.toString());
		sbv1.put("Operator", mGlobalInput.Operator);
		sbv1.put("mCurrentDate", mCurrentDate);
		sbv1.put("mCurrentTime", mCurrentTime);
		sbv1.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv1, "UPDATE");

		// 保全批单
		sbSQL.setLength(0);
		sbSQL
				.append(
						" UPDATE LPEdorMain set EdorState = '2' , UWState = '0', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(sbSQL.toString());
		sbv2.put("Operator", mGlobalInput.Operator);
		sbv2.put("mCurrentDate", mCurrentDate);
		sbv2.put("mCurrentTime", mCurrentTime);
		sbv2.put("tEdorAcceptNo", tEdorAcceptNo);
		map.put(sbv2, "UPDATE");

		// 保全申请
		sbSQL.setLength(0);
		sbSQL.append(" UPDATE LPEdorApp set EdorState = '2' , UWState = '0', ")
				.append(" Currency = '").append("?tCurrency?").append("', ")
				.append(" Operator = '").append("?Operator?").append(
						"', ModifyDate = '").append("?mCurrentDate?").append(
						"', ModifyTime = '").append("?mCurrentTime?").append("' ")
				.append(wherePart);
		SQLwithBindVariables sbv3=new SQLwithBindVariables();
		sbv3.sql(sbSQL.toString());
		sbv3.put("Operator", mGlobalInput.Operator);
		sbv3.put("mCurrentDate", mCurrentDate);
		sbv3.put("mCurrentTime", mCurrentTime);
		sbv3.put("tEdorAcceptNo", tEdorAcceptNo);
		sbv3.put("tCurrency", tCurrency);
		map.put(sbv3, "UPDATE");

		return true;
	}

	/**
	 * 备份并删除保全受理和无扫描受理节点任务
	 * 
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	private boolean delMission() {
		String sYBTFlag = (String) mTransferData.getValueByName("YBT");
		if (sYBTFlag != null && sYBTFlag.equals("YBT")) {
			return true;
		}
		String sMissionID = (String) mTransferData.getValueByName("MissionID");

		String sql = " select * from lwmission where ActivityID in ('0000000001', '0000000002') "
				+ " and missionid = '" + "?sMissionID?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sMissionID", sMissionID);
		LWMissionDB tLWMissionDB = new LWMissionDB();
		LWMissionSet tLWMissionSet = tLWMissionDB.executeQuery(sqlbv);
		if (tLWMissionDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询工作流保全申请任务节点失败");
			return false;
		}
		if (tLWMissionSet == null || tLWMissionSet.size() < 1) {
			return true;
		}

		TransferData trf = new TransferData();
		VData tVdata = new VData();
		tVdata.add(trf);
		for (int i = 1; i <= tLWMissionSet.size(); i++) {
			LWMissionSchema tLWMissionSchema = tLWMissionSet.get(i);
			ActivityOperator tActivityOperator = new ActivityOperator();
			if (!tActivityOperator.DeleteMission(tLWMissionSchema
					.getMissionID(), tLWMissionSchema.getSubMissionID(),
					tLWMissionSchema.getActivityID(), tVdata)) {
				CError tError = new CError();
				tError.errorMessage = "工作流保全撤销节点删除失败!";
				mErrors.addOneError(tError);
				return false;
			}

			VData tempVData = tActivityOperator.getResult();
			if ((tempVData != null) && (tempVData.size() > 0)) {
				MMap tmap = (MMap) tempVData.getObjectByObjectName("MMap", 0);
				map.add(tmap);
			}
		}


		return true;
	}




    /**批单打印 支持保单级别客户信息，险种信息的变更，
     * 但支持客户级别保单信息的变更会有问题，即变更同一客户下多张保单
	   可能需要通过项目分支来区分 
	   changed by pst on 2008-11-26*/
	@SuppressWarnings({ "unchecked", "static-access" })
	private boolean printData() {
		String mEdorAcceptNo = mLPEdorAppSchema.getEdorAcceptNo();
		XmlExportNew xmlExport = new XmlExportNew();
		logger.debug("\n\nStart Write Print Data\n\n");
		logger.debug("EdorAcceptNo ================= > " + mEdorAcceptNo);
		// 一个变更出一张批单
		ExeSQL aExeSQL = new ExeSQL();
		SSRS aSSRS = new SSRS();
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql("select  edorno,edortype,contno from lpedoritem where edoracceptno = '"
				+ "?mEdorAcceptNo?" + "' group by edorno,edortype,contno");
		sqlbv.put("mEdorAcceptNo", mEdorAcceptNo);
		aSSRS = aExeSQL
				.execSQL(sqlbv);
		for (int j = 1; j <= aSSRS.getMaxRow(); j++) {
			// 存放批改保单级别信息，支持保单级别客户信息，险种信息的变更，但支持客户级别保单信息的变更会有问题，即变更同一客户下多张保单
			//但可以通过项目分支来区分
			LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
			tLPEdorItemSchema.setEdorAcceptNo(mEdorAcceptNo);
			tLPEdorItemSchema.setEdorNo(aSSRS.GetText(j, 1));
			tLPEdorItemSchema.setEdorType(aSSRS.GetText(j, 2));
			tLPEdorItemSchema.setContNo(aSSRS.GetText(j, 3));
			xmlExport.createDocument("个人保全申请书");
			String customerNo = "";
			if("3".equals(mLPEdorAppSchema.getOtherNoType())){
				LCContDB tLCContDB = new LCContDB();
				tLCContDB.setContNo(mLPEdorAppSchema.getOtherNo());
				tLCContDB.getInfo();
				customerNo = tLCContDB.getAppntNo();
			}else{
				customerNo = mLPEdorAppSchema.getOtherNo();
			}
			String uLanguage = PrintTool.getCustomerLanguage(customerNo);
			if (uLanguage != null && !"".equals(uLanguage)) 
				xmlExport.setUserLanguage(uLanguage);//用户语言
			xmlExport.setSysLanguage(PrintTool.getSysLanguage(mGlobalInput));//系统语言

			boolean mClassflag = true;
			ExeSQL tExeSQL = new ExeSQL();
			SQLwithBindVariables sbv=new SQLwithBindVariables();
			sbv.sql("select edorname from lmedoritem where edorcode = '"
					+ "?edorcode?" + "'");
			sbv.put("edorcode", aSSRS.GetText(j, 2));
			String EdorName = tExeSQL
					.getOneValue(sbv);
			try {
				Class tClass = Class.forName("com.sinosoft.lis.bq.PEdor"
						+ tLPEdorItemSchema.getEdorType() + "PrintBL");
				EdorPrint tEdorPrint = (EdorPrint) tClass.newInstance();
				VData aVData = new VData();
				aVData.add(tLPEdorItemSchema);
				aVData.add(mLPEdorAppSchema); // 有时需要从主表中判断本次保全的收付费方式，故添加保全主申请表信息
				aVData.add(xmlExport);
				aVData.add(mGlobalInput);
				if (!tEdorPrint.submitData(aVData, "PRINT"
						+ tLPEdorItemSchema.getEdorType())) {
					//mErrors.copyAllErrors(tEdorPrint.mErrors);
					mErrors.addOneError("保全项目" + EdorName + "打印处理失败!原因是"+tEdorPrint.mErrors.getFirstError());
					return false;
				}
				VData cVData = new VData();
				cVData = tEdorPrint.getResult();
				xmlExport = (XmlExportNew) cVData.getObjectByObjectName(
						"XmlExportNew", 0);
			} catch (ClassNotFoundException ex) {
				mClassflag = false;
				logger.debug("未找到" + EdorName + "保全项目打印处理!");
			} catch (Exception ex) {
				mClassflag = false;
				CError.buildErr(this, "保全项目" + EdorName + "打印处理失败!");
				return false;
			}
			logger.debug("成功完成" + EdorName + "保全项目打印处理!");
			if (mClassflag) {

				// 生成主打印批单schema
				LPEdorPrintSchema tLPEdorPrintSchemaMain = new LPEdorPrintSchema();
				tLPEdorPrintSchemaMain.setEdorNo(tLPEdorItemSchema.getEdorNo());
				tLPEdorPrintSchemaMain.setManageCom(mGlobalInput.ManageCom);
				tLPEdorPrintSchemaMain.setPrtFlag("N");
				tLPEdorPrintSchemaMain.setPrtTimes(0);
				tLPEdorPrintSchemaMain.setMakeDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setMakeTime(PubFun.getCurrentTime());
				tLPEdorPrintSchemaMain.setOperator(mGlobalInput.Operator);
				tLPEdorPrintSchemaMain.setModifyDate(PubFun.getCurrentDate());
				tLPEdorPrintSchemaMain.setModifyTime(PubFun.getCurrentTime());
				InputStream ins = xmlExport.getInputStream();

				tLPEdorPrintSchemaMain.setEdorInfo(ins);
				SQLwithBindVariables dsbv=new SQLwithBindVariables();
				dsbv.sql("delete from LPEdorPrint where EdorNo='"
						+ "?EdorNo?" + "'");
				dsbv.put("EdorNo", tLPEdorItemSchema.getEdorNo());
				map.put(dsbv, "DELETE");
				map.put(tLPEdorPrintSchemaMain, "BLOBINSERT");
			}
		}
		return true;
	}
	

	/**
	 * 判断是否包含保全项目，如果包含则返回最晚保全生效日期
	 * 
	 * @param sEdorAcceptNo
	 * @param sEdorType
	 * @return String
	 */
	private String hasEdorType(String sEdorAcceptNo, String sEdorType) {
		String sql = " select max(edorvalidate) from lpedoritem where edortype in ("
				+ "?sEdorType?" + ") and edoracceptno = '" + "?sEdorAcceptNo?" + "'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sql);
		sqlbv.put("sEdorType", sEdorType);
		sqlbv.put("sEdorAcceptNo", sEdorAcceptNo);
		ExeSQL tExeSQL = new ExeSQL();
		String sEdorValidate = tExeSQL.getOneValue(sqlbv);
		if (tExeSQL.mErrors.needDealError()) {
			CError.buildErr(this, "保全项目查询失败");
			return null;
		}
		if (sEdorValidate == null || sEdorValidate.trim().equals("")) {
			return "N";
		} else {
			if (sEdorValidate.length() > 10) {
				sEdorValidate = sEdorValidate.substring(0, 10);
			}
			return sEdorValidate;
		}

	}

	/**
	 * 校验银行账户信息是否完整
	 * 
	 * @param pLPEdorAppSchema
	 * @return boolean
	 */
	private boolean checkPayGetPersonInfo(LPEdorAppSchema pLPEdorAppSchema) {
		// 校验领取人信息是否完整
		if (pLPEdorAppSchema.getPayGetName() != null
				&& !pLPEdorAppSchema.getPayGetName().trim().equals("")) {
			if (pLPEdorAppSchema.getPersonID() == null
					|| pLPEdorAppSchema.getPersonID().trim().equals("")) {
				CError.buildErr(this, "请录入领取人身份证号!");
				return false;
			}
		}
		if (pLPEdorAppSchema.getPersonID() != null
				&& !pLPEdorAppSchema.getPersonID().trim().equals("")) {
			if (pLPEdorAppSchema.getPayGetName() == null
					|| pLPEdorAppSchema.getPayGetName().trim().equals("")) {
				CError.buildErr(this, "请录入领取人姓名!");
				return false;
			}
		}

		if (pLPEdorAppSchema.getPayForm() != null
				&& (pLPEdorAppSchema.getPayForm().trim().equals("4") || pLPEdorAppSchema
						.getPayForm().trim().equals("7"))
				&& pLPEdorAppSchema.getOtherNoType().equals("1")) // 客户层
		{
			if (pLPEdorAppSchema.getPersonID() == null
					|| pLPEdorAppSchema.getPersonID().trim().equals("")
					|| pLPEdorAppSchema.getPayGetName() == null
					|| pLPEdorAppSchema.getPayGetName().trim().equals("")) {
				CError.buildErr(this, "选择银行收付费方式时必须录入领取人及身份证号码!");
				return false;
			}
		}
		return true;
	}

	
	@SuppressWarnings("unchecked")
	private boolean dealCurrency(LPEdorItemSchema tLPEdorItemSchema) {
		String tSQL = "Select distinct Currency "
			+ "  from LJSGetEndorse l "
			+ " where l.EndorsementNo  = '"+"?EndorsementNo?"+"' "
			+ "   and OtherNo != '000000'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(tSQL);
		sqlbv.put("EndorsementNo", tLPEdorItemSchema.getEdorNo());
		String tPartSql = "";
		double dLocalMoney = 0.0;//统一按补费算~补费为正
		SSRS curSSRS = mExeSql.execSQL(sqlbv);
		String tCurrency = "";
		if(curSSRS == null || curSSRS.getMaxRow()==0){
			//查保单的
			tSQL = "Select distinct Currency from lcpol where contno= '"+"?contno?"+"'";
			sqlbv=new SQLwithBindVariables();
			sqlbv.sql(tSQL);
			sqlbv.put("contno", tLPEdorItemSchema.getContNo());
			curSSRS = mExeSql.execSQL(sqlbv);
			if(curSSRS == null ||curSSRS.getMaxRow()==0){
				CError.buildErr(this, "查询币种定义失败");
				return false;
			}
			if(curSSRS.getMaxRow()>1){
				tCurrency = mLocalCur;

			}else{
				tCurrency = curSSRS.GetText(1, 1);
			}

		}else if(curSSRS.getMaxRow()>0){
			
			boolean changCur = false;
			
			if(curSSRS.getMaxRow()>1){
				tCurrency = mLocalCur;
			}else{
				tCurrency = curSSRS.GetText(1, 1);
				changCur = true;
			}
			
			if(changCur){
				//转
				tSQL = "Select getflag,currency,getmoney "
					+ "  from LJSGetEndorse l "
					+ " where l.EndorsementNo  = '"+"?EndorsementNo?"+"' "
					+ "   and OtherNo != '000000'";
				sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSQL);
				sqlbv.put("EndorsementNo", tLPEdorItemSchema.getEdorNo());
				SSRS tSSRS = mExeSql.execSQL(sqlbv);
				LDExch tLDExch = new LDExch();
				
				for(int i = 1;i<=tSSRS.getMaxRow();i++){
					String tGetFlag = tSSRS.GetText(i, 1);
					String tCur = tSSRS.GetText(i, 2);
					String tGetMoney = tSSRS.GetText(i, 3);
					tGetFlag = GetOrPayMoney(tGetFlag);
					double dGetMoney = Double.parseDouble(tGetMoney);
					
					dGetMoney = tLDExch.toBaseCur(tCur, tCurrency, tLPEdorItemSchema.getEdorValiDate(), dGetMoney);
					if ("Y".equals(tGetFlag)) // 补费
					{
						dLocalMoney = Arith.add(dLocalMoney,dGetMoney);
					} else if ("N".equals(tGetFlag)) // 退费
					{
						dLocalMoney = Arith.add(dLocalMoney,-dGetMoney);
					}
					
				}
				tPartSql = " ,GetMoney = ?dLocalMoney? ";
			}
		}
		if(!curList.contains(tCurrency)){
			curList.add(tCurrency);
		}
		String upItemSQL = "Update LPEdorItem set Currency = '"+"?tCurrency?"+"' "+tPartSql+" where EdorNo = '"+"?EdorNo?"+"' and EdorType = '"+"?EdorType?"+"'"; 
		SQLwithBindVariables sbv1=new SQLwithBindVariables();
		sbv1.sql(upItemSQL);
		sbv1.put("tCurrency", tCurrency);
		sbv1.put("dLocalMoney", dLocalMoney);
		sbv1.put("EdorNo", tLPEdorItemSchema.getEdorNo());
		sbv1.put("EdorType", tLPEdorItemSchema.getEdorType());
		map.put(sbv1, "UPDATE");
		String upMainSQL = "Update LPEdorMain set Currency = '"+"?tCurrency?"+"' "+tPartSql+" where EdorNo = '"+"?EdorNo?"+"'";
		SQLwithBindVariables sbv2=new SQLwithBindVariables();
		sbv2.sql(upItemSQL);
		sbv2.put("tCurrency", tCurrency);
		sbv2.put("dLocalMoney", dLocalMoney);
		sbv2.put("EdorNo", tLPEdorItemSchema.getEdorNo());
		map.put(sbv2, "UPDATE");
		return true;
	}
	
	private String GetOrPayMoney(String GetFlag) {
		String tFlag = "";
		if ("1".equals(GetFlag)) {
			tFlag = "N";
			return tFlag;
		} else if ("0".equals(GetFlag)) {
			tFlag = "Y";
			return tFlag;
		}
		return tFlag;
	}

	public static void main(String[] args) {

	}

}
