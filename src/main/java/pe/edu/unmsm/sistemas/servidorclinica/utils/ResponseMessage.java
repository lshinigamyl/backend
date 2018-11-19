package pe.edu.unmsm.sistemas.servidorclinica.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseMessage {
	@JsonProperty("code")
	private Integer code;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("messageDetail")
	private List<String> messageDetail;
	
	@JsonProperty("type")
	private String type;
	
	@JsonProperty("valor")
	private Integer valor;
	
	@JsonProperty("data")
	private Object data;
	
	public ResponseMessage(Object data) {
		this.data = data;
	}
	 
	public ResponseMessage(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}
	
	public ResponseMessage() {
		// TODO Auto-generated constructor stub
	}
	public ResponseMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getValor() {
		return valor;
	}
	public void setValor(Integer valor) {
		this.valor = valor;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public List<String> getMessageDetail() {
		return messageDetail;
	}
	public void setMessageDetail(List<String> messageDetail) {
		this.messageDetail = messageDetail;
	}	
}
