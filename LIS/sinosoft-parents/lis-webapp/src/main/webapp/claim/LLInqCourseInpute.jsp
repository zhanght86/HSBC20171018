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
 		  
	%> 	
	<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
	<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
	<SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
	<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
	<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
	<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
	<SCRIPT src="LLInqCourse.js"></SCRIPT>
	<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
	<%@include file="LLInqCourseInit.jsp"%>
 </head>

 <body  onload="initForm();" >
   <form method=post name=fm id=fm target="fraSubmit"> 
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLInqApplyInfo);"></TD>
            <TD class= titleImg>����������ϸ��Ϣ</TD>
       </TR>
    </Table>
    <Div id= "divLLInqApplyInfo" style= "display: ''" class="maxbox">
        <Table class=common>
            <TR class=common>
                <TD class=title> ������ </TD>
                <TD class= input><Input class="readonly wid" readonly  name=ClmNo id=ClmNo></TD>
                <TD class=title> ������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name=InqNo id=InqNo></TD>
                <TD class=title> �������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name=BatNo id=BatNo></TD>                
            </TR>
            <TR class=common>
                <TD class=title> �����˿ͻ��� </TD>
                <TD class= input><Input class="readonly wid" readonly  name="CustomerNo" id="CustomerNo"></TD>
                <TD class=title> ���������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name="CustomerName" id="CustomerName"></TD>
                <TD class=title> VIP�ͻ���־ </TD>
                <TD class= input><Input class="readonly wid" readonly  name="VIPFlag" id="VIPFlag"></TD>                
            </TR>
            <TR class=common>
                <TD class=title> ������ </TD>
                <TD class= input><Input class="readonly wid" readonly  name="ApplyPer" id="ApplyPer"></TD>
                <TD class=title> �������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name="ApplyDate" id="ApplyDate"></TD>
                <TD class=title> ����׶� </TD>
                <!--##ondblclick="return showCodeList('llinitphase',[this,InitPhaseName],[0,1]);"##-->
	            <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InitPhase" id="InitPhase" onclick="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);" ondblclick="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);" onkeyup="return showCodeListKey('llinitphase',[this,InitPhaseName],[0,1]);"><input class=codename name="InitPhaseName" id="InitPhaseName" readonly=true></TD>          
            </TR>
            <TR class=common>
				<TD  class= title>�������</TD>
				<!--<TD  class= input><input class="readonly" readonly name="InqDept"></TD>  ondblclick="return showCodeList('stati',[this,InqDeptName],[0,1]);"-->  
				<TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InqDept" id="InqDept"  onclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);"><input class=codename name="InqDeptName" id="InqDeptName" readonly=true></TD> 	              
      			<TD class=title> ������� </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InitDept"></TD>  ondblclick="return showCodeList('stati',[this,InitDeptName],[0,1]);" -->
              <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InitDept" id="InitDept" onclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ><input class=codename name="InitDeptName" id="InitDeptName" readonly=true></TD> 	   
            	<TD class=title> ���ر�־ </TD>
            	<TD class= input><Input class="readonly wid" readonly name="LocFlag" id="LocFlag"></TD> 
            	<!--<TD class= input><input class=codeno readonly name="LocFlag" CodeData="0|3^0|����^1|���"  onkeyup="return showCodeListKeyEx('LocFlag', [this,LocFlagName],[0,1]);"><input class=codename name="LocFlagName" readonly=true></TD>--> 	   	 
            </TR>    
            <TR class=common>
			    <TD class=title> ����ԭ�� </TD>
                <!--<TD class= input><Input class="readonly" readonly  name="InqRCode"></TD>  ondblclick="return showCodeList('llinqreason',[this,InqRCodeName],[0,1]);"-->
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InqRCode" id="InqRCode"  onclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" ondblclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);"><input class=codename name="InqRCodeName" id="InqRCodeName" readonly=true></TD>               
                <TD class=title> ������Ŀ </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqItem" id="InqItem"></TD>   
                <TD class=title> </TD>
                <TD class= input></TD>           
            </TR>         
		</Table>   
		<Table class=common>
			<TR  class= common> <TD class= title> ��������</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="199" rows="4" name="InqDesc" readonly class="common" ></textarea></TD>
			</TR>   
		</Table> </DIV>
		<!--<Table class= common> 
		    <TR><Input class=cssButton  name=""  value="��ѯ����������Ϣ" type=button onclick="queryOtherInqClick();"></TR>
	    </Table> -->
			<a name="" href="javascript:void(0);" class="button" onClick="queryOtherInqClick();">��ѯ����������Ϣ</a>           
 
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqCourseInfo);"></TD>
            <TD class= titleImg>�������¼��</TD>
       </TR>
    </Table>
    <Div  id= "divInqCourseInfo" align=center style= "display: ''" class="maxbox1">
		<Table class = common>
			<TR  class= common>
				<TD  class= title>���鷽ʽ</TD>
				<!--<TD  class= input><input class= common name="InqMode"><font size=1 color='#ff0000'><b>*</b></font></TD>-->
	            <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name="InqMode" id="InqMode" ondblclick="return showCodeList('llinqmode',[this,InqModeName],[0,1]);" onclick="return showCodeList('llinqmode',[this,InqModeName],[0,1]);" onkeyup="return showCodeListKey('llinqmode',[this,InqModeName],[0,1]);"><input class=codename name="InqModeName" id="InqModeName" readonly=true></TD>				
				<TD  class= title>����ص�</TD>
				<TD  class= input><input class="wid" class= common name="InqSite" id="InqSite"></TD>
				<TD  class= title>��������</TD>
            <!--     <TD  class= input><input class="wid" class= 'multiDatePicker' dateFormat="Short" name="InqDate" id="InqDate"></TD>   --> 
			<td>	<Input class="coolDatePicker" onClick="laydate({elem: '#InqDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=InqDate id="InqDate"><span class="icon"><a onClick="laydate({elem: '#InqDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> </td> <!-- modify wyc -->
				
			</TR>
			<TR class = common> 
				<TD  class= title>��һ������</TD>
				<TD  class= input><input class="wid" class= common name="InqPer1" id="InqPer1"></TD>
				<TD  class= title>�ڶ�������</TD>
				<TD  class= input><input class="wid" class= common name="InqPer2" id="InqPer2" ></TD>
				<TD  class= title>��������</TD>
				<TD  class= input><input class="wid" class= common name="InqByPer" id="InqByPer" ></TD>
			</TR>
		</Table><br>	
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLLInqCertificateGrid" ></span> </TD>
			</TR>
		</Table>
		 <Div id= "DivInqCertificate" style= "display: 'none'">
	        <Table class= common>
	            <TR class= common>
	                <TD  class= title>��֤����</TD>                      
	                <TD  class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name=AffixCode id=AffixCode ondblclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onclick="showCodeList('llmaffix',[this,AffixName],[0,1]);" onkeyup="showCodeListKey('llmaffix',[this,AffixName],[0,1],'','','','1','500');"><input class=codename name=AffixName id=AffixName readonly=true></TD>
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                     
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                                                                     
	            </TR>   
  
	            <TR class= common>
	                <TD  class= title><input style="vertical-align:middle" type="checkbox" value="" name="checkbox" onclick="checkboxClick()"><span style="vertical-align:middle">ѡ������</span></input></TD>                       
	                <TD  class= input> <Input class="wid" class=common name="OtherName" id="OtherName" disabled=true></TD>
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                     
	                <TD  class= title></TD>                                       
	                <TD  class= input></TD>                                                                                         
            	</TR>      

	        </Table>
	       <Table class= common style="display:none">
	    	    <TR class= common>    
	                <input class=cssButton type=button value=" ȷ �� " onclick="addInqCertificate();">
	                <input class=cssButton type=button value=" ȡ �� " onclick="cancelInqCerficate();">
	            </TR>
	        </Table>
 
	   </Div>  
       <!--<a href="javascript:void(0);" class="button" onClick="addInqCertificate();">ȷ    ��</a>
       <a href="javascript:void(0);" class="button" onClick="cancelInqCerficate();">ȡ    ��</a>  -->
		<Table class=common>
			<TR  class= common> <TD  class= title> �������¼�� </TD></TR>
			<TR  class= common><TD style="padding-left:16px" class= common> <textarea cols="199" rows="4" name="InqCourse"  class="common" ></textarea> </TD></TR>
			<TR  class= common> <TD  class= title> ������̱�ע </TD></TR>
			<TR  class= common><TD style="padding-left:16px"  class= common><textarea name="InqCourseRemark" cols="199" rows="4" class="common" ></textarea></TD></TR>
		</Table></Div>
	   <!-- <Table class= common> 
		    <TR>
		        <TD>
		        	<Input class=cssButton name=""  value="���鵥֤" type=button onclick="showInqCerficate();">
		        	<Input class=cssButton name=""  value=" ��  �� " type=button onclick="AddInqCourseClick();">
	                <Input class=cssButton name=""  value="��ѯ����" type=button onclick="InqCourseQueryClick();">
	            </TD>
		    </TR>
	   </Table> -->	
       <br>	    
    	<a href="javascript:void(0);" name="" class="button" onClick="showInqCerficate();">���鵥֤</a>
        <a href="javascript:void(0);" name="" class="button" onClick="AddInqCourseClick();">��    ��</a>
        <a href="javascript:void(0);" name="" class="button" onClick="InqCourseQueryClick();">��ѯ����</a>
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqFeeInfo);"></TD>
            <TD class= titleImg>�鿱����¼��</TD>
       </TR>
    </Table>
    <Div  id= "divInqFeeInfo" align=center style= "display: ''" class="maxbox1">
        <Table class= common> 
        	<TR  class= common>
                <TD class=title> �������� </TD>
		        <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name="FeeType"  id="FeeType" ondblclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1]);"onclick="return showCodeList('llinqfeetype',[this,FeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llinqfeetype',[this,FeeTypeName],[0,1]);"><input class=codename name="FeeTypeName" id="FeeTypeName" readonly=true></TD>
                <TD  class= title>���ý��</TD>
                <TD  class= input><input class="wid" class= common name="FeeSum" id="FeeSum"></TD>
                <TD  class= title>����ʱ��</TD>
                <TD  class= input><!--<input class= 'multiDatePicker' dateFormat="Short" name="FeeDate">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#FeeDate'});" verify="��Ч��ʼ����|DATE" dateFormat="short" name=FeeDate id="FeeDate"><span class="icon"><a onClick="laydate({elem: '#FeeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
           </TR>
            <TR  class= common>
                <TD  class= title>�����</TD>
                <TD  class= input><input class="wid" class= common name="Payee" id="Payee"></TD>
                <TD  class= title>��ʽ</TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly=true name="PayeeType" id="PayeeType" ondblclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onclick="return showCodeList('llpaymode',[this,PayeeTypeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,PayeeTypeName],[0,1]);"><input class=codename name="PayeeTypeName" id="PayeeTypeName" readonly=true></TD>
           </TR>
        </Table>
        <Table class= common>         
	    	<TR class= common><TD class= title> ��ע </TD></TR>
		    <TR class= common><TD style="padding-left:16px" class= input> <textarea name="Remark" id="Remark" cols="199" rows="4" class="common"></textarea></TD></TR>
        </Table>	</Div>
	<!--	    
	    <Table class= common> 
		    <TR>
		        <TD>
		        	<Input class=cssButton name=""  value=" ��  �� " type=button onclick="AddInqFeeClick();">
	                <Input class=cssButton name=""  value="��ѯ����" type=button onclick="QueryInqFeeClick();">
	            </TD>
		    </TR>
	   </Table> -->      
<a href="javascript:void(0);" class="button" name="" onClick="AddInqFeeClick();">��    ��</a>
<a href="javascript:void(0);" class="button" name="" onClick="QueryInqFeeClick();">��ѯ����</a>    
    <Table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqConclusionInfo);"></TD>
            <TD class= titleImg>�������¼��</TD>
        </TR>   
        
    </Table>
    <Div  id= "divInqConclusionInfo" align=center style= "display: ''" class="maxbox1">
		<Table class=common>	
			<TR  class= common>
				<TD class="input" ><input type="checkbox" value="1" name="MoreInq" onclick="MoreInqClick();">������ɱ�־</input></TD>
            </TR>		
			<TR  class= common> <TD  class=title> �������</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="199" rows="4" name="InqConclusion" id="InqConclusion" disabled class="common" ></textarea></TD>
			</TR>
		</Table></Div>
		<!--<Table class= common> 
		    <TR>
		        <TD>
		        	
	                <Input class=cssButton name=""  value=" �� �� " type=button onclick="turnback();">
	            </TD>
		    </TR>
	   </Table> -->		<br> 
			<Input class=cssButton  disabled name="InqConfirm"  value=" ȷ �� " type=button onclick="InqConfirmClick();">
            <a href="javascript:void(0);" name="" class="button" onClick="turnback();">��    ��</a>
    	        
		<!-- ��������-->
	    <input type=hidden id="InqState" name="InqState"><!--������ɱ�ʾ  ��02--��ɡ�-->	
	    <!--<TD class=title> ����׶� </TD>-->
	 	<!--<Input type=hidden class="readonly" readonly  name="InitPhase">-->
	 	
	 	<input type=hidden id="isReportExist" name="isReportExist"><!--�Ƿ�Ϊ�����¼�,���ж���Ϊ��-->
	    <input type=hidden id="fmtransact" name="fmtransact"><!--�������롮insert,delete����-->	
	    
	    <input type=hidden id="WorkFlowFlag" name="WorkFlowFlag"><!---->	 
	      
	    <input type=hidden id="MissionID" name="MissionID"><!--����ID-->	
	    <input type=hidden id="SubMissionID" name="SubMissionID"><!--������ID-->	  
    	<input type=hidden id="Activityid" name="Activityid"><!--������ID-->	  
    	
        <Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
        
        <Input type=hidden id="ConNo" name="ConNo"><!--����������-->

	</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
