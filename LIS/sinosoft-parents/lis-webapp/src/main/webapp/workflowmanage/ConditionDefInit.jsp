<%
/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */

%>

<!--用户校验类-->

<script language="JavaScript">  

		function ondblclickQ(obj1,obj2)
		{
            if(document.all('BusiTypeQ').value == "")
            {
                alert("请先选择业务类型!");
                return false;
            }
			return showCodeList('queryactivityid2',[obj1,obj2],[0,1],null,null,document.all('BusiTypeQ').value,1,'300');
		}
		function ondblclick2(obj1,obj2)
		{
            if(document.all('BusiType').value == "")
            {
                alert("请先选择业务类型!");
                return false;
            }
			//return showCodeList('queryactivityid',[fm.StartActivityIDQ,fm.StartActivityNameQ],[0,1],null,null,'123',1,'300');
            return showCodeList('queryactivityid2',[obj1,obj2],[0,1],null,null,document.all('BusiType').value,1,'300');
		}


function initInpBox()
{

    try
    {
    }
    catch(ex)
    {
        alert("在InvAssOrderbuildDetailInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    try
    {
        initInpBox();
        initTICollectGrid();
    }
    catch(re)
    {
        alert("InvAssOrderbuildDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
} //成交后汇总表
function initTICollectGrid()
{
    var iArray = new Array();
    var i = 0;
    try
    {
        iArray[i] = new Array();
        iArray[i][0] = "序号";
        iArray[i][1] = "30px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "业务类型CODE";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "业务类型";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "起点活动";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "起点活动名称";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "终点活动";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "终点活动名称";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "转移条件类型CODE";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "转移条件类型";
        iArray[i][1] = "80px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        iArray[i] = new Array();
        iArray[i][0] = "转移条件";
        iArray[i][1] = "0px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;

        iArray[i] = new Array();
        iArray[i][0] = "转移条件描述";
        iArray[i][1] = "100px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;

        TICollectGrid = new MulLineEnter("fm", "TICollectGrid");
        //这些属性必须在loadMulLine前
        TICollectGrid.mulLineCount = 5;
        TICollectGrid.displayTitle = 1;
        TICollectGrid.hiddenPlus = 1;
        TICollectGrid.hiddenSubtraction = 1;
        TICollectGrid.canSel = 1;
        TICollectGrid.selBoxEventFuncName = "showData";
        TICollectGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


</script>
