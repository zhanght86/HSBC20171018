

package com.sinosoft.lis.reinsure.impdata;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class RIRClearData {
	/** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    /** 前台传入的公共变量 */
    private GlobalInput globalInput = new GlobalInput();
    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    public RIRClearData() {
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
        this.mInputData = (VData) cInputData.clone();
        if (mInputData == null) {
            buildError("getInputData", "前台传输全局公共数据失败");
            return false;
        }
        this.globalInput.setSchema((GlobalInput) mInputData.getObjectByObjectName("GlobalInput", 0));
        if (this.globalInput == null) {
            buildError("getInputData", "前台传输用户信息数据失败");
            return false;
        }
        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData() {
    	try{
	    	String sql = " delete from rirecordtrace a where a.batchno='0000000000' ";
	        ExeSQL tExeSQL = new ExeSQL();
	        if(!tExeSQL.execUpdateSQL(sql)){
	        	buildError("dealData", "删除分保数据失败 ");
	        	return false;
	        }
    	}catch(Exception ex){
    		buildError("dealData", "删除分保数据失败 "+ex.getMessage());
    	}
        return true;
    }

    private void buildError(String szFunc, String szErrMsg) {
        CError cError = new CError();
        cError.moduleName = "RIRClearData";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
    
    public CErrors getCErrors() {
        return this.mErrors;
    }
}
