<% 
//�������ƣ�WFEdorAppInit.jsp
//�����ܣ���ȫ������-��ȫ����
//�������ڣ�2005-04-27 11:49:22
//������  ��zhangtao
//���¼�¼��  ������    ��������     ����ԭ��/����
//
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>

<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

function initInpBox()
{ 

  try
  {                                   
	//��ѯ�����ÿ�
    //document.all('PrtNo').value = '';
   // document.all('ManageCom').value = '';
   // document.all('InputDate').value = '';
  }
  catch(ex)
  {
    alert("WFEdorAppInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
  }      
}
                            

function initForm()
{
  try
  {    
    initInpBox();
    initWFEdorAppPool();
   // initAllGrid();  //��ʼ����������
  //  initSelfGrid(); //��ʼ���ҵ��������
  //  easyQueryClickSelf();  //��ѯ�ҵ��������
  }
  catch(re)
  {
    alert("WFEdorAppInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re);
  }
}
function initWFEdorAppPool(){
	var sql1 = "";
	var sql2 = "";
	var sql3 = "";
	var sql4 = "";
	var sql5 = "";
	var sql6 = "";
	if(_DBT==_DBO){
		sql1 = "  and defaultoperator is null and manageCom like '"  + manageCom + "%%' order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime";
	}else if(_DBT==_DBM){
		sql1 = "  and defaultoperator is null and manageCom like '"  + manageCom + "%%' order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime";
	}
	 if(_DBT==_DBO){
		 sql2 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd')  else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
	 }else if(_DBT==_DBM){
		 sql2 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd')  else '' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) ";
	 }
	if(_DBT==_DBO){
		sql3 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y') ";
	}else if(_DBT==_DBM){
		sql3 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y') ";
	}
	if(_DBT==_DBO){
		sql4 = "  and trim(defaultoperator) ='"+operator+"'  order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo and rownum = 1), makedate, maketime";
	}else if(_DBT=_DBM){
		sql4 = "  and trim(defaultoperator) ='"+operator+"'  order by (select edorappdate from lpedoritem l  "+
		  " where l.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime";
	}
	if(_DBT==_DBO){
		sql5 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) ";
	}else if(_DBT==_DBM){
		sql5 = " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) ";
	}
	if(_DBT==_DBO){
		sql6 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 and rownum = 1) and workdateflag = 'Y') ";
	}else if(_DBT==_DBM){
		sql6 = " (select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1 limit 0,1) and workdateflag = 'Y') ";
	}
		var config = {
			functionId : "10020001",
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							newcol0:{ 
								  title:"�������",//�еı���
								  colNo : 1,
								  style : 2,
								  width : "80px",
								  colName:"MissionProp2",
								  refercode1:"station",
								  addCondition:function (colName,value){//���ص�sql����Ϊlike
									return " and " + colName + " like '" + value + "%' ";
								  }
								  }, 
							result0  : {title : " ��ȫ�����       ",style : 3},  
							result1  : {title : " �������        ",style : 3},            
							result2  : {title : " ɨ��Ա   ",style : 3},  
							result3  : {title : " ɨ������       ",width : "80px", style : 8,colNo : 2},  
							result4  : {title : " ��֤����      ",style : 3}, 
							result5  : {title : " ��֤��       ",style : 3} 
						}
					}
				},
				resultTitle : "��������",
				result : {
					selBoxEventFuncName : "HighlightAllRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : sql1
					},
					mulLineCount : 5,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "¼������",
								style : 0,
								colNo : 3 ,
								width : "120px",
								colName : "makedate "
							},
							newCol1 : {
								title : "����Ա",
								style : 0,
								colNo : 4 ,
								width : "60px",
								colName : "createoperator "
							},
							newCol2 : {
								title : "����Ա����",
								style : 0,
								colNo : 4 ,
								width : "60px",
								colName : " (select username from lduser where usercode = createoperator) ",
								rename : "createoperator_name"
					
							},
							newCol3 : {
								title : "��������",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : sql2,
								rename : "edor_appdate"
					
							},
							newCol4 : {
								title : "��������",
								style : 0,
								colNo : 6 ,
								width : "50px",
								colName : sql3,
								rename : "out_day"
							},
							newCol5 : {
								title : "����Ա",
								style : 3,
								colName : "defaultoperator "
							},
							newCol6 : {
								title : "¼��ʱ��",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " ��ȫ�����       ",width : "160px", style : 0,colNo : 1},  
							result1  : {title : " �������        ",width : "120px",  style : 0,colNo : 2},            
							result2  : {title : " ɨ��Ա   ",style : 3},  
							result3 : {title : " ɨ������       ",style : 3},  
							result4  : {title : " ��֤����      ",style : 3}, 
							result5  : {title : " ��֤��       ",style : 3}         
						}
					}
				}	
			},
			private : {
				query :{
					show : false
				},
				resultTitle : "�ҵ�����",
				result : {
					selBoxEventFuncName : "HighlightSelfRow",
					selBoxEventFuncParam : "",
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : sql4
					},
					mulLineCount : 5,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "¼������",
								style : 0,
								colNo : 3 ,
								width : "120px",
								colName : "makedate "
							},
							newCol1 : {
								title : "��ȫ����",
								style : 0,
								colNo : 4 ,
								width : "80px",
								colName : "case (select count(0) from lbmission where activityid in (select activityid from lwactivity where functionid = '10020005') and missionid = t.missionid) when 0 then '��ȫ����' else '�����޸�' end ",
								rename : "edor_operate_name"
							},
							newCol2 : {
								title : "��������",
								style : 0,
								colNo : 5 ,
								width : "70px",
								colName : sql5,
								rename : "edor_appdate"
					
							},
							newCol3 : {
								title : "��������",
								style : 0,
								colNo : 6 ,
								width : "50px",
								colName : sql6,
								rename : "out_day"
							},
							newCol4 : {
								title : "����Ա",
								style : 3,
								colName : "defaultoperator "
							},
							newCol5 : {
								title : "¼��ʱ��",
								style : 3,
								colName : "maketime "
							},
							result0  : {title : " ��ȫ�����       ",width : "160px", style : 1,colNo : 1},  
							result1  : {title : " �������        ",width : "120px",  style : 1,colNo : 2},            
							result2  : {title : " ɨ��Ա   ",style : 3},  
							result3 : {title : " ɨ������       ",style : 3},  
							result4  : {title : " ��֤����      ",style : 3}, 
							result5  : {title : " ��֤��       ",style : 3}         
						}
					}
				}
			},
			midContent : { 
			id : 'MidContent',
			show : true,
			<!--html : '<INPUT class=cssButton id="riskbutton" VALUE="��  ��" TYPE=button onClick="applyMission()">'-->
			html : '<a id="riskbutton" href="javascript:void(0);" class="button" onClick="applyMission();">��    ��</a>'
			}
	};
	jQuery("#WFEdorInputPool").workpool(config);
	jQuery("#privateSearch").click();
}
/*
function initAllGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="��֤��";         	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=150;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[2]=new Array();
      iArray[2][0]="��ȫ�����";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=170;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�������";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="¼������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[5]=new Array();
      iArray[5][0]="�����������";         	//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������������";        //����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������Id";         	//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  
      
      iArray[8]=new Array();
      iArray[8][0]="��֤����";         	//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������       
           
      //add by jiaqiangli 2009-05-21 ���Ӳ���Ա��
      iArray[9]=new Array();
      iArray[9][0]="����Ա����";         	//����
      iArray[9][1]="60px";            		//�п�
      iArray[9][2]=200;            			//�����ֵ
      iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      //add by jiaqiangli 2009-05-21 ���Ӳ���Ա��
      iArray[10]=new Array();
      iArray[10][0]="����Ա����";         	//����
      iArray[10][1]="60px";            		//�п�
      iArray[10][2]=200;            			//�����ֵ
      iArray[10][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[11]=new Array();
      iArray[11][0]="��������";
      iArray[11][1]="70px";
      iArray[11][2]=0;
      iArray[11][3]=8;
      
      iArray[12]=new Array();
      iArray[12][0]="��������";
      iArray[12][1]="50px";
      iArray[12][2]=0;
      iArray[12][3]=0;
    
      AllGrid = new MulLineEnter( "document" , "AllGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      AllGrid.mulLineCount = 5;   
      AllGrid.displayTitle = 1;
      AllGrid.locked = 1;
      AllGrid.canSel = 1;
      AllGrid.canChk = 0;
      AllGrid.selBoxEventFuncName = "HighlightAllRow";
      AllGrid.hiddenPlus = 1;
      AllGrid.hiddenSubtraction = 1;        
      AllGrid.loadMulLine(iArray);  

      //��Щ����������loadMulLine����

	}
	catch(ex)
	{
		alert(ex);
	}
}

//
function initSelfGrid()
{                                  
	var iArray = new Array();      
	try
	{
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
      
      iArray[1]=new Array();
      iArray[1][0]="��֤��";         	//����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=1500;            			//�����ֵ
      iArray[1][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[2]=new Array();
      iArray[2][0]="��ȫ�����";         		//����
      iArray[2][1]="160px";            		//�п�
      iArray[2][2]=170;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�������";         		//����
      iArray[3][1]="120px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[4]=new Array();
      iArray[4][0]="¼������";         		//����
      iArray[4][1]="120px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=8;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[5]=new Array();
      iArray[5][0]="�����������";         	//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[6]=new Array();
      iArray[6][0]="�������������";        //����
      iArray[6][1]="0px";            		//�п�
      iArray[6][2]=200;            			//�����ֵ
      iArray[6][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[7]=new Array();
      iArray[7][0]="�������Id";         	//����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������      
      
      iArray[8]=new Array();
      iArray[8][0]="��֤����";         	//����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������  


        iArray[9]=new Array();
        iArray[9][0]="Ĭ�ϴ�����";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="��ȫ����";
        iArray[10][1]="80px";
        iArray[10][2]=0;
        iArray[10][3]=0;
        
      iArray[11]=new Array();
      iArray[11][0]="��������";
      iArray[11][1]="70px";
      iArray[11][2]=0;
      iArray[11][3]=8;
      
      iArray[12]=new Array();
      iArray[12][0]="��������";
      iArray[12][1]="50px";
      iArray[12][2]=0;
      iArray[12][3]=0;
      
      SelfGrid = new MulLineEnter( "document" , "SelfGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrid.mulLineCount = 5;   
      SelfGrid.displayTitle = 1;
      SelfGrid.locked = 1;
      SelfGrid.canSel = 1;
      SelfGrid.selBoxEventFuncName = "HighlightSelfRow";
      SelfGrid.canChk = 0;
      SelfGrid.hiddenPlus = 1;
      SelfGrid.hiddenSubtraction = 1;        
      SelfGrid.loadMulLine(iArray);  

      //��Щ����������loadMulLine����

	}
	catch(ex)
	{
		alert(ex);
	}
}
*/
</script>