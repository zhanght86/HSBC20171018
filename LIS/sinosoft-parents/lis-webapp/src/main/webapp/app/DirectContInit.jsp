<%
//程序名称：DirectContInit.jsp
//程序功能：直销险种录入初始化页面
//创建日期： 2006-1-20 10:13
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%@include file="../common/jsp/UsrCheck.jsp"%>

<Script language="JavaScript">
//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}
	//按照不同的LoadFlag进行不同的处理，得到界面的调用位置
    /**********************************************
		*LoadFlag=1 -- 个人投保单直接录入
		*LoadFlag=2 -- 集体下个人投保单录入
		*LoadFlag=3 -- 个人投保单明细查询
		*LoadFlag=4 -- 集体下个人投保单明细查询
		*LoadFlag=5 -- 复核
		*LoadFlag=6 -- 查询
		*LoadFlag=7 -- 保全新保加人
		*LoadFlag=8 -- 保全新增附加险
		*LoadFlag=9 -- 无名单补名单
		*LoadFlag=10-- 浮动费率
		*LoadFlag=13-- 集体下投保单复核修改
		*LoadFlag=16-- 集体下投保单查询
		*LoadFlag=25-- 人工核保修改投保单
		*LoadFlag=99-- 随动定制
     ************************************************/	
     //判断是否是有扫描件 
    /************************************************
     *ScanFlag=0--有扫描　
     *ScanFlag=1--无扫描　
     ***********************************************/
	
//输入栏初始化
function initInputField()
{
	//Q:scantype是判断是否有扫描件，用于定制随动
    if(scantype=="scan")
    {
      setFocus();
    }  
	//LoadFlag=="1" -- 个人投保单直接录入
	if(LoadFlag=="1")
	{
		document.all('PrtNo').value = prtNo;
		document.all('ProposalContNo').value = prtNo;
		document.all('ContNo').value = prtNo;
		document.all('ManageCom').value = ManageCom;
		document.all('MissionID').value = MissionID;
		document.all('SubMissionID').value = SubMissionID;
		document.all('ActivityID').value = ActivityID;
		fm.SellType.value="12"; //默认销售方式：12——电话直销 
		showOneCodeName('sellType','SellType','SellTypeName'); 
		fm.AppntIDType.value="0";  
		fm.SequenceNo.value="1";
		fm.RelationToMainInsured.value="00";
		fm.InsuredIDType.value="0"; 
		//判断记事本中的记录数量
		checkNotePad(prtNo,LoadFlag);
		divContInfoSaveButton.style.display=""; //显示保存合同及投保人信息按钮控件
		divSamePerson.style.display=""; //显示有勾选投保人与被保人是同一人的框
		divContInsurdSaveButton.style.display = ""; //显示保存被保人信息按钮控件
		divInputContButton.style.display = "";  //显示录单界面【 录单按钮 】
	}
	//LoadFlag=="5" -- 个人投保单复核
	if(LoadFlag=="5")
	{
		document.all('PrtNo').value = prtNo;
		document.all('ProposalContNo').value = prtNo;
		document.all('ContNo').value = prtNo;
		document.all('ManageCom').value = ManageCom;
		document.all('MissionID').value = MissionID;
		document.all('SubMissionID').value = SubMissionID;
		document.all('ActivityID').value = ActivityID;
		fm.Operator.value = nullToEmpty("<%=tGI.Operator%>");
		//判断记事本中的记录数量
		checkNotePad(prtNo,LoadFlag);
		divContInfoSaveButton.style.display="none"; //显示保存合同及投保人信息按钮控件
		divContInsurdSaveButton.style.display = "none"; //显示保存被保人信息按钮控件
		divApproveContButton.style.display = "";  //录单界面【 复核按钮 】
	}
	//LoadFlag=="3" -- 个险问题件修改
	if(LoadFlag=="3")
	{
		//document.all('PrtNo').value = prtNo;
		//document.all('ProposalContNo').value = prtNo;
		document.all('ContNo').value = prtNo;
		document.all('ManageCom').value = ManageCom;
		document.all('MissionID').value = MissionID;
		document.all('SubMissionID').value = SubMissionID;
		document.all('ActivityID').value = ActivityID;
		//判断记事本中的记录数量
		checkNotePad(prtNo,LoadFlag);
		divContInfoSaveButton.style.display=""; //显示保存合同及投保人信息按钮控件
		divContInsurdSaveButton.style.display = ""; //显示保存被保人信息按钮控件
		divProblemModifyContButton.style.display = "";  //显示个险问题件修改按钮
	}
	if(LoadFlag=="6") //查询
	{
		document.all('PrtNo').value = prtNo;
		document.all('ProposalContNo').value = prtNo;
		document.all('ContNo').value = ContNo;
		document.all('ManageCom').value = ManageCom;
		divInputQuery.style.display = "";  //查询投保单按钮
	}
	//人工核保修改投保单
    if(LoadFlag=="25")
    {      
　  document.all('PrtNo').value = prtNo;
		document.all('ProposalContNo').value = prtNo;
		document.all('ContNo').value = ContNo;
		document.all('ManageCom').value = ManageCom;
		divInputQuery.style.display = "";  //查询投保单按钮  
    }
	if(LoadFlag == "99") //随动定制
	{
      autoMoveButton.style.display="";    
    }    
    
} 	
	
//*******载入页面时进行初始化*****/	
function initForm()
{
    initInsuredGrid(); //初始化被保人列表
	initImpartInsuredGrid() ; // 被保人告知信息列表的初始化
	initPolGrid(); //被保人险种信息列表初始化
	initInputField();
	initQuery();
}	


// 被保人告知信息列表的初始化
function initImpartInsuredGrid() 
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
      iArray[1][4]="impver_insu_direct";  
      //iArray[1][9]="告知版别|len<=5";
      //iArray[1][10]="InsuredImpart";
      //iArray[1][11]="0|^07|防癌";
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
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      ImpartInsuredGrid.loadMulLine(iArray);  
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
      iArray[1][0]="保险单险种号码";         		//列名
      iArray[1][1]="60px";            		        //列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="险种编码";         		//列名
      iArray[2][1]="60px";            		        //列宽
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
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid.hiddenPlus=1;
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) 
    {
      alert(ex);
    }
}
//初始化被保人列表
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
	//InsuredGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
	InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
	InsuredGrid.hiddenSubtraction=1;
	InsuredGrid.loadMulLine(iArray);  
	}
	catch(ex)
	{
	alert(ex);
	}
}
</Script>
