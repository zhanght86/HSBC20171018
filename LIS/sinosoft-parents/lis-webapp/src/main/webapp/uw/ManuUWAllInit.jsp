<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//程序名称：ManuUWAllInit.jsp
//程序功能：个人人工核保
//创建日期：2005-01-29 11:10:36
//创建人  ：HYQ
//更新记录：  更新人    更新日期     更新原因/内容
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--用户校验类-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//添加页面控件的初始化。
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) 
	{
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// 输入框的初始化（单记录部分）
function initInpBox()
{ 

  try
  {            
  	if(operFlag == "1")
  	{
  		//fm.ApplyNo.value = "5";    
  		fm.ApplyType.value = "1";  				//个单申请
  		//fm.ActivityID.value = "0000001110"; 
  	}
  	if(operFlag == "2")
  	{
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "2";    		  //团单申请
  		fm.ActivityID.value = "0000002004"; 
  	}  	      
  	if(operFlag == "3")
  	{
 
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "3";    			//询价申请
  		fm.ActivityID.value = "0000006004"; 
  	}            
  }
  catch(ex)
  {
    alert("在UWInit.jsp-->InitInpBox函数中发生异常:初始化界面错误!");
  }      
}

function initForm()
{
  try
  {
    initInpBox();
    if(operFlag == "1")
    { 
    	returncomcode();
    	initManuUWAllPool();
    	returnPolSum();
    	//initPolGrid();  
   // 	alert();
    	//initAllPolGrid();    
    }
    if(operFlag == "2" || operFlag == "3")
    {
    	//alert("aaaaaa");
    	initGrpPolGrid();
    	initGrpAllPolGrid(); 
    }  
   // alert(1);        
    //easyQueryClick();
  }
  catch(re)
  {
    alert("在UWInit.jsp-->InitForm函数中发生异常:初始化界面错误!" +re.name +":"+re.message);
  }
}

//modify by lzf 2013-03-28
function initManuUWAllPool(){
	 var config = {
				functionId : "10010028",//核保完成
				//functionId : "10010021",
				//activityId : "0000001110";
	           // operator : operator,
				public : {
					query : {
						queryButton : {
						},
						arrayInfo : {
							col : {								
								result0 : {title : " 印刷号  ",style : 1,colNo : 1},
								result1 : {title : "合同号码",style : 3},
								result2 : {title : "投保合同号",style : 3},
								result3 : {
									title : "代理人编码",
									style : 2,
									colNo : 6 ,
									referfunc1 : "queryAgent",
									referpara1 : "[0],[6]"
								},
								result4 : {title : "代理人名称",style : 3},
								result5 : {title : "投保人编码",style : 3},
								result6 : {title : "投保人名称",style : 1,colNo : 10},
								result7 : {title : "状态日期",style : 3},
								result8 : {title : "状态时间",style : 3},
								result9 : {
									title : "管理机构",
									style : 2,
									colNo : 5,
									refercode1 : "station",
									colName : "missionprop10",
									addCondition : function(colName, value) {
										return " and " + colName + " like '" + value
												+ "%' ";
									}
								},
								result10 : {title : "呈报类型",style : 3},
								result11 : {
									title : "核保级别",
									style : 2,
									colNo : 9,
									refercode1 : "uwpopedom",
									colName : "missionprop12",
									addCondition : function(colName, value) {
										return " and " + colName + " like '" + value
												+ "%'";
									}
									},
								result12 : {title : "复核日期",style : 3},
								result13 : {title : "核保员代码",style : 3},
								result14 : {title : "申请日期",style : 3},
								result15 : {title : "分保标志",style : 3},
								result16 : {
									title : "扫描时间",
									style : 8,
									colNo : 2,
									refercode1 : "scandate",
									addCondition : function(colName,value){
										return " and exists (select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UA001' and makedate <='"+ value +"') ";
									}
								  },
								result17 : {
									title : "核保状态",
									style : 2,
									colNo : 12,
									refercode1 : "uwstatus",
									defaultvalue : "1",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and exists(select distinct 1 from lccuwmaster where contno=t.missionprop1 and lccuwmaster.uwstate in('1','2','3'))";
										}else{
											return " and exists(select distinct 1 from lccuwmaster where contno=t.missionprop1 and lccuwmaster.uwstate = '"+ value +"')";
										}
										
									}
								
									},
								result18 : {title : "核保员",style : 3},
								result19 : {title : "上报核保轨迹",style : 3},
								result20 : {
									title : "核保员代码",
									style : "0",
									colNo : 13,
									referfunc1 : "CheckUserCode",
									referpara1 : "1" 
									},
								result21 : {title : "并单号",style : 3},
								result22 : {title : "核保等待原因",style : 3},
								result23 : {title : "核保员说明",style : 3},
								result24 : {title : "上报核保师编码",style : 3},
							
								newCol0 : {
									title :"销售渠道",
									style : 2,
									colNo : 3,
									refercode1 : "salechnl",
									addCondition : function(colName,value){
										return "  and exists(select 'x' from lccont where lccont.contno = t.missionprop2 and lccont.salechnl='"+ value +"')";
									}
								},
								newCol1 : {
									title :"险种编码",
									style : 2,
									colNo : 4,
									refercode1 : "riskcode",
									addCondition : function(colName,value){
										return " and exists ( select 1 from lcpol where polno=(select min(polno) from lcpol where prtno = t.missionprop1 and insuredno = (select insuredno from lccont where prtno = t.missionprop1 and conttype = '1')) and riskcode = '"+ value +"') ";
									}
								},
								newCol2 : {
									title :"星级业务员",
									style : 0,
									colNo : 7,
									refercode2 : "staragent",
									refercodedata2 : "0|^0|否^1|是",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and exists(select 1 from latree where trim(agentcode)=trim(t.missionprop4) and (vipproperty = '0' or vipproperty is null ))";
										}
										if(value=="1"){
											return " and exists(select 1 from latree where trim(agentcode)=trim(t.missionprop4) and vipproperty = '1')";
										}
									}
								},
								newCol3 : {
									title :"VIP客户",
									style : 2,
									colNo : 8,
									refercode1 : "vipvalue",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and  exists(select 1 from ldperson where trim(customerno)=trim(t.missionprop6) and (vipvalue = '0' or vipvalue is null))";
										}
										if(value=="1"){
											return " and exists(select 1 from ldperson where trim(customerno)=trim(t.missionprop6) and vipvalue = '1')";
										}
									}
								},
								newCol4 : {
									title :"被保人",
									style : 1,
									colNo : 11,
									refercode1 : "InsurName2",
									addCondition : function(colName,value){
										return " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredname='"+value+"')";
									}
								},
								newCol5 : {
									title :"是否到账",
									style : 2,
									colNo : 14,
									refercode2 : "EnteraccState2",
									refercodedata2 : "0|^0|否^1|是",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and not exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
										}
										if(value =="1"){
											return " and exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
										}
										    
									}
								},
								newCol6 : {
									title :"是否员工单",
									style : 2,
									colNo : 15,
									refercode2 : "OperatorState2",
									refercodedata2 : "0|^0|否^1|是",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and t.MissionProp4 not like '%%999999' ";
										}
										if(value =="1"){
											return " and t.MissionProp4  like '%%999999' ";
										}										    
									}
								},
								newCol7 : {
									title :"业务员等级",
									style : 2,
									colNo : 16,
									refercode1 : "uwlevel",
									addCondition : function(colName,value){
										return " and exists(select 'x' from latree where agentcode = t.MissionProp4 and UWLevel = '"+ value +"')";
									}
								},
								newCol8 : {
									title :"百团机构投保单",
									style : 2,
									colNo : 17,
									refercode2 : "baituancom",
									refercodedata2 : "0|^0|否^1|是",
									addCondition : function(colName,value){
										if(value =="0"){
											return " and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '0' or comareatype1 is null) ";
										}
										if(value =="1"){
											return "  and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '1')";
										}
									}
								}
							}
						}
					},
					resultTitle : "共享工作池",
					result : {
						selBoxEventFuncName:"ApplyUW",
						 selBoxEventFuncParam:"",
						condition : {
							"0" : false,
							"1" : false,
							"2" : false,
							"3" : false,
							"4" : true,
							"5" : " and not exists(select 1 from ES_DOC_MAIN where doccode = prtno and subtype='UR201')" +
							      " and (UserCode is null or UserCode = '0000000000') and managecom like '"+ returncomcode() +"%'" +
							      " and UWAuthority <= (select (case SurpassGradeFlag when null then uwpopedom else SurpassGradeFlag end) from lduwuser where usercode='"+operator+"' and uwtype='1')"
						},
						mulLineCount : 0,
						arrayInfo : {
							col : {
								col5 : "0",
								col6 : "0",
								col7 : "0",
								result0 : {title : " 印刷号  ",style : 0,colNo : 10},
								result1 : {title : "合同号码",style : 3},
								result2 : {title : "投保合同号",style : 3},
								result3 : {title : "代理人编码",style : 3},
								result4 : {title : "代理人名称",style : 3},
								result5 : {title : "投保人编码",style : 3},
								result6 : {title : "投保人名称",style : 3},
								result7 : {title : "最后回复日期",style : 3},
								result8 : {title : "最后回复时间",style : 3},
								result9 : {
									title : "管理机构",
									style : 0,
									colNo : 8,
									colName : "(case  when (char_length(t.missionprop10)>4) then substr(t.missionprop10,1,4) else t.missionprop10 end) ",
									rename : "managecom"
								},
								result10 : {
									title : "呈报类型",
									style : 3,
									colName : " (case t.missionprop11 when '0' then '新契约核保' when '1' then '上报'  when '2' then '超评点上报' when '3' then '再保上报' when '4' then '返回下级' end) ",
									rename : "order1"
									},
								result11 : {
									title : "核保级别",
									style : 0,
									colNo : 1
									},
								result12 : {title : "复核日期",style : 3},
								result13 : {title : "核保员代码",style : 3},
								result14 : {title : "申请日期",style : 3},
								result15 : {title : "分保标志",style : 3},
								result16 : {
									title : "扫描时间",
									style : 0,
									colNo : 5,
									colName : " (case t.missionprop17 when 'NOSCAN' then '无扫描件' else missionprop17 end) ",
									rename : "scandate"
									},
								result17 : {
									title : "核保状态",
									style : 3
									},
								result18 : {title : "核保员",style : 3},
								result19 : {title : "上报核保轨迹",style : 3},
								result20 : {
									title : "核保员代码",
									style : 0,
									colNo : 2,
									colName : " (case t.missionprop20 when null then t.lastoperator else t.missionprop20 end) ",
									rename : "lastusercode"
									},
								result21 : {title : "并单号",style : 3},
								result22 : {title : "核保等待原因",style : 3},
								result23 : {title : "核保员说明",style : 3},
								result24 : {title : "上报核保师编码",style : 3},
								
								newcol0 : {
									title : "VIP客户",
									colName : " (case (select vipvalue from ldperson where customerno = t.missionprop6) when '1' then '是' else null end)",
									rename : "vipvalue",
									style : 0,
									colNo : 3
								},
								newcol1 : {
									title : "星级业务员",
									colName : " (select '星级业务员'  from dual)",
									rename : "order3",
									style : 0,
									colNo : 4
								},
								newcol2 : {
									title : "险种编码",
									colName : " (select riskcode from lcpol where polno=(select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1 and conttype='1')))",
									rename : "riskcode",
									style : 0,
									colNo : 9
								},
								newcol3 : {
									title : "被保险人",
									colName : " (select insuredname from lccont where prtno =t.MissionProp1 and conttype='1') ",
									rename : "insuredname",
									style : 0,
									colNo : 11
								},
								newcol4 : {
									title : "最后回复时间",
									colName : " ( case when (select uwstate from lccuwmaster where contno= t.missionprop1)='1' then null when (select uwstate from lccuwmaster where contno= t.missionprop1)<>'1' then (concat(concat(missionprop8,' '),missionprop9)) end) ",
									rename : "order4",
									style : 0,
									colNo : 6
								},
								newcol5 : {
									title : "核保状态",
									colName : " (select c.uwstate from lccuwmaster c where c.contno=t.missionprop2) ",
									rename : "uwstate",
									colNo : 7,
									style : 0
								}
							}
						}
					}
				},				
				midContent :{//the content between two pools
					id : "MidContent",
					show : true,
					html : " <div> 总单数 <Input class='readonly' id=PolSum readonly name=PolSum ></div>"
				},
				private : {
					query : {
						queryButton : {
						},
						arrayInfo : {
							col : {
								
								result0 : {title : " 印刷号  ",style : 1,colNo : 1},
								result1 : {title : "合同号码",style : 3},
								result2 : {title : "投保合同号",style : 3},
								result3 : {
									title : "代理人编码",
									style : 2,
									colNo : 3 ,
									referfunc1 : "queryAgent",
									referpara1 : "[0],[3]"
								},
								result4 : {title : "代理人名称",style : 3},
								result5 : {title : "投保人编码",style : 3},
								result6 : {title : "投保人名称",style : 1,colNo : 8},
								result7 : {title : "状态日期",style : 3},
								result8 : {title : "状态时间",style : 3},
								result9 : {
									title : "管理机构",
									style : 2,
									colNo : 6,
									refercode1 : "station",
									colName : "missionprop10",
									addCondition : function(colName, value) {
										return " and " + colName + " like '" + value
												+ "%' ";
									}
								},
								result10 : {
									title : "呈报类型",
									style : 2,
									colNo : 5,
									refercode2 : "uwupreport",
									refercodedata2 : "0|^1|上报^4|返回下级",
									addCondition : function(colName,value){
										return " and missionprop11 = '"+ value +"'";
									}
								},
								result11 : {
									title : "核保级别",
									style : 3
									},
								result12 : {title : "复核日期",style : 3},
								result13 : {title : "核保员代码",style : 3},
								result14 : {title : "申请日期",style : 3},
								result15 : {title : "分保标志",style : 3},
								result16 : {
									title : "扫描时间",
									style : 3
								  },
								result17 : {
									title : "核保状态",
									style : 2,
									colNo : 4,
									refercode1 : "uwstatus",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and exists(select distinct 1 from lccuwmaster where contno=t.missionprop1 and lccuwmaster.uwstate in('1','2','3'))";
										}else{
											return " and exists(select distinct 1 from lccuwmaster where contno=t.missionprop1 and lccuwmaster.uwstate = '"+ value +"')";
										}
									   }								
									},
								result18 : {
									title : "核保员",
									style : 3
									},
								result19 : {title : "上报核保轨迹",style : 3},
								result20 : {
									title : "核保员代码",
									style : 3
									},
								result21 : {title : "并单号",style : 3},
								result22 : {title : "核保等待原因",style : 3},
								result23 : {title : "核保员说明",style : 3},
								result24 : {title : "上报核保师编码",style : 3},
								
								newCol0 : {
									title :"销售渠道",
									style : 2,
									colNo : 7,
									refercode1 : "salechnl",
									addCondition : function(colName,value){
										return "  and exists(select 'x' from lccont where lccont.contno = t.missionprop2 and lccont.salechnl='"+ value +"')";
									}
								},
								newCol1 : {
									title :"险种编码",
									style : 2,
									colNo : 2,
									refercode1 : "riskcode",
									addCondition : function(colName,value){
										return " and exists ( select 1 from lcpol where polno=(select min(polno) from lcpol where prtno = t.missionprop1 and insuredno = (select insuredno from lccont where prtno = t.missionprop1 and conttype = '1')) and riskcode = '"+ value +"') ";
									}
								},																
								newCol2 : {
									title :"被保人",
									style : 1,
									colNo : 9,
									refercode1 : "InsurName2",
									addCondition : function(colName,value){
										return " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredname='"+value+"')";
									}
								},
								newCol3 : {
									title :"是否到账",
									style : 2,
									colNo : 10,
									refercode2 : "EnteraccState2",
									refercodedata2 : "0|^0|否^1|是",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and not exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
										}
										if(value =="1"){
											return " and exists(select 'x' from ljtempfee x where x.otherno = t.missionprop2 and x.enteraccdate is not null) ";
										}
										    
									}
								},
								newCol4 : {
									title :"是否员工单",
									style : 2,
									colNo : 11,
									refercode2 : "OperatorState2",
									refercodedata2 : "0|^0|否^1|是",
									addCondition : function(colName,value){
										if(value=="0"){
											return " and t.MissionProp4 not like '%%999999' ";
										}
										if(value =="1"){
											return " and t.MissionProp4  like '%%999999' ";
										}										    
									}
								},
								newCol5 : {
									title :"业务员等级",
									style : 2,
									colNo : 12,
									refercode1 : "uwlevel",
									addCondition : function(colName,value){
										return " and exists(select 'x' from latree where agentcode = t.MissionProp4 and UWLevel = '"+ value +"')";
									}
								},
								newCol6 : {
									title :"百团机构投保单",
									style : 2,
									colNo : 13,
									refercode2 : "baituancom",
									refercodedata2 : "0|^0|否^1|是",
									addCondition : function(colName,value){
										if(value =="0"){
											return " and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '0' or comareatype1 is null) ";
										}
										if(value =="1"){
											return "  and exists(select 1 from ldcom where comcode = t.MissionProp10 and comareatype1 = '1')";
										}
									}
								}
							}
						}
					},
					resultTitle : "待核保投保单",
					result : {	
						selBoxEventFuncName:"IniteasyQueryAddClick",
						 selBoxEventFuncParam:"",
						condition : {
							"0" : false,
							"1" : false,
							"2" : false,
							"5" : " and not exists(select 1 from ES_DOC_MAIN where doccode = prtno and subtype='UR201')"
							       +" and (defaultoperator ='" + operator + "' or usercode = '" + operator + "')"									      
							       +" and managecom like '"+ returncomcode() +"%'"
						},
						mulLineCount : 0,
						arrayInfo : {
							col : {
								col5 : "0",
								col6 : "0",
								col7 : "0",
								result0 : {title : " 印刷号  ",style : 0,colNo : 9},
								result1 : {title : "合同号码",style : 3},
								result2 : {title : "投保合同号",style : 3},
								result3 : {title : "代理人编码",style : 3},
								result4 : {title : "代理人名称",style : 3},
								result5 : {title : "投保人编码",style : 3},
								result6 : {title : "投保人名称",style : 3},
								result7 : {title : "最后回复日期",style : 3},
								result8 : {title : "最后回复时间",style : 3},
								result9 : {
									title : "管理机构",
									style : 0,
									colNo : 7,
									colName : "(case  when (char_length(t.missionprop10)>4) then substr(t.missionprop10,1,4) else t.missionprop10 end) ",
									rename : "managecom"
								},
								result10 : {
									title : "呈报类型",
									style : 0,
									colNo : 3,
									colName : "(case t.missionprop11 when '0' then '新契约核保' when '1' then '上报'  when '2' then '超评点上报' when '3' then '再保上报' when '4' then '返回下级' end) ",
									rename : "order1"
									},
								result11 : {
									title : "核保级别",
									style : 0,
									colNo : 1
									},
								result12 : {title : "复核日期",style : 3},
								result13 : {title : "核保员代码",style : 3},
								result14 : {title : "申请日期",style : 3},
								result15 : {title : "分保标志",style : 3},
								result16 : {
									title : "扫描时间",
									style : 0,
									colNo : 4,
									colName : " (case t.missionprop17 when 'NOSCAN' then '无扫描件' else missionprop17 end) ",
									rename : "scandate"
									},
								result17 : {
									title : "核保状态",
									style : 3
									},
								result18 : {title : "核保员",style : 3},
								result19 : {title : "上报核保轨迹",style : 3},
								result20 : {
									title : "核保员代码",
									style : 0,
									colNo : 10,
									colName : " (case t.missionprop20 when null then t.lastoperator else t.missionprop20 end) ",
									rename : "lastusercode"
									},
								result21 : {title : "并单号",style : 3},
								result22 : {title : "核保等待原因",style : 3},
								result23 : {title : "核保员说明",style : 3},
								result24 : {title : "上报核保师编码",style : 3},
								
								newcol0 : {
									title : "险种编码",
									colName : "(select riskcode from lcpol where polno=(select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1 and conttype='1')))",
									rename : "riskcode",
									style : 0,
									colNo : 8
								},
								newcol1 : {
									title : "被保险人",
									colName : "(select insuredname from lccont where prtno =t.MissionProp1 and conttype='1') ",
									rename : "insuredname",
									style : 0,
									colNo : 2
								},
								newcol2 : {
									title : "最后回复时间",
									colName : " (case when (select uwstate from lccuwmaster where contno= t.missionprop1)='1' then null when (select uwstate from lccuwmaster where contno= t.missionprop1)<>'1' then (concat(concat(missionprop8,' '),missionprop9)) end) ",
									rename : "order4",
									style : 0,
									colNo : 5
								},
								newcol3 : {
									title : "核保状态",
									colName : " (select c.uwstate from lccuwmaster c where c.contno=t.missionprop2) ",
									rename : "uwstate",
									colNo : 6,
									style : 0
								}
							}
						}
					}
				}
			};
			jQuery("#ManuUWAllPool").workpool(config);
			jQuery("#privateSearch").click();
}


// 保单信息列表的初始化
/*function initPolGrid()
  {              
  	bg1 = new CGrid("fm", "PolGrid", turnPage2);
  	bg1.setActivityId("0000001100");
  	bg1.setProcessId("0000000003");
  	//正常模式
  	//bg1.addColumn("核保级别,被保险人,上报类型,星级业务员,扫描时间,最后回复时间,核保任务状态,管理机构,险种编码,印刷号,最近处理人,VIP客户,业务员,投保日期,复核日期,上报标志,到账日期,工作流任务号,工作流子任务号,工作流活动Id,核保任务状态,初审日期,投保人号");
  	//bg.setRadioFunction("IniteasyQueryAddClick");
  	//JSON模式
  	//核保级别,被保险人,上报类型,星级业务员,扫描时间,最后回复时间,核保任务状态,管理机构,险种编码,
  	//印刷号,最近处理人,VIP客户,业务员,投保日期,复核日期,上报标志,到账日期, 工作流任务号 ,工作流子任务号,
  	//工作流活动ID,核保任务状态,初审日期,投保人号         

  	bg1.addColumn([//{title:'序号',width:'20px'},
  	{title:'核保级别',width:'20px'},
  	{title:'被保险人',width:'80px'},
  	{title:'上报类型',width:'80px'},
  	{title:'星级业务员',width:'0px'},
  	{title:'扫描时间',width:'150px'},
  	{title:'最后回复时间',width:'150px'},
  	{title:'核保任务状态',width:'40px'},
  	{title:'管理机构',width:'60px'},
  	{title:'险种编码',width:'120px'},
  	{title:'印刷号',width:'120px'},
  	{title:'最近处理人',width:'60px'},
  	{title:'VIP客户',width:'0px'},
  	{title:'业务员',width:'0px'},
  	{title:'投保日期',width:'0px'},
  	{title:'复核日期',width:'0px'},
  	{title:'上报标志',width:'0px'},
  	{title:'到账日期',width:'0px'},
  	{title:'工作流任务号',width:'0px'},
  	{title:'工作流子任务号',width:'0px'},
  	{title:'工作流活动Id',width:'0px'},
  	{title:'核保状态',width:'0px'},
  	{title:'初审日期',width:'0px'},
  	{title:'投保人号',width:'0px'}
  	]); 	
  	
  	//bg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
  	//bg.addColumn("ManageCom,AgentCode");
  	//bg.addParam("ManageCom","86",'like');
  	//bg.setDefaultOperator(null);
  	bg1.showEmergency(1, 1, 1);
  	bg1.setRadioFunction("IniteasyQueryAddClick");
  	bg1.setEmergencyColor([[10, "yellow"], [50, "#33DEDB"], [100, "#F06147"]]);
  	bg1.initGrid();
    //bg1.queryData();
  	//changeColor(bg1);
  
  	PolGrid = bg1.grid;
  	/*       
     var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="核保级别";         		//列名
      iArray[1][1]="20px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="被保险人";         		//列名
      iArray[2][1]="80px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="上报类型";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="星级业务员";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="扫描时间";         		//列名
      iArray[5][1]="150px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[6]=new Array();                                                       
      iArray[6][0]="最后回复时间";         		//列名                                     
      iArray[6][1]="150px";            		//列宽                                   
      iArray[6][2]=100;            			//列最大值                                 
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[7]=new Array();
      iArray[7][0]="核保任务状态";         		//列名
      iArray[7][1]="20px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="管理机构";         		//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="险种编码";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="印刷号";         		//列名
      iArray[10][1]="120px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="最近处理人";         		//列名
      iArray[11][1]="60px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[12]=new Array();                                                       
      iArray[12][0]="VIP客户";         		//列名                                     
      iArray[12][1]="40px";            		//列宽                                   
      iArray[12][2]=100;            			//列最大值                                 
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[13]=new Array();
      iArray[13][0]="业务员";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[14]=new Array();
      iArray[14][0]="投保日期";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="复核日期";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许 


      iArray[16]=new Array();
      iArray[16][0]="上报标志";         		//列名
      iArray[16][1]="0px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[17]=new Array();
      iArray[17][0]="到账日期";         		//列名
      iArray[17][1]="0px";            		//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[18]=new Array();
      iArray[18][0]="工作流任务号";         		//列名
      iArray[18][1]="0px";            		//列宽
      iArray[18][2]=200;            			//列最大值
      iArray[18][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[19]=new Array();
      iArray[19][0]="工作流子任务号";         		//列名
      iArray[19][1]="0px";            		//列宽
      iArray[19][2]=200;            			//列最大值
      iArray[19][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[20]=new Array();
      iArray[20][0]="工作流活动Id";         		//列名
      iArray[20][1]="0px";            		//列宽
      iArray[20][2]=60;            			//列最大值
      iArray[20][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[21]=new Array();
      iArray[21][0]="核保任务状态";         		//列名
      iArray[21][1]="0px";            		//列宽
      iArray[21][2]=60;            			//列最大值
      iArray[21][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[22]=new Array();
      iArray[22][0]="初审日期";         		//列名
      iArray[22][1]="80px";            		//列宽
      iArray[22][2]=100;            			//列最大值
      iArray[22][3]=8;              			//是否允许输入,1表示允许，0表示不允许       
      
      iArray[23]=new Array();
      iArray[23][0]="投保人号";         		//列名
      iArray[23][1]="0px";            		//列宽
      iArray[23][2]=100;            			//列最大值
      iArray[23][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray); 
      
      PolGrid.selBoxEventFuncName = "IniteasyQueryAddClick";
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
      */
//}

// 保单信息列表的初始化
function initGrpPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=3;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="集体投保单号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保单位";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="核保任务状态";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[5]=new Array();                                                       
      iArray[5][0]="核保类型";         		//列名                                     
      iArray[5][1]="80px";            		//列宽                                   
      iArray[5][2]=100;            			//列最大值                                 
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[9]=new Array();
      iArray[9][0]="上报类型";              //列名
      iArray[9][1]="80px";                   //列宽
      iArray[9][2]=60;                      //列最大值
      iArray[9][3]=0;       
      
      iArray[10]=new Array();
      iArray[10][0]="核保级别";               //列名
      iArray[10][1]="60px";                 //列宽
      iArray[10][2]=60;                     //列最大值
      iArray[10][3]=0;                      
      
      iArray[11]=new Array();
      iArray[11][0]="管理机构";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[12]=new Array();
      iArray[12][0]="业务员";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[13]=new Array();
      iArray[13][0]="投保日期";         		//列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=8;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[14]=new Array();
      iArray[14][0]="复核日期";         		//列名
      iArray[14][1]="80px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=8;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="星级业务员";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[16]=new Array();
      iArray[16][0]="VIP客户";         		//列名
      iArray[16][1]="80px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[17]=new Array();
      iArray[17][0]="上报标志";         		//列名
      iArray[17][1]="0px";            		//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许    
      
      iArray[18]=new Array();
      iArray[18][0]="到账日期";         		//列名
      iArray[18][1]="80px";            		//列宽
      iArray[18][2]=60;            			//列最大值
      iArray[18][3]=8;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[19]=new Array();
      iArray[19][0]="扫描日期";         		//列名
      iArray[19][1]="80px";            		//列宽
      iArray[19][2]=60;            			//列最大值
      iArray[19][3]=8;              			//是否允许输入,1表示允许，0表示不允许 

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //这些属性必须在loadMulLine前
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;     
      PolGrid.selBoxEventFuncName = "GrpIniteasyQueryAddClick";
      PolGrid.loadMulLine(iArray);     
      
     
      
      //这些操作必须在loadMulLine后面
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// 保单信息列表的初始化
/*function initAllPolGrid()
  {    
  	//alert('1');
  	bg = new CGrid("fm", "AllPolGrid",turnPage);
  	bg.setActivityId("0000001100");
  	bg.setProcessId("0000000003");
  	//bg.setDefaultOperator(null);
  	//bg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
  	//核保级别,最近处理人,VIP客户,星级业务员,扫描时间,最后回复时间,核保任务状态,管理机构,
  	//险种编码,印刷号,被保险人,上报类型,业务员,投保日期,复核日期,上报标志,到账日期,工作流任务号,工作流子任务号,工作流活动ID,初审日期
  	bg.addColumn([
  	{title:'核保级别',width:'20px'},
  	{title:'最近处理人',width:'60px'},
  	{title:'VIP客户',width:'40px'},
  	{title:'星级业务员',width:'100px'},
  	{title:'扫描时间',width:'150px'},
  	{title:'最后回复时间',width:'150px'},
  	{title:'核保任务状态',width:'20px'},
  	{title:'管理机构',width:'40px'},
  	{title:'险种编码',width:'60px'},
  	{title:'印刷号',width:'120px'},
  	{title:'被保险人',width:'80px'},
  	{title:'上报类型',width:'0px'},
  	{title:'业务员',width:'0px'},
  	{title:'投保日期',width:'0px'},
  	{title:'复核日期',width:'0px'},
  	{title:'上报标志',width:'0px'},
  	{title:'到账日期',width:'0px'},
  	{title:'工作流任务号',width:'0px'},
  	{title:'工作流子任务号',width:'0px'},
  	{title:'工作流活动Id',width:'0px'},
  	{title:'初审日期',width:'0px'}
  	]); 	
  	
  	
  	
  	bg.showEmergency(1, 1, 1);
  	bg.setRadioFunction("ApplyUW");
  	bg.setEmergencyColor([[10, "yellow"], [50, "#33DEDB"], [100, "#F06147"]]);
  	bg.initGrid();
  	//bg.queryData();
  	//changeColor(bg);
  
  	AllPolGrid = bg.grid;
  	
  	/*                           
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="核保级别";         		//列名
      iArray[1][1]="20px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="最近处理人";         		//列名
      iArray[2][1]="60px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="VIP客户";         		//列名
      iArray[3][1]="40px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="星级业务员";         		//列名
      iArray[4][1]="0px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[5]=new Array();
      iArray[5][0]="扫描时间";         		//列名
      iArray[5][1]="150px";            		//列宽
      iArray[5][2]=100;            			//列最大值
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[6]=new Array();                                                       
      iArray[6][0]="最后回复时间";         		//列名                                     
      iArray[6][1]="150px";            		//列宽                                   
      iArray[6][2]=100;            			//列最大值                                 
      iArray[6][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[7]=new Array();
      iArray[7][0]="核保任务状态";         		//列名
      iArray[7][1]="20px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="管理机构";         		//列名
      iArray[8][1]="40px";            		//列宽
      iArray[8][2]=200;            			//列最大值
      iArray[8][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[9]=new Array();
      iArray[9][0]="险种编码";         		//列名
      iArray[9][1]="60px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="印刷号";         		//列名
      iArray[10][1]="120px";            		//列宽
      iArray[10][2]=100;            			//列最大值
      iArray[10][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[11]=new Array();
      iArray[11][0]="被保险人";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=100;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[12]=new Array();                                                       
      iArray[12][0]="上报类型";         		//列名                                     
      iArray[12][1]="0px";            		//列宽                                   
      iArray[12][2]=100;            			//列最大值                                 
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[13]=new Array();
      iArray[13][0]="业务员";         		//列名
      iArray[13][1]="0px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[14]=new Array();
      iArray[14][0]="投保日期";         		//列名
      iArray[14][1]="0px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="复核日期";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许 


      iArray[16]=new Array();
      iArray[16][0]="上报标志";         		//列名
      iArray[16][1]="0px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[17]=new Array();
      iArray[17][0]="到账日期";         		//列名
      iArray[17][1]="0px";            		//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[18]=new Array();
      iArray[18][0]="工作流任务号";         		//列名
      iArray[18][1]="0px";            		//列宽
      iArray[18][2]=200;            			//列最大值
      iArray[18][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[19]=new Array();
      iArray[19][0]="工作流子任务号";         		//列名
      iArray[19][1]="0px";            		//列宽
      iArray[19][2]=200;            			//列最大值
      iArray[19][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[20]=new Array();
      iArray[20][0]="工作流活动Id";         		//列名
      iArray[20][1]="0px";            		//列宽
      iArray[20][2]=60;            			//列最大值
      iArray[20][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[21]=new Array();
      iArray[21][0]="初审日期";         		//列名
      iArray[21][1]="80px";            		//列宽
      iArray[21][2]=100;            			//列最大值
      iArray[21][3]=8;              			//是否允许输入,1表示允许，0表示不允许       

      AllPolGrid = new MulLineEnter( "fm" , "AllPolGrid" ); 
      //这些属性必须在loadMulLine前
      AllPolGrid.mulLineCount = 3;   
      AllPolGrid.displayTitle = 1;
      AllPolGrid.locked = 1;
      AllPolGrid.canSel = 1;
      AllPolGrid.hiddenPlus = 1;
      AllPolGrid.hiddenSubtraction = 1;
      AllPolGrid.loadMulLine(iArray); 
      AllPolGrid.selBoxEventFuncName = "ApplyUW";   

      }
      catch(ex)
      {
        alert(ex);
      }
      */
//}

// 保单信息列表的初始化
function initGrpAllPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="序号";         			//列名（此列为顺序号，列名无意义，而且不显示）
      iArray[0][1]="30px";            		//列宽
      iArray[0][2]=30;            			//列最大值
      iArray[0][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[1]=new Array();
      iArray[1][0]="印刷号";         		//列名
      iArray[1][1]="0px";            		//列宽
      iArray[1][2]=100;            			//列最大值
      iArray[1][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[2]=new Array();
      iArray[2][0]="集体投保单号";         		//列名
      iArray[2][1]="160px";            		//列宽
      iArray[2][2]=100;            			//列最大值
      iArray[2][3]=0;              			//是否允许输入,1表示允许，0表示不允许

      iArray[3]=new Array();
      iArray[3][0]="投保单位";         		//列名
      iArray[3][1]="80px";            		//列宽
      iArray[3][2]=100;            			//列最大值
      iArray[3][3]=0;              			//是否允许输入,1表示允许，0表示不允许
      
      iArray[4]=new Array();
      iArray[4][0]="核保任务状态";         		//列名
      iArray[4][1]="100px";            		//列宽
      iArray[4][2]=100;            			//列最大值
      iArray[4][3]=0;              			//是否允许输入,1表示允许，0表示不允许            

      iArray[5]=new Array();                                                       
      iArray[5][0]="核保类型";         		//列名                                     
      iArray[5][1]="80px";            		//列宽                                   
      iArray[5][2]=100;            			//列最大值                                 
      iArray[5][3]=0;              			//是否允许输入,1表示允许，0表示不允许   
            
      iArray[6]=new Array();
      iArray[6][0]="工作流任务号";         		//列名
      iArray[6][1]="0px";            		//列宽
      iArray[6][2]=200;            			//列最大值
      iArray[6][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[7]=new Array();
      iArray[7][0]="工作流子任务号";         		//列名
      iArray[7][1]="0px";            		//列宽
      iArray[7][2]=200;            			//列最大值
      iArray[7][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[8]=new Array();
      iArray[8][0]="工作流活动Id";         		//列名
      iArray[8][1]="0px";            		//列宽
      iArray[8][2]=60;            			//列最大值
      iArray[8][3]=3;              			//是否允许输入,1表示允许，0表示不允许
  
       iArray[9]=new Array();
      iArray[9][0]="上报类型";         		//列名
      iArray[9][1]="100px";            		//列宽
      iArray[9][2]=60;            			//列最大值
      iArray[9][3]=0;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[10]=new Array();
      iArray[10][0]="核保级别";               //列名
      iArray[10][1]="60px";                 //列宽
      iArray[10][2]=60;                     //列最大值
      iArray[10][3]=0;                      
      
      iArray[11]=new Array();
      iArray[11][0]="管理机构";         		//列名
      iArray[11][1]="80px";            		//列宽
      iArray[11][2]=60;            			//列最大值
      iArray[11][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[12]=new Array();
      iArray[12][0]="业务员";         		//列名
      iArray[12][1]="80px";            		//列宽
      iArray[12][2]=60;            			//列最大值
      iArray[12][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[13]=new Array();
      iArray[13][0]="投保日期";         		//列名
      iArray[13][1]="80px";            		//列宽
      iArray[13][2]=60;            			//列最大值
      iArray[13][3]=8;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[14]=new Array();
      iArray[14][0]="复核日期";         		//列名
      iArray[14][1]="80px";            		//列宽
      iArray[14][2]=60;            			//列最大值
      iArray[14][3]=8;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[15]=new Array();
      iArray[15][0]="星级业务员";         		//列名
      iArray[15][1]="0px";            		//列宽
      iArray[15][2]=60;            			//列最大值
      iArray[15][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[16]=new Array();
      iArray[16][0]="VIP客户";         		//列名
      iArray[16][1]="80px";            		//列宽
      iArray[16][2]=60;            			//列最大值
      iArray[16][3]=0;              			//是否允许输入,1表示允许，0表示不允许 

      iArray[17]=new Array();
      iArray[17][0]="上报标志";         		//列名
      iArray[17][1]="0px";            		//列宽
      iArray[17][2]=60;            			//列最大值
      iArray[17][3]=3;              			//是否允许输入,1表示允许，0表示不允许 
      
      iArray[18]=new Array();
      iArray[18][0]="到账日期";         		//列名
      iArray[18][1]="80px";            		//列宽
      iArray[18][2]=60;            			//列最大值
      iArray[18][3]=8;              			//是否允许输入,1表示允许，0表示不允许 
      
      
      AllPolGrid = new MulLineEnter( "fm" , "AllPolGrid" ); 
      //这些属性必须在loadMulLine前
      AllPolGrid.mulLineCount = 3;   
      AllPolGrid.displayTitle = 1;
      AllPolGrid.locked = 1;
      AllPolGrid.canSel = 1;
      AllPolGrid.hiddenPlus = 1;
      AllPolGrid.hiddenSubtraction = 1;
      AllPolGrid.loadMulLine(iArray);     

      }
      catch(ex)
      {
        alert(ex);
      }
}
</script>
