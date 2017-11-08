<html>
<%
//名称：LLInqOtherQueryInput.jsp
//功能：查看其他调查信息（过程、调查申请结论、过程、机构结论）
//日期：2005-10-6
//更新记录：  更新人:     更新日期     更新原因/内容
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
		String tCurrentDate = PubFun.getCurrentDate();  //提取服务器当前日期
		String tClmNo = request.getParameter("ClmNo"); //赔案号
		String tBatNo= request.getParameter("BatNo");	//批次号
		String tInqNo=request.getParameter("InqNo");	//调查序号
		String tInqDept=request.getParameter("InqDept");	//调查机构
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
            <TD class= titleImg>该赔案下的已经发起调查信息（机构层面）</TD>
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
                 <TD><INPUT class=cssButton90 VALUE=" 首页 " TYPE=button onclick="turnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton93 VALUE=" 尾页 " TYPE=button onclick="turnPage.lastPage();"></TD>
             </TR>
         </Table>
		<Table class=common>
			<TR  class= common> <TD class= title> 调查结论(机构层面)</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InstituConclusion" id="InstituConclusion" readonly class="common" ></textarea></TD>
			</TR>   
			<TR  class= common> <TD class= title> 调查备注</TD></TR>
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
                 <TD><INPUT class=cssButton90 VALUE=" 首页 " TYPE=button onclick="inqturnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="inqturnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="inqturnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton93 VALUE=" 尾页 " TYPE=button onclick="inqturnPage.lastPage();"></TD>
             </TR>
         </Table>
         <div class="maxbox">
	    <Table class=common>
            <TR class=common>
                <TD class=title> 调查分配人 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqPer" id="InqPer" ></TD>
                <TD class=title> 调查分配日期 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqStartDate" id="InqStartDate" ></TD>
                <TD class=title> 调查结束日期 </TD>
                <TD class= input><Input class="readonly wid" readonly  name="InqEndDate" id="InqEndDate" ></TD>                
            </TR>
             <TR class=common>
            	 <TD class=title>调查原因 </TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InqRCode" id="InqRCode"  onclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" ondblclick="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,InqRCodeName],[0,1]);"><input class=codename name="InqRCodeName" id="InqRCodeName" readonly=true></TD>                
				<TD  class=title>发起机构</TD>
				<TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly  name="InitDept"  id="InitDept"  onclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InitDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InitDeptName],[0,1]);"><input class=codename name="InitDeptName" id="InitDeptName" readonly=true></TD> 	              
      			<TD class=title>调查机构</TD>
                <TD class= input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno readonly name="InqDept" id="InqDept" onclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" ondblclick="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,InqDeptName],[0,1]);" ><input class=codename name="InqDeptName" id="InqDeptName" readonly=true></TD> 	   
			</TR> 
        </Table>
		<Table class=common>
			<TR  class= common> <TD class= title> 调查项目</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqItem" id="InqItem" readonly class="common" ></textarea></TD>
			</TR> 
			<TR  class= common> <TD class= title> 调查描述</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqDesc" id="InqDesc" readonly class="common" ></textarea></TD>
			</TR> 
			<TR  class= common> <TD class= title> 调查结论</TD></TR>
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
                 <TD><INPUT class=cssButton90 VALUE=" 首页 " TYPE=button onclick="tturnPage.firstPage();"></TD>
                 <TD><INPUT class=cssButton91 VALUE="上一页" TYPE=button onclick="tturnPage.previousPage();"></TD>
                 <TD><INPUT class=cssButton92 VALUE="下一页" TYPE=button onclick="tturnPage.nextPage();"></TD>
                 <TD><INPUT class=cssButton93 VALUE=" 尾页 " TYPE=button onclick="tturnPage.lastPage();"></TD>
             </TR>
         </Table>		
		<Table class=common>
			<TR  class= common> <TD class= title> 调查过程描述</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqCourse" id="InqCourse" readonly class="common" ></textarea></TD>
			</TR> 
			<TR  class= common> <TD class= title> 调查过程备注</TD></TR>
			<TR  class= common>
				<TD style="padding-left:16px"  class= common><textarea cols="224" rows="4" name="InqCourseRemark" id="InqCourseRemark" readonly class="common" ></textarea></TD>
			</TR> 
		</Table> 
	</Div>
	
	<!--<Table class= common> 
	    <TR>
	        <TD><Input class=cssButton value=" 返 回 " type=button onclick="top.close();"></TD>
	    </TR>
	</Table>--> <br> 
    <a href="javascript:void(0);" class="button" onClick="top.close();">返    回</a>
	<!--###隐藏区域###-->
	<Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
	<Input type=hidden id="tClmNo" name="tClmNo"><!--赔案号-->  
    <Input type=hidden id="tBatNo" name="tBatNo"><!--批次号-->  
    <Input type=hidden id="tInqNo" name="tInqNo"><!--调查序号-->  
    <Input type=hidden id="tInqDept" name="tInqDept"><!--调查机构-->  
    <Input type=hidden id="tType" name="tType"><!--标志-->  
    
	</form>
  	<span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
