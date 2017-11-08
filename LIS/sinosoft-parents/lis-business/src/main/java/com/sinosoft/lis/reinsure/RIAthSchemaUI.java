

package com.sinosoft.lis.reinsure;
/**
 * <p>ClassName: RIAthSchemaUI.java </p>
 * <p>Description: 方案算法定义 </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: Sinosoft </p>
 * @Database:
 * @CreateDate：2011/6/16
 */

//包名
//package com.sinosoft.lis.health;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

public class RIAthSchemaUI {

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;
    //业务处理相关变量
    
    public RIAthSchemaUI() {

    }

    /**
     * 传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate) {
       // 将操作数据拷贝到本类中
       this.mOperate = cOperate;
       RIAthSchemaBL tRIAthSchemaBL = new RIAthSchemaBL();
       tRIAthSchemaBL.submitData(cInputData, mOperate);
       // 如果有需要处理的错误，则返回
       if (tRIAthSchemaBL.mErrors.needDealError()) {
           // @@错误处理
           this.mErrors.copyAllErrors(tRIAthSchemaBL.mErrors);
           CError tError = new CError();
           tError.moduleName = "RIAthSchemaUI";
           tError.functionName = "submitData";
           tError.errorMessage = "数据提交失败!";
           this.mErrors.addOneError(tError);
           return false;
       }

       if (mOperate.equals("INSERT||MAIN")) {
           this.mResult.clear();
           this.mResult = tRIAthSchemaBL.getResult();
       }
       return true;
    }

    public static void main(String[] args) {

    }
    
    public VData getResult() {
       return this.mResult;
    }
}
