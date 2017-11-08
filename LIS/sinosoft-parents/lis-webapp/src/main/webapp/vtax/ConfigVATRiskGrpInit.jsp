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

function initForm()
{
  try
  {
    initRiskGrpGrid(); 
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


function initRiskGrpGrid()
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
      iArray[1][0]="险种编码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="50px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种组";    	                //列名
      iArray[2][1]="80px";            		        //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="记录ID";    	                //列名
      iArray[3][1]="60px";            		        //列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
            
      
      RiskGrpGrid = new MulLineEnter( "fm" , "RiskGrpGrid" );

      //这些属性必须在loadMulLine前
      RiskGrpGrid.mulLineCount = 5;
      RiskGrpGrid.displayTitle = 1;
      RiskGrpGrid.canChk =0;
      RiskGrpGrid.canSel =1;
      RiskGrpGrid.selBoxEventFuncName ="showRiskGrpDetail";
      RiskGrpGrid.locked =1;            //是否锁定：1为锁定 0为不锁定
      RiskGrpGrid.hiddenPlus=1;        //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏 
      RiskGrpGrid.hiddenSubtraction=1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏  
      RiskGrpGrid.recordNo=0;         //设置序号起始基数为10，如果要分页显示数据有用
      RiskGrpGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }



</script>