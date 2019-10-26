package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddtionTwoNumByArray {

	@Test
	public void contextLoads() {
		int []nums ={3,3,4};
		int target = 6;
		int []s=twoSum(nums,target);
		System.out.println(s);
	}

	/**
	 * 两数相加
	 * @param nums
	 * @param target
	 * @return
	 */
	public int[] twoSum(int[] nums, int target) {
		Map<Integer,Integer> map=new HashMap<>();
		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i],i);
		}
		for (int i = 0; i < nums.length; i++) {
			if(map.containsKey(target-nums[i])&& map.get(target-nums[i]) != i){
				int[]result={i,map.get(target-nums[i])};
				return result;
			}
		}
		return null;
	}

}
