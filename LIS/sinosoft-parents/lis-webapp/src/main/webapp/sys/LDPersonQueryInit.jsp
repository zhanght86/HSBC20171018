<%
//程序名称：LDPersonQueryInit.jsp
//程序功能：客户管理（新增客户查询）
//创建日期：2005-06-20
//创建人  ：wangyan
//更新记录：更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
	try
	{
		// 保单查询条件
		document.all('CustomerNo').value = '';
		document.all('Name').value = '';
		document.all('Sex').value = '';
		document.all('Birthday').value = '';
		document.all('IDType').value = '';
		document.all('IDNo').value = '';
	}
	catch(ex)
	{
		alert("在LDPersonQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initInpBox();
		initPersonGrid();
	}
	catch(re)
	{
		alert("LDPersonQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

// 保单信息列表的初始化
function initPersonGrid()
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
		iArray[1][0]="客户号码";
		iArray[1][1]="160px";
		iArray[1][2]=100;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="姓名";
		iArray[2][1]="100px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="性别";
		iArray[3][1]="60px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="出生日期";
		iArray[4][1]="100px";
		iArray[4][2]=200;
		iArray[4][3]=0;              		
		
		iArray[5]=new Array();
		iArray[5][0]="证件类型";         	
		iArray[5][1]="60px";            	
		iArray[5][2]=200;            		
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="证件号码";
		iArray[6][1]="140px";
		iArray[6][2]=200;
		iArray[6][3]=0;

		PersonGrid = new MulLineEnter( "fm" , "PersonGrid" );
		//这些属性必须在loadMulLine前
		PersonGrid.mulLineCount = 10;
		PersonGrid.displayTitle = 1;
		PersonGrid.locked = 1;
		PersonGrid.canSel = 1;
		PersonGrid.hiddenPlus=1;
		PersonGrid.hiddenSubtraction=1;
		PersonGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
