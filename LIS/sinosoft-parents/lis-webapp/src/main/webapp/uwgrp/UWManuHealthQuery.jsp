<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuHealthQuery.jsp
//程序功能：人工核保体检资料查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";

	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		out.println("session has expired");
		return;
	}
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
  	loggerDebug("UWManuHealthQuery","----------------------------------qqq-------------------");
 	LCPolSchema tLCPolSchema = new LCPolSchema();
	LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
	LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");

	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("Edate");
	String tNote = request.getParameter("Note");
	
	String thealthcode[] = request.getParameterValues("HealthGrid1");
	String thealthname[] = request.getParameterValues("HealthGrid2");
	String tChk[] = request.getParameterValues("InpHealthGridChk");
	
	loggerDebug("UWManuHealthQuery","Contno:"+tContNo);
	loggerDebug("UWManuHealthQuery","hospital:"+tHospital);
	loggerDebug("UWManuHealthQuery","note:"+tNote);
	loggerDebug("UWManuHealthQuery","ifempty:"+tIfEmpty);
	loggerDebug("UWManuHealthQuery","insureno:"+tInsureNo);
	
	boolean flag = true;
	int ChkCount = 0;
	//ChkCount = tChk.length;
	//loggerDebug("UWManuHealthQuery","chkcount:"+ChkCount);
	if ( tContNo.equals("")||tInsureNo.equals(""))
	{
		Content = "条件录入不完整!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("UWManuHealthQuery","111");
	}
	else
	{
		loggerDebug("UWManuHealthQuery","222");
		
 		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
 			
		//保单
		tLCPolSchema.setContNo(tContNo);
		tLCPolSchema.setInsuredNo(tInsureNo);
				    
	    	//tLCPolSet.add( tLCPolSchema );
       loggerDebug("UWManuHealthQuery","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCPolSchema );
		tVData.add( tG );		
				
		// 数据传输
		 String busiName="cbcheckgrpUWAutoHealthQueryUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		UWAutoHealthQueryUI tUWAutoHealthQueryUI   = new UWAutoHealthQueryUI();
		if (tUWAutoHealthQueryUI.submitData(tVData,"INSERT") == false)
		{
			int n = tUWAutoHealthQueryUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 自动核保失败，原因是: " + tUWAutoHealthQueryUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
				tVData.clear();
				tVData = tUWAutoHealthQueryUI.getResult();
		
				// 显示
				LDHealthSet mLDHealthSet = new LDHealthSet(); 
				mLDHealthSet.set((LDHealthSet)tVData.getObjectByObjectName("LDHealthSet",0));
				
				loggerDebug("UWManuHealthQuery","size:"+mLDHealthSet.size());
%>
				<script language="javascript">					
					//parent.fraInterface.HealthGrid.clearData ();				
				</script>         				
<%
				for (int j = 1;j <= mLDHealthSet.size();j++)
				{
					LDHealthSchema mLDHealthSchema = new LDHealthSchema();			
					mLDHealthSchema = mLDHealthSet.get(j);
												
					loggerDebug("UWManuHealthQuery","code:"+mLDHealthSchema.getHealthCode().trim());
					loggerDebug("UWManuHealthQuery","name:"+mLDHealthSchema.getHealthName().trim());
					loggerDebug("UWManuHealthQuery","j:"+j);
%>
					<script language="javascript">					
						parent.fraInterface.HealthGrid.addOne();
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,1,"<%=mLDHealthSchema.getHealthCode()%>");
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,2,"<%=mLDHealthSchema.getHealthName()%>");
                    			</script>         
<%
				}
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tUWAutoHealthQueryUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	Content = " 人工核保成功! ";
		    	FlagStr = "Succ";
		    }
		    else                                                                           
		    {
		    	Content = " 人工核保失败，原因是:" + tError.getFirstError();
		    	FlagStr = "Fail";
		    }
		}
	}
	} 
%>

