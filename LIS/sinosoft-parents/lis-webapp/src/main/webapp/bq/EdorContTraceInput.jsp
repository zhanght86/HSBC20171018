<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorContTraceInput.jsp
//�����ܣ���ȫ��ͬ�˱��켣��ѯ  
//�������ڣ�2005-07-13 10:10:36
//������  ��liurx
//���¼�¼��  ������    ��������     ����ԭ��/���� 
%>
 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //������
	String tContNo = request.getParameter("ContNo");
	String tEdorNo = request.getParameter("EdorNo");
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");

%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
    var ContNo = "<%=tContNo%>"; //������
    var EdorNo = "<%=tEdorNo%>";

</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>��ȫ��ͬ�˱��켣</title> 

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="EdorContTrace.js"></SCRIPT> 
  
  <%@include file="EdorContTraceInit.jsp"%>  
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id=fm target="fraSubmit" >

    <table class=common>
	    <tr class=common>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
	    	</td>
	    	<td class= titleImg >
	    	 ��ͬ�˱��켣��Ϣ 
	    	</td>
	    </tr>
	  </table>      
	  <div id= "divCont" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanContTraceGrid" >   
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

      <input class="cssButton" value="     ��  ��     " type="button" onclick="returnParent();">
    </table>
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>

</html>
