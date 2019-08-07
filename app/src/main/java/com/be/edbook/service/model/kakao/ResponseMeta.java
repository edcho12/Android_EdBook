package com.be.edbook.service.model.kakao;

public class ResponseMeta {
    public boolean is_end;
    public int pageable_count;
    public int total_count;

    public ResponseMeta(){
        is_end = false;
        pageable_count = 0;
        total_count = 0;
    }
}
