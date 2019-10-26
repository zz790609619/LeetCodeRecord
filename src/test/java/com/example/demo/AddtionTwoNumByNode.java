package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddtionTwoNumByNode {

	@Test
	public void contextLoads() {
		/**
		 * 题目
		 * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
		 * 输出：7 -> 0 -> 8
		 * 原因：342 + 465 = 807
		 */
		ListNode l1 = new ListNode(2);
		ListNode l1_2 = new ListNode(4);
		ListNode l1_3 = new ListNode(3);
		l1.next=l1_2;
		l1_2.next=l1_3;
		ListNode l2 = new ListNode(5);
		ListNode l2_2 = new ListNode(6);
		ListNode l2_3 = new ListNode(4);
		l2.next=l2_2;
		l2_2.next=l2_3;
		ListNode result=addTwoNumbers(l1,l2);

	}

	/**
	 * 两数相加-链表
	 * @return
	 */
	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
		ListNode result=new ListNode(0);
		int i=0;
		ListNode cur=result;
		while(l1!=null||l2!=null){
			if(l1==null)l1.val=0;
			if(l2==null)l2.val=0;
			cur.next=new ListNode((l1.val+l2.val+i)%10);
			cur=cur.next;
			i=(l1.val+l2.val+i)/10;
			l1=l1.next;
			l2=l2.next;
		}
		if(i>0){
			result.next=new ListNode(i);
		}
		return result.next;
	}
}
