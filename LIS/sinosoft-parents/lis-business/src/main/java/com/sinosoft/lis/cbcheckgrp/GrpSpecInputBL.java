package com.sinosoft.lis.cbcheckgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCCGrpSpecSchema;
import com.sinosoft.lis.schema.LCGrpContSchema;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class GrpSpecInputBL {
private static Logger logger = Logger.getLogger(GrpSpecInputBL.class);
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private MMap map = new MMap();
	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	/** 往前面传输数据的容器 */
	private VData mInputData;
	private String mOperate;
	private VData mResult = new VData();
	private LCCGrpSpecSchema tLCCGrpSpecSchema = new LCCGrpSpecSchema();
	private LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
	private LCGrpContDB tLCGrpContDB = new LCGrpContDB();

	public GrpSpecInputBL() {
	}

	public boolean submitData(VData cInputData, String cOperate) {
		mInputData = (VData) cInputData.clone();
		mOperate = cOperate;
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
//		map.put("delete from LCCGrpSpec where ProposalGrpContNo='"
//				+ tLCCGrpSpecSchema.getProposalGrpContNo() + "'", "DELETE");
		if("INSERT".equals(mOperate))
		    map.put(tLCCGrpSpecSchema, "INSERT");
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
		if("INSERT".equals(mOperate)){
			String serial = PubFun1.CreateMaxNo("SubNo", 11);
			tLCCGrpSpecSchema.setMakeDate(PubFun.getCurrentDate());
			tLCCGrpSpecSchema.setMakeTime(PubFun.getCurrentTime());
			tLCCGrpSpecSchema.setModifyDate(PubFun.getCurrentDate());
			tLCCGrpSpecSchema.setModifyTime(PubFun.getCurrentTime());
			tLCCGrpSpecSchema.setOperator(mGlobalInput.Operator);
			tLCCGrpSpecSchema.setSerialNo(serial);
			tLCCGrpSpecSchema.setPrtSeq(serial);
		}
		if("DELETE".equals(mOperate)){
			map.put("delete from lccgrpspec where proposalgrpcontno ='"+tLCCGrpSpecSchema.getProposalGrpContNo()+"'"
					+" and serialno = '"+tLCCGrpSpecSchema.getSerialNo()+"'", "DELETE");
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
		tLCCGrpSpecSchema.setSchema((LCCGrpSpecSchema) cInputData
				.getObjectByObjectName("LCCGrpSpecSchema", 0));
		logger.debug("in bl tLCCGrpSpecSchema:"
				+ tLCCGrpSpecSchema.getGrpContNo());
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
