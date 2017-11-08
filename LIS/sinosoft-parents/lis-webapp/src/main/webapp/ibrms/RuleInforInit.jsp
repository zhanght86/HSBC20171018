
<script language="JavaScript">
var tResourceName = 'ibrms.RuleInforSql';
function initForm()
{  
	if(flag==1)
	{
		initForm_Create(); //新建规则
	}
	else if (flag==2||flag==4)
		 initForm_Modify(); //复制、修改规则
	else if (flag==3||flag==5||flag==6||flag==7||flag==8||flag==9)
		 initForm_Read(); //查询规则
	else
		alert("Flag值传入错误！");
}


    function initForm_Create()
     {
	   var str='';
	   //初始化“创建者”
	   var sqlid1="RuleInforSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(Operator);//指定传入的参数
	
	  var strSql=mySql1.getString();	
	  
	  // var creatorSQL="select usercode,username from lduser where usercode='"+Operator+"'";
	    str=easyQueryVer3(strSql);
	    var creatorArray=decodeEasyQueryResult(str);
        try{
	    fm.Creator.value=creatorArray[0][0];
	    fm.Creator.readOnly="true";
	    fm.CreatorName.value=creatorArray[0][1];
	    fm.CreatorName.readOnly="true";
        }catch(e)
        {
        	alert("创建者信息初始化错误！");
         }
	   
	   //初始化“级别”
	  var sqlid2="RuleInforSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara('ibrmstemplatelevel');//指定传入的参数
	
	  var strSq2=mySql2.getString();	
	  
	  // var templateLevelSQL="select code,codename from ldcode where code=6 and codetype='ibrmstemplatelevel'";

	    str=easyQueryVer3(strSq2);
	    var templateLevelArray=decodeEasyQueryResult(str);
        try{
	    fm.TemplateLevel.value=templateLevelArray[0][0];
	    fm.TemplateLevel.readOnly="true";
	    fm.TemplateLevelName.value=templateLevelArray[0][1];
	    fm.TemplateLevelName.readOnly="true";
        }catch(e)
        {
        	alert("级别信息初始化错误！");
            }
	   //初始化“业务模块”
	  var sqlid3="RuleInforSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara('ibrmsbusiness');//指定传入的参数
	
	  var strSq3=mySql3.getString();	
	   
	   
	  //  var businessSQL="select code,codename from ldcode where code='01' and codetype='ibrmsbusiness'";

	    str=easyQueryVer3(strSq3);
	    var businessArray=decodeEasyQueryResult(str);
        try{
	    fm.Business.value=businessArray[0][0];
	    fm.Business.readOnly="true";
	    fm.BusinessName.value=businessArray[0][1];
	    fm.BusinessName.readOnly="true";
        }catch(e)
        {
        	alert("业务模块信息初始化错误！");
            }
	    
	   //初始化“状态”
	 //  var stateSQL="select code,codename from ldcode where code=0 and codetype='ibrmsstate'";
		var sqlid4="RuleInforSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(tResourceName); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara('ibrmsstate');//指定传入的参数
	
	  var strSq4=mySql4.getString();	


	    var str=easyQueryVer3(strSq4);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.State.value=stateArray[0][0];
		    fm.State.readOnly="true";
		    fm.StateName.value=stateArray[0][1];
		    fm.StateName.readOnly="true";
	        }catch(e)
	        {
	        	alert("状态信息初始化错误！");
	            }

	   //初始化“有效标志”
	  // var validSQL="select code,codename from ldcode where code=1 and codetype='ibrmsvalid'";
		
		var sqlid5="RuleInforSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(tResourceName); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara('ibrmsvalid');//指定传入的参数
	
	  var strSq5=mySql5.getString();	
	  
	  

	    str=easyQueryVer3(strSq5);
	    var validArray=decodeEasyQueryResult(str);

	    try{
	    fm.Valid.value=validArray[0][0];
	    fm.Valid.readOnly="true";
	    fm.ValidName.value=validArray[0][1];
	    fm.ValidName.readOnly="true";
	    }catch(e)
        {
        	alert("有效标志信息初始化错误！");
            }
}

function initForm_Modify()
{
		var sqlid6="RuleInforSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(tResourceName); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(LRTemplateName);//指定传入的参数
		mySql6.addSubPara(LRTemplate_ID);//指定传入的参数
	
	  var strSq6=mySql6.getString();	
	
//var sql="select RuleName,Description,StartDate,EndDate,TemplateLevel,Business,Creator,State,Valid from "+LRTemplateName+" where id='"+LRTemplate_ID+"'";
	var str=easyQueryVer3(strSq6);
    var validArray=decodeEasyQueryResult(str);

    var RuleName=validArray[0][0];
    var RuleDes=validArray[0][1];
    var StartDate=validArray[0][2];
    var EndDate=validArray[0][3];
    var TemplateLevel=validArray[0][4];
    var Business=validArray[0][5];
    var Creator=validArray[0][6];
    var State=1; //validArray[0][7];
    var Valid="1";

    fm.RuleName.value=RuleName;
    fm.RuleDes.value=RuleDes;
    fm.RuleStartDate.value=StartDate;
    fm.RuleEndDate.value=EndDate;

	  Operator = Creator;
		initForm_Create();
		 try{
	    fm.Creator.value=Creator;
	    fm.Creator.readOnly="disabled";
     }catch(e)
     {
     	alert("创建者信息初始化错误！");
       }
	/*
    var str='';
	   //初始化“创建者”
	   var creatorSQL="select username from lduser where usercode='"+Creator+"'";
	    str=easyQueryVer3(creatorSQL);
	    var creatorArray=decodeEasyQueryResult(str);
     try{
	    fm.Creator.value=Creator;
	    fm.Creator.readOnly="disabled";
	    fm.CreatorName.value=creatorArray[0][0];
	    fm.CreatorName.readOnly="true";
     }catch(e)
     {
     	alert("创建者信息初始化错误！");
         }
	   
	   //初始化“级别”
	   var templateLevelSQL="select codename from ldcode where codetype='ibrmstemplatelevel' and code='"+TemplateLevel+"'";

	    str=easyQueryVer3(templateLevelSQL);
	    var templateLevelArray=decodeEasyQueryResult(str);
     try{
	    fm.TemplateLevel.value=TemplateLevel;
	    fm.TemplateLevel.readOnly="true";
	    fm.TemplateLevelName.value=templateLevelArray[0][0];
	    fm.TemplateLevelName.readOnly="true";
     }catch(e)
     {
     	alert("级别信息初始化错误！");
         }
	   //初始化“业务模块”
	    var businessSQL="select codename from ldcode where codetype='ibrmsbusiness' and code='"+Business+"'";

	    str=easyQueryVer3(businessSQL);
	    var businessArray=decodeEasyQueryResult(str);
     try{
	    fm.Business.value=Business;
	    fm.Business.readOnly="true";
	    fm.BusinessName.value=businessArray[0][0];
	    fm.BusinessName.readOnly="true";
     }catch(e)
     {
     	alert("业务模块信息初始化错误！");
         }
	    
	   //初始化“状态”
	   var stateSQL="select codename from ldcode where codetype='ibrmsstate' and code='"+State+"'";

	    var str=easyQueryVer3(stateSQL);
	    var stateArray=decodeEasyQueryResult(str);
	    try{
		    fm.State.value=State;
		    fm.State.readOnly="true";
		    fm.StateName.value=stateArray[0][0];
		    fm.StateName.readOnly="true";
	        }catch(e)
	        {
	        	alert("状态信息初始化错误！");
	            }

	   //初始化“有效标志”
	   var validSQL="select codename from ldcode where codetype='ibrmsvalid' and code='"+Valid+"'";

	    str=easyQueryVer3(validSQL);
	    var validArray=decodeEasyQueryResult(str);

	    try{
	    fm.Valid.value=Valid;
	    fm.Valid.readOnly="true";
	    fm.ValidName.value=validArray[0][0];
	    fm.ValidName.readOnly="true";
	    }catch(e)
     {
     	alert("有效标志信息初始化错误！");
         }
         
         */
}
</script>

