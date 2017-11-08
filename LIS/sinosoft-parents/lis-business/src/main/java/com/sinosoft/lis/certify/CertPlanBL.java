/*
* <p>ClassName: CertPlanBL </p>
* <p>Description: 单证统计查询的实现文件 </p>
* <p>Copyright: Copyright (c) 2002</p>
* <p>Company: sinosoft </p>
* @Database: lis
* @CreateDate：2004-1-6
 */
package com.sinosoft.lis.certify;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.vschema.*;

import com.sinosoft.utility.*;

import java.sql.*;

import java.text.*;

import java.util.Hashtable;

public class CertPlanBL
{
private static Logger logger = Logger.getLogger(CertPlanBL.class);

    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 全局数据 */
    private GlobalInput m_GlobalInput = new GlobalInput();

    /** 数据操作字符串 */
    private String m_strOperate;

    /** 业务处理相关变量 */
    private String m_sql = "";
    SQLwithBindVariables sqlbvm = new SQLwithBindVariables();
    private LZCardPlanSet m_LZCardPlanSet = new LZCardPlanSet();

    public CertPlanBL()
    {
    }

    /**
 * 传输数据的公共方法
 * @param: cInputData 输入的数据
 * cOperate 数据操作
 * @return:
 */
    public boolean submitData(VData cInputData, String cOperate)
    {
        m_strOperate = verifyOperate(cOperate);
        if (m_strOperate.equals(""))
        {
            buildError("submitData", "不支持的操作字符串");

            return false;
        }

        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!dealData())
        {
            return false;
        }

        return true;
    }

    /**
 * 根据前面的输入数据，进行BL逻辑处理
 * 如果在处理过程中出错，则返回false,否则返回true
 */
    private boolean dealData()
    {
        try
        {
            if (!m_strOperate.equals(""))
            {
                return submitPrint(m_strOperate); //申请
            }
            else
            {
                buildError("dealData", "不支持的操作字符串");

                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();

            return false;
        }
    }

    /**
 * 从输入数据中得到所有对象
 * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
 */
    private boolean getInputData(VData cInputData)
    {
        try
        {
            this.m_GlobalInput.setSchema((GlobalInput) cInputData
                                         .getObjectByObjectName("GlobalInput", 0));
            
            if(cInputData.get(1) instanceof SQLwithBindVariables){
            	sqlbvm = (SQLwithBindVariables)cInputData.get(1);
            	m_sql = sqlbvm.sql();
            }else{
            	this.m_sql = (String) cInputData.get(1);
            	sqlbvm.sql(m_sql);
            }
            if ((m_sql == null) || m_sql.equals(""))
            {
                buildError("getInputData", "没有查询语句");

                return false;
            }
            if ((m_GlobalInput == null) || m_GlobalInput.equals(""))
            {
                buildError("getInputData", "没有登陆信息");

                return false;
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            buildError("getInputData", ex.getMessage());

            return false;
        }

        return true;
    }

    private boolean prepareOutputData(VData aVData)
    {
        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "CertPlanBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

    private String verifyOperate(String szOperate)
    {
        String szReturn = "";
        String[] szOperates = {"PRINT"};
        for (int nIndex = 0; nIndex < szOperates.length; nIndex++)
        {
            if (szOperate.equals(szOperates[nIndex]))
            {
                szReturn = szOperate;
            }
        }

        return szReturn;
    }

    /**
 * 统计报表的打印功能
 * @return
 */
    private boolean submitPrint(String codeType) throws Exception
    {
        mResult.clear();

        XmlExport xe = new XmlExport();
//        logger.debug("sql:" + m_sql);

        LZCardPlanDB tLZCardPlanDB = new LZCardPlanDB();
        m_LZCardPlanSet = tLZCardPlanDB.executeQuery(sqlbvm);
        printReport(xe, m_LZCardPlanSet);
        mResult.add(xe);

        return true;
    }

    /**
 * 针对于单证统计报表打印
 * @param xe
 * @param aLZCardPlanSet
 * @param
 * @throws Exception
 */
    private void printReport(XmlExport xe, LZCardPlanSet aLZCardPlanSet)
                      throws Exception
    {
        xe.createDocument("CardAppReport.vts", "printer");

        Hashtable hashPrice = new Hashtable();
        String strSQL = "SELECT CertifyCode, CertifyPrice FROM LZCardPrint A"
                        + " WHERE PrtNo = (SELECT MAX(PrtNo) FROM LZCardPrint B"
                        + " WHERE B.CertifyCode = A.CertifyCode)"
                        + " ORDER BY CertifyCode";
        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
        sqlbv.sql(strSQL);
        ExeSQL es = new ExeSQL();
        SSRS ssrs = es.execSQL(sqlbv);
        if (ssrs.mErrors.needDealError())
        {
            mErrors.copyAllErrors(ssrs.mErrors);
            throw new Exception("取单证印刷费用失败！");
        }

        String strCertifyCode = "";
        for (int nIndex = 0; nIndex < ssrs.getMaxRow(); nIndex++)
        {
            strCertifyCode = ssrs.GetText(nIndex + 1, 1);
            hashPrice.put(strCertifyCode, ssrs.GetText(nIndex + 1, 2));
        }

        // put data to xmlexport
        int nLen = 10; // 列表数据的列数 申请和归档的列数为7，批复的为9  取max
        String[] values = new String[nLen];
        double dMoney = 0.0; // print cost
        double dSumMoney = 0.0; // sum print cost
        double dSumCount = 0.0; // sum count
        double dReSumCount = 0.0;
        CardPlanBL tCardPlanBl = new CardPlanBL();
        ListTable lt = new ListTable();
        lt.setName("Info");
        for (int nIndex = 0; nIndex < aLZCardPlanSet.size(); nIndex++)
        {
            LZCardPlanSchema tLZCardPlanSchema = aLZCardPlanSet.get(nIndex + 1);
            values = new String[nLen];
            strCertifyCode = tLZCardPlanSchema.getCertifyCode();
            try
            {
                values[0] = tLZCardPlanSchema.getMakeDate();
                values[1] = tLZCardPlanSchema.getAppCom();
                values[2] = strCertifyCode;
                values[3] = ReportPubFun.getCertifyName(strCertifyCode);
                values[4] = String.valueOf(tLZCardPlanSchema.getAppCount());
                values[5] = (String) hashPrice.get(strCertifyCode);
                dSumCount += Double.parseDouble(values[4]);
                dMoney = tLZCardPlanSchema.getAppCount() * Double.parseDouble(values[5]);
                values[6] = String.valueOf(dMoney);
                values[7] = String.valueOf(tLZCardPlanSchema.getRetCount());
                values[8] = tLZCardPlanSchema.getRetState();
                values[9]=  "";
                if (tCardPlanBl.queryPlanBuget(tLZCardPlanSchema, m_GlobalInput))
                {
                    values[9] = "超过预算";
                }
                else
                {
                    values[9] = "没超过预算";
                }

                dReSumCount += Double.parseDouble(values[7]);
                dSumMoney += dMoney;
            }
            catch (Exception ex)
            {
                ex.getMessage();
            }
            lt.add(values);
        }

        values = new String[nLen];
        values[0] = "AppTime";
        values[1] = "AppCom";
        values[2] = "CertifyCode";
        values[3] = "CertifyName";
        values[4] = "AppCount";
        values[5] = "Price";
        values[6] = "PrintMoney";
        values[7] = "ReCount";
        values[8] = "ReState";
        values[9] = "Buget";
        xe.addListTable(lt, values);

        TextTag tag = new TextTag();
        tag.add("SumMoney", new DecimalFormat(".00").format(dSumMoney));
        tag.add("SumCount", new DecimalFormat("0").format(dSumCount));
        tag.add("ReSumCount", new DecimalFormat(".00").format(dReSumCount));
        xe.addTextTag(tag);
    }
}
