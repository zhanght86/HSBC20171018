<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">
function initForm(){
	initMainInsured();
	initInsuredGrid();
	initPolGrid();
	initHidden();
	initImpartGrid();
	initAgentImpartGrid();
	queryMainInsured();
}

//受益人信息
function initMainInsured() {
  var iArray = new Array();

  try {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="合同号";          		//列名
    iArray[1][1]="120px";      	      		//列宽
    iArray[1][2]=20;            			//列最大值
    iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

    iArray[2]=new Array();
    iArray[2][0]="险种号";         			//列名
    iArray[2][1]="60px";            			//列宽
    iArray[2][2]=20;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


    iArray[3]=new Array();
    iArray[3][0]="险种名称";      	   		//列名
    iArray[3][1]="160px";            			//列宽
    iArray[3][2]=160;            			//列最大值
    iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="被保人客户号"; 		//列名
    iArray[4][1]="60px";		//列宽
    iArray[4][2]=60;			//列最大值
    iArray[4][3]=0;			//是否允许输入,1表示允许，0表示不允许

	iArray[5]=new Array();
    iArray[5][0]="姓名"; 		//列名
    iArray[5][1]="40px";		//列宽
    iArray[5][2]=40;			//列最大值
    iArray[5][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[6]=new Array();
    iArray[6][0]="性别"; 	//列名
    iArray[6][1]="40px";		//列宽
    iArray[6][2]=30;			//列最大值
    iArray[6][3]=0;			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="出生日期"; 		//列名
    iArray[7][1]="80px";		//列宽
    iArray[7][2]=40;			//列最大值
    iArray[7][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="证件类型"; 		//列名
    iArray[8][1]="40px";		//列宽
    iArray[8][2]=80;			//列最大值
    iArray[8][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[9]=new Array();
    iArray[9][0]="证件号码"; 	//列名
    iArray[9][1]="80px";		//列宽
    iArray[9][2]=60;			//列最大值
    iArray[9][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="保单号"; 	//列名
    iArray[10][1]="80px";		//列宽
    iArray[10][2]=60;			//列最大值
    iArray[10][3]=3;			//是否允许输入,1表示允许，0表示不允许


    MainInsuredGrid = new MulLineEnter( "fm" , "MainInsuredGrid" );
    //这些属性必须在loadMulLine前
    MainInsuredGrid.mulLineCount = 1;
    MainInsuredGrid.displayTitle = 1;
    MainInsuredGrid.canSel = 1;
    MainInsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
    MainInsuredGrid.hiddenPlus = 1;
    MainInsuredGrid.hiddenSubtraction = 1;   
    MainInsuredGrid.loadMulLine(iArray);
  } catch(ex) {
    alert("在AddRelationInit.jsp-->InitMainInsuredGrid函数中发生异常:初始化界面错误!");
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
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号码";          		//列名
      iArray[1][1]="50px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="40px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="性别";      	   		//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出生日期";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 
      
      iArray[5]=new Array();
      iArray[5][0]="证件类型";      	   		//列名
      iArray[5][1]="40px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=0; 
      //iArray[5][18]=300;
      
      iArray[6]=new Array();
      iArray[6][0]="证件号码";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=0; 

      iArray[7]=new Array();
      iArray[7][0]="与主被保险人关系";      	   		//列名
      iArray[7][1]="40px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=2; 
      iArray[7][4]="relation";
      iArray[7][9]="与被保人关系|code:relation";

      iArray[8]=new Array();
      iArray[8][0]="polno";      	   		//列名
      iArray[8][1]="80px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=3; 

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 1;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =0;
      InsuredGrid.canChk =1;
      InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuredGrid.hiddenSubtraction=1;
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
      iArray[1][0]="主险类别"; 		//列名
      iArray[1][1]="0px";		//列宽
      iArray[1][2]=40;			//列最大值
      iArray[1][3]=0;			//是否允许输入,1表示允许，0表示不允许

	  iArray[2]=new Array();
      iArray[2][0]="被保险人类型"; 		//列名
      iArray[2][1]="50px";		//列宽
      iArray[2][2]=40;			//列最大值
      iArray[2][3]=0;			//是否允许输入,1表示允许，0表示不允许
      iArray[2][10]="SquesNo";
      iArray[2][11]="0|^1|主被保险人 ^2|第二被保险人 ^3|第三被保险人";  
      iArray[2][19]=1;

	  
      iArray[3]=new Array();
      iArray[3][0]="投保险种";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
	  iArray[3][4]="RiskCode";
      iArray[3][18]=300;
      iArray[3][19]=1;            			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="保险金额(元)";         		//列名
      iArray[4][1]="40px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许 
      //iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[5]=new Array();
      iArray[5][0]="份数";         		//列名
      iArray[5][1]="40px";            		        //列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许 
      //iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[6]=new Array();
      iArray[6][0]="档次";         		//列名
      iArray[6][1]="40px";            		        //列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许 
      //iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[7]=new Array();
      iArray[7][0]="保险期间";         		//列名
      iArray[7][1]="40px";            		        //列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="交费年期";         		//列名
      iArray[8][1]="40px";            		        //列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="标准保费";         		//列名
      iArray[9][1]="40px";            		        //列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="职业加费";         		//列名
      iArray[10][1]="40px";            		        //列宽
      iArray[10][2]=60;            			//列最大值
      iArray[10][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="备注";         		//列名
      iArray[11][1]="0px";            		        //列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[12]=new Array();
      iArray[12][0]="保单险种号码";         		//列名
      iArray[12][1]="0px";            		        //列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[13]=new Array();
      iArray[13][0]="险种添空号码";         		//列名
      iArray[13][1]="0px";            		        //列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[14]=new Array();
      iArray[14][0]="被保人客户号";         		//列名
      iArray[14][1]="0px";            		        //列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[15]=new Array();
      iArray[15][0]="主险号码";         		//列名
      iArray[15][1]="0px";            		        //列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 1;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =0;
      //PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}


//健康告知
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
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="impartver";
      iArray[1][18]=300;
      iArray[1][19]=0;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="询问事项";         		//列名
      iArray[3][1]="370px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="投保人“是”或“否”";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="主被保险人“是”或“否”";         		//列名
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=90;            			//列最大值
      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[6][10]="ForYN";
      iArray[6][11]="0|^0|是 ^1|否"; 
      
      iArray[7]=new Array();
      iArray[7][0]="第二被保险人“是”或“否”";         		//列名
      iArray[7][1]="50px";            		//列宽
      iArray[7][2]=90;            			//列最大值
      iArray[7][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[7][10]="ForYN";
      iArray[7][11]="0|^0|是 ^1|否"; 
      
      iArray[8]=new Array();
      iArray[8][0]="第三被保险人“是”或“否”";         		//列名
      iArray[8][1]="50px";            		//列宽
      iArray[8][2]=90;            			//列最大值
      iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][10]="ForYN";
      iArray[8][11]="0|^0|是 ^1|否"; 
      
      iArray[9]=new Array();
      iArray[9][0]="添空号码";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=90;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 5;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.loadMulLine(iArray);  
      ImpartGrid.locked = 1;
      ImpartGrid.canSel = 0;
      ImpartGrid.canChk = 0;
      ImpartGrid.hiddenPlus = 1;
      ImpartGrid.hiddenSubtraction = 1;        
      
      //这些操作必须在loadMulLine后面
    }
    catch(ex) {
      alert(ex);
    }
}

//业务员告知
function initAgentImpartGrid(){
	  
	  var iArray=new Array();
	  
	  try{
	  iArray[0]=new Array();
	  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;      					//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="询问事项";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

      iArray[5]=new Array();
      iArray[5][0]="投保人“是”或“否”";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][10]="ForYN";
      iArray[5][11]="0|^0|是 ^1|否"; 

      iArray[6]=new Array();
      iArray[6][0]="被保险人“是”或“否”";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=90;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[6][10]="ForYN";
      iArray[6][11]="0|^0|是 ^1|否"; 
      
      iArray[7]=new Array();
      iArray[7][0]="第二被保险人“是”或“否”";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=90;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[7][10]="ForYN";
      iArray[7][11]="0|^0|是 ^1|否"; 
      
      iArray[8]=new Array();
      iArray[8][0]="第三被保险人“是”或“否”";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=90;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][10]="ForYN";
      iArray[8][11]="0|^0|是 ^1|否"; 
      
      iArray[9]=new Array();
      iArray[9][0]="添空号码";         		//列名
      iArray[9][1]="0px";            		//列宽
      iArray[9][2]=90;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      AgentImpartGrid = new MulLineEnter( "fm" , "AgentImpartGrid" ); 
      //这些属性必须在loadMulLine前
      AgentImpartGrid.mulLineCount = 0;   
      AgentImpartGrid.displayTitle = 1;
      AgentImpartGrid.loadMulLine(iArray);
      AgentImpartGrid.locked = 1;
      AgentImpartGrid.canSel = 0;
      AgentImpartGrid.canChk = 0;
      AgentImpartGrid.hiddenPlus = 1;
      AgentImpartGrid.hiddenSubtraction = 1;          
      }catch(ex){
      	alert("执行initAgentImpartGrid时出错！");
      }
}

function initHidden(){
	fm.GrpContNo.value = tGrpContNo;
	fm.PrtNo.value = tPrtNo;
}
</script>
