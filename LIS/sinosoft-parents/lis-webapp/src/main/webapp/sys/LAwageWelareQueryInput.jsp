<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�LAwageWelareQueryInput.jsp
//�����ܣ�ѪԵ��ϵ��Ϣ��ѯ�б�ҳ��
//�������ڣ�2005-10-28
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

	<title>������ʷ��Ϣ��ѯ��� </title>
</head>

<body  onload="initLAwageWelareForm();" >
  <form  method=post name=fm id=fm target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCClaim1);">
    	</td>
			<td class= titleImg>
				������ʷ��Ϣ��ѯ���
			</td>
		</tr>
	</table>
	
  	<Div  id= "divLCClaim1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanLAwageWelareGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
    	
    	
    	<table align="center">
            <tr>
                <td><INPUT class=cssButton90 VALUE="��  ҳ" TYPE=button onclick="turnPage6.firstPage();"></td>
                <td><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage6.previousPage();"></td>
                <td><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage6.nextPage();"></td>
                <td><INPUT class=cssButton93 VALUE="β  ҳ" TYPE=button onclick="turnPage6.lastPage();"></td>
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
  <br/><br/><br/><br/>
</body>
</html>


