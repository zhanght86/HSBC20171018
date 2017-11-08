<%@include file="../i18n/language.jsp"%>


<%/**
 * Created by IntelliJ IDEA.
 * User: jinsh
 * Date: 2009-1-7
 * Time: 15:32:15
 * To change this template use File | Settings | File Templates.
 */%><!--用户校验类-->
<script type="text/javascript">
function initForm()
{
    try
    {
        initTabHeadConfGrid();
        initSaveTabHeadConfGrid();
        initMulline10Grid();
        tabHeadQueryGrid();
    }
    catch(re)
    {
        myAlert("PDSugTabHeadConfInit.jsp.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
    }
} //全部日期信息
function initTabHeadConfGrid()
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
        iArray[i][0] = "表头ID";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;
        iArray[i] = new Array();
        iArray[i][0] = "表头收益项代码";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "表头中文名称";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
        iArray[i] = new Array();
        iArray[i][0] = "顺序";
        iArray[i][1] = "60px";
        iArray[i][2] = 10;
        iArray[i++][3] = 0;
       
        TabHeadConfGrid = new MulLineEnter("fm", "TabHeadConfGrid");
        //这些属性必须在loadMulLine前
        TabHeadConfGrid.mulLineCount = 5;
        TabHeadConfGrid.displayTitle = 1;
        TabHeadConfGrid.hiddenPlus = 1;
        TabHeadConfGrid.hiddenSubtraction = 1;
        TabHeadConfGrid.canChk = 1;
        TabHeadConfGrid.selBoxEventFuncName = "";
        TabHeadConfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert(ex);
    }
} 

function initSaveTabHeadConfGrid()
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
        iArray[i][0] = "表头ID";
        iArray[i][1] = "40px";
        iArray[i][2] = 10;
        iArray[i++][3] = 3;
        SaveTabHeadConfGrid = new MulLineEnter("fm", "SaveTabHeadConfGrid");
        //这些属性必须在loadMulLine前
        SaveTabHeadConfGrid.mulLineCount = 0;
        SaveTabHeadConfGrid.displayTitle = 1;
        SaveTabHeadConfGrid.hiddenPlus = 1;
        SaveTabHeadConfGrid.hiddenSubtraction = 1;
        SaveTabHeadConfGrid.canChk = 1;
        SaveTabHeadConfGrid.selBoxEventFuncName = "";
        SaveTabHeadConfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="ID";
		iArray[1][1]="40px";
		iArray[1][2]=100;
		iArray[1][3]=3;

		iArray[2]=new Array();
		iArray[2][0]="表头收益项代码";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="表头中文名称";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="顺序";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="类型";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}
</script>

