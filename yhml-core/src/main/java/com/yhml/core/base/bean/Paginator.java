package com.yhml.core.base.bean;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 分页器，根据pageNum,pageSize,totalItem用于页面上分页显示多项内容，计算页码，方便页面分页使用.
 */
@Getter
public class Paginator extends BaseBean {
    private static final long serialVersionUID = 6089482156906595931L;

    private static int DEFAULT_SLIDERS_COUNT = 5;

    private int pageSize;        //分页大小
    private int pageNum;        //页数
    private int totalCount;        //总记录数

    private int totalPages;        //总页数
    private Boolean isFirstPage;    //是否是首页（第一页）
    private Boolean isLastPage;        //是否是最后一页
    private Integer[] numList;        //滑动页码列表
    private int prePageNum;        //前一页，如果有返回上一页，没有返回当前页
    private int nextPageNum;    //后一页，如果有返回下一页，没有返回当前页
    private Boolean hasPrePage;        //有没有前一页
    private Boolean hasNextPage;    //有没有后一页
    private Boolean hasBegDotDot;    //有没有前点点点
    private Boolean hasEndDotDot;    //有没有后点点点
    private Boolean showFirstPage;    //是否需要显示第一页
    private Boolean showLastPage;    //是否需要显示最后一页

    public Paginator(int pageNum, int pageSize, int totalItems) {
        super();
        this.pageSize = pageSize;
        this.totalCount = totalItems;
        this.pageNum = computePageNo(pageNum);

        calc();
    }

    /**
     * 页码滑动窗口，并将当前页尽可能地放在滑动窗口的中间部位。
     *
     * @param currentPageNumber
     * @param lastPageNumber
     * @param count
     * @return
     */
    private static Integer[] generateLinkPageNumbers(int currentPageNumber, int lastPageNumber, int count) {
        int avg = count / 2;

        int startPageNumber = currentPageNumber - avg;
        if (startPageNumber <= 0) {
            startPageNumber = 1;
        }

        int endPageNumber = currentPageNumber + avg;
        if (endPageNumber > lastPageNumber) {
            endPageNumber = lastPageNumber;
        }

        List<Integer> result = new ArrayList<>();
        for (int i = startPageNumber; i <= endPageNumber; i++) {
            result.add(i);
        }
        return result.toArray(new Integer[result.size()]);
    }

    private static int computePageNumber(int pageNum, int pageSize, int totalItems) {
        if (pageNum <= 1) {
            return 1;
        }
        if (Integer.MAX_VALUE == pageNum || pageNum > computeLastPageNumber(totalItems, pageSize)) { //last page
            return computeLastPageNumber(totalItems, pageSize);
        }
        return pageNum;
    }

    private static int computeLastPageNumber(int totalItems, int pageSize) {
        if (pageSize <= 0)
            return 1;
        int result = (int) (totalItems % pageSize == 0 ? totalItems / pageSize : totalItems / pageSize + 1);
        if (result <= 1)
            result = 1;
        return result;
    }

    public static void main(String[] args) {
        Paginator paginator = new Paginator(10, 20, 5);
        System.out.println(paginator.toString());
    }

    private void calc() {

        //totalPages
        if (totalCount > 0 && pageSize > 0) {
            int count = totalCount / pageSize;
            if (totalCount % pageSize > 0) {
                count++;
            }
            totalPages = count;
        } else {
            totalPages = 0;
        }

        //isFirstPage
        isFirstPage = pageNum <= 1;

        //isLastPage
        isLastPage = pageNum >= totalPages;

        //hasPrePage
        hasPrePage = pageNum - 1 >= 1;

        //hasNextPage
        hasNextPage = pageNum + 1 <= totalPages;

        //prePageNum
        prePageNum = hasPrePage ? pageNum - 1 : pageNum;

        //nextPageNum
        nextPageNum = hasNextPage ? pageNum + 1 : pageNum;

        numList = generateLinkPageNumbers(pageNum, totalPages, DEFAULT_SLIDERS_COUNT);

        if (numList != null && numList.length > 0) {
            hasBegDotDot = numList[0] > 2;
            hasEndDotDot = numList[numList.length - 1] < totalPages - 1;
            showFirstPage = numList[0] > 1;
            showLastPage = numList[numList.length - 1] < totalPages;
        } else {
            hasEndDotDot = false;
            showFirstPage = false;
            showLastPage = false;
        }

    }

    /**
     * 计算分页的页码
     *
     * @param pageNum
     * @return
     */
    protected int computePageNo(int pageNum) {
        return computePageNumber(pageNum, pageSize, totalCount);
    }

}
