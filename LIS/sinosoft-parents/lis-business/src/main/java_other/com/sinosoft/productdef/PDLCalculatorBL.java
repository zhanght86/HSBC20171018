/**
 * <p>Title: PDDutyGetClm</p>
 * <p>Description: 责任给付生存</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */

package com.sinosoft.productdef;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.PD_LCalculatorFactorSet;
import com.sinosoft.lis.vschema.PD_LCalculatorFeeCodeSet;
import com.sinosoft.lis.vschema.PD_LDPlanFeeRelaSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * 程序名称：累加器BL 程序功能：累加器配置 创建时间：2016-5-26 创建人：王海超
 */

public class PDLCalculatorBL implements BusinessService {
	private static Logger logger = Logger.getLogger(PDLCalculatorBL.class);
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

	private TransferData mTransferData = new TransferData();
	private PD_LCalculatorMainSchema mPD_LCalculatorMainSchema = new PD_LCalculatorMainSchema();
	private PD_LCalculatorNatureTimeSchema mPD_LCalculatorNatureTimeSchema = new PD_LCalculatorNatureTimeSchema();
	private PD_LCalculatorInsuranceTimeSchema mPD_LCalculatorInsuranceTimeSchema = new PD_LCalculatorInsuranceTimeSchema();
	private PD_LCalculatorOrderSchema mPD_LCalculatorOrderSchema = new PD_LCalculatorOrderSchema();
	private PD_LCalculatorFactorSchema mPD_LCalculatorFactorSchema = new PD_LCalculatorFactorSchema();
	private PD_LCalculatorFeeCodeSchema mPD_LCalculatorFeeCodeSchema= new PD_LCalculatorFeeCodeSchema();
	private PD_LDPlanFeeRelaSchema mPD_LDPlanFeeRelaSchema=new PD_LDPlanFeeRelaSchema();

	private String mCalCodeType = "";
	private String RiskCode = "";
	private String GetDutyCode = "";
	private String mCalculatorCode = "";
	private String mTableName = "";
	private String FlagStr="";

	public PDLCalculatorBL() {
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		this.mOperate = cOperate;
		// 获取数据
		if (!getInputData(cInputData)) {
			this.mResult.add(0, this.mErrors.getFirstError());
			return false;
		}
		// 数据校验
		if (!check()) {
			this.mResult.add(0, this.mErrors.getFirstError());
			return false;
		}
		// 数据处理
		if (!dealData()) {
			CError tError = new CError();
			tError.moduleName = "PDLCalculatorBL";
			tError.functionName = "submitData";
			tError.errorMessage = "数据处理失败PDLCalculatorBL-->dealData!";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData()) {
			return false;
		}

		PubSubmit tSubmit = new PubSubmit();
		if (!tSubmit.submitData(mInputData, "")) {
			CError tError = new CError();
			tError.moduleName = "PDLCalculatorBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "数据提交失败。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	private boolean getInputData(VData cInputData) {
		try {
			mTransferData = (TransferData) cInputData.getObjectByObjectName(
					"TransferData", 0);
			mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
					"GlobalInput", 0);
			this.mPD_LCalculatorInsuranceTimeSchema = (PD_LCalculatorInsuranceTimeSchema) cInputData
					.getObjectByObjectName("PD_LCalculatorInsuranceTimeSchema",
							0);
			this.mPD_LCalculatorMainSchema = (PD_LCalculatorMainSchema) cInputData
					.getObjectByObjectName("PD_LCalculatorMainSchema", 0);
			this.mPD_LCalculatorNatureTimeSchema = (PD_LCalculatorNatureTimeSchema) cInputData
					.getObjectByObjectName("PD_LCalculatorNatureTimeSchema", 0);
			this.mPD_LCalculatorOrderSchema = (PD_LCalculatorOrderSchema) cInputData
					.getObjectByObjectName("PD_LCalculatorOrderSchema", 0);
			this.mPD_LCalculatorFactorSchema = (PD_LCalculatorFactorSchema) cInputData
					.getObjectByObjectName("PD_LCalculatorFactorSchema", 0);

			this.mPD_LCalculatorFeeCodeSchema = (PD_LCalculatorFeeCodeSchema) cInputData
					.getObjectByObjectName("PD_LCalculatorFeeCodeSchema", 0);
			this.mPD_LDPlanFeeRelaSchema=(PD_LDPlanFeeRelaSchema) cInputData
			.getObjectByObjectName("PD_LDPlanFeeRelaSchema", 0);
			
			
			if(mPD_LCalculatorMainSchema.getCtrlLevel()>=3){
				mPD_LDPlanFeeRelaSchema=null;
			}
			
			FlagStr=(String) mTransferData
			.getValueByName("FlagStr");
			
			// 累加器编码
			this.mCalculatorCode = (String) mTransferData
					.getValueByName("CalculatorCode");

			// 增加算法编码
			if ("3".equals(mPD_LCalculatorMainSchema.getCalMode())) {
				String tCalCode = mTransferData.getValueByName("CALCODE") == null ? ""
						: (String) mTransferData.getValueByName("CALCODE");
				this.mCalCodeType = (String) mTransferData
						.getValueByName("CalCodeType");
				this.mTableName = (String) mTransferData
						.getValueByName("tableName");
				if (tCalCode.equals("")) {
					tCalCode = PubFun1.CreateRuleCalCode("PD", mCalCodeType);
					mTransferData.removeByName("CALCODE");
					mTransferData.setNameAndValue("CALCODE", tCalCode);
					this.mResult.add(0, tCalCode);
				}

				else {
					// 校验算法类型和算法编码的关系
					if ((tCalCode.substring(0, 2).toUpperCase().equals("RU") && mCalCodeType
							.equals("N"))
							|| !tCalCode.substring(0, 2).toUpperCase()
									.equals("RU") && mCalCodeType.equals("Y")) {
						CError.buildErr(this, "算法编码与算法类型不一致,请删除后重新添加!");
						return false;
					}
					this.mResult.add(0, tCalCode);
				}
				mPD_LCalculatorMainSchema.setCalCode(tCalCode);
			} else {
				this.mResult.add(0, "");
				mPD_LCalculatorMainSchema.setCalCode("");
			}
			this.RiskCode = (String) mTransferData.getValueByName("RiskCode");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			CError.buildErr(this, "获取数据出错!");

			return false;
		}

		return true;
	}

	private boolean dealData() {
		try {
			// 判断操作类型
			if (this.mOperate.equals("INSERT")) {
				if (mPD_LCalculatorInsuranceTimeSchema != null) {
					map.put(this.mPD_LCalculatorInsuranceTimeSchema, "INSERT");
				}
				if (mPD_LCalculatorMainSchema != null) {
					map.put(this.mPD_LCalculatorMainSchema, "INSERT");
				}
				if (mPD_LCalculatorNatureTimeSchema != null) {
					map.put(this.mPD_LCalculatorNatureTimeSchema, "INSERT");
				}
				if (mPD_LCalculatorOrderSchema != null) {
					map.put(this.mPD_LCalculatorOrderSchema, "INSERT");
				}
				if (mPD_LCalculatorFactorSchema != null) {
					map.put(mPD_LCalculatorFactorSchema, "INSERT");
				}
				if (mPD_LCalculatorFeeCodeSchema != null) {
					map.put(mPD_LCalculatorFeeCodeSchema, "INSERT");
				}
//				if(mPD_LDPlanFeeRelaSchema!=null){
//					map.put(mPD_LDPlanFeeRelaSchema, "INSERT");
//				}
			}
			if (this.mOperate.equals("UPDATE")) {
				if (mPD_LCalculatorInsuranceTimeSchema != null) {
					map.put(this.mPD_LCalculatorInsuranceTimeSchema,
							"DELETE&INSERT");
				}
				if (mPD_LCalculatorMainSchema != null) {
					map.put(this.mPD_LCalculatorMainSchema, "DELETE&INSERT");
				}
				if (mPD_LCalculatorNatureTimeSchema != null) {
					map.put(this.mPD_LCalculatorNatureTimeSchema,
							"DELETE&INSERT");
				}
				if (mPD_LCalculatorOrderSchema != null) {
					map.put(this.mPD_LCalculatorOrderSchema, "DELETE&INSERT");
				}
//				if(mPD_LCalculatorFeeCodeSchema!=null){
//					map.put(mPD_LCalculatorFeeCodeSchema, "DELETE&INSERT");
//				}
				if (mPD_LDPlanFeeRelaSchema != null) {
					map.put(this.mPD_LDPlanFeeRelaSchema, "UPDATE");
				}
			}
			if (this.mOperate.equals("DELETE")) {
				//如果累加器关联表中的对应的累加器大于1条，则只是删除关联表中的信息，其他信息不删除；
				String checksql="select count(1) from pd_lcalculatorfactor where calculatorcode='?calculatorcode?'";
		 		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		 		sqlbv.sql(checksql);
		 		sqlbv.put("calculatorcode", mPD_LCalculatorFactorSchema.getCalculatorCode());
		 		String number = (new ExeSQL()).getOneValue(sqlbv);
		 		int count=Integer.parseInt(number);
				if(count==1){
					if (mPD_LCalculatorInsuranceTimeSchema != null) {
						map.put(this.mPD_LCalculatorInsuranceTimeSchema, "DELETE");
					}
					if (mPD_LCalculatorMainSchema != null) {
						map.put(this.mPD_LCalculatorMainSchema, "DELETE");
					}
					if (mPD_LCalculatorNatureTimeSchema != null) {
						map.put(this.mPD_LCalculatorNatureTimeSchema, "DELETE");
					}
					if (mPD_LCalculatorOrderSchema != null) {
						map.put(this.mPD_LCalculatorOrderSchema, "DELETE");
					}
					if(mPD_LCalculatorFeeCodeSchema!=null){
						map.put(mPD_LCalculatorFeeCodeSchema, "DELETE");
					}
				}
				if (mPD_LCalculatorFactorSchema!= null){
					map.put(mPD_LCalculatorFactorSchema, "DELETE");
				}

				
			}
		} catch (Exception e) {
			e.printStackTrace();
			CError.buildErr(this, "获取数据出错!");
			return false;
		}

		return true;
	}

	private boolean check() {
		/* 根据操作类型，针对前台的数据进行校验和赋值 */
		if (mOperate != null && !"".equals(mOperate)
				&& "INSERT".equals(mOperate) && "0".equals(FlagStr)) {
//			//生成累加器的编码
//			String calculatorcode=PubFun1.CreateMaxNo("PD_LCalculatorMain", 8);
			
			//PD_LCalculatorMain
			if(mPD_LCalculatorMainSchema!=null){
			PD_LCalculatorMainDB tPD_LCalculatorMainDB=new PD_LCalculatorMainDB();
			tPD_LCalculatorMainDB.setCalculatorCode(mPD_LCalculatorMainSchema.getCalculatorCode());
			if(tPD_LCalculatorMainDB.getInfo()){
				mPD_LCalculatorMainSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				mPD_LCalculatorMainSchema.setOperator(mGlobalInput.Operator);
				mPD_LCalculatorMainSchema.setMakeDate(PubFun.getCurrentDate());
				mPD_LCalculatorMainSchema.setMakeTime(PubFun.getCurrentTime());
				mPD_LCalculatorMainSchema.setModifyDate(PubFun.getCurrentDate());
				mPD_LCalculatorMainSchema.setModifyTime(PubFun.getCurrentTime());
			}
			}
			//pd_lcalculatorinsurancetime
			if(mPD_LCalculatorInsuranceTimeSchema!=null){
			PD_LCalculatorInsuranceTimeDB tPD_LCalculatorInsuranceTimeDB=new PD_LCalculatorInsuranceTimeDB();
			tPD_LCalculatorInsuranceTimeDB.setCalculatorCode(mPD_LCalculatorInsuranceTimeSchema.getCalculatorCode());
			if(tPD_LCalculatorInsuranceTimeDB.getInfo()){
				mPD_LCalculatorInsuranceTimeSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				mPD_LCalculatorInsuranceTimeSchema.setOperator(mGlobalInput.Operator);
				mPD_LCalculatorInsuranceTimeSchema.setMakeDate(PubFun.getCurrentDate());
				mPD_LCalculatorInsuranceTimeSchema.setMakeTime(PubFun.getCurrentTime());
				mPD_LCalculatorInsuranceTimeSchema.setModifyDate(PubFun.getCurrentDate());
				mPD_LCalculatorInsuranceTimeSchema.setModifyTime(PubFun.getCurrentTime());
			}
			}
			//pd_lcalculatornaturetime
			if(mPD_LCalculatorNatureTimeSchema!=null){
			PD_LCalculatorNatureTimeDB tPD_LCalculatorNatureTimeDB=new PD_LCalculatorNatureTimeDB();
			tPD_LCalculatorNatureTimeDB.setCalculatorCode(mPD_LCalculatorNatureTimeSchema.getCalculatorCode());
			if(tPD_LCalculatorNatureTimeDB.getInfo()){
				mPD_LCalculatorNatureTimeSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				mPD_LCalculatorNatureTimeSchema.setOperator(mGlobalInput.Operator);
				mPD_LCalculatorNatureTimeSchema.setMakeDate(PubFun.getCurrentDate());
				mPD_LCalculatorNatureTimeSchema.setMakeTime(PubFun.getCurrentTime());
				mPD_LCalculatorNatureTimeSchema.setModifyDate(PubFun.getCurrentDate());
				mPD_LCalculatorNatureTimeSchema.setModifyTime(PubFun.getCurrentTime());
			}
			}
			//pd_lcalculatororder
			if(mPD_LCalculatorOrderSchema!=null){
			PD_LCalculatorOrderDB tPD_LCalculatorOrderDB=new PD_LCalculatorOrderDB();
			tPD_LCalculatorOrderDB.setCalculatorCode(mPD_LCalculatorOrderSchema.getCalculatorCode());
			if(tPD_LCalculatorOrderDB.getInfo()){
				mPD_LCalculatorOrderSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				mPD_LCalculatorOrderSchema.setOperator(mGlobalInput.Operator);
				mPD_LCalculatorOrderSchema.setMakeDate(PubFun.getCurrentDate());
				mPD_LCalculatorOrderSchema.setMakeTime(PubFun.getCurrentTime());
				mPD_LCalculatorOrderSchema.setModifyDate(PubFun.getCurrentDate());
				mPD_LCalculatorOrderSchema.setModifyTime(PubFun.getCurrentTime());
			}
			}
			//pd_lcalculatorfactor
			if(mPD_LCalculatorFactorSchema!=null){
			PD_LCalculatorFactorDB tPD_LCalculatorFactorDB=new PD_LCalculatorFactorDB();
			tPD_LCalculatorFactorDB.setCalculatorCode(mPD_LCalculatorFactorSchema.getCalculatorCode());
			//判断该累加器是否已经存在信息，如果没有默认为第一次，明细流水号的值为1；
			String SerialNo="";
			String checksql="select max(SerialNo) from pd_lcalculatorfactor where calculatorcode='?calculatorcode?'";
	 		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	 		sqlbv.sql(checksql);
	 		sqlbv.put("calculatorcode", mPD_LCalculatorFactorSchema.getCalculatorCode());
	 		String number = (new ExeSQL()).getOneValue(sqlbv);
	 		if(number==null || "".equals(number)){
	 			SerialNo="1";
	 		}else{
	 			SerialNo+=1;
	 		}
	 		tPD_LCalculatorFactorDB.setSerialNo(SerialNo);
	 		if(tPD_LCalculatorFactorDB.getInfo()){
	 			mPD_LCalculatorFactorSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
	 		}else{
	 			mPD_LCalculatorFactorSchema.setSerialNo(SerialNo);
	 			mPD_LCalculatorFactorSchema.setOperator(mGlobalInput.Operator);
	 			mPD_LCalculatorFactorSchema.setMakeDate(PubFun.getCurrentDate());
	 			mPD_LCalculatorFactorSchema.setMakeTime(PubFun.getCurrentTime());
	 			mPD_LCalculatorFactorSchema.setModifyDate(PubFun.getCurrentDate());
	 			mPD_LCalculatorFactorSchema.setModifyTime(PubFun.getCurrentTime());
	 		}
			}
			//PD_LCalculatorFeeCode
			if(mPD_LCalculatorFeeCodeSchema!=null){
	 		PD_LCalculatorFeeCodeDB tPD_LCalculatorFeeCodeDB=new PD_LCalculatorFeeCodeDB();
	 		tPD_LCalculatorFeeCodeDB.setCalculatorCode(mPD_LCalculatorFeeCodeSchema.getCalculatorCode());
	 		tPD_LCalculatorFeeCodeDB.setFeeType(mPD_LCalculatorFeeCodeSchema.getFeeType());
	 		if(tPD_LCalculatorFeeCodeDB.getInfo()){
	 			mPD_LCalculatorFeeCodeSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
	 		}else{
	 			mPD_LCalculatorFeeCodeSchema.setOperator(mGlobalInput.Operator);
	 			mPD_LCalculatorFeeCodeSchema.setMakeDate(PubFun.getCurrentDate());
 				mPD_LCalculatorFeeCodeSchema.setMakeTime(PubFun.getCurrentTime());
 				mPD_LCalculatorFeeCodeSchema.setModifyDate(PubFun.getCurrentDate());
 				mPD_LCalculatorFeeCodeSchema.setModifyTime(PubFun.getCurrentTime());
	 		}
			}
	 	//pd_ldplanfeerelaschema
			if(mPD_LDPlanFeeRelaSchema!=null){
	 		PD_LDPlanFeeRelaDB tPD_LDPlanFeeRelaDB=new PD_LDPlanFeeRelaDB();
	 		tPD_LDPlanFeeRelaDB.setPlanCode(mPD_LDPlanFeeRelaSchema.getPlanCode());
	 		tPD_LDPlanFeeRelaDB.setRiskCode(mPD_LDPlanFeeRelaSchema.getRiskCode());
	 		tPD_LDPlanFeeRelaDB.setDutyCode(mPD_LDPlanFeeRelaSchema.getDutyCode());
	 		tPD_LDPlanFeeRelaDB.setGetDutyCode(mPD_LDPlanFeeRelaSchema.getGetDutyCode());
	 		tPD_LDPlanFeeRelaDB.setFeeCode(mPD_LDPlanFeeRelaSchema.getFeeCode());
	 		if(tPD_LDPlanFeeRelaDB.getInfo()){
	 			PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaDB.getSchema();
	 			if(!"".equals(mPD_LDPlanFeeRelaSchema.getCountLimitAnnual())){
	 			tPD_LDPlanFeeRelaSchema.setCountLimitAnnual(mPD_LDPlanFeeRelaSchema.getCountLimitAnnual());
	 			}
	 			if(!"".equals(mPD_LDPlanFeeRelaSchema.getDaysLimitAnnual())){
				tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual(mPD_LDPlanFeeRelaSchema.getDaysLimitAnnual());
	 			}
	 			if(!"".equals(mPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime())){
				tPD_LDPlanFeeRelaSchema.setDaysLimitEveryTime(mPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime());
	 			}
				tPD_LDPlanFeeRelaSchema.setFeeType(mPD_LDPlanFeeRelaSchema.getFeeType());
				if(!"".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual())){
				tPD_LDPlanFeeRelaSchema.setMoneyLimitAnnual(mPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual());
				}
				if(!"".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay())){
				tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryDay(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay());
				}
				if(!"".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime())){
				tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryTime(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime());
				}
				tPD_LDPlanFeeRelaSchema.setPayMoneyEveryDay(mPD_LDPlanFeeRelaSchema.getPayMoneyEveryDay());
				mPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaSchema;
	 			//mPD_LDPlanFeeRelaSchema=null;
	 			if(mPD_LDPlanFeeRelaSchema!=null){
					map.put(mPD_LDPlanFeeRelaSchema, "UPDATE");
				}
				//buildError("check", "该数据已经存在，请重新操作。");
				//return false;
	 		}else{
	 			mPD_LDPlanFeeRelaSchema.setMakeDate(PubFun.getCurrentDate());
	 			mPD_LDPlanFeeRelaSchema.setMakeTime(PubFun.getCurrentTime());
	 			mPD_LDPlanFeeRelaSchema.setModifyDate(PubFun.getCurrentDate());
	 			mPD_LDPlanFeeRelaSchema.setModifyTime(PubFun.getCurrentTime());
	 			mPD_LDPlanFeeRelaSchema.setOperator(mGlobalInput.Operator);
	 			mPD_LDPlanFeeRelaSchema.setManageCom(mGlobalInput.ManageCom);
	 			if(mPD_LDPlanFeeRelaSchema!=null){
					map.put(mPD_LDPlanFeeRelaSchema, "INSERT");
				}
	 		}
			}
		}else if(mOperate != null && !"".equals(mOperate)
				&& "INSERT".equals(mOperate) && !"".equals(mCalculatorCode)){
			if(mPD_LCalculatorFactorSchema!=null){
			/*默认的是数据的关联只是需要在pd_lcalculatorfactor中增加一条数据即可，其他表的数据不需要增加*/
			//pd_lcalculatorfactor
			PD_LCalculatorFactorDB tPD_LCalculatorFactorDB=new PD_LCalculatorFactorDB();
			tPD_LCalculatorFactorDB.setCalculatorCode(mPD_LCalculatorFactorSchema.getCalculatorCode());
			//判断该累加器是否已经存在信息，如果没有默认为第一次，明细流水号的值为1；
			String SerialNo="";
			String checksql="select max(SerialNo) from pd_lcalculatorfactor where calculatorcode='?calculatorcode?'";
	 		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	 		sqlbv.sql(checksql);
	 		sqlbv.put("calculatorcode", mPD_LCalculatorFactorSchema.getCalculatorCode());
	 		String number = (new ExeSQL()).getOneValue(sqlbv);
	 		if(number==null || "".equals(number)){
	 			SerialNo="1";
	 		}else{
	 			SerialNo=String.valueOf(Integer.parseInt(number)+1);
	 		}
	 		tPD_LCalculatorFactorDB.setSerialNo(SerialNo);
	 		if(tPD_LCalculatorFactorDB.getInfo()){
	 			mPD_LCalculatorFactorSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
	 		}else{
	 			mPD_LCalculatorFactorSchema.setSerialNo(SerialNo);
	 			mPD_LCalculatorFactorSchema.setOperator(mGlobalInput.Operator);
	 			mPD_LCalculatorFactorSchema.setMakeDate(PubFun.getCurrentDate());
	 			mPD_LCalculatorFactorSchema.setMakeTime(PubFun.getCurrentTime());
	 			mPD_LCalculatorFactorSchema.setModifyDate(PubFun.getCurrentDate());
	 			mPD_LCalculatorFactorSchema.setModifyTime(PubFun.getCurrentTime());
	 		}
	 		//将其他数据表清空
	 		mPD_LDPlanFeeRelaSchema=null;
	 		mPD_LCalculatorInsuranceTimeSchema=null;
	 		mPD_LCalculatorMainSchema=null;
	 		mPD_LCalculatorNatureTimeSchema=null;
	 		mPD_LCalculatorOrderSchema=null;
	 		mPD_LCalculatorFeeCodeSchema=null;
		}
		}
		if (mOperate != null && !"".equals(mOperate)
				&& "UPDATE".equals(mOperate)) {
			//PD_LCalculatorMain
			if(mPD_LCalculatorMainSchema!=null){
			PD_LCalculatorMainDB tPD_LCalculatorMainDB=new PD_LCalculatorMainDB();
			tPD_LCalculatorMainDB.setCalculatorCode(mPD_LCalculatorMainSchema.getCalculatorCode());
			if(!tPD_LCalculatorMainDB.getInfo()){
				mPD_LCalculatorMainSchema=null;
				buildError("check", "该数据不存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorMainSchema tPD_LCalculatorMainSchema=tPD_LCalculatorMainDB.getSchema();
				tPD_LCalculatorMainSchema.setCalculatorName(mPD_LCalculatorMainSchema.getCalculatorName());
				tPD_LCalculatorMainSchema.setCtrlLevel(mPD_LCalculatorMainSchema.getCtrlLevel());
				tPD_LCalculatorMainSchema.setType(mPD_LCalculatorMainSchema.getType());
				tPD_LCalculatorMainSchema.setCtrlFactorType(mPD_LCalculatorMainSchema.getCtrlFactorType());
				tPD_LCalculatorMainSchema.setCtrlFactorValue(mPD_LCalculatorMainSchema.getCtrlFactorValue());
				tPD_LCalculatorMainSchema.setCtrlFactorUnit(mPD_LCalculatorMainSchema.getCtrlFactorUnit());
				tPD_LCalculatorMainSchema.setCalMode(mPD_LCalculatorMainSchema.getCalMode());
				tPD_LCalculatorMainSchema.setCalCode(mPD_LCalculatorMainSchema.getCalCode());
				tPD_LCalculatorMainSchema.setDefaultValue(mPD_LCalculatorMainSchema.getDefaultValue());
				mPD_LCalculatorMainSchema=tPD_LCalculatorMainSchema;
			}
			}
			//pd_lcalculatorinsurancetime
			if(mPD_LCalculatorInsuranceTimeSchema!=null){
			PD_LCalculatorInsuranceTimeDB tPD_LCalculatorInsuranceTimeDB=new PD_LCalculatorInsuranceTimeDB();
			tPD_LCalculatorInsuranceTimeDB.setCalculatorCode(mPD_LCalculatorInsuranceTimeSchema.getCalculatorCode());
			if(!tPD_LCalculatorInsuranceTimeDB.getInfo()){
				mPD_LCalculatorInsuranceTimeSchema=null;
				buildError("check", "该数据不存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorInsuranceTimeSchema tPD_LCalculatorInsuranceTimeSchema=tPD_LCalculatorInsuranceTimeDB.getSchema();
				tPD_LCalculatorInsuranceTimeSchema.setValidPeriod(mPD_LCalculatorInsuranceTimeSchema.getValidPeriod());
				tPD_LCalculatorInsuranceTimeSchema.setValidPeriodUnit(mPD_LCalculatorInsuranceTimeSchema.getValidPeriodUnit());
				mPD_LCalculatorInsuranceTimeSchema=tPD_LCalculatorInsuranceTimeSchema;
			}
			}
			//pd_lcalculatornaturetime
			if(mPD_LCalculatorNatureTimeSchema!=null){
			PD_LCalculatorNatureTimeDB tPD_LCalculatorNatureTimeDB=new PD_LCalculatorNatureTimeDB();
			tPD_LCalculatorNatureTimeDB.setCalculatorCode(mPD_LCalculatorNatureTimeSchema.getCalculatorCode());
			if(!tPD_LCalculatorNatureTimeDB.getInfo()){
				mPD_LCalculatorNatureTimeSchema=null;
				buildError("check", "该数据不存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorNatureTimeSchema tPD_LCalculatorNatureTimeSchema=tPD_LCalculatorNatureTimeDB.getSchema();
				tPD_LCalculatorNatureTimeSchema.setStartDate(mPD_LCalculatorNatureTimeSchema.getStartDate());
				tPD_LCalculatorNatureTimeSchema.setEndDate(mPD_LCalculatorNatureTimeSchema.getEndDate());
				mPD_LCalculatorNatureTimeSchema=tPD_LCalculatorNatureTimeSchema;
			}
			}
			//pd_lcalculatororder
			if(mPD_LCalculatorOrderSchema!=null){
			PD_LCalculatorOrderDB tPD_LCalculatorOrderDB=new PD_LCalculatorOrderDB();
			tPD_LCalculatorOrderDB.setCalculatorCode(mPD_LCalculatorOrderSchema.getCalculatorCode());
			if(!tPD_LCalculatorOrderDB.getInfo()){
				mPD_LCalculatorOrderSchema=null;
				buildError("check", "该数据不存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorOrderSchema tPD_LCalculatorOrderSchema=tPD_LCalculatorOrderDB.getSchema();
				tPD_LCalculatorOrderSchema.setCalCode(mPD_LCalculatorOrderSchema.getCalCode());
				tPD_LCalculatorOrderSchema.setCalOrder(mPD_LCalculatorOrderSchema.getCalOrder());
				mPD_LCalculatorOrderSchema=tPD_LCalculatorOrderSchema;
			}
			}
			//pd_ldplanfeerela
			if(mPD_LDPlanFeeRelaSchema!=null){
			PD_LDPlanFeeRelaDB tPD_LDPlanFeeRelaDB=new PD_LDPlanFeeRelaDB();
			tPD_LDPlanFeeRelaDB.setPlanCode(mPD_LDPlanFeeRelaSchema.getPlanCode());
			tPD_LDPlanFeeRelaDB.setRiskCode(mPD_LDPlanFeeRelaSchema.getRiskCode());
			tPD_LDPlanFeeRelaDB.setDutyCode(mPD_LDPlanFeeRelaSchema.getDutyCode());
			tPD_LDPlanFeeRelaDB.setGetDutyCode(mPD_LDPlanFeeRelaSchema.getGetDutyCode());
			tPD_LDPlanFeeRelaDB.setFeeCode(mPD_LDPlanFeeRelaSchema.getFeeCode());
			if(!tPD_LDPlanFeeRelaDB.getInfo()){
				mPD_LDPlanFeeRelaSchema=null;
				buildError("check", "该数据不存在，请重新操作。");
				return false;
			}else{
				PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaDB.getSchema();
				tPD_LDPlanFeeRelaSchema.setCountLimitAnnual(mPD_LDPlanFeeRelaSchema.getCountLimitAnnual());
				tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual(mPD_LDPlanFeeRelaSchema.getDaysLimitAnnual());
				tPD_LDPlanFeeRelaSchema.setDaysLimitEveryTime(mPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime());
				tPD_LDPlanFeeRelaSchema.setFeeType(mPD_LDPlanFeeRelaSchema.getFeeType());
				tPD_LDPlanFeeRelaSchema.setMoneyLimitAnnual(mPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual());
				tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryDay(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay());
				tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryTime(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime());
				tPD_LDPlanFeeRelaSchema.setPayMoneyEveryDay(mPD_LDPlanFeeRelaSchema.getPayMoneyEveryDay());
				mPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaSchema;
			}
		}
		}
		if (mOperate != null && !"".equals(mOperate)
				&& "DELETE".equals(mOperate)) {
			//PD_LCalculatorMain
			if(mPD_LCalculatorMainSchema!=null){
			PD_LCalculatorMainDB tPD_LCalculatorMainDB=new PD_LCalculatorMainDB();
			tPD_LCalculatorMainDB.setCalculatorCode(mPD_LCalculatorMainSchema.getCalculatorCode());
			if(!tPD_LCalculatorMainDB.getInfo()){
				mPD_LCalculatorMainSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorMainSchema tPD_LCalculatorMainSchema=tPD_LCalculatorMainDB.getSchema();
				mPD_LCalculatorMainSchema=tPD_LCalculatorMainSchema;
			}
			}
			//pd_lcalculatorinsurancetime
			if(mPD_LCalculatorInsuranceTimeSchema!=null){
			PD_LCalculatorInsuranceTimeDB tPD_LCalculatorInsuranceTimeDB=new PD_LCalculatorInsuranceTimeDB();
			tPD_LCalculatorInsuranceTimeDB.setCalculatorCode(mPD_LCalculatorInsuranceTimeSchema.getCalculatorCode());
			if(!tPD_LCalculatorInsuranceTimeDB.getInfo()){
				mPD_LCalculatorInsuranceTimeSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorInsuranceTimeSchema tPD_LCalculatorInsuranceTimeSchema=tPD_LCalculatorInsuranceTimeDB.getSchema();
				mPD_LCalculatorInsuranceTimeSchema=tPD_LCalculatorInsuranceTimeSchema;
			}
			}
			//pd_lcalculatornaturetime
			if(mPD_LCalculatorNatureTimeSchema!=null){
			PD_LCalculatorNatureTimeDB tPD_LCalculatorNatureTimeDB=new PD_LCalculatorNatureTimeDB();
			tPD_LCalculatorNatureTimeDB.setCalculatorCode(mPD_LCalculatorNatureTimeSchema.getCalculatorCode());
			if(!tPD_LCalculatorNatureTimeDB.getInfo()){
				mPD_LCalculatorNatureTimeSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorNatureTimeSchema tPD_LCalculatorNatureTimeSchema=tPD_LCalculatorNatureTimeDB.getSchema();
				mPD_LCalculatorNatureTimeSchema=tPD_LCalculatorNatureTimeSchema;
			}
			}
			//pd_lcalculatororder
			if(mPD_LCalculatorOrderSchema!=null){
			PD_LCalculatorOrderDB tPD_LCalculatorOrderDB=new PD_LCalculatorOrderDB();
			tPD_LCalculatorOrderDB.setCalculatorCode(mPD_LCalculatorOrderSchema.getCalculatorCode());
			if(!tPD_LCalculatorOrderDB.getInfo()){
				mPD_LCalculatorOrderSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorOrderSchema tPD_LCalculatorOrderSchema=tPD_LCalculatorOrderDB.getSchema();
				mPD_LCalculatorOrderSchema=tPD_LCalculatorOrderSchema;
			}
			}
			//pd_lcalculatorfactor
			if(mPD_LCalculatorFactorSchema!=null){
			PD_LCalculatorFactorDB tPD_LCalculatorFactorDB=new PD_LCalculatorFactorDB();
			tPD_LCalculatorFactorDB.setCalculatorCode(mPD_LCalculatorFactorSchema.getCalculatorCode());
			tPD_LCalculatorFactorDB.setSerialNo(mPD_LCalculatorFactorSchema.getSerialNo());
			if(!tPD_LCalculatorFactorDB.getInfo()){
				mPD_LCalculatorFactorSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorFactorSchema tPD_LCalculatorFactorSchema=tPD_LCalculatorFactorDB.getSchema();
				mPD_LCalculatorFactorSchema=tPD_LCalculatorFactorSchema;
			}
			}
	 		//pd_lcalculatorfeecode
			if(mPD_LCalculatorFeeCodeSchema!=null){
			PD_LCalculatorFeeCodeDB tPD_LCalculatorFeeCodeDB=new PD_LCalculatorFeeCodeDB();
			tPD_LCalculatorFeeCodeDB.setCalculatorCode(mPD_LCalculatorFeeCodeSchema.getCalculatorCode());
			tPD_LCalculatorFeeCodeDB.setFeeType(mPD_LCalculatorFeeCodeSchema.getFeeType());
			if(!tPD_LCalculatorFeeCodeDB.getInfo()){
				mPD_LCalculatorFeeCodeSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				PD_LCalculatorFeeCodeSchema tPD_LCalculatorFeeCodeSchema=tPD_LCalculatorFeeCodeDB.getSchema();
				mPD_LCalculatorFeeCodeSchema=tPD_LCalculatorFeeCodeSchema;
			}
			}
			//pd_ldplanfeerelaset  执行的是UPDATE
			//判断累加器的层级，如果是责任层级以上，我们需要判断factory表中是否存在多条改给付责任的累加器，如果大于2条，我们暂时不需要修改该表的数据，为了数据的统一性；
			if(mPD_LCalculatorMainSchema!=null && !"1".equals(mPD_LCalculatorMainSchema.getCtrlLevel())){
					String checksql="select count(1) from pd_lcalculatorfactor where calculatorcode='?calculatorcode?'";
			 		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
			 		sqlbv.sql(checksql);
			 		sqlbv.put("calculatorcode", mPD_LCalculatorFactorSchema.getCalculatorCode());
			 		String number = (new ExeSQL()).getOneValue(sqlbv);
			 		int count=Integer.parseInt(number);
					
			 		if(count>1){
			 			mPD_LDPlanFeeRelaSchema=null;
			 		}
			}
			if(mPD_LDPlanFeeRelaSchema!=null){
			PD_LDPlanFeeRelaDB tPD_LDPlanFeeRelaDB=new PD_LDPlanFeeRelaDB();
			tPD_LDPlanFeeRelaDB.setPlanCode(mPD_LDPlanFeeRelaSchema.getPlanCode());
			tPD_LDPlanFeeRelaDB.setDutyCode(mPD_LDPlanFeeRelaSchema.getDutyCode());
			tPD_LDPlanFeeRelaDB.setRiskCode(mPD_LDPlanFeeRelaSchema.getRiskCode());
			tPD_LDPlanFeeRelaDB.setGetDutyCode(mPD_LDPlanFeeRelaSchema.getGetDutyCode());
			tPD_LDPlanFeeRelaDB.setFeeCode(mPD_LDPlanFeeRelaSchema.getFeeCode());
			if(!tPD_LDPlanFeeRelaDB.getInfo()){
				mPD_LDPlanFeeRelaSchema=null;
				buildError("check", "该数据已经存在，请重新操作。");
				return false;
			}else{
				PD_LDPlanFeeRelaSchema tPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaDB.getSchema();
				//判断前端的值那个字段是N/A
				if(mPD_LDPlanFeeRelaSchema!=null && !"".equals(mPD_LDPlanFeeRelaSchema.getCountLimitAnnual()) && "N/A".equals(mPD_LDPlanFeeRelaSchema.getCountLimitAnnual())){
					tPD_LDPlanFeeRelaSchema.setCountLimitAnnual("");
				}else if(mPD_LDPlanFeeRelaSchema!=null && !"".equals(mPD_LDPlanFeeRelaSchema.getDaysLimitAnnual()) && "N/A".equals(mPD_LDPlanFeeRelaSchema.getDaysLimitAnnual())){
					tPD_LDPlanFeeRelaSchema.setDaysLimitAnnual("");
				}else if(mPD_LDPlanFeeRelaSchema!=null && !"".equals(mPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime()) && "N/A".equals(mPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime())){
					tPD_LDPlanFeeRelaSchema.setDaysLimitEveryTime("");
				}else if(mPD_LDPlanFeeRelaSchema!=null && !"".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual()) && "N/A".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual())){
					tPD_LDPlanFeeRelaSchema.setMoneyLimitAnnual("");
				}else if(mPD_LDPlanFeeRelaSchema!=null && !"".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay()) && "N/A".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay())){
					tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryDay("");
				}else if(mPD_LDPlanFeeRelaSchema!=null && !"".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime()) && "N/A".equals(mPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime())){
					tPD_LDPlanFeeRelaSchema.setMoneyLimitEveryTime("");
				}else if(mPD_LDPlanFeeRelaSchema!=null && !"".equals(mPD_LDPlanFeeRelaSchema.getPayMoneyEveryDay()) && "N/A".equals(mPD_LDPlanFeeRelaSchema.getPayMoneyEveryDay())){
					tPD_LDPlanFeeRelaSchema.setPayMoneyEveryDay("");
				}
				
				if(("".equals(tPD_LDPlanFeeRelaSchema.getCountLimitAnnual())|| tPD_LDPlanFeeRelaSchema.getCountLimitAnnual()==null) 
						&& ("".equals(tPD_LDPlanFeeRelaSchema.getDaysLimitAnnual())||tPD_LDPlanFeeRelaSchema.getDaysLimitAnnual()==null)
						&& ("".equals(tPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime()) ||tPD_LDPlanFeeRelaSchema.getDaysLimitEveryTime()==null)
						&&("".equals(tPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual()) ||tPD_LDPlanFeeRelaSchema.getMoneyLimitAnnual()==null)
						&&("".equals(tPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay()) ||tPD_LDPlanFeeRelaSchema.getMoneyLimitEveryDay()==null)
						&&("".equals(tPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime()) ||tPD_LDPlanFeeRelaSchema.getMoneyLimitEveryTime()==null)
						&&("".equals(tPD_LDPlanFeeRelaSchema.getPayMoneyEveryDay()) ||tPD_LDPlanFeeRelaSchema.getPayMoneyEveryDay()==null)){
					mPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaSchema;
					map.put(mPD_LDPlanFeeRelaSchema, "DELETE");
				}else{
				mPD_LDPlanFeeRelaSchema=tPD_LDPlanFeeRelaSchema;
				map.put(mPD_LDPlanFeeRelaSchema, "UPDATE");
				}
			}
			}
		}
		return true;
	}

	public CErrors getErrors() {
		return mErrors;
	}

	public VData getResult() {
		return this.mResult;
	}

	private boolean prepareOutputData() {
		try {
			mInputData.clear();
			mInputData.add(map);

		} catch (Exception ex) {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "LCExchangeRateBL";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
			this.mErrors.addOneError(tError);
			return false;
		}
		return true;
	}

	/**
	 * 错误构建
	 * 
	 * @param szFunc
	 *            String
	 * @param szErrMsg
	 *            String
	 */
	private void buildError(String szFunc, String szErrMsg) {
		CError cError = new CError();
		cError.moduleName = "PDLCalculatorBL";
		cError.functionName = szFunc;
		cError.errorMessage = szErrMsg;
		this.mErrors.addOneError(cError);
	}

	public static void main(String[] args) {
	}
}
