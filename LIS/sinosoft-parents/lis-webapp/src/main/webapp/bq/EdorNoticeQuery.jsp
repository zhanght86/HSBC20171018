
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<html>
<%
    //���ҳ��ؼ��ĳ�ʼ����
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput) session.getValue("GI");
    String tEdorAcceptNo = request.getParameter("EdorAcceptNo");
%>
<script>
    var operator = "<%=tGI.Operator%>";   //��¼����Ա
    var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var comcode = "<%=tGI.ComCode%>"; //��¼��½����
    var curDay = "<%=PubFun.getCurrentDate()%>"; 
    var EdorAcceptNo = "<%=tEdorAcceptNo%>";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./EdorNoticeQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="EdorNoticeQueryInit.jsp"%>
  <title>�������ѯ </title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  	<table>
		<tr>
		<td class=common>
		    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAllNoticeGrid);">
		</td>
		<td class= titleImg>������б�</td>
		</tr>
	</Table>      
  	<Div  id= "divAllNoticeGrid" style= "display: ''" align =center>
      <table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAllNoticeGrid" align=center>
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
    		<tr>
    			<td>
			      <INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPageAll.firstPage();"> 
			    </td>
			    <td>  
			      <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPageAll.previousPage();"> 					
			    </td>
			    <td> 			      
			      <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPageAll.nextPage();"> 
			    </td>
			    <td> 			      
			      <INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPageAll.lastPage();"> 						
			    </td>  			
  			</tr>
  		</table>
  		 	</div> 
 <Div  id= "divNotice" style= "display: ''" align =center> 		 	
   		 <table>
	        <TR>	        	
	         	<TD class= titleImg>��������</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea readonly=true name="AskInfo" id=AskInfo cols="100" rows="4" witdh=25% class="common wid"></textarea></TD>
	       	</TR>
	      </table>
	      <table>
	        <TR>	        	
	         	<TD class= titleImg>�ظ�����</TD>
	       	</TR>
	       	<TR>	        	
	         <TD class= input> <textarea readonly=true name="ReplyInfo" id=ReplyInfo cols="100" rows="4" witdh=25% class="common wid"></textarea></TD>
	       	</TR>
	     </table>
	    </div>
	     <INPUT class = cssButton VALUE="��   ��" TYPE=button onclick="returnParent();"> 
	     <input type="hidden" name="EdorAcceptNo" id=EdorAcceptNo>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
