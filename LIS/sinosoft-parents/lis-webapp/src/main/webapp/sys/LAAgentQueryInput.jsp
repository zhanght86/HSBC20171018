<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�LAAgentQueryInput.jsp
//�����ܣ�ҵ��Ա������Ϣ��ѯ�б�ҳ��
//�������ڣ�2005-10-27
//������  �������


%>
<%
	String tContNo = "";
	try
	{
		tContNo = request.getParameter("ContNo");
	}
	catch( Exception e )
	{
		tContNo = "";
	}
%>
<head>
<script>
	var tContNo = "<%=tContNo%>";

</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="ChannelQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="ChannelQueryInit.jsp"%>

	<title>ҵ��Ա������Ϣ��ѯ��� </title>
</head>

<body  onload="initLAAgentForm();" >
  <form  method=post name=fm1 id=fm1 target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCClaim1);">
    	</td>
			<td class= titleImg>
				ҵ��Ա������Ϣ��ѯ���
			</td>
		</tr>
	</table>
	
  	<Div  id= "divLCClaim1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanLAAgentGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
    	
    	
    	<table align="center">
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage2.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage2.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage2.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage2.lastPage();"></td>
            </tr>
        </table>

       <table>
            <tr>
                <td><INPUT class=cssButton VALUE="��   ��" TYPE=button onclick="GoBack();"></td>
                
            </tr>
        </table>     
  	</div>

  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>


