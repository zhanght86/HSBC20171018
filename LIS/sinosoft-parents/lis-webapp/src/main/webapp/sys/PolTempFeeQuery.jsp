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
	String tContNo = "";
	String tIsCancelPolFlag = "";
	try
	{
		tContNo = request.getParameter("ContNo");
		tIsCancelPolFlag = request.getParameter("IsCancelPolFlag");
	}
	catch( Exception e )
	{
		tContNo = "";
		tIsCancelPolFlag = "";
	}
%>
<head >
<script> 
	var tContNo = "<%=tContNo%>";
	var tIsCancelPolFlag = "<%=tIsCancelPolFlag%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<SCRIPT src="TempFeeQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="PolTempFeeQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>�ݽ��Ѳ�ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm >
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol1);">
    	</td>
			<td class= titleImg>
				��ͬ��Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLCPol1" style= "display: ''">
    <div class="maxbox1" >
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            ��ͬ����
          </TD>
          <TD  class= input5>
            <Input class="common wid" name=ContNo id=ContNo>
          </TD>
          <TD  class= title5> </TD>
          <TD  class= input5> </TD>
	  </TR>
     </table>
  </Div>
    </Div>
  
  
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJTempFee1);">
    		</td>
    		<td class= titleImg>
    			 �ݽ�����Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJTempFee1" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td style=" text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��  ҳ" class = cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="β  ҳ" class = cssButton93 TYPE=button onClick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<SCRIPT>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";

	if( tIsCancelPolFlag == "0"){
// 		strSQL = "select a.TempFeeNo,a.TempFeeType,a.RiskCode,a.PayMoney, c.CodeName, a.PayDate,a.AgentCode,a.ConfDate,a.AppntName,a.Operator,a.Currency "+
// 		         "  from LJTempFee a,LJTempFeeClass b,LDCode c"+
// 		         " where a.TempFeeNo = b.TempFeeNo "+
// 		         "   and b.PayMode = c.code "+
// 		         //"   and a.OtherNo='" + tContNo + "'"+
// 		         //"   and a.OtherNoType in ('2','3')"+
// 		         "   and a.OtherNo in (select contno from lccont where contno='" + tContNo + "'   union  select familycontno from lccont where contno='" + tContNo + "' )"+
// 		         "   and a.OtherNoType in ('0','3')"+ //0-���˵�     3-��ͥ�����ͬ��
// 		         "   and c.CodeType = 'paymode'";
		
		var sqlid1="PolTempFeeQuerySql1";
   	 	var mySql1=new SqlClass();
   	 	mySql1.setResourceName("sys.PolTempFeeQuerySql");
   	 	mySql1.setSqlId(sqlid1); //ָ��ʹ��SQL��id
   	 	mySql1.addSubPara(tContNo);//ָ���������
   	    mySql1.addSubPara(tContNo);
   	 	strSQL = mySql1.getString();
		
	}
	else
	if(tIsCancelPolFlag=="1"){ //����������ѯ
// 		strSQL = "select a.TempFeeNo,a.TempFeeType,a.RiskCode,a.PayMoney, c.CodeName, a.PayDate,a.AgentCode,a.ConfDate,a.AppntName,a.Operator  "+
// 		         "  from LJTempFee a,LJTempFeeClass b,LDCode c"+
// 		         " where a.TempFeeNo = b.TempFeeNo "+
// 		         "   and b.PayMode = c.code "+
// 		         "   and a.OtherNo='" + tContNo + "' "+
// 		         "   and a.OtherNoType in ('0','1')"+
// 		         "   and c.CodeType = 'paymode'";
	
		var sqlid2="PolTempFeeQuerySql2";
   	 	var mySql2=new SqlClass();
   	 	mySql2.setResourceName("sys.PolTempFeeQuerySql");
   	 	mySql2.setSqlId(sqlid2); //ָ��ʹ��SQL��id
   	 	mySql2.addSubPara(tContNo);//ָ���������
   	    mySql2.addSubPara(tContNo);
   	 	strSQL = mySql2.getString();
	
	}
	else {
	  alert("�������ʹ������!");
	  return;
	  }
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


</SCRIPT>
</html>
