<script language="JavaScript">

function initInpBox()
{
	try
	{
   
	}
	catch(ex)
	{
    	alert("��ʼ������!");
	}
}
function initForm()
{
	try
	{
    	initInpBox();
    	initLPrtPrintTestGrid();
	}
	catch(ex)
	{
		alert("��ʼ������");
	}
}
var LPrtPrintTestGrid;
function initLPrtPrintTestGrid()
{
	var iArray=new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=30;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="ģ�����";
		iArray[1][1]="60px";
		iArray[1][2]=15;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="ģ������";
		iArray[2][1]="60px";
		iArray[2][2]=30;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="����";
		iArray[3][1]="60px";
		iArray[3][2]=15;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="ģ������";
		iArray[4][1]="60px";
		iArray[4][2]=15;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="�������";
		iArray[5][1]="60px";
		iArray[5][2]=15;
		iArray[5][3]=0;
		
		LPrtPrintTestGrid =new MulLineEnter("fm","LPrtPrintTestGrid");
		LPrtPrintTestGrid.mulLineCount =5;
		LPrtPrintTestGrid.displayTitle=1;
		LPrtPrintTestGrid.hiddenPlus=1;
		LPrtPrintTestGrid.hiddenSubtraction=1;
		LPrtPrintTestGrid.canSel=1;
		LPrtPrintTestGrid.canChk=0;
		LPrtPrintTestGrid.loadMulLine(iArray);
}
	catch(ex)
	{
    	alert(ex);
	}
}
</script>




