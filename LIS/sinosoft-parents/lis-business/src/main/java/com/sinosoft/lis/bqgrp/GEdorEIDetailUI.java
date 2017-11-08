package com.sinosoft.lis.bqgrp;
import org.apache.log4j.Logger;

import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 业务系统团体延长保险期间功能部分</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @version 1.0
 */

public class GEdorEIDetailUI implements BusinessService
{
private static Logger logger = Logger.getLogger(GEdorEIDetailUI.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData = new VData();

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    public GEdorEIDetailUI()
    {
    }

    /**
     * 传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mOperate = cOperate;
        mResult.clear();

        logger.debug("---PEdorEIDetailUI BEGIN---" + mOperate);

        GEdorEIDetailBL tGEdorEIDetailBL = new GEdorEIDetailBL();
        if (!tGEdorEIDetailBL.submitData(cInputData, mOperate))
        {
            // @@错误处理
            CError.buildErr(this, "提交 PEdorGADetailBL 后台失败!",
                tGEdorEIDetailBL.mErrors);
            return false;
        }

        mResult = tGEdorEIDetailBL.getResult();
        mResult.clear();
        return true;
    }

    /*
     * 获取BL层返回结果
     */
    public VData getResult()
    {
        return mResult;
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}
