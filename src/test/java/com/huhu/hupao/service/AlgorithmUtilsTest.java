package com.huhu.hupao.service;

import com.huhu.hupao.utils.AlgorithmUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author xxx
 * @date 2024/2/6 1:28
 */
//算法工具类测试
    //编辑举例算法  用于计算最详实的两个字符串
public class AlgorithmUtilsTest {
    @Test
    void test(){
        /*String str1 ="鱼皮是狗";
        String str2="鱼皮不是狗";
        String str3="鱼皮是鱼不是狗";
        String str4="鱼皮是猫";
        int score1 = AlgorithmUtils.minDistance(str1, str2);
        int score2=AlgorithmUtils.minDistance(str1,str3);
        System.out.println(score1);
        System.out.println(score2);*/

        List<String> tagList1 = Arrays.asList("Java", "大一", "男");
        List<String> tagList2 = Arrays.asList("Java", "大一", "女");
        List<String> tagList3 = Arrays.asList("Python", "大二", "女");

        int score1 = AlgorithmUtils.minDistance(tagList1, tagList2);
        int score2=AlgorithmUtils.minDistance(tagList1,tagList3);
        System.out.println(score1);
        System.out.println(score2);
    }


}
