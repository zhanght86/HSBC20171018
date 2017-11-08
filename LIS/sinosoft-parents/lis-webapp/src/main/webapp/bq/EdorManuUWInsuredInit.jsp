<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：EdorManuUWInsuredInit.jsp
//程序功能：人工核保被保人信息
//创建日期：2005-06-04 18:32:36
//创建人  ：liurongxiao
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput tglobalInput = (GlobalInput)session.getValue("GI");
	
	if(tglobalInput == null) {
		out.println("session has expired");
		return;
	}
	
	String strOperator = tglobalInput.Operator;
	
%>                            

<script language="JavaScript">  
//初始化页面元素
function initParam()
{
   document.all('ContNo').value = nullToEmpty("<%= tContNo %>");
   document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
   document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
   document.all('EdorNo').value = nullToEmpty("<%= tEdorNo %>");
   document.all('PrtNo').value = nullToEmpty("<%= tPrtNo %>");
   document.all('InsuredNo').value = nullToEmpty("<%=tInsuredNo%>");
   document.all('EdorAcceptNo').value = nullToEmpty("<%= tEdorAcceptNo %>");
   document.all('EdorType').value = nullToEmpty("<%= tEdorType %>"); //alert("123"+nullToEmpty("<%= tEdorType %>"));
   document.all('OtherNo').value = nullToEmpty("<%= tOtherNo %>");
   document.all('OtherNoType').value = nullToEmpty("<%= tOtherNoType %>");
   document.all('EdorAppName').value = nullToEmpty("<%= tEdorAppName %>");
   document.all('Apptype').value = nullToEmpty("<%= tApptype %>");
   document.all('ManageCom').value = nullToEmpty("<%= tManageCom %>");
   document.all('UWType').value = "EdorItem";
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}
//初始化页面元素
function initInpBox()
{ 

  try
  {
  	
  	document.all('EdorUWState').value = "";
  	document.all('EdorUWStateName').value = "";
	document.all('UWDelay').value = "";
	document.all('UWPopedomCode').value = "";
	document.all('UWIdea').value = "";

  	

  }
  catch(ex)
  {
    alert("在EdorManuUWInsuredInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!"+ex);
  }      
}
function initForm()
{  
  try
  {  
    
    initInpBox();
    initParam();
    initRiskGrid();
    initInsuredRelatedGrid(); 
    
    queryInsuredInfo();
    
    queryRiskInfo();
	//alert("EDORTYPE=="+fm.EdorType.value);
   
  }
  catch(re)
  {
    alert("在EdorManuUWInsuredInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 险种信息列表的初始化
function initRiskGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="批单号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批改类型";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[3]=new Array();
      iArray[3][0]="保单险种号码";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="主险保单号码";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="险种编码";         		//列名
      iArray[5][1]="70px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="险种名称";         		//列名
      iArray[6][1]="140px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[7]=new Array();
      iArray[7][0]="保费";         		//列名
      iArray[7][1]="70px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="保额";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[9]=new Array();
      iArray[9][0]="保险起期";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="保险止期";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[11]=new Array();
      iArray[11][0]="交费间隔(月)";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="交费年期";         		//列名
      iArray[12][1]="60px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许     
      
       
      
      iArray[13]=new Array();
	  iArray[13][0]="核保状态";         	//列名
	  iArray[13][1]="80px";            		//列宽
	  iArray[13][2]=80;            			//列最大值
	  iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[14]=new Array();
	  iArray[14][0]="核保状态编码";         		//列名
	  iArray[14][1]="0px";            		//列宽
	  iArray[14][2]=100;            			//列最大值
	  iArray[14][3]=3;              			//是否允许输入,1表示允许，0表示不允许
           
      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //这些属性必须在loadMulLine前
      RiskGrid.mulLineCount = 3;   
      RiskGrid.displayTitle = 1;
      RiskGrid.locked = 1;
      RiskGrid.canSel = 1;
      RiskGrid.hiddenPlus = 1;
      RiskGrid.hiddenSubtraction = 1;
      RiskGrid.loadMulLine(iArray);     
      
      RiskGrid.selBoxEventFuncName = "getRiskInfo";
      
      //这些操作必须在loadMulLine后面
      //RiskGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
// 连生被保人信息列表的初始化
function initInsuredRelatedGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="名称";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="性别";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="出生日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[5]=new Array();
      iArray[5][0]="证件类型";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="证件号码";         		//列名
      iArray[6][1]="140px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="主被保人客户号";         		//列名
      iArray[7][1]="140px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="与主被保人关系";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
           
           
      InsuredRelatedGrid = new MulLineEnter( "fm" , "InsuredRelatedGrid" ); 
      //这些属性必须在loadMulLine前
      InsuredRelatedGrid.mulLineCount = 3;   
      InsuredRelatedGrid.displayTitle = 1;
      InsuredRelatedGrid.locked = 1;
      InsuredRelatedGrid.canSel = 0;
      InsuredRelatedGrid.hiddenPlus = 1;
      InsuredRelatedGrid.hiddenSubtraction = 1;
      InsuredRelatedGrid.loadMulLine(iArray);     
      
      //InsuredRelatedGrid. selBoxEventFuncName = "getInsuredRelatedInfo";
      
      //这些操作必须在loadMulLine后面
      //InsuredRelatedGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
