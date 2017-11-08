<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() {
    fm.GrpContNo.value = "<%=request.getParameter("GrpContNo")%>"; 
    fm.BatchNo.value = "<%=request.getParameter("BatchNo")%>"; 
    fm.ImportFlag.value = "<%=request.getParameter("ImportFlag")%>"; 
    fm.Remark.value = "<%=request.getParameter("ErrorInfo")%>"; 
}                                     

function initForm() {
  try {
  	initInpBox(); 
  	if (fm.ImportFlag.value == null || fm.ImportFlag.value == "" 
  	     || fm.ImportFlag.value == "null")
  	{
  	    initImportErrorGrid();
  	}
  	else
  	{
  	    initCarImportErrorGrid();
  	} 	
  	easyQuery();
  }
  catch(re) {
    alert("ContGrpInsuredInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initImportErrorGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="被保人序号";         		//列名
    iArray[1][1]="40px";            		//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许           

    iArray[2]=new Array();
    iArray[2][0]="被保人姓名";      //列名
    iArray[2][1]="40px";            		//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="错误信息";                //列名
    iArray[3][1]="280px";            	//列宽
    iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许  
     
    ImportErrorGrid = new MulLineEnter( "fm" , "ImportErrorGrid" ); 
    //这些属性必须在loadMulLine前
    ImportErrorGrid.mulLineCount = 0;   
    ImportErrorGrid.displayTitle = 1;
    ImportErrorGrid.hiddenPlus = 1;
    ImportErrorGrid.hiddenSubtraction = 1;
    ImportErrorGrid.canSel = 0;
    ImportErrorGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

function initCarImportErrorGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="车辆序号";         		//列名
    iArray[1][1]="40px";            		//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许           

    iArray[2]=new Array();
    iArray[2][0]="车牌号";      //列名
    iArray[2][1]="40px";            		//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="错误信息";                //列名
    iArray[3][1]="280px";            	//列宽
    iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许  
     
    ImportErrorGrid = new MulLineEnter( "fm" , "ImportErrorGrid" ); 
    //这些属性必须在loadMulLine前
    ImportErrorGrid.mulLineCount = 0;   
    ImportErrorGrid.displayTitle = 1;
    ImportErrorGrid.hiddenPlus = 1;
    ImportErrorGrid.hiddenSubtraction = 1;
    ImportErrorGrid.canSel = 0;
    ImportErrorGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
