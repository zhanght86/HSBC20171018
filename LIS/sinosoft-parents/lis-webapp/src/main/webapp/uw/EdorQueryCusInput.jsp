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
	String tContNo = request.getParameter("ContNo");
	String tCustomerNo = request.getParameter("CustomerNo");
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("EdorQueryCusInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
        var contNo = "<%=tContNo%>"; //�ͻ���
        var customerNo = "<%=tCustomerNo%>"; //�ͻ���
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css> 
  <title>��ȫ��ѯ</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="EdorQueryCus.js"></SCRIPT>
  
  
  <%@include file="EdorQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" id="fm" name="fm" target="fraSubmit">
   <div id= "divcustomer" class="maxbox1" style= "display: none" >
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
<hr class="line">
 </div>
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
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	
<hr class="line">

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
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">
        </div>
    </div>	


<hr class="line">
    <div id="Button" style="display:">
      <input class="cssButton" id="button2" name="button2" value="  Ӱ�����ϲ�ѯ  " type="button" onclick="showImage();"> 
      <input class="cssButton" id="button3" name="button3" value="  ��ȫ��ϸ��ѯ  " type="button" onclick="BqEdorQueryClick();"> 
      <!-- <input class="cssButton" name="button4" value="��ȫ�˱��ջ��ѯ" type="button" onclick="EdorUWDetailQuery()">  -->
<hr class="line">
      <input class="cssButton" value="  ��  ��  " type="button" onclick="returnParent();">
    </table>
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>


</html>
