<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-12-20 
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tActuGetNo = "";
	try
	{
		tActuGetNo = request.getParameter("ActuGetNo");
	}
	catch( Exception e )
	{
		tActuGetNo = "";
	}
	String tSumGetMoney = "";
	try 
	{
		tSumGetMoney = request.getParameter("SumGetMoney");
		loggerDebug("AllGetQueryTempDetail",tSumGetMoney);
	}
	catch( Exception e )
	{
		tSumGetMoney = "";
	}
%>
<head >
<script> 
	var tActuGetNo = "<%=tActuGetNo%>";  
	var tSumGetMoney = "<%=tSumGetMoney%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<!--SCRIPT src="AllGetQueryTempDetail.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllGetQueryTempDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ϸ��ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGet1);">
    	</td>
			<td class= titleImg>
				����������Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLJAGet1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            ʵ������
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=ActuGetNo id=ActuGetNo >
          </TD>
          <TD  class= title5>
            �ܸ������
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=SumGetMoney id=SumGetMoney >
          </TD>
        </TR>
  </table>
  </Div>
  
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAGetTempFee1);">
    		</td>
    		<td class= titleImg>
    			 ������ϸ��Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAGetTempFee1" style= "display: ''"  align=center>
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
var mySql = new SqlClass();
function easyQueryClick()
{
	
	//alert("here");
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";
	
	//strSQL = "select ActuGetNo,TempFeeNo,(select codename from ldcode where codetype='tempfeetype' and code=TempFeeType),(select codename from ldcode where codetype='paymode' and code=PayMode),GetMoney,GetDate,GetReasonCode,'�����˷�','�����˷�',RiskCode from LJAGetTempFee where ActuGetNo='" + tActuGetNo + "'";			 	 
	mySql = new SqlClass();
	mySql.setResourceName("sys.AllGetQueryTempDetailSql");
	mySql.setSqlId("AllGetQueryTempDetailSql1");
	mySql.addSubPara(tActuGetNo); 
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("��ʵ���Ŷ�Ӧ�ĸ�����ϸ�����ڣ����ʵ��");
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


