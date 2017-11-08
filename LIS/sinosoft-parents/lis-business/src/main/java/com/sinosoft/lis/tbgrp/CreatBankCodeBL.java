package com.sinosoft.lis.tbgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.utility.CError;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class CreatBankCodeBL
{
private static Logger logger = Logger.getLogger(CreatBankCodeBL.class);

    /** 往后面传输数据的容器 */
    private VData mResult = new VData();
    private TransferData mTransferData = new TransferData();
    private VData mInputData = new VData();
    private MMap map = new MMap();

    private String mManageCom = "";
    private String BankCode = "";
    private String mBankName = "";
    private String mBankType = "";


    public CreatBankCodeBL()
    {
    }

    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */
    public boolean submitData(VData cInputData)
    {
        if(!getInputData(cInputData))
        {
            return false;
        }

        if(!dealData())
        {
            return false;
        }

        if(!prepareOutputData())
        {
            return false;
        }

        //数据提交、保存
        PubSubmit tPubSubmit = new PubSubmit();
        logger.debug("Start CreatBankCodeBL Submit...");

        if (!tPubSubmit.submitData(mInputData, ""))
        {
            logger.debug("维护银行编码失败！");
            return false;
        }

        mInputData = null;


        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    private boolean getInputData(VData tInputData)
    {
        mTransferData = (TransferData) tInputData.getObjectByObjectName("TransferData",0);
        mBankName = (String) mTransferData.getValueByName("BankName");
        mManageCom = (String) mTransferData.getValueByName("ManageCom");
        mBankType = (String) mTransferData.getValueByName("BankType");

        if(mBankName == null || mBankName.equals("") || mManageCom == null || mManageCom.equals(""))
        {
            // @@错误处理
            return false;
        }

        return true;
    }

    private boolean dealData()
    {
        try
        {
            String tLimitNo = PubFun.getNoLimit(mManageCom);

            BankCode = PubFun1.CreateMaxNo("BankCode",tLimitNo);

            if(BankCode != null)
            {
                String sql = "insert into ldcode values('bank','"+BankCode+"','"+mBankName+"','','"+mManageCom+"','"+mBankType+"')";
                map.put(sql,"INSERT");
            }

        } catch(Exception ex)
        {
            ex.printStackTrace();

            return false;
        }
        return true;
    }

    /**
     * 准备往后层输出所需要的数据
     * 输出：如果准备数据时发生错误则返回false,否则返回true
     */
    private boolean prepareOutputData()
    {
        mResult.clear();
        mInputData.clear();

        try
        {
            mInputData.add(map);
            mResult.add(BankCode);
        } catch(Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }

}
