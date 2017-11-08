

/*
 * <p>ClassName: RIProfitLossCal </p>
 * <p>Description: RIProfitLossCal类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: Zhang Bin
 * @CreateDate：2007-03-14
 */
package com.sinosoft.lis.reinsure;

import com.sinosoft.lis.db.RIProLossCalDB;
import com.sinosoft.lis.db.RIProLossResultDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.RIProLossCalSchema;
import com.sinosoft.lis.schema.RIProLossResultSchema;
import com.sinosoft.lis.schema.RIProLossValueSchema;
import com.sinosoft.lis.vschema.RIProLossResultSet;
import com.sinosoft.lis.vschema.RIProLossValueSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class LRProfitLossCalBL implements BusinessService {

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	private VData mResult = new VData();
	private TransferData tTransferdata = new TransferData();
	/** 前台传入的公共变量 */
	private GlobalInput globalInput = new GlobalInput();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	private RIProLossValueSet mRIProLossValueSet = new RIProLossValueSet();
	private RIProLossResultSchema mRIProLossResultSchema = new RIProLossResultSchema();

	private String mCalType = "";
	private String mCalSql = "";
	private double mRIProLoss = 0;
	private double mResultValue = 0;

	/** 数据操作字符串 */
	private String strOperate;
	private MMap mMap = new MMap();

	private PubFun mPubFun = new PubFun();
	private String currentDate = mPubFun.getCurrentDate();
	private String currentTime = mPubFun.getCurrentTime();

	private PubSubmit tPubSubmit = new PubSubmit();

	// 业务处理相关变量
	/** 全局数据 */

	public LRProfitLossCalBL() {
	}

	/**
	 * 提交数据处理方法
	 * 
	 * @param cInputData
	 *            传入的数据,VData对象
	 * @param cOperate
	 *            数据操作字符串
	 * @return 布尔值（true--提交成功, false--提交失败）
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.strOperate = cOperate;
		if (strOperate.equals("")) {
			buildError("verifyOperate", "不支持的操作字符串");
			return false;
		}
		if (!getInputData(cInputData))
			return false;

		if (!dealData()) {
			return false;
		}

		if (!prepareOutputData()) {
			return false;
		}
		if (!tPubSubmit.submitData(this.mInputData, "")) {
			if (tPubSubmit.mErrors.needDealError()) {
				buildError("insertData", "保存再保合同信息时出现错误!");
				return false;
			}
		}
		mMap = null;
		tPubSubmit = null;

		return true;
	}

	/**
	 * 从输入数据中得到所有对象 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
	 */
	private boolean getInputData(VData cInputData) {
		globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0));
		// 纯溢手续费结果表
		mRIProLossResultSchema.setSchema((RIProLossResultSchema) cInputData
				.getObjectByObjectName("RIProLossResultSchema", 0));
		// 纯溢手续费要素值表
		mRIProLossValueSet.set((RIProLossValueSet) cInputData
				.getObjectByObjectName("RIProLossValueSet", 0));

		if ("conclusion".equals(strOperate)) {
			if (mRIProLossResultSchema == null
					|| "".equals(mRIProLossResultSchema.getStandbyString1())) {
				buildError("getInputData", "获取前台审核结论信息失败！");
				return false;
			}
		}

		return true;
	}

	/**
	 * 根据前面的输入数据，进行UI逻辑处理 如果在处理过程中出错，则返回false,否则返回true
	 */
	private boolean dealData() {
		// 盈余佣金计算
		if (this.strOperate.equals("CALCULATE")) {
			try {
				if (!calProfitLoss()) {
					return false;
				}
			} catch (Exception ex) {
			}
		}

		// 审核结论保存
		if ("conclusion".equals(strOperate)) {
			if (!saveConclusion()) {
				return false;
			}
		}

		if (this.strOperate.equals("DELETE")) {
			if (!deleteData()) {
				return false;
			}
		}

		return true;
	}

	private boolean calProfitLoss() {

		RIProLossResultDB tRIProLossResultDB = new RIProLossResultDB();
		tRIProLossResultDB.setCalYear(mRIProLossResultSchema.getCalYear());
		tRIProLossResultDB.setReComCode(mRIProLossResultSchema.getReComCode());
		tRIProLossResultDB.setRIContNo(mRIProLossResultSchema.getRIContNo());
		// if(!tRIProLossResultDB.getInfo())
		// {
		// mRIProLossResultSchema.setSerialNo(createWorkNo("PROFITLOSS"));
		// }
		// else
		// if(mRIProLossResultSchema.getSerialNo()==null||mRIProLossResultSchema.getSerialNo().equals(""))
		// {
		// buildError("insertData",
		// mRIProLossResultSchema.getReComCode()+" 公司的"+mRIProLossResultSchema.getCalYear()+"年度纯溢手续费已经计算过，请查询后重新计算");
		// return false;
		// }
		if (!calculate()) {
			return false;
		}
		mRIProLossResultSchema.setProLosAmnt(mResultValue);
		mRIProLossResultSchema.setMakeDate(currentDate);
		mRIProLossResultSchema.setMakeTime(currentTime);
		mRIProLossResultSchema.setModifyDate(currentDate);
		mRIProLossResultSchema.setModifyTime(currentTime);
		mRIProLossResultSchema.setManageCom(globalInput.ManageCom);
		mRIProLossResultSchema.setOperator(globalInput.Operator);
		mRIProLossResultSchema.setStandbyString1("01");
		mMap.put(mRIProLossResultSchema, "DELETE&INSERT");

		String strSQL = "delete from RIProLossValue where SerialNo='";
		// + mRIProLossResultSchema.getSerialNo() + "'";
		mMap.put(strSQL, "DELETE");

		for (int i = 1; i <= mRIProLossValueSet.size(); i++) {
			// mRIProLossValueSet.get(i).setSerialNo(
			// mRIProLossResultSchema.getSerialNo());
			mRIProLossValueSet.get(i).setMakeDate(currentDate);
			mRIProLossValueSet.get(i).setMakeTime(currentTime);
			mRIProLossValueSet.get(i).setModifyDate(currentDate);
			mRIProLossValueSet.get(i).setModifyTime(currentTime);
			mRIProLossValueSet.get(i).setManageCom(globalInput.ManageCom);
			mRIProLossValueSet.get(i).setOperator(globalInput.Operator);
		}
		mMap.put(mRIProLossValueSet, "INSERT");

		return true;
	}

	/**
	 * calculate
	 * 
	 * @return boolean
	 */
	private boolean calculate() {
		RIProLossCalDB tRIProLossCalDB = new RIProLossCalDB();
		// tRIProLossCalDB.setReComCode(mRIProLossResultSchema.getReComCode());
		// tRIProLossCalDB.setRIContNo(mRIProLossResultSchema.getRIContNo());
		System.out.println(" contno: " + mRIProLossResultSchema.getRIContNo()
				+ "  company: " + mRIProLossResultSchema.getReComCode());
		if (!tRIProLossCalDB.getInfo()) {
			buildError("insertData", mRIProLossResultSchema.getReComCode()
					+ " 公司的纯溢手续费算法没有定义");
			return false;
		}
		RIProLossCalSchema tRIProLossCalSchema = new RIProLossCalSchema();
		tRIProLossCalSchema = tRIProLossCalDB.getSchema();
		mCalType = tRIProLossCalSchema.getItemCalType();
		mRIProLoss = tRIProLossCalSchema.getRIProLoss();

		if (mCalType.equals("01")) {

		} else if (mCalType.equals("02")) {
			mCalSql = tRIProLossCalSchema.getCalSQL();
			PubCalculator tPubCalculator = new PubCalculator();
			tPubCalculator.setCalSql(mCalSql);
			System.out.println(" calSql: " + mCalSql);
			for (int i = 1; i <= mRIProLossValueSet.size(); i++) {
				String clumnname = mRIProLossValueSet.get(i).getFactorCode();
				String clumnvalue = mRIProLossValueSet.get(i).getFactorValue();
				tPubCalculator.addBasicFactor(clumnname, clumnvalue);
			}
			tPubCalculator.addBasicFactor("RIProLoss", mRIProLoss + "");

			String tCalSql = tPubCalculator.calculateEx();
			System.out.println("纯溢手续费计算sql为：" + tCalSql);
			ExeSQL tExeSQL = new ExeSQL();
			String temp = tExeSQL.getOneValue(tCalSql);
			if (tExeSQL.mErrors.needDealError()) {
				buildError("verifyOperate", "纯溢手续费sql计算出错：");
				return false;
			} else if (temp.equals("")) {
				buildError("verifyOperate", "纯溢手续费sql计算出错：");
				return false;
			} else {
				mResultValue = Double.parseDouble(temp);
			}
		} else if (mCalType.equals("03")) {

		}

		return true;
	}

	private boolean saveConclusion() {

		String tSQL = "select * from RIProLossResult where SerialNo = '";
		// + mRIProLossResultSchema.getSerialNo() + "' and CalYear='"
		// + mRIProLossResultSchema.getCalYear() + "' and ReComCode = '"
		// + mRIProLossResultSchema.getReComCode() + "' and RIContNo = '"
		// + mRIProLossResultSchema.getRIContNo() + "' ";

		RIProLossResultDB tRIProLossResultDB = new RIProLossResultDB();
		RIProLossResultSet tRIProLossResultSet = tRIProLossResultDB
				.executeQuery(tSQL);

		if (tRIProLossResultSet != null && tRIProLossResultSet.size() > 0) {
			tRIProLossResultSet.get(1).setStandbyString1(
					mRIProLossResultSchema.getStandbyString1());
			tRIProLossResultSet.get(1).setModifyDate(PubFun.getCurrentDate());
			tRIProLossResultSet.get(1).setModifyTime(PubFun.getCurrentTime());

			mMap.put(tRIProLossResultSet.get(1), "UPDATE");
		}

		return true;
	}

	/**
	 * deleteData
	 * 
	 * @return boolean
	 */
	private boolean deleteData() {

		return true;
	}

	private String createWorkNo(String ags) {
		String tWorkNo = "";
		if (ags.equals("PROFITLOSS")) {
			tWorkNo = PubFun1.CreateMaxNo("PROFITLOSS", 10);
		}
		return tWorkNo;
	}

	/**
	 * 准备往后层输出所需要的数据 输出：如果准备数据时发生错误则返回false,否则返回true
	 */
	private boolean prepareOutputData() {
		try {
			this.mInputData.clear();
			this.mInputData.add(mMap);
		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LDComBL";
			tError.functionName = "prepareData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	public VData getResult() {
		tTransferdata.setNameAndValue("Double", mResultValue);
		mResult.add(tTransferdata);
		return mResult;
	}

	/*
	 * add by kevin, 2002-10-14
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();

		cError.moduleName = "ReComManageBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		GlobalInput globalInput = new GlobalInput();
		globalInput.ComCode = "8611";
		globalInput.ManageCom = "8611";
		globalInput.Operator = "001";

		LRProfitLossCalBL tLRProfitLossCalBL = new LRProfitLossCalBL();
		RIProLossValueSet tRIProLossValueSet = new RIProLossValueSet();
		RIProLossResultSchema tRIProLossResultSchema = new RIProLossResultSchema();

		// tRIProLossResultSchema.setSerialNo("0000000027");
		tRIProLossResultSchema.setCalYear("2007");
		tRIProLossResultSchema.setReComCode("R000000002");
		tRIProLossResultSchema.setRIContNo("C000000001");

		RIProLossValueSchema tRIProLossValueSchema = new RIProLossValueSchema();
		tRIProLossValueSchema.setReComCode("R000000002");
		tRIProLossValueSchema.setRIContNo("C000000001");
		// tRIProLossValueSchema.setSerialNo("");
		tRIProLossValueSchema.setFactorCode("CessPrem");
		tRIProLossValueSchema.setFactorValue("3612.04");
		tRIProLossValueSet.add(tRIProLossValueSchema);

		tRIProLossValueSchema = new RIProLossValueSchema();
		tRIProLossValueSchema.setReComCode("R000000002");
		tRIProLossValueSchema.setRIContNo("C000000001");
		// tRIProLossValueSchema.setSerialNo("001");
		tRIProLossValueSchema.setFactorCode("ReturnComm");
		tRIProLossValueSchema.setFactorValue("0");
		tRIProLossValueSet.add(tRIProLossValueSchema);

		tRIProLossValueSchema = new RIProLossValueSchema();
		tRIProLossValueSchema.setReComCode("R000000002");
		tRIProLossValueSchema.setRIContNo("C000000001");
		// tRIProLossValueSchema.setSerialNo("001");
		tRIProLossValueSchema.setFactorCode("LastYearLoss");
		tRIProLossValueSchema.setFactorValue("100");
		tRIProLossValueSet.add(tRIProLossValueSchema);

		tRIProLossValueSchema = new RIProLossValueSchema();
		tRIProLossValueSchema.setReComCode("R000000002");
		tRIProLossValueSchema.setRIContNo("C000000001");
		// tRIProLossValueSchema.setSerialNo("001");
		tRIProLossValueSchema.setFactorCode("RIMngFeeRate");
		tRIProLossValueSchema.setFactorValue("0.05");
		tRIProLossValueSet.add(tRIProLossValueSchema);

		tRIProLossValueSchema = new RIProLossValueSchema();
		tRIProLossValueSchema.setReComCode("R000000002");
		tRIProLossValueSchema.setRIContNo("C000000001");
		// tRIProLossValueSchema.setSerialNo("001");
		tRIProLossValueSchema.setFactorCode("ProCommRate");
		tRIProLossValueSchema.setFactorValue("0.1");
		tRIProLossValueSet.add(tRIProLossValueSchema);

		VData vData = new VData();
		vData.addElement(tRIProLossValueSet);
		vData.addElement(tRIProLossResultSchema);
		vData.addElement(globalInput);
		try {
			tLRProfitLossCalBL.submitData(vData, "CALCULATE");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public CErrors getErrors() {

		return this.mErrors;
	}

}
