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
	String tContNo = request.getParameter("ContNo");
	String tCustomerNo = request.getParameter("CustomerNo");
	//09-11-11������ǣ�ֻ�б����˱���ѯ����Żᴫ��ContFlag����ֵΪ1
	//���ContFlag==1�Ļ���ֻ��contno��
	String tContFlag = request.getParameter("ContFlag");
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("ClaimQueryCusInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
  var contNo = "<%=tContNo%>"; //��
  var customerNo = "<%=tCustomerNo%>"; //�ͻ���
  var contFlag = "<%=tContFlag%>"
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>���������ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="ClaimQueryCus.js"></SCRIPT>
  
  
  <%@include file="ClaimQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
<div id= "divCustomer" style= "display: none" >
    <table class="common">
      <tr class="common">
        <td class="title5">
          �ͻ���
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerNo" id="CustomerNo" readonly>
        </td>
        <td class="title5">
          �ͻ�����
        </td>
        <td class="input5">
          <input class="common wid" name="CustomerName" id="CustomerName" readonly>
        </td>
      </tr>
    </table>
</div>
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
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
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
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage2.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage2.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage2.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage2.lastPage();">
        </div>
    </div>	


    <div id="Button" style="display:">
   <hr class="line">
   <table>
   <tr>
   <td>
      <INPUT VALUE=" ����������ϸ��ѯ " class=cssButton TYPE=button onClick="showDetail();">
      <INPUT VALUE=" Ӱ�����ϲ�ѯ " class=cssButton TYPE=button onClick="showImage();"> 
      <INPUT VALUE=" �˱��ջ��ѯ " class=cssButton TYPE=button onClick="queryClaimUW();">
   </td>
   </tr>
   </table>
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
