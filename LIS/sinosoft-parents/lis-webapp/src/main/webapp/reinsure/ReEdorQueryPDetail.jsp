<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
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
	String tPolNo = "";
	String tReinsurItem="";
	String tInsuredYear="";
	tPolNo = request.getParameter("PolNo");
	tReinsurItem = request.getParameter("ReinsurItem");
	tInsuredYear = request.getParameter("InsuredYear");
	System.out.println("tPolNo:" + request.getParameter("PolNo"));
  
%>
<head >

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<!--SCRIPT src="AllFeeQueryPDetail.js"></SCRIPT-->
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="ReEdorQueryPDetailInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ϸ��ѯ</title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm >
  
  <Div  id= "divLJAPPersonHidden" style= "display:none;">
  
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
	<Div  id= "divLJAPay1" style= "display: ''">
    <table  class= common align=center>
      	<TR  class= common>
          <TD  class= title5>
��������
          </TD>
          <TD  class= input5>
            <Input class= common name=PolNo >
          </TD>
        </TR>
      	<TR  class= common>
          <TD  class= title5>
���嵥����
          </TD>
          <TD  class= input5>
            <Input class= common name=GrpPolNo >
          </TD>
          <TD  class= title5>
�ܵ�/��ͬ������
          </TD>
          <TD  class= input5>
            <Input class= common name=ContNo >
          </TD>
        </TR>
  </table>
  </Div>
  
  
    <TR  class= common>
          <TD  class= title5>
�����վݺ�
          </TD>
          <TD  class= input5>
            <Input class= common name=PayNo >
          </TD>
          <TD  class= title5>
��ʵ�����
          </TD>
          <TD  class= input5>
            <Input class= common name=SumActuPayMoney >
          </TD>
        </TR>
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
  	<Div  id= "divLJAPPerson1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<br>
      <INPUT VALUE="��ҳ" class= common TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" class= common TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" class= common TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" class= common TYPE=button onclick="turnPage.lastPage();">				
  	</div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script>
var turnPage = new turnPageClass(); 
function easyQueryClick()
{
	var tPolNo = "<%=tPolNo%>";
	var tReinsurItem = "<%=tReinsurItem%>";
	var tInsuredYear = "<%=tInsuredYear%>";
	//alert("here");
	// ��ʼ�����
	initPolGrid();
	
	// ��дSQL���
	var strSQL = "";

	strSQL = "select polno,edortype,edorno,ChgCessAmt,ShRePrem,ShReComm from LREdorMain where PolNo='" + tPolNo + "' and ReinsurItem='"+tReinsurItem+"' and InsuredYear='"+tInsuredYear+"' ";		
	
	//��ѯSQL�����ؽ���ַ���
  turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);  
  
  //�ж��Ƿ��ѯ�ɹ�
  if (!turnPage.strQueryResult) {
  	PolGrid.clearData();
  	myAlert("���ݿ���û���������������ݣ�");
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
//  var tGrpPolNo=PolGrid. getRowColData(0,8);
//	var tContNo=PolGrid. getRowColData(0,9);
//	var tPolNo = PolGrid. getRowColData(0,7);
//  fm.GrpPolNo.value = tGrpPolNo;
//	fm.ContNo.value = tContNo;
//	fm.PolNo.value = tPolNo;
}
</script>
</html>


