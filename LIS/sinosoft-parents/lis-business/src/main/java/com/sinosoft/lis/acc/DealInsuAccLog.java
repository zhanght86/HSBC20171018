package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:投连处理日志 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 */

public class DealInsuAccLog {
private static Logger logger = Logger.getLogger(DealInsuAccLog.class);
    public String CurrentDate=PubFun.getCurrentDate();
    public String CurrentTime=PubFun.getCurrentTime();

    /**Location-错误位置
     *01-计价处理
     *02-管理费处理
     *03-后续处理
     **/
    public DealInsuAccLog() {
    }

    /*生成日志*/
    public void createLOAccLog(String aDealDate,LCInsureAccSchema aLCInsureAccSchema,
                               String aLocation, String aMessage) {
        LOAccLogSchema tLOAccLogSchema = new LOAccLogSchema();
        Reflections tReflections = new Reflections();
        tReflections.transFields(tLOAccLogSchema, aLCInsureAccSchema);
        String tLimit = PubFun.getNoLimit(aLCInsureAccSchema.getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        tLOAccLogSchema.setSerialNo(SerialNo);
        tLOAccLogSchema.setState("1");
        tLOAccLogSchema.setDealDate(aDealDate);
        tLOAccLogSchema.setLocation(aLocation);
        tLOAccLogSchema.setReason(aMessage);
        tLOAccLogSchema.setMakeDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        tLOAccLogSchema.setModifyDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        DeleteLOAccLog(tLOAccLogSchema);
        insertLOAccLog(tLOAccLogSchema);
    }
    //生成日志2
    public void createLOAccLog(String aDealDate,String ManageCom,
                              String aLocation, String aMessage) {
       LOAccLogSchema tLOAccLogSchema = new LOAccLogSchema();

       String tLimit = PubFun.getNoLimit(ManageCom);
       String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
       tLOAccLogSchema.setSerialNo(SerialNo);
       tLOAccLogSchema.setPolNo("000000");
       tLOAccLogSchema.setInsuAccNo("000000");
       tLOAccLogSchema.setManageCom(ManageCom);
       tLOAccLogSchema.setState("1");
       tLOAccLogSchema.setDealDate(aDealDate);
       tLOAccLogSchema.setLocation(aLocation);
       tLOAccLogSchema.setReason(aMessage);
       tLOAccLogSchema.setMakeDate(CurrentDate);
       tLOAccLogSchema.setMakeTime(CurrentTime);
       tLOAccLogSchema.setModifyDate(CurrentDate);
       tLOAccLogSchema.setMakeTime(CurrentTime);
       DeleteLOAccLog(tLOAccLogSchema);
       insertLOAccLog(tLOAccLogSchema);
   }

   /*生成日志*/
   public void createLOAccLog(String aDealDate,LCPolSchema aLCPolSchema,
                              String aLocation, String aMessage) {
       LOAccLogSchema tLOAccLogSchema = new LOAccLogSchema();
       Reflections tReflections = new Reflections();
       tReflections.transFields(tLOAccLogSchema, aLCPolSchema);
       String tLimit = PubFun.getNoLimit(aLCPolSchema.getManageCom());
       String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
       tLOAccLogSchema.setSerialNo(SerialNo);
       tLOAccLogSchema.setState("1");
       tLOAccLogSchema.setDealDate(aDealDate);
       tLOAccLogSchema.setLocation(aLocation);
       tLOAccLogSchema.setReason(aMessage);
       tLOAccLogSchema.setMakeDate(CurrentDate);
       tLOAccLogSchema.setMakeTime(CurrentTime);
       tLOAccLogSchema.setModifyDate(CurrentDate);
       tLOAccLogSchema.setMakeTime(CurrentTime);
       DeleteLOAccLog(tLOAccLogSchema);
       insertLOAccLog(tLOAccLogSchema);
    }

    /*生成管理费日志*/
    public void createLOAccLog(String aDealDate,LCInsureAccClassSchema aLCInsureAccClassSchema,String aLocation,String aMessage)
    {
        LOAccLogSchema tLOAccLogSchema=new LOAccLogSchema();
        Reflections tReflections=new Reflections();
        tReflections.transFields(tLOAccLogSchema,aLCInsureAccClassSchema);
        String tLimit = PubFun.getNoLimit(aLCInsureAccClassSchema.getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        tLOAccLogSchema.setSerialNo(SerialNo);
        tLOAccLogSchema.setState("1");
        tLOAccLogSchema.setDealDate(aDealDate);
        tLOAccLogSchema.setLocation(aLocation);
        tLOAccLogSchema.setReason(aMessage);
        tLOAccLogSchema.setMakeDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        tLOAccLogSchema.setModifyDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        DeleteLOAccLog(tLOAccLogSchema);
        insertLOAccLog(tLOAccLogSchema);
    }

    /*生成计价日志*/
    public void createLOAccLog(String aDealDate,LCInsureAccTraceSchema aLCInsureAccTraceSchema,String aLocation,String aMessage)
    {
        LOAccLogSchema tLOAccLogSchema=new LOAccLogSchema();
        Reflections tReflections=new Reflections();
        tReflections.transFields(tLOAccLogSchema,aLCInsureAccTraceSchema);
        String tLimit = PubFun.getNoLimit(aLCInsureAccTraceSchema.getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        tLOAccLogSchema.setSerialNo(SerialNo);
        tLOAccLogSchema.setState("1");
        tLOAccLogSchema.setDealDate(aDealDate);
        tLOAccLogSchema.setLocation(aLocation);
        tLOAccLogSchema.setReason(aMessage);
        tLOAccLogSchema.setMakeDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        tLOAccLogSchema.setModifyDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        DeleteLOAccLog(tLOAccLogSchema);
        insertLOAccLog(tLOAccLogSchema);
    }

    /*生成后续处理日志*/
    public void createLOAccLog(String aDealDate,LOPolAfterDealSchema aLOPolAfterDealSchema,String aLocation,String aMessage)
    {
        LOAccLogSchema tLOAccLogSchema=new LOAccLogSchema();
        Reflections tReflections=new Reflections();
        tReflections.transFields(tLOAccLogSchema,aLOPolAfterDealSchema);
        String tLimit = PubFun.getNoLimit(aLOPolAfterDealSchema.getManageCom());
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        tLOAccLogSchema.setSerialNo(SerialNo);
        tLOAccLogSchema.setState("1");
        tLOAccLogSchema.setDealDate(aDealDate);
        tLOAccLogSchema.setLocation(aLocation);
        tLOAccLogSchema.setReason(aMessage);
        tLOAccLogSchema.setMakeDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        tLOAccLogSchema.setModifyDate(CurrentDate);
        tLOAccLogSchema.setMakeTime(CurrentTime);
        DeleteLOAccLog(tLOAccLogSchema);
        insertLOAccLog(tLOAccLogSchema);
    }
    /*更新LOAccLog表*/
    public boolean insertLOAccLog(LOAccLogSchema aLOAccLogSchema)
    {
        LOAccLogDB tLOAccLogDB=new LOAccLogDB();
        tLOAccLogDB.setSchema(aLOAccLogSchema);
        if(tLOAccLogDB.insert())
            return true;
        else
            return false;
    }
    public boolean DeleteLOAccLog(LOAccLogSchema aLOAccLogSchema)
    {
        LOAccLogDB tLOAccLogDB=new LOAccLogDB();
         LOAccLogSet tLOAccLogSet=new LOAccLogSet();
         SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String SQL="select * from LOAccLog where PolNo='"+"?PolNo?";
        String Codedata=aLOAccLogSchema.getPayPlanCode();
      //  logger.debug(PayPlanCode);
        if(Codedata==null||Codedata.equals("null")||Codedata.equals("")){
             SQL  =SQL  ;
        }else{
            SQL  =SQL  +"' and PayPlanCode='"+"?PayPlanCode?";
        }
        Codedata=aLOAccLogSchema.getInsuAccNo();
        if(Codedata==null||Codedata.equals("")||Codedata.equals("null")){
             SQL  =SQL  ;
        } else{
             SQL  =SQL +"' and InsuAccNo='"+"?InsuAccNo?";
        }
        Codedata=aLOAccLogSchema.getDealDate();
        if(Codedata==null||Codedata.equals("")||Codedata.equals("null")){
            SQL  =SQL  ;
        }else{
             SQL  =SQL +"' and DealDate='"+"?DealDate?";
        }
        Codedata= aLOAccLogSchema.getState();
        if( Codedata==null||Codedata.equals("")||Codedata.equals("null")){
              SQL  =SQL  ;
        }else{
             SQL  =SQL +"' and State='"+"?State?";
        }
        Codedata=aLOAccLogSchema.getLocation();
        if(Codedata==null||Codedata.equals("")||Codedata.equals("null")){
          SQL  =SQL  ;
        }else{
             SQL  =SQL  +"' and Location='"+"?Location?";
        }

        SQL  =SQL +"'";
        sqlbv.sql(SQL);
        sqlbv.put("PolNo", aLOAccLogSchema.getPolNo());
        sqlbv.put("PayPlanCode", aLOAccLogSchema.getPayPlanCode());
        sqlbv.put("InsuAccNo", aLOAccLogSchema.getInsuAccNo());
        sqlbv.put("DealDate", aLOAccLogSchema.getDealDate());
        sqlbv.put("State", aLOAccLogSchema.getState());
        sqlbv.put("Location", aLOAccLogSchema.getLocation());
       tLOAccLogSet= tLOAccLogDB.executeQuery(sqlbv);
       logger.debug(SQL);
       VData tVData = new VData();
    MMap mmap = new MMap();
    //准备公共提交数据
    mmap.put(tLOAccLogSet, "DELETE");

    if (mmap != null && mmap.keySet().size() > 0)
       tVData.add(mmap);
    PubSubmit tPubSubmit = new PubSubmit();
    if (!tPubSubmit.submitData(tVData, "")) {
      return false;
    }

           return true;

    }
}
