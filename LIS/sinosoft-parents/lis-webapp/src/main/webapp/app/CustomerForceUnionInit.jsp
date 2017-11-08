<%
//程序名称：ClientMergeInit.jsp
//程序功能：
//创建日期：2002-08-19
//创建人  ：Kevin
//更新记录：  更新人    更新日期     更新原因/内容
%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   

  }
  catch(ex)
  {
    //alert("在ClientMergeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    document.all('PrtNo').value =PtrNo;
    initClientList();
    initOPolGrid();
    displayCustomer();
    fm.CustomerNo_OLD.value = Hole_CustomerNo;
    //initCustomerContGrid();
  }
  catch(re)
  {
    //alert("ClientMergeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initClientList()
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
        iArray[1][0]="客户号";         		//列名
        iArray[1][1]="100px";            		//列宽
        iArray[1][2]=100;            			//列最大值
        iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="姓名";         		//列名
        iArray[2][1]="80px";            		//列宽
        iArray[2][2]=100;            			//列最大值
        iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[3]=new Array();
        iArray[3][0]="性别";         		//列名
        iArray[3][1]="40px";            		//列宽
        iArray[3][2]=10;            			//列最大值
        iArray[3][3]=0; 
        
        
        iArray[4]=new Array();
        iArray[4][0]="出生日期";         		//列名
        iArray[4][1]="100px";            		//列宽
        iArray[4][2]=100;            			//列最大值
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="证件类型";         		//列名
        iArray[5][1]="100px";            		//列宽
        iArray[5][2]=100;            			//列最大值
        iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[6]=new Array();
        iArray[6][0]="证件号码";         		//列名
        iArray[6][1]="150px";            		//列宽
        iArray[6][2]=200;            			//列最大值
        iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[7]=new Array();
        iArray[7][0]="其它证件类型";         		//列名
        iArray[7][1]="0px";            		//列宽
        iArray[7][2]=100;            			//列最大值
        iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[8]=new Array();
        iArray[8][0]="其它证件号码";         		//列名
        iArray[8][1]="150px";            		//列宽
        iArray[8][2]=100;            			//列最大值
        iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[9]=new Array();
        iArray[9][0]="通信地址";         		//列名
        iArray[9][1]="150px";            		//列宽
        iArray[9][2]=100;            			//列最大值
        iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[10]=new Array();
        iArray[10][0]="首选联系电话";         		//列名
        iArray[10][1]="100px";            		//列宽
        iArray[10][2]=100;            			//列最大值
        iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[11]=new Array();
        iArray[11][0]="其他联系电话";         		//列名
        iArray[11][1]="100px";            		//列宽
        iArray[11][2]=100;            			//列最大值
        iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[12]=new Array();
        iArray[12][0]="职业";         		//列名
        iArray[12][1]="0px";            		//列宽
        iArray[12][2]=100;            			//列最大值
        iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[13]=new Array();
        iArray[13][0]="管理机构";         		//列名
        iArray[13][1]="100px";            		//列宽
        iArray[13][2]=100;            			//列最大值
        iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
        
        iArray[14]=new Array();
        iArray[14][0]="业务员姓名";         		//列名
        iArray[14][1]="0px";            		//列宽
        iArray[14][2]=100;            			//列最大值
        iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      ClientList = new MulLineEnter( "fm" , "ClientList" ); 
      //这些属性必须在loadMulLine前
      ClientList.mulLineCount = 0;   
      ClientList.displayTitle = 1;
      ClientList.canSel = 1;
      ClientList.locked = 1;
      ClientList.hiddenPlus = 1;
      ClientList.hiddenSubtraction = 1;
      ClientList.loadMulLine(iArray);  
      
      ClientList.selBoxEventFuncName = "customerUnion";
       } 
      catch(ex) {
      alert(ex);
    }
}

function initOPolGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="姓名";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="性别";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0; 
      
      
      iArray[4]=new Array();
      iArray[4][0]="出生日期";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="证件类型";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="证件号码";         		//列名
      iArray[6][1]="150px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="其它证件类型";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="其它证件号码";         		//列名
      iArray[8][1]="150px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="通信地址";         		//列名
      iArray[9][1]="150px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="首选联系电话";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="其他联系电话";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="职业";         		//列名
      iArray[12][1]="0px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[13]=new Array();
      iArray[13][0]="管理机构";         		//列名
      iArray[13][1]="100px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="业务员姓名";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=100;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //这些属性必须在loadMulLine前
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 1;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray); 
      
      //OPolGrid. selBoxEventFuncName = "customerInfoequels"; 
      
      //这些操作必须在loadMulLine后面
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

//tongmeng 2009-03-03 add
//相似客户的保单信息
function initCustomerContGrid() {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="集体保单号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="保单/投保单号";         		//列名
      iArray[2][1]="100px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[3]=new Array();
      iArray[3][0]="印刷号";         		//列名
      iArray[3][1]="100px";            		//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=0;       
      
      iArray[4]=new Array();
      iArray[4][0]="被保人";         		//列名
      iArray[4][1]="40px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;
      
      iArray[5]=new Array();
      iArray[5][0]="性别";         		//列名
      iArray[5][1]="10px";            		//列宽
      iArray[5][2]=10;            			//列最大值
      iArray[5][3]=3;  
      
      iArray[6]=new Array();
      iArray[6][0]="出生日期";         		//列名
      iArray[6][1]="10px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="证件类型";         		//列名
      iArray[7][1]="10px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="证件号码";         		//列名
      iArray[8][1]="10px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[9]=new Array();
      iArray[9][0]="地址";         		//列名
      iArray[9][1]="150px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[10]=new Array();
      iArray[10][0]="联系电话1";         		//列名
      iArray[10][1]="100px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[11]=new Array();
      iArray[11][0]="联系电话2";         		//列名
      iArray[11][1]="100px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="职业";         		//列名
      iArray[12][1]="150px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[13]=new Array();
      iArray[13][0]="投保人";         		//列名
      iArray[13][1]="40px";            		//列宽
      iArray[13][2]=100;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[14]=new Array();
      iArray[14][0]="性别";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=10;            			//列最大值
      iArray[14][3]=3;       
      
      iArray[15]=new Array();
      iArray[15][0]="出生日期";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=100;            			//列最大值
      iArray[15][3]=3;

      iArray[16]=new Array();
      iArray[16][0]="证件类型";         		//列名
      iArray[16][1]="0px";            		//列宽
      iArray[16][2]=100;            			//列最大值
      iArray[16][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[17]=new Array();
      iArray[17][0]="证件号码";         		//列名
      iArray[17][1]="0px";            		//列宽
      iArray[17][2]=200;            			//列最大值
      iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[18]=new Array();
      iArray[18][0]="地址";         		//列名
      iArray[18][1]="150px";            		//列宽
      iArray[18][2]=100;            			//列最大值
      iArray[18][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[19]=new Array();
      iArray[19][0]="首选联系电话";         		//列名
      iArray[19][1]="100px";            		//列宽
      iArray[19][2]=100;            			//列最大值
      iArray[19][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[20]=new Array();
      iArray[20][0]="其他联系电话";         		//列名
      iArray[20][1]="100px";            		//列宽
      iArray[20][2]=100;            			//列最大值
      iArray[20][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[21]=new Array();
      iArray[21][0]="职业";         		//列名
      iArray[21][1]="150px";            		//列宽
      iArray[21][2]=100;            			//列最大值
      iArray[21][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[22]=new Array();
      iArray[22][0]="管理机构";         		//列名
      iArray[22][1]="100px";            		//列宽
      iArray[22][2]=100;            			//列最大值
      iArray[22][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[23]=new Array();
      iArray[23][0]="业务员姓名";         		//列名
      iArray[23][1]="0px";            		//列宽
      iArray[23][2]=100;            			//列最大值
      iArray[23][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[24]=new Array();
      iArray[24][0]="生效日期";         		//列名
      iArray[24][1]="40px";            		//列宽
      iArray[24][2]=200;            			//列最大值
      iArray[24][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[25]=new Array();
      iArray[25][0]="合同类型";         		//列名
      iArray[25][1]="0px";            		//列宽
      iArray[25][2]=100;            			//列最大值
      iArray[25][3]=0;              			//是否允许输入,1表示允许，0表示不允许
/*
      iArray[26]=new Array();
      iArray[26][0]="其它证件号码";         		//列名
      iArray[26][1]="150px";            		//列宽
      iArray[26][2]=100;            			//列最大值
      iArray[26][3]=0;              			//是否允许输入,1表示允许，0表示不允许

  */            
      CustomerContGrid = new MulLineEnter( "fm" , "CustomerContGrid" ); 
      //这些属性必须在loadMulLine前
      CustomerContGrid.mulLineCount = 1;   
      CustomerContGrid.displayTitle = 1;
      CustomerContGrid.locked = 1;
      CustomerContGrid.canSel = 1;
      CustomerContGrid.hiddenPlus = 1;
      CustomerContGrid.hiddenSubtraction = 1;
      CustomerContGrid.loadMulLine(iArray); 
      
      //OPolGrid. selBoxEventFuncName = "customerInfoequels"; 
      
      //这些操作必须在loadMulLine后面
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
