<%@include file="/i18n/language.jsp"%>

<%
	//Creator :张斌
	//Date :2006-08-21
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>

<%@page import="com.sinosoft.utility.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<%
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput) session.getAttribute("GI");
	String Operator = tG.Operator;
	String Comcode = tG.ManageCom;
	String CurrentDate = PubFun.getCurrentDate();
	String tCurrentYear = StrTool.getVisaYear(CurrentDate);
	String tCurrentMonth = StrTool.getVisaMonth(CurrentDate);
	String tCurrentDate = StrTool.getVisaDay(CurrentDate);
%>
<script type="text/javascript">
	function initInpBox() {
		try {
			fm.RIContNo.value = "";
			fm.RIContName.value = "";
			fm.ContType.value = "";
			fm.ContTypeName.value = "";
			fm.ReCountType.value = "";
			fm.ReCountTypeName.value = "";

			fm.RValidate.value = "";
			fm.RInvalidate.value = "";
			fm.RSignDate.value = "";
			fm.ModRIContNo.value = "";
			fm.ContState.value = "02";
			//IE11-大小写
			fm.ContStateName.value = "未生效";
			fm.UseType.value="";
			fm.UseTypeName.value="";
		} catch (ex) {
			myAlert("进行初始化是出现错误！");
		}
	};

	// 下拉列表的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("在LRContInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			initSignerGrid();
			initFactorGrid();
		} catch (re) {
			myAlert("ReContManageInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}

	//再保合同签订人列表初始化
	function initSignerGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 10;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "公司名称";
			iArray[1][1] = "130px";
			iArray[1][2] = 100;
			iArray[1][3] = 2;
			iArray[1][4] = "ricompanycode";
			iArray[1][5] = "1|2"; //将引用代码分别放在第1、2
			iArray[1][6] = "1|0";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "公司代码";
			iArray[2][1] = "100px";
			iArray[2][2] = 100;
			iArray[2][3] = 1;

			iArray[3] = new Array();
			iArray[3][0] = "合同签订人"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 20; //列最大值
			iArray[3][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[4] = new Array();
			iArray[4][0] = "职务"; //列名
			iArray[4][1] = "80px"; //列宽
			iArray[4][2] = 50; //列最大值
			iArray[4][3] = 1; //2表示是代码选择

			iArray[5] = new Array();
			iArray[5][0] = "联系电话"; //列名
			iArray[5][1] = "90px"; //列宽
			iArray[5][2] = 30; //列最大值
			iArray[5][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[6] = new Array();
			iArray[6][0] = "手机"; //列名
			iArray[6][1] = "90px"; //列宽
			iArray[6][2] = 30; //列最大值
			iArray[6][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[7] = new Array();
			iArray[7][0] = "传真"; //列名
			iArray[7][1] = "90px"; //列宽
			iArray[7][2] = 30; //列最大值
			iArray[7][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[8] = new Array();
			iArray[8][0] = "电子邮箱"; //列名
			iArray[8][1] = "180px"; //列宽
			iArray[8][2] = 30; //列最大值
			iArray[8][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[9] = new Array();
			iArray[9][0] = "联系人编码";
			iArray[9][1] = "70px";
			iArray[9][2] = 100;
			iArray[9][3] = 3;

			SignerGrid = new MulLineEnter("fm", "SignerGrid");
			SignerGrid.mulLineCount = 0;
			SignerGrid.displayTitle = 1;
			//  SignerGrid.locked = 0;
			SignerGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）
			//  SignerGrid.hiddenPlus=0; 
			//  SignerGrid.hiddenSubtraction=1;
			SignerGrid.loadMulLine(iArray);
			SignerGrid.detailInfo = "单击显示详细信息";
		} catch (ex) {
			myAlert("初始化时出错:" + ex);
		}
	}

	//初始化合同级分保信息 
	function initFactorGrid() {
		var contno = fm.RIContNo.value;
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽 
			iArray[0][2] = 100; //列最大值 
			iArray[0][3] = 0; //是否允许输入,1表示允许，0表示不允许

			iArray[1] = new Array();
			iArray[1][0] = "要素名称";
			iArray[1][1] = "38px";
			iArray[1][2] = 200;
			iArray[1][3] = 2;
			iArray[1][4] = "rifactor";
			iArray[1][5] = "1|2"; //将引用代码分别放在第1、2 
			iArray[1][6] = "1|0";
			iArray[1][15] = "" + contno;
			iArray[1][17] = "2";
			iArray[1][19] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "要素代码";
			iArray[2][1] = "25px";
			iArray[2][2] = 200;
			iArray[2][3] = 3;

			iArray[3] = new Array();
			iArray[3][0] = "要素值";
			iArray[3][1] = "15px";
			iArray[3][2] = 200;
			iArray[3][3] = 1;

			FactorGrid = new MulLineEnter("fm", "FactorGrid");
			//这些属性必须在loadMulLine前                                                          
			FactorGrid.mulLineCount = 0;
			FactorGrid.displayTitle = 1;
			FactorGrid.locked = 0;
			FactorGrid.canSel = 0; // 1 显示 ；0 隐藏（缺省值）                                     
			//FactorGrid.selBoxEventFuncName ="CardInfoGridClick"; //响应的函数名，不加扩号        
			FactorGrid.hiddenPlus = 0;
			FactorGrid.hiddenSubtraction = 0;
			FactorGrid.loadMulLine(iArray);

		} catch (ex) {
			myAlert(ex);
		}
	}
</script>
