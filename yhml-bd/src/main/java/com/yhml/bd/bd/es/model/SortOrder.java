package com.yhml.bd.bd.es.model;

/**
 * @author: Jfeng
 * @date: 2018/7/6
 */
public enum SortOrder {
    ASC {
        public String toString() {
            return "asc";
        }
    },
    DESC {
        public String toString() {
            return "desc";
        }
    };

}
