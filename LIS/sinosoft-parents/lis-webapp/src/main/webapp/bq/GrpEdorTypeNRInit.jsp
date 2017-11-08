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
	document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
	document.all('GrpContNo').value = top.opener.document.all('OtherNo').value;
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	document.all('EdorType').value = top.opener.document.all('EdorType').value;
	document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
	//alert("---------"+document.all('EdorValiDate').value);
	document.all('EdorTypeCal').value = top.opener.document.all('EdorTypeCal').value;
	document.all('ContNo').value = "";
}
// 输入框的初始化（单记录部分）
function initInpBox() { 
  try 
  {                            
  
  } 
  catch(ex) 
  {
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
    iArray[1][0]="个人保单号";    				//列名1
    iArray[1][1]="150px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="客户号";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="姓名";         			//列名8
    iArray[3][1]="80px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="性别";         			//列名5
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="出生日期";         		//列名6
    iArray[5][1]="80px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="证件类型";         		//列名6
    iArray[6][1]="80px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[7]=new Array();
    iArray[7][0]="证件号码";         		//列名6
    iArray[7][1]="150px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      LPInsuredGrid = new MulLineEnter( "fm" , "LPInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      LPInsuredGrid.mulLineCount = 0;   
      LPInsuredGrid.displayTitle = 1;
	    LPInsuredGrid.canSel = 1;
      LPInsuredGrid.hiddenPlus = 1;
      LPInsuredGrid.hiddenSubtraction = 1;
      LPInsuredGrid.selBoxEventFuncName ="onInsuredGridSelected";
      LPInsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initEYGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="团体保单号";         			//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="原挂帐金额";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="本次使用挂帐金额";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		iArray[4]=new Array();
		iArray[4][0]="币种";         		
		iArray[4][1]="50px";            		 
		iArray[4][2]=60;            			
		iArray[4][3]=2;              		
		iArray[4][4]="Currency";              	  
		iArray[4][9]="币种|code:Currency";
      
      EYGrid = new MulLineEnter( "fm" , "EYGrid" ); 
      //这些属性必须在loadMulLine前
      EYGrid.mulLineCount = 1;   
      EYGrid.displayTitle = 1;
	    EYGrid.canSel = 0;
      EYGrid.hiddenPlus = 1;
      EYGrid.hiddenSubtraction = 1;
      EYGrid.selBoxEventFuncName ="";
      EYGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initInsurAccGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="团体保单号";         			//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="公共帐户金额";         			//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="本次保全使用金额";         			//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

		iArray[4]=new Array();
		iArray[4][0]="币种";         		
		iArray[4][1]="50px";            		 
		iArray[4][2]=60;            			
		iArray[4][3]=2;              		
		iArray[4][4]="Currency";              	  
		iArray[4][9]="币种|code:Currency";
      
      InsurAccGrid = new MulLineEnter( "fm" , "InsurAccGrid" ); 
      //这些属性必须在loadMulLine前
      InsurAccGrid.mulLineCount = 1;   
      InsurAccGrid.displayTitle = 1;
	    InsurAccGrid.canSel = 0;
      InsurAccGrid.hiddenPlus = 1;
      InsurAccGrid.hiddenSubtraction = 1;
      InsurAccGrid.selBoxEventFuncName ="";
      InsurAccGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initBlogGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="导入文件名";         			//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=80;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="合同序号";         			//列名
      iArray[2][1]="30px";            		//列宽
      iArray[2][2]=80;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人姓名";         			//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=80;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="错误信息";         			//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=80;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="导入日期";         			//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=80;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="导入时间";         			//列名
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=80;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      BlogGrid = new MulLineEnter( "fm" , "BlogGrid" ); 
      //这些属性必须在loadMulLine前
      BlogGrid.mulLineCount = 0;   
      BlogGrid.displayTitle = 1;
	    BlogGrid.canSel = 0;
      BlogGrid.hiddenPlus = 1;
      BlogGrid.hiddenSubtraction = 1;
      BlogGrid.selBoxEventFuncName ="";
      BlogGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLPPolGrid()
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
    iArray[1][0]="个人保单号";    				//列名1
    iArray[1][1]="100px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="客户号";         			//列名2
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="姓名";         			//列名8
    iArray[3][1]="80px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="险种";         			//列名5
    iArray[4][1]="50px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    iArray[5]=new Array();
    iArray[5][0]="保费";         		//列名6
    iArray[5][1]="60px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
    
    iArray[6]=new Array();
    iArray[6][0]="保额";         		//列名6
    iArray[6][1]="60px";            		//列宽
    iArray[6][2]=100;            			//列最大值
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
    
    iArray[7]=new Array();
    iArray[7][0]="险种保单号";         		//列名6
    iArray[7][1]="100px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许         

	iArray[8]=new Array();
	iArray[8][0]="币种";         		
	iArray[8][1]="50px";            		 
	iArray[8][2]=60;            			
	iArray[8][3]=2;              		
	iArray[8][4]="Currency";              	  
	iArray[8][9]="币种|code:Currency";

      LPPolGrid = new MulLineEnter( "fm" , "LPPolGrid" ); 
      //这些属性必须在loadMulLine前
      LPPolGrid.mulLineCount = 1;   
      LPPolGrid.displayTitle = 1;
	    LPPolGrid.canSel = 0;
      LPPolGrid.hiddenPlus = 1;
      LPPolGrid.hiddenSubtraction = 1;
      LPPolGrid.selBoxEventFuncName ="";
      LPPolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}
function initForm() 
{
	initGrpEdor();
	initLPInsuredGrid();
	initLPPolGrid();
	initQuery();
	QueryEdorInfo();
	//QueryEdorMoney();		
}

</script>
