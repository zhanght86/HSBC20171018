
<%
//程序名称：CtrlClaimInit.jsp
//程序功能：
//创建日期：2005-11-17
//创建人  ：Sujie
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<script language="JavaScript">
// 输入框的初始化（单记录部分）
function initInpBox()
{
  	try
  	{

		    if(GrpContNo == "null"){
		    fm.all('GrpContNo').value = "";
		    }else{
		    fm.all('GrpContNo').value = GrpContNo ;
		    }
		    fm.DutyCode.value="";				
				fm.all('OccupationType').value = "";
				fm.all('OccupationTypeName').value = "";
		    //fm.all('CtrlProp').value = "";
		    
				fm.all('ContPlanCode').value = "";
		    fm.all('RiskCode').value = "";
		    
		    fm.all('DutyCode').value = "";
		    fm.all('GetDutyCode').value = "";
		    
		    fm.all('AddressNoName').value = "";
		    fm.all('DutyCodeName').value = "";
		    fm.all('GetDutyCodeName').value = "";
		    fm.all('CtrlPropName').value = "";
				
				
				fm.CtrlBatchNo.value="";
				
				
				fm.ObserveDate.value="";
				fm.Exempt.value="";
				fm.ExemptDate.value="";
				fm.TotalLimit.value="";

				fm.ClaimRate.value="";

				fm.StartPay.value="";
				fm.EndPay.value="";

				
		 }
	  catch(ex)
	  {
		    alert("在CtrlClaimInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
	  }
}

function initForm()
{
	  try
	  {
	    	initInpBox();
		    initCtrlClaimCodeGrid();
		    QueryForm();
		    //initDutyCtrllevelCodeGrid();
		    //initMultbegin();
	  }
	  catch(re)
	  {
	     	alert("CtrlClaimInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
	  }
}

//理赔控制初始化
function initCtrlClaimCodeGrid() {
	var iArray = new Array();
	try {
		iArray[0]=new Array();
		iArray[0][0]="序号";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="团单号";
		iArray[1][1]="0px";
		iArray[1][2]=100;
		iArray[1][3]=0;
		
		iArray[2]=new Array();
		iArray[2][0]="职业类别";
		iArray[2][1]="60px";
		iArray[2][2]=150;
		iArray[2][3]=0;
			
		iArray[3]=new Array();
		iArray[3][0]="控制属性";
		iArray[3][1]="80px";
		iArray[3][2]= 60;
		iArray[3][3]= 0;
		
		iArray[4]=new Array();
		iArray[4][0]="保障计划编码";
		iArray[4][1]="80px";
		iArray[4][2]= 60;
		iArray[4][3]= 0;
		
		iArray[5]=new Array();
		iArray[5][0]="险种编码";
		iArray[5][1]="80px";
		iArray[5][2]= 60;
		iArray[5][3]= 0;
		
		iArray[6]=new Array();
		iArray[6][0]="保险责任编码";
		iArray[6][1]="80px";
		iArray[6][2]= 60;
		iArray[6][3]= 0;
		
		iArray[7]=new Array();
		iArray[7][0]="给付责任编码";
		iArray[7][1]="100px";
		iArray[7][2]= 60;
		iArray[7][3]= 0;
				
			
		iArray[8]=new Array();
		iArray[8][0]="控制层次";
		iArray[8][1]="0px";
		iArray[8][2]= 60;
		iArray[8][3]= 0;
				
		iArray[9]=new Array();
		iArray[9][0]="控制批次号";
		iArray[9][1]="0px";
		iArray[9][2]= 60;
		iArray[9][3]= 0;
				
		iArray[10]=new Array();
		iArray[10][0]="观察期";
		iArray[10][1]="100px";
		iArray[10][2]= 60;
		iArray[10][3]= 0;
		
		iArray[11]=new Array();
		iArray[11][0]="免赔额";
		iArray[11][1]="100px";
		iArray[11][2]= 60;
		iArray[11][3]= 0;
		
		iArray[12]=new Array();
		iArray[12][0]="免陪天数";
		iArray[12][1]="100px";
		iArray[12][2]= 60;
		iArray[12][3]= 0;
		
		iArray[13]=new Array();
		iArray[13][0]="总赔付限额";
		iArray[13][1]="100px";
		iArray[13][2]= 60;
		iArray[13][3]= 0;
		
		iArray[14]=new Array();
		iArray[14][0]="赔付比例";
		iArray[14][1]="100px";
		iArray[14][2]= 60;
		iArray[14][3]= 0;
		
		iArray[15]=new Array();
		iArray[15][0]="起付线";
		iArray[15][1]="100px";
		iArray[15][2]= 60;
		iArray[15][3]= 0;
		
		iArray[16]=new Array();
		iArray[16][0]="封顶线";
		iArray[16][1]="100px";
		iArray[16][2]= 60;
		iArray[16][3]= 0;
		
		iArray[17]=new Array();
		iArray[17][0]="备注";
		iArray[17][1]="100px";
		iArray[17][2]= 60;
		iArray[17][3]= 0;
		
		CtrlClaimCodeGrid = new MulLineEnter( "fm" , "CtrlClaimCodeGrid" );
		//这些属性必须在loadMulLine前
		CtrlClaimCodeGrid.mulLineCount = 0;
		CtrlClaimCodeGrid.displayTitle = 1;
		CtrlClaimCodeGrid.hiddenPlus = 1;
		CtrlClaimCodeGrid.hiddenSubtraction = 1;
		CtrlClaimCodeGrid.canSel=1;
		CtrlClaimCodeGrid.selBoxEventFuncName = "ShowCtrlClaim";
		CtrlClaimCodeGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
</script>
