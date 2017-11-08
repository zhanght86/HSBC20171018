

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.schema.RIUWTaskSchema;
import com.sinosoft.lis.db.RIUWTaskDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.vschema.RIUWTaskSet;
import com.sinosoft.lis.schema.RIAnwserIdeaSchema;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @Zhang Bin
 * @version 1.0
 */
public class AnswerUWReInsureBL {
    public AnswerUWReInsureBL() {
    }

    /** 传入参数 */
    private VData mInputData;
    /** 传入操作符 */
    private String mOperate;
    /** 登陆信息 */
    private GlobalInput mGlobalInput;
    /** 报错存储对象 */
    public CErrors mErrors = new CErrors();
    /** 最后保存结果 */
    private VData mResult = new VData();
    /** 最后递交Map */
    private MMap map = new MMap();
    /** 数据操作字符串 */
    private String mOperator;
    private String mManageCom;
    private String mOpeData;
    private String mEdorNo;
    private String mEdorType;
    private String mCaseNo;
    private String mOpeFlag;
    private String mRemark;
    private String mFilePath;
    private String mFileName;

    private TransferData mTransferData = new TransferData();
    /**再保审核任务表*/
    private RIUWTaskSchema mRIUWTaskSchema = new RIUWTaskSchema();

    /**再保审核任务核保意见表*/
    private RIAnwserIdeaSchema mRIAnwserIdeaSchema = new RIAnwserIdeaSchema();

    /**
     * submitData
     *
     * @param nInputData VData
     * @param cOperate String
     * @return boolean
     */
    public boolean submitData(VData nInputData, String cOperate) {
        this.mInputData = nInputData;
        this.mOperate = cOperate;

        if (!getInputData()) {
            return false;
        }
        if (!checkData()) {
            return false;
        }
        if (!dealData()) {
            return false;
        }
        if (!prepareOutputData()) {
            return false;
        }
        PubSubmit tPubSubmit = new PubSubmit();
        if (!tPubSubmit.submitData(this.mResult, "INSERT")) {
            this.mErrors.copyAllErrors(tPubSubmit.mErrors);
            return false;
        }
        return true;
    }

    /**
     * getInputData
     * @return boolean
     */
    private boolean getInputData() {
        mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        this.mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
        if (mGlobalInput == null) {
            // @@错误处理
            //this.mErrors.copyAllErrors( tLCContDB.mErrors );
            CError tError = new CError();
            tError.moduleName = "AnswerUWReInsurBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * checkData
     * @return boolean
     */
    private boolean checkData() {
        if (this.mGlobalInput == null) {
            String str = "登陆信息为null，可能是页面超时，请重新登陆!";
            buildError("checkData", str);
            return false;
        }
        return true;
    }

    /**
     * dealData
     * @return boolean
     */
    private boolean dealData() {
    mOpeData = (String) mTransferData.getValueByName("OpeData");
    mRemark = (String) mTransferData.getValueByName("Remark");
    mFilePath = (String) mTransferData.getValueByName("FilePath");
    mFileName = (String) mTransferData.getValueByName("FileName");
    mEdorNo = (String) mTransferData.getValueByName("EdorNo");
    mEdorType = (String) mTransferData.getValueByName("EdorType");
    mCaseNo = (String) mTransferData.getValueByName("CaseNo");
    mOpeFlag = (String) mTransferData.getValueByName("OpeFlag");
    int uwno = 0; //核保顺序号
    RIUWTaskDB tRIUWTaskDB = new RIUWTaskDB();
    StringBuffer sql = new StringBuffer();

    if(mOpeFlag.equals("01")){
        sql.append("select * from RIUWTASK b where b.serialno=(select max(a.serialno) from RIUWTask a where a.proposalgrpcontno='000000' and a.proposalcontno='");
        sql.append(mOpeData);
        sql.append("' and a.AuditType='01') ");

        RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
        tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
        if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
            buildError("dealData", "查询任务信息失败");
            return false;
        } else {
            mRIUWTaskSchema = tRIUWTaskSet.get(1);
            mRIUWTaskSchema.setState("01");
            mRIUWTaskSchema.setOperator(mGlobalInput.Operator);
            mRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
            mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
            mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());
            map.put(mRIUWTaskSchema, "UPDATE");
        }
        mRIAnwserIdeaSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
        mRIAnwserIdeaSchema.setUWNo(mRIUWTaskSchema.getUWNo());
        mRIAnwserIdeaSchema.setProposalGrpContNo("000000");
        mRIAnwserIdeaSchema.setProposalContNo(mOpeData);
        mRIAnwserIdeaSchema.setAuditType(mOpeFlag);
        mRIAnwserIdeaSchema.setAuditCode("000000");
        mRIAnwserIdeaSchema.setAuditAffixCode("000000");
        mRIAnwserIdeaSchema.setAdjunctPath(mFilePath + mFileName);
        mRIAnwserIdeaSchema.setReInsurer(mGlobalInput.Operator);
        mRIAnwserIdeaSchema.setOperator(mGlobalInput.Operator);
        mRIAnwserIdeaSchema.setUWIdea(mRemark);
        mRIAnwserIdeaSchema.setManageCom(mGlobalInput.ManageCom);
        mRIAnwserIdeaSchema.setMakeDate(PubFun.getCurrentDate());
        mRIAnwserIdeaSchema.setMakeTime(PubFun.getCurrentTime());
        mRIAnwserIdeaSchema.setModifyDate(PubFun.getCurrentDate());
        mRIAnwserIdeaSchema.setModifyTime(PubFun.getCurrentTime());
        map.put(mRIAnwserIdeaSchema, "INSERT");

    }else if(mOpeFlag.equals("03")){
        sql.append("select * from RIUWTASK b where b.serialno=(select max(a.serialno) from RIUWTask a where a.proposalcontno='000000' and a.proposalgrpcontno='");
        sql.append(mOpeData);
        sql.append("' and a.AuditType='03') ");

        RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
        tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
        if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
            buildError("dealData", "查询任务信息失败");
            return false;
        } else {
            mRIUWTaskSchema = tRIUWTaskSet.get(1);
            mRIUWTaskSchema.setState("01");
            mRIUWTaskSchema.setOperator(mGlobalInput.Operator);
            mRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
            mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
            mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());
            map.put(mRIUWTaskSchema, "UPDATE");
        }
            mRIAnwserIdeaSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
            mRIAnwserIdeaSchema.setUWNo(mRIUWTaskSchema.getUWNo());
            mRIAnwserIdeaSchema.setProposalGrpContNo("000000");
            mRIAnwserIdeaSchema.setProposalContNo(mOpeData);
            mRIAnwserIdeaSchema.setAuditType(mOpeFlag);
            mRIAnwserIdeaSchema.setAuditCode(mEdorNo);
            mRIAnwserIdeaSchema.setAuditAffixCode(mEdorType);
            mRIAnwserIdeaSchema.setAdjunctPath(mFilePath + mFileName);
            mRIAnwserIdeaSchema.setReInsurer(mGlobalInput.Operator);
            mRIAnwserIdeaSchema.setOperator(mGlobalInput.Operator);
            mRIAnwserIdeaSchema.setUWIdea(mRemark);
            mRIAnwserIdeaSchema.setManageCom(mGlobalInput.ManageCom);
            mRIAnwserIdeaSchema.setMakeDate(PubFun.getCurrentDate());
            mRIAnwserIdeaSchema.setMakeTime(PubFun.getCurrentTime());
            mRIAnwserIdeaSchema.setModifyDate(PubFun.getCurrentDate());
            mRIAnwserIdeaSchema.setModifyTime(PubFun.getCurrentTime());
            map.put(mRIAnwserIdeaSchema, "INSERT");
    } else if (mOpeFlag.equals("04")) {
        sql.append("select * from RIUWTASK a where a.serialno='");
        sql.append(mOpeData);
        sql.append("'");

        RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
        tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
        if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
            buildError("dealData", "查询任务信息失败");
            return false;
        } else {
            mRIUWTaskSchema = tRIUWTaskSet.get(1);
            mRIUWTaskSchema.setState("01");
            mRIUWTaskSchema.setOperator(mGlobalInput.Operator);
            mRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
            mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
            mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());
            map.put(mRIUWTaskSchema, "UPDATE");
        }
        mRIAnwserIdeaSchema.setSerialNo(mRIUWTaskSchema.getSerialNo());
        mRIAnwserIdeaSchema.setUWNo(mRIUWTaskSchema.getUWNo());
        mRIAnwserIdeaSchema.setProposalGrpContNo("000000");
        mRIAnwserIdeaSchema.setProposalContNo(mRIUWTaskSchema.getProposalContNo());
        mRIAnwserIdeaSchema.setAuditType(mOpeFlag);
        mRIAnwserIdeaSchema.setAuditCode(mCaseNo);
        mRIAnwserIdeaSchema.setAuditAffixCode("000000");
        mRIAnwserIdeaSchema.setAdjunctPath(mFilePath + mFileName);
        mRIAnwserIdeaSchema.setReInsurer(mGlobalInput.Operator);
        mRIAnwserIdeaSchema.setOperator(mGlobalInput.Operator);
        mRIAnwserIdeaSchema.setUWIdea(mRemark);
        mRIAnwserIdeaSchema.setManageCom(mGlobalInput.ManageCom);
        mRIAnwserIdeaSchema.setMakeDate(PubFun.getCurrentDate());
        mRIAnwserIdeaSchema.setMakeTime(PubFun.getCurrentTime());
        mRIAnwserIdeaSchema.setModifyDate(PubFun.getCurrentDate());
        mRIAnwserIdeaSchema.setModifyTime(PubFun.getCurrentTime());
        map.put(mRIAnwserIdeaSchema, "INSERT");
    } else if(mOpeFlag.equals("05")){
        sql.append("select * from RIUWTask where ProposalGrpContNo = '"+mOpeData+"' and polno='000000' and dutycode='000000' and AuditCode='000000' and AuditAffixCode='000000' and AuditType='05' order by uwno desc");
        System.out.println("sql: "+sql);
        RIUWTaskSet tRIUWTaskSet = new RIUWTaskSet();
        tRIUWTaskSet = tRIUWTaskDB.executeQuery(sql.toString());
        if (tRIUWTaskSet == null || tRIUWTaskSet.size() == 0) {
            String str = "没有相关再保审核信息";
            buildError("checkData", str);
            return false;
        } else {
            uwno = tRIUWTaskSet.size();
        }
        tRIUWTaskDB=new RIUWTaskDB();
        tRIUWTaskDB.setProposalGrpContNo(mOpeData);
        tRIUWTaskDB.setAuditCode("000000");
        tRIUWTaskDB.setAuditAffixCode("000000");
        tRIUWTaskDB.setUWNo(uwno);
        if(!tRIUWTaskDB.getInfo()){
            String str = "查询再保审核信息失败";
            buildError("checkData", str);
            return false;
        }
        //再保审核任务表
        mRIUWTaskSchema=tRIUWTaskDB.getSchema();
        mRIUWTaskSchema.setState("01"); //01-已回复
        mRIUWTaskSchema.setOperator(mGlobalInput.Operator);
        mRIUWTaskSchema.setManageCom(mGlobalInput.ManageCom);
        mRIUWTaskSchema.setModifyDate(PubFun.getCurrentDate());
        mRIUWTaskSchema.setModifyTime(PubFun.getCurrentTime());
        map.put(mRIUWTaskSchema,"UPDATE");

        //再保审核任务核保意见表
        mRIAnwserIdeaSchema.setUWNo(uwno);
        mRIAnwserIdeaSchema.setProposalGrpContNo(this.mOpeData);
        mRIAnwserIdeaSchema.setAuditType("05");
        mRIAnwserIdeaSchema.setAuditCode("000000");
        mRIAnwserIdeaSchema.setAuditAffixCode("000000");
        mRIAnwserIdeaSchema.setAdjunctPath(mFilePath + mFileName);
        mRIAnwserIdeaSchema.setReInsurer(mGlobalInput.Operator);
        mRIAnwserIdeaSchema.setOperator(mGlobalInput.Operator);
        mRIAnwserIdeaSchema.setUWIdea(mRemark);
        mRIAnwserIdeaSchema.setManageCom(mGlobalInput.ManageCom);
        mRIAnwserIdeaSchema.setMakeDate(PubFun.getCurrentDate());
        mRIAnwserIdeaSchema.setMakeTime(PubFun.getCurrentTime());
        mRIAnwserIdeaSchema.setModifyDate(PubFun.getCurrentDate());
        mRIAnwserIdeaSchema.setModifyTime(PubFun.getCurrentTime());

        map.put(mRIAnwserIdeaSchema, "INSERT");
    }else
    {
        String str = "非法数据处理类型 !";
        buildError("dealData", str);
    }
    return true;
    }

    public static void main(String[] args) {
        AnswerUWReInsureBL tAnswerUWReInsureBL = new AnswerUWReInsureBL();
        GlobalInput tG = new GlobalInput();
        tG.Operator="zb";
        tG.ManageCom="86";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("OpeData", "2008070110");
        tTransferData.setNameAndValue("FilePath", "/ing/ui/temp/reinsure/2008-09-05-7882/");
        tTransferData.setNameAndValue("FileName", "个人周报-张斌-2008年08月08日.xls");
        tTransferData.setNameAndValue("EdorNo", "");
        tTransferData.setNameAndValue("EdorType", "");
        tTransferData.setNameAndValue("CaseNo", "");
        tTransferData.setNameAndValue("OpeFlag", "01");
        tTransferData.setNameAndValue("Remark", "");
        VData tVData = new VData();
        tVData.add(tG );
        tVData.add(tTransferData);
        tAnswerUWReInsureBL.submitData(tVData,"") ;
    }

    /**
     * prepareOutputData
     *
     * @return boolean
     */
    private boolean prepareOutputData() {
        this.mResult.add(map);
        return true;
    }

    /**
     * 出错处理
     * @param szFunc String
     * @param szErrMsg String
     */
    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "AnswerUWReInsureBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
