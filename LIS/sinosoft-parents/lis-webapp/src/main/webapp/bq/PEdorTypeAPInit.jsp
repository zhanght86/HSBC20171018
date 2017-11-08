<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : sino
 * @version  : 1.00, 1.01
 * @date     : 2005-11-13, 2006-03-11
 * @direction: 保全保费自垫申请、终止初始化
 ******************************************************************************/
%>

    <!-- 调用 JSP Init 初始化页面 : 开始 -->

    <script language="JavaScript">

        /**
         * 总函数，初始化整个页面
         */
        function initForm()
        {
            try
            {
                EdorQuery();
                initInputBox();
                initPolGrid() ;
                queryPolInfo();
                queryAutoPayFlag();
            }
            catch (ex)
            {
                alert("在 PEdorTypeAPInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
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
                //document.getElementsByName("PolNo")[0].value = top.opener.document.getElementsByName("PolNo")[0].value;
                document.getElementsByName("EdorItemAppDate")[0].value = top.opener.document.getElementsByName("EdorItemAppDate")[0].value;
                document.getElementsByName("EdorValiDate")[0].value = top.opener.document.getElementsByName("EdorValiDate")[0].value;
                showOneCodeName('PEdorType', 'EdorType', 'EdorTypeName');
            }
            catch (ex)
            {
                alert("在 PEdorTypeAPInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
            }
        }
        
  //险种列表      
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
        iArray[1][0]="被保人客户号";
        iArray[1][1]="60px";
        iArray[1][2]=60;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="被保人姓名";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="投保人客户号";
        iArray[3][1]="60px";
        iArray[3][2]=80;
        iArray[3][3]=0;
        iArray[3][21]=3;

        iArray[4]=new Array();
        iArray[4][0]="投保人姓名";
        iArray[4][1]="60px";
        iArray[4][2]=80;
        iArray[4][3]=0;
        iArray[4][21]=3;

        iArray[5]=new Array();
        iArray[5][0]="险种名称";
        iArray[5][1]="100px";
        iArray[5][2]=80;
        iArray[5][3]=0;
        iArray[5][21]=3;

        iArray[6]=new Array();
        iArray[6][0]="保费标准";
        iArray[6][1]="60px";
        iArray[6][2]=80;
        iArray[6][3]=0;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="基本保额";
        iArray[7][1]="60px";
        iArray[7][2]=80;
        iArray[7][3]=7;
        iArray[7][21]=3;
        iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="份数";
        iArray[8][1]="50px";
        iArray[8][2]=80;
        iArray[8][3]=0;
        iArray[8][21]=3;

        iArray[9]=new Array();
        iArray[9][0]="PolNo";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=0;
        iArray[9][21]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="币种";
        iArray[10][1]="60px";
        iArray[10][2]=100;
        iArray[10][3]=2;
        iArray[10][4]="currency";

      PolGrid = new MulLineEnter("fm", "PolGrid");
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 1;
      PolGrid.displayTitle = 1;
      PolGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolGrid.hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);
      PolGrid.selBoxEventFuncName ="" ;
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

    </script>

    <!-- 调用 JSP Init 初始化页面 : 结束 -->

