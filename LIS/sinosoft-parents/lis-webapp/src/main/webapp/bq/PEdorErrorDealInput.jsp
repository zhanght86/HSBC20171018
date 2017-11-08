<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01
 * @date     : 2009-01-11, 2009-01-14
 * @direction: 保全问题件处理申请主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>保全抽检处理申请</title>
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
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="PEdorErrorDeal.js"></script>
    <%@ include file="PEdorErrorDealInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()">
    <form name="fm" id="fm" action="./PEdorErrorDealSubmit.jsp" method="post" target="fraSubmit">
        <!-- 共享队列查询折叠 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorAllSearch)"></td>
                <td class="titleImg">请输入查询条件</td>
            </tr>
        </table>
        <!-- 共享队列查询录入 -->
        <div id="divEdorAllSearch" style="display:''" class="maxbox1">
            <table class="common" >
                <tr class="common">
                    <td class="title">保全受理号</td>
                    <td class="input"><input type="text" class="common wid" name="EdorAcceptNo1" id="EdorAcceptNo1"></td>
                    <td class="title">保单号</td>
                    <td class="input"><input type="text" class="common wid" name="OtherNo1" id="OtherNo1"></td>
                    <td class="title">管理机构</td>
                    <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="ManageCom1" id="ManageCom1" ondblclick="showCodeList('station',[this,ManageComName1],[0,1],null,null,null,0,256)" onclick="showCodeList('station',[this,ManageComName1],[0,1],null,null,null,0,256)" onkeyup="showCodeListKey('station',[this,ManageComName1],[0,1],null,null,null,0,256)"><input class="codename" name="ManageComName1" id="ManageComName1" readonly></td>
              </tr>
                <tr class="common">
                    <td class="title">函件类型</td>
                    <td class="input"><input style="background:url(../common/images/select--bg_03.png) no-repeat right center" type="text" class="codeno" name="LetterType" id="LetterType" ondblclick="showCodeList('lettertype',[this,LetterTypeName],[0,1],null,codeSql,1,0,256)" onclick="showCodeList('lettertype',[this,LetterTypeName],[0,1],null,codeSql,1,0,256)" onkeyup="showCodeListKey('LetterType',[this,LetterTypeName],[0,1],null,codeSql,1,0,256)"><input type="text" class="codename" name="LetterTypeName" id="LetterTypeName" readonly></td>
                    <td class="title">受理员</td>
                    <td class="input"><input type="text" class="common wid" name="AcceptOperator1" id="AcceptOperator1"></td>
                    <td class="title">受理日期</td>
                    <td class="input"><!--<input type="text" class= "multiDatePicker" dateFormat="short" name="MakeDate1">-->
                    <Input class="coolDatePicker" onClick="laydate({elem: '#MakeDate1'});" verify="有效开始日期|DATE" dateFormat="short" name=MakeDate1 id="MakeDate1"><span class="icon"><a onClick="laydate({elem: '#MakeDate1'});"><img src="../common/laydate/skins/default/icon.png" /></a></span>
                    </td>
                </tr>
            </table></div>
            <!-- 查询数据操作按钮 -->
            <!--<input type="button" class="cssButton" value=" 查 询 " onclick="queryAllGrid(1)">-->
        <a href="javascript:void(0);" class="button" onClick="queryAllGrid(1)">查    询</a>
        <!-- 共享队列折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divAllList)"></td>
                <td class="titleImg">函件列表</td>
           </tr>
        </table>
        <!-- 共享队列结果展现 -->
        <div id="divAllList" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanAllGrid"></span></td>
                </tr>
            </table>
            <!-- 共享队列结果翻页 -->
            <!--<div id="divTurnPageAllGrid" align="center" style= "display:'none'">
                <input type="button" class="cssButton" value="首  页" onclick="turnPageAllGrid.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPageAllGrid.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPageAllGrid.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPageAllGrid.lastPage()">
            </div>-->
        </div>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="LoginOperator">
        <input type="hidden" name="LoginManageCom">
        <input type="hidden" name="PrtSerNo">
      <Div  id= "divForceBack" style= "display: none">
       <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divAppCancelInfo)"></td>
                <td class="titleImg">回销原因</td>
           </tr>
        </table>
        <Div  id= "divAppCancelInfo" style= "display: ''" class="maxbox1">
            <table  class= common>
                <tr class=common>
                    <td class=title>回销原因</td>
                    <td class=input><Input style="background:url(../common/images/select--bg_03.png) no-repeat right center" class="codeno" name=MCanclReason id=MCanclReason verify="撤销原因|NotNull&Code:edorcancelmreason" ondblclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onclick="return showCodeList('edorcancelmreason',[this,SCanclReasonName],[0,1]);" onkeyup="return showCodeListKey('edorcancelmreason',[this,SCanclReasonName],[0,1]);"><input class=codename name=SCanclReasonName id=SCanclReasonName readonly></td>
                    <td class="title"><Div id="divCancelMainReason" style="display:none"> 具体原因 </Div></td>
                    <td class="input"><Div id="divApproveMofiyReasonInput" style="display:none"><input class="CodeNo" CodeData=""  name="SCanclReason" verify="修改原因|Code:edorcancelsreason" ondblclick="return showCodeListEx('edorcancelsreason',[this,MCanclReasonName],[0,1])" onkeyup="return showCodeListKeyEx('edorcancelsreason',[this,MCanclReasonName],[0,1])"><input class="CodeName" name="MCanclReasonName" readonly></Div></td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
            </table>
        </DIV>
         <Div  id= "divAppCancelA" style= "display: none">            
            <table  class= common>
                <tr class=common>
                    <td class=title>A．	客户原因</td>
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="A01">客户反悔
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="A02" >家庭矛盾
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="A03" >客户申请
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelB" style= "display: none">            
           <table  class= common>
                <tr class=common>
                    <td class=title>B．	代办问题</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="B01">越权办理
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="B02" >代办存在误解
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="B03" >存在代签字问题
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelC" style= "display: none">            
           <table  class= common>
                <tr class=common>
                    <td class=title>C．	业务员原因</td>
                 
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="C01">越权代办
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="C02" >存在误导
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="C03" >存在代签字问题
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelD" style= "display: none">            
           <table  class= common>
                <tr class=common>
                    <td class=title>D．	系统问题</td>
                  
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="D01">系统报错
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="D02" >结果数据错误
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelE" style= "display: none">            
           <table  class= common>
                <tr class=common>
                    <td class=title>E．	公司原因</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E01">操作错误
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E02" >沟通存在误解
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E03">其他部门转办
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E04" >不符合公司相关办理规定
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E05">超期未确认
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E06" >有相同项目未结保全案件
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="E07" >有其他未结保全案件
              </TD>
             </tr>
           </table>
        </DIV>
         <Div  id= "divAppCancelF" style= "display: none">            
           <table  class= common>
                <tr class=common>
                    <td class=title>F．	司法配合</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="F01">司法配合
              </TD>
             </tr>
           </table>
        </DIV>
        <Div  id= "divAppCancelG" style= "display: none">     
        <table  class= common>
                <tr class=common>
                    <td class=title>G．	其他</td>
                   
                    <td class="input">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                    <td class="title">&nbsp;</td>
                    <td class="input">&nbsp;</td>
                </tr>
                
            </table>
            <table  class= common>
             <tr class=common>
             	<TD class=title colspan=6 >
               <input class=title name=SCanclReason id=SCanclReason TYPE=radio  value="G01">其他
              </TD>
             </tr>
           </table>
           <table  class= common>
           <tr class=common>
                    <TD class=title colspan=6 > 详细情况 </TD>
                </tr>
                <tr class=common>
                    <TD style="padding-left:16px" colspan=6 ><textarea name="CancelReasonContent" id=CancelReasonContent cols="180%" rows="4" witdh=100% class="common"></textarea></TD>
                </tr>
            </table>       
        </DIV>   
       </DIV>
       <Div  id= "divBack" style= "display: none">      
        <!-- 提交数据操作按钮 -->
        <!--<input type="button" class="cssButton" value="  逾期回销  " onclick="dealSpotCheck()">
        <input type="button" class="cssButton" value=" 保全明细查询 " onclick="viewEdorDetail()">
        <input type="button" class="cssButton" value="  正常回销 " onclick="dealCheck()">--><br>
        <a href="javascript:void(0);" class="button" onClick="dealSpotCheck();">逾期回销</a>
        <a href="javascript:void(0);" class="button" onClick="viewEdorDetail();">保全明细查询</a>
        <a href="javascript:void(0);" class="button" onClick="dealCheck();">正常回销</a>
        </DIV><br>
       
       <Div  id= "divForceBankBack" style= "display: none">       
        <!--<input type="button" class="cssButton" value=" 保全明细查询 " onclick="viewEdorDetail()">
        <input type="button" class="cssButton" value="  正常回销 " onclick="dealCheck()">-->
        <a href="javascript:void(0);" class="button" onClick="viewEdorDetail();">保全明细查询</a>
        <a href="javascript:void(0);" class="button" onClick="dealCheck();">正常回销</a>
       </DIV>  
        <input type="hidden" name="ActionFlag" id=ActionFlag>
        <input type="hidden" name="CancelReasonCode" id=CancelReasonCode> 
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span><br><br><br><br>
</body>
</html>
<script>
	<!--选择机构：只能查询本身和下级机构-->
	var codeSql = "1  and othersign =#1#";
</script>
