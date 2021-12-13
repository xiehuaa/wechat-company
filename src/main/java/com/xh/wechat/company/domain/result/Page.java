package com.xh.wechat.company.domain.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Create By IntelliJ IDEA
 *
 * @author: XieHua
 * @date: 2021-12-09 16:59
 */
@Setter
@Getter
public class Page<T> {
    private int pageNum;
    private int pageSize;
    private int total;
    private int pages;
    private List<T> data;
}
