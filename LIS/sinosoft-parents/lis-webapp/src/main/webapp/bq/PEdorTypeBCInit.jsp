<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-10-10, 2006-10-13, 2006-11-01, 2006-11-22
 * @direction: 保全受益人变更初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var CustomerGrid;    //全局变量, 保单客户表格
        var OldBnfGrid;      //全局变量, 原受益人表格
        var NewBnfGrid;      //全局变量, 新受益人表格

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                EdorQuery();
                initHiddenArea();
                initInputBox();
                initCustomerGrid();
                initOldBnfGrid();
                initNewBnfGrid();
                queryPolInfo();
                queryCustomerGrid();
                queryOldBnfGrid();
                checkEdorState();
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 隐藏域的初始化
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value;
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initHiddenArea 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                //文本框
                document.getElementsByName("EdorAcceptNo")[0].value = top.opener.document.getElementsByName("EdorAcceptNo")[0].value;
                document.getElementsByName("EdorNo")[0].value = top.opener.document.getElementsByName("EdorNo")[0].value;
                document.getElementsByName("EdorType")[0].value = top.opener.document.getElementsByName("EdorType")[0].value;
                document.getElementsByName("ContNo")[0].value = top.opener.document.getElementsByName("ContNo")[0].value;
                document.getElementsByName("PolNo")[0].value = top.opener.document.getElementsByName("PolNo")[0].value;
                document.getElementsByName("InsuredNo")[0].value = top.opener.document.getElementsByName("InsuredNo")[0].value;
                document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
                document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
                showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 保单客户信息查询 MultiLine 的初始化
         */
        function initCustomerGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

                iArray[1] = new Array();
                iArray[1][0] = "角色";
                iArray[1][1] = "100px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "客户号";
                iArray[2][1] = "100px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "姓名";
                iArray[3][1] = "100px";
                iArray[3][2] = 100;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "性别";
                iArray[4][1] = "100px";
                iArray[4][2] = 100;
                iArray[4][3] = 2;
                iArray[4][4] = "Sex";
                
                iArray[5] = new Array();
                iArray[5][0] = "出生日期";
                iArray[5][1] = "100px";
                iArray[5][2] = 100;
                iArray[5][3] = 8;
                //iArray[5][4] = "Sex";

                iArray[6] = new Array();
                iArray[6][0] = "证件类型";
                iArray[6][1] = "100px";
                iArray[6][2] = 150;
                iArray[6][3] = 2;
                iArray[6][4] = "IDType";

                iArray[7] = new Array();
                iArray[7][0] = "证件号码";
                iArray[7][1] = "150px";
                iArray[7][2] = 200;
                iArray[7][3] = 0;
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initCustomerGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
                CustomerGrid.mulLineCount = 0;
                CustomerGrid.displayTitle = 1;
                CustomerGrid.locked = 1;
                CustomerGrid.hiddenPlus = 1;
                CustomerGrid.hiddenSubtraction = 1;
                CustomerGrid.canChk = 0;
                CustomerGrid.canSel = 0;
                CustomerGrid.chkBoxEventFuncName = "";
                CustomerGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                CustomerGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initCustomerGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 原受益人信息查询 MultiLine 的初始化
         */
        function initOldBnfGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

                iArray[1] = new Array();
                iArray[1][0] = "被保人号";
                iArray[1][1] = "55px";
                iArray[1][2] = 100;
                iArray[1][3] = 2;
                iArray[1][10] = "InsuredCodeList";
                iArray[1][11] = getInsuredCodeList();
                iArray[1][12] = "1|2";
                iArray[1][13] = "0|1";
                iArray[1][19] = 1;

                iArray[2] = new Array();
                iArray[2][0] = "被保人姓名";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "受益人类别";
                iArray[3][1] = "45px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "NewBnfType";

                iArray[4] = new Array();
                iArray[4][0] = "受益人姓名";
                iArray[4][1] = "50px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "无";
                iArray[5][1] = "0px";
                iArray[5][2] = 0;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "证件类型";
                iArray[6][1] = "40px";
                iArray[6][2] = 100;
                iArray[6][3] = 2;
                iArray[6][4] = "IDType";

                iArray[7] = new Array();
                iArray[7][0] = "证件号码";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "与被保人关系";
                iArray[8][1] = "50px";
                iArray[8][2] = 100;
                iArray[8][3] = 2;
                iArray[8][4] = "Relation";

                iArray[9] = new Array();
                iArray[9][0] = "受益顺序";
                iArray[9][1] = "40px";
                iArray[9][2] = 100;
                iArray[9][3] = 2;
                iArray[9][4] = "BnfOrder";

                iArray[10] = new Array();
                iArray[10][0] = "受益份额";
                iArray[10][1] = "40px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;

                iArray[11] = new Array();
                iArray[11][0] = "速填";
                iArray[11][1] = "40px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][10] = "CustomerType";
                iArray[11][11] = getCustomerType();
                iArray[11][19] = 1;
                
                iArray[12] = new Array();
                iArray[12][0] = "性别";
                iArray[12][1] = "80px";
                iArray[12][2] = 100;
                iArray[12][3] = 2;
                iArray[12][4] = "Sex";
                
                iArray[13] = new Array();
                iArray[13][0] = "出生日期";
                iArray[13][1] = "80px";
                iArray[13][2] = 100;
                iArray[13][3] = 8;
                
                iArray[14] = new Array();
                iArray[14][0] = "住址";
                iArray[14][1] = "80px";
                iArray[14][2] = 100;
                iArray[14][3] = 1;
                                
                iArray[15] = new Array();
                iArray[15][0] = "邮编";
                iArray[15][1] = "40px";
                iArray[15][2] = 100;
                iArray[15][3] = 1;
                                
                iArray[16] = new Array();
                iArray[16][0] = "备注信息";
                iArray[16][1] = "40px";
                iArray[16][2] = 100;
                iArray[16][3] = 1;
                
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initOldBnfGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                OldBnfGrid = new MulLineEnter("fm", "OldBnfGrid");
                OldBnfGrid.mulLineCount = 0;
                OldBnfGrid.displayTitle = 1;
                OldBnfGrid.locked = 1;
                OldBnfGrid.hiddenPlus = 0;
                OldBnfGrid.hiddenSubtraction = 0;
                OldBnfGrid.canChk = 0;
                OldBnfGrid.canSel = 0;
                OldBnfGrid.chkBoxEventFuncName = "";
                OldBnfGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                OldBnfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initOldBnfGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 新受益人信息查询 MultiLine 的初始化
         */
        function initNewBnfGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

                iArray[1] = new Array();
                iArray[1][0] = "被保人号";
                iArray[1][1] = "55px";
                iArray[1][2] = 100;
                iArray[1][3] = 2;
                iArray[1][9] = "被保人|NotNull";
                iArray[1][10] = "InsuredCodeList";
                iArray[1][11] = getInsuredCodeList();
                iArray[1][12] = "1|2";
                iArray[1][13] = "0|1";
                iArray[1][19] = 1;

                iArray[2] = new Array();
                iArray[2][0] = "被保人姓名";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "受益人类别";
                iArray[3][1] = "45px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "NewBnfType";
                iArray[3][9] = "受益人类别|NotNull&Code:NewBnfType";

                iArray[4] = new Array();
                iArray[4][0] = "受益人姓名";
                iArray[4][1] = "50px";
                iArray[4][2] = 100;
                iArray[4][3] = 1;
                iArray[4][9] = "受益人姓名|NotNull&Len<=60";

                iArray[5] = new Array();
                iArray[5][0] = "无";
                iArray[5][1] = "0px";
                iArray[5][2] = 0;
                iArray[5][3] = 0;
               
                iArray[6] = new Array();
                iArray[6][0] = "证件类型";
                iArray[6][1] = "40px";
                iArray[6][2] = 100;
                iArray[6][3] = 2;
                iArray[6][4] = "IDType";
                iArray[6][9] = "证件类型|NotNull&Code:IDType";

                iArray[7] = new Array();
                iArray[7][0] = "证件号码";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 1;
                iArray[7][9] = "证件号码|NotNull&Len<=20";

                iArray[8] = new Array();
                iArray[8][0] = "与被保人关系";
                iArray[8][1] = "50px";
                iArray[8][2] = 100;
                iArray[8][3] = 2;
                iArray[8][4] = "Relation";
                iArray[8][9] = "与被保人关系|NotNull&Code:Relation";

                iArray[9] = new Array();
                iArray[9][0] = "受益顺序";
                iArray[9][1] = "40px";
                iArray[9][2] = 100;
                iArray[9][3] = 2;
                iArray[9][4] = "BnfOrder";
                iArray[9][9] = "受益顺序|NotNull&Code:BnfOrder";

                iArray[10] = new Array();
                iArray[10][0] = "受益份额";
                iArray[10][1] = "40px";
                iArray[10][2] = 100;
                iArray[10][3] = 1;
                iArray[10][9] = "受益份额|NotNull&Value>=0&Value<=1";

                iArray[11] = new Array();
                iArray[11][0] = "速填";
                iArray[11][1] = "40px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][10] = "CustomerType";
                iArray[11][11] = getCustomerType();
                iArray[11][19] = 1;
                
                iArray[12] = new Array();
                iArray[12][0] = "性别";
                iArray[12][1] = "80px";
                iArray[12][2] = 100;
                iArray[12][3] = 2;
                iArray[12][4] = "Sex";
                
                iArray[13] = new Array();
                iArray[13][0] = "出生日期";
                iArray[13][1] = "80px";
                iArray[13][2] = 100;
                iArray[13][3] = 8;
                iArray[13][9] = "出生日期|Date";
                
                iArray[14] = new Array();
                iArray[14][0] = "住址";
                iArray[14][1] = "80px";
                iArray[14][2] = 100;
                iArray[14][3] = 1;
                                
                iArray[15] = new Array();
                iArray[15][0] = "邮编";
                iArray[15][1] = "40px";
                iArray[15][2] = 100;
                iArray[15][3] = 1;
                iArray[15][9] = "邮编|Len=6";
                                
                iArray[16] = new Array();
                iArray[16][0] = "备注信息";
                iArray[16][1] = "40px";
                iArray[16][2] = 100;
                iArray[16][3] = 1;
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initNewBnfGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                NewBnfGrid = new MulLineEnter("fm", "NewBnfGrid");
                NewBnfGrid.mulLineCount = 0;
                NewBnfGrid.displayTitle = 1;
                NewBnfGrid.locked = 0;
                NewBnfGrid.hiddenPlus = 0;
                NewBnfGrid.hiddenSubtraction = 0;
                NewBnfGrid.canChk = 0;
                NewBnfGrid.canSel = 0;
                NewBnfGrid.chkBoxEventFuncName = "";
                NewBnfGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                NewBnfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initNewBnfGrid 函数中发生异常: 初始化界面错误！");
            }
        }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

