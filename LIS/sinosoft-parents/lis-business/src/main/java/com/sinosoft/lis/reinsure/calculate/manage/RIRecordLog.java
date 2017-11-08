

package com.sinosoft.lis.reinsure.calculate.manage;

import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.RIWFLogSchema;
import com.sinosoft.lis.db.RIWFLogDB;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft</p>
 * @zhangbin
 * @version 1.0
 */
public class RIRecordLog {
    public RIRecordLog() {
    }
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    private MMap mMap = new MMap();
    private PubSubmit mPubSubmit = new PubSubmit();
    private RIWFLogSchema mRIWFLogSchema ;
    /** 数据操作字符串 */
    private String mOperate;

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     * cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
            return false;
        //进行业务处理
        if (!dealData()) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "BL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败BL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if (!prepareOutputData()) {
            return false;
        }
        if (!submitData()) {
            return false;
        }
        mInputData = null;
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        if(mOperate.equals("01")){
            if(!updateLog()){
                return false;
            }
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean updateLog(){
        String nodeState = mRIWFLogSchema.getNodeState();
        RIWFLogDB tRIWFLogDB = new RIWFLogDB();
        tRIWFLogDB.setBatchNo(mRIWFLogSchema.getBatchNo());
        tRIWFLogDB.setTaskCode(mRIWFLogSchema.getTaskCode());
        if(!tRIWFLogDB.getInfo()){
            buildError("BL", "更新日志工作节点状态时出现错误:没有找到原节点日志记录");
            return false;
        }
        mRIWFLogSchema.setSchema(tRIWFLogDB.getSchema());

        mRIWFLogSchema.setNodeState(nodeState);
        mRIWFLogSchema.setMakeDate(PubFun.getCurrentDate());
        mRIWFLogSchema.setMakeTime(PubFun.getCurrentTime());
        mMap.put(mRIWFLogSchema,"UPDATE");
        mResult.clear();
        mResult.add(mRIWFLogSchema);
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        this.mRIWFLogSchema = (RIWFLogSchema) cInputData.getObjectByObjectName("RIWFLogSchema", 0);
        return true;
    }

    private boolean prepareOutputData() {
        try {
            this.mInputData.clear();
            mInputData.add(mMap);
        } catch (Exception ex) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "BL";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 向后台提交数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean submitData() {
        try
        {
            if (!mPubSubmit.submitData(this.mInputData, "")) {
                if (mPubSubmit.mErrors.needDealError()) {
                    buildError("BL", "更新日志工作节点状态时出现错误:"+mPubSubmit.mErrors.getFirstError());
                    return false;
                }
            }
            mMap = null;
        }
        catch (Exception ex)
        {
            CError tError = new CError();
            tError.moduleName = "BL";
            tError.functionName = "saveResult";
            tError.errorMessage = "更新日志工作节点状态时出现错误："+ex.getMessage();
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }

    public VData getResult() {
        return this.mResult;
    }

    public static void main(String[] args) {
        RIRecordLog tRIRecordLog = new RIRecordLog();
        VData tVData = new VData();
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ManageCom="86";
        tGlobalInput.Operator = "001";
        tGlobalInput.ComCode = "86";

        RIWFLogSchema mRIWFLogSchema = new RIWFLogSchema();
        mRIWFLogSchema.setBatchNo("200801");
        mRIWFLogSchema.setTaskCode("L001000001");
        mRIWFLogSchema.setNodeState("08");
        mRIWFLogSchema.setStartDate("2008-1-1");
        mRIWFLogSchema.setEndDate("2008-3-1");
        mRIWFLogSchema.setOperator("001");
        mRIWFLogSchema.setManageCom("86");
        mRIWFLogSchema.setMakeDate(PubFun.getCurrentDate());
        mRIWFLogSchema.setMakeTime(PubFun.getCurrentTime());
        mRIWFLogSchema.setModifyDate(PubFun.getCurrentDate());
        mRIWFLogSchema.setModifyTime(PubFun.getCurrentTime());

        tVData.add(mRIWFLogSchema);
        tVData.add(tGlobalInput);
        tRIRecordLog.submitData(tVData,"01");

    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "CalItemDeal";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
        System.out.print(szErrMsg);
    }

}
