<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2002-1-21
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tEdorAcceptNo = "";
	String tPolNo = "";
	try
	{
		tEdorAcceptNo = request.getParameter("EdorAcceptNo");
		tPolNo = request.getParameter("PolNo");
		loggerDebug("AllPBqItemQuery","����"+tEdorAcceptNo);
	}
	catch( Exception e )
	{
		tEdorAcceptNo = "";
	}
	String tSumGetMoney = "";
	try 
	{
		tSumGetMoney = request.getParameter("SumGetMoney");
		loggerDebug("AllPBqItemQuery","���"+tSumGetMoney);
	}
	catch( Exception e )
	{
		tSumGetMoney = "";
	}
%>
<head >
<script>
	var tEdorAcceptNo = "<%=tEdorAcceptNo%>"; 
	var tPolNo = "<%=tPolNo%>";   
	var tSumGetMoney = "<%=tSumGetMoney%>"; 
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<!--SCRIPT src="AllPBqItemQuery.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="AllPBqItemQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>���˱�ȫ��ϸ��ѯ </title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm id=fm>
  <table >
    	<tr>
    	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain1);">
    	</td>
			<td class= titleImg>
				��Ŀ��ϸ��Ϣ
			</td>
		</tr>
	</table>
	<Div  id= "divLPEdorMain1" style= "display: ''" class=maxbox1>
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
            ��ȫ�������
          </TD>
          <TD  class= input5>
            <Input class= "common wid" name=EdorAcceptNo id=EdorAcceptNo >
          </TD>
          <TD  class= title5>
            �ܲ��˷ѽ��
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
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain2);">
    		</td>
    		<td class= titleImg>
    			 ��Ŀ��ϸ��Ϣ
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLPEdorMain2" style= "display: '';text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE="��ҳ"  class = cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class = cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class = cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class = cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
  </body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	// ��ʼ�����
	initPolGrid();

	// ��дSQL���
	var strSQL = "";
	
	//strSQL = "select EdorNo,EdorType,GetMoney,EdorValiDate,EdorAppDate,decode(EdorState,'0','��ȫȷ��','1','��������','2','����ȷ��'),Operator from LPEdorMain where EdorAcceptNo='" + tEdorAcceptNo + "' order by makedate,maketime ";			 
	var sqlresourcename = "sys.AllPBqItemQuerySql";
	var sqlid1="AllPBqItemQuerySql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(sqlresourcename);
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tEdorAcceptNo);
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(mySql1.getString(), 1, 0, 1);  
  
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


