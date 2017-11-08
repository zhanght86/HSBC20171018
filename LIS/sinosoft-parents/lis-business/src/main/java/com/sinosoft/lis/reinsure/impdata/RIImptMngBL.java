

package com.sinosoft.lis.reinsure.impdata;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.CError;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CErrors;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2008</p>
 * <p>Company: sinosoft </p>
 * @ zhangbin
 * @version 1.0
 */

public class RIImptMngBL {
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 前台传入的公共变量 */
    private GlobalInput globalInput = new GlobalInput();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();
    /**返回前台的信息*/
    private VData mResult = new VData();
    /**操作类型*/
    private String mOperate = "";

    public RIImptMngBL() {
    }

    /**
     * 提交数据处理方法
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate) {
        this.mOperate = cOperate;
        if (mOperate.equals("")) {
            buildError("verifyOperate", "不支持的操作字符串");
            return false;
        }
        if (!getInputData(cInputData))return false;

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
        this.mInputData = (VData)cInputData.clone();
        if(this.globalInput==null){
            buildError("getInputData","前台传输用户信息数据失败");
            return false;
        }
        if(mInputData==null){
            buildError("getInputData","前台传输全局公共数据失败");
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
        if(mOperate.equals("21")){    //业务数据导入
            RIBDataImpl tRIBDataImpl = new RIBDataImpl();
            if(!tRIBDataImpl.submitData(mInputData,"")){
                this.mErrors.copyAllErrors(tRIBDataImpl.mErrors);
                return false;
            }
        }else if(mOperate.equals("22")){//分保数据导入
            RIRDataImpl tRIRDataImpl = new RIRDataImpl();
            if(!tRIRDataImpl.submitData(mInputData,"")){
                this.mErrors.copyAllErrors(tRIRDataImpl.mErrors);
                return false;
            }
        }else if(mOperate.equals("31")){//业务数据删除
        	RIBClearData tRIBClearData = new RIBClearData();
            if(!tRIBClearData.submitData(mInputData,"")){
                this.mErrors.copyAllErrors(tRIBClearData.mErrors);
                return false;
            }
        }else if(mOperate.equals("32")){//分保数据产出
        	RIRClearData tRIRClearData = new RIRClearData();
            if(!tRIRClearData.submitData(mInputData,"")){
                this.mErrors.copyAllErrors(tRIRClearData.mErrors);
                return false;
            }
        }else{
            buildError("dealData", "非法操作类型");
            return false;
        }

        return true;
    }
    /**
     * 获取后台返回的信息
     * @return VData
     */
    public VData getResult() {
        return mResult;
    }

    /*
     * add by kevin, 2002-10-14
     */
    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "RIImptMngBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
