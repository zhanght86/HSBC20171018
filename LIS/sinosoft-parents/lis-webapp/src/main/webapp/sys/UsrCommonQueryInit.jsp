<%
//程序名称：UsrCommonQueryInit.jsp
//程序功能：系统用户信息查询
//创建日期：2005-11-30
//创建人  ：liurx
//更新记录：  更新人    更新日期     更新原因/内容
%>
<script language="JavaScript">

function initForm()
{
  try
  {
    initInpBox();
    initUsrGrid();
    initManageCom(); //如果传入管理机构不为空，则管理机构下拉框只能查此机构及其下级机构
  }
  catch(re)
  {
    alert("UsrCommonQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

// 输入框的初始化（单记录部分）
function initInpBox()
{

  try
  {
    document.all('UsrCode').value = '';
    document.all('UsrName').value = '';
    if(managecom != null && managecom !="")
    {
      document.all('ManageCom').value = managecom;
      showOneCodeName("Station", "ManageCom", "ManageComName");
    }
    if(usercode != null && usercode !="" && usercode != 'null' && usercode != '')
    {
      document.all('UsrCode').value = usercode;
    }

  }
  catch(ex)
  {
    alert("在UsrCommonQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

/************************************************************
 *
 *输入：          没有
 *输出：          没有
 *功能：          初始化UsrGrid
 ************************************************************
 */
function initUsrGrid()
  {
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";
        iArray[0][1]="30px";
        iArray[0][2]=30;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="用户代码";
        iArray[1][1]="120px";
        iArray[1][2]=150;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="用户姓名";
        iArray[2][1]="140px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="管理机构";
        iArray[3][1]="140px";
        iArray[3][2]=200;
        iArray[3][3] = 0;

        iArray[4]=new Array();
        iArray[4][0]="个单保全权限级别";
        iArray[4][1]="140px";
        iArray[4][2]=200;
        iArray[4][3]=2;
        iArray[4][4] = "EdorPopedom";
        iArray[4][18] = 236;
        
        iArray[5]=new Array();
        iArray[5][0]="团单保全权限级别";
        iArray[5][1]="140px";
        iArray[5][2]=200;
        iArray[5][3]=2;
        iArray[5][4] = "EdorPopedom";
        iArray[5][18] = 236;
        
        UsrGrid = new MulLineEnter("document", "UsrGrid");
        //这些属性必须在loadMulLine前
        UsrGrid.mulLineCount = 5;
        UsrGrid.displayTitle = 1;
        UsrGrid.canSel=1;
        //UsrGrid.canChk=0;
        UsrGrid.locked=1;
        UsrGrid.hiddenPlus=1;
        UsrGrid.hiddenSubtraction=1;
        UsrGrid.loadMulLine(iArray);
      }
      catch(ex)
      {
        alert("初始化UsrGrid时出错："+ ex);
      }
    }

</script>
