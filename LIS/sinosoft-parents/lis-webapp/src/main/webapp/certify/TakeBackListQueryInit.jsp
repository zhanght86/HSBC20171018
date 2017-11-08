<%//程序名称：TakeBackListQueryInit.jsp
//程序功能：查询保单回执清单和打印
//创建日期：2005-3-31
//创建人  ：weikai
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
    alert("TakeBackListQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initCodeGrid()    							//函数名为init+MulLine的对象名ObjGrid
{                           						
	var iArray = new Array(); 						//数组放置各个列
	try
	{
    	iArray[0]=new Array();
    	iArray[0][0]="序号";         			//列名
    	iArray[0][1]="40px";         			//列宽
    	iArray[0][2]=30;           				//列最大值
    	iArray[0][3]=0;         					//1表示允许该列输入，0表示只读且不响应Tab键
    																		//2表示为容许输入且颜色加深

	    iArray[1]=new Array();
	    iArray[1][0]="保单号";  			//列名（序号列，第1列）
	    iArray[1][1]="80px";  						//列宽
	    iArray[1][2]=10;      						//列最大值
	    iArray[1][3]=0;   								//1表示允许该列输入，0表示只读且不响应Tab键
	                   										//2表示为容许输入且颜色加深.
	                                    	
	    iArray[2]=new Array();          	
	    iArray[2][0]="管理机构";				//列名（第2列）
	    iArray[2][1]="60px";  						//列宽
	    iArray[2][2]=20;        					//列最大值
	    iArray[2][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置
	    iArray[3]=new Array();          	
	    iArray[3][0]="代理机构";					//列名（第2列）
	    iArray[3][1]="60px";  						//列宽
	    iArray[3][2]=10;        					//列最大值
	    iArray[3][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置
	
	    iArray[4]=new Array();          	
	    iArray[4][0]="业务员代码";		//列名（第2列）
	    iArray[4][1]="60px";  						//列宽
	    iArray[4][2]=10;        					//列最大值
	    iArray[4][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置
		iArray[5]=new Array();          	
	    iArray[5][0]="业务员姓名";		//列名（第2列）
	    iArray[5][1]="50px";  						//列宽
	    iArray[5][2]=10;        					//列最大值
	    iArray[5][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置		
		
	    iArray[6]=new Array();          	
	    iArray[6][0]="投保人姓名";				  //列名（第2列）
	    iArray[6][1]="50px";  						//列宽
	    iArray[6][2]=10;        					//列最大值
	    iArray[6][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置
	       																
	    iArray[7]=new Array();          	
	    iArray[7][0]="初审日期";				  //列名（第2列）
	    iArray[7][1]="60px";  						//列宽
	    iArray[7][2]=10;        					//列最大值
	    iArray[7][3]=0;       						//是否允许输入,1表示允许，0表示不允许
	       																//后续可以添加N列，如上设置
	
	    iArray[8]=new Array();          	
	    iArray[8][0]="最后一次问题件回复时间";				  //列名（第2列）
	    iArray[8][1]="60px";  						//列宽
	    iArray[8][2]=10;        					//列最大值
	    iArray[8][3]=0;       						//是否允许输入,1表示允许，0表示不允许


	    iArray[9]=new Array();          	
		iArray[9][0]="保单签发时间";				  //列名（第2列）
		iArray[9][1]="60px";  						//列宽
	    iArray[9][2]=10;        					//列最大值
	    iArray[9][3]=0;       						//是否允许输入,1表示允许，0表示不允许


	    iArray[10]=new Array();          	
	    iArray[10][0]="签收日期";				  //列名（第2列）
	    iArray[10][1]="60px";  						//列宽
	    iArray[10][2]=10;        					//列最大值
	    iArray[10][3]=0;       						//是否允许输入,1表示允许，0表示不允许

	    iArray[11]=new Array();          	
	    iArray[11][0]="回收时间";				  //列名（第2列）
		iArray[11][1]="60px";  						//列宽
		iArray[11][2]=10;        					//列最大值
		iArray[11][3]=0;       						//是否允许输入,1表示允许，0表示不允许		

		iArray[12]=new Array();          	
		iArray[12][0]="操作员号";				  //列名（第2列）
		iArray[12][1]="40px";  						//列宽
		iArray[12][2]=10;        					//列最大值
		iArray[12][3]=0;       						//是否允许输入,1表示允许，0表示不允许	

		iArray[13]=new Array();          	
		iArray[13][0]="延迟原因";				  //列名（第2列）
		iArray[13][1]="60px";  						//列宽
		iArray[13][2]=10;        					//列最大值
		iArray[13][3]=0;       						//是否允许输入,1表示允许，0表示不允许			       																
	
	    //生成对象区，规则：对象名=new MulLineEnter(“表单名”,”对象名”); 
	    CodeGrid= new MulLineEnter( "fm" , "CodeGrid" ); 
	    //设置属性区 (需要其它特性，在此设置其它属性)
	    CodeGrid.mulLineCount = 5 ; 		//行属性：设置行数=10    
	    CodeGrid.displayTitle = 1;   		//标题属性：1显示标题(缺省值), 0隐藏标题        
	    //对象初始化区：调用对象初始化方法，属性必须在此前设置
	    CodeGrid.hiddenSubtraction=1;
	    CodeGrid.hiddenPlus=1;
	    //CodeGrid.canChk=1;
	    CodeGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{ alert(ex); }
}

function initInpBox()
{ 
  try
  {
		document.all('ManageCom').value = '';
//		document.all('SaleChnl').value = '';
//		document.all('TakeBackDateB').value = '';
//		document.all('TakeBackDateE').value = '';
//		document.all('Operator').value = '';
  }
  catch(ex)
  {
    alert("TakeBackListQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}    
    
</script>
