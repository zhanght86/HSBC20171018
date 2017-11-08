<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%--
   queryFlag作用：判断在新契约录单界面中是从投保人录入调用的查询，还是被保人录入调用的查询。
   投保人录入：queryFlag=queryappnt
   被保人录入：queryFlag=queryInsured
   
--%>
<%
  String queryFlag = request.getParameter("queryFlag");
%>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>   
  <SCRIPT src="../bq/QueryPEdor.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css> 
  <%@include file="QueryPEdorInit.jsp"%> 
<html>
<head>
<title>保全批改查询</title>
<script language="javascript">
  var queryFlag = "<%=queryFlag%>";
  //alert("aa="+queryFlag);
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../bq/QueryPEdorInit.jsp"%>

</head>
<body  onload="initForm();">
<!--登录画面表格-->
<form name=fm id=fm target=fraSubmit method=post>
	
 
    <Table>
    	<TR>
        	<TD class=common>
	           <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
    		</TD>
    		<TD class= titleImg>
    			 保全批改信息
    		</TD>
    	</TR>
    </Table>    	
    <Div  id= "divLDPerson1" style= "display: ''" class=maxbox1>
    	<table class = common>
    		<TR class = common>
    			<TD class = title>批单号 </TD>
    			<TD  class= input> <Input class= "common wid" name= > </TD>
    			<TD class = title>批改项目 </TD>
    			<TD  class= input> <Input class= "common wid" name= > </TD>
    			<TD class = title>补退费金额 </TD>
    			<TD  class= input> <Input class= "common wid" name= > </TD>
    		</TR>
    		<TR class = common>
    			<TD class = title>申请时间</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title>生效时间</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title>批改状态</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    		</TR>
    		<TR class = common>
    			<TD class = title>经办人</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title>复核人</TD>
    			<TD class = input> <Input class = "common wid" name=> </TD>
    			<TD class = title></TD>
    			<TD class = input> <Input class = "readonly wid" readonly name=> </TD> 
    		</TR>
    </table>			
    </Div>
    <table>
     	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divReportByLoseInfo);">
      </td>
      <td class= titleImg>
        核保详细信息
      </td>
   </tr>
   </table>
   
   <Div  id= "divReportByLoseInfo" style= "display: ''">
      <table  class= common>
       <TR  class= common>
        <TD class= title>核保结论</TD>
        <TD  class= input> <Input class= "common wid" name=ContNo id=ContNo > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
       </TR>
       
      <TR class=common>
				<TD class=title colspan=6 > 保全核保意见 </TD>
			</TR>
			<TR class=common>
					<TD  class = "readonly wid" readonly colspan=6 ><textarea name="UWIdea" id=UWIdea cols="100%" rows="5" witdh=100% class="readonly" readonly ></textarea></TD>
			</TR>
       
       <TR class=common>
       	<TD class=title>批单打印次数</TD>
       	<TD  class= input> <Input class= "common wid" name= > </TD>
       	<TD class=title>核保时间</TD>
       	<TD  class= input> <Input class= "common wid" name= > </TD>
       	<TD class=title>特别约定</TD>
       	<TD  class= input> <Input class= "common wid" name= > </TD>
       </TR>
       

    </Table>					
 </Div>	
    
 
    <table>
     	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divReportByLoseInfo1);">
      </td>
      <td class= titleImg>
        收/付费详细信息
      </td>
   </tr>
   </table>
    <Div  id= "divReportByLoseInfo1" style= "display: ''">
      <table  class= common>
       <TR  class= common>
        <TD class= title>应收日期</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>实收日期</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>应付日期</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
       </TR>
       <TR  class= common>
        <TD class= title>实付日期</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>收费方式</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
        <TD class=title>付费方式</TD>
        <TD  class= input> <Input class= "common wid" name= > </TD>
       </TR>
       <TR>
		 <TD class= title>
		     银行编码
		 </TD>    
		 <TD class= input>
		    <Input class= "readOnly wid" readonly  name=BankCode id=BankCode >
		 </TD>	
		 <TD class= title>
		     银行帐号
		 </TD>     
		 <TD class= input>
		    <Input class= "readOnly wid" readonly  name=BankAccNo  id=BankAccNo>
		 </TD>
		 <TD class= title>
		     银行账户名
		 </TD>    
		 <TD class= input>
		    <Input class= "readOnly wid" readonly  name=AccName id=AccName >
		 </TD>		       
     </TR>
      </table>
    </Div>
		
		<table>
     	<tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divReportByLoseInfo2);">
      </td>
      <td class= titleImg>
        回退详细信息
      </td>
   </tr>
   </table>
    <Div  id= "divReportByLoseInfo2" style= "display: ''">
      <table  class= common>
       <TR  class= common>
        <TD class= title>回退原因</TD>
        <TD  class= input> <Input class= "common wid" name=ContNo id=ContNo > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
        <TD class=title></TD>
        <TD  class= input> <Input class= "readonly wid" readonly name= > </TD>
       </TR>
      </table>
    </Div>	
    <INPUT class=cssButton VALUE="调阅影像资料" TYPE=button onclick="QueryPEdor();">
    <INPUT class=cssButton VALUE=" 返回 " TYPE=button onclick="QueryBonus();">
			

<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>					
</Form>
</body>
</html>
