<%@include file="../i18n/language.jsp"%>


<%
  //�������ƣ�PDUMInit.jsp
  //�����ܣ����ֺ˱�������
  //�������ڣ�2009-3-14
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		document.getElementById("LANGTYPE").value = "";
		document.getElementById("LANGTYPEName").value = "";
		document.getElementById("NAME").value = "";
		initMulline10Grid();
		queryMulline10Grid();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDSugMultiLanguageInput.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
	}
}


function initMulline10Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="<%=bundle.getString("handword_language")%>";
		iArray[1][1]="30px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��ͷ����";
		iArray[2][1]="40px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		Mulline10Grid = new MulLineEnter( "fm" , "Mulline10Grid" ); 

		Mulline10Grid.mulLineCount=0;
		Mulline10Grid.displayTitle=1;
		Mulline10Grid.canSel=1;
		Mulline10Grid.canChk=0;
		Mulline10Grid.hiddenPlus=1;
		Mulline10Grid.hiddenSubtraction=1;
		Mulline10Grid.selBoxEventFuncName ="updateDisplayState";
		Mulline10Grid.loadMulLine(iArray);
	}
	catch(ex){
		myAlert(ex);
	}
}

function updateDisplayState(){
	var selNo = Mulline10Grid.getSelNo();
	if(selNo == 0){
	
	}
	else{
		document.getElementById("LANGTYPE").value = Mulline10Grid.getRowColData(selNo-1,1);
		document.getElementById("NAME").value = Mulline10Grid.getRowColData(selNo-1,2);
		showOneCodeName('language','LANGTYPE','LANGTYPEName');
	}
}
</script>

