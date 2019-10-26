package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class removeDuplicates {

	@Test
	public void contextLoads() {
		int []nums ={3,3,4};
		int s=removeDuplicates(nums);
		System.out.println(s);
	}

	/**
	 * 去除一个给定排序数组的重复数字
	 * @param nums
	 * @param nums
	 * @return
	 */
	public int removeDuplicates(int[] nums) {
		if (nums == null || nums.length == 0) {
			return 0;
		}
		int count=0;
		for (int i = 1; i < nums.length; i++) {
			if(nums[i]!=nums[count]){
				count++;
				nums[count]=nums[i];
			}

		}
		return count+1;
	}

}
