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
    var tIsCancelPolFlag = "0" ;
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="PolQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="ProposalQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>Ͷ������ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm  id="fm">
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
          <TD  class= title>
            �ͻ�����
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
	else if (tFlag.equals("Agent"))
		{
%>       
         <TD  class= title>
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
  </Div>
  
  <table>
        <tr>		
		<td>
         <INPUT class=cssButton VALUE="Ͷ������ϸ��ѯ" TYPE=button onClick="PolClick1();"> 	</td>
   		</tr>
  </table>
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divProposal1);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divProposal1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
<center>    	
    	<table>	
    		<tr>
	    	<td> <INPUT class=cssButton90 VALUE="��ҳ" TYPE=button onClick="turnPage.firstPage();"> </td>
	      	<td>  <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onClick="turnPage.previousPage();"> 					</td>
	      	<td>  <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onClick="turnPage.nextPage();"> </td>
	      	<td> <INPUT class=cssButton93 VALUE="βҳ" TYPE=button onClick="turnPage.lastPage();">			</td>
      	</tr>
      	
  		</table>
</center>  				
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

var sqlid1="ProposalQuerySql1";
var mySql1=new SqlClass();
mySql1.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql1.setSqlId(sqlid1); //ָ��ʹ�õ�Sql��id
mySql1.addSubPara(tCustomerNo); //ָ������Ĳ���
strSQL=mySql1.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName from LCCont where AppntNo='" + tCustomerNo + "' and AppFlag='0'";			 
//	alert(strSQL);
	
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>	
var sqlid2="ProposalQuerySql2";
var mySql2=new SqlClass();
mySql2.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql2.setSqlId(sqlid2); //ָ��ʹ�õ�Sql��id
mySql2.addSubPara(tCustomerNo); //ָ������Ĳ���
strSQL=mySql2.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName from LCCont where AgentCode='" + tCustomerNo + "' and AppFlag='0'";		
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


