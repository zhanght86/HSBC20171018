<html> 
<%
//�������ڣ�2008-08-11
//������  ��ZhongYan
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
  <%@page import="java.util.*"%> 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
	String mStartDate = "";
	String mSubDate = "";
	String mEndDate = "";
        GlobalInput tGI = new GlobalInput();
        //PubFun PubFun=new PubFun();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("CostTypeDefInput","1"+tGI.Operator);
	loggerDebug("CostTypeDefInput","2"+tGI.ManageCom);
%>
<script>
	var VersionNo = <%=request.getParameter("VersionNo")%>;
	var VersionState = <%=request.getParameter("VersionState")%>;
	var CertificateID = <%=request.getParameter("CertificateID")%>;
</script>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>  
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>  
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="CostTypeDefInput.js"></SCRIPT> 
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>

  <%@include file="CostTypeDefInputInit.jsp"%>
</head>

<body  onload="initForm();initElementtype();" >

<form name=fm id=fm   target=fraSubmit method=post>
	 <table>
    	<tr>  
        	 <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCostTypeDefQuery);">
                </td>  		 
    		 <td class= titleImg>
        		 ƾ֤�������Ͷ���
       	 </td>   		 
    	</tr>
    </table>

  <Div  id= "divCostTypeDefQuery" style= "display: ''">
      <table  class= common>
       	<tr  class= common>
      	  <td text-align: left colSpan=1>
  					<span id="spanCostTypeDefGrid" >
  					</span>
  			  </td>
  			</tr>
    	</table>
    <Div  id= "divPage" align=center style= "display: none ">      
      <INPUT CLASS=cssButton90 VALUE="��ҳ" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS=cssButton93 VALUE="βҳ" TYPE=button onclick="turnPage.lastPage();">
    </Div>
  </Div>
  <br>
  
   <!--<hr class="line"></hr>-->
    <div class="maxbox">  
<table class= common border=0 width=100%>
  <table class= common>			
  	
			<tr class= common>
				<TD class= title5>
					 �������ͱ���
				</TD>
				<TD class=input5>
					<Input class="wid" class=common name=CostID id=CostID elementtype=nacessary verify="�������ͱ���|len<=20" >
				</TD>
				<TD class= title5>
		   	   ������������
		    </TD>
		    <TD class=input5>
					<Input class="wid" class=common name=CostName id=CostName	elementtype=nacessary verify="������������|len<=100" >
				</TD>
			</tr>
			
		  <tr class= common>
				<TD class= title5>
					 �����־
				</TD>
				<TD class=input5>
					<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FinItemType id=FinItemType readonly=true verify="�����־|NOTNULL" CodeData="0|^D|�跽^C|����" ondblClick="showCodeListEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('FinItemType',[this,FinItemTypeName],[0,1],null,null,null,[1]);"><input class=codename name=FinItemTypeName id=FinItemTypeName readonly=true elementtype=nacessary>				 	 
				</TD>
				<TD class= title5>
		   	   ��Ŀ���
		    </TD>
		    <TD class= input5>
		  		<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=FinItemID id=FinItemID ondblclick="return showCodeList('FinItemID', [this,FinItemName],[0,1],null,fm.VersionNo.value,'VersionNo');" onMouseDown="return showCodeList('FinItemID', [this,FinItemName],[0,1],null,fm.VersionNo.value,'VersionNo');" onkeyup="return showCodeListKey('FinItemID', [this,FinItemName],[0,1],null,fm.VersionNo.value,'VersionNo');" verify="��Ŀ���|NOTNULL"><input class=codename name=FinItemName id=FinItemName readonly=true elementtype=nacessary>					
		    </TD>
			</tr>					 
		  
		  <tr class= common>
				<TD class= title5>
					 ������������
				</TD>
				<TD class=input5>
					<Input class="wid" class=common name=Remark id=Remark verify="������������|len<=4000" >
				</TD>
				<!--TD class= title>
					 ״̬
				</TD>
				<TD class=input>
					<Input class=codeno name=State verify="State|NOTNULL" CodeData="0|^01|�������^02|������ȡ��ʵ��" ondblClick="showCodeListEx('State',[this,StateName],[0,1]);" onkeyup="showCodeListKeyEx('State',[this,StateName],[0,1]);"><input class=codename name=StateName readonly=true elementtype=nacessary>				 	 
				</TD-->
			</tr>					  
		  
    </table>         
    	</div>				 
  	<p>       
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="addbutton" onclick="return addClick();">   
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name="updatebutton" onclick="return updateClick();">
    <INPUT VALUE="ɾ  ��" TYPE=button class= cssButton name="deletebutton" onclick="return deleteClick();">   
    <INPUT VALUE="��  ��" TYPE=button class= cssButton name= resetbutton onclick="return resetAgain()">
    
    <INPUT type=hidden name=hideOperate value=''>
    <INPUT type=hidden name=VersionNo value=''> 
    <INPUT type=hidden name=CertificateID value=''>    
    <INPUT type=hidden name=VersionState value=''>        
    <INPUT type=hidden name=State value=''> <!--״̬����ֶ���FICostTypeDef��ƾ֤�������Ͷ��壩�������֮����Ϊ02����FICostDataAcquisitionDef��ƾ֤�������ݲɼ����壩���֮����Ϊ01-�������-->          
   
   <br> 
   <br>    
    
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</form>

</body>
</html>
