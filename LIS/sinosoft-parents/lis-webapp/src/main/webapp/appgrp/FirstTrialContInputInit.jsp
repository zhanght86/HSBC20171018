<script language="JavaScript">
var loadFlag=<%=tLoadFlag%>;
//��ʼ�������
function initInpBox() { 
  fm.PrtNo.value=prtNo;
  fm.ManageCom.value=ManageCom;
  fm.MissionID.value=tMissionID;
  fm.SubMissionID.value=tSubMissionID;
}

// ������ĳ�ʼ��
function initSelBox(){

}

//����ʼ��
function initForm(){
  initInpBox();
  initImpartGrid();
  initDetail();
}
// ��֪��Ϣ�б�ĳ�ʼ��
function initImpartGrid() {                               
    var iArray = new Array();
      
    try {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            		//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�ͻ���";         		//����
      iArray[1][1]="160px";            		//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="�ͻ�����";         		//����
      iArray[2][1]="120px";            		//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[3]=new Array();
      iArray[3][0]="�ͻ����ձ���";         		//����
      iArray[3][1]="200px";            		//�п�
      iArray[3][2]=200;            			//�����ֵ
      iArray[3][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������


      iArray[4]=new Array();
      iArray[4][0]="�����������";         		//����
      iArray[4][1]="0px";            		//�п�
      iArray[4][2]=200;            			//�����ֵ
      iArray[4][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 
      
      iArray[5]=new Array();
      iArray[5][0]="�������������";         		//����
      iArray[5][1]="0px";            		//�п�
      iArray[5][2]=200;            			//�����ֵ
      iArray[5][3]=3;              			//�Ƿ���������,1��ʾ����0��ʾ������ 

      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount = 0;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 1;
      GrpGrid.canChk = 0;
      GrpGrid.loadMulLine(iArray);  
    }
    catch(ex) {
      alert(ex);
    }
}

</script>
