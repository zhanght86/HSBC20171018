<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<html>
<head>
<script language="javascript">

	
	var grpcontno = "<%=request.getParameter("grpcontno")%>";  //�жϴӺδ����뱣��¼�����,�ñ�����Ҫ�ڽ����ʼ��ǰ����
	   
</script>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>

	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

	<SCRIPT src="ContPlanDeleteInput.js"></SCRIPT>
	<SCRIPT src="SpecDealByRisk.js"></SCRIPT> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

</head>

<body>
  <form action="./ContPlanDeleteInsuredSave.jsp" method=post name=fm id="fm" target="fraSubmit">
   

    <div id= "divRiskCode0" style="display:''">
      <div class="maxbox1">
      <table class=common>
         <tr class=common>
            <TD  class= title>
              ���ϼƻ�
            </TD>
            <TD  class= input>
             
              <Input style="background:url(../common/images/select--bg_03.png)    no-repeat right center" class="codeno" name="ContPlan" id="ContPlan" onclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" ondblclick="getaddresscodedata();return showCodeListEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);" onkeyup="getaddresscodedata();return showCodeListKeyEx('GetAddressNo',[this,AddressNoName],[0,1],'', '', '', true);"><input class=codename name=AddressNoName id="AddressNoName" readonly=true>
              </TD>
            
         </tr>
         <!-- <INPUT class=cssButton name="deletebutton" VALUE="ɾ  ��"  TYPE=button onclick="return deleteall();"> -->
      </table>
      </div>
      <a href="javascript:void(0)" class=button name="deletebutton" onclick="return deleteall();">ɾ  ��</a>
      <table class=common>
			<TR>
				<TD id="info" width='100%' style="font:10pt"></TD>
			</TR>
		</table>
    </div>

   
     
    <input type=hidden id="GrpContNo" name="GrpContNo" value="">

	  
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <span id="spanApprove"  style="display: none; position:relative; slategray"></span>
</body>
</html>


