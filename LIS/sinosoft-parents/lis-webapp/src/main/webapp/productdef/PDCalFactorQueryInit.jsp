<%@include file="../i18n/language.jsp"%>
<%
  //�������ƣ�PDCalFactorQueryInit.jsp
  //�����ܣ�����Ҫ����ʾ��ѯ����
  //�������ڣ�2009-3-18
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		initMulline9Grid();
		fm.selectTable.value = '<%=request.getParameter("selectTable")%>';
	}
	catch(re){
		myAlert("PDCalFactorQueryInit.jsp-->"+"��ʼ���������!");
	}
}

function initMulline9Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=200;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="Ҫ�����";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="Ҫ��ģ��";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		iArray[3]=new Array();
		iArray[3][0]="Ҫ������";
		iArray[3][1]="150px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="Ҫ�ش���";
		iArray[4][1]="100px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="Ҫ��ֵ��������";
		iArray[5][1]="105px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="Ҫ�غ���˵��";
		iArray[6][1]="150px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="Ҫ������code";
		iArray[7][1]="0";
		iArray[7][2]=100;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="Ҫ��ģ��code";
		iArray[8][1]="0";
		iArray[8][2]=100;
		iArray[8][3]=3;
		
		iArray[9]=new Array();
		iArray[9][0]="Ҫ������code";
		iArray[9][1]="0";
		iArray[9][2]=100;
		iArray[9][3]=3;

		Mulline9Grid = new MulLineEnter( "fm" , "Mulline9Grid" ); 

		Mulline9Grid.mulLineCount=0;
		Mulline9Grid.displayTitle=1;
		Mulline9Grid.canSel=0;
		Mulline9Grid.canChk=1;
		Mulline9Grid.hiddenPlus=1;
		Mulline9Grid.hiddenSubtraction=1;
		Mulline9Grid.chkBoxEventFuncName = "checkedEvent";
		Mulline9Grid.chkBoxAllEventFuncName = "checkedEvent";

		Mulline9Grid.loadMulLine(iArray);

	}
	catch(ex){
		myAlert(ex);
	}
}


</script>
