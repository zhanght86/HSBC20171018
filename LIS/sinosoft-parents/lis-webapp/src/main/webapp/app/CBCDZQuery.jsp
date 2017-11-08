<%
//程序名称：CBCDZQuery.jsp
//程序功能：建行对帐信息查询
//创建日期：2008-07-02 15:10
//创建人  ：
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
	var comCodeLen = <%=tComCode.toString().length()%>;
	var Str = "1 and comcode like #<%=tComCode%>%#";
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
  <SCRIPT src="./CBCDZQuery.js"></SCRIPT>   
  <%@include file="./CBCDZQueryInit.jsp"%>   
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>建行对帐信息查询</title>
</head>      
 
<body  onload="initForm();" >
	<table class= common border=0 width=100%>
  <tr>
  <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
	<td class= titleImg align= center>请输入查询条件：</td>
	</tr>
	</table>
  <form method=post name=fm id=fm>
  <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
   <table class= common>
     <TR class= common> 
          <TD  class= title5>
            业务开始时间
          </TD>
          <TD  class= input5>
          <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#StartDate'});"dateFormat="short" name=StartDate id=StartDate><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            业务结束时间
          </TD>
          <TD  class= input5>
           <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EndDate'});"dateFormat="short" name=EndDate id=EndDate><span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
     </TR>
          <TR class= common> 
          
          <TD  class= title5>
            管理机构
          </TD>
          <TD  class= input5>
             <Input class="common wid" name=ManageCom value=<%=tComCode %>  
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
          onclick="return showCodeList('comcode',[this],null,null,Str,'1');" 
              onDblClick="return showCodeList('comcode',[this],null,null,Str,'1');" 
              onKeyUp="return showCodeListKey('comcode',[this],null,null,Str,'1');"  readonly>
          </TD>
     </TR>
   	</table> 
    </div>
    </div> 
    <br>
    <!--数据区-->
     <a href="javascript:void(0);" class="button"onClick="easyQuery();">查   询</a>
      <a href="javascript:void(0);" class="button"onClick="easyPrint();">生成下载和打印数据</a>
   <!-- <INPUT VALUE="查询" class= Button TYPE=button onClick="easyQuery();"> 	
    <INPUT VALUE="生成下载和打印数据" class= Button TYPE=button onClick="easyPrint();"> -->	

    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCodeGrid);">
    		</td>
    		<td class= titleImg>
    			 建行对帐信息查询
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
             <div align="center">
      	<INPUT VALUE="首页" class="cssButton90" TYPE=button onClick="getFirstPage();"> 
      	<INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="getPreviousPage();"> 					
      	<INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="getNextPage();"> 
      	<INPUT VALUE="尾页" class= "cssButton93" TYPE=button onClick="getLastPage();"> 
        </div>					
  	</div>
    <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
