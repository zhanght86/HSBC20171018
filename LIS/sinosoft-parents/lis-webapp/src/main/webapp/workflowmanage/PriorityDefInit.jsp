<%
/*******************************************************************************
 * <p>Title       : 任务分配</p>
 * <p>Description : 用来将系统中已经申请的任务分配给其它员工</p>
 * <p>Copyright   : Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company     : 中科软科技股份有限公司</p>
 * <p>WebSite     : http://www.sinosoft.com.cn</p>
 * @author        :
 * @version       : 1.00
 * @date          : 2007-12-25
 ******************************************************************************/
%>
<script language="JavaScript">

function initWorkFlowGridP()
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
      iArray[1][0]="紧急度类型";         		//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="80px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]= "priority";
      iArray[1][5]="1|6";     //引用代码对应第几列，'|'为分割符
	  iArray[1][6]="0|1";    //上面的列中放置引用代码中第几位值

      iArray[2]=new Array();
      iArray[2][0]="活动ID";    	            //列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[3]=new Array();
      iArray[3][0]="活动名称";    	        //列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[3][7]="getProcessName";  


      iArray[4]=new Array();
      iArray[4][0]="对应SQL";    	                //列名
      iArray[4][1]="400px";            		        //列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[5]=new Array();
      iArray[5][0]="ProcessID";    	                //列名
      iArray[5][1]="50px";            		        //列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=3;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[6]=new Array();
      iArray[6][0]="优先级ID";    	                //列名
      iArray[6][1]="0px";            		        //列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      iArray[7]=new Array();
      iArray[7][0]="流程版本";    	                //列名
      iArray[7][1]="100px";            		        //列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=1;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      
      WorkFlowGridP = new MulLineEnter( "fm" , "WorkFlowGridP" );

      //这些属性必须在loadMulLine前
      WorkFlowGridP.mulLineCount = 5;
      WorkFlowGridP.displayTitle = 1;
      WorkFlowGridP.canChk =0;
      WorkFlowGridP.canSel =1;
      WorkFlowGridP.locked =0;            //是否锁定：1为锁定 0为不锁定
      WorkFlowGridP.hiddenPlus=0;        //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
      WorkFlowGridP.hiddenSubtraction=1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
      WorkFlowGridP.recordNo=0;         //设置序号起始基数为10，如果要分页显示数据有用
      WorkFlowGridP.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }
function initWorkGridPa()
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
      iArray[1][0]="紧急度类型";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="80px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[2]=new Array();
      iArray[2][0]="颜色名称 	";                  //列名
      iArray[2][1]="150px";            		        //列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=2;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[2][4]= "prioritycolor";
      iArray[2][5]="2|3";     //引用代码对应第几列，'|'为分割符
	  iArray[2][6]="0|1";    //上面的列中放置引用代码中第几位值
      
      iArray[3]=new Array();
      iArray[3][0]="COLORID";    	                //列名
      iArray[3][1]="0px";            		        //列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的


      iArray[4]=new Array();
      iArray[4][0]="时效范围";    	                //列名
      iArray[4][1]="150px";            		        //列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=1;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      
      iArray[5]=new Array();
      iArray[5][0]="优先级ID";    	                //列名
      iArray[5][1]="0px";            		        //列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      
      
      WorkGridPa = new MulLineEnter( "fmb" , "WorkGridPa" );

      //这些属性必须在loadMulLine前
      WorkGridPa.mulLineCount = 5;
      WorkGridPa.displayTitle = 1;
      WorkGridPa.canChk =0;
      WorkGridPa.canSel =1;
      WorkGridPa.locked =0;            //是否锁定：1为锁定 0为不锁定
      WorkGridPa.hiddenPlus=0;        //是否隐藏"+"添加一行标志：1为隐藏；0为不隐藏
      WorkGridPa.hiddenSubtraction=1; //是否隐藏"-"添加一行标志：1为隐藏；0为不隐藏
      WorkGridPa.recordNo=0;         //设置序号起始基数为10，如果要分页显示数据有用
      WorkGridPa.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert(ex);
      }
  }

</script>
