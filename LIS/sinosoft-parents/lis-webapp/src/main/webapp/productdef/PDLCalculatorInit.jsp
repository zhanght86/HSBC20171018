<%
  //�������ƣ�PDLCalculatorInit.jsp
  //�����ܣ��ۼ�������
  //�������ڣ�2016-5-12
  //������  ��W-HC
  //���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
/*ҳ���ʼ��*/
function initForm()
{
	try{
		//ҳ���ʼ��
		initBox();
		/*��ȡҳ����ת���ݵĲ���*/
		var riskcode="<%=request.getParameter("riskcode")%>";
		var getdutycode="<%=request.getParameter("getdutycode2")%>";
		document.all("RiskCode").value=riskcode;
		document.all("GetDutyCode").value=getdutycode;
		initMulline1Grid();
		initMulline2Grid();
		initMulline3Grid();
		/*��ѯ�ø����������ϲ㼶���ۼ���*/
		queryLCalculatorInfo();
		/*��ѯ�ø������β㼶���ۼ���*/
		queryLCalculatorInfo1();
	}
	catch(re){
		alert("PDLCalculatorInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}



/*���������β���Ϣ*/
function initMulline2Grid(){
	var iArray = new Array();
	var i=0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ۼ�������";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ۼ����㼶";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="��ϸ��ˮ��";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���ֱ���";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���α���";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		
		iArray[++i]=new Array();
		iArray[i][0]="�������α���";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ۼ�������";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		Mulline2Grid = new MulLineEnter( "fm" , "Mulline2Grid" ); 

		Mulline2Grid.mulLineCount=0;
		Mulline2Grid.displayTitle=1;
		Mulline2Grid.canSel=1;
		Mulline2Grid.canChk=0;
		Mulline2Grid.hiddenPlus=1;
		Mulline2Grid.hiddenSubtraction=1;
		Mulline2Grid.selBoxEventFuncName ="showLCalculatorData";

		Mulline2Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
/*���������β����ϵ��ۼ�����Ϣ��Ϣ*/
function initMulline1Grid(){
	var iArray = new Array();
	var i=0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ۼ�������";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="�ۼ����㼶";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���ֱ���";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="���α���";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		
		iArray[++i]=new Array();
		iArray[i][0]="�������α���";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		
		iArray[++i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="0px";
		iArray[i][2]=100;
		iArray[i][3]=0;
		

		Mulline1Grid = new MulLineEnter( "fm" , "Mulline1Grid" ); 

		Mulline1Grid.mulLineCount=0;
		Mulline1Grid.displayTitle=1;
		Mulline1Grid.canSel=1;
		Mulline1Grid.canChk=0;
		Mulline1Grid.hiddenPlus=1;
		Mulline1Grid.hiddenSubtraction=1;
		Mulline1Grid.selBoxEventFuncName ="showLCalculatorDataInfo";
		Mulline1Grid.loadMulLine(iArray);

	}
	catch(ex){
		alert(ex);
	}
}
function initMulline3Grid()
{
	var iArray = new Array();
	try{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=100;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="�˵�����";
		iArray[1][1]="60px";
		iArray[1][2]=100;
		iArray[1][3]=1;
		iArray[1][4]="pd_llmedfeetype";
		iArray[1][5]="1";     //���ô����Ӧ�ڼ��У�'|'Ϊ�ָ��
	    iArray[1][6]="0";    //��������з������ô����еڼ�λֵ

		Mulline3Grid= new MulLineEnter( "fm" , "Mulline3Grid" ); 

		Mulline3Grid.mulLineCount=0;
		Mulline3Grid.displayTitle=1;
		Mulline3Grid.canSel=0;
		Mulline3Grid.canChk=0;
		Mulline3Grid.hiddenPlus=0;
		Mulline3Grid.hiddenSubtraction=0; 
		Mulline3Grid.loadMulLine(iArray);
	}
	catch(ex){
		alert(ex);
	}
}
//ҳ���ʼ��
function initBox(){
	document.all("FlagStr").value='';
	document.getElementById('CalculatorCode').value='';
	document.getElementById('CalculatorName').value='';
	document.getElementById('CtrlFactorValue').value='';
	document.getElementById('CalCode').value='';
	document.getElementById('DefaultValue').value='';
	document.getElementById('CalOrder').value='';
	document.getElementById('CalculatorCodeAfter').value='';
	document.getElementById('ValidPeriod').value='';
	document.getElementById('selfPayPercent').value='';
	document.getElementById('IfPayMoney').value='';
	$("input.codeno").val("");
	$("input.codename").val("");
	$("input.coolDatePicker").val("");
	initCalCodeMain('','');
}
</script>
