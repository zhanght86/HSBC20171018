package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.schema.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;

import java.text.*;
import java.util.*;

/**
 * <p>Title: lis</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: sinosoft</p>
 * @author tm
 * @version 2.0 by jiaqiangli   
 * 2010-03-03 modify  by mslife
 */


public class EdorOutDayDoneBL {
private static Logger logger = Logger.getLogger(EdorOutDayDoneBL.class);

  public EdorOutDayDoneBL() {
  }
  /** 错误处理类，每个需要错误处理的类中都放置该类 */
  public CErrors mErrors=new CErrors();
  private VData mResult = new VData();

  //取得的时间
  private String StartDay="";
  private String EndDay="";
  private String CalManageCom="";

  //含M00 1表示是 0表示否
  private String tIsHaveM00 = "";

  private String mFilePathDesc = "TKReportCreat"; //生成文件的路径,待修改
  private String mFilePath = ""; //生成文件路径
  private String mModePath = "";//模版路径
  private String mTemplatePath="";

  String strArr[] = new String[23];
//  private String strArr[] = new String[15];
  //业务处理相关变量
  /** 全局数据 */
  private GlobalInput mGlobalInput =new GlobalInput() ;
  /**
  传输数据的公共方法
  */
  public boolean submitData(VData cInputData, String cOperate)
  {
    // 得到外部传入的数据，将数据备份到本类中
    if( !getInputData(cInputData) ) {
      return false;
    }
    mResult.clear();
    // 准备所有要打印的数据
    if( !queryDataOther() ) {
      return false;
    }
    return true;
  }
  private void dealError(String FuncName,String ErrMsg) {
    CError error = new CError();
    error.moduleName = "SummitMarketingTeam";
    error.errorMessage = ErrMsg.trim();
    error.functionName = FuncName.trim();
    this.mErrors.addOneError(error);
  }
  /**
     * 取模版存放路径
     * @return
     */
    private boolean checkDesc()
    {
      logger.debug("@@@@@@@@@@@@@@@@@@@@@");
      LDSysVarDB tLDSysVarDB = null;

      //生成文件的存放路径
      tLDSysVarDB = new LDSysVarDB();
      tLDSysVarDB.setSysVar(mFilePathDesc);
      if (tLDSysVarDB.getInfo() == false)
      {
        dealError("checkDesc", "LDSysVar取文件路径(" + mFilePathDesc + ")描述失败");
        return false;
      }
      //生成文件路径
      mFilePath = tLDSysVarDB.getSysVarValue();
      // mFileName = getFileName();
      //mFilePath="d:/zhujc/";
      logger.debug("mFilePath:"+mFilePath);
  
		//模版路径
	  this.mModePath=mTemplatePath;
      logger.debug("mModePath:"+mModePath);
      return true;
  }
  private boolean getTempPath()
  {
    //获取生成文件路径
    if(!checkDesc())
    {
      return false;
    }
    return true;
  }
  /**
   * 从输入数据中得到所有对象
   * 输出：如果没有得到足够的业务数据对象，则返回false,否则返回true
   */
  private boolean getInputData(VData cInputData)
  {
    //全局变量
    StartDay=(String)cInputData.get(0);
    EndDay=(String)cInputData.get(1);
    CalManageCom=(String)cInputData.get(2);
    tIsHaveM00=(String)cInputData.get(3);
    logger.debug("BL是否含M00["+tIsHaveM00+"]");
    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;
    this.mTemplatePath=(String)cInputData.get(5);
    //获取模版和生成文件的路径
    if(!getTempPath())
    {
      logger.debug("获取模版和生成文件的路径出错");
      return false;
    }

    return true;
  }
  public VData getResult()
  {
    return this.mResult;
  }

  private boolean queryDataOther()
  {
	  
	String  mSaleChlSQL ="";
		if (tIsHaveM00 != null && !"".equals(tIsHaveM00)) {
			mSaleChlSQL = " and exists (select 'X'" + " from lccont p "
					+ "where p.contno = a.contno "
					+ " and p.salechnl = '" + "?tIsHaveM00?" + "') ";
		}
		

    try{
    	SQLwithBindVariables sqlbv = new SQLwithBindVariables();
      String[] tSQL = new String[7];
      String tSQL_key = "";//(select decode(d.edorappname,b.AppntName,(select decode(w.phone,null,w.mobile,w.phone) from LCAddress w,lcappnt q where  w.customerno = q.appntno and w.addressno = q.addressno  and q.contno=b.contno),b.InsuredName,(select decode(phone,null,mobile,phone) from LCAddress w,lcinsured q where  w.customerno = q.appntno and w.addressno = q.addressno  and q.contno=b.contno),'')  from lccont b where contno=a.contno)
      if(SysConst.DBTYPE.equals(SysConst.DBTYPE_ORACLE)){
    	  tSQL_key = "select substr(trim(d.managecom), 1, 6) com3,(select name from ldcom where comcode=d.managecom) com4,(select (select name from lacom where agentcom =b.agentcom) from lccont b where contno=d.otherno) agentcom,"
    		      +" (select (select codename from ldcode where  codetype='salechnl' and code=b.salechnl)  from lccont b where contno=d.otherno) salechnl,"
    		      +" d.edorappname,"
    		      +" d.phone,"
    		      +" d.postaladdress,"
    		      +" d.zipcode, d.otherno,"
    		      +" (select (select codename from ldcode where  codetype='edortype' and code=b.edortype) from lpedoritem b where edoracceptno=d.edoracceptno and contno=d.otherno ) edortype,"
    		      +" (select (select name from laagent where agentcode=b.agentcode) from lccont b where contno=d.otherno) agentname,"
    		      +" (select agentcode from lccont where contno=d.otherno) agentcode,"
    		      +" (select (select (case when mobile is null then phone else mobile end) from laagent where agentcode=b.agentcode) from lccont b where contno=d.otherno) agentphone"
    		      +" ,(select max(edorappdate) from lpedoritem where edoracceptno=d.edoracceptno)," 
    		      +" (case when (select codename from ldcode where code = (select activityid from lwmission where missionprop2 = b.contno" 
    		      +" and activityid in ('0000000007', '0000000003', '0000000009', '0000000001', '0000000005', '0000000006', '0000000100')" 
    		      +" and rownum = 1) and codetype = 'bqactivityname') is not null then (select codename from ldcode where code = (select activityid from lwmission where missionprop2 = b.contno" 
    		      +" and activityid in ('0000000007', '0000000003', '0000000009', '0000000001', '0000000005', '0000000006', '0000000100')" 
    		      +" and rownum = 1) and codetype = 'bqactivityname')  else (case (select count(*) from lwmission where missionprop1 = d.edoracceptno and activityid = '0000000007') when 0 then ((case (select count(*) from lwmission where missionprop1 = d.edoracceptno and activityid = '0000000005') when 0 then (select CodeName from LDCode where codetype = 'edorstate' and trim(code) = (b.edorstate)) else '人工核保中' end)) else '审批中' end) end) edorstate"
    		      +" from lpedorapp d,lpedoritem b"
    				   +" where d.makedate >= '"+"?StartDay?"+"' and d.makedate <= '"+"?EndDay?"+"' and d.managecom like concat('"+"?CalManageCom?"+"','%') and  d.edorstate  in ('1','2','3','5','a') and d.othernotype in ('1','3') " //and  a.edorstate in ('0', 'c', 'd')
    				   +" and d.edoracceptno=b.edoracceptno"
    				   +" and not exists (select '1' from lccont where contno=d.otherno and conttype='2')";
    				//   +" and exists (select count(*) from ldcalendar where commondate >= d.makedate"
    				//   +" and commondate <='"+"?EndDay?"+"' and workdateflag = 'Y' having count(*) - 1 >= 5)";
      }else if(SysConst.DBTYPE.equals(SysConst.DBTYPE_MYSQL)){
      tSQL_key = "select substr(trim(d.managecom), 1, 6) com3,(select name from ldcom where comcode=d.managecom) com4,(select (select name from lacom where agentcom =b.agentcom) from lccont b where contno=d.otherno) agentcom,"
	      +" (select (select codename from ldcode where  codetype='salechnl' and code=b.salechnl)  from lccont b where contno=d.otherno) salechnl,"
	      +" d.edorappname,"
	      +" d.phone,"
	      +" d.postaladdress,"
	      +" d.zipcode, d.otherno,"
	      +" (select (select codename from ldcode where  codetype='edortype' and code=b.edortype) from lpedoritem b where edoracceptno=d.edoracceptno and contno=d.otherno ) edortype,"
	      +" (select (select name from laagent where agentcode=b.agentcode) from lccont b where contno=d.otherno) agentname,"
	      +" (select agentcode from lccont where contno=d.otherno) agentcode,"
	      +" (select (select (case when mobile is null then phone else mobile end) from laagent where agentcode=b.agentcode) from lccont b where contno=d.otherno) agentphone"
	      +" ,(select max(edorappdate) from lpedoritem where edoracceptno=d.edoracceptno)," 
	      +" (case when (select codename from ldcode where code = (select activityid from lwmission where missionprop2 = b.contno" 
	      +" and activityid in ('0000000007', '0000000003', '0000000009', '0000000001', '0000000005', '0000000006', '0000000100')" 
	      +" limit 0,1) and codetype = 'bqactivityname') is not null then (select codename from ldcode where code = (select activityid from lwmission where missionprop2 = b.contno" 
	      +" and activityid in ('0000000007', '0000000003', '0000000009', '0000000001', '0000000005', '0000000006', '0000000100')" 
	      +" limit 0,1) and codetype = 'bqactivityname')  else (case (select count(*) from lwmission where missionprop1 = d.edoracceptno and activityid = '0000000007') when 0 then ((case (select count(*) from lwmission where missionprop1 = d.edoracceptno and activityid = '0000000005') when 0 then (select CodeName from LDCode where codetype = 'edorstate' and trim(code) = (b.edorstate)) else '人工核保中' end)) else '审批中' end) end) edorstate"
	      +" from lpedorapp d,lpedoritem b"
			   +" where d.makedate >= '"+"?StartDay?"+"' and d.makedate <= '"+"?EndDay?"+"' and d.managecom like concat('"+"?CalManageCom?"+"','%') and  d.edorstate  in ('1','2','3','5','a') and d.othernotype in ('1','3') " //and  a.edorstate in ('0', 'c', 'd')
			   +" and d.edoracceptno=b.edoracceptno"
			   +" and not exists (select '1' from lccont where contno=d.otherno and conttype='2')";
			 //  +" and exists (select count(*) from ldcalendar where commondate >= d.makedate"
			  // +" and commondate <='"+"?EndDay?"+"' and workdateflag = 'Y' having count(*) - 1 >= 5)";
      }
      sqlbv.sql(tSQL_key);
      sqlbv.put("StartDay", StartDay);
      sqlbv.put("EndDay", EndDay);
      sqlbv.put("CalManageCom", CalManageCom);  
      
            ZHashReport tZHashReport = new ZHashReport(sqlbv);
//            for (int i = 0; i < tSQL.length; i++) {
//              tZHashReport.addSql(tSQL[i]);
//            }
            tZHashReport.setColumnType(1,tZHashReport.StringType);//0机构代码 1机构名称
//            tZHashReport.setColumnType(2,tZHashReport.IntType);//期初人力
//            tZHashReport.setColumnType(3,tZHashReport.IntType);//期末人力
            tZHashReport.setColumnType(1,tZHashReport.StringType);
            tZHashReport.setColumnType(2,tZHashReport.StringType);
            tZHashReport.setColumnType(3,tZHashReport.StringType);
            tZHashReport.setColumnType(4,tZHashReport.StringType);
            tZHashReport.setColumnType(5,tZHashReport.StringType);
            tZHashReport.setColumnType(6,tZHashReport.StringType);
            tZHashReport.setColumnType(7,tZHashReport.StringType);
            tZHashReport.setColumnType(8,tZHashReport.StringType);
            tZHashReport.setColumnType(9,tZHashReport.StringType);
            tZHashReport.setColumnType(10,tZHashReport.StringType);
            tZHashReport.setColumnType(11,tZHashReport.StringType);
            tZHashReport.setColumnType(12,tZHashReport.StringType);
            tZHashReport.setColumnType(13,tZHashReport.StringType);
            tZHashReport.setColumnType(14,tZHashReport.StringType);
            tZHashReport.setColumnType(15,tZHashReport.StringType);
            tZHashReport.setDoformat(false);
            tZHashReport.setSumColumn(false);

            String[][] result = tZHashReport.calItem();
//            String[][] newresult = new String[result.length+1][14];
//            String[] sums = new String[14];
//            double[] tsums = new double[10];
//                  //add by renhailong 2008-05-14
//                  //增加一条机构代码;
//                  //+1是为了检查方便;
//            for (int i = 0; i < result.length; i++) {
//              //logger.debug("result.length["+result.length+"]");
//                  newresult[i][0]=result[i][0];
//            		
//            		for (int j = 1; j <= 1; j++) {
//
//					newresult[i][0 + j] = result[i][1]; // 机构
//
//					// logger.debug("nnnnnnnnnn");
//					newresult[i][1 + j] = result[i][2]; // 期初人力
//					newresult[i][2 + j] = result[i][3]; // 期末人力
//					newresult[i][3 + j] = result[i][4]; // 当日规模保费
//					newresult[i][4 + j] = result[i][6]; // 累计规模保费
//					newresult[i][5 + j] = result[i][5]; // 当日标准保费
//					newresult[i][6 + j] = result[i][7]; // 累计标准保费
//					newresult[i][7 + j] = result[i][8]; // 标准件数
//					newresult[i][8 + j] = result[i][9]; // 活动人力
//
//					double r1 = Double.parseDouble(newresult[i][1 + j]);// 期初人力
//					double r2 = Double.parseDouble(newresult[i][2 + j]);// 期末人力
//					double r5 = Double.parseDouble(newresult[i][3 + j]);// 当日规模保费
//					double r6 = Double.parseDouble(newresult[i][4 + j]);// 累计规模保费
//					double r9 = Double.parseDouble(newresult[i][5 + j]);// 当日标准保费
//					double r10 = Double.parseDouble(newresult[i][6 + j]);// 累计标准保费
//
//					double r11 = Double.parseDouble(result[i][9]);//result[i][11
//																	// ] 活动人力
//
//					double r13 = Double.parseDouble(newresult[i][7 + j]); // 标准件数
//
//					// logger.debug("eeeeeeeeeeeee");
//					double r23 = 0.00;
//					if (r13 != 0)
//						r23 = r10 / r13 * 10000;// 件均标保
//					double r24 = (r1 + r2) / 2;// 平均人力
//					double r14 = 0.00;
//					if (r24 != 0)
//						r14 = r11 / r24; // 活动率
//
//					// logger.debug("qqqqqqqqqqqqq");
//
//					newresult[i][9 + j] = new DecimalFormat("0.00%")
//							.format(r14); // 活动率
//					if (r24 != 0) {
//						newresult[i][10 + j] = String
//								.valueOf(r10 * 10000 / r24); // 人均标保
//						newresult[i][11 + j] = String.valueOf(r13 / r24); // 人均标件
//					} else {
//						newresult[i][10 + j] = String.valueOf(0.00); // 人均标保
//						newresult[i][11 + j] = String.valueOf(0.00); // 人均标件
//					}
//					newresult[i][12 + j] = String.valueOf(r23); // r23=r10/r13*
//																// 10000;件均标保
//
//
//					tsums[1] = tsums[1] + r1; // 期初人力
//					tsums[2] = tsums[2] + r2;// 期末人力
//					tsums[3] = tsums[3] + r5;// 当日规模保费
//					tsums[4] = tsums[4] + r6;// 累计规模保费
//					tsums[5] = tsums[5] + r9;// 当日标准保费
//					tsums[6] = tsums[6] + r10;// 累计标准保费
//					tsums[7] = tsums[7] + r13;// 标准件数
//					tsums[8] = tsums[8] + r11;// 活动人力
//					tsums[9] = tsums[9] + r24;//平均人力
//
//				}
//            }
//            
//               //统计合计
//               logger.debug("统计合计");
//              
//                sums[0]=this.mGlobalInput.ManageCom;
//               sums[0+1] = "合计";
//              
//               sums[1+1] = String.valueOf(tsums[1]);
//               sums[2+1] = String.valueOf(tsums[2]);
//               sums[3+1] = String.valueOf(tsums[3]);
//               sums[4+1] = String.valueOf(tsums[4]);
//               sums[5+1] = String.valueOf(tsums[5]);
//               sums[6+1] = String.valueOf(tsums[6]);
//               sums[7+1] = String.valueOf(tsums[7]);
//               sums[8+1] = String.valueOf(tsums[8]);
//               if (tsums[9]!=0) {
//                 sums[9+1] = new DecimalFormat("0.00%").format(tsums[8] / tsums[9]); //活动率
//                 sums[10+1] = String.valueOf(tsums[6] / tsums[9] * 10000); //newresult[i][15] = String.valueOf(r10 * 10000 / r24);人均标保
//                 sums[11+1] = String.valueOf(tsums[7] / tsums[9]); //String.valueOf(r13 / r24);人均标件
//               }
//               else {
//                 sums[9+1] = String.valueOf(0.00);
//                 sums[10+1] = String.valueOf(0.00);
//                 sums[11+1] = String.valueOf(0.00);
//               }
//               if (tsums[7]!=0)
//                 sums[12+1] = String.valueOf(tsums[6]/tsums[7] * 10000);//r23 = r10 / r13 * 10000;件均标保
//               else
//                 sums[12+1] = String.valueOf(0.00);
//
//               //复制最后的统计行
//               for (int m=0;m<newresult[0].length;m++) {
//                 newresult[newresult.length-1][m] = sums[m];
//               }
            if (result==null||result.length==0)
            {
              // @@错误处理
              CError tError = new CError();
              tError.moduleName = "EdorOutDayDoneBL";
              tError.functionName = "queryDataOther()";
              tError.errorMessage = "没有查询到数据!";
              this.mErrors .addOneError(tError) ;
              return false;
            }
			for (int jj = 0; jj < result.length; jj++) {
				for (int kk = 0; kk < result[jj].length; kk++) {
					logger.debug(result[jj][kk] + ",");
				}
			}
               //表头
               TransferData tTitleTransferData = new TransferData();
               tTitleTransferData.setNameAndValue("ManageCom",this.CalManageCom);
               tTitleTransferData.setNameAndValue("StartDate",this.StartDay);
               tTitleTransferData.setNameAndValue("EndDate",this.EndDay);
               

            tTitleTransferData.setNameAndValue("Operator",
					this.mGlobalInput.Operator);// 制表人
			tTitleTransferData.setNameAndValue("CurrentDT", PubFun
					.getCurrentDate()
					+ " " + PubFun.getCurrentTime());// 制表日期时间

			HashReport tHashReport = new HashReport();
			// 生成路径，模版路径，多维数组
			String tCreatePath = "";
			String tModelPath = "";
			// 生成文件名命名规范：报表名_级别_月份.xls
			// 注意：此处还需要在生成文件名加些参数。。。。
			tCreatePath = this.mFilePath + "EdorOutDayDone_"+this.CalManageCom + "_"
					+ this.StartDay + "_" + this.EndDay + "_"
					+ tIsHaveM00 + ".xls";
			tModelPath = this.mModePath + "EdorOutDayDoneQuery.xls";
			logger.debug("[" + tModelPath + "]");
			logger.debug(tCreatePath);
			logger.debug(tModelPath);
			try {
				// 有表头
				tHashReport.outputArrayToFile1(tCreatePath, tModelPath,
						result, tTitleTransferData);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
      logger.debug("data end");
    }
    catch(Exception ex)
    {
    	
      // @@错误处理
      CError tError =new CError();
      tError.moduleName="LAAgentMakePolBL";
      tError.functionName="queryDataother";
      tError.errorMessage=ex.toString();
      this.mErrors .addOneError(tError);
      return false;
    }
    logger.debug("end");
    return true;
  }
  public static void main(String[] args) {
	  EdorOutDayDoneBL tLAAgentTempfeePolBL = new EdorOutDayDoneBL();
    /*StartDay=(String)cInputData.get(0);
    EndDay=(String)cInputData.get(1);
    CalManageCom=(String)cInputData.get(2);
    tIsHaveM00=(String)cInputData.get(3);
    mGlobalInput=(GlobalInput)cInputData.getObjectByObjectName("GlobalInput",0) ;*/
    String tStartDay = "2010-04-01";
    String tEndDay = "2010-04-30";
    String tCalManageCom = "86";
    String tIsHaveM00 = "";
    String tm="D:/zhujc/lis6.5/ui/f1print/exceltemplate/";
    GlobalInput tGI = new GlobalInput();
    tGI.ManageCom = "8613";
    tGI.Operator = "AutoPrt";
    VData tVData = new VData();

    tVData.addElement(tStartDay);
    tVData.addElement(tEndDay);
    tVData.addElement(tCalManageCom);
    tVData.addElement(tIsHaveM00);
    tVData.addElement(tGI);
    tVData.addElement(tm);

    tLAAgentTempfeePolBL.submitData(tVData,"");
    
    //logger.debug(new DecimalFormat("0.00%").format((double)0.2525)); // 活动率);
  }
}

