<%
//�������ƣ�CardProposalSignInit.jsp
//�����ܣ�
//�������ڣ�2006-12-05 17:30:36
//������  ��CR
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
<SCRIPT src="../common/javascript/Common.js"></SCRIPT>
<%
     //���ҳ��ؼ��ĳ�ʼ����
%>                            

<script language="JavaScript">

     var turnPage = new turnPageClass();
// �����ĳ�ʼ��������¼���֣�
function getAddFeeReason(){
    var tSelNo = AddFeeGrid.getSelNo()-1;
    var tProposalNo = AddFeeGrid.getRowColData(tSelNo,15);
    if( tSelNo <0)
    {
       alert("��ѡ��һ����Ϣ��");
       return;
    }else{
//       var tSql = "select addpremreason from lcuwmaster where proposalno = '"+tProposalNo+"'";
       var tSql = "";
       var sqlid1="RiskAddFeeQueryInitSql1";
		var mySql1=new SqlClass();
		mySql1.setResourceName("app.RiskAddFeeQueryInitSql"); 
		mySql1.setSqlId(sqlid1);
		mySql1.addSubPara(tProposalNo);
		tSql=mySql1.getString();	
       var tAddReason = easyExecSql(tSql);
       if(tAddReason !=null)
           fm.AddReason.value = tAddReason;
    }
}
function queryFee(tContNo,tInsuredNo){
    //��ʼ����ѯ�ӷ�����
/**
	var tSQL = "select b.riskcode,(select riskname from lmriskapp where riskcode=b.riskcode),a.dutycode, "
	         + " a.payplantype,a.addfeedirect,a.suppriskscore,a.prem,'',a.paystartdate,a.payenddate,'',a.polno,"
	         + " c.mainpolno,a.payplancode,c.proposalno "
	         + " from lcprem a,lmriskduty b,lcpol c  where a.dutycode=b.dutycode and a.polno=c.polno"
	         + " and c.contno='"+tContNo+"' and c.insuredno='"+tInsuredNo+"' "
	         + " and a.payplancode like '000000%%' ";*/
	 var tSQL = "";
     var sqlid2="RiskAddFeeQueryInitSql2";
		var mySql2=new SqlClass();
		mySql2.setResourceName("app.RiskAddFeeQueryInitSql"); 
		mySql2.setSqlId(sqlid2);
		mySql2.addSubPara(tContNo);
		mySql2.addSubPara(tInsuredNo);
		tSQL=mySql2.getString();
	 turnPage.strQueryResult  = easyQueryVer3(tSQL, 1, 0, 1);          
	//�ж��Ƿ��ѯ�ɹ�
  if (turnPage.strQueryResult){
     turnPage.queryModal(tSQL,AddFeeGrid);
  }else{
      alert("û�мӷ���Ϣ��");
  }
}
                          
function initForm(tContNo,tInsuredNo)
{
    try
    {
        initAddFeeGrid();
        queryFee(tContNo,tInsuredNo);
    }
    catch(re)
    {
        alert("RiskAddFeeQueryInit.jsp-->InitForm�����з����쳣:��ʼ���������!");
    }
}

// ������Ϣ�б�ĳ�ʼ��
function initAddFeeGrid()
{                               
      var iArray = new Array();
      try
      {
          iArray[0]=new Array();
      iArray[0][0]="���";         			//����������Ϊ˳��ţ����������壬���Ҳ���ʾ��
      iArray[0][1]="30px";         			//�п�
      iArray[0][2]=10;          			  //�����ֵ
      iArray[0][3]=0;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
//���ֱ���,��������,�ӷ�����,�ӷ����,�ӷѷ�ʽ,����,�ӷѽ��,�ӷ�ԭ��,����,ֹ��,�·�״̬,������,���ձ�����
   		iArray[1]=new Array();
      iArray[1][0]="���ֱ���";    	        //����
      iArray[1][1]="0px";            		//�п�
      iArray[1][2]=100;            			//�����ֵ
      iArray[1][3]=1;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
     
      
	  iArray[2]=new Array();
      iArray[2][0]="��������";         		//����
      iArray[2][1]="150px";            		//�п�
      iArray[2][2]=100;    //�����ֵ
      iArray[2][3]=2;         			
  


      iArray[3]=new Array();
      iArray[3][0]="�ӷ����α���";    	        //����
      iArray[3][1]="0px";            		//�п�
      iArray[3][2]=100;            			//�����ֵ
      iArray[3][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

		  iArray[4]=new Array();
      iArray[4][0]="�ӷ����";    	        //����
      iArray[4][1]="80px";            		//�п�
      iArray[4][2]=100;            			//�����ֵ
      iArray[4][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[4][10] = "PlanPay";
      iArray[4][11] = "0|^01|�����ӷ�||^02|ְҵ�ӷ�|03|^03|��ס�ؼӷ�|03|^04|���üӷ�|03|";
      iArray[4][12] = "4|5|6";
      iArray[4][13] = "0|2|3";
      iArray[4][19] = 1;


		  iArray[5]=new Array();
      iArray[5][0]="�ӷѷ�ʽ";    	        //����
      iArray[5][1]="80px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[5][10] = "AddFeeMethod";
      iArray[5][11] = "0|^01|��EMֵ|^02|�����ѱ���|^03|������|";
      iArray[5][12] = "5|6";
      iArray[5][13] = "0|2";
      iArray[5][19] = 1;

/*
			iArray[5]=new Array();
      iArray[5][0]="�ӷѷ�ʽ";         		//����
      iArray[5][1]="60px";            		//�п�
      iArray[5][2]=100;            			//�����ֵ
      iArray[5][3]=2;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
      iArray[5][4] = "addfeetypemethod";
      iArray[5][5] = "5";
      iArray[5][6] = "0";
*/      
			iArray[6]=new Array();
      iArray[6][0]="����/����";         		//����
      iArray[6][1]="80px";            		//�п�
      iArray[6][2]=100;            			//�����ֵ
      iArray[6][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

			iArray[7]=new Array();
      iArray[7][0]="�ӷѽ��";         		//����
      iArray[7][1]="80px";            		//�п�
      iArray[7][2]=100;            			//�����ֵ
      iArray[7][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
			iArray[7][7]="AutoCalAddFee";

      iArray[8]=new Array();
      iArray[8][0]="�ӷ�ԭ��";    	        //����
      iArray[8][1]="80px";            		//�п�
      iArray[8][2]=100;            			//�����ֵ
      iArray[8][3]=0;                       //�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
     

      iArray[9]=new Array();
      iArray[9][0]="�ӷ�����";         		//����
      iArray[9][1]="80px";            		//�п�
      iArray[9][2]=100;            			//�����ֵ
      iArray[9][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[10]=new Array();
      iArray[10][0]="�ӷ�ֹ��";         		//����
      iArray[10][1]="80px";            		//�п�
      iArray[10][2]=60;            			//�����ֵ
      iArray[10][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[11]=new Array();
      iArray[11][0]="�·�״̬";         		//����
      iArray[11][1]="0px";            		//�п�
      iArray[11][2]=60;            			//�����ֵ
      iArray[11][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[12]=new Array();
      iArray[12][0]="���ֺ�";         		//����
      iArray[12][1]="0px";            		//�п�
      iArray[12][2]=60;            			//�����ֵ
      iArray[12][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[13]=new Array();
      iArray[13][0]="�������ֺ�";         		//����
      iArray[13][1]="0px";            		//�п�
      iArray[13][2]=60;            			//�����ֵ
      iArray[13][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��

      iArray[14]=new Array();
      iArray[14][0]="�ɷѼƻ�����";         		//����
      iArray[14][1]="0px";            		//�п�
      iArray[14][2]=60;            			//�����ֵ
      iArray[14][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
    
      iArray[15]=new Array();
      iArray[15][0]="Ͷ�������ֺ�";         		//����
      iArray[15][1]="0px";            		//�п�
      iArray[15][2]=60;            			//�����ֵ
      iArray[15][3]=1;              			//�Ƿ���������,1��ʾ����0��ʾ������ 2��ʾ����ѡ��
    
          AddFeeGrid = new MulLineEnter( "fm" , "AddFeeGrid" ); 
          AddFeeGrid.mulLineCount = 0;   
          AddFeeGrid.displayTitle = 1;
          AddFeeGrid.hiddenPlus = 1;
          AddFeeGrid.hiddenSubtraction = 1;
          AddFeeGrid.canSel = 1;
          AddFeeGrid.canChk = 0;
          AddFeeGrid.selBoxEventFuncName = "getAddFeeReason";
          AddFeeGrid.loadMulLine(iArray);  
      }
      catch(ex)
      {
        alert(ex);
      }
}

</script>
