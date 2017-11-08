<%
//程序名称：LFGetNoticeInit.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>


<script language="JavaScript">
function initInpBox()
{ 
  try
  {     


  }
  catch(ex)
  {
    alert("在LFGetNoticeInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initPolGrid();   
  }
  catch(re)
  {
    alert("LFGetNoticeInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
 var PolGrid;          //定义为全局变量，提供给displayMultiline使用
// 投保单信息列表的初始化
function initPolGrid()
{                               
  var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="领取清单号";   		//列名
	  iArray[1][1]="80px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
	  iArray[2][0]="团单号";        		//列名
	  iArray[2][1]="100px";            	//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[3]=new Array();
	  iArray[3][0]="投保单位名称";       	//列名
	  iArray[3][1]="100px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0; 
	
	  iArray[4]=new Array();
	  iArray[4][0]="流水号";      //列名
	  iArray[4][1]="0px";            	//列宽
	  iArray[4][2]=100;            			//列最大值
	  iArray[4][3]=3; 									//是否允许输入,1表示允许，0表示不允许
      
    iArray[5]=new Array();
	  iArray[5][0]="领取申请人";       	//列名
	  iArray[5][1]="100px";            	//列宽
	  iArray[5][2]=100;            			//列最大值
	  iArray[5][3]=0;  
	  	        
    iArray[6]=new Array();
	  iArray[6][0]="操作员";      //列名
	  iArray[6][1]="60px";            	//列宽
	  iArray[6][2]=60;            			//列最大值
	  iArray[6][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	  
    PolGrid = new MulLineEnter( "document" , "PolGrid" ); 
    //这些属性必须在loadMulLine前
    //PolGrid.mulLineCount = 10;   
    PolGrid.displayTitle = 1;
    PolGrid.canSel = 1;
    PolGrid.hiddenPlus=1;
    PolGrid.hiddenSubtraction=1;
    PolGrid.selBoxEventFuncName ="reportDetailClick";
    PolGrid.loadMulLine(iArray);  
    }
    catch(ex)
    {
      alert("LFGetNoticeInit.jsp-->initPolGrid函数中发生异常:"+ex);
    }
}


function initLFGetGrid()
{                               
  var iArray = new Array();
  try
  {
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	  iArray[0][1]="30px";            	//列宽
	  iArray[0][2]=10;            			//列最大值
	  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[1]=new Array();
	  iArray[1][0]="个人保单号";   		//列名
	  iArray[1][1]="120px";            	//列宽
	  iArray[1][2]=100;            			//列最大值
	  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[2]=new Array();
	  iArray[2][0]="被保险人";        		//列名
	  iArray[2][1]="80px";            	//列宽
	  iArray[2][2]=100;            			//列最大值
	  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	  iArray[3]=new Array();
	  iArray[3][0]="领取方式";       	//列名
	  iArray[3][1]="60px";            	//列宽
	  iArray[3][2]=100;            			//列最大值
	  iArray[3][3]=0; 
	
	  iArray[4]=new Array();
	  iArray[4][0]="领取期数";      //列名
	  iArray[4][1]="80px";            	//列宽
	  iArray[4][2]=100;            			//列最大值
	  iArray[4][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
    iArray[5]=new Array();
	  iArray[5][0]="应领日期";       	//列名
	  iArray[5][1]="80px";            	//列宽
	  iArray[5][2]=100;            			//列最大值
	  iArray[5][3]=0;  
	  	        
    iArray[6]=new Array();
	  iArray[6][0]="本次应付金额";      //列名
	  iArray[6][1]="60px";            	//列宽
	  iArray[6][2]=60;            			//列最大值
	  iArray[6][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[7]=new Array();
	  iArray[7][0]="支付方式";      //列名
	  iArray[7][1]="60px";            	//列宽
	  iArray[7][2]=60;            			//列最大值
	  iArray[7][3]=0; 									//是否允许输入,1表示允许，0表示不允许
	
	  iArray[8]=new Array();                                                 
  	iArray[8][0]="开户银行";      //列名                                  
  	iArray[8][1]="120px";            	//列宽                               
  	iArray[8][2]=60;            			//列最大值                          
  	iArray[8][3]=0; 									//是否允许输入,1表示允许，0表示不允许 
  	
  	iArray[9]=new Array();                                                 
  	iArray[9][0]="户名";      //列名                                
  	iArray[9][1]="60px";            	//列宽                              
  	iArray[9][2]=60;            			//列最大值                              
  	iArray[9][3]=0; 									//是否允许输入,1表示允许，0表示不允许  
     
    iArray[10]=new Array();                                                                                                                                                                           
    iArray[10][0]="账号";      //列名 
    iArray[10][1]="60px";      //列宽     
    iArray[10][2]=60;         //列最大值 
    iArray[10][3]=0; 				//是否允许输入,1表示允许，0表示不允许  
     
     
    LFGetGrid = new MulLineEnter( "document" , "LFGetGrid" ); 
    //这些属性必须在loadMulLine前
    //PolGrid.mulLineCount = 10;   
    LFGetGrid.displayTitle = 1;
    //LFGetGrid.canSel = 1;
    LFGetGrid.hiddenPlus=1;
    LFGetGrid.hiddenSubtraction=1;
    //LFGetGrid.selBoxEventFuncName ="reportDetailClick";
    LFGetGrid.loadMulLine(iArray);                                                                                                                             }
    catch(ex)
    {
      alert("LFGetNoticeInit.jsp-->initPolGrid函数中发生异常:"+ex);
    }
}
</script>
