<script language="JavaScript">

	function initInputBox(flag) {
	 switch (flag)
	 {
	    case 2 : {InitCopy();break;} //复制
	    case 3 : {InitTest();break;} //测试
	    case 4 : {InitModify();break;} //修改
	    case 5 : {InitApprove();break;} //审批
	    case 6 : {InitDeploy();break;} //部署
	    case 7 : {InitDrop();break;} //作废
	    case 8 : {InitSelect();break;} //查询
	    case 9 : {InitPrint();break;} //打印
	             
	 } 	
	}

	function initForm(flag) {
		try {
			initInputBox(flag);
			initQueryGrpGrid();	
		} catch (re) {
			alert("RuleQueryInit.jsp InitForm函数中发生异常:初始化界面错误!");
		}
	}

    function InitCopy(){
        var CopyDiv=document.getElementById("CopyDiv");
        CopyDiv.style.display='';
        //initIbrmsState(1);
    }
    
    function InitTest(){
        var TestDiv=document.getElementById("TestDiv");
        TestDiv.style.display='';
        initIbrmsState(1);
    }
    
    function InitModify(){
    	var ModifyDiv=document.getElementById("ModifyDiv");
    	ModifyDiv.style.display='';
    	initIbrmsState(2);
    }

    function InitApprove(){

       var ApproveDiv=document.getElementById("ApproveDiv");
       ApproveDiv.style.display='';
       initIbrmsState(3);
       
	}

    function InitDeploy(){
       var DeployDiv=document.getElementById("DeployDiv");
       DeployDiv.style.display='';
       initIbrmsState(4);     
    }
    
    function InitDrop(){
    
     var DropDiv=document.getElementById("DropDiv");
       DropDiv.style.display='';
       initIbrmsState(7);
    }

    function InitSelect(){
    
      var DivSelect=document.getElementById("DivSelect");
       DivSelect.style.display='';
       
     //   var stateSQL="select code,codename from ldcode where 1=1 and codetype='ibrmsstate'";

	  //  var str=easyQueryVer3(stateSQL);
	  //  var stateArray=decodeEasyQueryResult(str);
	  //  try{
		//    fm.IbrmsState.value=stateArray[0][0];
		   // fm.IbrmsState.readOnly= false ;
		//    fm.ibrmsstateName.value=stateArray[0][1];
		   // fm.ibrmsstateName.readOnly= false;
	  //     }catch(e)
	   //     {
	   //     	alert("状态信息初始化错误！");
	   //         }
    
    }	
	
    function InitPrint(){
    	var PrintDiv=document.getElementById("PrintDiv");
    	PrintDiv.style.display='';
    }	
    	
	function initQueryGrpGrid() {

		var iArray = new Array();
        var i11Array="0^3|审批通过^4|审批未通过";
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "规则名称"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "80px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
			//iArray[1][4] = "ibrmsresulttype";

			iArray[2] = new Array();
			iArray[2][0] = "业务模块"; //列名
			iArray[2][1] = "80px"; //列宽
			iArray[2][2] = 100; //列最大值
			iArray[2][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[2][4] = "ibrmsbusiness";

			iArray[3] = new Array();
			iArray[3][0] = "规则级别"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[3][4] = "ibrmstemplatelevel";

			iArray[4] = new Array();
			iArray[4][0] = "生效日期"; //列名
			iArray[4][1] = "80px"; //列宽
			iArray[4][2] = 100; //列最大值
			iArray[4][3] = 8; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[4][4] = "IbrmsValid";
			
			iArray[5] = new Array();
			iArray[5][0] = "失效日期"; //列名
			iArray[5][1] = "80px"; //列宽
			iArray[5][2] = 100; //列最大值
			iArray[5][3] = 8; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[5][4] = "IbrmsCommandType";
			
			iArray[6] = new Array();
			iArray[6][0] = "创建者"; //列名
			iArray[6][1] = "80px"; //列宽
			iArray[6][2] = 100; //列最大值
			iArray[6][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[6][4] = "IbrmsResulttype";

			iArray[7] = new Array();
			iArray[7][0] = "状态"; //列名
			iArray[7][1] = "100px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 2; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			iArray[7][4] = "ibrmsstate";
			
			iArray[8] = new Array();
			iArray[8][0] = "规则描述"; //列名
			iArray[8][1] = "180px"; //列宽
			iArray[8][2] = 100; //列最大值
			iArray[8][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[8][4] = "ibrmsvalid";
		
		    iArray[9] = new Array();
			iArray[9][0] = "id"; //列名
			iArray[9][1] = "50px"; //列宽
			iArray[9][2] = 100; //列最大值
			iArray[9][3] = 3; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			//iArray[9][4] = "IbrmsValid";

			iArray[10] = new Array();
			iArray[10][0] = "来源表名"; //列名
			iArray[10][1] = "100px"; //列宽
			iArray[10][2] = 100; //列最大值
			iArray[10][3] = 3; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
		  iArray[11]=new Array();
			iArray[11][0]="审批意见"; 
			iArray[11][1]="100px";
			iArray[11][2]=10;
			iArray[11][3]=3;
			iArray[11][10]='PlanState';  //引用代码："PlanState"为传入数据的名称 
			iArray[11][11]=i11Array; //i11Array 是传入要下拉显示的代码
			iArray[11][12]="10";			
			iArray[11][13]="0";
			iArray[11][19]= 3; 

		 iArray[12]=new Array();
			iArray[12][0]="规则类型"; 
			iArray[12][1]="100px";
			iArray[12][2]=10;
			iArray[12][3]=3;
		
			
			
     QueryGrpGrid = new MulLineEnter( "document" , "QueryGrpGrid" );
     //这些属性必须在loadMulLine前
      QueryGrpGrid.mulLineCount = 5;
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

	function initIbrmsState(flag){
		//var stateSQL = "select code,codename from ldcode where code="+flag+" and codetype='ibrmsstate'";
	  var sqlid8="RuleQuerySql8";
		var mySql8=new SqlClass();
		mySql8.setResourceName(tResourceName); //指定使用的properties文件名
		mySql8.setSqlId(sqlid8);//指定使用的Sql的id
		mySql8.addSubPara(flag);//指定传入的参数
		
		
	  var stateSQL =mySql8.getString();	
		var str = easyQueryVer3(stateSQL);
		var stateArray = decodeEasyQueryResult(str);
		try {
			fm.IbrmsState.value = stateArray[0][0];
			fm.IbrmsState.readOnly = "true";
            //下拉不可用
			fm.IbrmsState.ondblclick = " ";
			fm.ibrmsstateName.value = stateArray[0][1];
			fm.ibrmsstateName.readOnly = "true";
		} catch (e) {
			alert("状态信息初始化错误！");
		}
	}
</script>
