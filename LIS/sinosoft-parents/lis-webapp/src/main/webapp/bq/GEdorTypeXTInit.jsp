<%
//GEdorTypeXTInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>                       

<script language="JavaScript">  

function initInpBox() { 
  try {        
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;	
    document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  }
  catch(ex) {
    alert("在GEdorTypeXTInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                     

function initForm() {
	lockScreen('lkscreen'); 
  try {
  	
    initInpBox();
    initLCInsuredGrid();  
    initLCInsured2Grid();
    QueryEdorInfo();
    queryClick();    
  	queryPEdorList(); 
  	queryGrpInfo(); 
		divGroupPol2.style.display='none';
		unlockScreen('lkscreen');
  }
  catch(re) {
    alert("GEdorTypeXTInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    unlockScreen('lkscreen');
  }
  
}

// 信息列表的初始化
function initLCInsuredGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			  //列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="个人保单号";    	  //列名1
    iArray[1][1]="110px";            	//列宽
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
    iArray[4][0]="性别";         		  //列名4
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
    iArray[8][0]="个人保单总保费/元"; //列名8
    iArray[8][1]="100px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
	iArray[9][0]="币种";          
	iArray[9][1]="50px";              
	iArray[9][2]=60;             
	iArray[9][3]=2;               
	iArray[9][4]="Currency";                 
	iArray[9][9]="币种|code:Currency";

    

    LCInsuredGrid = new MulLineEnter( "fm" , "LCInsuredGrid" ); 
    
    //这些属性必须在loadMulLine前
    LCInsuredGrid.mulLineCount = 0;    
    LCInsuredGrid.displayTitle = 1;
    LCInsuredGrid.canChk = 0;
    LCInsuredGrid.canSel = 1;
    LCInsuredGrid.hiddenPlus = 1; 
    LCInsuredGrid.hiddenSubtraction = 1;
     LCInsuredGrid.selBoxEventFuncName = "queryPolGrid";
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
    iArray[0][2]=10;          		  	//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="个人保单号";    	  //列名1
    iArray[1][1]="100px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="客户号";         		//列名2
    iArray[2][1]="80px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="姓名";         			//列名3
    iArray[3][1]="50px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="性别";         		  //列名4
    iArray[4][1]="30px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="出生日期";         	//列名5
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="证件类型";         	//列名6
    iArray[6][1]="50px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="证件号码";         	//列名7
    iArray[7][1]="150px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="保单生效日期";         	//列名8
    iArray[8][1]="80px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="个人保单总保费/元";         		  //列名9
    iArray[9][1]="100px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="协议退费金额";        		//列名10
    iArray[10][1]="100px";            	//列宽
    iArray[10][2]=100;            		//列最大值
    iArray[10][3]=0;              		//是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
	iArray[11][0]="币种";          
	iArray[11][1]="50px";              
	iArray[11][2]=60;             
	iArray[11][3]=2;               
	iArray[11][4]="Currency";                 
	iArray[11][9]="币种|code:Currency";

    

    LCInsured2Grid = new MulLineEnter( "fm" , "LCInsured2Grid" ); 
    
    //这些属性必须在loadMulLine前
    LCInsured2Grid.mulLineCount = 0;   
    LCInsured2Grid.displayTitle = 1;
    LCInsured2Grid.canChk = 1; 
    LCInsured2Grid.hiddenPlus = 1; 
    LCInsured2Grid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    LCInsured2Grid.detailInfo = "单击显示详细信息";
    LCInsured2Grid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

function initLPGrpContGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          		  	//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="团体保单号";    	  //列名1
    iArray[1][1]="100px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="原保费";         		//列名2
    iArray[2][1]="80px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="原保额";         			//列名3
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="原份数";         		  //列名4
    iArray[4][1]="80px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="原投保人数";         	//列名5
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="变动后保费";         	//列名6
    iArray[6][1]="80px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="变动后保额";         	//列名7
    iArray[7][1]="80px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="变动后份数";         	//列名8
    iArray[8][1]="80px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="变动后投保人数";  //列名9
    iArray[9][1]="80px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
	iArray[10][0]="币种";          
	iArray[10][1]="50px";              
	iArray[10][2]=60;             
	iArray[10][3]=2;               
	iArray[10][4]="Currency";                 
	iArray[10][9]="币种|code:Currency";

    
 

    LPGrpContGrid = new MulLineEnter("fm" ,"LPGrpContGrid" ); 
    
    //这些属性必须在loadMulLine前
    LPGrpContGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    LPGrpContGrid.hiddenSubtraction = 1;
    LPGrpContGrid.hiddenPlus = 1; 
    LPGrpContGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "单击显示详细信息";
   	LPGrpContGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

function initLPGrpPolGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          		  	//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="团体险种号";    	  //列名1
    iArray[1][1]="100px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="险种编号";    	  //列名1
    iArray[2][1]="60px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="原保费";         		//列名2
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="原保额";         			//列名3
    iArray[4][1]="80px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="原份数";         		  //列名4
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[6]=new Array();
    iArray[6][0]="原参保人数";         	//列名5
    iArray[6][1]="80px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="变动后保费";         	//列名6
    iArray[7][1]="80px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="变动后保额";         	//列名7
    iArray[8][1]="80px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="变动后份数";         	//列名8
    iArray[9][1]="80px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="变动后参保人数";  //列名9
    iArray[10][1]="80px";            	//列宽
    iArray[10][2]=100;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
	iArray[11][0]="币种";          
	iArray[11][1]="50px";              
	iArray[11][2]=60;             
	iArray[11][3]=2;               
	iArray[11][4]="Currency";                 
	iArray[11][9]="币种|code:Currency";

    
 

    LPGrpPolGrid = new MulLineEnter("fm" ,"LPGrpPolGrid" ); 
    
    //这些属性必须在loadMulLine前
    LPGrpPolGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    LPGrpPolGrid.hiddenSubtraction = 1;
    LPGrpPolGrid.hiddenPlus = 1; 
    LPGrpPolGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "单击显示详细信息";
    LPGrpPolGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

function initLCPolGrid()
{
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          		  	//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保单险种号";    	  //列名1
      iArray[1][1]="80px";             //列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种编码";        	//列名2
      iArray[2][1]="50px";              //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种名称";         	//列名3
      iArray[3][1]="120px";            	//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="客户号";         		//列名4
      iArray[4][1]="60px";            	//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              	

      iArray[5]=new Array();
      iArray[5][0]="客户姓名";         	//列名5
      iArray[5][1]="60px";            	//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="生效日";        //列名6
      iArray[6][1]="60px";            	//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[7]=new Array();
      iArray[7][0]="保费/现价";        //列名6
      iArray[7][1]="60px";            	//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[8]=new Array();
      iArray[8][0]="保额";        //列名6
      iArray[8][1]="60px";            	//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[9]=new Array();
      iArray[9][0]="份数";        //列名6
      iArray[9][1]="60px";            	//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不?
      
      iArray[10]=new Array();
      iArray[10][0]="协议退费金额";        //列名6
      iArray[10][1]="70px";            	//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=2;              			//是否允许输入,1表示允许，0表示不?
      iArray[10][9]="协议退费|NOTNULL&NUM";  
      
      iArray[11]=new Array();
	  iArray[11][0]="币种";          
	  iArray[11][1]="50px";              
	  iArray[11][2]=60;             
	  iArray[11][3]=2;               
	  iArray[11][4]="Currency";                 
	  iArray[11][9]="币种|code:Currency";

      
      
                  
      
      LCPolGrid = new MulLineEnter( "fm" , "LCPolGrid" ); 
      //LCPolGrid.mulLineCount = 10;   
      LCPolGrid.displayTitle = 1;
      LCPolGrid.hiddenPlus = 1;
      LCPolGrid.hiddenSubtraction = 1;
      //LCPolGrid.canSel = 1;
      //LCPolGrid.selBoxEventFuncName = "reportDetailClick";
      //LCInsureAccClassGrid.chkBoxEventFuncName = "reportDetailClick1";
      LCPolGrid.loadMulLine(iArray);      
    }
    catch(ex)
    {
      alert(ex);
    }
}

</script>
