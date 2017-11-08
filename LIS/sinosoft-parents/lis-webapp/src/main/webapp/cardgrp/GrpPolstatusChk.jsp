<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：PolStatusChk.jsp
//程序功能：保单状态查询
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.tbgrp.*"%>
  <%@page import="com.sinosoft.lis.cardgrp.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
  <%@page import="com.sinosoft.service.*" %>
<%
         
  //输出参数
  CErrors tError = null;
  //CErrors tErrors = new CErrors();
  String FlagStr = "Fail";
  String Content = "";
  String grpcontno="";    
	GlobalInput tG = new GlobalInput();
  
	tG=(GlobalInput)session.getValue("GI");
  
  	if(tG == null) {
		loggerDebug("GrpPolstatusChk","session has expired");
		return;
	}
  
  //校验处理
  //内容待填充
  
  	//接收信息
  	// 投保单列表
	LCGrpContSet tLCGrpContSet = new LCGrpContSet();

	
	String tGrpContNo[] = request.getParameterValues("PolGrid1");
	//String tProposalGrpContNo[] = request.getParameterValues("PolGrid2");
	String tSel[] = request.getParameterValues("InpPolGridSel");
	boolean flag = false;
	int proposalCount= tGrpContNo.length;
			
	for (int i = 0; i < proposalCount; i++)
	{
		if (!tGrpContNo[i].equals("") && tSel[i].equals("1"))
		{
			loggerDebug("GrpPolstatusChk","ProposalNo:"+i+":"+tGrpContNo[i]);
	  		LCGrpContSchema tLCGrpContSchema = new LCGrpContSchema();
	
		    tLCGrpContSchema.setGrpContNo( tGrpContNo[i] );
		    grpcontno=tGrpContNo[i];
		    loggerDebug("GrpPolstatusChk","************"+grpcontno);
		    //tLCGrpContSchema.setProposalGrpContNo( tProposalGrpContNo[i] );
		    
	    
		    tLCGrpContSet.add( tLCGrpContSchema );
		    flag = true;
		}
	}

try
{
  	if (flag == true)
  	{
		// 准备传输数据 VData
		VData tVData = new VData();
		tVData.add( tLCGrpContSet );
		tVData.add( tG );
		
		// 数据传输
		   String busiName="cardgrpGrpPolStatusChkUI";
   BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
		//GrpPolStatusChkUI tGrpPolStatusChkUI   = new GrpPolStatusChkUI();
		if (tBusinessDelegate.submitData(tVData,"INSERT",busiName) == false)
		{
			int n = tBusinessDelegate.getCErrors().getErrorCount();
			for (int i = 0; i < n; i++)
			loggerDebug("GrpPolstatusChk","Error: "+tBusinessDelegate.getCErrors().getError(i).errorMessage);
			Content = " 查询失败，原因是: " + tBusinessDelegate.getCErrors().getError(0).errorMessage;
			FlagStr = "Fail";
		}
		//如果在Catch中发现异常，则不从错误类中提取错误信息
		if (FlagStr == "Fail")
		{
		    tError = tBusinessDelegate.getCErrors();
		    //tErrors = tGrpPolStatusChkUI.mErrors;
		    if (!tError.needDealError())
		    {                          
		    	FlagStr = "Succ";
		    	
		    	LMCalModeSet tLMCalModeSet = new LMCalModeSet();
		    	VData tResult = tBusinessDelegate.getResult();
		    	if(tResult != null)
		    	{
		    		tLMCalModeSet = (LMCalModeSet)tResult.getObjectByObjectName("LMCalModeSet",0);
		    	}
		    	
		    	if(tLMCalModeSet.size() > 0)
		    	{
%>
				<script language="javascript">					
					parent.fraInterface.PolStatuGrid.clearData ();				
				</script>         				
<%
		    	
		    		for(int i = 1;i <= tLMCalModeSet.size();i++)
		    		{
		    			LMCalModeSchema tLMCalModeSchema = new LMCalModeSchema();
		    			tLMCalModeSchema = tLMCalModeSet.get(i);
		    			//查询lwmission 
		    ExeSQL tExeSQL = new ExeSQL();
        String tSql = "select lastoperator,nvl(defaultoperator,'公共池') from lwmission where missionprop1='" + grpcontno +"'";
        SSRS pSSRS = new SSRS();
        String tname = "";
        String tname1 = "";
        pSSRS = tExeSQL.execSQL(tSql);
        if (pSSRS.MaxRow == 0)
        {
           
        }else{
          tname=pSSRS.GetText(1, 1);
          tname1=pSSRS.GetText(1, 2);
        }
%>
					<script language="javascript">					
						parent.fraInterface.PolStatuGrid.addOne();
						parent.fraInterface.PolStatuGrid.setRowColData(<%=i-1%>,1,"<%=tLMCalModeSchema.getRemark()%>");		
						parent.fraInterface.PolStatuGrid.setRowColData(<%=i-1%>,2,"<%=tname%>");	
						parent.fraInterface.PolStatuGrid.setRowColData(<%=i-1%>,3,"<%=tname1%>");				
                    			</script>         
<%		    			
		    		}
		    	}
		    }
		    else                                                                           
		    {
		    	FlagStr = "Fail";
		    }
		}
	}
	else
	{
		Content = "请选择保单！";
	}  
}
catch(Exception e)
{
	e.printStackTrace();
	Content = Content.trim() +" 提示:异常退出.";
}
%>                      
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
