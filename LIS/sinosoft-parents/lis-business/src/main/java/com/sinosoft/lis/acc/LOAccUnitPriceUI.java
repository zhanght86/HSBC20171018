package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.vschema.*;
import  com.sinosoft.lis.schema.*;
import  com.sinosoft.lis.db.*;
import java.text.*;

public class LOAccUnitPriceUI implements BusinessService{
private static Logger logger = Logger.getLogger(LOAccUnitPriceUI.class);
    private  VData mInputData=new VData();
    private  MMap  map = new MMap();
    private String  mOperate="";
    private GlobalInput mGlobalInput = new GlobalInput();
   private LOAccUnitPriceSchema mLOAccUnitPriceSchema   = new LOAccUnitPriceSchema();
   private LOAccUnitPriceSet mLOAccUnitPriceSet=new  LOAccUnitPriceSet();
  private String CurrentDate = PubFun.getCurrentDate();
   private String CurrentTime = PubFun.getCurrentTime();
   
   DecimalFormat df6=new DecimalFormat("#.000000");
   DecimalFormat df4=new DecimalFormat("#.0000");
   
    public LOAccUnitPriceUI() {
    }
     public CErrors mErrors=new CErrors();

     public boolean submitData(VData cInputData, String cOperate)
         {


           //将操作数据拷贝到本类中
             this.mOperate = cOperate;
             
             logger.debug("mOperate:.:.:.:.:"+mOperate);
             //得到外部传入的数据,将数据备份到本类中
             if (!getInputData(cInputData))
             {
                 return false;
             }
             
             if(!mOperate.equals("CONFIRM")){
            	 
//            	进行业务处理
                 if (!dealData())
                 {
                     return false;
                 } 
             }else
             {
            	 String strState = mLOAccUnitPriceSchema.getState();
            	 
            	 
            	 LOAccUnitPriceDB tLOAccUnitPriceDB=new LOAccUnitPriceDB();
                 LOAccUnitPriceSet tLOAccUnitPriceSet=new LOAccUnitPriceSet();
                 SQLwithBindVariables sqlbv = new SQLwithBindVariables();
                 String sql="select * from LOAccUnitPrice where InsuAccNo='"+
                         "?InsuAccNo?"+
                         //"' and RiskCode='"+mLOAccUnitPriceSchema.getRiskCode()+
                         "' and SRateDate='"+
                         "?SRateDate?"+"'";
                 sqlbv.sql(sql);
                 sqlbv.put("InsuAccNo", mLOAccUnitPriceSchema.getInsuAccNo());
                 sqlbv.put("SRateDate", mLOAccUnitPriceSchema.getSRateDate());
                 tLOAccUnitPriceSet=tLOAccUnitPriceDB.executeQuery(sqlbv);
                 logger.debug(":::::::"+sql);
                  if(tLOAccUnitPriceSet.size()==0)
                  {
                  // @@错误处理
                   CError tError = new CError();
                   tError.moduleName = "LOAccUnitPriceUI";
                   tError.functionName = "getInputData";
                   tError.errorMessage = "原来的记录没有信息！";
                   this.mErrors.addOneError(tError);
                   return false;

                  }
                  mLOAccUnitPriceSchema.setSchema(tLOAccUnitPriceSet.get(1));
               mLOAccUnitPriceSchema.setMakeDate(tLOAccUnitPriceSet.get(1).getMakeDate());
               mLOAccUnitPriceSchema.setMakeTime(tLOAccUnitPriceSet.get(1).getMakeTime());
               mLOAccUnitPriceSchema.setModifyDate(CurrentDate);
               mLOAccUnitPriceSchema.setModifyTime(CurrentTime);
               
//               LOAccUnitPriceSchema lzLOAccUnitPriceSchema = new LOAccUnitPriceSchema();
//               lzLOAccUnitPriceSchema = tLOAccUnitPriceSet.get(1);
//               lzLOAccUnitPriceSchema.setModifyDate(CurrentDate);
//               lzLOAccUnitPriceSchema.setModifyTime(CurrentTime);
//               lzLOAccUnitPriceSchema.setState(strState);
               
               
//             修改为险种无关-20070906-鲁哲
               String lzInsuAccNo = mLOAccUnitPriceSchema.getInsuAccNo();
               
               LMRiskToAccDB tLMRisktoAccDB = new LMRiskToAccDB();
               tLMRisktoAccDB.setInsuAccNo(lzInsuAccNo);
               LMRiskToAccSet tLMRiskToAccSet = new LMRiskToAccSet();
               tLMRiskToAccSet = tLMRisktoAccDB.query();
               
               for(int i=1;i<=tLMRiskToAccSet.size();i++)
               {
              	 LOAccUnitPriceSchema lzLOAccUnitPriceSchema = new LOAccUnitPriceSchema();
              	 lzLOAccUnitPriceSchema.setSchema(mLOAccUnitPriceSchema);
//              	 lzLOAccUnitPriceSchema.setRiskCode(tLMRiskToAccSet.get(i).getRiskCode());
              	lzLOAccUnitPriceSchema.setState(strState);
              	 this.mLOAccUnitPriceSet.add(lzLOAccUnitPriceSchema);
               }
               //add by nandd 
//               LOAccUnitPriceTrace1Set tLOAccUnitPriceTrace1Set = getLOAccUnitPriceTrace1Set(
//					"0", mLOAccUnitPriceSet);//生效操作
//               map.put(tLOAccUnitPriceTrace1Set, "INSERT");
//               this.mLOAccUnitPriceSet.add(lzLOAccUnitPriceSchema);
             }
             
             
             //准备往后台的数据
             if (!prepareOutputData())
             {
                 return false;
             }
             LOAccUnitPriceBL tLOAccUnitPriceBL = new LOAccUnitPriceBL();
             logger.debug("Start LOAccUnitPriceUI Submit...");
             tLOAccUnitPriceBL.submitData(mInputData, mOperate);
             logger.debug("End LOAccUnitPriceUI Submit...");
             //如果有需要处理的错误，则返回
             if (tLOAccUnitPriceBL.mErrors.needDealError())
             {
                 // @@错误处理
                 this.mErrors.copyAllErrors(tLOAccUnitPriceBL.mErrors);
                 CError tError = new CError();
                 tError.moduleName = "LOAccUnitPriceUI";
                 tError.functionName = "submitData";
                 tError.errorMessage = "数据提交失败!";
                 this.mErrors.addOneError(tError);
                 return false;
             }
             mInputData = null;
             return true;
    }
     /**
		 * @param strOperateType
		 *            操作类型，现在只有一种操作需要我们记录其操作轨迹，0表示生效处理。
		 * @param tLOAccUnitPriceSet
		 *            本次操作的投连帐户计价记录集合
		 * @return 本次操作对应的投连帐户计价记录轨迹集合
		 */
//	public LOAccUnitPriceTrace1Set getLOAccUnitPriceTrace1Set(
//			String strOperateType, LOAccUnitPriceSet tLOAccUnitPriceSet) {
//		LOAccUnitPriceTrace1Set tLOAccUnitPriceTrace1Set;
//		if (tLOAccUnitPriceSet == null || tLOAccUnitPriceSet.size() < 0) {
//			tLOAccUnitPriceTrace1Set = null;
//		} else {
//			Reflections tRef = new Reflections();
//			tLOAccUnitPriceTrace1Set = new LOAccUnitPriceTrace1Set();
//			LOAccUnitPriceTrace1Schema tLOAccUnitPriceTrace1Schema;
//			String strCurrentDate = PubFun.getCurrentDate();
//			String strCurrentTime = PubFun.getCurrentTime();
//			for (int i = 1; i <= tLOAccUnitPriceSet.size(); i++) {
//				LOAccUnitPriceSchema tLOAccUnitPriceSchema = tLOAccUnitPriceSet
//						.get(i);
//				tLOAccUnitPriceTrace1Schema = new LOAccUnitPriceTrace1Schema();
//				tRef.transFields(tLOAccUnitPriceTrace1Schema,
//						tLOAccUnitPriceSchema);
//				tLOAccUnitPriceTrace1Schema.setPriceTraceNo(PubFun1
//						.CreateMaxNo("PriceTraceNo", 20));
//				tLOAccUnitPriceTrace1Schema.setOperator(mGlobalInput.Operator);
//				tLOAccUnitPriceTrace1Schema
//						.setManageCom(mGlobalInput.ManageCom);
//				tLOAccUnitPriceTrace1Schema.setMakeDate(strCurrentDate);
//				tLOAccUnitPriceTrace1Schema.setMakeTime(strCurrentTime);
//				tLOAccUnitPriceTrace1Schema.setOperateType(strOperateType);
//				tLOAccUnitPriceTrace1Set.add(tLOAccUnitPriceTrace1Schema);
//			}
//		}
//
//		return tLOAccUnitPriceTrace1Set;
//	}
	
	
    private boolean getInputData(VData cInputData)
      {
          // 全局变量
          mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                  "GlobalInput", 0));
          this.mLOAccUnitPriceSchema.setSchema((LOAccUnitPriceSchema) cInputData.
                                       getObjectByObjectName("LOAccUnitPriceSchema", 0));

         if(mOperate.equals("INSERT"))
       {
          mLOAccUnitPriceSchema.setModifyDate(CurrentDate);
          mLOAccUnitPriceSchema.setModifyTime(CurrentTime);
           mLOAccUnitPriceSchema.setMakeDate(CurrentDate);
          mLOAccUnitPriceSchema.setMakeTime(CurrentTime);
          
          LOAccUnitPriceDB llzzLOAccUnitPriceDB = new LOAccUnitPriceDB();
          llzzLOAccUnitPriceDB.setInsuAccNo(mLOAccUnitPriceSchema.getInsuAccNo());
          llzzLOAccUnitPriceDB.setStartDate(mLOAccUnitPriceSchema.getStartDate());
          if(llzzLOAccUnitPriceDB.query().size()>0)
          {
//        	 @@错误处理
              CError tError = new CError();
              tError.moduleName = "LOAccUnitPriceUI";
              tError.functionName = "getInputData";
              tError.errorMessage = "此记录已经存在！";
              this.mErrors.addOneError(tError);
              return false;  
          }
          
      }
           if(mOperate.equals("UPDATE")){
               LOAccUnitPriceDB tLOAccUnitPriceDB=new LOAccUnitPriceDB();
               LOAccUnitPriceSet tLOAccUnitPriceSet=new LOAccUnitPriceSet();
               SQLwithBindVariables sqlbv = new SQLwithBindVariables();
               String sql="select * from LOAccUnitPrice where InsuAccNo='"+
                       "?InsuAccNo?"+
                      // "' and RiskCode='"+mLOAccUnitPriceSchema.getRiskCode()+
                       "' and SRateDate='"+
                       "?SRateDate?"+"'";
               sqlbv.sql(sql);
               sqlbv.put("InsuAccNo", mLOAccUnitPriceSchema.getInsuAccNo());
               sqlbv.put("SRateDate", mLOAccUnitPriceSchema.getSRateDate());
               tLOAccUnitPriceSet=tLOAccUnitPriceDB.executeQuery(sqlbv);
               logger.debug(":::::::"+sql);
                if(tLOAccUnitPriceSet.size()==0)
                {
                // @@错误处理
                 CError tError = new CError();
                 tError.moduleName = "LOAccUnitPriceUI";
                 tError.functionName = "getInputData";
                 tError.errorMessage = "原来的记录没有信息！";
                 this.mErrors.addOneError(tError);
                 return false;

                }
             mLOAccUnitPriceSchema.setMakeDate(tLOAccUnitPriceSet.get(1).getMakeDate());
             mLOAccUnitPriceSchema.setMakeTime(tLOAccUnitPriceSet.get(1).getMakeTime());
             mLOAccUnitPriceSchema.setModifyDate(CurrentDate);
             mLOAccUnitPriceSchema.setModifyTime(CurrentTime);
   }
           
           
           if(mOperate.equals("DELETE")){
               LOAccUnitPriceDB tLOAccUnitPriceDB=new LOAccUnitPriceDB();
               LOAccUnitPriceSet tLOAccUnitPriceSet=new LOAccUnitPriceSet();
               SQLwithBindVariables sqlbv = new SQLwithBindVariables();
               String sql="select * from LOAccUnitPrice where InsuAccNo='"+
                       "?InsuAccNo?"+
                      // "' and RiskCode='"+mLOAccUnitPriceSchema.getRiskCode()+
                       "' and SRateDate='"+
                       "?SRateDate?"+"'";
               sqlbv.sql(sql);
               sqlbv.put("InsuAccNo", mLOAccUnitPriceSchema.getInsuAccNo());
               sqlbv.put("SRateDate", mLOAccUnitPriceSchema.getSRateDate());
               tLOAccUnitPriceSet=tLOAccUnitPriceDB.executeQuery(sqlbv);
               logger.debug(":::::::"+sql);
                if(tLOAccUnitPriceSet.size()==0)
                {
                // @@错误处理
                 CError tError = new CError();
                 tError.moduleName = "LOAccUnitPriceUI";
                 tError.functionName = "getInputData";
                 tError.errorMessage = "原来的记录没有信息！";
                 this.mErrors.addOneError(tError);
                 return false;

                }
             mLOAccUnitPriceSchema.setMakeDate(tLOAccUnitPriceSet.get(1).getMakeDate());
             mLOAccUnitPriceSchema.setMakeTime(tLOAccUnitPriceSet.get(1).getMakeTime());
             mLOAccUnitPriceSchema.setModifyDate(CurrentDate);
             mLOAccUnitPriceSchema.setModifyTime(CurrentTime);
   }
           

       
          if (mGlobalInput == null)
          {
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "LOAccUnitPriceUI";
              tError.functionName = "getInputData";
              tError.errorMessage = "没有得到足够的信息！";
              this.mErrors.addOneError(tError);
              return false;
          }
          return true;
      }

    private boolean dealData()
      {
    	//账户总资产
    	double douInsuTotalMoney = mLOAccUnitPriceSchema.getInsuTotalMoney();
    	//负债
    	double douLiabilities = mLOAccUnitPriceSchema.getLiabilities();
    	//公司投资单位数
    	double CompanyUnitCount = mLOAccUnitPriceSchema.getCompanyUnitCount();
    	//公司本次变动单位数
    	double ComChgUnitCount = mLOAccUnitPriceSchema.getComChgUnitCount();
    	//未实现利得营业税
    	double douOtherTax = 0;
    	
    	LDCodeDB lzLDCodeDB0 = new LDCodeDB();
    	lzLDCodeDB0.setCodeType("OtherTax");
    	lzLDCodeDB0.setCode(mLOAccUnitPriceSchema.getInsuAccNo());
    	LDCodeSet lzLDCodeSet0 = new LDCodeSet();
    	lzLDCodeSet0 = lzLDCodeDB0.query();
    	if(lzLDCodeSet0.size() == 0)
    	{
//    		 @@错误处理
            CError tError = new CError();
            tError.moduleName = "LOAccUnitPriceUI";
            tError.functionName = "getInputData";
            tError.errorMessage = "找不到对应的ldcode中的未实现利得营业税的定义！";
            this.mErrors.addOneError(tError);
            return false;
    	}
    	douOtherTax = Double.parseDouble(lzLDCodeSet0.get(1).getCodeAlias());
    	//账户资产管理费
    	double douAccasManageFee = 0;
    	//账户资产管理费的比例
    	double douguanlifeibili = 1;    	
    	LDCodeDB lzLDCodeDB = new LDCodeDB();
    	lzLDCodeDB.setCodeType("PropAccasManageFee");
    	lzLDCodeDB.setCode(mLOAccUnitPriceSchema.getInsuAccNo());
    	LDCodeSet lzLDCodeSet = new LDCodeSet();
    	lzLDCodeSet = lzLDCodeDB.query();
    	if(lzLDCodeSet.size() == 0)
    	{
//    		 @@错误处理
            CError tError = new CError();
            tError.moduleName = "LOAccUnitPriceUI";
            tError.functionName = "getInputData";
            tError.errorMessage = "找不到对应的ldcode中的资产管理费比例的定义！";
            this.mErrors.addOneError(tError);
            return false;
    	}
    	
    	douguanlifeibili = Double.parseDouble(lzLDCodeDB.query().get(1).getCodeAlias());
    	
    	
    	//距上一评估基准日的天数
    	int intdayscount = 1;
    	ExeSQL tExeSQL = new ExeSQL();
    	SSRS tStS = new SSRS();
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String strSQL = "select max(StartDate) from LOAccUnitPrice where InsuAccNo = '"+"?InsuAccNo?"+"'"
				+" and StartDate<'"+"?StartDate?"+"'";
    	sqlbv.sql(strSQL);
    	sqlbv.put("InsuAccNo", mLOAccUnitPriceSchema.getInsuAccNo());
    	sqlbv.put("StartDate", mLOAccUnitPriceSchema.getStartDate());
    	String lastStartDate = tExeSQL.getOneValue(sqlbv);
    	intdayscount = PubFun.calInterval(lastStartDate, mLOAccUnitPriceSchema.getStartDate(), "D");

    	douAccasManageFee = (douInsuTotalMoney-douLiabilities-douOtherTax)*intdayscount*douguanlifeibili/365;
    	logger.debug("这个管理费是:::::"+douAccasManageFee);
    	//投连帐户净值
    	double doujingzhi = douInsuTotalMoney-douLiabilities-douOtherTax-douAccasManageFee;
    	logger.debug("这个投连帐户净值是:::::"+doujingzhi); 
    	double doudanweicount = 0;
    	//String strRiskcode = mLOAccUnitPriceSchema.getRiskCode();
//    	String strInsuAccNo = mLOAccUnitPriceSchema.getInsuAccNo();
//    	String SQL = "select sum(UnitCount),count(*) from lcinsureacc where "
//    		+"insuaccno = '"+strInsuAccNo+"' "
//    		;
//    	tStS= tExeSQL.execSQL(SQL);
//    	String tStr =tStS.GetText(1, 1); 
        doudanweicount = mLOAccUnitPriceSchema.getCustomersUnitCount()+mLOAccUnitPriceSchema.getCompanyUnitCount();
        logger.debug("这个投连帐户总单位数是:::::"+doudanweicount);  
//        if(Double.parseDouble(tStS.GetText(1, 2))<=0)
//    	{
//    		CError tError = new CError();
//            tError.moduleName = "LOAccUnitPriceUI";
//            tError.functionName = "prepareData";
//            tError.errorMessage = "暂时没有该帐户的数据！";
//            this.mErrors.addOneError(tError);
//            return false;	
//    	}
        //单位价格 
        double doudanweijiage = doujingzhi/doudanweicount;
        logger.debug("这个投连帐户单位价格是:::::"+doudanweijiage); 
         
        double UnitPriceSell = doudanweijiage;
        //取得买入卖出的比例
        LDCodeDB lzLDCodeDB2 = new LDCodeDB();
    	lzLDCodeDB2.setCodeType("PropSellBuy");
    	lzLDCodeDB2.setCode(mLOAccUnitPriceSchema.getInsuAccNo());
    	LDCodeSet lzLDCodeSet2 = new LDCodeSet();
    	logger.debug("查询前");
    	lzLDCodeSet2 = lzLDCodeDB2.query();
    	if(lzLDCodeSet2.size() == 0)
    	{
//    		 @@错误处理
            CError tError = new CError();
            tError.moduleName = "LOAccUnitPriceUI";
            tError.functionName = "getInputData";
            tError.errorMessage = "找不到对应的ldcode中的买入卖出价格比例的定义！";
            this.mErrors.addOneError(tError);
            return false;
    	}
    	double UnitPriceBuy = UnitPriceSell*(1+Double.parseDouble(lzLDCodeSet2.get(1).getCodeAlias()));
    	/////////////根据扩张或者收缩来取小数位\\\\\\\\\\\\\\\\\\\
    	double newUnitPriceSell = Double.parseDouble(df4.format(UnitPriceSell));
    	logger.debug("查询后"+mLOAccUnitPriceSchema.getSKFlag());
    	if(mLOAccUnitPriceSchema.getSKFlag().equals("0")&&newUnitPriceSell<UnitPriceSell)
    	{
    		newUnitPriceSell = newUnitPriceSell+0.0001;
    	}
    	else if(mLOAccUnitPriceSchema.getSKFlag().equals("1")&&newUnitPriceSell>UnitPriceSell)
    	{
    		newUnitPriceSell = newUnitPriceSell-0.0001;    		
    	}
    	else if(newUnitPriceSell==UnitPriceSell)
    	{
    		if(mLOAccUnitPriceSchema.getSKFlag().equals("0"))
    		{
    			newUnitPriceSell = newUnitPriceSell+0.0001;
    		}
    		else
    		{
    			newUnitPriceSell = newUnitPriceSell-0.0001;
    		}
    	}
    	logger.debug("查询后"+newUnitPriceSell);
    	double newUnitPriceBuy = Double.parseDouble(df4.format(UnitPriceBuy));
    	if(mLOAccUnitPriceSchema.getSKFlag().equals("0")&&newUnitPriceBuy<UnitPriceBuy)
    	{
    		newUnitPriceBuy = newUnitPriceBuy+0.0001;
    	}
    	else if(mLOAccUnitPriceSchema.getSKFlag().equals("1")&&newUnitPriceBuy>UnitPriceBuy)
    	{
    		newUnitPriceBuy = newUnitPriceBuy-0.0001;    		
    	}
    	else if(newUnitPriceBuy==UnitPriceBuy)
    	{
    		if(mLOAccUnitPriceSchema.getSKFlag().equals("0"))
    		{
    			newUnitPriceBuy = newUnitPriceBuy+0.0001;
    		}
    		else
    		{
    			newUnitPriceBuy = newUnitPriceBuy-0.0001;
    		}
    	}
    	
        mLOAccUnitPriceSchema.setUnitPriceSell(newUnitPriceSell);
        mLOAccUnitPriceSchema.setUnitPriceBuy(newUnitPriceBuy);
        logger.debug("取后的sell"+mLOAccUnitPriceSchema.getUnitPriceSell());
        logger.debug("取后的buy"+mLOAccUnitPriceSchema.getUnitPriceBuy());
        mLOAccUnitPriceSchema.setOtherTax(douOtherTax);
        mLOAccUnitPriceSchema.setAccasManageFee(douAccasManageFee);
        mLOAccUnitPriceSchema.setCompanyUnitCount(CompanyUnitCount);
        
        
//        CError tError = new CError();
//        tError.moduleName = "LOAccUnitPriceUI";
//        tError.functionName = "prepareData";
//        tError.errorMessage = "管理费:"+df6.format(douAccasManageFee)+";帐户净值:"+df6.format(doujingzhi)+";总单位数:"+df6.format(doudanweicount)+";单位价格:"+df6.format(doudanweijiage);
//        this.mErrors.addOneError(tError);
//        return false;
    	
        //  boolean tReturn = false;
          //此处增加一些校验代码
          //double u=mLOAccUnitPriceSchema.getRedeemMoney()/
    	
    	//暂时注释了-鲁哲-20070824夜
         if(mLOAccUnitPriceSchema.getUnitPriceBuy()<=0)
        {
            CError tError = new CError();
              tError.moduleName = "LOAccUnitPriceUI";
              tError.functionName = "dealData";
              tError.errorMessage = "单位买入价格不能小于等于0！!";
              this.mErrors.addOneError(tError);
              return false;

         }
         if(mLOAccUnitPriceSchema.getUnitPriceSell()<=0)
      {
          CError tError = new CError();
            tError.moduleName = "LOAccUnitPriceUI";
            tError.functionName = "dealData";
            tError.errorMessage = "单位卖出价格不能小于等于0！";
            this.mErrors.addOneError(tError);
            return false;

       }
//         if(mLOAccUnitPriceSchema.getRedeemRate()<0||mLOAccUnitPriceSchema.getRedeemRate()>1)
//        {
//           CError tError = new CError();
//           tError.moduleName = "LOAccUnitPriceUI";
//           tError.functionName = "dealData";
//            tError.errorMessage = "赎回比例只能在0~1之间！";
//            this.mErrors.addOneError(tError);
//                 return false;
//        }
//        if(mLOAccUnitPriceSchema.getRedeemMoney()<0)
//          {
//              CError tError = new CError();
//                tError.moduleName = "LOAccUnitPriceUI";
//                tError.functionName = "dealData";
//                tError.errorMessage = "赎回金额不能小于0！";
//                this.mErrors.addOneError(tError);
//                return false;
//
//       }
//       if(!mLOAccUnitPriceSchema.getEndDate().equals("")){
//           if(mLOAccUnitPriceSchema.getEndDate().compareTo(mLOAccUnitPriceSchema.getStartDate())<0){
//               CError tError = new CError();
//              tError.moduleName = "LOAccUnitPriceUI";
//              tError.functionName = "dealData";
//              tError.errorMessage = "价格止期不能小于计价日期";
//              this.mErrors.addOneError(tError);
//              return false;
//
//           }

//       }
          //mLOAccUnitPriceSchema.get
       //   tReturn = true;
         
         
         
         //修改为险种无关-20070906-鲁哲
         String lzInsuAccNo = mLOAccUnitPriceSchema.getInsuAccNo();
         
         LMRiskToAccDB tLMRisktoAccDB = new LMRiskToAccDB();
         tLMRisktoAccDB.setInsuAccNo(lzInsuAccNo);
         LMRiskToAccSet tLMRiskToAccSet = new LMRiskToAccSet();
         tLMRiskToAccSet = tLMRisktoAccDB.query();
         
         for(int i=1;i<=tLMRiskToAccSet.size();i++)
         {
        	 LOAccUnitPriceSchema lzLOAccUnitPriceSchema = new LOAccUnitPriceSchema();
        	 lzLOAccUnitPriceSchema.setSchema(mLOAccUnitPriceSchema);
//        	 lzLOAccUnitPriceSchema.setRiskCode(tLMRiskToAccSet.get(i).getRiskCode());
        	 this.mLOAccUnitPriceSet.add(lzLOAccUnitPriceSchema);
         }
         
         //this.mLOAccUnitPriceSet.add(mLOAccUnitPriceSchema);
          return true;
    }

    private boolean prepareOutputData()
       {
           try
           {
        	   
        	   //这个时候注释了，以后还要呢！！-鲁哲-20070824
                int i = mLOAccUnitPriceSet.size();
                logger.debug("i===" + i + "-------riskcode=" +
                         mLOAccUnitPriceSet.get(1).getOperator());
                
                if(mOperate.equals("CONFIRM"))
                {
                	mOperate = "UPDATE";
                }

                map.put(mLOAccUnitPriceSet, mOperate);
                mInputData.clear();
                mInputData.add(map);

           }
           catch (Exception ex)
           {
               // @@错误处理
               CError tError = new CError();
               tError.moduleName = "LOAccUnitPriceUI";
               tError.functionName = "prepareData";
               tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
               this.mErrors.addOneError(tError);
               return false;
           }
           
          return true;
       }
	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
	public VData getResult() {
		// TODO Auto-generated method stub
		return mInputData;
	}
}
