<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.vschema.*"%>
<%@page import="com.sinosoft.lis.f1printgrp.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="java.io.*"%>
<%
		loggerDebug("MeetF1PSave","--------------------start------------------");
		String tPath = application.getRealPath("f1print/NCLtemplate") + "/";
		loggerDebug("MeetF1PSave","��׼·��==="+tPath);
        CError cError = new CError( );
        boolean operFlag=true;
		String tRela  = "";
		String FlagStr = "";
		String Content = "";
		String strOperation = "";
    String PrtSeq=request.getParameter("PrtSeq");
	String tMissionID = request.getParameter("MissionID");
	String tSubMissionID = request.getParameter("SubMissionID");
	String tContNo	 = request.getParameter("ContNo");
	loggerDebug("MeetF1PSave","��ͬ��==="+tContNo);

   String tCode = request.getParameter("NoticeType");
   if(tCode==null)
   {
      tCode="04";
   }
	LOPRTManagerSchema tLOPRTManagerSchema = new LOPRTManagerSchema();
	tLOPRTManagerSchema.setPrtSeq(PrtSeq);
	tLOPRTManagerSchema.setCode(tCode);
	loggerDebug("MeetF1PSave",PrtSeq);
	loggerDebug("MeetF1PSave","tCode:"+tCode);
	GlobalInput tG = new GlobalInput();
	tG = (GlobalInput)session.getValue("GI");

	VData tVData = new VData();
	VData mResult = new VData();
	CErrors mErrors = new CErrors();
    tVData.addElement(tLOPRTManagerSchema);
    tVData.addElement(tG);
    tVData.addElement(tPath); 
    MeetF1PUI tMeetF1PUI = new MeetF1PUI();
	XmlExport txmlExport = new XmlExport();
	/////////////add by yaory for generate distinct .vts then combine-2005-7-5-18:47
	//first query how many questionaires
        /////����ֻ�������ʾ�һ�������ʾ�һ�������ʾ���������֧ʵ�֣�����û�ѡ��һ���ʾ����
        /////������һ�������ļ���Ȼ������֪ͨ�顣���������ɵ������ļ����кϲ���

      ExeSQL exeSql = new ExeSQL();
      SSRS testSSRS = new SSRS();
      testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
      //String strTemplatePath = testSSRS.GetText(1,1); //���ݿ��е�·��-ģ��·��D:/lis/ui/f1print/NCLtemplate/
      String strTemplatePath=tPath;
      testSSRS = exeSql.execSQL("select askcode from LCRReportPrt where PrtSeq='"+PrtSeq+"'");
      String remark="";

      loggerDebug("MeetF1PSave","�ʾ������==="+testSSRS.MaxRow);
      for(int i=1;i<=testSSRS.MaxRow;i++)
      {
          if(testSSRS.GetText(i,1).equals("87"))
          {
            loggerDebug("MeetF1PSave","���������ʾ�!");
            if(!tMeetF1PUI.submitData(tVData,"PRINT1"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}
	////add by yaory 2005-7-5
	String strVFPathName = strTemplatePath+"liveData.vts";

	CombineVts tcombineVts = null;
	////end add
	if (operFlag==true)
	{
	///add by yaory 2005-7-5

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
             }
          }
          
          else if(testSSRS.GetText(i,1).equals("88"))
          {
            loggerDebug("MeetF1PSave","��������ʾ�!");
            if(!tMeetF1PUI.submitData(tVData,"PRINT2"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}
	////add by yaory 2005-7-5
	String strVFPathName = strTemplatePath+"financeData.vts";

	CombineVts tcombineVts = null;
	////end add
	if (operFlag==true)
	{
	///add by yaory 2005-7-5

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }
          
          else if(testSSRS.GetText(i,1).equals("89"))
          {
            loggerDebug("MeetF1PSave","�����ʾ���!");
            if(!tMeetF1PUI.submitData(tVData,"PRINTa"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"DXData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .
          
          else if(testSSRS.GetText(i,1).equals("90"))
          {
            loggerDebug("MeetF1PSave","�����ʾ�������");
            if(!tMeetF1PUI.submitData(tVData,"PRINTb"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"XCData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .  
          
          else if(testSSRS.GetText(i,1).equals("91"))
          {
            loggerDebug("MeetF1PSave","�����ʾ����򲡣�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTc"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"TNBData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .              
          
          else if(testSSRS.GetText(i,1).equals("92"))
          {
            loggerDebug("MeetF1PSave","�����ʾ���״�٣�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTd"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"JZXData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .  
            
          
          else if(testSSRS.GetText(i,1).equals("93"))
          {
            loggerDebug("MeetF1PSave","�����ʾ���ʹ��");
            if(!tMeetF1PUI.submitData(tVData,"PRINTe"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"XTData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .                
          
          else if(testSSRS.GetText(i,1).equals("94"))
          {
            loggerDebug("MeetF1PSave","�����ʾ�ͷʹ��ͷ�Σ�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTf"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"TTTYData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .      
          
          else if(testSSRS.GetText(i,1).equals("95"))
          {
            loggerDebug("MeetF1PSave","Ů�Խ��������ʾ�  ");
            if(!tMeetF1PUI.submitData(tVData,"PRINTg"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"NXJKBCWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("96"))
          {
            loggerDebug("MeetF1PSave","�����ʾ����ף�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTh"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"GYData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               

          else if(testSSRS.GetText(i,1).equals("97"))
          {
            loggerDebug("MeetF1PSave","�����ʾ���������");
            if(!tMeetF1PUI.submitData(tVData,"PRINTi"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"HXDData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("98"))
          {
            loggerDebug("MeetF1PSave","�����ʾ�����ϵͳ��");
            if(!tMeetF1PUI.submitData(tVData,"PRINTj"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"XHXTData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("99"))
          {
            loggerDebug("MeetF1PSave","Ӥ�׶������ʾ�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTk"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"YYEJKWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("100"))
          {
            loggerDebug("MeetF1PSave","�����ʾ�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTl"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"sportdcbData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("101"))
          {
            loggerDebug("MeetF1PSave","�ɳ����ǽ����ʾ�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTm"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"CZWYWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("102"))
          {
            loggerDebug("MeetF1PSave","��ʷ�ʾ� ");
            if(!tMeetF1PUI.submitData(tVData,"PRINTn"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"BSWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("103"))
          {
            loggerDebug("MeetF1PSave","�����ʾ���Ѫѹ��");
            if(!tMeetF1PUI.submitData(tVData,"PRINTo"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"JbwjGxaData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("104"))
          {
            loggerDebug("MeetF1PSave","��ʻִ��ִ�����ʾ� ");
            if(!tMeetF1PUI.submitData(tVData,"PRINTp"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"JszzzyzwjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("105"))
          {
            loggerDebug("MeetF1PSave","�����Ա�����ʾ�");
            if(!tMeetF1PUI.submitData(tVData,"PRINTq"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"WcrydcwjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("106"))
          {
            loggerDebug("MeetF1PSave","�����ʿͶ��   ");
            if(!tMeetF1PUI.submitData(tVData,"PRINTr"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"WdrstbwjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("107"))
          {
            loggerDebug("MeetF1PSave","�����ʾ�����������");
            if(!tMeetF1PUI.submitData(tVData,"PRINTs"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"JbwjXhxkyData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("108"))
          {
            loggerDebug("MeetF1PSave","�غ���ҵ��ҵ��Ա  ");
            if(!tMeetF1PUI.submitData(tVData,"PRINTt"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}

	String strVFPathName = strTemplatePath+"YhyycyrywjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
      }     // end of for.
      
      

 /////////////add end//////////////��������֪ͨ��-��֧-PRINT3
    if(!tMeetF1PUI.submitData(tVData,"PRINT3"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}
	////add by yaory 2005-7-5
	String strVFPathName =strTemplatePath+"announceData.vts";

	CombineVts tcombineVts = null;
	////end add
	if (operFlag==true)
	{
	///add by yaory 2005-7-5

	//�ϲ�VTS�ļ�
	// SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//��dataStream�洢�������ļ�
	//loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
	
	


/////////////////////////////////////����������////////////////
if(!tMeetF1PUI.submitData(tVData,"PRINT4"))
    {
       operFlag=false;
       Content=tMeetF1PUI.mErrors.getFirstError().toString();
    }
    else
    {
	  mResult = tMeetF1PUI.getResult();
	  txmlExport=(XmlExport)mResult.getObjectByObjectName("XmlExport",0);
	  if(txmlExport==null)
	  {
	   operFlag=false;
	   Content="û�еõ�Ҫ��ʾ�������ļ�";
	  }
	}
	////add by yaory 2005-7-5
	 strVFPathName = strTemplatePath+"combineData.vts";


	////end add
	if (operFlag==true)
	{
          ///add by yaory 2005-7-5

          //�ϲ�VTS�ļ�
          // SysConst.TEMPLATE ģ��Ŀ¼�����������պ����

          tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

          ByteArrayOutputStream dataStream1 = new ByteArrayOutputStream();
          tcombineVts.output(dataStream1);

          //��dataStream�洢�������ļ�
          //loggerDebug("MeetF1PSave","�洢�ļ���"+strVFPathName);
          AccessVtsFile.saveToFile(dataStream1,strVFPathName);
          loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
          strVFPathName = strTemplatePath+"new.vts";
        }
	//end add//////////////////////////////////////////////////////
	session.putValue("PrintNo",PrtSeq );
	session.putValue("MissionID",tMissionID );
	session.putValue("SubMissionID",tSubMissionID );
	session.putValue("Code",tCode );	//�˱�֪ͨ�����
	session.putValue("ContNo",tContNo );
	
  session.putValue("PrtNo",tContNo );
	session.putValue("PrintStream", txmlExport.getInputStream());
	loggerDebug("MeetF1PSave","put session value,ContNo:"+tContNo);
	loggerDebug("MeetF1PSave","put session value=="+tMissionID);
	loggerDebug("MeetF1PSave","put session value,SubMissionID:"+tSubMissionID);
	loggerDebug("MeetF1PSave","put session value,PrtSeq=="+PrtSeq);
        session.putValue("RealPath",strVFPathName );
	response.sendRedirect("./GetF1PrintJ1.jsp");
//	response.sendRedirect("../f1print/GetF1PrintJ1.jsp?RealPath="+strVFPathName);
//	  response.sendRedirect("../f1print/GetF1Print.jsp?RealPath="+strVFPathName);

	}
	else
	{
    	FlagStr = "Fail";

%>
<html>
<%@page contentType="text/html;charset=gb2312" %>
<%@include file="../common/jsp/Log4jUI.jsp"%>  
<script language="javascript">
	alert("<%=Content%>");
	top.close();

	//window.opener.afterSubmit("<%=FlagStr%>","<%=Content%>");

</script>
</html>
<%
  }

%>

