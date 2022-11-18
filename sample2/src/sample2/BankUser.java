package sample2;

public class BankUser extends User{
	
	private String bankName;
	private String NoOfCustomers;
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getNoOfCustomers() {
		return NoOfCustomers;
	}
	public void setNoOfCustomers(String noOfCustomers) {
		NoOfCustomers = noOfCustomers;
	}

}
