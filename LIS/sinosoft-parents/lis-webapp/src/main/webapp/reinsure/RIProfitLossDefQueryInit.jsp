<%@include file="/i18n/language.jsp"%>
 <%
  //�������ƣ�RIProfitLossDefQueryInit.jsp
  //�����ܣ�ӯ��Ӷ�����ѯ
  //�������ڣ�2011/8/20
  //������  ��
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm(){
	try{
		initMulDefQueryGrid();
	}
	catch(re){
		myAlert("RIProfitLossDefQueryInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMulDefQueryGrid(){
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="ӯ��Ӷ�����";
		iArray[1][1]="105px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="ӯ��Ӷ������";
		iArray[2][1]="105px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ٱ���˾";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�ٱ���ͬ";
		iArray[4][1]="60px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="��������";
		iArray[5][1]="60px";
		iArray[5][2]=100;
		iArray[5][3]=0;


		MulDefQueryGrid = new MulLineEnter( "fm" , "MulDefQueryGrid" ); 

		MulDefQueryGrid.mulLineCount=1;
		MulDefQueryGrid.displayTitle=1;
		MulDefQueryGrid.canSel=1;
		MulDefQueryGrid.canChk=0;
		MulDefQueryGrid.hiddenPlus=1;
		MulDefQueryGrid.hiddenSubtraction=1;

		MulDefQueryGrid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
