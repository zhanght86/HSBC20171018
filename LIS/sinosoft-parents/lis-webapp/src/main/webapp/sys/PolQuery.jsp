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
	String tManageCom = "";
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
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput tG = new GlobalInput();
  tG=(GlobalInput)session.getValue("GI");
  if(tG == null) {
    out.println("session has expired");
    return;
  }
  tManageCom = tG.ManageCom;
  
	
	//String strOperator = globalInput.Operator;
	//loggerDebug("PolQuery","1:"+strOperator);
%>
  
<head >
<script> 
	var tCustomerNo = "<%=tCustomerNo%>";
	var tName = "<%=tName%>";
	var tManageCom = "<%=tManageCom%>";
	var tFlag = "<%=tFlag%>";
	var tIsCancelPolFlag = "0" ;
	
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="PolQuery.js"></SCRIPT>
	<SCRIPT src="AllProposalQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ѯ </title>
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
	if (tFlag.equals("Customer")||tFlag.equals("Customer2"))
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
	if (tFlag.equals("Customer")||tFlag.equals("Customer2"))
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
		<td> 
        <INPUT class=cssButton VALUE="������ϸ��ѯ" TYPE=button onClick="PolClick1();"> 	</td>
   		</tr>
  </table>
  </Div>
  
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol1);">
    		</td>
    		<td class= titleImg>
    			 ������Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divPol1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
<center>    	
      <INPUT VALUE="��  ҳ" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91  TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92  TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class=cssButton93 TYPE=button onClick="turnPage.lastPage();">				
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

var sqlid7="ProposalQuerySql7";
var mySql7=new SqlClass();
mySql7.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql7.setSqlId(sqlid7); //ָ��ʹ�õ�Sql��id

mySql7.addSubPara(tCustomerNo); //ָ������Ĳ���
mySql7.addSubPara(tManageCom); //ָ������Ĳ���
mySql7.addSubPara(tCustomerNo); //ָ������Ĳ���
mySql7.addSubPara(tManageCom); //ָ������Ĳ���

mySql7.addSubPara(tCustomerNo); //ָ������Ĳ���
mySql7.addSubPara(tManageCom); //ָ������Ĳ���
mySql7.addSubPara(tCustomerNo); //ָ������Ĳ���
strSQL=mySql7.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'��������'  from LCCont where InsuredNo='" + tCustomerNo + "' and AppFlag='1' and ManageCom like '" + tManageCom + "%%'"
	 //         + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'Ͷ����' from LCCont where AppntNo='" + tCustomerNo +"' and AppFlag='1' and ManageCom like '" + tManageCom + "%%'"
	 //         + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'������������' from LCCont where InsuredNo in(select MainCustomerNo from LCInsuredRelated where CustomerNo = '" + tCustomerNo + "' and AppFlag='1' and ManageCom like '" + tManageCom + "%%') and AppFlag='1' "
	 //         + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'������' from LCCont where InsuredNo in(select InsuredNo from LCBnf where CustomerNo = '" + tCustomerNo + "') and AppFlag='1' ";			 
	//alert(strSQL);                                                                                                                                        
<%
		}
	else if (tFlag.equals("Customer2"))
		{
%>	

var sqlid8="ProposalQuerySql8";
var mySql8=new SqlClass();
mySql8.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql8.setSqlId(sqlid8); //ָ��ʹ�õ�Sql��id

mySql8.addSubPara(tCustomerNo); //ָ������Ĳ���
mySql8.addSubPara(tManageCom); //ָ������Ĳ���
mySql8.addSubPara(tCustomerNo); //ָ������Ĳ���
mySql8.addSubPara(tManageCom); //ָ������Ĳ���

mySql8.addSubPara(tCustomerNo); //ָ������Ĳ���
mySql8.addSubPara(tManageCom); //ָ������Ĳ���
mySql8.addSubPara(tCustomerNo); //ָ������Ĳ���
strSQL=mySql8.getString();

	/*strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'��������' from LCCont where InsuredNo='" + tCustomerNo + "' and AppFlag='0' and ManageCom like '" + tManageCom + "%%' "
	        + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'Ͷ����' from LCCont where AppntNo='" + tCustomerNo +"' and AppFlag='0' and ManageCom like '" + tManageCom + "%%'"
	        + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'������������' from LCCont where InsuredNo in(select MainCustomerNo from LCInsuredRelated where CustomerNo = '" + tCustomerNo + "' and AppFlag='0' and ManageCom like '" + tManageCom + "%%') and AppFlag='0' "
	        + "union select GrpContNo,ContNo,PrtNo,InsuredName,AppntName,'������' from LCCont where InsuredNo in(select InsuredNo from LCBnf where CustomerNo = '" + tCustomerNo + "') and AppFlag='0' ";
	   */     		 			 
	//alert(strSQL);
<%
		}
	else if (tFlag.equals("Agent"))
		{
%>	

var sqlid9="ProposalQuerySql9";
var mySql9=new SqlClass();
mySql9.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
mySql9.setSqlId(sqlid9); //ָ��ʹ�õ�Sql��id

mySql9.addSubPara(tCustomerNo); //ָ������Ĳ���
mySql9.addSubPara(tManageCom); //ָ������Ĳ���

strSQL=mySql9.getString();

	//strSQL = "select GrpContNo,ContNo,PrtNo,InsuredName,AppntName from LCCont where AgentCode='" + tCustomerNo + "' and AppFlag='1' and ManageCom like '" + tManageCom + "%%' ";		
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


