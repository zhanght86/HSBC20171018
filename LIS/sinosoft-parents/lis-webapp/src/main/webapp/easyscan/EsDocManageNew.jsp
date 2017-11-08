<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
	String bussType = request.getParameter("busstype");
	loggerDebug("EsDocManageNew","url的参数为:" + bussType);
	String bussStr = "";
	if("".equals(bussType) || bussType == null) bussType = "LP";
	if("TB".equals(bussType)) bussStr = "承保";
	if("BQ".equals(bussType)) bussStr = "保全";
	if("OF".equals(bussType)) bussStr = "财务";
	if("LP".equals(bussType)) bussStr = "理赔";
	
	GlobalInput tG1 = (GlobalInput) session.getValue("GI");
	String ComCode = tG1.ComCode;
	String Operator = tG1.Operator; //操作员
%>
<html> 
<%
//程序名称：EsDocManage.jsp
//程序功能：
//创建日期：2009-09-09
//创建人  yanglh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="EsDocManageNew.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="EsDocManageNewInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./EsDocManageNewSave.jsp" method=post name=fm id=fm target="fraSubmit">
    
    
    <table>
    	<tr>
    		<td class="common">
    		     <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCode1);">
    		</td>
    		 <td class= titleImg>
        		 <%=bussStr%>扫描单证信息
       		 </td>   		 
    	</tr>
    	<tr>
    		  	
    	</tr>
    </table>
    <Div  id= "divCode1" style= "display: ''" class="maxbox1">
      <table  class= common>
        <TR  class= common>
          <TD  class= title5>
            单证号码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=DOC_CODE id=DOC_CODE >
             <input class= common readonly type=hidden name=busstype value=<%=bussType%>>
             <Input class= common  type=hidden name=PrtNo id="PrtNo" >
             <Input class= common  type=hidden name=DOC_ID id=DOC_ID >
             <Input class= common  type=hidden name=NUM_PAGES id="NUM_PAGES" >
             <Input class= common  type=hidden name=MngCom id="MngCom" value=<%=ComCode%> ></TD>
             <TD  class= title5></TD>
             <TD  class= input5></TD>
             </TR>
       <!--</table>-->
      <!--Input class=code name=ComCode ondblclick="return showCodeList('station',[this]);" onkeyup="return showCodeListKey('station',[this]);" >
      <!--Input class= common name=ComCode -->
	      <!--<Div  id= "divReason" style= "display: 'none'">
		      <table class = common border=0>-->
			    <TR style="display:none" class=common >
			       
				      <TD class= title5> 单证删除原因 </TD>
				    </TR>
				    <TR style="display:none"  class= common>
				      <TD style="padding-left:16px" colspan="4"><textarea name="DelReason" cols="146" rows="4" class="common"></textarea></TD>
				    </TR>
		    </table>  
	    </Div>
    </Div>
    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden name="BussType" value="">
    <%@include file="../common/jsp/OperateButton.jsp"%>
    <!-- ES_DOC_PAGES数据区 MultiLine -->
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 扫描件单证页信息
    		</td>
    	</tr>
    </table>
    
   
    
  	<Div  id= "divCodeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanCodeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <center>
      	<INPUT VALUE="首  页" TYPE=Button class=cssButton90 onclick="turnPage1.firstPage();"> 
      	<INPUT VALUE="上一页" TYPE=Button class=cssButton91 onclick="turnPage1.previousPage();"> 					
      	<INPUT VALUE="下一页" TYPE=Button class=cssButton92 onclick="turnPage1.nextPage();"> 
      	<INPUT VALUE="尾  页" TYPE=Button class=cssButton93 onclick="turnPage1.lastPage();"> 	</center>				
  	</div>
<!-- <INPUT VALUE="修改页码" TYPE=Button class=cssButton onclick="saveUpdate()"> 	
    <INPUT VALUE="删除选中页" TYPE=Button class=cssButton onclick="deleteChecked()">
    <INPUT VALUE="修改单证类型" TYPE=Button class=cssButton onclick="updateClick1()">
    <INPUT VALUE="删除单证" TYPE=Button class=cssButton onclick="deleteClick1()">--><br>
    <a href="javascript:void(0);" class="button" onClick="saveUpdate();">修改页码</a>
    <a href="javascript:void(0);" class="button" onClick="deleteChecked();">删除选中页</a>
    <a href="javascript:void(0);" class="button" onClick="updateClick1();">修改单证类型</a>
    <a href="javascript:void(0);" class="button" onClick="deleteClick1();">删除单证</a><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
