<%
/*******************************************************************************
 * <p>Title       : 增值税税率</p>
 * <p>Description : 税率配置</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        :
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script language="JavaScript">
var turnPage = new turnPageClass(); 
var FeeTypeCode='<%=request.getParameter("FeeTypeCode")%>';
var ConfigID = '<%=request.getParameter("configID")%>';
strSql ="SELECT FeeTypeName FROM LDVATConfig WHERE id = '"+ConfigID+"' AND FeeTypeCode='"+FeeTypeCode+"'" ;
var FeeTypeName = easyExecSql(strSql );
function initForm()
{
  try
  {
    initInpBox();
    initTaxRateGrid(); 
  }
  catch(re)
  {
    alert("初始化界面错误!-initInpBox");
  }
  try {
		query(); 
	
		}catch(e) {
			alert("TaxRateGrid error");
		}
}

function query()
{

    mySql = new SqlClass();
    mySql.setResourceName("vrate.VatRate");
    mySql.setSqlId("VATRateSql2");
    mySql.addSubPara(FeeTypeCode);   
    mySql.addSubPara(ConfigID);
    initTaxRateGrid();
	turnPage.queryModal(mySql.getString(), TaxRateGrid);
}

function initInpBox()
{ 
  try
  {               
      document.all('TFeeTypeCode').value= FeeTypeCode;
      document.all('ConfigID').value = ConfigID;
      document.all('TFeeTypeName').value = FeeTypeName;
  }
  catch(ex)
  {
    alert("在ConfigVATRateInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initTaxRateGrid()
{

    var iArray = new Array();

      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";         			//列宽
      iArray[0][2]=10;          			//列最大值
      iArray[0][3]=0;

      iArray[1]=new Array();
      iArray[1][0]="费用类型";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="50px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="费用类型名称";    	                //列名
      iArray[2][1]="80px";            		        //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="险种组";    	                //列名
      iArray[3][1]="80px";            		        //列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[4]=new Array();
      iArray[4][0]="机构";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[4][1]="80px";         			//列宽
      iArray[4][2]=10;          			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="税率";    	                //列名
      iArray[5][1]="80px";            		        //列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[6]=new Array();
      iArray[6][0]="起期";    	                //列名
      iArray[6][1]="80px";            		        //列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[7]=new Array();
      iArray[7][0]="止期";    	                //列名
      iArray[7][1]="80px";            		        //列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[8]=new Array();
      iArray[8][0]="记录ID";    	                //列名
      iArray[8][1]="60px";            		        //列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=3;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      
      TaxRateGrid = new MulLineEnter( "fm" , "TaxRateGrid" );

      //这些属性必须在loadMulLine前
      TaxRateGrid.mulLineCount = 5;
      TaxRateGrid.displayTitle = 1;
      TaxRateGrid.canChk =0;
      TaxRateGrid.canSel =1;
      TaxRateGrid.selBoxEventFuncName ="showVATRateDetail";
      TaxRateGrid.locked =1;            //是否锁定：1为锁定 0为不锁定
      TaxRateGrid.hiddenPlus=1;        //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏 
      TaxRateGrid.hiddenSubtraction=1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏  
      TaxRateGrid.recordNo=0;         //设置序号起始基数为10，如果要分页显示数据有用
      TaxRateGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }



</script>