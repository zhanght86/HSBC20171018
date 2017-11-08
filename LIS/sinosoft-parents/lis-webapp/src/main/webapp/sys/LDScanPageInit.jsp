
<script language="JavaScript">


function initForm()
{
	try
	{   
		initScanGrid(); 
		SubTypeQuery();
	}
	catch(ex)
	{
		alert("AllGBqQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}  

function initScanGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";      
		iArray[0][1]="30px";            
		iArray[0][2]=10;                      
		iArray[0][3]=0; 

		iArray[1]=new Array();
		iArray[1][0]="影像件类型";
		iArray[1][1]="145px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="影像件类型名称";
		iArray[2][1]="145px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="定义页面";
		iArray[3][1]="0px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="影像页数";
		iArray[4][1]="120px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="操作员";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="入机日期";
		iArray[6][1]="80px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="入机时间";
		iArray[7][1]="80px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		
		ScanGrid = new MulLineEnter("document", "ScanGrid");
		//这些属性必须在loadMulLine前
		ScanGrid.mulLineCount = 5;
		ScanGrid.displayTitle = 1;
		ScanGrid.hiddenSubtraction=1;
		ScanGrid.hiddenPlus = 1;
		ScanGrid.locked = 1;
		ScanGrid.canSel = 1;
		ScanGrid.loadMulLine(iArray);
		//PolGrid. selBoxEventFuncName = "PBqQueryClick";
	}
	catch(ex)
	{
		alert(ex);
	}
} 

</script>
