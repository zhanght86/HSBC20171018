<%@include file="../common/jsp/UsrCheck.jsp"%>
<%
//�������ƣ�UWHealthInit.jsp
//�����ܣ���������Լɨ�������¼��
//�������ڣ�
//������  ��
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<script language="JavaScript">
// �����ĳ�ʼ��������¼���֣�
function initInpBox()
{
	try
	{
		// ������ѯ����
		document.all('QContNo').value = '';
		document.all('QPrtSeq').value = '';
		document.all('QHandler').value = '';
	}
	catch(ex)
	{
		alert("��UWHealthInit.jsp-->InitInpBox�����з����쳣:��ʼ���������!");
	}
}

function initForm()
{
	try
	{
		initTr();
		initInpBox();
		//initGrpGrid();
		initLLHealthPool();
	}
	catch(re)
	{
		alert("��UWHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
	}
}

//modify by lzf
function initLLHealthPool(){
	var config = {
			//activityId : "0000005534",
			functionId : "10030024",
			//operator : operator,
			public : {
				query : {
					queryButton : {},
					arrayInfo : {
						col : {
							result0 : {title : "  ӡˢ��   ",style : 3},
							result1 : {title : "  ��ͬ����   ",style : 1,colNo : 1},
							result2 : {title : "֪ͨ����ˮ��",style :1,colNo : 2},
							result3 : {
								title : "���������",
								style : 3
								},
							result4 : {
								title : " ������ ",
								style : 1,
								colNo : 4
								},
							
							result5 : {
								title : " ��������  ",
								style : 8,
								colNo : 3
								},
							result6 : {
								title : "�ⰸ��",
								style : 3
							},
							result7 : {title : "���κ�",style : 3}
						}
					}
				},
				resultTitle : "������Ϣ",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"5" : " and exists (select distinct 1 from lccont where lccont.contno=newcontno and lccont.managecom like '"+comcode+"%') order by PrtNo"
					},
					mulLineCount : 10,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newCol0 : {
								title : "",
								style : 3,
								colName : "missionprop2",
								rename : "newcontno"
							},
							result0 : {title : "  ӡˢ��   ",style : 3},
							result1 : {title : "  ��ͬ����   ",style : 1,colNo : 1},
							result2 : {title : "֪ͨ����ˮ��",style :1,colNo : 2},
							result3 : {
								title : "���������",
								style : 3
								},
							result4 : {
								title : " ������  ",
								style : 1,
								colNo : 4
								},
							
							result5 : {
								title : " ��������  ",
								style : 1,
								colNo : 3
								},
							result6 : {
								title : "�ⰸ��",
								style : 3
							},
							result7 : {title : "���κ�",style : 3}
						}
					}
				}
			},
			private : {
				show : false
			}
	};
	jQuery("#LLHealthPool").workpool(config);
}

// ������Ϣ�б�ĳ�ʼ��
/*function initGrpGrid()
{
	var iArray = new Array();
	try
	{
		iArray[0]=new Array();
		iArray[0][0]="���";
		iArray[0][1]="30px";
		iArray[0][2]=10;
		iArray[0][3]=0;

		iArray[1]=new Array();
		iArray[1][0]="��ͬ��";
		iArray[1][1]="120px";
		iArray[1][2]=170;
		iArray[1][3]=0;

		iArray[2]=new Array();
		iArray[2][0]="���֪ͨ����ˮ��";
		iArray[2][1]="120px";
		iArray[2][2]=100;
		iArray[2][3]=0;

		iArray[3]=new Array();
		iArray[3][0]="��������";
		iArray[3][1]="80px";
		iArray[3][2]=200;
		iArray[3][3]=0;

		iArray[4]=new Array();
		iArray[4][0]="������";         	
		iArray[4][1]="100px";           
		iArray[4][2]=100;            	
		iArray[4][3]=0;              	
		
		iArray[5]=new Array();
		iArray[5][0]="�����������";  
		iArray[5][1]="0px";
		iArray[5][2]=200;
		iArray[5][3]=3;

		iArray[6]=new Array();
		iArray[6][0]="�������������";
		iArray[6][1]="0px";
		iArray[6][2]=200;
		iArray[6][3]=3;

      iArray[7]=new Array();
      iArray[7][0]="�������Id";      //����
      iArray[7][1]="0px";            		//�п�
      iArray[7][2]=60;            			//�����ֵ
      iArray[7][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      iArray[8]=new Array();
      iArray[8][0]="����˿ͻ�����";      //����
      iArray[8][1]="0px";            		//�п�
      iArray[8][2]=60;            			//�����ֵ
      iArray[8][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
    
      
		GrpGrid = new MulLineEnter( "fm" , "GrpGrid" );
		//��Щ���Ա�����loadMulLineǰ
		GrpGrid.mulLineCount = 10;
		GrpGrid.displayTitle = 1;
		GrpGrid.locked = 1;
		GrpGrid.canSel = 1;
		GrpGrid.canChk = 0;
		GrpGrid.hiddenSubtraction = 1;
		GrpGrid.hiddenPlus = 1;
		GrpGrid.loadMulLine(iArray);
	}
	catch(ex)
	{
		alert(ex);
	}
}
*/
</script>
