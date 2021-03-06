/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */

package com.sinosoft.lis.vdb;
import org.apache.log4j.Logger;

import java.sql.*;
import com.sinosoft.lis.schema.LCRReportSchema;
import com.sinosoft.lis.vschema.LCRReportSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

/*
 * <p>ClassName: LCRReportDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: sinosoft </p>
 * @Database: 承保流程
 */
public class LCRReportDBSet extends LCRReportSet
{
private static Logger logger = Logger.getLogger(LCRReportDBSet.class);

	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;


	// @Constructor
	public LCRReportDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LCRReport");
		mflag = true;
	}

	public LCRReportDBSet()
	{
		db = new DBOper( "LCRReport" );
	}
	// @Method
	public boolean deleteSQL()
	{
		if (db.deleteSQL(this))
		{
		        return true;
		}
		else
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCRReportDBSet";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);
			return false;
		}
	}

    /**
     * 删除操作
     * 删除条件：主键
     * @return boolean
     */
	public boolean delete()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("DELETE FROM LCRReport WHERE  ProposalContNo = ? AND PrtSeq = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getProposalContNo());
			}
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getPrtSeq());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCRReport");
		sqlObj.setSQL(4, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCRReportDBSet";
			tError.functionName = "delete()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 更新操作
     * 更新条件：主键
     * @return boolean
     */
	public boolean update()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("UPDATE LCRReport SET  GrpContNo = ? , ContNo = ? , ProposalContNo = ? , PrtSeq = ? , AppntNo = ? , AppntName = ? , CustomerNo = ? , Name = ? , ManageCom = ? , Contente = ? , ReplyContente = ? , ReplyFlag = ? , Operator = ? , ReplyOperator = ? , ReplyDate = ? , ReplyTime = ? , MakeDate = ? , MakeTime = ? , ModifyDate = ? , ModifyTime = ? , GrpPrtSeq = ? , Remark = ? , RReportReason = ? , RReportFee = ? , SendDate = ? , SendTime = ? WHERE  ProposalContNo = ? AND PrtSeq = ?");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getProposalContNo());
			}
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtSeq());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAppntName());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCustomerNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getName());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getContente() == null || this.get(i).getContente().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getContente());
			}
			if(this.get(i).getReplyContente() == null || this.get(i).getReplyContente().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getReplyContente());
			}
			if(this.get(i).getReplyFlag() == null || this.get(i).getReplyFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getReplyFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOperator());
			}
			if(this.get(i).getReplyOperator() == null || this.get(i).getReplyOperator().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getReplyOperator());
			}
			if(this.get(i).getReplyDate() == null || this.get(i).getReplyDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getReplyDate()));
			}
			if(this.get(i).getReplyTime() == null || this.get(i).getReplyTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getReplyTime());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getGrpPrtSeq() == null || this.get(i).getGrpPrtSeq().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGrpPrtSeq());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getRemark());
			}
			if(this.get(i).getRReportReason() == null || this.get(i).getRReportReason().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRReportReason());
			}
			pstmt.setDouble(24, this.get(i).getRReportFee());
			if(this.get(i).getSendDate() == null || this.get(i).getSendDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getSendDate()));
			}
			if(this.get(i).getSendTime() == null || this.get(i).getSendTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSendTime());
			}
			// set where condition
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(27,null);
			} else {
				pstmt.setString(27, this.get(i).getProposalContNo());
			}
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(28,null);
			} else {
				pstmt.setString(28, this.get(i).getPrtSeq());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCRReport");
		sqlObj.setSQL(2, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCRReportDBSet";
			tError.functionName = "update()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

    /**
     * 新增操作
     * @return boolean
     */
	public boolean insert()
	{
		PreparedStatement pstmt = null;

		if( !mflag ) {
			con = DBConnPool.getConnection();
		}

		try
		{
            int tCount = this.size();
			pstmt = con.prepareStatement("INSERT INTO LCRReport(GrpContNo ,ContNo ,ProposalContNo ,PrtSeq ,AppntNo ,AppntName ,CustomerNo ,Name ,ManageCom ,Contente ,ReplyContente ,ReplyFlag ,Operator ,ReplyOperator ,ReplyDate ,ReplyTime ,MakeDate ,MakeTime ,ModifyDate ,ModifyTime ,GrpPrtSeq ,Remark ,RReportReason ,RReportFee ,SendDate ,SendTime) VALUES( ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)");
            for (int i = 1; i <= tCount; i++)
            {
			if(this.get(i).getGrpContNo() == null || this.get(i).getGrpContNo().equals("null")) {
				pstmt.setString(1,null);
			} else {
				pstmt.setString(1, this.get(i).getGrpContNo());
			}
			if(this.get(i).getContNo() == null || this.get(i).getContNo().equals("null")) {
				pstmt.setString(2,null);
			} else {
				pstmt.setString(2, this.get(i).getContNo());
			}
			if(this.get(i).getProposalContNo() == null || this.get(i).getProposalContNo().equals("null")) {
				pstmt.setString(3,null);
			} else {
				pstmt.setString(3, this.get(i).getProposalContNo());
			}
			if(this.get(i).getPrtSeq() == null || this.get(i).getPrtSeq().equals("null")) {
				pstmt.setString(4,null);
			} else {
				pstmt.setString(4, this.get(i).getPrtSeq());
			}
			if(this.get(i).getAppntNo() == null || this.get(i).getAppntNo().equals("null")) {
				pstmt.setString(5,null);
			} else {
				pstmt.setString(5, this.get(i).getAppntNo());
			}
			if(this.get(i).getAppntName() == null || this.get(i).getAppntName().equals("null")) {
				pstmt.setString(6,null);
			} else {
				pstmt.setString(6, this.get(i).getAppntName());
			}
			if(this.get(i).getCustomerNo() == null || this.get(i).getCustomerNo().equals("null")) {
				pstmt.setString(7,null);
			} else {
				pstmt.setString(7, this.get(i).getCustomerNo());
			}
			if(this.get(i).getName() == null || this.get(i).getName().equals("null")) {
				pstmt.setString(8,null);
			} else {
				pstmt.setString(8, this.get(i).getName());
			}
			if(this.get(i).getManageCom() == null || this.get(i).getManageCom().equals("null")) {
				pstmt.setString(9,null);
			} else {
				pstmt.setString(9, this.get(i).getManageCom());
			}
			if(this.get(i).getContente() == null || this.get(i).getContente().equals("null")) {
				pstmt.setString(10,null);
			} else {
				pstmt.setString(10, this.get(i).getContente());
			}
			if(this.get(i).getReplyContente() == null || this.get(i).getReplyContente().equals("null")) {
				pstmt.setString(11,null);
			} else {
				pstmt.setString(11, this.get(i).getReplyContente());
			}
			if(this.get(i).getReplyFlag() == null || this.get(i).getReplyFlag().equals("null")) {
				pstmt.setString(12,null);
			} else {
				pstmt.setString(12, this.get(i).getReplyFlag());
			}
			if(this.get(i).getOperator() == null || this.get(i).getOperator().equals("null")) {
				pstmt.setString(13,null);
			} else {
				pstmt.setString(13, this.get(i).getOperator());
			}
			if(this.get(i).getReplyOperator() == null || this.get(i).getReplyOperator().equals("null")) {
				pstmt.setString(14,null);
			} else {
				pstmt.setString(14, this.get(i).getReplyOperator());
			}
			if(this.get(i).getReplyDate() == null || this.get(i).getReplyDate().equals("null")) {
				pstmt.setDate(15,null);
			} else {
				pstmt.setDate(15, Date.valueOf(this.get(i).getReplyDate()));
			}
			if(this.get(i).getReplyTime() == null || this.get(i).getReplyTime().equals("null")) {
				pstmt.setString(16,null);
			} else {
				pstmt.setString(16, this.get(i).getReplyTime());
			}
			if(this.get(i).getMakeDate() == null || this.get(i).getMakeDate().equals("null")) {
				pstmt.setDate(17,null);
			} else {
				pstmt.setDate(17, Date.valueOf(this.get(i).getMakeDate()));
			}
			if(this.get(i).getMakeTime() == null || this.get(i).getMakeTime().equals("null")) {
				pstmt.setString(18,null);
			} else {
				pstmt.setString(18, this.get(i).getMakeTime());
			}
			if(this.get(i).getModifyDate() == null || this.get(i).getModifyDate().equals("null")) {
				pstmt.setDate(19,null);
			} else {
				pstmt.setDate(19, Date.valueOf(this.get(i).getModifyDate()));
			}
			if(this.get(i).getModifyTime() == null || this.get(i).getModifyTime().equals("null")) {
				pstmt.setString(20,null);
			} else {
				pstmt.setString(20, this.get(i).getModifyTime());
			}
			if(this.get(i).getGrpPrtSeq() == null || this.get(i).getGrpPrtSeq().equals("null")) {
				pstmt.setString(21,null);
			} else {
				pstmt.setString(21, this.get(i).getGrpPrtSeq());
			}
			if(this.get(i).getRemark() == null || this.get(i).getRemark().equals("null")) {
				pstmt.setString(22,null);
			} else {
				pstmt.setString(22, this.get(i).getRemark());
			}
			if(this.get(i).getRReportReason() == null || this.get(i).getRReportReason().equals("null")) {
				pstmt.setString(23,null);
			} else {
				pstmt.setString(23, this.get(i).getRReportReason());
			}
			pstmt.setDouble(24, this.get(i).getRReportFee());
			if(this.get(i).getSendDate() == null || this.get(i).getSendDate().equals("null")) {
				pstmt.setDate(25,null);
			} else {
				pstmt.setDate(25, Date.valueOf(this.get(i).getSendDate()));
			}
			if(this.get(i).getSendTime() == null || this.get(i).getSendTime().equals("null")) {
				pstmt.setString(26,null);
			} else {
				pstmt.setString(26, this.get(i).getSendTime());
			}

		// only for debug purpose
		SQLString sqlObj = new SQLString("LCRReport");
		sqlObj.setSQL(1, this.get(i));
		sqlObj.getSQL();

                pstmt.addBatch();
            }
            pstmt.executeBatch();
			pstmt.close();
		} catch (Exception ex) {
			// @@错误处理
ex.printStackTrace();
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCRReportDBSet";
			tError.functionName = "insert()";
			tError.errorMessage = ex.toString();
			this.mErrors .addOneError(tError);

			try {
				pstmt.close();
			} catch (Exception e){e.printStackTrace();}

			if( !mflag ) {
				try {
					con.close();
				} catch (Exception e){e.printStackTrace();}
			}

			return false;
		}

		if( !mflag ) {
			try {
				con.close();
			} catch (Exception e){e.printStackTrace();}
		}

		return true;
	}

}
