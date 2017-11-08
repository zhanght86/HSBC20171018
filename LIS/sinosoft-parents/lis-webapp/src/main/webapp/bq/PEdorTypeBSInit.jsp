<%
/*******************************************************************************
 * <p>Title: Lis 6.0</p>
 * <p>Description: 中科软人寿保险核心业务管理系统</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.Ltd. All Rights Reserved</p>
 * <p>Company: 中科软科技股份有限公司</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : zengyg
 * @version  : 1.00
 * @date     : 2007-03-19
 * @direction: 补签名输入页面初始化JavaScript函数
 * modified by pst on 2007 06 19
 ******************************************************************************/
 %>
<%
   String mCurrentDate = PubFun.getCurrentDate();
%>

<script language="JavaScript">


function initForm()
{
	try{
  	//QueryEdorInfo();
    initEdorInfo();
	  initEdorGrid();
	  initQuery();
	  //initCheckInfo();
	}
  catch(ex)
  {
     alert("在PEdorTypePSInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

function initEdorInfo()
{
  try
  {
  	 //Edorquery();
     document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
     document.all('ContNo').value = top.opener.document.all('ContNo').value;
     
     document.all('EdorType').value = top.opener.document.all('EdorType').value;
     //document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
     document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
     document.all('PolNo').value = top.opener.document.all('PolNo').value;
     document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
     document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;

     showOneCodeName('EdorCode','EdorType','EdorTypeName');

     
     fm.chkS1.value="0";
     fm.chkS2.value="0";
     fm.chkS3.value="0";
     fm.chkS4.value="0";
     fm.chkS5.value="0";
     fm.chkS6.value="0";
     fm.chkS7.value="0";
     fm.chkS8.value="0";
     
     fm.chkO1.value="0";
     fm.chkO2.value="0";
     fm.chkO3.value="0";

  }
  catch(ex)
  {
     alert("在PEdorTypePSInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}


/**
 * 根据操作类型(录入或查询)决定操作按钮是否显示
 */
function EdorQuery()
{
	  //alert(22);
    var sButtonFlag;
    try
    {
        sButtonFlag = top.opener.document.getElementsByName("ButtonFlag")[0].value;
    }
    catch (ex) {}
    if (sButtonFlag != null && trim(sButtonFlag) == "1")
    {
        try
        {
            document.all("divEdorQuery").style.display = "none";
        }
        catch (ex) {}
    }
    else
    {
        try
        {
            document.all("divEdorQuery").style.display = "";
        }
        catch (ex) {}
    }
}

function initEdorGrid()
{
    var iArray = new Array();

      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";            		//列宽
        iArray[0][2]=10;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="保全受理号";
        iArray[1][1]="80px";
        iArray[1][2]=50;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="保全批单号";
        iArray[2][1]="80px";
        iArray[2][2]=160;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="批改项目";
        iArray[3][1]="30px";
        iArray[3][2]=80;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="批改项目名称";
        iArray[4][1]="80px";
        iArray[4][2]=60;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="保单号";
        iArray[5][1]="80px";
        iArray[5][2]=60;
        iArray[5][3]=0;
        
        iArray[6]=new Array();
        iArray[6][0]="批改柜面受理日期";
        iArray[6][1]="60px";
        iArray[6][2]=70;
        iArray[6][3]=8;

        iArray[7]=new Array();
        iArray[7][0]="批改生效日期";
        iArray[7][1]="60px";
        iArray[7][2]=70;
        iArray[7][3]=8;

        

      EdorGrid = new MulLineEnter("fm","EdorGrid" );
      //这些属性必须在loadMulLine前
      EdorGrid.mulLineCount = 1;
      EdorGrid.displayTitle = 1;
      EdorGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      EdorGrid.canSel = 1;
      EdorGrid.canChk=0;
      EdorGrid.hiddenSubtraction=1;
      EdorGrid.loadMulLine(iArray);
      EdorGrid.selBoxEventFuncName ="" ;
      //这些操作必须在loadMulLine后面

      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>