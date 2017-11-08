<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//程序名称：
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：HST
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="ProposalFee.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="ProposalFeeInit.jsp"%>
  <title>暂交费信息 </title>
</head>      
<body  onload="initForm();" >
  <form action="./ProposalFeeSave.jsp" method=post name=fm target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- 保单信息部分 -->
    <table class=common>
    <TR  class= common>
      <TD  class= title>
        保单号码
      </TD>
      <TD  class= input>
        <Input class="readonly" readonly name=PolNo >
        <Input class="readonly" readonly name=polType type=hidden>
      </TD>
    </TR>
    </table>
    
    <!-- 暂交费信息部分（列表） -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJTempFee1);">
    		</td>
    		<td class= titleImg>
    			 暂交费信息
    		</td>
    	</tr>
    </table>
	<Div  id= "divLJTempFee1" style= "display: ''">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1>
					<span id="spanFeeGrid" >
					</span> 
				</td>
			</tr>
		</table>
	</div>
	
  <INPUT VALUE="保存" CLASS=common TYPE=button onclick="submitForm();"> 					
  <INPUT VALUE="返回" CLASS=common TYPE=button onclick="top.close();"> 					

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  
</body>
</html>
