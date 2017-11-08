/*
 * <p>ClassName: ContInsuredBL </p>
 * <p>Description: ContInsuredBL类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: testcompany </p>
 * @Database:
 * @CreateDate：2004-11-18 10:09:44
 */
package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.bq.ReCalBL;

public class PEdorIRDetailBL {
private static Logger logger = Logger.getLogger(PEdorIRDetailBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    private SSRS tSSRS = new SSRS(); //备用


    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();


    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private Reflections ref = new Reflections();
    private TransferData mTransferData = new TransferData();
    private MMap map = new MMap();


    //统一更新日期，时间
    private String theCurrentDate = PubFun.getCurrentDate();
    private String theCurrentTime = PubFun.getCurrentTime();


    /** 数据操作字符串 */
    private String mOperate;
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();
    private LPEdorItemSet mLPEdorItemSet = new LPEdorItemSet();
    private LPEdorMainSet mLPEdorMainSet = new LPEdorMainSet();
    private LPInsuredSchema mLPInsuredSchema = new LPInsuredSchema();
    private LPInsuredDB mOLDLCInsuredDB = new LPInsuredDB();
    private LPPersonSchema mLPPersonSchema = new LPPersonSchema(); //从界面传入的客户数据


//    private ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = new
//            ES_DOC_RELATIONSchema(); //添加到关联表被保险人的数据
    private LPContSchema mLPContSchema = new LPContSchema();
    private LPAddressSchema mLPAddressSchema = new LPAddressSchema();
    private LPAccountSchema mLPAccountSchema = new LPAccountSchema();
    private LPCustomerImpartSet mLPCustomerImpartSet;
    private LPCustomerImpartParamsSet mLPCustomerImpartParamsSet;
    private LPCustomerImpartDetailSet mLPCustomerImpartDetailSet;
    private LPPolSet mLPPolSet = new LPPolSet();
    private LCPolSet mLCPolSet = new LCPolSet();
    private LPContSet mLPContSet = new LPContSet();
    private LPPersonSet mLPPersonSet = new LPPersonSet();
    private LPInsuredSet mLPInsuredSet = new LPInsuredSet();
    private LPAddressSet mLPAddressSet = new LPAddressSet();
    private String CustomerNo;
    private String CustomerNoBak;
    private String CustomerNoNew;
    private String ContNo;
    private String FamilyType;
    private String ContType;
    private String tPolTypeFlag;
    private String tInsuredPeoples;
    private String tInsuredAppAge;
    private String tSequenceNo; //客户顺序号
    private double mChgPrem = 0.0;
    private double mChgAmnt = 0.0;


//private LCInsuredSet mLCInsuredSet=new LCInsuredSet();
    public PEdorIRDetailBL() {
    }


    /**
     * 传输数据的公共方法
     * @param cInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData)) {
            return false;
        }
        if (mOperate.equals("REPLACE||CONTINSURED")) {
            if (!this.checkData()) {
                return false;
            }
            logger.debug("---END PEdorIRDetailBL checkData---sc");
            //进行业务处理
            if (!dealData()) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "PEdorIRDetailBL";
                tError.functionName = "submitData";
                tError.errorMessage = "数据处理失败PEdorIRDetailBL-->dealData!";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        //准备往后台的数据
        logger.debug("++++++++++++++++++++++" +
                           mLPInsuredSchema.getInsuredNo());
        if (!prepareOutputData()) {
            return false;
        }

        //　数据提交、保存

        PubSubmit tPubSubmit = new PubSubmit();
        logger.debug("Start tPRnewManualDunBLS Submit...");

        if (!tPubSubmit.submitData(mInputData, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);

            CError tError = new CError();
            tError.moduleName = "ContBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";

            this.mErrors.addOneError(tError);
            return false;
        }

        mInputData = null;
        return true;
    }


    //被保人替换保额，保费重算
    private boolean ReCalculate(LPPolSchema aLPPolSchema,
                                LPEdorItemSchema aLPEdorItemSchema) {
        // 校验被保人投保年龄
        int tInsuredAge = PubFun.calInterval(aLPPolSchema.getInsuredBirthday(),
                                             aLPPolSchema.getCValiDate(), "Y");
        LMRiskAppDB tLMRiskAppDB = new LMRiskAppDB();
        tLMRiskAppDB.setRiskCode(aLPPolSchema.getRiskCode());
        if (!tLMRiskAppDB.getInfo()) {
            CError.buildErr(this, "查询险种承保定义表失败sc！");
            return false;
        }

        if ((tInsuredAge < tLMRiskAppDB.getMinInsuredAge())
            || ((tInsuredAge > tLMRiskAppDB.getMaxInsuredAge())
                && (tLMRiskAppDB.getMaxInsuredAge() > 0))) {
            /*
             在此处添加年龄不符时的处理，目前只返回错误           //add by zhangrong
             */
            CError.buildErr(this, "被保人投保年龄不符合" + tLMRiskAppDB + "投保要求！");
            return false;
        }

        BqCalBase tBqCalBase = new BqCalBase();
        double OldPrem = aLPPolSchema.getPrem(); //保存重算前的保费值

        /*      若要的替换被保人 是以前保全增人时录入的，会出现问题*/
        //所以做替换被保人时 计算保费也采用增人的算法
        aLPEdorItemSchema.setEdorType("NI");
        ReCalBL tReCalBL = new ReCalBL(aLPPolSchema, aLPEdorItemSchema);
        //在将edortype改回来
        aLPEdorItemSchema.setEdorType("IR");
        /*------------------------------------------*/
        if (!tReCalBL.recal()) { // 保额，保费重算
            this.mErrors.copyAllErrors(tReCalBL.mErrors);
            CError.buildErr(this,
                            "重算失败，原因可能是：" +
                            "真实年龄、性别不在投保范围内，且生效小于2年，请根据条款解除保险合同，若分红型产品已经领取红利的扣除已支付的红利后无息返还保费！");
            CError.buildErr(this,
                            "重算失败，原因可能是：" +
                            "真实年龄、性别不在投保范围内，且生效大于等于2年，请转精算个案处理！");
            return false;
        }
        aLPPolSchema.setSchema(tReCalBL.aftLPPolSet.get(1));
//        mLPDutySet.add(tReCalBL.aftLPDutySet);
//        mLPPremSet.add(tReCalBL.aftLPPremSet);
//        mLPGetSet.add(tReCalBL.aftLPGetSet);

        if (OldPrem != aLPPolSchema.getPrem()) { // 如果保费有变化，则计算各期保费变动总额和利息
            tBqCalBase.setCalType(aLPEdorItemSchema.getEdorType());
            tBqCalBase.setCount(aLPPolSchema.getInsuredPeoples());
            tBqCalBase.setPolNo(aLPPolSchema.getPolNo());
            tBqCalBase.setGrpPolNo(aLPPolSchema.getGrpPolNo());
            tBqCalBase.setPrem(OldPrem);
            tBqCalBase.setNewPrem(aLPPolSchema.getPrem());
            tBqCalBase.setGet(aLPPolSchema.getAmnt());
            tBqCalBase.setMult(aLPPolSchema.getMult());
            tBqCalBase.setYears(aLPPolSchema.getYears());
            tBqCalBase.setInsuYear(aLPPolSchema.getInsuYear());
            tBqCalBase.setInsuYearFlag(aLPPolSchema.getInsuYearFlag());
            tBqCalBase.setAppAge(aLPPolSchema.getInsuredAppAge());
            tBqCalBase.setSex(aLPPolSchema.getInsuredSex());
            tBqCalBase.setJob(aLPPolSchema.getOccupationType());

            LMPolDutyEdorCalDB tLMPolDutyEdorCalDB = new LMPolDutyEdorCalDB();
            tLMPolDutyEdorCalDB.setRiskCode(aLPPolSchema.getRiskCode());
            tLMPolDutyEdorCalDB.setEdorType(aLPEdorItemSchema.getEdorType());
            LMPolDutyEdorCalSet tLMPolDutyEdorCalSet = tLMPolDutyEdorCalDB.
                    query(); //查找保费累计变动金额计算信息
            if (tLMPolDutyEdorCalDB.mErrors.needDealError()) {
                mErrors.copyAllErrors(tLMPolDutyEdorCalDB.mErrors);
                mErrors.addOneError(new CError("查询险种责任保全计算信息失败！"));
                return false;
            }
            if (tLMPolDutyEdorCalSet == null ||
                tLMPolDutyEdorCalSet.size() <= 0) {
                return true; //没有计算信息，则不作计算
            }
        }
        return true;
    }


    /**
     * 校验传入的数据
     * @return boolean
     */
    private boolean checkData() {
//        if (!this.checkLPPerson())
//        {
//            return false;
//        }
//        if (!this.checkLPAddress())
//        {
//            return false;
//        }
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setEdorNo(mLPEdorItemSchema.getEdorNo());
        tLPGrpEdorItemDB.setEdorType("IR");
        mLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(1);
        if (mLPGrpEdorItemSchema.getEdorState().equals("2")) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorICDetailBL";
            tError.functionName = "Preparedata";
            tError.errorMessage = "该保全已经申请确认不能修改!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }


    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     * @return boolean
     */
    private boolean dealData() {
        /**
         * 根据C表得到相关信息
         */
        /*
               SSRS tSSRS = new SSRS();
               String sql = "Select Case When max(AddressNo) Is Null Then 0 Else max(AddressNo) End from LCAddress where CustomerNo='"
                   + CustomerNoNew + "'";
               ExeSQL tExeSQL = new ExeSQL();
               tSSRS = tExeSQL.execSQL(sql);
               Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
               int tttNo = firstinteger.intValue() + 1;
               //Integer integer = new Integer(ttNo);
               //String tttNo = integer.toString();
               logger.debug("得到的地址码是：" + tttNo);
         */
        //得到P表Person数据
        LDPersonSchema tLDPersonSchema = new LDPersonSchema();
        LPPersonSchema tLPPersonSchema = new LPPersonSchema();
        LPAddressSchema tLPAddressSchema = new LPAddressSchema();
        tLPAddressSchema.setSchema(this.mLPAddressSchema);
        String tsql = "";
        if (mLPPersonSchema.getIDNo().equals("")) {
            tsql = " select customerno from ldperson where 1=1 "
                   + " and name = '" + mLPPersonSchema.getName() + "'"
                   + " and sex = '" + mLPPersonSchema.getSex() + "'"
                   //+ " and IDNo is NULL "
                   //+ " and IDType = '" + mLPPersonSchema.getIDType() +
                   //"'"
                   + " and Birthday = '" + mLPPersonSchema.getBirthday() +
                   "'";

        } else {

            tsql = " select customerno from ldperson where 1=1 "
                   + " and name = '" + mLPPersonSchema.getName() + "'"
                   //+ " and sex = '" + mLPPersonSchema.getSex() + "'"
                   + " and IDNo = '" + mLPPersonSchema.getIDNo() + "'"
                   + " and IDType = '" + mLPPersonSchema.getIDType() +
                   "'";
            //+ " and Birthday = '" + mLPPersonSchema.getBirthday() +
            //"'";
        }
        String ss = "";
        logger.debug("----------tsql------" + tsql);
        ExeSQL ttExeSQL = new ExeSQL();
        ss = ttExeSQL.getOneValue(tsql);
        logger.debug("sfdjfldjfl:" + ss);

        //判断是否需要新建客户
        //if ((mLPPersonSchema.getCustomerNo() == null ||
        //  ("").equals(mLPPersonSchema.getCustomerNo()))&&(ss == null || ss.equals("")))
        if (ss == null || ss.equals("")) {
            String tNo;
            tNo = PubFun1.CreateMaxNo("CUSTOMERNO", "SN");
            mLPPersonSchema.setCustomerNo(tNo);
            this.CustomerNoNew = tNo;
            //ref.transFields(tLPPersonSchema,mLPPersonSchema);

            tLPPersonSchema.setSchema(mLPPersonSchema);
            tLPPersonSchema.setOperator(mGlobalInput.Operator);
            tLPPersonSchema.setMakeDate(this.theCurrentDate);
            tLPPersonSchema.setMakeTime(this.theCurrentTime);
            tLPPersonSchema.setModifyDate(this.theCurrentDate);
            tLPPersonSchema.setModifyTime(this.theCurrentTime);
            mLPPersonSet.add(tLPPersonSchema);
            if (tLPAddressSchema != null && tLPAddressSchema.getCustomerNo() != null &&
                !tLPAddressSchema.getCustomerNo().trim().equals("")) {
                SSRS tSSRS = new SSRS();
                String sql = "Select Case When max(AddressNo) Is Null Then 0 Else max(AddressNo) End from LCAddress where CustomerNo='"
                             + CustomerNoNew + "'";
                ExeSQL tExeSQL = new ExeSQL();
                tSSRS = tExeSQL.execSQL(sql);
                Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
                int tttNo = firstinteger.intValue() + 1;
                //Integer integer = new Integer(ttNo);
                //String tttNo = integer.toString();
                logger.debug("得到的地址码是：" + tttNo);

                tLPAddressSchema.setCustomerNo(this.CustomerNoNew);
                tLPAddressSchema.setAddressNo(tttNo);
                tLPAddressSchema.setOperator(mGlobalInput.Operator);
                tLPAddressSchema.setMakeDate(this.theCurrentDate);
                tLPAddressSchema.setMakeTime(this.theCurrentTime);
                tLPAddressSchema.setModifyDate(this.theCurrentDate);
                tLPAddressSchema.setModifyTime(this.theCurrentTime);
                mLPAddressSet.add(tLPAddressSchema);
            }

        } else {
            this.CustomerNoNew = ss;
            //防止更换成保单原有存在的人加的校验
            LCContDB tLCContDB = new LCContDB();
            tLCContDB.setGrpContNo(mLPGrpEdorItemSchema.getGrpContNo());
            tLCContDB.setInsuredNo(CustomerNoNew);
            LCContSet tLCContSet = tLCContDB.query();
	    logger.debug("----CustomerNoNew:"+CustomerNoNew);
            if (tLCContSet.size()>0&& !tLCContSet.get(1).getAppFlag().equals("4")) {
                CError tError = new CError();
                tError.moduleName = "PEdorIRDetailBL";
                tError.functionName = "dealData";
                tError.errorMessage = "保单下已存在该被保人:"+tLCContSet.get(1).getInsuredName()+"，不能重复添加！";
                this.mErrors.addOneError(tError);
                return false;
            }
            //end
            LDPersonDB tLDPersonDB = new LDPersonDB();
            tLDPersonDB.setCustomerNo(CustomerNoNew);
            if (!tLDPersonDB.getInfo()) {
                CError tError = new CError();
                tError.moduleName = "PEdorIRDetailBL";
                tError.functionName = "dealData";
                tError.errorMessage = "tLDPersonDB.getInfo!";
                this.mErrors.addOneError(tError);
                return false;
            }
            tLDPersonSchema = tLDPersonDB.getSchema();
            ref.transFields(tLPPersonSchema, tLDPersonSchema);
            tLPPersonSchema.setSchema(mLPPersonSchema);
            tLPPersonSchema.setOperator(mGlobalInput.Operator);
            tLPPersonSchema.setMakeDate(this.theCurrentDate);
            tLPPersonSchema.setMakeTime(this.theCurrentTime);
            tLPPersonSchema.setModifyDate(this.theCurrentDate);
            tLPPersonSchema.setModifyTime(this.theCurrentTime);

            mLPPersonSet.add(tLPPersonSchema);

            if (tLPAddressSchema != null && tLPAddressSchema.getCustomerNo() != null &&
                !tLPAddressSchema.getCustomerNo().trim().equals("")) {
                SSRS tSSRS = new SSRS();
                String sql = "Select Case When max(AddressNo) Is Null Then 0 Else max(AddressNo) End from LCAddress where CustomerNo='"
                             + CustomerNoNew + "'";
                ExeSQL tExeSQL = new ExeSQL();
                tSSRS = tExeSQL.execSQL(sql);
                Integer firstinteger = Integer.valueOf(tSSRS.GetText(1, 1));
                int tttNo = firstinteger.intValue() + 1;
                //Integer integer = new Integer(ttNo);
                //String tttNo = integer.toString();
                logger.debug("得到的地址码是：" + tttNo);
                tLPAddressSchema.setCustomerNo(this.CustomerNoNew);
                tLPAddressSchema.setOperator(mGlobalInput.Operator);
                tLPAddressSchema.setAddressNo(tttNo);
                tLPAddressSchema.setMakeDate(this.theCurrentDate);
                tLPAddressSchema.setMakeTime(this.theCurrentTime);
                tLPAddressSchema.setModifyDate(this.theCurrentDate);
                tLPAddressSchema.setModifyTime(this.theCurrentTime);
                mLPAddressSet.add(tLPAddressSchema);
            }

        }
       
        //得到P表合同信息
        LPContSchema tLPContSchema = new LPContSchema();
        LCContSchema tLCContSchema = new LCContSchema();
        LCContDB tLCContDB = new LCContDB();
        tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
	tLCContDB.setAppFlag("1");
        tLCContSchema = tLCContDB.query().get(1);
        if (tLCContSchema == null) {
            CError tError = new CError();
            tError.moduleName = "PEdorIRDetailBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询个人保单表失败！(个人客户号："+CustomerNoBak+"),原因有可能该保单不存在或者已经失效。";
            this.mErrors.addOneError(tError);
            return false;
        }
        //SB提的需求，上线后又不要了
//        String tSql = "select min(months_between(add_months(edorappdate, -3), '"+mLPGrpEdorItemSchema.getEdorAppDate()+"')) from lpedoritem where" 
//        			+ " exists (select 'X'  from lpcont  where 1 = 1  and edorno = lpedoritem.edorno and edortype = 'IR' and insuredno = '"+CustomerNoNew+"') and edortype = 'IR' and edorstate = '0'";
//        ExeSQL tExeSQL = new ExeSQL();
//		String dNum = tExeSQL.getOneValue(tSql);
//		if (dNum != null && !dNum.equals("")&&Double.parseDouble(dNum)<0) {
//			CError.buildErr(this, "换入的被保人在3个月内发生过被替换记录,不能申请替换!");
//			return false;
//		}
        ref.transFields(tLPContSchema, tLCContSchema);
//        tLPContSchema.setGrpContNo(tLCContSchema.getGrpContNo());
        tLPContSchema.setSchema(mLPContSchema);
        tLPContSchema.setModifyDate(this.theCurrentDate);
        tLPContSchema.setModifyTime(this.theCurrentTime);
        tLPContSchema.setInsuredNo(this.CustomerNoNew);
        //tLPContSchema.setSchema(mLPContSchema);
        mLPContSet.add(tLPContSchema);
        mLPContSchema.setSchema(tLPContSchema);

        //得到P表的被保人信息
        LCInsuredSchema tLCInsuredSchema = new LCInsuredSchema();
        LPInsuredSchema tLPInsuredSchema = new LPInsuredSchema();
        LCInsuredDB tLCInsuredDB = new LCInsuredDB();
        tLCInsuredDB.setContNo(this.ContNo);
        tLCInsuredDB.setInsuredNo(this.CustomerNoBak);
        logger.debug("-----------------contno=======" + ContNo);
        logger.debug("---------------CustomerNoBak=======" +
                           CustomerNoBak);
        tLCInsuredSchema = tLCInsuredDB.query().get(1);
        if (tLCInsuredSchema == null) {
            CError tError = new CError();
            tError.moduleName = "PEdorIRDetailBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询LCInsured失败！";
            this.mErrors.addOneError(tError);
            return false;
        }

        //logger.debug("----------------"+tLCInsuredSchema.getGrpContNo());
        //ref.transFields(tLPInsuredSchema,tLCInsuredSchema);
        //logger.debug("---------------"+tLPInsuredSchema.getGrpContNo());
        tLPInsuredSchema.setSchema(this.mLPInsuredSchema);
        tLPInsuredSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
        tLPInsuredSchema.setEdorType(mLPEdorItemSchema.getEdorType());
        tLPInsuredSchema.setGrpContNo(tLCInsuredSchema.getGrpContNo());
        tLPInsuredSchema.setContNo(tLCInsuredSchema.getContNo());
        tLPInsuredSchema.setPrtNo(tLCInsuredSchema.getPrtNo());
        tLPInsuredSchema.setAppntNo(tLCInsuredSchema.getAppntNo());
        tLPInsuredSchema.setManageCom(tLCInsuredSchema.getManageCom());
        tLPInsuredSchema.setOperator(mGlobalInput.Operator);
        tLPInsuredSchema.setExecuteCom(mGlobalInput.ManageCom);
        tLPInsuredSchema.setMakeDate(this.theCurrentDate);
        tLPInsuredSchema.setMakeTime(this.theCurrentTime);
        tLPInsuredSchema.setModifyDate(this.theCurrentDate);
        tLPInsuredSchema.setModifyTime(this.theCurrentTime);
        tLPInsuredSchema.setInsuredNo(this.CustomerNoNew);
        //mLPInsuredSchema.setSchema(tLPInsuredSchema);
        logger.debug("---------222------" + tLPInsuredSchema.getGrpContNo());
        mLPInsuredSet.add(tLPInsuredSchema);

        /*
                 //得到地址信息
                 LPAddressSchema tLPAddressSchema = new LPAddressSchema();
                 tLPAddressSchema.setSchema(this.mLPAddressSchema);
                 if(tLPAddressSchema != null && tLPAddressSchema.getCustomerNo() != null && !tLPAddressSchema.getCustomerNo().trim().equals("")){
            tLPAddressSchema.setOperator(mGlobalInput.Operator);
            tLPAddressSchema.setMakeDate(this.theCurrentDate);
            tLPAddressSchema.setMakeTime(this.theCurrentTime);
            tLPAddressSchema.setModifyDate(this.theCurrentDate);
            tLPAddressSchema.setModifyTime(this.theCurrentTime);
            mLPAddressSet.add(tLPAddressSchema);
                 }
         */
        //得到被替换被保人险种信息
        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setInsuredNo(this.CustomerNoBak);
        tLCPolDB.setContNo(this.ContNo);
        tLCPolDB.setAppFlag("1"); //侯哥说退保的险种不能替换
        mLCPolSet = tLCPolDB.query();
        if (mLCPolSet.size() == 0) {
            CError tError = new CError();
            tError.moduleName = "PEdorIRDetailBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询保单下有效险种失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        for (int i = 0; i < mLCPolSet.size(); i++) {
            LCPolSchema tLCPolSchema = mLCPolSet.get(i + 1);
            LPPolSchema tLPPolSchema = new LPPolSchema();
            ref.transFields(tLPPolSchema, tLCPolSchema);
            tLPPolSchema.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
            tLPPolSchema.setEdorType(mLPEdorItemSchema.getEdorType());
            tLPPolSchema.setInsuredNo(mLPContSchema.getInsuredNo());
            tLPPolSchema.setInsuredName(mLPContSchema.getInsuredName());
            tLPPolSchema.setInsuredSex(mLPContSchema.getInsuredSex());
            tLPPolSchema.setInsuredBirthday(mLPContSchema.getInsuredBirthday());
            this.mLPPolSet.add(tLPPolSchema);
        }
        //初始化LPEdorItemSchema & LPEdorMainSchema
        initEdorObject();
        return true;
    }

    private void initEdorObject() {
        //mLPEdorItemSchema.setInsuredNo(mLPInsuredSchema.getInsuredNo());
//    LCContDB tLCContDB=new LCContDB();
//    tLCContDB.setContNo(mLPInsuredSchema.getContNo());
//    LCContSchema tLCContSchema=tLCContDB.query().get(1);
//    ref.transFields(mLPEdorItemSchema, tLCContSchema);
        LPGrpEdorItemDB tLPGrpEdorItemDB = new LPGrpEdorItemDB();
        tLPGrpEdorItemDB.setEdorNo(this.mLPEdorItemSchema.getEdorNo());
        tLPGrpEdorItemDB.setEdorType("IR");
        LPGrpEdorItemSchema tLPGrpEdorItemSchema = tLPGrpEdorItemDB.query().get(
                1);

        LCPolDB tLCPolDB = new LCPolDB();
        tLCPolDB.setContNo(mLPInsuredSchema.getContNo());
        tLCPolDB.setAppFlag("1");
        LCPolSet tLCPolSet = tLCPolDB.query();
        mLPEdorItemSet = new LPEdorItemSet();
        for (int i = 1; i <= tLCPolSet.size(); i++) {
            ref.transFields(mLPEdorItemSchema, tLCPolSet.get(i));
            mLPEdorItemSchema.setInsuredNo(CustomerNoNew);
            mLPEdorItemSchema.setPolNo(tLCPolSet.get(i).getPolNo());
            mLPEdorItemSchema.setGrpContNo(tLCPolSet.get(1).getGrpContNo());
            mLPEdorItemSchema.setEdorState("1");
            mLPEdorItemSchema.setUWFlag("0");
            mLPEdorItemSchema.setGetMoney(0);
            mLPEdorItemSchema.setChgPrem(0);
            mLPEdorItemSchema.setChgAmnt(0);
            mLPEdorItemSchema.setOperator(mGlobalInput.Operator);
            mLPEdorItemSchema.setManageCom(tLPGrpEdorItemSchema.getManageCom());
            mLPEdorItemSchema.setMakeDate(PubFun.getCurrentDate());
            mLPEdorItemSchema.setMakeTime(PubFun.getCurrentTime());
            mLPEdorItemSchema.setModifyDate(PubFun.getCurrentDate());
            mLPEdorItemSchema.setModifyTime(PubFun.getCurrentTime());
            mLPEdorItemSchema.setEdorAppNo(mLPEdorItemSchema.getEdorNo());
            mLPEdorItemSchema.setEdorValiDate(tLPGrpEdorItemSchema.
                                              getEdorValiDate());
            mLPEdorItemSchema.setEdorAppDate(tLPGrpEdorItemSchema.
                                             getEdorAppDate());
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            ref.transFields(tLPEdorItemSchema, mLPEdorItemSchema);
            mLPEdorItemSet.add(tLPEdorItemSchema);
        }

        LPEdorMainSchema tLPEdorMainSchema = new LPEdorMainSchema();
        ref.transFields(tLPEdorMainSchema, mLPEdorItemSchema);
        tLPEdorMainSchema.setChgAmnt(0);
        tLPEdorMainSchema.setChgPrem(0);
        tLPEdorMainSchema.setGetMoney(0);
        tLPEdorMainSchema.setGetInterest(0);
        mLPEdorMainSet.add(tLPEdorMainSchema);
    }


    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     * @param cInputData VData
     * @return boolean
     */
    private boolean getInputData(VData cInputData) {
        try {
            mGlobalInput.setSchema((GlobalInput) cInputData.
                                   getObjectByObjectName(
                                           "GlobalInput", 0));
            this.mLPContSchema.setSchema((LPContSchema) cInputData.
                                         getObjectByObjectName("LPContSchema",
                    0));
            this.mLPInsuredSchema.setSchema((LPInsuredSchema) cInputData.
                                            getObjectByObjectName(
                    "LPInsuredSchema",
                    0));
            this.mLPAddressSchema.setSchema((LPAddressSchema) cInputData.
                                            getObjectByObjectName(
                    "LPAddressSchema",
                    0));

            this.mLPPersonSchema.setSchema((LPPersonSchema) cInputData.
                                           getObjectByObjectName(
                    "LPPersonSchema", 0));
            this.mLPCustomerImpartSet = (LPCustomerImpartSet) cInputData.
                                        getObjectByObjectName(
                                                "LPCustomerImpartSet", 0);
            this.mLPCustomerImpartDetailSet = (LPCustomerImpartDetailSet)
                                              cInputData.
                                              getObjectByObjectName(
                    "LPCustomerImpartDetailSet", 0);
            this.mTransferData = (TransferData) cInputData.
                                 getObjectByObjectName(
                                         "TransferData", 0);
            mLPEdorItemSchema = (LPEdorItemSchema) cInputData.
                                getObjectByObjectName(
                                        "LPEdorItemSchema", 0);
            if (mTransferData == null) {
                CError tError = new CError();
                tError.moduleName = "ContInsuredBL";
                tError.functionName = "getInputData";
                tError.errorMessage = "未得到传送数据!";
                this.mErrors.addOneError(tError);
                return false;
            }
            if (mLPEdorItemSchema == null || mGlobalInput == null) {
                mErrors.addOneError(new CError("数据传输不完全！"));
                return false;
            }

            CustomerNo = (String) mTransferData.getValueByName("CustomerNo");
            CustomerNoBak = (String) mTransferData.getValueByName(
                    "CustomerNoBak");
            ContNo = (String) mTransferData.getValueByName("ContNo");
        } catch (Exception ex) {

            CError tError = new CError();
            tError.moduleName = "ContInsuredBL";
            tError.functionName = "getInputData";
            tError.errorMessage = ex.toString();
            this.mErrors.addOneError(tError);
            return false;

        }
        return true;
    }

    private boolean prepareOutputData() {
        try {
            String strSQLDel1 = "delete from LPPerson where EdorNo ='" +
                                this.mLPEdorItemSchema.getEdorNo() +
                                "' and EdorType ='" +
                                this.mLPEdorItemSchema.getEdorType() +
                                "' and CustomerNo in (select InsuredNo from LPEdorItem " +
                                " where EdorNo = '"
                                + this.mLPEdorItemSchema.getEdorNo() +
                                "' and EdorType = '"
                                + this.mLPEdorItemSchema.getEdorType() +
                                "' and ContNo= '"
                                + this.mLPEdorItemSchema.getContNo() + "')";
            logger.debug("---------------" + strSQLDel1);
            map.put(strSQLDel1, "DELETE");
            String strSQLDel2 = "delete from LPAddress where EdorNo ='" +
                                this.mLPEdorItemSchema.getEdorNo() +
                                "' and EdorType ='" +
                                this.mLPEdorItemSchema.getEdorType() +
                                "' and CustomerNo in (select InsuredNo from LPEdorItem " +
                                " where EdorNo = '"
                                + this.mLPEdorItemSchema.getEdorNo() +
                                "' and EdorType = '"
                                + this.mLPEdorItemSchema.getEdorType() +
                                "' and ContNo= '"
                                + this.mLPEdorItemSchema.getContNo() + "')";
            logger.debug("---------------" + strSQLDel2);
            map.put(strSQLDel2, "DELETE");

            String strSQLDel3 = "delete from  LPEdorItem " +
                                " where EdorNo = '"
                                + this.mLPEdorItemSchema.getEdorNo() +
                                "' and EdorType = '"
                                + this.mLPEdorItemSchema.getEdorType() +
                                "' and ContNo= '"
                                + this.mLPEdorItemSchema.getContNo() + "'";
            map.put(strSQLDel3, "DELETE");

            strSQLDel3 = "delete from  LPInsured " +
                         " where EdorNo = '"
                         + this.mLPEdorItemSchema.getEdorNo() +
                         "' and EdorType = '"
                         + this.mLPEdorItemSchema.getEdorType() +
                         "' and ContNo= '"
                         + this.mLPEdorItemSchema.getContNo() + "'";
            map.put(strSQLDel3, "DELETE");

//      mLPEdorItemSchema.setInsuredNo(this.CustomerNoNew);
            map.put(this.mLPEdorItemSet, "DELETE&INSERT");
            map.put(this.mLPEdorMainSet, "DELETE&INSERT");
            map.put(this.mLPContSet, "DELETE&INSERT");
            map.put(this.mLPAddressSet, "DELETE&INSERT");
            map.put(this.mLPPersonSet, "DELETE&INSERT");
            map.put(this.mLPInsuredSet, "DELETE&INSERT");
            map.put(mLPPolSet, "DELETE&INSERT");
            logger.debug("-----------------" +
                               this.mLPEdorItemSchema.getEdorNo());
            logger.debug("-----------------" +
                               this.mLPEdorItemSchema.getEdorType());
            String strSQL1 = "update LPGrpEdorItem set edorstate='1'" +
                             " where EdorNo = '"
                             + this.mLPEdorItemSchema.getEdorNo() +
                             "' and EdorType = '" +
                             this.mLPEdorItemSchema.getEdorType() + "'";
            logger.debug("---------------" + strSQL1);
            map.put(strSQL1, "UPDATE");
            logger.debug("111111111111119");
            String strSQL2 = "update LPGrpEdorMain set edorstate='1'" +
                             " where EdorNo = '"
                             + this.mLPEdorItemSchema.getEdorNo() + "'";
            map.put(strSQL2, "UPDATE");
            logger.debug("111111111111110");

//            map.put(this.mLPCustomerImpartSet,"DELETE&INSERT");
//            map.put(this.mLPCustomerImpartParamsSet,"DELETE&INSERT");
//            map.put(this.mLPCustomerImpartDetailSet,"DELETE&INSERT");
            this.mInputData.clear();
            logger.debug("111111111111111--");
            mInputData.add(map);
            logger.debug("++++++++++++++++++++++" +
                               mLPInsuredSchema.getInsuredNo());
            mResult.add(mLPInsuredSchema.getInsuredNo());
            this.mResult.add(map);

        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LCInsuredBL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult() {
        return this.mResult;
    }

    private boolean delrisk(VData cInputData) {
        this.mTransferData = (TransferData) cInputData.
                             getObjectByObjectName(
                                     "TransferData", 0);
        if (mTransferData == null) {
            CError tError = new CError();
            tError.moduleName = "ContInsuredBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "未得到传送数据!";
            this.mErrors.addOneError(tError);
            return false;

        }
        String InsuredNo = (String) mTransferData.getValueByName("InsuredNo");
        String PolNo = (String) mTransferData.getValueByName("PolNo");
        map.put("delete from lcpol where PolNo='" + PolNo + "'", "DELETE");
        map.put("delete from LCInsuredRelated where PolNo='" + PolNo + "'",
                "DELETE");
        map.put("delete from LCDuty where PolNo='" + PolNo + "'", "DELETE");
        map.put("delete from LCBnf where PolNo='" + PolNo + "'", "DELETE");
        map.put("delete from LCPrem where PolNo='" + PolNo + "'", "DELETE");
        map.put("delete from LCGet where PolNo='" + PolNo + "'", "DELETE");
        map.put("delete from LCSpec where PolNo='" + PolNo + "'", "DELETE");
        return true;
    }


    /**
     * 检查被保人录入的数据是否正确(适用于个人投保单和集体下的个单)
     * 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
     * @return boolean
     */
    private boolean checkLPPerson() {
        //如果是无名单或者公共帐户的个人，不校验返回
        if (StrTool.compareString(tPolTypeFlag, "1")
            || StrTool.compareString(tPolTypeFlag, "2")) {
            return true;
        }

        if (mLPPersonSchema != null) {
            if (mLPPersonSchema.getName() != null) { //去空格
                mLPPersonSchema.setName(mLPPersonSchema.getName().trim());
                if (mLPPersonSchema.getSex() != null) {
                    mLPPersonSchema.setSex(mLPPersonSchema.getSex().trim());
                }
                if (mLPPersonSchema.getIDNo() != null) {
                    mLPPersonSchema.setIDNo(mLPPersonSchema.getIDNo().trim());
                }
                if (mLPPersonSchema.getIDType() != null) {
                    mLPPersonSchema.setIDType(mLPPersonSchema.getIDType().trim());
                }
                if (mLPPersonSchema.getBirthday() != null) {
                    mLPPersonSchema.setBirthday(mLPPersonSchema.getBirthday().
                                                trim());
                }

            }
            if (mLPPersonSchema.getCustomerNo() != null) {
                //如果有客户号
                if (!mLPPersonSchema.getCustomerNo().equals("")) {
                    LDPersonDB tLDPersonDB = new LDPersonDB();

                    tLDPersonDB.setCustomerNo(mLPPersonSchema.getCustomerNo());
                    if (!tLDPersonDB.getInfo()) {
                        CError tError = new CError();
                        tError.moduleName = "ContBL";
                        tError.functionName = "checkPerson";
                        tError.errorMessage = "查询客户失败!请确认您录入的客户号码";
                        this.mErrors.addOneError(tError);

                        return false;
                    }
                    if (mLPPersonSchema.getName() != null) {
                        String Name = StrTool.GBKToUnicode(tLDPersonDB.getName()
                                .trim());
                        String NewName = StrTool.GBKToUnicode(mLPPersonSchema.
                                getName()
                                .trim());

                        if (!Name.equals(NewName)) {
                            CError tError = new CError();
                            tError.moduleName = "ProposalBL";
                            tError.functionName = "checkPerson";
                            tError.errorMessage = "您输入的被保险人客户号对应在数据库中的客户姓名("
                                                  + Name + ")与您录入的客户姓名("
                                                  + NewName + ")不匹配！";
                            this.mErrors.addOneError(tError);

                            return false;
                        }
                    }
                    if (mLPPersonSchema.getSex() != null) {
                        if (!tLDPersonDB.getSex().equals(mLPPersonSchema
                                .getSex())) {
                            CError tError = new CError();
                            tError.moduleName = "ContBL";
                            tError.functionName = "checkPerson";
                            tError.errorMessage = "您输入的被保险人客户号对应在数据库中的客户性别("
                                                  + tLDPersonDB.getSex()
                                                  + ")与您录入的客户性别("
                                                  + mLPPersonSchema.getSex()
                                                  + ")不匹配！";
                            this.mErrors.addOneError(tError);

                            return false;
                        }
                    }
                    if (mLPPersonSchema.getBirthday() != null) {
                        if (!tLDPersonDB.getBirthday().equals(mLPPersonSchema
                                .getBirthday())) {
                            CError tError = new CError();
                            tError.moduleName = "ContBL";
                            tError.functionName = "checkPerson";
                            tError.errorMessage = "您输入的被保险人客户号对应在数据库中的客户生日("
                                                  + tLDPersonDB.getBirthday()
                                                  + ")与您录入的客户生日("
                                                  + mLPPersonSchema.getBirthday()
                                                  + ")不匹配！";
                            this.mErrors.addOneError(tError);

                            return false;
                        }
                    }

                } else {
                    //如果没有客户号,查找客户信息表是否有相同名字，性别，出生年月，身份证号的纪录，若有，取客户号
                    if (mLPPersonSchema.getIDType().equals("0")) {
                        if ((mLPPersonSchema.getName() != null)
                            && (mLPPersonSchema.getSex() != null)
                            && (mLPPersonSchema.getIDNo() != null)) {
                            LDPersonDB tLDPersonDB = new LDPersonDB();
                            tLDPersonDB.setName(mLPPersonSchema.getName());
                            tLDPersonDB.setSex(mLPPersonSchema.getSex());
                            tLDPersonDB.setBirthday(mLPPersonSchema.getBirthday());

                            tLDPersonDB.setIDType("0"); //证件类型为身份证
                            tLDPersonDB.setIDNo(mLPPersonSchema.getIDNo());

                            LDPersonSet tLDPersonSet = tLDPersonDB.query();
                            if (tLDPersonSet != null) {
                                if (tLDPersonSet.size() > 0) {
                                    mLPPersonSchema.setCustomerNo(tLDPersonSet.
                                            get(1)
                                            .getCustomerNo());
                                }
                            }
                        }
                    }
                }
            }

        }

        return true;
    }


    /**
     * 检查地址数据是否正确
     * 如果在处理过程中出错或者数据有错误，则返回false,否则返回true
     * @return boolean
     */
    private boolean checkLPAddress() {
        if (mLPAddressSchema != null) {
            if (mLPAddressSchema.getPostalAddress() != null) { //去空格
                mLPAddressSchema.setPostalAddress(mLPAddressSchema.
                                                  getPostalAddress().
                                                  trim());
                if (mLPAddressSchema.getZipCode() != null) {
                    mLPAddressSchema.setZipCode(mLPAddressSchema.getZipCode().
                                                trim());
                }
                if (mLPAddressSchema.getPhone() != null) {
                    mLPAddressSchema.setPhone(mLPAddressSchema.getPhone().trim());
                }

            }
            //if (mLPAddressSchema.getAddressNo() != null)
            //getAddressNo是integer,不能用getAddressNo() != null
            logger.debug(mLPAddressSchema.getAddressNo());
            if (mLPAddressSchema.getAddressNo() > 0) {
                //如果有地址号
                //if (!mLPAddressSchema.getAddressNo().equals(""))
                //{
                logger.debug("===" + mLPAddressSchema.getAddressNo());
                LCAddressDB tLCAddressDB = new LCAddressDB();

                tLCAddressDB.setAddressNo(mLPAddressSchema.getAddressNo());
                tLCAddressDB.setCustomerNo(mLPAddressSchema.getCustomerNo());
                if (!tLCAddressDB.getInfo()) {
                    CError tError = new CError();
                    tError.moduleName = "ContBL";
                    tError.functionName = "checkAddress";
                    tError.errorMessage = "数据库查询失败!";
                    this.mErrors.addOneError(tError);

                    return false;
                }
                //}
            }
        }
        return true;
    }


    /**
     * 对比险种中关系到保额保费计算的被保人信息是否有更改
     * @param vLDPersonSchema LDPersonSchema
     * @param vLCInsuredSchema LCInsuredSchema
     * @return boolean
     */
    private static boolean balance(LDPersonSchema vLDPersonSchema,
                                   LCInsuredSchema vLCInsuredSchema) {
        //性别
        if (!(vLDPersonSchema.getSex().trim()).equals(vLCInsuredSchema.getSex().
                trim())) {
            logger.debug("性别:" + vLDPersonSchema.getSex() + ":"
                               + vLCInsuredSchema.getSex());
            return true;
        }
        //生日
        if (!(vLDPersonSchema.getBirthday().trim()).equals(vLCInsuredSchema.
                getBirthday().trim())) {
            logger.debug("生日:" + vLDPersonSchema.getBirthday() + ":"
                               + vLCInsuredSchema.getBirthday());
            return true;
        }
        //职业类别
        if (vLDPersonSchema.getOccupationType() != null) {
            if (!StrTool.compareString(vLDPersonSchema.getOccupationType(),
                                       vLCInsuredSchema.getOccupationType())) {
                logger.debug("职业:" + vLDPersonSchema.getOccupationType()
                                   + ":" + vLCInsuredSchema.getOccupationType());
                return true;
            }
        }
        //职业类别
        if (!StrTool.compareString(vLDPersonSchema.getIDType(),
                                   vLCInsuredSchema.getIDType())) {
            logger.debug("证件类型:" + vLDPersonSchema.getIDType()
                               + ":" + vLCInsuredSchema.getIDType());
            return true;
        }

        if (!StrTool.compareString(vLDPersonSchema.getIDNo(),
                                   vLCInsuredSchema.getIDNo())) {
            logger.debug("证件号码:" + vLDPersonSchema.getIDNo()
                               + ":" + vLCInsuredSchema.getIDNo());
            return true;
        }

        else {
            if (vLCInsuredSchema.getOccupationType() != null) {
                return true;
            }
        }

        return false;
    }


    public static void main(String[] args) {
    }
}
