package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.service.BusinessService;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 业务数据转换到银行系统</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class RenewBankManageUI  implements BusinessService{
private static Logger logger = Logger.getLogger(RenewBankManageUI.class);
    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();

    public RenewBankManageUI() {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        // 数据操作字符串拷贝到本类中
        this.mInputData = (VData)cInputData.clone();
        this.mOperate = cOperate;

        logger.debug("---RenewBankManage BL BEGIN---");
        RenewBankManageBL tRenewBankManageBL = new RenewBankManageBL();
        if(tRenewBankManageBL.submitData(cInputData, cOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tRenewBankManageBL.mErrors);
            mResult.clear();
            mResult.add(mErrors.getFirstError());
            return false;
        }
        logger.debug("---RenewBankManage BL END---");

        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult() {
        return mResult;
    }
    public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}
    public static void main(String[] args) {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.Operator = "001";
        tGlobalInput.ComCode = "8611";

        VData tVData = new VData();
        tVData.add(tGlobalInput);

        RenewBankManageUI RenewBankManageUI1 = new RenewBankManageUI();
        if (!RenewBankManageUI1.submitData(tVData, "GETMONEY")) {
            VData rVData = RenewBankManageUI1.getResult();
            logger.debug("Submit Failed! " + (String)rVData.get(0));
        }
        else logger.debug("Submit Succed!");
    }
}
