/**
 * <p>Title: 预付管理确认业务类 </p>
 * <p>Description: </p>
 * <p>Company: SinoSoft</p>
 * @author yuejw
 */

package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

public class LClaimPrepayConfirmBL {
private static Logger logger = Logger.getLogger(LClaimPrepayConfirmBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 往后面传输数据的容器 */
    private VData mInputData;
    /** 往界面传输数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    private GlobalInput mG = new GlobalInput();
    private MMap map = new MMap();
    private TransferData mTransferData = new TransferData();
    //private LLBnfSchema mLLBnfSchema=new  LLBnfSchema();

    private Reflections tRef = new Reflections();
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private String mClmNo;

    //double tPrepaySum=0;//预付金额


    public LClaimPrepayConfirmBL() {
    }

    public static void main(String[] args) {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        logger.debug(
                "----------LClaimPrepayConfirmBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }
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
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mClmNo=(String) mTransferData.getValueByName("RptNo");
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
            if (mTransferData == null || mG == null)
            {
                CError tError = new CError();
                tError.moduleName = "LClaimPrepayConfirmBL";
                tError.functionName = "checkInputData";
                tError.errorMessage = "在数据传入时出错!";
                this.mErrors.addOneError(tError);
                return false;
            }
            //非空字段检验
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LClaimPrepayConfirmBL";
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
        try
        {
            //1、作数据校验，是否完成“受益人分配”工作，如果没有则不允许进行 预付确认<是否在此进行有待考虑>
            //查询LLBnf表（受益人账户），条件（赔案号，受益性质（BnfKind）==B），统计所有的受益金额
            //查询LLPrepayClaim（预付赔案记录）一条汇总的预付赔案的预付金额<条件（赔案号)>
            //String str_llbnf ="select clmno,sum(getmoney) from llbnf where clmno='" +mClmNo +"' and and bnfkind='B' group by clmno";
            //String str_prepay ="select clmno,prepaysum from llprepayclaim where clmno='" + mClmNo+ "' ";
            //<“受益人分配”工作检验在前台进行>

            //2、更新（UpDate）LLBnf（受益人账户表）,条件（赔案号，受益性质（BnfKind）==B），
            //修改保险金支付标志（CasePayFlag=1）
            String strSql = "update LLBnf set CasePayFlag='1' where clmno='" + mClmNo+ "'";
            map.put(strSql, "UPDATE");

            //3、[预付确认]，转移数据,生成实付总表（LJAGet）数据和赔付实付表（LJAGetClaim）数据
            //赔付应付表（LJSGetClaim）表的相应字段 转入 赔付实付表（LJAGetClaim）
            //应付总表（LJSGet）表的相应字段 转入 生成实付总表（LJAGet）
            LJSGetDB tLJSGetDB = new LJSGetDB();
            tLJSGetDB.setOtherNo(mClmNo);
            tLJSGetDB.setOtherNoType("5");
            LJSGetSet tLJSGetSet = new LJSGetSet();
            tLJSGetSet = tLJSGetDB.query(); //[应付总表（LJSGet）]
            LJAGetSet tLJAGetSet = new LJAGetSet(); //[实付总表（LJAGet)]

            if (tLJSGetSet != null && tLJSGetSet.size() != 0)
            {
                Reflections tRef = new Reflections();
                LJAGetSchema tLJAGetSchema = new LJAGetSchema();
                tLJAGetSet.add(tLJAGetSchema);
                tRef.transFields(tLJAGetSet, tLJSGetSet); //[应付总表（LJSGet）----实付总表（LJAGet)]
                for (int j = 1; j <= tLJSGetSet.size(); j++)
                {
                    String tNo = PubFun1.CreateMaxNo("GETNO", 10); //生成实付号
                    tLJAGetSet.get(j).setActuGetNo(tNo);
                    tLJAGetSet.get(j).setOperator(mG.Operator);
                    tLJAGetSet.get(j).setMakeDate(CurrentDate);
                    tLJAGetSet.get(j).setMakeTime(CurrentTime);
                    tLJAGetSet.get(j).setModifyDate(CurrentDate);
                    tLJAGetSet.get(j).setModifyTime(CurrentTime);
                    tLJAGetSet.get(j).setManageCom(mG.ManageCom);
                    tLJAGetSet.get(j).setOperState("0");

                    LJSGetClaimDB tLJSGetClaimDB = new LJSGetClaimDB(); //赔付应付表
                    tLJSGetClaimDB.setGetNoticeNo(tLJSGetSet.get(j).
                                                  getGetNoticeNo());
                    LJSGetClaimSet tLJSGetClaimSet = new LJSGetClaimSet();
                    tLJSGetClaimDB.setOtherNo(mClmNo);
                    tLJSGetClaimDB.setOtherNoType("5");
                    tLJSGetClaimSet = tLJSGetClaimDB.query(); //[]
                    LJAGetClaimSet tLJAGetClaimSet = new LJAGetClaimSet(); //[]

                    if (tLJSGetClaimSet != null && tLJSGetClaimSet.size() != 0)
                    {
                        Reflections ttRef = new Reflections();
                        LJAGetClaimSchema tLJAGetClaimSchema = new
                                LJAGetClaimSchema();
                        tLJAGetClaimSet.add(tLJAGetClaimSchema);
                        ttRef.transFields(tLJAGetClaimSet, tLJSGetClaimSet);
                        for (int k = 1; k <= tLJSGetClaimSet.size(); k++)
                        {
                            tLJAGetClaimSet.get(k).setActuGetNo(tNo);
                            tLJAGetClaimSet.get(k).setOPConfirmCode(mG.Operator);
                            tLJAGetClaimSet.get(k).setOPConfirmDate(CurrentDate);
                            tLJAGetClaimSet.get(k).setOPConfirmTime(CurrentTime);
                            tLJAGetClaimSet.get(k).setOperator(mG.Operator);
                            tLJAGetClaimSet.get(k).setMakeDate(CurrentDate);
                            tLJAGetClaimSet.get(k).setMakeTime(CurrentTime);
                            tLJAGetClaimSet.get(k).setModifyDate(CurrentDate);
                            tLJAGetClaimSet.get(k).setModifyTime(CurrentTime);
                            tLJAGetClaimSet.get(k).setManageCom(mG.ManageCom);
                            tLJAGetClaimSet.get(k).setOperState("0");

                            //如果支付金额为零,就不添加到实付表
                            if (tLJAGetClaimSet.get(k).getPay() != 0) {
                                map.put(tLJAGetClaimSet.get(k), "DELETE&INSERT");
                            }

                        }
//                        map.put(tLJAGetClaimSet, "DELETE&INSERT");
                        map.put(tLJSGetClaimSet, "DELETE");
                        //如果总给付金额为零,就不添加到实付表
                        if (tLJAGetSet.get(j).getSumGetMoney() != 0)
                        {
                            map.put(tLJAGetSet.get(j), "DELETE&INSERT");
                        }

                    }
                }
//                map.put(tLJAGetSet, "DELETE&INSERT");
                map.put(tLJSGetSet, "DELETE");

                //插入预付打印------------------------------------------------------
                //首先删除
                String tDSql = "delete from LOPRTManager where 1=1 "
                               + " and OtherNo = '" + mClmNo + "'"
                               + " and Code = 'PCT014'";
                map.put(tDSql, "DELETE");

                if (!insertLOPRTManager("PCT014")) //插入预付打印
                {
                    return false;
                }

            }
            tReturn = true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LClaimPrepayConfirmBL";
            tError.functionName = "dealdata";
            tError.errorMessage = "业务处理过程中出现错误";
            this.mErrors.addOneError(tError);
            return false;
        }


        return tReturn;
    }

    /**
     * 添加打印数据
     * 2005-8-16 14:49
     * @return boolean
     */
    private boolean insertLOPRTManager(String tCode)
    {
        LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
        //插入新值
        String tPrtSeq = PubFun1.CreateMaxNo("PRTSEQNO", 10); //生成印刷流水号
        tLOPRTManagerSchema.setPrtSeq(tPrtSeq); //印刷流水号
        tLOPRTManagerSchema.setOtherNo(mClmNo); //对应其它号码
        tLOPRTManagerSchema.setOtherNoType("05"); //其它号码类型--05赔案号
        tLOPRTManagerSchema.setCode(tCode); //单据编码
        tLOPRTManagerSchema.setManageCom(mG.ManageCom); //管理机构
        tLOPRTManagerSchema.setReqCom(mG.ComCode); //发起机构
        tLOPRTManagerSchema.setReqOperator(mG.Operator); //发起人
        tLOPRTManagerSchema.setPrtType("0"); //打印方式
        tLOPRTManagerSchema.setStateFlag("0"); //打印状态
        tLOPRTManagerSchema.setMakeDate(CurrentDate); //入机日期
        tLOPRTManagerSchema.setMakeTime(CurrentTime); //入机时间
        tLOPRTManagerSchema.setPatchFlag("0"); //补打标志
        tLOPRTManagerSchema.setStandbyFlag1(CurrentDate);

        //查询立案信息
        LLCaseDB tLLCaseDB = new LLCaseDB();
        String tSSql = "select * from llcase where 1=1 "
                     + " and caseno = '" + mClmNo + "'";
        LLCaseSet tLLCaseSet = tLLCaseDB.executeQuery(tSSql);
        String tCusNo = tLLCaseSet.get(1).getCustomerNo();
        tLOPRTManagerSchema.setStandbyFlag4(tCusNo); //客户号码

        //立案日期
        String tSql2 = "select to_char(rgtdate,'yyyy-mm-dd') from llregister a where "
              + " a.rgtno = '" + mClmNo + "'";
        ExeSQL tExeSQL2 = new ExeSQL();
        String tRgtDate = tExeSQL2.getOneValue(tSql2);

        tLOPRTManagerSchema.setStandbyFlag5(tRgtDate); //立案日期
        tLOPRTManagerSchema.setStandbyFlag6("30"); //赔案状态

        map.put(tLOPRTManagerSchema, "INSERT");
        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData()
    {
        try
        {
            mResult.add(map);
            mResult.add(mTransferData);
            mResult.add(mG);
            return true;
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LClaimPrepayConfirmBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
    }

}
