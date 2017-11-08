package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.TransferData;
import com.sinosoft.lis.schema.LOPRTManagerSchema;
import com.sinosoft.lis.vschema.LOPRTManagerSet;
import com.sinosoft.lis.db.LOPRTManagerDB;


/**
 * <p>Title: </p>
 * <p>Description: 理赔决定通知书打印</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author zhaoy,2005-08-1
 * @version 1.0
 */

public class LLPRTProtestClaimUI
{
private static Logger logger = Logger.getLogger(LLPRTProtestClaimUI.class);


    public CErrors mErrors = new CErrors();         /** 错误处理类，每个需要错误处理的类中都放置该类 */
    private VData mInputData = new VData();         /** 往后面传输数据的容器 */
    private VData mResult = new VData();            /** 往界面传输数据的容器 */
    private String mOperate;                        /** 数据操作字符串 */
    private TransferData mTransferData = new TransferData();
    private String mClmNo    = "";      //赔案号

    public LLPRTProtestClaimUI(){}


    /**
       传输数据的公共方法
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!getInputData(cInputData,cOperate))
        {
            return false;
        }

        LLPRTProtestClaimBL tLLPRTProtestClaimBL = new LLPRTProtestClaimBL();

        if (tLLPRTProtestClaimBL.submitData(cInputData, mOperate) == false)
        {
            // @@错误处理
            this.mErrors.copyAllErrors(tLLPRTProtestClaimBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "LLPRTProtestClaimBL";
            tError.functionName = "submitData";
            tError.errorMessage = "数据查询失败!";
            this.mErrors.addOneError(tError);
            mInputData.clear();
            return false;
        }
        else
        {
            mResult = tLLPRTProtestClaimBL.getResult();
        }
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    public boolean getInputData(VData cInputData,String cOperate)
        {
            this.mInputData = cInputData;
            mTransferData = (TransferData)mInputData.getObjectByObjectName("TransferData", 0);
            this.mClmNo = (String) mTransferData.getValueByName("ClmNo");
            //从 打印管理表（LOPRTManager）中查询出 “印刷流水号---PrtSeq”传入打印类中
                  LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
                  LOPRTManagerSet tLOPRTManagerSet = new LOPRTManagerSet();
                  LOPRTManagerDB tLOPRTManagerDB = new LOPRTManagerDB();
                  String tPrtSeq="";//打印流水号--印刷流水号《》

            String tLOPRTManagerSql="select * from loprtmanager where 1=1 "
                              +" and otherno='"+mClmNo+"' "
                              +" and trim(code)='PCT005' "
                              +" and stateflag !='1' ";
                  logger.debug(tLOPRTManagerSql);
                  tLOPRTManagerSet.set(tLOPRTManagerDB.executeQuery(tLOPRTManagerSql));
                  if(tLOPRTManagerSet.size()!=1)
                  {
                      CError tError = new CError();
                      tError.moduleName = "LLPRTPatchFeeBL";
                      tError.functionName = "dealdata";
                      tError.errorMessage = "在打印管理表查询打印流水号参数失败!";
                      this.mErrors.addOneError(tError);
                      return false;
                  }
                  tLOPRTManagerSchema=tLOPRTManagerSet.get(1);
                  tPrtSeq=tLOPRTManagerSchema.getPrtSeq();
                  mTransferData.setNameAndValue("PrtSeq",tPrtSeq);
                  logger.debug("打印流水号为"+tPrtSeq);
                  logger.debug("######------在打印管理表查询打印流水号成功------########");

            return true;
    }
    public static void main(String[] args)
    {

    }
}
