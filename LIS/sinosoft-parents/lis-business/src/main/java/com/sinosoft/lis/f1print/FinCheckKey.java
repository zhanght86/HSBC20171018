package com.sinosoft.lis.f1print;
import org.apache.log4j.Logger;

public class FinCheckKey {
private static Logger logger = Logger.getLogger(FinCheckKey.class);
	private String[] Row;
	private String[] Column;
	private String mKey = "";

	public FinCheckKey(String tRow[], String tColumn[]) {
		this.Row = tRow;
		this.Column = tColumn;
		String ttRow = "";
		String ttColumn = "";
		for (int i = 0; i < this.Row.length; i++) {
			ttRow = ttRow + this.Row[i];
		}

		for (int i = 0; i < this.Column.length; i++) {
			ttColumn = ttColumn + this.Column[i];
		}
		this.mKey = ttRow + ttColumn;

	}

	public void setRow(String tRow[]) {
		this.Row = tRow;
	}

	public void setColumn(String tColumn[]) {
		this.Column = tColumn;
	}

	public String[] getRow() {
		return this.Row;
	}

	public String[] getColumn() {
		return this.Column;
	}

	public boolean equals(Object o) {
		if (!(o instanceof FinCheckKey)) {
			return false;
		}
		if (!this.mKey.equals(((FinCheckKey) o).mKey)) {
			return false;
		}
		return true;
	}

	public int hashCode() {
		return (this.mKey.hashCode());
	}
	/*
	 * public boolean equals(Object o) { if (!(o instanceof FinCheckKey)) return
	 * false; if (this.Row.length!=((FinCheckKey)o).Row.length) return false; if
	 * (this.Column.length!=((FinCheckKey)o).Column.length) return false; for
	 * (int i=0;i<this.Row.length;i++) { if
	 * (!this.Row[i].equals(((FinCheckKey)o).Row[i])) { return false; } } for
	 * (int i=0;i<this.Column.length;i++) { if
	 * (!this.Column[i].equals(((FinCheckKey)o).Column[i])) { return false; } }
	 * return true; }
	 * 
	 * public int hashCode() { String tRow=""; String tColumn=""; for (int i=0;i<this.Row.length;i++) {
	 * tRow = tRow + this.Row[i]; }
	 * 
	 * for (int i=0;i<this.Column.length;i++) { tColumn = tColumn +
	 * this.Column[i]; } String t=tRow+tColumn; Integer i = new Integer(t); int
	 * im = i.intValue(); logger.debug(im); return im; }
	 */
}
