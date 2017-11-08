<%
//程序名称：ChannelQueryInit.jsp
//程序功能：工资历史查询显示页面
//创建日期：2005-10-27
//创建人  ：万泽辉
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">


//工资历史信息列表的初始化
function initLAwageForm()
{
  try
  { 
    initLAwageGrid();
    LAwageClick();
    
  }
  catch(re)
  {
    alert("initLAwageForm函数中发生异常:初始化界面错误! "+re.message);
  }
}

// 业务员基本信息列表的初始化
function initLAAgentForm()
{
  try
  {
    initLAAgentGrid();
    LAAgentClick();
  }
  catch(re)
  {
    alert("initLAAgentForm函数中发生异常:初始化界面错误!"+re.message);
  }
}

// 业务员历史信息列表的初始化
function initLAAgentBForm()
{
  try
  {
    initLAAgentBGrid();
    LAAgentBClick();
  }
  catch(re)
  {
    alert("initLAAgentBForm函数中发生异常:初始化界面错误!"+re.message);
  }
}

// 考核历史查询列表的初始化
function initLAAssessAccessoryForm()
{
  try
  {
    initLAAssessAccessoryGrid();
    LAAssessAccessoryClick();
  }
  catch(re)
  {
    alert("initLAAssessAccessoryForm函数中发生异常:初始化界面错误!"+re.message);
  }
}

// 代理人血缘关系查询列表的初始化
function initLARearRelationForm()
{
  try
  {
    initLARearRelationGrid();
    LARearRelationClick();
  }
  catch(re)
  {
    alert("initLARearRelationForm函数中发生异常:初始化界面错误!"+re.message);
  }
}

// 福利历史查询列表的初始化
function initLAwageWelareForm()
{
  try
  {
    initLAwageWelareGrid();
    LAwageWelareClick();
  }
  catch(re)
  {
    alert("initLAwageWelareForm函数中发生异常:初始化界面错误!"+re.message);
  }
}

// 组织关系列表的初始化
function initAgentForm()
{
  try
  {
    initAgentGrid();
    AgentClick();
  }
  catch(re)
  {
    alert("initAgentForm函数中发生异常:初始化界面错误!"+re.message);
  }
}

var LAwageGrid; 
// 工资历史信息列表的初始化
function initLAwageGrid()
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
      iArray[1][0]="指标计算编码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="代理人编码";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
           
      iArray[3]=new Array();
      iArray[3][0]="代理人姓名";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="代理人组别";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="实发(税后薪资)";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="上期余额";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[7]=new Array();
      iArray[7][0]="应发(税前薪资)";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0; 
        
      
      LAwageGrid = new MulLineEnter( "fm" , "LAwageGrid" ); 
      //这些属性必须在loadMulLine前
      LAwageGrid.mulLineCount = 0;   
      LAwageGrid.displayTitle = 1;
      LAwageGrid.locked = 1;
      LAwageGrid.canSel = 0;
 
      LAwageGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LAwageGrid.hiddenSubtraction=1;
      LAwageGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

//业务员基本信息列表的初始化
var LAAgentGrid;
function initLAAgentGrid()
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
      iArray[1][0]="代理人编码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="代理人展业机构代码";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
           
      iArray[3]=new Array();
      iArray[3][0]="姓名";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="性别";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="管理机构";         		//列名
      iArray[5][1]="90px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="代理人级别";         		//列名
      iArray[6][1]="90px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[7]=new Array();
      iArray[7][0]="手机";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="E_Mail";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[9]=new Array();
      iArray[9][0]="身份证号码";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      LAAgentGrid = new MulLineEnter( "fm1" , "LAAgentGrid" ); 
      //这些属性必须在loadMulLine前
      LAAgentGrid.mulLineCount = 0;   
      LAAgentGrid.displayTitle = 1;
      LAAgentGrid.locked = 1;
      LAAgentGrid.canSel = 0;
 
      LAAgentGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LAAgentGrid.hiddenSubtraction=1;
      LAAgentGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

//业务员基本信息列表的初始化
var LAAgentBGrid;
function initLAAgentBGrid()
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
      iArray[1][0]="转储号码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="展业类型";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
           
      iArray[3]=new Array();
      iArray[3][0]="代理人编码";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="新代理人编码";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="姓名";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="性别";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[7]=new Array();
      iArray[7][0]="代理人展业机构代码";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="手机";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[9]=new Array();
      iArray[9][0]="身份证号码";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      LAAgentBGrid = new MulLineEnter( "fm" , "LAAgentBGrid" ); 
      //这些属性必须在loadMulLine前
      LAAgentBGrid.mulLineCount = 0;   
      LAAgentBGrid.displayTitle = 1;
      LAAgentBGrid.locked = 1;
      LAAgentBGrid.canSel = 0;
 
      LAAgentBGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LAAgentBGrid.hiddenSubtraction=1;
      LAAgentBGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

//业务员基本信息列表的初始化
var LAAssessAccessoryGrid;
function initLAAssessAccessoryGrid()
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
      iArray[1][0]="指标计算编码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="考核对象编码";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
           
      iArray[3]=new Array();
      iArray[3][0]="姓名";         		//列名
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="性别";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="管理机构";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="原代理人级别";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[7]=new Array();
      iArray[7][0]="新代理人级别";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0; 
        
      
      LAAssessAccessoryGrid = new MulLineEnter( "fm" , "LAAssessAccessoryGrid" ); 
      //这些属性必须在loadMulLine前
      LAAssessAccessoryGrid.mulLineCount = 0;   
      LAAssessAccessoryGrid.displayTitle = 1;
      LAAssessAccessoryGrid.locked = 1;
      LAAssessAccessoryGrid.canSel = 0;
 
      LAAssessAccessoryGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LAAssessAccessoryGrid.hiddenSubtraction=1;
      LAAssessAccessoryGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}


//业务员基本信息列表的初始化
var LARearRelationGrid;
function initLARearRelationGrid()
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
      iArray[1][0]="育成级别";         		//列名
      iArray[1][1]="80px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="育成代数目";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
           
      iArray[3]=new Array();
      iArray[3][0]="被育成人代码";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="被育成人姓名";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="育成人代码";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="育成人姓名";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[7]=new Array();
      iArray[7][0]="被育成机构";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="状态";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0; 
       
      LARearRelationGrid = new MulLineEnter( "fm" , "LARearRelationGrid" ); 
      //这些属性必须在loadMulLine前
      LARearRelationGrid.mulLineCount = 0;   
      LARearRelationGrid.displayTitle = 1;
      LARearRelationGrid.locked = 1;
      LARearRelationGrid.canSel = 0;
 
      LARearRelationGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LARearRelationGrid.hiddenSubtraction=1;
      LARearRelationGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}


// 福利历史信息列表的初始化
var LAwageWelareGrid; 

function initLAwageWelareGrid()
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
      iArray[1][0]="指标计算编码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="代理人编码";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
           
      iArray[3]=new Array();
      iArray[3][0]="代理人组别";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="姓名";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="养老保险";         		//列名
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="失业保险";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[7]=new Array();
      iArray[7][0]="医疗保险";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0; 
        
      iArray[8]=new Array();
      iArray[8][0]="住房公积金";         		//列名
      iArray[8][1]="60px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[9]=new Array();
      iArray[9][0]="生育保险";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      iArray[10]=new Array();
      iArray[10][0]="退休金";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许  
      
      
      LAwageWelareGrid = new MulLineEnter( "fm" , "LAwageWelareGrid" ); 
      //这些属性必须在loadMulLine前
      LAwageWelareGrid.mulLineCount = 0;   
      LAwageWelareGrid.displayTitle = 1;
      LAwageWelareGrid.locked = 1;
      LAwageWelareGrid.canSel = 0;
 
      LAwageWelareGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LAwageWelareGrid.hiddenSubtraction=1;
      LAwageWelareGrid.loadMulLine(iArray);
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

var AgentGrid; 
// 组织关系列表的初始化
function initAgentGrid()
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
      iArray[1][0]="销售机构编码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="90px";            		//列宽
      iArray[1][2]=10;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="营业组";         		//列名
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="代理人姓名";         		//列名
      iArray[3][1]="70px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="代理人编码";         		//列名
      iArray[4][1]="80px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="职级";         		//列名
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="入司时间";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 
      //这些属性必须在loadMulLine前
      AgentGrid.mulLineCount = 0;   
      AgentGrid.displayTitle = 1;
      AgentGrid.locked = 1;
      AgentGrid.canSel = 0;
      AgentGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      AgentGrid.hiddenSubtraction=1;
      AgentGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>     
      
      
      
      
      
      
      
      
      


      
