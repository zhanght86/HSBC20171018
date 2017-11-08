package com.sinosoft.lis.pubfun;
import org.apache.log4j.Logger;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SysConst;

public class LDExch {
private static Logger logger = Logger.getLogger(LDExch.class);
	public CErrors mErrors = new CErrors();
	public LDExch(){}
	
	public String tBaseCur;
	
	/**
	 * 根据原始币种和交易日期，从即期外汇牌价表中查找交易时点的汇率，将原币金额折算到本币。
	 * 如果返回值小于0，则有错误数据
	 * orgitype 传入的币种
	 * destype 需要转换的币种
	 * transdate 转换日期
	 * amnt 转换金额
	*/
	
	public double toBaseCur(String orgitype,String destype,String transdate,double amnt)
	{
		double DestCur=0;
		if(orgitype.equals(destype)){
			return amnt;
		}
		ExeSQL tExeSQL = new ExeSQL();
		
		//校验传入的币种是否存在
		String sqlExit="select count('x') from LDCurrency where CurrCode='"+"?CurrCode?"+"'";
		SQLwithBindVariables sqlbv=new SQLwithBindVariables();
		sqlbv.sql(sqlExit);
		sqlbv.put("CurrCode", orgitype);
		if(Integer.parseInt(tExeSQL.getOneValue(sqlbv))<=0)
		{
			CError tError = new CError();
            tError.moduleName = "LDMthMidRateBL";
            tError.functionName = "submitData";
            tError.errorMessage = "传入的币种不存在!";
            this.mErrors.addOneError(tError);
            return amnt;//-1;
		}
		
		//校验目标币种是否存在
		sqlExit="select count('x') from LDCurrency where CurrCode='"+"?CurrCode1?"+"'";
		SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
		sqlbv1.sql(sqlExit);
		sqlbv1.put("CurrCode1", destype);

		if(Integer.parseInt(tExeSQL.getOneValue(sqlbv1))<=0)
		{
			CError tError = new CError();
            tError.moduleName = "LDMthMidRateBL";
            tError.functionName = "submitData";
            tError.errorMessage = "传入的目标币种不存在!";
            this.mErrors.addOneError(tError);
            return amnt;//-1;
		}
		
		//校验录入的本币是否小于等于0  负值也允许转换
//		if(amnt<0)
//		{
//			CError tError = new CError();
//            tError.moduleName = "LDMthMidRateBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "传入的目标币值小于零!";
//            this.mErrors.addOneError(tError);
//            return -1;
//		}
		
		String sql="select middle*"+"?amnt?"+"/per from LDExRate where currcode='"+"?orgitype?"+"' " +
				" and destcode='"+"?destype?"+"'";
		String sql1="select per*"+"?amnt?"+"/middle from LDExRate where currcode='"+"?destype?"+"' " +
		" and destcode='"+"?orgitype?"+"'";
		
		SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
		sqlbv3.sql(sql);
		sqlbv3.put("amnt", amnt);
		sqlbv3.put("orgitype", orgitype);
		sqlbv3.put("destype", destype);
		
		SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
		sqlbv2.sql(sql1);
		sqlbv2.put("amnt", amnt);
		sqlbv2.put("destype", destype);
		sqlbv2.put("orgitype", orgitype);
		
		//如果没有查到外汇币种对本币的直接汇率就尝试查外汇币种对美元的汇率，在通过美元对本币的汇率来间接获得
		if(tExeSQL.getOneValue(sqlbv3)!=null&&!tExeSQL.getOneValue(sqlbv3).equals(""))
		{
			DestCur=Double.parseDouble(tExeSQL.getOneValue(sqlbv3));
			
		}else if(tExeSQL.getOneValue(sqlbv2)!=null&&!tExeSQL.getOneValue(sqlbv2).equals(""))
		{
			
			DestCur=Double.parseDouble(tExeSQL.getOneValue(sqlbv2));
		}else{
			sql="select count('x') from LDExOtherRate where currcode='?orgitype?' " +
					" and DestCurrCode='?DestCurrCode?'"+
					" and startdate<='?transdate?'"+
					" and (enddate is null or enddate>='?transdate?')";
			SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
			sqlbv5.sql(sql);
			sqlbv5.put("orgitype", orgitype);
			sqlbv5.put("DestCurrCode", SysConst.DollarCode);
			sqlbv5.put("transdate", transdate);
			if(Integer.parseInt(tExeSQL.getOneValue(sqlbv5))<=0)
			{
				CError tError = new CError();
	            tError.moduleName = "LDMthMidRateBL";
	            tError.functionName = "submitData";
	            tError.errorMessage = "该币种没有对应折算率记录!";
	            this.mErrors.addOneError(tError);
	            return amnt;//-1;
			}else
			{
				sql="select "+"?amnt?"+"*a.ExchRate*b.middle/a.per/b.per " +
				"from LDExOtherRate a,LDExRate b where a.currcode='"+"?orgitype?"+"' " +
				" and a.DestCurrCode='"+"?SysConst?"+"'"+
				" and a.startdate<='"+"?transdate?"+"'"+
				" and (a.enddate is null or a.enddate>='"+"?transdate?"+"')"+
				" and b.currcode='"+"?SysConst?"+"' and b.destcode='"+"?destype?"+"'";
				SQLwithBindVariables sqlbv4=new SQLwithBindVariables();
				sqlbv4.sql(sql);
				sqlbv4.put("amnt", amnt);
				sqlbv4.put("orgitype", orgitype);
				sqlbv4.put("destype", destype);
				sqlbv4.put("SysConst", SysConst.DollarCode);
				sqlbv4.put("transdate", transdate);
				DestCur=Double.parseDouble(tExeSQL.getOneValue(sqlbv4));
			}
		}
		return DestCur;
		
	}
	
	/**
	 * 根据原始币种和交易日期，从LDMthMidRate中查找交易时点的汇率，将原币金额折算到目标币种金额。
	 * 如果返回值小于0，则有错误数据
	 * orgitype 传入的币种
	 * destype 需要转换的币种
	 * transdate 转换日期
	 * amnt 转换金额
	*/
	public double toOtherCur(String orgitype,String destype,String transdate,double amnt)
	{
		
		//tongmeng 2010-12-02 modify
		//如果原币种和目标币种一致,不转换,直接返回
		if(orgitype!=null&&destype!=null&destype.equals(orgitype))
		{
			return amnt;
		}
		
		//修改代码 2010-12-20 
		if(orgitype!=null&&destype!=null)
		{
			if(destype.equals(orgitype)){
				return amnt;		
			}	
		}
		
		double DestCur=0;
		ExeSQL tExeSQL = new ExeSQL();
		
		//校验传入的币种是否存在
		String sqlExit="select count('x') from LDCurrency where CurrCode='"+"?CurrCode?"+"'";
		SQLwithBindVariables sqlbv5=new SQLwithBindVariables();
		sqlbv5.sql(sqlExit);
		sqlbv5.put("CurrCode", orgitype);
		if(Integer.parseInt(tExeSQL.getOneValue(sqlbv5))<=0)
		{
			CError tError = new CError();
            tError.moduleName = "LDMthMidRateBL";
            tError.functionName = "submitData";
            tError.errorMessage = "传入的币种不存在!";
            this.mErrors.addOneError(tError);
            return amnt;//-1;
		}
		
		//校验目标币种是否存在
		sqlExit="select count('x') from LDCurrency where CurrCode='"+"?destype?"+"'";
		SQLwithBindVariables sqlbv6=new SQLwithBindVariables();
		sqlbv6.sql(sqlExit);
		sqlbv6.put("destype", destype);
		if(Integer.parseInt(tExeSQL.getOneValue(sqlbv6))<=0)
		{
			CError tError = new CError();
            tError.moduleName = "LDMthMidRateBL";
            tError.functionName = "submitData";
            tError.errorMessage = "传入的币种不存在!";
            this.mErrors.addOneError(tError);
            return amnt;//-1;
		}
		
		//校验录入的本币是否小于等于0
//		if(amnt<0)
//		{
//			CError tError = new CError();
//            tError.moduleName = "LDMthMidRateBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "传入的目标币值小于零!";
//            this.mErrors.addOneError(tError);
//            return -1;
//		}
		
		String sql="select Average*"+"?amnt?"+"/per from LDMthMidRate where currcode='"+"?orgitype?"+"' " +
				" and destcode='"+"?destype?"+"'"+
				" and ValidYear='"+"?ValidYear?"+"'"+
				" and ValidMonth='"+"?ValidMonth?"+"'";
		String sql1="select per*"+"?amnt1?"+"/Average from LDMthMidRate where currcode='"+"?destype?"+"' " +
		" and destcode='"+"?destype1?"+"'"+
		" and ValidYear='"+"?ValidYear1?"+"'"+
		" and ValidMonth='"+"?ValidMonth1?"+"'";
		
		//如果没有查到外汇币种对目标币的直接汇率就尝试查外汇币种对美元的汇率，在通过美元对目标币的汇率来间接获得
		SQLwithBindVariables sqlbv7=new SQLwithBindVariables();
		sqlbv7.sql(sql);
		sqlbv7.put("amnt", amnt);
		sqlbv7.put("orgitype", orgitype);
		sqlbv7.put("destype", destype);
		sqlbv7.put("ValidYear", transdate.substring(0,4));
		sqlbv7.put("ValidMonth", transdate.substring(5,7));
		
		SQLwithBindVariables sqlbv8=new SQLwithBindVariables();
		sqlbv8.sql(sql1);
		sqlbv8.put("amnt1", amnt);
		sqlbv8.put("destype1", destype);
		sqlbv8.put("orgitype1", orgitype);
		sqlbv8.put("ValidYear1", transdate.substring(0,4));
		sqlbv8.put("ValidMonth1", transdate.substring(5,7));
		if(tExeSQL.getOneValue(sqlbv7)!=null&&!tExeSQL.getOneValue(sqlbv7).equals(""))
		{
			DestCur=Double.parseDouble(tExeSQL.getOneValue(sqlbv7));
		}else if(tExeSQL.getOneValue(sqlbv8)!=null&&!tExeSQL.getOneValue(sqlbv8).equals(""))
		{
			DestCur=Double.parseDouble(tExeSQL.getOneValue(sqlbv8));
		}else{
			sql="select count('x') from LDExOtherRate where currcode='"+"?currcode?"+"' " +
			" and DestCurrCode='"+"?DestCurrCode?"+"'"+
			" and startdate<='"+"?transdate?"+"'"+
			" and (enddate is null or enddate>='"+"?transdate?"+"')";
			SQLwithBindVariables sqlbv9=new SQLwithBindVariables();
			sqlbv9.sql(sql);
			sqlbv9.put("currcode", orgitype);
			sqlbv9.put("DestCurrCode", SysConst.DollarCode);
			sqlbv9.put("transdate", transdate);
			if(Integer.parseInt(tExeSQL.getOneValue(sqlbv9))<=0)
			{
				CError tError = new CError();
	            tError.moduleName = "LDMthMidRateBL";
	            tError.functionName = "submitData";
	            tError.errorMessage = "该币种没有对应的折算率记录!";
	            this.mErrors.addOneError(tError);
	            return amnt;//-1;
			}else
			{
				amnt=Double.parseDouble(tExeSQL.getOneValue(sqlbv9));
				sql="select "+"?amnt?"+"*a.Average*b.ExchRate/a.per/b.per from LDMthMidRate a,LDExOtherRate b" +
				" where  b.DestCurrCode='"+"?DestCurrCode1?"+"' and b.startdate<='"+"?transdate?"+"'" +
				" and (b.enddate is null or b.enddate>='"+"?transdate?"+"')"+
				" and a.currcode='"+"?DestCurrCode1?"+"' "+
				" and a.destcode='"+"?destype?"+"'"+
				" and a.ValidYear='"+"?ValidYear?"+"'"+
				" and a.ValidMonth='"+"?ValidMonth?"+"'";
				SQLwithBindVariables sqlbv10=new SQLwithBindVariables();
				sqlbv10.sql(sql);
				sqlbv10.put("amnt", amnt);
				sqlbv10.put("DestCurrCode1", SysConst.DollarCode);
				sqlbv10.put("transdate", transdate);
				sqlbv10.put("destype", destype);
				sqlbv10.put("ValidYear", transdate.substring(0,4));
				sqlbv10.put("ValidMonth", transdate.substring(5,7));

				DestCur=Double.parseDouble(tExeSQL.getOneValue(sqlbv10));
				
				
			}
		}
		return DestCur;
	}
	/**
     * 主函数，测试用
     * @param args String[]
     */
    public static void main(String[] args)
    {
        LDExch tLDExch = new LDExch();
        double x=tLDExch.toBaseCur("15","01","2009-10-15", 100);
        double y=tLDExch.toOtherCur("15","01","2009-10-15", 100);
//        logger.debug("toBaseCur:"+x);
        logger.debug("toOtherCur:"+y);
    }
}
