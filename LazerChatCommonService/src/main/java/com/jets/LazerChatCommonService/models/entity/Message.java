package com.jets.LazerChatCommonService.models.entity;

import java.io.Serializable;

public class Message implements Serializable {

	private String messageString;
	private MessageState state;
	private MessageStyle messageStyle;
	private User user;

	public Message() {
		this.state = MessageState.UNDELIVERED;
	}

	public Message(String messageString, MessageState state, MessageStyle messageStyle) {
		super();
		this.messageString = messageString;
		this.state = state;
		this.messageStyle = messageStyle;
	}

	public String getMessageString() {
		return messageString;
	}

	public void setMessageString(String messageString) {
		this.messageString = messageString;
	}

	public MessageState getState() {
		return state;
	}

	public void setState(MessageState state) {
		this.state = state;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MessageStyle getMessageStyle() {
		return messageStyle;
	}

	public void setMessageStyle(MessageStyle messageStyle) {
		this.messageStyle = messageStyle;
	}

	//Inner classes
	private enum MessageState {
		UNDELIVERED, DELIVERED, SEEN
	};

	private class MessageStyle {

		private int size;
		private String color;
		private String fontFamily;
		private boolean isBold;
		private boolean isItalic;
		private boolean isUnderline;

		public MessageStyle() {
			// krokiii
			this.size = 12;
			this.color = "black";
			this.fontFamily = "console";
			this.isBold = false;
			this.isItalic = false;
			this.isUnderline = false;
		}

		public MessageStyle(int size, String color, String fontFamily, boolean isBold, boolean isItalic,
							boolean isUnderline) {
			super();
			this.size = size;
			this.color = color;
			this.fontFamily = fontFamily;
			this.isBold = isBold;
			this.isItalic = isItalic;
			this.isUnderline = isUnderline;
		}

		// Getter & Setter
		public int getSize() {
			return size;
		}

		public void setSize(int size) {
			this.size = size;
		}

		public String getColor() {
			return color;
		}

		public void setColor(String color) {
			this.color = color;
		}

		public String getFontFamily() {
			return fontFamily;
		}

		public void setFontFamily(String fontFamily) {
			this.fontFamily = fontFamily;
		}

		public boolean isBold() {
			return isBold;
		}

		public void setBold(boolean isBold) {
			this.isBold = isBold;
		}

		public boolean isItalic() {
			return isItalic;
		}

		public void setItalic(boolean isItalic) {
			this.isItalic = isItalic;
		}

		public boolean isUnderline() {
			return isUnderline;
		}

		public void setUnderline(boolean isUnderline) {
			this.isUnderline = isUnderline;
		}

	}
}
