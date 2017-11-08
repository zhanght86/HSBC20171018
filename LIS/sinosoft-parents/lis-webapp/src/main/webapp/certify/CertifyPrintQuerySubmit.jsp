<%
//程序名称：CertifyPrintQuerySubmit.jsp
//程序功能：
//创建日期：2002-10-14 10:20:50
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.certify.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.Hashtable"%>
<%@page import="com.sinosoft.service.*" %>
  
<%@include file="../common/jsp/UsrCheck.jsp"%>
  
<%!
	String buildMsg(boolean bFlag, String strMsg) {
		String strReturn = "";
		
		strReturn += "<script language=\"javascript\">";
		
		if( bFlag ) {
			strReturn += "  parent.fraInterface.afterSubmit('Succ', '操作成功完成');";
		} else {
			strReturn += "  parent.fraInterface.afterSubmit('Fail','" + strMsg + "');";
		}
		strReturn += "</script>";
		
		return strReturn;
	}
%>

<html>

<%
	try {
		GlobalInput globalInput = new GlobalInput();
		
		globalInput.setSchema( (GlobalInput)session.getValue("GI") );
	
	  LZCardPrintSchema schemaLZCardPrint   = new LZCardPrintSchema();
	
	  schemaLZCardPrint.setPrtNo(request.getParameter("PrtNo"));
	  schemaLZCardPrint.setCertifyCode(request.getParameter("CertifyCode"));
	  schemaLZCardPrint.setRiskCode(request.getParameter("RiskCode"));
	  schemaLZCardPrint.setRiskVersion(request.getParameter("RiskVersion"));
	  schemaLZCardPrint.setSubCode(request.getParameter("SubCode"));
	  schemaLZCardPrint.setMaxMoney(request.getParameter("MaxMoney"));
	  schemaLZCardPrint.setMaxDate(request.getParameter("MaxDate"));
	  schemaLZCardPrint.setComCode(request.getParameter("ComCode"));
	  schemaLZCardPrint.setPhone(request.getParameter("Phone"));
	  schemaLZCardPrint.setLinkMan(request.getParameter("LinkMan"));
	  schemaLZCardPrint.setCertifyPrice(request.getParameter("CertifyPrice"));
	  schemaLZCardPrint.setManageCom(request.getParameter("ManageCom"));
	  schemaLZCardPrint.setOperatorInput(request.getParameter("OperatorInput"));
	  schemaLZCardPrint.setInputDate(request.getParameter("InputDate"));
	  schemaLZCardPrint.setInputMakeDate(request.getParameter("InputMakeDate"));
	  schemaLZCardPrint.setGetMan(request.getParameter("GetMan"));
	  schemaLZCardPrint.setGetDate(request.getParameter("GetDate"));
	  schemaLZCardPrint.setOperatorGet(request.getParameter("OperatorGet"));
	  schemaLZCardPrint.setStartNo(request.getParameter("StartNo"));
	  schemaLZCardPrint.setEndNo(request.getParameter("EndNo"));
	  schemaLZCardPrint.setGetMakeDate(request.getParameter("GetMakeDate"));
	  schemaLZCardPrint.setSumCount(request.getParameter("SumCount"));
	  schemaLZCardPrint.setState(request.getParameter("State"));
	  
	  String strWhere = request.getParameter("sql_where");
	
	  // 准备传输数据 VData
	  VData tVData = new VData();
	
		tVData.addElement(schemaLZCardPrint);
		tVData.add(globalInput);
		tVData.add(strWhere);

		Hashtable hashParams = new Hashtable();
		hashParams.put("CertifyClass", CertifyFunc.CERTIFY_CLASS_CERTIFY);
		tVData.add( hashParams );
		
	  // 数据传输
	  /*CardPrintUI tCardPrintUI = new CardPrintUI();

		if (!tCardPrintUI.submitData(tVData, "QUERY||MAIN")) {
			out.println( buildMsg(false, "查询失败，原因是:" + tCardPrintUI.mErrors.getFirstError()));
			return;
		
		} else {
			tVData.clear();
			tVData = tCardPrintUI.getResult();
			
			// 显示
			LZCardPrintSet mLZCardPrintSet = new LZCardPrintSet();
			mLZCardPrintSet.set((LZCardPrintSet)tVData.getObjectByObjectName("LZCardPrintSet",0));
	    out.println("<script language=javascript>");
	    out.println("function getGridResult()");
	    out.println("{");
	    out.println("parent.fraInterface.arrStrReturn[0]=\"0|" + String.valueOf(mLZCardPrintSet.size()) + "^" + mLZCardPrintSet.encode()+"\";");
	    out.println("}");
	    out.println("</script>");
		} // end of if*/
		
		String busiName="CardPrintUI";
		String mDescType="查询";
		BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		  if(!tBusinessDelegate.submitData(tVData,"QUERY||MAIN",busiName))
		  {    
		       if(tBusinessDelegate.getCErrors()!=null&&tBusinessDelegate.getCErrors().getErrorCount()>0)
		       { 
		    	   out.println( buildMsg(false, mDescType+"失败，原因是:" + tBusinessDelegate.getCErrors().getFirstError()));
		    	   return;
			   }
			   else
			   {
				   out.println( buildMsg(false, mDescType+"失败"));
				   return;			
			   }
		  }
		  else
		  {
			  tVData.clear();
				tVData = tBusinessDelegate.getResult();
				
				// 显示
				LZCardPrintSet mLZCardPrintSet = new LZCardPrintSet();
				mLZCardPrintSet.set((LZCardPrintSet)tVData.getObjectByObjectName("LZCardPrintSet",0));
			    out.println("<script language=javascript>");
			    out.println("function getGridResult()");
			    out.println("{");
			    out.println("parent.fraInterface.arrStrReturn[0]=\"0|" + String.valueOf(mLZCardPrintSet.size()) + "^" + mLZCardPrintSet.encode()+"\";");
			    out.println("}");
			    out.println("</script>");  
		  }
		
	} catch (Exception ex) {
		ex.printStackTrace();
		out.println( buildMsg(false, "发生异常") );
		return;
	}
	
	out.println( buildMsg(true, "查询成功") );
%>

</html>
