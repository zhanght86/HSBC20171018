<%@include file="../i18n/language.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">
function initInpBox()
{ 
  try
  {    //页面文本框的初始化（待补充）
	 fm.RiskCode.value = "";
	 fm.RiskCodeName.value = "";
	 fm.SaleChnl.value = "";
	 fm.SaleChnlName.value = "";
	 fm.ManageComGrp.value = "";
	 fm.ManageComGrpName.value = "";
	 fm.Currency.value = "";
	 fm.CurrencyName.value = "";
	 fm.ReRiskCode.value = "";
	 fm.ReRiskCodeName.value = "";
	 fm.ReSaleChnl.value = "";
     fm.ReSaleChnlName.value = "";
     fm.ReManageComGrp.value = "";
     fm.ReManageComGrpName.value = "";
     fm.ReCurrency.value = "";
     fm.ReCurrencyName.value = "";
     fm.LISStartDate.value = "";
     fm.LISEndDate.value = "";
     fm.PPLStartDate.value = "";
     fm.PPLEndDate.value = "";
    
		
	
    
  }
  catch(ex)
  {	
    myAlert("RiskSaleNameInit.jsp-->"+""+"InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initConfigCvalidateGrid();
    if("0" == "<%=request.getParameter("flag")%>"){
    easyQueryClick();
    }
  }
  catch(re)
  {
    myAlert("RiskSaleNameInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
  }
}

function initConfigCvalidateGrid()
  {                             
    var iArray = new Array();
    var i=0; 
      try
      {
     
      iArray[i]=new Array();
      iArray[i][0]="序号";         		     //列名
      iArray[i][1]="30px";            		//列宽
      iArray[i][2]=120;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[++i]=new Array();
      iArray[i][0]="险种编码";         		//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=120;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[++i]=new Array();
      iArray[i][0]="销售渠道 ";         		//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[++i]=new Array();
      iArray[i][0]="管理机构";         		//列名
      iArray[i][1]="120px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
            
      iArray[++i]=new Array();
      iArray[i][0]="币种";         	//列名
      iArray[i][1]="60px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
            
      iArray[++i]=new Array();
      iArray[i][0]="";         	//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//列名
      iArray[i][1]="80px";            		//列宽
      iArray[i][2]=100;            			//列最大值
      iArray[i][3]=0;              			//是否允许输入,1表示允许，0表示不允许    
            
      
      
     
      
      ConfigCvalidateGrid = new MulLineEnter( "fm" , "ConfigCvalidateGrid" ); 
      
      ConfigCvalidateGrid.mulLineCount = 4;//行属性：设置行数
      ConfigCvalidateGrid.displayTitle = 1;//1显示标题 (缺省值) ,0隐藏标题
      ConfigCvalidateGrid.hiddenPlus = 1;//不显示“+”号按钮
      ConfigCvalidateGrid.hiddenSubtraction = 1;//不显示“--”号按钮
      ConfigCvalidateGrid.canSel=1;//显示Radio 单选框
      ConfigCvalidateGrid.selBoxEventFuncName = "ShowSaleDate";//在MulLine中单击RadioBox时响应开发人员外部编写的JS函数  ShowGift是函数名
      ConfigCvalidateGrid.loadMulLine(iArray);//调用对象初始化方法，属性必须在此前设置
      
      }
      catch(ex)
      {
        myAlert("ConfigCvalidateInit.jsp-->"+""+"");
      }
}


</script>