<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIAthSchemaQueryInit.jsp
  //�����ܣ��㷨������ѯ
  //�������ڣ�2011/6/17
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMul10Grid();
	}
	catch(re){
		myAlert("RIAthSchemaQueryInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMul10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��������";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�������ͱ���";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=3;
				
		iArray[5]=new Array();
		iArray[5][0]="����״̬";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		Mul10Grid = new MulLineEnter( "fm" , "Mul10Grid" ); 

		Mul10Grid.mulLineCount=1;
		Mul10Grid.displayTitle=1;
		Mul10Grid.canSel=1;
		Mul10Grid.canChk=0;
		Mul10Grid.hiddenPlus=1;
		Mul10Grid.hiddenSubtraction=1;

		Mul10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}

</script>