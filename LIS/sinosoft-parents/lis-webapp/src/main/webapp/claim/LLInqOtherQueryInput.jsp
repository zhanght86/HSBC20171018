<html>
<%
//���ƣ�LLInqOtherQueryInput.jsp
//���ܣ��鿴����������Ϣ�����̡�����������ۡ����̡��������ۣ�
//���ڣ�2005-10-6
//���¼�¼��  ������:     ��������     ����ԭ��/����
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
		String tClmNo = request.getParameter("ClmNo"); //�ⰸ��
		String tBatNo= request.getParameter("BatNo");	//���κ�
		String tInqNo=request.getParameter("InqNo");	//�������
		String tInqDept=request.getParameter("InqDept");	//�������
		String tType = request.getParameter("Type");
		loggerDebug("LLInqOtherQueryInput",tClmNo);
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
   <SCRIPT src="LLInqOtherQuery.js"></SCRIPT>
   <%@include file="LLInqOtherQueryInit.jsp"%>
 </head>

 <body  onload="initForm();" >
   <form method=post name=fm id=fm target="fraSubmit"> 
    <table>
        <TR>
        	<TD class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInstituInfo);"></TD>
            <TD class= titleImg>���ⰸ�µ��Ѿ����������Ϣ���������棩</TD>
       </TR>
    </table>
	<Div id= "divInstituInfo" style= "display: ''">
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLLInqConclusionGrid" ></span> </TD>
			</TR>
		</Table>
		<Table align="center">
             <TR>
                 <TD><INPUT class=cssButton90 VALUE=" ��ҳ " TYPE=button onclick="turnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="turnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="turnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton93 VALUE=" βҳ " TYPE=button onclick="turnPage.lastPage();"></TD>
             </TR>
         </Table>
		<Table class=common>
			<TR  class= common> <TD class= title> �������(��������)</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InstituConclusion" id="InstituConclusion" readonly class="common" ></textarea></TD>
			</TR>   
			<TR  class= common> <TD class= title> ���鱸ע</TD></TR>
			<TR  class= common>
				<TD  style="padding-left:16px" class= common><textarea cols="224" rows="4" name="InstituRemark" id="InstituRemark" readonly class="common" ></textarea></TD>
			</TR>   
		</Table> 
	</Div><br>
	
	<Div id= "divLLInqApplyInfo" style= "display: 'none'">
		<Table  class= common>
			<TR  class= common>
				<TD text-align: left colSpan=1><span id="spanLLInqApplyGrid" ></span> </TD>
			</TR>
		</Table>
		<Table align="center">
             <TR>
                 <TD><INPUT class=cssButton90 VALUE=" ��ҳ " TYPE=button onclick="inqturnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="inqturnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="inqturnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton93 VALUE=" βҳ " TYPE=button onclick="inqturnPage.lastPage();"></TD>
             </TR>
         </Table>
         <div class="maxbox">
	    <Table class=common>
            <TR class=common>
                <TD class=title> ��������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqPer" id="InqPer" ></TD>
                <TD class=title> ����������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqStartDate" id="InqStartDate" ></TD>
                <TD class=title> ����������� </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqEndDate" id="InqEndDate" ></TD>                
            </TR>
             <TR class=common>
            	 <TD class=title>����ԭ�� </TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InqRCode" id="InqRCode"  onclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" ondblclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);"><input class=codename name="InqRCodeName" id="InqRCodeName" readonly=true></TD>                
				<TD  class=title>�������</TD>
				<TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InitDept"  id="InitDept"  onclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);"><input class=codename name="InitDeptName" id="InitDeptName" readonly=true></TD> 	              
      			<TD class=title>�������</TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqDept" id="InqDept" onclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" ><input class=codename name="InqDeptName" id="InqDeptName" readonly=true></TD> 	   
			</TR> 
        </Table>
		<Table class=common>
			<TR  class= common> <TD class= title> ������Ŀ</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqItem" id="InqItem" readonly class="common" ></textarea></TD>
			</TR> 
			<TR  class= common> <TD class= title> ��������</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqDesc" id="InqDesc" readonly class="common" ></textarea></TD>
			</TR> 
			<TR  class= common> <TD class= title> �������</TD></TR>
			<TR  class= common>
				<TD  style="padding-left:16px" class= common><textarea cols="224" rows="4" name="InqConclusion" id="InqConclusion" readonly class="common" ></textarea></TD>
			</TR> 
		</Table> 
	</Div></Div>
	
	<Div  id= "divLLInqCourseInfo" style= "display: 'none'">
		<Table  class= common>
		  <TR  class= common>
		       <TD text-align: left colSpan=1><span id="spanLLInqCourseGrid" ></span> </TD>
		   </TR>
		</Table>
		<Table align="center">
             <TR>
                 <TD><INPUT class=cssButton90 VALUE=" ��ҳ " TYPE=button onclick="tturnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton91 VALUE="��һҳ" TYPE=button onclick="tturnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton92 VALUE="��һҳ" TYPE=button onclick="tturnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton93 VALUE=" βҳ " TYPE=button onclick="tturnPage.lastPage();"></TD>
             </TR>
         </Table>		
		<Table class=common>
			<TR  class= common> <TD class= title> �����������</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqCourse" id="InqCourse" readonly class="common" ></textarea></TD>
			</TR> 
			<TR  class= common> <TD class= title> ������̱�ע</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqCourseRemark" id="InqCourseRemark" readonly class="common" ></textarea></TD>
			</TR> 
		</Table> 
	</Div>
	
	<!--<Table class= common> 
	    <TR>
	        <TD><Input class=cssButton value=" �� �� " type=button onclick="top.close();"></TD>
	    </TR>
	</Table>--> <br> 
    <a href="javascript:void(0);" class="button" onClick="top.close();">��    ��</a>
	<!--###��������###-->
	<Input type=hidden id="ManageCom" name="ManageCom"><!--����-->
	<Input type=hidden id="tClmNo" name="tClmNo"><!--�ⰸ��-->  
    <Input type=hidden id="tBatNo" name="tBatNo"><!--���κ�-->  
    <Input type=hidden id="tInqNo" name="tInqNo"><!--�������-->  
    <Input type=hidden id="tInqDept" name="tInqDept"><!--�������-->  
    <Input type=hidden id="tType" name="tType"><!--��־-->  
    
	</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
