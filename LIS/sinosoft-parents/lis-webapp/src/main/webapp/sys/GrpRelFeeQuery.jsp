<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-12-24 11:10:36
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tGrpContNo = "";
	try
	{
		tGrpContNo = request.getParameter("GrpContNo");
	}
	catch( Exception e )
	{
		tGrpContNo = "";
	}
	String tRiskCode = "";
	try
	{
		tRiskCode = request.getParameter("RiskCode");		
	}
	catch( Exception e )
	{
		tRiskCode = "";
	}
		String tInsuredName = "";
	try
	{
		tInsuredName = request.getParameter("InsuredName");
		//tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tInsuredName = "";
	}
		String tAppntName = "";
	try
	{
		tAppntName = request.getParameter("AppntName");
		//tAppntName = new String(tAppntName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tAppntName = "";
	}
%>
<head >
<script> 
	var tGrpContNo = "<%=tGrpContNo%>";
	var tRiskCode = "<%=tRiskCode%>";
	var tInsuredName = "<%=tInsuredName%>";
	var tAppntName = "<%=tAppntName%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="GrpRelFeeQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="GrpRelFeeQueryInit.jsp"%>
	 
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>���Ѳ�ѯ </title>
</head>
<body  onload="initForm();easyQueryClick();" >

  <form  name=fm id="fm">
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				
				�����ͬ��Ϣ
			</td>
		</tr>
	</table>
	<div class="maxbox1">
	<Div  id= "divLCPol1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            �����ͬ����
          </TD>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=GrpContNo id="GrpContNo">
          </TD>
          <TD  class= title5>
            Ͷ����λ����
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AppntName id="AppntName">
          </TD>
        </TR>		
     </table>
  </Div>
  </div>
  
    <!-- <INPUT class=cssButton VALUE="������ϸ"  TYPE=button onclick="getQueryDetail();"> -->
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPay1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class ="cssButton90"  TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class ="cssButton91" TYPE=button onclick="turnPage.previousPage();"> 	
      <INPUT VALUE="��һҳ" class ="cssButton92" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class ="cssButton93"  TYPE=button onclick="turnPage.lastPage();">			
    </Div>
  	<a href="javascript:void(0)" class=button onclick="getQueryDetail();">������ϸ</a>
  	<a href="javascript:void(0)" class=button onclick="goback();">��  ��</a>
  	<!-- <INPUT VALUE="��  ��" class=cssButton TYPE=button onclick="goback();"> -->
  	<Input type= "hidden" class= "readonly" readonly name=RiskCode id="RiskCode">
  	<Input type= "hidden" class= "readonly" readonly name=InsuredName id="InsuredName">
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>


</html>


