<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ClaimQueryInput.jsp
//�����ܣ����������ѯ
//�������ڣ�2005-06-01 11:10:36
//������  ��HL
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
	loggerDebug("ClaimQueryInput","operator:"+tGI.Operator);
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
  <title>���������ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="ClaimQuery.js"></SCRIPT>
  
  
  <%@include file="ClaimQueryInit.jsp"%>
  
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

<!--���ౣ���ۼ�-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divClaim);">
	    	</td>
	    	<td class= titleImg>
	    	 ������Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divClaim" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanClaimGrid" >
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


<!--������Ϣ-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>
	    	 �����ⰸ������Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divPol" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanPolGrid" >
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


    <div id="Button" style="display:">
   <hr>
   <table>
   <tr>
   <td>
      <INPUT VALUE=" ����������ϸ��ѯ " class=cssButton TYPE=button onclick="">
      <INPUT VALUE=" Ӱ�����ϲ�ѯ " class=cssButton TYPE=button onclick="showImage();"> 
      <INPUT VALUE=" ����˱���ѯ " class=cssButton TYPE=button onclick="">
   </td>
   </tr>
   </table>
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
