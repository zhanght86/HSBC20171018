<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWPastInfo.jsp
//�����ܣ�����˱�δ��ԭ���ѯ
//�������ڣ�2008-11-28 16:12:00
//������  ��liuqh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html> 
<%
  String tGrpContNo = "";
  tGrpContNo = request.getParameter("GrpContNo");
//  loggerDebug("UWPastInfo","!!!tGrpContNo:" + tGrpContNo);
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWPastInfo.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="UWPastInfoInit.jsp"%>
  <title>����Ͷ�����⸶�����ѯ </title>
</head>
<body  onload="initForm('<%=tGrpContNo%>');" >
  <form method=post name=fm id="fm" target="fraSubmit">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- �����˱���¼���֣��б� -->
   <!--
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 �Զ��˱���Ϣ 
    		</td>
    	</tr>
    </table>
   -->
	<Div  id= "divLCUWSub1" style= "display: ''" align=center>
    	<table  class= common >
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWInfoGrid" >
					</span> 
				</td>
			</tr>
		</table>
<!--
      <INPUT VALUE="��  ҳ" class="cssButton" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class="cssButton" TYPE=button onclick="getLastPage();"> 
  -->
	</div>
	<hr class="line">
	<Div  id= "divLCUWSub1" style= "display: ''" align=center>
    	<table  class= common >
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWSubInfoGrid" >
					</span> 
				</td>
			</tr>
		</table>
<!--
      <INPUT VALUE="��  ҳ" class="cssButton" TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class="cssButton" TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class="cssButton" TYPE=button onclick="getLastPage();"> 
  -->
	</div>
  <a href="javascript:void(0)" class=button onclick="parent.close();">��  ��</a>
    <!-- <INPUT VALUE="��  ��"class="cssButton"  TYPE=button onclick="parent.close();"> 					 -->
    <INPUT type= "hidden" name= GrpContNo id="GrpContNo" value= "">				
    <!--��ȡ��Ϣ-->
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
