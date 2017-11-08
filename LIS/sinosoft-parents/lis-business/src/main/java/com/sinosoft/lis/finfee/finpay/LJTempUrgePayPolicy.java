package com.sinosoft.lis.finfee.finpay;
import org.apache.log4j.Logger;

import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.schema.LJTempFeeSchema;
import com.sinosoft.lis.schema.LJTempFeeClassSchema;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.db.LCContDB;
import com.sinosoft.lis.db.LCGrpContDB;
import com.sinosoft.lis.db.LJTempFeeDB;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.vschema.LCGrpContSet;
import com.sinosoft.lis.vschema.LCPolSet;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.lis.vschema.LCContSet;
import com.sinosoft.lis.vschema.LJTempFeeSet;
import com.sinosoft.lis.db.LCPolDB;
import com.sinosoft.lis.db.LMRiskAppDB;
import com.sinosoft.lis.db.LJSPayDB;
import com.sinosoft.lis.schema.LJSPaySchema;
import com.sinosoft.lis.schema.LCPremSchema;
import com.sinosoft.lis.db.LCPremDB;
import com.sinosoft.lis.vschema.LCPremSet;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
public class LJTempUrgePayPolicy implements LJTempIF {
private static Logger logger = Logger.getLogger(LJTempUrgePayPolicy.class);

    public CErrors mErrors = new CErrors();
    private VData mInputData = new VData();

    private GlobalInput globalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private LJTempFeeSchema mLJTempFeeSchema = new LJTempFeeSchema();
    private LJTempFeeClassSchema mLJTempFeeClassSchema = new LJTempFeeClassSchema();

    public boolean submitData(VData cInputData, String cOperate){
        if(!getInputData(cInputData,cOperate)){
            return false;
        }

        if(!dealData()){
            return false;
        }
        return true;
    }

    public boolean getInputData(VData cInputData,String cOperate){
        globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        this.mLJTempFeeSchema = (LJTempFeeSchema)cInputData.getObjectByObjectName("LJTempFeeSchema", 0);
        this.mLJTempFeeClassSchema = (LJTempFeeClassSchema)cInputData.getObjectByObjectName("LJTempFeeClassSchema", 0);

        return true;
    }

    public boolean dealData() {
        String currentDate = PubFun.getCurrentDate();
        String currentTime = PubFun.getCurrentTime();
        //现金支票、转账支票
        if("2".equals(mLJTempFeeClassSchema.getPayMode())||"3".equals(mLJTempFeeClassSchema.getPayMode())){
            mLJTempFeeSchema.setEnterAccDate("");
            mLJTempFeeClassSchema.setEnterAccDate("");
        }

        mLJTempFeeSchema.setMakeDate(currentDate);
        mLJTempFeeSchema.setMakeTime(currentTime);
        mLJTempFeeSchema.setModifyDate(currentDate);
        mLJTempFeeSchema.setModifyTime(currentTime);
        mLJTempFeeSchema.setOperator(globalInput.Operator);

        if("1".equals(mLJTempFeeClassSchema.getPayMode())||"7".equals(mLJTempFeeClassSchema.getPayMode())){
            mLJTempFeeClassSchema.setChequeNo("000000");
        }

        mLJTempFeeClassSchema.setMakeDate(currentDate);
        mLJTempFeeClassSchema.setMakeTime(currentTime);
        mLJTempFeeClassSchema.setModifyDate(currentDate);
        mLJTempFeeClassSchema.setModifyTime(currentTime);
        mLJTempFeeClassSchema.setOperator(globalInput.Operator);

        if (mLJTempFeeSchema.getOtherNo().equals("") || mLJTempFeeSchema.getOtherNo() == null){
            CError tError = new CError();
            tError.moduleName = "TempFeeBL";
            tError.functionName = "checkData";
            tError.errorMessage = "未录入投保单号码";
            this.mErrors.addOneError(tError);
            return false;
        }

        String BDQ = "select * from lcprem where contno='?contno?' and payintv>'0' and urgepayflag='N' ";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(BDQ);
        sqlbv.put("contno", mLJTempFeeSchema.getOtherNo());
        LCPremSet tLCPremSet = new LCPremSet();
        LCPremDB tLCPremDB = new LCPremDB();
        tLCPremSet = tLCPremDB.executeQuery(sqlbv);
        if (tLCPremSet.size() == 0)
        {
            CError tError = new CError();
            tError.moduleName = "TempFeeBL";
            tError.functionName = "dealdate";
            tError.errorMessage = "该保单无不定期交费险种";
            this.mErrors.addOneError(tError);
            return false;
        }
        /**------------*/
        // 代理人销售渠道
        if (mLJTempFeeSchema.getAgentCode() != null) {
            LAAgentDB tLAAgentDB = new LAAgentDB();
            tLAAgentDB.setAgentCode(mLJTempFeeSchema.getAgentCode());
            if (!tLAAgentDB.getInfo()) {
                CError tError = new CError();
                tError.moduleName = "TempFeeBL";
                tError.functionName = "checkData";
                tError.errorMessage = "未找到代理人销售机构!";
                this.mErrors.addOneError(tError);
                return false;
            }

            LAAgentSchema tLAAgentSchema = new LAAgentSchema();
            tLAAgentSchema = tLAAgentDB.getSchema();
            if (tLAAgentSchema != null) {
                mLJTempFeeSchema.setSaleChnl(tLAAgentSchema.getBranchType());
                mLJTempFeeSchema.setPolicyCom(tLAAgentSchema.getManageCom());
            }
        }
        /**没有应缴记录，不用判断银行在途**/

        return true;
    }

    public CErrors getCErrors() {
        return mErrors;
    }

    /*
     * add by kevin, 2002-10-14
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "ReComManageBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

}
