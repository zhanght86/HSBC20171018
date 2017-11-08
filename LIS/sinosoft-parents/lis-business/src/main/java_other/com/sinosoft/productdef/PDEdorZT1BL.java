

/**
 * <p>Title: PDEdorZT1</p>
 * <p>Description: 具体保全项目定义2</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-16
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import java.util.ArrayList;


public class PDEdorZT1BL {
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    private VData tResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    /** 数据操作字符串 */
    private String mOperate;
    /** 业务处理相关变量 */
    private MMap map = new MMap();
    private TransferData mTransferData = new TransferData();
    private ArrayList mList = new ArrayList();
    private ArrayList mList2 = new ArrayList();
    private PD_LMEdorZT1Schema mPD_LMEdorZT1Schema = new PD_LMEdorZT1Schema();
    public PDEdorZT1BL() {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        if (!check()) {
            return false;
        }
        if (!getInputData(cInputData)) {
            return false;
        }

        //进行业务处理
        CommonBase commonBase = new CommonBase();
        if (!commonBase.prepareSubmitData(cInputData, cOperate)) {
            this.mErrors.addOneError("PDRiskDutyRelaBL.submitData处理失败，" +
                                     commonBase.mErrors.getFirstError());
            return false;
        } else {
            this.mResult.add(commonBase.getResult());
        }
        //进行业务处理
        if (!dealData(cOperate)) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "$tableName$BL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据处理失败$tableName$BL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }
        //准备给后台的数据
        if (!prepareOutputData(cOperate)) {
            return false;
        }
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(tResult, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "ProdDefWorkFlowBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    private boolean check() {
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        mTransferData = (TransferData) cInputData.getObjectByObjectName(
                "TransferData", 0);
        mList = (ArrayList) mTransferData.getValueByName("list");
        mList2 = (ArrayList) mTransferData.getValueByName("list2");
        for (int i = 0; i < mList2.size(); i++) {
            this.mPD_LMEdorZT1Schema.setV(String.valueOf(mList2.get(i)),
                                          String.valueOf(mList.get(i)));
        }
        this.mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0);

        return true;
    }

    /**
     * 准备需要保存的数据
     * @return boolean
     */
    private boolean prepareOutputData(String cOperate) {
        try {
            VData tData = new VData();
            tData = (VData) mResult.get(0);
            MMap tmap = (MMap) tData.getObjectByObjectName("MMap", 0);
            tmap.add(map);
            tResult.add(tmap);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData(String cOperate) {
        if ("save".equals(cOperate)) {
            PD_LMEdorZTDutyDB tPD_LMEdorZTDutyDB = new PD_LMEdorZTDutyDB();
            tPD_LMEdorZTDutyDB.setRiskCode(mPD_LMEdorZT1Schema.getRiskCode());
            tPD_LMEdorZTDutyDB.setDutyCode(mPD_LMEdorZT1Schema.getDutyCode());
            if (!tPD_LMEdorZTDutyDB.getInfo()) {
                tPD_LMEdorZTDutyDB.setPayByAcc("0"); //默认不按账户退保
                tPD_LMEdorZTDutyDB.setPayCalType("1"); //默认按保额
                tPD_LMEdorZTDutyDB.setOperator(mGlobalInput.Operator);
                tPD_LMEdorZTDutyDB.setMakeDate(PubFun.getCurrentDate());
                tPD_LMEdorZTDutyDB.setMakeTime(PubFun.getCurrentTime());
                tPD_LMEdorZTDutyDB.setModifyDate(PubFun.getCurrentDate());
                tPD_LMEdorZTDutyDB.setModifyTime(PubFun.getCurrentTime());
                this.map.put(tPD_LMEdorZTDutyDB.getSchema(), "INSERT");
            }
        } else if ("del".equals(cOperate)) {
            //一般情况下，都是按照责任进行退费的，如果按照缴费进行退费的话，在责任的最后一个缴费删除时，删除PD_LMEdorZTDuty
            PD_LMEdorZT1DB tPD_LMEdorZT1DB = new PD_LMEdorZT1DB();
            tPD_LMEdorZT1DB.setRiskCode(mPD_LMEdorZT1Schema.getRiskCode());
            tPD_LMEdorZT1DB.setDutyCode(mPD_LMEdorZT1Schema.getDutyCode());
            PD_LMEdorZT1Set tPD_LMEdorZT1Set = tPD_LMEdorZT1DB.query();
            if (tPD_LMEdorZT1Set.size() == 1) {
                PD_LMEdorZTDutyDB tPD_LMEdorZTDutyDB = new PD_LMEdorZTDutyDB();
                tPD_LMEdorZTDutyDB.setRiskCode(mPD_LMEdorZT1Schema.getRiskCode());
                tPD_LMEdorZTDutyDB.setDutyCode(mPD_LMEdorZT1Schema.getDutyCode());
                this.map.put(tPD_LMEdorZTDutyDB.getSchema(), "DELETE");
            }
        }
        return true;
    }


    public static void main(String[] args) {
    }
}
