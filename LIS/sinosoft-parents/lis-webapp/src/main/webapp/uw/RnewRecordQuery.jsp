<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�RnewRecordQuery.jsp
//�����ܣ�������ѯ
//�������ڣ�2005-06-22 11:10:36
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
    String tMissionID = request.getParameter("MissionID");
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("RnewRecordQuery","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var ContNo = "<%=tContNo%>"; //Ͷ������
  var MissionID = "<%=tMissionID%>"; 
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>������ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="RnewRecordQuery.js"></SCRIPT>
  
  
  <%@include file="RnewRecordQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divRecord);">
	    	</td>
	    	<td class= titleImg>
	    	 ����������Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divRecord" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanRecordGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: none ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	
    
      <input class="cssButton" value=" ��  �� " type="button" onClick="returnParent();">
      <input class="cssButton" value=" �˱��ȴ�ԭ���ѯ " type="hidden" onClick="queryReason();">

		<!--������-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>

</html>
