

package com.sinosoft.lis.reinsure.impdata;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.vschema.RIAccumulateDefSet;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.vschema.RIItemCalSet;
import com.sinosoft.lis.db.RIItemCalDB;

/**
 * <p>Title: 业务数据导入处理类</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class RIBDataImpl {
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 前台传入的公共变量 */
    private GlobalInput globalInput = new GlobalInput();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    private RIAccumulateDefSet mRIAccumulateDefSet = new RIAccumulateDefSet();

    private TransferData mTransferData = new TransferData();

    public RIBDataImpl() {
    }

    /**
     * 提交数据处理方法
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        if (!getInputData(cInputData)){
            return false;
        }
        if (!dealData()) {
            return false;
        }

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        this.globalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        if(this.globalInput==null){
            buildError("getInputData","前台传输用户信息数据失败");
            return false;
        }
        this.mInputData = (VData) cInputData.clone();
        if(mInputData==null){
            buildError("getInputData","前台传输全局公共数据失败");
            return false;
        }
        mRIAccumulateDefSet = (RIAccumulateDefSet)mInputData.getObjectByObjectName("RIAccumulateDefSet",0);
        if(mRIAccumulateDefSet==null||mRIAccumulateDefSet.size()==0){
            buildError("getInputData","前台传输任务列表为空");
            return false;
        }
        mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData",0);
        if(mTransferData==null){
            buildError("getInputData","前台传输数据失败");
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        RIBDataImport tRIBDataImport ;
        RIAccRiskCal tRIAccRiskCal ;
        RIItemCalSet tRIItemCalSet ;
        TransferData tTransferData ;
        VData tVData ;
        RIItemCalDB tRIItemCalDB = new RIItemCalDB();
        String strSQL ;
        for (int i = 1; i <= mRIAccumulateDefSet.size(); i++) {
            strSQL =" select * from riitemcal a where a.arithmetictype='21' and a.ArithmeticID='" + mRIAccumulateDefSet.get(i).getAccumulateDefNO() + "' order by a.CalItemOrder ";
            tRIItemCalSet = tRIItemCalDB.executeQuery(strSQL);
            System.out.println(tRIItemCalSet.size());
            if (tRIItemCalDB.mErrors.needDealError()) {
                buildError("dealData", "获取业务数据导入算法时出错");
                return false;
            }
            if (tRIItemCalSet.size() == 0) {
                buildError("dealData", "没有累计风险编码为：" + mRIAccumulateDefSet.get(i).getAccumulateDefNO() + " 的业务数据导入算法");
                return false;
            }
            tVData = new VData();
            tTransferData = new TransferData();
            tTransferData.setNameAndValue("EndDate",(String)mTransferData.getValueByName("EndDate"));
            tTransferData.setNameAndValue("AccumulateDefNo",mRIAccumulateDefSet.get(i).getAccumulateDefNO());
            tVData.add(tRIItemCalSet);
            tVData.add(tTransferData);
            tVData.add(globalInput);

            tRIBDataImport = new RIBDataImport();
            if (!tRIBDataImport.submitData(tVData, "")) {
                this.mErrors.copyAllErrors(tRIBDataImport.mErrors);
                return false;
            }
            tRIAccRiskCal = new RIAccRiskCal();
            if(!tRIAccRiskCal.submitData(tVData,"")){
                this.mErrors.copyAllErrors(tRIAccRiskCal.mErrors);
                return false;
            }
        }
        return true;
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();

        cError.moduleName = "ReComManageBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
