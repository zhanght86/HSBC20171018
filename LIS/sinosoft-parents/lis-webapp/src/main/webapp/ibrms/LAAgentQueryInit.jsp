<%
//程序名称：LAAgentQueryInit.jsp
//程序功能：
//创建日期：2003-01-13 15:31:09
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>                           

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 
  try
  {    
   document.all('AgentCode').value = '';
    
    document.all('Name').value = '';
    document.all('Sex').value = '';
    document.all('Birthday').value = '';
    document.all('IDNoType').value = '';
    document.all('IDNo').value = '';
    document.all('Nationality').value = '';
    document.all('NativePlace').value = '';
    document.all('PolityVisage').value = '';
    document.all('RgtAddress').value = '';
    document.all('Degree').value = '';    
    document.all('GraduateSchool').value = '';
    document.all('Speciality').value = '';
    document.all('PostTitle').value = '';
    document.all('HomeAddress').value = '';
    document.all('PostalAddress').value = '';
    document.all('ZipCode').value = '';
    document.all('Phone').value = '';
    //document.all('BP').value = '';
    document.all('Mobile').value = '';
    
    document.all('EMail').value = '';
    document.all('BankCode').value = '';
    document.all('BankAccNo').value = '';
    document.all('Workage').value = '';
    document.all('OldCom').value = '';
    document.all('OldOccupation').value = '';
    
    document.all('HeadShip').value = '';
    document.all('QuafNo').value = '';
    document.all('GrantDate').value = '';
    document.all('DevNo1').value = '';
    //document.all('VIPProperty').value = '';
    document.all('EmployDate').value = '';
    
    document.all('ReceiptNo').value = '';
    
   // document.all('BranchType').value = top.opener.document.all('BranchType').value;
     document.all('BranchType').value='1';
  }
  catch(ex)
  {
  	 
    alert("在LAAgentQueryInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}                                      

function initForm()
{
  try
  {
    initInpBox();   
    initAgentGrid();
  }
  catch(re)
  {
    alert("LAAgentQueryInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
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
        iArray[4][1]="100px";         //宽度
        iArray[4][2]=100;         //最大长度
        iArray[4][3]=0;         //是否允许录入，0--不能，1--允许?        
        iArray[5]=new Array();
        iArray[5][0]="职级";         //列名
        iArray[5][1]="100px";         //宽度
        iArray[5][2]=100;         //最大长度
        iArray[5][3]=0;         //是否允许录入，0--不能，1--允许?
        
        iArray[6]=new Array();
        iArray[6][0]="身份证号";         //列名
        iArray[6][1]="120px";         //宽度
        iArray[6][2]=100;         //最大长度
        iArray[6][3]=0;         //是否允许录入，0--不能，1--允许
        
        iArray[7]=new Array();
        iArray[7][0]="代理人状态";         //列名
        iArray[7][1]="50px";         //宽度
        iArray[7][2]=100;         //最大长度
        iArray[7][3]=0;         //是否允许录入，0--不能，1--允许

  
        AgentGrid = new MulLineEnter( "fm" , "AgentGrid" ); 

        //这些属性必须在loadMulLine前
        AgentGrid.mulLineCount = 0;   
        AgentGrid.displayTitle = 1;
        AgentGrid.canSel=1;
        AgentGrid.canChk=0;
        AgentGrid.locked=1;
        AgentGrid.hiddenPlus = 1;
      	AgentGrid.hiddenSubtraction = 1;

        AgentGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert("初始化AgentGrid时出错："+ ex);
      }
    }

</script>
