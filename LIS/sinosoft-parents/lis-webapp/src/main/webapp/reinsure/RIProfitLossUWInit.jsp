<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIProfitLossUWInit.jsp
  //�����ܣ�ӯ��Ӷ�����
  //�������ڣ�2011/8/18
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm(){
	try{
		
		fm.RIProfitNo.value="";
		fm.RIcomCode.value="";
		fm.ContNo.value="";
		fm.RIProfitName.value="";
		fm.RIcomName.value="";
		fm.ContName.value="";
		
		
		initMul9Grid();
		initMul10Grid();
	}
	catch(re){
		myAlert("RIProfitLossUWInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMul9Grid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="���������ѱ���";
		iArray[1][1]="105px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ٱ���˾����";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=3;

		iArray[4]=new Array();
		iArray[4][0]="�ٱ���˾����";
		iArray[4][1]="120px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�ٱ���ͬ����";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="�ٱ���ͬ����";
		iArray[6][1]="120px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="���������ѽ��";
		iArray[7][1]="100px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="�ұ�";
		iArray[8][1]="60px";
		iArray[8][2]=100;
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="״̬";
		iArray[9][1]="60px";
		iArray[9][2]=100;
		iArray[9][3]=0;


		Mul9Grid = new MulLineEnter( "fm" , "Mul9Grid" ); 

		Mul9Grid.mulLineCount=0;
		Mul9Grid.displayTitle=1;
		Mul9Grid.canSel=1;
		Mul9Grid.canChk=0;
		Mul9Grid.hiddenPlus=1;
		Mul9Grid.hiddenSubtraction=1;
		Mul9Grid.selBoxEventFuncName = "queryClickDetail";

		Mul9Grid.loadMulLine(iArray);
		

	}
	catch(ex){
		myAlert(ex);
	}
}
function initMul10Grid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="����֧������";
		iArray[1][1]="45px";
		iArray[1][2]=100;
		iArray[1][3]=3;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="45px";
		iArray[2][2]=100;
		iArray[2][3]=3;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="¼������";
		iArray[4][1]="70px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="����ֵ";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="�ұ�";
		iArray[6][1]="45px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		iArray[7]=new Array();
		iArray[7][0]="�ŷN";
		iArray[7][1]="45px";
		iArray[7][2]=100;
		iArray[7][3]=3;
		
		iArray[8]=new Array();
		iArray[8][0]="֧������";
		iArray[8][1]="10px";
		iArray[8][2]=100;
		iArray[8][3]=3;


		Mul10Grid = new MulLineEnter( "fm" , "Mul10Grid" ); 

		Mul10Grid.mulLineCount=0;
		Mul10Grid.displayTitle=1;
		Mul10Grid.canSel=0;
		Mul10Grid.canChk=0;
		Mul10Grid.hiddenPlus=1;
		Mul10Grid.hiddenSubtraction=1;
		Mul10Grid.recordNo=0;
		Mul10Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
