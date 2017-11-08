<script language="JavaScript">

function initInpBox()
{
	try
	{
   
	}
	catch(ex)
	{
    	alert("初始化错误!");
	}
}
function initForm()
{
	try
	{
    	initInpBox();
    	initLPrtTempleteLogGrid();
	}
	catch(ex)
	{
		alert("初始化错误");
	}
}
var LPrtTempleteLogGrid;
function initLPrtTempleteLogGrid()
{
	var iArray=new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=30;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="类型";
		iArray[1][1]="40px";
		iArray[1][2]=15;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="操作类型";
		iArray[2][1]="40px";
		iArray[2][2]=30;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="模板类型";
		iArray[3][1]="40px";
		iArray[3][2]=30;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="语言";
		iArray[4][1]="40px";
		iArray[4][2]=15;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="输出类型";
		iArray[5][1]="40px";
		iArray[5][2]=15;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="操作员";
		iArray[6][1]="40px";
		iArray[6][2]=15;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="操作日期";
		iArray[7][1]="50px";
		iArray[7][2]=15;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="操作时间";
		iArray[8][1]="50px";
		iArray[8][2]=15;
		iArray[8][3]=0;
		
		LPrtTempleteLogGrid =new MulLineEnter("fm","LPrtTempleteLogGrid");
		LPrtTempleteLogGrid.mulLineCount =5;
		LPrtTempleteLogGrid.displayTitle=1;
		LPrtTempleteLogGrid.hiddenPlus=1;
		LPrtTempleteLogGrid.hiddenSubtraction=1;
		LPrtTempleteLogGrid.canSel=0;
		LPrtTempleteLogGrid.canChk=0;
		LPrtTempleteLogGrid.loadMulLine(iArray);
}
	catch(ex)
	{
    	alert(ex);
	}
}
</script>




