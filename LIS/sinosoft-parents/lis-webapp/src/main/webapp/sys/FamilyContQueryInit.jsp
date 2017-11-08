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
  }
  catch(ex)
  {
    alert("在FamilyContQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在FamilyContQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
		initFamilyCardInfo();
  }
  catch(re)
  {
    alert("FamilyContQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
		iArray[1][0]="印刷号";         		  //列名
		iArray[1][1]="120px";            		//列宽
		iArray[1][2]=100;            			  //列最大值
		iArray[1][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[2]=new Array();
		iArray[2][0]="合同号";          		//列名
		iArray[2][1]="120px";            		//列宽
		iArray[2][2]=140;            			  //列最大值
		iArray[2][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		
		iArray[3]=new Array();
		iArray[3][0]="投保人客户号";         		    //列名
		iArray[3][1]="80px";            		//列宽
		iArray[3][2]=70;            			  //列最大值
		iArray[3][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
	  	iArray[4]=new Array();
		iArray[4][0]="投保人姓名";          		  //列名
		iArray[4][1]="80px";            		//列宽
		iArray[4][2]=70;            			  //列最大值
		iArray[4][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[5]=new Array();
		iArray[5][0]="被保人客户号"; //列名
		iArray[5][1]="80px";            		//列宽
		iArray[5][2]=70;            			  //列最大值
		iArray[5][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		
		iArray[6]=new Array();
		iArray[6][0]="被保人姓名";            //列名
		iArray[6][1]="80px";            		//列宽
		iArray[6][2]=70;            		 	  //列最大值
		iArray[6][3]=0;              			  //是否允许输入,1表示允许，0表示不允许
		

		FamilyCardInfoGrid = new MulLineEnter("fm" , "FamilyCardInfoGrid"); 
		// 这些属性必须在loadMulLine前
		FamilyCardInfoGrid.displayTitle = 5;
	  FamilyCardInfoGrid.hiddenSubtraction=1;
	  FamilyCardInfoGrid.hiddenPlus=1;
		FamilyCardInfoGrid.locked = 1;
		FamilyCardInfoGrid.canSel = 1;
		FamilyCardInfoGrid.loadMulLine(iArray);  
		//这些操作必须在loadMulLine后面
	}
	catch(ex)
	{
		alert(ex);
	}
}




</script>
