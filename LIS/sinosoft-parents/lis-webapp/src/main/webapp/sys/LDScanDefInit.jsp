
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
		alert("AllGBqQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}  

function initScanGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";      
		iArray[0][1]="30px";            
		iArray[0][2]=10;                      
		iArray[0][3]=0; 

		iArray[1]=new Array();
		iArray[1][0]="Ӱ�������";
		iArray[1][1]="145px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="Ӱ�����������";
		iArray[2][1]="145px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="����ҳ��";
		iArray[3][1]="80px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="Ӱ��ҳ��";
		iArray[4][1]="120px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="����Ա";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�������";
		iArray[6][1]="80px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="���ʱ��";
		iArray[7][1]="80px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		
		ScanGrid = new MulLineEnter("document", "ScanGrid");
		//��Щ���Ա�����loadMulLineǰ
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
