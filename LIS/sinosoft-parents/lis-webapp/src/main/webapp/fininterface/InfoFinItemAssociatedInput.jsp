 <html> 
 	<%
//创建日期：2008-08-11
//创建人  ：ZhongYan
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="java.util.*"%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String mStartDate = "";
	String mSubDate = "";
	String mEndDate = "";
        GlobalInput tGI = new GlobalInput();
        //PubFun PubFun=new PubFun();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("InfoFinItemAssociatedInput","1"+tGI.Operator);
	loggerDebug("InfoFinItemAssociatedInput","2"+tGI.ManageCom);
%>
<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
	var FinItemID = <%=request.getParameter("FinItemID")%>;
</script>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="InfoFinItemAssociatedInput.js"></SCRIPT>  
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="InfoFinItemAssociatedInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm id=fm    target=fraSubmit method=post>
	 <table>
    	<tr> <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAssociatedQuery);"></td>   		 
    		 <td class= titleImg>
        		 科目关联专项定义 
       	 </td>   		 
    	</tr>
    </table>

  <Div  id= "divAssociatedQuery" style= "display: ''">
      <table  class= common>
       	<tr  class= common>
        
      	  <td text-align: left colSpan=1>
  					<span id="spanItemAssociatedGrid" >
  					</span>
  			  </td>
  			</tr>
    	</table>
   <!-- <Div  id= "divPage" align=center style= "display: 'none' ">      
      <INPUT CLASS=cssButton90 VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
    </Div>-->
  </Div>
  
<div class="maxbox1">      
<table class= common border=0 width=100%>
  <table class= common>			
  	
			<!--tr class= common>
				<TD class= title>
					  版本编号
				</TD>
				<TD class=input>
				 	<Input class=codeno name=Version ondblclick="return showCodeList('VersionNo', [this,VersionName],[0,1]);" onkeyup="return showCodeListKey('Version', [this,VersionName],[0,1]);" verify="版本编号|NOTNULL"><input class=codename name=VersionName readonly=true elementtype=nacessary>
				</TD>
				<TD class= title>
		   	   科目编号
		    </TD>
		    <TD class= input>
		  		<Input class=codeno name=FinItem ondblclick="return showCodeList('FinItemID', [this,FinItemName],[0,1]);" onkeyup="return showCodeListKey('FinItemID', [this,FinItemName],[0,1]);" verify="科目编号|NOTNULL"><input class=codename name=FinItemName readonly=true elementtype=nacessary>
		    </TD>
			</tr-->	
					  
			<tr class= common>
				<TD class= title5>
					  专项编号
				</TD>
				<TD class=input5>
				 	 <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AssociatedID id=AssociatedID ondblclick="return showCodeList('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" onclick="return showCodeList('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" onkeyup="return showCodeListKey('AssociatedID', [this,AssociatedName],[0,1],null,fm.VersionNo.value,'VersionNo');" verify="专项编号|NOTNULL"><input class=codename name=AssociatedName id=AssociatedName readonly=true elementtype=nacessary>
				</TD>		
				<TD class= title5>
		   	   描述
		    </TD>
		    <TD class= input5>
		  	<Input class="wid" class=common name=ReMark id=ReMark verify="描述|len<=500" >
		    </TD>						 
			</tr>			
		  
    </table>         
    					 
  	<p>       
    <INPUT VALUE="添  加" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <!--INPUT VALUE="修  改" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();"-->
    <INPUT VALUE="删  除" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">   
    <INPUT VALUE="重  置" TYPE=button class= cssButton name= resetbutton onclick="return resetAgain()">   
    <INPUT type=hidden name=hideOperate value=''>
    <INPUT type=hidden name=VersionNo value=''> 
    <INPUT type=hidden name=FinItemID value=''>
    <INPUT type=hidden name=AssociatedID1 value=''>     
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>

</body>
</html>
