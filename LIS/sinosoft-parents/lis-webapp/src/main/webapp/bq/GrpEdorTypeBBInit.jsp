<%

%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
  document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
  document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
  //alert(top.opener.document.all('EdorAcceptNo').value);
  //document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  //alert(top.opener.document.all('EdorNo').value);
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;	
  document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
  document.all('EdorType').value = top.opener.document.all('EdorType').value;
  document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
  document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  document.all('ContNo').value = "";
  document.all('CustomerNo').value = "";	
  }
  catch(ex) {
    //alert("在GEdorTypeICInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                     

function initForm() {
  try {
 
    initInpBox();
    
    initLCInsuredGrid();
    
    initLCInsured2Grid();
    
    QueryEdorInfo();
    
    queryClick2();
  }
  catch(re) {
    //alert("GEdorTypeICInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
    iArray[1][0]="个人保单号";    				//列名1
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="客户号";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="姓名";         			//列名8
    iArray[3][1]="80px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="性别";         			//列名5
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="出生日期";         		//列名6
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="证件类型";         		//列名6
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="证件号码";         		//列名6
    iArray[7][1]="150px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
    //这些属性必须在loadMulLine前
    LCInsuredGrid.mulLineCount = 0;    
    LCInsuredGrid.displayTitle = 1;
    //LCInsuredGrid.canSel = 1;
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
    iArray[1][0]="个人保单号";    				//列名1
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="客户号";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="姓名";         			//列名8
    iArray[3][1]="80px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="性别";         			//列名5
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="出生日期";         		//列名6
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="证件类型";         		//列名6
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="证件号码";         		//列名6
    iArray[7][1]="150px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

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
