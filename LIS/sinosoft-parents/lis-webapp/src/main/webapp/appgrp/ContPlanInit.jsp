
<%
//�������ƣ�GrpHealthFactoryQueryInit.jsp
//�����ܣ�
//�������ڣ�2004-08-30
//������  ��sunxy
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		document.all('ContPlanCode').value = "";
		document.all('ContPlanName').value = "";
		document.all('RiskCode').value = "";
		document.all('PlanSql').value = "";
		document.all('MainRiskCode').value="";
	}
	catch(ex)
	{
		alert("��ContPlanInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initContPlanCodeGrid();
		initContPlanDutyGrid();
		initContPlanGrid();
		if(this.LoadFlag=="4"||this.LoadFlag=="16")
		{
			document.getElementById("divRiskPlanSave").style.display="none";
		}
		if(this.LoadFlag=="11")
		{
		 document.getElementById("divRiskPlanSave").style.display="none";
		 document.getElementById("divRiskPlanRela").style.display="none";
		}
		if(this.LoadFlag=="18")
		{
		document.getElementById("divRiskPlanSave").style.display="none";
		}	
		//wangxw 20100916 ѯ����Ϣ����
		if( (fm.AskpriceNo.value != "" &&fm.AskpriceNo.value != null&&fm.AskpriceNo.value != 'null' ) &&(fm.AskNo.value!= null&&fm.AskNo.value!= 'null' &&fm.AskNo.value!= "") )
		{
			ImportContPlan();
		}
		easyQueryClick();
	}
	catch(re)
	{
		alert("ContPlanInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

var ContPlanGrid;




// ҪԼ��Ϣ�б�ĳ�ʼ��
function initContPlanGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���ֱ���";
		iArray[2][1]="0px";
		iArray[2][2]=200;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="���α���";
		iArray[3][1]="0px";
		iArray[3][2]= 60;
		iArray[3][3]= 3;
		iArray[3][10]="DutyCode";

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="80px";
		iArray[4][2]=60;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="����Ҫ��";
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=3;
		iArray[5][10]="FactorCode";

		iArray[6]=new Array();
		iArray[6][0]="Ҫ������";
		iArray[6][1]="80px";
		iArray[6][2]=150;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="Ҫ��˵��";
		iArray[7][1]="80px";
		iArray[7][2]=150;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="Ҫ��ֵ";
		iArray[8][1]="50px";
		iArray[8][2]=150;
		iArray[8][3]=1;

		iArray[9]=new Array();
		iArray[9][0]="�ر�˵��";
		iArray[9][1]="20px";
		iArray[9][2]=150;
		iArray[9][3]=1;

		iArray[10]=new Array();
		iArray[10][0]="���ְ汾";
		iArray[10][1]="0px";
		iArray[10][2]=10;
		iArray[10][3]=3;

		iArray[11]=new Array();
		iArray[11][0]="���屣�����ֺ���";
		iArray[11][1]="40px";
		iArray[11][2]=10;
		iArray[11][3]=3;

		iArray[12]=new Array();
		iArray[12][0]="���ձ���";
		iArray[12][1]="0px";
		iArray[12][2]=10;
		iArray[12][3]=3;

		iArray[13]=new Array();
		iArray[13][0]="����";
		iArray[13][1]="0px";
		iArray[13][2]=10;
		iArray[13][3]=3;

		iArray[14]=new Array();
		iArray[14][0]="���㷽��";
		iArray[14][1]="0px";
		iArray[14][2]=10;
		iArray[14][3]=3;

		ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ContPlanGrid.mulLineCount = 1;
		ContPlanGrid.displayTitle = 1;
		ContPlanGrid.hiddenPlus = 1;
		ContPlanGrid.hiddenSubtraction = 0;
		ContPlanGrid.canChk=0;
		ContPlanGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}

//������Ϣ��ʼ��
function initContPlanDutyGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���α���";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="120px";
		iArray[2][2]=150;
		iArray[2][3]=0;
		iArray[2][10]="RiskCode";

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="120px";
		iArray[3][2]= 60;
		iArray[3][3]= 0;

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="120px";
		iArray[4][2]=60;
		iArray[4][3]=0;

		ContPlanDutyGrid = new MulLineEnter( "fm" , "ContPlanDutyGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ContPlanDutyGrid.mulLineCount = 1;
		ContPlanDutyGrid.displayTitle = 1;
		ContPlanDutyGrid.hiddenPlus = 1;
		ContPlanDutyGrid.hiddenSubtraction = 1;
		ContPlanDutyGrid.canChk=1;
		ContPlanDutyGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert(ex);
	}
}

//���ռƻ���ʼ��
function initContPlanCodeGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="�ƻ�����";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="�ƻ�����";
		iArray[2][1]="260px";
		iArray[2][2]=150;
		iArray[2][3]=0;
		iArray[2][10]="RiskCode";
		
		iArray[3]=new Array();
		iArray[3][0]="����˵��";
		iArray[3][1]="0px";
		iArray[3][2]= 60;
		iArray[3][3]= 3;
		
		iArray[4]=new Array();
		iArray[4][0]="�ɱ�����";
		iArray[4][1]="120px";
		iArray[4][2]= 60;
		iArray[4][3]= 0;
		
		ContPlanCodeGrid = new MulLineEnter( "fm" , "ContPlanCodeGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ContPlanCodeGrid.mulLineCount = 1;
		ContPlanCodeGrid.displayTitle = 1;
		ContPlanCodeGrid.hiddenPlus = 1;
		ContPlanCodeGrid.hiddenSubtraction = 1;
		ContPlanCodeGrid.canSel=1;
		ContPlanCodeGrid.selBoxEventFuncName = "ShowContPlan";
		ContPlanCodeGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}


// ҪԼ��Ϣ�б�ĳ�ʼ��
function initContPlanGridyry() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="100px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���ֱ���";
		iArray[2][1]="0px";
		iArray[2][2]=150;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="���α���";
		iArray[3][1]="0px";
		iArray[3][2]= 60;
		iArray[3][3]= 3;
		iArray[3][10]="DutyCode";

		iArray[4]=new Array();
		iArray[4][0]="��������";
		iArray[4][1]="180px";
		iArray[4][2]=60;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="����Ҫ��";
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=3;
		iArray[5][10]="FactorCode";

		iArray[6]=new Array();
		iArray[6][0]="Ҫ������";
		iArray[6][1]="80px";
		iArray[6][2]=150;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="Ҫ��˵��";
		iArray[7][1]="200px";
		iArray[7][2]=150;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="Ҫ��ֵ";
		iArray[8][1]="50px";
		iArray[8][2]=150;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="�ر�˵��";
		iArray[9][1]="80px";
		iArray[9][2]=150;
		iArray[9][3]=0;

		iArray[10]=new Array();
		iArray[10][0]="���ְ汾";
		iArray[10][1]="0px";
		iArray[10][2]=10;
		iArray[10][3]=3;

		iArray[11]=new Array();
		iArray[11][0]="���屣�����ֺ���";
		iArray[11][1]="40px";
		iArray[11][2]=10;
		iArray[11][3]=3;

		iArray[12]=new Array();
		iArray[12][0]="���ձ���";
		iArray[12][1]="0px";
		iArray[12][2]=10;
		iArray[12][3]=3;

		iArray[13]=new Array();
		iArray[13][0]="����";
		iArray[13][1]="0px";
		iArray[13][2]=10;
		iArray[13][3]=3;

		iArray[14]=new Array();
		iArray[14][0]="���㷽��";
		iArray[14][1]="0px";
		iArray[14][2]=10;
		iArray[14][3]=3;

		ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" );
		//��Щ���Ա�����loadMulLineǰ
		ContPlanGrid.mulLineCount = 1;
		ContPlanGrid.displayTitle = 1;
		ContPlanGrid.hiddenPlus = 1;
		ContPlanGrid.hiddenSubtraction = 0;
		ContPlanGrid.canChk=0;
		ContPlanGrid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
</script>
