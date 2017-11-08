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


function initForm()
{
  var str = "";
  try
  {
  	//alert("tInsuredNo:"+tInsuredNo);
    //alert('1');
   
    initHide();
    initGrpGrid();
   // easyQueryClick();
    initBtnDisable();
  }
  catch(re)
  {
    alert("UWSubInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initBtnDisable(){
 	var tteFlag = "<%=request.getParameter("teFlag")%>";
  //  alert(tteFlag);
	if(tteFlag != null && tteFlag == "1"){
   	 	document.getElementById("showBtn").style.display = "none";
   	 	easyQueryClick1();
    }else{
		document.getElementById("showBtn").style.display = "";  
		easyQueryClick(); 	 	
    }
}

function initHide(){
	fm.PrtSeq.value = tPrtSeq;
	fm.ContNo.value = tContNo;
	fm.MissionID.value = tMissionID;
	fm.SubMissionID.value = tSubMissionID;
	fm.ActivityID.value = tActivityID;
	fm.EdorAcceptNo.value = tEdorAcceptNo;
	fm.EdorNo.value = tEdorNo;
	fm.EdorType.value = tEdorType;
	initUWIdea();
}

function initGrpGrid()
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
      iArray[1][0]="保全受理号";         	//列名
      iArray[1][1]="120px";            	//列宽
      iArray[1][2]=170;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批改类型";         	//列名
      iArray[2][1]="80px";            	//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="保单号";         	//列名
      iArray[3][1]="80px";            	//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[4]=new Array();
      iArray[4][0]="打印流水号";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[5]=new Array();
      iArray[5][0]="管理机构";         		//列名
      iArray[5][1]="140px";            		//列宽
      iArray[5][2]=60;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许    

      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";      //列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";    //列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";      //列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许   

	  iArray[9]=new Array();
      iArray[9][0]="客户是否同意";      //列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=2;
      iArray[9][10]="ForYN";              			//是否允许输入,1表示允许，0表示不允许   
      iArray[9][11]="0|^0|是 ^1|否";    //下拉选择
      
      iArray[10]=new Array();
      iArray[10][0]="批单号";    //列名
      iArray[10][1]="0px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 


      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //这些属性必须在loadMulLine前
      GrpGrid.mulLineCount =0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;        
      GrpGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
