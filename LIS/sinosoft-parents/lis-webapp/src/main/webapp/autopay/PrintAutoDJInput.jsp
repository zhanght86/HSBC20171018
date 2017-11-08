<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  GlobalInput GI = new GlobalInput();
	GI = (GlobalInput)session.getValue("GI");
%>
<script>
	var manageCom = "<%=GI.ManageCom%>"; //记录管理机构
	var ComCode = "<%=GI.ComCode%>"; //记录登陆机构
</script>

<html>    
<% 
//程序名称：自动垫交打印程序
//程序功能：
//创建日期：2004-5-20
//创建人  ：CrtHtml程序创建
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="PrintAutoDJInput.js"></SCRIPT>    
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <%@include file="PrintAutoDJInit.jsp"%>
</head>
<body  onload="initForm();" >    
  <form  method=post name=fm id="fm"  target="fraSubmit">
    <table class= common border=0 width=100%>
    	<tr> 
        <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>    		
    		 <td class= titleImg>
        		输入查询条件
       		 </td>   		      
    	</tr>
    </table>
        <Div  id= "divFeeInv" style= "display: ''">
         <div class="maxbox1" >
      <table class= common border=0 width=100%>
        <TR  class= common>
          <TD  class= title5>  开始日期  </TD>
          <TD  class= input5>  <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
 </TD> 
          <TD  class= title5>   结束日期 </TD>
          <TD  class= input5>	 <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="提数日期|Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span> </TD> 
          </TR>
          <TR  class= common>
          <TD  class= title5>   管理机构 </TD>
          <TD  class= input5>   <Input class="common wid" name=Station   id=Station 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeList('Station',[this]);" 
          onDblClick="return showCodeList('Station',[this]);" 
          onKeyUp="return showCodeListKey('Station',[this]);">	</TD>
						<Input type=hidden name=Date>
          <TD  class= title5>   还垫标记 </TD>
          <TD  class= input5>   <Input class="common wid" name=TRFlag id=TRFlag CodeData="0|^0|未还垫^1|已还垫" 
           style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="return showCodeListEx('TRFlag',[this],[0]);" 
          onDblClick="return showCodeListEx('TRFlag',[this],[0]);"
           onKeyUp="return showCodeListKeyEx('TRFlag',[this],[0]);"> </TD>
        </TR>
      </table> 
      </div></Div>
    <!--<INPUT VALUE="未还垫清单打印" class= cssButton TYPE=button onClick="PrintQD();">
		<INPUT VALUE="查    询" class= cssButton TYPE=button onClick="QueryAutoDJ();">-->
        <a href="javascript:void(0);" class="button"onClick="PrintQD();">未还垫清单打印</a>
       <a href="javascript:void(0);" class="button"onClick="QueryAutoDJ();">查   询</a>

     
      <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLJAPay1);">
    		</td>
    		<td class= titleImg>
    			 自动垫交的详细信息
    		</td>
    	</tr>
    </table>
  	<Div  id= "divLJAPay1" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanPolGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        <div align="center">
    	<INPUT VALUE="首  页" class= cssButton90 TYPE=button onClick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class= cssButton91 TYPE=button onClick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class= cssButton92 TYPE=button onClick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class= cssButton93 TYPE=button onClick="turnPage.lastPage();">
      	</div>				
  	</div>
    </div>
        <input type=hidden id="fmtransact" name="fmtransact">
        
        <br/>
		<!--<INPUT VALUE="打印自动垫交通知书" class= cssButton TYPE=button onClick="PrintAutoDJ();"> -->
		 <a href="javascript:void(0);" class="button"onClick="PrintAutoDJ();">打印自动垫交通知书</a>
         <br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html> 
