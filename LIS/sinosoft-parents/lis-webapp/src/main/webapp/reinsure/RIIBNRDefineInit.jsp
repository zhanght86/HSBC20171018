<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIIBNRDefineInit.jsp
  //�����ܣ�IBNR����ά��
  //�������ڣ�2011-7-30
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
		myAlert("RIIBNRDefineInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="�ٱ���ͬ���";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�ٱ���ͬ����";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="������������";
		iArray[4][1]="90px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="���ִ���";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=2;

		iArray[6]=new Array();
		iArray[6][0]="��������";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="INBR����";
		iArray[7][1]="90px";
		iArray[7][2]=100;
		iArray[7][3]=0;


		Mul10Grid = new MulLineEnter( "fm" , "Mul10Grid" ); 

		Mul10Grid.mulLineCount=1;
		Mul10Grid.displayTitle=1;
		Mul10Grid.canSel=0;
		Mul10Grid.canChk=0;
		Mul10Grid.hiddenPlus=0;
		Mul10Grid.hiddenSubtraction=0;

		Mul10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>

