<%

//�������ƣ�GrpEdorTypeBSInput.jsp
//�����ܣ������ڼ��жϲ���
//�������ڣ�2006-04-19 11:10:36
//������  �������

%>
<!--�û�У����-->
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

function initGrpEdor()
{
	document.all('EdorAcceptNo').value = top.opener.document.all('EdorAcceptNo').value;
  document.all('EdorNo').value = top.opener.document.all('EdorNo').value;
  document.all('GrpContNo').value = top.opener.document.all('OtherNo').value;
  document.all('EdorType').value = top.opener.document.all('EdorType').value;
  document.all('EdorTypeName').value = top.opener.document.all('EdorTypeName').value;
  var strSQL = "select max(startdate) from lcgrpcontstate where grpcontno = '"+document.all('GrpContNo').value+"' and enddate is null and statetype='Available' and state = '1'";
  var trr = easyExecSql(strSQL);
  if(trr)
	{
			fm.OldEndDate.value = trr[0][0];
	}else {
		alert("��ѯ��ͬ�ж�����ʧ��!");
	}  
}

function initForm() 
{
 
  initGrpEdor();
  QueryEdorInfo();
	initLCGrpPolGrid();
	queryClick();
	initLCGrpContStateGrid();
	GrpPolSel();
}

//�����������б�
function initLCGrpPolGrid()
{
    var iArray = new Array();
      
      try
      {
		    iArray[0]=new Array();
		    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		    iArray[0][1]="30px";         			//�п�
		    iArray[0][2]=10;          				//�����ֵ
		    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[1]=new Array();
		    iArray[1][0]="���屣����";    				//����1
		    iArray[1][1]="100px";            		//�п�
		    iArray[1][2]=100;            			//�����ֵ
		    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[2]=new Array();
		    iArray[2][0]="�������ֺ�";         			//����2
		    iArray[2][1]="100px";            		//�п�
		    iArray[2][2]=100;            			//�����ֵ
		    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[3]=new Array();
		    iArray[3][0]="���ֺ���";         			//����8
		    iArray[3][1]="60px";            		//�п�
		    iArray[3][2]=100;            			//�����ֵ
		    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[4]=new Array();
		    iArray[4][0]="�ŵ�����";         			//����5
		    iArray[4][1]="100px";            		//�п�
		    iArray[4][2]=100;            			//�����ֵ
		    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
		
		    iArray[5]=new Array();
		    iArray[5][0]="��Ч����";         		//����6
		    iArray[5][1]="60px";            		//�п�
		    iArray[5][2]=100;            			//�����ֵ
		    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[6]=new Array();
		    iArray[6][0]="�ж�����";         		//����6
		    iArray[6][1]="60px";            		//�п�
		    iArray[6][2]=100;            			//�����ֵ
		    iArray[6][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

		    iArray[7]=new Array();
		    iArray[7][0]="������ֹ����";         		//����6
		    iArray[7][1]="60px";            		//�п�
		    iArray[7][2]=100;            			//�����ֵ
		    iArray[7][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������		

		    iArray[8]=new Array();
		    iArray[8][0]="�ָ�����";         		//����6
		    iArray[8][1]="60px";            		//�п�
		    iArray[8][2]=100;            			//�����ֵ
		    iArray[8][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
        
        iArray[9]=new Array();
		    iArray[9][0]="�ָ��󱣵���ֹ����";         		//����6
		    iArray[9][1]="80px";            		//�п�
		    iArray[9][2]=100;            			//�����ֵ
		    iArray[9][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		    

	      LCGrpPolGrid = new MulLineEnter( "fm" , "LCGrpPolGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      LCGrpPolGrid.mulLineCount = 0;   
	      LCGrpPolGrid.displayTitle = 1;
		    LCGrpPolGrid.canSel = 0;
	      LCGrpPolGrid.hiddenPlus = 1;
	      LCGrpPolGrid.hiddenSubtraction = 1;
	      LCGrpPolGrid.loadMulLine(iArray);  
	      //LCGrpPolGrid.selBoxEventFuncName = "GrpPolSel";
	      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initLCGrpContStateGrid()
{
    var iArray = new Array();
      
      try
      {
		    iArray[0]=new Array();
		    iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
		    iArray[0][1]="30px";         			//�п�
		    iArray[0][2]=10;          				//�����ֵ
		    iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[1]=new Array();
		    iArray[1][0]="���屣����";    				//����1
		    iArray[1][1]="100px";            		//�п�
		    iArray[1][2]=100;            			//�����ֵ
		    iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[2]=new Array();
		    iArray[2][0]="�������ֺ�";         			//����2
		    iArray[2][1]="100px";            		//�п�
		    iArray[2][2]=100;            			//�����ֵ
		    iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[3]=new Array();
		    iArray[3][0]="״̬";         			//����8
		    iArray[3][1]="60px";            		//�п�
		    iArray[3][2]=100;            			//�����ֵ
		    iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		
		    iArray[4]=new Array();
		    iArray[4][0]="״̬��ʼʱ��";         			//����5
		    iArray[4][1]="80px";            		//�п�
		    iArray[4][2]=100;            			//�����ֵ
		    iArray[4][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������,2��ʾ��������
		
		    iArray[5]=new Array();
		    iArray[5][0]="״̬����ʱ��";         		//����6
		    iArray[5][1]="80px";            		//�п�
		    iArray[5][2]=100;            			//�����ֵ
		    iArray[5][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������
		

	      LCGrpContStateGrid = new MulLineEnter( "fm" , "LCGrpContStateGrid" ); 
	      //��Щ���Ա�����loadMulLineǰ
	      LCGrpContStateGrid.mulLineCount = 0;   
	      LCGrpContStateGrid.displayTitle = 1;
		    //LCGrpPolGrid.canChk = 0;
		    LCGrpContStateGrid.canSel = 0;
	      LCGrpContStateGrid.hiddenPlus = 1;
	      LCGrpContStateGrid.hiddenSubtraction = 1;
	      LCGrpContStateGrid.loadMulLine(iArray);  
	      //LCGrpContStateGrid.selBoxEventFuncName = "GrpPolSel";
	      //��Щ����������loadMulLine����

      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
