/**
 * <p>Title:保全报表</p>
 * <p>Description: 1张报表</p>
 * <p>bq1：保全工作量统计</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: sinosoft</p>
 * @author guoxiang
 * @version 1.0
 */
package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class BQWorkStatBL
{
private static Logger logger = Logger.getLogger(BQWorkStatBL.class);

    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /** 全局数据 */
    private GlobalInput mGlobalInput = new GlobalInput();
    private String mStartDate = ""; // 统计开始时间
    private String mEndDate = "";   //统计结束日期
    private String mManageCom = ""; //统计机构
    private String mStatType = "";  //统计粒度 (4-二级机构 6-三级机构)

    public BQWorkStatBL(){ }

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
            CError.buildErr(this, "缺少传入后台的参数！");
            return false;
        }

        mStartDate = (String)tTransferData.getValueByName("StartDate");
        mEndDate = (String)tTransferData.getValueByName("EndDate");
        mManageCom = (String)tTransferData.getValueByName("ManageCom");
        mStatType = (String)tTransferData.getValueByName("StatType");
        if(mStartDate.equals("") || mEndDate.equals("") || mManageCom.equals("") || mStatType.equals(""))
        {
            CError.buildErr(this, "没有得到足够的查询信息！");
            return false;
        }

        return true;
    }

    /**
     * 获取保全项目系数表
     * @return
     */
//    private boolean getStatRate()
//    {
//        String sql = "select edortype,stattype,convertrate "
//                   + "from lmedorstatrate order by edortype";
//        SSRS tssrs = new ExeSQL().execSQL(sql);
//        if(tssrs == null || tssrs.getMaxRow() <= 0)
//        {
//            buildError("getStatRate","无法获取保全项目参数表信息！");
//            return false;
//        }
//        mArrRate = (String[][])tssrs.getAllData();
//
//        return true;
//    }

    /**
		 * 获取打印数据
		 */
    private boolean getPrintData()
    {
        XmlExport xmlexport = new XmlExport(); //新建一个XmlExport的实例
        xmlexport.createDocument("BQWorkStat.vts", "");

        ListTable tlistTable = new ListTable();
        tlistTable.setName("WorkStat");
        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
        //指标项：机构代码
        String sql = "select comcode from ldcom "
                   + "where comcode like concat('" + "?mManageCom?" + "','%')  and comcode not in('8699') "
				           + "and char_length(trim(comcode))=" + "?mStatType?"
                   + "order by comcode";
        sqlbv1.sql(sql);
        sqlbv1.put("mManageCom", mManageCom);
        sqlbv1.put("mStatType", mStatType);
        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
				String asql = "select com1 ,count(distinct opt),sum(t1),sum(t2),sum(t3),sum(rate) "
								+ "from (select substr(com,1," + "?mStatType?" + ") com1,opt,etype,"
								+ "(case type when '1' then 1 else 0 end) t1,(case type when '2' then 1 else 0 end) t2,(case type when '3' then 1 else 0 end) t3,rate from "
				        + "(select min(managecom) com,"
				        + "min(operator) opt,edorno,edortype etype,"
				        + "(select convertrate from lmedorstatrate where edortype=a.edortype) rate,"
				        + "(select stattype from lmedorstatrate where edortype=a.edortype) type "
				        + "From lpedoritem  a where edorstate='0' and grpcontno='00000000000000000000'" +
				        		" and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
								+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
				        		+")"
								+ " and managecom like concat('"+ "?mManageCom?" + "','%')  group by edorno,edortype"				       
				        + " ))  where exists (select 1 from lduser where usercode=opt) "
				        + "group by com1 order by com1"
				        ;
				sqlbv2.sql(asql);
				sqlbv2.put("mStatType", mStatType);
				sqlbv2.put("mStartDate", mStartDate);
				sqlbv2.put("mEndDate", mEndDate);
				sqlbv2.put("mManageCom", mManageCom);

        VData tVData = new VData();
        tVData.add(sqlbv1);
        tVData.add(sqlbv2);

        HashReport tHashReport = new HashReport(sql,tVData,6,"Double");
        String[][] arrRate = tHashReport.calReportItem();
        String[] strArr = new String[9];
				int[] tTotal = {0,0,0,0,0,0};
        for (int i = 0 ; i < arrRate.length; i++)
        {
            strArr = new String[9];
            strArr[0] = ReportPubFun.getMngName(arrRate[i][0]);                 //支公司机构
            strArr[1] = Double.valueOf(arrRate[i][1]).intValue()+"";                  //保全人力
            strArr[2] = Double.valueOf(arrRate[i][2]).intValue()+"";       //变更类项目操作件数
            strArr[3] = Double.valueOf(arrRate[i][3]).intValue()+"";       //给付类项目操作件数
            strArr[4] = Double.valueOf(arrRate[i][4]).intValue()+"";       //其他类项目操作件数
            strArr[5] = ReportPubFun.functionJD(Double.parseDouble(arrRate[i][2]) + Double.parseDouble(arrRate[i][3]) + Double.parseDouble(arrRate[i][4]),"0"); //合计
            strArr[6] = ReportPubFun.functionDivision(strArr[5],strArr[1],"0.00");
            strArr[7] = arrRate[i][5];                                          //标准工作量
						strArr[8] = ReportPubFun.functionDivision(strArr[7],strArr[1],"0.00");//人均标准工作量
            tlistTable.add(strArr);

						//计算合计值
						tTotal[1]+=Double.valueOf(arrRate[i][1]).intValue();
						tTotal[2]+=Double.valueOf(arrRate[i][2]).intValue();
						tTotal[3]+=Double.valueOf(arrRate[i][3]).intValue();
						tTotal[4]+=Double.valueOf(arrRate[i][4]).intValue();
						tTotal[5]+=Double.valueOf(arrRate[i][5]).intValue();
        }
				String[] tArr = new String[9];
				tArr[0] = "合计";     //"机构名称";
				tArr[1] = tTotal[1]+"";     //"保全人力";
				tArr[2] = tTotal[2]+"";     //"变更类件数";
				tArr[3] = tTotal[3]+"";     //"给付类件数";
				tArr[4] = tTotal[4]+"";     //"其他类件数";
				tArr[5] = (tTotal[2]+tTotal[3]+tTotal[4])+"";     //"合计件数";
				tArr[6] = ReportPubFun.functionDivision(tArr[5],tArr[1],"0.00");;     //"人均";
				tArr[7] = tTotal[5]+"";     //"标准工作量";
				tArr[8] = ReportPubFun.functionDivision(tArr[7],tArr[1],"0.00");     //"人均标准工作量";

				tlistTable.add(tArr);

        tArr = new String[9];
        tArr[0] = "";     //"机构名称";
        tArr[1] = "";     //"保全人力";
        tArr[2] = "";     //"变更类件数";
        tArr[3] = "";     //"给付类件数";
        tArr[4] = "";     //"其他类件数";
        tArr[5] = "";     //"合计件数";
        tArr[6] = "";     //"人均";
        tArr[7] = "";     //"标准工作量";
				tArr[8] = "";     //"人均标准工作量";
        xmlexport.addListTable(tlistTable, tArr);

        TextTag texttag = new TextTag(); //新建一个TextTag的实例
        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
        texttag.add("StartDate", mStartDate);
        texttag.add("EndDate", mEndDate);
        texttag.add("date", PubFun.getCurrentDate());
        logger.debug("大小" + texttag.size());
        if (texttag.size() > 0)
        {
            xmlexport.addTextTag(texttag);
        }

        //xmlexport.outputDocumentToFile("e:\\","BQWorkStat.xml");//输出xml文档到文件
        mResult.clear();
        mResult.addElement(xmlexport);

        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

    /* 测试 */
    public static void main (String []args)
    {
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput.ComCode = "86";
        tGlobalInput.ManageCom = "8613";
        tGlobalInput.Operator = "DEBUG";

        TransferData tTransferData = new TransferData();
        tTransferData.setNameAndValue("StartDate","2006-01-01");
        tTransferData.setNameAndValue("EndDate","2006-05-31");
        tTransferData.setNameAndValue("ManageCom","8613");
        tTransferData.setNameAndValue("StatType","6");

        VData tVData = new VData();
        tVData.add(tGlobalInput);
        tVData.add(tTransferData);

        BQWorkStatBL tBQWorkStatBL = new BQWorkStatBL();
        tBQWorkStatBL.submitData(tVData,"PRINT");
    }
}
