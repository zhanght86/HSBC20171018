<%
//程序名称：RelFeeQueryInit.jsp
//程序功能：
//创建日期：2002-12-16
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                           
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                    
	// 保单查询条件
    	
    document.all('GrpContNo').value = tGrpContNo;
    document.all('RiskCode').value = tRiskCode;
    document.all('InsuredName').value = tInsuredName;
    document.all('AppntName').value = tAppntName;
  }
  catch(ex)
  {
    alert("在RelFeeQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
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
    alert("在RelFeeQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
		initPolGrid();
  }
  catch(re)
  {
    alert("RelFeeQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

var PolGrid; 
// 保单信息列表的初始化

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
      iArray[1][0]="交费收据号码";         		//列名
      iArray[1][1]="160px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="应收/实收编号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="应收/实收编号类型";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][10] = "NoType1";
      iArray[3][11] = "0|^1|集体保单号^2|个人保单号^3|批改号^4|合同投保单号^5|集体投保单号^6|个人投保单号^7|合同印刷号^8|集体印刷号^9|个人印刷号";
      iArray[3][12] = "3";
      iArray[3][18]=300;
      iArray[3][19] = "0";

      iArray[4]=new Array();
      iArray[4][0]="总实交金额";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="交至日期";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="确认日期";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[7]=new Array();
      iArray[7][0]="操作员";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
	  iArray[8]=new Array();
      iArray[8][0]="管理机构";         		//列名
      iArray[8][1]="100px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=2; 
      iArray[8][4]="station";              	        //是否引用代码:null||""为不引用
      iArray[8][5]="8";              	                //引用代码对应第几列，'|'为分割符
      iArray[8][9]="出单机构|code:station&NOTNULL";
      iArray[8][18]=250;
      iArray[8][19]= 0 ; 
      
	  iArray[9]=new Array();
      iArray[9][0]="代理人编码";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
 	  iArray[10]=new Array();
      iArray[10][0]="交费次数";         		//列名
      iArray[10][1]="60px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0; 									//是否允许输入,1表示允许，0表示不允许
          
      iArray[11]=new Array();
	  iArray[11][0]="币种";         		
	  iArray[11][1]="50px";            		 
	  iArray[11][2]=60;            			
	  iArray[11][3]=2;              		
	  iArray[11][4]="Currency";              	  
	  iArray[11][9]="币种|code:Currency";
              
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 5;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      }
      catch(ex)
      {
        alert(ex);
      }
      
}

</script>