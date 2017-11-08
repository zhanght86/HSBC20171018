package com.sinosoft.lis.fininterface;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.FIDataDealErrSet;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.FIDataDealErrSchema;

public class FiDistillErrInfo {
private static Logger logger = Logger.getLogger(FiDistillErrInfo.class);
    public FiDistillErrInfo() {
    }

    public static void main(String[] args) {
        FiDistillErrInfo fidistillerrinfo = new FiDistillErrInfo();
    }

    public boolean DealError(String BatchNo, String ASerialNo,
                             String CertificateId,
                             String AccountDate, String eInfo, String eType,
                             String ErrStage) {
        FIDataDealErrSet tFIDataDealErrSet = new FIDataDealErrSet();
        FIDataDealErrSchema tFIDataDealErrSchema = new
                FIDataDealErrSchema();
        String EeeSerialNo = PubFun1.CreateMaxNo(
                "EeeSerialNo", 20);
        tFIDataDealErrSchema.setEeeSerialNo(EeeSerialNo);
        tFIDataDealErrSchema.setErrStage(ErrStage);
        tFIDataDealErrSchema.setAFSerialNo(ASerialNo);
        tFIDataDealErrSchema.setBatchNo(BatchNo);
        tFIDataDealErrSchema.setCertificateid(CertificateId);
        tFIDataDealErrSchema.setBussDate(AccountDate);
        tFIDataDealErrSchema.setDealDescription("");
        tFIDataDealErrSchema.setErrInfo(
                eInfo);
        tFIDataDealErrSchema.setErrType(eType);
        tFIDataDealErrSchema.setErrDealState("01");
        tFIDataDealErrSchema.setMakeDate(PubFun.
                                         getCurrentDate());
        tFIDataDealErrSchema.setMakeTime(PubFun.
                                         getCurrentTime());
        tFIDataDealErrSet.add(tFIDataDealErrSchema);
        LogInfoSubmit tLogInfoSubmit = new LogInfoSubmit();
        VData tVData = new VData();
        tVData.add(tFIDataDealErrSet);
        tLogInfoSubmit.submitDealErrData(tVData);
        return true;
    }
}
