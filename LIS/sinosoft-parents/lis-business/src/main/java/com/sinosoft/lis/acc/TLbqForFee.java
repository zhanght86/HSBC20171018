package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;

import com.sinosoft.lis.f1print.BqNameFun;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
/**
*投连保全计算手续费
 */

public class TLbqForFee {
private static Logger logger = Logger.getLogger(TLbqForFee.class);

    public TLbqForFee() {
    }


//  险种编码
	String strRiskcode;
	//险种生效日期
	String strCValiDate ;
	//合同号码
	String strContNo ;
	//批改类型
	String strEdorType ;
	//得到统计起期
	String strSDate ;
	//批改生效日期
	String strEdorValiDate ;
	//
	String strEdorNo;

	double mMainMoney = 0;
	LMEdorCalSet tLMEdorCalSet = new LMEdorCalSet();
	private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
	LCPolSchema lzLCPolSchema = new LCPolSchema();

    /**
     * 计算保全手续费入口函数
     * 适用范围：SPUL和RPUL的AR,TI,PA.
     * 鲁哲*_*
     * @version 1.0
     */

    public double GetCalFee(double MainMoney,String EdorNo) throws Exception
    {
    	strEdorNo = EdorNo;
    	LPEdorItemDB lzLPEdorItemDB = new LPEdorItemDB();
    	lzLPEdorItemDB.setEdorNo(strEdorNo);
    	mLPEdorItemSchema = lzLPEdorItemDB.query().get(1);

    	strEdorType = mLPEdorItemSchema.getEdorType();
    	strContNo = mLPEdorItemSchema.getContNo();

    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String SQL = "select a.* from lcpol a,lmriskapp b where a.contno = '"+ "?strContNo?" +"' "
    				+" and a.riskcode = b.riskcode and b.risktype3 = '3'";
    	sqlbv.sql(SQL);
    	sqlbv.put("strContNo", strContNo);

    	LCPolDB lzLCPolDB = new LCPolDB();
    	lzLCPolSchema = lzLCPolDB.executeQuery(sqlbv).get(1);
    	strRiskcode = lzLCPolSchema.getRiskCode();
    	strCValiDate = lzLCPolSchema.getCValiDate();

//    	LCContDB lzLCContDB = new LCContDB();
//    	lzLCContDB.setContNo(strContNo);
//    	strCValiDate = lzLCContDB.query().get(1).getCustomGetPolDate();


    	strEdorValiDate = mLPEdorItemSchema.getEdorValiDate();
    	strSDate = GetStartDate(strCValiDate,strEdorValiDate);
    	mMainMoney = MainMoney;


    	LMEdorCalDB tLMEdorCalDB =  new LMEdorCalDB();
    	tLMEdorCalDB.setRiskCode(strRiskcode);
    	tLMEdorCalDB.setRiskVer("2002");
        tLMEdorCalDB.setDutyCode("000000");
        tLMEdorCalDB.setEdorType(strEdorType);  //退保
        tLMEdorCalDB.setCalType("CalFee");
        tLMEdorCalSet = tLMEdorCalDB.query();
        if (tLMEdorCalDB.mErrors.needDealError()){
            CError.buildErr(this, "退保区分类型计算代码查询失败!");
            throw new Exception();
        }
        if (tLMEdorCalSet == null || tLMEdorCalSet.size() != 1){
            CError.buildErr(this, "没有查到退保区分类型计算代码!");
            throw new Exception();
        }

    	if((strRiskcode.equals("SPUL")&&(strEdorType.equals("TI")||strEdorType.equals("PA")))
               ||(strRiskcode.equals("RPUL")&&(strEdorType.equals("AR")||strEdorType.equals("TI")||strEdorType.equals("PA")))
               ||(strRiskcode.equals("SPUB")&&(strEdorType.equals("TI")||strEdorType.equals("PA"))))
    	{
    		return ForFee1();
    	}
    	else if(strRiskcode.equals("SPUL")||strEdorType.equals("AR")||strRiskcode.equals("SPUB"))
    	{
    		return ForFee2();
    	}
    	else
    	{
           // CError.buildErr(this, "本类不支持传入的险种和保全类型!");
           // throw new Exception();
    		return ForFee1();
    	}
//    	return ForFee1();
    }

    /**
     * 计算保全手续费1
     * 适用范围：SPUL的TI,PA.RPUL的AR,TI,PA.
     * @version 1.0
     */
    private double ForFee1() throws Exception
    {
    	logger.debug("ForFee1--begin");

        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
        tCalculator.addBasicFactor("strContNo",strContNo);
        tCalculator.addBasicFactor("strEdorType", strEdorType);
        tCalculator.addBasicFactor("strSDate", strSDate);
        tCalculator.addBasicFactor("endDate", strEdorValiDate);
        tCalculator.addBasicFactor("EdorNo", strEdorNo);
        if(mMainMoney ==- 1000)
        {
        	tCalculator.addBasicFactor("QHFlag", "Q");
        }
        else
        {
        	tCalculator.addBasicFactor("QHFlag", "H");
        }

       return Double.parseDouble(tCalculator.calculate());
    }
    /**
     * 得到保单周年日
     * @version 1.0
     * @Rewrited QianLy 原来的写法是错误的，如传入'2008-02-29'和'2009-05-27',返回了'2009-02-29'，是不存在的日期
     */
    public String GetStartDate(String strCValiDate,String strEdorvaluedate)
    {
    	String sNewDate = ""; 
    	String sCValiDate = BqNameFun.delTime(strCValiDate);
    	String sEdorvaluedate = BqNameFun.delTime(strEdorvaluedate);
        //返回计算时点前面最近的保单周年日
        while(PubFun.calInterval(sCValiDate,sEdorvaluedate,"Y") > 0){
        	sCValiDate = PubFun.calDate(sCValiDate,1,"Y",null);
        }
        sNewDate = sCValiDate;
        return sNewDate;
    }

    /**
     * 得到第N保单年
     * @version 1.0
     */
   private int getYearCount(String strCValiDate)
   {
	   int YearCount = Integer.parseInt(strSDate.substring(0, 4))-Integer.parseInt(strCValiDate.substring(0, 4))+1;
	   return YearCount;
   }


    /**
     * 计算保全手续费2
     * 适用范围：SPUL的AR.
     * @version 1.0
     */
    private double ForFee2()
    {
    	logger.debug("ForFee2--begin");
    	//取得第N保单年
    	String strYearCount = String.valueOf(getYearCount(strCValiDate));

    	//调用手续费计算方法
    	Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(tLMEdorCalSet.get(1).getCalCode());
        tCalculator.addBasicFactor("contno",strContNo);
        tCalculator.addBasicFactor("MainMoney", String.valueOf(mMainMoney));
        tCalculator.addBasicFactor("YearCount", strYearCount);
        tCalculator.addBasicFactor("strSDate", strSDate);
        tCalculator.addBasicFactor("endDate", strEdorValiDate);
        tCalculator.addBasicFactor("EdorNo", strEdorNo);
       return Double.parseDouble(tCalculator.calculate());

    }
    
    public double CalAccFee(LCInsureAccTraceSchema aLCInsureAccTraceSchema){
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String	strsql = "select * from lmriskfee where insuaccno In ('000000','"+"?InsuAccNo?"
    		    +"') And payplancode In ('000000','"+ "?PayPlanCode?" +"') and FeeItemType = '06' and Feeperiod = '"+ "?BusyType?" +"' order by FeeNum";
    	sqlbv.sql(strsql);
    	sqlbv.put("InsuAccNo", aLCInsureAccTraceSchema.getInsuAccNo());
    	sqlbv.put("PayPlanCode", aLCInsureAccTraceSchema.getPayPlanCode());
    	sqlbv.put("BusyType", aLCInsureAccTraceSchema.getBusyType());
	    logger.debug(strsql);
		LMRiskFeeSet aLMRiskFeeSet = new LMRiskFeeSet();
		LMRiskFeeDB aLMRiskFeeDB = new LMRiskFeeDB();
		aLMRiskFeeSet = aLMRiskFeeDB.executeQuery(sqlbv);
		
		double feeTraceValue = 0.00; //管理费金额
		
		//对管理费表进行循环处理
		for(int k = 1; k <= aLMRiskFeeSet.size(); k++)
		{
		    LMRiskFeeSchema aLMRiskFeeSchema = aLMRiskFeeSet.get(k);
		    //计算单位数以及金额
		    if(aLMRiskFeeSchema.getFeeCalModeType().equals("0"))
		    {
		        //取固定值
		        if(aLMRiskFeeSchema.getFeeCalMode().equals("01"))
		        {
		            //固定值内扣
		            feeTraceValue = aLMRiskFeeSchema.getFeeValue();
		        }else if(aLMRiskFeeSchema.getFeeCalMode().equals("02"))
		        {
		            //账户价值固定比例内扣
		            feeTraceValue = -aLCInsureAccTraceSchema.getMoney() *aLMRiskFeeSchema.getFeeValue();
		        }
		        else
		        {
		            //默认情况
		            feeTraceValue = aLMRiskFeeSchema.getFeeValue();
		        }
		    }
		    else if(aLMRiskFeeSchema.getFeeCalModeType().equals("1"))
		    {
		        //通过sql计算得到
		        feeTraceValue = CalFeeForTrace(aLMRiskFeeSchema.getFeeCalCode(),aLCInsureAccTraceSchema);
		    }
		    else if(aLMRiskFeeSchema.getFeeCalModeType().equals("2"))
		    {
		        //通过class计算得到
		        try
		        {
		            Class tClass = Class.forName(aLMRiskFeeSchema.getInterfaceClassName());
		            ManageFeeService tManageFeeService = (ManageFeeService) tClass.newInstance();
		            VData tVData = new VData();
		            String tOperate = "";
		            if(!tManageFeeService.submitData(tVData,tOperate))
		            {
		                //错处理
		                continue;
		            }
		            feeTraceValue = tManageFeeService.getfeeValue();
		            //获取金额或单位
		        }
		        catch(Exception ex)
		        {
		        	break;
		        }
		    }
		    if(feeTraceValue == 0){
		        continue;
		    }
	    }
    	return Arith.round(feeTraceValue,2);
    }
    
    /**
     * 通过SQL计算手续费
     * 适用范围：SPUL的AR.
     * @version 1.0
     */
    private double CalFeeForTrace(String aCalCode,LCInsureAccTraceSchema aLCInsureAccTraceSchema)
    {
    	//调用手续费计算方法
    	Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(aCalCode);
        tCalculator.addBasicFactor("ContNo",aLCInsureAccTraceSchema.getContNo());
        tCalculator.addBasicFactor("Money", String.valueOf(aLCInsureAccTraceSchema.getMoney()));
        tCalculator.addBasicFactor("UnitCount", String.valueOf(aLCInsureAccTraceSchema.getUnitCount()));
        tCalculator.addBasicFactor("PayDate", aLCInsureAccTraceSchema.getPayDate());
        return Double.parseDouble(tCalculator.calculate());
    }    


    public static void main(String arg[]) {

    	LPEdorItemSchema mainLPEdorItemSchema = new LPEdorItemSchema();
    	LCPolSchema mainLCPolSchema = new LCPolSchema();
    	LPEdorItemDB mainLPEdorItemDB = new LPEdorItemDB();
    	LCPolDB mainLCPolDB = new LCPolDB();
    	mainLPEdorItemDB.setEdorAcceptNo("6120070831000003");
    	mainLPEdorItemSchema = mainLPEdorItemDB.query().get(1);
    	mainLCPolDB.setContNo(mainLPEdorItemSchema.getContNo());
    	mainLCPolSchema = mainLCPolDB.query().get(1);
    	TLbqForFee tTLbqForFee =new TLbqForFee();
    	double douCalFee = -1;
    	try {
			douCalFee = tTLbqForFee.GetCalFee(99999,mainLPEdorItemSchema.getEdorNo());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(":::;"+douCalFee);

    }


}

