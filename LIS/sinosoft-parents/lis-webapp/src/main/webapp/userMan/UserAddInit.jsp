<%
//程序名称：OLDUserInput.jsp
//程序功能：
//创建日期：2002-08-16 17:44:42
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.lis.pubfun.PubFun"%>
<%
//添加页面控件的初始化。
GlobalInput tG1 = new GlobalInput();
tG1=(GlobalInput)session.getValue("GI");
%>
<script src="./UserAdd.js"></script>
<script language="JavaScript">
function initInpBox()
{
	try
	{
		document.all('UserCode').value = '';
		document.all('UserName').value = '';
		document.all('ComCode').value = '';
		document.all('Password').value = '';
		document.all('PasswordConfirm').value = '';
		document.all('UserDescription').value = '';
		document.all('UserState').value = '';
		document.all('UWPopedom').value = '';
		document.all('ClaimPopedom').value = '';
		document.all('OtherPopedom').value = '';
		document.all('EdorPopedom').value = '';
		document.all('PopUWFlag').value = '';
		document.all('SuperPopedomFlag').value = '';
		document.all('Operator').value = '<%=tG1.Operator%>';
		document.all('ValidStartDate').value = '<%=PubFun.getCurrentDate()%>';
		document.all('ValidEndDate').value = '';
	}
	catch(ex)
	{
		alert("在UserInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initSelectMenuGrpGrid();
		initUnselectMenuGrpGrid();
		//初始化hideMenuGrpGrid必须在unselectMenuGrpGrid后
		initHideMenuGrpGrid1();
		initUserGrid();
	}
	catch(re)
	{
		alert("OLDUserInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

function initUserGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";	//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";	//列宽
		iArray[0][2]=10;		//列最大值
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="用户姓名";
		iArray[1][1]="100px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="用户编码";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="用户状态";
		iArray[3][1]="60px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="用户描述";
		iArray[4][1]="200px";
		iArray[4][2]=100;
		iArray[4][3]=0;
		
		
		iArray[5]=new Array();
		iArray[5][0]="机构编码";
		iArray[5][1]="100px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="代理机构";
		iArray[6][1]="100px";
		iArray[6][2]=100;
		iArray[6][3]=0;
		
		

		UserGrid = new MulLineEnter( "fm" , "UserGrid" );
		//这些属性必须在loadMulLine前
		UserGrid.mulLineCount = 5;
		UserGrid.displayTitle = 1;
		UserGrid.canChk =0;
		UserGrid.canSel =1;
		UserGrid.locked =1;				//是否锁定：1为锁定 0为不锁定
		UserGrid.hiddenPlus=1;			//是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
		UserGrid.hiddenSubtraction=1;	//是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
		UserGrid.recordNo=0;			//设置序号起始基数为10，如果要分页显示数据有用
		UserGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function  initHideMenuGrpGrid1()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="菜单组名称";
		iArray[1][1]="140px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="菜单组描述";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		HideMenuGrpGrid1 = new MulLineEnter( "fm" , "HideMenuGrpGrid1" );
		HideMenuGrpGrid1.mulLineCount = 5;
		HideMenuGrpGrid1.displayTitle = 1;
		HideMenuGrpGrid1.canChk =1;
		HideMenuGrpGrid1.canSel =0;
		HideMenuGrpGrid1.locked =1;
		HideMenuGrpGrid1.hiddenPlus=1;
		HideMenuGrpGrid1.hiddenSubtraction=1;
		HideMenuGrpGrid1.recordNo=0;
		HideMenuGrpGrid1.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function  initSelectMenuGrpGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="菜单组名称";
		iArray[1][1]="100px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="菜单组描述";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="具体菜单";
		iArray[3][1]="70px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		iArray[3][7]="showSelectGridMenus";

		SelectMenuGrpGrid = new MulLineEnter( "fm" , "SelectMenuGrpGrid" );
		SelectMenuGrpGrid.mulLineCount = 5;
		SelectMenuGrpGrid.displayTitle = 1;
		SelectMenuGrpGrid.canChk =1;
		SelectMenuGrpGrid.canSel =0;
		SelectMenuGrpGrid.locked =1;
		SelectMenuGrpGrid.hiddenPlus=1;
		SelectMenuGrpGrid.hiddenSubtraction=1;
		SelectMenuGrpGrid.recordNo=0;
		SelectMenuGrpGrid.mulLineNum = 2;
		SelectMenuGrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}

function  initUnselectMenuGrpGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="菜单组名称";
		iArray[1][1]="60px";
		iArray[1][2]=10;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="菜单组描述";
		iArray[2][1]="60px";
		iArray[2][2]=100;
		iArray[2][3]=0;


		iArray[3]=new Array();
		iArray[3][0]="具体菜单";
		iArray[3][1]="50px";
		iArray[3][2]=100;
		iArray[3][3]=0;
		iArray[3][7]="showUnselectGridMenus";


		UnselectMenuGrpGrid = new MulLineEnter( "fm" , "UnselectMenuGrpGrid" );
		UnselectMenuGrpGrid.mulLineCount = 5;
		UnselectMenuGrpGrid.displayTitle = 1;
		UnselectMenuGrpGrid.canChk =1;
		UnselectMenuGrpGrid.canSel =0;
		UnselectMenuGrpGrid.locked =1;
		UnselectMenuGrpGrid.hiddenPlus=1;
		UnselectMenuGrpGrid.hiddenSubtraction=1;
		UnselectMenuGrpGrid.recordNo=0;
		UnselectMenuGrpGrid.mulLineNum = 2;
		UnselectMenuGrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
