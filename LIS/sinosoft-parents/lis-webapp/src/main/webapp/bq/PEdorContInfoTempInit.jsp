<%
/*******************************************************************************
 * <p>Title: Lis 6.5</p>
 * <p>Description: �п������ٱ��պ���ҵ�����ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005 Sinosoft, Co.,Ltd. All Rights Reserved</p>
 * <p>Company: �п���Ƽ��ɷ����޹�˾</p>
 * <p>WebSite: http://www.sinosoft.com.cn</p>
 *
 * @author   : pst <pengst@sinosoft.com.cn>
 * @version  : 1.00, 
 * @date     : 2008-12-3
 * @direction: �ͻ���Ҫ���ϱ��
 ******************************************************************************/
%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.util.*"%>
<%
    //���ҳ��ؼ��ĳ�ʼ����
    String tCurDate=PubFun.getCurrentDate();
%>

<script language="JavaScript">

var ContGrid;

function initForm()
{
    try
    {
        initContGrid();  
        easyQueryClickCont();
    	initContInfoPool();
        //��ʼ���ҵ��������
        fm.EdorAppDate.value=NullToEmpty("<%= tCurDate %>");  
        fm.AppType.value="IC"; //����������ɨ�蹤�����ڵ㣬MissionProp5
        //alert(fm.EdorAppDate.value);
       // initSelfGrid();
        //easyQueryClickSelf();    //��ѯ�ҵ��������
    }
    catch (ex)
    {
        alert("�� PEdorContInfoTempInit.jsp --> initForm �����з����쳣:��ʼ��������� ");
    }
}

function initContInfoPool(){
	var sql1 = "";
	if(_DBT==_DBO){
		sql1 =" and trim(defaultoperator) ='"+operator+ "' and OtherNo in (select contno from lpconttempinfo where state = '0') and ICFlag = 'IC' "+
		  " order by (select edorappdate from lpedoritem l where l.EdorAcceptNo = EdorAcceptNo and rownum=1), makedate, maketime";
	}else if(_DBT==_DBM){
		sql1 =" and trim(defaultoperator) ='"+operator+ "' and OtherNo in (select contno from lpconttempinfo where state = '0') and ICFlag = 'IC' "+
		  " order by (select edorappdate from lpedoritem l where l.EdorAcceptNo = EdorAcceptNo limit 0,1), makedate, maketime";
	}
	var config = {
			functionId : "10020002",
			public : {
				show : false 	
			},
			private : {
				query :{
					queryButton : {},
					arrayInfo : {
							col : {
							result0  : {title : " ��ȫ�����       ",style : 1,width : "145px",colNo : 1},  
							result1  : {title : " �������        ",style :3},           
							result2 : {title : "Ͷ����", style : 3 }, 
							result3  : {title : " ���Ѷ�Ӧ��       ",style : 3},  
							result4  : {title : " ������       ",style : 1,width : "130px",colNo : 2},   
							result5  : {title : " �����������           ",style : 3},            
							result6  : {title : " ����������       ",style : 3} ,          
							result7  : {title : " ���뷽ʽ       ",style : 3} ,          
							result8  : {title : " ��������       ",style : 3},           
							result9  : {title : " �������Ա      ",style : 3} ,          
							result10  : {title : " ��Ҫ���ϱ�����      ",style : 3}         
							}
						}
				},
				resultTitle : "���ڴ���ı���",
				result : {
					selBoxEventFuncName : "HighlightSelfRow",
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
								title : "�������",
								style : 0,
								colNo : 3,
								width : "130px",
								colName : "MissionProp7 ",
								rename : "other_no"
								},
							newCol1 : {
								title : "������������",
								style : 0,
								colNo : 4 ,
								width : "70px",
								colName : "(select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1)",
								rename : "edor_appdate"
							},
							newCol2 : {
								title : "��������",
								style : 0,
								colNo : 7 ,
								width : "70px",
								colName : " (select (case when edorappdate is not null then to_char(edorappdate,'yyyy-mm-dd') else '' end) from lpedoritem where edoracceptno = t.missionprop1)",
								rename : "edor_appdate1"
							},
							newCol3 : {
								title : "��ȫ״̬",
								style : 0,
								colNo : 6 ,
								width : "50px",
								colName : "(select (select codename from ldcode where codetype = 'edorstate' and code = edorstate) from lpedorapp where EdorAcceptNo = t.missionprop1)",
								rename : "edorstate_name"
							},
							newCol4 : {
								title : "��������",
								style : 0,
								colNo : 8 ,
								width : "50px",
								colName : "(select count(1) from ldcalendar where commondate > (select edorappdate from lpedoritem where edoracceptno = t.missionprop1) )",
								rename : "out_day"
							},
							newCol5 : {
								title : "¼��ʱ��",
								style : 3,
								colName : "maketime "
							},
							newCol6 : {
								title : "¼������",
								style : 3,
								colName : "makedate "
							},
							result0  : {title : " ��ȫ�����       ",style : 0,width : "145px",colNo : 1},  
							result1  : {title : " �������        ",style : 0,width : "70px",colNo : 5},           
							result2 : {title : "Ͷ����", style : 3 }, 
							result3  : {title : " ���Ѷ�Ӧ��       ",style : 3},  
							result4  : {title : " ������       ",style : 0,width : "130px",colNo : 2},   
							result5  : {title : " �����������           ",style : 3},            
							result6  : {title : " ����������       ",style : 3} ,          
							result7  : {title : " ���뷽ʽ       ",style : 3} ,          
							result8  : {title : " ��������       ",style : 3},           
							result9  : {title : " �������Ա      ",style : 3} ,          
							result10  : {title : " ��Ҫ���ϱ�����      ",style : 3}
						}
					}
				}	
			}
	};
	jQuery("#PEdorContInfoInputPool").workpool(config);
	jQuery("#privateSearch").click();
}


function initContGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="160px";
        iArray[1][2]=200;
        iArray[1][3]=3;


        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="200px";
        iArray[2][2]=100;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="Ͷ���˺���";
        iArray[3][1]="150px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="�����˺���";
        iArray[4][1]="100px";
        iArray[4][2]=100;
        iArray[4][3]=0;

        iArray[5]=new Array();
        iArray[5][0]="�Ƿ���Ҫ�����־";
        iArray[5][1]="120px";
        iArray[5][2]=100;
        iArray[5][3]=0;


        iArray[6]=new Array();
        iArray[6][0]="ȷ������";
        iArray[6][1]="80px";
        iArray[6][2]=100;
        iArray[6][3]=8;
        iArray[6][21]=3;

        iArray[7]=new Array();
        iArray[7][0]="״̬";
        iArray[7][1]="60px";
        iArray[7][2]=100;
        iArray[7][3]=0;
        
        iArray[8]=new Array();
      	iArray[8][0]="��������";
      	iArray[8][1]="70px";
      	iArray[8][2]=0;
      	iArray[8][3]=8;
      	       
      	iArray[9]=new Array();
      	iArray[9][0]="��������";
      	iArray[9][1]="50px";
      	iArray[9][2]=0;
      	iArray[9][3]=0;

        
        ContGrid = new MulLineEnter("document", "ContGrid");
        //��Щ���Ա�����loadMulLineǰ
        ContGrid.mulLineCount = 5;
        ContGrid.displayTitle = 1;
        ContGrid.locked = 1;
        ContGrid.canSel = 1;
        ContGrid.canChk = 0;
        ContGrid.hiddenPlus = 1;
        ContGrid.hiddenSubtraction = 1;
        ContGrid.selBoxEventFuncName="displayInfo";
        ContGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert("�� PEdorContInfoTempInit.jsp --> initContGrid �����з����쳣:��ʼ��������� ");
    }
}
/*
function initSelfGrid()
{
    var iArray = new Array();
    try
    {
        iArray[0]=new Array();
        iArray[0][0]="���";                    //����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
        iArray[0][1]="30px";                    //�п�
        iArray[0][2]=30;                        //�����ֵ
        iArray[0][3]=0;                         //�Ƿ���������,1��ʾ����0��ʾ������

        iArray[1]=new Array();
        iArray[1][0]="��ȫ�����";
        iArray[1][1]="145px";
        iArray[1][2]=200;
        iArray[1][3]=0;

        iArray[2]=new Array();
        iArray[2][0]="������";
        iArray[2][1]="130px";
        iArray[2][2]=200;
        iArray[2][3]=0;

        iArray[3]=new Array();
        iArray[3][0]="�������";
        iArray[3][1]="130px";
        iArray[3][2]=100;
        iArray[3][3]=0;

        iArray[4]=new Array();
        iArray[4][0]="������������";
        iArray[4][1]="80px";
        iArray[4][2]=100;
        iArray[4][3]=8;

        iArray[5]=new Array();
        iArray[5][0]="�������";
        iArray[5][1]="70px";
        iArray[5][2]=100;
        iArray[5][3]=0;
        iArray[5][21]=3;   
        
        iArray[6]=new Array();
        iArray[6][0]="�����������";
        iArray[6][1]="0px";
        iArray[6][2]=0;
        iArray[6][3]=3;

        iArray[7]=new Array();
        iArray[7][0]="�������������";
        iArray[7][1]="0px";
        iArray[7][2]=0;
        iArray[7][3]=3;

        iArray[8]=new Array();
        iArray[8][0]="�������ID";
        iArray[8][1]="0px";
        iArray[8][2]=0;
        iArray[8][3]=3;
        
        iArray[9]=new Array();
        iArray[9][0]="Ĭ�ϴ�����";
        iArray[9][1]="0px";
        iArray[9][2]=0;
        iArray[9][3]=3;
        
        iArray[10]=new Array();
        iArray[10][0]="��ȫ״̬";
        iArray[10][1]="50px";
        iArray[10][2]=100;
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
        
        SelfGrid = new MulLineEnter("document", "SelfGrid");
        //��Щ���Ա�����loadMulLineǰ
        SelfGrid.mulLineCount = 5;
        SelfGrid.displayTitle = 1;
        SelfGrid.locked = 1;
        SelfGrid.canSel = 1;
        SelfGrid.canChk = 0;
        SelfGrid.hiddenPlus = 1;
        SelfGrid.hiddenSubtraction = 1;
        SelfGrid.selBoxEventFuncName = "HighlightSelfRow";
        SelfGrid.loadMulLine(iArray);
    }
    catch(ex)
    {
        alert("�� PEdorContInfoTempInit.jsp --> initSelfGrid �����з����쳣:��ʼ��������� ");
    }
}
*/
</script>
