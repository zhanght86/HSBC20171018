/*
 * @(#)GrpEdorRECancelBL.java      Jun 14, 2006
 *
 * Copyright 2006 Sinosoft Co. Ltd. All rights reserved.
 *  All right reserved.
 */
package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.schema.LPGrpEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author：lizhuo
 * @CreateDate: Jun 14, 2006
 * @version：1.0
 */
public class GrpEdorRECancelBL implements EdorCancel {
private static Logger logger = Logger.getLogger(GrpEdorRECancelBL.class);
    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private MMap mMap = new MMap();
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    /** 全局基础数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LPGrpEdorItemSchema mLPGrpEdorItemSchema = new LPGrpEdorItemSchema();

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();

        // 得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
            return false;
        }

        // 进行业务处理
        if (!dealData()) {
            return false;
        }

        // 准备往后台的数据
        if (!prepareData()) {
            return false;
        }
        return true;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData() {
        try {
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
            mLPGrpEdorItemSchema = (LPGrpEdorItemSchema) mInputData
                                   .getObjectByObjectName("LPGrpEdorItemSchema",
                    0);
        } catch (Exception e) {
            // @@错误处理
            e.printStackTrace();
            CError tError = new CError();
            tError.moduleName = "GrpEdorRECancelBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors.addOneError(tError);
            return false;
        }

        if (mGlobalInput == null || mLPGrpEdorItemSchema == null) {
            CError tError = new CError();
            tError.moduleName = "GrpEdorRECancelBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "输入数据有误!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        mMap.put("delete from ljspayperson where grpcontno = '" +
                 mLPGrpEdorItemSchema.getGrpContNo() + "' and paytype = '" +
                 mLPGrpEdorItemSchema.getEdorType() + "' and edorno='" +
                 mLPGrpEdorItemSchema.getEdorNo() + "'", "DELETE");
        return true;
    }

    private boolean prepareData() {
        mResult.clear();
        mResult.add(mMap);
        return true;
    }

    public VData getResult() {
        return mResult;
    }

    public CErrors getErrors() {
        return mErrors;
    }

}
