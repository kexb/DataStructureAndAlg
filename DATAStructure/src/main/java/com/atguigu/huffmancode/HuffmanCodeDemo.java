package com.atguigu.huffmancode;

import java.io.*;
import java.util.*;

public class HuffmanCodeDemo {
    //字符串zip和unzip
    public static void main4(String[] args) {
        String s = byteToBitString(true, (byte) -1);
        System.out.println((((byte) Integer.parseInt("10101000", 2))));
        String content = "i like like like java do you like a java";//40
        byte[] contentBytes = content.getBytes();
        byte[] zip = huffmanZip(contentBytes);
        System.out.println("解码结果" + Arrays.toString(decode(huffmanCodes, zip)));
        System.out.println("\nhuffmanCodeBytes=" + Arrays.toString(zip));
    }

    //赫夫曼编码压缩文件
    public static void main2(String[] args) {
        String srcFile = "C:\\Users\\71560\\Desktop\\runoob0\\src.png";
        String dstFile = "C:\\Users\\71560\\Desktop\\runoob0\\dst.zip";
        zipFile(srcFile, dstFile);
        System.out.println("压缩文件ok~~");
    }

    public static void main(String[] args) {
        String zipFile = "C:\\Users\\71560\\Desktop\\runoob0\\dst.zip";
        String dstFile = "C:\\Users\\71560\\Desktop\\runoob0\\src2.png";
        unzipFile(zipFile, dstFile);
        System.out.println("解压成功");
    }


    public static void unzipFile(String zipFile, String dstFile) {
        InputStream is = null;
        ObjectInputStream ois = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(zipFile);
            ois = new ObjectInputStream(is);
            byte[] huffmanBytes = (byte[]) ois.readObject();
            Map<Byte, String> huffmanCodes = (Map<Byte, String>) ois.readObject();
            byte[] bytes = decode(huffmanCodes, huffmanBytes);
            os = new FileOutputStream(dstFile);
            os.write(bytes);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                os.close();
                ois.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //编写一个方法进行文件压缩

    /**
     * @param srcFile 你传入的希望压缩的文件的全路径
     * @param dstFile 我们压缩后将文件放到哪个目录
     */
    public static void zipFile(String srcFile, String dstFile) {
        //创建输出流
        OutputStream os = null;
        FileInputStream is = null;
        ObjectOutputStream oos = null;
        try {
            //创建文件的输入流
            is = new FileInputStream(srcFile);
            //创建一个和源文件一样大小的byte[]
            byte[] b = new byte[is.available()];
            //读取文件
            int read = is.read(b);
            //获取到文件对应的赫夫曼编码
            byte[] huffmanBytes = huffmanZip(b);
            //创建文件的输出流,存放压缩文件
            os = new FileOutputStream(dstFile);
            //创建一个和文件输出流关联的ObjectOutputStream
            oos = new ObjectOutputStream(os);
            //把赫夫曼编码后的字节数组写入压缩文件
            oos.writeObject(huffmanBytes);
            //这里我们以对象流的方式写入 赫夫曼编码 是为了以后我们恢复源文件时使用
            //注意一定要把赫夫曼编码写入压缩文件
            oos.writeObject(huffmanCodes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
                assert oos != null;
                oos.close();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        //1. 先得到huffmanBytes对应的二进制字符串形式为11010011...
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            //判断是不是最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, huffmanBytes[i]));
        }
        //编码表反转 如100->97 100为发路径 97位字符
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1;
            boolean flag = true;
            Byte b = null;
            while (flag) {
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;

        }
        byte[] b = new byte[list.size()];
        for (int i = 0; i < b.length; i++) {
            b[i] = list.get(i);
        }
        return b;
    }


    /**
     * 将一个byte转成二进制的字符串
     *
     * @param flag 是否需要补高位 如果是最后一个字节无需补高位
     * @param b    对应byte
     * @return 是该b 对应的二进制字符串,(注意是补码返回)
     */
    private static String byteToBitString(boolean flag, byte b) {
        int temp = b;
        if (flag) {
            temp |= 256;//按位或 256 1 0000 0000 | 0000 0001 =》 1 0000 0001
        }
        String str = Integer.toBinaryString(temp);
        if (flag) {
            //返回后8位
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }

    private static byte[] huffmanZip(byte[] contentBytes) {
        List<Node> nodes = getNodes(contentBytes);
        Node root = createHuffmanTree(nodes);
        getCodes(root, "", stringBuilder);
        return zip(contentBytes, huffmanCodes);
    }


    //编写一个方法 将字符串对应的byte[]数组 通过赫夫曼编码表 返回一个赫夫曼编码  压缩后的byte[]

    /**
     * @param bytes        原始的字符串对应的byte[] (每个字符 对应 ASCALL编码值)
     * @param huffmanCodes 生成的赫夫曼编码表map 字符（ASCALL）->路径
     * @return String content="i like like like java do you like a java"=>byte[] contentBytes=content.getBytes()
     * 返回的是字符串1010100010111111110010001011111111001000101111111100100101001101110001110000011011101000111100101000101111111100110001001010011011100
     * 对应的byte[]huffmanCodeBytes 即8位对应一个byte 放入到huffmanCodeBytes
     * 假设放入到huffmanCodeBytes[0]=10101000(补码)=>10101000=>10101000-1=>10100111(反码)=>11011000(源码)=-88
     */
    private static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        //如果是整除那么加了不足一倍结果取整不变
        //如果是多了一点那么至少多1 那么+7 肯定是多了一倍而不足两倍 结果还是会多1 分页的时候也可借鉴
        int len = (stringBuilder.length() + 7) / 8;
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0;
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > stringBuilder.length()) {
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            huffmanCodeBytes[index++] = (((byte) Integer.parseInt(strByte, 2)));
        }
        return huffmanCodeBytes;
    }

    private static void huffmanPathTest(String content, Node root) {
        List<String> path = root.getPath();
        System.out.println(path);

        Map<String, String> dict = new HashMap<>();
        for (String s : path) {
            String[] split = s.split(":");
            //key是字符 value路径
            dict.put(split[1], split[0]);
        }
        String line = "";

        Map<String, String> reverse = new HashMap<>();
        for (Map.Entry<String, String> stringStringEntry : dict.entrySet()) {
            reverse.put(stringStringEntry.getValue(), stringStringEntry.getKey());

        }

        List<String> codes = new ArrayList<>();
        for (char c : content.toCharArray()) {
            codes.add(dict.get(c + ""));
            line += dict.get(c + "");
        }
        System.out.printf("编码是=[%s]\n", line);
        System.out.println("还原");
        for (String code : codes) {
            System.out.print(reverse.get(code));
        }
    }

    //生成赫夫曼树对应的赫夫曼编码
    //思路
    //1.将赫夫曼编码表存放在Map<Byte,String> 如 32->101 97->100 98->101等等
    static Map<Byte, String> huffmanCodes = new HashMap<>();

    static Map<String, StringBuilder> huffmanCodes2 = new HashMap<>();
    //2.在生成赫夫曼编码表，需要去拼接路径,定义一个StringBuilder 存储叶子节点的路径
    static StringBuilder stringBuilder = new StringBuilder();

    private static Map<Byte, String> getCodes(Node root) {
        if (root == null) {
            return null;
        }
        getCodes(root.left, "", stringBuilder);
        getCodes(root.right, "", stringBuilder);
        return huffmanCodes;
    }

    /**
     * 功能: 将传入的node节点的所有叶子节点的赫夫曼编码得到,并放入到huffmanCodes
     *
     * @param node          传入节点
     * @param code          路径: 左子节点时0,右子节点是1
     * @param stringBuilder 用于拼接路径
     */
    private static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        stringBuilder2.append(code);
        if (node != null) {
            if (node.data == null) {//非叶子节点
                getCodes(node.left, "0", stringBuilder2);
                getCodes(node.right, "1", stringBuilder2);
            } else {
                huffmanCodes.put(node.data, stringBuilder2.toString());
                //huffmanCodes2.put(((char) node.data.intValue()) + "", stringBuilder2);
            }
        }
    }

    //通过list创建赫夫曼树
    private static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);
        }
        return nodes.get(0);

    }


    private static List<Node> getNodes(byte[] bytes) {
        //1.创建一个ArrayList
        ArrayList<Node> nodes = new ArrayList<>();
        //2.遍历bytes,统计每一个字节出现的次数map[key,value]
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) {
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }
        for (Map.Entry<Byte, Integer> count : counts.entrySet()) {
            nodes.add(new Node(count.getKey(), count.getValue()));
        }
        return nodes;
    }
}

//创建Node,带数字和权值
class Node implements Comparable<Node> {
    Byte data;//存放数据(字符)本身,比如'a'=>97 ' '=>32
    int weight;//权值,表示字符出现的次数
    Node left;
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    //前序遍历
    public void preOrder() {
        System.out.println(this);
        if (this.left != null) {
            this.left.preOrder();
        }
        if (this.right != null) {
            this.right.preOrder();
        }
    }


    public List<String> getPath() {
        List<String> leftPath = getDirectionPath(0);
        List<String> rightPath = getDirectionPath(1);
        List<String> path = new ArrayList<>();
        for (String s : leftPath) {
            path.add(0 + s);
        }
        for (String s : rightPath) {
            path.add(1 + s);
        }
        return path;
    }

    public List<String> getDirectionPath(int direction) {
        Node node = direction == 0 ? this.left : this.right;
        List<String> path = new ArrayList<>();
        if (node.left != null) {
            List<String> path1 = node.getDirectionPath(0);
            for (String str : path1) {
                String np = 0 + str;
                path.add(np);
            }
        } else {
            //让路径的最后一个为叶子节点的字符
            String data = ((char) node.data.intValue()) + "";
            return Collections.singletonList(":" + data);
        }
        if (node.right != null) {
            List<String> path2 = node.getDirectionPath(1);
            for (String str : path2) {
                String np = 1 + str;
                path.add(np);
            }
        } else {
            //让路径的最后一个为叶子节点的字符
            String data = ((char) node.data.intValue()) + "";
            return Collections.singletonList(" \t" + data);
        }
        return path;
    }

}
