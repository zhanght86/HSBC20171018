
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
import com.sinosoft.lis.db.LCContStateDB;
import com.sinosoft.lis.vschema.LCContStateSet;
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
public class LJTempConBefPolicy implements LJTempIF {
private static Logger logger = Logger.getLogger(LJTempConBefPolicy.class);

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

        // 判断该投保单是否作过撤单 uwflag='a'
	        String nSql = "select * from lccont where contno='?contno?' and uwflag='a'";
	        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
	        sqlbv.sql(nSql);
	        sqlbv.put("contno", mLJTempFeeSchema.getOtherNo());
	        LCContDB nLCContDB = new LCContDB();
	        LCContSet nLCContSet = new LCContSet();
	        nLCContSet = nLCContDB.executeQuery(sqlbv);
	        if (nLCContSet.size() > 0) {
	            CError tError = new CError();
	            tError.moduleName = "TempFeeBL";
	            tError.functionName = "checkData";
	            tError.errorMessage = "该投保单已经做过撤保，不能录入暂交费";
	            this.mErrors.addOneError(tError);
	            return false;
	        }
	    if("1".equals(mLJTempFeeClassSchema.getPayMode())||"3".equals(mLJTempFeeClassSchema.getPayMode()))
	    {
	        // 判断该保单效力是否终止
	        nSql = "select * from lccont where contno = '?contno?' and appflag='4'" ;
	        SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
	        sqlbv1.sql(nSql);
	        sqlbv1.put("contno", mLJTempFeeSchema.getOtherNo());
	        nLCContDB = new LCContDB();
	        nLCContSet = new LCContSet();
	        nLCContSet = nLCContDB.executeQuery(sqlbv1);
	        if (nLCContSet.size() > 0) {
	            CError tError = new CError();
	            tError.moduleName = "TempFeeBL";
	            tError.functionName = "checkData";
	            tError.errorMessage = "该保单效力已经终止，不可再交费！";
	            this.mErrors.addOneError(tError);
	            return false;
	        }
	        // 判断该保单是否失效（效力中止）
	        nSql = "select * From lccontstate a,lcpol b where a.contno='?contno?' and a.contno=b.contno and a.polno=b.polno and a.statetype='Available' and a.state='1' and a.startdate>b.paytodate and a.enddate is null" ;
	        SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
	        sqlbv2.sql(nSql);
	        sqlbv2.put("contno", mLJTempFeeSchema.getOtherNo());
	        LCContStateDB nLCContStateDB = new LCContStateDB();
	        LCContStateSet nLCContStateSet = new LCContStateSet();
	        nLCContStateSet = nLCContStateDB.executeQuery(sqlbv2);
	        if (nLCContStateSet.size() > 0) {
	            CError tError = new CError();
	            tError.moduleName = "TempFeeBL";
	            tError.functionName = "checkData";
	            tError.errorMessage = "该保单已经失效，不可再做续期交费，请保全复效！";
	            this.mErrors.addOneError(tError);
	            return false;
	        }
        }

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

        // 保存投保人名称
        String tSql = "select * from LCCont where prtno='?prtno?'";
        SQLwithBindVariables sqlbv3=new SQLwithBindVariables();
        sqlbv3.sql(tSql);
        sqlbv3.put("prtno", mLJTempFeeSchema.getOtherNo());
        LCContDB tLCContDB = new LCContDB();
        LCContSet tLCContSet = new LCContSet();
        tLCContSet = tLCContDB.executeQuery(sqlbv3);

        if (tLCContSet.size() > 0) {
            mLJTempFeeSchema.setAPPntName(tLCContSet.get(1).getAppntName());
        }

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
