<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//程序名称：UWManuRReport.jsp
//程序功能：新契约人工核保提起再保呈报
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人 ln   更新日期  2008-11-04   更新原因/内容 根据新需求进行修改
%>
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="UWManuUpReport.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> 新契约生存调查报告 </title>
  <%@include file="UWManuUpReportInit.jsp"%>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tPrtNo%>','<%=tMissionID%>','<%=tSubMissionID%>');" >
  <form method=post id="fm" name=fm target="fraSubmit" action= "./UWManuUpReportChk.jsp">
    <!-- 以往核保记录部分（列表） -->
   <table class= common border=0 width=100%>
     <tr>
		  <td class=common><IMG src="../common/images/butExpand.gif"
			style="cursor: hand;" OnClick="showPage(this,divUWSpec1);"></td> <td class=titleImg align=center>再保基本信息</td>
	   </tr>
   </table>
   <div id="divUWSpec1" class="maxbox">
   <table  class=common border=0 align=center>
     <tr>
       <td class=title>印刷号</td>
       <TD  class=input>
         <Input class= "readonly wid" readonly id="PrtNo" name=PrtNo >      
         <input type='hidden' aclass="codeno" id="SellType" name="SellType" verify="销售方式|notnull" verifyorder="1"  ondblclick="showCodeList('sellType',[this,sellTypeName],[0,1])" onkeyup="return showCodeListKey('sellType',[this,sellTypeName],[0,1])"><input type="hidden" id="sellTypeName" name="sellTypeName" class="codename" readonly="readonly">
       </TD>	  
      <!--td class="title">销售方式</td>
　　　<td class="input"-->
      <!--/td-->     
       <TD class = title> 业务员代码 </TD>
       <TD  class=input><Input class="readonly wid" readonly id="AgentCode" name=AgentCode ></TD>     
       <TD class = title>管理机构</TD>
       <TD  class=input><Input class="readonly wid" readonly id="ManageCom" name=ManageCom ></TD>
     </tr>
     <tr>       
      <TD class = title> 销售渠道 </TD>
       <TD  class=input><Input class="readonly wid" readonly id="SaleChnl" name=SaleChnl ></TD>
       <TD class = title> 客户姓名 </TD>
       <TD  class=input><Input class="readonly wid" readonly id="CustomerName" name=CustomerName ></TD>
     </tr>
     <!--

     <tr>                     
       <td class= title>银行网点</td>
       <TD  class= input><Input class= "readonly" name=BankCode ></TD>
     </tr>
     -->
    </table>
   <table class=common align="center">     
     <tr class= common>
	       <td class=title>呈报原因</td>
	       <TD  class=input><input class="codeno" id="ReportReason" name="ReportReason" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('ReportReason',[this,ReportReasonDes],[0,1])" onkeyup="return showCodeListKey('ReportReason',[this,ReportReasonDes],[0,1])"><input id="ReportReasonDes" name="ReportReasonDes" class="codename" readonly="readonly">
	       </TD>
	       <td><input type='hidden'></td>	 
	       <td><input type='hidden'></td>   
	 </tr>
    </table>
    </div>
      <table class=common>         
         <TR  class= common> 
           <TD  class= common>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;呈报原因描述（800字以内,回车符占一个汉字） </TD>
         </TR>
         <TR  class= common>
           <TD  class= common>
             &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<textarea name="ReportRemark" cols="120" rows="3" class="common" ></textarea>
           </TD>
         </TR>
      </table>
      
   
      <INPUT type= "hidden" id="ProposalNoHide" name= "ProposalNoHide" value= "">
      <INPUT type= "hidden" id="ContNo" name= "ContNo" value= "">
      <INPUT type= "hidden" id="MissionID" name= "MissionID" value= "">
      <INPUT type= "hidden" id="SubMissionID" name= "SubMissionID" value= "">
      <INPUT type= "hidden" id="SubNoticeMissionID" name= "SubNoticeMissionID" value= "">
      <INPUT type= "hidden" id="Flag" name= "Flag"  value = "">
      <INPUT type= "button" id="sure" name= "sure" value="确  认" class= cssButton onclick="submitForm()">			
		
    <!--读取信息-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
