<html>
<%
//程序名称 :FIOperationLogQuery.jsp
//程序功能 :系统操作日志查询
//创建人 :范昕
//创建日期 :2008-09-24
//
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.db.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
  String link = "";
  FICodeTransDB  tFiCodetransDB = new FICodeTransDB();     
  tFiCodetransDB.setCodeType("application");
  FICodeTransSet tFICodeTransSet = new FICodeTransSet();
  tFICodeTransSet = tFiCodetransDB.query();
  if(tFICodeTransSet.size()>0)
  {
    link = tFICodeTransSet.get(1).getCode();
  }
    
 %>
<script>
  var comcode = "<%=tGI1.ComCode%>";
  var filepath = "<%=link%>";
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>  
<SCRIPT src = "FIOperationLogQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="FIOperationLogQueryinit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="" method=post name=fm id=fm target="fraSubmit">
  
  	<table>
    	<tr>
        	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divLLReport1);"></td>
    		 <td class= titleImg>
        		查询条件
       		 </td>   		 
    	</tr>
    </table>
   <Div id= "divLLReport1" style= "display: ''"><div class="maxbox1">
   <!--<input class="cssButton" type=button value="返  回" onclick="ReturnData()">-->
 <table class= common border=0 width=100%>
	<table  class= common>
        <TR  class= common>
          <TD  class= title5>
            起始时间
          </TD>
          <TD  class= input5>
            <!--<Input class= "coolDatePicker" verify="起始时间|notnull&date" dateFormat="short" name=StartDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="起始时间|notnull&date" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
          <TD  class= title5>
            结束时间
          </TD>
          <TD  class= input5>
            <!--<Input class= "coolDatePicker" verify="结束时间|notnull&date"  dateFormat="short" name=EndDay >-->
            <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="结束时间|notnull&date" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
        </TR> 
      	<TR  class= common>
          <TD  class= title5>
            事件类型
          </TD>
          <TD  class= input5>
	    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=EventClass  ondblclick="showCodeList('EventType',[this,EventClassName],[0,1]);" onMouseDown="showCodeList('EventType',[this,EventClassName],[0,1]);" onkeyup="return showCodeList('EventType',[this,EventClassName],[0,1]);"  readonly=true ><input class=codename name=EventClassName readonly=true ></TD>	          
          </TD>
          <TD  class= title5>
            事件状态
          </TD>
          <TD  class= input5>
	    <Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=EventFlag CodeData="0|^0|成功|M^1|失败|M" ondblclick="showCodeListEx('EventFlag',[this,EventFlagName],[0,1]);" onMouseDown="showCodeListEx('EventFlag',[this,EventFlagName],[0,1]);" onkeyup="return showCodeListEx('EventFlag',[this,EventFlagName],[0,1]);"  readonly=true ><input class=codename name=EventFlagName readonly=true ></TD>
          </TD>
        </TR>      
      </table>
      </div>
      </div>
<!--<input class="cssButton" type=button value="查  询" onclick="OperationLogQuery()"> -->
<a href="javascript:void(0);" class="button" onClick="OperationLogQuery();">查    询</a>
      
      <table  class= common>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick="showPage(this,divFIOperationLogGrid);">
    		</td>
    		<td class= titleImg>
    			 系统事件操作日志查询
    		</td>
    	</tr>
    </table>
<Div  id= "divFIOperationLogGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFIOperationLogGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<!--<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> -->					
  <br>
</Div>
<Div  id= "divFIOperationParameterGrid" style= "display: ''" align=center>
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanFIOperationParameterGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
		<INPUT VALUE="首  页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
		<INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
		<INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
		<INPUT VALUE="尾  页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();"> 					
  <br>
</Div>
    <!--<input class="cssButton" type=button value="导出该日志下载地址" onclick="DownloadAddress()">-->
    <a href="javascript:void(0);" class="button" onClick="DownloadAddress();">导出该日志下载地址</a><br><br>


<Div  id= "Querydiv" style= "display: none" align=left>
	<table class=common>
		 <TR  class= common>
          <TD class= title5>
					  事件号码
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=EventNo id=EventNo readonly = true>
				</TD>
          <TD class= title5>
					  事件类型
				 </TD>
				 <TD class=input5>
				 	 <Input class="wid" class=readonly name=EventType id=EventType readonly = true>
				</TD>
        </TR>
      <TR  class= common>
      <TD  class= title5>
        下载文件链接
      </TD>
      <TD  class= input5>
        <A id=fileUrl name = filepath  href=""></A>
      </TD>           
    </TR>   
 </table> 	  
</Div>
</table>	
   <input type=hidden id="OperateType" name="OperateType">
   <input type=hidden name="EventNo1" VALUE=''>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
