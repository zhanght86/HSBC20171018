<%
/***************************************************************
 * <p>ProName：LLClaimDataInit.jsp</p>
 * <p>Title：索赔资料</p>
 * <p>Description：索赔资料</p>
 * <p>Copyright：Copyright (c) 2014</p>
 * <p>Company：Sinosoft</p>
 * @author   : lixf
 * @version  : 8.0
 * @date     : 2014-01-01
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

		initDocumentListGrid();
		
		initAffix();
		
	} catch (re) {
		alert("初始化界面错误！");
	}
}

/**
 * 初始化参数

 */
function initParam() {
	
	try {
		
		fm.RptNo.value = mRptNo;
		fm.CustomerNo.value = mCustomerNo;
		
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
		
		if (mType=="3" || mType=="4") {
			fm.creatAffix.disabled = true;
			fm.DocumentSave.disabled = true;
			fm.DocumentDelete.disabled = true;
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
//单证信息列表
function initDocumentListGrid() {
		
		turnPage1 = new turnPageClass();
		turnPage1.pageLineNum = 200;
    var iArray = new Array();
    var i = 0;
    try{
    
    iArray[i]=new Array();
    iArray[i][0]="序号";               //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[i][1]="30px";              //列宽
    iArray[i][2]=10;                  //列最大值
    iArray[i++][3]=0;                 //是否允许输入,1表示允许，0表示不允许

    iArray[i]=new Array();
    iArray[i][0]="单证编码";             
    iArray[i][1]="80px";                
    iArray[i][2]=100;                 
    iArray[i++][3]=3;

    iArray[i]=new Array();
    iArray[i][0]="单证名称";             
    iArray[i][1]="380px";                
    iArray[i][2]=380;                 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="单证类型编码"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=3;          

    iArray[i]=new Array();
    iArray[i][0]="单证类型"; 
    iArray[i][1]="120px";
    iArray[i][2]=120; 
    iArray[i++][3]=0;

		iArray[i] = new Array();
		iArray[i][0] = "提交形式";
		iArray[i][1] = "100px";
		iArray[i][2] = 100;
		iArray[i][3] = 2;
		iArray[i][4] = "property";
		iArray[i][5]="5|6"; 
		iArray[i++][6] = "1|0";
		
		iArray[i] = new Array();
		iArray[i][0] = "Property";
		iArray[i][1] = "0px";
		iArray[i][2] = 300;
		iArray[i++][3] = 2;		
    
    iArray[i]=new Array();
    iArray[i][0]="是否已保存"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=0;
    
    iArray[i]=new Array();
    iArray[i][0]="单证序号"; 
    iArray[i][1]="80px";
    iArray[i][2]=60; 
    iArray[i++][3]=3;    
    
    DocumentListGrid = new MulLineEnter( "fm" , "DocumentListGrid" ); 
    //这些属性必须在loadMulLine前
    DocumentListGrid.mulLineCount = 0;   
    DocumentListGrid.displayTitle = 1;
    DocumentListGrid.locked = 0;
    DocumentListGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
    DocumentListGrid.canChk = 1;
    DocumentListGrid.hiddenPlus=1;
    DocumentListGrid.hiddenSubtraction=1;
    DocumentListGrid.loadMulLine(iArray);
     
    }
    catch(ex){
        alert("ex");
    }
}
</script>
