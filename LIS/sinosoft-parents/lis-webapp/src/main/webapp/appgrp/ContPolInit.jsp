<%
//程序名称：GroupPolInput.jsp
//程序功能：
//创建日期：2002-08-15 11:48:43
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
function initInpBox()
{
	try 
	{
		document.all('ProposalGrpContNo').value = prtNo;
		document.all('GrpContNo').value = prtNo;
		document.all('PrtNo').value = prtNo;
		document.all('ManageCom').value = ManageCom;
		//alert(ManageCom);
		document.all('MissionID').value = MissionID;
		document.all('SubMissionID').value = SubMissionID;
		document.all('ActivityID').value = ActivityID;
		document.all('NoType').value = NoType;
		//document.all('RiskCurrency').value = '01'; //默认为人民币01
		//showOneCodeName('currency','RiskCurrency','RiskCurrencyName');
		//初始化界面的销售渠道
		document.all('SaleChnl').value = '01';

		//保全调用会传2过来，否则默认为0，将付值于保单表中的appflag字段
		if (BQFlag=="2")
		{
			addClick();
		}
	}
	catch(ex)
	{
		alert("在GroupPolInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
//alert(LoadFlag);
	try
	{
	
		
		if(scantype=="scan")
	    	{
	    	
	    		setFocus();
	    	}
	  
		initMissionID();
		initInpBox();
		initRiskGrid();
		//fillriskgrid();
		//initHistoryImpartGrid();
		//initDiseaseGrid();
		//initImpartGrid();
		//initServInfoGrid();
		initMultiAgentGrid();
		initSpecInfoGrid();
		querySpec();
//alert("a");		
		//alert("LoadFlag=="+LoadFlag);
		if(this.ScanFlag == "1"||this.ScanFlag == "0")
		{
			fm.PrtNo.readOnly=true;
			//alert("LoadFlag=="+LoadFlag);
			var arrResult = easyExecSql("select * from LCGrpCont where PrtNo = '" + prtNo + "'", 1, 0);
			//显示代码选择中文
			if (arrResult != null)
			{
				polNo=arrResult[0][0];
				//LoadFlag=2;
			}
			else
			{
			var arrResult1 = easyExecSql("select paymode,bankcode,bankaccno from Ljtempfeeclass where otherno = '" + prtNo + "' and othernotype='4' and paymode in ('3','4','5')", 1, 0);	
			if(arrResult1 != null)
			{
			document.all('GetFlag').value=arrResult1[0][0];
			document.all('BankCode').value=arrResult1[0][1];
			document.all('BankAccNo').value=arrResult1[0][2];	
			showOneCodeName('paymode','GetFlag','GetFlagName');
	    showOneCodeName('bank','BankCode','BankCodeName');
			}	
			}
		}
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
		if(this.LoadFlag =="4")
		{
		//tongmeng 2009-04-10 add
		//控制按钮灰暗
		ctrlButtonDisabled(prtNo,LoadFlag);
		}
		QueryChargeDate();
//alert("b");		
		//alert("1");
		//alert(LoadFlag);
		if(this.LoadFlag == "2")
		{ 
			addGrpVar();
			if(polNo!="")
			{
				//显示代码选择中文
				parent.fraInterface.showCodeName();
//alert("b");		
				getapproveinfo();
//alert("c");		
			}
			document.getElementById("divnormalquesbtn").style.display="";
		
		//代码汉化的反显函数
		//alert("1");
		//QueryChargeDate();
		//QueryCValiDate();  
		//alert("2");
		showCodeName();
		//alert("3");
		return;
		}
		//alert("2");
		if(this.LoadFlag == "4")
		{
			//显示代码选择中文
			parent.fraInterface.showCodeName();
			document.getElementById("divRiskDeal").style.display="none";
			document.getElementById("divButton").style.display="none";
			document.getElementById("divnormalbtn").style.display="none";
			document.getElementById("divchangplanbtn").style.display="none";
			document.getElementById("divGroupPol4").style.display="none";
			document.getElementById("divapprovenormalbtn").style.display="";
			document.getElementById("divapprovebtn").style.display="";
			getapproveinfo();
		  //代码汉化的反显函数
		  showCodeName();
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		if(this.LoadFlag == "6")
		{
			//显示代码选择中文
			parent.fraInterface.showCodeName();
			document.getElementById("divButton").style.display="none";
			document.getElementById("divchangplanbtn").style.display="none";
			document.getElementById("divnormalbtn").style.display="none";
			document.getElementById("divapprovenormalbtn").style.display="";
			getapproveinfo();
		  //代码汉化的反显函数
		  showCodeName();

	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		if(this.LoadFlag == "13")
		{
			//显示代码选择中文
			// parent.fraInterface.showCodeName();
			document.getElementById("divchangplanbtn").style.display="none";
			//divButton.style.display="none";
			document.getElementById("divnormalbtn").style.display="none";
			document.getElementById("divapprovenormalbtn").style.display="";
			document.getElementById("divapproveupdatebtn1").style.display="";
			getapproveinfo();
		  //代码汉化的反显函数
		  showCodeName();
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		if(this.LoadFlag == "14")
		{
			//显示代码选择中文
			// parent.fraInterface.showCodeName();
			document.getElementById("divchangplanbtn").style.display="none";
			document.getElementById("divButton").style.display="";
			document.getElementById("divnormalbtn").style.display="";
			document.getElementById("divuwupdatebtn").style.display="";
			//divapprovenormalbtn.style.display="";
			//divapproveupdatebtn.style.display="";
			getapproveinfo();
		  //代码汉化的反显函数
		  showCodeName();
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		if(this.LoadFlag == "16")
		{
			//显示代码选择中文
			document.getElementById("divRiskDeal").style.display="none";
			parent.fraInterface.showCodeName();
			document.getElementById("divButton").style.display="none";
			
			document.getElementById("divnormalbtn").style.display="none";
			document.getElementById("divchangplanbtn").style.display="none";
			document.getElementById("divapprovenormalbtn").style.display="";
			document.getElementById("divquerybtn").style.display="";

			fm.feewatch.disabled="none";

			getapproveinfo();
		  //代码汉化的反显函数
		  showCodeName();
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
	    
		  return;
		}
		
		
		if(this.LoadFlag == "18")
		{
			//显示代码选择中文
			document.getElementById("divRiskDeal").style.display="none";
			parent.fraInterface.showCodeName();
			document.getElementById("divButton").style.display="none";
			document.getElementById("divnormalbtn").style.display="none";
			document.getElementById("divchangplanbtn").style.display="none";
			document.getElementById("divapprovenormalbtn").style.display="none";
			document.getElementById("divnopertoperbtn").style.display="";
			document.getElementById("divquerybtn").style.display="";
			getapproveinfo();
		  //代码汉化的反显函数
		  showCodeName();
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		
		
		if(this.LoadFlag == "99")
		{
			//显示代码选择中文
			document.getElementById("divRiskDeal").style.display="none";
			document.getElementById("divButton").style.display="none";
			document.getElementById("divnormalbtn").style.display="none";
			document.getElementById("divchangplanbtn").style.display="none";
			document.getElementById("autoMoveButton").style.display="";
		  //代码汉化的反显函数
		  showCodeName();
		  //alert(scantype);
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    	//alert("ok");
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		
		if(this.LoadFlag =="23")
		{
			document.getElementById("divButton").style.display="none";
			document.getElementById("divnormalbtn").style.display="none";
			document.getElementById("divapprovenormalbtn").style.display="";
			document.getElementById("divapproveupdatebtn").style.display="none";
			document.getElementById("divchangplanbtn").style.display="";
			getapproveinfo();
		  //代码汉化的反显函数
		  showCodeName();
	    try
	    {
	    	if(scantype=="scan")
	    	{
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		
	}
	catch(re)
	{
		alert("GroupPolInputInit.jsp-->InitForm1函数中发生异常:初始化界面错误!"+re);
	}
}

// 险种信息列表的初始化
function initRiskGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="40px";
		iArray[0][2]=1;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="险种编码";
		iArray[1][1]="60px";
		iArray[1][2]=20;
		iArray[1][3]=0;
		//iArray[1][4]="RiskCode";
		//iArray[1][5]="1|2";
		//iArray[1][6]="0|1";
		//iArray[1][9]="险种编码|code:RiskCode&NOTNULL";
		//iArray[1][18]=300;
		//iArray[1][19]=1;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="200px";
		iArray[2][2]=20;
		iArray[2][3]=0;
		//iArray[2][9]="险种名称|NOTNULL";

		iArray[3]=new Array();
		iArray[3][0]="交费期间";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=1;
		//iArray[3][9]="保费合计|NUM&NOTNULL";

		iArray[4]=new Array();
		iArray[4][0]="应保人数";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;
		//iArray[4][9]="预计交费金额|NUM&NOTNULL";

		iArray[5]=new Array();
		iArray[5][0]="投保人数";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		//iArray[5][9]="交费金额|NUM&NOTNULL";

		iArray[6]=new Array();
		iArray[6][0]="保费";
		iArray[6][1]="80px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		//iArray[6][9]="保费合计|NUM&NOTNULL";
		
		iArray[7]=new Array();
		iArray[7][0]="保额";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=0;
		//iArray[7][9]="保费合计|NUM&NOTNULL";
		
		iArray[8]=new Array();
		iArray[8][0]="缴费终止日";
		iArray[8][1]="80px";
		iArray[8][2]=20;
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="分红标记";
		iArray[9][1]="70px";
		iArray[9][2]=20;
		iArray[9][3]=3;
		
		iArray[10]=new Array();
		iArray[10][0]="手续费比例";
		iArray[10][1]="70px";
		iArray[10][2]=20;
		iArray[10][3]=0;
		
		iArray[11]=new Array();
		iArray[11][0]="佣金比例";
		iArray[11][1]="70px";
		iArray[11][2]=20;
		iArray[11][3]=0;
		
		iArray[12]=new Array();
		iArray[12][0]="红利分配比例";
		iArray[12][1]="80px";
		iArray[12][2]=20;
		iArray[12][3]=0;
		
		iArray[13]=new Array();
		iArray[13][0]="固定收益比例";
		iArray[13][1]="80px";
		iArray[13][2]=20;
		iArray[13][3]=0;
		
		iArray[14]=new Array();
		iArray[14][0]="业务类型";
		iArray[14][1]="60px";
		iArray[14][2]=20;
		iArray[14][3]=0;
		
		//add by 2011-09-28 增加币种显示
		iArray[15]=new Array();
		iArray[15][0]="币种";
		iArray[15][1]="60px";
		iArray[15][2]=20;
		iArray[15][3]=0;

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" );
		//这些属性必须在loadMulLine前
		RiskGrid.mulLineCount = 5;
		RiskGrid.displayTitle = 1;
		RiskGrid.canChk =1;
		RiskGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		RiskGrid. hiddenSubtraction=1;
		RiskGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

//团体告知参数
function initImpartGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="告知版别";
		iArray[1][1]="60px";
		iArray[1][2]=60;
		iArray[1][3]=2;
		iArray[1][4]="GrpImpartVer";
		iArray[1][9]="告知版别|len<=5";
		iArray[1][18]=300;

		iArray[2]=new Array();
		iArray[2][0]="告知编码";
		iArray[2][1]="60px";
		iArray[2][2]=60;
		iArray[2][3]=2;
		iArray[2][4]="ImpartCode";
		iArray[2][5]="2|3";
		iArray[2][6]="0|1";
		//iArray[2][7]="getImpartCode";
		iArray[2][9]="告知编码|len<=4";
		iArray[2][15]="ImpartVer";
		iArray[2][17]="1";
		iArray[2][18]=700;

		iArray[3]=new Array();
		iArray[3][0]="告知内容";
		iArray[3][1]="250px";
		iArray[3][2]=200;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="填写内容";
		iArray[4][1]="150px";
		iArray[4][2]=150;
		iArray[4][3]=1;
		iArray[4][9]="填写内容|len<=200";
		ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
		//这些属性必须在loadMulLine前
		ImpartGrid.mulLineCount = 0;
		ImpartGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		ImpartGrid.loadMulLine(iArray);

		//这些操作必须在loadMulLine后面
		//ImpartGrid.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initHistoryImpartGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="保障期间";
		iArray[1][1]="50px";
		iArray[1][2]=60;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="保障期间标志";
		iArray[2][1]="65px";
		iArray[2][2]=1;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="保障内容";
		iArray[3][1]="75px";
		iArray[3][2]=300;
		iArray[3][3]=1;


		iArray[4]=new Array();
		iArray[4][0]="费率";
		iArray[4][1]="30px";
		iArray[4][2]=200;
		iArray[4][3]=1;

		iArray[5]=new Array();
		iArray[5][0]="保障";
		iArray[5][1]="30px";
		iArray[5][2]=300;
		iArray[5][3]=1;

		iArray[6]=new Array();
		iArray[6][0]="参加人数";
		iArray[6][1]="50px";
		iArray[6][2]=20;
		iArray[6][3]=1;

		iArray[7]=new Array();
		iArray[7][0]="报销人数";
		iArray[7][1]="50px";
		iArray[7][2]=20;
		iArray[7][3]=1;

		iArray[8]=new Array();
		iArray[8][0]="发生金额";
		iArray[8][1]="50px";
		iArray[8][2]=20;
		iArray[8][3]=1;

		iArray[9]=new Array();
		iArray[9][0]="报销金额";
		iArray[9][1]="50px";
		iArray[9][2]=20;
		iArray[9][3]=1;

		iArray[10]=new Array();
		iArray[10][0]="未决金额";
		iArray[10][1]="50px";
		iArray[10][2]=20;
		iArray[10][3]=1;

		iArray[11]=new Array();
		iArray[11][0]="流水号";
		iArray[11][1]="0px";
		iArray[11][2]=20;
		iArray[11][3]=3;

		HistoryImpartGrid = new MulLineEnter( "fm" , "HistoryImpartGrid" );
		//这些属性必须在loadMulLine前
		HistoryImpartGrid.mulLineCount = 0;
		HistoryImpartGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		HistoryImpartGrid.loadMulLine(iArray);
		//这些操作必须在loadMulLine后面
		//ImpartGrid.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initSpecInfoGrid() {
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许

	iArray[1]=new Array();
    iArray[1][0]="合同号"; 		//列名
    iArray[1][1]="120px";		//列宽
    iArray[1][2]=40;			//列最大值
    iArray[1][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="特约内容"; 		//列名
    iArray[2][1]="300px";		//列宽
    iArray[2][2]=40;			//列最大值
    iArray[2][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="操作员"; 	//列名
    iArray[3][1]="80px";		//列宽
    iArray[3][2]=30;			//列最大值
    iArray[3][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="录入时间"; 	//列名
    iArray[4][1]="80px";		//列宽
    iArray[4][2]=30;			//列最大值
    iArray[4][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="流水号"; 	//列名
    iArray[5][1]="0px";		//列宽
    iArray[5][2]=30;			//列最大值
    iArray[5][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();
    iArray[6][0]="团体投保单号"; 	//列名
    iArray[6][1]="0px";		//列宽
    iArray[6][2]=30;			//列最大值
    iArray[6][3]=0;			//是否允许输入,1表示允许，0表示不允许

    SpecInfoGrid = new MulLineEnter( "fm" , "SpecInfoGrid" );
    //这些属性必须在loadMulLine前
    SpecInfoGrid.mulLineCount = 1;
    SpecInfoGrid.displayTitle = 1;
    SpecInfoGrid.hiddenPlus = 1;
    SpecInfoGrid.canSel=1;
    SpecInfoGrid.hiddenSubtraction = 1;   
    SpecInfoGrid. selBoxEventFuncName ="getSpecContent";
    SpecInfoGrid.loadMulLine(iArray);

  } catch(ex) {
    alert("在ProposalInit.jsp-->initBnfGrid函数中发生异常:初始化界面错误!");
  }
}

function initDiseaseGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="发生时间";
		iArray[1][1]="100px";
		iArray[1][2]=60;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="疾病名称";
		iArray[2][1]="100px";
		iArray[2][2]=60;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="患病人数";
		iArray[3][1]="100px";
		iArray[3][2]=300;
		iArray[3][3]=1;


		iArray[4]=new Array();
		iArray[4][0]="医疗费用总额";
		iArray[4][1]="100px";
		iArray[4][2]=200;
		iArray[4][3]=1;

		iArray[5]=new Array();
		iArray[5][0]="备注";
		iArray[5][1]="100px";
		iArray[5][2]=20;
		iArray[5][3]=1;

		iArray[6]=new Array();
		iArray[6][0]="流水号";
		iArray[6][1]="0px";
		iArray[6][2]=20;
		iArray[6][3]=3;

		DiseaseGrid = new MulLineEnter( "fm" , "DiseaseGrid" );
		//这些属性必须在loadMulLine前
		DiseaseGrid.mulLineCount = 0;
		DiseaseGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		DiseaseGrid.loadMulLine(iArray);
		//这些操作必须在loadMulLine后面
		//ImpartGrid.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}

function initServInfoGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="服务类型";
		iArray[1][1]="130px";
		iArray[1][2]=200;
		iArray[1][3]=1;

		iArray[2]=new Array();
		iArray[2][0]="服务明细";
		iArray[2][1]="130px";
		iArray[2][2]=20;
		iArray[2][3]=1;

		iArray[3]=new Array();
		iArray[3][0]="服务选择值";
		iArray[3][1]="130px";
		iArray[3][2]=20;
		iArray[3][3]=1;

		iArray[4]=new Array();
		iArray[4][0]="服务备注";
		iArray[4][1]="130px";
		iArray[4][2]=20;
		iArray[4][3]=1;

		ServInfoGrid = new MulLineEnter( "fm" , "ServInfoGrid" );
		//这些属性必须在loadMulLine前
		ServInfoGrid.mulLineCount = 0;
		ServInfoGrid.displayTitle = 1;
		//ImpartGrid.tableWidth   ="500px";
		ServInfoGrid.loadMulLine(iArray);
		//这些操作必须在loadMulLine后面
		//ImpartGrid.setRowColData(1,1,"asdf");
    }
	catch(ex)
	{
		alert(ex);
	}
}

function getGrpCont()
{
	try { document.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
	try { document.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
}

function initMultiAgentGrid(){
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="业务员代码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="queryAgentGrid";  　//双击调用查询业务员的函数          

      iArray[2]=new Array();
      iArray[2][0]="业务员姓名";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="所属机构";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="所属分部";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="业绩比例(小数)";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=150;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="所属分部/辖区";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      //这些属性必须在loadMulLine前
      MultiAgentGrid.mulLineCount = 1;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
	
}

</script>
