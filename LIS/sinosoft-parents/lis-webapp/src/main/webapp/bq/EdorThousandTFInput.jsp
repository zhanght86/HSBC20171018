<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//程序名称：EdorAllCTBillPrintInput.jsp
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
<SCRIPT src="./EdorThousandTF.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorThousandTFInit.jsp"%>
<title>保全系统处理超期提醒清单 </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorThousandTFSave.jsp" method=post name=fm  id=fm target="fraSubmit">
<Div id=DivFileDownload style="display:'none'">
      <A id=fileUrl href=""></A>
</Div>
    <!-- 查询部分 -->
<table >
  <tr>
    <td class="common">
      <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" >
   </td>
    <td class= titleImg>
保全系统处理超期提醒清单
    </td> 
   </tr> 
 </table> 
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
          <TD  class= title5>管理机构<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class="common wid" name=ManageCom id=ManageCom verify="管理机构|notnull"
            style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this],[0],null,codeSql,'1')" 
            onDblClick="showCodeList('Station',[this],[0],null,codeSql,'1')"
             onKeyUp="showCodeListKey('Station',[this],[0],null,codeSql,'1')"></TD>
          <td class="title5">渠道</td>
          <td class="input5">
              <Input class="codeno" name=SaleChnl id=SaleChnl CodeData="0|^02|个人营销^03|银行代理^05|专业代理^07|联办代理^10|收展业务" 
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
              onDblClick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])" 
              onKeyUp="showCodeListKeyEx('SaleChnl', [this,SaleChnlName],[0,1])" ><input class="codename" name=SaleChnlName readonly=true>
          </td>
 
       </TR>
    </table>
<div id = "divDate" style= "display: ''">
 <table  class= common align=center>
        <TR  class= common>   
          		<TD  class= title5 width="25%">统计起期</TD>
          		<TD  class= input5 width="25%">
                <input class="coolDatePicker" dateFormat="short" id="StartDate"  name="StartDate" onClick="laydate
({elem:'#StartDate'});" verify="统计起期|notnull&Date"> <span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img 
src="../common/laydate/skins/default/icon.png" /></a></span>

                
                 </TD>      
          		<TD  class= title5 width="25%">统计止期</TD>
          		<TD  class= input5 width="25%">
                 <input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});" verify="统计止期|notnull&Date"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>  
      	 </TR>
 </table>
</div>
</div>
</div>
 


    <!-- 确认对话框 初始化为0 点确认框的确定修改为1 计算完毕后重新初始话为0 --> 
	<INPUT VALUE="0" TYPE=hidden name=myconfirm> 
	<INPUT VALUE="" TYPE=hidden name=FileName> 
	<INPUT VALUE="" TYPE=hidden name=Url> 
	<input type=hidden id="fmtransact" name="fmtransact"> 
	<!--<INPUT VALUE="生成清单" class=cssButton name=compute TYPE=button onClick="return fmsubmit()"> 
	<INPUT VALUE="下载清单" class=cssButton name=download TYPE=button onClick="fileDownload()">-->
    <a href="javascript:void(0);" class="button"onClick="return fmsubmit()">生成清单</a>
    <a href="javascript:void(0);" class="button"onClick="fileDownload()">下载清单</a>



  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";
	var SQL = "1  and edorcode in (select code from ldcode where codetype=#edortype# and code in (#CT#,#XT#,#WT#,#PT#,#XS#))";
</script>
