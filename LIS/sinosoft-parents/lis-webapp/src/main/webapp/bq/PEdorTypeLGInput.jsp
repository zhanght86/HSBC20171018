<html>
<% 
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-11-26
 * @direction: 红利领取
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
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="./PEdorTypeLG.js"></SCRIPT>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeLGInit.jsp"%>
  <title>生存领取</title>
</head>
<body  onload="initForm();" >
  <form  method=post name=fm id=fm target="fraSubmit">
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
         <TD class =title>柜面受理日期</TD>
          <TD class = input>
            <input class="multiDatePicker wid" readonly name=EdorItemAppDate id=EdorItemAppDate ></TD>
         <TD class =title>生效日期</TD>
          <TD class = input>
            <input class="multiDatePicker wid" readonly name=EdorValiDate id=EdorValiDate ></TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
      </TR>
  </TABLE>
  </div>
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
                <td text-align: left colSpan=1>
                    <span id="spanCustomerGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </Div>
        
        <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCBnfPol);">
                </td>
                <td class= titleImg>
                生存受益人列表
                </td>
            </tr>
        </table>
    <Div  id= "divLCBnfPol" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanCustomerBnfGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </DIV>

	<table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCGrpPol);">
                </td>
                <td class= titleImg>
                    生存领取险种列表
                </td>
            </tr>
    </table>
    <Div  id= "divLCGrpPol" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </DIV>

     <table>
            <tr>
                <td class="common">
                    <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divActLCBnfPol);">
                </td>
                <td class= titleImg>
               实际领取人列表:<font color=red>如果证件为身份证,性别、出生日期不用填写,默认实际领取人为第一生存受益人</font></td>
                </td>
            </tr>
        </table>
    <Div  id= "divActLCBnfPol" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanCustomerActBnfGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </DIV>


	
    <!-- table class=common>
		<tr class=common>
			<td class=titile>
			实际领取金额:
			</td>
			<td class = input>
				<input class="common"  name=ActMoney >
			</td>
            <TD  class= title >
				<Div id= "divEdorquery1" style="display: ''">
					<Input  class= cssButton type=Button value="领取" onclick="sedorTypeDBSave();">
			 	</Div>
			</TD>
  			<TD  class= title > </TD>
            <TD  class= title > </TD>
			<TD  class= title > </TD>
            <TD  class= title > </TD>
		</tr>
		
	</table>
	 -->
    
   <br>
   <Div id= "divEdorquery" style="display: ''">
             <Input  class= cssButton type=Button value="保   存" onclick="sedorTypeDBSave();">
             <Input  class= cssButton type=Button value="返   回 " onclick="returnParent()">
             <Input  class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">
    </Div>

    <input type=hidden id="fmtransact" name="fmtransact">
    <input type=hidden id="ContType" name="ContType">
    <input type=hidden id="EdorNo" name="EdorNo">

    <br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br><br><br><br><br>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
