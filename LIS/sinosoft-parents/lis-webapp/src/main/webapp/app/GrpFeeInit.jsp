<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：GrpFeeInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
	//添加页面控件的初始化。
%>
<script language="JavaScript">

function initInpBox()
{ 
  try
  {                                   
    document.all('ProposalGrpContNo').value = ProposalGrpContNo;
    document.all('GrpContNo').value = ProposalGrpContNo;
    try { document.all('AppntNo').value = mSwitch.getVar( "GrpNo" ); if(document.all('AppntNo').value=="false"){document.all('AppntNo').value="";} } catch(ex) { };
   // alert(document.all('GrpContNo').value);
    
  }
  catch(ex)
  {
    alert("在GroupPolInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm(){
	try{
		//alert(scantype);
    if(scantype=="scan"){

	  	document.all('RiskCode').focus();

      setFocus();
		  
		}		
		
		initInpBox();
		//initGrpFeeGrid();
		initContPlanGrid();
		//easyQueryClick();
        if(this.LoadFlag=="4"||this.LoadFlag=="16")
        {
            document.getElementById("divRiskSave").style.display="none";
            document.getElementById("divRiskAdd").style.display="none";
            document.all("btnDefPubAcc").style.display="none";
            document.all("btnDefPubAmnt").style.display="none";
        }
		initContPlanDutyGrid();
		initPubAmntGrid();
		initPubAccGrid();
	}
	catch(re){
		alert("GrpFeeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

var ContPlanGrid; 

// 要约信息列表的初始化
function initContPlanGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="险种名称";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=150;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";    	        //列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     	
      
      iArray[3]=new Array();
      iArray[3][0]="责任编码";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 3;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10]="DutyCode";   
      
      
      iArray[4]=new Array();
      iArray[4][0]="险种责任";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[5]=new Array();
      iArray[5][0]="计算要素";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=3;  
      iArray[5][10]="FactorCode";            			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="要素名称";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="要素说明";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="要素值";         		//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=150;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="特别说明";         		//列名
      iArray[9][1]="200px";            		//列宽
      iArray[9][2]=150;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="险种版本";         		//列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=10;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="集体保单险种号码";         		//列名
      iArray[11][1]="0px";            		//列宽
      iArray[11][2]=10;            			//列最大值
      iArray[11][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[12]=new Array();
      iArray[12][0]="主险编码";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=10;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="类型";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=10;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" ); 
      //这些属性必须在loadMulLine前
      ContPlanGrid = new MulLineEnter( "fm" , "ContPlanGrid" ); 
      //这些属性必须在loadMulLine前
      ContPlanGrid.mulLineCount = 0;   
      ContPlanGrid.displayTitle = 1;
      ContPlanGrid.hiddenPlus = 1;
      //ContPlanGrid.hiddenSubtraction = 1;
      ContPlanGrid.hiddenSubtraction = 1;
      ContPlanGrid.canChk=0; 
      ContPlanGrid.loadMulLine(iArray); 
      
      
      //这些操作必须在loadMulLine后面
      //ContPlanGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// 要约信息列表的初始化
function initContPlanDutyGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	        //列名
      iArray[1][1]="155px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         		//列名
      iArray[2][1]="155px";            		//列宽
      iArray[2][2]=150;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][10]="RiskCode";

      iArray[3]=new Array();
      iArray[3][0]="责任类型";         		//列名
      iArray[3][1]="155px";            		//列宽
      iArray[3][2]= 60;            			//列最大值
      iArray[3][3]= 0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[4]=new Array();
      iArray[4][0]="类型名称";         		//列名
      iArray[4][1]="155px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ContPlanDutyGrid = new MulLineEnter( "fm" , "ContPlanDutyGrid" );
      //这些属性必须在loadMulLine前
      ContPlanDutyGrid.mulLineCount = 0;
      ContPlanDutyGrid.displayTitle = 1;
      ContPlanDutyGrid.hiddenPlus = 1;
      ContPlanDutyGrid.hiddenSubtraction = 1;
      ContPlanDutyGrid.canChk=1;
      ContPlanDutyGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ContPlanGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// 公共帐户列表的初始化
function initPubAccGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种名称";    	        //列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      
      iArray[2]=new Array();
      iArray[2][0]="险种责任";    	        //列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[3]=new Array();
      iArray[3][0]="公共帐户名称";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=150;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="缴费金额";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]= 60;            			//列最大值
      iArray[4][3]= 1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="险种编码";    	        //列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      
      iArray[6]=new Array();
      iArray[6][0]="责任编码";    	        //列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[7]=new Array();
      iArray[7][0]="自动应用公共帐户";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      iArray[7][4]="yesno"; 
      
      iArray[8]=new Array();
      iArray[8][0]="客户编码";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      
      iArray[9]=new Array();
      iArray[9][0]="合同号";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择

      iArray[10]=new Array();
      iArray[10][0]="集体合同号";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      
      iArray[11]=new Array();
      iArray[11][0]="帐户余额";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择

      iArray[12]=new Array();
      iArray[12][0]="个人保单号";         		//列名
      iArray[12][1]="100px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择




      PubAccGrid = new MulLineEnter( "fm" , "PubAccGrid" );
      //这些属性必须在loadMulLine前
      PubAccGrid.canSel=1;
      PubAccGrid.mulLineCount = 1;
      PubAccGrid.displayTitle = 1;
      PubAccGrid.addEventFuncName="addNewLine";
      //PubAccGrid.tableWidth   ="500px";
      PubAccGrid.hiddenPlus = 1;
      PubAccGrid.hiddenSubtraction = 1;
      PubAccGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      //PubAccGrid.setRowColData(1,1,"asdf");

    }
    catch(ex) {
      alert(ex);
    }
}

// 公共保额列表的初始化
function initPubAmntGrid() {
    var iArray = new Array();

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种名称";    	        //列名
      iArray[1][1]="155px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      
      iArray[2]=new Array();
      iArray[2][0]="险种责任";    	        //列名
      iArray[2][1]="140px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[3]=new Array();
      iArray[3][0]="公共保额名称";         		//列名
      iArray[3][1]="130px";            		//列宽
      iArray[3][2]=150;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         		//列名
      iArray[4][1]="130px";            		//列宽
      iArray[4][2]= 60;            			//列最大值
      iArray[4][3]= 1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="保费";         		//列名
      iArray[5][1]="130px";            		//列宽
      iArray[5][2]= 60;            			//列最大值
      iArray[5][3]= 1;              			//是否允许输入,1表示允许，0表示不允许
         
      iArray[6]=new Array();
      iArray[6][0]="险种编码";    	        //列名
      iArray[6][1]="140px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择

      iArray[7]=new Array();
      iArray[7][0]="责任编码";         		//列名
      iArray[7][1]="120px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择 
      
      iArray[8]=new Array();
      iArray[8][0]="客户编码";         		//列名
      iArray[8][1]="120px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      
      iArray[9]=new Array();
      iArray[9][0]="合同号";         		//列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择

      iArray[10]=new Array();
      iArray[10][0]="集体合同号";         		//列名
      iArray[10][1]="120px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许,2表示代码选择
      

      PubAmntGrid = new MulLineEnter( "fm" , "PubAmntGrid" );
      //这些属性必须在loadMulLine前
      PubAmntGrid.canSel=1;
      PubAmntGrid.mulLineCount = 1;
      PubAmntGrid.displayTitle = 1;
      PubAmntGrid.addEventFuncName="addNewLine";
      //PubAmntGrid.tableWidth   ="500px";
      PubAmntGrid.hiddenPlus = 1;
      PubAmntGrid.hiddenSubtraction = 1;
      PubAmntGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      //PubAmntGrid.setRowColData(1,1,"asdf");

    }
    catch(ex) {
      alert(ex);
    }
}

// 要约信息列表的初始化
//function initImpartGrid(tImpContPlanCode) {
function initImpartGrid() {
    var tImpContPlanCode="";
    var iArray = new Array();
    //alert("impartgrid");

    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[1]=new Array();
      iArray[1][0]="险种名称";    	        //列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
            
      iArray[2]=new Array();
      iArray[2][0]="计划险种";    	        //列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
            
      iArray[3]=new Array();
      iArray[3][0]="计划级别";    	        //列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
            
      iArray[4]=new Array();
      iArray[4][0]="要素类别";    	        //列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=3;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
            
      iArray[5]=new Array();
      iArray[5][0]="要素目标编码";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
            
      iArray[6]=new Array();
      iArray[6][0]="险种责任";    	        //列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
            
      iArray[7]=new Array();
      iArray[7][0]="要素计算编码";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
            
      iArray[8]=new Array();
      iArray[8][0]="要素内容";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
            
      iArray[9]=new Array();
      iArray[9][0]="要素名称";    	        //列名
      iArray[9][1]="120px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
      
      iArray[10]=new Array();
      iArray[10][0]="要素说明";    	        //列名
      iArray[10][1]="120px";            		//列宽
      iArray[10][2]=80;            			//列最大值
      iArray[10][3]=0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
            
      iArray[11]=new Array();
      iArray[11][0]="要素值";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=150;            			//列最大值
      iArray[11][3]=1;              			//是否允许输入,1表示允许，0表示不允许
            
      
      iArray[12]=new Array();
      iArray[12][0]="计划名称";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=150;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许
            
      iArray[13]=new Array();
      iArray[13][0]="险种版本";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=150;            			//列最大值
      iArray[13][3]=3;              			//是否允许输入,1表示允许，0表示不允许
            
      iArray[14]=new Array();
      iArray[14][0]="计算编码名称";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=150;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许
            
      iArray[15]=new Array();
      iArray[15][0]="主险编码";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=150;            			//列最大值
      iArray[15][3]=3;              			//是否允许输入,1表示允许，0表示不允许
           
      iArray[16]=new Array();
      iArray[16][0]="主险版本";         		//列名
      iArray[16][1]="0px";            		//列宽
      iArray[16][2]=150;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;
      ImpartGrid.displayTitle = 1;
      ImpartGrid.addEventFuncName="addNewLine";
      //ImpartGrid.tableWidth   ="500px";

      ImpartGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
      }
    catch(ex) {
      alert(ex);
    }
 }
</script>
