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
  <%@page import="com.sinosoft.lis.tb.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
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

  	//接收信息
  	// 投保单列表
  	loggerDebug("BQManuHealthQuery","----------------------------------qqq-------------------");
	LPPENoticeSet tLPPENoticeSet = new LPPENoticeSet();
	LPPENoticeItemSet tLPPENoticeItemSet = new LPPENoticeItemSet();
	
	String tContNo = request.getParameter("ContNo");
	String tInsureNo = request.getParameter("InsureNo");

	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("Edate");
	String tNote = request.getParameter("Note");
	
	String tChk[] = request.getParameterValues("InpHealthGridChk");
	
	loggerDebug("BQManuHealthQuery","Contno:"+tContNo);
	loggerDebug("BQManuHealthQuery","hospital:"+tHospital);
	loggerDebug("BQManuHealthQuery","note:"+tNote);
	loggerDebug("BQManuHealthQuery","ifempty:"+tIfEmpty);
	loggerDebug("BQManuHealthQuery","insureno:"+tInsureNo);
	
	boolean flag = true;
	int ChkCount = 0;
	//ChkCount = tChk.length;
	//loggerDebug("BQManuHealthQuery","chkcount:"+ChkCount);
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("ContNo",tContNo);
	tTransferData.setNameAndValue("InsuredNo",tInsureNo);
	if ( tContNo.equals("")||tInsureNo.equals(""))
	{
		Content = "条件录入不完整!";
		FlagStr = "Fail";
		flag = false;
		loggerDebug("BQManuHealthQuery","111");
	}
	else
	{
		loggerDebug("BQManuHealthQuery","222");
		
 		LPPENoticeSchema tLPPENoticeSchema = new LPPENoticeSchema();
 			
		//保单
				   
	    	//tLCPolSet.add( tLCPolSchema );
       loggerDebug("BQManuHealthQuery","flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		//tVData.add( tLCPolSchema );
		tVData.add(tTransferData);
		tVData.add( tG );		
				
		// 数据传输
		BQAutoHealthQueryUI tBQAutoHealthQueryUI   = new BQAutoHealthQueryUI();
		if (tBQAutoHealthQueryUI.submitData(tVData,"INSERT") == false)
		{
			int n = tBQAutoHealthQueryUI.mErrors.getErrorCount();
			for (int i = 0; i < n; i++)
			Content = " 自动核保失败，原因是: " + tBQAutoHealthQueryUI.mErrors.getError(0).errorMessage;
			FlagStr = "Fail";
		}
		else
		{
				tVData.clear();
				tVData = tBQAutoHealthQueryUI.getResult();
		
				// 显示
				LDHealthSet mLDHealthSet = new LDHealthSet(); 
				mLDHealthSet.set((LDHealthSet)tVData.getObjectByObjectName("LDHealthSet",0));
				
				loggerDebug("BQManuHealthQuery","size:"+mLDHealthSet.size());
%>
      				
<%
				String tHealthSubCodeAll = ""; 
				String tHealthMainCode = "";
				for (int j = 1;j <= mLDHealthSet.size();j++)
				{
					LDHealthSchema mLDHealthSchema = new LDHealthSchema();			
					mLDHealthSchema = mLDHealthSet.get(j);
					if(mLDHealthSchema.getHealthCode()==null||mLDHealthSchema.getHealthCode().trim().equals(""))
					{
						continue;
					}
					loggerDebug("BQManuHealthQuery","code:"+mLDHealthSchema.getHealthCode().trim());
					loggerDebug("BQManuHealthQuery","name:"+mLDHealthSchema.getHealthName().trim());
					loggerDebug("BQManuHealthQuery","j:"+j);
					tHealthSubCodeAll = tHealthSubCodeAll + mLDHealthSchema.getSubHealthCode();
					if(j==1)
					{
						tHealthMainCode = mLDHealthSchema.getHealthCode();
						tHealthSubCodeAll = tHealthSubCodeAll + "#";
					}
%>						
					<%
				}
				//tHealthMainCode = "PEG001";
				//tHealthSubCodeAll = "PE001#PE002#PE003";
				if(!tHealthMainCode.equals(""))
				{
				%>
				<script language="javascript">
						parent.fraInterface.checkMainHealthCode('<%=tHealthMainCode%>');				
						parent.fraInterface.showBodyCheck('<%=tHealthSubCodeAll%>');
					//	parent.fraInterface.getAllChecked();
                    			</script>        
				<%
				}
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBQAutoHealthQueryUI.mErrors;
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

