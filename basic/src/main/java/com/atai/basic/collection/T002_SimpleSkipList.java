package com.atai.basic.collection;

import java.util.Random;

public class T002_SimpleSkipList {
    // 每一层头部节点的标记符
    private final static byte HEAD_BIT = (byte) -1;
    // 每一层尾部节点的标记符
    private final static byte TAIL_BIT = (byte) 1;
    // 每一个数据节点的标记符
    private final static byte DATA_BIT = (byte) 0;

    // 定义头部节点属性
    private Node head;
    // 定义尾部节点属性
    private Node tail;
    // 元素个数
    private int size;
    // 跳表层高
    private int height;
    // 随机数，主要用于通过随机的方式决定元素应该被放在第几层
    private Random random;

    // 构造函数
    public T002_SimpleSkipList() {
// 初始化头部和尾部节点
        this.head = new Node(null, HEAD_BIT);
        this.tail = new Node(null, TAIL_BIT);
        // 头部节点的右边节点为尾部节点
        head.right = tail;
        // 尾部节点的左边元素为头部节点
        tail.left = head;
        this.random = new Random(System.currentTimeMillis());
    }

    private Node find(Integer element) {
        // 从head节点开始寻找
        Node current = head;
        for (; ; ) {
            // 当前节点的右节点不是尾节点，并且当前节点的右节点数据小于element
            while (current.right.bit != TAIL_BIT && current.right.value <= element) {
                // 继续朝右前行
                current = current.right;
            }
            // 当current节点存在down节点
            if (current.down != null) {
                // 开始向下一层
                current = current.down;
            } else {
                // 到达最底层，终止循环
                break;
            }
        }
        return current;
    }

    public void add(Integer element) {
        // 根据element找到合适它的存储位置，也就是邻近的节点，需要注意的是，此刻该节点在整个跳表的第一层
        Node nearNode = this.find(element);
        // 定义一个新的节点
        Node newNode = new Node(element);
        // 新节点的左节点为nearNode
        newNode.left = nearNode;
        // 新节点的右节点为nearNode.right，相当于将新节点插入到了nearNode和nearNode中间
        newNode.right = nearNode.right;
        // 更新nearNode.right.left节点为新节点
        nearNode.right.left = newNode;
        // nearNode.right节点为新节点
        nearNode.right = newNode;
        // 当前层级为0，代表最底层第一层
        int currentLevel = 0;
        // 根据随机值判断是否将新的节点放到新的层级，在跳表算法描述中，该动作被称为抛投硬币
        while (random.nextDouble() < 0.5d) {
            // 如果currentLevel 大于整个跳表的层高，则需要为跳表多增加一层链表
            if (currentLevel >= height) {
                height++;
                // 定义新层高的head和tail
                Node dumyHead = new Node(null, HEAD_BIT);
                Node dumyTail = new Node(null, TAIL_BIT);
                // 指定新层高head和tail的关系
                dumyHead.right = dumyTail;
                dumyHead.down = head;
                head.up = dumyHead;
                dumyTail.left = dumyHead;
                dumyTail.down = tail;
                tail.up = dumyTail;
                head = dumyHead;
                tail = dumyTail;
            }
            // 在新的一层中增加element节点，同样要维护上下左右之间的关系
            while ((nearNode != null) && nearNode.up == null) {
                nearNode = nearNode.left;
            }
            nearNode = nearNode.up;
            Node upNode = new Node(element);
            upNode.left = nearNode;
            upNode.right = nearNode.right;
            upNode.down = newNode;
            nearNode.right.left = upNode;
            nearNode.right = upNode;
            newNode.up = upNode;
            newNode = upNode;
            currentLevel++;
        }
        // 元素个数自增
        size++;
    }

    public boolean contains(Integer element) {
        // 如果找到了就包含，若未找到就不包含
        Node node = this.find(element);
        return (node.value.equals(element));
    }

    public Integer get(Integer element) {
        // 若找到该元素则返回value，否则返回null
        Node node = this.find(element);
        return (node.value.equals(element)) ? node.value : null;
    }

    public boolean isEmpty() {
        // 根据size判断当前链表是否为空
        return (size() == 0);
    }

    // 跳表的size方法
    public int size() {
        return size;
    }

    // 元素节点类，该节点中只存放整数类型
    private static class Node {
        // 数据
        private Integer value;
        // 每一个节点的周围节点引用（上下左右）
        private Node up, down, left, right;
        // 节点类型
        private byte bit;

        public Node(Integer value) {
            this(value, DATA_BIT);
        }

        public Node(Integer value, byte bit) {
            this.value = value;
            this.bit = bit;
        }

        @Override
        public String toString() {
            return value + " bit:" + bit;
        }
    }
}
