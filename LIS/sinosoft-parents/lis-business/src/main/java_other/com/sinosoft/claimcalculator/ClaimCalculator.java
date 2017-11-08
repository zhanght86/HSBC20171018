/***********************************************************************
 * Module:  ClaimCalculator.java
 * Author:  
 * Purpose: Defines the Class ClaimCalculator
 ***********************************************************************/

package com.sinosoft.claimcalculator;
import java.util.*;

import org.apache.log4j.Logger;


import com.sinosoft.claimcalculator.cache.CachedCalculatorTrace;
import com.sinosoft.claimcalculator.dimension.DimensionofDiseaseCode;
import com.sinosoft.claimcalculator.dimension.DimensionofFeeCode;
import com.sinosoft.claimcalculator.dimension.DimensionofInsuranceTime;
import com.sinosoft.claimcalculator.dimension.DimensionofNatureTime;
import com.sinosoft.claimcalculator.pubfun.CalculatorPubFun;
import com.sinosoft.lis.pubfun.Arith;
import com.sinosoft.lis.pubfun.Calculator;
import com.sinosoft.lis.pubfun.LDExch;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubCalculator;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LCalculatorMainSchema;
import com.sinosoft.lis.schema.LCalculatorNatureTimeSchema;
import com.sinosoft.lis.schema.LCalculatorInsuranceTimeSchema;
import com.sinosoft.lis.schema.LCalculatorFeeCodeSchema;
import com.sinosoft.lis.schema.LCalculatorDiseaseCodeSchema;
import com.sinosoft.lis.schema.LCalculatorOrderSchema;
import com.sinosoft.lis.schema.LCalculatorFactorSchema;
import com.sinosoft.lis.schema.LCalculatorTraceSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.acc.DealInsuAccPrice;
import com.sinosoft.lis.db.LCalculatorMainDB;
import com.sinosoft.lis.db.LCalculatorNatureTimeDB;
import com.sinosoft.lis.db.LCalculatorInsuranceTimeDB;
import com.sinosoft.lis.db.LCalculatorFeeCodeDB;
import com.sinosoft.lis.db.LCalculatorDiseaseCodeDB;
import com.sinosoft.lis.db.LCalculatorOrderDB;
import com.sinosoft.lis.db.LCalculatorFactorDB;
import com.sinosoft.lis.db.LCalculatorTraceDB;
import com.sinosoft.lis.vschema.LCalculatorMainSet;
import com.sinosoft.lis.vschema.LCalculatorNatureTimeSet;
import com.sinosoft.lis.vschema.LCalculatorInsuranceTimeSet;
import com.sinosoft.lis.vschema.LCalculatorFeeCodeSet;
import com.sinosoft.lis.vschema.LCalculatorDiseaseCodeSet;
import com.sinosoft.lis.vschema.LCalculatorOrderSet;
import com.sinosoft.lis.vschema.LCalculatorFactorSet;
import com.sinosoft.lis.vschema.LCalculatorTraceSet;
import com.sinosoft.utility.JdbcUrl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
//import com.sun.org.apache.xml.internal.utils.synthetic.reflection.Method;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.schema.LLRegisterSchema;
import com.sinosoft.lis.schema.LLClaimDutyFeeSchema;
import com.sinosoft.lis.tb.CachedRiskInfo;
import com.sinosoft.lis.db.LLClaimDetailDB;
import com.sinosoft.lis.db.LLRegisterDB;
import com.sinosoft.lis.vschema.LLClaimDetailSet;
import com.sinosoft.lis.vschema.LLRegisterSet;

/** @pdOid 73b72718-186f-4d1c-88c9-9ab795724f19 */
public class ClaimCalculator {
	private static Logger logger = Logger.getLogger(ClaimCalculator.class);
	protected LLClaimDetailSchema mLLClaimDetailSchema;
	protected LLRegisterSchema mLLRegisterSchema;
	protected double mApplyPay;
	protected LLClaimDutyFeeSchema mLLClaimDutyFeeSchema;
	private LCalculatorTraceSchema mLCalculatorTraceSchema;
	//累加器trace缓存
	private CachedCalculatorTrace mCCT = CachedCalculatorTrace.getInstance();
	
	/** @pdOid cabb1b69-5de8-4fdc-b97a-882639f497ed */
	protected String Code;
   /** @pdOid ed34bb2e-0c07-4964-945b-360b5c90ad13 */
	protected String Name;
   /** 1-保障责任（给付责任）
    * 2-责任
    * 3-连生被保人
    * 4-产品
    * 5-被保人（多被保人-家庭单的情况）
    * 6-保单
    * 7-个人（个险系统累加器只包含以上7中层级）
    * 8-计划
    * 9.团单
    * 
    * 
    * @pdOid 4dd4a6b8-8128-4924-b0e1-35e596b9f8ed */
   protected ClaimCalculatorLayerDef Layer = new ClaimCalculatorLayerDef();
   /** 1-赔付金额
    * 2-赔付天数
    * 3-赔付次数
    * 4-免赔额
    * 5-比例
    * 
    * 
    * @pdOid f835d8bd-f710-44ab-8dcf-b668840d8089 */
   protected ClaimCalculatorTypeDef Type = new ClaimCalculatorTypeDef();
   protected FactorTypeDef FactorType = new FactorTypeDef();
   /** @pdOid c14ef7d8-89ef-4e0d-bbd4-3adab8443cb0 */
   protected Double FactorValue;
   protected Double DefaultValue;
   /** 1-金额（元）
    * 2-天
    * 3-次
    * 4-小时
    * 
    * 
    * @pdOid 5357ec7e-3ac0-45e4-b770-5d1fbe16715b */
   protected ClaimCalculatorFactorUnit FactorUnit = new ClaimCalculatorFactorUnit();
   /** 算法：
    * 1-取描述默认值
    * 2-实例化时取默认计算要素计算
    * 3-实例化时取约定计算要素计算
    * 
    * 
    * @pdOid d4c2323a-36fc-4da0-9566-a661aa139bae */
   private String CalCode;
   /** @pdOid 52a00065-8fdd-4d72-b505-cf3f12ec2d70 */
   private List<ClaimCalculatorDimension> Dimension;
   /** @pdOid 3c8a6984-f158-42a8-b6bb-086a29508e61 */
   private List<ClaimCalculatorFactor> Factor;
   
   /** @pdRoleInfo migr=no name=ClaimCalculatorDimension assc=association2 coll=java.util.Collection impl=java.util.HashSet mult=1..* */
   private java.util.Collection claimCalculatorDimension;
   
   /** @pdGenerated default getter */
   public java.util.Collection getClaimCalculatorDimension() {
      if (claimCalculatorDimension == null)
         claimCalculatorDimension = new java.util.HashSet();
      return claimCalculatorDimension;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorClaimCalculatorDimension() {
      if (claimCalculatorDimension == null)
         claimCalculatorDimension = new java.util.HashSet();
      return claimCalculatorDimension.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newClaimCalculatorDimension */
   public void setClaimCalculatorDimension(java.util.Collection newClaimCalculatorDimension) {
      removeAllClaimCalculatorDimension();
      for (java.util.Iterator iter = newClaimCalculatorDimension.iterator(); iter.hasNext();)
         addClaimCalculatorDimension((ClaimCalculatorDimension)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newClaimCalculatorDimension */
   public void addClaimCalculatorDimension(ClaimCalculatorDimension newClaimCalculatorDimension) {
      if (newClaimCalculatorDimension == null)
         return;
      if (this.claimCalculatorDimension == null)
         this.claimCalculatorDimension = new java.util.HashSet();
      if (!this.claimCalculatorDimension.contains(newClaimCalculatorDimension))
         this.claimCalculatorDimension.add(newClaimCalculatorDimension);
   }
   
   /** @pdGenerated default remove
     * @param oldClaimCalculatorDimension */
   public void removeClaimCalculatorDimension(ClaimCalculatorDimension oldClaimCalculatorDimension) {
      if (oldClaimCalculatorDimension == null)
         return;
      if (this.claimCalculatorDimension != null)
         if (this.claimCalculatorDimension.contains(oldClaimCalculatorDimension))
            this.claimCalculatorDimension.remove(oldClaimCalculatorDimension);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllClaimCalculatorDimension() {
      if (claimCalculatorDimension != null)
         claimCalculatorDimension.clear();
   }
   
   /** @pdRoleInfo migr=no name=ClaimCalculatorFactor assc=association1 coll=java.util.Collection impl=java.util.HashSet mult=1..* */
   public java.util.Collection claimCalculatorFactor;

   public void setLLRegisterSchema(LLRegisterSchema tLLRegisterSchema){
	   this.mLLRegisterSchema = tLLRegisterSchema;
   }

   public void setLLClaimDetailSchema(LLClaimDetailSchema tLLClaimDetailSchema){
	   this.mLLClaimDetailSchema = tLLClaimDetailSchema;
   }

   public void setApplyPay(double tApplyPay){
	   this.mApplyPay = tApplyPay;
   }

   public void setLLClaimDutyFeeSchema(LLClaimDutyFeeSchema tLLClaimDutyFeeSchema){
	   this.mLLClaimDutyFeeSchema = tLLClaimDutyFeeSchema;
   }

   public LCalculatorTraceSchema getLCalculatorTraceSchema(){
	   return this.mLCalculatorTraceSchema;
   }
   
   /** 对每一个Dimension判断当前理赔是否需要计算累加器，返回true需要计算，返回false不需要计算。具体的业务逻辑在累加器维度对象里实现。
    * 
    * @pdOid cb3f5b9d-9a31-4b38-97ee-d4ea778619e1 */
   public boolean ifNeedCalculating() {
      // TODO: implement
	   boolean tCalFlag = true; //默认状态为“需要计算”，如果有一个维度不满足，则判定“不需要计算”。
	   try {
		   for (Object obj:claimCalculatorDimension){
			   logger.info(obj.getClass());
//			   logger.info(obj.getClass().getName());
//			   if ("com.sinosoft.claimcalculator.DimensionofFeeCode".equals(obj.getClass().getName())){
//				   String tFeeItemCode = obj.getClass().getMethod("getFeeCode",new Class[0]).invoke(obj,new Object[0]).toString();
//				   logger.info(tFeeItemCode);
//				   break;
//			   }

			   if (obj.getClass().getMethod("ifNeedCalculating",new Class[0]).invoke(obj,new Object[0]).equals(false)){
				   tCalFlag = false;
				   break;
			   }
		   }
	   }
	   catch(Exception e){
		   logger.info(e.getMessage());
		   return false;
	   }
	   
      return tCalFlag;
   }

   /** 根据累加器代码，将累加器属性从数据库中取出用来初始化本累加器对象 */
   public boolean initClaimCalculator()
   {
	   try{
		   if ((this.Code == null) || "".equals(this.Code)){
			   logger.info("传入累加器代码为空，累加器初始化失败！");
			   return false;
		   }
		   //累加器主表信息查询：
		   LCalculatorMainDB tLCalculatorMainDB = new LCalculatorMainDB();
		   tLCalculatorMainDB.setCalculatorCode(this.Code);
		   if (!tLCalculatorMainDB.getInfo()){
			   logger.info("累加器主表数据查询失败，累加器初始化失败！累加器代码=="+this.Code);
			   return false;
		   }else{
			   LCalculatorMainSchema tLCalculatorMainSchema = tLCalculatorMainDB.getSchema();
			   if (tLCalculatorMainSchema.getCalculatorCode()==null||"".equals(tLCalculatorMainSchema.getCalculatorCode())){
				   logger.info("累加器主表数据查询失败，累加器初始化失败！累加器代码=="+this.Code);
				   return false;
			   }
			   //查询数据成功，开始设置累加器对象
			   this.Name = tLCalculatorMainSchema.getCalculatorName();
			   this.Layer.setLayerCode(tLCalculatorMainSchema.getCtrlLevel());
			   this.Type.setTypeCode(tLCalculatorMainSchema.getType());
			   this.FactorType.setFactorTypeCode(tLCalculatorMainSchema.getCtrlFactorType());
			   this.FactorValue = tLCalculatorMainSchema.getCtrlFactorValue();
			   this.DefaultValue = tLCalculatorMainSchema.getDefaultValue();
			   this.FactorUnit.setFactorUnitCode(tLCalculatorMainSchema.getCtrlFactorUnit());
			   this.CalCode = tLCalculatorMainSchema.getCalCode();
			   
			   //累加器维度信息查询，一个正常的累加器应该含有一到多个维度：
			   ////自然时间维度
			   LCalculatorNatureTimeDB tLCalculatorNatureTimeDB = new LCalculatorNatureTimeDB();
			   tLCalculatorNatureTimeDB.setCalculatorCode(this.Code);
			   if(tLCalculatorNatureTimeDB.getInfo()){
				   LCalculatorNatureTimeSchema tLCalculatorNatureTimeSchema = tLCalculatorNatureTimeDB.getSchema();
				   if (tLCalculatorNatureTimeSchema.getCalculatorCode()!=null && !"".equals(tLCalculatorNatureTimeSchema.getCalculatorCode())){
					   //有自然时间维度
					   DimensionofNatureTime tDimensionofNatureTime = new DimensionofNatureTime();
					   tDimensionofNatureTime.setValidDateStart(tLCalculatorNatureTimeSchema.getStartDate());
					   tDimensionofNatureTime.setValidDateEnd(tLCalculatorNatureTimeSchema.getEndDate());
					   tDimensionofNatureTime.setLLClaimDetailSchema(this.mLLClaimDetailSchema);
					   tDimensionofNatureTime.setLLRegisterSchema(this.mLLRegisterSchema);
					   this.addClaimCalculatorDimension(tDimensionofNatureTime);
				   }
			   }
			   ////保险时间维度
			   LCalculatorInsuranceTimeDB tLCalculatorInsuranceTimeDB = new LCalculatorInsuranceTimeDB();
			   tLCalculatorInsuranceTimeDB.setCalculatorCode(this.Code);
			   if(tLCalculatorInsuranceTimeDB.getInfo()){
				   LCalculatorInsuranceTimeSchema tLCalculatorInsuranceTimeSchema = tLCalculatorInsuranceTimeDB.getSchema();
				   if (tLCalculatorInsuranceTimeSchema.getCalculatorCode()!=null && !"".equals(tLCalculatorInsuranceTimeSchema.getCalculatorCode())){
					 //有保险时间维度
					   DimensionofInsuranceTime tDimensionofInsuranceTime = new DimensionofInsuranceTime();
					   tDimensionofInsuranceTime.setValidPeriod(tLCalculatorInsuranceTimeSchema.getValidPeriod());
					   tDimensionofInsuranceTime.setValidPeriodUnit(tLCalculatorInsuranceTimeSchema.getValidPeriodUnit());
					   tDimensionofInsuranceTime.setLLClaimDetailSchema(this.mLLClaimDetailSchema);
					   tDimensionofInsuranceTime.setLLRegisterSchema(this.mLLRegisterSchema);
					   this.addClaimCalculatorDimension(tDimensionofInsuranceTime);
				   }
			   }
			   ////账单费用类型维度
			   LCalculatorFeeCodeDB tLCalculatorFeeCodeDB = new LCalculatorFeeCodeDB();
			   tLCalculatorFeeCodeDB.setCalculatorCode(this.Code);
			   if(tLCalculatorFeeCodeDB.getInfo()){
				   LCalculatorFeeCodeSchema tLCalculatorFeeCodeSchema = tLCalculatorFeeCodeDB.getSchema();
				   if (tLCalculatorFeeCodeSchema.getCalculatorCode()!=null && !"".equals(tLCalculatorFeeCodeSchema.getCalculatorCode())){
					 //有账单费用类型维度
					   DimensionofFeeCode tDimensionofFeeCode = new DimensionofFeeCode();
					   tDimensionofFeeCode.setFeeCode(tLCalculatorFeeCodeSchema.getFeeType());
					   tDimensionofFeeCode.setLLClaimDetailSchema(this.mLLClaimDetailSchema);
					   tDimensionofFeeCode.setLLRegisterSchema(this.mLLRegisterSchema);
					   tDimensionofFeeCode.setLLClaimDutyFeeSchema(this.mLLClaimDutyFeeSchema);
					   this.addClaimCalculatorDimension(tDimensionofFeeCode);
				   }
			   }
			   ////疾病维度
			   LCalculatorDiseaseCodeDB tLCalculatorDiseaseCodeDB = new LCalculatorDiseaseCodeDB();
			   tLCalculatorDiseaseCodeDB.setCalculatorCode(this.Code);
			   if(tLCalculatorDiseaseCodeDB.getInfo()){
				   LCalculatorDiseaseCodeSchema tLCalculatorDiseaseCodeSchema = tLCalculatorDiseaseCodeDB.getSchema();
				   if (tLCalculatorDiseaseCodeSchema.getCalculatorCode()!=null && !"".equals(tLCalculatorDiseaseCodeSchema.getCalculatorCode())){
					 //有疾病维度
					   DimensionofDiseaseCode tDimensionofDiseaseCode = new DimensionofDiseaseCode();
					   tDimensionofDiseaseCode.setDiseaseCode(tLCalculatorDiseaseCodeSchema.getDiagnosisCode());
					   tDimensionofDiseaseCode.setLLClaimDetailSchema(this.mLLClaimDetailSchema);
					   tDimensionofDiseaseCode.setLLRegisterSchema(this.mLLRegisterSchema);
					   this.addClaimCalculatorDimension(tDimensionofDiseaseCode);
				   }
			   }
			 //累加器维度信息查询，一个正常的累加器应该含有一到多个维度，少于1个维度的累加器数据有错误：
			   if (this.claimCalculatorDimension.size() < 1){
				   logger.info("累加器维度数据查询失败，累加器初始化失败！累加器代码=="+this.Code);
				   return false;
			   }
		   }
	   }
	   catch (Exception e) {
		   logger.info("累加器初始化失败！累加器代码=="+e.getMessage());
		   return false;
	   }
	   return true;
   }
   

   /** 得到经累加器控制后的理赔赔付金额。具体的业务逻辑在5个具体到类型的累加器对象中实现。
    * 
    * @pdOid 7e18f37a-adbe-4b86-be40-e3e4d3f94d8b */
   public double getClaimCalculationResult() {
      // TODO: implement
	  if (!initClaimCalculator()){
		  return 0;
	  }
	  if (!ifNeedCalculating()){
		  //return 0;
		  //如果不需要计算，应该返回原值而不是0
		  return this.mApplyPay;
	  }
      return getClaimCalculationResultDetail();
   }
   
   public double getClaimCalculationResultDetail() {
	   return 0;
    }
   /** 累加器轨迹保存处理。
    * tUsedLimit 本次计算使用掉的累加器限额
    * tBeforeUsedLimit 本次计算之前累加器余额
    * @pdOid 233d87dd-d89d-4c76-aaf3-0a6b0011b6b8 */
   public boolean dealCalculatorTrace(double tUsedLimit,double tBeforeUsedLimit) {
      // TODO: implement
	   this.mLCalculatorTraceSchema = new LCalculatorTraceSchema();
	   ExeSQL es = new ExeSQL();
	   SQLwithBindVariables sqlbva = new SQLwithBindVariables();
	   sqlbva.sql("SELECT SERIALNO_SEQ.NEXTVAL FROM DUAL");
	   mLCalculatorTraceSchema.setSerNo(PubFun.LCh(es.getOneValue(sqlbva),"0",20)); 
	   mLCalculatorTraceSchema.setGrpContNo(this.mLLClaimDetailSchema.getGrpContNo());
	   mLCalculatorTraceSchema.setContPlanCode("");/////////////////////////////////
	   mLCalculatorTraceSchema.setMainInsuredNo(this.mLLClaimDetailSchema.getCustomerNo());////////////////////////////////
	   mLCalculatorTraceSchema.setContNo(this.mLLClaimDetailSchema.getContNo());
	   mLCalculatorTraceSchema.setInsuredNo(this.mLLClaimDetailSchema.getCustomerNo());
	   mLCalculatorTraceSchema.setRiskCode(this.mLLClaimDetailSchema.getRiskCode());
	   mLCalculatorTraceSchema.setRelatedInsuredNo("");///////////////////////////
	   mLCalculatorTraceSchema.setDutyCode(this.mLLClaimDetailSchema.getDutyCode());
	   mLCalculatorTraceSchema.setGetDutyCode(this.mLLClaimDetailSchema.getGetDutyCode());
	   mLCalculatorTraceSchema.setGetDutyKind(this.mLLClaimDetailSchema.getGetDutyKind());
	   mLCalculatorTraceSchema.setCalculatorCode(this.Code);
	   mLCalculatorTraceSchema.setCtrlLevel(this.Layer.getLayerCode());
	   mLCalculatorTraceSchema.setClmNo(this.mLLClaimDetailSchema.getClmNo());
	   mLCalculatorTraceSchema.setUsedLimit(tUsedLimit);
	   mLCalculatorTraceSchema.setType(this.Type.getTypeCode());
	   mLCalculatorTraceSchema.setCtrlFactorType(this.FactorType.getFactorTypeCode());
	   mLCalculatorTraceSchema.setCtrlFactorUnit(this.FactorUnit.getFactorUnitCode());
	   mLCalculatorTraceSchema.setBeforeUsedLimit(tBeforeUsedLimit); 
	   mLCalculatorTraceSchema.setAfterUsedLimit(tBeforeUsedLimit - tUsedLimit); 
	   mLCalculatorTraceSchema.setApplyPay(this.mApplyPay);
	   if (this.mLLClaimDutyFeeSchema!=null){
		   mLCalculatorTraceSchema.setDutyFeeType(this.mLLClaimDutyFeeSchema.getDutyFeeType());
		   mLCalculatorTraceSchema.setDutyFeeCode(this.mLLClaimDutyFeeSchema.getDutyFeeCode());
		   mLCalculatorTraceSchema.setDutyFeeStaNo(this.mLLClaimDutyFeeSchema.getDutyFeeStaNo());
	   }
	   mLCalculatorTraceSchema.setAccidentDate(this.mLLRegisterSchema.getAccidentDate());
	   mLCalculatorTraceSchema.setOperator(this.mLLClaimDetailSchema.getOperator());
	   mLCalculatorTraceSchema.setMakeDate(PubFun.getCurrentDate());
	   mLCalculatorTraceSchema.setMakeTime(PubFun.getCurrentTime());
	   mLCalculatorTraceSchema.setModifyDate(mLCalculatorTraceSchema.getMakeDate());
	   mLCalculatorTraceSchema.setModifyTime(mLCalculatorTraceSchema.getMakeTime());
	   
//	   MMap eMap = new MMap();
//	   eMap.put(tLCalculatorTraceSchema, "INSERT");
//	   VData eVData = new VData();
//	   eVData.add(eMap);
//       if(!new PubSubmit().submitData(eVData, "")){
//			CError tError = new CError();
//           tError.moduleName = "HesitationPolicyServiceBusiness";
//           tError.functionName = "getPolicyStatus";
//           tError.errorMessage = "执行保单查询SQL语句失败";
//           this.mErrors.addOneError(tError);
//    	   logger.info("累加器轨迹保存失败！");
//           return false;
//	    }
	   //直接提交改为存Hash表
      return mCCT.addTrace2Cache(mLCalculatorTraceSchema);
   }

   /** 
    * 累加器轨迹保存处理。
    * */
   public boolean dealCalculatorTraceNew(LCalculatorTraceSchema tLCalculatorTraceSchema) {
      // TODO: implement
	   this.mLCalculatorTraceSchema = new LCalculatorTraceSchema();
	   ExeSQL es = new ExeSQL();
	   SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
	   sqlbvb.sql("SELECT SERIALNO_SEQ.NEXTVAL FROM DUAL");
	   mLCalculatorTraceSchema.setSerNo(PubFun.LCh(es.getOneValue(sqlbvb),"0",20)); 
	   mLCalculatorTraceSchema.setGrpContNo(this.mLLClaimDetailSchema.getGrpContNo());
	   mLCalculatorTraceSchema.setContPlanCode("");/////////////////////////////////
	   mLCalculatorTraceSchema.setMainInsuredNo(this.mLLClaimDetailSchema.getCustomerNo());////////////////////////////////
	   mLCalculatorTraceSchema.setContNo(this.mLLClaimDetailSchema.getContNo());
	   mLCalculatorTraceSchema.setInsuredNo(this.mLLClaimDetailSchema.getCustomerNo());
	   mLCalculatorTraceSchema.setRiskCode(this.mLLClaimDetailSchema.getRiskCode());
	   mLCalculatorTraceSchema.setRelatedInsuredNo("");///////////////////////////
	   mLCalculatorTraceSchema.setDutyCode(this.mLLClaimDetailSchema.getDutyCode());
	   mLCalculatorTraceSchema.setGetDutyCode(this.mLLClaimDetailSchema.getGetDutyCode());
	   mLCalculatorTraceSchema.setGetDutyKind(this.mLLClaimDetailSchema.getGetDutyKind());
	   mLCalculatorTraceSchema.setCalculatorCode(this.Code);
	   mLCalculatorTraceSchema.setCtrlLevel(this.Layer.getLayerCode());
	   mLCalculatorTraceSchema.setClmNo(this.mLLClaimDetailSchema.getClmNo());
	   
	   //以下5个字段：用传入的tLCalculatorTraceSchema-计算结果赋值当前schema
	   mLCalculatorTraceSchema.setApplyPay(tLCalculatorTraceSchema.getApplyPay());
	   mLCalculatorTraceSchema.setUsedLimit(tLCalculatorTraceSchema.getUsedLimit());
	   mLCalculatorTraceSchema.setSumApplyPay(tLCalculatorTraceSchema.getSumApplyPay());
	   mLCalculatorTraceSchema.setSumNotPay(tLCalculatorTraceSchema.getSumNotPay());
	   mLCalculatorTraceSchema.setSumPaid(tLCalculatorTraceSchema.getSumPaid());
	   
	   mLCalculatorTraceSchema.setType(this.Type.getTypeCode());
	   mLCalculatorTraceSchema.setCtrlFactorType(this.FactorType.getFactorTypeCode());
	   mLCalculatorTraceSchema.setCtrlFactorUnit(this.FactorUnit.getFactorUnitCode());
//	   mLCalculatorTraceSchema.setBeforeUsedLimit(tBeforeUsedLimit); 
//	   mLCalculatorTraceSchema.setAfterUsedLimit(tBeforeUsedLimit - tUsedLimit); 
	   if (this.mLLClaimDutyFeeSchema!=null){
		   mLCalculatorTraceSchema.setDutyFeeType(this.mLLClaimDutyFeeSchema.getDutyFeeType());
		   mLCalculatorTraceSchema.setDutyFeeCode(this.mLLClaimDutyFeeSchema.getDutyFeeCode());
		   mLCalculatorTraceSchema.setDutyFeeStaNo(this.mLLClaimDutyFeeSchema.getDutyFeeStaNo());
	   }
	   mLCalculatorTraceSchema.setAccidentDate(this.mLLRegisterSchema.getAccidentDate());
	   mLCalculatorTraceSchema.setOperator(this.mLLClaimDetailSchema.getOperator());
	   mLCalculatorTraceSchema.setMakeDate(PubFun.getCurrentDate());
	   mLCalculatorTraceSchema.setMakeTime(PubFun.getCurrentTime());
	   mLCalculatorTraceSchema.setModifyDate(mLCalculatorTraceSchema.getMakeDate());
	   mLCalculatorTraceSchema.setModifyTime(mLCalculatorTraceSchema.getMakeTime());
	   //累加器要素类型是限额/天金额/次金额的，本字段取值同UsedLimit；是次数/天数的，本字段取值UsedLimit*DeFaultValue
	   if ( mLCalculatorTraceSchema.getUsedLimit() > 0){
		   if ("1".equals(this.FactorType.getFactorTypeCode())||"4".equals(this.FactorType.getFactorTypeCode())||"5".equals(this.FactorType.getFactorTypeCode())){
			   mLCalculatorTraceSchema.setActualPay( mLCalculatorTraceSchema.getUsedLimit() );
		   }
		   if ("2".equals(this.FactorType.getFactorTypeCode())||"3".equals(this.FactorType.getFactorTypeCode())){
			   mLCalculatorTraceSchema.setActualPay( mLCalculatorTraceSchema.getUsedLimit() * this.DefaultValue );
		   }
	   }

	   //赋值记过提交至Hash表
      return mCCT.addTrace2Cache(mLCalculatorTraceSchema);
   }

   /*
    * 获得累加器trace的select查询条件
    */
   public String getTraceQryWherePart(SQLwithBindVariables sqlbv) {
	   int tLevel = this.Layer.getLayerCode();
	/** 累加器层级代码：
	 	* 	1.保障责任（给付责任）
	    * 	2.责任
	    * 	3.连生被保人
	    * 	4.产品
	    * 	5.被保人（多被保人-家庭单的情况）
	    * 	6.保单
	    * 	7.个人（个险系统累加器只包含以上7中层级）
	    * 	8.计划
	    * 	9.团单
	    * */
	   String tWherePart = " where calculatorcode='"+this.Code+"'";
	   logger.info("累加器层级:");
		  switch (tLevel){
		  case 1:
			  logger.info("1.保障责任（给付责任）");
			  tWherePart += " and contno='?contno?'"
			  	+" and insuredno='?insuredno?'"
			  	+" and dutycode='?dutycode?'"
			  	+" and getdutycode='?getdutycode?'";
			  sqlbv.put("contno", this.mLLClaimDetailSchema.getContNo());
			  sqlbv.put("insuredno", this.mLLClaimDetailSchema.getCustomerNo());
			  sqlbv.put("dutycode", this.mLLClaimDetailSchema.getDutyCode());
			  sqlbv.put("getdutycode", this.mLLClaimDetailSchema.getGetDutyCode());
			  break;
		  case 2:
			  logger.info("2.责任");
			  tWherePart += " and contno='?contno?'"
					  	+" and insuredno='?insuredno?'"
					  	+" and dutycode='?dutycode?'";
			  sqlbv.put("contno", this.mLLClaimDetailSchema.getContNo());
			  sqlbv.put("insuredno", this.mLLClaimDetailSchema.getCustomerNo());
			  sqlbv.put("dutycode", this.mLLClaimDetailSchema.getDutyCode());
			  break;
		  case 3:
			  logger.info("3.连生被保人");    //逻辑待确认，暂时同"4.产品"
			  tWherePart += " and contno='?contno?'"
			  	+" and insuredno='?insuredno?'"
			  	+" and riskcode='?riskcode?'";
			  sqlbv.put("contno", this.mLLClaimDetailSchema.getContNo());
			  sqlbv.put("insuredno", this.mLLClaimDetailSchema.getCustomerNo());
			  sqlbv.put("riskcode", this.mLLClaimDetailSchema.getRiskCode());
			  break;
		  case 4:
			  logger.info("4.产品");
			  tWherePart += " and contno='?contno?'"
					  	+" and insuredno='?insuredno?'"
					  	+" and riskcode='?riskcode?'";
					  sqlbv.put("contno", this.mLLClaimDetailSchema.getContNo());
					  sqlbv.put("insuredno", this.mLLClaimDetailSchema.getCustomerNo());
					  sqlbv.put("riskcode", this.mLLClaimDetailSchema.getRiskCode());
			  break;
		  case 5:
			  logger.info("5.被保人（多被保人-家庭单的情况）");
			  tWherePart += " and contno='?contno?'"
					  	+" and insuredno='?insuredno?'";
			  sqlbv.put("contno", this.mLLClaimDetailSchema.getContNo());
			  sqlbv.put("insuredno", this.mLLClaimDetailSchema.getCustomerNo());
			  break;
		  case 6:
			  logger.info("6.保单");
			  tWherePart += " and contno='?contno?'";
			  sqlbv.put("contno", this.mLLClaimDetailSchema.getContNo());
			  break;
		  case 7:
			  logger.info("7.个人（个险系统累加器只包含以上7中层级）");
			  tWherePart += " and MainInsuredNo='?MainInsuredNo?'";
			  sqlbv.put("MainInsuredNo", this.mLLClaimDetailSchema.getCustomerNo());
			  break;
		  case 8:
			  logger.info("8.计划");
			  tWherePart += " and GrpContNo='?GrpContNo?'"
			  	+" and contplancode=(select contplancode from lcinsured where contno='?contno?' and insuredno='?insuredno?')";
			  sqlbv.put("GrpContNo", this.mLLClaimDetailSchema.getGrpContNo());
			  sqlbv.put("contno", this.mLLClaimDetailSchema.getContNo());
			  sqlbv.put("insuredno", this.mLLClaimDetailSchema.getCustomerNo());
			  break;
		  case 9:
			  logger.info("9.团单");
			  tWherePart += " and GrpContNo='?GrpContNo?'";
			  sqlbv.put("GrpContNo", this.mLLClaimDetailSchema.getGrpContNo());
			  break;
		  default:
		  	logger.info("其他");
		  	break;
		  }
		  for (Object obj:claimCalculatorDimension){
			   if ("com.sinosoft.claimcalculator.DimensionofFeeCode".equals(obj.getClass().getName())){
					  //判断本累加器是否有”费用类型“维度，如果有，需要拼接FeeItemCode查询条件
				   try {
					   String tFeeItemCode = obj.getClass().getMethod("getFeeCode",new Class[0]).invoke(obj,new Object[0]).toString();
					   tWherePart += " and DutyFeeCode like concat('?tFeeItemCode?','%')";
					   sqlbv.put("tFeeItemCode", tFeeItemCode);
				   }
				   catch(Exception e){
					   logger.info(e.getMessage());
				   }
				   continue;
			   }
			   if ("com.sinosoft.claimcalculator.DimensionofInsuranceTime".equals(obj.getClass().getName())){
					  //判断本累加器是否有”保单保险年期时间“维度，如果有，需要拼接日期查询条件
				   try {
					   String tValidPeriod = obj.getClass().getMethod("getValidPeriod",new Class[0]).invoke(obj,new Object[0]).toString();
					   String tValidPeriodUnit = obj.getClass().getMethod("getValidPeriodUnit",new Class[0]).invoke(obj,new Object[0]).toString();
					   String tInsuranceTimeWherePart = getInsuranceTimeWherePart(
							   this.mLLClaimDetailSchema.getCValiDate(),  //保险起期
							   this.mLLRegisterSchema.getAccidentDate(),	//出险日期
							   tValidPeriod,	//累加器有效期
							   tValidPeriodUnit,sqlbv);	//累加器有效期时间单位
					   tWherePart += tInsuranceTimeWherePart;
				   }
				   catch(Exception e){
					   logger.info(e.getMessage());
				   }
				   continue;
			   }
		   }
		  
		  //天限额、次限额累加器不参与累加：
		  tWherePart += " and ctrlfactortype not in ('4','5')";

	   return tWherePart;
   }
   
   /*
    * 得到含”保单保险年期时间维度“累加器的trace查询附加条件
    * 目前本方法仅实现了“年限额/年免赔额”需要的逻辑，未来如果有n年限额/免赔额，n季限额/免赔额,n月限额/免赔额 等需求，还需要修改此处的逻辑
    * parameters：
    * tCValiDate 保险起期
    * tAccidentDate 出险日期
    * tValidPeriod 累加器有效期
    * tValidPeriodUnit 累加器有效期时间单位
    */
   public String getInsuranceTimeWherePart(String tCValiDate,String tAccidentDate,String tValidPeriod,String tValidPeriodUnit,SQLwithBindVariables sqlbv ) {
	   String tInsuranceTimeWherePart = "";
	 //赔案发生对应的保单生效对应日-"年限额/免赔额"时间段起期
	   String tDueStartDate = "" ;
	   //"年限额/免赔额"时间段止期
	   String tDueEndDate = "" ;
	   if (PubFun.calInterval(tCValiDate, tAccidentDate, "Y") > 0) {
		   while (true){
			   tDueStartDate = PubFun.calDate(tCValiDate, 1, "Y", "");
			   if (PubFun.calInterval(tDueStartDate, tAccidentDate, "Y") <= 0){
				   break;
			   }
		   }
	   } else {
		   tDueStartDate = tCValiDate;
	   }
	   tDueEndDate = PubFun.calDate(tDueStartDate, 1, "Y", "");
	   tInsuranceTimeWherePart = " and AccidentDate >= '?tDueStartDate?' and AccidentDate < '?tDueEndDate?'";
	   sqlbv.put("tDueStartDate", tDueStartDate);
	   sqlbv.put("tDueEndDate", tDueEndDate);
	   return tInsuranceTimeWherePart;
   }

   //获取本次理赔之前的累加器余额（金额/天数）
   public double getRemainedLimit(){
	   //累加器的原始值是this.tRemainedLimint，本方法要获取本保单累加器之前的轨迹，再从该原始值中扣除
	   
	   double tRemainedLimit = 0;
	   //获取本累加器的既往已申请赔付总金额
	   double tClaimSum = getClaimSum();
	   tRemainedLimit = this.FactorValue - tClaimSum;
	   return tRemainedLimit;
   }
   
   //获得本累加器的既往已申请赔付总金额（金额/天数）
   public double getClaimSum(){
	   //由于当前累加器的trace记录中，正在计算的claim的trace在Cache中尚未保存，其他claim的trace存在DB里，因此要分两部分获取Sum
	   double tClaimSum = 0;
	   double tSumFromDB = getSumLimitFromDB();
	   double tSumFromCache = getSumLimitFromCache();
	   tClaimSum = tSumFromDB + tSumFromCache;
	   return tClaimSum;
   }

   //从LCalculatorTrace表记录中汇总当前累加器trace的总金额/天数
   public double getSumLimitFromDB(){
	   double tTraceSum = 0;
	   SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	   String tSql = "select sum(usedlimit) from LCalculatorTrace";
	   String tWherePart = getTraceQryWherePart(sqlbv);
	   tSql += tWherePart;
	   sqlbv.sql(tSql);

	   logger.info(tSql);
	   if (tSql.indexOf(" and ")==-1){
		   tTraceSum = 0;
	   }else{
	     ExeSQL mExeSQL = new ExeSQL(); 
	     String tRetStr = mExeSQL.getOneValue(sqlbv);
	     if (tRetStr == null || "".equals(tRetStr)){
	   	  tRetStr = "0";
	     }
	     tTraceSum =  Double.valueOf(tRetStr);
	   }
	   return tTraceSum;
   }
   
 //从缓存中汇总当前累加器trace的总金额/天数
   public double getSumLimitFromCache(){
	   double tTraceSum = 0;
	   LCalculatorTraceSet tLCalculatorTraceSet = mCCT.findTrace(this.mLLClaimDetailSchema.getClmNo(),this.Code);
	   if (tLCalculatorTraceSet!=null && tLCalculatorTraceSet.size()>0){
		   for ( int i = 1 ; i <= tLCalculatorTraceSet.size() ; i++ ){
				if (tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("4")||tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("5")){
					//天限额、次限额累加器不参与累加：
					continue;
				}
				if (!tLCalculatorTraceSet.get(i).getContNo().equals(this.mLLClaimDetailSchema.getContNo())){
					//sl add 20150610：多保单时的控制：非本保单的trace此处不应参与累加：
					continue;
				}
			   tTraceSum += tLCalculatorTraceSet.get(i).getUsedLimit();
		   }
	   }
	   return tTraceSum;
   }


   //获取本次理赔之前的累加器次数
   public double getRemainedCount(){
	   //累加器的原始值是this.tRemainedCount，本方法要获取本保单累加器之前的轨迹，再从该原始值中扣除
	   
	   double tRemainedCount = 0;
	   //获取本累加器的既往已申请赔付总金额
	   double tClaimCount = getClaimCount();
	   tRemainedCount = this.FactorValue - tClaimCount;
	   return tRemainedCount;
   }
   
   //获得本累加器的既往已申请赔付总次数
   public double getClaimCount(){
	   //由于当前累加器的trace记录中，正在计算的claim的trace在Cache中尚未保存，其他claim的trace存在DB里，因此要分两部分获取Sum
	   double tClaimCount = 0;
	   double tCountFromDB = getSumCountFromDB();
	   double tCountFromCache = getSumCountFromCache();
	   tClaimCount = tCountFromDB + tCountFromCache;
	   return tClaimCount;
   }

   //从LCalculatorTrace表记录中汇总当前累加器trace的总笔数
   public double getSumCountFromDB(){
	   double tTraceCount = 0;
	   SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	   String tSql = "select count(1) from LCalculatorTrace";
	   String tWherePart = getTraceQryWherePart(sqlbv1);
	   tSql += tWherePart;
	   sqlbv1.sql(tSql);

	   logger.info(tSql);
	   if (tSql.indexOf(" and ")==-1){
		   tTraceCount = 0;
	   }else{
	     ExeSQL mExeSQL = new ExeSQL(); 
	     String tRetStr = mExeSQL.getOneValue(sqlbv1);
	     if (tRetStr == null || "".equals(tRetStr)){
	   	  tRetStr = "0";
	     }
	     tTraceCount =  Double.valueOf(tRetStr);
	   }
	   return tTraceCount;
   }
   
 //从缓存中汇总当前累加器trace的总笔数
   public double getSumCountFromCache(){
	   double tTracetTraceCount = 0;
	   LCalculatorTraceSet tLCalculatorTraceSet = mCCT.findTrace(this.mLLClaimDetailSchema.getClmNo(),this.Code);
	   if (tLCalculatorTraceSet!=null && tLCalculatorTraceSet.size()>0){
		   for ( int i = 1 ; i <= tLCalculatorTraceSet.size() ; i++ ){
			   tTracetTraceCount += 1;
		   }
	   }
	   return tTracetTraceCount;
   }

	/*
	 * 得到本账单的上一条相关的trace记录（如果有）
	 * 应该是从Cache里获取，而不用从库里查——如果查不到，相关数额即为0值
	 */
	public LCalculatorTraceSchema getLastRelatedTrace(){
		LCalculatorTraceSchema tLCalculatorTraceSchema = new LCalculatorTraceSchema();
		LCalculatorTraceSet tLCalculatorTraceSet = mCCT.findTraceByClmNoClone(this.mLLClaimDetailSchema.getClmNo());
		if (tLCalculatorTraceSet!=null && tLCalculatorTraceSet.size()>0){
			for ( int i = tLCalculatorTraceSet.size() ; i >= 1 ; i-- ){
				if (tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("4")||tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("5")){
					//天限额、次限额累加器不参与累加：
					continue;
				}
				if ( tLCalculatorTraceSet.get(i).getGetDutyCode().equals(this.mLLClaimDutyFeeSchema.getGetDutyCode())  //条件1getdutycode匹配
					&& tLCalculatorTraceSet.get(i).getCtrlFactorType().equals(this.FactorType.getFactorTypeCode())  //条件2累加器要素类型匹配
					&& tLCalculatorTraceSet.get(i).getDutyFeeType().equals(this.mLLClaimDutyFeeSchema.getDutyFeeType())
					&& tLCalculatorTraceSet.get(i).getDutyFeeCode().equals(this.mLLClaimDutyFeeSchema.getDutyFeeCode())
					&& tLCalculatorTraceSet.get(i).getDutyFeeStaNo().equals(this.mLLClaimDutyFeeSchema.getDutyFeeStaNo()))  //条件3账单匹配
				{
					tLCalculatorTraceSchema = tLCalculatorTraceSet.get(i);
					break;
				}
			}
		}
		return tLCalculatorTraceSchema;
	}

	/*
	 * 得到汇总当前getdutycode的全部赔付
	 */
	public double getAllRelatedSumPaid(){
		double tAllRelatedSumPaid = 0;
		tAllRelatedSumPaid = getClaimSum();
		return tAllRelatedSumPaid;
	}
	
	
	/*
	 * 得到本get上一个账单的最后一个限额类累加器的trace
	 */
	public LCalculatorTraceSchema getLastLimitTrace(){
		LCalculatorTraceSchema tLCalculatorTraceSchema = new LCalculatorTraceSchema();
		LCalculatorTraceSet tLCalculatorTraceSet = mCCT.findTraceByClmNoClone(this.mLLClaimDetailSchema.getClmNo());
		//先从cache里找trace，找不到再从DB里找——query from cache
		if (tLCalculatorTraceSet!=null && tLCalculatorTraceSet.size()>0){
			for ( int i = tLCalculatorTraceSet.size() ; i >= 1 ; i-- ){
				if (tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("4")||tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("5")){
					//天限额、次限额累加器不参与累加：
					continue;
				}
				if (tLCalculatorTraceSet.get(i).getGetDutyCode().equals(this.mLLClaimDutyFeeSchema.getGetDutyCode())   //条件1：本get
						   && tLCalculatorTraceSet.get(i).getDutyFeeCode().equals(this.mLLClaimDutyFeeSchema.getDutyFeeCode())   //条件2：本FeeCode
						   && !(tLCalculatorTraceSet.get(i).getDutyFeeType().equals(this.mLLClaimDutyFeeSchema.getDutyFeeType())
								   && tLCalculatorTraceSet.get(i).getDutyFeeCode().equals(this.mLLClaimDutyFeeSchema.getDutyFeeCode())
								   && tLCalculatorTraceSet.get(i).getDutyFeeStaNo().equals(this.mLLClaimDutyFeeSchema.getDutyFeeStaNo())) //条件3：上一个账单，即不是本账单
						   && tLCalculatorTraceSet.get(i).getType().equals("1") //条件4：累加器type="1"即限额类累加器
						   && tLCalculatorTraceSet.get(i).getCtrlFactorType().equals(this.FactorType.getFactorTypeCode()) )   //条件5：累加器要素类型匹配
				{
					   tLCalculatorTraceSchema = tLCalculatorTraceSet.get(i);
					   break;
				}
			   }
		}
		//先从cache里找trace，找不到再从DB里找——query from DB
		if (tLCalculatorTraceSchema.getCalculatorCode()==null || "".equals(tLCalculatorTraceSchema.getCalculatorCode())){
			SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
			   String tSql = "select sumnotpay,sumpaid,contno from LCalculatorTrace";
			   String tWherePart = getTraceQryWherePart(sqlbv2);//////////////////////////////////////////////////////////////////////////////////////
			   tSql += tWherePart;
			   //取最后一个账单的trace记录：
			   tSql += " order by serno desc";
			   sqlbv2.sql(tSql);

			   logger.info(tSql);

			   SSRS tSSRS = new SSRS();
			   ExeSQL tExeSQL = new ExeSQL();
			   tSSRS = tExeSQL.execSQL(sqlbv2);
			   if (tSSRS.getMaxRow() > 0) {
				   tLCalculatorTraceSchema.setSumNotPay(tSSRS.GetText(1, 1));
				   tLCalculatorTraceSchema.setSumPaid(tSSRS.GetText(1, 2));
				   tLCalculatorTraceSchema.setContNo(tSSRS.GetText(1, 3));
			   }
			   
		}
		return tLCalculatorTraceSchema;
	}
	
	/*
	 * 得到本get的上一条免赔额trace的赔付
	 */
	public LCalculatorTraceSchema getLastRelatedDeductibleTrace(){
		LCalculatorTraceSchema tLCalculatorTraceSchema = new LCalculatorTraceSchema();
		LCalculatorTraceSet tLCalculatorTraceSet = mCCT.findTraceByClmNoClone(this.mLLClaimDetailSchema.getClmNo());
		//先从cache里找trace，找不到再从DB里找——query from cache
		if (tLCalculatorTraceSet!=null && tLCalculatorTraceSet.size()>0){
			for ( int i = tLCalculatorTraceSet.size() ; i >= 1 ; i-- ){
				   if (tLCalculatorTraceSet.get(i).getGetDutyCode().equals(this.mLLClaimDutyFeeSchema.getGetDutyCode())   //条件1：本get
						   && tLCalculatorTraceSet.get(i).getType().equals("1")) //条件2：累加器type="2"即免赔额类累加器
				   {
					   tLCalculatorTraceSchema = tLCalculatorTraceSet.get(i);
					   break;
				   }
			   }
		}
		//先从cache里找trace，找不到再从DB里找——query from DB
		if (tLCalculatorTraceSchema.getCalculatorCode()==null || "".equals(tLCalculatorTraceSchema.getCalculatorCode())){
			SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
			   String tSql = "select usedlimit from LCalculatorTrace";
			   String tWherePart = getTraceQryWherePart(sqlbv3);
			   tSql += tWherePart;
			   //取最后一个账单的trace记录：
			   tSql += " order by serno desc";
			   sqlbv3.sql(tSql);

			   logger.info(tSql);

			   SSRS tSSRS = new SSRS();
			   ExeSQL tExeSQL = new ExeSQL();
			   tSSRS = tExeSQL.execSQL(sqlbv3);
			   if (tSSRS.getMaxRow() > 0) {
				   tLCalculatorTraceSchema.setUsedLimit(tSSRS.GetText(1, 1));
			   }
			   
		}
		return tLCalculatorTraceSchema;
	}
	/*
	 * //判断本账单不是第一次计算
	 */
	public boolean feeAlreadyInCache(){
		boolean feeAlreadyInCache = false;
		LCalculatorTraceSchema tLCalculatorTraceSchema = new LCalculatorTraceSchema();
		LCalculatorTraceSet tLCalculatorTraceSet = mCCT.findTraceByClmNoClone(this.mLLClaimDetailSchema.getClmNo());
		if (tLCalculatorTraceSet!=null && tLCalculatorTraceSet.size()>0){
			for ( int i = tLCalculatorTraceSet.size() ; i >= 1 ; i-- ){
					if (tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("4")||tLCalculatorTraceSet.get(i).getCtrlFactorType().equals("5")){
						//天限额、次限额累加器不参与累加：
						continue;
					}
				   if (tLCalculatorTraceSet.get(i).getGetDutyCode().equals(this.mLLClaimDutyFeeSchema.getGetDutyCode())
						   && tLCalculatorTraceSet.get(i).getCtrlFactorType().equals(this.FactorType.getFactorTypeCode())  //条件2累加器要素类型匹配
						   && tLCalculatorTraceSet.get(i).getDutyFeeType().equals(this.mLLClaimDutyFeeSchema.getDutyFeeType())
						   && tLCalculatorTraceSet.get(i).getDutyFeeCode().equals(this.mLLClaimDutyFeeSchema.getDutyFeeCode())
						   && tLCalculatorTraceSet.get(i).getDutyFeeStaNo().equals(this.mLLClaimDutyFeeSchema.getDutyFeeStaNo())){
					   feeAlreadyInCache = true;
					   break;
				   }
			   }
		}
		return feeAlreadyInCache;
	}
	
	/**
	 * 目的：为了实现“公式计算”类型的累加器计算，暂将LLClaimCalAutoBL中的executePay方法照搬过来，除了下述累加器入参以外的要素先注掉：
	 * 	protected LLClaimDetailSchema mLLClaimDetailSchema;
	 * 	protected LLRegisterSchema mLLRegisterSchema;
	 * 	protected double mApplyPay;
	 * 	protected LLClaimDutyFeeSchema mLLClaimDutyFeeSchema;
	 * @param pCalSum
	 *            计算金额
	 * @param pCalCode
	 *            计算公式
	 * @param pDutyFeeStaNo 账单明细编码
	 * @return double
	 */
   
	public double executePay(double pCalSum, String pCalCode,String pDutyFeeStaNo) {
		double rValue;
		if (null == pCalCode || "".equals(pCalCode) || pCalCode.length() == 0) {
			return 0;
		}
		logger.debug("\n=========================================进行公式运算=====开始=========================================\n");
		
		logger.debug("ContNo(合同):"+mLLClaimDetailSchema.getContNo()+",Clmno(赔案):"+mLLClaimDetailSchema.getClmNo()
				           +"RiskCode(险种):"+mLLClaimDetailSchema.getRiskCode()+",DutyCode(责任):"+mLLClaimDetailSchema.getDutyCode()
				           +",GetDutyCode(给付责任编码):"+mLLClaimDetailSchema.getGetDutyCode()+",GetDutyKind(给付责任类型):"
				           +mLLClaimDetailSchema.getGetDutyKind()+",pCalCode(算法):"+pCalCode+",pCalSum(给付金)"+pCalSum);

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No1.0 获取出险时点之前做过保全复效的批单号
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tContNo = mLLClaimDetailSchema.getContNo(); // 合同号
		String tPolNo = mLLClaimDetailSchema.getPolNo(); // 保单险种号
		String tNBPolNo = mLLClaimDetailSchema.getNBPolNo(); // 承保时的保单险种号

//		LPEdorItemSchema tLPEdorItemSchema = mLLClaimPubFunBL
//				.getLPEdorItemBefore(tContNo, tNBPolNo, this.mInsDate, "RE");// 出险时点前的保全批单号
//		String tPosEdorNoFront = "";
//		if (tLPEdorItemSchema != null) {
//			tPosEdorNoFront = StrTool.cTrim(tLPEdorItemSchema.getEdorNo());
//		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0 设置各种计算要素
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		// 增加基本要素,计算给付金
		TransferData tTransferData = new TransferData();
		
		// 出险原因
		tTransferData.setNameAndValue("AccidentReason", String.valueOf(mLLRegisterSchema.getAccidentReason()));

		// 出险日期
		tTransferData.setNameAndValue("AccidentDate", String.valueOf(this.mLLRegisterSchema.getAccidentDate()));

		// 终了红利
		tTransferData.setNameAndValue("FinalBonus", String.valueOf("0"));

		// [重新计算]投保人职业类别
//		tTransferData.setNameAndValue("OccupationType", String.valueOf(getOccupationType()));

		// 住院天数
//		tTransferData.setNameAndValue("DaysInHos", String.valueOf(getSumDayCount(mLLClaimDetailSchema)));
		//sl modified 20150605
		tTransferData.setNameAndValue("DaysInHos", String.valueOf(CalculatorPubFun.getSumDayCount(mLLClaimDutyFeeSchema)));

		// 保险结束日期
//		tTransferData.setNameAndValue("EndPolDate", String.valueOf(mLCPolSchema.getEndDate()));

		// 费用开始日期
		tTransferData.setNameAndValue("StartFeeDate", String
				.valueOf(mLLClaimDutyFeeSchema.getStartDate()));

		// 费用结束日期
		tTransferData.setNameAndValue("EndFeeDate", String
				.valueOf(mLLClaimDutyFeeSchema.getEndDate()));

		// 伤残比例
		tTransferData.setNameAndValue("DefoRate", String
				.valueOf(mLLClaimDutyFeeSchema.getRealRate()));

		// 每天床位费
		tTransferData.setNameAndValue("InHospdayFee", String
				.valueOf(mLLClaimDutyFeeSchema.getAdjSum()));


		// 治疗类型（B为住院治疗；A为门诊治疗）
		tTransferData.setNameAndValue("CureType", String
				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeType()));

		// [重新计算]保费（包括健康加费和职业加费及出险时点的保全补退费）,取自出险时间,需要考虑保全
//		tTransferData.setNameAndValue("TotalPrem", String
//				.valueOf(getTotalPrem()));

		// [重新计算]起领日期,取自出险时点,需要考虑保全
		/* 0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化 */
//		if (mLLClaimDetailSchema.getPosFlag().equals("0")
//				|| mLLClaimDetailSchema.getPosFlag().equals("1")) {
//			tTransferData.setNameAndValue("GetStartDate", String
//					.valueOf(mLLClaimPubFunBL.getGetStartDate(null,
//							mLCGetSchema)));
//		} else if (mLLClaimDetailSchema.getPosFlag().equals("2")) {
//			tTransferData
//					.setNameAndValue("GetStartDate", String
//							.valueOf(mLLClaimPubFunBL.getGetStartDate(
//									mLLClaimDetailSchema.getPosEdorNo(),
//									mLCGetSchema)));
//		}

		// 累计红利保额
		tTransferData.setNameAndValue("SumAmntBonus", String
				.valueOf(mLLClaimDetailSchema.getYearBonus()));

		// 出险时已保整月数
//		tTransferData.setNameAndValue("Rgtmonths", String.valueOf(PubFun
//				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "M")));
		
		// 给付责任编码
		tTransferData.setNameAndValue("GetDutyCode", String
				.valueOf(mLLClaimDetailSchema.getGetDutyCode()));

		// 给付责任类型
		tTransferData.setNameAndValue("GetDutyKind", String
				.valueOf(mLLClaimDetailSchema.getGetDutyKind()));

		// 保单号
		tTransferData.setNameAndValue("PolNo", String
				.valueOf(mLLClaimDetailSchema.getPolNo()));

		// 赔案号
		tTransferData.setNameAndValue("CaseNo", String.valueOf(this.mLLClaimDetailSchema.getClmNo()));

		// 出险人编号
		tTransferData.setNameAndValue("InsuredNo", String.valueOf(this.mLLRegisterSchema.getCustomerNo()));

//		// 出险时已保年期
//		tTransferData.setNameAndValue("RgtYears", String.valueOf(PubFun
//				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));
//
//		// 出险时已保天数
//		tTransferData.setNameAndValue("RgtDays", String.valueOf(PubFun
//				.calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "D")+1));
//
//		// 被保人性别
//		tTransferData.setNameAndValue("Sex", String.valueOf(mLCPolSchema
//				.getInsuredSex()));
//
//		// [重新计算]累计保费(包括健康加费和职业加费),取自出险时点,需要考虑保全
//		tTransferData
//				.setNameAndValue("SumPrem", String.valueOf(getPrem()));
//		logger.debug("计算的累计保费是" + tTransferData.getValueByName("SumPrem"));
//
//		// 保单生效日期
//		tTransferData.setNameAndValue("CValiDate", String.valueOf(mLCPolSchema
//				.getCValiDate()));
//
//		// [重新计算]保险年期,取自出险时点,需要考虑保全
//		tTransferData.setNameAndValue("Years", String.valueOf(getYears()));
//
//		// 已交费年期,取自出险时点,需要考虑保全
//		tTransferData
//				.setNameAndValue("AppAge", String.valueOf(PubFun.calInterval(
//						mLCPolSchema.getCValiDate(), getPaytoDate(), "Y")));
//
//		// 被保人投保年龄
//		tTransferData.setNameAndValue("InsuredAppAge", String
//				.valueOf(mLLClaimPubFunBL.getInsuranceAge(mLCPolSchema)));
//
//		// 被保人0岁保单生效对应日
//		tTransferData.setNameAndValue("InsuredvalidBirth", String
//				.valueOf(mLLClaimPubFunBL.getInsuredvalideBirth(mLCPolSchema)));

		// 计算给付金
		tTransferData.setNameAndValue("Je_gf", String.valueOf(pCalSum));

//		// [重新计算]份数,取自出险时点,需要考虑保全
//		tTransferData.setNameAndValue("Mult", String.valueOf(getMult()));
//
//		// 交费间隔
//		tTransferData.setNameAndValue("PayIntv", String.valueOf(mLCPolSchema
//				.getPayIntv()));
//
//		// 医疗费用序号
//		tTransferData.setNameAndValue("DutyFeeStaNo", String
//				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeStaNo()));
//
//		// 总保费[该要素将被删除],取自出险时点,需要考虑保全
//		tTransferData.setNameAndValue("Prem", String.valueOf(mLCPolSchema
//				.getPrem()));
//
//		// [重新计算]保单累计支付,取自出险时点,适用于责任下多个给付责任共享险种保额
//		tTransferData.setNameAndValue("PolPay", String.valueOf(getPolPay()));

		// 医疗费用编码
		tTransferData.setNameAndValue("DutyFeeCode", String
				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeCode()));

		
		// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额一致时(既lcget的standmoney一致)时
//		double tDAmnt = getAmnt();
//		tTransferData.setNameAndValue("Amnt", String.valueOf(tDAmnt));
//		
//		// [重新计算]基本保额,取自出险时点,需要考虑保全,适用于多个给付责任共享保额且各个给付的保额不一致时(既lcget的standmoney不一致)时
//		double tMaxAmnt = getMaxAmnt();
//		tTransferData.setNameAndValue("MaxAmnt", String.valueOf(tMaxAmnt));
//
//		// [重新计算]初始基本保额,取自出险时点,需要考虑保全
//		tTransferData.setNameAndValue("Oamnt", String.valueOf(getOamnt()));
//
//		// 交费年期
//		tTransferData.setNameAndValue("PayYears", String.valueOf(mLCDutySchema
//				.getPayYears()));
//
//		// 出险时年龄
//		tTransferData.setNameAndValue("GetAge", String.valueOf(PubFun
//				.calInterval(mLLClaimPubFunBL.getInsuredvalideBirth(mLCPolSchema), this.mInsDate,"Y")));
//
//		// 责任代码
//		tTransferData.setNameAndValue("DutyCode", String.valueOf(mLCDutySchema
//				.getDutyCode()));
//
//		// VPU
//		tTransferData.setNameAndValue("VPU", String.valueOf(mLLClaimPubFunBL
//				.getLMDuty(mLCDutySchema.getDutyCode()).getVPU()));
//
//		// 费用明细的流水号
//		tTransferData.setNameAndValue("DutyFeeStaNo", String
//				.valueOf(mLLClaimDutyFeeSchema.getDutyFeeStaNo()));
//
//		// 事故号
//		tTransferData.setNameAndValue("CaseRelaNo", String.valueOf(mAccNo));
//
//		// [重新计算]每期标准保费,,取自出险时点,需要考虑保全
//		double tDStandPrem = getPeriodPrem();
//		tTransferData.setNameAndValue("StandPrem", String.valueOf(tDStandPrem));
//
//		// 交费期限
//		tTransferData.setNameAndValue("PayEndDate", String
//				.valueOf(mLCDutySchema.getPayEndDate()));
//
//		// 交费终止期间
//		tTransferData.setNameAndValue("PayEndYear", String
//				.valueOf(mLCDutySchema.getPayEndYear()));

		// 客户号
		tTransferData.setNameAndValue("CustomerNo", String
				.valueOf(mLLClaimDetailSchema.getCustomerNo()));

//		// [重新计算]保单是否复效的标记
//		tTransferData.setNameAndValue("LRFlag", String.valueOf(mLLClaimPubFunBL
//				.getLRFlag(tPosEdorNoFront, tPolNo)));
//
//		// [重新计算]复效时间
//		String tLastRevDate = mLLClaimPubFunBL.getLastRevDate(tPosEdorNoFront,
//				tContNo, tPolNo, mLCPolSchema);
//
//		// 复效到出险时已保年期,取自出险时点,需要考虑保全
//		tTransferData.setNameAndValue("LRYears", String.valueOf(PubFun
//				.calInterval(tLastRevDate, this.mInsDate, "Y")));
//
//		// 复效到出险时已保天数,取自出险时点,需要考虑保全
//		tTransferData.setNameAndValue("LRDays", String.valueOf(PubFun
//				.calInterval(tLastRevDate, this.mInsDate, "D")+1));
//
//		/* 续保投保标志,0未续保,1已续保 */
//		tTransferData.setNameAndValue("AppFlag", String
//				.valueOf(mLLClaimPubFunBL.getAppFlag(tPosEdorNoFront,
//						mLCPolSchema)));

		// [重新计算] 交费次数 ,取自出险时点,需要考虑保全
//		double tDPayTimes = getPayTimes();
//		tTransferData.setNameAndValue("PayTimes", String.valueOf(tDPayTimes));

//		// 利差返还后增加的保额
//		tTransferData.setNameAndValue("RateAmnt", String.valueOf("0"));
//
//		// 保单号
//		tTransferData.setNameAndValue("PolNo", String.valueOf(mLCPolSchema
//				.getPolNo()));
//
//		// LCGet的开始时间
//		tTransferData.setNameAndValue("LCGetStartDate", String
//				.valueOf(mLCGetSchema.getGetStartDate()));
//
//		// LCGet的终止时间
//		tTransferData.setNameAndValue("LCGetEndDate", String
//				.valueOf(mLCGetSchema.getGetEndDate()));
//
//		// 合同号
//		tTransferData.setNameAndValue("ContNo", String.valueOf(mLCGetSchema
//				.getContNo()));

		/**
		 * 2008-12-11 zhangzheng
		 * 终了红利属于英式分红,MS采取的是美式分红,所以封住这段处理逻辑,以免报错
		 */
//		// 终了红利
//		tTransferData.setNameAndValue("EndBonus", String
//				.valueOf(mLLClaimDetailSchema.getEndBonus()));
		
		//2008-09-27  zhangzheng 社保赔付比例
//		if (!mLLClaimPubFunBL.getLMRiskSort(mLLClaimDetailSchema.getRiskCode(), "18")) //投保人豁免险有可能不存在其作为被保险人的保单,所以不查询赔付比例
//		{
//	        tTransferData.setNameAndValue("SocialInsuRate",String.
//	        		valueOf(getSocialInsuRate(mLLClaimDetailSchema.getRiskCode(),
//	        				mLLClaimPubFunBL.getLCInsured(tContNo, mLLClaimDetailSchema.getCustomerNo()).getSocialInsuFlag())));
//
//		}


		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ 其中投保费率差额＝投保保额对应费率－限额保额对应费率
		 * 退还保费＝已交保费×缴费次数×（冲减后金额－给付限额）/基本保额
		 * ?PayBackPrem?=?StandPrem?*?PayTimes?*(1-?Je_gf?/?Amnt?)
		 * 
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		double tDPayBackPrem = Arith.round(tDStandPrem * tDPayTimes
//				* (mLLClaimDetailSchema.getAmnt() - pCalSum) / tDAmnt, 4);
//		tTransferData.setNameAndValue("PayBackPrem", String
//				.valueOf(tDPayBackPrem));

		// 有效保额(对于账户型的就是账户价值)
		tTransferData.setNameAndValue("ValidAmnt", String
				.valueOf(mLLClaimDetailSchema.getAmnt()));
		logger.debug("计算的有效保额是"
				+ tTransferData.getValueByName("ValidAmnt"));

		// 已部分领取的账户价值)
//		tTransferData.setNameAndValue("SumAccGet", String.valueOf(String
//				.valueOf(getSumAccGet())));
//
//		// 未成年人限额
//		tTransferData.setNameAndValue("PupilAmnt", String.valueOf(mPupilAmnt));
//		
//		// 得到每个给付责任的累计赔付金额,适用于除费用补偿型险种之外的所有险种
//		tTransferData.setNameAndValue("GetDutySumPay", String.valueOf(getGetDutySumPay()));
//		
//		// 得到每个给付责任的累计赔付金额,适用于费用补偿型险种
//		tTransferData.setNameAndValue("CompensateDutySumPay", String.valueOf(getCompensateDutySumPay()));
//		
//		// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任共享险种保额
//		tTransferData.setNameAndValue("CurrentDutyPay", String.valueOf(getCurrentDutyPay()));
//		
//		// 共享保额的给付责任已经算出的理赔金,理算时给付责任的相互冲减,适用于责任下多个给付责任分類共享不同的保额
//		tTransferData.setNameAndValue("CurrentClassifiDutyPay", String.valueOf(getCurrentClassifiDutyPay()));
//		
//		// 给付责任的赔付次数
//		tTransferData.setNameAndValue("ClaimCount", String.valueOf(getGetDutyClaimCount()));
//		
//		//账单费用项目编码
//		tTransferData.setNameAndValue("DutyFeeCode", getDutyItemCode(mLLClaimDetailSchema.getClmNo(),pDutyFeeStaNo));
//		
//		//账单结束日期
//		tTransferData.setNameAndValue("FeereceiEndDate", getFeereceiEndDate(mLLClaimDetailSchema.getClmNo(),pDutyFeeStaNo));
		

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.0
		 * 针对于266,267,附加险采用主险的计算要素 －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
//		String tRiskCode = StrTool.cTrim(mLCPolSchema.getRiskCode());
//		String tSubRiskFlag = StrTool.cTrim(mLLClaimDetailSchema.getRiskType());
//		String tEffectOnMainRisk = StrTool.cTrim(mLLClaimDetailSchema
//				.getEffectOnMainRisk());
//		if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01")||tEffectOnMainRisk.equals("02"))) {
//			/**
//			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.1 取出附加险所在的主险赔付信息
//			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//			 */
//			LLClaimDetailSchema tRMLLClaimDetailSchema = getRMRiskInfo();
//			if (tRMLLClaimDetailSchema != null) {
//				String tRMContNo = tRMLLClaimDetailSchema.getContNo();// 附加险所在主险的合同号
//				String tRMPolNo = tRMLLClaimDetailSchema.getPolNo(); // 附加险所在主险的保单号
//
//				/**
//				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.2
//				 * 获取出险时点之前做过保全复效的批单号 保全信息
//				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//				 */
//				String tRMPosEdorNoFront = "";
//				LPEdorItemSchema tRMLPEdorItemSchema = mLLClaimPubFunBL
//						.getLPEdorItemBefore(tRMContNo, tRMPolNo,
//								this.mInsDate, "RE");// 出险时点前的保全批单号
//				if (tRMLPEdorItemSchema != null) {
//					tRMPosEdorNoFront = StrTool.cTrim(tRMLPEdorItemSchema
//							.getEdorNo());
//				}
//
//				/**
//				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.3 添加计算要素
//				 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//				 */
//
//				// 附险险所在主险的理赔给付金,为产品所加
//				tTransferData.setNameAndValue("RMAmnt", String
//						.valueOf(tRMLLClaimDetailSchema.getRealPay()));
//
//				// 附加险所在主险的有效保额,为产品所加,基本保额 + 累计年度红利保额（包括非整年度部分）＋终了红利
//				// 出险时点，附加该附加险的主险的有效保额（基本保额＋累计年度红利保额（包括非整年度部分）＋终了红利）
//				// （需考虑减保和其他保全变更的因素对保额以及红利保额的影响）（以上所提红利相关内容适用于主险为分红险的情况）
//
//				double t_Sum_Amnt = 0;
//				double t_Sum_YearBonus = 0;
//				double t_Sum_FinalBonus = 0;
//
//				t_Sum_Amnt = tRMLLClaimDetailSchema.getAmnt();
//				t_Sum_YearBonus = tRMLLClaimDetailSchema.getYearBonus();
//				t_Sum_FinalBonus = tRMLLClaimDetailSchema.getEndBonus();
//				t_Sum_Amnt = t_Sum_Amnt + t_Sum_YearBonus;
//
//				t_Sum_Amnt = Arith.round(t_Sum_Amnt, 2);
//
//				tTransferData.setNameAndValue("TRMAmnt", String
//						.valueOf(t_Sum_Amnt));
//
//				// 出险时点，主险是否复效过的标志，('0'没有, '1'有)
//				tTransferData.setNameAndValue("MLRFlag", String
//						.valueOf(mLLClaimPubFunBL.getLRFlag(tRMPosEdorNoFront,
//								tRMPolNo)));
//
//				// 复效时间
//				String tRMLastRevDate = mLLClaimPubFunBL.getLastRevDate(
//						tRMPosEdorNoFront, tRMContNo, tPolNo, mLCPolSchema);
//
//				// 复效到出险时已保天数,取自出险时点,需要考虑保全
//				tTransferData.setNameAndValue("MLRDays", String.valueOf(PubFun
//						.calInterval(tRMLastRevDate, this.mInsDate, "D")+1));
//
//				// 复效到出险时已保年期,取自出险时点,需要考虑保全
//				tTransferData.setNameAndValue("MLRYears", String.valueOf(PubFun
//						.calInterval(tRMLastRevDate, this.mInsDate, "Y")));
//
//				// 主险的保单号
//				tTransferData.setNameAndValue("MainPolNo", String
//						.valueOf(tRMPolNo));
//
//			}
//			/**
//			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No8.6
//			 * 附加险没有主险的赔付信息，则取出本身所在的险种信息
//			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//			 */
//			else {
//				String tMainPolNo = mLCPolSchema.getMainPolNo(); // 得到主险的保单险种号
//				LCPolSchema tRMLCPolSchema = mLLClaimPubFunBL
//						.getLCPol(tMainPolNo);
//				if (tRMLCPolSchema == null) {
//					this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
//					return 0;
//				} else {
//					String tRMContNo = tRMLCPolSchema.getContNo();
//					String tRMPolNo = tRMLCPolSchema.getPolNo();
//
//					/* 获取出险时点之前做过保全复效的批单号 */
//					String tRMPosEdorNoFront = "";
//					LPEdorItemSchema tRMLPEdorItemSchema = mLLClaimPubFunBL
//							.getLPEdorItemBefore(tRMContNo, tRMPolNo,
//									this.mInsDate, "RE");// 出险时点前的保全批单号
//					if (tRMLPEdorItemSchema != null) {
//						tRMPosEdorNoFront = StrTool.cTrim(tRMLPEdorItemSchema
//								.getEdorNo());
//					}
//
//					// 附险险的理赔给付金,为产品所加
//					tTransferData
//							.setNameAndValue("RMAmnt", String.valueOf("0"));
//
//					// 附险险的有效保额,为产品所加,基本保额
//					tTransferData.setNameAndValue("TRMAmnt", String
//							.valueOf(tRMLCPolSchema.getAmnt()));
//
//					// 附险险的理赔给付金,为产品所加
//					tTransferData.setNameAndValue("MLRFlag", String
//							.valueOf(mLLClaimPubFunBL.getLRFlag(
//									tRMPosEdorNoFront, tRMPolNo)));
//
//					// 复效时间
//					String tRMLastRevDate = mLLClaimPubFunBL.getLastRevDate(
//							tRMPosEdorNoFront, tRMContNo, tPolNo, mLCPolSchema);
//
//					// 复效到出险时已保年期,取自出险时点,需要考虑保全
//					tTransferData.setNameAndValue("MLRDays", String
//							.valueOf(PubFun.calInterval(tRMLastRevDate,
//									this.mInsDate, "D")+1));
//
//					// 复效到出险时已保天数,取自出险时点,需要考虑保全
//					tTransferData.setNameAndValue("MLRYears", String
//							.valueOf(PubFun.calInterval(tRMLastRevDate,
//									this.mInsDate, "Y")));
//
//					// 主险的保单号
//					tTransferData.setNameAndValue("MainPolNo", String
//							.valueOf(tRMLCPolSchema.getPolNo()));
//				}
//
//			}
//
//		}
		
		//如果是投连险
//		String sql = "select nvl(count(*),0) from lmrisktoacc c, lmriskinsuacc r "
//		  + " where r.insuaccno = c.insuaccno and r.acckind = '2' "
//			+ " and c.riskcode = '" + mLCPolSchema.getRiskCode() +	"' ";
//		ExeSQL tExeSQL = new ExeSQL();
//	    int tCount=Integer.parseInt(tExeSQL.getOneValue(sql));
//	    if(tCount>0)
//	    {
//	    	//是投连险
//	    	double tSumAccBala=0;
//	    	//判断是否进行了结算
//	    	sql = "select nvl(count(*),0) from LOPolAfterDeal where busytype='CL' and AccAlterNo='"+mLLClaimDetailSchema.getClmNo()
//				+"' and AccAlterType='4' order by state desc";
//	    	int tCount1=Integer.parseInt(tExeSQL.getOneValue(sql));
//	    	if(tCount1>0)
//		    {
//	    		sql="select currency,sum(money) from lcinsureacctrace where accalterno='"+mLLClaimDetailSchema.getClmNo()+"'"
//	    			+" and AccAlterType='4' and polno = '" + mLLClaimDetailSchema.getPolNo() +"' and payplancode in ( select payplancode from LMDutyPayRela where dutycode ='"+mLLClaimDetailSchema.getDutyCode()+"') group by currency";
//		    	SSRS tSSRS = new SSRS();
//				tSSRS = tExeSQL.execSQL(sql);
//				if (tSSRS.getMaxRow() > 0) {
//					LDExch tLDExch = new LDExch();
//					for(int i=1;i<=tSSRS.getMaxRow();i++)
//					{
//						if(tSSRS.GetText(i, 1).equals(mLLClaimDetailSchema.getCurrency()))
//							tSumAccBala = tSumAccBala+ Double.parseDouble(tSSRS.GetText(i, 2))*(-1);	
//						else
//							tSumAccBala = tSumAccBala+ tLDExch.toOtherCur(tSSRS.GetText(i, 1), mLLClaimDetailSchema.getCurrency(), PubFun.getCurrentDate(), Double.parseDouble(tSSRS.GetText(i, 2)))*(-1);	
//					}								
//				}
//		    }
//	    	else
//	    	{
//	    		//如果还没有计价则取估算账户价值
//	    		DealInsuAccPrice tDealInsuAccPrice= new DealInsuAccPrice(mGlobalInput);
//				tSumAccBala=tDealInsuAccPrice.calOnePrice(mLLClaimDetailSchema.getPolNo(), mLLClaimDetailSchema.getDutyCode(), PubFun.getCurrentDate(),mLLClaimDetailSchema.getCurrency());
//	    	}	 
//	    	logger.debug("账户价值: "+tSumAccBala);
//			
//	    	// 账户价值
//			tTransferData.setNameAndValue("InsureAccBalance", String
//					.valueOf(tSumAccBala));
//	    }

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0
		 * 将所有设置的参数加入到PubCalculator.addBasicFactor()中 加入到PubCalculator以后在没有用到PubCalculator，感觉这步是废的，注掉。
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Vector tVector = tTransferData.getValueNames();
//		PubCalculator tPubCalculator = new PubCalculator();
//
//		for (int i = 0; i < tVector.size(); i++) {
//			String tName = (String) tVector.get(i);
//			String tValue = (String) tTransferData.getValueByName(tName);
//			// logger.debug("PubCalculator.addBasicFactor--tName:" + tName
//			// + " tValue:" + tValue);
//			tPubCalculator.addBasicFactor(tName, tValue);
//		}

		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0
		 * 将所有设置的参数加入到Calculator.addBasicFactor()中
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		Calculator tCalculator = new Calculator();
		tCalculator.setCalCode(pCalCode);

		tVector = tTransferData.getValueNames();
		logger.debug("======================================================================================================");
		for (int i = 0; i < tVector.size(); i++) {
			String tName = (String) tVector.get(i);
			String tValue = (String) tTransferData.getValueByName(tName);
			logger.debug("第[" + i + "]个理算要素名称--" + tName + "--[" + tValue
					+ "]");
			tCalculator.addBasicFactor(tName, tValue);
		}
		logger.debug("======================================================================================================");
		//暂不加Bom了。
//		mBomList=mPrepareBOMClaimBL.dealData(tTransferData);
//		tCalculator.setBOMList(mBomList);
		/**
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No6.0
		 * 进行计算，Calculator.calculate()
		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		 */
		String tStr = "";
		
		logger.debug("----------------------------------------------------------------------------------\n");
		//logger.debug("计算公式==[" + tCalculator.getCalSQL() + "]");
		logger.debug("\n----------------------------------------------------------------------------------");
		tStr = tCalculator.calculate();
		if (tStr.trim().equals("")) {
			rValue = 0;
		} else {
			rValue = Arith.round(Double.parseDouble(tStr), 2);
		}

		if (tCalculator.mErrors.needDealError()) {
//			this.mErrors.addOneError("计算发生错误!原公式:" + pCalCode + "||,解析后的公式:"
//					+ tCalculator.getCalSQL());
			logger.error("计算发生错误!原公式:" + pCalCode + "||,解析后的公式:"
					+ tCalculator.getCalSQL());
		}
		logger.debug("\n=========================================进行公式运算=====结束=========================================\n");
		return rValue;
	}
	
	
   /** 经本次理赔后累加器的要素值（余额）处理。具体的业务逻辑在5个具体到类型的累加器对象中实现。
    * 
    * @pdOid 6d5a381b-031e-4624-b243-4420fd99876f */
   public boolean dealFactorValue() {
      // TODO: implement
      return false;
   }
   
   /** @pdOid d9975449-0d1f-45dd-a4d9-4542dc49072e */
   public String getCode() {
      // TODO: implement
      return null;
   }
   
   /** @param tCode
    * @pdOid 36ee96b2-1cea-433b-a056-37c91a600b4f */
   public void setCode(String tCode) {
      // TODO: implement
	   this.Code = tCode;
   }
   
   /** @pdOid 688bc6ae-29ef-42b3-ba5b-d1b3f52cee9b */
   public String getName() {
      // TODO: implement
      return null;
   }
   
   /** @param tName
    * @pdOid d138b9c0-f4bc-4685-a6fd-cc496d91938e */
   public void setName(String tName) {
      // TODO: implement
   }
   
   /** @pdOid 8170ffed-905d-4da7-af85-082e65741de6 */
   public ClaimCalculatorLayerDef getLayer() {
      // TODO: implement
      return null;
   }
   
   /** @param tLayer
    * @pdOid 3ad3517c-a3bb-4c27-90ab-75218ab00616 */
   public void setLayer(ClaimCalculatorLayerDef tLayer) {
      // TODO: implement
   }
   
   /** @pdOid 71ac4b7c-fc7d-4f32-88ca-1f2b15c987a3 */
   public ClaimCalculatorTypeDef getType() {
      // TODO: implement
      return null;
   }
   
   /** @param tType
    * @pdOid 0e9ca16f-9ece-4db7-a08d-5dd614c8040f */
   public void setType(ClaimCalculatorTypeDef tType) {
      // TODO: implement
   }
   
   /** @pdOid b989efec-fa9c-496c-b91e-d4457cc569df */
   public Float getFactorValue() {
      // TODO: implement
      return null;
   }
   
   /** @param tFactorValue
    * @pdOid 9d9d2dc8-69ca-4c6a-8f75-1d71056368c8 */
   public void setFactorValue(Float tFactorValue) {
      // TODO: implement
   }
   
   /** @pdOid 3d4c19ad-07ad-4cb4-92ca-87c2b1f3cd2b */
   public ClaimCalculatorFactorUnit getFactorUnit() {
      // TODO: implement
      return null;
   }
   
   /** @param tFactorUnit
    * @pdOid c77bac60-394f-4c4a-97e7-5768d8a1fe09 */
   public void setFactorUnit(ClaimCalculatorFactorUnit tFactorUnit) {
      // TODO: implement
   }
   
   /** @pdOid 68f74faf-a180-4818-b5b3-b604af8e2b87 */
   public String getCalCode() {
      return this.CalCode;
   }
   
   /** @param tCalCode
    * @pdOid 8b017fc6-d025-4a0a-a62e-07e415f04723 */
   public void setCalCode(String tCalCode) {
	   this.CalCode = tCalCode;
   }
   
   /** @pdOid d924370d-3b20-456e-8e2f-c5fb0ade2a3b */
   public List<ClaimCalculatorDimension> getDimension() {
      // TODO: implement
      return null;
   }
   
   /** @param tDimension
    * @pdOid 8aa4deb5-d48d-45d9-adb9-8035a21f21d3 */
   public void setDimension(List<ClaimCalculatorDimension> tDimension) {
      // TODO: implement
   }
   
   /** @pdOid 953d471c-c54d-41a1-b243-8fd708e1aaba */
   public List<ClaimCalculatorFactor> getFactor() {
      // TODO: implement
      return null;
   }
   
   /** @param tFactor
    * @pdOid a1b52cda-fb71-48de-bf85-72433a467a3d */
   public void setFactor(List<ClaimCalculatorFactor> tFactor) {
      // TODO: implement
   }
   
   
   /** @pdGenerated default getter */
   public java.util.Collection getClaimCalculatorFactor() {
      if (claimCalculatorFactor == null)
         claimCalculatorFactor = new java.util.HashSet();
      return claimCalculatorFactor;
   }
   
   /** @pdGenerated default iterator getter */
   public java.util.Iterator getIteratorClaimCalculatorFactor() {
      if (claimCalculatorFactor == null)
         claimCalculatorFactor = new java.util.HashSet();
      return claimCalculatorFactor.iterator();
   }
   
   /** @pdGenerated default setter
     * @param newClaimCalculatorFactor */
   public void setClaimCalculatorFactor(java.util.Collection newClaimCalculatorFactor) {
      removeAllClaimCalculatorFactor();
      for (java.util.Iterator iter = newClaimCalculatorFactor.iterator(); iter.hasNext();)
         addClaimCalculatorFactor((ClaimCalculatorFactor)iter.next());
   }
   
   /** @pdGenerated default add
     * @param newClaimCalculatorFactor */
   public void addClaimCalculatorFactor(ClaimCalculatorFactor newClaimCalculatorFactor) {
      if (newClaimCalculatorFactor == null)
         return;
      if (this.claimCalculatorFactor == null)
         this.claimCalculatorFactor = new java.util.HashSet();
      if (!this.claimCalculatorFactor.contains(newClaimCalculatorFactor))
         this.claimCalculatorFactor.add(newClaimCalculatorFactor);
   }
   
   /** @pdGenerated default remove
     * @param oldClaimCalculatorFactor */
   public void removeClaimCalculatorFactor(ClaimCalculatorFactor oldClaimCalculatorFactor) {
      if (oldClaimCalculatorFactor == null)
         return;
      if (this.claimCalculatorFactor != null)
         if (this.claimCalculatorFactor.contains(oldClaimCalculatorFactor))
            this.claimCalculatorFactor.remove(oldClaimCalculatorFactor);
   }
   
   /** @pdGenerated default removeAll */
   public void removeAllClaimCalculatorFactor() {
      if (claimCalculatorFactor != null)
         claimCalculatorFactor.clear();
   }

	public static void main(String args[]) {
		ClaimCalculator tCalculator = new ClaimCalculator();
		LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
		tLLRegisterSchema.setRgtNo("86130020100610000007");
		tLLRegisterSchema.setAccidentDate("2009-11-10");
		LLClaimDetailSchema tLLClaimDetailSchema = new LLClaimDetailSchema();
		tLLClaimDetailSchema.setClmNo("86130020100610000007");
		tLLClaimDetailSchema.setCValiDate("2009-07-01");
		tCalculator.setCode("A00001");
		tCalculator.setLLClaimDetailSchema(tLLClaimDetailSchema);
		tCalculator.setLLRegisterSchema(tLLRegisterSchema);
		logger.info("======"+tCalculator.getClaimCalculationResult());
	}

}