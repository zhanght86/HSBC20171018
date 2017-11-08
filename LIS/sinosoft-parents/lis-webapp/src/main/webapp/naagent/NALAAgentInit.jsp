<%
//程序名称：LAAgentInput.jsp
//程序功能：
//创建日期：2002-08-16 15:39:06
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //添加页面控件的初始化。
%>                            

<script language="JavaScript">
function initInpBox()
{ 
  try
  {      
    document.all('AgentCode').value = '';
    //document.all('Password').value = '';
    //document.all('EntryNo').value = '';
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('Birthday').value = '';
    document.all('NativePlace').value = '';
    document.all('Nationality').value = '';
    //document.all('Marriage').value = '';
    //document.all('CreditGrade').value = '';
    //document.all('HomeAddressCode').value = '';
    document.all('HomeAddress').value = '';
    //document.all('PostalAddress').value = '';
    document.all('ZipCode').value = '';
    document.all('Phone').value = '';
    document.all('BP').value = '';
    document.all('Mobile').value = '';
    document.all('EMail').value = '';
    //document.all('MarriageDate').value = '';
    document.all('IdType').value = '';
    document.all('IDNo').value = '';
    //document.all('Source').value = '';
    //document.all('BloodType').value = '';
    document.all('PolityVisage').value = '';
    document.all('Degree').value = '';
    document.all('GraduateSchool').value = '';
    document.all('Speciality').value = '';
    document.all('PostTitle').value = '';
    //document.all('ForeignLevel').value = '';
    //document.all('WorkAge').value = '';
    document.all('OldCom').value = '';
    document.all('OldOccupation').value = '';
    document.all('HeadShip').value = '';
    //document.all('RecommendAgent').value = '';
    //document.all('Business').value = '';
    //document.all('SaleQuaf').value = '';
    document.all('QuafNo').value = '';
    //document.all('QuafStartDate').value = '';
    document.all('QuafEndDate').value = '';
    document.all('DevNo1').value = '';
    //document.all('DevNo2').value = '';
    //document.all('RetainContNo').value = '';
    //document.all('AgentKind').value = '';
    //document.all('DevGrade').value = '';
    //document.all('InsideFlag').value = '';
    //document.all('FullTimeFlag').value = '';
    //document.all('NoWorkFlag').value = '';
    document.all('TrainPeriods').value = '';
    document.all('EmployDate').value = '';
    //document.all('InDueFormDate').value = '';
    //document.all('OutWorkDate').value = '';
    //document.all('Approver').value = '';
    //document.all('ApproveDate').value = '';
    document.all('AssuMoney').value = '';
    //document.all('AgentState').value = '01';  //增员状态
    //document.all('QualiPassFlag').value = '';
    //document.all('SmokeFlag').value = '';
    document.all('RgtAddress').value = '';
    document.all('BankCode').value = '';
    document.all('BankAccNo').value = '';
    document.all('Remark').value = '';
    document.all('Operator').value = '';
    //行政信息
    document.all('UpAgent').value = '';
    document.all('GroupManagerName').value = '';
    document.all('DepManagerName').value = '';
    document.all('IntroAgency').value = '';
    document.all('BranchCode').value = '';
    document.all('ManageCom').value = '';
    document.all('AgentSeries').value = '';
    document.all('AgentGrade').value = '';
    document.all('AgentGrade1').value = '';
    document.all('RearAgent').value = '';
    document.all('RearDepartAgent').value = '';
    document.all('RearSuperintAgent').value = '';
    document.all('RearAreaSuperintAgent').value = '';
    document.all('hideBranchCode').value = '';
    //document.all('AgentGroup').value = '';
    //document.all('hideManageCom').value = '';
    document.all('ManagerCode').value = '';
    document.all('upBranchAttr').value = '';
    document.all('hideIsManager').value = 'false';
    document.all('BranchType').value = getBranchType();  
    //by jiaqiangli 2006-07-24
    //由于在初始信息修改中NALAAgentEditInput1.jsp引用了此jsp,所以也加了2个控件
    document.all('DirManagerName').value = '';   
    document.all('MajordomoName').value = '';
  }
  catch(ex)
  {
    alert("在LAAgentInputInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

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
    alert("在LAAgentInputInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm()
{
  try
  {
    initInpBox();
    initSelBox();
    initWarrantorGrid();    
  }
  catch(re)
  {
    alert("LAAgentInputInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}  
// 担保人信息的初始化
function initWarrantorGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="姓名";          		        //列名
      iArray[1][1]="60px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=1;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的
      iArray[1][9]="姓名|NOTNULL";

      iArray[2]=new Array();
      iArray[2][0]="性别";         		        //列名
      iArray[2][1]="30px";            			//列宽
      iArray[2][2]=10;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="Sex";
      iArray[2][9]="性别|NOTNULL&code:Sex";

      iArray[3]=new Array();
      iArray[3][0]="身份证号码";      	   		//列名
      iArray[3][1]="130px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][9]="身份证号码|NOTNULL";

      iArray[4]=new Array();
      iArray[4][0]="单位";      	   		//列名
      iArray[4][1]="110px";            			//列宽
      iArray[4][2]=30;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="单位|NOTNULL";
    
      iArray[5]=new Array();
      iArray[5][0]="家庭地址";      	   		//列名
      iArray[5][1]="120px";            			//列宽
      iArray[5][2]=40;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][9]="家庭地址|NOTNULL";

      iArray[6]=new Array();
      iArray[6][0]="手机";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=1;  
      iArray[6][9]="手机|NOTNULL&NUM";
      
      iArray[7]=new Array();
      iArray[7][0]="邮政编码";      	   		//列名
      iArray[7][1]="60px";            			//列宽
      iArray[7][2]=20;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[7][9]="邮政编码|NOTNULL&NUM&len=6";
  
      iArray[8]=new Array();
      iArray[8][0]="电话";      	   		//列名
      iArray[8][1]="80px";            			//列宽
      iArray[8][2]=20;            			//列最大值
      iArray[8][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][9]="电话|NOTNULL&NUM";
  
      iArray[9]=new Array();
      iArray[9][0]="关系";      	   		//列名
      iArray[9][1]="50px";            			//列宽
      iArray[9][2]=20;            			//列最大值
      iArray[9][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[9][4]='relaseries';
      iArray[9][9]="关系|NOTNULL&code:relaseries";
      

      WarrantorGrid = new MulLineEnter( "fm" , "WarrantorGrid" ); 
      //这些属性必须在loadMulLine前
      WarrantorGrid.mulLineCount = 1;   
      WarrantorGrid.displayTitle = 1;
      //WarrantorGrid.locked=1;      
      WarrantorGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
