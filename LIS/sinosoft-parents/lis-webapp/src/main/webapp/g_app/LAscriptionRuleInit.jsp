<%
/***************************************************************
 * <p>ProName��LAscriptionRuleInit.jsp</p>
 * <p>Title����������ά��</p>
 * <p>Description����������ά��</p>
 * <p>Copyright��Copyright (c) 2012</p>
 * <p>Company��Sinosoft</p>
 * @author   : ���ƴ�
 * @version  : 8.0
 * @date     : 2014-05-09
 ****************************************************************/
%>
<script language="JavaScript">



function initForm(){
	try{
		initButton();
		initInpBox();
		initPositionGrid();
		initAscriptionGrid();
		initSepicalAscriptionGrid();
		initPosition();
		initAscription();
		initSpeAscription();
		querySerialNo();
		
	}
	catch(re){
		alert("Init.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

function initInpBox(){ 
	try{
		fm.SepicalType.value='';
		fm.SepicalTypeName.value='';
		fm.Rate1.value='';
		
		fm.AscriptionCode.value='';
		fm.AscriptionCodeName.value='';
		fm.StartYears.value='';
		fm.EndYears.value='';
		fm.Rate.value='';
	}
	catch(ex){
		alert("��Init.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}
/**
 * ��ʼ����ť
 */
function initButton() {
	
	try {
		if(tQueryFlag=="2" || tQueryFlag=="3"|| tQueryFlag=="4"){
			divAccascriptionSave.style.display = "";
			divRiskSave.style.display = "";
			divSepicalSave.style.display = "";
		}
	} catch (ex) {
		alert("��ʼ����ť����");
	}
}


function initAscriptionGrid () {
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i =0 ;
	try {
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ְ������";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="ְ������";
		iArray[i][1]="60px"; 
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��ʼֵ";
		iArray[i][1]="30px";
		iArray[i][2]=10; 
		iArray[i++][3]=0;
		   
		iArray[i]=new Array();
		iArray[i][0]="��ֵֹ";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="���к�";
		iArray[i][1]="100px";
		iArray[i][2]=10;
		iArray[i++][3]=3;
		
		AscriptionGrid = new MulLineEnter( "fm" , "AscriptionGrid" );
		AscriptionGrid.mulLineCount = 0;
		AscriptionGrid.displayTitle = 1;
		AscriptionGrid.hiddenPlus = 1;
		AscriptionGrid.hiddenSubtraction = 1;
		AscriptionGrid.selBoxEventFuncName ="transData2";
		AscriptionGrid.canSel=1;
		AscriptionGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert(ex);
	}
}

function initSepicalAscriptionGrid () {
	turnPage3 = new turnPageClass();
	var iArray = new Array();
	var i= 0;
	try {
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10; 
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="�������ʹ���"; 
		iArray[i][1]="40px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������������";
		iArray[i][1]="40px"; 
		iArray[i][2]=10; 
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="��������";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="���к�";
		iArray[i][1]="100px";
		iArray[i][2]=10;
		iArray[i++][3]=3;
		
		SepicalAscriptionGrid = new MulLineEnter( "fm" , "SepicalAscriptionGrid" );
		SepicalAscriptionGrid.mulLineCount = 0;
		SepicalAscriptionGrid.displayTitle = 1;
		SepicalAscriptionGrid.hiddenPlus = 1;
		SepicalAscriptionGrid.hiddenSubtraction = 1;
		SepicalAscriptionGrid.selBoxEventFuncName ="transData3";
		SepicalAscriptionGrid.canSel=1;
		SepicalAscriptionGrid.loadMulLine(iArray);
	}
	catch(ex) {
		alert(ex);
	}
}

 function initPositionGrid(){
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i =0 ;
	try {
		iArray[i]=new Array();
		iArray[i][0]="���";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ְ������";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="ְ������";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="������ȼ��㷽ʽ";
		iArray[i][1]="0px";
		iArray[i][2]=10;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="�����������";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="���к�";
		iArray[i][1]="100px"; 
		iArray[i][2]=10;
		iArray[i++][3]=3; 
		
		PositionGrid = new MulLineEnter( "fm" , "PositionGrid" );
		PositionGrid.mulLineCount = 0;
		PositionGrid.displayTitle = 1;
		PositionGrid.hiddenPlus = 1;
		PositionGrid.hiddenSubtraction = 1;
		PositionGrid.selBoxEventFuncName ="transData1";
		PositionGrid.canSel=1;
		PositionGrid.loadMulLine(iArray);

	}
	catch(ex) {
		alert("GrpFeeInit.jsp-->initPositionGrid�����з����쳣:��ʼ���������!");
	}
}
</script>
