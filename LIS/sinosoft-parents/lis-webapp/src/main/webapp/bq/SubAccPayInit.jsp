<%
//PEdorTypeIAInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">  
//单击时查询

function initInpBox()
{ 

  try
  {       	
    //document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    //document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value; 
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;   
    //document.all('ContNo').value = top.opener.document.all('ContNoBak').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;    
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    //alert(document.all('EdorType').value);
    document.all('PEdorState').value = top.opener.document.all('PEdorState').value;
    //alert(document.all('PEdorState').value);
    document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value; 
    //document.all('InsuredNo').value = top.opener.document.all('CustomerNoBak').value;
    document.all('GrpContNo').value = top.opener.document.all('GrpContNo').value;   
    if(document.all('GrpContNo').value == null || document.all('GrpContNo').value == "")
    {
    	document.all('GrpContNo').value = top.opener.document.all('ContNoApp').value;
    }
  }
  catch(ex)
  {
    alert("在PEdorTypeIAInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                                        

function initForm()
{
  try
  {

    initInpBox();    
    initMoneyDetailGrid();
    initSubAccGrid();
    queryMoneyDetail();
   
  }
  catch(re)
  {
    alert("PEdorTypeIAInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


function initMoneyDetailGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="个人合同号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="80px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="个人客户号";					//列名1
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="姓名";         			//列名2
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

	 		iArray[4]=new Array();
      iArray[4][0]="退费金额";         			//列名2
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许




 

      MoneyDetailGrid = new MulLineEnter( "fm" , "MoneyDetailGrid" ); 
      //这些属性必须在loadMulLine前        
      MoneyDetailGrid.mulLineCount = 10;   
      MoneyDetailGrid.displayTitle = 1;
      MoneyDetailGrid.canSel=1;
      MoneyDetailGrid.hiddenPlus = 1; 
      MoneyDetailGrid.hiddenSubtraction = 1;
      MoneyDetailGrid.selBoxEventFuncName ="resetSubAccInfo";
      MoneyDetailGrid.loadMulLine(iArray);  
      MoneyDetailGrid.detailInfo="单击显示详细信息";      
      MoneyDetailGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initSubAccGrid()
{
    var iArray = new Array();
    
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;
      
      iArray[1]=new Array();
      iArray[1][0]="个人合同号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="100px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户号";					//列名1
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="客户姓名";         			//列名2
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

	 		iArray[4]=new Array();
      iArray[4][0]="退费金额";         			//列名2
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="退费方式";         			//列名2
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=3; 
      
      iArray[6]=new Array();
      iArray[6][0]="退费方式";         			//列名2
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0; 
      
      iArray[7]=new Array();
      iArray[7][0]="领取人";         			//列名2
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="领取人身份证号";         			//列名2
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                  
      iArray[9]=new Array();
      iArray[9][0]="银行编码";         			//列名2
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="银行名称";         			//列名2
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="银行账户";         			//列名2
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="户名";         			//列名2
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许


             

 

      SubAccGrid = new MulLineEnter( "fm" , "SubAccGrid" ); 
      //这些属性必须在loadMulLine前        
      //SubAccGrid.mulLineCount = 10;   
      SubAccGrid.displayTitle = 1;
      SubAccGrid.canSel=1;
      SubAccGrid.hiddenPlus = 1; 
      SubAccGrid.hiddenSubtraction = 1;
      SubAccGrid.selBoxEventFuncName ="ShowSubAccInfo";
      SubAccGrid.loadMulLine(iArray);  
      SubAccGrid.detailInfo="单击显示详细信息";      
      SubAccGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
        alert(ex);
      }
}

function returnParent(){
top.close();
}







</script>