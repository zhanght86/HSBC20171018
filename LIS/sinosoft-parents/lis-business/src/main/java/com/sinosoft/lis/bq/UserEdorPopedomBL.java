package com.sinosoft.lis.bq;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LDEdorUserSchema;
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
 * Description: 用户保全权限级别指定
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
public class UserEdorPopedomBL {
private static Logger logger = Logger.getLogger(UserEdorPopedomBL.class);

	public CErrors mErrors = new CErrors();
	private String mOperate;
	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	private MMap mMap = new MMap();

	private String mEdorPopedom;
	private String mGEdorPopedom;
	private String mUserCode;
	private String mOperator;
	private GlobalInput mGlobalInput = new GlobalInput();
	private TransferData mTransferData = new TransferData();

	private String mCurrentDate = PubFun.getCurrentDate();
	private String mCurrentTime = PubFun.getCurrentTime();

	public UserEdorPopedomBL() {
	}

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

	private boolean getInputData() {
		mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);

		if (mGlobalInput == null || mTransferData == null) {
			mErrors.addOneError(new CError("数据传输不完全！"));
			return false;
		}
		mOperator = mGlobalInput.Operator;
		mUserCode = (String) mTransferData.getValueByName("UserCode");
		mEdorPopedom = (String) mTransferData.getValueByName("EdorPopedom");
		mGEdorPopedom = (String) mTransferData.getValueByName("GEdorPopedom");

		if (mOperator == null || mOperator.trim().equals("")) {
			mErrors.addOneError(new CError("传入操作员代码为空！"));
			return false;
		}
		if (mUserCode == null || mUserCode.trim().equals("")) {
			mErrors.addOneError(new CError("传入用户代码不能为空！"));
			return false;
		}
		if (mEdorPopedom == null) {
			mErrors.addOneError(new CError("传入权限级别不能为空！"));
			return false;
		}
		if (mGEdorPopedom == null) {
			mErrors.addOneError(new CError("传入权限级别不能为空！"));
			return false;
		}
		return true;
	}

	/**
	 * 校验规则 用户可以给比自己权限低的保全员指定与自己同级别的权限 用户可以给与自己同级别的保全员指定比自己低的权限
	 * 用户不可以调整比自己权限高的保全员的权限级别
	 */
	private boolean checkData() {
		String tOperPopedom = ""; // 操作员个人保全权限级别
		String tOperGPopedom = "";// 操作员团体保全权限级别
		String tUserPopedom = ""; // 保全员个人保全权限级别
		String tUserGPopedom = "";// 保全员体保全权限级别

		if (mOperator.equals(mUserCode)) {
			mErrors.addOneError(new CError("不允许给自己指定权限级别！"));
			return false;
		}
		ExeSQL q_exesql = new ExeSQL();
		String strsql = " select usercode, usertype, edorpopedom from ldedoruser "
				+ " where usercode in (?usercode?) order by usertype ";
		ArrayList<String> strArr=new ArrayList<String>();
		strArr.add(mOperator);
		strArr.add(mUserCode);
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(strsql);
		sqlbv.put("usercode", strArr);
		SSRS tssrs = q_exesql.execSQL(sqlbv);
		if (tssrs == null || tssrs.getMaxRow() < 1) {
			mErrors.addOneError(new CError("用户权限查询失败！"));
			return false;
		}
		for (int i = 1; i <= tssrs.getMaxRow(); i++) {
			if (tssrs.GetText(i, 1).trim().equals(mOperator)) // 当前操作用户
			{
				if (tssrs.GetText(i, 2).trim().equals("1")) // usertype 1-个人保全
				{
					tOperPopedom = tssrs.GetText(i, 3);
				}
				if (tssrs.GetText(i, 2).trim().equals("2")) // usertype 2-团体保全
				{
					tOperGPopedom = tssrs.GetText(i, 3);
				}
			} else if (tssrs.GetText(i, 1).trim().equals(mUserCode)) // 被指定权限的保全员
			{
				if (tssrs.GetText(i, 2).trim().equals("1")) // usertype = 1 个人保全
				{
					tUserPopedom = tssrs.GetText(i, 3);
				}
				if (tssrs.GetText(i, 2).trim().equals("2")) // usertype = 2 团体保全
				{
					tUserGPopedom = tssrs.GetText(i, 3);
				}
			}
		}
		if (!tUserPopedom.equals(mEdorPopedom)) {// 修改了个险权限
			if (tOperPopedom == null || tOperPopedom.trim().equals("")) {
				mErrors.addOneError(new CError("对不起，您没有个险保全权限！"));
				return false;
			}
			if (!mEdorPopedom.equals("")
					&& tOperPopedom.compareToIgnoreCase(mEdorPopedom) < 0) {
				mErrors.addOneError(new CError("给保全员指定的权限" + mEdorPopedom
						+ "大于用户自身的权限" + tOperPopedom + "！"));
				return false;
			}
			if (tUserPopedom != null && !tUserPopedom.equals("")
					&& tUserPopedom.compareToIgnoreCase(tOperPopedom) > 0) {
				mErrors.addOneError(new CError("对不起，您不可以调整比自己权限高的保全员的权限级别！"));
				return false;
			}

		}
		if (!tUserGPopedom.equals(mGEdorPopedom)) {// 修改了团体权限
			if (tOperGPopedom == null || tOperGPopedom.trim().equals("")) {
				mErrors.addOneError(new CError("对不起，您没有团体保全权限！"));
				return false;
			}
			if (!mGEdorPopedom.equals("")
					&& tOperGPopedom.compareToIgnoreCase(mGEdorPopedom) < 0) {
				mErrors.addOneError(new CError("给保全员指定的权限" + mGEdorPopedom
						+ "大于用户自身的权限" + tOperPopedom + "！"));
				return false;
			}
			if (tUserGPopedom != null && !tUserGPopedom.equals("")
					&& tUserGPopedom.compareToIgnoreCase(tOperGPopedom) > 0) {
				mErrors.addOneError(new CError("对不起，您不可以调整比自己权限高的保全员的权限级别！"));
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
	private boolean dealData() {
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(" delete from ldedoruser where usercode = '?mUserCode?'");
		sqlbv1.put("mUserCode", mUserCode);
		mMap.put(sqlbv1,"DELETE");
		if (!mEdorPopedom.equals("")) // 个险权限指
		{
			LDEdorUserSchema iLDEdorUserSchema = new LDEdorUserSchema();
			iLDEdorUserSchema.setUserCode(mUserCode);
			iLDEdorUserSchema.setUserType("1");
			iLDEdorUserSchema.setUpUserCode(mUserCode);
			iLDEdorUserSchema.setUpEdorPopedom(mEdorPopedom);
			iLDEdorUserSchema.setEdorPopedom(mEdorPopedom);
			iLDEdorUserSchema.setUserState("0"); // 0-有效
			iLDEdorUserSchema.setValidStartDate(mCurrentDate);
			iLDEdorUserSchema.setOperator(mOperator);
			iLDEdorUserSchema.setMakeDate(mCurrentDate);
			iLDEdorUserSchema.setMakeTime(mCurrentTime);
			iLDEdorUserSchema.setManageCom(mGlobalInput.ManageCom);
			iLDEdorUserSchema.setModifyDate(mCurrentDate);
			iLDEdorUserSchema.setModifyTime(mCurrentTime);
			mMap.put(iLDEdorUserSchema, "INSERT");
		}
		if (!mGEdorPopedom.equals("")) // 团体权限
		{
			LDEdorUserSchema gLDEdorUserSchema = new LDEdorUserSchema();
			gLDEdorUserSchema.setUserCode(mUserCode);
			gLDEdorUserSchema.setUserType("2");
			gLDEdorUserSchema.setUpUserCode(mUserCode);
			gLDEdorUserSchema.setUpEdorPopedom(mGEdorPopedom);
			gLDEdorUserSchema.setEdorPopedom(mGEdorPopedom);
			gLDEdorUserSchema.setUserState("0"); // 0-有效
			gLDEdorUserSchema.setValidStartDate(mCurrentDate);
			gLDEdorUserSchema.setOperator(mOperator);
			gLDEdorUserSchema.setManageCom(mGlobalInput.ManageCom);
			gLDEdorUserSchema.setMakeDate(mCurrentDate);
			gLDEdorUserSchema.setMakeTime(mCurrentTime);
			gLDEdorUserSchema.setModifyDate(mCurrentDate);
			gLDEdorUserSchema.setModifyTime(mCurrentTime);
			mMap.put(gLDEdorUserSchema, "INSERT");
		}

		return true;
	}

	private boolean prepareOutputData() {
		mInputData.clear();
		mInputData.add(mMap);

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
		VData tVData = new VData();

		GlobalInput tGlobalInput = new GlobalInput();
		tGlobalInput.ManageCom = "86110000";
		tGlobalInput.Operator = "wangle";

		TransferData mTransferData = new TransferData();
		mTransferData.setNameAndValue("UserCode", "zhangchao");
		mTransferData.setNameAndValue("EdorPopedom", "P");
		mTransferData.setNameAndValue("GEdorPopedom", "G");

		tVData.add(tGlobalInput);
		tVData.add(mTransferData);
		UserEdorPopedomBL tUserEdorPopedomBL = new UserEdorPopedomBL();
		if (!tUserEdorPopedomBL.submitData(tVData, "")) {
			logger.debug("test failed:"
					+ tUserEdorPopedomBL.mErrors.getFirstError().toString());
		}
		String ss = null;
		logger.debug(ss);
	}
}
