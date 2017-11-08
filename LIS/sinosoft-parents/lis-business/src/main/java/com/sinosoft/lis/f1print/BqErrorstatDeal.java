package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.ReportPubFun;
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



public class BqErrorstatDeal {
private static Logger logger = Logger.getLogger(BqErrorstatDeal.class);
	
	/**
	 * <p>Title:保全报表</p>
	 * <p>Description: 1张报表</p>
	 * <p>bq1：协议退保原因统计报表</p>
	 * <p>Copyright: Copyright (c) 2003</p>
	 * <p>Company: sinosoft</p>
	 * @author guoxiang
	 * @version 1.0
	 */
	

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

	    public BqErrorstatDeal(){ }

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
	        xmlexport.createDocument("BqErrorstat.vts", "");

	        ListTable tlistTable = new ListTable();
	        tlistTable.setName("BqError");
	        
	        
	        String strSaleChnl = "";

			if (mSaleChnl != null && !mSaleChnl.equals(""))
			{
					strSaleChnl = " and exists (select 1 from lcpol where salechnl='"
											 + "?mSaleChnl?" + "' and contno=a.contno)";
			}

	        ExeSQL tExeSQL = new ExeSQL();
	        SQLwithBindVariables sqlbv = new SQLwithBindVariables();
	        String asql = "select com, count(edoracceptno) from ( " +
    		"select substr(a.managecom,1,'"+"?mStatType?"+"') com ,a.edoracceptno from " +
    		"lpedoritem a where edorstate = '0' and grpcontno = '00000000000000000000' " 
    		+" and  managecom like concat('"+"?mManageCom?"+"','%') and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
			+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
    		+")"
               + strSaleChnl 
    		+"union all select substr(a.managecom,1,'"+"?mStatType?"+"') com ,a.edoracceptno from lobedoritem a where managecom like concat('"+"?mManageCom?"+"','%') and " +
    		"grpcontno = '00000000000000000000' " +
    		"and exists(select '1' from lobedormain where a.edorno=edorno and a.contno=contno  "
			+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
    		+")"
               + strSaleChnl+") group by com order by com  ";
	        sqlbv.sql(asql);
	        sqlbv.put("mStatType", mStatType);
	        sqlbv.put("mManageCom", mManageCom);
	        sqlbv.put("mStartDate", mStartDate);
	        sqlbv.put("mEndDate", mEndDate);
	        sqlbv.put("mSaleChnl", mSaleChnl);
	        SSRS assrs = (SSRS)tExeSQL.execSQL(sqlbv);
	        if(tExeSQL.mErrors.needDealError() == true || assrs == null || assrs.getMaxRow() <= 0)
	        {
	            CError.buildErr(this, "无数据！");
	            return false;
	        }
	        
	        
	        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
	        String bsql = "select com ,count(type1),count(type2),count(type3) from " +
	   		"( select substr(a.managecom,1,'"+"?mStatType?"+"') com ,null type1, (select distinct edoracceptno " +
	   		"from lpcancelreason where a.edoracceptno = edoracceptno ) type2, " +
	   		"(select distinct edoracceptno from lpcancelreason where a.edoracceptno = edoracceptno and reasoncode not in ('chk401', 'chk402')) type3 " +
	   		"from lobedoritem a where managecom like concat('"+"?mManageCom?"+"','%') and grpcontno = '00000000000000000000' " 
	   		+" and exists(select '1' from lobedormain where a.edorno=edorno and a.contno=contno  "
			+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1) +")"
               + strSaleChnl +
	   		"union all " +
	   		"select substr(a.managecom,1,'"+"?mStatType?"+"') com ,(case edortype when 'RB' then edoracceptno else null end) type1, null type2,null type3 " +
	   		"from lpedoritem a where managecom like concat('"+"?mManageCom?"+"','%') and edorstate = '0' and grpcontno = '00000000000000000000'" +
	   	
	   		"and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
			+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1) +")"
              + strSaleChnl+") group by com order by com" ;
	        sqlbv1.sql(bsql);
	        sqlbv1.put("mStatType", mStatType);
	        sqlbv1.put("mManageCom", mManageCom);
	        sqlbv1.put("mStartDate", mStartDate);
	        sqlbv1.put("mEndDate", mEndDate);
	        sqlbv1.put("mSaleChnl", mSaleChnl);
	        SSRS bssrs = (SSRS)tExeSQL.execSQL(sqlbv1);

	        String[] strArr;
					double tTotal[] = {0,0,0,0};
					int n = 1;
	        for(int i = 1 ; i <= assrs.getMaxRow() ; i ++)
	        {
	            strArr = new String[5];
	        
	            strArr[0] = ReportPubFun.getMngName(bssrs.GetText(i,1));  //公司名称
	            strArr[1] =  bssrs.GetText(i,2) ; 
	            
	            strArr[2] =  bssrs.GetText(i,3) ; 	
	            
	            strArr[3] =  bssrs.GetText(i,4) ; 	
	            strArr[4] =ReportPubFun.functionJD((Double.parseDouble(bssrs.GetText(i,2))+Double.parseDouble(bssrs.GetText(i,3))+Double.parseDouble(bssrs.GetText(i,4)))/Double.parseDouble(assrs.GetText(i,2)),"0.0000"); 	
	            tTotal[0] += Double.parseDouble(bssrs.GetText(i,2));
	            tTotal[1] += Double.parseDouble(bssrs.GetText(i,3));
	            tTotal[2] += Double.parseDouble(bssrs.GetText(i,4));
	            tTotal[3]+=Double.parseDouble(assrs.GetText(i,2));

	            tlistTable.add(strArr);
	        }
	        

	        String[] tArr = new String[5];
	        
	         tArr[0]="合计";
	        tArr[1] = ReportPubFun.functionJD(tTotal[0],"0");
	        tArr[2] = ReportPubFun.functionJD(tTotal[1],"0");
	        tArr[3] = ReportPubFun.functionJD(tTotal[2],"0");
	        tArr[4]=ReportPubFun.functionJD((Double.parseDouble(tArr[1])+Double.parseDouble(tArr[2])+Double.parseDouble(tArr[3]))/tTotal[3],"0.0000");
	        
	      
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

	  
	}
	

