package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: 计算步骤三：对匹配后的保项和费用进行过滤，并提交到数据库</p>
 *
 * <p>Copyright: Copyright (c) 2002</p>
 *
 * <p>Company: SinoSoft Co. Ltd,</p>
 *
 * @author not attributable
 * @version 1.0
 */
public class LLClaimCalMatchFilterBL
{
private static Logger logger = Logger.getLogger(LLClaimCalMatchFilterBL.class);



    public  CErrors mErrors = new CErrors();        /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();

    private GlobalInput mGlobalInput = new GlobalInput();    /** 全局数据 */
    private TransferData mTransferData = new TransferData();
    private MMap mMMap = new MMap();                        /** 往后面传输的数据库操作 */


    private String mAccNo = "";     //事件号
    private String mAccDate = "";   //意外事故发生日期
    private String mClmNo = "";     //赔案号
    private String mContType = "";  //总单类型,1-个人总投保单,2-集体总单

    //匹配后返回的保项，费用信息
    LLToClaimDutySet mLLToClaimDutySet = new LLToClaimDutySet();
    LLToClaimDutyFeeSet mLLToClaimDutyFeeSet = new LLToClaimDutyFeeSet();

    //帐户临时表
    LLClaimAccountSet mLLClaimAccountSet= new LLClaimAccountSet();
    public LLClaimCalMatchFilterBL(){}



    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData,String cOperate)
    {

        logger.debug("----------理算步骤三-----过滤匹配保项并保存-----LLClaimCalMatchFilterBL测试-----开始----------");


        if (!getInputData(cInputData, cOperate))
        {
            return false;
        }


        if (!dealData())
        {
            return false;
        }


        if (!pubSubmit())
        {
            return false;
        }

        logger.debug("----------理算步骤三-----过滤匹配保项并保存-----LLClaimCalMatchFilterBL测试-----结束----------");
        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData,String cOperate)
    {


        mGlobalInput.setSchema((GlobalInput)cInputData.getObjectByObjectName("GlobalInput", 0));
        this.mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData", 0);
        mLLToClaimDutySet = (LLToClaimDutySet)cInputData.getObjectByObjectName("LLToClaimDutySet", 0);
        mLLToClaimDutyFeeSet = (LLToClaimDutyFeeSet)cInputData.getObjectByObjectName("LLToClaimDutyFeeSet", 0);
        mLLClaimAccountSet = (LLClaimAccountSet)cInputData.getObjectByObjectName("LLClaimAccountSet", 0);
        this.mAccNo   = (String) mTransferData.getValueByName("AccNo");     //事件号
        this.mAccDate = (String) mTransferData.getValueByName("AccDate");   //意外事故发生日期
        this.mClmNo   = (String) mTransferData.getValueByName("ClmNo");     //赔案号
        this.mContType= (String) mTransferData.getValueByName("ContType");  //总单类型,1-个人投保单,2-集体总投保单

        return true;
    }




    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData()
    {
        if (!getMatchFilterResult())
        {
            return false;
        }
        return true;
    }

    /**
     * 得到匹配后的结果集
     * @param tResult
     * @return
     */
    private boolean getMatchFilterResult()
    {

        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 1.0 用于保存的LLToClaimDutyFee集合
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */

        LLToClaimDutySet tLLToClaimDutySaveSet = new LLToClaimDutySet();
        LLToClaimDutyFeeSet tLLToClaimDutyFeeSaveSet = new LLToClaimDutyFeeSet();

		/*
		 * 2008-10-10 zhangzheng 
		 * 在准备参与计算的责任和费用时，如果数据库中已经存在计算过的记录，(llclaimdetail和llclaimdutyfee)，就会取数据库中已经存在的记录,但是这样有一个问题，就是当计算过一次后这两个表就会有记录
						
		       但是此时再修改医疗账单的金额，但修改账单金额则不会设计这两个表，但是参与计算的责任金额以及责任都只能是第一次计算成功时保存到数据库中的数据，则导致后面的修改无效
						
                            修改为应该每一次参与计算的责任和费用都应该是匹配出来的结果，而不应该用这种从数据库取值的方法！               
		 */
		
		tLLToClaimDutySaveSet.set(mLLToClaimDutySet);
		tLLToClaimDutyFeeSaveSet.set(mLLToClaimDutyFeeSet);
		
//        for(int m=1;m <= mLLToClaimDutySet.size(); m++)
//        {
//            LLToClaimDutySchema tLLToClaimDutySchema = mLLToClaimDutySet.get(m);
//            LLToClaimDutySchema tLLToClaimDutySaveSchema = new LLToClaimDutySchema();//用于保存
//
//            //查询赔付明细表LLClaimDetail是否有数据
////            LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
////            tLLClaimDetailDB.setClmNo(tLLToClaimDutySchema.getCaseNo());
////            tLLClaimDetailDB.setPolNo(tLLToClaimDutySchema.getPolNo());
////            tLLClaimDetailDB.setGetDutyKind(tLLToClaimDutySchema.getGetDutyKind());
////            tLLClaimDetailDB.setGetDutyCode(tLLToClaimDutySchema.getGetDutyCode());
////            LLClaimDetailSet tLLClaimDetailSet = tLLClaimDetailDB.query();
//            LLClaimDetailSet tLLClaimDetailSet = new LLClaimDetailSet();
//            LLClaimDetailSchema tLLClaimDetailSchema = null;
//
//            if(tLLClaimDetailSet != null && tLLClaimDetailSet.size() == 1 )
//            {
//
//                tLLClaimDetailSchema  = tLLClaimDetailSet.get(1);
//
//                tLLToClaimDutySaveSchema = new LLToClaimDutySchema();
//
//                tLLToClaimDutySaveSchema.setCaseNo(tLLClaimDetailSchema.getClmNo());          //赔案号
//                tLLToClaimDutySaveSchema.setCaseRelaNo(tLLClaimDetailSchema.getClmNo());      /* 受理事故号 */
//                tLLToClaimDutySaveSchema.setSubRptNo(tLLClaimDetailSchema.getClmNo());        /* 事件号 */
//
//                tLLToClaimDutySaveSchema.setPolNo(tLLClaimDetailSchema.getPolNo());
//                /*保单性质0 --个人单,1 --无名单,2 --（团单）公共帐户*/
//                tLLToClaimDutySaveSchema.setPolType(tLLToClaimDutySchema.getPolType());
//                /* 保单类型,C表保单，B表保单,A承保出单前出险*/
//                tLLToClaimDutySaveSchema.setPolSort(tLLToClaimDutySchema.getPolSort());
//
//                tLLToClaimDutySaveSchema.setDutyCode(tLLClaimDetailSchema.getDutyCode());       /* 责任编码 */
//                tLLToClaimDutySaveSchema.setGetDutyCode(tLLClaimDetailSchema.getGetDutyCode()); /* 给付责任编码 */
//                tLLToClaimDutySaveSchema.setGetDutyKind(tLLClaimDetailSchema.getGetDutyKind()); /* 给付责任类型 */
//                tLLToClaimDutySaveSchema.setContNo(tLLClaimDetailSchema.getContNo());           /* 个单合同号 */
//
//                tLLToClaimDutySaveSchema.setGrpContNo(tLLClaimDetailSchema.getGrpContNo());     /* 集体合同号 */
//                tLLToClaimDutySaveSchema.setGrpPolNo(tLLClaimDetailSchema.getGrpContNo());      /* 集体保单号 */
//                tLLToClaimDutySaveSchema.setKindCode(tLLClaimDetailSchema.getKindCode());       /* 险类代码 */
//                tLLToClaimDutySaveSchema.setRiskCode(tLLClaimDetailSchema.getRiskCode());       /* 险种代码 */
//                tLLToClaimDutySaveSchema.setRiskVer(tLLClaimDetailSchema.getRiskVer());         /* 险种版本号 */
//                tLLToClaimDutySaveSchema.setPolMngCom(tLLClaimDetailSchema.getPolMngCom());     /* 保单管理机构 */
//
//
//                /* 赔付结论 */
//                if (tLLClaimDetailSchema.getGiveType().equals("2"))
//                {
//                    tLLToClaimDutySaveSchema.setGiveType("2");   //给付标志
//                }
//                else if (tLLClaimDetailSchema.getGiveType().equals("1"))
//                {
//                    tLLToClaimDutySaveSchema.setGiveType("1");   //给付标志
//                }
//                else
//                {
//                    tLLToClaimDutySaveSchema.setGiveType(tLLToClaimDutySchema.getGiveType());       //给付标志
//                }
//
//
//                tLLToClaimDutySaveSchema.setGiveTypeDesc(tLLClaimDetailSchema.getGiveTypeDesc());
//                tLLToClaimDutySaveSchema.setGiveReason(tLLClaimDetailSchema.getGiveReason());
//                tLLToClaimDutySaveSchema.setGiveReasonDesc(tLLClaimDetailSchema.getGiveReasonDesc());
//
//
//                /* 有效保额 = 标准给付金额 - 已领金额 */
//                tLLToClaimDutySaveSchema.setAmnt(tLLToClaimDutySchema.getAmnt());
//                tLLToClaimDutySaveSchema.setPosFlag(tLLToClaimDutySchema.getPosFlag());     /*0未做保全,1已做保全*/
//                tLLToClaimDutySaveSchema.setPosEdorNo(tLLToClaimDutySchema.getPosEdorNo()); /*保全批单号*/
//
//
//                /* 缴费宽限期 */
//                tLLToClaimDutySaveSchema.setGracePeriod(tLLToClaimDutySchema.getGracePeriod());
//
//                /*给付类型,0-被保人,1- 投保人,2-连带被保人*/
//                tLLToClaimDutySaveSchema.setCasePolType(tLLToClaimDutySchema.getCasePolType());
//
//                /* LCGet表的该字段属于借用，用于存放出险人编号 */
//                tLLToClaimDutySaveSchema.setCustomerNo(tLLToClaimDutySchema.getCustomerNo());
//
//                tLLToClaimDutySaveSchema.setYearBonus(tLLToClaimDutySchema.getYearBonus());//年度红利
//                tLLToClaimDutySaveSchema.setEndBonus(tLLToClaimDutySchema.getEndBonus());  //终了红利
//
//                /* 预付标志,0没有预付,1已经预付 */
//                tLLToClaimDutySaveSchema.setPrepayFlag(tLLToClaimDutySchema.getPrepayFlag());
//
//                /* 预付金额 */
//                tLLToClaimDutySaveSchema.setPrepaySum(tLLToClaimDutySchema.getPrepaySum());
//
//                /*附加险影响主险标志*/
//                tLLToClaimDutySaveSchema.setEffectOnMainRisk(tLLToClaimDutySchema.getEffectOnMainRisk());
//
//                /*用于保存主险的公式*/
//                tLLToClaimDutySaveSchema.setRiskCalCode(tLLToClaimDutySchema.getRiskCalCode());
//
//                /* 加保后的保额 */
//                tLLToClaimDutySaveSchema.setAddAmnt("0");
//
//                /* 做过续期抽档之后,把原号保存起来,用于后续业务 */
//                tLLToClaimDutySaveSchema.setNBPolNo(tLLToClaimDutySchema.getNBPolNo());
//
//                /*保全业务类型*/
//                tLLToClaimDutySaveSchema.setPosEdorType(tLLToClaimDutySchema.getPosEdorType());
//
//                /* 主附险标志 M主险 S附险 */
//                tLLToClaimDutySaveSchema.setRiskType(tLLToClaimDutySchema.getRiskType());
//
//            }
//            else
//            {
//                tLLToClaimDutySaveSchema.setSchema(tLLToClaimDutySchema);
//            }
//
//            //logger.debug("-----==========-------1==========="+tLLToClaimDutySaveSchema.getGiveType());
//            tLLToClaimDutySaveSet.add(tLLToClaimDutySaveSchema);
//
//
//        }//for循环结束
//
//
//        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         * 3.0 将匹配返回的的Set集合添加到LLToClaimDuty表中
//         *  如果有将原数据取出放入临时表
//         *  如果没有则直接放入
//         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
//         */
//        for(int i=1;i<=mLLToClaimDutyFeeSet.size();i++)
//        {
//            LLToClaimDutyFeeSchema tLLToClaimDutyFeeSchema = mLLToClaimDutyFeeSet.get(i);
//            LLToClaimDutyFeeSchema tLLToClaimDutyFeeSaveSchema = new LLToClaimDutyFeeSchema();//用于保存
//
//
//            //查询责任费用LLClaimDutyFee是否有数据
////            LLClaimDutyFeeDB tLLClaimDutyFeeDB = new LLClaimDutyFeeDB();
////            tLLClaimDutyFeeDB.setClmNo(tLLToClaimDutyFeeSchema.getClmNo());
////            tLLClaimDutyFeeDB.setCaseNo(tLLToClaimDutyFeeSchema.getCaseNo());
////            tLLClaimDutyFeeDB.setPolNo(tLLToClaimDutyFeeSchema.getPolNo());
////            tLLClaimDutyFeeDB.setDutyCode(tLLToClaimDutyFeeSchema.getDutyCode());
////            tLLClaimDutyFeeDB.setGetDutyType(tLLToClaimDutyFeeSchema.getGetDutyType());
////            tLLClaimDutyFeeDB.setGetDutyCode(tLLToClaimDutyFeeSchema.getGetDutyCode());
////            tLLClaimDutyFeeDB.setDutyFeeType(tLLToClaimDutyFeeSchema.getDutyFeeType());
////            tLLClaimDutyFeeDB.setDutyFeeCode(tLLToClaimDutyFeeSchema.getDutyFeeCode());
////            tLLClaimDutyFeeDB.setDutyFeeStaNo(tLLToClaimDutyFeeSchema.getDutyFeeStaNo());
////            LLClaimDutyFeeSet tLLClaimDutyFeeSet = tLLClaimDutyFeeDB.query();
//            LLClaimDutyFeeSet tLLClaimDutyFeeSet = new LLClaimDutyFeeSet();
//            LLClaimDutyFeeSchema tLLClaimDutyFeeSchema  = null;
//            if(tLLClaimDutyFeeSet != null && tLLClaimDutyFeeSet.size() == 1 )
//            {
//
//                tLLClaimDutyFeeSchema  = tLLClaimDutyFeeSet.get(1);
//
//                tLLToClaimDutyFeeSaveSchema = new LLToClaimDutyFeeSchema();
//
//                tLLToClaimDutyFeeSaveSchema.setClmNo(tLLToClaimDutyFeeSchema.getClmNo());          //赔案号
//                tLLToClaimDutyFeeSaveSchema.setCaseNo(tLLToClaimDutyFeeSchema.getCaseNo());        //分案号
//                tLLToClaimDutyFeeSaveSchema.setPolNo(tLLToClaimDutyFeeSchema.getPolNo());          //保单号
//
//                tLLToClaimDutyFeeSaveSchema.setGetDutyType(tLLToClaimDutyFeeSchema.getGetDutyType());     //给付责任类型
//                tLLToClaimDutyFeeSaveSchema.setGetDutyCode(tLLToClaimDutyFeeSchema.getGetDutyCode());     //给付责任编码
//
//                tLLToClaimDutyFeeSaveSchema.setDutyFeeType(tLLToClaimDutyFeeSchema.getDutyFeeType());     //费用类型
//                tLLToClaimDutyFeeSaveSchema.setDutyFeeCode(tLLToClaimDutyFeeSchema.getDutyFeeCode());     //费用代码
//                tLLToClaimDutyFeeSaveSchema.setDutyFeeName(tLLToClaimDutyFeeSchema.getDutyFeeName());     //费用名称
//
//                tLLToClaimDutyFeeSaveSchema.setDutyFeeStaNo(tLLToClaimDutyFeeSchema.getDutyFeeStaNo());   //账单费用明细序号
//
//                tLLToClaimDutyFeeSaveSchema.setKindCode(tLLToClaimDutyFeeSchema.getKindCode());   //险类代码
//                tLLToClaimDutyFeeSaveSchema.setRiskCode(tLLToClaimDutyFeeSchema.getRiskCode());   //险种代码
//                tLLToClaimDutyFeeSaveSchema.setRiskVer(tLLToClaimDutyFeeSchema.getRiskVer());     //险种版本号
//                tLLToClaimDutyFeeSaveSchema.setPolMngCom(tLLToClaimDutyFeeSchema.getPolMngCom()); //保单管理机构
//
//
//                tLLToClaimDutyFeeSaveSchema.setHosID(tLLToClaimDutyFeeSchema.getHosID());         //医院编号
//                tLLToClaimDutyFeeSaveSchema.setHosName(tLLToClaimDutyFeeSchema.getHosName());     //医院名称
//                tLLToClaimDutyFeeSaveSchema.setHosGrade(tLLToClaimDutyFeeSchema.getHosGrade());   //医院等级
//
//                tLLToClaimDutyFeeSaveSchema.setStartDate(tLLToClaimDutyFeeSchema.getStartDate());  //开始时间
//                tLLToClaimDutyFeeSaveSchema.setEndDate(tLLToClaimDutyFeeSchema.getEndDate());      //结束时间
//                tLLToClaimDutyFeeSaveSchema.setDayCount(tLLToClaimDutyFeeSchema.getDayCount());    //天数
//
//                tLLToClaimDutyFeeSaveSchema.setDefoType(tLLClaimDutyFeeSchema.getDefoType());     //伤残类型
//                tLLToClaimDutyFeeSaveSchema.setDefoCode(tLLClaimDutyFeeSchema.getDefoCode());     //伤残代码
//                tLLToClaimDutyFeeSaveSchema.setDefoeName(tLLClaimDutyFeeSchema.getDefoeName());   //伤残级别名称
//                tLLToClaimDutyFeeSaveSchema.setDefoRate(tLLClaimDutyFeeSchema.getDefoRate());     //残疾给付比例
//                tLLToClaimDutyFeeSaveSchema.setRealRate(tLLClaimDutyFeeSchema.getRealRate());     //实际给付比例
//
//                tLLToClaimDutyFeeSaveSchema.setOriSum(tLLClaimDutyFeeSchema.getOriSum());   //原始金额
//                tLLToClaimDutyFeeSaveSchema.setAdjSum(tLLClaimDutyFeeSchema.getAdjSum());   //调整金额
//                tLLToClaimDutyFeeSaveSchema.setCalSum(tLLClaimDutyFeeSchema.getCalSum());   //理算金额
//
//                tLLToClaimDutyFeeSaveSchema.setAdjReason(tLLToClaimDutyFeeSchema.getAdjReason());    //调整原因
//                tLLToClaimDutyFeeSaveSchema.setAdjRemark(tLLToClaimDutyFeeSchema.getAdjRemark());    //调整备注
//
//                tLLToClaimDutyFeeSaveSchema.setOutDutyAmnt(0);                                         //免赔额
//                tLLToClaimDutyFeeSaveSchema.setOutDutyRate(tLLClaimDutyFeeSchema.getOutDutyRate());    //免赔比例
//                tLLToClaimDutyFeeSaveSchema.setOutDutyType(tLLClaimDutyFeeSchema.getOutDutyType());    //免赔类型
//                tLLToClaimDutyFeeSaveSchema.setDutyCode(tLLToClaimDutyFeeSchema.getDutyCode());
//                /* 做过续期抽档之后,把原号保存起来,用于后续业务 */
//                tLLToClaimDutyFeeSaveSchema.setNBPolNo(tLLToClaimDutyFeeSaveSchema.getNBPolNo());
//                tLLToClaimDutyFeeSaveSchema.setCutApartAmnt(tLLToClaimDutyFeeSchema.getCutApartAmnt());//分割单受益金额
//                tLLToClaimDutyFeeSaveSchema.setCustomerNo(tLLToClaimDutyFeeSchema.getCustomerNo()); //出险人编码
//                tLLToClaimDutyFeeSaveSchema.setFeeItemType(tLLToClaimDutyFeeSchema.getFeeItemType()); //帐单类型 A门诊 B住院
//                logger.debug("出险人编码 =========== " +tLLToClaimDutyFeeSchema.getCustomerNo());
//                logger.debug("帐单类型   =========== " +tLLToClaimDutyFeeSchema.getFeeItemType());
//            }
//            else
//            {
//                tLLToClaimDutyFeeSchema.setOutDutyAmnt(0);
//                tLLToClaimDutyFeeSaveSchema.setSchema(tLLToClaimDutyFeeSchema);
//            }
//
//            tLLToClaimDutyFeeSaveSet.add(tLLToClaimDutyFeeSaveSchema);
//
//        }//for循环结束

        LLClaimAccountSet tLLClaimAccountSet = new LLClaimAccountSet();
        String strSQL3="";
        if(mLLClaimAccountSet!=null&&mLLClaimAccountSet.size()>0)
        {
          tLLClaimAccountSet.add(mLLClaimAccountSet);
          strSQL3 = "delete from LLClaimAccount where ClmNo='" + this.mClmNo + "'";
        }
        /**－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         * 4.0 准备提交的数据并提交到后台进行操作
         * －－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－
         */
        String strSQL1 = "delete from LLToClaimDuty where caseno='" + this.mClmNo + "'";
        String strSQL2 = "delete from LLToClaimDutyFee where ClmNo='" + this.mClmNo + "'";

        MMap tMMap = new MMap();
        tMMap.put(strSQL1, "DELETE");
        tMMap.put(strSQL2, "DELETE");
        if(strSQL3!="")
        {
            logger.debug("有LLClaimAccount数据");
            tMMap.put(strSQL3, "DELETE");
            tMMap.put(tLLClaimAccountSet, "INSERT");
        }
        tMMap.put(tLLToClaimDutySaveSet, "INSERT");
        tMMap.put(tLLToClaimDutyFeeSaveSet, "INSERT");


        mInputData.clear();
        mInputData.add(tMMap);

        return true;

    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {

        mInputData.clear();
        mInputData.add(mMMap);
        return true;
    }


    /**
     * 提交数据
     * @return
     */
    private boolean pubSubmit()
    {
        //  进行数据提交
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, ""))
        {
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
    public VData getResult()
    {
        return mResult;
    }

    /**
     * 测试主方法
     * @param args
     */
    public static void main(String[] args)
    {

        GlobalInput tG = new GlobalInput();

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("AccNo","80000000630");
        tTransferData.setNameAndValue("AccDate","2005-05-30");
        tTransferData.setNameAndValue("ClmNo","90000000690");
        tTransferData.setNameAndValue("ContType","1");


        VData tVData = new VData();

        tVData.add( tG );
        tVData.add( tTransferData );

        LLClaimCalMatchBL tLLClaimCalMatchBL = new LLClaimCalMatchBL();

        String tContent = "";
        VData tResult = new VData();

        if (!tLLClaimCalMatchBL.submitData(tVData,""))
        {
            tResult = tLLClaimCalMatchBL.getResult();
            LLClaimCalMatchFilterBL tLLClaimCalMatchFilterBL = new LLClaimCalMatchFilterBL();


            if (tLLClaimCalMatchFilterBL.submitData(tResult,"") == false)
            {
                int n = tLLClaimCalMatchFilterBL.mErrors.getErrorCount();

                for (int i = 0; i < n; i++)
                {
                    tContent = tContent + tLLClaimCalMatchFilterBL.mErrors.getError(i).errorMessage;

                }
            }

        }





    }

}
