<%@include file="../i18n/language.jsp"%>


<%
  //�������ƣ�PDSugStaticDocument.jsp
  //�����ܣ����ֺ˱�������
  //�������ڣ�2009-3-14
  //������  ��CM
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script type="text/javascript">

function initForm()
{
	try{
		document.getElementById("ID").value = "";
		document.getElementById("IDName").value = "";
		document.getElementById("BAK").value = "";
		fm.all('FILEPATH').value = "";
		document.getElementById("LANGUAGE").value = "";
		document.getElementById("LANGUAGEName").value = "";
		initMulline10Grid();
		queryMulline10Grid();
		updateDisplayState();
		fm.all("IsReadOnly").value = "<%=session.getAttribute("IsReadOnly")%>";
	}
	catch(re){
		myAlert("PDSugStaticDocument.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
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
		iArray[1][0]="�ĵ�����";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="��������";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="�ļ�";
		iArray[3][1]="90px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="<%=bundle.getString("handword_language")%>";
		iArray[4][1]="40px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		iArray[5]=new Array();
		iArray[5][0]="��ע";
		iArray[5][1]="90px";
		iArray[5][2]=100;
		iArray[5][3]=0;
		

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
		
		document.getElementById("ID").value = Mulline10Grid.getRowColData(selNo-1,1);
		document.getElementById("FILEPATH").value = Mulline10Grid.getRowColData(selNo-1,3);
		document.getElementById("LANGUAGE").value = Mulline10Grid.getRowColData(selNo-1,4);
		document.getElementById("BAK").value = Mulline10Grid.getRowColData(selNo-1,5);
		var sql="select tittle from PD_LMTypeMsg where   id= '"+document.getElementById("ID").value+"'";
		var tNameArr = easyExecSql(sql,1,1,1); 
		showOneCodeName('language','LANGUAGE','LANGUAGEName');
		document.getElementById("IDName").value = tNameArr[0][0];
	}
}
</script>

