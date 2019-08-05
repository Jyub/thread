package com.wy.yubiao.thread;

/**
 * @version v1.0
 * @author: yubiao
 * @date: 2019/8/2 11:07
 * @description: 不规则数组
 */
public class LotteryArray {

    public static void main(String[] args) {
        final int NMAX =10;

        int[][] odds = new int[NMAX+1][];

        for (int i = 0; i <= NMAX; i++) {
            odds[i] = new int[i+1];
        }

        for (int i = 0; i < odds.length; i++) {
            for (int j = 0; j < odds[i].length; j++) {
                int lotteryOdds = 1;
                /*for (int k = 1; k <= j; k++) {
                    lotteryOdds = lotteryOdds*(i-k+1)/k;
                }*/
                if (j == 0 || j == odds[i].length-1){
                    odds[i][j] = lotteryOdds;
                }else {
                    odds[i][j] = odds[i-1][j-1]+odds[i-1][j];
                }

            }
        }

        for (int[] row : odds) {
            for (int i : row) {
                System.out.printf("%4d",i);
            }
            System.out.println();
        }

        for(int i=0;i<=NMAX;i++)
        {
            for(int j=0;j<=NMAX-i;j++)
            {
                System.out.print("\t");
            }
            for(int j=0;j<=i;j++)
            {   //打印空格后面的字符, 从第1 列开始往后打印
                System.out.print(odds[i][j] +"\t\t");
            }
            System.out.println();
        }
    }

}
