package com.mustr.common.entity;

/**
 * 消息
 * @author mustr
 *
 */
public class Mess extends Mu {
	private static final long serialVersionUID = 4211073577732505646L;

	private String content;

	public Mess() {
		super();
	}

	public Mess(Long id, String name) {
		super(id, name);
	}

	public Mess(String content) {
		super();
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
