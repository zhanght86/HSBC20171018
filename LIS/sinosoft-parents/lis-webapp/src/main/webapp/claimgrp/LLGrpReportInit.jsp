<%
//**************************************************************************************************
//Name: LLGrpReportInit.jsp
//function：团体报案界面初始化
//author: pd
//Date: 2005-10-19
//**************************************************************************************************
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>

<script language="JavaScript">

var mCurrentDate = "";
var mNowYear = "";
var mNowMonth = "";
var mNowDay = "";
var mManageCom = "";

//接收报案页面传递过来的参数
function initParam()
{
    mCurrentDate = nullToEmpty("<%= CurrentDate %>");  
    mNowYear = mCurrentDate.substring(0,4);
    mNowMonth = mCurrentDate.substring(5,7);
    mNowDay = mCurrentDate.substring(8,10);
   
    document.all('ClmNo').value = nullToEmpty("<%= tClmNo %>");
    document.all('isNew').value = nullToEmpty("<%= tIsNew %>");
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    document.all('isClaimState').value = nullToEmpty("<%= tIsClaimState %>"); //判断来自理赔状态查询的节点
    document.all('rgtstate').value = nullToEmpty("<%=trgtstate%>");    
    //alert("rgtstate:"+document.all('rgtstate').value);
    
    if (document.all('rgtstate').value=="" || document.all('rgtstate').value==null)
    {
       spanText7.disabled = false; 
    }
    else if (document.all('rgtstate').value!="" || document.all('rgtstate').value!=null)
    {      
      switch (document.all('rgtstate').value)
      {       
        case "11" :   
             fm.rgtstate[0].checked=true;
             break;
        case "03":
             fm.rgtstate[1].checked=true;
             divreport1.style.display="none";  
           	 divreport2.style.display="none"; 
           	 divreport3.style.display="none";
           	 divreport4.style.display="";
             initDiskErrQueryGrid();    
             divDiskErr.style.display="";  

             break;
        case "02":
             fm.rgtstate[2].checked=true;
             divreport1.style.display="none";  
           	 divreport2.style.display="none"; 
           	 divreport3.style.display="none";
           	 divreport4.style.display="";
           	 divreport5.style.display="";
             break;
      }
      spanText7.disabled = true;
    }

    mManageCom = nullToEmpty("<%= tG.ManageCom %>");
    document.all('AllManageCom').value = mManageCom;             //取到登陆机构的全部代码 用于查询保单处理机构
    document.all('ManageCom').value = mManageCom.substring(0,2); //取到登陆机构的前两个代码 用于查询医院机构
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

function initInpBox()
{
    try
    {
        fm.AccidentDate.disabled=false;
        fm.occurReason.disabled=false;
        fm.accidentDetail.disabled=false;
        fm.claimType.disabled=false;
        fm.Remark.disabled=false;

        fm.DoHangUp.disabled=true;
        fm.CreateNote.disabled=true;
        fm.BeginInq.disabled=true;
        fm.ViewInvest.disabled=true;
        fm.Condole.disabled=true;
        fm.SubmitReport.disabled=true;
        fm.ViewReport.disabled=true;
        fm.AccidentDesc.disabled=true;
        //fm.QueryCont2.disabled=true;
        //fm.QueryCont3.disabled=true;
        fm.RptConfirm.disabled=true;        
        fm.MedCertForPrt.disabled = true;
    }
    catch(ex)
    {
        alert("在LLGrpReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initSelBox()
{
    try
    {
    }
    catch(ex)
    {
        alert("在LLGrpReportInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
        initParam();
        initInpBox();
        initSelBox();
        initSubReportGrid();
        //initDiskErrQueryGrid();

        //*******************************************************************
        //条件判断(新增、工作队列)
        //新增：0
        //工作队列：1
        //*******************************************************************
        if(fm.isNew.value == '0')
        {
            //显示新增按钮栏
            showPage(this,operateButton2);
            showPage(this,operateButton1);
            showPage(this,operateButton3);
        }
        else if(fm.isNew.value == '1')
        {
            //显示修改按钮栏
            if(fm.rgtstate[1].checked == true)
            {
               	operateButton2.style.display="none";
            }
            else
            {
                showPage(this,operateButton2);
            }

            showPage(this,operateButton1);
            showPage(this,operateButton3); //alert(150);
            initQuery();
            
            //设置可操作按钮
            //fm.QueryPerson.disabled = true;
            if(fm.rgtstate[0].checked == true || fm.rgtstate[1].checked == true)
    		{
            	fm.QueryReport.disabled = false;
            }else{
            	fm.QueryReport.disabled = true;
            }
            fm.RptConfirm.disabled = false;

          if(fm.isClaimState.value == '1')
           {
           fm.addbutton.disabled=true;
           fm.deletebutton.disabled=true;
           fm.updatebutton.disabled=true;
           fm.CreateNote.disabled=true;
           fm.CreateNote2.disabled=true;
           fm.BeginInq.disabled=true;
           fm.RptConfirm.disabled=true;
           fm.QueryReport2.disabled=true;           
       //    fm.goBack.disabled=true;
           }
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
        iArray[1][0]="客户编码"; //原事故者客户号
        iArray[1][1]="120px";
        iArray[1][2]=100;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户姓名"; //事故者姓名
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;

		iArray[3]=new Array();
        iArray[3][0]="性别代码";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="120px";
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
        
        iArray[8]=new Array()
        iArray[8][0]="证件类型";
        iArray[8][1]="60px";
        iArray[8][2]=80;
        iArray[8][3]=0;  
        
        iArray[9]=new Array()
        iArray[9][0]="证件号码";
        iArray[9][1]="200px";
        iArray[9][2]=80;
        iArray[9][3]=0;  
        
        SubReportGrid = new MulLineEnter("document","SubReportGrid");
        SubReportGrid.mulLineCount = 5;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
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

function initDiskErrQueryGrid()
{
  var iArray = new Array();
  try
  {
    iArray[0]=new Array();
    iArray[0][0]="序号";  //列名（此列为顺序号，列名无意义，而且不显示）
    iArray[0][1]="30px";  //列宽
    iArray[0][2]=10;    //列最大值
    iArray[0][3]=0;      //是否允许输入,1表示允许，0表示不允许

    iArray[1]=new Array();
    iArray[1][0]="赔案号";
    iArray[1][1]="200px";
    iArray[1][2]=200;
    iArray[1][3]=0;

    iArray[2]=new Array();
    iArray[2][0]="客户号";
    iArray[2][1]="80px";
    iArray[2][2]=100;
    iArray[2][3]=0;

    iArray[3]=new Array();
    iArray[3][0]="出险人";
    iArray[3][1]="60px";
    iArray[3][2]=100;
    iArray[3][3]=0;

    iArray[4]=new Array();
    iArray[4][0]="错误信息";
    iArray[4][1]="200px";
    iArray[4][2]=100;
    iArray[4][3]=0;

    iArray[5]=new Array();
    iArray[5][0]="导入文件名";
    iArray[5][1]="100px";
    iArray[5][2]=100;
    iArray[5][3]=0;

    iArray[6]=new Array();
    iArray[6][0]="导入序号";
    iArray[6][1]="30px";
    iArray[6][2]=30;
    iArray[6][3]=0;
    
    iArray[7]=new Array();
    iArray[7][0]="导入操作员";
    iArray[7][1]="80px";
    iArray[7][2]=100;
    iArray[7][3]=0;
    
    iArray[8]=new Array();
    iArray[8][0]="导入日期";
    iArray[8][1]="80px";
    iArray[8][2]=100;
    iArray[8][3]=0;        

    iArray[9]=new Array();
    iArray[9][0]="导入时间";
    iArray[9][1]="80px";
    iArray[9][2]=100;
    iArray[9][3]=0;   

    DiskErrQueryGrid = new MulLineEnter( "fmload" , "DiskErrQueryGrid" );
    //这些属性必须在loadMulLine前
    DiskErrQueryGrid.mulLineCount = 0;
    DiskErrQueryGrid.displayTitle = 1;
    DiskErrQueryGrid.locked = 0;
    DiskErrQueryGrid.hiddenPlus=1;
    DiskErrQueryGrid.canChk =0; //多选按钮，1显示，0隐藏
    DiskErrQueryGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
    DiskErrQueryGrid.hiddenSubtraction=1;
    DiskErrQueryGrid.loadMulLine(iArray);
    //这些操作必须在loadMulLine后面
  }
  catch(ex)
  {
    alert(ex);
  }
}
</script>
