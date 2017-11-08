 <%
//程序名称：TempFeeInit.jsp
//程序功能：
//创建日期：2009-11-14 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人 张斌    更新日期     更新原因/内容
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
String CurrentDate = PubFun.getCurrentDate();
String CurrentTime = PubFun.getCurrentTime();
GlobalInput tGI = new GlobalInput(); 			//repair:
tGI=(GlobalInput)session.getValue("GI");  //参见loginSubmit.jsp
String ManageCom = tGI.ComCode;
String Operator = tGI.Operator;
loggerDebug("TempFinFeeInit","Operator: "+Operator);
%>  
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  { 
    //Frame1.style.display='';
    //Frame2.style.display=''; 
    document.all('ManageCom').value = '<%=ManageCom%>';
    ////document.all('ManageComName').value = '';
    document.all('PayDate').value = '<%=CurrentDate%>';
    //document.all('AgentGroup').value = '';
    //document.all('AgentCode').value = '0078903448';
    //document.all('TempFeeType').value = '';
    //document.all('TempFeeTypeName').value = '';         
    ////document.all('InputNo').value = '';     
    document.all('Operator').value = '<%=Operator%>';
    //document.all('SumTempFee').value = 0.0 ;    
  }
  catch(ex)
  {
    alert("在TempFeeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在TempFeeInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initQueryLJAGetGrid();
    initFinFeeGrid();  
    
    initTempToGrid(); 
    //更新2010-2-26
    initTTempToGrid();  
    initTTempClassToGrid(); 
    //更新2010-2-26
  }
  catch(re)
  {
    alert("TempFeeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 暂收费信息列表的初始化
function initFinFeeGrid()
{
    var iArray = new Array();      
    try
    {
    	iArray[0]=new Array();
    	iArray[0][0]="序号";         					//列名（此列为顺序号，列名无意义，而且不显示）
    	iArray[0][1]="0px"; 	           			//列宽
    	iArray[0][2]=1;            						//列最大值
    	iArray[0][3]=3;              					//是否允许输入,1表示允许，0表示不允许
			
    	iArray[1]=new Array();
    	iArray[1][0]="交费方式";          		//列名
    	iArray[1][1]="60px";      	      		//列宽
    	iArray[1][2]=20;            					//列最大值
    	iArray[1][3]=0;             					//是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
			
    	iArray[2]=new Array();
    	iArray[2][0]="交费方式名称";         	//列名
    	iArray[2][1]="80px";            			//列宽
    	iArray[2][2]=80;            					//列最大值
    	iArray[2][3]=0;              					//是否允许输入,1表示允许，0表示不允许
			
    	iArray[3]=new Array();
    	iArray[3][0]="交费金额";      	   		//列名
    	iArray[3][1]="60px";            			//列宽
    	iArray[3][2]=20;            					//列最大值
    	iArray[3][3]=0;              					//是否允许输入,1表示允许，0表示不允许
    	iArray[3][9]="交费金额|NUM&NOTNULL";
			
    	iArray[4]=new Array();
    	iArray[4][0]="币种";      	   				//列名
    	iArray[4][1]="0px";            			//列宽
    	iArray[4][2]=40;            					//列最大值
    	iArray[4][3]=3;              					//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[5]=new Array();
    	iArray[5][0]="票据号码";      	   		//列名
    	iArray[5][1]="60px";            			//列宽
    	iArray[5][2]=20;            					//列最大值
    	iArray[5][3]=0;                   		//是否允许输入,1表示允许，0表示不允许
    	      
    	iArray[6]=new Array();
    	iArray[6][0]="票据日期";      	   		//列名
    	iArray[6][1]="60px";            			//列宽
    	iArray[6][2]=20;            					//列最大值
    	iArray[6][3]=0;              					//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[7]=new Array();
    	iArray[7][0]="开户银行";      	   		//列名
    	iArray[7][1]="60px";            			//列宽
    	iArray[7][2]=20;            					//列最大值
    	iArray[7][3]=0;             					//是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
    	
    	iArray[8]=new Array();
    	iArray[8][0]="银行名称";      	   		//列名
    	iArray[8][1]="0px";            				//列宽
    	iArray[8][2]=20;            					//列最大值
    	iArray[8][3]=0;              					//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[9]=new Array();
    	iArray[9][0]="银行账号";      	   		//列名
    	iArray[9][1]="90px";            			//列宽
    	iArray[9][2]=20;            					//列最大值
    	iArray[9][3]=0;              					//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[10]=new Array();
    	iArray[10][0]="户名";      	   				//列名
    	iArray[10][1]="60px";            			//列宽
    	iArray[10][2]=20;            					//列最大值
    	iArray[10][3]=0;              				//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[11]=new Array();
    	iArray[11][0]="收款银行号码";      	  //列名
    	iArray[11][1]="60px";            			//列宽 0
    	iArray[11][2]=40;            					//列最大值
    	iArray[11][3]=0;              				//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[12]=new Array();
    	iArray[12][0]="收款银行账号";      	  //列名
    	iArray[12][1]="100px";            			//列宽
    	iArray[12][2]=20;            					//列最大值
    	iArray[12][3]=0;              				//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[13]=new Array();
    	iArray[13][0]="收款银行户名";      	  //列名
    	iArray[13][1]="0px";            			//列宽 0
    	iArray[13][2]=60;            					//列最大值
    	iArray[13][3]=3;              				//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[14]=new Array();
    	iArray[14][0]="证件类型";      	   		//列名
    	iArray[14][1]="0px";            			//列宽 0
    	iArray[14][2]=60;            					//列最大值
    	iArray[14][3]=3;              				//是否允许输入,1表示允许，0表示不允许 
    	
    	iArray[15]=new Array();
    	iArray[15][0]="证件号码";      	   		//列名
    	iArray[15][1]="0px";            			//列宽 0
    	iArray[15][2]=60;            					//列最大值
    	iArray[15][3]=3;              				//是否允许输入,1表示允许，0表示不允许
    	
    	iArray[16]=new Array();
    	iArray[16][0]="到账日期";      	   		//列名
    	iArray[16][1]="0px";            			//列宽 0
    	iArray[16][2]=60;            					//列最大值
    	iArray[16][3]=3;              				//是否允许输入,1表示允许，0表示不允许
    	iArray[16][9]="到账日期|DATE";
    	      
    	iArray[17]=new Array();
    	iArray[17][0]="应付号码";    					//列名
    	iArray[17][1]="0px";            			//列宽
    	iArray[17][2]=500;            				//列最大值
    	iArray[17][3]=3;              				//隐藏 3
    	
    	iArray[18]=new Array();
    	iArray[18][0]="交费人姓名";      			//列名
    	iArray[18][1]="0px";            			//列宽
    	iArray[18][2]=20;            					//列最大值
    	iArray[18][3]=3;              				//隐藏 3
			
    	iArray[19]=new Array();
    	iArray[19][0]="暂时收据号";    				//列名
    	iArray[19][1]="0px";            			//列宽
    	iArray[19][2]=500;            				//列最大值
    	iArray[19][3]=3;              				//隐藏 3
    	
    	iArray[20]=new Array();
    	iArray[20][0]="币种";      	   				//列名
    	iArray[20][1]="40px";            			//列宽
    	iArray[20][2]=40;            					//列最大值
    	iArray[20][3]=0;  
    	
    	FinFeeGrid = new MulLineEnter( "fm" , "FinFeeGrid" ); 
    	//这些属性必须在loadMulLine前
    	FinFeeGrid.mulLineCount = 1;   
    	FinFeeGrid.displayTitle = 1;
    	FinFeeGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
    	FinFeeGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
    	FinFeeGrid.locked=0;      
    	FinFeeGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert(ex);
    }
}

function initQueryLJAGetGrid()
{                               
   var iArray = new Array();
   
   try
   {
   	iArray[0]=new Array();
   	iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
   	iArray[0][1]="30px";            		//列宽
   	iArray[0][2]=10;            				//列最大值
   	iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
   	
   	iArray[1]=new Array();
   	iArray[1][0]="应付号码";   				//列名
   	iArray[1][1]="160px";            	//列宽
   	iArray[1][2]=100;            			//列最大值
   	iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
   	
   	iArray[2]=new Array();
   	iArray[2][0]="业务号码";						//列名
   	iArray[2][1]="160px";            	//列宽
   	iArray[2][2]=60;            				//列最大值
   	iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
   	
   	iArray[3]=new Array();
   	iArray[3][0]="给付金额";         	//列名
   	iArray[3][1]="80px";            		//列宽
   	iArray[3][2]=200;            	    //列最大值
   	iArray[3][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
   	
   	iArray[4]=new Array();
   	iArray[4][0]="交费人";         		//列名
   	iArray[4][1]="80px";            		//列宽
   	iArray[4][2]=200;            	    //列最大值
   	iArray[4][3]=0;                   	//是否允许输入,1表示允许，0表示不允许  
   	
   	iArray[5]=new Array();
   	iArray[5][0]="交费人身份证";       //列名
   	iArray[5][1]="120px";            	//列宽
   	iArray[5][2]=200;            	    //列最大值
   	iArray[5][3]=0;                   	//是否允许输入,1表示允许，0表示不允许
   	
   	QueryLJAGetGrid= new MulLineEnter( "fm" , "QueryLJAGetGrid" ); 
   	//这些属性必须在loadMulLine前
   	QueryLJAGetGrid.mulLineCount = 1;   
   	QueryLJAGetGrid.displayTitle = 1;
   	QueryLJAGetGrid.hiddenPlus = 1;
   	QueryLJAGetGrid.hiddenSubtraction = 1;
   	QueryLJAGetGrid.canSel = 1;
   	QueryLJAGetGrid.loadMulLine(iArray);
   	QueryLJAGetGrid.selBoxEventFuncName = "GetRecord";  
   }
   catch(ex)
   {
     alert(ex);
   }
}

// 暂收费分类列表的初始化
function initTempToGrid()
{                               
  var iArray = new Array();
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px"; 	           	//列宽
    iArray[0][2]=1;            				//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="通知书号码";      //列名
    iArray[1][1]="80px";      	      //列宽
    iArray[1][2]=20;            			//列最大值
    iArray[1][3]=0;										//是否允许输入,1表示允许，0表示不允许 3
    
    iArray[2]=new Array();
    iArray[2][0]="交费方式";          		//列名
    iArray[2][1]="60px";      	      		//列宽
    iArray[2][2]=20;            			//列最大值
    iArray[2][3]=0;										//是否允许输入,1表示允许，0表示不允许 3
          
    iArray[3]=new Array();
    iArray[3][0]="交费日期";      	   		//列名
    iArray[3][1]="80px";            			//列宽
    iArray[3][2]=10;            			//列最大值
    iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
    iArray[4]=new Array();
    iArray[4][0]="交费金额";      	  //列名
    iArray[4][1]="80px";            	//列宽
    iArray[4][2]=10;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="币种";      	  //列名
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=10;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
    iArray[6]=new Array();
    iArray[6][0]="到账日期";          //列名
    iArray[6][1]="0px";      	      //列宽
    iArray[6][2]=20;            			//列最大值
    iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 3
		
    iArray[7]=new Array();
    iArray[7][0]="管理机构";      	  //列名
    iArray[7][1]="100px";            	//列宽
    iArray[7][2]=20;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 3
		
    iArray[8]=new Array();
    iArray[8][0]="险种编码";          //列名
    iArray[8][1]="0px";      	      	//列宽
    iArray[8][2]=20;            			//列最大值
    iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 3
		
    iArray[9]=new Array();
    iArray[9][0]="代理人组别";        //列名
    iArray[9][1]="100px";      	      //列宽
    iArray[9][2]=20;            			//列最大值
    iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 3
		
    iArray[10]=new Array();
    iArray[10][0]="代理人编码";        //列名
    iArray[10][1]="100px";      	      //列宽
    iArray[10][2]=20;            			//列最大值
    iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
    iArray[11]=new Array();
    iArray[11][0]="业务号码";      	  //列名
    iArray[11][1]="100px";            //列宽
    iArray[11][2]=20;            			//列最大值
    iArray[11][3]=0;              		//隐藏
		
    iArray[12]=new Array();
    iArray[12][0]="业务类型";     //列名
    iArray[12][1]="60px";            //列宽
    iArray[12][2]=20;            			//列最大值
    iArray[12][3]=3;              		//隐藏

    iArray[13]=new Array();
    iArray[13][0]="缴费间隔";         //列名
    iArray[13][1]="0px";      	      //列宽
    iArray[13][2]=20;            			//列最大值
    iArray[13][3]=3;              		//是否允许输入,1表示允许，0表示不允许 3

    iArray[14]=new Array();
    iArray[14][0]="缴费期间";      	  //列名
    iArray[14][1]="0px";            	//列宽
    iArray[14][2]=20;            			//列最大值
    iArray[14][3]=3;              		//隐藏 3

    iArray[15]=new Array();
    iArray[15][0]="缴费单位";      	  //列名
    iArray[15][1]="0px";            	//列宽
    iArray[15][2]=20;            			//列最大值
    iArray[15][3]=3;              		//隐藏 3

    iArray[16]=new Array();
    iArray[16][0]="投保人";      			//列名
    iArray[16][1]="60px";            	//列宽
    iArray[16][2]=20;            			//列最大值
    iArray[16][3]=0;              		//隐藏 3

    iArray[17]=new Array();
    iArray[17][0]="备注";      	   	  //列名
    iArray[17][1]="0px";            	//列宽
    iArray[17][2]=500;            		//列最大值
    iArray[17][3]=3;              		//隐藏 3
    
    iArray[18]=new Array();
    iArray[18][0]="收据类型";      	  //列名
    iArray[18][1]="60px";            	//列宽
    iArray[18][2]=20;            			//列最大值
    iArray[18][3]=0;              		//是否允许输入,1表示允许，0表示不允许
           
    iArray[19]=new Array();
    iArray[19][0]="币种名称";     //列名
    iArray[19][1]="60px";            	//列宽
    iArray[19][2]=20;            			//列最大值
    iArray[19][3]=3;              		//是否允许输入,1表示允许，0表示不允许
    
    iArray[20]=new Array();
    iArray[20][0]="暂收号码";     //列名
    iArray[20][1]="60px";            	//列宽
    iArray[20][2]=20;            			//列最大值
    iArray[20][3]=0;              		//是否允许输入,1表示允许，0表示不允许
                    
    TempToGrid = new MulLineEnter( "fm" , "TempToGrid" ); 
    //这些属性必须在loadMulLine前
    TempToGrid.mulLineCount = 1;   
    TempToGrid.displayTitle = 1;
    TempToGrid.locked=0;
    TempToGrid.canSel = 1; //是否增加单选框,1 显示 ；0 隐藏(缺省值)
    TempToGrid.hiddenPlus=1;
    TempToGrid.hiddenSubtraction=0;
    TempToGrid.selBoxEventFuncName = "showInform"; // mulLine中点击radiobox触发函数, 你写的JS函数名，不加扩号
    TempToGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert(ex);
    }	
}


//更新2010-2-26
//初始化保存暂交费数据的Grid
function initTTempToGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="暂收号码";          		//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;
      
      iArray[2]=new Array();
      iArray[2][0]="收费类型";          		//列名
      iArray[2][1]="80px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;
            
      iArray[3]=new Array();
      iArray[3][0]="交费日期";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="交费金额";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="到账日期";          		//列名
      iArray[5][1]="0px";      	      		//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="管理机构";      	   		//列名
      iArray[6][1]="0px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="险种编码";          		//列名
      iArray[7][1]="60px";      	      		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="代理人组别";          		//列名
      iArray[8][1]="80px";      	      		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="代理人编码";          		//列名
      iArray[9][1]="80px";      	      		//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="业务号码";      	   	        //列名
      iArray[10][1]="100px";            			//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=0;              			//隐藏

      iArray[11]=new Array();
      iArray[11][0]="业务类型";      	   	        //列名
      iArray[11][1]="100px";            			//列宽
      iArray[11][2]=20;            			//列最大值
      iArray[11][3]=0;              			//隐藏

      iArray[12]=new Array();
      iArray[12][0]="缴费间隔";          		//列名
      iArray[12][1]="0px";      	      		//列宽
      iArray[12][2]=20;            			//列最大值
      iArray[12][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="缴费期间";      	   	        //列名
      iArray[13][1]="0px";            			//列宽
      iArray[13][2]=20;            			//列最大值
      iArray[13][3]=3;              			//隐藏

      iArray[14]=new Array();
      iArray[14][0]="缴费单位";      	   	        //列名
      iArray[14][1]="0px";            			//列宽
      iArray[14][2]=20;            			//列最大值
      iArray[14][3]=3;              			//隐藏

      iArray[15]=new Array();
      iArray[15][0]="投保人姓名";      	   	   //列名
      iArray[15][1]="100px";            			//列宽
      iArray[15][2]=20;            			//列最大值
      iArray[15][3]=3;              			//隐藏

      iArray[16]=new Array();
      iArray[16][0]="备注";      	   	        //列名
      iArray[16][1]="100px";            			//列宽
      iArray[16][2]=500;            			//列最大值
      iArray[16][3]=3;              			//隐藏
      
      iArray[17]=new Array();
      iArray[17][0]="交费通知单号";      	   	        //列名
      iArray[17][1]="100px";            			//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=3;              			//隐藏   
      
      iArray[18]=new Array();
      iArray[18][0]="币种";      	   	        //列名
      iArray[18][1]="100px";            			//列宽
      iArray[18][2]=60;            			//列最大值
      iArray[18][3]=3;              			//隐藏          

      TTempToGrid = new MulLineEnter( "fmSave" , "TTempToGrid" ); 
      //这些属性必须在loadMulLine前
      TTempToGrid.mulLineCount = 1;   
      TTempToGrid.displayTitle = 1;
      TTempToGrid.locked=0;
      TTempToGrid.hiddenPlus=1;
      TTempToGrid.hiddenSubtraction=1;      
      TTempToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
}



//初始化保存暂交费分类数据的Grid
function initTTempClassToGrid()
{
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="暂收号码";          		//列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="交费方式";          		//列名
      iArray[2][1]="60px";      	      		//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="交费日期";          		//列名
      iArray[3][1]="80px";      	      		//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="交费金额";          		//列名
      iArray[4][1]="60px";      	      		//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交费币种";      	   		//列名
      iArray[5][1]="60px";            			//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="币种名称";      	   		//列名
      iArray[6][1]="60px";            			//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[7]=new Array();
      iArray[7][0]="到账日期";          		//列名
      iArray[7][1]="0px";      	      		//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="管理机构";          		//列名
      iArray[8][1]="0px";      	      		//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="票据号码";          		//列名
      iArray[9][1]="100px";      	      		//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="支票日期";          		//列名
      iArray[10][1]="80px";      	      		//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="开户银行";      	   		//列名
      iArray[11][1]="60px";            			//列宽
      iArray[11][2]=40;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="银行账号";      	   		//列名
      iArray[12][1]="100px";            			//列宽
      iArray[12][2]=40;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="户名";      	   		//列名
      iArray[13][1]="60px";            			//列宽
      iArray[13][2]=10;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="收款银行";      	   		//列名
      iArray[14][1]="0px";            			//列宽
      iArray[14][2]=40;            			//列最大值
      iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[15]=new Array();
      iArray[15][0]="收款银行账号";      	   		//列名
      iArray[15][1]="150px";            		//列宽
      iArray[15][2]=40;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[16]=new Array();
      iArray[16][0]="收款银行户名";      	   		//列名
      iArray[16][1]="0px";            			//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[17]=new Array();
      iArray[17][0]="缴费间隔";      	   		//列名
      iArray[17][1]="0px";            			//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[18]=new Array();
      iArray[18][0]="缴费期间";      	   		//列名
      iArray[18][1]="0px";            			//列宽
      iArray[18][2]=60;            			//列最大值
      iArray[18][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[19]=new Array();
      iArray[19][0]="交费账户";      	   		//列名
      iArray[19][1]="0px";            			//列宽
      iArray[19][2]=60;            			//列最大值
      iArray[19][3]=3;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[20]=new Array();
      iArray[20][0]="证件类型";      	   		//列名
      iArray[20][1]="0px";            			//列宽
      iArray[20][2]=60;            			//列最大值
      iArray[20][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[21]=new Array();
      iArray[21][0]="证件号码";      	   		//列名
      iArray[21][1]="0px";            			//列宽
      iArray[21][2]=60;            			//列最大值
      iArray[21][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[22]=new Array();
      iArray[22][0]="业务号码";      	   		//列名
      iArray[22][1]="60px";            			//列宽
      iArray[22][2]=20;            			//列最大值
      iArray[22][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[23]=new Array();
      iArray[23][0]="业务类型";      	   		//列名
      iArray[23][1]="60px";            			//列宽
      iArray[23][2]=20;            			//列最大值
      iArray[23][3]=0;              			//是否允许输入,1表示允许，0表示不允许     
      
    	iArray[24]=new Array();
    	iArray[24][0]="交费人姓名";      			//列名
    	iArray[24][1]="0px";            			//列宽
    	iArray[24][2]=20;            					//列最大值
    	iArray[24][3]=3;              				//隐藏 3       
      
     
      TTempClassToGrid = new MulLineEnter( "fmSave" , "TTempClassToGrid" ); 
      //这些属性必须在loadMulLine前
      TTempClassToGrid.mulLineCount = 1;   
      TTempClassToGrid.displayTitle = 1;
      TTempClassToGrid.locked=0;
      TTempClassToGrid.hiddenPlus=1;
      TTempClassToGrid.hiddenSubtraction=1;
      TTempClassToGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }	
	
}
//更新2010-2-26

</script>
