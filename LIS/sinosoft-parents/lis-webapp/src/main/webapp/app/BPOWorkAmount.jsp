<%
//程序名称：NoBackPolQuery.jsp
//程序功能：外包录入未回传保单查询
//创建日期：2007-09-28 15:10
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
	var comCode= <%=tComCode%>;
</script>
<html>    
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <SCRIPT src="../common/easyQueryVer3/EasyQueryPrint.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./BPOWorkAmount.js"></SCRIPT>   
  <%@include file="./BPOWorkAmountInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>外包录入未回传保单查询</title>
</head>      
 
<body  onload="initForm();" >
<form method=post name=fm id=fm target="fraSubmit" action="./BPOWorkAmountSave.jsp">
	<table class= common border=0 width=100%>
  <tr>
  <td class="common"><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,laver);"></td>
	<td class= titleImg>请输入查询条件：</td>
	</tr>
	</table>
  <form method=post name=fm>
  <Div  id= "laver" style= "display: ''" class="maxbox1">
   <table class= common>
     <TR class= common> 
          <TD  class= title5>
            外包公司
          </TD>
          <TD  class= input5>
          <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code  name=BPOID id=BPOID  ondblclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype in (#TB#)','1');" onclick="return showCodeList('querybpoid',[this],null,null,'1 and bussnotype in (#TB#)','1');" onkeyup="return showCodeListKey('querybpoid',[this],null,null,'1 and bussnotype in (#TB#)','1');">
           </TD>
          <TD  class= title5>
            管理机构
          </TD>
          <TD  class= input5>
             <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class= code name=ManageCom id=ManageCom ondblclick="return showCodeList('comcode',[this]);" onclick="return showCodeList('comcode',[this]);" onkeyup="return showCodeListKey('comcode',[this]);">
          </TD>
          </TR>
          <TR class= common>
         <td class="title5">险种代码</td>
                    <td class="input5"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="RiskCode" id="RiskCode" verify="险种代码|Code:IYRiskCode" ondblclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onclick="showCodeList('IYRiskCode',[this,RiskCodeName],[0,1])" onkeyup="showCodeListKey('IYRiskCode',[this,RiskCodeName],[0,1])"><input type="text" class="codename" name="RiskCodeName" id="RiskCodeName" readonly></td>
                    <TD  class= title5>
            开始时间
          </TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#StartDate'});" verify="起始日期|NOTNULL" dateFormat="short" name=StartDate id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
     </TR>
     <TR class= common> 
          
          <TD  class= title5>
            结束时间
          </TD>
          <TD  class= input5><Input class="coolDatePicker" onClick="laydate({elem: '#EndDate'});" verify="结束日期|NOTNULL" dateFormat="short" name=EndDate id="EndDate"><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
     </TR>
   	</table>  
    </Div>
    <!--数据区-->
    <!--INPUT VALUE="查    询" class= cssButton TYPE=button onclick="easyQuery();"--> 	
    <!--<INPUT VALUE="打印数据" class= cssButton TYPE=button onclick="easyPrint();">-->
     <a href="javascript:void(0);" class="button" onClick="easyPrint();">打印数据</a> <br><br>	

  	<Div  id= "divCodeGrid" style= "display: none" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
									<span id="spanCodeGrid" >
									</span> 
		  				</td>
					</tr>
    		</table> 
            <center>
      	<INPUT VALUE="首  页" class= cssButton90 TYPE=button onclick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class= cssButton91 TYPE=button onclick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class= cssButton92 TYPE=button onclick="getNextPage();"> 
      	<INPUT VALUE="尾  页" class= cssButton93 TYPE=button onclick="getLastPage();"> 	</center>				
  	</div>
    
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
