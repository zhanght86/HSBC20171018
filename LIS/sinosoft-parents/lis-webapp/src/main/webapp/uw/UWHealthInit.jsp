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
		initUWHealthPool();
	}
	catch(re)
	{
		alert("��UWHealthInit.jsp-->InitForm�����з����쳣:��ʼ���������!"+re.name +":"+re.message);
	}
}

//add by lzf 2013-03-22
function initUWHealthPool(){
	var config = {
			functionId : "10010036",
			//activityId : "0000001121",
			operator : operator,
			public : {
				id : "UWHealthPool",
				show : true,
				query : {
					arrayInfo : {
						col : {
							
							result0 : {title : " ӡˢ��",verify : "ӡˢ��|num&len=14" ,colNo : 1 ,style : 1},
							result1 : {title : "��ͬ��",verify : "��ͬ��|num&len=14" ,style : 3},
							result2 : {title : "֪ͨ����ˮ��",colNo : 2 ,style : 1},
							result3 : {title : "���������",style : 3},
							result4 : {title : "������",colNo : 4 ,style : 1},
							result5 : {title : "��������",colNo : 3 ,style : 8}
						}
					}
				},
				resultTitle : "������Ϣ",
				result : {
					condition : {
						"0" : false,
						"1" : false,
						"2" : false,
						"3" : false,
						"4" : true,
						"5" : "and managecom like'"
							+ comcode
							+ "%'  order by contno"
					},
					mulLineCount : 0,
					arrayInfo : {
						col : {
							col5 : "0",
							col6 : "0",
							col7 : "0",
							newcol0 : {
								title : "�������",
								colName : "(select a.managecom from lccont a where a.contno= t.missionprop2) ",
								rename : "managecom",
								style : "3"
							},
							result0 : {title : "ӡˢ��",verify : "ӡˢ��|num&len=14" ,style : "3"},
							result1 : {title : "  ��ͬ��  ",verify : "��ͬ��|num&len=14" ,colNo : 1 ,style : "1"},
							result2 : {title : "���֪ͨ����ˮ��",colNo : 2 ,style : "1"},
							result3 : {title : "���������",style : "3"},
							result4 : {title : " ������",colNo : 4 ,style : "1"},
							result5 : {title : " ��������  ",colNo : 3 ,style : "8"}
						}
						
					}
				}
			},
			private : {show : false}			
	};
	jQuery("#UWHealthPool").workpool(config);
}
// end by lzf 2013-03-22

// ������Ϣ�б�ĳ�ʼ��
/**function initGrpGrid()
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
		iArray[3][3]=8;

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
}*/
</script>
