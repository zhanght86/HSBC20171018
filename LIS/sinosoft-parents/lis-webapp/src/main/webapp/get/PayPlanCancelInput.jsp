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
  var comcode = "<%=tGI1.ComCode%>"; //记录登陆机构
</script>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <!--<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>-->
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="PayPlanCancel.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="PayPlanCancelInit.jsp"%>
  <title>催付查询 </title>
</head>

<body  onload="initForm();">
  <form action="./PayPlanCancelSubmit.jsp" method=post name=fm id="fm" target="fraSubmit" >    
   
  <!-- 保单信息部分 -->
  <table class= common border=0 width=100%>
    <tr>
    <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,cuifu);"></td>
      <td class= titleImg align= center> 请输入催付查询条件：</td>
    </tr>
  </table>   
  <Div  id= "cuifu" style= "display: ''" class="maxbox1" >
  <table  class= common align=center>
    <TR  class= common>
     <TD  class= title5>
        团体保单号
      </TD>
      <TD  class= input5>
        <Input class=wid name=GrpContNo id=GrpContNo >
      </TD>
      <TD  class= title5>
        领取日期
      </TD>
      <TD  class= input5>
        <!--<Input class= "coolDatePicker" dateFormat="short" name=GetDate >-->
        <Input  class="coolDatePicker" class="laydate-icon" onClick="laydate({elem: '#GetDate'});" 	verify="有效开始日期|DATE" dateFormat="short" name=GetDate 	id="GetDate"><span class="icon"><a onClick="laydate({elem: 	'#GetDate'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
      </TD>

    </TR>
    
        
  </table>     
  </div>

<!--<INPUT class="cssButton" VALUE=" 查  询 " TYPE=button onclick="easyQueryClick();">-->
<a href="javascript:void(0);" class="button" onClick="easyQueryClick();">查    询</a>

  
  <Div  id= "divLCGet" style= "display: none">
    <table>
      <tr>
        <td class=common>
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGet);">
        </td>
        <td class= titleImg>
          催付信息
        </td>
      </tr>
    </table>
  
    <table  class= common>
      <tr  class= common>
        <td text-align: left colSpan=1>
          <span id="spanLJSGetGrid" > </span> 
        </td>
      </tr>	
    </table>
    <br>
  <Div id= "divLCGetButton" style= "display: ''" align="center">
    	
        <INPUT VALUE="首  页" class="cssButton90" type="button" onclick="turnPage.firstPage();"> 
				<INPUT VALUE="上一页" class="cssButton91" type="button" onclick="turnPage.previousPage();"> 					
				<INPUT VALUE="下一页" class="cssButton92" type="button" onclick="turnPage.nextPage();"> 
				<INPUT VALUE="尾  页" class="cssButton93" type="button" onclick="turnPage.lastPage();">  			

  </div> 
</div>  
  <Div  id= "divCancelCommit" style= "display: none">
    <table>
    <tr>
      <td>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divSpeccontent);">
      </td>
      <td class= titleImg>
          请输入撤消原因：
      </td>
    </tr>
  </table>
      <TR  class= common>
        <TD  class= title>
          <textarea name="CancelReason" cols="120" rows="3" class="common" >
        </textarea></TD>
        <td  class= title>
          <INPUT class="cssButton"  VALUE="申请撤销" TYPE=button onclick="CancelCommit();">     			
			</td>
      </TR>
    </table> 



  </div> 
                                                                                  
  <input type=hidden id="fmtransact" name="fmtransact">
  <input type=hidden id="OutGetNoticeNo" name="OutGetNoticeNo">

  </form>
                                                                                                                                                                    
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>    
</body>
</html>
