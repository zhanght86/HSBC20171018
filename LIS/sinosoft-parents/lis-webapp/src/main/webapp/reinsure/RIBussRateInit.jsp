<%@include file="/i18n/language.jsp"%>

<%
  //�������ƣ�RIBussRateInit.jsp
  //�����ܣ�����ά��
  //�������ڣ�2011-6-27
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMul11Grid();
	}
	catch(re){
		myAlert("RIBussRateInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMul11Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
        iArray[1][0]="���ʱ��";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=3;

        iArray[2]=new Array();
        iArray[2][0]="�ŷN";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="����ֵ";
        iArray[3][1]="45px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="��Ч����";
        iArray[4][1]="60px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="ʧЧ����";
        iArray[5][1]="60px";
        iArray[5][2]=100;
        iArray[5][3]=0;


		Mul11Grid = new MulLineEnter( "fm" , "Mul11Grid" ); 

		Mul11Grid.mulLineCount=0;
		Mul11Grid.displayTitle=1;
		Mul11Grid.canSel=1;
		Mul11Grid.hiddenPlus=1;
		Mul11Grid.hiddenSubtraction=1;
		Mul11Grid.selBoxEventFuncName = "showFormInfo";
		
		Mul11Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
