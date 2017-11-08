<%
//程序名称：FineProposalInput.jsp
//程序功能：
//创建日期：2008-06-26 
//创建人  ：ln
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox() { 
  try {  
                                   
	  // 收费信息部分
    document.all('TempFeeNo').value = '';
    document.all('OtherNo').value = '';
    document.all('ManageCom').value = '';
    document.all('PayDate').value = '';
    document.all('EnterAccDate').value = '';
    document.all('AgentCode').value = '';

    
    document.all('SumTempFee').value = 0.0 ;   
   
    
  } catch(ex) {
    //alert("在ProposalInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  } 
  
}

// 暂收费信息列表的初始化
function initTempGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";          		//列名
      iArray[1][1]="50px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[1][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[1][18]=300;
      //iArray[1][19]=1;

      iArray[2]=new Array();
      iArray[2][0]="交费金额";      	   		//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][9]="交费金额|NUM&NOTNULL";

      iArray[3]=new Array();
      iArray[3][0]="其它号码";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="缴费方式";      	   		//列名
      iArray[4][1]="60px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][10]="PayIntv";
      iArray[4][11]="0|^12|年缴|^0|趸缴|^6|半年缴^3|季缴^1|月缴^-1|不定期缴";
      
      iArray[5]=new Array();
      iArray[5][0]="缴费年期";      	   		//列名
      iArray[5][1]="60px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      TempGrid = new MulLineEnter( "fm" , "TempGrid" ); 
      //这些属性必须在loadMulLine前
      TempGrid.mulLineCount = 0;   
      TempGrid.displayTitle = 1;      
      TempGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 暂收费分类列表的初始化
function initTempClassGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="交费方式";          		//列名
      iArray[1][1]="50px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][4]="paymode";              	        //是否引用代码:null||""为不引用
      iArray[1][9]="交费方式|code:paymode&NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="交费金额";      	   		//列名
      iArray[2][1]="90px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][9]="交费金额|NUM&NOTNULL";
      
      iArray[3]=new Array();
      iArray[3][0]="票据号码";      	   		//列名
      iArray[3][1]="140px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[4][9]="票据号码|NULL|LEN>10";              //测试或运算

      iArray[4]=new Array();
      iArray[4][0]="到账日期";      	   		//列名
      iArray[4][1]="100px";            			//列宽
      iArray[4][2]=10;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  
      iArray[5]=new Array();
      iArray[5][0]="开户银行";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=40;            			//列最大值
      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][4]="bank";              	 //是否引用代码:null||""为不引用
      iArray[5][9]="开户银行|code:bank";
      iArray[5][18]=250;
      
      iArray[6]=new Array();
      iArray[6][0]="银行账号";      	   		//列名
      iArray[6][1]="140px";            		//列宽
      iArray[6][2]=40;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="户名";      	   		//列名
      iArray[7][1]="100px";            			//列宽
      iArray[7][2]=60;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[8]=new Array();
      iArray[8][0]="证件号码";      	   		//列名
      iArray[8][1]="100px";            			//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
           

      TempClassGrid = new MulLineEnter( "fm" , "TempClassGrid" ); 
      //这些属性必须在loadMulLine前
      TempClassGrid.mulLineCount = 0;   
      TempClassGrid.displayTitle = 1;     
      TempClassGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
  }

function emptyForm() {
	//emptyFormElements();     //清空页面所有输入框，在COMMON。JS中实现
	initInpBox();
	initTempGrid();
	initTempClassGrid();
	
}

function initForm() {
	try	{   
		
		if (loadFlag == "3"||loadFlag == "4") {	//个人投保单明细查询
			var tTempFeeNo = top.opener.parent.VD.gVSwitch.getVar( "TempFeeNo" );
			//alert("top.opener.parent"+top.opener.parent.location);
			
			//alert("tTempFeeNo:"+tTempFeeNo);
			queryPolDetail( tTempFeeNo );
		} 
	} catch(ex) {
	}			
}

 

</script>
