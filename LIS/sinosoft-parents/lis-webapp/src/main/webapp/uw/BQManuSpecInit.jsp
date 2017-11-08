<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：UWManuSpecInit.jsp
//程序功能：人工核保条件承保
//创建日期：2002-06-19 11:10:36
//创建人  ：WHN
//更新记录：  更新人    更新日期     更新原因/内容
%>

<!--用户校验类-->
 
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
  <%@page import="com.sinosoft.lis.cbcheck.*"%>
                       
<%
  String tPrtNo = "";
  String tContNo = "";
  String tMissionID = "";
  String tSubMissionID = "";
  String tEdorNo = "";
  String tEdorAcceptNo = "";
  String tEdorType = "";
  tPrtNo = request.getParameter("PrtNo");
  tContNo = request.getParameter("ContNo");
  tMissionID = request.getParameter("MissionID");
  tSubMissionID = request.getParameter("SubMissionID");
  String tInsuredNo = request.getParameter("InsuredNo");
  tEdorNo = request.getParameter("EdorNo");
  tEdorAcceptNo = request.getParameter("EdorAcceptNo");
  tEdorType = request.getParameter("EdorType");
%>      

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script language="JavaScript">

var str = "";

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
try
  {                                   
	// 延长日期天数
    //document.all('Prem').value = '';
    //document.all('SumPrem').value = '';
    //document.all('Mult').value = '';
    //document.all('RiskAmnt').value = '';
    //document.all('Remark').value = '';
    //document.all('Reason').value = '';
    //document.all('SpecReason').value = '';
  }
  catch(ex)
  {
    alert("在UWManuDateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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


function initForm(tContNo,tMissionID,tSubMission,tInsuredNo,tEdorNo,tEdorAcceptNo,tEdorType)
{
  var str = "";
  try
  {
    //initInpBox();
    initUWSpecGrid();
    initHide(tContNo,tMissionID,tSubMission,tInsuredNo,tEdorNo,tEdorAcceptNo,tEdorType);
    if(tQueryFlag=="1"){
      fm.button1.style.display="none";
      fm.button3.style.display="none";
      fm.button4.style.display="none";
      fm.button5.style.display="none";
	 }
    initpolno(tContNo,tInsuredNo,tEdorNo);
    QueryPolSpecGrid(tContNo,tEdorNo,tInsuredNo);
    initUWSpecContGrid();
    QueryPolSpecCont(tContNo,tEdorNo,tInsuredNo);
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initUWSpecGrid()
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
      iArray[1][0]="特约内容";         		//列名
      iArray[1][1]="430px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="流水号";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="状态代码";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="状态";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="保单号";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
      iArray[6]=new Array();
      iArray[6][0]="序列号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="下发状态标志";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="下发状态";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      UWSpecGrid = new MulLineEnter( "fm" , "UWSpecGrid" ); 
      //这些属性必须在loadMulLine前
      UWSpecGrid.mulLineCount = 3;   
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 1;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid.hiddenSubtraction = 1;
      UWSpecGrid.loadMulLine(iArray);       
      UWSpecGrid.selBoxEventFuncName = "getSpecGridCho2";
     
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


function initUWSpecContGrid()
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
      iArray[1][0]="特约内容";         		//列名
      iArray[1][1]="260px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

       

      UWSpecContGrid = new MulLineEnter( "fm" , "UWSpecContGrid" ); 
      //这些属性必须在loadMulLine前
      UWSpecContGrid.mulLineCount = 2;   
      UWSpecContGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecContGrid.canSel = 1;
      UWSpecContGrid.hiddenPlus = 1;
      UWSpecContGrid.hiddenSubtraction = 1;
      UWSpecContGrid.loadMulLine(iArray);       
      UWSpecContGrid.selBoxEventFuncName = "getSpecGridCho";
     
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
function initHide(tContNo,tMissionID,tSubMission,tInsuredNo,tEdorNo,tEdorAcceptNo,tEdorType)
{
	document.all('ContNo').value=tContNo;
	document.all('MissionID').value=tMissionID;
	document.all('SubMissionID').value=tSubMission;
	document.all('InsuredNo').value = tInsuredNo ;
	document.all('EdorNo').value=tEdorNo;
	document.all('EdorAcceptNo').value=tEdorAcceptNo;
	document.all('EdorType').value = tEdorType ;
}


</script>


