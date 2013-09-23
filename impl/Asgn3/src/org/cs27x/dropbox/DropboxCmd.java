package org.cs27x.dropbox;

import java.io.Serializable;
import java.util.Arrays;

public class DropboxCmd implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum OpCode {
		ADD, REMOVE, UPDATE, SYNC, GET

	}

	private String from_ = "";
	private String path_ = "";
	private byte[] data_ = "".getBytes();
	private OpCode opCode_ = OpCode.ADD;

	public String getFrom() {
		return from_;
	}

	public void setFrom(String from) {
		from_ = from;
	}

	public String getPath() {
		return path_;
	}

	public void setPath(String path) {
		path_ = path;
	}

	public byte[] getData() {
		return data_;
	}

	public void setData(byte[] data) {
		data_ = data;
	}

	public OpCode getOpCode() {
		return opCode_;
	}

	public void setOpCode(OpCode opCode) {
		opCode_ = opCode;
	}

	// implmented equals to make testing easier
	@Override
	public boolean equals(Object aThat) {
		if (this == aThat)
			return true;
		if (!(aThat instanceof DropboxCmd))
			return false;
		DropboxCmd that = (DropboxCmd) aThat;
		if (Arrays.equals(this.data_, that.data_) == false) {
			return false;
		}
		// if ((this.from_ != null) && (that.from_ != null))
		if (this.from_.equals(that.from_) == false) {
			return false;
		}
		if (this.path_.equals(that.path_) == false) {
			return false;
		}
		if (this.opCode_ != that.opCode_) {
			return false;
		}
		return true;
	}

}
