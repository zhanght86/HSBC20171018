<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-12-19 11:10:36
//������  ��lh
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
		loggerDebug("AllFeeQueryGDetail",tSumActuPayMoney);
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
		<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="AllFeeQueryGDetail.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllFeeQueryGDetailInit.jsp"%>
	<title>������ϸ��ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id="fm">
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
  <div class="maxbox1">
	<Div  id= "divLJAPay1" style= "display: ''">
    <table  class= common align=center>
      	<TR>
        <TD  class= title5>
            ���屣������
          </TD>
          <TD  class= input5>
            <Input class="common wid"  name=GrpPolNo >
          </TD>
          <TD  class= title5>
            �����վݺ���
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=PayNo >
          </TD>
          </TR>
      	<TR  class= common>
          
          <TD  class= title5>
            ��ʵ�����
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=SumActuPayMoney >
          </TD>
        </TR>
     </table>
  </Div>
  </div>
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
      <INPUT VALUE="��  ҳ" TYPE=button class="cssButton90"  onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" TYPE=button class="cssButton91"  onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE=button class="cssButton92"  onclick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" TYPE=button class="cssButton93"  onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script>
<!--
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";

	var sqlid26="ProposalQuerySql26";
	var mySql26=new SqlClass();
	mySql26.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql26.setSqlId(sqlid26); //ָ��ʹ�õ�Sql��id
	mySql26.addSubPara(tPayNo ); //ָ������Ĳ���
	strSQL=mySql26.getString();
	
	//strSQL = "select contno,PayAimClass,SumActuPayMoney,PayIntv,PayDate,CurPayToDate,PayType,ContNo,GrpContNo from LJAPayPerson where PayNo='" + tPayNo + "'";			 
	//alert(strSQL);
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("���վݶ�Ӧ�ı���������ϸ�����ڣ����ʵ��");
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
  
	//�������ؼ����ƽ��и�ֵ
	var tNo =PolGrid.mulLineCount;
	if(tNo>=1)
	{
		var tGrpPolNo=PolGrid. getRowColData(0,9);
		var tContNo=PolGrid. getRowColData(0,8);
		
		fm.GrpPolNo.value = tGrpPolNo;
	}

	
	}

</script>
</html>


