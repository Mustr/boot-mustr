package com.mustr.common.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 文件对象
 * @author chenxj
 * @Date 2021-6-7
 *
 */

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileBean extends Mu {
    private static final long serialVersionUID = -7743743075961575337L;

    private Long size;
    
    private LocalDateTime createTime;
    
    private String contentType;
    
    private String suffix;
    
    private String bucket;
    
    private String objectName;
    
}
