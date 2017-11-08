<%
//GEdorTypeRiskDetailInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>                       

<script language="JavaScript">  

function initInpBox() { 

	//alert("This is GEdorTypeRiskDetailInit.jsp->initInpBox");
  try {        
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.all('EdorNo').value       = top.opener.document.all('EdorNo').value;
	document.all('GrpContNo').value    = top.opener.document.all('ContNoApp').value;		
	document.all('EdorType').value     = top.opener.document.all('EdorType').value;
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
  document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
	document.all('ContNo').value       = "";
	document.all('CustomerNo').value   = "";
  }
  catch(ex) {
    alert("在GEdorTypeICInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                     

//================
function initForm() {

	//alert("This is GEdorTypeRiskDetailInit.jsp -> initForm");
  try {
    initInpBox();    
    initLCInsured0Grid();    
    queryClick0();
    initLCInsuredGrid();   
    initLCInsured2Grid();    
    QueryEdorInfo();
  }
  catch(re) {
    alert("GEdorTypeRiskDetailInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//=====================

// 信息列表的初始化
function initLCInsured0Grid() {

  var iArray = new Array();      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[1]=new Array();
    iArray[1][0]="团体险种保单号";		//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[1][1]="120px";         		//列宽
    iArray[1][2]=100;          				//列最大值
    iArray[1][3]=0;            				//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="险种编码";					//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[2][1]="100px";         		//列宽
    iArray[2][2]=100;          				//列最大值
    iArray[2][3]=0;            				//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="险种名称";    			//列名
    iArray[3][1]="260px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="变更人次";         	//列名
    iArray[4][1]="120px";           	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="变更保费";       		//列名
    iArray[5][1]="120px";          		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="申请状态";        	//列名
    iArray[6][1]="120px";          		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    LCInsured0Grid = new MulLineEnter( "fm" , "LCInsured0Grid" ); 
    
    //这些属性必须在loadMulLine前
    LCInsured0Grid.mulLineCount = 0;    
    LCInsured0Grid.displayTitle = 1;
    LCInsured0Grid.canSel = 1;
    LCInsured0Grid.hiddenPlus = 1; 
    LCInsured0Grid.hiddenSubtraction = 1;
    LCInsured0Grid.selBoxEventFuncName = "reportDetail0Click";
    LCInsured0Grid.detailInfo = "单击显示详细信息";
    LCInsured0Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert("Big error");
  }
}

// 信息列表的初始化
function initLCInsuredGrid() {
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="保单号";    				//列名1
    iArray[1][1]="150px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="客户号";         		//列名2
    iArray[2][1]="80px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="姓名";         			//列名3
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="性别";         			//列名4
    iArray[4][1]="50px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="出生日期";         	//列名5
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="证件类型";         	//列名6
    iArray[6][1]="80px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="证件号码";         	//列名7
    iArray[7][1]="150px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="保单险种号码";      //列名8
    iArray[8][1]="0px";          		  //列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
    
    //这些属性必须在loadMulLine前
    LCInsuredGrid.mulLineCount = 0;    
    LCInsuredGrid.displayTitle = 1;
    LCInsuredGrid.canSel = 1;
    LCInsuredGrid.hiddenPlus = 1; 
    LCInsuredGrid.hiddenSubtraction = 1;
    LCInsuredGrid.selBoxEventFuncName = "reportDetailClick";   
    LCInsuredGrid.detailInfo = "单击显示详细信息";
    LCInsuredGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// 信息列表的初始化
function initLCInsured2Grid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="保单号";    				//列名1
    iArray[1][1]="150px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="客户号";         		//列名2
    iArray[2][1]="80px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="姓名";         			//列名3
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="性别";         			//列名4
    iArray[4][1]="50px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="出生日期";         	//列名5
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="证件类型";         	//列名6
    iArray[6][1]="80px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="证件号码";         	//列名7
    iArray[7][1]="150px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="保单险种号码";      //列名8
    iArray[8][1]="0px";          		  //列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    LCInsured2Grid = new MulLineEnter( "fm" , "LCInsured2Grid" ); 
    
    //这些属性必须在loadMulLine前
    LCInsured2Grid.mulLineCount = 0;   
    LCInsured2Grid.displayTitle = 1;
    LCInsured2Grid.canSel = 1;
    LCInsured2Grid.hiddenPlus = 1; 
    LCInsured2Grid.hiddenSubtraction = 1;
    LCInsured2Grid.selBoxEventFuncName = "reportDetail2Click";
    LCInsured2Grid.detailInfo = "单击显示详细信息";
    LCInsured2Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
