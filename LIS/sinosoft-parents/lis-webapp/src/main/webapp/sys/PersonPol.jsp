
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    String tDisplay = "";
    String tContNo="";
    String customerNo="";
    try
    {
       tDisplay = request.getParameter("display");
       customerNo =request.getParameter("CustomerNo");
       //loggerDebug("PersonPol",tDisplay+"--------------------");
    }
    catch( Exception e )
    {
        tDisplay = "";
    }
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
   //loggerDebug("PersonPol","管理机构-----"+tG.ComCode);
%>

<script>
  var comCode = "<%=tG.ComCode%>";
  var customerNo = "<%=customerNo%>";
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>客户保单信息查询</title>
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
    <script src="../common/javascript/MultiCom.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PersonPol.js"></script>
    <%@ include file="PersonPolInit.jsp" %>
</head>
<body  onload="initForm()" >
  <form method=post name=fm id=fm target="fraSubmit">
    <!-- 客户基本信息部分 -->
    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divIdNo);">
            </td>
            <td class= titleImg align=left>
               勾选查看保单信息：
              </td>
         </tr>
    </table>
	       
     <Div id= "divTurnPagePolyGrid" style= "display:''; text-align:center;" >
        <table  class= common>
            <tr  class= common>
                <td><span id="spanPolGrid"></span></td>
            </tr>
        </table>
                <input type="button" class="cssButton90" value="首  页" onclick="turnPageP.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageP.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageP.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnP.lastPage()">
    </div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
  <br/><br/><br/><br/>
</body>
</html>
