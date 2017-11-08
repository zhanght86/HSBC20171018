/**
 * <p>Title:保全报表</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：协议退保原因统计报表</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author guoxiang
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class GTReasonStatBL
{
private static Logger logger = Logger.getLogger(GTReasonStatBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mStartDate = "";
    private String mEndDate = "";
    private String mManageCom = "";
    private String mStatType = "";
		private String mSaleChnl = "";

    public GTReasonStatBL(){ }

    /**传输数据的公共方法*/
    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!cOperate.equals("PRINT"))
        {
            CError.buildErr(this, "不支持的操作字符串");
            return false;
        }

        if (!getInputData(cInputData))
        {
            return false;
        }

        if (!getPrintData())
        {
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
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput",0));
        TransferData tTransferData = (TransferData)cInputData.getObjectByObjectName("TransferData",0);
        if (tTransferData == null || mGlobalInput == null)
        {
            CError.buildErr(this, "传入后台的参数缺少！");
            return false;
        }

        mStartDate = (String)tTransferData.getValueByName("StartDate");
        mEndDate = (String)tTransferData.getValueByName("EndDate");
        mManageCom = (String)tTransferData.getValueByName("ManageCom");
        mStatType = (String)tTransferData.getValueByName("StatType");
				mSaleChnl = (String)tTransferData.getValueByName("SaleChnl");

        if(mStartDate.equals("") || mEndDate.equals("") || mManageCom.equals("") || mStatType.equals(""))
        {
            CError.buildErr(this, "没有得到足够的查询信息！");
            return false;
        }

        return true;
    }

    private boolean getPrintData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("GTReasonStat.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("GTReason");
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        ExeSQL tExeSQL = new ExeSQL();
        //指标项：机构代码
				String sql = "select comcode,name from ldcom where comcode like concat('" + "?mManageCom?"
							       + "','%') and comcode not in('8699') and char_length(trim(comcode))="
										 + "?mStatType?" + "order by comcode";
				sqlbv1.sql(sql);
				sqlbv1.put("mManageCom", mManageCom);
				sqlbv1.put("mStatType", mStatType);
        SSRS mssrs = (SSRS)tExeSQL.execSQL(sqlbv1);
        if(tExeSQL.mErrors.needDealError() == true || mssrs == null || mssrs.getMaxRow() <= 0)
        {
            CError.buildErr(this, "无法查询得到机构代码信息！");
            return false;
        }

				String strSaleChnl = "";

				if (mSaleChnl != null && !mSaleChnl.equals(""))
				{
						strSaleChnl = " and exists (select 1 from lcpol where salechnl='"
												 + "?mSaleChnl?" + "' and contno=a.contno)";
				}
				SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
        //查询协议退保件数、协议退保件数：退保原因可以从ldcode－GTReason中查得
        String asql = "select com,type,count(num),sum(money) from"
						        +  "(select substr(a.managecom,1," + "?mStatType?" + ") com,"
								+"	a.EdorReasonCode type,"
                    + "1 num,abs(getmoney) money from lpedoritem a where managecom like concat('" + "?mManageCom?"
							       + "','%') and edortype = 'XT' and edorstate = '0' "
										+"and grpcontno='00000000000000000000' " 
					+	" and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
								+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)+")"
                                   + strSaleChnl 
									+") group by com,type  order by com,type ";
//        logger.debug("查询协议退保件数、协议退保件数" + asql);
        sqlbv2.sql(asql);
        sqlbv2.put("mStatType", mStatType);
        sqlbv2.put("mManageCom", mManageCom);
        sqlbv2.put("mSaleChnl", mSaleChnl);
        sqlbv2.put("mStartDate", mStartDate);
        sqlbv2.put("mEndDate", mEndDate);
        SSRS assrs = (SSRS)tExeSQL.execSQL(sqlbv2);
        if(tExeSQL.mErrors.needDealError() == true || assrs == null || assrs.getMaxRow() <= 0)
        {
            CError.buildErr(this, "无法查询得到协议退保件数、协议退保金额！");
            return false;
        }


        String tComCode = "";
        String[] strArr;
				double tTotal[] = {0,0,0,0,0,0,0,0,0,0,0,0,0};
				int n = 1;
        for(int i = 1 ; i <= mssrs.getMaxRow() ; i ++)
        {
            strArr = new String[13];
            tComCode = mssrs.GetText(i,1);
            strArr[0] = mssrs.GetText(i,2);  //支公司机构

						for(int t = 1 ; t < 13 ; t ++)
                strArr[t] = "0";

            for(int j = n ; j <= assrs.getMaxRow() ; j ++)
            {
                if(assrs.GetText(j,1).equals(tComCode))
                {
                    strArr[Integer.parseInt(assrs.GetText(j,2))] = assrs.GetText(j,3);      //填充协议退保件数
										tTotal[Integer.parseInt(assrs.GetText(j,2))] += Double.parseDouble(assrs.GetText(j,3));
                    strArr[Integer.parseInt(assrs.GetText(j,2)) + 6] = assrs.GetText(j,4);  //填充协议退保金额
										tTotal[Integer.parseInt(assrs.GetText(j,2)) + 6] += Double.parseDouble(assrs.GetText(j,4));
										n= j + 1;
										
								
                }
            }

            tlistTable.add(strArr);
        }

        String[] tArr = new String[13];
        tArr[0] = "合计";
        tArr[1] = ReportPubFun.functionJD(tTotal[1],"0");
        tArr[2] = ReportPubFun.functionJD(tTotal[2],"0");
        tArr[3] = ReportPubFun.functionJD(tTotal[3],"0");
        tArr[4] = ReportPubFun.functionJD(tTotal[4],"0");
        tArr[5] = ReportPubFun.functionJD(tTotal[5],"0");
        tArr[6] = ReportPubFun.functionJD(tTotal[6],"0");
        tArr[7] = ReportPubFun.functionJD(tTotal[7],"0.00");
        tArr[8] = ReportPubFun.functionJD(tTotal[8],"0.00");
        tArr[9] = ReportPubFun.functionJD(tTotal[9],"0.00");
        tArr[10] = ReportPubFun.functionJD(tTotal[10],"0.00");
        tArr[11] = ReportPubFun.functionJD(tTotal[11],"0.00");
        tArr[12] = ReportPubFun.functionJD(tTotal[12],"0.00");
        tlistTable.add(tArr);

        xmlexport.addListTable(tlistTable, tArr);

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
        texttag.add("StartDate", mStartDate);
        texttag.add("EndDate", mEndDate);
        texttag.add("Date", PubFun.getCurrentDate());
        logger.debug("大小" + texttag.size());
        if (texttag.size() > 0)
        {
            xmlexport.addTextTag(texttag);
        }

        //xmlexport.outputDocumentToFile("e:\\","GTReasonStat.xml");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

    public static void main (String []args)
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ComCode = "86";
        tGlobalInput.ManageCom = "86";
        tGlobalInput.Operator = "DEBUG";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("StartDate","2006-06-01");
        tTransferData.setNameAndValue("EndDate","2006-12-30");
        tTransferData.setNameAndValue("ManageCom","86");
        tTransferData.setNameAndValue("StatType","6");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        GTReasonStatBL tGTReasonStatBL = new GTReasonStatBL();
        tGTReasonStatBL.submitData(tVData,"PRINT");
    }
}
