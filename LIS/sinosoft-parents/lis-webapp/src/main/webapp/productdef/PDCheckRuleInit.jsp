<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDCheckRuleInit.jsp
  //�����ܣ���������
  //�������ڣ�2009-3-17
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initDisplay();
		initMulline9Grid();
	}
	catch(re){
		myAlert("PDCheckRuleInit.jsp-->"+"��ʼ���������!");
	}
}

function initDisplay()
{
	fm.all("CheckRuleCode").value = "";
	fm.all("CheckRuleName").value = "";
	fm.all("Algo").value = "";
	fm.all("Type").value = "";
	fm.all("Description").value = "";
	
	fm.all("btnSave").disabled = false;
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="������";
		iArray[1][1]="20px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="���";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�����㷨";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="��ע";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="����";
		iArray[6][1]="0";
		iArray[6][2]=100;
		iArray[6][3]=3;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.selBoxEventFuncName = "fill";

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

function fill()
{
	var selRow = Mulline9Grid.getSelNo();
	
	fm.all("CheckRuleCode").value = Mulline9Grid.getRowColData(selRow-1,1);
	fm.all("CheckRuleName").value = Mulline9Grid.getRowColData(selRow-1,2);
	fm.all("Algo").value = Mulline9Grid.getRowColData(selRow-1,4);
	fm.all("Type").value = Mulline9Grid.getRowColData(selRow-1,6);
	fm.all("Description").value = Mulline9Grid.getRowColData(selRow-1,5);
	
	fm.all("btnSave").disabled = true;
}
</script>
