<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupProposalQueryInput.jsp
//�����ܣ��б���ѯ
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
	
	String tAppntNo = request.getParameter("AppntNo");
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("GroupProposalQueryInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var AppntNo = "<%=tAppntNo%>"; //�ͻ���
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�б���ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="GroupProposalQuery.js"></SCRIPT>
  
  
  <%@include file="GroupProposalQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" target="fraSubmit">

    <table class="common">
      <tr class="common">
        <td class="title">
          ��λ����
        </td>
        <td class="input">
          <input class="common" name="AppntNo" readonly="readonly">
        </td>
        <td class="title">
          ��λ����
        </td>
        <td class="input">
          <input class="common" name="GrpName" readonly="readonly">
          <input type="hidden" name= "GrpContNo" value= "">
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
	    	 �ѳб����� 
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
	    	 �ѳб�����������Ϣ 
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


<hr>
    <div id="Button" style="display:">
      <input class="cssButton" name="button1" value="������ϸ��Ϣ��ѯ"  type="button" onclick="getContDetailInfo();">
      <input class="cssButton" name="button2" value="  Ӱ�����ϲ�ѯ  "  type="button" onclick="showImage();">
      <input class="cssButton" name="button3" value="  �������Ѳ�ѯ  "  type="button" onclick="showTempFee();">
      <input class="cssButton" name="button4" value="  �˱����۲�ѯ  "  type="button" onclick="UWQuery();">
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
