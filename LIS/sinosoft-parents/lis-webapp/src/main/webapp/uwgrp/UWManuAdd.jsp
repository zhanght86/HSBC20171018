<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWManuSpec.jsp
//�����ܣ���ȫ�˹��˱������б�
//�������ڣ�2002-06-19 11:10:36
//������  ��WHN
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<%
 
   GlobalInput tGlobalInput = new GlobalInput();
   tGlobalInput=(GlobalInput)session.getValue("GI");	
   String tOperator = tGlobalInput.Operator;
   loggerDebug("UWManuAdd"," tOperator:"+ tOperator);
 %>  
 <script language="JavaScript">
	var tOperator = "<%=tOperator%>";
 </script>                          
<html> 
<head >
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="UWManuAdd.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title> �ӷѳб� </title>
  <%@include file="UWManuAddInit.jsp"%>
 
</head>
<body  onload="initForm('', '<%=tContNo%>', '<%=tMissionID%>', '<%=tSubMissionID%>','<%=tInsuredNo%>');" >
	
	
  <form method=post name=fm target="fraSubmit" >
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol2);">
    		</td>
    		<td class= titleImg>
    			 Ͷ������Ϣ
    		</td>
    	    </tr>
        </table>	
        <Div  id= "divLCPol2" style= "display: ''" >
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanPolAddGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	<tr class=common align=center>
        	<INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="getFirstPage();"> 
        	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getPreviousPage();"> 					
        	<INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="getNextPage();"> 
        	<INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="getLastPage();"> 
    	</tr>				
      </Div>
   
        <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divUWSpec1);">
    		</td>
    		<td class= titleImg>
    			 �ӷ���Ϣ
    		</td>
    	</tr>
       </table>
      	<Div  id= "divUWSpec1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1 >
  					<span id="spanSpecGrid">
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	</div>
    
    	
     <table class="common">
    	<TR  class= common>
          <TD  class= title>
            �ӷ�ԭ��
          </TD>
          <tr></tr>
          
      <TD  class= input> <textarea name="AddReason" cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </TR>
     </table>
      <INPUT type="hidden" name="PolNo2" value= ""> <!--��ȫ�ӷ�����Եı���-->
      <INPUT type= "hidden" name="PolNo" value= ""> <!--��ȫ��Ŀ����Եı���,��ǰһҳ�洫��-->
      <INPUT type= "hidden" name="ContNo" value= "">
      <INPUT type= "hidden" name="MissionID" value= "">
      <INPUT type= "hidden" name="SubMissionID" value= "">
      <INPUT type= "hidden" name="InsuredNo" value="">
      
      <INPUT type= "hidden" name="RiskCode" value= "">
      <INPUT type = "hidden" name = "DutyCode" value = "">
      <INPUT type= "hidden" name="AddFeeObject" value= "">
      <INPUT type= "hidden" name="AddFeeType" value="">
      <INPUT type= "hidden" name="SuppRiskScore" value= "">
      <INPUT type= "hidden" name="SecondScore" value= "">
   
     
      
      <INPUT type= "button"  name="sure" value="ȷ  ��" class= cssButton onclick="submitForm()">			
	  
 	
    <!--��ȡ��Ϣ-->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
