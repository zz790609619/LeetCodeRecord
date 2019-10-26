package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RomanNumToNum {
	/**
	 * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
	 * 字符          数值
	 * I             1
	 * V             5
	 * X             10
	 * L             50
	 * C             100
	 * D             500
	 * M             1000
	 *
	 * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
	 *
	 * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。
	 * 同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
	 *
	 * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
	 * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
	 * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
	 *
	 * 给定一个罗马数字，将其转换成整数。输入确保在 1 到 3999 的范围内。
	 * 示例 1:
	 * 输入: "III"
	 * 输出: 3
	 */
	@Test
	public void contextLoads() {
		int s=romanToInt2("III");
		System.out.println(s);
	}

	/**
	 * 第一个想法
	 * @param s
	 * @return
	 */
	public int romanToInt(String s) {
		int i=0;
		int result=0;
		while(i<s.length()){
			String cur=String.valueOf(s.charAt(i));
			String next="";
			if(i+1<s.length()){
				next=String.valueOf(s.charAt(i+1));
			}
			if(cur.equals("I")&&i+1<s.length()&&next.equals("V")){
				result+=4;
				i=i+1;
			}else if(cur.equals("I")&&i+1<s.length()&&next.equals("X")){
				result+=9;
				i=i+1;
			} else if(cur.equals("X")&&i+1<s.length()&&next.equals("L")){
				result+=40;
				i=i+1;
			} else if(cur.equals("X")&&i+1<s.length()&&next.equals("C")){
				result+=90;
				i=i+1;
			}else if(cur.equals("C")&&i+1<s.length()&&next.equals("D")){
				result+=400;
				i=i+1;

			}else if(cur.equals("C")&&i+1<s.length()&&next.equals("M")){
				result+=900;
				i=i+1;
			}else{
				switch (cur){
					case "I":
						result+=1;
						break;
					case "V":
						result+=5;
						break;
					case "X":
						result+=10;
						break;
					case "L":
						result+=50;
						break;
					case "C":
						result+=100;
						break;
					case "D":
						result+=500;
						break;
					case "M":
						result+=1000;
						break;
				}
			}
			i++;
		}
		return result;

	}

	/**
	 * 第二个想法
	 * @param s
	 * @return
	 */
	public int romanToInt2(String s) {
		Map<Character,Integer> map=new HashMap<Character,Integer>();
		map.put('I',1);
		map.put('V',5);
		map.put('X',10);
		map.put('L',50);
		map.put('C',100);
		map.put('D',500);
		map.put('M',1000);
		int i=0;
		int result=0;
		while(i<s.length()){
			char cur=s.charAt(i);
			int curNum=map.get(cur);
			if(i+1<s.length()){
				if(curNum<map.get(s.charAt(i+1))){
					result=result-curNum;
				}else{
					result=result+curNum;
				}
			}else{
				result+=curNum;
			}
			i++;
		}
		return result;

	}

}
