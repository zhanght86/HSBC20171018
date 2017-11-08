<%
/***************************************************************
 * <p>ProName：LLClaimCommendHospitalInit.jsp</p>
 * <p>Title：医院编码维护</p>
 * <p>Description：医院编码维护</p>
 * <p>Copyright：Copyright (c) 2012</p>
 * <p>Company：Sinosoft</p>
 * @author   : 高冬华
 * @version  : 8.0
 * @date     : 2012-01-01
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
		
		initLLCommendHospitalGrid();
		
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
		
		fm.editHospitalButton.disabled=true;
		fm.deleteHospitalButton.disabled=true;
	
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

function initLLCommendHospitalGrid() {

	turnPage1 = new turnPageClass();
	var iArray = new Array();
	var i=0;
	try {
	
		iArray[i]=new Array();
		iArray[i][0]="序号";
		iArray[i][1]="30px";
		iArray[i][2]=30;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="医院代码";
		iArray[i][1]="100px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="医院名称";
		iArray[i][1]="200px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="医院等级编码";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
		iArray[i]=new Array();
		iArray[i][0]="医院等级";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;

		iArray[i]=new Array();
		iArray[i][0]="医院状态编码";
		iArray[i][1]="50px";
		iArray[i][2]=100;
		iArray[i++][3]=3;

		iArray[i]=new Array();
		iArray[i][0]="医院状态";
		iArray[i][1]="60px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="医院电话";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=0;
		
		iArray[i]=new Array();
		iArray[i][0]="所在地址";
		iArray[i][1]="80px";
		iArray[i][2]=100;
		iArray[i++][3]=3;
		
    LLCommendHospitalGrid = new MulLineEnter("fm","LLCommendHospitalGrid");
    LLCommendHospitalGrid.mulLineCount = 0;
    LLCommendHospitalGrid.displayTitle = 1;
    LLCommendHospitalGrid.locked = 0;
    LLCommendHospitalGrid.canSel =1;              //单选按钮，1显示，0隐藏
    LLCommendHospitalGrid.hiddenPlus=1;           //＋号，1隐藏，0显示
    LLCommendHospitalGrid.hiddenSubtraction=1;    //－号：1隐藏，0显示
    LLCommendHospitalGrid.selBoxEventFuncName = "LLCommendHospitalGridClick"; //函数名称
    LLCommendHospitalGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
      alert(ex);
  }
}
</script>
