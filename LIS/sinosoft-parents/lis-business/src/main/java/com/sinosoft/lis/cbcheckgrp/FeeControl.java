package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCFeeControlSchema;
import com.sinosoft.lis.vbl.LCGrpPolBLSet;
import com.sinosoft.lis.vschema.LCFeeControlSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FeeControl {
private static Logger logger = Logger.getLogger(FeeControl.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 往前面传输数据的容器 */
	private VData mInputData;
	private VData mResult = new VData();

	private LCFeeControlSet tFeeControlSet = new LCFeeControlSet();
	private LCFeeControlSet subFeeControlSet = new LCFeeControlSet();

	private LCFeeControlSchema tFeeControlSchema = new LCFeeControlSchema();
	private TransferData mTransferData = new TransferData();
	// 领取人与领取人ID
	private String Drawer = "";
	private String DrawerID = "";
	private String tGetNoticeNo = "";
	private String mCurrentDate;
	private String mCurrentTime;

	// private LJSGetSchema tLJSGetSchema=new LJSGetSchema();//应付数据（一个领取人只有一条记录）
	private String FORMATMODOL = "0.000000"; // 计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	public FeeControl() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
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

		if (!tPubSubmit.submitData(mInputData, "UPDATE")) {
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

		map.put(subFeeControlSet, "INSERT");
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

		mCurrentDate = PubFun.getCurrentDate();
		mCurrentTime = PubFun.getCurrentTime();

		// 一个领取人生成一个给付通知书号码

		tGetNoticeNo = PubFun1.CreateMaxNo("GETNOTICENO", PubFun
				.getNoLimit(mGlobalInput.ManageCom));

		for (int i = 1; i <= tFeeControlSet.size(); i++) {
			tFeeControlSchema = new LCFeeControlSchema();
			tFeeControlSchema = tFeeControlSet.get(i);
			if (parseFloat(tFeeControlSchema.getStandbyFlag1()) > 1
					|| parseFloat(tFeeControlSchema.getStandbyFlag1()) < 0) {
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "第" + i + "行的手续费比例数据错误!";

				this.mErrors.addOneError(tError);
				return false;

			}

			if (parseFloat(tFeeControlSchema.getStandbyFlag2()) == 2) {
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "第" + i + "行的手续费数据错误!";

				this.mErrors.addOneError(tError);
				return false;

			}

			// 对于次数，首先查询该分单下最大的次数，然后加1
			ExeSQL tExeSQL = new ExeSQL();
			String tSql = "select max(countno) from lcfeecontrol where GrpPolno='"
					+ tFeeControlSchema.getGrpPolno()
					+ "' and drawer='"
					+ Drawer + "'";
			SSRS tSSRS = new SSRS();
			tSSRS = tExeSQL.execSQL(tSql);
			int tCount = 1;
			logger.debug("%%%%%%%%%%%%" + tSSRS.GetText(1, 1));
			if (tSSRS.MaxRow > 0 && tSSRS.GetText(1, 1) != null
					&& !tSSRS.GetText(1, 1).equals("null")) {
				tCount = Integer.parseInt(tSSRS.GetText(1, 1)) + 1;
				// tSql="select GetNoticeNo from lcfeecontrol where GrpPolno='"
				// +
				// tFeeControlSchema.getGrpPolno() + "' and drawer='" +
				// Drawer + "'";
				// tSSRS = tExeSQL.execSQL(tSql);
				// tGetNoticeNo=tSSRS.GetText(1,1);//实现分批支付
			}
			// 根据手续费比例或手续费计算另个值；
			double money = 0;
			double rate = 0;
			if ((!tFeeControlSchema.getStandbyFlag1().equals("0") || !tFeeControlSchema
					.getStandbyFlag1().equals(""))
					&& tFeeControlSchema.getStandbyFlag2().equals("0")) {
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
				String sql = "select * From LCGrpPol where grpcontno='"
						+ tFeeControlSchema.getGrpContno() + "' and grppolno='"
						+ tFeeControlSchema.getGrpPolno() + "'";
				logger.debug(sql);
				LCGrpPolSet tLCGrpPolSet = new LCGrpPolBLSet();
				tLCGrpPolSet = tLCGrpPolDB.executeQuery(sql);
				if (tLCGrpPolSet == null || tLCGrpPolSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "ContBL";
					tError.functionName = "submitData";
					tError.errorMessage = "第" + i + "行的数据库信息丢失，请查证!";

					this.mErrors.addOneError(tError);
					return false;

				}

				rate = parseFloat(tFeeControlSchema.getStandbyFlag1());
				money = rate * tLCGrpPolSet.get(1).getPrem();
			}
			// 如果录入手续费而没有录入比例，或手续费与比例同时录入 即手续费录入数值啦
			if (!tFeeControlSchema.getStandbyFlag2().equals("0")
					|| !tFeeControlSchema.getStandbyFlag2().equals("")) {
				LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
				String sql = "select * From LCGrpPol where grpcontno='"
						+ tFeeControlSchema.getGrpContno() + "' and grppolno='"
						+ tFeeControlSchema.getGrpPolno() + "'";
				LCGrpPolSet tLCGrpPolSet = new LCGrpPolBLSet();
				tLCGrpPolSet = tLCGrpPolDB.executeQuery(sql);
				if (tLCGrpPolSet == null || tLCGrpPolSet.size() == 0) {
					CError tError = new CError();
					tError.moduleName = "ContBL";
					tError.functionName = "submitData";
					tError.errorMessage = "第" + i + "行的数据库信息丢失，请查证!";

					this.mErrors.addOneError(tError);
					return false;

				}

				money = parseFloat(tFeeControlSchema.getStandbyFlag2());
				String temp = mDecimalFormat.format(money
						/ tLCGrpPolSet.get(1).getPrem());
				rate = parseFloat(temp);
				logger.debug("^..^^^^^^..^^^^^" + rate);
			}

			tFeeControlSchema.setFeeRate(rate);
			tFeeControlSchema.setFee(money);

			tFeeControlSchema.setMakeDate(mCurrentDate);
			tFeeControlSchema.setMakeTime(mCurrentTime);
			tFeeControlSchema.setModifyDate(mCurrentDate);
			tFeeControlSchema.setModifyTime(mCurrentTime);
			tFeeControlSchema.setOperator(mGlobalInput.Operator);
			tFeeControlSchema.setPayNo("00");// 签单时进行更换
			tFeeControlSchema.setGetNoticeNo(tGetNoticeNo);
			tFeeControlSchema.setCountNo(tCount);
			tFeeControlSchema.setDrawer(Drawer);
			tFeeControlSchema.setDrawerID(DrawerID);

			if (tFeeControlSchema.getStandbyFlag1().equals("0")
					&& tFeeControlSchema.getStandbyFlag2().equals("0")) {
				logger.debug("此条数据不需要保存！");
			} else {
				subFeeControlSet.add(tFeeControlSchema); // 最后需要提交的手续费数据
			}
			// 写入数据
			// map.put("insert into FeeControl values ()", "INSERT");

		}

		// 应付总表与其他退费表也要写入数据//在签单时生成
		// tLJSGetSchema=new LJSGetSchema();
		// tLJSGetSchema.setGetNoticeNo(tGetNoticeNo);
		// tLJSGetSchema.setOtherNo(tFeeControlSchema.getGrpContno());
		// tLJSGetSchema.setOtherNoType("1");
		// tLJSGetSchema.setPayMode("1");
		// tLJSGetSchema.setManageCom(mGlobalInput.ManageCom);
		// tLJSGetSchema.setSumGetMoney(SumMoney);
		// tLJSGetSchema.setMakeDate(mCurrentDate);
		// tLJSGetSchema.setMakeTime(mCurrentTime);
		// tLJSGetSchema.setModifyDate(mCurrentDate);
		// tLJSGetSchema.setModifyTime(mCurrentTime);
		// 其他退费表也要写入数据

		return true;
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
				tmp = "2";
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

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		tFeeControlSet = (LCFeeControlSet) mTransferData.getValueByName("Fee");
		if (tFeeControlSet == null || tFeeControlSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "提交的数据不存在任何数值！";
			this.mErrors.addOneError(tError);

			return false;

		}
		Drawer = (String) mTransferData.getValueByName("Drawer");
		if (Drawer == null) {
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请录入领取人！";
			this.mErrors.addOneError(tError);

			return false;

		}
		DrawerID = (String) mTransferData.getValueByName("DrawerID");
		if (DrawerID == null) {
			DrawerID = "000000"; // 给个默认值
		}
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
