/**
 * Copyright (c) 2005 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.LLAccountSet;
import com.sinosoft.lis.db.LLAccountDB;
import com.sinosoft.lis.vschema.LLBAccountSet;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 理赔立案结论逻辑处理类</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: Sinosoft</p>
 * @author zl
 * @version 1.0
 */
public class LLAccountBL {
private static Logger logger = Logger.getLogger(LLAccountBL.class);
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
    private LLAccountSet mLLAccountSet = new LLAccountSet();

    public LLAccountBL() {
    }

    public static void main(String[] args) {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        logger.debug(
                "----------LLAccountBL begin submitData----------");
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }
        logger.debug(
                "----------LLAccountBL after getInputData----------");

        //检查数据合法性
        if (!checkInputData()) {
            return false;
        }
        logger.debug(
                "----------LLAccountBL after checkInputData----------");
        //进行业务处理
        if (!dealData(cOperate)) {
            return false;
        }
        logger.debug(
                "----------LLAccountBL after dealData----------");
        //准备往后台的数据
        if (!prepareOutputData()) {
            return false;
        }
        logger.debug(
                "----------LLAccountBL after prepareOutputData----------");
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(mInputData, cOperate)) {
            // @@错误处理
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLAccountBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        mInputData = null;
        return true;
    }

    public VData getResult() {
        return mResult;
    }

    /**
     * 从输入数据中得到所有对象
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData() {
        logger.debug("---LLAccountBL start getInputData()...");
        //获取页面报案信息
        mG = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0); //按类取值
        mTransferData = (TransferData) mInputData.getObjectByObjectName(
                "TransferData", 0);
        mLLAccountSet = (LLAccountSet) mInputData.getObjectByObjectName(
                "LLAccountSet", 0);
        return true;
    }

    /**
     * 校验传入的报案信息是否合法
     * @return：如果发生错误则返回false,否则返回true
     */
    private boolean checkInputData() {
        return true;
    }

    /**
     * 数据操作类业务处理
     * @return：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean dealData(String cOperate) {
        logger.debug("cOperate=" + cOperate);
        LLAccountSet tLLAccountSet = new LLAccountSet();
        String isql = "";
        if (cOperate.equals("INSERT") || cOperate.equals("UPDATE")) {
            logger.debug("Britney=" + mLLAccountSet.size());
            if (cOperate.equals("UPDATE") && mLLAccountSet.size() > 0) { //要是修改或删除需要备份到B表
                String SQL1 = "Select * From LLAccount where ContNo ='" +
                              mLLAccountSet.get(1).getContNo() + "'";
                logger.debug("Britney00=" + SQL1);
                LLAccountDB mLLAccountDB = new LLAccountDB();
                LLAccountSet SLLAccountSet = new LLAccountSet(); //查原表出来的Set

		/*SLLAccountSet = mLLAccountDB.executeQuery(SQL1);
                LLBAccountSet SLLBAccountSet = new LLBAccountSet(); //备份Set
                for (int a = 1; a <= SLLAccountSet.size(); a++) {
                    LLBAccountSchema sLLBAccountSchema = new LLBAccountSchema();
                    String mSerialNo = PubFun1.CreateMaxNo("ACCSERIALNO2", "10");
                    sLLBAccountSchema.setSerialNo(mSerialNo); //生成序列号
                    sLLBAccountSchema.setGrpContNo(SLLAccountSet.get(a).
                            getGrpContNo());
                    sLLBAccountSchema.setInsuredNo(SLLAccountSet.get(a).
                            getInsuredNo());
                    sLLBAccountSchema.setInsuredName(SLLAccountSet.get(a).
                            getInsuredName());
                    sLLBAccountSchema.setContNo(SLLAccountSet.get(a).getContNo());
                    sLLBAccountSchema.setName(SLLAccountSet.get(a).getName());
                    sLLBAccountSchema.setIdNo(SLLAccountSet.get(a).getIdNo());
                    sLLBAccountSchema.setBnfRate(SLLAccountSet.get(a).
                                                 getBnfRate());
                    sLLBAccountSchema.setBranchCode(SLLAccountSet.get(a).
                            getBranchCode());
                    sLLBAccountSchema.setBranchName(SLLAccountSet.get(a).
                            getBranchName());
                    //sLLBAccountSchema.setAccount(SLLAccountSet.get(a). getAccount());
                    //jinsh20070606...银行代码从页面取
		    sLLBAccountSchema.setAccount(mLLAccountSet.get(1).getAccount().toString());
		    sLLBAccountSchema.setBankCode(SLLAccountSet.get(a).
                                                  getBankCode());
                    sLLBAccountSchema.setMakeDate(SLLAccountSet.get(a).
                                                  getMakeDate());
                    sLLBAccountSchema.setMakeTime(SLLAccountSet.get(a).
                                                  getMakeTime());
                    sLLBAccountSchema.setModifyDate(CurrentDate);
                    sLLBAccountSchema.setModefyTime(CurrentTime);
                    sLLBAccountSchema.setOperator(mG.Operator);
                    SLLBAccountSet.add(sLLBAccountSchema);
                }
//                String SQL = "delete from LLBAccount where ContNo = '" +
//                             mLLAccountSet.get(1).getContNo() + "'";
//                logger.debug("Britney11= " + SQL);
//                map.put(SQL, "DELETE");
                map.put(SLLBAccountSet, "DELETE&INSERT");*/
            }
            for (int i = 1; i <= mLLAccountSet.size(); i++) {
                logger.debug("Britney112 ");
                LLAccountSchema mLLAccountSchema = new LLAccountSchema();
                mLLAccountSchema.setGrpContNo(mLLAccountSet.get(i).getGrpContNo());
                mLLAccountSchema.setInsuredNo(mLLAccountSet.get(i).getInsuredNo());
                mLLAccountSchema.setInsuredName(mLLAccountSet.get(i).
                                                getInsuredName());
                mLLAccountSchema.setContNo(mLLAccountSet.get(i).getContNo());
                mLLAccountSchema.setName(mLLAccountSet.get(i).getName());
                mLLAccountSchema.setIdNo(mLLAccountSet.get(i).getIdNo());
                mLLAccountSchema.setBnfRate(mLLAccountSet.get(i).getBnfRate());
                mLLAccountSchema.setBranchCode(mLLAccountSet.get(i).
                                               getBranchCode());
                mLLAccountSchema.setBranchName(mLLAccountSet.get(i).
                                               getBranchName());
                mLLAccountSchema.setAccount(mLLAccountSet.get(i).getAccount());
                mLLAccountSchema.setBankCode(mLLAccountSet.get(i).getBankCode());
                mLLAccountSchema.setMakeDate(CurrentDate);
                mLLAccountSchema.setMakeTime(CurrentTime);
                mLLAccountSchema.setModifyDate(CurrentDate);
                mLLAccountSchema.setModefyTime(CurrentTime);
                mLLAccountSchema.setOperator(mG.Operator);
                tLLAccountSet.add(mLLAccountSchema);

                if (cOperate.equals("UPDATE")) {
                    isql = "update lcinsured set bankcode='" +
                           mLLAccountSet.get(i).getBankCode() +
                           "',bankaccno='" + mLLAccountSet.get(i).getAccount() +
                           "',modifydate='" + CurrentDate + "',modifytime='" +
                           CurrentTime + "' where grpcontno='" +
                           mLLAccountSet.get(i).getGrpContNo() +
                           "' and insuredno='" +
                           mLLAccountSet.get(i).getInsuredNo() + "'  ";
                    map.put(isql, "UPDATE");
                }
            }
            String SQL = "delete from LLAccount where ContNo = '" +
                         mLLAccountSet.get(1).getContNo() + "'";
            logger.debug("Britney115= " + SQL);
            map.put(SQL, "DELETE");
            map.put(tLLAccountSet, "INSERT");


        }
        if (cOperate.equals("DELETE")) {
            if (mLLAccountSet.size() > 0) { //要是修改或删除需要备份到B表
                String SQL1 = "Select * From LLAccount where ContNo ='" +
                              mLLAccountSet.get(1).getContNo() + "'";
                LLAccountDB mLLAccountDB = new LLAccountDB();
                LLAccountSet SLLAccountSet = new LLAccountSet(); //查原表出来的Set
                SLLAccountSet = mLLAccountDB.executeQuery(SQL1);
                LLBAccountSet SLLBAccountSet = new LLBAccountSet(); //备份Set
                for (int a = 1; a <= SLLAccountSet.size(); a++) {
                    LLBAccountSchema sLLBAccountSchema = new LLBAccountSchema();
                    String mSerialNo = PubFun1.CreateMaxNo("ACCSERIALNO", "10");
                    sLLBAccountSchema.setSerialNo(mSerialNo); //生成序列号
                    sLLBAccountSchema.setGrpContNo(SLLAccountSet.get(a).
                            getGrpContNo());
                    sLLBAccountSchema.setInsuredNo(SLLAccountSet.get(a).
                            getInsuredNo());
                    sLLBAccountSchema.setInsuredName(SLLAccountSet.get(a).
                            getInsuredName());
                    sLLBAccountSchema.setContNo(SLLAccountSet.get(a).getContNo());
                    sLLBAccountSchema.setName(SLLAccountSet.get(a).getName());
                    sLLBAccountSchema.setIdNo(SLLAccountSet.get(a).getIdNo());
                    sLLBAccountSchema.setBnfRate(SLLAccountSet.get(a).
                                                 getBnfRate());
                    sLLBAccountSchema.setBranchCode(SLLAccountSet.get(a).
                            getBranchCode());
                    sLLBAccountSchema.setBranchName(SLLAccountSet.get(a).
                            getBranchName());
                    sLLBAccountSchema.setAccount(SLLAccountSet.get(a).
                                                 getAccount());
                    sLLBAccountSchema.setBankCode(SLLAccountSet.get(a).
                                                  getBankCode());
                    sLLBAccountSchema.setMakeDate(SLLAccountSet.get(a).
                                                  getMakeDate());
                    sLLBAccountSchema.setMakeTime(SLLAccountSet.get(a).
                                                  getMakeTime());
                    sLLBAccountSchema.setModifyDate(CurrentDate);
                    sLLBAccountSchema.setModefyTime(CurrentTime);
                    sLLBAccountSchema.setOperator(mG.Operator);
                    SLLBAccountSet.add(sLLBAccountSchema);
                }
                String SQL = "delete from  LLBAccount where ContNo = '" +
                             SLLAccountSet.get(1).getContNo() + "'";
                logger.debug("Britney118= " + SQL);
                map.put(SQL, "DELETE");

                map.put(SLLBAccountSet, "INSERT");
            }

            for (int i = 1; i <= mLLAccountSet.size(); i++) {
                String SQL = "delete from LLAccount where ContNo = '" +
                             mLLAccountSet.get(1).getContNo() +
                             "' and name = '" + mLLAccountSet.get(i).getName() +
                             "'";
                map.put(SQL, "DELETE");
            }

        }
        return true;
    }


    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private String getPolManageCom(String PolNo) {
        //查询管理机构(来自承保)
        String tSql = " select managecom from lcpol where "
                      + " PolNo = '" + PolNo + "'";
        ExeSQL tExeSQL = new ExeSQL();
        String tManageCom = tExeSQL.getOneValue(tSql);
        if (tManageCom == null) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLAccountBL";
            tError.functionName = "dealData";
            tError.errorMessage = "查询保单管理机构失败!";
            this.mErrors.addOneError(tError);
            return "false";
        }
        return tManageCom;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData() {
        try {
            mInputData.clear();
            mInputData.add(map);
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "LLAccountBL";
            tError.functionName = "prepareOutputData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

}
