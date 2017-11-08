<%
//程序名称：BankDataQuery.jsp
//程序功能：财务报盘数据查询
//创建日期：2004-10-20
//创建人  ：wentao
//更新记录：  更新人    更新日期     更新原因/内容
%>                         
<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initCodeGrid();
  }
  catch(re)
  {
    alert("BankDataQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{ 
  try
  {
		document.all('ManageCom').value = <%=tG1.ComCode%>;
//		document.all('PolNo').value = '';
//		document.all('StartDate').value = '';	
//		document.all('EndDate').value = '';
  }
  catch(ex)
  {
    alert("BankDataQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}    

function initCodeGrid()    							//函数名为init+MulLine的对象名ObjGrid
{                           						
	var iArray = new Array(); 						//数组放置各个列
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="序号";         			//列名（序号列，第1列）
    	iArray[0][1]="50px";         			//列宽
    	iArray[0][2]=10;	         				//列最大值
    	iArray[0][3]=0;         					//1表示允许该列输入，0表示只读且不响应Tab键
    																		//2表示为容许输入且颜色加深

	    iArray[1]=new Array();
	    iArray[1][0]="保单号";    				//列名
	    iArray[1][1]="120px";  						//列宽
	    iArray[1][2]=30;      						//列最大值
	    iArray[1][3]=0;   								//1表示允许该列输入，0表示只读且不响应Tab键
	                   										//2表示为容许输入且颜色加深.

	    iArray[2]=new Array();          	
	    iArray[2][0]="险种编码";  				//列名（第2列）
	    iArray[2][1]="60px";  						//列宽
	    iArray[2][2]=10;        					//列最大值
	    iArray[2][3]=2;       						//是否允许输入,1表示允许，0表示不允许
			iArray[2][4]="RiskCode"; 
      iArray[2][9]="险种编码|code:RiskCode&NOTNULL";
	
	    iArray[3]=new Array();          	
	    iArray[3][0]="投保人";  				//列名（第2列）
	    iArray[3][1]="60px";  						//列宽
	    iArray[3][2]=10;        					//列最大值
	    iArray[3][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置

	    iArray[4]=new Array();          	
	    iArray[4][0]="被保险人";  				//列名（第2列）
	    iArray[4][1]="60px";  						//列宽
	    iArray[4][2]=10;        					//列最大值
	    iArray[4][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置

	    iArray[5]=new Array();          	
	    iArray[5][0]="应缴日期";  				//列名（第2列）
	    iArray[5][1]="80px";  						//列宽
	    iArray[5][2]=10;        					//列最大值
	    iArray[5][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置

	    iArray[6]=new Array();          	
	    iArray[6][0]="已发盘次数";  				//列名（第2列）
	    iArray[6][1]="60px";  						//列宽
	    iArray[6][2]=10;        					//列最大值
	    iArray[6][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置

	    iArray[7]=new Array();          	
	    iArray[7][0]="缴费通知书号";
	    iArray[7][1]="0px";
	    iArray[7][2]=20; 
	    iArray[7][3]=0; 


	    //生成对象区，规则：对象名=new MulLineEnter(“表单名”,”对象名”); 
	    CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	    //设置属性区 (需要其它特性，在此设置其它属性)
	    CodeGrid.mulLineCount = 5 ; 		//行属性：设置行数=10    
	    CodeGrid.displayTitle = 1;   		//标题属性：1显示标题(缺省值), 0隐藏标题        
	    //对象初始化区：调用对象初始化方法，属性必须在此前设置
	    CodeGrid.hiddenSubtraction=1;
	    CodeGrid.hiddenPlus=1;
	    CodeGrid.canSel=1;
	    CodeGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{ alert(ex); }
}

</script>
