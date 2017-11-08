package com.sinosoft.lis.tb;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LBPOAppntBL;
import com.sinosoft.lis.db.LBPOContDB;
import com.sinosoft.lis.db.LBPOCustomerImpartDB;
import com.sinosoft.lis.db.LBPOPolDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LBPOContSchema;
import com.sinosoft.lis.schema.LBPOInsuredSchema;
import com.sinosoft.lis.schema.LBPOPolSchema;
import com.sinosoft.lis.vschema.LBPOCustomerImpartSet;
import com.sinosoft.lis.vschema.LBPOPolSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class DSImpartBL {
private static Logger logger = Logger.getLogger(DSImpartBL.class);
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	private VData rResult = new VData(); // add by yaory

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	private GlobalInput mGlobalInput = new GlobalInput();

	private MMap map = new MMap();
	
	private TransferData tTransferData = new TransferData();     

	// 统一更新日期，时间
	private String theCurrentDate = PubFun.getCurrentDate();

	private String theCurrentTime = PubFun.getCurrentTime();

	private String mInsuredAppAge = "";
	
	/** 投保单如果是银代或简易投保单则BankFlag为1*/
	private String tBankFlag = "";
	
	private LBPOContSchema mLBPOContSchema = new LBPOContSchema();
	
	private LBPOCustomerImpartSet mLBPOCustomerImpartSet = new LBPOCustomerImpartSet();
	private LBPOCustomerImpartSet mmLBPOCustomerImpartSet = new LBPOCustomerImpartSet();

	public boolean submitData(VData cInputData, String cOperate) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) cInputData.clone();
		this.mOperate = cOperate;
		logger.debug("now in ContBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false) {
			return false;
		}
		logger.debug("---getInputData---");
		// 校验传入的数据
		// if (!mOperate.equals("DELETE||CONT"))
		// {
		if (this.checkData() == false) {
			return false;
		}
		logger.debug("---checkData---");
		// }

		// 根据业务逻辑对数据进行处理
		logger.debug("---dealData start---");
		if (this.dealData() == false) {
			return false;
		}
		logger.debug("---dealData  ended---");
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		logger.debug("---prepareOutputData---");

		PubSubmit tPubSubmit = new PubSubmit();

		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			CError tError = new CError();
			tError.moduleName = "ContBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据提交失败!";

			this.mErrors.addOneError(tError);
			return false;
		}

		logger.debug("---commitData---");
		return true;
	}
	
	private boolean getInputData(){
		mGlobalInput.setSchema((GlobalInput) mInputData
				.getObjectByObjectName("GlobalInput", 0));
		mLBPOCustomerImpartSet = (LBPOCustomerImpartSet) mInputData.getObjectByObjectName("LBPOCustomerImpartSet", 0);
		mLBPOContSchema.setSchema((LBPOContSchema)mInputData.getObjectByObjectName("LBPOContSchema", 0));
		tTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
		rResult = (VData) mInputData.getObjectByObjectName("VData", 0);
		mmLBPOCustomerImpartSet = (LBPOCustomerImpartSet) rResult.getObjectByObjectName("LBPOCustomerImpartSet", 0);
		mInsuredAppAge = (String) tTransferData.getValueByName("InsuredAppAge");
		tBankFlag = (String) tTransferData.getValueByName("BankFlag");
		logger.debug("被保人投保年龄："+mInsuredAppAge);
		return true;
	}
	
	private boolean checkData(){
		return true;
	}
	
	private boolean dealData(){
		LBPOContDB tLBPOContDB = new LBPOContDB();
		tLBPOContDB.setContNo(mLBPOContSchema.getContNo());
		tLBPOContDB.setInputNo(mLBPOContSchema.getInputNo());
		tLBPOContDB.setFillNo(mLBPOContSchema.getFillNo());
		if (tLBPOContDB.getInfo() == false) {
			CError tError = new CError();
			tError.moduleName = "DSImpartBL";
			tError.functionName = "dealData";
			tError.errorMessage = "查询合同信息失败!";
			this.mErrors.addOneError(tError);
			return false;
		}
		tLBPOContDB.setAppSignName(mLBPOContSchema.getAppSignName());
		if("1".equals(tBankFlag)){
			tLBPOContDB.setAccName(mLBPOContSchema.getAccName());
			tLBPOContDB.setBankCode(mLBPOContSchema.getBankCode());
			tLBPOContDB.setBankAccNo(mLBPOContSchema.getBankAccNo());
		}
		tLBPOContDB.setInsSignName2(mLBPOContSchema.getInsSignName2());
		tLBPOContDB.setPolApplyDate(mLBPOContSchema.getPolApplyDate());
		tLBPOContDB.setManageCom(mLBPOContSchema.getManageCom());
		tLBPOContDB.setAgentCode(mLBPOContSchema.getAgentCode());
		tLBPOContDB.setHandler(mLBPOContSchema.getHandler());
		tLBPOContDB.setSaleChnl(mLBPOContSchema.getSaleChnl());
		tLBPOContDB.setAgentComName(mLBPOContSchema.getAgentComName());
		tLBPOContDB.setAgentCom(mLBPOContSchema.getAgentCom());
		tLBPOContDB.setSignAgentName(mLBPOContSchema.getSignAgentName());
		tLBPOContDB.setAgentSignDate(mLBPOContSchema.getAgentSignDate());
		tLBPOContDB.setFirstTrialOperator(mLBPOContSchema.getFirstTrialOperator());
		tLBPOContDB.setFirstTrialDate(mLBPOContSchema.getFirstTrialDate());
		tLBPOContDB.setUWOperator(mLBPOContSchema.getUWOperator());
		tLBPOContDB.setUWDate(mLBPOContSchema.getUWDate());
		tLBPOContDB.setImpartRemark(mLBPOContSchema.getImpartRemark());
		tLBPOContDB.setLang(mLBPOContSchema.getLang());
		tLBPOContDB.setCurrency(mLBPOContSchema.getCurrency());
		tLBPOContDB.setModifyDate(theCurrentDate);
		tLBPOContDB.setModifyTime(theCurrentTime);
		map.put(tLBPOContDB.getSchema(), "UPDATE");
		for(int i=1;i<=mLBPOCustomerImpartSet.size();i++){
			mLBPOCustomerImpartSet.get(i).setOperator(mGlobalInput.Operator);
			mLBPOCustomerImpartSet.get(i).setModifyDate(theCurrentDate);
			mLBPOCustomerImpartSet.get(i).setModifyTime(theCurrentTime);
		}
		for(int i=1;i<=mmLBPOCustomerImpartSet.size();i++){
			mmLBPOCustomerImpartSet.get(i).setOperator(mGlobalInput.Operator);
			mmLBPOCustomerImpartSet.get(i).setModifyDate(theCurrentDate);
			mmLBPOCustomerImpartSet.get(i).setModifyTime(theCurrentTime);
		}
		map.put(mLBPOCustomerImpartSet, "UPDATE");
		map.put(mmLBPOCustomerImpartSet, "UPDATE");
		return true;
	}
	
	private boolean prepareOutputData(){
		mInputData.clear();
		mInputData.add(map);
		return true;
	}
	
	public VData getResult() {
		return mResult;
	}
}
