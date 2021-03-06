 <%
//程序名称：TempFeeInit.jsp
//程序功能：
//创建日期：2002-06-27 08:49:52
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
<%
  String CurrentDate = PubFun.getCurrentDate();
  String CurrentTime = PubFun.getCurrentTime();
%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<SCRIPT src="GrpContInsuredInput.js"></SCRIPT>

<script language="JavaScript">
function initInpBox()
{
    try
    {
        fm.all('ContType').value=ContType;
        fm.all( 'BQFlag' ).value = BQFlag;
        fm.all( 'EdorType' ).value = EdorType;
        fm.all( 'EdorTypeCal' ).value = EdorTypeCal;
        fm.all( 'EdorValiDate' ).value = EdorValiDate;  
        fm.SelPolNo.value='';
        fm.all('OldContNo').value = "";
        try{mSwitch.deleteVar("PolNo");}catch(ex){};
    }
    catch(ex)
    {
        alert("在GrpInsuredInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
    }
}

function initSelBox()
{
    try
    {
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
    }
    catch(ex)
    {
        alert("在GrpInsuredInit.jsp-->InitSelBox函数中发生异常:初始化界面错误!");
    }
}

function initForm()
{
    
        initInpBox();
        initSelBox();
        initInsuredGrid();
        initImpartGrid();
        initMainInsuredGrid();
        initInsuredRelaGrid();
        initContGrid();
        
        queryPol();
        initGrpInfo();

        //设置证件类型初始化数值是9
        //fm.IDType.value="0";
        
        if (scantype== "scan")
        {
            setFocus();
        }
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
        //alert("c");
        initContInfo();
        //initPolInfo();
        //alert("d");

        DivGrpNoname.style.display="none";
        //alert("d1");
        if(fm.all('FamilyType').value==''||fm.all('FamilyType').value==null||fm.all('FamilyType').value=='0'||fm.all('FamilyType').value=='false'){
            fm.all('FamilyType').value='0';
            getAge(); 
            //alert(NameType);
            if(NameType=="1")
            {
                DivLCBasicInfo.style.display="none";     
                //fm.all('PolTypeFlagName').value="无名单"; 
                fm.all('Sex').value="0"; 
                fm.all('InsuredPeoples').readOnly=false;
                        fm.all('InsuredAppAge').readOnly=false; 
                fm.all('Name').value="无名单"; 
                //fm.all('PolTypeFlag').value="1";
            }
            else if(NameType=="2")
            {
                DivLCBasicInfo.style.display="none"; 
                //fm.all('PolTypeFlag').value="2";
                fm.all('InsuredPeoples').readOnly=false;
                fm.all('InsuredAppAge').readOnly=false;     
                //fm.all('PolTypeFlagName').value="公共帐户"; 
                fm.all('Sex').value="0"; 
                fm.all('Name').value="公共帐户"; 
            }else{
            
                //fm.all('PolTypeFlag').value="0";
                //fm.all('PolTypeFlagName').value="个人单";
                fm.all('InsuredPeoples').value="";
                fm.all('InsuredPeoples').value="1";
            }
        }

        try
        {
    }
    catch(re)
    {
        alert("GrpInsuredInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
    }
    if (ContType=="2")
    {
        initContPlanCode();
        //initExecuteCom();
    }

    if(LoadFlag=="18"){ //无名单补录
        fm.InsuredSequencename.value="被保险人资料";
        divaddPerButton.style.display="";
        fm.butBack.style.display="";
        divSamePerson.style.display="none";
        DivRelation.style.display="none";
        //DivAddress.style.display="";
        DivClaim.style.display="none";
        divContPlan.style.display="none";
        divAnotherAddDelButton.style.display="";
        if(checkISFILL()) //是否需要激活判断
        {
          checkGrpContType(); //判断保单类型，确定查询表格
        }
    }
    
    //姓名的初始化
    //if(fm.PolTypeFlag.value=="0")
    //{
    //    fm.Name.focus();
    //}
}


// 暂收费信息列表的初始化
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
//      iArray[1][4]="ImpartVer";
      iArray[1][9]="告知版别|len<=5";
      iArray[1][10]="AppntImpart";
      iArray[1][11]="0|^01|财务及其他告知^02|健康告知";
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

      ImpartGrid = new MulLineEnter( "fm" , "ImpartGrid" );
      //这些属性必须在loadMulLine前
      ImpartGrid.mulLineCount = 1;
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
// 告知明细信息列表的初始化
function initImpartDetailGrid() {
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
      iArray[1][4]="ImpartVer";
      iArray[1][9]="告知版别|len<=5";
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


      ImpartDetailGrid = new MulLineEnter( "fm" , "ImpartDetailGrid" );
      //这些属性必须在loadMulLine前
      ImpartDetailGrid.mulLineCount = 1;
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
      iArray[4][1]="60px";            		        //列宽
      iArray[4][2]=60;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许


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


function initContInfo()
{
    try { fm.all( 'ContNo' ).value = mSwitch.getVar( "ContNo" ); if(fm.all( 'ContNo' ).value=="false"){fm.all( 'ContNo' ).value="";} } catch(ex) { };
    try { fm.all( 'PrtNo' ).value = mSwitch.getVar( "PrtNo" ); } catch(ex) { };
    try { fm.all( 'ProposalContNo' ).value = mSwitch.getVar( "ProposalGrpContNo" ); } catch(ex) { };
    try { fm.all( 'GrpContNo' ).value = mSwitch.getVar( "GrpContNo" ); } catch(ex) { };
    try { fm.all( 'FamilyType' ).value = mSwitch.getVar( "FamilyType" ); } catch(ex) {};
    //try { fm.all( 'PolTypeFlag' ).value = mSwitch.getVar( "PolType" );if(fm.all( 'PolTypeFlag' ).value=="false"){fm.all( 'PolTypeFlag' ).value="0";} } catch(ex) {};
    try { fm.all( 'InsuredPeoples' ).value = mSwitch.getVar( "InsuredPeoples" );if(fm.all( 'InsuredPeoples' ).value=="false"){fm.all( 'InsuredPeoples' ).value="";} } catch(ex) {};
}

function initContPlanCode()
{
	 fm.all("ContPlanCode").CodeData=getContPlanCode(mSwitch.getVar( "ProposalGrpContNo" ));
}

function initExecuteCom()
{
	 //fm.all("ExecuteCom").CodeData=getExecuteCom(mSwitch.getVar( "ProposalGrpContNo" ));
}  

function initGrpInfo()
{
	fm.SaleChnl.value=mSwitch.getVar('SaleChnl');
	fm.ManageCom.value=mSwitch.getVar('ManageCom');
	fm.AgentCode.value=mSwitch.getVar('AgentCode');
	fm.AgentGroup.value=mSwitch.getVar('AgentGroup');
	fm.GrpName.value=mSwitch.getVar('GrpName');
	fm.CValiDate.value=mSwitch.getVar('CValiDate');
	fm.ProposalGrpContNo.value = mSwitch.getVar('ProposalGrpContNo');
}

//主被保人信息
function initMainInsuredGrid() {
    var iArray = new Array();

    try {
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

        iArray[7]=new Array();
        iArray[7][0]="出生日期";      	   		//列名
        iArray[7][1]="80px";            			//列宽
        iArray[7][2]=20;            			//列最大值
        iArray[7][3]=3;

        MainInsuredGrid = new MulLineEnter( "fm" , "MainInsuredGrid" );
        //这些属性必须在loadMulLine前
        MainInsuredGrid.mulLineCount = 0;
        MainInsuredGrid.displayTitle = 1;
        MainInsuredGrid.canSel =1;
        MainInsuredGrid. selBoxEventFuncName ="getInsuredRela" ;     //点击RadioBox时响应的JS函数
        MainInsuredGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        MainInsuredGrid. hiddenSubtraction=1;
        MainInsuredGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}

//保单信息
function initContGrid() {
    var iArray = new Array();

    try {
        iArray[0]=new Array();
        iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
        iArray[0][1]="40px"; 	           		//列宽
        iArray[0][2]=1;            			//列最大值
        iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

        iArray[1]=new Array();
        iArray[1][0]="合同号";          		//列名
        iArray[1][1]="120px";      	      		//列宽
        iArray[1][2]=20;            			//列最大值
        iArray[1][3]=0;             //是否允许输入,1表示允许，0表示不允许,2表示代码选择，3表示该列是隐藏的

        iArray[2]=new Array();
        iArray[2][0]="险种号";         			//列名
        iArray[2][1]="60px";            			//列宽
        iArray[2][2]=20;            			//列最大值
        iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许


        iArray[3]=new Array();
        iArray[3][0]="险种名称";      	   		//列名
        iArray[3][1]="120px";            			//列宽
        iArray[3][2]=20;            			//列最大值
        iArray[3][3]=0;              	//是否允许输入,1表示允许，0表示不允许

        iArray[4]=new Array();
        iArray[4][0]="可录连带被保人数";      	   		//列名
        iArray[4][1]="80px";            			//列宽
        iArray[4][2]=20;            			//列最大值
        iArray[4][3]=0;

        //iArray[5]=new Array();
        //iArray[5][0]="与第一被保险人关系";      	   		//列名
        //iArray[5][1]="100px";            			//列宽
        //iArray[5][2]=20;            			//列最大值
        //iArray[5][3]=3;
        //iArray[5][4]="Relation";              	        //是否引用代码:null||""为不引用
        //iArray[5][9]="与主被保险人关系|code:Relation&NOTNULL";
        //iArray[5][18]=300;

        //iArray[6]=new Array();
        //iArray[6][0]="客户内部号";      	   		//列名
        //iArray[6][1]="80px";            			//列宽
        //iArray[6][2]=20;            			//列最大值
        //iArray[6][3]=3;
        //iArray[6][10]="MutiSequenceNo";
        //iArray[6][11]="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人";

        iArray[5]=new Array();
        iArray[5][0]="保单号";      	   		//列名
        iArray[5][1]="80px";            			//列宽
        iArray[5][2]=20;            			//列最大值
        iArray[5][3]=3;

        iArray[6]=new Array();
        iArray[6][0]="是否校验连带被保险人标志";      	   		//列名
        iArray[6][1]="20px";            			//列宽
        iArray[6][2]=20;            			//列最大值
        iArray[6][3]=3;

        ContGrid = new MulLineEnter( "fm" , "ContGrid" );
        //这些属性必须在loadMulLine前
        ContGrid.mulLineCount = 0;
        ContGrid.displayTitle = 1;
        ContGrid.canSel =1;
        ContGrid. selBoxEventFuncName ="getInsuredInfo" ;     //点击RadioBox时响应的JS函数
        ContGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        ContGrid. hiddenSubtraction=1;
        ContGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}


//连带被保人信息
function initInsuredRelaGrid() {
    var iArray = new Array();

    try {
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
        iArray[5][0]="与主被保险人关系";      	   		//列名
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
        iArray[6][3]=3;
        iArray[6][10]="MutiSequenceNo";
        iArray[6][11]="0|^1|第一被保险人 ^2|第二被保险人 ^3|第三被保险人";

        iArray[7]=new Array();
        iArray[7][0]="出生日期";      	   		//列名
        iArray[7][1]="80px";            			//列宽
        iArray[7][2]=20;            			//列最大值
        iArray[7][3]=3;

        InsuredRelaGrid = new MulLineEnter( "fm" , "InsuredRelaGrid" );
        //这些属性必须在loadMulLine前
        InsuredRelaGrid.mulLineCount = 0;
        InsuredRelaGrid.displayTitle = 1;
        InsuredRelaGrid.canSel =0;
        //InsuredRelaGrid. selBoxEventFuncName ="getInsuredRela" ;     //点击RadioBox时响应的JS函数
        InsuredRelaGrid. hiddenPlus=1;   //是否隐藏"+"号标志：1为隐藏；0为不隐藏(缺省值)
        InsuredRelaGrid. hiddenSubtraction=1;
        InsuredRelaGrid.loadMulLine(iArray);
    }
    catch(ex) {
      alert(ex);
    }
}
</script>
