
<script language="JavaScript">  

function initInpBox() 
{
	try
	{
	  
	 fm.all('GrpContNo').value = '<%= tGrpContNo %>';
	 fm.all('RptNo').value = '<%=tRptNo%>';
	}
	catch(ex)
	{
	  alert("��LLGrpClaimEdorTypeCAInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}                                     

function initForm()
{
	initInpBox();
	initLPAccMoveGrid();
	getTotalAcc();
	getAcc();
}


function initLPAccMoveGrid()
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
	  iArray[1][0]="�����˿ͻ���";
	  iArray[1][1]="80px";
	  iArray[1][2]=10;
	  iArray[1][3]=0;	  
	  
	  iArray[2]=new Array();
	  iArray[2][0]="����������";
	  iArray[2][1]="120px";
	  iArray[2][2]=10;
	  iArray[2][3]=0;	  
	  
	  iArray[3]=new Array();
	  iArray[3][0]="��������";
	  iArray[3][1]="80px";
	  iArray[3][2]=10;
	  iArray[3][3]=0;
	  
	  iArray[4]=new Array();
	  iArray[4][0]="�ͻ��Ա�";
	  iArray[4][1]="60px";
	  iArray[4][2]=10;
	  iArray[4][3]=0;	  
	  
	  iArray[5]=new Array();
	  iArray[5][0]="�ۼ��⸶���";
	  iArray[5][1]="120px";
	  iArray[5][2]=10;
	  iArray[5][3]=0;	
	    
	  iArray[6]=new Array();
	  iArray[6][0]="ת�ƽ��";
	  iArray[6][1]="120px";
	  iArray[6][2]=10;
	  iArray[6][3]=1;
	  iArray[6][9]="ת�ƽ��|NOTNULL";
	  
	  iArray[7]=new Array();
	  iArray[7][0]="�˻����";
	  iArray[7][1]="120px";
	  iArray[7][2]=10;
	  iArray[7][3]=0;
	  
	  
	  iArray[8]=new Array();
	  iArray[8][0]="��������";
	  iArray[8][1]="80px";
	  iArray[8][2]=10;
	  iArray[8][3]=0;
	  
	  iArray[9]=new Array();
	  iArray[9][0]="��ͬ��";
	  iArray[9][1]="50px";
	  iArray[9][2]=10;
	  iArray[9][3]=3;
	  
	  iArray[10]=new Array();
	  iArray[10][0]="�ʻ�����";
	  iArray[10][1]="120px";
	  iArray[10][2]=10;
	  iArray[10][3]=3;
	  
	  iArray[11]=new Array();
	  iArray[11][0]="���Ѽƻ�����";
	  iArray[11][1]="50px";
	  iArray[11][2]=10;
	  iArray[11][3]=3;
	  
	  
	  iArray[12]=new Array();
	  iArray[12][0]="���ֺ���";
	  iArray[12][1]="0px";
	  iArray[12][2]=10;
	  iArray[12][3]=3;
	  

	  
	  
	  LPAccMoveGrid=new MulLineEnter( "fm" , "LPAccMoveGrid" );
	  LPAccMoveGrid.hiddenPlus = 1;
	  LPAccMoveGrid.hiddenSubtraction = 1;
	  LPAccMoveGrid.canChk =1;
	  LPAccMoveGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{
	   alert("��LLGrpClaimEdorTypeCAInit.jsp-->initLPAccMoveGrid�����з����쳣:��ʼ���������!");
	}
}

</script>
