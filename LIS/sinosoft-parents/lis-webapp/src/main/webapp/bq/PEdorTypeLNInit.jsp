<%
//程序名称：PEdorInputInit.jsp
//程序功能：
//创建日期：2005-4-29 11:32上午
//创建人  ：Lihs
//更新记录：  更新人    更新日期     更新原因/内容
%>

<script language="JavaScript">

var LoanGrid;

function initForm()
{
    try
    {
        initInputBox();
        //initQueryPolInfo();
        //getPolInfo(document.all('ContNo').value);
        initCustomerGrid();
        initLoanGrid();
        //initBankInfo();
        queryCustomerGrid();
    }
    catch (ex)
    {
        alert("在 PEdorTypeLNInit.jsp --> initForm 函数中发生异常: 初始化界面错误！");
    }
}

function initInputBox()
{
    try
    {
        Edorquery();
        document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
        document.all('ContNo').value = top.opener.document.all('ContNo').value;
        document.all('EdorType').value = top.opener.document.all('EdorType').value;
        document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
        document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
        document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
        document.all('PolNo').value = top.opener.document.all('PolNo').value;
        document.all('InsuredNo').value = top.opener.document.all('InsuredNo').value;
        //document.all('ContType').value ="1";
        showOneCodeName('PEdorType','EdorType','EdorTypeName');
    }
    catch (ex)
    {
        alert("在 PEdorTypeLNInit.jsp --> initInputBox 函数中发生异常: 初始化界面错误！");
    }
}

function initSelQuery()
{
  var i = 0;
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
  var iWidth=550;      //弹出窗口的宽度; 
  var iHeight=250;     //弹出窗口的高度; 
  var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
  var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
  showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

  showInfo.focus();

  document.all('fmtransact').value = "QUERY||MAIN";
  fm.submit();
}
function initQueryPolInfo()
{
    var tContNo=top.opener.document.all('ContNo').value;
    var strSQL=""
    var arrSelected = new Array();
    turnPage.pageDisplayGrid = LoanGrid;
    turnPage.pageIndex = 0;
    //alert("------"+tContNo+"---------");
    if(tContNo!=null || tContNo !='')
      {
//       strSQL = "SELECT RISKCODE,INSUREDNO,INSUREDNAME,AMNT,PREM,'','' FROM LCPOL WHERE MAINPOLNO=POLNO AND "
//                             +"CONTNO='"+tContNo+"'";
      var sqlid1="PEdorTypeLNInputSql4";
      var mySql1=new SqlClass();
      mySql1.setResourceName("bq.PEdorTypeLNInputSql"); //指定使用的properties文件名
      mySql1.setSqlId(sqlid1);//指定使用的Sql的id
      mySql1.addSubPara(tContNo);//指定传入的参数
      strSQL=mySql1.getString();
    //alert("-----------"+strSQL+"------------");
    }
    else
    {
        alert('没有相应的险种代码！');
        return;
    }
     turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
     if(!turnPage.strQueryResult)
     {
            alert("查询失败！");
     }
     else
     {
      arrSelected = decodeEasyQueryResult(turnPage.strQueryResult);
      displayMultiline(arrSelected, turnPage.pageDisplayGrid);
      }
}

function initCustomerGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="100px";
        iArray[1][2]=100;
        iArray[1][3]=0;

         iArray[2]=new Array();
        iArray[2][0]="角色";
        iArray[2][1]="80px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="姓名";
        iArray[3][1]="80px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="证件类型";
        iArray[4][1]="90px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="证件号码";
        iArray[5][1]="140px";
        iArray[5][2]=150;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="性别";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=0;
        iArray[6][21]=2;

        iArray[7]=new Array();
        iArray[7][0]="出生日期";
        iArray[7][1]="80px";
        iArray[7][2]=100;
        iArray[7][3]=8;
        iArray[7][21]=3;

        CustomerGrid = new MulLineEnter("fm", "CustomerGrid");
        //这些属性必须在loadMulLine前
        CustomerGrid.mulLineCount = 3;
        CustomerGrid.displayTitle = 1;
        CustomerGrid.canSel=0;
        //PolGrid.canChk=1;
        CustomerGrid.hiddenPlus=1;
        CustomerGrid.hiddenSubtraction=1;
        CustomerGrid.loadMulLine(iArray);
    }
    catch (ex)
    {
        alert("在 PEdorTypeLNInit.jsp --> initCustomerGrid 函数中发生异常: 初始化界面错误！");
    }
}

// 信息列表的初始化
function initLoanGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                    //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                    //列宽
        iArray[0][2]=30;                        //列最大值
        iArray[0][3]=0;                         //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="合同号";
        iArray[1][1]="80px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="借款日期";
        iArray[2][1]="60px";
        iArray[2][2]=300;
        iArray[2][3]=8;

        iArray[3]=new Array();
        iArray[3][0]="基本保额";
        iArray[3][1]="70px";
        iArray[3][2]=100;
        iArray[3][3]=7;
        iArray[3][21]=3;
        iArray[3][23]="0";

        iArray[4]=new Array();
        iArray[4][0]="保费标准";
        iArray[4][1]="70px";
        iArray[4][2]=100;
        iArray[4][3]=7;
        iArray[4][21]=3;
        iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="现金价值";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=7;
        iArray[5][21]=3;
        iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="贷款限额";
        iArray[6][1]="70px";
        iArray[6][2]=100;
        iArray[6][3]=7;
        iArray[6][21]=3;
        iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="贷款利率";
        iArray[7][1]="70px";
        iArray[7][2]=100;
        iArray[7][3]=7;
        iArray[7][21]=0;
        iArray[7][23]="0";
  
        iArray[8]=new Array();
        iArray[8][0]="历史贷款本息累计";
        iArray[8][1]="70px";
        iArray[8][2]=100;
        iArray[8][3]=7;
        iArray[8][21]=0;
        iArray[8][23]="0";
        
        iArray[9]=new Array();
        iArray[9][0]="币种";
        iArray[9][1]="60px";
        iArray[9][2]=100;
        iArray[9][3]=2;
        iArray[9][4]="currency";
        
        iArray[10]=new Array();
        iArray[10][0]="本次借款";
        iArray[10][1]="70px";
        iArray[10][2]=100;
        iArray[10][3]=7;
        iArray[10][21]=0;
        iArray[10][23]="0";


        LoanGrid = new MulLineEnter("fm", "LoanGrid");
        //这些属性必须在loadMulLine前
        //LoanGrid.mulLineCount = 6;
        LoanGrid.canSel = 1;
        LoanGrid.displayTitle = 1;
        LoanGrid.hiddenPlus = 1;
        LoanGrid.hiddenSubtraction = 1;
		LoanGrid.selBoxEventFuncName = "ShowLoanInfo";
        LoanGrid.loadMulLine(iArray);

        //后台查询，初始化界面MulLine用数组
        fm.action="./PEdorTypeLNCount.jsp";
        fm.submit();
    }
    catch (ex)
    {
        alert("在 PEdorTypeLNInit.jsp --> initLoanGrid 函数中发生异常: 初始化界面错误！");
    }
}

function initBankInfo()
{
     //var i = 0;
     var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
     var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
     //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	 var name='提示';   //网页名称，可为空; 
		var iWidth=550;      //弹出窗口的宽度; 
		var iHeight=250;     //弹出窗口的高度; 
		var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
		var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
		showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

		showInfo.focus();

   var strSql = "";
//    strSql = "select AppntNo,AppntName,Prem,Amnt,PayMode,BankCode,BankAccNo,AccName,PayLocation from LPCont where 1 =1" + " "
//               + getWherePart('ContNo')
//               + getWherePart('EdorNo')
//               + getWherePart('EdorType')
 //alert(strSql);
   var sqlid1="PEdorTypeLNInputSql5";
   var mySql1=new SqlClass();
   mySql1.setResourceName("bq.PEdorTypeLNInputSql"); //指定使用的properties文件名
   mySql1.setSqlId(sqlid1);//指定使用的Sql的id
   mySql1.addSubPara(window.document.getElementsByName("ContNo")[0].value);//指定传入的参数
   mySql1.addSubPara(window.document.getElementsByName("EdorNo")[0].value);//指定传入的参数
   mySql1.addSubPara(window.document.getElementsByName("EdorType")[0].value);//指定传入的参数
   strSql=mySql1.getString();
   var arrResult = easyExecSql(strSql, 1, 0);
   if (arrResult == null)
   {
       //alert("没有相应的保单信息");
       showInfo.close();
           document.all('BankCode').value = "";
           document.all('BankAccNo').value = "";
           document.all('AccName').value = "";
           document.all('PayLocation').value = "";
   }
   else
   {
       displayBank(arrResult);
       showInfo.close();
   }

   //turnPage.strQueryResult  = easyQueryVer3(strSql, 1, 1, 1);
     //document.all('fmtransact').value = "QUERY||PayLocation";
     //fm.submit();

}

</script>