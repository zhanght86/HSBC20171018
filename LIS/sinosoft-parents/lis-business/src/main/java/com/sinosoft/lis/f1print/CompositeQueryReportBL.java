package com.sinosoft.lis.f1print;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.service.BusinessService;
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

public class CompositeQueryReportBL implements BusinessService
{
private static Logger logger = Logger.getLogger(CompositeQueryReportBL.class);

    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();

    /*
    * @define globe variable
    */
    private TransferData tTransferData = new TransferData();
    private String ManageCom;//管理机构
    private String TemplateName; //模板的名称
    private String ReportName; //报表的名称
    private String TemplatePath; //模板路径
    private String ReportPath; //报表路径
    private String ReturnFlag = "0";
    private String tGrpContNo="";//团体保单号
    private HashMap tHashMap = new HashMap();
    private TransferData mTransferData = new TransferData();

    public CompositeQueryReportBL()
    {
    }

    public boolean submitData(VData cInputData, String cOperate)
    {
        if (!cOperate.equals("STAT"))
        {
            buildError("submitData", "不支持的操作字符串");

            return false;
        }

        if (!getInputData(cInputData))
        {
            return false;
        }

        mResult.clear();

        //准备打印数据
        if (!calReport())
        {
        	buildError("calReport", "无理赔记录!");
            return false;
        }

        return true;
    }


    /***
      * Name : getInputData()
      * function :receive data from jsp and check date
      * check :judge whether startdate begin enddate
      * return :true or false
      */
    private boolean getInputData(VData cInputData)
    {
    	//获得从页面传入的数据;
        tTransferData = (TransferData) cInputData.getObjectByObjectName("TransferData",0);
        //从容器中获得页面传入的数据
        tGrpContNo = (String) tTransferData.getValueByName("GrpContNo");

        //输出信息，做为验证
        logger.debug("本次需要查询的团单号是:"+tGrpContNo); 
        
        
        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

    /********
     * name : buildError
     * function : Prompt error message
     * return :error message
     */
    private void buildError(String szFunc, String szErrMsg)
    {
        CError cError = new CError();
        cError.moduleName = "CertifyFeeOperReportBL";
        cError.functionName = szFunc;
        cError.errorMessage = szErrMsg;
        this.mErrors.addOneError(cError);
    }

  

    private boolean calReport()
    {
    	XmlExport xmlexport = new XmlExport();
		ListTable tlistTable = new ListTable();
		xmlexport.createDocument("GroupOperStat.vts", "printer");
		tlistTable.setName("DATA");
		SQLwithBindVariables sqlbv = new SQLwithBindVariables();
    	String  t_sql=" select t1, t2, t3, t4, t5, t6, t7, t8, t9, t10, t11 " 
        	 +" from (select f.customerno t1,(select name from ldperson where ldperson.customerno = f.customerno) t2,"
        	 +" (select birthday from ldperson where ldperson.customerno = f.customerno) t3,"
        	 +" (select idno from ldperson where ldperson.customerno = f.customerno) t4,"
        	 +" (select contno from lccont where grpcontno = '"+"?tGrpContNo?"+"' and insuredno = f.customerno) t5,"
        	 +" g.rptno t6, (select (case when sum(realpay) is not null then sum(realpay)  else 0 end) from llclaim where clmno = g.rptno) t7, '报案' t8, '' t9,"
        	 +" (select makedate from llregister o where o.rgtno = g.rptno) t10,"
        	 +" (select endcasedate from llregister o where o.rgtno = g.rptno) t11 "
        	 +" from llreport g, llsubreport f where g.rptno = f.subrptno "
        	 +" and not exists (select 1 from llclaim v where g.rptno = v.clmno) and g.grpcontno = '"+"?tGrpContNo?"+"'"
        	 +" union "
        	 +" select f.customerno t1, (select name from ldperson where ldperson.customerno = f.customerno) t2,"
        	 +" (select birthday from ldperson where ldperson.customerno = f.customerno) t3,"
        	 +" (select idno from ldperson where ldperson.customerno = f.customerno) t4,"
        	 +" (select contno from lccont where grpcontno = '"+"?tGrpContNo?"+"' and insuredno = f.customerno) t5,"
        	 +" g.rgtno t6, (select (case when sum(realpay) is not null then sum(realpay)  else 0 end) from llclaim where clmno = g.rgtno) t7,"
        	 +" (case g.clmstate when '10' then  '报案' when '20' then '立案' when '30' then '审核' when '40' then '审批'"
        	 +" when '50' then '结案' when '60' then '完成' when '70' then '关闭' end) t8,'' t9,"
        	 +" g.makedate t10, g.endcasedate t11 "
        	 +" from llregister g, llcase f where g.rgtno = f.caseno and not exists "
        	 +" (select 1 from llclaimuwmain v where g.rgtno = v.clmno) and g.grpcontno = '"+"?tGrpContNo?"+"'"
        	 +"  union "
        	 +" select f.customerno t1, (select name from ldperson where ldperson.customerno = f.customerno) t2," 
        	 +" (select birthday from ldperson where ldperson.customerno = f.customerno) t3,"
        	 +" (select idno from ldperson where ldperson.customerno = f.customerno) t4,"
        	 +" (select contno from lccont where grpcontno = '"+"?tGrpContNo?"+"' and insuredno = f.customerno) t5,"
        	 +"  t.rgtno t6, g.realpay t7,"
        	 +" (case g.clmstate when '10' then '报案' when '20' then '立案' when '30' then '审核'"
        	 +"  when '40' then '审批' when '50' then  '结案' when '60' then '完成' when '70' then '关闭' end) t8,"
        	 +" (case k.auditconclusion  when '0' then  '给付' when '1' then  '拒付' when '2' then  '客户撤案'"
        	 +" when '3' then '公司撤案' when '4' then '给付' when '5' then '理赔回退' end) t9,"
        	 +" t.makedate t10, t.endcasedate t11"
        	 +"  from llregister t, llclaim g, llcase f, llclaimuwmain k"
        	 +" where t.rgtno = g.clmno and g.clmno = f.caseno and f.caseno = k.clmno and t.grpcontno = '"+"?tGrpContNo?"+"') l"
        	 +"  order by t1, t6";
           logger.debug("统计SQL是"+t_sql);
           sqlbv.sql(t_sql);
           sqlbv.put("tGrpContNo", tGrpContNo);
           
        ExeSQL BQExeSQL = new ExeSQL();
   		SSRS BQSSRS = new SSRS();
   		logger.debug(t_sql);
   		BQSSRS = BQExeSQL.execSQL(sqlbv);

   		int tArrLen = BQSSRS.MaxRow;
   		if(tArrLen<1){
   			return false;
   		}
   		for (int i = 1; i <= tArrLen; i++) {
   			String[] strBQ = new String[11] ;
   			for(int k=0;k<strBQ.length;k++){
   				strBQ[k] =  BQSSRS.GetText(i, k+1);
   			}
   			tlistTable.add (strBQ) ;
   		}

   		String[] b_strBQ = new String[11];
   		xmlexport.addListTable(tlistTable, b_strBQ);

   		TextTag texttag = new TextTag(); //新建一个TextTag的实例
   		
   		texttag.add("GrpContNo", tGrpContNo);
   		texttag.add("makeReportTime", PubFun.getCurrentDate());
   		if (texttag.size() > 0) {
   			xmlexport.addTextTag(texttag);
   		}
           
;
           mResult.add(xmlexport);

        mTransferData.setNameAndValue("sysvar", ReportPath + ReportName);
        mResult.add(mTransferData);

        return true;
    }


    public static void main(String[] args)
    {
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return this.mErrors;
	}
}

