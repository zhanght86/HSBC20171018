package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author lh
 * @version 1.0
 */

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.util.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class PFeeInvoiceF1PBL {
private static Logger logger = Logger.getLogger(PFeeInvoiceF1PBL.class);

  /** 错误处理类，每个需要错误处理的类中都放置该类 */
      public CErrors mErrors=new CErrors();
      private VData mResult = new VData();

      //业务处理相关变量
      /** 全局数据 */
      private GlobalInput mGlobalInput =new GlobalInput() ;
      private LCPolSchema mLCPolSchema=new LCPolSchema();
      private LJAPaySchema mLJAPaySchema=new LJAPaySchema();
      private LJSPaySchema mLJSPaySchema=new LJSPaySchema();
      private String mOperate="";
      private LOPRTManager2Schema mLOPRTManager2Schema = new LOPRTManager2Schema();

      public PFeeInvoiceF1PBL() {
      }
      /**
       传输数据的公共方法
       */
      public boolean submitData(VData cInputData, String cOperate)
      {
          mOperate=cOperate;
          logger.debug("cOperate:"+cOperate);
          if( !mOperate.equals("FORECONFIRM") && !cOperate.equals("CONFIRM") &&!cOperate.equals("PRINT")&&!cOperate.equals("REPRINT"))
          {
              buildError("submitData", "不支持的操作字符串");
              return false;
          }
        // 得到外部传入的数据，将数据备份到本类中
        if( !getInputData(cInputData) ) {
          return false;
        }

        mResult.clear();

        if( cOperate.equals("CONFIRM") )
        {
          mResult.clear();
          // 准备所有要打印的数据
          getPrintData();
        }
        if( cOperate.equals("FORECONFIRM") )
        {
          mResult.clear();
           // 准备所有要打印的数据
          getForePrintData();
        }
        else if( cOperate.equals("PRINT"))
        {
            logger.debug("update print");
          if( !saveData() )
          {
            return false;
          }
        }
        else if( cOperate.equals("REPRINT"))
        {
            mResult.clear();
            // 准备所有要打印的数据
            getPrintData2();
        }


        return true;
      }

      /**
       * 根据前面的输入数据，进行BL逻辑处理
       * 如果在处理过程中出错，则返回false,否则返回true
       */
      private boolean dealData()
      {
        return true;
      }
  
      /**
       * 从输入数据中得到所有对象
       * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
       */
      private boolean getInputData(VData cInputData)
      {
          if (mOperate.equals("PRINT")||mOperate.equals("REPRINT"))
          {
              mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
              mLOPRTManager2Schema.setSchema((LOPRTManager2Schema)cInputData.getObjectByObjectName("LOPRTManager2Schema",0));
          }

         if (mOperate.equals("CONFIRM"))
         {
             //全局变量
             mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
             mLJAPaySchema.setSchema((LJAPaySchema)cInputData.getObjectByObjectName("LJAPaySchema",0));
         }
       if (mOperate.equals("FORECONFIRM"))
        {
         //全局变量
           mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
           mLJSPaySchema.setSchema((LJSPaySchema)cInputData.getObjectByObjectName("LJSPaySchema",0));
        }

        if( mGlobalInput==null ) {
          buildError("getInputData", "没有得到足够的信息！");
          return false;
        }

        return true;
      }

      public VData getResult()
      {
        return this.mResult;
      }

      private void buildError(String szFunc, String szErrMsg)
      {
        CError cError = new CError( );

        cError.moduleName = "LCPolF1PBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
      }

      private boolean getForePrintData()
      {
      	try{
             TextTag texttag=new TextTag();//新建一个TextTag的实例
             XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
             java.text.DecimalFormat format=new java.text.DecimalFormat(".00"); 
             int i;
               
             LJSPayDB tLJSPayDB = new LJSPayDB();
             tLJSPayDB.setSchema(mLJSPaySchema);
             if(!tLJSPayDB.getInfo()){
               mErrors.copyAllErrors(tLJSPayDB.mErrors);
               buildError("outputXML", "没有收费信息");
               return false;
             }
             String DSumMoney = "" ;
             double SumMoney = tLJSPayDB.getSumDuePayMoney();
             DSumMoney=PubFun.getChnMoney(SumMoney).replaceFirst("人民币", "");

             LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
             tLJSPayPersonDB.setGetNoticeNo(tLJSPayDB.getGetNoticeNo());
             LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
             tLJSPayPersonSet.set(tLJSPayPersonDB.query());
             //处理tLJSPayPersonSet，将主险放在第一条
             for (i=0;i<tLJSPayPersonSet.size();i++){
             	LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
             	tLMRiskAppDB.setRiskCode(tLJSPayPersonSet.get(i+1).getRiskCode());
             	if(!tLMRiskAppDB.getInfo()){
             		mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
                     buildError("outputXML", "在取得险种信息时发生错误");
                     return false;
             	}
             	if("M".equals(tLMRiskAppDB.getSubRiskFlag()) && i!=0 ){
             		LJSPayPersonSchema tLJSPayPersonSchema = tLJSPayPersonSet.get(i+1);
             		LJSPayPersonSchema tempLJSPayPersonSchema =  tLJSPayPersonSet.get(1);
             		tLJSPayPersonSet.set(1,tLJSPayPersonSchema);
             		tLJSPayPersonSet.set(i+1,tempLJSPayPersonSchema);
             		tLJSPayPersonSchema = null;
             		tempLJSPayPersonSchema = null;
             	}
             }
            
             logger.debug("tLJSPayPersonSet.size(): "+tLJSPayPersonSet.size());
             ListTable tlistTable = new ListTable();
             String strArr[] = null;
             tlistTable.setName("FEE");
             LJSPayPersonSchema aLJSPayPersonSchema = null;
             String riskName = "";
             for (i=1;i<=tLJSPayPersonSet.size();i++)
             {
               aLJSPayPersonSchema = new LJSPayPersonSchema();
               aLJSPayPersonSchema.setSchema(tLJSPayPersonSet.get(i));
               LCPolDB tLCPolDB = new LCPolDB();
               tLCPolDB.setPolNo(aLJSPayPersonSchema.getPolNo());

               if (!tLCPolDB.getInfo())
               {
                 mErrors.copyAllErrors(tLCPolDB.mErrors);
                 buildError("outputXML", "没有保单信息");
                 return false;
               }
               strArr = new String[3];
               LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
               tLMRiskAppDB.setRiskCode(tLCPolDB.getRiskCode());
               if (!tLMRiskAppDB.getInfo()){
                   mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
                   buildError("outputXML", "没有险种信息");
                   return false;
                   }
               if(i == 1){
     			  riskName = riskName + (String)tLMRiskAppDB.getRiskName();
               }else{
     			  riskName = riskName + "；"+(String)tLMRiskAppDB.getRiskName();
               }
               strArr[0]=tLMRiskAppDB.getRiskName();
         	  texttag.add("FEE_ROW"+i+"COL1",tLMRiskAppDB.getRiskName());
               LDCodeDB tLDCodeDB = new LDCodeDB();
               tLDCodeDB.setCodeType("payintv");
               tLDCodeDB.setCode(String.valueOf(tLCPolDB.getPayIntv()));
               if (!tLDCodeDB.getInfo())
               {
                 mErrors.copyAllErrors(tLDCodeDB.mErrors);
                 buildError("outputXML", "无效的缴费期间");
                 return false;
               }
               strArr[1]=tLDCodeDB.getCodeName();
               strArr[2]=String.valueOf(aLJSPayPersonSchema.getSumDuePayMoney());
               tlistTable.add(strArr);
               
               texttag.add("FEE_ROW"+i+"COL2",tLDCodeDB.getCodeName());
               texttag.add("FEE_ROW"+i+"COL3",format.format((double)aLJSPayPersonSchema.getSumActuPayMoney()));
               texttag.add("FEE_ROW"+i+"COL5","第"+aLJSPayPersonSchema.getPayCount()+"年");
//               String tstrPaytoDate = tLCPolDB.getPaytoDate();
//               FDate tD=new FDate();
//               Date tDate =new Date();
//               tDate=tD.getDate(tstrPaytoDate);
//               SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
//               texttag.add("FEE_ROW"+i+"COL4",sdf.format(tDate));
               String tSQL = "";
               if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
            	   tSQL = "select CurPayToDate from LJSPayperson where GetNoticeNo = '"+"?GetNoticeNo?"+"' and rownum=1";
               }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
            	   tSQL = "select CurPayToDate from LJSPayperson where GetNoticeNo = '"+"?GetNoticeNo?"+"' limit 0,1";
               }
               SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
               sqlbv1.sql(tSQL);
               sqlbv1.put("GetNoticeNo", mLJSPaySchema.getGetNoticeNo());
               ExeSQL tExeSQL = new ExeSQL();
               String tstrPaytoDate = tExeSQL.getOneValue(sqlbv1);
               FDate tD=new FDate();
               Date tDate =new Date();
               tDate=tD.getDate(tstrPaytoDate);
               Date tDate1 = PubFun.calDate(tDate,-1,"D",null);
               String strPaytoDate = tD.getString(tDate1);
               strPaytoDate = strPaytoDate.substring(0, 4) + "年"
                + strPaytoDate.substring(5, 7) + "月"
                  + strPaytoDate.substring(8, 10) + "日";
               SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
               texttag.add("FEE_ROW"+i+"COL4",sdf.format(tDate));
               
               if(i == 1){//主险
            	   texttag.add("MainRiskName", tLMRiskAppDB.getRiskName());
            	   texttag.add("MainPayIntv", tLDCodeDB.getCodeName());
         	       LAAgentDB tLAAgentDB = new LAAgentDB();
            	   tLAAgentDB.setAgentCode(tLCPolDB.getAgentCode());
            	   if (!tLAAgentDB.getInfo())
            	   {
            		   mErrors.copyAllErrors(tLAAgentDB.mErrors);
            	       buildError("outputXML", "没有代理人信息");
            	       return false;
            	   }
            	   texttag.add("AgentName",tLAAgentDB.getName());
            	               
            	   texttag.add("AgentCode",tLCPolDB.getAgentCode());        
            	   texttag.add("AppntName",tLCPolDB.getAppntName());
                   texttag.add("ContNo",tLCPolDB.getContNo());            
            	   String tstrPaytoDatex = tLCPolDB.getPaytoDate();
//            	   FDate tDx=new FDate();
//            	   Date tDatex =new Date();
//            	   tDatex=tDx.getDate(tstrPaytoDatex);
//            	   Date tDate1 = PubFun.calDate(tDatex,-1,"D",null);
//            	   String strPaytoDate = tDx.getString(tDate1);
//            	   strPaytoDate = strPaytoDate.substring(0, 4) + "年"
//            	                  + strPaytoDate.substring(5, 7) + "月"
//            	                  + strPaytoDate.substring(8, 10) + "日";
            	   texttag.add("PaytoDate", strPaytoDate);
            	   }
             }
             texttag.add("FEE_COL1",riskName);
              logger.debug("22222");
             strArr = new String[3];
             strArr[0] = "Risk"; strArr[1] ="Mode";strArr[2] = "Money";

             LCPolDB tLCPolDB = new LCPolDB();
//             tLCPolDB.setPolNo(tLJSPayDB.getOtherNo());
//             if( !tLCPolDB.getInfo() ) {
//               mErrors.copyAllErrors(tLCPolDB.mErrors);
//               buildError("outputXML", "在取得主险数据时发生错误");
//               return false;
//             }
             //新系统的保单的polno与contno不一致
             String mpSql="select * from lcpol where contno='"+"?contno?"+"' and polno=mainpolno";
             SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
             sqlbv2.sql(mpSql);
             sqlbv2.put("contno", tLJSPayDB.getOtherNo());
             LCPolSet mLCPolSet =tLCPolDB.executeQuery(sqlbv2) ;
             if(mLCPolSet==null || mLCPolSet.size()<1)
             {
            	 CError.buildErr(this, "相应的主险信息不存在");
            	 return false;
             }

             LDCodeDB tLDCodeDB = new LDCodeDB();
             String vtsName = null;
             tLDCodeDB.setCodeType("feeinvoicecomvts");
     	     if(mGlobalInput.ManageCom.length()>=6){
	     		 tLDCodeDB.setCode(mGlobalInput.ManageCom.substring(0, 6));
	     		 if (!tLDCodeDB.getInfo())
	     		 {
	     		      	tLDCodeDB.setCodeType("feeinvoicecomvts");
	     		        tLDCodeDB.setCode(mGlobalInput.ManageCom.substring(0, 4));
	     		        if(tLDCodeDB.getInfo()) {
	     		           	vtsName = tLDCodeDB.getCodeName();
	     		        }
	     		 }else{
	     		       	vtsName = tLDCodeDB.getCodeName();
	     		 }
     	     }
     	     if(mGlobalInput.ManageCom.length()>=4){
     	    	tLDCodeDB.setCodeType("feeinvoicecomvts");
     	        tLDCodeDB.setCode(mGlobalInput.ManageCom.substring(0, 4));
     	        if(tLDCodeDB.getInfo()) {
     	           	vtsName = tLDCodeDB.getCodeName();
     	        }
     	     }
             if(vtsName==null){
             	vtsName = "FeeInvoice.vts";
             }
             xmlexport.createDocument(vtsName,"printer");//最好紧接着就初始化xml文档
             
             texttag.add("PayNo",tLJSPayDB.getGetNoticeNo());
             texttag.add("XSumMoney",format.format((double)tLJSPayDB.getSumDuePayMoney()));
             texttag.add("DSumMoney",DSumMoney);
             texttag.add("Handler",mGlobalInput.Operator);
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy  MM  dd");
             SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
             SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy    MM    dd");
             texttag.add("Today", sdf.format(new Date()));
             texttag.add("Today2", sdf2.format(new Date()));
             texttag.add("Today1", sdf1.format(new Date()));
         	String nsql = "select Name,address,phone from LDCom where ComCode='"
     			+ "?mGlobalInput?" + "'";
         	SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
         	sqlbv3.sql(nsql);
         	sqlbv3.put("mGlobalInput", mGlobalInput.ManageCom );
     		ExeSQL nExeSQL = new ExeSQL();
     		SSRS nSSRS = nExeSQL.execSQL(sqlbv3);
     		String manageCom = nSSRS.GetText(1, 1);
     		String address = nSSRS.GetText(1, 2);
     		String phone = nSSRS.GetText(1, 3);
     		texttag.add("ManageCom",manageCom);
     		texttag.add("address",address);
     		texttag.add("phone",phone);
             if (texttag.size()>0)
               xmlexport.addTextTag(texttag);
             strArr = new String[3];
             strArr[0] = "Risk"; strArr[1] ="Mode";strArr[2] = "Money";
             xmlexport.addListTable(tlistTable, strArr);
             mResult.clear();
             mResult.addElement(xmlexport);
             mResult.addElement(mLOPRTManager2Schema);

      	}catch(Exception ex)
        {
              buildError("outputXML", ex.toString());
              return false;
        }

      	return true;

      }

      private boolean getPrintData()
      {
    	TextTag texttag=new TextTag();//新建一个TextTag的实例
        XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
        java.text.DecimalFormat format=new java.text.DecimalFormat(".00"); 
        int i;
        LJAPayDB tLJAPayDB = new LJAPayDB();
        tLJAPayDB.setSchema(mLJAPaySchema);
        
        String tSql = "select currency from LJApay where 1 =1 "
			+ " and payNo='"
			+ "?payNo?'";

	//logger.debug("--按保单、理赔类型进行的分组信息，分别进行计算:"+tSql);
	SQLwithBindVariables sqlbv5 =new SQLwithBindVariables();
	sqlbv5.sql(tSql);
	sqlbv5.put("payNo", mLJAPaySchema.getPayNo());
	ExeSQL tExeSQL = new ExeSQL();
	SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
		
		if(tSSRS!=null){
			String[][] aCurrency =tSSRS.getAllData();
			tLJAPayDB.setCurrency(aCurrency[0][0]);
			
		}
        if(!tLJAPayDB.getInfo()){
          mErrors.copyAllErrors(tLJAPayDB.mErrors);
          buildError("outputXML", "没有缴费信息");
          return false;
        }
        String DSumMoney = "" ;
        //zy 2009-08-12 由于对于余额不显示，所以总金额的获取需要进行调整
        double SumMoney = 0.00;
//        double SumMoney = tLJAPayDB.getSumActuPayMoney();
        //不再使用PubFun
//        DSumMoney=getChnMoney(SumMoney).replaceFirst("人民币", "");
        
        LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
        tLJAPayPersonDB.setPayNo(tLJAPayDB.getPayNo());
        //ZY 2009-08-11只查询正常保费
        tLJAPayPersonDB.setPayType("ZC");
        LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
        tLJAPayPersonSet.set(tLJAPayPersonDB.query());
        //处理tLJAPayPersonSet，将主险放在第一条
        for (i=0;i<tLJAPayPersonSet.size();i++){
        	LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        	tLMRiskAppDB.setRiskCode(tLJAPayPersonSet.get(i+1).getRiskCode());
        	if(!tLMRiskAppDB.getInfo()){
        		mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
                buildError("outputXML", "在取得险种信息时发生错误");
                return false;
        	}
        	if("M".equals(tLMRiskAppDB.getSubRiskFlag()) && i!=0 ){
        		LJAPayPersonSchema tLJAPayPersonSchema = tLJAPayPersonSet.get(i+1);
        		LJAPayPersonSchema tempLJAPayPersonSchema =  tLJAPayPersonSet.get(1);
        		tLJAPayPersonSet.set(1,tLJAPayPersonSchema);
        		tLJAPayPersonSet.set(i+1,tempLJAPayPersonSchema);
        		tLJAPayPersonSchema = null;
        		tempLJAPayPersonSchema = null;
        	}
        }
        
 		ListTable tlistTable = new ListTable();
        String strArr[] = null;
        tlistTable.setName("FEE");
        String riskName = "";
        for( i = 1; i <=tLJAPayPersonSet.size(); i++ )
        {
          LJAPayPersonSchema aLJAPayPersonSchema = new LJAPayPersonSchema();
    	  aLJAPayPersonSchema.setSchema(tLJAPayPersonSet.get(i));
    	  

          LCPolDB tLCPolDB = new LCPolDB();
          tLCPolDB.setPolNo(aLJAPayPersonSchema.getPolNo());

          if (!tLCPolDB.getInfo())
          {
            mErrors.copyAllErrors(tLCPolDB.mErrors);
            buildError("outputXML", "没有保单信息");
            return false;
          }
          strArr = new String[3];
          LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
          tLMRiskAppDB.setRiskCode(tLCPolDB.getRiskCode());
          if (!tLMRiskAppDB.getInfo()){
              mErrors.copyAllErrors(tLMRiskAppDB.mErrors);
              buildError("outputXML", "没有险种信息");
              return false;
              }
          strArr[0]=tLMRiskAppDB.getRiskName();
		  texttag.add("FEE_ROW"+i+"COL1",tLMRiskAppDB.getRiskName());
		  if(i == 1){
			  riskName = riskName + (String)tLMRiskAppDB.getRiskName();
		  }else{
			  riskName = riskName + "；"+(String)tLMRiskAppDB.getRiskName();
		  }
          LDCodeDB tLDCodeDB = new LDCodeDB();
          tLDCodeDB.setCodeType("payintv");
          tLDCodeDB.setCode(String.valueOf(tLCPolDB.getPayIntv()));
          if (!tLDCodeDB.getInfo())
          {
            mErrors.copyAllErrors(tLDCodeDB.mErrors);
            buildError("outputXML", "无效的缴费期间");
            return false;
          }
          strArr[1]=tLDCodeDB.getCodeName();
          strArr[2]=String.valueOf(aLJAPayPersonSchema.getSumActuPayMoney());
          tlistTable.add(strArr);
		  texttag.add("FEE_ROW"+i+"COL2",tLDCodeDB.getCodeName());
//          texttag.add("FEE_ROW"+i+"COL3",format.format((double)aLJAPayPersonSchema.getSumActuPayMoney()));
		  //zy 调整金额的显示方式 2009-08-11
		  double pMoney = PubFun.round((aLJAPayPersonSchema.getSumActuPayMoney()), 2);
		  texttag.add("FEE_ROW"+i+"COL3",pMoney);
          texttag.add("FEE_ROW"+i+"COL5","第"+aLJAPayPersonSchema.getPayCount()+"年");
          String tstrPaytoDate = tLCPolDB.getPaytoDate();
          FDate tD=new FDate();
          Date tDate =new Date();
          tDate=tD.getDate(tstrPaytoDate);
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
          texttag.add("FEE_ROW"+i+"COL4",sdf.format(tDate));
          
          if(i == 1){//主险
//目前发票中不需要缴费方式        	  
//        	  String nsql = "";
//        	  if(!"".equals(aLJAPayPersonSchema.getGetNoticeNo())){
//        		  nsql = "select (select codename from ldcode where codetype='paymode' and code=paymode) from ljtempfeeclass a where a.tempfeeno='"+aLJAPayPersonSchema.getGetNoticeNo()+"'";
//        	  }
//        	  else{
//        		  nsql = "select (select codename from ldcode where codetype='paymode' and code=paymode) from ljtempfeeclass a where   a.otherno='"+aLJAPayPersonSchema.getPayNo()+"' ";  
//        	  }
//        	  
////        	  "select (select codename from ldcode where codetype='paymode' and code=paymode) from ljtempfeeclass a where  exists (select * from ljapayperson b where a.otherno=b.contno and b.contno='"+aLJAPayPersonSchema.getContNo()+"') ";
//        	  ExeSQL nExeSQL = new ExeSQL();
//        	  SSRS nSSRS = nExeSQL.execSQL(nsql);
//        	  String payType = nSSRS.GetText(1, 1);	  
//        	  texttag.add("PayType", payType);
        	  texttag.add("MainRiskName", tLMRiskAppDB.getRiskName());
        	  texttag.add("MainPayIntv", tLDCodeDB.getCodeName());
        	  
              LAAgentDB tLAAgentDB = new LAAgentDB();
              tLAAgentDB.setAgentCode(tLCPolDB.getAgentCode());
              if (!tLAAgentDB.getInfo())
              {
                mErrors.copyAllErrors(tLAAgentDB.mErrors);
                buildError("outputXML", "没有代理人信息");
                return false;
              }
              texttag.add("AgentName",tLAAgentDB.getName());
              
              texttag.add("AgentCode",tLCPolDB.getAgentCode());        
              texttag.add("AppntName",tLCPolDB.getAppntName());
              
              String tstrPaytoDatex = tLCPolDB.getPaytoDate();
              FDate tDx=new FDate();
              Date tDatex =new Date();
              tDatex=tDx.getDate(tstrPaytoDatex);
              Date tDate1 = PubFun.calDate(tDatex,-1,"D",null);
              String strPaytoDate = tDx.getString(tDate1);
              strPaytoDate = strPaytoDate.substring(0, 4) + "年"
                           + strPaytoDate.substring(5, 7) + "月"
                           + strPaytoDate.substring(8, 10) + "日";
              texttag.add("PaytoDate", strPaytoDate);
          }
          //zy 2009-08-11 获取总金额
          SumMoney = PubFun.round(SumMoney, 2);
          SumMoney =SumMoney +pMoney;
        }
        texttag.add("FEE_COL1",riskName);
        if(controlPrt(tLJAPayDB.getSchema())==false)
        {
          buildError("outputXML", "在准备打印数据时发生错误");
          return false;
        }
       
        LDCodeDB tLDCodeDB = new LDCodeDB();
        String vtsName = null;
	    tLDCodeDB.setCodeType("feeinvoicecomvts");
	    if(mGlobalInput.ManageCom.length()>=6){
		    tLDCodeDB.setCode(mGlobalInput.ManageCom.substring(0, 6));
		    if (!tLDCodeDB.getInfo())
		    {
		      	tLDCodeDB.setCodeType("feeinvoicecomvts");
		        tLDCodeDB.setCode(mGlobalInput.ManageCom.substring(0, 4));
		        if(tLDCodeDB.getInfo()) {
		           	vtsName = tLDCodeDB.getCodeName();
		        }
		    }else{
		       	vtsName = tLDCodeDB.getCodeName();
		    }
	    }
	    if(mGlobalInput.ManageCom.length()>=4){
	    	tLDCodeDB.setCodeType("feeinvoicecomvts");
	        tLDCodeDB.setCode(mGlobalInput.ManageCom.substring(0, 4));
	        if(tLDCodeDB.getInfo()) {
	           	vtsName = tLDCodeDB.getCodeName();
	        }
	    }
        if(vtsName==null){
        	vtsName = "FeeInvoice.vts";
        }
        xmlexport.createDocument(vtsName,"printer");//最好紧接着就初始化xml文档
        texttag.add("PayNo",tLJAPayDB.getPayNo());
        //zy 调整总金额的显示 2009-08-11
        SumMoney = PubFun.round(SumMoney, 2);
        logger.debug("SumMoney-------"+SumMoney);
        DSumMoney=getChnMoney(SumMoney).replaceFirst("人民币", "");
//        texttag.add("XSumMoney",format.format((double)tLJAPayDB.getSumActuPayMoney()));
        texttag.add("XSumMoney",SumMoney);
        texttag.add("DSumMoney",DSumMoney);
        texttag.add("ContNo",tLJAPayDB.getIncomeNo());
        //texttag.add("Handler",tLCPolDB.getHandler());
        texttag.add("Handler",mGlobalInput.Operator);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy  MM  dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日");
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy    MM    dd");
        texttag.add("Today", sdf.format(new Date()));
        texttag.add("Today2", sdf2.format(new Date()));
        texttag.add("Today1", sdf1.format(new Date()));
    	String nsql = "select Name,address,phone from LDCom where ComCode='"
			+ "?mGlobalInput?" + "'";
    	SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
    	sqlbv4.sql(nsql);
    	sqlbv4.put("mGlobalInput", mGlobalInput.ManageCom);
		ExeSQL nExeSQL = new ExeSQL();
		SSRS nSSRS = nExeSQL.execSQL(sqlbv4);
		String manageCom = nSSRS.GetText(1, 1);
		String address = nSSRS.GetText(1, 2);
		String phone = nSSRS.GetText(1, 3);
		texttag.add("ManageCom",manageCom);
		texttag.add("address",address);
		texttag.add("phone",phone);
        if (texttag.size()>0)
          xmlexport.addTextTag(texttag);
        strArr = new String[3];
        strArr[0] = "Risk"; strArr[1] ="Mode";strArr[2] = "Money";
        xmlexport.addListTable(tlistTable, strArr);
        mResult.clear();
        mResult.addElement(xmlexport);
        mResult.addElement(mLOPRTManager2Schema);
        return true;

      }

      private boolean getPrintData2()
      {
        int i;
        String PolNo = "";
        LJAPayDB tLJAPayDB = new LJAPayDB();
        tLJAPayDB.setPayNo(mLOPRTManager2Schema.getOtherNo());
        if(!tLJAPayDB.getInfo()){
          mErrors.copyAllErrors(tLJAPayDB.mErrors);
          buildError("outputXML", "在取得LJAPay的数据时发生错误");
          return false;
        }
        mLJAPaySchema.setSchema(tLJAPayDB.getSchema());
        String DSumMoney = "" ;
        double SumMoney = tLJAPayDB.getSumActuPayMoney();
        DSumMoney=PubFun.getChnMoney(SumMoney);

        LJAPayPersonDB tLJAPayPersonDB = new LJAPayPersonDB();
        tLJAPayPersonDB.setPayNo(mLJAPaySchema.getPayNo());
        tLJAPayPersonDB.setPayType("ZC");
        LJAPayPersonSet tLJAPayPersonSet = new LJAPayPersonSet();
        tLJAPayPersonSet.set(tLJAPayPersonDB.query());
        ListTable tlistTable = new ListTable();
        String strArr[] = null;
        tlistTable.setName("FEE");
        LJAPayPersonSchema aLJAPayPersonSchema = null;
        for (i=0;i<tLJAPayPersonSet.size();i++)
        {

          aLJAPayPersonSchema = new LJAPayPersonSchema();
          aLJAPayPersonSchema.setSchema(tLJAPayPersonSet.get(i+1));
          LCPolDB tLCPolDB = new LCPolDB();
          tLCPolDB.setPolNo(aLJAPayPersonSchema.getPolNo());
          tLCPolDB.setMainPolNo(aLJAPayPersonSchema.getPolNo());
          if (tLCPolDB.getCount()==1)
          {
            PolNo=tLCPolDB.getPolNo();
          }
          strArr = new String[3];
          LMRiskDB tLMRiskDB = new LMRiskDB();
          tLMRiskDB.setRiskCode(aLJAPayPersonSchema.getRiskCode());
          if (!tLMRiskDB.getInfo()){
              mErrors.copyAllErrors(tLMRiskDB.mErrors);
              buildError("outputXML", "在取得LMRisk的数据时发生错误");
              return false;
              }
          strArr[0]=tLMRiskDB.getRiskName();
          LDCodeDB tLDCodeDB = new LDCodeDB();
          tLDCodeDB.setCodeType("payintv");
          tLDCodeDB.setCode(String.valueOf(aLJAPayPersonSchema.getPayIntv()));
          if (!tLDCodeDB.getInfo())
          {
            mErrors.copyAllErrors(tLDCodeDB.mErrors);
            buildError("outputXML", "在取得LDCode的数据时发生错误");
            return false;
          }
          strArr[1]=tLDCodeDB.getCodeName();
          strArr[2]=String.valueOf(aLJAPayPersonSchema.getSumActuPayMoney());
          tlistTable.add(strArr);
        }
        strArr = new String[3];
        strArr[0] = "Risk"; strArr[1] ="Mode";strArr[2] = "Money";

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(PolNo);
        if( !tLCPolDB.getInfo() ) {
          mErrors.copyAllErrors(tLCPolDB.mErrors);
          buildError("outputXML", "在取得LCPol的数据时发生错误");
          return false;
        }

        LAAgentDB tLAAgentDB = new LAAgentDB();
        tLAAgentDB.setAgentCode(tLCPolDB.getAgentCode());
        if (!tLAAgentDB.getInfo())
        {
          mErrors.copyAllErrors(tLAAgentDB.mErrors);
          buildError("outputXML", "在取得LAAgent的数据时发生错误");
          return false;
        }

        if(controlPrt(tLJAPayDB.getSchema())==false)
        {
            buildError("outputXML", "在准备打印数据时发生错误");
          return false;
        }

        String strPaytoDate = tLCPolDB.getPaytoDate();

        strPaytoDate = strPaytoDate.substring(0, 4) + "年"
                     + strPaytoDate.substring(5, 7) + "月"
                     + strPaytoDate.substring(8, 10) + "日";

        TextTag texttag=new TextTag();//新建一个TextTag的实例
        XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
        xmlexport.createDocument("PFeeInvoice.vts","printer");//最好紧接着就初始化xml文档
        texttag.add("PayNo",tLJAPayDB.getPayNo());
        texttag.add("XSumMoney",tLJAPayDB.getSumActuPayMoney());
        texttag.add("DSumMoney",DSumMoney);
        texttag.add("AppntName",tLCPolDB.getAppntName());
        texttag.add("PolNo",tLCPolDB.getPolNo());
        texttag.add("PaytoDate", strPaytoDate);
        //texttag.add("Handler",tLCPolDB.getHandler());
        texttag.add("Handler",mGlobalInput.Operator);
        texttag.add("AgentCode",tLCPolDB.getAgentCode());
        texttag.add("AgentName",tLAAgentDB.getName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy  MM  dd");
        texttag.add("Today", sdf.format(new Date()));

        if (texttag.size()>0)
          xmlexport.addTextTag(texttag);
        xmlexport.addListTable(tlistTable, strArr);
        mResult.clear();
        mResult.addElement(xmlexport);
        mResult.addElement(mLOPRTManager2Schema);
        return true;

      }

      public boolean saveData()
      {
          logger.debug("update print:"+mLOPRTManager2Schema.getPrtSeq());
          LOPRTManager2DB tLOPRTManager2DB=new LOPRTManager2DB();
          tLOPRTManager2DB.setPrtSeq(mLOPRTManager2Schema.getPrtSeq());
          if(tLOPRTManager2DB.getInfo()==false)
          {
              CError tError = new CError();
              tError.moduleName = "outputXML";
              tError.functionName = "getPrintData";
              tError.errorMessage = "打印数据准备失败!";
              this.mErrors .addOneError(tError) ;
              return false;
          }
          mLOPRTManager2Schema.setSchema(tLOPRTManager2DB.getSchema());
          mLOPRTManager2Schema.setStateFlag("1");
          tLOPRTManager2DB=new LOPRTManager2DB();
          tLOPRTManager2DB.setSchema(mLOPRTManager2Schema);
          tLOPRTManager2DB.update();
          logger.debug("update print");

          return true;
      }

      public boolean controlPrt(LJAPaySchema tLJAPaySchema)
      {
          //添加打印控制--预览时插入打印管理数据，打印时更新打印数据状态为已打
          LOPRTManager2DB tLOPRTManager2DB=new LOPRTManager2DB();
          LOPRTManager2Set tLOPRTManager2Set=new LOPRTManager2Set();
          tLOPRTManager2DB.setOtherNo(tLJAPaySchema.getPayNo());
          tLOPRTManager2DB.setOtherNoType("05");//05 --- 交费收据号码
          tLOPRTManager2DB.setCode(PrintManagerBL.CODE_PINVOICE);
          tLOPRTManager2Set=tLOPRTManager2DB.query();
          if(tLOPRTManager2Set.size()==0)
          {
              LOPRTManager2Schema tLOPRTManager2Schema=getPrintData(tLJAPaySchema);
              if(tLOPRTManager2Schema==null)
              {
                  CError tError = new CError();
                  tError.moduleName = "outputXML";
                  tError.functionName = "getPrintData";
                  tError.errorMessage = "打印数据准备失败!";
                  this.mErrors .addOneError(tError) ;
                  return false;
              }
//              tLOPRTManager2DB=new LOPRTManager2DB();
//              tLOPRTManager2DB.setSchema(tLOPRTManager2Schema);
//              if(tLOPRTManager2DB.insert()==false)
//              {
//                  this.mErrors.copyAllErrors(tLOPRTManager2DB.mErrors);
//                  CError tError = new CError();
//                  tError.moduleName = "outputXML";
//                  tError.functionName = "getPrintData";
//                  tError.errorMessage = "打印数据保存失败!";
//                  this.mErrors .addOneError(tError) ;
//                  return false;
//              }

              mLOPRTManager2Schema.setSchema(tLOPRTManager2Schema);
          }
          else
          {
              mLOPRTManager2Schema.setSchema(tLOPRTManager2Set.get(1));
          }

          return true;
      }


      public LOPRTManager2Schema getPrintData(LJAPaySchema tLJAPaySchema)
      {
          LOPRTManager2Schema tLOPRTManager2Schema=new LOPRTManager2Schema();
         try
         {
             String tLimit=PubFun.getNoLimit(tLJAPaySchema.getManageCom());
             String prtSeqNo=PubFun1.CreateMaxNo("PRTSEQNO",tLimit);
             tLOPRTManager2Schema.setPrtSeq(prtSeqNo);
             tLOPRTManager2Schema.setOtherNo(tLJAPaySchema.getPayNo());
             tLOPRTManager2Schema.setOtherNoType("05");
             tLOPRTManager2Schema.setCode(PrintManagerBL.CODE_PINVOICE);
             tLOPRTManager2Schema.setManageCom(tLJAPaySchema.getManageCom());
             tLOPRTManager2Schema.setAgentCode(tLJAPaySchema.getAgentCode());
             tLOPRTManager2Schema.setReqCom(tLJAPaySchema.getManageCom());
             tLOPRTManager2Schema.setReqOperator(tLJAPaySchema.getOperator());
             tLOPRTManager2Schema.setPrtType("0");
             tLOPRTManager2Schema.setStateFlag("0");
             tLOPRTManager2Schema.setMakeDate(PubFun.getCurrentDate());
             tLOPRTManager2Schema.setMakeTime(PubFun.getCurrentTime());
         }
         catch(Exception ex)
         {
             return null;
          }
          return tLOPRTManager2Schema;
    }

      public static void main(String[] args) {
        PFeeInvoiceF1PBL PFeeInvoiceF1PBL1 = new PFeeInvoiceF1PBL();
        //1560.69=壹仟伍佰陆拾零元陆角玖分
//        String DSumMoney=PFeeInvoiceF1PBL1.getChnMoney(11101100.19).replaceFirst("人民币", "");
//        logger.debug(DSumMoney);
//        DSumMoney=PFeeInvoiceF1PBL1.getChnM("1110","万").replaceFirst("人民币", "");
//        logger.debug(DSumMoney);
//        texttag.add("XSumMoney",PubFun.setPrecision(tLJAPayDB.getSumActuPayMoney(),"0.00"));
        
        logger.debug(PubFun.setPrecision(0.00,"100.1"));
        java.text.DecimalFormat format=new java.text.DecimalFormat(".00"); 
        logger.debug(format.format(3)); 
        logger.debug(format.format(Double.parseDouble("500")));
      }
      /**
  	 * 添加仟、佰、拾等单位信息
  	 * 
  	 * @param strUnit
  	 *            String
  	 * @param digit
  	 *            String
  	 * @return String
  	 */
  	public static String getChnM(String strUnit, String digit) {
  		String sMoney = "";
  		boolean flag = false;

  		if (strUnit.equals("0000")) {
  			sMoney += "0";
  			return sMoney;
  		}
  		if (!strUnit.substring(0, 1).equals("0")) {
  			sMoney += getNum(strUnit.substring(0, 1)) + "仟";
  		} else {
  			sMoney += "0";
  			flag = true;
  		}
  		if (!strUnit.substring(1, 2).equals("0")) {
  			sMoney += getNum(strUnit.substring(1, 2)) + "佰";
  			flag = false;
  		} else {
  			if (!flag) {
  				sMoney += "0";
  				flag = true;
  			}
  		}
  		if (!strUnit.substring(2, 3).equals("0")) {
  			sMoney += getNum(strUnit.substring(2, 3)) + "拾";
  			flag = false;
  		} else {
  			if (!flag) {
  				sMoney += "0";
  				flag = true;
  			}
  		}
  		if (!strUnit.substring(3, 4).equals("0")) {
  			sMoney += getNum(strUnit.substring(3, 4));
  		} else {
  			if (!flag) {
  				sMoney += "0";
  				flag = true;
  			}
  		}

  		if (sMoney.substring(sMoney.length() - 1, sMoney.length()).equals("0")) {
  			sMoney = sMoney.substring(0, sMoney.length() - 1) + digit.trim();
//  					+ "0"; //为什么要在结尾加0
  		} else {
  			sMoney += digit.trim();
  		}
  		return sMoney;
  	}
  	private static String getNum(String value) {
		String sNum = "";
		Integer I = new Integer(value);
		int iValue = I.intValue();
		switch (iValue) {
		case 0:
			sNum = "零";
			break;
		case 1:
			sNum = "壹";
			break;
		case 2:
			sNum = "贰";
			break;
		case 3:
			sNum = "叁";
			break;
		case 4:
			sNum = "肆";
			break;
		case 5:
			sNum = "伍";
			break;
		case 6:
			sNum = "陆";
			break;
		case 7:
			sNum = "柒";
			break;
		case 8:
			sNum = "捌";
			break;
		case 9:
			sNum = "玖";
			break;
		}
		return sNum;
	}
  	public static String getChnMoney(double money) {
		String ChnMoney = "";
		String s0 = "";

		// 在原来版本的程序中，getChnMoney(585.30)得到的数据是585.29。

		if (money == 0.0) {
			ChnMoney = "人民币零元整";
			return ChnMoney;
		}

		if (money < 0) {
			s0 = "负";
			money *= (-1);
		}

		String sMoney = new DecimalFormat("0").format(money * 100);

		int nLen = sMoney.length();
		String sInteger;
		String sDot;
		if (nLen < 2) {
			// add by JL at 2004-9-14
			sInteger = "";
			if (nLen == 1) {
				sDot = "0" + sMoney.substring(nLen - 1, nLen);
			} else {
				sDot = "0";
			}
		} else {
			sInteger = sMoney.substring(0, nLen - 2);
			sDot = sMoney.substring(nLen - 2, nLen);
		}

		String sFormatStr = formatStr(sInteger);

		String s1 = getChnM(sFormatStr.substring(0, 4), "亿");

		String s2 = getChnM(sFormatStr.substring(4, 8), "万");

		String s3 = getChnM(sFormatStr.substring(8, 12), "");

		String s4 = getDotM(sDot);

		if (s1.length() > 0 && s1.substring(0, 1).equals("0")) {
			s1 = s1.substring(1, s1.length());
		}
		if (s1.length() > 0
				&& s1.substring(s1.length() - 1, s1.length()).equals("0")
				&& s2.length() > 0 && s2.substring(0, 1).equals("0")) {
			s1 = s1.substring(0, s1.length() - 1);
		}
		if (s2.length() > 0
				&& s2.substring(s2.length() - 1, s2.length()).equals("0")
				&& s3.length() > 0 && s3.substring(0, 1).equals("0")) {
			s2 = s2.substring(0, s2.length() - 1);
		}
		if (s4.equals("00")) {
			s4 = "";
			if (s3.length() > 0
					&& s3.substring(s3.length() - 1, s3.length()).equals("0")) {
				s3 = s3.substring(0, s3.length() - 1);
			}
		}
		if (s3.length() > 0
				&& s3.substring(s3.length() - 1, s3.length()).equals("0")
				&& s4.length() > 0 && s4.substring(0, 1).equals("0")) {
			s3 = s3.substring(0, s3.length() - 1);
		}
		if (s4.length() > 0
				&& s4.substring(s4.length() - 1, s4.length()).equals("0")) {
			s4 = s4.substring(0, s4.length() - 1);
		}
		if (s3.equals("0")) {
			s3 = "";
			s4 = "0" + s4;
		}

		ChnMoney =s0 + s1 + s2 + s3 + "元" + s4;
		if (ChnMoney.substring(0, 1).equals("0")) {
			ChnMoney = ChnMoney.substring(1, ChnMoney.length());
		}
		for (int i = 0; i < ChnMoney.length(); i++) {
			if (ChnMoney.substring(i, i + 1).equals("0")) {
				ChnMoney = ChnMoney.substring(0, i) + "零"
						+ ChnMoney.substring(i + 1, ChnMoney.length());
			}
		}

		if (sDot.substring(1, 2).equals("0")) {
			ChnMoney += "整";
		}

		return "人民币"+ChnMoney;
	}
	private static String formatStr(String sIn) {
		int n = sIn.length();
		String sOut = sIn;
		// int i = n % 4;

		for (int k = 1; k <= 12 - n; k++) {
			sOut = "0" + sOut;
		}
		return sOut;
	}
	private static String getDotM(String sIn) {
		String sMoney = "";
		if (!sIn.substring(0, 1).equals("0")) {
			sMoney += getNum(sIn.substring(0, 1)) + "角";
		} else {
			sMoney += "0";
		}
		if (!sIn.substring(1, 2).equals("0")) {
			sMoney += getNum(sIn.substring(1, 2)) + "分";
		} else {
			sMoney += "0";
		}

		return sMoney;
	}
}
