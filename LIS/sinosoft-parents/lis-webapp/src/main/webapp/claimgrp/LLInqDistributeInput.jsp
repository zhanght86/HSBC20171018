<html>
<%
//Name：LLInqDistributeInput.jsp
//Function：调查任务分配界面
//Date：2004-12-23 16:49:22
//Author：wujs
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
%>
 <%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 <%@include file="../common/jsp/UsrCheck.jsp"%>

 <head >
   <%
      GlobalInput tGlobalInput = new GlobalInput(); 
      tGlobalInput = (GlobalInput)session.getValue("GI");
    %>
   <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
   <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
   <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
   <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
   <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
   <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
   <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
   <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
   <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
   <SCRIPT src="LLInqDistribute.js"></SCRIPT>
   <%@include file="LLInqDistributeInit.jsp"%>
 </head>

 <body  onload="initForm();" >
   <form action="./LLInqDistributeSave.jsp" method=post name=fm target="fraSubmit">
  <Table>
    <TR>
         <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divQueryCondition);"></TD>
         <TD class= titleImg>请输入查询条件</TD>
    </TR>
  </Table>  
     <Div  id= "divQueryCondition" style= "display: ''">
       <Table  class= common>
       <TR  class= common>
        <TD  class= title>立案号</TD>
        <TD  class= input><input class= common name="ClmNo" ></TD>
        <TD  class= title>出险人编码</TD>
        <TD  class= input><input class= common name="CustomerNo"></TD>
        <TD  class= title>出险人姓名</TD>
        <TD  class= input><input class= common name="CustomerName"></TD>        
      </TR>
      </Table>
    </DIV>
    <input class=cssButton VALUE="查询调查任务" TYPE=button onclick="InqApplyGridQueryClick();">    
  <Table>
    <TR>
         <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqApplyGrid);"></TD>
         <TD class= titleImg>调查任务分配队列</TD>
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
         <td><input class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
         <td><input class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
         <td><input class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
         <td><input class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
      </tr>
    </Table>  
    </Div>
    <div id= "divInqPer" style= "display: 'none'" >
     
      <HR>
    <Table>
        <TR>
            <TD><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divInqSavedInfoGrid);"></TD>
            <TD class= titleImg>调查内容信息:</TD>
        </TR>
    </Table>  
	<Div  id= "divInqSavedInfoGrid" align=center style= "display: ''">  	
	    <Table class = common >
            <TR class=common>
		        <TD class=title>出险人姓名 </TD>
                <TD class= input><Input class="readonly" readonly  name=SavedCustomerName></TD>
                <TD class=title> 调查批次 </TD>
                <TD class= input><Input class="readonly" readonly  name=SavedBatNo></TD> 		         
		        <TD class=title>异地调查 </TD>
		        <TD class= input><input class="readonly" readonly name="SavedMoreInq"></TD>                                               
            </TR>	    	
            <TR class=common>
		        <!-- <TD class=title>V I P 标志 </TD>
                <TD class= input><Input class="readonly" readonly  name=SavedVIPFlag></TD> -->          	
                <TD class=title> 调查原因 </TD> 
                <TD class= input><Input class=codeno readonly disabled name="SavedInqReason" ondblclick="return showCodeList('llinqreason',[this,SavedInqReasonName],[0,1]);" onkeyup="return showCodeListKey('llinqreason',[this,SavedInqReasonName],[0,1]);"><input class=codename name="SavedInqReasonName" readonly=true></TD>                                 
                <TD class=title> 调查机构 </TD>
		        <TD class= input><Input class=codeno readonly disabled name="SavedInqOrg" ondblclick="return showCodeList('stati',[this,SavedInqOrgName],[0,1]);" onkeyup="return showCodeListKey('stati',[this,SavedInqOrgName],[0,1]);"><input class=codename name="SavedInqOrgName" readonly=true></TD> 	 	
            </TR> 	    	
        </TABLE>
        <Table class= common>		
                <TR class= common>
	    	        <TD class= title> 调查项目 </TD>
    		    </TR> 
		        <TR class= common>       
	    	        <TD class= input> <textarea name="SavedInqItem"  rows="3" readonly class="hcommon"></textarea></TD>
		        </TR>
    	    
	    	    <TR class= common>
	    	        <TD class= title> 调查描述 </TD>
    		    </TR> 
		        <TR class= common>       
	    	        <TD class= input> <textarea name="SavedInqDesc" rows="3" readonly class="hcommon"></textarea></TD>
		        </TR>
        </TABLE>	    	
	</DIV>
	    <Table class= common>	
	    	<TR  class= common>
	            <TD class=title> 指定调查人 </TD> 
	       		<TD><Input class=codeno readonly=true name=InqPer ondblclick="return showCodeList('llclaimuser',[this,InqPerName],[0,1],null,fm.all('tComCode').value,'comcode','1');" onkeyup="return showCodeListKey('llclaimuser',[this,InqPerName],[0,1],null,fm.all('tComCode').value,'comcode','1');"><input class=codename name=InqPerName readonly=true></TD>    
	       </TR>          
	     </Table>   
         <!--<input class=cssButton value="指定确认"  TYPE=button onclick="Designate()";">-->
         <a href="javascript:void(0);" class="button" onClick="Designate();">指定确认</a>
    </div>            
     <!--隐藏域-->
     <Input type=hidden class= common name="fmtransact" >
     <Input type=hidden class= common name="tClmNo" >   
     <Input type=hidden class= common name="tInqNo" >   
     <Input type=hidden class= common name="tCustomerNo" >   
     <Input type=hidden class= common name="tCustomerName" >   
     <Input type=hidden class= common name="tInitDept" >    
     <Input type=hidden class= common name="Operator" >
     <Input type=hidden class= common name="ComCode" >  
     <Input type=hidden class= common name="tComCode" >  <!--用于根据机构查询调查人--> 
     <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->

  <!--工作流参数-->   
  <Input type=hidden id="WorkFlowFlag" name="WorkFlowFlag">
  <Input type=hidden id="MissionID"    name= "MissionID">
  <Input type=hidden id="SubMissionID" name= "SubMissionID">
  <Input type=hidden id="ActivityID" name= "ActivityID">

    </form>
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  </body>
</html>
