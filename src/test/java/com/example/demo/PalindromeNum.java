package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PalindromeNum {
	/**
	 * 回文数
	 */
	@Test
	public void contextLoads() {
		boolean s=isPalindrome(-121);
		System.out.println(s);
	}

    /**
     * 第一个想法
	 * 由于之前写过数字倒叙相同
	 *  执行用时 :9 ms, 在所有 java 提交中击败了99.04%的用户
	 * 	内存消耗 :36.5 MB, 在所有 java 提交中击败了95.03%的用户
	 * @param s
     * @return
     */
	public boolean isPalindrome(int s) {
		int begin=s;
		int result=0;
		while (s!=0){
			result=result*10+s%10;
			s=s/10;
		}
		if(begin>=0&&result==begin){
			return true;
		}
		return false;
	}

	/**
	 * 蠢办法也是要有的，将整数转换成字符串然后逐位比较
	 * @param x
	 * @return
	 */
	public boolean isPalindrome2(int x) {
		if(x<0||(x<10&&x>0)){
			return false;
		}else if(x==0){
			return true;
		}else{
			String s=String.valueOf(x);
			for (int i = 0; i <s.length() ; i++) {
				if(!(s.charAt(i)==s.charAt(s.length()-1-i))){
					return false;
				}
			}
			return true;
		}
	}
}
