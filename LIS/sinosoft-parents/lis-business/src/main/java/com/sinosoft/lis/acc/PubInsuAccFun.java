package com.sinosoft.lis.acc;
import java.text.DateFormat;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:投连公共函数类 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 * @Cleaned QianLy 2009-7-16
 */

public class PubInsuAccFun
{
private static Logger logger = Logger.getLogger(PubInsuAccFun.class);

	public static String isTN = "Y";//是否T+N计价 Y:是T+N，N:最近一次计价日计价，L:上次计价日计价(含本次计价日)
	
    public PubInsuAccFun()
    {
    }

    /*统计待计价的集合*/
    public static LCInsureAccSet queryCalSet()
    {
        LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        /*查询逻辑*/
        String Sql ="select * from lcinsureacc a ,lmriskapp b where a.riskcode=b.riskcode and b.risktype3='3' and (a.polno,a.insuaccno) in (select distinct polno,insuaccno from LCInsureAccTrace where state='0' and paydate is not null)";
        sqlbv.sql(Sql);
        _LCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
        logger.debug(Sql);
        return _LCInsureAccSet;
    }

    public static LCInsureAccSet queryCalSet(LCContSet tLCContSet)
    {
        LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        /*查询逻辑*/
        for (int i = 1; i <= tLCContSet.size(); i++)
        {
            LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
            SQLwithBindVariables sqlbv = new SQLwithBindVariables();
            String strContNo = tLCContSet.get(i).getContNo();
            String Sql = "select * from lcinsureacc a ,lmriskapp b where a.riskcode=b.riskcode and b.risktype3='3' and a.polno in (select polno from lcpol where ContNo='" + "?strContNo?"
                       + "') and (a.polno,a.insuaccno) in (select distinct polno,insuaccno from LCInsureAccTrace  where state='0' and paydate is not null)";
            sqlbv.sql(Sql);
            sqlbv.put("strContNo", strContNo);
            tLCInsureAccSet = tLCInsureAccDB.executeQuery(sqlbv);
            logger.debug(Sql);
            _LCInsureAccSet.add(tLCInsureAccSet);
        }
        return _LCInsureAccSet;
    }

    /**查询需要进行管理费处理的保单
     * risktype3='3'为投连产品
     */
    public static LCPolSet queryPolManageSet()
    {
        LCPolSet _LCPolSet = new LCPolSet();
        LCPolDB _LCPolDB = new LCPolDB();
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql("select * from lcpol a,lmriskapp b where a.riskcode=b.riskcode and a.payintv!=0 and b.risktype3='3' and not exists (select distinct 1 from lccontstate where polno = a.mainpolno and statetype in ('Terminate','Available') and state='1' and enddate is null) and not exists(select distinct 1 from lopolafterdeal where contno=a.contno and state!='2')");
        _LCPolSet = _LCPolDB.executeQuery(sqlbv);
        return _LCPolSet;
    }

    public static LCPolSet queryPolManageSet(LCContSet tLCContSet)
    {
        LCPolSet _LCPolSet = new LCPolSet();
        LCPolDB _LCPolDB = new LCPolDB();
        for (int i = 1; i <= tLCContSet.size(); i++)
        {
            LCPolSet tLCPolSet = new LCPolSet();
            SQLwithBindVariables sqlbv = new SQLwithBindVariables();
            String strcontno = tLCContSet.get(i).getContNo();
            String SQL = "select * from lcpol a,lmriskapp b where a.contno='" +
                         "?strcontno?" + "' and a.riskcode=b.riskcode and a.payintv!=0 and b.risktype3='3' and not exists (select distinct 1 from lccontstate where contno=a.contno and polno=a.polno and statetype in ('Terminate','Available') and state='1' and enddate is null) and not exists(select distinct 1 from lopolafterdeal where contno=a.contno and state!='2')";
            sqlbv.sql(SQL);
            sqlbv.put("strcontno", strcontno);
            logger.debug(SQL);
            tLCPolSet = _LCPolDB.executeQuery(sqlbv);
            _LCPolSet.add(tLCPolSet);
        }
        return _LCPolSet;
    }

    /*查询需要进行管理费处理的账户*/
    public static LCInsureAccSet queryInsuAccManageSet()
    {
        LCInsureAccSet _LCInsureAccSet = new LCInsureAccSet();
        LCInsureAccDB _LCInsureAccDB = new LCInsureAccDB();
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        /*查询逻辑*/
        String strsql =
                "select * from lcinsureacc a where not exists (select distinct 1 from lccontstate where contno=a.contno and statetype in ('Terminate','Available') and state='1' and enddate is null )  and not exists(select distinct 1 from lopolafterdeal where contno=a.contno and state!='2')  and not exists (select 1 from llappclaimreason where reasoncode in ('102','202','103','203') and customerno=a.insuredno)";
        sqlbv.sql(strsql);
        _LCInsureAccSet = _LCInsureAccDB.executeQuery(sqlbv);
        return _LCInsureAccSet;
    }

    /*查询需要进行后续处理的保单*/
    public static LOPolAfterDealSet queryAfterSet()
    {
        LOPolAfterDealSet _LOPolAfterDealSet = new LOPolAfterDealSet();
        LOPolAfterDealDB _LOPolAfterDealDB = new LOPolAfterDealDB();
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String strsql = "select * from LOPolAfterDeal a where state='0' order by confdate,maketime";
        sqlbv.sql(strsql);
        _LOPolAfterDealSet = _LOPolAfterDealDB.executeQuery(sqlbv);
        /*查询逻辑*/
        return _LOPolAfterDealSet;
    }

    public static LOPolAfterDealSet queryAfterSet(LCContSet tLCContSet)
    {
        LOPolAfterDealSet _LOPolAfterDealSet = new LOPolAfterDealSet();
        LOPolAfterDealDB _LOPolAfterDealDB = new LOPolAfterDealDB();
        for (int i = 1; i <= tLCContSet.size(); i++)
        {
            LOPolAfterDealSet tLOPolAfterDealSet = new LOPolAfterDealSet();
            SQLwithBindVariables sqlbv = new SQLwithBindVariables();
            String strsql = "select * from LOPolAfterDeal a where state='0' and ContNo='"+ "?ContNo?" + "'";
            sqlbv.sql(strsql);
            sqlbv.put("ContNo", tLCContSet.get(i).getContNo());
            tLOPolAfterDealSet = _LOPolAfterDealDB.executeQuery(sqlbv);
            logger.debug(strsql);
            _LOPolAfterDealSet.add(tLOPolAfterDealSet);
        }
        /*查询逻辑*/
        return _LOPolAfterDealSet;
    }

    /*得到下一个计价日*/
    public static String getNextStartDate(String _PayDate, String InsuAccNo )
    {
    	String LastStartDate = "";
    	String getWherePart = "";
        if (!InsuAccNo.equals(""))
        {
            getWherePart = " and InsuAccNo='" + "?InsuAccNo?" + "' ";
        }
    	if(isTN != null && "Y".equals(isTN)){
    		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
//            String Sql = "Select min(StartDate) from LOAccUnitPrice where 1=1 " + getWherePart + " and StartDate >='" + _PayDate + "' and state='0'";
            String Sql = "Select StartDate from LOAccUnitPrice where 1=1 " + getWherePart + " and StartDate >='" + "?_PayDate?" + "' and state='0' order by StartDate";
            sqlbv.sql(Sql);
            sqlbv.put("InsuAccNo", InsuAccNo);
            sqlbv.put("_PayDate", _PayDate);
            
            ExeSQL nExeSQL = new ExeSQL();
            SSRS nSSRS = new SSRS();
            nSSRS = nExeSQL.execSQL(sqlbv);
            
            if(nSSRS != null && nSSRS.getMaxRow() > 0){
            	SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
            	String tSql = "Select (case when tnflag is not null then tnflag  else 0 end) From lmriskinsuacc Where insuaccno = '"+"?InsuAccNo?"+"'";
            	sqlbv2.sql(tSql);
            	sqlbv2.put("InsuAccNo", InsuAccNo);
            	String tn = nExeSQL.getOneValue(sqlbv2);
            	int n;
            	if(tn == null || "".equals(tn)){
            		n = 0;//InsuAccNo为空时 默认为最近一次计价
            	}else{
            		n = Integer.parseInt(tn);
            	}
            	if(n+1 <= nSSRS.getMaxRow()){
            		LastStartDate = nSSRS.GetText(n+1, 1);
            	}
//                LastStartDate = nSSRS.GetText(1, 1);
            }
    	}else if(isTN != null && "L".equals(isTN)){
    		LastStartDate = getLastStartDate(_PayDate, InsuAccNo);
    	}else{
    		SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        	String Sql = "Select min(StartDate) from LOAccUnitPrice where 1=1 " + getWherePart + " and StartDate >='" + "?_PayDate?" + "' and state='0'";
        	sqlbv3.sql(Sql);
        	sqlbv3.put("InsuAccNo", InsuAccNo);
        	sqlbv3.put("_PayDate", _PayDate);
        	ExeSQL nExeSQL = new ExeSQL();
            SSRS nSSRS = new SSRS();
            nSSRS = nExeSQL.execSQL(sqlbv3);
            if(nSSRS != null && nSSRS.getMaxRow() > 0){
                LastStartDate = nSSRS.GetText(1, 1);
            }
    	}
        
        return LastStartDate;
    }

    /**单位算金额
     * _Unit投资单位
     * _PriceDate计价日期
     **/
    public static double calPrice(double _Unit, LOAccUnitPriceSchema tLOAccUnitPriceSchema)
    {
        double _Price = 0;
        /*处理逻辑*/
        if (tLOAccUnitPriceSchema != null)
        {
            _Price = calPrice(_Unit, tLOAccUnitPriceSchema.getStartDate(),
                              tLOAccUnitPriceSchema.getInsuAccNo());
        }
        return _Price;
    }

    /*  _PayDate 为业务缴费日期,或是计价日期;
     */
    public static double calPrice(double _Unit, String _PayDate, String InsuAccNo)
    {
        double _Price = 0;
        /*处理逻辑*/

        String LastStartDate = "";
        LastStartDate = getNextStartDate(_PayDate, InsuAccNo);
        if (LastStartDate.equals("") || LastStartDate == null ||
            LastStartDate.equals(null))
        {
            _Price = 0;
        }
        else
        {
        	SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
            String tSql = "Select UnitPriceBuy,UnitPriceSell from LOAccUnitPrice where InsuAccNo='" +"?InsuAccNo?" + "' and  StartDate ='" + "?LastStartDate?" + "' and state='0'";
            sqlbv4.sql(tSql);
            sqlbv4.put("InsuAccNo", InsuAccNo);
            sqlbv4.put("LastStartDate", LastStartDate);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(sqlbv4);
            double PriceBuy = 0; //单位买入价格
            double PriceSel = 0; //单位卖出价格
            if (tSSRS.MaxRow > 0)
            {
                PriceBuy = Double.parseDouble(tSSRS.GetText(1, 1));
                PriceSel = Double.parseDouble(tSSRS.GetText(1, 2));
                if (_Unit <= 0)
                { //资金退出帐户,用卖出价将投资账户中的投资单位兑现为现金。！
                    _Price = _Unit * PriceSel;
                }
                else
                { //资金进入账户，用买入价将投资账户中的投资单位兑现为现金。
                    _Price = _Unit * PriceBuy;
                }
            }
            else
            {
                _Price = 0;
            }
        }
        return _Price;
    }

    /**
     *   _PayDate 为业务缴费日期,或是计价日期;
     *   处理保全项目指定卖出买入价的情况
     */
    public static double calPrice(double _Unit, LCInsureAccTraceSchema _LCInsureAccTraceSchema)
    {
        double _Price = 0;
        /*处理逻辑*/
        String _PayDate = _LCInsureAccTraceSchema.getPayDate();
        String InsuAccNo = _LCInsureAccTraceSchema.getInsuAccNo();
//        String riskcode = _LCInsureAccTraceSchema.getRiskCode();
        String moneyType = _LCInsureAccTraceSchema.getMoneyType();

        String LastStartDate = "";
        LastStartDate = getNextStartDate(_PayDate, InsuAccNo);
        if (LastStartDate.equals("") || LastStartDate == null ||
            LastStartDate.equals(null))
        {
            _Price = 0;
        }
        else
        {
        	SQLwithBindVariables sqlbv5 = new SQLwithBindVariables();
            String tSql =
                    "Select UnitPriceBuy,UnitPriceSell from LOAccUnitPrice where InsuAccNo='" +
                    "?InsuAccNo?" + "' and  StartDate ='" + "?LastStartDate?" + "' and state='0'";
            sqlbv5.sql(tSql);
            sqlbv5.put("InsuAccNo", InsuAccNo);
            sqlbv5.put("LastStartDate", LastStartDate);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(sqlbv5);
            double PriceBuy = 0; //单位买入价格
            double PriceSel = 0; //单位卖出价格
            if (tSSRS.MaxRow > 0)
            {
                PriceBuy = Double.parseDouble(tSSRS.GetText(1, 1));
                PriceSel = Double.parseDouble(tSSRS.GetText(1, 2));
                if (_Unit <= 0)
                { //资金退出帐户,卖出
                    if (moneyType != null && !moneyType.equals(""))
                    {
                        LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
                        tLMEdorItemDB.setEdorCode(moneyType);
                        if (_LCInsureAccTraceSchema.getGrpPolNo().equals(
                                "00000000000000000000"))
                        {
                            tLMEdorItemDB.setAppObj("I");
                        }
                        else
                        {
                            tLMEdorItemDB.setAppObj("G");
                        }
                        if (tLMEdorItemDB.getInfo())
                        {
                            if (tLMEdorItemDB.getPriceSellFlag() != null && tLMEdorItemDB.getPriceSellFlag().equals("01"))
                            {
                                _Price = _Unit * PriceBuy;
                            }
                            else
                            {
                                _Price = _Unit * PriceSel;
                            }
                        }
                        else
                        {
                            _Price = _Unit * PriceSel;
                        }
                    }
                }
                else
                { //资金进入账户，买入
                    if (moneyType != null && !moneyType.equals(""))
                    {
                        LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
                        tLMEdorItemDB.setEdorCode(moneyType);
                        if (_LCInsureAccTraceSchema.getGrpPolNo().equals("00000000000000000000"))
                        {
                            tLMEdorItemDB.setAppObj("I");
                        }
                        else
                        {
                            tLMEdorItemDB.setAppObj("G");
                        }
                        if (tLMEdorItemDB.getInfo())
                        {
                            if (tLMEdorItemDB.getPriceBuyFlag() != null && tLMEdorItemDB.getPriceBuyFlag().equals("02"))
                            {
                                _Price = _Unit * PriceSel;
                            }
                            else
                            {
                                _Price = _Unit * PriceBuy;
                            }
                        }
                        else
                        {
                            _Price = _Unit * PriceBuy;
                        }
                    }
                }
            }
            else
            {
                _Price = 0;
            }
        }
        return _Price;
    }

    /**金额算单位
     * _Price投资金额
     *
     **/
    public static double calUnit(double _Price, LOAccUnitPriceSchema tLOAccUnitPriceSchema)
    {
        double _Unit = 0;
        /*处理逻辑*/
        if (tLOAccUnitPriceSchema != null)
        {
            _Unit = calUnit(_Unit, tLOAccUnitPriceSchema.getStartDate(),
                            tLOAccUnitPriceSchema.getInsuAccNo());
        }
        return _Unit;
    }

    /**金额算单位
     * _Price投资金额
     * _PayDate 为业务缴费日期,或是计价日期;
     **/

    public static double calUnit(double _Price, String _PayDate, String InsuAccNo )
    {
        double _Unit = 0;
        /*处理逻辑*/
        String LastStartDate = "";
        LastStartDate = getNextStartDate(_PayDate, InsuAccNo);
        if (LastStartDate.equals("") ||
            LastStartDate == null && LastStartDate.equals(null))
        {
            _Price = 0;
        }
        else
        {
        	SQLwithBindVariables sqlbv6 = new SQLwithBindVariables();
            String tSql =
                    "Select UnitPriceBuy,UnitPriceSell from LOAccUnitPrice where InsuAccNo='" +
                    "?InsuAccNo?" + "'and  StartDate ='" + "?LastStartDate?" +
                    "' and state='0'";
            sqlbv6.sql(tSql);
            sqlbv6.put("InsuAccNo", InsuAccNo);
            sqlbv6.put("LastStartDate", LastStartDate);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(sqlbv6);
            double PriceBuy = 0; //单位买入价格
            double PriceSel = 0; //单位卖出价格
            if (tSSRS.MaxRow > 0)
            {
                PriceBuy = Double.parseDouble(tSSRS.GetText(1, 1));
                PriceSel = Double.parseDouble(tSSRS.GetText(1, 2));
                if (PriceBuy == 0 || PriceSel == 0)
                {
                    _Unit = 0;
                }
                else
                {
                    if (_Price < 0)
                    { //资金退出帐户,用卖出价
                        _Unit = _Price / PriceSel;
                    }
                    else
                    { //资金进入投资账户，用买入价将资金折算为投资单位。！
                        _Unit = _Price / PriceBuy;
                    }
                }
            }
            else
            {
                _Unit = 0;
            }
        }
        return _Unit;
    }


    /**金额算单位
     * _Price投资金额
     * _PayDate 为业务缴费日期,或是计价日期;
     * 处理保全项目指定卖出买入价的情况
     **/

    public static double calUnit(double _Price, LCInsureAccTraceSchema _LCInsureAccTraceSchema)
    {
        double _Unit = 0;
        /*处理逻辑*/
        String _PayDate = _LCInsureAccTraceSchema.getPayDate();
        String InsuAccNo = _LCInsureAccTraceSchema.getInsuAccNo();
//        String riskcode = _LCInsureAccTraceSchema.getRiskCode();
        String moneyType = _LCInsureAccTraceSchema.getMoneyType();

        String LastStartDate = "";
        LastStartDate = getNextStartDate(_PayDate, InsuAccNo);
        if (LastStartDate.equals("") ||
            LastStartDate == null && LastStartDate.equals(null))
        {
            _Price = 0;
        }
        else
        {
        	SQLwithBindVariables sqlbv7 = new SQLwithBindVariables();
            String tSql = "Select UnitPriceBuy,UnitPriceSell from LOAccUnitPrice where InsuAccNo='" + "?InsuAccNo?" + "'and  StartDate ='" + "?LastStartDate?" + "' and state='0'";
            sqlbv7.sql(tSql);
            sqlbv7.put("InsuAccNo", InsuAccNo);
            sqlbv7.put("LastStartDate", LastStartDate);
            ExeSQL tExeSQL = new ExeSQL();
            SSRS tSSRS = new SSRS();
            tSSRS = tExeSQL.execSQL(sqlbv7);
            double PriceBuy = 0; //单位买入价格
            double PriceSel = 0; //单位卖出价格
            if (tSSRS.MaxRow > 0)
            {
                PriceBuy = Double.parseDouble(tSSRS.GetText(1, 1));
                PriceSel = Double.parseDouble(tSSRS.GetText(1, 2));
                if (PriceBuy == 0 || PriceSel == 0)
                {
                    _Unit = 0;
                }
                else
                {
                    if (_Price < 0)
                    { //资金退出帐户,卖出
                        if (moneyType != null && !moneyType.equals(""))
                        {
                            LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
                            tLMEdorItemDB.setEdorCode(moneyType);
                            if (_LCInsureAccTraceSchema.getGrpPolNo().equals("00000000000000000000"))
                            {
                                tLMEdorItemDB.setAppObj("I");
                            }
                            else
                            {
                                tLMEdorItemDB.setAppObj("G");
                            }
                            if (tLMEdorItemDB.getInfo())
                            {
                                if (tLMEdorItemDB.getPriceSellFlag() != null &&
                                    tLMEdorItemDB.getPriceSellFlag().equals(
                                            "01"))
                                {
                                    _Unit = _Price / PriceBuy;

                                }
                                else
                                {
                                    _Unit = _Price / PriceSel;
                                }
                            }
                            else
                            {
                                _Unit = _Price / PriceSel;
                            }
                        }
                    }
                    else
                    { //资金进入投资账户，买入
                        if (moneyType != null && !moneyType.equals(""))
                        {
                            LMEdorItemDB tLMEdorItemDB = new LMEdorItemDB();
                            tLMEdorItemDB.setEdorCode(moneyType);
                            if (_LCInsureAccTraceSchema.getGrpPolNo().equals("00000000000000000000"))
                            {
                                tLMEdorItemDB.setAppObj("I");
                            }
                            else
                            {
                                tLMEdorItemDB.setAppObj("G");
                            }
                            if (tLMEdorItemDB.getInfo())
                            {
                                if (tLMEdorItemDB.getPriceBuyFlag() != null &&
                                    tLMEdorItemDB.getPriceBuyFlag().equals("02"))
                                {
                                    _Unit = _Price / PriceSel;
                                }
                                else
                                {
                                    _Unit = _Price / PriceBuy;
                                }
                            }
                            else
                            {
                                _Unit = _Price / PriceBuy;
                            }
                        }
                    }
                }
            }
            else
            {
                _Unit = 0;
            }
        }
        return _Unit;
    }


    /*输入：一条LCInsureAccTraceSchema记录；
      输出：一条LCInsureAccTraceSchema记录；
      功能：根据输入的LCInsureAccTraceSchema记录，先判断该条记录是否需已作过计价处理，如果没有做过计价处理，则返回
     经过计价处理的LCInsureAccTraceSchema记录；如该记录已经作过计价处理，则返回原记录。

     */
    public static LCInsureAccTraceSchema getNewLCInsureAccTraceSchema(
            LCInsureAccTraceSchema mLCInsureAccTraceSchema, String DealDate)
    {
        LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
        tLCInsureAccTraceSchema.setSchema(mLCInsureAccTraceSchema);
        if (tLCInsureAccTraceSchema.getState().equals("0"))
        {
            double Money = 0;
            double Unit = 0;
            String LastStartDate = "";
            LastStartDate = getNextStartDate(tLCInsureAccTraceSchema.getPayDate(),
                                             tLCInsureAccTraceSchema.
                                             getInsuAccNo());
            if (LastStartDate.equals("") || LastStartDate.equals(null) ||
                LastStartDate == null)
            {
                Unit = 0;
                Money = 0;
            }
            else
            {
                String Smoney = String.valueOf(tLCInsureAccTraceSchema.
                                               getMoney());
                String SUnitcont = String.valueOf(tLCInsureAccTraceSchema.
                                                  getUnitCount());
                //money为空，则用投资单位数UnitCont计算出money
                if (Smoney == null || Smoney.equals("") ||
                    Smoney.equals(null) ||
                    tLCInsureAccTraceSchema.getMoney() == 0)
                {
                    //UnitCont>0意味着资金要进入投资账户,则用买入价。
                    if (tLCInsureAccTraceSchema.getUnitCount() >= 0)
                    {
                        Unit = tLCInsureAccTraceSchema.getUnitCount();
                        Money = calPrice(Unit, tLCInsureAccTraceSchema);
                        tLCInsureAccTraceSchema.setMoney(Money);
                    }
                    else
                    { //UnitCont<0时意味着资金要退出投资账户，用卖出价。
                        Unit = tLCInsureAccTraceSchema.getUnitCount();
                        Money = calPrice(Unit, tLCInsureAccTraceSchema);
                        tLCInsureAccTraceSchema.setMoney(Money);
                    }
                }
                //投资单位数UnitCont为空，则用money计算出投资单位数
                if (SUnitcont == null || SUnitcont.equals("") ||
                    SUnitcont.equals(null) ||
                    tLCInsureAccTraceSchema.getUnitCount() == 0)
                { //money>0意味着资金要进入投资账户,则用买入价，
                    if (tLCInsureAccTraceSchema.getMoney() > 0)
                    {
                        Money = tLCInsureAccTraceSchema.getMoney();
                        Unit = calUnit(Money, tLCInsureAccTraceSchema);
                        tLCInsureAccTraceSchema.setUnitCount(Unit);
                    }
                    else
                    { //money<0意味着资金要退出投资账户,则用卖出价
                        Money = tLCInsureAccTraceSchema.getMoney();
                        Unit = calUnit(Money, tLCInsureAccTraceSchema);
                        tLCInsureAccTraceSchema.setUnitCount(Unit);
                    }
                }

                tLCInsureAccTraceSchema.setShouldValueDate(LastStartDate);
                tLCInsureAccTraceSchema.setValueDate(DealDate);
                tLCInsureAccTraceSchema.setState("1");

            }
        }
        return tLCInsureAccTraceSchema;
    }

    public static boolean isTakePlace(LCInsureAccClassSchema aLCInsureAccClassSchema,LMRiskFeeSchema aLMRiskFeeSchema,String aDealDate, double aPolAccBala)
    {
        if (!isTakePlace(aLCInsureAccClassSchema, aLMRiskFeeSchema.getFeeCode(),aLMRiskFeeSchema.getFeeTakePlace(), aDealDate,aPolAccBala))
        {
            return false;
        }
        return true;
    }

    public static boolean isTakePlace(LCInsureAccClassSchema aLCInsureAccClassSchema, LCGrpFeeSchema aLCGrpFeeSchema, String aDealDate, double aPolAccBala)
    {
        if (!isTakePlace(aLCInsureAccClassSchema, aLCGrpFeeSchema.getFeeCode(), aLCGrpFeeSchema.getFeeTakePlace(), aDealDate, aPolAccBala))
        {
            return false;
        }
        return true;
    }

    /**
     *根据判断是否收取管理费
     * aFeeCode管理费编码
     * aFeeTakePlace管理费收取位置(对应LMRiskFee或LCGrpFee中的FeeTaskPlce)
     * */
    public static boolean isTakePlace(LCInsureAccClassSchema aLCInsureAccClassSchema, String aFeeCode, String aFeeTakePlace, String aDealDate, double aPolAccBala)
    {

        //该管理费在Trace表中是否存在，存在表示已经收取完毕
        ExeSQL tExeSQL = new ExeSQL();
        SQLwithBindVariables sqlbv8 = new SQLwithBindVariables();
        sqlbv8.sql("select count(*) from lcinsureacctrace where polno='" + "?polno?" + "' and insuaccno='" + "?insuaccno?"
                + "' and payplancode='" + "?payplancode?" + "' and feecode='" + "?aFeeCode?" + "' and valuedate='" + "?aDealDate?" + "' and state='1' and busytype<>'EB'");
        sqlbv8.put("polno", aLCInsureAccClassSchema.getPolNo());
        sqlbv8.put("insuaccno", aLCInsureAccClassSchema.getInsuAccNo());
        sqlbv8.put("payplancode", aLCInsureAccClassSchema.getPayPlanCode());
        sqlbv8.put("aFeeCode", aFeeCode);
        sqlbv8.put("aDealDate", aDealDate);
        String _value = tExeSQL.getOneValue(sqlbv8);
        if (!_value.equals("0")){
            return false;
        }

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
        if (!tLCPolDB.getInfo())
        {
            return false;
        }
        else
        {
            if (aFeeTakePlace.equals("03"))
            {
                //生效日收取/
                int year = PubFun.calInterval(tLCPolDB.getCValiDate(), aDealDate, "Y");
                if (year < 0)
                {
                    return false;
                }
                String date = PubFun.calOFDate(tLCPolDB.getCValiDate(), year, "Y", tLCPolDB.getCValiDate());

                if (getNextStartDate(date, aLCInsureAccClassSchema.getInsuAccNo()).equals(aDealDate))
                {
                    return true;
                }
                else{
                    return false;
                }
            }
            else if (aFeeTakePlace.equals("05"))
            {
                //每月的首次计价日
                LOAccUnitPriceSet tLOAccUnitPriceSet = new LOAccUnitPriceSet();
                LOAccUnitPriceDB tLOAccUnitPriceDB = new LOAccUnitPriceDB();
                SQLwithBindVariables sqlbv9 = new SQLwithBindVariables();
                sqlbv9.sql("select * from loaccunitprice where insuaccno='"+ "?insuaccno?" +
                        "' and startdate>=to_date(concat(to_char(to_date('" + "?aDealDate?" + "','yyyy-mm-dd'),'YYYY-MM'),'-01'),'yyyy-mm-dd') and state='0' order by startdate");
                sqlbv9.put("insuaccno", aLCInsureAccClassSchema.getInsuAccNo());
                sqlbv9.put("aDealDate", aDealDate);
                tLOAccUnitPriceSet = tLOAccUnitPriceDB.executeQuery(sqlbv9);
                if (tLOAccUnitPriceSet.size() > 0)
                {
                    if (tLOAccUnitPriceSet.get(1).getStartDate().equals(aDealDate))
                    {
                        return true;
                    }
                    else{
                        return false;
                    }
                }
            }
            else if (aFeeTakePlace.equals("08"))
            {
                //每个计价日收取
                LOAccUnitPriceDB tLOAccUnitPriceDB = new LOAccUnitPriceDB();
                tLOAccUnitPriceDB.setInsuAccNo(aLCInsureAccClassSchema.getInsuAccNo());
                tLOAccUnitPriceDB.setStartDate(aDealDate);

                if (tLOAccUnitPriceDB.getInfo()){
                    return true;
                }
                else{
                    return false;
                }
            }
            else if (aFeeTakePlace.equals("09"))
            {
                try
                {
                    int dealflag = 0;
                    if (tLCPolDB.getUintLinkValiFlag() != null && tLCPolDB.getUintLinkValiFlag().equals("2"))
                    { //如果客户选择签单后生效则签单后的第一个计价日扣除风险保费
                    	String nextStartDate = getNextStartDate(tLCPolDB.getSignDate(),aLCInsureAccClassSchema.getInsuAccNo());
                    	if(nextStartDate == null || "".equals(nextStartDate)){
                    		return false;
                    	}
                        if (DateFormat.getDateInstance().parse(nextStartDate).compareTo(DateFormat.getDateInstance().parse(aDealDate)) == 0)
                        {
                            //特殊处理
                            dealShouldPay(tLCPolDB.getSchema(),aLCInsureAccClassSchema,aFeeCode, aDealDate,aPolAccBala);
                            dealflag = 1;
                        }
                    }
                    else
                    { //默认犹豫期之后的第一个计价日扣除风险保费
                        LCContDB tLCContDB = new LCContDB();
                        tLCContDB.setContNo(tLCPolDB.getContNo());
                        if (tLCContDB.getInfo())
                        {
                            if (tLCContDB.getCustomGetPolDate() != null && !"".equals(tLCContDB.getCustomGetPolDate()))
                            {
                            	SQLwithBindVariables sqlbv10 = new SQLwithBindVariables();
                            	sqlbv10.sql("select to_char(adddate(to_date('" +"?CustomGetPolDate?" +"','yyyy-mm-dd'),11),'YYYY-MM-DD') from dual");
                            	sqlbv10.put("CustomGetPolDate", tLCContDB.getCustomGetPolDate());
                                String hesitateend = tExeSQL.getOneValue(sqlbv10);
                                //犹豫期后的下一个计价日(收取保单生效日至今该收未收的管理费)
                                String nextStartDate = getNextStartDate(hesitateend,aLCInsureAccClassSchema.getInsuAccNo());
                            	if(nextStartDate == null || "".equals(nextStartDate)){
                            		return false;
                            	}
                                if (DateFormat.getDateInstance().parse(nextStartDate).compareTo(DateFormat.getDateInstance().parse(aDealDate)) == 0)
                                {
                                    //特殊处理
                                    dealShouldPay(tLCPolDB.getSchema(),aLCInsureAccClassSchema,aFeeCode, aDealDate,aPolAccBala);
                                    dealflag = 1;
                                }
                            }
                            else
                            {
                                return false;
                            }
                        }
                    }
                    if (dealflag == 0)
                    {
                        //每月对应日收取
                        int month = PubFun.calInterval(tLCPolDB.getCValiDate(),aDealDate,"M");
                        if (month < 1)
                        {
                            return false;
                        }
                        String date = PubFun.calOFDate(tLCPolDB.getCValiDate(),month,"M", tLCPolDB.getCValiDate());
                        if (DateFormat.getDateInstance().parse(getNextStartDate(date, aLCInsureAccClassSchema.getInsuAccNo())).compareTo(DateFormat.getDateInstance().parse(aDealDate)) ==0)
                        {
                        	SQLwithBindVariables sqlbv11 = new SQLwithBindVariables();
                            //设置业务生效日期
                        	String nextDate = PubFun.calOFDate(date,1,"M", tLCPolDB.getCValiDate());
                        	String tSql = "select 1 from lccontstate a where contno ='"+ "?contno?" + "' and polno ='"+ "?polno?"
									+ "' and ((statetype='Available' and state='0') or (statetype='PH' and state='1')) and startdate <=to_date('"+ "?date?"+ "','yyyy-mm-dd') "
									+ " and enddate is null and not exists(select 'X' from lccontstate where polno=a.polno and contno=a.contno and statetype='Terminate' and state ='1' and startdate <=to_date('"+ "?date?"+ "','yyyy-mm-dd') "
									+ " and enddate is null) and not exists (select 'X' from lcinsureacctrace where polno =a.polno and paydate>=to_date('"+ "?date?"+ "','yyyy-mm-dd') and paydate <to_date('"+"?nextDate?"+"','yyyy-mm-dd') and moneytype='GL')";
                        	sqlbv11.sql(tSql);
                        	sqlbv11.put("contno", tLCPolDB.getContNo());
                        	sqlbv11.put("polno", tLCPolDB.getPolNo());
                        	sqlbv11.put("date", date);
                        	sqlbv11.put("nextDate", nextDate);
                        	
                        	if("1".equals(tExeSQL.getOneValue(sqlbv11)))
                        	{
                        		aLCInsureAccClassSchema.setBalaDate(date);
                        	}
                            
                            return true;
                        }
                        else
                        {
                        	SQLwithBindVariables sqlbv12 = new SQLwithBindVariables();
                        	//如果在date之前做复效，而在date这天补费没收进来还没有生效，
                        	//错过了date之后的第一个计价日导致没扣管理费的在此补扣
                        	String nextDate = PubFun.calOFDate(date,1,"M", tLCPolDB.getCValiDate());
                        	String tSql = "select 1 from lccontstate a where contno ='"+ "?contno?"+ "' and polno ='"+ "?polno?"
									+ "' and ((statetype='Available' and state='0') or (statetype='PH' and state='1')) and startdate <=to_date('"+ "?date?"+ "','yyyy-mm-dd') "
									+ " and enddate is null and not exists(select 'X' from lccontstate where polno=a.polno and contno=a.contno and statetype='Terminate' and state ='1' and startdate <=to_date('"+ "?date?"+ "','yyyy-mm-dd') "
									+ " and enddate is null) and not exists (select 'X' from lcinsureacctrace where polno =a.polno and paydate>=to_date('"+ "?date?"+ "','yyyy-mm-dd') and paydate <to_date('"+"?nextDate?"+"','yyyy-mm-dd') and moneytype='GL')";
                        	sqlbv12.sql(tSql);
                        	sqlbv12.put("contno", tLCPolDB.getContNo());
                        	sqlbv12.put("polno", tLCPolDB.getPolNo());
                        	sqlbv12.put("date", date);
                        	sqlbv12.put("nextDate", nextDate);
                        	
                        	if("1".equals(tExeSQL.getOneValue(sqlbv12)))
                        	{
//                        		设置业务生效日期
                                aLCInsureAccClassSchema.setBalaDate(date);
                                return true;
                        	}
                        	else
                        	{
                        		return false;
                        	}
                        }
                    }
                    else
                    {
                        return false;
                    }
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                    return false;
                }
            }
            else
            {
                return false;
            }
        }
        return true;
    }

    /*对延迟收取的管理费进行特殊处理*/
    public static boolean dealShouldPay(LCPolSchema aLCPolSchema, LCInsureAccClassSchema aLCInsureAccClassSchema, String aFeeCode, String aDealDate, double aPolAccBala)
    {
        int month = PubFun.calInterval(aLCPolSchema.getCValiDate(),aDealDate,"M");
        if (month < 0)
        {
            return false;
        }
        LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
        LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccFeeTraceSet mLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();

        for (int i = 0; i <= month; i++)
        {
            String date = PubFun.calOFDate(aLCPolSchema.getCValiDate(), i,"M", aLCPolSchema.getCValiDate());

            ExeSQL tExeSQL = new ExeSQL();
            SQLwithBindVariables sqlbv = new SQLwithBindVariables();
            sqlbv.sql("select count(*) from lcinsureacctrace where polno='" + "?polno?" + "' and insuaccno='" + "?insuaccno?" + "' and payplancode='" + "?payplancode?" + "' and feecode='" + "?aFeeCode?" + "' and paydate='" + "?date?" + "' and state='1'");
            sqlbv.put("polno", aLCInsureAccClassSchema.getPolNo());
            sqlbv.put("insuaccno", aLCInsureAccClassSchema.getInsuAccNo());
            sqlbv.put("payplancode", aLCInsureAccClassSchema.getPayPlanCode());
            sqlbv.put("aFeeCode", aFeeCode);
            sqlbv.put("date", date);
            
            String _value = tExeSQL.getOneValue(sqlbv);

            if (!_value.equals("0"))
            {
                continue;
            }

            LMRiskFeeSet aLMRiskFeeSet = new LMRiskFeeSet();
            LMRiskFeeDB aLMRiskFeeDB = new LMRiskFeeDB();
            aLMRiskFeeDB.setFeeCode(aFeeCode);
            aLMRiskFeeDB.setPayPlanCode(aLCInsureAccClassSchema.getPayPlanCode());
            aLMRiskFeeDB.setInsuAccNo(aLCInsureAccClassSchema.getInsuAccNo());
            aLMRiskFeeSet = aLMRiskFeeDB.query();
            if (aLMRiskFeeSet.size() > 0)
            {
                LMRiskFeeSchema aLMRiskFeeSchema = aLMRiskFeeSet.get(1);
                double feeTraceValue = 0;
                double feeTraceUnit = 0;
                if (aLMRiskFeeSchema.getFeeCalModeType().equals("1"))
                {
                    //通过sql计算得到
                    PubFun tPubFun = new PubFun();
                    Calculator tCalculator = new Calculator();
                    tCalculator.setCalCode(aLMRiskFeeSchema.getFeeCalCode());
                    //准备计算要素
                    //扣除管理费之前的投资单位

                    tCalculator.addBasicFactor("UnitCount",String.valueOf(aLCInsureAccClassSchema.getUnitCount()));
                    SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
                    sqlbv2.sql("select (case when sum(amnt) is not null then sum(amnt)  else 0 end) from lcpol where polno='" + "?polno?" + "'");
                    sqlbv2.put("polno", aLCInsureAccClassSchema.getPolNo());
                    //保险金额
                    tCalculator.addBasicFactor("Amnt",tExeSQL.getOneValue(sqlbv2));

                    double PolAccBala = aPolAccBala;
                    double AccBala = -calPrice( -aLCInsureAccClassSchema.getUnitCount(),aDealDate,aLCInsureAccClassSchema.getInsuAccNo());
                    //币种转换
                	LDExch ex = new LDExch();
                	double midCurBala = ex.toBaseCur(aLCInsureAccClassSchema.getCurrency(), SysConst.BaseCur, aDealDate, AccBala);
                	if(midCurBala == -1){//转换出错
                		midCurBala = AccBala;
                	}
                	AccBala = midCurBala;

                    //投资账户价值
                    tCalculator.addBasicFactor("AccBala",String.valueOf(AccBala));
                    //保单账户总价值
                    tCalculator.addBasicFactor("PolAccBala",String.valueOf(PolAccBala));
                    //账户价值占比
                    if (PolAccBala != 0){
                        tCalculator.addBasicFactor("AccRate",String.valueOf(AccBala / PolAccBala));
                    }
                    else{
                    	SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
                    	sqlbv3.sql("select 1/count(*) from lcinsureaccclass where polno='" +"?polno?"+"'");
                    	sqlbv3.put("polno", aLCInsureAccClassSchema.getPolNo());
                        tCalculator.addBasicFactor("AccRate", tExeSQL.getOneValue(sqlbv3));
                    }

                    //被保人年龄InsuredAge
                    LCPolDB tLCPolDB = new LCPolDB();
                    tLCPolDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
                    if (tLCPolDB.getInfo())
                    {
                        tCalculator.addBasicFactor("InsuredAge",String.valueOf(tPubFun.calAppAge(tLCPolDB.getInsuredBirthday(),date, "Y")));
                        tCalculator.addBasicFactor("InsuredSex",String.valueOf(tLCPolDB.getInsuredSex()));
                        tCalculator.addBasicFactor("OccupationType",String.valueOf(tLCPolDB.getOccupationType()));
                    }
                    SQLwithBindVariables sqlbv4 = new SQLwithBindVariables();
                    sqlbv4.sql("select (case when sum(SuppRiskScore) is not null then sum(SuppRiskScore)  else 0 end) from lcprem where polno='" + "?polno?" +"'");
                    sqlbv4.put("polno", aLCInsureAccClassSchema.getPolNo());
                    //加费评点
                    tCalculator.addBasicFactor("SuppRiskScore",tExeSQL.getOneValue(sqlbv4));

                    String tfeeTraceValue = tCalculator.calculate();

                    if (tfeeTraceValue != null && !tfeeTraceValue.equals("0"))
                    {
                        feeTraceValue = Double.parseDouble(tfeeTraceValue);
                        //计算投资单位,用卖出价
                        feeTraceUnit = -PubInsuAccFun.calUnit( -feeTraceValue,aDealDate,aLCInsureAccClassSchema.getInsuAccNo());

                        LCInsureAccTraceSchema mLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                        Reflections tReflections = new Reflections();
                        tReflections.transFields(mLCInsureAccTraceSchema,aLCInsureAccClassSchema);
                        String tLimit = PubFun.getNoLimit(mLCInsureAccTraceSchema.getManageCom());
                        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);

                        mLCInsureAccTraceSchema.setSerialNo(SerialNo);
                        mLCInsureAccTraceSchema.setMoneyType("GL");
                        mLCInsureAccTraceSchema.setPayDate(aDealDate);
                        mLCInsureAccTraceSchema.setShouldValueDate(aDealDate);
                        mLCInsureAccTraceSchema.setValueDate(aDealDate);
                        mLCInsureAccTraceSchema.setState("1");
                        mLCInsureAccTraceSchema.setMoney( -feeTraceValue);
                        mLCInsureAccTraceSchema.setUnitCount( -feeTraceUnit);
                        mLCInsureAccTraceSchema.setOperator(aLCInsureAccClassSchema.getOperator());
                        mLCInsureAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
                        mLCInsureAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
                        mLCInsureAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
                        mLCInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
                        mLCInsureAccTraceSchema.setFeeCode(aFeeCode);

                        LCInsureAccFeeTraceSchema mLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
                        tReflections = new Reflections();
                        tReflections.transFields(mLCInsureAccFeeTraceSchema,mLCInsureAccTraceSchema);
                        mLCInsureAccFeeTraceSchema.setMoneyType("GL");
                        mLCInsureAccFeeTraceSchema.setFee( -mLCInsureAccTraceSchema.getMoney());
                        mLCInsureAccFeeTraceSchema.setFeeUnit( -mLCInsureAccTraceSchema.getUnitCount());

                        tLCInsureAccTraceSet.add(mLCInsureAccTraceSchema.getSchema());
                        tLCInsureAccFeeTraceSet.add(mLCInsureAccFeeTraceSchema.getSchema());
                        mLCInsureAccTraceSchema.setPayDate(date);
                        mLCInsureAccFeeTraceSchema.setPayDate(date);
                        mLCInsureAccTraceSet.add(mLCInsureAccTraceSchema);
                        mLCInsureAccFeeTraceSet.add(mLCInsureAccFeeTraceSchema);
                    }
                }
            }
        }

        LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
        LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
        tLCInsureAccClassSet = PubInsuAccFun.createAccClassByTrace(tLCInsureAccTraceSet);

        tLCInsureAccClassFeeSet = PubInsuAccFun.createAccFeeClassByTrace(tLCInsureAccFeeTraceSet);

        tLCInsureAccSet = PubInsuAccFun.createAccByTrace(tLCInsureAccTraceSet);
        tLCInsureAccFeeSet = PubInsuAccFun.createAccFeeByTrace(tLCInsureAccFeeTraceSet);

        VData tVData = new VData();
        MMap mmap = new MMap();
        //准备公共提交数据
        mmap.put(mLCInsureAccTraceSet, "INSERT");
        mmap.put(tLCInsureAccClassSet, "UPDATE");
        mmap.put(tLCInsureAccSet, "UPDATE");
        mmap.put(mLCInsureAccFeeTraceSet, "INSERT");
        mmap.put(tLCInsureAccClassFeeSet, "DELETE&INSERT");
        mmap.put(tLCInsureAccFeeSet, "DELETE&INSERT");

        if (mmap != null && mmap.keySet().size() > 0){
            tVData.add(mmap);
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(tVData, ""))
        {
            return false;
        }
        return true;
    }

    /*是否收取保单管理费*/
    public static boolean isTakePlace(LCPolSchema aLCPolSchema,LMRiskFeeSchema aLMRiskFeeSchema, String aDealDate)
    {
        if (!isTakePlace(aLCPolSchema, aLMRiskFeeSchema.getFeeCode(), aLMRiskFeeSchema.getFeeTakePlace(), aDealDate))
        {
            return false;
        }
        return true;
    }

    /*是否收取保单管理费*/
    public static boolean isTakePlace(LCPolSchema aLCPolSchema,LCGrpFeeSchema aLCGrpFeeSchema,String aDealDate)
    {
        if (!isTakePlace(aLCPolSchema, aLCGrpFeeSchema.getFeeCode(),aLCGrpFeeSchema.getFeeTakePlace(), aDealDate))
        {
            return false;
        }
        return true;
    }

    /**
     *根据判断是否收取保单管理费
     * aFeeCode管理费编码
     * aFeeTakePlace管理费收取位置(对应LMRiskFee或LCGrpFee中的FeeTaskPlce)
     * */
    public static boolean isTakePlace(LCPolSchema aLCPolSchema,String aFeeCode, String aFeeTakePlace,String aDealDate)
    {
        //该管理费在Trace表中是否存在，存在表示已经收取完毕
        ExeSQL tExeSQL = new ExeSQL();
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql("select count(*) from lcinsureacctrace where polno='" + "?polno?" + "' and feecode='" + "?aFeeCode?" + "' and valuedate='" + "?aDealDate?" + "' and state='1'");
        sqlbv.put("polno", aLCPolSchema.getPolNo());
        sqlbv.put("aFeeCode", aFeeCode);
        sqlbv.put("aDealDate", aDealDate);
        String _value = tExeSQL.getOneValue(sqlbv);
        if (!_value.equals("0")){
            return false;
        }

        if (aFeeTakePlace.equals("07"))
        {
            int month = PubFun.calInterval(aLCPolSchema.getCValiDate(),aDealDate,"M");
            if (month < 0)
            {
                return false;
            }
            String date = PubFun.calOFDate(aLCPolSchema.getCValiDate(), month,"M",aLCPolSchema.getCValiDate());
            if (getNextStartDate(date, "").equals(aDealDate))
            {
                return true;
            }
            else{
                return false;
            }
        }
        else if (aFeeTakePlace.equals("08"))
        {
            //每个计价日收取
            LOAccUnitPriceSet tLOAccUnitPriceSet = new LOAccUnitPriceSet();
            LOAccUnitPriceDB tLOAccUnitPriceDB = new LOAccUnitPriceDB();

//            tLOAccUnitPriceDB.setRiskCode(aLCPolSchema.getRiskCode());
            tLOAccUnitPriceDB.setStartDate(aDealDate);
            tLOAccUnitPriceSet = tLOAccUnitPriceDB.query();

            if (tLOAccUnitPriceSet.size() > 0){
                return true;
            }
            else{
                return false;
            }
        }
        else if (aFeeTakePlace.equals("09"))
        {
            //每月对应日收取
            int month = PubFun.calInterval(aLCPolSchema.getCValiDate(),aDealDate,"M");
            if (month < 0)
            {
                return false;
            }
            String date = PubFun.calOFDate(aLCPolSchema.getCValiDate(), month,"M",aLCPolSchema.getCValiDate());

            if (getNextStartDate(date, "").equals(aDealDate))
            {
                return true;
            }
            else{
                return false;
            }
        }
        else
        {
            return false;
        }
    }


    /**计算账户金额
     * aFeeCalCode管理费编码
     * aLCInsureAccClassSchema账户信息
     * aLOAccUnitPriceSchema投资账户信息
     * */
    public static double calFeeValue(String aFeeCalCode,LCInsureAccClassSchema aLCInsureAccClassSchema,LOAccUnitPriceSchema aLOAccUnitPriceSchema,double aPolAccBala)
    {
        //通过sql计算得到
        PubFun tPubFun = new PubFun();
        ExeSQL tExeSQL = new ExeSQL();
        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(aFeeCalCode);
        //准备计算要素
        //扣除管理费之前的投资单位

        tCalculator.addBasicFactor("UnitCount",String.valueOf(aLCInsureAccClassSchema.getUnitCount()));
        //投资单位卖出价
        tCalculator.addBasicFactor("UnitPriceSell",String.valueOf(aLOAccUnitPriceSchema.getUnitPriceSell()));
        //投资单位买入价
        tCalculator.addBasicFactor("UnitPriceBuy",String.valueOf(aLOAccUnitPriceSchema.getUnitPriceBuy()));
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql("select (case when sum(amnt) is not null then sum(amnt)  else 0 end) from lcpol where polno='" + "?polno?" +"'");
        sqlbv.put("polno", aLCInsureAccClassSchema.getPolNo());
        //保险金额
        tCalculator.addBasicFactor("Amnt",tExeSQL.getOneValue(sqlbv));

        double AccBala = -calPrice( -aLCInsureAccClassSchema.getUnitCount(),aLOAccUnitPriceSchema);
        //币种转换
    	LDExch ex = new LDExch();
    	double midCurBala = ex.toBaseCur(aLCInsureAccClassSchema.getCurrency(), SysConst.BaseCur, aLOAccUnitPriceSchema.getStartDate(), AccBala);
    	if(midCurBala == -1){//转换出错
    		midCurBala = AccBala;
    	}
    	AccBala = midCurBala;
        
        //投资账户价值
        tCalculator.addBasicFactor("AccBala", String.valueOf(AccBala));

        //账户价值占比
        if (aPolAccBala != 0){
            tCalculator.addBasicFactor("AccRate",String.valueOf(AccBala / aPolAccBala));
        }
        else{
        	SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        	sqlbv2.sql("select 1/count(*) from lcinsureaccclass where polno='" + "?polno?" +"'");
        	sqlbv2.put("polno", aLCInsureAccClassSchema.getPolNo());
            tCalculator.addBasicFactor("AccRate", tExeSQL.getOneValue(sqlbv2));
        }
        //当月的天数
        tCalculator.addBasicFactor("MonthLength",PubInsuAccFun.getMonthLength(aLOAccUnitPriceSchema.getStartDate()));
        //上一个计价日
        tCalculator.addBasicFactor("LastStartDate",PubInsuAccFun.getLastStartDate(aLOAccUnitPriceSchema));
        //本次计价日
        tCalculator.addBasicFactor("StartDate",aLOAccUnitPriceSchema.getStartDate());
        //被保人年龄InsuredAge
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(aLCInsureAccClassSchema.getPolNo());
        if (tLCPolDB.getInfo())
        {
            int month = PubFun.calInterval(tLCPolDB.getCValiDate(),aLOAccUnitPriceSchema.getStartDate(),"M");
            String date = PubFun.calOFDate(tLCPolDB.getCValiDate(), month,"M", tLCPolDB.getCValiDate());

            tCalculator.addBasicFactor("InsuredAge",String.valueOf(tPubFun.calAppAge(tLCPolDB.getInsuredBirthday(), date, "Y")));
            tCalculator.addBasicFactor("InsuredSex",String.valueOf(tLCPolDB.getInsuredSex()));
            tCalculator.addBasicFactor("OccupationType",String.valueOf(tLCPolDB.getOccupationType()));
        }
        SQLwithBindVariables sqlbv3 = new SQLwithBindVariables();
        sqlbv3.sql("select (case when sum(SuppRiskScore) is not null then sum(SuppRiskScore)  else 0 end) from lcprem where polno='" + "?polno?" +"'");
        sqlbv3.put("polno", aLCInsureAccClassSchema.getPolNo());
        //加费评点
        tCalculator.addBasicFactor("SuppRiskScore",tExeSQL.getOneValue(sqlbv3));

        String tfeeTraceValue = tCalculator.calculate();
        logger.debug("tfeeTraceValue:" +tfeeTraceValue);

        return Double.parseDouble(tfeeTraceValue);
    }

    /*根据日期得到当月的天数*/
    public static String getMonthLength(String tDate)
    {
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String tSQL = "select datediff(to_date(concat(to_char(add_months(to_date('" + "?tDate?" +"','yyyy-mm-dd'),1),'YYYY-MM'),'-01'),'yyyy-mm-dd'),to_date(concat(to_char(to_date('" +"?tDate?" + "','yyyy-mm-dd'),'YYYY-MM'),'-01'),'yyyy-mm-dd')) from dual";
    	sqlbv.sql(tSQL);
    	sqlbv.put("tDate", tDate);
        ExeSQL tExeSQL = new ExeSQL();
        return tExeSQL.getOneValue(sqlbv);
    }

    /*得到上一个计价日*/
    public static String getLastStartDate(LOAccUnitPriceSchema tLOAccUnitPriceSchema)
    {
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String tSQL = "Select to_char(max(StartDate),'YYYY-MM-DD') from LOAccUnitPrice where insuaccno='" + "?insuaccno?" + "' and StartDate <'" + "?StartDate?" +"' and state='0'";
    	sqlbv.sql(tSQL);
    	sqlbv.put("insuaccno", tLOAccUnitPriceSchema.getInsuAccNo());
    	sqlbv.put("StartDate", tLOAccUnitPriceSchema.getStartDate());
        String tLastStartDate = "";
        ExeSQL tExeSQL = new ExeSQL();
        tLastStartDate = tExeSQL.getOneValue(sqlbv);
        if (tLastStartDate.equals(""))
        {
            tLastStartDate = tLOAccUnitPriceSchema.getStartDate();
        }
        return tLastStartDate;
    }
    
    /*得到上一个计价日 包含本次计价日*/
    public static String getLastStartDate(String _PayDate,String InsuAccNo)
    {
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String tSQL = "Select to_char(max(StartDate),'YYYY-MM-DD') from LOAccUnitPrice where insuaccno='" + "?InsuAccNo?" + "' and StartDate <='" + "?_PayDate?" +"' and state='0'";
    	sqlbv.sql(tSQL);
    	sqlbv.put("InsuAccNo", InsuAccNo);
    	sqlbv.put("_PayDate", _PayDate);
        String tLastStartDate = "";
        ExeSQL tExeSQL = new ExeSQL();
        tLastStartDate = tExeSQL.getOneValue(sqlbv);
        return tLastStartDate;
    }

    /**根据轨迹LCInsureAccTrace生成LCInsureAccClass记录
     * tLCInsureAccTraceSet为账户操作轨迹
     **/
    public static LCInsureAccClassSet createAccClassByTrace(LCInsureAccTraceSet tLCInsureAccTraceSet)
    {
        String CurrentDate = PubFun.getCurrentDate();
        String CurrentTime = PubFun.getCurrentTime();
        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++)
        {
            LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
            tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);

            String flag = "0";
            for (int j = 1; j <= tLCInsureAccClassSet.size(); j++)
            {
                if (tLCInsureAccTraceSchema.getPolNo().equals(tLCInsureAccClassSet.get(j).getPolNo()) &&
                    tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccClassSet.get(j).getInsuAccNo()) &&
                    tLCInsureAccTraceSchema.getPayPlanCode().equals(tLCInsureAccClassSet.get(j).getPayPlanCode()) &&
                    tLCInsureAccTraceSchema.getAccAscription().equals(tLCInsureAccClassSet.get(j).getAccAscription()))
                {
                    flag = "1";
                    //如果在集合中存在的话，则更新原记录
                    tLCInsureAccClassSet.get(j).setUnitCount(tLCInsureAccClassSet.get(j).getUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    tLCInsureAccClassSet.get(j).setInsuAccBala( -calPrice(-tLCInsureAccClassSet.get(j).getUnitCount(),tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));
                    tLCInsureAccClassSet.get(j).setBalaDate(getNextStartDate(tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));

                    if (tLCInsureAccTraceSchema.getBusyType() != null &&
                        tLCInsureAccTraceSchema.getBusyType().equals("NB"))
                    {
                        tLCInsureAccClassSet.get(j).setLastUnitCount(tLCInsureAccClassSet.get(j).getLastUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    }

                    tLCInsureAccClassSet.get(j).setBalaTime(CurrentTime);
                    tLCInsureAccClassSet.get(j).setModifyDate(CurrentDate);
                    tLCInsureAccClassSet.get(j).setModifyTime(CurrentTime);
                    tLCInsureAccClassSet.get(j).setOperator(tLCInsureAccTraceSchema.getOperator());
                    break;
                }
            }
            LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();

            LCInsureAccClassSet tempLCInsureAccClassSet = new LCInsureAccClassSet();
            LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
            tLCInsureAccClassDB.setPolNo(tLCInsureAccTraceSchema.getPolNo());
            tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccTraceSchema.getInsuAccNo());
            tLCInsureAccClassDB.setPayPlanCode(tLCInsureAccTraceSchema.getPayPlanCode());

            tLCInsureAccClassDB.setAccAscription(tLCInsureAccTraceSchema.getAccAscription());
            tempLCInsureAccClassSet = tLCInsureAccClassDB.query();
            //如果在集合中不存在的话，则更新class
            if (flag.equals("0"))
            {
                if (tempLCInsureAccClassSet.size() > 0)
                {
                    tLCInsureAccClassSchema = tempLCInsureAccClassSet.get(1);
                    tLCInsureAccClassSchema.setUnitCount(tLCInsureAccClassSchema.getUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    tLCInsureAccClassSchema.setInsuAccBala( -calPrice(-tLCInsureAccClassSchema.getUnitCount(),tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));
                    tLCInsureAccClassSchema.setBalaDate(getNextStartDate(tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));

                    if (tLCInsureAccTraceSchema.getBusyType() != null &&
                        tLCInsureAccTraceSchema.getBusyType().equals("NB"))
                    {
                        tLCInsureAccClassSchema.setLastUnitCount(tLCInsureAccClassSchema.getLastUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    }

                    tLCInsureAccClassSchema.setBalaTime(CurrentTime);
                    tLCInsureAccClassSchema.setModifyDate(CurrentDate);
                    tLCInsureAccClassSchema.setModifyTime(CurrentTime);
                    tLCInsureAccClassSchema.setOperator(tLCInsureAccTraceSchema.getOperator());
                    tLCInsureAccClassSet.add(tLCInsureAccClassSchema);
                }
                else
                {
                    return null;
                }
            }
        }

        return tLCInsureAccClassSet;
    }

    /**根据轨迹LCInsureAccTrace生成LCInsureAcc记录
     * tLCInsureAccTraceSet为账户操作轨迹
     **/
    public static LCInsureAccSet createAccByTrace( LCInsureAccTraceSet tLCInsureAccTraceSet)
    {
        LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
        String CurrentDate = PubFun.getCurrentDate();
        String CurrentTime = PubFun.getCurrentTime();

        for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++)
        {
            LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
            tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);

            String flag = "0";
            for (int j = 1; j <= tLCInsureAccSet.size(); j++)
            {
                if (tLCInsureAccTraceSchema.getPolNo().equals(tLCInsureAccSet.get(j).getPolNo()) &&
                    tLCInsureAccTraceSchema.getInsuAccNo().equals(tLCInsureAccSet.get(j).getInsuAccNo()))
                {
                    flag = "1";
                    //如果在集合中存在的话，则更新原记录
                    tLCInsureAccSet.get(j).setUnitCount(tLCInsureAccSet.get(j).getUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    tLCInsureAccSet.get(j).setInsuAccBala( -calPrice(-tLCInsureAccSet.get(j).getUnitCount(),tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));
                    tLCInsureAccSet.get(j).setBalaDate(getNextStartDate(tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));

                    if (tLCInsureAccTraceSchema.getBusyType() != null &&
                        tLCInsureAccTraceSchema.getBusyType().equals("NB"))
                    {
                        tLCInsureAccSet.get(j).setLastUnitCount(tLCInsureAccSet.get(j).getLastUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    }

                    tLCInsureAccSet.get(j).setBalaTime(CurrentTime);
                    tLCInsureAccSet.get(j).setModifyDate(CurrentDate);
                    tLCInsureAccSet.get(j).setModifyTime(CurrentTime);
                    tLCInsureAccSet.get(j).setOperator(tLCInsureAccTraceSchema.getOperator());
                    break;
                }
            }
            LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
            LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
            tLCInsureAccDB.setPolNo(tLCInsureAccTraceSchema.getPolNo());
            tLCInsureAccDB.setInsuAccNo(tLCInsureAccTraceSchema.getInsuAccNo());

            //如果在集合中不存在的话，则更新class
            if (flag.equals("0"))
            {
                if (tLCInsureAccDB.getInfo())
                {
                    tLCInsureAccSchema = tLCInsureAccDB.getSchema();
                    tLCInsureAccSchema.setUnitCount(tLCInsureAccSchema.getUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    tLCInsureAccSchema.setInsuAccBala( -calPrice(-tLCInsureAccSchema.getUnitCount(),tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));
                    tLCInsureAccSchema.setBalaDate(getNextStartDate(tLCInsureAccTraceSchema.getPayDate(),tLCInsureAccTraceSchema.getInsuAccNo()));

                    if (tLCInsureAccTraceSchema.getBusyType() != null &&
                        tLCInsureAccTraceSchema.getBusyType().equals("NB"))
                    {
                        tLCInsureAccSchema.setLastUnitCount(tLCInsureAccSchema.getLastUnitCount() +tLCInsureAccTraceSchema.getUnitCount());
                    }

                    tLCInsureAccSchema.setBalaTime(CurrentTime);
                    tLCInsureAccSchema.setModifyDate(CurrentDate);
                    tLCInsureAccSchema.setModifyTime(CurrentTime);
                    tLCInsureAccSchema.setOperator(tLCInsureAccTraceSchema.getOperator());
                    tLCInsureAccSet.add(tLCInsureAccSchema);
                }
                else
                {
                    return null;
                }
            }
        }

        return tLCInsureAccSet;
    }

    /***
     * 生成通知书数据
     * gaoht
     * add
     * **/
    public static LOPRTManagerSet creatPrtManagerSet(LCInsureAccTraceSet cLCInsureAccTraceSet,String cCode)
    {
        LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
        String tPrtSeqNo = PubFun1.CreateMaxNo("PRTSEQNO", cLCInsureAccTraceSet.get(1).getManageCom());
        tLOPRTManagerSchema.setPrtSeq(tPrtSeqNo);
        tLOPRTManagerSchema.setCode(cCode);
        tLOPRTManagerSchema.setOtherNo(cLCInsureAccTraceSet.get(1).getContNo());
        tLOPRTManagerSchema.setOtherNoType("00");
        tLOPRTManagerSchema.setManageCom(cLCInsureAccTraceSet.get(1).getManageCom());
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(cLCInsureAccTraceSet.get(1).getContNo());
        tLCContDB.getInfo();
        LCContSchema tLCContSchema = tLCContDB.getDB().getSchema();
        tLOPRTManagerSchema.setAgentCode(tLCContSchema.getAgentCode());
        tLOPRTManagerSchema.setReqCom(cLCInsureAccTraceSet.get(1).getManageCom());
        tLOPRTManagerSchema.setReqOperator(cLCInsureAccTraceSet.get(1).getOperator());
        tLOPRTManagerSchema.setExeCom(cLCInsureAccTraceSet.get(1).getManageCom());
        tLOPRTManagerSchema.setPrtType("0");
        tLOPRTManagerSchema.setStateFlag("0");
        tLOPRTManagerSchema.setStandbyFlag1(cLCInsureAccTraceSet.get(1).getValueDate());
        tLOPRTManagerSchema.setMakeDate(PubFun.getCurrentDate());
        tLOPRTManagerSchema.setMakeTime(PubFun.getCurrentTime());
        tLOPRTManagerSet.add(tLOPRTManagerSchema);
        return tLOPRTManagerSet;
    }

    /**根据轨迹LCInsureAccClassFeeTrace生成LCInsureAccClassFee记录
     * tLCInsureAccClassFeeTraceSet为账户操作轨迹
     **/
    public static LCInsureAccClassFeeSet createAccFeeClassByTrace(
            LCInsureAccFeeTraceSet tLCInsureAccClassFeeTraceSet)
    {
        LCInsureAccClassFeeSet tLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
        String CurrentDate = PubFun.getCurrentDate();
        String CurrentTime = PubFun.getCurrentTime();

        for (int i = 1; i <= tLCInsureAccClassFeeTraceSet.size(); i++)
        {
            LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
            tLCInsureAccFeeTraceSchema = tLCInsureAccClassFeeTraceSet.get(i);

            String flag = "0";
            for (int j = 1; j <= tLCInsureAccClassFeeSet.size(); j++)
            {
                if (tLCInsureAccFeeTraceSchema.getPolNo().equals(tLCInsureAccClassFeeSet.get(j).getPolNo()) &&
                    tLCInsureAccFeeTraceSchema.getInsuAccNo().equals(tLCInsureAccClassFeeSet.get(j).getInsuAccNo()) &&
                    tLCInsureAccFeeTraceSchema.getPayPlanCode().equals(tLCInsureAccClassFeeSet.get(j).getPayPlanCode()))
                {
                    flag = "1";
                    //如果在集合中存在的话，则更新原记录
                    tLCInsureAccClassFeeSet.get(j).setFeeUnit(tLCInsureAccClassFeeSet.get(j).getFeeUnit() +tLCInsureAccFeeTraceSchema.getFeeUnit());
                    tLCInsureAccClassFeeSet.get(j).setFee(tLCInsureAccClassFeeSet.get(j).getFee() +tLCInsureAccFeeTraceSchema.getFee());
                    tLCInsureAccClassFeeSet.get(j).setBalaDate(getNextStartDate(tLCInsureAccFeeTraceSchema.getPayDate(),tLCInsureAccFeeTraceSchema.getInsuAccNo()));
                    tLCInsureAccClassFeeSet.get(j).setBalaTime(CurrentTime);
                    tLCInsureAccClassFeeSet.get(j).setModifyDate(CurrentDate);
                    tLCInsureAccClassFeeSet.get(j).setModifyTime(CurrentTime);
                    tLCInsureAccClassFeeSet.get(j).setOperator(tLCInsureAccFeeTraceSchema.getOperator());
                    break;
                }
            }

            LCInsureAccClassFeeSchema tLCInsureAccClassFeeSchema = new LCInsureAccClassFeeSchema();
            LCInsureAccClassFeeDB tLCInsureAccClassFeeDB = new LCInsureAccClassFeeDB();
            tLCInsureAccClassFeeDB.setPolNo(tLCInsureAccFeeTraceSchema.getPolNo());
            tLCInsureAccClassFeeDB.setInsuAccNo(tLCInsureAccFeeTraceSchema.getInsuAccNo());
            tLCInsureAccClassFeeDB.setPayPlanCode(tLCInsureAccFeeTraceSchema.getPayPlanCode());
            tLCInsureAccClassFeeDB.setAccAscription(tLCInsureAccFeeTraceSchema.getAccAscription());
            // 增加了主键FeeCode
            tLCInsureAccClassFeeDB.setFeeCode(tLCInsureAccFeeTraceSchema.getFeeCode());
            LCInsureAccClassFeeSet tempLCInsureAccClassFeeSet = new LCInsureAccClassFeeSet();
            tempLCInsureAccClassFeeSet = tLCInsureAccClassFeeDB.query();

            //如果在集合中不存在的话，则更新class
            if (flag.equals("0"))
            {
                if (tempLCInsureAccClassFeeSet.size() > 0)
                {
                    tLCInsureAccClassFeeSchema = tempLCInsureAccClassFeeSet.get(1);
                    tLCInsureAccClassFeeSchema.setFeeUnit(tLCInsureAccClassFeeSchema.getFeeUnit() +tLCInsureAccFeeTraceSchema.getFeeUnit());
                    tLCInsureAccClassFeeSchema.setFee(tLCInsureAccClassFeeSchema.getFee() +tLCInsureAccFeeTraceSchema.getFee());
                }
                else
                {
                    LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();

                    LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
                    tLCInsureAccClassDB.setPolNo(tLCInsureAccFeeTraceSchema.getPolNo());
                    tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccFeeTraceSchema.getInsuAccNo());
                    tLCInsureAccClassDB.setPayPlanCode(tLCInsureAccFeeTraceSchema.getPayPlanCode());
                    tLCInsureAccClassDB.setAccAscription(tLCInsureAccFeeTraceSchema.getAccAscription());
                    LCInsureAccClassSet tempLCInsureAccClassSet = new LCInsureAccClassSet();
                    tempLCInsureAccClassSet = tLCInsureAccClassDB.query();
                    if (tempLCInsureAccClassSet.size() > 0)
                    {
                        tLCInsureAccClassSchema = tempLCInsureAccClassSet.get(1);
                        Reflections tReflections = new Reflections();
                        tReflections.transFields(tLCInsureAccClassFeeSchema,tLCInsureAccClassSchema);
                        //为了主键不为空，把FeeCode置“000000”
                        if (tLCInsureAccFeeTraceSchema.getFeeCode() == null 
                        		|| tLCInsureAccFeeTraceSchema.getFeeCode().equals("") 
                        		||tLCInsureAccFeeTraceSchema.getFeeCode().equals("null"))
                        {
                            tLCInsureAccClassFeeSchema.setFeeCode("000000");
                        }
                        else
                        {
                            tLCInsureAccClassFeeSchema.setFeeCode(tLCInsureAccFeeTraceSchema.getFeeCode());
                        }
                        tLCInsureAccClassFeeSchema.setFeeUnit(tLCInsureAccFeeTraceSchema.getFeeUnit());
                        tLCInsureAccClassFeeSchema.setFee(tLCInsureAccFeeTraceSchema.getFee());
                        tLCInsureAccClassFeeSchema.setMakeDate(CurrentDate);
                        tLCInsureAccClassFeeSchema.setMakeTime(CurrentTime);
                    }
                    else
                    {
                        return null;
                    }
                }
                tLCInsureAccClassFeeSchema.setBalaDate(getNextStartDate(tLCInsureAccFeeTraceSchema.getPayDate(),tLCInsureAccFeeTraceSchema.getInsuAccNo()));
                tLCInsureAccClassFeeSchema.setBalaTime(CurrentTime);
                tLCInsureAccClassFeeSchema.setOperator(tLCInsureAccClassFeeSchema.getOperator());
                tLCInsureAccClassFeeSchema.setModifyDate(CurrentDate);
                tLCInsureAccClassFeeSchema.setModifyTime(CurrentTime);
                tLCInsureAccClassFeeSet.add(tLCInsureAccClassFeeSchema);
            }
        }

        return tLCInsureAccClassFeeSet;
    }

    /**根据轨迹LCInsureAccFeeTrace生成LCInsureAccFee记录
     * tLCInsureAccFeeTraceSet为账户操作轨迹
     **/
    public static LCInsureAccFeeSet createAccFeeByTrace( LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet)
    {
        LCInsureAccFeeSet tLCInsureAccFeeSet = new LCInsureAccFeeSet();
        String CurrentDate = PubFun.getCurrentDate();
        String CurrentTime = PubFun.getCurrentTime();

        for (int i = 1; i <= tLCInsureAccFeeTraceSet.size(); i++)
        {
            LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
            tLCInsureAccFeeTraceSchema = tLCInsureAccFeeTraceSet.get(i);

            String flag = "0";
            for (int j = 1; j <= tLCInsureAccFeeSet.size(); j++)
            {
                if (tLCInsureAccFeeTraceSchema.getPolNo().equals(tLCInsureAccFeeSet.get(j).getPolNo()) &&
                    tLCInsureAccFeeTraceSchema.getInsuAccNo().equals(tLCInsureAccFeeSet.get(j).getInsuAccNo()))
                {
                    flag = "1";
                    //如果在集合中存在的话，则更新原记录
                    tLCInsureAccFeeSet.get(j).setFeeUnit(tLCInsureAccFeeSet.get(j).getFeeUnit() +tLCInsureAccFeeTraceSchema.getFeeUnit());
                    tLCInsureAccFeeSet.get(j).setFee(tLCInsureAccFeeSet.get(j).getFee() + tLCInsureAccFeeTraceSchema.getFee());
                    tLCInsureAccFeeSet.get(j).setBalaDate(getNextStartDate(tLCInsureAccFeeTraceSchema.getPayDate(),tLCInsureAccFeeTraceSchema.getInsuAccNo()));
                    tLCInsureAccFeeSet.get(j).setBalaTime(CurrentTime);
                    tLCInsureAccFeeSet.get(j).setModifyDate(CurrentDate);
                    tLCInsureAccFeeSet.get(j).setModifyTime(CurrentTime);
                    tLCInsureAccFeeSet.get(j).setOperator(tLCInsureAccFeeTraceSchema.getOperator());
                    break;
                }
            }

            LCInsureAccFeeSchema tLCInsureAccFeeSchema = new LCInsureAccFeeSchema();
            LCInsureAccFeeDB tLCInsureAccFeeDB = new LCInsureAccFeeDB();
            tLCInsureAccFeeDB.setPolNo(tLCInsureAccFeeTraceSchema.getPolNo());
            tLCInsureAccFeeDB.setInsuAccNo(tLCInsureAccFeeTraceSchema.getInsuAccNo());

            //如果在集合中不存在的话，则更新class
            if (flag.equals("0"))
            {
                if (tLCInsureAccFeeDB.getInfo())
                {
                    tLCInsureAccFeeSchema = tLCInsureAccFeeDB.getSchema();
                    tLCInsureAccFeeSchema.setFeeUnit(tLCInsureAccFeeSchema.getFeeUnit() +tLCInsureAccFeeTraceSchema.getFeeUnit());
                    tLCInsureAccFeeSchema.setFee(tLCInsureAccFeeSchema.getFee() +tLCInsureAccFeeTraceSchema.getFee());
                }
                else
                {
                    LCInsureAccSchema tLCInsureAccSchema = new LCInsureAccSchema();
                    LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
                    tLCInsureAccDB.setPolNo(tLCInsureAccFeeTraceSchema.getPolNo());
                    tLCInsureAccDB.setInsuAccNo(tLCInsureAccFeeTraceSchema.getInsuAccNo());
                    if (tLCInsureAccDB.getInfo())
                    {
                        tLCInsureAccSchema = tLCInsureAccDB.getSchema();
                        Reflections tReflections = new Reflections();
                        tReflections.transFields(tLCInsureAccFeeSchema,tLCInsureAccSchema);
                        tLCInsureAccFeeSchema.setFeeUnit(tLCInsureAccFeeTraceSchema.getFeeUnit());
                        tLCInsureAccFeeSchema.setFee(tLCInsureAccFeeTraceSchema.getFee());
                        tLCInsureAccFeeSchema.setMakeDate(CurrentDate);
                        tLCInsureAccFeeSchema.setMakeTime(CurrentTime);
                    }
                    else{
                        return null;
                    }
                }
                tLCInsureAccFeeSchema.setBalaDate(getNextStartDate(tLCInsureAccFeeTraceSchema.getPayDate(),tLCInsureAccFeeTraceSchema.getInsuAccNo()));
                tLCInsureAccFeeSchema.setBalaTime(CurrentTime);
                tLCInsureAccFeeSchema.setOperator(tLCInsureAccFeeTraceSchema.getOperator());
                tLCInsureAccFeeSchema.setModifyDate(CurrentDate);
                tLCInsureAccFeeSchema.setModifyTime(CurrentTime);
                tLCInsureAccFeeSet.add(tLCInsureAccFeeSchema);
            }
        }

        return tLCInsureAccFeeSet;
    }

    /**根据LCInsureAccTrace生成LCInsureAccFeeTrace
     *用于非计价日而进行管理费收取的情况
     * 在非计价日生成Trace记录，待计价日进行管理费收取
     * */
    public static LCInsureAccFeeTraceSet createFeeTraceByAccTrace(LCInsureAccTraceSet tLCInsureAccTraceSet)
    {
        LCInsureAccFeeTraceSet tLCInsureAccFeeTraceSet = new LCInsureAccFeeTraceSet();
        Reflections tReflections = new Reflections();
        String CurrentDate = PubFun.getCurrentDate();
        String CurrentTime = PubFun.getCurrentTime();
        for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++)
        {
            LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
            LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
            tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
            tReflections.transFields(tLCInsureAccFeeTraceSchema,tLCInsureAccTraceSchema);
            tLCInsureAccFeeTraceSchema.setFee( -tLCInsureAccTraceSchema.getMoney());
            tLCInsureAccFeeTraceSchema.setFeeUnit( -tLCInsureAccTraceSchema.getUnitCount());
            tLCInsureAccFeeTraceSchema.setMakeDate(CurrentDate);
            tLCInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
            tLCInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
            tLCInsureAccFeeTraceSchema.setModifyTime(CurrentTime);
        }
        return tLCInsureAccFeeTraceSet;
    }

    /**根据LCInsureAccTrace生成LCInsureAccFeeTrace
     *用于非计价日而进行管理费收取的情况
     * 在非计价日生成Trace记录，待计价日进行管理费收取
     * */
    public static LCInsureAccFeeTraceSchema createFeeTraceByAccTrace( LCInsureAccTraceSchema tLCInsureAccTraceSchema)
    {
        LCInsureAccFeeTraceSchema tLCInsureAccFeeTraceSchema = new LCInsureAccFeeTraceSchema();
        Reflections tReflections = new Reflections();
        String CurrentDate = PubFun.getCurrentDate();
        String CurrentTime = PubFun.getCurrentTime();
        tReflections.transFields(tLCInsureAccFeeTraceSchema,tLCInsureAccTraceSchema);
        tLCInsureAccFeeTraceSchema.setFee( -tLCInsureAccTraceSchema.getMoney());
        tLCInsureAccFeeTraceSchema.setFeeUnit( -tLCInsureAccTraceSchema.getUnitCount());
        tLCInsureAccFeeTraceSchema.setMakeDate(CurrentDate);
        tLCInsureAccFeeTraceSchema.setMakeTime(CurrentTime);
        tLCInsureAccFeeTraceSchema.setModifyDate(CurrentDate);
        tLCInsureAccFeeTraceSchema.setModifyTime(CurrentTime);

        return tLCInsureAccFeeTraceSchema;
    }

    /**小额账户处理
     * 欠费处理
     * */
//    public static LOArrearageSchema createArrearage(LCInsureAccClassSchema mLCInsureAccClassSchema,LCInsureAccTraceSchema mLCInsureAccTraceSchema)
//    {
//        LOArrearageSchema tLOArrearageSchema = new LOArrearageSchema();
//        String CurrentDate = PubFun.getCurrentDate();
//        String CurrentTime = PubFun.getCurrentTime();
//        tLOArrearageSchema.setContNo(mLCInsureAccClassSchema.getContNo());
//        tLOArrearageSchema.setPolNo(mLCInsureAccClassSchema.getPolNo());
//        tLOArrearageSchema.setEdorNo("00000000000000000000");
//        tLOArrearageSchema.setInsuredNo(mLCInsureAccClassSchema.getInsuredNo());
//        tLOArrearageSchema.setAppntNo(mLCInsureAccClassSchema.getAppntNo());
//        tLOArrearageSchema.setRiskCode(mLCInsureAccClassSchema.getRiskCode());
//        tLOArrearageSchema.setPayOffFlag("0");
//        tLOArrearageSchema.setFeeCode(mLCInsureAccTraceSchema.getFeeCode());
//        tLOArrearageSchema.setPayDate(mLCInsureAccTraceSchema.getPayDate());
//        tLOArrearageSchema.setMakeDate(CurrentDate);
//        tLOArrearageSchema.setMakeTime(CurrentTime);
//        tLOArrearageSchema.setModifyDate(CurrentDate);
//        tLOArrearageSchema.setModifyTime(CurrentTime);
//        tLOArrearageSchema.setOperator(mLCInsureAccClassSchema.getOperator());
//        LOArrearageDB tLOArrearageDB = new LOArrearageDB();
//        Reflections tReflections = new Reflections();
//        tReflections.transFields(tLOArrearageDB, tLOArrearageSchema);
//        if (tLOArrearageDB.getInfo())
//        {
//            if (tLOArrearageDB.getPayOffFlag().equals("0")){
//                tLOArrearageSchema.setSumMoney(tLOArrearageDB.getSumMoney());
//            }
//        }
//        else
//        {
//            tLOArrearageSchema.setSumMoney(0);
//        }
//
//        return tLOArrearageSchema;
//    }

    /******
     * @todo 帐户不足的失效处理
     * if true
     * 可以继续收取管理费
     * if false
     * 保单失效
     * Add by Gaoht
     * ******/
    public static boolean CheckAccValue(LCPolSchema cLCPolSchema, String cDealDate)
    {
        //查询欠款
    	// add by nandd temp
//        String tSql = "select * from LOArrearage where polno='" +cLCPolSchema.getPolNo() + "' and Payoffflag='0' and paydate = (select min(paydate) from LOArrearage where polno='" +cLCPolSchema.getPolNo() + "' and Payoffflag='0')";
//        LOArrearageDB tLOArrearageDB = new LOArrearageDB();
//        LOArrearageSet tLOArrearageSet = new LOArrearageSet();
//        tLOArrearageSet = tLOArrearageDB.executeQuery(tSql);
//        //没有欠款说明帐户价值足够
//        if (tLOArrearageSet.size() == 0)
//        {
//            return true;
//        }
//        tSql = "select nvl(sum(unitcount),0) from lcinsureacc where polno ='"+cLCPolSchema.getPolNo()+"'";
//        ExeSQL tExeSQL = new ExeSQL();
//        double InsuraccBala= Double.parseDouble(tExeSQL.getOneValue(tSql));
//		// 没有欠款说明帐户价值足够
//        if(InsuraccBala>0)
//        {
//        	return true;
//        }
//        String tFirstPayDate = tLOArrearageSet.get(1).getPayDate();
//
//        //查询系统帐户宽限期 ， sysvartype='1' 表示开关 1--开 0--关
//        tSql = "select nvl(sysvarvalue,0) from ldsysvar where sysvar='Acc_Avail_Period' and sysvartype='1'";
//        int tAAP = 0;
//        String tAAP_SYS = tExeSQL.getOneValue(tSql);
//        if (tAAP_SYS == null || tAAP_SYS.equals("")) {
//        	tAAP_SYS = "60";
//        }
//        tAAP = Integer.parseInt(tAAP_SYS);
//        Date tEndDate = new Date();
//        FDate tD = new FDate();
//        //得到宽限期最后一天
//        tEndDate = FinFeePubFun.calOFDate(tD.getDate(tFirstPayDate), tAAP, "D",tD.getDate(tFirstPayDate));
//        String EndDate = tD.getString(tEndDate);
//
//        //计价日如大于宽限期最后一天，则返回false;
//        FDate tFDate = new FDate();
//        if (tFDate.getDate(cDealDate).after(tFDate.getDate(EndDate)))
//        {
//        	return false;
//        }
        return true;
    }

}
