<html>
<%
//�������� :AccountantPeriodInput.jsp
//������ :����ڼ����
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
<SCRIPT src = "AccountantPeriodInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AccountantPeriodInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./AccountantPeriodSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
 
 		<table>
    	<tr>
         	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAccountantPeriod);"></td>
    		 <td class= titleImg>
        		����ڼ����
       		 </td>   		 
    	</tr>
    </table>
     <Div id= "divAccountantPeriod" style= "display: ''"><div class="maxbox">
   	<Table class= common>
		<TR  class= common>
			<TD  class= title5>������</TD>
			<TD  class= input5><Input class="wid" class=common name=Year id=Year elementtype=nacessary verify="���|NOTNULL&INT&len=4" >(��:2008)</TD>
			<TD  class= title5>����¶�</TD>
			<TD   class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name= Month id= Month verify="�¶�ֹ��|NOTNULL"   CodeData="0|^01|1��^02|2��^03|3��^04|4��^05|5��^06|6��^07|7��^08|8��^09|9��^10|10��^11|11��^12|12��" ondblClick="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('Month',[this],[0,1],null,null,null,[1]);" readonly=true>(��:08)</TD>
		</TR>
		 <TR  class= common>   
          		<TD  class= title5 width="25%">�¶�����</TD>
          		<TD  class= input5 width="25%"><!--<Input class= "coolDatePicker" dateFormat="short" name=StartDay elementtype=nacessary verify="�¶�����|notnull">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="�¶�����|notnull" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                 </TD>      
          		<TD  class= title5 width="25%">�¶�ֹ��</TD>
          		<TD  class= input5 width="25%"><!--<Input class= "coolDatePicker" dateFormat="short" name=EndDay elementtype=nacessary verify="�¶�ֹ��|notnull">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="�¶�ֹ��|notnull" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>  
     	 </TR> 
  		<TR  class= common>
    		<TD class="title5">����Ա</TD>
          	<TD class="input5">
          	<input class="wid" class="readonly" readonly name="Operator" id="Operator" readonly=true ></TD>
          	<TD class= title5>
          	״̬
        	</TD>
        	<TD class= input5>
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= State id= State verify="״̬|NOTNULL"  CodeData="0|^0|δ����^1|����" ondblClick="showCodeListEx('State',[this,StateName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('State',[this,StateName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('State',[this,StateName],[0,1],null,null,null,[1]);" readonly=true ><input class=codename name=StateName id=StateName readonly=true elementtype=nacessary>
         	</TD>
  		</TR>
   </Table>
   </Div>
   </Div>
   <input type=hidden id="OperateType" name="OperateType">
		<INPUT class=cssButton name="addbutton" VALUE="��  ��"  TYPE=button onclick="return submitForm();">
  	<INPUT class=cssButton name="updatebutton" VALUE="��  ��"  TYPE=button onclick="return updateClick();">
  	<INPUT class=cssButton name="querybutton" VALUE="��  ѯ"  TYPE=button onclick="return queryClick();">
  	<INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteClick();">
  	<INPUT class=cssButton name="resetbutton" VALUE="��  ��"  TYPE=button onclick="return resetAgain();">
    <!--<a href="javascript:void(0);" name="addbutton" class="button" onClick="return submitForm();">��    ��</a>
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">��    ��</a>
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">��    ѯ</a>
    <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">ɾ    ��</a>
    <a href="javascript:void(0);" name="resetbutton" class="button" onClick="return resetAgain();">��    ��</a>-->

</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
