<%@include file="../i18n/language.jsp"%>
<%
	//程序名称：bomFunInit.jsp
	//程序功能：对BOM和词条的初始化
	//创建日期：2008-8-13
	//创建人  ：
	//更新记录：
%>
<SCRIPT src="../common/javascript/Common.js" ></SCRIPT>
<%
	//添加页面控件的初始化。
%>

<script type="text/javascript">


	function initForm() {
		try {
			if(jEditFlag!=null&&jEditFlag=='1') 
			{
				document.getElementById('buttonForm').style.display="";
			}
			initQueryGrpGrid();
			queryClick();
	  fm.all("MsgLan").value=""; 
	  fm.all("MsgDetail").value="";
	  fm.all("MsgLanName").value="";
		 showOneCodeName('language','MsgLan','MsgLanName');;
	   showOneCodeName('multlantype','MsgType','MsgTypeName');;
			
		} catch (re) {
			myAlert("bomFunInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
		}
	}


	function initQueryGrpGrid() {

		var iArray = new Array();

		try {
			iArray[0] = new Array();
			iArray[0][0] = "序号"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[0][1] = "30px"; //列宽
			iArray[0][2] = 10; //列最大值
			iArray[0][3] = 0;

			iArray[1] = new Array();
			iArray[1][0] = "<%=bundle.getString("handword_language")%>"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[1][1] = "40px"; //列宽
			iArray[1][2] = 10; //列最大值
			iArray[1][3] = 0; //是否允许输入,1表示允许，0表示不允许
			//iArray[1][4] = "language";
			
			iArray[2] = new Array();
			iArray[2][0] = "类型"; //列名（此列为顺序号，列名无意义，而且不显示）
			iArray[2][1] = "40px"; //列宽
			iArray[2][2] = 10; //列最大值
			iArray[2][3] = 0; //是否允许输入,1表示允许，0表示不允许
			//iArray[2][4] = "msgtype";
			

			iArray[3] = new Array();
			iArray[3][0] = "信息"; //列名
			iArray[3][1] = "200px"; //列宽
			iArray[3][2] = 100; //列最大值
			iArray[3][3] = 0; //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

			QueryGrpGrid = new MulLineEnter("fm", "QueryGrpGrid");

			//这些属性必须在loadMulLine前
			QueryGrpGrid.mulLineCount = 3;
			QueryGrpGrid.displayTitle = 1;
			QueryGrpGrid.canChk = 0;
			QueryGrpGrid.canSel = 1;
			QueryGrpGrid.locked = 1; //是否锁定：1为锁定 0为不锁定
			QueryGrpGrid.hiddenPlus = 1; //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
			QueryGrpGrid.hiddenSubtraction = 1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
			QueryGrpGrid.recordNo = 0; //设置序号起始基数为10，如果要分页显示数据有用
			QueryGrpGrid.selBoxEventFuncName ="itemQuery";

			QueryGrpGrid.loadMulLine(iArray);
		} catch (ex) {
			myAlert(ex);
		}
	}

</script>