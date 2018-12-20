package com.biz.bank.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import com.biz.bank.vo.BankVO;

public class BankService {
	
	List<BankVO> bankList;
	String balFile;
	Scanner sc;
	
	public BankService(String balFile) {
		bankList=new ArrayList<>();
		this.balFile=balFile;
		sc=new Scanner(System.in);
	}
	
	public BankVO bankFindID(String strID) {
		for(BankVO vo:bankList) {
			if(vo.getStrID().equals(strID)) return vo;
		}
		System.out.println("계좌번호가 잘못되었습니다.");
		return null;
	}
	
	public void bankInput() {
		
		System.out.println("계좌번호를 입력하세요.");
		System.out.print(">> ");
		String strID=sc.nextLine();
		BankVO vo=bankFindID(strID);
		if(vo==null) return;
		System.out.println("입금하실 금액을 입력하세요.");
		System.out.print(">> ");
		String strIO=sc.nextLine();
		try {
			int intIO=Integer.valueOf(strIO);
			vo.setStrIO("입금");
			vo.setIntIOCash(intIO);
			vo.setIntBalance(vo.getIntBalance()+vo.getIntIOCash());
			SimpleDateFormat sm=new SimpleDateFormat("yyyy-mm-dd");
			Date curDate=new Date();
			String strDate=sm.format(curDate);
			vo.setStrLastDate(strDate);
			System.out.println("입금이 완료 되었습니다.");
			System.out.println("잔액은 "+vo.getIntBalance()+"원 입니다.");
			this.bankIOWrite(vo);
		} catch (NumberFormatException e) {
			System.out.println("금액이 잘못입력되었습니다.");
		}
	}
	
	public void bankOutput() {
		
		System.out.println("계좌번호를 입력하세요.");
		System.out.print(">> ");
		String strID=sc.nextLine();
		BankVO vo=bankFindID(strID);
		if(vo==null) return;
		System.out.println("출금하실 금액을 입력하세요.");
		System.out.print(">> ");
		String strIO=sc.nextLine();
		try {
			int intIO=Integer.valueOf(strIO);
			if(intIO>vo.getIntBalance()) {
				System.out.println("잔액이 부족합니다.");
				return;
			}
			vo.setStrIO("출금");
			vo.setIntIOCash(intIO);
			vo.setIntBalance(vo.getIntBalance()-vo.getIntIOCash());
			LocalDate date=LocalDate.now();
			String strDate=date.toString();
			vo.setStrLastDate(strDate);
			System.out.println("출금이 완료 되었습니다.");
			System.out.println("잔액은 "+vo.getIntBalance()+"원 입니다.");
			this.bankIOWrite(vo);
		} catch (NumberFormatException e) {
			System.out.println("금액이 잘못입력되었습니다.");
		}
	}
	
	public void bankCheckID() {
		
		System.out.println("계좌번호를 입력하세요.");
		System.out.print(">> ");
		String strID=sc.nextLine();
		BankVO vo=bankFindID(strID);
		if(vo==null) return;
		System.out.println("계좌번호   : "+vo.getStrID());
		System.out.println("잔액       : "+vo.getIntBalance());
		System.out.println("최근거래일 : "+vo.getStrLastDate());
	}
	
	public void bankWriteBalance() {
		
		PrintWriter pw;
		try {
			pw=new PrintWriter(balFile);
			for(BankVO vo:bankList) {
				pw.println(vo.getStrID()+":"+vo.getIntBalance()+":"+vo.getStrLastDate());
			}
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bankIOWrite(BankVO vo) {
		
		String ioFile="src/com/biz/bank/iolist/"+vo.getStrID()+".txt";
		FileWriter fw;
		PrintWriter pw;
		
		try {
			fw=new FileWriter(ioFile,true);
			pw=new PrintWriter(fw);
			if(vo.getStrIO().equals("입금"))
				pw.println(vo.getStrID()+":"+vo.getStrLastDate()+":"+vo.getStrIO()+":"+vo.getIntIOCash()+":0:"+vo.getIntBalance());
			if(vo.getStrIO().equals("출금"))
				pw.println(vo.getStrID()+":"+vo.getStrLastDate()+":"+vo.getStrIO()+":0:"+vo.getIntIOCash()+":"+vo.getIntBalance());
			pw.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Boolean bankMenu() {
		
		System.out.println("=================================");
		System.out.println("1:입금 / 2:출금 / 3:조회 / 0:종료");
		System.out.println("=================================");
		System.out.print(">> ");
		String strMenu=sc.nextLine();
		try {
			int intMenu = Integer.valueOf(strMenu);
			if(intMenu==0 || intMenu>3) return false;
			if(intMenu==1) this.bankInput();
			if(intMenu==2) this.bankOutput();
			if(intMenu==3) this.bankCheckID();
			this.bankWriteBalance();
		} catch (NumberFormatException e) {
			System.out.println("메뉴가 잘못입력되었습니다.");
		}
		return true;
	}
	
	public void readBalance() {
		
		FileReader fr;
		BufferedReader buffer;
		bankList.clear();
		
		try {
			fr=new FileReader(balFile);
			buffer=new BufferedReader(fr);
			while(true) {
				String reader=buffer.readLine();
				if(reader==null) break;
				String[] banks=reader.split(":");
				BankVO vo=new BankVO();
				vo.setStrID(banks[0]);
				int intBalance=Integer.valueOf(banks[1]);
				vo.setIntBalance(intBalance);
				vo.setStrLastDate(banks[2]);
				bankList.add(vo);
			}
			buffer.close();
			fr.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
