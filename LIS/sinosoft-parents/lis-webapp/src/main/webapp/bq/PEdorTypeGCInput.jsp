<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
//程序名称：
//程序功能：领取形式变更
//创建日期：2005-4-25 01:40下午
//创建人  ：Lihs
%>


<%@include file="../common/jsp/UsrCheck.jsp"%>


<html>
<head >
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>生存给付转账授权</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeGC.jsp";
        }
    </script>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
    <link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本  -->
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="./PEdor.js"></script>
    <script language="JavaScript" src="./PEdorTypeGC.js"></script>
    <%@include file="PEdorTypeGCInit.jsp"%>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeGCSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
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
        <input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
                 <TR  class= common>
               <TD class =title>柜面受理日期</TD>
        <TD class = input>
            <input class="readonly wid" readonly name=EdorItemAppDate  id=EdorItemAppDate></TD>
        </TD>
                <TD class = title> </TD>
                <TD class = input> </TD>
				<TD class = title> </TD>
                <TD class = input> </TD>
      </TR>
  </TABLE>
  </div>
  <table>
    <tr>
      <td class=common>
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
                <td text-align: left colSpan=1>
                    <span id="spanPolGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>

    <table>
    <tr>
      <td class=common>
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCPol);">
      </td>
      <td class= titleImg>
        保单信息
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
            <Input class="readonly wid" readonly  name=AppntNo id=AppntNo>
          </TD>

         <TD class= title>
            投保人姓名
         </TD>
             <TD class= input>
                <Input class="readonly wid" readonly  name=AppntName id=AppntName>
             </TD>
              <TD class= title>
                交费方式
                 </TD>
                 <TD class= input>
                 <Input class="readonly wid" readonly  name=PayMode id=PayMode>
                 </TD>
                </TR>
                <TR>
               <TD class= title>
                 保费标准
         </TD>
         <TD class= input>
             <Input class="readonly wid" readonly  name=Prem id=Prem>
         </TD>

         <TD class= title>
         基本保额
         </TD>
         <TD class= input>
             <Input class="readonly wid" readonly  name=Amnt id=Amnt>
         </TD>
         <TD class= title>
                交费形式
              </TD>
              <TD  class= input>
                <Input class="readonly wid" readonly name=PayLocation_S id=PayLocation_S>
              </TD>
            </TR>
            <TR>
         <TD class= title>
             银行编码
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=BankCode_S id=BankCode_S>
         </TD>
         <TD class= title>
             银行帐号
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=BankAccNo_S id=BankAccNo_S> 
         </TD>
         <TD class= title>
             银行账户名
         </TD>
         <TD class= input>
            <Input class="readonly wid" readonly  name=AccName_S>
         </TD>
     </TR>
     <TR>
                <TD class = title>
                    领取形式
                </TD>
                <TD class = input>
                    <input class ="readonly wid" readonly name=GerForm_S id=GerForm_S>
                </TD>
                <TD class = title> </TD>
                <TD class = input> </TD>
                <TD class = title> </TD>
                <TD class = input> </TD>
            </TR>
    </Table>
    </Div>

  <table>
    <tr>
      <td class=common>
        <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divAGInfo);">
      </td>
      <td class= titleImg>
        领取形式变更
      </td>
    </tr>
   </table>
    <Div  id= "divAGInfo" class=maxbox1 style= "display: ''">
      <table  class= common>
        <TR class = common >
          <TD class= title width="">
          领取形式
          </TD>
          <TD  class= input width="">
          <input class="codeno" name=GetForm id=GetForm verify="领取形式|NotNull&Code:GetLocation" style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" ondblclick="showCodeList('GetLocation', [this,GetFormName],[0,1]);" onkeyup="showCodeListKey('GetLocation', [this,GetFormName],[0,1]);"><input class="codename" name=GetFormName id=GetFormName readonly>
                <!--<input class="codeno" name=GetForm CodeData="0|^1|自行领取^2|银行转帐^3|网上支付" ondblclick="showCodeListEx('GoonGetMethod', [this,GetFormName],[0,1]);" onkeyup="showCodeListKeyEx('GoonGetMethod', [this,GetFormName],[0,1]);" onfocus="showBankDiv();" ><input class="codename" name=GetFormName readonly=true>-->
                </TD>
                   <TD class= title width="">
                     </TD>
                    <TD  class= input width="">
                    </TD>
                    </TD>
                   <TD class= title width="">
                     </TD>
                    <TD  class= input width="">
                    </TD>
        </TR>
      </table>
      <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divOldBnfGridLayer)"></td>
                <td class="titleImg">受益人分配</td>
            </tr>
        </table>
      <!-- 生存受益人录入表格 -->
        <div id="divNewBnfGridLayer" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanNewBnfGrid"></span></td>
                </tr>
            </table>
            <!-- 新受益人结果翻页 -->
            <div id="divTurnPageNewBnfGrid" align="center" style= "display:none">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageNewBnfGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageNewBnfGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageNewBnfGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageNewBnfGrid.lastPage()">
            </div>
        </div>
     <!-- modify by jiaqiangli 2008-08-21 采用更多列的DIV
        <Div  id= "BankInfo" style= "display: none">
          <table  class= common>
            <TR class = common >
              <TD  class= title width="">
              开户银行
              </TD>
              <TD  class= input width="">
                    <Input class="codeno" name=GetBankCode ondblclick="showCodeList('bank',[this, BankName], [0, 1]);"onkeyup="showCodeListKey('AgentCode', [this, BankName], [0, 1]);"><input class=codename name=BankName readonly=true>
              </TD>
                    <td class="title">银行帐号</td>
                    <td class="input"><input type="text" class="coolConfirmBox" name="GetBankAccNo"></td>
               <TD  class= title width="">
              户   名
              </TD>
              <TD  class= input width="">
                     <Input class= "common"  name="GetAccName">
              </TD>
            </TR>
          </table>
        </Div>
        -->
    </Div>
    <br>
    <Div id= "divEdorquery" style="display: ''">
             <Input  class= cssButton type=Button value=" 保 存 " onclick="saveEdorTypeGC()">
             <Input  class= cssButton type=Button value=" 返 回 " onclick="returnParent()">
             <Input  class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad()">
    </Div>
     <input type="hidden" name="fmtransact">
     <input type="hidden" name="ContType">
     <input type="hidden" name="InsuredNo">
     <input type="hidden" name="PolNo">
     <input type="hidden" name="EdorNo">
     <input type="hidden" name="AppObj">
  </form>
  <br /><br /><br /><br />
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";

</script>
</html>
