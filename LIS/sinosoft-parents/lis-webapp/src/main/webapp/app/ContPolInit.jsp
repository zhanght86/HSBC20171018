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
		document.all('prtNo').value = prtNo;
		document.all('ManageCom').value = ManageCom;
		document.all('MissionID').value = MissionID;
		document.all('SubMissionID').value = SubMissionID;
		document.all('ActivityID').value = ActivityID;
		document.all('NoType').value = NoType;
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
	try
	{
		initMissionID();
		initInpBox();
		initRiskGrid();
		
		//initHistoryImpartGrid();
		//initDiseaseGrid();
		//initImpartGrid();
		//initServInfoGrid();
		initMultiAgentGrid();
		initPlanGrid();
		initPlanRiskGrid();
		
//alert("a");		
		//alert("LoadFlag=="+LoadFlag);
		if(this.ScanFlag == "1"||this.ScanFlag == "0")
		{
			fm.PrtNo.readOnly=true;
// 			var arrResult = easyExecSql("select * from LCGrpCont where PrtNo = '" + prtNo + "'", 1, 0);
			
			var sqlid1="ContPolInitSql0";
			var mySql1=new SqlClass();
			mySql1.setResourceName("app.ContPolInitSql"); //指定使用的properties文件名
			mySql1.setSqlId(sqlid1);//指定使用的Sql的id
			mySql1.addSubPara(prtNo);//指定传入的参数
			var strSql1=mySql1.getString();
			var arrResult = easyExecSql(strSql1, 1, 0);
					
			//显示代码选择中文
			if (arrResult != null)
			{
				polNo=arrResult[0][0];
				
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
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/
		
		
		
		
		if(this.LoadFlag == "2")
		{
			if(polNo!="")
			{
				//显示代码选择中文
				parent.fraInterface.showCodeName();             
				getapproveinfo();

			}
			divnormalquesbtn.style.display="";
			divUpdateButton.style.display="none";
		
    		//代码汉化的反显函数
    		//alert("1");
    		//查询保单生效日期
    		//QueryCValiDate();
    		//查询财务收费日期
    		QueryChargeDate();  
    		//alert("2");
    		showCodeName();
    		//alert("3");
    			    try
	    {
	    	if(scantype=="scan")
	    	{
	    		//alert("4");
	    		setFocus();
	    	}
	    }
	    catch(re)
	    {
	    	//alert("1");
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
    		return;
		}
		if(this.LoadFlag == "4")
		{
			//显示代码选择中文
			parent.fraInterface.showCodeName();
			divRiskDeal.style.display="none";
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divchangplanbtn.style.display="none";
			divGroupPol4.style.display="none";
			divapprovenormalbtn.style.display="";
			divapprovebtn.style.display="";
			divUpdateButton.style.display="none";
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
	    		//alert("2");
	    	alert("GroupPolInputInit.jsp-->InitForm3函数中发生异常:初始化界面错误!");
	    }
		  return;
		}
		if(this.LoadFlag == "6")
		{
			//显示代码选择中文
			parent.fraInterface.showCodeName();
			divButton.style.display="none";
			divchangplanbtn.style.display="none";
			divnormalbtn.style.display="none";
			divapprovenormalbtn.style.display="";
			divUpdateButton.style.display="none";
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
			divchangplanbtn.style.display="none";
			divButton.style.display="none";
			divnormalbtn.style.display="";
			divapprovenormalbtn.style.display="none";
			divUpdateButton.style.display="";
			divapproveupdatebtn1.style.display="";
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
			divchangplanbtn.style.display="none";
			divButton.style.display="";
			divnormalbtn.style.display="";
			divuwupdatebtn.style.display="";
			//divapprovenormalbtn.style.display="";
			//divapproveupdatebtn.style.display="";
			divUpdateButton.style.display="none";
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
			divRiskDeal.style.display="none";
			parent.fraInterface.showCodeName();
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divchangplanbtn.style.display="none";
			divapprovenormalbtn.style.display="";
			divquerybtn.style.display="";
			divUpdateButton.style.display="none";
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
			divRiskDeal.style.display="none";
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divchangplanbtn.style.display="none";
			autoMoveButton.style.display="";
			divUpdateButton.style.display="none";
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
		if(this.LoadFlag =="23")
		{
			divButton.style.display="none";
			divnormalbtn.style.display="none";
			divapprovenormalbtn.style.display="";
			divapproveupdatebtn.style.display="none";
			divchangplanbtn.style.display="";
			divUpdateButton.style.display="none";
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
		iArray[1][3]=2;
		iArray[1][4]="RiskCode";
		iArray[1][5]="1|2";
		iArray[1][6]="0|1";
		iArray[1][9]="险种编码|code:RiskCode&NOTNULL";
		iArray[1][18]=300;
		//iArray[1][19]=1;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="160px";
		iArray[2][2]=20;
		iArray[2][3]=0;
		//iArray[2][9]="险种名称|NOTNULL";

		iArray[3]=new Array();
		iArray[3][0]="交费期间";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=1;
		iArray[3][9]="保费合计|NUM&NOTNULL";

		iArray[4]=new Array();
		iArray[4][0]="应保人数";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;
		iArray[4][9]="预计交费金额|NUM&NOTNULL";

		iArray[5]=new Array();
		iArray[5][0]="参保人数";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		iArray[5][9]="交费金额|NUM&NOTNULL";
		
		iArray[6]=new Array();
		iArray[6][0]="预期保费";
		iArray[6][1]="80px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		iArray[6][9]="保费合计|NUM&NOTNULL";

		iArray[7]=new Array();
		iArray[7][0]="保费合计";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=0;
		iArray[7][9]="保费合计|NUM&NOTNULL";
		
		iArray[8]=new Array();
		iArray[8][0]="预期保额/份数";
		iArray[8][1]="80px";
		iArray[8][2]=20;
		iArray[8][3]=0;
		iArray[8][9]="保费合计|NUM&NOTNULL";
		
		iArray[9]=new Array();
		iArray[9][0]="份数合计";
		iArray[9][1]="50px";
		iArray[9][2]=20;
		iArray[9][3]=0;
		iArray[9][9]="保费合计|NUM&NOTNULL";
		
		iArray[10]=new Array();
		iArray[10][0]="保额合计";
		iArray[10][1]="80px";
		iArray[10][2]=20;
		iArray[10][3]=0;
		iArray[10][9]="保费合计|NUM&NOTNULL";
		
		iArray[11]=new Array();
		iArray[11][0]="投保比例";
		iArray[11][1]="50px";
		iArray[11][2]=20;
		iArray[11][3]=0;
		iArray[11][9]="保费合计|NUM&NOTNULL";
		
		iArray[12]=new Array();
		iArray[12][0]="币种";          
		iArray[12][1]="50px";              
		iArray[12][2]=60;             
		iArray[12][3]=2;               
		iArray[12][4]="Currency";                 
		iArray[12][9]="币种|code:Currency";

		

		RiskGrid = new MulLineEnter( "fm" , "RiskGrid" );
		//这些属性必须在loadMulLine前
		RiskGrid.mulLineCount = 0;
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


// 险种信息列表的初始化
function initPlanGrid()
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
		iArray[1][0]="产品组合编码";
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="产品组合名称";
		iArray[2][1]="160px";
		iArray[2][2]=50;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="交费期间";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="应保人数";
		iArray[4][1]="0px";
		iArray[4][2]=20;
		iArray[4][3]=3;

		iArray[5]=new Array();
		iArray[5][0]="参保人数";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="预期保费";
		iArray[6][1]="0px";
		iArray[6][2]=20;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="保费合计";
		iArray[7][1]="80px";
		iArray[7][2]=20;
		iArray[7][3]=0;
		
		iArray[8]=new Array();
		iArray[8][0]="投保比例";
		iArray[8][1]="140px";
		iArray[8][2]=20;
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="币种";          
		iArray[9][1]="50px";              
		iArray[9][2]=60;             
		iArray[9][3]=2;               
		iArray[9][4]="Currency";                 
		iArray[9][9]="币种|code:Currency";
		

		PlanGrid = new MulLineEnter( "fm" , "PlanGrid" );
		//这些属性必须在loadMulLine前
		PlanGrid.mulLineCount = 0;
		PlanGrid.displayTitle = 1;
		PlanGrid.selBoxEventFuncName = "getPlanRiskDetail"; 
		PlanGrid.canSel =1;
		PlanGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		PlanGrid.hiddenSubtraction=1;
		PlanGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

// 险种信息列表的初始化
function initPlanRiskGrid()
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
		iArray[1][1]="80px";
		iArray[1][2]=20;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="险种名称";
		iArray[2][1]="0px";
		iArray[2][2]=20;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="责任编码";
		iArray[3][1]="60px";
		iArray[3][2]=20;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="责任名称";
		iArray[4][1]="100px";
		iArray[4][2]=20;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="保费合计";
		iArray[5][1]="60px";
		iArray[5][2]=20;
		iArray[5][3]=0;
		
		iArray[6]=new Array();
		iArray[6][0]="保额合计";
		iArray[6][1]="60px";
		iArray[6][2]=20;
		iArray[6][3]=0;
		
		
		iArray[7]=new Array();
		iArray[7][0]="币种";          
		iArray[7][1]="50px";              
		iArray[7][2]=60;             
		iArray[7][3]=2;               
		iArray[7][4]="Currency";                 
		iArray[7][9]="币种|code:Currency";
		

		PlanRiskGrid = new MulLineEnter( "fm" , "PlanRiskGrid" );
		//这些属性必须在loadMulLine前
		PlanRiskGrid.mulLineCount = 0;
		PlanRiskGrid.displayTitle = 1;
		PlanRiskGrid.canSel =0; 
		PlanRiskGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
		PlanRiskGrid.hiddenSubtraction=1;
		PlanRiskGrid.loadMulLine(iArray);
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
		
		iArray[12]=new Array();
		iArray[12][0]="币种";          
		iArray[12][1]="50px";              
		iArray[12][2]=60;             
		iArray[12][3]=2;               
		iArray[12][4]="Currency";                 
		iArray[12][9]="币种|code:Currency";

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
      iArray[1][22]="queryAgent1Grid";        

      iArray[2]=new Array();
      iArray[2][0]="业务员姓名";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="所属机构";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="所属分部";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="佣金比例";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=150;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="AgentGroup";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 


      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      //这些属性必须在loadMulLine前
      MultiAgentGrid.mulLineCount = 0;   
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
