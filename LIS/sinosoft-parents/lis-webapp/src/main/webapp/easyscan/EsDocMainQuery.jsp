<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�EsDocMainQuery.jsp
//�����ܣ�
//�������ڣ�2004-06-02
//������  ��LiuQiang
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="./EsDocMainQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./EsDocMainQueryInit.jsp"%>
  <title>ɨ�����֤��Ϣ���� </title>
</head>
<body  onload="initForm();" >
  <form action="./EsDocMainQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <table>
      <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid1);">
      </td>
      <td class= titleImg>
        ���������ѯ������ 
      </td>
    	</tr>   
    </table>
  <Div  id= "divCodeGrid1" class=maxbox1 style= "display: ''">    
  <table  class= common>
  <TR  class= common>
    <TD  class= title5>ӡˢ����</TD>
    <TD  class= input5><Input class="common wid" name=DOC_CODE id=DOC_CODE ></TD>
	<TD  class= title5></TD>
    <TD  class= input5></td>
  </TR>
</table>
 </Div>
          <INPUT VALUE="��  ѯ" TYPE=button class= cssButton onclick="easyQueryClick();"> 
          <INPUT VALUE="��  ��" TYPE=button class= cssButton onclick="returnParent();"> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 ɨ�����֤��Ϣ������
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" TYPE=button class= cssButton90 onclick="getFirstPage();"> 
      <INPUT VALUE="��һҳ" TYPE=button class= cssButton91 onclick="getPreviousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE=button class= cssButton92 onclick="getNextPage();"> 
      <INPUT VALUE="β  ҳ" TYPE=button class= cssButton93 onclick="getLastPage();"> 					
  	</div>
  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
