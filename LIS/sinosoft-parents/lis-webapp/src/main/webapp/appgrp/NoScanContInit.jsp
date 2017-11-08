<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ScanContInit.jsp
//程序功能：个单新契约扫描件保单录入
//创建日期：2004-12-22 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
	try
	{
		// 保单查询条件
		document.all('PrtNo').value = '';
	}
	catch(ex)
	{
		alert("在GroupUWAutoInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	}
}

function initForm()
{
	try
	{
		initTr();
		initInpBox();
		initGrpGrid();
	}
	catch(re)
	{
		alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	}
}

// 保单信息列表的初始化
function initGrpGrid()
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
		iArray[1][0]="投保单号";
		iArray[1][1]="120px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="询价编号";
		iArray[2][1]="0px";
		iArray[2][2]=100;
		iArray[2][3]=0;
		
		
		iArray[3]=new Array();
		iArray[3][0]="询价版本号";
		iArray[3][1]="0px";
		iArray[3][2]=50;
		iArray[3][3]=0;
		

		iArray[4]=new Array();
		iArray[4][0]="管理机构";
		iArray[4][1]="120px";
		iArray[4][2]=100;
		iArray[4][3]=0;

		iArray[5]=new Array();
		iArray[5][0]="申请日期";
		iArray[5][1]="80px";
		iArray[5][2]=100;
		iArray[5][3]=0;

		iArray[6]=new Array();
		iArray[6][0]="申请人";         	
		iArray[6][1]="100px";           
		iArray[6][2]=100;            	
		iArray[6][3]=0;              	
		
		iArray[7]=new Array();
		iArray[7][0]="工作流任务号";  
		iArray[7][1]="0px";
		iArray[7][2]=200;
		iArray[7][3]=3;

		iArray[8]=new Array();
		iArray[8][0]="工作流子任务号";
		iArray[8][1]="0px";
		iArray[8][2]=200;
		iArray[8][3]=3;

      iArray[9]=new Array();
      iArray[9][0]="工作流活动Id";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许 


		GrpGrid = new MulLineEnter( "fm" , "GrpGrid" );
		//这些属性必须在loadMulLine前
		GrpGrid.mulLineCount = 10;
		GrpGrid.displayTitle = 1;
		GrpGrid.locked = 1;
		GrpGrid.canSel = 1;
		GrpGrid.canChk = 0;
		GrpGrid.hiddenSubtraction = 1;
		GrpGrid.hiddenPlus = 1;
		GrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
