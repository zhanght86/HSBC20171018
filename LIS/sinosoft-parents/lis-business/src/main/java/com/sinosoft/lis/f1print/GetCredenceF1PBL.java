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
import java.text.DecimalFormat;

public class GetCredenceF1PBL implements PrintService{
private static Logger logger = Logger.getLogger(GetCredenceF1PBL.class);
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
      public CErrors mErrors=new CErrors();

      private VData mResult = new VData();

      //业务处理相关变量
      /** 全局数据 */
      private GlobalInput mGlobalInput =new GlobalInput() ;
      //private LCPolSchema mLCPolSchema=new LCPolSchema();
      private LJAGetSchema mLJAGetSchema=new LJAGetSchema();
      private LOPRTManager2Schema mLOPRTManager2Schema = new LOPRTManager2Schema();
      private LOPRTManager2Schema aLOPRTManager2Schema = new LOPRTManager2Schema();
      private int index;
      private String mOperate="";
      private String mPolNo;
      private String mRiskName;
      private String mAppntName;
      private String mInsuredName;
      private String mBnfName;
      private String mPayItem;
      private String mMoneyW = "";
      private String mMoneyQ = "";
      private String mMoneyB = "";
      private String mMoneyS = "";
      private String mMoneyY = "";
      private String mMoneyJ = "";
      private String mMoneyF = "";

      public GetCredenceF1PBL() {
      }
      /**
 传输数据的公共方法
       */
      public boolean submitData(VData cInputData, String cOperate)
      {
    	  try{
	        if( (!cOperate.equals("PRINT"))&&(!cOperate.equals("REPRINT") )) {
	          buildError("submitData", "不支持的操作字符串");
	          return false;
	        }
	        mOperate = cOperate;
	        // 得到外部传入的数据，将数据备份到本类中
	        if( !getInputData(cInputData) ) {
	          return false;
	        }
	
	        mResult.clear();
	        if (mOperate.equals("PRINT"))
	        {
	          if (!dealData()){
	            return false;
	          }
	        }
	        else if (mOperate.equals("REPRINT"))
	        {
	          index=0;
	        }
	
	        if (index==0)
	        {
	          // 准备所有要打印的数据
	          if( !getPrintData()) {
	            return false;
	          }
	        }
	        else
	        {
	          mResult.add(mLJAGetSchema.getActuGetNo());
	        }
	        return true;
    	  }catch(Exception ex){
    			ex.printStackTrace();
    			CError tError = new CError();
    			tError.moduleName = "FinDayListBL";
    			tError.functionName = "VoucherReport";
    			tError.errorMessage = ex.toString();
    			this.mErrors.addOneError(tError);
    			logger.debug(ex.toString());
    			return false;
    	  }
      }


      /**
       * 根据前面的输入数据，进行BL逻辑处理
       * 如果在处理过程中出错，则返回false,否则返回true
       */
      private boolean dealData()
      {
        mLOPRTManager2Schema.setOtherNo(mLJAGetSchema.getActuGetNo());
        mLOPRTManager2Schema.setOtherNoType("04");
        LOPRTManager2DB tLOPRTManager2DB = new LOPRTManager2DB();
        LOPRTManager2Set tLOPRTManager2Set = new LOPRTManager2Set();
        tLOPRTManager2DB.setSchema(mLOPRTManager2Schema);
        tLOPRTManager2Set.set(tLOPRTManager2DB.query());
        if (tLOPRTManager2DB.mErrors.needDealError())
        {
          mErrors.copyAllErrors(tLOPRTManager2DB.mErrors);
          buildError("dealData", "在取得LOPRTManager2的数据时发生错误");
          return false;
        }
        index = tLOPRTManager2Set.size();
        return true;
      }

      /**
       * 从输入数据中得到所有对象
       * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
       */
      private boolean getInputData(VData cInputData)
      {
        //全局变量
        mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0));
        if (mOperate.equals("PRINT"))
          mLJAGetSchema.setSchema((LJAGetSchema)cInputData.getObjectByObjectName("LJAGetSchema",0));
        else if (mOperate.equals("REPRINT"))
        {
          LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
          tLOPRTManager2Schema.setSchema((LOPRTManager2Schema)cInputData.getObjectByObjectName("LOPRTManager2Schema",0));
          aLOPRTManager2Schema.setSchema(tLOPRTManager2Schema);
          mLJAGetSchema.setActuGetNo(tLOPRTManager2Schema.getOtherNo());
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

      private boolean print(String tPolNo)
      {
        mPolNo = tPolNo;
        LCPolSchema tLCPolSchema = new LCPolSchema();
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setContNo(mPolNo);
        if (tLCPolDB.getCount() <= 0)
        {
        	buildError("print", "保单不存在");
            return false;
        }
        else if(tLCPolDB.getCount()>1)
        {
        	String tempPolNo = tLCPolDB.query().get(1).getMainPolNo();
        	tLCPolDB.setPolNo(tempPolNo);
        	if(tLCPolDB.getCount()!=1){
        		buildError("print", "此笔交易没有主险信息");
        		return false;
        	}else{
        		tLCPolSchema.setSchema(tLCPolDB.query().get(1));
        	}
        }
        else
        {
          tLCPolSchema.setSchema(tLCPolDB.query().get(1));
        }
        mAppntName = tLCPolSchema.getAppntName();
        mInsuredName = tLCPolSchema.getInsuredName();
        LMRiskDB tLMRiskDB = new LMRiskDB();
        tLMRiskDB.setRiskCode(tLCPolSchema.getRiskCode());
        if (!tLMRiskDB.getInfo())
        {
          mErrors.copyAllErrors(tLMRiskDB.mErrors);
          buildError("Print", "此险种没有对应的险种名称信息");
          return false;
        }
        mRiskName = tLMRiskDB.getRiskName();
        LCBnfDB tLCBnfDB = new LCBnfDB();
        tLCBnfDB.setContNo(mPolNo);
        tLCBnfDB.setBnfType("0");
        LCBnfSet tLCBnfSet = new LCBnfSet();
        tLCBnfSet.set(tLCBnfDB.query());
        if (tLCBnfDB.mErrors.needDealError())
        {
          mErrors.copyAllErrors(tLCBnfDB.mErrors);
          buildError("Print", "在取得受益人时发生错误");
          return false;
        }
        if (tLCBnfSet.size()<=0)
        {
          mBnfName = "法定";
        }
        else
        {
          mBnfName = tLCBnfSet.get(1).getName();

          for (int i=1; i<tLCBnfSet.size(); i++)
          {
            mBnfName += "、" + tLCBnfSet.get(i+1).getName();
          }
        }

        return true;
      }

      /**
       * 集体退费数据
       * @param tGrpPolNo
       * @return
       */
      private boolean printGrp(String tGrpContNo)
      {
        mPolNo=tGrpContNo; //模版元素所用
        String mGrpContNo = tGrpContNo;
        LCGrpPolSchema tLCGrpPolSchema = new LCGrpPolSchema();
        LCGrpPolDB tLCGrpPolDB = new LCGrpPolDB();
        tLCGrpPolDB.setGrpContNo(mGrpContNo);
        LCGrpPolSet tLCGrpPolSet = new LCGrpPolSet();
        tLCGrpPolSet.set(tLCGrpPolDB.query());
        if (tLCGrpPolSet.size()<=0)
        {
        	buildError("printGrp", "查询不到团险信息");
            return false;
        }
        tLCGrpPolSchema.setSchema(tLCGrpPolSet.get(1));
        mAppntName = tLCGrpPolSchema.getGrpName();
        mInsuredName = ""; //被保人姓名
        mBnfName = ""; //受益人姓名
        LMRiskDB tLMRiskDB = new LMRiskDB();
        tLMRiskDB.setRiskCode(tLCGrpPolSchema.getRiskCode());
        if (!tLMRiskDB.getInfo())
        {
          mErrors.copyAllErrors(tLMRiskDB.mErrors);
          buildError("Print", "此险种没有对应的险种名称");
          return false;
        }
        mRiskName = tLMRiskDB.getRiskName();

        return true;
      }

      private boolean getPrintData()
      {
//        int i=0,j,m;
//        boolean tflag = true;
//        String tAppnt = "";
        String tOtherNoType = "";
        LJAGetDB tLJAGetDB = new LJAGetDB();
        tLJAGetDB.setSchema(mLJAGetSchema);
        
      //xuyunpeng add	
		String tSql = "select currency from LJAGet where 1 =1 "
			+ " and ActuGetNo='"
			+ "?ActuGetNo?'";

	logger.debug("--按保单、理赔类型进行的分组信息，分别进行计算:"+tSql);
	SQLwithBindVariables sqlbv5 =new SQLwithBindVariables();
	sqlbv5.sql(tSql);
	sqlbv5.put("ActuGetNo", mLJAGetSchema.getActuGetNo());
	ExeSQL tExeSQL = new ExeSQL();
	SSRS tSSRS = tExeSQL.execSQL(sqlbv5);
		
		if(tSSRS!=null){
			String[][] aCurrency =tSSRS.getAllData();
			tLJAGetDB.setCurrency(aCurrency[0][0]);
			
		}
        if(!tLJAGetDB.getInfo()){
          mErrors.copyAllErrors(tLJAGetDB.mErrors);
          buildError("getPrintData", "不存在相应的实付信息，请检查输入是否正确");
          return false;
        }
        tOtherNoType = tLJAGetDB.getOtherNoType();
//        if (tOtherNoType.equals("1"))
//        {
//          LDGrpDB tLDGrpDB = new LDGrpDB();
//          tLDGrpDB.setGrpNo(tLJAGetDB.getAppntNo());
//          if(!tLDGrpDB.getInfo()){
//            mErrors.copyAllErrors(tLDGrpDB.mErrors);
//            buildError("outputXML", "在取得LDGrp的数据时发生错误");
//            return false;
//          }
//          tAppnt=tLDGrpDB.getGrpName();
//        }
//        else{
//          LDPersonDB tLDPersonDB = new LDPersonDB();
//          tLDPersonDB.setCustomerNo(tLJAGetDB.getAppntNo());
//          if(!tLDPersonDB.getInfo()){
//            mErrors.copyAllErrors(tLDPersonDB.mErrors);
//            buildError("outputXML", "在取得LDPerson的数据时发生错误");
//            return false;
//          }
//          tAppnt=tLDPersonDB.getName();
//        }
//        String DSumGetMoney ="";
//        DSumGetMoney = PubFun.getChnMoney(tLJAGetDB.getSumGetMoney());
//
//        LAAgentDB tLAAgentDB = new LAAgentDB();
//        tLAAgentDB.setAgentCode(tLJAGetDB.getAgentCode());
//        if (!tLAAgentDB.getInfo())
//        {
// //        mErrors.copyAllErrors(tLAAgentDB.mErrors);
// //         buildError("outputXML", "在取得LAAgent的数据时发生错误");
// //         return false;
//          tflag = false ;
//        }
//
//        ListTable tlistTable = new ListTable();
//        String strArr[] = null;
//        tlistTable.setName("GET");
        if (tOtherNoType.equals("1")||tOtherNoType.equals("2"))
        {
          LJAGetDrawSchema tLJAGetDrawSchema = new LJAGetDrawSchema();
          LJAGetDrawDB tLJAGetDrawDB = new LJAGetDrawDB();
          tLJAGetDrawDB.setActuGetNo(tLJAGetDB.getActuGetNo());
          LJAGetDrawSet tLJAGetDrawSet = new LJAGetDrawSet();
          tLJAGetDrawSet.set(tLJAGetDrawDB.query());
          if(tLJAGetDrawSet.size()<=0){
        	  buildError("getPrintData", "查询不到这笔交易对应的生存领取实付信息");
        	  return false;
          }
          tLJAGetDrawSchema.setSchema(tLJAGetDrawSet.get(1));
          if (tOtherNoType.equals("2"))
          {
            if (!print(tLJAGetDrawSchema.getContNo()))
            {
            	//zy 2009-07-14  增加团单的处理
            	if(!printGrp(tLJAGetDrawSchema.getGrpContNo()))
            	{
	              buildError("print", "查询不到这笔交易相应的保单信息");
	              return false;
            	}
            }
          }
          mPayItem = "生存领取";

//          for (i=0;i<tLJAGetDrawSet.size();i++)
//          {
//            tLJAGetDrawSchema = new LJAGetDrawSchema();
//            tLJAGetDrawSchema.setSchema(tLJAGetDrawSet.get(i+1));
//            strArr = new String[3];
//            strArr[0]=tLJAGetDrawSchema.getPolNo();
//            strArr[1]=tLJAGetDrawSchema.getFeeOperationType();
//            strArr[2]=String.valueOf(tLJAGetDrawSchema.getGetMoney());
//            tlistTable.add(strArr);
//          }
        }
        else if (tOtherNoType.equals("10"))
        {
          LJAGetEndorseSchema tLJAGetEndorseSchema = new LJAGetEndorseSchema();
          LJAGetEndorseDB tLJAGetEndorseDB = new LJAGetEndorseDB();
          tLJAGetEndorseDB.setActuGetNo(tLJAGetDB.getActuGetNo());
          LJAGetEndorseSet tLJAGetEndorseSet = new LJAGetEndorseSet();
          tLJAGetEndorseSet.set(tLJAGetEndorseDB.query());
          if(tLJAGetEndorseSet.size()<=0){
        	  buildError("getPrintData", "没有查询到修改补退费信息");
        	  return false;
          }
          tLJAGetEndorseSchema.setSchema(tLJAGetEndorseSet.get(1));
          if(tLJAGetEndorseSchema.getGrpPolNo().equals("00000000000000000000")){
              if (!print(tLJAGetEndorseSchema.getContNo()))
                 {
                   buildError("print", "在获取打印数据时发生错误");
                   return false;
                 }
              mPayItem = "批改退费";
          }else {
            if (!printGrp(tLJAGetEndorseSchema.getGrpContNo()))
               {
                 buildError("print", "在获取打印数据时发生错误");
                 return false;
               }
              mPayItem = "批改退费";
          }
//          for (i=0;i<tLJAGetEndorseSet.size();i++)
//          {
//            tLJAGetEndorseSchema = new LJAGetEndorseSchema();
//            tLJAGetEndorseSchema.setSchema(tLJAGetEndorseSet.get(i+1));
//            strArr = new String[3];
//            strArr[0]=tLJAGetEndorseSchema.getPolNo();
//            strArr[1]=tLJAGetEndorseSchema.getFeeOperationType();
//            strArr[2]=String.valueOf(tLJAGetEndorseSchema.getGetMoney());
//            tlistTable.add(strArr);
//          }
        }
        else if (tOtherNoType.equals("4"))
        {
          LJAGetTempFeeSchema tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
          LJAGetTempFeeDB tLJAGetTempFeeDB = new LJAGetTempFeeDB();
          tLJAGetTempFeeDB.setActuGetNo(tLJAGetDB.getActuGetNo());
          LJAGetTempFeeSet tLJAGetTempFeeSet = new LJAGetTempFeeSet();
          tLJAGetTempFeeSet.set(tLJAGetTempFeeDB.query());
          if(tLJAGetTempFeeSet.size()<=0){
        	  buildError("getPrintData", "没有查询到暂收退费信息");
        	  return false;
          }
          tLJAGetTempFeeSchema.setSchema(tLJAGetTempFeeSet.get(1));
          LJTempFeeDB tLJTempFeeDB = new LJTempFeeDB();
          tLJTempFeeDB.setTempFeeNo(tLJAGetTempFeeSchema.getTempFeeNo());
          tLJTempFeeDB.setTempFeeType(tLJAGetTempFeeSchema.getTempFeeType());
          tLJTempFeeDB.setRiskCode(tLJAGetTempFeeSchema.getRiskCode());
          if (!tLJTempFeeDB.getInfo())
          {
            mErrors.copyAllErrors(tLJTempFeeDB.mErrors);
            buildError("getPrintData", "在取得LJTempFee的数据时发生错误");
            return false;
          }
          mPolNo = tLJTempFeeDB.getOtherNo();
          LMRiskDB tLMRiskDB = new LMRiskDB();
          tLMRiskDB.setRiskCode(tLJAGetTempFeeSchema.getRiskCode());
          // zy 2009-07-14 由于暂收收费可能针对合同收费所以险种信息可能是000000
          if (!tLMRiskDB.getInfo())
          {
//            mErrors.copyAllErrors(tLMRiskDB.mErrors);
//            buildError("getPrintData", "在取得险种名称时发生错误");
//            return false;
        	  mRiskName="";
          }
          else
          {
        	  mRiskName = tLMRiskDB.getRiskName();
          }
          mPayItem = "暂交费退费";
//          for (i=0;i<tLJAGetTempFeeSet.size();i++)
//          {
//            tLJAGetTempFeeSchema = new LJAGetTempFeeSchema();
//            tLJAGetTempFeeSchema.setSchema(tLJAGetTempFeeSet.get(i+1));
//            strArr = new String[3];
//            strArr[0]="";
//            strArr[1]=tLJAGetTempFeeSchema.getFeeOperationType();
//            strArr[2]=String.valueOf(tLJAGetTempFeeSchema.getGetMoney());
//            tlistTable.add(strArr);
//          }
        }
        else if (tOtherNoType.equals("5"))
        {
          mPayItem = "理赔款";
          LLClaimDB tLLClaimDB = new LLClaimDB();
          tLLClaimDB.setClmNo(tLJAGetDB.getOtherNo());
          if (tLLClaimDB.getInfo())
          {
            LLCaseDB tLLCaseDB = new LLCaseDB();
            tLLCaseDB.setCaseNo(tLJAGetDB.getOtherNo());
            LLCaseSet tLLCaseSet = new LLCaseSet();
            tLLCaseSet=tLLCaseDB.query();
            if (tLLCaseSet.size()>0)
            {
              mInsuredName=tLLCaseSet.get(1).getCustomerName();
            }
          }

        }
        else if (tOtherNoType.equals("6"))
        {
          if (!print(tLJAGetDB.getOtherNo()))
          {
        	  if(!printGrp(tLJAGetDB.getOtherNo()))
        	  {
	            buildError("print", "对应的合同信息不存在，请核实");
	            return false;
        	  }
          }
          mPayItem = "退费";
//          LJAGetOtherSchema tLJAGetOtherSchema;
//          LJAGetOtherDB tLJAGetOtherDB = new LJAGetOtherDB();
//          tLJAGetOtherDB.setActuGetNo(tLJAGetDB.getActuGetNo());
//          LJAGetOtherSet tLJAGetOtherSet = new LJAGetOtherSet();
//          tLJAGetOtherSet.set(tLJAGetOtherDB.query());
//          for (i=0;i<tLJAGetOtherSet.size();i++)
//          {
//            tLJAGetOtherSchema = new LJAGetOtherSchema();
//            tLJAGetOtherSchema.setSchema(tLJAGetOtherSet.get(i+1));
//            strArr = new String[3];
//            strArr[0]="";
//            strArr[1]=tLJAGetOtherSchema.getFeeOperationType();
//            strArr[2]=String.valueOf(tLJAGetOtherSchema.getGetMoney());
//            tlistTable.add(strArr);
//          }
        }
        else if (tOtherNoType.equals("7"))
        {
          if (!print(tLJAGetDB.getOtherNo()))
          {
        	  if(!printGrp(tLJAGetDB.getOtherNo()))
        		{
		            buildError("print", "对应的合同信息不存在，请核实");
		            return false;
        		}
          }
          mPayItem = "红利给付";
//          LJABonusGetSchema tLJABonusGetSchema;
//          LJABonusGetDB tLJABonusGetDB = new LJABonusGetDB();
//          tLJABonusGetDB.setActuGetNo(tLJAGetDB.getActuGetNo());
//          LJABonusGetSet tLJABonusGetSet = new LJABonusGetSet();
//          tLJABonusGetSet.set(tLJABonusGetDB.query());
//          for (i=0;i<tLJABonusGetSet.size();i++)
//          {
//            tLJABonusGetSchema = new LJABonusGetSchema();
//            tLJABonusGetSchema.setSchema(tLJABonusGetSet.get(i+1));
//            strArr = new String[3];
//            strArr[0]="";
//            strArr[1]=tLJABonusGetSchema.getFeeOperationType();
//            strArr[2]=String.valueOf(tLJABonusGetSchema.getGetMoney());
//            tlistTable.add(strArr);
//          }
        }

        else if (tOtherNoType.equals("8"))
        {
            if (!printGrp(tLJAGetDB.getOtherNo()))
            {
              buildError("print", "在获取打印数据时发生错误");
              return false;
            }
            mPayItem = "退费";
        }
//        j = 8 - i;
//        for (m=0;m<j;m++)
//        {
//            strArr = new String[3];
//            strArr[0]="";
//            strArr[1]="";
//            strArr[2]="";
//            tlistTable.add(strArr);
//        }
//        strArr = new String[3];
//        strArr[0] = "PolNo"; strArr[1] ="FeeOperationType";strArr[2] = "GetMoney";
//
        String Money = new DecimalFormat(".00").format((double)(tLJAGetDB.getSumGetMoney()));

        ListTable tlistTable = new ListTable();
        String strArr[] = null;
        tlistTable.setName("GET");
        strArr = new String[4];
        strArr[0]=mPayItem;
        strArr[1]=Money;
        strArr[2]="";
        strArr[3]="";
        tlistTable.add(strArr);
        String CurrentDate = PubFun.getCurrentDate();
        int a = CurrentDate.lastIndexOf("-");
        String tYear = CurrentDate.substring(0,4);
        String tMonth = CurrentDate.substring(5,a);
        String tDay = CurrentDate.substring(a+1,CurrentDate.length());

        //String Money = String.valueOf(tLJAGetDB.getSumGetMoney());
        StringBuffer tMoney = new StringBuffer(Money);
        StringBuffer ReMoney = tMoney.reverse();
        String Unit;
        for (int b=0 ; b<ReMoney.length() ; b++)
        {
          if (b!=2)
            Unit=getNum(String.valueOf(ReMoney.charAt(b)));
          else
            Unit = "";
          switch(b)
          {
            case 0:
              mMoneyF = Unit;
              break;
            case 1:
              mMoneyJ = Unit;
              break;
            case 2:
              break;
            case 3:
              mMoneyY = Unit;
              break;
            case 4:
              mMoneyS = Unit;
              break;
            case 5:
              mMoneyB = Unit;
              break;
            case 6:
              mMoneyQ = Unit;
              break;
          }
        }

		if(Money.length()>7){
		  String strWan = Money.substring(0,Money.length()-7);
		  mMoneyW = this.getChnMoney(Double.parseDouble(strWan));
		}

        LDCodeDB tLDCodeDB = new LDCodeDB();
        tLDCodeDB.setCodeType("paymode");
        tLDCodeDB.setCode(tLJAGetDB.getPayMode());
        tLDCodeDB.getInfo();
//        if (!tLDCodeDB.getInfo())
//        {
//          mErrors.copyAllErrors(tLDCodeDB.mErrors);
//          buildError("getPrintData", "在取得交费方式时发生错误");
//          return false;
//        }

        TextTag texttag=new TextTag();//新建一个TextTag的实例
        XmlExport xmlexport=new XmlExport();//新建一个XmlExport的实例
        xmlexport.createDocument("GetCredence.vts","printer");//最好紧接着就初始化xml文档
        texttag.add("PolNo",mPolNo);
        texttag.add("RiskName",mRiskName);
        texttag.add("AppntName",mAppntName);
        texttag.add("InsuredName",mInsuredName);
        texttag.add("BnfName",mBnfName);
        texttag.add("PayMode",tLDCodeDB.getCodeName());
        texttag.add("Year",tYear);
        texttag.add("Month",tMonth);
        texttag.add("Day",tDay);
        texttag.add("MoneyF",mMoneyF);
        texttag.add("MoneyJ",mMoneyJ);
        texttag.add("MoneyY",mMoneyY);
        texttag.add("MoneyS",mMoneyS);
        texttag.add("MoneyB",mMoneyB);
        texttag.add("MoneyQ",mMoneyQ);
        texttag.add("MoneyW",mMoneyW);
        texttag.add("ActuGetNo",mLJAGetSchema.getActuGetNo());

//        texttag.add("DSumGetMoney",DSumGetMoney);
//        texttag.add("SumGetMoney",tLJAGetDB.getSumGetMoney());
//        texttag.add("Operator",tLJAGetDB.getOperator());
//        if (tflag == true)
//          texttag.add("AgentCode",tLAAgentDB.getName());
//        else
//          texttag.add("AgentCode","");
        if (texttag.size()>0)
        xmlexport.addTextTag(texttag);
        xmlexport.addListTable(tlistTable, strArr);
//        xmlexport.outputDocumentToFile("e:\\","test");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);
        if (mOperate.equals("PRINT"))
        {
          LOPRTManager2Schema tLOPRTManager2Schema = new LOPRTManager2Schema();
          tLOPRTManager2Schema.setCode("13");
          tLOPRTManager2Schema.setOtherNo(mLJAGetSchema.getActuGetNo());
          tLOPRTManager2Schema.setOtherNoType("04");
          mResult.addElement(tLOPRTManager2Schema);
        }
        else if (mOperate.equals("REPRINT"))
        {
          mResult.addElement(aLOPRTManager2Schema);
        }
        return true;

      }

      private static String getNum(String value)
      {
        String sNum = "";
        Integer I = new Integer(value);
        int iValue = I.intValue();
        switch(iValue)
        {
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

      public CErrors getErrors() {
        return mErrors;
      }

	  private String formatStr(String sIn)
	  {
		int n = sIn.length();
		String sOut = sIn;
		int i = n%4;

		for(int k = 1; k <= 12-n; k++)
		{
		  sOut = "0" + sOut;
		}
		return sOut;
	  }
	  private String getChnM(String strUnit, String digit)
	  {
		  String sMoney = "";
		  boolean flag = false;

		  if( strUnit.equals( "0000" ))
		  {
			sMoney += "0";
			return sMoney;
		  }
		  if( !strUnit.substring( 0, 1 ).equals( "0" ))
			sMoney += getNum(strUnit.substring( 0, 1 )) + "仟";
		  else
		  {
			sMoney += "0";
			flag = true;
		  }
		  if( !strUnit.substring( 1, 2 ).equals( "0" ))
		  {
			sMoney += getNum(strUnit.substring( 1, 2 )) + "佰";
			flag = false;
		  }
		  else
		  {
			if( flag == false )
			{
			  sMoney += "0";
			  flag = true;
			}
		  }
		  if( !strUnit.substring( 2, 3 ).equals( "0" ))
		  {
			sMoney += getNum(strUnit.substring( 2, 3 )) + "拾";
			flag = false;
		  }
		  else
		  {
			if( flag == false )
			{
			  sMoney += "0";
			  flag = true;
			}
		  }
		  if( !strUnit.substring( 3, 4 ).equals( "0" ))
			sMoney += getNum(strUnit.substring( 3, 4 ));
		  else
		  {
			if( flag == false )
			{
			  sMoney += "0";
			  flag = true;
			}
		  }

		  if( sMoney.substring( sMoney.length()-1, sMoney.length() ).equals( "0" ))
			sMoney = sMoney.substring( 0, sMoney.length()-1 ) + digit.trim() + "0";
		  else
			sMoney += digit.trim();
		  return sMoney;
	  }

	  public String getChnMoney(double money)
		{
		  String ChnMoney = "";
		  String s0 = "";

		  if( money == 0.0 )
		  {
			ChnMoney = "";
			return ChnMoney;
		  }

		  if (money < 0 )
		  {
			s0 = "负";
			money = money * (-1);
		  }

		  String sMoney = new DecimalFormat("0").format(money * 100);

		  int	nLen = sMoney.length();
		  String sInteger;
		  String sDot;
		  if(nLen<2)
		  { //add by JL at 2004-9-14
			sInteger="";
			if(nLen==1)
			{
			  sDot = "0"+sMoney.substring( nLen - 1, nLen );
			}else{
			  sDot="0";
			}
		  }else{
			sInteger = sMoney.substring( 0, nLen - 2 );
			sDot = sMoney.substring( nLen - 2, nLen );
		  }

		  String sFormatStr = formatStr( sInteger );

		  String s1 = this.getChnM( sFormatStr.substring( 0, 4 ), "亿" );

		  String s2 = this.getChnM( sFormatStr.substring( 4, 8 ), "万" );

		  String s3 = this.getChnM( sFormatStr.substring( 8, 12 ), "" );

		  String s4 = this.getDotM( sDot );

		  if( s1.length() > 0 && s1.substring( 0, 1 ).equals( "0" )) s1 = s1.substring( 1, s1.length() );
		  if( s1.length() > 0 && s1.substring( s1.length()-1, s1.length()).equals( "0" )
			  && s2.length() > 0 && s2.substring( 0, 1 ).equals( "0" ))
			s1 = s1.substring( 0, s1.length()-1 );
		  if( s2.length() > 0 && s2.substring( s2.length()-1, s2.length()).equals( "0" )
			  && s3.length() > 0 && s3.substring( 0, 1 ).equals( "0" ))
			s2 = s2.substring( 0, s2.length()-1 );
		  if( s4.equals( "00" ))
		  {
			s4 = "";
			if( s3.length() > 0 && s3.substring( s3.length()-1, s3.length()).equals( "0" ))
			  s3 = s3.substring( 0, s3.length()-1 );
		  }
		  if( s3.length() > 0 && s3.substring( s3.length()-1, s3.length()).equals( "0" )
			  && s4.length() > 0 && s4.substring( 0, 1 ).equals( "0" ))
			s3 = s3.substring( 0, s3.length()-1 );
		  if( s4.length() > 0 && s4.substring( s4.length()-1, s4.length()).equals( "0" ))
			s4 = s4.substring( 0, s4.length()-1 );
		  if( s3.equals( "0" ))
		  {
			s3 = "";
			s4 = "0" + s4;
		  }

		  ChnMoney = s0 + s1 + s2 + s3 + "" + s4;
		  if( ChnMoney.substring(0,1).equals( "0" )) ChnMoney = ChnMoney.substring( 1, ChnMoney.length() );
		  for (int i = 0; i < ChnMoney.length(); i++)
		  {
			if( ChnMoney.substring( i, i+1 ).equals( "0" ))
			  ChnMoney = ChnMoney.substring( 0, i ) + "零" + ChnMoney.substring( i+1, ChnMoney.length() );
		  }

		  return ChnMoney;
	  }

	  private  String getDotM( String sIn )
	  {
		String sMoney = "";
		if( !sIn.substring( 0, 1 ).equals( "0" ))
	      sMoney += getNum(sIn.substring( 0, 1 )) + "角";
        else
		  sMoney += "0";
		if( !sIn.substring( 1, 2 ).equals( "0" ))
	      sMoney += getNum(sIn.substring( 1, 2 )) + "分";
        else
	      sMoney += "0";

	    return sMoney;
      }

      public static void main(String[] args) {
        GetCredenceF1PBL getCredenceF1PBL1 = new GetCredenceF1PBL();
		logger.debug(getCredenceF1PBL1.getChnMoney(14578526.89));
      }
}
