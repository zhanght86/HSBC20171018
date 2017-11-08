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
    document.all('Name').value = '1';
    document.all('AppntSex').value = '';
    document.all('Birthday').value = '1971-1-1';
    document.all('Marriage').value = '';
    document.all('OccupationType').value = '';
    document.all('Health').value = '';

    document.all('AppntIDType').value = '';
    document.all('Proterty').value = '';
    document.all('IDNo').value = '';
    document.all('ICNo').value = '';
    document.all('OthIDType').value = '';
    document.all('OthIDNo').value = '';

   document.all('GrpName').value = '';
    document.all('GrpAddress').value = '';
    document.all('HomeAddressCode').value = '';
    document.all('HomeAddress').value = '';
    document.all('Phone').value = '';
    document.all('BP').value = '';
    document.all('Mobile').value = '';
    document.all('EMail').value = '';

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
    //initInpBox();
    initClientList( );
    initOPolGrid();
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
      iArray[0][0]="序号";         		    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";        			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号";    	        //列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="姓名";    	        //列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      

      iArray[3]=new Array();
      iArray[3][0]="性别"; 	            //列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=10;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="出生日期"; 	            //列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;  

      iArray[5]=new Array();
      iArray[5][0]="证件类型"; 	            //列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="证件号码"; 	            //列名
      iArray[6][1]="150px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="其它证件类型"; 	            //列名
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;  
      
      iArray[8]=new Array();
      iArray[8][0]="其它证件号码"; 	            //列名
      iArray[8][1]="150px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=1;  

      ClientList = new MulLineEnter( "fm" , "ClientList" ); 
      //这些属性必须在loadMulLine前
      ClientList.mulLineCount = 0;   
      ClientList.displayTitle = 1;
      ClientList.canSel = 1;
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
      iArray[7][1]="100px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[8]=new Array();
      iArray[8][0]="其它证件号码";         		//列名
      iArray[8][1]="150px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      
      OPolGrid = new MulLineEnter( "fm" , "OPolGrid" ); 
      //这些属性必须在loadMulLine前
      OPolGrid.mulLineCount = 1;   
      OPolGrid.displayTitle = 1;
      OPolGrid.locked = 1;
      OPolGrid.canSel = 1;
      OPolGrid.hiddenPlus = 1;
      OPolGrid.hiddenSubtraction = 1;
      OPolGrid.loadMulLine(iArray); 
      
      OPolGrid. selBoxEventFuncName = "customerInfoequels"; 
      
      //这些操作必须在loadMulLine后面
      //OPolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
