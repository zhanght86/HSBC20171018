<%@include file="/i18n/language.jsp"%>
<%
//Creator :张斌
//Date :2006-10-24
%>
<!--用户校验类-->

<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/javascript/CCodeOperate.js"></SCRIPT>
<%
 GlobalInput tG = new GlobalInput();
 tG=(GlobalInput)session.getAttribute("GI");
 String CurrentDate	= PubFun.getCurrentDate();   
 String CurrentTime	= PubFun.getCurrentTime();
 String Operator   	= tG.Operator;
%>

<script type="text/javascript">
function initInpBox()
{
  try
  { 
		fm.StartDate.value='';
		fm.EndDate.value='';
		fm.ContNo.value='';
		fm.ContName.value='';
		fm.RIcomCode.value='';
		fm.RIcomName.value='';
  }
  catch(ex)
  {
    myAlert("初始化界面错误!");
  }
}

// 下拉框的初始化
function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    myAlert("2在CertifyDescInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
	try
	{
		initInpBox();
		initSelBox();
		initBatchListGrid();
		initBillDetailGrid();
		initAuditBillListGrid();
		queryAuditList(); //待审核账单
		initBillUpdateGrid() // 账单修改
	  
	}
	catch(re)
	{
		myAlert("3CertifyDescInit.jsp-->"+"初始化界面错误!");
	}
}

//批次列表的初始化
function initBatchListGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="账单编号"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="账单周期";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=0;     

		iArray[3]=new Array();
		iArray[3][0]="账单期次";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=0;  
				
		iArray[4]=new Array();
		iArray[4][0]="分保合同编号";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 
		
		iArray[5]=new Array();
		iArray[5][0]="分保公司编号";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="起始日期";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=0;   
		
		iArray[7]=new Array();
		iArray[7][0]="终止日期";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=0;   
		
		iArray[8]=new Array();
		iArray[8][0]="账单状态";
		iArray[8][1]="50px";  
		iArray[8][2]=100; 
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="账单状态代码";
		iArray[9][1]="25px";  
		iArray[9][2]=100; 
		iArray[9][3]=3;
			
	  	BatchListGrid = new MulLineEnter( "fm" , "BatchListGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	BatchListGrid.mulLineCount = 0;   
	  	BatchListGrid.displayTitle = 1;
	  	BatchListGrid.locked = 0;
	  	BatchListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	  	BatchListGrid.canChk = 0; //是否增加多选框,1为显示CheckBox列，0为不显示 (缺省值)
	  	BatchListGrid.selBoxEventFuncName ="queryDetial"; //响应的函数名，不加扩号   
	  	BatchListGrid.hiddenPlus=1; 
	  	BatchListGrid.hiddenSubtraction=1;
	  	BatchListGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

//账单明细信息 
function initBillDetailGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="账单编号"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 

		iArray[2]=new Array();
		iArray[2][0]="科目编码";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=3;   
										
		iArray[3]=new Array();
		iArray[3][0]="Details（明细）";
		iArray[3][1]="100px";  
		iArray[3][2]=100; 
		iArray[3][3]=0;     
		
		iArray[4]=new Array();
		iArray[4][0]="Debit（借）";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="Credit（贷）";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
				
		iArray[6]=new Array();
		iArray[6][0]="机构编码";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=3; 

		iArray[7]=new Array();
		iArray[7][0]="账单日期";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=3;   

		iArray[8]=new Array();
		iArray[8][0]="操作人员";
		iArray[8][1]="40px";  
		iArray[8][2]=100; 
		iArray[8][3]=3;  
					
	  	BillDetailGrid = new MulLineEnter( "fm" , "BillDetailGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	BillDetailGrid.mulLineCount = 0;   
	  	BillDetailGrid.displayTitle = 1;
	  	BillDetailGrid.locked = 0;
	  	//TaskListGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号   
	  	BillDetailGrid.hiddenPlus=1; 
	  	BillDetailGrid.hiddenSubtraction=1;
	  	BillDetailGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

//账单明细修改
function initBillUpdateGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     

		iArray[1]=new Array();
		iArray[1][0]="明细编码";
		iArray[1][1]="30px";  
		iArray[1][2]=30; 
		iArray[1][3]=2;   
    	iArray[1][4]="ridebcre"; 
		iArray[1][5]="1|2"; 	 			//将引用代码分别放在第1、2
		iArray[1][6]="0|1";	
		iArray[1][19]=1; 
												
		iArray[2]=new Array();
		iArray[2][0]="Details（明细）";
		iArray[2][1]="60px";  
		iArray[2][2]=60; 
		iArray[2][3]=1;     
		
		iArray[3]=new Array();
		iArray[3][0]="Debit（借）";
		iArray[3][1]="30px";  
		iArray[3][2]=30; 
		iArray[3][3]=1; 

		iArray[4]=new Array();
		iArray[4][0]="Credit（贷）";
		iArray[4][1]="30px";  
		iArray[4][2]=30; 
		iArray[4][3]=1;  

		BillUpdateGrid = new MulLineEnter( "fm" , "BillUpdateGrid" );
		BillUpdateGrid.mulLineCount = 1;
		BillUpdateGrid.displayTitle = 1;
		//RelateGrid.canSel=1;
		BillUpdateGrid.loadMulLine(iArray);
		BillUpdateGrid.detailInfo="单击显示详细信息";
    	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}

//待审核列表
function initAuditBillListGrid()
{
    var iArray = new Array();   
    try
    {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=40;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许   			     
		
		iArray[1]=new Array();
		iArray[1][0]="账单编号"; 
		iArray[1][1]="40px"; 
		iArray[1][2]=100; 
		iArray[1][3]=0; 
		
		iArray[2]=new Array();
		iArray[2][0]="账单周期";
		iArray[2][1]="40px";  
		iArray[2][2]=100; 
		iArray[2][3]=0;     

		iArray[3]=new Array();
		iArray[3][0]="账单期次";
		iArray[3][1]="40px";  
		iArray[3][2]=100; 
		iArray[3][3]=0;  
				
		iArray[4]=new Array();
		iArray[4][0]="分保合同编号";
		iArray[4][1]="40px";  
		iArray[4][2]=100; 
		iArray[4][3]=0; 

		iArray[5]=new Array();
		iArray[5][0]="分保公司编号";
		iArray[5][1]="40px";  
		iArray[5][2]=100; 
		iArray[5][3]=0; 
		
		iArray[6]=new Array();
		iArray[6][0]="起始日期";
		iArray[6][1]="40px";  
		iArray[6][2]=100; 
		iArray[6][3]=0;   
		
		iArray[7]=new Array();
		iArray[7][0]="终止日期";
		iArray[7][1]="40px";  
		iArray[7][2]=100; 
		iArray[7][3]=0;   
		
		iArray[8]=new Array();
		iArray[8][0]="账单状态";
		iArray[8][1]="50px";  
		iArray[8][2]=100; 
		iArray[8][3]=0;
		
		iArray[9]=new Array();
		iArray[9][0]="账单状态代码";
		iArray[9][1]="25px";  
		iArray[9][2]=100; 
		iArray[9][3]=3;
			
	  	AuditBillListGrid = new MulLineEnter( "fm" , "AuditBillListGrid" ); 
	  	//这些属性必须在loadMulLine前
	  	AuditBillListGrid.mulLineCount = 0;   
	  	AuditBillListGrid.displayTitle = 1;
	  	AuditBillListGrid.locked = 0;
	  	AuditBillListGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
	  	AuditBillListGrid.canChk = 0; //是否增加多选框,1为显示CheckBox列，0为不显示 (缺省值)
	  	//TaskListGrid.selBoxEventFuncName =""; //响应的函数名，不加扩号   
	  	AuditBillListGrid.hiddenPlus=1; 
	  	AuditBillListGrid.hiddenSubtraction=1;
	  	AuditBillListGrid.loadMulLine(iArray); 
	  	
    }
    catch(ex)
    {
        myAlert(ex);
    }
}
</script>