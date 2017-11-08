<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="CertifySearch.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="CertifySearchInit.jsp"%>
  <title>单证查询打印</title>
</head>
<body  onload="initForm();" >
  <form action="./CertifySearchOut.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 单证信息部分 -->
    <table class="common" border=0 width=100%>
      <tr>
	    <td class=common>
            <img  src= "../common/images/butExpand.gif" style= "cursor:hand;" ></img>
		</td>
		<td class="titleImg" align="center">请输入查询条件：</td>
	  </tr>
	</table>
    <div class=maxbox>
    <table class="common"  id="tbInfo" name="tbInfo">
      <tr class="common">
        <td class="title5">单证编码</td>
        <td class="input5">
          <input class="code" name="CertifyCode" id=CertifyCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center"
            ondblclick="return showCodeList('CertifyCode', [this]);" 
            onkeyup="return showCodeListKey('CertifyCode', [this]);"></td> 
        
        <td class="title5"></td>
        <td class="input5"></td>
      </tr>

      <tr class="common">
        <td class="title5">发放者</td>
        <td class="input5"><input class="wid common" name="SendOutCom" id=SendOutCom></td>

        <td class="title5">接收者</td>
        <td class="input5"><input class="wid common" name="ReceiveCom" id=ReceiveCom></td>
      </tr>
        
      <tr class="common">
        <td class="title5">操作员</td>
        <td class="input5"><input class="wid common" name="Operator" id=Operator></td>
        
        <td class="title5">经办人</td>
        <td class="input5"><input class="wid common" name="Handler" id=Handler></td></tr>

      <tr class="common">
        <td class="title5">经办日期（开始）</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" name="HandleDateB" id=HandleDateB></td>
        
        <td class="title5">经办日期（结束）</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" name="HandleDateE" id=HandleDateE></td></tr>

      <tr class="common">
        <td class="title5">入机日期（开始）</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" name="MakeDateB" id=MakeDateB verify="入机日期（开始）|NOTNULL"></td>
        
        <td class="title5">入机日期（结束）</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" name="MakeDateE" id=MakeDateE verify="入机日期（结束）|NOTNULL"></td></tr>

      <tr class="common">
        <td class="title5">失效日期（开始）</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" name="InvalidDateB" id=InvalidDateB></td>
        
        <td class="title5">失效日期（结束）</td>
        <td class="input5"><input class="coolDatePicker wid" dateFormat="short" name="InvalidDateE" id=InvalidDateE></td></tr>
        
      <tr class="common"><td cols=4><br></td></tr>
      
      <tr class="common">
        <td class="title5">清算单号</td>
        <td class="input5"><input class="wid common" name="TakeBackNo" id=TakeBackNo></td>
        
        <td class="title5">数量</td>
        <td class="input5"><input class="wid common" name="SumCount" id=SumCount></td></tr>
        
      <tr class="common">
        <td class="title5">起始号</td>
        <td class="input5"><input class="wid common" name="StartNo" id=StartNo></td>
        
        <td class="title5">终止号</td>
        <td class="input5"><input class="wid common" name="EndNo" id=EndNo></td></tr>
        
      <tr class="common">
        <td class="title5">单证状态</td>
        <td class="input5"><input class="wid common" name="CertifyState" id=CertifyState></td>
        
        <td class="title5">操作标志</td>
        <td class="input5"><input class="wid common" name="OperateFlag" id=OperateFlag></td></tr>

    </table>

    <input value="查询状态" type="button" class="cssButton" onclick="searchState();">
    <input value="查询轨迹" type="button" class="cssButton" onclick="searchTrack();">
    <input value="放入队列" type="button" class="cssButton" onclick="putList();">
    <br>
    <br>
    <input value="打    印" type="button" class="cssButton" onclick="printList();">
    <input value="重    置" type="button" class="cssButton" onclick="initInpBox(); initCardInfo();">
    
    <input type="hidden" name="State">
    </div>
    <table>
      <tr>
      <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCardInfo);"></td>
    	<td class= titleImg>单证信息（单证状态释意 : 0-未用，1-缴销，2-正常作废，3-遗失被盗，4-销毁）</td>
    	</tr>
    </table>
    
  	<div id="divCardInfo" style="display: ;text-align:center">
      <table class="common">
        <tr class="common">
      	  <td style="text-align: left" colSpan=1><span id="spanCardInfo"></span></td></tr>
      </table>
      
      <input VALUE="首  页" TYPE="button" class="cssButton90" onclick="turnPage.firstPage();"> 
      <input VALUE="上一页" TYPE="button" class="cssButton91" onclick="turnPage.previousPage();"> 					
      <input VALUE="下一页" TYPE="button" class="cssButton92" onclick="turnPage.nextPage();"> 
      <input VALUE="尾  页" TYPE="button" class="cssButton93" onclick="turnPage.lastPage();"> 					
  	</div>

	  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  	
  </form>

  <form action="./CertifySearchPrint.jsp" method=post name="fm_print" target="_blank">
    <table>
      <tr>
        <td class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this, divCardListInfo);"></td>
    	<td class= titleImg>打印信息</td></tr>
    </table>
    
  	<div id="divCardListInfo" style="display: ''">
      <table class="common">
        <tr class="common">
      	  <td style="text-align: left" colSpan=1><span id="spanCardListInfo"></span></td></tr>
      </table>
  	</div>
	</form>
	<br /><br /><br /><br />
</body>
</html>
