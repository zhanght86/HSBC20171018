<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�
//������  ��
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
		tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"),"GBK");
		loggerDebug("RelGetQuery","sxy--sxy : "+tInsuredName );
	}
	catch( Exception e )
	{
		tInsuredName = "";
	}
		String tAppntName = "";
	try
	{
		tAppntName = request.getParameter("AppntName");
		tAppntName = new String(tAppntName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tAppntName = "";
	}
%>
<head>
<script> 
	var tContNo = "<%=tContNo%>";
	var tPolNo = "<%=tPolNo%>";
	var tRiskCode = "<%=tRiskCode%>";
	var tInsuredName = "<%=tInsuredName%>";
	var tAppntName = "<%=tAppntName%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">	
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="RelGetQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="RelGetQueryInit.jsp"%>

	<title>������ѯ </title>
</head>

<body  onload="initForm();">
  <form  name=fm id=fm>
  <table>
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
            <Input class="readonly wid"  readonly name=ContNo id=ContNo >
          </TD>
          <TD  class= title>
            �������ֺ���
          </TD>
          <TD  class= input>
          	<Input class="readonly wid" readonly name=PolNo id=PolNo >
          </TD>
          <TD  class= title>
            ���ֱ���
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=RiskCode id=RiskCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            ����������
          </TD>
          <TD  class= input>
          	<Input class="readonly wid" readonly name=InsuredName id=InsuredName >
          </TD>
          <TD  class= title>
            Ͷ��������
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=AppntName id=AppntName >
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
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGet1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAGet1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
   
     <Div id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="��ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
    </Div>
      <!--INPUT class=cssButton VALUE="������ϸ" TYPE=button onclick="getQueryDetail();"-->
      <INPUT class=cssButton VALUE=" ���� " TYPE=button onclick="returnParent();">
  	</Div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>


