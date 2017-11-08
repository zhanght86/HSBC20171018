package com.sinosoft.lis.acc;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: SinoSoft</p>
 *
 * @author Gaoht
 * @version 1.0
 */
import com.sinosoft.utility.*;
import com.sinosoft.lis.f1print.BqBill;

public class AccPrintManagerBL
{
private static Logger logger = Logger.getLogger(AccPrintManagerBL.class);


    // 常量定义
    public static final String TL_CODE_BUSINESS = "TL01"; // 投连交易通知书
    public static final String CODE_InsuAccTLNotice = "TL02"; //投连帐户预警通知书;
    public static final String CODE_InsuAccTLHJNotice = "TL03"; //缓缴期通知书;
    public static final String CODE_InsuAccTLHJYJNotice = "TL04"; //缓缴预警期通知书;
    public static final String CODE_InsuAccDISAVLNotice = "TL05"; //停效期通知书;
    public static final String CODE_InsuAccTLTXYJNotice = "TL06";//投连停效预警通知数
    public AccPrintManagerBL()
    {
    }

    private String mCode = "";
    private VData mResult = new VData();
    public CErrors mErrors = new CErrors();
    public VData mVData = new VData();

    public boolean submitData(VData cInputData, String cOperate)
    {
        mCode = cOperate;
        mVData = cInputData;
        if(mCode == null || mCode.equals(""))
        {
            CError.buildErr(this, "传入参数为空");
            return false;
        }
        if(!DealData())
        {
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        return mResult;
    }

    private boolean DealData()
    {
        ExeSQL tExeSQL = new ExeSQL();
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql("select prtbl from LOReportCode where code='" + "?mCode?" + "' and codetype = '2'");
        sqlbv.put("mCode", mCode);
        String tClassName = tExeSQL.getOneValue(sqlbv);
        logger.debug(tClassName);
        if(tClassName != null && !tClassName.equals(""))
        {
            try
            {
                Class _Class = Class.forName(tClassName);
                BqBill tBqBill = (BqBill) _Class.newInstance();
                if(!tBqBill.submitData(mVData, mCode))
                {
                    CError.buildErr(this, tBqBill.getErrors().getFirstError());
                    return false;
                }
                else
                {
                    XmlExport tXmlExport = new XmlExport();
                    tXmlExport = (XmlExport) tBqBill.getResult().
                                 getObjectByObjectName("XmlExport", 0);
                    if(tXmlExport==null)
                    {
                        CError.buildErr(this, "生成打印数据失败原因：XmlExport==NULL");
                        return false;
                    }
                }
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                CError.buildErr(this, "初始化打印类失败");
                return false;
            }
        }
        else
        {
            CError.buildErr(this, "缺失LOReportCode打印描述");
            return false;
        }

        return true;
    }

    public static void main(String[] args)
    {
        AccPrintManagerBL accprintmanagerbl = new AccPrintManagerBL();
        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("valueDate","2008-01-11");
        tTransferData.setNameAndValue("Contno","801006004762");
        tTransferData.setNameAndValue("Printer","PDF");
        tTransferData.setNameAndValue("ManageCom","86");
        tTransferData.setNameAndValue("AgentCode","00000028");
        VData tVData = new VData();
        tVData.addElement(tTransferData); 
        accprintmanagerbl.submitData(tVData,"tl_008");
    }
}
