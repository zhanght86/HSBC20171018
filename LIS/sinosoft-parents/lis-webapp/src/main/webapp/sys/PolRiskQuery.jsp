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
	String tContNo = "";
	String tPolNo = "";
	String tIsCancelPolFlag = "";
	String tPrtNo = "";
	try
	{
		//tContNo = request.getParameter("ContNo");
		//tPolNo = request.getParameter("PolNo");
		//tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
		tPrtNo = request.getParameter("prtNo");
	}
	catch( Exception e )
	{
		tContNo = "";
		tPolNo = "";
		tIsCancelPolFlag = "";
		tPrtNo = "";
	}
%>
<head>
<script> 
	var tPrtNo = "<%=tPrtNo%>";
	//alert(tPrtNo);
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="PolRiskQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolRiskQueryInit.jsp"%>
	
	<title>�������ֲ�ѯ </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id=fm >
	<Div  id= "divLCPol1" style= "display: none">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "common wid" readonly name=ContNo id=ContNo >
          </TD>
           <TD  class= title>
            ���ֺ���
          </TD>
          <TD  class= input>
          	<Input class= "common wid" readonly name=PolNo id=PolNo >
          </TD>
          <TD  class= title>
          </TD>
          <TD  class= input> 
          </TD>
          
		</TR>
     </table>
  </Div>
  
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPrem1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCPrem1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>			
  	</div>
  	
  	<Div id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="��ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
      <table class= common align=center>
    	<tr class= common>
    	  <td class= input align=lift>
    		<INPUT class=cssButton VALUE=" ��������Ϣ " TYPE=button onclick="PremQuery();">
    		<INPUT class=cssButton VALUE=" ���� " TYPE=button onclick="returnParent();">
    	  </td>
    	</tr>
      </table>
    </Div>
  	
  </form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


