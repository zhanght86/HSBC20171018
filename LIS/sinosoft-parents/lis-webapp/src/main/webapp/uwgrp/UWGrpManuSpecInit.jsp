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
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
  <%@page import="com.sinosoft.lis.cbcheckgrp.*"%>
                       
<%
  
  String tContNo = "";
  String tPolNo = "";
  
  tContNo = request.getParameter("ContNo");
  tPolNo = request.getParameter("PolNo");
 
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
    //fm.all('Prem').value = '';
    //fm.all('SumPrem').value = '';
    //fm.all('Mult').value = '';
    //fm.all('RiskAmnt').value = '';
    //fm.all('Remark').value = '';
    //fm.all('Reason').value = '';
    //fm.all('SpecReason').value = '';
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


function initForm(tContNo,tMissionID,tSubMission,tPrtNo)
{
  var str = "";
  try
  {
    initInpBox();
    initUWSpecGrid();
    initHide(tContNo,tMissionID,tSubMission,tPrtNo);
    QueryPolSpecGrid(tContNo);
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
      iArray[1][0]="个人合同号";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[2]=new Array();
      iArray[2][0]="投保单号";         		//列名
      iArray[2][1]="140px";            		//列宽
      iArray[2][2]=0;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="主险投保单号";         		//列名
      iArray[3][1]="0px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种编码";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=80;            			//列最大值
      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[4][5]="4";              	                //引用代码对应第几列，'|'为分割符
      iArray[4][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[4][18]=250;
      iArray[4][19]= 0 ;

      iArray[5]=new Array();
      iArray[5][0]="险种版本";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="投保单位";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="被保人";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      UWSpecGrid = new MulLineEnter( "fm" , "UWSpecGrid" ); 
      //这些属性必须在loadMulLine前
      UWSpecGrid.mulLineCount = 3;   
      UWSpecGrid.displayTitle = 1;
      //PolAddGrid.locked = 1;
      UWSpecGrid.canSel = 1;
      UWSpecGrid.hiddenPlus = 1;
      UWSpecGrid.loadMulLine(iArray);       
      UWSpecGrid.selBoxEventFuncName = "getPolGridCho";
     
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initHide(tContNo,tMissionID,tSubMission,tPrtNo,tPolNo)
{
	fm.all('ContNo').value=tContNo;
	
	fm.all('PolNo').value = tPolNo ;
	
}


</script>


