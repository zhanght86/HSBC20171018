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
    	initLPrtXmlStyleGrid();
	}
	catch(ex)
	{
		alert("初始化错误");
	}
}
var LPrtXmlStyleGrid;
function initLPrtXmlStyleGrid()
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
		iArray[1][0]="模板号码";
		iArray[1][1]="35px";
		iArray[1][2]=15;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="模板名称";
		iArray[2][1]="35px";
		iArray[2][2]=30;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="语言";
		iArray[3][1]="35px";
		iArray[3][2]=15;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="模板类型";
		iArray[4][1]="35px";
		iArray[4][2]=15;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="输出类型";
		iArray[5][1]="35px";
		iArray[5][2]=15;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="文件";
		iArray[6][1]="80px";
		iArray[6][2]=15;
		iArray[6][3]=0;
		
		LPrtXmlStyleGrid =new MulLineEnter("fm","LPrtXmlStyleGrid");
		LPrtXmlStyleGrid.mulLineCount =5;
		LPrtXmlStyleGrid.displayTitle=1;
		LPrtXmlStyleGrid.hiddenPlus=1;
		LPrtXmlStyleGrid.hiddenSubtraction=1;
		LPrtXmlStyleGrid.canSel=1;
		LPrtXmlStyleGrid.canChk=0;
		LPrtXmlStyleGrid.loadMulLine(iArray);
}
	catch(ex)
	{
    	alert(ex);
	}
}
</script>




