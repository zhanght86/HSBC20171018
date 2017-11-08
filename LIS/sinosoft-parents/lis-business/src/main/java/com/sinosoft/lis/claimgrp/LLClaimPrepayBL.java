/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.LLPrepayDetailSchema;
import com.sinosoft.lis.schema.LLPrepayClaimSchema;
import com.sinosoft.lis.schema.LLClaimDetailSchema;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.LLClaimSchema;
import com.sinosoft.lis.schema.LLBalanceSchema;
import com.sinosoft.lis.vschema.LDCodeSet;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 预付管理提交逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author: zl
 * @version 1.0
 */
public class LLClaimPrepayBL
{
private static Logger logger = Logger.getLogger(LLClaimPrepayBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData ;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private GlobalInput mG = new GlobalInput();
    private MMap map = new MMap();
    private LLClaimDetailSchema mLLClaimDetailSchema=new LLClaimDetailSchema();
    private LLPrepayDetailSchema mLLPrepayDetailSchema=new LLPrepayDetailSchema();
    private LLPrepayClaimSchema mLLPrepayClaimSchema=new LLPrepayClaimSchema();
    private LLClaimSchema mLLClaimSchema=new LLClaimSchema();
    private LLBalanceSchema mLLBalanceSchema=new LLBalanceSchema();

    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    //double tPrepaySum=0;//预付金额


    public LLClaimPrepayBL()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
    * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
    * @param cInputData 传入的数据,VData对象
    * @param cOperate 数据操作字符串
    * @return 布尔值（true--提交成功, false--提交失败）
    */
    public boolean submitData(VData cInputData,String cOperate)
    {
        logger.debug("----------LLClaimPrepayBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData)cInputData.clone() ;
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
            return false;
        logger.debug("----------after getInputData----------");

        //检查数据合法性
        if (!checkInputData())
        {
            return false;
        }
        logger.debug("----------after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate))
        {
            return false;
        }
        logger.debug("----------after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }
        logger.debug("----------after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        mInputData = null;
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData()
    {
        logger.debug("--start getInputData()");
        //获取页面信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);//按类取值
        mLLClaimDetailSchema = (LLClaimDetailSchema) mInputData.getObjectByObjectName("LLClaimDetailSchema", 0);//按类取值
        mLLPrepayClaimSchema = (LLPrepayClaimSchema) mInputData.getObjectByObjectName("LLPrepayClaimSchema", 0);//按类取值
        mLLPrepayDetailSchema = (LLPrepayDetailSchema) mInputData.getObjectByObjectName("LLPrepayDetailSchema", 0);//按类取值
        mLLClaimSchema = (LLClaimSchema) mInputData.getObjectByObjectName("LLClaimSchema", 0);//按类取值
        mLLBalanceSchema = (LLBalanceSchema) mInputData.getObjectByObjectName("LLBalanceSchema", 0);//按类取值


        if (mLLClaimDetailSchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mLLPrepayClaimSchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mLLPrepayDetailSchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mLLClaimSchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (mLLBalanceSchema == null)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接受的信息全部为空!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * 输出：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData()
    {
        logger.debug("----------begin checkInputData----------");
        try
        {
            //非空字段检验
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "checkInputData";
            tError.errorMessage = "在校验输入的数据时出错!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 数据操作类业务处理
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        logger.debug("------start dealData-----");
        boolean tReturn = false;
        //生成预付明细记录
        if (cOperate.equals("Prepay||Insert"))
        {
            logger.debug("----- dealData---transact= "+cOperate);
            //查询  赔付明细----LLClaimDetail，用于准备数据
            LLClaimDetailDB tLLClaimDetailDB = new LLClaimDetailDB();
            tLLClaimDetailDB.setClmNo(mLLClaimDetailSchema.getClmNo());
            tLLClaimDetailDB.setCaseNo(mLLClaimDetailSchema.getCaseNo());
            tLLClaimDetailDB.setPolNo(mLLClaimDetailSchema.getPolNo());
            tLLClaimDetailDB.setDutyCode(mLLClaimDetailSchema.getDutyCode());
            tLLClaimDetailDB.setGetDutyCode(mLLClaimDetailSchema.getGetDutyCode());
            tLLClaimDetailDB.setGetDutyKind(mLLClaimDetailSchema.getGetDutyKind());
            tLLClaimDetailDB.setCaseRelaNo(mLLClaimDetailSchema.getCaseRelaNo());
            tLLClaimDetailDB.getInfo();


            //生成预付明细记录
            //赔案号、分案号、保单号、批次号、给付责任类型、给付责任编码<主键>、预付金额由前台传入
            mLLPrepayDetailSchema.setSerialNo(PubFun1.CreateMaxNo("SerialNo",10));//序号(生成)<主键>
            mLLPrepayDetailSchema.setRgtNo(tLLClaimDetailDB.getRgtNo());
            mLLPrepayDetailSchema.setDeclineNo(tLLClaimDetailDB.getDeclineNo());
            mLLPrepayDetailSchema.setGrpContNo(tLLClaimDetailDB.getGrpContNo());
            mLLPrepayDetailSchema.setGrpPolNo(tLLClaimDetailDB.getGrpPolNo());
            mLLPrepayDetailSchema.setContNo(tLLClaimDetailDB.getContNo());
            mLLPrepayDetailSchema.setKindCode(tLLClaimDetailDB.getKindCode());
            mLLPrepayDetailSchema.setRiskCode(tLLClaimDetailDB.getRiskCode());
            mLLPrepayDetailSchema.setRiskVer(tLLClaimDetailDB.getRiskVer());
            mLLPrepayDetailSchema.setPolMngCom(tLLClaimDetailDB.getPolMngCom());
            mLLPrepayDetailSchema.setSaleChnl(tLLClaimDetailDB.getSaleChnl());
            mLLPrepayDetailSchema.setDutyCode(tLLClaimDetailDB.getDutyCode());
            mLLPrepayDetailSchema.setMngCom(mG.ManageCom);
            mLLPrepayDetailSchema.setOperator(mG.Operator);
            mLLPrepayDetailSchema.setMakeDate(CurrentDate);
            mLLPrepayDetailSchema.setMakeTime(CurrentTime);
            mLLPrepayDetailSchema.setModifyDate(CurrentDate);
            mLLPrepayDetailSchema.setModifyTime(CurrentTime);
            map.put(mLLPrepayDetailSchema, "INSERT");

          //生成或修改汇总记录，不存在则生成，存在则修改
           //判断汇总记录是否存在,条件(赔案号)
            LLPrepayClaimDB ttLLPrepayClaimDB = new LLPrepayClaimDB();
            ttLLPrepayClaimDB.setClmNo(mLLPrepayClaimSchema.getClmNo());
            ttLLPrepayClaimDB.getInfo();
            logger.debug("-----查询预付赔案记录表所得记录= " + ttLLPrepayClaimDB.getCount());
            if (ttLLPrepayClaimDB.getCount() == 0)//创建预付赔案汇总记录
            {
                logger.debug("-----创建预付赔案汇总记录------");
                //赔案号、立案号、分案号、预付赔付金额由前台传入
                mLLPrepayClaimSchema.setMngCom(mG.ManageCom);
                mLLPrepayClaimSchema.setOperator(mG.Operator);
                mLLPrepayClaimSchema.setMakeDate(CurrentDate);
                mLLPrepayClaimSchema.setMakeTime(CurrentTime);
                mLLPrepayClaimSchema.setModifyDate(CurrentDate);
                mLLPrepayClaimSchema.setModifyTime(CurrentTime);
                map.put(mLLPrepayClaimSchema, "INSERT");
            }
            else                   //修改预付赔案记录
            {
                logger.debug("-----修改预付赔案记录，对预付总金额进行累加------");
                double tPrepaySum=mLLPrepayClaimSchema.getPrepaySum();
                LLPrepayClaimDB tLLPrepayClaimDB = new LLPrepayClaimDB();
                tLLPrepayClaimDB.setClmNo(mLLPrepayClaimSchema.getClmNo());
                tLLPrepayClaimDB.getInfo();
                mLLPrepayClaimSchema.setSchema(tLLPrepayClaimDB.getSchema());;
                mLLPrepayClaimSchema.setPrepaySum(tLLPrepayClaimDB.getPrepaySum()+tPrepaySum);
                mLLPrepayClaimSchema.setModifyDate(CurrentDate);
                mLLPrepayClaimSchema.setModifyTime(CurrentTime);
                map.put(mLLPrepayClaimSchema, "UPDATE");

            }

            //修改赔付明细表（LLClaimDetail）预付标志（PrepayFlag置为“1”）

            //检测是否预付《每个保单只能进行一次预付》

            //检查“预付金额”必须不超过“核赔赔付金额 X 预付比例”
            //查询预付比例
            LDCodeDB tLDCodeDB = new LDCodeDB();
            LDCodeSet tLDCodeSet=new LDCodeSet();
            double tllprepayscale=0;
            tLDCodeDB.setCodeType("llprepayscale");
            tLDCodeSet.set(tLDCodeDB.query());
            if(tLDCodeSet.size()==1)
            {
              String llprepayscale=tLDCodeSet.get(1).getCode();
              tllprepayscale= Double.parseDouble(llprepayscale);
              logger.debug("-----预付比例------"+tllprepayscale);
            }
            else
            {
                CError tError = new CError();
                tError.moduleName = "LLClaimPrepayBL";
                tError.functionName = "checkInputData";
                tError.errorMessage = "查询预付比例出错!";
                this.mErrors.addOneError(tError);
                return false;
            }
            //计算可以预付的金额

            double tRealPay= tllprepayscale*tLLClaimDetailDB.getRealPay()-tLLClaimDetailDB.getPrepaySum();
            logger.debug("------------可以预付的金额(总)===="+tllprepayscale*tLLClaimDetailDB.getRealPay());
            logger.debug("-------------已经预付金额()====="+tLLClaimDetailDB.getPrepaySum());
            double ttPrepaySum=mLLClaimDetailSchema.getPrepaySum();   //预付金额
            logger.debug("------------还可以预付的金额===="+tRealPay);
            logger.debug("-------------准备预付金额====="+ttPrepaySum);
            if(tRealPay<(ttPrepaySum))
            {
                CError tError = new CError();
                tError.moduleName = "LLClaimPrepayBL";
                tError.functionName = "checkInputData";
                tError.errorMessage = "预付金额已经超过核赔赔付金额 X "+tllprepayscale+",还可预付金额为"+tRealPay;
                this.mErrors.addOneError(tError);
                return false;
            }
            mLLClaimDetailSchema.setSchema(tLLClaimDetailDB.getSchema());
            mLLClaimDetailSchema.setPrepaySum(tLLClaimDetailDB.getPrepaySum()+ttPrepaySum);
            mLLClaimDetailSchema.setPrepayFlag("1");//预付标志置为“1--已经预付”
            mLLClaimDetailSchema.setModifyDate(CurrentDate);
            mLLClaimDetailSchema.setModifyTime(CurrentTime);
            map.put(mLLClaimDetailSchema, "DELETE&INSERT");

            //在理赔结算表（LLBalance）中生成理赔结算记录，如果存在则更新累加 “赔付金额”（pay---------存负数值）
            //判断汇总记录是否存在,条件(赔案号、业务类型、子业务类型、财务类型、批次号、保单号码)
              LLBalanceDB ttLLBalanceDB=new LLBalanceDB();
              ttLLBalanceDB.setClmNo(mLLBalanceSchema.getClmNo());
              ttLLBalanceDB.setFeeOperationType( mLLBalanceSchema.getFeeOperationType());
              ttLLBalanceDB.setSubFeeOperationType(mLLBalanceSchema.getSubFeeOperationType());
              ttLLBalanceDB.setFeeFinaType(mLLBalanceSchema.getFeeFinaType());
              ttLLBalanceDB.setBatNo(mLLBalanceSchema.getBatNo());
              ttLLBalanceDB.setPolNo(mLLBalanceSchema.getPolNo());
              ttLLBalanceDB.getInfo();
             logger.debug("-----查询理赔结算记录表所得记录= " + ttLLBalanceDB.getCount());
             if (ttLLBalanceDB.getCount() == 0)//创建理赔结算记录
             {
                 logger.debug("-----创建理赔结算记录------");
                 //赔案号、业务类型、子业务类型、财务类型、批次号、保单号码、赔付金额由前台传入
                 //“赔付金额”存储负值
                 mLLBalanceSchema.setOtherNo("0");
                 mLLBalanceSchema.setOtherNoType("0");
                 mLLBalanceSchema.setGetDutyCode(tLLClaimDetailDB.getDutyCode());
                 mLLBalanceSchema.setGetDutyKind(tLLClaimDetailDB.getGetDutyKind());
                 mLLBalanceSchema.setDutyCode(tLLClaimDetailDB.getDutyCode());
                 mLLBalanceSchema.setGrpContNo(tLLClaimDetailDB.getGrpContNo());
                 mLLBalanceSchema.setGrpPolNo(tLLClaimDetailDB.getGrpPolNo());
                 mLLBalanceSchema.setContNo(tLLClaimDetailDB.getContNo());
                 mLLBalanceSchema.setKindCode(tLLClaimDetailDB.getKindCode());
                 mLLBalanceSchema.setRiskCode(tLLClaimDetailDB.getRiskCode());
                 mLLBalanceSchema.setRiskVersion(tLLClaimDetailDB.getRiskVer());
                 mLLBalanceSchema.setState("0");
                 mLLBalanceSchema.setDealFlag("0");
                 double tPay=-1*mLLBalanceSchema.getPay();
                 mLLBalanceSchema.setPay(tPay);
                 mLLBalanceSchema.setOperator(mG.Operator);
                 mLLBalanceSchema.setMakeDate(CurrentDate);
                 mLLBalanceSchema.setMakeTime(CurrentTime);
                 mLLBalanceSchema.setModifyDate(CurrentDate);
                 mLLBalanceSchema.setModifyTime(CurrentTime);
                 map.put(mLLBalanceSchema, "INSERT");
             }
             else  //修改记录
             {
                 logger.debug("-----修改理赔结算记录，赔付金额进行累加------");
                 double tPay=-1*mLLBalanceSchema.getPay();
                 LLBalanceDB tLLBalanceDB = new LLBalanceDB();
                 tLLBalanceDB.setClmNo(mLLBalanceSchema.getClmNo());
                 tLLBalanceDB.setFeeOperationType(mLLBalanceSchema.getFeeOperationType());
                 tLLBalanceDB.setSubFeeOperationType(mLLBalanceSchema.getSubFeeOperationType());
                 tLLBalanceDB.setFeeFinaType(mLLBalanceSchema.getFeeFinaType());
                 tLLBalanceDB.setBatNo(mLLBalanceSchema.getBatNo());
                 tLLBalanceDB.setPolNo(mLLBalanceSchema.getPolNo());
                 tLLBalanceDB.getInfo();
                 mLLBalanceSchema =tLLBalanceDB.getSchema();
                 mLLBalanceSchema.setPay(tLLBalanceDB.getPay()+tPay);
                 mLLBalanceSchema.setModifyDate(CurrentDate);
                 mLLBalanceSchema.setModifyTime(CurrentTime);
                 map.put(mLLBalanceSchema, "UPDATE");
             }

            //修改赔案表中的“先给付金额（BeforePay）”=预付汇总表的“预付赔付金额（PrepaySum）”
            LLClaimDB tLLClaimDB = new LLClaimDB();
            tLLClaimDB.setClmNo(mLLClaimSchema.getClmNo());
            tLLClaimDB.getInfo();
            //LLPrepayClaimDB ttLLPrepayClaimDB = new LLPrepayClaimDB();
            ttLLPrepayClaimDB.setClmNo(mLLClaimSchema.getClmNo());
            ttLLPrepayClaimDB.getInfo();
            mLLClaimSchema=tLLClaimDB.getSchema();
            mLLClaimSchema.setBeforePay(tLLClaimDetailDB.getPrepaySum()+ttPrepaySum);//先给付金额
            mLLClaimSchema.setModifyDate(CurrentDate);
            mLLClaimSchema.setModifyTime(CurrentTime);
            map.put(mLLClaimSchema, "UPDATE");

            tReturn = true;
        }
        else
        {
            tReturn = false;
        }

        return tReturn;
    }


    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(map);
            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLClaimPrepayBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

}
