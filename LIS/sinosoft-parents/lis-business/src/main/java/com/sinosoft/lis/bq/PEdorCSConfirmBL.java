package com.sinosoft.lis.bq;
import org.apache.log4j.Logger;

import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.lis.db.*;
import com.sinosoft.lis.vdb.*;
import com.sinosoft.lis.bl.*;
import com.sinosoft.lis.vbl.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.lis.acc.*;
import com.sinosoft.lis.f1print.BqNameFun;


/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 保全账户赎回确认处理类</p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Sinosoft</p>
 * @author ck
 * @version 1.0
 */
public class PEdorCSConfirmBL implements EdorConfirm {
private static Logger logger = Logger.getLogger(PEdorCSConfirmBL.class);
    /** 错误处理类，每个需要错误处理的类中都放置该类 */
    public CErrors mErrors = new CErrors();

    /** 往后面传输数据的容器 */
    private VData mInputData;


    /** 往界面传输数据的容器 */
    private VData mResult = new VData();


    /** 数据操作字符串 */
    private String mOperate;
    private String CurrentDate = PubFun.getCurrentDate();
    private String CurrentTime = PubFun.getCurrentTime();
    private GlobalInput mGlobalInput = new GlobalInput();
    /**  */
    private LPEdorItemSchema mLPEdorItemSchema = new LPEdorItemSchema();

    /** 全局数据 */
    private MMap map = new MMap();


    public PEdorCSConfirmBL() {}


    /**
     * 传输数据的公共方法
     * @param: cInputData 输入的数据
     *         cOperate 数据操作
     * @return:
     */

    public boolean submitData(VData cInputData, String cOperate) {
        //将操作数据拷贝到本类中
        mInputData = (VData) cInputData.clone();
        mOperate = cOperate;

        //得到外部传入的数据,将数据备份到本类中
        if (!getInputData()) {
          return false;
        }

          //数据准备操作
        if (!dealData()) {
            return false;
         }

        if (!prepareOutputData()) {
            return false;
        }
        return true;
    }

    public VData getResult() {
        return mResult;
    }

    private boolean prepareOutputData() {

        mResult.clear();
        mResult.add(map);

        return true;
    }


    /**
     * 从输入数据中得到所有对象
     * @return
     */
    private boolean getInputData() {
        try {
            mLPEdorItemSchema = (LPEdorItemSchema) mInputData.
                                   getObjectByObjectName("LPEdorItemSchema",
                    0);
            mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
                    "GlobalInput", 0);
        } catch (Exception e) {
            CError.buildErr(this, "接收数据失败");
            return false;
        }
        return true;
    }


    /**
     * 准备需要保存的数据
     */
    private boolean dealData() {
    	Reflections tRef = new Reflections();
    	
    	LCContDB tLCContDB = new LCContDB();
    	tLCContDB.setContNo(mLPEdorItemSchema.getContNo());
		LCContSet tLCContSet = tLCContDB.query();
		if (tLCContDB.mErrors.needDealError()) {
			CError.buildErr(this, "查询保全个人保单信息失败!");
			return false;
		}
		if (tLCContSet == null || tLCContSet.size() < 1) {
			CError.buildErr(this, "没有查到保单信息!");
			return false;
		}
		ES_DOC_MAINDB sES_DOC_MAINDB = new ES_DOC_MAINDB();
		sES_DOC_MAINDB.setDocCode(mLPEdorItemSchema.getEdorAcceptNo());
		ES_DOC_MAINSchema sES_DOC_MAINSchema = sES_DOC_MAINDB.query().get(1);
		
		ES_DOC_PAGESDB sES_DOC_PAGESDB = new ES_DOC_PAGESDB();
		sES_DOC_PAGESDB.setDocID(sES_DOC_MAINSchema.getDocID());
		sES_DOC_PAGESDB.setPageType("7");//7为签名
		ES_DOC_PAGESSet sES_DOC_PAGESSet = sES_DOC_PAGESDB.query();
		if(sES_DOC_PAGESSet == null && sES_DOC_PAGESSet.size()<1 ){
			CError.buildErr(this, "没有查到签名文件!");
			return false;
		}
		ES_DOC_PAGESSchema sES_DOC_PAGESSchema = sES_DOC_PAGESSet.get(1);
		ES_DOC_PAGESSchema oES_DOC_PAGESSchema = null;
		int pageCode = 1;
		int docID= 0;

		LCContSchema tLCContSchema = tLCContSet.get(1);
		String prtNo = tLCContSchema.getPrtNo();
		ES_DOC_MAINDB tES_DOC_MAINDB = new ES_DOC_MAINDB();
		tES_DOC_MAINDB.setDocCode(prtNo);
		ES_DOC_MAINSet tES_DOC_MAINSet = tES_DOC_MAINDB.query();
		if(tES_DOC_MAINSet != null && tES_DOC_MAINSet.size()>0){
			//存在原扫描件
			ES_DOC_MAINSchema tES_DOC_MAINSchema = tES_DOC_MAINSet.get(1);
			docID = tES_DOC_MAINSchema.getDocID();
			ES_DOC_PAGESDB oES_DOC_PAGESDB = new ES_DOC_PAGESDB();
			oES_DOC_PAGESDB.setDocID(docID);
			oES_DOC_PAGESDB.setPageType("7");//7为签名
			ES_DOC_PAGESSet oES_DOC_PAGESSet = oES_DOC_PAGESDB.query();
			if(oES_DOC_PAGESSet != null && oES_DOC_PAGESSet.size()>0 ){
				//原来签名存在
				oES_DOC_PAGESSchema = oES_DOC_PAGESSet.get(1);
				pageCode = oES_DOC_PAGESSchema.getPageCode();
				ES_DOC_PAGESSchema nbES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();//转为备份
				tRef.transFields(nbES_DOC_PAGESSchema, oES_DOC_PAGESSchema);
				nbES_DOC_PAGESSchema.setDocID(sES_DOC_MAINSchema.getDocID());
				nbES_DOC_PAGESSchema.setPageCode(sES_DOC_PAGESSchema.getPageCode());
				map.put(oES_DOC_PAGESSchema, "DELETE");
				map.put(nbES_DOC_PAGESSchema, "INSERT");
			}else {
				String tSql = "Select max(pagecode)+1 from es_doc_pages where docid = '?docid?' and pagetype <> '7'";
				SQLwithBindVariables sqlbv=new SQLwithBindVariables();
				sqlbv.sql(tSql);
				sqlbv.put("docid", tES_DOC_MAINSchema.getDocID());
				String tPageCode = new ExeSQL().getOneValue(sqlbv);
				pageCode = Integer.parseInt(tPageCode);
			}		
			
		}else{
			//不存在原扫描件，生成新的
			ES_DOC_MAINSchema tES_DOC_MAINSchema = new ES_DOC_MAINSchema();
			String tPageID = PubFun1.CreateMaxNo("PageID", 1);
			docID = Integer.parseInt(tPageID);
			tRef.transFields(tES_DOC_MAINSchema, sES_DOC_MAINSchema);
			tES_DOC_MAINSchema.setDocID(tPageID);
			tES_DOC_MAINSchema.setDocCode(prtNo);
			tES_DOC_MAINSchema.setBussType("TB");
			tES_DOC_MAINSchema.setSubType("UA001");
			tES_DOC_MAINSchema.setNumPages(0);
			map.put(tES_DOC_MAINSchema, "INSERT");
			
			ES_DOC_RELATIONSchema tES_DOC_RELATIONSchema = new ES_DOC_RELATIONSchema();
			tES_DOC_RELATIONSchema.setDocID(tPageID);
			tES_DOC_RELATIONSchema.setBussNoType("11");
			tES_DOC_RELATIONSchema.setBussNo(prtNo);
			tES_DOC_RELATIONSchema.setDocCode(prtNo);
			tES_DOC_RELATIONSchema.setBussType("TB");
			tES_DOC_RELATIONSchema.setSubType("UA001");
			tES_DOC_RELATIONSchema.setRelaFlag("0");
			map.put(tES_DOC_RELATIONSchema, "INSERT");
			
		}
		
		map.put(sES_DOC_PAGESSchema, "DELETE");
		ES_DOC_PAGESSchema nsES_DOC_PAGESSchema = new ES_DOC_PAGESSchema();
		tRef.transFields(nsES_DOC_PAGESSchema, sES_DOC_PAGESSchema);
		nsES_DOC_PAGESSchema.setDocID(docID);
		nsES_DOC_PAGESSchema.setPageCode(pageCode);
		nsES_DOC_PAGESSchema.setModifyDate(CurrentDate);
		nsES_DOC_PAGESSchema.setModifyTime(CurrentTime);
		map.put(nsES_DOC_PAGESSchema, "INSERT");
		
        return true;
    }

    public CErrors getErrors() {
        return mErrors;
    }
}
