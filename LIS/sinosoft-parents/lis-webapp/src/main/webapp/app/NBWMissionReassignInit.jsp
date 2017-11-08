<%
//程序名称：NBWMissionReassignInit.jsp
//程序功能：新契约任务调配
//创建日期：2006-2-20 14:40
//创建人  ：chenrong
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<script language="JavaScript">

//初始化页面传递过来的参数
function initParam()
{
   document.all('Operator').value = nullToEmpty("<%= tGI.Operator %>");
   document.all('ComCode').value = nullToEmpty("<%= tGI.ComCode %>");
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initForm()
{
    try
    {
        initParam();
        initAllPolGrid();      
        initInpBox();          
    }
    catch(re)
    {
        alert("在NBWMissionReassignInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//初始化输入框
function initInpBox()
{
    try
    {
		fm.DefaultOperator.value = "";
		fm.DesignateOperator.value = "";
    }
    catch(ex)
    {
        alert("在NBWMissionReassignInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

// 保单信息列表的初始化
function initAllPolGrid()
{                               
    var iArray = new Array();
  
    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";            		//列宽
        iArray[0][2] = 30;            			//列最大值
        iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[1] = new Array();
        iArray[1][0] = "投保单号";         		//列名
        iArray[1][1] = "160px";            		//列宽
        iArray[1][2] = 100;            			//列最大值
        iArray[1][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[2] = new Array();
        iArray[2][0] = "新契约状态";         		//列名
        iArray[2][1] = "160px";            		//列宽
        iArray[2][2] = 100;            			//列最大值
        iArray[2][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[3] = new Array();
        iArray[3][0] = "操作员工号";         		//列名
        iArray[3][1] = "100px";            		//列宽
        iArray[3][2] = 100;            			//列最大值
        iArray[3][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[4] = new Array();
        iArray[4][0] = "申请日期";         		//列名
        iArray[4][1] = "70px";            		//列宽
        iArray[4][2] = 100;            			//列最大值
        iArray[4][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
            
        iArray[5] = new Array();
        iArray[5][0] = "申请时间";         		//列名
        iArray[5][1] = "70px";            		//列宽
        iArray[5][2] = 200;            			//列最大值
        iArray[5][3] = 0;              			//是否允许输入,1表示允许，0表示不允许 
        
        iArray[6] = new Array();
        iArray[6][0] = "扫描日期";         		//列名
        iArray[6][1] = "70px";            		//列宽
        iArray[6][2] = 200;            			//列最大值
        iArray[6][3] = 0;              			//是否允许输入,1表示允许，0表示不允许 
        
        iArray[7] = new Array();
        iArray[7][0] = "操作员姓名";         		//列名
        iArray[7][1] = "0px";            		//列宽
        iArray[7][2] = 60;            			//列最大值
        iArray[7][3] = 3;              			//是否允许输入,1表示允许，0表示不允许 
        
                    
        iArray[8] = new Array();
        iArray[8][0] = "工作流任务号";         		//列名
        iArray[8][1] = "0px";            		//列宽
        iArray[8][2] = 200;            			//列最大值
        iArray[8][3] = 3;              			//是否允许输入,1表示允许，0表示不允许 
        
        iArray[9] = new Array();
        iArray[9][0] = "工作流子任务号";         		//列名
        iArray[9][1] = "0px";            		//列宽
        iArray[9][2] = 200;            			//列最大值
        iArray[9][3] = 3;              			//是否允许输入,1表示允许，0表示不允许 
        
        iArray[10] = new Array();
        iArray[10][0] = "工作流活动Id";         		//列名
        iArray[10][1] = "0px";            		//列宽
        iArray[10][2] = 60;            			//列最大值
        iArray[10][3] = 3;              			//是否允许输入,1表示允许，0表示不允许 
        
        
        AllPolGrid = new MulLineEnter( "document" , "AllPolGrid" ); 
        //这些属性必须在loadMulLine前
        AllPolGrid.mulLineCount = 5;
        AllPolGrid.displayTitle = 1;
        AllPolGrid.locked = 1;
        AllPolGrid.canSel = 0;
        AllPolGrid.canChk = 1;
        AllPolGrid.hiddenPlus = 1;
        AllPolGrid.hiddenSubtraction = 1;
   //     AllPolGrid.selBoxEventFuncName = "setTextValue";
        AllPolGrid.loadMulLine(iArray);     
    
    }
    catch(ex)
    {
        alert("在NBWMissionReassignInit.jsp-->initAllPolGrid函数中发生异常:初始化界面错误!");
    }
}


</script>
