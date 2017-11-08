<%
//程序名称：EsDocManageInit.jsp
//程序功能：
//创建日期：2004-06-02
//创建人  ：LiuQiang
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {                                   
  	document.all('DOC_ID').value = '' 	;
    document.all('DOC_CODE').value = '' 	;
    document.all('NUM_PAGES').value = ''	;
    document.all('INPUT_DATE').value = ''	;
    //document.all('Input_Time').value = ''	;
    document.all('ScanOperator').value = ''	;
    document.all('ManageCom').value = ''	;
    document.all('InputState').value = ''	;
    document.all('Operator').value = ''	;
    document.all('InputStartDate').value = ''	;
    document.all('InputStartTime').value = ''	;
	document.all('DOC_FLAGE').value = 	''	;
	document.all('DOC_REMARK').value = 	''	;
	//document.all('Doc_Ex_Flag').value = 	''	;
	document.all('InputEndDate').value = 	''	;
	document.all('InputEndTime').value = 	''	;
	fm.addbutton.disabled=true;
	//fm.deletebutton.disabled=true;	
	fm.updatebutton.disabled=true;

  }
  catch(ex)
  {
    alert("在CodeInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initInpBox1()
{ 
  try
  {                                   
  	document.all('DOC_ID').value = '' 	;
    //document.all('DOC_CODE').value = '' 	;
    document.all('NUM_PAGES').value = ''	;
    document.all('INPUT_DATE').value = ''	;
    //document.all('Input_Time').value = ''	;
    document.all('ScanOperator').value = ''	;
    document.all('ManageCom').value = ''	;
    document.all('InputState').value = ''	;
    document.all('Operator').value = ''	;
    document.all('InputStartDate').value = ''	;
    document.all('InputStartTime').value = ''	;
	document.all('DOC_FLAGE').value = 	''	;
	document.all('DOC_REMARK').value = 	''	;
	//document.all('Doc_Ex_Flag').value = 	''	;
	document.all('InputEndDate').value = 	''	;
	document.all('InputEndTime').value = 	''	;
	fm.addbutton.disabled=true;
	fm.deletebutton.disabled=true;

  }
  catch(ex)
  {
    alert("在CodeInputInit.jsp-->InitInpBox1函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
//    setOption("sex","0=男&1=女&2=不详");        
  }
  catch(ex)
  {
    alert("在CodeInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initCodeGrid();
  }
  catch(re)
  {
    alert("CodeInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化CodeGrid
 ************************************************************
 */
  var CodeGrid;          //定义为全局变量，提供给displayMultiline使用
function initCodeGrid()
{                               
    var iArray = new Array();
      
      try
      {
      	iArray[0]=new Array();
        iArray[0][0]="序号";         //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";         //列宽
        iArray[0][2]=100;            //列最大值
        iArray[0][3]=0;              //是否允许输入,1表示允许，0表示不允许
        
		iArray[1]=new Array();
        iArray[1][0]="页内部号";         //列名
        iArray[1][1]="70px";         //列宽
        iArray[1][2]=100;            //列最大值
        iArray[1][3]=0;              //是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="单证内部号";         //列名
        iArray[2][1]="70px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="页码";         //列名
        iArray[3][1]="30px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=1;         //是否允许录入，0--不能，1--允许

        iArray[4]=new Array();
        iArray[4][0]="服务器名";         //列名
        iArray[4][1]="0px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=1;         //是否允许录入，0--不能，1--允许
    
        iArray[5]=new Array();
        iArray[5][0]="文件名";         //列名
        iArray[5][1]="70px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许

		iArray[6]=new Array();
        iArray[6][0]="状态";         //列名
        iArray[6][1]="30px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[7]=new Array();
        iArray[7][0]="文件FTP路径";         //列名
        iArray[7][1]="80px";         //宽度
        iArray[7][2]=100;         //最大长度
        iArray[7][3]=0;         //是否允许录入，0--不能，1--允许

		iArray[8]=new Array();
        iArray[8][0]="文件HTTP路径";         //列名
        iArray[8][1]="80px";         //宽度
        iArray[8][2]=100;         //最大长度
        iArray[8][3]=0;         //是否允许录入，0--不能，1--允许
        
		iArray[9]=new Array();
        iArray[9][0]="扫描操作员";         //列名
        iArray[9][1]="70px";         //宽度
        iArray[9][2]=100;         //最大长度
        iArray[9][3]=1;         //是否允许录入，0--不能，1--允许
        
        iArray[10]=new Array();
        iArray[10][0]="扫描日期";         //列名
        iArray[10][1]="70px";         //宽度
        iArray[10][2]=100;         //最大长度
        iArray[10][3]=1;         //是否允许录入，0--不能，1--允许
        
        iArray[11]=new Array();
        iArray[11][0]="扫描时间";         //列名
        iArray[11][1]="70px";         //宽度
        iArray[11][2]=100;         //最大长度
        iArray[11][3]=1;         //是否允许录入，0--不能，1--允许
        
        iArray[12]=new Array();
        iArray[12][0]="单证类型";         //列名
        iArray[12][1]="70px";         //宽度
        iArray[12][2]=100;         //最大长度
        iArray[12][3]=2;         //是否允许录入，0--不能，1--允许
        iArray[12][4]="imagetype";  
        
        iArray[13]=new Array();
        iArray[13][0]="单证扫描号码";         //列名
        iArray[13][1]="0px";         //宽度
        iArray[13][2]=100;         //最大长度
        iArray[13][3]=2;         //是否允许录入，0--不能，1--允许
        iArray[13][4]="scanno";                     
  
        CodeGrid = new MulLineEnter( "document" , "CodeGrid" ); 

        //这些属性必须在loadMulLine前
        CodeGrid.mulLineCount = 5;   
        CodeGrid.displayTitle = 1;
        CodeGrid.canSel=0;
        CodeGrid.hiddenSubtraction=1;
        CodeGrid.hiddenPlus=1;
        CodeGrid.canChk=1;
        CodeGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化CodeGrid时出错："+ ex);
      }
}
    
    
</script>
