

package com.sinosoft.productdef;

import com.sinosoft.lis.db.PD_LMRiskDB;
import com.sinosoft.lis.db.PD_LMRiskPayDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PD_LMRiskPaySchema;
import com.sinosoft.lis.schema.PD_LMRiskSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

public class PDRiskGraceBL implements BusinessService {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	private VData mResult = new VData();

	/** 往后面传输数据的容器 */
	private VData mInputData = new VData();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();

	/** 数据操作字符串 */
	private String mOperate;

	/** 业务处理相关变量 */
	private MMap map = new MMap();

	private TransferData mTransferData;
	
	private PD_LMRiskPaySchema mPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
	private PD_LMRiskSchema mPD_LMRiskSchema = new PD_LMRiskSchema();
	private String mCalCodeType = "";
	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}
	
	/**
	 * 传输数据的公共方法
	 * 
	 * @param: data 输入的数据 Operater 数据操作
	 * @return:
	 */
	public boolean submitData(VData data, String Operater) {
		if (!getInputData(data, Operater)) {
			return false;
		}
		
		if(!checkData()){
			return false;
		}

		if (!dealData()) {
			return false;
		}
		
		this.mInputData.clear();
		this.mInputData.add(this.map);
		PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            this.createError("submitData", "数据提交失败!");
            return false;
        }
		return true;
	}
	
	/**
	 * 获取基础数据
	 * @see CommonBase.java, The method getInputData()
	 * @param cInputData
	 * @return
	 */
	private boolean getInputData(VData cInputData, String cOperate) {
		this.mInputData = cInputData;
		this.mOperate = cOperate;
		mTransferData = (TransferData) cInputData.getObjectByObjectName(
				"TransferData", 0);
		mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
				"GlobalInput", 0);
		mPD_LMRiskPaySchema = (PD_LMRiskPaySchema)cInputData.getObjectByObjectName(
				"PD_LMRiskPaySchema", 0);
		this.mCalCodeType = mTransferData.getValueByName("CalCodeType")==null?"":(String)mTransferData.getValueByName("CalCodeType");
		return true;
	}
	
	/**
	 * 校验数据正确性
	 * @return boolean
	 * */
	private boolean checkData(){
		if(mOperate==null || "".equals(mOperate)){
			this.createError("checkData", "前台传递操作类型有误");
			return false;
		}
		if(mPD_LMRiskPaySchema.getRiskCode()==null || "".equals(mPD_LMRiskPaySchema.getRiskCode())){
			this.createError("checkData", "前台传递宽限期信息有误");
			return false;
		}
		PD_LMRiskDB tPD_LMRiskDB = new PD_LMRiskDB();
		tPD_LMRiskDB.setRiskCode(mPD_LMRiskPaySchema.getRiskCode());
		if(!tPD_LMRiskDB.getInfo()){
			this.createError("checkData", "险种基础信息尚未保存，请先保存险种基础信息");
			return false;
		}
		mPD_LMRiskSchema = tPD_LMRiskDB.getSchema();
		
		return true;
	}
	
	/**
	 * 业务处理，保存/修改/删除宽限期信息
	 * 
	 * @return boolean
	 */
	private boolean dealData() {
		if("save".equals(mOperate)){
			mPD_LMRiskPaySchema.setRiskVer(mPD_LMRiskSchema.getRiskVer());
			mPD_LMRiskPaySchema.setRiskName(mPD_LMRiskSchema.getRiskName());
			mPD_LMRiskPaySchema.setOperator(this.mGlobalInput.Operator);
			mPD_LMRiskPaySchema.setMakeDate(PubFun.getCurrentDate());
			mPD_LMRiskPaySchema.setMakeTime(PubFun.getCurrentTime());
			mPD_LMRiskPaySchema.setModifyDate(PubFun.getCurrentDate());
			mPD_LMRiskPaySchema.setModifyTime(PubFun.getCurrentTime());
			PD_LMRiskPayDB tPD_LMRiskPayDB=new PD_LMRiskPayDB();
			tPD_LMRiskPayDB.setRiskCode(mPD_LMRiskPaySchema.getRiskCode());
			if(tPD_LMRiskPayDB.getInfo()){
				this.createError("dealData", "宽限期信息已经存在，无法做该操作！");
				return false;
			}
			//如果没有录入宽限期，则需要生成宽限期算法
			if(mPD_LMRiskPaySchema.getGracePeriod()==0){
				//tongmeng 2011-07-19 add
				String tCalCode = mPD_LMRiskPaySchema.getGraceCalCode();
				if(tCalCode==null||tCalCode.equals(""))
		 		{
					tCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
		 			//mTransferData.removeByName("CALCODE");
		 			//mTransferData.setNameAndValue("CALCODE", payCalCode);
		 			//this.mResult.add(0,tCalCode);
		 		}
		 		
		 		else
		 		{
		 			//校验算法类型和算法编码的关系
		 			if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
		 			||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
		 			{
		 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
		 				return false;
		 			}
		 		}
				//mPD_LMRiskPaySchema.setGraceCalCode(this.createGraceCalCode());
				mPD_LMRiskPaySchema.setGraceCalCode(tCalCode);
			}
			
			this.map.put(mPD_LMRiskPaySchema, "INSERT");
			this.mResult.add(mPD_LMRiskPaySchema);
		}else if("update".equals(mOperate)){
			PD_LMRiskPaySchema tPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
			PD_LMRiskPayDB tPD_LMRiskPayDB = new PD_LMRiskPayDB();
			tPD_LMRiskPayDB.setRiskCode(mPD_LMRiskPaySchema.getRiskCode());
			if(!tPD_LMRiskPayDB.getInfo()){
				this.createError("dealData", "宽限期信息尚未保存，无法做该操作");
				return false;
			}
			tPD_LMRiskPaySchema = tPD_LMRiskPayDB.getSchema();
			tPD_LMRiskPaySchema.setGracePeriod(mPD_LMRiskPaySchema.getGracePeriod());
			tPD_LMRiskPaySchema.setGracePeriodUnit(mPD_LMRiskPaySchema.getGracePeriodUnit());
			tPD_LMRiskPaySchema.setGraceDateCalMode(mPD_LMRiskPaySchema.getGraceDateCalMode());
			tPD_LMRiskPaySchema.setOverdueDeal(mPD_LMRiskPaySchema.getOverdueDeal());
			tPD_LMRiskPaySchema.setUrgePayFlag(mPD_LMRiskPaySchema.getUrgePayFlag());
			tPD_LMRiskPaySchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMRiskPaySchema.setModifyTime(PubFun.getCurrentTime());
			//如果没有录入宽限期，则需要生成宽限期算法
			if (mPD_LMRiskPaySchema.getGracePeriod()==0
					&& (tPD_LMRiskPaySchema.getGraceCalCode() == null
							|| "".equals(tPD_LMRiskPaySchema.getGraceCalCode()))) {
				//tPD_LMRiskPaySchema.setGraceCalCode(this.createGraceCalCode());
				//tongmeng 2011-07-19 add
				String tCalCode = tPD_LMRiskPaySchema.getGraceCalCode();
				if(tCalCode==null||tCalCode.equals(""))
		 		{
					tCalCode = PubFun1.CreateRuleCalCode("PD",mCalCodeType);
		 			//mTransferData.removeByName("CALCODE");
		 			//mTransferData.setNameAndValue("CALCODE", payCalCode);
		 			//this.mResult.add(0,tCalCode);
		 		}
		 		
		 		else
		 		{
		 			//校验算法类型和算法编码的关系
		 			if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
		 			||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
		 			{
		 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
		 				return false;
		 			}
		 		}
				//mPD_LMRiskPaySchema.setGraceCalCode(this.createGraceCalCode());
				tPD_LMRiskPaySchema.setGraceCalCode(tCalCode);
			}
			
			if(null!=tPD_LMRiskPaySchema.getGraceCalCode()&&!"".equals(tPD_LMRiskPaySchema.getGraceCalCode()))
			{
				//校验算法类型和算法编码的关系
				String tCalCode = tPD_LMRiskPaySchema.getGraceCalCode();
	 			if((tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("N"))
	 			||!tCalCode.substring(0,2).toUpperCase().equals("RU")&&mCalCodeType.equals("Y"))
	 			{
	 				CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
	 				return false;
	 			}
			}
			
			this.map.put(tPD_LMRiskPaySchema, "UPDATE");
			this.mResult.add(tPD_LMRiskPaySchema);
		}else if("del".equals(mOperate)){
			PD_LMRiskPaySchema tPD_LMRiskPaySchema = new PD_LMRiskPaySchema();
			PD_LMRiskPayDB tPD_LMRiskPayDB = new PD_LMRiskPayDB();
			tPD_LMRiskPayDB.setRiskCode(mPD_LMRiskPaySchema.getRiskCode());
			if(!tPD_LMRiskPayDB.getInfo()){
				this.createError("dealData", "宽限期信息尚未保存，无法做该操作");
				return false;
			}
			tPD_LMRiskPaySchema = tPD_LMRiskPayDB.getSchema();
			
			this.map.put(tPD_LMRiskPaySchema, "DELETE");
		}else{
			this.createError("dealData", "无法识别的操作类型");
			return false;
		}
		
		return true;
	}
	
	/**
	 * 生成一个错误信息并添加到错误容器中
	 * @param mFunc:产生错误的方法；mMessage:错误信息
	 * */
	private void createError(String mFunc,String mMessage){
        CError tError = new CError();
        tError.moduleName = "PDRiskGraceBL";
        tError.functionName = mFunc;
        tError.errorMessage = mMessage;
        this.mErrors.addOneError(tError);
	}
	
	/**
	 * 生成宽限期算法编码，RG+4位流水号
	 * @return 生成的算法编码
	 * */
	private String createGraceCalCode(){
		String noType = "CALMODEGRACENO";
		String tempStr = PubFun1.CreateMaxNo(noType, 4);
		String tCalCode = "RG" + tempStr;
		return tCalCode;
	}

}
