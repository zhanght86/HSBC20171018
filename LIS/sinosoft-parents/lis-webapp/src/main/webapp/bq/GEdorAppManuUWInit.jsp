<%
/*******************************************************************************
 * @direction: 团单保全人工核保整单层初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <%@ page import="com.sinosoft.lis.pubfun.*" %>

    <%
        GlobalInput tGlobalInput = new GlobalInput();
        tGlobalInput = (GlobalInput)session.getValue("GI");
        String sOperator = tGlobalInput.Operator;
        String sManageCom = tGlobalInput.ManageCom;
        tGlobalInput = null;
    %>


    <script language="JavaScript">

        var GrpPolGrid;    //全局变量, 团单险种队列

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                initHiddenArea();
                initInputBox();
                initEdorItemGrid();
                // initGrpPolGrid();
                queryEdorAppInfo();
                queryEdorItemInfo();
                queryGrpContInfo();
                //queryGrpPolGrid();
                //queryLastUWState();
            }
            catch (ex)
            {
                alert("在 GEdorAppManuUWInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 隐藏域的初始化
         */
        function initHiddenArea()
        {
            try
            {
                document.getElementsByName("MissionID")[0].value = "<%=request.getParameter("MissionID")%>";
                document.getElementsByName("SubMissionID")[0].value = "<%=request.getParameter("SubMissionID")%>";
                document.getElementsByName("ActivityStatus")[0].value = "<%=request.getParameter("ActivityStatus")%>";
                document.getElementsByName("ActivityID")[0].value = "0000008005";
                document.getElementsByName("LoginOperator")[0].value = "<%=sOperator%>";
                document.getElementsByName("LoginManageCom")[0].value = "<%=sManageCom%>";
            }
            catch (ex)
            {
                alert("在 GEdorAppManuUWInit.jsp --> initHiddenArea 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                document.getElementsByName("EdorAcceptNo")[0].value = "<%=request.getParameter("EdorAcceptNo")%>";
            }
            catch (ex)
            {
                alert("在 GEdorAppManuUWInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 保全项目列表的初始化
         */
        function initEdorItemGrid()
        {
            var iArray = new Array();

            try
            {
                  iArray[0] = new Array();
                  iArray[0][0] = "序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
                  iArray[0][1] = "30px";                  //列宽
                  iArray[0][2] = 30;                      //列最大值
                  iArray[0][3] = 0;                       //是否允许输入,1表示允许，0表示不允许

                  iArray[1] = new Array();
                  iArray[1][0] = "保全受理号";
                  iArray[1][1] = "120px";
                  iArray[1][2] = 100;
                  iArray[1][3] = 0;

                  iArray[2] = new Array();
                  iArray[2][0] = "批改类型";
                  iArray[2][1] = "120px";
                  iArray[2][2] = 100;
                  iArray[2][3] = 0;

                  iArray[3] = new Array();
                  iArray[3][0] = "团体保单号";
                  iArray[3][1] = "150px";
                  iArray[3][2] = 100;
                  iArray[3][3] = 0;

                  iArray[4] = new Array();
                  iArray[4][0] = "生效日期";
                  iArray[4][1] = "80px";
                  iArray[4][2] = 100;
                  iArray[4][3] = 0;
                  iArray[4][21]  =  3;

                  iArray[5] = new Array();
                  iArray[5][0] = "补退费金额合计";
                  iArray[5][1] = "100px";
                  iArray[5][2] = 100;
                  iArray[5][3] = 0;
                  iArray[5][21]=  3;

                  iArray[6] = new Array();
                  iArray[6][0] = "补退费利息";
                  iArray[6][1] = "80px";
                  iArray[6][2] = 100;
                  iArray[6][3] = 0;
                  iArray[6][21]  =  3;

                  iArray[7] = new Array();
                  iArray[7][0] = "批改状态";
                  iArray[7][1] = "100px";
                  iArray[7][2] = 100;
                  iArray[7][3] = 0;

                  iArray[8] = new Array();
                  iArray[8][0] = "批改状态编码";
                  iArray[8][1] = "0px";
                  iArray[8][2] = 100;
                  iArray[8][3] = 3;

                  iArray[9] = new Array();
                  iArray[9][0] = "柜面受理日期";
                  iArray[9][1] = "0px";
                  iArray[9][2] = 0;
                  iArray[9][3] = 3;

                  iArray[10] = new Array();
                  iArray[10][0] = "批改类型编码";
                  iArray[10][1] = "0px";
                  iArray[10][2] = 0;
                  iArray[10][3] = 3;

				iArray[11]=new Array();
				iArray[11][0]="币种";         		
				iArray[11][1]="40px";            		 
				iArray[11][2]=60;            			
				iArray[11][3]=2;              		
				iArray[11][4]="Currency";              	  
				iArray[11][9]="币种|code:Currency";
                 
                  EdorItemGrid = new MulLineEnter("document", "EdorItemGrid");
                  //这些属性必须在loadMulLine前
                  EdorItemGrid.mulLineCount = 5;
                  EdorItemGrid.displayTitle = 1;
                  EdorItemGrid.locked = 1;
                  EdorItemGrid.canSel = 0;
                  EdorItemGrid.hiddenPlus = 1;
                  EdorItemGrid.hiddenSubtraction=1;
                  EdorItemGrid.loadMulLine(iArray);
            }
            catch(ex)
            {
                alert("在 GEdorAppManuUWInit.jsp --> initEdorItemGrid 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 团单险种队列查询 MultiLine 的初始化
         */
        function initGrpPolGrid()
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
                iArray[1][0] = "集体险种号";
                iArray[1][1] = "0px";
                iArray[1][2] = 0;
                iArray[1][3] = 3;

                iArray[2] = new Array();
                iArray[2][0] = "批单号";
                iArray[2][1] = "110px";
                iArray[2][2] = 150;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "批改类型";
                iArray[3][1] = "65px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;
                iArray[3][4] = "GEdorType";
                iArray[3][18] = 170;

                iArray[4] = new Array();
                iArray[4][0] = "险种代码";
                iArray[4][1] = "65px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;

                iArray[5] = new Array();
                iArray[5][0] = "险种名称";
                iArray[5][1] = "200px";
                iArray[5][2] = 200;
                iArray[5][3] = 0;

                iArray[6] = new Array();
                iArray[6][0] = "交费间隔";
                iArray[6][1] = "70px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "投保人数";
                iArray[7][1] = "70px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][21] = 3;

                iArray[8] = new Array();
                iArray[8][0] = "交至日期";
                iArray[8][1] = "70px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;
                iArray[8][21] = 3;

                iArray[9] = new Array();
                iArray[9][0] = "生效日期";
                iArray[9][1] = "70px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;
                iArray[9][21] = 3;

                iArray[10] = new Array();
                iArray[10][0] = "核保结论";
                iArray[10][1] = "70px";
                iArray[10][2] = 100;
                iArray[10][3] = 2;
                iArray[10][4] = "gedorgrppoluwstate";
                iArray[10][18] = 78;
                
                GrpPolGrid = new MulLineEnter("document", "GrpPolGrid");
                          
                GrpPolGrid.mulLineCount = 5;
                GrpPolGrid.displayTitle = 1;
                GrpPolGrid.locked = 1;
                GrpPolGrid.hiddenPlus = 1;
                GrpPolGrid.hiddenSubtraction = 1;
                GrpPolGrid.canChk = 0;
                GrpPolGrid.canSel = 1;
                GrpPolGrid.chkBoxEventFuncName = "";
                GrpPolGrid.selBoxEventFuncName = "initGrpPolInfo";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                GrpPolGrid.loadMulLine(iArray);
       		
            }
            catch (ex)
            {
                alert("在 GEdorAppManuUWInit.jsp --> initGrpPolGrid 函数中发生异常: 初始化界面错误！");
            }
        }
        
        function initAgentGrid()
  			{                               
    			var iArray = new Array();
      
      		try
      		{
       			iArray[0]=new Array();
       			iArray[0][0]="序号";         //列名
       			iArray[0][1]="30px";         //列名
       			iArray[0][2]=100;         //列名
       			iArray[0][3]=0;         //列名
       			
       			iArray[1]=new Array();
       			iArray[1][0]="问题件流水号";         //列名
       			iArray[1][1]="120px";         //宽度
       			iArray[1][2]=100;         //最大长度
       			iArray[1][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[2]=new Array();
       			iArray[2][0]="团单号码";         //列名
       			iArray[2][1]="120px";         //宽度
       			iArray[2][2]=100;         //最大长度
       			iArray[2][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[3]=new Array();
       			iArray[3][0]="保全受理号";         //列名
       			iArray[3][1]="120px";         //宽度
       			iArray[3][2]=100;         //最大长度
       			iArray[3][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[4]=new Array();
       			iArray[4][0]="问题人";         //列名
       			iArray[4][1]="60px";         //宽度
       			iArray[4][2]=100;         //最大长度
       			iArray[4][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[5]=new Array();
       			iArray[5][0]="回复人";         //列名
       			iArray[5][1]="60px";         //宽度
       			iArray[5][2]=100;         //最大长度
       			iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
 
       			iArray[6]=new Array();
       			iArray[6][0]="问题件类型";         //列名
       			iArray[6][1]="60px";         //宽度
       			iArray[6][2]=100;         //最大长度
       			iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[7]=new Array();
       			iArray[7][0]="下发日期";         //列名
       			iArray[7][1]="80px";         //宽度
       			iArray[7][2]=100;         //最大长度
       			iArray[7][3]=0;         //是否允许录入，0--不能，1--允许

       			iArray[8]=new Array();
       			iArray[8][0]="回复日期";         //列名
       			iArray[8][1]="80px";         //宽度
       			iArray[8][2]=100;         //最大长度
       			iArray[8][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[9]=new Array();
       			iArray[9][0]="问题内容";         //列名
       			iArray[9][1]="0px";         //宽度
       			iArray[9][2]=100;         //最大长度
       			iArray[9][3]=3;         //是否允许录入，0--不能，1--允许
       		
        		iArray[10]=new Array();
       			iArray[10][0]="回复内容";         //列名
       			iArray[10][1]="0px";         //宽度
       			iArray[10][2]=100;         //最大长度
       			iArray[10][3]=3;         //是否允许录入，0--不能，1--允许
      		
       		AgentGrid = new MulLineEnter( "document" , "AgentGrid" ); 
       		
       		//这些属性必须在loadMulLine前
       		//AgentGrid.mulLineCount = 1;   
       		AgentGrid.displayTitle = 1;
       		AgentGrid.canSel=1;
//     		AgentGrid.canChk=0;
       		AgentGrid.locked=1;
     		AgentGrid.hiddenPlus=1;
     		AgentGrid.hiddenSubtraction=1;
     		AgentGrid.selBoxEventFuncName = "ShowAskInfo"; //单击RadioBox时响应函数
       		AgentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化AgentGrid时出错："+ ex);
      }
    }

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

