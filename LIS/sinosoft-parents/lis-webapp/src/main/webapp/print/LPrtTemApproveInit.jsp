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
	    document.all("State").value = "0";
	    document.all("ApproveState").value = "ͣ��";
		initInpBox();
		initLPrtTempleteGrid();
	}
	catch(ex)
	{
		alert("��ʼ������");
	}
}
var LPrtTempleteGrid;
function initLPrtTempleteGrid()
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
		iArray[1][1]="50px";
		iArray[1][2]=15;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="ģ������";
		iArray[2][1]="50px";
		iArray[2][2]=30;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="����";
		iArray[3][1]="50px";
		iArray[3][2]=15;
		iArray[3][3]=0;
		
		iArray[4]=new Array();
		iArray[4][0]="ģ������";
		iArray[4][1]="50px";
		iArray[4][2]=15;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="�������";
		iArray[5][1]="50px";
		iArray[5][2]=30;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="�ļ�";
		iArray[6][1]="100px";
		iArray[6][2]=30;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="����״̬";
		iArray[7][1]="0px";
		iArray[7][2]=30;
		iArray[7][3]=3;
		
		iArray[8]=new Array();
		iArray[8][0]="����״̬";
		iArray[8][1]="0px";
		iArray[8][2]=30;
		iArray[8][3]=3;
		
		LPrtTempleteGrid =new MulLineEnter("fm","LPrtTempleteGrid");
		LPrtTempleteGrid.mulLineCount=5;
		LPrtTempleteGrid.displayTitle=1;
		LPrtTempleteGrid.hiddenPlus=1;
		LPrtTempleteGrid.hiddenSubtraction=1;
		LPrtTempleteGrid.selBoxEventFuncName = "getQueryResult";
		LPrtTempleteGrid.canSel=1;
		LPrtTempleteGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>




