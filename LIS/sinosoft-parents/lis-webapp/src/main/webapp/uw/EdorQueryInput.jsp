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
	
        GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("EdorQueryInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
        var contNo = "<%=tContNo%>"; //�ͻ���
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

  <script src="EdorQuery.js"></SCRIPT>
  
  
  <%@include file="EdorQueryInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit">
   <div id= "divcustomer" style= "display:none" >
    <table class="common">
      <tr class="common">
        <td class="title5">
          �ͻ���
        </td>
        <td class="input5">
          <input class="common wid" id="CustomerNo" name="CustomerNo" readonly>
        </td>
        <td class="title5">
          �ͻ�����
        </td>
        <td class="input5">
          <input class="common wid" id="CustomerName" name="CustomerName" readonly>
        </td>
      </tr>
    </table>   

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
	  <div id= "divEdor" style= "display:  " >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanEdorGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display:   ">
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
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorItem);">
	    	</td>
	    	<td class= titleImg>
	    	 ������ȫ������Ŀ��Ϣ 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divEdorItem" style= "display:  " >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanEdorItemGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display:   ">
          <input class=cssButton90 VALUE="��  ҳ" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="β  ҳ" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	



    <div id="Button" style="display:">
      <input class="cssButton" id="button2" name="button2" value="  Ӱ�����ϲ�ѯ  " type="button" onClick="showImage();"> 
      <input class="cssButton" id="button3" name="button3" value="  ��ȫ��ϸ��ѯ  " type="button" onClick="BqEdorQueryClick();"> 
      <!-- <input class="cssButton" name="button4" value="��ȫ�˱��ջ��ѯ" type="button" onclick="EdorUWDetailQuery()">  -->

      <input class="cssButton" value="  ��  ��  " type="button" onClick="returnParent();">
    </table>
    </div>

		<!--������-->
    <div id = "divHidden" style = "display:none" >   
  	</div>

  </form>

  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
