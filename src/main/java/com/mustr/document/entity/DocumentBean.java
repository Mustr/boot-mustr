package com.mustr.document.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.mustr.common.entity.Mu;

import cn.hutool.core.io.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 文档实体
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
public class DocumentBean extends Mu {
    private static final long serialVersionUID = -592547895230739055L;

    public static final short STATUS_NOT = 0;//待转换
    public static final short STATUS_NORMAL = 1;//正常
    public static final short STATUS_FAIL = 2;//转换失败
    
    private Long projectId;
    
    private Long fileId;
    
    private Long convertFileId;
    
    private Long size;
    
    private String remark;
    
    private Long creatorId;
    
    private LocalDateTime createTime;
    
    private Short status;//'0：待转换,1:正常,2转换失败',
    
    public String getSizeStr() {
        if (size != null) {
            return FileUtil.readableFileSize(size);
        }
        return null;
    }
    
    public String getFormatCreateTime() {
        if (createTime != null) {
            return createTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        return null;
    }
}
