<html>
<%
//�������� :CostDataBaseDefInput.jsp
//������ :ƾ֤��������Դ����
//������ :���
//�������� :2008-08-18
//
%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--�û�У����-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//����ҳ��ؼ��ĳ�ʼ���� 
 	%>
<script>

  var comcode = "<%=tGI1.ComCode%>";
  var VersionNo = <%=request.getParameter("VersionNo")%>;
  var AcquisitionID = <%=request.getParameter("AcquisitionID")%>;
  var VersionState = <%=request.getParameter("VersionState")%>;
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src = "CostDataBaseDefInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="CostDataBaseDefInputInit.jsp"%>
</head>
<body  onload="initForm();queryCostDataBaseDefInputGrid();initElementtype();">
  <form action="./CostDataBaseDefSave.jsp" method=post name=fm id=fm target="fraSubmit">
  <Div id= "divCostDataBaseDef" style= "display: ''">
  	<table class= common border=0 width=100%>
  		<table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divCostDataBaseDefInputGrid);">
    		</td>
    		<td class= titleImg>
    			 �嵥�б�
    		</td>
    	</tr>
    </table>
		<Div  id= "divCostDataBaseDefInputGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanCostDataBaseDefInputGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
      
   </Div>
  <table class= common>
	<tr class= common>
				 <TD class= title5>
					  ����Դ���
				 </TD>
				 <TD  class= input5 > <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=DataBaseID id=DataBaseID readonly=true ondblclick="return showCodeList('DataBaseID',[this,DataBaseName],[0,1]);" onMouseDown="return showCodeList('DataBaseID',[this,DataBaseName],[0,1]);" onkeyup="return showCodeList('DataBaseID',[this,DataBaseName],[0,1]);" ><input class=codename name=DataBaseName id=DataBaseName readonly=true elementtype = nacessary >
				</TD>
	</tr>
	<tr class= common>
				 <TD class= title5>
					  ��ȡ���
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=DataBaseOrder id=DataBaseOrder readonly=true >
				</TD>
				<TD class= title5>
					  ����
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=common id=common name=Remark >
				</TD>
	</tr>
	</table>
	<INPUT VALUE="��  ��" TYPE=button  class= cssButton name=addbutton onclick="submitForm()">   
  <INPUT VALUE="ɾ  ��" TYPE=button class= cssButton name=deletebutton onclick="deleteClick()">
  <INPUT VALUE="��  ��" TYPE=button class= cssButton name= resetbutton onclick="resetAgain()">
  <INPUT TYPE=hidden NAME=VersionNo VALUE=''>
  <INPUT TYPE=hidden NAME=AcquisitionID VALUE=''>
  <INPUT TYPE=hidden NAME=VersionState VALUE=''>
  <INPUT TYPE=hidden NAME=DataBaseID1 VALUE=''>
  <input type=hidden NAME="OperateType" VALUE=''>
</table>
</Div>
</form>
<Div  id= "divPage" align=center style= "display: none ">
      <INPUT VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();" class="cssButton90">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();" class="cssButton91">
      <INPUT VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();" class="cssButton92">
      <INPUT VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();" class="cssButton93">
      </Div>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>