<html>
<%
//�������ƣ����ݽӿ����ù���
//�����ܣ�
//�������ڣ�2008-8-5
//������  �����
%>
<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
</script>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--�û�У����-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="FIDataBaseLinkQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIDataBaseLinkQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
<form action="./FIDataBaseLinkQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
		<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIDataBaseLinkQuery);">
    </IMG>
    <td class=titleImg>
      ��ѯ����
      </td>
    </tr>
  </table>
  
   <Div id= "divFIDataBaseLinkQuery" style= "display: ''">
   <div class="maxbox">
   		<Table class= common>
		<TR  class= common>
			<TD  class= title5>�ӿڱ���</TD>
			<TD  class= input5><Input class="wid" class=common name=InterfaceCode id=InterfaceCode ></TD>
			<TD class="title5">����Ա</TD>
      <TD class="input5">
      <input class="wid" class="readonly" readonly name="Operator"  id="Operator"></TD>
		</TR>
		 <TR  class= common>
			<TD  class= title5>�ӿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=InterfaceName id=InterfaceName ></TD>
			<TD  class= title5>���ݿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=DBType id=DBType ></TD>
		</TR>
  	<TR  class= common>
			<TD  class= title5>IP</TD>
			<TD  class= input5><Input class="wid" class=common name=IP id=IP ></TD>
			<TD  class= title5>�˿ں�</TD>
			<TD  class= input5><Input class="wid" class=common name=Port id=Port ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>���ݿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=DBName id=DBName ></TD>
			<TD  class= title5>��������</TD>
			<TD  class= input5><Input class="wid" class=common name=ServerName id=ServerName ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>�û���</TD>
			<TD  class= input5><Input class="wid" class=common name=UserName id=UserName ></TD>
			<TD  class= title5>����</TD>
			<TD  class= input5><Input class="wid" class=common name=PassWord id=PassWord ></TD>
		</TR>
		<TR  class= common>
			<TD class="title5">�������</TD>
      <TD class="input5">
      <input class="wid" class="readonly" readonly name="ManageCom"></TD>
		</TR>
   </Table>
   	<!--<INPUT VALUE="��  ѯ" TYPE=button onclick="submitForm();" class="cssButton">-->
     <a href="javascript:void(0);" class="button" onClick="submitForm();">��    ѯ</a>
		</div>
  	</Div>
  	 <table>
    	<tr>
        	<td class=common>
		<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFIDataBaseLinkGrid);">
    		</td>
    		<td class= titleImg>
    			 ��ѯ���
    		</td>
    	</tr>
    </table>
  	<Div  id= "divFIDataBaseLinkGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFIDataBaseLinkGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
        <!--<div align="left"><INPUT VALUE="��  ��" TYPE=button onclick="ReturnData();" class="cssButton"></div>-->
      <div align="center"><INPUT VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton93"></div>
  	</div>
    <a href="javascript:void(0);" class="button" onClick="ReturnData();">��    ��</a>
      <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
