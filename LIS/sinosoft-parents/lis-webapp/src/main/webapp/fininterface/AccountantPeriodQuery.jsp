<html>
<%
//程序名称：会计期间管理
//程序功能：
//创建日期：2008-8-5
//创建人  ：范昕
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head >
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="AccountantPeriodQuery.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="AccountantPeriodQueryInit.jsp"%>
</head>

<body  onload="initForm();" >
<form action="./AccountantPeriodQuerySave.jsp" method=post name=fm id=fm target="fraSubmit">
		<%@include file="../common/jsp/InputButton.jsp"%>
  	<table>
    <tr class=common>
    <td class=common>
    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccountantPeriodQuery);">
    </IMG>
    <td class=titleImg>
      查询条件
      </td>
    </tr>
   
  </table>
   <Div id= "divAccountantPeriodQuery" style= "display: ''">
		 <div class="maxbox">
   		<Table class= common>
		<TR  class= common>
			<TD  class= title5>年度</TD>
			<TD  class= input5><Input class="wid" class=common name=Year id=Year elementtype=nacessary verify="年度|NOTNULL&INT&len=4" >(例:2008)</TD>
			<TD  class= title5>月度</TD>
			<TD  class= input5><Input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class=code name= Month id= Month verify="月度止期|NOTNULL"   CodeData="0|^01|1月^02|2月^03|3月^04|4月^05|5月^06|6月^07|7月^08|8月^09|9月^10|10月^11|11月^12|12月" ondblClick="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('Month',[this],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('Month',[this],[0,1],null,null,null,[1]);" readonly=true>(例:08)</TD>
		</TR>
  		<TR  class= common>
    		<TD class="title5">操作员</TD>
          <TD class="input5">
          <input class="wid" class="readonly" readonly name="Operator" id="Operator"></TD>
          <TD class= title5>
          	状态
        	</TD>
        	<TD class= input5>
          	<Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name= State id= State verify="状态|NOTNULL" CodeData="0|^0|未开启^1|开启" ondblClick="showCodeListEx('CertifyStateList',[this,StateName],[0,1],null,null,null,[1]);" onMouseDown="showCodeListEx('CertifyStateList',[this,StateName],[0,1],null,null,null,[1]);" onkeyup="showCodeListKeyEx('CertifyStateList',[this,StateName],[0,1],null,null,null,[1]);" readonly=true><input class=codename name=StateName id=StateName readonly=true>
         	</TD>
  		</TR>
    </Table>

		<input class="cssButton" type=button value="查  询" onclick="submitForm()">
        <!--<a href="javascript:void(0);" class="button" onClick="submitForm();">查    询</a>--> 
		</Div>
	</div>
	
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAccountantPeriodGrid);">
    		</td>
    		<td class= titleImg>
    			 清单列表
    		</td>
    	</tr>
    </table>
  	<Div  id= "divAccountantPeriodGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  	<td text-align: left colSpan=1>
  					<span id="spanAccountantPeriodGrid" >
  					</span>
  			  	</td>
  			</tr>
    	</table>
       
      <Div  id= "divPage" align=center style= "display: none ">
      <INPUT CLASS="cssButton90" VALUE="首页" TYPE=button onclick="turnPage.firstPage();">
      <INPUT CLASS="cssButton91" VALUE="上一页" TYPE=button onclick="turnPage.previousPage();">
      <INPUT CLASS="cssButton92" VALUE="下一页" TYPE=button onclick="turnPage.nextPage();">
      <INPUT CLASS="cssButton93" VALUE="尾页" TYPE=button onclick="turnPage.lastPage();">
      </Div>
  	</div><!--<a href="javascript:void(0);" class="button" onClick="ReturnData();">返    回</a>--><input class="cssButton" type=button value="返  回" onclick="ReturnData()">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
