package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description:投连账户赎回
 * </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class PEdorARDetailBL {
private static Logger logger = Logger.getLogger(PEdorARDetailBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    public String CurrentDate = PubFun.getCurrentDate();
    public String CurrentTime = PubFun.getCurrentTime();

    private LPInsuAccInSet tLPInsuAccInSet = new LPInsuAccInSet();
    private LPInsuAccOutSchema mLPInsuAccOutSchema = new LPInsuAccOutSchema();
    private LPInsuAccOutSet mLPInsuAccOutSet = new LPInsuAccOutSet();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPInsureAccSet mLPInsureAccSet = new LPInsureAccSet();
    private LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
    private LPInsureAccTraceSet mLPInsureAccTraceSet = new
            LPInsureAccTraceSet();
    private LPInsureAccTraceSchema mLPInsureAccTraceSchema = new
    LPInsureAccTraceSchema();
    DecimalFormat df = new DecimalFormat("0.00"); 
    DecimalFormat df6 = new DecimalFormat("0.000000"); 
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    MMap map = new MMap();
    Reflections tRef = new Reflections();
    private String tEdorAcceptNo = "";
    public PEdorARDetailBL() {}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中

        getInputData(cInputData);
        //数据校验操作（checkdata)
        if (!checkData())
            return false;
        if (cOperate.equals("INSERT||PMAIN")) {
        	if (!checkData500())
                return false;
        	if (!checkDataTotal1000())
                return false;
            if (!dealData())
                return false;
            if (!insertData())
                return false;
        } else if (cOperate.equals("DELETE||PMAIN")) {
            if (!deleteData())
                return false;
        }
        PubSubmit tPubSubmit = new PubSubmit();
        //tPubSubmit.submitData(mInputData, cOperate);
        if (tPubSubmit.submitData(mInputData, cOperate) == false) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            return false;
        }

        return true;
    }

    public VData getResult() {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private void getInputData(VData cInputData) {
        mLPInsuAccOutSchema = (LPInsuAccOutSchema) cInputData.
                              getObjectByObjectName("LPInsuAccOutSchema", 0);
        mLPEdorItemSchema = (LPEdorItemSchema) cInputData.
                            getObjectByObjectName("LPEdorItemSchema", 0);
        mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0);
    }

    /**
     * 更新集体保单保全信息
     * @return
     */
    private boolean updateData() {

        return true;
    }

    /**
     * 校验传入的数据的合法性
     * 输出：如果发生错误则返回false,否则返回true
     */

    private boolean checkData() {
        boolean flag = true;
        LPEdorItemSet tLPEdorItemSet = new LPEdorItemSet();
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setSchema(mLPEdorItemSchema);
        tLPEdorItemSet = tLPEdorItemDB.query();
        if (tLPEdorItemSet.size() == 0) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorPGDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "无保全项目数据!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mLPEdorItemSchema = tLPEdorItemSet.get(1);
        logger.debug("***EdorState***" +
                           mLPEdorItemSchema.getEdorState().trim());
        if (mLPEdorItemSchema.getEdorState().trim().equals("2")) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorPGDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保全项目已经申请确认不能修改!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //如果地址号不为空，需要校验该地址号是否为本次保全申请产生，如果不是本次保全申请产生，则不让修改
        tEdorAcceptNo = tLPEdorItemSet.get(1).getEdorAcceptNo();

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mLPInsuAccOutSchema.getOutPolNo());
        if (!tLCPolDB.getInfo()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorPGDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "未得到转出保单号码!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mLPInsuAccOutSchema.setOutInsuredNo(tLCPolDB.getInsuredNo());
        mLPInsuAccOutSchema.setOutAppntNo(tLCPolDB.getAppntNo());
        return flag;
    }

    
    /**
     * 校验投资单位价值余额是否小于500RMB
     * 申请领取时，领取后的投资单位价值余额不得低于500元人民币(按最近一次账户评估日卖出价格计算)
     * 否则投保人只能申请退保，不能申请部分领取
     * 输出：如果单位价值余额是否小于500RMB则返回false,否则返回true
     */

    private boolean checkData500() {
    	
    	ExeSQL esee = new ExeSQL();
    	String strEdorNo = mLPInsuAccOutSchema.getEdorNo();
    	String strPolNo = mLPInsuAccOutSchema.getOutPolNo();
    	String strInsuAccNo = mLPInsuAccOutSchema.getOutInsuAccNo();
    	String SQLQSL = "select (case when sum(UnitCount) is not null then sum(UnitCount) else 0 end) from LCInsureAccClass where polno = '"+"?strPolNo?"+"' and InsuAccNo = '"+"?strInsuAccNo?"+"'";
    	String SQLQSL2 = "select (case when sum(AccOutUnit) is not null then sum(AccOutUnit) else 0 end) from LPInsuAccOut a where OutPolNo = '"+"?strPolNo?"+"' and OutInsuAccNo = '"+"?strInsuAccNo?"+"' and state !='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0', '1', '2', '3'))";
    	SQLwithBindVariables sqbv1=new SQLwithBindVariables();
    	sqbv1.sql(SQLQSL);
    	sqbv1.put("strPolNo", strPolNo);
    	sqbv1.put("strInsuAccNo", strInsuAccNo);
        SQLwithBindVariables sqbv2=new SQLwithBindVariables();
        sqbv2.sql(SQLQSL2);
        sqbv2.put("strPolNo", strPolNo);
        sqbv2.put("strInsuAccNo", strInsuAccNo);
    	double douUnitCount = mLPInsuAccOutSchema.getAccOutUnit();
//    	double douUnitCount = Double.parseDouble(esee.getOneValue(SQLQSL))-Double.parseDouble(esee.getOneValue(SQLQSL2));
//    	douUnitCount = douUnitCount - mLPInsuAccOutSchema.getAccOutUnit();
//    	String sqlbb = "select UnitPriceSell from LOAccUnitPrice where "
//    	+" InsuAccNo = '"+strInsuAccNo+"' "
//    	+" and StartDate <= '"+CurrentDate+"' "
//    	+" order by StartDate DESC";
//    	String SQL = "select UnitPriceSell from ("+sqlbb+") where rownum =1";
//    	
//    	String strUnitPriceSell = esee.getOneValue(SQL);
//    	double douUnitPriceSell = Double.parseDouble(strUnitPriceSell);
    	
    	
    	
    	//正确的取价格的方法
    	ExeSQL tExeSQL = new ExeSQL();
    	SSRS tSSRS2 = new SSRS();
        String tStartDate = "";
        String SQL1 = "select min(startdate) from loaccunitprice where  startdate>='"+"?startdate?"+"'";
        SQLwithBindVariables sbv1=new SQLwithBindVariables();
        sbv1.sql(SQL1);
        sbv1.put("startdate", mLPEdorItemSchema.getEdorValiDate());
        tSSRS2 = tExeSQL.execSQL(sbv1);
 	   tStartDate = tSSRS2.GetText(1, 1);
 	  logger.debug(SQL1);
        
        
        if(tStartDate.equals("")||tStartDate == null)
        {
     	   String SQL2 = "select max(startdate) from loaccunitprice where startdate<'"+"?startdate?"+"'";
           SQLwithBindVariables sbv2=new SQLwithBindVariables();
           sbv2.sql(SQL2);
           sbv2.put("startdate", mLPEdorItemSchema.getEdorValiDate());
     	   tSSRS2 = tExeSQL.execSQL(sbv2);
     	   tStartDate = tSSRS2.GetText(1, 1);
     	  logger.debug(SQL2);
        }
        String SQL3 = "select UnitPriceSell from loaccunitprice where insuaccno='"+"?strInsuAccNo?"+"' and startdate='"+"?tStartDate?"+"'";
        logger.debug("flag04"+SQL3);
        SQLwithBindVariables sbv3=new SQLwithBindVariables();
        sbv3.sql(SQL3);
        sbv3.put("strInsuAccNo", strInsuAccNo);
        sbv3.put("tStartDate", tStartDate);
        String ups = tExeSQL.getOneValue(sbv3);
        double douUnitPriceSell = 100;
        if(ups != null && !"".equals(ups)) {
        	douUnitPriceSell = Double.parseDouble(ups);
        } 
        	
    	
    	
    	
    	//LPInsuAccOutSet lzLPInsuAccOutSet = new LPInsuAccOutSet();
    	//LPInsuAccOutDB lzLPInsuAccOutDB = new LPInsuAccOutDB();
    	//lzLPInsuAccOutDB.setEdorNo(strEdorNo);
    	//lzLPInsuAccOutDB.setOutPolNo(strPolNo);
    	//lzLPInsuAccOutDB.setOutInsuAccNo(strInsuAccNo);
    	//lzLPInsuAccOutDB.setOutPayPlanCode(strPayPlanCode);
    	//lzLPInsuAccOutSet = lzLPInsuAccOutDB.query();
//    	String QuerySql = "select nvl(sum(AccOutUnit),0) from LPInsuAccOut where "
//    		+" EdorNo = '"+strEdorNo+"' "
//    		+" and OutPolNo = '"+strPolNo+"' "
//    		+" and OutInsuAccNo = '"+strInsuAccNo+"' "
    		//+" and OutPayPlanCode = '"+strPayPlanCode+"'"
    		;
//    	SSRS ss = new SSRS();
//    	ss = esee.execSQL(QuerySql);

    		//double outUnitCount = Double.parseDouble(ss.GetText(1, 1));
    		//douUnitCount = douUnitCount - outUnitCount;
    		//logger.debug("保全去掉的单位数："+outUnitCount);

    	
    	double douMain = douUnitCount*douUnitPriceSell;
    	douMain = Double.parseDouble(df.format(douMain));
    	logger.debug("预计领取价值："+douMain);
//    	if(douMain!=0&&douMain<1000)
//    	{
//    		CError tError = new CError();
//            tError.moduleName = "PEdorTIDetailBL";
//            tError.functionName = "checkData";
//            tError.errorMessage = "当日领取的投资单位价值不得低于1000元人民币！";
//            this.mErrors.addOneError(tError);
//            return false;
//    	}
    	
    	return true;
    }
    
    
    
    /**
     * 校验投资单位总价值余额是否小于1000RMB
     * 申请领取时，领取后的投资单位总价值余额不得低于1000元人民币(按最近一次账户评估日卖出价格计算)
     * 否则投保人只能申请退保，不能申请部分领取
     * 输出：如果单位总价值余额是否小于1000RMB则返回false,否则返回true
     */

    private boolean checkDataTotal1000() {
    	
    	LCInsureAccDB lzLCInsureAccDB = new LCInsureAccDB();
    	LCInsureAccSet lzLCInsureAccSet = new LCInsureAccSet();
    	lzLCInsureAccDB.setPolNo(mLPInsuAccOutSchema.getOutPolNo());
    	lzLCInsureAccSet = lzLCInsureAccDB.query();
    	
    	double mainmoney = 0;
    	
    	for(int i=1;i<=lzLCInsureAccSet.size();i++)
    	{
    	
    	ExeSQL esee = new ExeSQL();
    	String strEdorNo = mLPInsuAccOutSchema.getEdorNo();
    	String strPolNo = mLPInsuAccOutSchema.getOutPolNo();
    	String strInsuAccNo = lzLCInsureAccSet.get(i).getInsuAccNo();
    	String SQLQSL = "select (case when sum(UnitCount) is not null then sum(UnitCount) else 0 end) from LCInsureAccClass where polno = '"+"?strPolNo?"+"' and InsuAccNo = '"+"?strInsuAccNo?"+"'";
    	String SQLQSL2 = "select (case when sum(AccOutUnit) is not null then sum(AccOutUnit) else 0 end) from LPInsuAccOut a where OutPolNo = '"+"?strPolNo?"+"' and OutInsuAccNo = '"+"?strInsuAccNo?"+"' and state !='0' and edorno in (select edorno from lpedoritem where contno = a.contno and edorstate in ('0', '1', '2', '3'))";
    	SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    	sqlbv1.sql(SQLQSL);
    	sqlbv1.put("strPolNo", strPolNo);
    	sqlbv1.put("strInsuAccNo", strInsuAccNo);
    	SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    	sqlbv2.sql(SQLQSL2);
    	sqlbv2.put("strPolNo", strPolNo);
    	sqlbv2.put("strInsuAccNo", strInsuAccNo);
    	double douUnitCount = Double.parseDouble(esee.getOneValue(sqlbv1))-Double.parseDouble(esee.getOneValue(sqlbv2));
    	
    	if(mLPInsuAccOutSchema.getOutInsuAccNo().equals(strInsuAccNo))
    	{
    	douUnitCount = douUnitCount - mLPInsuAccOutSchema.getAccOutUnit();
    	logger.debug("本次的OutInsuAccNo是:"+mLPInsuAccOutSchema.getOutInsuAccNo()+",扣掉单位数:"+mLPInsuAccOutSchema.getAccOutUnit());
    	}
    	String sqlbb = "select UnitPriceSell from LOAccUnitPrice where "
    	+" InsuAccNo = '"+"?strInsuAccNo?"+"' "
    	+" and StartDate <= '"+"?CurrentDate?"+"' "
    	+" order by StartDate DESC";
    	String SQL = "";
    	if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	SQL = "select UnitPriceSell from ("+sqlbb+") where rownum =1";
    	}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    		SQL = "select UnitPriceSell from ("+sqlbb+") g limit 0,1";	
    	}
    	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    	sqlbv.sql(SQL);
    	sqlbv.put("strInsuAccNo", strInsuAccNo);
    	sqlbv.put("CurrentDate", CurrentDate);
    	String strUnitPriceSell = esee.getOneValue(sqlbv);
    	double douUnitPriceSell = Double.parseDouble(strUnitPriceSell);
    	
    	//LPInsuAccOutSet lzLPInsuAccOutSet = new LPInsuAccOutSet();
    	//LPInsuAccOutDB lzLPInsuAccOutDB = new LPInsuAccOutDB();
    	//lzLPInsuAccOutDB.setEdorNo(strEdorNo);
    	//lzLPInsuAccOutDB.setOutPolNo(strPolNo);
    	//lzLPInsuAccOutDB.setOutInsuAccNo(strInsuAccNo);
    	//lzLPInsuAccOutDB.setOutPayPlanCode(strPayPlanCode);
    	//lzLPInsuAccOutSet = lzLPInsuAccOutDB.query();
//    	String QuerySql = "select nvl(sum(AccOutUnit),0) from LPInsuAccOut where "
//    		+" EdorNo = '"+strEdorNo+"' "
//    		+" and OutPolNo = '"+strPolNo+"' "
//    		+" and OutInsuAccNo = '"+strInsuAccNo+"' "
//    		//+" and OutPayPlanCode = '"+strPayPlanCode+"'"
//    		;
//    	SSRS ss = new SSRS();
//    	ss = esee.execSQL(QuerySql);

    		//double outUnitCount = Double.parseDouble(ss.GetText(1, 1));
    		//douUnitCount = douUnitCount - outUnitCount;
    		//logger.debug("保全去掉的单位数："+outUnitCount);

    	
    	double douMain = douUnitCount*douUnitPriceSell;
    	douMain = Double.parseDouble(df.format(douMain));
    	logger.debug("预计剩余总价值："+douMain);
    	mainmoney = mainmoney+douMain;
    	}
    	logger.debug("投资单位总价值余额@_@...::"+mainmoney);
    	
    	//估算12个月的风险保费
    	
    	//由于期缴的钱进帐户时候就会收取,所以这里取上次收取的费用,如果没有收取,就说明不需要收取,返回0
    	ExeSQL manageEX = new ExeSQL();
    	String manageSQL = "select (case when -sum(money)*12 is not null then -sum(money)*12 else 0 end) from lcinsureacctrace c where c.contno = '"+"?contno?"+"' and c.moneytype = 'GL' and c.paydate = (select max(paydate) from lcinsureacctrace where contno = c.contno and moneytype = c.moneytype)";
    	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    	sqlbv.sql(manageSQL);
    	sqlbv.put("contno", mLPEdorItemSchema.getContNo());
    	
    	double managefee12 = Double.parseDouble(manageEX.getOneValue(sqlbv));
//    	if(mainmoney<1000||mainmoney<managefee12)
    	if(mainmoney<2000)
    	{
    		CError tError = new CError();
            tError.moduleName = "PEdorTIDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "账户的投资单位总价值预估余额不得低于2000元人民币!";
            this.mErrors.addOneError(tError);
            return false;
    	}
    	
    	return true;
    }
    
    
    

    /**
     * 准备需要保存的数据
     */
    private boolean dealData() {
        Reflections tReflections = new Reflections();
        String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
        
        mLPInsuAccOutSchema.setState("1");
        mLPInsuAccOutSchema.setOperator(mGlobalInput.Operator);
        mLPInsuAccOutSchema.setMakeDate(CurrentDate);
        mLPInsuAccOutSchema.setMakeTime(CurrentTime);
        mLPInsuAccOutSchema.setModifyDate(CurrentDate);
        mLPInsuAccOutSchema.setModifyTime(CurrentTime);

        LCInsureAccSet tLCInsureAccSet = new LCInsureAccSet();
        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        tLCInsureAccDB.setPolNo(mLPInsuAccOutSchema.getOutPolNo());
        tLCInsureAccDB.setInsuAccNo(mLPInsuAccOutSchema.
                                    getOutInsuAccNo());
        tLCInsureAccSet = tLCInsureAccDB.query();
        if (tLCInsureAccSet.size() == 0) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorRDDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "未找到该保单账户信息!";
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
            LPInsureAccSchema mLPInsureAccSchema = new LPInsureAccSchema();
            tReflections.transFields(mLPInsureAccSchema,
                                     tLCInsureAccSet.get(i));
            mLPInsureAccSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPInsureAccSchema.setEdorType(mLPEdorItemSchema.getEdorType());
            mLPInsureAccSchema.setMakeDate(CurrentDate);
            mLPInsureAccSchema.setMakeTime(CurrentTime);
            mLPInsureAccSchema.setMakeDate(CurrentDate);
            mLPInsureAccSchema.setMakeTime(CurrentTime);
            mLPInsureAccSet.add(mLPInsureAccSchema);
        }

        LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
        LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
        tLCInsureAccClassDB.setPolNo(mLPInsuAccOutSchema.getOutPolNo());
        tLCInsureAccClassDB.setInsuAccNo(mLPInsuAccOutSchema.
                                         getOutInsuAccNo());
        //tLCInsureAccClassDB.setPayPlanCode(mLPInsuAccOutSchema.getOutPayPlanCode());
        tLCInsureAccClassSet = tLCInsureAccClassDB.query();
        if (tLCInsureAccClassSet.size() == 0) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorRDDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "未找到该保单账户信息!";
            this.mErrors.addOneError(tError);
            return false;
        }

        double sumunitcount = 0;
        for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
            LPInsureAccClassSchema mLPInsureAccClassSchema = new
                    LPInsureAccClassSchema();
            tReflections.transFields(mLPInsureAccClassSchema,
                                     tLCInsureAccClassSet.get(i));
            mLPInsureAccClassSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPInsureAccClassSchema.setEdorType(mLPEdorItemSchema.getEdorType());
            mLPInsureAccClassSchema.setMakeDate(CurrentDate);
            mLPInsureAccClassSchema.setMakeTime(CurrentTime);
            mLPInsureAccClassSchema.setMakeDate(CurrentDate);
            mLPInsureAccClassSchema.setMakeTime(CurrentTime);
            sumunitcount = sumunitcount+tLCInsureAccClassSet.get(i).getUnitCount();
            mLPInsureAccClassSet.add(mLPInsureAccClassSchema);
        }
        
        

        double outmainmoney = 0;
        double outonemoney = 0;
        for(int i=1;i<=tLCInsureAccClassSet.size();i++)
        {
        	LPInsureAccTraceSchema lzLPInsureAccTraceSchema = new LPInsureAccTraceSchema();
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
        tReflections.transFields(lzLPInsureAccTraceSchema,
                                 tLCInsureAccClassSet.get(i));
        lzLPInsureAccTraceSchema.setSerialNo(SerialNo);
        lzLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        lzLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.
                                            getEdorType());
        lzLPInsureAccTraceSchema.setMoneyType("AR");
        lzLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.
                                           getEdorValiDate()); //保全生效日期
        outonemoney =  -mLPInsuAccOutSchema.getAccOutUnit()*tLCInsureAccClassSet.get(i).getUnitCount()/sumunitcount;
        outonemoney = Double.parseDouble(df6.format(outonemoney));
        
        if(i==tLCInsureAccClassSet.size())
        {
        	outonemoney = -mLPInsuAccOutSchema.getAccOutUnit()-outmainmoney;
        	logger.debug("这次调整精度*_*././."+mLPInsuAccOutSchema.getAccOutUnit()+"||"+outmainmoney);
        }
        else
        {
        	outmainmoney = outmainmoney+outonemoney;
        }
        logger.debug("第"+i+"次的转出记录::>_<...."+outonemoney);
        //outonemoney = Double.parseDouble(df6.format(outonemoney));
        lzLPInsureAccTraceSchema.setUnitCount(outonemoney);
        if(lzLPInsureAccTraceSchema.getUnitCount()==0)
        {
        	continue;
        }
        //lzLPInsureAccTraceSchema.setMoney( -mLPInsuAccOutSchema.getAccOutBala());
        lzLPInsureAccTraceSchema.setState("0");
        lzLPInsureAccTraceSchema.setAccAlterType("3"); //保全
        lzLPInsureAccTraceSchema.setBusyType(mLPEdorItemSchema.
                                            getEdorType()); //账户转换
        lzLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.
                                              getEdorNo());
        lzLPInsureAccTraceSchema.setFeeCode("000000");
        lzLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
        lzLPInsureAccTraceSchema.setMakeDate(CurrentDate);
        lzLPInsureAccTraceSchema.setMakeTime(CurrentTime);
        lzLPInsureAccTraceSchema.setMakeDate(CurrentDate);
        lzLPInsureAccTraceSchema.setMakeTime(CurrentTime);
        lzLPInsureAccTraceSchema.setPayPlanCode(tLCInsureAccClassSet.get(i).getPayPlanCode());
        logger.debug("lzLPInsureAccTraceSchema:" +
        		lzLPInsureAccTraceSchema.encode());
        
        LPInsuAccOutSchema lzLPInsuAccOutSchema = new LPInsuAccOutSchema();
        tReflections.transFields(lzLPInsuAccOutSchema,
        		mLPInsuAccOutSchema);
        lzLPInsuAccOutSchema.setOutPayPlanCode(tLCInsureAccClassSet.get(i).getPayPlanCode());
        lzLPInsuAccOutSchema.setAccOutUnit(-outonemoney);
        lzLPInsuAccOutSchema.setSerialNo(SerialNo);
        mLPInsuAccOutSet.add(lzLPInsuAccOutSchema);
        lzLPInsureAccTraceSchema.setPayPlanCode(lzLPInsuAccOutSchema.getOutPayPlanCode());
        lzLPInsureAccTraceSchema.setOtherNo(mLPEdorItemSchema.getEdorAcceptNo());
        lzLPInsureAccTraceSchema.setOtherType("2");
        mLPInsureAccTraceSet.add(lzLPInsureAccTraceSchema);
        logger.debug("cnm____><><><"+lzLPInsureAccTraceSchema.getPayPlanCode()+"//\\//\\"+lzLPInsuAccOutSchema.getOutPayPlanCode());
        logger.debug("cnm____mLPInsureAccTraceSet:" +
        		mLPInsureAccTraceSet.encode());
        }
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean insertData() {
        map.put(mLPInsureAccSet, "DELETE&INSERT");
        map.put(mLPInsureAccClassSet, "DELETE&INSERT");
        map.put(mLPInsuAccOutSet, "INSERT");
        map.put(mLPInsureAccTraceSet, "INSERT");
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql("update LPEdorItem set EdorState='1' where EdorAcceptNo='" +
                "?tEdorAcceptNo?" + "'");
        sqlbv.put("tEdorAcceptNo", tEdorAcceptNo);
        map.put(sqlbv, "UPDATE");

        mInputData.clear();
        mInputData.add(map);
        mResult.clear();
        mResult.add(mLPInsuAccOutSchema);

        return true;
    }


    /**
     * 准备需要保存的数据
     */
    private boolean deleteData() {
    	logger.debug("mLPInsuAccOutSchema wo see see ::>_<::"+mLPInsuAccOutSchema.encode());
    	logger.debug("mLPEdorItemSchema wo see see ::>_<::"+mLPEdorItemSchema.encode());
        
    	LPInsuAccOutDB mLPInsuAccOutDB = new LPInsuAccOutDB();
        LPInsuAccOutSet lzLPInsuAccOutSet = new LPInsuAccOutSet();
        mLPInsuAccOutDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPInsuAccOutDB.setEdorType(mLPEdorItemSchema.getEdorType());
        mLPInsuAccOutDB.setOutInsuAccNo(mLPInsuAccOutSchema.getOutInsuAccNo());
        lzLPInsuAccOutSet = mLPInsuAccOutDB.query();
        map.put(lzLPInsuAccOutSet, "DELETE");
        LPInsureAccDB mLPInsureAccDB = new LPInsureAccDB();
        mLPInsureAccDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPInsureAccDB.setEdorType(mLPEdorItemSchema.getEdorType());
        mLPInsureAccDB.setInsuAccNo(mLPInsuAccOutSchema.getOutInsuAccNo());
        mLPInsureAccSet = mLPInsureAccDB.query();
        if (mLPInsureAccSet.size() > 0) {
            map.put(mLPInsureAccSet, "DELETE");
        }

        LPInsureAccClassDB mLPInsureAccClassDB=new LPInsureAccClassDB();
        mLPInsureAccClassDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPInsureAccClassDB.setEdorType(mLPEdorItemSchema.getEdorType());
        mLPInsureAccClassDB.setInsuAccNo(mLPInsuAccOutSchema.getOutInsuAccNo());
        mLPInsureAccClassSet=mLPInsureAccClassDB.query();
        if(mLPInsureAccClassSet.size()>0)
        {
            map.put(mLPInsureAccClassSet, "DELETE");
        }

        LPInsureAccTraceDB mLPInsureAccTraceDB = new LPInsureAccTraceDB();
        //mLPInsureAccTraceDB.setSerialNo(mLPInsuAccOutSchema.getSerialNo());
        mLPInsureAccTraceDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        mLPInsureAccTraceDB.setEdorType(mLPEdorItemSchema.getEdorType());
        mLPInsureAccTraceDB.setInsuAccNo(mLPInsuAccOutSchema.getOutInsuAccNo());
        mLPInsureAccTraceSet = mLPInsureAccTraceDB.query();
        if (mLPInsureAccTraceSet.size()>0) {
            map.put(mLPInsureAccTraceSet, "DELETE");
        } else {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorRDDetailBL";
            tError.functionName = "deleteData";
            tError.errorMessage = "没有交易信息，请确认后再进行交易取消!";
            this.mErrors.addOneError(tError);
            return false;
        }
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql("update LPEdorItem set EdorState='1' where EdorAcceptNo='" +
                "?tEdorAcceptNo?" + "'");
        sqlbv.put("tEdorAcceptNo", tEdorAcceptNo);
        map.put(sqlbv, "UPDATE");
        mInputData.clear();
        mInputData.add(map);
        mResult.clear();
        mResult.add(mLPInsuAccOutSchema);

        return true;
    }

}



