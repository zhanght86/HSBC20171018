<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%> 
<%
//程序名称：QuestInput.jsp
//程序功能：问题件录入
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
LDCodeSet tLDCodeSet = new LDCodeSet();
LDCodeDB tLDCodeDB = new LDCodeDB();
%>
<html>
<head>
   <%@page import="com.sinosoft.lis.vschema.*"%>
   <%@page import="com.sinosoft.lis.db.*"%>
   <%@page import="com.sinosoft.service.*"%>
   <%@page import="com.sinosoft.utility.*"%>
   <link href="../common/css/Project.css" rel="stylesheet" type="text/css">
   <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
<body>
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,HealthQ);">
			</td>
			<td class= titleImg>健康问卷</td>   
		</tr>
	</table>
	<div id="HealthQ" class="maxbox" style="display:">
	<table>
<%  String tHealthSQL =" select * from ldcode where codetype = 'QuestionnaireHealth' ";
//  tLDCodeSet = new LDCodeSet();
//	tLDCodeDB = new LDCodeDB();
//	tLDCodeSet = tLDCodeDB.executeQuery(tHealthSQL);
	SSRS ttSSRS = new SSRS();
    TransferData sTransferData=new TransferData();
    sTransferData.setNameAndValue("SQL", tHealthSQL);
    VData sVData = new VData();
    sVData.add(sTransferData);
    BusinessDelegate tBusinessDelegate=BusinessDelegate.getBusinessDelegate();
    if(tBusinessDelegate.submitData(sVData, "execSQL", "ExeSQLUI"))
    {
    	ttSSRS = (SSRS)tBusinessDelegate.getResult().getObjectByObjectName("SSRS", 0);
    }
	
//	for(int i=1;i<=tLDCodeSet.size();i++)
	for(int i=1;i<=ttSSRS.MaxRow;i++)
                {
                	String tCode = ttSSRS.GetText(i,2); //tLDCodeSet.get(i).getCode();
                	String tCodeName = ttSSRS.GetText(i,3); //tLDCodeSet.get(i).getCodeName();
                	String tName ="H"+tCode;
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	%>
                		<td class= common width=25%> <input type=checkbox name='<%=tName%>' id='<%=tCode%>' value='<%=tCodeName%>' ><%=tCodeName %>
                		</td>
                	<%
                	//每4个做换行处理.
                	if(i%3==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
//                	if(i==tLDCodeSet.size())
	                if(i==ttSSRS.MaxRow)
                	{
                		%>
                		</tr>
                		<%

                	}
                }
             %>
        </table>
        </div>
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,OccupationQ);">
			</td>
			<td class= titleImg>职业问卷</td>   
		</tr>
	</table>
	<div id="OccupationQ" class="maxbox1" style="display:''">
	<table>
<%  String tOccupationSQL =" select * from ldcode where codetype = 'QuestionnaireOccup' ";
//  tLDCodeSet = new LDCodeSet();
//	tLDCodeDB = new LDCodeDB();
//	tLDCodeSet = tLDCodeDB.executeQuery(tOccupationSQL);
	SSRS ttSSRS2 = new SSRS();
    TransferData sTransferData2=new TransferData();
    sTransferData2.setNameAndValue("SQL", tOccupationSQL);
    VData sVData2 = new VData();
    sVData2.add(sTransferData2);
    BusinessDelegate tBusinessDelegate2=BusinessDelegate.getBusinessDelegate();
    if(tBusinessDelegate2.submitData(sVData2, "execSQL", "ExeSQLUI"))
    {
    	ttSSRS2 = (SSRS)tBusinessDelegate2.getResult().getObjectByObjectName("SSRS", 0);
    }
	
//	for(int i=1;i<=tLDCodeSet.size();i++)
	for(int i=1;i<=ttSSRS2.MaxRow;i++)
                {
                	String tCode = ttSSRS.GetText(i,2); //tLDCodeSet.get(i).getCode();
                	String tCodeName = ttSSRS.GetText(i,3); //tLDCodeSet.get(i).getCodeName();
                	String tName = "O"+tCode;
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	%>
                		<td class= common width=25%> <input type=checkbox name='<%=tName%>' id='<%=tCode%>' value='<%=tCodeName%>' ><%=tCodeName %>
                		</td>
                	<%
                	//每4个做换行处理.
                	if(i%3==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
//                	if(i==tLDCodeSet.size())
                	if(i==ttSSRS2.MaxRow)
                	{
                		%>
                		</tr>
                		<%

                	}
                }
             %>
        </table>
        </div>
  <table >
		<tr>
			<td class=common>
				<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,FinanceOQ);">
			</td>
			<td class= titleImg>财务及其他问卷</td>   
		</tr>
	</table>
	<div id="FinanceOQ" class="maxbox" style="display:''">
	<table>
<%  String tFinaceOSQL =" select * from ldcode where codetype = 'QuestionnaireF' ";
//  tLDCodeSet = new LDCodeSet();
//	tLDCodeDB = new LDCodeDB();
//	tLDCodeSet = tLDCodeDB.executeQuery(tFinaceOSQL);
	SSRS ttSSRS3 = new SSRS();
    TransferData sTransferData3=new TransferData();
    sTransferData3.setNameAndValue("SQL", tFinaceOSQL);
    VData sVData3 = new VData();
    sVData3.add(sTransferData3);
    BusinessDelegate tBusinessDelegate3=BusinessDelegate.getBusinessDelegate();
    if(tBusinessDelegate3.submitData(sVData3, "execSQL", "ExeSQLUI"))
    {
    	ttSSRS3 = (SSRS)tBusinessDelegate3.getResult().getObjectByObjectName("SSRS", 0);
    }
	
//	for(int i=1;i<=tLDCodeSet.size();i++)
	for(int i=1;i<=ttSSRS3.MaxRow;i++)
                {
                	String tCode = ttSSRS3.GetText(i,2); //tLDCodeSet.get(i).getCode();
                	String tCodeName = ttSSRS3.GetText(i,3); //tLDCodeSet.get(i).getCodeName();
                	String tName = "F"+tCode;
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	%>
                		<td class= common width=25%> <input type=checkbox name='<%=tName%>' id='<%=tCode%>' value='<%=tCodeName%>' ><%=tCodeName %>
                		</td>
                	<%
                	//每4个做换行处理.
                	if(i%3==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
//                	if(i==tLDCodeSet.size())
                	if(i==ttSSRS3.MaxRow)
                	{
                		%>
                		</tr>
                		<%

                	}
                }
             %>
        </table>
        </div>
  
</body>
</html>
