package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import java.util.*;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description : 单证管理废除、遗失操作</p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Sinosoft</p>
 * @author wentao
 * @version 1.0
 */

public class CertAbandonBL  {
private static Logger logger = Logger.getLogger(CertAbandonBL.class);
    //错误处理类，每个需要错误处理的类中都放置该类
    public  CErrors mErrors = new CErrors();

    /* 业务相关的数据 */
    private GlobalInput globalInput = new GlobalInput();
    private String mStartNo = "";
    private String mEndNo = "";
    private String mCertifyCode = "";
    private String mReceiveCom = "";

    private LZCardSet outLZCardSet = new LZCardSet();
    private LZCardTrackSet outLZCardTrackSet = new LZCardTrackSet();
    private VData outputData = new VData();

    public CertAbandonBL() {
    }

    //传输数据的公共方法
    public boolean submitData(VData cInputData, String cOperate)
    {
        String stateflag = "";
        if(cOperate.equals("Abandon"))
            stateflag = "2";
        else if (cOperate.equals("Miss"))
            stateflag = "3";
        else
        {
            buildError("submitData","错误的操作类型！");
            return false;
        }

        if( !getInputData(cInputData) )  return false;

        //逻辑处理
        if( !dealData(stateflag) )  return false;

        //准备向后台传输的数据
        if( !prepareOutputData() )  return false;

        CertAbandonBLS tCertAbandonBLS = new CertAbandonBLS();
        if( !tCertAbandonBLS.submitData(outputData, "INSERT") )
        {
            if( tCertAbandonBLS.mErrors.needDealError() )
            {
                mErrors.copyAllErrors(tCertAbandonBLS.mErrors);
                return false;
            }else
            {
                buildError("dealOne", "CertAbandonBLS出错，但是没有提供详细的信息");
                return false;
            }
        }
        return true;
    }
    
    private String sfun(String str1 ,String str2){
    	if(str1 == null || str1.equals(""))return "";
    	return "?"+str2+"?";
    }
    //根据前面的输入数据，进行逻辑处理
    //如果在处理过程中出错，则返回false,否则返回true
    private boolean dealData(String cStateFlag)
    {
        String sql = "select certifycode,startno,endno,receivecom from lzcard "
                   + "where startno >= '" + "?startno?" + "' and endno <= '" + "?endno?" + "' "
                   + ReportPubFun.getWherePart("certifycode",sfun(mCertifyCode,"certifycode"))
                   + "and receivecom = " + "concat('A','?receivecom?')"          //and operateflag in ('1','2')
                   + "order by certifycode,startno,endno ";
        logger.debug("SQL : " + sql);
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(sql);
        sqlbv.put("startno", mStartNo);
        sqlbv.put("endno", mEndNo);
        sqlbv.put("certifycode", mCertifyCode);
        sqlbv.put("receivecom", globalInput.ComCode);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv);
        if(tssrs.mErrors.needDealError() == true || tssrs.getMaxRow() <= 0)
        {
            buildError("dealData","查询失败或者没有对应的记录！");
            return false;
        }

        String currentDate = PubFun.getCurrentDate();
        String currentTime = PubFun.getCurrentTime();

        // 产生回收清算单号
        String tTakeBackNo = PubFun1.CreateMaxNo("TAKEBACKNO",PubFun.getNoLimit(globalInput.ComCode));
        for(int i = 1; i <= tssrs.getMaxRow() ; i ++)
        {
            LZCardDB tLZCardDB = new LZCardDB();
            tLZCardDB.setCertifyCode(tssrs.GetText(i,1));
            tLZCardDB.setStartNo(tssrs.GetText(i,2));
            tLZCardDB.setEndNo(tssrs.GetText(i,3));
            LZCardSchema tLZCardSchema = (LZCardSchema)tLZCardDB.query().get(1);

            // Verify SendOutCom and ReceiveCom
//            LZCardSet tLZCardSet = CertifyFunc.formatCardList( tLZCardSchema );

            //准备变更的单证状态表数据
            tLZCardSchema.setSendOutCom(tssrs.GetText(i,4));                    //单证之前是在谁的手中
            tLZCardSchema.setReceiveCom(mReceiveCom);
            tLZCardSchema.setTakeBackNo(tTakeBackNo);
            tLZCardSchema.setStateFlag(cStateFlag);                             //置状态标志为作废、挂失
            tLZCardSchema.setOperateFlag("1");                                  //置操作标志为回收
            tLZCardSchema.setOperator(globalInput.Operator);
            tLZCardSchema.setModifyDate(currentDate);
            tLZCardSchema.setModifyTime(currentTime);

            //准备插入的单证轨迹表数据
            LZCardTrackSchema tLZCardTrackSchema = new LZCardTrackSchema();

            Reflections tReflections = new Reflections();
            tReflections.transFields(tLZCardTrackSchema,tLZCardSchema);

            tLZCardTrackSchema.setMakeDate(currentDate);
            tLZCardTrackSchema.setMakeTime(currentTime);

            outLZCardSet.add(tLZCardSchema);
            outLZCardTrackSet.add(tLZCardTrackSchema);
        }

        return true;
    }

    //从输入数据中得到所有对象
    //输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
    private boolean getInputData(VData vData)
    {
        globalInput.setSchema((GlobalInput)vData.getObjectByObjectName("GlobalInput", 0));
        TransferData tTransferData = (TransferData)vData.getObjectByObjectName("TransferData", 0);
        if(globalInput == null || tTransferData == null)
        {
            buildError("getInputData","传入的参数个数不足！");
            return false;
        }

        mStartNo = (String)tTransferData.getValueByName("StartNo");
        mEndNo = (String)tTransferData.getValueByName("EndNo");
        mCertifyCode = (String)tTransferData.getValueByName("CertifyCode");
        mReceiveCom = (String)tTransferData.getValueByName("ReceiveCom");

        if(mReceiveCom.substring(0,1).equals("D"))
        {
            buildError("getInputData","不允许将单证直接在业务员手中作废！");
            return false;
        }

        return true;
    }

    //准备往后层输出所需要的数据
    //输出：如果准备数据时发生错误则返回false,否则返回true
    private boolean prepareOutputData()
    {
        try
        {
            outputData.clear();
//            outputData.addElement(globalInput);
            outputData.addElement(outLZCardSet);
            outputData.addElement(outLZCardTrackSet);
        } catch(Exception ex)
        {
            // @@错误处理
            buildError("prepareData", "在准备往后层处理所需要的数据时出错。");
            return false;
        }
        return true;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError( );
        cError.moduleName = "CertAbandonBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    public static void main(String[] args)
    {
        GlobalInput globalInput = new GlobalInput( );
        globalInput.ComCode = "86";
        globalInput.Operator = "Debug";

        TransferData tTransferData = new TransferData();
//        tTransferData.setNameAndValue("CertifyCode","1201");
        tTransferData.setNameAndValue("StartNo","000000000000017913");
        tTransferData.setNameAndValue("EndNo","000000000000017920");
        tTransferData.setNameAndValue("ReceiveCom","A8688");

        //校验处理
        //内容待填充
        try
        {
            // 准备传输数据 VData
            VData vData = new VData();
            vData.addElement(globalInput);
            vData.addElement(tTransferData);

            // 数据传输
            CertAbandonBL tCertAbandonBL = new CertAbandonBL();
            if (!tCertAbandonBL.submitData(vData, "Abandon"))
                logger.debug(" 保存失败，原因是: " + tCertAbandonBL.mErrors.getFirstError());
            else
                logger.debug(" 保存成功 ");
        } catch(Exception ex)
        {
            logger.debug(ex.toString());
        }
    }
}
