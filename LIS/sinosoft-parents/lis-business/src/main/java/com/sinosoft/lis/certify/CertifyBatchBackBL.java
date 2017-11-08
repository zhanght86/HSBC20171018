package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.text.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author z
 * @version 1.0
 */
import java.util.*;


public class CertifyBatchBackBL
{
private static Logger logger = Logger.getLogger(CertifyBatchBackBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    //业务处理相关变量

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private TransferData mTransferData = new TransferData();
    private String mOperate = "";
    private String CurrentDate = PubFun.getCurrentDate();
    private String receivecom="";//接收机构
    private String sendoutcom="";//发送机构
    private String CertifyCode="";//单证编码
    private boolean flag=true;//回退标志

    public CertifyBatchBackBL()
    {
    }



    /**
     * 传输数据的公共方法
     * @param cInputData
     * @param cOperate
     * @return
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        mOperate = cOperate;

        try
        {
            if (!cOperate.equals("INSERT"))
            {
                buildError("submitData", "不支持的操作字符串");

                return false;
            }

            // 得到外部传入的数据，将数据备份到本类中（不管有没有operate,都要执行这一部）
            if (!getInputData(cInputData))
            {
                return false;
            }

            if (cOperate.equals("TAKEBACK"))
            {
                mResult.clear();

                // 准备所有要打印的数据
                //getPrintData();
            }

            return BackCard();

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("submitData", ex.toString());

            return false;
        }
    }

    /**
     * 根据前面的输入数据，进行BL逻辑处理
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
        //全局变量
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        mTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);

        if (mGlobalInput == null)
        {
            buildError("getInputData", "没有得到足够的信息！");

            return false;
        }
        if(mTransferData != null)
        {
          logger.debug("mTransferData != null");
          sendoutcom=(String)mTransferData.getValueByName("SendOutCom");
          logger.debug("页面输入的发送机构是"+sendoutcom);
          receivecom=(String)mTransferData.getValueByName("ReceiveCom");
          logger.debug("页面输入的接收机构是"+receivecom);
          CertifyCode=(String)mTransferData.getValueByName("CertifyCode");
          logger.debug("页面输入的单证编码是"+CertifyCode);

        }

        return true;
    }

    //得到返回值
    public VData getResult()
    {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();

        cError.moduleName = "CertifyBatchBackBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }
    
    private String sfun(String str1 ,String str2){
    	if(str1 == null || str1.equals(""))return "";
    	return "?"+str2+"?";
    }

    //根据sql语句查询记录
    private boolean BackCard()
    {
    	int max=1;
    	int i=0;

    	ExeSQL tExeSQL = new ExeSQL();
    	LZCardSet setLZCard = new LZCardSet( );
    	CertReveSendOutBL tCertReveSendOutBL=new CertReveSendOutBL();

        //组成sql语句执行相应的操作
    	String sql="select CertifyCode,StartNo,EndNo,SumCount from lzcard "
    			  +"where receivecom = '"+"?receivecom?"+"' and sendoutcom = '"+"?sendoutcom?"+"' "
    			  + ReportPubFun.getWherePart("certifycode",sfun(CertifyCode,"certifycode"))
    			  + " order by certifycode,startno";
    	//String sql="select CertifyCode,StartNo,EndNo from lzcard where receivecom = 'A86110000' and sendoutcom like 'A%'";
    	logger.debug("SQL : " + sql);
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	sqlbv.sql(sql);
    	sqlbv.put("receivecom",sendoutcom);
    	sqlbv.put("sendoutcom",receivecom);
    	sqlbv.put("certifycode",CertifyCode);
        SSRS tSSRS = tExeSQL.execSQL(sqlbv);
        max=tSSRS.getMaxRow();
        logger.debug("符合条件的记录有" + max +"条");

        //循环进行回退操作
        for(i=1;i<=max;i++)
        {
//        	logger.debug("第"+i+"条记录"+",回退标志状态为"+flag);
        	LZCardSchema schemaLZCard = new LZCardSchema( );
        	schemaLZCard.setCertifyCode(tSSRS.GetText(i,1));
			//schemaLZCard.setSubCode(tSSRS.GetText(i,2));
			//schemaLZCard.setRiskCode(tSSRS.GetText(i,3));
			//schemaLZCard.setRiskVersion(tSSRS.GetText(i,4));
			schemaLZCard.setStartNo(tSSRS.GetText(i,2));
			schemaLZCard.setEndNo(tSSRS.GetText(i,3));
			schemaLZCard.setSendOutCom(sendoutcom);
			schemaLZCard.setReceiveCom(receivecom);
            schemaLZCard.setSumCount(tSSRS.GetText(i,4));            //回收无号单证考虑
			/*
			schemaLZCard.setSumCount(tSSRS.GetText(i,9));
			schemaLZCard.setPrem(tSSRS.GetText(i,10));
		    schemaLZCard.setAmnt(tSSRS.GetText(i,11));
		    schemaLZCard.setHandler(tSSRS.GetText(i,12));
		    schemaLZCard.setHandleDate(tSSRS.GetText(i,13));
		    schemaLZCard.setInvaliDate(tSSRS.GetText(i,14));
		    schemaLZCard.setTakeBackNo(tSSRS.GetText(i,15));
			schemaLZCard.setSaleChnl(tSSRS.GetText(i,16));
			schemaLZCard.setOperateFlag(tSSRS.GetText(i,17));
			schemaLZCard.setPayFlag(tSSRS.GetText(i,18));
			schemaLZCard.setEnterAccFlag(tSSRS.GetText(i,19));
			schemaLZCard.setReason(tSSRS.GetText(i,20));
			schemaLZCard.setState(tSSRS.GetText(i,21));
			schemaLZCard.setOperator(tSSRS.GetText(i,22));
			schemaLZCard.setMakeDate(tSSRS.GetText(i,23));
			schemaLZCard.setMakeTime(tSSRS.GetText(i,24));
			schemaLZCard.setModifyDate(tSSRS.GetText(i,25));
			schemaLZCard.setModifyTime(tSSRS.GetText(i,26));
			*/
			setLZCard.add(schemaLZCard);
        }

        // 准备传输数据 VData,每100条记录提交一次数据
        logger.debug("开始提交数据");
        VData vData = new VData();
        vData.addElement(mGlobalInput);
        vData.addElement(setLZCard);
        Hashtable hashParams = new Hashtable();
        hashParams.put("CertifyClass", CertifyFunc.CERTIFY_CLASS_CERTIFY);
        vData.addElement(hashParams);

        //执行回退操作
        flag=tCertReveSendOutBL.submitData(vData,"INSERT");
        setLZCard.clear();
        vData.clear();
        if(flag==false)
        {
            logger.debug("回退标志为false,返回 : " + tCertReveSendOutBL.mErrors.getFirstError());
            buildError("BackCard",tCertReveSendOutBL.mErrors.getFirstError());
            return flag;
        }

    	return flag;
    }

    public static void main(String[] args)
    {

        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ComCode = "8611";
        tGlobalInput.Operator = "001";
        tGlobalInput.ManageCom = "8611";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("SendOutCom","A86310000");
        tTransferData.setNameAndValue("ReceiveCom","A863100");
        tTransferData.setNameAndValue("CertifyCode","110101");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        CertifyBatchBackBL tCertifyBatchBackBL = new CertifyBatchBackBL();
        tCertifyBatchBackBL.submitData(tVData,"INSERT");

    }

}
