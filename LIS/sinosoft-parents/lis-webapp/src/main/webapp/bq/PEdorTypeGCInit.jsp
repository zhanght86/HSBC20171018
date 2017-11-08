<%
//GEdorTypexxxxInit.jsp
//程序功能：
//创建日期：2005-4-26 19:41
//更新记录：  更新人    更新日期     更新原因/内容
%>


<script language="JavaScript">

function initForm()
{
  try
  {
    Edorquery();
    initInpBox();
    initPolGrid();
    //initBankInfo();
    queryCustomerInfo();
    initSelQuery();
    newGetType();
    //queryBankInfo();
    //改成DIV显示 add by jiaqiangli 2008-08-22
    initNewBnfGrid();
    //查询受益人信息
    queryNewBnfGrid()
  }
  catch(re)
  {
    alert("GEdorTypeGCInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

//单击时查询
function initInpBox()
{
  try
  {
    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
    document.all('ContNo').value = top.opener.document.all('ContNo').value;
    document.all('EdorType').value = top.opener.document.all('EdorType').value;
    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
    document.all('PolNo').value = top.opener.document.all('PolNo').value;
    document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
    showOneCodeName('PEdorType','EdorType','EdorTypeName');
    try
    {
        document.getElementsByName("AppObj")[0].value = top.opener.document.getElementsByName("AppObj")[0].value;
    }
    catch (ex) {}
  }
  catch(ex)
  {
      alert("在 GEdorTypeGCInit.jsp --> InitInpBox 函数中发生异常:初始化界面错误！");
  }
}

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
        iArray[1][0]="客户号码";
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="100px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="30px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="100px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="证件类型";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="证件号码";
        iArray[7][1]="120px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        PolGrid = new MulLineEnter("fm", "PolGrid");
        //这些属性必须在loadMulLine前
        PolGrid.mulLineCount = 3;
        PolGrid.displayTitle = 1;
        PolGrid.canSel=0;
        PolGrid.canChk=0;
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

//add by jiaqiangli 2008-08-21 生存金领取形式变更
/**
         * 新受益人信息查询 MultiLine 的初始化
         */
        function initNewBnfGrid()
        {
            var iArray = new Array();                           //总数组, 返回给 MultiLine 表格

            try
            {
                iArray[0] = new Array();
                iArray[0][0] = "序号";                          //列名(顺序号, 无意义)
                iArray[0][1] = "30px";                          //列宽
                iArray[0][2] = 30;                              //列最大值
                iArray[0][3] = 0;                               //是否允许输入: 0表示不允许; 1表示允许。

                iArray[1] = new Array();
                iArray[1][0] = "被保人号";
                iArray[1][1] = "55px";
                iArray[1][2] = 100;
                iArray[1][3] = 0;


                iArray[2] = new Array();
                iArray[2][0] = "被保人姓名";
                iArray[2][1] = "50px";
                iArray[2][2] = 100;
                iArray[2][3] = 0;

                iArray[3] = new Array();
                iArray[3][0] = "受益人类别";
                iArray[3][1] = "0px";
                iArray[3][2] = 100;
                iArray[3][3] = 2;


                iArray[4] = new Array();
                iArray[4][0] = "受益人姓名";
                iArray[4][1] = "50px";
                iArray[4][2] = 100;
                iArray[4][3] = 0;
                iArray[4][9] = "受益人姓名|NotNull&Len<=60";

                iArray[5] = new Array();
                iArray[5][0] = "无";
                iArray[5][1] = "0px";
                iArray[5][2] = 0;
                iArray[5][3] = 0;
               
                iArray[6] = new Array();
                iArray[6][0] = "证件类型";
                iArray[6][1] = "40px";
                iArray[6][2] = 100;
                iArray[6][3] = 0;

                iArray[7] = new Array();
                iArray[7][0] = "证件号码";
                iArray[7][1] = "80px";
                iArray[7][2] = 100;
                iArray[7][3] = 0;
                iArray[7][9] = "证件号码|NotNull&Len<=20";

                iArray[8] = new Array();
                iArray[8][0] = "与被保人关系";
                iArray[8][1] = "50px";
                iArray[8][2] = 100;
                iArray[8][3] = 0;

                iArray[9] = new Array();
                iArray[9][0] = "受益顺序";
                iArray[9][1] = "40px";
                iArray[9][2] = 100;
                iArray[9][3] = 0;

                iArray[10] = new Array();
                iArray[10][0] = "受益份额";
                iArray[10][1] = "40px";
                iArray[10][2] = 100;
                iArray[10][3] = 0;
                iArray[10][9] = "受益份额|NotNull&Value>=0&Value<=1";

                iArray[11] = new Array();
                iArray[11][0] = "速填";
                iArray[11][1] = "0px";
                iArray[11][2] = 100;
                iArray[11][3] = 2;
                iArray[11][10] = "CustomerType";
                //iArray[11][11] = getCustomerType();
                iArray[11][19] = 1;
                
                iArray[12] = new Array();
                iArray[12][0] = "性别";
                iArray[12][1] = "80px";
                iArray[12][2] = 100;
                iArray[12][3] = 0;
                
                iArray[13] = new Array();
                iArray[13][0] = "出生日期";
                iArray[13][1] = "80px";
                iArray[13][2] = 100;
                iArray[13][3] = 0;
                
                //隐藏这三列
                iArray[14] = new Array();
                iArray[14][0] = "住址";
                iArray[14][1] = "0px";
                iArray[14][2] = 100;
                iArray[14][3] = 1;
                                
                iArray[15] = new Array();
                iArray[15][0] = "邮编";
                iArray[15][1] = "0px";
                iArray[15][2] = 100;
                iArray[15][3] = 1;
                                
                iArray[16] = new Array();
                iArray[16][0] = "备注信息";
                iArray[16][1] = "0px";
                iArray[16][2] = 100;
                iArray[16][3] = 1;
                
                //add by jiaqiangli 银行编码、银行帐号、银行帐户名
                iArray[17] = new Array();
                iArray[17][0] = "银行编码";
                iArray[17][1] = "80px";
                iArray[17][2] = 100;
                iArray[17][3] = 2;
                iArray[17][4] = "bank";
                iArray[17][9] = "银行编码|NotNull&Code:bank";
                
                                
                iArray[18] = new Array();
                iArray[18][0] = "银行帐号";
                iArray[18][1] = "120px";
                iArray[18][2] = 100;
                iArray[18][3] = 1;
                iArray[18][22]="confirmSecondInput1"
                                
                iArray[19] = new Array();
                iArray[19][0] = "银行帐户名";
                iArray[19][1] = "80px";
                iArray[19][2] = 100;
                iArray[19][3] = 1;
            }
            catch (ex)
            {
                alert("在 PEdorTypeGCInit.jsp --> initNewBnfGrid 函数中发生异常: 初始化数组错误！");
            }

            try
            {
                NewBnfGrid = new MulLineEnter("fm", "NewBnfGrid");
                NewBnfGrid.mulLineCount = 0;
                NewBnfGrid.displayTitle = 1;
                NewBnfGrid.locked = 1;
                NewBnfGrid.hiddenPlus = 1;
                NewBnfGrid.hiddenSubtraction = 1;
                NewBnfGrid.canChk = 0;
                NewBnfGrid.canSel = 0;
                NewBnfGrid.chkBoxEventFuncName = "";
                NewBnfGrid.selBoxEventFuncName = "";
                //上面属性必须在 MulLineEnter loadMulLine 之前
                NewBnfGrid.loadMulLine(iArray);
            }
            catch (ex)
            {
                alert("在 PEdorTypeGCInit.jsp --> initNewBnfGrid 函数中发生异常: 初始化界面错误！");
            }
        }
        
</script>
