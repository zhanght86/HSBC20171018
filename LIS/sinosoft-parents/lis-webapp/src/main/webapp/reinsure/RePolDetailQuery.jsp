<%@include file="/i18n/language.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html> 
<%
//�������ƣ�
//�����ܣ�
//�������ڣ�2003-1-22 
//������  ��lh
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
	String tPolNo = "";
	try
	{
		tPolNo = request.getParameter("PolNo");
	}
	catch( Exception e )
	{
		tPolNo = "";
	}
%>
<head >
<script> 
	var tPolNo = "<%=tPolNo%>";  
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT><SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT><SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="RePolQuery.js"></SCRIPT>
	<SCRIPT src="AllProposalQuery.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css><LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<%@include file="RePolDetailQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>������ѯ</title>
</head>

<body  onload="initForm();easyQueryClick();" >
  <form  name=fm >  
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
      	  		<td style="text-align:left;" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<table>
              <tr>		
			            <td><INPUT class=common VALUE="��ȫ�����ѯ" TYPE=button onclick="FeeQueryClick();"> </td>
			            <td><INPUT class=common VALUE="����̯�ز�ѯ" TYPE=button onclick="GetQueryClick();"> </td>
						
				        <td><INPUT class=common VALUE="����" TYPE=button onclick="GoBack();"> </td>
				        </tr>
  		</table>		
  	</Div>
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
	 strSQL = "select LRPol.ReinsurItem,LRPol.PolNo,LRPol.InsuredName,LRPol.RiskCode,LRPol.Prem ,LRPol.RiskAmnt ,LRPol.CValiDate,LRPol.InsuredYear from LRPol where PolNo='" + tPolNo + "' order by LRPol.CessStart";			 
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
  
}
</script>
</html>


