package com.leetcode.page01;

/**
 * @author: Jfeng
 * @date: 2018/4/20
 */
public class T02 {
    /**
     * 给定两个非空链表来表示两个非负整数。位数按照逆序方式存储，它们的每个节点只存储单个数字。将两数相加返回一个新的链表。
     * <p>
     * 你可以假设除了数字 0 之外，这两个数字都不会以零开头。
     * <p>
     * 示例：
     * <p>
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     */
    public static void main(String[] args) {
        T02 ins = new T02();
        ListNode l1 = ins.process("2,4,3,5");
        ListNode l2 = ins.process("5,6,4,5");

        ListNode node = ins.addTwoNumbers(l1, l2);
        System.out.println(node);
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode node = new ListNode(0);
        ListNode temp = node;

        int carry = node.val;

        while (l1 != null || l2 != null) {
            if (l1 != null) {
                carry += l1.val;
                l1 = l1.next;

            }
            if (l2 != null) {
                carry += l2.val;
                l2 = l2.next;
            }

            // 保存整数
            temp.next = new ListNode(carry % 10);
            temp = temp.next;
            // 余数进位
            carry = carry / 10;
        }

        if (carry != 0) {
            temp.next = new ListNode(carry);
        }

        return node.next;
    }

    public ListNode process(String str) {
        ListNode node = new ListNode(0);
        ListNode temp = node;

        for (String s : str.split(",")) {
            temp.next = new ListNode(Integer.valueOf(s));
            temp = temp.next;
        }

        return node.next;
    }

}

class ListNode {
    int val;
    ListNode next;

    public ListNode(int val) {
        this.val = val;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(val);
        if (next != null) {
            sb.append("->").append(next);
        }
        return sb.toString();
    }
}




