<%
//�������ƣ�TempFinFeeSave.jsp
//�����ܣ�
//�������ڣ�2009-11-14
//������  ���ű�
//���¼�¼��  ������    ��������     ����ԭ��/����
%>
<!--�û�У����-->
<%@include file="../common/jsp/UsrCheck.jsp"%>
  <%@page import="com.sinosoft.utility.*"%>
  <%@page import="com.sinosoft.lis.schema.*"%>
  <%@page import="com.sinosoft.lis.vschema.*"%>
  <%@page import="com.sinosoft.lis.db.*"%>
  <%@page import="com.sinosoft.lis.vdb.*"%>
  <%@page import="com.sinosoft.lis.sys.*"%>
  <%@page import="com.sinosoft.lis.vbl.*"%>
  <%@page import="com.sinosoft.lis.pubfun.*"%>
	<%@page import="com.sinosoft.lis.finfee.*"%>
	<%@page contentType="text/html;charset=GBK" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<%
  loggerDebug("TempFinFeeSave","��ʼִ��Saveҳ��");
  
  GlobalInput globalInput = new GlobalInput();
	globalInput.setSchema((GlobalInput)session.getValue("GI"));
  
  TransferData mTransferData = new TransferData();
  
  LJTempFeeSet mLJTempFeeSet = new LJTempFeeSet();
  LJTempFeeSchema tLJTempFeeSchema ;
  LJTempFeeClassSet mLJTempFeeClassSet = new LJTempFeeClassSet();
  LJTempFeeClassSchema tLJTempFeeClassSchema;
  
  FinFeeUI mFinFeeUI = new FinFeeUI();
  
  CErrors tError = null;
  String mOperateType ="INSERT";
  
  loggerDebug("TempFinFeeSave","������������"+mOperateType);
  String FlagStr = "";
  String Content = "";
  String mDescType = "";	//��������־��Ӣ��ת���ɺ��ֵ���ʽ
  //������Ϣ
  String mManageCom				= request.getParameter("ManageCom");
  String mOperator 	 			= request.getParameter("Operator"	);
  
//  //���񽻷���Ϣ
//	String[] mTempNum				=	request.getParameterValues("FinFeeGridNo"	); 
//	String[] mPayMode				=	request.getParameterValues("FinFeeGrid1"	); //���ѷ�ʽ
//  String[] mChequeNo			=	request.getParameterValues("FinFeeGrid5"	); //֧Ʊ��
//  
//  loggerDebug("TempFinFeeSave"," zb ================= : "+ mChequeNo[0]);
//  
//  String[] mChequeDate  	=	request.getParameterValues("FinFeeGrid6"	); //֧Ʊ����
//  ////֧Ʊ��������
//  String[] mBankCode    	=	request.getParameterValues("FinFeeGrid7"	); //���б���
//  String[] mBankAccNo   	=	request.getParameterValues("FinFeeGrid9"	); //�����˺�
//  String[] mAccName     	=	request.getParameterValues("FinFeeGrid10"	); //����
//  ////�շ�����
//  String[] mInBankCode  	=	request.getParameterValues("FinFeeGrid11"	);	//�տ�����
//  String[] mInBankAccNo 	=	request.getParameterValues("FinFeeGrid12"	);	//�տ������˺�
//  String[] mInAccName   	=	request.getParameterValues("FinFeeGrid13"	);	//�տ����л���
//  String[] mPayUserName		=	request.getParameterValues("FinFeeGrid19"	);  //����������
//  
//	//ҵ�񽻷���Ϣ
//  String[] mNum						=	request.getParameterValues("TempToGridNo"	); 
//  String[] mGetNoticeNo 	=	request.getParameterValues("TempToGrid1"	); 	//Ӧ�պ���
//  String[] mTempFeeType 	=	request.getParameterValues("TempToGrid18"	); 	//��������
//  String[] mPayDate 			=	request.getParameterValues("TempToGrid3"	); 	//��������
//  String[] mPayMoney 			=	request.getParameterValues("TempToGrid4"	); 	//���ѽ��
//  String[] mCurrency 			=	request.getParameterValues("TempToGrid5"	); 	//����
//  
//  String[] mAgentGroup 		=	request.getParameterValues("TempToGrid9"	); 	//���������
//  String[] mAgentCode			=	request.getParameterValues("TempToGrid10"	); 	//�����˱���
//  String[] mOtherNo 			=	request.getParameterValues("TempToGrid11"	); 	//ҵ�����
//  String[] mOtherNoType		=	request.getParameterValues("TempToGrid12"	); 	//ҵ�����
//  String[] mAPPntName 		=	request.getParameterValues("TempToGrid16"	); 	//Ͷ����
//  String[] mPolicyCom 		=	request.getParameterValues("TempToGrid7"	); 	//�������
//  String[] mTempFeeNo 		=	request.getParameterValues("TempToGrid20"	); 	//���շѺ�
//	
//  LJTempFeeClassSchema tLJTempFeeClassSchema;
//  
//  if(mOperateType.equals("INSERT")){
//  	loggerDebug("TempFinFeeSave","��ʼִ��INSERTҳ��");
//  	
//	LJTempFeeSchema tLJTempFeeSchema ;
//		if(mNum!=null){
//			int tLength = mNum.length;
//			String tLimit ;
//			String tNo 		; 
//			String serNo 	; 
//  		for(int i = 0 ;i < tLength ;i++){
//  			tLimit = PubFun.getNoLimit(mPolicyCom[i]);
//  			if("".equals(mTempFeeNo[i])||mTempFeeNo[i]==null){
//  				tNo = "TS"+PubFun1.CreateMaxNo("GETNOTICENO", tLimit);// ����֪ͨ��ż��ݽ��Ѻ�
//  			}else{
//  				tNo = mTempFeeNo[i];
//  			}
//  			serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
//  			
//  			//�����շ�
//  			tLJTempFeeClassSchema = new LJTempFeeClassSchema();
//  			tLJTempFeeClassSchema.setTempFeeNo(		tNo							);
//  			tLJTempFeeClassSchema.setPayMode(	 		mPayMode[0]			);
//		  	tLJTempFeeClassSchema.setChequeNo( 		mChequeNo[0]		); //Ʊ�ݺ���
//		  	tLJTempFeeClassSchema.setPayMoney(	 	mPayMoney[i]		); 
//		  	tLJTempFeeClassSchema.setCurrency(	  mCurrency[i]		);
//		  	tLJTempFeeClassSchema.setPayDate(			mPayDate[0]			);
//		  	tLJTempFeeClassSchema.setChequeDate(	mChequeDate[0]	);
//		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);
//		  	tLJTempFeeClassSchema.setConfFlag(		"0"							);
//		  	tLJTempFeeClassSchema.setSerialNo(		serNo						);
//		  	tLJTempFeeClassSchema.setBankCode(		mBankCode[0]		);
//		  	tLJTempFeeClassSchema.setBankAccNo(		mBankAccNo[0]		);
//		  	tLJTempFeeClassSchema.setAccName(			mAccName[0]			);     
//		  	tLJTempFeeClassSchema.setInBankCode(	mInBankCode[0]	);     
//		  	tLJTempFeeClassSchema.setInBankAccNo(	mInBankAccNo[0]	);     
//		  	tLJTempFeeClassSchema.setInAccName(		mInAccName[0]		);     
//		  	tLJTempFeeClassSchema.setEnterAccDate(mPayDate[0]			);     
//		  	tLJTempFeeClassSchema.setManageCom(		mManageCom			);     
//		  	tLJTempFeeClassSchema.setPolicyCom(		mPolicyCom[i]		);     
//		  	tLJTempFeeClassSchema.setOperator(		mOperator				);     
//		  	tLJTempFeeClassSchema.setOtherNo(			mOtherNo[i]			);     
//		  	tLJTempFeeClassSchema.setOtherNoType(	mOtherNoType[i]	);     
//		  	                                       
//		  	tLJTempFeeClassSchema.setPayUserName(	mPayUserName[0]	);     
//		  	                                       
//		  	mLJTempFeeClassSet.add(tLJTempFeeClassSchema);               
//		  	
//  			//�����շ�
//  			tLJTempFeeSchema = new LJTempFeeSchema();
//  			tLJTempFeeSchema.setTempFeeNo(		tNo									);
//  			tLJTempFeeSchema.setTempFeeType(	mTempFeeType[i]			);
//  			tLJTempFeeSchema.setRiskCode(	 		"000000"						);
//  			tLJTempFeeSchema.setOtherNo(			mOtherNo[i]					);
//  			tLJTempFeeSchema.setOtherNoType(	mOtherNoType[i]			);
//  			tLJTempFeeSchema.setPayMoney(			mPayMoney[i]				);
//  			tLJTempFeeSchema.setPayDate(			mPayDate[i]					);
//  			tLJTempFeeSchema.setEnterAccDate(	mPayDate[0]					);
//  			tLJTempFeeSchema.setSerialNo(			serNo								);
//  			tLJTempFeeSchema.setConfFlag(			"0"									);
//  			tLJTempFeeClassSchema.setSerialNo(serNo								);
//  			tLJTempFeeSchema.setManageCom(		mManageCom					);
//  			tLJTempFeeSchema.setPolicyCom(		mPolicyCom[i]				);
//  			tLJTempFeeSchema.setAPPntName(		mAPPntName[i]				);
//  			tLJTempFeeSchema.setAgentGroup(		mAgentGroup[i]			);
//  			tLJTempFeeSchema.setAgentCode(		mAgentCode[i]				);
//  			tLJTempFeeSchema.setCurrency(			mCurrency[i]				);
//  			tLJTempFeeSchema.setRemark(				mGetNoticeNo[i]			);
//  			
//  			mLJTempFeeSet.add(tLJTempFeeSchema);
//  		}
//  	}
//	}

  if(mOperateType.equals("INSERT")){
			loggerDebug("TempFinFeeSave","��ʼִ��INSERTҳ��");
		//�ܱ�����
			String[] zTempFeeNum			=	request.getParameterValues("TTempToGridNo"); 
			String[] zTempFeeNO				=	request.getParameterValues("TTempToGrid1"	);		
			String[] zTempFeeType		  =	request.getParameterValues("TTempToGrid2"	);
			String[] zPayDate				  =	request.getParameterValues("TTempToGrid3"	);
			String[] zPayMoney				=	request.getParameterValues("TTempToGrid4"	);
			String[] zEnterAccDate		=	request.getParameterValues("TTempToGrid5"	);
			String[] zPolicyCom				=	request.getParameterValues("TTempToGrid6"	);
			String[] zRiskCode				=	request.getParameterValues("TTempToGrid7"	);
			String[] zAgentGroup			=	request.getParameterValues("TTempToGrid8"	);
			String[] zAgentCode				=	request.getParameterValues("TTempToGrid9"	);
			String[] zOtherNo				  =	request.getParameterValues("TTempToGrid10");
			String[] zOtherNoType			=	request.getParameterValues("TTempToGrid11");
			String[] zPayIntv				  =	request.getParameterValues("TTempToGrid12");
			String[] zPayYear				  =	request.getParameterValues("TTempToGrid13");
			String[] zPayYearFlag			=	request.getParameterValues("TTempToGrid14");
			String[] zAPPntName				=	request.getParameterValues("TTempToGrid15");
			String[] zReMark				  =	request.getParameterValues("TTempToGrid16");
			String[] zGetNoticeNo		  =	request.getParameterValues("TTempToGrid17");
			String[] zCurrency			  =	request.getParameterValues("TTempToGrid18");		
			String tLimit="" ;
			String tTempNo="" ; 
			String serNo="" 	; 
		  serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);	
			if(zTempFeeNum!=null){
					int tLength = zTempFeeNum.length; 
		  		for(int i = 0 ;i < tLength ;i++){		  			
		  			tLJTempFeeSchema = new LJTempFeeSchema();
		  			tLJTempFeeSchema.setTempFeeNo(		zTempFeeNO[i]							);
		  			tLJTempFeeSchema.setTempFeeType(	zTempFeeType[i]			);
		  			tLJTempFeeSchema.setRiskCode(	 		"000000"						);
		  			tLJTempFeeSchema.setOtherNo(			zOtherNo[i]					);
		  			tLJTempFeeSchema.setOtherNoType(	zOtherNoType[i]			);
		  			tLJTempFeeSchema.setPayMoney(			zPayMoney[i]				);
		  			tLJTempFeeSchema.setPayDate(			zPayDate[i]					);
		  			tLJTempFeeSchema.setEnterAccDate(	zEnterAccDate[i]		);
		  			tLJTempFeeSchema.setSerialNo(			serNo								);
		  			tLJTempFeeSchema.setConfFlag(			"0"									);
		  			tLJTempFeeSchema.setManageCom(		zPolicyCom[i]				);
		  			tLJTempFeeSchema.setPolicyCom(		zPolicyCom[i]				);
		  			tLJTempFeeSchema.setAPPntName(		zAPPntName[i]				);
		  			tLJTempFeeSchema.setAgentGroup(		zAgentGroup[i]			);
		  			tLJTempFeeSchema.setAgentCode(		zAgentCode[i]				);
		  			tLJTempFeeSchema.setCurrency(			zCurrency[i]				);
		  			tLJTempFeeSchema.setRemark(				zGetNoticeNo[i]			);
		  			
		  			mLJTempFeeSet.add(tLJTempFeeSchema);  			
		  		}	
			}
		
			//���������
			String[] sTempFeeClassNum			=	request.getParameterValues("TTempClassToGridNo"	);  
			String[] sTempFeeNO				=	request.getParameterValues("TTempClassToGrid1"	);
			String[] sPayMode		  =	request.getParameterValues("TTempClassToGrid2"	);
			String[] sPayDate				  =	request.getParameterValues("TTempClassToGrid3"	);
			String[] sPayMoney				=	request.getParameterValues("TTempClassToGrid4"	);
			String[] sCurrency		=	request.getParameterValues("TTempClassToGrid5"	);
			//String[] sPolicyCom				=	request.getParameterValues("TTempClassToGrid6"	);
			String[] sEnterAccDate				=	request.getParameterValues("TTempClassToGrid7"	);
			String[] sPolicyCom			=	request.getParameterValues("TTempClassToGrid8"	);
			String[] sChequeNo				=	request.getParameterValues("TTempClassToGrid9"	);
			String[] sChequeDate				  =	request.getParameterValues("TTempClassToGrid10"	);
			String[] sBankName			=	request.getParameterValues("TTempClassToGrid11"	);
			String[] sBankAccNo				  =	request.getParameterValues("TTempClassToGrid12"	);
			String[] sAccName				  =	request.getParameterValues("TTempClassToGrid13"	);
			String[] sInBankCode			=	request.getParameterValues("TTempClassToGrid14"	);
			String[] sInBankAccNo				=	request.getParameterValues("TTempClassToGrid15"	);
			String[] sInAccName				  =	request.getParameterValues("TTempClassToGrid16"	);
			//String[] zPayIntv		  =	request.getParameterValues("TTempClassToGrid17"	);
			//String[] sCurrency			  =	request.getParameterValues("TTempClassToGrid18"	);
			//String[] sPayYearFlag			=	request.getParameterValues("TTempClassToGrid19"	);
			//String[] sAPPntName				=	request.getParameterValues("TTempClassToGrid20"	);
			//String[] sReMark				  =	request.getParameterValues("TTempClassToGrid21"	);
			String[] sOtherNo		  =	request.getParameterValues("TTempClassToGrid22"	);
			String[] sOtherNoType			  =	request.getParameterValues("TTempClassToGrid23"	);
			String[] sPayUserName			  =	request.getParameterValues("TTempClassToGrid24"	);
			if(sTempFeeClassNum!=null){
				int tLength = sTempFeeClassNum.length; 
				for(int j = 0 ;j < tLength ;j++){
				    serNo = PubFun1.CreateMaxNo("SERIALNO", tLimit);
		  			tLimit = PubFun.getNoLimit(sPolicyCom[j]);
		  			tLJTempFeeClassSchema = new LJTempFeeClassSchema();
		  			tLJTempFeeClassSchema.setTempFeeNo(		sTempFeeNO[j]							);
		  			tLJTempFeeClassSchema.setPayMode(	 		sPayMode[j]			);
		  			if(sChequeNo[j] == null || sChequeNo[j] == "")
		  			{
		  			   sChequeNo[j] = "000000";
		  			}
				  	tLJTempFeeClassSchema.setChequeNo( 		sChequeNo[j]		); //Ʊ�ݺ���
				  	tLJTempFeeClassSchema.setPayMoney(	 	sPayMoney[j]		); 
				  	tLJTempFeeClassSchema.setCurrency(	  sCurrency[j]		);
				  	tLJTempFeeClassSchema.setPayDate(			sPayDate[j]			);
				  	tLJTempFeeClassSchema.setChequeDate(	sChequeDate[j]	);
				  	tLJTempFeeClassSchema.setEnterAccDate(sEnterAccDate[j]);
				  	tLJTempFeeClassSchema.setConfFlag(		"0"							);
				  	tLJTempFeeClassSchema.setSerialNo(		serNo						);
				  	tLJTempFeeClassSchema.setBankName(		sBankName[j]		);
				  	tLJTempFeeClassSchema.setBankAccNo(		sBankAccNo[j]		);
				  	if(sBankName[j] != null && !sBankName[j].equals(""))
				  	{
				  	 				  	tLJTempFeeClassSchema.setBankCode(	"000000"	);
				  	}
				  	tLJTempFeeClassSchema.setAccName(			sAccName[j]			);     
				  	tLJTempFeeClassSchema.setInBankCode(	sInBankCode[j]	);     
				  	tLJTempFeeClassSchema.setInBankAccNo(	sInBankAccNo[j]	);     
				  	tLJTempFeeClassSchema.setInAccName(		sInAccName[j]		);     
				  	tLJTempFeeClassSchema.setEnterAccDate(sEnterAccDate[j]);     
				  	tLJTempFeeClassSchema.setManageCom(		sPolicyCom[j]		);     
				  	tLJTempFeeClassSchema.setPolicyCom(		sPolicyCom[j]		);     
//				  	tLJTempFeeClassSchema.setOperator(		mOperator			);     
				  	tLJTempFeeClassSchema.setOtherNo(			sOtherNo[j]			);     
				  	tLJTempFeeClassSchema.setOtherNoType(	sOtherNoType[j]	);     		  	                                       
				  	tLJTempFeeClassSchema.setPayUserName(	sPayUserName[j]	);     
				  	                                       
				  	mLJTempFeeClassSet.add(tLJTempFeeClassSchema);  		
				}	
			}
	}
				
	
  if(mOperateType.equals("INSERT")){
    mDescType = "�����շ�";
  }
  if(mOperateType.equals("UPDATE")){
    mDescType = "�޸�ƥ����Ϣ�ɹ�";
  }
  if(mOperateType.equals("DELETE")){
    mDescType = "ɾ�����񽻷���Ϣ";
  }
  if(mOperateType.equals("QUERY")){
    mDescType = "��ѯ���񽻷���Ϣ";
  }
	
  VData tVData = new VData();
  try{
  	tVData.addElement(		globalInput					);
  	tVData.addElement(		mTransferData				);
    tVData.addElement(		mLJTempFeeClassSet	);
    tVData.addElement(		mLJTempFeeSet				);
    if(!mFinFeeUI.submitData(	tVData,mOperateType	)){
   	}	
  }catch(Exception ex){
    Content = mDescType+"ʧ�ܣ�ԭ����:" + ex.toString();
    FlagStr = "Fail";
  }

  //�����Catch�з����쳣���򲻴Ӵ���������ȡ������Ϣ
  String tFinFeeNo = "";
  if (FlagStr==""){
    tError = mFinFeeUI.mErrors;
    if (!tError.needDealError()){
      Content = mDescType+"�ɹ�";    
    	FlagStr = "Succ";
    }
    else{
    	Content = mDescType+" ʧ�ܣ�ԭ����:" + tError.getFirstError();
    	FlagStr = "Fail";
    }
  }
%>
<html>
<script language="javascript">
	parent.fraInterface.afterSubmit("<%=FlagStr%>","<%=Content%>");
</script>
</html>
