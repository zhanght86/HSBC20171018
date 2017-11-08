<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@include file="../common/jsp/UsrCheck.jsp"%>
<html>
<%
//程序名称：
//程序功能：
//创建日期：2002-08-21 09:25:18
//创建人 ：HST
//更新记录： 更新人  更新日期   更新原因/内容
%>

<%
//==================WANZH=====BGN========================
  String tFlag = request.getParameter("Flag");  //出处标识
  loggerDebug("GrpCustomerDiskInput","tFlag:"+tFlag);
//==================WANZH=====END========================
//==================wood=====BGN========================
  String tRgtNo = request.getParameter("RgtNo");  //出处标识
//==================WANZH=====END========================
%>
<head>
<%
    GlobalInput tGlobalInput = new GlobalInput();
    tGlobalInput = (GlobalInput)session.getValue("GI");
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="GrpCustomerDiskInput.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="GrpCustomerDiskInit.jsp"%>
</head>
<body onload="initForm();">
  <form action="./GrpCustomerDiskSave.jsp" method=post name=fm target="fraSubmit" ENCTYPE="multipart/form-data">  
    <table class=common>
      <TR class=common>
        <TD class=title>文件地址:</TD>
        <TD class=common width=30%>
          
          <Input type="file" name=FileName size=30>
          <input name=ImportPath type= hidden>
          <input name=RgtNo type=hidden >
          <Input type=hidden name="Operator" >
          <input name=Flag type=hidden value="<%=tFlag%>">
          <INPUT class=cssButton VALUE="导  入" TYPE=button onclick="submitForm();">
          <INPUT class=cssButton VALUE="重  置" TYPE=reset>
        </TD>
      </TR>
      <input type=hidden name=ImportFile>
    </table>

  </form>
  <form name=fmquery action=./DiskErrList.jsp target=fraSubmit method=post> 

    <table class=common border=0 width=100%>
      <tr>
        <td class=titleImg align=center>团体出险人导入错误信息查询－请输入查询条件:</td>
      </tr>
    </table>
     <table class=common align=center>
      <TR class=common>
        <TD class=title>理赔号</TD>
        <TD class=input>
          <Input class=common name=tRgtNo>
        </TD>
        <TD class=title>客户号</TD>
        <TD class=input>
          <Input class=common name=tCustomerNo>
        </TD>
        <TD class=title>客户姓名</TD>
        <TD class=input>
          <Input class=common name=tCustomerName>
        </TD>
        </TD>
      </TR>
    </Table> 
    <INPUT VALUE="查  询" class=cssButton TYPE=button onclick="easyQueryClick();">
    <INPUT VALUE="删  除" class=cssButton TYPE=button onclick="deleteInsured();">
    <INPUT VALUE="全部删除" class=cssButton TYPE=button onclick="deleteAll();">
    <Table>
    <Div id="divDiskErr" style="display: ''" align = center>
      <table class=common>
        <TR class=common>
          <TD text-align: left colSpan=1>
            <span id="spanDiskErrQueryGrid"></span>
          </TD>
        </TR>
      </Table>
      <Table align = center>
      <INPUT VALUE="首  页" class=cssButton TYPE=button onclick="turnPage.firstPage();">
      <INPUT VALUE="上一页" class=cssButton TYPE=button onclick="turnPage.previousPage();">
      <INPUT VALUE="下一页" class=cssButton TYPE=button onclick="turnPage.nextPage();">
      <INPUT VALUE="尾  页" class=cssButton TYPE=button onclick="turnPage.lastPage();">
      </Table>
    </Div>
  <span id="spanCode" style="display: none; position:absolute; slategray"></span>
  </form>
</body>
</html>
