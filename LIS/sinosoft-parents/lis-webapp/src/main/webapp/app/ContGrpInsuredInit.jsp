<%
//程序名称：ContGrpInsuredInit.jsp
//程序功能：
//创建日期：2004-11-26 11:10:36
//创建人  ： yuanaq
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() { 
}                                     

function initForm() {
  try {
  	initInpBox();
  	mSwitch = parent.VD.gVSwitch;
  	try{document.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};     
        try{document.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){sdfsdfsdf};  
  	initPersonInsuredGrid();
  	if(LoadFlag=="4"||LoadFlag=="16"){
        document.getElementById("divSaveInsuredButton").style.display="none"; 
        document.getElementById("divSaveButton").style.display="";
  	}
  }
  catch(re) {
    alert("ContGrpInsuredInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 领取项信息列表的初始化
var PersonInsuredGrid;
function initPersonInsuredGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="被保人客户号";         		//列名
    iArray[1][1]="80px";            		//列宽
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许           

    iArray[2]=new Array();
    iArray[2][0]="姓名";      //列名
    iArray[2][1]="80px";            		//列宽
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="性别";                //列名
    iArray[3][1]="40px";            	//列宽
    iArray[3][3]=2;              	//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="Sex"; 

    iArray[4]=new Array();
    iArray[4][0]="出生日期";         	//列名
    iArray[4][1]="60px";            	//列宽
    iArray[4][3]=0;              	//是否允许输入,1表示允许，0表示不允许
 
    
    iArray[5]=new Array();
    iArray[5][0]="证件类型";         	//列名
    iArray[5][1]="40px";            	//列宽
    iArray[5][3]=2;              	//是否允许输入,1表示允许，0表示不允许
    iArray[5][4]="IDType";  
        
    iArray[6]=new Array();
    iArray[6][0]="证件号码";         	//列名
    iArray[6][1]="80px";            	//列宽
    iArray[6][3]=0;              	//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="个人合同号码";         	//列名
    iArray[7][1]="40px";            	//列宽
    iArray[7][3]=3;              	//是否允许输入,1表示允许，0表示不允许
        

    iArray[8]=new Array();
    iArray[8][0]="保费（元）";         	//列名
    iArray[8][1]="80px";            	//列宽
    iArray[8][3]=0;              	//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="保障级别";         	//列名
    iArray[9][1]="50px";            	//列宽
    iArray[9][3]=0;              	//是否允许输入,1表示允许，0表示不允许  

   
     
    PersonInsuredGrid = new MulLineEnter( "fm" , "PersonInsuredGrid" ); 
    //这些属性必须在loadMulLine前
    PersonInsuredGrid.mulLineCount = 0;   
    PersonInsuredGrid.displayTitle = 1;
    PersonInsuredGrid.hiddenPlus = 1;
    PersonInsuredGrid.hiddenSubtraction = 1;
    PersonInsuredGrid.canSel = 1;
    PersonInsuredGrid.selBoxEventFuncName ="getdetail";
    PersonInsuredGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

</script>
