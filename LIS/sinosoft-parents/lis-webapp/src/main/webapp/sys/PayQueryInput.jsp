
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>

 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
    String tDisplay = "";
    String tContNo="";
    String tCustomerNo ="";
    try
    {
       tCustomerNo = request.getParameter("CustomerNo");
       tDisplay = request.getParameter("display");
       //loggerDebug("PayQueryInput",tDisplay+"--------------------");
    }
    catch( Exception e )
    {
        tDisplay = "";
    }
%>

<%
   GlobalInput tG = new GlobalInput();
   tG=(GlobalInput)session.getValue("GI");//添加页面控件的初始化。
   //loggerDebug("PayQueryInput","管理机构-----"+tG.ComCode);
%>

<script>
  var comCode = "<%=tG.ComCode%>";
  var customerNo = "<%=tCustomerNo%>"; //客户号
</script>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>客户既往收费信息查询</title>
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
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PayQuery.js"></script>
    <%@ include file="PayQueryInit.jsp" %>
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
               客户既往收费信息查询：
              </td>
         </tr>
    </table>
        <Div  id= "divIdNo" style= "display: ''" align="left" class=maxbox1>
        <table class= common border=0 width=100%>
            <tr>

         </tr>
        <table  class= common>
        <TR  class= common>
        <TD  class=title5>收费业务类型</TD>
          <TD  class=input5> <Input class= codeno name=TempFeeType id=TempFeeType style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData= "0|^6|新契约个人^4|新契约团体^7|新契约银贷^2|续期交费(渠道-个人)^3|续期交费(渠道-银贷)^10|保全交费^02|预存续期保费(个人)^9|理赔收费^5|客户预存" ondblClick="showCodeListEx('TempFeeType',[this,TempFeeTypeName],[0,1]);" onkeyup="showCodeListKeyEx('TempFeeType',[this,TempFeeTypeName],[0,1]);"><input class="codename" name="TempFeeTypeName" id=TempFeeTypeName readonly="readonly">
           <TD  class=title5>收费方式</TD>
         <TD  class=input5> <Input class= codeno name=PayMode id=PayMode style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" CodeData= "0|^0|老系统数据补录^1|现金^2|现金送款簿^3|支票^4|银行转帐（非制返盘)^5|内部转帐^6|POS收款^7|银行代扣（制返盘)^8|邮政业务^9|银行收款^A|银保通^C|续期冲正" ondblClick="showCodeListEx('PayMode',[this,PayModeName],[0,1]);" onkeyup="showCodeListKeyEx('PayMode',[this,PayModeName],[0,1]);"><input class="codename" name="PayModeName" id=PayModeName readonly="readonly wid">      	
        	</TD>
			 </TR>
			 <TR  class= common>
          <TD  class=title5>收费银行账号</TD>
         <TD  class=input5><Input class= "common wid" name=InBankAccNo id=InBankAccNo > </TD>
          <TD  class=title5>交费金额</TD>
          <TD  class=input5> <Input class= "common wid" name=PayMoney id=PayMoney > </TD>
          
                        <!--td class="title">&nbsp;</td-->
                        <!--td class="input">&nbsp;</td-->
        </TR>
       </table>
            <INPUT VALUE=" 查 询 " class =cssButton TYPE=button onclick="initQuery();">

         
       </div>
  <!-- Add By QianLy --------------->


   <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divCustomer1);">
            </td>
            <td class= titleImg>
                 客户既往收费信息
            </td>
        </tr>
    </table>
     <Div id= "divCustomer1" style= "display:''">
        <table  class= common>
            <tr  class= common>
                <td><span id="spanPayGrid"></span></td>
            </tr>
        </table>
            <div id="divTurnPagePayGrid" align="center" style="display:'none'">
                <input type="button" class="cssButton90" value="首  页" onclick="turnPagePayGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPagePayGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPagePayGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPagePayGrid.lastPage()">
            </div>
    </div>
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
<br/><br/><br/><br/>
</body>
</html>
