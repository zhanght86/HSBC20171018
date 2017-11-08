package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.LCFeeControlDB;
import com.sinosoft.lis.db.LCGrpPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCFeeControlSchema;
import com.sinosoft.lis.vbl.LCGrpPolBLSet;
import com.sinosoft.lis.vschema.LCFeeControlSet;
import com.sinosoft.lis.vschema.LCGrpPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class FeeControlUpdate {
private static Logger logger = Logger.getLogger(FeeControlUpdate.class);
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
	private String mOperate;
	// private LJSGetSchema tLJSGetSchema=new LJSGetSchema();//应付数据（一个领取人只有一条记录）
	private String FORMATMODOL = "0.000000"; // 计算出来后的精确位数
	private DecimalFormat mDecimalFormat = new DecimalFormat(FORMATMODOL); // 数字转换对象

	public FeeControlUpdate() {
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

		for (int i = 1; i <= tFeeControlSet.size(); i++) {

			tFeeControlSchema = new LCFeeControlSchema();
			tFeeControlSchema = tFeeControlSet.get(i);
			// 将CHECKDATA（）方法写入这啦
			LCFeeControlDB mLCFeeControlDB = new LCFeeControlDB();
			String sqls = "select * From lcfeecontrol where grppolno='"
					+ tFeeControlSchema.getGrpPolno() + "' and GetNoticeNo='"
					+ tFeeControlSchema.getGetNoticeNo() + "' and CountNO='"
					+ tFeeControlSchema.getCountNo() + "'";
			LCFeeControlSet mLCFeeControlSet = new LCFeeControlSet();
			mLCFeeControlSet = mLCFeeControlDB.executeQuery(sqls);
			logger.debug(sqls);
			if (mLCFeeControlSet.get(1).getStandbyFlag1().equals("1")) {
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "第" + i + "行的数据已经签单，不能进行修改!";

				this.mErrors.addOneError(tError);
				return false;

			}
			if (mLCFeeControlSet.get(1).getStandbyFlag1().equals("2")) {
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "第" + i + "行的数据已经发生实际给付，不能进行修改!";

				this.mErrors.addOneError(tError);
				return false;

			}

			if (mOperate.equals("UPDATE||MAIN")) {

				if (parseFloat(tFeeControlSchema.getStandbyFlag1()) > 1
						|| parseFloat(tFeeControlSchema.getStandbyFlag1()) < 0) {
					CError tError = new CError();
					tError.moduleName = "ContBL";
					tError.functionName = "submitData";
					tError.errorMessage = "第" + i + "行的手续费比例数据错误!";

					this.mErrors.addOneError(tError);
					return false;

				}

				if (parseFloat(tFeeControlSchema.getStandbyFlag2()) == -1) {
					CError tError = new CError();
					tError.moduleName = "ContBL";
					tError.functionName = "submitData";
					tError.errorMessage = "第" + i + "行的手续费数据错误!";

					this.mErrors.addOneError(tError);
					return false;

				}

				// 根据手续费比例或手续费计算另个值；
				double money = 0;
				double rate = 0;
				if (!tFeeControlSchema.getStandbyFlag1().equals("0")
						&& !tFeeControlSchema.getStandbyFlag1().equals("")
						&& tFeeControlSchema.getStandbyFlag2().equals("0")) {
					LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
					String sql = "select * From LCGrpPol where  grppolno='"
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
						&& !tFeeControlSchema.getStandbyFlag2().equals("")) {
					LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
					String sql = "select * From LCGrpPol where grppolno='"
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

					money = parseFloat(tFeeControlSchema.getStandbyFlag2());
					String temp = mDecimalFormat.format(
							money / tLCGrpPolSet.get(1).getPrem()).substring(0,
							7);
					rate = parseFloat(temp);
					logger.debug("^..^^^^^^..^^^^^" + rate);
				}
				// 写入数据
				map.put("update lcFeeControl set fee='" + money + "',feerate='"
						+ rate + "' where grppolno='"
						+ tFeeControlSchema.getGrpPolno()
						+ "' and GetNoticeNo='"
						+ tFeeControlSchema.getGetNoticeNo()
						+ "' and CountNO='" + tFeeControlSchema.getCountNo()
						+ "'", "UPDATE");

			} else {
				map.put("delete from lcfeecontrol where grppolno='"
						+ tFeeControlSchema.getGrpPolno()
						+ "' and GetNoticeNo='"
						+ tFeeControlSchema.getGetNoticeNo()
						+ "' and CountNO='" + tFeeControlSchema.getCountNo()
						+ "'", "DELETE");

			}

		}

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

		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		tFeeControlSet = (LCFeeControlSet) mTransferData.getValueByName("Fee");
		if (tFeeControlSet == null || tFeeControlSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "请选择一条记录！";
			this.mErrors.addOneError(tError);

			return false;

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
