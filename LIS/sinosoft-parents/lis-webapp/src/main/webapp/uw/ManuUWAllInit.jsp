<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�ManuUWAllInit.jsp
//�����ܣ������˹��˱�
//�������ڣ�2005-01-29 11:10:36
//������  ��HYQ
//���¼�¼��  ������    ��������     ����ԭ��/����
%> 
<%@page import="com.sinosoft.lis.pubfun.*"%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
	//���ҳ��ؼ��ĳ�ʼ����
	GlobalInput globalInput = (GlobalInput)session.getValue("GI");
	if(globalInput == null) 
	{
		out.println("session has expired");
		return;
	}
	String strOperator = globalInput.Operator;
%>                            

<script language="JavaScript">

// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{ 

  try
  {            
  	if(operFlag == "1")
  	{
  		//fm.ApplyNo.value = "5";    
  		fm.ApplyType.value = "1";  				//��������
  		//fm.ActivityID.value = "0000001110"; 
  	}
  	if(operFlag == "2")
  	{
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "2";    		  //�ŵ�����
  		fm.ActivityID.value = "0000002004"; 
  	}  	      
  	if(operFlag == "3")
  	{
 
  		//fm.ApplyNo.value = "2";  
  		fm.ApplyType.value = "3";    			//ѯ������
  		fm.ActivityID.value = "0000006004"; 
  	}            
  }
  catch(ex)
  {
    alert("��UWInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
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
    alert("��UWInit.jsp-->InitForm�����з����쳣:��ʼ���������!" +re.name +":"+re.message);
  }
}

//modify by lzf 2013-03-28
function initManuUWAllPool(){
	 var config = {
				functionId : "10010028",//�˱����
				//functionId : "10010021",
				//activityId : "0000001110";
	           // operator : operator,
				public : {
					query : {
						queryButton : {
						},
						arrayInfo : {
							col : {								
								result0 : {title : " ӡˢ��  ",style : 1,colNo : 1},
								result1 : {title : "��ͬ����",style : 3},
								result2 : {title : "Ͷ����ͬ��",style : 3},
								result3 : {
									title : "�����˱���",
									style : 2,
									colNo : 6 ,
									referfunc1 : "queryAgent",
									referpara1 : "[0],[6]"
								},
								result4 : {title : "����������",style : 3},
								result5 : {title : "Ͷ���˱���",style : 3},
								result6 : {title : "Ͷ��������",style : 1,colNo : 10},
								result7 : {title : "״̬����",style : 3},
								result8 : {title : "״̬ʱ��",style : 3},
								result9 : {
									title : "�������",
									style : 2,
									colNo : 5,
									refercode1 : "station",
									colName : "missionprop10",
									addCondition : function(colName, value) {
										return " and " + colName + " like '" + value
												+ "%' ";
									}
								},
								result10 : {title : "�ʱ�����",style : 3},
								result11 : {
									title : "�˱�����",
									style : 2,
									colNo : 9,
									refercode1 : "uwpopedom",
									colName : "missionprop12",
									addCondition : function(colName, value) {
										return " and " + colName + " like '" + value
												+ "%'";
									}
									},
								result12 : {title : "��������",style : 3},
								result13 : {title : "�˱�Ա����",style : 3},
								result14 : {title : "��������",style : 3},
								result15 : {title : "�ֱ���־",style : 3},
								result16 : {
									title : "ɨ��ʱ��",
									style : 8,
									colNo : 2,
									refercode1 : "scandate",
									addCondition : function(colName,value){
										return " and exists (select 1 from ES_DOC_MAIN where doccode = t.missionprop1 and subtype='UA001' and makedate <='"+ value +"') ";
									}
								  },
								result17 : {
									title : "�˱�״̬",
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
								result18 : {title : "�˱�Ա",style : 3},
								result19 : {title : "�ϱ��˱��켣",style : 3},
								result20 : {
									title : "�˱�Ա����",
									style : "0",
									colNo : 13,
									referfunc1 : "CheckUserCode",
									referpara1 : "1" 
									},
								result21 : {title : "������",style : 3},
								result22 : {title : "�˱��ȴ�ԭ��",style : 3},
								result23 : {title : "�˱�Ա˵��",style : 3},
								result24 : {title : "�ϱ��˱�ʦ����",style : 3},
							
								newCol0 : {
									title :"��������",
									style : 2,
									colNo : 3,
									refercode1 : "salechnl",
									addCondition : function(colName,value){
										return "  and exists(select 'x' from lccont where lccont.contno = t.missionprop2 and lccont.salechnl='"+ value +"')";
									}
								},
								newCol1 : {
									title :"���ֱ���",
									style : 2,
									colNo : 4,
									refercode1 : "riskcode",
									addCondition : function(colName,value){
										return " and exists ( select 1 from lcpol where polno=(select min(polno) from lcpol where prtno = t.missionprop1 and insuredno = (select insuredno from lccont where prtno = t.missionprop1 and conttype = '1')) and riskcode = '"+ value +"') ";
									}
								},
								newCol2 : {
									title :"�Ǽ�ҵ��Ա",
									style : 0,
									colNo : 7,
									refercode2 : "staragent",
									refercodedata2 : "0|^0|��^1|��",
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
									title :"VIP�ͻ�",
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
									title :"������",
									style : 1,
									colNo : 11,
									refercode1 : "InsurName2",
									addCondition : function(colName,value){
										return " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredname='"+value+"')";
									}
								},
								newCol5 : {
									title :"�Ƿ���",
									style : 2,
									colNo : 14,
									refercode2 : "EnteraccState2",
									refercodedata2 : "0|^0|��^1|��",
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
									title :"�Ƿ�Ա����",
									style : 2,
									colNo : 15,
									refercode2 : "OperatorState2",
									refercodedata2 : "0|^0|��^1|��",
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
									title :"ҵ��Ա�ȼ�",
									style : 2,
									colNo : 16,
									refercode1 : "uwlevel",
									addCondition : function(colName,value){
										return " and exists(select 'x' from latree where agentcode = t.MissionProp4 and UWLevel = '"+ value +"')";
									}
								},
								newCol8 : {
									title :"���Ż���Ͷ����",
									style : 2,
									colNo : 17,
									refercode2 : "baituancom",
									refercodedata2 : "0|^0|��^1|��",
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
					resultTitle : "��������",
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
								result0 : {title : " ӡˢ��  ",style : 0,colNo : 10},
								result1 : {title : "��ͬ����",style : 3},
								result2 : {title : "Ͷ����ͬ��",style : 3},
								result3 : {title : "�����˱���",style : 3},
								result4 : {title : "����������",style : 3},
								result5 : {title : "Ͷ���˱���",style : 3},
								result6 : {title : "Ͷ��������",style : 3},
								result7 : {title : "���ظ�����",style : 3},
								result8 : {title : "���ظ�ʱ��",style : 3},
								result9 : {
									title : "�������",
									style : 0,
									colNo : 8,
									colName : "(case  when (char_length(t.missionprop10)>4) then substr(t.missionprop10,1,4) else t.missionprop10 end) ",
									rename : "managecom"
								},
								result10 : {
									title : "�ʱ�����",
									style : 3,
									colName : " (case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '�������ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end) ",
									rename : "order1"
									},
								result11 : {
									title : "�˱�����",
									style : 0,
									colNo : 1
									},
								result12 : {title : "��������",style : 3},
								result13 : {title : "�˱�Ա����",style : 3},
								result14 : {title : "��������",style : 3},
								result15 : {title : "�ֱ���־",style : 3},
								result16 : {
									title : "ɨ��ʱ��",
									style : 0,
									colNo : 5,
									colName : " (case t.missionprop17 when 'NOSCAN' then '��ɨ���' else missionprop17 end) ",
									rename : "scandate"
									},
								result17 : {
									title : "�˱�״̬",
									style : 3
									},
								result18 : {title : "�˱�Ա",style : 3},
								result19 : {title : "�ϱ��˱��켣",style : 3},
								result20 : {
									title : "�˱�Ա����",
									style : 0,
									colNo : 2,
									colName : " (case t.missionprop20 when null then t.lastoperator else t.missionprop20 end) ",
									rename : "lastusercode"
									},
								result21 : {title : "������",style : 3},
								result22 : {title : "�˱��ȴ�ԭ��",style : 3},
								result23 : {title : "�˱�Ա˵��",style : 3},
								result24 : {title : "�ϱ��˱�ʦ����",style : 3},
								
								newcol0 : {
									title : "VIP�ͻ�",
									colName : " (case (select vipvalue from ldperson where customerno = t.missionprop6) when '1' then '��' else null end)",
									rename : "vipvalue",
									style : 0,
									colNo : 3
								},
								newcol1 : {
									title : "�Ǽ�ҵ��Ա",
									colName : " (select '�Ǽ�ҵ��Ա'  from dual)",
									rename : "order3",
									style : 0,
									colNo : 4
								},
								newcol2 : {
									title : "���ֱ���",
									colName : " (select riskcode from lcpol where polno=(select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1 and conttype='1')))",
									rename : "riskcode",
									style : 0,
									colNo : 9
								},
								newcol3 : {
									title : "��������",
									colName : " (select insuredname from lccont where prtno =t.MissionProp1 and conttype='1') ",
									rename : "insuredname",
									style : 0,
									colNo : 11
								},
								newcol4 : {
									title : "���ظ�ʱ��",
									colName : " ( case when (select uwstate from lccuwmaster where contno= t.missionprop1)='1' then null when (select uwstate from lccuwmaster where contno= t.missionprop1)<>'1' then (concat(concat(missionprop8,' '),missionprop9)) end) ",
									rename : "order4",
									style : 0,
									colNo : 6
								},
								newcol5 : {
									title : "�˱�״̬",
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
					html : " <div> �ܵ��� <Input class='readonly' id=PolSum readonly name=PolSum ></div>"
				},
				private : {
					query : {
						queryButton : {
						},
						arrayInfo : {
							col : {
								
								result0 : {title : " ӡˢ��  ",style : 1,colNo : 1},
								result1 : {title : "��ͬ����",style : 3},
								result2 : {title : "Ͷ����ͬ��",style : 3},
								result3 : {
									title : "�����˱���",
									style : 2,
									colNo : 3 ,
									referfunc1 : "queryAgent",
									referpara1 : "[0],[3]"
								},
								result4 : {title : "����������",style : 3},
								result5 : {title : "Ͷ���˱���",style : 3},
								result6 : {title : "Ͷ��������",style : 1,colNo : 8},
								result7 : {title : "״̬����",style : 3},
								result8 : {title : "״̬ʱ��",style : 3},
								result9 : {
									title : "�������",
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
									title : "�ʱ�����",
									style : 2,
									colNo : 5,
									refercode2 : "uwupreport",
									refercodedata2 : "0|^1|�ϱ�^4|�����¼�",
									addCondition : function(colName,value){
										return " and missionprop11 = '"+ value +"'";
									}
								},
								result11 : {
									title : "�˱�����",
									style : 3
									},
								result12 : {title : "��������",style : 3},
								result13 : {title : "�˱�Ա����",style : 3},
								result14 : {title : "��������",style : 3},
								result15 : {title : "�ֱ���־",style : 3},
								result16 : {
									title : "ɨ��ʱ��",
									style : 3
								  },
								result17 : {
									title : "�˱�״̬",
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
									title : "�˱�Ա",
									style : 3
									},
								result19 : {title : "�ϱ��˱��켣",style : 3},
								result20 : {
									title : "�˱�Ա����",
									style : 3
									},
								result21 : {title : "������",style : 3},
								result22 : {title : "�˱��ȴ�ԭ��",style : 3},
								result23 : {title : "�˱�Ա˵��",style : 3},
								result24 : {title : "�ϱ��˱�ʦ����",style : 3},
								
								newCol0 : {
									title :"��������",
									style : 2,
									colNo : 7,
									refercode1 : "salechnl",
									addCondition : function(colName,value){
										return "  and exists(select 'x' from lccont where lccont.contno = t.missionprop2 and lccont.salechnl='"+ value +"')";
									}
								},
								newCol1 : {
									title :"���ֱ���",
									style : 2,
									colNo : 2,
									refercode1 : "riskcode",
									addCondition : function(colName,value){
										return " and exists ( select 1 from lcpol where polno=(select min(polno) from lcpol where prtno = t.missionprop1 and insuredno = (select insuredno from lccont where prtno = t.missionprop1 and conttype = '1')) and riskcode = '"+ value +"') ";
									}
								},																
								newCol2 : {
									title :"������",
									style : 1,
									colNo : 9,
									refercode1 : "InsurName2",
									addCondition : function(colName,value){
										return " and exists(select 'x' from lccont where contno = t.MissionProp2 and insuredname='"+value+"')";
									}
								},
								newCol3 : {
									title :"�Ƿ���",
									style : 2,
									colNo : 10,
									refercode2 : "EnteraccState2",
									refercodedata2 : "0|^0|��^1|��",
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
									title :"�Ƿ�Ա����",
									style : 2,
									colNo : 11,
									refercode2 : "OperatorState2",
									refercodedata2 : "0|^0|��^1|��",
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
									title :"ҵ��Ա�ȼ�",
									style : 2,
									colNo : 12,
									refercode1 : "uwlevel",
									addCondition : function(colName,value){
										return " and exists(select 'x' from latree where agentcode = t.MissionProp4 and UWLevel = '"+ value +"')";
									}
								},
								newCol6 : {
									title :"���Ż���Ͷ����",
									style : 2,
									colNo : 13,
									refercode2 : "baituancom",
									refercodedata2 : "0|^0|��^1|��",
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
					resultTitle : "���˱�Ͷ����",
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
								result0 : {title : " ӡˢ��  ",style : 0,colNo : 9},
								result1 : {title : "��ͬ����",style : 3},
								result2 : {title : "Ͷ����ͬ��",style : 3},
								result3 : {title : "�����˱���",style : 3},
								result4 : {title : "����������",style : 3},
								result5 : {title : "Ͷ���˱���",style : 3},
								result6 : {title : "Ͷ��������",style : 3},
								result7 : {title : "���ظ�����",style : 3},
								result8 : {title : "���ظ�ʱ��",style : 3},
								result9 : {
									title : "�������",
									style : 0,
									colNo : 7,
									colName : "(case  when (char_length(t.missionprop10)>4) then substr(t.missionprop10,1,4) else t.missionprop10 end) ",
									rename : "managecom"
								},
								result10 : {
									title : "�ʱ�����",
									style : 0,
									colNo : 3,
									colName : "(case t.missionprop11 when '0' then '����Լ�˱�' when '1' then '�ϱ�'  when '2' then '�������ϱ�' when '3' then '�ٱ��ϱ�' when '4' then '�����¼�' end) ",
									rename : "order1"
									},
								result11 : {
									title : "�˱�����",
									style : 0,
									colNo : 1
									},
								result12 : {title : "��������",style : 3},
								result13 : {title : "�˱�Ա����",style : 3},
								result14 : {title : "��������",style : 3},
								result15 : {title : "�ֱ���־",style : 3},
								result16 : {
									title : "ɨ��ʱ��",
									style : 0,
									colNo : 4,
									colName : " (case t.missionprop17 when 'NOSCAN' then '��ɨ���' else missionprop17 end) ",
									rename : "scandate"
									},
								result17 : {
									title : "�˱�״̬",
									style : 3
									},
								result18 : {title : "�˱�Ա",style : 3},
								result19 : {title : "�ϱ��˱��켣",style : 3},
								result20 : {
									title : "�˱�Ա����",
									style : 0,
									colNo : 10,
									colName : " (case t.missionprop20 when null then t.lastoperator else t.missionprop20 end) ",
									rename : "lastusercode"
									},
								result21 : {title : "������",style : 3},
								result22 : {title : "�˱��ȴ�ԭ��",style : 3},
								result23 : {title : "�˱�Ա˵��",style : 3},
								result24 : {title : "�ϱ��˱�ʦ����",style : 3},
								
								newcol0 : {
									title : "���ֱ���",
									colName : "(select riskcode from lcpol where polno=(select min(polno) from lcpol where prtno=t.MissionProp1 and insuredno=(select insuredno from lccont where prtno=t.MissionProp1 and conttype='1')))",
									rename : "riskcode",
									style : 0,
									colNo : 8
								},
								newcol1 : {
									title : "��������",
									colName : "(select insuredname from lccont where prtno =t.MissionProp1 and conttype='1') ",
									rename : "insuredname",
									style : 0,
									colNo : 2
								},
								newcol2 : {
									title : "���ظ�ʱ��",
									colName : " (case when (select uwstate from lccuwmaster where contno= t.missionprop1)='1' then null when (select uwstate from lccuwmaster where contno= t.missionprop1)<>'1' then (concat(concat(missionprop8,' '),missionprop9)) end) ",
									rename : "order4",
									style : 0,
									colNo : 5
								},
								newcol3 : {
									title : "�˱�״̬",
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


// ������Ϣ�б�ĳ�ʼ��
/*function initPolGrid()
  {              
  	bg1 = new CGrid("fm", "PolGrid", turnPage2);
  	bg1.setActivityId("0000001100");
  	bg1.setProcessId("0000000003");
  	//����ģʽ
  	//bg1.addColumn("�˱�����,��������,�ϱ�����,�Ǽ�ҵ��Ա,ɨ��ʱ��,���ظ�ʱ��,�˱�����״̬,�������,���ֱ���,ӡˢ��,���������,VIP�ͻ�,ҵ��Ա,Ͷ������,��������,�ϱ���־,��������,�����������,�������������,�������Id,�˱�����״̬,��������,Ͷ���˺�");
  	//bg.setRadioFunction("IniteasyQueryAddClick");
  	//JSONģʽ
  	//�˱�����,��������,�ϱ�����,�Ǽ�ҵ��Ա,ɨ��ʱ��,���ظ�ʱ��,�˱�����״̬,�������,���ֱ���,
  	//ӡˢ��,���������,VIP�ͻ�,ҵ��Ա,Ͷ������,��������,�ϱ���־,��������, ����������� ,�������������,
  	//�������ID,�˱�����״̬,��������,Ͷ���˺�         

  	bg1.addColumn([//{title:'���',width:'20px'},
  	{title:'�˱�����',width:'20px'},
  	{title:'��������',width:'80px'},
  	{title:'�ϱ�����',width:'80px'},
  	{title:'�Ǽ�ҵ��Ա',width:'0px'},
  	{title:'ɨ��ʱ��',width:'150px'},
  	{title:'���ظ�ʱ��',width:'150px'},
  	{title:'�˱�����״̬',width:'40px'},
  	{title:'�������',width:'60px'},
  	{title:'���ֱ���',width:'120px'},
  	{title:'ӡˢ��',width:'120px'},
  	{title:'���������',width:'60px'},
  	{title:'VIP�ͻ�',width:'0px'},
  	{title:'ҵ��Ա',width:'0px'},
  	{title:'Ͷ������',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'�ϱ���־',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'�����������',width:'0px'},
  	{title:'�������������',width:'0px'},
  	{title:'�������Id',width:'0px'},
  	{title:'�˱�״̬',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'Ͷ���˺�',width:'0px'}
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
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�˱�����";         		//����
      iArray[1][1]="20px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="80px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ϱ�����";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="ɨ��ʱ��";         		//����
      iArray[5][1]="150px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[6]=new Array();                                                       
      iArray[6][0]="���ظ�ʱ��";         		//����                                     
      iArray[6][1]="150px";            		//�п�                                   
      iArray[6][2]=100;            			//�����ֵ                                 
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[7]=new Array();
      iArray[7][0]="�˱�����״̬";         		//����
      iArray[7][1]="20px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="���ֱ���";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="ӡˢ��";         		//����
      iArray[10][1]="120px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="���������";         		//����
      iArray[11][1]="60px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[12]=new Array();                                                       
      iArray[12][0]="VIP�ͻ�";         		//����                                     
      iArray[12][1]="40px";            		//�п�                                   
      iArray[12][2]=100;            			//�����ֵ                                 
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[13]=new Array();
      iArray[13][0]="ҵ��Ա";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[14]=new Array();
      iArray[14][0]="Ͷ������";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="��������";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 


      iArray[16]=new Array();
      iArray[16][0]="�ϱ���־";         		//����
      iArray[16][1]="0px";            		//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[17]=new Array();
      iArray[17][0]="��������";         		//����
      iArray[17][1]="0px";            		//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[18]=new Array();
      iArray[18][0]="�����������";         		//����
      iArray[18][1]="0px";            		//�п�
      iArray[18][2]=200;            			//�����ֵ
      iArray[18][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[19]=new Array();
      iArray[19][0]="�������������";         		//����
      iArray[19][1]="0px";            		//�п�
      iArray[19][2]=200;            			//�����ֵ
      iArray[19][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[20]=new Array();
      iArray[20][0]="�������Id";         		//����
      iArray[20][1]="0px";            		//�п�
      iArray[20][2]=60;            			//�����ֵ
      iArray[20][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[21]=new Array();
      iArray[21][0]="�˱�����״̬";         		//����
      iArray[21][1]="0px";            		//�п�
      iArray[21][2]=60;            			//�����ֵ
      iArray[21][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[22]=new Array();
      iArray[22][0]="��������";         		//����
      iArray[22][1]="80px";            		//�п�
      iArray[22][2]=100;            			//�����ֵ
      iArray[22][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������       
      
      iArray[23]=new Array();
      iArray[23][0]="Ͷ���˺�";         		//����
      iArray[23][1]="0px";            		//�п�
      iArray[23][2]=100;            			//�����ֵ
      iArray[23][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;
      PolGrid.loadMulLine(iArray); 
      
      PolGrid.selBoxEventFuncName = "IniteasyQueryAddClick";
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
      */
//}

// ������Ϣ�б�ĳ�ʼ��
function initGrpPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����Ͷ������";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����λ";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�˱�����״̬";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[5]=new Array();                                                       
      iArray[5][0]="�˱�����";         		//����                                     
      iArray[5][1]="80px";            		//�п�                                   
      iArray[5][2]=100;            			//�����ֵ                                 
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������Id";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[9]=new Array();
      iArray[9][0]="�ϱ�����";              //����
      iArray[9][1]="80px";                   //�п�
      iArray[9][2]=60;                      //�����ֵ
      iArray[9][3]=0;       
      
      iArray[10]=new Array();
      iArray[10][0]="�˱�����";               //����
      iArray[10][1]="60px";                 //�п�
      iArray[10][2]=60;                     //�����ֵ
      iArray[10][3]=0;                      
      
      iArray[11]=new Array();
      iArray[11][0]="�������";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[12]=new Array();
      iArray[12][0]="ҵ��Ա";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[13]=new Array();
      iArray[13][0]="Ͷ������";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[14]=new Array();
      iArray[14][0]="��������";         		//����
      iArray[14][1]="80px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[16]=new Array();
      iArray[16][0]="VIP�ͻ�";         		//����
      iArray[16][1]="80px";            		//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[17]=new Array();
      iArray[17][0]="�ϱ���־";         		//����
      iArray[17][1]="0px";            		//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������    
      
      iArray[18]=new Array();
      iArray[18][0]="��������";         		//����
      iArray[18][1]="80px";            		//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[19]=new Array();
      iArray[19][0]="ɨ������";         		//����
      iArray[19][1]="80px";            		//�п�
      iArray[19][2]=60;            			//�����ֵ
      iArray[19][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      PolGrid = new MulLineEnter( "fm" , "PolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      PolGrid.mulLineCount = 3;   
      PolGrid.displayTitle = 1;
      PolGrid.locked = 1;
      PolGrid.canSel = 1;
      PolGrid.hiddenPlus = 1;
      PolGrid.hiddenSubtraction = 1;     
      PolGrid.selBoxEventFuncName = "GrpIniteasyQueryAddClick";
      PolGrid.loadMulLine(iArray);     
      
     
      
      //��Щ����������loadMulLine����
      //PolGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}

// ������Ϣ�б�ĳ�ʼ��
/*function initAllPolGrid()
  {    
  	//alert('1');
  	bg = new CGrid("fm", "AllPolGrid",turnPage);
  	bg.setActivityId("0000001100");
  	bg.setProcessId("0000000003");
  	//bg.setDefaultOperator(null);
  	//bg.addColumn([{name:'Missionid',readonly:'3'},{name:'submissionid',readonly:'3'},{name:'activityid',readonly:'3'}]);
  	//�˱�����,���������,VIP�ͻ�,�Ǽ�ҵ��Ա,ɨ��ʱ��,���ظ�ʱ��,�˱�����״̬,�������,
  	//���ֱ���,ӡˢ��,��������,�ϱ�����,ҵ��Ա,Ͷ������,��������,�ϱ���־,��������,�����������,�������������,�������ID,��������
  	bg.addColumn([
  	{title:'�˱�����',width:'20px'},
  	{title:'���������',width:'60px'},
  	{title:'VIP�ͻ�',width:'40px'},
  	{title:'�Ǽ�ҵ��Ա',width:'100px'},
  	{title:'ɨ��ʱ��',width:'150px'},
  	{title:'���ظ�ʱ��',width:'150px'},
  	{title:'�˱�����״̬',width:'20px'},
  	{title:'�������',width:'40px'},
  	{title:'���ֱ���',width:'60px'},
  	{title:'ӡˢ��',width:'120px'},
  	{title:'��������',width:'80px'},
  	{title:'�ϱ�����',width:'0px'},
  	{title:'ҵ��Ա',width:'0px'},
  	{title:'Ͷ������',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'�ϱ���־',width:'0px'},
  	{title:'��������',width:'0px'},
  	{title:'�����������',width:'0px'},
  	{title:'�������������',width:'0px'},
  	{title:'�������Id',width:'0px'},
  	{title:'��������',width:'0px'}
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
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�˱�����";         		//����
      iArray[1][1]="20px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="���������";         		//����
      iArray[2][1]="60px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="VIP�ͻ�";         		//����
      iArray[3][1]="40px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="ɨ��ʱ��";         		//����
      iArray[5][1]="150px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[6]=new Array();                                                       
      iArray[6][0]="���ظ�ʱ��";         		//����                                     
      iArray[6][1]="150px";            		//�п�                                   
      iArray[6][2]=100;            			//�����ֵ                                 
      iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[7]=new Array();
      iArray[7][0]="�˱�����״̬";         		//����
      iArray[7][1]="20px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������";         		//����
      iArray[8][1]="40px";            		//�п�
      iArray[8][2]=200;            			//�����ֵ
      iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[9]=new Array();
      iArray[9][0]="���ֱ���";         		//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="ӡˢ��";         		//����
      iArray[10][1]="120px";            		//�п�
      iArray[10][2]=100;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[11]=new Array();
      iArray[11][0]="��������";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=100;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[12]=new Array();                                                       
      iArray[12][0]="�ϱ�����";         		//����                                     
      iArray[12][1]="0px";            		//�п�                                   
      iArray[12][2]=100;            			//�����ֵ                                 
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[13]=new Array();
      iArray[13][0]="ҵ��Ա";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[14]=new Array();
      iArray[14][0]="Ͷ������";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="��������";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 


      iArray[16]=new Array();
      iArray[16][0]="�ϱ���־";         		//����
      iArray[16][1]="0px";            		//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[17]=new Array();
      iArray[17][0]="��������";         		//����
      iArray[17][1]="0px";            		//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[18]=new Array();
      iArray[18][0]="�����������";         		//����
      iArray[18][1]="0px";            		//�п�
      iArray[18][2]=200;            			//�����ֵ
      iArray[18][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[19]=new Array();
      iArray[19][0]="�������������";         		//����
      iArray[19][1]="0px";            		//�п�
      iArray[19][2]=200;            			//�����ֵ
      iArray[19][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[20]=new Array();
      iArray[20][0]="�������Id";         		//����
      iArray[20][1]="0px";            		//�п�
      iArray[20][2]=60;            			//�����ֵ
      iArray[20][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[21]=new Array();
      iArray[21][0]="��������";         		//����
      iArray[21][1]="80px";            		//�п�
      iArray[21][2]=100;            			//�����ֵ
      iArray[21][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������       

      AllPolGrid = new MulLineEnter( "fm" , "AllPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
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

// ������Ϣ�б�ĳ�ʼ��
function initGrpAllPolGrid()
  {                               
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=30;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ӡˢ��";         		//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="����Ͷ������";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="Ͷ����λ";         		//����
      iArray[3][1]="80px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[4]=new Array();
      iArray[4][0]="�˱�����״̬";         		//����
      iArray[4][1]="100px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������            

      iArray[5]=new Array();                                                       
      iArray[5][0]="�˱�����";         		//����                                     
      iArray[5][1]="80px";            		//�п�                                   
      iArray[5][2]=100;            			//�����ֵ                                 
      iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������   
            
      iArray[6]=new Array();
      iArray[6][0]="�����������";         		//����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������������";         		//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=200;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[8]=new Array();
      iArray[8][0]="�������Id";         		//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������
  
       iArray[9]=new Array();
      iArray[9][0]="�ϱ�����";         		//����
      iArray[9][1]="100px";            		//�п�
      iArray[9][2]=60;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[10]=new Array();
      iArray[10][0]="�˱�����";               //����
      iArray[10][1]="60px";                 //�п�
      iArray[10][2]=60;                     //�����ֵ
      iArray[10][3]=0;                      
      
      iArray[11]=new Array();
      iArray[11][0]="�������";         		//����
      iArray[11][1]="80px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[12]=new Array();
      iArray[12][0]="ҵ��Ա";         		//����
      iArray[12][1]="80px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[13]=new Array();
      iArray[13][0]="Ͷ������";         		//����
      iArray[13][1]="80px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[14]=new Array();
      iArray[14][0]="��������";         		//����
      iArray[14][1]="80px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[15]=new Array();
      iArray[15][0]="�Ǽ�ҵ��Ա";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[16]=new Array();
      iArray[16][0]="VIP�ͻ�";         		//����
      iArray[16][1]="80px";            		//�п�
      iArray[16][2]=60;            			//�����ֵ
      iArray[16][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[17]=new Array();
      iArray[17][0]="�ϱ���־";         		//����
      iArray[17][1]="0px";            		//�п�
      iArray[17][2]=60;            			//�����ֵ
      iArray[17][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[18]=new Array();
      iArray[18][0]="��������";         		//����
      iArray[18][1]="80px";            		//�п�
      iArray[18][2]=60;            			//�����ֵ
      iArray[18][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      
      AllPolGrid = new MulLineEnter( "fm" , "AllPolGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
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
