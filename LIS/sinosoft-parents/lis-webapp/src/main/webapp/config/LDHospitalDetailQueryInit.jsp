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
   // initInpBox();
    initLDHospitalGrid();  
  }
  catch(re) {
    alert("LDUWUserQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
function initLDHospitalGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         		//列名
    iArray[0][1]="30px";         		//列名
    iArray[0][3]=0;         		//列名
    iArray[0][4]="station";         		//列名
    
    iArray[1]=new Array();
		iArray[1][0]="体检医院编码";  
		iArray[1][1]="80px";  
		iArray[1][3]=0;
	
		iArray[2]=new Array();
		iArray[2][0]="体检医院名称";  
		iArray[2][1]="80px";  
		iArray[2][3]=0;       

		iArray[3]=new Array();
		iArray[3][0]="体检医院级别";  
		iArray[3][1]="80px";  
		iArray[3][3]=0;       
 
  	iArray[4]=new Array();
		iArray[4][0]="机构代码";  
		iArray[4][1]="80px";  
		iArray[4][3]=0;       

  	iArray[5]=new Array();
		iArray[5][0]="协议书签署日期";  
		iArray[5][1]="80px";  
		iArray[5][3]=0;       
     
  	iArray[6]=new Array();
		iArray[6][0]="详细地址";  
		iArray[6][1]="80px";  
		iArray[6][3]=0;       

 
       

    
    LDHospitalGrid = new MulLineEnter( "document" , "LDHospitalGrid" ); 
    //这些属性必须在loadMulLine前

    LDHospitalGrid.mulLineCount = 5;   
    LDHospitalGrid.displayTitle = 1;
    LDHospitalGrid.hiddenPlus = 1;
    LDHospitalGrid.hiddenSubtraction = 1;
    LDHospitalGrid.canSel = 1;
    LDHospitalGrid.canChk = 0;
    //LDUWUserGrid.selBoxEventFuncName = "showOne";

		LDHospitalGrid.loadMulLine(iArray);  
    //这些操作必须在loadMulLine后面
    //LDUWUserGrid.setRowColData(1,1,"asdf");
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
