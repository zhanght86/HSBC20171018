<script language="JavaScript">

function initInpBox()
{
	try
	{
   
	}
	catch(ex)
	{
		alert("³õÊ¼»¯´íÎó!");
	}
}
function initForm()
{
	try
	{
		initInpBox();
    	initLPrtDefinitionGrid();
	}
	catch(ex)
	{
    	alert("³õÊ¼»¯´íÎó");
	}
}
var LPrtDefinitionGrid;
function initLPrtDefinitionGrid()
{
	var iArray=new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="ÐòºÅ";
		iArray[0][1]="30px";
		iArray[0][2]=30;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="´òÓ¡ºÅÂë";
		iArray[1][1]="60px";
		iArray[1][2]=15;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="´òÓ¡Ãû³Æ";
		iArray[2][1]="60px";
		iArray[2][2]=30;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="´òÓ¡¶ÔÏó";
		iArray[3][1]="60px";
		iArray[3][2]=15;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="´òÓ¡ÀàÐÍ";
		iArray[4][1]="60px";
		iArray[4][2]=15;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="´òÓ¡ÓïÑÔ";
		iArray[5][1]="60px";
		iArray[5][2]=15;
		iArray[5][3]=0;
		
		LPrtDefinitionGrid =new MulLineEnter("fm","LPrtDefinitionGrid");
		LPrtDefinitionGrid.mulLineCount=5;
		LPrtDefinitionGrid.displayTitle=1;
		LPrtDefinitionGrid.hiddenPlus=1;
		LPrtDefinitionGrid.hiddenSubtraction=1;
		LPrtDefinitionGrid.canSel=1;
		LPrtDefinitionGrid.canChk=0;
		LPrtDefinitionGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>




