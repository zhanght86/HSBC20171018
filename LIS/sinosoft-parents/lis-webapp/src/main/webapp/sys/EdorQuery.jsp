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
	String tPolNo = "";
	try
	{
		tPolNo = request.getParameter("PolNo");
	}
	catch( Exception e )
	{
		tPolNo = "";
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
		tInsuredName = new String(tInsuredName.getBytes("ISO-8859-1"), "GBK");
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
<head >
<script> 
	var tPolNo = "<%=tPolNo%>";
	var tRiskCode = "<%=tRiskCode%>";
	var tInsuredName = "<%=tInsuredName%>";
	var tAppntName = "<%=tAppntName%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="EdorQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="EdorQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
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
          <TD  class= title5>
            ��������
          </TD>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=PolNo  id=PolNo>
          </TD>
          <TD  class= title5>
            ���ֱ���
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=RiskCode id=RiskCode >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            ����������
          </TD>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=InsuredName id=InsuredName >
          </TD>
          <TD  class= title5>
            Ͷ��������
          </TD>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=AppntName  id=AppntName>
          </TD>
        </TR>
     </table>
  </Div>  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdor1);">
    		</td>
    		<td class= titleImg>
    			 ��ȫ�ɷ���Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divEdor1" style= "display: ''; text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��ҳ" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	
	//alert("here");
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";

	var sqlid42="ProposalQuerySql42";
	var mySql42=new SqlClass();
	mySql42.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql42.setSqlId(sqlid42); //ָ��ʹ�õ�Sql��id
	mySql42.addSubPara(comCode.substring(0,6) ); //ָ������Ĳ���
	strSQL=mySql42.getString();
	
	//strSQL = "select ActuGetNo,EndorsementNo,FeeOperationType,FeeFinaType,GetDate,GetMoney,RiskCode,ManageCom,AgentCom,GrpName,PolType,GetFlag from LJAGetEndorse where PolNo='" + tPolNo + "'";			 
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("���ݿ���û���������������ݣ�");
    //alert("��ѯʧ�ܣ�");
    return false;
  }
  
  //��ѯ�ɹ������ַ��������ض�ά����
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
  
  //���ó�ʼ������MULTILINE����
  turnPage.pageDisplayGrid = PolGrid;    
          
  //����SQL���
  turnPage.strQuerySql     = strSQL; 
  
  //���ò�ѯ��ʼλ��
  turnPage.pageIndex = 0;  
  
  //�ڲ�ѯ���������ȡ������ҳ����ʾ��С���õ�����
  var arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  
  //����MULTILINE������ʾ��ѯ���
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
  
}
</script>
</html>


