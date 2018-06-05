package com.jsq.util;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Administrator on 2017-08-17.
 */
public class PageUtil {

    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "10";

    private PageUtil(){

    };

    public static Page getPage(Pagination pagination, List records){
        Page page = new Page();
        page.setSize(pagination.getSize());
        page.setCurrent(pagination.getCurrent());
        page.setRecords(records);
        page.setTotal(pagination.getTotal());
        return page;
    }

    public static void startPage(int page,int size){
        PageHelper.startPage(page + 1, size);
    }

    public static Page getPage(int page, int size){
        return new Page(page + 1,size);
    }

    public static void remove(){
        PageHelper.remove();
    }

    public static Page getPage(Pageable pageable){
        return  new Page(pageable.getPageNumber()+1,pageable.getPageSize());
    }

    public static HttpHeaders generateHttpHeaders(Page<?> page, String baseURL) throws URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("x-total-count", "" + page.getTotal());
        headers.add("Link", baseURL);
        return headers;
    }
}
