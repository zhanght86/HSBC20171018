<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-06-19 11:10:36
//������  ��CrtHtml���򴴽�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tPayNo = "";
	try
	{
		tPayNo = request.getParameter("PayNo");
		loggerDebug("AllFeeQueryEDetail","����"+tPayNo);
	}
	catch( Exception e )
	{
		tPayNo = "";
	}
	String tSumActuPayMoney = "";
	try 
	{
		tSumActuPayMoney = request.getParameter("SumActuPayMoney");
		loggerDebug("AllFeeQueryEDetail","���"+tSumActuPayMoney);
	}
	catch( Exception e )
	{
		tSumActuPayMoney = "";
	}
%>
<head >
<script>
	var tPayNo = "<%=tPayNo%>";  
	var tSumActuPayMoney = "<%=tSumActuPayMoney%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<!--SCRIPT src="AllFeeQueryEDetail.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllFeeQueryEDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ϸ��ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm  id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    	</td>
			<td class= titleImg>
				����������Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLJAPay1" class=maxbox style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            �����վݺ���
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=PayNo id=PayNo >
          </TD>
          <TD  class= title5>
            ��ʵ�����
          </TD>
          <TD  class= input5>
            <Input class="wid common" name=SumActuPayMoney id=SumActuPayMoney >
          </TD>
        </TR>
  </table>
  </Div>
  
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPPerson1);">
    		</td>
    		<td class= titleImg>
    			 ������ϸ��Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPPerson1" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT class=cssButton90 VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT class=cssButton93 VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">				
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

	var sqlid34="ProposalQuerySql34";
	var mySql34=new SqlClass();
	mySql34.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql34.setSqlId(sqlid34); //ָ��ʹ�õ�Sql��id
	mySql34.addSubPara(tPayNo); //ָ������Ĳ���
	strSQL=mySql34.getString();
	
	//strSQL = "select ActuGetNo,EndorsementNo,FeeOperationType,FeeFinaType,ContNo,GrpPolNo,PolNo,GetDate,GetMoney,RiskCode,GrpName,PolType,GetFlag from LJAGetEndorse where ActuGetNo='" + tPayNo + "'";			 
	//strSQL = "select PolNo,GrpPolNo,ContNo,PayNo,PayAimClass,SumActuPayMoney,PayIntv,PayDate,PayType,CurPayToDate from LJAPayPerson";			 
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


