<html>
<%
//�������� :FIDataBaseLinkInput.jsp
//������ :���ݽӿ����ù���
//������ :���
//�������� :2008-08-04
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//���ҳ��ؼ��ĳ�ʼ����
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT> 
<SCRIPT src = "FIDataBaseLinkInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIDataBaseLinkInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./FIDataBaseLinkSave.jsp" method=post name=fm target="fraSubmit">
  
  
		<table>
    	<tr>
        	<td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divFIDataBaseLink);"></td>
    		 <td class= titleImg>
        		���ݽӿ����ù���
       		 </td>   		 
    	</tr>
    </table>
    <Div id= "divFIDataBaseLink" style= "display: ''"><div class="maxbox1">
   	<Table class= common>
		<TR  class= common>
			<TD  class= title5>�ӿڱ���</TD>
			<TD  class= input5><Input class="wid" class=common name=InterfaceCode id=InterfaceCode elementtype=nacessary ></TD>
			<TD class="title5">����Ա</TD>
      <TD class="input5">
      <input class="wid" class="readonly" readonly name="Operator" id="Operator" readonly=true ></TD>
		</TR>
		 <TR  class= common>
			<TD  class= title5>�ӿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=InterfaceName id=InterfaceName ></TD>
			<TD  class= title5>���ݿ�����</TD>
			<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name= DBType id= DBType verify="���ݿ�����|NOTNULL"   CodeData="0|^ORACLE|ORACLE^INFORMIX|INFORMIX^SQLSERVER|SQLSERVER^WEBLOGICPOOL|WEBLOGICPOOL" ondblClick="showCodeListEx('DBType',[this],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('DBType',[this],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('DBType',[this],[0,1],null,null,null,[1]);" readonly=true></TD>
		</TR>
	</table>
    </div>
 </div>
	<hr class="line"></hr>
	
	<Div  id= "ORACLEdiv" style= "display: none" align=left>
	<table class=common>
		<TR  class= common>
			<TD  class= title5>IP</TD>
			<TD  class= input5><Input class="wid" class=common name=IP1 id=IP1 elementtype=nacessary ></TD>
			<TD  class= title5>�˿ں�</TD>
			<TD  class= input5><Input class="wid" class=common name=Port1 id=Port1 elementtype=nacessary ></TD>
		</TR>
	 <tr class= common>
			<TD  class= title5>���ݿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=DBName1 id=DBName1 elementtype=nacessary ></TD>
			<TD  class= title5>��������</TD>
			<TD  class= input5><Input class="wid" class=common name=ServerName1 id=ServerName1 ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>�û���</TD>
			<TD  class= input5><Input class="wid" class=common name=UserName1 id=UserName1 elementtype=nacessary ></TD>
			<TD  class= title5>����</TD>
			<TD  class= input5><Input class="wid" class=common name=PassWord1 id=PassWord1 elementtype=nacessary ></TD>
		</TR>
 </table> 	  
</div>

<Div  id= "INFORMIXdiv" style= "display: none" align=left>
	<table class=common>
  	<TR  class= common>
			<TD  class= title5>IP</TD>
			<TD  class= input5><Input class="wid" class=common name=IP2 id=IP2 elementtype=nacessary ></TD>
			<TD  class= title5>�˿ں�</TD>
			<TD  class= input5><Input class="wid" class=common name=Port2 id=Port2 elementtype=nacessary ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>���ݿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=DBName2 id=DBName2 elementtype=nacessary ></TD>
			<TD  class= title5>��������</TD>
			<TD  class= input5><Input class="wid" class=common name=ServerName2 id=ServerName2 elementtype=nacessary ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>�û���</TD>
			<TD  class= input5><Input class="wid" class=common name=UserName2 id=UserName2 elementtype=nacessary ></TD>
			<TD  class= title5>����</TD>
			<TD  class= input5><Input class="wid" class=common name=PassWord2 id=PassWord2 elementtype=nacessary ></TD>
		</TR>
 </table> 	  
</div>

	<Div  id= "SQLSERVERdiv" style= "display: none" align=left>
	<table class=common>
		<TR  class= common>
			<TD  class= title5>IP</TD>
			<TD  class= input5><Input class="wid" class=common name=IP3 id=IP3 elementtype=nacessary ></TD>
			<TD  class= title5>�˿ں�</TD>
			<TD  class= input5><Input class="wid" class=common name=Port3 id=Port3 elementtype=nacessary ></TD>
		</TR>
	 <tr class= common>
			<TD  class= title5>���ݿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=DBName3 id=DBName3 elementtype=nacessary ></TD>
			<TD  class= title5>��������</TD>
			<TD  class= input5><Input class="wid" class=common name=ServerName3 id=ServerName3 ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>�û���</TD>
			<TD  class= input5><Input class="wid" class=common name=UserName3 id=UserName3 elementtype=nacessary ></TD>
			<TD  class= title5>����</TD>
			<TD  class= input5><Input class="wid" class=common name=PassWord3 id=PassWord3 elementtype=nacessary ></TD>
		</TR>
 </table> 	  
</div>

	<Div  id= "WEBLOGICPOOLdiv" style= "display: none" align=left>
	<table class=common>
		<TR  class= common>
			<TD  class= title5>IP</TD>
			<TD  class= input5><Input class="wid" class=common name=IP4 id=IP4 elementtype=nacessary ></TD>
			<TD  class= title5>�˿ں�</TD>
			<TD  class= input5><Input class="wid" class=common name=Port4 id=Port4 elementtype=nacessary ></TD>
		</TR>
	 <tr class= common>
			<TD  class= title5>���ݿ�����</TD>
			<TD  class= input5><Input class="wid" class=common name=DBName4 id=DBName4 elementtype=nacessary ></TD>
			<TD  class= title5>��������</TD>
			<TD  class= input5><Input class="wid" class=common name=ServerName4 id=ServerName4 ></TD>
		</TR>
		<TR  class= common>
			<TD  class= title5>�û���</TD>
			<TD  class= input5><Input class="wid" class=common name=UserName4 id=UserName4 ></TD>
			<TD  class= title5>����</TD>
			<TD  class= input5><Input class="wid" class=common name=PassWord4 id=PassWord4 ></TD>
		</TR>
 </table> 	  
</div>
   </Div>
   <input type=hidden id="OperateType" name="OperateType">
   <input type=hidden name=tManageCom value=''>
	 <!--<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">
   <INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
   <INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
   <INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
   <INPUT class=cssButton name="resetbutton" VALUE="��  ��"  TYPE=button onclick="return resetAgain();">--><br>
   <a href="javascript:void(0);" name="addbutton" class="button" onClick="return submitForm();">��    ��</a>
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">��    ��</a>
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">��    ѯ</a>
    <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" name="resetbutton" class="button" onClick="return resetAgain();">��    ��</a>
 
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
