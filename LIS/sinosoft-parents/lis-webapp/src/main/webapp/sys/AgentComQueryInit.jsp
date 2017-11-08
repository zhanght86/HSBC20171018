<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {    
    document.all('AgentCom').value = '';
    document.all('Name').value = '';
    document.all('ManageCom').value = '';
    document.all('Address').value = ''; 
    document.all('ZipCode').value = '';
    document.all('Phone').value = ''; 
    if(ManageCom != null && ManageCom !="")
    {
    	document.all('ManageCom').value = ManageCom;
    }
  }
  catch(ex)
  {
    alert("在AgentQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");       
  }
  catch(ex)
  {
    alert("在AgentQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initAgentGrid();
  }
  catch(re)
  {
    alert("AgentQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initAgentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="30px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

        iArray[1]=new Array();
        iArray[1][0]="代理机构编码";         //列名
        iArray[1][1]="80px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="管理机构";         //列名
        iArray[2][1]="80px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="代理机构名称";         //列名
        iArray[3][1]="80px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[4]=new Array();
        iArray[4][0]="机构注册地址";         //列名
        iArray[4][1]="100px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[5]=new Array();
        iArray[5][0]="机构邮编";         //列名
        iArray[5][1]="80px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[6]=new Array();
        iArray[6][0]="机构电话";         //列名
        iArray[6][1]="80px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
  
        AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 
        //这些属性必须在loadMulLine前
        AgentGrid.mulLineCount = 5;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
        AgentGrid.locked=1;
	    AgentGrid.hiddenPlus=1;
	    AgentGrid.hiddenSubtraction=1;
        AgentGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert("初始化AgentGrid时出错："+ ex);
      }
    }

</script>
