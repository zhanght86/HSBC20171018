 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�PEdorUWErr.jsp
//�����ܣ���ȫ�Զ��˹��˱�δ��ԭ���ѯ
//�������ڣ�2002-06-19 11:10:36
//������  ��sxy
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="PEdorUWErr.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PEdorUWErrInit.jsp"%>
  <title>���պ˱���Ϣ</title>
</head>
<body  onload="initForm('<%=tContNo%>','<%=tEdorNo%>','<%=tEdorType%>');" >
  <form method=post name=fm id=fm target="fraSubmit" action= "./PEdorUWErrQuery.jsp">
    <%@include file="../common/jsp/InputButton.jsp"%>
    <!-- �����˱���¼���֣��б� -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCDuty1);">
    		</td>
    		<td class= titleImg>
    			 ���˱���Ϣ 
    		</td>
    	</tr>
    </table>
	<Div  id= "divLCUWSub1" style= "display: ''; text-align:center">
    	<table  class= common>
        	<tr  class= common>
    	  		<td text-align: left colSpan=1 >
					<span id="spanUWErrGrid" >
					</span> 
				</td>
			</tr>
		</table>
      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onclick="getLastPage();"> 
      <INPUT type= "hidden" name= "ContNoHide" id=ContNoHide value= "">		
      <INPUT type= "hidden" name= "EdorNoHide" id="EdorNoHide" value= "">	
      <INPUT type= "hidden" name= "EdorTypeHide" id="EdorTypeHide" value= "">				
	</div>
	<p>
        <INPUT VALUE="��  ��" class= cssButton TYPE=button onclick="parent.close();"> 					
    </P>
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
