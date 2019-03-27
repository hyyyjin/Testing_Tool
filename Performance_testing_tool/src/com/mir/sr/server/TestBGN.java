package com.mir.sr.server;

public class TestBGN {

	public TestBGN(){
		
		
//		int aa = testNumber();
//		System.out.println(aa);
	}
	
	public static void main(String[] args){
		
		for(int i =0; i<10; i++){
		new TestBGN();
		}
	}
	
	public int randomNakNumber() {
		int nakNum = (int) (Math.random() * (6 - 2)) + 2;

		return nakNum;
	}
}
