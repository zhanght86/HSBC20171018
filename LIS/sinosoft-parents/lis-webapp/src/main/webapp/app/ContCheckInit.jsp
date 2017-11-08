<%
//程序名称：ContCheckInit.jsp
//程序功能：
//创建日期：2006-2-13 10:38
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

//初始化输入框
function initInpBox() 
{ 

    //Q:scantype是判断是否有扫描件，用于定制随动
    if(scantype=="scan")
    {
      setFocus();
    }  
    //判断是否是有扫描件 
    /**********************
     *ScanFlag=0--有扫描　
     *ScanFlag=1--无扫描　
     ***********************/
	  //根据需求PrtNo中存投保单号，故将投保单号字段赋值
      document.all('PrtNo').value = prtNo;
      document.all('ProposalContNo').value = prtNo;
      document.all('ContNo').value = prtNo;
      document.all('ManageCom').value = ManageCom;
      document.all('ActivityID').value = ActivityID;
      document.all('MissionID').value = MissionID;
      document.all('SubMissionID').value = SubMissionID;
      fm.Operator.value = nullToEmpty("<%=tGI.Operator%>");
      
      document.all('NoType').value = NoType;
      fm.PrtNo.readOnly=true; 

}

// 下拉框的初始化
function initSelBox()
{

}


//表单初始化
function initForm()
{
	 //初始化多业务员列表
    initMultiAgentGrid();
	//初始化业务员告知
    initAgentImpartGrid();    
    //初始化投保人告知信息
    initImpartGrid();
	// 被保人信息列表的初始化
	initInsuredGrid();
	// 被保人告知信息列表的初始化
    initImpartInsuredGrid() ;
    //被保人险种信息列表初始化
	initPolGrid();
	// 受益人信息列表的初始化
    initLCBnfGrid();
    //初始化输入框
	initInpBox();
	//初始化查询
	 initQuery();
	 //初始化投被保人校验按钮可用还是不可用
	 displayAppntButton();	 
	 
}



//多业务员列表初始化
function initMultiAgentGrid()
{
    var iArray = new Array();
    try 
    {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="业务员代码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="queryAgentGrid";  　//双击调用查询业务员的函数          

      iArray[2]=new Array();
      iArray[2][0]="业务员姓名";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="所属机构";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="营业部、组";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="佣金比例";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=150;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="AgentGroup";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      MultiAgentGrid.mulLineCount = 0;   
      MultiAgentGrid.displayTitle = 1;
      MultiAgentGrid.hiddenPlus = 1;
      MultiAgentGrid.hiddenSubtraction = 1;
      MultiAgentGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
	
}
// 业务员告知信息列表的初始化
function initAgentImpartGrid() 
{  
    var iArray = new Array();
    try 
    {
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
      iArray[1][4]="impver_agent_sig";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="AgentImpart";
      //iArray[1][11]="0|^05|业务员告知";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

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

      AgentImpartGrid = new MulLineEnter( "fm" , "AgentImpartGrid" ); 
      AgentImpartGrid.mulLineCount = 0;
      AgentImpartGrid.canChk = 0;   
      AgentImpartGrid.displayTitle = 1;
      AgentImpartGrid.hiddenPlus = 1;
      AgentImpartGrid.hiddenSubtraction = 1;
      AgentImpartGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}

// 投保人告知信息 告知信息列表的初始化
function initImpartGrid()
{  
    var iArray = new Array();
    try 
    {
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
      iArray[1][4]="impver_appnt_sig";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="AppntImpart";
      //iArray[1][11]="0|^01|财务及其他告知^02|健康告知";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

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

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 0;   
      ImpartGrid.displayTitle = 1;
      ImpartGrid.hiddenPlus = 1;
      ImpartGrid.hiddenSubtraction = 1;
      ImpartGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
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
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="性别";      	   		//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=2;              	//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="Sex"; 


      iArray[4]=new Array();
      iArray[4][0]="出生日期";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 
      
      iArray[5]=new Array();
      iArray[5][0]="与第一被保险人关系";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=2; 
      iArray[5][4]="Relation";              	        //是否引用代码:null||""为不引用
      iArray[5][9]="与主被保险人关系|code:Relation&NOTNULL";
      //iArray[5][18]=300;
      
      iArray[6]=new Array();
      iArray[6][0]="客户内部号";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=2; 
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人";      
	  iArray[6][19]=1;

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

// 被保人告知信息列表的初始化
function initImpartInsuredGrid() 
{                               
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
      iArray[1][4]="impver_insu_sig";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="InsuredImpart";
      //iArray[1][11]="0|^01|财务及其他告知^02|健康告知";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

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

      ImpartInsuredGrid = new MulLineEnter( "fm" , "ImpartInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      ImpartInsuredGrid.hiddenPlus = 1;
      ImpartInsuredGrid.hiddenSubtraction = 1;
      ImpartInsuredGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}
// 告知明细信息列表的初始化
function initImpartDetailGrid2()
{                               
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
			iArray[1][4]="impver_insu_sig";
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="InsuredImpartDetail";
      //iArray[1][11]="0|^01|财务及其他告知^02|健康告知";
      iArray[1][18]=300;
      iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2";
      iArray[2][6]="0";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="内容";         		//列名
      iArray[3][1]="450px";            		//列宽
      iArray[3][2]=2000;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
 
      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartDetailGrid.mulLineCount = 0;   
      ImpartDetailGrid.displayTitle = 1;
      ImpartDetailGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
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
      iArray[1][0]="保险单险种号码";         		//列名
      iArray[1][1]="0px";            		        //列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="60px";            		        //列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      //iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[3]=new Array();
      iArray[3][0]="险种名称";         		//列名
      iArray[3][1]="200px";            		        //列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[4]=new Array();
      iArray[4][0]="保费";         		//列名
      iArray[4][1]="60px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="保额";         		//列名
      iArray[5][1]="60px";            		        //列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="份数";         		//列名
      iArray[6][1]="60px";            		        //列宽
      iArray[6][2]=60;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="保险期间";         		//列名
      iArray[7][1]="60px";            		        //列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="缴费期限";         		//列名
      iArray[8][1]="60px";            		        //列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="领取年龄";         		//列名
      iArray[9][1]="0px";            		        //列宽
      iArray[9][2]=0;            			//列最大值
      iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="领取方式";         		//列名
      iArray[10][1]="0px";            		        //列宽
      iArray[10][2]=0;            			//列最大值
      iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="币种";         		//列名
      iArray[11][1]="60px";            		        //列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[11][4]="Currency";              	        //是否引用代码:null||""为不引用
      iArray[11][9]="币种|code:Currency";
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}

// 受益人信息列表的初始化
function initLCBnfGrid() 
{
  var iArray = new Array();
  try 
  {
    iArray[0]=new Array();
    iArray[0][0]="序号"; 			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";		//列宽
    iArray[0][2]=10;			//列最大值
    iArray[0][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="受益人类别"; 		//列名
    iArray[1][1]="0px";		//列宽
    iArray[1][2]=40;			//列最大值
    iArray[1][3]=3;			//是否允许输入,1表示允许，0表示不允许

    iArray[2]=new Array();
    iArray[2][0]="姓名"; 	//列名
    iArray[2][1]="60px";		//列宽
    iArray[2][2]=30;			//列最大值
    iArray[2][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="证件类型"; 		//列名
    iArray[3][1]="60px";		//列宽
    iArray[3][2]=40;			//列最大值
    iArray[3][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[3][4]="IDType";
    iArray[3][9]="证件类型|code:IDType";

    iArray[4]=new Array();
    iArray[4][0]="证件号码"; 		//列名
    iArray[4][1]="150px";		//列宽
    iArray[4][2]=80;			//列最大值
    iArray[4][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[5]=new Array();
    iArray[5][0]="与被保人关系"; 	//列名
    iArray[5][1]="80px";		//列宽
    iArray[5][2]=60;			//列最大值
    iArray[5][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[5][4]="Relation";

    iArray[6]=new Array();
    iArray[6][0]="受益顺序"; 		//列名
    iArray[6][1]="60px";		//列宽
    iArray[6][2]=40;			//列最大值
    iArray[6][3]=2;			//是否允许输入,1表示允许，0表示不允许
    iArray[6][4]="bnforder";

    iArray[7]=new Array();
    iArray[7][0]="受益份额"; 		//列名
    iArray[7][1]="60px";		//列宽
    iArray[7][2]=40;			//列最大值
    iArray[7][3]=0;			//是否允许输入,1表示允许，0表示不允许

    iArray[8]=new Array();
    iArray[8][0]="所属被保人"; 		//列名
    iArray[8][1]="80px";		//列宽
    iArray[8][2]=30;			//列最大值
  	iArray[8][3]=0;
 
    iArray[9]=new Array();
    iArray[9][0]="所属被保人内部客户号"; 		//列名
    iArray[9][1]="0px";		//列宽
    iArray[9][2]=30;			//列最大值
    iArray[9][3]=3;			//是否允许输入,1表示允许，0表示不允许

    iArray[10]=new Array();
    iArray[10][0]="所属被保人客户号"; 		//列名
    iArray[10][1]="100px";		//列宽
    iArray[10][2]=30;			//列最大值
    iArray[10][3]=0;			//是否允许输入,1表示允许，0表示不允许

    LCBnfGrid = new MulLineEnter( "fm" , "LCBnfGrid" );
    LCBnfGrid.mulLineCount = 1;
    LCBnfGrid.displayTitle = 1;
    LCBnfGrid.hiddenPlus=1;
    LCBnfGrid.hiddenSubtraction=1;
    LCBnfGrid.loadMulLine(iArray);
  } 
  catch(ex) 
  {
    alert("initLCBnfGrid函数中发生异常:初始化界面错误!");
  }
}

//投连险种的投资计划
function initInvestPlanRate()
{
    var iArray = new Array();

    try {
      iArray[0] = new Array();
      iArray[0][0] = "序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1] = "30px";            		//列宽
      iArray[0][2] = 10;            			//列最大值
      iArray[0][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1] = new Array();
      iArray[1][0] = "投资帐户号";    	        //列名
      iArray[1][1] = "80px";            		//列宽
      iArray[1][2] = 100;            			//列最大值
      iArray[1][3] = 0;                       //是否允许输入,1表示允许，0表示不允许 2表示代码选择
     // iArray[1][14]=document.all('PolNo').value;
  
      iArray[2] = new Array();
      iArray[2][0] = "投资帐户名称";         		//列名
      iArray[2][1] = "100px";            		//列宽
      iArray[2][2] = 60;            			//列最大值
      iArray[2][3] = 0;                   			//是否允许输入,1表示允许，0表示不允许
   //   iArray[2][4] ="findinsuaccno";
   //   iArray[2][15] ="PolNo";
   //   iArray[2][16] =document.all('PolNo').value;
   
   
      
      iArray[3] = new Array();
      iArray[3][0] = "投资比例上限";         		//列名
      iArray[3][1] = "0px";            		//列宽
      iArray[3][2] = 60;            			//列最大值
      iArray[3][3] = 0;              			//是否允许输入,1表示允许，0表示不允许
     // iArray[3][4] = "fpayplancode";
   //    iArray[3][15] ="PolNo";
    //   iArray[3][16] =document.all('PolNo').value;

      iArray[4] = new Array();
      iArray[4][0] = "投资比例下限";         		//列名
      iArray[4][1] = "0px";            		//列宽
      iArray[4][2] = 50;            			//列最大值
      iArray[4][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5] = new Array();
      iArray[5][0] = "投资比例";         		//列名
      iArray[5][1] = "80px";            		//列宽
      iArray[5][2] = 50;            			//列最大值
      iArray[5][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

 
      iArray[6] = new Array();
      iArray[6][0] = "缴费编号";         		//列名
      iArray[6][1] = "0px";            		//列宽
      iArray[6][2] = 100;            			//列最大值
      iArray[6][3] = 0;              			//是否允许输入,1表示允许，0表示不允许

        
      /*iArray[7] = new Array();
      iArray[7][0] = "";         		//列名
      iArray[7][1] = "80px";            		//列宽
      iArray[7][2] = 100;            			//列最大值
      iArray[7][3] = 1;


     
      iArray[8] = new Array();
      iArray[8][0] = "";         		//列名
      iArray[8][1] = "80px";            		//列宽
      iArray[8][2] = 100;            			//列最大值
      iArray[8][3] = 1;*/
 
      InvestPlanRate = new MulLineEnter( "fm" , "InvestPlanRate" );
      //这些属性必须在loadMulLine前
      InvestPlanRate.mulLineCount = 0;
     InvestPlanRate.displayTitle = 1;
     InvestPlanRate.hiddenPlus = 1;
      InvestPlanRate.hiddenSubtraction = 1;
      InvestPlanRate.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}
</script>
