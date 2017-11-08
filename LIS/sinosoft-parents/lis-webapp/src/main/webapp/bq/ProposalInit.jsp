<%
//程序名称：ProposalInput.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() { 
  try {  
                                   
	  // 保单信息部分
    document.all('ProposalNo').value = '';
    document.all('PrtNo').value = '';
    document.all('ManageCom').value = '';
    document.all('SaleChnl').value = '';
    document.all('AgentCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
    document.all('Handler').value = '';
    document.all('AgentCode1').value = '';
 

  //  document.all('ContNo').value = '';
    document.all('GrpPolNo').value = '';
    document.all('MainPolNo').value = '';
    document.all('FirstPayDate').value = '';
    document.all('Lang').value = '';
    document.all('Currency').value = '';
    document.all('DisputedFlag').value = '';
    document.all('AgentPayFlag').value = '';
    document.all('AgentGetFlag').value = '';
    document.all('Remark').value = '';
      

  	// 个人投保人信息部分   
    document.all( 'AppntCustomerNo' ).value          = "";
  	document.all( 'AppntName' ).value                = "";
  	document.all( 'AppntSex' ).value                 = "";
  	document.all( 'AppntBirthday' ).value            = "";
  	document.all( 'AppntAge' ).value                 = "";
  	document.all( 'AppntIDType' ).value              = "";
  	document.all( 'AppntIDNo' ).value                = "";
  	document.all( 'AppntNativePlace' ).value         = "";
  	document.all( 'AppntRgtAddress' ).value          = "";
  	document.all( 'AppntMarriage' ).value            = "";
  	document.all( 'AppntNationality' ).value         = "";
  	document.all( 'AppntDegree' ).value              = "";
  	document.all( 'AppntRelationToInsured' ).value   = "";
  	document.all( 'AppntPostalAddress' ).value       = "";
  	document.all( 'AppntZipCode' ).value             = "";
  	document.all( 'AppntPhone' ).value               = "";
  	document.all( 'AppntMobile' ).value              = "";
  	document.all( 'AppntEMail' ).value               = "";
  	document.all( 'AppntGrpName' ).value             = "";
  	document.all( 'AppntGrpPhone' ).value            = "";
  	document.all( 'AppntGrpAddress' ).value          = "";
  	document.all( 'AppntGrpZipCode' ).value          = "";
  	document.all( 'AppntWorkType' ).value            = "";
  	document.all( 'AppntPluralityType' ).value       = "";
  	document.all( 'AppntOccupationType' ).value      = "";
  	document.all( 'AppntOccupationCode' ).value      = "";
  	document.all( 'SmokeFlag' ).value                = "";

  	// 集体投保人信息部分    
  	document.all('GrpNo').value = '';
    document.all('GrpName').value = '';
    document.all('LinkMan').value = '';
    document.all('GrpRelation').value = '';
    document.all('GrpPhone').value = '';
    document.all('GrpFax').value = '';
    document.all('GrpEMail').value = '';
    document.all('GrpZipCode').value = '';
    document.all('GrpAddress').value = '';

	  // 被保人信息部分  
  	document.all( 'CustomerNo' ).value          = "";
  	document.all( 'Name' ).value                = "";
  	document.all( 'Sex' ).value                 = "";
  	document.all( 'Birthday' ).value            = "";
  	document.all( 'Age' ).value                 = "";
  	document.all( 'IDType' ).value              = "";
  	document.all( 'IDNo' ).value                = "";
  	document.all( 'NativePlace' ).value         = "";
  	document.all( 'RgtAddress' ).value          = "";
  	document.all( 'Marriage' ).value            = "";
  	document.all( 'Nationality' ).value         = "";
  	document.all( 'Degree' ).value              = "";
  	document.all( 'SmokeFlag' ).value           = "";
  	document.all( 'PostalAddress' ).value       = "";
  	document.all( 'ZipCode' ).value             = "";
  	document.all( 'Phone' ).value               = "";
  	document.all( 'Mobile' ).value              = "";
  	document.all( 'EMail' ).value               = "";
  	document.all( 'GrpName' ).value             = "";
  	document.all( 'GrpPhone' ).value            = "";
  	document.all( 'GrpAddress' ).value          = "";
  	document.all( 'GrpZipCode' ).value          = "";	
  	document.all( 'WorkType' ).value            = "";
  	document.all( 'PluralityType' ).value       = "";
  	document.all( 'OccupationType' ).value      = "";
  	document.all( 'OccupationCode' ).value      = "";
  
    // 险种信息部分
    document.all('RiskCode').value              = '';
    document.all('RiskVersion').value           = '';
    document.all('CValiDate').value             = '';
    document.all('HealthCheckFlag').value       = '';
    document.all('Years').value                 = '';
    document.all('PayYears').value              = '';
    document.all('PayIntv').value               = '';
    document.all('OutPayFlag').value            = '';
    document.all('PayLocation').value           = '';
    document.all('BankCode').value              = '';
    document.all('countName').value             = '';
    document.all('BankAccNo').value             = '';
    document.all('LiveGetMode').value           = '';
    document.all('getTerm').value               = '';
    document.all('getIntv').value               = '';
    document.all('BonusGetMode').value          = '';
    document.all('Mult').value                  = '';
    document.all('Prem').value                  = '';
    document.all('Amnt').value                  = '';
   
    document.all('PayEndYear').value = '';
    document.all('PayEndYearFlag').value = '';
    document.all('GetYear').value = '';
    document.all('GetYearFlag').value = '';
    document.all('GetStartType').value = '';
    document.all('InsuYear').value = '';
    document.all('InsuYearFlag').value = '';
    document.all('AutoPayFlag').value = '';
    document.all('InterestDifFlag').value = '';
    document.all('SubFlag').value = '';
  } catch(ex) {
    //alert("在ProposalInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在ProposalInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

// 被保人信息列表的初始化
function initSubInsuredGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="连带被保人客户号";    	//列名
      iArray[1][1]="180px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="showSubInsured";
      iArray[1][8]="['SubInsured']";        //是否使用自己编写的函数 

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="性别";         			//列名
      iArray[3][1]="140px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出生日期";         		//列名
      iArray[4][1]="140px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="与被保人关系";         		//列名
      iArray[5][1]="160px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="Relation";

      SubInsuredGrid = new MulLineEnter( "fm" , "SubInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      SubInsuredGrid.mulLineCount = 1;   
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 受益人信息列表的初始化
function initBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许
  
    iArray[1]=new Array();
    iArray[1][0]="受益人类别"; 		//列名
    iArray[1][1]="60px";		//列宽
    iArray[1][2]=40;			//列最大值
    iArray[1][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="BnfType";
    iArray[1][9]="受益人类别|notnull&code:BnfType";
   
    iArray[2]=new Array();
    iArray[2][0]="姓名"; 	//列名
    iArray[2][1]="30px";		//列宽
    iArray[2][2]=30;			//列最大值
    iArray[2][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[2][9]="姓名|len<=20"; //校验
  
    iArray[3]=new Array();
    iArray[3][0]="证件类型"; 		//列名
    iArray[3][1]="40px";		//列宽
    iArray[3][2]=40;			//列最大值
    iArray[3][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="IDType";
    iArray[3][9]="证件类型|code:IDType";
  
    iArray[4]=new Array();
    iArray[4][0]="证件号码"; 		//列名
    iArray[4][1]="120px";		//列宽
    iArray[4][2]=80;			//列最大值
    iArray[4][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[4][9]="证件号码|len<=20";

    iArray[5]=new Array();
    iArray[5][0]="与被保人关系"; 	//列名
    iArray[5][1]="60px";		//列宽
    iArray[5][2]=60;			//列最大值
    iArray[5][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[5][4]="Relation";
    iArray[5][9]="与被保人关系|code:Relation";
  
    iArray[6]=new Array();
    iArray[6][0]="受益比例"; 		//列名
    iArray[6][1]="40px";		//列宽
    iArray[6][2]=40;			//列最大值
    iArray[6][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][9]="受益比例|num&len<=10";
  
    iArray[7]=new Array();
    iArray[7][0]="受益顺序"; 		//列名
    iArray[7][1]="40px";		//列宽
    iArray[7][2]=40;			//列最大值
    iArray[7][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[7][4]="OccupationType";
    iArray[7][9]="受益顺序|code:OccupationType";
  
    iArray[8]=new Array();
    iArray[8][0]="住址（填序号）"; 		//列名
    iArray[8][1]="160px";		//列宽
    iArray[8][2]=100;			//列最大值
    iArray[8][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[8][9]="住址|len<=80";
  
    iArray[9]=new Array();
    iArray[9][0]="速填"; 		//列名
    iArray[9][1]="30px";		//列宽
    iArray[9][2]=30;			//列最大值
    iArray[9][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[9][4]="customertype";
  
    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" ); 
    //这些属性必须在loadMulLine前
    BnfGrid.mulLineCount = 1; 
    BnfGrid.displayTitle = 1;
    BnfGrid.loadMulLine(iArray); 
  
    //这些操作必须在loadMulLine后面
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}

// 告知信息列表的初始化
function initImpartGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="ImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="告知客户类型";         		//列名
      iArray[5][1]="90px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="CustomerType";
      iArray[5][9]="告知客户类型|len<=18";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}

// 特别约定信息列表的初始化
function initSpecGrid() {                               
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="0px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="特约类型";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="特约编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="特约内容";         		//列名
      iArray[3][1]="540px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="特约内容|len<=255";
      
      SpecGrid = new MulLineEnter( "fm" , "SpecGrid" );
      //这些属性必须在loadMulLine前
      SpecGrid.mulLineCount = 1;   
      SpecGrid.displayTitle = 0;
      SpecGrid.hiddenPlus = 1;
      SpecGrid.hiddenSubtraction = 1;
      SpecGrid.loadMulLine(iArray);           
      //这些操作必须在loadMulLine后面
      //SpecGrid.setRowColData(1,1,"asdf");
      SpecGrid.setRowColData(0,1,"1");
      SpecGrid.setRowColData(0,2,"1");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//责任列表
function initDutyGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="50px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="责任名称";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保费";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保额";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费年期";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="年期单位";         			//列名
      iArray[6][1]="40px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="领取年期";         			//列名
      iArray[7][1]="40px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="年期单位";         			//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;             			//是否允许输入,1表示允许，0表示不允许


      iArray[9]=new Array();
      iArray[9][0]="保险年期";         			//列名
      iArray[9][1]="40px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="年期单位";         			//列名
      iArray[10][1]="40px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="交费方式";         			//列名
      iArray[11][1]="40px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=1;
      
      iArray[12]=new Array();
      iArray[12][0]="管理费比例";         			//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=1;              			//是否允许输入,1表示允许，0表示不允许
 
  
 
      DutyGrid = new MulLineEnter( "fm" , "DutyGrid" ); 
      //这些属性必须在loadMulLine前
      DutyGrid.mulLineCount = 0;   
      DutyGrid.displayTitle = 1;
      DutyGrid.canChk = 1;
      DutyGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DutyGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//保费项列表
function initPremGrid()
{
    var iArray = new Array();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="责任编码";    	//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="交费编码";         			//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费名称";         			//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="保费";         			//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="分配比例";         			//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="关联帐户号";         			//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[7]=new Array();
      iArray[7][0]="帐户分配比率";         			//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
	  
     
        if(typeof(window.spanPremGrid) == "undefined" )
        {
              //alert("out");
              return false;
        }
        else
        {
      			//alert("in");
      	    PremGrid = new MulLineEnter( "fm" , "PremGrid" );
	  	    //这些属性必须在loadMulLine前
	 	     PremGrid.mulLineCount = 0;   
	 	     PremGrid.displayTitle = 1;
	 	     PremGrid.canChk = 1;
	 	     PremGrid.loadMulLine(iArray);  
     	 }

      
     }
     catch(ex)
    {
		return false;
    }
    return true;
}

function emptyForm() {
	//emptyFormElements();     //清空页面所有输入框，在COMMON。JS中实现

	//initInpBox();
	//initSelBox();    
	initSubInsuredGrid();
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	initPremGrid();
}

function initForm() {
	try	{  
	
		if (loadFlag == "2") {	//集体下个人投保单录入
			var tRiskCode = parent.VD.gVSwitch.getVar( "RiskCode" );
			getRiskInput( tRiskCode, loadFlag );
		}
		
		if (loadFlag == "3") {	//个人投保单明细查询
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}

		if (loadFlag == "4") {	//集体下个人投保单明细查询
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}

		if (loadFlag == "5") {	//个人投保单复核查询
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
		
		if (loadFlag == "6") {	//个人投保单查询
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
		
		if (loadFlag == "10") {	//个人投保单浮动费率修改
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
		}
				
		
		//方便录入，MINIM增加
		document.all('RiskCode').focus();
		setFocus(prtNo);
		
	} catch(ex) {
	}			
}

</script>