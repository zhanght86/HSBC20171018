<%@include file="/i18n/language.jsp"%>
<html>
<%
	//name :ReComManageInit.jsp
	//function :Manage 
	//Creator :
	//date :2006-08-15
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
			fm.ReComCode.value = "";
			fm.ReComName.value = "";
			fm.PostalCode.value = "";
			fm.Address.value = "";
			fm.FaxNo.value = "";
			fm.ComType.value = "";
			fm.ComTypeName.value = "";
			fm.Info.value = "";
		} catch (ex) {
			myAlert("进行初始化是出现错误！！！！");
		}
	};

	// 下拉框的初始化
	function initSelBox() {
		try {
		} catch (ex) {
			myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
		}
	}

	function initForm() {
		try {
			initInpBox();
			initSelBox();
			initRelateGrid();
		} catch (re) {
			myAlert("3CertifyDescInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}

	function initRelateGrid() {
		var iArray = new Array();
		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号";
			iArray[0][1] = "30px";
			iArray[0][2] = 10;
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "联系人";
			iArray[1][1] = "70px";
			iArray[1][2] = 100;
			iArray[1][3] = 1;

			iArray[2] = new Array();
			iArray[2][0] = "部门"; //列名
			iArray[2][1] = "100px"; //列宽
			iArray[2][2] = 200; //列最大值
			iArray[2][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[3] = new Array();
			iArray[3][0] = "职务"; //列名
			iArray[3][1] = "80px"; //列宽
			iArray[3][2] = 60; //列最大值
			iArray[3][3] = 1; //2表示是代码选择

			iArray[4] = new Array();
			iArray[4][0] = "联系电话"; //列名
			iArray[4][1] = "100px"; //列宽
			iArray[4][2] = 60; //列最大值
			iArray[4][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[5] = new Array();
			iArray[5][0] = "手机"; //列名
			iArray[5][1] = "100px"; //列宽
			iArray[5][2] = 60; //列最大值
			iArray[5][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[6] = new Array();
			iArray[6][0] = "传真"; //列名
			iArray[6][1] = "100px"; //列宽
			iArray[6][2] = 60; //列最大值
			iArray[6][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[7] = new Array();
			iArray[7][0] = "电子邮箱"; //列名
			iArray[7][1] = "200px"; //列宽
			iArray[7][2] = 100; //列最大值
			iArray[7][3] = 1; //是否允许输入,1表示允许，0表示不允许

			iArray[8] = new Array();
			iArray[8][0] = "联系人编码";
			iArray[8][1] = "70px";
			iArray[8][2] = 100;
			iArray[8][3] = 3;

			RelateGrid = new MulLineEnter("fm", "RelateGrid");
			RelateGrid.mulLineCount = 0;
			RelateGrid.displayTitle = 1;
			//RelateGrid.canSel=1;
			RelateGrid.loadMulLine(iArray);
			RelateGrid.detailInfo = "单击显示详细信息";
		} catch (ex) {
			myAlert("初始化时出错:" + ex);
		}
	}
</script>