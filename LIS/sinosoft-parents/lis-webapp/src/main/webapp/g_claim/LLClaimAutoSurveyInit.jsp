<%
/***************************************************************
 * <p>ProName：LLClaimSpotCheckInit.js</p>
 * <p>Title：初始化抽检规则</p>
 * <p>Description：初始化抽检规则</p>
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

		initLLClaimRuleListGrid();				
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数
 */
function initParam() {
	
	try {
		
		initQueryInfo();
		initDetailInfo();
		 	
	} catch (re) {
		alert("初始化参数错误！");
	}
}

/**
 * 初始化查询条件
 */
function initQueryInfo() {
	
	fm.QueryManageCom.value = "";
	fm.QueryManageComName.value = "";
	fm.QueryRiskcode.value = "";
	fm.QueryRiskName.value = "";
	fm.QueryUWMoney.value = "";	
	fm.QueryPayMoney.value = "";
	fm.QueryStartDate.value = "";	
	fm.QueryEndDate.value = "";
}

/**
 * 初始化明细
 */
function initDetailInfo() {
	
	fm.RuleNo.value = "";
	fm.CheckManageCom.value = "";
	fm.CheckManageComName.value = "";	
	fm.CheckRiskCode.value = "";
	fm.CheckRiskName.value = "";
	fm.CheckAppPay.value = "";
	fm.CheckClmPay.value = "";
	fm.CheckStartDate.value = "";
	fm.CheckEndDate.value = "";	
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
		tSQLInfo.setResourceName("g_claim.LLClaimSpotCheckSql");
		tSQLInfo.setSqlId("LLClaimSpotCheckSql3");
		tSQLInfo.addSubPara(mManageCom);
		
		var tArr = easyExecSql(tSQLInfo.getString(), 1, 0, 1);
		if (tArr==null || tArr.length==0) {
			alert("机构级别获得失败！");
			return false;
		} else {
			var tComGrade = tArr[0][0];
			if (tComGrade!=null && tComGrade=="03") {//三级机构不能维护规则
				
				fm.querybutton.disabled = "true";
				fm.exportbutton.disabled = "true";
				fm.addButton.disabled = "true";
				fm.modifyButton.disabled = "true";
				fm.deleteButton.disabled = "true";
				fm.initButton.disabled = "true";
				alert("请在分公司或总公司机构下维护自动调查规则！");
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
// 初始化抽检规则列表
function initLLClaimRuleListGrid() {
	
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许    

    iArray[i]=new Array();
    iArray[i][0]="抽检规则序号";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;                

    iArray[i]=new Array();
    iArray[i][0]="抽检机构";             
    iArray[i][1]="80px";                
    iArray[i][2]=80;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="抽检机构名称";             
    iArray[i][1]="100px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;
    
    iArray[i]=new Array();
    iArray[i][0]="险种编码"; 
    iArray[i][1]="100px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;    
    
    iArray[i]=new Array();
    iArray[i][0]="险种名称"; 
    iArray[i][1]="140px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="审核金额"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;    

    iArray[i]=new Array();
    iArray[i][0]="赔付金额"; 
    iArray[i][1]="100px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;

    iArray[i]=new Array();
    iArray[i][0]="生效起期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="生效止期"; 
    iArray[i][1]="100px";
    iArray[i][2]=100; 
    iArray[i++][3]=0;              
    
    LLClaimRuleListGrid = new MulLineEnter( "fm" , "LLClaimRuleListGrid" ); 
    //这些属性必须在loadMulLine前
    LLClaimRuleListGrid.mulLineCount = 0;   
    LLClaimRuleListGrid.displayTitle = 1;
    LLClaimRuleListGrid.locked = 0;
    LLClaimRuleListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    LLClaimRuleListGrid.selBoxEventFuncName ="showClaimRuleDetail";
    LLClaimRuleListGrid.hiddenPlus=1;
    LLClaimRuleListGrid.hiddenSubtraction=1;
    LLClaimRuleListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("初始化界面信息出错->LLClaimCaseGrid");
    }
}
</script>
