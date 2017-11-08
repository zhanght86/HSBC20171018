<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : 罗辉 <luohui@sinosoft.com.cn>
 * @version  : 1.00, 1.01, 1.02, 1.03
 * @date     : 2006-10-10, 2006-10-13, 2006-11-01, 2006-11-22
 * @direction: 恢复交费初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        var CustomerGrid;    //全局变量, 保单客户表格

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            { 
                EdorQuery();
                //initHiddenArea();       
                initInputBox();
                initCustomerGrid();
                initPolGrid();
                getCustomerGrid(); 
                showRiskInfo();
            }
            catch (ex)
            {
                alert("在 PEdorTypeBCInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
            }
        }

        /**
         * 输入框的初始化
         */
        function initInputBox()
        {
            try
            {
                //文本框
                document.getElementsByName("EdorAcceptNo")[0].value = top.opener.document.getElementsByName("EdorAcceptNo")[0].value;
                document.getElementsByName("EdorNo")[0].value = top.opener.document.getElementsByName("EdorNo")[0].value;
                document.getElementsByName("EdorType")[0].value = top.opener.document.getElementsByName("EdorType")[0].value;
                document.getElementsByName("ContNo")[0].value = top.opener.document.getElementsByName("ContNo")[0].value;
                document.getElementsByName("PolNo")[0].value = top.opener.document.getElementsByName("PolNo")[0].value;
                document.getElementsByName("InsuredNo")[0].value = top.opener.document.getElementsByName("InsuredNo")[0].value;
                document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
                document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
                showOneCodeName("PEdorType", "EdorType", "EdorTypeName");
                showOneCodeName("HesitateFlag", "HesitateFlag", "HesitateFlagName");
            }
            catch (ex)
            {
                alert("在 PEdorTypeHJInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }
        function showRiskInfo()
        {
            
            var tContNo=document.all("ContNo").value;
            if(tContNo!=null&&tContNo!="")
            {        
                var strSQL = "select a.riskcode,b.riskname,a.insuredname,a.amnt,a.mult,a.prem,a.cvalidate,a.enddate from lcpol a,lmrisk b where a.riskcode = b.riskcode and a.contno = '"+tContNo+"' order by a.PolNo asc "; 
                turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
                //判断是否查询成功
                if (!turnPage.strQueryResult) 
                {
                   return;
                }
                //查询成功则拆分字符串，返回二维数组
                turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
                //设置初始化过的MULTILINE对象
                turnPage.pageDisplayGrid = PolGrid;    
                //保存SQL语句
                turnPage.strQuerySql = strSQL; 
                //设置查询起始位置
                turnPage.pageIndex = 0;  
                //在查询结果数组中取出符合页面显示大小设置的数组
                arrDataSet = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
                //调用MULTILINE对象显示查询结果
                displayMultiline(arrDataSet, turnPage.pageDisplayGrid);
            }

        }
        //客户基本信息列表
        
function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                  //列宽
        iArray[0][2]=30;                      //列最大值
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="90px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="90px";
        iArray[2][2]=50;
        iArray[2][3]=0;
        iArray[2][21]=2;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="90px";
        iArray[3][2]=100;
        iArray[3][3]=0;
        iArray[3][21]=2;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="80px";
        iArray[4][2]=30;
        iArray[4][3]=0;
        iArray[4][21]=2;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="90px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=2;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="150px";
        iArray[7][2]=200;
        iArray[7][3]=0;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //这些属性必须在loadMulLine前
        CustomerGrid.mulLineCount = 0;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        CustomerGrid.hiddenPlus = 1;
        CustomerGrid.hiddenSubtraction = 1;
        CustomerGrid.selBoxEventFuncName = "";
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypeFTInit.jsp --> initCustomerGrid 函数中发生异常:初始化界面错误！");
    }
    
}
function initPolGrid()
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
      iArray[1][0]="险种代码";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[1][1]="50px";         			//列宽
      iArray[1][2]=10;          			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="险种名称";					//列名1
      iArray[2][1]="200px";            		//列宽
      iArray[2][2]=50;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="被保人姓名";         			//列名2
      iArray[3][1]="60px";            		//列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="基本保额";         		//列名8
      iArray[4][1]="60px";            		//列宽
      iArray[4][2]=30;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[5]=new Array();
      iArray[5][0]="份数";     //列名6
      iArray[5][1]="60px";            		//列宽
      iArray[5][2]=50;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="保费标准";         		//列名8
      iArray[6][1]="60px";            		//列宽
      iArray[6][2]=30;            			//列最大值
      iArray[6][3]=0;

      iArray[7]=new Array();
      iArray[7][0]="险种起期";         		//列名5
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用
      
      iArray[8]=new Array();
      iArray[8][0]="险种止期";         		//列名5
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许,2表示代码引用

    

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前        
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel=0;
      PolGrid.hiddenPlus = 1; 
      PolGrid.hiddenSubtraction = 1;
      PolGrid.selBoxEventFuncName ="";
      PolGrid.loadMulLine(iArray);  
      PolGrid.detailInfo="单击显示详细信息";      
      PolGrid.loadMulLine(iArray);        
      }
      catch(ex)
      {
      
      }
}
    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

