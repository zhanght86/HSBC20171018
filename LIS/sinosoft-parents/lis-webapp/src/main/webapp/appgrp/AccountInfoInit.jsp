
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
		//document.all('PrtNo').value = opener.fm.PrtNo.value;
//alert("PrtNo"+document.all('PrtNo').value);
		//document.all('ContPlanName').value = "";
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
		initAccountInfoGrid();
		easyQueryClick()
	}
	catch(re)
	{
		alert("ContPlanInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}


//���ռƻ���ʼ��
function initAccountInfoGrid() {
	var iArray = new Array();

	try {
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;
		
		iArray[1]=new Array();
		iArray[1][0]="�ӹ�˾����";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="�ӹ�˾����";
		iArray[2][1]="200px";
		iArray[2][2]=150;
		iArray[2][3]=0;
		iArray[2][10]="RiskCode";
		
		iArray[3]=new Array();
		iArray[3][0]="��ҵ���";
		iArray[3][1]="100px";
		iArray[3][2]= 60;
		iArray[3][3]= 0;
		
		iArray[4]=new Array();
		iArray[4][0]="��λ����";
		iArray[4][1]="100px";
		iArray[4][2]= 60;
		iArray[4][3]= 0;
		
		iArray[5]=new Array();
		iArray[5][0]="Ա������";
		iArray[5][1]="100px";
		iArray[5][2]= 60;
		iArray[5][3]= 0;
		
		iArray[6]=new Array();
		iArray[6][0]="��λ��ַ����";
		iArray[6][1]="100px";
		iArray[6][2]= 60;
		iArray[6][3]= 0;						
		
		AccountInfoGrid = new MulLineEnter( "fm" , "AccountInfoGrid" );
		//��Щ���Ա�����loadMulLineǰ
		AccountInfoGrid.mulLineCount = 0;
		AccountInfoGrid.displayTitle = 1;
		AccountInfoGrid.hiddenPlus = 1;
		AccountInfoGrid.hiddenSubtraction = 1;
		AccountInfoGrid.canSel=1;
		AccountInfoGrid.selBoxEventFuncName = "ChooseAccount";
		AccountInfoGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

</script>
