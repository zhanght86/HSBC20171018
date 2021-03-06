<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claimgrp.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
//=============================================================BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
	  String tClmNo = request.getParameter("claimNo");	//赔案号
	  String tBnfKind = request.getParameter("BnfKind"); //受益类型
//=============================================================END
%>
    <title>受益人分配</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/Calendar/Calendar.js"></SCRIPT>
    <script src="./LLBnf.js"></script>
    <%@include file="LLBnfInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm target=fraSubmit method=post>
<!--赔案保单列表-->
<br>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBalanceGrid);"></TD>
            <TD class= titleImg> 赔案保单信息 </TD>
        </TR>
    </Table>
    <Div  id= "divLLBalanceGrid" style= "display: ''">
        <Table  class= common>
            <TR  class= common>
                <TD text-align: left colSpan=1><span id="spanLLBalanceGrid" ></span> </TD>
            </TR>
        </Table>
        <!--table>
            <tr>
                <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
            </tr>
        </table-->
    </Div>
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBalanceForm);"></TD>
            <TD class= titleImg> 赔案保单详细信息 </TD>
        </TR>
    </Table>
    <Div  id= "divLLBalanceForm" style= "display: ''">
         <TABLE class=common>
             <tr class=common>
                 <td class=title> 赔案号 </td>
                 <td class= input><Input class= "readonly" readonly name="ClmNo2"></td>
                 <td class=title> 保单号 </td>
                 <td class= input><Input class= "readonly" readonly name="polNo"></td>
                 <td class=title> 赔付金额 </td>
                 <td class= input><Input class= "readonly" readonly name="sumPay"></td>
             </tr>
         </TABLE>
    </div>
<!--理赔受益人账户-->
    <Div  id= "divLLBalance" style= "display: """>
        <hr>
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfGrid);"></TD>
                <TD class= titleImg> 理赔受益人信息 </TD>
            </TR>
         </Table>
         <Div  id= "divLLBnfGrid" style= "display: ''">
             <Table class= common>
                 <TR class= common>
                     <TD text-align: left colSpan=1><span id="spanLLBnfGrid" ></span> </TD>
                 </TR>
             </Table>
             <!--table> 
                 <tr>
                     <td><INPUT class=cssButton VALUE="首  页" TYPE=button onclick="turnPage.firstPage();"></td>
                     <td><INPUT class=cssButton VALUE="上一页" TYPE=button onclick="turnPage.previousPage();"></td>
                     <td><INPUT class=cssButton VALUE="下一页" TYPE=button onclick="turnPage.nextPage();"></td>
                     <td><INPUT class=cssButton VALUE="尾  页" TYPE=button onclick="turnPage.lastPage();"></td>
                 </tr>
             </table-->
         </Div>
         <Table>
              <TR>
                   <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfForm);"></TD>
                   <TD class= titleImg> 理赔受益人详细信息 </TD>
              </TR>
         </Table>
         <Div id= "divLLBnfForm" style= "display: ''">
              <TABLE class=common>
                   <tr class=common>
                        <td class= title> 受益人与被保人关系 </td>
                        <td class= input><Input class=codeno name="relationtoinsured" ondblclick="return showCodeList('relation',[this,relationtoinsuredName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtoinsuredName],[0,1]);"><input class=codename name=relationtoinsuredName readonly=true></TD>
                        <td class= title> 受益人姓名 </td>
                        <td class= input><Input class=common name="cName" onblur="setName();"><font size=1 color='#ff0000'><b>*</b></font></td>
                        <td class= title> 受益人性别 </td>
                        <td class= input><Input class=codeno name="sex" ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName readonly=true></TD>
                   </tr>
                   <tr class=common>
                        <td class= title> 受益人出生日期 </td>
                        <td class= input><Input class="coolDatePicker" dateFormat="short" name="birthday"></td>
                        <td class= title> 受益人证件类型 </td>
                        <td class= input><Input class=codeno name="idtype" ondblclick="return showCodeList('llidtype',[this,idtypeName],[0,1]);" onkeyup="return showCodeListKey('llidtype',[this,idtypeName],[0,1]);"><input class=codename name=idtypeName readonly=true></TD>
                        <td class= title> 受益人证件号码 </td>
                        <td class= input><Input class=common name="idno"></td>
                   </tr>
              </TABLE>
         </div>
         <Table>
              <TR>
                   <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayee);"></TD>
                   <TD class= titleImg> 理赔领款人详细信息 </TD>
              </TR>
         </Table>
         <Div id= "divPayee" style= "display: ''">
              <TABLE class=common>
                   <tr class=common>
                        <td class= title> 领款人与被保人关系 </td>
                        <td class= input><Input class=codeno name="relationtopayee" ondblclick="return showCodeList('relation',[this,relationtopayeeName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtopayeeName],[0,1]);"><input class=codename name=relationtopayeeName readonly=true></TD>
                        <td class= title> 领款人姓名 </td>
                        <td class= input><Input class=common name="payeename"><font size=1 color='#ff0000'><b>*</b></font></td>
                        <td class= title> 领款人性别 </td>
                        <td class= input><Input class=codeno name="payeesex" ondblclick="return showCodeList('sex',[this,payeesexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,payeesexName],[0,1]);"><input class=codename name=payeesexName readonly=true></TD>
                   </tr>
                   <tr class=common>
                        <td class= title> 领款人出生日期 </td>
                        <td class= input><Input class="coolDatePicker" dateFormat="short" name="payeebirthday"></td>
                        <td class= title> 领款人证件类型 </td>
                        <td class= input><Input class=codeno name="payeeidtype" ondblclick="return showCodeList('llidtype',[this,payeeidtypeName],[0,1]);" onkeyup="return showCodeListKey('llidtype',[this,payeeidtypeName],[0,1]);"><input class=codename name=payeeidtypeName readonly=true></TD>
                        <td class= title> 领款人证件号码 </td>
                        <td class= input><Input class=common name="payeeidno"></td>
                   </tr>
              </TABLE>
        </div>
         <Table>
              <TR>
                   <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayee);"></TD>
                   <TD class= titleImg> 分配详细信息 </TD>
              </TR>
         </Table>
         <Div id= "divPayee" style= "display: ''">
              <TABLE class=common>
                   <tr class=common>
                        <td class= title> 剩余比例(%)</td>
                        <td class= input><Input class=readonly readonly name="RemainLot"></td>
                        <td class= title> 受益比例(%)</td>
                        <td class= input><Input class=common name="bnflot"></td>
                        <td class= title> 受益金额</td>
                        <td class= input><Input class=readonly readonly name="getmoney"></td>
                   </tr>
                   <tr class=common>
                        <td class= title> 支付方式</td>
                        <td class= input> <Input class=codeno name="CasePayMode" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);"><input class=codename name=CasePayModeName readonly=true></TD>
                        <td class= title> 银行编码</td>
                        <td class= input> <Input class=codeno name="BankCode" ondblclick="return showCodeList('bank',[this,BankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCodeName],[0,1]);"><input class=codename name=BankCodeName readonly=true></TD>
                        <td class= title> 银行帐号</td>
                        <td class= input> <Input class=common name="BankAccNo" ></TD>
                   </tr>
                   <tr class=common>
                        <td class= title> 银行帐户名</td>
                        <td class= input> <Input class=common name="AccName" ></TD>
                        <td class= title> </td>
                        <td class= input> </TD>
                        <td class= title> </td>
                        <td class= input> </TD>
                  </tr>
              </TABLE>
        </div>
        <br>
        <table>
            <tr>
                <td><Input class=cssButton name="addButton" value="添  加" type=button onclick="addClick();"></td>
                <td><Input class=cssButton name="updateButton" value="修  改" type=button onclick="updateClick();"></td>
                <td><Input class=cssButton name="deleteButton" value="删  除" type=button onclick="deleteClick();"></td>
                <td><Input class=cssButton name="saveButton" value="保  存" type=button onclick="saveClick();"></td>
            </tr>
        </table>
    </div>
    <hr>
    <table>
        <tr>
            <td><Input class=cssButton name="goBack" value="返  回" type=button onclick="top.close();"></td>
        </tr>
    </table>    
    <%
    //******************
    //保存数据的隐藏表单
    //******************
    %>
    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    
    <Input type=hidden id="GrpContNo" name="GrpContNo"><!--集体合同号码-->
    <Input type=hidden id="GrpPolNo" name="GrpPolNo"><!--集体保单险种号码-->
    <Input type=hidden id="ContNo" name="ContNo"><!--合同号码-->
    <Input type=hidden id="BnfKind" name="BnfKind"><!--受益类型B为预付受益人分配-->
    <Input type=hidden id="BatNo" name="BatNo"><!--批次号码-->
    
    <!--待删除内容-->
    <Input type=hidden id="bnfno" name="bnfno"><!--受益人序号-->
    <Input type=hidden id="insuredno" name="insuredno" value="0" ><!--被保人号码-->
    <Input type=hidden id="bnftype" name="bnftype" value="0" ><!--受益人类别-->
    <Input type=hidden id="bnfgrade" name="bnfgrade" value="0" ><!--受益人级别-->
    <Input type=hidden id="customerNo" name="customerNo" value="0"><!--受益人号码-->
    <Input type=hidden id="payeeNo" name="payeeNo" value="0"><!--领款人号码-->
    
    <span id="spanCode"  style="display: 'none'; position:absolute; slategray"></span>
</Form>
</body>
</html>
