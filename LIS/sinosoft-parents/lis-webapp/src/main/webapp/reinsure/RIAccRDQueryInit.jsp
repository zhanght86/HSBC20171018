<%@include file="/i18n/language.jsp"%>
<%
  //�������ƣ�RIAccRDQueryInit.jsp
  //�����ܣ��ֳ����ζ���-��Ϣ��ѯ
  //�������ڣ�2011/6/16
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initRIAccumulateGrid();
	}
	catch(re){
		myAlert("RIAccRDQueryInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initRIAccumulateGrid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�ֳ����α���";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�ֳ���������";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ֳ�����״̬";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="���ݲɼ���������";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;


		RIAccumulateGrid = new MulLineEnter( "fm" , "RIAccumulateGrid" ); 

		RIAccumulateGrid.mulLineCount=1;
		RIAccumulateGrid.displayTitle=1;
		RIAccumulateGrid.canSel=1;
		RIAccumulateGrid.canChk=0;
		RIAccumulateGrid.hiddenPlus=1;
		RIAccumulateGrid.hiddenSubtraction=1;

		RIAccumulateGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
