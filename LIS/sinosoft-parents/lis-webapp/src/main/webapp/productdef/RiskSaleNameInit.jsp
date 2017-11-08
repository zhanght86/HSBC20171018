<%@include file="../i18n/language.jsp"%>
<%@include file="../i18n/language.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>

<script type="text/javascript">
function initInpBox()
{ 
  try
  {    //椤甸潰鏂囨湰妗嗙殑鍒濆鍖栵紙寰呰ˉ鍏咃級
	 
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
    myAlert("RiskSaleNameInit.jsp-->"+""+"InitInpBox函数中发生异常:初始化界面错误!");
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
    myAlert("RiskSaleNameInit.jsp-->"+"InitForm函数中发生异常:初始化界面错误!");
  }
}

function initMullineRiskSaleNameGrid()
  {                             
    var iArray = new Array();
    var i=0; 
      try
      {
     
      iArray[i]=new Array();
      iArray[i][0]="序号";         		     //鍒楀悕
      iArray[i][1]="30px";            		//鍒楀
      iArray[i][2]=120;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?

      iArray[++i]=new Array();
      iArray[i][0]="险种编码";         		//鍒楀悕
      iArray[i][1]="100px";            		//鍒楀
      iArray[i][2]=120;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?   
      
      iArray[++i]=new Array();
      iArray[i][0]="销售渠道 ";         		//鍒楀悕
      iArray[i][1]="100px";            		//鍒楀
      iArray[i][2]=100;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?   
      
      iArray[++i]=new Array();
      iArray[i][0]="管理机构";         		//鍒楀悕
      iArray[i][1]="100px";            		//鍒楀
      iArray[i][2]=100;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?   
            
      iArray[++i]=new Array();
      iArray[i][0]="";         	//鍒楀悕
      iArray[i][1]="100px";            		//鍒楀
      iArray[i][2]=100;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?   
      
      iArray[++i]=new Array();
      iArray[i][0]="";         	//鍒楀悕
      iArray[i][1]="0px";            		//鍒楀
      iArray[i][2]=100;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?   
            
      iArray[++i]=new Array();
      iArray[i][0]="";         		//鍒楀悕
      iArray[i][1]="0px";            		//鍒楀
      iArray[i][2]=100;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?
      
      iArray[++i]=new Array();
      iArray[i][0]="";         		//鍒楀悕
      iArray[i][1]="0px";            		//鍒楀
      iArray[i][2]=100;            			//鍒楁渶澶у?
      iArray[i][3]=0;              			//鏄惁鍏佽杈撳叆,1琛ㄧず鍏佽锛?琛ㄧず涓嶅厑璁?
                 			
      
     
      
      MullineRiskSaleNameGrid = new MulLineEnter( "fm" , "MullineRiskSaleNameGrid" ); 
      
      MullineRiskSaleNameGrid.mulLineCount = 4;//琛屽睘鎬э細璁剧疆琛屾暟
      MullineRiskSaleNameGrid.displayTitle = 1;//1鏄剧ず鏍囬 (缂虹渷鍊? ,0闅愯棌鏍囬
      MullineRiskSaleNameGrid.hiddenPlus = 1;//涓嶆樉绀衡?+鈥濆彿鎸夐挳
      MullineRiskSaleNameGrid.hiddenSubtraction = 1;//涓嶆樉绀衡?--鈥濆彿鎸夐挳
      MullineRiskSaleNameGrid.canSel=1;//鏄剧ずRadio 鍗曢?妗?
      MullineRiskSaleNameGrid.selBoxEventFuncName = "ShowGift";//鍦∕ulLine涓崟鍑籖adioBox鏃跺搷搴斿紑鍙戜汉鍛樺閮ㄧ紪鍐欑殑JS鍑芥暟  ShowGift鏄嚱鏁板悕
      MullineRiskSaleNameGrid.loadMulLine(iArray);//璋冪敤瀵硅薄鍒濆鍖栨柟娉曪紝灞炴?蹇呴』鍦ㄦ鍓嶈缃?
      
      }
      catch(ex)
      {
        myAlert("RiskSaleNameInit.jsp-->"+""+"");
      }
}


</script>