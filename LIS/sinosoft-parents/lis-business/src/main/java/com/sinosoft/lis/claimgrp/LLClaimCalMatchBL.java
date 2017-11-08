package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.bl.LCGetBL;
import com.sinosoft.lis.bq.EdorCalZT;
import com.sinosoft.lis.bq.BqCode;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

/**
 * <p>Title: </p>
 *
 * <p>Description: 计算步骤二：找出匹配保项</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: SinoSoft Co. Ltd,</p>
 *
 * @author 续涛
 * @version 6.0
 */
public class LLClaimCalMatchBL
{
private static Logger logger = Logger.getLogger(LLClaimCalMatchBL.class);


    public CErrors mErrors = new CErrors(); /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData(); /** 往后面传输数据的容器 */
    private VData mResult = new VData(); /** 往界面传输数据的容器 */
    private String mOperate; /** 数据操作字符串 */

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput(); /** 全局数据 */
    private TransferData mTransferData = new TransferData();
    private MMap mMMap = new MMap(); /** 往后面传输的数据库操作 */

    //立案表
    private LLRegisterSchema mLLRegisterSchema = new LLRegisterSchema();

    //立案份案信息
    private LLCaseSet mLLCaseSet = new LLCaseSet();
    private LLCaseSchema mLLCaseSchema = new LLCaseSchema();


    //理赔类型
    private LLAppClaimReasonSet mLLAppClaimReasonSet = new LLAppClaimReasonSet();

    //给付责任信息
    private LCGetSet mLCGetSet = new LCGetSet(); /** 用于获取领取项数据的集合 */
    private LBGetSet mLBGetSet = new LBGetSet(); /** 用于获取领取项数据的集合 */

    private LCPolSchema mLCPolSchema = new LCPolSchema();
    private LCDutySchema mLCDutySchema = new LCDutySchema();
    private LCGetSchema mLCGetSchema = new LCGetSchema();

    //待匹配保项信息
    private LLToClaimDutySet mLLToClaimDutySet = new LLToClaimDutySet();
    private LLToClaimDutySchema mLLToClaimDutySchema = new LLToClaimDutySchema();


    //待匹配保项下费用信息
    private LLToClaimDutyFeeSchema mLLToClaimDutyFeeSchema;
    private LLToClaimDutyFeeSet mLLToClaimDutyFeeSet = new LLToClaimDutyFeeSet();

    //保全批改项目
    private LPEdorItemSchema mLPEdorItemSchema = null;

    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    private ExeSQL mExeSQL = new ExeSQL();


    private String mAccNo = ""; //事件号
    private String mAccDate = ""; //意外事故发生日期
	private String mInsDate = ""; // 出险时间
    private String mClmNo = ""; //赔案号
    private String mContType = ""; //总单类型,1-个人总投保单,2-集体总单
    private String mGetDutyKind; //理赔类型
    private String mGetDutyCode; //责任编码

    private String mMaxCalDate = null; //费用类型的最大计算日期，就是费用明细的结束时间

    private String mNBPolNo = ""; //承保时的保单号

    private String mRiskPeriod = ""; //长险帐户标示 1 表示需要结息
    //账户型案件表
    private LLClaimAccountSet mLLClaimAccountSet=new LLClaimAccountSet();
    private LDExch mLDExch = new LDExch();

    public LLClaimCalMatchBL() {}


    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        logger.debug(
                "----------理算步骤二-----匹配保项-----LLClaimCalMatchBL测试-----开始----------");

        if (!getInputData(cInputData, cOperate)) {
            return false;
        }

        if (!dealData()) {
            return false;
        }

        if (!prepareOutputData()) {
            return false;
        }
//        PubSubmit tPubSubmit = new PubSubmit();
//        if (!tPubSubmit.submitData(mResult, cOperate)) {
//            // @@错误处理
//            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLEndCaseBL";
//            tError.functionName = "submitData";
//            tError.errorMessage = "数据提交失败!";
//            this.mErrors.addOneError(tError);
//            return false;
//            }
        logger.debug(
                "----------理算步骤二-----匹配保项-----LLClaimCalMatchBL测试-----结束----------");

        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData, String cOperate) {
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
        this.mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        this.mAccNo = (String) mTransferData.getValueByName("AccNo"); //事件号
        this.mAccDate = (String) mTransferData.getValueByName("AccDate"); //意外事故发生日期
        this.mClmNo = (String) mTransferData.getValueByName("ClmNo"); //赔案号
        this.mContType = (String) mTransferData.getValueByName("ContType"); //总单类型,1-个人投保单,2-集体总投保单

        return true;
    }


    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData() {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 得到匹配需要用到的计算要素信息
         * 从LLCaseReceipt表中得到医疗费用明细的最大结束日期
         * 根据赔案号，从LLCaseSet表中取出所有出险人信息
         * 根据赔案号，从LLRegister表中取出立案登记信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getMatchFactor()) {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 得到匹配需要用到的计算要素信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getMutiInsuredInfo()) {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 根据一个赔案下的出险人的个数得到相应的匹配数据
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i = 1; i <= mLLCaseSet.size(); i++) {
            mLLCaseSchema = mLLCaseSet.get(i);

            if (!getMatchInfo()) {
                return false;
            }

        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 打印出提示信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        logger.debug("======================================================================================================");
        for (int i = 1; i <= mLCGetSet.size(); i++) {
            logger.debug("-----初步匹配的保项信息-----保单号:[" +
                               mLCGetSet.get(i).getPolNo() + "],理赔类型:[" +
                               mLCGetSet.get(i).getGetDutyKind() + "],责任:[" +
                               mLCGetSet.get(i).getDutyCode() + "],给付责任:[" +
                               mLCGetSet.get(i).getGetDutyCode() + "],投保标志:[" +
                               mLCGetSet.get(i).getState() + "],数据来源:[" +
                               mLCGetSet.get(i).getOperator() + "]");
        }

        logger.debug("======================================================================================================");
        for (int i = 1; i <= mLLToClaimDutySet.size(); i++) {

            logger.debug("-----匹配出来的保项信息-----保单号:[" +
                               mLLToClaimDutySet.get(i).getPolNo() + "],理赔类型:[" +
                               mLLToClaimDutySet.get(i).getGetDutyKind() +
                               "],责任:[" + mLLToClaimDutySet.get(i).getDutyCode() +
                               "],给付责任:[" +
                               mLLToClaimDutySet.get(i).getGetDutyCode() +
                               "],投保被保信息:[" +
                               mLLToClaimDutySet.get(i).getCasePolType() + "]");

        }
        logger.debug("======================================================================================================");

        return true;

    }


    /**
     * 目的：得到匹配需要用到的计算要素信息表
     * @return
     */
    private boolean getMatchFactor() {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 从LLCaseReceipt表中得到医疗费用明细的最大结束日期
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//----2006-03-15 注释掉 泰康不需要这样的控制 P.D------------------------------------
//        String tSql = "select max(enddate) from LLCaseReceipt where 1 = 1"
//                      + " and ClmNo = '" + this.mClmNo + "'";
//
//        ExeSQL tExeSQL = new ExeSQL();
//        SSRS tSSRS = tExeSQL.execSQL(tSql);
//
//        this.mMaxCalDate = tExeSQL.getOneValue(tSql);
//        if (this.mMaxCalDate == null || this.mMaxCalDate.length() == 0) {
//            this.mMaxCalDate = null;
//        } else {
//            this.mMaxCalDate = this.mMaxCalDate.substring(0, 10);
//        }
//------------------------------------end---------------------------------------
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 根据赔案号，从LLCaseSet表中取出所有出险人信息
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLCaseDB tLLCaseDB = new LLCaseDB();
        tLLCaseDB.setCaseNo(this.mClmNo);
        mLLCaseSet = tLLCaseDB.query();

        if (mLLCaseSet == null || mLLCaseSet.size() == 0) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLCaseDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getMatchFactor";
            tError.errorMessage = "在LLCaseDB表中没有找到记录，不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 根据赔案号，从LLRegister表中取出立案登记信息
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        tLLRegisterDB.setRgtNo(this.mClmNo);

        if (tLLRegisterDB.getInfo() == false) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLRegisterDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getMatchFactor";
            tError.errorMessage = "在立案登记信息中没有找到记录,不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
        
		mInsDate = mLLClaimPubFunBL.getInsDate(this.mClmNo);
    	
    	
        return true;
    }


    /**
     * 判断匹配后的保单中是否符合联生险的条件
     * @return
     */
    private boolean getMutiInsuredInfo() {

        //团体报案不用判断
        if (this.mContType.equals("2")) {
            return true;
        }

        //出险人数量只有一个不用判断
        if (mLLCaseSet.size() == 1) {
            return true;
        }

        //出险人数量大于2个时返回
        if (mLLCaseSet.size() > 2) {
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getMutiInsuredInfo";
            tError.errorMessage = "个险下的出险人不能超过2个!匹配理算终止";
            this.mErrors.addOneError(tError);
            return false;
        }

        String tPer1 = "";
        String tPer2 = "";
        if (mLLCaseSet.size() == 2) {
            tPer1 = mLLCaseSet.get(1).getCustomerNo();
            tPer2 = mLLCaseSet.get(2).getCustomerNo();

            String tSql1 = "select * from LCInsuredRelated where 1=1 "
                           + " and (CustomerNo in ('" + tPer1 + "')"
                           + " or MainCustomerNo in ('" + tPer2 + "') )"
                           ;

            LCInsuredRelatedDB tLCInsuredRelatedDB1 = new LCInsuredRelatedDB();
            LCInsuredRelatedSet tLCInsuredRelatedSet1 = tLCInsuredRelatedDB1.
                    executeQuery(tSql1);

            if (tLCInsuredRelatedDB1.mErrors.needDealError()) {
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getMutiInsuredInfo";
                tError.errorMessage = "查询连带被保人信息失败,匹配理算终止!" + tSql1;
                this.mErrors.addOneError(tError);
                return false;
            }

            String tSql2 = "select * from LCInsuredRelated where 1=1 "
                           + " and (CustomerNo in ('" + tPer2 + "')"
                           + " or MainCustomerNo in ('" + tPer1 + "') )"
                           ;

            LCInsuredRelatedDB tLCInsuredRelatedDB2 = new LCInsuredRelatedDB();
            LCInsuredRelatedSet tLCInsuredRelatedSet2 = tLCInsuredRelatedDB2.
                    executeQuery(tSql2);

            if (tLCInsuredRelatedDB2.mErrors.needDealError()) {
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getMutiInsuredInfo";
                tError.errorMessage = "查询连带被保人信息失败,匹配理算终止!" + tSql1;
                this.mErrors.addOneError(tError);
                return false;
            }

            if (tLCInsuredRelatedSet1.size() == 0 &&
                tLCInsuredRelatedSet2.size() == 0) {
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getMutiInsuredInfo";
                tError.errorMessage = "该赔案下的2个出险人没有在同一张保单上,匹配理算终止!";
                this.mErrors.addOneError(tError);
                logger.debug("该赔案下的2个出险人没有在同一张保单上,匹配理算终止!");
                return false;
            }
        }

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－进行匹配－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 得到单个出险人的保项、费用的匹配信息
     * @return
     */
    public boolean getMatchInfo() {
        this.mAccDate = mLLCaseSchema.getAccDate(); //意外事故发生日期
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 获取理赔类型信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getLLAppClaimReason()) {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 得到C表所有的保项--领取项表
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!getLCLBGet()) {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 循环立案记录的该出险人的所有理赔类型，
         *  在lmdutygetclm【责任给付赔付】找出该理赔类型下的给付代码
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tMssage = "";
        for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
            // 得到立案记录的理赔类型，拼成一个字符串。
            String tClaimType = mLLAppClaimReasonSet.get(i).getReasonCode();

            logger.debug("================开始========执行理赔类型为========[" +
                               tClaimType +
                               "]=====的匹配算法===============================");

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No5.1 循环取得LCGet的所有保项,
             * 将符合条件的保项添加到mLCGetSet集合中
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            for (int j = 1; j <= mLCGetSet.size(); j++) {

                LCGetSchema tLCGetSchema = mLCGetSet.get(j);
                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 *  取出险种的帐户标志
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                ExeSQL tExeSQL = new ExeSQL();

                String mriskduty="select distinct riskcode from lmriskduty where dutycode='"+tLCGetSchema.getDutyCode()+"' ";//从关联表里取险种
                String mRiskCode = tExeSQL.getOneValue(mriskduty);
                logger.debug("+++++++++++++++++++++++mRiskCode:"+mRiskCode);
                
				LMDutyGetClmSchema ttLMDutyGetClmSchema = mLLClaimPubFunBL
				.getLMDutyGetClm(tClaimType, tLCGetSchema.getGetDutyCode());

                //LMRiskSchema tLMRiskSchema = mLLClaimPubFunBL.getLMRisk(mRiskCode);

                //String tInsuredFlag = StrTool.cTrim(tLMRiskSchema.
                                                    //getInsuAccFlag()); //帐户标志
				
				String tInsuredFlag="";
				if(ttLMDutyGetClmSchema!=null)
				{
					tInsuredFlag= StrTool.cTrim(ttLMDutyGetClmSchema.getInpFlag());
				}
				
                if (tInsuredFlag.equals("3")) { //帐户型险种
                    String NeedAcc = "";
                    String Type = "";
                    String RiskPeriod = "";
                    String tGetDutyCode = tLCGetSchema.getGetDutyCode().trim();

                    String tSql =
                            "select needacc,type from lmdutyget where getdutycode = '" +
                            tLCGetSchema.getGetDutyCode() + "'";
//                    NeedAcc = StrTool.cTrim(tExeSQL.getOneValue(tSql));
                    SSRS tSSRS = tExeSQL.execSQL(tSql);
                    if (tExeSQL.mErrors.needDealError()) {
                        this.mErrors.copyAllErrors(tExeSQL.mErrors);
                        CError tError = new CError();
                        tError.moduleName = "LLClaimCalMatchBL";
                        tError.functionName = "getLLToClaimDuty";
                        tError.errorMessage = "得到lmdutyget发生错误!";
                        this.mErrors.addOneError(tError);
                    }
                    NeedAcc = tSSRS.GetText(1, 1);
                    Type    = tSSRS.GetText(1, 2);

                    String tSql2 = "select riskperiod from lmriskapp where riskcode = '"+mRiskCode+"'";
                    RiskPeriod = StrTool.cTrim(tExeSQL.getOneValue(tSql2));
                    if(RiskPeriod.equals("L")&&Type.equals("1")){
                        mRiskPeriod = "1"; //长险帐户结息表示
                    }
                    if (NeedAcc.equals("1")) { //对非帐户型责任的过滤
                        mLCGetSchema.setSchema(tLCGetSchema);
                        /*将数据赋值给全局变量*/
                    } else {
                        continue;
                    }
                }
                else if(getStopDate(tLCGetSchema.getGrpContNo())==false){//责任中止判断
                    continue;
                }
                else if(getGraceperiod(tLCGetSchema.getPolNo())==false){//宽限期判断
                    continue;
                }
                else{
                    mLCGetSchema.setSchema(tLCGetSchema); /*将数据赋值给全局变量*/
                }

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No5.2 得到保单的全局信息
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                if (!getLCPol(tLCGetSchema)) {
                    return false;
                }

                //得到责任的全局信息
                if (!getLCDuty(tLCGetSchema)) {
                    return false;
                }
//不需要输出太多的语句在一定程度上影响速度 2003-03-27 P.D
//                tMssage = "-----" + j + "/" + mLCGetSet.size() + "保项,合同号:[" +
//                          tLCGetSchema.getContNo() + "],险种号:[" +
//                          tLCGetSchema.getPolNo() + "],责任:[" +
//                          tLCGetSchema.getDutyCode() + "],责任编码:[" +
//                          tLCGetSchema.getGetDutyCode() + "],投保标志:[" +
//                          tLCGetSchema.getState() + "];取自:[" +
//                          tLCGetSchema.getOperator() + "]表.";
//                String tPrint = "合同号:[" + tLCGetSchema.getContNo() + "],险种号:[" +
//                                tLCGetSchema.getPolNo() + "]";

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No6.1 判断LCGet表的责任终止标志,GetEndState = 0 有效,GetEndState = 1终止
                 *
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                String tGetEndState = StrTool.cTrim(tLCGetSchema.getGetEndState());
                if (tGetEndState.equals("1")) {
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//                    logger.debug("-----保险责任被终止,将被过滤掉-----\n" + tMssage);
//                    logger.debug("--------------------------------------------------------------------------------------------------");

                    /*mErrors.addOneError("保险责任被终止,将被过滤掉!"+tMssage);*/
                    continue;
                }

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No6.2 判断合同是否被挂起
                 * 2003-03-27 P.D 注释
                 * 泰康没有保单挂起功能
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
//                LCContHangUpStateDB tLCContHangUpStateDB = new
//                        LCContHangUpStateDB();
//                tLCContHangUpStateDB.setContNo(tLCGetSchema.getContNo());
//                tLCContHangUpStateDB.setClaimFlag("1");
//                LCContHangUpStateSet tLCContHangUpStateSet =
//                        tLCContHangUpStateDB.query();
//
//                if (tLCContHangUpStateDB.mErrors.needDealError()) {
//                    // @@错误处理
//                    this.mErrors.copyAllErrors(tLCContHangUpStateDB.mErrors);
//                    CError tError = new CError();
//                    tError.moduleName = "LLClaimCalMatchBL";
//                    tError.functionName = "getLLAppClaimReason";
//                    tError.errorMessage = "合同号:[" + tLCGetSchema.getContNo() +
//                                          "]的查询出现问题,不能进行理赔计算!";
//                    this.mErrors.addOneError(tError);
//                    return false;
//                }
//
//                if (tLCContHangUpStateSet.size() > 0) {
//
//                    String tMess_HangUp = "";
//                    LCContHangUpStateSchema tLCContHangUpStateSchema =
//                            tLCContHangUpStateSet.get(1);
//                    if (StrTool.cTrim(tLCContHangUpStateSet.get(1).
//                                      getStandFlag2()).length() > 0) {
//                        tMess_HangUp = "[保全]";
//                    }
//
//                    if (StrTool.cTrim(tLCContHangUpStateSet.get(1).
//                                      getStandFlag5()).length() > 0) {
//                        tMess_HangUp = tMess_HangUp + "[续期]";
//                    }
//
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//                    logger.debug("-----合同被" + tMess_HangUp +
//                                       "挂起,理赔理算终止-----\n" + tMssage);
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//
//                    // @@错误处理
//                    this.mErrors.copyAllErrors(tLCContHangUpStateDB.mErrors);
//                    CError tError = new CError();
//                    tError.moduleName = "LLClaimCalMatchBL";
//                    tError.functionName = "getLLAppClaimReason";
//                    tError.errorMessage = "合同被" + tMess_HangUp + "挂起,匹配理算终止!" + tPrint;
//                    this.mErrors.addOneError(tError);
//                    return false;
//                }

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No6.3 LCContState表的终止标志如果为"1",代表该合同现被终止掉,不再匹配
                 * Terminate:1为终止
                 * 2003-03-27 P.D 注释
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */

//                String tSql = "select * from LCContState where 1=1 "
//                              + " and ContNo = '" + mLCGetSchema.getContNo() +
//                              "'"
//                              + " and PolNo = '" + mLCGetSchema.getPolNo() +
//                              "'"
//                              + " and StateType = 'Terminate'"
//                              + " and State = '1'"
//                              + " and lccontstate.StartDate <= to_date('" +
//                              this.mAccDate + "','yyyy-mm-dd') "
//                              + " and (lccontstate.enddate  >= to_date('" +
//                              this.mAccDate +
//                        "','yyyy-mm-dd') or lccontstate.enddate is null )"
//                              ;
//                LCContStateDB tLCContStateDB = new LCContStateDB();
//                LCContStateSet tLCContStateSet = tLCContStateDB.executeQuery(
//                        tSql);
//                if (tLCContStateDB.mErrors.needDealError()) {
//                    this.mErrors.copyAllErrors(tLCContStateDB.mErrors);
//                    CError tError = new CError();
//                    tError.moduleName = "LLClaimCalAutoBL";
//                    tError.functionName = "getAddYearBonus";
//                    tError.errorMessage = "得到合同终止状态失败!";
//                    this.mErrors.addOneError(tError);
//                    return false;
//                }
//
//                if (tLCContStateSet.size() > 0) {
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//                    logger.debug("-----合同为状态终止,将被过滤掉-----\n" + tMssage);
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//
//                    /*mErrors.addOneError("合同状态为终止,将被过滤掉!"+tMssage);*/
//                    continue;
//                }
             //liuyu 2007-4-25 针对108险种医疗赔付时不需要保险期间的限制
             String t108sql = "select riskcode from llregister where rgtno='"+mLLRegisterSchema.getRgtNo()+"' ";
             //ExeSQL tExeSQL= new  ExeSQL();
             String triskcode=tExeSQL.getOneValue(t108sql);
             String tflag="";
             String tSqlget="";
             if (triskcode=="108" || triskcode.equals("108"))
             {
                 tflag="0";
		 String justsql="select reasoncode from LLAppClaimReason where rgtno='"+mLLRegisterSchema.getRgtNo()+"'"
				+" and customerno='"+mLLCaseSchema.getCustomerNo()+"'";
		 SSRS ssrs=new SSRS();
		 ssrs=tExeSQL.execSQL(justsql);
		 int justrow=ssrs.getMaxRow();
		 for(int index=1;index<=justrow;index++)
		 {
		     if(!ssrs.GetText(index,1).equals("100")&&!ssrs.GetText(index,1).equals("200"))
		     {
			 tflag="1";
		     }
		 }

             }else if (triskcode!="108" || !triskcode.equals("108")||triskcode==null ||triskcode.equals(""))
             {
                 tflag="1";
             }


                if (tflag!="0" || !tflag.equals("0"))
                {
                	
                // 因出险日期与医疗出险日期相同，因此使用this.mAccDate（LLCase.AccDate）
                    tSqlget = "select * from LCGet where 1=1 "
                                    + " and PolNo = '" + mLCGetSchema.getPolNo()
                                    + "' and DutyCode='" + mLCGetSchema.getDutyCode()
                                    + "' and GetDutyCode='" + mLCGetSchema.getGetDutyCode()
                                    + "' and GetStartDate<=to_date('" + this.mAccDate + "')"
                                    + " and GetEndDate>=to_date('" + this.mAccDate + "')";

                }else
               { //jixf 20051214 泰康这暂时不用lccontstate的限制，所以要用责任去限制出险日期
                   tSqlget = "select * from LCGet where 1=1 "
                                   + " and PolNo = '" + mLCGetSchema.getPolNo()
                                   + "' and DutyCode='" + mLCGetSchema.getDutyCode()
                                   + "' and GetDutyCode='" + mLCGetSchema.getGetDutyCode()+"' ";

               }
                logger.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+tSqlget);
                LCGetDB tLCGetDB = new LCGetDB();
                LCGetSet tLCGetSet = tLCGetDB.executeQuery(tSqlget);
                if (tLCGetSet.size() == 0)
                {
                   logger.debug("保单责任不在有效期内");
                    //判断保单责任是否在有效期内
                    continue;
                }
                else
                {
                    String tSqlzt = "select * from LPEdorItem  where 1=1 "
                            + " and PolNo ='" + mLCGetSchema.getPolNo() + "'"
                            + " and EdorState='0'"
                            + "' and EdorType='ZT' and EdorValidate<to_date('"
                            +this.mAccDate+  "')";
                    //logger.debug("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+tSqlzt);
                    LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
                    LPEdorItemSet tLPEdorItemSet = tLPEdorItemDB.executeQuery(tSqlzt);
                    if (tLPEdorItemSet.size() > 0)
                    {
                        logger.debug("保单责任不在有效期内");
                        //判断保单责任是否在有效期内
                        continue;
                    }

                }


                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No6.4 LCContState表的终止标志如果为"1",代表该合同现被终止掉,不再匹配
                 * Terminate:1为终止
                 * 对于出险时点后被理赔终止的保单,只判断是否是主险
                 * 2003-03-27 P.D 注释
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
//                String tRiskCode = mLCPolSchema.getRiskCode();
//                LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL.
//                        getLMRiskApp(tRiskCode);
//                if (tLMRiskAppSchema == null) {
//                    this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
//                    return false;
//                }
//                String tSubRiskFlag = StrTool.cTrim(tLMRiskAppSchema.
//                        getSubRiskFlag()); //主附险标志
//
//                if (tSubRiskFlag.equals("M")) {
//                    tSql = "select * from LCContState where 1=1 "
//                           + " and ContNo = '" + mLCGetSchema.getContNo() + "'"
//                           + " and PolNo = '" + mLCGetSchema.getPolNo() + "'"
//                           + " and StateType = 'Terminate'"
//                           + " and State = '1'"
//                           + " and StateReason ='04'"
//                           + " and lccontstate.StartDate >= to_date('" +
//                           this.mAccDate + "','yyyy-mm-dd') "
//                           ;
//                    LCContStateDB tLPLCContStateDB = new LCContStateDB();
//                    LCContStateSet tLPLCContStateSet = tLPLCContStateDB.
//                            executeQuery(tSql);
//                    if (tLPLCContStateDB.mErrors.needDealError()) {
//                        this.mErrors.copyAllErrors(tLCContStateDB.mErrors);
//                        CError tError = new CError();
//                        tError.moduleName = "LLClaimCalAutoBL";
//                        tError.functionName = "getAddYearBonus";
//                        tError.errorMessage = "得到合同终止状态失败!";
//                        this.mErrors.addOneError(tError);
//                        return false;
//                    }
//
//                    if (tLPLCContStateSet.size() > 0) {
//                        logger.debug("--------------------------------------------------------------------------------------------------");
//                        logger.debug(
//                                "-----出险时点在[理赔合同状态终止]之前,将被过滤掉-----\n" + tMssage);
//                        logger.debug("--------------------------------------------------------------------------------------------------");
//
//                        /*mErrors.addOneError("合同号["+tLPLCContStateSet.get(1).getContNo()+"],保单险种号["+tLPLCContStateSet.get(1).getPolNo()+"],出险时点在[理赔合同终止"+tLPLCContStateSet.get(1).getStartDate()+"]之前,将不参与理算!");*/
//                        continue;
//                    }
//                }

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No6.5 LCContState表的有效标志如果为"1",代表该合同现已失效,不再匹配
                 * Available: 0-有效 1-失效 （险种状态）
                 * 2003-03-27 P.D 注释
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */

//                tSql = "select * from LCContState where 1=1 "
//                       + " and ContNo = '" + mLCGetSchema.getContNo() + "'"
//                       + " and PolNo = '" + mLCGetSchema.getPolNo() + "'"
//                       + " and StateType = 'Available'"
//                       + " and State = '1'"
//                       + " and lccontstate.StartDate <= to_date('" +
//                       this.mAccDate + "','yyyy-mm-dd') "
//                       + " and (lccontstate.enddate  >= to_date('" +
//                       this.mAccDate +
//                       "','yyyy-mm-dd') or lccontstate.enddate is null )"
//                       ;
//
//                tLCContStateDB = new LCContStateDB();
//                tLCContStateSet = tLCContStateDB.executeQuery(tSql);
//                if (tLCContStateDB.mErrors.needDealError()) {
//                    this.mErrors.copyAllErrors(tLCContStateDB.mErrors);
//                    CError tError = new CError();
//                    tError.moduleName = "LLClaimCalAutoBL";
//                    tError.functionName = "getAddYearBonus";
//                    tError.errorMessage = "得到合同有效失效状态失败!";
//                    this.mErrors.addOneError(tError);
//                    return false;
//                }
//
//                if (tLCContStateSet.size() > 0) {
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//                    logger.debug("-----合同状态为失效,将被过滤掉-----\n" + tMssage);
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//
//                    /*mErrors.addOneError("合同状态为失效,将被过滤掉!"+tMssage);*/
//                    continue;
//                }

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No6.6 LCGet表的该字段属于借用，保单类型,C表保单，B表保单,A承保出单前出险
                 *  承保出险,出险原因为疾病,不匹配
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                String tPolSort = tLCGetSchema.getOperator();
                String tSql = "";
                if (tPolSort.equals("A") 
                		//&&tClaimType.substring(0, 1).equals("2")
                		) 
                {
//                    logger.debug("--------------------------------------------------------------------------------------------------");
//                    logger.debug("-----承保前疾病出险,将被过滤掉-----\n" + tMssage);
//                    logger.debug("--------------------------------------------------------------------------------------------------");

                    /*mErrors.addOneError("承保前疾病出险,将被过滤掉!"+tMssage);*/
                    continue;
                }

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No7.1 进行产品的匹配校验工作
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                //给付责任编码
                tSql = "select * from lmdutygetclm where 1 =1 "
                       + " and getdutycode ='" + tLCGetSchema.getGetDutyCode() + "'"
                       + " and getdutykind ='" + tClaimType + "' ";

                logger.debug("----------------------------------------------------");
                String tMssageSql = tMssage + "\n----" + tSql;
                logger.debug("--匹配前:" + tMssageSql);

                LMDutyGetClmSet tLMDutyGetClmSet = new LMDutyGetClmDB().
                        executeQuery(tSql);

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No7.2 循环符合匹配条件LMDutyGetClm责任给付赔付信息,
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                logger.debug("---符合理赔给付责任的条数为：["+tLMDutyGetClmSet.size()+"]条---");
                for (int m = 1; m <= tLMDutyGetClmSet.size(); m++) {

                    LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmSet.
                            get(m);

                    String tContNo = tLCGetSchema.getContNo();
                    this.mNBPolNo = getNBPolNo(this.mLCPolSchema); //得到承保时的保单号

                    mLPEdorItemSchema = mLLClaimPubFunBL.getLPEdorItemAfter(
                            tContNo, mNBPolNo, this.mInsDate, null); //得到保全的批单号

//                    logger.debug("--过滤前:" + tMssageSql);

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No8.1
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    //将GetDutyKind【理赔类型】设置到LCGet表中
                    tLCGetSchema.setGetDutyKind(tClaimType);

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No8.3 执行过滤算法getFilterCalCode()和getFilterMatch()
                     *  如果没有通过，则不符合匹配条件,继续循环
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    String tCalCode = tLMDutyGetClmSchema.getFilterCalCode();
                    if (!getFilterCalCode(tCalCode)) {
                        logger.debug("--------------------------------------------------------------------------------------------------");
                        logger.debug("-----保险责任不符合产品定义的过滤条件,将被过滤掉-----\n" +
                                           tMssage);
                        logger.debug("--------------------------------------------------------------------------------------------------");

                        /*mErrors.addOneError("保险责任不符合产品定义条件,将被过滤掉!"+tMssage);*/
                        continue;
                    }

                    if (!getFilterMatch(tLMDutyGetClmSchema, tLCGetSchema)) {
                        logger.debug("--------------------------------------------------------------------------------------------------");
                        logger.debug( "-----保险责任进行被保人,连带被保险人判断时不符合条件,将被过滤掉-----\n" + tMssage);
                        logger.debug("--------------------------------------------------------------------------------------------------");

                        /*mErrors.addOneError("保险责任进行投保被保人判断时不符合条件,将被过滤掉!"+tMssage);*/
                        continue;
                    }
                    
                    
                    //03表示连带被保险人,由于MS是连带被保险人附属在主险的polno上,且共享一套getdutycode,所以不能过滤掉重复责任
                    if(!tLCGetSchema.getState().equals("03"))
                    {
                        if (!getFilterGetDutyCode(tLCGetSchema, mLLToClaimDutySet)) {
                            logger.debug("--------------------------------------------------------------------------------------------------");
                            logger.debug("-----两个出险人的保险责任相同,将被过滤掉一个责任-----\n" +
                                               tMssage);
                            logger.debug("--------------------------------------------------------------------------------------------------");

                            /*mErrors.addOneError("保险责任进行投保被保人判断时不符合条件,将被过滤掉!"+tMssage);*/
                            continue;
                        }
                    }

        

                    logger.debug("--------------------------------------------------------------------------------------------------");
                    logger.debug("--匹配后:" + tMssageSql);
                    logger.debug("--------------------------------------------------------------------------------------------------");

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     *  No8.5 设置LLToClaimDuty信息
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */

                    if (!getLLToClaimDuty(tLCGetSchema, tLMDutyGetClmSchema)) {
                        return false;
                    }

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No8.6 判断是否是加保记录,如果是加保的数据,不进行后续的费用数据提取计算
                     *  因为加保的DutyCode为8为,正常的DutyCode的为6位
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    String tDutyCode = StrTool.cTrim(tLCGetSchema.getDutyCode());
                    if (tDutyCode.length() == 8) {
                        continue;
                    }

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     *  No8.7 设置LLToClaimDutyFee信息
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    if (!getLLToClaimDutyFee(tClaimType,tLCGetSchema.getInsuredNo())) {
                        return false;
                    }
                    
        

                } //for--tLMDutyGetClmSet结束

            } //for--mLCGetSet结束

            logger.debug("================结束========执行理赔类型为========[" +
                               tClaimType +
                               "]=====的匹配算法===============================");
        } //理赔类型循环结束

        return true;
    }


    /**
     * 目的：获取理赔类型信息
     * @return
     */
    private boolean getLLAppClaimReason() {
        LLAppClaimReasonDB tLLAppClaimReasonDB = new LLAppClaimReasonDB();
        tLLAppClaimReasonDB.setCaseNo(this.mClmNo); //赔案号
        tLLAppClaimReasonDB.setCustomerNo(mLLCaseSchema.getCustomerNo()); //客户号

        mLLAppClaimReasonSet = tLLAppClaimReasonDB.query();
        if (mLLAppClaimReasonSet == null || mLLAppClaimReasonSet.size() == 0) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLAppClaimReasonDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getLLAppClaimReason";
            tError.errorMessage = "在LLAppClaimReason表中没有找到到理赔类型，不能理算!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 进行保单挂起的校验
     * @param pContNo
     * @return
     */
    private boolean getLCContHangUpState(String pContNo) {
        LCContHangUpStateDB tLCContHangUpStateDB = new LCContHangUpStateDB();
        tLCContHangUpStateDB.setContNo(pContNo);
        tLCContHangUpStateDB.setClaimFlag("1");
        LCContHangUpStateSet tLCContHangUpStateSet = tLCContHangUpStateDB.query();

        if (tLCContHangUpStateSet.size() > 0) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCContHangUpStateDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getLLAppClaimReason";
            tError.errorMessage = "合同号:[" + pContNo + "]的保单已经被挂起,不能进行理赔计算!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }


    /**
     * 获取出险人作为投保人和被保人的所有的给付责任信息
     *  从[领取项表]C表或轨迹表中取得给付项记录
     */
    public boolean getLCLBGet() {
        logger.debug("================开始========获取LCLBGet领取项信息==============================================");
        String tSql = "";
        String tSqlTemp = "";

        LCGetDB tLCGetDB = new LCGetDB();
        LCGetSet tLCGetSet = null;
        mLCGetSet.clear();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 承保出单前出险--对LCGet表--准备公用查询SQL
         *      LCGet表存放当期记录，LBGet表存放上期记录
         *  AppFlag=0[投保单],根据意外事故日期 >= 投保日期
         *  AppFlag=1[保单],  根据意外事故日期 >= 投保日期 and 保单生效对应日 > 意外事故日期
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.1 承保出单前出险--对LCGet表进行作为被保人的判断
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

//
//        tSql = "select lcget.* from lcget,lcpol where 1 = 1"
//               + " and lcget.polno=lcpol.polno "
//               + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
//               + " and lcpol.AppFlag  in ('0') " //投保单
//               + " and lcpol.PolApplyDate<=to_date('" + this.mAccDate +
//               "','yyyy-mm-dd') " //事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
//               + " and lcpol.insuredno  in ( '" + mLLCaseSchema.getCustomerNo() +
//               "')";
//        //单保单理赔----20051130-----P.D------
//        if (mLLRegisterSchema.getGrpContNo() != null &&
//            !mLLRegisterSchema.getGrpContNo().equals("")) {
//            tSql += " and lcpol.GrpContno = '" + mLLRegisterSchema.getGrpContNo() +
//                    "'";
//        }
//        //针对某险种里赔----20051130-----P.D------
//        if (mLLRegisterSchema.getRiskCode() != null &&
//            !mLLRegisterSchema.getRiskCode().equals("")) {
//            tSql += " and lcpol.RiskCode = '" + mLLRegisterSchema.getRiskCode() +
//                    "'";
//        }
//        tSql += " union "
//                + " select lcget.* from lcget,lcpol where 1 = 1"
//                + " and lcget.polno=lcpol.polno "
//                + " and lcpol.AppFlag  in ('1') " //保单
//                + " and RenewCount = 0 " //续保次数为0,代表没有续保过
//                + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
//                + " and lcpol.PolApplyDate<=to_date('" + this.mAccDate +
//                "','yyyy-mm-dd') " //事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
//                + " and lcpol.CValiDate   > to_date('" + this.mAccDate +
//                "','yyyy-mm-dd') "
//                + " and lcpol.insuredno  in ( '" + mLLCaseSchema.getCustomerNo() +
//                "')";
//        //单保单理赔----20051130-----P.D------
//        if (mLLRegisterSchema.getGrpContNo() != null &&
//            !mLLRegisterSchema.getGrpContNo().equals("")) {
//            tSql += " and lcpol.GrpContno = '" + mLLRegisterSchema.getGrpContNo() +
//                    "'";
//        }
//        //针对某险种里赔----20051130-----P.D------
//        if (mLLRegisterSchema.getRiskCode() != null &&
//            !mLLRegisterSchema.getRiskCode().equals("")) {
//            tSql += " and lcpol.RiskCode = '" + mLLRegisterSchema.getRiskCode() +
//                    "'";
//        }
//
//        logger.debug("-----承保出单前出险--getLCGet的被保人Sql语句-----" + tSql);
//        tLCGetSet = tLCGetDB.executeQuery(tSql);
//
//        if (tLCGetDB.mErrors.needDealError()) {
//            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalAutoBL";
//            tError.functionName = "getPolPay";
//            tError.errorMessage = "承保出单前出险发生错误!";
//            logger.debug(
//                    "------------------------------------------------------");
//            logger.debug("--LLClaimCalMatchBL.getLCLBGet()--承保出单前出险发生错误!" +
//                               tSql);
//            logger.debug(
//                    "------------------------------------------------------");
//            this.mErrors.addOneError(tError);
//            return false;
//        }
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * No1.2 承保出单前出险--对LCGet表进行作为被保人的判断
//         *  给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//        for (int i = 1; i <= tLCGetSet.size(); i++) {
//            LCGetSchema tLCGetSchema = tLCGetSet.get(i);
//            tLCGetSchema.setState("01"); //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
//            tLCGetSchema.setOperator("A"); //LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
//            tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo()); //LCGet表的该字段属于借用，用于存放出险人编号
//            mLCGetSet.add(tLCGetSchema);
//        }
//
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * No1.3 承保出单前出险--对LCGet表进行作为投保人的判断
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//        tSql = "select lcget.* from lcget,lcpol where 1 = 1"
//               + " and lcget.polno=lcpol.polno "
//               + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
//               + " and lcpol.AppFlag  in ('0') " //投保单
//               + " and lcpol.PolApplyDate<=to_date('" + this.mAccDate +
//               "','yyyy-mm-dd') " //事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
//               + " and lcpol.AppntNo  in ( '" + mLLCaseSchema.getCustomerNo() +
//               "')";
//        //单保单理赔----20051130-----P.D------
//        if (mLLRegisterSchema.getGrpContNo() != null &&
//            !mLLRegisterSchema.getGrpContNo().equals("")) {
//            tSql += " and lcpol.GrpContno = '" + mLLRegisterSchema.getGrpContNo() +
//                    "'";
//        }
//        //针对某险种里赔----20051130-----P.D------
//        if (mLLRegisterSchema.getRiskCode() != null &&
//            !mLLRegisterSchema.getRiskCode().equals("")) {
//            tSql += " and lcpol.RiskCode = '" + mLLRegisterSchema.getRiskCode() +
//                    "'";
//        }
//        tSql += " union "
//                + " select lcget.* from lcget,lcpol where 1 = 1"
//                + " and lcget.polno=lcpol.polno "
//                + " and lcpol.AppFlag  in ('1') " //保单
//                + " and RenewCount = 0 " //续保次数为0,代表没有续保过
//                + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
//                + " and lcpol.PolApplyDate<=to_date('" + this.mAccDate +
//                "','yyyy-mm-dd') " //事故日期必须在投保日期和生效日期之间，这样可以找出收费后承保前出险的保项
//                + " and lcpol.CValiDate   > to_date('" + this.mAccDate +
//                "','yyyy-mm-dd') "
//                + " and lcpol.AppntNo  in ( '" + mLLCaseSchema.getCustomerNo() +
//                "')";
//        //单保单理赔----20051130-----P.D------
//        if (mLLRegisterSchema.getGrpContNo() != null &&
//            !mLLRegisterSchema.getGrpContNo().equals("")) {
//            tSql += " and lcpol.GrpContno = '" + mLLRegisterSchema.getGrpContNo() +
//                    "'";
//        }
//        //针对某险种里赔----20051130-----P.D------
//        if (mLLRegisterSchema.getRiskCode() != null &&
//            !mLLRegisterSchema.getRiskCode().equals("")) {
//            tSql += " and lcpol.RiskCode = '" + mLLRegisterSchema.getRiskCode() +
//                    "'";
//        }
//
//        logger.debug("-----承保出单前出险--getLCGet的投保人Sql语句-----" + tSql);
//        tLCGetSet = tLCGetDB.executeQuery(tSql);
//
//        if (tLCGetDB.mErrors.needDealError()) {
//            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalAutoBL";
//            tError.functionName = "getPolPay";
//            tError.errorMessage = "承保出单前出险发生错误!";
//            logger.debug(
//                    "------------------------------------------------------");
//            logger.debug("--LLClaimCalMatchBL.getLCLBGet()--承保出单前出险发生错误!" +
//                               tSql);
//            logger.debug(
//                    "------------------------------------------------------");
//            this.mErrors.addOneError(tError);
//            return false;
//        }
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * No1.4 承保出单前出险--对LCGet表进行作为投保人的判断
//         * 给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//        for (int i = 1; i <= tLCGetSet.size(); i++) {
//            LCGetSchema tLCGetSchema = tLCGetSet.get(i);
//            tLCGetSchema.setState("00"); //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
//            tLCGetSchema.setOperator("A"); //LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
//            tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo()); //LCGet表的该字段属于借用，用于存放出险人编号
//            mLCGetSet.add(tLCGetSchema);
//        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 对LCGet表，准备公用查询SQL
         *  最大计算日期，也就是费用结束日期为空，认为是没有费用录入信息
         *  否则，认为有费用信息，则根据该日期进行判断
         *  此种情况针对于类似于238主险产品，跨期出险，生成了续期记录，
         *      LCGet表存放当期记录，LBGet表存放上期记录
         *      如果事故发生在上期,治疗时间在当期结束,也要把当期的保项匹配出来,
         *      因为当期也要赔付一部分
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        //liuyu 2007-4-25 针对108险种医疗赔付时不需要保险期间的限制

        String t108sql = "select riskcode from llregister where rgtno='"+mLLRegisterSchema.getRgtNo()+"' ";
        ExeSQL tExeSQL= new  ExeSQL();
        SSRS tSSRS=new SSRS();
        String triskcode=tExeSQL.getOneValue(t108sql);
        String tflag="";
        logger.debug(triskcode);
        if (triskcode=="108" || triskcode.equals("108"))
        {
	    tflag="0";//108变态险种
	    String justsql="select reasoncode from LLAppClaimReason where rgtno='"+mLLRegisterSchema.getRgtNo()+"'"
	    +" and customerno='"+mLLCaseSchema.getCustomerNo()+"'";
	    SSRS ssrs=new SSRS();
	    ssrs=tExeSQL.execSQL(justsql);
	    int justrow=ssrs.getMaxRow();
	    for(int i=1;i<=justrow;i++)
	    {
		if(!ssrs.GetText(i,1).equals("100")&&!ssrs.GetText(i,1).equals("200"))
		{
		    tflag="1";
		}
	    }

        }else if (triskcode!="108" || !triskcode.equals("108")||triskcode==null ||triskcode.equals(""))
        {
            tflag="1";//非108险种
        }
  //针对保险期间中断和恢复的保全BS(中断)和BR(恢复)，添加出险日期不能在这段区间的限制。---liuyu--2008-1-10
  String JgSql="select count(*) from lcgrpcontstopedorstate where grpcontno='"+mLLRegisterSchema.getGrpContNo()+"' and stopdate<>restartdate and stopdate <= to_date('" + this.mInsDate + "','yyyy-mm-dd') and restartdate > to_date('" + this.mInsDate + "','yyyy-mm-dd')  ";
  if (mLLRegisterSchema.getRiskCode() != null &&
    !mLLRegisterSchema.getRiskCode().equals("")) {
    JgSql += " and RiskCode = '" +
            mLLRegisterSchema.getRiskCode() + "'";
  }
  String tJgSqlCount=tExeSQL.getOneValue(JgSql);

  if ((tflag!="0" || !tflag.equals("0"))&&(tJgSqlCount=="0" || tJgSqlCount.equals("0")))
   {
//       String PSql="select count(*) from lcpol where grpcontno='"+mLLRegisterSchema.getGrpContNo()+"' and insuredno='"+mLLCaseSchema.getCustomerNo()+"' and lastedordate is not null ";
//       if (mLLRegisterSchema.getRiskCode() != null &&
//           !mLLRegisterSchema.getRiskCode().equals("")) {
//           PSql += " and RiskCode = '" +
//                   mLLRegisterSchema.getRiskCode() + "'";
//        }
//       int pcount=Integer.parseInt(tExeSQL.getOneValue(PSql));
//       if (pcount!=0)
//       {
//           tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
//                         + " and lcget.polno=lcpol.polno "
//                         + " and lcget.LiveGetType = '1' " //生存意外给付标志,1意外
//                         + " and lcpol.AppFlag  in ('1','4')" //0-投保单,1-保单
//                         + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
//                         + " and lcpol.cvalidate<=to_date('" + this.mInsDate +"','yyyy-mm-dd') "
//                         + " and lcpol.lastedordate  >= to_date('" + this.mInsDate + "','yyyy-mm-dd') ";
//              //单保单理赔----20051130-----P.D------
//              if (mLLRegisterSchema.getGrpContNo() != null &&
//                  !mLLRegisterSchema.getGrpContNo().equals("")) {
//                  tSqlTemp += " and lcpol.GrpContno = '" +
//                          mLLRegisterSchema.getGrpContNo() + "'";
//              }
//              //针对某险种里赔----20051130-----P.D------
//              if (mLLRegisterSchema.getRiskCode() != null &&
//                  !mLLRegisterSchema.getRiskCode().equals("")) {
//                  tSqlTemp += " and lcpol.RiskCode = '" +
//                          mLLRegisterSchema.getRiskCode() + "'";
//            }
//            tSql = tSqlTemp + " and lcpol.insuredno  in ('" +
//                   mLLCaseSchema.getCustomerNo() + "')"; //作为被保人
//            logger.debug("-----getLCGet的被保人Sql语句-----" + tSql);
//
//            tSSRS=tExeSQL.execSQL(tSql);
//            if (tSSRS.getMaxRow()<=0)
//            {
//                tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
//                              + " and lcget.polno=lcpol.polno "
//                              + " and lcget.LiveGetType = '1' " //生存意外给付标志,1意外
//                              + " and lcpol.AppFlag  in ('1','4')" //0-投保单,1-保单
//                              + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
//                              + " and lcget.getstartdate<=to_date('" + this.mInsDate +"','yyyy-mm-dd') "
//                              + " and lcget.getenddate  >= to_date('" + this.mInsDate + "','yyyy-mm-dd') "
//                              + " and lcpol.lastedordate is null ";
//
//
//
//                     //单保单理赔----20051130-----P.D------
//                     if (mLLRegisterSchema.getGrpContNo() != null &&
//                         !mLLRegisterSchema.getGrpContNo().equals("")) {
//                         tSqlTemp += " and lcpol.GrpContno = '" +
//                                 mLLRegisterSchema.getGrpContNo() + "'";
//                     }
//                     //针对某险种里赔----20051130-----P.D------
//                     if (mLLRegisterSchema.getRiskCode() != null &&
//                         !mLLRegisterSchema.getRiskCode().equals("")) {
//                         tSqlTemp += " and lcpol.RiskCode = '" +
//                                 mLLRegisterSchema.getRiskCode() + "'";
//                     }
//                     tSql = tSqlTemp + " and lcpol.insuredno  in ('" +
//                            mLLCaseSchema.getCustomerNo() + "')"; //作为被保人
//            logger.debug("-----getLCGet的被保人Sql语句-----" + tSql);
//            }
//
//        }
        //else
        //{
	  
	  //因出险日期与医疗出险日期相同，因此使用this.mAccDate（LLCase.AccDate）
            tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
                          + " and lcget.polno=lcpol.polno "
                          + " and lcget.LiveGetType = '1' " //生存意外给付标志,1意外
                          + " and lcpol.AppFlag  in ('1','4')" //0-投保单,1-保单
                          + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
                          + " and lcget.getstartdate<=to_date('" + this.mAccDate +"','yyyy-mm-dd') "
                         + " and lcget.getenddate  >= to_date('" + this.mAccDate + "','yyyy-mm-dd') ";



                 //单保单理赔----20051130-----P.D------
                 if (mLLRegisterSchema.getGrpContNo() != null &&
                     !mLLRegisterSchema.getGrpContNo().equals("")) {
                     tSqlTemp += " and lcpol.GrpContno = '" +
                             mLLRegisterSchema.getGrpContNo() + "'";
                 }
                 //针对某险种里赔----20051130-----P.D------
                 if (mLLRegisterSchema.getRiskCode() != null &&
                     !mLLRegisterSchema.getRiskCode().equals("")) {
                     tSqlTemp += " and lcpol.RiskCode = '" +
                             mLLRegisterSchema.getRiskCode() + "'";
                 }

//        } else {
//            tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
//                       + " and lcget.polno=lcpol.polno "
//                       + " and lcget.LiveGetType = '1' " //生存意外给付标志,1意外
//                       + " and lcpol.AppFlag  in ('1','4')" //0-投保单,1-保单
//                       + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
//                       + " and (  (lcget.getstartdate<=to_date('" +
//                       this.mAccDate +
//                       "','yyyy-mm-dd')    and lcget.getenddate > to_date('" +
//                       this.mAccDate + "','yyyy-mm-dd') ) "
//                       + " or (lcget.getstartdate<=to_date('" +
//                       this.mMaxCalDate +
//                       "','yyyy-mm-dd') and lcget.getenddate > to_date('" +
//                       this.mMaxCalDate +
//                       "','yyyy-mm-dd') and length(trim(lcget.DutyCode))=6 ) )"
//                       + " and to_date('" + this.mAccDate +
//                       "','yyyy-mm-dd')<=to_date('" + this.mMaxCalDate +
//                       "','yyyy-mm-dd')";
//            //单保单理赔----20051130-----P.D------
//            if (mLLRegisterSchema.getGrpContNo() != null &&
//                !mLLRegisterSchema.getGrpContNo().equals("")) {
//                tSqlTemp += " and lcpol.GrpContno = '" +
//                        mLLRegisterSchema.getGrpContNo() + "'";
//            }
//            //针对某险种里赔----20051130-----P.D------
//            if (mLLRegisterSchema.getRiskCode() != null &&
//                !mLLRegisterSchema.getRiskCode().equals("")) {
//                tSqlTemp += " and lcpol.RiskCode = '" +
//                        mLLRegisterSchema.getRiskCode() + "'";
//            }
//        }

             /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              * No2.1 对LCGet表进行作为被保人的判断
              * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
              */
             tSql = tSqlTemp + " and lcpol.insuredno  in ('" +
                    mLLCaseSchema.getCustomerNo() + "')"; //作为被保人
        logger.debug("-----getLCGet的被保人Sql语句-----" + tSql);
        //}

  }
  else if (tflag.equals("0"))
  {
      tSqlTemp = "select lcget.* from lcget,lcpol where 1 = 1"
                    + " and lcget.polno=lcpol.polno "
                    + " and lcget.LiveGetType = '1' " //生存意外给付标志,1意外
                    + " and lcpol.AppFlag  in ('1','4')" //0-投保单,1-保单
                    + " and lcpol.conttype  = '" + this.mContType + "'" ;  //总单类型,1-个人投保单,2-集体总投保单


            //单保单理赔----20051130-----P.D------
            if (mLLRegisterSchema.getGrpContNo() != null &&
                !mLLRegisterSchema.getGrpContNo().equals("")) {
                tSqlTemp += " and lcpol.GrpContno = '" +
                        mLLRegisterSchema.getGrpContNo() + "'";
            }
            //针对某险种里赔----20051130-----P.D------
            if (mLLRegisterSchema.getRiskCode() != null &&
                !mLLRegisterSchema.getRiskCode().equals("")) {
                tSqlTemp += " and lcpol.RiskCode = '" +
                        mLLRegisterSchema.getRiskCode() + "'";
            }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.1 对LCGet表进行作为被保人的判断
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = tSqlTemp + " and lcpol.insuredno  in ('" +
               mLLCaseSchema.getCustomerNo() + "')"; //作为被保人
        logger.debug("-----getLCGet的被保人Sql语句-----" + tSql);

  }else if (tJgSqlCount!="0" || !tJgSqlCount.equals("0"))
  {
      logger.debug("中断期间的JgSql---："+JgSql);
      logger.debug("出险日期是否在中断期间内："+tJgSqlCount+" 条");
      return true;
  }
        tLCGetDB = new LCGetDB();
        tLCGetSet = tLCGetDB.executeQuery(tSql);


        if (tLCGetDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "匹配发生错误!";
            this.mErrors.addOneError(tError);
            logger.debug(
                    "------------------------------------------------------");
            logger.debug("--LLClaimCalMatchBL.getLCLBGet()--匹配发生错误!" +
                               tSql);
            logger.debug(
                    "------------------------------------------------------");
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.2 对LCGet表进行作为被保人的判断
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        logger.debug("******************"+tLCGetSet.size());
        for (int i = 1; i <= tLCGetSet.size(); i++) {

            LCGetSchema tLCGetSchema = tLCGetSet.get(i);
            tLCGetSchema.setState("01"); //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
            tLCGetSchema.setOperator("C"); //LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
            tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo()); //LCGet表的该字段属于借用，用于存放出险人编号
            mLCGetSet.add(tLCGetSchema);

        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.2 对LCGet表进行作为投保人的判断
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－

        tSql = tSqlTemp + " and lcpol.AppntNo in ('" +
               mLLCaseSchema.getCustomerNo() + "')"; //作为投保人
        logger.debug("-----getLCGet的投保人Sql语句-----" + tSql);
        tLCGetDB = new LCGetDB();
        tLCGetSet = tLCGetDB.executeQuery(tSql);
        if (tLCGetDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "发生错误!";
            this.mErrors.addOneError(tError);
            logger.debug(
                    "------------------------------------------------------");
            logger.debug("--LLClaimCalMatchBL.getLCLBGet()--匹配发生错误!" +
                               tSql);
            logger.debug(
                    "------------------------------------------------------");
            return false;
        }
*/
        /*给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人*/
        /*
        for (int i = 1; i <= tLCGetSet.size(); i++) {
            LCGetSchema tLCGetSchema = tLCGetSet.get(i);
            tLCGetSchema.setState("00"); //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
            tLCGetSchema.setOperator("C"); //LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
            tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo()); //LCGet表的该字段属于借用，用于存放出险人编号
            mLCGetSet.add(tLCGetSchema);
        }
        */
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 判断出险人在LCInsuredRelated连带被保人表中是否有数据
         *  用于144联生险的情况
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        if (this.mMaxCalDate == null) {
            tSql =
                    "select lcget.* from lcget,lcpol,LCInsuredRelated where 1 = 1"
                    + " and lcget.polno=lcpol.polno "
                    + " and lcget.polno=LCInsuredRelated.polno "
                    + " and lcget.LiveGetType = '1' " //生存意外给付标志,1意外
                    + " and lcpol.AppFlag  in ('1','4')" //0-投保单,1-保单
                    + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
                    + " and lcget.getstartdate<=to_date('" + this.mInsDate +
                    "','yyyy-mm-dd') "
                    + " and lcget.getenddate  > to_date('" + this.mInsDate +
                    "','yyyy-mm-dd') "
                    + " and LCInsuredRelated.CustomerNo = '" +
                    mLLCaseSchema.getCustomerNo() + "'";
            //单保单理赔----20051130-----P.D------
            if (mLLRegisterSchema.getGrpContNo() != null &&
                !mLLRegisterSchema.getGrpContNo().equals("")) {
                tSql += " and lcpol.GrpContno = '" +
                        mLLRegisterSchema.getGrpContNo() + "'";
            }
            //针对某险种里赔----20051130-----P.D------
            if (mLLRegisterSchema.getRiskCode() != null &&
                !mLLRegisterSchema.getRiskCode().equals("")) {
                tSql += " and lcpol.RiskCode = '" +
                        mLLRegisterSchema.getRiskCode() + "'";
            }
        } else {
            tSql =
                    "select lcget.* from lcget,lcpol,LCInsuredRelated where 1 = 1"
                    + " and lcget.polno=lcpol.polno "
                    + " and lcget.polno=LCInsuredRelated.polno "
                    + " and lcget.LiveGetType = '1' " //生存意外给付标志,1意外
                    + " and lcpol.AppFlag  in ('1','4')" //0-投保单,1-保单
                    + " and lcpol.conttype  = '" + this.mContType + "'" //总单类型,1-个人投保单,2-集体总投保单
                    + " and (  (lcget.getstartdate<=to_date('" + this.mInsDate +
                    "','yyyy-mm-dd')    and lcget.getenddate > to_date('" +
                    this.mInsDate + "','yyyy-mm-dd') ) "
                    + " or (lcget.getstartdate<=to_date('" + this.mMaxCalDate +
                    "','yyyy-mm-dd') and lcget.getenddate > to_date('" +
                    this.mMaxCalDate + "','yyyy-mm-dd') ) )"
                    + " and to_date('" + this.mInsDate +
                    "','yyyy-mm-dd')<=to_date('" + this.mMaxCalDate +
                    "','yyyy-mm-dd')"
                    + " and LCInsuredRelated.CustomerNo = '" +
                    mLLCaseSchema.getCustomerNo() + "'";
            //单保单理赔----20051130-----P.D------
            if (mLLRegisterSchema.getGrpContNo() != null &&
                !mLLRegisterSchema.getGrpContNo().equals("")) {
                tSql += " and lcpol.GrpContno = '" +
                        mLLRegisterSchema.getGrpContNo() + "'";
            }
            //针对某险种里赔----20051130-----P.D------
            if (mLLRegisterSchema.getRiskCode() != null &&
                !mLLRegisterSchema.getRiskCode().equals("")) {
                tSql += " and lcpol.RiskCode = '" +
                        mLLRegisterSchema.getRiskCode() + "'";
            }
        }

        logger.debug("-----连带被保人的Sql语句-----" + tSql);
        tLCGetDB = new LCGetDB();
        tLCGetSet = tLCGetDB.executeQuery(tSql);
        if (tLCGetDB.mErrors.needDealError()) {
            CError.buildErr(this, "查询给付责任发生错误！");
            logger.debug(
                    "------------------------------------------------------");
            logger.debug("--LLClaimCalMatchBL.getLCLBGet()--匹配发生错误!" +
                               tSql);
            logger.debug(
                    "------------------------------------------------------");
            return false;
        }

        /*给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人*/
        logger.debug("=============get.size"+tLCGetSet.size());

        for (int i = 1; i <= tLCGetSet.size(); i++) {
            LCGetSchema tLCGetSchema = tLCGetSet.get(i);
            tLCGetSchema.setState("03"); //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
            tLCGetSchema.setOperator("C"); //LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
            tLCGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo()); //LCGet表的该字段属于借用，用于存放出险人编号
            mLCGetSet.add(tLCGetSchema);
        }

        logger.debug("================结束========获取LCLBGet领取项信息==============================================");
        return true;

    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－进行匹配－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 得到保单险种信息
     */
    private boolean getLCPol(LCGetSchema pLCGetSchema) {

        SynLCLBPolBL tSynLCLBPolBL = new SynLCLBPolBL();
        tSynLCLBPolBL.setPolNo(pLCGetSchema.getPolNo());

        tSynLCLBPolBL.getInfo();
        if (tSynLCLBPolBL.mErrors.needDealError()) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSynLCLBPolBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getLCPol";
            tError.errorMessage = "LCLBPol表查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mLCPolSchema.setSchema(tSynLCLBPolBL.getSchema());

        return true;
    }


    /**
     * 得到保单责任信息
     */
    private boolean getLCDuty(LCGetSchema pLCGetSchema) {

        SynLCLBDutyBL tSynLCLBDutyBL = new SynLCLBDutyBL();
        tSynLCLBDutyBL.setPolNo(pLCGetSchema.getPolNo());
        tSynLCLBDutyBL.setDutyCode(pLCGetSchema.getDutyCode());

        tSynLCLBDutyBL.getInfo();
        if (tSynLCLBDutyBL.mErrors.needDealError()) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSynLCLBDutyBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getLCPol";
            tError.errorMessage = "LCLBDuty表查询失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mLCDutySchema.setSchema(tSynLCLBDutyBL.getSchema());
//        logger.debug("xxxxxxxxxxx:" + mLCDutySchema.getCValiDate());

        return true;
    }


    /**
     * 找出换号前的保单险种号,也就是承保时的保单号
     * @param pLCPolSchema
     * @return
     */
    private String getNBPolNo(LCPolSchema pLCPolSchema) {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0
         *  续期抽档时
         *      原保单的 AppFlag = "1"
         *      新保单的 AppFlag = "9"
         *  抽档回销成功时
         *      原保单的 AppFlag = "4"
         *      新保单的 AppFlag = "1"
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tReturn = "";
        String tAppFlag = StrTool.cTrim(pLCPolSchema.getAppFlag());
        if (tAppFlag.equals("4")) {
            String tSql = "select * from lcpol where 1=1 "
                          + " and ProposalNo='" + pLCPolSchema.getProposalNo() +
                          "'"
                          + " and appflag='1'"
                          ;

            LCPolDB tLCPolDB = new LCPolDB();
            LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSql);
            if (tLCPolDB.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tLCPolDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalMatchBL";
                tError.functionName = "getYearBonus";
                tError.errorMessage = "得到承保时的保单险种号失败!";
                logger.debug("--------------------------------------------------------------------------------------------------");
                logger.debug(
                        "--LLClaimCalMatchBL.getNBPolNo()--得到承保时的保单险种号失败!" +
                        tSql);
                logger.debug("--------------------------------------------------------------------------------------------------");
                this.mErrors.addOneError(tError);
            }

            if (tLCPolSet.size() == 1) {
                tReturn = StrTool.cTrim(tLCPolSet.get(1).getPolNo());
            } else {
                tReturn = StrTool.cTrim(pLCPolSchema.getPolNo());
            }
        } else {
            tReturn = StrTool.cTrim(pLCPolSchema.getPolNo());
        }
        return StrTool.cTrim(tReturn);
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－执行过滤算法－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**
     * 执行过滤算法，进一步判断
     * @param string String
     * @return boolean
     */
    private boolean getFilterCalCode(String pCalCode) {
        if (null == pCalCode || "".equals(pCalCode)) {
            return true;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 获取出险时点之前做过保全复效的批单号
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tPosEdorNoFront = getPosEdorNoFront(mLCGetSchema, this.mNBPolNo);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 设置各种过滤算法的计算要素
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        logger.debug(
                "----------开始---------执行过滤算法-----计算过滤要素--------------------");

        TransferData tTransferData = new TransferData();

        //出险日期
        tTransferData.setNameAndValue("AccidentDate",
                                      String.valueOf(this.mInsDate));

        //交费期限
        tTransferData.setNameAndValue("PayEndDate",
                                      String.valueOf(mLCDutySchema.
                getPayEndDate()));

        //
        tTransferData.setNameAndValue("PayEndYear",
                                      String.valueOf(mLCDutySchema.
                getPayEndYear()));

        //保单号
        tTransferData.setNameAndValue("PolNo",
                                      String.valueOf(mLCPolSchema.getPolNo()));

        //被保人0岁保单生效对应日
        tTransferData.setNameAndValue("InsuredvalidBirth",
                                      getInsuredvalideBirth());

        //出险时已保天数
        tTransferData.setNameAndValue("RgtDays",
                                      String.valueOf(PubFun.
                calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "D")));

        //出险时已保年期
        tTransferData.setNameAndValue("RgtYears",
                                      String.valueOf(PubFun.
                calInterval(mLCPolSchema.getCValiDate(), this.mInsDate, "Y")));

        //被保险人客户号
        tTransferData.setNameAndValue("InsuredNo",
                                      String.valueOf(mLCPolSchema.getInsuredNo()));

        //赔案号
        tTransferData.setNameAndValue("CaseNo", String.valueOf(this.mClmNo));

        //保单生效日期
        tTransferData.setNameAndValue("CValiDate",
                                      String.valueOf(mLCDutySchema.getCValiDate()));

        //死亡日期,也就是出险时间
        tTransferData.setNameAndValue("DeathDate",
                                      String.valueOf(mLLCaseSchema.getAccDate()));

        //意外伤害发生日期
        tTransferData.setNameAndValue("InjuryDate",
                                      String.valueOf(this.mAccDate));

        //交费间隔
        tTransferData.setNameAndValue("PayIntv",
                                      String.valueOf(mLCPolSchema.getPayIntv()));

        //交费期限
        tTransferData.setNameAndValue("PayEndDate",
                                      String.valueOf(mLCDutySchema.
                getPayEndDate()));

        //起领日期,取自出险时点,需要考虑保全
        tTransferData.setNameAndValue("GetStartDate",
                                      String.valueOf(getGetStartDate()));

        //保单是否复效的标记
        tTransferData.setNameAndValue("LRFlag",
                                      String.valueOf(getLRFlag(mLCGetSchema,
                tPosEdorNoFront)));

        //复效到出险时已保年期,取自出险时点,需要考虑保全
        tTransferData.setNameAndValue("LRYears",
                                      String.valueOf(PubFun.
                calInterval(getLastRevDate(mLCGetSchema, tPosEdorNoFront),
                            this.mInsDate, "Y")));

        //复效到出险时已保天数,取自出险时点,需要考虑保全
        tTransferData.setNameAndValue("LRDays",
                                      String.valueOf(PubFun.
                calInterval(getLastRevDate(mLCGetSchema, tPosEdorNoFront),
                            this.mInsDate, "D")));

        // 投保标志,1正常,4终止,9续保未生效
        tTransferData.setNameAndValue("AppFlag",
                                      String.valueOf(getAppFlag(mLCPolSchema)));

        // 责任
        tTransferData.setNameAndValue("DutyCode",
                                      String.valueOf(mLCDutySchema.getDutyCode()));

        // LCGet的开始时间
        tTransferData.setNameAndValue("LCGetStartDate",
                                      String.valueOf(mLCGetSchema.
                getGetStartDate()));

        // LCGet的终止时间
        tTransferData.setNameAndValue("LCGetEndDate",
                                      String.valueOf(mLCGetSchema.getGetEndDate()));

        //续保次数,用于医疗类附加险计算
        tTransferData.setNameAndValue("RenewCount",
                                      String.valueOf(mLCPolSchema.getRenewCount()));
        
        //保单生效日期
        tTransferData.setNameAndValue("CValiDate", String.valueOf(mLCPolSchema.getCValiDate()));

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 责任筛选算法.用于匹配算法的描述,返回值为1--匹配,0--不匹配
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(pCalCode);

        Vector tVector = tTransferData.getValueNames();
        logger.debug("==================================================================");
        for (int i = 0; i < tVector.size(); i++)
        {
            String tName = (String) tVector.get(i);
            String tValue = (String) tTransferData.getValueByName(tName);
            logger.debug("理算计算要素名称--tName:" + tName + "  tValue:" + tValue);
            tCalculator.addBasicFactor(tName, tValue);
        }
        logger.debug("==================================================================");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 进行计算，Calculator.calculate()
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        logger.debug("计算公式=" + tCalculator.getCalSQL());
        String tRet = tCalculator.calculate();
        if (tRet.trim().equals(""))
        {
        	tRet = "0";
        }

        
        if (tCalculator.mErrors.needDealError()) {
            CError.buildErr(this, "过滤算法计算发生错误!原公式:" + pCalCode + "||,解析后的公式:" +
                    tCalculator.getCalSQL());
            return false;
        }

        logger.debug(
                "----------结束---------执行过滤算法-------------------------------------");

        if ("1".equals(tRet)) {
            return true;
        } else {
            return false;
        }

    }


    /**
     * 计算被保人0岁保单生效对应日
     * @return
     */
    private String getInsuredvalideBirth() {
        FDate tInsuredBirthday = new FDate();
        FDate tCValidate = new FDate();
        tCValidate.getDate(mLCPolSchema.getCValiDate());
        Date tInsuredvalideBirth = null;
        tInsuredvalideBirth = PubFun.calDate(tInsuredBirthday.getDate(
                mLCPolSchema.getInsuredBirthday()), 0, "Y",
                                             tCValidate.getDate(mLCPolSchema.
                getCValiDate()));

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);

        return df.format(tInsuredvalideBirth);
    }


    /**
     * 找出险时点之前保全做过复效的批单号
     * 如果没有,将返回值置为空
     * @return
     */
    private String getPosEdorNoFront(LCGetSchema pLCGetSchema, String pNBPolNo) {

        String tReturn = "";
        String tSql = "";

        ExeSQL tExeSQL = new ExeSQL();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No1.0 根据保单的合同号、被保人编号
         *  在LPEdorItem个险保全项目表中找出最后的批改生效日期
         *  2005-05-01 >= 2005-04-01
         *  AA:附加险加保变更,PT:减保,and EdorType in ('PT','AA')
         *  RE：复效
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select max(EdorValiDate) from LPEdorItem where 1=1 "
               + " and ContNo ='" + pLCGetSchema.getContNo() + "'"
               + " and (PolNo ='" + pNBPolNo + "' or  PolNo in ('000000'))" //承保时的保单号
               + " and to_date('" + this.mInsDate +
               "','YYYY-MM-DD') >= EdorValiDate"
               + " and EdorType in ('RE')"
               ;

        String tEdorValiDate = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPosEdorNo";
            tError.errorMessage = "获取出险时点之前保全复效过生效日期发生错误!";
            logger.debug(
                    "------------------------------------------------------");
            logger.debug(
                    "--LLClaimCalAutoBL.getPosEdorNoFront()--获取出险时点之前保全复效过生效日期发生错误!" +
                    tSql);
            logger.debug(
                    "------------------------------------------------------");
            this.mErrors.addOneError(tError);
        }

        logger.debug("-----------------------------------------------------------------------------------");
        logger.debug("--getPosEdorNoFront()--获取出险时点之前保全复效的批改生效日期:[" +
                           tEdorValiDate + "]:" + tSql);
        logger.debug("-----------------------------------------------------------------------------------");

        if (tEdorValiDate == null || tEdorValiDate.length() == 0) {
            return null;
        } else {
            tEdorValiDate = tEdorValiDate.substring(0, 10);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No2.0 根据最早的核保完成生效时间
         *  在LPEdorItem个险保全项目表中找出批单号
         *  如果批单号空，则说明没有发生保全变更项目，取C表的基本保额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select EdorNo from LPEdorItem where 1=1 "
               + " and ContNo ='" + pLCGetSchema.getContNo() + "'"
               + " and (PolNo ='" + pNBPolNo + "' or  PolNo in ('000000'))" //承保时的保单号
               + " and EdorValiDate = to_date('" + tEdorValiDate +
               "','yyyy-mm-dd')"
               + " and EdorType in ('RE')"
               ;

        tReturn = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "获取出险时点之前保全复效过的批单号发生错误!";
            logger.debug(
                    "------------------------------------------------------");
            logger.debug(
                    "--LLClaimCalMatchBL.getPosEdorNoFront()--获取出险时点之前保全复效过的批单号发生错误!" +
                    tSql);
            logger.debug(
                    "------------------------------------------------------");

            this.mErrors.addOneError(tError);
        }

        if (tReturn == null || tReturn.length() == 0) {
            tReturn = null;
        }

        logger.debug("-----------------------------------------------------------------------------------");
        logger.debug("--getPosEdorNoFront()--获取出险时点之前保全复效过的批单号:[" +
                           tReturn + "]:" + tSql);
        logger.debug("-----------------------------------------------------------------------------------");

        return tReturn;
    }


    /**
     * 保单是否复效的标记
     * 1复效
     * 0无复效
     * @return
     */
    private String getLRFlag(LCGetSchema pLCGetSchema, String ptPosEdorNoFront) {

        String tSql = "";
        ExeSQL tExeSQL = new ExeSQL();
        String tReturn = "";

        if (ptPosEdorNoFront == null || ptPosEdorNoFront.length() == 0 ||
            ptPosEdorNoFront.equals("")) {
            return "0";
        } else {
            tSql = "select nvl(count(*),0) from LPPol where 1=1 "
                   + " and EdorNo ='" + ptPosEdorNoFront + "'"
                   + " and PolNo  ='" + pLCGetSchema.getPolNo() + "'";

            logger.debug("要素LRFlag[复效标记,保全]:" + tSql);
            tExeSQL = new ExeSQL();
            tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
            if (tExeSQL.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "LRFlag";
                tError.errorMessage = "得到保单是否复效的标记发生错误!";
                this.mErrors.addOneError(tError);
            }
        }
        return tReturn;

    }


    /**
     * 得到复效到出险时已保年期,取自出险时点,需要考虑保全
     * @return
     *
     */
    private String getLastRevDate(LCGetSchema pLCGetSchema,
                                  String ptPosEdorNoFront) {
        String tReturn = "";
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));

        /*0未做保全,1已做保全*/
        if (ptPosEdorNoFront == null || ptPosEdorNoFront.length() == 0) {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No1.0 本险种:保项保单号 = LCPol表的保单号
             *  计算主险[266] ,采用主险保单号,主要是针对于主险处理
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (pLCGetSchema.getPolNo().equals(mLCPolSchema.getPolNo())) {
                tReturn = mLCPolSchema.getLastRevDate();
                if (tReturn == null) {
                    tReturn = "2900-01-01";
                }
            } else {
                LCPolDB tLCPolDB = new LCPolDB();
                tLCPolDB.setPolNo(pLCGetSchema.getPolNo());
                if (tLCPolDB.getInfo() == false) {
                    this.mErrors.copyAllErrors(tLCPolDB.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "UrgeNoticeOverdueBL";
                    tError.functionName = "getLastRevDate";
                    tError.errorMessage = "查询保单险种信息失败!";
                    this.mErrors.addOneError(tError);
                }
                tReturn = tLCPolDB.getLastRevDate();
                if (tReturn == null) {
                    tReturn = "2900-01-01";
                }
            }

        } else {
            String tSql =
                    "select nvl(EdorValiDate,'2900-01-01') from LPEdorItem where 1=1 "
                    + " and EdorNo ='" + ptPosEdorNoFront + "'"
                    + " and ContNo ='" + pLCGetSchema.getContNo() + "'"
                    ;

            logger.debug("要素LastRevDate[复效到出险时已保年期]:" + tSql);
            ExeSQL tExeSQL = new ExeSQL();
            tReturn = tExeSQL.getOneValue(tSql);
            if (tExeSQL.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalAutoBL";
                tError.functionName = "getLastRevDate";
                tError.errorMessage = "计算要素LastRevDate[复效到出险时已保年期]发生错误!";
                this.mErrors.addOneError(tError);
            }
            if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
                tReturn = tReturn.substring(0, 10);
            }
        }

        return tReturn;
    }


    /**
     * 得到生存领取的起领日期,取自出险时点
     * @return
     */
    private String getGetStartDate() {
        String tReturn = "";
        ExeSQL tExeSQL = new ExeSQL();
        String tSql = "";

        /*0未做保全,1已做保全*/

        if (mLPEdorItemSchema == null) {
            tSql =
                    "select nvl(GetStartDate,'1900-01-01') from LCGet where 1=1 "
                    + " and PolNo ='" + mLCGetSchema.getPolNo() + "'"
                    + " and DutyCode ='" + mLCGetSchema.getDutyCode() + "'"
                    + " and GetDutyCode in "
                    +
                    " (select b.GetDutyCode from LMDutyGetRela a,LMDutyGet b where a.DutyCode = '" +
                    mLCGetSchema.getDutyCode() + "'"
                    + " and a.GetDutyCode = b.GetDutyCode "
                    + " and a.GetDutyCode not in ('" +
                    mLCGetSchema.getGetDutyCode() + "')"
                    + " and b.GetType2 = '1')" //养老金
                    + " and LiveGetType ='0'"; //生存意外给付标志,为0生存领取
        } else {
            String tPosEdorNo = StrTool.cTrim(this.mLPEdorItemSchema.getEdorNo());
            tSql =
                    "select nvl(GetStartDate,'1900-01-01') from LPGet where 1=1 "
                    + " and EdorNo ='" + tPosEdorNo + "'"
                    + " and PolNo ='" + mLCGetSchema.getPolNo() + "'"
                    + " and DutyCode ='" + mLCGetSchema.getDutyCode() + "'"
                    + " and GetDutyCode in "
                    +
                    " (select b.GetDutyCode from LMDutyGetRela a,LMDutyGet b where a.DutyCode = '" +
                    mLCGetSchema.getDutyCode() + "'"
                    + " and a.GetDutyCode not in ('" +
                    mLCGetSchema.getGetDutyCode() + "')"
                    + " and b.GetType2 = '1')" //养老金
                    + " and LiveGetType ='0'"; //生存意外给付标志,为0生存领取
        }

        tReturn = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全生存领取的起领日期发生错误!";
            logger.debug(
                    "------------------------------------------------------");
            logger.debug(
                    "--LLClaimCalMatchBL.getGetStartDate()--得到保全生存领取的起领日期发生错误!" +
                    tSql);
            logger.debug(
                    "------------------------------------------------------");
            this.mErrors.addOneError(tError);
        }

        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            tReturn = tReturn.substring(0, 10);
        }

        logger.debug("要素GetStartDate[生存领取的起领日期]:" + tSql);
        return tReturn;

    }


    /**
     *
     * @return
     */
    private String getAppFlag(LCPolSchema pLCPolSchema) {
        String tReturn = "";
        String tSql = "select nvl(count(*),0) from lcpol where 1=1 "
                      + " and proposalno='" + pLCPolSchema.getProposalNo() +
                      "'"
                      + " and renewcount>0 "
                      + " and appflag=1 "
                      + " and riskcode='" + pLCPolSchema.getRiskCode() + "'"
                      + " and insuredno='" + pLCPolSchema.getInsuredNo() + "'"

                      ;

        logger.debug("要素AppFlag[投保标志]:" + tSql);
        ExeSQL tExeSQL = new ExeSQL();
        String tAppFlag = StrTool.cTrim(tExeSQL.getOneValue(tSql));

        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getAppFlag";
            tError.errorMessage = "计算投保标志发生错误!";
            this.mErrors.addOneError(tError);
        }

        if (tAppFlag.equals("1")) {
            tReturn = "1";
        } else {
            tReturn = "0";
        }

        return tReturn;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－进行保项的过滤，针对多出险人的情况－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 目的：进行保项的过滤，针对多出险人的情况
     * @param pLMDutyGetClmSchema
     * @param pPolNo
     * @param pCasePolType
     * @return
     */
    private boolean getFilterMatch(LMDutyGetClmSchema pLMDutyGetClmSchema,
                                   LCGetSchema pLCGetSchema) {
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * 给付类型,pLMDutyGetClmSchema.getCasePolType()
//         * 12位,00--表示投保人,01--表示被保人
//         * 56位,01--第一顺序（投保人或被保险人等）,02第二顺序
//         *
//         * 给付类型pCasePolType,00- 投保人,01-被保人,02-受益人，03-连带被保人
//         *
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//
//        String tCasePolType = pLMDutyGetClmSchema.getCasePolType();
//        String tRiskRole = "";
//        String tRiskRoleNo = "";
//        if (tCasePolType != null && tCasePolType.length() == 6) {
//            tRiskRole = tCasePolType.substring(0, 2);
//            tRiskRoleNo = tCasePolType.substring(4, 6);
//        }
//
//        String pCasePolType = pLCGetSchema.getState(); //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
//
//        logger.debug("--------第二过滤算法:LCGet的投保标志:[" + pCasePolType +
//                           "],产品定义的标志:[" + tCasePolType + "]");
//
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * 一定要先判断符合匹配条件的信息
//         * 进行12位判断，也就是投保人判断
//         *
//         * 给付类型pCasePolType,00- 投保人,01-被保人,02-受益人，03-连带被保人
//         *
//         * 传进来的参数与产品LMDutyGetClmSchema表中CasePolType的12位进行比较
//         *
//         *  pCasePolType = 00 and tRiskRole = 00    符合匹配条件
//         *  pCasePolType = 00 and tRiskRole = 01    不符合匹配条件
//         *  pCasePolType = 00 and tRiskRole = null  不符合匹配条件
//         *
//         *  pCasePolType = 01 and tRiskRole = 空    符合匹配条件
//         *  pCasePolType = 01 and tRiskRole = 00    不符合匹配条件
//         *  pCasePolType = 01 and tRiskRole = 01    进行下一步判断
//         *
//         *  pCasePolType = 01 and tRiskRole = 01    进行下一步判断
//         *
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//
//        if (tRiskRole.equals("00") || tRiskRole.equals("01") ||
//            tRiskRole.equals("") || tRiskRole == null) {
//
//        } else {
//            return false;
//        }
//
//        if (pCasePolType.equals("00") && tRiskRole.equals("00")) {
//            return true;
//        }
//        if (pCasePolType.equals("00") && tRiskRole.equals("01")) {
//            return false;
//        }
//        if (pCasePolType.equals("00") &&
//            (tRiskRole == null || tRiskRole.equals(""))) {
//            return false;
//        }
//
//        if (pCasePolType.equals("01") &&
//            (tRiskRole == null || tRiskRole.equals(""))) {
//            return true;
//        }
//
//        if (pCasePolType.equals("01") && tRiskRole.equals("00")) {
//            return false;
//        }
//
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * 进行56位判断，也就是被保人判断，01--第一顺序（投保人或被保险人等）,02第二顺序
//         *
//         * pCasePolType = 01 and tRiskRole = 01    进行下一步判断
//         *
//         * 1.   先从LCInsured表中找出是否有数据
//         *  1.2  如果有，将tSequenceNo = 01，与LCInsured.被保人顺序号相比较，如果不等，则不符合匹配条件
//         *
//         *  1.3  如果无，从LCInsuredRelated个单连带被保人表找是否数据
//         *      1.3.1如果有，LCInsuredRelated.SequenceNo(连带被保人表的序号)
//         *                     与 LMDutyGetClmSchema.CasePolType(5,6) ,
//         *                  相比较，如果不等，则不符合匹配条件
//         *
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//        if (pCasePolType.equals("01") && tRiskRole.equals("01")) {
//            String tSequenceNo = "";
//            LCInsuredDB tLCInsuredDB = new LCInsuredDB();
//            tLCInsuredDB.setContNo(pLCGetSchema.getContNo());
//            tLCInsuredDB.setInsuredNo(mLLCaseSchema.getCustomerNo());
//            LCInsuredSet tLCInsuredSet = tLCInsuredDB.query();
//
//            //LCInsured表有数据
//            if (tLCInsuredSet != null && tLCInsuredSet.size() == 1) {
//                tSequenceNo = "01";
//                String tNBSequenceNo = "0" +
//                                       StrTool.cTrim(tLCInsuredSet.get(1).getSequenceNo());
//
//                if (tSequenceNo.equals(tNBSequenceNo) &&
//                    tSequenceNo.equals(tRiskRoleNo)) {
//                    return true;
//                }
//
//                if (!tSequenceNo.equals(tNBSequenceNo)) {
//                    String pPolNo = pLCGetSchema.getPolNo();
//
//                    LCInsuredRelatedDB tLCInsuredRelatedDB = new
//                            LCInsuredRelatedDB();
//                    tLCInsuredRelatedDB.setPolNo(pPolNo);
//                    tLCInsuredRelatedDB.setCustomerNo(mLLCaseSchema.
//                            getCustomerNo());
//                    LCInsuredRelatedSet tLCInsuredRelatedSet =
//                            tLCInsuredRelatedDB.query();
//
//                    if (tLCInsuredRelatedSet != null &&
//                        tLCInsuredRelatedSet.size() == 1) {
//                        tSequenceNo = tLCInsuredRelatedSet.get(1).getSequenceNo();
//
//                        if (tSequenceNo.equals(tRiskRoleNo)) {
//                            return true;
//                        } else {
//                            return false;
//                        }
//
//                    } else {
//                        return false;
//                    }
//
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//
//        }
    	
	      /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	       * 2009-02-18 zhangzheng
	      * 给付类型,pLMDutyGetClmSchema.getCasePolType()
	      * 0或空 主被保险人
	      * 2 配偶
	      * 3 子女
	      * 4 连带被保险人
	      * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
	      */
	
	     String tCasePolType = pLMDutyGetClmSchema.getCasePolType();
	     
	     /**
	      * 过滤不需要的给付责任
	      * 当给付类型为 0或空时,表示是作为主被保险人,当在LCInsuredRelated查询到记录时,return false,被过滤掉;
	      * 										   当在LCInsuredRelated查询不到记录时,return true;
	      * 当给付类型为 2,3,4时,表示是作为连带被保险人,当在LCInsuredRelated查询到记录时,return true;
	      * 										   当在LCInsuredRelated查询不到记录时,return false,被过滤掉;
	      */
	     
	     LCInsuredRelatedDB tLCInsuredRelatedDB = new LCInsuredRelatedDB();
	     tLCInsuredRelatedDB.setPolNo(pLCGetSchema.getPolNo());
	     tLCInsuredRelatedDB.setCustomerNo(mLLCaseSchema.getCustomerNo());

	     logger.debug("--------本次过滤的产品定义的标志:[" + tCasePolType + "],polno:"+pLCGetSchema.getPolNo()+",客户号:"+
	    		 mLLCaseSchema.getCustomerNo()+"客户姓名:"+mLLCaseSchema.getCustomerName());
	     
	     //tCasePolType为空时默认为主被保险人
	     if(tCasePolType==null||tCasePolType.equals("")||tCasePolType.equals("0")){
	    	 
	         if(tLCInsuredRelatedDB.query().size()>0)
		     {
	    	     logger.debug("--------本次过滤的产品定义的标志:[" + tCasePolType + "],polno:"+pLCGetSchema.getPolNo()+",客户号:"+
	    	    		 mLLCaseSchema.getCustomerNo()+"客户姓名:"+mLLCaseSchema.getCustomerName()+",被过滤掉!");
	    	     return false;
		     }
	         else
	         {
	    	     return true;
	         }
	     }
	     
	     //tCasePolType不为空时即为连带被保险人中的一种
	     if(!(tCasePolType==null||tCasePolType.equals(""))){ 
	    	 
	    	 LCInsuredRelatedSet tLCInsuredRelatedSet = new LCInsuredRelatedSet();
	    	 tLCInsuredRelatedSet=tLCInsuredRelatedDB.query();
	    	 
	    	 if(tCasePolType.equals("4"))
	    	 {
	             if(tLCInsuredRelatedSet.size()>0)
	    	     {
	        	     return true;
	    	     }
	             else
	             {
	        	     logger.debug("--------本次过滤的产品定义的标志:[" + tCasePolType + "],polno:"+pLCGetSchema.getPolNo()+",客户号:"+
	        	    		 mLLCaseSchema.getCustomerNo()+"客户姓名:"+mLLCaseSchema.getCustomerName()+",被过滤掉!");
	        	     return false;
	             }
	    	 }
	    	 
	    	 if(tCasePolType.equals("2"))
	    	 {
	             if(tLCInsuredRelatedSet.size()>0)
	    	     {
	            	 if(tLCInsuredRelatedSet.get(1).getRelationToInsured().equals("01")||
	            			 tLCInsuredRelatedSet.get(1).getRelationToInsured().equals("02"))
	            	 {
		        	     return true;
	            	 }
	            	 else
	            	 {
	             	     logger.debug("--------本次过滤的产品定义的标志:[" + tCasePolType + "],polno:"+pLCGetSchema.getPolNo()+",客户号:"+
		        	    		 mLLCaseSchema.getCustomerNo()+"客户姓名:"+mLLCaseSchema.getCustomerName()+",与主被保险人关系不是配偶,被过滤掉!");
		        	     return false;
	            	 }

	    	     }
	             else
	             {
	        	     logger.debug("--------本次过滤的产品定义的标志:[" + tCasePolType + "],polno:"+pLCGetSchema.getPolNo()+",客户号:"+
	        	    		 mLLCaseSchema.getCustomerNo()+"客户姓名:"+mLLCaseSchema.getCustomerName()+",被过滤掉!");
	        	     return false;
	             }
	    	 }
	    	 
	    	 if(tCasePolType.equals("3"))
	    	 {
	             if(tLCInsuredRelatedSet.size()>0)
	    	     {
	            	 if(tLCInsuredRelatedSet.get(1).getRelationToInsured().equals("05")||
	            			 tLCInsuredRelatedSet.get(1).getRelationToInsured().equals("06"))
	            	 {
		        	     return true;
	            	 }
	            	 else
	            	 {
	             	     logger.debug("--------本次过滤的产品定义的标志:[" + tCasePolType + "],polno:"+pLCGetSchema.getPolNo()+",客户号:"+
		        	    		 mLLCaseSchema.getCustomerNo()+"客户姓名:"+mLLCaseSchema.getCustomerName()+",与主被保险人关系不是子女,被过滤掉!");
		        	     return false;
	            	 }

	    	     }
	             else
	             {
	        	     logger.debug("--------本次过滤的产品定义的标志:[" + tCasePolType + "],polno:"+pLCGetSchema.getPolNo()+",客户号:"+
	        	    		 mLLCaseSchema.getCustomerNo()+"客户姓名:"+mLLCaseSchema.getCustomerName()+",被过滤掉!");
	        	     return false;
	             }
	    	 }
	     } 

	     return true;

    }


    /**
     * 对于两个被保人的情况过滤掉相同的保项信息
     * @param pLCGetSchema
     * @param pLLToClaimDutySet
     * @return
     */
    private boolean getFilterGetDutyCode(LCGetSchema pLCGetSchema,
                                         LLToClaimDutySet pLLToClaimDutySet) {

        String tLCPolNo = StrTool.cTrim(pLCGetSchema.getPolNo());
        String tLCDutyCode = StrTool.cTrim(pLCGetSchema.getDutyCode());
        String tLCGetDutyKind = StrTool.cTrim(pLCGetSchema.getGetDutyKind());
        String tLCGetDutyCode = StrTool.cTrim(pLCGetSchema.getGetDutyCode());

        for (int i = 1; i <= pLLToClaimDutySet.size(); i++) {
            LLToClaimDutySchema tLLToClaimDutySchema = pLLToClaimDutySet.get(i);
            String tLLPolNo = StrTool.cTrim(tLLToClaimDutySchema.getPolNo());
            String tLLDutyCode = StrTool.cTrim(tLLToClaimDutySchema.getDutyCode());
            String tLLGetDutyKind = StrTool.cTrim(tLLToClaimDutySchema.
                                                  getGetDutyKind());
            String tLLGetDutyCode = StrTool.cTrim(tLLToClaimDutySchema.
                                                  getGetDutyCode());

            if (tLCPolNo.equals(tLLPolNo) && tLCDutyCode.equals(tLLDutyCode) &&
                tLCGetDutyKind.equals(tLLGetDutyKind) &&
                tLCGetDutyCode.equals(tLLGetDutyCode)) {
                return false;
            }
        }

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－执行过滤算方－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */



    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－进行匹配后保项的添加－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 目的：根据LCGet设置LLToClaimDuty信息
     * @return
     */
    private boolean getLLToClaimDuty(LCGetSchema pLCGetSchema,
                                     LMDutyGetClmSchema pLMDutyGetClmSchema) {
        String tGetDutyKind = pLCGetSchema.getGetDutyKind();
        mLLToClaimDutySchema = new LLToClaimDutySchema();
        mLLToClaimDutySchema.setCaseNo(this.mClmNo); /* 分案号 */
        mLLToClaimDutySchema.setCaseRelaNo(this.mClmNo); /* 受理事故号 */
        mLLToClaimDutySchema.setSubRptNo(this.mClmNo); /* 事件号 */

        mLLToClaimDutySchema.setPolNo(pLCGetSchema.getPolNo()); /* 保单号*/
        mLLToClaimDutySchema.setPolType(mLCPolSchema.getPolTypeFlag());
                /*保单性质0 --个人单,1 --无名单,2 --（团单）公共帐户*/

        /* LCGet表的该字段属于借用，保单类型,C表保单，B表保单,A承保出单前出险*/
        mLLToClaimDutySchema.setPolSort(pLCGetSchema.getOperator());

        mLLToClaimDutySchema.setDutyCode(pLCGetSchema.getDutyCode());
                /* 责任编码 */
        mLLToClaimDutySchema.setGetDutyCode(pLCGetSchema.getGetDutyCode());
                /* 给付责任编码 */
        mLLToClaimDutySchema.setGetDutyKind(pLCGetSchema.getGetDutyKind());
                /* 给付责任类型 */

        mLLToClaimDutySchema.setGrpContNo(mLCPolSchema.getGrpContNo());
                /* 集体合同号 */
        mLLToClaimDutySchema.setGrpPolNo(mLCPolSchema.getGrpContNo());
                /* 集体保单号 */
        mLLToClaimDutySchema.setContNo(mLCPolSchema.getContNo()); /* 个单合同号 */
        mLLToClaimDutySchema.setKindCode(mLCPolSchema.getKindCode());
                /* 险类代码 */
        mLLToClaimDutySchema.setRiskCode(mLCPolSchema.getRiskCode());
                /* 险种代码 */
        mLLToClaimDutySchema.setRiskVer(mLCPolSchema.getRiskVersion());
                /* 险种版本号 */
        mLLToClaimDutySchema.setPolMngCom(mLCPolSchema.getManageCom());
                /* 保单管理机构 */
        mLLToClaimDutySchema.setClaimCount(0); /* 理算次数 */
        mLLToClaimDutySchema.setGiveType("0"); /* 赔付结论 */
        mLLToClaimDutySchema.setCurrency(mLCPolSchema.getCurrency());

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 取出险种的帐户标志
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tRiskCode = mLLToClaimDutySchema.getRiskCode();
        LMRiskSchema tLMRiskSchema = mLLClaimPubFunBL.getLMRisk(tRiskCode);
        if (tLMRiskSchema == null) {
            this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
            return false;
        }
 
    	String tInsuredFlag = StrTool.cTrim(pLMDutyGetClmSchema.getInpFlag());// 帐户标志

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 取出主附险[M],附加险标志[S]
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LMRiskAppSchema tLMRiskAppSchema = mLLClaimPubFunBL.getLMRiskApp(
                tRiskCode);
        if (tLMRiskAppSchema == null) {
            this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
            return false;
        }
        String tSubRiskFlag = StrTool.cTrim(tLMRiskAppSchema.getSubRiskFlag()); //主附险标志

        /* 主附险标志 M主险 S附险 */
        mLLToClaimDutySchema.setRiskType(tSubRiskFlag);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 如果出附加险影响主险,则取出主险所在的LCGet信息
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tEffectOnMainRisk = StrTool.cTrim(pLMDutyGetClmSchema.
                                                 getEffectOnMainRisk()); //附险影响主险标志

        LCGetSchema tMRiskLCGetSchema = null;
        if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01"))) {
            tMRiskLCGetSchema = getMRiskLCGetSchema(pLCGetSchema);
            if (tMRiskLCGetSchema == null) {
                this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                return false;
            }

            mLLToClaimDutySchema.setEffectOnMainRisk(tEffectOnMainRisk);
            /*该字段属于借用,用于保存主险的公式*/
            mLLToClaimDutySchema.setRiskCalCode(tMRiskLCGetSchema.getOperator());
        }
        
        if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("02"))) {

            mLLToClaimDutySchema.setEffectOnMainRisk(tEffectOnMainRisk);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 判断是否做过保全,如果做过保全,
         *  从LPGet表里取出数据,如果没有,返回LPPol的信息
         *
         *  有效保额 = 标准给付金额 - 已领金额
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (mLPEdorItemSchema == null && !tInsuredFlag.equals("3")) {
            if (tSubRiskFlag.equals("S") && (tEffectOnMainRisk.equals("01"))) {
                //取主险的保额
                double tMoney = tMRiskLCGetSchema.getStandMoney() - tMRiskLCGetSchema.getSumMoney();
                if( tMoney <= 0 ){
                    tMoney = 0;
                }
                mLLToClaimDutySchema.setAmnt(tMoney);
            } else {
                double tMoney = pLCGetSchema.getStandMoney() - pLCGetSchema.getSumMoney();
                if (tMoney <= 0) {
                    tMoney = 0;
                }
              mLLToClaimDutySchema.setAmnt(tMoney);
            }
            mLLToClaimDutySchema.setPosFlag("0"); /*0未做保全,1已做保全*/
            mLLToClaimDutySchema.setPosEdorNo(""); /*保全批单号*/

        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No7.0 发生过保全业务
         *  1.为附险并影响主险,判断是否有保全批单号,如果有,找LPGet
         *  2.0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        else if (mLPEdorItemSchema != null && !tInsuredFlag.equals("3")) {
            LPGetSchema tLPGetSchema = null;

            //取主险的保额
            if (tSubRiskFlag.equals("S") && tEffectOnMainRisk.equals("01")) {
                /* 取出主险的保全批改类型表 */
                LPEdorItemSchema tMRLPEdorItemSchema = mLLClaimPubFunBL.
                        getLPEdorItemAfter(tMRiskLCGetSchema.getContNo(),
                                           tMRiskLCGetSchema.getPolNo(),
                                           this.mInsDate, null); //得到保全批改项目表

                String tMRiskPosEdorNo = "";
                if (tMRLPEdorItemSchema != null) {
                    tMRiskPosEdorNo = StrTool.cTrim(tMRLPEdorItemSchema.
                            getEdorNo());
                }

                tLPGetSchema = mLLClaimPubFunBL.getLPGet(tMRiskPosEdorNo,
                        tMRiskLCGetSchema.getPolNo(), tMRiskLCGetSchema);

                if (tLPGetSchema != null) {
                    double tMoney = tLPGetSchema.getStandMoney() -
                                    tLPGetSchema.getSumMoney();
                    if (tMoney <= 0) {
                        tMoney = 0;
                    }
                    mLLToClaimDutySchema.setAmnt(tMoney);
                    mLLToClaimDutySchema.setPosFlag("2");
                            /*0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化*/
                } else {
                    double tMoney = pLCGetSchema.getStandMoney() -
                                pLCGetSchema.getSumMoney();
                    if (tMoney <= 0) {
                        tMoney = 0;
                    }
                        mLLToClaimDutySchema.setAmnt(tMoney);
//                        mLLToClaimDutySchema.setAmnt(pLCGetSchema.getStandMoney());
                    mLLToClaimDutySchema.setPosFlag("1"); /*0未做保全,1已做保全*/
                }
                mLLToClaimDutySchema.setPosEdorNo(tMRiskPosEdorNo); /*保全批单号*/
                mLLToClaimDutySchema.setPosEdorType(tMRLPEdorItemSchema.
                        getEdorType()); /*保全业务类型*/
            }
            else {
//                /* 有效保额 = 标准给付金额 - 已领金额 */
                mLCGetSchema.setPolNo(this.mNBPolNo);
                String tPosEdorNo = StrTool.cTrim(mLPEdorItemSchema.getEdorNo());
//
//                tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosEdorNo,
//                        this.mNBPolNo, mLCGetSchema);
//
//                if (tLPGetSchema != null) {
//                    double tMoney = tLPGetSchema.getStandMoney() -
//                                    tLPGetSchema.getSumMoney();
//                    if (tMoney <= 0) {
//                        tMoney = 0;
//                    }
//                    mLLToClaimDutySchema.setAmnt(tMoney);
//                    mLLToClaimDutySchema.setPosFlag("2");
//                    /*0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化*/
//
//                }
//                else {
                    double tMoney =pLCGetSchema.getStandMoney() -
                            pLCGetSchema.getSumMoney();
                    if (tMoney <= 0) {
                        tMoney = 0;
                    }
                    mLLToClaimDutySchema.setAmnt(tMoney);
//                        mLLToClaimDutySchema.setAmnt(pLCGetSchema.getStandMoney());
                    mLLToClaimDutySchema.setPosFlag("1"); /*0未做保全,1已做保全*/
//                }
                mLLToClaimDutySchema.setPosEdorNo(tPosEdorNo); /*保全批单号*/
                mLLToClaimDutySchema.setPosEdorType(mLPEdorItemSchema.getEdorType()); /*保全业务类型*/

            }

        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No7.0 如果是帐户型险种,取出本金,利息作为有效保额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (tInsuredFlag.equals("3")) {
            double tMoney = 0.0;
            if(mRiskPeriod.equals("1")){
                AccountManage tAccountManage = new AccountManage();
                LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
                LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
                TransferData aAccClassRet = new TransferData();
                if(!pLCGetSchema.getGetDutyKind().equals("1")){
                    tLCInsureAccClassDB.setPolNo(pLCGetSchema.getPolNo());
                    tLCInsureAccClassDB.setInsuredNo(pLCGetSchema.getInsuredNo());
                    tLCInsureAccClassDB.setRiskCode(tLMRiskAppSchema.getRiskCode());
                    String tSql ="select payplancode from lmdutypayrela where dutycode='" + pLCGetSchema.getDutyCode() + "'";
                    ExeSQL tExeSQL = new ExeSQL();
                    String tReturn = tExeSQL.getOneValue(tSql);
                    tLCInsureAccClassDB.setPayPlanCode(tReturn);
                    tLCInsureAccClassDB.setAccAscription("1");
                    LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
                    tLCInsureAccClassSet = tLCInsureAccClassDB.query();

                    if (tLCInsureAccClassSet != null &&
                        tLCInsureAccClassSet.size() > 0) {
                        for (int a = 1; a <= tLCInsureAccClassSet.size(); a++) {
                            double tMoneyLC = 0.0;
                            tLCInsureAccClassSchema = tLCInsureAccClassSet.get(a);
//                            aAccClassRet = tAccountManage.getAccClassInterestNew(
//                                    tLCInsureAccClassSchema, CurrentDate, "Y",
//                                    "D", 2, "F", "D");
                            //09-03-20里算程序只理算，不结息，注掉此段
//                            aAccClassRet=tAccountManage.getAccClassInterestNewMS(tLCInsureAccClassSchema,CurrentDate,"Y", "D");
                            
//                            String tempmoney = String.valueOf(aAccClassRet.getValueByName("aAccClassSumPay"));
                            String tempmoney = String.valueOf(tLCInsureAccClassSchema.getInsuAccBala());
                            tMoneyLC = Double.parseDouble(tempmoney);
                            tMoney = tMoney + tMoneyLC;
                        }
                    }
//                    else {
//                        // @@错误处理
//                        this.mErrors.copyAllErrors(tLCInsureAccClassSchema.
//                                mErrors);
//                        CError tError = new CError();
//                        tError.moduleName = "LLClaimCalMatchBL";
//                        tError.functionName = "dealData";
//                        tError.errorMessage = "未查询到个人帐户信息!";
//                        this.mErrors.addOneError(tError);
//                        return false;
//                    }
                }
            }else{
                tMoney = getInsureAcc(pLCGetSchema, this.mClmNo);
            }
            if (tMoney <= 0) {
                tMoney = 0;
            }
            mLLToClaimDutySchema.setAmnt(tMoney);
            mLLToClaimDutySchema.setPosFlag("0"); /*0未做保全,1已做保全*/
            mLLToClaimDutySchema.setPosEdorNo(""); /*保全批单号*/
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No7.0 判断是否是分红险
         * 泰康目前不需要分红险 2006-03-27 注释 P.D
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

//        if (mLLClaimPubFunBL.getBonus(mLCPolSchema).equals("Y")) {
//            mLLToClaimDutySchema.setYearBonus(mLLClaimPubFunBL.getYearBonus(
//                    mLCPolSchema.getPolNo(), this.mAccDate)); /* 年度红利 */
//            mLLToClaimDutySchema.setEndBonus(mLLClaimPubFunBL.getFinalBonus(
//                    mLCPolSchema.getPolNo(), this.mAccDate)); /* 终了红利 */
//        }
//
//        else {
            mLLToClaimDutySchema.setYearBonus("0"); /* 年度红利 */
            mLLToClaimDutySchema.setEndBonus("0"); /* 终了红利 */
//        }

        /* 缴费宽限期 */
        mLLToClaimDutySchema.setGracePeriod(getGracePeriod(mLCPolSchema.
                getRiskCode()));

        /* LCGet表的该字段属于借用，给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人*/
        mLLToClaimDutySchema.setCasePolType(pLCGetSchema.getState());

        /* LCGet表的该字段属于借用，用于存放出险人编号 */
        mLLToClaimDutySchema.setCustomerNo(pLCGetSchema.getInsuredNo());

        /* 预付标志,0没有预付,1已经预付 */
        mLLToClaimDutySchema.setPrepayFlag("0");

        /* 预付金额 */
        mLLToClaimDutySchema.setPrepaySum("0");
        /* 加保后的保额 */
        mLLToClaimDutySchema.setAddAmnt("0");

        /* 做过续期抽档之后,把原号保存起来,用于后续业务 */
        mLLToClaimDutySchema.setNBPolNo(this.mNBPolNo);

        mLLToClaimDutySet.add(mLLToClaimDutySchema);

        return true;
    }

    /**
     *  出险类型（GetDutyKind）等于101，102，201，202的时候
     *  得到已经结案的赔付金额
     *  TK 2005-12-14 P.D
     * @return double
     */
    private double getDieMoney(LCGetSchema pLCGetSchema){
        String tSql = "";
        String tReturn = "";
        double DieMoney = 0.00;
        ExeSQL tExeSQL = new ExeSQL();
        tSql = " select sum(b.realpay) from llclaim a , llclaimdetail b where a.ClmState = '60'"
             + " and a.caseno = b.caseno and b.getdutykind in ('101','102','201','202')"
             + " and b.polno = '"+pLCGetSchema.getPolNo()+"'"
             + " and b.dutycode = '"+pLCGetSchema.getDutyCode()+"'";
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            DieMoney = Double.parseDouble(tReturn);
        }
        return DieMoney;
    }

    /**
     * 得到主险所在的LCGet信息
     * @return
     */
    private LCGetSchema getMRiskLCGetSchema(LCGetSchema pLCGetSchema) {

        String tGetDutyKind = pLCGetSchema.getGetDutyKind();

        String tSql = "";
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No1.0 根据附加险的合同号找出主险的PolNo
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select * from LCPol where 1=1"
               + " and ContNo = '" + pLCGetSchema.getContNo() + "'"
               +
                " and RiskCode in (select riskcode from LMRiskApp where SubRiskFlag in ('M') )"
               ;
        LCPolDB tLCPolDB = new LCPolDB();
        LCPolSet tLCPolSet = tLCPolDB.executeQuery(tSql);

        if (tLCPolDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "查找合同号:[" + pLCGetSchema.getContNo() +
                                  "]的主险信息失败!";
            this.mErrors.addOneError(tError);
            logger.debug(
                    "------------------------------------------------------");
            logger.debug(
                    "--LLClaimCalMatchBL.getMRiskLCGetSchema()--查找合同号:[" +
                    pLCGetSchema.getContNo() + "]的主险信息失败!" + tSql);
            logger.debug(
                    "------------------------------------------------------");
            return null;
        }

        if (tLCPolSet.size() != 1) {
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "查找合同号:[" + pLCGetSchema.getContNo() +
                                  "]的主险信息不唯一!" + tSql;
            this.mErrors.addOneError(tError);
            return null;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No2.0 根据主险的PolNo和附加险所在理赔类型,在定义表找出相应的GetDutyCode信息
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        tSql = "select * from LCGet where 1=1 "
               + " and PolNo = '" + tLCPolSet.get(1).getPolNo() + "'"
               + " and GetDutyCode in "
               + " (select GetDutyCode from lmdutygetclm where getdutykind ='" +
               tGetDutyKind + "')"
               ;

        LCGetDB tLCGetDB = new LCGetDB();
        LCGetSet tLCGetSet = tLCGetDB.executeQuery(tSql);

        if (tLCGetDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "查找主险保单号:[" + tLCGetSet.get(1).getPolNo() +
                                  "]的领取项信息失败!" + tSql;
            this.mErrors.addOneError(tError);
            return null;
        }

        if (tLCGetSet.size() != 1) {
            this.mErrors.copyAllErrors(tLCPolDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "查找主险保单号:[" + tLCGetSet.get(1).getPolNo() +
                                  "]的领取项信息不唯一!" + tSql;
            this.mErrors.addOneError(tError);
            return null;
        }

        LCGetSchema tLCGetSchema = null;
        tLCGetSchema = tLCGetSet.get(1);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *  No3.0 找出主险所在公式
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
        tLMDutyGetClmDB.setGetDutyKind(tGetDutyKind);
        tLMDutyGetClmDB.setGetDutyCode(tLCGetSchema.getGetDutyCode());
        if (!tLMDutyGetClmDB.getInfo()) {
            mErrors.addOneError("查找理赔类型:[" + tGetDutyKind + "],给付责任编码:[" +
                                tLCGetSchema.getGetDutyCode() + "],的产品定义信息失败!");
            return null;
        }

        LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmDB.getSchema();

        //保项计算公式代码
        String t_CalDutyCode = StrTool.cTrim(tLMDutyGetClmSchema.getCalCode());
        tLCGetSchema.setOperator(t_CalDutyCode); /*该字段属于借用,用于保存主险的公式*/

        return tLCGetSchema;
    }


    /**
     * 得到宽限期的天数
     * @param pRiskCode 险种编号
     * @return
     */
    private int getGracePeriod(String pRiskCode) {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        LMRiskPayDB tLMRiskPayDB = new LMRiskPayDB();
        tLMRiskPayDB.setRiskCode(pRiskCode);
        //tLMRiskPayDB.setRiskVer(pRiskVer);
        LMRiskPaySet tLMRiskPaySet = tLMRiskPayDB.query();

        int tGracePeriod = 0;
        if (tLMRiskPaySet != null && tLMRiskPaySet.size() == 1) {
            tGracePeriod = tLMRiskPaySet.get(1).getGracePeriod();
            //if (tGracePeriod != null && !tGracePeriod.equals("") )
        }
        return tGracePeriod;
    }


    /**
     * 计算帐户型的本金和利息
     * 修改账户理赔  l.b  2005-12-06
     * @return
     */
    private double getInsureAcc(LCGetSchema pLCGetSchema, String tClmNo) {
        logger.debug("赔案号:" + tClmNo);
        LLClaimAccountSet vLLClaimAccountSet=new LLClaimAccountSet();
        double tSumMoney = 0;//最后赔付的金额
        double tEachMoney = 0; //账户最终金额
        String tFlag=""; //比较标志
        boolean bAccFlag = false; //团账户标志
        boolean tRegWay = false; //录入标志,判断是按个人或团体录入的赔付还是按录入的明细赔付；
        double cAccFee=0;//帐单明细记录
        String accidentDate = "";
        String tPol = pLCGetSchema.getPolNo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询LCInsureAcc保险帐户表中的所有数据
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
//        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
//        tLCInsureAccDB.setPolNo(tPol);
//        LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.query();
//
//        if (tLCInsureAccSet == null || tLCInsureAccSet.size() == 0) {
//            this.mErrors.copyAllErrors(tLCInsureAccDB.mErrors);
//            CError tError = new CError();
//            tError.moduleName = "LLClaimCalAutoBL";
//            tError.functionName = "";
//            tError.errorMessage = "没有查询到帐户信息,理算发生错误!";
//            this.mErrors.addOneError(tError);
//            return 0;
//        }

        //得到理赔赔付的金额（与账户余额比较之前）
        //No.1 查询相关的标志
        //查询LLRegister中团体账户使用的标志
        String AccFlagSQL = "select rgtobj,accidentdate from llregister where rgtno='" +
                            tClmNo +
                            "'";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS = tExeSQL.execSQL(AccFlagSQL);
        if (tSSRS.getMaxRow() > 0 && tSSRS != null) {
            String tAccFlag[] = tSSRS.getRowData(1);
            logger.debug("是否使用团体账户：[" + tAccFlag[0] + "]");
            if (tAccFlag[0].equals("10")) {
                bAccFlag = true;
            }
            if(!tAccFlag[1].equals(""))
            {
               accidentDate = tAccFlag[1];
            }
        } else {
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getInsureAcc";
            tError.errorMessage = "没有相关的报案信息,匹配发生错误!";
            this.mErrors.addOneError(tError);
            return 0;
        }
        //查询LLClaimAccount中关于LCGet的记录
        String LLClaimAccountSQL = "select * from llclaimaccount where clmno='" +
                                   tClmNo + "' and Othertype in('S','P') and DeclineNo='"+pLCGetSchema.getInsuredNo()+"'";
//                                   payplancode in (select payplancode from lcprem where grpcontno='" +
//                                   pLCGetSchema.getGrpContNo() +
//                                   "' and contno='" +
//                                   pLCGetSchema.getContNo() + "' and polno='" +
//                                   pLCGetSchema.getPolNo() + "' and dutycode='" +
//                                   pLCGetSchema.getDutyCode() +
//                                   "')
        LLClaimAccountSet rLLClaimAccountSet = new LLClaimAccountSet(); //结果集
        LLClaimAccountDB tLLClaimAccountDB = new LLClaimAccountDB();
        rLLClaimAccountSet = tLLClaimAccountDB.executeQuery(LLClaimAccountSQL);
        if (rLLClaimAccountSet.size() <= 0) {
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getInsureAcc";
            tError.errorMessage = "没有相关的账户明细信息,"+pLCGetSchema.getDutyCode().substring(0,3)+"该责任将不进行计算!";
            this.mErrors.addOneError(tError);
            return 0;
        }

        //查询帐单明细的记录
        int ReceiptSize = 0; //帐单明细的个数
        String ReceiptSQL = "select * from llcasereceipt where clmno='" + tClmNo +
                            "' and caseno='" + tClmNo +
                            "' and  customerno='" + pLCGetSchema.getInsuredNo() +
                            "' and mainfeeno = '0000000000' " ;
        LLCaseReceiptSet rLLCaseReceiptSet = new LLCaseReceiptSet(); //结果集
        LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
        rLLCaseReceiptSet = tLLCaseReceiptDB.executeQuery(ReceiptSQL);

        /*String Receipt2SQL = "select * from llcasereceipt where clmno='" + tClmNo +
                            "' and caseno='" + tClmNo +
                            "' and  customerno='" + pLCGetSchema.getInsuredNo() +
                            "' and mainfeeno <> '0000000000' " ;
        LLCaseReceiptSet tLLCaseReceiptSet = new LLCaseReceiptSet(); //结果集
        LLCaseReceiptDB pLLCaseReceiptDB = new LLCaseReceiptDB();
        tLLCaseReceiptSet = pLLCaseReceiptDB.executeQuery(Receipt2SQL);
        logger.debug("tLLCaseReceiptSet.size():"+tLLCaseReceiptSet.size());
        && tLLCaseReceiptSet.size()==0*/
        if (rLLCaseReceiptSet.size() > 0 ) { //llcasereceipt中只有一条mainfeeno = '0000000000'的数据时说明是按团体或个人录入的金额赔付
            tRegWay = true;
        }


        //No.2 录入金额
        for (int rindexAcc = 1; rindexAcc <= rLLClaimAccountSet.size();
                             rindexAcc++) {
              boolean tCyc = false; //循环记录标志
              double tReslut = 0; //金额
            //团体赔付金额
            if (bAccFlag == true &&
                rLLClaimAccountSet.get(rindexAcc).getOtherType().equals("P") &&
                tRegWay == true&&tCyc == false) {
                double AmntPAcc = 0;
                AmntPAcc = rLLClaimAccountSet.get(rindexAcc).getAccPayMoney();
                tReslut = tReslut + AmntPAcc;
                tFlag="00";
                tCyc = true;
            }

            //个人赔付金额
            if (rLLClaimAccountSet.get(rindexAcc).getOtherType().equals("S") &&
                tRegWay == true&&tCyc == false) {
                double AmntSAcc = 0;
                AmntSAcc = rLLClaimAccountSet.get(rindexAcc).getAccPayMoney();
                tReslut = tReslut + AmntSAcc;
                tFlag="01";
                tCyc = true;
            }
            //明细录入团体金额
            if (bAccFlag == true &&
                rLLClaimAccountSet.get(rindexAcc).getOtherType().equals("P") &&
                tRegWay == false&&tCyc == false) {
                cAccFee = getReceiptFee(pLCGetSchema.getInsuredNo());
                tReslut = tReslut+cAccFee;
                tFlag= "10";
                tCyc = true;
            }
            //明细录入个人金额
            if (rLLClaimAccountSet.get(rindexAcc).getOtherType().equals("S") &&
                tRegWay == false&&tCyc == false) {
                cAccFee = getReceiptFee(pLCGetSchema.getInsuredNo());
                tReslut = tReslut+cAccFee;
                tFlag= "11";
               tCyc = true;
            }
            String tempGetDutyCode="";
            if(tFlag.equals("10")||tFlag.equals("00")){
                    //查询公共帐户赔付比例
                    String tSql =
                            "select GetDutyCode from LCGet where polno=(select polno from lcpol " +
                            "where poltypeflag='2' and grpcontno='" +
                            mLLToClaimDutySchema.getGrpContNo() +
                            "' and RiskCode ='"+mLLToClaimDutySchema.getRiskCode()+"' )";
                    // and dutycode='"+mLLToClaimDutySchema.getDutyCode()+
                    //    "' and getdutycode='"+mLLToClaimDutySchema.getGetDutyCode()+"'";

                    String tReturn = tExeSQL.getOneValue(tSql);

                    if (tReturn != null && tReturn.length() != 0) {
                        tempGetDutyCode = tReturn;
                    }else{
                        tempGetDutyCode= pLCGetSchema.getGetDutyCode();
                    }
            }else{
                tempGetDutyCode= pLCGetSchema.getGetDutyCode();
            }

        logger.debug("计算传入参数  金额["+tReslut+"]  标志["+tFlag+"]  合同号["+pLCGetSchema.getContNo()+"]  出险日期["+accidentDate+"]");
        logger.debug("tempGetDutyCode ==== "+tempGetDutyCode );
        tEachMoney = CalAccFee(tReslut,tFlag,pLCGetSchema.getContNo(),accidentDate,tClmNo,tempGetDutyCode,pLCGetSchema.getInsuredNo());
        logger.debug("各账户应付金额："+tEachMoney);

        //更新LLClaimAccount表
        vLLClaimAccountSet = updateAccount(rLLClaimAccountSet.get(rindexAcc),tEachMoney,tempGetDutyCode);
        mLLClaimAccountSet.add(vLLClaimAccountSet);

        tSumMoney = tSumMoney+tEachMoney;
    }

//        //No.3
//        //帐单明细的总金额
//        if(tRegWay==false)
//        {
//         cAccFee = getReceiptFee();
//        }
//        logger.debug("明细钱数[" + cAccFee + "]");
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * No2.0 循环处理LCInsureAccClass保险帐户表中的所有数据,计算累计帐户余额,利息
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//
//        double tAccBala = 0; //保险账户余额
//        double tAccInterest = 0; //保险账户利息
//
//        double tAccTraceBala = 0; //保险账户轨迹余额
//        double tAccTraceInterest = 0; //保险账户轨迹利息
//
//        double tSumAccTraceBala = 0; //保险账户轨迹累计余额
//        double tSumAccTraceInterest = 0; //保险账户轨迹累计利息
//
//        String tRateIntv = ""; //利息结算间隔
//        double tAccRate = 0; //帐户结算利率
//
//        AccountManage tAccountManage = new AccountManage();
//
//        for (int j = 1; j <= tLCInsureAccSet.size(); j++) {
//            LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet.get(j);
//
//            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//             * No3.0 保单的结算日 早于 立案日期 , 需要计算上次结算日 到 立案时点的 利息
//             * PubFun.calInterval("2006-05-14","2007-05-12", "D") = 365
//             * 帐户现金余额[InsuAccBala] - 账户可领金额[InsuAccGetMoney]
//             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//             */
//            tAccBala = tAccBala + tLCInsureAccSchema.getInsuAccBala() -
//                       tLCInsureAccSchema.getInsuAccGetMoney();
//
//            int tDays = PubFun.calInterval(tLCInsureAccSchema.getBalaDate(),
//                                           mLLRegisterSchema.getRgtDate(), "D");
//            if (tDays >= 0) {
//                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 * No4.0 取出结算时点的帐户利率
//                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 */
//                String tSql = "select * from LMInsuAccRate where 1=1 "
//                              + " and InsuAccNo = '" +
//                              tLCInsureAccSchema.getInsuAccNo() + "'"
//                              + " and to_date('" +
//                              tLCInsureAccSchema.getBalaDate() +
//                              "','YYYY-MM-DD') = BalaDate ";
//
//                LMInsuAccRateDB tLMInsuAccRateDB = new LMInsuAccRateDB();
//                LMInsuAccRateSet tLMInsuAccRateSet = tLMInsuAccRateDB.
//                        executeQuery(tSql);
//
//                if (tLMInsuAccRateDB.mErrors.needDealError()) {
//                    this.mErrors.copyAllErrors(tLMInsuAccRateDB.mErrors);
//                    CError tError = new CError();
//                    tError.moduleName = "LLClaimCalAutoBL";
//                    tError.functionName = "";
//                    tError.errorMessage = "查询帐户利率发生错误!";
//                    logger.debug(
//                            "------------------------------------------------------");
//                    logger.debug(
//                            "--LLClaimCalMatchBL.getInsureAcc()--查询帐户利率发生错误!" +
//                            tSql);
//                    logger.debug(
//                            "------------------------------------------------------");
//                    this.mErrors.addOneError(tError);
//                }
//
//                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 * No4.2 取出结算时点的帐户利率
//                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 */
//                if (tLMInsuAccRateSet.size() == 0) {
//                    tRateIntv = BqCode.Acc_EnsuredRateIntv;
//                    tAccRate = BqCode.Acc_EnsuredRate;
//                } else {
//                    tRateIntv = tLMInsuAccRateSet.get(1).getRateIntv();
//                    tAccRate = tLMInsuAccRateSet.get(1).getRate();
//                }
//
//                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 * No4.3 取出结算时点以后的轨迹记录
//                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 */
//
//                tSql = "select * from LCInsureAccTrace where 1=1 "
//                       + " and PolNo = '" + tLCInsureAccSchema.getPolNo() + "'"
//                       + " and InsuAccNo = '" + tLCInsureAccSchema.getInsuAccNo() +
//                       "'"
//                       + " and MoneyType in ('BF') "
//                       + " and to_date('" + tLCInsureAccSchema.getBalaDate() +
//                       "','YYYY-MM-DD') < MakeDate ";
//
//                LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
//                LCInsureAccTraceSet tLCInsureAccTraceSet = tLCInsureAccTraceDB.
//                        executeQuery(tSql);
//                if (tLCInsureAccTraceDB.mErrors.needDealError()) {
//                    this.mErrors.copyAllErrors(tLCInsureAccTraceDB.mErrors);
//                    CError tError = new CError();
//                    tError.moduleName = "LLClaimCalAutoBL";
//                    tError.functionName = "getPolPay";
//                    tError.errorMessage = "查询保险帐户表记价履历信息错误!";
//                    logger.debug(
//                            "------------------------------------------------------");
//                    logger.debug(
//                            "--LLClaimCalMatchBL.getInsureAcc()--查询保险帐户表记价履历信息错误!" +
//                            tSql);
//                    logger.debug(
//                            "------------------------------------------------------");
//                    this.mErrors.addOneError(tError);
//                    return 0;
//                }
//
//                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 * No4.5 取出结算时点以后的轨迹本金,利息
//                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 */
//                for (int m = 1; m <= tLCInsureAccTraceSet.size(); m++) {
//                    //轨迹金额
//                    tAccTraceBala = tLCInsureAccTraceSet.get(m).getMoney();
//
//                    //轨迹利息
//                    tAccTraceInterest = tAccountManage.getAccInterest(
//                            tLCInsureAccSchema.getInsuAccNo(),
//                            tRateIntv,
//                            tAccRate,
//                            tAccTraceBala,
//                            tLCInsureAccSchema.getBalaDate(),
//                            mLLRegisterSchema.getRgtDate(),
//                            "D");
//                    //保险账户轨迹累计利息
//                    tSumAccTraceInterest = tSumAccTraceInterest +
//                                           tAccTraceInterest;
//
//                    //保险账户轨迹累计余额
//                    tSumAccTraceBala = tSumAccTraceBala + tAccTraceBala;
//                }
//
//                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 * No4.6 对 总金额 - 取出结算时点以后的轨迹本金 的部分进行结息
//                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 */
//
//
//                //轨迹利息
//                tAccInterest = tAccountManage.getAccInterest(
//                        tLCInsureAccSchema.getInsuAccNo(),
//                        tRateIntv,
//                        tAccRate,
//                        tAccBala - tSumAccTraceBala,
//                        tLCInsureAccSchema.getBalaDate(),
//                        mLLRegisterSchema.getRgtDate(),
//                        "D");
//
//                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 * No4.6 对 总金额 - 取出结算时点以后的轨迹本金 的部分进行结息
//                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//                 */
//            } //if
//
//            tSumMoney = tAccBala + Arith.round(tAccInterest, 2) +
//                        tSumAccTraceBala;
//        } //for

        return tSumMoney;

    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－进行匹配后保项的添加－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－得到医疗单证信息－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    private boolean getLLToClaimDutyFee(String pClaimType,String tCustomerNo) {

        //当理赔类型为100意外医疗，200疾病医疗
        if (pClaimType.equals("100") || pClaimType.equals("200") 
        		//|| pClaimType.equals("204")
        		)
        {
            //医疗费用明细
            if (!getLLCaseReceiptInfo(pClaimType,tCustomerNo)) {
                return false;
            }

            //其它录入要素表,特种费用
            if (!getLLOtherFactorInfo("T",tCustomerNo)) {
                return false;
            }

        }

        //当理赔类型为101意外伤残，201伤残医疗
        if (pClaimType.equals("101") || pClaimType.equals("201")||pClaimType.equals("103") || pClaimType.equals("203")) {
    		    //分案疾病伤残明细
            if (!getLLCaseInfo(tCustomerNo)) {
                return false;
            }
        }

        //当理赔类型为100意外特种疾病，200疾病特种疾病
//        if (pClaimType.equals("105") || pClaimType.equals("205"))
//        {
//            //手术信息表,D--手术,E--疾病
//            if (!getLLOperationInfo("D"))
//            {
//                return false;
//            }
//
//            //手术信息表,D--手术,E--疾病
//            if (!getLLOperationInfo("E"))
//            {
//                return false;
//            }
//
//            //手术信息表,D--手术,E--疾病
//            if (!getLLOperationInfo("F"))
//            {
//                return false;
//            }
//
//        }

        return true;
    }


    /**
     * 目的：获取LLCaseReceipt门诊，住院医疗费用明细表信息
     * @return
     */
    private boolean getLLCaseReceiptInfo(String pClaimType,String tCustomerNo) {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 根据给付责任编码和理赔类型
         *  在对应的LMDutyGetFeeRela费用表中找到符合该编码条件的医疗费用项
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tGetDutyCode = mLLToClaimDutySchema.getGetDutyCode();

        LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
        tLMDutyGetFeeRelaDB.setGetDutyCode(tGetDutyCode);
        tLMDutyGetFeeRelaDB.setGetDutyKind(pClaimType);
        LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB.query();

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 循环LMDutyGetFeeRela表中的医疗费用项
         *  取出费用编码，然后在对应的医疗费用明细记录中进行查找
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i = 1; i <= tLMDutyGetFeeRelaSet.size(); i++) {
            LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema =
                    tLMDutyGetFeeRelaSet.get(i);
            String tClaimFeeCode = tLMDutyGetFeeRelaSchema.getClmFeeCode();
            String tFeeItemType = tClaimFeeCode.substring(0, 1);
            String tSql = "";

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.1 如果产品中费用类型的开始头一个字符为A或B，代表需要区分门诊，住院
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            if (tFeeItemType.equals("A") || tFeeItemType.equals("B")||tFeeItemType.equals("H")) {

                tSql = "select * from LLCaseReceipt where 1 = 1"
                       + " and ClmNo = '" + this.mClmNo + "'"
                       + " and FeeItemCode like '" + tClaimFeeCode + "%'"
                       + " and MainFeeNo in (select MainFeeNo from LLFeeMain where 1 = 1 "
                       + " and ClmNo = '" + this.mClmNo + "' )"
                       //TK 2005-12-12 P.D 取到每个出险人的帐单数据
                       + " and CustomerNo = '"+tCustomerNo+"'";
                
                LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
                LLCaseReceiptSet tLLCaseReceiptSet = tLLCaseReceiptDB.executeQuery(tSql);
                logger.debug("tSql:"+tSql);
                if (tLLCaseReceiptDB.mErrors.needDealError()) {
                    // @@错误处理
        			CError.buildErr(this, "查询医疗费用明细信息失败,"+tLLCaseReceiptDB.mErrors.getLastError());

                    logger.debug(
                            "------------------------------------------------------");
                    logger.debug(
                            "--LLClaimCalMatchBL.getLLCaseReceiptInfo()--在医疗费用主信息中查询失败，不能理算!" +
                            tSql);
                    logger.debug(
                            "------------------------------------------------------");
           
                    return false;
                }
                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No3.0 循环LLCaseReceipt表中的医疗费用项
                 *
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                String tMainFeeNoNew = "";
                String tMainFeeNoOld = "";
                LLFeeMainSchema  tLLFeeMainSchema = new LLFeeMainSchema();

                for (int j = 1; j <= tLLCaseReceiptSet.size(); j++) {
                    LLCaseReceiptSchema tLLCaseReceiptSchema = tLLCaseReceiptSet.
                            get(j);

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No3.1 找出对应的费用主记录,
                     *  如果上一次帐单号和本次不一致，则需要重新进行查询
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
                    tMainFeeNoNew = tLLCaseReceiptSchema.getMainFeeNo();

                    if (!tMainFeeNoNew.equals(tMainFeeNoOld)) {
                        tSql = "select * from LLFeeMain where 1 = 1 "
                               + " and ClmNo = '" + this.mClmNo + "'"
                               + " and MainFeeNo = '" + tMainFeeNoNew + "'";

                        LLFeeMainDB tLLFeeMainDB = new LLFeeMainDB();
                        LLFeeMainSet tLLFeeMainSet = tLLFeeMainDB.executeQuery(tSql);

                        if (tLLFeeMainSet.size() > 0) {
                            tLLFeeMainSchema = tLLFeeMainSet.get(1);

                        } else {
                            // @@错误处理
                            this.mErrors.copyAllErrors(tLLFeeMainDB.mErrors);
                            CError tError = new CError();
                            tError.moduleName = "LLClaimCalMatchBL";
                            tError.functionName = "getLLCaseReceiptInfo";
                            tError.errorMessage = "在医疗费用主信息中没有费用主记录，不能理算!";
                            logger.debug(
                                    "------------------------------------------------------");
                            logger.debug(
                                    "--LLClaimCalMatchBL.getLLCaseReceiptInfo()--在医疗费用主信息中没有费用主记录，不能理算!" +
                                    tSql);
                            logger.debug(
                                    "------------------------------------------------------");
                            this.mErrors.addOneError(tError);
                            return false;
                        }
                   }

                    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     * No3.2 加入到LLToClaimDutyFee中
                     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                     */
        			double tRealAdjSum=0.0;//账单金额-自费/自付金额后的实际参加理算的金额
    				tRealAdjSum=tLLCaseReceiptSchema.getAdjSum()-tLLCaseReceiptSchema.getSelfAmnt();
    				logger.debug("clmno(赔案):"+this.mClmNo+",FEEDETAILNO(账单明细号):"+tLLCaseReceiptSchema.getFeeDetailNo()
    						 		+",adjSum(待理算金额):"+tLLCaseReceiptSchema.getAdjSum()+",selfAmnt(自费/自付金额):"+tLLCaseReceiptSchema.getSelfAmnt()
    						 		+",realAdjSum(减去自费/自付金额后的实际参与计算的理赔金额):"+tRealAdjSum);
    	
    				
                    mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();

                    mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); //赔案号
                    mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); //分案号
                    mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema.getPolNo()); //保单号

                    mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema.
                            getGetDutyKind()); //给付责任类型
                    mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema.
                            getGetDutyCode()); //给付责任编码

                    mLLToClaimDutyFeeSchema.setDutyFeeType(tLLCaseReceiptSchema.
                            getFeeItemType()); //费用类型
                    mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLCaseReceiptSchema.
                            getFeeItemCode()); //费用代码
                    mLLToClaimDutyFeeSchema.setDutyFeeName(tLLCaseReceiptSchema.
                            getFeeItemName()); //费用名称

                    mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLCaseReceiptSchema.
                            getFeeDetailNo()); //账单费用明细序号

                    mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema.
                            getKindCode()); //险类代码
                    mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema.
                            getRiskCode()); //险种代码
                    mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema.
                            getRiskVer()); //险种版本号
                    mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema.
                            getPolMngCom()); //保单管理机构

                    mLLToClaimDutyFeeSchema.setHosID(tLLFeeMainSchema.
                                                     getHospitalCode()); //医院编号
                    mLLToClaimDutyFeeSchema.setHosName(tLLFeeMainSchema.
                            getHospitalName()); //医院名称
                    mLLToClaimDutyFeeSchema.setHosGrade(tLLFeeMainSchema.
                            getHospitalGrade()); //医院等级

                    mLLToClaimDutyFeeSchema.setStartDate(tLLCaseReceiptSchema.
                            getStartDate()); //开始时间
                    mLLToClaimDutyFeeSchema.setEndDate(tLLCaseReceiptSchema.
                            getEndDate()); //结束时间
                    mLLToClaimDutyFeeSchema.setDayCount(tLLCaseReceiptSchema.
                            getDayCount()); //天数

                    //mLLToClaimDutyFeeSchema.setDefoRate() //残疾给付比例
                    //mLLToClaimDutyFeeSchema.setRealRate()   //实际给付比例

//                    String tDutyFeeCode = mLLToClaimDutyFeeSchema.getDutyFeeCode().
//                                          substring(0, 2);
//                    if (tDutyFeeCode.endsWith("CO")) {
//                        mLLToClaimDutyFeeSchema.setAdjSum("0"); //调整金额
//                        mLLToClaimDutyFeeSchema.setCalSum("0"); //理算金额
    //
//                    } else {

    				if(tLLCaseReceiptSchema.getCurrency().equals(mLLToClaimDutySchema.getCurrency()))
					{
						mLLToClaimDutyFeeSchema.setOriSum(tLLCaseReceiptSchema.getFee()); // 原始金额
						//String tDutyFeeCode = mLLToClaimDutyFeeSchema.getDutyFeeCode().substring(0, 2);

						mLLToClaimDutyFeeSchema.setAdjSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
						mLLToClaimDutyFeeSchema.setCalSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
					}						
					else
					{
						mLLToClaimDutyFeeSchema.setOriSum(mLDExch.toOtherCur(tLLCaseReceiptSchema.getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tLLCaseReceiptSchema.getFee())); // 原始金额
						mLLToClaimDutyFeeSchema.setAdjSum(mLDExch.toOtherCur(tLLCaseReceiptSchema.getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
						mLLToClaimDutyFeeSchema.setCalSum(mLDExch.toOtherCur(tLLCaseReceiptSchema.getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
					}					
					
					mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());
                  //  }

                    //mLLToClaimDutyFeeSchema.setCustomerNo(tLLFeeMainSchema.getCustomerNo());//出险人编码
                    mLLToClaimDutyFeeSchema.setCustomerNo(tLLCaseReceiptSchema.getCustomerNo());//出险人编码
                    mLLToClaimDutyFeeSchema.setFeeItemType(tLLCaseReceiptSchema.getFeeItemType());//帐单类型 A门诊 B住院
                    //logger.debug("出险人编码 =========== "+ tLLFeeMainSchema.getCustomerNo());
                    logger.debug("出险人编码 =========== "+ tLLCaseReceiptSchema.getCustomerNo());
                    logger.debug("帐单类型   =========== "+ tLLCaseReceiptSchema.getFeeItemType());

                    mLLToClaimDutyFeeSchema.setAdjReason(tLLCaseReceiptSchema.
                            getAdjReason()); //调整原因
                    mLLToClaimDutyFeeSchema.setAdjRemark(tLLCaseReceiptSchema.
                            getAdjRemark()); //调整备注
                    mLLToClaimDutyFeeSchema.setCutApartAmnt(tLLCaseReceiptSchema.
                            getCutApartAmnt()); //分割单受益金额


                    mLLToClaimDutyFeeSchema.setOutDutyAmnt(0); //免赔额
                    mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema.
                            getDutyCode());
                    /* 做过续期抽档之后,把原号保存起来,用于后续业务 */
                    mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);

                    mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);

                    tMainFeeNoOld = tMainFeeNoNew;

                } //内层FOR循环
            }


            //logger.debug("---符合匹配责任的医疗费用数目为：["+tLLCaseReceiptSet.size()+"]条---正确的医疗帐单编码:["+tClaimFeeCode+"]");
        } //外层FOR循环


        return true;
    }


    /**
     * 目的：获取LLCaseInfo分案疾病伤残明细信息
     * @return
     */
    private boolean getLLCaseInfo(String pCustomerNo) {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 根据给付责任编码和理赔类型
         *  在对应的费用表中找到符合该编码条件的医疗费用项
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLCaseInfoDB tLLCaseInfoDB = new LLCaseInfoDB();
        tLLCaseInfoDB.setClmNo(this.mClmNo);
        tLLCaseInfoDB.setCustomerNo(pCustomerNo);
        
        LLCaseInfoSet tLLCaseInfoSet = tLLCaseInfoDB.query();
        
		LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB=null;
		LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema=null;


        for (int i = 1; i <= tLLCaseInfoSet.size(); i++) {

        	/**
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No2.0
			 * 判断产品定义中是否定义了该费用信息
			 * 
			 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
			 */
			String tGetDutyKind = mLLToClaimDutySchema.getGetDutyKind();
			String tGetDutyCode = mLLToClaimDutySchema.getGetDutyCode();
			String tDutyFeeCode = tLLCaseInfoSet.get(i).getDefoType();

			tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
			tLMDutyGetFeeRelaDB.setGetDutyKind(tGetDutyKind);
			tLMDutyGetFeeRelaDB.setGetDutyCode(tGetDutyCode);
			tLMDutyGetFeeRelaDB.setClmFeeCode(tDutyFeeCode);

			LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB
					.query();
			
			if (tLMDutyGetFeeRelaSet.size() == 1) {
				   mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();
		            LLCaseInfoSchema tLLCaseInfoSchema = tLLCaseInfoSet.get(i);

		            mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); //赔案号
		            mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); //分案号
		            mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema.getPolNo()); //保单号

		            mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema.
		                    getGetDutyKind()); //给付责任类型
		            mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema.
		                    getGetDutyCode()); //给付责任编码

		            mLLToClaimDutyFeeSchema.setDutyFeeType("L"); //费用类型为伤残
		            mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLCaseInfoSchema.
		                    getDefoCode()); //费用代码
		            mLLToClaimDutyFeeSchema.setDutyFeeName(tLLCaseInfoSchema.
		                    getDefoName()); //费用名称

		            mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLCaseInfoSchema.
		                    getSerialNo()); //账单费用明细序号

		            mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema.
		                                                getKindCode()); //险类代码
		            mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema.
		                                                getRiskCode()); //险种代码
		            mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema.getRiskVer()); //险种版本号
		            mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema.
		                                                 getPolMngCom()); //保单管理机构
		            mLLToClaimDutyFeeSchema.setStartDate(tLLCaseInfoSchema.getJudgeDate()); //伤残鉴定时间
		            mLLToClaimDutyFeeSchema.setEndDate(tLLCaseInfoSchema.getJudgeDate()); //伤残鉴定时间
		            mLLToClaimDutyFeeSchema.setCustomerNo(tLLCaseInfoSchema.getCustomerNo());//出险人客户号
		            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		             * No1.1 找出伤残比例
		             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
		             */
		            String tDefoType = tLLCaseInfoSchema.getDefoType(); //伤残类型,1新，2旧
		            String tDefoCode = tLLCaseInfoSchema.getDefoCode();
		            String tSql = "select * from LLParaDeformity where 1 = 1"
		                          + " and DefoType = '" + tDefoType + "'"
		                          + " and DefoCode = '" + tDefoCode + "'";

		            LLParaDeformityDB tLLParaDeformityDB = new LLParaDeformityDB();
		            LLParaDeformitySet tLLParaDeformitySet = tLLParaDeformityDB.
		                    executeQuery(tSql);

		            if (tLLParaDeformitySet == null || tLLParaDeformitySet.size() == 0) {
		                // @@错误处理
		                this.mErrors.copyAllErrors(tLLParaDeformityDB.mErrors);
		                CError tError = new CError();
		                tError.moduleName = "LLClaimCalMatchBL";
		                tError.functionName = "getLLCaseInfo";
		                tError.errorMessage = "没有查找到伤残比例，不能理算!";
		                logger.debug(
		                        "------------------------------------------------------");
		                logger.debug(
		                        "--LLClaimCalMatchBL.getLLCaseInfo()--在LLParaDeformity表中没有查找到伤残比例!" +
		                        tSql);
		                logger.debug(
		                        "------------------------------------------------------");
		                this.mErrors.addOneError(tError);
		                return false;
		            }

		            LLParaDeformitySchema tLLParaDeformitySchema = tLLParaDeformitySet.
		                    get(1);

		            mLLToClaimDutyFeeSchema.setDefoType(tDefoType); //伤残类型
		            mLLToClaimDutyFeeSchema.setDefoCode(tDefoCode); //伤残代码
//		            mLLToClaimDutyFeeSchema.setDefoeName(tLLParaDeformitySchema.
//		                                                 getDefoName()); //伤残级别名称

		            mLLToClaimDutyFeeSchema.setDefoRate(tLLParaDeformitySchema.
		                                                getDefoRate()); //残疾给付比例
		            mLLToClaimDutyFeeSchema.setRealRate(tLLParaDeformitySchema.
		                                                getDefoRate()); //实际给付比例

		            mLLToClaimDutyFeeSchema.setAdjReason(tLLCaseInfoSchema.getAdjReason()); //调整原因
		            mLLToClaimDutyFeeSchema.setAdjRemark(tLLCaseInfoSchema.getAdjRemark()); //调整备注

		            mLLToClaimDutyFeeSchema.setOutDutyAmnt(0);
		            mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema.
		                                                getDutyCode());
		            mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());

		            /* 做过续期抽档之后,把原号保存起来,用于后续业务 */
		            mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);

		            mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);
		            
		    		mLLToClaimDutyFeeSchema=null;
			}
         
        }
        
		
		//释放强引用
		tLLCaseInfoSet=null;
		tLLCaseInfoDB=null;

        return true;
    }


    /**
     * 目的：获取LLOperation手术信息表信息
     * @return
     */
    private boolean getLLOperationInfo(String pType) {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 找出手术和特殊疾病信息
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        LLOperationDB tLLOperationDB = new LLOperationDB();
        tLLOperationDB.setClmNo(this.mClmNo);
        tLLOperationDB.setOperationType(pType);
        LLOperationSet tLLOperationSet = tLLOperationDB.query();

        for (int i = 1; i <= tLLOperationSet.size(); i++) {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.0 判断产品定义中是否定义了该费用信息
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tGetDutyKind = mLLToClaimDutySchema.getGetDutyKind();
            String tGetDutyCode = mLLToClaimDutySchema.getGetDutyCode();
            String tDutyFeeCode = tLLOperationSet.get(i).getOperationCode();

            LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
            tLMDutyGetFeeRelaDB.setGetDutyKind(tGetDutyKind);
            tLMDutyGetFeeRelaDB.setGetDutyCode(tGetDutyCode);
            tLMDutyGetFeeRelaDB.setClmFeeCode(tDutyFeeCode);

            LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB.
                    query();
            if (tLMDutyGetFeeRelaSet.size() == 1) {
                LMDutyGetFeeRelaSchema tLMDutyGetFeeRelaSchema =
                        tLMDutyGetFeeRelaSet.get(1);

                mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();
                LLOperationSchema tLLOperationSchema = tLLOperationSet.get(i);

                mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); //赔案号
                mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); //分案号
                mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema.getPolNo()); //保单号

                mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema.
                        getGetDutyKind()); //给付责任类型
                mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema.
                        getGetDutyCode()); //给付责任编码

                mLLToClaimDutyFeeSchema.setDutyFeeType(pType); //费用类型为伤残
                mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLOperationSchema.
                        getOperationCode()); //费用代码
                mLLToClaimDutyFeeSchema.setDutyFeeName(tLLOperationSchema.
                        getOperationName()); //费用名称

                mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLOperationSchema.
                        getSerialNo()); //账单费用明细序号

                mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema.
                        getKindCode()); //险类代码
                mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema.
                        getRiskCode()); //险种代码
                mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema.
                        getRiskVer()); //险种版本号
                mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema.
                        getPolMngCom()); //保单管理机构

                mLLToClaimDutyFeeSchema.setOutDutyAmnt(0);
                mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema.
                        getDutyCode());
                mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());

                mLLToClaimDutyFeeSchema.setAdjReason(tLLOperationSchema.
                        getAdjReason()); //调整原因
                mLLToClaimDutyFeeSchema.setAdjRemark(tLLOperationSchema.
                        getAdjRemark()); //调整备注

                /* 做过续期抽档之后,把原号保存起来,用于后续业务 */
                mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);

                mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);

            }

        }

        return true;
    }


    /**
     * 目的：获取LLOtherFactor其它录入要素表信息
     * @return
     */
    private boolean getLLOtherFactorInfo(String pType,String pCustomerNo) {
        LLOtherFactorDB tLLOtherFactorDB = new LLOtherFactorDB();
        tLLOtherFactorDB.setClmNo(this.mClmNo);
        tLLOtherFactorDB.setCustomerNo(pCustomerNo);
        
		String tSql = "select * from LLOtherFactor where ClmNo='"
			+ this.mClmNo + "'" + " and Feeitemtype='T'";

        LLOtherFactorSet tLLOtherFactorSet = tLLOtherFactorDB.executeQuery(tSql);
        
		logger.debug("--LLClaimCalMatchBL.java:查询录入的特种费用(如救援费用)的sql:"+tSql);
		
		double tRealAdjSum=0.0;//重置初始化金额

        for (int i = 1; i <= tLLOtherFactorSet.size(); i++) {
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.0 判断产品定义中是否定义了该费用信息
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */

            LMDutyGetFeeRelaDB tLMDutyGetFeeRelaDB = new LMDutyGetFeeRelaDB();
            tLMDutyGetFeeRelaDB.setGetDutyKind(mLLToClaimDutySchema.getGetDutyKind());
            tLMDutyGetFeeRelaDB.setGetDutyCode(mLLToClaimDutySchema.getGetDutyCode());
            tLMDutyGetFeeRelaDB.setClmFeeCode(tLLOtherFactorSet.get(i).getFactorType());

            LMDutyGetFeeRelaSet tLMDutyGetFeeRelaSet = tLMDutyGetFeeRelaDB.
                    query();
            if (tLMDutyGetFeeRelaSet.size() == 1) {
                mLLToClaimDutyFeeSchema = new LLToClaimDutyFeeSchema();
                LLOtherFactorSchema tLLOtherFactorSchema = tLLOtherFactorSet.
                        get(i);
                
            	
				tRealAdjSum=tLLOtherFactorSet.get(i).getAdjSum()-tLLOtherFactorSet.get(i).getSelfAmnt();
				logger.debug("clmno(赔案):"+this.mClmNo+",FactorCode(费用代码):"+tLLOtherFactorSet.get(i).getFactorCode()
						 		+",adjSum(待理算金额):"+tLLOtherFactorSet.get(i).getAdjSum()+",selfAmnt(自费/自付金额):"+tLLOtherFactorSet.get(i).getSelfAmnt()
						 		+",realAdjSum(减去自费/自付金额后的实际参与计算的理赔金额):"+tRealAdjSum);
			

                mLLToClaimDutyFeeSchema.setClmNo(this.mClmNo); //赔案号
                mLLToClaimDutyFeeSchema.setCaseNo(this.mClmNo); //分案号
                mLLToClaimDutyFeeSchema.setPolNo(mLLToClaimDutySchema.getPolNo()); //保单号

                mLLToClaimDutyFeeSchema.setGetDutyType(mLLToClaimDutySchema.
                        getGetDutyKind()); //给付责任类型
                mLLToClaimDutyFeeSchema.setGetDutyCode(mLLToClaimDutySchema.
                        getGetDutyCode()); //给付责任编码

                mLLToClaimDutyFeeSchema.setDutyFeeType(pType); //费用类型为伤残
                mLLToClaimDutyFeeSchema.setDutyFeeCode(tLLOtherFactorSchema.
                        getFactorCode()); //费用代码
                mLLToClaimDutyFeeSchema.setDutyFeeName(tLLOtherFactorSchema.
                        getFactorName()); //费用名称

                mLLToClaimDutyFeeSchema.setDutyFeeStaNo(tLLOtherFactorSchema.
                        getSerialNo()); //账单费用明细序号

                mLLToClaimDutyFeeSchema.setKindCode(mLLToClaimDutySchema.
                        getKindCode()); //险类代码
                mLLToClaimDutyFeeSchema.setRiskCode(mLLToClaimDutySchema.
                        getRiskCode()); //险种代码
                mLLToClaimDutyFeeSchema.setRiskVer(mLLToClaimDutySchema.
                        getRiskVer()); //险种版本号
                mLLToClaimDutyFeeSchema.setPolMngCom(mLLToClaimDutySchema.
                        getPolMngCom()); //保单管理机构
                    		
				if(tLLOtherFactorSet.get(i).getCurrency().equals(mLLToClaimDutySchema.getCurrency()))
				{
					mLLToClaimDutyFeeSchema.setAdjSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
					mLLToClaimDutyFeeSchema.setCalSum(tRealAdjSum);// 减去自费/自付金额后的实际参与计算的理赔金额
				}
				else
				{
					mLLToClaimDutyFeeSchema.setAdjSum(mLDExch.toOtherCur(tLLOtherFactorSet.get(i).getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
					mLLToClaimDutyFeeSchema.setCalSum(mLDExch.toOtherCur(tLLOtherFactorSet.get(i).getCurrency(),mLLToClaimDutySchema.getCurrency(),PubFun.getCurrentDate(),tRealAdjSum));// 减去自费/自付金额后的实际参与计算的理赔金额
				}				
				mLLToClaimDutyFeeSchema.setCurrency(mLLToClaimDutySchema.getCurrency());

                mLLToClaimDutyFeeSchema.setAdjReason(tLLOtherFactorSchema.
                        getAdjReason()); //调整原因
                mLLToClaimDutyFeeSchema.setAdjRemark(tLLOtherFactorSchema.
                        getAdjRemark()); //调整备注
                mLLToClaimDutyFeeSchema.setCustomerNo(tLLOtherFactorSchema.getCustomerNo());//出险人客户号
                mLLToClaimDutyFeeSchema.setOutDutyAmnt(0);
                mLLToClaimDutyFeeSchema.setDutyCode(mLLToClaimDutySchema.
                        getDutyCode());
                /* 做过续期抽档之后,把原号保存起来,用于后续业务 */
                mLLToClaimDutyFeeSchema.setNBPolNo(this.mNBPolNo);

                mLLToClaimDutyFeeSet.add(mLLToClaimDutyFeeSchema);
            }

        }

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－得到医疗单证信息－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */





    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData() {
        /****************/
        mMMap.put(mLLToClaimDutyFeeSet, "INSERT");
        mMMap.put(mLLToClaimDutySet, "INSERT");
        mResult.add(mMMap);
        mResult.add(mLLClaimAccountSet);
        /****************/
        mResult.add(mLLToClaimDutyFeeSet);
        mResult.add(mLLToClaimDutySet);
        mResult.add(this.mTransferData);
        mResult.add(this.mGlobalInput);

        mInputData.clear();
        mInputData.add(mMMap);
        return true;
    }


    /**
     * 提交数据
     * @return
     */
    private boolean pubSubmit() {
        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchFilterBL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }


    /**
     * 得到返回的结果集
     * @return
     */
    public VData getResult() {
        return mResult;
    }


    /**
     * 测试主方法
     * @param args
     */
    public static void main(String[] args) {

        GlobalInput tG = new GlobalInput();
        tG.Operator = "001";
        tG.ManageCom = "86000000";
        tG.ComCode = "86110000";

        LLReportSchema tLLReportSchema = new LLReportSchema();

        String tClmNo = "90000001541"; /*赔案号90000001687*/

        String tSql = "select * from LLAccident where AccNo in "
                      + " (select CaseRelaNo from LLCaseRela where CaseNo = '" +
                      tClmNo + "')";

        LLAccidentDB tLLAccidentDB = new LLAccidentDB();
        LLAccidentSet tLLAccidentSet = tLLAccidentDB.executeQuery(tSql);

        if (tLLAccidentDB.mErrors.needDealError()) {
            logger.debug(
                    "------------------------------------------------------");
            logger.debug(
                    "--LLClaimCalMatchBL.getPosEdorNo()--获取保全处理的批改生效日期发生错误!" +
                    tSql);
            logger.debug(
                    "------------------------------------------------------");
        }

        if (tLLAccidentSet.size() == 0) {
            logger.debug(
                    "----------------------无事故信息-----------------------");
        }
        String tAccNo = tLLAccidentSet.get(1).getAccNo();
        String tAccDate = tLLAccidentSet.get(1).getAccDate().substring(0, 10);

        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        tLLRegisterDB.setRgtNo(tClmNo);
        if (tLLRegisterDB.getInfo() == false) {
            logger.debug(
                    "------------------------------------------------------");
            logger.debug("--查询立案信息失败!");
            logger.debug(
                    "------------------------------------------------------");
        }
        String tClmState = tLLRegisterDB.getClmState();
        String tRgtClass = tLLRegisterDB.getRgtClass();

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo", tAccNo);
        tTransferData.setNameAndValue("ClmNo", tClmNo);
        tTransferData.setNameAndValue("AccDate", tAccDate);
        tTransferData.setNameAndValue("ContType", tRgtClass); //总单类型,1-个人总投保单,2-集体总单
        tTransferData.setNameAndValue("ClmState", tClmState); //赔案状态，20立案，30审核

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);

        LLClaimCalMatchBL tLLClaimCalMatchBL = new LLClaimCalMatchBL();
        tLLClaimCalMatchBL.submitData(tVData, "");
        int n = tLLClaimCalMatchBL.mErrors.getErrorCount();
        logger.debug(
                "-------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            String Content = "";
            Content = Content + "原因是: " +
                      tLLClaimCalMatchBL.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        }

    }


    private double getReceiptFee(String cusNo) {
        String tSql = "";
        String tReturn = "";
        double t_Sum_Return = 0.00;
        ExeSQL tExeSQL = new ExeSQL();
        tSql = "  select sum(AdjSum) from LLCaseReceipt where clmno = '" +
               mClmNo + "' and customerno='"+cusNo+"' and mainfeeno not in ('0000000000')";
               //and  mainfeeno in (select mainfeeno from llfeemain where ClmNo=llcasereceipt.ClmNo and customerno='"+cusNo+"' and mainfeeno not in ('0000000000'))";
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到保全基本保额发生错误!";
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }
        return t_Sum_Return;
    }


    //账户金额与理算金额的比较
    /*
    *tflag  计算的标志
    *00  团报销额    01  个人报销额   10  明细团体   11  明细个人
    *集体合同号   客户号   出险日期
    */
    private double CalAccFee(double Fee,String tflag,String tContNo,String AccidentDate,String clmNo,String getKind,String InsuredNo)
    {
        double FeePara = 0;
        double PerFee = 0;
        double PubFee = 0;
        double PerGetRate=0.0;//个人帐户赔付比例，如果等于0则为1
        double PubGetRate=0.0;//公共帐户赔付比例，如果等于0则为1ss
        double MaxPayFee=0.0;//反算能赔付的最大金额
        double MaxPerPayFee = 0.0; //反算个人帐户能赔付的最大金额
        double MaxPubPayFee = 0.0; //反算公共帐户能赔付的最大金额
        String tUnifyClaimRate = "";//理赔控制帐户赔付比例

        tUnifyClaimRate = getUnifyClaimRate();//先取到该保单的理赔控制的帐户赔付比例值
        if(tUnifyClaimRate == null && tUnifyClaimRate.equals("")){//判断是否录入了理赔控制
        //查询个人帐户赔付比例
        String tSql="select GetRate from LCGet where polno='"+mLLToClaimDutySchema.getPolNo()+
                    "' and DutyCode='"+mLLToClaimDutySchema.getDutyCode()+
                    "' and GetDutyCode='"+mLLToClaimDutySchema.getGetDutyCode()+"'";

        ExeSQL tExeSQL = new ExeSQL();
        String tReturn = tExeSQL.getOneValue(tSql);

        if (tReturn != null && tReturn.length() != 0) {
            PerGetRate=Double.parseDouble(tReturn);
            if(PerGetRate<0.0003)
                PerGetRate=1.0;
        }else
        {
            PerGetRate = 1.0;
        }
        //查询公共帐户赔付比例
        tSql="select GetRate from LCGet where polno=(select polno from lcpol "+
                     "where poltypeflag='2' and grpcontno='"+mLLToClaimDutySchema.getGrpContNo()+
                     "' and RiskCode = '"+mLLToClaimDutySchema.getRiskCode()+"')";
               // and dutycode='"+mLLToClaimDutySchema.getDutyCode()+
                 //    "' and getdutycode='"+mLLToClaimDutySchema.getGetDutyCode()+"'";

        tReturn = tExeSQL.getOneValue(tSql);

        if (tReturn != null && tReturn.length() != 0) {
            PubGetRate=Double.parseDouble(tReturn);
            if(PubGetRate<0.0003)
                PubGetRate=1.0;
        }else
        {
            PubGetRate=1.0;
        }

        }else{//判断理赔控制帐户赔付比例值
            if (tUnifyClaimRate != null && tUnifyClaimRate.length() != 0) {
                PubGetRate = Double.parseDouble(tUnifyClaimRate);
                if (PubGetRate < 0.0003)
                    PubGetRate = 1.0;
                PerGetRate = 1.0;
            } else {
                PubGetRate = 1.0;
                PerGetRate = 1.0;
            }
        }
        logger.debug("========个人帐户赔付比例："+PerGetRate);
        logger.debug("========公共帐户赔付比例："+PubGetRate);

        if(tflag=="00")
        {
            PubFee = getPublicFee(AccidentDate,clmNo,getKind);
            MaxPubPayFee = PubFee / PubGetRate;
            if(MaxPubPayFee>Fee)
            {
              FeePara=Fee*PubGetRate;
            }
            else
            {
              FeePara = PubFee;
            }
        }
        if(tflag=="01")
        {
            PerFee = getPersonFee(tContNo,AccidentDate,clmNo,getKind,"Y",InsuredNo);
            MaxPerPayFee = PerFee / PerGetRate;
            if(MaxPerPayFee>Fee)
            {
              FeePara=Fee*PerGetRate;
            }
            else
            {
              FeePara = PerFee;
            }
        }
        if(tflag=="10")
        {
           PubFee = getPublicFee(AccidentDate,clmNo,getKind);
           PerFee = getPersonFee(tContNo,AccidentDate,clmNo,getKind,"N",InsuredNo);
           MaxPubPayFee = PubFee / PubGetRate;
           MaxPerPayFee = PerFee / PerGetRate;
           MaxPayFee=MaxPubPayFee+MaxPerPayFee;

           if(MaxPayFee>Fee)
           {
               if(MaxPerPayFee>Fee)
               {
                   FeePara = 0;
               }else
               {
                   FeePara = (Fee-MaxPerPayFee)*PubGetRate;
               }
           }else
           {
               FeePara = PubFee;
           }
        }
        if(tflag=="11")
        {
           PerFee = getPersonFee(tContNo,AccidentDate,clmNo,getKind,"Y",InsuredNo);
           MaxPerPayFee = PerFee / PerGetRate;
           if(Fee >MaxPerPayFee)
           {
              FeePara=PerFee;
           }
           else
           {
             FeePara = Fee*PerGetRate;
           }
        }
      return FeePara;
    }


    //帐户性理赔帐户余额金额(个人账户)
    private double getPersonFee(String tContNo, String Date, String No,
                                String getKind, String LXFlag, String InsuredNo) {
//      AccountManage mAccountManage = new AccountManage();
        double perFee = 0;
//      String LXDate="";
//      LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
//      LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
//      LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
//      String PerFeeSql="select * from lcinsureaccclass where contno='"+tContNo+"' and grpcontno=(select grpcontno from llregister where rgtno='"+No+"')";
//      tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(PerFeeSql);
//      if(tLCInsureAccClassSet.size()!=0)
//       {
//         LXDate = Date;
//         for (int i=1;i<=tLCInsureAccClassSet.size();i++)
//          {
//              LLClaimAccountSchema tLLClaimAccountSchema = new LLClaimAccountSchema();
//              double perFee1=0;
//              double perLXFee=0;
//              tLCInsureAccClassSchema = tLCInsureAccClassSet.get(i);
//              perFee1 = tLCInsureAccClassSchema.getInsuAccBala();
//              perLXFee = mAccountManage.getAccClassInterest(tLCInsureAccClassSchema,LXDate,"Y","D",2,"F","D");
//              logger.debug("个人利息:"+perLXFee);
//              if(perLXFee>=0.01&&LXFlag=="Y")
//              {
//               tLLClaimAccountSchema = getAccountLX(tLCInsureAccClassSchema,perLXFee,"S",No,getKind,LXDate);
//               mLLClaimAccountSet.add(tLLClaimAccountSchema);
//              }
//              perFee1 = perFee1+perLXFee;
//              perFee = perFee+perFee1;
//          }
//       }

        String tSql = " select InsuAccBala from lcinsureaccclass"
                      +
                " where grpcontno  = (select grpcontno from llregister where rgtno='" +
                      No + "')"
                      + " and acctype = '002'"
                      + " and insuredno = '" + InsuredNo + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String tInsuAccBala = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            CError.buildErr(this, "查询个人账户余额金额失败!" + tSql);
        }

        if (tInsuAccBala == null || tInsuAccBala.length() == 0) {
            tInsuAccBala = "0";
        }
        perFee = Double.parseDouble(tInsuAccBala);

        if (perFee <= 0.001) {
            return 0;
        }
        return perFee;
    }



//帐户性理赔帐户余额金额(公共账户)
    private double getPublicFee(String Date, String No, String getKind) {
//      AccountManage mAccountManage = new AccountManage();
        double pubFee = 0;
//      String LXDate="";
//      LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
//      LCInsureAccClassSet tLCInsureAccClassSet = new LCInsureAccClassSet();
//      LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
//      String pubFeeSql="select * from lcinsureaccclass where contno=(select contno from lcpol where grpcontno=(select grpcontno from llregister where rgtno='"+No+"') and poltypeflag='2' )";
//      tLCInsureAccClassSet = tLCInsureAccClassDB.executeQuery(pubFeeSql);
//      if(tLCInsureAccClassSet.size()!=0)
//       {
//         LXDate = Date;
//         for (int i=1;i<=tLCInsureAccClassSet.size();i++)
//          {
//              LLClaimAccountSchema tLLClaimAccountSchema = new LLClaimAccountSchema();
//              double pubFee1=0;
//              double pubLXFee=0;
//              tLCInsureAccClassSchema = tLCInsureAccClassSet.get(i);
//              pubFee1 = tLCInsureAccClassSchema.getInsuAccBala();
//              pubLXFee = mAccountManage.getAccClassInterest(tLCInsureAccClassSchema,LXDate,"Y","D",2,"F","D");
//              logger.debug("团体利息:"+pubLXFee);
//              if(pubLXFee>=0.01)
//              {
//               tLLClaimAccountSchema = getAccountLX(tLCInsureAccClassSchema,pubLXFee,"P",No,getKind,LXDate);
//               mLLClaimAccountSet.add(tLLClaimAccountSchema);
//              }
//              pubFee1 = pubFee1+pubLXFee;
//              pubFee = pubFee+pubFee1;
//          }
//       }

        String tSql = " select InsuAccBala from lcinsureaccclass"
                      +
                      " where grpcontno  = (select grpcontno from llregister where rgtno='" +
                      No + "')"
                      + " and acctype = '001' and RiskCode = '"+mLLToClaimDutySchema.getRiskCode()+"' ";
        ExeSQL tExeSQL = new ExeSQL();
        String tInsuAccBala = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            CError.buildErr(this, "查询公共账户余额金额失败!" + tSql);
        }

        if (tInsuAccBala == null || tInsuAccBala.length() == 0) {
            tInsuAccBala = "0";
        }
        pubFee = Double.parseDouble(tInsuAccBala);

        if (pubFee <= 0.001) {
            return 0;
        }
        return pubFee;
}


//利息保存表
private LLClaimAccountSchema getAccountLX(LCInsureAccClassSchema PLCInsureAccClassSchema,double LX,String flag,String No,String getKind,String LXDate)
{
    String tNFlag = "";
    if(flag=="S")
    {
     tNFlag="M";
    }
    if(flag=="P")
    {
      tNFlag="N";
    }
    LLClaimAccountSchema pLLClaimAccountSchema = new LLClaimAccountSchema();
    String SerialNo = PubFun1.CreateMaxNo("SerialNo",mGlobalInput.ManageCom);
    logger.debug("-----生成流水线号--- " + SerialNo);
    pLLClaimAccountSchema.setDeclineNo(PLCInsureAccClassSchema.getInsuredNo());
    pLLClaimAccountSchema.setOtherNo(PLCInsureAccClassSchema.getRiskCode());
    pLLClaimAccountSchema.setGrpContNo(PLCInsureAccClassSchema.getGrpContNo());
    pLLClaimAccountSchema.setPayDate(LXDate);
    pLLClaimAccountSchema.setClmNo(No);
    pLLClaimAccountSchema.setSerialNo(SerialNo);
    pLLClaimAccountSchema.setGetDutyCode(getKind); //暂存作为临时记录(团体报销额)
    pLLClaimAccountSchema.setPolNo(PLCInsureAccClassSchema.getPolNo());
    pLLClaimAccountSchema.setInsuAccNo(PLCInsureAccClassSchema.getInsuAccNo());
    pLLClaimAccountSchema.setPayPlanCode(PLCInsureAccClassSchema.getPayPlanCode());
    pLLClaimAccountSchema.setAccAscription(PLCInsureAccClassSchema.getAccAscription());
    pLLClaimAccountSchema.setAccPayMoney(LX);
    pLLClaimAccountSchema.setOtherNo(PLCInsureAccClassSchema.getRiskCode());
    pLLClaimAccountSchema.setOtherType(tNFlag);
    pLLClaimAccountSchema.setMngCom(PLCInsureAccClassSchema.getManageCom());
    pLLClaimAccountSchema.setOperator(mGlobalInput.Operator);
    pLLClaimAccountSchema.setMakeDate(CurrentDate);
    pLLClaimAccountSchema.setMakeTime(CurrentTime);
    pLLClaimAccountSchema.setModifyDate(CurrentDate);
    pLLClaimAccountSchema.setModifyTime(CurrentTime);
    return pLLClaimAccountSchema;
}


//更新LLClaimAccount
private LLClaimAccountSet updateAccount(LLClaimAccountSchema inLLClaimAccountSchema,double Money,String getkind)
{
    LLClaimAccountSet outLLClaimAccountSet = new LLClaimAccountSet();
    outLLClaimAccountSet.add(inLLClaimAccountSchema);
    for(int inAccindex=1;inAccindex<=outLLClaimAccountSet.size();inAccindex++)
    {
     outLLClaimAccountSet.get(inAccindex).setGetDutyCode(getkind);
     outLLClaimAccountSet.get(inAccindex).setAccPayMoney(Money);
     outLLClaimAccountSet.get(inAccindex).setOperator(mGlobalInput.Operator);
     outLLClaimAccountSet.get(inAccindex).setMngCom(mGlobalInput.ManageCom);
     outLLClaimAccountSet.get(inAccindex).setModifyDate(CurrentDate);
     outLLClaimAccountSet.get(inAccindex).setModifyTime(CurrentTime);
   }
   return outLLClaimAccountSet;
}

//取到险种责任中止时间
private boolean getStopDate(String tGrpContNo){
    ExeSQL tExeSQL = new ExeSQL();
    String tSql ="select count(*) from lcgrpcontstopedorstate where stopdate <= '"+this.mInsDate+"'"
                 +" and restartdate >= '"+this.mInsDate+"'"
                 +" and grpcontno = '"+tGrpContNo+"'";
    String tCount = tExeSQL.getOneValue(tSql);
    if (tExeSQL.mErrors.needDealError()) {
        this.mErrors.copyAllErrors(tExeSQL.mErrors);
        CError tError = new CError();
        tError.moduleName = "LLClaimCalMatchBL";
        tError.functionName = "getStopDate";
        tError.errorMessage = "得到责任中止时间发生错误!";
        this.mErrors.addOneError(tError);
    }
    if(!tCount.equals("0")){
          return false;
    }else{
        return true;
    }
}

//宽限期判断
private boolean getGraceperiod(String tPolno){
    ExeSQL tExeSQL = new ExeSQL();
    //判断保单交费方式，如果为趸交的话不判断
    String tSql2 = "select payintv From lcpol where polno = '"+tPolno+"'";
    String tPayintv = tExeSQL.getOneValue(tSql2);
    if (tExeSQL.mErrors.needDealError()) {
        this.mErrors.copyAllErrors(tExeSQL.mErrors);
        CError tError = new CError();
        tError.moduleName = "LLClaimCalMatchBL";
        tError.functionName = "getGraceperiod";
        tError.errorMessage = "未得到保单交费方式!";
        this.mErrors.addOneError(tError);
        }
    int  IntPayintv = Integer.parseInt(tPayintv);
    if(IntPayintv > 0 ){
        //得到交至日
        String tSql =
                "   select count(*) from lcpol,lmriskpay where lcpol.polno = '" +
                tPolno + "'"
                + " and lcpol.riskcode=lmriskpay.riskcode "
                + " and lcpol.paytodate+lmriskpay.graceperiod  >= to_date('" +
                this.mInsDate + "','yyyy-mm-dd')";
        String tCount = tExeSQL.getOneValue(tSql);
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getStopDate";
            tError.errorMessage = "未得到交至日!";
            this.mErrors.addOneError(tError);
        }
        if (tCount.equals("0")) { //等于0是证明该出险日期没有在交至日+宽限期之前
            logger.debug("tCount="+tCount+"--出险日在缴至日期后！");
            return false;
        } else {
            return true;
        }
    }else{
        return true;
    }
}

//取到理赔控制的帐户赔付比例
private String getUnifyClaimRate(){
    double dGetRate = 0;
    String tGetRate = ""; 
    
    // zhangzheng 2009-04-08 团险特约处理 统一 ，屏蔽这段逻辑
//
//    String tContType = ""; //保单险种号
//    String tContPlanCode = ""; //保单险种号
//
//    LLDutyCtrlDB mLLDutyCtrlDB2 = new LLDutyCtrlDB();
//    LLDutyCtrlSchema mLLDutyCtrlSchema2 = new LLDutyCtrlSchema();
//    String tSql =
//            " select b.conttype, c.contplancode from lcpol b, lcinsured c"
//            + " where b.polno = '" + mLCGetSchema.getPolNo() + "'"
//            + " and c.contno = b.contno ";
//    ExeSQL tExeSQL = new ExeSQL();
//    SSRS tSSRS = tExeSQL.execSQL(tSql);
//    for (int a = 1; a <= tSSRS.getMaxRow(); a++) {
//        tContType = "2"; //保单险种号
//        tContPlanCode = tSSRS.GetText(a, 2); //保单险种号
//        if (tContPlanCode == null || tContPlanCode.equals("")) {
//            tContPlanCode = "0";
//        }
//        mLLDutyCtrlDB2.setContType(tContType);
//        mLLDutyCtrlDB2.setContNo(mLCGetSchema.getGrpContNo());
//        mLLDutyCtrlDB2.setDutyCode(mLCGetSchema.getDutyCode());
//        if (tContPlanCode != null && !tContPlanCode.equals("")) {
//            mLLDutyCtrlDB2.setContPlanCode(tContPlanCode);
//        } else {
//            mLLDutyCtrlDB2.setContPlanCode("0");
//        }
//        mLLDutyCtrlDB2.setGrpContNo(mLCGetSchema.getGrpContNo());
//        if (mLLDutyCtrlDB2.getInfo()) {
//            mLLDutyCtrlSchema2 = mLLDutyCtrlDB2.getSchema();
//        } else {
//            //保障计划为0的时候理赔控制对整个团单适用
//            mLLDutyCtrlDB2.setContType(tContType);
//            mLLDutyCtrlDB2.setContNo(mLCGetSchema.getGrpContNo());
//            mLLDutyCtrlDB2.setDutyCode(mLCGetSchema.getDutyCode());
//            mLLDutyCtrlDB2.setContPlanCode("0");
//            mLLDutyCtrlDB2.setGrpContNo(mLCGetSchema.getGrpContNo());
//            if (mLLDutyCtrlDB2.getInfo()) {
//                mLLDutyCtrlSchema2 = mLLDutyCtrlDB2.getSchema();
//            }
//        }
//    }
//    //tongmeng 2009-03-18 暂时屏蔽
//    //dGetRate = mLLDutyCtrlSchema2.getUnifyClaimRate();
    tGetRate = Double.toString(dGetRate);
    return tGetRate;
}

/**
 * @param aLCInsureAccClassSchema 
 * @param aBalaDate 结算时间
 * @param aRateType 原始利率类型 
 * @param aIntvType 目标利率类型
 * 一般情况下原始利率类型、目标利率类型都为年利率
 * @return
 */
public TransferData getAccClassInterestNewMS(
		LCInsureAccClassSchema aLCInsureAccClassSchema, String aBalaDate,
		String aRateType, String aIntvType) {

	logger.debug("=====This is getAccClassInterestNew!=====\n");

	// 记录帐户分类表的利息值
	double aAccClassInterest = 0.0;

	// 记录帐户分类表的本息和
	double aAccClassSumPay = 0.0;

	ExeSQL tExeSql = new ExeSQL();
	SSRS tSSRS = new SSRS();

	String tsql = "select poltypeflag from lcpol where polno ='"
			+ aLCInsureAccClassSchema.getPolNo() + "' ";
	ExeSQL tExeSQL = new ExeSQL();
	String tpoltypeflag = tExeSQL.getOneValue(tsql);

	if (tpoltypeflag == "2" || tpoltypeflag.equals("2")) {
		logger.debug("=====poltype=" + tpoltypeflag
				+ "=====对公共账户重新结息！");
		aLCInsureAccClassSchema.setBalaDate("1900-1-1");
		aLCInsureAccClassSchema.setBalaTime("00:00:00");
		aLCInsureAccClassSchema.setInsuAccBala("0");
	}

	//MS无此字段 被屏蔽  modify by wk 2008-11-13
//	String accSql = "select count(*) from lmriskapp where riskcode ='"
//			+ aLCInsureAccClassSchema.getRiskCode()
//			+ "' and riskflagacc ='D' "; // riskflagacc ='D'
//											// 代表139，151，121，122，108险种，D类险
//	String accCount = tExeSql.getOneValue(accSql);
//
//	if (!accCount.equals("0")) {
//
//		logger.debug("=====对" + aLCInsureAccClassSchema.getRiskCode()
//				+ "账户重新结息！=====");
//		aLCInsureAccClassSchema.setBalaDate("1900-1-1");
//		aLCInsureAccClassSchema.setBalaTime("00:00:00");
//		aLCInsureAccClassSchema.setInsuAccBala("0");
//	}
	// 记录返回值利息和本息
	TransferData aAccClassRet = new TransferData();

	// 检验数据有效性
//	if (!verifyNotNull("当前结息日期", aBalaDate)) {
//		returnNull(aAccClassRet);
//		return aAccClassRet;
//	}
//	if (!verifyNotNull("原始利率类型", aRateType)) {
//		returnNull(aAccClassRet);
//		return aAccClassRet;
//	}
//	if (!verifyNotNull("目标利率类型", aIntvType)) {
//		returnNull(aAccClassRet);
//		return aAccClassRet;
//	}

	// 记录结息间隔
	int tInterval = 0;

	// 记录查询LCInsureAccClassTrace表返回有效记录的个数
	int tCount = 0;

	// 记录原始利率值
	double tAccClassRate = 0.00;

	// 记录目标利率值
	double tCalAccClassRate = 0.00;

	// 记录保险帐户现金余额
	double tInsuAccClassBala = aLCInsureAccClassSchema.getInsuAccBala();


//	if (Integer.parseInt(tLMRiskInsuAccSchema.getAccComputeFlag()) != 0) {
		aAccClassSumPay = tInsuAccClassBala ;
//				+ tInsureAccTraceMoneySum
//				+ aAccClassInterest;
//	} else {
//		aAccClassSumPay = tInsureAccTraceMoneySum;
//	}
	// ===20061018===testing start==================
	logger.debug("帐户金额： " + tInsuAccClassBala);
//	logger.debug("Trace表的缴费和： " + tInsureAccTraceMoneySum);
	logger.debug("Trace表的缴费利息和： " + aAccClassInterest);
	logger.debug("帐户实际余额： " + aAccClassSumPay);
	// ===20061018===testing end====================

	// 准备返回的数据包
	aAccClassRet.setNameAndValue("aAccClassInterest", aAccClassInterest);
	aAccClassRet.setNameAndValue("aAccClassSumPay", aAccClassSumPay);

	return aAccClassRet;
}



}
///**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
// * No4.0 对LBGet表，准备公用查询SQL
// * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
// */
//tSqlTemp = "select lbget.* from lbget,lbpol,lccontstate where 1 = 1 "
//        + " and lbget.polno=lbpol.polno "
//        + " and lccontstate.polno=lbpol.polno "
//        + " and lccontstate.StateType in ('Available') "    //Available: 0-有效 1-失效 （险种状态）
//        + " and lccontstate.StartDate<=to_date('"+this.mAccDate+ "','yyyy-mm-dd') "
//        + " and (lccontstate.enddate  >=to_date('"+this.mAccDate+ "','yyyy-mm-dd') or lccontstate.enddate is null )"
//        + " and lbget.LiveGetType = '1' "                               //生存意外给付标志,1意外
//        + " and lbpol.AppFlag  in ('1')"                                //0-投保单,1-保单
//        + " and lbpol.conttype  = '"+this.mContType+"'"                //总单类型,1-个人投保单,2-集体总投保单
//        + " and lbget.getstartdate<=to_date('"+this.mAccDate+ "','yyyy-mm-dd') "
//        + " and lbget.getenddate  > to_date('"+this.mAccDate+ "','yyyy-mm-dd') "
//        ;
//
//
///**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
// * No4.1 对LBGet表进行作为被保人的判断
// * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
// */
//tSql = tSqlTemp + " and lbpol.insuredno  in ('" + mLLCaseSchema.getCustomerNo() + "')";    //作为被保人
//
//logger.debug("-----getLBGet的被保人Sql语句-----"+tSql);
//
//
//LBGetDB tLBGetDB = new LBGetDB();
//LBGetSet tLBGetSet = tLBGetDB.executeQuery(tSql);
//if (tLBGetSet.mErrors.needDealError())
//{
//    this.mErrors.copyAllErrors(tLBGetSet.mErrors);
//    CError tError = new CError();
//    tError.moduleName = "LLClaimCalAutoBL";
//    tError.functionName = "getPolPay";
//    tError.errorMessage = "发生错误!"+tSql;
//    this.mErrors.addOneError(tError);
//    return false;
//}
//
//if (tLBGetSet != null || tLBGetSet.size() > 0 )
//{
//    /*给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人*/
//    for ( int i=1 ; i <= tLBGetSet.size() ; i++ )
//    {
//        LBGetSchema tLBGetSchema = tLBGetSet.get(i);
//        tLBGetSchema.setState("01");    //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
//        tLBGetSchema.setOperator("B");  //LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
//        tLBGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo());//LCGet表的该字段属于借用，用于存放出险人编号
//
//        //做映射，将C表数据赋值到B表
//        Reflections tR = new Reflections();
//        LCGetSchema tLCGetSchema = new LCGetSchema();
//        tR.transFields(tLCGetSchema,tLBGetSchema);
//        mLCGetSet.add(tLCGetSchema);
//
//    }
//}
//
///**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
// * No4.2 对LBGet表进行作为投保人的判断
// * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
// */
//tSql = tSqlTemp + " and lbpol.AppntNo in ('" + mLLCaseSchema.getCustomerNo() + "')";  //作为投保人
//logger.debug("-----getLBGet的投保人Sql语句-----"+tSql);
//
//tLBGetDB = new LBGetDB();
//tLBGetSet = tLBGetDB.executeQuery(tSql);
//if (tLBGetSet.mErrors.needDealError())
//{
//    this.mErrors.copyAllErrors(tLBGetSet.mErrors);
//    CError tError = new CError();
//    tError.moduleName = "LLClaimCalAutoBL";
//    tError.functionName = "getPolPay";
//    tError.errorMessage = "发生错误!"+tSql;
//    this.mErrors.addOneError(tError);
//    return false;
//}
//
//if (tLBGetSet != null || tLBGetSet.size() > 0 )
//{
//    /*给付类型,00- 投保人,01-被保人,02-受益人，03-连带被保人*/
//    for ( int i=1 ; i <= tLBGetSet.size() ; i++ )
//    {
//        LBGetSchema tLBGetSchema = tLBGetSet.get(i);
//        tLBGetSchema.setState("00");    //LCGet表的该字段属于借用，用于存放给付类型：00- 投保人,01-被保人,02-受益人，03-连带被保人
//        tLBGetSchema.setOperator("B");  //LCGet表的该字段属于借用，用于存放保单类型：C表保单，B表保单,A承保出单前出险
//        tLBGetSchema.setInsuredNo(mLLCaseSchema.getCustomerNo());//LCGet表的该字段属于借用，用于存放出险人编号
//
//        //做映射，将C表数据赋值到B表
//        Reflections tR = new Reflections();
//        LCGetSchema tLCGetSchema = new LCGetSchema();
//        tR.transFields(tLCGetSchema,tLBGetSchema);
//        mLCGetSet.add(tLCGetSchema);
//
//    }
//}





