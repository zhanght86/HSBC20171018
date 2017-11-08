<%
//**************************************************************************************************
//Name: LLReportInit.jsp
//function：报案界面初始化
//author: zl
//Date: 2005-6-10 13:51
//**************************************************************************************************
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">

var mCurrentDate = "";
var mNowYear = "";


//接收报案页面传递过来的参数
function initParam()
{
	//var sysdatearr=easyExecSql("select to_date(sysdate) from dual"); //取服务器的时间作为系统当前日期
	mySql = new SqlClass();
		mySql.setResourceName("claim.LLClaimRegisterInputSql");
		mySql.setSqlId("LLClaimRegisterSql54");
		mySql.addSubPara("1");
	var sysdatearr=easyExecSql(mySql.getString());
	mCurrentDate=sysdatearr[0][0];
      
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('isNew').value = nullToEmpty("<%= tIsNew %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");



    
}

//把null的字符串转为空
function nullToEmpty(string)
{
	if ((string == "null") || (string == "undefined"))
	{
		string = "";
	}
	return string;
}

//
function initInpBox()
{
    try
    {
        fm.AccidentDate.disabled=false;
        fm.occurReason.disabled=false;
        fm.accidentDetail.disabled=false;
        fm.claimType.disabled=false;
        fm.Remark.disabled=false;

        fm.addbutton.disabled=true;
        fm.DoHangUp.disabled=true;
        fm.CreateNote.disabled=true;
        fm.BeginInq.disabled=true;
        fm.ViewInvest.disabled=true;
        //fm.Condole.disabled=true;
        //fm.SubmitReport.disabled=false;
        //fm.ViewReport.disabled=true;
        fm.AccidentDesc.disabled=true;
        fm.QueryCont2.disabled=true;
        fm.QueryCont3.disabled=true;
        fm.RptConfirm.disabled=true;
        
        
    }
    catch(ex)
    {
        alert("在LLReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}
function initSelBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("在LLReportInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}
function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initSelBox();
        initSubReportGrid();
        
        //*******************************************************************
        //条件判断(新增、工作队列)
        //新增：0
        //工作队列：1
        //*******************************************************************
        if(fm.isNew.value == '0')
        {
            //显示新增按钮栏
            showPage(this,operateButton2);

            document.all('MedicalAccidentDate').disabled=true;
            document.all('OtherAccidentDate').disabled=true;
        }
        else if(fm.isNew.value == '1')
        {
            //显示修改按钮栏
            showPage(this,operateButton3);
            initQuery();
            //设置可操作按钮
            fm.QueryPerson.disabled = true;
            fm.QueryReport.disabled = true;
            fm.RptConfirm.disabled = false;
        }
        else if(fm.isNew.value == '3')
        {
        	  initQuery();
              //设置可操作按钮
              fm.QueryPerson.disabled = true;
              fm.QueryReport.disabled = true;
              fm.RptConfirm.disabled = true;
              fm.MedCertForPrt.disabled = true;
              fm.BarCodePrint.disabled = true;
              fm.ColQueryImage.disabled = true;
              fm.DoHangUp.disabled = true;
              fm.CreateNote.disabled = true;
              fm.PrintCertify.disabled = true;
              fm.BeginInq.disabled = true;
              fm.SubmitReport.disabled = true;
              fm.ViewReport.disabled = true;

              fm.QueryCont2.disabled = false;
              fm.QueryCont3.disabled = false;
              fm.ViewInvest.disabled = false;
              fm.AccidentDesc.disabled = false;
        }

    }
    catch(re)
    {
        alert("在LLReportInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}
function initSubReportGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="序号";
        iArray[0][1]="30px";
        iArray[0][2]=10;
        iArray[0][3]=0;

        iArray[1]=new Array();
        iArray[1][0]="客户号码";
        iArray[1][1]="230px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户姓名";
        iArray[2][1]="150px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="性别代码";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="150px";
        iArray[5][2]=100;
        iArray[5][3]=0;
                

        iArray[6]=new Array()
        iArray[6][0]="社保标志代码";
        iArray[6][1]="50px";
        iArray[6][2]=100;
        iArray[6][3]=3;
        
        iArray[7]=new Array()
        iArray[7][0]="社保标志";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;  


        SubReportGrid = new MulLineEnter("fm","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
        SubReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        SubReportGrid.selBoxEventFuncName ="SubReportGridClick"; //响应的函数名，不加扩号
        SubReportGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项
        SubReportGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        SubReportGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}
</script>
