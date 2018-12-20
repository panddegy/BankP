package com.biz.bank.exec;

import com.biz.bank.vo.BankVO;

public class Exec01 {
	
	public static void main(String[] args) {
		
		BankVO[] banks=new BankVO[10];
		
		for(BankVO v:banks) {
			v=new BankVO();
		}
	}
}
