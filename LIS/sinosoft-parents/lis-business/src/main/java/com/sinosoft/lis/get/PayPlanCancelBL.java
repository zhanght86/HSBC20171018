package com.sinosoft.lis.get;
import org.apache.log4j.Logger;

import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.service.BusinessService;
import com.sinosoft.utility.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 生存领取催付数据撤消功能类</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author JL
 * @version 1.0
 */
public class PayPlanCancelBL implements BusinessService
{
private static Logger logger = Logger.getLogger(PayPlanCancelBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;

    /** 往界面传输数据的容器 */
    private VData mResult = new VData();

    /** 数据操作字符串 */
    private String mOperate;

    /** 全局变量 */
    private LJSGetSchema mLJSGetSchema = new LJSGetSchema();
    private LJSGetDrawSchema mLJSGetDrawSchema = new LJSGetDrawSchema();
    private LJSGetDrawSet mLJSGetDrawSet = new LJSGetDrawSet();
    private LCLFGetCancelLogSchema mLCLFGetCancelLogSchema = new LCLFGetCancelLogSchema();

    /** 公共参数 */
    private GlobalInput mGlobalInput = new GlobalInput();

    public PayPlanCancelBL()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        this.mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData(cInputData))
        {
            return false;
        }
        logger.debug("==> End getInputData");

        if (!checkData())
        {
            return false;
        }
        logger.debug("==> End checkData");

        if (!dealData())
        {
            return false;
        }
        logger.debug("==> End dealData");

        if (!prepareData())
        {
            return false;
        }

        PubSubmit tSubmit = new PubSubmit();
        if (!tSubmit.submitData(mInputData, "")) {
        	CError.buildErr(this,"催付撤消失败!");
        	return false;
        }
        

        return true;
    }

    /**
     * 从输入数据中得到所有对象
     * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
     */
    private boolean getInputData(VData cInputData)
    {
        // 查询条件
        if (mOperate.equals("DELETE||LFGET"))
        {
            mLJSGetSchema.setSchema((LJSGetSchema) cInputData
                .getObjectByObjectName("LJSGetSchema", 0));
            mLCLFGetCancelLogSchema.setSchema((LCLFGetCancelLogSchema)cInputData
                .getObjectByObjectName("LCLFGetCancelLogSchema", 0));
            mGlobalInput = (GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                    0);
        }
        else
        {
            CError.buildErr(this, "不支持该删除动作");
            return false;
        }
        return true;
    }

    /**
     *检查数据的合法性
    **/
    private boolean checkData()
    {
        //校验基本操作数据
        LJSGetDB tLJSGetDB = new LJSGetDB();
        tLJSGetDB.setGetNoticeNo(mLJSGetSchema.getGetNoticeNo());
        if (!tLJSGetDB.getInfo())
        {
            CError.buildErr(this, "催付总表查询失败!");
            return false;
        }
        mLJSGetSchema.setSchema(tLJSGetDB.getSchema());

        LJSGetDrawDB tLJSGetDrawDB = new LJSGetDrawDB();
        tLJSGetDrawDB.setGetNoticeNo(mLJSGetSchema.getGetNoticeNo());
        mLJSGetDrawSet = tLJSGetDrawDB.query();
        if (mLJSGetDrawSet.size() <= 0)
        {
            CError.buildErr(this, "催付子表查询失败!");
            return false;
        }

        //若对一张保单有多次催付记录，不能撤消中间的催付数据
        String strSQL = "select * from LJSGet where otherno='?otherno?' and othernotype='2' order by getdate desc";
        SQLwithBindVariables sqlbv=new SQLwithBindVariables();
        sqlbv.sql(strSQL);
        sqlbv.put("otherno", mLJSGetSchema.getOtherNo());
        tLJSGetDB = new LJSGetDB();
        LJSGetSet tLJSGetSet = tLJSGetDB.executeQuery(sqlbv);
        if (tLJSGetSet.get(1).getGetDate().compareTo(mLJSGetSchema.getGetDate()) > 0)
        {
            //若还有比当前催付记录晚的催付记录,则不能撤消当前记录
            CError.buildErr(this, "请先从保单号"+mLJSGetSchema.getOtherNo()+"最近的催付记录开始顺序删除!");
            return false;
        }

        return true;
    }

    /**
     * 处理业务数据数据
     */
    private boolean dealData()
    {
        //设置撤消日志表
        String tSerialNo = PubFun1.CreateMaxNo("SERIALNO",
                PubFun.getNoLimit(mGlobalInput.ManageCom));
        logger.debug("==> serialNo:" + tSerialNo);

        mLCLFGetCancelLogSchema.setSerialNo(tSerialNo);
        mLCLFGetCancelLogSchema.setContNo(mLJSGetSchema.getOtherNo());
        mLCLFGetCancelLogSchema.setPolNo("000000");
        mLCLFGetCancelLogSchema.setGetDutyCode(mLJSGetDrawSet.get(1).getGetDutyCode());
        mLCLFGetCancelLogSchema.setOldGetDate(mLJSGetSchema.getGetDate());
        mLCLFGetCancelLogSchema.setOldGetMoney(mLJSGetSchema.getSumGetMoney());
        mLCLFGetCancelLogSchema.setOldOperator(mLJSGetSchema.getOperator());
        mLCLFGetCancelLogSchema.setOldMakeDate(mLJSGetSchema.getMakeDate());
        mLCLFGetCancelLogSchema.setOldMakeTime(mLJSGetSchema.getMakeTime());
        mLCLFGetCancelLogSchema.setManageCom(mGlobalInput.ManageCom);
        mLCLFGetCancelLogSchema.setOperator(mGlobalInput.Operator);
        mLCLFGetCancelLogSchema.setMakeDate(PubFun.getCurrentDate());
        mLCLFGetCancelLogSchema.setMakeTime(PubFun.getCurrentTime());

        return true;
    }

    /**
     * 准备需要保存的数据
     */
    private boolean prepareData()
    {
        MMap map  = new MMap();
        map.put(mLJSGetSchema,"DELETE");
        map.put(mLJSGetDrawSet,"DELETE");
        map.put(mLCLFGetCancelLogSchema,"DELETE&INSERT");

        mInputData =  new VData();
        mInputData.add(map);

        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }
    public CErrors getErrors()
    {
        return mErrors;
    }

    
    public static void main(String[] args)
    {
        PayPlanCancelBL tPPayPlanCancelBL = new PayPlanCancelBL();
    }
}
