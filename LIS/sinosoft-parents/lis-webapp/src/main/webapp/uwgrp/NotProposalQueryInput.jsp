<%@include file="../common/jsp/UsrCheck.jsp"%>


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
	loggerDebug("NotProposalQueryInput","operator:"+tGI.Operator);
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
  <title>δ�б���ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="NotProposalQuery.js"></SCRIPT>
  
  
  <%@include file="NotProposalQueryInit.jsp"%>
  
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
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
	    	</td>
	    	<td class= titleImg>
	    	 δ�б����� 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divCont" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanContGrid" >
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
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>
	    	 δ�б�����������Ϣ 
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
      <!--input class="cssButton" name="button1" value="������ϸ��Ϣ��ѯ" type="button" onclick="getContDetailInfo();">
      <input class="cssButton" name="button2" value="  Ӱ�����ϲ�ѯ  " type="button" onclick="showImage();">        
      <input class="cssButton" name="button3" value="  �������Ѳ�ѯ  " type="button" onclick="showTempFee();">      
      <input class="cssButton" name="button4" value="  �˱����۲�ѯ  " type="button" onclick="UWQuery();"-->           
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
