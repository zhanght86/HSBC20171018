<html>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<!--用户校验类-->
<%@page import = "com.sinosoft.utility.*"%>
<%@page import = "com.sinosoft.lis.pubfun.*"%>
<%@page import = "com.sinosoft.lis.schema.*"%>
<%@page import = "com.sinosoft.lis.vschema.*"%>
<%@page import = "com.sinosoft.lis.claim.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<head>
<%
//=============================================================BGN
	  GlobalInput tG = new GlobalInput();
	  tG = (GlobalInput)session.getValue("GI");
	  String tClmNo = request.getParameter("claimNo");	//赔案号
	  String tBnfKind = request.getParameter("BnfKind"); //受益类型
	  String tInsuredNo = request.getParameter("InsuredNo"); //被保险人客户号
//=============================================================END
%>
    <title>受益人分配查询</title>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
	<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script src="./LLBnf.js"></script>
    <SCRIPT src="LLClaimPubFun.js"></SCRIPT>
    <%@include file="LLBnfInit.jsp"%>
</head>
<body onload="initForm();">
<form name=fm id=fm target=fraSubmit method=post>
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
                <TD style="text-align: left" colSpan=1><span id="spanLLBalanceGrid" ></span> </TD>
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
    <Div  id= "divLLBalanceForm" style= "display: none">
    <Table>
        <TR>
            <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBalanceForm);"></TD>
            <TD class= titleImg> 赔案保单详细信息 </TD>
        </TR>
    </Table>
	    <div class=maxbox1>
         <TABLE class=common>
             <tr class=common>
                 <td class=title> 立案号 </td>
                 <td class= input><Input class="readonly wid" readonly name="ClmNo2" id=ClmNo2></td>
                 <td class=title> 保单号 </td>
                 <td class= input><Input class="readonly wid" readonly name="polNo" id=polNo></td>
                 <td class=title> 赔付金额 </td>
                 <td class= input><Input class="readonly wid" readonly name="sumPay" id=sumPay></td>
             </tr>
         </TABLE>
		</div>
    </div>
<!--理赔受益人账户-->
    <Div  id= "divLLBalance" style= "display: """>
        <hr class=line>
        <Table>
            <TR>
                <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfGrid);"></TD>
                <TD class= titleImg> 理赔受益人信息 </TD>
            </TR>
         </Table>
         <Div  id= "divLLBnfGrid"  style= "display: ''">
             <Table class= common>
                 <TR class= common>
                     <TD style="text-align: left" colSpan=1><span id="spanLLBnfGrid" ></span> </TD>
                 </TR>
             </Table>
             
         </Div>
         <Div id= "divButtonBnf" style= "display: ''">
             <Table>
                  <TR>
                       <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLLBnfForm);"></TD>
                       <TD class= titleImg> 理赔受益人详细信息 </TD>
                  </TR>
             </Table>
             <Div id= "divLLBnfForm" class=maxbox style= "display: ''">
                  <TABLE class=common>
                       <tr class=common>
                            <td class= title> 受益人与被保人关系 </td>
                            <td class= input><Input class=codeno name="relationtoinsured" id=relationtoinsured style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('relation',[this,relationtoinsuredName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtoinsuredName],[0,1]);"><input class=codename name=relationtoinsuredName id=relationtoinsuredName readonly=true></TD>
                            <td class= title> 受益人姓名 </td>
                            <td class= input><Input class="common wid" name="cName" id=cName ><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> 受益人性别 </td>
                            <td class= input><Input class=codeno name="sex" id=sex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,SexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,SexName],[0,1]);"><input class=codename name=SexName id=SexName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                       </tr>
                       <tr class=common>
                            <td class= title> 受益人出生日期 </td>
                            <td class= input><Input class="coolDatePicker" dateFormat="short" name="birthday" onblur=" CheckDate(fm.birthday); " onClick="laydate({elem: '#birthday'});" id="birthday"><span class="icon"><a onClick="laydate({elem: '#birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> 受益人证件类型 </td>
                            <td class= input><Input class=codeno name="idtype" id=idtype style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,idtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');" onkeyup="return showCodeListKey('llidtype',[this,idtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');"><input class=codename name=idtypeName id=idtypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                            <td class= title> 受益人证件号码 </td>
                            <td class= input><Input class="common wid" name="idno" id=idno elementtype=nacessary verify="受益人证件号码|NOTNULL&LEN<=20"  verifyorder="1" onblur="checkidtype();getBirthdaySexByIDNo(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></td>
                       </tr>
                  </TABLE>
             </div>
             <Table>
                  <TR>
                       <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divBrawer);"></TD>
                       <TD class= titleImg> 理赔领款人详细信息 </TD>
                  </TR>
             </Table>
             <Div id= "divBrawer" class=maxbox1 style= "display: ''">
                  <TABLE class=common>
                       <tr class=common>
                            <td class= title> 领款人与被保人关系 </td>
                            <td class= input><Input class=codeno name="relationtopayee" id=relationtopayee style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('relation',[this,relationtopayeeName],[0,1]);" onkeyup="return showCodeListKey('relation',[this,relationtopayeeName],[0,1]);"><input class=codename name=relationtopayeeName id=relationtopayeeName readonly=true></TD>
                            <td class= title> 领款人姓名 </td>
                            <td class= input><Input class="common wid" name="payeename" id=payeename><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> 领款人性别 </td>
                            <td class= input><Input class=codeno name="payeesex" id=payeesex style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('sex',[this,payeesexName],[0,1]);" onkeyup="return showCodeListKey('sex',[this,payeesexName],[0,1]);"><input class=codename name=payeesexName id=payeesexName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                       </tr>
                       <tr class=common>
                            <td class= title> 领款人出生日期 </td>
                            <td class= input><Input class="coolDatePicker" dateFormat="short" name="payeebirthday" onblur=" CheckDate(fm.payeebirthday); " onClick="laydate({elem: '#payeebirthday'});" id="payeebirthday"><span class="icon"><a onClick="laydate({elem: '#payeebirthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span><font size=1 color='#ff0000'><b>*</b></font></td>
                            <td class= title> 领款人证件类型 </td>
                            <td class= input><Input class=codeno name="payeeidtype" id=payeeidtype style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('idtype',[this,payeeidtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');" onkeyup="return showCodeListKey('llidtype',[this,payeeidtypeName],[0,1],null,'1 and code <> #9#',1,null,'150');"><input class=codename name=payeeidtypeName id=payeeidtypeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                            <td class= title> 领款人证件号码 </td>
                            <td class= input><Input class="common wid" name="payeeidno" id=payeeidno elementtype=nacessary verify="领款人证件号码|NOTNULL&LEN<=20" verifyorder="1" onblur="checkidtype1();getBirthdaySexByIDNo1(this.value);confirmSecondInput(this,'onblur');"><font size=1 color='#ff0000'><b>*</b></font></td>
                       </tr>
                  </TABLE>
            </div>
             <Table>
                  <TR>
                       <TD class=common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPayee);"></TD>
                       <TD class= titleImg> 分配详细信息 </TD>
                  </TR>
             </Table>
             <Div id= "divPayee" class=maxbox1 style= "display: ''">
                  <TABLE class=common>
                       <tr class=common>
                            <td class= title> 剩余比例(%)</td>
                            <td class= input><Input class="readonly wid" readonly name="RemainLot" id=RemainLot></td>
                            <td class= title> 受益比例(%)</td>
                            <td class= input><Input class="common wid" name="bnflot" id=bnflot></td>
                            <td class= title> 受益金额</td>
                            <td class= input><Input class="readonly wid" readonly name="getmoney" id=getmoney></td>
                       </tr>
                       <tr class=common>
                            <td class= title> 支付方式</td>
                            <td class= input> <Input class=codeno name="CasePayMode" id=CasePayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llpaymode',[this,CasePayModeName],[0,1]);" onkeyup="return showCodeListKey('llpaymode',[this,CasePayModeName],[0,1]);"><input class=codename name=CasePayModeName id=CasePayModeName readonly=true><font size=1 color='#ff0000'><b>*</b></font></TD>
                            <td class= title> 银行编码</td>
                            <td class= input> <Input class=codeno name="BankCode" id=BankCode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('bank',[this,BankCodeName],[0,1]);" onkeyup="return showCodeListKey('bank',[this,BankCodeName],[0,1]);"><input class=codename name=BankCodeName id=BankCodeName readonly=true></TD>
                            <td class= title> 银行帐号</td>
                            <!--<td class= input> <Input class=common name="BankAccNo" ></TD>-->
                            <td class= input><Input class="common wid" name="BankAccNo" id=BankAccNo  onblur="confirmSecondInput(this,'onblur');" >
                       </tr>
                       <tr class=common>
                            <td class= title> 银行帐户名</td>
                            <td class= input> <Input class="common wid" name="AccName" id=AccName ></TD>
                            <td class= title> </td>
                            <td class= input> </TD>
                            <td class= title> </td>
                            <td class= input> </TD>
                      </tr>
                  </TABLE>
            </div>
            <table>
                <tr>
                    <td><Input class=cssButton name="addButton" id=addButton value="添  加" type=hidden onclick="addClick();"></td>
                    <td><Input class=cssButton name="updateButton" id=updateButton value="修  改" type=hidden onclick="updateClick();"></td>
                    <td><Input class=cssButton name="deleteButton" id=deleteButton value="删  除" type=hidden onclick="deleteClick();"></td>
                    <td><Input class=cssButton name="saveButton" id=saveButton value="保  存" type=hidden onclick="saveClick();"></td>
					<td><Input class=cssButton name="saveButton" id=saveButton value="全部撤销" type=hidden onclick="repealClick();"></td>
            	</tr>
            </table>
        </div>
        <!--转年处理操作域-->
        <Div id= "divButtonToPension" style= "display: none">
            <hr class=line>
             <Table>
                  <TR>
                       <TD class= common><IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPensionForm);"></TD>
                       <TD class= titleImg> 理赔金转年金 </TD>
                  </TR>
             </Table>
             <Div id= "divPensionForm" class=maxbox1 style= "display: ''">
                <table>
                    <TR class= common>
                        <TD class= title> 转年金类型</TD>
                        <TD class= input> <Input class=codeno name=PensionType id=PensionType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('llclmtopension',[this,PensionTypeName],[0,1],null,fm.polNo.value,'polno','1');" onkeyup="return showCodeListKey('lldiseases2',[this,PensionTypeName],[0,1],null,fm.polNo.value,'polno','1');"><input class=codename name=PensionTypeName id=PensionTypeName readonly=true></TD>
                        <TD class= title> </TD>
                        <TD class= input> </TD>
                        <TD class= title> </TD>
                        <TD class= input> </TD>
                    </TR>
                </table>
                <table>
                    <tr>
                        <td><Input class=cssButton disabled name="clmToPension" id=clmToPension value="转年金确认" type=button onclick = "saveClmToPension()"></td>
                        <td><Input class=cssButton name="adjustBnfButton" id=adjustBnfButton value="受益人调整" type=button onclick = "toAdjustBnf();"></td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
    <hr class=line>
    <table>
        <tr>
            <td><Input class=cssButton name="goBack" id=goBack value="返  回" type=button onclick="top.close();"></td>
        </tr>
    </table>    
    <%
    //******************
    //保存数据的隐藏表单
    //******************
    %>
    <Input type=hidden id="ManageCom" name="ManageCom"><!--机构-->
    <Input type=hidden id="ClmNo" name="ClmNo"><!--赔案号-->
    <Input type=hidden id="ClmFlag" name="ClmFlag"><!--案件领取方式标记(1一次统一给付 2 按年金方式领取 3 分期支付),查询初始化-->
    
    <Input type=hidden id="GrpContNo" name="GrpContNo"><!--集体合同号码-->
    <Input type=hidden id="GrpPolNo" name="GrpPolNo"><!--集体保单险种号码-->
    <Input type=hidden id="ContNo" name="ContNo"><!--合同号码-->
    <Input type=hidden id="PolNo" name="PolNo"><!--保单险种号号码-->
    <Input type=hidden id="BnfKind" name="BnfKind"><!--受益类型B为预付受益人分配-->
    <Input type=hidden id="FeeOperationType" name="FeeOperationType"><!--分配项目编码-->
    <Input type=hidden id="BatNo" name="BatNo"><!--批次号码-->
    <Input class=hidden id="bnfCurrency" name="bnfCurrency" >
     <Input class=hidden id="Currency" name="Currency" >
    <!--待删除内容-->
    <Input type=hidden id="bnfno" name="bnfno"><!--受益人序号-->
    <Input type=hidden id="insuredno" name="insuredno" ><!--被保人号码-->
	<Input type=hidden id="insurednobak" name="insurednobak" ><!--被保人号码（保存最初传过来的客户号）--> 
    <Input type=hidden id="bnftype" name="bnftype" value="0" ><!--受益人类别-->
    <Input type=hidden id="bnfgrade" name="bnfgrade" value="0" ><!--受益人级别-->
    <Input type=hidden id="customerNo" name="customerNo" value="0"><!--受益人号码-->
    <Input type=hidden id="payeeNo" name="payeeNo" value="0"><!--领款人号码-->
	<Input type=hidden id="oriBnfNo" name="oriBnfNo" value=""><!--原受益人序号-->  
	<Input type=hidden id="oriBnfPay" name="oriBnfPay" value=""><!--原受益人金额-->  
    <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</Form>
</body>
</html>
