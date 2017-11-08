package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.lang.*;
import com.sinosoft.lis.vschema.*;

/**
 * Title: Web业务系统
 * Description: 万能险追加保费确认BL
 * Copyright: Copyright (c) 2007-06-01
 * Company: Sinosoft
 * @author QianLy
 * @version 1.0
 * @date 2007-10-30
 */
public class PEdorUPConfirmBL implements EdorConfirm
{
private static Logger logger = Logger.getLogger(PEdorUPConfirmBL.class);

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();
	/** 往后面传输数据的容器 */
	private VData mInputData;
	private MMap mMap = new MMap();
	/** 往界面传输数据的容器 */
	private VData mResult = new VData();
	/** 数据操作字符串 */
	private String mOperate;
	private Reflections mReflections = new Reflections();
	/** 业务数据 */
	private GlobalInput mGlobalInput = new GlobalInput();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

        private String strCurrentDate = PubFun.getCurrentDate();
        private String strCurrentTime = PubFun.getCurrentTime();

	public PEdorUPConfirmBL(){
	}

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     *
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括""和""
     * @return 布尔值（true--提交成功, false--提交失败）
     */
     public boolean submitData(VData cInputData, String cOperate){
         // 将操作数据拷贝到本类中
         this.mInputData = (VData) cInputData.clone();
         this.mOperate = cOperate;
         // 得到外部传入的数据,将数据备份到本类中
         if (!getInputData()){
             return false;
         }

         // 得到外部传入的数据,将数据备份到本类中
         if (!checkData()){
             return false;
         }
         // 进行业务处理
         if (!dealData()){
             return false;
         }

         //准备提交后台的数据
         if (!prepareOutputData()){
             return false;
         }

         return true;
      }

    /**
     * 数据输出方法，供外界获取数据处理结果
     *
     * @return 包含有数据查询结果字符串的VData对象
     */
     public VData getResult(){
         return mResult;
     }

     public CErrors getErrors(){
         return this.mErrors;
     }

    /**
     * 将外部传入的数据分解到本类的属性中
     *
     * @param: 无
     * @return: boolean
     */
     private boolean getInputData(){
         try{
             mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
             // 需要回退的保全项目
             mLPEdorItemSchema = (LPEdorItemSchema) mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
         }catch (Exception e){
             e.printStackTrace();
             return this.makeError("getInputData", "接收前台数据失败！");
         }
         if (mGlobalInput == null || mLPEdorItemSchema == null){
             return this.makeError("getInputData", "传入数据有误！");
          }
          return true;
      }

    /**
     * 校验传入的数据的合法性 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData(){
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setSchema(mLPEdorItemSchema);
        if (!tLPEdorItemDB.getInfo()){
            // @@错误处理
            return this.makeError("checkData", "无保全数据！");
        }
        mLPEdorItemSchema.setSchema(tLPEdorItemDB.getSchema());

        return true;
    }

    private boolean prepareOutputData(){
        try{
            mResult.clear();
            mResult.add(mMap);
        }catch (Exception ex){
            ex.printStackTrace();
            CError.buildErr(this, "在准备往后层处理所需要的数据时出错!");
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     *
     * @return 如果在处理过程中出错，则返回false,否则返回true
     */
     private boolean dealData(){
       try{
               String tSql = "";
               //LCCont表数据交换-->
               LPContDB tLPContDB = new LPContDB();
               tLPContDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPContDB.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPContDB.setContNo(mLPEdorItemSchema.getContNo());
               if (!tLPContDB.getInfo()){
                   return makeError("dealData", "查询保全保单记录信息失败！");
               }
               LPContSchema tLPContSchema = tLPContDB.getSchema();
               LCContSchema tLCContSchema = new LCContSchema();
               mReflections.transFields(tLCContSchema, tLPContSchema);
               tLCContSchema.setOperator(mGlobalInput.Operator);
               tLCContSchema.setModifyDate(strCurrentDate);
               tLCContSchema.setModifyTime(strCurrentTime);
               mMap.put(tLCContSchema, "DELETE&INSERT");
               LCContDB tLCContDB = new LCContDB();
               tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
               if (!tLCContDB.getInfo()){
                   return makeError("dealData", "查询保单记录信息失败！");
               }
               tLCContSchema = new LCContSchema();
               tLCContSchema = tLCContDB.getSchema();
               tLPContSchema = new LPContSchema();
               mReflections.transFields(tLPContSchema, tLCContSchema);
               tLPContSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPContSchema.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPContSchema.setOperator(mGlobalInput.Operator);
               tLPContSchema.setModifyDate(strCurrentDate);
               tLPContSchema.setModifyTime(strCurrentTime);
               mMap.put(tLPContSchema, "DELETE&INSERT");

               //LCPol表数据交换-->
               LPPolDB tLPPolDB = new LPPolDB();
               tLPPolDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPPolDB.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPPolDB.setContNo(mLPEdorItemSchema.getContNo());
               LPPolSet chgLPPolSet = tLPPolDB.query();
               if (tLPPolDB.mErrors.needDealError()){
                   return makeError("dealData", "查询保全保单险种记录信息失败！");
               }
               LCPolDB tLCPolDB = new LCPolDB();
               LCPolSchema tLCPolSchema;
               LPPolSchema tLPPolSchema;
               for (int k = 1; k <= chgLPPolSet.size(); k++){
                       tLPPolSchema = chgLPPolSet.get(k);
                       tLCPolDB.setPolNo(tLPPolSchema.getPolNo());
                       if (!tLCPolDB.getInfo()){
                           return makeError("dealData", "查询保单险种记录信息失败！");
                       }
                       tLCPolSchema = tLCPolDB.getSchema();
                       tLPPolSchema = new LPPolSchema();
                       mReflections.transFields(tLPPolSchema, tLCPolSchema);
                       tLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                       tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                       tLPPolSchema.setOperator(mGlobalInput.Operator);
                       tLPPolSchema.setModifyDate(strCurrentDate);
                       tLPPolSchema.setModifyTime(strCurrentTime);
                       mMap.put(tLPPolSchema, "DELETE&INSERT");
                       

                       tLCPolSchema.setSumPrem(tLCPolSchema.getSumPrem()
						+ mLPEdorItemSchema.getGetMoney());
                       tLCPolSchema.setOperator(mGlobalInput.Operator);
                       tLCPolSchema.setModifyDate(strCurrentDate);
                       tLCPolSchema.setModifyTime(strCurrentTime);
                       mMap.put(tLCPolSchema, "DELETE&INSERT");


                       //LCDuty表数据交换-->
                       LPDutyDB tLPDutyDB = new LPDutyDB();
                       tLPDutyDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                       tLPDutyDB.setEdorType(mLPEdorItemSchema.getEdorType());
                       tLPDutyDB.setContNo(mLPEdorItemSchema.getContNo());
                       tLPDutyDB.setPolNo(tLPPolSchema.getPolNo());
                       LPDutySet tempLPDutySet = new LPDutySet();
                       tempLPDutySet = tLPDutyDB.query();
                       if (tempLPDutySet == null || tempLPDutySet.size() <= 0){
                           return makeError("dealData", "查询保全责任记录信息失败！");
                       }
                       LCDutySet tLCDutySet = new LCDutySet();
                       for (int i = 1; i <= tempLPDutySet.size(); i++){
                               LCDutySchema tLCDutySchema = new LCDutySchema();
                               mReflections.transFields(tLCDutySchema, tempLPDutySet.get(i));
                               tLCDutySchema.setOperator(mGlobalInput.Operator);
                               tLCDutySchema.setModifyDate(strCurrentDate);
                               tLCDutySchema.setModifyTime(strCurrentTime);
                               tLCDutySet.add(tLCDutySchema);
                       }
                       mMap.put(tLCDutySet, "DELETE&INSERT");
                       LCDutyDB tLCDutyDB = new LCDutyDB();
                       tLCDutyDB.setContNo(mLPEdorItemSchema.getContNo());
                       tLCDutyDB.setPolNo(tLPPolSchema.getPolNo());
                       LCDutySet tempLCDutySet = tLCDutyDB.query();
                       if (tempLCDutySet == null || tempLCDutySet.size() <= 0){
                           return makeError("dealData", "查询责任记录信息失败！");
                       }
                       LPDutySet tLPDutySet = new LPDutySet();
                       for (int i = 1; i <= tempLCDutySet.size(); i++){
                               LPDutySchema tLPDutySchema = new LPDutySchema();
                               mReflections.transFields(tLPDutySchema, tempLCDutySet.get(i));
                               tLPDutySchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                               tLPDutySchema.setEdorType(mLPEdorItemSchema.getEdorType());
                               tLPDutySchema.setOperator(mGlobalInput.Operator);
                               tLPDutySchema.setModifyDate(strCurrentDate);
                               tLPDutySchema.setModifyTime(strCurrentTime);
                               tLPDutySet.add(tLPDutySchema);
                       }
                       mMap.put(tLPDutySet, "DELETE&INSERT");

                       //LCPrem表数据交换-->
                       LPPremDB tLPPremDB = new LPPremDB();
                       tLPPremDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
                       tLPPremDB.setEdorType(mLPEdorItemSchema.getEdorType());
                       tLPPremDB.setContNo(mLPEdorItemSchema.getContNo());
                       tLPPremDB.setPolNo(tLPPolSchema.getPolNo());
                       LPPremSet tempLPPremSet = new LPPremSet();
                       tempLPPremSet = tLPPremDB.query();
                       if (tempLPPremSet == null || tempLPPremSet.size() <= 0){
                           return makeError("dealData", "查询保全保费项记录信息失败！");
                       }
                       LCPremSet tLCPremSet = new LCPremSet();
                       for (int i = 1; i <= tempLPPremSet.size(); i++){
                               LCPremSchema tLCPremSchema = new LCPremSchema();
                               mReflections.transFields(tLCPremSchema, tempLPPremSet.get(i));
                               tLCPremSchema.setOperator(mGlobalInput.Operator);
                               tLCPremSchema.setModifyDate(strCurrentDate);
                               tLCPremSchema.setModifyTime(strCurrentTime);
                               tLCPremSet.add(tLCPremSchema);
                       }
                       mMap.put(tLCPremSet, "DELETE&INSERT");
                       LCPremDB tLCPremDB = new LCPremDB();
                       tLCPremDB.setContNo(mLPEdorItemSchema.getContNo());
                       tLCPremDB.setPolNo(tLPPolSchema.getPolNo());
                       LCPremSet tempLCPremSet = tLCPremDB.query();
                       if (tempLCPremSet == null || tempLCPremSet.size() <= 0){
                           return makeError("dealData", "查询保费项记录信息失败！");
                       }
                       LPPremSet tLPPremSet = new LPPremSet();
                       for (int i = 1; i <= tempLCPremSet.size(); i++){
                               LPPremSchema tLPPremSchema = new LPPremSchema();
                               this.mReflections.transFields(tLPPremSchema, tempLCPremSet.get(i));
                               tLPPremSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
                               tLPPremSchema.setEdorType(mLPEdorItemSchema.getEdorType());
                               tLPPremSchema.setOperator(mGlobalInput.Operator);
                               tLPPremSchema.setModifyDate(strCurrentDate);
                               tLPPremSchema.setModifyTime(strCurrentTime);
                               tLPPremSet.add(tLPPremSchema);
                       }
                       mMap.put(tLPPremSet, "DELETE&INSERT");
               }

               //处理帐户相关表
               LCInsureAccClassSchema aLCInsureAccClassSchema = new LCInsureAccClassSchema();
               LPInsureAccClassSchema aLPInsureAccClassSchema = new LPInsureAccClassSchema();
               LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
               LPInsureAccClassSet tLPInsureAccClassSet = new LPInsureAccClassSet();
               LPInsureAccClassSchema tLPInsureAccClassSchema = new LPInsureAccClassSchema();
               LCInsureAccClassSet aLCInsureAccClassSet = new LCInsureAccClassSet();
               LPInsureAccClassSet aLPInsureAccClassSet = new LPInsureAccClassSet();
               tLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPInsureAccClassSchema.setContNo(mLPEdorItemSchema.getContNo());
               LPInsureAccClassDB tLPInsureAccClassDB = new LPInsureAccClassDB();
               tLPInsureAccClassDB.setSchema(tLPInsureAccClassSchema);
               tLPInsureAccClassSet = tLPInsureAccClassDB.query();
               for (int j = 1; j <= tLPInsureAccClassSet.size(); j++){
                   aLPInsureAccClassSchema = tLPInsureAccClassSet.get(j);
                   LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
                   tLPInsureAccClassSchema = new LPInsureAccClassSchema();
                   LCInsureAccClassDB aLCInsureAccClassDB = new LCInsureAccClassDB();
                   aLCInsureAccClassDB.setContNo(aLPInsureAccClassSchema.getContNo());
                   aLCInsureAccClassDB.setPolNo(aLPInsureAccClassSchema.getPolNo());
                   aLCInsureAccClassDB.setInsuAccNo(aLPInsureAccClassSchema.getInsuAccNo());
                   aLCInsureAccClassDB.setPayPlanCode(aLPInsureAccClassSchema.getPayPlanCode());
                   aLCInsureAccClassDB.setOtherNo(aLPInsureAccClassSchema.getOtherNo());
                   aLCInsureAccClassDB.setAccAscription(aLPInsureAccClassSchema.getAccAscription());
                   LCInsureAccClassSet ttLCInsureAccClassSet = aLCInsureAccClassDB.query();

                   if (ttLCInsureAccClassSet != null && ttLCInsureAccClassSet.size() >= 1){
                       aLCInsureAccClassSchema = ttLCInsureAccClassSet.get(1);//前面设定主键了
                       mReflections.transFields(tLPInsureAccClassSchema, aLCInsureAccClassSchema);
                       tLPInsureAccClassSchema.setEdorNo(aLPInsureAccClassSchema.getEdorNo());
                       tLPInsureAccClassSchema.setEdorType(aLPInsureAccClassSchema.getEdorType());
                       aLPInsureAccClassSet.add(tLPInsureAccClassSchema);
                   }
                   mReflections.transFields(tLCInsureAccClassSchema, aLPInsureAccClassSchema);
                   tLCInsureAccClassSchema.setModifyDate(strCurrentDate);
                   tLCInsureAccClassSchema.setModifyTime(strCurrentTime);
                   aLCInsureAccClassSet.add(tLCInsureAccClassSchema);
                }
               mMap.put(aLCInsureAccClassSet, "DELETE&INSERT");
               mMap.put(aLPInsureAccClassSet, "DELETE&INSERT");

               LPInsureAccTraceDB tLPInsureAccTraceDB = new LPInsureAccTraceDB();
               LPInsureAccTraceSchema tLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
               LPInsureAccTraceSet tLPInsureAccTraceSet = new LPInsureAccTraceSet();
               tLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPInsureAccTraceDB.setContNo(mLPEdorItemSchema.getContNo());
               tLPInsureAccTraceSet = tLPInsureAccTraceDB.query();
               if(tLPInsureAccTraceSet != null && tLPInsureAccTraceSet.size() >= 1){
                   for (int i = 1; i <= tLPInsureAccTraceSet.size(); i++){
                       tLPInsureAccTraceSchema = tLPInsureAccTraceSet.get(i);
                       LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                       mReflections.transFields(tLCInsureAccTraceSchema,tLPInsureAccTraceSchema);
                       tLCInsureAccTraceSchema.setMakeDate(strCurrentDate);
                       tLCInsureAccTraceSchema.setMakeTime(strCurrentTime);
                       tLCInsureAccTraceSchema.setModifyDate(strCurrentDate);
                       tLCInsureAccTraceSchema.setModifyTime(strCurrentTime);
                       mMap.put(tLCInsureAccTraceSchema, "DELETE&INSERT");
                   }
               }

               LCInsureAccClassFeeSchema aLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
               LPInsureAccClassFeeSchema aLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
               LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
               LPInsureAccClassFeeSet tLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
               LPInsureAccClassFeeSchema tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
               LCInsureAccClassFeeSet aLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
               LPInsureAccClassFeeSet aLPInsureAccClassFeeSet = new LPInsureAccClassFeeSet();
               tLPInsureAccClassFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPInsureAccClassFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPInsureAccClassFeeSchema.setContNo(mLPEdorItemSchema.getContNo());
               LPInsureAccClassFeeDB tLPInsureAccClassFeeDB = new LPInsureAccClassFeeDB();
               tLPInsureAccClassFeeDB.setSchema(tLPInsureAccClassFeeSchema);
               tLPInsureAccClassFeeSet = tLPInsureAccClassFeeDB.query();
               for (int j = 1; j <= tLPInsureAccClassFeeSet.size(); j++){
                   aLPInsureAccClassFeeSchema = tLPInsureAccClassFeeSet.get(j);
                   LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
                   tLPInsureAccClassFeeSchema = new LPInsureAccClassFeeSchema();
                   LCInsureAccClassFeeDB aLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
                   aLCInsureAccClassFeeDB.setContNo(aLPInsureAccClassFeeSchema.getContNo());
                   aLCInsureAccClassFeeDB.setPolNo(aLPInsureAccClassFeeSchema.getPolNo());
                   aLCInsureAccClassFeeDB.setInsuAccNo(aLPInsureAccClassFeeSchema.getInsuAccNo());
                   aLCInsureAccClassFeeDB.setPayPlanCode(aLPInsureAccClassFeeSchema.getPayPlanCode());
                   aLCInsureAccClassFeeDB.setOtherNo(aLPInsureAccClassFeeSchema.getOtherNo());
                   aLCInsureAccClassFeeDB.setFeeCode(aLPInsureAccClassFeeSchema.getFeeCode());
                   aLCInsureAccClassFeeDB.setAccAscription(aLPInsureAccClassFeeSchema.getAccAscription());
                   LCInsureAccClassFeeSet ttLCInsureAccClassFeeSet = aLCInsureAccClassFeeDB.query();

                   if (ttLCInsureAccClassFeeSet != null && ttLCInsureAccClassFeeSet.size() >= 1){
                       aLCInsureAccClassFeeSchema = ttLCInsureAccClassFeeSet.get(1);//前面设定主键了
                       mReflections.transFields(tLPInsureAccClassFeeSchema, aLCInsureAccClassFeeSchema);
                       tLPInsureAccClassFeeSchema.setEdorNo(aLPInsureAccClassFeeSchema.getEdorNo());
                       tLPInsureAccClassFeeSchema.setEdorType(aLPInsureAccClassFeeSchema.getEdorType());
                       aLPInsureAccClassFeeSet.add(tLPInsureAccClassFeeSchema);
                   }

                   mReflections.transFields(tLCInsureAccClassFeeSchema, aLPInsureAccClassFeeSchema);
                   tLCInsureAccClassFeeSchema.setModifyDate(strCurrentDate);
                   tLCInsureAccClassFeeSchema.setModifyTime(strCurrentTime);
                   aLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
                }
               mMap.put(aLCInsureAccClassFeeSet, "DELETE&INSERT");
               mMap.put(aLPInsureAccClassFeeSet, "DELETE&INSERT");

               LPInsureAccFeeTraceDB tLPInsureAccFeeTraceDB = new LPInsureAccFeeTraceDB();
               LPInsureAccFeeTraceSchema tLPInsureAccFeeTraceSchema = new LPInsureAccFeeTraceSchema();
               LPInsureAccFeeTraceSet tLPInsureAccFeeTraceSet = new LPInsureAccFeeTraceSet();
               tLPInsureAccFeeTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPInsureAccFeeTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPInsureAccFeeTraceDB.setContNo(mLPEdorItemSchema.getContNo());
               tLPInsureAccFeeTraceSet = tLPInsureAccFeeTraceDB.query();
               if(tLPInsureAccFeeTraceSet != null && tLPInsureAccFeeTraceSet.size() >= 1){
                   for (int i = 1; i <= tLPInsureAccFeeTraceSet.size(); i++){
                       tLPInsureAccFeeTraceSchema = tLPInsureAccFeeTraceSet.get(i);
                       LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
                       mReflections.transFields(tLCInsureAccFeeTraceSchema,tLPInsureAccFeeTraceSchema);
                       tLCInsureAccFeeTraceSchema.setModifyDate(strCurrentDate);
                       tLCInsureAccFeeTraceSchema.setModifyTime(strCurrentTime);
                       mMap.put(tLCInsureAccFeeTraceSchema, "DELETE&INSERT");
                   }
               }

               LCInsureAccFeeSchema aLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
               LPInsureAccFeeSchema aLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
               LPInsureAccFeeSet tLPInsureAccFeeSet = new LPInsureAccFeeSet();
               LPInsureAccFeeSchema tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
               LCInsureAccFeeSet aLCInsureAccFeeSet = new LCInsureAccFeeSet();
               LPInsureAccFeeSet aLPInsureAccFeeSet = new LPInsureAccFeeSet();
               tLPInsureAccFeeSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
               tLPInsureAccFeeSchema.setEdorType(mLPEdorItemSchema.getEdorType());
               tLPInsureAccFeeSchema.setContNo(mLPEdorItemSchema.getContNo());
               LPInsureAccFeeDB tLPInsureAccFeeDB = new LPInsureAccFeeDB();
               tLPInsureAccFeeDB.setSchema(tLPInsureAccFeeSchema);
               tLPInsureAccFeeSet = tLPInsureAccFeeDB.query();
               for (int j = 1; j <= tLPInsureAccFeeSet.size(); j++){
                   aLPInsureAccFeeSchema = tLPInsureAccFeeSet.get(j);
                   LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
                   tLPInsureAccFeeSchema = new LPInsureAccFeeSchema();
                   LCInsureAccFeeDB aLCInsureAccFeeDB = new LCInsureAccFeeDB();
                   aLCInsureAccFeeDB.setContNo(aLPInsureAccFeeSchema.getContNo());
                   aLCInsureAccFeeDB.setPolNo(aLPInsureAccFeeSchema.getPolNo());
                   aLCInsureAccFeeDB.setInsuAccNo(aLPInsureAccFeeSchema.getInsuAccNo());
                   LCInsureAccFeeSet ttLCInsureAccFeeSet = aLCInsureAccFeeDB.query();

                   if (ttLCInsureAccFeeSet != null && ttLCInsureAccFeeSet.size() >= 1){
                       aLCInsureAccFeeSchema = ttLCInsureAccFeeSet.get(1);//前面设定主键了
                       mReflections.transFields(tLPInsureAccFeeSchema, aLCInsureAccFeeSchema);
                       tLPInsureAccFeeSchema.setEdorNo(aLPInsureAccFeeSchema.getEdorNo());
                       tLPInsureAccFeeSchema.setEdorType(aLPInsureAccFeeSchema.getEdorType());
                       aLPInsureAccFeeSet.add(tLPInsureAccFeeSchema);
                   }

                   mReflections.transFields(tLCInsureAccFeeSchema, aLPInsureAccFeeSchema);
                   tLCInsureAccFeeSchema.setModifyDate(strCurrentDate);
                   tLCInsureAccFeeSchema.setModifyTime(strCurrentTime);
                   aLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
                }
               mMap.put(aLCInsureAccFeeSet, "DELETE&INSERT");
               mMap.put(aLPInsureAccFeeSet, "DELETE&INSERT");

               mResult.clear();
               mResult.add(mMap);
           }catch (Exception e){
               return makeError("dealData", "数据处理错误！"+ e.getMessage());
           }
           return true;
       }

    /**
     * 创建一个错误
     * @param vFuncName 发生错误的函数名
     * @param vErrMsg 错误信息
     * @return 布尔值（false--永远返回此值）
     */
     private boolean makeError(String vFuncName, String vErrMsg){
         CError tError = new CError();
         tError.moduleName = "PEdorUPConfirmBL";
         tError.functionName = vFuncName;
         tError.errorMessage = vErrMsg;
         this.mErrors.addOneError(tError);
         return false;
     }
}
