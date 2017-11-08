<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2007 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zengyg <XinYQ@sinosoft.com.cn>
 * @version  : 1.00
 * @date     : 2007-05-24
 * @direction: 保全万能险部分领取初始化界面
 ******************************************************************************/
%>                          

<script language="JavaScript">  
function initForm()
{
  try
  {
     initInpBox();
     initPolGrid();
     getPolInfo();
     queryAccData();
     initRemark(); 
     Edorquery();

  }
  catch(re)
  {
    alert("PEdorTypeRCInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{ 
  try
  {   
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value; 
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value; 
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value; 
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;

    var SQL = "select EdorName from lmedoritem where EdorCode = '"+document.all('EdorType').value +"'";
    var arr = easyExecSql(SQL);
    document.all('EdorTypeName').value=arr[0][0];
    //showOneCodeName('edorcode','EdorCode','EdorName');
  }
  catch(ex)
  {
    alert("初始化界面错误!");
  }
    var tEdorAcceptNo=document.all("EdorAcceptNo").value;
    try{
        var SQL = "select standbyflag3 from lpedoritem where EdorAcceptNo = '"+tEdorAcceptNo+"'";
        var arr = easyExecSql(SQL);
        if(arr){
            document.all('Remark').value = arr[0][0];  
        } 
    }catch(ex){
        alert("在 PEdorTypeOPInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }      
}
var PolGrid;
function initPolGrid()
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
        iArray[1][0]="被保险人号码";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="姓名";
        iArray[2][1]="60px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="险种代码";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="险种名称";
        iArray[4][1]="150px";
        iArray[4][2]=100;
        iArray[4][3]=0;


        iArray[5]=new Array();
        iArray[5][0]="保费标准";
        iArray[5][1]="60px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="基本保额";
        iArray[6][1]="60px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="生效日期";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="保单号码";
        iArray[8][1]="0px";
        iArray[8][2]=100;
        iArray[8][3]=3;

        iArray[9]=new Array();
        iArray[9][0]="集体保单号码";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=3;


      PolGrid = new MulLineEnter( "fm" , "PolGrid" );
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 0;
      PolGrid.displayTitle = 1;
      //PolGrid.canChk=1;
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