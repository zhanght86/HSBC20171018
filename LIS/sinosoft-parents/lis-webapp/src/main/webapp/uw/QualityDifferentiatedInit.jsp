<%
//差异化核保  品质差异化维护
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">
//返回按钮初始化
var str = "";
function initDisplayButton() {
	if (tDisplay=="1") {
		fm.Return.style.display='';
	} else if (tDisplay=="0") {
		fm.Return.style.display='none';
	}
}
// 输入框的初始化（单记录部分）
function initInpBox() { 

  try {                                   
	// 保单查询条件
    document.all('AgentCode').value = '';
    //document.all('AgentGroup').value = '';
    document.all('AgentName').value = '';
    document.all('UWClass').value = '';
    document.all('UWClassName').value = '';
    document.all('UWLevel').value = '';
    document.all('UWLevelName').value = '';
  } catch(ex) {
    alert("在QualityDifferentiatedInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox() {  
  try {

  }
  catch(ex) {
    alert("在PrtReplaceInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm() {
	try {
		initInpBox();
    	initSelBox();    
		initPolGrid();
  	} catch(re) {
    	alert("PrtReplaceInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  	}
}

// 保单信息列表的初始化
function initPolGrid() {                               
	var iArray = new Array();
      
	try {
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=10;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="管理机构";         		//列名
		iArray[1][1]="80px";            		//列宽
		iArray[1][2]=100;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			      
		iArray[2]=new Array();
		iArray[2][0]="机构类别";         		//列名
		iArray[2][1]="80px";            		//列宽
		iArray[2][2]=100;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			      
		iArray[3]=new Array();
		iArray[3][0]="业务员代码";         		//列名
		iArray[3][1]="100px";            		//列宽
		iArray[3][2]=200;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="业务员类别";         		//列名
		iArray[4][1]="80px";            		//列宽
		iArray[4][2]=200;            			//列最大值
		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	    iArray[5]=new Array();
	    iArray[5][0]="业务员姓名";         		//列名
	    iArray[5][1]="70px";            		//列宽
	    iArray[5][2]=100;            			//列最大值
	    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	    iArray[6]=new Array();
	    iArray[6][0]="差异化级别";         		//列名
	    iArray[6][1]="80px";            		//列宽
	    iArray[6][2]=100;            			//列最大值
	    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	    iArray[7]=new Array();
	    iArray[7][0]="维护时间";         		//列名
	    iArray[7][1]="150px";            		//列宽
	    iArray[7][2]=100;            			//列最大值
	    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	    PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
	    //这些属性必须在loadMulLine前
	    PolGrid.mulLineCount = 5;   
	    PolGrid.displayTitle = 1;
	    PolGrid.locked = 1;
	    PolGrid.canSel = 0;
	    PolGrid.hiddenPlus = 1;
	    PolGrid.hiddenSubtraction = 1;
	    //PolGrid.selBoxEventFuncName="ShowPrt";
	    PolGrid.loadMulLine(iArray);  
	      
	    //这些操作必须在loadMulLine后面
	    //PolGrid.setRowColData(1,1,"asdf");
	} catch(ex) {
    	alert(ex);
    }
}

</script>
