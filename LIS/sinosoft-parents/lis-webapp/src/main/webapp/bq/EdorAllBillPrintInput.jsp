<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<html>
<%
//程序名称：EdorAllBillPrintInput.jsp
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
<SCRIPT src="./EdorAllBillPrint.js"></SCRIPT>
<LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
<LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
<LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
<%@include file="EdorAllBillPrintInit.jsp"%>
<title>保全操作统计一览表 </title>
</head>
<body  onload="initForm();" >
  <form  action="./EdorAllBillPrintSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <!-- 查询部分 -->
<table >
  <tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" >
            </TD>

    <td class= titleImg>
    保全操作统计一览表
    </td> 
   </tr> 
 </table> 
   <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common align=center>
       <TR  class= common>
          <td class="title5">渠道</td>
          <td class="input5">
              <Input class="codeno" name=SaleChnl  id=SaleChnl 
              CodeData="0|^02|个人营销^03|银行代理^05|专业代理^07|联办代理^10|收展业务"   
             style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])"
              onDblClick="showCodeListEx('SaleChnl', [this,SaleChnlName],[0,1])"
               onKeyUp="showCodeListKeyEx('SaleChnl', [this,SaleChnlName],[0,1])" ><input class="codename" name=SaleChnlName readonly=true>
          </td>
          <td class="title5">保全项目</td>
          <td class="input5">
          <input type="text" class="codeno" name="EdorType" id="EdorType" 
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeList('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)"
           ondblclick="showCodeList('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)"
            onKeyUp="showCodeListKey('PEdorType',[this,EdorTypeName],[0,1],null,SQL,1,null)"><input type="text" class="codename" name="EdorTypeName" readonly>
          </td>
 
       </TR>
    </table>
<div id = "divDate" style= "display: ''">
 <table  class= common align=center>
       <TR>
          <TD  class= title5>统计起期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" name="StartDate"  onClick="laydate({elem: '#StartDate'});" id="StartDate"><span class="icon"><a onClick="laydate({elem: '#StartDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span></TD>
          <TD  class= title5>统计止期<font color=red> *</font></TD>
          <TD  class= input5><input class="coolDatePicker" dateFormat="short" id="EndDate"  name="EndDate" onClick="laydate({elem:'#EndDate'});"> <span class="icon"><a onClick="laydate({elem: '#EndDate'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>  </TD>
       </TR>
 </table>
</div>
 <table  class= common align=center>
       <TR>
          <TD  class= title5>管理机构<font color=red> *</font></TD>
          <TD  class= input5>
          	<Input class="common wid" name=ManageCom id=ManageCom 
             style="background:url(../common/images/guanliyuan-bg.png) no-repeat right center" 
           onclick="showCodeList('Station',[this],[0],null,codeSql,'1')" 
            onDblClick="showCodeList('Station',[this],[0],null,codeSql,'1')" 
            onKeyUp="showCodeListKey('Station',[this],[0],null,codeSql,'1')"></TD>
          <TD  class= title5>操作员</TD>
          <TD  class= input5><input type="text" class="common wid" name="UserCode"></TD>  
       </TR>
       <TR>
       	  <TD  class= title5>保全批改状态</TD>
					<td class=input5 >
						<input type="text" class="codeno" name="EdorState"
                        style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
                         onclick="showCodeList('EdorState',[this,EdorStateName],[0,1] ,null,tSQL,1,null)"
                         ondblclick="showCodeList('EdorState',[this,EdorStateName],[0,1] ,null,tSQL,1,null)"
                          onKeyUp="showCodeListKey('EdorState',[this,EdorStateName],[0,1],null,tSQL,1,null)"><input type="text" class="codename" name="EdorStateName" readonly>
					</td>  
          <td class="title5">保全收费标识</td>
          <td class="input5">
              <Input class="codeno" name=PayFlag CodeData="0|^1|已收费^2|未收费"
              style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('PayFlag', [this,PayFlagName],[0,1])" 
               onDblClick="showCodeListEx('PayFlag', [this,PayFlagName],[0,1])" 
               onKeyUp="showCodeListKeyEx('PayFlag', [this,PayFlagName],[0,1])" ><input class="codename" name=PayFlagName readonly=true>
          </td>					             
        </TR>  
        <TR>
          <TD  class= title5>扫描标识</TD>
          <TD  class= input5>
          	<Input type="text" class="codeno" name=ScanFlag CodeData="0|^01|已扫描^02|未扫描"
            style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('ScanFlag', [this,ScanFlagName],[0,1])" 
             onDblClick="showCodeListEx('ScanFlag', [this,ScanFlagName],[0,1])" 
             onKeyUp="showCodeListKeyEx('ScanFlag', [this,ScanFlagName],[0,1])"><input type="text" class="codename" name=ScanFlagName readonly=true>
          </TD>
          <TD  class= title5>报表类型<font color=red> *</font></TD>
          <td class="input5">
              <Input class="codeno" name=RePortFlag
               CodeData="0|^1|退保清单^2|契撤清单^3|红利给付清单^4|保全未收费清单^5|保全未确认清单^6|档案扫描清单"
               style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
             onclick="showCodeListEx('RePortFlag', [this,RePortFlagName],[0,1])"
                onDblClick="showCodeListEx('RePortFlag', [this,RePortFlagName],[0,1])"
                 onKeyUp="showCodeListKeyEx('RePortFlag', [this,RePortFlagName],[0,1])" ><input class="codename" name=RePortFlagName readonly=true>
          </td>			          
       </TR>               
</table>
</div></div>
<br>
   <!-- <p>
      <INPUT VALUE=" 打印清单 " class= cssButton TYPE=button onClick="printBill();">
    </p>-->
    <a href="javascript:void(0);" class="button"onClick="printBill();">打印清单</a>

    <input type="hidden" name="fmtransact">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>-
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and code like #"+<%=tGI.ComCode%>+"%#";	
	var tSQL=" 1 and code not in (#6#,#8#,#b#,#7#)"; // 状态不支持
	var SQL = "1  and edorcode in (select code from ldcode where codetype=#edortype#)";
</script>
;