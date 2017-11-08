<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
//程序名称：
//程序功能：
//创建日期：2003-1-22
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
  <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
  <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
  <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
  <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
  <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
  <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
  <SCRIPT src="AllPBqQuery.js"></SCRIPT>
  <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
  <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
  <%@include file="../sys/AllPBqQueryInit.jsp"%>
  <title>保全查询</title>
</head>
<body  onload="initForm();">
  <form action="./AllPBqQuerySubmit.jsp" method=post name=fm id=fm target="fraSubmit" >
    <!-- 保单信息部分 -->
  <table class= common border=0 width=100%>
    <tr>
    <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage

(this,divInvAssBuildInfo);">
            </TD>
            <td class= titleImg align= center>
                请输入个人保全查询条件：
            </td>
        </tr>
    </table>
 <div id="divInvAssBuildInfo">
       <div class="maxbox1" >
    <table  class= common>
        <TR  class= common>
         <td class=title5>
            保全受理号
        </td>
        <td class= input5>
            <Input type="input" class="common wid" name=EdorAcceptNo>
        </td>
          <td class=title5>
                    号码类型
          </td>
          <td class= input5><Input class="codeno" name=OtherNoType id=OtherNoType
          style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('edornotype',[this,OtherNoName],[0,1])"
           onDblClick="showCodeList('edornotype',[this,OtherNoName],[0,1])"
            onKeyUp="showCodeListKey('edornotype',[this,OtherNoName],[0,1])"><input class=codename name=OtherNoName readonly=true></td>
         </TR>
         <TR  class= common>
         <td class=title5>
                    客户/保单号
         </td>
         <td class= input5>
         <Input class="common wid"   name=OtherNo>
         </td>
      
          <!--TD  class= title> 险种编码 </TD>
          <TD  class= input> <Input class="code" name=RiskCode ondblclick="return showCodeList('RiskCode',[this]);" onkeyup="return showCodeListKey('RiskCode',[this]);"> </TD-->
          
          <td class=title5>
                    申请人姓名
                </td>
                <td class= input5>
                    <Input type="input" class="common wid"  name=EdorAppName>
                </td>
                </TR>
         <TR  class= common>
           <td class=title5>
                    申请方式
                </td>
                <td class= input5><Input class="codeno" name=AppType 
                style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" 
                onDblClick="showCodeList('edorapptype',[this,AppTypeName],[0,1])" 
                onKeyUp="showCodeListKey('edorapptype',[this,AppTypeName],[0,1])"><input class=codename name=AppTypeName readonly=true></td>
                <TD  class= title5>
                    保全申请日期
                </TD>
                <TD  class= input5>
                    <Input class="multiDatePicker laydate-icon"   onClick="laydate({elem: '#EdorAppDate'});"dateFormat="short" 

name=EdorAppDate id=EdorAppDate><span class="icon"><a onClick="laydate({elem: '#EdorAppDate'});"><img 

src="../common/laydate/skins/default/icon.png" /></a></span>
                </TD>
        </TR>
         <TR  class= common>
         <td class=title5>
            批单号
        </td>
        <td class= input5>
            <Input type="input" class="common wid"  name=EdorNo>
        </td>
          <TD  class= title5>   管理机构  </TD>
        <TD  class= input5><Input class= codeno name=ManageCom   id=ManageCom 
        style="background:url(../common/images/select--bg_03.png) no-repeat right center" 
            onclick="showCodeList('station',[this,ManageComName],[0,1])" 
        onDblClick="showCodeList('station',[this,ManageComName],[0,1])" 
        onKeyUp="showCodeListKey('station',[this,ManageComName],[0,1])"><input class="codename" name=ManageComName readonly=true></TD>
          
         
         </TR>


    </table>
    </div>
    </div>
<!-- <a href="javascript:void(0);" class="button"onClick="easyQueryClick();">查   询</a> -->

          <INPUT VALUE=" 查 询 " class=cssButton  TYPE=button  onClick="easyQueryClick();">

    <table>
        <tr>
            <td class=common>
                <IMG  src= "../common/images/butExpand.gif" style= "cursor:hand;" OnClick= "showPage(this,divLPEdorMain1);">
            </td>
            <td class= titleImg>
                 保全信息
            </td>
        </tr>
    </table>
    <Div  id= "divLPEdorMain1" style= "display: ''" align = center>
        <table  class= common>
            <tr  class= common>
                <td text-align: left colSpan=1>
                    <span id="spanPolGrid" >
                    </span>
                </td>
            </tr>
   </table>
      <INPUT VALUE="首  页" class=cssButton90 TYPE=button onClick="turnPage1.firstPage();HighlightByRow()">
      <INPUT VALUE="上一页" class=cssButton91 TYPE=button onClick="turnPage1.previousPage();HighlightByRow()">
      <INPUT VALUE="下一页" class=cssButton92 TYPE=button onClick="turnPage1.nextPage();HighlightByRow()">
      <INPUT VALUE="尾  页" class=cssButton93 TYPE=button onClick="turnPage1.lastPage();HighlightByRow()">

        <input type=hidden name=Transact >
    </div>
    <br>
     <a href="javascript:void(0);" class="button" onClick="gotoDetail();">保全明细</a>
     <a href="javascript:void(0);" class="button" onClick="queryNotice();">问题件查询</a>
     
 <a href="javascript:void(0);" class="button"onClick="MissionQuery();">保全操作轨迹</a>
 
 <a href="javascript:void(0);" class="button" onClick="showNotePad();">记事本查看</a>
    <!--<INPUT class=cssButton VALUE=" 保全明细"  TYPE=button onClick="gotoDetail();">
    <INPUT class=cssButton VALUE=" 问题件查询"  TYPE=button onClick="queryNotice();">
    <INPUT VALUE="保全操作轨迹" class=cssButton TYPE=button onClick="MissionQuery();">
    <INPUT class= cssButton TYPE=button VALUE="记事本查看" onClick="showNotePad();">-->

        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="LoginManageCom">
  </form>
  <span id="spanCode"  style="display: none; position:absolute; slategray"></span>
</body>
</html>
