<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<!--用户校验类-->
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.tb.*"%>
<%@page import="java.lang.reflect.*"%>
<%@page import="java.util.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>     
<%@page import="com.sinosoft.utility.*"%>

<%
//loggerDebug("ProposalQueryRiskPlan","asdfasdfas");
  //输出参数
  CErrors tError = null;
  String FlagStr = "Fail";
  String Content = "";
  boolean oneDutyFlag=false;
  
  LCDutySchema oneDuty = new LCDutySchema();
  LCDutySet mLCDutySet = new LCDutySet();
  String tContPlanCode=request.getParameter("ContPlanCode");
  LCContPlanRiskSchema tLCContPlanRiskSchema=new LCContPlanRiskSchema();
  tLCContPlanRiskSchema.setProposalGrpContNo(request.getParameter("ProposalGrpContNo"));
  tLCContPlanRiskSchema.setContPlanCode(request.getParameter("ContPlanCode"));
  tLCContPlanRiskSchema.setRiskCode(request.getParameter("RiskCode"));
  tLCContPlanRiskSchema.setMainRiskCode(request.getParameter("MainRiskCode"));
  loggerDebug("ProposalQueryRiskPlan","mainRiskCode:"+request.getParameter("MainRiskCode"));
%>
<html>      
<head>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
</head>
<body>
<script language="javascript">
	
    try 
    {
    	parent.fraInterface.getRiskInput("<%=tLCContPlanRiskSchema.getRiskCode()%>", parent.fraInterface.LoadFlag);   
       
    }
    catch(ex) {}
    
</script>
<%  
  VData tVData = new VData();
  tVData.addElement( tLCContPlanRiskSchema );
  // 数据传输
  ContPlanQryUI tContPlanQryUI   = new ContPlanQryUI();
  if (!tContPlanQryUI.submitData( tVData, "QUERY||DETAIL" )) 
  {    
      Content = "查询指定保险计划信息失败，原因是: " + tContPlanQryUI.mErrors.getError(0).errorMessage;
      loggerDebug("ProposalQueryRiskPlan",Content);
      FlagStr = "Fail";
%>
			<script language="javascript">
				alert(<%=Content%>);
			</script>
			<%
  }
  else
  {
  	loggerDebug("ProposalQueryRiskPlan","+++++++++++++++++++++++Succ");
  	tVData.clear();
    tVData = tContPlanQryUI.getResult();
    
    TransferData tTransferData = tContPlanQryUI.getTransferData();
    if (tVData.getObjectByObjectName("LCDutySchema",0)!=null)
    {
    	oneDuty=(LCDutySchema)tVData.getObjectByObjectName("LCDutySchema",0);
    	
    	oneDutyFlag=true;
    }
    else
    {
    	mLCDutySet=(LCDutySet)tVData.getObjectByObjectName("LCDutySet",0);
    }

		//如果只有一个责任的时候，给界面上的元素赋值
		if (oneDutyFlag)  
		{
		  //取得年金类型
		  Vector tVector = tTransferData.getValueNames();
		  if(tVector.size()>0){
		    
		    for (int i=0;i<tVector.size();i++){
		    	loggerDebug("ProposalQueryRiskPlan","vector1:"+tVector.get(i).toString());
		  %>
		  <script>
		  
		  try{
		    eval("parent.fraInterface.fm.all('"+"<%=tVector.get(i).toString()%>"+"').value="+"<%=tTransferData.getValueByName(tVector.get(i))%>");
		  }
		  catch(ex){};
		  </script>
		  <%
		    
                    } 
		  }
		  
			int elementsNum;
    	Class c = oneDuty.getClass();
    	Field f[] = c.getDeclaredFields();
			%>
			<script language="javascript">
			<%
    	for(elementsNum=0; elementsNum<f.length; elementsNum++) 
    	{
        //loggerDebug("ProposalQueryRiskPlan","name:"+f[elementsNum].getName()+"value:"+oneDuty.getV(f[elementsNum].getName()));
        if (!oneDuty.getV(f[elementsNum].getName()).equals("null")&&!oneDuty.getV(f[elementsNum].getName()).equals("0.0")) 
      	{ 
      	  if(f[elementsNum].getName().equals("PayIntv")){
      	    //loggerDebug("ProposalQueryRiskPlan","payIntv:"+oneDuty.getV(f[elementsNum].getName()));
      	    continue;
      	  }
      	  out.println("try{");
      	  out.println("parent.fraInterface.fm.all('" + f[elementsNum].getName() + "').value='" + oneDuty.getV(f[elementsNum].getName()) + "';");
      	  out.println("} catch(ex) {};");
      	  
      	}
    	}
    	%>
			</script>
			<%
		}
		//如果有多个责任，给责任列表赋值
		else if(mLCDutySet.size()>0)
		{
			for (int i=1;i<=mLCDutySet.size();i++)
			{

				LCDutySchema tLCDutySchema=mLCDutySet.get(i);
				ExeSQL tExeSQL=new ExeSQL();
				String dutyname=tExeSQL.getOneValue("select dutyname from lmduty where dutycode='"+tLCDutySchema.getDutyCode()+"'");
				loggerDebug("ProposalQueryRiskPlan","tLCDutySchema.getAmnt()"+tLCDutySchema.getAmnt());
				loggerDebug("ProposalQueryRiskPlan","浮动费率："+tLCDutySchema.getGetRate());
				%>
				<script>
				try
				{
			
					for (j=0;j<parent.fraInterface.DutyGrid.mulLineCount ;j++)
					{
					    if (parent.fraInterface.DutyGrid.getRowColData(j,1)==<%=StrTool.cTrim(tLCDutySchema.getDutyCode())%>)
					    {
					    
					        parent.fraInterface.DutyGrid.checkBoxSel(j+1);  
					        parent.fraInterface.DutyGrid.setRowColData(j,1,'<%=StrTool.cTrim(tLCDutySchema.getDutyCode())%>');					       
					        parent.fraInterface.DutyGrid.setRowColData(j,2,'<%=dutyname%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,3,'<%=tLCDutySchema.getInsuYear()%>');					      
					        parent.fraInterface.DutyGrid.setRowColData(j,4,'<%=StrTool.cTrim(tLCDutySchema.getInsuYearFlag())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,5,'<%=tLCDutySchema.getPayEndYear()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,6,'<%=StrTool.cTrim(tLCDutySchema.getPayEndYearFlag())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,7,'<%=StrTool.cTrim(tLCDutySchema.getGetYearFlag())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,8,'<%=tLCDutySchema.getGetYear()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,9,'<%=StrTool.cTrim(tLCDutySchema.getStandbyFlag1())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,10,'<%=StrTool.cTrim(tLCDutySchema.getStandbyFlag2())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,11,'<%=StrTool.cTrim(tLCDutySchema.getStandbyFlag3())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,12,'<%=StrTool.cTrim(tLCDutySchema.getPremToAmnt())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,13,'<%=tLCDutySchema.getPrem()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,14,'<%=tLCDutySchema.getAmnt()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,15,'<%=tLCDutySchema.getMult()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,16,'<%=StrTool.cTrim(tLCDutySchema.getCalRule())%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,17,'<%=tLCDutySchema.getFloatRate()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,18,'<%=tLCDutySchema.getPayIntv()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,19,'<%=tLCDutySchema.getGetLimit()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,20,'<%=tLCDutySchema.getGetRate()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,21,'<%=tLCDutySchema.getSSFlag()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,22,'<%=tLCDutySchema.getPeakLine()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,26,'<%=tLCDutySchema.getGetStartType()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,27,'<%=tLCDutySchema.getAscriptionRuleCode()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,28,'<%=tLCDutySchema.getPayRuleCode()%>');
					        parent.fraInterface.DutyGrid.setRowColData(j,29,'<%=tLCDutySchema.getBonusGetMode()%>');
					        
					    }
					}
				}
				catch(ex)
				{}
				</script>
				<%

    	    }
		}
	%>
	<script>
	  //parent.fraInterface.showFloatRate("<%=request.getParameter("ContPlanCode")%>");
	</script>
	<%
  }
%>
