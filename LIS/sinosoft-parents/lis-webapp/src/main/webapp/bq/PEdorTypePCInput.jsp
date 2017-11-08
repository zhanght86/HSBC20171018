<html>
<%
//程序名称：
//程序功能：交费信息变更
//创建日期：2003-01-14 16:49:22
//创建人  ：Dingzhong
//更新记录：  更新人    更新日期     更新原因/内容
//                      Lihs            2005-5-8 10:07上午  界面更新及后台连接开发
%>
<%@page contentType="text/html;charset=GBK" %>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%

%>

<head >
  <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <script src="../common/javascript/VerifyInput.js"></script>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdorTypePC.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypePCInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypePCSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title > 保全受理号</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name="EdorAcceptNo" id=EdorAcceptNo>
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly>
      </TD>

      <TD class = title > 保单号 </TD>
      <TD class = input >
        <input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
    <TR class=common>
        <TD class =title>柜面受理日期</TD>
        <TD class = input>
            <Input class= "coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
        <TD class =title>生效日期</TD>
        <TD class = input>
            <Input class= "coolDatePicker" readonly name=EdorValiDate onClick="laydate({elem: '#EdorValiDate'});" id="EdorValiDate"><span class="icon"><a onClick="laydate({elem: '#EdorValiDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
        <TD class = title></TD>
        <TD class = input></TD>
    </TR>
  </TABLE>
  </div>
  <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
      </td>
      <td class= titleImg>
        客户信息
      </td>
    </tr>
   </table>
  <Div  id= "divPolGrid" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanPolGrid"></span></td>
            </tr>
        </table>
    </div>


    <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
      </td>
      <td class= titleImg>
        原缴费信息
      </td>
    </tr>
   </table>

    <Div  id= "divLCPol" class=maxbox1 style= "display: ''">

   <table  class= common>

       <TR>
          <TD class = title>
             投保人客户号
          </TD>
          <TD class= input>
            <Input class="readonly wid" readonly  name=AppntNo id=AppntNo >
          </TD>

         <TD class= title>
            投保人姓名
         </TD>
             <TD class= input>
                <Input class="readonly wid" readonly  name=AppntName id=AppntName >
             </TD>
              <TD class= title>
                交费形式
                 </TD>
                 <TD class= input>
                 <Input class="readonly wid" readonly  name= PayMode id=PayMode>
                 </TD>
                </TR>

    <TR style= "display: none">
         <TD class= title>
          总保费
         </TD>
         <TD class= input>
             <Input class="readonly wid" readonly  name=Prem id=Prem >
         </TD>

         <TD class= title>
         总保额
         </TD>
         <TD class= input>
             <Input class="readonly wid" readonly  name=Amnt id=Amnt >
         </TD>
         <TD class= title>
              </TD>
              <TD  class= input>
                <Input class = "readOnly" >
              </TD>
    </TR>
    <TR>
         <TD class= title>
             银行编码
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=BankCodeS id=BankCodeS >
         </TD>
         <TD class= title>
             银行帐号
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=BankAccNoS id=BankAccNoS >
         </TD>
         <TD class= title>
             银行账户名
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=AccNameS  id=AccNameS>
         </TD>
     </TR>
    </Table>
    </Div>

    <table>
        <tr>
          <td class=common>
            <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBonusGetMode);">
          </td>
          <td class= titleImg>
            新缴费信息
          </td>
        </tr>
   </table>

  <DIV id= "divBonusGetMode" class=maxbox1 style= "display: ''">
      <table class= common>
          <TR>
              <TD  class= title>
                交费形式
              </TD>
              <TD  class= input>
                <Input class="codeno" name=PayLocation id=PayLocation style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="交费形式|NotNull&Code:PayLocation" ondblclick="showCodeList('paylocation',[this, PayLocationName], [0, 1]);" onkeyup="showCodeListKey('paylocation', [this, PayLocationName], [0, 1]);"><input class=codename name=PayLocationName id=PayLocationName readonly>
              </TD>
              <td class = title></td>
              <td class = input></td>
              <td class = title></td>
              <td class = input></td>

          </TR>
      </table>
  </Div>

  <div id= "divBank" style= "display: none">
      <table class= common>
          <TR>
             <TD class= title>
                 开户银行
             </TD>
             <TD class= input>
                <Input class="codeno" name=BankCode id=BankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('Bank',[this,BankCodeName],[0,1])" onkeyup="showCodeListKey('Bank',[this,BankCodeName],[0,1])"><input class=codename name=BankCodeName id=BankCodeName readonly>
             </TD>
              <TD class= title>银行帐号</TD>
          <TD class=input>
                    <Input type="text" class="coolConfirmBox" name="BankAccNo" id=BankAccNo>
                    </TD>
             <TD class= title>
                 户 名
             </TD>
             <TD class= input>
                <Input  class="common wid" name=AccName id=AccName >
             </TD>
            </TR>
        </table>
    </div>
    <br>
<Div id= "divEdorquery" style="display: ''">
     <Input class= cssButton type=Button value=" 保 存 " onclick="edorTypePCSave()">
     <Input type=Button class= cssButton value=" 返 回 " onclick="returnParent()">
     <Input class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">
</Div>


     <input type=hidden id="fmtransact" name="fmtransact">
     <input type=hidden id="ContType" name="ContType">
     <input type=hidden id="EdorNo" name="EdorNo">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
