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
		loggerDebug("MeetF1PSave","标准路径==="+tPath);
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
	loggerDebug("MeetF1PSave","合同号==="+tContNo);

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
        /////现在只有两个问卷一个生调问卷，一个生存问卷，用两个分支实现，如果用户选择一个问卷程序
        /////就生成一个数据文件，然后生成通知书。最后调用生成的数据文件进行合并。

      ExeSQL exeSql = new ExeSQL();
      SSRS testSSRS = new SSRS();
      testSSRS = exeSql.execSQL("select sysvarvalue From ldsysvar where sysvar='DataPrintCombine'");
      //String strTemplatePath = testSSRS.GetText(1,1); //数据库中的路径-模板路径D:/lis/ui/f1print/NCLtemplate/
      String strTemplatePath=tPath;
      testSSRS = exeSql.execSQL("select askcode from LCRReportPrt where PrtSeq='"+PrtSeq+"'");
      String remark="";

      loggerDebug("MeetF1PSave","问卷的行数==="+testSSRS.MaxRow);
      for(int i=1;i<=testSSRS.MaxRow;i++)
      {
          if(testSSRS.GetText(i,1).equals("87"))
          {
            loggerDebug("MeetF1PSave","进入生调问卷!");
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
	   Content="没有得到要显示的数据文件";
	  }
	}
	////add by yaory 2005-7-5
	String strVFPathName = strTemplatePath+"liveData.vts";

	CombineVts tcombineVts = null;
	////end add
	if (operFlag==true)
	{
	///add by yaory 2005-7-5

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
             }
          }
          
          else if(testSSRS.GetText(i,1).equals("88"))
          {
            loggerDebug("MeetF1PSave","进入财务问卷!");
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
	   Content="没有得到要显示的数据文件";
	  }
	}
	////add by yaory 2005-7-5
	String strVFPathName = strTemplatePath+"financeData.vts";

	CombineVts tcombineVts = null;
	////end add
	if (operFlag==true)
	{
	///add by yaory 2005-7-5

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }
          
          else if(testSSRS.GetText(i,1).equals("89"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（癫痫）!");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"DXData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .
          
          else if(testSSRS.GetText(i,1).equals("90"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（哮喘）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"XCData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .  
          
          else if(testSSRS.GetText(i,1).equals("91"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（糖尿病）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"TNBData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .              
          
          else if(testSSRS.GetText(i,1).equals("92"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（甲状腺）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"JZXData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .  
            
          
          else if(testSSRS.GetText(i,1).equals("93"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（胸痛）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"XTData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .                
          
          else if(testSSRS.GetText(i,1).equals("94"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（头痛、头晕）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"TTTYData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .      
          
          else if(testSSRS.GetText(i,1).equals("95"))
          {
            loggerDebug("MeetF1PSave","女性健康补充问卷  ");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"NXJKBCWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("96"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（肝炎）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"GYData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               

          else if(testSSRS.GetText(i,1).equals("97"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（呼吸道）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"HXDData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("98"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（消化系统）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"XHXTData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("99"))
          {
            loggerDebug("MeetF1PSave","婴幼儿健康问卷");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"YYEJKWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("100"))
          {
            loggerDebug("MeetF1PSave","体育问卷");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"sportdcbData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("101"))
          {
            loggerDebug("MeetF1PSave","成长无忧健康问卷");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"CZWYWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("102"))
          {
            loggerDebug("MeetF1PSave","病史问卷 ");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"BSWJData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("103"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（高血压）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"JbwjGxaData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("104"))
          {
            loggerDebug("MeetF1PSave","驾驶执照执有者问卷 ");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"JszzzyzwjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("105"))
          {
            loggerDebug("MeetF1PSave","外出人员调查问卷");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"WcrydcwjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("106"))
          {
            loggerDebug("MeetF1PSave","外地人士投保   ");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"WdrstbwjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("107"))
          {
            loggerDebug("MeetF1PSave","疾病问卷（消化性溃疡）");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"JbwjXhxkyData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
          else if(testSSRS.GetText(i,1).equals("108"))
          {
            loggerDebug("MeetF1PSave","沿海渔业从业人员  ");
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
	   Content="没有得到要显示的数据文件";
	  }
	}

	String strVFPathName = strTemplatePath+"YhyycyrywjData.vts";

	CombineVts tcombineVts = null;

	if (operFlag==true)
	{

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
            }
          }   //end of if .               
          
      }     // end of for.
      
      

 /////////////add end//////////////以下生成通知书-分支-PRINT3
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
	   Content="没有得到要显示的数据文件";
	  }
	}
	////add by yaory 2005-7-5
	String strVFPathName =strTemplatePath+"announceData.vts";

	CombineVts tcombineVts = null;
	////end add
	if (operFlag==true)
	{
	///add by yaory 2005-7-5

	//合并VTS文件
	// SysConst.TEMPLATE 模板目录常量，方便日后更改

	tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

	ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
	tcombineVts.output(dataStream);

	//把dataStream存储到磁盘文件
	//loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
	AccessVtsFile.saveToFile(dataStream,strVFPathName);
	loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
	
	


/////////////////////////////////////下面进行组合////////////////
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
	   Content="没有得到要显示的数据文件";
	  }
	}
	////add by yaory 2005-7-5
	 strVFPathName = strTemplatePath+"combineData.vts";


	////end add
	if (operFlag==true)
	{
          ///add by yaory 2005-7-5

          //合并VTS文件
          // SysConst.TEMPLATE 模板目录常量，方便日后更改

          tcombineVts = new CombineVts(txmlExport.getInputStream(),strTemplatePath);

          ByteArrayOutputStream dataStream1 = new ByteArrayOutputStream();
          tcombineVts.output(dataStream1);

          //把dataStream存储到磁盘文件
          //loggerDebug("MeetF1PSave","存储文件到"+strVFPathName);
          AccessVtsFile.saveToFile(dataStream1,strVFPathName);
          loggerDebug("MeetF1PSave","==> Write VTS file to disk ");
          strVFPathName = strTemplatePath+"new.vts";
        }
	//end add//////////////////////////////////////////////////////
	session.putValue("PrintNo",PrtSeq );
	session.putValue("MissionID",tMissionID );
	session.putValue("SubMissionID",tSubMissionID );
	session.putValue("Code",tCode );	//核保通知书类别
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

