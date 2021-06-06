package com.mustr.document.entity;

import com.mustr.common.entity.Mu;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @Description: 项目实体
 * @author mustr
 * @date 2021-6-6
 *
 */

@Setter
@Getter
public class ProjectBean extends Mu {
    private static final long serialVersionUID = -4850172692795098573L;

    private Long parentId;
    
    private String webhook;
    
    private Short status;
}
