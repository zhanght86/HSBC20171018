
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PEdorUWManuHealthQuery.jsp
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
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
 	LCPolSchema tLCPolSchema = new LCPolSchema();
	LCPENoticeSet tLCPENoticeSet = new LCPENoticeSet();
	LCPENoticeItemSet tLCPENoticeItemSet = new LCPENoticeItemSet();
	
	String tProposalNo = request.getParameter("PolNo");
	String tInsureNo = request.getParameter("InsureNo");
	//tInsureNo = "86110020030990000863";
	String tHospital = request.getParameter("Hospital");
	String tIfEmpty = request.getParameter("IfEmpty");
	String tEDate = request.getParameter("Edate");
	String tNote = request.getParameter("Note");
	
	String thealthcode[] = request.getParameterValues("HealthGrid1");
	String thealthname[] = request.getParameterValues("HealthGrid2");
	String tChk[] = request.getParameterValues("InpHealthGridChk");
	
	System.out.println("polno:"+tProposalNo);
	System.out.println("hospital:"+tHospital);
	System.out.println("note:"+tNote);
	System.out.println("ifempty:"+tIfEmpty);
	System.out.println("insureno:"+tInsureNo);
	
	boolean flag = true;
	int ChkCount = 0;
	//ChkCount = tChk.length;
	//System.out.println("chkcount:"+ChkCount);
	if ( tProposalNo.equals("")||tInsureNo.equals(""))
	{
		Content = "条件录入不完整!";
		FlagStr = "Fail";
		flag = false;
		System.out.println("111");
	}
	else
	{
		System.out.println("222");
		
 		LCPENoticeSchema tLCPENoticeSchema = new LCPENoticeSchema();
 			
		//保单
		tLCPolSchema.setPolNo( tProposalNo);
		tLCPolSchema.setProposalNo(tProposalNo);
		tLCPolSchema.setInsuredNo(tInsureNo);
				    
	    	//tLCPolSet.add( tLCPolSchema );
       System.out.println("flag:"+flag);
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCPolSchema );
		tVData.add( tG );		
				
		// 数据传输
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
				
				System.out.println("size:"+mLDHealthSet.size());
%>
				<script language="javascript">					
					parent.fraInterface.HealthGrid.clearData ();				
				</script>         				
<%
				for (int j = 1;j <= mLDHealthSet.size();j++)
				{
					LDHealthSchema mLDHealthSchema = new LDHealthSchema();			
					mLDHealthSchema = mLDHealthSet.get(j);
												
					System.out.println("code:"+mLDHealthSchema.getHealthCode().trim());
					System.out.println("name:"+mLDHealthSchema.getHealthName().trim());
					System.out.println("j:"+j);
%>
					<script language="javascript">					
						parent.fraInterface.HealthGrid.addOne();
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,1,"<%=mLDHealthSchema.getHealthCode()%>");
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,2,"<%=mLDHealthSchema.getHealthName()%>");
						parent.fraInterface.HealthGrid.setRowColData(<%=j-1%>,3,"N");
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

