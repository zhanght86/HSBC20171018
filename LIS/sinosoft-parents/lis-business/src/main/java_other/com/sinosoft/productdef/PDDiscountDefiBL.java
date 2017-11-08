

package com.sinosoft.productdef;

import com.sinosoft.lis.db.PD_LMDiscountDB;
import com.sinosoft.lis.db.PD_LMDutyDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.PD_LMDiscountSchema;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

public class PDDiscountDefiBL implements BusinessService {
	/** 传入数据的容器 */
	private VData mInputData = new VData();

	/** 往前面传输数据的容器 */
	private VData mResult = new VData();

	/** 数据操作字符串 */
	private String mOperate;

	/** 错误处理类 */
	public CErrors mErrors = new CErrors();

	/** 全局数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private MMap map = new MMap();
	
	/** 业务处理相关变量 */
	private PD_LMDiscountSchema mPD_LMDiscountSchema = new PD_LMDiscountSchema();

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return mResult;
	}

	public boolean submitData(VData data, String Operater) {
		// 将传入的数据拷贝到本类中
		mInputData = (VData) data.clone();
		this.mOperate = Operater;
		System.out.println("now in PDDiscountDefiBL submit");
		// 将外部传入的数据分解到本类的属性中，准备处理
		if (this.getInputData() == false)
			return false;
		System.out.println("---getInputData---");
		if (this.checkData() == false)
			return false;
		System.out.println("---checkData---");

		// 根据业务逻辑对数据进行处理
		if (this.dealData() == false)
			return false;
		
		// 装配处理好的数据，准备给后台进行保存
		this.prepareOutputData();
		System.out.println("---prepareOutputData---");

		// 数据提交、保存
		PubSubmit tPubSubmit = new PubSubmit();
		System.out.println("Start PDDiscountDefiBL Submit...");
		if (!tPubSubmit.submitData(mInputData, mOperate)) {
			// @@错误处理
			this.mErrors.copyAllErrors(tPubSubmit.mErrors);

			this.createError("submitData", "数据提交失败");
			return false;
		}

		System.out.println("---commitData---");
		
		return true;
	}
	
	/**
	 * 将外部传入的数据分解到本类的属性中
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean getInputData() {
		try {
			// 全局变量
			mGlobalInput.setSchema((GlobalInput) mInputData
					.getObjectByObjectName("GlobalInput", 0));
			mPD_LMDiscountSchema.setSchema((PD_LMDiscountSchema) mInputData
					.getObjectByObjectName("PD_LMDiscountSchema", 0));
		} catch (Exception ex) {
			this.createError("getInputData", ex.toString());
			return false;
		}
		
		return true;
	}
	
	/**
	 * 校验传入的数据
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean checkData() {
		if(mOperate==null || "".equals(mOperate)){
			this.createError("checkData", "前台传递操作类型有误");
			return false;
		}
		if(!"del".equals(mOperate)){
			if(mPD_LMDiscountSchema.getDutyCode()!=null && !"".equals(mPD_LMDiscountSchema.getDutyCode())
					&& !"000000".equals(mPD_LMDiscountSchema.getDutyCode())){
				PD_LMDutyDB tPD_LMDutyDB= new PD_LMDutyDB();
				tPD_LMDutyDB.setDutyCode(mPD_LMDiscountSchema.getDutyCode());
				if(!tPD_LMDutyDB.getInfo()){
					this.createError("checkData", "录入的险种责任不存在");
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: void
	 */
	private void prepareOutputData() {
		mInputData.clear();
		mInputData.add(map);
	}
	
	/**
	 * 根据业务逻辑对数据进行处理
	 * 
	 * @param: 无
	 * @return: boolean
	 */
	private boolean dealData() {
		if("save".equals(mOperate)){
			mPD_LMDiscountSchema.setDiscountType("D_PR");
			mPD_LMDiscountSchema.setDiscountCode(PubFun1.CreateMaxNo("DiscountCode", 20));
			mPD_LMDiscountSchema.setOperator(this.mGlobalInput.Operator);
			mPD_LMDiscountSchema.setMakeDate(PubFun.getCurrentDate());
			mPD_LMDiscountSchema.setMakeTime(PubFun.getCurrentTime());
			mPD_LMDiscountSchema.setModifyDate(PubFun.getCurrentDate());
			mPD_LMDiscountSchema.setModifyTime(PubFun.getCurrentTime());
			//生成折扣算法
			mPD_LMDiscountSchema.setCalCode(this.createDiscountCalCode());
			if(mPD_LMDiscountSchema.getDutyCode()==null||mPD_LMDiscountSchema.getDutyCode().equals(""))
			{
				mPD_LMDiscountSchema.setDutyCode("000000");
			}
			
			this.map.put(mPD_LMDiscountSchema, "INSERT");
			this.mResult.add(0,mPD_LMDiscountSchema.getDiscountCode());
		}else if("update".equals(mOperate)){
			PD_LMDiscountDB tPD_LMDiscountDB = new PD_LMDiscountDB();
			tPD_LMDiscountDB.setDiscountCode(mPD_LMDiscountSchema.getDiscountCode());
			if(!tPD_LMDiscountDB.getInfo()){
				this.createError("dealData", "产品折扣信息尚未保存，无法做该操作");
				return false;
			}
			PD_LMDiscountSchema tPD_LMDiscountSchema = new PD_LMDiscountSchema();
			tPD_LMDiscountSchema.setSchema(tPD_LMDiscountDB.getSchema());
			tPD_LMDiscountSchema.setAddFeeDiscFlag(mPD_LMDiscountSchema.getAddFeeDiscFlag());
			tPD_LMDiscountSchema.setStartDate(mPD_LMDiscountSchema.getStartDate());
			tPD_LMDiscountSchema.setEndDate(mPD_LMDiscountSchema.getEndDate());
			tPD_LMDiscountSchema.setModifyDate(PubFun.getCurrentDate());
			tPD_LMDiscountSchema.setModifyTime(PubFun.getCurrentTime());
			if(mPD_LMDiscountSchema.getDutyCode()==null||mPD_LMDiscountSchema.getDutyCode().equals(""))
			{
				tPD_LMDiscountSchema.setDutyCode("000000");
			}else{
				tPD_LMDiscountSchema.setDutyCode(mPD_LMDiscountSchema.getDutyCode());
			}
			
			this.map.put(tPD_LMDiscountSchema, "UPDATE");
			this.mResult.add(0,"");
		}else if("del".equals(mOperate)){
			PD_LMDiscountDB tPD_LMDiscountDB = new PD_LMDiscountDB();
			tPD_LMDiscountDB.setDiscountCode(mPD_LMDiscountSchema.getDiscountCode());
			if(!tPD_LMDiscountDB.getInfo()){
				this.createError("dealData", "产品折扣信息尚未保存，无法做该操作");
				return false;
			}
			PD_LMDiscountSchema tPD_LMDiscountSchema = new PD_LMDiscountSchema();
			tPD_LMDiscountSchema.setSchema(tPD_LMDiscountDB.getSchema());
			this.map.put(tPD_LMDiscountSchema, "DELETE");
			this.mResult.add(0,"");
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
        tError.moduleName = this.getClass().getName();
        tError.functionName = mFunc;
        tError.errorMessage = mMessage;
        this.mErrors.addOneError(tError);
	}
	
	/**
	 * 生成产品折扣算法编码，RD+4位流水号
	 * @return 生成的算法编码
	 * */
	private String createDiscountCalCode(){
//		String noType = "CALMODEDISCTNO";
//		String tempStr = PubFun1.CreateMaxNo(noType, 4);
//		String tCalCode = "RD" + tempStr;
		String tCalCode = PubFun1.CreateRuleCalCode("DS");
		return tCalCode;
	}

}
