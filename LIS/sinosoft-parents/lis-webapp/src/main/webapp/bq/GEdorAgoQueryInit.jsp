<%
/*******************************************************************************

 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var EdorItemGrid;    //全局变量, 既往保全批改项目信息

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initInputBox();
                initEdorItemGrid();
                queryCustomerInfo();
                queryEdorItem();
            }
            catch (ex)
            {
                alert("在 GEdorAgoQueryInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("CustomerNo")[0].value = "<%=request.getParameter("CustomerNo")%>";
            }
            catch (ex)
            {
                alert("在 GEdorAgoQueryInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 既往保全批改项目信息查询 MultiLine 的初始化
         */
        function initEdorItemGrid()
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
                iArray[1][0] = "保全受理号";
                iArray[1][1] = "120px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "批单号";
                iArray[2][1] = "120px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "批改类型";
                iArray[3][1] = "60px";
                iArray[3][2] = 50;
                iArray[3][3] = 2;
                iArray[3][4] = "GEdorType";
                iArray[3][18] = 170;

                iArray[4] = new Array();
                iArray[4][0] = "集体保单号";
                iArray[4][1] = "120px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "保全柜面受理日期";
                iArray[5][1] = "80px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;
                
                iArray[6] = new Array();
                iArray[6][0] = "保全生效日期";
                iArray[6][1] = "80px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;           

                iArray[7] = new Array();
                iArray[7][0] = "补退费合计";
                iArray[7][1] = "100px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;

                iArray[8] = new Array();
                iArray[8][0] = "补退费利息";
                iArray[8][1] = "100px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                
                
                iArray[9]=new Array();
				iArray[9][0]="币种";          
				iArray[9][1]="50px";              
				iArray[9][2]=60;             
				iArray[9][3]=2;               
				iArray[9][4]="Currency";                 
				iArray[9][9]="币种|code:Currency";

                
                                   
            }
            catch (ex)
            {
                alert("在 GEdorAgoQueryInit.jsp --> initEdorItemGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                EdorItemGrid = new MulLineEnter("document", "EdorItemGrid");
                EdorItemGrid.mulLineCount = 5;
                EdorItemGrid.displayTitle = 1;
                EdorItemGrid.locked = 1;
                EdorItemGrid.hiddenPlus = 1;
                EdorItemGrid.hiddenSubtraction = 1;
                EdorItemGrid.canChk = 0;
                EdorItemGrid.canSel = 1;
                EdorItemGrid.chkBoxEventFuncName = "";
                EdorItemGrid.selBoxEventFuncName = "initHide";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                EdorItemGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 GEdorAgoQueryInit.jsp --> initEdorItemGrid 函数中发生异常: 初始化界面错误！");
            }
        }
    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

