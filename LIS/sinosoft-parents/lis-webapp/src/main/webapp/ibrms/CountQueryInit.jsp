<script language="JavaScript">

	function initInputBox(flag) {
	 switch (flag)
	 { 
	    case 2 : {InitCopy();break;} //复制
	    case 3 : {InitAbilityDetail();break;} //测试
	    case 4 : {InitModify();break;} //修改
	    case 5 : {InitApprove();break;} //审批
	    case 6 : {InitDeploy();break;} //部署
	    case 7 : {InitDrop();break;} //作废
	    case 8 : {InitSelect();break;} //查询
	    case 9 : {InitPrint();break;} //打印
	             
	 } 	
	}
//违反排名详细页面初始化信息
	function InitBreakDetail(){

      document.all('ManageCom').value = ManageCom;
      document.all('Business').value = Business;
      document.all('RuleStartDate').value = RuleStartDate;
      document.all('RuleEndDate').value = RuleEndDate;
      document.all('templateid').value = templateid;
      

      //var stateSQL="select name from ldcom where comcode = " +ManageCom;
	    //var str=easyQueryVer3(stateSQL);
	   // var stateArray=decodeEasyQueryResult(str);
	    showOneCodeName('comcode','ManageCom','ManageComName');
	    showOneCodeName('ibrmsbusiness','Business','ibrmsbusinessName');
	    /*
	    try{
		    fm.ManageComName.value=stateArray[0][0];
		    fm.ManageComName.readOnly="true";
	        }catch(e)
	        {
	        	alert("机构信息初始化错误！");
	            }
      */
     /*  var stateSQL1="select CodeName from ldcode  where 1 = 1 and codetype = 'ibrmsbusiness' and code = "+Business+ " order by Code ";
	    var str=easyQueryVer3(stateSQL1);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.ibrmsbusinessName.value=stateArray[0][0];
		    fm.ibrmsbusinessName.readOnly="true";
	        }catch(e)
	        {
	        	alert("业务初始化错误！");
	            }
	            */
}
	
	
	
function InitAbilityDetail(){

      document.all('ManageCom').value = ManageCom;
      document.all('Business').value = Business;
      document.all('RuleStartDate').value = RuleStartDate;
      document.all('RuleEndDate').value = RuleEndDate;
   
      
/*
        var stateSQL="select name from ldcom where comcode = " +ManageCom;
	    var str=easyQueryVer3(stateSQL);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.ManageComName.value=stateArray[0][0];
		    fm.ManageComName.readOnly="true";
	        }catch(e)
	        {
	        	alert("机构信息初始化错误！");
	            }
      
        var stateSQL1="select CodeName from ldcode  where 1 = 1 and codetype = 'ibrmsbusiness' and code = "+Business+ " order by Code ";
	    var str=easyQueryVer3(stateSQL1);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.ibrmsbusinessName.value=stateArray[0][0];
		    fm.ibrmsbusinessName.readOnly="true";
	        }catch(e)
	        {
	        	alert("业务初始化错误！");
	            }
	            */
	             showOneCodeName('comcode','ManageCom','ManageComName');
	    showOneCodeName('ibrmsbusiness','Business','ibrmsbusinessName');
}


	function initForm(flag) {
		try {
			//alert('1');
			try{
			initInputBox(flag);
			}
			catch(e)
			{
			}
			//alert('2');
			if(flag=='2')
			{
			
			//alert('3');
			try{
			initBreakCountGrid();
			}catch(e)
			{
			}
			}
			if(flag=='21')
			{
				try{
			initBreakDetailCountGrid();
			}
			catch(e)
			{
			}
			}
			
			//alert('4');
			if(flag=='3')
			{
			try{
			initAbilityCountGrid();	
		}catch(e)
			{
			}
			//alert('5');
		/*	try{
			initAbilityDetailGrid();
			}catch(e)
			{
			}
			*/
		}
			//alert('6');
			if(flag=='1')
			{
			try{
			initQueryGrpGrid();		
			}catch(e)
			{
			}
		}
		} catch (re) {
			alert("RuleQueryInit.jsp InitForm函数中发生异常:初始化界面错误!");
		}
	}

  

    //模板统计查询	
	function initQueryGrpGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "模板号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
			//iArray[1][4] = "ibrmsresulttype";

			iArray[2] = new Array();
			iArray[2][0] = "版本号码"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "模板名"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "模板逻辑"; //列名
			iArray[4][1] = "80px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "业务模块"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "分类"; //列名
			iArray[6][1] = "30px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[6][4] = "IbrmsResulttype";

			iArray[7] = new Array();
			iArray[7][0] = "状态"; //列名
			iArray[7][1] = "50px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[7][4] = "ibrmsstate";
			
			iArray[8] = new Array();
			iArray[8][0] = "生效日期"; //列名
			iArray[8][1] = "80px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 8; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[8][4] = "IbrmsValid";
		
		    iArray[9] = new Array();
			iArray[9][0] = "失效日期"; //列名
			iArray[9][1] = "80px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 8; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[9][4] = "IbrmsValid";
			
		    iArray[10]=new Array();
			iArray[10][0]="创建人"; 
			iArray[10][1]="30px";
			iArray[10][2]=10;
			iArray[10][3]=0;
			
		    iArray[11]=new Array();
			iArray[11][0]="修改人"; 
			iArray[11][1]="30px";
			iArray[11][2]=100;
			iArray[11][3]=0;

			iArray[12]=new Array();
			iArray[12][0]="审批人"; 
			iArray[12][1]="30px";
			iArray[12][2]=100;
			iArray[12][3]=0;

			iArray[13]=new Array();
			iArray[13][0]="发布人"; 
			iArray[13][1]="30px";
			iArray[13][2]=100;
			iArray[13][3]=0;

			iArray[14]=new Array();
			iArray[14][0]="创建日期"; 
			iArray[14][1]="80px";
			iArray[14][2]=100;
			iArray[14][3]=8;

			iArray[15]=new Array();
			iArray[15][0]="审批日期"; 
			iArray[15][1]="80px";
			iArray[15][2]=100;
			iArray[15][3]=8;

			iArray[16]=new Array();
			iArray[16][0]="发布日期"; 
			iArray[16][1]="80px";
			iArray[16][2]=100;
			iArray[16][3]=8;

			iArray[17]=new Array();
			iArray[17][0]="有效标志"; 
			iArray[17][1]="110px";
			iArray[17][2]=100;
			iArray[17][3]=0;

			iArray[18]=new Array();
			iArray[18][0]="状态"; 
			iArray[18][1]="110px";
			iArray[18][2]=100;
			iArray[18][3]=0;

			iArray[19]=new Array();
			iArray[19][0]="功能描述"; 
			iArray[19][1]="110px";
			iArray[19][2]=100;
			iArray[19][3]=0;
			
		  iArray[20]=new Array();
			iArray[20][0]="表名"; 
			iArray[20][1]="110px";
			iArray[20][2]=100;
			iArray[20][3]=3;
			
      QueryGrpGrid = new MulLineEnter( "fm" , "QueryGrpGrid" );
     //这些属性必须在loadMulLine前
      QueryGrpGrid.mulLineCount = 1;
      QueryGrpGrid.displayTitle = 1;
      QueryGrpGrid.hiddenPlus = 1;
      QueryGrpGrid.hiddenSubtraction = 1;
      QueryGrpGrid.canSel= 1;
      QueryGrpGrid.canChk =0;
      QueryGrpGrid.loadMulLine(iArray);
			
			
		} catch (ex) {
			alert(ex);
		}
	}
	//违反排名查询
	function initBreakCountGrid() {

		var iArray = new Array();
		try {  
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "60px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "机构"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "180px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "业务模块"; //列名
			iArray[2][1] = "180px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "模板编号"; //列名
			iArray[3][1] = "180px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "规则名字"; //列名
			iArray[4][1] = "180px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "违反规则次数"; //列名
			iArray[5][1] = "180px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		
			
			
      BreakCountGrid = new MulLineEnter( "fm" , "BreakCountGrid" );
     //这些属性必须在loadMulLine前
      BreakCountGrid.mulLineCount = 1;
      BreakCountGrid.displayTitle = 1;
      BreakCountGrid.hiddenPlus = 1;
      BreakCountGrid.hiddenSubtraction = 1;
      BreakCountGrid.canSel= 1;
      BreakCountGrid.canChk =0;
      BreakCountGrid.loadMulLine(iArray);
	
		} catch (ex) {
			alert(ex);
		}
	}
	
	//违反排名详细查询
	function initBreakDetailCountGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "60px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "保单号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "模板号"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "规则名字"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "版本号"; //列名
			iArray[4][1] = "80px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "执行结果"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
			iArray[6] = new Array();
			iArray[6][0] = "执行日期"; //列名
			iArray[6][1] = "80px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，7表示该列是隐藏的
		//	iArray[3][7] = "ibrmstemplatelevel";

			iArray[7] = new Array();
			iArray[7][0] = "执行时间"; //列名
			iArray[7][1] = "80px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[8] = new Array();
			iArray[8][0] = "执行的顺序号"; //列名
			iArray[8][1] = "80px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
      BreakDetailCountGrid = new MulLineEnter( "fm" , "BreakDetailCountGrid" );
     //这些属性必须在loadMulLine前
      BreakDetailCountGrid.mulLineCount = 1;
      BreakDetailCountGrid.displayTitle = 1;
      BreakDetailCountGrid.hiddenPlus = 1;
      BreakDetailCountGrid.hiddenSubtraction = 1;
      BreakDetailCountGrid.canSel= 0;
      BreakDetailCountGrid.canChk =0;
      BreakDetailCountGrid.loadMulLine(iArray);
 
	
		} catch (ex) {
			alert(ex);
		}
	}
	
	function initAbilityCountGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "60px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "机构"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "180px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "业务模块"; //列名
			iArray[2][1] = "180px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "校验保单总数"; //列名
			iArray[3][1] = "180px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "平均处理时间(单位:s)"; //列名
			iArray[4][1] = "180px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "id"; //列名
			iArray[5][1] = "180px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 3;
			
      AbilityCountGrid = new MulLineEnter( "fm" , "AbilityCountGrid" );
     //这些属性必须在loadMulLine前
      AbilityCountGrid.mulLineCount = 1;
      AbilityCountGrid.displayTitle = 1;
      AbilityCountGrid.hiddenPlus = 1;
      AbilityCountGrid.hiddenSubtraction = 1;
      AbilityCountGrid.canSel= 1;
      AbilityCountGrid.canChk =0;
      AbilityCountGrid.loadMulLine(iArray);
			
			
		} catch (ex) {
			alert(ex);
		}
	}
	
	//性能详细查询
	function initAbilityDetailGrid() {

		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "60px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "机构"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "180px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 2; //是否允许输入,1表示允许，0表示不允许
			iArray[1][4] = "comcode";

			iArray[2] = new Array();
			iArray[2][0] = "业务模块"; //列名
			iArray[2][1] = "180px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "校验保单号"; //列名
			iArray[3][1] = "180px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
		//	iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "处理时间(单位:ms)"; //列名
			iArray[4][1] = "180px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "处理结果"; //列名
			iArray[5][1] = "180px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 3;
			iArray[5][4] = "ibrmsflag";
			
			iArray[6] = new Array();
			iArray[6][0] = "发生日期"; //列名
			iArray[6][1] = "180px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0;
      AbilityDetailGrid = new MulLineEnter( "fm" , "AbilityDetailGrid" );
     //这些属性必须在loadMulLine前
      AbilityDetailGrid.mulLineCount = 1;
      AbilityDetailGrid.displayTitle = 1;
      AbilityDetailGrid.hiddenPlus = 1;
      AbilityDetailGrid.hiddenSubtraction = 1;
      AbilityDetailGrid.canSel= 0;
      AbilityDetailGrid.canChk =0;
      AbilityDetailGrid.loadMulLine(iArray);
			
			
		} catch (ex) {
			alert(ex);
		}
	}
		
	
</script>
