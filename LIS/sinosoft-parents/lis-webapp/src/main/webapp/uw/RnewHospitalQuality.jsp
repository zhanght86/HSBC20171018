<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.cbcheck.UWHospitalQualityRecordUI"%>
<%@page import="com.sinosoft.utility.*"%>
<%
//程序名称：UWCustomerQuality.jsp
//程序功能：客户品质管理
//创建日期：2005-06-18 11:10:36
//创建人  ：ccvip
//更新记录：  更新人    更新日期     更新原因/内容
%>
<html>
<%
  //个人下个人
	String tGrpPolNo = "00000000000000000000";
	String tContNo = "00000000000000000000";
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	//初始化信息
	UWHospitalQualityRecordUI tUWHospitalQualityRecordUI = new UWHospitalQualityRecordUI();
	tUWHospitalQualityRecordUI.submitData(new VData(),"INIT");
	VData mInitData = new VData();
	mInitData = tUWHospitalQualityRecordUI.getResult();
	//SSRS tSSRS_Score= (SSRS)mInitData.get(0);
	//SSRS tSSRS_Manage = (SSRS)mInitData.get(1);
	//SSRS tSSRS_Inner = (SSRS)mInitData.get(2);
	SSRS tSSRS_Quality =(SSRS)mInitData.get(0);
	
%>

<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
	
	var ContNo = "<%=request.getParameter("ContNo")%>";
	var tContNo = "<%=request.getParameter("ContNo")%>";
	//alert("ContNo="+ContNo);
	var PrtNo = "<%=request.getParameter("PrtNo")%>";
	var QueryFlag = "<%=request.getParameter("QueryFlag")%>";
//	var OperatePos = "<%=request.getParameter("OperatePos")%>";
</script>

<head >
<title>体检医院品质管理 </title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  
  <SCRIPT src="RnewHospitalQuality.js"></SCRIPT>
  <%@include file="RnewHospitalQualityInit.jsp"%>
  
</head>

<body  onload="initForm(ContNo);" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./RnewHospitalQualitySave.jsp">
   <br>  
   <div class="maxbox1">
    <table class= common>
    	<TR  class= common>
    	  
    	  <td class=title>
           请选择体检医院
         </td>                
          <TD  class= input> <Input class=code readonly name=HospitalCode id="HospitalCode" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="showCodeListEx('HospitalCode',[this],[0,1],null,null,null,1);" ondblClick="showCodeListEx('HospitalCode',[this],[0,1],null,null,null,1);" 
          onkeyup="showCodeListKeyEx('HospitalCode',[this],[0,1],null,null,null,1);"></TD>
        <td class=title></td>
         <td class= input></td>
         <td class=title></td>
         <td class= input></td>
      </TR>
    
    </table>
    </div>
    <div class="maxbox">
    <table class= common>
    	<!-- <hr class="line"> -->
  <TR  class= common>
    <TD  class= title>
      体检医院编码
    </TD>
    <TD  class= input>
        <Input class= 'common wid' name=HospitalCode1 id="HospitalCode1" verify="体检医院编码|code:pehospital" readonly >
    </TD>
    <TD  class= title>
      体检医院名称
    </TD>
    <TD  class= input>
      <input class="common wid" name=HospitalName id="HospitalName" elementtype=nacessary  verify="体检医院名称|notnull" readonly>
    </TD>
    <TD  class= title>
      所属管理机构
    </TD>
    <TD  class= input>
       <Input class= 'codeno' name=ManageCom id="ManageCom" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="所属管理机构|notnull&len<=8"  readonly ><input class=codename readonly=true name=ManageComName id="ManageComName" >
    </TD>
	</TR>
    </table>
    </div>
 <div id="HealthType" >
 <div class="maxbox">
 	<table class= common align=center>
    	<%
    	//直接写到界面上
    	 for(int i=1;i<=tSSRS_Quality.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Quality.GetText(i,1);
                	String tSubName = tSSRS_Quality.GetText(i,2);
                	String tother = tSSRS_Quality.GetText(i,3);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	/*
                	if(tother.equals("21"))
                	{
                		%>
                		
                		<td></td>
                		<td></td>
                		<%
                	} 
                	*/
                	//else
                	{
                	%>
                		<td class= common width=25%> <input type=checkbox ShowTYPE='Score' name='<%=tSubCode%>' id='<%=tSubCode%>' value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	}
                	//每4个做换行处理.
                	if(i%3==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Quality.getMaxRow())
                	{
                		%>
                		</tr>
                		<%

                	}
                }
    	%>
  </table>
  </div>
</div>

<!--   table class=common border=0 width=100%>
	<tr><td class=titleImg align=center>体检医院管理岗</td>
	</tr>
</table-->

<div id="HealthType">

 	<table class= common align=center>
    	<%
    	//直接写到界面上
    	/*
    	 for(int i=1;i<=tSSRS_Manage.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Manage.GetText(i,1);
                	String tSubName = tSSRS_Manage.GetText(i,2);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	{
                	%>
                		<td class= common width=25%> <input type=checkbox ShowTYPE='Manage' name='<%=tSubCode%>' id='<%=tSubCode%>'  value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	}
                	//每4个做换行处理.
                	if(i%4==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Manage.getMaxRow())
                	{
                		%>
                		<td></td>
                		</tr>
                		<%

                	}
                }
    	*/
    	%>
  </table>
</div>

<!-- table class=common border=0 width=100%>
	<tr><td class=titleImg align=center>陪检内勤</td>
	</tr>
</table-->

<div id="HealthType">

 	<table class= common align=center>
    	<%
    	//直接写到界面上
    	/*
    	 for(int i=1;i<=tSSRS_Inner.getMaxRow();i++)
                {
                	String tSubCode = tSSRS_Inner.GetText(i,1);
                	String tSubName = tSSRS_Inner.GetText(i,2);
                	if(i==1)
                	{
                		
                		%>
                		  <tr class=common>
                		<% 
                	}
                	{
                	%>
                		<td class= common width=25%> <input type=checkbox ShowTYPE='Inner' name='<%=tSubCode%>' id='<%=tSubCode%>' value='<%=tSubCode%>' ><%=tSubName %>
                		</td>
                	<%
                	}
                	//每4个做换行处理.
                	if(i%4==0)
                	{
                		%>
                		</tr>
                		<tr class=common>
                		<%
                	}
                	if(i==tSSRS_Inner.getMaxRow())
                	{
                		%>
                		<td></td>
                		</tr>
                		<%

                	}
                }
    	*/
    	%>
  </table>
</div>
      <table class= common style="display:none">
      	<!-- <hr class="line"> -->
    	  <tr>
    	    <TD  class= title>
          黑名单标记
        </TD>
        <TD  class= input>
            <Input class="codeno" name=QualityFlag id="QualityFlag" style="background: url(../common/images/select--bg_03.png) no-repeat center; " onClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onDblClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onKeyUp="return showCodeListKey('QualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id="QualityFlagName" readonly=true>
          </TD> 
         <td class= title></td>
         <td class= input></td>
         <td class= title></td>
         <td class= input></td>  
    	  </tr>	  
    	
    </table>


  <div class="maxbox1">
  <table width="80%" height="20%" class= common>
    <TR  class= common> 
      <TD width="100%" height="15%"  class= title> 备注信息 </TD>
    </TR>
    <TR  class= common>
      <TD height="85%"  class= title><textarea name="Remark" id=Remark cols="135" rows="5" class="common" ></textarea></TD>
    </TR>
  </table>
  </div>
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">
  <input class=input type=hidden id="CheckedItem1" name="CheckedItem1" >
  <input class=input type=hidden id="CheckedItem2" name="CheckedItem2" >
  <input class=input type=hidden id="CheckedItem3" name="CheckedItem3" >


  <INPUT CLASS=cssButton VALUE="确  定" name=sure TYPE=button onClick="UpdateClick()">
  <INPUT CLASS=cssButton VALUE="返  回" TYPE=button onClick="returnParent()">
  
</form>
<br><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
