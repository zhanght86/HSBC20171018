<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.schema.*" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
		GlobalInput tGI = (GlobalInput)session.getValue("GI");
		String comcode = tGI.ComCode;
%>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  

  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT> 

  <SCRIPT src="LFGetNotice.js"></SCRIPT>
  <%@include file="LFGetNoticeInit.jsp"%>

  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>��ӡ����嵥 </title> 
</head>
<body  onload="initForm();" >
  <form  method=post name=fm id="fm" target="f1print">
		<!-- Ͷ������Ϣ���� -->
		<table class= common border=0 width=100%>
			<tr>
            <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,inshow);"></td>
				<td class= titleImg>�������ѯ������</td>
			</tr>
		</table>
        <Div  id= "inshow" style= "display: ''" class="maxbox1" >
      <table  class= common>
    <TR  class= common>
    
      <TD  class= title5>
        ���屣����
      </TD>
      <TD  class= input5>
        <Input class=wid name=GrpContNo id=GrpContNo >
      </TD>
     <TD  class= title5></TD>
	 <TD  class= input5></TD>
    </TR>
  </table> 
  </div>
    <!--<INPUT class="cssButton" VALUE=" ��   ѯ " TYPE=button onclick="easyQueryClick();">-->
    <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">��    ѯ</a>

  	<input type=hidden id="PrtSeq" name="PrtSeq" id="PrtSeq">
    
    <table>
			<tr>
					<td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);"></td>
					<td class= titleImg>��ȡ��Ϣ</td>
			</tr>
    </table>
  	<Div  id= "divLCPol1" style= "display: ''" >
			<table  class= common>
				<tr  class= common>
					<td text-align: left colSpan=1>
							<span id="spanPolGrid" ></span> 
					</td>
				</tr>
			</table>
			<INPUT VALUE=" �� ҳ " class= cssButton90 TYPE=button onclick="getFirstPage();"> 
			<INPUT VALUE="��һҳ " class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
			<INPUT VALUE="��һҳ " class= cssButton92 TYPE=button onclick="getNextPage();"> 
			<INPUT VALUE=" β ҳ " class= cssButton93 TYPE=button onclick="getLastPage();"> 					
  	</div>
  	<p>
  	
      <!--<INPUT VALUE="��ӡ����嵥" class= cssButton TYPE=button onclick="printPol();">-->
      <a href="javascript:void(0);" class="button" onClick="printPol();">��ӡ����嵥</a> 
  	</DIV>
  	</p> 
  	
<div id="divGetDrawInfo" style= "display:none">
	     <table>
         
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGetDrawButton);">
        </td>
        <td class= titleImg>
          ��ȡ��¼��ϸ
        </td>
      </tr>
    </table>
  
    <table  class= common>
      <tr  class= common>
        <td text-align: left colSpan=1>
          <span id="spanLFGetGrid" > </span> 
        </td>
      </tr>	
    </table>
      <Div id= "divLCGetDrawButton" style= "display: ''" align="center">
    	
        <INPUT VALUE="��  ҳ" class="cssButton90" type="button" onclick="turnPage1.firstPage();"> 
				<INPUT VALUE="��һҳ" class="cssButton91" type="button" onclick="turnPage1.previousPage();"> 					
				<INPUT VALUE="��һҳ" class="cssButton92" type="button" onclick="turnPage1.nextPage();"> 
				<INPUT VALUE="β  ҳ" class="cssButton93" type="button" onclick="turnPage1.lastPage();">  			

  </div> 
</div> 	
  
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
<script>
	<!--ѡ�������ֻ�ܲ�ѯ������¼�����-->
	var codeSql = "1  and code like #"+<%=comcode%>+"%#";
</script>
