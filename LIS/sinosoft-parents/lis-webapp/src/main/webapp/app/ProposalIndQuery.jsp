<%
//程序名称：ProposalIndQuery.jsp
//程序功能：个单状态轨迹查询
//创建日期：2007-03-26 10:02
//创建人  ：Fuqx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
    GlobalInput tG1 = (GlobalInput)session.getValue("GI");
    String tComCode =tG1.ComCode;
%>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1 and length(trim(code))>=6 and code like #"+<%=tComCode%>+"%#";
</script>
<script>
	var comCode= <%=tComCode%>;
</script>
<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="./ProposalIndQuery.js"></SCRIPT>   
  <%@include file="./ProposalIndQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>保单处理过程查询</title>
</head>      
 
<body  onload="initForm();" >
<form action="./ProposalIndQuerySave.jsp" method=post name=fm id="fm" target="fraSubmit">
	<table class= common border=0 width=100%>
  <tr>
    <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divFCDay);">
        </td>
	<td class= titleImg align= center>请输入查询条件：</td>
	</tr>
	</table>
  <!-- <form method=post name=fm id="fm"> -->
    <div class="maxbox">
    <Div  id= "divFCDay" style= "display: ''">
   <Table class= common>
     <TR class= common> 
          <TD  class= title5>管理机构</TD>
				  <TD  class= input5>
					  <Input style="background:url(../common/images/select--bg_03.png)     no-repeat right center" class="codeno" name=ManageCom id="ManageCom" onclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"  ondblclick="showCodeList('station',[this,ManageComName],[0,1],null,codeSql,'1');"  onkeyup="showCodeListKey('station',[this,ManageComName],[0,1],null,codeSql,'1');"><input class=codename name=ManageComName id="ManageComName" readonly=true>
				  </TD>
          <TD  class= title5>
            印刷号
          </TD>
          <TD  class= input5><Input class='common wid' name=PrtNo id="PrtNo"></TD>
      </TR>
     <TR class= common>
          <TD  class= title5>
            保单号
          </TD>
          <TD  class= input5><Input class='common wid' name=ContNo id="ContNo"></TD>
           <TD  class= title5>
            投保人姓名
          </TD>
          <TD  class= input5><Input class='common wid' name=AppntName id="AppntName"></TD>
      </TR>
     <TR class= common>
          <TD  class= title5>
            被保险人姓名
          </TD>
          <TD  class= input5><Input class='common wid' name=InsuredName id="InsuredName"></TD>
          <TD  class= title5>
            被保人客户号码
          </TD>
          <TD  class= input5><Input class='common wid' name=InsuredNo id="InsuredNo"></TD>
     </TR>
	<TR class=common>
		<TD class=title5>起始日期</TD>
		<TD class= input5>
      <Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|DATE" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		</TD>
		<TD class=title5>结束日期</TD>
		<TD class= input5>
      <Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|DATE" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
		</TD>
	</TR>
   	</Table> 
    </Div> 
    </div>
    <br>
    <!--数据区-->
    <a href="javascript:void(0)" class=button onclick="easyQuery();">查  询</a>
    <a href="javascript:void(0)" class=button onclick="easyPrint();">打印数据</a>
    <!-- <INPUT VALUE="查    询" class= cssButton TYPE=button onclick="easyQuery()"> 	
    <INPUT VALUE="打印数据" class= cssButton TYPE=button onclick="easyPrint()"> --> 	

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 保单处理流程数据
    		</td>
    	</tr>
    </table>
  	<Div  id= "divCodeGrid" style= "display: ''"  align = center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
      	<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();">
		    <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();">
		    <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();">
		    <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">				
  	</div>
    
  </form>
  <br>
  <br>
  <br>
  <br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
