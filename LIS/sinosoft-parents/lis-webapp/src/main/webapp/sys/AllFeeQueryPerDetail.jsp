<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2009-04-21 11:13
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tPayNo = "";
	
	try
	{
		tPayNo = request.getParameter("PayNo");
	}
	catch( Exception e )
	{
		tPayNo = "";
	}
	String tSumActuPayMoney = "";
	try 
	{
		tSumActuPayMoney = request.getParameter("SumActuPayMoney");
		loggerDebug("AllFeeQueryPerDetail",tSumActuPayMoney);
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
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllFeeQueryPerDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ϸ��ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm >
  
  <Div  id= "divLJAPPersonHidden" style= "display: ''">
  
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
	<Div  id= "divLJAPay1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            ��ͬ����
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=PolNo id=PolNo >
          </TD>
          <TD  class= title5>
            �����ͬ����
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=GrpPolNo id=GrpPolNo >
          </TD>
        </TR>
    <TR  class= common>
          <TD  class= title5>
            �����վݺ�
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=PayNo id=PayNo >
          </TD>
          <TD  class= title5>
            ��ʵ�����
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=SumActuPayMoney id=SumActuPayMoney >
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
  	<Div  id= "divLJAPPerson1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<br>
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
	/*strSQL = "select PayNo,PayAimClass,SumActuPayMoney,PayIntv,MakeDate,PayType,CurPayToDate,paycount,contno,grpcontno from LJAPayPerson where payno='" + tPayNo + "' order by paycount";		
	*/mySql = new SqlClass();
	mySql.setResourceName("sys.AllFeeQueryPerDetailSql");
	mySql.setSqlId("AllFeeQueryPerDetailSql1");
	mySql.addSubPara(tPayNo); 
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql.getString(), 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("���վݶ�Ӧ�ı���������ϸ�����ڣ����ʵ��");
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
  var tGrpPolNo=PolGrid. getRowColData(0,10);
	var tContNo=PolGrid. getRowColData(0,9);
  fm.GrpPolNo.value = tGrpPolNo;
	fm.PolNo.value = tContNo;
}


</script>
</html>


