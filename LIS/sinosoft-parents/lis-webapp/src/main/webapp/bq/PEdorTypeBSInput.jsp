<html> 
<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst
 * @version  : 1.00
 * @date     : 2007-06-19
 * @direction: 补签名信息输入界面
 ******************************************************************************/
 %>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>


<head >
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT> 
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeBS.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeBSInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="PEdorTypeBSSubmit.jsp" method=post name=fm id=fm target="fraSubmit">    
  	        <!-- 保全受理通用保单信息 -->
		<div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">保全受理号</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">批改类型</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">保单号</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo></td>
            </tr>
            <tr class="common">
                <td class="title">柜面受理日期</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">生效日期</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorValiDate" readonly onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
		</div>
  <table >
   	<tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divEdorGrid);">
      </td>
      <td class= titleImg>
        既往保单效力确认批改信息
      </td>
   	</tr>
   </table>
  <Div  id= "divEdorGrid">
        <table  class= common>
        	<tr  class= common>
          		<td text-align: left colSpan=1>
        			<span id="spanEdorGrid" >
        			</span> 
        	  	</td>
        	</tr>
        </table>
  </Div>
     <Div  id= "divButton" style= "display: ''" align = center>
      <INPUT VALUE="首页" class=cssButton90 TYPE=button onclick="turnPage.firstPage();"> 
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onclick="turnPage.previousPage();"> 					
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onclick="turnPage.nextPage();"> 
      <INPUT VALUE="尾页" class=cssButton93 TYPE=button onclick="turnPage.lastPage();">
  </Div>	
<Div id= "divImageQuery" style="display: ''">
<table class =common>
     	     <Input type=Button class = cssButton value=" 既往效力确认影象件查询 " name=beSafe onclick="QueryPhoto()">
     	     <Input type=Button class = cssButton value=" 承保影像查询 " name=beSafe onclick="ImageQuery()">
</table>
</Div>	 
        
     <table class="common">
      <tr class=common>
          <td class=title>申请原因</td>
          <td class=input><Input class="codeno" name=AppReason id=AppReason verify="申请原因|NotNull&Code:appreasoncode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('appreasoncode',[this,AppReasonCodeName],[0,1]);" onkeyup="return showCodeListKey('appreasoncode',[this,AppReasonCodeName],[0,1]);"><input class=codename name=AppReasonCodeName id=AppReasonCodeName readonly></td>
          <td class="title"><Div id="divApproveMofiyReasonTitle" style="display:none"> 未亲笔签名原因 </Div></td>
          <td class="input"><Div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" name="UnValiReason" id=UnValiReason verify="修改原因|Code:nnvalireasoncode" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('nnvalireasoncode',[this,ReValiReasonName],[0,1])" onkeyup="return showCodeListKey('nnvalireasoncode',[this,ReValiReasonName],[0,1])"><input class="CodeName" name="ReValiReasonName" id=ReValiReasonName readonly></Div></td>
          <td class="title">&nbsp;</td>
          <td class="input">&nbsp;</td>
      </tr>
     </table>
<Div  id= "divRemarkInfo" style= "display: none">
     <table>
     	<tr>
      <td class="common">
        <IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divWordsRemakeByInfo);">
      </td>
      <td class= titleImg>
        备注批注信息
      </td>
   </tr>
   </table>
    <Div  id= "divWordsRemakeByInfo" class=maxbox1 style= "display: ''">
			  <table class= common>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea name="Remark" id=Remark verify="备注内容|len<1000" verifyorder="1" cols="80" rows="3" class="common" ></textarea></TD>
			    </TR>
			  </table>
    </Div>
</Div>

  <table>
     	<tr>
      <td class="common">
        <IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSignCertify);">
      </td>
      <td class= titleImg>
        客户签名单证
      </td>
   </tr>
   </table>
    <Div  id= "divSignCertify" class=maxbox1 style= "display: ''">
    <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=chk01 TYPE=checkbox dischecked>投保单
               <input class=title name=chk02 TYPE=checkbox dischecked>健康告知书
               <input class=title name=chk03 TYPE=checkbox dischecked>问题件
               <input class=title name=chk04 TYPE=checkbox dischecked>回执
               <input class=title name=chk05 TYPE=checkbox dischecked>投保提示
               <input class=title name=chk06 TYPE=checkbox dischecked>婴儿问卷
               <input class=title name=chk07 TYPE=checkbox dischecked>财务/健康等调查问卷
               <input class=title name=chk08 TYPE=checkbox dischecked>首期保险费自动转账授权书
              </TD>
             </tr>
      </table>  
    </Div>  
    
   <table>
     	<tr>
      <td class="common">
        <IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divSignObject);">
      </td>
      <td class= titleImg>
        效力确认对象
      </td>
   </tr>
   </table>
    <Div  id= "divSignObject" class=maxbox1 style= "display: ''">
    <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=chk11 TYPE=checkbox dischecked>投保人
               <input class=title name=chk12 TYPE=checkbox dischecked>被保险人
               <input class=title name=chk13 TYPE=checkbox dischecked>被保险人的法定监护人             </TD>
             </tr>
      </table>  
    </Div>  
<Div id= "divEdorquery" style="display: ''">
<table class =common>
     	     <Input type=Button class = cssButton value=" 保 存 " name=beSafe onclick="edorTypePSSubmit()">
     	     <Input type=Button class = cssButton value=" 返 回 " onclick="returnParent()">
</table>
</Div>
  <input type="hidden" name="AppObj">
  <input type=hidden id="fmtransact" name="fmtransact">
  <input type=hidden id="ContType" name="ContType">
  <input type=hidden name="EdorNo">
  <input type=hidden id="InsuredNo" name="InsuredNo">  
  <input type=hidden name="PolNo">
  <input type=hidden name="SignDate">
  <input type=hidden name="Flag"><!--如果是明细查询调用，则为"1"-->

  <input type=hidden name=chkS1>
  <input type=hidden name=chkS2>
  <input type=hidden name=chkS3>
  <input type=hidden name=chkS4>
  <input type=hidden name=chkS5>
  <input type=hidden name=chkS6>
  <input type=hidden name=chkS7>
  <input type=hidden name=chkS8>
  
  <input type=hidden name=chkO1>
  <input type=hidden name=chkO2>
  <input type=hidden name=chkO3>
<Br /><Br /><Br /><Br />
</form>
<span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
	var splFlag = "<%=request.getParameter("splflag")%>";
	
</script>
</html>
