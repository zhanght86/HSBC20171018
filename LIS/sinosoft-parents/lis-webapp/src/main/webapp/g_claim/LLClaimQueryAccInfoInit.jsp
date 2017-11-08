<%
/***************************************************************
 * <p>ProName：LLClaimQuerySocialInfoInit.jsp</p>
 * <p>Title：账户查询</p>i
 * <p>Description：账户查询</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2014-02-26
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
		
		initQuesRecordGrid();
		queryAccInfo();
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
		
		//mode:0-操作；1-查看
		if (tMode==0) {
			fm.choseButton.style.display="";
		} else {
			fm.choseButton.style.display="none";
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

function initQuesRecordGrid(){
	
	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i = 0;
	try{
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";//列宽
		iArray[i][2]=10;//列最大值
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="保单号";
		iArray[i][1]="140px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="出险人编码";
		iArray[i][1]="80px";
		iArray[i][2]=120;
		iArray[i++][3]=3;

		iArray[i]=new Array();
		iArray[i][0]="出险人名称";
		iArray[i][1]="100px";
		iArray[i][2]=120;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="省编码";
		iArray[i][1]="120px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="开户银行所在省";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="市编码";
		iArray[i][1]="120px";
		iArray[i][2]=100;
		iArray[i++][3]=3;		
		
		iArray[i]=new Array();
		iArray[i][0]="开户银行所在市";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="银行编码";
		iArray[i][1]="30px";
		iArray[i][2]=120;
		iArray[i++][3]=3;
				
		iArray[i]=new Array();
		iArray[i][0]="银行名称";
		iArray[i][1]="120px";
		iArray[i][2]=120;
		iArray[i++][3]=0;		
		
		iArray[i]=new Array();
		iArray[i][0]="账户名";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="账号";
		iArray[i][1]="120px";
		iArray[i][2]=100;
		iArray[i++][3]=0;								
		
		QuesRecordGrid = new MulLineEnter("fm","QuesRecordGrid");
		QuesRecordGrid.mulLineCount = 1;//默认初始化显示行数
		QuesRecordGrid.displayTitle = 1;
		QuesRecordGrid.locked = 0;
		QuesRecordGrid.canSel = 1;//单选按钮，1显示，0隐藏
		QuesRecordGrid.hiddenPlus = 1;//＋号，1隐藏，0显示
		QuesRecordGrid.hiddenSubtraction = 1;//－号：1隐藏，0显示
		QuesRecordGrid.loadMulLine(iArray);
        
	}catch(ex){
		alert("初始化发起问题件列表格报错!");
	}
	
}

</script>