<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
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
	String tPrtNo = "";
	try
	{
		tPrtNo = request.getParameter("PrtNo");
	}
	catch( Exception e )
	{
		tPrtNo = "";
	}
	String tMainPolNo = "";
	try
	{
		tMainPolNo = request.getParameter("MainPolNo");
	}
	catch( Exception e )
	{
		tMainPolNo = "";
	}
%>
<head >
<script> 
	var tPrtNo = "<%=tPrtNo%>";
	var tMainPolNo = "<%=tMainPolNo%>";
</script>

	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="ProposalQuery.js"></SCRIPT>
	<%@include file="MainOddRiskQueryInit.jsp"%>
	
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<title>�����ղ�ѯ </title>
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
    <Div  id= "divLCPol1" class="maxbox1" style= "display:  ;text-align:left">
	    <table  class= common>
	      	<TR  class= common>
	          <TD  class= title5>
	            ���ձ�������
	          </TD>
	          <TD class= input5>
	          	<Input class= "readonly wid" readonly name=MainPolNo id="MainPolNo" >
	          </TD>
	          <TD  class= title5>
	            ӡˢ����
	          </TD>
	          <TD  class= input5>
	          	<Input class= "readonly wid" readonly name=PrtNo id="PrtNo" >
	          </TD>
	        </TR>
	     </table>
	     <INPUT class=cssButton VALUE="Ͷ������ϸ" TYPE=button onclick="queryDetailClick();"> 	
	  </Div>
	  <table>
    	<tr>
        <td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divMainOddRisk1);">
    		</td>
    		<td class= titleImg>
    			 ��������Ϣ
    		</td>
    	</tr>
    </table>        
  	<Div  id= "divMainOddRisk1" style= "display:  ">
      	<table  class= common >
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>	
      <INPUT VALUE="��ҳ" TYPE=button class=cssButton90 onclick="turnPage.firstPage();"> 
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton91 onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="��һҳ" TYPE=button class=cssButton92 onclick="turnPage.nextPage();"> 
      <INPUT VALUE="βҳ" TYPE=button class=cssButton93 onclick="turnPage.lastPage();">				
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
	//var strSQL = "";
	
	//strSQL = "select PolNo,RiskCode,InsuredName,AppntName from LCPol where MainPolNo='" + tMainPolNo + "'";			 
	//alert(strSQL);
	var sqlid1="MainOddRiskQuerySql0";
	var mySql1=new SqlClass();	
	mySql1.setResourceName("app.MainOddRiskQuerySql"); //ָ��ʹ�õ�properties�ļ�   
	mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id	
	mySql1.addSubPara(tMainPolNo);//ָ������Ĳ���	
	var strSQL=mySql1.getString();
	
	
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


