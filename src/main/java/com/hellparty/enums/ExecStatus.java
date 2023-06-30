package com.hellparty.enums;

/**
 * title        :
 * author       : sim
 * date         : 2023-06-30
 * description  :
 */
public enum ExecStatus {

    H("헬스중"), W("준비중"), I("부상으로 쉬는중");

    private String statusName;

    ExecStatus(String statusName){
        this.statusName = statusName;
    }
}
