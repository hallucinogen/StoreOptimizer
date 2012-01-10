package dp.mobile.store.adapter;

public class CashReport {
	public CashReport(String custName, String custAddr, long amountCash){
		mCustomerName 	= custName;
		mCustomerAddr	= custAddr;
		mAmountCash		= amountCash;
	}
	
	public String	mCustomerName, mCustomerAddr;
	public long		mAmountCash;
}
