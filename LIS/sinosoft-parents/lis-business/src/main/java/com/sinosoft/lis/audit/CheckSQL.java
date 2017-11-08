package com.sinosoft.lis.audit;
import org.apache.log4j.Logger;
import java.util.Vector;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.service.CovBase;
import com.sinosoft.service.ServiceA;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>
 * Title: Web业务系统
 * </p>
 * <p>
 * Description: 进行数据规则校验（数据库连接有写权限）
 * </p>
 */
public class CheckSQL extends CovBase {
private static Logger logger = Logger.getLogger(CheckSQL.class);
/** 往后面传输数据的容器 */
private VData mInputData;
private VData mInputDataNew;
/** 全局数据 */
private GlobalInput mGlobalInput = new GlobalInput();
private TransferData mTransferData = new TransferData();
	
	private String mCalCode="";
	private String mCalSql="";
	public void  check()
	{
            SSRS mSSRS = new SSRS();
            SSRS rSSRS = new SSRS();
            ExeSQL tExeSQL = new ExeSQL();

	    String tCalCode = "";
	    String tCalSql = "";
            String str ="select * from StatSqlConfig where ValiFlag = '1' order by calcode"; 
            SQLwithBindVariables sqlbvs=new SQLwithBindVariables();
            sqlbvs.sql(str);
            mSSRS = tExeSQL.execSQL(sqlbvs);
            int trowsize = mSSRS.getMaxRow();
	    if (trowsize < 1) {
	        logger.debug("StatSqlConfig表中没有需要执行的检查语句！");
                return;
	    }
	    tCalCode = "";
	    tCalSql = "";
            for(int i=1;i<=trowsize ;i++)
            {
                rSSRS = new SSRS();
                tExeSQL = new ExeSQL();
                try{
                    tCalCode = mSSRS.GetText(i, 1);
                    tCalSql = mSSRS.GetText(i, 6);
                    tCalSql = StrTool.replaceEx(tCalSql, "’", "'"); //替换字符
//20080507 d                   rSSRS = tExeSQL.execSQL(tCalSql);
                    int m = 0;
                    if (tCalCode.compareTo("c000") > 0) {
                    	SQLwithBindVariables sqlbva = new SQLwithBindVariables();
                    	sqlbva.sql(tCalSql);
                        countdata(tCalCode,sqlbva);   //计算数据记录条数的语句
                    }
                    else {
                    	SQLwithBindVariables sbv=new SQLwithBindVariables();
                    	sbv.sql("select count(*) from (" + tCalSql + ") gq");
                    	 	rSSRS = tExeSQL.execSQL(sbv);                       //20080507 a
                        if (tExeSQL.mErrors.getErrorCount() == 0) {
                            if (rSSRS != null) {
//20080507 d                               m = rSSRS.getMaxRow();
                                m = Integer.parseInt(rSSRS.GetText(1, 1));   //20080507 a
                                logger.debug("效验的sql数量" + m);
                            } else
                                m = 0;

//                            str = "update StatSqlConfig set ErrCount='" + m +"条' , ValiFlag = '0' ," +" MakeDate='" + PubFun.getCurrentDate() +"',MakeTime='" + PubFun.getCurrentTime() +"' WHERE CalCode='" +tCalCode + "'";
                            str = "update StatSqlConfig set ErrCount=concat('" + "?ErrCount?" +"','条') ," + "MakeDate='" +"?MakeDate?" +"',MakeTime='" + "?MakeTime?" +"' WHERE CalCode='" +"?CalCode?" + "'";
                            SQLwithBindVariables sqlbv=new  SQLwithBindVariables();
                            sqlbv.sql(str);
                            sqlbv.put("ErrCount", m);
                            sqlbv.put("MakeDate",  PubFun.getCurrentDate());
                            sqlbv.put("MakeTime", PubFun.getCurrentTime());
                            sqlbv.put("CalCode", tCalCode);
                            if (!tExeSQL.execUpdateSQL(sqlbv)) {
                                logger.debug("更新数据失败");
                            }
                        }
                        else {
//                            str = "update StatSqlConfig set ErrCount='SQL中的表或字段不存在' , ValiFlag = '0' ," +
//                                    " MakeDate='" + PubFun.getCurrentDate() +
//                                    "',MakeTime='" + PubFun.getCurrentTime() +
//                                    "' WHERE CalCode='" + tCalCode + "'";
                        	//不进行是否控制的调整，即只保留最新的校验结果即可
                            str = "update StatSqlConfig set ErrCount='SQL中的表或字段不存在'  ," +
                            " MakeDate='" + "?MakeDate?" +
                            "',MakeTime='" +"?MakeTime?" +
                            "' WHERE CalCode='" + "?CalCode?" + "'";
                            SQLwithBindVariables sqlbv1=new  SQLwithBindVariables();
                            sqlbv1.sql(str);
                            sqlbv1.put("MakeDate", PubFun.getCurrentDate());
                            sqlbv1.put("MakeTime", PubFun.getCurrentTime());
                            sqlbv1.put("CalCode", tCalCode);
                            if (!tExeSQL.execUpdateSQL(sqlbv1)) {
                                logger.debug("更新数据失败err");
                            }
                        }
                    }

                }
                catch(Exception ex){
                    System.err.print(ex);
                }
            }
            //1 String iSQL ="insert into StatSqlConfigLog select calcode,ErrCount,remark,substr(sysdate,1,10),substr(sysdate,12,10) from StatSqlConfig ";
            String iSQL ="insert into StatSqlConfigLog select calcode,ErrCount,remark,substr(now(),1,10),substr(now(),12,10) from StatSqlConfig ";
            SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
            sqlbv6.sql(iSQL);
            if (!tExeSQL.execUpdateSQL(sqlbv6)) {
                logger.debug("插入日志表失败");
            }
	}
        public void countdata(String TcCode,SQLwithBindVariables CalSql) {
            String DataCount = "";
            ExeSQL tSQL1 = new ExeSQL();
            DataCount = tSQL1.getOneValue(CalSql);
            if (tSQL1.mErrors.getErrorCount() != 0)
                DataCount = "无表";
            else if (DataCount.equals("") || DataCount == null)
                     DataCount = "空值";
            else if (DataCount.length() > 30)
                     DataCount = DataCount.substring(30);
            logger.debug("效验的数量" + DataCount);

//            String strsql = "update StatSqlConfig set ErrCount='" + DataCount +
//                  "' , ValiFlag = '0' ," +
//                  " MakeDate='" + PubFun.getCurrentDate() +
//                  "',MakeTime='" + PubFun.getCurrentTime() +
//                  "' WHERE CalCode='" + TcCode + "'";
          //不进行是否控制的调整，即只保留最新的校验结果即可
          //String strsql = "update StatSqlConfig set ErrCount='" + DataCount +" , MakeDate='" + PubFun.getCurrentDate() +"',MakeTime='" + PubFun.getCurrentTime() +"' WHERE CalCode='" + TcCode + "'";
            String strsql = "update StatSqlConfig set ErrCount='" + "?ErrCount?" +"', MakeDate='" + "?MakeDate?" +"',MakeTime='" + "?MakeTime?" +"' WHERE CalCode='" + "?CalCode?" + "'";
            SQLwithBindVariables sqlbv3=new  SQLwithBindVariables();
            sqlbv3.sql(strsql);
            sqlbv3.put("ErrCount", DataCount);
            sqlbv3.put("MakeDate", PubFun.getCurrentDate());
            sqlbv3.put("MakeTime", PubFun.getCurrentTime());   
            sqlbv3.put("CalCode", TcCode);
           if (!tSQL1.execUpdateSQL(sqlbv3)) {
                logger.debug(TcCode + "表更新数据失败");
            }

        }

	public static void main()
	{
		// TODO Auto-generated method stub
		ExeSQL tExeSQL = new ExeSQL();
		 SSRS mSSRS = new SSRS();
		 String tCalCode = "";
		    String tCalSql = "";
	            String str ="select * from StatSqlConfig where ValiFlag = '1' order by calcode";
	            SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
	            sqlbv7.sql(str);
	            mSSRS = tExeSQL.execSQL(sqlbv7);
	            int trowsize = mSSRS.getMaxRow();
		    if (trowsize < 1) {
		        logger.debug("StatSqlConfig表中没有需要执行的检查语句！");
	                return;
		    }
		    
		Vector vectorlists = new Vector();
		for(int i=0;i<trowsize;i++)
		{
			
			String[] temp = {mSSRS.GetText(i+1, 1),mSSRS.GetText(i+1, 6)};	
			vectorlists.add(temp);
		}
		ServiceA tService = new ServiceA(
				"com.sinosoft.lis.audit.CheckSQL",
				vectorlists, 5,5);
		tService.start();
		// 2
		 String iSQL ="insert into StatSqlConfigLog select calcode,ErrCount,remark,substr(now(),1,10),substr(now(),12,10) from StatSqlConfig ";
         SQLwithBindVariables sqlbv=new SQLwithBindVariables();
         sqlbv.sql(iSQL);
		 if (!tExeSQL.execUpdateSQL(sqlbv)) {
             logger.debug("插入日志表失败");
         }
	}
//	public void setObject(Object object)
//	{
//		
//		// TODO Auto-generated method stub
//		String[] temp = (String[])object;
//		this.mCalCode=temp[0];
//		this.mCalSql=temp[1];
//	}
	public void setObject(Object tObject) {
		//多线程的外部参数条件
		mInputDataNew = (VData) tObject;
		mTransferData = (TransferData) mInputDataNew.getObjectByObjectName(
				"TransferData", 0);
		if (mTransferData != null) {
			this.mCalCode = (String) mTransferData.getValueByName("CalCode");
			this.mCalSql = (String) mTransferData.getValueByName("CalSql");
		}
		mGlobalInput=(GlobalInput) mInputDataNew.getObjectByObjectName(
				"GlobalInput", 0);	
	}
	
	public void run()
	{
		// TODO Auto-generated method stub
		String tCalCode = this.mCalCode;
		String tCalSql = this.mCalSql;
		 try{

             tCalSql = StrTool.replaceEx(tCalSql, "’", "'"); //替换字符
//20080507 d                   rSSRS = tExeSQL.execSQL(tCalSql);
             int m = 0;
             String str ;
             if (tCalCode.compareTo("c000") > 0) {
            	 SQLwithBindVariables sqlbvb = new SQLwithBindVariables();
             	 sqlbvb.sql(tCalSql);
                 countdata(tCalCode,sqlbvb);   //计算数据记录条数的语句
             }
             else {
            	 ExeSQL tExeSQL = new ExeSQL();
            	 SSRS rSSRS = new SSRS();
             	 	//rSSRS = tExeSQL.execSQL("select count(*) from (" + tCalSql + ") gq");                       //20080507 a
            	     SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
            	     sqlbv5.sql("select count(*) from (" + tCalSql + ") gq");
             
             	 	  rSSRS = tExeSQL.execSQL(sqlbv5);
                 if (tExeSQL.mErrors.getErrorCount() == 0) {
                     if (rSSRS != null) {
//20080507 d                               m = rSSRS.getMaxRow();
                         m = Integer.parseInt(rSSRS.GetText(1, 1));   //20080507 a
                         logger.debug("效验的sql数量" + m);
                     } else
                         m = 0;
                    
//                   str = "update StatSqlConfig set ErrCount='" + m +"条' , ValiFlag = '0' ," +" MakeDate='" + PubFun.getCurrentDate() +"',MakeTime='" + PubFun.getCurrentTime() +"' WHERE CalCode='" +tCalCode + "'";
                     str = "update StatSqlConfig set ErrCount=concat('" + "?ErrCount?"+ "','条')," +" MakeDate='" + "?MakeDate?" +"',MakeTime='" + "?MakeTime?" +"' WHERE CalCode='" +"?CalCode?" + "'";
                     SQLwithBindVariables sqlbv6=new  SQLwithBindVariables();
                     sqlbv6.sql(str);
                     sqlbv6.put("ErrCount", m);
                     sqlbv6.put("MakeDate",  PubFun.getCurrentDate());
                     sqlbv6.put("MakeTime", PubFun.getCurrentTime());
                     sqlbv6.put("CalCode", tCalCode);
                     if (!tExeSQL.execUpdateSQL(sqlbv6)) {
                         logger.debug("更新数据失败");
                     }
     			    //日志监控,过程监控        
     			  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"CalCode为"+tCalCode+"校验的SQL数量"+m+"条");
                 }
                 else {
//                     str = "update StatSqlConfig set ErrCount='SQL中的表或字段不存在' , ValiFlag = '0' ," +
//                             " MakeDate='" + PubFun.getCurrentDate() +
//                             "',MakeTime='" + PubFun.getCurrentTime() +
//                             "' WHERE CalCode='" + tCalCode + "'";
                 	//不进行是否控制的调整，即只保留最新的校验结果即可
                     str = "update StatSqlConfig set ErrCount='SQL中的表或字段不存在'  ," +
                     " MakeDate='" +"?MakeDate?" +
                     "',MakeTime='" + "?MakeTime?" +
                     "' WHERE CalCode='" + "?CalCode?" + "'";
                     SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
                     sqlbv7.put("MakeDate", PubFun.getCurrentDate());
                     sqlbv7.put("MakeTime", PubFun.getCurrentTime());
                     sqlbv7.put("CalCode", tCalCode);
                     if (!tExeSQL.execUpdateSQL(sqlbv7)) {
                         logger.debug("更新数据失败err");
                     }
                     //日志监控,过程监控        
      			  	PubFun.logTrack(mGlobalInput,mGlobalInput.LogID[1],"CalCode为"+tCalCode+"校验SQL中的表或字段不存在");
                 }
             }

         }
         catch(Exception ex){
             System.err.print(ex);
         }
         finally
         {
        	 this.close();
         }
		
	}
}
