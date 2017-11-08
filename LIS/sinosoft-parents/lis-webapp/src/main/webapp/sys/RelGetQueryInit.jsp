<%
//程序名称：RelGetQueryInit.jsp
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
	document.all('ContNo').value = tContNo;
    document.all('PolNo').value = tPolNo;
    document.all('RiskCode').value = tRiskCode;
    //document.all('InsuredName').value = tInsuredName;
    //document.all('AppntName').value = tAppntName;
  }
  catch(ex)
  {
    alert("在RelGetQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
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
    alert("在RelGetQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initPolGrid();
	easyQueryClick2();
	easyQueryClick();

  }
  catch(re)
  {
    alert("RelGetQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
      iArray[1][0]="实付号码";         		//列名
      iArray[1][1]="140px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

//      iArray[2]=new Array();
//      iArray[2][0]="其它号码";         		//列名
//      iArray[2][1]="140px";            		//列宽
//      iArray[2][2]=100;            			//列最大值
//      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

//      iArray[3]=new Array();
//      iArray[3][0]="其它号码类型";         		//列名
//      iArray[3][1]="120px";            		//列宽
//      iArray[3][2]=200;            			//列最大值
//      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[3][10] = "NoType";
//      iArray[3][11] = "0|^0|生存领取对应的合同号^1|生存领取对应的集体保单号^2|生存领取对应的个人保单号^3|批改号^4|暂交费退费,对应暂交费退费表的暂缴费收据号^5|赔付应收表中的给付通知书号（就是赔案号）^6|其他退费的给付通知书号码(对应号码添个人保单号）^7|红利对应的个人保单号^8|其他退费的给付通知书号码(对应号码填团体保单号）";
//      iArray[3][12] = "3";
//      iArray[3][18]=300;
//      iArray[3][19] = "0";
     


      iArray[2]=new Array();
      iArray[2][0]="总给付金额";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="应付日期";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="财务确认日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[5]=new Array();
      iArray[5][0]="操作员";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0; 									//是否允许输入,1表示允许，0表示不允许
      
	  iArray[6]=new Array();
      iArray[6][0]="管理机构";         		//列名
      iArray[6][1]="100px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=2; 
      iArray[6][4]="station";              	        //是否引用代码:null||""为不引用
      iArray[6][5]="8";              	                //引用代码对应第几列，'|'为分割符
      iArray[6][9]="出单机构|code:station&NOTNULL";
      iArray[6][18]=250;
      iArray[6][19]= 0 ;    

	  iArray[7]=new Array();
      iArray[7][0]="代理人编码";         		//列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=2; 									//是否允许输入,1表示允许，0表示不允许
      iArray[7][4]="AgentCode";              	        //是否引用代码:null||""为不引用
      iArray[7][5]="9";              	                //引用代码对应第几列，'|'为分割符
      iArray[7][9]="代理人编码|code:AgentCode&NOTNULL";
      iArray[7][18]=250;
      iArray[7][19]= 0 ;    
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.selBoxEventFuncName ="" ;     //点击RadioBox时响应的JS函数
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

</script>
