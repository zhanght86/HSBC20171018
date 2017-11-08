<%@include file="/i18n/language.jsp"%>
<%
//程序名称：ProposalQueryInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>                         

<script type="text/javascript">
//返回按钮初始化
var str = "";
/**
function initDisplayButton()
{
	var tDisplay = <%=//tDisplay%>;
	if (tDisplay=="1")
	{
		fm.Return.style.display='';
	}
	else if (tDisplay=="0")
	{
		fm.Return.style.display='none';
	}
}
*/
// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {                                   
	// 保单查询条件
    fm.all('PolNo').value = '';
    fm.all('GrpPolNo').value = '';
    fm.all('RiskCode').value = '';
    fm.all('CessStart').value = '';
    fm.all('ReinsurItem').value = '';
    fm.all('ProtItem').value = '';
    fm.all('InsuredName').value = '';
    fm.all('InsuredNo').value = '';
    fm.all('InsuredBirthday').value = '';
  }
  catch(ex)
  {
    myAlert("在AllReinsurPolQueryInit.jsp-->"+"初始化界面错误!");
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
    myAlert("在ProposalQueryInit.jsp-->"+"InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
	initPolGrid();
//	initDisplayButton();
	
  }
  catch(re)
  {
    myAlert("ProposalQueryInit.jsp-->"+"初始化界面错误!");
  }
}

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
      iArray[1][0]="保单号码";         		//列名
      iArray[1][1]="150px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]="再保项目";         		//列名
      iArray[2][1]="30px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="集体保单号";         		//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="险种编码";         		//列名
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=200;            			//列最大值
      iArray[4][3]=2; 
      iArray[4][4]="RiskCode";              	        //是否引用代码:null||""为不引用
      iArray[4][5]="4";              	                //引用代码对应第几列，'|'为分割符
      iArray[4][9]="险种编码|code:RiskCode&NOTNULL";
      iArray[4][18]=250;
      iArray[4][19]= 0 ;
     
      
      iArray[5]=new Array();
      iArray[5][0]="签单日期";         		//列名
      iArray[5][1]="80px";            		//列宽
      iArray[5][2]=200;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="生效日期";         		//列名
      iArray[6][1]="80px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="被保人";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;    
      
      iArray[8]=new Array();
      iArray[8][0]="标准保费";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;       
      
      iArray[9]=new Array();
      iArray[9][0]="保险金额";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=200;            			//列最大值
      iArray[9][3]=0;       
      
      iArray[10]=new Array();
      iArray[10][0]="分保比例";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=200;            			//列最大值
      iArray[10][3]=0;       
      
      iArray[11]=new Array();
      iArray[11][0]="分保金额";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=200;            			//列最大值
      iArray[11][3]=0;       
      
      iArray[12]=new Array();
      iArray[12][0]="分保保费 ";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=200;            			//列最大值
      iArray[12][3]=0;       
      
      iArray[13]=new Array();
      iArray[13][0]="分保佣金 ";         		//列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=200;            			//列最大值
      iArray[13][3]=0;       
      
      iArray[14]=new Array();
      iArray[14][0]="额外保费 ";         		//列名
      iArray[14][1]="60px";            		//列宽
      iArray[14][2]=200;            			//列最大值
      iArray[14][3]=0;       
      
      iArray[15]=new Array();
      iArray[15][0]="额外分保费 ";         		//列名
      iArray[15][1]="60px";            		//列宽
      iArray[15][2]=200;            			//列最大值
      iArray[15][3]=0;       
      
      iArray[16]=new Array();
      iArray[16][0]="额外分保佣金 ";         		//列名
      iArray[16][1]="60px";            		//列宽
      iArray[16][2]=200;            			//列最大值
      iArray[16][3]=0;       
      
      iArray[17]=new Array();
      iArray[17][0]="被保人投保年龄 ";         		//列名
      iArray[17][1]="60px";            		//列宽
      iArray[17][2]=200;            			//列最大值
      iArray[17][3]=0;       

      iArray[18]=new Array();
      iArray[18][0]="被保人性别";         		//列名
      iArray[18][1]="60px";            		//列宽
      iArray[18][2]=200;            			//列最大值
      iArray[18][3]=0;       
      
                			//是否允许输入,1表示允许，0表示不允许
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        myAlert(ex);
      }
}

</script>