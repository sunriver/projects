package com.oobe.bill;

public class PurchaseBean {
	private String sku;
	private String price;
	private String currency;
	private String timestamp;
	private String transaction;
	private String receipt;

	/*
	 * App store: as = AppStore, gp = Google Play, aa = Amazon Appstore, bn = BARNES
	 * & NOBLE (Nook Store), lm = Lenovo Market, sa = Samsung AppStore, gj =
	 * GetJar, fh = Fuhu, ud = Undefined.
	 */
	private String store = "";
	/*
	 * Device type: 1 = handset, 2 = tablet, 3 = desktop or Laptop, 0 = undefined
	 */
	private String device = "";

	/*
	 * Devcie Platform: 0 = undefined, 1 = iOS, 2 = Android, 3 = Windows, 4 = Mac OS
	 * X, 5 =BlackBerry, 6 = Linux
	 */
	private String os = "";

	// The version no of the OS
	private String osver = "";


	public String getInfo() {
		return "store=" + store + ",device=" + device + ",os=" + os + ",osver" + osver;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsver() {
		return osver;
	}

	public void setOsver(String osver) {
		this.osver = osver;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public String getReceipt() {
		return receipt;
	}

	public void setReceipt(String receipt) {
		this.receipt = receipt;
	}
}
