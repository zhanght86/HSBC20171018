<%
/*******************************************************************************
* <p>Title: 综合查询-集体保全查询</p>
* <p>Description: 集体保全查询-init初始化页面</p>
* <p>Copyright: Copyright (c) 2006 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: 中科软科技股份有限公司</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : 保全-VIP客户查询
* @author   : liuxiaosong
* @version  : 1.00
* @date     : 2006-10-16
* 更新记录：  更新人    更新日期     更新原因/内容
******************************************************************************/
%>

<%@ page import="com.sinosoft.lis.pubfun.*" %>

<%
	//获得当前登陆的管理机构
	String sYesterday = PubFun.calDate(PubFun.getCurrentDate(), -1, "D", null);
	GlobalInput tGlobalInput = new GlobalInput();
	tGlobalInput = (GlobalInput)session.getValue("GI");
	String scurManageCom = tGlobalInput.ManageCom;
	tGlobalInput = null;
%>

<script language="JavaScript">

/**
 *初始化页面
 */
function initForm()
{
	try
	{   
		initInpBox();  //初始化页面固有字段
		initPolGrid();  //生成查询结果容器mulLine控件
	}
	catch(ex)
	{
		alert("AllGBqQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
} //end function initForm

/**
 *初始化输入表格以及隐藏域
 */
function initInpBox()
{
	try
	{
		document.all('EdorAcceptNo').value = ''; //保全受理号
		document.all('OtherNo').value = '';  //集体保单/集体客户号
		document.all('OtherNoType').value = ''; //类型 = ？集体保单：集体客户
		document.all('EdorAppName').value = ''; //申请人姓名
		document.all('AppType').value = ''; //申请方式
		document.all('EdorAppDate').value = ''; //申请时间
		document.getElementsByName("LoginManageCom")[0].value = "<%=scurManageCom%>"; //当前管理机构
	}
	catch(ex)
	{
		alert("在AllGBqQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
} // end function initInpBox


/**
 *数据列表容器MilLine生成
 */
function initPolGrid()
{
	var iArray = new Array();

	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";                  //列宽
		iArray[0][2]=10;                      //列最大值
		iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

		iArray[1]=new Array();
		iArray[1][0]="受理号码";
		iArray[1][1]="145px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="批单号码";
		iArray[2][1]="145px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="客户/保单号";
		iArray[3][1]="120px";
		iArray[3][2]=100;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="号码类型";
		iArray[4][1]="80px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="申请人姓名";
		iArray[5][1]="0px";
		iArray[5][2]=100;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="补/退费金额";
		iArray[6][1]="80px";
		iArray[6][2]=100;
		iArray[6][3]=0;

		iArray[7]=new Array();
		iArray[7][0]="受理人";
		iArray[7][1]="0px";
		iArray[7][2]=100;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="确认人";
		iArray[8][1]="0px";
		iArray[8][2]=100;
		iArray[8][3]=3;

		iArray[9]=new Array();
		iArray[9][0]="批改状态";
		iArray[9][1]="80px";
		iArray[9][2]=100;
		iArray[9][3]=0;

		iArray[10]=new Array();
		iArray[10][0]="操作岗位";
		iArray[10][1]="120px";
		iArray[10][2]=100;
		iArray[10][3]=0;

		iArray[11]=new Array();
		iArray[11][0]="操作员";
		iArray[11][1]="80px";
		iArray[11][2]=100;
		iArray[11][3]=0;

		iArray[12]=new Array();
		iArray[12][0]="批改类型";
		iArray[12][1]="80px";
		iArray[12][2]=100;
		iArray[12][3]=0;
		
		iArray[13]=new Array();
		iArray[13][0]="保全生效日期";
		iArray[13][1]="80px";
		iArray[13][2]=100;
		iArray[13][3]=0;

		iArray[14]=new Array();
		iArray[14][0]="入机日期";
		iArray[14][1]="0px";
		iArray[14][2]=100;
		iArray[14][3]=3;

		iArray[15]=new Array();
		iArray[15][0]="入机时间";
		iArray[15][1]="0px";
		iArray[15][2]=100;
		iArray[15][3]=3;

		iArray[16]=new Array();
		iArray[16][0]="受理机构";
		iArray[16][1]="140px";
		iArray[16][2]=100;
		iArray[16][3]=0;

		iArray[17]=new Array();
		iArray[17][0]="EdorNo";
		iArray[17][1]="0px";
		iArray[17][2]=100;
		iArray[17][3]=3;
		
		PolGrid = new MulLineEnter("fm", "PolGrid");
		//这些属性必须在loadMulLine前
		PolGrid.mulLineCount = 10;
		PolGrid.displayTitle = 1;
		PolGrid.hiddenSubtraction=1;
		PolGrid.hiddenPlus = 1;
		PolGrid.locked = 1;
		PolGrid.canSel = 1;
		PolGrid.loadMulLine(iArray);
		//PolGrid. selBoxEventFuncName = "PBqQueryClick";
	}
	catch(ex)
	{
		alert(ex);
	}
} // end function initPolGrid

</script>
