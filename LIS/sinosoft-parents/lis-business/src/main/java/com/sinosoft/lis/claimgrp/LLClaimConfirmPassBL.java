/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;
import java.util.Vector;

//import bsh.This;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.bq.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔立案结论逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLClaimConfirmPassBL {
private static Logger logger = Logger.getLogger(LLClaimConfirmPassBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;

    //全局变量
    private MMap map = new MMap();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private GlobalInput mG = new GlobalInput();
    TransferData mTransferData = new TransferData();

    private LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
    private LJAGetSet mLJAGetSet = new LJAGetSet();
    private LJSGetSet mLJSGetSet = new LJSGetSet();
    private LJSGetClaimSet mLJSGetClaimSet = new LJSGetClaimSet();
//    private LJAGetClaimSet mLJAGetClaimSet = new LJAGetClaimSet();
    private LOPRTManagerSet mLOPRTManagerSet = new LOPRTManagerSet();
    private Reflections ref = new Reflections();

    // 理赔公用方法类
    private LLClaimPubFunBL mLLClaimPubFunBL = new LLClaimPubFunBL();
    //账户理赔存取轨迹
    LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();    
    private String mClmNo = "";
    private String mPolNo = "";
    private String mDutyCode = "";
    private String mGetDutyCode = "";
    private String mCusNo = ""; //客户号码
    private String mMngCom = ""; //立案管理机构
    private String mAccDate = ""; //意外事故发生日期
    private String mRgtDate = ""; //立案日期
    private int m = 0;
    private int n = 0;
    private double TotalAmnt=0.00;

    public LLClaimConfirmPassBL() {
    }


    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        logger.debug(
                "----------LLClaimConfirmPassBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }
        logger.debug(
                "----------LLClaimConfirmPassBL after getInputData----------");

        //检查数据合法性
        if (!checkBalAmnt()) {
            return false;
        }
        logger.debug(
                "----------LLClaimConfirmPassBL after checkInputData----------");
        //进行业务处理
        if (!dealData()) {
            return false;
        }
        logger.debug(
                "----------LLClaimConfirmPassBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData()) {
            return false;
        }
        logger.debug(
                "----------LLClaimConfirmPassBL after prepareOutputData----------");

        mInputData = null;
        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData() {
        logger.debug("---LLClaimConfirmPassBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);

        mClmNo = (String) mTransferData.getValueByName("RptNo");

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－保额冲减校验－－－续涛加－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 处理数据前做数据校验
     * @return
     */
    private boolean checkBalAmnt() {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 判断该赔案是否有给付记录
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        String tQuerySql = "";
        tQuerySql = "select a.* from LLClaimDetail a,LLClaim b "
                    + "where a.clmno = b.clmno "
                    +
                    "and a.givetype != '1' and (b.givetype = '0' or b.givetype = '4')"
                    + "and a.clmno = '" + mClmNo + "'";

        mLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tQuerySql);
        if (tLLClaimDetailDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLLClaimDetailDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "checkBalAmnt";
            tError.errorMessage = "查询赔案给付保项发生错误!";
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 和LCGet表的有效保额进行比较
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
            LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.2 查询领取项表
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LCGetDB tLCGetDB = new LCGetDB();
            String tSql1 = "select * from LCGet where 1=1 "
                           + " and PolNo = '" + tLLClaimDetailSchema.getPolNo() +
                           "'"
                           + " and DutyCode = '" +
                           tLLClaimDetailSchema.getDutyCode() + "'"
                           + " and GetDutyCode = '" +
                           tLLClaimDetailSchema.getGetDutyCode() + "'";

            LCGetSet tLCGetSet = tLCGetDB.executeQuery(tSql1);
            if (tLCGetSet.size() != 1 || tLCGetDB.mErrors.needDealError()) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLCGetDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmPassBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询承保领取项数据时出错!";
                this.mErrors.addOneError(tError);
                logger.debug(
                        "------------------------------------------------------");
                logger.debug(
                        "--LLClaimContPolDealAutoBL.checkData()--查询承保领取项数据时出错!" +
                        tSql1);
                logger.debug(
                        "------------------------------------------------------");
                return false;
            }
            LCGetSchema tLCGetSchema = tLCGetSet.get(1);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.3 取出各种标志位,进行保额冲减的判断
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tRiskCode = StrTool.cTrim(tLLClaimDetailSchema.getRiskCode());
            String tPosFlag = StrTool.cTrim(tLLClaimDetailSchema.getPosFlag()); //0未做保全,1做过保全
            String tPosPosEdorNo = StrTool.cTrim(tLLClaimDetailSchema.
                                                 getPosEdorNo()); //保全批单号
            String tNBPolNo = StrTool.cTrim(tLLClaimDetailSchema.getNBPolNo()); //承保时的保单号

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.4 判断保项剩余的有效保额
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            if (mLLClaimPubFunBL.getLMRiskSort(tRiskCode, "7")) {
                double tRealPay = tLLClaimDetailSchema.getRealPay(); //赔案给付金额
                double tBalAmnt = 0;

                String tGetDutyName = mLLClaimPubFunBL.getGetDutyName(
                        tLCGetSchema);
                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No2.4 对做过保全的进行处理
                 *  0未做保全,1已做保全LPGet不发生变化,2已做保全LPGet发生变化
                 *  如果tNBAmnt > tPosAmnt {[做保全前]--5万 > 减保后的保额[做保全后]--2万}
                 *
                 *      tRealPay > tBalAmnt {用给付金额 与 做保全前的有效保额相比较}
                 *
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                if (tPosFlag.equals("2")) {
                    LPGetSchema tLPGetSchema = null;
                    tLPGetSchema = mLLClaimPubFunBL.getLPGet(tPosPosEdorNo,
                            tNBPolNo, tLCGetSchema);
                    if (tLPGetSchema == null) {
                        return false;
                    }
                    double tNBAmnt = tLPGetSchema.getStandMoney(); //承保时的保额[做保全前]--5万
                    double tPosAmnt = tLCGetSchema.getStandMoney(); //减保后的保额[做保全后]--2万
                    tBalAmnt = tLPGetSchema.getStandMoney() -
                               tLPGetSchema.getSumMoney(); //做保全前的有效保额

                    if ((tNBAmnt > tPosAmnt) && (tRealPay > tBalAmnt)) {
                        mErrors.addOneError("保单险种号:[" + tLPGetSchema.getPolNo() +
                                            "]做过保全减保,减保前的有效保额为:[" + tBalAmnt +
                                            "],不足以支付" + tGetDutyName +
                                            "的给付金额,请先到审核阶段进行金额调整,然后再进行审批确认操作!");
                        return false;
                    }
                } else {
                    tBalAmnt = tLCGetSchema.getStandMoney() -
                               tLCGetSchema.getSumMoney(); //做保全前的有效保额
                    if (tRealPay > tBalAmnt) {
                        mErrors.addOneError("有效保额不足以支付" + tGetDutyName +
                                            "的给付金额!");
                        return false;
                    }
                }
            }

        }
        
        
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 实付信息校验
         * 1.0  主表和子表记录必须一致
         * 2.0  应付子表总金额必须和理算金额一致
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        /**
		 * 2009-06-25 zhangzheng 
		 * 校验受益人分配产生的应付子表和总表金额必须保持一致
		 */
		LJSGetSet tLJSGetSet=new LJSGetSet();
		LJSGetDB tLJSGetDB= new LJSGetDB();
		tLJSGetDB.setOtherNo(mClmNo);
		tLJSGetSet=tLJSGetDB.query();
		
		LLClaimSchema mLLClaimSchema = new LLClaimSchema();
		mLLClaimSchema = mLLClaimPubFunBL.getLLClaim(mClmNo);
		
		String sql="";
		SSRS tSSRS= null;
		ExeSQL tExeSQL = new ExeSQL();
		double sumljsPay=0.0;
		
		for(int i=1;i<=tLJSGetSet.size();i++)
		{
			sql = "select sum(pay) from ljsgetclaim where getnoticeno='"+tLJSGetSet.get(i).getGetNoticeNo()+"'";
			
			tSSRS = new SSRS();
			
			tSSRS = tExeSQL.execSQL(sql);
			
			if(tSSRS.getMaxRow()==0)
			{
				tSSRS=null;
				sql=null;
				CError.buildErr(this, "给付通知书号:"+tLJSGetSet.get(i).getGetNoticeNo()+"查询不到子表的应付信息,不能结案保存,请先查明原因!");
				return false;
			}
			
			if(tLJSGetSet.get(i).getSumGetMoney()!=Double.parseDouble(tSSRS.GetText(1,1)))
			{
				tSSRS=null;
				sql=null;
				CError.buildErr(this, "给付通知书号:"+tLJSGetSet.get(i).getGetNoticeNo()+"的主子表金额不一致,不能结案保存,请先查明原因!");
				return false;
			}
			
			sumljsPay= sumljsPay + PubFun.setPrecision(tLJSGetSet.get(i).getSumGetMoney(),"0.00");
			sumljsPay= PubFun.setPrecision(sumljsPay,"0.00");
			
			tSSRS=null;
			sql=null;
		}
		
		tLJSGetSet=null;
		tExeSQL=null;
		tLJSGetDB=null;
		
		logger.debug("案件:"+mClmNo+":的理算金额:"+mLLClaimSchema.getRealPay());
		logger.debug("案件:"+mClmNo+":的应付子表总金额:"+sumljsPay);
		
		if(mLLClaimSchema.getRealPay()!=sumljsPay)
		{	
			mLLClaimSchema=null;
			CError.buildErr(this, "案件理算金额和应付金额不一致,不能审核确认,请查明原因!");
			return false;
		}
		
		mLLClaimSchema=null;

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－保额冲减校验－－－续涛加－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData() {
        /**---------------------------------------------------------------------BEG
         * 处理1.根据LLClaimPolicy表的PolNo保单号将LCPol表的ClaimTimes理赔次数加一
         * --------------------------------------------------------------------*/
        String tUpdateSql1 = "";
        tUpdateSql1 = "update LCPol b set b.claimtimes = b.claimtimes+1 where "
                      +
                      "b.polno in (select a.polno from LLClaimPolicy a where "
                      + "a.clmno = '" + mClmNo + "')";

        map.put(tUpdateSql1, "UPDATE");
        //----------------------------------------------------------------------END


        if (!getPrepareCalInfo()) {
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.1 进行保额冲减
         *  续涛加,2005.08.10
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        if (!dealData02()) {
            return false;
        }

        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理3.遍历LLClaimDetail表中每一个给付标志GiveType为0（给付）的保项，然后根据
         * 给付责任编码到LMDutyGetClm表中去找AfterGet，给付后动作这个信息做如下操作
         * --------------------------------------------------------------------*/
        if (!dealData03()) {
            return false;
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * --------------------------------------------------------------------*/

        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理5.生成财务数据处理
         * --------------------------------------------------------------------*/
        if (!dealData05()) {
            return false;
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理6.合同处理:
         * ---------------------------------------------------------------------*/
        if (!dealData06()) {
            return false;
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理6.01.合同处理:lcpol.polstate = 7 死亡理赔
         * DATE : 2006-07-04
         * ---------------------------------------------------------------------*/
        if (!dealData0601()) {
            return false;
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理7.豁免处理
         * ---------------------------------------------------------------------*/
        if (!dealData07()) {
            return false;
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理8.删除财务备份表对应数据
         * ---------------------------------------------------------------------*/
        if (!dealData08()) {
            return false;
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理9. 产生的数据插入到打印管理表:
         * ---------------------------------------------------------------------*/
        if (!dealData09()) {
            return false;
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * 处理10. 对于帐户型的险种处理方式
         * ---------------------------------------------------------------------*/
        if (!dealData10()) {
            return false;
        }
        
        //----------------------------------------------------------------------END
        /**
         * 处理11. 对MMA项目需要保存公共限额的记录
         * **/
        if (!dealData11()) {
            return false;
        }
        
        return true;
    }


    /**
     * 得到处理需要的信息
     * @return boolean
     */
    private boolean getPrepareCalInfo() {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 得到意外事故发生日期
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "";
        tSql = "select a.accDate from LLAccident a,LLCaseRela b where 1=1 "
               + " and a.AccNo = b.CaseRelaNo "
               + " and b.CaseNo in ('" + this.mClmNo + "')";

        ExeSQL tExeSQL = new ExeSQL();
        String tAccDate = tExeSQL.getOneValue(tSql);
        if (tAccDate == null || tAccDate.equals("")) {
            mErrors.addOneError("意外事故发生日期没有找到!");
            return false;
        }

        this.mAccDate = tAccDate.substring(0, 10).trim();
        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－过程中－－－－非帐户保额冲减－－－－－续涛加－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 进行保额冲减
     * @param
     * @return
     * 续涛,2005.08.10
     */
    private boolean dealData02() {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询赔案给付的保项数据
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tSql = "select b.* from llclaim a,LLClaimDetail b where 1=1"
                      + " and a.clmno = b.clmno "
                      + " and a.clmno = '" + mClmNo + "'"
                      + " and (a.givetype ='0' or a.givetype ='4' )"
                      + " and b.givetype !='1'"
                      + " and substr(b.GetDutyKind,2,2) <> '09'"
                      ;
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.executeQuery(tSql);
        if (tLLClaimDetailDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLLClaimDetailDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询赔案给付保项发生错误!" + tSql;
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 循环赔案给付的保项数据,进行保额冲减
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLClaimDetailSchema tLLClaimDetailSchema = null;
        LCGetSet tLCGetSet = new LCGetSet();
        for (int i = 1; i <= tLLClaimDetailSet.size(); i++) {
            tLLClaimDetailSchema = tLLClaimDetailSet.get(i);
            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.1 非帐户型保额冲减
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tAcctFlag = StrTool.cTrim(tLLClaimDetailSchema.getAcctFlag());
            if (!tAcctFlag.equals("1")) {
                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No2.2 本责任保额冲减
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                LCGetSchema tLCGetSchema = new LCGetSchema();
                tLCGetSchema = getLCGet(tLLClaimDetailSchema);
                if (tLCGetSchema != null) {
                    tLCGetSet.add(tLCGetSchema);
                }

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No2.3 判断是否冲减其他保项的保额
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                LMDutyGetClmSchema tLMDutyGetClmSchema = getLMDutyGetClm(
                        tLLClaimDetailSchema);

                if (tLMDutyGetClmSchema != null) {
                    LCGetSchema tOtherLCGetSchema = new LCGetSchema();
                    tOtherLCGetSchema = getOtherLCGet(tLLClaimDetailSchema,
                            tLMDutyGetClmSchema);
                    if (tOtherLCGetSchema != null) {
                        tLCGetSet.add(tOtherLCGetSchema);
                    }
                }
            }
        }
        DealAccountNew();
        map.put(tLCGetSet, "UPDATE");

        return true;
    }


    /**
     * 判断LMDutyGetClm表的CnterCalCode,是否冲减其他保项的保额
     * @param pLLClaimDetailSchema
     * @return
     */
    private LMDutyGetClmSchema getLMDutyGetClm(LLClaimDetailSchema
                                               pLLClaimDetailSchema) {
        LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
        tLMDutyGetClmDB.setGetDutyKind(pLLClaimDetailSchema.getGetDutyKind());
        tLMDutyGetClmDB.setGetDutyCode(pLLClaimDetailSchema.getGetDutyCode());
        LMDutyGetClmSet tLMDutyGetClmSet = tLMDutyGetClmDB.query();

        if (tLMDutyGetClmDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLMDutyGetClmDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询产品定义责任给付赔付信息错误!";
            this.mErrors.addOneError(tError);
            return null;
        }

        if (tLMDutyGetClmSet.size() != 1) {
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询产品定义责任给付赔付信息数据不唯一!";
            this.mErrors.addOneError(tError);
            return null;
        }

        LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmSet.get(1);
        String tCnterCalCode = StrTool.cTrim(tLMDutyGetClmSchema.
                                             getCnterCalCode());

        if (tCnterCalCode != null && tCnterCalCode.length() > 0 &&
            !tCnterCalCode.equals("")) {
            return tLMDutyGetClmSchema;
        }

        return null;
    }


    /**
     * 进行本责任保额冲减
     * @param pLLClaimDetailSchema
     * @return
     */
    private LCGetSchema getLCGet(LLClaimDetailSchema pLLClaimDetailSchema) {

        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(pLLClaimDetailSchema.getPolNo());
        tLCGetDB.setDutyCode(pLLClaimDetailSchema.getDutyCode());
        tLCGetDB.setGetDutyCode(pLLClaimDetailSchema.getGetDutyCode());

        LCGetSet tLCGetSet = tLCGetDB.query();
        if (tLCGetDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询保单险种号[" + pLLClaimDetailSchema.getPolNo() +
                                  "],给付责任:[" +
                                  pLLClaimDetailSchema.getGetDutyCode() +
                                  "],领取项信息失败!";
            this.mErrors.addOneError(tError);
            return null;
        }

        if (tLCGetSet.size() != 1) {
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询保单险种号[" + pLLClaimDetailSchema.getPolNo() +
                                  "],给付责任:[" +
                                  pLLClaimDetailSchema.getGetDutyCode() +
                                  "],领取项信息不唯一!";
            this.mErrors.addOneError(tError);
            return null;
        }

        LCGetSchema tLCGetSchema = tLCGetSet.get(1);

        double t_SumMoney = 0;
        t_SumMoney = tLCGetSchema.getSumMoney();

        t_SumMoney = tLCGetSchema.getSumMoney() +
                     pLLClaimDetailSchema.getRealPay();

        logger.debug(
                "------------------------------开始---------正常冲减保额------------------------------");
        logger.debug("保单险种号PolNo:[" + pLLClaimDetailSchema.getPolNo() +
                           "],责任DutyCode:[" + pLLClaimDetailSchema.getDutyCode() +
                           "],给付责任GetDutyCode:[" +
                           pLLClaimDetailSchema.getGetDutyCode() + "],基本保额:[" +
                           tLCGetSchema.getStandMoney() + "],已冲减的金额:[" +
                           tLCGetSchema.getSumMoney() + "],本次给付金额:[" +
                           pLLClaimDetailSchema.getRealPay() + "],冲减后金额:[" +
                           t_SumMoney + "]");
        logger.debug(
                "------------------------------结束---------正常冲减保额------------------------------");
        TotalAmnt=t_SumMoney;
        tLCGetSchema.setSumMoney(t_SumMoney);
        tLCGetSchema.setOperator(mG.Operator);
        tLCGetSchema.setModifyDate(this.CurrentDate);
        tLCGetSchema.setModifyTime(this.CurrentTime);
        return tLCGetSchema;
    }


    /**
     * 进行其他起责任保额冲减
     * @param pLLClaimDetailSchema
     * @return
     */
    private LCGetSchema getOtherLCGet(LLClaimDetailSchema pLLClaimDetailSchema,
                                      LMDutyGetClmSchema pLMDutyGetClmSchema) {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.1 通过公式计算出冲减其他责任的金额
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tCnterCalCode = StrTool.cTrim(pLMDutyGetClmSchema.
                                             getCnterCalCode()); //其他责任编码
        String tOthCalCode = StrTool.cTrim(pLMDutyGetClmSchema.getOthCalCode()); //其他计算公式

        double t_OthMoney = 0;
        t_OthMoney = Double.parseDouble(new DecimalFormat("0.00").format(
                t_OthMoney));
        t_OthMoney = executepay(tOthCalCode, pLLClaimDetailSchema);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.2 对其他责任的保额进行冲减
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(pLLClaimDetailSchema.getPolNo());
        tLCGetDB.setDutyCode(pLLClaimDetailSchema.getDutyCode());
        tLCGetDB.setGetDutyCode(tCnterCalCode);

        LCGetSet tLCGetSet = tLCGetDB.query();
        if (tLCGetDB.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询其他责任保单险种号[" +
                                  pLLClaimDetailSchema.getPolNo() + "],给付责任:[" +
                                  tCnterCalCode + "],领取项信息失败!";
            this.mErrors.addOneError(tError);
            return null;
        }

        if (tLCGetSet.size() != 1) {
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getPolPay";
            tError.errorMessage = "查询其他责任保单险种号[" +
                                  pLLClaimDetailSchema.getPolNo() + "],给付责任:[" +
                                  tCnterCalCode + "],领取项信息不唯一!";
            this.mErrors.addOneError(tError);
            return null;
        }

        LCGetSchema tLCGetSchema = tLCGetSet.get(1);

        double t_SumMoney = 0;
        t_SumMoney = tLCGetSchema.getSumMoney();

        t_SumMoney = tLCGetSchema.getSumMoney() + t_OthMoney;

        logger.debug("------------------------------开始---------其他责任冲减保额------------------------------");
        logger.debug("保单险种号PolNo:[" + pLLClaimDetailSchema.getPolNo() +
                           "],责任DutyCode:[" + pLLClaimDetailSchema.getDutyCode() +
                           "],给付责任GetDutyCode:[" +
                           pLLClaimDetailSchema.getGetDutyCode() + "],基本保额:[" +
                           tLCGetSchema.getStandMoney() + "],已冲减的金额:[" +
                           tLCGetSchema.getSumMoney() + "],本次给付金额:[" +
                           t_OthMoney + "],冲减后金额:[" + t_SumMoney + "]");
        logger.debug("------------------------------结束---------其他责任冲减保额------------------------------");

        TotalAmnt=t_SumMoney;
        tLCGetSchema.setSumMoney(t_SumMoney);
        tLCGetSchema.setOperator(mG.Operator);
        tLCGetSchema.setMakeDate(this.CurrentDate);
        tLCGetSchema.setMakeTime(this.CurrentTime);

        return tLCGetSchema;
    }


    /**
     * 目的：理赔计算
     * @param pCalSum 计算金额
     * @param pCalCode 计算公式
     * @return double
     */
    private double executepay(String pCalCode,
                              LLClaimDetailSchema pLLClaimDetailSchema) {
        double rValue;

        if (null == pCalCode || "".equals(pCalCode)) {
            return 0;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 设置各种计算要素
         * ?Amnt? \?PolNo??\?GetDutyCode?\?GetDutyKind?\?ClmNO?
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        logger.debug(
                "==================================================================");

        //增加基本要素,计算给付金
        TransferData tTransferData = new TransferData();

        //基本保额,取自出险时点,需要考虑保全
        tTransferData.setNameAndValue("Amnt",
                                      String.valueOf(getAmnt(
                                              pLLClaimDetailSchema)));

        //保单号
        tTransferData.setNameAndValue("PolNo",
                                      String.valueOf(pLLClaimDetailSchema.
                getPolNo()));

        //给付责任类型
        tTransferData.setNameAndValue("GetDutyCode",
                                      String.valueOf(pLLClaimDetailSchema.
                getGetDutyCode()));

        //赔案号
        tTransferData.setNameAndValue("ClmNO", String.valueOf(this.mClmNo));

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No4.0 将所有设置的参数加入到PubCalculator.addBasicFactor()中
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        Vector tVector = tTransferData.getValueNames();
        PubCalculator tPubCalculator = new PubCalculator();

        for (int i = 0; i < tVector.size(); i++) {
            String tName = (String) tVector.get(i);
            String tValue = (String) tTransferData.getValueByName(tName);
//            logger.debug("PubCalculator.addBasicFactor--tName:" + tName + "  tValue:" + tValue);
            tPubCalculator.addBasicFactor(tName, tValue);
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No5.0 将所有设置的参数加入到Calculator.addBasicFactor()中
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        Calculator tCalculator = new Calculator();
        tCalculator.setCalCode(pCalCode);

        tVector = tTransferData.getValueNames();
        logger.debug(
                "==================================================================");
        for (int i = 0; i < tVector.size(); i++) {
            String tName = (String) tVector.get(i);
            String tValue = (String) tTransferData.getValueByName(tName);
            logger.debug("计算其他责任计算要素名称--tName:" + tName + "  tValue:" +
                               tValue);
            tCalculator.addBasicFactor(tName, tValue);
        }
        logger.debug(
                "==================================================================");

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No6.0 进行计算，Calculator.calculate()
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String tStr = "";
        logger.debug("计算公式=" + tCalculator.getCalSQL());
        tStr = tCalculator.calculate();
        if (tStr.trim().equals("")) {
            rValue = 0;
        } else {
            rValue = Double.parseDouble(tStr);
        }

        if (tCalculator.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tCalculator.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "executepay";
            tError.errorMessage = "计算其他责任发生错误!原公式:" + pCalCode + "||,解析后的公式:" +
                                  tCalculator.getCalSQL();
            this.mErrors.addOneError(tError);
        }
        logger.debug(
                "==================================================================");
        return rValue;
    }


    /**
     * 得到基本保额,取自出险时点,需要考虑保全
     * @return
     */
    private double getAmnt(LLClaimDetailSchema pLLClaimDetailSchema) {
        String tReturn = "";
        double t_Sum_Return = 0;
        t_Sum_Return = Double.parseDouble(new DecimalFormat("0.00").format(
                t_Sum_Return));

        ExeSQL tExeSQL = new ExeSQL();
        String tSql = "";

        /*0未做保全,1已做保全*/
        if (pLLClaimDetailSchema.getPosFlag().endsWith("0")) {
            tSql = "select nvl(Amnt,0) from LCDuty where 1=1 "
                   + " and PolNo ='" + pLLClaimDetailSchema.getPolNo() + "'"
                   + " and DutyCode ='" + pLLClaimDetailSchema.getDutyCode() +
                   "'";
        } else {

            tSql = "select nvl(Amnt,0) from LPDuty where 1=1 "
                   + " and EdorNo ='" + pLLClaimDetailSchema.getPosEdorNo() +
                   "'"
                   + " and PolNo ='" + pLLClaimDetailSchema.getPolNo() + "'"
                   + " and DutyCode ='" + pLLClaimDetailSchema.getDutyCode() +
                   "'";
        }

        logger.debug("要素Amnt[基本保额]:" + tSql);
        tReturn = StrTool.cTrim(tExeSQL.getOneValue(tSql));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getOamnt";
            tError.errorMessage = "得到基本保额发生错误!" + tSql;
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            t_Sum_Return = Double.parseDouble(tReturn);
        }

        return t_Sum_Return;

    }


    private boolean DealAccountNew() {
        double modifyMoney = 0;
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 根据赔案号，从LLRegister表中取出立案登记信息
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
            tError.errorMessage = "在立案登记信息中没有找到记录,不能结案!";
            this.mErrors.addOneError(tError);
            return false;
        }

        LLClaimPolicySet tLLClaimPolicySet = new LLClaimPolicySet();
        LLClaimPolicyDB tLLClaimPolicyDB = new LLClaimPolicyDB();
        String PSql = "select * from LLClaimPolicy where clmno = '" + mClmNo +
                      "'";

        tLLClaimPolicySet = tLLClaimPolicyDB.executeQuery(PSql);
        if (tLLClaimPolicySet.size() < 0) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLRegisterDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalMatchBL";
            tError.functionName = "getMatchFactor";
            tError.errorMessage = "未查询到理算金额不能结案!";
            this.mErrors.addOneError(tError);
            return false;
        }

        for (int a = 1; a <= tLLClaimPolicySet.size(); a++) {
            double tGetMoney = 0.00; //该保单的理赔金额
            tGetMoney = Arith.round(tLLClaimPolicySet.get(a).getRealPay(), 2);
            LLClaimDetailSet mLLClaimDetailSet = new LLClaimDetailSet();
            LLClaimDetailDB mLLClaimDetailDB = new LLClaimDetailDB();
            mLLClaimDetailDB.setCaseNo(mClmNo);
            mLLClaimDetailDB.setClmNo(mClmNo);
            mLLClaimDetailDB.setPolNo(tLLClaimPolicySet.get(a).getPolNo());
            mLLClaimDetailSet = mLLClaimDetailDB.query();
            if (mLLClaimDetailSet != null && mLLClaimDetailSet.size() < 0) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLRegisterDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalMatchBL";
                tError.functionName = "getMatchFactor";
                tError.errorMessage = "未查询到理算金额不能结案!";
                this.mErrors.addOneError(tError);
                return false;
            }

            //取到长险标识
            ExeSQL tExeSQL = new ExeSQL();
//            String tSql = "select type from lmdutyget where getdutycode = '" +
//                          mLLClaimDetailSet.get(1).getGetDutyCode() + "'";
//            String tType = StrTool.cTrim(tExeSQL.getOneValue(tSql)); //帐户标识
            String tSql = "select insuaccflag from lmrisk where riskcode = '" +
                          mLLClaimDetailSet.get(1).getRiskCode() + "'";
            String tInsuaccFlag = StrTool.cTrim(tExeSQL.getOneValue(tSql)); //帐户标识

            String tSql2 =
                    "select riskperiod from lmriskapp where riskcode = '" +
                    mLLClaimDetailSet.get(1).getRiskCode() + "'";
            String tRiskPeriod = StrTool.cTrim(tExeSQL.getOneValue(tSql2)); //长险标识
            String tSql3 =
                    " select acckind From lmriskinsuacc a,lmrisktoacc b "
                    + " where a.insuaccno = b.insuaccno and a.acctype = '002' "
                    + " and b.riskcode = '" +
                    mLLClaimDetailSet.get(1).getRiskCode() + "'";
            String tAccKind = StrTool.cTrim(tExeSQL.getOneValue(tSql3)); //先进先出标识

            if (tExeSQL.mErrors.needDealError()) {
                this.mErrors.copyAllErrors(tExeSQL.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalMatchBL";
                tError.functionName = "getLLToClaimDuty";
                tError.errorMessage = "得到长险先进先出标识发生错误!";
                this.mErrors.addOneError(tError);
            }
            //if (tRiskPeriod.equals("L") && tType.equals("1"))
            if (tRiskPeriod.equals("L") && tInsuaccFlag.equals("Y")) { //长险判断

                LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
                LCInsureAccClassSchema tLCInsureAccClassSchema = new
                        LCInsureAccClassSchema();
                tLCInsureAccClassDB.setPolNo(mLLClaimDetailSet.get(1).getPolNo());
                tLCInsureAccClassDB.setInsuredNo(mLLClaimDetailSet.get(1).
                                                 getCustomerNo());
                tLCInsureAccClassDB.setRiskCode(mLLClaimDetailSet.get(1).
                                                getRiskCode());
                String tSql4 =
                        "select payplancode from lmdutypayrela where dutycode='"
                        + mLLClaimDetailSet.get(1).getDutyCode() + "'";
                String tReturn = tExeSQL.getOneValue(tSql4);
                tLCInsureAccClassDB.setPayPlanCode(tReturn);
                tLCInsureAccClassDB.setAccAscription("1");
                LCInsureAccClassSet tLCInsureAccClassSet = new
                        LCInsureAccClassSet();
                tLCInsureAccClassSet = tLCInsureAccClassDB.query();
                if (tAccKind.equals("3")) { //需要先进先出
                    //使用法人帐户缴费则使用先进先出的原则
                    String tRateType = "Y"; //原始利率类型（）
                    String tIntvType = "D"; //目标利率类型（日利率）
                    int tPerio = 0; //银行利率期间
                    String tType2 = "F"; //截息计算类型（单利还是复利）
                    String tDepst = "D"; //贷存款标志（贷款还是存款）
                    TransferData pLastMoney = new TransferData(); //每个帐户的当前余额
                    AccountManage tAccountManage = new AccountManage();
                    for (int i = 1; i <= tLCInsureAccClassSet.size(); i++) {
                        double pSumPay = 0.00; //本次余额
                        pLastMoney = tAccountManage.getAccClassInterestNew(
                                tLCInsureAccClassSet.get(i),
                                mLLClaimDetailSet.get(1).getMakeDate(),
                                tRateType,
                                tIntvType,
                                tPerio,
                                tType2,
                                tDepst);
                        if (pLastMoney != null) {
                            String tempmoney = String.valueOf(pLastMoney.
                                    getValueByName("aAccClassSumPay"));
                            pSumPay = Arith.round(Double.parseDouble(tempmoney),
                                                  2);
                            //如果本次缴费小于帐户余额则直接修改轨迹金额
                            if (pSumPay >= tGetMoney) {
                                LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                                        LCInsureAccTraceSchema();
                                String tSerialNo = PubFun1.CreateMaxNo(
                                        "SERIALNO", mG.ManageCom);
                                tLCInsureAccTraceSchema.setGrpContNo(
                                        tLCInsureAccClassSet.get(i).
                                        getGrpContNo());
                                tLCInsureAccTraceSchema.setGrpPolNo(
                                        tLCInsureAccClassSet.get(i).getGrpPolNo());
                                tLCInsureAccTraceSchema.setAccAscription(
                                        tLCInsureAccClassSet.get(i).
                                        getAccAscription());
                                tLCInsureAccTraceSchema.setFeeCode("000000");
                                tLCInsureAccTraceSchema.setContNo(
                                        tLCInsureAccClassSet.get(i).getContNo());
                                tLCInsureAccTraceSchema.setInsuAccNo(
                                        tLCInsureAccClassSet.get(i).
                                        getInsuAccNo());
                                tLCInsureAccTraceSchema.setMakeDate(PubFun.
                                        getCurrentDate());
                                tLCInsureAccTraceSchema.setMakeTime(PubFun.
                                        getCurrentTime());
                                tLCInsureAccTraceSchema.setModifyDate(PubFun.
                                        getCurrentDate());
                                tLCInsureAccTraceSchema.setModifyTime(PubFun.
                                        getCurrentTime());
                                tLCInsureAccTraceSchema.setMoney( -tGetMoney);
                                tLCInsureAccTraceSchema.setMoneyType("PG");
                                tLCInsureAccTraceSchema.setOperator(mG.Operator);
                                tLCInsureAccTraceSchema.setManageCom(mG.
                                        ManageCom);
                                tLCInsureAccTraceSchema.setOtherNo(mClmNo);
                                tLCInsureAccTraceSchema.setOtherType("5");
                                tLCInsureAccTraceSchema.setPayDate(
                                        mLLClaimDetailSet.get(1).getMakeDate()); //理算日期＝结息日期
                                tLCInsureAccTraceSchema.setPayNo(
                                        tLCInsureAccClassSet.get(i).getOtherNo());
                                tLCInsureAccTraceSchema.setPolNo(
                                        tLCInsureAccClassSet.get(i).getPolNo());
                                tLCInsureAccTraceSchema.setRiskCode(
                                        tLCInsureAccClassSet.get(i).getRiskCode());
                                tLCInsureAccTraceSchema.setPayPlanCode(
                                        tLCInsureAccClassSet.get(i).
                                        getPayPlanCode());
                                tLCInsureAccTraceSchema.setState("0");
                                tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
                                String DeleteSQL =
                                        " delete from lcinsureacctrace where PolNo = '" +
                                        tLCInsureAccClassSet.get(i).getPolNo() +
                                        "'" +
                                        " and moneytype in('PL','PX','SL','SX','PG')" +
                                        " and OtherNo='" + mClmNo + "'";
                                map.put(DeleteSQL, "DELETE");
                                mLCInsureAccTraceSet.add(
                                        tLCInsureAccTraceSchema);
                                break;
                            } else if (pSumPay > 0 && pSumPay < tGetMoney) {
                                //使用一次法人帐户后的本次缴费金额
                                tGetMoney = tGetMoney - pSumPay;
                                LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                                        LCInsureAccTraceSchema();
                                String tSerialNo = PubFun1.CreateMaxNo(
                                        "SERIALNO", mG.ManageCom);
                                tLCInsureAccTraceSchema.setGrpContNo(
                                        tLCInsureAccClassSet.get(i).
                                        getGrpContNo());
                                tLCInsureAccTraceSchema.setFeeCode("000000");
                                tLCInsureAccTraceSchema.setGrpPolNo(
                                        tLCInsureAccClassSet.get(i).getGrpPolNo());
                                tLCInsureAccTraceSchema.setAccAscription(
                                        tLCInsureAccClassSet.get(i).
                                        getAccAscription());
                                tLCInsureAccTraceSchema.setContNo(
                                        tLCInsureAccClassSet.get(i).getContNo());
                                tLCInsureAccTraceSchema.setInsuAccNo(
                                        tLCInsureAccClassSet.get(i).
                                        getInsuAccNo());
                                tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
                                tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
                                tLCInsureAccTraceSchema.setModifyDate(
                                        CurrentDate);
                                tLCInsureAccTraceSchema.setModifyTime(
                                        CurrentTime);
                                tLCInsureAccTraceSchema.setMoney( -pSumPay); //
                                tLCInsureAccTraceSchema.setMoneyType("PG");
                                tLCInsureAccTraceSchema.setOperator(mG.Operator);
                                tLCInsureAccTraceSchema.setManageCom(mG.
                                        ManageCom);
                                tLCInsureAccTraceSchema.setOtherNo(mClmNo);
                                tLCInsureAccTraceSchema.setOtherType("5");
                                tLCInsureAccTraceSchema.setPayDate(
                                        mLLClaimDetailSet.get(1).getMakeDate()); //理算日期＝结息日期
                                tLCInsureAccTraceSchema.setPayNo(
                                        tLCInsureAccClassSet.get(i).getOtherNo());
                                tLCInsureAccTraceSchema.setPolNo(
                                        tLCInsureAccClassSet.get(i).getPolNo());
                                tLCInsureAccTraceSchema.setRiskCode(
                                        tLCInsureAccClassSet.get(i).getRiskCode());
                                tLCInsureAccTraceSchema.setPayPlanCode(
                                        tLCInsureAccClassSet.get(i).
                                        getPayPlanCode());
                                tLCInsureAccTraceSchema.setState("0");
                                tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
                                String DeleteSQL2 =
                                        " delete from lcinsureacctrace where PolNo = '" +
                                        tLCInsureAccClassSet.get(i).getPolNo() +
                                        "'" +
                                        " and moneytype in('PL','PX','SL','SX','PG')" +
                                        " and OtherNo='" + mClmNo + "'";
                                map.put(DeleteSQL2, "DELETE");
                                mLCInsureAccTraceSet.add(
                                        tLCInsureAccTraceSchema);
                            }
                        }
                    }

                } else { //不需要先进先出
                    tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);

                    LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                            LCInsureAccTraceSchema();
                    String tSerialNo = PubFun1.CreateMaxNo("SERIALNO",
                            mG.ManageCom);
                    tLCInsureAccTraceSchema.setGrpContNo(
                            tLCInsureAccClassSchema.getGrpContNo());
                    tLCInsureAccTraceSchema.setFeeCode("000000");
                    tLCInsureAccTraceSchema.setGrpPolNo(tLCInsureAccClassSchema.
                            getGrpPolNo());
                    tLCInsureAccTraceSchema.setAccAscription(
                            tLCInsureAccClassSchema.getAccAscription());
                    tLCInsureAccTraceSchema.setContNo(tLCInsureAccClassSchema.
                            getContNo());
                    tLCInsureAccTraceSchema.setInsuAccNo(
                            tLCInsureAccClassSchema.getInsuAccNo());
                    tLCInsureAccTraceSchema.setMoney( -tLLClaimPolicySet.get(a).
                            getRealPay());
                    tLCInsureAccTraceSchema.setMoneyType("PG");
                    tLCInsureAccTraceSchema.setOtherNo(mClmNo);
                    tLCInsureAccTraceSchema.setOtherType("5");
                    tLCInsureAccTraceSchema.setPayDate(mLLClaimDetailSet.get(1).
                            getMakeDate()); //理算日期＝结息日期
                    tLCInsureAccTraceSchema.setPayNo(mClmNo);
                    tLCInsureAccTraceSchema.setPolNo(tLCInsureAccClassSchema.
                            getPolNo());
                    tLCInsureAccTraceSchema.setRiskCode(tLCInsureAccClassSchema.
                            getRiskCode());
                    tLCInsureAccTraceSchema.setPayPlanCode(
                            tLCInsureAccClassSchema.getPayPlanCode());
                    tLCInsureAccTraceSchema.setState("0");
                    tLCInsureAccTraceSchema.setSerialNo(tSerialNo);
                    tLCInsureAccTraceSchema.setOperator(mG.Operator);
                    tLCInsureAccTraceSchema.setManageCom(mG.ManageCom);
                    tLCInsureAccTraceSchema.setMakeDate(CurrentDate);
                    tLCInsureAccTraceSchema.setMakeTime(CurrentTime);
                    tLCInsureAccTraceSchema.setModifyDate(CurrentDate);
                    tLCInsureAccTraceSchema.setModifyTime(CurrentTime);
                    String DeleteSQL3 =
                            " delete from lcinsureacctrace where PolNo = '" +
                            tLCInsureAccClassSchema.getPolNo() + "'" +
                            " and moneytype in('PL','PX','SL','SX','PG')" +
                            " and OtherNo='" + mClmNo + "'";
                    map.put(DeleteSQL3, "DELETE");
                    mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
                }
            }
        }

        //
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
        LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
        LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
        tLLClaimDetailDB.setCaseNo(mClmNo);
        tLLClaimDetailDB.setClmNo(mClmNo);
        tLLClaimDetailSet = tLLClaimDetailDB.query();

        for (int j = 1; j <= tLLClaimDetailSet.size(); j++) {
            String tCustomerNo = tLLClaimDetailSet.get(j).getCustomerNo();
            LLClaimAccountSet tLLClaimAccountSet = new LLClaimAccountSet();
            LLClaimAccountDB tLLClaimAccountDB = new LLClaimAccountDB();
            String accountSQL =
                    "select * from llclaimaccount where clmno='" + mClmNo +
                    "' and declineno = '" + tCustomerNo +
                    "' and othertype = 'S'";
            tLLClaimAccountSet = tLLClaimAccountDB.executeQuery(accountSQL);
            if (tLLClaimAccountSet.size() != 0) {
                for (int i = 1; i <= tLLClaimAccountSet.size(); i++) {
                    LLClaimAccountSchema tLLClaimAccountSchema = new
                            LLClaimAccountSchema();
                    LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                            LCInsureAccTraceSchema();
                    tLLClaimAccountSchema = tLLClaimAccountSet.get(i);
                    if (tLLClaimAccountSchema.getAccPayMoney() > 0) {
                        tLCInsureAccTraceSchema = getAccountToTrace(
                                tLLClaimAccountSchema);
                        mLCInsureAccTraceSet.add(tLCInsureAccTraceSchema);
                    }
                }
            }
        }
        //使用团体帐户赔付
        LCInsureAccTraceSchema mLCInsureAccTraceSchema = new
                LCInsureAccTraceSchema();
        String PAccountSQL =
                "select distinct polno,insuaccno,payplancode,otherno,othertype from "
                + " llclaimaccount where clmno='" + mClmNo +
                "' and othertype ='P' ";
        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS = new SSRS();
        tSSRS = tExeSQL.execSQL(PAccountSQL);
        if (tSSRS != null && tSSRS.getMaxRow() > 0) {
            String tPolNo = tSSRS.GetText(1, 1);
            String tInsuAccNo = tSSRS.GetText(1, 2);
            String tPayPlanCode = tSSRS.GetText(1, 3);
            String tOtherNo = tSSRS.GetText(1, 4);
            String tOtherType = tSSRS.GetText(1, 5);

            mLCInsureAccTraceSchema.setPolNo(tPolNo);
            mLCInsureAccTraceSchema.setInsuAccNo(tInsuAccNo);
            mLCInsureAccTraceSchema.setPayPlanCode(tPayPlanCode);
            mLCInsureAccTraceSchema.setRiskCode(tOtherNo);
            mLCInsureAccTraceSchema.setPayDate(tLLClaimDetailSet.get(1).
                                               getMakeDate()); //理算日期＝结息日期
            String tRealPay =
                    "select sum(accpaymoney) from llclaimaccount where clmno = '" +
                    mClmNo + "' and othertype = 'P'";
            SSRS tSSRS2 = new SSRS();
            tSSRS2 = tExeSQL.execSQL(tRealPay);
            if (tSSRS2 != null && tSSRS2.getMaxRow() > 0) {
                if (!tSSRS2.GetText(1, 1).equals("")) {
                    modifyMoney = Double.parseDouble(tSSRS2.GetText(1, 1));
                }
            }
            if (modifyMoney > 0) {
                modifyMoney = modifyMoney * ( -1);
                mLCInsureAccTraceSchema.setMoney(modifyMoney);
                mLCInsureAccTraceSchema.setMoneyType("PG");
                String AccTraceSQL =
                        "select * from lcinsureacctrace where polno='" +
                        mLCInsureAccTraceSchema.getPolNo() +
                        "' and insuaccno='" +
                        mLCInsureAccTraceSchema.getInsuAccNo() +
                        "' and riskcode='" +
                        mLCInsureAccTraceSchema.getRiskCode() +
                        "' and payplancode='" +
                        mLCInsureAccTraceSchema.getPayPlanCode() +
                        "' and moneytype='BF'";
                LCInsureAccTraceDB trLCInsureAccTraceDB = new
                        LCInsureAccTraceDB();
                LCInsureAccTraceSet trLCInsureAccTraceSet = new
                        LCInsureAccTraceSet();
                trLCInsureAccTraceSet = trLCInsureAccTraceDB.executeQuery(
                        AccTraceSQL);

                if (trLCInsureAccTraceSet.size() > 0) {
                    LCInsureAccTraceSchema trLCInsureAccTraceSchema = new
                            LCInsureAccTraceSchema();
                    trLCInsureAccTraceSchema = trLCInsureAccTraceSet.get(1);
                    String SerialNo = PubFun1.CreateMaxNo("SERIALNO",
                            mG.ManageCom);
                    mLCInsureAccTraceSchema.setSerialNo(SerialNo);
                    mLCInsureAccTraceSchema.setGrpPolNo(
                            trLCInsureAccTraceSchema.getGrpPolNo());
                    mLCInsureAccTraceSchema.setOtherType("5");
                    mLCInsureAccTraceSchema.setFeeCode("000000");
                    mLCInsureAccTraceSchema.setManageCom(
                            trLCInsureAccTraceSchema.getManageCom());
                    mLCInsureAccTraceSchema.setGrpContNo(
                            trLCInsureAccTraceSchema.getGrpContNo());
                    mLCInsureAccTraceSchema.setContNo(trLCInsureAccTraceSchema.
                            getContNo());
                    mLCInsureAccTraceSchema.setOtherNo(mClmNo);
                    mLCInsureAccTraceSchema.setPayNo(mClmNo);
                    mLCInsureAccTraceSchema.setState("0");
                    mLCInsureAccTraceSchema.setAccAscription("1");
                    mLCInsureAccTraceSchema.setOperator(mG.Operator);
                    mLCInsureAccTraceSchema.setMakeDate(CurrentDate);
                    mLCInsureAccTraceSchema.setMakeTime(CurrentTime);
                    mLCInsureAccTraceSchema.setModifyDate(CurrentDate);
                    mLCInsureAccTraceSchema.setModifyTime(CurrentTime);
                    mLCInsureAccTraceSet.add(mLCInsureAccTraceSchema);
                } else {
                    // @@错误处理
                    this.mErrors.copyAllErrors(tLLRegisterDB.mErrors);
                    CError tError = new CError();
                    tError.moduleName = "LLClaimCalMatchBL";
                    tError.functionName = "getMatchFactor";
                    tError.errorMessage = "该公共帐户保单 " + tPolNo + " 信息有误,不能结案!";
                    this.mErrors.addOneError(tError);
                    return false;
                }
            }
        }
        if (mLCInsureAccTraceSet.size() != 0) {
            String DeleteTraceSQL =
                    " delete from lcinsureacctrace where trim(grpcontno) in" +
                    " (select distinct grpcontno from llclaimaccount where clmno='" +
                    mClmNo +
                    "') and moneytype in('PL','PX','SL','SX','PG')" +
                    " and OtherNo='" + mClmNo + "'";
            map.put(DeleteTraceSQL, "DELETE");
            map.put(mLCInsureAccTraceSet, "INSERT");
        }
        return true;
    }

    private boolean DealAccount(LLClaimDetailSchema pLLClaimDetailSchema) {
        String tPolNo = pLLClaimDetailSchema.getPolNo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No3.0 根据赔案号，从LLRegister表中取出立案登记信息
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

        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());

        //从LCInsureAccTrace中取

        String TraceSQL = "select * from lcinsureacctrace where polno = '" +
                          tPolNo + "' "
                          + " and otherno ='" + mClmNo + "' and grpcontno in "
                          +
                          " (select distinct grpcontno from llclaimaccount where "
                          + " clmno='" + mClmNo +
                          "') and moneytype in('PL','SL','PG')";
        LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccTraceDB tLCInsureAccTraceDB = new LCInsureAccTraceDB();
        tLCInsureAccTraceSet = tLCInsureAccTraceDB.executeQuery(TraceSQL);
        for (int i = 1; i <= tLCInsureAccTraceSet.size(); i++) {
            LCInsureAccTraceSchema tLCInsureAccTraceSchema = new
                    LCInsureAccTraceSchema();
            tLCInsureAccTraceSchema = tLCInsureAccTraceSet.get(i);
            if (!getNotInDatabaseAcc(tLCInsureAccTraceSchema)) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLLRegisterDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimCalMatchBL";
                tError.functionName = "getMatchFactor";
                tError.errorMessage = "账户金额修改出错";
                this.mErrors.addOneError(tError);
                return false;
            }
        }
        return true;
    }

    private LCInsureAccTraceSchema getAccountToTrace(LLClaimAccountSchema
            mLLClaimAccountSchema) {
        LCInsureAccTraceDB mLCInsureAccTraceDB = new LCInsureAccTraceDB();
        LCInsureAccTraceSet mLCInsureAccTraceSet = new LCInsureAccTraceSet();
        LCInsureAccTraceSchema mLCInsureAccTraceSchema = new
                LCInsureAccTraceSchema();
        LCInsureAccTraceSchema rLCInsureAccTraceSchema = new
                LCInsureAccTraceSchema();
        String SAccTraceSQL = "select * from lcinsureacctrace where polno='" +
                              mLLClaimAccountSchema.getPolNo() +
                              "' and insuaccno='" +
                              mLLClaimAccountSchema.getInsuAccNo() +
                              "' and riskcode='" +
                              mLLClaimAccountSchema.getOtherNo() +
                              "' and payplancode='" +
                              mLLClaimAccountSchema.getPayPlanCode() +
                              "' ";
        logger.debug("轨迹：" + SAccTraceSQL);
        mLCInsureAccTraceSet = mLCInsureAccTraceDB.executeQuery(SAccTraceSQL);
        if (mLCInsureAccTraceSet.size() > 0) {
            mLCInsureAccTraceSchema = mLCInsureAccTraceSet.get(1);
        }
        String SerialNo = PubFun1.CreateMaxNo("SERIALNO",
                                              mG.ManageCom);
        logger.debug("-----生成流水线号--- " + SerialNo);
        rLCInsureAccTraceSchema.setSchema(mLCInsureAccTraceSchema);
        rLCInsureAccTraceSchema.setSerialNo(SerialNo);
        rLCInsureAccTraceSchema.setGrpContNo(mLLClaimAccountSchema.getGrpContNo());
        rLCInsureAccTraceSchema.setPolNo(mLLClaimAccountSchema.getPolNo());
        rLCInsureAccTraceSchema.setInsuAccNo(mLLClaimAccountSchema.getInsuAccNo());
        rLCInsureAccTraceSchema.setPayPlanCode(mLLClaimAccountSchema.
                                               getPayPlanCode());
        rLCInsureAccTraceSchema.setOtherType("5");
        rLCInsureAccTraceSchema.setOtherNo(mClmNo);
        rLCInsureAccTraceSchema.setPayNo(mClmNo);
        rLCInsureAccTraceSchema.setRiskCode(mLLClaimAccountSchema.getOtherNo());
        //  rLCInsureAccTraceSchema.setPayDate(CurrentDate);
        rLCInsureAccTraceSchema.setPayDate(mLLClaimAccountSchema.getMakeDate());
        rLCInsureAccTraceSchema.setAccAscription("1");
        double ModifyMoney = 0;
        ModifyMoney = mLLClaimAccountSchema.getAccPayMoney();
        if (ModifyMoney > 0) {
            if (mLLClaimAccountSchema.getOtherType().equals("S")
                && mLLClaimAccountSchema.getAccPayMoney() > 0) { //个人账户报销额
                ModifyMoney = ModifyMoney * ( -1);
                rLCInsureAccTraceSchema.setMoney(ModifyMoney);
                rLCInsureAccTraceSchema.setMoneyType("PG");
            }
            if (mLLClaimAccountSchema.getOtherType().equals("M")) { //个人账户余额
                rLCInsureAccTraceSchema.setMoney(ModifyMoney);
                rLCInsureAccTraceSchema.setMoneyType("SX");
            }
            rLCInsureAccTraceSchema.setOperator(mG.Operator);
            rLCInsureAccTraceSchema.setMakeDate(CurrentDate);
            rLCInsureAccTraceSchema.setMakeTime(CurrentTime);
            rLCInsureAccTraceSchema.setModifyDate(CurrentDate);
            rLCInsureAccTraceSchema.setModifyTime(CurrentTime);
        }
        return rLCInsureAccTraceSchema;
    }


    private boolean getNotInDatabaseAcc(LCInsureAccTraceSchema
                                        tLCInsureAccTraceSchema) {
        LCInsureAccTraceSchema fLCInsureAccTraceSchema = new
                LCInsureAccTraceSchema();
        fLCInsureAccTraceSchema.setSchema(tLCInsureAccTraceSchema);
        String accClassSQL = this.getUpdateAccClassSQL(
                fLCInsureAccTraceSchema);
        map.put(accClassSQL, "UPDATE");
        String accSQL = this.getUpdateAccSQL(fLCInsureAccTraceSchema);
        map.put(accSQL, "UPDATE");
        return true;
    }

    /**
     * 得到更新账户分类表（LCInsureAccClass）余额的sql.
     * @param tLCInsureAccTraceSchema LCInsureAccTraceSchema
     * @return String
     */

    private String getUpdateAccClassSQL(LCInsureAccTraceSchema
                                        tLCInsureAccTraceSchema) {
        ExeSQL tExeSQL = new ExeSQL();
        String strSQL = "";
        String strSQL2 = "";
        String tReturn = "";
        double tInsuAccBala = 0;
        double tMoney = 0.00;
        //20060209 增加对保险帐户现金余额的赔付后的判断 P.D
        tInsuAccBala = Double.parseDouble(new DecimalFormat("0.00").format(
                tInsuAccBala));

        strSQL2 = " select nvl(InsuAccBala,0) from LCInsureAccClass where"
                  + " PolNo= '" + tLCInsureAccTraceSchema.getPolNo()
                  + "' and PayPlanCode='" +
                  tLCInsureAccTraceSchema.getPayPlanCode()
                  + "' and InsuAccNo='" + tLCInsureAccTraceSchema.getInsuAccNo()
                  + "' and AccAscription='1'";

        tReturn = StrTool.cTrim(tExeSQL.getOneValue(strSQL2));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getUpdateAccClassSQL";
            tError.errorMessage = "得到保险帐户现金余额错误!" + strSQL2;
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            tInsuAccBala = Double.parseDouble(tReturn);
        }
        if (tInsuAccBala + tLCInsureAccTraceSchema.getMoney() <= 0) {
            tMoney = 0.00;
        } else {
            tMoney = tInsuAccBala + tLCInsureAccTraceSchema.getMoney();
        }

        if (tLCInsureAccTraceSchema.getMoneyType().equals("PX") ||
            tLCInsureAccTraceSchema.getMoneyType().equals("SX")) {
            strSQL = "update LCInsureAccClass set InsuAccBala='" + tMoney +
                     "', sumpaym=sumpaym+" + tLCInsureAccTraceSchema.getMoney() +
//                    ", modifyDate='" + PubFun.getCurrentDate() + "', modifyTime='" + PubFun.getCurrentTime() + "'" +
                     ", Operator='" + mG.Operator + "', baladate='" +
                     tLCInsureAccTraceSchema.getPayDate() + "', balatime='" +
                     PubFun.getCurrentTime() + "'" +
                     " where PolNo='" + tLCInsureAccTraceSchema.getPolNo() +
                     "' and PayPlanCode='" +
                     tLCInsureAccTraceSchema.getPayPlanCode() + "' and" +
                     " InsuAccNo='" + tLCInsureAccTraceSchema.getInsuAccNo() +
                     "' and AccAscription='1' and " + m + "=" + m;
            logger.debug("Update AccClass SQL:" + strSQL);
            m++;
        } else {
            strSQL = "update LCInsureAccClass set InsuAccBala='" + tMoney +
                     "', sumpaym=sumpaym+" + tLCInsureAccTraceSchema.getMoney() +
//                    ",modifyDate='" + PubFun.getCurrentDate() + "', modifyTime='" + PubFun.getCurrentTime() + "'" +
                     ",Operator='" + mG.Operator + "'" +
                     " where PolNo='" + tLCInsureAccTraceSchema.getPolNo() +
                     "' and PayPlanCode='" +
                     tLCInsureAccTraceSchema.getPayPlanCode() + "' and" +
                     " InsuAccNo='" + tLCInsureAccTraceSchema.getInsuAccNo() +
                     "' and AccAscription='1' and " + m + "=" + m;
            logger.debug("Update AccClass SQL:" + strSQL);
            m++;
        }
        return strSQL;
    }


    /**
     * 得到更新账户主表(LCInsureAcc)余额的sql.
     * @param tLCInsureAccTraceSchema LCInsureAccTraceSchema
     * @return String
     */

    private String getUpdateAccSQL(LCInsureAccTraceSchema
                                   tLCInsureAccTraceSchema) {
        ExeSQL tExeSQL = new ExeSQL();
        String strSQL = "";
        String strSQL2 = "";
        String tReturn = "";
        double tInsuAccBala = 0;
        double tMoney = 0.00;
        //20060209 增加对保险帐户表现金余额的赔付后的判断 P.D
        tInsuAccBala = Double.parseDouble(new DecimalFormat("0.00").format(
                tInsuAccBala));

        strSQL2 = " select nvl(InsuAccBala,0) from LCInsureAcc "
                  + " where PolNo='" + tLCInsureAccTraceSchema.getPolNo() + "'"
                  + " and InsuAccNo='" + tLCInsureAccTraceSchema.getInsuAccNo() +
                  "'";

        tReturn = StrTool.cTrim(tExeSQL.getOneValue(strSQL2));
        if (tExeSQL.mErrors.needDealError()) {
            this.mErrors.copyAllErrors(tExeSQL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimCalAutoBL";
            tError.functionName = "getUpdateAccSQL";
            tError.errorMessage = "得到保险帐户表现金余额错误!" + strSQL2;
            this.mErrors.addOneError(tError);
        }
        if (tReturn != null && tReturn.length() > 0 && !tReturn.equals("")) {
            tInsuAccBala = Double.parseDouble(tReturn);
        }
        if (tInsuAccBala + tLCInsureAccTraceSchema.getMoney() <= 0) {
            tMoney = 0.00;
        } else {
            tMoney = tInsuAccBala + tLCInsureAccTraceSchema.getMoney();
        }
        if (tLCInsureAccTraceSchema.getMoneyType().equals("PX") ||
            tLCInsureAccTraceSchema.getMoneyType().equals("SX")) {
            strSQL = "update LCInsureAcc set InsuAccBala= '" +
                     tMoney + "', sumpaym=sumpaym+" +
                     tLCInsureAccTraceSchema.getMoney() +
//                    " , modifyDate='" + PubFun.getCurrentDate() +
//                    "' , modifyTime='" + PubFun.getCurrentTime() + "'" +
                     " , Operator='" + mG.Operator + "', baladate='" +
                     tLCInsureAccTraceSchema.getPayDate() + "', balatime='" +
                     PubFun.getCurrentTime() + "'" +
                     " where PolNo='" + tLCInsureAccTraceSchema.getPolNo() +
                     "'" +
                     " and InsuAccNo='" + tLCInsureAccTraceSchema.getInsuAccNo() +
                     "' and " + n + "=" + n;
            logger.debug("Update Acc SQL:" + strSQL);
            n++;
        } else {
            strSQL = "update LCInsureAcc set InsuAccBala='" +
                     tMoney + "', sumpaym=sumpaym+" +
                     tLCInsureAccTraceSchema.getMoney() +
//                    " , modifyDate='" + PubFun.getCurrentDate() +
//                    "' , modifyTime='" + PubFun.getCurrentTime() + "'" +
                     " , Operator='" + mG.Operator + "' " +
                     " where PolNo='" + tLCInsureAccTraceSchema.getPolNo() +
                     "'" +
                     " and InsuAccNo='" + tLCInsureAccTraceSchema.getInsuAccNo() +
                     "' and " + n + "=" + n;
            logger.debug("Update Acc SQL:" + strSQL);
            n++;
        }
        return strSQL;
    }

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－过程中－－－－－帐户保额冲减－－－－－续涛加－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */
    /**
     * 处理帐户型的保额冲减
     */
    private boolean dealAccount(LLClaimDetailSchema pLLClaimDetailSchema) {
        String tPol = pLLClaimDetailSchema.getPolNo();
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.1
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
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

        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());

        String tRgtDate = tLLRegisterSchema.getRgtDate().substring(0, 10);

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.4 查出该保单的所有帐户
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LCInsureAccDB tLCInsureAccDB = new LCInsureAccDB();
        String strSql = "select * from LCInsureAcc where 1=1"
                        + " and PolNo ='" + tPol + "'";

        LCInsureAccSet tLCInsureAccSet = tLCInsureAccDB.executeQuery(strSql);
        if (tLCInsureAccDB.mErrors.needDealError()) {
            CError.buildErr(this, "保单号:[" + tPol + "]保险帐户信息查询失败!");
            return false;
        }
        if (tLCInsureAccSet == null || tLCInsureAccSet.size() < 1) {
            CError.buildErr(this, "保单号:[" + tPol + "]保险帐户信息查询为空!");
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.5 查出该保单的所有帐户信息LCInsureAcc
         *
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LCInsureAccSet tLCInsureAccSaveSet = new LCInsureAccSet();
        for (int i = 1; i <= tLCInsureAccSet.size(); i++) {
            LCInsureAccSchema tLCInsureAccSchema = tLCInsureAccSet.get(i);

            double tAmnt = 0.0; //计算出的有效保额,包括本金和利息
            double tInsuAccBala = 0.0; //帐户现金余额
            double tInsuAccGetMoney = 0; //账户可领金额

            tAmnt = pLLClaimDetailSchema.getAmnt();
            tInsuAccBala = tLCInsureAccSchema.getInsuAccBala();
            tInsuAccGetMoney = tLCInsureAccSchema.getInsuAccGetMoney();

            /* 帐户现金余额[InsuAccBala] - 账户可领金额[InsuAccGetMoney] */
            tInsuAccBala = tLCInsureAccSchema.getInsuAccBala() -
                           tLCInsureAccSchema.getInsuAccGetMoney();

            /* 帐户现金余额 */
            tLCInsureAccSchema.setInsuAccBala(tAmnt);

            /* 累计领取 */
            tLCInsureAccSchema.setSumPaym(tLCInsureAccSchema.getSumPaym() +
                                          tAmnt);
            /* 设置账户可领金额 */
            tLCInsureAccSchema.setInsuAccGetMoney(tLCInsureAccSchema.
                                                  getInsuAccGetMoney() + tAmnt);

            tLCInsureAccSchema.setBalaDate(tRgtDate);
            tLCInsureAccSchema.setModifyDate(this.CurrentDate);
            tLCInsureAccSchema.setModifyTime(this.CurrentTime);
            tLCInsureAccSaveSet.add(tLCInsureAccSchema);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No1.5.1 查出保险账户分类表信息LCInsureAccClass
             *
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LCInsureAccClassDB tLCInsureAccClassDB = new LCInsureAccClassDB();
            tLCInsureAccClassDB.setPolNo(tLCInsureAccSchema.getPolNo());
            tLCInsureAccClassDB.setInsuAccNo(tLCInsureAccSchema.getInsuAccNo());
            LCInsureAccClassSet tLCInsureAccClassSet = tLCInsureAccClassDB.
                    query();

            LCInsureAccClassSet tLCInsureAccClassSaveSet = new
                    LCInsureAccClassSet();
            for (int m = 1; m <= tLCInsureAccClassSet.size(); m++) {
                LCInsureAccClassSchema tLCInsureAccClassSchema =
                        tLCInsureAccClassSet.get(m);

                /* 累计领取 */
                tLCInsureAccClassSchema.setSumPaym(tLCInsureAccClassSchema.
                        getSumPay());
                /* 设置账户可领金额 */
                tLCInsureAccClassSchema.setInsuAccGetMoney(tLCInsureAccSchema.
                        getInsuAccBala());

                tLCInsureAccClassSchema.setBalaDate(tRgtDate);
                tLCInsureAccClassSchema.setModifyDate(this.CurrentDate);
                tLCInsureAccClassSchema.setModifyTime(this.CurrentTime);
                tLCInsureAccClassSaveSet.add(tLCInsureAccClassSchema);
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No1.5.2 对保险帐户表记价履历表LCInsureAccTrace进行操作
             *  加一条正的利息信息
             *  加一条负的本金 + 利息信息
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LCInsureAccTraceSet tLCInsureAccTraceSet = new LCInsureAccTraceSet();

            LCInsureAccTraceSchema tLCInsureAccTraceLXSchema = new
                    LCInsureAccTraceSchema();
            String sLimit = PubFun.getNoLimit(tLCInsureAccSchema.getManageCom());
            String serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);

            tLCInsureAccTraceLXSchema.setGrpContNo(tLCInsureAccSchema.getContNo());
            /*集体合同号码*/
            tLCInsureAccTraceLXSchema.setGrpPolNo(tLCInsureAccSchema.
                                                  getGrpPolNo()); /*集体保单险种号码*/
            tLCInsureAccTraceLXSchema.setContNo(tLCInsureAccSchema.getContNo());
            /*合同号码*/
            tLCInsureAccTraceLXSchema.setPolNo(tLCInsureAccSchema.getPolNo());
            /*保单险种号码*/
            tLCInsureAccTraceLXSchema.setSerialNo(serNo); /*流水号*/

            tLCInsureAccTraceLXSchema.setInsuAccNo(tLCInsureAccSchema.
                    getInsuAccNo()); /*保险帐户号码*/
            /*流水号*/
            tLCInsureAccTraceLXSchema.setRiskCode(tLCInsureAccSchema.
                                                  getRiskCode()); /*险种编码*/
            tLCInsureAccTraceLXSchema.setPayPlanCode("902020"); /*交费计划编码*/
            tLCInsureAccTraceLXSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
            /*对应其它号码*/
            tLCInsureAccTraceLXSchema.setOtherType("1");
            tLCInsureAccTraceLXSchema.setAccAscription("0");
            tLCInsureAccTraceLXSchema.setMoneyType("LX");
            double tLX = Arith.round((tAmnt - tInsuAccBala), 2);
            tLCInsureAccTraceLXSchema.setMoney(tLX); /*本次金额*/
            tLCInsureAccTraceLXSchema.setUnitCount("0"); /*本次单位数*/
            tLCInsureAccTraceLXSchema.setPayDate(tRgtDate); /*交费日期*/
            tLCInsureAccTraceLXSchema.setState(tLCInsureAccSchema.getState());
            /*状态*/
            tLCInsureAccTraceLXSchema.setManageCom(tLCInsureAccSchema.
                    getManageCom());
            tLCInsureAccTraceLXSchema.setOperator(tLCInsureAccSchema.
                                                  getOperator());
            tLCInsureAccTraceLXSchema.setMakeDate(this.CurrentDate);
            tLCInsureAccTraceLXSchema.setMakeTime(this.CurrentTime);
            tLCInsureAccTraceLXSchema.setModifyDate(this.CurrentDate);
            tLCInsureAccTraceLXSchema.setModifyTime(this.CurrentTime);
            tLCInsureAccTraceLXSchema.setFeeCode("100001");
            tLCInsureAccTraceLXSchema.setAccAlterNo("0");
            tLCInsureAccTraceLXSchema.setAccAlterType("0");
            tLCInsureAccTraceSet.add(tLCInsureAccTraceLXSchema);

            /*存本金*/
            LCInsureAccTraceSchema tLCInsureAccTraceBJLXSchema = new
                    LCInsureAccTraceSchema();
            serNo = PubFun1.CreateMaxNo("SERIALNO", sLimit);

            tLCInsureAccTraceBJLXSchema.setGrpContNo(tLCInsureAccSchema.
                    getContNo()); /*集体合同号码*/
            tLCInsureAccTraceBJLXSchema.setGrpPolNo(tLCInsureAccSchema.
                    getGrpPolNo()); /*集体保单险种号码*/
            tLCInsureAccTraceBJLXSchema.setContNo(tLCInsureAccSchema.getContNo());
            /*合同号码*/
            tLCInsureAccTraceBJLXSchema.setPolNo(tLCInsureAccSchema.getPolNo());
            /*保单险种号码*/
            tLCInsureAccTraceBJLXSchema.setSerialNo(serNo); /*流水号*/

            tLCInsureAccTraceBJLXSchema.setInsuAccNo(tLCInsureAccSchema.
                    getInsuAccNo()); /*保险帐户号码*/
            /*流水号*/
            tLCInsureAccTraceBJLXSchema.setRiskCode(tLCInsureAccSchema.
                    getRiskCode()); /*险种编码*/
            tLCInsureAccTraceBJLXSchema.setPayPlanCode("902020"); /*交费计划编码*/
            tLCInsureAccTraceBJLXSchema.setOtherNo(tLCInsureAccSchema.getPolNo());
            /*对应其它号码*/
            tLCInsureAccTraceBJLXSchema.setOtherType("1");
            tLCInsureAccTraceBJLXSchema.setAccAscription("0");
            tLCInsureAccTraceBJLXSchema.setMoneyType("TF");
            tLCInsureAccTraceBJLXSchema.setMoney( -tAmnt); /*本次金额*/
            tLCInsureAccTraceBJLXSchema.setUnitCount("0"); /*本次单位数*/
            tLCInsureAccTraceBJLXSchema.setPayDate(tRgtDate); /*交费日期*/
            tLCInsureAccTraceBJLXSchema.setState(tLCInsureAccSchema.getState());
            /*状态*/
            tLCInsureAccTraceBJLXSchema.setManageCom(tLCInsureAccSchema.
                    getManageCom());
            tLCInsureAccTraceBJLXSchema.setOperator(tLCInsureAccSchema.
                    getOperator());
            tLCInsureAccTraceBJLXSchema.setMakeDate(this.CurrentDate);
            tLCInsureAccTraceBJLXSchema.setMakeTime(this.CurrentTime);
            tLCInsureAccTraceBJLXSchema.setModifyDate(this.CurrentDate);
            tLCInsureAccTraceBJLXSchema.setModifyTime(this.CurrentTime);
            tLCInsureAccTraceBJLXSchema.setFeeCode("100001");
            tLCInsureAccTraceBJLXSchema.setAccAlterNo("0");
            tLCInsureAccTraceBJLXSchema.setAccAlterType("0");
            tLCInsureAccTraceSet.add(tLCInsureAccTraceBJLXSchema);

            map.put(tLCInsureAccClassSaveSet, "DELETE&INSERT");
            map.put(tLCInsureAccTraceSet, "DELETE&INSERT");
        }

        map.put(tLCInsureAccSaveSet, "DELETE&INSERT");

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－保额冲减－－－－－续涛加－－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－开始－－－－－－－－终止责任－－－－－续涛修改－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */

    /**
     * 处理3：遍历LLClaimDetail表中每一个给付标志GiveType为0（给付）的保项，然后根据
     * 给付责任编码到LMDutyGetClm表中去找AfterGet，给付后动作这个信息做如下操作
     */
    private boolean dealData03() {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 循环保项信息
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        for (int i = 1; i <= mLLClaimDetailSet.size(); i++) {
            LLClaimDetailSchema tLLClaimDetailSchema = mLLClaimDetailSet.get(i);

            LMDutyGetClmDB tLMDutyGetClmDB = new LMDutyGetClmDB();
            LMDutyGetClmSet tLMDutyGetClmSet = new LMDutyGetClmSet();
            String tSql = "";

            tSql = "select * from LMDutyGetClm where 1 = 1 "
                   + " and getdutycode = '" +
                   tLLClaimDetailSchema.getGetDutyCode() + "'"
                   + " and GetDutyKind = '" +
                   tLLClaimDetailSchema.getGetDutyKind() + "'"
                   ;
            tLMDutyGetClmSet = tLMDutyGetClmDB.executeQuery(tSql);
            if (tLMDutyGetClmSet == null && tLMDutyGetClmSet.size() == 0) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmPassBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询责任给付赔付数据时出错!";
                this.mErrors.addOneError(tError);
                logger.debug(
                        "------------------------------------------------------");
                logger.debug(
                        "--LLClaimConfirmPassBL.dealData03()--查询责任给付赔付数据时发生错误!" +
                        tSql);
                logger.debug(
                        "------------------------------------------------------");
                return false;
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No2.0 进行终止判断
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            for (int j = 1; j <= tLMDutyGetClmSet.size(); j++) {

                LMDutyGetClmSchema tLMDutyGetClmSchema = tLMDutyGetClmSet.get(j);
                String tAfterGet = StrTool.cTrim(tLMDutyGetClmSchema.
                                                 getAfterGet());

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No5.1
                 * AfterGet＝000 无动作
                 * AfterGet＝001 [审批通过]保额递减，只冲减保额
                 * AfterGet＝003 无条件销户时,终止该合同(包括所有的险种)
                 * AfterGet＝004 最后一次给付销户,判断保额是否冲减完毕，如果冲减完毕执行003的动作
                 * AfterGet＝005 [待定]
                 * AfterGet＝006 [审批通过]终止该责任给付时,终止LCGet的数据
                 * AfterGet＝007 [待定]终止该责任时：根据DutyCode的前六位，在LCDuty表中查找总数，如果与总数相等，
                 *  则终止LCContState表中的状态,终止本险种.
                 * AfterGet＝008  终止本险种
                 *
                 *
                 * LMRiskApp.SubRiskFlag='S'[附险],LMDutyGetClm.EffectOnMainRisk='01',终止本身及主险
                 *
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */

                if (tAfterGet.equals("006")) {
                    setLCGetEndState(tLLClaimDetailSchema);

                } else if (tAfterGet.equals("007")) {

                }

            }
        }
        return true;
    }

    /**
     * 在LCGet表中找出相应记录并将其GetEndState状态置为1终止
     */
    private boolean setLCGetEndState(LLClaimDetailSchema pLLClaimDetailSchema) {

        LCGetDB tLCGetDB = new LCGetDB();
        tLCGetDB.setPolNo(pLLClaimDetailSchema.getPolNo());
        tLCGetDB.setDutyCode(pLLClaimDetailSchema.getDutyCode());
        tLCGetDB.setGetDutyCode(pLLClaimDetailSchema.getGetDutyCode());
        LCGetSet tLCGetSet = tLCGetDB.query();

        if (tLCGetDB.mErrors.needDealError()) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "setLCGetEndState";
            tError.errorMessage = "查询领取项信息失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (tLCGetSet.size() != 1) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLCGetDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "setLCGetEndState";
            tError.errorMessage = "查询领取项信息不唯一!";
            this.mErrors.addOneError(tError);
            return false;
        }
        LCGetSchema tLCGetSchema = new LCGetSchema();
        tLCGetSchema = tLCGetSet.get(1);
        tLCGetSchema.setGetEndState("1");
        tLCGetSchema.setOperator(mG.Operator);
        tLCGetSchema.setModifyDate(this.CurrentDate);
        tLCGetSchema.setModifyTime(this.CurrentTime);

        map.put(tLCGetSchema, "UPDATE");

        return true;
    }


    /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     *
     * －－－－－－－－结束－－－－－－－－终止责任－－－－－续涛修改－－－－－－－－－－－
     *
     * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
     */


    /**
     * 处理5.生成财务数据处理
     * a) 将LJSGet应付总表、LJSGetClaim赔付应付表的数据先转移到LJAGet实付总表，
     *    LJAGetClaim赔付实付表后，删除应付数据
     * b) 实付号：PubFun1.CreateMaxNo("GETNO", StrLimit);
     * c) 将LLbnf表中的，CasePayFlag保险金支付标志置为1，已支付
     * d) 将LLBalance表中的，PayFlag金支付标志置为1，已支付
     */
    private boolean dealData05() {
        /**---------------------------------------------------------------------
         * 统括保单相应处理
         * 取到该团体保单指定机构存到应付总表的PolicyCom
         * 新增 2006-02-22 P.D
         *----------------------------------------------------------------------*/
        String tMCom = ""; //保单理赔机构
        String tGrpContNo = "";
        String trgtobj = "";
        String tavalireason = "";
        String tRiskCode = "";
        String tPolNo = "";
//        String tSql =
//                "select distinct grpcontno,mngcom from llclaimpolicy where caseno = '" +
//                mClmNo + "'";
        String tSql =
                "select distinct grpcontno,mngcom,rgtobj,avalireason,riskcode from llreport where rptno = '" +
                mClmNo + "'";

        ExeSQL tExeSQL = new ExeSQL();
        SSRS tSSRS2 = tExeSQL.execSQL(tSql);
        if (tExeSQL.mErrors.needDealError()) {
            CError.buildErr(this, "未查询到团体保单号!" + tSql);
        } else {
            tGrpContNo = tSSRS2.GetText(1, 1);
            tMCom = tSSRS2.GetText(1, 2);
            trgtobj = tSSRS2.GetText(1, 3);
            tavalireason = tSSRS2.GetText(1, 4);
            tRiskCode = tSSRS2.GetText(1, 5);
        }

        String tManageCom = ""; //保单投保机构
        String tGrpName = "";
        String tAppntNo = "";
        String tSql2 =
                "select distinct ManageCom,grpname,appntno from lcgrpcont where grpcontno = '" +
                tGrpContNo + "'";
        SSRS tSSRS21 = tExeSQL.execSQL(tSql2);
        if (tSSRS21.getMaxRow() <= 0) {
            CError.buildErr(this, "未查询到保单机构!" + tSql2);
        } else {
            tManageCom = tSSRS21.GetText(1, 1);
            tGrpName = tSSRS21.GetText(1, 2);
            tAppntNo = tSSRS21.GetText(1, 3);
        }

        /**---------------------------------------------------------------------BEG
         * No.1 将LJSGet应付总表、LJSGetClaim赔付应付表的数据先转移到LJAGet实付总表，
         *      LJAGetClaim赔付实付表后，删除应付数据
         *----------------------------------------------------------------------*/
        //应付总表
        LJSGetDB tLJSGetDB = new LJSGetDB();
        tLJSGetDB.setOtherNo(mClmNo);
        tLJSGetDB.setOtherNoType("5");
        LJSGetSet tLJSGetSet = new LJSGetSet();
        tLJSGetSet = tLJSGetDB.query();
        if (tLJSGetSet != null && tLJSGetSet.size() != 0) {
            LJAGetSchema tLJAGetSchema = new LJAGetSchema();
            mLJAGetSet.add(tLJAGetSchema);
            ref.transFields(mLJAGetSet, tLJSGetSet);
            for (int j = 1; j <= tLJSGetSet.size(); j++) {
                //String tNo = PubFun1.CreateMaxNo("GETNO", 10); //生成实付号
                LJSGetSchema tLJSGetSchema = new LJSGetSchema();
                tLJSGetSchema = tLJSGetSet.get(j);
                String tNo = PubFun1.CreateMaxNo("GETNO", PubFun.getNoLimit(mG.ManageCom)); // 生成实付号
                logger.debug("==========tNo====" + tNo);
                mLJAGetSet.get(j).setActuGetNo(tNo);
                mLJAGetSet.get(j).setShouldDate(CurrentDate); //应付日期
                mLJAGetSet.get(j).setOperator(mG.Operator);
                mLJAGetSet.get(j).setMakeDate(CurrentDate);
                mLJAGetSet.get(j).setMakeTime(CurrentTime);
                mLJAGetSet.get(j).setModifyDate(CurrentDate);
                mLJAGetSet.get(j).setModifyTime(CurrentTime);
                mLJAGetSet.get(j).setPolicyCom(tMCom); //保单理赔机构
                mLJAGetSet.get(j).setManageCom(tManageCom); //保单机构
                //mLJAGetSet.get(j).setDrawer(tGrpName); //投保单位名称 2003-03-22 新加 P.D
                //mLJAGetSet.get(j).setDrawerID(tAppntNo); //投保单位客户号 2003-03-22 新加 P.D
                mLJAGetSet.get(j).setOperState("0");
//                mLJAGetSet.get(j).setConfDate(CurrentDate);//财务确认日期
                
                
            	/**
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.0 根据赔案号，给付通知书号查询受益人表中的记录 `
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        		 */
        		LLBnfDB tLLBnfDB = new LLBnfDB();
        		tLLBnfDB.setClmNo(mClmNo);
        		tLLBnfDB.setOtherNo(tLJSGetSchema.getGetNoticeNo()); // 给付通知书号
        		tLLBnfDB.setBnfKind("A");
        		LLBnfSet tLLBnfSet = tLLBnfDB.query();
        		if (tLLBnfDB.mErrors.needDealError()) {
        			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + tLJSGetSchema.getGetNoticeNo()
        					+ "]的受益人记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!"
        					+ tLLBnfDB.mErrors.getError(0).errorMessage);
        			return false;
        		}

        		if (tLLBnfSet.size() == 0) {
        			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + tLJSGetSchema.getGetNoticeNo()
        					+ "]的受益人记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!");
        			return false;
        		}

        		/**
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No4.1 将受益人表中的记录 置值 实付号 `
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        		 */
        		LLBnfSet tLLBnfSaveSet = new LLBnfSet();
        		for (int i = 1; i <= tLLBnfSet.size(); i++) {
        			
        			LLBnfSchema tLLBnfSchema = tLLBnfSet.get(i);
        			tLLBnfSchema.setOtherNo(tNo);
        			tLLBnfSchema.setOtherNoType("5");
        			tLLBnfSchema.setCasePayFlag("1");// 保险金支付标志,0未支付,1已支付
        			tLLBnfSchema.setOperator(mG.Operator);
        			tLLBnfSchema.setModifyDate(CurrentDate);
        			tLLBnfSchema.setModifyTime(CurrentTime);
        			tLLBnfSaveSet.add(tLLBnfSchema);
        			
        			tLLBnfSchema=null;
        		}
        		map.put(tLLBnfSaveSet, "DELETE&INSERT");
        		
        		
        		/**
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.0 根据赔案号，给付通知书号查询受益人汇总表中的记录 `
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        		 */
        		LLBnfGatherDB tLLBnfGatherDB = new LLBnfGatherDB();
        		tLLBnfGatherDB.setClmNo(mClmNo);
        		tLLBnfGatherDB.setBnfKind("A");
        		tLLBnfGatherDB.setOtherNo(tLJSGetSchema.getGetNoticeNo()); // 给付通知书号
        		
        		LLBnfGatherSet tLLBnfGatherSet = tLLBnfGatherDB.query();
        		
        		if (tLLBnfGatherDB.mErrors.needDealError()) {
        			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + tLJSGetSchema.getGetNoticeNo()
        					+ "]的受益人汇总记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!"
        					+ tLLBnfDB.mErrors.getError(0).errorMessage);
        			return false;
        		}

        		if (tLLBnfGatherSet.size() == 0) {
        			this.mErrors.addOneError("审批通过时，查询该赔案给付通知书号:[" + tLJSGetSchema.getGetNoticeNo()
        					+ "]的受益人汇总记录数为[" + tLLBnfSet.size() + "]，发生错误!!!!");
        			return false;
        		}

        		/**
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ No5.1 将受益人汇总表中的记录 置值 实付号 `
        		 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
        		 */
        		LLBnfGatherSet tLLBnfGatherSaveSet = new LLBnfGatherSet();
        		LLBnfGatherSchema tLLBnfGatherSchema =null;
        		for (int i = 1; i <= tLLBnfGatherSet.size(); i++) {
        			
        			tLLBnfGatherSchema = tLLBnfGatherSet.get(i);
        			tLLBnfGatherSchema.setOtherNo(tNo);
        			tLLBnfGatherSchema.setOtherNoType("5");
        			tLLBnfGatherSchema.setCasePayFlag("1");// 保险金支付标志,0未支付,1已支付
        			tLLBnfGatherSchema.setOperator(mG.Operator);
        			tLLBnfGatherSchema.setModifyDate(CurrentDate);
        			tLLBnfGatherSchema.setModifyTime(CurrentTime);
        			tLLBnfGatherSaveSet.add(tLLBnfGatherSchema);
        			
        			tLLBnfGatherSchema=null;
        		}
        		map.put(tLLBnfGatherSaveSet, "DELETE&INSERT");

                if (tavalireason=="02"||tavalireason.equals("02"))
                {
                   if (trgtobj=="10"||trgtobj.equals("10"))
                   {
                      String gpolsql="select polno from lcpol where grpcontno='"+tGrpContNo+"' and exists (select * from lmriskapp where riskcode='"+tRiskCode+"' and risktype6='1')  and poltypeflag='2' ";
                      tPolNo=tExeSQL.getOneValue(gpolsql);
                   }
                }

                //赔付应付表
                LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet();
                LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB();
                tLJSGetClaimDB.setOtherNo(mClmNo);
                tLJSGetClaimDB.setOtherNoType("5");
                tLJSGetClaimDB.setGetNoticeNo(tLJSGetSet.get(j).getGetNoticeNo());
                LJSGetClaimSet tLJSGetClaimSet = new LJSGetClaimSet();
                tLJSGetClaimSet = tLJSGetClaimDB.query();
                if (tLJSGetClaimSet != null && tLJSGetClaimSet.size() != 0) {
                    LJAGetClaimSchema tLJAGetClaimSchema = new
                            LJAGetClaimSchema();
                    tLJAGetClaimSet.add(tLJAGetClaimSchema);
                    ref.transFields(tLJAGetClaimSet, tLJSGetClaimSet);
                    for (int k = 1; k <= tLJSGetClaimSet.size(); k++) {
                        tLJAGetClaimSet.get(k).setActuGetNo(tNo);
                        tLJAGetClaimSet.get(k).setOPConfirmCode(mG.Operator);
                        tLJAGetClaimSet.get(k).setOPConfirmDate(CurrentDate);
                        tLJAGetClaimSet.get(k).setOPConfirmTime(CurrentTime);
                        tLJAGetClaimSet.get(k).setOperator(mG.Operator);
                        tLJAGetClaimSet.get(k).setMakeDate(CurrentDate);
                        tLJAGetClaimSet.get(k).setMakeTime(CurrentTime);
                        tLJAGetClaimSet.get(k).setModifyDate(CurrentDate);
                        tLJAGetClaimSet.get(k).setModifyTime(CurrentTime);
                        tLJAGetClaimSet.get(k).setOperState("0");
//                        tLJAGetClaimSet.get(k).setConfDate(CurrentDate);//财务确认日期
                        if (tavalireason=="02"||tavalireason.equals("02"))
                        {
                           if (trgtobj=="10"||trgtobj.equals("10"))
                           {
                               tLJAGetClaimSet.get(k).setAccPolNo(tPolNo);
                           }else
                           {
                               tLJAGetClaimSet.get(k).setAccPolNo(tLJAGetClaimSet.get(k).getPolNo());
                           }
                        }
                        //如果支付金额为零,就不添加到实付表
                        //if (tLJAGetClaimSet.get(k).getPay() != 0) {
                            map.put(tLJAGetClaimSet.get(k), "DELETE&INSERT");
                        //}

                    }
                    //如果总给付金额为零,就不添加到实付表
                    //if (mLJAGetSet.get(j).getSumGetMoney() != 0) {
                        map.put(mLJAGetSet.get(j), "DELETE&INSERT");
                    //}
                    map.put(tLJSGetClaimSet, "DELETE");
                }
            }
            map.put(tLJSGetSet, "DELETE");
        }
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * No.2 将LLbnf表中的，CasePayFlag保险金支付标志置为1，已支付
         * --------------------------------------------------------------------*/
        String tUpdateSql1 = "";
        tUpdateSql1 = "update llbnf a set a.casepayflag = '1' where "
                      + "a.clmno = '" + mClmNo + "'";
        map.put(tUpdateSql1, "UPDATE");
        //----------------------------------------------------------------------END

        /**---------------------------------------------------------------------BEG
         * No.3 将LLBalance表中的，PayFlag金支付标志置为1，已支付
         * --------------------------------------------------------------------*/
        String tUpdateSql2 = "";
        tUpdateSql2 = "update LLBalance a set a.Payflag = '1' where "
                      + "a.clmno = '" + mClmNo + "'";
        map.put(tUpdateSql2, "UPDATE");
        //----------------------------------------------------------------------END
        return true;
    }

    /**
     * 处理6.合同处理:
     * 将LLContState表中的数据更新到LCContState表中
     * @return boolean
     */
    private boolean dealData06() {
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No1.0 查询理赔合同状态暂存表数据
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LLContStateDB tLLContStateQueryDB = new LLContStateDB();
        tLLContStateQueryDB.setClmNo(mClmNo);
        LLContStateSet tLLContStateQuerySet = tLLContStateQueryDB.query();
        if (tLLContStateQueryDB.mErrors.needDealError()) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLContStateQueryDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询理赔合同终止记录暂存表时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * No2.0 循环理赔合同状态暂存表数据,进行处理
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        LCContStateSet tLCContStateSaveSet = new LCContStateSet(); //合同状态正式表
        LLContStateSet tLLContStateSaveSet = new LLContStateSet(); //合同状态暂存表
        LCContSet tLCContSaveSet = new LCContSet(); //合同表
        LCPolSet tLCPolSaveSet = new LCPolSet(); //保单险种表

        for (int j = 1; j <= tLLContStateQuerySet.size(); j++) {

            LLContStateSchema tLLContStateQuerySchema = tLLContStateQuerySet.
                    get(j);

            LCContStateSchema tLCContStateSaveSchema = new LCContStateSchema();

            String tContNo = tLLContStateQuerySchema.getContNo();
            String tPolNo = tLLContStateQuerySchema.getPolNo();

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No3.0 删除合同状态正式表中旧的终止记录,并插入新的记录
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tSql = "delete from LCContState where 1=1 "
                          + " and ContNo='" + tContNo + "'"
                          + " and PolNo='" + tPolNo + "'"
                          + " and StateType='Terminate' and state='1'"
                          ;

            map.put(tSql, "DELETE");

            ref.transFields(tLCContStateSaveSchema, tLLContStateQuerySchema);

            tLCContStateSaveSchema.setStartDate(this.mAccDate);
            tLCContStateSaveSchema.setEndDate("");
            tLCContStateSaveSchema.setRemark("理赔合同终止处理");
            tLCContStateSaveSchema.setMakeDate(CurrentDate);
            tLCContStateSaveSchema.setMakeTime(CurrentTime);
            tLCContStateSaveSchema.setModifyDate(CurrentDate);
            tLCContStateSaveSchema.setMakeTime(CurrentTime);
            tLCContStateSaveSchema.setOperator(mG.Operator);
            tLCContStateSaveSet.add(tLCContStateSaveSchema);

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No4.0 将删除掉的合同状态正式表中旧的终止记录,同步复制到暂存表
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LCContStateDB tLCContStateExpDB = new LCContStateDB();
            tLCContStateExpDB.setContNo(tContNo);
            tLCContStateExpDB.setPolNo(tPolNo);
            tLCContStateExpDB.setStateType("Terminate");
            tLCContStateExpDB.setState("1");
            LCContStateSet tLCContStateExpSet = tLCContStateExpDB.query();

            if (tLCContStateExpDB.mErrors.needDealError()) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLCContStateExpDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmPassBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询合同状态C表的终止记录时出错!";
                this.mErrors.addOneError(tError);
                return false;
            }

            LCContStateSchema tLCContStateExpSchema = new LCContStateSchema();
            LLContStateSchema tLLContStateSaveSchema = new LLContStateSchema();

            for (int m = 1; m <= tLCContStateExpSet.size(); m++) {
                tLCContStateExpSchema = tLCContStateExpSet.get(m);
                String tStartDate = tLCContStateExpSchema.getStartDate().
                                    substring(0, 10);
                String tAccDate = this.mAccDate.substring(0, 10);

                /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 * No5.0 合同状态正式表中的终止记录 的时间 与暂存表相同,更改暂存表的状态信息
                 * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
                 */
                if (tStartDate.equals(tAccDate)) {
                    tLLContStateSaveSchema.setSchema(tLLContStateQuerySchema);
                    tLLContStateSaveSchema.setClmState("8");
                    tLLContStateSaveSchema.setRemark(
                            "8--审批通过时从LCContState中转来已经删除的数据,在本表中已存在");
                } else {
                    ref.transFields(tLLContStateSaveSchema,
                                    tLCContStateExpSchema);
                    tLLContStateSaveSchema.setClmNo(this.mClmNo);
                    tLLContStateSaveSchema.setClmState("9");
                    tLLContStateSaveSchema.setRemark(
                            "9--审批通过时从LCContState中转来已经删除的数据,在本表中不存在");
                }
                tLLContStateSaveSet.add(tLLContStateSaveSchema);
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No7.0 更新LCCont的AppFlag置为4
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            String tDealState = StrTool.cTrim(tLLContStateQuerySchema.
                                              getDealState());
            if (tDealState.length() > 0) {
                tDealState = tDealState.substring(0, 1);
            }

            LCContSchema tLCContSchema = new LCContSchema();
            tLCContSchema = mLLClaimPubFunBL.getLCCont(tContNo);
            if (tLCContSchema == null) {
                this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                return false;
            } else if (tLCContSchema != null && tDealState.equals("D")) {
                tLCContSchema.setAppFlag("4");
                tLCContSchema.setModifyDate(CurrentDate);
                tLCContSchema.setMakeTime(CurrentTime);
                tLCContSchema.setOperator(mG.Operator);
                tLCContSaveSet.add(tLCContSchema);
            }

            /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             * No8.0 更新LCPol的AppFlag置为4
             * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
             */
            LCPolSchema tLCPolSchema = new LCPolSchema();
            tLCPolSchema = mLLClaimPubFunBL.getLCPol(tPolNo);
            if (tLCPolSchema == null) {
                this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                return false;
            } else {
                tLCPolSchema.setAppFlag("4");
                tLCPolSchema.setModifyDate(CurrentDate);
                tLCPolSchema.setMakeTime(CurrentTime);
                tLCPolSchema.setOperator(mG.Operator);
                tLCPolSaveSet.add(tLCPolSchema);
            }

        }

        map.put(tLCContSaveSet, "UPDATE");
        map.put(tLCPolSaveSet, "UPDATE");
        map.put(tLCContStateSaveSet, "INSERT");
        map.put(tLLContStateSaveSet, "DELETE&INSERT");
        return true;
    }


    /**
     * 处理7.豁免处理:
     * 将LLExempt表的FreeFlag[免交标志], FreeStartDate[免交起期],
     * FreeEndDate [免交止期],更新到LCPrem表中对应的数据
     */
    private boolean dealData07() {
        LLExemptDB tLLExemptDB = new LLExemptDB();
        tLLExemptDB.setClmNo(mClmNo);
        LLExemptSet tLLExemptSet = new LLExemptSet();
        tLLExemptSet = tLLExemptDB.query();
        if (tLLExemptDB.mErrors.needDealError()) {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLExemptDB.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询保费豁免记录时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }

        LCPremSet tLCPremSet = new LCPremSet();
        for (int j = 1; j <= tLLExemptSet.size(); j++) {

            LCPremDB tLCPremDB = new LCPremDB();
            tLCPremDB.setPolNo(tLLExemptSet.get(j).getPolNo());
            tLCPremDB.setDutyCode(tLLExemptSet.get(j).getDutyCode());
            tLCPremDB.setPayPlanCode(tLLExemptSet.get(j).getPayPlanCode());

            if (tLCPremDB.getInfo() == false) {
                // @@错误处理
                this.mErrors.copyAllErrors(tLCPremDB.mErrors);
                CError tError = new CError();
                tError.moduleName = "UrgeNoticeOverdueBL";
                tError.functionName = "dealData";
                tError.errorMessage = "进行豁免处理时查询缴费信息失败!";
                this.mErrors.addOneError(tError);
                return false;
            }

            LCPremSchema tLCPremSchema = new LCPremSchema();
            tLCPremSchema.setSchema(tLCPremDB.getSchema());

            tLCPremSchema.setFreeFlag(tLLExemptSet.get(j).getFreeFlag());
            tLCPremSchema.setFreeStartDate(tLLExemptSet.get(j).getFreeStartDate());
            tLCPremSchema.setFreeEndDate(tLLExemptSet.get(j).getFreeEndDate());
            tLCPremSchema.setOperator(mG.Operator);
            tLCPremSchema.setModifyDate(CurrentDate);
            tLCPremSchema.setMakeTime(CurrentTime);
            tLCPremSet.add(tLCPremSchema);
        }

        map.put(tLCPremSet, "DELETE&INSERT");
        return true;
    }

    /**
     * 处理8.删除财务应收应付备份表对应数据:
     */
    private boolean dealData08() {
        //理赔应收总表暂存表LLJSPay对应应收总表LJSPay
        LLJSPayDB tLLJSPayDB = new LLJSPayDB();
        tLLJSPayDB.setClmNo(mClmNo);
        LLJSPaySet tLLJSPaySet = new LLJSPaySet();
        tLLJSPaySet = tLLJSPayDB.query();
        if (tLLJSPaySet != null && tLLJSPaySet.size() != 0) {
            for (int j = 1; j <= tLLJSPaySet.size(); j++) {
                LJSPayDB tLJSPayDB = new LJSPayDB();
                tLJSPayDB.setGetNoticeNo(tLLJSPaySet.get(j).getGetNoticeNo());
                LJSPaySet tLJSPaySet = new LJSPaySet();
                tLJSPaySet = tLJSPayDB.query();
                map.put(tLJSPaySet, "DELETE");
            }
        }
        //理赔应付总表暂存表LLJSGet对应付总表LJSGet
        LLJSGetDB tLLJSGetDB = new LLJSGetDB();
        tLLJSGetDB.setClmNo(mClmNo);
        LLJSGetSet tLLJSGetSet = new LLJSGetSet();
        tLLJSGetSet = tLLJSGetDB.query();
        if (tLLJSGetSet != null && tLLJSGetSet.size() != 0) {
            for (int j = 1; j <= tLLJSGetSet.size(); j++) {
                LJSGetDB tLJSGetDB = new LJSGetDB();
                tLJSGetDB.setGetNoticeNo(tLLJSGetSet.get(j).getGetNoticeNo());
                LJSGetSet tLJSGetSet = new LJSGetSet();
                tLJSGetSet = tLJSGetDB.query();
                map.put(tLJSGetSet, "DELETE");
            }
        }
        //理赔应收个人交费暂存表LLJSPayPerson对应收个人交费表LJSPayPerson
        LLJSPayPersonDB tLLJSPayPersonDB = new LLJSPayPersonDB();
        tLLJSPayPersonDB.setClmNo(mClmNo);
        LLJSPayPersonSet tLLJSPayPersonSet = new LLJSPayPersonSet();
        tLLJSPayPersonSet = tLLJSPayPersonDB.query();
        if (tLLJSPayPersonSet != null && tLLJSPayPersonSet.size() != 0) {
            for (int j = 1; j <= tLLJSPayPersonSet.size(); j++) {
                LJSPayPersonDB tLJSPayPersonDB = new LJSPayPersonDB();
                tLJSPayPersonDB.setGetNoticeNo(tLLJSPayPersonSet.get(j).
                                               getGetNoticeNo());
                LJSPayPersonSet tLJSPayPersonSet = new LJSPayPersonSet();
                tLJSPayPersonSet = tLJSPayPersonDB.query();
                map.put(tLJSPayPersonSet, "DELETE");
            }
        }
        //理赔给付表(生存领取_应付)暂存表LLJSGetDraw对应给付表(生存领取_应付)表LJSGetDraw
        LLJSGetDrawDB tLLJSGetDrawDB = new LLJSGetDrawDB();
        tLLJSGetDrawDB.setClmNo(mClmNo);
        LLJSGetDrawSet tLLJSGetDrawSet = new LLJSGetDrawSet();
        tLLJSGetDrawSet = tLLJSGetDrawDB.query();
        if (tLLJSGetDrawSet != null && tLLJSGetDrawSet.size() != 0) {
            for (int j = 1; j <= tLLJSGetDrawSet.size(); j++) {
                LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
                tLJSGetDrawDB.setGetNoticeNo(tLLJSGetDrawSet.get(j).
                                             getGetNoticeNo());
                LJSGetDrawSet tLJSGetDrawSet = new LJSGetDrawSet();
                tLJSGetDrawSet = tLJSGetDrawDB.query();
                map.put(tLJSGetDrawSet, "DELETE");
            }
        }
        return true;
    }

    /**
     * 处理9. 产生的数据插入到打印管理表:
     */
    private boolean dealData09() {
        ExeSQL tExeSQL = new ExeSQL();

        //查询立案信息
        LLCaseDB tLLCaseDB = new LLCaseDB();
        String tSSql = "select * from llcase where 1=1 "
                       + " and caseno = '" + mClmNo + "'";
        LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(tSSql);
        mCusNo = tLLCaseSet.get(1).getCustomerNo();
        mMngCom = tLLCaseSet.get(1).getMngCom();

        LLRegisterDB tLLRegisterDB = new LLRegisterDB();
        LLRegisterSchema tLLRegisterSchema = new LLRegisterSchema();
        tLLRegisterDB.setRgtNo(mClmNo);
        if (tLLRegisterDB.getInfo()) {
            tLLRegisterSchema.setSchema(tLLRegisterDB.getSchema());
        }
        mRgtDate = tLLRegisterSchema.getRgtDate(); //立案日期

//        tSSql = "select to_date(rgtdate,'yyyy-mm-dd') from llregister where 1=1 "
//                + " and rgtno = '" + mClmNo + "'";
//        mmRgtDate = tExeSQL.getOneValue(tSSql); //立案日期

        logger.debug("################################################");
        logger.debug("#mmRgtDate:" + mRgtDate);
        logger.debug("################################################");

        //查询赔付结论
        LLClaimDB tLLClaimDB = new LLClaimDB();
        tLLClaimDB.setClmNo(mClmNo);
        LLClaimSet tLLClaimSet = tLLClaimDB.query();
        String tGType = tLLClaimSet.get(1).getGiveType();
        if (tGType.equals("1")) { //赔案拒付
            if (!insertLOPRTManager("PCT005")) { //插入理赔决定通知书－拒付[赔案层面]
                return false;
            }
        } else { //赔案给付
            if (!insertLOPRTManager("PCT010")) { //插入批单-理赔给付批注[个人]
                return false;
            }
        }

        //插入理赔决定通知书－拒付[保项层面] llclaimdetail
        String tSSql06 = "select count(1) from llclaimdetail a where 1=1"
                         + " and a.ClmNo  = '" + mClmNo + "'"
                         + " and a.GiveType = '1' ";
        String tCount06 = tExeSQL.getOneValue(tSSql06);
        if (tCount06 != null && !tCount06.equals("0")) {
            if (!insertLOPRTManager("PCT006")) { //插入理赔决定通知书－拒付[保项层面]
                return false;
            }
        }

        //插入医疗理赔给付清单[个人] llclaimdetail
        String tSSql15 = "select count(1) from llclaimdetail a where 1=1"
                         + " and a.ClmNo  = '" + mClmNo + "'"
                         + " and a.GiveType != '1' "
                         + " and substr(a.GetDutyKind,2,2) = '00' ";
        String tCount15 = tExeSQL.getOneValue(tSSql15);
        if (tCount15 != null && !tCount15.equals("0")) {
            if (!insertLOPRTManager("PCT015")) { //插入医疗理赔给付清单[个人] llclaimdetail
                return false;
            }
        }

        //插入批单-合同处理批注 llclaimpolicy
        /**
         * zhangzheng 2009-02-11 
         * 团险没有合同处理,不用打印理赔批注
         */
//        String tSSql13 = "select count(1) from llclaimpolicy a where 1=1"
//                         + " and a.ClmNo  = '" + mClmNo + "'";
////        String tSSql13 = "select count(1) from LLBalance a where 1=1"
////                         + " and a.ClmNo  = '" + mClmNo + "'"
////                         + " and substr(a.FeeOperationType,1,1) in ('C') ";
//        String tCount13 = tExeSQL.getOneValue(tSSql13);
//        if (tCount13 != null && !tCount13.equals("0")) {
//            if (!insertLOPRTManager("PCT013")) { //插入批单-合同处理批注
//                return false;
//            }
//        }

        //插入批单-豁免保费批注 LLExempt
        String tSSql12 = "select count(1) from LLExempt where 1=1 "
                         + " and ClmNo = '" + mClmNo + "'";
        String tCount12 = tExeSQL.getOneValue(tSSql12);
        if (tCount12 != null && !tCount12.equals("0")) {
            if (!insertLOPRTManager("PCT012")) { //插入批单-豁免保费批注
                return false;
            }
        }

//        //插入批单-理赔预付保险金批注 LLPrepayClaim-----------------------已在预付确认时生成 周磊 2005-8-30 1:15
//        String tSSql14 = "select count(1) from LLPrepayClaim where 1=1 "
//                       + " and ClmNo = '" + mClmNo + "'";
//        String tCount14 = tExeSQL.getOneValue(tSSql14);
//        if (tCount14 != null && !tCount14.equals("0"))
//        {
//            if (!insertLOPRTManager("PCT014")) //插入批单-理赔预付保险金批注
//            {
//                return false;
//            }
//        }

        map.put(mLOPRTManagerSet, "DELETE&INSERT");
        return true;
    }

    /**
     * 判断赔案是否是死亡理赔
     * 给lcpol.state赋值 7
     * @return boolean
     */
    private boolean dealData0601() {
        LCPolSet mLCPolSet = new LCPolSet();
        LLAppClaimReasonDB mLLAppClaimReasonDB = new LLAppClaimReasonDB();
        mLLAppClaimReasonDB.setCaseNo(mClmNo);
        LLAppClaimReasonSet mLLAppClaimReasonSet = mLLAppClaimReasonDB.query();
        for (int i = 1; i <= mLLAppClaimReasonSet.size(); i++) {
            LLAppClaimReasonSchema mLLAppClaimReasonSchema = new
                    LLAppClaimReasonSchema();
            mLLAppClaimReasonSchema = mLLAppClaimReasonSet.get(i);
            if (mLLAppClaimReasonSchema.getReasonCode().substring(1,3).equals("02")) {
                LLClaimPolicyDB mLLClaimPolicyDB = new LLClaimPolicyDB();
                mLLClaimPolicyDB.setClmNo(mClmNo);
                mLLClaimPolicyDB.setInsuredNo(mLLAppClaimReasonSchema.getCustomerNo());
                LLClaimPolicySet mLLClaimPolicySet = mLLClaimPolicyDB.query();
                for (int t = 1; t <= mLLClaimPolicySet.size(); t++) {
                    LCPolSchema tLCPolSchema = new LCPolSchema();
                    tLCPolSchema = mLLClaimPubFunBL.getLCPol(mLLClaimPolicySet.
                            get(t).getPolNo());
                    if (tLCPolSchema == null) {
                        this.mErrors.copyAllErrors(mLLClaimPubFunBL.mErrors);
                        return false;
                    } else {
//                        tLCPolSchema.setAppFlag("4");
                        tLCPolSchema.setPolState("7");
                        tLCPolSchema.setModifyDate(CurrentDate);
                        tLCPolSchema.setMakeTime(CurrentTime);
                        tLCPolSchema.setOperator(mG.Operator);
                        mLCPolSet.add(tLCPolSchema);
                    }
                }
                //删除出险人下的催付数据
                 String sqldraw ="delete from ljsgetdraw where getdutycode in (select getdutycode from LMDutyGetAlive) and insuredno ='" +mLLAppClaimReasonSchema.getCustomerNo()+ "'";
                 map.put(sqldraw, "DELETE");
            }
        }
        map.put(mLCPolSet, "UPDATE");

        return true;
    }

    /**
     * 处理10. 对于帐户型的险种处理方式
     */
    private boolean dealData10() {

    	LLClaimPolicySet mLLClaimPolicySet=mLLClaimPubFunBL.getLLClaimPolicy(mClmNo);
    	
        if (mLLClaimPolicySet == null) {
        	
            // @@错误处理
        	CError.buildErr(this, "查询不到赔案:"+mClmNo+"的保单信息!");
            return false;
        }
        
        LMRiskSortDB tLMRiskSortDB=new LMRiskSortDB();
        LMRiskSortSet tLMRiskSortSet=null;
        
        for(int i=1;i<=mLLClaimPolicySet.size();i++)
        {
            tLMRiskSortDB.setRiskCode(mLLClaimPolicySet.get(i).getRiskCode());
            tLMRiskSortDB.setRiskSortType("27");
            tLMRiskSortSet = tLMRiskSortDB.query();
            
            if(tLMRiskSortSet.size()>0)
            {
            	//更新保险账户分类表
            	String sqlLCInsureAcc ="update LCInsureAcc set InsuAccBala=InsuAccBala-"+(mLLClaimPolicySet.get(i).getRealPay())+",modifydate='"+PubFun.getCurrentDate()+"',modifytime='"+PubFun.getCurrentTime()+"'"
            		                      + " where polno='"+mLLClaimPolicySet.get(i).getPolNo()+"' and InsuAccNo='"+tLMRiskSortSet.get(1).getRiskSortValue()+"'";
            	logger.debug("sqlLCInsureAcc:"+sqlLCInsureAcc);
            		
            	LCInsureAccClassDB tLCInsureAccClassDB=new LCInsureAccClassDB();
            	tLCInsureAccClassDB.setPolNo(mLLClaimPolicySet.get(i).getPolNo());
            	tLCInsureAccClassDB.setInsuAccNo(tLMRiskSortSet.get(1).getRiskSortValue());
            	
            	LCInsureAccClassSet tLCInsureAccClassSet=new LCInsureAccClassSet();
            	tLCInsureAccClassSet = tLCInsureAccClassDB.query();


                map.put(sqlLCInsureAcc, "UPDATE");
            	
            	if(tLCInsureAccClassSet.size()>0)
            	{
            		LCInsureAccClassSchema tLCInsureAccClassSchema = new LCInsureAccClassSchema();
            		tLCInsureAccClassSchema = tLCInsureAccClassSet.get(1);
            		tLCInsureAccClassSchema.setInsuAccBala(tLCInsureAccClassSchema.getInsuAccBala()-mLLClaimPolicySet.get(i).getRealPay());
            		tLCInsureAccClassSchema.setModifyDate(PubFun.getCurrentDate());
            		tLCInsureAccClassSchema.setModifyTime(PubFun.getCurrentTime());
            		
            		//插入一条账户轨迹
                    Reflections ref = new Reflections();

                    LCInsureAccTraceSchema tLCInsureAccTraceSchema = new LCInsureAccTraceSchema();
                    ref.transFields(tLCInsureAccTraceSchema, tLCInsureAccClassSchema);
                    String tLimit = PubFun.getNoLimit(tLCInsureAccClassSchema.getManageCom());
                    String serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
                    tLCInsureAccTraceSchema.setSerialNo(serNo);
                    tLCInsureAccTraceSchema.setFeeCode("633601");
                    tLCInsureAccTraceSchema.setMoney(-mLLClaimPolicySet.get(i).getRealPay());
                    tLCInsureAccTraceSchema.setOtherNo(mLLClaimPolicySet.get(i).getClmNo());
                    tLCInsureAccTraceSchema.setOtherType("3");//3-理赔赔案号
                    tLCInsureAccTraceSchema.setMoneyType("CM");
                    tLCInsureAccTraceSchema.setPayDate(PubFun.getCurrentDate());
                    tLCInsureAccTraceSchema.setMakeDate(PubFun.getCurrentDate());
                    tLCInsureAccTraceSchema.setMakeTime(PubFun.getCurrentTime());
                    tLCInsureAccTraceSchema.setModifyDate(PubFun.getCurrentDate());
                    tLCInsureAccTraceSchema.setModifyTime(PubFun.getCurrentTime());
                    tLCInsureAccTraceSchema.setOperator(mG.Operator);
                    tLCInsureAccTraceSchema.setManageCom(mG.ManageCom);
                    
                    map.put(tLCInsureAccClassSchema, "UPDATE");
                    map.put(tLCInsureAccTraceSchema, "INSERT");
            	}
            	else
            	{
            	     // @@错误处理
                	CError.buildErr(this, "查询不到赔案:"+mClmNo+",保单:"+mLLClaimPolicySet.get(i).getPolNo()+"的账户信息!");
                    return false;
            	}
  
            }
        }
        
    
 
        
        
        return true;
    }

    
    /**
     * 处理11. 对MMA项目需要保存公共限额的记录
     */
    private boolean dealData11() {
		/**
		 * 轨迹表
		 * **/
    	LLClmLimitAmntTraceDB tLLClmLimitAmntTraceDB = new LLClmLimitAmntTraceDB();
		tLLClmLimitAmntTraceDB.setCaseNo(mClmNo);
		LLClmLimitAmntTraceSet tLLClmLimitAmntTraceSet = tLLClmLimitAmntTraceDB.query();
		
    	
		/**
		 * 从LLClaimPolicy表获取保单的基本信息
		 **/
    	
		LLClaimPolicySet mLLClaimPolicySet=mLLClaimPubFunBL.getLLClaimPolicy(mClmNo);
		LLClaimPolicySchema tLLClaimPolicySchema=new LLClaimPolicySchema();	
	    if (mLLClaimPolicySet == null) {
	        // @@错误处理
	    	CError.buildErr(this, "查询不到赔案:"+mClmNo+"的保单信息!");
	    }else{
	    	tLLClaimPolicySchema =mLLClaimPolicySet.get(1);	
	    }
	    
		/**
		 * 查询改赔案下账单信息
		 * **/
    	LLCaseReceiptDB tLLCaseReceiptDB = new LLCaseReceiptDB();
		//tLLCaseReceiptDB.setCaseNo(mClmNo);
    	String ttsql="select * from LLCaseReceipt where clmno='"+mClmNo+"' order by makedate";
		tLLCaseReceiptDB.executeQuery(ttsql);
		//LLCaseReceiptSet tLLCaseReceiptSet = tLLCaseReceiptDB.query(); 
		LLCaseReceiptSet tLLCaseReceiptSet = tLLCaseReceiptDB.executeQuery(ttsql);
		
		if(tLLCaseReceiptSet==null||tLLCaseReceiptSet.size()==0){
			CError.buildErr(this, "没有查询到医疗账单记录！");	
		}else{
			
		for(int j=1;j<=tLLCaseReceiptSet.size();j++){
		
		LLClmLimitAmntTraceSchema tLLClmLimitAmntTraceSchema=new LLClmLimitAmntTraceSchema();		
		LLCaseReceiptSchema tLLCaseReceiptSchema=new LLCaseReceiptSchema();	
		tLLCaseReceiptSchema=	tLLCaseReceiptSet.get(j);
	    String tFeeItemCode=tLLCaseReceiptSchema.getFeeItemCode();
		ExeSQL tExeSQL = new ExeSQL();
		SSRS   tSSRS = new SSRS();
		String tFeeTypeSql="select othersign from ldcode where codetype='llmmahospfeetype'  and code='"+tFeeItemCode+"'";
		tSSRS =tExeSQL.execSQL(tFeeTypeSql);
		
		if(tSSRS.getMaxRow()<1){
			logger.debug("没有查到该账单类型的费用类型！");
			continue;
		}
		/**账单类型为A，那种限额型的
        *总限额类型的（A类型），还是日限额类型的（B类型）。
        *总限额类型的类似Sugical Fees-Major operation，报价时候只需指定一个限额值；日限额类型的，
   		*类似Daily Room per day limit和Maxinum number of days 都是成对出现，描述赔付限额的，
   		*前者描述日限额（B1类型），后者描述该赔付的天数限制（B2类型）
		**/
		else if(tSSRS.GetText(1, 1).equals("A")){
	     /**
	      * TotalAmnt是最终的理算金额
	      * tSSRS.GetText(1, 1)是账单录入金额
	      * 当tSSRS.GetText(1, 1)<=TotalAmnt时说明最终的账单金额小于理算金额
	      * 那么这个时候就需要将存在的限额和公共的理算要素值更新并保持理算轨迹
	      * **/	
    	 String tSql1="update lccontplandutyparam a set a.calfactorvalue=nvl((a.calfactorvalue-"+TotalAmnt+"),0) where " +
    	 		"  a.grpcontno='"+tLLClaimPolicySchema.getGrpContNo()+"'  " +
    	 		"  and a.riskcode='"+tLLClaimPolicySchema.getRiskCode()+"'  " +
    	 		"  and a.calfactor='"+tFeeItemCode+"' " +
    	 		"  and a.contplancode=(select contplancode from lcinsured where insuredno='"+tLLClaimPolicySchema.getInsuredNo()+"') ";
    	 logger.debug("tSql1====="+tSql1);
    	 map.put(tSql1, "UPDATE");

    	 /**
    	  * 判断是否存在公共限额的情况，如果有公共限额的情况，需要更新公共限额的值
    	  * 同时保存公共限额的轨迹
    	  * */ 
    	  // A公共限额处理
    	  String tSq10 = " select a.claimctrlcode,a.grpcontno,a.* from LCDutyClmCtrlRela a,LCClaimCtrl b where  " +
	      " a.claimctrlcode=b.claimctrlcode  and a.grpcontno=b.grpcontno  " +
	      " and a.grpcontno='"+tLLClaimPolicySchema.getGrpContNo()+"'  " +
	      " and a.riskcode='"+tLLClaimPolicySchema.getRiskCode()+"'  " +
	      " and a.contplancode=(select contplancode from lcinsured where insuredno='"+tLLClaimPolicySchema.getInsuredNo()+"')  " +
	      " and a.dutycalfactator= '"+tFeeItemCode+"'  ";
    	  tSSRS = tExeSQL.execSQL(tSq10);	
    	 if(tSSRS.getMaxRow()<1){
    		 logger.debug("理赔要素"+tFeeItemCode+"没有公共限额！");
    	 }else{
	        /**
	         * 更新公共限额的参数
	         * */
    		String  tSql11="update LCClaimCtrl a set a.DefaultValue=nvl((a.DefaultValue-"+tLLCaseReceiptSchema.getFee()+"),0)," +
    				"  set a.ModifyDate='"+CurrentDate+"' ,set a.ModifyTime='"+CurrentTime+"'  " +
 	 		        "  where a.grpcontno='"+tSSRS.GetText(1, 2)+"'  " +
 	 		        "  and a.claimctrlcode='"+tSSRS.GetText(1, 1)+"' ";
		 	 
    		logger.debug("tSql11====="+tSql11);
		 	map.put(tSql11, "UPDATE");   
    	 
    	 /**
    	  * 记录轨迹
    	  * */
    	 tLLClmLimitAmntTraceSchema.setSerNo(tExeSQL.getOneValue("select (max(SerNo)+1) from LLClmLimitAmntTrace where 1=1 "));
    	 tLLClmLimitAmntTraceSchema.setClaimCtrlCode(tSSRS.GetText(1, 1));
    	 tLLClmLimitAmntTraceSchema.setContNo(tLLClaimPolicySchema.getContNo());
    	 tLLClmLimitAmntTraceSchema.setClmNo(tLLClaimPolicySchema.getClmNo());
    	 tLLClmLimitAmntTraceSchema.setCaseNo(tLLClaimPolicySchema.getCaseNo());
    	 tLLClmLimitAmntTraceSchema.setPolNo(tLLClaimPolicySchema.getPolNo());
    	 tLLClmLimitAmntTraceSchema.setRgtNo(tLLClaimPolicySchema.getRgtNo());
    	 tLLClmLimitAmntTraceSchema.setContPlanCode(tExeSQL.getOneValue("select contplancode from lcinsured where insuredno='"+tLLClaimPolicySchema.getInsuredNo()+"'"));
    	 tLLClmLimitAmntTraceSchema.setTabFeeMoney(TotalAmnt);
    	 tLLClmLimitAmntTraceSchema.setMakeDate(CurrentDate);
    	 tLLClmLimitAmntTraceSchema.setMakeTime(CurrentTime);
    	 tLLClmLimitAmntTraceSchema.setModifyDate(CurrentDate);
    	 tLLClmLimitAmntTraceSchema.setModifyTime(CurrentTime);
    	 tLLClmLimitAmntTraceSet.add(tLLClmLimitAmntTraceSchema);
    	 
    	 }    	 
    	 
    	 
		}
		else if(tSSRS.GetText(1, 1).equals("B")){
		     /**
		      * TotalAmnt是最终的理算金额
		      * tSSRS.GetText(1, 1)是账单录入金额
		      * 当tSSRS.GetText(1, 1)<=TotalAmnt时说明最终的账单金额小于理算金额
		      * 那么这个时候就需要将存在的限额和公共的理算要素值更新并保持理算轨迹
		      * **/	
			/*下面是限额天数*/
	    	 String tSql2="update lccontplandutyparam a set a.calfactorvalue=nvl((a.calfactorvalue-"+tLLCaseReceiptSchema.getDayCount()+"),0) where " +
	    	 		"  a.grpcontno='"+tLLClaimPolicySchema.getGrpContNo()+"'  " +
	    	 		"  and a.riskcode='"+tLLClaimPolicySchema.getRiskCode()+"'  " +
	    	 		"  and a.calfactor=(select paramscode from lmriskparamsdef where riskcode='"+tLLClaimPolicySchema.getRiskCode()+"' and othercode='"+tFeeItemCode+"' and paramstype='Days') " +
	    	 		"  and a.contplancode=(select contplancode from lcinsured where insuredno='"+tLLClaimPolicySchema.getInsuredNo()+"') ";
	    	 logger.debug("tSql2====="+tSql2);

	    	 map.put(tSql2, "UPDATE");	

	    	 /**
	    	  * 判断是否存在公共限额的情况，如果有公共限额的情况，需要更新公共限额的值
	    	  * 同时保存公共限额的轨迹
	    	  * */ 	    	 
	    	  // B公共限额处理
	    	  String tSql20 = " select a.claimctrlcode,a.grpcontno,a.* from LCDutyClmCtrlRela a,LCClaimCtrl b where  " +
		      " a.claimctrlcode=b.claimctrlcode  and a.grpcontno=b.grpcontno  " +
		      " and a.grpcontno='"+tLLClaimPolicySchema.getGrpContNo()+"'  " +
		      " and a.riskcode='"+tLLClaimPolicySchema.getRiskCode()+"'  " +
		      " and a.contplancode=(select contplancode from lcinsured where insuredno='"+tLLClaimPolicySchema.getInsuredNo()+"')  " +
		      " and a.dutycalfactator= (select paramscode from lmriskparamsdef where riskcode='"+tLLClaimPolicySchema.getRiskCode()+"' and othercode='"+tFeeItemCode+"' and paramstype='Days') " ;
	    	  tSSRS = tExeSQL.execSQL(tSql20);	
	    	 if(tSSRS.getMaxRow()<1){
	    		 logger.debug("理赔要素"+tFeeItemCode+"没有公共限额！");
	    	 }else{
		        /**
		         * 更新公共限额的参数
		         * */
	    		String  tSql21="update LCClaimCtrl a set a.DefaultValue=nvl((a.DefaultValue-"+tLLCaseReceiptSchema.getFee()+"),0)," +
	    				"  set a.ModifyDate='"+CurrentDate+"' ,set a.ModifyTime='"+CurrentTime+"'  " +
	 	 		        "  where a.grpcontno='"+tSSRS.GetText(1, 2)+"'  " +
	 	 		        "  and a.claimctrlcode='"+tSSRS.GetText(1, 1)+"' ";
			 	 
	    		logger.debug("tSql11====="+tSql21);
			 	map.put(tSql21, "UPDATE");   
	    	 
	    	 /**
	    	  * 记录轨迹
	    	  * */
	    	 tLLClmLimitAmntTraceSchema.setSerNo(tExeSQL.getOneValue("select (max(SerNo)+1) from LLClmLimitAmntTrace where 1=1 "));
	    	 tLLClmLimitAmntTraceSchema.setClaimCtrlCode(tSSRS.GetText(1, 1));
	    	 tLLClmLimitAmntTraceSchema.setContNo(tLLClaimPolicySchema.getContNo());
	    	 tLLClmLimitAmntTraceSchema.setClmNo(tLLClaimPolicySchema.getClmNo());
	    	 tLLClmLimitAmntTraceSchema.setCaseNo(tLLClaimPolicySchema.getCaseNo());
	    	 tLLClmLimitAmntTraceSchema.setPolNo(tLLClaimPolicySchema.getPolNo());
	    	 tLLClmLimitAmntTraceSchema.setRgtNo(tLLClaimPolicySchema.getRgtNo());
	    	 tLLClmLimitAmntTraceSchema.setContPlanCode(tExeSQL.getOneValue("select contplancode from lcinsured where insuredno='"+tLLClaimPolicySchema.getInsuredNo()+"'"));
	    	 tLLClmLimitAmntTraceSchema.setTabFeeMoney(TotalAmnt);
	    	 tLLClmLimitAmntTraceSchema.setMakeDate(CurrentDate);
	    	 tLLClmLimitAmntTraceSchema.setMakeTime(CurrentTime);
	    	 tLLClmLimitAmntTraceSchema.setModifyDate(CurrentDate);
	    	 tLLClmLimitAmntTraceSchema.setModifyTime(CurrentTime);
	    	 tLLClmLimitAmntTraceSet.add(tLLClmLimitAmntTraceSchema);	    	 
			}
		}
		}
		map.put(tLLClmLimitAmntTraceSet, "INSERT");
		}
		return true;
    }    
    
//    /**
//     * 根据保项所在的合同号到LCContState表中找到该合同所有数据,
//     * 将StateType类型为Terminate的记录全部置为1终止，并填写终止时间
//     */
//    private boolean setContState(String tGetDutyCode, String tGetDutyKind)
//    {
//        String tUpdateSql = "";
//        tUpdateSql = "update LCContState a "
//                     + "set a.state = '1', a.enddate = to_date('"
//                     + CurrentDate + "','yyyy-mm-dd') "
//                     + "where a.statetype = 'Terminate' "
//                     + "and a.contno in ("
//                     + "select b.contno from LLClaimDetail b, llclaim c "
//                     + "where b.clmno = c.clmno "
//                     + "and c.givetype = '0' and b.givetype = '0' "
//                     + "and b.clmno = '" + mClmNo + "' "
//                     + "and b.GetDutyKind = '" + tGetDutyKind + "' "
//                     + "and b.getdutycode = '" + tGetDutyCode + "')";
//        logger.debug("更新合同状态:" + tUpdateSql);
//        map.put(tUpdateSql, "UPDATE");
//
//        return true;
//    }



    /**
     * 处理合同轨迹信息
     *
     * 如果LLContState表中有记录,并且ClmState的状态为0[合同处理涉及的保单],那么将该状态
     * 修改为2[合同处理,审批通过共同处理的保单.];如果LLContState表中无记录,那么在改表插入
     * 一条记录,并且ClmState的状态为1[赔案审批通过涉及处理的保单]
     *
     * 这样做的目的是在理赔系统中留一个轨迹信息.
     */
    private boolean updateContState(LLClaimDetailSchema tLLClaimDetail) {
        LLContStateDB ttLLContStateDB = new LLContStateDB();
        LLContStateSet ttLLContStateSet = new LLContStateSet();
        ttLLContStateDB.setClmNo(mClmNo);
        ttLLContStateDB.setContNo(tLLClaimDetail.getContNo());
        ttLLContStateDB.setPolNo(tLLClaimDetail.getPolNo());
        ttLLContStateSet = ttLLContStateDB.query();
        if (ttLLContStateSet == null && ttLLContStateSet.size() == 0) {
            //无记录
            LCContStateDB ttLCContStateDB = new LCContStateDB();
            LCContStateSet ttLCContStateSet = new LCContStateSet();
            ttLCContStateDB.setContNo(tLLClaimDetail.getContNo());
            ttLCContStateDB.setPolNo(tLLClaimDetail.getPolNo());
            ttLCContStateDB.setStateType("Terminate");
            ttLCContStateSet = ttLCContStateDB.query();
            if (ttLCContStateSet == null && ttLCContStateSet.size() == 0) {
                // @@错误处理
                CError tError = new CError();
                tError.moduleName = "LLClaimConfirmPassBL";
                tError.functionName = "dealData";
                tError.errorMessage = "查询保单状态信息时出错!";
                this.mErrors.addOneError(tError);
                return false;
            } else {
                LLContStateSchema tLLContStateSchema = new LLContStateSchema();
                ttLLContStateSet.add(tLLContStateSchema);
                ref.transFields(ttLLContStateSet, ttLCContStateSet);
                for (int j = 1; j <= ttLLContStateSet.size(); j++) {
                    ttLLContStateSet.get(j).setState("1");
                }
                map.put(ttLLContStateSet, "DELETE&INSERT");
            }
        } else {
            //有记录
            for (int j = 1; j <= ttLLContStateSet.size(); j++) {
                LLContStateSchema tLLContStateSchema = new LLContStateSchema();
                tLLContStateSchema.setSchema(ttLLContStateSet.get(j).getSchema());
                if (tLLContStateSchema.getClmState().equals("0")) {
                    tLLContStateSchema.setClmState("2");
                    map.put(tLLContStateSchema, "DELETE&INSERT");
                }
            }
        }
        return true;
    }

    /**
     * 添加打印数据
     * 2005-8-16 14:49
     * @return boolean
     */
    private boolean insertLOPRTManager(String tCode) {

        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();

        String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 10); //生成印刷流水号
        tLOPRTManagerSchema.setPrtSeq(tPrtSeq); //印刷流水号
        tLOPRTManagerSchema.setOtherNo(mClmNo); //对应其它号码
        tLOPRTManagerSchema.setOtherNoType("05"); //其它号码类型--05赔案号
        tLOPRTManagerSchema.setCode(tCode); //单据编码--单证通知书
        tLOPRTManagerSchema.setManageCom(mMngCom); //立案管理机构
        tLOPRTManagerSchema.setReqCom(mG.ComCode); //发起机构
        tLOPRTManagerSchema.setReqOperator(mG.Operator); //发起人
        tLOPRTManagerSchema.setPrtType("0"); //打印方式
        tLOPRTManagerSchema.setStateFlag("0"); //打印状态
        tLOPRTManagerSchema.setMakeDate(CurrentDate); //入机日期(报案日期)
        tLOPRTManagerSchema.setMakeTime(CurrentTime); //入机时间
        tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
        tLOPRTManagerSchema.setStandbyFlag1(CurrentDate); //批打检索日期
        tLOPRTManagerSchema.setStandbyFlag4(mCusNo); //客户号码
        tLOPRTManagerSchema.setStandbyFlag5(mRgtDate); //立案日期
        tLOPRTManagerSchema.setStandbyFlag6("50"); //赔案状态

        mLOPRTManagerSet.add(tLOPRTManagerSchema);
        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData() {
        try {
            mInputData.clear();
            mInputData.add(map);
            mResult.add(map);
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimConfirmPassBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
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
            tError.moduleName = "LLClaimCalCheckBL";
            tError.functionName = "PubSubmit.submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }


    public VData getResult() {
        return mResult;
    }


    public static void main(String[] args) {

        LLReportSchema tLLReportSchema = new LLReportSchema();

        String tClmNo = "86110020100610000033"; /*赔案号*/

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

        GlobalInput tG = new GlobalInput();
        tG.Operator = tLLRegisterDB.getOperator();
        tG.ManageCom = tLLRegisterDB.getMngCom();
        tG.ComCode = tLLRegisterDB.getMngCom();

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("RptNo", tClmNo);

        VData tVData = new VData();
        tVData.addElement(tG);
        tVData.addElement(tTransferData);

        LLClaimConfirmPassBL tLLClaimConfirmPassBL = new LLClaimConfirmPassBL();
        tLLClaimConfirmPassBL.submitData(tVData, "");
//        tLLClaimConfirmPassBL.pubSubmit();
        int n = tLLClaimConfirmPassBL.mErrors.getErrorCount();
        logger.debug(
                "-------------------------------------------------------");
        for (int i = 0; i < n; i++) {
            String Content = "";
            Content = Content + "原因是: " +
                      tLLClaimConfirmPassBL.mErrors.getError(i).errorMessage;
            logger.debug(Content);
        }
    }
}
