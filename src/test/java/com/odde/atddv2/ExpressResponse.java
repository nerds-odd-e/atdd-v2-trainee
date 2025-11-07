package com.odde.atddv2;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExpressResponse {

    private int status;
    private String msg;
    private Result result;

    @Data
    public static class Result {
        private String number;
        private String type;
        private String typename;
        private String logo;
        private List<ExpressItem> list = new ArrayList<>();
        private int deliverystatus;
        private int issign;

        @Data
        public static class ExpressItem {
            private String time;
            private String status;
        }
    }
}
