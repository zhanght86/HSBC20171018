package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.LCPolBL;
import com.sinosoft.lis.bq.ReCalBL;
import com.sinosoft.lis.db.LCDutyDB;
import com.sinosoft.lis.db.LCGetDB;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.db.LMRiskDB;
import com.sinosoft.lis.db.LPEdorAppDB;
import com.sinosoft.lis.db.LPEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorItemDB;
import com.sinosoft.lis.db.LPGrpEdorMainDB;
import com.sinosoft.lis.pubfun.BqCalBase;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LCGetSchema;
import com.sinosoft.lis.schema.LCPolSchema;
import com.sinosoft.lis.schema.LPDutySchema;
import com.sinosoft.lis.schema.LPEdorAppSchema;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.lis.schema.LPGetSchema;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.lis.schema.LPGrpEdorMainSchema;
import com.sinosoft.lis.schema.LPPolSchema;
import com.sinosoft.lis.vbl.LCDutyBLSet;
import com.sinosoft.lis.vbl.LCGetBLSet;
import com.sinosoft.lis.vschema.LCGetSet;
import com.sinosoft.lis.vschema.LCPremSet;
import com.sinosoft.lis.vschema.LPDutySet;
import com.sinosoft.lis.vschema.LPEdorItemSet;
import com.sinosoft.lis.vschema.LPGetSet;
import com.sinosoft.lis.vschema.LPGrpEdorItemSet;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 领取年龄变更（保全项目代码：GB）项目明细</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zjt
 * @version 1.0
 */
public class PGrpEdorGBDetailBL implements BusinessService
{
private static Logger logger = Logger.getLogger(PGrpEdorGBDetailBL.class);

	String mEdorAcceptNo = "";
    String mEdorNo      = "";
    String mEdorType    = "";
    String mContNo      = "";
    String mGrpContNo   = "";
    String mInsuredNo   = "";
    String mRiskCode    = "";
    String mPolNo       = "";
    String mEdorEndAge  = "";
    String mOperator    = "";
    String mManageCom   = "";

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往界面传输数据的容器 */
    private MMap mMap     = new MMap();
    private VData mResult = new VData();
    private LPDutySet mLPDutySet = new LPDutySet();
    private LPGetSet mLPGetSet   = new LPGetSet();
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

    /** 数据操作字符串 */
    private String mOperate;

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    //准备校验规则所需要的计算要素
    private BqCalBase mBqCalBase = new BqCalBase();
    Reflections mReflections = new Reflections();

    /**控制信息传输类*/
    private TransferData mTransferData = new TransferData();

    /** 重算后的领取标准 */
    private String mStandMoney;

    /**
     * Constructor
     **/
    public PGrpEdorGBDetailBL()
    {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public void setOperate(String cOperate)
    {
        this.mOperate = cOperate;
    }

    /**
     *
     **/
    public String getOperate()
    {
        return this.mOperate;
    }

    /**
     *获得重算后的领取标准金额，可在保全操作完毕后由BLF层加入mResult
     */
    public String getStandMoney()
    {
        return this.mStandMoney;
    }

    /**
     *
     **/
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
            "=====This is PGrpEdorGBDetailBL->submitData=====\n");

        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.setOperate(cOperate);
        if (!cOperate.equals("UPDATE||MAIN"))
        {
            return false;
        }

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }

        //数据校验操作（checkdata)
        if (!checkData())
        {
            return false;
        }

        // 数据操作业务处理(新增insertData();修改updateData();删除deletedata())
        if (!dealData())
        {
            return false;
        }
        if (!prepareOutPutData())
        {
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, ""))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "PGrpEdorGBDetailBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);

            return false;
        }
        
        
        //支持EJB改造
        LPPolSchema backLPPolSchema = null;
        backLPPolSchema = (LPPolSchema) mResult.getObjectByObjectName("LPPolSchema",0);
        mResult.clear();
        mResult.add(backLPPolSchema);

        return true;
    }

    /**
     *
     **/
    public VData getResult()
    {
        return this.mResult;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        logger.debug(
            "=====This is PGrpEdorGBDetailBL->getInputData=====\n");
        try{
            mInputData = (VData) cInputData.clone();
            mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
                    "GlobalInput", 0);
            mTransferData = (TransferData) mInputData.getObjectByObjectName(
                    "TransferData", 0);
            mOperator  = (String) mGlobalInput.Operator;
            mManageCom = (String) mGlobalInput.ManageCom;
            mEdorAcceptNo = (String)mTransferData.getValueByName("EdorAcceptNo");
            mEdorNo    = (String) mTransferData.getValueByName("EdorNo");
            mEdorType  = (String) mTransferData.getValueByName("EdorType");
            mContNo    = (String) mTransferData.getValueByName("ContNo");
            mGrpContNo = (String) mTransferData.getValueByName("GrpContNo");
            mInsuredNo = (String) mTransferData.getValueByName("InsuredNo");
            mRiskCode  = (String) mTransferData.getValueByName("RiskCode");
            mPolNo     = (String) mTransferData.getValueByName("PolNo");
            mEdorEndAge = (String) mTransferData.getValueByName("EdorEndYear");
        } catch (Exception ex) {
            CError.buildErr(this, ex.toString());
            return false;
        }

        return true;
    }

    /**
     *
     **/
    private boolean checkData()
    {
        logger.debug(
            "=====This is PGrpEdorGBDetailBL->checkData=====\n");
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        if (mEdorNo.equals("") || mEdorType.equals("") || mContNo.equals("")||
            mInsuredNo.equals("") || mRiskCode.equals("") ||
            mEdorAcceptNo.equals("")||mPolNo.equals("")|| mEdorEndAge.equals(""))
        {
            CError.buildErr(this, "传输数据失败！");
            return false;
        }
        tLPEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
        tLPEdorItemDB.setEdorNo(mEdorNo);
        tLPEdorItemDB.setEdorType(mEdorType);
        tLPEdorItemDB.setContNo(mContNo);
        tLPEdorItemDB.setInsuredNo(mInsuredNo);
        //tLPEdorItemDB.setPolNo("000000");
        LPEdorItemSet tLPEdorItemSet = null;
        try{
            tLPEdorItemSet = tLPEdorItemDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLPEdorItemDB.mErrors.needDealError()){
            mErrors.copyAllErrors(tLPEdorItemDB.mErrors);
            return false;
        }
        if (tLPEdorItemSet.size() <= 0) {

            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PGrpEdorGBDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "无保全申请数据!";
            this.mErrors.addOneError(tError);

            return false;
        } else {
            mLPEdorItemSchema.setSchema(tLPEdorItemSet.get(1).getSchema());
            if (mLPEdorItemSchema.getPolNo().equals("000000")){
                String tSql = "delete from LPEdorItem where EdorNo = '" +
                              mEdorNo + "' and EdorType = '" +
                              mEdorType + "' and ContNo = '" +
                              mContNo + "' and InsuredNo = '" +
                              mInsuredNo + "' and PolNo  = '000000'";
                mMap.put(tSql, "DELETE");
            }
            mLPEdorItemSchema.setPolNo(mPolNo);
        }
        String tEdorState = mLPEdorItemSchema.getEdorState();
        if (tEdorState.equals("2")) {
            logger.debug("2");

            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PGrpEdorGBDetailBL";
            tError.functionName = "checkData";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            this.mErrors.addOneError(tError);

            return false;
        }

        //查询变更前的投保年龄
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mPolNo);
        LCPolSchema tLCPolSchema = null;
        try {
            tLCPolSchema = tLCPolDB.query().get(1);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCPolDB.mErrors.needDealError()){
            mErrors.copyAllErrors(tLCPolDB.mErrors);
            return false;
        }
        if (tLCPolSchema == null ||
            String.valueOf(tLCPolSchema.getGetYear()).equals("")){
            CError.buildErr(this, "查询个人险种保单表失败！");
            return false;
        }

        //保单生效日
        String tCValiDate = tLCPolSchema.getCValiDate();

        //投保年龄
        int tInsuredAppAge = tLCPolSchema.getInsuredAppAge();

        //===20060804===testing start=====================
        logger.debug("投保年龄： " + tInsuredAppAge);
        //===20060804===testing end=======================


        //校验非法输入
        try{
            float fEdorEndAge = Float.parseFloat(mEdorEndAge);
        } catch (Exception ex){
            CError.buildErr(this, "非法输入，请重新输入！");
            ex.printStackTrace();
            return false;
        }
        float aEdorEndAge = Float.parseFloat(mEdorEndAge);
        int tEdorEndAge = (int) aEdorEndAge;
        if (tEdorEndAge - aEdorEndAge != 0) {
            CError.buildErr(this, "年龄必须输入整数，请重新输入！");
            return false;
        }

        //校验该保单是否已经开始领取
        if (!IfStartGetMoney()){
            return false;
        }

        //校验输入年龄是否小于被保险人今年的年龄
        if (!IfSmallerAgeThisYear(tInsuredAppAge, tCValiDate, tEdorEndAge)) {
            return false;
        }
        //校验输入年龄是否小于被保险人的投保年龄
        if (tEdorEndAge < tInsuredAppAge) {
            CError.buildErr(this, "变更年龄不能小于投保年龄，请重新输入！");
            return false;
        }

        return true;
    }

    /**
     * @tCValiDate: 保单生效日
     * @tInsuredAppAge: 被保险人的投保年龄
     * @tEdorEndAge: 变更后的领取年龄
     **/
    private boolean IfSmallerAgeThisYear(int tInsuredAppAge,
                                           String tCValidDate,
                                           int tEdorEndAge){

        logger.debug(
            "=====This is PGrpEdorGBDetailBl->IfStartGetMoney=====\n");

        /***********************************************************
         * 算法：保单生效日和系统日期的年期差距与被保险人的投保年龄和输入年龄
         *      的年龄差距比较， 如果前者大则false, 如果前者小，则true
         ***********************************************************/
        String tCurrentDate = PubFun.getCurrentDate();
        int tIntv = PubFun.calInterval(tCValidDate, tCurrentDate, "Y");

        //===20060804===testing start=====================
        logger.debug("被保险人今年的年龄：" + (tInsuredAppAge + tIntv));
        //===200060804===testing end======================

        if (tEdorEndAge - tInsuredAppAge < tIntv){
            CError.buildErr(this, "变更年龄不能小于现在的年龄，请重新输入！");
            return false;
        }

        return true;
    }

    /**
     *
     **/
    private boolean IfStartGetMoney(){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->startGetMoney=====\n");
        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(mPolNo);
        LCGetSet tLCGetSet = null;
        try {
            tLCGetSet = tLCGetDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLCGetDB.mErrors.needDealError()){
            mErrors.copyAllErrors(tLCGetDB.mErrors);
            return false;
        }
        if (tLCGetSet == null || tLCGetSet.size() <= 0){
            CError.buildErr(this, "传入保单号错误！");
            return false;
        }
        LCGetSchema tLCGetSchema = null;
        for (int i = 1; i <= tLCGetSet.size(); i++){
            tLCGetSchema = new LCGetSchema();
            tLCGetSchema.setSchema(tLCGetSet.get(i));
            if (tLCGetSchema.getSumMoney() > 0.0){
                CError.buildErr(this, "该险种保单已经开始领取，不能作领取年龄变更！");
                return false;
            }
        }

        return true;
    }

    /**
     * 准备需要保存的数据
     */
    private boolean dealData()
    {
        logger.debug(
            "=====This is PGrpEdorGBDetail->dealData=====\n");

        //险种表(保费重算)
        if(!dealPol()){
            return false;
        }

        //更新LPGrpEdorItem
        if (!dealGrpEdorItem()){
            return false;
        }

        //修改“个险保全项目表”相应信息
        if (!dealEdorItem()) {
            return false;
        }

        //校验LPGrpEdorMain下的所有LPGrpEdorItem
        String tSql = "select * from LPGrpEdorItem where EdorNo = '" +
                      mEdorNo + "' and EdorType <> 'GB' and EdorState = '3'";
        LPGrpEdorItemSet tLPGrpEdorItemSet = null;
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        try {
            tLPGrpEdorItemSet = tLPGrpEdorItemDB.executeQuery(tSql);
        } catch (Exception ex) {
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return false;
        }
        if (tLPGrpEdorItemDB.mErrors.needDealError()) {
            mErrors.copyAllErrors(tLPGrpEdorItemDB.mErrors);
            return false;
        }
        if (tLPGrpEdorItemSet != null && tLPGrpEdorItemSet.size() > 0) {
            return true;
        }

        //更新LPGrpEdorMain
        if (!dealGrpEdorMain()) {
            return false;
        }

        //更新LPGrpEdorApp
        if (!dealEdorApp()){
            return false;
        }

        return true;
    }

    /**
     *
     **/
    private boolean dealPol(){

        logger.debug("=====This is PGrpEdorGBDetailBL->dealPol=====\n");
        String tCurrentDate = PubFun.getCurrentDate();
        String tCurrentTime = PubFun.getCurrentTime();
        LCPolSchema tLCPolSchema = null;
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setPolNo(mPolNo);
        try{
            tLCPolSchema = tLCPolDB.query().get(1);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            return false;
        }
        if (tLCPolDB.mErrors.needDealError()){
            mErrors.copyAllErrors(tLCPolDB.mErrors);
            return false;
        }
        if (tLCPolSchema == null){
            CError.buildErr(this, "查询个人险种表失败！");
            return false;
        }

        //转换Schema
        Reflections tReflections = new Reflections();
        LPPolSchema aLPPolSchema = new LPPolSchema();
        tReflections.transFields(aLPPolSchema, tLCPolSchema);
        aLPPolSchema.setEdorNo(mLPEdorItemSchema.getEdorNo());
        aLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        if (aLPPolSchema.getInsuYear() == aLPPolSchema.getGetYear()) {
            aLPPolSchema.setInsuYear(mEdorEndAge);
        }
        aLPPolSchema.setGetYear(mEdorEndAge);

        //添加计算要素
        mBqCalBase.setPolNo(mPolNo);
        mBqCalBase.setRiskCode(mRiskCode);

        //领取年龄变更后是否需要重算领取标准的标识
        boolean tReCal = false;

        //由于帐户型险种变更年龄，但是保费不变，因此不需要重算；
        //但是，非帐户型险种保费一般随年龄而改变，如定额养老等
        /*String tInsuAccFlag = getInsuAccFlag(aLPPolSchema.getRiskCode());
        if (tInsuAccFlag.equals("")){
            CError.buildErr(this, "处理险种帐户信息失败！");
            return false;
        }*/
        /*if (tInsuAccFlag.equals("Y")){
            if (!dealInsuAccData()){
                return false;
            }
        } else {*/

            //把变动部分相关的表数据准备好，然后调用重算程序
            tReCal = this.ReCalculate(aLPPolSchema, mLPEdorItemSchema);
            if (!tReCal) {
                mErrors.addOneError(new CError("计算失败，请检查变更年龄是否合法！"));
                return false;
            }
        /*}*/
        //aLPPolSchema.setOperator(mOperator);
        //aLPPolSchema.setManageCom(mManageCom);
        aLPPolSchema.setModifyDate(tCurrentDate);
        aLPPolSchema.setModifyTime(tCurrentTime);
        mMap.put(aLPPolSchema, "DELETE&INSERT");
        mResult.clear();
        mResult.add(aLPPolSchema);
        if (mLPGetSet != null && mLPGetSet.size() > 0){
            mMap.put(mLPGetSet, "DELETE&INSERT");
        }
        if (mLPDutySet != null && mLPDutySet.size() > 0){
            mMap.put(mLPDutySet, "DELETE&INSERT");
        }

        return true;
    }

    /**
     *
     **/
    private boolean dealInsuAccData(){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->dealInsuAccPrem=====\n");

        //准备需要的责任表数据
        LCDutyBLSet tLCDutyBLSet = getRecalDuty();
        if (tLCDutyBLSet == null || tLCDutyBLSet.size() <= 0){
            return false;
        }
        if (!prepareDutyData(tLCDutyBLSet)){
            return false;
        }

        //准备需要的领取项表数据
        LCGetBLSet tLCGetBLSet = getRecalGet();
        if (tLCGetBLSet == null || tLCGetBLSet.size() <= 0){
            return false;
        }
        if (!prepareGetData(tLCGetBLSet)){
            return false;
        }

        return true;
    }

    /**
     *
     **/
    private boolean preparePolData(LPPolSchema aLPPolSchema){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->preparePolData");

        return true;
    }

    /**
     *
     **/
    private boolean prepareDutyData(LCDutyBLSet tLCDutyBLSet){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->prepareDutyData=====\n");
        LPDutySchema tLPDutySchema = null;
        String tCurrentDate = PubFun.getCurrentDate();
        String tCurrentTime = PubFun.getCurrentTime();
        Reflections tRef = new Reflections();
        for (int i = 1; i <= tLCDutyBLSet.size(); i++)
        {
            if (tLCDutyBLSet.get(i).getInsuYear() ==
                tLCDutyBLSet.get(i).getGetYear())
            {
                tLCDutyBLSet.get(i).setInsuYear(mEdorEndAge);
            }
            tLCDutyBLSet.get(i).setGetYear(mEdorEndAge);
            tLPDutySchema = new LPDutySchema();
            tRef.transFields(tLPDutySchema, tLCDutyBLSet.get(i).getSchema());
            tLPDutySchema.setEdorNo(mEdorNo);
            tLPDutySchema.setEdorType(mEdorType);
            tLPDutySchema.setModifyDate(tCurrentDate);
            tLPDutySchema.setModifyTime(tCurrentTime);
            mLPDutySet.add(tLPDutySchema);
        }

        return true;
    }

    /**
     *
     **/
    private boolean prepareGetData(LCGetBLSet tLCGetBLSet){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->prepareGetData=====\n");
        String tCurrentDate = PubFun.getCurrentDate();
        String tCurrentTime = PubFun.getCurrentTime();
        Reflections tRef = new Reflections();
        LPGetSchema tLPGetSchema = null;
        for (int i = 1; i <= tLCGetBLSet.size(); i++){
            tLPGetSchema = new LPGetSchema();
            tRef.transFields(tLPGetSchema, tLCGetBLSet.get(i));
            tLPGetSchema.setEdorNo(mEdorNo);
            tLPGetSchema.setEdorType(mEdorType);
            //tLPGetSchema.setOperator(mOperator);
            //tLPGetSchema.setManageCom(mManageCom);
            tLPGetSchema.setModifyDate(tCurrentDate);
            tLPGetSchema.setModifyTime(tCurrentTime);
            mLPGetSet.add(tLPGetSchema);
        }

        return true;
    }

    /**
     *
     **/
    private boolean dealGrpEdorItem(){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->dealGrpEdorItem");
        String tCurrentDate = PubFun.getCurrentDate();
        String tCurrentTime = PubFun.getCurrentTime();
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setEdorAcceptNo(mEdorAcceptNo);
        tLPGrpEdorItemDB.setEdorNo(mEdorNo);
        tLPGrpEdorItemDB.setEdorType(mEdorType);
        tLPGrpEdorItemDB.setGrpContNo(mGrpContNo);
        LPGrpEdorItemSchema tLPGrpEdorItemSchema =
            tLPGrpEdorItemDB.query().get(1);
        if (tLPGrpEdorItemDB.mErrors.needDealError()){
            CError.buildErr(this, "查询LPGrpEdorItem表发生意外!");
            return  false;
        }
        if (tLPGrpEdorItemSchema == null){
            CError.buildErr(this, "查询LPGrpEdorItem为空!");
            return false;
        }
        tLPGrpEdorItemSchema.setEdorState("1");
        tLPGrpEdorItemSchema.setOperator(mOperator);
        //tLPGrpEdorItemSchema.setManageCom(mManageCom);
        tLPGrpEdorItemSchema.setModifyDate(tCurrentDate);
        tLPGrpEdorItemSchema.setModifyTime(tCurrentTime);
        mMap.put(tLPGrpEdorItemSchema, "UPDATE");

        return true;
    }

    /**
     *
     **/
    private boolean dealGrpEdorMain(){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->dealGrpEdorMain=====\n");
        String tCurrentDate = PubFun.getCurrentDate();
        String tCurrentTime = PubFun.getCurrentTime();
        LPGrpEdorMainDB tLPGrpEdorMainDB = new LPGrpEdorMainDB();
        tLPGrpEdorMainDB.setEdorAcceptNo(mEdorAcceptNo);
        tLPGrpEdorMainDB.setEdorNo(mEdorNo);
        LPGrpEdorMainSchema tLPGrpEdorMainSchema = new LPGrpEdorMainSchema();
        try{
            tLPGrpEdorMainSchema = tLPGrpEdorMainDB.query().get(1);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            mErrors.copyAllErrors(tLPGrpEdorMainDB.mErrors);
            return false;
        }
        if (tLPGrpEdorMainSchema.getEdorAcceptNo().equals("")) {

            CError.buildErr(this, "查询团体批改表失败! 保全受理号: "
                    + mEdorNo);
            return false;
        }
        tLPGrpEdorMainSchema.setEdorState("1");
        //tLPGrpEdorMainSchema.setOperator(mOperator);
        //tLPGrpEdorMainSchema.setManageCom(mManageCom);
        tLPGrpEdorMainSchema.setModifyDate(tCurrentDate);
        tLPGrpEdorMainSchema.setModifyTime(tCurrentTime);
        mMap.put(tLPGrpEdorMainSchema, "UPDATE");

        return true;
    }

    /**
     *
     **/
    private boolean dealEdorApp(){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->dealEdorApp=====\n");
        String tCurrentDate = PubFun.getCurrentDate();
        String tCurrentTime = PubFun.getCurrentTime();
        LPEdorAppDB tLPEdorAppDB = new LPEdorAppDB();
        tLPEdorAppDB.setEdorAcceptNo(mEdorAcceptNo);
        LPEdorAppSchema tLPEdorAppSchema = new LPEdorAppSchema();
        try{
            tLPEdorAppSchema = tLPEdorAppDB.query().get(1);
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            return false;
        }
        if (tLPEdorAppSchema.getEdorAcceptNo().equals("")){

            CError.buildErr(this, "查询保全申请主表失败! 保全受理号: "
                    + mEdorNo);
            return false;
        }
        tLPEdorAppSchema.setEdorState("1");
        tLPEdorAppSchema.setOtherNoType("4");
        //tLPEdorAppSchema.setOtherNo(mGrpContNo);
        tLPEdorAppSchema.setOperator(mOperator);
        tLPEdorAppSchema.setManageCom(mManageCom);
        tLPEdorAppSchema.setModifyDate(tCurrentDate);
        tLPEdorAppSchema.setModifyTime(tCurrentTime);
        mMap.put(tLPEdorAppSchema, "UPDATE");

        return true;
    }

    /**
     *
     **/
    private boolean dealEdorItem(){

        logger.debug(
            "=====This is PGrpEdorGBDetail->dealEdorItem=====\n");
        String tCurrentDate = PubFun.getCurrentDate();
        String tCurrentTime = PubFun.getCurrentTime();

        //修改“个险保全项目表”相应信息
        mLPEdorItemSchema.setEdorState("1");
        mLPEdorItemSchema.setPolNo(mPolNo);
        mLPEdorItemSchema.setOperator(mOperator);
        //mLPEdorItemSchema.setManageCom(mManageCom);
        mLPEdorItemSchema.setMakeDate(tCurrentDate);
        mLPEdorItemSchema.setMakeTime(tCurrentTime);
        mLPEdorItemSchema.setModifyDate(tCurrentDate);
        mLPEdorItemSchema.setModifyTime(tCurrentTime);
        mMap.put(mLPEdorItemSchema, "DELETE&INSERT");

        return true;
    }

    /**
     * 根据传入的险种信息和个人保全项目信息重算，得到重算后的领取、责任、险种等信息
     * @param aLPPolSchema LPPolSchema
     * @param aLPEdorItemSchema LPEdorItemSchema
     * @return boolean
     */
    private boolean ReCalculate(LPPolSchema aLPPolSchema,
                                LPEdorItemSchema aLPEdorItemSchema)
    {
        logger.debug(
            "=====This is PGrpEdorGBDetailBL->ReCalculate=====\n");
        ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, aLPEdorItemSchema);

        //准备重算需要的保单表数据
        LCPolBL tLCPolBL = tReCalBL.getRecalPol(aLPPolSchema);

        //帐户型险种是保费计算保费、保额；非帐户型险种是保费、保额互相计算。
        //重算时帐户型险种传保费，但是保费肯定不会更改；定额养老险种传保费
//        if (tInsuAccFlag.equals("")){
//            return false;
//        }
//        if (tInsuAccFlag.equals("Y")){
//            tLCPolBL.setAmnt(0.00);
//        } else {
//            tLCPolBL.setAmnt(0.00);
//        }
        tLCPolBL.setAmnt(0.00);
        tLCPolBL.setPrem(0.0);
        tLCPolBL.setStandPrem(0.0);
        tLCPolBL.setGetYear(mEdorEndAge);
        tLCPolBL.setGetYearFlag("A");
        //tReCalBL.preLCPolSchema = tLCPolBL.getSchema();

        //准备重算需要的保费项表数据
        LCPremSet tLCPremSet = getRecalPrem();
        if (tLCPremSet == null){
            return false;
        }
        tReCalBL.preLCPremSet.add(tLCPremSet);

        //准备重算需要的责任表数据
        LCDutyBLSet tLCDutyBLSet = getRecalDuty();
        for (int i = 1; i <= tLCDutyBLSet.size(); i++)
        {
            if (tLCDutyBLSet.get(i).getInsuYear() ==
                tLCDutyBLSet.get(i).getGetYear())
            {
                tLCDutyBLSet.get(i).setInsuYear(mEdorEndAge);
            }
            tLCDutyBLSet.get(i).setGetYear(mEdorEndAge);
            tLCDutyBLSet.get(i).setGetYearFlag("A");
            tLCDutyBLSet.get(i).setAmnt(0.00);
        }
        tReCalBL.preLCDutySet.set(tLCDutyBLSet);

        //准备重算需要的领取项表数据
        LCGetBLSet tLCGetBLSet = getRecalGet();
        if (tLCGetBLSet == null){
            return false;
        }
        tReCalBL.preLCGetSet.add(tLCGetBLSet);
        if (!tReCalBL.recalWithData(tLCPolBL, tLCDutyBLSet, tLCPremSet,
                                    tLCGetBLSet, aLPEdorItemSchema))
        {
            this.mErrors.copyAllErrors(tReCalBL.mErrors);
            CError tCError = new CError();
            tCError.buildErr(this, "重算失败，请检查变更年龄是否合法！");
            mErrors.addOneError(tCError);

            return false;
        }

        //将计算结果返回
        aLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1));
        mLPDutySet = new LPDutySet();
        mLPGetSet = new LPGetSet();
        mLPDutySet.set(tReCalBL.aftLPDutySet);
        mLPGetSet.set(tReCalBL.aftLPGetSet);

        return true;
    }

    /**
     *
     **/
    private String getInsuAccFlag(String tRiskCode){

        logger.debug(
            "=====This is PGrpEdorDBDetailBL->getInsuAccFlag=====\n");
        LMRiskDB tLMRiskDB = new LMRiskDB();
        tLMRiskDB.setRiskCode(tRiskCode);

        //帐户标志
        String tInsuAccFlag;
        try {
            tInsuAccFlag = tLMRiskDB.query().get(1).getInsuAccFlag();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            return "";
        }
        if (tInsuAccFlag.equals("")){
            CError.buildErr(this, "查询险种定义表失败， 险种编码：" + tRiskCode);
        }

        return tInsuAccFlag;
    }

    /**
     *
     **/
    private LCPremSet getRecalPrem(){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->getRecalPrem=====\n");
        LCPremDB tLCPremDB = new LCPremDB();
        tLCPremDB.setPolNo(mPolNo);
        LCPremSet tLCPremSet = null;
        try{
            tLCPremSet = tLCPremDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            return null;
        }
        if (tLCPremSet.size() <= 0){
            CError.buildErr(this, "查询保费项表失败！");
            return null;
        }

        return  tLCPremSet;
    }

    /**
     *
     **/
    private LCDutyBLSet getRecalDuty(){

        logger.debug("=====This is PGrpEdorGBDetailBL->getRecalDuty=====\n");

        //责任表
        LCDutyDB tLCDutyDB = new LCDutyDB();
        tLCDutyDB.setPolNo(mPolNo);
        LCDutyBLSet tLCDutyBLSet = new LCDutyBLSet();
        try{
            tLCDutyBLSet.set(tLCDutyDB.query());
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            return null;
        }
        if (tLCDutyDB.mErrors.needDealError()){
            CError.buildErr(this, "查询保单险种责任表时发生意外!");
            return null;
        }
        int n = tLCDutyBLSet.size();
        if (n <= 0){
            CError.buildErr(this, "没有查到险种责任表相关记录, 险种保单号： " + mPolNo);
            return null;
        }

        return tLCDutyBLSet;
    }

    /**
     *
     **/
    private LCGetBLSet getRecalGet(){

        logger.debug("=====This PGrpEdorGBDetailBL->getRecalGet=====\n");
        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(mPolNo);
        LCGetBLSet tLCGetBLSet = new LCGetBLSet();
        LCGetSet tLCGetSet = null;
        try {
            tLCGetSet = tLCGetDB.query();
        } catch (Exception ex){
            CError.buildErr(this, ex.toString());
            ex.printStackTrace();
            return null;
        }
        if (tLCGetDB.mErrors.needDealError()){
            CError.buildErr(this, "查询领取C表发生意外!");
            return null;
        }
        tLCGetBLSet.set(tLCGetSet);
        int n = tLCGetBLSet.size();
        if (n <= 0){
            CError.buildErr(this, "没有查到领取项C表记录, 险种保单号：" + mPolNo);
            return null;
        }

        return tLCGetBLSet;
    }

    /**
     *   判断是否需要领取时重算领取标准：
     *
     * @param aLPGetSchema LPGetSchema
     * @return boolean
     * true  领取时需要重算，则变更领取年龄后不需要重算领取标准；
     * false 领取时不需要重算，则变更领取年龄后需要重算领取标准；
     */
    private boolean needCalculate(LPGetSchema aLPGetSchema)
    {
        boolean flag = false;
        //BqCalBase tBqCalBase = new BqCalBase();
        String strSQL = "Select a.NeedReCompute from LMDutyGetAlive a"
                        + " where a.GetDutyCode ='" +
                        aLPGetSchema.getGetDutyCode() + "'"
                        + " and a.GetDutyKind ='" + aLPGetSchema.getGetDutyKind() +
                        "'";
        try
        {
            ExeSQL exeSQL = new ExeSQL();
            if (exeSQL.getOneValue(strSQL).equals("1"))
            { //领取时需要计算
                flag = true;
            }
            else if (exeSQL.getOneValue(strSQL).equals("0"))
            { //领取时不需要计算
                flag = false;
            }
        }
        catch (Exception e)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PGrpEdorGBDetailBL";
            tError.functionName = "needCalculate";
            tError.errorMessage = "判断领取时是否需要计算领取标准时产生错误!";
            this.mErrors.addOneError(tError);
        }
        return flag;
    }

    /**
     *
     **/
    private String queryBirthday(LPEdorItemSchema aLPEdorItemSchema) {

         String strSQL = "Select a.Birthday from LcInsured a "
                         + " where a.InsuredNo = '" +
                         aLPEdorItemSchema.getInsuredNo() + "'"
                         + " and a.ContNo='" +
                         aLPEdorItemSchema.getContNo().toString() + "'";
         String tBirthday = "";
         logger.debug("queryBirthday strSQL:" + strSQL);
         try
         {
             ExeSQL exeSQL = new ExeSQL();
             tBirthday = exeSQL.getOneValue(strSQL);
         }
         catch (Exception ex)
         {
             // @@错误处理
             CError tError = new CError();
             tError.moduleName = "PGrpEdorGBDetailBL";
             tError.functionName = "queryBirthday";
             tError.errorMessage = "未查询到被保人出生日期!";
             this.mErrors.addOneError(tError);
         }
         return tBirthday;
    }

    /**
     *
     **/
    private boolean prepareOutPutData(){

        logger.debug(
            "=====This is PGrpEdorGBDetailBL->prepareOutPutData======\n");
        mResult.add(mMap);
        mResult.add(mBqCalBase);

        return true;
    }

    /**
     *
     **/
    public static void main(String[] args)
    {
        //PGrpEdorGBDetailBL PGrpEdorGBDetailBL = new PGrpEdorGBDetailBL();

//        GlobalInput tG = new GlobalInput();
//        tG.Operator = "asdf";
//        tG.ManageCom = "86110000";
//
//        LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
//        tLPEdorItemSchema.setEdorAcceptNo("86000000001213");
//        tLPEdorItemSchema.setEdorNo("410000000001044");
//        tLPEdorItemSchema.setEdorType("GM");
//        tLPEdorItemSchema.setContNo("230110000000534");
//        tLPEdorItemSchema.setInsuredNo("0000498120");
//        tLPEdorItemSchema.setPolNo("210110000001066");
//
//        LPGetSchema tLPGetSchema = new LPGetSchema();
//
//        VData tVData = new VData();
//        tVData.add(tG);
//        tVData.add(tLPEdorItemSchema);
//        tVData.add(tLPGetSchema);
//
//        if (!PGrpEdorGBDetailBL.submitData(tVData, "UPDATE|MAIN")) {
//            logger.debug(PGrpEdorGBDetailBL.mErrors.getError(0).errorMessage);
//       }

    }

    /**
     *
     **/
    private void jbInit() throws Exception {
     ;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}

}
