<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2002-07-19 16:49:22
//创建人  ：Tjj
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<%
  String CurrentDate = PubFun.getCurrentDate();
  Date dt = PubFun.calDate(new FDate().getDate(CurrentDate), 1, "D", null);
  String ValidDate = CurrentDate;   
  String dayAfterCurrent = new FDate().getString(dt);   
  String CurrentTime = PubFun.getCurrentTime();
%>                             

<script language="JavaScript">  
function initInpBox()
{ 

  try
  {        
    fm1.all('ContNo').value ='';
    document.all('ContNo').value = '';
    document.all('InsuredName').value = '';
    document.all('AppntName').value = '';
 
   
    document.all('ValiDate').value = '';
    document.all('PaytoDate').value = '';
    document.all('Prem').value = '';

    document.all('GetPolDate').value = '';
    document.all('AgentCode').value = '';
    
   document.all('EdorType').value = '';
   document.all('EdorValiDate').value = '<%=CurrentDate%>';
   //document.all('ChgPrem').value = '';
   document.all('EdorNo').value='';
   //document.all('ChgAmnt').value = '';
   document.all('Operator').value = "<%=tG.Operator%>";
   document.all('EdorAppDate').value = '<%=CurrentDate%>';
   document.all('ContType').value ='1';
   document.all('currentDay').value = '<%=CurrentDate%>';
   document.all('dayAfterCurrent').value = '<%=dayAfterCurrent%>';
  }
  catch(ex)
  {
    alert("在PEdorInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!"+ex);
  }      
}

function initEdorItemGrid()
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
        iArray[1][0]="保全受理号";
        iArray[1][1]="0px";
        iArray[1][2]=100;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="批单号";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="批改类型";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="批改类型显示级别";
        iArray[4][1]="0px";
        iArray[4][2]=100;
        iArray[4][3]=3;        
        
        iArray[5]=new Array();
        iArray[5][0]="集体合同号码";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=3;
        
        iArray[6]=new Array();
        iArray[6][0]="合同号码";
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="被保人客户号码";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
        iArray[8][0]="保单险种号码";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=0;
        
        iArray[9]=new Array();
        iArray[9][0]="批改生效日期";
        iArray[9][1]="100px";
        iArray[9][2]=100;
        iArray[9][3]=0;
        
        iArray[10]=new Array();
        iArray[10][0]="补退费金额";
        iArray[10][1]="100px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        
      

      
      EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" ); 
      //这些属性必须在loadMulLine前
      EdorItemGrid.mulLineCount = 0;   
      EdorItemGrid.displayTitle = 1;
      EdorItemGrid.canSel =1;
      EdorItemGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //点击RadioBox时响应的JS函数
      EdorItemGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      EdorItemGrid.hiddenSubtraction=1;
      EdorItemGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}


function initInusredGrid()

{                               
    alert("in ");
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="被保险人号码";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;
        
        iArray[2]=new Array();
        iArray[2][0]="被保险人姓名";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
        iArray[3][0]="性别";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="出生日期";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=3;        
        
        iArray[5]=new Array();
        iArray[5][0]="被保险人证件号码";
        iArray[5][1]="80px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="与主被保险人关系";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
        //这些属性必须在loadMulLine前
        InsuredGrid.mulLineCount = 0;   
        InsuredGrid.displayTitle = 1;
        if (fm.DisplayType.value=='2')
        {
          InsuredGrid.canChk=1;
          InsuredGrid.canSel =0;
          InsuredGrid.selBoxEventFuncName ="getEdorItemDetail" ;     //点击RadioBox时响应的JS函数
        }
        else
        {
          InsuredGrid.canChk=0;
          InsuredGrid.canSel =1;
        }
        InsuredGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        InsuredGrid.hiddenSubtraction=1;
        InsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initPolGrid()
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
        iArray[1][0]="保单险种号码";
        iArray[1][1]="0px";
        iArray[1][2]=100;
        iArray[1][3]=3;
        
        iArray[2]=new Array();
        iArray[2][0]="险种代码";
        iArray[2][1]="0px";
        iArray[2][2]=100;
        iArray[2][3]=3;
        
        iArray[3]=new Array();
        iArray[3][0]="险种名称";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        
        iArray[4]=new Array();
        iArray[4][0]="被保险人号码";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="实际保费";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=3;        
        
        iArray[6]=new Array();
        iArray[6][0]="实际保额"
        iArray[6][1]="0px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array();
        iArray[7][0]="生效日期";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=3;
        
        PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canChk=1;
      PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}
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
    alert("在PEdorInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initFormFirst()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initDiv();  
    addClick();
    
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 
    initDiv();
    initEdorItemGrid();  
    
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initDiv()
{
//	divedortype.style.display ='none';
	divappconfirm.style.display ='none';
	showDiv(operateButton,"true"); 
    showDiv(inputButton,"false"); 
//	divdetail.style.display = 'none';
}
</script>