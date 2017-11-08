<%
//程序名称：SysCertTakeBackQueryInput.jsp
//程序功能：
//创建日期：2002-10-14 10:20:49
//创建人  ：kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="./GrpSysCertTakeBackQuery.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="./GrpSysCertTakeBackQueryInit.jsp"%>
<title>系统单证表 </title>
</head>
<body  onload="initForm();" >
  <form action="./SysCertTakeBackQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit">

    <!-- 单证号码和经办日期 -->
    <div class="maxbox" style="margin-top:4px;">
    <table class="common">
    	<tr class="common">
    		<td class="title5" width="25%">单证编码</td>
           <td class="input5">
           	<input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class=codeno name="CertifyCode" id="CertifyCode" CodeData="0|^9994|团体保单" value='9994' ondblclick="return showCodeListEx('SysCertCode', [this,CertifyCodeName],[0,1],null,null,null,1)" onMouseDown="return showCodeListEx('SysCertCode', [this,CertifyCodeName],[0,1],null,null,null,1)" onkeyup="return showCodeListKeyEx('SysCertCode', [this,CertifyCodeName],[0,1],null,null,null,1)"><input class=codename name=CertifyCodeName id=CertifyCodeName readonly=true value='个人及银代保单'>
           </td>
        </tr>
        <tr class="common">
        <td class="title5" width="25%">单证号码</td>
        <td class="input5" width="25%"><input class="wid" class="common" name="CertifyNo" id="CertifyNo"></td>

        <td class="title5" width="25%">有效日期</td>
        <td class="input5" width="25%"><!--<input class="coolDatePicker" dateFormat="short" name="ValidDate">-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#ValidDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ValidDate id="ValidDate"><span class="icon"><a onClick="laydate({elem: '#ValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td></tr>

      <tr class="common">
        <td class="title5">发放机构</td>
        <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom"></td>

        <td class="title5">接收机构</td>
        <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom"></td></tr>

      <tr class="common">
        <td class="title5">经办人</td>
        <td class="input5"><input class="wid" class="common" name="Handler" id="Handler"></td>

        <td class="title5">经办日期</td>
        <td class="input5"><!--<input class="coolDatePicker" dateFormat="short" name="HandleDate">-->
        <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="有效开始日期|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
        </td></tr>
      
    </table>

    </table>
          <!--INPUT VALUE="查询" class="common" TYPE=button onclick="submitForm();return false;"--> 
          <!--<INPUT VALUE="查  询" class="cssButton" TYPE=button onclick="QueryClick();">--> 
          <a href="javascript:void(0);" class="button" onClick="QueryClick();">查    询</a>
         </div> 					
    <table>
    	<tr>
        	<td class=common>
			    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCardPrintGrid);">
    		</td>
    		<td class= titleImg>
    			 单证印刷表结果
    		</td>
    	</tr>
    </table>
  	<Div  id= "divSysCertifyGrid" style= "display: ''">
      	<table  class= common>
       		<tr  class= common>
      	  		<td text-align: left colSpan=1>
  					<span id="spanSysCertifyGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
        
        <!--<div align="left"><INPUT VALUE="返  回" class="cssButton" TYPE=button onclick="returnParent();"> </div>-->
        
       <center> 
      <INPUT VALUE="首  页" class="cssButton90" TYPE="button" onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class="cssButton91" TYPE="button" onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class="cssButton92" TYPE="button" onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾  页" class="cssButton93" TYPE="button" onclick="turnPage.lastPage();"> </center>					

      <input type="hidden" name="sql_where">
  	</div><a href="javascript:void(0);" class="button" onClick="returnParent();">返    回</a>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
