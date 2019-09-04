package com.zws.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {


    public  static  Pageable Pageable(Integer page, Integer pageSize){

        page = page <= 1 ? 0 : page -1;// 如果page为负数则修改为0，防止在首页点击上一页发生错误
        Sort sort = new Sort(Sort.Direction.DESC, "id");// 按id倒叙排列
        Pageable pageable = new PageRequest(page, pageSize, sort);

        return  pageable;

    }

    public  static  Pageable Pageable(Integer page, Integer pageSize, String sortKey){

        page = page <= 1 ? 0 : page -1;// 如果page为负数则修改为0，防止在首页点击上一页发生错误
        Sort sort = new Sort(Sort.Direction.DESC, sortKey);// 按id倒叙排列
        Pageable pageable = new PageRequest(page, pageSize, sort);

        return  pageable;

    }


}
