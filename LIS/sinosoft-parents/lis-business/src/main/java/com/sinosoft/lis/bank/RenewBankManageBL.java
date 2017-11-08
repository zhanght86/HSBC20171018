package com.sinosoft.lis.bank;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.LYRenewBankInfoDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.LYRenewBankInfoSchema;
import com.sinosoft.lis.vschema.LYRenewBankInfoSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 业务数据转换到银行系统</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author Minim
 * @version 1.0
 */

public class RenewBankManageBL
{
private static Logger logger = Logger.getLogger(RenewBankManageBL.class);

    /** 传入数据的容器 */
    private VData mInputData = new VData();
    /** 传出数据的容器 */
    private VData mResult = new VData();
    /** 错误处理类 */
    public  CErrors mErrors = new CErrors();

    //业务数据
    private GlobalInput mGlobalInput = new GlobalInput();
    private LYRenewBankInfoSet inLYRenewBankInfoSet = new LYRenewBankInfoSet();

    /**数据提交**/
    MMap map = new MMap();

    public RenewBankManageBL() {
    }

    /**
     * 数据提交的公共方法，提交成功后将返回结果保存入内部VData对象中
     * @param cInputData 传入的数据,VData对象
     * @param cOperate 数据操作字符串，主要包括"GETMONEY"和"PAYMONEY"
     * @return 布尔值（true--提交成功, false--提交失败）
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //将操作数据拷贝到本类中
        this.mInputData = (VData)cInputData.clone();

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData())
            return false;
        logger.debug("---End getInputData---");

        //进行业务处理
        if (!dealData(cOperate))
            return false;
        logger.debug("---End dealData---");

        if(!prepareOutputData())
            return false;

        //如果前面都执行成功后进行数据的提交
        PubSubmit pubsubmit = new PubSubmit();
        if (!pubsubmit.submitData(mInputData, cOperate))
        {
            mErrors.copyAllErrors(pubsubmit.mErrors);
            return false;
        }
        return true;
    }

    /**
     * 将外部传入的数据分解到本类的属性中
     * @param: 无
     * @return: boolean
     */
    private boolean getInputData()
    {
        try
        {
            mGlobalInput = (GlobalInput)mInputData.getObjectByObjectName("GlobalInput", 0);
            inLYRenewBankInfoSet = (LYRenewBankInfoSet)mInputData.getObjectByObjectName("LYRenewBankInfoSet", 0);
        }
        catch (Exception e) {
            // @@错误处理
            CError tError = new CError();
            tError.moduleName = "RenewBankManageBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "接收数据失败!!";
            this.mErrors .addOneError(tError) ;
            return false;
        }

        return true;
    }


    /**
     * 根据前面的输入数据，进行逻辑处理
     * @return 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData(String cOperate)
    {
        boolean retflag = true;

        //转帐确认
        if (cOperate.equals("Confirm"))
            retflag = confirmData();
        //转帐撤销
        else if(cOperate.equals("Undo"))
            retflag = undoData();

        return retflag;
    }

    /**
     *
     * @return
     */
    private boolean confirmData()
    {
        String tLimit = PubFun.getNoLimit( mGlobalInput.ManageCom);
        String tSerialNo = PubFun1.CreateMaxNo("SERIALNO",tLimit );           //产生流水号码
        String currentDate = PubFun.getCurrentDate();
        String currentTime = PubFun.getCurrentTime();

        for(int i = 1 ; i <= inLYRenewBankInfoSet.size() ; i ++)
        {
            LYRenewBankInfoSchema tLYRenewBankInfoSchema = (LYRenewBankInfoSchema)inLYRenewBankInfoSet.get(i);
            tLYRenewBankInfoSchema.setSerialNo(tSerialNo);
            tLYRenewBankInfoSchema.setState("0");                               //确认状态
            tLYRenewBankInfoSchema.setConfirmOperator(mGlobalInput.Operator);
            tLYRenewBankInfoSchema.setMakeDate(currentDate);
            tLYRenewBankInfoSchema.setMakeTime(currentTime);
            tLYRenewBankInfoSchema.setModifyDate(currentDate);
            tLYRenewBankInfoSchema.setModifyTime(currentTime);
            inLYRenewBankInfoSet.set(i,tLYRenewBankInfoSchema);
        }
        map.put(inLYRenewBankInfoSet,"INSERT");

        return true;
    }

    private boolean undoData()
    {
        String currentDate = PubFun.getCurrentDate();
        String currentTime = PubFun.getCurrentTime();

        for(int i = 1 ; i <= inLYRenewBankInfoSet.size() ; i ++)
        {
            LYRenewBankInfoSchema tLYRenewBankInfoSchema = (LYRenewBankInfoSchema)inLYRenewBankInfoSet.get(i);
            LYRenewBankInfoDB tLYRenewBankInfoDB = new LYRenewBankInfoDB();
            tLYRenewBankInfoDB.setSchema(tLYRenewBankInfoSchema);
            if(tLYRenewBankInfoDB.getInfo())
            {
                tLYRenewBankInfoSchema = (LYRenewBankInfoSchema)tLYRenewBankInfoDB.getSchema();
                tLYRenewBankInfoSchema.setState("1");                               //确认状态
                tLYRenewBankInfoSchema.setUndoOperator(mGlobalInput.Operator);
                tLYRenewBankInfoSchema.setModifyDate(currentDate);
                tLYRenewBankInfoSchema.setModifyTime(currentTime);
                inLYRenewBankInfoSet.set(i,tLYRenewBankInfoSchema);
            }
        }
        map.put(inLYRenewBankInfoSet,"UPDATE");

        return true;
    }

    //输出：如果准备数据时发生错误则返回false,否则返回true
    private boolean prepareOutputData()
    {
        mInputData.clear();
        mInputData.add(map);

        return true;
    }

    /**
     * 数据输出方法，供外界获取数据处理结果
     * @return 包含有数据查询结果字符串的VData对象
     */
    public VData getResult() {
        return mResult;
    }

    public static void main(String[] args)
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.Operator = "DEBUG";
        tGlobalInput.ComCode = "86";

        LYRenewBankInfoSchema tLYRenewBankInfoSchema = new LYRenewBankInfoSchema();
        tLYRenewBankInfoSchema.setSerialNo("86000020070980000865");
        tLYRenewBankInfoSchema.setGetNoticeNo("86110020050310007058");
        tLYRenewBankInfoSchema.setPrtNo("86110100033565");
        tLYRenewBankInfoSchema.setContNo("86110020040210043123");
        tLYRenewBankInfoSchema.setAppntName("果艳红");
        tLYRenewBankInfoSchema.setRiskCode("111601");
        LYRenewBankInfoSet tLYRenewBankInfoSet = new LYRenewBankInfoSet();
        tLYRenewBankInfoSet.add(tLYRenewBankInfoSchema);

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tLYRenewBankInfoSet);

        RenewBankManageBL RenewBankManageBL1 = new RenewBankManageBL();
        logger.debug(RenewBankManageBL1.submitData(tVData,"Undo"));
    }
}
