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
    	initLPrtPrintLogGrid();
	}
	catch(ex)
	{
		alert("��ʼ������");
	}
}
var LPrtPrintLogGrid;
function initLPrtPrintLogGrid()
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
		iArray[1][0]="��ӡ����";
		iArray[1][1]="35px";
		iArray[1][2]=15;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="��ӡ״̬";
		iArray[2][1]="35px";
		iArray[2][2]=30;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="����Ա";
		iArray[3][1]="35px";
		iArray[3][2]=15;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="��ӡ����";
		iArray[4][1]="35px";
		iArray[4][2]=15;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="��ӡʱ��";
		iArray[5][1]="35px";
		iArray[5][2]=15;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="��ӡ��ע";
		iArray[6][1]="80px";
		iArray[6][2]=15;
		iArray[6][3]=0;
		
		LPrtPrintLogGrid =new MulLineEnter("fm","LPrtPrintLogGrid");
		LPrtPrintLogGrid.mulLineCount =5;
		LPrtPrintLogGrid.displayTitle=1;
		LPrtPrintLogGrid.hiddenPlus=1;
		LPrtPrintLogGrid.hiddenSubtraction=1;
		LPrtPrintLogGrid.canSel=0;
		LPrtPrintLogGrid.canChk=0;
		LPrtPrintLogGrid.loadMulLine(iArray);
}
	catch(ex)
	{
    	alert(ex);
	}
}
</script>




