package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;

public class LLClaimClearAccBL {
private static Logger logger = Logger.getLogger(LLClaimClearAccBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	private Reflections mReflections = new Reflections();

	/** 数据操作字符串 */
	private String mOperate;

	private String mClmNo;

	/** 业务处理相关变量 */
	private TransferData mTransferData = new TransferData();

	private LCGrpIvstPlanSet mLCGrpIvstPlanSet = new LCGrpIvstPlanSet();

	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();

	// private String mPlanCode = "";
	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值

	String mCurrentTime = PubFun.getCurrentTime();

	private MMap map = new MMap();

	public LLClaimClearAccBL() {
	}

	public boolean submitData(VData cInputData, String operate) {
		mInputData = cInputData;
		// 数据操作字符串拷贝到本类中
		this.mOperate = operate;
		if (!getInputData()) {
			this.buildError("submitData", "无法获取输入信息");
			return false;
		}
		if (!checkData()) {
			this.buildError("", "提交的数据有误，请核对后再操作");
			return false;
		}
		if (!dealData()) {
			this.buildError("submitData", "处理数据时失败");
			return false;
		}
		if (!prepareData()) {
			return false;
		}
		logger.debug("--------end prepareOutputData");
		PubSubmit tSubmit = new PubSubmit();

		if (!tSubmit.submitData(mResult, mOperate)) { // 数据提交
			// @@错误处理
			this.mErrors.copyAllErrors(tSubmit.mErrors);
			CError tError = new CError();
			tError.moduleName = "PMAscriptionRuleBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";
			this.mErrors.addOneError(tError);
			return false;
		}

		return true;
	}

	/**
	 * prepareData
	 * 
	 * @return boolean
	 */
	private boolean prepareData() {
		mResult.clear();
		mResult.add(map);
		return true;
	}

	private boolean getInputData() {
		mGlobalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName(
				"GlobalInput", 0));

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");

		if (mGlobalInput == null || mLCGrpIvstPlanSet == null) {
			buildError("getInputData", "传过来的数据不全");
			return false;
		}

		return true;
	}

	private boolean checkData() {
		return true;
	}

	private boolean dealData() {
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
		//考虑多个投连险的可能
		//contno,polno,riskcode1
		//contno,polno,riskcode2
		String sql="select distinct contno,polno,riskcode from llclaimpolicy a where a.caseno='"+"?mClmNo?"+"'"
				+" and exists(select 1 from lmrisktoacc c, lmriskinsuacc r "// 是投连险
				+" where r.insuaccno = c.insuaccno and r.acckind = '2' and c.riskcode =a.riskcode)";
		sqlbv.sql(sql);
		sqlbv.put("mClmNo", mClmNo);
		SSRS tSSRS= new SSRS();
		ExeSQL tExeSQL = new ExeSQL();
		tSSRS = tExeSQL.execSQL(sqlbv);
		if(tSSRS==null || tSSRS.getMaxRow()<=0){
			this.buildError("dealDate", "无法找到相应数据");
			return false;
		}
		
		boolean ipFlag = false;//默认不走后续处理
		LLClaimPolicySchema tLLClaimPolicySchema = new LLClaimPolicySchema();
		tLLClaimPolicySchema.setCaseNo(mClmNo);
		for (int i = 1; i <= tSSRS.getMaxRow(); i++) {		
			tLLClaimPolicySchema.setContNo(tSSRS.GetText(i, 1));
			tLLClaimPolicySchema.setPolNo(tSSRS.GetText(i, 2));
			tLLClaimPolicySchema.setRiskCode(tSSRS.GetText(i, 3));
			
				LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
				LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				//根据保单备份
				backUp(tLLClaimPolicySchema);// 对acc表和class表进行备份，供回退使用

				tLCInsureAccTraceDB.setMoneyType("BF");
				tLCInsureAccTraceDB.setState("0");
				tLCInsureAccTraceDB.setContNo(tLLClaimPolicySchema
						.getContNo());
				tLCInsureAccTraceDB.setPolNo(tLLClaimPolicySchema.getPolNo());
				tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
				if (tLCInsureAccTraceSet == null
						|| tLCInsureAccTraceSet.size() <= 0) {
					ipFlag = true;
				} else {					
					logger.debug(tLCInsureAccTraceSet.size());
					for (int k = 1; k <= tLCInsureAccTraceSet.size(); k++) {
						tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(k);
						// if (!ctoP(tLCInsureAccTraceSchema)) {//
						// 无论是NB还是IP,只要是BF就要从c表转p表
						// return false;
						// }
						if (tLCInsureAccTraceSchema.getBusyType().equals("IP")
								|| tLCInsureAccTraceSchema.getBusyType()
										.equals("RN")) {
							ipFlag = true;// 有一条是追加或续期保费的就要进行后续处理
						}
					}
					if (!ipFlag) {
						clearZero(tLLClaimPolicySchema);// 因为不走后续处理，在此需对acc表和class表清0
					}
					ctoP(tLLClaimPolicySchema);// 无论是NB还是IP,只要是BF就要从c表转p表
				}
		}
		logger.debug("tLLClaimPolicySchema.getPolNo(): "+tLLClaimPolicySchema.getPolNo());
		if (ipFlag) {
			insertAfterDeal(tLLClaimPolicySchema, "0");
		} else {
			insertAfterDeal(tLLClaimPolicySchema, "2");
		}
		return true;
	}

	public boolean ctoP(LLClaimPolicySchema tLLClaimPolicySchema) {
		LPInsureAccTraceSet mLPInsureAccTraceSet = new LPInsureAccTraceSet();
		LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
		LPInsureAccFeeSet mLPInsureAccFeeSet = new LPInsureAccFeeSet();
		LCInsureAccFeeSet mLCInsureAccFeeSet = new LCInsureAccFeeSet();
		LPInsureAccClassFeeSet mLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
		LCInsureAccClassFeeSet mLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
		LPInsureAccFeeTraceSet mLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
		LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();

		String otherNo = new String();
		String otherType = new String();
		// LCInsureAccTrace表转LPInsureAccTrace表
		LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
		LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
		tLCInsureAccTraceDB.setMoneyType("BF");
		tLCInsureAccTraceDB.setState("0");
		tLCInsureAccTraceDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLCInsureAccTraceDB.setPolNo(tLLClaimPolicySchema.getPolNo());
		tLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
		for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
			tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
			otherNo = tLCInsureAccTraceSchema.getOtherNo();
			otherType = tLCInsureAccTraceSchema.getOtherType();
			mReflections.transFields(tLPInsureAccTraceSchema,
					tLCInsureAccTraceSchema);
			tLPInsureAccTraceSchema.setEdorNo(mClmNo);
			tLPInsureAccTraceSchema.setEdorType("CL");

			mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
			mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		}

		// LCInsureAccFee转LPInsureAccFee LCInsureAccClassFee转LPInsureAccClassFee
		LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
		LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
		tLCInsureAccFeeDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLCInsureAccFeeDB.setPolNo(tLLClaimPolicySchema.getPolNo());
		tLCInsureAccFeeSet = tLCInsureAccFeeDB.query();
		for (int i = 1; i <= tLCInsureAccFeeSet.size(); i++) {
			double accFee = 0;
			LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
			LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
			tLCInsureAccFeeSchema = tLCInsureAccFeeSet.get(i);
			mReflections.transFields(tLPInsureAccFeeSchema,
					tLCInsureAccFeeSchema);
			tLPInsureAccFeeSchema.setEdorNo(mClmNo);
			tLPInsureAccFeeSchema.setEdorType("CL");

			LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
			LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
			tLCInsureAccClassFeeDB.setContNo(tLCInsureAccFeeSchema.getContNo());
			tLCInsureAccClassFeeDB.setPolNo(tLCInsureAccFeeSchema.getPolNo());
			tLCInsureAccClassFeeDB.setInsuAccNo(tLCInsureAccFeeSchema
					.getInsuAccNo());
			tLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();
			for (int k = 1; k <= tLCInsureAccClassFeeSet.size(); k++) {
				double classFee = 0;
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
				LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
				tLCInsureAccClassFeeSchema = tLCInsureAccClassFeeSet.get(k);
				mReflections.transFields(tLPInsureAccClassFeeSchema,
						tLCInsureAccClassFeeSchema);
				tLPInsureAccClassFeeSchema.setEdorNo(mClmNo);
				tLPInsureAccClassFeeSchema.setEdorType("CL");

				// LCInsureAccFeeTrace转LPInsureAccFeeTrace
				LCInsureAccFeeTraceDB tLCInsureAccFeeTraceDB = new LCInsureAccFeeTraceDB();
				LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
				tLCInsureAccFeeTraceDB.setContNo(tLCInsureAccClassFeeSchema
						.getContNo());
				tLCInsureAccFeeTraceDB.setPolNo(tLCInsureAccClassFeeSchema.getPolNo());
				tLCInsureAccFeeTraceDB.setInsuAccNo(tLCInsureAccClassFeeSchema
						.getInsuAccNo());
				tLCInsureAccFeeTraceDB
						.setPayPlanCode(tLCInsureAccClassFeeSchema
								.getPayPlanCode());
				tLCInsureAccFeeTraceDB.setFeeCode(tLCInsureAccClassFeeSchema
						.getFeeCode());
				tLCInsureAccFeeTraceDB.setOtherNo(otherNo);
				tLCInsureAccFeeTraceDB.setOtherType(otherType);
				tLCInsureAccFeeTraceSet = tLCInsureAccFeeTraceDB.query();
				for (int m = 1; m <= tLCInsureAccFeeTraceSet.size(); m++) {
					LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
					LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();
					tLCInsureAccFeeTraceSchema = tLCInsureAccFeeTraceSet.get(m);
					mReflections.transFields(tLPInsureAccFeeTraceSchema,
							tLCInsureAccFeeTraceSchema);
					tLPInsureAccFeeTraceSchema.setEdorNo(mClmNo);
					tLPInsureAccFeeTraceSchema.setEdorType("CL");

					classFee += tLCInsureAccFeeTraceSchema.getFee();

					mLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
					mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
				}
				accFee += classFee;
				tLCInsureAccClassFeeSchema.setFee(tLCInsureAccClassFeeSchema
						.getFee()
						- classFee);

				mLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
				mLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			}
			tLCInsureAccFeeSchema.setFee(tLCInsureAccFeeSchema.getFee()
					- accFee);

			mLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
			mLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
		}

		map.put(mLPInsureAccTraceSet, "INSERT");
		map.put(mLCInsureAccTraceSet, "DELETE");

		map.put(mLPInsureAccFeeSet, "INSERT");
		map.put(mLCInsureAccFeeSet, "UPDATE");

		map.put(mLPInsureAccClassFeeSet, "INSERT");
		map.put(mLCInsureAccClassFeeSet, "UPDATE");

		//add zhangyingfeng 2016-08-03
		//营改增 价税分离计算器
		TaxCalculator.calBySchemaSet(mLPInsureAccFeeTraceSet);
		//end zhangyingfeng 2016-08-03
		map.put(mLPInsureAccFeeTraceSet, "INSERT");
		map.put(mLCInsureAccFeeTraceSet, "DELETE");

		return true;
	}

	public void insertAfterDeal(LLClaimPolicySchema tLLClaimPolicySchema,
			String state) {
		LOPolAfterDealSchema tLOPolAfterDealSchema = new LOPolAfterDealSchema();
		tLOPolAfterDealSchema.setAccAlterNo(mClmNo);
		tLOPolAfterDealSchema.setAccAlterType("4");
		tLOPolAfterDealSchema.setBusyType("CL");
		tLOPolAfterDealSchema.setConfDate(mCurrentDate);
		tLOPolAfterDealSchema.setContNo(tLLClaimPolicySchema.getContNo());
		tLOPolAfterDealSchema.setPolNo(tLLClaimPolicySchema.getPolNo());
		tLOPolAfterDealSchema.setManageCom(mGlobalInput.ManageCom);
		tLOPolAfterDealSchema.setState(state);

		tLOPolAfterDealSchema.setMakeDate(mCurrentDate);
		tLOPolAfterDealSchema.setMakeTime(mCurrentTime);
		tLOPolAfterDealSchema.setModifyDate(mCurrentDate);
		tLOPolAfterDealSchema.setModifyTime(mCurrentTime);
		map.put(tLOPolAfterDealSchema, "INSERT");
	}

	private void backUp(LLClaimPolicySchema tLLClaimPolicySchema) {
		LPInsureAccSet mLPInsureAccSet = new LPInsureAccSet();
		// LCInsureAccSet mLCInsureAccSet=new LCInsureAccSet();
		LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
		// LCInsureAccClassSet mLCInsureAccClassSet=new LCInsureAccClassSet();

		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
		tLCInsureAccDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLCInsureAccDB.setPolNo(tLLClaimPolicySchema.getPolNo());
		tLCInsureAccSet = tLCInsureAccDB.query();
		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
			LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
			tLCInsureAccSchema = tLCInsureAccSet.get(i);
			mReflections.transFields(tLPInsureAccSchema, tLCInsureAccSchema);
			tLPInsureAccSchema.setEdorNo(mClmNo);
			tLPInsureAccSchema.setEdorType("CL");

			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
			tLCInsureAccClassDB.setContNo(tLCInsureAccSchema.getContNo());
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
			tLCInsureAccClassSet = tLCInsureAccClassDB.query();
			for (int k = 1; k <= tLCInsureAccClassSet.size(); k++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
				LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
				tLCInsureAccClassSchema = tLCInsureAccClassSet.get(k);
				mReflections.transFields(tLPInsureAccClassSchema,
						tLCInsureAccClassSchema);
				tLPInsureAccClassSchema.setEdorNo(mClmNo);
				tLPInsureAccClassSchema.setEdorType("CL");

				mLPInsureAccClassSet.add(tLPInsureAccClassSchema);
			}
			mLPInsureAccSet.add(tLPInsureAccSchema);
		}

		map.put(mLPInsureAccSet, "INSERT");
		map.put(mLPInsureAccClassSet, "INSERT");
	}

	private boolean clearZero(LLClaimPolicySchema tLLClaimPolicySchema) {
		LCInsureAccSet tLCInsureAccSaveSet = new LCInsureAccSet();
		LCInsureAccClassSet tLCInsureAccClassSaveSet = new LCInsureAccClassSet();

		LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
		LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();

		tLCInsureAccDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLCInsureAccDB.setPolNo(tLLClaimPolicySchema.getPolNo());

		tLCInsureAccSet = tLCInsureAccDB.query();

		for (int i = 1; i <= tLCInsureAccSet.size(); i++) {

			LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet.get(i);

			tLCInsureAccSchema.setUnitCount(0);
			tLCInsureAccSchema.setInsuAccBala(0);

			tLCInsureAccSchema.setModifyDate(mCurrentDate);
			tLCInsureAccSchema.setModifyTime(mCurrentTime);

			LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
			tLCInsureAccClassDB.setPolNo(tLCInsureAccSchema.getPolNo());
			tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
			LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB
					.query();

			for (int m = 1; m <= tLCInsureAccClassSet.size(); m++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = tLCInsureAccClassSet
						.get(m);
				tLCInsureAccClassSchema.setUnitCount(0);
				tLCInsureAccClassSchema.setInsuAccBala(0);
				tLCInsureAccClassSchema.setModifyDate(mCurrentDate);
				tLCInsureAccClassSchema.setModifyTime(mCurrentTime);

				tLCInsureAccClassSaveSet.add(tLCInsureAccClassSchema);
			}
			tLCInsureAccSaveSet.add(tLCInsureAccSchema);
		}
		map.put(tLCInsureAccSaveSet, "UPDATE");
		map.put(tLCInsureAccClassSaveSet, "UPDATE");

		/*
		LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
		tLCInsureAccClassDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLCInsureAccClassDB.setPolNo(tLLClaimPolicySchema.getPolNo());
		LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB
				.query();
		map.put(tLCInsureAccSet, "DELETE");
		map.put(tLCInsureAccClassSet, "DELETE");
		*/
		return true;
	}

	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LLClaimClearBackBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		LLClaimClearAccBL tLLClaimClearAccBL = new LLClaimClearAccBL();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";
		tG.ManageCom = "86";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "90000060380");

		String operate = "MATCH||INSERT";

		VData cInputData = new VData();
		cInputData.add(tG);
		cInputData.add(tTransferData);

		tLLClaimClearAccBL.submitData(cInputData, operate);
	}

}
