<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%
//�������ƣ�ScanContInput.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�2004-12-22 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<html>
<%
  //�����¸���	
	String tContNo = "";
	String tFlag = request.getParameter("type");
  	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>	
	var contNo = "<%=tContNo%>";          //���˵��Ĳ�ѯ����.
	var operator = "<%=tGI.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGI.ManageCom%>"; //��¼��½����
	var type = "<%=tFlag%>";
	var comcode = "<%=tGI.ComCode%>"; //��¼��½����
	var RiskSql = "1 and subriskFlag =#M# ";
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="CustomerAssociate.js"></SCRIPT>
  <script src="../common/javascript/jquery.workpool.js"></script>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CustomerAssociateInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
  <title>ɨ��¼��</title>
</head>
<body  onload="initForm();initElementtype();" >
  <form action="./ScanContInputSave.jsp" method=post name=fm id="fm" target="fraSubmit">
    <!-- ������Ϣ���� -->
    <div id ="CustomerPool"></div>
     <table>   	
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID  value= "">
          <input  type= "hidden" class= Common name="WorkFlowFlag" value="">        
          <input  type= "hidden" class= Common name= AppntName value="">        
          <input  type= "hidden" class= Common name= InsuredName value="">        
          <input  type= "hidden" class= Common name= AppntNo value="">        
          <input  type= "hidden" class= Common name= InsuredNo value="">        
          <input  type= "hidden" class= Common name= AgentCode value="">        
          <input  type= "hidden" class= Common name= ContNo value="">        
          <input  type= "hidden" class= Common name= PrtNo value="">        
          <input  type= "hidden" class= Common name= managecom value="">        
    	</tr>
    </table>
 <!--     <table class= common border=0 width=100%>
    	<tr>
			<td class= titleImg align= center>�������ѯ������</td>
		</tr>
    </table>
    <Div  id= "divSearch" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
	          <TD  class= title>�������</TD>
	          <TD  class= input>
	            <Input class="codeno" name=ManageCom verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName],[0,1]);"><input class=codename name=ManageComName readonly=true>
	          </TD>      	
	          <TD  class= title>ӡˢ��</TD>
	          <TD  class= input><Input class= common name=PrtNo ></TD>
			  <TD class="title" >��������</td>
			  <TD class="input" ><input class="multiDatePicker" name="FirstTrialDate"></TD>  
			</TD>                 
	        </TR>
	        <TR  class= common>
	          <TD  class= title>ɨ������</TD>
	          <TD  class= input><Input class="multiDatePicker" name=ScanMakeDate > </TD> 	
	          <TD  class= title>���ֱ���</TD>
	          <TD  class= input>
            	<Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onkeyup="return showCodeListKey('RiskCode',[this],null,null,RiskSql,'1',1);">
       		  </TD>
              <TD  class= title>ҵ��Ա����</TD>
              <td class=input>
              	<input class=common name=AgentCode>
              </td>           
	        </TR>      
	    </table>

    </div> 
    <INPUT VALUE="��   ѯ" class = cssButton TYPE=button onclick="easyQueryClick();"> 
    <INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="ApplyUW();">

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;"   OnClick= "showPage(this,divClientListGrid);"  > 
    		</td>
    		<td class= titleImg>��������</td>
    	</tr>
    	<tr>
          <INPUT  type= "hidden" class= Common name= MissionID  value= "">
          <INPUT  type= "hidden" class= Common name= SubMissionID  value= "">
          <INPUT  type= "hidden" class= Common name= ActivityID  value= "">
          <input  type= "hidden" class= Common name="WorkFlowFlag" value="">        
          <input  type= "hidden" class= Common name= AppntName value="">        
          <input  type= "hidden" class= Common name= InsuredName value="">        
          <input  type= "hidden" class= Common name= AppntNo value="">        
          <input  type= "hidden" class= Common name= InsuredNo value="">        
          <input  type= "hidden" class= Common name= AgentCode value="">        
          <input  type= "hidden" class= Common name= ContNo value="">        
          <input  type= "hidden" class= Common name= PrtNo value="">        
          <input  type= "hidden" class= Common name= managecom value="">        
    	</tr>
    </table>
  	<Div  id= "divClientListGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanClientListGrid" ></span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT CLASS=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT CLASS=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT CLASS=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();">						
  	</div>
  	
  <br>
  
   <DIV id=DivLCContInfo STYLE="display:''"> 
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divOPolGrid);">
    		</td>
    		<td class= titleImg>���˹�����</td>
    	</tr>  	
    </table>
        <Div  id= "divSearch1" style= "display: ''">
	    <table  class= common >   
	      	<TR  class= common>
	          <TD  class= title>�������</TD>
	          <TD  class= input>
	            <Input class="codeno" name=ManageCom1 verify="�������|code:station" ondblclick="return showCodeList('station',[this,ManageComName1],[0,1]);" onkeyup="return showCodeListKey('station',[this,ManageComName1],[0,1]);"><input class=codename name=ManageComName1 readonly=true>
	          </TD>      	
	          <TD  class= title>ӡˢ��</TD>
	          <TD  class= input><Input class= common name=PrtNo1 ></TD>
			  <TD class="title" >��������</td>
			  <TD class="input" ><input class="multiDatePicker" name="FirstTrialDate1"></TD>  
			  
	        </TR>
	        <TR  class= common>
	          <TD  class= title>ɨ������</TD>
	          <TD  class= input><Input class="multiDatePicker" name=ScanMakeDate1 > </TD> 	
	          <TD  class= title>���ֱ���</TD>
	          <TD  class= input>
            	<Input class="code" name=RiskCode1 ondblclick="return showCodeList('RiskCode',[this],null,null,RiskSql,'1',1);" onkeyup="return showCodeListKey('RiskCode',[this],null,null,RiskSql,'1',1);">
       		  </TD>
              <TD  class= title>ҵ��Ա����</TD>
              <td class=input>
              	<input class=common name=AgentCode1>
              </td>           
	        </TR>      
	    </table>

    </div> 
    <INPUT VALUE="��   ѯ" class = cssButton TYPE=button onclick="easyQueryClickSelf();"> 
    
    
  	<Div  id= "divOPolGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanOPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
         <INPUT VALUE="��  ҳ" class=cssButton TYPE=button onclick="turnPage2.firstPage();"> 
         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.previousPage();"> 					
         <INPUT VALUE="��һҳ" class=cssButton TYPE=button onclick="turnPage2.nextPage();"> 
         <INPUT VALUE="β  ҳ" class=cssButton TYPE=button onclick="turnPage2.lastPage();"> 					
  	</div>
 	
  	<p>
		 <!--INPUT VALUE="У��Ͷ����" class=cssButton TYPE=button onclick="GoToAppnt();"-->
		 <!--INPUT VALUE="У�鱻����" class=cssButton TYPE=button onclick="GoToInsured();"-->
		 <!--INPUT VALUE="�ϲ����" class=cssButton TYPE=button onclick="UnionConfirm();" -->
     	
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
