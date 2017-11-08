<%
//程序名称：EdorApproveInit.jsp
//程序功能：保全复核
//创建日期：2005-05-08 15:20:22
//创建人  ：zhangtao
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

//页面初始化
function initForm()
{
    try
    {
        initInpBox();
        initEdorMainGrid();
        initEdorItemGrid();
        initQuery();
    }
    catch (ex)
    {
        alert("在 EdorApproveInit.jsp --> initForm 函数中发生异常:初始化界面错误！");
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
       			iArray[1][1]="80px";         //宽度
       			iArray[1][2]=100;         //最大长度
       			iArray[1][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[2]=new Array();
       			iArray[2][0]="个人保单号码";         //列名
       			iArray[2][1]="80px";         //宽度
       			iArray[2][2]=100;         //最大长度
       			iArray[2][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[3]=new Array();
       			iArray[3][0]="保全受理号";         //列名
       			iArray[3][1]="80px";         //宽度
       			iArray[3][2]=100;         //最大长度
       			iArray[3][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[4]=new Array();
       			iArray[4][0]="问题人";         //列名
       			iArray[4][1]="50px";         //宽度
       			iArray[4][2]=100;         //最大长度
       			iArray[4][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[5]=new Array();
       			iArray[5][0]="回复人";         //列名
       			iArray[5][1]="50px";         //宽度
       			iArray[5][2]=100;         //最大长度
       			iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
 
       			iArray[6]=new Array();
       			iArray[6][0]="问题件类型";         //列名
       			iArray[6][1]="60px";         //宽度
       			iArray[6][2]=100;         //最大长度
       			iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
       			
       			iArray[7]=new Array();
       			iArray[7][0]="下发日期及时间";         //列名
       			iArray[7][1]="100px";         //宽度
       			iArray[7][2]=100;         //最大长度
       			iArray[7][3]=0;         //是否允许录入，0--不能，1--允许

       			iArray[8]=new Array();
       			iArray[8][0]="回复日期及时间";         //列名
       			iArray[8][1]="100px";         //宽度
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

        		iArray[11]=new Array();
       			iArray[11][0]="问题件状态";         //列名
       			iArray[11][1]="50px";         //宽度
       			iArray[11][2]=100;         //最大长度
       			iArray[11][3]=0;         //是否允许录入，0--不能，1--允许
     		
       		AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 
       		
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

//初始化页面元素
function initInpBox()
{
    try
    {
        document.all('EdorAcceptNo').value     = NullToEmpty("<%= tEdorAcceptNo %>");
        document.all('MissionID').value        = NullToEmpty("<%= tMissionID %>");
        document.all('SubMissionID').value     = NullToEmpty("<%= tSubMissionID %>");
        document.all('EdorPopedom').value=NullToEmpty("<%= tEdorPopedom %>");
        document.all('ActivityID').value     = NullToEmpty("<%= tActivityID %>");
    }
    catch (ex)
    {
        alert("在 EdorApproveInit.jsp --> initInpBox 函数中发生异常:初始化界面错误！");
    }
}

// 保全批单列表的初始化
function initEdorMainGrid()
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
        iArray[1][0] = "申请批单号";
        iArray[1][1] = "130px";
        iArray[1][2] = 150;
        iArray[1][3] = 0;

        iArray[2] = new Array();
        iArray[2][0] = "保单号";
        iArray[2][1] = "130px";
        iArray[2][2] = 150;
        iArray[2][3] = 0;

        iArray[3] = new Array();
        iArray[3][0] = "投保人";
        iArray[3][1] = "100px";
        iArray[3][2] = 150;
        iArray[3][3] = 0;

        iArray[4] = new Array();
        iArray[4][0] = "主险交费对应日";
        iArray[4][1] = "100px";
        iArray[4][2] = 150;
        iArray[4][3] = 0;
        iArray[4][21] = 3;

        iArray[5] = new Array();
        iArray[5][0] = "柜面受理日期";
        iArray[5][1] = "0px";
        iArray[5][2] = 0;
        iArray[5][3] = 3;
        iArray[5][21] = 3;

        iArray[6] = new Array();
        iArray[6][0] = "生效日期";
        iArray[6][1] = "0px";
        iArray[6][2] = 0;
        iArray[6][3] = 3;
        iArray[6][21] = 3;

        iArray[7] = new Array();
        iArray[7][0] = "补退费金额";
        iArray[7][1] = "70px";
        iArray[7][2] = 100;
        iArray[7][3] = 0;
        iArray[7][21] = 3;

        iArray[8] = new Array();
        iArray[8][0] = "补退费利息";
        iArray[8][1] = "70px";
        iArray[8][2] = 100;
        iArray[8][3] = 0;
        iArray[8][21] = 3;

        iArray[9] = new Array();
        iArray[9][0] = "批改状态";
        iArray[9][1] = "70px";
        iArray[9][2] = 100;
        iArray[9][3] = 0;

        iArray[10] = new Array();
        iArray[10][0] = "批改状态编码";
        iArray[10][1] = "0px";
        iArray[10][2] = 0;
        iArray[10][3] = 3;

        iArray[11] = new Array();
        iArray[11][0] = "保全申请操作员";
        iArray[11][1] = "0px";
        iArray[11][2] = 0;
        iArray[11][3] = 3;

        EdorMainGrid = new MulLineEnter("fm", "EdorMainGrid");
        //这些属性必须在loadMulLine前
        EdorMainGrid.mulLineCount = 1;
        EdorMainGrid.displayTitle = 1;
        EdorMainGrid.locked = 1;
        EdorMainGrid.canSel = 1;
        EdorMainGrid.hiddenPlus = 1;
        EdorMainGrid.hiddenSubtraction=1;
        EdorMainGrid.selBoxEventFuncName = "showEdorItemList";
        EdorMainGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 EdorApproveInit.jsp --> initEdorMainGrid 函数中发生异常:初始化界面错误！");
    }
}

// 保全项目列表的初始化
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
        iArray[1][0] = "申请批单号";
        iArray[1][1] = "0px";
        iArray[1][2] = 0;
        iArray[1][3] = 3;

        iArray[2] = new Array();
        iArray[2][0] = "批改类型";
        iArray[2][1] = "100px";
        iArray[2][2] = 100;
        iArray[2][3] = 0;

        iArray[3] = new Array();
        iArray[3][0] = "保单号";
        iArray[3][1] = "110px";
        iArray[3][2] = 150;
        iArray[3][3] = 0;

        iArray[4] = new Array();
        iArray[4][0] = "客户号";
        iArray[4][1] = "70px";
        iArray[4][2] = 100;
        iArray[4][3] = 0;

        iArray[5] = new Array();
        iArray[5][0] = "险种号";
        iArray[5][1] = "110px";
        iArray[5][2] = 150;
        iArray[5][3] = 0;

        iArray[6] = new Array();
        iArray[6][0] = "柜面受理日期";
        iArray[6][1] = "0px";
        iArray[6][2] = 0;
        iArray[6][3] = 3;
        iArray[6][21] = 3;

        iArray[7] = new Array();
        iArray[7][0] = "生效日期";
        iArray[7][1] = "0px";
        iArray[7][2] = 0;
        iArray[7][3] = 3;
        iArray[7][21] = 3;

        iArray[8] = new Array();
        iArray[8][0] = "补退费金额";
        iArray[8][1] = "70px";
        iArray[8][2] = 100;
        iArray[8][3] = 0;
        iArray[8][21] = 3;

        iArray[9] = new Array();
        iArray[9][0] = "补退费利息";
        iArray[9][1] = "70px";
        iArray[9][2] = 100;
        iArray[9][3] = 0;
        iArray[9][21] = 3;

        iArray[10] = new Array();
        iArray[10][0] = "批改状态";
        iArray[10][1] = "70px";
        iArray[10][2] = 100;
        iArray[10][3] = 0;

        iArray[11] = new Array();
        iArray[11][0] = "批改状态编码";
        iArray[11][1] = "0px";
        iArray[11][2] = 0;
        iArray[11][3] = 3;

        iArray[12] = new Array();
        iArray[12][0] = "生成日期";
        iArray[12][1] = "0px";
        iArray[12][2] = 0;
        iArray[12][3] = 3;
        iArray[12][21] = 3;

        iArray[13] = new Array();
        iArray[13][0] = "生成时间";
        iArray[13][1] = "0px";
        iArray[13][2] = 0;
        iArray[13][3] = 3;
        iArray[13][21] = 3;

        iArray[14] = new Array();
        iArray[14][0] = "批改类型编码";
        iArray[14][1] = "0px";
        iArray[14][2] = 0;
        iArray[14][3] = 3;

        iArray[15] = new Array();
        iArray[15][0] = "需要保单密码";
        iArray[15][1] = "70px";
        iArray[15][2] = 100;
        iArray[15][3] = 0;
        iArray[15][21] = 2;

        EdorItemGrid = new MulLineEnter("fm", "EdorItemGrid");
        //这些属性必须在loadMulLine前
        EdorItemGrid.mulLineCount = 1;
        EdorItemGrid.displayTitle = 1;
        EdorItemGrid.locked = 1;
        EdorItemGrid.canSel = 1;
        EdorItemGrid.hiddenPlus = 1;
        EdorItemGrid.hiddenSubtraction=1;
        EdorItemGrid.selBoxEventFuncName = "getEdorItemInfo";
        EdorItemGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 EdorApproveInit.jsp --> initEdorItemGrid 函数中发生异常:初始化界面错误！");
    }
}

</script>
