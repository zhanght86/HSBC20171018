package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.tb.*;
import java.util.*;

/**
 * <p>Title: 保全项目投连万能复缴明细</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */

public class PEdorTSDetailBLF
        implements EdorDetail
{
private static Logger logger = Logger.getLogger(PEdorTSDetailBLF.class);


    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    /** 全局数据 */
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private GlobalInput mGlobalInput = new GlobalInput();
    private LCCustomerImpartSet mLCCustomerImpartSet = new LCCustomerImpartSet();
    private MMap mMap = new MMap();

    public VData getResult()
    {
        return mResult;
    }

    /**
     * 数据提交的公共方法
     * @param: cInputData 传入的数据
     *		  cOperate 数据操作字符串
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
        {
            return false;
        }

        //数据检测
        if (!checkData())
        {
            return false;
        }

        //数据准备操作
        if (!dealData())
        {
            return false;
        }

        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mResult, ""))
        {
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

    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData()
    {
        try
        {
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData
                                .getObjectByObjectName("LPEdorItemSchema", 0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
            mLCCustomerImpartSet = (LCCustomerImpartSet) mInputData.
                                   getObjectByObjectName(
                                           "LCCustomerImpartSet", 0);
        }
        catch (Exception e)
        {
            CError.buildErr(this, "接收数据失败");
            return false;
        }
        return true;
    }

    /**
     * 数据检测
     * @return boolean
     */
    private boolean checkData()
    {
        LPEdorItemDB tLPEdorItemDB = new LPEdorItemDB();
        tLPEdorItemDB.setSchema(mLPEdorItemSchema);
        if (!tLPEdorItemDB.getInfo())
        {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "PEdorTSDetailBLF";
            tError.functionName = "checkData";
            tError.errorMessage = "无保全申请数据!";
            logger.debug("------" + tError);
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * 处理数据
     * @return boolean
     */
    private boolean dealData()
    {
        PEdorTSDetailBL tPEdorTSDetailBL = new PEdorTSDetailBL();
        if (!tPEdorTSDetailBL.submitData(mInputData, mOperate))
        {
            this.mErrors.copyAllErrors(tPEdorTSDetailBL.mErrors);
            return false;
        }
        mResult.clear();
        mResult = tPEdorTSDetailBL.getResult();
        return true;
    }

    public PEdorTSDetailBLF()
    {
    }

    public CErrors getErrors()
    {
        return mErrors;
    }

}
