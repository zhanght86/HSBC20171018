<%
//程序名称：EdorAppManuUWInit.jsp
//程序功能：保全人工核保
//创建日期：2005-04-26 16:49:22
//创建人  ：zhangtao
//更新记录：  更新人liurongxiao    更新日期   2005-06-17  更新原因/内容 与新契约界面取得一致
%>

<script language="JavaScript">

//页面初始化
function initForm()
{
  try
  {
      initParam();
      initInpBox();
      initEdorMainGrid();
      initPolAddGrid();
      initRiskGrid();
      initQuery();
      displayDetail();
      ctrlButtonDisabled();
      //alert("EDORTYPE=="+fm.EdorType.value);
  }
  catch (ex)
  {
      alert("在 EdorAppManuUWInit --> InitForm 函数中发生异常:初始化界面错误！ ");
  }
}

//接收从工作流保全申请页面传递过来的参数
function initParam()
{
   document.all('EdorAcceptNo').value     = NullToEmpty(EdorAcceptNo);
   document.all('MissionID').value        = NullToEmpty(MissionID);
   document.all('SubMissionID').value     = NullToEmpty(SubMissionID);
   document.all('ActivityStatus').value   = NullToEmpty(ActivityStatus);
   document.all('EdorType').value   		= NullToEmpty(EdorType);
   document.all('ActivityID').value   		= NullToEmpty(ActivityID);
   document.all('EdorNo').value = ""; //alert(document.all('SubMissionID').value);
   //makeWorkFlow();alert(36);
}

//初始化页面元素
function initInpBox()
{

  try
  {
    document.all('ContNo').value = '';
    document.all('lpManageCom').value = '';
    document.all('SaleChnl').value = '';
    document.all('AgentCode').value = '';
    document.all('Remark').value = '';

    document.all('AppntName').value = '';
    document.all('AppntSex').value = '';
    document.all('AppntBirthday').value = '';
    document.all('OccupationCode').value = '';
    document.all('OccupationType').value = '';
    document.all('NativePlace').value = '';
   // document.all('VIPValue').value = '';
   // document.all('BlacklistFlag').value = '';

    document.all('EdorUWState').value = "";
    document.all('edoruwstateName').value = "";
    document.all('UWDelay').value = "";
    document.all('UWPopedomCode').value = "";
    document.all('UWIdea').value = "";
  }
  catch (ex)
  {
    alert("在 EdorAppManuUWInit.jsp --> InitInpBox 函数中发生异常:初始化界面错误！ ");
  }
}

// 保全批单列表的初始化
function initEdorMainGrid()
{
    var iArray = new Array();

    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="30px";                  //列宽
        iArray[0][2]=30;                      //列最大值
        iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="业务类别";
        iArray[1][1]="80px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="保单号";
        iArray[2][1]="150px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="批单号";
        iArray[3][1]="150px";
        iArray[3][2]=150;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="客户号";
        iArray[4][1]="100px";
        iArray[4][2]=200;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="险种编码";
        iArray[5][1]="0px";
        iArray[5][2]=100;
        iArray[5][3]=0;

        iArray[6]=new Array();
        iArray[6][0]="险种名称";
        iArray[6][1]="150px";
        iArray[6][2]=200;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="投保人客户号";
        iArray[7][1]="0px";
        iArray[7][2]=100;
        iArray[7][3]=0;

        iArray[8]=new Array();
        iArray[8][0]="投保人";
        iArray[8][1]="80px";
        iArray[8][2]=100;
        iArray[8][3]=0;

        iArray[9]=new Array();
        iArray[9][0]="被保人客户号";
        iArray[9][1]="0px";
        iArray[9][2]=100;
        iArray[9][3]=0;

        iArray[10]=new Array();
        iArray[10][0]="被保险人";
        iArray[10][1]="80px";
        iArray[10][2]=100;
        iArray[10][3]=0;

        iArray[11]=new Array();
        iArray[11][0]="保单生效日期";
        iArray[11][1]="100px";
        iArray[11][2]=200;
        iArray[11][3]=8;

        iArray[12]=new Array();
        iArray[12][0]="保单失效日期";
        iArray[12][1]="100px";
        iArray[12][2]=100;
        iArray[12][3]=8;
        
        iArray[13]=new Array();
        iArray[13][0]="保单状态";
        iArray[13][1]="80px";
        iArray[13][2]=80;
        iArray[13][3]=0;

        EdorMainGrid = new MulLineEnter("fm", "EdorMainGrid");
        //这些属性必须在loadMulLine前
        EdorMainGrid.mulLineCount = 3;
        EdorMainGrid.displayTitle = 1;
        EdorMainGrid.locked = 1;
        EdorMainGrid.canSel = 1;
        EdorMainGrid.hiddenPlus = 1;
        EdorMainGrid.hiddenSubtraction=1;
        EdorMainGrid.loadMulLine(iArray);
        EdorMainGrid. selBoxEventFuncName = "showDetailInfo1";
    }
    catch (ex)
    {
        alert(ex);
    }
}
function initPolAddGrid()
  {
      var iArray = new Array();

      try
      {
          iArray[0]=new Array();
          iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
          iArray[0][1]="30px";                  //列宽
          iArray[0][2]=30;                      //列最大值
          iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

          iArray[1]=new Array();
          iArray[1][0]="客户号";
          iArray[1][1]="100px";
          iArray[1][2]=200;
          iArray[1][3]=0;

          iArray[2]=new Array();
          iArray[2][0]="姓名";
          iArray[2][1]="100px";
          iArray[2][2]=200;
          iArray[2][3]=0;

          iArray[3]=new Array();
          iArray[3][0]="性别";
          iArray[3][1]="80px";
          iArray[3][2]=200;
          iArray[3][3]=0;

          iArray[4]=new Array();
          iArray[4][0]="年龄";
          iArray[4][1]="80px";
          iArray[4][2]=200;
          iArray[4][3]=0;

          iArray[5]=new Array();
          iArray[5][0]="与投保人关系";
          iArray[5][1]="80px";
          iArray[5][2]=200;
          iArray[5][3]=0;
          
          iArray[6]=new Array();
          iArray[6][0]="与主被保人关系";
          iArray[6][1]="80px";
          iArray[6][2]=200;
          iArray[6][3]=0;
          
          iArray[7]=new Array();
          iArray[7][0]="国籍";
          iArray[7][1]="80px";
          iArray[7][2]=200;
          iArray[7][3]=0; 
          
          PolAddGrid = new MulLineEnter("fm", "PolAddGrid");
          //这些属性必须在loadMulLine前
          PolAddGrid.mulLineCount = 3;
          PolAddGrid.displayTitle = 1;
          PolAddGrid.locked = 1;
          PolAddGrid.canSel = 1;
          PolAddGrid.hiddenPlus = 1;
          PolAddGrid.hiddenSubtraction = 1;
          PolAddGrid.loadMulLine(iArray);
          PolAddGrid.selBoxEventFuncName = "initLppolDetailf";
      }
      catch (ex)
      {
        alert(ex);
      }
}

// 险种信息列表的初始化
function initRiskGrid()
{                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[1]=new Array();
      iArray[1][0]="批单号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="批改类型";         		//列名
      iArray[2][1]="0px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[3]=new Array();
      iArray[3][0]="保单险种号码";         		//列名
      iArray[3][1]="150px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="主险保单号码";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[5]=new Array();
      iArray[5][0]="险种编码";         		//列名
      iArray[5][1]="0px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[6]=new Array();
      iArray[6][0]="险种名称";         		//列名
      iArray[6][1]="140px";            		//列宽
      iArray[6][2]=100;            			//列最大值
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

      iArray[7]=new Array();
      iArray[7][0]="保额";         		//列名
      iArray[7][1]="80px";            		//列宽
      iArray[7][2]=100;            			//列最大值
      iArray[7][3]=7;              			//是否允许输入,1表示允许，0表示不允许
			iArray[7][23]="0";
			
      iArray[8]=new Array();
      iArray[8][0]="标准保费";         		//列名
      iArray[8][1]="80px";            		//列宽
      iArray[8][2]=100;            			//列最大值
      iArray[8][3]=7;              			//是否允许输入,1表示允许，0表示不允许
			iArray[8][23]="0";
			
      iArray[9]=new Array();
      iArray[9][0]="健康加费";         		//列名
      iArray[9][1]="80px";            		//列宽
      iArray[9][2]=100;            			//列最大值
      iArray[9][3]=7;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][23]="0";
      
      iArray[10]=new Array();
      iArray[10][0]="职业加费";         		//列名
      iArray[10][1]="80px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=7;              			//是否允许输入,1表示允许，0表示不允许
      iArray[10][23]="0";             			//是否允许输入,1表示允许，0表示不允许      
      
      iArray[11]=new Array();
      iArray[11][0]="居住地加费";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=7;              			//是否允许输入,1表示允许，0表示不允许
      iArray[11][23]="0";            			//是否允许输入,1表示允许，0表示不允许
      
      iArray[12]=new Array();
      iArray[12][0]="爱好加费";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=100;            			//列最大值
      iArray[12][3]=7;              			//是否允许输入,1表示允许，0表示不允许
      iArray[12][23]="0";               			//是否允许输入,1表示允许，0表示不允许        
      
      iArray[13]=new Array();
	  iArray[13][0]="保险起期";         	//列名
	  iArray[13][1]="80px";            		//列宽
	  iArray[13][2]=80;            			//列最大值
	  iArray[13][3]=8;              			//是否允许输入,1表示允许，0表示不允许

	  iArray[14]=new Array();
	  iArray[14][0]="保险止期";         		//列名
	  iArray[14][1]="80px";            		//列宽
	  iArray[14][2]=100;            			//列最大值
	  iArray[14][3]=8;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[15]=new Array();
	  iArray[15][0]="交费间隔(月)";         		//列名
	  iArray[15][1]="80px";            		//列宽
	  iArray[15][2]=100;            			//列最大值
	  iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[16]=new Array();
	  iArray[16][0]="交费年期";         		//列名
	  iArray[16][1]="80px";            		//列宽
	  iArray[16][2]=100;            			//列最大值
	  iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[17]=new Array();
	  iArray[17][0]="保单状态";         		//列名
	  iArray[17][1]="80px";            		//列宽
	  iArray[17][2]=100;            			//列最大值
	  iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[18]=new Array();
	  iArray[18][0]="原核保结论";         		//列名
	  iArray[18][1]="80px";            		//列宽
	  iArray[18][2]=100;            			//列最大值
	  iArray[18][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
	  iArray[19]=new Array();
	  iArray[19][0]="现核保结论";         		//列名
	  iArray[19][1]="80px";            		//列宽
	  iArray[19][2]=100;            			//列最大值
	  iArray[19][3]=0;              			//是否允许输入,1表示允许，0表示不允许
	  
        iArray[20]=new Array();
        iArray[20][0]="币种";
        iArray[20][1]="60px";
        iArray[20][2]=100;
        iArray[20][3]=2;
        iArray[20][4]="currency";
           
      RiskGrid = new MulLineEnter( "fm" , "RiskGrid" ); 
      //这些属性必须在loadMulLine前
      RiskGrid.mulLineCount = 0;   
      RiskGrid.displayTitle = 1;
      RiskGrid.locked = 1;
      RiskGrid.canSel = 0;
      RiskGrid.hiddenPlus = 1;
      RiskGrid.hiddenSubtraction = 1;
      RiskGrid.loadMulLine(iArray);     
      
     // RiskGrid.selBoxEventFuncName = "getRiskInfo";
      
      //这些操作必须在loadMulLine后面
      //RiskGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
function initPolGrid()
{
}
</script>
