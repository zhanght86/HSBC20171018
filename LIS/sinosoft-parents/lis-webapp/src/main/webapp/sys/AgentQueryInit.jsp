<%
//程序名称：AgentQueryInit.js
//程序功能：
//创建日期：2003-4-8
//创建人  ：lh
//更新记录：  更新人    更新日期     更新原因/内容
%>
<%
     //添加页面控件的初始化。
%>                            
<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {    
    document.all('AgentCode').value = '';
    document.all('AgentGroup').value = '';
    document.all('ManageCom').value = '';
    //document.all('Password').value = '';
    document.all('EntryNo').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('Birthday').value = '';
    //document.all('NativePlace').value = '';
    //document.all('Nationality').value = '';
   // document.all('Marriage').value = '';
    document.all('CreditGrade').value = '';
//    document.all('HomeAddressCode').value = '';
//    document.all('HomeAddress').value = '';
   // document.all('PostalAddress').value = '';
    document.all('ZipCode').value = '';
    //document.all('Phone').value = '';
    document.all('BP').value = '';
    document.all('Mobile').value = '';
    document.all('EMail').value = '';
    //document.all('MarriageDate').value = '';
    document.all('IDNo').value = '';
    //document.all('Source').value = '';
    //document.all('BloodType').value = '';
//    document.all('PolityVisage').value = '';
//    document.all('Degree').value = '';
//   document.all('GraduateSchool').value = '';
//    document.all('Speciality').value = '';
//    document.all('PostTitle').value = '';
//    document.all('ForeignLevel').value = '';
//    document.all('WorkAge').value = '';
//    document.all('OldCom').value = '';
//    document.all('OldOccupation').value = '';
    document.all('HeadShip').value = '';
    document.all('RecommendAgent').value = '';
    document.all('Business').value = '';
    document.all('SaleQuaf').value = '';
    document.all('QuafNo').value = '';
//    document.all('QuafStartDate').value = '';
//    document.all('QuafEndDate').value = '';
//    document.all('DevNo1').value = '';
//    document.all('DevNo2').value = '';
//    document.all('RetainContNo').value = '';
//    document.all('AgentKind').value = '';
//    document.all('DevGrade').value = '';
//    document.all('InsideFlag').value = '';
//    document.all('FullTimeFlag').value = '';
//    document.all('NoWorkFlag').value = '';
//    document.all('TrainDate').value = '';
//    document.all('EmployDate').value = '';
//    document.all('InDueFormDate').value = '';
//    document.all('OutWorkDate').value = '';
//    document.all('Approver').value = '';
//    document.all('ApproveDate').value = '';
//    document.all('AssuMoney').value = '';
//    document.all('AgentState').value = '';
//    document.all('QualiPassFlag').value = '';
//    document.all('SmokeFlag').value = '';
//    document.all('RgtAddress').value = '';
//    document.all('BankCode').value = '';
//    document.all('BankAccNo').value = '';
//    document.all('Remark').value = '';
//    document.all('Operator').value = '';
//    document.all('BranchType').value = top.opener.document.all('BranchType').value;
  }
  catch(ex)
  {
    alert("在AgentQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

// 下拉框的初始化
function initSelBox()
{  
  try                 
  {
//    setOption("t_sex","0=男&1=女&2=不详");      
//    setOption("sex","0=男&1=女&2=不详");        
//    setOption("reduce_flag","0=正常状态&1=减额交清");
//    setOption("pad_flag","0=正常&1=垫交");   
  }
  catch(ex)
  {
    alert("在AgentQueryInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();    
    initAgentGrid();
  }
  catch(re)
  {
    alert("AgentQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}
/************************************************************
 *               
 *输入：          没有
 *输出：          没有
 *功能：          初始化AgentGrid
 ************************************************************
 */
function initAgentGrid()
  {                               
    var iArray = new Array();
      
      try
      {
        iArray[0]=new Array();
        iArray[0][0]="序号";         //列名
        iArray[0][1]="30px";         //列名
        iArray[0][2]=100;         //列名
        iArray[0][3]=0;         //列名

        iArray[1]=new Array();
        iArray[1][0]="代理人编码";         //列名
        iArray[1][1]="80px";         //宽度
        iArray[1][2]=100;         //最大长度
        iArray[1][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[2]=new Array();
        iArray[2][0]="销售机构";         //列名
        iArray[2][1]="100px";         //宽度
        iArray[2][2]=100;         //最大长度
        iArray[2][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[3]=new Array();
        iArray[3][0]="管理机构";         //列名
        iArray[3][1]="80px";         //宽度
        iArray[3][2]=100;         //最大长度
        iArray[3][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[4]=new Array();
        iArray[4][0]="姓名";         //列名
        iArray[4][1]="80px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=0;         //是否允许录入，0--不能，1--允许

        iArray[5]=new Array();
        iArray[5][0]="身份证号";         //列名
        iArray[5][1]="100px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[6]=new Array();
        iArray[6][0]="状态";         //列名
        iArray[6][1]="30px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[7]=new Array();
        iArray[7][0]="电话";         //列名
        iArray[7][1]="80px";         //宽度
        iArray[7][2]=100;         //最大长度
        iArray[7][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[8]=new Array();
        iArray[8][0]="手机";         //列名
        iArray[8][1]="80px";         //宽度
        iArray[8][2]=100;         //最大长度
        iArray[8][3]=0;         //是否允许录入，0--不能，1--允许

  
        AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 

        //这些属性必须在loadMulLine前
        AgentGrid.mulLineCount = 10;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
//        AgentGrid.canChk=0;
        AgentGrid.locked=1;
	      AgentGrid.hiddenPlus=1;
	      AgentGrid.hiddenSubtraction=1;
        AgentGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert("初始化AgentGrid时出错："+ ex);
      }
    }


</script>
