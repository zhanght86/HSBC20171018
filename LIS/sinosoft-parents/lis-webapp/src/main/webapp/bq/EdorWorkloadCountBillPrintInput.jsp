<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//程序名称：EdorWorkloadCountBillPrintInput.jsp
//程序功能：保全清单在线打印控制台
//创建日期：2005-08-08 16:20:22
//创建人  ：liurx
//更新记录：  更新人    更新日期      更新原因/内容
%>
<%
    GlobalInput tGI = new GlobalInput();
    tGI = (GlobalInput)session.getValue("GI");
%>
<script>
    var managecom = "<%=tGI.ManageCom%>"; //记录管理机构
    var comcode = "<%=tGI.ComCode%>"; //记录登陆机构
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
<SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
<SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
<SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
<SCRIPT src="../common/javascript/MultiCom.js"></SCRIPT>
<SCRIPT src="./EdorWorkloadCountBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorWorkloadCountBillPrintInit.jsp"%>
<title>保全作业量统计表 </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorWorkloadCountBillPrintSave.jsp" method=post name=fm  id=fm target="fraSubmit">
    <!-- 查询部分 -->

    <table class= common border=0 width=100%>
        <tr>
         <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <td class= titleImg align= center>请输入查询条件：</td>
    </tr>
    </table>
     <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
       <TR  class= common>
          <td class="title5">渠道</td>
          <td class="input5">
              <Input class="codeno" name=SaleChnl   id=SaleChnl 
              CodeData="0|^02|个人营销^03|银行代理^05|专业代理^07|联办代理^10|收展业务"
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
               onDblClick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
               onKeyUp="showCodeListKeyEx('SaleChnl', [this,SaleChnlName],[0,1])" ><input class="codename" name=SaleChnlName id=SaleChnlName readonly=true>
          </td>
          <TD  class= title5>管理机构<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class= code name=ManageCom  id=ManageCom
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this])"  
            onDblClick="showCodeList('Station',[this])" 
            onKeyUp="showCodeListKey('Station',[this])">
         </TD>
     
 
       </TR>
    </table>
<div id = "divDate" style= "display: ''">
 <table  class= common align=center>
       <TR>
          <TD  class= title5>统计起期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" > <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>
</TD>
          <TD  class= title5>统计止期<font color=red> *</font></TD>
          <TD  class= input5> <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
       </TR>
 </table>
</div>
 <table  class= common align=center>
       <TR>
          <TD  class= title5>报表类型<font color=red> *</font></TD>
          <td class="input5">
              <Input class="codeno" name=NewWRePortFlag idNewWRePortFlag 
              CodeData="0|^1|月度工作量统计^2|保全人员月度工作量明细^3|各机构不同项目对应工作量"
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('NewWRePortFlag', [this,NewRePortFlagName],[0,1])"
               onDblClick="showCodeListEx('NewWRePortFlag', [this,NewRePortFlagName],[0,1])"
                onKeyUp="showCodeListKeyEx('NewWRePortFlag', [this,NewRePortFlagName],[0,1])" ><input class="codename" name=NewRePortFlagName id=NewRePortFlagName readonly=true>
          </td>	
          <TD  class= title5>统计粒度</TD>
          <td class="input5">
              <Input class="codeno" name=ComGrade id=ComGrade
               CodeData="0|^1|二级机构^2|三级机构"
               style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('ComGrade', [this,ComGradeName],[0,1])"
               onDblClick="showCodeListEx('ComGrade', [this,ComGradeName],[0,1])"
                onKeyUp="showCodeListKeyEx('ComGrade', [this,ComGradeName],[0,1])" ><input class="codename" name=ComGradeName id=ComGradeName readonly=true>
          </td>	
	 
       </TR>
  </table>  
<div id = "divItem" style= "display: 'none'">
    <table  class= common align=center>
       <TR  class= common>
          <td class="title5">保全项目</td>
          <td class="input5"><input type="text" class="codeno" name="EdorType"   id="EdorType" 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeList('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)" 
           ondblclick="showCodeList('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)" 
           onKeyUp="showCodeListKey('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)"><input type="text" class="codename" name="EdorTypeName" readonly></td>

          <td class="title5"></td>
          <td class="input5"></td>
  
       </TR>
    </table> 
</div> 
</div></div>       
<br>
   <!-- <p>
      <INPUT VALUE=" 打印报表 " class= cssButton TYPE=button onClick="printBill();">
    </p>-->
    <a href="javascript:void(0);" class="button"onClick="printBill();">打印报表</a>
    <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
		var SQL = "1  and edorcode in (select code from ldcode where codetype=#edortype# )";
</script>
