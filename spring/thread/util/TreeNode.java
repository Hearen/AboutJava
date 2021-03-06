package com.worksap.morphling.raptor.dump.thread.util;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TreeNode {
    private String callTrace;
    private int count;
    private List<TreeNode> children;
}
