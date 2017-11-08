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
	try{
	var i = 0;
	var j = 0;
	var m = 0;
	var n = 0;
	var strsql = "";
	var tCodeData = "0|"; //yaory
	strsql = "select insuredno,name from lcinsured where grpcontno='"+tt+"'" ;
	//alert("strsql :" + strsql);
	turnPage.strQueryResult  = easyQueryVer3(strsql, 1, 0, 1);
	if (turnPage.strQueryResult != "")
	{
		turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		m = turnPage.arrDataCacheSet.length;
		for (i = 0; i < m; i++)
		{
			j = i + 1;
//			tCodeData = tCodeData + "^" + j + "|" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
			tCodeData = tCodeData + "^" + turnPage.arrDataCacheSet[i][0] + "|" + turnPage.arrDataCacheSet[i][1];
		}
		}}catch(ex)
		{}

// 输入框的初始化（单记录部分）
function initInpBox() {
  try {

	  // 保单信息部分
    fm.all('ProposalNo').value = '';
    fm.all('PrtNo').value = '';
    fm.all('ManageCom').value = '';
    fm.all('SaleChnl').value = '';
    fm.all('AgentCom').value = '';
    fm.all('AgentCode').value = '';
    fm.all('AgentGroup').value = '';
    fm.all('Handler').value = '';
    fm.all('AgentCode1').value = '';


  //  fm.all('ContNo').value = '';
    fm.all('GrpPolNo').value = '';
    //fm.all('MainPolNo').value = '';
    fm.all('FirstPayDate').value = '';
    fm.all('Lang').value = '';
    fm.all('Currency').value = '';
    fm.all('DisputedFlag').value = '';
    fm.all('AgentPayFlag').value = '';
    fm.all('AgentGetFlag').value = '';
    fm.all('Remark').value = '';


  	// 个人投保人信息部分
    fm.all( 'AppntCustomerNo' ).value          = "";
  	fm.all( 'AppntName' ).value                = "";
  	fm.all( 'AppntSex' ).value                 = "";
  	fm.all( 'AppntBirthday' ).value            = "";
  	fm.all( 'AppntAge' ).value                 = "";
  	fm.all( 'AppntIDType' ).value              = "";
  	fm.all( 'AppntIDNo' ).value                = "";
  	fm.all( 'AppntNativePlace' ).value         = "";
  	fm.all( 'AppntRgtAddress' ).value          = "";
  	fm.all( 'AppntMarriage' ).value            = "";
  	fm.all( 'AppntNationality' ).value         = "";
  	fm.all( 'AppntDegree' ).value              = "";
  	fm.all( 'AppntRelationToInsured' ).value   = "";
  	fm.all( 'AppntPostalAddress' ).value       = "";
  	fm.all( 'AppntZipCode' ).value             = "";
  	fm.all( 'AppntPhone' ).value               = "";
  	fm.all( 'AppntMobile' ).value              = "";
  	fm.all( 'AppntEMail' ).value               = "";
  	fm.all( 'AppntGrpName' ).value             = "";
  	fm.all( 'AppntGrpPhone' ).value            = "";
  	fm.all( 'AppntGrpAddress' ).value          = "";
  	fm.all( 'AppntGrpZipCode' ).value          = "";
  	fm.all( 'AppntWorkType' ).value            = "";
  	fm.all( 'AppntPluralityType' ).value       = "";
  	fm.all( 'AppntOccupationType' ).value      = "";
  	fm.all( 'AppntOccupationCode' ).value      = "";
  	fm.all( 'SmokeFlag' ).value                = "";

  	// 集体投保人信息部分
  	fm.all('GrpNo').value = '';
    fm.all('GrpName').value = '';
    fm.all('LinkMan').value = '';
    fm.all('GrpRelation').value = '';
    fm.all('GrpPhone').value = '';
    fm.all('GrpFax').value = '';
    fm.all('GrpEMail').value = '';
    fm.all('GrpZipCode').value = '';
    fm.all('GrpAddress').value = '';

	  // 被保人信息部分
  	fm.all( 'CustomerNo' ).value          = "";
  	fm.all( 'Name' ).value                = "";
  	fm.all( 'Sex' ).value                 = "";
  	fm.all( 'Birthday' ).value            = "";
  	fm.all( 'Age' ).value                 = "";
  	fm.all( 'IDType' ).value              = "";
  	fm.all( 'IDNo' ).value                = "";
  	fm.all( 'NativePlace' ).value         = "";
  	fm.all( 'RgtAddress' ).value          = "";
  	fm.all( 'Marriage' ).value            = "";
  	fm.all( 'Nationality' ).value         = "";
  	fm.all( 'Degree' ).value              = "";
  	fm.all( 'SmokeFlag' ).value           = "";
  	fm.all( 'PostalAddress' ).value       = "";
  	fm.all( 'ZipCode' ).value             = "";
  	fm.all( 'Phone' ).value               = "";
  	fm.all( 'Mobile' ).value              = "";
  	fm.all( 'EMail' ).value               = "";
  	fm.all( 'GrpName' ).value             = "";
  	fm.all( 'GrpPhone' ).value            = "";
  	fm.all( 'GrpAddress' ).value          = "";
  	fm.all( 'GrpZipCode' ).value          = "";
  	fm.all( 'WorkType' ).value            = "";
  	fm.all( 'PluralityType' ).value       = "";
  	fm.all( 'OccupationType' ).value      = "";
  	fm.all( 'OccupationCode' ).value      = "";

    // 险种信息部分
    fm.all('RiskCode').value              = '';
    fm.all('RiskVersion').value           = '';
    fm.all('CValiDate').value             = '';
    fm.all('PolApplyDate').value          = '';
    fm.all('HealthCheckFlag').value       = '';
    fm.all('Years').value                 = '';
    fm.all('PayYears').value              = '';
    fm.all('PayIntv').value               = '';
    fm.all('OutPayFlag').value            = '';
    fm.all('PayLocation').value           = '';
    fm.all('BankCode').value              = '';
    fm.all('countName').value             = '';
    fm.all('BankAccNo').value             = '';
    fm.all('LiveGetMode').value           = '';
    fm.all('getTerm').value               = '';
    fm.all('getIntv').value               = '';
    fm.all('BonusGetMode').value          = '';
    fm.all('Mult').value                  = '';
    fm.all('Prem').value                  = '';
    fm.all('Amnt').value                  = '';

    fm.all('PayEndYear').value = '';
    fm.all('PayEndYearFlag').value = '';
    fm.all('GetYear').value = '';
    fm.all('GetYearFlag').value = '';
    fm.all('GetStartType').value = '';
    fm.all('InsuYear').value = '';
    fm.all('InsuYearFlag').value = '';
    fm.all('AutoPayFlag').value = '';
    fm.all('InterestDifFlag').value = '';
    fm.all('SubFlag').value = '';


  } catch(ex) {
    //alert("在ProposalInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }

  //附加字段
  try{
  fm.all('StandbyFlag1').value = '';
  fm.all('StandbyFlag2').value = '';
  fm.all('StandbyFlag3').value = '';

  }
  catch(ex){}
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
   if(checktype=="1")
   {
     param="13";
     fm.pagename.value="13";
   }
   if(checktype=="2")
   {
     param="23";
     fm.pagename.value="23";
   }
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
      SubInsuredGrid.mulLineCount = 0;
      SubInsuredGrid.displayTitle = 1;
      //SubInsuredGrid.tableWidth = 200;
      SubInsuredGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert("在ProposalInit.jsp-->initSubInsuredGrid函数中发生异常:初始化界面错误!");
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
    iArray[1][1]="50px";		//列宽
    iArray[1][2]=40;			//列最大值
    iArray[1][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="BnfType";
    iArray[1][9]="受益人类别|notnull&code:BnfType";

    iArray[2]=new Array();
    iArray[2][0]="姓名"; 	//列名
    iArray[2][1]="50px";		//列宽
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
    iArray[6][0]="受益顺序"; 		//列名
    iArray[6][1]="40px";		//列宽
    iArray[6][2]=40;			//列最大值
    iArray[6][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][4]="bnforder";
    iArray[6][9]="受益顺序|code:bnforder";

    iArray[7]=new Array();
    iArray[7][0]="受益份额"; 		//列名
    iArray[7][1]="40px";		//列宽
    iArray[7][2]=40;			//列最大值
    iArray[7][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[7][9]="受益比例|num&len<=10";

    iArray[8]=new Array();
    iArray[8][0]="住址（填序号）"; 		//列名
    iArray[8][1]="0px";		//列宽
    iArray[8][2]=100;			//列最大值
    iArray[8][3]=3;			//是否允许输入,1表示允许，0表示不允许
    iArray[8][9]="住址|len<=80";

    iArray[9]=new Array();
    iArray[9][0]="速填"; 		//列名
    iArray[9][1]="30px";		//列宽
    iArray[9][2]=30;			//列最大值
    iArray[9][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[9][4]="customertype";

    iArray[10]=new Array();
    iArray[10][0]="所属被保人"; 		//列名
    iArray[10][1]="80px";		//列宽
    iArray[10][2]=30;			//列最大值
    iArray[10][3]=2;			//是否允许输入,1表示允许，0表示不允许
    //iArray[10][7]=1;
    //iArray[10][4]="insuredpeople";
    
    iArray[10][10]="insured1";
    iArray[10][11]=tCodeData;
    iArray[10][19]=1;
    //iArray[10][5]="1|2|3";
    //iArray[10][5]="10|11|12";
    //iArray[10][6]="10|11|12";
    //iArray[10][15]="ContNo";
    //iArray[10][16]="#"+tt+"#";

    iArray[11]=new Array();
    iArray[11][0]="所属被保人内部客户号"; 		//列名
    iArray[11][1]="0px";		//列宽
    iArray[11][2]=30;			//列最大值
    iArray[11][3]=3;			//是否允许输入,1表示允许，0表示不允许

    iArray[12]=new Array();
    iArray[12][0]="所属被保人客户号"; 		//列名
    iArray[12][1]="0px";		//列宽
    iArray[12][2]=30;			//列最大值
    iArray[12][3]=3;			//是否允许输入,1表示允许，0表示不允许



    BnfGrid = new MulLineEnter( "fm" , "BnfGrid" );
    //这些属性必须在loadMulLine前
    BnfGrid.mulLineCount = 1;
    BnfGrid.displayTitle = 1;
    BnfGrid.loadMulLine(iArray);

    //这些操作必须在loadMulLine后面
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert("在ProposalInit.jsp-->initBnfGrid函数中发生异常:初始化界面错误!");
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

      iArray[6]=new Array();
      iArray[6][0]="告知客户号码";         		//列名
      iArray[6][1]="90px";            		//列宽
      iArray[6][2]=90;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[6][4]="CustomerNo";
      iArray[6][9]="告知客户号码";

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
      alert("在ProposalInit.jsp-->initImpartGrid函数中发生异常:初始化界面错误!");
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
      iArray[0][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="特约类型";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="特约编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许

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
        alert("在ProposalInit.jsp-->initSpecGrid函数中发生异常:初始化界面错误!");
      }
}


// 被保人信息列表的初始化
function initInsuredGrid()
  {
   var iArray = new Array();
   var turnPage = new turnPageClass();
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号码";          		//列名
      iArray[1][1]="80px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="性别";      	   		//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=2;              	//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="Sex";


      iArray[4]=new Array();
      iArray[4][0]="出生日期";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="与第一被保险人关系";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=2;
      iArray[5][4]="Relation";              	        //是否引用代码:null||""为不引用
      iArray[5][9]="与主被保险人关系|code:Relation&NOTNULL";
      //iArray[5][18]=300;

      iArray[6]=new Array();
      iArray[6][0]="客户内部号";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=2;
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人";

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" );
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 0;
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;

      //InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuredGrid.hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);
      }

      catch(ex)
      {
        alert("在ProposalInit.jsp-->initInsuredGrid函数中发生异常:初始化界面错误!");
      }
      //alert(parent.VD.gVSwitch.getVar( "ContNo" ));
      //alert(parent.VD.gVSwitch.getVar("InsuredNo"));
      turnPage.queryModal("select InsuredNo,Name,Sex,Birthday,RelationToMainInsured,SequenceNo from LCInsured where ContNo='"+parent.VD.gVSwitch.getVar( "ContNo" )+"' and insuredno!='"+parent.VD.gVSwitch.getVar("InsuredNo")+"'", InsuredGrid)
      
}


function emptyForm() {
	//emptyFormElements();     //清空页面所有输入框，在COMMON。JS中实现
	//initInpBox();
	initSelBox();
	initSubInsuredGrid();

	//alert(getRisk()); //这是所有的险种编码及汉字的拼串

	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	initPremGrid();
	//add by yaory for relatedinsured

  //initInsuredGrid();


}

function initForm() {
	try	{
	  //fm.all("RiskCode").CodeData=getRisk();	//delete by yaory
	  //alert(PolTypeFlag);
		try
		{
		  fm.all('SelPolNo').value=parent.VD.gVSwitch.getVar("PolNo");
			if (fm.all('SelPolNo').value=='false')
			{
			  fm.all('SelPolNo').value='';
			}
		}
		catch(ex){

		}
		//alert("LoadFlag="+LoadFlag);
	  //alert("ok:"+fm.all('SelPolNo').value);
	  //alert(scantype);
	  //alert(1);
    if(scantype=="scan"){
    	fm.all('pagename').value='11';	  
	  	//方便录入，MINIM增加
	  	//alert("aa");
	  	fm.all('RiskCode').focus();
		  //alert("ccc");
      setFocus();
		  //alert("ddd");
		  
		}
	//	alert(1);
	
			


    //按照不同的LoadFlag进行不同的处理
    //得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=18-- 集体下无名单补单
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/
		if (LoadFlag == "1"&& fm.all('SelPolNo').value!="") {
		  //alert(fm.all('SelPolNo').value);
			var tPolNo = fm.all('SelPolNo').value;
			queryPolDetail( tPolNo );
			divButton.style.display="none";
			divInputContButton.style.display = "none";
			divApproveContButton.style.display = "none";
			divApproveModifyContButton.style.display = "none";
		//alert("bbb");
			//工作流相关控件赋值
			fm.all('MissionID').value=tMissionID;
			fm.all('SubMissionID').value=tSubMissionID;
			if(ScanFlag=="1"){
			  fm.all('WorkFlowFlag').value="0000001099";
			  
		  }
		  else{
			  fm.all('WorkFlowFlag').value="0000001098";
		  }




	  	//方便录入，MINIM增加
	  	fm.all('RiskCode').focus();
		  //alert("ccc");
      setFocus();
		  //alert("ddd");

		  return;

		}
		


		if (LoadFlag == "2"||LoadFlag == "18") {	//集体下个人投保单录入
		   //alert(LoadFlag);
		    var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		    var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
		    //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
		    if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		    {

		    	fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    	}
		    else
		    {
					fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
				}
		    if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //如果前一个界面选中险种
		    {
		    	var tPolNo = fm.all('SelPolNo').value;
					queryPolDetail( tPolNo );
					inputButton.style.display = "none";
					modifyButton.style.display = "";
		    }
		    else
		    {
		    //alert("1");
					getRiskInput( tRiskCode, LoadFlag );
				}
    		//方便录入，MINIM增加
	    	fm.all('RiskCode').focus();
  	  	setFocus();
  	  	return;

		}

		if (LoadFlag == "3") {	//个人投保单明细查询
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			//alert("PolNo="+tPolNo);
			if(tPolNo!=false){
        queryPolDetail( tPolNo );
			}
			//alert("abc");
			divButton.style.display="none";
			divInputContButton.style.display = "none";
			divApproveContButton.style.display = "none";
			divApproveModifyContButton.style.display = "none";
  		//方便录入，MINIM增加
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;

		}

		if (LoadFlag == "4") {	//集体下个人投保单明细审核
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
  		//方便录入，MINIM增加
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;
		}

		if (LoadFlag == "5") {	//个人投保单复核查询
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
    	divButton.style.display="none";
			divInputContButton.style.display = "none";
			divApproveModifyContButton.style.display = "none";
      divApproveContButton.style.display = "";
    	inputButton.style.display = "";
  		//方便录入，MINIM增加
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;
		}

		if (LoadFlag == "6") {	//个人投保单查询
			//<!--fm.all("divHideButton").style.display="none";-->
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
			return;
		}

		if (LoadFlag == "7") {	//保全新保增人
		    var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		    var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
		    //alert(tContPlanCode);
		    if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		    {
		    	fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    }
		    else
		    {
				fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		    if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //如果前一个界面选中险种
		    {
		    	var tPolNo = fm.all('SelPolNo').value;
				queryPolDetail( tPolNo );
				inputButton.style.display = "none";
				modifyButton.style.display = "";
		    }
		    else
		    {
				getRiskInput( tRiskCode, LoadFlag );
			}

		  //方便录入，MINIM增加
		  fm.all('RiskCode').focus();
		  setFocus();
		  return;
		}
		
		if(LoadFlag == "8"){			 
			 	  //fm.all('BQContNo').value = top.opener.fm.all('ContNo').value;
			 	  //var aContNo = fm.all('BQContNo').value;
			 	  //fm.all("RiskCode").CodeData = getRiskCodeNS(aContNo);
			 	var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		    var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
		    //alert(tContPlanCode);
		    if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		    {
		    	fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    }
		    else
		    {
				fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		    if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //如果前一个界面选中险种
		    {
		    	var tPolNo = fm.all('SelPolNo').value;
				queryPolDetail( tPolNo );
				inputButton.style.display = "none";
				modifyButton.style.display = "";
		    }
		    else
		    {
				getRiskInput( tRiskCode, LoadFlag );
			}

		  //方便录入，MINIM增加
		  fm.all('RiskCode').focus();
		  setFocus();
		  return;
		}

		if (LoadFlag == "10") {	//个人投保单浮动费率修改
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
  		//方便录入，MINIM增加
	  	fm.all('RiskCode').focus();
		  setFocus();
		  return;
		}

	  if (LoadFlag == "13"||LoadFlag == "14"||LoadFlag == "23")
	  {	//个人投保单浮动费率修改
      var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
		  var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		  var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );

		  //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
		  if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		  {
		    fm.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		  }
		  else
		  {
			  fm.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		  if (fm.all('SelPolNo').value!=""&&fm.all('SelPolNo').value!='false') //如果前一个界面选中险种
		  {
		    //alert(tPolNo);
		    var tPolNo = fm.all('SelPolNo').value;
			  queryPolDetail( tPolNo );
			  inputButton.style.display = "none";
			  modifyButton.style.display = "";
		  }
		  else
		  {
			  getRiskInput( tRiskCode, LoadFlag );
			}
			//方便录入，MINIM增加
	  	fm.all('RiskCode').focus();
  		setFocus();
      return;
		}

	  if (LoadFlag == "16") {	//集体下个人投保单明细查询
		  var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
			//方便录入，MINIM增加
  		fm.all('RiskCode').focus();
	  	setFocus();
	  	return;

		}

	  //人工核保修改投保单,并且是查看已有险种
		if (LoadFlag =="25"&&fm.all('SelPolNo').value!="") {
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );

			queryPolDetail( tPolNo );

//    	inputButton.style.display = "";

  		//getRiskInput( tRiskCode, LoadFlag );

  		//方便录入，MINIM增加
	  	fm.all('RiskCode').focus();
		  setFocus();
      return;
		}

	  if (LoadFlag == "99") {	//集体随动定制
      fm.all("RiskCode").CodeData=getRiskByGrpAll();
      inputButton.style.display = "none";
			//方便录入，MINIM增加
  		fm.all('RiskCode').focus();
	  	setFocus();
	  	autoMoveButton.style.display="";
	  	return;
		}

	}
	catch(ex){
	}
}

//取得集体投保单的信息
function getGrpPolInfo()
{
    //alert("getGrpPolInfo"+parent.VD.gVSwitch.getVar("GrpContNo")+"|"+fm.all('GrpContNo').value);
   try{fm.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    //alert("getGrpPolInfo"+fm.all('GrpContNo').value);
    try{fm.all('ProposalGrpContNo').value=parent.VD.gVSwitch.getVar("ProposalGrpContNo");}catch(ex){};
    try{fm.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{fm.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    try{fm.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{fm.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{fm.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{fm.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{fm.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{fm.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{fm.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{fm.all('Password2').value=parent.VD.gVSwitch.getVar("Password2");}catch(ex){};
    try{fm.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{fm.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{fm.all('Peoples2').value=parent.VD.gVSwitch.getVar("Peoples2");}catch(ex){};
    try{fm.all('GrpName').value=parent.VD.gVSwitch.getVar("GrpName");}catch(ex){};
    try{fm.all('BusinessType').value=parent.VD.gVSwitch.getVar("BusinessType");}catch(ex){};
    try{fm.all('GrpNature').value=parent.VD.gVSwitch.getVar("GrpNature");}catch(ex){};
    try{fm.all('RgtMoney').value=parent.VD.gVSwitch.getVar("RgtMoney");}catch(ex){};
    try{fm.all('Asset').value=parent.VD.gVSwitch.getVar("Asset");}catch(ex){};
    try{fm.all('NetProfitRate').value=parent.VD.gVSwitch.getVar("NetProfitRate");}catch(ex){};
    try{fm.all('MainBussiness').value=parent.VD.gVSwitch.getVar("MainBussiness");}catch(ex){};
    try{fm.all('Corporation').value=parent.VD.gVSwitch.getVar("Corporation");}catch(ex){};
    try{fm.all('ComAera').value=parent.VD.gVSwitch.getVar("ComAera");}catch(ex){};
    try{fm.all('Fax').value=parent.VD.gVSwitch.getVar("Fax");}catch(ex){};
    try{fm.all('Phone').value=parent.VD.gVSwitch.getVar("Phone");}catch(ex){};
    try{fm.all('GetFlag').value=parent.VD.gVSwitch.getVar("GetFlag");}catch(ex){};
    try{fm.all('Satrap').value=parent.VD.gVSwitch.getVar("Satrap");}catch(ex){};
    try{fm.all('EMail').value=parent.VD.gVSwitch.getVar("EMail");}catch(ex){};
    try{fm.all('FoundDate').value=parent.VD.gVSwitch.getVar("FoundDate");}catch(ex){};
    try{fm.all('GrpGroupNo').value=parent.VD.gVSwitch.getVar("GrpGroupNo");}catch(ex){};
    try { fm.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };

//    try { fm.all( 'DisputedFlag' ).value = parent.VD.gVSwitch.getVar( "DisputedFlag"); } catch(ex) { };
//    try { fm.all( 'OutPayFlag' ).value = parent.VD.gVSwitch.getVar( "OutPayFlag"); } catch(ex) { };
//    try { fm.all( 'GetPolMode' ).value = parent.VD.gVSwitch.getVar( "GetPolMode"); } catch(ex) { };
//    try { fm.all( 'Lang' ).value = parent.VD.gVSwitch.getVar( "Lang"); } catch(ex) { };
//    try { fm.all( 'Currency' ).value = parent.VD.gVSwitch.getVar( "Currency"); } catch(ex) { };
//    try { fm.all( 'LostTimes' ).value = parent.VD.gVSwitch.getVar( "LostTimes"); } catch(ex) { };
//    try { fm.all( 'PrintCount' ).value = parent.VD.gVSwitch.getVar( "PrintCount"); } catch(ex) { };
//    try { fm.all( 'RegetDate' ).value = parent.VD.gVSwitch.getVar( "RegetDate"); } catch(ex) { };
//    try { fm.all( 'LastEdorDate' ).value = parent.VD.gVSwitch.getVar( "LastEdorDate"); } catch(ex) { };
//    try { fm.all( 'LastGetDate' ).value = parent.VD.gVSwitch.getVar( "LastGetDate"); } catch(ex) { };
//    try { fm.all( 'LastLoanDate' ).value = parent.VD.gVSwitch.getVar( "LastLoanDate"); } catch(ex) { };
//    try { fm.all( 'SpecFlag' ).value = parent.VD.gVSwitch.getVar( "SpecFlag"); } catch(ex) { };
//    try { fm.all( 'GrpSpec' ).value = parent.VD.gVSwitch.getVar( "GrpSpec"); } catch(ex) { };
//    try { fm.all( 'PayMode' ).value = parent.VD.gVSwitch.getVar( "PayMode"); } catch(ex) { };
//    try { fm.all( 'SignCom' ).value = parent.VD.gVSwitch.getVar( "SignCom"); } catch(ex) { };
//    try { fm.all( 'SignDate' ).value = parent.VD.gVSwitch.getVar( "SignDate"); } catch(ex) { };
//    try { fm.all( 'SignTime' ).value = parent.VD.gVSwitch.getVar( "SignTime"); } catch(ex) { };
//    try { fm.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };
//    try { fm.all( 'PayIntv' ).value = parent.VD.gVSwitch.getVar( "PayIntv"); } catch(ex) { };
//    try { fm.all( 'ManageFeeRate' ).value = parent.VD.gVSwitch.getVar( "ManageFeeRate"); } catch(ex) { };
//    try { fm.all( 'ExpPeoples' ).value = parent.VD.gVSwitch.getVar( "ExpPeoples"); } catch(ex) { };
//    try { fm.all( 'ExpPremium' ).value = parent.VD.gVSwitch.getVar( "ExpPremium"); } catch(ex) { };
//    try { fm.all( 'ExpAmnt' ).value = parent.VD.gVSwitch.getVar( "ExpAmnt"); } catch(ex) { };
//    try { fm.all( 'Peoples' ).value = parent.VD.gVSwitch.getVar( "Peoples"); } catch(ex) { };
//    try { fm.all( 'Mult' ).value = parent.VD.gVSwitch.getVar( "Mult"); } catch(ex) { };
//    try { fm.all( 'Prem' ).value = parent.VD.gVSwitch.getVar( "Prem"); } catch(ex) { };
//    try { fm.all( 'Amnt' ).value = parent.VD.gVSwitch.getVar( "Amnt"); } catch(ex) { };
//    try { fm.all( 'SumPrem' ).value = parent.VD.gVSwitch.getVar( "SumPrem"); } catch(ex) { };
//    try { fm.all( 'SumPay' ).value = parent.VD.gVSwitch.getVar( "SumPay"); } catch(ex) { };
//    try { fm.all( 'Dif' ).value = parent.VD.gVSwitch.getVar( "Dif"); } catch(ex) { };
//    try { fm.all( 'Remark' ).value = parent.VD.gVSwitch.getVar( "Remark"); } catch(ex) { };
//    try { fm.all( 'StandbyFlag1' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag1"); } catch(ex) { };
//    try { fm.all( 'StandbyFlag2' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag2"); } catch(ex) { };
//    try { fm.all( 'StandbyFlag3' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag3"); } catch(ex) { };
//    try { fm.all( 'InputOperator' ).value = parent.VD.gVSwitch.getVar( "InputOperator"); } catch(ex) { };
//    try { fm.all( 'InputDate' ).value = parent.VD.gVSwitch.getVar( "InputDate"); } catch(ex) { };
//    try { fm.all( 'InputTime' ).value = parent.VD.gVSwitch.getVar( "InputTime"); } catch(ex) { };
//    try { fm.all( 'ApproveFlag' ).value = parent.VD.gVSwitch.getVar( "ApproveFlag"); } catch(ex) { };
//    try { fm.all( 'ApproveCode' ).value = parent.VD.gVSwitch.getVar( "ApproveCode"); } catch(ex) { };
//    try { fm.all( 'ApproveDate' ).value = parent.VD.gVSwitch.getVar( "ApproveDate"); } catch(ex) { };
//    try { fm.all( 'ApproveTime' ).value = parent.VD.gVSwitch.getVar( "ApproveTime"); } catch(ex) { };
//    try { fm.all( 'UWOperator' ).value = parent.VD.gVSwitch.getVar( "UWOperator"); } catch(ex) { };
//    try { fm.all( 'UWFlag' ).value = parent.VD.gVSwitch.getVar( "UWFlag"); } catch(ex) { };
//    try { fm.all( 'UWDate' ).value = parent.VD.gVSwitch.getVar( "UWDate"); } catch(ex) { };
//    try { fm.all( 'UWTime' ).value = parent.VD.gVSwitch.getVar( "UWTime"); } catch(ex) { };
//    try { fm.all( 'AppFlag' ).value = parent.VD.gVSwitch.getVar( "AppFlag"); } catch(ex) { };
//    try { fm.all( 'PolApplyDate' ).value = parent.VD.gVSwitch.getVar( "PolApplyDate"); } catch(ex) { };
//    try { fm.all( 'CustomGetPolDate' ).value = parent.VD.gVSwitch.getVar( "CustomGetPolDate"); } catch(ex) { };
//    try { fm.all( 'GetPolDate' ).value = parent.VD.gVSwitch.getVar( "GetPolDate"); } catch(ex) { };
//    try { fm.all( 'GetPolTime' ).value = parent.VD.gVSwitch.getVar( "GetPolTime"); } catch(ex) { };
//    try { fm.all( 'State' ).value = parent.VD.gVSwitch.getVar( "State"); } catch(ex) { };

    //集体投保人信息
    //alert("GrpNo"+parent.VD.gVSwitch.getVar( "GrpNo"))

    try { fm.all( 'AppGrpNo' ).value = parent.VD.gVSwitch.getVar( "GrpNo"); } catch(ex) { };

    try { fm.all( 'ContNo' ).value = parent.VD.gVSwitch.getVar( "ContNo"); } catch(ex) { };




}
//获得合同,投保人,被保险人信息
function getContInput()
{
    //alert("getContInput:"+parent.VD.gVSwitch.getVar("GrpContNo"))  ;
    //alert("here!");
    //try{fm.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    //try{fm.all('ProposalNo').value=parent.VD.gVSwitch.getVar("ProposalContNo");}catch(ex){};
    //alert("fm.ProposalContNo.value=="+fm.ProposalContNo.value);
    try{fm.all('ProposalContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{fm.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{fm.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{fm.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    try{fm.all('ContType').value=parent.VD.gVSwitch.getVar("ContType");}catch(ex){};
    try{fm.all('FamilyType').value=parent.VD.gVSwitch.getVar("FamilyType");}catch(ex){};
    try{fm.all('PolType').value=parent.VD.gVSwitch.getVar("PolType");}catch(ex){};
    try{fm.all('CardFlag').value=parent.VD.gVSwitch.getVar("CardFlag");}catch(ex){};
    try{fm.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{fm.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{fm.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{fm.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{fm.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{fm.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{fm.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
//    try{fm.all('Handler').value=parent.VD.gVSwitch.getVar("Handler");}catch(ex){};
    try{fm.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{fm.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{fm.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{fm.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{fm.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{fm.all('AppntIDType').value=parent.VD.gVSwitch.getVar("AppntIDType");}catch(ex){};
    try{fm.all('AppntIDNo').value=parent.VD.gVSwitch.getVar("AppntIDNo");}catch(ex){};
    try{fm.all('CustomerNo').value=parent.VD.gVSwitch.getVar("InsuredNo");}catch(ex){};
    try{fm.all('Name').value=parent.VD.gVSwitch.getVar("Name");}catch(ex){};
    try{fm.all('Sex').value=parent.VD.gVSwitch.getVar("Sex");}catch(ex){};
    try{fm.all('Birthday').value=parent.VD.gVSwitch.getVar("Birthday");}catch(ex){};
    try{fm.all('IDType').value=parent.VD.gVSwitch.getVar("IDType");}catch(ex){};
    try{fm.all('IDNo').value=parent.VD.gVSwitch.getVar("IDNo");}catch(ex){};
    try{fm.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    //try{fm.all('InsuredAppAge').value=parent.VD.gVSwitch.getVar("InsuredAppAge");}catch(ex){};
    try{fm.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
//   try{fm.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
//   try{fm.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
//   try{fm.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{fm.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{fm.all('AppntGrade').value=parent.VD.gVSwitch.getVar("AppntGrade");}catch(ex){};
    try{fm.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{fm.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{fm.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{fm.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{fm.all('State').value=parent.VD.gVSwitch.getVar("State");}catch(ex){};
    try{fm.all('AppntType').value=parent.VD.gVSwitch.getVar("AppntType");}catch(ex){};
    try{fm.all('Operator').value=parent.VD.gVSwitch.getVar("Operator");}catch(ex){};
    try{fm.all('MakeDate').value=parent.VD.gVSwitch.getVar("MakeDate");}catch(ex){};
    try{fm.all('MakeTime').value=parent.VD.gVSwitch.getVar("MakeTime");}catch(ex){};
    try{fm.all('ModifyDate').value=parent.VD.gVSwitch.getVar("ModifyDate");}catch(ex){};
    try{fm.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
    
    //tongmeng 2009-03-23 Modify
    //支持团单下个单生效日的指定.
    /*var tCValidate = parent.VD.gVSwitch.getVar("CValiDate");
    var tContCvaliDate = parent.VD.gVSwitch.getVar("ContCValiDate");
    
    alert("tContCvaliDate:"+tContCvaliDate+":tCValidate"+tCValidate)
    if(tContCvaliDate!=null&&tContCvaliDate!='')
    {
    	try{fm.all('CValiDate').value=tContCvaliDate;}catch(ex){};
    }
    else
    {
    	try{fm.all('CValiDate').value=parent.VD.gVSwitch.getVar("CValiDate");}catch(ex){};
    }*/
    try{fm.all('CValiDate').value=parent.VD.gVSwitch.getVar("CValiDate");}catch(ex){};
    try{fm.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    try{fm.all('InsuredPeoples').value=parent.VD.gVSwitch.getVar("InsuredPeoples");}catch(ex){};
    //alert("fm.all('CValiDate').value=="+fm.all('InsuredPeoples').value);
//alert("AppntNo"+parent.VD.gVSwitch.getVar("AppntNo"));
//alert("ManageCom"+parent.VD.gVSwitch.getVar("ManageCom"));


}
</script>
