<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
                         

<script language="JavaScript">

function initForm()
{
  try
  {    
    initGrpGrid();
    initSelfGrpGrid();
    initSexGrpGrid();
    //����Ǳ�ȫҵ���򱣴水ťʧЧ
	if("bq"==businesstype){
		divSaveRate.style.display = "none";
	}
    easyQueryClick();
  }
  catch(re)
  {
    alert("InitForm�����з����쳣:��ʼ���������!");
  }
}
function initSexGrpGrid()
{
var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�Ա�";         	//����
      iArray[1][1]="140px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ռ�ٷֱ�";         	//����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      SexGrpGrid = new MulLineEnter( "fm" , "SexGrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SexGrpGrid.mulLineCount = 2;   
      SexGrpGrid.displayTitle = 1;
      SexGrpGrid.locked = 1;
      SexGrpGrid.canSel = 0;
      SexGrpGrid.canChk = 0;
      SexGrpGrid.hiddenPlus = 1;
      SexGrpGrid.hiddenSubtraction = 1;         
      SexGrpGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }

}
function initSelfGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="�����";         	//����
      iArray[1][1]="140px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ռ�ٷֱ�";         	//����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      SelfGrpGrid = new MulLineEnter( "fm" , "SelfGrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      SelfGrpGrid.mulLineCount = 9;   
      SelfGrpGrid.displayTitle = 1;
      SelfGrpGrid.locked = 1;
      SelfGrpGrid.canSel = 0;
      SelfGrpGrid.canChk = 0;
      SelfGrpGrid.hiddenPlus = 1;
      SelfGrpGrid.hiddenSubtraction = 1;         
      SelfGrpGrid.loadMulLine(iArray);  

      }
      catch(ex)
      {
        alert(ex);
      }
}

function initGrpGrid()
  {     
                             
    var iArray = new Array();
      
      try
      {
      iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";            	//�п�
      iArray[0][2]=10;            			//�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[1]=new Array();
      iArray[1][0]="ְҵ���";         	//����
      iArray[1][1]="140px";            	//�п�
      iArray[1][2]=170;            			//�����ֵ
      iArray[1][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

      iArray[2]=new Array();
      iArray[2][0]="ռ�ٷֱ�";         	//����
      iArray[2][1]="120px";            	//�п�
      iArray[2][2]=100;            			//�����ֵ
      iArray[2][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������

 
      GrpGrid = new MulLineEnter( "fm" , "GrpGrid" ); 
      //��Щ���Ա�����loadMulLineǰ
      GrpGrid.mulLineCount = 4;   
      GrpGrid.displayTitle = 1;
      GrpGrid.locked = 1;
      GrpGrid.canSel = 0;
      GrpGrid.canChk = 0;
      GrpGrid.hiddenPlus = 1;
      GrpGrid.hiddenSubtraction = 1;          
      GrpGrid.loadMulLine(iArray);  
      
      
      //��Щ����������loadMulLine����
      //GrpGrid.setRowColData(1,1,"asdf");
      }
      catch(ex)
      {
        alert(ex);
      }
}


</script>
