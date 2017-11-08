

/**
 * <p>Title: PDEdorZTDutyBL</p>
 * <p>Description: 算法模板库</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-3-17
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.sys.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class PDEdorZTDutyBL {
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    private VData mResult = new VData();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 数据操作字符串 */
    private String mOperate;

    /** 业务处理相关变量 */
    private MMap map = new MMap();

    public PDEdorZTDutyBL() {
    }

    /**
     * 传输数据的公共方法
     *
     * @param: cInputData 输入的数据 cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        try {
            if (!check()) {
                return false;
            }

            // 进行业务处理
            if (!deal(cInputData, cOperate)) {
                return false;
            }
        } catch (Exception ex) {
            this.mErrors.addOneError(ex.getMessage());
            return false;
        }
        return true;
    }

    private boolean deal(VData cInputData, String cOperate) {
        TransferData transferData = (TransferData) cInputData
                                    .getObjectByObjectName("TransferData", 0);
        PD_LMEdorZTDutySchema tPD_LMEdorZTDutySchema = (PD_LMEdorZTDutySchema)
                transferData.getValueByName("PD_LMEdorZTDutySchema");

        GlobalInput tG = (GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0);

        PD_LMEdorZTDutyDB tPD_LMEdorZTDutyDB = new PD_LMEdorZTDutyDB();
        tPD_LMEdorZTDutyDB.setSchema(tPD_LMEdorZTDutySchema);

        if (cOperate.equals("update")) {
            if (!tPD_LMEdorZTDutyDB.getInfo()) {
                this.mErrors.addOneError("数据中不存在该记录，请先插入再修改");
                return false;
            }

            PD_LMEdorZTDutySchema oldPD_LMEdorZTDutySchema = tPD_LMEdorZTDutyDB
                    .getSchema();

            tPD_LMEdorZTDutySchema.setModifyDate(PubFun.getCurrentDate());
            tPD_LMEdorZTDutySchema.setModifyTime(PubFun.getCurrentTime());
            tPD_LMEdorZTDutySchema.setMakeDate(oldPD_LMEdorZTDutySchema
                                               .getMakeDate());
            tPD_LMEdorZTDutySchema.setMakeTime(oldPD_LMEdorZTDutySchema
                                               .getMakeTime());
            tPD_LMEdorZTDutySchema.setOperator(oldPD_LMEdorZTDutySchema
                                               .getOperator());

            tPD_LMEdorZTDutyDB.setSchema(tPD_LMEdorZTDutySchema);

            if (!tPD_LMEdorZTDutyDB.update()) {
                this.mErrors.addOneError(tPD_LMEdorZTDutyDB.mErrors
                                         .getFirstError());
                return false;
            }
        }

        return true;
    }

    private boolean check() {
        return true;
    }

    public static void main(String[] args) {
    }
}
