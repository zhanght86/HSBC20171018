package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import java.sql.Connection;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.vdb.LJSPayDBSet;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;



/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ReSendSpeTagRemove extends TaskThread 
{
private static Logger logger = Logger.getLogger(ReSendSpeTagRemove.class);



        public ReSendSpeTagRemove()
        {}
        public VData mResult = new VData();
        private GlobalInput mGlobalInput = new GlobalInput();
        private String tManageCom = "";
        
        private LJSPaySet update_LJSPaySet = new LJSPaySet();
        private String currentdate = PubFun.getCurrentDate();
        private String currenttime = PubFun.getCurrentTime();

        public CErrors mErrors = new CErrors();
        private String mDate=null;

        public boolean dealMain()
        {
          if (!getInputData())
          {
            return false;
          }
          if (!dealData())
          {
            return false;
          }
          return true;
        }

        public boolean dealData()
        {
            /*业务处理逻辑*/
            logger.debug("进入业务逻辑处理,开始清除特殊标记。!");

            String SQL= "";
            SQL=" select * from ljspay where payform='1' ";
            SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
            sqlbv1.sql(SQL);
            LJSPaySet tLJSPaySet = new LJSPaySet();
            LJSPayDB tLJSPayDB = new LJSPayDB();
            tLJSPaySet=tLJSPayDB.executeQuery(sqlbv1);
            
            if(tLJSPaySet.size()>0)
            {
                for(int m=1;m<=tLJSPaySet.size();m++)
                {
                     LJSPaySchema tLJSPaySchema = new LJSPaySchema();
                     tLJSPaySchema=tLJSPaySet.get(m);

                     tLJSPaySchema.setPayForm("0");
                     tLJSPaySchema.setOperator(this.mGlobalInput.Operator);
                     tLJSPaySchema.setModifyDate(this.currentdate);
                     tLJSPaySchema.setModifyTime(this.currenttime);

                     update_LJSPaySet.add(tLJSPaySchema);
                }

            }
            if(!updata(update_LJSPaySet))
            {
               logger.debug("清除特殊标记失败！！");
               this.buildError("dealdata", "清除特殊标记失败！！");
               return false;
            }
        	   
              
            
            
             return true;
        }

     private boolean getInputData()
      {
          try
          {
              this.tManageCom = "86";
              mGlobalInput.Operator="auto";
              mGlobalInput.ManageCom="86";
              mGlobalInput.ComCode="86";
        
              return true;
          }
          catch (Exception e)
          {
              return false;
          }
      }
        

     private boolean updata(LJSPaySet tljspayset)
     {
         boolean tReturn =true;
         logger.debug("start save.....");
         Connection conn;
         conn=null;
         conn=DBConnPool.getConnection();
         if(conn==null)
         {
             CError tError = new CError();
             tError.moduleName = "SpePolBankTagBLS";
             tError.functionName = "update";
             tError.errorMessage = "数据库连接失败!";
             this.mErrors .addOneError(tError) ;
             return false;
         }
         try
         {
             conn.setAutoCommit(false);
                  
             logger.debug("update LJSPay");
             LJSPayDBSet tLJSPayDBSet=new LJSPayDBSet(conn);
             LJSPaySet tLJSPaySet=new LJSPaySet();
             tLJSPaySet=tljspayset;
             if(tLJSPaySet.size()>0)
             {
               tLJSPayDBSet.set(tLJSPaySet);
               if(!tLJSPayDBSet.update())
               {
                 CError tCError =new CError();
                 tCError.moduleName="SpePolBankTagBLS";
                 tCError.functionName="Updata";
                 tCError.errorMessage="更新LJSPay表失败";
                 this.mErrors.addOneError(tCError);
                 conn.rollback();
                 conn.close();
               }
             }


         conn.commit();
         conn.close();
         }
         catch(Exception e)
         {
             // @@错误处理
             CError tError =new CError();
             tError.moduleName="SpePolBankTagBLS";
             tError.functionName="submitData";
             tError.errorMessage=e.toString();
             logger.debug("e.toString()"+e.toString());
             this.mErrors .addOneError(tError);
             tReturn=false;
             try
             {
                 conn.rollback() ;
                 conn.close();
             }
              catch(Exception ex){}
         }
         return tReturn;
     }

        /**
       *错误生成函数
       */
      private void buildError(String szFunc, String szErrMsg)
      {
         CError cError = new CError();
         cError.moduleName = "ReRateCalTask";
         cError.functionName = szFunc;
         cError.errorMessage = szErrMsg;
         this.mErrors.addOneError(cError);
      }

     

        public static void main(String[] args)
        {
        	ReSendSpeTagRemove tSpeTagRemove = new ReSendSpeTagRemove();
            tSpeTagRemove.dealMain();
//          String managecom = "861100";
//          String yearmonth = "200711";
//          GlobalInput tG = new GlobalInput();
//          tG.ManageCom="86";
//          tG.Operator ="001";
//          tG.ComCode="86";
//
//
//          VData tVData = new VData();
//
//          tVData.addElement(managecom);
//          tVData.addElement(yearmonth);
//          tVData.addElement(tG);
//
//          ReportAutoCreate tReportAutoCreate = new ReportAutoCreate();
//          tReportAutoCreate.submitData(tVData, "");

        }
        
}
