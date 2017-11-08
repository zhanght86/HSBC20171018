<%
//程序名称：GrpPolQueryInit.jsp
//程序功能：
//创建日期：2003-03-14 
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

//返回按钮初始化
var str = "";
function initDisplayButton()
{
	//alert("Dis:" + tDisplay);
 	//if(tDisplay != null && tDisplay != "")
 	//{	
 	//  fm.Return.style.display='none';
 	//  return;
 	//}
 	
 	//团体保全查询会复用该界面，并传递参数4
 	if (tDisplay=="4")
	{
		fm.Return.style.display='';
	}
	else if (tDisplay=="1")
	{
		fm.Return.style.display='';
	}
	else if (tDisplay=="0")
	{
		fm.Return.style.display='none';
	}
	else if (tDisplay=="3")
	{
		return;
	}
	
}

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    document.all('PrtNo').value = '';
    document.all('GrpContNo').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
     
  }
  catch(ex)
  {
    alert("在GroupPolQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在GroupPolQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
  
    initInpBox();
    
    initSelBox(); 
    initGrpPolGrid();
  
    initDisplayButton();
  }
  catch(re)
  {
    alert("GroupPolQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 保单信息列表的初始化
function initGrpPolGrid()
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
      iArray[1][0]="团体合同号";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
    if(flag=="1"){
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    }else{
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    }

      iArray[2]=new Array();
      iArray[2][0]="结算批次号";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      iArray[3]=new Array();
      iArray[3][0]="投保人名称";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="生效日期";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="承保人数";         		//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="总保费";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="总保额";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      
      iArray[8]=new Array();
      iArray[8][0]="承保机构";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0; 

      iArray[9]=new Array();
      iArray[9][0]="保单状态";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0; 
    
      GrpPolGrid = new MulLineEnter( "fm" , "GrpPolGrid" ); 
      //这些属性必须在loadMulLine前
      GrpPolGrid.mulLineCount = 10;   
      GrpPolGrid.displayTitle = 1;
      GrpPolGrid.locked = 1;
      GrpPolGrid.canSel = 1;
      GrpPolGrid.hiddenPlus = 1;
      GrpPolGrid.hiddenSubtraction = 1;
      GrpPolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //GrpPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
