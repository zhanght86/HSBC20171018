<%
//GEdorTypeXTInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>                       

<script language="JavaScript">  
                                    

function initForm()
{
    try
    {
       fm.GIManageCom.value= manageCom;
       fm.GLManageCom.value= manageCom;
       fm.GEManageCom.value= manageCom;
       fm.GEUManageCom.value= manageCom;
       showOneCodeName('station', 'GIManageCom', 'GIManageComName');
       showOneCodeName('station', 'GLManageCom', 'GLManageComName');
       showOneCodeName('station', 'GEManageCom', 'GEManageComName');
       showOneCodeName('station', 'GEUManageCom', 'GEUManageComName');
    }
    catch (ex)
    {
        alert("在 WFEdorNoscanAppInit.jsp --> initForm 函数中发生异常:初始化界面错误！ ");
    }
}

// 信息列表的初始化
function initGrpSinglePolGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          			  //列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="个单保单号";    	  //列名1
    iArray[1][1]="100px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="姓名";         		//列名2
    iArray[2][1]="80px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="性别";         			//列名3
    iArray[3][1]="40px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="生日";         		  //列名4
    iArray[4][1]="60px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="证件类型";         	//列名5
    iArray[5][1]="60px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="号码";         	//列名6
    iArray[6][1]="100px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="生效日期";         	//列名7
    iArray[7][1]="60px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="险种"; //列名8
    iArray[8][1]="100px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="保额"; //列名8
    iArray[9][1]="60px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="保费"; //列名8
    iArray[10][1]="100px";            	//列宽
    iArray[10][2]=100;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[11]=new Array();
    iArray[11][0]="个单状态"; //列名8
    iArray[11][1]="100px";            	//列宽
    iArray[11][2]=100;            			//列最大值
    iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    GrpSinglePolGrid = new MulLineEnter( "fm" , "GrpSinglePolGrid" ); 
    
    //这些属性必须在loadMulLine前
    GrpSinglePolGrid.mulLineCount = 0;    
    GrpSinglePolGrid.displayTitle = 1;
    //GrpSinglePolGrid.canChk = 0;
    //GrpSinglePolGrid.canSel = 1;
    GrpSinglePolGrid.hiddenPlus = 1; 
    GrpSinglePolGrid.hiddenSubtraction = 1;
    //LCInsuredGrid.selBoxEventFuncName = "queryPolGrid";
    GrpSinglePolGrid.detailInfo = "单击显示详细信息";
    GrpSinglePolGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

// 信息列表的初始化
function initGrpPolGrid() {
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          		  	//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="集体保险单号";    	  //列名1
    iArray[1][1]="100px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="印刷号";         		//列名2
    iArray[2][1]="80px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="险种";         			//列名3
    iArray[3][1]="100px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="机构";         		  //列名4
    iArray[4][1]="60px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="投保人";         	//列名5
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="人数";         	//列名6
    iArray[6][1]="50px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="保费";         	//列名7
    iArray[7][1]="60px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="保额";         	//列名8
    iArray[8][1]="60px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="生效日期";         		  //列名9
    iArray[9][1]="80px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="保险期间";        		//列名10
    iArray[10][1]="60px";            	//列宽
    iArray[10][2]=100;            		//列最大值
    iArray[10][3]=0;              		//是否允许输入,1表示允许，0表示不允许

    iArray[11]=new Array();
    iArray[11][0]="团单状态";        		//列名10
    iArray[11][1]="60px";            	//列宽
    iArray[11][2]=100;            		//列最大值
    iArray[11][3]=0;              		//是否允许输入,1表示允许，0表示不允许

    GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
    
    //这些属性必须在loadMulLine前
    GrpPolGrid.mulLineCount = 0;   
    GrpPolGrid.displayTitle = 1;
    //GrpPolGrid.canChk = 1; 
    GrpPolGrid.hiddenPlus = 1; 
    GrpPolGrid.hiddenSubtraction = 1;
    //GrpPolGrid.chkBoxEventFuncName = "reportDetail2Click"; 
    GrpPolGrid.detailInfo = "单击显示详细信息";
    GrpPolGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }
}

function initGrpSingleAccGrid()
{
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
    iArray[2][0]="姓名";         		//列名2
    iArray[2][1]="60px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="身份证号";         			//列名3
    iArray[3][1]="100px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="部门";         			//列名3
    iArray[4][1]="60px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="金额";         			//列名3
    iArray[5][1]="60px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="财务类型";         			//列名3
    iArray[6][1]="60px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="交费日期";         			//列名3
    iArray[7][1]="80px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="账户类型";         			//列名3
    iArray[8][1]="150px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许


    iArray[9]=new Array();
    iArray[9][0]="操作员";         			//列名3
    iArray[9][1]="60px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 

    GrpSingleAccGrid = new MulLineEnter("fm" ,"GrpSingleAccGrid" ); 
    
    //这些属性必须在loadMulLine前
    GrpSingleAccGrid.mulLineCount = 0; 
    GrpSingleAccGrid.displayTitle = 1;
    GrpSingleAccGrid.hiddenSubtraction = 1;
    GrpSingleAccGrid.hiddenPlus = 1; 
    GrpSingleAccGrid.hiddenSubtraction = 1;
   	GrpSingleAccGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

function initGrpEdorGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          		  	//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="保全项目";    	  //列名1
    iArray[1][1]="50px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="集体保险单号";    	  //列名1
    iArray[2][1]="100px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="批单号";         		//列名2
    iArray[3][1]="100px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    
    iArray[4]=new Array();
    iArray[4][0]="险种";         		//列名2
    iArray[4][1]="50px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="机构";         			//列名3
    iArray[5][1]="60px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="投保人";         		  //列名4
    iArray[6][1]="80px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[7]=new Array();
    iArray[7][0]="合计金额";         		  //列名4
    iArray[7][1]="80px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[8]=new Array();
    iArray[8][0]="涉及人数";         		  //列名4
    iArray[8][1]="80px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[9]=new Array();
    iArray[9][0]="操作日期";         		  //列名4
    iArray[9][1]="80px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[10]=new Array();
    iArray[10][0]="操作员";         		  //列名4
    iArray[10][1]="80px";            	//列宽
    iArray[10][2]=100;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[11]=new Array();
    iArray[11][0]="确认日期";         		  //列名4
    iArray[11][1]="80px";            	//列宽
    iArray[11][2]=100;            			//列最大值
    iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[12]=new Array();
    iArray[12][0]="保全时效";         		  //列名4
    iArray[12][1]="80px";            	//列宽
    iArray[12][2]=100;            			//列最大值
    iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用


    iArray[13]=new Array();
    iArray[13][0]="备注";         		  //列名4
    iArray[13][1]="80px";            	//列宽
    iArray[13][2]=100;            			//列最大值
    iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    GrpEdorGrid = new MulLineEnter("fm" ,"GrpEdorGrid" ); 
    
    //这些属性必须在loadMulLine前
    GrpEdorGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    GrpEdorGrid.hiddenSubtraction = 1;
    GrpEdorGrid.hiddenPlus = 1; 
    GrpEdorGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "单击显示详细信息";
    GrpEdorGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

//=================================
function initGrpLoanGrid()
{
    var iArray = new Array();      
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          				//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="团单号";    	    //列名1
      iArray[1][1]="140px";              //列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="机构";    	//列名2
      iArray[2][1]="120px";             //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保单位";         		//列名3
      iArray[3][1]="160px";            	//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="账户余额";        //列名4
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              	

      iArray[5]=new Array();
      iArray[5][0]="借款金额";      //列名5
      iArray[5][1]="60px";            	//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="印花税";         	//列名6
      iArray[6][1]="40px";            	//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

      iArray[7]=new Array();
      iArray[7][0]="借款日期";         	//列名6
      iArray[7][1]="80px";            	//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

      iArray[8]=new Array();
      iArray[8][0]="还款金额";         	//列名6
      iArray[8][1]="80px";            	//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

      iArray[9]=new Array();
      iArray[9][0]="还款利息";         	//列名6
      iArray[9][1]="60px";            	//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

      iArray[10]=new Array();
      iArray[10][0]="还款日期";         	//列名6
      iArray[10][1]="80px";            	//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

      iArray[11]=new Array();
      iArray[11][0]="备注";         	//列名6
      iArray[11][1]="60px";            	//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许   

      
                  
      GrpLoanGrid = new MulLineEnter( "fm" , "GrpLoanGrid" ); 
      GrpLoanGrid.displayTitle = 1;
      GrpLoanGrid.hiddenPlus = 1;
      //GrpLoanGrid.canSel = 1;
      //GrpLoanGrid.selBoxEventFuncName = "queryAccInfo";
      GrpLoanGrid.hiddenSubtraction = 1;
      GrpLoanGrid.loadMulLine(iArray);      
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initGrpEdorUserGrid()
{
  var iArray = new Array();
      
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";         			//列宽
    iArray[0][2]=10;          		  	//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="用户代码";    	  //列名1
    iArray[1][1]="60px";            	//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="姓名";    	  //列名1
    iArray[2][1]="60px";            	//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[3]=new Array();
    iArray[3][0]="机构";         		//列名2
    iArray[3][1]="80px";            	//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    
    iArray[4]=new Array();
    iArray[4][0]="权限级别";         		//列名2
    iArray[4][1]="50px";            	//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="人员状态";         			//列名3
    iArray[5][1]="60px";            	//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="是否通过考核";         		  //列名4
    iArray[6][1]="80px";            	//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[7]=new Array();
    iArray[7][0]="注册时间";         		  //列名4
    iArray[7][1]="80px";            	//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[8]=new Array();
    iArray[8][0]="注销时间";         		  //列名4
    iArray[8][1]="80px";            	//列宽
    iArray[8][2]=100;            			//列最大值
    iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[9]=new Array();
    iArray[9][0]="备注";         		  //列名4
    iArray[9][1]="80px";            	//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用


    GrpEdorUserGrid = new MulLineEnter("fm" ,"GrpEdorUserGrid" ); 
    
    //这些属性必须在loadMulLine前
    GrpEdorUserGrid.displayTitle = 1;
    //LPGrpContGrid.canChk = 1; 
    GrpEdorUserGrid.hiddenSubtraction = 1;
    GrpEdorUserGrid.hiddenPlus = 1; 
    GrpEdorUserGrid.hiddenSubtraction = 1;
    //LCInsured2Grid.chkBoxEventFuncName = "reportDetail2Click"; 
    //LCInsured2Grid.detailInfo = "单击显示详细信息";
    GrpEdorUserGrid.loadMulLine(iArray);  
  }
  catch(ex) {
    alert(ex);
  }	
}

</script>
