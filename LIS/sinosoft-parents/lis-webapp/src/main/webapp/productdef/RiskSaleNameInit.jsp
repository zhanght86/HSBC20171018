<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">
function initInpBox()
{ 
  try
  {    //页面文本框的初始化（待补充）
	 
	 if("0" == "<%=request.getParameter("flag")%>"){
		 fm.RiskCode.value = "<%=request.getParameter("riskcode")%>";
		}else{
			fm.RiskCode.value = "";
			fm.RiskCodeName.value = "";
		}
     
     fm.SaleChnl.value = "";
     fm.SaleChnlName.value = "";
     fm.ManageCom.value = "";
     fm.ManageComName.value = "";
     fm.RiskNameCn.value = "";
     fm.RiskNameEn.value = "";
     fm.RiskNameShort.value = "";
     fm.RiskNameTr.value = "";
    
		
	
    
  }
  catch(ex)
  {	
    myAlert("RiskSaleNameInit.jsp-->"+""+"InitInpBox�����з����쳣:��ʼ���������!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    initMullineRiskSaleNameGrid();
    if("0" == "<%=request.getParameter("flag")%>"){
    easyQueryClick();
    }
  }
  catch(re)
  {
    myAlert("RiskSaleNameInit.jsp-->"+"InitForm�����з����쳣:��ʼ���������!");
  }
}

function initMullineRiskSaleNameGrid()
  {                             
    var iArray = new Array();
    var i=0; 
      try
      {
     
      iArray[i]=new Array();
      iArray[i][0]="���";         		     //列名
      iArray[i][1]="30px";            		//列宽
      iArray[i][2]=120;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?

      iArray[++i]=new Array();
      iArray[i][0]="���ֱ���";         		//列名
      iArray[i][1]="100px";            		//列宽
      iArray[i][2]=120;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?   
      
      iArray[++i]=new Array();
      iArray[i][0]="�������� ";         		//列名
      iArray[i][1]="100px";            		//列宽
      iArray[i][2]=100;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?   
      
      iArray[++i]=new Array();
      iArray[i][0]="�������";         		//列名
      iArray[i][1]="100px";            		//列宽
      iArray[i][2]=100;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?   
            
      iArray[++i]=new Array();
      iArray[i][0]="";         	//列名
      iArray[i][1]="100px";            		//列宽
      iArray[i][2]=100;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?   
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//列名
      iArray[i][1]="0px";            		//列宽
      iArray[i][2]=100;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?   
            
      iArray[++i]=new Array();
      iArray[i][0]="";         		//列名
      iArray[i][1]="0px";            		//列宽
      iArray[i][2]=100;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?
      
      iArray[++i]=new Array();
      iArray[i][0]="";         		//列名
      iArray[i][1]="0px";            		//列宽
      iArray[i][2]=100;            			//列最大�?
      iArray[i][3]=0;              			//是否允许输入,1表示允许�?表示不允�?
                 			
      
     
      
      MullineRiskSaleNameGrid = new MulLineEnter( "fm" , "MullineRiskSaleNameGrid" ); 
      
      MullineRiskSaleNameGrid.mulLineCount = 4;//行属性：设置行数
      MullineRiskSaleNameGrid.displayTitle = 1;//1显示标题 (缺省�? ,0隐藏标题
      MullineRiskSaleNameGrid.hiddenPlus = 1;//不显示�?+”号按钮
      MullineRiskSaleNameGrid.hiddenSubtraction = 1;//不显示�?--”号按钮
      MullineRiskSaleNameGrid.canSel=1;//显示Radio 单�?�?
      MullineRiskSaleNameGrid.selBoxEventFuncName = "ShowGift";//在MulLine中单击RadioBox时响应开发人员外部编写的JS函数  ShowGift是函数名
      MullineRiskSaleNameGrid.loadMulLine(iArray);//调用对象初始化方法，属�?必须在此前设�?
      
      }
      catch(ex)
      {
        myAlert("RiskSaleNameInit.jsp-->"+""+"");
      }
}


</script>