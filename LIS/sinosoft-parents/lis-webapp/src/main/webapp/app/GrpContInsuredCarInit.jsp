<%
//程序名称：GrpContInsuredCarInit.jsp
//程序功能：
//创建日期：2006-10-23 11:10:36
//创建人  ： chenrong
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() { 
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

// 隐藏框的初始化
function initHiddenBox()
{
    try
    {
        fm.ManageCom.value = nullToEmpty("<%=request.getParameter("ManageCom")%>");
        fm.PrtNo.value = nullToEmpty("<%=request.getParameter("prtNo")%>");
        fm.PolNo.value = nullToEmpty("<%=request.getParameter("polNo")%>");
        fm.ScanType.value = nullToEmpty("<%=request.getParameter("scantype")%>");
        fm.MissionID.value = nullToEmpty("<%=request.getParameter("MissionID")%>");
        fm.SubMissionID.value = nullToEmpty("<%=request.getParameter("SubMissionID")%>");
        fm.ActivityID.value = nullToEmpty("<%=request.getParameter("ActivityID")%>");
        fm.NoType.value = nullToEmpty("<%=request.getParameter("NoType")%>");
        fm.GrpContNo.value = nullToEmpty("<%=request.getParameter("GrpContNo")%>");
    	fm.ScanType.value = nullToEmpty("<%=request.getParameter("scantype")%>");    	    	
    	fm.LoadFlag.value = nullToEmpty("<%=request.getParameter("LoadFlag")%>");    	
    }
    catch(ex)
    {
        alert("GrpContInsuredCarInit.jsp-->initHiddenBox函数中发生异常:初始化界面错误!");
    }
}
                             

function initForm() {
    try {
        initHiddenBox();
        try{document.all('ProposalGrpContNo').value= mSwitch.getVar( "ProposalGrpContNo" ); }catch(ex){};     
        try{document.all('ManageCom').value= mSwitch.getVar( "ManageCom" ); }catch(ex){sdfsdfsdf};  
        initInsuredCarGrid();
        initCarPolGrid();
        if (fm.LoadFlag.value == "4" || fm.LoadFlag.value == "16")
        {
            divSaveCarButton.style.display="none"; 
            divNotSaveCarButton.style.display="";
        }
    }
    catch(re) {
    alert(re);
        alert("GrpContInsuredCarInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

// 领取项信息列表的初始化
var InsuredCarGrid;
function initInsuredCarGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][2]=10;
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="车牌号";         		//列名
    iArray[1][1]="100px";            		//列宽
    iArray[1][2]=20;
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许           

    iArray[2]=new Array();
    iArray[2][0]="座位数";      //列名
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=10;
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="架子号";                //列名
    iArray[3][1]="100px";            	//列宽
    iArray[3][2]=20;
    iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许

    iArray[4]=new Array();
    iArray[4][0]="被保人类别代码";         	//列名
    iArray[4][1]="0px";            	//列宽
    iArray[4][2]=20;
    iArray[4][3]=0;              	//是否允许输入,1表示允许，0表示不允许
    
    iArray[5]=new Array();
    iArray[5][0]="被保人类别";         	//列名
    iArray[5][1]="100px";            	//列宽
    iArray[5][2]=20;
    iArray[5][3]=0;              	//是否允许输入,1表示允许，0表示不允许
   
     
    InsuredCarGrid = new MulLineEnter( "fm" , "InsuredCarGrid" ); 
    //这些属性必须在loadMulLine前
    InsuredCarGrid.mulLineCount = 0;   
    InsuredCarGrid.displayTitle = 1;
    InsuredCarGrid.hiddenPlus = 1;
    InsuredCarGrid.hiddenSubtraction = 1;
    InsuredCarGrid.canSel = 1;
    InsuredCarGrid.selBoxEventFuncName ="queryPolInfo";
    InsuredCarGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}

function initCarPolGrid() {                               
  var iArray = new Array();
    
  try {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            	//列宽
    iArray[0][2]=10;
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="车牌号";         		//列名
    iArray[1][1]="80px";            		//列宽
    iArray[1][2]=20;
    iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许           

    iArray[2]=new Array();
    iArray[2][0]="险种编码";      //列名
    iArray[2][1]="80px";            		//列宽
    iArray[2][2]=10;
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="险种名称";                //列名
    iArray[3][1]="120px";            	//列宽
    iArray[3][2]=20;
    iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许

    
    iArray[4]=new Array();
    iArray[4][0]="保费（元）";         	//列名
    iArray[4][1]="80px";            	//列宽
    iArray[4][2]=10;
    iArray[4][3]=0;              	//是否允许输入,1表示允许，0表示不允许
        
    iArray[5]=new Array;
    iArray[5][0]="保额(元)";         	//列名
    iArray[5][1]="80px";            	//列宽
    iArray[5][2]=20;
    iArray[5][3]=0;              	//是否允许输入,1表示允许，0表示不允许

    CarPolGrid = new MulLineEnter( "fm" , "CarPolGrid" ); 
    //这些属性必须在loadMulLine前
    CarPolGrid.mulLineCount = 0;   
    CarPolGrid.displayTitle = 1;
    CarPolGrid.hiddenPlus = 1;
    CarPolGrid.hiddenSubtraction = 1;
    CarPolGrid.canSel = 0;
    //InsuredCarGrid.selBoxEventFuncName ="getdetail";
    CarPolGrid.loadMulLine(iArray);  
    
  }
  catch(ex) {
    alert(ex);
  }
}
</script>
