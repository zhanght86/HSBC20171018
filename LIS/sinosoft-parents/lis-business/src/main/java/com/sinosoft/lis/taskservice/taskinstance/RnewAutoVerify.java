package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.TaskThread;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.operfee.IndiFinUrgeVerifyUI;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;


import com.sinosoft.lis.reagent.*;

import java.sql.Connection;

/**
 * <p>Title: </p>
 * <p>Description: 核销自动运行程序</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author xiongzh 08-1-30
 * @version 1.0
 */

public class RnewAutoVerify extends TaskThread 
{
private static Logger logger = Logger.getLogger(RnewAutoVerify.class);



        public RnewAutoVerify() {
        }

        public CErrors mErrors = new CErrors();
        private String mDate=null;
        private ExeSQL tExeSQL = new ExeSQL();
        private String tContNo ="";


        public boolean dealMain()
        {
            /*业务处理逻辑*/
            logger.debug("进入业务逻辑处理,开始自动核销。!");
            logger.debug("Start AutoVerify~~~"); 
            
            GlobalInput tGI=new GlobalInput();
            tGI.ComCode="86";
            tGI.Operator="001";
            tGI.ManageCom="86";
            
			 //日志监控,初始化           	
            tGI.LogID[0]="TASK"+(String)mParameters.get("TaskCode");          
            tGI.LogID[1]=(String)mParameters.get("SerialNo"); 
		    //日志监控,过程监控        
		  	PubFun.logTrack(tGI,tGI.LogID[1],"续期核销批处理开始");
		  	
            //实现查询出需要核销的保单号集合
            String Data_sql="";
            if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            Data_sql = "select  /*+index(ljspay ,IDX_LJSPAY_5)*/ otherno from ljspay where StartPayDate <= now() "
	          +" and othernotype = '2'  and sumduepaymoney > 0 "
	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
	          +" and exists (select 1 from ljtempfee where tempfeeno = ljspay.getnoticeno and tempfeetype = '2' "
	          +"  and othernotype = '0' and (case when ConfFlag is not null then ConfFlag else '0' end) = '0'  and EnterAccDate is not null and makedate <= '"+"?PubFun?"+"') "
	          //+"  and not exists "
	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
	          +"  and not exists "
	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
	          +" union "
	          + "select  /*+index(ljspay ,IDX_LJSPAY_5)*/ otherno from ljspay where StartPayDate <= now() "
	          +" and othernotype = '2'  and sumduepaymoney = 0 "
	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
	          //+"  and not exists "
	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
	          +"  and not exists "
	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
	          ;
            }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
            	  Data_sql = "select otherno from ljspay where StartPayDate <= now() "
            	          +" and othernotype = '2'  and sumduepaymoney > 0 "
            	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
            	          +" and exists (select 1 from ljtempfee where tempfeeno = ljspay.getnoticeno and tempfeetype = '2' "
            	          +"  and othernotype = '0' and (case when ConfFlag is not null then ConfFlag else '0' end) = '0'  and EnterAccDate is not null and makedate <= '"+"?PubFun?"+"') "
            	          //+"  and not exists "
            	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
            	          +"  and not exists "
            	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
            	          +" union "
            	          + "select otherno from ljspay where StartPayDate <= now() "
            	          +" and othernotype = '2'  and sumduepaymoney = 0 "
            	          +" and exists (select 1 from LCPol where contno = ljspay.OtherNO and appflag='1') "//避免可能因为理赔导致销户，但应收和暂收还存在
            	          //+"  and not exists "
            	          //+" (select 1 from lcprem where contno = ljspay.otherno  and freeflag='1') "//排除豁免险种
            	          +"  and not exists "
            	          +" ( select 1 from lcconthangupstate where contno = ljspay.otherno and rnflag='1') "//排除挂起保单
            	          ;	
            }
       	    SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	 		sqlbv1.sql(Data_sql);
	 		sqlbv1.put("PubFun", PubFun.getCurrentDate());
            SSRS data_ssrs = new SSRS();
            data_ssrs = tExeSQL.execSQL(sqlbv1);
            int suc=0,fail=0;
            for(int i=1;i<=data_ssrs.MaxRow;i++)
            {
            	String Content = "";
            	tContNo = "";
            	tContNo = data_ssrs.GetText(i, 1);
            	
            	LJTempFeeSchema tLJTempFeeSchema = new LJTempFeeSchema();
            	tLJTempFeeSchema.setOtherNo(tContNo);
            	
            	VData tVData = new VData();
            	tVData.add(tLJTempFeeSchema);
            	tVData.add(tGI);

            	IndiFinUrgeVerifyUI tIndiFinUrgeVerifyUI = new IndiFinUrgeVerifyUI();
            	tIndiFinUrgeVerifyUI.submitData(tVData, "VERIFY");
            	mErrors = tIndiFinUrgeVerifyUI.mErrors;
            	if (!mErrors.needDealError()) {
            		Content = "ContNo="+tContNo+" 核销事务成功";
//					日志监控,状态监控          
	  		    	PubFun.logState(tGI,tContNo,"保单"+tContNo+"续期核销成功","1");
	  		    	suc++;
            	} else {
            		Content = "ContNo="+tContNo+" 核销事务失败，原因是:" + mErrors.getFirstError();
//					日志监控,状态监控          
	  		    	PubFun.logState(tGI,tContNo,"保单"+tContNo+"续期核销失败，原因是:"+mErrors.getFirstError(),"0");
	  		    	fail++;
            	}
            	logger.debug(Content);
            }
            logger.debug("end deal AutoVerify!! ");  
//			日志监控,结果监控
			PubFun.logResult (tGI,tGI.LogID[1],"共有"+suc+"个保单续期核销成功");
			PubFun.logResult (tGI,tGI.LogID[1],"共有"+fail+"个保单续期核销失败");	
            //日志监控,过程监控        
		  	PubFun.logTrack(tGI,tGI.LogID[1],"续期核销批处理结束");
            return true;
        }
        
        public static void main(String[] args)
        {
//          CalCommisionTask tCalCommisionTask = new CalCommisionTask();
//          tCalCommisionTask.run();
        	RnewAutoVerify tRnewAutoVerify = new RnewAutoVerify();
        	tRnewAutoVerify.dealMain();
        }
        

}
