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
	String tCustomerNo = "";
	try
	{
		tCustomerNo = request.getParameter("CustomerNo");
	}
	catch( Exception e )
	{
		tCustomerNo = "";
	}
	String tName = "";
	try
	{
		tName = request.getParameter("Name");
		tName = new String(tName.getBytes("ISO-8859-1"), "GBK");
	}
	catch( Exception e )
	{
		tName = "";
	}
	String tFlag = "";
	try
	{
		tFlag = request.getParameter("Flag");
	}
	catch( Exception e )
	{
		tFlag = "";
	}
%>
<head >
<script> 
	var tCustomerNo = "<%=tCustomerNo%>";
	var tName = "<%=tName%>";
	var tIsCancelPolFlag = "1" ;
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="DesPolQuery.js"></SCRIPT>
	<SCRIPT src="AllProposalQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="DesPolQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>����������ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id="fm" >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLDPerson1);">
    	</td>
			<td class= titleImg>
				�ͻ���Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLDPerson1" class="maxbox1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
<% 
	if (tFlag.equals("Customer"))
		{
%>
          <TD  class= title5>
            �ͻ�����
          </TD>
<%
		}
	else if (tFlag.equals("AppntCustomer"))
		{
%>
					<TD  class= title5>
            Ͷ���˱���
          </TD>
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>
					<TD  class= title5>
            �����˱���
          </TD>
<%
		}
%>
          <TD  class= input5>
          	<Input class= "readonly wid" readonly name=CustomerNo id="CustomerNo" >
          </TD>
  <% 
	if (tFlag.equals("Customer"))
		{
  %>
          <TD  class= title5>
            �ͻ�����
          </TD>
   <%
		}
	else if (tFlag.equals("AppntCustomer"))
		{
%>       
         <TD  class= title5>
            Ͷ��������
          </TD>
   <%
		}
	else if (tFlag.equals("Agent"))
		{
%>       
         <TD  class= title5>
            ����������
          </TD>
          <%
		}
%>
          <TD  class= input5>
            <Input class= "readonly wid" readonly name=Name id="Name" >
          </TD>
        </TR>
	</table>
	<table>
        <tr>		
		<!--<td> <INPUT class=cssButton VALUE="���Ѳ�ѯ" TYPE=button onclick="FeeQueryClick();"> </td> -->
		<!--<td> <INPUT class=cssButton VALUE="������ѯ" TYPE=button onclick="GetQueryClick();"> 	</td>-->
		<!--<td> <INPUT class=cssButton VALUE="���Ĳ��˷Ѳ�ѯ" TYPE=button onclick="EdorQueryClick();"> </td>-->
		<td> 
        <INPUT class=cssButton VALUE="������ϸ��ѯ" TYPE=button onClick="PolClick_B();"> </td>
		<!--<td> <INPUT class=cssButton VALUE="ɨ�����ѯ" TYPE=button onclick="ScanQuery();"> 	</td> </tr>-->
  </table>
  </Div>

      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divDesPol1);">
    		</td>
    		<td class= titleImg>
    			 ����������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divDesPol1" style= "display: '';text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT class=cssButton90 VALUE="��ҳ" class=common TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssButton91 VALUE="��һҳ" class=common TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssButton92 VALUE="��һҳ" class=common TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class=cssButton93 VALUE="βҳ" class=common TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
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
<% 
	if (tFlag.equals("Customer"))
		{
%>		

var sqlid22="ProposalQuerySql22";
var mySql22=new SqlClass();
mySql22.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql22.setSqlId(sqlid22); //ָ��ʹ�õ�Sql��id
mySql22.addSubPara(tCustomerNo ); //ָ������Ĳ���
strSQL=mySql22.getString();

	//strSQL = "select GrpPolNo,PolNo,PrtNo,RiskCode,InsuredName,AppntName from LBPol where InsuredNo='" + tCustomerNo + "' and mainpolno = polno ";			 
	//alert(strSQL);
<%
		}
	else if (tFlag.equals("AppntCustomer"))
		{
%>

var sqlid23="ProposalQuerySql23";
var mySql23=new SqlClass();
mySql23.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql23.setSqlId(sqlid23); //ָ��ʹ�õ�Sql��id
mySql23.addSubPara(tCustomerNo ); //ָ������Ĳ���
strSQL=mySql23.getString();

	//strSQL = "select GrpPolNo,PolNo,PrtNo,RiskCode,InsuredName,AppntName from LBPol where AppntNo='" + tCustomerNo + "' and mainpolno = polno ";			 
	//alert(strSQL);
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>	
var sqlid24="ProposalQuerySql24";
var mySql24=new SqlClass();
mySql24.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql24.setSqlId(sqlid24); //ָ��ʹ�õ�Sql��id
mySql24.addSubPara(tCustomerNo ); //ָ������Ĳ���
strSQL=mySql24.getString();

	//strSQL = "select GrpPolNo ,PolNo,PrtNo,RiskCode,InsuredName,AppntName from LBPol where AgentCode='" + tCustomerNo + "' and mainpolno = polno";		
//	alert(strSQL);
<%
		}
%>		
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


