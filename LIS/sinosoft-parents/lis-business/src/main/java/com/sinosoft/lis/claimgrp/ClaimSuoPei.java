package com.sinosoft.lis.claimgrp;
import org.apache.log4j.Logger;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *理赔结案报告单     苏建栋   2007-01-09
 * @author not attributable
 * @version 1.0
 */
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.sinosoft.lis.claim.LLPRTPubFunBL;
import com.sinosoft.lis.db.LAAgentDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.schema.LAAgentSchema;
import com.sinosoft.lis.schema.LLAccidentSchema;
import com.sinosoft.lis.schema.LLSubReportSchema;
import com.sinosoft.lis.vschema.LLReportAffixSet;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.StrTool;
import com.sinosoft.utility.TransferData;
import com.sinosoft.utility.VData;
import com.sinosoft.service.BusinessService;


public class ClaimSuoPei implements BusinessService{
private static Logger logger = Logger.getLogger(ClaimSuoPei.class);
	
	public CErrors mErrors = new CErrors();
	
	private VData mResult = new VData();
//	 输入数据
	private VData rInputData;

	private String rOperation;

	private GlobalInput rGlobalInput;

	private TransferData rTransferData;
	
	//判断是打印单个客户还是全部客户
	private String flag;
	
	private String filename;
	
	private String RealPath;
	
	private String RptNo;

	// 输出数据
	private MMap tMap;

	private VData rResultData;

     
	public static PdfPCell cell;
    public ClaimSuoPei() {
    }
    
//  ==========================================================================

	/**
	 * 外部调用本类的业务处理接口
	 * 
	 * @param VData
	 * @param String
	 * @return boolean
	 */
	public boolean submitData(VData cInputData, String cOperate) {
		// 接收参数
		if (cInputData == null) {
			CError tError = new CError();
            tError.moduleName = "ClaimSuoPei";
            tError.functionName = "ClaimDanZhengDetail";
            tError.errorMessage = "无法获取提交的数据, 请确认数据不为空！";
            this.mErrors.addOneError(tError);
            logger.debug("打印失败，原因是：无法获取提交的数据, 请确认数据不为空！");
            return false;
			
		} else {
			rInputData = new VData();
			rInputData = (VData) cInputData.clone();
		}
		rOperation = (cOperate != null) ? cOperate.trim() : "";

//		业务处理
        if (!getInputData())return false;
		if (!dealData())
			return false;
		
		// 垃圾处理
		collectGarbage();
		return true;
	}

	/**
	 * 获取外部传入数据和校验必录字段的合法性
	 * 
	 * @param null
	 * @return boolean
	 */

	private boolean getInputData() {

		rTransferData = (TransferData) rInputData.getObjectByObjectName(
				"TransferData", 0);
		if (rTransferData == null) {
			CError tError = new CError();
            tError.moduleName = "ClaimSuoPei";
            tError.functionName = "getInputData";
            tError.errorMessage = "无法TransferData数据, 请确认数据不为空！";
            this.mErrors.addOneError(tError);
            logger.debug("打印失败，原因是：无法TransferData数据, 请确认数据不为空！");
            return false;
		}
		filename = (String) rTransferData.getValueByName("filename");
		RptNo = (String) rTransferData.getValueByName("RptNo");
		flag = (String) rTransferData.getValueByName("flag");
		RealPath = (String) rTransferData.getValueByName("RealPath");
		
		
		return true;
	}

	// ==========================================================================
//	 ==========================================================================
	/**
	 * 本类的核心业务处理过程
	 * 
	 * @param null
	 * @return boolean
	 */
	private boolean dealData(){
		if(flag.equals("1")){
			if(!this.ClaimDanZheng(filename,RealPath,RptNo)){
				return false;
			}
		}
		return true;
	}
    
        
    public String ClaimOverDetail(String filename,String RealPath,String RgtNo)
    {
        String state="";
        ExeSQL tExeSQL=new ExeSQL();
        SSRS  tSSRS2 = new SSRS();
        String GrPContNo=tExeSQL.getOneValue("select grpcontno from llregister where rgtno='"+RgtNo+"'");
        try{
            //定义字体
//            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
//                                              BaseFont.NOT_EMBEDDED);
        	BaseFont bf =
   			 BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
   			 BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font Font12 = new Font(bf, 14, Font.NORMAL);
            Font Font10 = new Font(bf, 12, Font.NORMAL);

            //Document document = new Document(PageSize.A4.rotate());
            Document document = new Document(PageSize.A4);
            //生成的PDF文件文件名
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(filename));
            document.setMargins(36, 36, 36, 36);
            document.open();

            String sql = "select distinct customerno from llsubreport where rptno='" + RgtNo +"'";
            tSSRS2 = tExeSQL.execSQL(sql);
            for (int numPage = 1; numPage <= tSSRS2.MaxRow; numPage++) {
                logger.debug("numPage=" + numPage);
            //写logo图片
            String filedrive=filename.substring(0,1);
            Image gif = Image.getInstance(RealPath+"\\common\\images\\minsheng2.GIF");
            gif.setAlignment(Image.RIGHT | Image.UNDERLYING);
            //gif.setAlignment(Image.RIGHT);
            document.add(gif);

            document.add(new Paragraph(Stringtools.createspace(70)+"理赔结案报告单", Font12));
            document.add(new Paragraph("  ", Font12));
            document.add(new Paragraph("  ", Font12));
            float[] widths = {20f, 20f};
            PdfPTable table = new PdfPTable(widths);
            String GrpContNo=tExeSQL.getOneValue("select grpcontno from llregister where rgtno='"+RgtNo+"'");
            cell = new PdfPCell(new Phrase("保单号："+GrpContNo, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("赔案号："+RgtNo, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            table.setWidthPercentage(100);
            document.add(table);

            float[] widths1 = {20f};
            table = new PdfPTable(widths1);
            String GrpName=tExeSQL.getOneValue("select grpname from lcgrpcont where grpcontno='"+GrpContNo+"'");
            cell = new PdfPCell(new Phrase("投保单位："+GrpName, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            String peoplestr="被保险人：";
            SSRS tSSRS=new SSRS();
            tSSRS=tExeSQL.execSQL("select distinct a.customerno,b.insuredname from llclaimdetail  a,lcpol b where a.clmno='"+RgtNo+"' and b.grpcontno='"+GrpContNo+"' and a.customerno=b.insuredno  and a.riskcode=b.riskcode and rownum<=2");
             if (tSSRS.getMaxRow()==1)
             {
                 peoplestr=peoplestr+tSSRS.GetText(1,2)+"      1人 ";
             }
             else
             {
                 peoplestr=peoplestr+tSSRS.GetText(1,2)+"、"+tSSRS.GetText(2,2);
                 String tmpnum=tExeSQL.getOneValue("select count(unique customerno) from llclaimdetail where clmno='"+RgtNo+"'");
                 int num=Stringtools.stringtoint(tmpnum);
                 if (num==2)
                 {
                     peoplestr=peoplestr+" 2 人";
                 }
                 else
                 {
                     peoplestr=peoplestr+" 等 "+tmpnum+" 人";
                 }
             }
            cell = new PdfPCell(new Phrase(peoplestr, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            tSSRS=tExeSQL.execSQL("select unique riskcode from llclaimdetail where clmno='"+RgtNo+"'");
            String risknamestr="";
            for (int i=1;i<=tSSRS.getMaxRow();i++)
            {
                String RiskCode=tSSRS.GetText(i,1);
                String RiskName=tExeSQL.getOneValue("select riskname from lmriskapp where riskcode='"+RiskCode+"'");
                risknamestr=risknamestr+RiskName+"、";
            }
            risknamestr="险种："+risknamestr.substring(0,risknamestr.lastIndexOf("、"));
            cell = new PdfPCell(new Phrase(risknamestr, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            String accdesc="事故描述：\n";
            SSRS tSSRS1=new SSRS();
            tSSRS1=tExeSQL.execSQL("select customerno,accdesc from llsubreport where subrptno='"+RgtNo+"'  and accdesc is not null");
            if (tSSRS1!=null)
            {
                for (int i=1;i<=tSSRS1.getMaxRow();i++)
                {
                    String name=tExeSQL.getOneValue("select insuredname from lcpol where grpcontno='"+GrPContNo+"' and insuredno='"+tSSRS1.GetText(i,1)+"'");
                    accdesc=accdesc+name+"："+tSSRS1.GetText(i,2)+"\n";
                }
                for (int i=1;i<=11;i++)
                {
                    accdesc=accdesc+"\n";
                }
            }
            else
            {
                accdesc=accdesc+"\n\n\n\n\n\n\n\n\n";
            }

            cell = new PdfPCell(new Phrase(accdesc, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("赔付金额计算：\n\n\n\n\n\n\n\n\n\n\n\n\n\n", Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            String jielun="审核结论：";
            jielun=jielun+tExeSQL.getOneValue("select codename from ldcode where codetype='llclaimconclusion' and code=(select givetype from llclaim where rgtno='"+RgtNo+"')");
            //jielun=jielun+"                                （□正常赔付□拒赔□协议赔付□通融赔付）";
            cell = new PdfPCell(new Phrase(jielun, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            String realpay=tExeSQL.getOneValue("select sum(realpay) from llclaimdetail where clmno='"+RgtNo+"'");
            realpay=Stringtools.stringtostring(realpay,2);
            String jine="金额（大写）："+PubFun.getChnMoney(Stringtools.stringtodouble(realpay));
            jine=jine+"             ￥：  "+realpay+"  元";
            cell = new PdfPCell(new Phrase(jine, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            table.setWidthPercentage(100);
            document.add(table);

            float[] widths2 = {20f, 20f};
            table = new PdfPTable(widths2);
            //立案人
            String opetator_lian = tExeSQL.getOneValue("select b.username from llregister  a,lduser b where a.rgtno='"+RgtNo+"'  and a.operator=b.usercode");
            String date_lian     = tExeSQL.getOneValue("select to_char(rgtdate,'yyyy-mm-dd') from llregister where rgtno='"+RgtNo+"'");
            date_lian=StrTool.getChnDate(date_lian);
            cell = new PdfPCell(new Phrase("立案人："+opetator_lian, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("日期："+date_lian, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            //复核人
            String opetator_uw = tExeSQL.getOneValue("select b.username from llclaimuwmain  a,lduser b where a.clmno='"+RgtNo+"'  and a.operator=b.usercode");
            String date_uw     = tExeSQL.getOneValue("select to_char(endcasedate,'yyyy-mm-dd') from llclaim where clmno='"+RgtNo+"'");
            date_uw=StrTool.getChnDate(date_uw);
            cell = new PdfPCell(new Phrase("复核人："+opetator_uw, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("日期："+date_uw, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            //审批人
            String date_shenpi     = tExeSQL.getOneValue("select to_char(AuditDate,'yyyy-mm-dd') from llclaimuwmain where clmno='"+RgtNo+"'");
            if (date_shenpi.trim().length()==0)
            {
                opetator_uw="";
            }
            else
            {
                date_shenpi = StrTool.getChnDate(date_shenpi);
            }
            cell = new PdfPCell(new Phrase("审批人："+opetator_uw, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("日期："+date_shenpi, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            table.setWidthPercentage(100);
            document.add(table);
//          建立新页(相当于人工插入分页符)
            document.newPage();
            }
            
            document.close();

        }catch(Exception e)
        {
            state="打印失败，原因是："+e.getMessage();
        }

        return state;
    }
    
    public boolean ClaimDanZhengDetail(Document document,Image gif,String insuredNo,String RptNo)
    {
    	
    	String state="";
        ExeSQL tExeSQL=new ExeSQL();
        SSRS  tSSRS2 = new SSRS();
        String GrPContNo=tExeSQL.getOneValue("select grpcontno from llreport where rptno='"+RptNo+"'");
        try{
            //定义字体
//            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
//                                              BaseFont.NOT_EMBEDDED);
        	BaseFont bf =
   			 BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
   			 BaseFont.IDENTITY_H,BaseFont.EMBEDDED);
            Font Font12 = new Font(bf, 14, Font.NORMAL);
            Font Font10 = new Font(bf, 12, Font.NORMAL);

            
            //document.setMargins(36, 36, 36, 36);
            //document.open();

            
            //写logo图片
           
            gif.setAlignment(Image.RIGHT | Image.UNDERLYING);
            //gif.setAlignment(Image.RIGHT);
            document.add(gif);

            document.add(new Paragraph("  ", Font12));
            document.add(new Paragraph("  ", Font12));
            document.add(new Paragraph(Stringtools.createspace(106)+"理赔单证通知书", Font12));
            document.add(new Paragraph("  ", Font12));
            document.add(new Paragraph("  ", Font12));
            float[] widths = {20f, 20f};
            PdfPTable table = new PdfPTable(widths);
            String GrpContNo=tExeSQL.getOneValue("select grpcontno from llreport where rptno='"+RptNo+"'");
            cell = new PdfPCell(new Phrase("保单号："+GrpContNo, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("赔案号："+RptNo, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            table.setWidthPercentage(100);
            document.add(table);
            
            
            LLPRTPubFunBL tLLPRTPubFunBL= new LLPRTPubFunBL();
            
            
//          理赔类型---------------------------------------------------------------
            String ClaimType = tLLPRTPubFunBL.getSLLReportReason(RptNo,insuredNo);
            String sex="";
            

//          出险原因---------------------------------------------------------------
            String reason = tLLPRTPubFunBL.getLLReport(RptNo).getAccidentReason();
            String tSQL = "select codename from ldcode where code='"+reason+"' and codetype='lloccurreason'";
            ExeSQL tExeSQL1 = new ExeSQL();
            String claimRsn = tExeSQL1.getOneValue(tSQL);
            
            
            table = new PdfPTable(widths);
            cell = new PdfPCell(new Phrase("理赔类型："+ClaimType, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("出险原因："+claimRsn, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            table.setWidthPercentage(100);
            document.add(table);
            
//          出险人姓名--------------------------------------------------------------
            String tSql = "select a.name from ldperson a where "
                        + "a.customerno = '" + insuredNo + "'";
            
            String tName = tExeSQL.getOneValue(tSql);
            LLAccidentSchema tLLAccidentSchema = new LLAccidentSchema();
            tLLAccidentSchema = tLLPRTPubFunBL.getLLAccident(RptNo);
            //出险时间---------------------------------------------------------------
            String claimTime = "";
            claimTime = tLLAccidentSchema.getAccDate();
            
            table = new PdfPTable(widths);
            cell = new PdfPCell(new Phrase("出险人："+tName, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("出险日期："+claimTime, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            table.setWidthPercentage(100);
            document.add(table);
            
            if(tLLPRTPubFunBL.getCustSex(insuredNo).equals("男"))
            {
                sex ="先生";
            }
            else
            {
                sex ="女士";
            }
            float[] widths1 = {20f};
            table = new PdfPTable(widths1);
            
            cell = new PdfPCell(new Phrase("尊敬的  "+tName + "  " +sex, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            table.setWidthPercentage(100);
            document.add(table);
            
            table = new PdfPTable(widths1);
            String tmpStr="        根据保险条款及相关的法律条文，对于您报案所描述的保险事故，"
            	+ "需要请被保险人或受益人作为申请人，填写索赔申请书，并凭下列资料向公司"
            	+ "提出申请。如委托他人代为申领，请提供授权委托书及受托人身份证明。";
            cell = new PdfPCell(new Phrase(tmpStr, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            table.setWidthPercentage(100);
            document.add(table);
            
            String AffixStr = "";
            LLReportAffixSet tLLReportAffixSet = tLLPRTPubFunBL.getLLReportAffix(RptNo,insuredNo);
            for(int i = 1; i <= 25; i++)
            {
            	
            	
                
                if(i<= tLLReportAffixSet.size()){
                AffixStr +="        "+i + "."+tLLReportAffixSet.get(i).getAffixName() + "\n";
                
                }else{
                	AffixStr +="\n";
                }
                
            }
            table = new PdfPTable(widths1);
            cell = new PdfPCell(new Phrase(AffixStr, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            table.setWidthPercentage(100);
            document.add(table);
            
//          保单服务人员和电话-()------------------------------------------------------


          String tAgentCodeSql="select AgentCode from lcgrpcont where 1=1 "
                            +" and grpcontno='"+GrpContNo+"'";
          ExeSQL tAgentCodeExeSQL = new ExeSQL();
          String tAgentCode = tAgentCodeExeSQL.getOneValue(tAgentCodeSql);
          String SeveiceName="";
          String transNo = "";
          String tel = "";
          if(tAgentCode.equals("") ||tAgentCode==null)
          {
               SeveiceName="";
               transNo = "";
               tel = "";
          }
          else
          {
              LAAgentSchema tLAAgentSchema = new LAAgentSchema();
              LAAgentDB tLAAgentDB = new LAAgentDB();
              tLAAgentDB.setAgentCode(tAgentCode);
              tLAAgentDB.getInfo();
              tLAAgentSchema.setSchema(tLAAgentDB.getSchema());
               SeveiceName = tLAAgentSchema.getName();
               transNo = tLAAgentSchema.getAgentCode();
               tel = tLAAgentSchema.getPhone();
               if(tel==null){
            	   tel="    ";
               }
          }

          //经办人-----------------------------------------------------------------
          String operator="";
          LLSubReportSchema ttLLSubReportSchema = new LLSubReportSchema();
          ttLLSubReportSchema = tLLPRTPubFunBL.getLLSubReport(RptNo,insuredNo);
          operator=ttLLSubReportSchema.getOperator();
          String checkerSQL = "select UserName from llclaimuser "
                           +"where usercode='"+operator+"'";
          ExeSQL cExeSQL = new ExeSQL() ;
          String jingbanren = cExeSQL.getOneValue(checkerSQL);


          String SysDate = StrTool.getYear() + "年" + StrTool.getMonth() + "月" +
                           StrTool.getDay() + "日";
           table = new PdfPTable(widths);
           
            cell = new PdfPCell(new Phrase("保单服务人员："+tAgentCode + "-" + SeveiceName, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("联系电话："+tel, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            table.setWidthPercentage(100);
            document.add(table);
            
            table = new PdfPTable(widths);
            cell = new PdfPCell(new Phrase("经办人："+operator + "-" + jingbanren, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("通知日期："+SysDate, Font10));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);

            table.setWidthPercentage(100);
            document.add(table);
            
            document.add(new Paragraph("  ", Font12));
            document.add(new Paragraph("      若您对理赔方面有什么疑问或对"+
            		"理赔服务有什么建议，请拨打" + tel +",如有其他问题请拨打"+
            		"全国统一客户服务电话：95522", Font12));
    

        }catch(Exception e)
        {
//        	 @@错误处理
            
            CError tError = new CError();
            tError.moduleName = "ClaimSuoPei";
            tError.functionName = "ClaimDanZhengDetail";
            tError.errorMessage = "生成文件失败!";
            this.mErrors.addOneError(tError);
            logger.debug("打印失败，原因是："+e.getMessage());
            return false;
        	
        }

        return true;
    }
    
    
    public boolean ClaimDanZheng(String filename,String RealPath,String RgtNo)
    {
        String state="";
        ExeSQL tExeSQL=new ExeSQL();
        String GrPContNo=tExeSQL.getOneValue("select grpcontno from llregister where rgtno='"+RgtNo+"'");
        try{
            logger.debug("filename="+filename);
            logger.debug("RealPath="+RealPath);
            logger.debug("RptNo="+RptNo);
        	//定义字体
//            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
//                                              BaseFont.NOT_EMBEDDED);
          BaseFont bf =
			 BaseFont.createFont("c:\\windows\\fonts\\simsun.ttc,1",
			 BaseFont.IDENTITY_H,BaseFont.EMBEDDED);

            Font Font12 = new Font(bf, 14, Font.NORMAL);
            Font Font10 = new Font(bf, 12, Font.NORMAL);

            //Document document = new Document(PageSize.A4.rotate());
            Document document = new Document(PageSize.A4);
            //生成的PDF文件文件名
            try{
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(filename));
            }
            catch(Exception e){
            	logger.debug("writer出错");
            	logger.debug(e.getMessage());
            }
            document.setMargins(36, 36, 36, 36);
            document.open();
            logger.debug("aa");
            //写logo图片
            String filedrive=filename.substring(0,1);
            Image gif = Image.getInstance(RealPath+"\\common\\images\\minsheng2.GIF");
            logger.debug("bb");
            String insuredNo = "";
            String mCusNoSQL ="select distinct CustomerNo from LLSubReport where SubRptNo='" +RgtNo + "'";
            ExeSQL cusExeSQL = new ExeSQL();
            SSRS cusNoSSRS = new SSRS();
            cusNoSSRS = cusExeSQL.execSQL(mCusNoSQL);
            for(int i=1;i<=cusNoSSRS.getMaxRow();i++){
            	insuredNo = cusNoSSRS.GetText(1,1) ;
            	if(!this.ClaimDanZhengDetail(document, gif, insuredNo, RgtNo))
            		return false;
            	document.newPage();
            }
            
            document.close();

        }catch(Exception e)
        {
        	CError tError = new CError();
            tError.moduleName = "ClaimSuoPei";
            tError.functionName = "ClaimDanZheng";
            tError.errorMessage = "生成文件失败!";
            this.mErrors.addOneError(tError);
            logger.debug("打印失败，原因是："+e.getMessage());
            logger.debug("打印失败，原因是："+e.getStackTrace());
            return false;
        	
        }

        return true;
    }
    
    
    /**
     * 处理本类运行时产生的垃圾
     * @param    null
     */
    private void collectGarbage() {
        if (rInputData != null) rInputData = null;
        if (rGlobalInput != null) rGlobalInput = null;
        
    }

	public CErrors getErrors() {
		// TODO Auto-generated method stub
		return mErrors;
	}

	public VData getResult() {
		// TODO Auto-generated method stub
		return mResult;
	} 
}
