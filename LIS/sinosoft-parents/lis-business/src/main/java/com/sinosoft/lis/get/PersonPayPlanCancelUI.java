package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:生存领取催付数据撤消功能类(个险)
 * </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class PersonPayPlanCancelUI  implements BusinessService
{
private static Logger logger = Logger.getLogger(PersonPayPlanCancelUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    public PersonPayPlanCancelUI()
    {
    }

    /**
    传输数据的公共方法
    */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;

        logger.debug("==> Begin PersonPayPlanCancelBL");
        PersonPayPlanCancelBL tPersonPayPlanCancelBL = new PersonPayPlanCancelBL();
        if (!tPersonPayPlanCancelBL.submitData(cInputData, mOperate))
        {
            // @@错误处理
            CError.buildErr(this,"数据撤销失败!",tPersonPayPlanCancelBL.mErrors);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }
    public CErrors getErrors()
    {
        return mErrors;
    }
}
