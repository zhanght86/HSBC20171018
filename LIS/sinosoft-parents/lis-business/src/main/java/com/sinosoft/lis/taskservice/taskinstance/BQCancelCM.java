package com.sinosoft.lis.taskservice.taskinstance;
import org.apache.log4j.Logger;

import com.sinosoft.lis.taskservice.TaskThread;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.operfee.ShowDueFeeList;
import java.sql.Connection;




/**
 * <p>Title: </p>
 * <p>Description: 新保险法上线后处理老版本中CM/IC未完结业务数据</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class BQCancelCM extends TaskThread
{
private static Logger logger = Logger.getLogger(BQCancelCM.class);



        public BQCancelCM() {
        }

    	/**字段映射类*/
    	private Reflections tRef = new Reflections();
        public CErrors mErrors = new CErrors();
        private String mDate=null;
    	/** 往界面传输数据的容器 */
    	private MMap mMap = new MMap();
    	private VData mResult = new VData();
        
        /* 1,被保人
        * 2,投保人
        * 3,既是投保人又是被保人
        */
        private String flag=""; 
        
        private LPInsuredSet tLPInsuredSet = new LPInsuredSet();
        private LPAppntSet tLPAppntSet = new LPAppntSet();
        
        private LCInsuredSet delete_LCInsuredSet = new LCInsuredSet();
        private LCInsuredSet insert_LCInsuredSet = new LCInsuredSet();
        
        private LCAppntSet delete_LCAppntSet = new LCAppntSet();
        private LCAppntSet insert_LCAppntSet = new LCAppntSet();
        
        private LCContSet mLCContSet = new LCContSet();
        private LPContSet delete_LPContSet = new LPContSet();
        
        private LCPolSet mLCPolSet = new LCPolSet();
        private LPPolSet delete_LPPolSet = new LPPolSet();
        
        private LDPersonSet mLDPersonSet = new LDPersonSet();
        private LPPersonSet delete_LPPersonSet = new LPPersonSet();
        

        
    	// 获得此时的日期和时间
    	private String strCurrentDate = PubFun.getCurrentDate();
    	private String strCurrentTime = PubFun.getCurrentTime();

        public boolean dealMain()
        {
          /*业务处理逻辑*/
          logger.debug("start BQCancelCM!!");
          logger.debug("进入业务逻辑处理,开始批量撤销CM。!");

          VData tVData = new VData();
          GlobalInput tG = new GlobalInput();
          tG.Operator = "auto";
          tG.ManageCom = "86";
          String sql="select * from lpedoritem a where edortype = 'CM' "
			+" and (edorappdate < '2010-4-27' or  edorappdate = '2010-4-27' and maketime <= '12:50:00') "
			+" and exists  (select 1 from lpconttempinfo b, lccont c  where b.edortype = 'CM' and b.edoracceptno = a.edoracceptno "
			+"                and b.state = '0'  and b.contno = c.contno  and c.appflag = '1')"
			+" and a.contno in ('86110020070210109249','86110020090210087748','86110020090210089828','86110020090210046084',"
			+" '86130620080210044824','86130220070210031516','86130220040210000584','86130220050210000228',"
			+" '86130020060210012910','86211220050210000546','86211220060210002096','86210720080210007058',"
			+" '86310020080210015478','86310020050210041010','86310020090210021509','86320320060210001594',"
			+" '86320020070210102895', '86320020090210110534','86320620090210036281','86321220070210002537',"
			+" '86360720080210005078','86370020060210019805','86410420070210001129','86421120090210000203',"
			+" '86450320090210000296','86510520070210004970','86513420070210016744','86513420070210004220')"
			//+" and contno in ('86450320090210000296','86510520070210004970')"
			+" 	  order by operator, edorappdate ";
       	  SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		  sqlbv1.sql(sql);
          LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
          LPEdorItemSet tLPEdorItemSet =new LPEdorItemSet();
          tLPEdorItemSet = tLPEdorItemDB.executeQuery(sqlbv1);
          for(int i=1;i<=tLPEdorItemSet.size();i++)
          {
        	  if(!CancelCM(tLPEdorItemSet.get(i)))
        	  {
        		  logger.debug("保单号"+tLPEdorItemSet.get(i).getContNo()+"CM撤销处理失败");
        		  continue;
        	  }
      		  if (!prepareOutputData(tLPEdorItemSet.get(i).getEdorAcceptNo(),tLPEdorItemSet.get(i).getEdorNo())) 
      		  {
      			 logger.debug("保单号"+tLPEdorItemSet.get(i).getContNo()+"CM准备提交数据失败");
      		     continue;
    		  }
             //提交数据
  			  PubSubmit tSubmit = new PubSubmit();
  			  if (!tSubmit.submitData(mResult, "")) 
  			  {
  				 logger.debug("保单"+tLPEdorItemSet.get(i).getContNo()+"CM撤销数据提交失败！");
  				 this.mErrors.copyAllErrors(tSubmit.mErrors);
  				 //ErrCount++;
  				 mResult.clear();
  				 continue;
  			  }
  			  mResult.clear();
        	  
          }
          logger.debug("end BQCancelCM!!");
          return true;
        }

      /*
       * CM撤销
       */
     private boolean CancelCM(LPEdorItemSchema tLPEdorItemSchema)
     {
    	 String tEdorAcceptNo = tLPEdorItemSchema.getEdorAcceptNo();
    	 String tEdorNo = tLPEdorItemSchema.getEdorNo();
    	 //首先通过批改号查询出当前变更客户号
    	 LPPersonSchema tLPPersonSchema = new LPPersonSchema();
    	 LPPersonDB tLPPersonDB = new LPPersonDB();
    	 
    	 tLPPersonDB.setEdorType("CM");
    	 tLPPersonDB.setEdorNo(tEdorNo);
    	 if(tLPPersonDB.query().size()==0)
    	 {
    		 logger.debug("保单号"+tLPEdorItemSchema.getContNo()+"查询客户备份信息时出错");
    		 return false;
    	 }
    	 tLPPersonSchema = tLPPersonDB.query().get(1);
    	 delete_LPPersonSet.add(tLPPersonSchema);
    	 
    	 LDPersonSchema tLDPersonSchema = new LDPersonSchema();
    	 tRef.transFields(tLDPersonSchema, tLPPersonSchema);
    	 tLDPersonSchema.setOperator("SYSMOD");
    	 tLDPersonSchema.setModifyDate(strCurrentDate);
    	 tLDPersonSchema.setModifyTime(strCurrentTime);
    	 mLDPersonSet.add(tLDPersonSchema);
    	 
    	 
    	 //通过查询lpcont确定当前CM涉及到几张保单,循环处理每一张保单
    	 LPContSet tLPContSet = new LPContSet();
    	 LPContDB tLPContDB = new LPContDB();
    	 tLPContDB.setEdorType("CM");
    	 tLPContDB.setEdorNo(tEdorNo);
    	 tLPContSet = tLPContDB.query();
    	 for(int j=1;j<=tLPContSet.size();j++)
    	 {
    		 String tContNo =tLPContSet.get(j).getContNo();
    		 //判断客户号是当前保单的投保人还是被保人
    		 if(!getflag(tContNo,tEdorNo))
    		 {
    			 logger.debug("保单号"+tLPEdorItemSchema.getContNo()+"判断客户角色时出错");
        		 return false;
    		 }
    		 if("1".equals(flag)) //只是被保人
    		 {
    			 if(!dealInsured(tContNo))
    			 {
    				 logger.debug("保单号"+tLPEdorItemSchema.getContNo()+"回退保单层被保人信息时出错");
            		 return false;
    			 }
    		 }
    		 else if("2".equals(flag))  //只是投保人
    		 {
    			 if(!dealAppnt(tContNo))
    			 {
    				 logger.debug("保单号"+tLPEdorItemSchema.getContNo()+"回退保单层投保人信息时出错");
            		 return false;
    			 }
    		 }
    		 else  //既是投保人又是被保人
    		 {
    			 if(!dealInsured(tContNo))
    			 {
    				 logger.debug("保单号"+tLPEdorItemSchema.getContNo()+"回退保单层被保人信息时出错");
            		 return false;
    			 }
    			 if(!dealAppnt(tContNo))
    			 {
    				 logger.debug("保单号"+tLPEdorItemSchema.getContNo()+"回退保单层投保人信息时出错");
            		 return false;
    			 } 
    		 }
    		 //然后处理lccont和lcpol 	 
        	 LPContSchema aLPContSchema = tLPContSet.get(j);
        	 delete_LPContSet.add(aLPContSchema);
        	 
        	 LCContSchema tLCContSchema = new LCContSchema();
        	 tRef.transFields(tLCContSchema, aLPContSchema);
        	 tLCContSchema.setOperator("SYSMOD");
        	 tLCContSchema.setModifyDate(strCurrentDate);
        	 tLCContSchema.setModifyTime(strCurrentTime);
        	 mLCContSet.add(tLCContSchema);
        	 
        	 LPPolDB tLPPolDB = new LPPolDB();
        	 tLPPolDB.setEdorType("CM");
        	 tLPPolDB.setEdorNo(tEdorNo);
        	 tLPPolDB.setContNo(tContNo);   	 
        	 
        	 LPPolSet tLPPolSet = tLPPolDB.query();
        	 for(int n=1;n<=tLPPolSet.size();n++)
        	 {
            	 delete_LPPolSet.add(tLPPolSet.get(n));
            	 
            	 LCPolSchema tLCPolSchema = new LCPolSchema();
            	 tRef.transFields(tLCPolSchema, tLPPolSet.get(n));
            	 tLCPolSchema.setOperator("SYSMOD");
            	 tLCPolSchema.setModifyDate(strCurrentDate);
            	 tLCPolSchema.setModifyTime(strCurrentTime);
            	 mLCPolSet.add(tLCPolSchema);
        	 }
    	 }
    	 //最后删除保全表及保全数据放到prepareOutputData中
    	 
    	 return true;
     }
     
 	private boolean prepareOutputData(String xEdorAcceptNo,String xEdorNo) 
 	{

 		mMap = new MMap();
 		mMap.put(tLPInsuredSet, "DELETE");
 		mMap.put(tLPAppntSet, "DELETE");
 		
		mMap.put(delete_LCInsuredSet, "DELETE");
		mMap.put(insert_LCInsuredSet, "INSERT");
		mMap.put(delete_LCAppntSet, "DELETE");
		mMap.put(insert_LCAppntSet, "INSERT");
		
		mMap.put(delete_LPContSet, "DELETE");
		mMap.put(mLCContSet, "DELETE&INSERT");
		
		mMap.put(delete_LPPolSet, "DELETE");
		mMap.put(mLCPolSet, "DELETE&INSERT");
		
		mMap.put(delete_LPPersonSet, "DELETE");
		mMap.put(mLDPersonSet, "DELETE&INSERT");
		
		
		//然后删除所有保全表数据
		 SQLwithBindVariables sqlbv3= new SQLwithBindVariables();
		  sqlbv3.sql("delete from lpedorapp where edoracceptno='"+"?xEdorAcceptNo?"+"'");
		  sqlbv3.put("xEdorAcceptNo", xEdorAcceptNo);
		mMap.put(sqlbv3,"DELETE");
		 SQLwithBindVariables sqlbv4= new SQLwithBindVariables();
		  sqlbv4.sql("delete from lpedoritem where edoracceptno='"+"?xEdorAcceptNo?"+"'");
		  sqlbv4.put("xEdorAcceptNo", xEdorAcceptNo);
		mMap.put(sqlbv4,"DELETE");
		 SQLwithBindVariables sqlbv5= new SQLwithBindVariables();
		  sqlbv5.sql("delete from lpedormain where edoracceptno='"+"?xEdorAcceptNo?"+"'");
		  sqlbv5.put("xEdorAcceptNo", xEdorAcceptNo);
		mMap.put(sqlbv5,"DELETE");
		 SQLwithBindVariables sqlbv6= new SQLwithBindVariables();
		  sqlbv6.sql("delete from lpconttempinfo where edoracceptno='"+"?xEdorAcceptNo?"+"'");
		  sqlbv6.put("xEdorAcceptNo", xEdorAcceptNo);
		mMap.put(sqlbv6,"DELETE");
		
		mResult.clear();
		mResult.add(mMap);

		return true;
	}
     /*
      * 通过保单号，批改号判断客户角色
      * 1,被保人
      * 2,投保人
      * 3,既是投保人又是被保人
      * */
     private boolean getflag(String mContNo,String mEdorNo)
     {
    	 //先清空全局变量
    	 flag="";
    	 tLPInsuredSet =null;
    	 tLPAppntSet =null;
    	 
    	 String insured_flag ="N";
    	 String appnt_flag ="N";
    	 LPInsuredDB mLPInsuredDB = new LPInsuredDB();
    	 mLPInsuredDB.setEdorNo(mEdorNo);
    	 mLPInsuredDB.setContNo(mContNo);
    	 mLPInsuredDB.setEdorType("CM");
    	 if(mLPInsuredDB.query().size()>0)
    	 {
    		 insured_flag ="Y";
    		 tLPInsuredSet = mLPInsuredDB.query();
    	 }
    	 
    	 LPAppntDB mLPAppntDB = new LPAppntDB();
    	 mLPAppntDB.setEdorNo(mEdorNo);
    	 mLPAppntDB.setContNo(mContNo);
    	 mLPAppntDB.setEdorType("CM");
    	 if(mLPAppntDB.query().size()>0)
    	 {
    		 appnt_flag ="Y";
    		 tLPAppntSet = mLPAppntDB.query();
    	 }
    	 if("N".equals(insured_flag)&&"N".equals(appnt_flag))
    	 {
    		 return false;
    	 }
    	 
    	 if("Y".equals(insured_flag)&&"N".equals(appnt_flag))
    	 {
    		 flag="1";
    	 }
    	 if("N".equals(insured_flag)&&"Y".equals(appnt_flag))
    	 {
    		 flag="2";
    	 }
    	 if("Y".equals(insured_flag)&&"Y".equals(appnt_flag))
    	 {
    		 flag="3";
    	 }
    	 
    	 return true;
     }
     
     private boolean dealInsured(String aContNo)
     {
    	 LCInsuredDB tLCInsuredDB = new LCInsuredDB();
    	 tLCInsuredDB.setContNo(aContNo);
    	 LCInsuredSchema delete_LCInsuredSchema  =tLCInsuredDB.query().get(1);
    	 delete_LCInsuredSet.add(delete_LCInsuredSchema);
    	 
    	 LPInsuredSchema aLPInsuredSchema = tLPInsuredSet.get(1);
    	 LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
    	 tRef.transFields(tLCInsuredSchema, aLPInsuredSchema);
    	 tLCInsuredSchema.setOperator("SYSMOD");
    	 tLCInsuredSchema.setModifyDate(strCurrentDate);
    	 tLCInsuredSchema.setModifyTime(strCurrentTime);
    	 insert_LCInsuredSet.add(tLCInsuredSchema);
    	 
    	 return true;
     }
     
     private boolean dealAppnt(String aContNo)
     {
    	 LCAppntDB tLCAppntDB = new LCAppntDB();
    	 tLCAppntDB.setContNo(aContNo);
    	 LCAppntSchema delete_LCAppntSchema  =tLCAppntDB.query().get(1);
    	 delete_LCAppntSet.add(delete_LCAppntSchema);
    	 
    	 LPAppntSchema aLPAppntSchema = tLPAppntSet.get(1);
    	 LCAppntSchema tLCAppntSchema = new LCAppntSchema();
    	 tRef.transFields(tLCAppntSchema, aLPAppntSchema);
    	 tLCAppntSchema.setOperator("SYSMOD");
    	 tLCAppntSchema.setModifyDate(strCurrentDate);
    	 tLCAppntSchema.setModifyTime(strCurrentTime);
    	 insert_LCAppntSet.add(tLCAppntSchema);
    	 return true;
     }
     
        /**
       *错误生成函数
       */
      private void buildError(String szFunc, String szErrMsg)
      {
         CError cError = new CError();
         cError.moduleName = "BQCancelCM";
         cError.functionName = szFunc;
         cError.errorMessage = szErrMsg;
         this.mErrors.addOneError(cError);
      }

        public static void main(String[] args)
        {
//          CalCommisionTask tCalCommisionTask = new CalCommisionTask();
//          tCalCommisionTask.run();
              BQCancelCM tBQCancelCM = new BQCancelCM();
              //本地测试
              if(!tBQCancelCM.dealMain())
              {
                logger.debug("失败，原因为："+tBQCancelCM.mErrors.getFirstError());
              }
              //tBQCancelCM.run();
        }
}
