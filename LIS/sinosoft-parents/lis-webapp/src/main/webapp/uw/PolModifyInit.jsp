<%
//程序名称：ProposalQueryInit.jsp
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
//返回按钮初始化
var str = "";
function initDisplayButton()
{

}
// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	document.all('Reason').value='';
	document.all('ContNo').value=ContNo;
	document.all('PrtNo').value=prtNo;
	/*
	if(document.all('PayLocation').value!="0"||document.all('PayLocation').value!="8")
	{
		document.all('BankCode').readonly;
	}
	*/
  }
  catch(ex)
  {
    alert("在PolModifyInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在ProposalQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
  	initInpBox();
  	initPolGrid();
  	//受益人信息
  	initLCBnfGrid();
  	initImpartGrid();
  	//查询数据库,显示合同等信息
  	queryAllContInfo();
  	//初始化不能修改的控件为只读属性
  	
  	
  }
  catch(re)
  {
    alert("ProposalQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


// 被保人信息列表的初始化
function initInsuredGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号码";          		//列名
      iArray[1][1]="80px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="证件号码";      	   		//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="idno"; 

      iArray[4]=new Array();
      iArray[4][0]="证件号码";      	   		//列名
      iArray[4][1]="40px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=1;              	//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="住址";      	   		//列名
      iArray[5][1]="80px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=1; 
      
      iArray[6]=new Array();
      iArray[6][0]="邮编";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=1; 
      
      iArray[7]=new Array();
      iArray[7][0]="电子邮箱";      	   		//列名
      iArray[7][1]="100px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=1; 
      
      iArray[8]=new Array();
      iArray[8][0]="联系电话（1）";      	   		//列名
      iArray[8][1]="100px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=1; 
      
      iArray[9]=new Array();
      iArray[9][0]="联系电话（2）";      	   		//列名
      iArray[9][1]="100px";            			//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=1; 
      
      iArray[10]=new Array();
      iArray[10][0]="工作单位";      	   		//列名
      iArray[10][1]="100px";            			//列宽
      iArray[10][2]=20;            			//列最大值
      iArray[10][3]=1; 
      
      iArray[11]=new Array();
      iArray[11][0]="客户内部号";      	   		//列名
      iArray[11][1]="80px";            			//列宽
      iArray[11][2]=20;            			//列最大值
      iArray[11][3]=3; 

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 0;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      InsuredGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

//被保人险种信息列表初始化
function initPolGrid()
{
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		        //列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种保单号码";         		//列名
      iArray[1][1]="0px";            		        //列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="40px";            		        //列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      //iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[3]=new Array();
      iArray[3][0]="险种名称";         		//列名
      iArray[3][1]="150px";            		        //列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[4]=new Array();
      iArray[4][0]="保费(元)";         		//列名
      iArray[4][1]="40px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="保额(元)";         		//列名
      iArray[5][1]="40px";            		        //列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
     // PolGrid.canSel =1;
      //PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}
// 受益人信息列表的初始化
function initLCBnfGrid() {                               
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许
  
    iArray[1]=new Array();
    iArray[1][0]="受益人类别"; 		//列名
    iArray[1][1]="60px";		//列宽
    iArray[1][2]=60;			//列最大值
    iArray[1][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[1][4]="bnftype";
   
    iArray[2]=new Array();
    iArray[2][0]="姓名"; 	//列名
    iArray[2][1]="80px";		//列宽
    iArray[2][2]=80;			//列最大值
    iArray[2][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[2][9]="姓名|len<=20"; //校验
  
    iArray[3]=new Array();
    iArray[3][0]="证件类型"; 		//列名
    iArray[3][1]="40px";		//列宽
    iArray[3][2]=40;			//列最大值
    iArray[3][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="IDType";
    iArray[3][9]="证件类型|code:IDType";
  
    iArray[4]=new Array();
    iArray[4][0]="证件号码"; 		//列名
    iArray[4][1]="140px";		//列宽
    iArray[4][2]=100;			//列最大值
    iArray[4][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[4][9]="证件号码|len<=20";

    iArray[5]=new Array();
    iArray[5][0]="与被保人关系"; 	//列名
    iArray[5][1]="60px";		//列宽
    iArray[5][2]=60;			//列最大值
    iArray[5][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[5][4]="Relation";
    iArray[5][9]="与被保人关系|code:Relation";
  
    iArray[6]=new Array();
    iArray[6][0]="受益比例"; 		//列名
    iArray[6][1]="40px";		//列宽
    iArray[6][2]=40;			//列最大值
    iArray[6][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][9]="受益比例|num&len<=10";
  
    iArray[7]=new Array();
    iArray[7][0]="受益顺序"; 		//列名
    iArray[7][1]="40px";		//列宽
    iArray[7][2]=40;			//列最大值
    iArray[7][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[7][4]="OccupationType";
    iArray[7][9]="受益顺序|code:OccupationType";
  
    iArray[8]=new Array();
    iArray[8][0]="住址（填序号）"; 		//列名
    iArray[8][1]="160px";		//列宽
    iArray[8][2]=100;			//列最大值
    iArray[8][3]=1;			//是否允许输入,1表示允许，0表示不允许
    iArray[8][9]="住址|len<=80";
  
    iArray[9]=new Array();
    iArray[9][0]="速填"; 		//列名
    iArray[9][1]="30px";		//列宽
    iArray[9][2]=30;			//列最大值
    iArray[9][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[9][10]="customertypea";
    iArray[9][11]="0|^0|投保人|^1|被保人";
    
    iArray[10]=new Array();
    iArray[10][0]="操作员"; 		//列名
    iArray[10][1]="0px";		//列宽
    iArray[10][2]=60;			//列最大值
    iArray[10][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[11]=new Array();
    iArray[11][0]="录入日期"; 		//列名
    iArray[11][1]="0px";		//列宽
    iArray[11][2]=60;			//列最大值
    iArray[11][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[12]=new Array();
    iArray[12][0]="录入时间"; 		//列名
    iArray[12][1]="0px";		//列宽
    iArray[12][2]=60;			//列最大值
    iArray[12][3]=0;			//是否允许输入,1表示允许，0表示不允许
  
    LCBnfGrid = new MulLineEnter( "fm" , "LCBnfGrid" ); 
    //这些属性必须在loadMulLine前
    LCBnfGrid.mulLineCount = 1; 
    LCBnfGrid.displayTitle = 1;
    //LCBnfGrid.hiddenPlus = 1;
    //LCBnfGrid.hiddenSubtraction = 1;
    LCBnfGrid.loadMulLine(iArray);    
  
    //这些操作必须在loadMulLine后面
    //BnfGrid.setRowColData(0,8,"1");
    //BnfGrid.setRowColData(0,9,"1");
  } catch(ex) {
    alert(ex);
  }
}

// 告知信息列表的初始化
function initImpartGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="ImpartVer1";                     //新版投保单对应告知
      iArray[1][5]="1";
      iArray[1][6]="0";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][18]=300;

      iArray[2]=new Array();             
      iArray[2][0]="告知编码";         	
      iArray[2][1]="60px";            	
      iArray[2][2]=60;            		
      iArray[2][3]=2;              		
      iArray[2][4]="ImpartCode";         
      iArray[2][5]="2|3";                
      iArray[2][6]="0|1";                
      iArray[2][9]="告知编码|len<=4";    
      iArray[2][15]="ImpartVer";     //查询数据库中对应列名
      iArray[2][17]="1";             //依赖第6列的值，即传入的查询条件为 ImpartVer ='001'  

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="告知客户类型";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][10]="CustomerNoType";
      iArray[5][11]="0|^0|投保人|^1|被保人|^2|业务员";      
      
      iArray[6]=new Array();                           //隐藏列
      iArray[6][0]="客户内部号码";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=2;  
      iArray[6][10]="CustomerToInsured";
      iArray[6][11]="0|^1|被保人|^2|第2被保人|^3|第3被保人";   

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 3;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus=1;
      ImpartGrid.hiddenSubtraction=1;
      ImpartGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}


</script>
