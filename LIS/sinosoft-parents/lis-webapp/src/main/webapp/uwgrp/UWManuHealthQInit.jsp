<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuHealthInit.jsp
//程序功能：保全人工核保体检资料录入
//创建日期：
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">


// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	
    fm.all('ContNo').value = '';
    fm.all('MissionID').value = '';
    fm.all('SubMissionID').value = '';
  }
  catch(ex)
  {
    alert("在UWManuHealthInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }   
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在UWSubInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm(tContNo,tMissionID,tSubMission,tPrtNo,tPrtSeq,tCustomerNo)
{
  try
  {
  
	initHide(tContNo,tMissionID,tSubMission,tPrtNo,tPrtSeq,tCustomerNo);
	
	initHealthGrid();
	
	initMainUWHealthGrid();
	
	initDisDesbGrid();
  	//alert(4);
 if(tFlag == "1")
 {

     divOperation.style.display = "";
		 divMainHealth.style.display = "";
		 divHealthButton.style.display = "none";
		 initInsureNo();
//     alert("operatorQuery()");
//     operatorQuery();
 }
 
 if(tFlag == "3")
 {
     divMainHealth.style.display = "";
		 divHealthButton.style.display = "none";
		 initCustomerNo();
 }
  //alert(111); 
	init();
	//alert(23);
	}
	
  catch(re) 
  {
  alert("UWManuHealthInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
  
}


// 责任信息列表的初始化
function initMainUWHealthGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="合同号";    	//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[2]=new Array();
      iArray[2][0]="流水号";         			//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

              

      iArray[3]=new Array();
      iArray[3][0]="体检客户号";         			//列名
      iArray[3][1]="120px";            			//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="体检人姓名";         			//列名
      iArray[4][1]="120px";            			//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="体检时间";         			//列名
      iArray[5][1]="120px";            			//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="入机时间";         			//列名
      iArray[6][1]="120px";            			//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="打印状态";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[7][1]="60px";         			//列宽
      iArray[7][2]=10;          			//列最大值
      iArray[7][3]=2; 
      iArray[7][10] = "ifPrint";
      iArray[7][11] = "0|3^0|未打印|^1|已打印|^2|已回复|";  
      iArray[7][12] = "7";
      iArray[7][13] = "0";
      iArray[7][14] = "N";
     
      
      
      MainHealthGrid = new MulLineEnter( "fm" , "MainHealthGrid" ); 
      //这些属性必须在loadMulLine前                            
      MainHealthGrid.mulLineCount = 1;
      MainHealthGrid.displayTitle = 1;
      MainHealthGrid.canChk = 0;
      MainHealthGrid.hiddenPlus = 1;
      MainHealthGrid.hiddenSubtraction = 1;
      MainHealthGrid.canSel =1
      MainHealthGrid.loadMulLine(iArray);  
      MainHealthGrid. selBoxEventFuncName = "easyQueryClick";
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 责任信息列表的初始化
function initHealthGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="体检项目编号";    	//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="体检项目名称";         			//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="体检项目结论";         			//列名
      iArray[3][1]="550px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //这些属性必须在loadMulLine前  

      
      HealthGrid = new MulLineEnter( "fm" , "HealthGrid" ); 
      //PolGrid. selBoxEventFuncName = "easyQueryAddClick";
      //这些属性必须在loadMulLine前
      HealthGrid.mulLineCount =0;   
      HealthGrid.displayTitle = 1;
      HealthGrid.hiddenPlus = 1;
      HealthGrid.hiddenSubtraction = 1;
//      HealthGrid.canSel = 1;
//      HealthGrid.canChk = 1;
      HealthGrid.loadMulLine(iArray); 
      
      //这些操作必须在loadMulLine后面
      //HealthGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 责任信息列表的初始化
function initDisDesbGrid()
  {                              
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="疾病症状";    	//列名
      iArray[1][1]="260px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="疾病结论";         		//列名
      iArray[2][1]="260px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ICDName";
      iArray[2][9]="疾病结论|len<=120";
      iArray[2][18]=300;

      iArray[3]=new Array();
      iArray[3][0]="ICD编码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="ICDCode";
      iArray[3][9]="ICD编码|len<=20";
      iArray[3][15]="ICDName";
      iArray[3][17]="2";
      iArray[3][18]=700;
     

      DisDesbGrid = new MulLineEnter( "fm" , "DisDesbGrid" ); 
      //这些属性必须在loadMulLine前                            
      DisDesbGrid.mulLineCount = 1;
      DisDesbGrid.displayTitle = 1;
      DisDesbGrid.canChk = 0;
      DisDesbGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //DisDesbGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tPrtSeq,tCustomerNo)
{
	fm.all('ContNO').value = tContNo;
  fm.all('MissionID').value = tMissionID;
	fm.all('SubMissionID').value = tSubMission ;
	fm.all('PrtNo').value = tPrtNo ;
	fm.all('PrtSeq').value = tPrtSeq;
	fm.all('CustomerNo').value = tCustomerNo;
	
  
}

</script>









