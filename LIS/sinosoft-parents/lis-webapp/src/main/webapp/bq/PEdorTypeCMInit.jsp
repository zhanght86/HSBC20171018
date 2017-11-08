<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-12-3
 * @direction: 客户重要资料变更
 ******************************************************************************/
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>

<script language="JavaScript">

function initInpBox()
{

  try
  {
    Edorquery();
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('CustomerNo').value = top.opener.document.all('InsuredNo').value;    //XinYQ modified on 2006-03-29 : old : document.all('CustomerNo').value = top.opener.document.all('CustomerNoBak').value
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('EdorCode','EdorType','EdorTypeName');
  }
  catch(ex)
  {
    alert("在PEdorTypeCMInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initSelBox()
{
  try
  {
  }
  catch(ex)
  {
    alert("在PEdorTypeCMInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}

function initForm()
{
  try
  {
    initInpBox();
    initInpCustomerInfo();
    initContNewGrid();
    queryRelationCont();
    initInpPCustomerInfo();
  }
  catch(re)
  {
    alert("PEdorTypeCMInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}



function initContNewGrid()
{
    var iArray = new Array();
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=10;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保单号码";
        iArray[1][1]="100px";
        iArray[1][2]=60;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="被保人客户号";
        iArray[2][1]="100px";
        iArray[2][2]=60;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="生效日期";
        iArray[3][1]="100px";
        iArray[3][2]=80;
        iArray[3][3]=8;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="保费标准";
        iArray[4][1]="100px";
        iArray[4][2]=80;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";
       

        iArray[5]=new Array();
        iArray[5][0]="基本保额";
        iArray[5][1]="100px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";
        
        iArray[6]=new Array();
				iArray[6][0]="币种";
				iArray[6][1]="60px";
				iArray[6][2]=100;
				iArray[6][3]=2;
				iArray[6][4]="currency";


      ContNewGrid = new MulLineEnter("fm", "ContNewGrid");
      ContNewGrid.mulLineCount = 1;
      ContNewGrid.displayTitle = 1;
      ContNewGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      ContNewGrid.hiddenSubtraction=1;
      ContNewGrid.loadMulLine(iArray);
      ContNewGrid.selBoxEventFuncName ="" ;
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}





</script>