<html>
<%
//Name��LLInqCourseInpute.jsp
//Function��������̽���(�������¼�롢�������¼�롢���������ȷ��)
//Date��2005-6-22
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
      GlobalInput tG = new GlobalInput();
      tG = (GlobalInput)session.getValue("GI");    
      String tCurrentDate = PubFun.getCurrentDate();  //��ȡ��������ǰ����
        String tClmNo=request.getParameter("ClmNo"); //�ⰸ��
        String tInqNo=request.getParameter("InqNo"); //�������      
        String tActivityid=request.getParameter("Activityid"); //�ID
      String tMissionID =request.getParameter("MissionID");  //����������ID
      String tSubMissionID =request.getParameter("SubMissionID");  //������������ID  
      
      loggerDebug("LLInqCourseInpute",);
  %>   
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLInqCourse.js"></SCRIPT>
   <%@include file="LLInqCourseInit.jsp"%>
 </head>

 <body  onload="initForm();" >
   <form method=post name=fm target="fraSubmit"> 
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyInfo);"></TD>
            <TD class= titleImg>����������ϸ��Ϣ</TD>
       </TR>
    </table>
    <Div id= "divLLInqApplyInfo" style= "display: ''">
        <TABLE class=common>
            <tr class=common>
                <td class=title> ������ </td>
                <td class= input><Input class="readonly" readonly  name=ClmNo></td>
                <td class=title> ������� </td>
                <td class= input><Input class="readonly" readonly  name=InqNo></td>
                <td class=title> �������� </td>
                <td class= input><Input class="readonly" readonly  name=BatNo></td>                
            </tr>
            <tr class=common>
                <td class=title> �����˿ͻ��� </td>
                <td class= input><Input class="readonly" readonly  name="CustomerNo"></td>
                <td class=title> ���������� </td>
                <td class= input><Input class="readonly" readonly  name="CustomerName"></td>
                <td class=title> VIP�ͻ���־ </td>
                <td class= input><Input class="readonly" readonly  name="VIPFlag"></td>                
            </tr>
            <tr class=common>
                <td class=title> ������ </td>
                <td class= input><Input class="readonly" readonly  name="ApplyPer"></td>
                <td class=title> �������� </td>
                <td class= input><Input class="readonly" readonly  name="ApplyDate"></td>
                <td class=title> ����׶� </td>
                <!--<td class= input><Input class="readonly" readonly  name="InitDept"></td>  ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" -->
                <td class= input><Input class=codeno readonly name="InitDept" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ><input class=codename name="InitDeptName" readonly=true></TD>      
            </tr>
            <TR class=common>
				<TD  class= title>�������</TD>
				<!--<TD  class= input><input class="readonly" readonly name="InqDept"></TD>  ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"-->  
				<TD class= input><Input class=codeno readonly  name="InqDept"  onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" readonly=true></TD> 	              
      			<TD class=title> ������� </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InitDept"></TD>  ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" -->
              <TD class= input><Input class=codeno readonly name="InitDept" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ><input class=codename name="InitDeptName" readonly=true></TD> 	   
            	<TD class=title> ���ر�־ </TD>
            	<TD class= input><Input class="readonly" readonly name="LocFlag"></TD> 
            	<!--<TD class= input><input class=codeno readonly name="LocFlag" CodeData="0|3^0|����^1|���"  onkeyup="return showCodeListKeyEx('LocFlag', [this,LocFlagName],[0,1]);"><input class=codename name="LocFlagName" readonly=true></TD>--> 	   	 
            </TR>    
            <TR class=common>
			    <TD class=title> ����ԭ�� </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InqRCode"></TD>  ondblclick="return showCodeList('llinqreason',[this,InqRCodeName],[0,1]);"-->
                <TD class= input><Input class=codeno readonly  name="InqRCode"  onkeyup="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);"><input class=codename name="InqRCodeName" readonly=true></TD>               
                <TD class=title> ������Ŀ </TD>
                <TD class= input><Input class="readonly" readonly  name="InqItem"></TD>   
                <TD class=title> </TD>
                <TD class= input></TD>           
            </TR>         
    </TABLE> 
	<Table class=common>
			<TR  class= common> <TD class= common> ��������</TD></TR>
			<TR  class= common>
				<TD  class= common><textarea name="InqDesc" readonly class="hcommon" ></textarea></TD>
			</TR>   
	</Table> 
	<Table class= common> 
		    <TR><Input class=cssButton  name=""  value="��ѯ����������Ϣ" type=button onclick="queryOtherInqClick();"></TR>
	</Table> 
  </DIV>               

    <hr><!--�ָ���-->  
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqCourseInfo);"></TD>
            <TD class= titleImg>�������¼��</TD>
       </TR>
    </table>
    <Div  id= "divInqCourseInfo" align=center style= "display: ''">
    <table class = common>
      <TR  class= common>
        <TD  class= title>���鷽ʽ</TD>
        <!--<TD  class= input><input class= common name="InqMode"><font size=1 color='#ff0000'><b>*</b></font></TD>-->
              <td class= input><Input class=codeno  name="InqMode" ondblclick="return showCodeList('llinqmode',[this,InqModeName],[0,1]);" onkeyup="return showCodeListKey('llinqmode',[this,InqModeName],[0,1]);"><input class=codename name="InqModeName" readonly=true></TD>        
        <TD  class= title>����ص�</TD>
        <TD  class= input><input class= common name="InqSite"></TD>
        <TD  class= title>��������</TD>
       <TD  class= input><input class= 'coolDatePicker' dateFormat="Short" name="InqDate"></TD> 
      </TR>
      <TR class = common> 
        <TD  class= title>��һ������</TD>
        <TD  class= input><input class= common name="InqPer1"></TD>
        <TD  class= title>�ڶ�������</TD>
        <TD  class= input><input class= common name="InqPer2" ></TD>
        <TD  class= title>��������</TD>
        <TD  class= input><input class= common name="InqByPer" ></TD>
      </TR>
    </table>  
    <Table  class= common>
      <TR  class= common>
        <TD text-align: left colSpan=1><span id="spanLLInqCertificateGrid" ></span> </TD>
      </TR>
    </Table>
     <Div id= "DivInqCertificate" style= "display: 'none'">
       <hr>
          <table class= common>
              <tr class= common>
                  <TD  class= title>��֤����</TD>                      
                  <TD  class= input><Input class=codeno name=AffixCode ondblclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1],'','','','1','500');"><input class=codename name=AffixName readonly=true></TD>
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                     
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                                                                     
              </tr>   
  
              <tr class= common>
                  <TD  class= title><input type="checkbox" value="" name="checkbox" onclick="checkboxClick()">ѡ������</input></TD>                       
                  <TD  class= input> <Input class=common name="OtherName" disabled=true></TD>
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                     
                  <TD  class= title></TD>                                       
                  <TD  class= input></TD>                                                                                         
              </tr>      

          </table>
          <table class= common>
            <tr class= common>    
                  <input class=cssButton type=button value=" ȷ �� " onclick="addInqCertificate();">
                  <input class=cssButton type=button value=" ȡ �� " onclick="cancelInqCerficate();">
              </tr>
          </table> 
         <hr>  
     </Div>    
    <Table class=common>
			<TR  class= common> <TD  class= common> �������¼�� </TD></TR>
			<TR  class= common><TD class= common> <textarea name="InqCourse"  class="hcommon" ></textarea> </TD></TR>
			<TR  class= common> <TD  class= common> ������̱�ע </TD></TR>
			<TR  class= common><TD  class= common><textarea name="InqCourseRemark" class="hcommon" ></textarea></TD></TR>
	</Table>
      <Table class= common> 
        <tr>
            <td>
              <Input class=cssButton name=""  value="���鵥֤" type=button onclick="showInqCerficate();">
              <Input class=cssButton name=""  value=" ��  �� " type=button onclick="AddInqCourseClick();">
                  <Input class=cssButton name=""  value="��ѯ����" type=button onclick="InqCourseQueryClick();">
              </td>
        </tr>
     </TABLE>         
    </Div>  
    <hr><!--�ָ���-->
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqFeeInfo);"></TD>
            <TD class= titleImg>�鿱����¼��</TD>
       </TR>
    </table>
    <Div  id= "divInqFeeInfo" align=center style= "display: ''">
        <Table class= common> 
          <TR  class= common>
                <td class=title> �������� </td>
            	<td class= input><Input class=codeno readonly=true name="FeeType" ondblclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1],null,null,null,null,'400');" onkeyup="return showCodeListKey('llinqfeetype',[this,FeeTypeName],[0,1],null,null,null,null,'400');"><input class=codename name="FeeTypeName" readonly=true></TD>
                <TD  class= title>���ý��</TD>
                <TD  class= input><input class= common name="FeeSum"></TD>
                <TD  class= title>����ʱ��</TD>
                <TD  class= input><input class= 'coolDatePicker' dateFormat="Short" name="FeeDate"></TD>
           </TR>
            <TR  class= common>
                <TD  class= title>�����</TD>
                <TD  class= input><input class= common name="Payee"></TD>
                <TD  class= title>��ʽ</TD>
                <td class= input><Input class=codeno  name="PayeeType" ondblclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,PayeeTypeName],[0,1]);"><input class=codename name="PayeeTypeName" readonly=true></TD>
           </TR>
        </TABLE>
        <Table class= common>         
	    	<TR class= common><TD class= title> ��ע </TD></TR>
		    <TR class= common><TD class= input> <textarea name="Remark" class="hcommon"></textarea></TD></TR>
        </Table>  
        
      <Table class= common> 
        <tr>
            <td>
              <Input class=cssButton name=""  value=" ��  �� " type=button onclick="AddInqFeeClick();">
                  <Input class=cssButton name=""  value="��ѯ����" type=button onclick="QueryInqFeeClick();">
              </td>
        </tr>
     </TABLE>       
    </Div>  
    <hr>
    <table>
        <TR>
          <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqConclusionInfoAffix);"></TD>
            <TD class= titleImg>������������</TD>
        </TR>           
    </table>
    <Div  id= "divInqConclusionInfoAffix" align=center style= "display: ''">
    <Table class= common> 
        <tr>
            <td>
              <Input class=cssButton name="AddAffix"  value="��Ӹ���" type=button onclick="AddAffixClick();">
              <Input class=cssButton name="LoadAffix" value="�������" type=button onclick="LoadAffixClick();">
              </td>
        </tr>
     </TABLE>     
    </Div>
    <hr><!--�ָ���-->    
   <Table>
        <TR>
        	<TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqConclusionInfo);"></TD>
            <TD class= titleImg>�������¼��</TD>
        </TR>   
        
    </Table>
    <Div  id= "divInqConclusionInfo" align=center style= "display: ''">
		<Table class=common>	
			<TR  class= common>
				<TD ><input type="checkbox" value="1" name="MoreInq" onclick="MoreInqClick();">������ɱ�־</input></TD>
            </TR>		
			<TR  class= common> <TD  class= common> �������</TD></TR>
			<TR  class= common>
				<TD  class= common><textarea name="InqConclusion" disabled class="hcommon" ></textarea></TD>
			</TR>
		</Table>
		<Table class= common> 
		    <TR>
		        <TD>
		        	<Input class=cssButton  disabled name="InqConfirm"  value=" ȷ �� " type=button onclick="InqConfirmClick();">
	                <Input class=cssButton name=""  value=" �� �� " type=button onclick="turnback();">
	            </TD>
		    </TR>
	   </Table>  
      
    </Div>          
    	<!-- ��������-->
      	<input type=hidden id="InqState" name="InqState"><!--������ɱ�ʾ  ��02--��ɡ�-->  
      	<!--<td class=title> ����׶� </td>-->
     	<Input type=hidden class="readonly" readonly  name="InitPhase">
     
     	<input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
      	<input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->  
      
      	<input type=hidden id="WorkFlowFlag" name="WorkFlowFlag"><!---->   
        
      	<input type=hidden id="MissionID" name="MissionID" ><!--����ID-->  
      	<input type=hidden id="SubMissionID" name="SubMissionID" ><!--������ID-->    
      	<input type=hidden id="Activityid" name="Activityid" ><!--������ID-->    
      
        <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
        
        <Input type=hidden id="ConNo" name="ConNo"><!--����������-->

  	</form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
