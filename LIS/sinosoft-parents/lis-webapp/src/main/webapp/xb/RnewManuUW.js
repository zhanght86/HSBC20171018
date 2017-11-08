//程序名称：EdorAppManuUW.js
//程序功能：保全人工核保
//创建日期：2005-05-04 16:49:22
//创建人  ：zhangtao
//更新记录：  更新人 liurongxiao   更新日期   2005-06-20  更新原因/内容 与新契约界面取得一致;添加按钮功能

var showInfo;
var turnPage = new turnPageClass();
var turnPageEdorMainGrid = new turnPageClass();
var turnPageInsured = new turnPageClass();
var cflag = "1";  //问题件操作位置 1.核保
var tContNo="";
var tPrtNo="";
var pflag = "1";  //保单类型 1.个人单
var QuestQueryFlag = new Array() ;
var sFeatures = "toolbar=no, menubar=no, scrollbars=yes, resizable=yes, location=no, status=yes;";

/*********************************************************************
 *  查询保全申请信息
 *  描述: 查询保全申请信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initQuery()
{
    var ActivityStatus;
    var strSQL;
    showEdorMainList();  //查询一个保单号下所有的续保二核申请
}


function showEdorMainList()
{
    //查询保全批改信息
	
	    var sqlid1="RnewManuUWSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql1.setSqlId(sqlid1);//指定使用的Sql的id
		mySql1.addSubPara(document.all('ContNo').value);//指定传入的参数
	    strSQL=mySql1.getString();	
	
//    strSQL =  " select  t.missionprop2,t.missionprop7,t.missionprop5,t.missionprop8,t.missionprop10 "
//     +" ,(select cvalidate from lcpol where polno=t.missionprop3 and appflag='9'), "
//     +"  (case when exists (select 1 from lccontstate a, lcpol b where b.contno = t.missionprop2 "
//		 +"		and b.riskcode = t.MissionProp4 and b.polno = a.polno and b.appflag='9' and a.statetype = 'Terminate' "
//		 +" and a.state = '1'and a.startdate <= sysdate and a.enddate is null) then '终止' "
//     +"  when exists (select 1 from lccontstate a, lcpol b where b.contno = t.missionprop2 "
//		 +"	and b.riskcode = t.MissionProp4 and b.polno = a.polno and b.appflag='9' and a.statetype = 'Available'and a.state = '1' "
//     +"  and a.startdate <= sysdate and a.enddate is null) then '失效' else '有效' end)"
//     +" ,t.missionid,t.submissionid,t.activityid,t.activitystatus,t.missionprop9,t.missionprop3,t.missionprop4 "
//     +" from lwmission t where 1=1 and activityid='0000007001' and t.missionprop2='"+document.all('ContNo').value+"' "
	  // prompt("",strSQL);
    var crr = easyExecSql(strSQL, 1, 0,"","",1);
    if ( !crr )
    {
        alert("该保单下没有需要二核的险种，请点击返回按钮回到主界面！")
        return;
    }
    else
    {
        //document.all('UWType').value = "EdorMain";
        turnPageEdorMainGrid.pageDivName = "divTurnPageEdorMainGrid";
        turnPageEdorMainGrid.queryModal(strSQL, EdorMainGrid);
        divEdorMainInfo.style.display="";
    }
}



/*********************************************************************
 *  保全人工核保
 *  描述: 显示人工核保界面
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showDetailInfo()
{
 document.all('UWType').value = "EdorMain";
  DivDetailInfo.style.display='';
 divContUWLable.style.display = '';
 divAppUWLable.style.display = 'none';
 initDivDetailInfo();//alert(171);
 UWcancel();//清空下核保结论输入框
  //生成工作流后续结点
  var tSelNo = EdorMainGrid.getSelNo()-1;
 // alert("showDetailInfo"+EdorMainGrid.getRowColData(tSelNo));
  fm.MissionID.value=EdorMainGrid.getRowColData(tSelNo,8);
  fm.SubMissionID.value=EdorMainGrid.getRowColData(tSelNo,9);
  fm.ActivityStatus.value=EdorMainGrid.getRowColData(tSelNo,11);
  
 //alert(tContNo);
 //然后根据需要看是否需要进入工作流流转
 
 	    var sqlid2="RnewManuUWSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql2.setSqlId(sqlid2);//指定使用的Sql的id
		mySql2.addSubPara(document.all('MissionID').value);//指定传入的参数
	     var check_sql=mySql2.getString();	
 
// var check_sql=" select count(*) from lwmission where processid='0000000007' and activityid ='0000007001' "
//   +" and missionid='"+fm.MissionID.value+"' and ActivityStatus<>'1' ";
 var check_flag=0; 
 check_flag = easyExecSql(check_sql, 1, 1, 1);
 //alert("check_flag="+check_flag);
 if(check_flag=="0" ) //工作流起始节点处于1状态时需要生成后续工作流
  {
  	//alert("11111111");
  	//新修改的工作流流向，不准产生新的工作流，只保留一个叫续保核保初始节点
   // makeWorkFlow(tContNo);
  }
  //另外，当核保通知书发送完后并且全部回收后，再次流转至此会重新生成发放核保通知书的工作流节点
  //此时，该任务应该有节点， 0000007001 和 0000007003
  //另外可能会有一个从来就没有往下走的0000007002节点
  
   	    var sqlid3="RnewManuUWSql3";
		var mySql3=new SqlClass();
		mySql3.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql3.setSqlId(sqlid3);//指定使用的Sql的id
		mySql3.addSubPara(document.all('MissionID').value);//指定传入的参数
		mySql3.addSubPara(document.all('MissionID').value);//指定传入的参数
	     var check_sql2=mySql3.getString();	
  
//  var check_sql2=" select count(*) from lwmission where processid='0000000007' and activityid ='0000007001' "
//   +" and missionid='"+fm.MissionID.value+"' and ActivityStatus='2' "
//   +" and not exists(select 1 from lwmission where processid='0000000007'and missionid='"+fm.MissionID.value+"' and activityid not in ('0000007001','0000007002','0000007003')  ) ";
  var check_flag2=0; 
  check_flag2 = easyExecSql(check_sql2, 1, 1, 1);
  //alert("check_flag2="+check_flag2);
  if(check_flag2=="1" )
  {
  	//alert("11111111");
  	//新修改的工作流流向，不准产生新的工作流，只保留一个叫续保核保初始节点。
  	//有需要再修改
  //  makeWorkFlow(tContNo);
  }
  
}

/*********************************************************************
 *  初始化保全保单级详细信息
 *  描述: 初始化保全保单级详细信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
  */
 function initDivDetailInfo()
 {
     var tSelNo = EdorMainGrid.getSelNo()-1;
     var tAppntNo = EdorMainGrid.getRowColData(tSelNo,2);//alert("tAppntNo=="+tAppntNo);
     var tInsuredNo = EdorMainGrid.getRowColData(tSelNo,12);//alert("tAppntNo=="+tAppntNo);
     fm.InsuredNo.value=tInsuredNo;
     tContNo = EdorMainGrid.getRowColData(tSelNo,1);//alert("tContNo:"+tContNo);
     fm.RiskCode.value=EdorMainGrid.getRowColData(tSelNo,14);
     fm.ProposalNo.value=EdorMainGrid.getRowColData(tSelNo,13);
     easyQueryClickUW();
     //initLpcont(tEdorNo,tContNo);  //初始化合同信息
     initLpAppt(tAppntNo,tContNo);  //初始化投保人
     initLppol(tInsuredNo,tContNo);  //初始化被保人
     //initFormerUW(tContNo,tEdorNo);//查询原核保结论
     //prompt("",str6);
 }
  //初始化投保人信息
  function initLpAppt(tAppntNo,tContNo)
 {
 	 
	    var sqlid4="RnewManuUWSql4";
		var mySql4=new SqlClass();
		mySql4.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql4.setSqlId(sqlid4);//指定使用的Sql的id
		mySql4.addSubPara(tContNo);//指定传入的参数
	    var strSql=mySql4.getString();	

//    var strSql = " select a.AppntName,"
//                     +"(select codename from ldcode where codetype = 'sex' and trim(code) = trim(a.AppntSex)),"
//                     +"a.AppntBirthday,(select occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode)),"
//                     +"(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType)),"
//                     +"(select codename from ldcode where codetype = 'nativeplace' and trim(code) = trim(a.NativePlace)),"
//                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'vipvalue' and trim(code) = trim(b.VIPValue)),"
//                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'blacklistflag' and trim(code) = trim(b.blacklistFlag)),"
//                     +"a.appntNo"
//                     +" from lcappnt a, ldperson b where 1=1"
//                     +" and a.ContNo='"+tContNo+"'"
//                     +" and b.customerNo=a.AppntNo";
    var brr = easyExecSql(strSql, 1, 0,"","",1);

    var tAppntBirthday="";
    if(brr)
    {
         brr[0][0]==null||brr[0][0]=='null'?'0':fm.AppntName.value  = brr[0][0];
         brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntSexName.value  = brr[0][1];
         brr[0][2]==null||brr[0][2]=='null'?'0':tAppntBirthday  = brr[0][2];
         fm.AppntBirthday.value=calAge(tAppntBirthday);
         brr[0][3]==null||brr[0][3]=='null'?'0':fm.OccupationCodeName.value  = brr[0][3];
         brr[0][4]==null||brr[0][4]=='null'?'0':fm.OccupationTypeName.value  = brr[0][4];
         brr[0][5]==null||brr[0][5]=='null'?'0':fm.NativePlace.value  = brr[0][5];
         //brr[0][6]==null||brr[0][6]=='null'?'0':fm.VIPValue.value  = brr[0][6];
         //brr[0][7]==null||brr[0][7]=='null'?'0':fm.BlacklistFlag.value  = brr[0][7];
         brr[0][8]==null||brr[0][8]=='null'?'0':fm.AppntNo.value  = brr[0][8];
    }
    else
    {
            //如果c表都查不到，则直接查询客户资料表
			
		var sqlid5="RnewManuUWSql5";
		var mySql5=new SqlClass();
		mySql5.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql5.setSqlId(sqlid5);//指定使用的Sql的id
		mySql5.addSubPara(tAppntNo);//指定传入的参数
	    strSql=mySql5.getString();	
			
//            strSql = "select Name,(select codename from ldcode where codetype = 'sex' and trim(code) = trim(Sex)),"
//                     +"Birthday,(select occupationname from ldoccupation where trim(ldoccupation.occupationcode) = trim(ldperson.OccupationCode)),"
//                     +"(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(OccupationType)),"
//                     +"(select codename from ldcode where codetype = 'nativeplace' and trim(code) = trim(NativePlace)),"
//                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'vipvalue' and trim(code) = trim(VIPValue)),"
//                     +"(select trim(code)||'-'||trim(codename) from ldcode where codetype = 'blacklistflag' and trim(code) = trim(blacklistFlag)), "
//                     +" customerno "
//                     +" from ldperson where customerno = '"+tAppntNo+"'";

            brr = easyExecSql(strSql, 1, 0,"","",1);
            if(brr)
            {
                  brr[0][0]==null||brr[0][0]=='null'?'0':fm.AppntName.value  = brr[0][0];
                  brr[0][1]==null||brr[0][1]=='null'?'0':fm.AppntSexName.value  = brr[0][1];
                  brr[0][2]==null||brr[0][2]=='null'?'0':fm.AppntBirthday.value  = brr[0][2];
                  fm.AppntAge.value=calAge(fm.AppntBirthday.value);
                  brr[0][3]==null||brr[0][3]=='null'?'0':fm.OccupationCodeName.value  = brr[0][3];
                  brr[0][4]==null||brr[0][4]=='null'?'0':fm.OccupationTypeName.value  = brr[0][4];
                  brr[0][5]==null||brr[0][5]=='null'?'0':fm.NativePlaceName.value  = brr[0][5];
                  //brr[0][6]==null||brr[0][6]=='null'?'0':fm.VIPValue.value  = brr[0][6];
                  //brr[0][7]==null||brr[0][7]=='null'?'0':fm.BlacklistFlag.value  = brr[0][7];
                  brr[0][8]==null||brr[0][8]=='null'?'0':fm.AppntNo.value  = brr[0][8];
             }
             else
             {
                  alert("投保人信息查询失败！");
                  return "";

             }


        
     }
     
     if(tContNo!=null && tContNo!='')
     {
     	 //查询投保人的身高、体重，有则显示  
			 var nnPrtNo = document.all('PrtNo').value;
			if(nnPrtNo!=null && nnPrtNo!="" && nnPrtNo.length==14 && nnPrtNo.substring(2,4)=="51")//家庭单
			{
				
		var sqlid6="RnewManuUWSql6";
		var mySql6=new SqlClass();
		mySql6.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql6.setSqlId(sqlid6);//指定使用的Sql的id
		mySql6.addSubPara(tContNo);//指定传入的参数
	    var sql=mySql6.getString();	
				
//			  var sql = "select impartparamname, impartparam"
//		         + " from lccustomerimpartparams"
//		         + " where 1 = 1"
//		         + " and impartcode = 'D0101'"
//		         + " and impartver = 'D01'"
//		         + " and impartparamno in ('1', '2')"
//		         + " and contno = '"+tContNo+"'"
//		         + " order by customernotype desc,customerno,impartparamno ";
			   var arr1 = easyExecSql(sql);
			   if(arr1!=null)
			   {
					document.all('Stature').value=arr1[0][1];
					document.all('Weight').value=arr1[1][1];
					document.all('BMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
				}
				
		var sqlid7="RnewManuUWSql7";
		var mySql7=new SqlClass();
		mySql7.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql7.setSqlId(sqlid7);//指定使用的Sql的id
		mySql7.addSubPara(tContNo);//指定传入的参数
	    var insql=mySql7.getString();	
				
//				var insql = "select impartparam"
//		         + " from lccustomerimpartparams"
//		         + " where 1 = 1"
//		         //+ " and customernotype = '1'"             
//		         + " and impartcode = 'D0119'"
//		         + " and impartver = 'D02'"
//		         + " and impartparamno ='1'"
//		         + " and contno = '"+tContNo+"'"
//		         + " order by customernotype ,customerno,impartparamno ";		
			    var incomeway = easyExecSql(insql);
			    if(incomeway!=null && incomeway !="")
			    {
			       document.all('income').value = incomeway;
			    }
			}
			else
			{
				
				var sqlid8="RnewManuUWSql8";
				var mySql8=new SqlClass();
				mySql8.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql8.setSqlId(sqlid8);//指定使用的Sql的id
				mySql8.addSubPara(tContNo);//指定传入的参数
			    var sql=mySql8.getString();	
				
//				 var sql = "select impartparamname, impartparam"
//		         + " from lccustomerimpartparams"
//		         + " where 1 = 1"
//		         //+ " and customernotype = '0'"         
//		         + " and impartcode = 'A0101'"
//		         + " and impartver = 'A01'"
//		         + " and impartparamno in ('3', '4')"
//		         + " and contno = '"+tContNo+"'"
//		         + " order by customernotype ,customerno,impartparamno ";
			   var arr1 = easyExecSql(sql);
			   if(arr1!=null)
			   {
					document.all('Stature').value=arr1[0][1];
					document.all('Weight').value=arr1[1][1];
					document.all('BMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
				}
				
				var sqlid9="RnewManuUWSql9";
				var mySql9=new SqlClass();
				mySql9.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql9.setSqlId(sqlid9);//指定使用的Sql的id
				mySql9.addSubPara(tContNo);//指定传入的参数
			    var insql=mySql9.getString();	
				
//				var insql = "select impartparam"
//		         + " from lccustomerimpartparams"
//		         + " where 1 = 1"
//		         //+ " and customernotype = '0'"             
//		         + " and impartcode = 'A0120'"
//		         + " and impartver = 'A02'"
//		         + " and impartparamno ='3'"
//		         + " and contno = '"+tContNo+"'"
//		         + " order by customernotype ,customerno,impartparamno ";		
			    var incomeway = easyExecSql(insql);
			    if(incomeway!=null && incomeway !="")
			    {
			       document.all('income').value = incomeway;//查询年收入
			    }
			}
	 }
     
     if(document.all('AppntNo').value!=null && document.all('AppntNo').value!='')
     {
	     var tSumAmnt =0;
		 
		 		var sqlid10="RnewManuUWSql10";
				var mySql10=new SqlClass();
				mySql10.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql10.setSqlId(sqlid10);//指定使用的Sql的id
				mySql10.addSubPara(document.all('AppntNo').value);//指定传入的参数
			    strSQL=mySql10.getString();	
		 
//	    strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','1','1') from dual ";
	    var arr1=easyExecSql(strSQL);
	   // prompt("",strSQL);
		if(arr1!=null){
			document.all('AppntSumLifeAmnt').value=arr1[0][0];
			tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
		}
		 
		 		 var sqlid11="RnewManuUWSql11";
				var mySql11=new SqlClass();
				mySql11.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql11.setSqlId(sqlid11);//指定使用的Sql的id
				mySql11.addSubPara(document.all('AppntNo').value);//指定传入的参数
			    strSQL=mySql11.getString();	
		 
//		strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','2','1') from dual ";
	    var arr2=easyExecSql(strSQL);
		if(arr2!=null){
			document.all('AppntSumHealthAmnt').value=arr2[0][0];
			tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
		}
		
				var sqlid12="RnewManuUWSql12";
				var mySql12=new SqlClass();
				mySql12.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql12.setSqlId(sqlid12);//指定使用的Sql的id
				mySql12.addSubPara(document.all('AppntNo').value);//指定传入的参数
			    strSQL=mySql12.getString();	
		
//		 strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','3','1') from dual ";
	    var arr3=easyExecSql(strSQL);
		if(arr3!=null){
			document.all('AppntMedicalAmnt').value=arr3[0][0];
			tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
		}
		
				var sqlid13="RnewManuUWSql13";
				var mySql13=new SqlClass();
				mySql13.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql13.setSqlId(sqlid13);//指定使用的Sql的id
				mySql13.addSubPara(document.all('AppntNo').value);//指定传入的参数
			    strSQL=mySql13.getString();	
		
//		strSQL =  "SELECT healthyamnt2('" + document.all('AppntNo').value +"','4','1') from dual ";
	    var arr4=easyExecSql(strSQL);
		if(arr4!=null){
			document.all('AppntAccidentAmnt').value=arr4[0][0];
			tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
		}
		
	    document.all('AppntSumAmnt').value=tSumAmnt;

	   /* 
	    //累计保费 不包括趸交和不定期交保费   
	    strSQL =  "SELECT decode(sum(a_Prem),'','x',sum(a_Prem)) FROM"
	          + "(select (case"
	          + " when s_PayIntv = '1' then"
	          + " s_Prem/0.09"
	          + " when s_PayIntv = '3' then"
	          + " s_Prem/0.27"
	          + " when s_PayIntv = '6' then"
	          + " s_Prem/0.52"
	          + " when s_PayIntv = '12' then"
	          + " s_Prem"
	          + " end) a_Prem"          
	          + " FROM (select distinct payintv as s_PayIntv,"
	          + " sum(prem) as s_Prem"
	          + " from lcpol c where polno in(select polno "
		          + " from lcpol a"
		          + " where a.insuredno = '"+document.all('AppntNo').value+"'"
		          + " or (a.appntno = '"+document.all('AppntNo').value+"' and a.riskcode in ('00115000','00115001'))"
		          + " union"
		          + " select b.polno"
		          + " from lcinsuredrelated b"
		          + " where b.customerno = '"+document.all('AppntNo').value+"')"
	          + " and c.payintv in ('1', '3','6','12')"
	          + " and c.uwflag not in ('1', '2', 'a')"
	          + " and c.appflag <> '4'" 
	          + " and not exists (select 'X'"
		          + " from lccont"
		          + " where ContNo = c.contno"
		          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
	          + " group by payintv))";
	    var arr5=easyExecSql(strSQL);
	     alert("sdajfklajdfaj");
			if(arr5!=null)
			{	
		     alert(arr5[0][0]);  
		   if(arr5[0][0]!='x')  
		     document.all('SumPrem').value=arr5[0][0];
		  }
		  */
   }  

 }
   //初始化被保人信息
  function initLppol(tInsuredNo,tContNo)
 {
     //alert("初始化被保人信息");
	 
	 			var sqlid14="RnewManuUWSql14";
				var mySql14=new SqlClass();
				mySql14.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql14.setSqlId(sqlid14);//指定使用的Sql的id
				mySql14.addSubPara(tContNo);//指定传入的参数
			    var strSql=mySql14.getString();	
	 
//     var strSql = "select insuredno,name,sex,(select max(insuredappage) from lcpol where contno=a.contno and insuredno=a.insuredno), "
//			       + " relationtoappnt,relationtomaininsured,nativeplace from lcinsured a where 1=1 "
//			       + " and contno='" + tContNo + "'"
//			       + " order by a.SequenceNo ";

      brr = easyExecSql(strSql, 1, 0,"","",1);
      if (brr)
      {
          turnPageInsured.queryModal(strSql, PolAddGrid);
          if(PolAddGrid.canSel==1&&PolAddGrid.mulLineCount>1)
					 {
					 			//alert('@@');
					 			document.all('PolAddGridSel')[0].checked=true;
					 			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
					 			getInsuredDetail(); 
					 }
					 if(PolAddGrid.canSel==1&&PolAddGrid.mulLineCount==1)
					 {
					 			//alert('@@');
					 			document.all('PolAddGridSel').checked=true;
					 			//eval("document.all('MultMainRiskGridSel')[" + MultMainRiskGrid.mulLineCount + "-1].checked=true");
					 			getInsuredDetail();
					 }
      }
      else
      {
         alert("被保人信息查询失败！");
         return "";
      }

 }
 /*********************************************************************
 *  MulLine的RadioBox点击事件，获得被保人详细信息，填入被保人信息表
 *  返回值：  无
 *********************************************************************
 */
function getInsuredDetail(parm1,parm2)
{
    var InsuredNo = PolAddGrid.getRowColData(PolAddGrid.getSelNo() - 1, 1);
    //alert("InsuredNo="+InsuredNo);
    var ContNo = fm.ContNo.value;
    var RiskCode = fm.RiskCode.value;
    document.all('InsuredNo').value=InsuredNo;
    tInsuredNo=InsuredNo;
	
		 			var sqlid15="RnewManuUWSql15";
				var mySql15=new SqlClass();
				mySql15.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql15.setSqlId(sqlid15);//指定使用的Sql的id
				mySql15.addSubPara(tContNo);//指定传入的参数
				mySql15.addSubPara(tInsuredNo);//指定传入的参数
			    var strSql=mySql15.getString();	
	
//    var strSql = "select a.Name"
//                  +" ,(select occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode))"
//                  +" ,(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType))"
//                  +" ,a.InsuredNo "
//                      +" from lcinsured a where 1=1"
//                      +" and a.ContNo='"+tContNo+"'"
//                      +" and a.InsuredNo='"+tInsuredNo+"'";

	//prompt('',strSql);
    var brr = easyExecSql(strSql, 1, 0,"","",1);
    if (brr)
    {
        brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredName.value  = brr[0][0];        
        brr[0][1]==null||brr[0][1]=='null'?'0':fm.InsuredOccupationCodeName.value  = brr[0][1];
	    brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredOccupationTypeName.value  = brr[0][2];
        brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredNo.value  = brr[0][3];
    }
    else
    {
		
				 var sqlid16="RnewManuUWSql16";
				var mySql16=new SqlClass();
				mySql16.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql16.setSqlId(sqlid16);//指定使用的Sql的id
				mySql16.addSubPara(tContNo);//指定传入的参数
				mySql16.addSubPara(tInsuredNo);//指定传入的参数
			   strSql=mySql16.getString();	
		
//        strSql = "select a.Name"
//                  +" ,(select occupationname from ldoccupation where trim(occupationcode) = trim(a.OccupationCode))"
//                  +" ,(select codename from ldcode where codetype = 'occupationtype' and trim(code) = trim(a.OccupationType))"
//                  +" ,a.InsuredNo "
//                      +" from lcinsured a where 1=1"
//                      +" and a.ContNo='"+tContNo+"'"
//                      +" and a.InsuredNo='"+tInsuredNo+"'";
		//prompt('',strSql);
        brr = easyExecSql(strSql, 1, 0,"","",1);
        if (brr)
        {
            brr[0][0]==null||brr[0][0]=='null'?'0':fm.InsuredName.value  = brr[0][0];        
	        brr[0][1]==null||brr[0][1]=='null'?'0':fm.InsuredOccupationCodeName.value  = brr[0][1];
		    brr[0][2]==null||brr[0][2]=='null'?'0':fm.InsuredOccupationTypeName.value  = brr[0][2];
	        brr[0][3]==null||brr[0][3]=='null'?'0':fm.InsuredNo.value  = brr[0][3];
        }
        else
        {
           alert("被保人信息查询失败！");
           return "";
        }
    }
    
    //被保人身高体重
	 var nnPrtNo = document.all('PrtNo').value;
	if(nnPrtNo!=null && nnPrtNo!="" && nnPrtNo.length==14 && nnPrtNo.substring(2,4)=="51")//家庭单
	{
	  var tSequenceNo = PolAddGrid.getSelNo();
	  //if(!confirm("tSequenceNo="+tSequenceNo)) return false;
	  if(tSequenceNo == "1")
	  {
	  	
			    var sqlid17="RnewManuUWSql17";
				var mySql17=new SqlClass();
				mySql17.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql17.setSqlId(sqlid17);//指定使用的Sql的id
				mySql17.addSubPara(tContNo);//指定传入的参数
			   var sql =mySql17.getString();	
		
//	  	var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and impartcode = 'D0101'"
//         + " and impartver = 'D01'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+tContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
       //prompt('',sql);
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//查询年收入
		
			    var sqlid18="RnewManuUWSql18";
				var mySql18=new SqlClass();
				mySql18.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql18.setSqlId(sqlid18);//指定使用的Sql的id
				mySql18.addSubPara(tContNo);//指定传入的参数
			   var insql =mySql18.getString();	
		
//	    var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and impartcode = 'D0119'"
//         + " and impartver = 'D02'"
//         + " and impartparamno ='1'"
//         + " and contno = '"+tContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	  }
	  else if(tSequenceNo == "2")
	  {
	  	
				var sqlid19="RnewManuUWSql19";
				var mySql19=new SqlClass();
				mySql19.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql19.setSqlId(sqlid19);//指定使用的Sql的id
				mySql19.addSubPara(tContNo);//指定传入的参数
			   var insql =mySql19.getString();	
		
//	  	var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and impartcode = 'D0101'"
//         + " and impartver = 'D01'"
//         + " and impartparamno in ('3', '4')"
//         + " and contno = '"+tContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//查询年收入
		
			    var sqlid20="RnewManuUWSql20";
				var mySql20=new SqlClass();
				mySql20.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql20.setSqlId(sqlid20);//指定使用的Sql的id
				mySql20.addSubPara(tContNo);//指定传入的参数
			   var insql =mySql20.getString();	
		
//	    var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and impartcode = 'D0119'"
//         + " and impartver = 'D02'"
//         + " and impartparamno ='3'"
//         + " and contno = '"+tContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	  }
	  else if(tSequenceNo == "3")
	  {
	  	
			    var sqlid21="RnewManuUWSql21";
				var mySql21=new SqlClass();
				mySql21.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql21.setSqlId(sqlid21);//指定使用的Sql的id
				mySql21.addSubPara(tContNo);//指定传入的参数
			   var sql =mySql21.getString();	
		
//	  	var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and impartcode = 'D0101'"
//         + " and impartver = 'D01'"
//         + " and impartparamno in ('5', '6')"
//         + " and contno = '"+tContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
	  }
	  
	}
	else
	{
		
				var sqlid22="RnewManuUWSql22";
				var mySql22=new SqlClass();
				mySql22.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql22.setSqlId(sqlid22);//指定使用的Sql的id
				mySql22.addSubPara(tContNo);//指定传入的参数
			   var sql =mySql22.getString();	
		
//		 var sql = "select impartparamname, impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and impartcode = 'A0101'"
//         + " and impartver = 'A01'"
//         + " and impartparamno in ('1', '2')"
//         + " and contno = '"+tContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
       //  prompt('',sql);
	   var arr1 = easyExecSql(sql);
	   if(arr1!=null)
	   {
			document.all('InsuredStature').value=arr1[0][1];
			document.all('InsuredWeight').value=arr1[1][1];
			document.all('InsuredBMI').value=Math.round(parseFloat(arr1[1][1])/parseFloat(arr1[0][1])/parseFloat(arr1[0][1])*10000);
		}
		
		//查询年收入
		
				var sqlid23="RnewManuUWSql23";
				var mySql23=new SqlClass();
				mySql23.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql23.setSqlId(sqlid23);//指定使用的Sql的id
				mySql23.addSubPara(tContNo);//指定传入的参数
			   var insql =mySql23.getString();	
		
//	    var insql = "select impartparam"
//         + " from lccustomerimpartparams"
//         + " where 1 = 1"
//         + " and impartcode = 'A0120'"
//         + " and impartver = 'A02'"
//         + " and impartparamno ='1'"
//         + " and contno = '"+tContNo+"' "
//         + " order by customernotype desc,customerno,impartparamno ";
	    //prompt("",insql);
	    var incomeway = easyExecSql(insql);
	    if(incomeway!=null && incomeway[0][0] !="")
	    {
	       document.all('Insuredincome').value = incomeway[0][0];
	    }	    
	}
	
	//计算被保人风险保额
    var tSumAmnt =0;
	
				var sqlid24="RnewManuUWSql24";
				var mySql24=new SqlClass();
				mySql24.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql24.setSqlId(sqlid24);//指定使用的Sql的id
				mySql24.addSubPara(tInsuredNo);//指定传入的参数
			   var strSQL =mySql24.getString();	
	
//    var strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','1','1') from dual ";   
    var arr1=easyExecSql(strSQL);
   // prompt("",strSQL);
	if(arr1!=null){
		document.all('InsuredSumLifeAmnt').value=arr1[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr1[0][0]);
	}
	 
	 			var sqlid25="RnewManuUWSql25";
				var mySql25=new SqlClass();
				mySql25.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql25.setSqlId(sqlid25);//指定使用的Sql的id
				mySql25.addSubPara(tInsuredNo);//指定传入的参数
			   strSQL =mySql25.getString();	
	 
//	 strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','2','1') from dual ";
    var arr2=easyExecSql(strSQL);
	if(arr2!=null){
		document.all('InsuredSumHealthAmnt').value=arr2[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr2[0][0]);
	}
	
		 		var sqlid26="RnewManuUWSql26";
				var mySql26=new SqlClass();
				mySql26.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql26.setSqlId(sqlid26);//指定使用的Sql的id
				mySql26.addSubPara(tInsuredNo);//指定传入的参数
			   strSQL =mySql26.getString();	
	
//	 strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','3','1') from dual ";
    var arr3=easyExecSql(strSQL);
	if(arr3!=null){
		document.all('InsuredMedicalAmnt').value=arr3[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr3[0][0]);
	}
	
			 	var sqlid27="RnewManuUWSql27";
				var mySql27=new SqlClass();
				mySql27.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql27.setSqlId(sqlid27);//指定使用的Sql的id
				mySql27.addSubPara(tInsuredNo);//指定传入的参数
			   strSQL =mySql27.getString();	
	
//	strSQL =  "SELECT healthyamnt2('" + tInsuredNo +"','4','1') from dual ";
    var arr4=easyExecSql(strSQL);
	if(arr4!=null){
		document.all('InsuredAccidentAmnt').value=arr4[0][0];
		tSumAmnt = tSumAmnt + parseFloat(arr4[0][0]);
	}
	document.all('InsuredSumAmnt').value=tSumAmnt;

	
    //累计保费 不包括趸交和不定期交保费   
	
				 var sqlid28="RnewManuUWSql28";
				var mySql28=new SqlClass();
				mySql28.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql28.setSqlId(sqlid28);//指定使用的Sql的id
				mySql28.addSubPara(tInsuredNo);//指定传入的参数
				mySql28.addSubPara(tInsuredNo);//指定传入的参数
				mySql28.addSubPara(tInsuredNo);//指定传入的参数
				
			   strSQL =mySql28.getString();	
	
//    strSQL =  "SELECT decode(sum(a_Prem),'','x',sum(a_Prem)) FROM"
//          + "(select (case"
//          + " when s_PayIntv = '1' then"
//          + " s_Prem/0.09"
//          + " when s_PayIntv = '3' then"
//          + " s_Prem/0.27"
//          + " when s_PayIntv = '6' then"
//          + " s_Prem/0.52"
//          + " when s_PayIntv = '12' then"
//          + " s_Prem"
//          + " end) a_Prem"          
//          + " FROM (select distinct payintv as s_PayIntv,"
//          + " sum(prem) as s_Prem"
//          + " from lcpol c where polno in(select polno "
//	          + " from lcpol a"
//	          + " where a.insuredno = '"+tInsuredNo+"'"
//	          + " or (a.appntno = '"+tInsuredNo+"' and a.riskcode in ('00115000','00115001'))"
//	          + " union"
//	          + " select b.polno"
//	          + " from lcinsuredrelated b"
//	          + " where b.customerno = '"+tInsuredNo+"')"
//          + " and c.payintv in ('1', '3','6','12')"
//          + " and c.uwflag not in ('1', '2', 'a')"
//          + " and c.appflag <> '4'" 
//          + " and not exists (select 'X'"
//	          + " from lccont"
//	          + " where ContNo = c.contno"
//	          + " and (uwflag in ('1', '2', 'a') or appflag='4' or (state is not null and substr(state,0,4) in ('1002', '1003'))  ))"
//          + " group by payintv))";
    //prompt('',strSQL);
    var arr5=easyExecSql(strSQL);
	if(arr5!=null){	
	       //alert(arr5[0][0]);    
	       if(arr5[0][0]!='x')
	       	  document.all('InsuredSumPrem').value=arr5[0][0];
	    }  
	  /*    
    strSQL ="select a.sequenceno,a.relationtomaininsured,a.name,a.occupationcode,a.occupationtype,b.BlacklistFlag"
           + " from LCInsured a,LDPerson b where a.ContNo = '"+ContNo+"' and a.InsuredNo='"+InsuredNo+"'"
           + "and b.CustomerNo = a.InsuredNo";     
    arrResult=easyExecSql(strSQL);
    if(arrResult!=null)
    {
        DisplayInsured();
    }
    alert("3333333333");
    */
    initPolRisk(ContNo,InsuredNo,fm.RiskCode.value); 
}


function initPolRisk(contno,insuredno,riskcode)
{
	//alert("contno:"+contno);
	
			    var sqlid29="RnewManuUWSql29";
				var mySql29=new SqlClass();
				mySql29.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql29.setSqlId(sqlid29);//指定使用的Sql的id
				mySql29.addSubPara(contno);//指定传入的参数
				mySql29.addSubPara(insuredno);//指定传入的参数
				
			   var strSQL =mySql29.getString();	
	
//	var strSQL = "select riskcode,(select riskname from lmrisk where riskcode=a.riskcode),"
//	         + " amnt,mult,insuyear || a.insuyearflag,payyears, "
//           + " payintv,standprem,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='02' and PayStartDate<=a.paytodate and payenddate>=a.paytodate),0) ,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='01' and PayStartDate<=a.paytodate and payenddate>=a.paytodate),0) ,"
//           + " polno ,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='03' and PayStartDate<=a.paytodate and payenddate>=a.paytodate),0) ,"
//           + " nvl((select sum(prem) from lcprem where polno=a.polno and payplancode like '000000%%' and payplantype='04' and PayStartDate<=a.paytodate and payenddate>=a.paytodate),0) "
//           +", (case when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Terminate' and state = '1'and startdate <= sysdate and enddate is null) then '终止' when exists (select 1 from lccontstate where contno = a.contno and polno = a.polno and statetype = 'Available'and state = '1' and startdate <= sysdate and enddate is null) then '失效' else '有效' end) ,"
//			     +" ( case a.uwflag when '9' then'标准体' when '4' then '次标体' "
//					 +"		when '1' then '拒保' else '其他' end  ) "
//           + " from lcpol a "
//           + " where contno='"+contno+"' "
//           + " and insuredno='"+insuredno+"'  and appflag = '1'  order by a.polno";
   //prompt('',strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);

  //判断是否查询成功
  if (!turnPage.strQueryResult) {
    //alert(turnPage.strQueryResult);0
    alert("没有未通过续保二次核保的个人合同单！");
    return;
  }
	
  //查询成功则拆分字符串，返回二维数组
  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
 
  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
  turnPage.pageDisplayGrid = PolRiskGrid;
  //保存SQL语句
  turnPage.strQuerySql     = strSQL;
  //设置查询起始位置
  turnPage.pageIndex       = 0;
  //在查询结果数组中取出符合页面显示大小设置的数组
  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
  //alert(arrDataSet);
  //调用MULTILINE对象显示查询结果
  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);        
}
 
function DisplayInsured()
{
	//InsuredName/InsuredOccupationCode/InsuredOccupationType/Insuredincome
	  //alert("asdfasfsf");
	  
	  try{document.all('SequenceNoCode').value= arrResult[0][0]; }catch(ex){};
	  try{document.all('RelationToMainInsured').value= arrResult[0][1]; }catch(ex){};
    try{document.all('InsuredName').value= arrResult[0][2]; }catch(ex){};
    try{document.all('InsuredOccupationCode').value= arrResult[0][3]; }catch(ex){};
    try{document.all('InsuredOccupationType').value= arrResult[0][4]; }catch(ex){};
    document.all('InsuredBlacklistFlag').value = arrResult[0][5];
	  quickGetName('blacklistflag',fm.InsuredBlacklistFlag,fm.InsuredBlacklistFlagName);
    quickGetName('occupationCode',fm.InsuredOccupationCode,fm.InsuredOccupationCodeName);
    quickGetName('OccupationType',fm.InsuredOccupationType,fm.InsuredOccupationTypeName);
    quickGetName('SequenceNo',fm.SequenceNoCode,fm.SequenceNoName);
    quickGetName('Relation',fm.RelationToMainInsured,fm.RelationToMainInsuredName);
}

 var cacheWin=null;
 function quickGetName(strCode, showObjc, showObjn) 
 {
 		showOneCodeNameOfObjEx(strCode,showObjc,showObjn);
	}

 /*********************************************************************
 *  进入被保人信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showInsuredInfo()
{

//      var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//        var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//        showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

        var tSelNo = PolAddGrid.getSelNo()-1;
        var tContNo = fm.ContNo.value;
        var tInsuredNo = PolAddGrid.getRowColData(tSelNo,1);
        var tMissionID = fm.MissionID.value;
        var tSubMissionID = fm.SubMissionID.value;
        var tEdorNo = fm.EdorNo.value;

        var tEdorAcceptNo = fm.EdorAcceptNo.value;
        var tOtherNo = fm.OtherNo.value;
        var tOtherNoType = fm.OtherNoType.value;
        var tEdorAppName = fm.EdorAppName.value;
        var tApptype     = fm.Apptype.value;
        var tManageCom   = fm.ManageCom.value;
        var tPrtNo       = fm.PrtNo.value;

        var strTran = "ContNo="+tContNo+"&InsuredNo="+tInsuredNo+"&MissionID="
                               +tMissionID+"&SubMissionID="+tSubMissionID+"&EdorNo="
                               +tEdorNo+"&EdorAcceptNo="+tEdorAcceptNo+"&PrtNo="+tPrtNo+"&OtherNo="
                               +tOtherNo+"&OtherNoType="+tOtherNoType+"&EdorAppName="
                               +tEdorAppName+"&Apptype="+tApptype+"&ManageCom="+tManageCom;
        window.open("./EdorManuUWInsuredMain.jsp?"+strTran, "EdorManuUWInsured", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
//      showInfo.close();
}

 /*********************************************************************
 *  鼠标双击初始化核保结论选项
 *  描述: 初始化核保结论选项
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initUWState(cObjCode, cObjName)
{
	  document.all('UWType').value = "EdorMain";
    var UWType = document.all('UWType').value;
    var UWStateCode;
    
    UWStateCode = "rnewuw";
    initUWCodeData(UWStateCode);
    return showCodeListEx('nothis',[cObjCode,cObjName],[0,1],null,null,null,1);
}

/*********************************************************************
 *  初始化核保结论选项
 *  描述: 初始化核保结论选项
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function onKeyUpUWState(cObjCode, cObjName)
{
    var UWType = document.all('UWType').value;
    var UWStateCode;

    if (UWType == "EdorApp")
    {
        UWStateCode = "edorappuwstate";
    }
    else if (UWType == "EdorMain")
    {
        UWStateCode = "edorcontuw";
    }
    initUWCodeData(UWStateCode);
    return showCodeListKeyEx('nothis',[cObjCode,cObjName],[0,1],null,null,null,1);
}

//初始化核保结论代码，由于要按othersign排序，故写此函数
function initUWCodeData(tCodeType)
{
        var i,j,m,n;
        var returnstr;
        var tTurnPage = new turnPageClass();

//        var strSQL = " select code,codename from ldcode where codetype = '"+tCodeType+"' order by othersign ";
        var sqlid47="RnewManuUWSql47";
		var mySql47=new SqlClass();
		mySql47.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql47.setSqlId(sqlid47);//指定使用的Sql的id
		mySql47.addSubPara(tCodeType);//指定传入的参数
	    var strSQL =mySql47.getString();	
        tTurnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
        if (tTurnPage.strQueryResult == "")
        {
          return "";
        }
        tTurnPage.arrDataCacheSet = decodeEasyQueryResult(tTurnPage.strQueryResult);
        var returnstr = "";
        var n = tTurnPage.arrDataCacheSet.length;
        if (n > 0)
        {
            for( i = 0;i < n; i++)
            {
                m = tTurnPage.arrDataCacheSet[i].length;
                if (m > 0)
                {
                    for( j = 0; j< m; j++)
                    {
                        if (i == 0 && j == 0)
                        {
                            returnstr = "0|^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i == 0 && j > 0)
                        {
                            returnstr = returnstr + "|" + tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j == 0)
                        {
                            returnstr = returnstr+"^"+tTurnPage.arrDataCacheSet[i][j];
                        }
                        if (i > 0 && j > 0)
                        {
                            returnstr = returnstr+"|"+tTurnPage.arrDataCacheSet[i][j];
                        }
                    }
                }
                else
                {
                    alert("查询核保结论描述失败!!");
                    return "";
                }
             }
         }
         else
         {
             alert("查询核保结论描述失败!");
             return "";
         }
         fm.EdorUWState.CodeData = returnstr;
         //return returnstr;
}

function FinishSubmit()
{
	
				var sqlid30="RnewManuUWSql30";
				var mySql30=new SqlClass();
				mySql30.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql30.setSqlId(sqlid30);//指定使用的Sql的id
				mySql30.addSubPara(document.all('ContNo').value);//指定传入的参数
				
			   var check_SQL =mySql30.getString();	
	
//    var check_SQL=" select count(*) from lwmission y where y.activityid='0000007001' and y.missionprop2= '"+tContNo+"' ";
    var check_count=0; 
    check_count = easyExecSql(check_SQL, 1, 1, 1);
    if(check_count>0)
    {
    	alert("还有任务未完成，请继续操作！");
    }
    else
    {
    	alert("续保二核核保成功！");
    	returnParent();
    	top.opener.easyQueryClickSelf();
      top.close();
      top.opener.focus();
    }
    
}

/*********************************************************************
 *  保全核保取消
 *  描述: 保全核保取消
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function UWcancel()
{
    document.all('EdorUWState').value = "";
    document.all('EdorUWStateName').value = "";
    document.all('UWDelay').value = "";
    document.all('UWPopedomCode').value = "";
    document.all('UWIdea').value = "";
}
/*********************************************************************
 *  返回
 *  描述: 页面层显示控制
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function returnParent()
{
    //top.opener.initSelfGrid();
   // top.opener.easyQueryClick();
    top.opener.privateWorkPoolQuery();
    top.close();
    top.opener.focus();
}
/*********************************************************************
 *  保全保单级核保结论提交
 *  描述: 保全保单级核保结论提交
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function UWSubmit()
{
	  if (!beforeUWSubmit())
      return false;
    var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
    //showInfo = window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
    document.all('ActionFlag').value = "UWMANUSUBMIT";
    fm.action = "./RnewManuUWSave.jsp";
    fm.submit();
}
//提交前的校验、计算  
function beforeUWSubmit()
{
   	//判断是否有未回收的通知书，有的话就发送核保通知书    
    if(checkNotice())
    {
    	sendAllNotice();
    	return;
    }
	/*
	//判断是否有未回收的通知书，有的话给出提示
	
				var sqlid31="RnewManuUWSql31";
				var mySql31=new SqlClass();
				mySql31.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql31.setSqlId(sqlid31);//指定使用的Sql的id
				mySql31.addSubPara(tContNo);//指定传入的参数
				mySql31.addSubPara(fm.ProposalNo.value);//指定传入的参数
			   var checksql =mySql31.getString();	
	
//	var checksql=" select 1 from lwmissio a where a.contno='"+tContNo+"' and a.polno='"+fm.ProposalNo.value+"'  "
//      +"   and (a.healthflag in ('1','2') or a.reportflag in ('1') or a.printflag in ('1','2')) "
//       ;

				var sqlid32="RnewManuUWSql32";
				var mySql32=new SqlClass();
				mySql32.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql32.setSqlId(sqlid32);//指定使用的Sql的id
				mySql32.addSubPara(tContNo);//指定传入的参数
				mySql32.addSubPara(fm.MissionID.value);//指定传入的参数
			   var checksql =mySql32.getString();

//  var checksql=" select 1 from lwmission x where  x.activityid='0000007001' and x.missionprop2='"+tContNo+"' and x.MissionID='"+fm.MissionID.value+"' and x.activitystatus='2' and x.missionprop12='3'";
  var check_arr = easyExecSql( checksql );
	if (check_arr) 
	{
	  //添加操作
	  if (confirm("该二核件有未回复的通知书，是否继续下核保结论？") == false)
	  {    
	    return false;	  
	  }
  }*/
  //加费情况下只能下次标体结论
  
  				var sqlid33="RnewManuUWSql33";
				var mySql33=new SqlClass();
				mySql33.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql33.setSqlId(sqlid33);//指定使用的Sql的id
				mySql33.addSubPara(fm.ProposalNo.value);//指定传入的参数
			   var CBT_check =mySql33.getString();
  
//  var CBT_check ="select 1 from lcprem where polno = '"+fm.ProposalNo.value+"' and payplancode like '000000%'";
  var CBT_count = easyExecSql( CBT_check );
	if (CBT_count) 
	{
	  //添加操作
	  if (document.all('EdorUWState').value!='4'&&document.all('EdorUWState').value!='1')
	  {   
	  	alert("该险种有加费，只能下拒保或次标体核保结论，请确认！"); 
	  	document.all('EdorUWState').value="";
	  	document.all('edoruwstateName').value="";
	    return false;	  
	  }
  }
  return true;
}

//校验核保通知书是否需要下发
function checkNotice()
{
	var tContNo = fm.ContNo.value;
	
//    var flowSql = " select 'y' from lbmission a where a.Missionprop2='"+tContNo+"' "
//	       + " and a.Activityid in ('0000007015','0000007012','0000007006','0000007013')"
//	       + " and exists( select 1 from lwmission  b where b.missionid = a.missionid and b.Missionprop2=a.Missionprop2 )";
//	var flowArr = easyExecSql(flowSql);
//	if(flowArr!=null&&flowArr.length>=0)
//	{
//	  return false;
//	}
	
//	var questionSql = " select 'y' from Rnewissuepol a where contno='"+tContNo+"' "
//	       + " and state is null and needprint='Y' and replyresult is null "
//	       + " and ((backobjtype = '2' and prtseq is null) or (backobjtype = '4'  ))";
	var sqlid48="RnewManuUWSql48";
	var mySql48=new SqlClass();
	mySql48.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
	mySql48.setSqlId(sqlid48);//指定使用的Sql的id
	mySql48.addSubPara(tContNo);//指定传入的参数
    var questionSql =mySql48.getString();	
	var questionArr = easyExecSql(questionSql);
	
//	var penoticeSql = "select 'y' from RnewPENotice where  contno = '"+tContNo+"' and printflag is null";
	var sqlid49="RnewManuUWSql49";
	var mySql49=new SqlClass();
	mySql49.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
	mySql49.setSqlId(sqlid49);//指定使用的Sql的id
	mySql49.addSubPara(tContNo);//指定传入的参数
    var penoticeSql =mySql49.getString();	
	var penoticeArr = easyExecSql(penoticeSql);
	
//	var reportSql = "select 'y' from RnewRReport where  contno = '"+tContNo+"' and replyflag is null";
	var sqlid50="RnewManuUWSql50";
	var mySql50=new SqlClass();
	mySql50.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
	mySql50.setSqlId(sqlid50);//指定使用的Sql的id
	mySql50.addSubPara(tContNo);//指定传入的参数
    var reportSql =mySql50.getString();	
	var reportArr = easyExecSql(reportSql);

//	var specSql = "select 'y' from LCCSpec where contno = '"+tContNo+"' and prtflag is null and needprint='Y'";
	var sqlid51="RnewManuUWSql51";
	var mySql51=new SqlClass();
	mySql51.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
	mySql51.setSqlId(sqlid51);//指定使用的Sql的id
	mySql51.addSubPara(tContNo);//指定传入的参数
    var specSql =mySql51.getString();	
	var specArr = easyExecSql(specSql);
	
	//var uwSql = "select 'y' from LCUWMaster where  contno = '"+tContNo+"'  and ChangePolFlag='1' and   and exists(select 1 from lcpol a where a.polno=LCUWMaster.polno )";
	//var uwArr = easyExecSql(uwSql);
//	var addPremSql = "select 'y' from lcprem where contno='"+ tContNo + "' and payplancode like '000000%%' "
//			 + " and exists(select 1 from lcpol a where a.polno=lcprem.polno and a.appflag='9')"
//			 + "and  exists( select 1 from LCUWMaster where  contno = '"+tContNo+"'  and AddPremFlag='1' and polno=lcprem.polno and uwtype='1')";
	var sqlid52="RnewManuUWSql52";
	var mySql52=new SqlClass();
	mySql52.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
	mySql52.setSqlId(sqlid52);//指定使用的Sql的id
	mySql52.addSubPara(tContNo);//指定传入的参数
	mySql52.addSubPara(tContNo);//指定传入的参数
    var addPremSql =mySql52.getString();	
	var addPremArr = easyExecSql(addPremSql);	 
	if((questionArr!=null&&questionArr.length>=0)||(penoticeArr!=null&&penoticeArr.length>=0)||(reportArr!=null&&reportArr.length>=0)||(specArr!=null&&specArr.length>=0)||(addPremArr!=null&&addPremArr.length>=0)){
		return true;
	}
	alert("没有录入问题件、没有承保计划变更、没有特约、没有加费，不能发核保通知书!");
	return false;
}
//发送通知书
function sendAllNotice()
{
  //增加对工作流结点的校验,以及取出最大的活动结点为核保通知书发放的subMissionId
//  var tSQL_SubMissionId = "select (case when max(submissionid) is not null then max(submissionid) else 'x' end) from lwmission where missionid='"+document.all('MissionID').value+"' "
//                           + " and activityid in (select activityid from lwactivity where functionid in ( '10040034','10040054','10047013','10047015','10047001') )";
  var tempSubMissionID = "";
  var sqlid53="RnewManuUWSql53";
	var mySql53=new SqlClass();
	mySql53.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
	mySql53.setSqlId(sqlid53);//指定使用的Sql的id
	mySql53.addSubPara(document.all('MissionID').value);//指定传入的参数
  var questionSql =mySql53.getString();	
  var brr = easyExecSql(tSQL_SubMissionId);
  tempSubMissionID = brr[0][0];
  if(tempSubMissionID == 'x' )
  {
  	//alert(" 存在已发送未回收的核保通知书,不允许再发送核保通知书! ");
  	return false;
  }
  document.all('SubMissionID').value = tempSubMissionID;
  var i = 0;
  var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();
  fm.action ="../uw/RnewSendAllNoticeChk.jsp";
  fm.submit(); //提交 
}
function afterSubmitSendNotice( FlagStr, content )
{
	try{showInfo.close();}catch(ex){}

    alert(content);

}
/*********************************************************************
 *  后台执行完毕反馈信息
 *  描述: 后台执行完毕反馈信息
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterSubmit1(FlagStr, content)
{
    try{showInfo.close();}catch(ex){}

    var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content ;
    //showModalDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
	var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

    if (FlagStr == "Succ" )
    {
        if (document.all('ActionFlag').value == "UWMANUSUBMIT")  //人工核保确认
        {
            var UWType = fm.UWType.value;

            if (UWType == "EdorApp")
            {
                //申请级 返回上级页面
                returnParent();

            }
            else if (UWType == "EdorMain")
            {
                //保单级
                initForm();
                DivDetailInfo.style.display='none';
                divContUWLable.style.display = 'none';
                divFormerContUWInfo.style.display = 'none';//原核保结论同时隐藏
                divAppUWLable.style.display = 'none';
                //showEdorMainList();
            }
            fm.EdorUWState.value = "";
            fm.edoruwstateName.value = "";
            fm.UWIdea.value = "";
        }

        if (document.all('ActionFlag').value == "UWMANUAPPLY")
        {
            top.opener.easyQueryClickSelf();
            //人工核保申请成功后需要重新查询人工核保确认节点的MissionID和SubMissionID
            //查询保全人工核保确认节点
			
			  	var sqlid34="RnewManuUWSql34";
				var mySql34=new SqlClass();
				mySql34.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql34.setSqlId(sqlid34);//指定使用的Sql的id
				mySql34.addSubPara(fm.MissionID.value);//指定传入的参数
			   strSQL=mySql34.getString();
			
//            strSQL =  " select submissionid "
//                    + " from lwmission "
//                    + " where activityid = '0000000006' "
//                    + " and missionid = '" + fm.MissionID.value + "'";
            var brr = easyExecSql(strSQL, 1, 0,"","",1);

            if ( brr )
            {
                brr[0][0]==null||brr[0][0]=='null'?'0':fm.SubMissionID.value = brr[0][0];
            }

        }
    }

    if (FlagStr == "Fail" )
    {
        if (document.all('ActionFlag').value == "UWMANUAPPLY")
        {
            //人工核保申请
            divDetailInfo.style.display='none';
            divEdorAppUWResultInfo.style.display='none';

        }
        if (document.all('ActionFlag').value == "UWMANUSUBMIT")
        {
            //人工核保确认
            var UWType = fm.UWType.value;

            if (UWType == "EdorMain")
            {

            }
        }
    }

}

//调用新契约创建节点返回接函数
function afterSubmit( FlagStr, content )
{
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + content;
  if (FlagStr == "Fail" )
  {
    alert(content);
  }
  else
  {
  }
}


/*********************************************************************
 *  选择核保结后的动作
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function afterCodeSelect( cCodeName, Field ) 
{

	  //alert("uwstate" + cCodeName + Field.value);
		if( cCodeName = "EdorUWState" ) 
		{
			//alert("111111");
			if(Field=1)
       {
          //alert("22222222");
          //divUwDelayTitle.style.display = '';
         // divUwDelay.style.display = '';            
       }
		}
}

/*************按钮功能区域*******Begin***************Added by liurx************2005-06-22******************/

//投保人健康告知查询
function queryHealthImpart()
{

    window.open("./RnewHealthImpartQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");

}

//被保人健康告知查询
function queryInsuredHealthImpart(){

	window.open("../uw/HealthImpartQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1",sFeatures);
}

//既往理赔查询
function queryClaim()
{
    window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+document.all('InsuredNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

//投保人保额累计信息
function amntAccumulate()
{
    window.open("../uw/AmntAccumulateMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

//投保人已承保保单查询
function queryProposal()
{

    window.open("../uw/ProposalQueryMain.jsp?CustomerNo="+fm.AppntNo.value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//投保人未承保保单查询
function queryNotProposal(){

    window.open("../uw/NotProposalQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//投保人既往保全查询
function queryEdor()
{
    //window.open("../uw/EdorQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
    var CustomerNo = fm.AppntNo.value;
    var EdorAcceptNo = fm.EdorAcceptNo.value;
    var varSrc = "CustomerNo="+CustomerNo+"&EdorAcceptNo="+EdorAcceptNo;
    window.open("../bq/EdorAgoQueryMain.jsp?"+varSrc,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
/*//投保人既往理赔查询
function queryClaim(){

    window.open("../uw/ClaimQueryMain.jsp?CustomerNo="+document.all('AppntNo').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}*/

//扫描件查询
function ScanDetail()
{
     var EdorAcceptNo    = document.all('EdorAcceptNo').value;
     var MissionID       = document.all('MissionID').value;
     var SubMissionID    = document.all('SubMissionID').value;
	 
	 			var sqlid35="RnewManuUWSql35";
				var mySql35=new SqlClass();
				mySql35.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql35.setSqlId(sqlid35);//指定使用的Sql的id
				mySql35.addSubPara(EdorAcceptNo);//指定传入的参数
			    var str=mySql35.getString();
	 
//     var str = "select docid from es_doc_relation where bussno = '" + EdorAcceptNo + "' and busstype = 'BQ' and relaflag = '0'";
     var arrResult = easyExecSql(str, 1, 0,"","",1);
     if(arrResult == null){
          alert("此次保全申请没有相关扫描影像资料！");
          return;
     }
     var PrtNo = arrResult[0][0];
	 
	 	 		var sqlid36="RnewManuUWSql36";
				var mySql36=new SqlClass();
				mySql36.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql36.setSqlId(sqlid36);//指定使用的Sql的id
				mySql36.addSubPara(EdorAcceptNo);//指定传入的参数
			    var str36=mySql36.getString();
	 
	 var tResult = easyExecSql(str36, 1, 0,"","",1);
     //var tResult = easyExecSql("select a.codealias from ldcode a,es_doc_relation b where a.codetype = 'bqscan' and trim(a.code) = trim(b.subtype) and b.busstype = 'BQ' and b.bussno = '"+EdorAcceptNo+"'", 1, 0,"","",1);
     if(tResult == null){
          alert("查询保全扫描子业务类型编码失败！");
          return;
     }
     var varSrc = "ScanFlag = 1&prtNo=" + PrtNo + "&EdorAcceptNo=" + EdorAcceptNo + "&MissionID=" + MissionID + "&SubMissionID=" + SubMissionID + "&SubType=" + tResult[0][0];
     var newWindow = OpenWindowNew("../bq/EdorScan.jsp?" + varSrc,"保全扫描影像","left");
}
//当前核保记录
function showNewUWSub()
{
//  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
//  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
//  showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");

  tContNo=fm.ContNo.value;
  tEdorNo=fm.EdorNo.value;

  window.open("./EdorUWErrMain.jsp?ContNo="+tContNo+"&EdorNo="+tEdorNo,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");

//  showInfo.close();

}
//   投保人体检资料查询
function showBeforeHealthQ()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cProposalContNo=fm.ProposalContNo.value;
  var cAppntNo = fm.AppntNo.value;

//alert(800);
  window.open("../uw/BQBeforeHealthQMain.jsp?ContNo="+cProposalContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);

  showInfo.close();

}

/*********************************************************************
 *  查看记事本
 *  描述: 查看记事本
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function showNotePad()
{
  var cContNo = fm.ContNo.value;
  var cPrtNo = fm.PrtNo.value;

  if (cContNo!="" && cPrtNo!="") {
    window.open("../uw/RnewNotePadMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo+"&OperatePos=3", "window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
  else {
    alert("请先选择保单!");
  }
}
//体检通知录入
function showHealth()
{

  var pContNo = fm.ContNo.value;
  //var pEdorNo=fm.EdorNo.value;
  //var pEdorAcceptNo = fm.EdorAcceptNo.value;

  var pMissionID = fm.MissionID.value;
  var pSubMissionID = fm.SubMissionID.value;
  var pPrtNo = fm.PrtNo.value;
  var pFlag = "1";

  //var varSrc = "EdorAcceptNo="+pEdorAcceptNo+"&EdorNo="+pEdorNo+"&ContNo="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="+pSubMissionID+"&PrtNo="+pPrtNo;
  //window.open("./EdorAppMUHealthMain.jsp?"+varSrc,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  //window.open("../uw/UWManuHealthMain.jsp?ContNo2="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="+pSubMissionID+"&PrtNo="+pPrtNo,"window1");

  if (pContNo != "")
  {
    window.open("../uw/RnewManuHealthMain.jsp?ContNo1="+pContNo+"&MissionID="+pMissionID+"&SubMissionID="+pSubMissionID+"&PrtNo="+pPrtNo+"&Flag="+pFlag, "window1",sFeatures);
    //showInfo.close();
  }
  else
  {
    showInfo.close();
    alert("请先选择保单!");
  }

}
//核保查询
function UWQuery()
{
    var pContNo = fm.ContNo.value;
    var pEdorNo=fm.EdorNo.value;
    window.open("./EdorUWQueryMain.jsp?ContNo="+pContNo+"&EdorNo="+pEdorNo,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}
//查询体检结果
function queryHealthReportResult()
{
    var tContNo = fm.ContNo.value;
    var tFlag = "1";
window.open("../uw/RnewManuHealthQMain.jsp?ContNo="+tContNo+"&Flag="+tFlag,"window1",sFeatures);
}


function checkUWState(tState,tMissionID,tSubMissionID,tMessage)
{
	
		 	 	var sqlid37="RnewManuUWSql37";
				var mySql37=new SqlClass();
				mySql37.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql37.setSqlId(sqlid37);//指定使用的Sql的id
				mySql37.addSubPara(tMissionID);//指定传入的参数
				mySql37.addSubPara(tSubMissionID);//指定传入的参数
				mySql37.addSubPara(tState);//指定传入的参数
			    var tSQL=mySql37.getString();
	
//	var tSQL = "select '1' from lwmission where missionid = '"+tMissionID+"' "
//	         + " and SubMissionID = '"+tSubMissionID+"' "
//	         + " and MissionProp12='"+tState+"'"
//	         + " and activityid='0000007001' ";
	var arr = easyExecSql( tSQL );
	 if (arr) 
   {
         alert("该合同当前处于"+tState+"状态,不允许"+tMessage);   
         return false;   
   }
	 return true;
} 

//查询生调结果
function queryRReportResult()
{

    var tContNo = fm.ContNo.value;
    var tEdorNo = fm.EdorNo.value;
    var tFlag = "1";

    window.open("../uw/RnewReportQueryMain.jsp?ContNo="+tContNo+"&EdorNo="+tEdorNo+"&Flag="+tFlag,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");

}

//操作履历查询
function QueryRecord(){

    window.open("../uw/RnewRecordQueryMain.jsp?ContNo="+document.all('ContNo').value+"&MissionID="+document.all('MissionID').value,"window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
}

//客户品质管理
function CustomerQuality() {
  ContNo = document.all('ContNo').value;
  if (ContNo!="") {
    window.open("../uw/UWCustomerQualityMain.jsp?ContNo="+ContNo, "window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
}

//业务员品质管理
function AgentQuality() {
  ContNo = document.all('ContNo').value;
  if (ContNo!="") {
    window.open("../uw/UWAgentQualityMain.jsp?ContNo="+ContNo, "window1", "top=0,left=0,toolbar=0,location=0,directories=0,menubar=0,scrollbars=1,resizable=1,status=0");
  }
}

//体检医院品质管理
function HospitalQuality() {
	ContNo = document.all('ContNo').value;
	PrtNo = document.all('PrtNo').value;
  if (ContNo!="" && PrtNo!="") {
  	window.open("../uw/RnewHospitalQualityMain.jsp?ContNo="+ContNo+"&PrtNo="+PrtNo, "window1",sFeatures);
  }
}

function IssueMistake(){
	var PrtNo = document.all.PrtNo.value;
	var NoType = "1";
	if(PrtNo == null || PrtNo == "")
	{
		alert("投保单号为空！");
		return;
	}

	var varSrc= "?PrtNo="+ PrtNo ;
	var newWindow = OpenWindowNew("../uw/MSQuestMistakeMain.jsp?Interface=../uw/MSQuestMistakeInput.jsp" + varSrc,"问题件差错记录","left");
	
}

//保全明细查询
function EdorDetailQuery()
{

    var tEdorAcceptNo = fm.EdorAcceptNo.value;
    var tOtherNoType  = fm.OtherNoType.value;

    var varSrc="&EdorAcceptNo=" + tEdorAcceptNo+"&OtherNoType="+tOtherNoType;
    var newWindow = OpenWindowNew("../sys/BqDetailQueryFrame.jsp?Interface= ../sys/BqDetailQuery.jsp" + varSrc,"保全查询明细","left");

}

function SendAllNotice(){	//alert("fm.SubMissionID.value=="+fm.SubMissionID.value);

		 	 	var sqlid38="RnewManuUWSql38";
				var mySql38=new SqlClass();
				mySql38.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql38.setSqlId(sqlid38);//指定使用的Sql的id
				mySql38.addSubPara( document.all('EdorNo').value);//指定传入的参数
			    var str6=mySql38.getString();

//    var str6 = "select edortype from lpedoritem where edorno = '"+ document.all('EdorNo').value +"'";
    var tEdorType = easyExecSql(str6, 1, 0,"","",1);
    window.open("../uw/BQSendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+fm.MissionID.value+"&SubMissionID="+fm.SubMissionID.value+"&EdorNo="+fm.EdorNo.value+"&EdorType="+tEdorType[0][0],"window1",sFeatures);
}


//<!-- XinYQ added on 2005-11-01 : 保全核保影像查询 : BGN -->
function UWScanQuery()
{
      var ContNo = document.getElementsByName("ContNo")[0].value;
      if (ContNo == "" || ContNo == null)
        return;
      window.open("../uw/EdorUWImageQueryMain.jsp?ContNo=" + ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}
//<!-- XinYQ added on 2005-11-01 : 保全核保影像查询 : END -->


//投保单影像查询
function ScanQuery()
{
      var ContNo = fm.ProposalContNo.value;
      if (ContNo == "")
        return;
      window.open("../uw/ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);
}

//保单信息查询
function ContInfoQuery()
{
    var tContNo = fm.ContNo.value;
    if(tContNo == null || tContNo == "")
    {
        return;
    }
    window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + tContNo +"&IsCancelPolFlag=0","",sFeatures);
}
//保全操作轨迹
function MissionQuery()
{
    var pMissionID=fm.MissionID.value;
    window.open("./EdorMissionFrame.jsp?MissionID="+pMissionID,"window3",sFeatures);
}
/*********************************************************************
 *  显示原保单级核保结论
 *  描述: 显示原核保结论
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function initFormerUW(tContNo,tEdorNo)
{
	
			 	var sqlid39="RnewManuUWSql39";
				var mySql39=new SqlClass();
				mySql39.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql39.setSqlId(sqlid39);//指定使用的Sql的id
				mySql39.addSubPara(tContNo);//指定传入的参数
				mySql39.addSubPara(tEdorNo);//指定传入的参数
			    var strSql=mySql39.getString();
	
//    var strSql = " select passflag,(select codename from ldcode where codetype = 'contuwstate' and code = passflag),uwidea "
//               + " from LPCUWMaster where contno='"+tContNo+"' and edorno='"+tEdorNo+"'";
    var brr=easyExecSql(strSql, 1, 0,"","",1);//prompt("",strSql);
    if(brr)
    {
        fm.FormerUWState.value = brr[0][0];
        fm.FormerUWStateName.value = brr[0][1];
        fm.FormerUWIdea.value = brr[0][2];
        divFormerContUWInfo.style.display = '';
    }
    else
    {
        fm.FormerUWState.value = "";
        fm.FormerUWStateName.value = "";
        fm.FormerUWIdea.value = "";
        divFormerContUWInfo.style.display = 'none';
    }
}

/*********************************************************************
 *  生成工作流后续结点
 *  参数  ：  无
 *  返回值：  无
 *********************************************************************
 */
function makeWorkFlow(tContNo)
{
    fm.action = "../uw/RnewUWChk.jsp?ContNo="+tContNo+"&MissionID="+document.all('MissionID').value+"&SubMissionID="+document.all('SubMissionID').value;
    fm.submit();
}

//既往投保信息
function showApp(cindex)
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cAppntNo = fm.AppntNo.value;

 if (cindex == 1)
	  window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+cAppntNo+"&type=1","",sFeatures);
 else  if (cindex == 2)
 	 window.open("../uw/UWAppMain.jsp?ContNo="+cContNo+"&CustomerNo="+document.all('InsuredNo').value+"&type=2","",sFeatures);
 else
	  window.open("../uw/UWAppFamilyMain.jsp?ContNo="+cContNo,"",sFeatures);

showInfo.close();

}

//特约承保
function showSpec()
{
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;
  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var tSelNo = PolAddGrid.getSelNo()-1;
  tUWIdea = document.all('UWIdea').value;
  var cPrtNo = PolAddGrid.getRowColData(tSelNo,3);
  var cInsuredNo=fm.InsuredNo.value;
  if (cContNo != ""&& cPrtNo !="" && cMissionID != "" )
  {
  	window.open("../uw/UWManuSpecMain.jsp?ContNo="+cContNo+"&PrtNo="+cPrtNo+"&InsuredNo="+cInsuredNo+"&MissionID="+cMissionID+"&SubMissionID="+cSubMissionID,"window1",sFeatures);
  	showInfo.close();
  }
  else
  {
  	showInfo.close();
  	alert("请先选择保单!");
  }
}


//承保结论变更录入
//需要校验选择了被保人之后才能进入
function NewChangeRiskPlan()
{
    var strSql="";
    //strSql="select salechnl from lccont where contno='"+tContNo+"'";
    
				 var sqlid40="RnewManuUWSql40";
				var mySql40=new SqlClass();
				mySql40.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql40.setSqlId(sqlid40);//指定使用的Sql的id
				mySql40.addSubPara(tContNo);//指定传入的参数
			   strSql=mySql40.getString();
	
//    strSql = "select case lmriskapp.riskprop"
//            +" when 'I' then '1'"
//	        +" when 'G' then '2'"
//	        +" when 'Y' then '3'"
//	        +" when 'T' then '5'"
//           + " end"
//           + " from lmriskapp"
//           + " where riskcode in (select riskcode"
//           + " from lcpol"
//           + " where polno = mainpolno"
//           + " and contno = '"+tContNo+"')"    
           
    arrResult = easyExecSql(strSql);
    if(arrResult==null){
		
				var sqlid41="RnewManuUWSql41";
				var mySql41=new SqlClass();
				mySql41.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql41.setSqlId(sqlid41);//指定使用的Sql的id
				mySql41.addSubPara(tContNo);//指定传入的参数
				mySql41.addSubPara(tContNo);//指定传入的参数
			   strSql=mySql41.getString();
		
//        strSql = " select * from ("
//               + " select case missionprop5"
//               + " when '05' then '3'"
//               + " when '12' then '3'"
//               + " when '13' then '5'"
//               + " else '1'"
//               + " end"
//               + " from lbmission"
//               + " where missionprop1 = '"+tContNo+"'"
//               + " and activityid = '0000001099'"
//               + " union"
//               + " select case missionprop5"
//               + " when 'TB05' then '3'"
//               + " when 'TB12' then '3'"
//               + " when 'TB06' then '5'"
//               + " else '1'"
//               + " end"
//               + " from lbmission"
//               + " where missionprop1 = '"+tContNo+"'"
//               + " and activityid = '0000001098'"
//               + ") where rownum=1";
        arrResult = easyExecSql(strSql);               
    }
    var BankFlag = arrResult[0][0];
    //alert("BankFlag="+arrResult[0][0]); 
    
    
					var sqlid42="RnewManuUWSql42";
				var mySql42=new SqlClass();
				mySql42.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql42.setSqlId(sqlid42);//指定使用的Sql的id
				mySql42.addSubPara(tContNo);//指定传入的参数
			    var strSql2=mySql42.getString();
	
//    var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1=(select distinct(prtno) from lcpol where contno='"+tContNo+"')";
    var crrResult = easyExecSql(strSql2);
    var SubType="";
    if(crrResult!=null){
      SubType = crrResult[0][0];
    }
     
     var NoType = "1";
	var InsuredNo = document.all('InsuredNo').value;
	window.open("../uw/RnewUWChangeRiskPlanMain.jsp?ContNo="+tContNo+"&PrtNo="+tPrtNo+"&MissionID="+document.all('MissionID').value+"&SubMissionID="+document.all('SubMissionID').value+"&InsuredNo="+document.all('InsuredNo').value+"&InsuredSumLifeAmnt="+fm.InsuredSumLifeAmnt.value+"&InsuredSumHealthAmnt="+fm.InsuredSumHealthAmnt.value+"&NoType="+NoType+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3","window1",sFeatures);  	

}


//tongmeng 2007-11-01 add
//增加MS发放核保通知书的功能
function SendAllNotice_MS()
{
	  if(!checkAutoUWPass('发送核保通知书'))
	  {
	  	return false;
	  }
	  if(!checkUWState('6',document.all('MissionID').value,document.all('SubMissionID').value,'发送核保通知书'))
	 	{
	 	 return false;
	 	}
	 	 if(!checkUWState('4',document.all('MissionID').value,document.all('SubMissionID').value,'发送核保通知书'))
	 	{
	 	 return false;
	 	}
	 if(!checkUWOperate(document.all('MissionID').value,document.all('SubMissionID').value,'发送核保通知书'))
	 	{
	 	 return false;
	 	}
//	 	var sql_sqnms = "select * from  Lwmission m where m.Missionprop2 = '"+fm.ContNo.value+"'";
	 	var sqlid54="RnewManuUWSql54";
		var mySql54=new SqlClass();
		mySql54.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
		mySql54.setSqlId(sqlid54);//指定使用的Sql的id
		mySql54.addSubPara(fm.ContNo.value);//指定传入的参数
	    var sql_sqnms =mySql54.getString();	
	 	var arr1 = easyExecSql(sql_sqnms);
	  	 if(arr1!=null)
	   {
			document.all('MissionID').value=arr1[0][0];
			document.all('SubMissionID').value=arr1[0][1];
		}
		window.open("../uw/RnewSendAllNoticeMain.jsp?ContNo="+fm.ContNo.value+"&MissionID="+document.all('MissionID').value+"&SubMissionID="+document.all('SubMissionID').value,"window1",sFeatures);

}


/**
* 校验自动核保是否都审阅完毕.
*/
function checkAutoUWPass(tMessage)
{
	//
	
						var sqlid43="RnewManuUWSql43";
				var mySql43=new SqlClass();
				mySql43.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql43.setSqlId(sqlid43);//指定使用的Sql的id
				mySql43.addSubPara(document.all('RiskCode').value);//指定传入的参数
				mySql43.addSubPara(document.all('ContNo').value);//指定传入的参数
			    var tSQL=mySql43.getString();
	
//	var tSQL = "select '1'  from LCUWError a,lcpol b where a.polno=b.polno and b.riskcode='"+document.all('RiskCode').value+"' "
//	+" and b.appflag='9' and a.contno='"+fm.ContNo.value+"' and (sugpassflag is null or sugpassflag='N') ";
	
	var arr = easyExecSql( tSQL );
	//alert(arr);
   if (arr) 
    {
         alert("还有自动核保信息没有审阅完毕, 不允许"+tMessage)   
         return false;   
    }
    /*
  var tSQL2 = "select '1' from RnewIndUWError where contno='"+fm.ContNo.value+"' and (sugpassflag is null or sugpassflag='N') ";
	var arr2 = easyExecSql( tSQL2 );
   if (arr2) 
    {
         alert("还有自动核保信息没有审阅完毕, 不允许"+tMessage)   
         return false;   
    }
    */
    return true;
}


//校验核保员是否有操作权限
function checkUWOperate(tMissionID,tSubMissionID,tMessage)
{
	
				var sqlid44="RnewManuUWSql44";
				var mySql44=new SqlClass();
				mySql44.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql44.setSqlId(sqlid44);//指定使用的Sql的id
				mySql44.addSubPara(tMissionID);//指定传入的参数
				mySql44.addSubPara(tSubMissionID);//指定传入的参数
				mySql44.addSubPara(operator);//指定传入的参数
			    var tSQL=mySql44.getString();
	
//	var tSQL = "select '1' from lwmission where missionid = '"+tMissionID+"' "
//	         + " and SubMissionID = '"+tSubMissionID+"' "
//	         + " and activityid='0000007001' "
//	         + " and exists (select 1 from lduwuser where usercode='"+ operator 
//	         	+ "' and uwtype='1' and uwpopedom<MissionProp12 and (UWProcessFlag is null or UWProcessFlag='N')) " ;
	//prompt('',tSQL);         
	var arr = easyExecSql( tSQL );
	 if (arr) 
   {
         alert("当前核保员核保级别不够，不允许"+tMessage+"！");   
         return false;   
   }
	 return true;
}



//生存调查报告
function showRReport()
{
	if(!checkUWState('6',document.all('MissionID').value,document.all('SubMissionID').value,'生存调查申请录入'))
	{
	 	 return false;
	}
  var showStr="正在查询数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");
  var name='提示';   //网页名称，可为空; 
	var iWidth=550;      //弹出窗口的宽度; 
	var iHeight=250;     //弹出窗口的高度; 
	var iTop = (window.screen.availHeight - iHeight) / 2; //获得窗口的垂直位置 
	var iLeft = (window.screen.availWidth - iWidth) / 2;  //获得窗口的水平位置 
	showInfo = window.open (urlStr,name, "status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no,height="+ iHeight+",width="+iWidth+",innerHeight="+iHeight+",innerWidth=" +iWidth+",left="+iLeft+",top="+iTop,false);

	showInfo.focus();

  var cContNo=fm.ContNo.value;

  var cMissionID =fm.MissionID.value;
  var cSubMissionID =fm.SubMissionID.value;
  var cPrtNo = fm.PrtNo.value;

  window.open("../uw/RnewManuRReportMain.jsp?ContNo="+cContNo+"&MissionID="+cMissionID+"&PrtNo="+cPrtNo+"&SubMissionID="+cSubMissionID+"&Flag="+pflag,"window2",sFeatures);
  showInfo.close();
}

//核保分析报告
function showUWReport()
{
	window.open("../uw/RnewReportMain.jsp?ProposalNo="+fm.ProposalNo.value+"&NoType=1","window5",sFeatures);
}

//保单明细信息
function showPolDetail()
{

  var cContNo = fm.ProposalContNo.value;
  var cPrtNo = fm.PrtNo.value;


	//去掉原来的判断投保单类型的逻辑，修改为按印刷号来判断投保单类型
	var BankFlag = "";
    var tSplitPrtNo = cPrtNo.substring(0,4);
    //alert("tSplitPrtNo=="+tSplitPrtNo); 
    //8635、8625、8615走银代投保单界面，其余的都走个险界面
    if(tSplitPrtNo=="8635"||tSplitPrtNo=="8625"||tSplitPrtNo=="8615"){
    	BankFlag="3";
    }else{
    	BankFlag="1";
    }

  				var sqlid45="RnewManuUWSql45";
				var mySql45=new SqlClass();
				mySql45.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql45.setSqlId(sqlid45);//指定使用的Sql的id
				mySql45.addSubPara(cPrtNo);//指定传入的参数
			    var strSql2=mySql45.getString();
  
//  var strSql2="select missionprop5 from lbmission where activityid='0000001099' and missionprop1='"+cPrtNo+"'";
	var crrResult = easyExecSql(strSql2);
	var SubType="";
	if(crrResult!=null)
	{
		SubType = crrResult[0][0];
	}
  window.open("../app/ProposalEasyScan.jsp?LoadFlag=6&prtNo="+cPrtNo+"&ContNo="+cContNo+"&BankFlag="+BankFlag+"&SubType="+SubType+"&QueryType=3", "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}
//保单信息查询
function ContInfoQuery()
{
    var tContNo = fm.ContNo.value;
    if(tContNo == null || tContNo == "")
    {
        return;
    }
    window.open("../sys/PolDetailQueryMain.jsp?ContNo=" + tContNo +"&IsCancelPolFlag=0","",sFeatures);
}

//扫描件查询
function ScanQuery()
{

	  var ContNo = fm.ContNo.value;
		if (ContNo == "")
		    return;
		  window.open("./ImageQueryMain.jsp?ContNo="+ContNo, "", "status:no;help:0;close:0;dialogTop:-800;dialogLeft:-800;fullscreen=1;"+sFeatures);

}




//人工核保增加问题件录入
//问题件录入
function QuestInput()
{
    cContNo = fm.ContNo.value;  //保单号码
    var ActivityID='0000007001';
   if(!checkUWState('6',document.all('MissionID').value,document.all('SubMissionID').value,'问题件录入'))
	 {
	 	 return false;
	 }
   
  window.open("../uw/RnewQuestInputMain.jsp?ContNo="+cContNo+"&Flag=6&MissionID="+document.all('MissionID').value+"&SubMissionID="+document.all('SubMissionID').value+"&ActivityID="+ActivityID,"window1",sFeatures);


}


//问题件查询
function QuestQuery()
{
	cContNo = fm.ContNo.value;  //保单号码


  if (cContNo != "")
  {
  	var tSelNo = PolAddGrid.getSelNo()-1;
  	QuestQueryFlag[tSelNo] = 1 ;
	window.open("../uw/RnewQuestQueryMain.jsp?ContNo="+cContNo+"&Flag="+cflag,"window2",sFeatures);
  }
  else
  {
  	alert("请先选择保单!");
  }
}



// 查询按钮
function easyQueryClickUW()
{
	// 初始化表格
	initUWErrGrid();
	//alert("1111111111111");
	// 书写SQL语句
	var strSQL = "";
	var ContNo = document.all('ContNo').value;
	var RiskCode =document.all('RiskCode').value;
	
	//var PolNo = document.all('PolNo').value;
	
//合同号,被保人,险种编码,险种名称,核保信息,修改时间,是否审阅,投保单号,流水号,核保序列号,合同险种标记,投保单号

  				var sqlid46="RnewManuUWSql46";
				var mySql46=new SqlClass();
				mySql46.setResourceName("xb.RnewManuUWSql"); //指定使用的properties文件名
				mySql46.setSqlId(sqlid46);//指定使用的Sql的id
				mySql46.addSubPara(ContNo);//指定传入的参数
				mySql46.addSubPara(RiskCode);//指定传入的参数
				mySql46.addSubPara(ContNo);//指定传入的参数
			    strSQL=mySql46.getString();

//	strSQL = "select A.contno,A.insuredno,A.riskcode,A.riskname,A.uwerror,A.modifydate,A.passname, "
//	       + " A.x,A.serialno,A.uwno,A.flag,A.proposalno from ( "
//         + " select a.contno,b.insuredno,b.riskcode, "
//         + " (select riskname from lmriskapp where riskcode=b.riskcode) riskname,a.uwerror, "
//         + " to_char(a.modifydate,'yyyy-mm-dd')||' '||a.modifytime modifydate, "
//         + " (select codename from ldcode where codetype='autouwpassflag' and code=nvl(a.sugpassflag,'N')) passname, "
//         + " a.proposalno x,a.serialno,a.uwno,'risk' flag,b.proposalno proposalno "
//	       + " from LCUWError a,lcpol b "
//         + " where a.polno=b.polno "
//	       + " and b.contno='"+ContNo+"' and b.riskcode='"+RiskCode+"' and b.appflag='9' "
//				+" and (a.uwno = (select c.batchno "
//				+" from LCUWMaster c where c.ContNo = '"+ContNo+"' "
//				+ " and c.PolNo = a.PolNo)) "
//         + " ) A order by A.contno,A.insuredno,A.proposalno,A.riskcode,A.modifydate";
	
	//prompt("",strSQL);
   turnPage.strQueryResult  = easyQueryVer3(strSQL, 1, 0, 1);
  //判断是否查询成功
  if (turnPage.strQueryResult) 
  {
  	  //alert("12232`4`4");
      //alert(turnPage.strQueryResult);
		  //查询成功则拆分字符串，返回二维数组
		  turnPage.arrDataCacheSet = decodeEasyQueryResult(turnPage.strQueryResult);
		 
		  //设置初始化过的MULTILINE对象，HealthGrid为在初始化页中定义的全局变量
		  turnPage.pageDisplayGrid = UWErrGrid;
		  //保存SQL语句
		  turnPage.strQuerySql     = strSQL;
		  //设置查询起始位置
		  turnPage.pageIndex       = 0;
		  //在查询结果数组中取出符合页面显示大小设置的数组
		  var arrDataSet   = turnPage.getData(turnPage.arrDataCacheSet, turnPage.pageIndex, MAXSCREENLINES);
		  //alert(arrDataSet);
		  //调用MULTILINE对象显示查询结果
		  displayMultiline(arrDataSet, turnPage.pageDisplayGrid);     
  }
  return true;

}



//自动核保审阅按钮提交函数
function submitFormUW()
{
  var i = 0;
  //var showStr="正在保存数据，请您稍候并且不要修改屏幕上的值或链接其他页面";
  //var urlStr="../common/jsp/MessagePage.jsp?picture=C&content=" + showStr ;  
  fm.action="../uw/RnewUWErrSave.jsp";
  fm.AutoUWButton.disabled=true;
  //alert("3124134");
  if(!checkChk())
	{
		fm.AutoUWButton.disabled=false;
		return false;
	}
  
  //showInfo=window.showModelessDialog(urlStr,window,"status:no;help:0;close:0;dialogWidth:550px;dialogHeight:250px");   
	//校验是否选择了数据.
	
  //showSubmitFrame(mDebug);
  fm.submit(); //提交
  //alert("submit");
}


function checkChk()
{
	var tCount = 0;
	var i;
	for(i=0;i<UWErrGrid.mulLineCount;i++)
	{
		  var t =UWErrGrid.getChkNo(i);
		  //alert(t);
			if(t)
			{
				tCount++;
			}
	}
	if(tCount<=0)
	{
		alert("请选择需要审阅的核保信息!");
		return false;
	}
	return true;
}

function releaseAutoUWButton()
{
	//fm.action='';
	easyQueryClickUW();
	fm.AutoUWButton.disabled=false;
}