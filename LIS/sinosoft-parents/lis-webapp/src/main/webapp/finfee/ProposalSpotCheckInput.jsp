<html>
<%
//name :ProposalSpotCheckInput.jsp
//Creator :ln
//date :2008-06-30
//���渴�˳�����
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.certify.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    GlobalInput tGlobalInput = (GlobalInput)session.getValue("GI");
    String Branch =tGlobalInput.ComCode;
    loggerDebug("ProposalSpotCheckInput","��ҳ���õ�½������"+Branch);
%>
	<head >
  	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src = "ProposalSpotCheckInput.js"></SCRIPT>
  	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  	<%@include file="ProposalSpotCheckInit.jsp"%>
	</head>
	
	<body  onload="initForm();" >
    <form action="./ProposalSpotCheckSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <%//query %>
    <table class="common">
    <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,maxbox);"></td>
		<td class=titleImg>���˳�����(������ֻ��¼��0��100֮�������)</td>
	</tr>
    </table>
    <div class="maxbox1" id="maxbox">
   	<Table class=common>  
   	   <TR class=common>
   			<TD class= title5>�����˾</TD>
   			<TD class= input5><input class="code" name="BussNo" id="BussNo"  readonly style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;"
            onClick="return showCodeList('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');"
            ondblclick="return showCodeList('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');" 
            onkeyup="return showCodeListKey('queryBPOID',[this], null, null,'<%=" #1# and bussnotype=#OF# "%>','1');"></TD>

        <TD  class= title5 >
          �������
        </TD>
        <TD  class= input5>
          <Input class= code  name=ManageCom id="ManageCom" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" onClick="return showCodeList('comcode',[this], null, null,'<%=" #1# and length(trim(ComCode))=4 and ComCode<>#8699# and comcode like #"+Branch+"%%# "%>','1');" onDblClick="return showCodeList('comcode',[this], null, null,'<%=" #1# and length(trim(ComCode))=4 and ComCode<>#8699# and comcode like #"+Branch+"%%# "%>','1');" onKeyUp="return showCodeListKey('comcode',[this], null, null,'<%=" #1# and length(trim(ComCode))=4 and ComCode<>#8699# and comcode like #"+Branch+"%%# "%>','1');">
        </TD>
   	   </TR>   		
       <TR class= common>
		<TD class= title5>������</TD>
        <TD class= input5><Input class= "common wid" name=checkRate id="checkRate" ></TD>
      	
		<td class= title5>�������</td>
        <td class= input5><input class="common wid" name=checkMax id="checkMax" ></td>
       </TR>        
     </table>
     <Br>
     <table>
       <TR  class= common>
    	 <td>
         <INPUT  VALUE="�� ��" TYPE=button class=cssButton onClick="save(1)"> </td>   
    	 <td>
         <INPUT  VALUE="�� ��" TYPE=button class=cssButton onClick="save(2)"> </td>
    	 <td>
         <INPUT  VALUE="ɾ ��" TYPE=button class=cssButton onClick="save(3)"> </td>
       </TR>
       <td><Input class= common type= hidden name=OperateType ></td>
    	 <td><Input class= common type= hidden name=BussNoa ></td>
    	 <td><Input class= common type= hidden name=ManageComa ></td>
    	 <td><Input class= common type= hidden name=checkRatea ></td>
    	 <td><Input class= common type= hidden name=checkMaxa ></td>
    	 <td><Input class= common type= hidden name=SelNo ></td>
   </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
