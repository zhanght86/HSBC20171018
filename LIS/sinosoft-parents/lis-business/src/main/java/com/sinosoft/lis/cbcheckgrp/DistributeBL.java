package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.DistriControlSchema;
import com.sinosoft.lis.vschema.DistriControlSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DistributeBL {
private static Logger logger = Logger.getLogger(DistributeBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();

	/** 往前面传输数据的容器 */
	private VData mInputData;
	private VData mResult = new VData();
	// private LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
	private DistriControlSet tDistriControlSet = new DistriControlSet();
	private DistriControlSet subDistriControlSet = new DistriControlSet();
	// private LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
	private DistriControlSchema tDistriControlSchema = new DistriControlSchema();
	private TransferData mTransferData = new TransferData();

	public DistributeBL() {
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
		for (int i = 1; i <= tDistriControlSet.size(); i++) {
			tDistriControlSchema = new DistriControlSchema();
			tDistriControlSchema = tDistriControlSet.get(i);
			if (parseFloat(tDistriControlSchema.getStandbyFlag1()) > 1
					|| parseFloat(tDistriControlSchema.getStandbyFlag1()) < 0) {
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "第" + i + "行的分保比例数据错误!";

				this.mErrors.addOneError(tError);
				return false;

			}

			if (parseFloat(tDistriControlSchema.getStandbyFlag2()) > 1
					|| parseFloat(tDistriControlSchema.getStandbyFlag2()) < 0) {
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "第" + i + "行的摊回比例数据错误!";

				this.mErrors.addOneError(tError);
				return false;

			}

			if (parseFloat(tDistriControlSchema.getStandbyFlag3()) > 1
					|| parseFloat(tDistriControlSchema.getStandbyFlag3()) < 0) {
				CError tError = new CError();
				tError.moduleName = "ContBL";
				tError.functionName = "submitData";
				tError.errorMessage = "第" + i + "行的临分手续费比例数据错误!";

				this.mErrors.addOneError(tError);
				return false;

			}
			// 如果三个数据都为0是不可以的
			if (parseFloat(tDistriControlSchema.getStandbyFlag1()) == 0
					&& parseFloat(tDistriControlSchema.getStandbyFlag2()) == 0
					&& parseFloat(tDistriControlSchema.getStandbyFlag3()) == 0) {
				// CError tError = new CError();
				// tError.moduleName = "ContBL";
				// tError.functionName = "submitData";
				// tError.errorMessage = "第"+i+"行的数据不能都为0!";
				//
				// this.mErrors.addOneError(tError);
				// return false;

			} else {
				map.put("update lcgrppol set DistriRate='"
						+ tDistriControlSchema.getStandbyFlag1()
						+ "',FeeRate='"
						+ tDistriControlSchema.getStandbyFlag2()
						+ "' where grpcontno='"
						+ tDistriControlSchema.getGrpContno()
						+ "' and grppolno='"
						+ tDistriControlSchema.getGrpPolno() + "'", "UPDATE");
				map.put("delete from DistriControl where grppolno='"
						+ tDistriControlSchema.getGrpPolno()
						+ "' and grpcontno='"
						+ tDistriControlSchema.getGrpContno() + "'", "DELETE");
				map.put("insert into DistriControl values ('"
						+ tDistriControlSchema.getGrpPolno() + "','"
						+ tDistriControlSchema.getGrpContno() + "','"
						+ tDistriControlSchema.getRiskcode() + "',"
						+ tDistriControlSchema.getStandbyFlag1() + ","
						+ tDistriControlSchema.getStandbyFlag2() + ",'"
						+ tDistriControlSchema.getManagecom() + "',"
						+ tDistriControlSchema.getStandbyFlag3()
						+ ",'','','','','')", "INSERT");
			}
			// tDistriControlSchema.setDistriRate(parseFloat(tDistriControlSchema.getStandbyFlag1()));
			// tDistriControlSchema.setFeeRate(parseFloat(tDistriControlSchema.getStandbyFlag2()));
			// tDistriControlSchema.setFee(parseFloat(tDistriControlSchema.getStandbyFlag3()));
			//
			// tDistriControlSchema.setStandbyFlag1("");
			// tDistriControlSchema.setStandbyFlag2("");
			// tDistriControlSchema.setStandbyFlag3("");
			// subDistriControlSet.add(tDistriControlSchema);

		}
		// map.put(subDistriControlSet,"DELETE&INSERT");

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
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		tDistriControlSet = (DistriControlSet) mTransferData
				.getValueByName("DISTRI");
		if (tDistriControlSet == null || tDistriControlSet.size() == 0) {
			CError tError = new CError();
			tError.moduleName = "ProposalBL";
			tError.functionName = "getInputData";
			tError.errorMessage = "提交的数据不存在任何数值！";
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
