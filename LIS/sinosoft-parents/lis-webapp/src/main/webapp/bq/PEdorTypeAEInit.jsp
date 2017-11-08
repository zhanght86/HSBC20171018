<%
//PEdorTypeAEInit.jsp
//程序功能：
//创建日期：2002-06-19 11:10:36
//创建人  ：CrtHtml程序创建
//更新记录：  更新人    更新日期     更新原因/内容
%>
<!--用户校验类-->
<%
     //添加页面控件的初始化。
%>
<script language="JavaScript">
var i=2;
var j=4;
var turnPage = new turnPageClass();          //使用翻页功能，必须建立为全局变量
var mySql=new SqlClass();

function initForm()
{
  try
  {
    initInpBox();
    initImpartGrid();
    initInsuredGrid();
    //initQuery();
     getInsuredInfo();
    newOccupatioinInfo();
    //QueryEdorInfo();
    //showCustomerImpart();
    AppntInfShow();
    showLPAppnt(); //查询已经录入的投保人变更信息
    showimpart();
    queryLRInfo();
  }
  catch(re)
  {
    alert("EdorTypeAEInit.jsp-->InitForm函数中发生异常:初始化界面错误!");
  }
}

function initInpBox()
{
  try
  {
	    Edorquery();
	    document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
	    document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
	    document.all('ContNo').value = top.opener.document.all('ContNo').value;
	    document.all('EdorType').value = top.opener.document.all('EdorType').value;
	    document.all('EdorItemAppDate').value = top.opener.document.all('EdorItemAppDate').value;
	    document.all('EdorValiDate').value = top.opener.document.all('EdorValiDate').value;
	    showOneCodeName('PEdorType','EdorType','EdorTypeName');

     	document.all('EdorReason').value = '';       //变更原因
        document.all('deathdate').value = '';        //死亡日期
        document.all('AppntNo').value = '';          //投保人客户号
        document.all('LastName').value = '';         //投保人-姓
        document.all('FirstName').value = '';        //投保人-名
        document.all('AppntName').value = '';        //投保人-姓名
        document.all('LastNameEn').value = '';       //英文-姓
        document.all('FirstNameEn').value = '';      //英文-名
        document.all('LastNamePY').value = '';       //拼音-姓
        document.all('FirstNamePY').value = '';      //拼音-名
        document.all('AppntIDType').value = '';      //证件类型编码
        document.all('AppntIDTypeName').value = '';  //证件类型名称
        document.all('AppntIDNo').value = '';        //证件号码
        document.all('AppntSex').value = '';         //投保人性别编码
        document.all('AppntSexName').value = '';     //投保人性别名称
        document.all('AppntBirthday').value = '';    //出生日期
        document.all('AppntMarriage').value = '';    //婚姻状况编码
        document.all('AppntMarriageName').value = '';  //婚姻状况名称
        document.all('AppntLanguage').value = '';      //语言编码
        document.all('AppntLanguageName').value = '';  //语言名称
        document.all('AppntNativePlace').value = '';   //国籍编码
        document.all('AppntNativePlaceName').value = '';  //国籍名称
        document.all('AppntRgtAddress').value = '';     //户口所在地
        document.all('AppntWorkType').value='';         //职业
        document.all('AppntOccupationCode').value = '';  //职业编码
        document.all('AppntOccupationCodeName').value = '';  //职业名称
        document.all('AppntOccupationType').value='';      //职业类别
        document.all('AppntOccupationTypeName').value='';  //职业类别编码
        document.all('PluralityType').value='';          //兼职
        document.all('AddressNo').value = '';            //地址编码
        document.all('PostalAddress').value = '';        //通讯地址
        document.all('ZipCode').value = '';              //通讯地址邮编
        document.all('HomeAddress').value = '';         //住址
        document.all('HomeZipCode').value = '';         //住址邮编
        document.all('Mobile').value = '';              //首选回访电话
        document.all('GrpPhone').value = '';            //其他联系电话
        document.all('GrpName').value = '';             //工作单位
        document.all('EMail').value = '';               //邮箱
        document.all('GetBankCode').value = '';        //开户行
        document.all('BankName').value='';             //开户行名称
        document.all('GetBankAccNo').value = '';       //账户编码
        document.all('GetAccName').value = '';         //账户名称
  }
  catch(ex)
  {
    alert("在PEdorTypeAEInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }
}

//初始化查询
function initQuery()
{
//   var strSQL ="select * from LPAppnt,LPAddress where LPAppnt.EdorNo=LPAddress.EdorNo and"
//              +" LPAppnt.EdorType=LPAddress.EdorType and LPAppnt.AppntNo=LPAddress.CustomerNo and"
//              +" LPAppnt.AddressNo=LPAddress.AddressNo and LPAppnt.EdorNo='"+fm.EdorNo.value+"' and"
//              +" LPAppnt.EdorType='"+fm.EdorType.value+"' and LPAppnt.ContNo='"+fm.ContNo.value+"'"
//              ;
	var strSQL ="";
  mySql=new SqlClass();
  mySql.setResourceName("bq.PEdorTypeAE");
  mySql.setSqlId("PEdorTypeAESql20");
  mySql.addSubPara(fm.EdorNo.value); 
  mySql.addSubPara(fm.EdorType.value); 
  mySql.addSubPara(fm.ContNo.value); 
  strSQL=mySql.getString();
  var arrResult = easyExecSql(strSQL, 1, 0);
    if (arrResult == null)
       {
       i=0;
       j=0;
//         var strSQL ="select * from LCAppnt,LCAddress where LCAppnt.AppntNo=LCAddress.CustomerNo and"
//              +" LCAppnt.AddressNo=LCAddress.AddressNo and  LCAppnt.ContNo='"+fm.ContNo.value+"'";
        var strSQL ="";
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeAE");
        mySql.setSqlId("PEdorTypeAESql21");
        mySql.addSubPara(fm.ContNo.value); 
        strSQL=mySql.getString();
        arrResult = easyExecSql(strSQL, 1, 0);
        if (arrResult == null)
        {
          alert("无投保人信息");
        }
        else
        {
            displayAddress(arrResult);
        }
      }
    else
    {
        displayAddress(arrResult);
        i=2;
        j=4;
    }
//    var InsuredResult = easyExecSql("select InsuredNo,Name,Sex,Birthday,IDType,IDNo from LCInsured where ContNo ='"+fm.ContNo.value+"'",1,0)
//    if(InsuredResult == null){
//      alert("无被保人信息");
//    }
//    else{
//      DisplayInsured(InsuredResult);
//    }
     getInsuredInfo();

}

function getInsuredInfo()
{
    var ContNo=document.all("ContNo").value;
    var tEdorNo = document.all("EdorNo").value;
    var tEdorType = document.all("EdorType").value;
    if(ContNo!=null&&ContNo!="")
    {
        mySql=new SqlClass();
        mySql.setResourceName("bq.PEdorTypeAE");
        mySql.setSqlId("PEdorTypeAESql1");
        mySql.addSubPara(ContNo); 
        mySql.addSubPara(tEdorNo); 
        mySql.addSubPara(tEdorType); 
        //var strSQL ="select i.InsuredNo,i.Name, (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),i.Birthday,(select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)),i.IdNo,i.Relationtomaininsured,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.Relationtomaininsured),i.RelationToAppnt,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.RelationToAppnt) from LPInsured i where i.ContNo='"+ContNo+"' and i.edorno = '" + tEdorNo + "' and edortype = '" + tEdorType + "' order by i.sequenceno";
        var  brrResult = easyExecSql(mySql.getString());
        //判断是否查询成功
        if (!brrResult)
        {
            mySql=new SqlClass();
            mySql.setResourceName("bq.PEdorTypeAE");
            mySql.setSqlId("PEdorTypeAESql2");
            mySql.addSubPara(ContNo);         
            //strSQL ="select i.InsuredNo,i.Name, (select trim(u.code)||'-'||trim(u.CodeName) from LDCode u where trim(u.codetype) = 'sex' and trim(u.code) = trim(sex)),i.Birthday,(select trim(y.code)||'-'||trim(y.CodeName) from LDCode y where trim(y.codetype) = 'idtype' and trim(y.code) = trim(idtype)),i.IdNo,i.Relationtomaininsured,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.Relationtomaininsured),i.RelationToAppnt,(select w.CodeName from ldcode w where w.codetype = 'relation' and w.code = i.RelationToAppnt) from LCInsured i where i.ContNo='"+ContNo+"' order by i.sequenceno";
            brrResult = easyExecSql(mySql.getString());
            if (!brrResult)
            {
                alert("查询投保人与被保人关系失败！");
                return false;
            }
            
        }
        
        displayMultiline(brrResult, InsuredGrid);
        
    }
    if (InsuredGrid.mulLineCount > 0)
    {
        tRelation = InsuredGrid.getRowColData(0, 9);
    }
    if (InsuredGrid.mulLineCount > 1)
    {
        tRelation2 = InsuredGrid.getRowColData(1, 9);
    }
    //alert(tRelation);

}

//显示查询结果
function displayAddress(arrResult)
{

     //document.all('PCustomerNo').value = arrResult[0][3+parseInt(i)];
     //document.all('PName').value =arrResult[0][5+parseInt(i)];
     //document.all('PSex').value = arrResult[0][6+parseInt(i)];

    // document.all('PBirthday').value = arrResult[0][7+parseInt(i)];


    // document.all('PIDType').value = arrResult[0][10+parseInt(i)];
    // document.all('PIDNo').value =arrResult[0][11+parseInt(i)];


    //var oldSex =document.all('AppntSex').value;
   // var oldBirthday = document.all('AppntBirthday').value;
   // var oldOccupationType = document.all('OccupationType').value;
   // var oldName=document.all('AppntName').value;
   
}



function initInsuredGrid() {
     var iArray = new Array();

     try {
          iArray[0]=new Array();
      iArray[0][0]="序号";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";                  //列宽
      iArray[0][2]=10;                      //列最大值
      iArray[0][3]=0;                       //是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="被保人客户号";              //列名
      iArray[1][1]="60px";                 //列宽
      iArray[1][2]=90;                      //列最大值
      iArray[1][3]=0;                       //是否允许输入,1表示允许，0表示不允许
      iArray[1][4]="InsuredNo";
      iArray[1][9]="被保人客户号|len<=90";

      iArray[2]=new Array();
      iArray[2][0]="姓名";                    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[2][1]="30px";                  //列宽
      iArray[2][2]=50;                      //列最大值
      iArray[2][3]=0;
      iArray[2][4]="InsureName";
      iArray[2][9]="姓名|len<=50";

      iArray[3]=new Array();
      iArray[3][0]="性别";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[3][1]="30px";                  //列宽
      iArray[3][2]=30;                      //列最大值
      iArray[3][3]=0;
      iArray[3][9]="性别|len<=30";

      iArray[4]=new Array();
      iArray[4][0]="出生日期";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[4][1]="50px";                  //列宽
      iArray[4][2]=50;                      //列最大值
      iArray[4][3]=8;
      iArray[4][4]="Birthday";
      iArray[4][9]="出生日期|len<=50";

      iArray[5]=new Array();
      iArray[5][0]="证件类型";                  //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[5][1]="40px";                  //列宽
      iArray[5][2]=100;                      //列最大值
      iArray[5][3]=0;
      iArray[5][9]="证件类型|len<=40";

      iArray[6]=new Array();
      iArray[6][0]="证件号";                    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[6][1]="100px";                 //列宽
      iArray[6][2]=150;                      //列最大值
      iArray[6][3]=0;
      iArray[6][4]="IDNo";
      iArray[6][9]="证件号|len<=80";


      iArray[7]=new Array();
      iArray[7][0]="与主被保人关系";                    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[7][1]="70px";                  //列宽
      iArray[7][2]=100;                      //列最大值
      iArray[7][3]=2;
      iArray[7][4]="relation";
      iArray[7][5]="7|8";
      iArray[7][6]="0|1";
      iArray[7][9]="与主被保人关系|len<=50";

      iArray[8]=new Array();
      iArray[8][0]="与主被保人关系";                    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[8][1]="70px";                  //列宽
      iArray[8][2]=100;                      //列最大值
      iArray[8][3]=0;


      iArray[9]=new Array();
      iArray[9][0]="与投保人关系";                    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[9][1]="70px";                  //列宽
      iArray[9][2]=100;                      //列最大值
      iArray[9][3]=2;
      iArray[9][4]="relation";
      iArray[9][5]="9|10";
      iArray[9][6]="0|1";
      iArray[9][9]="与投保人关系|len<=50";

      iArray[10]=new Array();
      iArray[10][0]="与投保人关系";                    //列名（此列为顺序号，列名无意义，而且不显示）
      iArray[10][1]="70px";                  //列宽
      iArray[10][2]=100;                      //列最大值
      iArray[10][3]=0;
      
      InsuredGrid = new MulLineEnter( "fm" , "InsuredGrid" );
      //这些属性必须在loadMulLine前
      InsuredGrid.mulLineCount = 0;
      InsuredGrid.displayTitle = 1;
      InsuredGrid.canChk=0;
      InsuredGrid.canSel =0;
      InsuredGrid.hiddenPlus=1;
      InsuredGrid.hiddenSubtraction=1;
      //ImpartGrid.tableWidth   ="500px";
      InsuredGrid.loadMulLine(iArray);

      //这些操作必须在loadMulLine后面
      //ImpartGrid.setRowColData(1,1,"asdf");
    }
    catch(ex) {
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
      iArray[1][4]="impartver";
      iArray[1][15]="1";
      iArray[1][16]="#1# and code in (#A01#,#A02#)";
      iArray[1][18]=300;

      iArray[2]=new Array();
      iArray[2][0]="告知编码";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=60;            			//列最大值
      iArray[2][3]=2;              			//是否允许输入,1表示允许，0表示不允许
      iArray[2][4]="ImpartCode";
      iArray[2][5]="2|3|4";
      iArray[2][6]="0|1|2";
      iArray[2][9]="告知编码|len<=5";
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

function newOccupatioinInfo(){
     mySql=new SqlClass();
     mySql.setResourceName("bq.PEdorTypeAE");
     mySql.setSqlId("PEdorTypeAESql3");
     mySql.addSubPara(top.opener.document.all('EdorNo').value); 
     mySql.addSubPara(top.opener.document.all('EdorType').value);
     mySql.addSubPara(top.opener.document.all('InsuredNo').value);
     
     //var strsql = "select OccupationCode,OccupationType from lpinsured where 1=1 and EdorNo = '"+top.opener.document.all('EdorNo').value+"' and EdorType = '"+top.opener.document.all('EdorType').value+"' and insuredno = '"+top.opener.document.all('InsuredNo').value+"'";
     var aResult = easyExecSql(mySql.getString(),1,0);
   if(aResult != null){
      document.all('OccupationCode').value = aResult[0][0];
      document.all('OccupationType').value = aResult[0][1];
   }
}


</script>