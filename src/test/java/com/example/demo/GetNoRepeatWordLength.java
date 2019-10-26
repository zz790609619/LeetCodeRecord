package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetNoRepeatWordLength {

	@Test
	public void contextLoads() {
		/**
		 * 题目
		 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
		 * 示例 1:
		 * 输入: "abcabcbb"
		 * 输出: 3
		 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
		 *
		 * 示例 2:
		 * 输入: "bbbbb"
		 * 输出: 1
		 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
		 *
		 * 示例 3:
		 * 输入: "pwwkew"
		 * 输出: 3
		 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
		 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
		 */
		String a="bbbbb";
		int result=lengthOfLongestSubstring(a);
		result=lengthOfLongestSubstringTwo(a);
	}

	/**
	 * 第一个想法，遍历字符串，用一个字符串去接受不重复的当前字符串的子字符串，并记录下数据。
	 * 执行用时 :15 ms, 在所有 java 提交中击败了72.04%的用户
	 * 内存消耗 :37.7 MB, 在所有 java 提交中击败了91.29%的用户
	 * @return
	 */
	public int lengthOfLongestSubstring(String s) {
		//定义一个比对结果字符串
		String result="";
		//最大长度
		int results=0;
		for (int i = 0; i < s.length(); i++) {
			//判断result是否包含当前字符
			if(result.contains(String.valueOf(s.charAt(i)))){
				//获取到当前字符在结果集的位置
				int z=result.indexOf(String.valueOf(s.charAt(i)));
				//如果 总字符串存在两个相邻且相等的字符串
				if(result.length()>1 && s.charAt(i-1)==s.charAt(i)){
					//刷新当前字符串
					result=String.valueOf(s.charAt(i));
				}
				//需要将result去掉重复的字符以前的所有字符（包含当前字符），并将当前字符在result字符后面追加，保证自己是不相同的字符
				if(result.length()>1){
					result=result.substring(z+1)+s.charAt(i);
				}

			}else{
				result=result+s.charAt(i);
			}
			if(result.length()>results){
				results=result.length();
			}

		}
		return results;
	}
	/**
	 * 因为第一个想法不是很简便和不便于理解，而且有个String的截取和赋值，极大的消耗了内存，所以参考了一下其他人的答案，提供了一种滑动窗口的算法思考模式
	 *
	 * @return
	 */
	public int lengthOfLongestSubstringTwo(String s) {
		int start=0;//初始位置
		int end=0;//结束位置
		int result=0;//最终长度
		Map<String,Integer> map=new HashMap<>();//存放
		for (int i = 0; i < s.length(); i++) {
			String cur=String.valueOf(s.charAt(i));
			if(map.containsKey(cur)){
				if(map.get(cur)>start){
					start=map.get(cur);
				}
			}
			end=i+1;
			if(result<end-start){
				result=end-start;
			}
			map.put(String.valueOf(s.charAt(i)),i+1);
		}
		return result;
	}
}
