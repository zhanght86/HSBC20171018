<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�AmntAccumulateInput.jsp
//�����ܣ������ۼ�
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
	loggerDebug("AmntAccumulateInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var customerNo = "<%=tCustomerNo%>"; //�ͻ���
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�����ۼ�</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="AmntAccumulate.js"></SCRIPT>
  
  
  <%@include file="AmntAccumulateInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" id="fm" name="fm" target="fraSubmit">
<div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">
          �ͻ���
        </td>
        <td class="input5">
          <input class="common wid"  name="CustomerNo" id="CustomerNo" readonly="readonly">
        </td>
        <td class="title5">
          �ͻ�����
        </td>
        <td class="input5">
          <input class="common wid"  name="CustomerName" id="CustomerName" readonly="readonly">
        </td>
      </tr>
    </table>
</div>
<!--���ౣ���ۼ�-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAmntAccu);">
	    	</td>
	    	<td class= titleImg>
	    	 ���ౣ���ۼ� 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divAmntAccuGrid" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanAmntAccuGrid" >
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

<!--�����ۼ���ϸ-->

	  <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAmntAccuDetail);">
	    	</td>
	    	<td class= titleImg>
	    	  �����ۼ���ϸ
	    	</td>
	    </tr>
	  </table>    

  	<div  id= "divAmntAccuDetail" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanAmntAccuDetailGrid" >
  					</span> 
  			  	</td>
  			  </tr>
    	  </table>
    	
        <div  id= "divPage2" align=center style= "display: '' ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();">
        </div>
    </div>  

    <div id="Button" style="display:">
      <input class="cssButton" value="����" type="button" onclick="returnParent();">
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:'none'" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
