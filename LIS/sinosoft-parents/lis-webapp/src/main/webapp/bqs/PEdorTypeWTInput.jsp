<html>

<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.*"%>
  <%
     %>

<head >
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>

  <SCRIPT src="./PEdor.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeWT.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeWTInit.jsp"%>


</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeWTSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
  <TABLE class=common>
    <TR  class= common>
      <TD  class= title > 保全受理号</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo >
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>

      <TD class = title > 保单号 </TD>
      <TD class = input >
        <input class = "readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
    <TR  class= common>
         <TD class =title>申请日期</TD>
          <TD class = input>
            <input class="readonly wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
         <TD class =title>生效日期</TD>
          <TD class = input>
            <input class="readonly wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
      </TR>
  </TABLE>
  </div>
        <!--Div  id= "divInsuredInfo" style= "display: none">
        <table>
            <tr>
                <td class="common">
                 <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
            <td class= titleImg>
                客户信息
            </td>
        </tr>
        </table>
        <Div  id= "divInsuredGrid" style= "display: ''">
            <table  class= common>
                <tr  class= common>
                    <td style="text-align: left" colSpan=1>
                        <span id="spanInsuredGrid" >
                        </span>
                    </td>
                </tr>
            </table>
    </div>
    </Div-->
    <table>
    <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolGrid1);">
      </td>
      <td class= titleImg>
        客户基本信息
      </td>
    </tr>
   </table>
  <Div  id= "divPolGrid1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td style="text-align: left" colSpan=1>
                    <span id="spanCustomerGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    保单险种信息
                </td>
            </tr>
        </table>
    <Div  id= "divLCGrpPol" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td style="text-align: left" colSpan=1>
                    <span id="spanPolGrid" >
                    </span>
                </td>
            </tr>
        </table>

    </DIV>


    <table>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPInsured);">
      </td>
      <td class= titleImg>
        退保信息
      </td>
   </tr>
   </table>


    <Div  id= "divLPInsured" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR class = common>
            <TD  class= title > 保单生效日期 </TD>
            <TD  class= input ><input class="wid common" readonly name=CvaliDate id=CvaliDate ></TD>
            <TD  class= title > 客户签收日期 </TD>
            <TD  class= input ><input class="wid common" readonly name=CustomGetPolDate id=CustomGetPolDate ></TD>
            <TD  class= title > 已满保单年度 </TD>
            <TD  class= input ><input class="wid common" readonly name=Inteval id=Inteval ></TD>
        </TR>
        <TR class = common>
            <td class = title> 退保原因 </td>
            <td class=input><Input class=codeno name=SurrReason id=SurrReason verify="退保原因|code:comcode&notnull" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="return showCodeList('SurrORDeReason',[this,SurrReasonName],[0,1],null,null,null,0,208);" onkeyup="return showCodeListKey('SurrORDeReason',[this,SurrReasonName],[0,1],null,null,null,0,208)" onFocus="showInfo();">
			<input class=codename name=SurrReasonName id=SurrReasonName readonly=true elementtype=nacessary></td>
            <td  class = title> 投保人与业务员关系 </td>
            <td class= input ><Input class="codeno" name=RelationToAppnt id=RelationToAppnt style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('relationtoappnt',[this,RelationToAppntName],[0,1],null,null,null,0,208);"onkeyup="showCodeListKey('relationtoappnt',[this,RelationToAppntName],[0,1],null,null,null,0,208);">
			<input class=codename name=RelationToAppntName id=RelationToAppntName readonly=true></td>            
            <TD  class= title > 扣除工本费 </TD>
            <TD  class= input ><input type="checkbox" name="FeeGetFlag" id=FeeGetFlag ></TD>
       </TR>
	   <TR class = common>
            <TD  class= title >  体检费用 </TD>
            <TD  class= input ><input class="wid common" readonly name=TJFee id=TJFee value="0" ></TD>
        </TR>
     </table>
   </Div>
   <Div  id= "divLRInfo" style= "display: none">
      <table  class= common>
        <TR  class = common>
            <TD  class= title width="15%"> 保单补发次数 </TD>
            <TD  class= input ><input class="common wid" type="input" name="LostTimes" id=LostTimes onblur="return checkNumber(this)"></TD>
            
            <TD  class= title width="15%"> &nbsp; </TD>
            <TD  class= input >&nbsp;</TD>
            <TD  class= title width="15%"> &nbsp; </TD>
            <TD  class= input >&nbsp;</TD>
            <input type=hidden id="TrueLostTimes" name="TrueLostTimes">
        </TR>
     </table>
</Div>
        <Div  id= "divRemarkInfo" style= "display: none">
     <table>
     	<tr>
      <td class="common">
        <IMG  src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divWordsRemakeByInfo);">
      </td>
      <td class= titleImg>
        退保备注信息
      </td>
   </tr>
   </table>
    <Div  id= "divWordsRemakeByInfo" class=maxbox1 style= "display: ''">
			  <table class= common>
			    <TR  class= common> 
			      <TD width="100%" height="15%"  class= title> 备注:</TD>
			    </TR>
			    <TR  class= common>
			      <TD height="85%"  class= title><textarea name="Remark" id=Remark verify="备注内容|len<100" verifyorder="1" cols="80" rows="3" class="common" ></textarea></TD>
			    </TR>
			  </table>
    </Div>
</Div>

   <br>
   <Div id= "divEdorquery" style="display: ''">
             <Input  class= cssButton type=Button value=" 保 存 " onclick="edorTypeCTSave()">
             <Input  class= cssButton type=Button value="费用明细" onclick="GetEndorseQuery()" style= "display: none">
             <Input  class= cssButton type=Button value=" 返 回 " onclick="returnParent()">
             <Input  class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">
    </Div>

    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden id="PayToDate" name="PayToDate">
    <input type=hidden id="PolNo" name="PolNo">
    <!--犹豫期退保标志，确定是整单退还是只退附加险，0,只退附加险，1 退全部-->
    <input type=hidden id="WTContFLag" name="WTContFLag">
    <input type=hidden name="EdorNo">

    <br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br /><br /><br /><br />
</body>
<script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
