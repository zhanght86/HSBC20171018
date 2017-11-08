 <%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称： 
//程序功能： 
//创建日期： 
//创建人  jw
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
     document.all('BusinessNo').value =  BusinessNo;
     document.all('AppNo').value =  AppNo;     
     document.all('CertificateId').value =  CertificateId;	 
  }
  catch(ex)
  {
    alert("FICertificateRBDealInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}
                            

function initForm()
{
  try
  {
    
    initInpBox();
    initResultGrid();
    queryRBResultGrid();


  }
  catch(re)
  {
    alert("FICertificateRBDealInit.jsp-->InitForm函数中发生异常:初始化界面错误!" );
    alert(re);
  }
}
 
function initResultGrid()
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
      iArray[1][0]="业务号码";         		//列名
      iArray[1][1]="120px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="凭证类型编号";         		//列名
      iArray[2][1]="120px";            		//列宽
      iArray[2][2]=200;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="凭证金额";         		//列名
      iArray[3][1]="120px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="帐务日期";         		//列名
      iArray[4][1]="120px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
 
      iArray[5]=new Array();
      iArray[5][0]="数据批次号";         		//列名
      iArray[5][1]="120px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="管理机构";         		//列名
      iArray[6][1]="120px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="凭证号码";         		//列名
      iArray[7][1]="120px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      
      RBDealResultGrid = new MulLineEnter( "document" , "RBDealResultGrid" ); 
      //这些属性必须在loadMulLine前
      RBDealResultGrid.mulLineCount = 1;   
      RBDealResultGrid.displayTitle = 1;
      RBDealResultGrid.locked = 1;
      RBDealResultGrid.canSel = 1;
      RBDealResultGrid.canChk = 0;
      RBDealResultGrid.hiddenSubtraction = 1;
      RBDealResultGrid.hiddenPlus = 1;
      RBDealResultGrid.loadMulLine(iArray);  
      
    }
    catch(ex)
    {
       alert(ex);
    }
}


</script>
