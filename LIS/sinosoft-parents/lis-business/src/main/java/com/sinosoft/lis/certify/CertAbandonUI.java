package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.utility.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.pubfun.*;
import java.util.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description:单证管理废除、遗失操作（界面输入）
 * </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author wentao
 * @version 1.0
 */
public class CertAbandonUI
{
private static Logger logger = Logger.getLogger(CertAbandonUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public  CErrors mErrors = new CErrors();
    /** 数据操作字符串 */
    private String mOperate;

    public CertAbandonUI() {}

    public static void main(String[] args)
    {
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        try
        {
            mOperate = cOperate;

            //得到外部传入的数据,将数据备份到本类中
            if (!getInputData(cInputData))
                return false;

            //进行业务处理
            if (!dealData())
                return false;

            VData vData = (VData) cInputData;
            CertAbandonBL tCertAbandonBL = new CertAbandonBL();
            boolean bReturn = tCertAbandonBL.submitData(vData, mOperate);
            if( !bReturn )
            {
                if( tCertAbandonBL.mErrors.needDealError() )
                    mErrors.copyAllErrors(tCertAbandonBL.mErrors);
                else
                    buildError("submitData", "CertReveTakeBackBL发生错误，但是没有提供详细的信息");
            }

            return bReturn;
        } catch(Exception ex)
        {
            ex.printStackTrace( );
            buildError("submitData", ex.getMessage());
            return false;
        }
    }

    /**
     * 根据前面的输入数据，进行UI逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        return true;
    }

    /**
     * 从输入数据中得到所有对象
     *输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        return true;
    }

    /**
     * Build Error
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );
        cError.moduleName = "CertAbandonUI";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
}
