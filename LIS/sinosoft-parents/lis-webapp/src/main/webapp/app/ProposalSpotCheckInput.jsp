<html>
<%
//name :ProposalSpotCheckInput.jsp
//Creator :����
//date :2007-07-26
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
  <table class=common>
	  <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
      </td>
  		<td class=titleImg>���˳�����(������ֻ��¼��0��100֮�������)</td>
  	</tr>
  </table>
  <div class="maxbox">
  <Div  id= "divFCDay" style= "display: ''">
   	<Table class=common>  
   	   <TR class=common>
   			<TD class= title5>���������</TD>
   			<TD class= input5><input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name="BussNo" id="BussNo"  readonly
            onclick="return showCodeList('queryBPOID',[this],null,null,'1 and bussnotype=#TB#','1');" ondblclick="return showCodeList('queryBPOID',[this],null,null,'1 and bussnotype=#TB#','1');" 
            onkeyup="return showCodeListKey('queryBPOID',[this],null,null,'1 and bussnotype=#TB#','1');"></TD>
        <TD  class= title5>���ֱ���</TD>
        <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class="code" name=RiskCode id="RiskCode" onclick="return showCodeList('riskcode',[this]);" ondblclick="return showCodeList('riskcode',[this]);" onkeyup="return showCodeListKey('riskcode',[this]);">
        </TD>
   	   </TR>   		
       <TR class= common>
		    <TD class= title5>������</TD>
        <TD class= input5><Input class= "common wid" name=checkRate id="checkRate"></TD>
		    <td class= title5>�������</td>
        <td class= input5><input class= "common wid" name=checkMax id="checkMax"></td>
       </TR>        
       <tr class= common>   
        <TD  class= title5 >�������</TD>
        <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png)     no-repeat right center"  class= code  name=ManageCom id="ManageCom" onclick="return showCodeList('comcode',[this]);" ondblclick="return showCodeList('comcode',[this]);" onkeyup="return showCodeListKey('comcode',[this]);">
        </TD>
       </tr>
    </table>
    <table>
      <TR class= common>
        <TD class= title5 style="width:4.2%;">��ע</TD>
        <TD class= input5><textarea name="Remark" cols="145%" rows="4" witdh=100% class="common"></textarea></TD>
      </TR>
 		</table>
  </Div>
  </div>
  <a href="javascript:void(0)" class=button onclick="submitForm();">��  ��</a>
  <!-- <INPUT  VALUE="����" TYPE=button onclick="submitForm();"> -->
  <Input class= common type= hidden name=OperateType id="OperateType">
   </form>
   <br>
   <br>
   <br>
   <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>

</html>
