<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�
//������  ��Howie
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tContNo = "";
	String tPolNo = "";
	String tIsCancelPolFlag = "";
	try
	{
		tContNo = request.getParameter("ContNo");
		tPolNo = request.getParameter("PolNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
	}
	catch( Exception e )
	{
		tContNo = "";
		tPolNo = "";
		tIsCancelPolFlag = "";
	}
%>
<head>
<script> 
	var tContNo = "<%=tContNo%>";
	var tPolNo = "<%=tPolNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
	<SCRIPT src="PayPremQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PayPremQueryInit.jsp"%>	
	<title>Ƿ�����Ѳ�ѯ </title>
</head>

<body  onload="initForm();" >
  <form  name=fm id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				������Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            ��������
          </TD>
          <TD  class= input>
            <Input class= "readonly wid" readonly name=ContNo id=ContNo >
          </TD>
           <TD  class= title>
            ���ֺ���
          </TD>
          <TD  class= input>
          	<Input class= "readonly wid" readonly name=PolNo id=PolNo >
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
    			 ��������Ϣ
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
  	
    <INPUT class=cssButton VALUE=" ���� " TYPE=button onclick="returnParent();">

  </form>
 <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
 
<br/><br/><br/><br/>
</body>
</html>


