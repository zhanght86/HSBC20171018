package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.schema.LJAGetOtherSchema;
import com.sinosoft.lis.schema.LJAGetSchema;
import com.sinosoft.lis.vbl.LJSGetBLSet;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LJSGetOtherSet;
import com.sinosoft.lis.vschema.LJSGetSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class LCManageFeeBL {
private static Logger logger = Logger.getLogger(LCManageFeeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 往前面传输数据的容器 */
	private VData mInputData;
	private VData mResult = new VData();
	private LCGrpContSet tLCGrpContSet = new LCGrpContSet();
	private LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();

	private String mCurrentDate;
	private String mCurrentTime;
	private String mOperate;
	private LJAGetSchema tLJSGetSchema = new LJAGetSchema(); // 应付数据（一个领取人只有一条记录）

	private LJAGetOtherSchema tLJSGetOtherSchema = new LJAGetOtherSchema(); // 其他退费应付表（一个领取人只有一条记录）
	private LJSGetSet tLJSGetSet = new LJSGetBLSet();
	private LJSGetOtherSet tLJSGetOtherSet = new LJSGetOtherSet();
	private String flag = "0"; // 如果等于1说明不需要进行手续费处理
	private String FORMATMODOL = "0.00"; // 计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	public LCManageFeeBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		mInputData = (VData) cInputData.clone();

		// 得到外部传入的数据，将数据备份到本类中
		if (!getInputData(cInputData)) {
			return false;
		}

		// 进行业务处理
		if (!dealData()) {
			return false;
		}

		// 准备往后台的数据
		this.prepareOutputData();

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, "INSERT")) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 * 
	 * @param vData
	 *            VData
	 * @return boolean
	 */
	private void prepareOutputData() {

		// map.put("tLJSGetOtherSet", "INSERT");
		// map.put("tLJSGetSet", "INSERT");

		mInputData.clear();
		mInputData.add(map);
	}

	private boolean prepareOutputData(VData vData) {
		try {

		} catch (Exception ex) {
			ex.printStackTrace();
			buildError("prepareOutputData", "发生异常");
			return false;
		}
		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 * 
	 * @return boolean
	 */
	private boolean dealData() {

		String GrpContno = tLCGrpContSchema.getGrpContNo();
		ExeSQL tExeSQL = new ExeSQL();
		// 查询保单下的通知书号，根据通知书号进行处理，一个通知书号代表一个领取人

		// String tSql =
		// "select distinct GetNoticeNo from lcfeecontrol where grpcontno='" +
		// GrpContno + "' and StandbyFlag1!='1' and StandbyFlag1!='2'";
		// SSRS tSSRS = new SSRS();
		// tSSRS = tExeSQL.execSQL(tSql);
		// if (tSSRS.MaxRow == 0) {
		// logger.debug("没有手续费需要处理！");
		flag = "1";
		return true;
		// } else {
		// for (int i = 1; i <= tSSRS.MaxRow; i++) {
		// String tLimit = PubFun.getNoLimit(mGlobalInput.ManageCom);
		// String tNo = PubFun1.CreateMaxNo("GETNO", tLimit);
		// tLJSGetSchema = new LJAGetSchema();
		// tLJSGetSchema.setGetNoticeNo(tSSRS.GetText(i, 1));
		// tLJSGetSchema.setOtherNo(GrpContno);
		// tLJSGetSchema.setOtherNoType("7");
		// tLJSGetSchema.setPayMode("1");
		// tLJSGetSchema.setManageCom(mGlobalInput.ManageCom);
		// tLJSGetSchema.setOperator(mGlobalInput.Operator);
		// mCurrentDate = PubFun.getCurrentDate();
		// mCurrentTime = PubFun.getCurrentTime();
		// tLJSGetSchema.setMakeDate(mCurrentDate);
		// tLJSGetSchema.setMakeTime(mCurrentTime);
		// tLJSGetSchema.setModifyDate(mCurrentDate);
		// tLJSGetSchema.setModifyTime(mCurrentTime);
		// SSRS ySSRS = new SSRS();
		// String sql =
		// "select sum(fee) from lcfeecontrol where grpcontno='" +
		// GrpContno + "' and GetNoticeNo='" + tSSRS.GetText(i, 1) +
		// "' group by GetNoticeNo";
		// ySSRS = tExeSQL.execSQL(sql);
		// if (ySSRS.MaxRow == 0) {
		// tLJSGetSchema.setSumGetMoney(0);
		// } else {
		// tLJSGetSchema.setSumGetMoney(parseFloat(ySSRS.GetText(1, 1)));
		// }
		// //直接写SQL
		// map.put("insert into
		// LJAGet(ActuGetNo,GetNoticeNo,OtherNo,OtherNoType,PayMode,ManageCom,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime,SumGetMoney,operstate)
		// values('"+tNo+"','" +
		// tSSRS.GetText(i, 1) + "','" + GrpContno +
		// "','1','1','" + mGlobalInput.ManageCom + "','" +
		// mGlobalInput.Operator + "','" + mCurrentDate + "','" +
		// mCurrentTime + "','" + mCurrentDate + "','" +
		// mCurrentTime + "'," + parseFloat(ySSRS.GetText(1, 1)) +
		// ",'0')", "INSERT");
		//
		// //tLJSGetSet.add(tLJSGetSchema);
		//
		// tLJSGetOtherSchema = new LJAGetOtherSchema();
		// tLJSGetOtherSchema.setGetNoticeNo(tSSRS.GetText(i, 1));
		// tLJSGetOtherSchema.setOtherNo(GrpContno);
		// tLJSGetOtherSchema.setOtherNoType("7");
		// tLJSGetOtherSchema.setPayMode("1");
		// if (ySSRS.MaxRow == 0) {
		// tLJSGetOtherSchema.setGetMoney(0);
		// } else {
		// tLJSGetOtherSchema.setGetMoney(ySSRS.GetText(1, 1));
		// }
		// //查询代理人信息
		// LCGrpContDB tLCGrpContDB = new LCGrpContDB();
		// sql = "select * From lcgrpcont where grpcontno='" + GrpContno +
		// "'";
		// LCGrpContSet tLCGrpContSet = new LCGrpContSet();
		// tLCGrpContSet = tLCGrpContDB.executeQuery(sql);
		// if (tLCGrpContSet == null || tLCGrpContSet.size() == 0) {
		// logger.debug("查询代理人信息失败！");
		// }
		// String agentcode = tLCGrpContSet.get(1).getAgentCode();
		// String agentgroup = tLCGrpContSet.get(1).getAgentGroup();
		// tLJSGetOtherSchema.setAgentCode(agentcode);
		// tLJSGetOtherSchema.setAgentGroup(agentgroup);
		// tLJSGetOtherSchema.setFeeOperationType("TF");
		// tLJSGetOtherSchema.setFeeFinaType("SXF");
		// tLJSGetOtherSchema.setOperator(mGlobalInput.Operator);
		// tLJSGetOtherSchema.setMakeDate(mCurrentDate);
		// tLJSGetOtherSchema.setModifyTime(mCurrentTime);
		// tLJSGetOtherSchema.setModifyDate(mCurrentDate);
		// tLJSGetOtherSchema.setModifyTime(mCurrentTime);
		//
		// map.put("insert into
		// LJAGetOther(ActuGetNo,OtherNo,OtherNoType,PayMode,GetMoney,AgentCode,AgentGroup,FeeOperationType,FeeFinaType,Operator,MakeDate,MakeTime,ModifyDate,ModifyTime,operstate)
		// values ('"+tNo+"','" + GrpContno + "','1','1'," +
		// parseFloat(ySSRS.GetText(1, 1)) + ",'" + agentcode +
		// "','" + agentgroup +
		// "','TF','SXF','"+mGlobalInput.Operator+"','"+mCurrentDate+"','"+mCurrentTime+"','"+mCurrentDate+"','"+mCurrentTime+"','0')",
		// "INSERT");
		// map.put("update lcfeecontrol set StandbyFlag1='1' where
		// GetNoticeNo='"+tSSRS.GetText(i, 1)+"'","UPDATE");//1:已签单，2：已给付
		// //tLJSGetOtherSet.add(tLJSGetOtherSchema);
		// }
		// }
		//
		// return true;
	}

	private float parseFloat(String s) {
		float f1 = 0;
		String tmp = "";
		String s1 = "";
		for (int i = 0; i < s.length(); i++) {
			s1 = s.substring(i, i + 1);
			if (s1.equals("0") || s1.equals("1") || s1.equals("2")
					|| s1.equals("3") || s1.equals("4") || s1.equals("5")
					|| s1.equals("6") || s1.equals("7") || s1.equals("8")
					|| s1.equals("9") || s1.equals(".")) {
				tmp = tmp + s1;
			} else {
				tmp = "-1";
				break;
			}
		}
		f1 = Float.parseFloat(tmp);
		// logger.debug("tmp:"+tmp+" f1:"+f1);
		return f1;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) {

		mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));

		if (mGlobalInput != null && mGlobalInput.Operator == null) {
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "网页超时，请重新登录！";
			this.mErrors.addOneError(tError);

			return false;

		}

		tLCGrpContSet = (LCGrpContSet) cInputData.getObjectByObjectName(
				"LCGrpContSet", 0);
		if (tLCGrpContSet == null || tLCGrpContSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请选择一条记录！";
			this.mErrors.addOneError(tError);

			return false;

		}

		tLCGrpContSchema = tLCGrpContSet.get(1); // 只有一条记录
		return true;
	}

	public VData getResult() {
		return this.mResult;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "DerferAppF1PUI";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	/**
	 * 测试函数
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {

	}

}
