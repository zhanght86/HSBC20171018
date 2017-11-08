<%
//程序名称：LLSecondManuUWInit.jsp
//程序功能：理赔人工核保
//创建日期：2002-09-24 11:10:36
//创建人  ：zhangxing
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<script language="JavaScript">

// 接收传入数据
function initParm()
{
	 document.all('Operator').value =nullToEmpty("<%=tGI.Operator%>"); //记录操作员
     document.all('ComCode').value =nullToEmpty("<%=tGI.ComCode%>"); //记录登陆机构
     document.all('ManageCom').value =nullToEmpty("<%=tGI.ManageCom%>"); //记录管理机构
    
   	 document.all('tCaseNo').value =nullToEmpty("<%=tCaseNo%>"); //赔案号		
     document.all('tBatNo').value =nullToEmpty("<%=tBatNo%>"); //批次号
     document.all('tInsuredNo').value =nullToEmpty("<%=tInsuredNo%>"); //出险人客户号 
     document.all('tClaimRelFlag').value =nullToEmpty("<%=tClaimRelFlag%>"); //赔案相关标志 	
    
	 document.all('tMissionid').value =nullToEmpty("<%=tMissionid%>");   //任务ID 
	 document.all('tSubmissionid').value =nullToEmpty("<%=tSubmissionid%>"); //子任务ID 
	 document.all('tActivityid').value =nullToEmpty("<%=tActivityid%>"); //节点号 	

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


function initForm()
{
  try
  {
	 	 initLLCUWBatchGrid();
		 initLLCUWSubGrid(); 	 //个人合同理赔核保核保轨迹表
	     initParm();
	     initLLCUWBatchGridQuery();
	     initInsuredGrid();
	     if(LLCUWBatchGrid.canSel==1&&LLCUWBatchGrid.mulLineCount>1)
	     {
	     			//alert('@@');
	     			document.all('LLCUWBatchGridSel')[0].checked=true;
	     			LLCUWBatchGridClick();
	     			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
	     			//getInsuredDetail();
	     }
	     if(LLCUWBatchGrid.canSel==1&&LLCUWBatchGrid.mulLineCount==1)
	     {
	     			//alert('@@');
	     			document.all('LLCUWBatchGridSel').checked=true;
	     			LLCUWBatchGridClick();
	     			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
	     			//getInsuredDetail();
	     }
  }
  catch(re)
  {
    alert("在LLSecondManuUWInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initLLCUWBatchGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[1]=new Array();
		iArray[1][0]="批次号";         		//列名
		iArray[1][1]="100px";            		//列宽
		iArray[1][2]=100;            			//列最大值
		iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
		iArray[2]=new Array();
		iArray[2][0]="立案号";         		//列名
		iArray[2][1]="120px";            		//列宽
		iArray[2][2]=100;            			//列最大值
		iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
		iArray[3]=new Array();
		iArray[3][0]="保单号";         		    //列名
		iArray[3][1]="120px";            		//列宽
		iArray[3][2]=100;            			//列最大值
		iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[4]=new Array();
		iArray[4][0]="投保人客户号";         		//列名
		iArray[4][1]="100px";            		//列宽
		iArray[4][2]=200;            			//列最大值
		iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
			
		iArray[5]=new Array();
		iArray[5][0]="投保人姓名";         		//列名
		iArray[5][1]="100px";            		//列宽
		iArray[5][2]=200;            			//列最大值
		iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		
		iArray[6]=new Array();
		iArray[6][0]="出险人客户号";
		iArray[6][1]="100px";
		iArray[6][2]=60;
		iArray[6][3]=0;
	
		iArray[7]=new Array();
		iArray[7][0]="出险人姓名";
		iArray[7][1]="100px";
		iArray[7][2]=120;
		iArray[7][3]=0;

		iArray[8]=new Array();
		iArray[8][0]="险种名称";
		iArray[8][1]="100px";
		iArray[8][2]=120;
		iArray[8][3]=0;

		iArray[9]=new Array();
		iArray[9][0]="保单生效日期";
		iArray[9][1]="100px";
		iArray[9][2]=120;
		iArray[9][3]=0;
		
		iArray[10]=new Array();
		iArray[10][0]="管理机构";
		iArray[10][1]="80px";
		iArray[10][2]=120;
		iArray[10][3]=0;
		
		iArray[11]=new Array();
		iArray[11][0]="赔案相关标志";
		iArray[11][1]="80px";
		iArray[11][2]=120;
		iArray[11][3]=0;

		iArray[12]=new Array();
		iArray[12][0]="保单状态";
		iArray[12][1]="80px";
		iArray[12][2]=120;
		iArray[12][3]=0;

		LLCUWBatchGrid = new MulLineEnter( "fm" , "LLCUWBatchGrid" ); 
		//这些属性必须在loadMulLine前
		LLCUWBatchGrid.mulLineCount = 1;   
		LLCUWBatchGrid.displayTitle = 1;
		LLCUWBatchGrid.locked = 1;
		LLCUWBatchGrid.canSel = 1;
		LLCUWBatchGrid.hiddenPlus = 1;
		LLCUWBatchGrid.hiddenSubtraction = 1;
		LLCUWBatchGrid.selBoxEventFuncName = "LLCUWBatchGridClick";
		LLCUWBatchGrid.loadMulLine(iArray);       
		
	}
	catch(ex)
	{
		alert(ex);
	}
}

//个人合同理赔核保核保轨迹表
function initLLCUWSubGrid()
{                               
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="序号";         			//列名 
		iArray[0][1]="30px";            		//列宽
		iArray[0][2]=30;            			//列最大值
		iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许		 
		
		iArray[1]=new Array();
		iArray[1][0]="分案号";         		 
		iArray[1][1]="0px";            		 
		iArray[1][2]=100;            			 
		iArray[1][3]=3;              			 
		
		iArray[2]=new Array();
		iArray[2][0]="批次号";         		 
		iArray[2][1]="120px";            		 
		iArray[2][2]=100;            			 
		iArray[2][3]=0;              			 
		
		iArray[3]=new Array();
		iArray[3][0]="合同号码";         		 
		iArray[3][1]="120px";            		 
		iArray[3][2]=100;            			 
		iArray[3][3]=0;              			 
		
		iArray[4]=new Array();
		iArray[4][0]="顺序号";         		 
		iArray[4][1]="50px";            		 
		iArray[4][2]=100;            			 
		iArray[4][3]=0;              			       
		
		iArray[5]=new Array();
		iArray[5][0]="核保标志";         		 
		iArray[5][1]="50px";            		 
		iArray[5][2]=100;            			 
		iArray[5][3]=0;              			 
		
		iArray[6]=new Array();
		iArray[6][0]="核保结论";         		 
		iArray[6][1]="80px";            		 
		iArray[6][2]=100;            			 
		iArray[6][3]=0;              			 
		
		iArray[7]=new Array();
		iArray[7][0]="核保意见";         		 
		iArray[7][1]="200px";            		 
		iArray[7][2]=100;            			 
		iArray[7][3]=0;              			 
		
		iArray[8]=new Array();
		iArray[8][0]="操作员";         		 
		iArray[8][1]="80px";            		 
		iArray[8][2]=100;            			 
		iArray[8][3]=0;              			 
		
		iArray[9]=new Array();
		iArray[9][0]="核保日期";         		 
		iArray[9][1]="80px";            		 
		iArray[9][2]=100;            			 
		iArray[9][3]=0;         
		
		iArray[10]=new Array();
		iArray[10][0]="核保时间";         		 
		iArray[10][1]="80px";            		 
		iArray[10][2]=100;            			 
		iArray[10][3]=0;        			 
	
		LLCUWSubGrid = new MulLineEnter( "fm","LLCUWSubGrid" ); 
		//这些属性必须在loadMulLine前
		LLCUWSubGrid.mulLineCount = 0;   
		LLCUWSubGrid.displayTitle = 1;
		LLCUWSubGrid.locked = 1;
		LLCUWSubGrid.canSel = 0;
		LLCUWSubGrid.hiddenPlus = 1;
		LLCUWSubGrid.hiddenSubtraction = 1;
		LLCUWSubGrid.loadMulLine(iArray);     
	}
	catch(ex)
	{
		alert(ex);
	}
}

/*=======================================================================
 * 修改原因：添加被保人信息
 * 修改人：  万泽辉
 * 修改时间：2005/12/15 16:30
 =======================================================================
 */
function initInsuredGrid()
{                               
    var iArray = new Array();
      
      try
      {
	      iArray[0]=new Array();
	      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
	      iArray[0][1]="30px";            		//列宽
	      iArray[0][2]=30;            			//列最大值
	      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[1]=new Array();
	      iArray[1][0]="客户号码";         		//列名
	      iArray[1][1]="140px";            		//列宽
	      iArray[1][2]=100;            			//列最大值
	      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	
	      iArray[2]=new Array();
	      iArray[2][0]="姓名";         		    //列名
	      iArray[2][1]="100px";            		//列宽
	      iArray[2][2]=100;            			//列最大值
	      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	      
	      iArray[3]=new Array();
	      iArray[3][0]="性别";         		    //列名
	      iArray[3][1]="100px";            		//列宽
	      iArray[3][2]=100;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	      iArray[4]=new Array();
	      iArray[4][0]="年龄";         		    //列名
	      iArray[4][1]="100px";            		//列宽
	      iArray[4][2]=100;            			//列最大值
	      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	      iArray[5]=new Array();
	      iArray[5][0]="与投保人关系";         		    //列名
	      iArray[5][1]="100px";            		//列宽
	      iArray[5][2]=100;            			//列最大值
	      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
		  	
	      iArray[6]=new Array();
	      iArray[6][0]="与主被保人关系";         		//列名
	      iArray[6][1]="60px";            		//列宽
	      iArray[6][2]=200;            			//列最大值
	      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      iArray[7]=new Array();
	      iArray[7][0]="国籍";         		//列名
	      iArray[7][1]="120px";            		//列宽
	      iArray[7][2]=200;            			//列最大值
	      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	
	      
	      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
	      //这些属性必须在loadMulLine前
	      InsuredGrid.mulLineCount      = 0;   
	      InsuredGrid.displayTitle      = 1;
	      InsuredGrid.locked            = 1;
	      InsuredGrid.canSel            = 1;
	      InsuredGrid.hiddenPlus        = 1;
	      InsuredGrid.hiddenSubtraction = 1;
	      InsuredGrid.selBoxEventFuncName = "getInsuredDetail"; 
	      InsuredGrid.loadMulLine(iArray);   
		   
      }
      catch(ex)
      {
          alert(ex);
      }
}


function initPolRiskGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="险种编码";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="币种";      	   		//列名
      iArray[3][1]="80px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
   
     	iArray[4]=new Array();
      iArray[4][0]="保额";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

  		iArray[5]=new Array();
      iArray[5][0]="份数";         		//列名
      iArray[5][1]="40px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保险期间";         		//列名
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
	
     	iArray[7]=new Array();
      iArray[7][0]="交费期间";         		//列名
      iArray[7][1]="60px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

    	iArray[8]=new Array();
      iArray[8][0]="交费频率";         		//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
	


      iArray[9]=new Array();
      iArray[9][0]="标准保费";         		//列名
      iArray[9][1]="40px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[10]=new Array();
      iArray[10][0]="职业加费";         		//列名
      iArray[10][1]="40px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[11]=new Array();
      iArray[11][0]="健康加费";         		//列名
      iArray[11][1]="40px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="险种号";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[13]=new Array();
      iArray[13][0]="居住地加费";         		//列名
      iArray[13][1]="60px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[14]=new Array();
      iArray[14][0]="爱好加费";         		//列名
      iArray[14][1]="40px";            		//列宽
      iArray[14][2]=200;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[15]=new Array();
	  iArray[15][0]="保险起期";         	//列名
	  iArray[15][1]="80px";            		//列宽
	  iArray[15][2]=80;            			//列最大值
	  iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[16]=new Array();
	  iArray[16][0]="保险止期";         		//列名
	  iArray[16][1]="80px";            		//列宽
	  iArray[16][2]=100;            			//列最大值
	  iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[17]=new Array();
	  iArray[17][0]="交费间隔(月)";         		//列名
	  iArray[17][1]="80px";            		//列宽
	  iArray[17][2]=100;            			//列最大值
	  iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[18]=new Array();
	  iArray[18][0]="交费年期";         		//列名
	  iArray[18][1]="80px";            		//列宽
	  iArray[18][2]=100;            			//列最大值
	  iArray[18][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[19]=new Array();
	  iArray[19][0]="保单状态";         		//列名
	  iArray[19][1]="80px";            		//列宽
	  iArray[19][2]=100;            			//列最大值
	  iArray[19][3]=3;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[20]=new Array();
	  iArray[20][0]="核保结论";         		//列名
	  iArray[20][1]="80px";            		//列宽
	  iArray[20][2]=100;            			//列最大值
	  iArray[20][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[21]=new Array();
	  iArray[21][0]="原核保结论";         		//列名
	  iArray[21][1]="80px";            		//列宽
	  iArray[21][2]=100;            			//列最大值
	  iArray[21][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      PolRiskGrid = new MulLineEnter( "fm" , "PolRiskGrid" ); 
      //这些属性必须在loadMulLine前
      PolRiskGrid.mulLineCount = 3;   
      PolRiskGrid.displayTitle = 1;
      PolRiskGrid.locked = 1;
      PolRiskGrid.canSel = 0;
      PolRiskGrid.hiddenPlus = 1;
      PolRiskGrid.hiddenSubtraction = 1;
      PolRiskGrid.loadMulLine(iArray);       
   //   PolAddGrid.selBoxEventFuncName = "showInsuredInfo";
   //   PolRiskGrid.selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      //这些操作必须在loadMulLine后面
      //PolAddGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
