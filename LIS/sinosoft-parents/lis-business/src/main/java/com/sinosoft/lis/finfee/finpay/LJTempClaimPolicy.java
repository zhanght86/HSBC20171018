package com.sinosoft.lis.finfee.finpay;
import org.apache.log4j.Logger;

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

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class LJTempClaimPolicy implements LJTempIF {
private static Logger logger = Logger.getLogger(LJTempClaimPolicy.class);

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
        //转账支票
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

        //判断是否银行在途
        if(mLJTempFeeSchema.getRemark()==null||mLJTempFeeSchema.getRemark().equals("")){
            buildError("dealData", "没有得到应交号码！");
            return false;
        }
        LJSPayDB tLJSPayDB = new LJSPayDB();
        tLJSPayDB.setGetNoticeNo(mLJTempFeeSchema.getRemark());
        if(!tLJSPayDB.getInfo()){
            buildError("dealData", "没有得到应交记录！");
            return false;
        }
        LJSPaySchema tLJSPaySchema = tLJSPayDB.getSchema();
        if(tLJSPaySchema.getBankOnTheWayFlag() != null && tLJSPaySchema.getBankOnTheWayFlag().equals("1")){
            buildError("dealData", "银行在途不能交费！");
            return false;
        }

        // 保存投保人名称
//        String tSql = "select * from LCCont where prtno='" +mLJTempFeeSchema.getOtherNo() + "'";
//        LCContDB tLCContDB = new LCContDB();
//        LCContSet tLCContSet = new LCContSet();
//        tLCContSet = tLCContDB.executeQuery(tSql);
//
//        if (tLCContSet.size() > 0) {
//            mLJTempFeeSchema.setAPPntName(tLCContSet.get(1).getAppntName());
//        }
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
