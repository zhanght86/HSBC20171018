<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html> 
<% 
//程序名称：EdorNoticePrintInput.jsp
//程序功能：保全通知书在线打印控制台
//创建日期：2005-08-02 16:20:22
//更新记录：  更新人    更新日期      更新原因/内容 
%>
<%
	GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
%>
<script>
	var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
	var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>   
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="./EdorNoticePrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorNoticePrintInit.jsp"%>
<title>保全通知书打印 </title>   
</head>
<body  onload="initForm();" >
  <form  action="./EdorNoticePrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 查询部分 -->
    <table class= common border=0 width=100%>
    	<tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
			<td class= titleImg align= center>请输入查询条件：(<font color=red>如果不输入保单号、保全受理号或通知书号查询，则必须输入统计起期和统计止期</font>)</td>
	</tr>
	</table>
    <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
       <TR  class= common>
          <TD  class= title5>通知书类型<font color=red> *</font></TD>
          <TD  class= input5> 
            <input class="codeno" name=NoticeType    
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeList('bqnotice',[this,NoticeTypeName],[0,1],null,SQL,1,null);" 
            ondblclick="showCodeList('bqnotice',[this,NoticeTypeName],[0,1],null,SQL,1,null);"
             onKeyUp="showCodeListKey('bqnotice', [this,NoticeTypeName],[0,1],null,SQL,1,null);" 
             onFocus="noticeSel();"><input class="codename" name=NoticeTypeName readonly=true>
          </TD>
          <TD  class= title5>      </TD>
          <TD  class= input5>    </TD> 
       </TR>
   </table>
<div id = divPeriod style= "display: ''">
    <table  class= common align=center>
       <TR> 
          <TD  class= title5>统计起期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>统计止期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
       </TR>
   </table>
</div>

<div id = divOtherNo style= "display: ''">
   <table  class= common align=center>     
       <TR> 
          <TD  class= title5> 
          <div id = divContTitle style= "display: ''">
          保单号
          </div>
          <div id = divEdorAcceptTitle style= "display:none">
          保全受理号
          </div>        
          </TD>
          <TD  class= input5>
          <Input class="common wid" name=OtherNo >
          </TD>
          <TD  class= title5>  通知书号   </TD>
          <TD  class= input5>  <Input class="common wid" name=NoticeNo > </TD> 
       </TR>
   </table>   
</div>

<div id = divOtherNo2 style= "display:none">
   <table  class= common align=center>     
       <TR> 
          <TD  class= title5> 保单号</TD>
          <TD  class= input5>
          <Input class="common wid" name=ContNo >
          </TD>
          
          <TD  class= title5>  保全受理号   </TD>
          <TD  class= input5>  <Input class="common wid" name=EdorAcceptNo > </TD> 
       </TR>
   </table>   
</div>

   <table  class= common align=center>
       <TR>
          <TD  class= title5>   管理机构<font color=red> *</font></TD>
          <TD  class= input5><Input class= "code" name=ManageCom  id=ManageCom 
          style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this],[0,1])"
          onDblClick="showCodeList('Station',[this],[0,1])"
           onKeyUp="showCodeListKey('Station',[this],[0,1])"></TD>
          <td class = title5>
          <div id = divCustomerTitle style= "display: ''">
          客户姓名
          </div>
          </td>
          <TD  class= input5> 
          <div id = divCustomer style= "display: ''">
          <Input class="common wid" name=CustomerName > 
          </div>
          </TD>
       </TR> 
   </table>
   <div id = divCont style= "display: ''">
   <table  class= common align=center>      
       <TR>
 		  <td class="title5">业务员编码</td>
		  <td class="input5" COLSPAN="1">
			  <input NAME="AgentCode" VALUE="" MAXLENGTH="10" CLASS="code" verifyorder="1"  ondblclick="return queryAgent();" >
          </td>        
          <td class = title5 >渠道</td>
          <TD  class= input5> 
          <Input class="codeno" name=SaleChnl  id=SaleChnl
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="return showCodeList('salechnl',[this,SaleChnlName],[0,1],null,null,null,null,'130');"

          onDblClick="return showCodeList('salechnl',[this,SaleChnlName],[0,1],null,null,null,null,'130');"
          onKeyUp="return showCodeListKey('salechnl',[this,SaleChnlName],[0,1],null,null,null,null,'130');"><input class="codename" name=SaleChnlName readonly=true>
          </TD> 
       </TR>         
    </table>
   </div> 
<div id = divBonus style= "display: ''">
    <table  class= common align=center>
       <TR> 
          <TD  class= title5>分红会计年度</TD>
          <TD  class= input5><Input class="common wid" name=FiscalYear onBlur="isYearNum();"></TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
       </TR>
   </table>
</div> 
</div> </div>    
         <!-- <INPUT VALUE=" 查 询 " class= cssButton TYPE=button onClick="queryNotice();"> -->
         <a href="javascript:void(0);" class="button"onClick="queryNotice();">查   询</a>
  <br><br>
   <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNoticeGrid);">
    		</td>
    		<td class= titleImg>
    			 打印通知书任务列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divNoticeGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanNoticeGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
    	 <div align="center">
	     <INPUT VALUE="首页" class=cssButton90 TYPE=button onClick="getFirstPage();"> 
         <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="getPreviousPage();"> 					
         <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="getNextPage();"> 
         <INPUT VALUE="尾页" class=cssButton93 TYPE=button onClick="getLastPage();"> 					
       </div>
   </div>
  	<!--<p>
      <INPUT VALUE="打印通知书" class= cssButton TYPE=button onClick="printNotice();"> 
  	</p>-->  
    <a href="javascript:void(0);" class="button" onClick="printNotice();">打印通知书</a>
	
  	<input type=hidden id="fmtransact" name="fmtransact">
  	<input type=hidden id="PrtSeq" name="PrtSeq">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
<script>
	<!--不显示理赔转账失败表-->
	var SQL = "1  and code not in (#LP01#)";
</script>

