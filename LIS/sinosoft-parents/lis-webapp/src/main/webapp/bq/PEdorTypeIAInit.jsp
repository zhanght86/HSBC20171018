<%
//PEdorTypeIAInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">  
var presel = -1;
//单击时查询
function reportDetailClick(parm1,parm2)
{
  	var ex,ey;
  	ex = window.event.clientX+document.body.scrollLeft;  //得到事件的坐标x
  	ey = window.event.clientY+document.body.scrollTop;   //得到事件的坐标y
  	divLPAppntIndDetail.style.left=ex;
  	divLPAppntIndDetail.style.top =ey;
   	detailQueryClick();
}
function initInpBox()
{ 

  try
  {      
  	
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
  
  }
  catch(ex)
  {
    alert("在PEdorTypeIAInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox()
{  
  try                 
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeIAInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox(); 

    initDiv();

    initLCAppntIndGrid();

    initQuery();  
  }
  catch(re)
  {
    alert("PEdorTypeIAInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
// 信息列表的初始化
function initLCAppntIndGrid()
{
    var iArray = new Array();
      
      try
      {

      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号";    	//列名1
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="客户姓名";    	//列名1
      iArray[2][1]="50px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="客户性别";         			//列名8
      iArray[3][1]="50px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="出生日期";         		//列名5
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

      iArray[5]=new Array();
      iArray[5][0]="民族";         		//列名6
      iArray[5][1]="50px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="证件类型";         		//列名6
      iArray[6][1]="50px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="证件号码";         		//列名7
      iArray[7][1]="150px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      LCAppntIndGrid = new MulLineEnter( "fm" , "LCAppntIndGrid" ); 
      //这些属性必须在loadMulLine前
      LCAppntIndGrid.mulLineCount = 10;   
      LCAppntIndGrid.displayTitle = 1;
      LCAppntIndGrid.canSel=1;
      LCAppntIndGrid.hiddenPlus = 1;
      LCAppntIndGrid.hiddenSubtraction = 1;
      LCAppntIndGrid.selBoxEventFuncName ="reportDetailClick";
      LCAppntIndGrid.loadMulLine(iArray);  
      LCAppntIndGrid.detailInfo="单击显示详细信息";
      }
      catch(ex)
      {
        alert(ex);
      }
}

function initQuery()
{	
	var i = 0;
	var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
	var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
	showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
		
	document.all('fmtransact').value = "QUERY||MAIN";
	//alert("----begin---");
	//showSubmitFrame(mDebug);
	fm.submit();	  	 	 

}


function detailQueryClick()
{
    divPersonQuery.style.display = "";
    var sel = LCAppntIndGrid.getSelNo();

    if (sel != presel) {
        document.all("QueryCustomerNo").value = "";
        presel = sel;
    }
        
}

function initDiv()
{
	divLPAppntIndDetail.style.display ='none';

//    divDetail.style.display='none';
}

</script>