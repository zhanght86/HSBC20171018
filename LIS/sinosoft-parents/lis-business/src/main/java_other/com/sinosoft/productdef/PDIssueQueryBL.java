

/**
 * <p>Title: PDIssueQuery</p>
 * <p>Description: 问题件录入</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author
 * @version 1.0
 * @CreateDate：2009-4-3
 */

package com.sinosoft.productdef;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;


public class PDIssueQueryBL implements BusinessService{
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 业务处理相关变量 */
    private MMap mMap = new MMap();

    private PD_IssueSchema mPD_IssueSchema = new PD_IssueSchema();

    public PDIssueQueryBL() {
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
            tError.moduleName = "PDIssueQueryBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据提交失败!";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }

    private boolean check() {
        PD_IssueDB tPD_IssueDB = new PD_IssueDB();
        tPD_IssueDB.setSchema(this.mPD_IssueSchema);
        if (!tPD_IssueDB.getInfo()) {
            CError tError = new CError();
            tError.moduleName = "PDIssueQueryBL";
            tError.functionName = "check";
            tError.errorMessage = "回复的问题件不存在，请检查!";
            this.mErrors.addOneError(tError);
            return false;
        }
        if(tPD_IssueDB.getIssueState().equals("2")){
            CError tError = new CError();
            tError.moduleName = "PDIssueQueryBL";
            tError.functionName = "check";
            tError.errorMessage = "已回复的问题件不能再次回复，请检查!";
            this.mErrors.addOneError(tError);
            return false;
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
            mTransferData = (TransferData) cInputData.getObjectByObjectName(
                    "TransferData", 0);

            if (mGlobalInput == null || mTransferData == null) {
                mErrors.addOneError(new CError("数据传输不完全！"));
                return false;
            }

            mPD_IssueSchema.setSerialNO((String) mTransferData.
                                        getValueByName("SerialNo"));
            mPD_IssueSchema.setReplyCont((String) mTransferData.
                                         getValueByName("ReplyCont"));
            mPD_IssueSchema.setReplyMan(mGlobalInput.Operator);
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
        PD_IssueDB tPD_IssueDB = new PD_IssueDB();
        tPD_IssueDB.setSerialNO(this.mPD_IssueSchema.getSerialNO());
        tPD_IssueDB.getInfo();
        tPD_IssueDB.setReplyCont(this.mPD_IssueSchema.getReplyCont());
        tPD_IssueDB.setReplyMan(this.mPD_IssueSchema.getReplyMan());
        tPD_IssueDB.setIssueState("2");//设置为已回复
        tPD_IssueDB.setModifyDate(PubFun.getCurrentDate());
        tPD_IssueDB.setModifyTime(PubFun.getCurrentTime());
        this.mMap.put(tPD_IssueDB.getSchema(), "DELETE&INSERT");
        return true;
    }
    public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return this.mResult;
	}
    public static void main(String[] args) {
    }
}
