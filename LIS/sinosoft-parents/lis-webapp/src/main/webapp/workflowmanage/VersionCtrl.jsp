<%@include file="../common/jsp/UsrCheck.jsp" %>
<%@page import="com.sinosoft.lis.pubfun.*" %>
<html>
<%/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */
    GlobalInput tG = new GlobalInput();
    tG = (GlobalInput) session.getValue("GI");%>
<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<head>
    <SCRIPT src="../common/javascript/Common.js"></SCRIPT>
    <SCRIPT src="../common/javascript/MulLine.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./VersionCtrl.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
     <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="VersionCtrlInit.jsp" %>
</head>
<body onLoad="initForm();initElementtype();">
<form action="./VersionCtrlSave.jsp" method=post name=fm id=fm target="fraSubmit">
    <table><!--页面公共部分-->
        <TR>
            <TD class=common>
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class=titleImg>
                查询条件
            </TD>
        </TR>
    </table>
    <div id="divInvAssBuildInfo">
    <div class="maxbox1">
    <table  class= common align=center>
        <table class=common>
            <TR class=common>
                <TD class=title5>
                    业务类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BusiTypeQ  id=BusiTypeQ
                    style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
                     onclick="showCodeList('busitype',[this,BusiTypeNameQ],[0,1]);" 
                    onDblClick="return showCodeList('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'300');"><input class=codename name=BusiTypeNameQ readonly=TRue elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>            
                <TD class=title5>
                    过程名称
                </TD>
                <TD class=input5>
                    <Input class=codeno name=ProcessIDQ 
                    style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
                     onclick="showCodeList('busiproxml',[this,ProcessNameQ],[0,1]);" 
                    onDblClick=" checkBusiType();  return showCodeList('busiproxml',[this,ProcessNameQ],[0,1],null,fm.BusiTypeQ.value,'BusiType','1');"
                     onKeyUp="checkBusiType(); return showCodeListKey('busiproxml',[this,ProcessNameQ],[0,1],null,fm.BusiTypeQ.value,'BusiType','1');"><input class=codename name=ProcessNameQ readonly=TRue>
                </TD>

            </TR>
        </table>
         </div>
         <br>
       <!-- <INPUT align=right VALUE="查  看" TYPE=button class="cssButton" onClick="queryClick()">-->
       <a href="javascript:void(0);" class="button" onClick="queryClick()">查  看</a>
<br><br>

        <table class=common>
            <TR class=common>
                <TD style=" text-align: left" colSpan=1>
   				<span id="spanTICollectGrid">
   				</span>
                </TD>
            </TR>
        </table>
        
       <!-- <INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
        <INPUT VALUE="上一页" class="cssButton91" TYPE=button onClick="turnPage.previousPage();">
        <INPUT VALUE="下一页" class="cssButton92" TYPE=button onClick="turnPage.nextPage();">
        <INPUT VALUE="尾页"   class="cssButton93" TYPE=button onClick="turnPage.lastPage();">-->
    </div>


    <br> <br>
    <table><!--页面公共部分-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divdetailinfo);">
            </TD>
            <TD class=titleImg>
                信息录入
            </TD>
        </TR>
    </table>
    <div id="divdetailinfo">
    <div class="maxbox1" >
        <table class="common">
            <TR class=common>
                <TD class=title5>
                    状态
                </TD>
                <TD class=input5>
                    <Input class=codeno name=ValiFlagCode 
                    style="background:url(../common/images/select--bg_03.png) 		no-repeat right center" 
                     onclick="showCodeList('valiflag',[this,ValiFlagName],[0,1]);" 
                    onDblClick="return showCodeList('valiflag',[this,ValiFlagName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('valiflag',[this,ValiFlagName],[0,1],null,null,null,1,'300');"
                      verify="状态|notnull"><input class=codename name=ValiFlagName readonly=TRue verify="状态|notnull" elementtype=nacessary>
                </TD>
                 <TD class=input5>
                  </TD>
                   <TD class=input5>
                  </TD>
                
            </TR>
        </table>
      </div>   
    </div>
    <BR>
    <INPUT align=right VALUE="修  改" TYPE=button class="cssButton" onClick="ModifyClick()">
  <!-- <a href="javascript:void(0);" class="button"onClick="ModifyClick()">修  改</a>-->

    <INPUT type="hidden" value="" name="hiddenProcessID" >
    <br><br><br><br>
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
