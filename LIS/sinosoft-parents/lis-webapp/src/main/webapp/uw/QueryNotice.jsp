<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�QueryNotice.jsp
//�����ܣ�֪ͨ���ѯ
//�������ڣ�2006-11-17 17:05
//������  ��haopan
//���¼�¼��  ������    ��������     ����ԭ��/���� 
%> 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

<html>
	<%
	String tGrpContNo = request.getParameter("GrpContNo");
	//loggerDebug("QueryNotice","@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@tGrpContNo"+tGrpContNo);
	%>
	<head>
		<script>
	
  var GrpContNo = "<%=tGrpContNo%>"; //Ͷ������  
</script>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>֪ͨ���ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
 <script src="QueryNotice.js"></SCRIPT> 
  
  <%@include file="QueryNoticeInit.jsp"%> 
    
</head>
  
  <body onload="initForm();">
  	
  	<form method="post" id="fm" name="fm" target="">
  		
  	 <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divnotice);">
	    	</td>
	    	<td class= titleImg>
	    	 �ѷ���֪ͨ����Ϣ
	    	</td>
	    </tr>
	  </table> 
	    
	  <div id= "divnotice" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanNoticeGrid" >   
  					</span>  
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	
    <input class="cssButton" value="  ��  ��  " type="button" onclick="returnParent();">
    </table>
  </form>
  </body>
</html>
