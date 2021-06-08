package com.mustr.utils;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 媒体类型
 * @author mustr
 * @Date 2021-6-7
 *
 */
public enum MediaType {
    TEXT("文本", Lists.newArrayList("txt", "doc", "docx", "chm", "pdf", "ppt", "pptx", "htm", "html", "xls", "xlsx","md")),

    AUDIO("音频", Lists.newArrayList("wav", "mp3", "ra", "ram", "midi", "m4a", "wma", "aac", "flac")),

    VIDEO("视频", Lists.newArrayList("rm", "rmvb", "wmv", "avi", "mpeg", "asf", "mp4", "mid", "wm", "mpg", "mpe", "3gp", "flv", "mov", "mkv")),

    IMAGE("图片", Lists.newArrayList("jpg", "gif", "jpeg", "bmp", "png", "tif", "svg", "webp", "ico")),

    ANIMATION("动画", Lists.newArrayList("swf", "fla", "ani", "mb")),

    ZIP("压缩包", Lists.newArrayList("rar", "zip", "7z")),
    
    OTHER("其他", java.util.Collections.emptyList());

    private final String desc;
    private final List<String> types;

    private MediaType(String desc, List<String> types) {
        this.desc = desc;
        this.types = types;
    }

    public String getDesc() {
        return desc;
    }

    public List<String> getTypes() {
        return types;
    }
    
    @Override
    public String toString() {
        return "[ " + this.name() + "," + this.desc + " ]";
    }
    
    /**
     * 根据文件类型获取媒体类型
     * @param fileType 文件后缀
     * @return
     */
    public static MediaType getMediaType(String fileType) {
        if (fileType == null || "".equals(fileType)) {
            return OTHER;
        }
        for (MediaType mediaType : values()) {
            if (mediaType.getTypes().contains(fileType)) {
                return mediaType;
            }
        }
        return OTHER;
    }
}
