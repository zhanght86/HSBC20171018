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
      iArray[1][0]="补/退费通知书号码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="80px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="补/退费财务类型";					//列名1
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="个人合同号码";         			//列名2
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	

	 iArray[4]=new Array();
      iArray[4][0]="姓名";         			//列名2
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="补/退费日期";         		//列名8
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=30;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="险种编码";     //列名6
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=50;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="补/退费金额(元)";         		//列名8
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=30;            			//列最大值
      iArray[7][3]=0;

      iArray[8]=new Array();
      iArray[8][0]="币种";         		
      iArray[8][1]="40px";            		 
      iArray[8][2]=60;            			
      iArray[8][3]=2;              		
      iArray[8][4]="Currency";              	  
      iArray[8][9]="币种|code:Currency";

      MoneyDetailGrid = new MulLineEnter( "fm" , "MoneyDetailGrid" ); 
      //这些属性必须在loadMulLine前        
      MoneyDetailGrid.mulLineCount = 10;   
      MoneyDetailGrid.displayTitle = 1;
      MoneyDetailGrid.canSel=0;
      MoneyDetailGrid.hiddenPlus = 1; 
      MoneyDetailGrid.hiddenSubtraction = 1;
      MoneyDetailGrid.selBoxEventFuncName ="";
      MoneyDetailGrid.loadMulLine(iArray);  
      MoneyDetailGrid.detailInfo="单击显示详细信息";      
      MoneyDetailGrid.loadMulLine(iArray);        
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
