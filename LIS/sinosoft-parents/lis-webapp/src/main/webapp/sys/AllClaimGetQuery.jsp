<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-4-2
//������  ��lh
//�޸��ˣ�������
//�޸�ʱ��:2004-2-17
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tInsuredNo = "";
	try
	{
		tInsuredNo = request.getParameter("InsuredNo");
	}
	catch( Exception e )
	{
		tInsuredNo = "";
	}
%>
<head>
<script>
	var tInsuredNo = "<%=tInsuredNo%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="AllClaimGetQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllClaimGetQueryInit.jsp"%>

	<title>�����ѯ </title>
</head>

<body  onload="initForm();" >
  <form action="./AllClaimGetQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
  <table >
    	<tr>
    	<td class=common>
			<IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				��������Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title>
            �����˱���
          </TD>
          <TD  class= input>
            <Input class= "common wid" name=InsuredNo id=InsuredNo >
          </TD>
		   <TD  ></TD>
		   <TD  ></TD>
		</TR>
     </table>
  </Div>


    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCClaim1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLCClaim1" style= "display: ''; text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��ҳ" class = cssButton90 TYPE=button onclick="turnPage.firstPage();">
      <INPUT VALUE="��һҳ" class = cssButton90 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="��һҳ" class = cssButton90 TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="βҳ" class = cssButton90 TYPE=button onclick="turnPage.lastPage();">
  	</div>
  	  <TD class= common>
      <input class=cssButton type=button value="��ѯ" onclick="ShowDetailInfo()">
      </TD>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


