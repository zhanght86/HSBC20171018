package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import java.util.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.acc.*;

//import com.sinosoft.lis.acc.InsuAccRiskFeeCalBL;

//import com.sinosoft.lis.acc.InsuAccRiskFeeReCalBL;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全- 投连险账户转换回退确认生效处理类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author：ck
 * @version：1.0
 * @CreateDate：2007-07-03
 */
public class PEdorTIBackConfirmBL implements EdorBack {
private static Logger logger = Logger.getLogger(PEdorTIBackConfirmBL.class);
    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /** 数据操作字符串 */
    private String mOperate;
    /** 错误处理类 */
    public CErrors mErrors = new CErrors();
    private MMap map = new MMap();
    /** 全局基础数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    //需要回退的保全项目
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();
    private LCContStateSet mInstLCContStateSet = new LCContStateSet();
    private LCContStateSet mUpdLCContStateSet = new LCContStateSet();
    //返回管理费轨迹
    private LCInsureAccTraceSet mReLCInsureAccTraceSet = new
            LCInsureAccTraceSet(); //结算轨迹记录
    private LCInsureAccFeeTraceSet mReLCInsureAccFeeTraceSet = new
            LCInsureAccFeeTraceSet(); //管理费轨迹记录
    private String mCurrentDate = PubFun.getCurrentDate();
    private String mCurrentTime = PubFun.getCurrentTime();
    private double mUnitPriceBuy; //单位买入价格
    private double mUnitPriceSell; //单位卖出价格
    PubInsuAccFun tPubInsuAccFun = new PubInsuAccFun();
    public PEdorTIBackConfirmBL() {
    }

    /**
     * 数据提交的公共方法
     * @param cInputData 传入的数据对象
     * @param cOperate 数据操作字符串
     * @return boolean
     */
    public boolean submitData(VData cInputData, String cOperate) {
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        if (!getInputData()) {
            return false;
        }

        //进行业务处理
        if (!dealData()) {
            return false;
        }

        //准备往后台的数据
        if (!prepareData()) {
            return false;
        }

        return true;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @return boolean
     */
    private boolean getInputData() {
        try {
            mGlobalInput =
                    (GlobalInput)
                    mInputData.getObjectByObjectName("GlobalInput", 0);
            mLPEdorItemSchema = //需要回退的保全项目
                    (LPEdorItemSchema)
                    mInputData.getObjectByObjectName("LPEdorItemSchema", 0);
        } catch (Exception e) {
            e.printStackTrace();
            CError.buildErr(this, "接收前台数据失败!");
            return false;
        }
        if (mGlobalInput == null || mLPEdorItemSchema == null) {
            CError.buildErr(this, "传入数据有误!");
            return false;
        }

        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return boolean
     */
    private boolean dealData() {
        logger.debug("=== 账户转换回退确认生效 ===");
        EdorInsuAccRollBack tEdorInsuAccRollBack = new EdorInsuAccRollBack(
                mGlobalInput);

        if(!tEdorInsuAccRollBack.rollBackEdorInsuAcc(mLPEdorItemSchema))
        {
            CError.buildErr(this, "保全账户转换差错处理出错!");
            return false;
        }

        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * @return boolean
     */
    private boolean prepareData() {
        mResult.clear();
        mResult.add(map);
        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return VData
     */
    public VData getResult() {
        return mResult;
    }

    public CErrors getErrors() {
        return null;
    }

}
