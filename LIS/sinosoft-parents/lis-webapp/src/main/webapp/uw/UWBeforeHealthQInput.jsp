<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ProposalQueryInput.jsp
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
	String tCustomerNo = request.getParameter("CustomerNo");
	String tContNo = request.getParameter("ContNo");
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("UWBeforeHealthQInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var tCustomerNo = "<%=tCustomerNo%>"; //�ͻ���
  var tContNo = "<%=tContNo%>";

</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel="stylesheet" type="text/css">
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>�б���ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="UWBeforeHealthQ.js"></SCRIPT>  
  
  <%@include file="UWBeforeHealthQInit.jsp"%>  
</head>
<body  onload="initForm();" > 
  
  <form method="post" id="fm" name="fm" target="fraSubmit">
    <div class=maxbox1>
    <table class="common">
      <tr class="common">
        <td class="title5">
          �ͻ���
        </td>
        <td class="input5">
          <input class="common wid" id="CustomerNo" name="CustomerNo" readonly="readonly">
        </td>
        <td class="title5">
          �ͻ�����
        </td>
        <td class="input5">
          <input class="common wid" id="CustomerName" name="CustomerName" readonly="readonly">
        </td>
      </tr>
    </table>
	</div>
<hr class=line>
<!--���ౣ���ۼ�-->

    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
	    	</td>
	    	<td class= titleImg>
	    	 �ÿͻ��µļ�����챣�� 
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
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	




    <div id="Button" style="display:">
      <!--<input class="cssButton" name="button1" value="������ϸ��Ϣ��ѯ"  type="button" onclick="getContDetailInfo();"> -->
      <input class="cssButton" id="button2" name="button2" value="  Ӱ�����ϲ�ѯ  "  type="button" onclick="showImage();">
      <!--<input class="cssButton" name="button3" value="  �������Ѳ�ѯ  "  type="button" onclick="showTempFee();">-->
      <!--<input class="cssButton" name="button4" value="  �˱����۲�ѯ  "  type="button" onclick="UWQuery();">-->
<hr>

      <input class="cssButton" value="��  ��" type="button" onclick="returnParent();">
    </table> 
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>

</html>
