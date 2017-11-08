//该文件中包含客户端需要处理的函数和事件

var showInfo;
var mDebug="0";
var turnPage = new turnPageClass();
var mySql = new SqlClass();
//提交后操作,服务器数据返回后执行的操作
function afterSubmit( FlagStr, content )
{
    showInfo.close();
    if (FlagStr == "Fail" )
    {       
        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;  
//        showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
        var name='提示';   //网页名称，可为空; 
        var iWidth=550;      //弹出窗口的宽度; 
        var iHeight=250;     //弹出窗口的高度; 
        var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
        var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
        showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

        showInfo.focus();

    }
    else
    { 
    }
}

//重置按钮对应操作,Form的初始化函数在功能名+Init.jsp文件中实现，函数的名称为initForm()
function resetForm()
{
  try
  {
    initForm();
  }
  catch(re)
  {
    alert("在LLLdPersonQuery.js-->resetForm函数中发生异常:初始化界面错误!");
  }
} 

//取消按钮对应操作
function cancelForm()
{
  
}
 
//提交前的校验、计算  
function beforeSubmit()
{
  //添加操作  
}       

// 查询按钮
function easyQueryClick()
{
    
    var tGrpContNo = fm.GrpContNo.value;
    
    var strSQL = "select a.insuredno,a.Name,a.sex,decode(a.sex,'0','男','1','女',''),a.Birthday,(select codename	from ldcode where '1225101583000' = '1225101583000' and codetype = 'idtype' and code = a.IDType),"
	      +" a.IDNo, a.IDType,b.appntno,b.grpname,b.grpcontno"
        +" from lcinsured a,lcgrpcont b"
        +" where a.grpcontno=b.grpcontno "
        +" and a.grpcontno='"+tGrpContNo+"' " ;
    
//    var strSQL = "select a.insuredno,a.Name,a.sex,decode(a.sex,'0','男','1','女',''),a.Birthday,(select codename	from ldcode where '1225101583000' = '1225101583000' and codetype = 'idtype' and code = a.IDType),"
//    	      +" a.IDNo, a.IDType,b.appntno,b.grpname,b.grpcontno"
//              +" from lcinsured a,lcgrpcont b,lccont c "
//              +" where a.grpcontno=b.grpcontno "
//              +" and c.insuredno = a.insuredno "
//              +" and c.poltype<>'1' "
//              +" and c.grpcontno = b.grpcontno "
//              +" and a.grpcontno='"+tGrpContNo+"' " ;
    
    //if(fm.ManageCom.value != "%")
    //{
        //strSQL += " and a.ManageCom like '"+fm.ManageCom.value+"%%'"
    //}
   /* mySql = new SqlClass();
	mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
	mySql.setSqlId("LLGrpLdPersonQuerySql1");
	mySql.addSubPara(tGrpContNo );   */  
    if(fm.InsuredNo.value!="")
    {
         strSQL=strSQL+"  and a.insuredno='"+fm.InsuredNo.value+"'";
        /* mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql2");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  */
    }
  
    if(fm.Name.value!="")
    {
      strSQL=strSQL+"  and a.name like '%%"+fm.Name.value+"%%'";
      /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql3");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  */
    }
    
    //增加连带被保险人查询
    strSQL = strSQL + " union "
    	+" select d.customerno,d.Name,d.sex,decode(d.sex,'0','男','1','女',''),d.Birthday,(select codename	from ldcode where '1225101583000' = '1225101583000' and codetype = 'idtype' and code = d.IDType),"
	    +" d.IDNo, d.IDType,e.appntno,e.grpname, e.grpcontno"
        +" from lcinsuredrelated d, lcgrpcont e, lcpol f "
        +" where e.grpcontno = f.grpcontno "
        +" and d.polno = f.polno "
        +" and e.grpcontno='"+tGrpContNo+"' " ;
   /* mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql4");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  
		mySql.addSubPara(tGrpContNo ); */   
    
    //if(fm.ManageCom.value != "%")
    //{
        //strSQL += " and e.ManageCom like '"+fm.ManageCom.value+"%%'"
    //}
    
    if(fm.InsuredNo.value!="")
    {
         strSQL=strSQL+"  and d.customerno='"+fm.InsuredNo.value+"'";
         /*mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql5");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  
		mySql.addSubPara(tGrpContNo );  
		mySql.addSubPara(fm.InsuredNo.value );  */
    }
  
    if(fm.Name.value!="")
    {
      strSQL=strSQL+"  and d.name like '%%"+fm.Name.value+"%%'";
     /* mySql = new SqlClass();
		mySql.setResourceName("claimgrp.LLGrpLdPersonQueryInputSql");
		mySql.setSqlId("LLGrpLdPersonQuerySql6");
		mySql.addSubPara(tGrpContNo );    
		mySql.addSubPara(fm.InsuredNo.value );  
		mySql.addSubPara(fm.Name.value );  
		mySql.addSubPara(tGrpContNo );  
		mySql.addSubPara(fm.InsuredNo.value );
		mySql.addSubPara(fm.Name.value );    */
    }
    
    var arrResult = new Array();
//    prompt("团体报案客户查询:",strSQL);
    
    arrResult=easyExecSql(strSQL);
    if(arrResult == null){

     alert("查询不到符合条件的记录!");
     return false;
  }

    turnPage.queryModal(strSQL, PersonGrid);
}



//对应RadioBox的单记录返回
function returnParent()
{
  
   var i = PersonGrid.getSelNo();

   if (i != 0)
   {    
      i = i - 1;
      var arr = new Array();
      arr[0] = PersonGrid.getRowColData(i,1);  //被保险人客户号
      arr[1] = PersonGrid.getRowColData(i,2);  //被保险人姓名
      arr[2] = PersonGrid.getRowColData(i,3);  //被保险人性别
      arr[3] = PersonGrid.getRowColData(i,4);  //被保险人性别名称
      arr[4] = getAge(PersonGrid.getRowColData(i,5));  //被保险人年龄
      arr[5] = PersonGrid.getRowColData(i,6);  //被保险人证件类型
      arr[6] = PersonGrid.getRowColData(i,7);  //被保险人证件号码
      arr[7] = PersonGrid.getRowColData(i,8);  //被保险人证件类型值
      arr[8] = PersonGrid.getRowColData(i,9);  //投保人客户号
      arr[9] = PersonGrid.getRowColData(i,10);  //投保人姓名
      arr[10] = PersonGrid.getRowColData(i,11);  //团体合同号

   }
   if (arr)
   {
       top.opener.afterQueryLL(arr);
   }
   top.close();
}

//参数为出生年月,如1980-5-9 
function getAge(birth)
{
  var now = new Date();
  var nowYear = now.getFullYear();
  var oneYear = birth.substring(0,4);
  var age = nowYear - oneYear;
  if (age <= 0)
  {
    age = 0
  }
  return age;
}



