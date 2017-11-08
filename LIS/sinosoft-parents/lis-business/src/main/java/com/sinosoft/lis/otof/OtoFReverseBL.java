package com.sinosoft.lis.otof;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.otof.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

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
public class OtoFReverseBL
{
private static Logger logger = Logger.getLogger(OtoFReverseBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往前面传输数据的容器 */
    private VData mResult = new VData();

    /** 往后面传输数据的容器 */
    private VData mInputData;
    private String mOperate;
    private String mOperator;
    private String Reason;
    private String AccountingDate;

    /** 业务处理相关变量 */
    String CurrentDate = PubFun.getCurrentDate();
    String CurrentTime = PubFun.getCurrentTime();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private LITranInfoSet mLITranInfoSet = new LITranInfoSet();
    private LITranUpdateLogSet mLITranUpdateLogSet = new LITranUpdateLogSet();
    private OFInterfaceSet fOFInterfaceSet = new OFInterfaceSet();
    private OFInterfaceSet mOFInterfaceSet = new OFInterfaceSet();
    private String tBatchNo = "";
    private  String rBatchNo = "";
    private String tFlag="";

    public OtoFReverseBL()
    {
    }

    public static void main(String[] args)
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
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (getInputData(cInputData) == false)
        {
            return false;
        }

        //数据操作校验
        if (checkData() == false)
        {
            return false;
        }

        //进行业务处理
        if (dealData() == false)
        {
            // @@错误处理
            CError.buildErr(this, "数据处理失败OtoFReverseBL-->dealData!");
            return false;
        }

        //准备往后台的数据
        if (prepareOutputData() == false)
        {
            return false;
        }

        //数据提交
        PubSubmit ps = new PubSubmit();
        if(!ps.submitData(mInputData))
        {
        	 this.mErrors.copyAllErrors(ps.mErrors);
           CError.buildErr(this, "数据提交失败!");
           return false;        	 
        }

        return true;
    }

    /**
     * 数据处理校验
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean checkData()
    {
        if (mGlobalInput == null)
        {
        	CError.buildErr(this, "请重新登录！");
            return false;
        }

        return true;
    }

    /**
     * 根据前面的输入数据，进行逻辑处理
     * 如果在处理过程中出错，则返回false,否则返回true
     */
    private boolean dealData()
    {
        if (mOperate.equals("Reverse") || mOperate.equals("ReverseAll"))
        {
            logger.debug(" fOFInterfaceSet.size()"+ fOFInterfaceSet.size());
            for (int size = 1; size <= fOFInterfaceSet.size(); size++)
            {
                OFInterfaceSet tOFInterfaceSet = new OFInterfaceSet();
                OFInterfaceDB tOFInterfaceDB = new OFInterfaceDB();
                String strsql = "select * from OFInterface where MatchID='"+"?m1?"+"' "
                			  + "and BatchNo='" +"?m2?"+"' and "
                			  + "bussno='"+"?m3?"+"' and "
                			  + "polno='"+"?m4?"+"' "
                			  + "and (ReversedStatus is null or ReversedStatus='0')";
                SQLwithBindVariables sqlbv1=new SQLwithBindVariables();
    		    sqlbv1.sql(strsql);
    		    sqlbv1.put("m1", fOFInterfaceSet.get(size).getBatchNo());
    		    sqlbv1.put("m2", fOFInterfaceSet.get(size).getBatchNo());
    		    sqlbv1.put("m3", fOFInterfaceSet.get(size).getBussNo());
    		    sqlbv1.put("m4", fOFInterfaceSet.get(size).getPolNo());
                tOFInterfaceSet = tOFInterfaceDB.executeQuery(sqlbv1);
                logger.debug("strsql:" + strsql);

                if (tOFInterfaceSet.size() == 0)
                {
                	CError.buildErr(this,  "未找到要冲销的凭证！");
                    return false;
                }

                LITranInfoSet tLITranInfoSet = new LITranInfoSet();
                LITranInfoDB tLITranInfoDB = new LITranInfoDB();
                tLITranInfoDB.setBatchNo(fOFInterfaceSet.get(size).getBatchNo());
                tLITranInfoDB.setMatchID(fOFInterfaceSet.get(size).getMatchID());
                tLITranInfoSet = tLITranInfoDB.query();
                if (tLITranInfoSet.size() > 0)
                  mLITranInfoSet.add(tLITranInfoSet);

                LITranUpdateLogSchema tLITranUpdateLogSchema = new LITranUpdateLogSchema();
                tLITranUpdateLogSchema.setSeriNo(PubFun1.CreateMaxNo("SERIALNO",
                                                                     PubFun.getNoLimit(mGlobalInput.ManageCom)));
                tLITranUpdateLogSchema.setType("1");   //凭证冲消
                tLITranUpdateLogSchema.setBatchNo(fOFInterfaceSet.get(size)
                                                                 .getBatchNo());
                tLITranUpdateLogSchema.setMatchID(fOFInterfaceSet.get(size)
                                                                 .getMatchID());
                tLITranUpdateLogSchema.setManageCom(fOFInterfaceSet.get(size)
                                                                   .getManageCom());
                tLITranUpdateLogSchema.setVoucherID(fOFInterfaceSet.get(size)
                                                                   .getVoucherID());
                tLITranUpdateLogSchema.setPolNo(fOFInterfaceSet.get(size)
                                                               .getPolNo());
                tLITranUpdateLogSchema.setBussNo(fOFInterfaceSet.get(size)
                                                                .getBussNo());
                tLITranUpdateLogSchema.setTransDate(fOFInterfaceSet.get(size)
                                                                   .getTransDate());
                tLITranUpdateLogSchema.setReason(Reason);
                tLITranUpdateLogSchema.setOperator(mOperator);
                tLITranUpdateLogSchema.setMakeDate(CurrentDate);
                tLITranUpdateLogSchema.setMakeTime(CurrentTime);
                mLITranUpdateLogSet.add(tLITranUpdateLogSchema);

                String thisBatchNo = fOFInterfaceSet.get(size).getBatchNo();//判断规则改为根据批次号、凭证类型、记账日期
                String thisAccountingDate = fOFInterfaceSet.get(size).getAccountingDate();
                String thisVoucherType = fOFInterfaceSet.get(size).getVoucherType();
                String lastBatchNo = "";
                String lastAccountingDate="";
                String lastVoucherType ="";


                if (size > 1)
                {
                    lastBatchNo = fOFInterfaceSet.get(size - 1).getBatchNo();
                    lastAccountingDate = fOFInterfaceSet.get(size - 1).getAccountingDate();
                    lastVoucherType = fOFInterfaceSet.get(size - 1).getVoucherType();
                }

                logger.debug("lastBatchNo:" + lastBatchNo);
                logger.debug("lastAccountingDate:" + lastAccountingDate);
                logger.debug("lastVoucherType"+lastVoucherType);
                logger.debug("thisBatchNo:" + thisBatchNo);
                logger.debug("thisAccountingDate:" + thisAccountingDate);
                logger.debug("thisVoucherType:" + thisVoucherType);

                //zy 2009-07-09 如果是应收保费的冲消则判断规则为当天的所有航意险冲消凭证生成一个批次号
                if("AirPol".equals(tFlag))
                {
	                    tBatchNo = rBatchNo;
                }
                else
                {
	                if (!thisBatchNo.equals(lastBatchNo) ||
	                        !thisAccountingDate.equals(lastAccountingDate) ||
	                        !thisVoucherType.equals(thisVoucherType))
	                {
	                    tBatchNo = PubFun1.CreateMaxNo("OTOF", "20");
	                }
                }

                //准备要修改的冲销数据
                for (int i = 1; i <= tOFInterfaceSet.size(); i++)
                {
                    OFInterfaceSchema aOFInterfaceSchema = new OFInterfaceSchema();
                    aOFInterfaceSchema.setSchema(tOFInterfaceSet.get(i)
                                                                .getSchema());

                    OFInterfaceSchema mOFInterfaceSchema = new OFInterfaceSchema();
                    mOFInterfaceSchema.setSchema(tOFInterfaceSet.get(i)
                                                                .getSchema());

                    //确定冲销生成的记录
                    String tRecordID = PubFun1.CreateMaxNo("OTOF", "RECORD");
                    logger.debug("tRecordID:" + tRecordID);
                    aOFInterfaceSchema.setRecordID(tRecordID);
                    aOFInterfaceSchema.setReversedStatus("2");
                    aOFInterfaceSchema.setBatchNo(tBatchNo);
                    aOFInterfaceSchema.setVoucherID("-1");
                    aOFInterfaceSchema.setVoucherFlag("NA");
                    aOFInterfaceSchema.setVoucherDate("");
                    aOFInterfaceSchema.setOrigRowID(mOFInterfaceSchema.getRecordID());
                    aOFInterfaceSchema.setMatchID(-mOFInterfaceSchema.getMatchID());
                    aOFInterfaceSchema.setEnteredDR(-mOFInterfaceSchema.getEnteredDR());
                    aOFInterfaceSchema.setEnteredCR(-mOFInterfaceSchema.getEnteredCR());

                    AccountingDate = (AccountingDate.equals("")) ? mOFInterfaceSchema.getTransDate() : AccountingDate;
                    logger.debug("AccountingDate " + AccountingDate);
                    aOFInterfaceSchema.setAccountingDate(AccountingDate);
                    aOFInterfaceSchema.setMakeDate(CurrentDate);
                    aOFInterfaceSchema.setMakeTime(CurrentTime);
                    aOFInterfaceSchema.setModifyDate(CurrentDate);
                    aOFInterfaceSchema.setModifyTime(CurrentTime);

                    //修改要冲销的记录
                    mOFInterfaceSchema.setReversedStatus("1");
                    mOFInterfaceSchema.setReversedRowID(aOFInterfaceSchema.getRecordID());
                    mOFInterfaceSchema.setModifyDate(CurrentDate);
                    mOFInterfaceSchema.setModifyTime(CurrentTime);

                    mOFInterfaceSet.add(aOFInterfaceSchema);
                    mOFInterfaceSet.add(mOFInterfaceSchema);
                }
            }
        }
        else if (mOperate.equals("Delete"))
        {
            for (int size = 1; size <= fOFInterfaceSet.size(); size++)
            {
                OFInterfaceSet tOFInterfaceSet = new OFInterfaceSet();
                OFInterfaceDB tOFInterfaceDB = new OFInterfaceDB();
                String strsql = "select * from OFInterface where MatchID='" +"?s1?"+"' "
                 			  + "and BatchNo='" +"?s2?" + "'";
                SQLwithBindVariables sqlbv2=new SQLwithBindVariables();
    		    sqlbv2.sql(strsql);
    		    sqlbv2.put("s1", fOFInterfaceSet.get(size).getMatchID());
    		    sqlbv2.put("s2", fOFInterfaceSet.get(size).getBatchNo());
                // and (ReversedStatus is null or ReversedStatus='0') and origrowid=0 and Reversedrowid=0 and VoucherFlag is null
                tOFInterfaceSet = tOFInterfaceDB.executeQuery(sqlbv2);

                if (tOFInterfaceSet.size() == 0)
                {
                	CError.buildErr(this, "未找到要删除的凭证！");
                    break;
                }

                String DelFlag = "1";

                for (int i = 1; i <= tOFInterfaceSet.size(); i++)
                {
                    OFInterfaceSchema tOFInterfaceSchema = new OFInterfaceSchema();
                    tOFInterfaceSchema = tOFInterfaceSet.get(i);

                    if ((((tOFInterfaceSchema.getReversedStatus() != null) &&
                            tOFInterfaceSchema.getReversedStatus().equals("0")) ||
                            ((tOFInterfaceSchema.getReversedStatus() == null) ||
                            tOFInterfaceSchema.getReversedStatus().equals(""))) &&
                            (tOFInterfaceSchema.getOrigRowID() == 0) &&
                            (tOFInterfaceSchema.getReversedRowID() == 0) &&
                            (tOFInterfaceSchema.getVoucherFlag().equals("NA")))
                    {
                        DelFlag = "1";
                    }
                    else
                    {
                        DelFlag = "0";

                        break;
                    }
                }

                if (DelFlag.equals("0"))
                {
                	CError.buildErr(this, "要删除的凭证中可能出现错误数据，请确定后再删除！");
                    return false;
                }

                mOFInterfaceSet.add(tOFInterfaceSet);

                LITranInfoSet tLITranInfoSet = new LITranInfoSet();
                LITranInfoDB tLITranInfoDB = new LITranInfoDB();
                tLITranInfoDB.setBatchNo(fOFInterfaceSet.get(size).getBatchNo());
                tLITranInfoDB.setMatchID(fOFInterfaceSet.get(size).getMatchID());
                tLITranInfoSet = tLITranInfoDB.query();

                if (tLITranInfoSet.size() > 0)
                  mLITranInfoSet.add(tLITranInfoSet);

                LITranUpdateLogSchema tLITranUpdateLogSchema = new LITranUpdateLogSchema();
                tLITranUpdateLogSchema.setSeriNo(PubFun1.CreateMaxNo("SERIALNO",
                                                                     PubFun.getNoLimit(mGlobalInput.ManageCom)));
                tLITranUpdateLogSchema.setType("2");
                tLITranUpdateLogSchema.setBatchNo(fOFInterfaceSet.get(size)
                                                                 .getBatchNo());
                tLITranUpdateLogSchema.setMatchID(fOFInterfaceSet.get(size)
                                                                 .getMatchID());
                tLITranUpdateLogSchema.setManageCom(fOFInterfaceSet.get(size)
                                                                   .getManageCom());
                tLITranUpdateLogSchema.setVoucherID(fOFInterfaceSet.get(size)
                                                                   .getVoucherID());
                tLITranUpdateLogSchema.setPolNo(fOFInterfaceSet.get(size)
                                                               .getPolNo());
                tLITranUpdateLogSchema.setBussNo(fOFInterfaceSet.get(size)
                                                                .getBussNo());
                tLITranUpdateLogSchema.setTransDate(fOFInterfaceSet.get(size)
                                                                   .getTransDate());
                tLITranUpdateLogSchema.setReason(Reason);
                tLITranUpdateLogSchema.setOperator(mOperator);
                tLITranUpdateLogSchema.setMakeDate(CurrentDate);
                tLITranUpdateLogSchema.setMakeTime(CurrentTime);
                mLITranUpdateLogSet.add(tLITranUpdateLogSchema);
            }
        }
        else
        {
            CError.buildErr(this, "处理类型出现错误，请确认传递的参数是否正确！");

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
        try
        {
            mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",
                                                                                  0));
            mOperator = mGlobalInput.Operator;

            if (mOperate.equals("Reverse") || mOperate.equals("ReverseAll"))
            {
                fOFInterfaceSet = (OFInterfaceSet) cInputData.getObjectByObjectName("OFInterfaceSet",
                                                                                    0);
                mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",
                                                                                0);
                Reason = (String) mTransferData.getValueByName("Reason");
                AccountingDate = (String) mTransferData.getValueByName("AccountingDate");
                rBatchNo =(String) mTransferData.getValueByName("rBatchNo"); //航意险批次号
                tFlag =(String) mTransferData.getValueByName("tFlag");//航意险标志
            }

            if (mOperate.equals("Delete"))
            {
                fOFInterfaceSet = (OFInterfaceSet) cInputData.getObjectByObjectName("OFInterfaceSet",
                                                                                    0);
                mTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",
                                                                                0);
                Reason = (String) mTransferData.getValueByName("Reason");
            }
        }
        catch (Exception ex)
        {
            CError.buildErr(this, ex.toString());
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
        mInputData = new VData();
        MMap mp = new MMap();

        if (mOperate.equals("Reverse") || mOperate.equals("ReverseAll") )
        {
            mp.put(mOFInterfaceSet, "DELETE&INSERT");
            mp.put(mLITranInfoSet, "DELETE");
            mp.put(mLITranUpdateLogSet, "INSERT");
        }
        
        if (mOperate.equals("Delete"))
        {
            mp.put(mOFInterfaceSet, "DELETE");
            mp.put(mLITranInfoSet, "DELETE");
            mp.put(mLITranUpdateLogSet, "INSERT");
        }
        
        mInputData.add(mp);

        return true;
    }

    public VData getResult()
    {
        return mResult;
    }
}
