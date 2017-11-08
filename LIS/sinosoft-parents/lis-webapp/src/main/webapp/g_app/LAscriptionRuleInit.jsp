<%
/***************************************************************
 * <p>ProName：LAscriptionRuleInit.jsp</p>
 * <p>Title：归属规则维护</p>
 * <p>Description：归属规则维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 蔡云聪
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
		alert("Init.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
		alert("在Init.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}
/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		if(tQueryFlag=="2" || tQueryFlag=="3"|| tQueryFlag=="4"){
			divAccascriptionSave.style.display = "";
			divRiskSave.style.display = "";
			divSepicalSave.style.display = "";
		}
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}


function initAscriptionGrid () {
	turnPage2 = new turnPageClass();
	var iArray = new Array();
	var i =0 ;
	try {
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="职级代码";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="职级名称";
		iArray[i][1]="60px"; 
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="起始值";
		iArray[i][1]="30px";
		iArray[i][2]=10; 
		iArray[i++][3]=0;
		   
		iArray[i]=new Array();
		iArray[i][0]="终止值";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="归属比例";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="序列号";
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
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10; 
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="归属类型代码"; 
		iArray[i][1]="40px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="归属类型名称";
		iArray[i][1]="40px"; 
		iArray[i][2]=10; 
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="归属比例";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0; 
		
		iArray[i]=new Array();
		iArray[i][0]="序列号";
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
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="职级代码";
		iArray[i][1]="30px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="职级名称";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="归属年度计算方式";
		iArray[i][1]="0px";
		iArray[i][2]=10;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="归属年度名称";
		iArray[i][1]="60px";
		iArray[i][2]=10;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="序列号";
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
		alert("GrpFeeInit.jsp-->initPositionGrid函数中发生异常:初始化界面错误!");
	}
}
</script>
