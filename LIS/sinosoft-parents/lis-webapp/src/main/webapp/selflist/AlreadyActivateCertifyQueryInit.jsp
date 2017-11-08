<%@page import="com.sinosoft.lis.pubfun.*"%>

<%
//程序名称：AlreadyActivateCertifyQuery.jsp
//程序功能：已激活卡单查询
//创建日期：2008-03-17
//创建人  ：zz
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	String strCurDate = PubFun.getCurrentDate();
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {
  	fm.MakeDateB.value = '<%= strCurDate %>';
  	fm.MakeDateE.value = '<%= strCurDate %>';
  }
  catch(ex)
  {
    alert("在AlreadyActivateCertifyInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在AlreadyActivateCertifyInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    	initInpBox();
    	initSelBox();
		initCardInfo();
		initFamilyCardInfo();
		initContInfo();
  }
  catch(re)
  {
    alert("AlreadyActivateCertifyInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="40px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="卡号";         		  //列名
		iArray[1][1]="130px";            		//列宽
		iArray[1][2]=100;            			  //列最大值
		iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="险种名称";          		//列名
		iArray[2][1]="140px";            		//列宽
		iArray[2][2]=140;            			  //列最大值
		iArray[2][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		
		iArray[3]=new Array();
		iArray[3][0]="投保人客户号";         		    //列名
		iArray[3][1]="70px";            		//列宽
		iArray[3][2]=70;            			  //列最大值
		iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	  	iArray[4]=new Array();
		iArray[4][0]="投保人姓名";          		  //列名
		iArray[4][1]="70px";            		//列宽
		iArray[4][2]=70;            			  //列最大值
		iArray[4][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="被保人客户号";         		    //列名
		iArray[5][1]="70px";            		//列宽
		iArray[5][2]=70;            			  //列最大值
		iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	  	iArray[6]=new Array();
		iArray[6][0]="被保人姓名";          		  //列名
		iArray[6][1]="70px";            		//列宽
		iArray[6][2]=70;            			  //列最大值
		iArray[6][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[7]=new Array();
		iArray[7][0]="承保日期"; //列名
		iArray[7][1]="70px";            		//列宽
		iArray[7][2]=70;            			  //列最大值
		iArray[7][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[8]=new Array();
		iArray[8][0]="激活日期";            //列名
		iArray[8][1]="70px";            		//列宽
		iArray[8][2]=70;            		 	  //列最大值
		iArray[8][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[9]=new Array();
		iArray[9][0]="生效日期";         		//列名
		iArray[9][1]="70px";            		//列宽
		iArray[9][2]=70;            			  //列最大值
		iArray[9][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[10]=new Array();
		iArray[10][0]="责任终止日期";            //列名
		iArray[10][1]="70px";            		//列宽
		iArray[10][2]=70;            			  //列最大值
		iArray[10][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[11]=new Array();
		iArray[11][0]="激活类型";            //列名
		iArray[11][1]="60px";            		//列宽
		iArray[11][2]=60;            			  //列最大值
		iArray[11][3]=0;              			  //是否允许输入,1表示允许，0表示不允许

		CardInfo = new MulLineEnter("fm" , "CardInfo"); 
		// 这些属性必须在loadMulLine前
		CardInfo.mulLineCount = 5;   
		CardInfo.displayTitle = 1;
	  	CardInfo.hiddenSubtraction=1;
	  	CardInfo.hiddenPlus=1;
		CardInfo.locked = 1;
		//CardInfo.canSel = 1;
		CardInfo.loadMulLine(iArray);  
		//这些操作必须在loadMulLine后面
		//CardInfo.setRowColData(1,1,"asdf");
	}
	catch(ex)
	{
		alert(ex);
	}
}


// 保单信息列表的初始化
function initFamilyCardInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="40px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="卡号";         		  //列名
		iArray[1][1]="130px";            		//列宽
		iArray[1][2]=100;            			  //列最大值
		iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="险种名称";          		//列名
		iArray[2][1]="140px";            		//列宽
		iArray[2][2]=140;            			  //列最大值
		iArray[2][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		
		iArray[3]=new Array();
		iArray[3][0]="投保人客户号";         		    //列名
		iArray[3][1]="70px";            		//列宽
		iArray[3][2]=70;            			  //列最大值
		iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	  	iArray[4]=new Array();
		iArray[4][0]="投保人姓名";          		  //列名
		iArray[4][1]="70px";            		//列宽
		iArray[4][2]=70;            			  //列最大值
		iArray[4][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="承保日期"; //列名
		iArray[5][1]="70px";            		//列宽
		iArray[5][2]=70;            			  //列最大值
		iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[6]=new Array();
		iArray[6][0]="激活日期";            //列名
		iArray[6][1]="70px";            		//列宽
		iArray[6][2]=70;            		 	  //列最大值
		iArray[6][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[7]=new Array();
		iArray[7][0]="生效日期";         		//列名
		iArray[7][1]="70px";            		//列宽
		iArray[7][2]=70;            			  //列最大值
		iArray[7][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[8]=new Array();
		iArray[8][0]="责任终止日期";            //列名
		iArray[8][1]="70px";            		//列宽
		iArray[8][2]=70;            			  //列最大值
		iArray[8][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[9]=new Array();
		iArray[9][0]="激活类型";            //列名
		iArray[9][1]="60px";            		//列宽
		iArray[9][2]=60;            			  //列最大值
		iArray[9][3]=0;              			  //是否允许输入,1表示允许，0表示不允许



		FamilyCardInfo = new MulLineEnter("fm" , "FamilyCardInfo"); 
		// 这些属性必须在loadMulLine前
		FamilyCardInfo.mulLineCount = 5;
		FamilyCardInfo.displayTitle = 1;
	    FamilyCardInfo.hiddenSubtraction=1;
	    FamilyCardInfo.hiddenPlus=1;
		FamilyCardInfo.locked = 1;
		FamilyCardInfo.selBoxEventFuncName = "getFamilyCont";
		FamilyCardInfo.canSel = 1;
		FamilyCardInfo.loadMulLine(iArray);  
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}



// 保单信息列表的初始化
function initContInfo()
{                               
	var iArray = new Array();
      
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			  //列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="40px";            		//列宽
		iArray[0][2]=10;            			  //列最大值
		iArray[0][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="卡号";         		  //列名
		iArray[1][1]="130px";            		//列宽
		iArray[1][2]=100;            			  //列最大值
		iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="合同号";          		//列名
		iArray[2][1]="140px";            		//列宽
		iArray[2][2]=140;            			  //列最大值
		iArray[2][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		
		iArray[3]=new Array();
		iArray[3][0]="险种名称";         		    //列名
		iArray[3][1]="70px";            		//列宽
		iArray[3][2]=70;            			  //列最大值
		iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	  	iArray[4]=new Array();
		iArray[4][0]="被保人客户号";          		  //列名
		iArray[4][1]="70px";            		//列宽
		iArray[4][2]=70;            			  //列最大值
		iArray[4][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="被保人姓名"; //列名
		iArray[5][1]="70px";            		//列宽
		iArray[5][2]=70;            			  //列最大值
		iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		



		ContInfoGrid = new MulLineEnter("fm" , "ContInfoGrid"); 
		// 这些属性必须在loadMulLine前
		ContInfoGrid.mulLineCount = 5;
		ContInfoGrid.displayTitle = 1;
	    ContInfoGrid.hiddenSubtraction=1;
	    ContInfoGrid.hiddenPlus=1;
		ContInfoGrid.locked = 1;
		ContInfoGrid.selBoxEventFuncName = "getFamilyCont";
//		ContInfoGrid.canSel = 1;
		ContInfoGrid.loadMulLine(iArray);  
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}	
</script>
