<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EdorQueryInput.jsp
//�����ܣ���ȫ��ѯ
//�������ڣ�2005-6-10 14:36
//������  ��guomy
//���¼�¼��  ������    ��������     ����ԭ��/����
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
        //�ͻ���
	String tCustomerNo = request.getParameter("CustomerNo");
	
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("EdorQueryInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
        var customerNo = "<%=tCustomerNo%>"; //�ͻ���
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css> 
  <title>��ȫ��ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="EdorQuery.js"></SCRIPT>
  
  
  <%@include file="EdorQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" target="fraSubmit">

    <table class="common">
      <tr class="common">
        <td class="title">
          �ͻ���
        </td>
        <td class="input">
          <input class="common" name="CustomerNo" readonly="readonly">
        </td>
        <td class="title">
          �ͻ�����
        </td>
        <td class="input">
          <input class="common" name="CustomerName" readonly="readonly">
        </td>
      </tr>
    </table>
<hr>
<!--���ౣ���ۼ�-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdor);">
	    	</td>
	    	<td class= titleImg>
	    	 ������ȫ������Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divEdor" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanEdorGrid" >
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
<hr>

<!--������Ϣ-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItem);">
	    	</td>
	    	<td class= titleImg>
	    	 ������ȫ������Ŀ��Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divEdorItem" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanEdorItemGrid" >
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


<hr>
    <div id="Button" style="display:">
      <input class="cssButton" name="button2" value="  Ӱ�����ϲ�ѯ  " type="button" onclick="showImage();"> 
      <input class="cssButton" name="button4" value="  ��ȫ�˱���ѯ  " type="button" onclick="EdorUWQuery()">
<hr>
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
