<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIAlgorithmSetInit.jsp
  //�����ܣ������㷨������
  //�������ڣ�2011/6/16
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initAlgLibMul11Grid();
		initAlgSetMul11Grid();
	}
	catch(re){
		myAlert("RIAlgorithmSetInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initAlgLibMul11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�㷨����";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�㷨����";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�㷨����";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="ҵ������";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�¼�����";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�㷨����";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		AlgLibMul11Grid = new MulLineEnter( "fm" , "AlgLibMul11Grid" ); 

		AlgLibMul11Grid.mulLineCount=1;
		AlgLibMul11Grid.displayTitle=1;
		AlgLibMul11Grid.canSel=1;
		AlgLibMul11Grid.canChk=0;
		AlgLibMul11Grid.hiddenPlus=1;
		AlgLibMul11Grid.hiddenSubtraction=1;

		AlgLibMul11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
function initAlgSetMul11Grid()
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
		iArray[2][0]="�㷨����";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�㷨����";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�㷨����";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="ҵ������";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�¼�����";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=0;


		AlgSetMul11Grid = new MulLineEnter( "fm" , "AlgSetMul11Grid" ); 

		AlgSetMul11Grid.mulLineCount=1;
		AlgSetMul11Grid.displayTitle=1;
		AlgSetMul11Grid.canSel=0;
		AlgSetMul11Grid.canChk=1;
		AlgSetMul11Grid.hiddenPlus=1;
		AlgSetMul11Grid.hiddenSubtraction=1;

		AlgSetMul11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
