<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDBaseFieldQueryInit.jsp
  //�����ܣ�������Ϣ�ֶβ�ѯ
  //�������ڣ�2009-3-18
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMulline9Grid();
		queryMulline9Grid();
	}
	catch(re){
		myAlert("PDBaseFieldQueryInit.jsp-->"+"��ʼ���������!");
	}
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��Ʒ������������";
		iArray[1][1]="90px";
		iArray[1][2]=100;
		iArray[1][3]=3;
		
		iArray[2]=new Array();
		iArray[2][0]="��Ʒ��������";
		iArray[2][1]="90px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��Ʒ�����ֶ�������";
		iArray[3][1]="105px";
		iArray[3][2]=100;
		iArray[3][3]=3;
		
		iArray[4]=new Array();
		iArray[4][0]="��Ʒ�����ֶ���";
		iArray[4][1]="105px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="�ֶ�ֵ��������";
		iArray[5][1]="105px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="�Ƿ���ʾ";
		iArray[6][1]="60px";
		iArray[6][2]=100;
		iArray[6][3]=2;

		iArray[7]=new Array();
		iArray[7][0]="�ٷ��ֶ�����";
		iArray[7][1]="90px";
		iArray[7][2]=100;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="ҵ����Ա��ע";
		iArray[8][1]="90px";
		iArray[8][2]=100;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="�ֶ�����";
		iArray[9][1]="90px";
		iArray[9][2]=100;
		iArray[9][3]=3;

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
