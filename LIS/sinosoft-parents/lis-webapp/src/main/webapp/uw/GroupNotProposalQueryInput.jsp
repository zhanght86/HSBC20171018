<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�GroupNotProposalQueryInput.jsp
//�����ܣ�δ�б���ѯ
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
	loggerDebug("GroupNotProposalQueryInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var AppntNo = "<%=tAppntNo%>"; //�ͻ���
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>δ�б���ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <script src="GroupNotProposalQuery.js"></SCRIPT>
  
  
  <%@include file="GroupNotProposalQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
	<div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">
         ��λ����
        </td>
        <td class="input5">
          <input class="common wid" name="AppntNo" id="AppntNo" readonly>
        </td>
        <td class="title5">
          ��λ����
        </td>
        <td class="input5">
          <input class="common wid" name="GrpName" id="GrpName" readonly>
         <input type="hidden" id="GrpContNo" name= "GrpContNo" value= "">
        </td>
      </tr>
    </table>
</div>
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
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	


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
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	



    <div id="Button" style="display:">
      <input class="cssButton" id="button1" name="button1" value="������ϸ��Ϣ��ѯ"  type="button" onclick="getContDetailInfo();">
      <input class="cssButton" id="button2" name="button2" value="  Ӱ�����ϲ�ѯ  "  type="button" onclick="showImage();">
      <input class="cssButton" id="button3" name="button3" value="  �������Ѳ�ѯ  "  type="button" onclick="showTempFee();">
     <input class="cssButton" id="button5" name="button5" value="  ����������ѯ  "  type="button" onclick="OperationQuery();">
      <input class="cssButton" id="button4" name="button4" value="  �˱����۲�ѯ  "  type="button" onclick="UWQuery();">

      <!--<input class="cssButton" value="     ��  ��     " type="button" onclick="returnParent();">-->
    </table>
    </div>
<br>
<a href="javascript:void(0);" class="button" onClick="returnParent();">��    ��</a>
		<!--������-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>

</body>


</html>