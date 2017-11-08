<html>
<%
//程序名称 :AccountantPeriodInput.jsp
//程序功能 :会计期间管理
//创建人 :范昕
//创建日期 :2008-08-04
//
%>

	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
	<!--用户校验类-->
	<%@page import = "com.sinosoft.utility.*"%>
	<%@page import = "com.sinosoft.lis.schema.*"%>
	<%@page import = "com.sinosoft.lis.vschema.*"%>
	<%@page import = "com.sinosoft.lis.pubfun.*"%>
	<%@include file="../common/jsp/UsrCheck.jsp"%>
	<%
  GlobalInput tGI1 = new GlobalInput();
  tGI1=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
  
 	%>
<script>
  var comcode = "<%=tGI1.ComCode%>";
</script>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>  
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src = "AccountantPeriodInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AccountantPeriodInit.jsp"%>
</head>
<body  onload="initForm();initElementtype();">
  <form action="./AccountantPeriodSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
 
 		<table>
    	<tr>
         	 <td class=common><IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAccountantPeriod);"></td>
    		 <td class= titleImg>
        		会计期间管理
       		 </td>   		 
    	</tr>
    </table>
     <Div id= "divAccountantPeriod" style= "display: ''"><div class="maxbox">
   	<Table class= common>
		<TR  class= common>
			<TD  class= title5>会计年度</TD>
			<TD  class= input5><Input class="wid" class=common name=Year id=Year elementtype=nacessary verify="年度|NOTNULL&INT&len=4" >(例:2008)</TD>
			<TD  class= title5>会计月度</TD>
			<TD   class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name= Month id= Month verify="月度止期|NOTNULL"   CodeData="0|^01|1月^02|2月^03|3月^04|4月^05|5月^06|6月^07|7月^08|8月^09|9月^10|10月^11|11月^12|12月" ondblClick="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('Month',[this],[0,1],null,null,null,[1]);" readonly=true>(例:08)</TD>
		</TR>
		 <TR  class= common>   
          		<TD  class= title5 width="25%">月度起期</TD>
          		<TD  class= input5 width="25%"><!--<Input class= "coolDatePicker" dateFormat="short" name=StartDay elementtype=nacessary verify="月度起期|notnull">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#StartDay'});" verify="月度起期|notnull" dateFormat="short" name=StartDay id="StartDay"><span class="icon"><a onClick="laydate({elem: '#StartDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                 </TD>      
          		<TD  class= title5 width="25%">月度止期</TD>
          		<TD  class= input5 width="25%"><!--<Input class= "coolDatePicker" dateFormat="short" name=EndDay elementtype=nacessary verify="月度止期|notnull">-->
                <Input class="coolDatePicker" onClick="laydate({elem: '#EndDay'});" verify="月度止期|notnull" dateFormat="short" name=EndDay id="EndDay"><span class="icon"><a onClick="laydate({elem: '#EndDay'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>  
     	 </TR> 
  		<TR  class= common>
    		<TD class="title5">操作员</TD>
          	<TD class="input5">
          	<input class="wid" class="readonly" readonly name="Operator" id="Operator" readonly=true ></TD>
          	<TD class= title5>
          	状态
        	</TD>
        	<TD class= input5>
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= State id= State verify="状态|NOTNULL"  CodeData="0|^0|未开启^1|开启" ondblClick="showCodeListEx('State',[this,StateName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('State',[this,StateName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('State',[this,StateName],[0,1],null,null,null,[1]);" readonly=true ><input class=codename name=StateName id=StateName readonly=true elementtype=nacessary>
         	</TD>
  		</TR>
   </Table>
   </Div>
   </Div>
   <input type=hidden id="OperateType" name="OperateType">
		<INPUT class=cssButton name="addbutton" VALUE="增  加"  TYPE=button onclick="return submitForm();">
  	<INPUT class=cssButton name="updatebutton" VALUE="修  改"  TYPE=button onclick="return updateClick();">
  	<INPUT class=cssButton name="querybutton" VALUE="查  询"  TYPE=button onclick="return queryClick();">
  	<INPUT class=cssButton name="deletebutton" VALUE="删  除"  TYPE=button onclick="return deleteClick();">
  	<INPUT class=cssButton name="resetbutton" VALUE="重  置"  TYPE=button onclick="return resetAgain();">
    <!--<a href="javascript:void(0);" name="addbutton" class="button" onClick="return submitForm();">增    加</a>
    <a href="javascript:void(0);" name="updatebutton" class="button" onClick="return updateClick();">修    改</a>
    <a href="javascript:void(0);" name="querybutton" class="button" onClick="return queryClick();">查    询</a>
    <a href="javascript:void(0);" name="deletebutton" class="button" onClick="return deleteClick();">删    除</a>
    <a href="javascript:void(0);" name="resetbutton" class="button" onClick="return resetAgain();">重    置</a>-->

</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
