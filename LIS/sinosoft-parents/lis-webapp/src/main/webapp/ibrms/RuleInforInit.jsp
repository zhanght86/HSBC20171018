
<script language="JavaScript">
var tResourceName = 'ibrms.RuleInforSql';
function initForm()
{  
	if(flag==1)
	{
		initForm_Create(); //�½�����
	}
	else if (flag==2||flag==4)
		 initForm_Modify(); //���ơ��޸Ĺ���
	else if (flag==3||flag==5||flag==6||flag==7||flag==8||flag==9)
		 initForm_Read(); //��ѯ����
	else
		alert("Flagֵ�������");
}


    function initForm_Create()
     {
	   var str='';
	   //��ʼ���������ߡ�
	   var sqlid1="RuleInforSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql1.setSqlId(sqlid1);//ָ��ʹ�õ�Sql��id
		mySql1.addSubPara(Operator);//ָ������Ĳ���
	
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
        	alert("��������Ϣ��ʼ������");
         }
	   
	   //��ʼ��������
	  var sqlid2="RuleInforSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql2.setSqlId(sqlid2);//ָ��ʹ�õ�Sql��id
		mySql2.addSubPara('ibrmstemplatelevel');//ָ������Ĳ���
	
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
        	alert("������Ϣ��ʼ������");
            }
	   //��ʼ����ҵ��ģ�顱
	  var sqlid3="RuleInforSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql3.setSqlId(sqlid3);//ָ��ʹ�õ�Sql��id
		mySql3.addSubPara('ibrmsbusiness');//ָ������Ĳ���
	
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
        	alert("ҵ��ģ����Ϣ��ʼ������");
            }
	    
	   //��ʼ����״̬��
	 //  var stateSQL="select code,codename from ldcode where code=0 and codetype='ibrmsstate'";
		var sqlid4="RuleInforSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql4.setSqlId(sqlid4);//ָ��ʹ�õ�Sql��id
		mySql4.addSubPara('ibrmsstate');//ָ������Ĳ���
	
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
	        	alert("״̬��Ϣ��ʼ������");
	            }

	   //��ʼ������Ч��־��
	  // var validSQL="select code,codename from ldcode where code=1 and codetype='ibrmsvalid'";
		
		var sqlid5="RuleInforSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql5.setSqlId(sqlid5);//ָ��ʹ�õ�Sql��id
		mySql5.addSubPara('ibrmsvalid');//ָ������Ĳ���
	
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
        	alert("��Ч��־��Ϣ��ʼ������");
            }
}

function initForm_Modify()
{
		var sqlid6="RuleInforSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName(tResourceName); //ָ��ʹ�õ�properties�ļ���
		mySql6.setSqlId(sqlid6);//ָ��ʹ�õ�Sql��id
		mySql6.addSubPara(LRTemplateName);//ָ������Ĳ���
		mySql6.addSubPara(LRTemplate_ID);//ָ������Ĳ���
	
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
     	alert("��������Ϣ��ʼ������");
       }
	/*
    var str='';
	   //��ʼ���������ߡ�
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
     	alert("��������Ϣ��ʼ������");
         }
	   
	   //��ʼ��������
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
     	alert("������Ϣ��ʼ������");
         }
	   //��ʼ����ҵ��ģ�顱
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
     	alert("ҵ��ģ����Ϣ��ʼ������");
         }
	    
	   //��ʼ����״̬��
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
	        	alert("״̬��Ϣ��ʼ������");
	            }

	   //��ʼ������Ч��־��
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
     	alert("��Ч��־��Ϣ��ʼ������");
         }
         
         */
}
</script>

