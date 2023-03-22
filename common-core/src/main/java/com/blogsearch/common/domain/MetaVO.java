package com.blogsearch.common.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class MetaVO implements Serializable {
    Integer total_count;

    Integer pageable_count;

    Boolean is_end;
}
