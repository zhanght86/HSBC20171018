<%
//程序名称：ProposalInput.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
//Modify by niuzj,2006-08-23,英大需要在录入受益人信息时增加一个“性别”字段
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
	var tCodeData = "0|";
	var tt1=tt;
	var gg1;

	var str_sql = "",sql_id = "", my_sql = "";   //绑定变量访问数据库
	sql_id = "ProposalInitSql1";
	my_sql = new SqlClass();
	my_sql.setResourceName("app.ProposalInitSql"); //指定使用的properties文件名
	my_sql.setSqlId(sql_id);//指定使用的Sql的id
	my_sql.addSubPara(tt1);//指定传入的参数
	str_sql = my_sql.getString();
	if(LoadFlag == '2')
	{
		tt1=null;gg1=gg;
		sql_id = "ProposalInitSql2";
		my_sql = new SqlClass();
		my_sql.setResourceName("app.ProposalInitSql"); //指定使用的properties文件名
		my_sql.setSqlId(sql_id);//指定使用的Sql的id
		my_sql.addSubPara(gg1);//指定传入的参数
		str_sql = my_sql.getString();
	}
	
	turnPage.strQueryResult  = easyQueryVer3(str_sql, 1, 0, 1);
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

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

// 输入框的初始化（单记录部分）
function initInpBox() {
  try {

	document.all('Operator').value = nullToEmpty("<%=tGI.Operator%>"); 
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
    //document.all('MainPolNo').value = '';
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
    document.all('PolApplyDate').value          = '';
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

  //附加字段
  try{
  document.all('StandbyFlag1').value = '';
  document.all('StandbyFlag2').value = '';
  document.all('StandbyFlag3').value = '';

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
     document.all('pagename').value="13";
   }
   if(checktype=="2")
   {
     param="23";
     document.all('pagename').value="23";
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

      SubInsuredGrid = new MulLineEnter( "document" , "SubInsuredGrid" );
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

//
// 多主险信息初始化
function initMultMainRiskGrid(tVCanSel)
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
      iArray[1][0]="主险险种编码";    	//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[2]=new Array();
      iArray[2][0]="主险险种名称";         			//列名
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="主险保单号码";         			//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=30;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="被保人编码";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=30;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

 	  
      MultMainRiskGrid = new MulLineEnter( "document" , "MultMainRiskGrid" );
      //这些属性必须在loadMulLine前
      MultMainRiskGrid.mulLineCount = 0;
      MultMainRiskGrid.displayTitle = 1;
      //alert(tVCanSel);
      if(tVCanSel=='1')
      {
        MultMainRiskGrid.canSel =1;
        MultMainRiskGrid.selBoxEventFuncName = "onMultMainRiskGridSelect";
      }
      else
      {
      	MultMainRiskGrid.canSel =0;
      	
      }

      MultMainRiskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      MultMainRiskGrid.hiddenSubtraction=1;
	//alert('1111111');
      //SubInsuredGrid.tableWidth = 200;
      MultMainRiskGrid.loadMulLine(iArray);
// alert('2222');
      //这些操作必须在loadMulLine后面
      //SubInsuredGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert("在ProposalInit.jsp-->initMultMainRiskGrid函数中发生异常:初始化界面错误!");
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
    iArray[1][9]="受益人类别|code:BnfType";

    iArray[2]=new Array();
    iArray[2][0]="姓名"; 	//列名
    iArray[2][1]="60px";		//列宽
    iArray[2][2]=30;			//列最大值
    iArray[2][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[2][9]="姓名|len<=20";//校验
    
    iArray[3]=new Array();
    iArray[3][0]="性别"; 	//列名
    iArray[3][1]="30px";		//列宽
    iArray[3][2]=30;			//列最大值
    iArray[3][3]=3;			//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="Sex";
    //iArray[3][9]="性别|code:Sex";
    

    iArray[4]=new Array();
    iArray[4][0]="证件类型"; 		//列名
    iArray[4][1]="50px";		//列宽
    iArray[4][2]=40;			//列最大值
    iArray[4][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[4][4]="IDType";
    iArray[4][9]="证件类型|code:IDType";

    iArray[5]=new Array();
    iArray[5][0]="证件号码"; 		//列名
    iArray[5][1]="120px";		//列宽
    iArray[5][2]=80;			//列最大值
    iArray[5][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[5][9]="证件号码|len<=20";

    iArray[6]=new Array();
    iArray[6][0]="与被保人关系"; 	//列名
    iArray[6][1]="70px";		//列宽
    iArray[6][2]=60;			//列最大值
    iArray[6][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][4]="Relation";
    iArray[6][9]="与被保人关系|code:Relation";


    iArray[7]=new Array();
    iArray[7][0]="受益顺序"; 		//列名
    iArray[7][1]="50px";		//列宽
    iArray[7][2]=40;			//列最大值
    iArray[7][3]=0;			//是否允许输入,1表示允许，0表示不允许
    iArray[7][4]="bnforder";
    iArray[7][9]="受益顺序|code:OccupationType";

    iArray[8]=new Array();
    iArray[8][0]="受益份额"; 		//列名
    iArray[8][1]="50px";		//列宽
    iArray[8][2]=40;			//列最大值
    iArray[8][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[8][9]="受益比例|num&len<=10";

    iArray[9]=new Array();
    iArray[9][0]="住址（填序号）"; 		//列名
    iArray[9][1]="0px";		//列宽
    iArray[9][2]=100;			//列最大值
    iArray[9][3]=3;			//是否允许输入,1表示允许，0表示不允许
    iArray[9][9]="住址|len<=80";

    iArray[10]=new Array();
    iArray[10][0]="证件有效期"; 		//列名
    iArray[10][1]="70px";		//列宽
    iArray[10][2]=100;			//列最大值
    iArray[10][3]=1;			//是否允许输入,1表示允许，0表示不允许

    iArray[11]=new Array();
    iArray[11][0]="速填"; 		//列名
    iArray[11][1]="30px";		//列宽
    iArray[11][2]=30;			//列最大值
    iArray[11][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[11][4]="customertype";

    iArray[12]=new Array();
    iArray[12][0]="所属被保人"; 		//列名
    iArray[12][1]="80px";		//列宽
    iArray[12][2]=30;			//列最大值
    iArray[12][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[12][10]="insured1";
    iArray[12][11]=tCodeData;
		iArray[12][19]=1;// 此处可能由于缓存原因，出现数据显示错误，故每次都强制刷新
 
    iArray[13]=new Array();
    iArray[13][0]="所属被保人内部客户号"; 		//列名
    iArray[13][1]="0px";		//列宽
    iArray[13][2]=30;			//列最大值
    iArray[13][3]=3;			//是否允许输入,1表示允许，0表示不允许

    iArray[14]=new Array();
    iArray[14][0]="所属被保人客户号"; 		//列名
    iArray[14][1]="0px";		//列宽
    iArray[14][2]=30;			//列最大值
    iArray[14][3]=3;			//是否允许输入,1表示允许，0表示不允许


    BnfGrid = new MulLineEnter( "document" , "BnfGrid" );
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

      ImpartGrid = new MulLineEnter( "document" , "ImpartGrid" );
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
	  if(BankFlag!=null && (BankFlag=="1" || BankFlag=="5"))
	  {
		iArray[3][3]=1;//银代、直销 特约录入允许输入,1表示允许，0表示不允许
	  }
	  else
	  { 
		iArray[3][3]=0;//个险特约录入不允许输入,1表示允许，0表示不允许
	  }
      iArray[3][9]="特约内容|len<=255";

      SpecGrid = new MulLineEnter( "document" , "SpecGrid" );
      //这些属性必须在loadMulLine前
      SpecGrid.mulLineCount = 1;
      SpecGrid.displayTitle =0;
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

      InsuredGrid = new MulLineEnter( "document" , "InsuredGrid" );
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
	initBnfGrid();
	initImpartGrid();
	initSpecGrid();
	initDutyGrid();
	initPremGrid();
	initDiscountGrid();
	//add by yaory for relatedinsured
  initInsuredGrid();
	//tongmeng 2008-07-02 for MultMainRisk
	//initMultMainRiskGrid();
}
function  InvestPlanInputInit(LoadFlag)
{
	//alert("InvestPlanInputInit：");
  document.all('UintLinkValiFlag').value = '4';
  document.all('UintLinkValiFlagName').value = '过犹豫期后生效';
	divInvestPlanRate.style.display="";
	if(LoadFlag=='1'||LoadFlag=='2'||LoadFlag=='8'||LoadFlag=='7'||LoadFlag=='3'||LoadFlag=='5')
	{
	divRiskPlanSave.style.display="";
	PremGrid.checkBoxAll();
 }
	///alert("@@@@@@@@@@2");
	//initInvestPlanRate();
		//alert("@@@@@@@@@@3");

	initQueryInvestPlanRateGrip();
	spanQueryInvestPlanRateGrip.style.display="none";
initInvestPlanRate();
	showDetailForCont();
	
	//alert("@@@@@@@@@@32222");
	
 if(LoadFlag=='6'){
       spanInvestPlanRate.style.display="none";
       spanQueryInvestPlanRateGrip.style.display="";
 }
}


function initQueryInvestPlanRateGrip()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1] = new Array();
      iArray[1][0] = "投资帐户号";    	        //列名
      iArray[1][1] = "80px";            		//列宽
      iArray[1][2] = 100;            			//列最大值
      iArray[1][3] = 0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "投资帐户名称";         		//列名
      iArray[2][1] = "100px";            		//列宽
      iArray[2][2] = 60;            			//列最大值
      iArray[2][3] = 0;                   			//是否允许输入,1表示允许，0表示不允许
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "投资比例上限";         		//列名
      iArray[3][1] = "0px";            		//列宽
      iArray[3][2] = 60;            			//列最大值
      iArray[3][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "投资比例下限";         		//列名
      iArray[4][1] = "0px";            		//列宽
      iArray[4][2] = 50;            			//列最大值
      iArray[4][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5] = new Array();
      iArray[5][0] = "缴费编号";         		//列名
      iArray[5][1] = "60px";            		//列宽
      iArray[5][2] = 50;            			//列最大值
      iArray[5][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[6] = new Array();
      iArray[6][0] = "缴费名称";         		//列名
      iArray[6][1] = "120px";            		//列宽
      iArray[6][2] = 100;            			//列最大值
      iArray[6][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

        
      iArray[7] = new Array();
      iArray[7][0] = "投资比例";         		//列名
      iArray[7][1] = "80px";            		//列宽
      iArray[7][2] = 100;            			//列最大值
      iArray[7][3] = 1;


     
    /*  iArray[8] = new Array();
      iArray[8][0] = "";         		//列名
      iArray[8][1] = "80px";            		//列宽
      iArray[8][2] = 100;            			//列最大值
      iArray[8][3] = 1;*/
 
      QueryInvestPlanRateGrip = new MulLineEnter( "document" , "QueryInvestPlanRateGrip" );
      //这些属性必须在loadMulLine前
      QueryInvestPlanRateGrip.mulLineCount = 0;
     QueryInvestPlanRateGrip.displayTitle = 1;
     QueryInvestPlanRateGrip.hiddenPlus = 1;
      QueryInvestPlanRateGrip.hiddenSubtraction = 1;
      QueryInvestPlanRateGrip.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}
function initForm() {
	try	{
	 
		//initMultMainRiskGrid('0');
	  //document.all("RiskCode").CodeData=getRisk();	//delete by yaory
		try
		{
		fm.ProposalContNo.value=tt;
		//	alert('111');
		  document.all('SelPolNo').value=parent.VD.gVSwitch.getVar("PolNo");
			if (document.all('SelPolNo').value=='false')
			{
			  document.all('SelPolNo').value='';
			}
			//tongmeng 2008-10-08 add for 承保计划变更
			if(LoadFlag==25&&NewChangeRiskPlanFlag!=''&&NewChangeRiskPlanFlag=='Y')
			{
				 document.all('SelPolNo').value = tempSelPolNo;
				 //parent.VD.gVSwitch.addtVar("PolNo");
				 parent.VD.gVSwitch.addVar('SelPolNo','',document.all('SelPolNo').value);
				 parent.VD.gVSwitch.addVar('PolNo','',document.all('SelPolNo').value);
				 //如果是新的承保计划变更,先初始化投保人和被保人的相关数据,并存放在缓存中....
				 //此种方式稍后设置.O(∩_∩)O~
				 //????????????
				 //获得投保人和被保人信息
				 //getAppntAndInsuredForChangeRiskPlan(tempSelPolNo);
			}
			//alert("temp:"+document.all('SelPolNo').value);
			
		}
		catch(ex){

		}
	//	alert("LoadFlag="+LoadFlag);
	  //alert("ok:"+document.all('SelPolNo').value);
	  //alert(scantype);
	  //alert(1);
    if(scantype=="scan"){
    	document.all('pagename').value='11';	  
	  	//方便录入，MINIM增加
	  	//alert("aa");
	  	document.all('RiskCode').focus();
		  //alert("ccc");
      setFocus();
		  //alert("ddd");
		  //document.all('gotoBnf').disabled="";
		  
		}
	//	alert(1);
	//
			


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
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/
     
    
	  
		if (LoadFlag == "1"&& document.all('SelPolNo').value!="") {
		//  alert(document.all('SelPolNo').value);
			var tPolNo = document.all('SelPolNo').value;
			queryPolDetail( tPolNo );
			
			divButton.style.display="none";
			divInputContButton.style.display = "none";
			divApproveContButton.style.display = "none";
			divApproveModifyContButton.style.display = "none";
		//alert("bbb");
			//工作流相关控件赋值
			document.all('MissionID').value=tMissionID;
			document.all('SubMissionID').value=tSubMissionID;
			if(ScanFlag=="1"){
			  document.all('WorkFlowFlag').value="0000001099";
			  
		  }
		  else{
			  document.all('WorkFlowFlag').value="0000001098";
		  }




	  	//方便录入，MINIM增加
	  	document.all('RiskCode').focus();
		  //alert("ccc");
      setFocus();
		  //alert("ddd");

		  return;

		}
		


		if (LoadFlag == "2") {	//集体下个人投保单录入
		   //alert(LoadFlag);
		    var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		    var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );
		    //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
		    if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		    {

		    	document.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    	}
		    else
		    {
					document.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
				}
		    if (document.all('SelPolNo').value!=""&&document.all('SelPolNo').value!='false') //如果前一个界面选中险种
		    {
		    	var tPolNo = document.all('SelPolNo').value;
					queryPolDetail( tPolNo );
					inputButton.style.display = "none";
					modifyButton.style.display = "";
		    }
		    else
		    {
					getRiskInput( tRiskCode, LoadFlag );
				}
    		//方便录入，MINIM增加
	    	document.all('RiskCode').focus();
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
	  	document.all('RiskCode').focus();
		  setFocus();
      return;

		}

		if (LoadFlag == "4") {	//集体下个人投保单明细审核
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
  		//方便录入，MINIM增加
	  	document.all('RiskCode').focus();
		  setFocus();
      return;
		}

		if (LoadFlag == "5") {	//个人投保单复核查询
		    if (scantype == "scan")
                initQueryRollSpeed();
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
    	    divButton.style.display="none";
			divInputContButton.style.display = "none";
			divApproveModifyContButton.style.display = "none";
            divApproveContButton.style.display = "";
    	    inputButton.style.display = "";
  		    //方便录入，MINIM增加
	  	    document.all('RiskCode').focus();
		    setFocus();
            return;
		}

		if (LoadFlag == "6") {	//个人投保单查询
			//<!--document.all("divHideButton").style.display="none";-->
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
		    	document.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		    }
		    else
		    {
				document.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		    if (document.all('SelPolNo').value!=""&&document.all('SelPolNo').value!='false') //如果前一个界面选中险种
		    {
		    	var tPolNo = document.all('SelPolNo').value;
				queryPolDetail( tPolNo );
				inputButton.style.display = "none";
				//modifyButton.style.display = "";
		    }
		    else
		    {
				getRiskInput( tRiskCode, LoadFlag );
			}

		  //方便录入，MINIM增加
		  document.all('RiskCode').focus();
		  setFocus();
		  return;
		}
		
		if(LoadFlag == "8"){
			 if(EdorType == "NS"){
			 	  document.all('BQContNo').value = top.opener.document.all('ContNo').value;
			 	  var aContNo = document.all('BQContNo').value;
			 	  document.all("RiskCode").CodeData = getRiskCodeNS(aContNo);
			 }
		}

		if (LoadFlag == "10") {	//个人投保单浮动费率修改
			var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
  		//方便录入，MINIM增加
	  	document.all('RiskCode').focus();
		  setFocus();
		  return;
		}

	  if (LoadFlag=="13" || LoadFlag == "14"||LoadFlag == "23")
	  {	//个人投保单浮动费率修改
      var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
		  var tGrpContNo = parent.VD.gVSwitch.getVar( "GrpContNo" );
		  var tContPlanCode = parent.VD.gVSwitch.getVar( "ContPlanCode" );

		  //alert("tContPlanCode:"+tContPlanCode+":"+tGrpContNo);
		  if (tContPlanCode=='false'||tContPlanCode==null||tContPlanCode=='')
		  {
		    document.all("RiskCode").CodeData=getRiskByGrpPolNo(tGrpContNo,LoadFlag);
		  }
		  else
		  {
			  document.all("RiskCode").CodeData=getRiskByContPlan(tGrpContNo,tContPlanCode);
			}
		  if (document.all('SelPolNo').value!=""&&document.all('SelPolNo').value!='false') //如果前一个界面选中险种
		  {
		    //alert(tPolNo);
		    var tPolNo = document.all('SelPolNo').value;
			  queryPolDetail( tPolNo );
			  inputButton.style.display = "none";
			  modifyButton.style.display = "";
		  }
		  else
		  {
			  getRiskInput( tRiskCode, LoadFlag );
			}
			//方便录入，MINIM增加
	  	document.all('RiskCode').focus();
  		setFocus();
      return;
		}

	  if (LoadFlag == "16") {	//集体下个人投保单明细查询
		  var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );
			queryPolDetail( tPolNo );
			//方便录入，MINIM增加
  		document.all('RiskCode').focus();
	  	setFocus();
	  	return;

		}

	  //人工核保修改投保单,并且是查看已有险种
	//  alert("document.all('SelPolNo').value:"+document.all('SelPolNo').value);
		if (LoadFlag =="25"&&document.all('SelPolNo').value!="") {
			var tPolNo = parent.VD.gVSwitch.getVar( "PolNo" );

			queryPolDetail( tPolNo );

//    	inputButton.style.display = "";

  		//getRiskInput( tRiskCode, LoadFlag );

  		//方便录入，MINIM增加
	  	document.all('RiskCode').focus();
		  setFocus();
		  
		  if(NewChangeRiskPlanFlag!=null&&NewChangeRiskPlanFlag=='Y')
			{
				 //getAppntAndInsuredForChangeRiskPlan(tempSelPolNo);
			} 
      return;
		}

	  if (LoadFlag == "99") {	//集体随动定制
      document.all("RiskCode").CodeData=getRiskByGrpAll();
      inputButton.style.display = "none";
			//方便录入，MINIM增加
  		document.all('RiskCode').focus();
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
    //alert("getGrpPolInfo"+parent.VD.gVSwitch.getVar("GrpContNo")+"|"+document.all('GrpContNo').value);
   try{document.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    //alert("getGrpPolInfo"+document.all('GrpContNo').value);
    try{document.all('ProposalGrpContNo').value=parent.VD.gVSwitch.getVar("ProposalGrpContNo");}catch(ex){};
    try{document.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{document.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    try{document.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{document.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{document.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{document.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{document.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{document.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{document.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{document.all('Password2').value=parent.VD.gVSwitch.getVar("Password2");}catch(ex){};
    try{document.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{document.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{document.all('Peoples2').value=parent.VD.gVSwitch.getVar("Peoples2");}catch(ex){};
    try{document.all('GrpName').value=parent.VD.gVSwitch.getVar("GrpName");}catch(ex){};
    try{document.all('BusinessType').value=parent.VD.gVSwitch.getVar("BusinessType");}catch(ex){};
    try{document.all('GrpNature').value=parent.VD.gVSwitch.getVar("GrpNature");}catch(ex){};
    try{document.all('RgtMoney').value=parent.VD.gVSwitch.getVar("RgtMoney");}catch(ex){};
    try{document.all('Asset').value=parent.VD.gVSwitch.getVar("Asset");}catch(ex){};
    try{document.all('NetProfitRate').value=parent.VD.gVSwitch.getVar("NetProfitRate");}catch(ex){};
    try{document.all('MainBussiness').value=parent.VD.gVSwitch.getVar("MainBussiness");}catch(ex){};
    try{document.all('Corporation').value=parent.VD.gVSwitch.getVar("Corporation");}catch(ex){};
    try{document.all('ComAera').value=parent.VD.gVSwitch.getVar("ComAera");}catch(ex){};
    try{document.all('Fax').value=parent.VD.gVSwitch.getVar("Fax");}catch(ex){};
    try{document.all('Phone').value=parent.VD.gVSwitch.getVar("Phone");}catch(ex){};
    try{document.all('GetFlag').value=parent.VD.gVSwitch.getVar("GetFlag");}catch(ex){};
    try{document.all('Satrap').value=parent.VD.gVSwitch.getVar("Satrap");}catch(ex){};
    try{document.all('EMail').value=parent.VD.gVSwitch.getVar("EMail");}catch(ex){};
    try{document.all('FoundDate').value=parent.VD.gVSwitch.getVar("FoundDate");}catch(ex){};
    try{document.all('GrpGroupNo').value=parent.VD.gVSwitch.getVar("GrpGroupNo");}catch(ex){};
    try { document.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };

//    try { document.all( 'DisputedFlag' ).value = parent.VD.gVSwitch.getVar( "DisputedFlag"); } catch(ex) { };
//    try { document.all( 'OutPayFlag' ).value = parent.VD.gVSwitch.getVar( "OutPayFlag"); } catch(ex) { };
//    try { document.all( 'GetPolMode' ).value = parent.VD.gVSwitch.getVar( "GetPolMode"); } catch(ex) { };
//    try { document.all( 'Lang' ).value = parent.VD.gVSwitch.getVar( "Lang"); } catch(ex) { };
//    try { document.all( 'Currency' ).value = parent.VD.gVSwitch.getVar( "Currency"); } catch(ex) { };
//    try { document.all( 'LostTimes' ).value = parent.VD.gVSwitch.getVar( "LostTimes"); } catch(ex) { };
//    try { document.all( 'PrintCount' ).value = parent.VD.gVSwitch.getVar( "PrintCount"); } catch(ex) { };
//    try { document.all( 'RegetDate' ).value = parent.VD.gVSwitch.getVar( "RegetDate"); } catch(ex) { };
//    try { document.all( 'LastEdorDate' ).value = parent.VD.gVSwitch.getVar( "LastEdorDate"); } catch(ex) { };
//    try { document.all( 'LastGetDate' ).value = parent.VD.gVSwitch.getVar( "LastGetDate"); } catch(ex) { };
//    try { document.all( 'LastLoanDate' ).value = parent.VD.gVSwitch.getVar( "LastLoanDate"); } catch(ex) { };
//    try { document.all( 'SpecFlag' ).value = parent.VD.gVSwitch.getVar( "SpecFlag"); } catch(ex) { };
//    try { document.all( 'GrpSpec' ).value = parent.VD.gVSwitch.getVar( "GrpSpec"); } catch(ex) { };
//    try { document.all( 'PayMode' ).value = parent.VD.gVSwitch.getVar( "PayMode"); } catch(ex) { };
//    try { document.all( 'SignCom' ).value = parent.VD.gVSwitch.getVar( "SignCom"); } catch(ex) { };
//    try { document.all( 'SignDate' ).value = parent.VD.gVSwitch.getVar( "SignDate"); } catch(ex) { };
//    try { document.all( 'SignTime' ).value = parent.VD.gVSwitch.getVar( "SignTime"); } catch(ex) { };
//    try { document.all( 'CValiDate' ).value = parent.VD.gVSwitch.getVar( "CValiDate"); } catch(ex) { };
//    try { document.all( 'PayIntv' ).value = parent.VD.gVSwitch.getVar( "PayIntv"); } catch(ex) { };
//    try { document.all( 'ManageFeeRate' ).value = parent.VD.gVSwitch.getVar( "ManageFeeRate"); } catch(ex) { };
//    try { document.all( 'ExpPeoples' ).value = parent.VD.gVSwitch.getVar( "ExpPeoples"); } catch(ex) { };
//    try { document.all( 'ExpPremium' ).value = parent.VD.gVSwitch.getVar( "ExpPremium"); } catch(ex) { };
//    try { document.all( 'ExpAmnt' ).value = parent.VD.gVSwitch.getVar( "ExpAmnt"); } catch(ex) { };
//    try { document.all( 'Peoples' ).value = parent.VD.gVSwitch.getVar( "Peoples"); } catch(ex) { };
//    try { document.all( 'Mult' ).value = parent.VD.gVSwitch.getVar( "Mult"); } catch(ex) { };
//    try { document.all( 'Prem' ).value = parent.VD.gVSwitch.getVar( "Prem"); } catch(ex) { };
//    try { document.all( 'Amnt' ).value = parent.VD.gVSwitch.getVar( "Amnt"); } catch(ex) { };
//    try { document.all( 'SumPrem' ).value = parent.VD.gVSwitch.getVar( "SumPrem"); } catch(ex) { };
//    try { document.all( 'SumPay' ).value = parent.VD.gVSwitch.getVar( "SumPay"); } catch(ex) { };
//    try { document.all( 'Dif' ).value = parent.VD.gVSwitch.getVar( "Dif"); } catch(ex) { };
//    try { document.all( 'Remark' ).value = parent.VD.gVSwitch.getVar( "Remark"); } catch(ex) { };
//    try { document.all( 'StandbyFlag1' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag1"); } catch(ex) { };
//    try { document.all( 'StandbyFlag2' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag2"); } catch(ex) { };
//    try { document.all( 'StandbyFlag3' ).value = parent.VD.gVSwitch.getVar( "StandbyFlag3"); } catch(ex) { };
//    try { document.all( 'InputOperator' ).value = parent.VD.gVSwitch.getVar( "InputOperator"); } catch(ex) { };
//    try { document.all( 'InputDate' ).value = parent.VD.gVSwitch.getVar( "InputDate"); } catch(ex) { };
//    try { document.all( 'InputTime' ).value = parent.VD.gVSwitch.getVar( "InputTime"); } catch(ex) { };
//    try { document.all( 'ApproveFlag' ).value = parent.VD.gVSwitch.getVar( "ApproveFlag"); } catch(ex) { };
//    try { document.all( 'ApproveCode' ).value = parent.VD.gVSwitch.getVar( "ApproveCode"); } catch(ex) { };
//    try { document.all( 'ApproveDate' ).value = parent.VD.gVSwitch.getVar( "ApproveDate"); } catch(ex) { };
//    try { document.all( 'ApproveTime' ).value = parent.VD.gVSwitch.getVar( "ApproveTime"); } catch(ex) { };
//    try { document.all( 'UWOperator' ).value = parent.VD.gVSwitch.getVar( "UWOperator"); } catch(ex) { };
//    try { document.all( 'UWFlag' ).value = parent.VD.gVSwitch.getVar( "UWFlag"); } catch(ex) { };
//    try { document.all( 'UWDate' ).value = parent.VD.gVSwitch.getVar( "UWDate"); } catch(ex) { };
//    try { document.all( 'UWTime' ).value = parent.VD.gVSwitch.getVar( "UWTime"); } catch(ex) { };
//    try { document.all( 'AppFlag' ).value = parent.VD.gVSwitch.getVar( "AppFlag"); } catch(ex) { };
//    try { document.all( 'PolApplyDate' ).value = parent.VD.gVSwitch.getVar( "PolApplyDate"); } catch(ex) { };
//    try { document.all( 'CustomGetPolDate' ).value = parent.VD.gVSwitch.getVar( "CustomGetPolDate"); } catch(ex) { };
//    try { document.all( 'GetPolDate' ).value = parent.VD.gVSwitch.getVar( "GetPolDate"); } catch(ex) { };
//    try { document.all( 'GetPolTime' ).value = parent.VD.gVSwitch.getVar( "GetPolTime"); } catch(ex) { };
//    try { document.all( 'State' ).value = parent.VD.gVSwitch.getVar( "State"); } catch(ex) { };

    //集体投保人信息
    //alert("GrpNo"+parent.VD.gVSwitch.getVar( "GrpNo"))

    try { document.all( 'AppGrpNo' ).value = parent.VD.gVSwitch.getVar( "GrpNo"); } catch(ex) { };

    try { document.all( 'ContNo' ).value = parent.VD.gVSwitch.getVar( "ContNo"); } catch(ex) { };




}
//获得合同,投保人,被保险人信息
function getContInput()
{
    //alert("getContInput:"+parent.VD.gVSwitch.getVar("GrpContNo"))  ;
    //alert("here!");
    //try{document.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    //try{document.all('ProposalNo').value=parent.VD.gVSwitch.getVar("ProposalContNo");}catch(ex){};
    //alert("fm.ProposalContNo.value=="+fm.ProposalContNo.value);
    try{document.all('ProposalContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{document.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
    try{document.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{document.all('GrpContNo').value=parent.VD.gVSwitch.getVar("GrpContNo");}catch(ex){};
    try{document.all('ContType').value=parent.VD.gVSwitch.getVar("ContType");}catch(ex){};
    try{document.all('FamilyType').value=parent.VD.gVSwitch.getVar("FamilyType");}catch(ex){};
    try{document.all('PolType').value=parent.VD.gVSwitch.getVar("PolType");}catch(ex){};
    try{document.all('CardFlag').value=parent.VD.gVSwitch.getVar("CardFlag");}catch(ex){};
    try{document.all('ManageCom').value=parent.VD.gVSwitch.getVar("ManageCom");}catch(ex){};
    try{document.all('AgentCom').value=parent.VD.gVSwitch.getVar("AgentCom");}catch(ex){};
    try{document.all('AgentCode').value=parent.VD.gVSwitch.getVar("AgentCode");}catch(ex){};
    try{document.all('AgentGroup').value=parent.VD.gVSwitch.getVar("AgentGroup");}catch(ex){};
    try{document.all('AgentCode1').value=parent.VD.gVSwitch.getVar("AgentCode1");}catch(ex){};
    try{document.all('AgentType').value=parent.VD.gVSwitch.getVar("AgentType");}catch(ex){};
    try{document.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    //alert(document.all('SaleChnl').value);
//    try{document.all('Handler').value=parent.VD.gVSwitch.getVar("Handler");}catch(ex){};
    try{document.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{document.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{document.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{document.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{document.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{document.all('AppntIDType').value=parent.VD.gVSwitch.getVar("AppntIDType");}catch(ex){};
    try{document.all('AppntIDNo').value=parent.VD.gVSwitch.getVar("AppntIDNo");}catch(ex){};
    try{document.all('CustomerNo').value=parent.VD.gVSwitch.getVar("InsuredNo");}catch(ex){};
    try{document.all('Name').value=parent.VD.gVSwitch.getVar("Name");}catch(ex){};
    try{document.all('Sex').value=parent.VD.gVSwitch.getVar("Sex");}catch(ex){};
    try{document.all('Birthday').value=parent.VD.gVSwitch.getVar("Birthday");}catch(ex){};
    try{document.all('IDType').value=parent.VD.gVSwitch.getVar("IDType");}catch(ex){};
    try{document.all('IDNo').value=parent.VD.gVSwitch.getVar("IDNo");}catch(ex){};
    try{document.all('AppntNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
    try{document.all('JoinCompanyDate').value=parent.VD.gVSwitch.getVar("JoinCompanyDate");}catch(ex){};
    try{document.all('Salary').value=parent.VD.gVSwitch.getVar("Salary");}catch(ex){};

    try{document.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
//   try{document.all('ContNo').value=parent.VD.gVSwitch.getVar("ContNo");}catch(ex){};
//   try{document.all('AppntCustomerNo').value=parent.VD.gVSwitch.getVar("AppntNo");}catch(ex){};
//   try{document.all('PrtNo').value=parent.VD.gVSwitch.getVar("PrtNo");}catch(ex){};
    try{document.all('AddressNo').value=parent.VD.gVSwitch.getVar("AddressNo");}catch(ex){};
    try{document.all('AppntGrade').value=parent.VD.gVSwitch.getVar("AppntGrade");}catch(ex){};
    try{document.all('AppntName').value=parent.VD.gVSwitch.getVar("AppntName");}catch(ex){};
    try{document.all('AppntSex').value=parent.VD.gVSwitch.getVar("AppntSex");}catch(ex){};
    try{document.all('AppntBirthday').value=parent.VD.gVSwitch.getVar("AppntBirthday");}catch(ex){};
    try{document.all('Password').value=parent.VD.gVSwitch.getVar("Password");}catch(ex){};
    try{document.all('State').value=parent.VD.gVSwitch.getVar("State");}catch(ex){};
    try{document.all('AppntType').value=parent.VD.gVSwitch.getVar("AppntType");}catch(ex){};
    try{document.all('Operator').value=parent.VD.gVSwitch.getVar("Operator");}catch(ex){};
    try{document.all('MakeDate').value=parent.VD.gVSwitch.getVar("MakeDate");}catch(ex){};
    try{document.all('MakeTime').value=parent.VD.gVSwitch.getVar("MakeTime");}catch(ex){};
    try{document.all('ModifyDate').value=parent.VD.gVSwitch.getVar("ModifyDate");}catch(ex){};
    try{document.all('ModifyTime').value=parent.VD.gVSwitch.getVar("ModifyTime");}catch(ex){};
    try{document.all('CValiDate').value=parent.VD.gVSwitch.getVar("CValiDate");}catch(ex){};
    try{document.all('SaleChnl').value=parent.VD.gVSwitch.getVar("SaleChnl");}catch(ex){};
    //alert("document.all('CValiDate').value=="+document.all('CValiDate').value);
//alert("AppntNo"+parent.VD.gVSwitch.getVar("AppntNo"));
//alert("ManageCom"+parent.VD.gVSwitch.getVar("ManageCom"));


}
//投连险种的投资计划
function initInvestPlanRate()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1] = new Array();
      iArray[1][0] = "投资帐户号";    	        //列名
      iArray[1][1] = "80px";            		//列宽
      iArray[1][2] = 100;            			//列最大值
      iArray[1][3] = 0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "投资帐户名称";         		//列名
      iArray[2][1] = "100px";            		//列宽
      iArray[2][2] = 60;            			//列最大值
      iArray[2][3] = 0;                   			//是否允许输入,1表示允许，0表示不允许
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "投资比例上限";         		//列名
      iArray[3][1] = "0px";            		//列宽
      iArray[3][2] = 60;            			//列最大值
      iArray[3][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "投资比例下限";         		//列名
      iArray[4][1] = "0px";            		//列宽
      iArray[4][2] = 50;            			//列最大值
      iArray[4][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5] = new Array();
      iArray[5][0] = "投资比例";         		//列名
      iArray[5][1] = "80px";            		//列宽
      iArray[5][2] = 50;            			//列最大值
      iArray[5][3] = 1;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[6] = new Array();
      iArray[6][0] = "缴费编号";         		//列名
      iArray[6][1] = "0px";            		//列宽
      iArray[6][2] = 100;            			//列最大值
      iArray[6][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="币种";         		//列名
      iArray[7][1]="50px";            		        //列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[7][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[7][9]="币种|code:Currency";
  
      /*iArray[7] = new Array();
      iArray[7][0] = "";         		//列名
      iArray[7][1] = "80px";            		//列宽
      iArray[7][2] = 100;            			//列最大值
      iArray[7][3] = 1;


     
      iArray[8] = new Array();
      iArray[8][0] = "";         		//列名
      iArray[8][1] = "80px";            		//列宽
      iArray[8][2] = 100;            			//列最大值
      iArray[8][3] = 1;*/
 
      InvestPlanRate = new MulLineEnter( "document" , "InvestPlanRate" );
      //这些属性必须在loadMulLine前
      InvestPlanRate.mulLineCount = 0;
     InvestPlanRate.displayTitle = 1;
     InvestPlanRate.hiddenPlus = 1;
      InvestPlanRate.hiddenSubtraction = 1;
      InvestPlanRate.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

function initDiscountGrid()
  {                
               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="折扣编码";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="折扣类型编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="折扣类型";         		//列名
      iArray[3][1]="90px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="折扣算法编码";         		//列名
      iArray[4][1]="90px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="责任编码";         		//列名
      iArray[5][1]="90px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="责任名称";         		//列名
      iArray[6][1]="90px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="是否勾选";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="折扣顺序";         		//列名
      iArray[8][1]="90px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][9]="折扣顺序|INT";
      
      DiscountGrid = new MulLineEnter( "document" , "DiscountGrid" ); 
      //这些属性必须在loadMulLine前
      DiscountGrid.mulLineCount = 5;   
      DiscountGrid.displayTitle = 1;
      DiscountGrid.locked = 1;
      DiscountGrid.canChk = 1;
      //DiscountGrid.canSel=1;
      DiscountGrid.hiddenPlus = 1;
      DiscountGrid.hiddenSubtraction = 1;
      DiscountGrid.loadMulLine(iArray);  
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      //DiscountGrid.checkBoxAll();
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
