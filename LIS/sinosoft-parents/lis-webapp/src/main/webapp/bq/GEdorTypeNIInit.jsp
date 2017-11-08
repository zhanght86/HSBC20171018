<%
//程序名称：GEdorTypeNIInput.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

function initGrpEdor()
{
	document.all('EdorNo').value       = top.opener.document.all('EdorNo').value;
	document.all('GrpContNo').value  	 = top.opener.document.all('ContNoApp').value;
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.all('EdorType').value 		 = top.opener.document.all('EdorType').value;
	document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;	
	document.all('EdorTypeCal').value  = top.opener.document.all('EdorTypeCal').value;
	document.all('ContNo').value 			 = "";
}

// 输入框的初始化（单记录部分）
function initInpBox() { 
  try 
  {} 
  catch(ex) 
  {}      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {}
  catch(ex)
  { 
  	 alert("在GEdorTypeNIInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

//新增被保人列表
function initLPInsuredGrid()
{
    var iArray = new Array();
    try
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          				//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="被保人代码";        //列名
      iArray[1][1]="80px";            	//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="被保人姓名";        //列名
      iArray[2][1]="80px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="险种代码";         	//列名
      iArray[3][1]="80px";            	//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="新增合同保单号";    //列名
      iArray[4][1]="100px";            	//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="保单类型";         	//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      LPInsuredGrid = new MulLineEnter( "fm" , "LPInsuredGrid" ); 
      
      //这些属性必须在loadMulLine前
      LPInsuredGrid.mulLineCount = 1;   
      LPInsuredGrid.displayTitle = 1;
	  	LPInsuredGrid.canSel = 1;
      LPInsuredGrid.hiddenPlus = 1;
      LPInsuredGrid.hiddenSubtraction = 1;
      LPInsuredGrid.selBoxEventFuncName = "onInsuredGridSelected";
      LPInsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面

    }catch(ex){
    	alert(ex);
    }
}








//================
function initForm() 
{
	initGrpEdor();
	initLPInsuredGrid();
	initQuery();
	QueryEdorInfo();
	//QueryEdorMoney();		
}

</script>
