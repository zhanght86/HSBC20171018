/**
 * <p>Title:保全报表</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：协议退保差值统计报表</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author guoxiang
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
import com.sinosoft.lis.schema.LPEdorItemSchema;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.ListTable;
import com.sinosoft.utility.SQLwithBindVariables;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.TextTag;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.XmlExport;

public class GTMarginStatBL
{
private static Logger logger = Logger.getLogger(GTMarginStatBL.class);

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

    public GTMarginStatBL(){ }

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
        xmlexport.createDocument("GTMarginStat.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("GTMargin");
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
				String strSaleChnl = "";

				if (mSaleChnl != null && !mSaleChnl.equals(""))
				{
					  strSaleChnl = " and exists (select 1 from lcpol where salechnl='"
									       + "?mSaleChnl?" + "' and contno=a.contno)";
				}

        String sql = "select substr(managecom,1," + "?mStatType?" + "),abs(getmoney),contno from lpedoritem a "
                   + "where managecom like concat('" + "?mManageCom?" + "','%') and grpcontno='00000000000000000000' "
                   + "and edortype = 'XT' and edorstate = '0' "
                   +	" and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
					+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate,"?mStartDate?"),ReportPubFun.getParameterStr(mEndDate,"?mEndDate?"),1)+")"
                   + strSaleChnl + " order by substr(managecom,1," + "?mStatType?" + "),contno ";
        logger.debug(sql);
        sqlbv1.sql(sql);
        sqlbv1.put("mSaleChnl", mSaleChnl);
        sqlbv1.put("mStatType", mStatType);
        sqlbv1.put("mManageCom", mManageCom);
        sqlbv1.put("mStartDate", mStartDate);
        sqlbv1.put("mEndDate", mEndDate);
        SSRS tssrs = new ExeSQL().execSQL(sqlbv1);
        if(tssrs == null || tssrs.getMaxRow() <= 0)
        {
            CError.buildErr(this, "查询到的数据为空！");
            return false;
        }

        double tTotalGetMoney = 0.0,tTatalCashValue = 0.0;
        double tSumGetMoney = 0.0,tSumCashValue = 0.0;
        String tComCode = "",tGetMoney = "",tContNo = "";
       
    
        String[] strArr = new String[4];
        for(int i = 1 ; i <= tssrs.getMaxRow() ; i ++)
        {
            tComCode = tssrs.GetText(i,1);
            tGetMoney = tssrs.GetText(i,2);
            tContNo = tssrs.GetText(i,3);
     //这种程序级别的调用导致sql不能进行groupby操作,只能通过循环判断来达到groupby的操作.
            if(i > 1 && !tComCode.equals(tssrs.GetText(i-1,1)))                 //提交机构内的数据
            {
                strArr = new String[4];
                strArr[0] = ReportPubFun.getMngName(tssrs.GetText(i-1,1));      //支公司机构名称
                strArr[1] = ReportPubFun.functionJD(tSumGetMoney,"0.00");       //协议退保金额
                strArr[2] = ReportPubFun.functionJD(tSumCashValue,"0.00");      //实际现金价值
                strArr[3] = ReportPubFun.functionJD(tSumGetMoney - tSumCashValue,"0.00");
                tlistTable.add(strArr);

                tSumGetMoney = 0.0;                                             //清空统计金额
                tSumCashValue = 0.0;
            }

            tSumGetMoney += Double.parseDouble(tGetMoney);
		   tTotalGetMoney += Double.parseDouble(tGetMoney);
	
            LPEdorItemSchema tLPEdorItemSchema = new LPEdorItemSchema();
            tLPEdorItemSchema.setContNo(tContNo);
        
				
				tSumCashValue += tLPEdorItemSchema.getGetMoney() - Double.parseDouble((tLPEdorItemSchema.getStandbyFlag3()==null )? "0":tLPEdorItemSchema.getStandbyFlag3());
	          tTatalCashValue += tLPEdorItemSchema.getGetMoney() - Double.parseDouble((tLPEdorItemSchema.getStandbyFlag3()==null )? "0":tLPEdorItemSchema.getStandbyFlag3());

            //因为业务数据中不会有机构8699的数据，所以不用另外判断这个机构
            if(i == tssrs.getMaxRow())    //最后一条记录
            {
                strArr = new String[4];
                strArr[0] = ReportPubFun.getMngName(tComCode);                  //支公司机构名称
                strArr[1] = ReportPubFun.functionJD(tSumGetMoney,"0.00");       //协议退保金额
                strArr[2] = ReportPubFun.functionJD(tSumCashValue,"0.00");      //实际现金价值
                strArr[3] = ReportPubFun.functionJD(Double.parseDouble((tLPEdorItemSchema.getStandbyFlag3()==null )? "0":tLPEdorItemSchema.getStandbyFlag3()),"0.00");   //差值(因为退保金额是负数，现金价值是正数，不能用减法)
                tlistTable.add(strArr);
            }
        }

				String[] tArr = new String[4];
				tArr[0] = "合计";
				tArr[1] = ReportPubFun.functionJD(tTotalGetMoney,"0.00");
				tArr[2] = ReportPubFun.functionJD(tTatalCashValue,"0.00");
				tArr[3] = ReportPubFun.functionJD(tTotalGetMoney - tTatalCashValue,"0.00");
				tlistTable.add(tArr);

				tArr = new String[4];
				tArr[0] = "";     //"支公司机构名称";
				tArr[1] = "";     //"协议退保金额";
				tArr[2] = "";     //"实际现金价值";
				tArr[3] = "";     //"差值";
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

//        xmlexport.outputDocumentToFile("e:\\","GTMarginStat.xml");//输出xml文档到文件
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
        tGlobalInput.ManageCom = "8613";
        tGlobalInput.Operator = "DEBUG";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("StartDate","2005-04-15");
        tTransferData.setNameAndValue("EndDate","2005-04-15");
        tTransferData.setNameAndValue("ManageCom","8613");
        tTransferData.setNameAndValue("StatType","6");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        GTMarginStatBL tGTMarginStatBL = new GTMarginStatBL();
        tGTMarginStatBL.submitData(tVData,"PRINT");
    }
}
