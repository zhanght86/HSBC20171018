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
    <SCRIPT src="../common/javascript/EasyQuery.js"></SCRIPT>
    <SCRIPT src="../common/javascript/VerifyInput.js"></SCRIPT>
    <SCRIPT src="../common/cvar/CCodeOperate.js"></SCRIPT>
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>
    <SCRIPT src="../common/easyQueryVer3/EasyQueryCache.js"></SCRIPT>
    <SCRIPT src="./ActivityDef.js"></SCRIPT>
    <LINK href="../common/css/Project.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/Project3.css" rel=stylesheet type=text/css>
    <LINK href="../common/css/mulLine.css" rel=stylesheet type=text/css>
    <%@include file="ActivityDefInit.jsp" %>
</head>
<body onLoad="initForm();initElementtype();">
<form action="./ActivityDefSave.jsp" method=post name=fm  id=fm  target="fraSubmit">
    <table><!--页面公共部分-->
        <TR>
            <TD class="common">
                <IMG src="../common/images/butExpand.gif" style="cursor:hand;" OnClick="showPage(this,divInvAssBuildInfo);">
            </TD>
            <TD class=titleImg>
                查询条件
            </TD>
        </TR>
    </table>
    <div id="divInvAssBuildInfo">
     <div class="maxbox1">
        <table class=common>
            <TR class=common>
                <TD class=title5>
                    业务类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BusiTypeQ  id=BusiTypeQ 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="功能名称|notnull"
                      onclick="return showCodeList('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'180');" 
                    onDblClick="return showCodeList('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'180');" 
                    onKeyUp="return showCodeListKey('busitype',[this,BusiTypeNameQ],[0,1],null,null,null,1,'180');"><input class=codename name=BusiTypeNameQ readonly=TRue elementtype=nacessary>
                </TD>            
                <TD class=title5>
                    活动代码
                </TD>
                <TD class=input5>
                    <Input class=codeno name=ActivityIDQ  id=ActivityIDQ
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  verify="功能名称|notnull"
                      onclick="checkBusiType(); return showCodeList('queryactivityid',[this,ActivityNameQ],[0,1],null,fm.BusiTypeQ.value,'BusiType','1');"
                    onDblClick="checkBusiType(); return showCodeList('queryactivityid',[this,ActivityNameQ],[0,1],null,fm.BusiTypeQ.value,'BusiType','1');"
                     onKeyUp="checkBusiType(); return showCodeListKey('queryactivityid',[this,ActivityNameQ],[0,1],null,fm.BusiTypeQ.value,'BusiType','1');"><input class=codename name=ActivityNameQ readonly=TRue>
                </TD>
            </TR>
        </table>
        </div>

       <!-- <INPUT align=right VALUE="查  看" TYPE=button class="cssButton" onClick="queryClick()">-->
       <br>
       <a href="javascript:void(0);" class="button"onClick="queryClick()">查  看</a>
 <br><br>
        <table class=common>
            <TR class=common>
                <TD text-align: left colSpan=1>
   				<span id="spanTICollectGrid">
   				</span>
                </TD>
            </TR>
        </table>
        <!--<INPUT VALUE="首页"   class="cssButton90" TYPE=button onClick="turnPage.firstPage();">
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
     <div class="maxbox" >
        <table class="common">
            <TR class="common">
                <TD class=title5>
                    活动代码
                </TD>
                <TD class=input5>
                    <input class="common wid"  name=ActivityID value="" verify="活动代码|notnull&&len<=10" elementtype=nacessary ><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <TD class=title5>
                    活动名称
                </TD>
                <TD class=input5>
                    <input class="common wid"  name=ActivityName value="" verify="活动名称|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    功能名称
                </TD>
                <TD class=input5>
                    <Input class=codeno name=FunctionID id=FunctionID
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('functionid',[this,FunctionIDName],[0,1],null,null,null,1,'300');" 
                     onDblClick="return showCodeList('functionid',[this,FunctionIDName],[0,1],null,null,null,1,'300');" 
                     onKeyUp="return showCodeListKey('functionid',[this,FunctionIDName],[0,1],null,null,null,1,'300');" 
                     verify="功能名称|notnull"><input class=codename name=FunctionIDName readonly=TRue verify="功能名称|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <TD class=title5>
                    业务类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BusiType  id=BusiType 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'180');" 
                    onDblClick="return showCodeList('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'180');" 
                    onKeyUp="return showCodeListKey('busitype',[this,BusiTypeName],[0,1],null,null,null,1,'180');"
                     verify="业务类型|notnull" readonly=True><input class=codename name=BusiTypeName readonly=TRue verify="业务类型|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                    <!--<Input class=codeno name=BusiType  verify="业务类型|notnull" readonly=True><input class=codename name=BusiTypeName readonly=TRue verify="业务类型|notnull" elementtype=nacessary>-->
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    执行属性
                </TD>
                <TD class=input5>
                    <Input class=codeno name=IsNeed  id=IsNeed 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('isneed',[this,IsNeedName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('isneed',[this,IsNeedName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('isneed',[this,IsNeedName],[0,1],null,null,null,1,'300');" 
                    verify="执行属性|notnull"><input class=codename name=IsNeedName readonly=TRue verify="执行属性|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <TD class=title5>
                    顺序属性
                </TD>
                <TD class=input5>
                    <Input class=codeno name=ActivityFlag  id=ActivityFlag
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('activityflag',[this,ActivityFlagName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('activityflag',[this,ActivityFlagName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('activityflag',[this,ActivityFlagName],[0,1],null,null,null,1,'300');" 
                    verify="顺序属性|notnull" ><input class=codename name=ActivityFlagName readonly=TRue verify="顺序属性|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
            </TR>
            
            <TR class=common>
                <TD class=title5>
                    重要等级
                </TD>
                <TD class=input5>
                    <Input class=codeno name=ImpDegree  id=ImpDegree 
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('impdegree',[this,ImpDegreeName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('impdegree',[this,ImpDegreeName],[0,1],null,null,null,1,'300');" 
                    onKeyUp="return showCodeListKey('impdegree',[this,ImpDegreeName],[0,1],null,null,null,1,'300');" 
                    verify="重要等级|notnull" ><input class=codename name=ImpDegreeName readonly=TRue verify="重要等级|notnull" elementtype=nacessary><font size=1 color='#ff0000'><b>*</b></font>
                </TD>
                <!-- jiyongtian 增加聚合模式 -->
                 <TD class=title5>
                    聚合模式
                </TD>
                <TD class=input5>
                    <Input class=codeno name=Together id=Together
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('Together',[this,TogetherName],[0,1],null,null,null,1,'300');"  
                    ondblclick="return showCodeList('Together',[this,TogetherName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('Together',[this,TogetherName],[0,1],null,null,null,1,'300');" ><input class=codename name=TogetherName  readonly=TRue>
                </TD>
            </TR>            
            
            <TR class=common>
                <TD class=title5>
                    进入前动作类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BeforeInitType  id=BeforeInitType 
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('inittype',[this,BeforeInitTypeName],[0,1],null,null,null,1,'300');"
                    onDblClick="return showCodeList('inittype',[this,BeforeInitTypeName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('inittype',[this,BeforeInitTypeName],[0,1],null,null,null,1,'300');"><input class=codename name=BeforeInitTypeName readonly=TRue>
                </TD>
                <TD class=title5>
                    进入前动作名称
                </TD>
                <TD class=input5>
                     <input class="common wid" name=BeforeInit value="" >
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    进入后动作类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=AfterInitType  id=AfterInitType
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('inittype',[this,AfterInitTypeName],[0,1],null,null,null,1,'300');"
                    onDblClick="return showCodeList('inittype',[this,AfterInitTypeName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('inittype',[this,AfterInitTypeName],[0,1],null,null,null,1,'300');"><input class=codename name=AfterInitTypeName readonly=TRue>
                </TD>
                <TD class=title5>
                    进入后动作名称
                </TD>
                <TD class=input5>
                     <input class="common wid" name=AfterInit value="" >
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    结束前动作类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=BeforeEndType id=BeforeEndType
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('inittype',[this,BeforeEndTypeName],[0,1],null,null,null,1,'300');" 
                    onDblClick="return showCodeList('inittype',[this,BeforeEndTypeName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('inittype',[this,BeforeEndTypeName],[0,1],null,null,null,1,'300');"><input class=codename name=BeforeEndTypeName readonly=TRue>
                </TD>
                <TD class=title5>
                    结束前动作名称
                </TD>
                <TD class=input5>
                     <input class="common wid" name=BeforeEnd value="" >
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                    结束后动作类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=AfterEndType id=AfterEndType
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('inittype',[this,AfterEndTypeName],[0,1],null,null,null,1,'300');" 
                     onDblClick="return showCodeList('inittype',[this,AfterEndTypeName],[0,1],null,null,null,1,'300');" 
                     onKeyUp="return showCodeListKey('inittype',[this,AfterEndTypeName],[0,1],null,null,null,1,'300');"><input class=codename name=AfterEndTypeName readonly=TRue>
                </TD>
                <TD class=title5>
                    结束后动作名称
                </TD>
                <TD class=input5>
                     <input class="common wid" name=AfterEnd value="" >
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                   创建动作类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=CreateActionType id=CreateActionType
                     style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('inittype',[this,CreateActionTypeName],[0,1],null,null,null,1,'300');"
                    onDblClick="return showCodeList('inittype',[this,CreateActionTypeName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('inittype',[this,CreateActionTypeName],[0,1],null,null,null,1,'300');"><input class=codename name=CreateActionTypeName readonly=TRue>
                </TD>
                <TD class=title5>
                    创建动作名称
                </TD>
                <TD class=input5>
                     <input class="common wid" name=CreateAction value="" >
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                 分配动作类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=ApplyActionType  id=ApplyActionType
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('inittype',[this,ApplyActionTypeName],[0,1],null,null,null,1,'300');"
                    onDblClick="return showCodeList('inittype',[this,ApplyActionTypeName],[0,1],null,null,null,1,'300');"
                     onKeyUp="return showCodeListKey('inittype',[this,ApplyActionTypeName],[0,1],null,null,null,1,'300');"><input class=codename name=ApplyActionTypeName readonly=TRue>
                </TD>
                <TD class=title5>
                    分配动作名称
                </TD>
                <TD class=input5>
                     <input class="common wid" name=ApplyAction value="" >
                </TD>
            </TR>
            <TR class=common>
                <TD class=title5>
                  删除动作类型
                </TD>
                <TD class=input5>
                    <Input class=codeno name=DeleteActionType id=DeleteActionType
                    style="background:url(../common/images/select--bg_03.png) no-repeat right center"  
                     onclick="return showCodeList('inittype',[this,DeleteActionTypeName],[0,1],null,null,null,1,'300');"
                     onDblClick="return showCodeList('inittype',[this,DeleteActionTypeName],[0,1],null,null,null,1,'300');"
                      onKeyUp="return showCodeListKey('inittype',[this,DeleteActionTypeName],[0,1],null,null,null,1,'300');"><input class=codename name=DeleteActionTypeName readonly=TRue>
                </TD>
                <TD class=title5>
                    删除动作名称
                </TD>
                <TD class=input5>
                     <input class="common wid" name=DeleteAction value="" >
                </TD>
            </TR>    
        </table>
        </div>
     <Table class = common >
        <TR class=common >
        <TD  class= titleImg>
           备注说明
        </TD>
        </TR>
        <TR class = common>
        <TD  class=input><textarea name="ActivityDesc" cols="169%" rows="4" width=100% class="common"></textarea></TD>
        </TR>
      </table>
    </div>
    <!--<INPUT align=right VALUE="参数定义" TYPE=button class="cssButton" onClick="DefParamClick()">-->
    <!--INPUT align=right VALUE="转移条件定义" TYPE=button class="cssButton" onclick="DefConditionClick()"-->
    <!--<INPUT align=right VALUE="保  存" TYPE=button class="cssButton" onClick="SaveClick()">
    <INPUT align=right VALUE="修  改" TYPE=button class="cssButton" onClick="ModifyClick()">
    <INPUT align=right VALUE="删  除" TYPE=button class="cssButton" onClick="DeleteClick()">-->
    <br>
    <a href="javascript:void(0);" class="button"onClick="DefParamClick()">参数定义</a>
    <a href="javascript:void(0);" class="button"onClick="SaveClick()">保  存</a>
    <a href="javascript:void(0);" class="button"onClick="ModifyClick()">修  改</a>
    <a href="javascript:void(0);" class="button"onClick="DeleteClick()">删  除</a>
    <INPUT type="hidden" value="" name="hiddenActivityID" >
     <br><br><br><br>  
</form>


<span id="spanCode" style="display: none; position:absolute; slategray"></span>
</body>
</html> 
