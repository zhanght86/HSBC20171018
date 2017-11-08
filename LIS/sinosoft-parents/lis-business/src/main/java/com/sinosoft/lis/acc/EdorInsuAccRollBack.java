package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;


import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

/**
 * <p>Title: </p>
 *
 * <p>Description:保全差错回退处理 </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * <p>Company:sinosoft </p>
 *
 * @author:ck
 * @version 1.0
 */
public class EdorInsuAccRollBack {
private static Logger logger = Logger.getLogger(EdorInsuAccRollBack.class);

    private GlobalInput _GlobalInput = new GlobalInput();
    public CErrors mErrors = new CErrors();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /*管理费收取时间*/
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private PubInsuAccFun tPubInsuAccFun = new PubInsuAccFun();
    private MMap map = new MMap();
    /*结果集*/
    private LCInsureAccSet tLCInsureAccSet;
    private LCInsureAccClassSet tLCInsureAccClassSet;
    private LCInsureAccTraceSet tLCInsureAccTraceSet;
    private String mCurrentDate = PubFun.getCurrentDate();
    private String mCurrentTime = PubFun.getCurrentTime();
    //
    private LCInsureAccSet updLCInsureAccSet = new LCInsureAccSet();
    private LCInsureAccClassSet updLCInsureAccClassSet = new
            LCInsureAccClassSet();
    private LCInsureAccTraceSet InsertLCInsureAccTraceSet = new
            LCInsureAccTraceSet();
    private LCInsureAccFeeSet updLCInsureAccFeeSet = new LCInsureAccFeeSet();

    private LCInsureAccClassFeeSet updLCInsureAccClassFeeSet = new
            LCInsureAccClassFeeSet();
    private LCInsureAccFeeTraceSet InsertLCInsureAccFeeTraceSet = new
            LCInsureAccFeeTraceSet();
    private PubSubmit tPubSubmit = new PubSubmit();
    /*默认当天计算管理费*/
    public EdorInsuAccRollBack(GlobalInput aGlobalInput) {
        _GlobalInput = aGlobalInput;
    }

    public boolean rollBackEdorInsuAcc(LPEdorItemSchema tLPEdorItemSchema) {
    	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    	//查询出保单
        String sql = " select * from lcpol " +
                     " where contno =  '" + "?contno?" +
                     "' and polno=mainpolno";
        sqlbv1.sql(sql);
        sqlbv1.put("contno", tLPEdorItemSchema.getContNo());
        ExeSQL tExeSQL = new ExeSQL();
        LCPolDB tLCPolDB = new LCPolDB();
        LCPolSet tLCPolSet = tLCPolDB.executeQuery(sqlbv1);
        if (tLCPolDB.mErrors.needDealError()) {
            CError.buildErr(this, "保单查询失败!");
            return false;
        }
        if (tLCPolSet == null || tLCPolSet.size() < 1) {
            CError.buildErr(this, "保单查询失败!");
            return false;
        }

        String strpolno = tLCPolSet.get(1).getPolNo();
        //如果退保投连险未计价回退，删除trace记录，恢复账户
        //如果退保投连险有计价回退,调用延迟自动处理:账户管理费重算（投资账户）
        sql = " select case when exists (select 1 from lcinsureacctrace " +
                " where polno = " +
                " '" + "?strpolno?" + "'" +
                " and state='0' and busytype='" + "?busytype?" +
                "' and accalterno='" + "?accalterno?" +
                "') then 1 else 0 end from dual";
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        sqlbv2.sql(sql);
        sqlbv2.put("strpolno", strpolno);
        sqlbv2.put("busytype", tLPEdorItemSchema.getEdorType());
        sqlbv2.put("accalterno", tLPEdorItemSchema.getEdorNo());
        String sInvestFlag = tExeSQL.getOneValue(sqlbv2);
        if ("1".equals(sInvestFlag)) {
            //未计价回退
            if (!backInsureAcc(tLPEdorItemSchema.getEdorNo(),
                               tLPEdorItemSchema.getEdorType(),
                               tLCPolSet.get(1).getPolNo())) {
                return false;
            }
        } else {
            if (!InvestbackInsureAcc(tLPEdorItemSchema.getEdorNo(),
                                     tLPEdorItemSchema.getEdorType(),
                                     tLCPolSet.get(1).getPolNo())) {
                return false;
            }
        }
        return true;
    }

    /**
     * 退保投连险未计价回退
     * @param sPolNo
     */
    private boolean backInsureAcc(String sEdorNo, String sEdorType,
                                  String sPolNo) {
        Reflections tRef = new Reflections();
        //查出P表中的结算履历(账户转换时的轨迹)
        LCInsureAccTraceSet delLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        tLCInsureAccTraceDB.setAccAlterNo(sEdorNo);
        tLCInsureAccTraceDB.setBusyType(sEdorType);
        tLCInsureAccTraceDB.setPolNo(sPolNo);
        delLCInsureAccTraceSet = tLCInsureAccTraceDB.query();
        if (delLCInsureAccTraceSet.size() == 0) {
            CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
            return false;
        }

        map.put(delLCInsureAccTraceSet, "DELETE");
        mResult.add(map);

        if (!tPubSubmit.submitData(mResult, "")) {
            CError.buildErr(this, "保全回退处理数据库更新错误!");
            return false;
        }
        return true;
    }

    /**
     * 投连计价回退
     * @param sPolNo
     */
    private boolean InvestbackInsureAcc(String sEdorNo, String sEdorType,
                                        String sPolNo) {
        Reflections tRef = new Reflections();
        //查出P表中的结算履历(账户转换时的结算)
        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        tLCInsureAccTraceDB.setAccAlterNo(sEdorNo);
        tLCInsureAccTraceDB.setBusyType(sEdorType);
        tLCInsureAccTraceDB.setPolNo(sPolNo);
        
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        String sql = " select * from (select * from lcinsureacctrace a where accalterno='" +
        "?sEdorNo?" + "' and polno='" + "?sPolNo?" + "' "
        + "union all "
        + "select * from lcinsureacctrace a where moneytype='GL' and (polno,payplancode,insuaccno) in(select polno,payplancode,insuaccno from lcinsureacctrace where accalterno='" +
        "?sEdorNo?" +
        "' and polno='" + "?sPolNo?" +
        "' and a.paydate>=shouldvaluedate and paydate<=(select (case when to_date('" +
        "?mCurrentDate?" + "','yyyy-mm-dd')>valuedate then to_date('" + "?mCurrentDate?" + "','yyyy-mm-dd') else valuedate end) from dual))) l order by payplancode,insuaccno,shouldvaluedate desc";
        sqlbv.sql(sql);
        sqlbv.put("sEdorNo", sEdorNo);
        sqlbv.put("sPolNo", sPolNo);
        sqlbv.put("mCurrentDate", mCurrentDate);
        LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.
                executeQuery(sqlbv);
        logger.debug("sql:" + sql);
        if (tLCInsureAccTraceDB.mErrors.needDealError()) {
            CError.buildErr(this, "查询保全保险帐户计价履历信息失败!");
            return false;
        }
        if (tLCInsureAccTraceSet == null || tLCInsureAccTraceSet.size() < 1) {
            CError.buildErr(this, "没有保全保险帐户结算履历信息");
            return true;
        }

        //计价后回退
        ManageFeeRollBack tManageFeeRollBack = new ManageFeeRollBack(
                _GlobalInput);
        if (!tManageFeeRollBack.rollBackManageFee(tLCInsureAccTraceSet)) {
            CError.buildErr(this, "管理费回退错误!");
            return false;
        }

        if (!tPubSubmit.submitData(tManageFeeRollBack.getResult(), "")) {
            CError.buildErr(this, "管理费回退数据库更新错误!");
            return false;
        }

        /*管理费重算*/
        if (!ReCalManageFee(sPolNo)) {
            CError.buildErr(this, "管理费重算错误!");
            return false;
        }

        return true;
    }

    /**
     * 管理费重算
     * state='3' and moneytype='GL'的为经过回退的管理费，将这些管理费进行重算
     * */
    private boolean ReCalManageFee(String sPolNo) {
        /*
                     sql = "select distinct shouldvaluedate from lcinsureacctrace a where moneytype='GL' and (polno,payplancode,insuaccno) in(select polno,payplancode,insuaccno from lcinsureacctrace where accalterno='" +
         sEdorNo +
         "' and polno='" + sPolNo +
         "' and a.paydate>=shouldvaluedate and paydate<=(select (case when to_date('" +
              mCurrentDate + "')>valuedate then to_date('" + mCurrentDate +
              "') else valuedate end) from dual)) order by shouldvaluedate";
         */
    	String sql = "select distinct shouldvaluedate from lcinsureacctrace a where moneytype='GL' and polno='" +
                "?sPolNo?" +
                "' and state='3' order by shouldvaluedate";
    	SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
    	sqlbv1.sql(sql);
    	sqlbv1.put("sPolNo", sPolNo);
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = tExeSQL.execSQL(sqlbv1);

        LCPolSet tLCPolSet = new LCPolSet();
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(sPolNo);
        tLCPolSet = tLCPolDB.query();

        for (int i = 1; i <= tSSRS.getMaxRow(); i++) {
            String dealDate = tSSRS.GetText(i, 1);
            if (dealDate == null || dealDate.equals("")) {
                CError.buildErr(this, "账户管理费轨迹计价日期为空!");
                return false;
            }
            DealPolManageFee _DealPolManageFee = new
                                                 DealPolManageFee(_GlobalInput,
                    dealDate);
            _DealPolManageFee.calPolManageFee(tLCPolSet);
        }
        return true;
    }

    public static void main(String arg[]) {

    }

}
