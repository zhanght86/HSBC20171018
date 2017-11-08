<%@ page contentType="text/html; charset=gb2312" language="java" errorPage="" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  

<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2005-12-14, 2006-03-21, 2006-08-19, 2006-10-26
 * @direction: 保全人工核保投(被)保人既往保全查询主框架
 ******************************************************************************/
%>

<%@ include file="../common/jsp/UsrCheck.jsp" %>


<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312">
    <title>既往保全查询</title>
    <!-- 公共引用样式 -->
    <link href="../common/css/Project.css" type="text/css" rel="stylesheet">
	<link href="../common/css/Project3.css" type="text/css" rel="stylesheet">
    <link href="../common/css/mulLine.css" type="text/css" rel="stylesheet">
    <!-- 公共引用脚本  -->
    
    <SCRIPT src="../common/laydate/laydate.js"></SCRIPT>
    <script language="JavaScript" src="../common/javascript/Common.js"></script>
    <script language="JavaScript" src="../common/cvar/CCodeOperate.js"></script>
    <script language="JavaScript" src="../common/javascript/MulLine.js"></script>
    <script language="JavaScript" src="../common/javascript/EasyQuery.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryVer3.js"></script>
    <script language="JavaScript" src="../common/easyQueryVer3/EasyQueryCache.js"></script>
    <script language="JavaScript" src="../common/javascript/VerifyInput.js"></script>
    <!-- 私有引用脚本 -->
    <script language="JavaScript" src="EdorAgoQuery.js"></script>
    <%@ include file="EdorAgoQueryInit.jsp" %>
</head>
<body topmargin="0" onload="initForm()" ondragstart="return false">
    <form name="fm" id=fm method="post" target="fraSubmit">
        <!-- 客户号码姓名显示 -->
		<div class="maxbox1">
        <table class="common">
            <tr class="common">
                <td class="title5">客户号</td>
                <td class="input5"><input type="text" class="readonly wid" name="CustomerNo" id=CustomerNo readonly></td>
                <td class="title5">客户姓名</td>
                <td class="input5"><input type="text" class="readonly wid" name="CustomerName" id=CustomerName readonly></td>
                
            </tr>
        </table></div>
        <!-- 既往批单折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorGrid)"></td>
                <td class="titleImg">既往保全批单信息</td>
           </tr>
        </table>
        <!-- 既往批单结果展现 -->
        <div id="divEdorGrid" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanEdorGrid"></span></td>
                </tr>
            </table>
            <!-- 既往批单结果翻页 -->
            <div id="divTurnPageEdorGrid" align="center" style="display:'none'">
                <input type="button" class=cssButton90 value="首  页" onclick="turnPageEdorGrid.firstPage()">
                <input type="button" class="cssButton91" value="上一页" onclick="turnPageEdorGrid.previousPage()">
                <input type="button" class="cssButton92" value="下一页" onclick="turnPageEdorGrid.nextPage()">
                <input type="button" class="cssButton93" value="尾  页" onclick="turnPageEdorGrid.lastPage()">
            </div>
            <!-- 提交数据操作按钮 -->
            <input type="button" class="cssButton" value=" 影像资料查询 " onclick="showImage()">
            <input type="button" class="cssButton" value="保全核保照会查询" onclick="EdorUWQuery()">
            <br>
        </div>
        <div id="divPrintEdorItemLayer" style="display:'none'">
            <br>
            <!-- 核保通知折叠展开 -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPrintGrid)"></td>
                    <td class="titleImg">既往保全批单核保通知书信息</td>
                </tr>
            </table>
            <!-- 核保通知结果展现 -->
            <div id="divPrintGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanPrintGrid"></span></td>
                    </tr>
                </table>
                <!-- 既往项目结果翻页 -->
                <div id="divTurnPagePrintGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="首  页" onclick="turnPagePrintGrid.firstPage()">
                    <input type="button" class="cssButton91" value="上一页" onclick="turnPagePrintGrid.previousPage()">
                    <input type="button" class="cssButton92" value="下一页" onclick="turnPagePrintGrid.nextPage()">
                    <input type="button" class="cssButton93" value="尾  页" onclick="turnPagePrintGrid.lastPage()">
                </div>
                <!-- 打印数据操作按钮 -->
                <input type="button" class="cssButton" value="查看核保通知书" onclick="printEdorUWNotice()">
            </div>
            <br>
            <!-- 既往项目折叠展开 -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divEdorItemGrid)"></td>
                    <td class="titleImg">既往保全批改项目信息</td>
                </tr>
            </table>
            <!-- 既往项目结果展现 -->
            <div id="divEdorItemGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanEdorItemGrid"></span></td>
                    </tr>
                </table>
                <!-- 既往项目结果翻页 -->
                <div id="divTurnPageEdorItemGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="首  页" onclick="turnPageEdorItemGrid.firstPage()">
                    <input type="button" class="cssButton91" value="上一页" onclick="turnPageEdorItemGrid.previousPage()">
                    <input type="button" class="cssButton92" value="下一页" onclick="turnPageEdorItemGrid.nextPage()">
                    <input type="button" class="cssButton93" value="尾  页" onclick="turnPageEdorItemGrid.lastPage()">
                </div>
                <!-- 查询数据操作按钮 -->
                <input type="button" class="cssButton" value=" 保全明细查询 " onclick="showEdorItemDetail()">
                <br>
            </div>
        </div>
        <div id="divPremSpecLayer" style="display:'none'">
            <br>
            <!-- 加费项目折叠展开 -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divPremGrid)"></td>
                    <td class="titleImg">既往保全批改项目加费信息</td>
                </tr>
            </table>
            <!-- 加费项目结果展现 -->
            <div id="divPremGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanPremGrid"></span></td>
                    </tr>
                </table>
                <!-- 加费项目结果翻页 -->
                <div id="divTurnPagePremGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="首  页" onclick="turnPagePremGrid.firstPage()">
                    <input type="button" class="cssButton91" value="上一页" onclick="turnPagePremGrid.previousPage()">
                    <input type="button" class="cssButton92" value="下一页" onclick="turnPagePremGrid.nextPage()">
                    <input type="button" class="cssButton93" value="尾  页" onclick="turnPagePremGrid.lastPage()">
                </div>
            </div>
            <br>
            <!-- 特别约定折叠展开 -->
            <table>
                <tr>
                    <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onclick="showPage(this,divSpecGrid)"></td>
                    <td class="titleImg">既往保全批改项目特别约定信息</td>
                </tr>
            </table>
            <!-- 特别约定结果展现 -->
            <div id="divSpecGrid" style="display:''">
                <table class="common">
                    <tr class="common">
                        <td><span id="spanSpecGrid"></span></td>
                    </tr>
                </table>
                <!-- 特别约定结果翻页 -->
                <div id="divTurnPageSpecGrid" align="center" style="display:'none'">
                    <input type="button" class="cssButton90" value="首  页" onclick="turnPageSpecGrid.firstPage()">
                    <input type="button" class="cssButton91" value="上一页" onclick="turnPageSpecGrid.previousPage()">
                    <input type="button" class="cssButton92" value="下一页" onclick="turnPageSpecGrid.nextPage()">
                    <input type="button" class="cssButton93" value="尾  页" onclick="turnPageSpecGrid.lastPage()">
                </div>
            </div>
        </div>
        <br>
        <!-- 获取数据的隐藏域 -->
        <input type="hidden" name="EdorAcceptNo" id=EdorAcceptNo>
        <input type="hidden" name="EdorNo" id=EdorNo>
        <input type="hidden" name="EdorType" id=EdorType>
        <input type="hidden" name="ContNo" id=ContNo>
        <input type="hidden" name="EdorItemAppDate" id=EdorItemAppDate>
        <input type="hidden" name="EdorValiDate" id=EdorValiDate>
        <input type="hidden" name="CurEdorAcceptNo" id=CurEdorAcceptNo>
        <input type="hidden" name="ButtonFlag" value="1" id=ButtonFlag>
        <!-- 关闭页面操作按钮 -->
        <input type="button" class="cssButton" value="   返    回   " onclick="returnParent()">
    </form>
    <!-- 通用下拉信息列表 -->
    <span id="spanCode" style="display:none; position:absolute; slategray"></span>
</body>
</html>
