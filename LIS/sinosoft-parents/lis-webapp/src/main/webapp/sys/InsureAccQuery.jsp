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
    String tIsCancelPolFlag = "";
	try
	{
		tPolNo = request.getParameter("PolNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
	}
	catch( Exception e )
	{
		tPolNo = "";
		tIsCancelPolFlag = "";
	}
%>
<head >
<script> 
	var tPolNo = "<%=tPolNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="InsureAccQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="InsureAccQueryInit.jsp"%>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<title>�ʻ���ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id="fm" >
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
	<Div  id= "divLCPol1" class="maxbox1" style= "display: ''" >
		<table  class= common align=center>
			<TR  class= common>
				<TD  class= title>
					�������ֺ���
				</TD>
				<TD  class= input>
					<Input class= "readonly wid" name=PolNo id="PolNo" >
				</TD>
				<TD  class= title>
				</TD>
				<TD  class= input>
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
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsureAcc1);">
    		</td>
    		<td class= titleImg>
    			 �ʻ���Ϣ
    		</td>
    	</tr>
    </table>
    
  
  	<Div  id= "divLCInsureAcc1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    
     <Div  id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="��ҳ" class=cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class=cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class=cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class=cssButton93 TYPE=button onClick="turnPage.lastPage();">
    </Div>				
  	</div>
    <!--INPUT VALUE="���˱�����ϸ" class = cssButton TYPE=button onclick="showPolDetail();"-->
    <INPUT class=cssButton VALUE=" ���� " TYPE=button onClick="top.close();">	 
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	// ��дSQL���
	var strSQL = "";
	if( tIsCancelPolFlag == "0"){
	  if(trim(tPolNo).substring(11,13)=='22')
	  {
//	  	strSQL = "select LCInsureAcc.PolNo,LCInsureAcc.RiskCode,lcpol.AppntName,LMRiskInsuAcc.InsuAccName,LCInsureAcc.ModifyDate,LCInsureAcc.SumPay,LCInsureAcc.SumPaym,(select sum(money) from lcinsureacctrace m where m.contno = LCInsureAcc.contno and insuaccno=LCInsureAcc.insuaccno),LCInsureAcc.InsuAccGetMoney,LCInsureAcc.FrozenMoney,LCInsureAcc.BalaDate from LCInsureAcc ,LMRiskInsuAcc, LCPol where LCInsureAcc.GrpPolNo='" + tPolNo + "' and LCInsureAcc.InsuAccNo = LMRiskInsuAcc.InsuAccNo and lcpol.POLNO = lcInsureAcc.POLNO and lcpol.grpcontno = lcInsureAcc.grpcontno";
	  	
	  	var sqlid1="InsureAccQuerySql1";
   	 	var mySql1=new SqlClass();
   	 	mySql1.setResourceName("sys.InsureAccQuerySql");
   	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
   	 	mySql1.addSubPara(tPolNo);//ָ���������
   	    strSQL = mySql1.getString();
	  	
	  }
	  else
	  {
//	  	strSQL = "select LCInsureAcc.PolNo,LCInsureAcc.RiskCode,lcpol.AppntName,LMRiskInsuAcc.InsuAccName,LCInsureAcc.ModifyDate,LCInsureAcc.SumPay,LCInsureAcc.SumPaym,(select sum(money) from lcinsureacctrace m where m.contno = LCInsureAcc.contno and insuaccno=LCInsureAcc.insuaccno),LCInsureAcc.InsuAccGetMoney,LCInsureAcc.FrozenMoney,LCInsureAcc.BalaDate,LCInsureAcc.Currency from LCInsureAcc ,LMRiskInsuAcc, LCPol where LCInsureAcc.PolNo='" + tPolNo + "' and LCInsureAcc.InsuAccNo = LMRiskInsuAcc.InsuAccNo and lcpol.POLNO = lcInsureAcc.POLNO and lcpol.grpcontno = lcInsureAcc.grpcontno";			 
	  
	  	var sqlid2="InsureAccQuerySql2";
   	 	var mySql2=new SqlClass();
   	 	mySql2.setResourceName("sys.InsureAccQuerySql");
   	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
   	 	mySql2.addSubPara(tPolNo);//ָ���������
   	    strSQL = mySql2.getString();
	  
	  }
	}
	else
	if(tIsCancelPolFlag=="1"){ //����������ѯ
//      strSQL = "select LCInsureAcc.PolNo,LCInsureAcc.RiskCode,lcpol.AppntName,LMRiskInsuAcc.InsuAccName,LCInsureAcc.ModifyDate,LCInsureAcc.SumPay,LCInsureAcc.SumPaym,(select sum(money) from lcinsureacctrace m where m.contno = LCInsureAcc.contno and insuaccno=LCInsureAcc.insuaccno),LCInsureAcc.InsuAccGetMoney,LCInsureAcc.FrozenMoney,LCInsureAcc.BalaDate from LCInsureAcc ,LMRiskInsuAcc, LCPol  where PolNo='" + tPolNo + "' and LCInsureAcc.InsuAccNo = LMRiskInsuAcc.InsuAccNo and lcpol.POLNO = lcInsureAcc.POLNO and lcpol.grpcontno = lcInsureAcc.grpcontno";			 
	
        var sqlid3="InsureAccQuerySql3";
 	 	var mySql3=new SqlClass();
 	 	mySql3.setResourceName("sys.InsureAccQuerySql");
 	 	mySql3.setSqlId(sqlid3); //ָ��ʹ��SQL��id
 	 	mySql3.addSubPara(tPolNo);//ָ���������
 	    strSQL = mySql3.getString();
	
	}
	else {
	  alert("�������ʹ������!");
	  return;
	  }
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	alert("���ݿ���û���������������ݣ�");
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


