<html>
<%
//Name��LLInqDistributeInput.jsp
//Function����������������
//Date��2004-12-23 16:49:22
//Author��wujs
//���¼�¼��  ������:yuejw    ��������     ����ԭ��/����
%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <%@page import = "com.sinosoft.utility.*"%>
 <%@page import = "com.sinosoft.lis.schema.*"%>
 <%@page import = "com.sinosoft.lis.vschema.*"%>
 <%@include file="../common/jsp/UsrCheck.jsp"%>

 <head >
 	<%
	    GlobalInput tGlobalInput = new GlobalInput(); 
	    tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
 <script>	
	var operator = "<%=tGlobalInput.Operator%>";   //��¼����Ա
	var manageCom = "<%=tGlobalInput.ManageCom%>"; //��¼��½����
	var comcode = "<%=tGlobalInput.ComCode%>"; //��¼��½����
</script>
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
   <!-- modified by lzf -->
   <script src="../common/javascript/jquery.workpool.js"></script>
   <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
   <SCRIPT src="LLInqDistribute.js"></SCRIPT>
   <%@include file="LLInqDistributeInit.jsp"%>
 </head>

 <body  onload="initForm();initElementtype();" >
   <form action="./LLInqDistributeSave.jsp" method=post name=fm id=fm target="fraSubmit">
   <div id = "DistributeInputPool" ></div>

<!--lzf 	<Table>
		<TR>
		     <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQueryCondition);"></TD>
		     <TD class= titleImg>�������ѯ����</TD>
		</TR>
	</Table>  
     <Div  id= "divQueryCondition" style= "display: ''">
       <Table  class= common>
		   <TR  class= common>
		   		<TD  class= title>������</TD>
		   		<TD  class= input><input class= common name="ClmNo" ></TD>
				<TD  class= title>�ͻ�����</TD>
				<TD  class= input><input class= common name="CustomerNo"></TD>
				<TD  class= title>�ͻ�����</TD>
				<TD  class= input><input class= common name="CustomerName"></TD>				
		  </TR>
      </Table>
    </DIV>
    <input class=cssButton VALUE="��ѯ��������" TYPE=button onclick="InqApplyGridQueryClick();">    
	<Table>
		<TR>
		     <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqApplyGrid);"></TD>
		     <TD class= titleImg>��������������</TD>
		</TR>
	</Table>    
    <Div  id= "divInqApplyGrid" align=center style= "display: ''">
      <Table  class= common>
        <TR  class= common>
          <TD text-align: left colSpan=1><span id="spanInqApplyGrid" ></span></TD>
       </TR>
    </Table>
    <Table>
	    <tr>
		     <td><input class=cssButton VALUE="��  ҳ" TYPE=button onclick="turnPage.firstPage();HighlightByRow()"></td>
             <td><input class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();HighlightByRow()"></td>
             <td><input class=cssButton VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();HighlightByRow()"></td>
             <td><input class=cssButton VALUE="β  ҳ" TYPE=button onclick="turnPage.lastPage();HighlightByRow()"></td>
	    </tr>
    </Table>	
    </Div> 
    -->
  <div id= "divInqPer" style= "display: none" >
	<!--����������Ϣ-->
    <Table>
        <TR>
            <TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqSavedInfoGrid);"></TD>
            <TD class= titleImg>����������Ϣ:</TD>
        </TR>
    </Table>  
	<Div  id= "divInqSavedInfoGrid" align=center style= "display: ''" class="maxbox">  	
	    <Table class = common >
            <TR class=common>
		        <TD class=title5>���������� </TD>
                <TD class= input5><Input class="readonly wid" readonly  name=SavedCustomerName id=SavedCustomerName></TD>
                <TD class=title5> �������� </TD>
                <TD class= input5><Input class="readonly wid" readonly  name=SavedBatNo id=SavedBatNo></TD> </TR>
               <TR class=common> 		         
		        <TD class=title5>��ص��� </TD>
		        <TD class= input5><input class="readonly wid" readonly name="SavedMoreInq" id="SavedMoreInq"></TD> 
                <TD class=title5> ����ԭ�� </TD>
                <TD class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno readonly disabled name="SavedInqReason"  id="SavedInqReason" ondblclick="return showCodeList('llinqreason',[this,SavedInqReasonName],[0,1]);" onclick="return showCodeList('llinqreason',[this,SavedInqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,SavedInqReasonName],[0,1]);"><input class=codename name="SavedInqReasonName" id="SavedInqReasonName" readonly=true></TD>                                                 
            </TR>	    	
            <TR class=common>
		        <!-- <TD class=title>V I P ��־ </TD>
                <TD class= input><Input class="readonly" readonly  name=SavedVIPFlag></TD>   -->         	
                                             
                <TD class=title5> ������� </TD>
		        <TD class= input5><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno readonly disabled name="SavedInqOrg" id="SavedInqOrg" ondblclick="return showCodeList('stati',[this,SavedInqOrgName],[0,1]);" onclick="return showCodeList('stati',[this,SavedInqOrgName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,SavedInqOrgName],[0,1]);"><input class=codename name="SavedInqOrgName" id="SavedInqOrgName" readonly=true></TD> 	 	
            </TR> 	    	
        </TABLE>
        <Table class= common>		
                <TR class= common>
	    	        <TD class= title5> ������Ŀ </TD>
    		    </TR> 
		        <TR class= common>       
	    	        <TD colspan="4" style="padding-left:16px"> <textarea name="SavedInqItem"  rows="4" cols="166" readonly class="common"></textarea></TD>
		        </TR>
    	    
	    	    <TR class= common>
	    	        <TD class= title5> �������� </TD>
    		    </TR> 
		        <TR class= common>       
	    	        <TD colspan="4" style="padding-left:16px"> <textarea name="SavedInqDesc" rows="4" cols="166" readonly class="common"></textarea></TD>
		        </TR>
        </TABLE>	    	

	    <Table class= common>	
	    	<TR  class= common>
	            <TD class=title5> ָ�������� </TD> 
	       		<TD class="input5"><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center"  class=codeno readonly=true name=InqPer id=InqPer ondblclick="return showCodeList(fm.claimUserTable.value,[this,InqPerName],[0,1],null,fm.all('tComCode').value,'comcode','1');" onclick="return showCodeList(fm.claimUserTable.value,[this,InqPerName],[0,1],null,fm.all('tComCode').value,'comcode','1');" onkeyup="return showCodeListKey(fm.claimUserTable.value,[this,InqPerName],[0,1],null,fm.all('tComCode').value,'comcode','1');"><input class=codename name=InqPerName id=InqPerName readonly=true></TD>    
                <TD class=title5></TD>
                <TD class="input5"></TD>
	       </TR>          
	     </Table>   	</DIV>
         <input class=cssButton value="ָ��ȷ��"  TYPE=button onclick="Designate();">
</div>            
     <!--������-->
     <Input type=hidden class= common name="fmtransact" >
     <Input type=hidden class= common name="tClmNo" >   
     <Input type=hidden class= common name="tInqNo" >   
     <Input type=hidden class= common name="tCustomerNo" >   
     <Input type=hidden class= common name="tCustomerName" >   
     <Input type=hidden class= common name="tInitDept" >    
     <Input type=hidden class= common name="Operator" >
	 <Input type=hidden class= common name="ComCode" >	
	 <Input type=hidden class= common name="tComCode" >	<!--���ڸ��ݻ�����ѯ������--> 
 	 <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
	 <Input type=hidden class= common name="claimUserTable" >	
     <Input type=hidden name=CurDate  id="CurDate">

	<!--����������--> 	
	<Input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
	<Input type=hidden id="MissionID" 	 name= "MissionID">
	<Input type=hidden id="SubMissionID" name= "SubMissionID">
	<Input type=hidden id="ActivityID" name= "ActivityID">

  	</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
  	
	</body>
</html>
