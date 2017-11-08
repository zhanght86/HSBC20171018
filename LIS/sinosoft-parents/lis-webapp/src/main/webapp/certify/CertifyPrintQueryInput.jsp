<%
//程序名称：CertifyPrintQueryInput.jsp
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
  <SCRIPT src="./CertifyPrintQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="./CertifyPrintQueryInit.jsp"%>
  <title>单证印刷表 </title>
</head>
<body  onload="initForm();" >
  <form action="./CertifyPrintQuerySubmit.jsp" method=post name=fm target="fraSubmit">
      <table class="common">
	    <tr class="common">
		    <td class=common>
                 <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
			</td>
			<td class=titleImg>请输入查询条件：</td>
		</tr>
	  </table>
	  <div class=maxbox>
      <table class="common">
        <tr class="common">
          <td class="title5">印刷批次号</td>
          <td class="input5"><input class="wid common" name="PrtNo"></td>
          
          <td class="title5">单证编码</td>
          <td class="input5"><input class="code" name="CertifyCode" id=CertifyCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"  ondblclick="return showCodeList('CertifyCode', [this]);" onkeyup="return showCodeList('CertifyCode', [this]);"></td>
          
        <tr class="common">
          <td class="title5">最大金额</td>
          <td class="input5"><input class="wid common" name="MaxMoney" id=MaxMoney></td>
          
          <td class="title5">最大日期</td>
          <td class="input5"><input class="wid common" name="MaxDate" id=MaxDate></td></tr>
          
        <tr class="common">
          <td class="title5">单证价格</td>
          <td class="input5"><input class="wid common" name="CertifyPrice" id=CertifyPrice></td>
          
          <td class="title5">管理机构</td>
          <td class="input5"><input class="wid common" name="ManageCom" id=ManageCom></td></tr>
          
        <tr class="common">
          <td class="title5">起始号</td>
          <td class="input5"><input class="wid common" name="StartNo" id=StartNo></td>
          
          <td class="title5">终止号</td>
          <td class="input5"><input class="wid common" name="EndNo" id=EndNo></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">厂商编码</td>
          <td class="input5"><input class="wid common" name=ComCode id=ComCode ></td>
          
          <td class="title5">电话</td>
          <td class="input5"><input class="wid common" name=Phone id=Phone ></td></tr>

        <tr class="common">
          <td class="title5">定单操作员</td>
          <td class="input5"><input class="wid common" name="OperatorInput" id=OperatorInput></td>
          
          <td class="title5">联系人</td>
          <td class="input5"><input class="wid common" name=LinkMan id=LinkMan ></td></tr>
          
        <tr class="common">
          <td class="title5">定单日期</td>
          <td class="input5"><input class="wid common" name="InputDate" id=InputDate></td>

          <td class="title5">定单操作日期</td>
          <td class="input5"><input class="wid common" name=InputMakeDate id=InputMakeDate ></td></tr>
          
        <tr class="common" height=20><td></td></tr>
          
        <tr class="common">
          <td class="title5">提单操作员</td>
          <td class="input5"><input class="wid common" name=OperatorGet id=OperatorGet ></td>
          
          <td class="title5">提单人</td>
          <td class="input5"><input class="wid common" name=GetMan id=GetMan ></td></tr>
          
        <tr class="common">
          <td class="title5">提单日期</td>
          <td class="input5"><input class="wid common" name=GetDate id=GetDate ></td>
          
          <td class="title5">提单操作日期</td>
          <td class="input5"><input class="wid common" name=GetMakeDate id=GetMakeDate ></td></tr>
          
      </table>

    </table>
          <INPUT VALUE="查询" TYPE=button class="cssButton" onclick="submitForm();return false;"> 
          <INPUT VALUE="返回" TYPE=button class="cssButton" onclick="returnParent();"> 
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
  	<Div  id= "divCertifyPrintGrid" style= "display: ;text-align:center">
      	<table  class= common>
       		<tr  class= common>
      	  		<td style="text-align: left" colSpan=1>
  					<span id="spanCertifyPrintGrid" >
  					</span> 
  			  	</td>
  			</tr>
    	</table>
      <INPUT VALUE=" 首页 " TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <INPUT VALUE=" 尾页 " TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>
  	
  	<input type=hidden name="sql_where" id=sql_where>
  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
