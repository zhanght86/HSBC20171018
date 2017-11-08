<% 
//程序名称：
//程序功能：保全查询详细信息

%>

<script language="JavaScript"> 
var turnPage = new turnPageClass();	
function initParam() 
{

   fm.EdorAcceptNo.value = nullToEmpty("<%= tEdorAcceptNo %>");  
 
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
	
//页面初始化
function initForm()
{	
	//alert("This is initForm!");
  try
  {	  
    initParam();    
    initEdorItemGrid();    
    initDiv();    
  }
  catch(re)
  {
    alert("PEdorInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//========================
function initEdorItemGrid()
{                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

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
        iArray[3][1]="120px";
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
        iArray[6][0]="团体保单号";
        iArray[6][1]="120px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="客户号码";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=3;
        
        iArray[8]=new Array();
        iArray[8][0]="保单险种号码";
        iArray[8][1]="100px";
        iArray[8][2]=100;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="保单险种名称";
        iArray[9][1]="140px";
        iArray[9][2]=100;
        iArray[9][3]=3;
                
        iArray[10]=new Array();
        iArray[10][0]="申请日期";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=0;
        
        iArray[11]=new Array();
        iArray[11][0]="生效日期";
        iArray[11][1]="80px";
        iArray[11][2]=100;
        iArray[11][3]=0;
        
        iArray[12]=new Array();
        iArray[12][0]="申请原因";
        iArray[12][1]="80px";
        iArray[12][2]=100;
        iArray[12][3]=0;
                 
        iArray[13]=new Array();
        iArray[13][0]="申请原因代码";
        iArray[13][1]="80px";
        iArray[13][2]=100;
        iArray[13][3]=3;
                 
        iArray[14]=new Array();
        iArray[14][0]="补退费金额";
        iArray[14][1]="100px";
        iArray[14][2]=100;
        iArray[14][3]=3;
                 
        iArray[15]=new Array();
        iArray[15][0]="生成时间";
        iArray[15][1]="80px";
        iArray[15][2]=100;
        iArray[15][3]=3;
                 
        iArray[16]=new Array();
        iArray[16][0]="生成具体时间";
        iArray[16][1]="0px";
        iArray[16][2]=100;
        iArray[16][3]=3;
                 
        iArray[17]=new Array();
        iArray[17][0]="最后保存时间";
        iArray[17][1]="80px";
        iArray[17][2]=100;
        iArray[17][3]=3;
                 
        iArray[18]=new Array();
        iArray[18][0]="最后处理人";
        iArray[18][1]="80px";
        iArray[18][2]=100;
        iArray[18][3]=3;
        
        iArray[19]=new Array();
        iArray[19][0]="处理状态";
        iArray[19][1]="80px";
        iArray[19][2]=100;
        iArray[19][3]=0;
        
        iArray[20]=new Array();
        iArray[20][0]="处理状态编码";
        iArray[20][1]="0px";
        iArray[20][2]=100;
        iArray[20][3]=3;
        
        iArray[21]=new Array();
        iArray[21][0]="保全批改类型";
        iArray[21][1]="0px";
        iArray[21][2]=100;
        iArray[21][3]=3;
        
        iArray[22]=new Array();
        iArray[22][0]="保全批改类型";
        iArray[22][1]="0px";
        iArray[22][2]=100;
        iArray[22][3]=3;

      EdorItemGrid = new MulLineEnter( "fm" , "EdorItemGrid" ); 
      
      //这些属性必须在loadMulLine前
      EdorItemGrid.mulLineCount = 1;   
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

</script>
