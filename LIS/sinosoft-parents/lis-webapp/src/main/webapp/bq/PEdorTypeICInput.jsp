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
 * @date     : 2008-12-3
 * @direction: 客户重要资料变更
 ******************************************************************************/
%>
<%@page contentType="text/html;charset=GBK" %>

<head >
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
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
  <SCRIPT src="./PEdorTypeIC.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@include file="PEdorTypeICInit.jsp"%>
  <title>客户重要资料变更</title>
</head>
<body  onload="initForm();" >
  <form name="fm" method="post" target="fraSubmit">
  <div class=maxbox1>
  <table class=common>
    <TR  class= common>
      <TD  class= title > 保全受理号</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=EdorAcceptNo id=EdorAcceptNo>
      </TD>
      <TD class = title > 批改类型 </TD>
      <TD class = input >
        <Input class=codeno  readonly name=EdorType id=EdorType><input class=codename name=EdorTypeName id=EdorTypeName readonly=true>
      </TD>

      <TD class = title >保单号</TD>
      <TD class = input >
        <input class="readonly wid" readonly name=ContNo id=ContNo>
      </TD>
    </TR>
      <TR  class= common>
        <TD class =title>柜面受理日期</TD>
        <TD class = input>
            <input class="coolDatePicker" readonly name=EdorItemAppDate onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
        </TD>
       <!-- <TD class =title>生效日期</TD>
        <TD class = input>
            <input class="readonly wid" readonly name=EdorValiDate ></TD>-->
            <TD  class= title > </TD>
            <TD  class= title > </TD>
           <TD  class= title > </TD>
            <TD  class= title > </TD>
      </TR>
  </TABLE>
  </div>
  <table>
   <tr class = common>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerOldInfo);">
      </td>
      <td class= titleImg>
        客户原要件信息
      </td>
   </tr>
   </table>
     <Div  id= "divCustomerOldInfo" class=maxbox1 style= "display: ">
         <table  class= common>
         <TR  class= common>
         	<TD  class= title>
            姓
          </TD>
          <TD  class= input>
            <Input class="readonly wid" name=LastName id=LastName readonly>
          </TD>
          <TD  class= title>
            名
          </TD>
          <TD  class= input>
            <Input class="readonly wid" name=FirstName id=FirstName readonly>
          </TD>
       
          <TD  class= title>
            客户姓名
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=Name id=Name > 
          </TD>
      
        </TR>
            <TR  class= common>
          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
            <Input class=codeno name=Sex id=Sex><input class=codename name=SexName id=SexName readonly=true>
          </TD>
          <TD  class= title>
           出生日期
          </TD>
          <TD  class= input>
            <Input class= "coolDatePicker" readonly name=Birthday onClick="laydate({elem: '#Birthday'});" id="Birthday"><span class="icon"><a onClick="laydate({elem: '#Birthday'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class=codeno name=IDType id=IDType readonly ><input class=codename name=IDTypeName id=IDTypeName readonly=true>
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=IDNo id=IDNo >
         </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
          <!--TD  class= title>
            年龄
          </TD>
          <TD  class= input>
            <Input class="readonly wid" readonly name=Age >
         </TD-->
        </TR>
      </table>
  </Div>
  <table>
   <tr class = common>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomerNewInfo);">
      </td>
      <td class= titleImg>
        客户更新后要件信息
      </td>
   </tr>
   </table>

     <Div  id= "divCustomerNewInfo" class=maxbox1 style= "display: ">
      <table  class= common>
      	
      	<TR  class= common>
      	         	<TD  class= title>
            姓
          </TD>
          <TD  class= input>
            <Input class="wid common" name=LastNameBak id=LastNameBak verify="姓|notnull&len<=120" readonly>
          </TD>
          <TD  class= title>
            名
          </TD>
          <TD  class= input>
            <Input class="wid common" name=FirstNameBak id=FirstNameBak verify="名|notnull&len<=120" readonly>
          </TD>
           <TD  class= title>
            客户姓名
          </TD>
         <TD  class= input>
            <Input class="readonly wid" name=NameBak id=NameBak readonly=true>
          </TD>
        </TR>
        <TR  class= common>

          <TD  class= title>
            性别
          </TD>
          <TD  class= input>
             <Input class=codeno readonly name=SexBak id=SexBak ><input class=codename name=SexNameBak id=SexNameBak readonly=true>
          </TD>
          <TD  class= title>
           出生日期
          </TD>
          <TD  class= input>
            <Input class= "coolDatePicker" dateFormat="short" name=BirthdayBak readonly verify="日期|DATE" onClick="laydate({elem: '#BirthdayBak'});" id="BirthdayBak"><span class="icon"><a onClick="laydate({elem: '#BirthdayBak'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
          </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
        </TR>
        <TR  class= common>
          <TD  class= title>
            证件类型
          </TD>
          <TD  class= input>
            <Input class=codeno name=IDTypeBak id=IDTypeBak readonly ><input class=codename name=IDTypeNameBak id=IDTypeNameBak readonly=true>
          </TD>
          <TD  class= title>
            证件号码
          </TD>
          <TD  class= input>
            <Input class="wid common" readonly name=IDNoBak id=IDNoBak >
         </TD>
            <TD  class= title > </TD>
            <TD  class= title > </TD>
         <!--TD  class= title>
            年龄
          </TD>
          <TD  class= input>
            <Input class="readonly wid" type=hidden readonly name=AgeBak >
         </TD-->
        </TR>
      </table>
      </Div>

      <table>
        <tr class = common>
            <td class=common>
              <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divPolNewGrid);">
            </td>
            <td class= titleImg>
              保单险种列表信息
           </td>
        </tr>
    </table>
    <div  id= "divPolNewGrid" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolNewGrid" >
                    </span>
                </td>
            </tr>
        </table>
    </div>
    </DIV>

    <br>
    <Div id= "divEdorquery" style="display: ''">
             <Input  class= cssButton type=Button value=" 保 存 " onclick="edorTypeICSave()">
             <Input  class= cssButton type=Button value=" 返 回 " onclick="returnParent()">
             <Input  class= cssButton TYPE=button VALUE="记事本查看" onclick="showNotePad();">
    </Div>
</Div>
        <input type="hidden" name="AppObj">    <!-- XinYQ added on 2006-04-06 -->
        <input type="hidden" name="fmtransact">
        <input type="hidden" name="ContType">
        <INPUT type= "hidden" name="CustomerNo">
        <input type="hidden" name="EdorNo">
        <input type="hidden" name="CMEdorNo">
        <input type="hidden" name="MissionID">
        <input type="hidden" name="SubMissionID">
        <input type="hidden" name="ActivityID">
       <input type="hidden" name="EdorValiDate">

        <br>
        <%@ include file="PEdorFeeDetail.jsp" %>    <!-- XinYQ added on 2005-12-21 -->
  </form>
  <br><br><br><br><br>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
<script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
