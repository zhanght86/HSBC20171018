

/**
 * <p>Title: PDBaseField</p>
 * <p>Description: 基础信息字段描述</p>
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


public class PDBaseFieldBL {
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mInTransferData = new TransferData();
    private PD_BaseTableSchema mPD_BaseTableSchema = new PD_BaseTableSchema();
    private PD_BaseFieldSchema mPD_BaseFieldSchema = new PD_BaseFieldSchema();
    /** 数据操作字符串 */
    private String mOperate;
    /** 业务处理相关变量 */
    private MMap mMap = new MMap();

    public PDBaseFieldBL() {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate) {
        this.mOperate = cOperate;
        // 得到外部传入的数据，将数据备份到本类中
        if (!getInputData(cInputData)) {
            return false;
        }
        if (!check()) {
            return false;
        }
        // 准备所有要打印的数据
        if (!dealData()) {
            return false;
        }
        mResult.clear();
        mResult.addElement(mMap);
        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, "")) {
            // @@错误处理
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "PEdorICDetailBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }

        return true;
    }

    private boolean check() {
        if (!mOperate.equals("update")) {
            this.mErrors.addOneError("基础字段信息描述只允许进行修改！");
        }
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData) {
        //全局变量
        try {
            System.out.println("here");
            mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName(
                    "GlobalInput", 0);
            mInTransferData = (TransferData) cInputData.getObjectByObjectName(
                    "TransferData", 0);

            if (mGlobalInput == null || mInTransferData == null) {
                mErrors.addOneError(new CError("数据传输不完全！"));
                return false;
            }

            mPD_BaseTableSchema.setTableCode((String) mInTransferData.
                                             getValueByName("TableCode"));
            mPD_BaseTableSchema.setTableName((String) mInTransferData.
                                             getValueByName("TableName"));

            mPD_BaseFieldSchema.setTableCode(mPD_BaseTableSchema.getTableCode());
            mPD_BaseFieldSchema.setFieldCode((String) mInTransferData.
                                             getValueByName("FieldCode"));
            mPD_BaseFieldSchema.setFieldName((String) mInTransferData.
                                             getValueByName("FieldName"));
            mPD_BaseFieldSchema.setFieldType((String) mInTransferData.
                                             getValueByName("FieldType"));
            mPD_BaseFieldSchema.setFieldTypeName((String) mInTransferData.
                    getValueByName("FieldTypeName"));           
            mPD_BaseFieldSchema.setIsDisplay((String) mInTransferData.
                                             getValueByName("IsDisplayCode"));
            mPD_BaseFieldSchema.setBusiDesc((String) mInTransferData.
                                            getValueByName("BusiDesc"));
            mPD_BaseFieldSchema.setOfficialDesc((String) mInTransferData.
                                                getValueByName("OfficialDesc"));
        } catch (Exception e) {
            e.printStackTrace();
            CError.buildErr(this, "接收前台数据失败!");
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        PD_BaseTableDB tPD_BaseTableDB = new PD_BaseTableDB();
        tPD_BaseTableDB.setTableCode(mPD_BaseTableSchema.getTableCode());
        if(tPD_BaseTableDB.getInfo()){
            if(!tPD_BaseTableDB.getTableName().equals(mPD_BaseTableSchema.getTableName())){
                tPD_BaseTableDB.setTableName(mPD_BaseTableSchema.getTableName());
                tPD_BaseTableDB.setModifyDate(PubFun.getCurrentDate());
                tPD_BaseTableDB.setModifyTime(PubFun.getCurrentTime());
                this.mMap.put(tPD_BaseTableDB.getSchema(),"UPDATE");
            }
        }
        PD_BaseFieldDB tPD_BaseFieldDB = new PD_BaseFieldDB();
        tPD_BaseFieldDB.setTableCode(mPD_BaseFieldSchema.getTableCode());
        tPD_BaseFieldDB.setFieldCode(mPD_BaseFieldSchema.getFieldCode());
        if(tPD_BaseFieldDB.getInfo()){
            tPD_BaseFieldDB.setFieldName(mPD_BaseFieldSchema.getFieldName());
            tPD_BaseFieldDB.setFieldType(mPD_BaseFieldSchema.getFieldType());
            tPD_BaseFieldDB.setFieldTypeName(mPD_BaseFieldSchema.getFieldTypeName());
            tPD_BaseFieldDB.setIsDisplay(mPD_BaseFieldSchema.getIsDisplay());
            tPD_BaseFieldDB.setOfficialDesc(mPD_BaseFieldSchema.getOfficialDesc());
            tPD_BaseFieldDB.setBusiDesc(mPD_BaseFieldSchema.getBusiDesc());
            tPD_BaseFieldDB.setModifyDate(PubFun.getCurrentDate());
            tPD_BaseFieldDB.setModifyTime(PubFun.getCurrentTime());
            this.mMap.put(tPD_BaseFieldDB.getSchema(),"UPDATE");
        }
        return true;
    }


    public static void main(String[] args) {
        PDBaseFieldBL pDBaseFieldBL = new PDBaseFieldBL();
        TransferData transferData = new TransferData();
        GlobalInput tG = new GlobalInput();

        transferData.setNameAndValue("TableCode","PD_LMACCGURATRATE");
        transferData.setNameAndValue("TableName","帐户保证利率公布表");
        transferData.setNameAndValue("FieldCode","RATESTARTDATE");
        transferData.setNameAndValue("FieldName","起始日期3");
        transferData.setNameAndValue("FieldType","DATE");
        transferData.setNameAndValue("IsDisplayCode","1");
        transferData.setNameAndValue("OfficialDesc","fefe");
        transferData.setNameAndValue("BusiDesc", "444");

        VData tVData = new VData();
        tVData.add(tG);
        tVData.add(transferData);
        pDBaseFieldBL.submitData(tVData,"update");


    }
}
