<%
//程序名称：LDUWUserQueryInit.jsp
//程序功能：功能描述
//创建日期：2005-01-24 18:15:01
//创建人  ：ctrHTML
//更新人  ：  
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%@page import="com.sinosoft.lis.pubfun.*"%>                  
<script language="JavaScript">
function initInpBox() { 
  try {     
    document.all('UserCode').value = "";
   // document.all('UWType').value = "";
    // document.all('UpUserCode').value = "";
     document.all('UpUWPopedom').value = "";
    //document.all('OtherUserCode').value = "";
    //document.all('OtherUpUWPopedom').value = "";
    //document.all('UWBranchCode').value = "";
    document.all('UWPopedom').value = "";
    document.all('AddPoint').value = "";
  
    //document.all('UserState').value = "";
    document.all('edpopedom').value = "";
    //document.all('ValidStartDate').value = "";
    //document.all('ValidEndDate').value = "";
     
    document.all('ClaimPopedom').value = "";
    
    //document.all('UpUserCode').value = "";
   
    //document.all('Remark').value = "";
    document.all('Operator').value = "";
    document.all('ManageCom').value = "";
    document.all('MakeDate').value = "";
    
    document.all('MakeTime').value = "";
    document.all('ModifyDate').value = "";
    document.all('ModifyTime').value = "";
  }
  catch(ex) {
    alert("在LDUWUserQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                    
function initForm() {
  try {
    initInpBox();
    initLDUWUserGrid();  
  }
  catch(re) {
    alert("LDUWUserQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
//领取项信息列表的初始化
var LDUWUserGrid;
function initLDUWUserGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         		//列名
    iArray[0][1]="30px";         		//列名
    iArray[0][3]=0;         		//列名
    iArray[0][4]="station";         		//列名
    
    iArray[1]=new Array();
	iArray[1][0]="核保师编码";  
	iArray[1][1]="80px";  
	iArray[1][3]=0;
	
	iArray[2]=new Array();
	iArray[2][0]="核保师类别";  
	iArray[2][1]="80px";  
	iArray[2][3]=0;       

	iArray[3]=new Array();
	iArray[3][0]="核保权限";  
	iArray[3][1]="80px";  
	iArray[3][3]=0;       
  
    
    LDUWUserGrid = new MulLineEnter( "document" , "LDUWUserGrid" ); 
    //这些属性必须在loadMulLine前

    LDUWUserGrid.mulLineCount = 5;   
    LDUWUserGrid.displayTitle = 1;
    LDUWUserGrid.hiddenPlus = 1;
    LDUWUserGrid.hiddenSubtraction = 1;
    LDUWUserGrid.canSel = 1;
    LDUWUserGrid.canChk = 0;
    //LDUWUserGrid.selBoxEventFuncName = "showOne";

	LDUWUserGrid.loadMulLine(iArray);  
    //这些操作必须在loadMulLine后面
    //LDUWUserGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
