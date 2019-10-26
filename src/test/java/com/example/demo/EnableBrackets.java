package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EnableBrackets {

	/**
	 * 括号匹配
	 */
	@Test
	public void contextLoads() {
		boolean s=isValid3("{}");
		System.out.println(s);
	}

    /**
     * 第一个想法 遍历每一个字符，然后放到一个字符串里面，然后判断当前字符与字符串中的位置之间是否相同，相同的差是多少 ，差是1则消除当前两个字符串
	 * 最后判断结果字符串是否为空
	 * @param s
     * @return
     */
	public boolean isValid(String s) {
		Map<Character,Character> map=new HashMap<>();
		map.put(')','(');
		map.put('}','{');
		map.put(']','[');
		boolean isValid=false;
		String result="";
		for (int i = 0; i < s.length(); i++) {
				char cur=s.charAt(i);
				result += cur;
				if(cur=='('||cur=='{'||cur=='['){
					isValid=true;
				}else{
					if(result.lastIndexOf(map.get(cur))!=-1&&result.lastIndexOf(map.get(cur)) == result.indexOf(cur) - 1) {
							result = result.substring(0,result.lastIndexOf(map.get(cur)));
							isValid=true;
					}else{
						isValid=false;
					}
				}
				if(result.length()>0){
					isValid=false;
				}
			}
		if(s.equals("")){
			isValid=true;
		}
		return isValid;
	}

    /**
     * 第二个想法 ：因为第一个想法的速度太过于慢，然后继承了第一个想法的消除，反正最后相匹配的话
	 * 最根源的{}[]()都是成对的，然后我可以消除他,
	 * 执行用时 :154 ms, 在所有 java 提交中击败了5.02%的用户
	 * 	内存消耗 :36.7 MB, 在所有 java 提交中击败了33.14%的用户
	 * 	但是结果显而易见的差
	 * 	  if (s.length() % 2 != 0) {return false; }增加判断是否为偶数后 可以减少执行时间，但是增加了内存的消耗
	 * @param s
     * @return
     */
	public boolean isValid2(String s) {
	while (s.contains("[]")||s.contains("{}")||s.contains("()")){
		s=s.replace("[]","");
		s=s.replace("{}","");
		s=s.replace("()","");
		}
		if(s.length()>0){
			return false;
		}
		return true;

	}
	/**
	 * 第三个想法 ：因为前两个想法都tm的蠢，所以借鉴了一下网上的栈处理，通过进栈出栈的方式去做
	 * 逻辑和前两个方法差不多，都是遇到匹配的就消除，不过栈处理更快
	 * @param s
	 * @return
	 */
	public boolean isValid3(String s) {
		Stack<Character> stack=new Stack<>();
		Map<Character,Character> map=new HashMap<>();
		map.put(')','(');
		map.put('}','{');
		map.put(']','[');
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);//获取当前字符
			//如果是左符号就push进栈
			if (map.containsKey(c)) {
				//判断栈顶元素是否为当前符号的对应的左符号
				char topElement = stack.empty() ? '#' : stack.pop();
				if (topElement != map.get(c)) {
					return false;
				}
			}else{
				stack.push(c);
			}
		}
		return stack.isEmpty();

	}

}
