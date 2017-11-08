<%
//**************************************************************************************************/
//Name: LLGrpClaimRegisterInit.jsp
//function：团体立案界面初始化
//author: pd
//Date: 2005-10-20
//**************************************************************************************************/
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
%>
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
    document.all('clmState').value = nullToEmpty("<%= tClmState %>");
    document.all('isClaimState').value = nullToEmpty("<%= tIsClaimState %>"); //判断来自理赔状态查询的节点
    document.all('MissionID').value = nullToEmpty("<%= tMissionID %>");
    document.all('SubMissionID').value = nullToEmpty("<%= tSubMissionID %>");
    mManageCom = nullToEmpty("<%= tG.ManageCom %>");
    document.all('tOperator').value = nullToEmpty("<%= tG.Operator %>");
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

//初始化表单
function initForm()
{
    try
    {
        initParam();
        initInpBox();
        initSubReportGrid();
        initClaimGrid();        
        initDutyKindGrid();
        initPolDutyKindGrid();
        initPolDutyCodeGrid();
        initLPEdorItemGrid();
        QueryRgtState();
        //******************************************************************************************Beg
        //通过传入参数isNew和clmState判断显示[修改]还是[保存]按钮
        //--新增：isNew=0 ，直接显示[保存]按钮
        //--共享池或工作队列：isNew=1&&clmState=10 ，立案表信息不存在，查询报案表，显示[保存]按钮
        //                    isNew=1&&clmState=20||30 ，立案表信息存在，查询立案表，显示[修改]按钮
        //******************************************************************************************
        //alert(fm.isNew.value);
        if(fm.isNew.value == '0')
        {
            fm.isReportExist.value = "false";
            fm.isRegisterExist.value = "false";
            //显示[保存]按钮栏
            operateButton21.style.display="";
            operateButton23.style.display="";
            //设置可操作按钮
            disabledButton();
            fm.QueryPerson.disabled = false;
            //fm.QueryReport.disabled = false;
            fm.addbutton.disabled = false;
            fm.deletebutton.disabled=false;
            fm.goBack.disabled = false;
            //fm.QueryCont4.disabled = false;
          //登陆机构操作控制,登陆机构代码小于4位时不允许操作
//         if(document.allManageCom.value.length < 4)
//         {
//           fm.updatebutton.disabled=true;
//           fm.deletebutton.disabled=true;
//           fm.QueryReport2.disabled=true;
//           fm.CreateNote2.disabled=true;
//           fm.CreateNote.disabled=true;
//           fm.BeginInq.disabled=true;

//          fm.printPassRgt.disabled=true;
//          fm.printDelayRgt.disabled=true;
//          fm.conclusionSave.disabled=true;
//          fm.printNoRgt.disabled=true;

//          fm.addUpdate.disabled=true;
//          fm.saveUpdate.disabled=true;

//          fm.MedicalFeeInp.disabled=true;
//          fm.dutySet.disabled=true;
//          fm.conclusionSave1.disabled=true;
//          fm.RgtConfirm.disabled=true;
//         }
        }
        else
        {
            if(fm.clmState.value == '10')
            {
                fm.isReportExist.value = "true";
                fm.isRegisterExist.value = "false";    
                //显示[保存]按钮栏
                operateButton21.style.display="";
                operateButton22.style.display="";
                operateButton23.style.display="";
                //查询报案
                queryReport();
                //查询申请人
                //queryProposer();
                //设置可操作按钮
                disabledButton();
                fm.QueryPerson.disabled = false;
                //fm.QueryReport.disabled = "";
                fm.addbutton.disabled = false;
                //fm.updatebutton.disabled=false;
                //fm.deletebutton.disabled=false;
                fm.QueryCont2.disabled = false;
                fm.QueryCont3.disabled = false;
                fm.goBack.disabled = false;               
                //fm.QueryCont4.disabled = false;
               //登陆机构操作控制,登陆机构代码小于4位时不允许操作
//                if(document.allManageCom.value.length < 4)
//                {
//                  fm.updatebutton.disabled=true;
//                  fm.deletebutton.disabled=true;
//                  fm.QueryReport2.disabled=true;
//                  fm.CreateNote2.disabled=true;
//                  fm.CreateNote.disabled=true;
//                  fm.BeginInq.disabled=true;
                
//                  fm.printPassRgt.disabled=true;
//                  fm.printDelayRgt.disabled=true;
//                  fm.conclusionSave.disabled=true;
//                  fm.printNoRgt.disabled=true;
                
//                  fm.addUpdate.disabled=true;
//                  fm.saveUpdate.disabled=true;
                
//                  fm.MedicalFeeInp.disabled=true;
//                  fm.dutySet.disabled=true;
//                  fm.conclusionSave1.disabled=true;
//                  fm.RgtConfirm.disabled=true;                 
//               }
               //判断来自理赔状态查询的节点的立案信息查询操作
                  if(fm.isClaimState.value == '1')
                  {                 
                   fm.updatebutton.disabled=true;
                   fm.deletebutton.disabled=true;
//                   fm.QueryReport2.disabled=true;
                   fm.CreateNote.disabled=true;
                   fm.CreateNote2.disabled=true;
                   fm.BeginInq.disabled=true; 
                   fm.printPassRgt.disabled=true;
                   fm.printDelayRgt.disabled=true;
                   fm.conclusionSave.disabled=true;
                   fm.printNoRgt.disabled=true;
                   fm.addUpdate.disabled=true;
                   fm.saveUpdate.disabled=true;
                   fm.MedicalFeeInp.disabled=true;
                   fm.dutySet.disabled=true;
//                   fm.conclusionSave1.disabled=true;
                   fm.RgtConfirm.disabled=true;
 //                  fm.goBack.disabled=true;
                  }
            }
            else
            {               
                fm.isReportExist.value = "true";
                fm.isRegisterExist.value = "true";             
                //显示[修改]按钮栏
                operateButton21.style.display="";
                operateButton22.style.display="";
                operateButton23.style.display="";
                
                fm.addbutton.disabled = true;
                fm.updatebutton.disabled=false;
                fm.deletebutton.disabled=false;
                //查询立案
                queryRegister();
                //设置可操作按钮
                fm.QueryPerson.disabled = false;
                //fm.QueryReport.disabled = true;
                //fm.QueryCont4.disabled = false;

              //登陆机构操作控制,登陆机构代码小于4位时不允许操作
//              if(document.allManageCom.value.length < 4)
//              {
//               fm.updatebutton.disabled=true;
//               fm.deletebutton.disabled=true;
//               fm.QueryReport2.disabled=true;
//               fm.CreateNote.disabled=true;
//               fm.CreateNote2.disabled=true;
//               fm.BeginInq.disabled=true;
    
//               fm.printPassRgt.disabled=true;
//               fm.printDelayRgt.disabled=true;
//               fm.conclusionSave.disabled=true;
//               fm.printNoRgt.disabled=true;
    
//               fm.addUpdate.disabled=true;
//               fm.saveUpdate.disabled=true;
    
//               fm.MedicalFeeInp.disabled=true;
//               fm.dutySet.disabled=true;
//               fm.conclusionSave1.disabled=true;
//               fm.RgtConfirm.disabled=true;
//              }
              //判断来自理赔状态查询的节点的立案信息查询操作

              if(fm.isClaimState.value == '1')
              { 
                            
               fm.updatebutton.disabled=true;               
               fm.deletebutton.disabled=true;             
//               fm.QueryReport2.disabled=true;                
               fm.CreateNote.disabled=true;
               fm.CreateNote2.disabled=true;              
               fm.BeginInq.disabled=true; 
               fm.printPassRgt.disabled=true;
               fm.printDelayRgt.disabled=true;
               fm.conclusionSave.disabled=true;               
               fm.printNoRgt.disabled=true;
               fm.addUpdate.disabled=true;
               fm.saveUpdate.disabled=true;
               fm.MedicalFeeInp.disabled=true;
               fm.dutySet.disabled=true;
//               fm.conclusionSave1.disabled=true;
               fm.RgtConfirm.disabled=true;
//              fm.goBack.disabled=true;                
              }
            }
        }
     //zy 2009-07-28 15:07 如果为康福产品则显示账户调整按钮
	   getLLEdorTypeCA();
	   if(fm.AccRiskCode.value=='211901')
	   {
	     fm.LCInsureAcc.disabled = false;
	   }
    fm.addUpdate.disabled = true; //添加修改
    fm.saveUpdate.disabled = true; //保存修改
        //******************************************************************************************End
    }
    catch(ex)
    {
        alert("在LLRegisterInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
}

//页面输入域初始化
function initInpBox()
{
    try
    {
        fm.dutySet.disabled = true;
        fm.medicalFeeCal.disabled = true;
        fm.printNoRgt.disabled = true;
        //fm.MedCertForPrt.disabled = true;
        //zy 2009-07-28 15:07 如果为康福产品则显示账户调整按钮
        fm.LCInsureAcc.disabled = true ;
    }
    catch(ex)
    {
        alert("在LLReportInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

//出险人信息列表初始化
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
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="客户姓名"; //事故者姓名
        iArray[2][1]="80px";
        iArray[2][2]=80;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="性别编码";
        iArray[3][1]="50px";
        iArray[3][2]=100;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="性别";
        iArray[4][1]="50px";
        iArray[4][2]=50;
        iArray[4][3]=0;
        
        iArray[5]=new Array();
        iArray[5][0]="出生日期";
        iArray[5][1]="100px";
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
        SubReportGrid.mulLineCount = 0;
        SubReportGrid.displayTitle = 1;
        SubReportGrid.locked = 0;
//        SubReportGrid.canChk =1;
        SubReportGrid.canSel =1; // 1 显示 ；0 隐藏（缺省值）
        SubReportGrid.selBoxEventFuncName ="allSubReportGridClick"; //响应的函数名，不加扩号
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


/**=========================================================================
    修改状态：开始
    修改原因：以下为理赔计算的结果区，分别显示为赔案的理赔类型，保单的理赔类型，保单理赔类型的保项
    修 改 人：续涛
    修改日期：2005.06.25
   =========================================================================
**/

//赔案总金额
function initClaimGrid()
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
	      iArray[1][0]="币种";      	   		//列名
	      iArray[1][1]="80px";            			//列宽
	      iArray[1][2]=20;            			//列最大值
	      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[2]=new Array();
        iArray[2][0]="赔付金额";
        iArray[2][1]="80px";
        iArray[2][2]=30;
        iArray[2][3]=7;
        iArray[2][22]="col1";
				iArray[2][23]="0";

        iArray[3]=new Array();
        iArray[3][0]="预付金额";
        iArray[3][1]="80px";
        iArray[3][2]=30;
        iArray[3][3]=7;
        iArray[3][22]="col1";
				iArray[3][23]="0";

        iArray[4]=new Array();
        iArray[4][0]="结算金额";
        iArray[4][1]="80px";
        iArray[4][2]=30;
        iArray[4][3]=7;
        iArray[4][22]="col1";
				iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="最终赔付金额";
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][22]="col1";
				iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="拒赔金额";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=7;
        iArray[6][22]="col1";
				iArray[6][23]="0";
        

        ClaimGrid = new MulLineEnter("fm","ClaimGrid");
        ClaimGrid.mulLineCount = 0;       // 显示行数
        ClaimGrid.displayTitle = 1;
        ClaimGrid.locked = 0;
    //  ClaimGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
        ClaimGrid.canSel =0;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
        ClaimGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        ClaimGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        ClaimGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}



//赔案的理赔类型
function initDutyKindGrid()
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
        iArray[1][0]="理赔类型代码";
        iArray[1][1]="80px";
        iArray[1][2]=80;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="理赔类型名称";
        iArray[2][1]="100px";
        iArray[2][2]=100;
        iArray[2][3]=0;
        
        iArray[3]=new Array();
	      iArray[3][0]="币种";      	   		//列名
	      iArray[3][1]="80px";            			//列宽
	      iArray[3][2]=20;            			//列最大值
	      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="账单金额";
        iArray[4][1]="80px";
        iArray[4][2]=80;
        iArray[4][3]=7;
        iArray[4][22]="col3";
				iArray[4][23]="0";

        iArray[5]=new Array();
        iArray[5][0]="自费/自付金额";      //也就是理算金额
        iArray[5][1]="80px";
        iArray[5][2]=80;
        iArray[5][3]=7;
        iArray[5][22]="col3";
				iArray[5][23]="0";

        iArray[6]=new Array();
        iArray[6][0]="保单合计理算金额";
        iArray[6][1]="80px";
        iArray[6][2]=80;
        iArray[6][3]=7;
        iArray[6][22]="col3";
				iArray[6][23]="0";

        iArray[7]=new Array();
        iArray[7][0]="社保给付";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=7;
        iArray[7][22]="col3";
				iArray[7][23]="0";

        iArray[8]=new Array();
        iArray[8][0]="第三方给付";
        iArray[8][1]="80px";
        iArray[8][2]=80;
        iArray[8][3]=7;
        iArray[8][22]="col3";
				iArray[8][23]="0";

        iArray[9]=new Array();
        iArray[9][0]="核赔赔付金额";
        iArray[9][1]="80px";
        iArray[9][2]=80;
        iArray[9][3]=7;
        iArray[9][22]="col3";
				iArray[9][23]="0";


        DutyKindGrid = new MulLineEnter("fm","DutyKindGrid");
        DutyKindGrid.mulLineCount = 0;       // 显示行数
        DutyKindGrid.displayTitle = 1;
        DutyKindGrid.locked = 0;
    //  DutyKindGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
        DutyKindGrid.canSel =0;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
        DutyKindGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        DutyKindGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
        DutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


//保单、理赔类型计算初始化
function initPolDutyKindGrid()
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
      iArray[1][0]="个人保单号";
      iArray[1][1]="105px";
      iArray[1][2]=105;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="保单号";
      iArray[2][1]="105px";
      iArray[2][2]=105;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="理赔类型";
      iArray[3][1]="60px";
      iArray[3][2]=60;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="生效日期";
      iArray[4][1]="70px";
      iArray[4][2]=60;
      iArray[4][3]=0;

      iArray[5]=new Array();
      iArray[5][0]="交至日期";
      iArray[5][1]="70px";
      iArray[5][2]=60;
      iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="险种代码";
        iArray[6][1]="60px";
        iArray[6][2]=60;
        iArray[6][3]=0;

        iArray[7]=new Array();
        iArray[7][0]="险种名称";
        iArray[7][1]="140px";
        iArray[7][2]=140;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
	      iArray[8][0]="币种";      	   		//列名
	      iArray[8][1]="80px";            			//列宽
	      iArray[8][2]=20;            			//列最大值
	      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[9]=new Array();
        iArray[9][0]="理算金额";
        iArray[9][1]="60px";
        iArray[9][2]=60;
        iArray[9][3]=7;
        iArray[9][22]="col8";
				iArray[9][23]="0";
        
        
      PolDutyKindGrid = new MulLineEnter("fm","PolDutyKindGrid");
      PolDutyKindGrid.mulLineCount = 0;       // 显示行数
      PolDutyKindGrid.displayTitle = 1;
      PolDutyKindGrid.locked = 0;
  //  PolDutyKindGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyKindGrid.canSel =0;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyKindGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyKindGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyKindGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert(ex);
    }
}


//保项计算初始化
/*
function initPolDutyCodeGrid()
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
      iArray[1][0]="保单号";
      iArray[1][1]="110px";
      iArray[1][2]=110;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="理赔类型";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=3;

      iArray[3]=new Array();
      iArray[3][0]="险种编码";
      iArray[3][1]="60px";
      iArray[3][2]=60;
      iArray[3][3]=3;

      iArray[4]=new Array();
      iArray[4][0]="保项编码";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=3;

      iArray[5]=new Array();
      iArray[5][0]="保项名称";
      iArray[5][1]="110px";
      iArray[5][2]=110;
      iArray[5][3]=0;


      iArray[6]=new Array();
      iArray[6][0]="责任起期";
      iArray[6][1]="80px";
      iArray[6][2]=80;
      iArray[6][3]=0;
        
        iArray[7]=new Array();
        iArray[7][0]="责任止期";
        iArray[7][1]="80px";
        iArray[7][2]=80;
        iArray[7][3]=0;
     
        iArray[8]=new Array();
        iArray[8][0]="宽限天数";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=0;    
        
        iArray[9]=new Array();
	      iArray[9][0]="币种";      	   		//列名
	      iArray[9][1]="80px";            			//列宽
	      iArray[9][2]=20;            			//列最大值
	      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许    

      iArray[10]=new Array();
      iArray[10][0]="保额";
      iArray[10][1]="60px";
      iArray[10][2]=60;
      iArray[10][3]=0;
      iArray[10][22]="col9";
			iArray[10][23]="0";

      iArray[11]=new Array();
      iArray[11][0]="年度红利";
      iArray[11][1]="60px";
      iArray[11][2]=60;
      iArray[11][3]=0;

      iArray[12]=new Array();
      iArray[12][0]="终了红利";
      iArray[12][1]="60px";
      iArray[12][2]=60;
      iArray[12][3]=0;

      iArray[13]=new Array();
      iArray[13][0]="理算金额";
      iArray[13][1]="60px";
      iArray[13][2]=60;
      iArray[13][3]=0;
      iArray[13][22]="col9";
			iArray[13][23]="0";

        iArray[14]=new Array();
        iArray[14][0]="给付代码";
        iArray[14][1]="60px";
        iArray[14][2]=60;
        iArray[14][3]=2;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        iArray[14][4]="llpayregconclusion"; //是否引用代码: null或者" "为不引用
        iArray[14][5]="13|14";              //引用代码信息分别放在第13列和第14列，'|'为分割符
        iArray[14][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列

        iArray[15]=new Array();
        iArray[15][0]="给付名称";
        iArray[15][1]="60px";
        iArray[15][2]=60;
        iArray[15][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[16]=new Array();
        iArray[16][0]="数据来源";
        iArray[16][1]="60px";
        iArray[16][2]=60;
        iArray[16][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        

        iArray[17]=new Array();
        iArray[17][0]="dutycode";
        iArray[17][1]="60px";
        iArray[17][2]=60;
        iArray[17][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏


      PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
      PolDutyCodeGrid.mulLineCount = 0;        // 显示行数
      PolDutyCodeGrid.displayTitle = 1;
      PolDutyCodeGrid.locked = 0;
  //  PolDutyCodeGrid.canChk =1;               // 多选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyCodeGrid.canSel =1;                   // 单选按钮：1 显示 ；0 隐藏（缺省值）
      PolDutyCodeGrid.hiddenPlus=1;        //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyCodeGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }  
}
*/
function initPolDutyCodeGrid()
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
        iArray[1][0]="保单号";
        iArray[1][1]="130px";
        iArray[1][2]=130;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="理赔类型";
        iArray[2][1]="60px";
        iArray[2][2]=60;
        iArray[2][3]=3;

        iArray[3]=new Array();
        iArray[3][0]="险种编码";
        iArray[3][1]="60px";
        iArray[3][2]=600;
        iArray[3][3]=3;

        iArray[4]=new Array();
        iArray[4][0]="保项编码";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=3;

        iArray[5]=new Array();
        iArray[5][0]="保项名称";
        iArray[5][1]="200px";
        iArray[5][2]=200;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="责任起期";
        iArray[6][1]="100px";
        iArray[6][2]=100;
        iArray[6][3]=0;
          
        iArray[7]=new Array();
        iArray[7][0]="责任止期";
        iArray[7][1]="100px";
        iArray[7][2]=100;
        iArray[7][3]=0;
     
        iArray[8]=new Array();
        iArray[8][0]="宽限天数";
        iArray[8][1]="60px";
        iArray[8][2]=60;
        iArray[8][3]=0;  
        
        iArray[9]=new Array();
	      iArray[9][0]="币种";      	   		//列名
	      iArray[9][1]="80px";            			//列宽
	      iArray[9][2]=20;            			//列最大值
	      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许      

        iArray[10]=new Array();
        iArray[10][0]="保额";
        iArray[10][1]="60px";
        iArray[10][2]=60;
        iArray[10][3]=7;
        iArray[10][22]="col9";
				iArray[10][23]="0";
      
        iArray[11]=new Array();
        iArray[11][0]="年度红利";
        iArray[11][1]="60px";
        iArray[11][2]=60;
        iArray[11][3]=3;
      
        iArray[12]=new Array();
        iArray[12][0]="终了红利";
        iArray[12][1]="60px";
        iArray[12][2]=60;
        iArray[12][3]=3;
      
        iArray[13]=new Array();
        iArray[13][0]="理算金额";
        iArray[13][1]="60px";
        iArray[13][2]=60;
        iArray[13][3]=7;
        iArray[13][22]="col9";
				iArray[13][23]="0";

        iArray[14]=new Array();
        iArray[14][0]="给付代码";
        iArray[14][1]="60px";
        iArray[14][2]=60;
        iArray[14][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        iArray[14][4]="llpayconclusion";    //是否引用代码: null或者" "为不引用
        iArray[14][5]="14|15";              //引用代码信息分别放在第13列和第14列，'|'为分割符
        iArray[14][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列


        iArray[15]=new Array();
        iArray[15][0]="给付名称";
        iArray[15][1]="60px";
        iArray[15][2]=60;
        iArray[15][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[16]=new Array();
        iArray[16][0]="拒付原因代码";
        iArray[16][1]="100px";
        iArray[16][2]=100;
        iArray[16][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        iArray[16][4]="llprotestreason";    //是否引用代码: null或者" "为不引用
        iArray[16][5]="16|17";              //引用代码信息分别放在第13列和第14列，'|'为分割符
        iArray[16][6]="0|1";                //引用代码数组的第0项（代码）放在第1列,第1项（名称）放在第2列

        iArray[17]=new Array();
        iArray[17][0]="拒付原因名称";
        iArray[17][1]="100px";
        iArray[17][2]=100;
        iArray[17][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        
        iArray[18]=new Array();
        iArray[18][0]="拒付依据";
        iArray[18][1]="60px";
        iArray[18][2]=60;
        iArray[18][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏

        iArray[19]=new Array();
        iArray[19][0]="特殊备注";
        iArray[19][1]="60px";
        iArray[19][2]=60;
        iArray[19][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
              

        iArray[20]=new Array();
        iArray[20][0]="预付金额";
        iArray[20][1]="60px";
        iArray[20][2]=60;
        iArray[20][3]=7;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        iArray[20][22]="col9";
				iArray[20][23]="0";
        
        iArray[21]=new Array();
        iArray[21][0]="";
        iArray[21][1]="60px";
        iArray[21][2]=60;
        iArray[21][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
           
        iArray[22]=new Array();
        iArray[22][0]="调整金额";
        iArray[22][1]="60px";
        iArray[22][2]=60;
        iArray[22][3]=7;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        iArray[22][22]="col9";
				iArray[22][23]="0";

        iArray[23]=new Array();
        iArray[23][0]="调整原因代码";
        iArray[23][1]="100px";
        iArray[23][2]=100;
        iArray[23][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
                                
        iArray[24]=new Array();
        iArray[24][0]="调整原因名称";
        iArray[24][1]="100px";
        iArray[24][2]=100;
        iArray[24][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
              
        iArray[25]=new Array();
        iArray[25][0]="调整备注";
        iArray[25][1]="60px";
        iArray[25][2]=60;
        iArray[25][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        
        iArray[26]=new Array();
        iArray[26][0]="预付标志代码";
        iArray[26][1]="60px";
        iArray[26][2]=60;
        iArray[26][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        
        iArray[27]=new Array();
        iArray[27][0]="预付标志";
        iArray[27][1]="60px";
        iArray[27][2]=60;
        iArray[27][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
        
        iArray[28]=new Array();
        iArray[28][0]="数据来源";
        iArray[28][1]="60px";
        iArray[28][2]=60;
        iArray[28][3]=0;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏                  
                    
          iArray[29]=new Array();
          iArray[29][0]="dutycode";
          iArray[29][1]="60px";
          iArray[29][2]=60;
          iArray[29][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
                    
          iArray[30]=new Array();
          iArray[30][0]="客户号";
          iArray[30][1]="60px";
          iArray[30][2]=60;
          iArray[30][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
          
          
          //金述海20070418修改,原因:在MULLINE中加入保单状态字
          iArray[31]=new Array();
          iArray[31][0]="保单状态";
          iArray[31][1]="60px";
          iArray[31][2]=60;
          iArray[31][3]=3;                    //1表示允许，0表示不允许,2表示代码选择,3隐藏
      PolDutyCodeGrid = new MulLineEnter("fm","PolDutyCodeGrid");
      PolDutyCodeGrid.mulLineCount = 0;        // 显示行数
      PolDutyCodeGrid.displayTitle = 1;
      PolDutyCodeGrid.locked = 0;
//      PolDutyCodeGrid.canChk =1;               // 多选按钮：1 显示 ；0 隐藏（缺省值）
     if(fm.isClaimState.value == '1')
     {
       PolDutyCodeGrid.canSel =0;                   // 单选按钮：1 显示 ；0 隐藏（缺省值）
     }else
     {
       PolDutyCodeGrid.canSel =1;   
     }
        //PolDutyCodeGrid.selBoxEventFuncName ="PolDutyCodeGridClick"; //响应的函数名，不加扩号
//        PolDutyCodeGrid.selBoxEventFuncParm ="1"; //传入的参数,可以省略该项  
      PolDutyCodeGrid.hiddenPlus=1;        //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyCodeGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      PolDutyCodeGrid.loadMulLine(iArray);
  }
  catch(ex)
  {
    alert(ex);
  }  
}


function initLPEdorItemGrid()
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
      iArray[1][0]="个人保单号";
      iArray[1][1]="80px";
      iArray[1][2]=110;
      iArray[1][3]=0;

      iArray[2]=new Array();
      iArray[2][0]="险种编码";
      iArray[2][1]="60px";
      iArray[2][2]=60;
      iArray[2][3]=0;

      iArray[3]=new Array();
      iArray[3][0]="保全类型";
      iArray[3][1]="60px";
      iArray[3][2]=150;
      iArray[3][3]=0;

      iArray[4]=new Array();
      iArray[4][0]="保全生效日期";
      iArray[4][1]="100px";
      iArray[4][2]=100;
      iArray[4][3]=0;
      
      iArray[5]=new Array();
	    iArray[5][0]="币种";      	   		//列名
	    iArray[5][1]="80px";            			//列宽
	    iArray[5][2]=20;            			//列最大值
	    iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="退费金额";
      iArray[6][1]="110px";
      iArray[6][2]=110;
      iArray[6][3]=7;
      iArray[6][22]="col5";
			iArray[6][23]="0";


//      iArray[6]=new Array();
//      iArray[6][0]="责任起期";
//      iArray[6][1]="80px";
//      iArray[6][2]=80;
//      iArray[6][3]=0;
        
//      iArray[7]=new Array();
//      iArray[7][0]="责任止期";
//      iArray[7][1]="80px";
//      iArray[7][2]=80;
//      iArray[7][3]=0;
   
    
      
      LPEdorItemGrid = new MulLineEnter("fm","LPEdorItemGrid");
      LPEdorItemGrid.mulLineCount = 0;       // 显示行数
      LPEdorItemGrid.displayTitle = 1;
      LPEdorItemGrid.locked = 0;
  //  LPEdorItemGrid.canChk =1;              // 多选按钮：1 显示 ；0 隐藏（缺省值）
      LPEdorItemGrid.canSel =0;                  // 单选按钮：1 显示 ；0 隐藏（缺省值）
      LPEdorItemGrid.hiddenPlus=1;           //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      LPEdorItemGrid.hiddenSubtraction=1; //是否隐藏"-"号标志：1为隐藏；0为不隐藏(缺省值)
      LPEdorItemGrid.loadMulLine(iArray);
 
  }
  catch(ex)
  {
    alert(ex);
  }  
}
/**=========================================================================
    修改状态：结束
    修改原因：以下为理赔计算的结果区，分别显示为赔案的理赔，保单的理赔类型，保单理赔类型的保项
    修 改 人：续涛
    修改日期：2005.06.25
   =========================================================================
**/

</script>
