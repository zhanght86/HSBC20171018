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

public class BqErrorReasonDeal {
private static Logger logger = Logger.getLogger(BqErrorReasonDeal.class);
	



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
			private String mErrorType = "";	

		    public BqErrorReasonDeal(){ }

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
				mErrorType=(String)tTransferData.getValueByName("ErrorType");
						

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
		        xmlexport.createDocument("BqErrorsReason.vts", "");

		        ListTable tlistTable = new ListTable();
		        tlistTable.setName("BqError");
		        
		        
		        String strSaleChnl = "";

				if (mSaleChnl != null && !mSaleChnl.equals(""))
				{
						strSaleChnl = " and exists (select 1 from lcpol where salechnl='"
												 + "?mSaleChnl?" + "' and contno=a.contno)";
				}

		        ExeSQL tExeSQL = new ExeSQL();
		        SQLwithBindVariables sqlbv1 = new SQLwithBindVariables();
		        String asql =  "select com ,count(type1)+count(type2)+count(type3) from " +
			   		"( select substr(a.managecom,1,'"+"?mStatType?"+"') com ,null type1, (select distinct edoracceptno " +
			   		"from lpcancelreason where a.edoracceptno = edoracceptno ) type2, " +
			   		"(select distinct edoracceptno from lpcancelreason where a.edoracceptno = edoracceptno and reasoncode not in ('chk401', 'chk402')) type3 " +
			   		"from lobedoritem a where managecom like concat('"+"?mManageCom?"+"','%') and grpcontno = '00000000000000000000' " 
			   		+" and exists(select '1' from lobedormain where a.edorno=edorno and a.contno=contno  "
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
			   		+")"
		               + strSaleChnl +
			   		"union all " +
			   		"select substr(a.managecom,1,'"+"?mStatType?"+"') com ,(case edortype when 'RB' then edoracceptno else null end) type1, null type2,null type3 " +
			   		"from lpedoritem a where managecom like concat('"+"?mManageCom?"+"','%') and edorstate = '0' and grpcontno = '00000000000000000000'" +
			   	
			   		"and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
					+ ReportPubFun.getWherePart("makedate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
			   		+")"
		              + strSaleChnl +") group by com order by com" ;
		        sqlbv1.sql(asql);
		        sqlbv1.put("mStatType", mStatType);
		        sqlbv1.put("mManageCom", mManageCom);
		        sqlbv1.put("mSaleChnl", mSaleChnl);
		        sqlbv1.put("mStartDate", mStartDate);
		        sqlbv1.put("mEndDate", mEndDate);
		        SSRS assrs = (SSRS)tExeSQL.execSQL(sqlbv1);
		        if(tExeSQL.mErrors.needDealError() == true || assrs == null || assrs.getMaxRow() <= 0)
		        {
		            CError.buildErr(this, "无数据！");
		            return false;
		        }
		        
		        
		        SQLwithBindVariables sqlbv2 = new SQLwithBindVariables();
		 String bsql = "select com, (case type when '02' then '回退' when '03' then '申请撤销' else '其他' end),count(edoracceptno)from ( " +
		 		"select '02' type , substr(a.managecom, 1, '"+"?mStatType?"+"') com,a.edoracceptno from lpedoritem a where " +
		 		"managecom like concat('"+"?mManageCom?"+"','%') and edorstate = '0' and grpcontno = '00000000000000000000' and exists(select '1' from lpedormain where a.edorno=edorno and a.contno=contno  "
				+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
		 		+")"
	             + strSaleChnl +" and edortype='RB' and '"+"?mErrorType?"+"' in ('01','02')"+
		 		"union all " +
		 		"select '03', substr(a.managecom, 1, '"+"?mStatType?"+"') com,a.edoracceptno from lobedoritem a where" +
		 		" managecom like concat('"+"?mManageCom?"+"','%') and grpcontno = '00000000000000000000' and exists(select '1' from lobedormain where a.edorno=edorno and a.contno=contno  "
				+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
		 		+")" + strSaleChnl+" and exists" +
		 		" (select '1' from lpcancelreason where a.edoracceptno = edoracceptno )and '"+"?mErrorType?"+"' in ('01','03')"+
		 		"union all select '04', substr(a.managecom, 1, '"+"?mStatType?"+"') com,a.edoracceptno from lobedoritem a where " +
		 		"managecom like concat('"+"?mManageCom?"+"','%') and grpcontno = '00000000000000000000' and exists(select '1' from lobedormain where a.edorno=edorno and a.contno=contno  "
				+ ReportPubFun.getWherePart("confdate", ReportPubFun.getParameterStr(mStartDate, "?mStartDate?"),ReportPubFun.getParameterStr(mEndDate, "?mEndDate?"),1)
		 		+")" + strSaleChnl +
		 		" and exists (select 1 from lpcancelreason where a.edoracceptno = edoracceptno and reasoncode " +
		 		"not in ('chk401', 'chk402')) and '"+"?mErrorType?"+"' in ('01','04') ) group by com,type order by com,type    "; 
		 		sqlbv2.sql(bsql);
		 		sqlbv2.put("mStatType", mStatType);
		 		sqlbv2.put("mManageCom", mManageCom);
		 		sqlbv2.put("mStartDate", mStartDate);
		 		sqlbv2.put("mEndDate", mEndDate);
		 		sqlbv2.put("mErrorType", mErrorType);
		        SSRS bssrs = (SSRS)tExeSQL.execSQL(sqlbv2);

		        String[] strArr;
						double tTotal[] = {0,0};
						int n = 1;
						tTotal[1]=	Double.parseDouble(assrs.GetText(n,2));
						
		        for(int i = 1 ; i <= bssrs.getMaxRow() ; i ++)
		        {
		            strArr = new String[4];
		        
		            strArr[0] = ReportPubFun.getMngName(bssrs.GetText(i,1));  //公司名称
		            strArr[1] =  bssrs.GetText(i,2) ;  //差错类型
		            
		            strArr[2] =  bssrs.GetText(i,3) ; 	//差错量
		            if(bssrs.GetText(i,1).equals(assrs.GetText(n,1))){
		          
		              strArr[3] = ReportPubFun.functionJD((Double.parseDouble(bssrs.GetText(i,3)))/Double.parseDouble(assrs.GetText(n,2)),"0.0000"); 	
		           
		            }
		            else {n++;
		            strArr[3] = ReportPubFun.functionJD((Double.parseDouble(bssrs.GetText(i,3)))/Double.parseDouble(assrs.GetText(n,2)),"0.0000");
		            tTotal[1]+=Double.parseDouble(assrs.GetText(n,2));
		            logger.debug("n:"+n);
		            
		            logger.debug("tTotal[1]:"+tTotal[1]);
		            }
		            
		          	
		            tTotal[0]+= Double.parseDouble(bssrs.GetText(i,3));
		          
		         

		            tlistTable.add(strArr);
		        }
		        

		        String[] tArr = new String[4];
		        
		         tArr[0]="合计";
		        tArr[1] = "总类型";
		        tArr[2] = ReportPubFun.functionJD(tTotal[0],"0");
		    
		        tArr[3]=ReportPubFun.functionJD(tTotal[0]/tTotal[1],"0.0000");
		        
		      
		        tlistTable.add(tArr);

		        xmlexport.addListTable(tlistTable, tArr);

		        TextTag texttag = new TextTag(); //新建一个TextTag的实例
		        texttag.add("ManageCom", ReportPubFun.getMngName(mManageCom));
		        texttag.add("StartDate", mStartDate);
		        texttag.add("EndDate", mEndDate);
		        texttag.add("Date", PubFun.getCurrentDate());
		        texttag.add("ErrorType", mErrorType);
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
		



