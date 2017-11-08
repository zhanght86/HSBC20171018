<%
/*******************************************************************************
* <p>Title: 保全-团单磁盘导入</p>
* <p>Description: 团单磁盘导入js文件</p>
* <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
* <p>Company: 中科软科技股份有限公司</p>
* <p>WebSite: http://www.sinosoft.com.cn</p>
*
* @todo     : 保全-团单磁盘导入
* @author   : zhangtao
* @version  : 1.00
* @date     : 2006-11-24
* @modify   : 2006-11-25
******************************************************************************/
%> 
<!--用户校验类-->
<script language="JavaScript">
function initInpBox()
{
	try
	{
		fm.GrpContNo.value="<%=request.getParameter("grpcontno")%>";
 
		document.all('FileName').value = '';
		document.all('EdorType').value = top.opener.document.all('EdorType').value;
		document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
		document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
		document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
		
	}
	catch(ex)
	{
		alert("GEdorDiskImportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initDataGrid(); //生成mileline容器
	}
	catch(re)
	{
		alert("GEdorDiskImportInit.jsp-->InitForm函数出现异常:初始化界面错误!");
	}
}

/********************************************
 *初始化mileLine控件
 *******************************************/
function initDataGrid()
{
	var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

	try
	{
		iArray[0] = new Array();
		iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
		iArray[0][1] = "30px";                          //列宽
		iArray[0][2] = 30;                              //列最大值
		iArray[0][3] = 0;                               //是否允许编辑: 0表示不允许; 1表示允许。

		iArray[1] = new Array();
		iArray[1][0] = "团体保单号";
		iArray[1][1] = "90px";
		iArray[1][2] = 100;
		iArray[1][3] = 0;

		iArray[2] = new Array();
		iArray[2][0] = "保全受理号";
		iArray[2][1] = "100px";
		iArray[2][2] = 100;
		iArray[2][3] = 0;
		
		iArray[3] = new Array();
		iArray[3][0] = "批改类型";
		iArray[3][1] = "60px";
		iArray[3][2] = 60;
		iArray[3][3] = 2;
    iArray[3][4] = "gedortype";
    iArray[3][18] = 236;
    
		iArray[4] = new Array();
		iArray[4][0] = "导入批次号";
		iArray[4][1] = "80px";
		iArray[4][2] = 50;
		iArray[4][3] = 0;

		iArray[5] = new Array();
		iArray[5][0] = "业务序号";
		iArray[5][1] = "60px";
		iArray[5][2] = 80;
		iArray[5][3] = 0;
		
		iArray[6] = new Array();
		iArray[6][0] = "错误信息";
		iArray[6][1] = "200px";
		iArray[6][2] = 200;
		iArray[6][3] = 0;
		
		iArray[7] = new Array();
		iArray[7][0] = "操作员";
		iArray[7][1] = "70px";
		iArray[7][2] = 100;
		iArray[7][3] = 0;
		
		iArray[8] = new Array();
		iArray[8][0] = "导入日期";
		iArray[8][1] = "70px";
		iArray[8][2] = 100;
		iArray[8][3] = 0;
		
		iArray[9] = new Array();
		iArray[9][0] = "导入时间";
		iArray[9][1] = "70px";
		iArray[9][2] = 150;
		iArray[9][3] = 0;


	}
	catch (ex)
	{
		alert("在 GEdorDiskImportInit.jsp --> initDataGrid() 函数中发生异常: 初始化数组错误！");
	}

	try
	{
		DataGrid = new MulLineEnter("fm", "DataGrid");
		DataGrid.mulLineCount = 5;
		DataGrid.displayTitle = 1;
		DataGrid.locked = 1;
		DataGrid.hiddenPlus = 1;
		DataGrid.hiddenSubtraction = 1;
		DataGrid.canChk = 0;
		DataGrid.canSel = 0;
		DataGrid.chkBoxEventFuncName = "";
		DataGrid.selBoxEventFuncName = "";		//值为在radio被选中时触发事件的函数名
		//上面属性必须在 MulLineEnter loadMulLine 之前
		DataGrid.loadMulLine(iArray);
	}
	catch (ex)
	{
		alert("在 GEdorDiskImportInit.jsp --> initDataGrid() 函数中发生异常: 初始化界面错误！");
	}
}
</script>
