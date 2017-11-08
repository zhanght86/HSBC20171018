<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 辛玉强 <XinYQ@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03, 1.04, 1.05, 1.06, 1.07
 * @date     : 2005-12-20, 2005-12-23, 2006-01-13, 2006-02-14. 2006-03-24, 2006-08-15, 2006-10-16, 2006-12-08，2007-12-08
 * @direction: 保全项目公用补退费信息被包含页面，去掉合计信息
 ******************************************************************************/
%>

    <!-- 调用费用明细页面 : 开始 -->


    <!-- 补退合计折叠展开 -->
  <!--  <table>
        <tr>
            <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBackFeeTotal)"></td>
            <td class="titleImg">补退费明细</td>
        </tr>
    </table>   
    
    <!-- 补退合计展现表格 -->
     <div id="divBackFeeTotal" style="display:none">
        <table class="common">
            <tr class="common">
                <td class="title">补退费金额合计(折合成本币)</td>
                <td class="input"><input type="text" class="multiCurrency wid" name="BackFeeGetMoney" id=BackFeeGetMoney readonly></td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
                <td class="title">&nbsp;</td>
                <td class="input">&nbsp;</td>
            </tr>
        </table>
    </div>  
    <!-- 补退合计展现表格 -->
    <div id="divBackFeePolSumDetailLayer" style="display:none">
        <!-- 险种补退折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBackFeePol)"></td>
                <td class="titleImg">险种补退费合计</td>
            </tr>
        </table>
        <!-- 险种补退展现表格 -->
        <div id="divBackFeePol" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanBackFeePolGrid"></span></td>
                </tr>
            </table>
            <!-- 险种补退结果翻页 -->
            <!--<div id="divTurnPageBackFeePol" align="center" style="display:none">
                <input type="button" class="cssButton" value="首  页" onclick="turnPageBackFeePol.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPageBackFeePol.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPageBackFeePol.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPageBackFeePol.lastPage()">
            </div>-->
        </div>
    </div>   
    
    <div id="divBackFeePolDetailLayer" style="display:none">
        <!-- 补退明细折叠展开 -->
        <table>
            <tr>
                <td class="common"><img src="../common/images/butExpand.gif" style="cursor:hand" onClick="showPage(this,divBackFeeDetail)"></td>
                <td class="titleImg">补退费费用明细</td>
            </tr>
        </table>
        <!-- 补退明细展现表格 -->
        <div id="divBackFeeDetail" style="display:''">
            <table class="common">
                <tr class="common">
                    <td><span id="spanBackFeeDetailGrid"></span></td>
                </tr>
            </table>
            <!-- 补退明细结果翻页 -->
            <!--<div id="divTurnPageBackFeeDetail" align="center" style="display:none">
                <input type="button" class="cssButton" value="首  页" onclick="turnPageBackFeeDetail.firstPage()">
                <input type="button" class="cssButton" value="上一页" onclick="turnPageBackFeeDetail.previousPage()">
                <input type="button" class="cssButton" value="下一页" onclick="turnPageBackFeeDetail.nextPage()">
                <input type="button" class="cssButton" value="尾  页" onclick="turnPageBackFeeDetail.lastPage()">
            </div>-->
        </div>
    </div>


    <!-- 获取传递的参数值 -->
    <%
        String sJvBackFeeEdorAcceptNo = request.getParameter("EdorAcceptNo");
        String sJvBackFeeEdorType = request.getParameter("EdorType");
        String sJvBackFeeContNo = request.getParameter("ContNo");
    %>

    <!-- 调用脚本初始查询 -->
		
    <script language="JavaScript">
				
        var BackFeePolGrid;                                 //全局变量, 险种补退费合计表格
        var BackFeeDetailGrid;                              //全局变量, 补退费费用明细表格
        var turnPageBackFeePol = new turnPageClass();       //全局变量, 险种补退费合计, 翻页必须有
        var turnPageBackFeeDetail = new turnPageClass();    //全局变量, 补退费费用明细, 翻页必须有

        //获取前台传输条件
        var sJsBackFeeEdorAcceptNo, sJsBackFeeEdorType, sJsBackFeeContNo;
        try { sJsBackFeeEdorAcceptNo = top.opener.document.getElementsByName("EdorAcceptNo")[0].value; } catch (ex) {}
        try { sJsBackFeeEdorType = top.opener.document.getElementsByName("EdorType")[0].value; } catch (ex) {}
        try { sJsBackFeeContNo = top.opener.document.getElementsByName("ContNo")[0].value; } catch (ex) {}

        //需要用到的查询条件
        var sBackFeeEdorAcceptNo = (sJsBackFeeEdorAcceptNo != null && sJsBackFeeEdorAcceptNo.trim() != "") ? sJsBackFeeEdorAcceptNo.trim() : "<%=sJvBackFeeEdorAcceptNo%>";
        var sBackFeeEdorType = (sJsBackFeeEdorType != null && sJsBackFeeEdorType.trim() != "") ? sJsBackFeeEdorType.trim() : "<%=sJvBackFeeEdorType%>";
        var sBackFeeContNo = (sJsBackFeeContNo != null && sJsBackFeeContNo.trim() != "") ? sJsBackFeeContNo.trim() : "<%=sJvBackFeeContNo%>";
        
       	function BackFeeInit(){
       	   initBackFeePolGrid();       //初始化险种补退费合计表格
           initBackFeeDetailGrid();    //初始化补退费费用明细表格
           queryBackFee();             //执行险种和明细补退费查询
       	}

       	// 页面加载完成在执行js代码
        window.addEventListener("load",BackFeeInit, false);
        
        /**
         * 执行险种和明细补退费查询
         */
          
        function queryBackFee()
        {
            if (sBackFeeEdorAcceptNo == null || trim(sBackFeeEdorAcceptNo) == "")
            {
                alert("警告：无法获取保全受理号。查询补退费失败！ ");
                return;
            }
            if (sBackFeeEdorType == null || trim(sBackFeeEdorType) == "")
            {
                alert("警告：无法获取保全项目。查询补退费失败！ ");
                return;
            }
            //满足查询条件, 执行查询
            queryBackFeeTotalMoney();    //补退费金额  合计
            queryBackFeePolGrid();       //险种补退费合计查询
            queryBackFeeDetailGrid();    //补退费费用明细查询
            queryBackFeeGrpCont();       //团险复用查询补退费
        }

        /**
         * 险种补退费合计 MultiLine 的初始化
         */
        function initBackFeePolGrid()
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
                iArray[1][0] = "险种号";
                iArray[1][1] = "120px";
                iArray[1][2] = 150;
                iArray[1][3] = 0;

                iArray[2] = new Array();
                iArray[2][0] = "险种代码";
                iArray[2][1] = "100px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "险种名称";
                iArray[3][1] = "350px";
                iArray[3][2] = 400;
                iArray[3][3] = 0;

                iArray[4] = new Array();
                iArray[4][0] = "补退费合计";
                iArray[4][1] = "80px";
                iArray[4][2] = 100;
                iArray[4][3] = 7;
                iArray[4][21] = 3;
            }
            catch (ex)
            {
                alert("在 PEdorFeeDetail.jsp --> initBackFeePolGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                BackFeePolGrid = new MulLineEnter("fm", "BackFeePolGrid");
                BackFeePolGrid.mulLineCount = 0;
                BackFeePolGrid.displayTitle = 1;
                BackFeePolGrid.locked = 1;
                BackFeePolGrid.hiddenPlus = 1;
                BackFeePolGrid.hiddenSubtraction = 1;
                BackFeePolGrid.canChk = 0;
                BackFeePolGrid.canSel = 0;
                BackFeePolGrid.chkBoxEventFuncName = "";
                BackFeePolGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                BackFeePolGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 PEdorFeeDetail.jsp --> initBackFeePolGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 补退费费用明细 MultiLine 的初始化
         */
        function initBackFeeDetailGrid()
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
                iArray[1][0] = "险种号";
                iArray[1][1] = "0px";
                iArray[1][2] = 150;
                iArray[1][3] = 3;
                
				if(sBackFeeEdorType=="LR")
				{
					iArray[2] = new Array();
	                iArray[2][0] = "险种代码";
	                iArray[2][1] = "60px";
	                iArray[2][2] = 150;
	                iArray[2][3] = 3;
	
	                iArray[3] = new Array();
	                iArray[3][0] = "险种名称";
	                iArray[3][1] = "120px";
	                iArray[3][2] = 200;
	                iArray[3][3] = 3;
				}
				else
				{
					iArray[2] = new Array();
	                iArray[2][0] = "险种代码";
	                iArray[2][1] = "60px";
	                iArray[2][2] = 150;
	                iArray[2][3] = 0;
	
	                iArray[3] = new Array();
	                iArray[3][0] = "险种名称";
	                iArray[3][1] = "120px";
	                iArray[3][2] = 200;
	                iArray[3][3] = 0;
				}
				
                iArray[4] = new Array();
                iArray[4][0] = "费用名称";
                iArray[4][1] = "90px";
                iArray[4][2] = 150;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "财务类型";
                iArray[5][1] = "60px";
                iArray[5][2] = 100;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "费用金额";
                iArray[6][1] = "60px";
                iArray[6][2] = 100;
                iArray[6][21] = 3;
                iArray[6][23] = "0";
                
        		iArray[7]=new Array();
        		iArray[7][0]="币种";
        		iArray[7][1]="60px";
        		iArray[7][2]=100;
        		iArray[7][3]=2;
        		iArray[7][4]="currency";
                //getflag控制
		        iArray[8]=new Array();
		        iArray[8][0]="收/付费";
		        iArray[8][1]="60px";
		        iArray[8][2]=0;
		        iArray[8][3]=0;
            }
            catch (ex)
            {
                alert("在 PEdorFeeDetail.jsp --> initBackFeeDetailGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                BackFeeDetailGrid = new MulLineEnter("fm", "BackFeeDetailGrid");
                BackFeeDetailGrid.mulLineCount = 0;
                BackFeeDetailGrid.displayTitle = 1;
                BackFeeDetailGrid.locked = 1;
                BackFeeDetailGrid.hiddenPlus = 1;
                BackFeeDetailGrid.hiddenSubtraction = 1;
                BackFeeDetailGrid.canChk = 0;
                BackFeeDetailGrid.canSel = 0;
                BackFeeDetailGrid.chkBoxEventFuncName = "";
                BackFeeDetailGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                BackFeeDetailGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 PEdorFeeDetail.jsp --> initBackFeeDetailGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 检查是否是团体复用个单项目
         */
        function isGrpBackFeeReuse()
        {
        	
            var sAppObj;
            try
            {
                sAppObj = top.opener.document.getElementsByName("AppObj")[0].value;
            }
            catch (ex) {}
            if (sAppObj != null && sAppObj.trim() == "G")
            {
                return true;
            }
            return false;
        }

        /**
         * 查询补退费金额合计
         */
        function queryBackFeeTotalMoney()
        {
        		var sqlresourcename = "bq.PEdorFeeDetailSql";
        	  var SQL = "";
            var flag1 = "";
            if (isGrpBackFeeReuse())
            {
            		flag1 = "and ContNo = '" + sBackFeeContNo + "'";
                
            }
						var sqlid1="PEdorFeeDetailSql1";
						var mySql1=new SqlClass();
						mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
						mySql1.setSqlId(sqlid1); //指定使用的Sql的id
						mySql1.addSubPara(sBackFeeEdorAcceptNo); //指定传入的参数
						mySql1.addSubPara(sBackFeeEdorType);
						mySql1.addSubPara(flag1);
					
						SQL=mySql1.getString();
	
						var strResult = easyExecSql(SQL);
       
       		try
            {
                arrResult = easyExecSql(SQL, 1, 0, "", "", 1);    //注意此处第6个参数应设为1，不使用翻页功能，否则可能会把全局变量 turnPage 覆盖
            }
            catch (ex)
            {
                alert("警告：查询补退费金额合计出现异常！ ");
                return;
            }
            if (strResult != null)
            {
                try
                {
           
                    document.getElementsByName("BackFeeGetMoney")[0].value = strResult[0][0];
                }
                catch (ex) {}
            }
        }

        /**
         * 险种补退费合计 MultiLine 的查询
         */
        function queryBackFeePolGrid()
        {
        	var sqlresourcename = "bq.PEdorFeeDetailSql";
            var SQL = "";
            var flag2 = "";
            if (isGrpBackFeeReuse())
            {
            		flag2 = "and ContNo = '" + sBackFeeContNo + "' ";
                
            }

						var mySql1=new SqlClass();
						var sqlid1="PEdorFeeDetailSql2";
						mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
						mySql1.setSqlId(sqlid1); //指定使用的Sql的id
						mySql1.addSubPara(sBackFeeEdorAcceptNo); //指定传入的参数
						mySql1.addSubPara(flag2);
						mySql1.addSubPara(sBackFeeEdorType);
						SQL=mySql1.getString();
	         
						var strResult = easyExecSql(SQL);
            try
            {
                //turnPageBackFeePol.pageDivName = "divTurnPageBackFeePol";
                turnPageBackFeePol.queryModal(SQL, BackFeePolGrid);
            }
            catch (ex)
            {
                alert("警告：查询险种补退费合计出现异常！ ");
                return;
            }
            //补退费险种层和明细层的显示与隐藏
           
            if (BackFeePolGrid.mulLineCount > 0)
            {
                try
                {
                    document.all("divBackFeePolDetailLayer").style.display = "";
                }
                catch (ex) {}
            }
            else
            {
                try
                {
                    document.all("divBackFeePolDetailLayer").style.display = "none";
                }
                catch (ex) {}
            }
        }

        /**
         * 补退费费用明细 MultiLine 的查询
         */
        function queryBackFeeDetailGrid()
        {
        	var sqlresourcename = "bq.PEdorFeeDetailSql";
            var SQL = "";
            var flag3 = "";
            if (isGrpBackFeeReuse())
            {
            		flag3 = "and ContNo = '" + sBackFeeContNo + "' ";
                
            }	
						var sqlid1="PEdorFeeDetailSql3";
						var mySql1=new SqlClass();
						mySql1.setResourceName(sqlresourcename); //指定使用的properties文件名
						mySql1.setSqlId(sqlid1); //指定使用的Sql的id
						mySql1.addSubPara(sBackFeeEdorAcceptNo); //指定传入的参数
						mySql1.addSubPara(flag3);
						mySql1.addSubPara(sBackFeeEdorType);
						SQL=mySql1.getString();
						
						var strResult = easyExecSql(SQL);
          
            try
            {
                //turnPageBackFeeDetail.pageDivName = "divTurnPageBackFeeDetail";
                turnPageBackFeeDetail.queryModal(SQL, BackFeeDetailGrid);
            }
            catch (ex)
            {
                alert("警告：查询补退费费用明细出现异常！ ");
                return;
            }
        }

        /**
         * 如果是团险复用还要查询团体总补退费
         */
        function queryBackFeeGrpCont()
        {
            if (isGrpBackFeeReuse())
            {
                try
                {
                    top.opener.queryBackFee();
                }
                catch (ex) {}
            }
        }

    </script>

    <!-- 调用费用明细页面 : 结束 -->

