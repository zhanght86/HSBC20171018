<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ProposalQueryInput.jsp
//程序功能：承保查询
//创建日期：2005-06-01 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人    更新日期     更新原因/内容
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<html>
<%
  //客户号
	String tContNo = request.getParameter("ContNo");
	
  GlobalInput tGI = new GlobalInput();
	tGI = (GlobalInput)session.getValue("GI");
	loggerDebug("UWChangeCvalidateInput","operator:"+tGI.Operator);
%>
<script>
	var operator = "<%=tGI.Operator%>";   //记录操作员
	var manageCom = "<%=tGI.ManageCom%>"; //记录登陆机构
    var tContNo = "<%=tContNo%>"; //客户号
    var tQueryFlag = "<%=request.getParameter("QueryFlag")%>"; //客户号
</script>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <link href="../common/css/Project.css" rel=stylesheet type=text/css>
  <link href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <link href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <title>修改保单生效日期</title>

  <script src="../common/javascript/Common.js" ></SCRIPT>
  <script src="../common/javascript/MulLine.js"></SCRIPT>
  <script src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <script src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>

  <script src="UWChangeCvalidate.js"></SCRIPT>
  <script src="../common/javascript/MultiCom.js"></script>
  
  <%@include file="UWChangeCvalidateInit.jsp"%>
  
</head>
<body  onload="initForm();" >
  
  
  <form method="post" name="fm" id="fm" target="fraSubmit" action="./UWChangeCvalidateChk.jsp">
<br>
    <table class="common">
      <tr class="common">
        <td class="title">
          印刷号
        </td>
        <td class="input">
          <input class="common wid" name="ContNo" id="ContNo" readonly >
        </td>
        <td class="title">
          生效日期
        </td>
        <td class="input">
        <!--class="multiDatePicker" -->
          <input class="common" dateFormat="short" name="Cvalidate" id="Cvalidate" onClick="laydate({elem:'#Cvalidate'});"><span class="icon"><a onClick="laydate({elem: '#Cvalidate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td>
        <TD  class= title>是否指定生效日期</TD>
		<TD class= input><Input class= code name=CvalidateConfirm id="CvalidateConfirm" style="background: url(../common/images/guanliyuan-bg.png) no-repeat center right;" CodeData= "0|^Y|指定^N|不指定" onClick="showCodeListEx('CvalidateConfirm',[this],[0]);" ondblClick="showCodeListEx('CvalidateConfirm',[this],[0]);" onKeyUp="showCodeListKeyEx('EnteraccState',[this],[0]);">
      </tr>
    </table>
<hr class="line">
<!--分类保额累计-->
   <input class="cssButton" value="生效期回溯特约" type="button" id="Button1" name="Button1" onClick="specInput();">
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCont);">
	    	</td>
	    	<td class= titleImg>
	    	 保单信息部分 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divCont" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanContGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: none ">
          <input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	
<hr class="line">

<!--险种信息-->
    <table>
	    <tr>
	      <td class=common>
	        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol);">
	    	</td>
	    	<td class= titleImg>
	    	 保单险种信息 
	    	</td>
	    </tr>
	  </table>    
	  <div id= "divPol" style= "display: ''" >
      <table  class= common>
        <tr  class= common>
      	  <td text-align: left colSpan=1>
  				  <span id="spanPolGrid" >
  					</span> 
          </td>
  			</tr>
    	</table>
        <div  id= "divTurnPage" align=center style= "display: none ">
          <input class=cssButton90 VALUE="首  页" TYPE=button onClick="turnPage.firstPage();"> 
          <input class=cssButton91 VALUE="上一页" TYPE=button onClick="turnPage.previousPage();"> 					
          <input class=cssButton92 VALUE="下一页" TYPE=button onClick="turnPage.nextPage();"> 
          <input class=cssButton93 VALUE="尾  页" TYPE=button onClick="turnPage.lastPage();">
        </div>
    </div>	
    <hr class="line">
    <div id = "divChangeResult" style = "display: 'none'">
      	  <table  class= common align=center>
          	<td height="24"  class= title>
            		生效日回溯特约:
          	</TD>
		<tr></tr>
      		<td  class= input><textarea name="CvalidateIdea" id=CvalidateIdea cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
    	 </table>
     </div>


      <input class="cssButton" value="确  认" id="sure" name=sure type="button" onClick="submitForm();">
      <!-- modified by liuyuxiao 2011-05-24 隐去返回，在tab中无用 -->
      <input class="cssButton" value="返  回" type="button" onClick="returnParent();" style= "display: none">
      
    </table>
    </div>
		<!--隐藏域-->
    <div id = "divHidden" style = "display:'none'" >   
    <INPUT  type= "hidden" class= Common id="InsuredNo" name= InsuredNo value= "">
    <INPUT  type= "hidden" class= Common id="ProposalContNo" name= ProposalContNo value= "">
    <INPUT  type= "hidden" class= Common id="CvalidateHide" name= CvalidateHide value= "">
    <INPUT  type= "hidden" class= Common id="Specifyvalidate" name= Specifyvalidate value= "">
    <INPUT  type= "hidden" class= Common id="Flag" name= Flag value= "1">
    <INPUT  type= "hidden" class= Common id="PolApplyDate" name= PolApplyDate value= "">
    <INPUT  type= "hidden" class= Common id="SpecCode" name= SpecCode value= "">
    <INPUT  type= "hidden" class= Common id="SpecType" name= SpecType value= "">
    <INPUT  type= "hidden" class= Common id="SpecReason" name= SpecReason value= "">
    <INPUT  type= "hidden" class= Common id="Operate" name= Operate value= "INSERT">
    <INPUT  type= "hidden" class= Common id="SerialNo" name= SerialNo value= "">
    <INPUT  type= "hidden" class= Common id="DivFlag" name= DivFlag value= "1">
  	</div>

  </form>
<br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>

</body>


</html>
