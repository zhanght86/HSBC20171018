<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
 GlobalInput tGI1 = new GlobalInput();
 tGI1=(GlobalInput)session.getValue("GI"); //添加页面控件的初始化。
 String ManageCom = tGI1.ManageCom;
%>
<script>
  var manageCom = "<%=tGI1.ManageCom%>"; //记录管理机构
</script>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="PayPlanCancelQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PayPlanCancelQueryInit.jsp"%>
  <title>催付撤消日志查询 </title>
</head>

<body  onload="initForm();">
  <form method=post name=fm id="fm" target="fraSubmit" >    
   
  <!-- 保单信息部分 -->
  <table class= common border=0 width=100%>
    <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLC);"></td>
      <td class= titleImg align= center> 请输入催付撤消日志查询条件：</td>
    </tr>
  </table>
  <Div  id= "divLC" style= "display: ''" class="maxbox1" >
  <table  class= common>
    <TR  class= common>
      <TD  class= title5>
        团单号码
      </TD>
      <TD  class= input5>
        <Input class= wid name=GrpContNo id=GrpContNo >
      </TD>
      <TD  class= title5>
        个人保单号码
      </TD>
      <TD  class= input5>
        <Input class= wid name=ContNo id=ContNo >
      </TD>
    </TR>
    <TR  class= common>

      <TD  class= title5>
        领取日期
      </TD>
      <TD  class= input5>
        <!--<Input class= "coolDatePicker" dateFormat="short" name=GetDate >-->
        <Input  class= "coolDatePicker" onClick="laydate({elem: '#GetDate'});" 	verify="有效开始日期|DATE" dateFormat="short" name=GetDate 	id="GetDate"><span class="icon"><a onClick="laydate({elem: 	'#GetDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
      </TD>
       <TD  class= title5></TD>
       <TD  class= input5></TD>  
          
      
    </TR>
  </table>     
  </div> 
  <!--<INPUT class="cssButton" VALUE=" 查  询 " TYPE=button onclick="easyQueryClick();">-->
  <!--<a?href="javascript:void(0);"?class="button" onclick="easyQueryClick();">查   询</a>-->
  <a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>
  
  
  <Div  id= "divCancelLog" style= "display: none">
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCancelLog);">
        </td>
        <td class= titleImg>
          催付信息
        </td>
      </tr>
    </table>
  
    <table  class= common>
      <tr  class= common>
        <td text-align: left colSpan=1>
          <span id="spanLFGetCancelLogGrid" > </span>
        </td>
      </tr>	
    </table>
   
	<div id="divLFCanButton" align="center">
        <INPUT class = cssButton90 VALUE=" 首 页 " TYPE=button onclick="turnPage.firstPage();"> 
        <INPUT class = cssButton91 VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"> 					
        <INPUT class = cssButton92 VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"> 
        <INPUT class = cssButton93 VALUE=" 尾 页 " TYPE=button onclick="turnPage.lastPage();">	  			
</div>
  </div>
  
  <Div  id= "divCancelContent" style= "display: none">
    <table>
      <TR  class= common>
        <td>
          撤消催付记录原因：
        </td>
      </TR>
      <TR  class= common>
        <TD  class= title>
          <textarea name="CancelReason" cols="120" rows="3" class="common" >
        </textarea></TD>
      </TR>
    </table> 
  </div> 
  
  </form>
                                                                                                                                                                    
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>    
</body>
</html>
