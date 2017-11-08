<%
//程序名称：CCBBatchQueryInit.jsp
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="../common/easyQueryVer3/EasyQueryVer3.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
var filePath;
function initInpBox() {
  //var strSql = "select SysVarValue from LDSysVar where SysVar = 'DisplayBankFilePath'";
  
  //filePath = easyExecSql(strSql);
  //document.all('Url').value = filePath;
  
  document.all('fmtransact').value = "";
} 

function initForm() {
  try {
  	//getFilePath();
  	
  	initInpBox();
  	
  	initBankGrid();
  	
    initBankFileDealDiv();
    
  	//easyQueryClick();
  } catch(re) {
    alert("InitForm 函数中发生异常:初始化界面错误!");
  }
}

// 领取项信息列表的初始化
var BankGrid;
function initBankGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="批次号";         		//列名
    iArray[1][1]="200px";            	//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="文件名";         		//列名
    iArray[2][1]="200px";            	//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    BankGrid = new MulLineEnter( "fm" , "BankGrid" ); 
    //这些属性必须在loadMulLine前
    BankGrid.mulLineCount = 5;   
    BankGrid.displayTitle = 1;
    BankGrid.hiddenPlus = 1;
    BankGrid.hiddenSubtraction = 1;
    BankGrid.canSel = 1;
    BankGrid.canChk = 0;
    BankGrid.selBoxEventFuncName = "showStatistics";
    BankGrid.loadMulLine(iArray);  
    
    //这些操作必须在loadMulLine后面
    //BankGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}

</script>

	
