<%
//程序名称：ContInit.jsp
//程序功能：
//创建日期：2005-05-12 08:49:52
//创建人  ：
//更新记录：  更新人    更新日期     更新原因/内容
%>


<%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>  
<script language="JavaScript">
var loadFlag=<%=tLoadFlag%>;

//初始化输入框
function initInpBox() { 

}

// 下拉框的初始化
function initSelBox(){

}

//表单初始化
function initForm(){
  try{
    
  
    //Q:scantype是判断是否有扫描件，用于定制随动
    if(scantype=="scan")
    {
      setFocus();
    }  
    
    
    //初始化业务员告知
    initAgentImpartGrid();
   
    
    //初始化投保人告知信息
    initImpartGrid();
    
    //初始化工作流任务节点
    initMissionID();
    
    
    //初始化多业务员列表
    initMultiAgentGrid();
   
    
    
    //判断是否是有扫描件 
    /**********************
     *ScanFlag=0--有扫描　
     *ScanFlag=1--无扫描　
     **********************
     */
     
    if(this.ScanFlag == "0")
    {
      //根据需求PrtNo中存投保单号，故将投保单号字段赋值

      fm.all('PrtNo').value = prtNo;
      fm.all('PrtNo2').value = prtNo;
      
      fm.all('ProposalContNo').value = prtNo;
      fm.all('ContNo').value = prtNo;
      fm.all('ManageCom').value = ManageCom;
      fm.all('ActivityID').value = ActivityID;
      fm.all('NoType').value = NoType;
      
      fm.PrtNo.readOnly=true; 
      showCodeName();
     
      //showOneCodeName('comcode','ManageCom','ManageComName');  //代码汉化
   
      //showOneCodeName('comcode','AgentManageCom','AgentManageComName');
     
      detailInit(prtNo); 
      //getImpartInitInfo(); 
    }
   
    if(this.ScanFlag == "1")
    {
      fm.all('PrtNo').value = prtNo;
      fm.all('PrtNo2').value = prtNo;
      fm.all('ProposalContNo').value = prtNo;
      fm.all('ContNo').value = prtNo;
      fm.all('ManageCom').value = ManageCom;
      fm.all('ActivityID').value = ActivityID;
      fm.all('NoType').value = NoType;
      fm.PrtNo.readOnly=true;
      showCodeName();
      //getImpartInitInfo();
      //detailInit(prtNo); 
      //showOneCodeName('comcode','ManageCom','ManageComName');  //代码汉化
      //alert(2)
      //showOneCodeName('comcode','AgentManageCom','AgentManageComName');
      //alert(3) 
     
    }

//有些信息没有查出来,故用此函数来补充.
     getLostInfo();
    
    //按照不同的LoadFlag进行不同的处理
    //得到界面的调用位置,默认为1,表示个人保单直接录入.
    /**********************************************
     *LoadFlag=1 -- 个人投保单直接录入
     *LoadFlag=2 -- 集体下个人投保单录入
     *LoadFlag=3 -- 个人投保单明细查询
     *LoadFlag=4 -- 集体下个人投保单明细查询
     *LoadFlag=5 -- 复核
     *LoadFlag=6 -- 查询
     *LoadFlag=7 -- 保全新保加人
     *LoadFlag=8 -- 保全新增附加险
     *LoadFlag=9 -- 无名单补名单
     *LoadFlag=10-- 浮动费率
     *LoadFlag=13-- 集体下投保单复核修改
     *LoadFlag=16-- 集体下投保单查询
     *LoadFlag=25-- 人工核保修改投保单
     *LoadFlag=99-- 随动定制
     *
     ************************************************/
     //Q: 7,8,9,10没有分支处理 25不知道是什么
     //7,8,9
  
    //新单录入
    if(this.loadFlag=="1"){
    	//Q:新单录入为何要用PolNo查询？ hl 20050505
      //detailInit(mSwitch.getVar( "PolNo" ));
      fm.AppntIDType.value="0";
      fm.AppntNativePlace.value="CHN";
      fm.NativePlace.value="CHN";
			divButton.style.display="";
			divAddDelButton.style.display = "";
      divInputContButton.style.display = "";  
      detailInit(prtNo);
      getImpartInitInfo();
      return;
    }
    
    //问题件修改
    if(this.loadFlag=="3"){

　    //详细信息初始化
　    detailInit(prtNo); 
　    AppntChkNew();
			divButton.style.display="";
			divInputContButton.style.display = "none";
			divApproveContButton.style.display = "none";
			divProblemModifyContButton.style.display = "";
			operateButton.style.display="";
			divAddDelButton.style.display="";
      return;  
    }
    
    //新单复合  
    if(this.LoadFlag=="5"){    
     
   
      divButton.style.display="none";
		  divInputContButton.style.display = "none";
		  divApproveModifyContButton.style.display = "none";
      divApproveContButton.style.display = "";
　    //详细信息初始化
	
      detailInit(prtNo);    
      //判断是否有重复客户
　    AppntChkNew();
      return;  
      
    }
    
    //投保信息查询核保
    if (this.loadFlag == "6"){
      detailInit(prtNo); 
      divButton.style.display="none";
      //fm.all("Donextbutton1").style.display="none";
      //fm.all("Donextbutton2").style.display="none";
      //fm.all("Donextbutton3").style.display="";
      //fm.all("Donextbutton5").style.display="none";
      //fm.all("butBack").style.display="none";
　　　//divAddDelButton.style.display="none";
      //divInputContButton.style.display="";
      fm.all("riskbutton3").style.display="";  //进入险种界面
      //查询投保人详细信息                 

  	  return;
    }

    //保全新保加人
    if(this.loadFlag == "7"){


      //判断是否有重复客户
　    AppntChkNew();

      return;  
    }    
    
    //保全新增附加险
    if(this.loadFlag == "8"){

      //判断是否有重复客户
　    AppntChkNew();

      return;  
    }    
    
    //无名单补名单
    if(this.loadFlag == "9"){

      //判断是否有重复客户
　    AppntChkNew();

      return;  
    }    

    //无名单补名单
    if(this.loadFlag == "10"){

      //判断是否有重复客户
　    AppntChkNew();

      return;  
    }    

    
    //人工核保修改投保单
    if(this.loadFlag=="25"){      
　    //详细信息初始化
      detailInit(prtNo);    
		  divAddDelButton.style.display = "";
      divchangplan.style.display = "";
      divButton.style.display="";
      return;  
    }
    
    //随动定制
    if(this.loadFlag == "99"){
      divInputContButton.style.display="none";
      autoMoveButton.style.display="";    

      //判断是否有重复客户
　    AppntChkNew();

      return;  
    }    

    //Q:由已有程序发现以下代码没有作用故注释掉
    /**********
    var tPolNo = top.opener.parent.VD.gVSwitch.getVar( "PolNo" );
　  //详细信息初始化
　  detailInit(tPolNo); 
    AppntChkNew();
    ***********/
    //alert("ContNo=="+fm.all("ContNo").value);
    
    
  }
  catch(ex){}
}

// 告知信息列表的初始化
function initImpartGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="GrpImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][10]="AppntImpart";
      //iArray[1][11]="0|^101|财务及其他告知^102|健康告知";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";         		//列名
//      iArray[5][1]="90px";            		//列宽
//      iArray[5][2]=90;            			//列最大值
//      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";         		//列名
//      iArray[6][1]="90px";            		//列宽
//      iArray[6][2]=90;            			//列最大值
//      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 0;   
      ImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
function initMultiAgentGrid(){
    var iArray = new Array();
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="业务员代码";         		//列名
      iArray[1][1]="100px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][7]="queryAgentGrid";  　//双击调用查询业务员的函数          

      iArray[2]=new Array();
      iArray[2][0]="业务员姓名";         		//列名
      iArray[2][1]="150px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="所属机构";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="station";              			

      iArray[4]=new Array();
      iArray[4][0]="营业部、组";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许
 
      iArray[5]=new Array();
      iArray[5][0]="佣金比例";         		//列名
      iArray[5][1]="100px";            		//列宽
      iArray[5][2]=150;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[6]=new Array();
      iArray[6][0]="AgentGroup";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=150;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[7]=new Array();
      iArray[7][0]="BranchAttr";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=150;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许


      MultiAgentGrid = new MulLineEnter( "fm" , "MultiAgentGrid" ); 
      //这些属性必须在loadMulLine前
      MultiAgentGrid.mulLineCount = 1;   
      MultiAgentGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      MultiAgentGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
	
}
// 告知信息列表的初始化
function initAgentImpartGrid() {  
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[1][4]="ImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][10]="AgentImpart";
      iArray[1][11]="0|^05|业务员告知";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";         		//列名
//      iArray[5][1]="90px";            		//列宽
//      iArray[5][2]=90;            			//列最大值
//      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";         		//列名
//      iArray[6][1]="90px";            		//列宽
//      iArray[6][2]=90;            			//列最大值
//      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      AgentImpartGrid = new MulLineEnter( "fm" , "AgentImpartGrid" ); 
      //这些属性必须在loadMulLine前
      AgentImpartGrid.mulLineCount = 0;
      //AgentImpartGrid.hiddenPlus = 1;
      //AgentImpartGrid.hiddenSubtraction = 1;
      AgentImpartGrid.canChk = 0;   
      AgentImpartGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      AgentImpartGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
      
      
    }
    catch(ex) {
      alert(ex);
    }
}



/*****************************************************
 *   将BankContInsuredInit.jsp中的内容加入到本页中
 *   
 *****************************************************
 */


 
function initInpBox2()
{ 
  try
  { 
  	//SelPolNo当前选中险种的保单号
  	fm.SelPolNo.value='';
  	try{mSwitch.deleteVar("PolNo");}catch(ex){};
  	fm.InsuredNo.value='';
  }
  catch(ex)
  {
    alert("在ContInsuredInit2.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initSelBox2()
{  
  try                 
  {
/*********  hanlin 20050416  不知道是干什么用的。
   if(checktype=="1")
   {
     param="121";
     fm.pagename.value="121";     
   }
   if(checktype=="2")
   {
     param="22";
     fm.pagename.value="22";     
   }   
*********/
  }
  catch(ex)
  {
    alert("在ContInsuredInit2.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
  }
}                                        

function initForm2()
{
  //alert("initForm2 ContNo=="+fm.all("ContNo").value);
  try
  { 
    initInpBox2();
    initSelBox2();    
    initInsuredGrid(); 
    initImpartGrid2();
   
    //initImpartDetailGrid2();
     
    initPolGrid();
   
    // initGrpInfo();
  
    
    //Q:因为不知道Auditing是什么，故注释掉。
    /***********************
    if(Auditing=="1")
    {
    	var tmSwitch = top.opener.parent.VD.gVSwitch;
			mSwitch.deleteVar( "ContNo" );
  		mSwitch.addVar( "ContNo", "", tmSwitch.getVar("ContNo") );
  		mSwitch.updateVar("ContNo", "", tmSwitch.getVar("ContNo"));
  		mSwitch.deleteVar( "ProposalContNo" );
  		mSwitch.addVar( "ProposalContNo", "", tmSwitch.getVar("ContNo") );
  		mSwitch.updateVar("ProposalContNo", "", tmSwitch.getVar("ContNo"));
  		mSwitch.deleteVar( "PrtNo" );
  		mSwitch.addVar( "PrtNo", "", tmSwitch.getVar("PrtNo") );
  		mSwitch.updateVar("PrtNo", "", tmSwitch.getVar("PrtNo"));
  		mSwitch.deleteVar( "GrpContNo" );
  		mSwitch.addVar( "GrpContNo", "", tmSwitch.getVar("GrpContNo") );
  		mSwitch.updateVar("GrpContNo", "", tmSwitch.getVar("GrpContNo"));
    }
  　************************/
  
    //getContInfo(); 
    
    //查询被保人信息。
    getInsuredInfo();  
    
    DivGrpNoname.style.display="none";
    
    //判断是否是家庭单，如果不是则不显示被保人列表？

   /********  
    if(fm.all('FamilyType').value==''||fm.all('FamilyType').value==null||fm.all('FamilyType').value=='0'||fm.all('FamilyType').value=='false'){  
      fm.all('FamilyType').value='0';
      divTempFeeInput.style.display="none";
      getProposalInsuredInfo();          //获得个人单信息，填写被保人表
    }
  *********/
  }
  catch(re)
  {
    alert("ContInsuredInit2.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
  
  //hanlin 20050416
  ContType = fm.ContType.value;
  if(fm.ContType.value=="2")
  {
  	initContPlanCode();
	  initExecuteCom(); 
  
  }
  
  //按照不同的LoadFlag进行不同的处理
  //得到界面的调用位置,默认为1,表示个人保单直接录入.
  /**********************************************
   *
   *LoadFlag=1  -- 个人投保单直接录入
   *LoadFlag=2  -- 集体下个人投保单录入
   *LoadFlag=3  -- 个人投保单明细查询
   *LoadFlag=4  -- 集体下个人投保单明细查询
   *LoadFlag=5  -- 复核
   *LoadFlag=6  -- 查询
   *LoadFlag=7  -- 保全新保加人
   *LoadFlag=8  -- 保全新增附加险
   *LoadFlag=9  -- 无名单补名单
   *LoadFlag=10 -- 浮动费率
   *LoadFlag=13 -- 集体下投保单复核修改
   *LoadFlag=16 -- 集体下投保单查询
   *LoadFlag=23 -- 团单核保承保计划变更
   *LoadFlag=25 -- 个单核保承保计划变更
   *LoadFlag=99 -- 随动定制
   *
   ************************************************/
  showCodeName();  //代码汉化
  //个险新单录入
  if(LoadFlag=="1"){ 
    divLCInsuredPerson.style.display="none";
    divFamilyType.style.display="";
    //divPrtNo.style.display="";
    divSamePerson.style.display="";
    DivGrpNoname.style.display="none";  
    fm.PolTypeFlag.value='0';
    return;     
  }
  
  //集体下个人投保单录入
  if(LoadFlag=="2"){ 
    fm.InsuredSequencename.value="被保险人资料";   
    divFamilyType.style.display="none";
    divSamePerson.style.display="none";
    //divPrtNo.style.display="none";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divGrpInputContButton.style.display="";
    divLCInsuredPerson.style.display="none";   
    DivRelation.style.display="none";
    DivAddress.style.display="none";
    DivClaim.style.display="none";       
    return;     
  }
  
  //个人投保单明细查询
  if(LoadFlag=="3"){
    divTempFeeInput.style.display="";
    //getInsuredInfo(); 前面已经运行过
    return;     
  }
  
  //集体下个人投保单明细查询
  if(LoadFlag=="4"){
    divFamilyType.style.display="none";
    //divPrtNo.style.display="none";
    divInputContButton.style.display="none";
    divApproveContButton.style.display="";
    divTempFeeInput.style.display="none";
    divAddDelButton.style.display="none";
    divSamePerson.style.display="none";
    divLCInsuredPerson.style.display="none";
    divCheckInsuredButton.style.display="none";        
    //getInsuredInfo();  前面已经运行过
    return;     
  }
  
  //复核
  if(LoadFlag=="5"){
   // alert("复核")
    divTempFeeInput.style.display="";
    //getInsuredInfo();  前面已经运行过
    divFamilyType.style.display="";
    divAddDelButton.style.display="none";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";   
    
    divInputContButton.style.display="none";
    divApproveContButton.style.display="";
    divLCInsuredPerson.style.display="none";
    //alert("复核1")
    return;     
  }

  //查询
  if(LoadFlag=="6"){
  	//被保人列表
    divTempFeeInput.style.display="";
    //divPrtNo.style.display="";    
    //录单按钮
    divInputContButton.style.display="none";
    
    //divQueryButton.style.display="";
    //divFamilyType.style.display="";
    //添加、删除、修改被保人
    divAddDelButton.style.display="none";
    //被保人信息“查询”按钮
    fm.InsuredButBack.style.display="none";
    //“如投保人为被保险人本人，可免填本栏”checkbox
    divSamePerson.style.display="none";
    divInputQuery.style.display="";
    return;     
  }

  //保全新保加人
  if(LoadFlag=="7"){
    divFamilyType.style.display="none";
    divSamePerson.style.display="none";
    //divPrtNo.style.display="none";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divGrpInputContButton.style.display="";
    divLCInsuredPerson.style.display="none";   
    fm.InsuredSequencename.value="被保险人资料"; 
    return;     
  }

  //保全新增附加险
  if(LoadFlag=="8"){
    return;	
  }

  //无名单补名单
  if(LoadFlag=="9"){
    return;	
  }

  //浮动费率
  if(LoadFlag=="10"){
    return;	
  }

  //集体下投保单复核修改
  if(LoadFlag=="13"){ 
    divFamilyType.style.display="none";
    divSamePerson.style.display="none";
    //divPrtNo.style.display="none";
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divLCInsuredPerson.style.display="none"; 
    divApproveModifyContButton.style.display="";
    //getInsuredInfo();  前面已经运行过
    return;     
  }

  //集体下投保单查询
  if(LoadFlag=="16"){
    fm.InsuredSequencename.value="被保险人资料";     
    divTempFeeInput.style.display="none";
    divInputContButton.style.display="none";
    divQueryButton.style.display="";
    divFamilyType.style.display="none";
    divAddDelButton.style.display="none";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none"; 
    divLCInsuredPerson.style.display="none";
    DivRelation.style.display="none";
    DivAddress.style.display="none";
    DivClaim.style.display="none";    
    return;     
  }

  //个单核保承保计划变更
  if(LoadFlag=="25"){
    divTempFeeInput.style.display="";
    //getInsuredInfo(); //前面已经运行过
    divFamilyType.style.display="";
    divAddDelButton.style.display="";
    fm.butBack.style.display="none";
    divSamePerson.style.display="none";   
    divInputContButton.style.display="none";
    divApproveContButton.style.display="none";
    divLCInsuredPerson.style.display="none";
    divchangplan.style.display="";
    return;     
  }
  
  //随动定制
  if(this.LoadFlag == "99"){
  	if(checktype=="1"){
      divAddDelButton.style.display="none";
      divInputContButton.style.display="none";
      autoMoveButton.style.display="";    
      return;     
    }
    if(checktype=="2"){
　　  divAddDelButton.style.display="none";
      divInputContButton.style.display="none";
      autoMoveButton.style.display="";    
      return;     
    }
    return;
  }

}


// 被保人信息列表的初始化
function initInsuredGrid()
  {                               
    var iArray = new Array();      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="40px"; 	           		//列宽
      iArray[0][2]=1;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="客户号码";          		//列名
      iArray[1][1]="80px";      	      		//列宽
      iArray[1][2]=20;            			//列最大值
      iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

      iArray[2]=new Array();
      iArray[2][0]="姓名";         			//列名
      iArray[2][1]="60px";            			//列宽
      iArray[2][2]=20;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


      iArray[3]=new Array();
      iArray[3][0]="性别";      	   		//列名
      iArray[3][1]="40px";            			//列宽
      iArray[3][2]=20;            			//列最大值
      iArray[3][3]=2;              	//是否允许输入,1表示允许，0表示不允许
      iArray[3][4]="Sex"; 


      iArray[4]=new Array();
      iArray[4][0]="出生日期";      	   		//列名
      iArray[4][1]="80px";            			//列宽
      iArray[4][2]=20;            			//列最大值
      iArray[4][3]=0; 
      
      iArray[5]=new Array();
      iArray[5][0]="与第一被保险人关系";      	   		//列名
      iArray[5][1]="100px";            			//列宽
      iArray[5][2]=20;            			//列最大值
      iArray[5][3]=2; 
      iArray[5][4]="Relation";              	        //是否引用代码:null||""为不引用
      iArray[5][9]="与主被保险人关系|code:Relation&NOTNULL";
      //iArray[5][18]=300;
      
      iArray[6]=new Array();
      iArray[6][0]="客户内部号";      	   		//列名
      iArray[6][1]="80px";            			//列宽
      iArray[6][2]=20;            			//列最大值
      iArray[6][3]=2; 
      iArray[6][10]="MutiSequenceNo";
      iArray[6][11]="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人";      

      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" ); 
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 0;   
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canSel =1;
      InsuredGrid. selBoxEventFuncName ="getInsuredDetail" ;     //点击RadioBox时响应的JS函数
      InsuredGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
      InsuredGrid. hiddenSubtraction=1;
      InsuredGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 被保人告知信息列表的初始化
function initImpartGrid2() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="GrpImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][10]="InsuredImpart";
      //iArray[1][11]="0|^01|财务及其他告知^02|健康告知";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3";
      iArray[2][6]="0|1";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="填写内容";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="填写内容|len<=200";

//      iArray[5]=new Array();
//      iArray[5][0]="告知客户类型";         		//列名
//      iArray[5][1]="90px";            		//列宽
//      iArray[5][2]=90;            			//列最大值
//      iArray[5][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[5][4]="CustomerType";
//      iArray[5][9]="告知客户类型|len<=18";
//      
//      iArray[6]=new Array();
//      iArray[6][0]="告知客户号码";         		//列名
//      iArray[6][1]="90px";            		//列宽
//      iArray[6][2]=90;            			//列最大值
//      iArray[6][3]=2;              			//是否允许输入,1表示允许，0表示不允许
//      iArray[6][4]="CustomerNo";
//      iArray[6][9]="告知客户号码";
//      iArray[6][15]="Cont";

      ImpartInsuredGrid = new MulLineEnter( "fm" , "ImpartInsuredGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartInsuredGrid.mulLineCount = 0;   
      ImpartInsuredGrid.displayTitle = 1;
      //ImpartGrid.tableWidth   ="500px";
      ImpartInsuredGrid.loadMulLine(iArray);  
      
      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
      alert(ex);
    }
}
// 告知明细信息列表的初始化
function initImpartDetailGrid2() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="告知版别";         		//列名
      iArray[1][1]="60px";            		//列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="GrpImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][10]="InsuredImpartDetail";
      //iArray[1][11]="0|^01|财务及其他告知^02|健康告知";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2";
      iArray[2][6]="0";
      //iArray[2][7]="getImpartCode";
      iArray[2][9]="告知编码|len<=4";
      iArray[2][15]="ImpartVer";
      iArray[2][17]="1";
      iArray[2][18]=700;

      iArray[3]=new Array();
      iArray[3][0]="内容";         		//列名
      iArray[3][1]="450px";            		//列宽
      iArray[3][2]=2000;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许


/*******************************
      iArray[3]=new Array();
      iArray[3][0]="告知内容";         		//列名
      iArray[3][1]="250px";            		//列宽
      iArray[3][2]=200;            			//列最大值
      iArray[3][3]=1;              			//是否允许输入,1表示允许，0表示不允许

      iArray[4]=new Array();
      iArray[4][0]="开始时间";         		//列名
      iArray[4][1]="150px";            		//列宽
      iArray[4][2]=150;            			//列最大值
      iArray[4][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[4][9]="开始时间|date";

      iArray[5]=new Array();
      iArray[5][0]="结束时间";         		//列名
      iArray[5][1]="90px";            		//列宽
      iArray[5][2]=90;            			//列最大值
      iArray[5][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      iArray[5][9]="结束时间|date";
      
      iArray[6]=new Array();
      iArray[6][0]="证明人";         		//列名
      iArray[6][1]="90px";            		//列宽
      iArray[6][2]=90;            			//列最大值
      iArray[6][3]=1;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[7]=new Array();
      iArray[7][0]="目前情况";         		//列名
      iArray[7][1]="90px";            		//列宽
      iArray[7][2]=90;            			//列最大值
      iArray[7][3]=1;              			//是否允许输入,1表示允许，0表示不允许


      iArray[8]=new Array();
      iArray[8][0]="能否证明";         		//列名
      iArray[8][1]="90px";            		//列宽
      iArray[8][2]=90;            			//列最大值
      iArray[8][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[8][4]="yesno";
*******************************/    
 
      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" ); 
      //这些属性必须在loadMulLine前
      ImpartDetailGrid.mulLineCount = 0;   
      ImpartDetailGrid.displayTitle = 1;
      ImpartDetailGrid.loadMulLine(iArray);  
      
    }
    catch(ex) {
      alert(ex);
    }
}
//被保人险种信息列表初始化
function initPolGrid(){
    var iArray = new Array();
      
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		        //列宽
      iArray[0][2]=10;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="保险单险种号码";         		//列名
      iArray[1][1]="60px";            		        //列宽
      iArray[1][2]=60;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[2]=new Array();
      iArray[2][0]=" 险种编码";         		//列名
      iArray[2][1]="60px";            		        //列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许 
      iArray[2][4]="RiskCode";              			//是否允许输入,1表示允许，0表示不允许           
      
      iArray[3]=new Array();
      iArray[3][0]=" 保费(元)";         		//列名
      iArray[3][1]="60px";            		        //列宽
      iArray[3][2]=60;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[4]=new Array();
      iArray[4][0]="保额(元)";         		//列名
      iArray[4][1]="0px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=3;              			//是否允许输入,1表示允许，0表示不允许
          
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      PolGrid.mulLineCount = 0;   
      PolGrid.displayTitle = 1;
      PolGrid.canSel =1;
      PolGrid. selBoxEventFuncName ="getPolDetail";
      PolGrid. hiddenPlus=1;
      PolGrid. hiddenSubtraction=1;
      PolGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}


function getContInfo()

{
    try { fm.all( 'ContNo' ).value = mSwitch.getVar( "ContNo" ); if(fm.all( 'ContNo' ).value=="false"){fm.all( 'ContNo' ).value="";} } catch(ex) { };
    try { fm.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
    try { fm.all( 'ProposalContNo' ).value = mSwitch.getVar( "ProposalContNo" ); } catch(ex) { };   
    try { fm.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
    try { fm.all( 'FamilyType' ).value = mSwitch.getVar( "FamilyType" ); } catch(ex) {};
    try { fm.all( 'PolTypeFlag' ).value = mSwitch.getVar( "PolType" );if(fm.all( 'PolTypeFlag' ).value=="false"){fm.all( 'PolTypeFlag' ).value="0";} } catch(ex) {};
    try { fm.all( 'InsuredPeoples' ).value = mSwitch.getVar( "InsuredPeoples" );if(fm.all( 'InsuredPeoples' ).value=="false"){fm.all( 'InsuredPeoples' ).value="";} } catch(ex) {};
}

function initContPlanCode() 
{
	 //alert(mSwitch.getVar( "ProposalGrpContNo" ));
	 fm.all("ContPlanCode").CodeData=getContPlanCode(mSwitch.getVar( "ProposalGrpContNo" ));	
}

function initExecuteCom()
{
	 fm.all("ExecuteCom").CodeData=getExecuteCom(mSwitch.getVar( "ProposalGrpContNo" ));	
}

function initGrpInfo()
{
	//fm.PrtNo.value=mSwitch.getVar('PrtNo');
	//fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
	//fm.ManageCom.value=mSwitch.getVar('ManageCom');
	//fm.AgentCode.value=mSwitch.getVar('AgentCode');
	//fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
	alert("sfasdf");
	//fm.GrpName.value=mSwitch.getVar('GrpName');
	//fm.CValiDate.value=mSwitch.getVar('CValiDate');
	//fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}




/*****************************************************
 *   结束
 *   
 *****************************************************
 */




</script> 
