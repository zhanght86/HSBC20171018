<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDAlgoTempLibQueryInit.jsp
  //�����ܣ��㷨ģ����ѯ����
  //�������ڣ�2009-3-18
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMulline9Grid();
	}
	catch(re){
		myAlert("PDAlgoTempLibQueryInit.jsp-->"+"��ʼ���������!");
	}
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="0px";
		iArray[0][2]=200;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�㷨ģ�����";
		iArray[1][1]="35px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="�㷨ģ������";
		iArray[2][1]="70px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�㷨ģ������";
		iArray[3][1]="35px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="�㷨ģ������";
		iArray[4][1]="150px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�㷨��ʽ";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;


		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=1;
		Mulline9Grid.canChk=0;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}
</script>
