<%
//程序名称：UserMissionInit.jsp
//程序功能：
//创建日期：2011-05-19
//创建人  ：sunsheng
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%@include file="../common/jsp/UsrCheck.jsp"%>

<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">                                                                        
function initForm()
{
  try
  {  
    //initPersonalWorkPool();
    //initOneselfGrpGrid();
    //initPublicGrpGrid(); 
   // easyQueryClickOneself();
    //easyQueryClickPublic();
    console.log(_DBT);
  }
  catch(re)
  {
    alert("出错啦! "+re);
  }
}
//function initSelfGrpGrid()
//{                               
//var iArray = new Array();
//  
//  try
//  {
//  iArray[0]=new Array();
//  iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
//  iArray[0][1]="30px";            		//列宽
//  iArray[0][2]=10;            			//列最大值
//  iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//
//  iArray[1]=new Array();
//  iArray[1][0]="任务名称";         		//列名
//  iArray[1][1]="150px";            		//列宽
//  iArray[1][2]=100;            			//列最大值
//  iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//
//  iArray[2]=new Array();
//  iArray[2][0]="个人待处理任务数";         		//列名
//  iArray[2][1]="150px";            		//列宽
//  iArray[2][2]=100;            			//列最大值
//  iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//
//  iArray[3]=new Array();
//  iArray[3][0]="公共待处理任务数";         		//列名
//  iArray[3][1]="150px";            		//列宽
//  iArray[3][2]=200;            			//列最大值
//  iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//
//  iArray[4]=new Array();
//  iArray[4][0]="URL";         		//列名
//  iArray[4][1]="0px";            		//列宽
//  iArray[4][2]=200;            			//列最大值
//  iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//  
//  iArray[5]=new Array();
//  iArray[5][0]="nodecode";         		//列名
//  iArray[5][1]="0px";            		//列宽
//  iArray[5][2]=20;            			//列最大值
//  iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//  
//  iArray[6]=new Array();
//  iArray[6][0]="parentnode";         	//列名
//  iArray[6][1]="0px";            		//列宽
//  iArray[6][2]=20;            			//列最大值
//  iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
//  
//  SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
//  SelfGrpGrid.mulLineCount = 5;   
//  SelfGrpGrid.displayTitle = 1;
//  SelfGrpGrid.locked = 1;
//  SelfGrpGrid.canSel = 1;
//  SelfGrpGrid.hiddenPlus = 1;
//  SelfGrpGrid.hiddenSubtraction = 1;
//  SelfGrpGrid.selBoxEventFuncName = "initMenu";
//  SelfGrpGrid.loadMulLine(iArray);  
//  
//  }
//  catch(ex)
//  {
//    alert(ex);
//  }
//}

//个人工作池
function initOneselfGrpGrid()
{                               
  var iArray = new Array();
    
    try
    {
    iArray[0]=new Array();
    iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";            		//列宽
    iArray[0][2]=100;            			//列最大值
    iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="processid";         		//列名
    iArray[1][1]="80px";            		//列宽
    iArray[1][2]=100;            			//列最大值
    iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[2]=new Array();
    iArray[2][0]="业务类型";         		//列名
    iArray[2][1]="200px";            		//列宽
    iArray[2][2]=100;            			//列最大值
    iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

    iArray[3]=new Array();
    iArray[3][0]="ActivityID";         		//列名
    iArray[3][1]="80px";            		//列宽
    iArray[3][2]=100;            			//列最大值
    iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[4]=new Array();
    iArray[4][0]="功能名称";         		//列名
    iArray[4][1]="200px";            		//列宽
    iArray[4][2]=100;            			//列最大值
    iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


    iArray[5]=new Array();
    iArray[5][0]="业务号码";         		//列名
    iArray[5][1]="200px";            		//列宽
    iArray[5][2]=100;            			//列最大值
    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[6]=new Array();                                                 
    iArray[6][0]="优先级别";         		//列名                             
    iArray[6][1]="150px";            		//列宽                               
    iArray[6][2]=100;            			//列最大值                           
    iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[7]=new Array();
    iArray[7][0]="优先级别名称";         		//列名
    iArray[7][1]="200px";            		//列宽
    iArray[7][2]=100;            			//列最大值
    iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[8]=new Array();
    iArray[8][0]="颜色ID";         		//列名
    iArray[8][1]="80px";            		//列宽
    iArray[8][2]=200;            			//列最大值
    iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[9]=new Array();
    iArray[9][0]="MissionID";         		//列名
    iArray[9][1]="80px";            		//列宽
    iArray[9][2]=100;            			//列最大值
    iArray[9][3]=3;              			//是否允许输入,1表示允许，0表示不允许
    
    iArray[10]=new Array();
    iArray[10][0]="MissionID";         		//列名
    iArray[10][1]="80px";            		//列宽
    iArray[10][2]=100;            			//列最大值
    iArray[10][3]=3;              			//是否允许输入,1表示允许，0表示不允许
                                                                           
    OneselfGrpGrid = new MulLineEnter( "fm" , "OneselfGrpGrid" ); 
    OneselfGrpGrid.mulLineCount = 1;   
    OneselfGrpGrid.displayTitle = 1;
    OneselfGrpGrid.locked = 1;
    OneselfGrpGrid.canSel = 1;
    OneselfGrpGrid.hiddenPlus = 1;
    OneselfGrpGrid.hiddenSubtraction = 1;
    OneselfGrpGrid.selBoxEventFuncName = "QueryMenuInitOneSelf";
    OneselfGrpGrid.loadMulLine(iArray); 
    OneselfGrpGrid.colorNum = 8;
    } 
    catch(ex)
    {
      alert(ex);
    }
}
//公共工作池
function initPublicGrpGrid()
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
        iArray[1][0]="processid";         		//列名
        iArray[1][1]="150px";            		//列宽
        iArray[1][2]=100;            			//列最大值
        iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[2]=new Array();
        iArray[2][0]="业务类型";         		//列名
        iArray[2][1]="200px";            		//列宽
        iArray[2][2]=100;            			//列最大值
        iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[3]=new Array();
        iArray[3][0]="ActivityID";         		//列名
        iArray[3][1]="100px";            		//列宽
        iArray[3][2]=100;            			//列最大值
        iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[4]=new Array();
        iArray[4][0]="任务名称";         		//列名
        iArray[4][1]="200px";            		//列宽
        iArray[4][2]=100;            			//列最大值
        iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


        iArray[5]=new Array();
        iArray[5][0]="优先级别";         		//列名
        iArray[5][1]="150px";            		//列宽
        iArray[5][2]=200;            			//列最大值
        iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        
        iArray[6]=new Array();                                                 
        iArray[6][0]="优先级别名称";         		//列名                             
        iArray[6][1]="200px";            		//列宽                               
        iArray[6][2]=200;            			//列最大值                           
        iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        
        iArray[7]=new Array();                                                 
        iArray[7][0]="颜色ID";         		//列名                             
        iArray[7][1]="150px";            		//列宽                               
        iArray[7][2]=200;            			//列最大值                           
        iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[8]=new Array();                                                 
        iArray[8][0]="任务件数";         		//列名                             
        iArray[8][1]="150px";            		//列宽                               
        iArray[8][2]=200;            			//列最大值                           
        iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
                                                                         
        PublicGrpGrid = new MulLineEnter( "fm" , "PublicGrpGrid" ); 
        PublicGrpGrid.mulLineCount = 0;   
        PublicGrpGrid.displayTitle = 1;
        PublicGrpGrid.locked = 1;
        PublicGrpGrid.canSel = 1;
        PublicGrpGrid.hiddenPlus = 1;
        PublicGrpGrid.hiddenSubtraction = 1;
        PublicGrpGrid.selBoxEventFuncName = "QueryMenuInitPublic";
        PublicGrpGrid.loadMulLine(iArray);
        PublicGrpGrid.colorNum = 7;
    }
    catch(ex)
    {
      alert(ex);
    }
}
</script>
