<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
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
%>

<script>
	var contNo = "<%=tContNo%>";          //个人单的查询条件.
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
<title>客户品质管理 </title>
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
  
  <SCRIPT src="UWCustomerQuality.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="UWCustomerQualityInit.jsp"%>
  
</head>

<body  onload="initForm();" >
  <form method=post name=fm id="fm" target="fraSubmit" action="./UWCustomerQualitySave.jsp">
   <div class="maxbox"> 
    <table class= common>
    	<TR  class= common>
    	  
    	  <td class=title>
           请选择客户
         </td>
                                
         <td class=input>
         <Input class="codeno" name=customer id="customer" style="background: url(../common/images/select--bg_03.png) no-repeat right center; " onClick="return showCodeList('customer',[this,customername],[0,1],null,tContNo,'ContNo');" onDblClick="return showCodeList('customer',[this,customername],[0,1],null,tContNo,'ContNo');" onKeyUp="return showCodeListKey('customer',[this,customername],[0,1]);" ><Input class="codename" name="customername" id="customername" readonly elementtype=nacessary>
         </td>
        <TD  class= title>
          客户号
        </TD>
        <TD  class= input>
          <Input class="common wid" name=CustomerNo id="CustomerNo" readonly=true>
        </TD>
        <TD  class= title>
          姓名
        </TD>
        <TD  class= input>
          <Input class="common wid" name="Name" id="Name" readonly=true>
        </TD> 
      </TR>
    
   <!-- </table>
    
    <table class= common>-->
    	
    	<tr class= common>
        	
        <TD  class= title>
          出生日期
        </TD>
        <TD  class= input>
          <Input class="coolDatePicker" dateFormat="short" name="Birthday"  id="Birthday" onClick="laydate({elem:'#Birthday'});"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </TD>
        <TD  class= title>
          性别
        </TD>
        <TD  class= input>
          <Input class="common wid" name=Sex id="Sex" readonly=true>
        </TD>
        <TD  class= title>
          证件类型
        </TD>
        <TD  class= input>
          <Input class="common wid" name=IDType id="IDType" readonly=true>
        </TD>
    	</tr>
    	
    	<tr>
        
        <TD  class= title>
          证件号码
        </TD>
        <TD  class= input>
          <Input class="common wid" name=IDNumber id="IDNumber" readonly=true>
        </TD>
         <TD  class= title>
          客户品质标记
        </TD>
        <TD  class= input>
            <Input class="codeno" name=QualityFlag id="QualityFlag" style="background: url(../common/images/select--bg_03.png) no-repeat right center; " onClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onDblClick="return showCodeList('QualityFlag',[this,QualityFlagName],[0,1]);" onKeyUp="return showCodeListKey('QualityFlag',[this,QualityFlagName],[0,1]);"><input class=codename name=QualityFlagName id="QualityFlagName" readonly=true>
          </TD> 
           <td class= title></td>
         <td class= input></td>
    	</tr>
    	<!--</table>
    	
      <table class= common>-->
      	
    	   
    	
    </table>


  
  <table class= common>
    <TR  class= common> 
      <TD class= title> 备注信息 </TD>
    </TR>
    <TR  class= common>
      <TD colspan="6" style="padding-left:16px"><textarea name="Remark" id="Remark" cols="226" rows="4" class="common" ></textarea></TD>
    </TR>
  </table>
  
  <input type="hidden" name= "PrtNo" value= "">
  <input type="hidden" name= "ContNo" value= "">


  <INPUT CLASS=cssButton VALUE="确  定" name=sure TYPE=button onClick="UpdateClick()">
  <!-- modified by liuyuxiao 2011-05-24 隐去返回，在tab中无用 -->
  <INPUT CLASS=cssButton VALUE="返  回" TYPE=button onClick="returnParent()" style= "display: none">
  
</form>
<br><br><br><br><br>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span> 

</body>
</html>
