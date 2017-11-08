package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import com.sinosoft.lis.otof.*;
import com.sinosoft.lis.pubfun.*;

import com.sinosoft.utility.*;

import java.text.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 财务凭证冲销 </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class OtoFReverseUI
{
private static Logger logger = Logger.getLogger(OtoFReverseUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往前面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    //业务处理相关变量

    /** 接受前台传输数据的容器 */
    private TransferData mTransferData = new TransferData();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();

    /** 保单 */
    public OtoFReverseUI()
    {
    }

    public static void main(String[] args)
    {
    }

    /**
     * 传输数据的公共方法
    */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        //this.mOperate =cOperate;
        //得到外部传入的数据,将数据备份到本类中
        if (getInputData(cInputData) == false)
        {
            return false;
        }

        //进行业务处理
        if (!dealData())
        {
            return false;
        }

        //准备往后台的数据
        if (!prepareOutputData())
        {
            return false;
        }

        OtoFReverseBL tOtoFReverseBL = new OtoFReverseBL();

        if (!tOtoFReverseBL.submitData(cInputData, cOperate))
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tOtoFReverseBL.mErrors);

            return false;
        }

        mInputData = null;

        mResult = tOtoFReverseBL.getResult();

        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        try
        {
        }
        catch (Exception ex)
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "OtoFReverseUI";
            tError.functionName = "prepareData";
            tError.errorMessage = "在准备往后层处理所需要的数据时出错。";
            this.mErrors.addOneError(tError);

            return false;
        }

        return true;
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
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        try
        {
            logger.debug("end UI-getInputData");
        }
        catch (Exception ex)
        {
            buildError("getInputData in OtoFReverseUI : ", ex.toString());

            return false;
        }

        return true;
    }

    /**
     * 记录错误的方法
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "OtoFReverseUI";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    public VData getResult()
    {
        return mResult;
    }
}
