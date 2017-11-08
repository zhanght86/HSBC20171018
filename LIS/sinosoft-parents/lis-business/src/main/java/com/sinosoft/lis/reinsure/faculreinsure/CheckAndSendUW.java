

package com.sinosoft.lis.reinsure.faculreinsure;

import com.sinosoft.lis.db.RIGUWErrorDB;
import com.sinosoft.lis.db.RIGrpStateDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.RIGrpStateSchema;
import com.sinosoft.lis.vschema.RIGUWErrorSet;
import com.sinosoft.lis.vschema.RIGrpStateSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
/**
 * 结案审核校验及审核发送接口
 *
 */
public class CheckAndSendUW {
    /** 报错存储对象 */
    public CErrors mErrors = new CErrors();
    /** 最后保存结果 */
    private VData mResult = new VData();
    private VData mInputData;
    /** 登陆信息 */
    private GlobalInput mGlobalInput;
    private TransferData mTransferData;

    public boolean getInputData() {
        this.mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0);
        this.mTransferData = (TransferData) mInputData.getObjectByObjectName("TransferData", 0);
        if (mGlobalInput == null) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "AutoSendUWReInsure";
            tError.functionName = "getInputData";
            tError.errorMessage = "前台传输全局公共数据失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public boolean submitData(VData nInputData, String cOperate) {
        this.mInputData = nInputData;
        if (!getInputData()) {
            return false;
        }
        String caseNo = (String) mTransferData.getValueByName("CaseNo");
        StringBuffer sql = new StringBuffer();
        sql.append("select * from RIGUWError a where a.CalItemType='14' and a.AuditCode='");
        sql.append(caseNo);
        sql.append("'");
        RIGUWErrorDB tRIGUWErrorDB = new RIGUWErrorDB();
        RIGUWErrorSet tRIGUWErrorSet = tRIGUWErrorDB.executeQuery(sql.toString());

        if (tRIGUWErrorSet != null && tRIGUWErrorSet.size() > 0) {
            //有核赔结果
            sql = new StringBuffer();
            sql.append("select * from RIGrpState a where a.serialno=(select max(serialno) from RIGrpState b where b.Exetype='04' and b.caseno = '");
            sql.append(caseNo);
            sql.append("')");
            RIGrpStateDB tRIGrpStateDB = new RIGrpStateDB();
            RIGrpStateSet tRIGrpStateSet = tRIGrpStateDB.executeQuery(sql.toString());
            TransferData tTransferData = new TransferData();
            if (tRIGrpStateSet == null || tRIGrpStateSet.size() == 0) {
                //没有审核任务,调用自动发送,返回false
                AutoSendUW tAutoSendUW = new AutoSendUW();
                if (!tAutoSendUW.submitData(nInputData, cOperate)) {
                    this.mErrors.copyAllErrors(tAutoSendUW.mErrors);
                }
                tTransferData.setNameAndValue("Message","该赔案需要再保核赔，核赔信息已发送到再保系统，请关注再保核赔信息。");
                mResult.add(tTransferData);
                return false;
            } else if (tRIGrpStateSet.get(1).getState().equals("02")) {
                // 有审核任务未确认,返回false
                tTransferData.setNameAndValue("Message","再保核赔任务未审核完毕，不能结案。");
                mResult.add(tTransferData);
                return false;
            }
        }
        return true;
    }

    public VData getResult() {
        return this.mResult;
    }

    public static void main(String[] args) {
        CheckAndSendUW tCheckAndSendUW = new CheckAndSendUW();
        GlobalInput globalInput = new GlobalInput();
        globalInput.ManageCom = "8611";
        globalInput.Operator = "001";
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("CaseNo", "90000000028");
        VData nInputData = new VData();
        nInputData.add(tTransferData);
        nInputData.add(globalInput);
        boolean b=tCheckAndSendUW.submitData(nInputData, "04");

        VData tVData = tCheckAndSendUW.getResult();
        TransferData tD = (TransferData) tVData.getObjectByObjectName("TransferData",0);
        if(!b){
            System.out.println(" Message: "+(String)tD.getValueByName("Message")+"  "+b);
        }else{
            System.out.println(" 可以结案 ");
        }

    }
}
