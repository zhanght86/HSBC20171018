<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="./PEdor.js"></SCRIPT>
  
  <SCRIPT src="./PEdorTypeXS.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <%@include file="PEdorTypeXSInit.jsp"%>
  <title>协议减保</title>
</head>
<body  onload="initForm();" >
  <form method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
 <TABLE class=common>
    <TR  class= common>
      <TD  class= title > 保全受理号</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
        <Input class=codeno name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly>
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
        <TD class = title></TD>
        <TD class = input></TD>
		<TD class = title></TD>
        <TD class = input></TD>
    </TR>
  </TABLE>
  </div>
  <!--保单险种列表信息-->
 <Div  id= "divPolInfo" style="display:''">
     <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,Customer);">
            </td>
            <td class= titleImg>
                客户信息
            </td>
        </tr>
    </table>
    <Div id = "Customer" style="display:''">
        <table>
            <tr>
                <td>
                    <span id="spanCustomerGrid"></span>
                </td>
            </tr>
        </table>
    </Div>
        <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid1);">
                </td>
                <td class= titleImg>
                    保单险种列表信息
                </td>
            </tr>
        </table>
    <Div  id= "divPolGrid1" style="display:''">
        <table  class=common>
            <tr  class=common>
                <td><span id="spanPolGrid"></span></td>
            </tr>
        </table>
    </DIV>

   <!--险种的详细信息-->

     <!--操作区域-->
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,OperationDiv);">
      </td>
      <td class= titleImg>
        当前操作 <font color = red >（操作前请选择操作险种 豁免险除外）</font>
      </td>
   </tr>
   </table>
   <div id="OperationDiv" class=maxbox1>
   <table>
   <TR class = common>
            <td class = common> 减保原因 </td>
            <td class=input><Input class=codeno name=SurrReasonCode id=SurrReasonCode verify="退保原因|code:XSurrORDeReason&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('XSurrORDeReason',[this,SurrReasonName],[0,1]);" onkeyup="return showCodeListKey('XSurrORDeReason',[this,SurrReasonName],[0,1]);"><input class=codename name=SurrReasonName id=SurrReasonName readonly=true elementtype=nacessary></td>
            <td  class = common> 投保人与业务员关系 </td>
            <td class= input ><Input class="codeno" name=RelationToAppnt id=RelationToAppnt style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('relationtoappnt',[this, RelationToAppntName], [0, 1]);"onkeyup="showCodeListKey('relationtoappnt', [this, RelationToAppntName], [0, 1]);"><input class=codename name=RelationToAppntName id=RelationToAppntName readonly=true></td>
            <TD  class= title > </TD>
            <TD  class= input > </TD>
       </TR>
       <tr class=common>
            <TD class=title colspan=6 > 协议减保其他原因备注 </TD>
        </tr>
        <tr class=common>
            <TD  class=input colspan=6 ><textarea name="ReasonContent" id=ReasonContent cols="100%" rows="5" witdh=100% class="common"></textarea></TD>
        </tr>
       </table>
   <!-- 减保额 -->
    <div id = "PTAmntDiv" style= "display: none">	
      <table class = common>
       		<tr  class= common>
		       <TD  class= title >减少保额</TD>
		       <TD  class= input > <input class="multiCurrency wid"  name=MinusPTAmnt id=MinusPTAmnt></TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
            </tr>   
       </table>
    </div>
    <!-- 减保费 -->
    <div id = "PTPremDiv" style= "display: none">	
      <table class = common>
       		<tr  class= common>
		       <TD  class= title >减少保费</TD>
		       <TD  class= input > <input class="multiCurrency wid"  name=MinusPTPrem id=MinusPTPrem></TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
            </tr>   
       </table>
    </div>
    <!-- 减份数 -->
    <div id = "PTMutDiv" style= "display: none">	
      <table class = common>
       		<tr  class= common>
		       <TD  class= title >减少份数</TD>
		       <TD  class= input > ]<input class="common wid"  name=MinusPTMut id=MinusPTMut></TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   <TD  class= title > </TD>
               <TD  class= input > </TD>
			   
            </tr>   
       </table>
    </div>
    <div id = "divPTbuttion" style = "display: ''">
        <Input class= cssButton type=Button value=" 协议减保 " onclick="reduceOneRisk()">
    </div>
    
    <!--协议减保后信息 add by jiaqiangli 2008-09-16 -->
    <!--应退与实退-->
    <Div  id= "divCTFeeInfo" style= "display: ''">
            <table class="common">
                <tr class="common">
                    <td class="title">应退金额</td>
                    <td class="input"><input type="text" class="multiCurrency wid" name="GetMoney" id=GetMoney readonly></td>
                    <td class="title">实退金额</td>
                    <td class="input"><input type="text" class="multiCurrency wid" name="AdjustMoney" id=AdjustMoney readonly></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
    </DIV>
	</div>
    <!--协议减保后信息 add by jiaqiangli 2008-09-16 -->
    
    <!--协议减保后信息 add by jiaqiangli 2008-09-16 -->
    <Div  id= "divCTFeePolDetailButton" style="display: none">
    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeePolDetail);">
                </td>
                <td class= titleImg> 险种退费合计 </td>
            </tr>
    </table>
	</Div>
    <Div  id= "divCTFeePolDetail" style="display: none">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCTFeePolGrid"></span></td>
            </tr>
        </table>
    </Div>
		 <Div  id= "divCTFeeDetailButton" style="display: none">
    <table>
            <tr>
                <td class=common>
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCTFeeDetail);">
                </td>
                <td class= titleImg> 退保费用调整 </td>
            </tr>
    </table>
	</Div>
    <Div  id= "divCTFeeDetail" style="display: none">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanCTFeeDetailGrid"></span></td>
            </tr>
        </table>
    </Div>
    <BR>
        <!--减保后信息-->
<div id = 'divnone' style = "display: none">
   <table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol5);">
      </td>
      <td class= titleImg>
        减保退还金额信息
      </td>
   </tr>
   </table>
<Div id = "divPol5" class=maxbox1 style= "display: none">
    <table class = common>
        <tr class = common>
            <td class = title>基本保额应退金额</td>
            <td class = input>
                <input class="readonly wid" name = CashValue id=CashValue>
            </td>
            <td class = title>累计红利应退金额</td>
            <td class = input>
                <input class="readonly wid" readonly name = AddupBonus id=AddupBonus>
            </td>
            <td class = title>终了红利应退金额</td>
            <td class = input>
                <input class="readonly wid" readonly name = FinaBonus id=FinaBonus>
            </td>
        </tr>
        <tr class = common>
            <td class = title>退还保费金额</td>
            <td class = input>
                <input class="readonly wid" readonly name = Prem id=Prem>
            </td>
            <td class = title>健康加费应退金额</td>
            <td class = input>
                <input class="readonly wid" readonly name = AddJK id=AddJK>
            </td>

            <td class = title>职业加费应退金额</td>
            <td class = input>
                <input class="readonly wid" readonly name = AddZY id=AddZY>
            </td>
        </tr>
    </table>
</Div>
<table>
   <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPol2);">
      </td>
      <td class= titleImg>
        扣除金额信息
      </td>
   </tr>
   </table>
   <Div id = "divPol2" class=maxbox1 style= "display:none">
    <table class = common>
        <tr class = common>
            <td class = title>贷款本金</td>
            <td class = input>
                <input class="readonly wid" readonly name=LoanCorpus id=LoanCorpus>
            </td>
            <td class = title>贷款利息</td>
            <td class = input>
                <input class="readonly wid" readonly name=LoanInterest id=LoanInterest>
            </td>
            <td class = title>自垫本金</td>
            <td class = input>
                <input class="readonly wid" readonly name=AutoPayPrem id=AutoPayPrem>
            </td>
            </tr>
        <tr>
            <td class = title>自垫利息</td>
            <td class = input>
                <input class="readonly wid" readonly name=AutoPayInterest id=AutoPayInterest>
            </td>
            <td class = title>欠缴保费</td>
            <td class = input>
                <input class="readonly wid" readonly name=OwePrem id=OwePrem>
            </td>
            <td class = title>欠缴保费利息</td>
            <td class = input>
                <input class="readonly wid" readonly name=OweInterest id=OweInterest>
            </td>
        </tr>
        <tr>
            <td class = title>扣除金额合计</td>
            <td class = input>
                <input class="readonly wid" readonly name=PayMoney id=PayMoney>
            </td>
            <td class = title></td>
            <td class = input>
            </td>
            <td class = title></td>
            <td class = input></td>
        </tr>
    </table>
  </Div>


   <Div id = "divPol4" style= "display: none">
    <table class = common>
        <tr class = common>
            <td class = title>减保后基本保额</td>
            <td class = input>
                <input class="readonly wid" readonly>
            </td>
            <td class = title> 续期缴费标准</td>
            <td class = input>
                <input class="readonly wid" readonly>
            </td>
            <td class = title>减保后累积红利保额</td>
            <td class = input>
                <input class="readonly wid" readonly>
            </td>
            </tr>

    </table>
  </Div>

</div>

  <br><br>
<Div  id= "divLRInfo" style= "display: none">
      <table  class= common>
        <TR  class = common>
            <TD  class= title width="15%"> 保单补发次数 </TD>
            <TD  class= input ><input class="common wid" type="text" name="LostTimes" id=LostTimes></TD>
            <input type=hidden id="TrueLostTimes" name="TrueLostTimes">
        </TR>
     </table>
</Div>
 
  <Div id= "divEdorquery" style="display: ''">
       <Input class= cssButton type=Button value=" 调整金额保存 " onclick="saveEdorTypePT()">
       <Input class= cssButton type=Button value=" 返 回 " onclick="returnParent()">
       <Input class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad()">
 </Div>

     <input type="hidden" name="AppObj">
     <input type="hidden" name="CalMode">
     <input type="hidden" name="fmtransact">
     <!-- comment by jiaqiangli 2008-11-20 减少标志 PTFlag 0减少保费1减少份数2减少保额-->
     <input type="hidden" name="PTFlag">
     <input type="hidden" name="ContType" value="1">
     <input type="hidden" name="EdorNo">
     <input type="hidden" name="Flag266">
     <input type="hidden" name="Flag267">
     <input type="hidden" name="SubScale">  <!--- 减保后投保比例 特殊险种使用 add at 2005-11-29--->
     <input type="hidden" name="InsuredNo">

    <!-- comment by jiaqiangli -->
    <!--br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br-->
	<br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
