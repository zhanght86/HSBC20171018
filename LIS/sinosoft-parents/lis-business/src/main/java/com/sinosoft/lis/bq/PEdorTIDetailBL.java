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
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.text.DecimalFormat;;;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:投连账户转换
 * </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class PEdorTIDetailBL {
private static Logger logger = Logger.getLogger(PEdorTIDetailBL.class);
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

    DecimalFormat df = new DecimalFormat("0.00");
    DecimalFormat df6 = new DecimalFormat("0.000000");

    private LPInsuAccInSet tLPInsuAccInSet = new LPInsuAccInSet();
    private LPInsuAccOutSchema mLPInsuAccOutSchema = new LPInsuAccOutSchema();
    private LPInsuAccOutSet mLPInsuAccOutSet = new LPInsuAccOutSet();
    private LPInsuAccInSet mLPInsuAccInSet = new LPInsuAccInSet();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPInsureAccSet mLPInsureAccSet = new LPInsureAccSet();
    private LPInsureAccClassSet mLPInsureAccClassSet = new LPInsureAccClassSet();
    private LPInsureAccTraceSchema mLPInsureAccTraceSchema = new
    LPInsureAccTraceSchema();
    private LPInsureAccTraceSet mLPInsureAccTraceSet = new
    LPInsureAccTraceSet();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    MMap map = new MMap();
    Reflections tRef = new Reflections();
    private String tEdorAcceptNo = "";
    public PEdorTIDetailBL() {}

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
    	logger.debug("***PEdorTIDetailBL-----submitData***");
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中

        getInputData(cInputData);

        if (!checkData())
            return false;

        if (cOperate.equals("INSERT||DEALOUT"))
        {
        	if (!checkDataOUT())
                return false;
        	if (!dealOUT())
                return false;
        }
        else if (cOperate.equals("DELETE||CANCELOUT"))
        {
        	if (!deleteOUT())
              return false;
        }
        else if (cOperate.equals("INSERT||DEALIN"))
        {
        	if (!checkDataIN())
                return false;
        	if (!dealIN())
                return false;
        }
        else if (cOperate.equals("DELETE||CANCELIN"))
        {
        	LPInsuAccInDB lzLPInsuAccInDB = new LPInsuAccInDB();
        	lzLPInsuAccInDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        	//LPInsuAccInSet lzLPInsuAccInSet = new LPInsuAccInSet();
        	mLPInsuAccInSet = lzLPInsuAccInDB.query();
//        	logger.debug("MSN上不去了aaa"+mLPInsuAccInSet.encode());

//        	String strDelete = "delete from LPInsuAccIn where edorno = '"+mLPEdorItemSchema.getEdorNo()+"'";
//        	logger.debug("删除LPInsuAcc::"+strDelete);
        	map.put(mLPInsuAccInSet, "DELETE");
        }








        //数据校验操作（checkdata)

//        if (cOperate.equals("INSERT||PMAIN")) {
//        	if (!checkData1000())
//                return false;
//            if (!dealData())
//                return false;
            if (!insertData())
                return false;
//        } else if (cOperate.equals("DELETE||PMAIN")) {
//            if (!deleteData())
//                return false;
//        }
        //PubSubmit tPubSubmit = new PubSubmit();
        //tPubSubmit.submitData(mInputData, cOperate);
//        if (tPubSubmit.submitData(mInputData, cOperate) == false) {
//            // @@错误处理
//            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
//            return false;
//        }

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
        tLPInsuAccInSet = (LPInsuAccInSet) cInputData.
                          getObjectByObjectName("LPInsuAccInSet", 0);

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


    private boolean dealIN() {
    	Reflections tReflections = new Reflections();
    	for (int i = 1; i <= tLPInsuAccInSet.size(); i++) {


            logger.debug(mLPEdorItemSchema.encode());
    		String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
    		logger.debug("014");

            logger.debug("014");
            LPInsuAccInSchema mLPInsuAccInSchema = new LPInsuAccInSchema();
            logger.debug("014");
            mLPInsuAccInSchema = tLPInsuAccInSet.get(i);
            logger.debug("第"+i+"个mLPInsuAccInSchema::"+mLPInsuAccInSchema.encode());
            LCPolDB tLCPolDB = new LCPolDB();
            tLCPolDB.setPolNo(mLPInsuAccInSchema.getInPolNo());
            if (!tLCPolDB.getInfo()) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "PEdorTIDetailBL";
                tError.functionName = "checkData";
                tError.errorMessage = "未得到转入保单号码!";
                this.mErrors.addOneError(tError);
                return false;
            }
            mLPInsuAccInSchema.setState("1");
            mLPInsuAccInSchema.setInInsuredNo(tLCPolDB.getInsuredNo());
            mLPInsuAccInSchema.setInAppntNo(tLCPolDB.getAppntNo());
            mLPInsuAccInSchema.setOperator(mGlobalInput.Operator);
            mLPInsuAccInSchema.setMakeDate(CurrentDate);
            mLPInsuAccInSchema.setMakeTime(CurrentTime);
            mLPInsuAccInSchema.setModifyDate(CurrentDate);
            mLPInsuAccInSchema.setModifyTime(CurrentTime);

            LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
            LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
            tLCInsureAccClassDB.setInsuAccNo(mLPInsuAccInSchema.getInInsuAccNo());
            tLCInsureAccClassDB.setContNo(mLPInsuAccInSchema.getContNo());
            tLCInsureAccClassSet = tLCInsureAccClassDB.query();
            double mainunitcount = 0;
            for(int j=1;j<=tLCInsureAccClassSet.size();j++)
            {
            	mainunitcount = mainunitcount+tLCInsureAccClassSet.get(j).getUnitCount();
            }


            for(int j=1;j<=tLCInsureAccClassSet.size();j++)
            {
            	String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
            	LPInsuAccInSchema lzLPInsuAccInSchema = new LPInsuAccInSchema();
            	tReflections.transFields(lzLPInsuAccInSchema,
            			mLPInsuAccInSchema);
            	double biliaaa = 0;
            	if(mainunitcount == 0)
            	{
            		biliaaa = mLPInsuAccInSchema.getAccInRate()/tLCInsureAccClassSet.size();
            	}
            	else
            	{
            		biliaaa = tLCInsureAccClassSet.get(j).getUnitCount()*mLPInsuAccInSchema.getAccInRate()/mainunitcount;
            	}
            	if(biliaaa==0)
            	{
            		continue;
            	}
            	lzLPInsuAccInSchema.setAccInRate(biliaaa);
            	lzLPInsuAccInSchema.setSerialNo(SerialNo);
            	lzLPInsuAccInSchema.setInPayPlanCode(tLCInsureAccClassSet.get(j).getPayPlanCode());
            	mLPInsuAccInSet.add(lzLPInsuAccInSchema);
            }



        }

    	map.put(mLPInsuAccInSet, "DELETE&INSERT");
        return true;
    }



    /**
     * 校验传入的数据的合法性
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkData() {
        LPEdorItemSet tLPEdorItemSet=new LPEdorItemSet();
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setEdorAcceptNo(mLPEdorItemSchema.getEdorAcceptNo());
        tLPEdorItemSet=tLPEdorItemDB.query();
        if (tLPEdorItemSet.size()==0) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorTIDetailBL";
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
            tError.moduleName = "PEdorTIDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保全项目已经申请确认不能修改!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //如果地址号不为空，需要校验该地址号是否为本次保全申请产生，如果不是本次保全申请产生，则不让修改
        tEdorAcceptNo = tLPEdorItemSet.get(1).getEdorAcceptNo();
        return true;
    }

    private boolean checkDataIN() {

    	double bili = 0;
    	logger.debug("比例之和:::::我算算算！"+tLPInsuAccInSet.size());
    	if(tLPInsuAccInSet.size()==0)
    	{
    		CError tError = new CError();
            tError.moduleName = "PEdorTIDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "请填写转入比例!";
            this.mErrors.addOneError(tError);
            return false;
    	}
    	for(int i=1;i<=tLPInsuAccInSet.size() ;i++)
    	{
    		logger.debug("挂了？"+tLPInsuAccInSet.get(i).getAccInRate());
    		bili = bili +tLPInsuAccInSet.get(i).getAccInRate();
    		if(tLPInsuAccInSet.get(i).getAccInRate()*100%5!=0)
    		{
    			CError tError = new CError();
                tError.moduleName = "PEdorTIDetailBL";
                tError.functionName = "checkData";
                tError.errorMessage = "比例必须为百分之五的整数倍!";
                this.mErrors.addOneError(tError);
                return false;
    		}
    		logger.debug("挂了？");
    		logger.debug("tLPInsuAccInSet.get(i)::"+tLPInsuAccInSet.get(i).encode());
    	}
    	bili = Double.parseDouble(df6.format(bili));
    	 logger.debug("比例之和:::::"+bili);
    	if(bili!=1)
    	{
    		CError tError = new CError();
            tError.moduleName = "PEdorTIDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "比例之和不为1!";
            this.mErrors.addOneError(tError);
            return false;
    	}
    	return true;

    }

    private boolean checkDataOUT() {

    	LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mLPInsuAccOutSchema.getOutPolNo());
        if (!tLCPolDB.getInfo()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorTIDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "未得到转出保单号码!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mLPInsuAccOutSchema.setOutInsuredNo(tLCPolDB.getInsuredNo());
        mLPInsuAccOutSchema.setOutAppntNo(tLCPolDB.getAppntNo());

        if (!checkData500()) {
            return false;
        }

    	return true;
    }
    /**
     * 校验投资单位价值余额是否小于500RMB
     * 申请领取时，领取后的投资单位价值余额不得低于500元人民币(按最近一次账户评估日卖出价格计算)
     * 否则必须全部转出
     * 输出：如果单位价值余额是否小于500RMB则返回false,否则返回true
     */
    private boolean checkData500() {

    	ExeSQL esee = new ExeSQL();
    	String strEdorNo = mLPInsuAccOutSchema.getEdorNo();
    	String strPolNo = mLPInsuAccOutSchema.getOutPolNo();
    	String strInsuAccNo = mLPInsuAccOutSchema.getOutInsuAccNo();
    	String SQLQSL = "select (case when sum(UnitCount) is not null then sum(UnitCount) else 0 end) from LCInsureAcc where polno = '?strPolNo?' and InsuAccNo = '?strInsuAccNo?'";

    	SQLwithBindVariables sqlbv=new SQLwithBindVariables();
    	sqlbv.sql(SQLQSL);
    	sqlbv.put("strPolNo", strPolNo);
    	sqlbv.put("strInsuAccNo", strInsuAccNo);
    	
    	double douUnitCount = Double.parseDouble(esee.getOneValue(sqlbv));
    	douUnitCount = douUnitCount - mLPInsuAccOutSchema.getAccOutUnit();
    	String sqlbb = "select UnitPriceSell from LOAccUnitPrice where "
    	+" InsuAccNo = '?strInsuAccNo?' "
    	+" and StartDate <= now() "
    	+" order by StartDate DESC";
    	String SQL = "";
    	if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    		SQL = "select UnitPriceSell from ("+sqlbb+") where rownum =1";
    	}else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
    		SQL = "select UnitPriceSell from ("+sqlbb+") g limit 0,1";
    	}
    	SQLwithBindVariables sbv=new SQLwithBindVariables();
    	sbv.sql(SQL);
    	sbv.put("strInsuAccNo", strInsuAccNo);

    	String strUnitPriceSell = esee.getOneValue(sbv);
    	double douUnitPriceSell = Double.parseDouble(strUnitPriceSell);

    	//LPInsuAccOutSet lzLPInsuAccOutSet = new LPInsuAccOutSet();
    	//LPInsuAccOutDB lzLPInsuAccOutDB = new LPInsuAccOutDB();
    	//lzLPInsuAccOutDB.setEdorNo(strEdorNo);
    	//lzLPInsuAccOutDB.setOutPolNo(strPolNo);
    	//lzLPInsuAccOutDB.setOutInsuAccNo(strInsuAccNo);
    	//lzLPInsuAccOutDB.setOutPayPlanCode(strPayPlanCode);
    	//lzLPInsuAccOutSet = lzLPInsuAccOutDB.query();
    	String QuerySql = "select (case when sum(AccOutUnit) is not null then sum(AccOutUnit) else 0 end) from LPInsuAccOut where "
    		+" EdorNo = '?strInsuAccNo?' "
    		+" and OutPolNo = '?strPolNo?' "
    		+" and OutInsuAccNo = '?strInsuAccNo?' "
    		//+" and OutPayPlanCode = '"+strPayPlanCode+"'"
    		;
    	SQLwithBindVariables sbv1=new SQLwithBindVariables();
    	sbv1.sql(QuerySql);
    	sbv1.put("strEdorNo", strEdorNo);
    	sbv1.put("strPolNo", strPolNo);
    	sbv1.put("strInsuAccNo", strInsuAccNo);
    	SSRS ss = new SSRS();
    	ss = esee.execSQL(sbv1);

    		double outUnitCount = Double.parseDouble(ss.GetText(1, 1));
    		douUnitCount = douUnitCount - outUnitCount;
    		logger.debug("保全去掉的单位数："+outUnitCount);


    	double douMain = douUnitCount*douUnitPriceSell;
    	douMain = Double.parseDouble(df.format(douMain));
    	logger.debug("预计剩余总价值："+douMain);
    	if(douMain!=0&&douMain<500)
    	{
    		CError tError = new CError();
            tError.moduleName = "PEdorTIDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "转出账户在当日的投资单位价值余额不得低于500元人民币，否则必须全部领取";
            this.mErrors.addOneError(tError);
            return false;
    	}

    	return true;
    }

    /**
     * 准备需要保存的数据
     */
    private boolean dealData() {
        if (tLPInsuAccInSet.size() > 0) {
            Reflections tReflections = new Reflections();
            String tLimit = PubFun.getNoLimit(mLPEdorItemSchema.getManageCom());
            String SerialNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
            mLPInsuAccOutSchema.setSerialNo(SerialNo);
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
            tLCInsureAccClassDB.setPayPlanCode(mLPInsuAccOutSchema.
                                               getOutPayPlanCode());
            tLCInsureAccClassSet = tLCInsureAccClassDB.query();
            if (tLCInsureAccClassSet.size() == 0) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "PEdorTIDetailBL";
                tError.functionName = "checkData";
                tError.errorMessage = "未找到该保单账户信息!";
                this.mErrors.addOneError(tError);
                return false;
            }

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
                mLPInsureAccClassSet.add(mLPInsureAccClassSchema);
            }

            tReflections.transFields(mLPInsureAccTraceSchema,
                                     tLCInsureAccClassSet.get(1));
            mLPInsureAccTraceSchema.setSerialNo(SerialNo);
            mLPInsureAccTraceSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
            mLPInsureAccTraceSchema.setEdorType(mLPEdorItemSchema.
                                                getEdorType());
            mLPInsureAccTraceSchema.setMoneyType("TI");
            mLPInsureAccTraceSchema.setPayDate(mLPEdorItemSchema.
                                               getEdorValiDate()); //保全生效日期
            mLPInsureAccTraceSchema.setUnitCount( -mLPInsuAccOutSchema.
                                                 getAccOutUnit());
            mLPInsureAccTraceSchema.setMoney( -mLPInsuAccOutSchema.getAccOutBala());
            mLPInsureAccTraceSchema.setState("0");
            mLPInsureAccTraceSchema.setAccAlterType("3"); //保全
            mLPInsureAccTraceSchema.setBusyType(mLPEdorItemSchema.
                                                getEdorType()); //账户转换
            mLPInsureAccTraceSchema.setAccAlterNo(mLPEdorItemSchema.
                                                  getEdorNo());
            mLPInsureAccTraceSchema.setFeeCode("000000");
            mLPInsureAccTraceSchema.setOperator(mGlobalInput.Operator);
            mLPInsureAccTraceSchema.setMakeDate(CurrentDate);
            mLPInsureAccTraceSchema.setMakeTime(CurrentTime);
            mLPInsureAccTraceSchema.setMakeDate(CurrentDate);
            mLPInsureAccTraceSchema.setMakeTime(CurrentTime);
            logger.debug("mLPInsureAccTraceSchema:" +
                               mLPInsureAccTraceSchema.encode());
            for (int i = 1; i <= tLPInsuAccInSet.size(); i++) {
                LPInsuAccInSchema mLPInsuAccInSchema = new LPInsuAccInSchema();
                mLPInsuAccInSchema = tLPInsuAccInSet.get(i);
                mLPInsuAccInSchema.setSerialNo(SerialNo);

                LCPolDB tLCPolDB = new LCPolDB();
                tLCPolDB.setPolNo(mLPInsuAccInSchema.getInPolNo());
                if (!tLCPolDB.getInfo()) {
                    // @@错误处理
                    CError tError = new CError();
                    tError.moduleName = "PEdorTIDetailBL";
                    tError.functionName = "checkData";
                    tError.errorMessage = "未得到转入保单号码!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
                mLPInsuAccInSchema.setState("1");
                mLPInsuAccInSchema.setInInsuredNo(tLCPolDB.getInsuredNo());
                mLPInsuAccInSchema.setInAppntNo(tLCPolDB.getAppntNo());
                mLPInsuAccInSchema.setOperator(mGlobalInput.Operator);
                mLPInsuAccInSchema.setMakeDate(CurrentDate);
                mLPInsuAccInSchema.setMakeTime(CurrentTime);
                mLPInsuAccInSchema.setModifyDate(CurrentDate);
                mLPInsuAccInSchema.setModifyTime(CurrentTime);
                mLPInsuAccInSet.add(mLPInsuAccInSchema);
            }
        }
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean insertData() {

        //map.put(mLPInsuAccInSet, "INSERT");

        mInputData.clear();
        mInputData.add(map);
        mResult.clear();
        mResult.add(map);
//        mResult.add(mLPInsuAccInSet);

        return true;
    }


    private boolean dealOUT() {

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
        lzLPInsureAccTraceSchema.setMoneyType("TI");
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
        lzLPInsuAccOutSchema.setAccOutUnit(-lzLPInsureAccTraceSchema.getUnitCount());
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


    	//处理
        map.put(mLPInsureAccSet, "DELETE&INSERT");
        map.put(mLPInsureAccClassSet, "DELETE&INSERT");
        map.put(mLPInsuAccOutSet, "INSERT");
        map.put(mLPInsureAccTraceSet, "INSERT");
    	return true;
    }
    private boolean deleteOUT() {
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

        mInputData.clear();
        mInputData.add(map);
        mResult.clear();
        mResult.add(mLPInsuAccOutSchema);
        mResult.add(mLPInsuAccInSet);

        return true;
    }

}


