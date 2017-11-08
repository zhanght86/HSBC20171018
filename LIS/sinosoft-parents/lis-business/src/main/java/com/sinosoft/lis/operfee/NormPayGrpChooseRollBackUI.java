package com.sinosoft.lis.operfee;
import org.apache.log4j.Logger;

import com.sinosoft.lis.operfee.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:团单不定期续期核销回滚（界面输入）
 * </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author JL
 * @version 1.0
 */
public class NormPayGrpChooseRollBackUI
{
private static Logger logger = Logger.getLogger(NormPayGrpChooseRollBackUI.class);

    //业务处理相关变量
    private VData mInputData;
    private VData mResult = new VData();
    public CErrors mErrors = new CErrors();

    public NormPayGrpChooseRollBackUI()
    {
    }

    public VData getResult()
    {
        return this.mResult;
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        //首先将数据在本类中做一个备份
        mInputData = (VData) cInputData.clone();

        NormPayGrpChooseRollBackBL tNormPayGrpChooseRollBackBL = new NormPayGrpChooseRollBackBL();
        //如果有需要处理的错误，则返回
        if (!tNormPayGrpChooseRollBackBL.submitData(mInputData, cOperate))
        {
            this.mErrors.copyAllErrors(tNormPayGrpChooseRollBackBL.mErrors);
            return false;
        }
        if (cOperate.equals("QUERYTB") || cOperate.equals("QUERYCA"))
        {
            mResult = tNormPayGrpChooseRollBackBL.getResult();
        }
        return true;
    }
}
