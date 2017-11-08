<%
//Name:LLInqCourseInit.jsp
//function：

//Date:2005-6-22
//更新记录：  更新人:yuejw    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
<script language="JavaScript">
function initParam()
{	
//	var sysdatearr=easyExecSql("select to_date(now(),'yyyy-mm-dd') from dual"); //取服务器的时间作为系统当前日期
	var sysdatearr=easyExecSql("select to_char(now(),'yyyy-mm-dd') from dual"); //取服务器的时间作为系统当前日期    //wyc
	mCurrentDate=sysdatearr[0][0];
	
   	document.all('ManageCom').value = nullToEmpty("<%= tG.ManageCom %>");	  
    document.all('ClmNo').value =nullToEmpty("<%=tClmNo%>");
    document.all('InqNo').value =nullToEmpty("<%=tInqNo%>");
    document.all('Activityid').value =nullToEmpty("<%=tActivityid%>");
    document.all('MissionID').value =nullToEmpty("<%=tMissionID%>");
    document.all('SubMissionID').value =nullToEmpty("<%=tSubMissionID%>");
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
//调查申请信息初始化	
function initLLInqApplyInfo()
{
  try
  {
    	fm.ClmNo.value=""; //赔案号
        fm.InqNo.value="";  //调查序号
        fm.BatNo.value=""; //批次号
        fm.CustomerNo.value=""; //出险人客户号
        fm.CustomerName.value=""; //出险人客户姓名
        fm.VIPFlag.value="";   //VIP客户
        fm.InqDept.value="";   //调查机构
        fm.InitPhase.value=""; //提起阶段
        fm.InqRCode.value="";  //调查原因
        fm.InqItem.value="";  //调查项目
  }
  catch(ex)
  {
    alter("在LLInqCourseInit.jsp-->initLLInqApplyInfo函数中发生异常:初始化界面错误!");
  }
}
//调查过程信息初始化
function initInqCourseInfo()
{
  try
  {
    	fm.InqMode.value=""; //调查方式
    	fm.InqModeName.value=""; //调查方式
        fm.InqSite.value="";  //调查地点
        fm.InqDate.value=""; //调查日期
        fm.InqPer1.value=""; //第一调查人
        fm.InqPer2.value=""; //第二调查人
        fm.InqByPer.value="";   //被调查人
        fm.InqCourse.value="";   //调查过程录入  	
        fm.InqCourseRemark.value="";   //调查过程备注
  }
  catch(ex)
  {
    alert("在LLInqCourseInit.jsp-->InitInqCourseInfo函数中发生异常:初始化界面错误!");
  }
}
//调查费用信息初始化
function initInqFeeInfo()
{
  try
  {
    fm.FeeType.value=""; //费用类型
    fm.FeeTypeName.value=""; //费用类型
    fm.FeeSum.value=""; //费用金额
    fm.FeeDate.value=""; //发生时间
    fm.Payee.value=""; //领款人
    fm.PayeeType.value=""; //  领款方式
    fm.PayeeTypeName.value=""; //  领款方式
    fm.Remark.value="";    //备注
  }
  catch(ex)
  {
    alert("在LLInqCourseInit.jsp-->initInqFeeInfo函数中发生异常:初始化界面错误!");
  }
}
//初始化页面
function initForm()
{
  try
  {
    initLLInqApplyInfo();
    initInqCourseInfo();
    initInqFeeInfo();
    initLLInqCertificateInfo();
	initParam();
	LLInqApplyQueryClick();
  }
  catch(re)
  {
    alter("在LLInqCourseInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}


//调查过程单证信息
function initLLInqCertificateGrid()
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
        iArray[1][0]="单证类型";
        iArray[1][1]="60px";
        iArray[1][2]=100;
        iArray[1][3]=0;
//     	iArray[1][4]="llmaffix";
////     	iArray[1][7]="llmaffixClick" //你写的JS函数名，不加扩号
//     	iArray[1][18]= "500"; 

		iArray[2]=new Array();
        iArray[2][0]="单证名称";
        iArray[2][1]="200px";
        iArray[2][2]=100;
        iArray[2][3]=0;
            
        iArray[3]=new Array()
        iArray[3][0]="原件标志";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=2;
		iArray[3][10]="OriFlag";
        iArray[3][11]="0|^0|原件^1|复印件";  
        iArray[3][14]="0";
        
        iArray[4]=new Array();
        iArray[4][0]="张数";
        iArray[4][1]="50px";
        iArray[4][2]=100;
        iArray[4][3]=1;
		iArray[4][14]=1;

		iArray[5]=new Array();
        iArray[5][0]="备注信息";
        iArray[5][1]="200px";
        iArray[5][2]=100;
        iArray[5][3]=1;
        
        LLInqCertificateGrid = new MulLineEnter("document","LLInqCertificateGrid");
        LLInqCertificateGrid.mulLineCount = 5;
        LLInqCertificateGrid.displayTitle = 1;
        LLInqCertificateGrid.locked = 0;
        LLInqCertificateGrid.canSel =0; // 1 显示 ；0 隐藏（缺省值）
        LLInqCertificateGrid.hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        LLInqCertificateGrid.hiddenSubtraction=0; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        LLInqCertificateGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }
}

function initLLInqCertificateInfo()
{
	initLLInqCertificateGrid();
	LLInqCertificateGrid.clearData();
	fm.AffixCode.value="";
	fm.AffixName.value="";
	fm.checkbox.checked=false;
	fm.OtherName.value="";
	fm.OtherName.disabled=true;
	DivInqCertificate.style.display="none";
}

 </script>
