<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RiskTrace.jsp
//�����ܣ����ֺ˱��켣��ѯ
//�������ڣ�2005-06-27 11:10:36
//������  ��CCVIP 
//���¼�¼��  ������    ��������     ����ԭ��/���� 
%>
 
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //Ͷ������
	String tContNo = request.getParameter("ContNo");
	String tPolNo = request.getParameter("PolNo");
	loggerDebug("RiskTrace","PolNo="+tPolNo);     
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("RiskTrace","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var ContNo = "<%=tContNo%>"; //Ͷ������
  var PolNo = "<%=tPolNo%>"; //���ֱ�����  
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>���ֺ˱��켣</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="RiskTrace.js"></SCRIPT> 
  
  <%@include file="RiskTraceInit.jsp"%> 
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" target="fraSubmit">

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRisk);">
	    	</td>
	    	<td class= titleImg>
	    	 ���ֺ˱��켣��Ϣ 
	    	</td>
	    </tr>
	  </table>      
	  <div id= "divRisk" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanRiskTraceGrid" >   
  					</span>  
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: '' ">
          <input class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
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
