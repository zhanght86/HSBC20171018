package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;

public class LLClaimClearBackBL {
private static Logger logger = Logger.getLogger(LLClaimClearBackBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData;

	/** 全局数据 */

	private Reflections mReflections = new Reflections();

	/** 数据操作字符串 */
	private String mOperate;

	private String mClmNo;

	/** 业务处理相关变量 */
	private TransferData mTransferData = new TransferData();

	private LLClaimPolicySet mLLClaimPolicySet = new LLClaimPolicySet();

	private LOPolAfterDealSet mLOPolAfterDealSet = new LOPolAfterDealSet();

	private LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
	
	private String mBackNo = "";//回退号
	private String mContNo = "";
	private String mPolNo = "";
	private String mRiskCode = "";
	
	// private String mPlanCode = "";
	/** 时间信息 */
	String mCurrentDate = PubFun.getCurrentDate(); // 当前值

	String mCurrentTime = PubFun.getCurrentTime();

	private MMap map = new MMap();

	public LLClaimClearBackBL() {

	}

	public boolean submitData(VData cInputData, String operate) {
		mInputData = cInputData;
		// 数据操作字符串拷贝到本类中
		this.mOperate = operate;
		if (!getInputData()) {
			this.buildError("submitData", "无法获取输入信息");
			return false;
		}

		if (!dealData()) {
			this.buildError("submitData", "处理数据时失败");
			return false;
		}
		if (!prepareData()) {
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
	
	public VData getResult() {
		return mResult;
	}

	private boolean getInputData() {

		mTransferData = (TransferData) mInputData.getObjectByObjectName(
				"TransferData", 0);
		mClmNo = (String) mTransferData.getValueByName("ClmNo");
		mContNo = (String) mTransferData.getValueByName("ContNo");
		mPolNo = (String) mTransferData.getValueByName("PolNo");
		
		mBackNo = (String) mTransferData.getValueByName("BackNo");
		mRiskCode = (String) mTransferData.getValueByName("RiskCode");
		logger.debug("回退号为： "+mBackNo);

		return true;
	}
	
	private boolean dealData() {		
		boolean ipFlag = false;//默认不走后续处理
		LLClaimPolicySchema tLLClaimPolicySchema = new LLClaimPolicySchema();
		tLLClaimPolicySchema.setCaseNo(mClmNo);	
			tLLClaimPolicySchema.setContNo(mContNo);
			tLLClaimPolicySchema.setPolNo(mPolNo);
			tLLClaimPolicySchema.setRiskCode(mRiskCode);
			
				LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
				LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
				LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
				tLPInsureAccTraceDB.setMoneyType("BF");
				tLPInsureAccTraceDB.setState("0");
				tLPInsureAccTraceDB.setContNo(tLLClaimPolicySchema
						.getContNo());
				tLPInsureAccTraceDB.setPolNo(tLLClaimPolicySchema.getPolNo());
				tLPInsureAccTraceDB.setEdorNo(mClmNo);
				tLPInsureAccTraceDB.setEdorType("CL");
				tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
				if (tLPInsureAccTraceSet == null
						|| tLPInsureAccTraceSet.size() <= 0) {
				} else {
					ptoC(tLLClaimPolicySchema);// 无论是NB还是IP,只要是BF就要从p表转c表
				}
				returnBack(tLLClaimPolicySchema);
	
		return true;
	}

	public boolean ptoC(LLClaimPolicySchema tLLClaimPolicySchema) {
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
		LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
		LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
		tLPInsureAccTraceDB.setMoneyType("BF");
		tLPInsureAccTraceDB.setState("0");
		tLPInsureAccTraceDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLPInsureAccTraceDB.setPolNo(tLLClaimPolicySchema.getPolNo());
		tLPInsureAccTraceDB.setEdorNo(mClmNo);
		tLPInsureAccTraceDB.setEdorType("CL");
		tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
		for (int i = 1; i <= tLPInsureAccTraceSet.size(); i++) {
			LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
			LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
			tLPInsureAccTraceSchema = tLPInsureAccTraceSet.get(i);
			otherNo = tLPInsureAccTraceSchema.getOtherNo();
			otherType = tLPInsureAccTraceSchema.getOtherType();
			mReflections.transFields(tLCInsureAccTraceSchema,
					tLPInsureAccTraceSchema);

			mLPInsureAccTraceSet.add(tLPInsureAccTraceSchema);
			mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
		}

		// LCInsureAccFee转LPInsureAccFee LCInsureAccClassFee转LPInsureAccClassFee
		LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
		LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
		tLPInsureAccFeeDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLPInsureAccFeeDB.setPolNo(tLLClaimPolicySchema.getPolNo());
		tLPInsureAccFeeDB.setEdorNo(mClmNo);
		tLPInsureAccFeeDB.setEdorType("CL");
		tLPInsureAccFeeSet = tLPInsureAccFeeDB.query();
		for (int i = 1; i <= tLPInsureAccFeeSet.size(); i++) {
			LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
			LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
			tLPInsureAccFeeSchema = tLPInsureAccFeeSet.get(i);
			mReflections.transFields(tLCInsureAccFeeSchema,
					tLPInsureAccFeeSchema);

			LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
			LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
			tLPInsureAccClassFeeDB.setContNo(tLCInsureAccFeeSchema.getContNo());
			tLPInsureAccClassFeeDB.setPolNo(tLCInsureAccFeeSchema.getPolNo());
			tLPInsureAccClassFeeDB.setInsuAccNo(tLCInsureAccFeeSchema
					.getInsuAccNo());
			tLPInsureAccClassFeeDB.setEdorNo(mClmNo);
			tLPInsureAccClassFeeDB.setEdorType("CL");
			tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB.query();
			for (int k = 1; k <= tLPInsureAccClassFeeSet.size(); k++) {
				LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
				LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
				tLPInsureAccClassFeeSchema = tLPInsureAccClassFeeSet.get(k);
				mReflections.transFields(tLCInsureAccClassFeeSchema,
						tLPInsureAccClassFeeSchema);

				// LCInsureAccFeeTrace转LPInsureAccFeeTrace
				LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new LPInsureAccFeeTraceDB();
				LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
				tLPInsureAccFeeTraceDB.setContNo(tLPInsureAccClassFeeSchema
						.getContNo());
				tLPInsureAccFeeTraceDB.setPolNo(tLPInsureAccClassFeeSchema.getPolNo());
				tLPInsureAccFeeTraceDB.setInsuAccNo(tLPInsureAccClassFeeSchema
						.getInsuAccNo());
				tLPInsureAccFeeTraceDB
						.setPayPlanCode(tLPInsureAccClassFeeSchema
								.getPayPlanCode());
				tLPInsureAccFeeTraceDB.setFeeCode(tLPInsureAccClassFeeSchema
						.getFeeCode());
				tLPInsureAccFeeTraceDB.setOtherNo(otherNo);
				tLPInsureAccFeeTraceDB.setOtherType(otherType);
				tLPInsureAccFeeTraceDB.setEdorNo(mClmNo);
				tLPInsureAccFeeTraceDB.setEdorType("CL");
				tLPInsureAccFeeTraceSet = tLPInsureAccFeeTraceDB.query();
				for (int m = 1; m <= tLPInsureAccFeeTraceSet.size(); m++) {
					LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
					LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();
					tLPInsureAccFeeTraceSchema = tLPInsureAccFeeTraceSet.get(m);
					mReflections.transFields(tLCInsureAccFeeTraceSchema,
							tLPInsureAccFeeTraceSchema);

					mLPInsureAccFeeTraceSet.add(tLPInsureAccFeeTraceSchema);
					mLCInsureAccFeeTraceSet.add(tLCInsureAccFeeTraceSchema);
				}
				mLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
				mLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
			}
			mLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
			mLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
		}

		map.put(mLPInsureAccTraceSet, "DELETE");
		map.put(mLCInsureAccTraceSet, "INSERT");

		map.put(mLPInsureAccFeeSet, "DELETE");
		map.put(mLCInsureAccFeeSet, "UPDATE");

		map.put(mLPInsureAccClassFeeSet, "DELETE");
		map.put(mLCInsureAccClassFeeSet, "UPDATE");

		map.put(mLPInsureAccFeeTraceSet, "DELETE");
		map.put(mLCInsureAccFeeTraceSet, "DELETE&INSERT");

		return true;
	}

	private void returnBack(LLClaimPolicySchema tLLClaimPolicySchema){
		LPInsureAccSet mLPInsureAccSet = new LPInsureAccSet();
		LCInsureAccSet mLCInsureAccSet = new LCInsureAccSet();
		LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
		LCInsureAccClassSet mLCInsureAccClassSet = new LCInsureAccClassSet();

		LPInsureAccDB tLPInsureAccDB = new LPInsureAccDB();
		LPInsureAccSet tLPInsureAccSet = new LPInsureAccSet();
		tLPInsureAccDB.setContNo(tLLClaimPolicySchema.getContNo());
		tLPInsureAccDB.setPolNo(tLLClaimPolicySchema.getPolNo());
		tLPInsureAccDB.setEdorNo(mClmNo);
		tLPInsureAccDB.setEdorType("CL");
		tLPInsureAccSet = tLPInsureAccDB.query();
		for (int i = 1; i <= tLPInsureAccSet.size(); i++) {
			LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
			LPInsureAccSchema tLPInsureAccSchema = new LPInsureAccSchema();
			tLPInsureAccSchema = tLPInsureAccSet.get(i);
			mReflections.transFields(tLCInsureAccSchema,
					tLPInsureAccSchema);

			LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
			LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
			tLPInsureAccClassDB.setContNo(tLCInsureAccSchema.getContNo());
			tLPInsureAccClassDB.setPolNo(tLCInsureAccSchema.getPolNo());
			tLPInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema
					.getInsuAccNo());
			tLPInsureAccClassDB.setEdorNo(mClmNo);
			tLPInsureAccClassDB.setEdorType("CL");
			tLPInsureAccClassSet = tLPInsureAccClassDB.query();
			for (int k = 1; k <= tLPInsureAccClassSet.size(); k++) {
				LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
				LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
				tLPInsureAccClassSchema = tLPInsureAccClassSet.get(k);
				mReflections.transFields(tLCInsureAccClassSchema,
						tLPInsureAccClassSchema);

				mLPInsureAccClassSet.add(tLPInsureAccClassSchema);
				mLCInsureAccClassSet.add(tLCInsureAccClassSchema);
			}
			mLPInsureAccSet.add(tLPInsureAccSchema);
			mLCInsureAccSet.add(tLCInsureAccSchema);
		}

		map.put(mLPInsureAccClassSet, "DELETE");
		map.put(mLCInsureAccClassSet, "DELETE&INSERT");

		map.put(mLPInsureAccSet, "DELETE");
		map.put(mLCInsureAccSet, "DELETE&INSERT");
		
		//增加对trace表的处理 置负值
		LCInsureAccTraceDB tLCInsureAccTraceDB=new LCInsureAccTraceDB();
	       LCInsureAccTraceSet tLCInsureAccTraceSet=new LCInsureAccTraceSet();
	       LCInsureAccTraceSet mLCInsureAccTraceSet=new LCInsureAccTraceSet();
	       
	       tLCInsureAccTraceDB.setBusyType("CL");
	       tLCInsureAccTraceDB.setContNo(tLLClaimPolicySchema.getContNo());
	       tLCInsureAccTraceDB.setPolNo(tLLClaimPolicySchema.getPolNo());
	       tLCInsureAccTraceDB.setAccAlterNo(tLLClaimPolicySchema.getClmNo());
	       
	       tLCInsureAccTraceSet=tLCInsureAccTraceDB.query();
	       
	       for (int k = 1; k <= tLCInsureAccTraceSet.size(); k++) {
				LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
				String tserNo = "CLM" + PubFun1.CreateMaxNo("CLMACC", 10);
				tLCInsureAccTraceSchema.setSchema(tLCInsureAccTraceSet.get(k));
				tLCInsureAccTraceSchema.setUnitCount(tLCInsureAccTraceSchema.getUnitCount()*(-1));
				tLCInsureAccTraceSchema.setMoney(tLCInsureAccTraceSchema.getMoney()*(-1));
				tLCInsureAccTraceSchema.setSerialNo(tserNo);
				tLCInsureAccTraceSchema.setBusyType("EB");//回退
				mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
			}
	       map.put(mLCInsureAccTraceSet, "INSERT");
	}
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "LLClaimClearBackBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
		LLClaimClearBackBL tLLClaimClearBackBL = new LLClaimClearBackBL();
		GlobalInput tG = new GlobalInput();
		tG.Operator = "001";

		TransferData tTransferData = new TransferData();
		tTransferData.setNameAndValue("ClmNo", "86000020100510000024");

		String operate = "DELETE";

		VData cInputData = new VData();
		cInputData.add(tG);
		cInputData.add(tTransferData);

		tLLClaimClearBackBL.submitData(cInputData, operate);
	}

}
