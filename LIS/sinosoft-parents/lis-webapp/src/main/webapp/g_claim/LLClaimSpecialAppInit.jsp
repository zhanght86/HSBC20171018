<%
/***************************************************************
 * <p>ProName：LLClaimSpecialInput.js</p>
 * <p>Title：特殊立案申请</p>
 * <p>Description：特殊立案申请</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 1.0
 * @date     : 2014-04-20
 ****************************************************************/
%>
<script language="JavaScript">

/**
 * 初始化界面

 */
function initForm() {
	
	try {
		
		initParam();
		initInpBox();
		initButton();

		initMainCaseGrid();
		initSelfCaseGrid();

		querySelf();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
			
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化录入控件

 */
function initInpBox() {
	
	try {		
	} catch (ex) {
		alert("初始化录入控件错误！");
	}
}

/**
 * 初始化按钮
 */
function initButton() {
	
	try {
		
		tSQLInfo = new SqlClass();
		tSQLInfo.setResourceName("g_claim.LLClaimCaseAppSql");
		tSQLInfo.setSqlId("LLClaimCaseAppSql3");
		tSQLInfo.addSubPara(mManageCom);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("机构级别获得失败！");
			return false;
		} else {
			var tComGrade = tArr[0][0];
			if (tComGrade!=null && tComGrade=="01") {//一级机构不能新增案件
				fm.AddCase.style.display = "none";
			}
		}		
		
	} catch (ex) {
		alert("初始化按钮错误！");
	}
}

/**
 * 把null的字符串转为空

 */
function nullToEmpty(string) {
	
	if ((string=="null")||(string=="undefined")) {
		string = "";
	}
	
	return string;
}
// 初始化案件信息列表
function initMainCaseGrid() {
		
		turnPage1 = new turnPageClass();
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许    

    iArray[i]=new Array();
    iArray[i][0]="批次号";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="案件号";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="被保人姓名"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="批次申请日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="受理日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="时效天数(工作日)"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    MainCaseGrid = new MulLineEnter( "fm" , "MainCaseGrid" ); 
    //这些属性必须在loadMulLine前
    MainCaseGrid.mulLineCount = 0;   
    MainCaseGrid.displayTitle = 1;
    MainCaseGrid.locked = 0;
    MainCaseGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    MainCaseGrid.hiddenPlus=1;
    MainCaseGrid.hiddenSubtraction=1;
    MainCaseGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}

// 初始化个人池下案件信息列表
function initSelfCaseGrid() {
	   
	  turnPage2 = new turnPageClass();
    var iArray = new Array();
    var i=0;
    try{
    	
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许    

    iArray[i]=new Array();
    iArray[i][0]="批次号";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i][3]=0;
    iArray[i++][7]="enterCase";
    
    iArray[i]=new Array();
    iArray[i][0]="案件号";
    iArray[i][1]="180px";
    iArray[i][2]=180;
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="投保人名称"; 
    iArray[i][1]="180px";
    iArray[i][2]=180; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="被保人姓名"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="批次申请日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;


    iArray[i]=new Array();
    iArray[i][0]="受理日期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="时效天数(工作日)"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="受理机构"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    SelfCaseGrid = new MulLineEnter( "fm" , "SelfCaseGrid" ); 
    //这些属性必须在loadMulLine前
    SelfCaseGrid.mulLineCount = 0;   
    SelfCaseGrid.displayTitle = 1;
    SelfCaseGrid.locked = 0;
    SelfCaseGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    //SelfCaseGrid.selBoxEventFuncName ="selfLLReport"; //响应的函数名，不加扩号
    SelfCaseGrid.hiddenPlus=1;
    SelfCaseGrid.hiddenSubtraction=1;
    SelfCaseGrid.loadMulLine(iArray);  

    }
    catch(ex){
        alert("初始化界面信息出错->SelfCaseGrid");
    }
}
</script>
