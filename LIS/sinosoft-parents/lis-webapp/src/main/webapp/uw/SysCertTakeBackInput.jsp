<html>
<% 
//清空缓存
//response.setHeader("Pragma","No-cache"); 
//response.setHeader("Cache-Control","no-cache"); 
//response.setDateHeader("Expires", 0); 
%>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
 
<%
//程序名称：
//程序功能：
//创建日期：2002-08-07
//创建人  ：周平
//更新记录：  更新人    更新日期     更新原因/内容
%>
<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
	<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="SysCertTakeBackInput.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="SysCertTakeBackInit.jsp"%>
  <script src="../common/javascript/MultiCom.js"></script>
</head>
<body  onload="initForm()" >
  <form action="./SysCertTakeBackSave.jsp" method=post name=fm id=fm target="fraSubmit">
  
    
  
    <!-- 单证类型 -->
    <div class="maxbox1">
    <table class="common">
      <tr class="common">
        <td class="title5">单证编码</td>
        <td class="input5"><input style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" class="code" name="CertifyCode" id="CertifyCode" CodeData="0|^8888|承保体检通知书^9999|承保核保通知书^1113|生调通知书^9996|业务员通知书^9986|客户合并通知书^0000|拒保通知书^0006|延期通知书^0075|团单核保要求通知书^0076|团单核保结论通知书^0081|修改事项和索要材料说明通知书^0082|承保特别约定通知书^0083|承保加费通知书^0084|退保附加险或主险部分退保通知书^0085|承保限额通知书^0086|拒保延期附加险通知书^0087|承保加退费通知书^0088|分保成功通知书^0089|承保核保问卷通知书^2000|保全体检通知书^2001|保全核保加费^2002|保全核保特别约定^2003|保全核保限额^2004|保全核保拒保^2005|保全核保延期^2006|保全核保修改事项索要材料^2007|保全核保拒保延期附加险^2008|保全核保不同意^2009|保全核保问卷^2010|保全生调^2011|保全核保通知书^2012|保全补充资料通知书^4403|理赔体检通知书^4481|理赔修改事项和索要材料通知书^4489|理赔核保问卷通知书^4490|理赔核保通知书^7775|核保通知书^7009|续保二核体检通知书^7012|续保二核生调通知书^7006|续保二核核保通知书^4499|补充告知问卷" ondblclick="return showCodeListEx('SysCertCode', [this],null,null,null,null,1)" onMouseDown="return showCodeListEx('SysCertCode', [this],null,null,null,null,1)" onkeyup="return showCodeListKeyEx('SysCertCode', [this],null,null,null,null,1)"></td>
        <td class="title5"></td>
        <td class="input5"></td>
        </tr>
        </table>
        </div>
    <!-- 回收的信息 -->
    <div style="width:120">
      <table class="common">
        <tr class="common">
          <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divTakeBackInfo);"></td>
          <td class="titleImg">回收信息</td></tr></table></div>

    <div id="divTakeBackInfo">
      <!-- 单证号码和经办日期 -->
      <div class="maxbox">
      <table class="common"> 
        <tr class="common">
          <td class="title5" width="25%">单证号码</td>
          <td class="input5" width="25%">
          	<input class="wid" class="common" name="CertifyNo" id="CertifyNo"  verify="单证号码|NOTNULL" onchange="query();">
          <td class="title5" width="25%">有效日期</td>
          <td class="input5" width="25%"><!--<input class="multiDatePicker" readonly name="ValidDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#ValidDate'});" verify="有效开始日期|DATE" dateFormat="short" name=ValidDate id="ValidDate"><span class="icon"><a onClick="laydate({elem: '#ValidDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">发放者</td>
          <td class="input5"><input class="wid" class="common" name="SendOutCom" id="SendOutCom" verify="发放者|NOTNULL"></td>
 
          <td class="title5">接收者</td>
          <td class="input5"><input class="wid" class="common" name="ReceiveCom" id="ReceiveCom" verify="接收者|NOTNULL"></td></tr>

        <tr class="common">
          <td class="title5">经办人</td>
          <td class="input5"><input class="wid" class="common" readonly name="Handler" id="Handler"></td>

          <td class="title5">经办日期</td>
          <td class="input5"><!--<input class="multiDatePicker" readonly name="HandleDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#HandleDate'});" verify="有效开始日期|DATE" dateFormat="short" name=HandleDate id="HandleDate"><span class="icon"><a onClick="laydate({elem: '#HandleDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
        
        <tr class="common">
          <td class="title5">操作员</td>
          <td class="input5"><input class="wid" class="readonly" readonly id="Operator" name="Operator"></td>
        
          <td class="title5">入机日期</td>
          <td class="input5"><!--<input class="multiDatePicker" readonly name="MakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate id="MakeDate"><span class="icon"><a onClick="laydate({elem: '#MakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>
          
        
    
        <tr class="common">
          <td class="title5">回收操作员</td>
          <td class="input5"><input class="wid" class="readonly" readonly id="TakeBackOperator" name="TakeBackOperator"></td>
        
          <td class="title5">回收日期</td>
          <td class="input5"><!--<input class="multiDatePicker" dateFormat="short" name="TakeBackDate" verify="回收日期|DATE&NOTNULL">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackDate'});" verify="回收日期|DATE&NOTNULL" dateFormat="short" name=TakeBackDate id="TakeBackDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td></tr>

        <tr class="common">
          <td class="title5">发放批次号</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="SendNo" id="SendNo"></td>
        
          <td class="title5">回收批次号</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackNo" id="TakeBackNo"></td></tr>
        
        <tr class="common">
          <td class="title5">回收操作日期</td>
          <td class="input5"><!--<input class="readonly" readonly name="TakeBackMakeDate">-->
          <Input class="coolDatePicker" onClick="laydate({elem: '#TakeBackMakeDate'});" verify="回收日期|DATE&NOTNULL" dateFormat="short" name=TakeBackMakeDate id="TakeBackMakeDate"><span class="icon"><a onClick="laydate({elem: '#TakeBackMakeDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </td>
          
          <td class="title5">回收操作时间</td>
          <td class="input5"><input class="wid" class="readonly" readonly name="TakeBackMakeTime" id="TakeBackMakeTime"></td></tr>
        
       <input type=hidden id="ContNo" name="ContNo">
  	   <input type=hidden id="MissionID" name="MissionID">
  	   <input type=hidden id="SubMissionID" name="SubMissionID">
  	   <input type=hidden id="ActivityID" name="ActivityID">
  	   <input type=hidden id="EdorNo" name="EdorNo">
  	   <input type=hidden id="CodeType" name="CodeType">
      </table>
    </div></div>

	<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
    
    <input type="hidden" id="hideOperation" name="hideOperation">
    <input type="hidden" id="sql_where" name="sql_where">
    <table class="common">
    	<tr class="common">
    		<td class="input"><!--<input class="cssButton" type="button" value="回  收" onclick="submitForm()" >
            <input class="cssButton" type="button" value="查  询" onclick="queryClick()" >-->
            <a href="javascript:void(0);" class="button" onClick="submitForm();">回    收</a>
            <!--<a href="javascript:void(0);" class="button" onClick="queryClick();">查    询</a>-->
    		</td></tr>
    
    </table>
    
  	
  </form>
</body>
</html>
