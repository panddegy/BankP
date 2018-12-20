package com.biz.bank.exec;

import com.biz.bank.service.BankService;

public class BankExec01 {
	
	public static void main(String[] args) {
		
		String balFile="src/com/biz/bank/bankBalance.txt";
		BankService bs=new BankService(balFile);
		
		boolean b=true;
		while(b) {
			bs.readBalance();
			b=bs.bankMenu();
		}
		System.out.println("안녕히 가세요.");
	}
}
