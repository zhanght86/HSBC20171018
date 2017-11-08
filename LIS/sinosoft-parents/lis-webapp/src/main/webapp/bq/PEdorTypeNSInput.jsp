<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
//程序名称：PEdorTypeNSInput.jsp
//程序功能：个人保全
//创建日期：2005-7-20 10:08
//创建人  ：lizhuo
//更新记录：  更新人    更新日期     更新原因/内容
%>



<%@page import="com.sinosoft.lis.finfee.FinFeePubFun"%>
<html>
<%@ include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.bq.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.lis.f1print.BqNameFun"%>
<%

String tContNo = request.getParameter("ContNo");
System.out.println("主险保单号tContNo"+tContNo);

ExeSQL tExeSQL = new ExeSQL();
String tCvalidate="";
String tCurDate="";
String CureCvaliDate="";    
String PreCvaliDate="";   
String NextCvaliDate="";
String tPaytodate="";
if(!"".equals(tContNo) && tContNo !=null && !"null".equals(tContNo))
{
	String tSQL="select cvalidate from lcpol where polno=mainpolno and contno='"+tContNo+"'";
//	主险保单号生效日
    tCvalidate=tExeSQL.getOneValue(tSQL);
    tCurDate=PubFun.getCurrentDate();
	String trSQL="select paytodate from lcpol where polno=mainpolno and contno='"+tContNo+"'";
	
	  tPaytodate=tExeSQL.getOneValue(trSQL);
		System.out.println("交至日期"+tPaytodate);
	    
    int tIntv=Integer.parseInt(tCurDate.substring(0,4))-Integer.parseInt(tCvalidate.substring(0,4));
    //CureCvaliDate=BqNameFun.calDate(Integer.parseInt(tCurDate.substring(0,4)),tCvalidate); 
    CureCvaliDate=FinFeePubFun.calOFDate(tCvalidate,tIntv,"Y",tCvalidate);
    
    NextCvaliDate=FinFeePubFun.calOFDate(CureCvaliDate,12,"M",tCvalidate);
    PreCvaliDate=PubFun.calDate(CureCvaliDate,-1,"Y","");   
	System.out.println("当前生效日："+CureCvaliDate);
	System.out.println("上年生效日："+PreCvaliDate);
	System.out.println("下年生效日："+NextCvaliDate);
}

%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>新增险种</title>
    <!-- 检查访问地址 -->
    <script language="JavaScript">
        if (top.location == self.location)
        {
            top.location = "PEdorTypeNS.jsp";
        }
    </script>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
	<link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本 -->
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
    <!-- 险种引用脚本 -->
    <script language="JavaScript" src="../app/SpecDealByRisk.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdorTypeNS.js"></script>
    <%@ include file="PEdorTypeNSInit.jsp" %>
</head>
<body  onload="initForm();" >
  <form action="./PEdorTypeNSSubmit.jsp" method=post name=fm id=fm target="fraSubmit">
  <div class=maxbox1>
        <table class="common">
            <tr class="common">
                <td class="title">保全受理号</td>
                <td class="input"><input type="text" class="readonly wid" name="EdorAcceptNo" id=EdorAcceptNo readonly></td>
                <td class="title">批改类型</td>
                <td class="input"><input type="text" class="codeno" name="EdorType" id=EdorType readonly><input type="text" class="codename" name="EdorTypeName" id=EdorTypeName readonly></td>
                <td class="title">保单号</td>
                <td class="input"><input type="text" class="readonly wid" name="ContNo" id=ContNo readonly></td>
            </tr>
            <tr class="common">
                <td class="title">柜面受理日期</td>
                <td class="input"><input type="text" class="coolDatePicker" name="EdorItemAppDate" readonly onClick="laydate({elem: '#EdorItemAppDate'});" id="EdorItemAppDate"><span class="icon"><a onClick="laydate({elem: '#EdorItemAppDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
				</td>
                <!--<td class="title">生效日期</td>
                <td class="input"><input type="text" class="multiDatePicker" name="EdorValiDate" readonly></td>
               --> 
                 <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
                 <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
	</div>
  <table>
    <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divAGInfo);">
      </td>
      <td class= titleImg>
        客户基本信息
      </td>
    </tr>
  </table>
  <Div  id= "divAGInfo" style= "display: ''" class=maxbox1>
      <TABLE class=common>
        <TR  class= common>
      <TD  class= title > 投保人姓名</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name= AppntName id=AppntName>
      </TD>
      <TD class = title > 证件类型 </TD>
      <TD class = input >
        <Input class=codeno  "readonly wid" readonly name=AppntIDType id=AppntIDType ><input class=codename name=AppntIDTypeName id=AppntIDTypeName readonly>
      </TD>
      <TD class = title > 证件号码 </TD>
      <TD class = input >
        <input class = "readonly wid" readonly name=AppntIDNo id=AppntIDNo>
      </TD>
    </TR>
           <TR  class= common>
      <TD  class= title > 被保人姓名</TD>
      <TD  class= input >
        <input class="readonly wid" readonly name=InsuredName id=InsuredName >
      </TD>
      <TD class = title > 证件类型 </TD>
      <TD class = input >
        <Input class=codeno  "readonly wid" readonly name=InsuredIDType id=InsuredIDType ><input class=codename name=InsuredIDTypeName id=InsuredIDTypeName readonly>
      </TD>

      <TD class = title > 证件号码 </TD>
      <TD class = input >
        <input class = "readonly wid" readonly name=InsuredIDNo id=InsuredIDNo>
      </TD>
    </TR>
      </TABLE>
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
  <Div  id= "divLCGrpPol" style= "display: ''" >
     <table  class= common>
        <tr  class= common>
            <td><span id="spanPolGrid"></span></td>
        </tr>
     </table>
  </DIV>
  <table>
     <tr>
        <td class="common">
          <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divNewPol);">
        </td>
        <td class= titleImg>
          新增险种信息
        </td>
     </tr>
  </table>
  <Div  id= "divNewPol" style= "display: ''">
     <table  class= common>
        <tr  class= common>
            <td><span id="spanNewPolGrid"></span></td>
        </tr>
     </table>
  </DIV>
  <table>
   <tr>
      <td class="common">
        <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLCImpart1);">
      </td>
      <td class= titleImg>
        被保人健康告知信息<font color=red>(请先录险种再录告知)</font>
      </td>
   </tr>
   </table>

  <Div  id= "divLCImpart1" style= "display: ''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanImpartGrid"></span></td>
            </tr>
        </table>
    </Div>
    <br> 
  <Div id= "divEdorquery" style="display: ''">
  	   <table class="common">
      <tr class=common>
          <td class=title>新增附加险类型</td>
          <td class=input><Input class="codeno"  id=NewAddType name=NewAddType id=NewAddType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" verify="类型|NotNull&Code:newaddtype" ondblclick="return showCodeList('newaddtype',[this,NewAddTypeName],[0,1]);" onkeyup="return showCodeListKey('newaddtype',[this,NewAddTypeName],[0,1]);"><input class=codename id=NewAddTypeName name=NewAddTypeName id=NewAddTypeName></td>                    
          <td class="title"><Div id="divApproveMofiyReasonTitle" style="display:"> 生效日 </Div></td>
          <td class="input"><Div id="divApproveMofiyReasonInput" style="display:"><Input class="coolDatePicker" dateFormat="short" id=NewCvaliDate name=NewCvaliDate onClick="laydate({elem: '#NewCvaliDate'});" id="NewCvaliDate"><span class="icon"><a onClick="laydate({elem: '#NewCvaliDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>

		  </Div></td>      <!--"multiDatePicker" dateFormat="short"     -->        
          <td class="title">&nbsp;</td>
          <td class="title">&nbsp;</td>
          <td class="input">&nbsp;</td>
      </tr>
     </table> 
        <Input class="cssButton" type=Button name="DetailButton" id=DetailButton value="进入险种信息"  onclick="gotoRisk()">
        &nbsp;
        <Input class="cssButton" type=Button value="  删除险种  " onclick="deleteapp()">
        <br><br>

       <Input class="cssButton" type=Button value=" 保 存 " onclick="edorTypeNSSave()">
        <Input class="cssButton" type=Button value=" 返 回 " onclick="returnParent()">
        <Input class="cssButton" TYPE=button VALUE="记事本查看" onclick="showNotePad()">
</Div>

     <input type="hidden" name="fmtransact" id=fmtransact>
     <input type="hidden" name="ContType" id=ContType>
     <input type="hidden" name="addrFlag" id=addrFlag>
     <input type="hidden" name="CustomerNo" id=CustomerNo>
     <input type="hidden" name="EdorNo" id=EdorNo>
     <input type="hidden" name="PolNo" id=PolNo>
     <input type="hidden" name="MainPolNo" id=MainPolNo>
     <input type="hidden" name="InsuredNo" id=InsuredNo>
     <input type="hidden" name="EdorValiDate" id=EdorValiDate>

     <input type="hidden" name="CureCvaliDate" id=CureCvaliDate>
     <input type="hidden" name="PreCvaliDate" id=PreCvaliDate>
     <input type="hidden" name="NextCvaliDate" id=NextCvaliDate>
     <input type="hidden" name="PayToDate" id=PayToDate>
     <input type="hidden" name="CurDate" id=CurDate>
    <br>
    <%@ include file="PEdorFeeDetail.jsp" %>
    <br><br>
</form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
 <script language="javascript">
    var splFlag = "<%=request.getParameter("splflag")%>";
</script>
</html>
