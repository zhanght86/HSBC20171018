<script language=javascript>

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

function initHiddenBox()
{
    fm.LoadFlag.value = nullToEmpty("<%=request.getParameter("LoadFlag")%>");
    fm.GrpContNoIn.value = nullToEmpty("<%=request.getParameter("GrpContNo")%>");
}

function initInpBox()
{

} 

function initForm()
{
    try
    {
        initHiddenBox();        
        var tLoadFlag = fm.LoadFlag.value;
        if(tLoadFlag=="4"||tLoadFlag=="16"||tLoadFlag=="14")
        {
            divTiggerSave.style.display="none";
        }
        initInpBox();
        
        initAccTriggerGrid();
        //getRiskAcc(); //查询险种帐户编码
        GrpPerPolDefine();
        //GrpPerPolDefineOld();
    }
    catch(ex)
    {
        alert("在LCGrpAccTriggerInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

var AccTriggerGrid;
function initAccTriggerGrid()
{
    var iArray=new Array()
    try
    {
        iArray[0] = new Array();
        iArray[0][0] = "序号";         			 //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1] = "30px";            		 //列宽
        iArray[0][2] = 10;            			   //列最大值
        iArray[0][3] = 0;              			 //是否允许输入,1表示允许，0表示不允许
        
        iArray[1]=new Array();
        iArray[1][0]="保险帐户编码";
        iArray[1][1]="00px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="缴费计划编码";
        iArray[2][1]="00px";
        iArray[2][2]=100;
        iArray[2][3]=0;       
   
   
        iArray[3]=new Array();
        iArray[3][0]="帐户类型";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=1;
        
        iArray[4]=new Array();
        iArray[4][0]="红利分配方式";
        iArray[4][1]="50px";
        iArray[4][2]=80;
        iArray[4][3]=2;
        iArray[4][4]="subriskacccodepayname";
        iArray[4][15]="PayPlanCode";
        iArray[4][17]="2";

        AccTriggerGrid = new MulLineEnter( "fm" , "AccTriggerGrid" );
        AccTriggerGrid.mulLineCount=0;
        AccTriggerGrid.hiddenPlus=1;
        AccTriggerGrid.hiddenSubtraction=1;
        AccTriggerGrid.displayTitle=1;
        AccTriggerGrid.loadMulLine(iArray);
    
    }
    catch(ex)
    {
        alert("在LCGrpAccTriggerInit.jsp-->InitAccTriggerGrid函数中发生异常:初始化界面错误!");
    }
}
  
</script> 
