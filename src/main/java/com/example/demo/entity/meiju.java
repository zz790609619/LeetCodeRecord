package com.example.demo.entity;

/**
 * @Author helloc
 * @Date 2019/9/12 17:28
 * @Version 1.0
 */
public enum meiju {
        白羊座(1),
        金牛座(2);

        private int text;

        meiju(int text) {
            this.text = text;
        }


        public static meiju fromString(int text) {
            for (meiju b : meiju.values()) {
                if (b.text==text) {
                    return b;
                }
            }
            return null;
        }
    }
