<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-07-24 08:38:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="PayPlanInput.js"></SCRIPT>
  <!--<SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>-->
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PayPlanInit.jsp"%>
</head>

<body  onload="initForm();" >
  <form action="./PayPlanSave.jsp" method=post name=fm id=fm target="fraSubmit">
          <!-- 显示或隐藏PayPlan1的信息 -->
    <table>
      <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayPlan1);">
      </td>
      <td class= titleImg>
        催付计划生成输入条件
      </td>
    	</tr>
    </table>
	
    <Div  id= "divPayPlan1" style= "display: ''" class="maxbox">
    
      <table  class= common>
        <TR class=common>
        <TD class=title5> 业务机构</TD>
        <TD  class= input5>
            <Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="wid"  class= code name=ManageCom value="" ondblclick="showCodeList('station',[this]);" 
            onkeyup="return showCodeListKey('station',[this]);" onMouseDown="showCodeList('station',[this]);">
 
        </TD>
        <!--<TD class=title> 时间范围</TD>
        <td class="input" width="25%"><input class="coolDatePicker" dateFormat="short" id="timeStart" name="timeStart" verify="起始日期|NOTNULL&DATE" ></td>
        -->
        <TD class=title5> 催付至日期</TD>
        		<td class="input5" width="25%"><!--<input class="coolDatePicker"
			dateFormat="short" id="timeEnd" name="timeEnd"
			verify="催付至日期|NOTNULL&DATE">-->
            <Input class="coolDatePicker" class="laydate-icon" onClick="laydate({elem: '#timeEnd'});" 	verify="催付至日期|NOTNULL&DATE" dateFormat="short" name=timeEnd 	id="timeEnd"><span class="icon"><a onClick="laydate({elem: 	'#timeEnd'});"><img src="../common/laydate/skins/default/icon.png" 	/></a></span>
            </td>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            个人合同号码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=ContNo id=ContNo >
          </TD>
          <TD  class= title5>
            被保人客户号码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=InsuredNo id=InsuredNo >
          </TD>
        </TR>
        <TR  class= common>
          <TD  class= title5>
            集体保单号码
          </TD>
          <TD  class= input5>
            <Input class="wid" class= common name=GrpContNo id=GrpContNo >
          </TD>
          <TD  class= title5></TD>
          <TD  class= input5></TD>
          </TR>
          
      </table> 
      </div>
      <!--<Input class= cssButton type=Button value="生成催付" onclick="submitForm()">-->
      <a href="javascript:void(0);" class="button" onClick="submitForm();">生成催付</a>
   <Div id = "divBTquery" style = "display :none">
             <INPUT class= cssButton VALUE="查询" TYPE=button width=10% OnClick="easyQueryClick();">
             </Div>
      <!--生存领取表（列表） -->
    <table>
    	<tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCInsured2);">
            </td>
            <td class= titleImg>
                     应付情况一览
            </td>
    	</tr>
     </table>
    <Div  id= "divLCInsured2" style= "display:none" >
    <table  class= common>
            <tr  class= common>
                    <td text-align: left colSpan=1>
                                    <span id="spanGetGrid" >
                                    </span>
                            </td>
                    </tr>
            </table>
      <center>
      				<INPUT VALUE="首  页" class="cssButton90" type="button" onclick="turnPage.firstPage();"> 
				    	<INPUT VALUE="上一页" class="cssButton91" type="button" onclick="turnPage.previousPage();"> 					
				    	<INPUT VALUE="下一页" class="cssButton92" type="button" onclick="turnPage.nextPage();"> 
				    	<INPUT VALUE="尾  页" class="cssButton93" type="button" onclick="turnPage.lastPage();"></center>
    </div>
   
    <table class = common>
    <tr class = common>
    
    	<td class = input width="2%">
    		共生成&nbsp<Input class= readonly readonly name=getCount >&nbsp条记录。
    	</td>
     	<input type=hidden id="SerialNo" name="SerialNo" id="SerialNo">
     </table>
      
    <!-- 显示或隐藏LJSGet1的信息 -->
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
