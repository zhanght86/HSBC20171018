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
		loggerDebug("AllGetQueryBonusDetail",tSumGetMoney);
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
	<!--SCRIPT src="AllGetQueryBonusDetail.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllGetQueryBonusDetailInit.jsp"%>
	
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
            <Input class= "common wid" name=SumGetMoney  id=SumGetMoney>
          </TD>
        </TR>
  </table>
  </Div>
  
  
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJABonusGet1);">
    		</td>
    		<td class= titleImg>
    			 ������ϸ��Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJABonusGet1" style= "display: ''"  align=center>
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

	var sqlid41="ProposalQuerySql41";
	var mySql41=new SqlClass();
	mySql41.setResourceName("sys.ProposalQuerySql"); //ָ��ʹ�õ�properties�ļ���
	mySql41.setSqlId(sqlid41); //ָ��ʹ�õ�Sql��id
	mySql41.addSubPara(tActuGetNo); //ָ������Ĳ���
	strSQL=mySql41.getString();
	
	//strSQL = "select ActuGetNo,contno,OtherNoType,BonusYear,(select codename from ldcode where codetype='paymode' and code=PayMode),GetMoney,GetDate,decode(FeeOperationType,'XJTF','�����ֽ����','LJTF','�����ۼ���Ϣ����','DJTF','�����ֽ����Ѹ���','HLTF','������������','��������'),(select codename from ldcode where codetype='feefinatype' and code=FeeFinaType and rownum=1),State from LJABonusGet where ActuGetNo='" + tActuGetNo + "'";			 	 
	
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
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


